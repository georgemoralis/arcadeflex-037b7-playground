/***************************************************************************

								-= Seta Games =-

					driver by	Luca Elia (eliavit@unina.it)


Sound Chip:

	X1-010 					[Seta Custom]
	Unsigned 16 Bit PCM 	[Fixed Per Game Pitch?]
	16 Voices				[There's More Data Written!]

Format:

	8 registers per channel (mapped to the lower bytes of 16 words on the 68K)

	Reg:	Bits:		Meaning:

	0		7654 321-
			---- ---0	Key On / Off

	1		7654 ----	Volume 1(L?)
			---- 3210	Volume 2(R?)

	2					? (high byte?)
	3					? (low  byte?)

	4					Sample Start / 0x1000 			[Start/End in bytes]
	5					0x100 - (Sample End / 0x1000)	[PCM ROM is Max 1MB?]

	6					?
	7					?


Hardcoded Values (for now):

	PCM ROM region:		REGION_SOUND1
	Sample Frequency:	4, 6, 8 KHz

***************************************************************************/
/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.sndhrdw;

import arcadeflex.common.ptrLib;
import arcadeflex.common.ptrLib.ShortPtr;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v056.mame.timer.*;
import static arcadeflex.v056.mame.timerH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static arcadeflex.v037b7.sound.streams.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound.mixerH.*;
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static gr.codebb.arcadeflex.common.libc.cstdio.*;
import static gr.codebb.arcadeflex.old.sound.mixer.*;

public class seta
{
	
	/* Variables and functions that driver has access to */
	public static UBytePtr seta_sound_ram = new UBytePtr();
	
	static int SETA_NUM_CHANNELS = 16;
	
	/* Variables only used here */
	static int firstchannel, frequency;
	static int[][] seta_reg = new int[SETA_NUM_CHANNELS][8];
	
	
	
	
	public static ShStartPtr seta_sh_start = new ShStartPtr() { public int handler(MachineSound msound) 
	{
		int i;
		int[] vol = new int[MIXER_MAX_CHANNELS];
	
		for (i = 0;i < MIXER_MAX_CHANNELS;i++)	vol[i] = 100;
		firstchannel = mixer_allocate_channels(SETA_NUM_CHANNELS,vol);
	
		for (i = 0; i < SETA_NUM_CHANNELS; i++)
		{
			String buf = "";
			buf = sprintf("X1-010 Channel #%d",i);
			mixer_set_name(firstchannel + i,buf);
		}
		return 0;
	} };
	
	public static ShStartPtr seta_sh_start_4KHz = new ShStartPtr() { public int handler(MachineSound msound) 
	{
		frequency = 4000;
		return seta_sh_start.handler(msound);
	} };
	
	public static ShStartPtr seta_sh_start_6KHz = new ShStartPtr() { public int handler(MachineSound msound) 
	{
		frequency = 6000;
		return seta_sh_start.handler(msound);
	} };
	
	public static ShStartPtr seta_sh_start_8KHz = new ShStartPtr() { public int handler(MachineSound msound) 
	{
		frequency = 8000;
		return seta_sh_start.handler(msound);
	} };
	
	
	
	/* Use these for 8 bit CPUs */
	
	
	public static ReadHandlerPtr seta_sound_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int channel	=	offset / 8;
		int reg		=	offset % 8;
	
		if (channel < SETA_NUM_CHANNELS)
		{
			switch (reg)
			{
				case 0:
					return ( mixer_is_sample_playing(firstchannel + channel)!=0 ? 1 : 0 );
				default:
					logerror("PC: %06X - X1-010 channel %X, register %X read!\n",cpu_get_pc(),channel,reg);
					return seta_reg[channel][reg];
			}
		}
	
		return seta_sound_ram.read(offset);
	} };
	
	
	
	
/*TODO*///	#define DUMP_REGS \
/*TODO*///		logerror("X1-010 REGS: ch %X] %02X %02X %02X %02X - %02X %02X %02X %02X\n", \
/*TODO*///								channel, \
/*TODO*///								seta_reg[channel][0],seta_reg[channel][1], \
/*TODO*///								seta_reg[channel][2],seta_reg[channel][3], \
/*TODO*///								seta_reg[channel][4],seta_reg[channel][5], \
/*TODO*///								seta_reg[channel][6],seta_reg[channel][7] );
	
	
	public static WriteHandlerPtr seta_sound_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int channel, reg;
	
		seta_sound_ram.write(offset, data);
	
		if (Machine.sample_rate == 0)		return;
	
		channel	=	offset / 8;
		reg		=	offset % 8;
	
		if (channel >= SETA_NUM_CHANNELS)	return;
	
		seta_reg[channel][reg] = data & 0xff;
	
		switch (reg)
		{
	
			case 0:
	
/*TODO*///				DUMP_REGS
	
				if ((data & 1) != 0)	// key on
				{
					int volume	=	seta_reg[channel][1];
	
					int start	=	seta_reg[channel][4]           * 0x1000;
					int end		=	(0x100 - seta_reg[channel][5]) * 0x1000; // from the end of the rom
	
					int len		=	end - start;
					int maxlen	=	memory_region_length(REGION_SOUND1);
	
					if (!( (start < end) && (end <= maxlen) ))
					{
						logerror("PC: %06X - X1-010 OUT OF RANGE SAMPLE: %06X - %06X, channel %X\n",cpu_get_pc(),start,end,channel);
/*TODO*///						DUMP_REGS
						return;
					}
	
/*TODO*///	#if 1
	/* Print some more debug info */
	logerror("PC: %06X - Play 16 bit sample %06X - %06X, channel %X\n",cpu_get_pc(),start, end, channel);
/*TODO*///	DUMP_REGS
/*TODO*///	#endif
	
					/*
					   Twineagl continuosly writes 1 to reg 0 of the channel, so
					   the sample is restarted every time and never plays to the
					   end. It looks like the previous sample must be explicitly
					   stopped before a new one can be played
					*/
					if (( seta_sound_r.handler(offset) & 1 )!=0)	return;	// play to the end
	
					/* These samples are probaly looped and use the 3rd & 4th register's value */
					if ((data & 2) != 0)	return;
	
					/* left and right speaker's volume can be set indipendently.
					   We use a mean volume for now */
					mixer_set_volume(firstchannel + channel, ((volume & 0xf)+(volume >> 4))*100/(2*0xf)  );
	
					/* I assume the pitch is fixed for a given board. It ranges
					   from 4 to 8 KHz for the games I've seen */
	
/*TODO*///					mixer_play_sample_16(
/*TODO*///						firstchannel + channel,
/*TODO*///						new ShortPtr (memory_region(REGION_SOUND1), start),	// start
/*TODO*///						len,												// len
/*TODO*///						frequency,											// frequency
/*TODO*///						0);													// loop
				}
				else
					mixer_stop_sample(channel + firstchannel);
	
				break;
	
		}
	} };
	
	
	
	
	
	/* Use these for 16 bit CPUs */
	
	public static ReadHandlerPtr seta_sound_word_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return seta_sound_r.handler(offset/2) & 0xff;
	} };
	
	public static WriteHandlerPtr seta_sound_word_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ( (data & 0x00ff0000) == 0 )
			seta_sound_w.handler(offset/2, data & 0xff);
	} };
}
