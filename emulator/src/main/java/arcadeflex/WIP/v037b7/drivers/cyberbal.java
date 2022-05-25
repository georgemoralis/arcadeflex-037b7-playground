/***************************************************************************

Cyberball Memory Map
--------------------

CYBERBALL 68000 MEMORY MAP

Function                           Address        R/W  DATA
-------------------------------------------------------------
Program ROM                        000000-07FFFF  R    D0-D15

Switch 1 (Player 1)                FC0000         R    D0-D7
Action                                            R    D5
Step (Development Only)                           R    D4
Joystick Left                                     R    D3
Joystick Right                                    R    D2
Joystick Down                                     R    D1
Joystick Up                                       R    D0

Switch 2 (Player 2)                FC2000         R    D0-D7
Action                                            R    D5
Step (Development Only)                           R    D4
Joystick Left                                     R    D3
Joystick Right                                    R    D2
Joystick Down                                     R    D1
Joystick Up                                       R    D0

Self-Test (Active Low)             FC4000         R    D7
Vertical Blank                                    R    D6
Audio Busy Flag (Active Low)                      R    D5

Audio Receive Port                 FC6000         R    D8-D15

EEPROM                             FC8000-FC8FFE  R/W  D0-D7

Color RAM                          FCA000-FCAFFE  R/W  D0-D15

Unlock EEPROM                      FD0000         W    xx
Sound Processor Reset              FD2000         W    xx
Watchdog reset                     FD4000         W    xx
IRQ Acknowledge                    FD6000         W    xx
Audio Send Port                    FD8000         W    D8-D15

Playfield RAM                      FF0000-FF1FFF  R/W  D0-D15
Alpha RAM                          FF2000-FF2FFF  R/W  D0-D15
Motion Object RAM                  FF3000-FF3FFF  R/W  D0-D15
RAM                                FF4000-FFFFFF  R/W

****************************************************************************/


/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.drivers;

import static arcadeflex.WIP.v037b7.machine.atarigen.*;
import static arcadeflex.WIP.v037b7.machine.atarigenH.*;        
import static arcadeflex.WIP.v037b7.vidhrdw.cyberbal.*;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;        
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;        
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.WIP.v037b7.sndhrdw.atarijsa.*;
import static arcadeflex.WIP.v037b7.sndhrdw.atarijsaH.*;        
import arcadeflex.common.ptrLib;
import arcadeflex.common.ptrLib.ShortPtr;
import arcadeflex.common.subArrays.IntSubArray;
import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.input.*;
import static arcadeflex.v037b7.mame.inputH.*;        
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound._2151intf.*;
import static arcadeflex.v037b7.sound._2151intfH.*;        
import static arcadeflex.v037b7.sound.dac.*;
import static arcadeflex.v037b7.sound.dacH.*;
import static arcadeflex.v037b7.sound.mixerH.*;
import static arcadeflex.v037b7.sound.streams.*;
import static gr.codebb.arcadeflex.common.libc.cstring.*;        
import static java.lang.Math.pow;

public class cyberbal
{
	
	
	
	/* better to leave this on; otherwise, you end up playing entire games out of the left speaker */
	static final int USE_MONO_SOUND	= 1;
	
	/* don't use this; it's incredibly slow (10k interrupts/second!) and doesn't really work */
	/* it's left in primarily for documentation purposes */
	/*#define EMULATE_SOUND_68000 1*/
	
	
	/* internal prototypes and variables */
	static UBytePtr bank_base = new UBytePtr();
	static int fast_68k_int, io_68k_int;
	static int sound_data_from_68k, sound_data_from_6502;
	static int sound_data_from_68k_ready, sound_data_from_6502_ready;
	
	
	
	/*************************************
	 *
	 *	Initialization
	 *
	 *************************************/
	
	static atarigen_int_callbackPtr update_interrupts = new atarigen_int_callbackPtr() {
            @Override
            public void handler() {
                int newstate1 = 0;
		int newstate2 = 0;
		int temp;
	
		if (atarigen_sound_int_state != 0)
			newstate1 |= 1;
		if (atarigen_video_int_state != 0)
			newstate2 |= 1;
	
		if (newstate1 != 0)
			cpu_set_irq_line(0, newstate1, ASSERT_LINE);
		else
			cpu_set_irq_line(0, 7, CLEAR_LINE);
	
		if (newstate2 != 0)
			cpu_set_irq_line(2, newstate2, ASSERT_LINE);
		else
			cpu_set_irq_line(2, 7, CLEAR_LINE);
	
		/* check for screen swapping */
		temp = input_port_2_r.handler(0);
		if ((temp & 1) != 0) cyberbal_set_screen(0);
		else if ((temp & 2) != 0) cyberbal_set_screen(1);
            }
        };
	
	
	public static InitMachinePtr init_machine = new InitMachinePtr() { public void handler() 
	{
		atarigen_eeprom_reset();
		atarigen_slapstic_reset();
		atarigen_interrupt_reset(update_interrupts);
		atarigen_scanline_timer_reset(cyberbal_scanline_update, 8);
		atarigen_sound_io_reset(1);
	
		/* reset the sound system */
		bank_base = new UBytePtr(memory_region(REGION_CPU2), 0x10000);
		cpu_setbank(8, new UBytePtr(bank_base, 0x0000));
		fast_68k_int = io_68k_int = 0;
		sound_data_from_68k = sound_data_from_6502 = 0;
		sound_data_from_68k_ready = sound_data_from_6502_ready = 0;
	
		/* CPU 2 doesn't run until reset */
		cpu_set_reset_line(2,ASSERT_LINE);
	
		/* make sure we're pointing to the right screen by default */
		cyberbal_set_screen(0);
	} };
	
	
	static atarigen_int_callbackPtr cyberb2p_update_interrupts = new atarigen_int_callbackPtr() {
            @Override
            public void handler() {
                int newstate = 0;
	
		if (atarigen_video_int_state != 0)
			newstate |= 1;
		if (atarigen_sound_int_state != 0)
			newstate |= 3;
	
		if (newstate != 0)
			cpu_set_irq_line(0, newstate, ASSERT_LINE);
		else
			cpu_set_irq_line(0, 7, CLEAR_LINE);
            }
        };
	
	public static InitMachinePtr cyberb2p_init_machine = new InitMachinePtr() { public void handler() 
	{
		atarigen_eeprom_reset();
		atarigen_interrupt_reset(cyberb2p_update_interrupts);
		atarigen_scanline_timer_reset(cyberbal_scanline_update, 8);
		atarijsa_reset();
	
		/* make sure we're pointing to the only screen */
		cyberbal_set_screen(0);
	} };
	
	
	
	/*************************************
	 *
	 *	I/O read dispatch.
	 *
	 *************************************/
	
	public static ReadHandlerPtr special_port0_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int temp = input_port_0_r.handler(offset);
		if (atarigen_cpu_to_sound_ready != 0) temp ^= 0x0080;
		return temp;
	} };
	
	
	public static ReadHandlerPtr special_port2_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int temp = input_port_2_r.handler(offset);
		if (atarigen_cpu_to_sound_ready != 0) temp ^= 0x2000;
		return temp;
	} };
	
	
	public static ReadHandlerPtr sound_state_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int temp = 0xffff;
	
		if (atarigen_cpu_to_sound_ready != 0) temp ^= 0xffff;
		return temp;
	} };
	
	
	
	/*************************************
	 *
	 *	Extra I/O handlers.
	 *
	 *************************************/
	
	public static WriteHandlerPtr p2_reset_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		cpu_set_reset_line(2,CLEAR_LINE);
	} };
	
	
	
	/*************************************
	 *
	 *	6502 Sound Interface
	 *
	 *************************************/
	
	public static ReadHandlerPtr special_port3_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int temp = input_port_3_r.handler(offset);
		if ((readinputport(0) & 0x8000)==0) temp ^= 0x80;
		if (atarigen_cpu_to_sound_ready != 0) temp ^= 0x40;
		if (atarigen_sound_to_cpu_ready != 0) temp ^= 0x20;
		return temp;
	} };
	
	
	public static ReadHandlerPtr sound_6502_stat_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int temp = 0xff;
	
		if (sound_data_from_6502_ready != 0) temp ^= 0x80;
		if (sound_data_from_68k_ready != 0) temp ^= 0x40;
		return temp;
	} };
	
	
	public static WriteHandlerPtr sound_bank_select_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		cpu_setbank(8, new UBytePtr(bank_base, 0x1000 * ((data >> 6) & 3)));
	} };
	
	
	public static ReadHandlerPtr sound_68k_6502_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		sound_data_from_68k_ready = 0;
		return sound_data_from_68k;
	} };
	
	
	public static WriteHandlerPtr sound_68k_6502_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		sound_data_from_6502 = data;
		sound_data_from_6502_ready = 1;
	
