/**********************************************************************************************
 *
 *   Yamaha YMZ280B driver
 *   by Aaron Giles
 *
 **********************************************************************************************/


/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.sound;

import static arcadeflex.common.ptrLib.*;
import static arcadeflex.v037b7.generic.funcPtr.*;
import arcadeflex.v037b7.mame.cpuintrfH.irqcallbacksPtr;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound.mixerH.*;
import static arcadeflex.v037b7.sound.streams.*;
import static arcadeflex.v037b7.sound.ymz280bH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.common.libc.cstdio.*;
import gr.codebb.arcadeflex.old.arcadeflex.libc_old.IntPtr;
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;

public class ymz280b extends snd_interface {
    
    public ymz280b() {
        this.name = "YM-Z280B";
        this.sound_num = SOUND_YMZ280B;        
    }

    @Override
    public int chips_num(MachineSound msound) {
        return ((YMZ280Binterface) msound.sound_interface).num;
    }

    @Override
    public int chips_clock(MachineSound msound) {
        return ((YMZ280Binterface) msound.sound_interface).baseclock;
    }

    @Override
    public int start(MachineSound msound) {
        return YMZ280B_sh_start.handler(msound);
    }

    @Override
    public void stop() {
        YMZ280B_sh_stop.handler();
    }

    @Override
    public void update() {
        //no functionality expected
    }

    @Override
    public void reset() {
        //no functionality expected
    }


	static int MAX_SAMPLE_CHUNK	= 10000;

	static int FRAC_BITS            = 14;
	static int FRAC_ONE             = (1 << FRAC_BITS);
        static int FRAC_MASK            = (FRAC_ONE - 1);
	
	
	/* struct describing a single playing ADPCM voice */
	public static class YMZ280BVoice
	{
		int playing;			/* 1 if we are actively playing */
	
		int keyon;			/* 1 if the key is on */
		int looping;			/* 1 if looping is enabled */
		int mode;				/* current playback mode */
		int fnum;			/* frequency */
		int level;			/* output level */
		int pan;				/* panning */
	
		int start;			/* start address, in nibbles */
		int stop;			/* stop address, in nibbles */
		int loop_start;		/* loop start address, in nibbles */
		int loop_end;		/* loop end address, in nibbles */
		int position;		/* current position, in nibbles */
	
		int signal;			/* current ADPCM signal */
		int step;				/* current ADPCM step */
	
		int output_left;		/* output volume (left) */
		int output_right;		/* output volume (right) */
		int output_step;		/* step value for frequency conversion */
		int output_pos;		/* current fractional position */
		int last_sample;		/* last sample output */
		int curr_sample;		/* current sample target */
	};
	
	public static class YMZ280BChip
	{
		int stream;						/* which stream are we using */
		UBytePtr region_base;				/* pointer to the base of the region */
		int current_register;			/* currently accessible register */
		int status_register;			/* current status register */
		int irq_state;				/* current IRQ state */
		int irq_mask;					/* current IRQ mask */
		int irq_enable;				/* current IRQ enable */
		int keyon_enable;				/* key on enable */
		double master_clock;			/* master clock frequency */
		irqcallbacksPtr irq_callback;		/* IRQ callback */
		YMZ280BVoice[]	voice = new YMZ280BVoice[8];	/* the 8 voices */
	};
	
	static YMZ280BChip[] ymz280b = new YMZ280BChip[MAX_YMZ280B];
	static IntPtr accumulator;
	static ShortPtr scratch;

	/* step size index shift table */
	static int index_scale[] = { 0x0e6, 0x0e6, 0x0e6, 0x0e6, 0x133, 0x199, 0x200, 0x266 };
	
	/* lookup table for the precomputed difference */
	static int[] diff_lookup = new int[16];
	
	/* volume lookup table */
	static int[] volume_table = new int[256];
	
	
	
