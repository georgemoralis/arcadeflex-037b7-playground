/***************************************************************************

Cinematronics sound handlers

Special thanks to Neil Bradley, Zonn Moore, and Jeff Mitchell of the
Retrocade Alliance

Update:
6/27/99 Jim Hernandez -- 1st Attempt at Fixing Drone Star Castle sound and
                         pitch adjustments.
6/30/99 MLR added Rip Off, Solar Quest, Armor Attack (no samples yet)

Bugs: Sometimes the death explosion (small explosion) does not trigger.


***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.sndhrdw;

import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;        
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.sound.ay8910.*;
import static arcadeflex.v037b7.sound.ay8910H.*;
import static arcadeflex.v037b7.sound.samples.*;
import static arcadeflex.v037b7.sound.samplesH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.z80fmly.*;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.z80fmlyH.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.common.libc.cstdlib.rand;

public class cinemat
{
	
	
	static int current_shift = 0;
	static int last_shift = 0;
	static int last_shift16= 0;
	static int current_pitch = 0x20000;
	static int last_frame = 0;
	
	static int cinemat_outputs = 0xff;
	
	public abstract static interface cinemat_sound_handler_proc {
            public abstract void handler(int sound_val, int bits_changed);
        }
	
	static cinemat_sound_handler_proc cinemat_sound_handler;
	
	public static ReadHandlerPtr cinemat_output_port_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return cinemat_outputs;
	} };
	
	public static WriteHandlerPtr cinemat_output_port_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (((cinemat_outputs ^ data) & 0x9f) != 0)
		{
			if (cinemat_sound_handler != null)
				cinemat_sound_handler.handler(data & 0x9f, (cinemat_outputs ^ data) & 0x9f);
		}
	
		cinemat_outputs = data;
	} };
	
	
	
	public static void cinemat_set_sound_handler(cinemat_sound_handler_proc sound_handler)
	{
		cinemat_sound_handler = sound_handler;
	
	    current_shift = 0xffff;
	    last_shift = 0xffff;
	    last_shift16 = 0xffff;
	    current_pitch = 0x20000;
	    last_frame = 0;
	
	// Pitch the Drone sound will start off at
	
	}
	
	static void cinemat_shift (int/*UINT8*/ sound_val, int/*UINT8*/ bits_changed, int/*UINT8*/ A1, int/*UINT8*/ CLK)
	{
		// See if we're latching a shift
	
	    if ((bits_changed & CLK)!=0 && (0 == (sound_val & CLK)))
		{
			current_shift <<= 1;
			if ((sound_val & A1) != 0)
                            current_shift |= 1;
		}
	}
	
	
	/***************************************************************************
	
	  Star Castle
	
	***************************************************************************/
	
	static String starcas_sample_names[] =
	{
		"*starcas",
		"lexplode.wav",
		"sexplode.wav",
		"cfire.wav",
		"pfire.wav",
		"drone.wav",
		"shield.wav",
		"star.wav",
		"thrust.wav",
                null	/* end of array */
	};
	
	public static Samplesinterface starcas_samples_interface = new Samplesinterface
	(
		8,	/* 8 channels */
		25,	/* volume */
		starcas_sample_names
	);
	
	public static cinemat_sound_handler_proc starcas_sound_w = new cinemat_sound_handler_proc() {
            @Override
            public void handler(int sound_val, int bits_changed) {
                int target_pitch;
                int shift_diff;

                cinemat_shift (sound_val, bits_changed, 0x80, 0x10);

                    // Now see if it's time to act upon the shifted data

                    if ((bits_changed & 0x01)!=0 && (0 == (sound_val & 0x01)))
                    {
                            // Yep. Falling edge! Find out what has changed.

                            shift_diff = current_shift ^ last_shift;

                            if ((shift_diff & 1)!=0 && (0 == (current_shift & 1)))
                                    sample_start(2, 2, 0);	// Castle fire

                            if ((shift_diff & 2)!=0 && (0 == (current_shift & 2)))
                                    sample_start(5, 5, 0);	// Shield hit

                            if ((shift_diff & 0x04) != 0)
                            {
                                    if ((current_shift & 0x04) != 0)
                                            sample_start(6, 6, 1);	// Star sound
                                    else
                                            sample_stop(6);	// Stop it!
                            }

                            if ((shift_diff & 0x08) != 0)
                            {
                                    if ((current_shift & 0x08) != 0)
                                            sample_stop(7);	// Stop it!
                                    else
                                            sample_start(7, 7, 1);	// Thrust sound
                            }

                            if ((shift_diff & 0x10) != 0)
                            {
                                    if ((current_shift & 0x10) != 0)
                                            sample_stop(4);
                                    else
                                            sample_start(4, 4, 1);	// Drone
                            }

                            // Latch the drone pitch

                    target_pitch = (current_shift & 0x60) >> 3;
                    target_pitch |= ((current_shift & 0x40) >> 5);
                    target_pitch |= ((current_shift & 0x80) >> 7);

                    // target_pitch = (current_shift & 0x60) >> 3;
                    // is the the target drone pitch to rise and stop at.

                    target_pitch = 0x10000 + (target_pitch << 12);

                    // 0x10000 is lowest value the pitch will drop to
                    // Star Castle drone sound

                    if (cpu_getcurrentframe() > last_frame)
                    {
                        if (current_pitch > target_pitch)
                            current_pitch -= 300;
                        if (current_pitch < target_pitch)
                            current_pitch += 200;
                        sample_set_freq(4, current_pitch);
                        last_frame = cpu_getcurrentframe();
                    }

                            last_shift = current_shift;
                    }

                    if ((bits_changed & 0x08)!=0 && (0 == (sound_val & 0x08)))
                            sample_start(3, 3, 0);			// Player fire

                    if ((bits_changed & 0x04)!=0 && (0 == (sound_val & 0x04)))
                            sample_start(1, 1, 0);			// Soft explosion

                    if ((bits_changed & 0x02)!=0 && (0 == (sound_val & 0x02)))
                            sample_start(0, 0, 0);			// Loud explosion

            }
        };
	
	/***************************************************************************
	
	  Warrior
	
	***************************************************************************/
	
	static String warrior_sample_names[] =
	{
		"*warrior",
		"appear.wav",
		"bgmhum1.wav",
		"bgmhum2.wav",
		"fall.wav",
		"killed.wav",
                null	/* end of array */
	};
	
	public static Samplesinterface warrior_samples_interface = new Samplesinterface
	(
		5,	/* 8 channels */
		25,	/* volume */
		warrior_sample_names
	);
	
	public static cinemat_sound_handler_proc warrior_sound_w = new cinemat_sound_handler_proc() {
            @Override
            public void handler(int sound_val, int bits_changed) {
	
		if ((bits_changed & 0x10)!=0 && (0 == (sound_val & 0x10)))
		{
			sample_start(0, 0, 0);			// appear
		}
	
		if ((bits_changed & 0x08)!=0 && (0 == (sound_val & 0x08)))
			sample_start(3, 3, 0);			// fall
	
		if ((bits_changed & 0x04)!=0 && (0 == (sound_val & 0x04)))
			sample_start(4, 4, 0);			// explosion (kill)
	
		if ((bits_changed & 0x02) != 0)
		{
			if ((sound_val & 0x02) == 0)
				sample_start(2, 2, 1);			// hi level
			else
				sample_stop(2);
		}
	
		if ((bits_changed & 0x01) != 0)
		{
			if ((sound_val & 0x01) == 0)
				sample_start(1, 1, 1);			// normal level
			else
				sample_stop(1);
		}
            }
        };
	
	
	/***************************************************************************
	
	  Armor Attack
	
	***************************************************************************/
	
	public static void armora_sound_w(int sound_val, int bits_changed)
	{
		int shift_diff;
	
                cinemat_shift (sound_val, bits_changed, 0x80, 0x10);
	
		// Now see if it's time to act upon the shifted data
	
		if ((bits_changed & 0x01)!=0 && (0 == (sound_val & 0x01)))
		{
			// Yep. Falling edge! Find out what has changed.
	
			shift_diff = current_shift ^ last_shift;
	
			if ((shift_diff & 1)!=0 && (0 == (current_shift & 1)))
				sample_start(0, 0, 0);	// Tank fire
	
			if ((shift_diff & 2)!=0 && (0 == (current_shift & 2)))
				sample_start(1, 1, 0);	// Hi explosion
	
			if ((shift_diff & 4)!=0 && (0 == (current_shift & 4)))
				sample_start(2, 2, 0);	// Jeep fire
	
			if ((shift_diff & 8)!=0 && (0 == (current_shift & 8)))
				sample_start(3, 3, 0);	// Lo explosion
	
	        /* High nibble unknown */
			last_shift = current_shift;
		}
	
	    if ((bits_changed & 0x2) != 0)
	    {
	        if ((sound_val & 0x2) != 0)
	            sample_start(4, 4, 1);	// Tank +
	        else
	            sample_stop(4);
	    }
	    if ((bits_changed & 0x4) != 0)
	    {
	        if ((sound_val & 0x4) != 0)
	            sample_start(5, 5, 1);	// Beep +
	        else
	            sample_stop(5);
	    }
	    if ((bits_changed & 0x8) != 0)
	    {
	        if ((sound_val & 0x8) != 0)
	            sample_start(6, 6, 1);	// Chopper +
	        else
	            sample_stop(6);
	    }
	}
	
	
	/***************************************************************************
	
	  Ripoff
	
	***************************************************************************/
	
	static String ripoff_sample_names[] =
	{
		"*ripoff",
                "efire.wav",
		"eattack.wav",
		"bonuslvl.wav",
		"explosn.wav",
		"shipfire.wav",
		"bg1.wav",
		"bg2.wav",
		"bg3.wav",
		"bg4.wav",
		"bg5.wav",
		"bg6.wav",
		"bg7.wav",
		"bg8.wav",
                null	/* end of array */
	};
	
	public static Samplesinterface ripoff_samples_interface = new Samplesinterface
	(
		8,	/* 8 channels */
		25,	/* volume */
		ripoff_sample_names
	);
        
        static int last_bg_sound;
	
	public static cinemat_sound_handler_proc ripoff_sound_w = new cinemat_sound_handler_proc() {
            @Override
            public void handler(int sound_val, int bits_changed) {
		int shift_diff, current_bg_sound;
	    
	
                cinemat_shift (sound_val, bits_changed, 0x01, 0x02);
	
		// Now see if it's time to act upon the shifted data
	
		if ((bits_changed & 0x04)!=0 && (0 == (sound_val & 0x04)))
		{
			// Yep. Falling edge! Find out what has changed.
	
			shift_diff = current_shift ^ last_shift;
	
	        current_bg_sound = ((current_shift & 0x1) << 2) | (current_shift & 0x2) | ((current_shift & 0x4) >> 2);
	        if (current_bg_sound != last_bg_sound) // use another background sound ?
	        {
	            shift_diff |= 0x08;
	            sample_stop(4);
	            last_bg_sound = current_bg_sound;
	        }
	
			if ((shift_diff & 0x08) != 0)
			{
				if ((current_shift & 0x08) != 0)
					sample_stop(5);
				else
	                sample_start(5, 5+last_bg_sound, 1);	// Background
			}
	
			if ((shift_diff & 0x10)!=0 && (0 == (current_shift & 0x10)))
				sample_start(2, 2, 0);	// Beep
	
			if ((shift_diff & 0x20) != 0)
			{
				if ((current_shift & 0x20) != 0)
					sample_stop(1);	// Stop it!
				else
					sample_start(1, 1, 1);	// Motor
			}
	
			last_shift = current_shift;
		}
	
		if ((bits_changed & 0x08)!=0 && (0 == (sound_val & 0x08)))
			sample_start(4, 4, 0);			// Torpedo
	
		if ((bits_changed & 0x10)!=0 && (0 == (sound_val & 0x10)))
			sample_start(0, 0, 0);			// Laser
	
		if ((bits_changed & 0x80)!=0 && (0 == (sound_val & 0x80)))
			sample_start(3, 3, 0);			// Explosion
	
            }
        };
	
	/***************************************************************************
	
	  Solar Quest
	
	***************************************************************************/
	
	static String solarq_sample_names[] =
	{
		"*solarq",
                "bigexpl.wav",
		"smexpl.wav",
		"lthrust.wav",
		"slaser.wav",
		"pickup.wav",
		"nuke1.wav",
		"nuke2.wav",
		"hypersp.wav",
                "extra.wav",
                "phase.wav",
                "efire.wav",
                null	/* end of array */
	};
	
	public static Samplesinterface solarq_samples_interface = new Samplesinterface
	(
		8,	/* 8 channels */
		25,	/* volume */
		solarq_sample_names
	);
	
	static int target_volume, current_volume;
        
	public static cinemat_sound_handler_proc solarq_sound_w = new cinemat_sound_handler_proc() {
            @Override
            public void handler(int sound_val, int bits_changed) {
		int shift_diff, shift_diff16;
	    
	
                cinemat_shift (sound_val, bits_changed, 0x80, 0x10);
	
		if ((bits_changed & 0x01)!=0 && (0 == (sound_val & 0x01)))
	    {
			shift_diff16 = current_shift ^ last_shift16;
	
			if ((shift_diff16 & 0x1)!=0 && (current_shift & 0x1)!=0)
	        {
	            switch (current_shift & 0xffff)
	            {
	            case 0xceb3:
	                sample_start(7, 7, 0);	// Hyperspace
	                break;
	            case 0x13f3:
	                sample_start(7, 8, 0);	// Extra
	                break;
	            case 0xfdf3:
	                sample_start(7, 9, 0);	// Phase
	                break;
	            case 0x7bf3:
	                sample_start(7, 10, 0);	// Enemy fire
	                break;
	            default:
	                logerror("Unknown sound starting with: %x\n", current_shift & 0xffff);
	                break;
	            }
	        }
	
			last_shift16 = current_shift;
	    }
	
		// Now see if it's time to act upon the shifted data
	
		if ((bits_changed & 0x02)!=0 && (0 == (sound_val & 0x02)))
		{
			// Yep. Falling edge! Find out what has changed.
	
			shift_diff = current_shift ^ last_shift;
	
			if ((shift_diff & 0x01)!=0 && (0 == (current_shift & 0x01)))
				sample_start(0, 0, 0);	// loud expl.
	
			if ((shift_diff & 0x02)!=0 && (0 == (current_shift & 0x02)))
				sample_start(1, 1, 0);	// soft expl.
	
			if ((shift_diff & 0x04) != 0) // thrust
			{
				if ((current_shift & 0x04) != 0)
					target_volume = 0;
				else
	            {
	                target_volume = 255;
	                current_volume = 0;
					sample_start(2, 2, 1);
	            }
	        }
	
	        if (sample_playing(2)!=0 && (last_frame < cpu_getcurrentframe()))
	        {
	            if (current_volume > target_volume)
	                current_volume -= 20;
	            if (current_volume < target_volume)
	                current_volume += 20;
	            if (current_volume > 0)
	                sample_set_volume(2, current_volume);
	            else
	                sample_stop(2);
	            last_frame = cpu_getcurrentframe();
	        }
	
			if ((shift_diff & 0x08)!=0 && (0 == (current_shift & 0x08)))
				sample_start(3, 3, 0);	// Fire
	
			if ((shift_diff & 0x10)!=0 && (0 == (current_shift & 0x10)))
				sample_start(4, 4, 0);	// Capture
	
			if ((shift_diff & 0x20) != 0)
			{
				if ((current_shift & 0x20) != 0)
					sample_start(6, 6, 1);	// Nuke +
				else
					sample_stop(6);
			}
	
			if ((shift_diff & 0x40)!=0 && (0 == (current_shift & 0x40)))
				sample_start(5, 5, 0);	// Photon
	
			last_shift = current_shift;
		}
            }
        };
	
	/***************************************************************************
	
	  Spacewar
	
	***************************************************************************/
	
	static String spacewar_sample_names[] =
	{
		"*spacewar",
		"explode1.wav",
		"fire1.wav",
		"idle.wav",
		"thrust1.wav",
		"thrust2.wav",
		"pop.wav",
		"explode2.wav",
		"fire2.wav",
                null	/* end of array */
	};
	
	public static Samplesinterface spacewar_samples_interface = new Samplesinterface
	(
		8,	/* 8 channels */
		25,	/* volume */
		spacewar_sample_names
	);
	
	public static cinemat_sound_handler_proc spacewar_sound_w = new cinemat_sound_handler_proc() {
            @Override
            public void handler(int sound_val, int bits_changed) {
                // Explosion
	
		if ((bits_changed & 0x01) != 0)
		{
			if ((sound_val & 0x01) != 0)
			{
                            if ((rand() & 1) != 0)
                                sample_start(0, 0, 0);
                            else
                                sample_start(0, 6, 0);
			}
		}
		// Fire sound
	
		if ((sound_val & 0x02)!=0 && (bits_changed & 0x02)!=0)
		{
	            if ((rand() & 1) != 0)
	                sample_start(1, 1, 0);
	            else
	                sample_start(1, 7, 0);
		}
	
		// Player 1 thrust
	
		if ((bits_changed & 0x04) != 0)
		{
			if ((sound_val & 0x04) != 0)
				sample_stop(3);
			else
				sample_start(3, 3, 1);
		}
	
		// Player 2 thrust
	
		if ((bits_changed & 0x08) != 0)
		{
			if ((sound_val & 0x08) != 0)
				sample_stop(4);
			else
				sample_start(4, 4, 1);
		}
	
		// Sound board shutoff (or enable)
	
		if ((bits_changed & 0x10) != 0)
		{
			// This is a toggle bit. If sound is enabled, shut everything off.
	
			if ((sound_val & 0x10) != 0)
			{
	            int i;
	
				for (i = 0; i < 5; i++)
				{
					if (i != 2)
						sample_stop(i);
				}
	
				sample_start(2, 5, 0);	// Pop when board is shut off
			}
			else
				sample_start(2, 2, 1);	// Otherwise play idle sound
		}
            }
        };
        
	
	/***************************************************************************
	
	  Demon
	
	***************************************************************************/
	
	
	/* circular queue with read and write pointers */
	static int QUEUE_ENTRY_COUNT  = 10;
	static int sound_latch_rp = 0;
	static int sound_latch_wp = 0;
	static int[] sound_latch = new int[QUEUE_ENTRY_COUNT];
	
	public static cinemat_sound_handler_proc demon_sound_w = new cinemat_sound_handler_proc() {
            @Override
            public void handler(int sound_val, int bits_changed) {
		int pc = cpu_get_pc();
	
		if (pc == 0x0fbc ||
			pc == 0x1fed ||
			pc == 0x2ff1 ||
			pc == 0x3fd3)
		{
			sound_latch[sound_latch_wp] = ((sound_val & 0x07) << 3);
		}
		if (pc == 0x0fc8 ||
			pc == 0x1ff9 ||
			pc == 0x2ffd ||
			pc == 0x3fdf)
		{
			sound_latch[sound_latch_wp] |= (sound_val & 0x07);
	
			//logerror("Writing Sound Latch %04X = %02X\n", pc, sound_latch[sound_latch_wp]);
	
			sound_latch_wp++;
			if (sound_latch_wp == QUEUE_ENTRY_COUNT)  sound_latch_wp = 0;
		}
            }
        };
	
	public static ReadHandlerPtr demon_sound_r = new ReadHandlerPtr() {
            @Override
            public int handler(int offset) {
                int ret;
	
		if (sound_latch_rp == sound_latch_wp)	return 0x80;	/* no data in queue */
	
		ret = sound_latch[sound_latch_rp];
	
		sound_latch_rp++;
		if (sound_latch_rp == QUEUE_ENTRY_COUNT)  sound_latch_rp = 0;
	
		//logerror("Reading Sound Latch %04X = %02X\n", cpu_get_pc(), ret);
	
		return ret;
            }
        };
	
	
	public static AY8910interface demon_ay8910_interface = new AY8910interface
	(
		3,	/* 3 chips */
		3579545,	/* 3.579545 MHz */
		new int[] { 25, 25, 25 },
		new ReadHandlerPtr[] { demon_sound_r, null, null },
		new ReadHandlerPtr[] { null, null, null },	/* there are sound enable bits in here, but don't know what is what */
		new WriteHandlerPtr[] { null, null, null },
		new WriteHandlerPtr[] { null, null, null }
	);
	
	
	public static IntrPtr ctc_interrupt = new IntrPtr() {
            @Override
            public void handler(int state) {
                cpu_cause_interrupt (1, Z80_VECTOR(0,state) );
            }
        };
        
	public static z80ctc_interface demon_z80ctc_interface = new z80ctc_interface
	(
		1,                   /* 1 chip */
		new int[]{ 0 },               /* clock (filled in from the CPU clock) */
		new int[]{ 0 },               /* timer disables */
		new IntrPtr[] { ctc_interrupt },   /* interrupt handler */
		new WriteHandlerPtr[]{ null },               /* ZC/TO0 callback */
		new WriteHandlerPtr[] { null },               /* ZC/TO1 callback */
		new WriteHandlerPtr[] { null }                /* ZC/TO2 callback */
	);
	
	
	public static MemoryReadAddress demon_sound_readmem[] =
	{
		new MemoryReadAddress( 0x0000, 0x0fff, MRA_ROM ),
		new MemoryReadAddress( 0x3000, 0x33ff, MRA_RAM ),
		new MemoryReadAddress( 0x4001, 0x4001, AY8910_read_port_0_r ),
		new MemoryReadAddress( 0x5001, 0x5001, AY8910_read_port_1_r ),
		new MemoryReadAddress( 0x6001, 0x6001, AY8910_read_port_2_r ),
		new MemoryReadAddress( -1 )  /* end of table */
	};
	
	public static MemoryWriteAddress demon_sound_writemem[] =
	{
		new MemoryWriteAddress( 0x0000, 0x0fff, MWA_ROM ),
		new MemoryWriteAddress( 0x3000, 0x33ff, MWA_RAM ),
		new MemoryWriteAddress( 0x4002, 0x4002, AY8910_write_port_0_w ),
		new MemoryWriteAddress( 0x4003, 0x4003, AY8910_control_port_0_w ),
		new MemoryWriteAddress( 0x5002, 0x5002, AY8910_write_port_1_w ),
		new MemoryWriteAddress( 0x5003, 0x5003, AY8910_control_port_1_w ),
		new MemoryWriteAddress( 0x6002, 0x6002, AY8910_write_port_2_w ),
		new MemoryWriteAddress( 0x6003, 0x6003, AY8910_control_port_2_w ),
		new MemoryWriteAddress( 0x7000, 0x7000, MWA_NOP ),  /* watchdog? */
		new MemoryWriteAddress( -1 )  /* end of table */
	};
	
	public static IOWritePort demon_sound_writeport[] =
	{
		new IOWritePort( 0x00, 0x03, z80ctc_0_w ),
		new IOWritePort( 0x1c, 0x1f, z80ctc_0_w ),
		new IOWritePort( -1 )	/* end of table */
	};
}