/*TODO*///	#ifdef EMULATE_SOUND_68000
/*TODO*///		if (!io_68k_int)
/*TODO*///		{
/*TODO*///			io_68k_int = 1;
/*TODO*///			update_sound_68k_interrupts();
/*TODO*///		}
/*TODO*///	#else
		handle_68k_sound_command(data);
/*TODO*///	#endif
	} };
	
	
	
	/*************************************
	 *
	 *	68000 Sound Interface
	 *
	 *************************************/
	
	static void update_sound_68k_interrupts()
	{
/*TODO*///	#ifdef EMULATE_SOUND_68000
/*TODO*///		int newstate = 0;
/*TODO*///	
/*TODO*///		if (fast_68k_int != 0)
/*TODO*///			newstate |= 6;
/*TODO*///		if (io_68k_int != 0)
/*TODO*///			newstate |= 2;
/*TODO*///	
/*TODO*///		if (newstate != 0)
/*TODO*///			cpu_set_irq_line(3, newstate, ASSERT_LINE);
/*TODO*///		else
/*TODO*///			cpu_set_irq_line(3, 7, CLEAR_LINE);
/*TODO*///	#endif
	}
	
	
	public static InterruptPtr sound_68k_irq_gen = new InterruptPtr() { public int handler() 
	{
		if (fast_68k_int==0)
		{
			fast_68k_int = 1;
			update_sound_68k_interrupts();
		}
		return 0;
	} };
	
	
	public static WriteHandlerPtr io_68k_irq_ack_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (io_68k_int != 0)
		{
			io_68k_int = 0;
			update_sound_68k_interrupts();
		}
	} };
	
	
	public static ReadHandlerPtr sound_68k_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int temp = (sound_data_from_6502 << 8) | 0xff;
	
		sound_data_from_6502_ready = 0;
	
		if (sound_data_from_6502_ready != 0) temp ^= 0x08;
		if (sound_data_from_68k_ready != 0) temp ^= 0x04;
		return temp;
	} };
	
	
	public static WriteHandlerPtr sound_68k_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0xff000000)==0)
		{
			sound_data_from_68k = (data >> 8) & 0xff;
			sound_data_from_68k_ready = 1;
		}
	} };
	
	
	public static WriteHandlerPtr sound_68k_dac_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		DAC_signed_data_w.handler((offset >> 4) & 1, ((data >> 8)&0xffff));
	
		if (fast_68k_int != 0)
		{
			fast_68k_int = 0;
			update_sound_68k_interrupts();
		}
	} };
	
	
	/*************************************
	 *
	 *	68000 Sound Simulator
	 *
	 *************************************/
	
	static final int SAMPLE_RATE = 10000;
	
	public static class sound_descriptor
	{
		/*00*/int start_address_h;
		/*02*/int start_address_l;
		/*04*/int end_address_h;
		/*06*/int end_address_l;
		/*08*/int reps;
		/*0a*/int volume;
		/*0c*/int delta_volume;
		/*0e*/int target_volume;
		/*10*/int voice_priority;	/* voice high, priority low */
		/*12*/int buffer_number;		/* buffer high, number low */
		/*14*/int continue_unused;	/* continue high, unused low */
	};
	
	public static class voice_descriptor
	{
		int /*UINT8*/ playing;
		UBytePtr start = new UBytePtr();
		UBytePtr current = new UBytePtr();
		UBytePtr end = new UBytePtr();
		int /*UINT16*/ reps;
		int /*INT16*/ volume;
		int /*INT16*/ delta_volume;
		int /*INT16*/ target_volume;
		int /*UINT8*/ priority;
		int /*UINT8*/ number;
		int /*UINT8*/ buffer;
		int /*UINT8*/ cont;
		IntSubArray chunk = new IntSubArray(48);
		int /*UINT8*/ chunk_remaining;
	};
	
	
	static int[] volume_table;
	static voice_descriptor[] voices = new voice_descriptor[6];
	static int sound_enabled;
	static int stream_channel;
	
	
	static void decode_chunk(UBytePtr memory, IntSubArray output, int overall)
	{
		int volume_bits = memory.READ_WORD();
		int volume, i, j;
                
                int _output=0;
	
		memory.inc(2);
		for (i = 0; i < 3; i++)
		{
			/* get the volume */
			volume = ((overall & 0x03e0) + (volume_bits & 0x3e0)) >> 1;
			volume_bits = ((volume_bits >> 5) & 0x07ff) | ((volume_bits << 11) & 0xf800);
	
			for (j = 0; j < 4; j++)
			{
				int data = memory.READ_WORD();
				memory.inc(2);
				output.write(_output++, volume_table[volume | ((data >>  0) & 0x000f)]);
				output.write(_output++, volume_table[volume | ((data >>  4) & 0x000f)]);
				output.write(_output++, volume_table[volume | ((data >>  8) & 0x000f)]);
				output.write(_output++, volume_table[volume | ((data >> 12) & 0x000f)]);
			}
		}
	}
	
	
	static StreamInitMultiPtr sample_stream_update = new StreamInitMultiPtr() {
            @Override
            public void handler(int param, ShortPtr[] buffer, int length) {
                ShortPtr buf_left = buffer[0];
		ShortPtr buf_right = buffer[1];
		int i;
	
		/* reset the buffers so we can add into them */
		memset(buf_left, 0, length);
		memset(buf_right, 0, length);
	
		/* loop over voices */
		for (i = 0; i < 6; i++)
		{
			//voice_descriptor *voice = &voices[i];
			int left = length;
			ShortPtr output;
                        int _output=0;
	
			/* bail if not playing */
			if (voices[i].playing==0 || voices[i].buffer==0)
				continue;
	
			/* pick a buffer */
			output = (voices[i].buffer == 0x10) ? buf_left : buf_right;
	
			/* loop until we're done */
			while (left != 0)
			{
				IntSubArray source;
				int this_batch;
	
				if (voices[i].chunk_remaining == 0)
				{
					/* loop if necessary */
					if (voices[i].current.offset >= voices[i].end.offset)
					{
						if (--voices[i].reps == 0)
						{
							voices[i].playing = 0;
							break;
						}
						voices[i].current = new UBytePtr(voices[i].start);
					}
	
					/* decode this chunk */
					decode_chunk(voices[i].current, voices[i].chunk, voices[i].volume);
					voices[i].current.inc(26);
					voices[i].chunk_remaining = 48;
	
					/* update volumes */
					voices[i].volume += voices[i].delta_volume;
					if ((voices[i].volume & 0xffe0) == (voices[i].target_volume & 0xffe0))
						voices[i].delta_volume = 0;
				}
	
				/* determine how much to copy */
				this_batch = (left > voices[i].chunk_remaining) ? voices[i].chunk_remaining : left;
				source = new IntSubArray(voices[i].chunk, 48 - voices[i].chunk_remaining);
				voices[i].chunk_remaining -= this_batch;
				left -= this_batch;
	
				while (this_batch-- != 0) {
                                    output.write(_output, (short) (output.read(_output) + source.readinc()));
                                    _output++;					
                                }
			}
		}
            }
        };
        
	
	static ShStartPtr samples_start = new ShStartPtr() {
            @Override
            public int handler(MachineSound msound) {
                String names[] =
		{
			"68000 Simulator left",
			"68000 Simulator right"
		};
                
		int[] vol = new int[2];
                int i, j;
	
		
		/* allocate volume table */
		volume_table = new int[64 * 16];
		if (volume_table==null)
			return 1;
	
		/* build the volume table */
		for (j = 0; j < 64; j++)
		{
			double factor = pow(0.5, (double)j * 0.25);
			for (i = 0; i < 16; i++)
				volume_table[j * 16 + i] = ((int)(factor * (i << 12))) & 0xffff;
		}
	
		/* get stream channels */
/*TODO*///	#if USE_MONO_SOUND
		vol[0] = MIXER(50, MIXER_PAN_CENTER);
		vol[1] = MIXER(50, MIXER_PAN_CENTER);
/*TODO*///	#else
/*TODO*///		vol[0] = MIXER(100, MIXER_PAN_LEFT);
/*TODO*///		vol[1] = MIXER(100, MIXER_PAN_RIGHT);
/*TODO*///	#endif
		stream_channel = stream_init_multi(2, names, vol, SAMPLE_RATE, 0, sample_stream_update);
	
		/* reset voices */
		//memset(voices, 0, voices.length);
                int _longo = voices.length;
                for (int _i=0 ; _i<_longo ; _i++)
                    voices[_i] = new voice_descriptor();
                
		sound_enabled = 1;
	
		return 0;
            }
        };
        
	
	static ShStopPtr samples_stop = new ShStopPtr() {
            @Override
            public void handler() {
                if (volume_table != null)
			volume_table = null;
            }
        };
	
	
	static void handle_68k_sound_command(int command)
	{
		sound_descriptor sound = new sound_descriptor();
		//voice_descriptor voice;
		int offset;
		int actual_delta, actual_volume;
		int temp;
	
		/* read the data to reset the latch */
		sound_68k_r.handler(0);
	
		switch (command)
		{
			case 0:		/* reset */
				break;
	
			case 1:		/* self-test */
				sound_68k_w.handler(0, 0x40 << 8);
				break;
	
			case 2:		/* status */
				sound_68k_w.handler(0, 0x00 << 8);
				break;
	
			case 3:
				sound_enabled = 0;
				break;
	
			case 4:
				sound_enabled = 1;
				break;
	
			default:
				/* bail if we're not enabled or if we get a bogus voice */
				offset = memory_region(REGION_CPU4).READ_WORD(0x1e2a + 2 * command);
/*TODO*///				sound = (struct sound_descriptor *)&memory_region(REGION_CPU4)[offset];
	
				/* check the voice */
				temp = sound.voice_priority >> 8;
				if (sound_enabled==0 || temp > 5)
					break;
				//voice = voices[temp];
	
				/* see if we're allowed to take over */
				actual_volume = sound.volume;
				actual_delta = sound.delta_volume;
				if (voices[temp].playing!=0 && voices[temp].cont!=0)
				{
					temp = sound.buffer_number & 0xff;
					if (voices[temp].number != temp)
						break;
	
					/* if we're ramping, adjust for the current volume */
					if (actual_delta != 0)
					{
						actual_volume = voices[temp].volume;
						if ((actual_delta < 0 && voices[temp].volume <= sound.target_volume) ||
							(actual_delta > 0 && voices[temp].volume >= sound.target_volume))
							actual_delta = 0;
					}
				}
				else if (voices[temp].playing != 0)
				{
					temp = sound.voice_priority & 0xff;
					if (voices[temp].priority > temp ||
						(voices[temp].priority == temp && (temp & 1) == 0))
						break;
				}
	
				/* fill in the voice; we're taking over */
				voices[temp].playing = 1;
				voices[temp].start = new UBytePtr(memory_region(REGION_CPU4), (sound.start_address_h << 16) | sound.start_address_l);
				voices[temp].current = new UBytePtr(voices[temp].start);
				voices[temp].end = new UBytePtr(memory_region(REGION_CPU4), (sound.end_address_h << 16) | sound.end_address_l);
				voices[temp].reps = sound.reps;
				voices[temp].volume = actual_volume;
				voices[temp].delta_volume = actual_delta;
				voices[temp].target_volume = sound.target_volume;
				voices[temp].priority = sound.voice_priority & 0xff;
				voices[temp].number = sound.buffer_number & 0xff;
				voices[temp].buffer = sound.buffer_number >> 8;
				voices[temp].cont = sound.continue_unused >> 8;
				voices[temp].chunk_remaining = 0;
				break;
		}
	}
	
	
	
	/*************************************
	 *
	 *	Main CPU memory handlers
	 *
	 *************************************/
	
	static MemoryReadAddress main_readmem[] =
	{
		new MemoryReadAddress( 0x000000, 0x03ffff, MRA_ROM ),
		new MemoryReadAddress( 0xfc0000, 0xfc03ff, atarigen_eeprom_r ),
		new MemoryReadAddress( 0xfc8000, 0xfcffff, atarigen_sound_upper_r ),
		new MemoryReadAddress( 0xfe0000, 0xfe0fff, special_port0_r ),
		new MemoryReadAddress( 0xfe1000, 0xfe1fff, input_port_1_r ),
		new MemoryReadAddress( 0xfe8000, 0xfe8fff, cyberbal_paletteram_2_r ),
		new MemoryReadAddress( 0xfec000, 0xfecfff, cyberbal_paletteram_1_r ),
		new MemoryReadAddress( 0xff0000, 0xff1fff, MRA_BANK1 ),
		new MemoryReadAddress( 0xff2000, 0xff3fff, MRA_BANK2 ),
		new MemoryReadAddress( 0xff4000, 0xff5fff, MRA_BANK3 ),
		new MemoryReadAddress( 0xff6000, 0xff7fff, MRA_BANK4 ),
		new MemoryReadAddress( 0xff8000, 0xff9fff, MRA_BANK5 ),
		new MemoryReadAddress( 0xffa000, 0xffbfff, MRA_BANK6 ),
		new MemoryReadAddress( 0xffc000, 0xffffff, MRA_BANK7 ),
		new MemoryReadAddress( -1 )  /* end of table */
	};
	
	static MemoryWriteAddress main_writemem[] =
	{
		new MemoryWriteAddress( 0x000000, 0x03ffff, MWA_ROM ),
		new MemoryWriteAddress( 0xfc0000, 0xfc03ff, atarigen_eeprom_w, atarigen_eeprom, atarigen_eeprom_size ),
		new MemoryWriteAddress( 0xfd0000, 0xfd1fff, atarigen_eeprom_enable_w ),
		new MemoryWriteAddress( 0xfd2000, 0xfd3fff, atarigen_sound_reset_w ),
		new MemoryWriteAddress( 0xfd4000, 0xfd5fff, watchdog_reset_w ),
		new MemoryWriteAddress( 0xfd6000, 0xfd7fff, p2_reset_w ),
		new MemoryWriteAddress( 0xfd8000, 0xfd9fff, atarigen_sound_upper_w ),
		new MemoryWriteAddress( 0xfe8000, 0xfe8fff, cyberbal_paletteram_2_w, paletteram_2 ),
		new MemoryWriteAddress( 0xfec000, 0xfecfff, cyberbal_paletteram_1_w, paletteram ),
		new MemoryWriteAddress( 0xff0000, 0xff1fff, cyberbal_playfieldram_2_w ),
		new MemoryWriteAddress( 0xff2000, 0xff3fff, MWA_BANK2 ),
		new MemoryWriteAddress( 0xff4000, 0xff5fff, cyberbal_playfieldram_1_w ),
		new MemoryWriteAddress( 0xff6000, 0xff7fff, MWA_BANK4 ),
		new MemoryWriteAddress( 0xff8000, 0xff9fff, MWA_BANK5 ),
		new MemoryWriteAddress( 0xffa000, 0xffbfff, MWA_NOP ),
		new MemoryWriteAddress( 0xffc000, 0xffffff, MWA_BANK7 ),
		new MemoryWriteAddress( -1 )  /* end of table */
	};
	
	
	
	/*************************************
	 *
	 *	Extra CPU memory handlers
	 *
	 *************************************/
	
	static MemoryReadAddress extra_readmem[] =
	{
		new MemoryReadAddress( 0x000000, 0x03ffff, MRA_ROM ),
		new MemoryReadAddress( 0xfe0000, 0xfe0fff, special_port0_r ),
		new MemoryReadAddress( 0xfe1000, 0xfe1fff, input_port_1_r ),
		new MemoryReadAddress( 0xfe8000, 0xfe8fff, cyberbal_paletteram_2_r ),
		new MemoryReadAddress( 0xfec000, 0xfecfff, cyberbal_paletteram_1_r ),
		new MemoryReadAddress( 0xff0000, 0xff1fff, MRA_BANK1 ),
		new MemoryReadAddress( 0xff2000, 0xff3fff, MRA_BANK2 ),
		new MemoryReadAddress( 0xff4000, 0xff5fff, MRA_BANK3 ),
		new MemoryReadAddress( 0xff6000, 0xff7fff, MRA_BANK4 ),
		new MemoryReadAddress( 0xff8000, 0xff9fff, MRA_BANK5 ),
		new MemoryReadAddress( 0xffa000, 0xffbfff, MRA_BANK6 ),
		new MemoryReadAddress( 0xffc000, 0xffffff, MRA_BANK7 ),
		new MemoryReadAddress( -1 )  /* end of table */
	};
	
	static MemoryWriteAddress extra_writemem[] =
	{
		new MemoryWriteAddress( 0x000000, 0x03ffff, MWA_ROM ),
		new MemoryWriteAddress( 0xfc0000, 0xfdffff, atarigen_video_int_ack_w ),
		new MemoryWriteAddress( 0xfe8000, 0xfe8fff, cyberbal_paletteram_2_w ),
		new MemoryWriteAddress( 0xfec000, 0xfecfff, cyberbal_paletteram_1_w ),			/* player 2 palette RAM */
		new MemoryWriteAddress( 0xff0000, 0xff1fff, cyberbal_playfieldram_2_w, cyberbal_playfieldram_2 ),
		new MemoryWriteAddress( 0xff2000, 0xff3fff, MWA_BANK2 ),
		new MemoryWriteAddress( 0xff4000, 0xff5fff, cyberbal_playfieldram_1_w, cyberbal_playfieldram_1, atarigen_playfieldram_size ),
		new MemoryWriteAddress( 0xff6000, 0xff7fff, MWA_BANK4 ),
		new MemoryWriteAddress( 0xff8000, 0xff9fff, MWA_BANK5 ),
		new MemoryWriteAddress( 0xffa000, 0xffbfff, MWA_BANK6 ),
		new MemoryWriteAddress( 0xffc000, 0xffffff, MWA_NOP ),
		new MemoryWriteAddress( -1 )  /* end of table */
	};
	
	
	
	/*************************************
	 *
	 *	Sound CPU memory handlers
	 *
	 *************************************/
	
	static MemoryReadAddress sound_readmem[] =
	{
		new MemoryReadAddress( 0x0000, 0x1fff, MRA_RAM ),
		new MemoryReadAddress( 0x2000, 0x2001, YM2151_status_port_0_r ),
		new MemoryReadAddress( 0x2802, 0x2803, atarigen_6502_irq_ack_r ),
		new MemoryReadAddress( 0x2c00, 0x2c01, atarigen_6502_sound_r ),
		new MemoryReadAddress( 0x2c02, 0x2c03, special_port3_r ),
		new MemoryReadAddress( 0x2c04, 0x2c05, sound_68k_6502_r ),
		new MemoryReadAddress( 0x2c06, 0x2c07, sound_6502_stat_r ),
		new MemoryReadAddress( 0x3000, 0x3fff, MRA_BANK8 ),
		new MemoryReadAddress( 0x4000, 0xffff, MRA_ROM ),
		new MemoryReadAddress( -1 )  /* end of table */
	};
	
	
	static MemoryWriteAddress sound_writemem[] =
	{
		new MemoryWriteAddress( 0x0000, 0x1fff, MWA_RAM ),
		new MemoryWriteAddress( 0x2000, 0x2000, YM2151_register_port_0_w ),
		new MemoryWriteAddress( 0x2001, 0x2001, YM2151_data_port_0_w ),
		new MemoryWriteAddress( 0x2800, 0x2801, sound_68k_6502_w ),
		new MemoryWriteAddress( 0x2802, 0x2803, atarigen_6502_irq_ack_w ),
		new MemoryWriteAddress( 0x2804, 0x2805, atarigen_6502_sound_w ),
		new MemoryWriteAddress( 0x2806, 0x2807, sound_bank_select_w ),
		new MemoryWriteAddress( 0x3000, 0xffff, MWA_ROM ),
		new MemoryWriteAddress( -1 )  /* end of table */
	};
	
	
	
	/*************************************
	 *
	 *	68000 Sound CPU memory handlers
	 *
	 *************************************/
	