	static void update_irq_state(YMZ280BChip chip)
	{
		int irq_bits = chip.status_register & chip.irq_mask;
	
		/* always off if the enable is off */
		if (chip.irq_enable==0)
			irq_bits = 0;
	
		/* update the state if changed */
		if (irq_bits!=0 && chip.irq_state==0)
		{
			chip.irq_state = 1;
			if (chip.irq_callback != null)
				(chip.irq_callback).handler(1);
		}
		else if (irq_bits==0 && chip.irq_state!=0)
		{
			chip.irq_state = 0;
			if (chip.irq_callback != null)
				(chip.irq_callback).handler(0);
		}
	}
	
	
	static void update_step(YMZ280BChip chip, YMZ280BVoice voice)
	{
		double frequency;
	
		/* handle the sound-off case */
		if (Machine.sample_rate == 0)
		{
			voice.output_step = 0;
			return;
		}
	
		/* compute the frequency */
		if (voice.mode == 1)
			frequency = chip.master_clock * (double)((voice.fnum & 0x0ff) + 1) * (1.0 / 256.0);
		else
			frequency = chip.master_clock * (double)((voice.fnum & 0x1ff) + 1) * (1.0 / 256.0);
		voice.output_step = (int) (frequency * (double)FRAC_ONE / (double)Machine.sample_rate);
	}
	
	
	static void update_volumes(YMZ280BVoice voice)
	{
		if (voice.pan == 8)
		{
			voice.output_left = volume_table[voice.level];
			voice.output_right = volume_table[voice.level];
		}
		else if (voice.pan < 8)
		{
			voice.output_left = volume_table[voice.level];
			voice.output_right = volume_table[voice.level] * voice.pan / 8;
		}
		else
		{
			voice.output_left = volume_table[voice.level] * (15 - voice.pan) / 8;
			voice.output_right = volume_table[voice.level];
		}
	}
	
	
	/**********************************************************************************************
	
	     compute_tables -- compute the difference tables
	
	***********************************************************************************************/
	
	static void compute_tables()
	{
		int step, nib;
	
		/* loop over all nibbles and compute the difference */
		for (nib = 0; nib < 16; nib++)
		{
			int value = (nib & 0x07) * 2 + 1;
			diff_lookup[nib] = (nib & 0x08)!=0 ? -value : value;
		}
	
		/* generate the volume table (currently just a guess) */
		for (step = 0; step < 256; step++)
			volume_table[step] = step * 128 / 256;
	}
	
	
	
	/**********************************************************************************************
	
	     generate_adpcm -- general ADPCM decoding routine
	
	***********************************************************************************************/
	
	static int generate_adpcm(YMZ280BVoice voice, UBytePtr base, ShortPtr buffer, int samples)
	{
		int position = voice.position;
		int signal = voice.signal;
		int step = voice.step;
		int val;
                
                buffer.offset = 0;
	
		/* two cases: first cases is non-looping */
		if (voice.looping==0)
		{
			/* loop while we still have samples to generate */
			while (samples != 0)
			{
				/* compute the new amplitude and update the current step */
				val = base.read(position / 2) >> (((position & 1) << 2) ^ 4);
				signal += (step * diff_lookup[val & 15]) / 8;
	
				/* clamp to the maximum */
				if (signal > 32767)
					signal = 32767;
				else if (signal < -32768)
					signal = -32768;
	
				/* adjust the step size and clamp */
				step = (step * index_scale[val & 7]) >> 8;
				if (step > 0x6000)
					step = 0x6000;
				else if (step < 0x7f)
					step = 0x7f;
	
				/* output to the buffer, scaling by the volume */
				buffer.writeinc((short) signal);
				samples--;
	
				/* next! */
				position++;
				if (position >= voice.stop)
					break;
			}
		}
	
		/* second case: looping */
		else
		{
			/* loop while we still have samples to generate */
			while (samples > 0)
			{
				/* compute the new amplitude and update the current step */
				val = base.read(position / 2) >> (((position & 1) << 2) ^ 4);
				signal += (step * diff_lookup[val & 15]) / 8;
	
				/* clamp to the maximum */
				if (signal > 32767)
					signal = 32767;
				else if (signal < -32768)
					signal = -32768;
	
				/* adjust the step size and clamp */
				step = (step * index_scale[val & 7]) >> 8;
				if (step > 0x6000)
					step = 0x6000;
				else if (step < 0x7f)
					step = 0x7f;
	
				/* output to the buffer, scaling by the volume */
				buffer.writeinc((short) signal);
				samples--;
	
				/* next! */
				position++;
				if (position >= voice.loop_end)
				{
					if (voice.keyon != 0)
						position = voice.loop_start;
				}
				if (position >= voice.stop)
					break;
			}
		}
	
		/* update the parameters */
		voice.position = position;
		voice.signal = signal;
		voice.step = step;
	
		return samples;
	}
	
	
	
	/**********************************************************************************************
	
	     generate_pcm8 -- general 8-bit PCM decoding routine
	
	***********************************************************************************************/
	
	static int generate_pcm8(YMZ280BVoice voice, UBytePtr base, ShortPtr buffer, int samples)
	{
		int position = voice.position;
		int val;
	
		/* two cases: first cases is non-looping */
		if (voice.looping==0)
		{
			/* loop while we still have samples to generate */
			while (samples != 0)
			{
				/* fetch the current value */
				val = base.read(position / 2);
	
				/* output to the buffer, scaling by the volume */
				buffer.writeinc((short) ((byte)val * 256));
				samples--;
	
				/* next! */
				position += 2;
				if (position >= voice.stop)
					break;
			}
		}
	
		/* second case: looping */
		else
		{
			/* loop while we still have samples to generate */
			while (samples != 0)
			{
				/* fetch the current value */
				val = base.read(position / 2);
	
				/* output to the buffer, scaling by the volume */
				buffer.writeinc((short) ((byte)val * 256));
				samples--;
	
				/* next! */
				position += 2;
				if (position >= voice.loop_end)
				{
					if (voice.keyon != 0)
						position = voice.loop_start;
				}
				if (position >= voice.stop)
					break;
			}
		}
	
		/* update the parameters */
		voice.position = position;
	
		return samples;
	}
	
	
	
	/**********************************************************************************************
	
	     generate_pcm16 -- general 16-bit PCM decoding routine
	
	***********************************************************************************************/
	
	static int generate_pcm16(YMZ280BVoice voice, UBytePtr base, ShortPtr buffer, int samples)
	{
		int position = voice.position;
		int val;
	
		/* two cases: first cases is non-looping */
		if (voice.looping==0)
		{
			/* loop while we still have samples to generate */
			while (samples != 0)
			{
				/* fetch the current value */
				val = ((base.read(position / 2 + 1) << 8) + base.read(position / 2)) & 0xffff;
	
				/* output to the buffer, scaling by the volume */
				buffer.writeinc((short) val);
				samples--;
	
				/* next! */
				position += 4;
				if (position >= voice.stop)
					break;
			}
		}
	
		/* second case: looping */
		else
		{
			/* loop while we still have samples to generate */
			while (samples != 0)
			{
				/* fetch the current value */
				val = ((base.read(position / 2 + 1) << 8) + base.read(position / 2)) & 0xffff;
	
				/* output to the buffer, scaling by the volume */
				buffer.writeinc((short) val);
				samples--;
	
				/* next! */
				position += 4;
				if (position >= voice.loop_end)
				{
					if (voice.keyon != 0)
						position = voice.loop_start;
				}
				if (position >= voice.stop)
					break;
			}
		}
	
		/* update the parameters */
		voice.position = position;
	
		return samples;
	}
	
	
	
	/**********************************************************************************************
	
	     ymz280b_update -- update the sound chip so that it is in sync with CPU execution
	
	***********************************************************************************************/
	