/*TODO*///	#ifdef EMULATE_SOUND_68000
/*TODO*///	
/*TODO*///	static UBytePtr ram = new UBytePtr();
/*TODO*///        
/*TODO*///	public static ReadHandlerPtr ram_r  = new ReadHandlerPtr() { public int handler(int offset) { return ram.READ_WORD(offset); } };
/*TODO*///	public static WriteHandlerPtr ram_w = new WriteHandlerPtr() {public void handler(int offset, int data) { COMBINE_WORD_MEM(ram, offset, data); } };
/*TODO*///	
/*TODO*///	static MemoryReadAddress sound_68k_readmem[] =
/*TODO*///	{
/*TODO*///		new MemoryReadAddress( 0x000000, 0x03ffff, MRA_ROM ),
/*TODO*///		new MemoryReadAddress( 0xff8000, 0xff87ff, sound_68k_r ),
/*TODO*///		new MemoryReadAddress( 0xfff000, 0xffffff, ram_r, ram ),
/*TODO*///		new MemoryReadAddress( -1 )  /* end of table */
/*TODO*///	};
/*TODO*///	
/*TODO*///	static MemoryWriteAddress sound_68k_writemem[] =
/*TODO*///	{
/*TODO*///		new MemoryWriteAddress( 0x000000, 0x03ffff, MWA_ROM ),
/*TODO*///		new MemoryWriteAddress( 0xff8800, 0xff8fff, sound_68k_w ),
/*TODO*///		new MemoryWriteAddress( 0xff9000, 0xff97ff, io_68k_irq_ack_w ),
/*TODO*///		new MemoryWriteAddress( 0xff9800, 0xff9fff, sound_68k_dac_w ),
/*TODO*///		new MemoryWriteAddress( 0xfff000, 0xffffff, ram_w, ram ),
/*TODO*///		new MemoryWriteAddress( -1 )  /* end of table */
/*TODO*///	};
/*TODO*///	
/*TODO*///	#endif
	
	
	
	/*************************************
	 *
	 *	2-player main CPU memory handlers
	 *
	 *************************************/
	
	static MemoryReadAddress cyberb2p_readmem[] =
	{
		new MemoryReadAddress( 0x000000, 0x07ffff, MRA_ROM ),
		new MemoryReadAddress( 0xfc0000, 0xfc0003, input_port_0_r ),
		new MemoryReadAddress( 0xfc2000, 0xfc2003, input_port_1_r ),
		new MemoryReadAddress( 0xfc4000, 0xfc4003, special_port2_r ),
		new MemoryReadAddress( 0xfc6000, 0xfc6003, atarigen_sound_upper_r ),
		new MemoryReadAddress( 0xfc8000, 0xfc8fff, atarigen_eeprom_r ),
		new MemoryReadAddress( 0xfca000, 0xfcafff, MRA_BANK1 ),
		new MemoryReadAddress( 0xfe0000, 0xfe0003, sound_state_r ),
		new MemoryReadAddress( 0xff0000, 0xff1fff, MRA_BANK2 ),
		new MemoryReadAddress( 0xff2000, 0xff2fff, MRA_BANK3 ),
		new MemoryReadAddress( 0xff3000, 0xff3fff, MRA_BANK4 ),
		new MemoryReadAddress( 0xff4000, 0xffffff, MRA_BANK5 ),
		new MemoryReadAddress( -1 )  /* end of table */
	};
	
	static MemoryWriteAddress cyberb2p_writemem[] =
	{
		new MemoryWriteAddress( 0x000000, 0x07ffff, MWA_ROM ),
		new MemoryWriteAddress( 0xfc8000, 0xfc8fff, atarigen_eeprom_w, atarigen_eeprom, atarigen_eeprom_size ),
		new MemoryWriteAddress( 0xfca000, 0xfcafff, atarigen_666_paletteram_w, paletteram ),
		new MemoryWriteAddress( 0xfd0000, 0xfd0003, atarigen_eeprom_enable_w ),
		new MemoryWriteAddress( 0xfd2000, 0xfd2003, atarigen_sound_reset_w ),
		new MemoryWriteAddress( 0xfd4000, 0xfd4003, watchdog_reset_w ),
		new MemoryWriteAddress( 0xfd6000, 0xfd6003, atarigen_video_int_ack_w ),
		new MemoryWriteAddress( 0xfd8000, 0xfd8003, atarigen_sound_upper_w ),
		new MemoryWriteAddress( 0xff0000, 0xff1fff, cyberbal_playfieldram_1_w, cyberbal_playfieldram_1, atarigen_playfieldram_size ),
		new MemoryWriteAddress( 0xff2000, 0xff2fff, MWA_BANK3, atarigen_alpharam, atarigen_alpharam_size ),
		new MemoryWriteAddress( 0xff3000, 0xff3fff, MWA_BANK4, atarigen_spriteram, atarigen_spriteram_size ),
		new MemoryWriteAddress( 0xff4000, 0xffffff, MWA_BANK5 ),
		new MemoryWriteAddress( -1 )  /* end of table */
	};
	
	
	
	/*************************************
	 *
	 *	Port definitions
	 *
	 *************************************/
	
	static InputPortPtr input_ports_cyberbal = new InputPortPtr(){ public void handler() { 
		PORT_START();       /* fe0000 */
		PORT_BIT( 0x0001, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_PLAYER4 );
		PORT_BIT( 0x0002, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_PLAYER4 );
		PORT_BIT( 0x0004, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_PLAYER4 );
		PORT_BIT( 0x0008, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_PLAYER4 );
		PORT_BIT( 0x0010, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x0020, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER4 );
		PORT_BIT( 0x00c0, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x0100, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_PLAYER3 );
		PORT_BIT( 0x0200, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_PLAYER3 );
		PORT_BIT( 0x0400, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_PLAYER3 );
		PORT_BIT( 0x0800, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_PLAYER3 );
		PORT_BIT( 0x1000, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x2000, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER3 );
		PORT_BIT( 0x4000, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_SERVICE( 0x8000, IP_ACTIVE_LOW );
	
		PORT_START();       /* fe1000 */
		PORT_BIT( 0x0001, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_PLAYER2 );
		PORT_BIT( 0x0002, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_PLAYER2 );
		PORT_BIT( 0x0004, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_PLAYER2 );
		PORT_BIT( 0x0008, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_PLAYER2 );
		PORT_BIT( 0x0010, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x0020, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2 );
		PORT_BIT( 0x00c0, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x0100, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_PLAYER1 );
		PORT_BIT( 0x0200, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_PLAYER1 );
		PORT_BIT( 0x0400, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_PLAYER1 );
		PORT_BIT( 0x0800, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_PLAYER1 );
		PORT_BIT( 0x1000, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x2000, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1 );
		PORT_BIT( 0x4000, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x8000, IP_ACTIVE_LOW, IPT_VBLANK );
	
		PORT_START(); 		/* fake port for screen switching */
		PORT_BITX(  0x0001, IP_ACTIVE_HIGH, IPT_BUTTON2, "Select Left Screen", KEYCODE_9, IP_JOY_NONE );
		PORT_BITX(  0x0002, IP_ACTIVE_HIGH, IPT_BUTTON2, "Select Right Screen", KEYCODE_0, IP_JOY_NONE );
		PORT_BIT( 0xfffc, IP_ACTIVE_LOW, IPT_UNUSED );
	
		PORT_START(); 		/* audio board port */
		PORT_BIT( 0x01, IP_ACTIVE_HIGH, IPT_COIN2 );
		PORT_BIT( 0x02, IP_ACTIVE_HIGH, IPT_COIN1 );
		PORT_BIT( 0x04, IP_ACTIVE_HIGH, IPT_COIN4 );
		PORT_BIT( 0x08, IP_ACTIVE_HIGH, IPT_COIN3 );
		PORT_BIT( 0x10, IP_ACTIVE_HIGH, IPT_UNUSED );
		PORT_BIT( 0x20, IP_ACTIVE_HIGH, IPT_UNUSED );/* output buffer full */
		PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_UNUSED );	/* input buffer full */
		PORT_BIT( 0x80, IP_ACTIVE_HIGH, IPT_UNUSED );/* self test */
	INPUT_PORTS_END(); }}; 
	
	
	static InputPortPtr input_ports_cyberb2p = new InputPortPtr(){ public void handler() { 
		PORT_START();       /* fc0000 */
		PORT_BIT( 0x0001, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_PLAYER1 );
		PORT_BIT( 0x0002, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_PLAYER1 );
		PORT_BIT( 0x0004, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_PLAYER1 );
		PORT_BIT( 0x0008, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_PLAYER1 );
		PORT_BIT( 0x0010, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x0020, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1 );
		PORT_BIT( 0xffc0, IP_ACTIVE_LOW, IPT_UNUSED );
	
		PORT_START();       /* fc2000 */
		PORT_BIT( 0x0001, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_PLAYER2 );
		PORT_BIT( 0x0002, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_PLAYER2 );
		PORT_BIT( 0x0004, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_PLAYER2 );
		PORT_BIT( 0x0008, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_PLAYER2 );
		PORT_BIT( 0x0010, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x0020, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2 );
		PORT_BIT( 0xffc0, IP_ACTIVE_LOW, IPT_UNUSED );
	
		PORT_START(); 		/* fc4000 */
		PORT_BIT( 0x1fff, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x2000, IP_ACTIVE_LOW, IPT_UNUSED );
		PORT_BIT( 0x4000, IP_ACTIVE_HIGH, IPT_VBLANK );
		PORT_SERVICE( 0x8000, IP_ACTIVE_LOW );
	
		JSA_II_PORT();		/* audio board port */
	INPUT_PORTS_END(); }}; 
	
	
	
	/*************************************
	 *
	 *	Graphics definitions
	 *
	 *************************************/
	
	static GfxLayout pflayout = new GfxLayout
	(
		16,8,	/* 8*8 chars */
		8192,	/* 8192 chars */
		4,		/* 4 bits per pixel */
		new int[] { 0, 1, 2, 3 },
		new int[] { 0,0, 4,4, 8,8, 12,12, 16,16, 20,20, 24,24, 28,28 },
		new int[] { 0*8, 4*8, 8*8, 12*8, 16*8, 20*8, 24*8, 28*8 },
		32*8	/* every char takes 32 consecutive bytes */
	);
	
	static GfxLayout anlayout = new GfxLayout
	(
		16,8,	/* 8*8 chars */
		4096,	/* 4096 chars */
		4,		/* 4 bits per pixel */
		new int[] { 0, 1, 2, 3 },
		new int[] { 0,0, 4,4, 8,8, 12,12, 16,16, 20,20, 24,24, 28,28 },
		new int[] { 0*8, 4*8, 8*8, 12*8, 16*8, 20*8, 24*8, 28*8 },
		32*8	/* every char takes 32 consecutive bytes */
	);
	
	static GfxLayout pflayout_interleaved = new GfxLayout
	(
		16,8,	/* 8*8 chars */
		8192,	/* 8192 chars */
		4,		/* 4 bits per pixel */
		new int[] { 0, 1, 2, 3 },
		new int[] { 0x20000*8+0,0x20000*8+0, 0x20000*8+4,0x20000*8+4, 0,0, 4,4, 0x20000*8+8,0x20000*8+8, 0x20000*8+12,0x20000*8+12, 8,8, 12,12 },
		new int[] { 0*8, 2*8, 4*8, 6*8, 8*8, 10*8, 12*8, 14*8 },
		16*8	/* every char takes 16 consecutive bytes */
	);
	
	static GfxLayout anlayout_interleaved = new GfxLayout
	(
		16,8,	/* 8*8 chars */
		4096,	/* 4096 chars */
		4,		/* 4 bits per pixel */
		new int[] { 0, 1, 2, 3 },
		new int[] { 0x10000*8+0,0x10000*8+0, 0x10000*8+4,0x10000*8+4, 0,0, 4,4, 0x10000*8+8,0x10000*8+8, 0x10000*8+12,0x10000*8+12, 8,8, 12,12 },
		new int[] { 0*8, 2*8, 4*8, 6*8, 8*8, 10*8, 12*8, 14*8 },
		16*8	/* every char takes 16 consecutive bytes */
	);
	
	static GfxLayout molayout = new GfxLayout
	(
		16,8,	/* 8*8 chars */
		20480,	/* 20480 chars */
		4,		/* 4 bits per pixel */
		new int[] { 0, 1, 2, 3 },
		new int[] { 0xf0000*8+0, 0xf0000*8+4, 0xa0000*8+0, 0xa0000*8+4, 0x50000*8+0, 0x50000*8+4, 0, 4,
		  0xf0000*8+8, 0xf0000*8+12, 0xa0000*8+8, 0xa0000*8+12, 0x50000*8+8, 0x50000*8+12, 8, 12 },
		new int[] { 0*8, 2*8, 4*8, 6*8, 8*8, 10*8, 12*8, 14*8 },
		16*8	/* every char takes 16 consecutive bytes */
	);
	
	static GfxDecodeInfo gfxdecodeinfo[] =
	{
		new GfxDecodeInfo( REGION_GFX2, 0, pflayout,     0, 128 ),
		new GfxDecodeInfo( REGION_GFX1, 0, molayout, 0x600, 16 ),
		new GfxDecodeInfo( REGION_GFX3, 0, anlayout, 0x780, 8 ),
		new GfxDecodeInfo( -1 ) /* end of array */
	};
	
	static GfxDecodeInfo gfxdecodeinfo_interleaved[] =
	{
		new GfxDecodeInfo( REGION_GFX2, 0, pflayout_interleaved,     0, 128 ),
		new GfxDecodeInfo( REGION_GFX1, 0, molayout,             0x600, 16 ),
		new GfxDecodeInfo( REGION_GFX3, 0, anlayout_interleaved, 0x780, 8 ),
		new GfxDecodeInfo( -1 ) /* end of array */
	};
	
	
	
	/*************************************
	 *
	 *	Sound definitions
	 *
	 *************************************/
	
	static YM2151interface ym2151_interface = new YM2151interface
	(
		1,			/* 1 chip */
		ATARI_CLOCK_14MHz/4,
/*TODO*///	#if USE_MONO_SOUND
		new int[] { YM3012_VOL(30,MIXER_PAN_CENTER,30,MIXER_PAN_CENTER) },
/*TODO*///	#else
/*TODO*///		new WriteYmHandlerPtr[] { YM3012_VOL(60,MIXER_PAN_LEFT,60,MIXER_PAN_RIGHT) },
/*TODO*///	#endif
		new WriteYmHandlerPtr[] { atarigen_ym2151_irq_gen }
	);
	