	static StreamInitMultiPtr ymz280b_update = new StreamInitMultiPtr() {
            @Override
            public void handler(int num, ShortPtr[] buffer, int length) {
                YMZ280BChip chip = ymz280b[num];
		IntPtr lacc = new IntPtr(accumulator);
		IntPtr racc = new IntPtr(accumulator, length);
		int v;
	
		/* clear out the accumulator */
		memset(accumulator, 0, 2 * length);
	
		/* loop over voices */
		for (v = 0; v < 8; v++)
		{
			YMZ280BVoice voice = chip.voice[v];
			int prev = voice.last_sample;
			int curr = voice.curr_sample;
			ShortPtr curr_data = new ShortPtr(scratch);
			IntPtr ldest = new IntPtr(lacc);
			IntPtr rdest = new IntPtr(racc);
			int new_samples, samples_left;
			int final_pos;
			int remaining = length;
			int lvol = voice.output_left;
			int rvol = voice.output_right;
	
			/* quick out if we're not playing and we're at 0 */
			if (voice.playing==0 && curr == 0)
				continue;
	
			/* finish off the current sample */
			if (voice.output_pos > 0)
			{
				/* interpolate */
				while (remaining > 0 && voice.output_pos < FRAC_ONE)
				{
					int interp_sample = ((prev * (FRAC_ONE - voice.output_pos)) + (curr * voice.output_pos)) >> FRAC_BITS;
					//*ldest++ += interp_sample * lvol;
                                        ldest.write(0, ldest.read(0) + interp_sample * lvol);
                                        ldest.inc();
                                        
					//*rdest++ += interp_sample * rvol;
                                        rdest.write(0, rdest.read(0) + interp_sample * rvol);
                                        rdest.inc();
                                        
					voice.output_pos += voice.output_step;
					remaining--;
				}
	
				/* if we're over, continue; otherwise, we're done */
				if (voice.output_pos >= FRAC_ONE)
					voice.output_pos -= FRAC_ONE;
				else
					continue;
			}
	
			/* compute how many new samples we need */
			final_pos = voice.output_pos + remaining * voice.output_step;
			new_samples = (final_pos + FRAC_ONE - 1) >> FRAC_BITS;
			if (new_samples > MAX_SAMPLE_CHUNK)
				new_samples = MAX_SAMPLE_CHUNK;
			samples_left = new_samples;
	
			/* generate them into our buffer */
			if (voice.playing != 0)
			{
				switch (voice.mode)
				{
					case 1:	samples_left = generate_adpcm(voice, new UBytePtr(chip.region_base), scratch, new_samples);	break;
					case 2:	samples_left = generate_pcm8(voice, new UBytePtr(chip.region_base), scratch, new_samples);	break;
					case 3:	samples_left = generate_pcm16(voice, new UBytePtr(chip.region_base), scratch, new_samples);	break;
					default:
					case 0:	samples_left = 0; memset(scratch, 0, new_samples);			break;
				}
			}
	
			/* if there are leftovers, ramp back to 0 */
			if (samples_left != 0)
			{
				int base = new_samples - samples_left;
				int i, t = (base == 0) ? curr : scratch.read(base - 1);
				for (i = 0; i < samples_left; i++)
				{
					if (t < 0) t = -((-t * 15) >> 4);
					else if (t > 0) t = (t * 15) >> 4;
					scratch.write(base + i, (short) t);
				}
	
				/* if we hit the end and IRQs are enabled, signal it */
				if (base != 0)
				{
					voice.playing = 0;
					chip.status_register |= 1 << v;
					update_irq_state(chip);
				}
			}
	
			/* advance forward one sample */
			prev = curr;
			curr = curr_data.readinc();
	
			/* then sample-rate convert with linear interpolation */
			while (remaining > 0)
			{
				/* interpolate */
				while (remaining > 0 && voice.output_pos < FRAC_ONE)
				{
					int interp_sample = ((prev * (FRAC_ONE - voice.output_pos)) + (curr * voice.output_pos)) >> FRAC_BITS;
					//*ldest++ += interp_sample * lvol;
                                        ldest.write(0, ldest.read(0) + interp_sample * lvol);
                                        ldest.inc();
                                        
					//*rdest++ += interp_sample * rvol;
                                        rdest.write(0, rdest.read(0) + interp_sample * rvol);
                                        rdest.inc();
                                        
					voice.output_pos += voice.output_step;
					remaining--;
				}
	
				/* if we're over, grab the next samples */
				if (voice.output_pos >= FRAC_ONE)
				{
					voice.output_pos -= FRAC_ONE;
					prev = curr;
					curr = curr_data.readinc();
				}
			}
	
			/* remember the last samples */
			voice.last_sample = prev;
			voice.curr_sample = curr;
                        
                        chip.voice[v] = voice;
		}
	
		/* mix and clip the result */
		for (v = 0; v < length; v++)
		{
			int lsamp = lacc.read(v) / 256;
			int rsamp = racc.read(v) / 256;
	
			if (lsamp < -32768) lsamp = -32768;
			else if (lsamp > 32767) lsamp = 32767;
			if (rsamp < -32768) rsamp = -32768;
			else if (rsamp > 32767) rsamp = 32767;
	
			buffer[0].write(v, (short) lsamp);
			buffer[1].write(v, (short) rsamp);
		}
                
                ymz280b[num] = chip;
            }
        };
        
	
	/**********************************************************************************************
	
	     YMZ280B_sh_start -- start emulation of the YMZ280B
	
	***********************************************************************************************/
	
	public static ShStartPtr YMZ280B_sh_start = new ShStartPtr() { public int handler(MachineSound msound) 
	{
		YMZ280Binterface intf = (YMZ280Binterface) msound.sound_interface;
		String[] stream_name = new String[2];
		String[] stream_name_ptrs = new String[2];
		int[] vol = new int[2];
		int i;
	
		/* compute ADPCM tables */
		compute_tables();
	
		/* initialize the voices */
		//memset(&ymz280b, 0, sizeof(ymz280b));
                ymz280b = new YMZ280BChip[MAX_YMZ280B];
                for (int _i=0 ; _i<MAX_YMZ280B ; _i++) {
                    ymz280b[_i] = new YMZ280BChip();
                    
                    for (int _j=0 ; _j<8 ; _j++)
                        ymz280b[_i].voice[_j] = new YMZ280BVoice();
                }
                
		for (i = 0; i < intf.num; i++)
		{
			/* generate the name and create the stream */
			stream_name[0] = sprintf("%s #%d (Left)", sound_name(msound), i);
			stream_name[1] = sprintf("%s #%d (Right)", sound_name(msound), i);
			stream_name_ptrs[0] = stream_name[0];
			stream_name_ptrs[1] = stream_name[1];
	
			/* set the volumes */
			vol[0] = MIXER(intf.mixing_level[i], MIXER_PAN_LEFT);
			vol[1] = MIXER(intf.mixing_level[i], MIXER_PAN_RIGHT);
	
			/* create the stream */
			ymz280b[i].stream = stream_init_multi(2, stream_name_ptrs, vol, Machine.sample_rate, i, ymz280b_update);
			if (ymz280b[i].stream == -1)
				return 1;
	
			/* initialize the rest of the structure */
			ymz280b[i].master_clock = (double)intf.baseclock/*[i]*/ / 384.0;
			ymz280b[i].region_base = new UBytePtr(memory_region(intf.region[i]));
			ymz280b[i].irq_callback = intf.irq_callback[i];
		}
	
		/* allocate memory */
		accumulator = new IntPtr(2 * MAX_SAMPLE_CHUNK * 4);
		scratch = new ShortPtr(MAX_SAMPLE_CHUNK * 2);
		if (accumulator==null || scratch==null)
			return 1;
	
		/* success */
		return 0;
	} };
	
	
	
	/**********************************************************************************************
	
	     YMZ280B_sh_stop -- stop emulation of the YMZ280B
	
	***********************************************************************************************/
	
	public static ShStopPtr YMZ280B_sh_stop = new ShStopPtr() { public void handler() 
	{
		/* free memory */
		if (accumulator != null)
			accumulator = null;
	
		if (scratch != null)
                    scratch = null;
	} };
	
	
	
	/**********************************************************************************************
	
	     write_to_register -- handle a write to the current register
	
	***********************************************************************************************/
	