/*TODO*///	#ifdef EMULATE_SOUND_68000
/*TODO*///	static DACinterface dac_interface = new DACinterface
/*TODO*///	(
/*TODO*///		2,
/*TODO*///		new int[] { MIXER(100,MIXER_PAN_LEFT), MIXER(100,MIXER_PAN_RIGHT) }
/*TODO*///	);
/*TODO*///	#endif
	
	static CustomSound_interface samples_interface = new CustomSound_interface
	(
		samples_start,
		samples_stop,
		null
	);
	
	
	/*************************************
	 *
	 *	Machine driver
	 *
	 *************************************/
	
	static MachineDriver machine_driver_cyberbal = new MachineDriver
	(
		/* basic machine hardware */
		new MachineCPU[] {
			new MachineCPU(
				CPU_M68000,		/* verified */
				ATARI_CLOCK_14MHz/2,
				main_readmem,main_writemem,null,null,
				ignore_interrupt,1
			),
			new MachineCPU(
				CPU_M6502,
				ATARI_CLOCK_14MHz/8,
				sound_readmem,sound_writemem,null,null,
				null,0,
				atarigen_6502_irq_gen, (int)(1000000000.0/(ATARI_CLOCK_14MHz/4/4/16/16/14))
			),
			new MachineCPU(
				CPU_M68000,		/* verified */
				ATARI_CLOCK_14MHz/2,
				extra_readmem,extra_writemem,null,null,
				atarigen_video_int_gen,1
			)
/*TODO*///	#ifdef EMULATE_SOUND_68000
/*TODO*///			,new MachineCPU(
/*TODO*///				CPU_M68000,		/* verified */
/*TODO*///				ATARI_CLOCK_14MHz/2,
/*TODO*///				sound_68k_readmem,sound_68k_writemem,null,null,
/*TODO*///				null,null,
/*TODO*///				sound_68k_irq_gen,10000
/*TODO*///			)
/*TODO*///	#endif
		},
		60, DEFAULT_REAL_60HZ_VBLANK_DURATION,	/* frames per second, vblank duration */
		10,
		init_machine,
	
		/* video hardware */
		42*16, 30*8, new rectangle( 0*16, 42*16-1, 0*8, 30*8-1 ),
		gfxdecodeinfo_interleaved,
		2048, 2048,
		null,
	
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK |
				VIDEO_PIXEL_ASPECT_RATIO_1_2,
		null,
		cyberbal_vh_start,
		cyberbal_vh_stop,
		cyberbal_vh_screenrefresh,
	
		/* sound hardware */
		SOUND_SUPPORTS_STEREO,0,0,0,
		new MachineSound[] {
			new MachineSound(
				SOUND_YM2151,
				ym2151_interface
			),
/*TODO*///	#ifdef EMULATE_SOUND_68000
/*TODO*///			new MachineSound(
/*TODO*///				SOUND_DAC,
/*TODO*///				dac_interface
/*TODO*///			)
/*TODO*///	#else
			new MachineSound(
				SOUND_CUSTOM,
				samples_interface
			)
/*TODO*///	#endif
		},
	
		atarigen_nvram_handler
	);
	
	
	static MachineDriver machine_driver_cyberb2p = new MachineDriver
	(
		/* basic machine hardware */
		new MachineCPU[] {
			new MachineCPU(
				CPU_M68000,		/* verified */
				ATARI_CLOCK_14MHz/2,
				cyberb2p_readmem,cyberb2p_writemem,null,null,
				atarigen_video_int_gen,1
			),
			JSA_II_CPU
		},
		60, DEFAULT_REAL_60HZ_VBLANK_DURATION,	/* frames per second, vblank duration */
		1,
		cyberb2p_init_machine,
	
		/* video hardware */
		42*16, 30*8, new rectangle( 0*16, 42*16-1, 0*8, 30*8-1 ),
		gfxdecodeinfo,
		2048, 2048,
		null,
	
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK |
				VIDEO_PIXEL_ASPECT_RATIO_1_2,
		null,
		cyberbal_vh_start,
		cyberbal_vh_stop,
		cyberbal_vh_screenrefresh,
	
		/* sound hardware */
		//JSA_II_MONO(REGION_SOUND1),
                0,0,0,0,
                new MachineSound[] {
			new MachineSound(
				SOUND_YM2151,
				atarijsa_ym2151_interface_mono
			),
                        new MachineSound(
				SOUND_OKIM6295,
				atarijsa_okim6295_interface_REGION_SOUND1
			)
		},
	
		atarigen_nvram_handler
	);
	
	
	
	/*************************************
	 *
	 *	ROM definition(s)
	 *
	 *************************************/
	
	static RomLoadPtr rom_cyberbal = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x40000, REGION_CPU1 );/* 4*64k for 68000 code */
		ROM_LOAD_EVEN( "4123.1m", 0x00000, 0x10000, 0xfb872740 );
		ROM_LOAD_ODD ( "4124.1k", 0x00000, 0x10000, 0x87babad9 );
	
		ROM_REGION( 0x14000, REGION_CPU2 );/* 64k for 6502 code */
		ROM_LOAD( "2131-snd.2f",  0x10000, 0x4000, 0xbd7e3d84 );
		ROM_CONTINUE(             0x04000, 0xc000 );
	
		ROM_REGION( 0x40000, REGION_CPU3 );/* 4*64k for 68000 code */
		ROM_LOAD_EVEN( "2127.3c", 0x00000, 0x10000, 0x3e5feb1f );
		ROM_LOAD_ODD ( "2128.1b", 0x00000, 0x10000, 0x4e642cc3 );
		ROM_LOAD_EVEN( "2129.1c", 0x20000, 0x10000, 0xdb11d2f0 );
		ROM_LOAD_ODD ( "2130.3b", 0x20000, 0x10000, 0xfd86b8aa );
	
		ROM_REGION( 0x40000, REGION_CPU4 );/* 256k for 68000 sound code */
		ROM_LOAD_EVEN( "1132-snd.5c",  0x00000, 0x10000, 0xca5ce8d8 );
		ROM_LOAD_ODD ( "1133-snd.7c",  0x00000, 0x10000, 0xffeb8746 );
		ROM_LOAD_EVEN( "1134-snd.5a",  0x20000, 0x10000, 0xbcbd4c00 );
		ROM_LOAD_ODD ( "1135-snd.7a",  0x20000, 0x10000, 0xd520f560 );
	
		ROM_REGION( 0x140000, REGION_GFX1 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1150.15a",  0x000000, 0x10000, 0xe770eb3e );/* MO */
		ROM_LOAD( "1154.16a",  0x010000, 0x10000, 0x40db00da );/* MO */
		ROM_LOAD( "2158.17a",  0x020000, 0x10000, 0x52bb08fb );/* MO */
		ROM_LOAD( "1162.19a",  0x030000, 0x10000, 0x0a11d877 );/* MO */
	
		ROM_LOAD( "1151.11a",  0x050000, 0x10000, 0x6f53c7c1 );/* MO */
		ROM_LOAD( "1155.12a",  0x060000, 0x10000, 0x5de609e5 );/* MO */
		ROM_LOAD( "2159.13a",  0x070000, 0x10000, 0xe6f95010 );/* MO */
		ROM_LOAD( "1163.14a",  0x080000, 0x10000, 0x47f56ced );/* MO */
	
		ROM_LOAD( "1152.15c",  0x0a0000, 0x10000, 0xc8f1f7ff );/* MO */
		ROM_LOAD( "1156.16c",  0x0b0000, 0x10000, 0x6bf0bf98 );/* MO */
		ROM_LOAD( "2160.17c",  0x0c0000, 0x10000, 0xc3168603 );/* MO */
		ROM_LOAD( "1164.19c",  0x0d0000, 0x10000, 0x7ff29d09 );/* MO */
	
		ROM_LOAD( "1153.11c",  0x0f0000, 0x10000, 0x99629412 );/* MO */
		ROM_LOAD( "1157.12c",  0x100000, 0x10000, 0xaa198cb7 );/* MO */
		ROM_LOAD( "2161.13c",  0x110000, 0x10000, 0x6cf79a67 );/* MO */
		ROM_LOAD( "1165.14c",  0x120000, 0x10000, 0x40bdf767 );/* MO */
	
		ROM_REGION( 0x040000, REGION_GFX2 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1146.9l",   0x000000, 0x10000, 0xa64b4da8 );/* playfield */
		ROM_LOAD( "1147.8l",   0x010000, 0x10000, 0xca91ec1b );/* playfield */
		ROM_LOAD( "1148.11l",  0x020000, 0x10000, 0xee29d1d1 );/* playfield */
		ROM_LOAD( "1149.10l",  0x030000, 0x10000, 0x882649f8 );/* playfield */
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1166.14n",  0x000000, 0x10000, 0x0ca1e3b3 );/* alphanumerics */
		ROM_LOAD( "1167.16n",  0x010000, 0x10000, 0x882f4e1c );/* alphanumerics */
	ROM_END(); }}; 
	
	
	static RomLoadPtr rom_cyberba2 = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x40000, REGION_CPU1 );/* 4*64k for 68000 code */
		ROM_LOAD_EVEN( "2123.1m", 0x00000, 0x10000, 0x502676e8 );
		ROM_LOAD_ODD ( "2124.1k", 0x00000, 0x10000, 0x30f55915 );
	
		ROM_REGION( 0x14000, REGION_CPU2 );/* 64k for 6502 code */
		ROM_LOAD( "2131-snd.2f",  0x10000, 0x4000, 0xbd7e3d84 );
		ROM_CONTINUE(             0x04000, 0xc000 );
	
		ROM_REGION( 0x40000, REGION_CPU3 );/* 4*64k for 68000 code */
		ROM_LOAD_EVEN( "2127.3c", 0x00000, 0x10000, 0x3e5feb1f );
		ROM_LOAD_ODD ( "2128.1b", 0x00000, 0x10000, 0x4e642cc3 );
		ROM_LOAD_EVEN( "2129.1c", 0x20000, 0x10000, 0xdb11d2f0 );
		ROM_LOAD_ODD ( "2130.3b", 0x20000, 0x10000, 0xfd86b8aa );
	
		ROM_REGION( 0x40000, REGION_CPU4 );/* 256k for 68000 sound code */
		ROM_LOAD_EVEN( "1132-snd.5c",  0x00000, 0x10000, 0xca5ce8d8 );
		ROM_LOAD_ODD ( "1133-snd.7c",  0x00000, 0x10000, 0xffeb8746 );
		ROM_LOAD_EVEN( "1134-snd.5a",  0x20000, 0x10000, 0xbcbd4c00 );
		ROM_LOAD_ODD ( "1135-snd.7a",  0x20000, 0x10000, 0xd520f560 );
	
		ROM_REGION( 0x140000, REGION_GFX1 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1150.15a",  0x000000, 0x10000, 0xe770eb3e );/* MO */
		ROM_LOAD( "1154.16a",  0x010000, 0x10000, 0x40db00da );/* MO */
		ROM_LOAD( "2158.17a",  0x020000, 0x10000, 0x52bb08fb );/* MO */
		ROM_LOAD( "1162.19a",  0x030000, 0x10000, 0x0a11d877 );/* MO */
	
		ROM_LOAD( "1151.11a",  0x050000, 0x10000, 0x6f53c7c1 );/* MO */
		ROM_LOAD( "1155.12a",  0x060000, 0x10000, 0x5de609e5 );/* MO */
		ROM_LOAD( "2159.13a",  0x070000, 0x10000, 0xe6f95010 );/* MO */
		ROM_LOAD( "1163.14a",  0x080000, 0x10000, 0x47f56ced );/* MO */
	
		ROM_LOAD( "1152.15c",  0x0a0000, 0x10000, 0xc8f1f7ff );/* MO */
		ROM_LOAD( "1156.16c",  0x0b0000, 0x10000, 0x6bf0bf98 );/* MO */
		ROM_LOAD( "2160.17c",  0x0c0000, 0x10000, 0xc3168603 );/* MO */
		ROM_LOAD( "1164.19c",  0x0d0000, 0x10000, 0x7ff29d09 );/* MO */
	
		ROM_LOAD( "1153.11c",  0x0f0000, 0x10000, 0x99629412 );/* MO */
		ROM_LOAD( "1157.12c",  0x100000, 0x10000, 0xaa198cb7 );/* MO */
		ROM_LOAD( "2161.13c",  0x110000, 0x10000, 0x6cf79a67 );/* MO */
		ROM_LOAD( "1165.14c",  0x120000, 0x10000, 0x40bdf767 );/* MO */
	
		ROM_REGION( 0x040000, REGION_GFX2 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1146.9l",   0x000000, 0x10000, 0xa64b4da8 );/* playfield */
		ROM_LOAD( "1147.8l",   0x010000, 0x10000, 0xca91ec1b );/* playfield */
		ROM_LOAD( "1148.11l",  0x020000, 0x10000, 0xee29d1d1 );/* playfield */
		ROM_LOAD( "1149.10l",  0x030000, 0x10000, 0x882649f8 );/* playfield */
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1166.14n",  0x000000, 0x10000, 0x0ca1e3b3 );/* alphanumerics */
		ROM_LOAD( "1167.16n",  0x010000, 0x10000, 0x882f4e1c );/* alphanumerics */
	ROM_END(); }}; 
	
	
	static RomLoadPtr rom_cyberbt = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x40000, REGION_CPU1 );/* 4*64k for 68000 code */
		ROM_LOAD_EVEN( "cyb1007.bin", 0x00000, 0x10000, 0xd434b2d7 );
		ROM_LOAD_ODD ( "cyb1008.bin", 0x00000, 0x10000, 0x7d6c4163 );
		ROM_LOAD_EVEN( "cyb1009.bin", 0x20000, 0x10000, 0x3933e089 );
		ROM_LOAD_ODD ( "cyb1010.bin", 0x20000, 0x10000, 0xe7a7cae8 );
	
		ROM_REGION( 0x14000, REGION_CPU2 );/* 64k for 6502 code */
		ROM_LOAD( "cyb1029.bin",  0x10000, 0x4000, 0xafee87e1 );
		ROM_CONTINUE(             0x04000, 0xc000 );
	
		ROM_REGION( 0x40000, REGION_CPU3 );
		ROM_LOAD_EVEN( "cyb1011.bin", 0x00000, 0x10000, 0x22d3e09c );
		ROM_LOAD_ODD ( "cyb1012.bin", 0x00000, 0x10000, 0xa8eeed8c );
		ROM_LOAD_EVEN( "cyb1013.bin", 0x20000, 0x10000, 0x11d287c9 );
		ROM_LOAD_ODD ( "cyb1014.bin", 0x20000, 0x10000, 0xbe15db42 );
	
		ROM_REGION( 0x40000, REGION_CPU4 );/* 256k for 68000 sound code */
		ROM_LOAD_EVEN( "1132-snd.5c",  0x00000, 0x10000, 0xca5ce8d8 );
		ROM_LOAD_ODD ( "1133-snd.7c",  0x00000, 0x10000, 0xffeb8746 );
		ROM_LOAD_EVEN( "1134-snd.5a",  0x20000, 0x10000, 0xbcbd4c00 );
		ROM_LOAD_ODD ( "1135-snd.7a",  0x20000, 0x10000, 0xd520f560 );
	
		ROM_REGION( 0x140000, REGION_GFX1 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1001.bin",  0x000000, 0x20000, 0x586ba107 );/* MO */
		ROM_LOAD( "1005.bin",  0x020000, 0x20000, 0xa53e6248 );/* MO */
		ROM_LOAD( "1032.bin",  0x040000, 0x10000, 0x131f52a0 );/* MO */
	
		ROM_LOAD( "1002.bin",  0x050000, 0x20000, 0x0f71f86c );/* MO */
		ROM_LOAD( "1006.bin",  0x070000, 0x20000, 0xdf0ab373 );/* MO */
		ROM_LOAD( "1033.bin",  0x090000, 0x10000, 0xb6270943 );/* MO */
	
		ROM_LOAD( "1003.bin",  0x0a0000, 0x20000, 0x1cf373a2 );/* MO */
		ROM_LOAD( "1007.bin",  0x0c0000, 0x20000, 0xf2ffab24 );/* MO */
		ROM_LOAD( "1034.bin",  0x0e0000, 0x10000, 0x6514f0bd );/* MO */
	
		ROM_LOAD( "1004.bin",  0x0f0000, 0x20000, 0x537f6de3 );/* MO */
		ROM_LOAD( "1008.bin",  0x110000, 0x20000, 0x78525bbb );/* MO */
		ROM_LOAD( "1035.bin",  0x130000, 0x10000, 0x1be3e5c8 );/* MO */
	
		ROM_REGION( 0x040000, REGION_GFX2 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "cyb1015.bin",  0x000000, 0x10000, 0xdbbad153 );/* playfield */
		ROM_LOAD( "cyb1016.bin",  0x010000, 0x10000, 0x76e0d008 );/* playfield */
		ROM_LOAD( "cyb1017.bin",  0x020000, 0x10000, 0xddca9ca2 );/* playfield */
		ROM_LOAD( "cyb1018.bin",  0x030000, 0x10000, 0xaa495b6f );/* playfield */
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "cyb1019.bin",  0x000000, 0x10000, 0x833b4768 );/* alphanumerics */
		ROM_LOAD( "cyb1020.bin",  0x010000, 0x10000, 0x4976cffd );/* alphanumerics */
	ROM_END(); }}; 
	
	
	static RomLoadPtr rom_cyberb2p = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x80000, REGION_CPU1 );/* 8*64k for 68000 code */
		ROM_LOAD_EVEN( "3019.bin", 0x00000, 0x10000, 0x029f8cb6 );
		ROM_LOAD_ODD ( "3020.bin", 0x00000, 0x10000, 0x1871b344 );
		ROM_LOAD_EVEN( "3021.bin", 0x20000, 0x10000, 0xfd7ebead );
		ROM_LOAD_ODD ( "3022.bin", 0x20000, 0x10000, 0x173ccad4 );
		ROM_LOAD_EVEN( "2023.bin", 0x40000, 0x10000, 0xe541b08f );
		ROM_LOAD_ODD ( "2024.bin", 0x40000, 0x10000, 0x5a77ee95 );
		ROM_LOAD_EVEN( "1025.bin", 0x60000, 0x10000, 0x95ff68c6 );
		ROM_LOAD_ODD ( "1026.bin", 0x60000, 0x10000, 0xf61c4898 );
	
		ROM_REGION( 0x14000, REGION_CPU2 );/* 64k for 6502 code */
		ROM_LOAD( "1042.bin",  0x10000, 0x4000, 0xe63cf125 );
		ROM_CONTINUE(          0x04000, 0xc000 );
	
		ROM_REGION( 0x140000, REGION_GFX1 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1001.bin",  0x000000, 0x20000, 0x586ba107 );/* MO */
		ROM_LOAD( "1005.bin",  0x020000, 0x20000, 0xa53e6248 );/* MO */
		ROM_LOAD( "1032.bin",  0x040000, 0x10000, 0x131f52a0 );/* MO */
	
		ROM_LOAD( "1002.bin",  0x050000, 0x20000, 0x0f71f86c );/* MO */
		ROM_LOAD( "1006.bin",  0x070000, 0x20000, 0xdf0ab373 );/* MO */
		ROM_LOAD( "1033.bin",  0x090000, 0x10000, 0xb6270943 );/* MO */
	
		ROM_LOAD( "1003.bin",  0x0a0000, 0x20000, 0x1cf373a2 );/* MO */
		ROM_LOAD( "1007.bin",  0x0c0000, 0x20000, 0xf2ffab24 );/* MO */
		ROM_LOAD( "1034.bin",  0x0e0000, 0x10000, 0x6514f0bd );/* MO */
	
		ROM_LOAD( "1004.bin",  0x0f0000, 0x20000, 0x537f6de3 );/* MO */
		ROM_LOAD( "1008.bin",  0x110000, 0x20000, 0x78525bbb );/* MO */
		ROM_LOAD( "1035.bin",  0x130000, 0x10000, 0x1be3e5c8 );/* MO */
	
		ROM_REGION( 0x040000, REGION_GFX2 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1036.bin",  0x000000, 0x10000, 0xcdf6e3d6 );/* playfield */
		ROM_LOAD( "1037.bin",  0x010000, 0x10000, 0xec2fef3e );/* playfield */
		ROM_LOAD( "1038.bin",  0x020000, 0x10000, 0xe866848f );/* playfield */
		ROM_LOAD( "1039.bin",  0x030000, 0x10000, 0x9b9a393c );/* playfield */
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );
		ROM_LOAD( "1040.bin",  0x000000, 0x10000, 0xa4c116f9 );/* alphanumerics */
		ROM_LOAD( "1041.bin",  0x010000, 0x10000, 0xe25d7847 );/* alphanumerics */
	
		ROM_REGION( 0x40000, REGION_SOUND1 );/* 256k for ADPCM samples */
		ROM_LOAD( "1049.bin",  0x00000, 0x10000, 0x94f24575 );
		ROM_LOAD( "1050.bin",  0x10000, 0x10000, 0x87208e1e );
		ROM_LOAD( "1051.bin",  0x20000, 0x10000, 0xf82558b9 );
		ROM_LOAD( "1052.bin",  0x30000, 0x10000, 0xd96437ad );
	ROM_END(); }}; 
	
	
	
	/*************************************
	 *
	 *	Machine initialization
	 *
	 *************************************/
	
	static int default_eeprom[] =
	{
		0x0001,0x01FF,0x0F00,0x011A,0x014A,0x0100,0x01A1,0x0200,
		0x010E,0x01AF,0x0300,0x01FF,0x0114,0x0144,0x01FF,0x0F00,
		0x011A,0x014A,0x0100,0x01A1,0x0200,0x010E,0x01AF,0x0300,
		0x01FF,0x0114,0x0144,0x01FF,0x0E00,0x01FF,0x0E00,0x01FF,
		0x0E00,0x01FF,0x0E00,0x01FF,0x0E00,0x01FF,0x0E00,0x01FF,
		0x0E00,0x01A8,0x0131,0x010B,0x0100,0x014C,0x0A00,0x01FF,
		0x0E00,0x01FF,0x0E00,0x01FF,0x0E00,0xB5FF,0x0E00,0x01FF,
		0x0E00,0x01FF,0x0E00,0x01FF,0x0E00,0x01FF,0x0E00,0x01FF,
		0x0E00,0x01FF,0x0E00,0x0000
	};
	
	public static InitDriverPtr init_cyberbal = new InitDriverPtr() { public void handler() 
	{
		atarigen_eeprom_default = new UShortArray(default_eeprom);
		atarigen_slapstic_init(0, 0x018000, 0);
	
		/* make sure the banks are pointing to the correct location */
		cpu_setbank(1, cyberbal_playfieldram_2);
		cpu_setbank(3, cyberbal_playfieldram_1);
	
		/* display messages */
	/*	atarigen_show_slapstic_message(); -- no slapstic */
		atarigen_show_sound_message();
	
		/* speed up the 6502 */
		atarigen_init_6502_speedup(1, 0x4191, 0x41A9);
	
		atarigen_playfieldram = cyberbal_playfieldram_1;
	} };
	
	
	public static InitDriverPtr init_cyberbt = new InitDriverPtr() { public void handler() 
	{
		atarigen_eeprom_default = new UShortArray(default_eeprom);
		atarigen_slapstic_init(0, 0x018000, 116);
	
		/* make sure the banks are pointing to the correct location */
		cpu_setbank(1, cyberbal_playfieldram_2);
		cpu_setbank(3, cyberbal_playfieldram_1);
	
		/* display messages */
	/*	atarigen_show_slapstic_message(); -- no known slapstic problems - yet! */
		atarigen_show_sound_message();
	
		/* speed up the 6502 */
		atarigen_init_6502_speedup(1, 0x4191, 0x41A9);
	
		atarigen_playfieldram = cyberbal_playfieldram_1;
	} };
	
	
	public static InitDriverPtr init_cyberb2p = new InitDriverPtr() { public void handler() 
	{
		atarigen_eeprom_default = new UShortArray(default_eeprom);
	
		/* initialize the JSA audio board */
		atarijsa_init(1, 3, 2, 0x8000);
	
		/* display messages */
		atarigen_show_sound_message();
	
		/* speed up the 6502 */
		atarigen_init_6502_speedup(1, 0x4159, 0x4171);
	
		atarigen_playfieldram = cyberbal_playfieldram_1;
	} };
	
	
	
	/*************************************
	 *
	 *	Game driver(s)
	 *
	 *************************************/
	
	public static GameDriver driver_cyberbal	   = new GameDriver("1988"	,"cyberbal"	,"cyberbal.java"	,rom_cyberbal,null	,machine_driver_cyberbal	,input_ports_cyberbal	,init_cyberbal	,ROT0_16BIT	,	"Atari Games", "Cyberball (Version 4)" );
	public static GameDriver driver_cyberba2	   = new GameDriver("1988"	,"cyberba2"	,"cyberbal.java"	,rom_cyberba2,driver_cyberbal	,machine_driver_cyberbal	,input_ports_cyberbal	,init_cyberbal	,ROT0_16BIT	,	"Atari Games", "Cyberball (Version 2)" );
	public static GameDriver driver_cyberbt	   = new GameDriver("1989"	,"cyberbt"	,"cyberbal.java"	,rom_cyberbt,driver_cyberbal	,machine_driver_cyberbal	,input_ports_cyberbal	,init_cyberbt	,ROT0_16BIT	,	"Atari Games", "Tournament Cyberball 2072" );
	public static GameDriver driver_cyberb2p	   = new GameDriver("1989"	,"cyberb2p"	,"cyberbal.java"	,rom_cyberb2p,driver_cyberbal	,machine_driver_cyberb2p	,input_ports_cyberb2p	,init_cyberb2p	,ROT0_16BIT	,	"Atari Games", "Cyberball 2072 (2 player)" );
}