	static void write_to_register(YMZ280BChip chip, int data)
	{
		YMZ280BVoice voice;
		int i;
	
		/* force an update */
		stream_update(chip.stream, 0);
	
		/* lower registers follow a pattern */
		if (chip.current_register < 0x80)
		{
			voice = chip.voice[(chip.current_register >> 2) & 7];
	
			switch (chip.current_register & 0xe3)
			{
				case 0x00:		/* pitch low 8 bits */
					voice.fnum = (voice.fnum & 0x100) | (data & 0xff);
					update_step(chip, voice);
					break;
	
				case 0x01:		/* pitch upper 1 bit, loop, key on, mode */
					voice.fnum = (voice.fnum & 0xff) | ((data & 0x01) << 8);
					voice.looping = (data & 0x10) >> 4;
					voice.mode = (data & 0x60) >> 5;
					if (voice.keyon==0 && (data & 0x80)!=0 && chip.keyon_enable!=0)
					{
						voice.playing = 1;
						voice.position = voice.start;
						voice.signal = 0;
						voice.step = 0x7f;
					}
					if (voice.keyon!=0 && (data & 0x80)==0 && voice.looping==0)
						voice.playing = 0;
					voice.keyon = (data & 0x80) >> 7;
					update_step(chip, voice);
					break;
	
				case 0x02:		/* total level */
					voice.level = data;
					update_volumes(voice);
					break;
	
				case 0x03:		/* pan */
					voice.pan = data & 0x0f;
					update_volumes(voice);
					break;
	
				case 0x20:		/* start address high */
					voice.start = (voice.start & (0x00ffff << 1)) | (data << 17);
					break;
	
				case 0x21:		/* loop start address high */
					voice.loop_start = (voice.loop_start & (0x00ffff << 1)) | (data << 17);
					break;
	
				case 0x22:		/* loop end address high */
					voice.loop_end = (voice.loop_end & (0x00ffff << 1)) | (data << 17);
					break;
	
				case 0x23:		/* stop address high */
					voice.stop = (voice.stop & (0x00ffff << 1)) | (data << 17);
					break;
	
				case 0x40:		/* start address middle */
					voice.start = (voice.start & (0xff00ff << 1)) | (data << 9);
					break;
	
				case 0x41:		/* loop start address middle */
					voice.loop_start = (voice.loop_start & (0xff00ff << 1)) | (data << 9);
					break;
	
				case 0x42:		/* loop end address middle */
					voice.loop_end = (voice.loop_end & (0xff00ff << 1)) | (data << 9);
					break;
	
				case 0x43:		/* stop address middle */
					voice.stop = (voice.stop & (0xff00ff << 1)) | (data << 9);
					break;
	
				case 0x60:		/* start address low */
					voice.start = (voice.start & (0xffff00 << 1)) | (data << 1);
					break;
	
				case 0x61:		/* loop start address low */
					voice.loop_start = (voice.loop_start & (0xffff00 << 1)) | (data << 1);
					break;
	
				case 0x62:		/* loop end address low */
					voice.loop_end = (voice.loop_end & (0xffff00 << 1)) | (data << 1);
					break;
	
				case 0x63:		/* stop address low */
					voice.stop = (voice.stop & (0xffff00 << 1)) | (data << 1);
					break;
	
				default:
					logerror("YMZ280B: unknown register write %02X = %02X\n", chip.current_register, data);
					break;
			}
                        
                        chip.voice[(chip.current_register >> 2) & 7] = voice;
		}
	
		/* upper registers are special */
		else
		{
			switch (chip.current_register)
			{
				case 0xfe:		/* IRQ mask */
					chip.irq_mask = data;
					update_irq_state(chip);
					break;
	
				case 0xff:		/* IRQ enable, test, etc */
					chip.irq_enable = (data & 0x10) >> 4;
					update_irq_state(chip);
					chip.keyon_enable = (data & 0x80) >> 7;
					if (chip.keyon_enable==0)
						for (i = 0; i < 8; i++)
							chip.voice[i].playing = 0;
					break;
	
				default:
					logerror("YMZ280B: unknown register write %02X = %02X\n", chip.current_register, data);
					break;
			}
		}
	}
	
	
	
	/**********************************************************************************************
	
	     compute_status -- determine the status bits
	
	***********************************************************************************************/
	
	static int compute_status(YMZ280BChip chip)
	{
		int result = chip.status_register;
	
		/* force an update */
		stream_update(chip.stream, 0);
	
		/* clear the IRQ state */
		chip.status_register = 0;
		update_irq_state(chip);
	
		return result;
	}
	
	
	
	/**********************************************************************************************
	
	     YMZ280B_status_0_r/YMZ280B_status_1_r -- handle a read from the status register
	
	***********************************************************************************************/
	
	public static ReadHandlerPtr YMZ280B_status_0_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
                YMZ280BChip _tmp = ymz280b[0];
		int _out = compute_status(_tmp);
                ymz280b[0] = _tmp;
                
                return _out;
	} };
	
/*TODO*///	public static ReadHandlerPtr YMZ280B_status_1_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		return compute_status(&ymz280b[1]);
/*TODO*///	} };
	
	
	
	/**********************************************************************************************
	
	     YMZ280B_register_0_w/YMZ280B_register_1_w -- handle a write to the register select
	
	***********************************************************************************************/
	
	public static WriteHandlerPtr YMZ280B_register_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		ymz280b[0].current_register = data;
	} };
	
/*TODO*///	public static WriteHandlerPtr YMZ280B_register_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		ymz280b[1].current_register = data;
/*TODO*///	} };
	
	
	
	/**********************************************************************************************
	
	     YMZ280B_data_0_w/YMZ280B_data_1_w -- handle a write to the current register
	
	***********************************************************************************************/
	
	public static WriteHandlerPtr YMZ280B_data_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
                YMZ280BChip _tmp = ymz280b[0]; 
		write_to_register(_tmp, data);
                ymz280b[0] = _tmp;
	} };
	
/*TODO*///	public static WriteHandlerPtr YMZ280B_data_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		write_to_register(&ymz280b[1], data);
/*TODO*///	} };    
}
