/*
C140.c

Simulator based on AMUSE sources.
The C140 sound chip is used by Namco SystemII, System21
This chip controls 24 channels of PCM.
16 bytes are associated with each channel.
Channels can be 8 bit signed PCM, or 12 bit signed PCM.

Timer behavior is not yet handled.

Unmapped registers:
	0x1f8:timer interval?	(Nx0.1 ms)
	0x1fa:irq ack? timer restart?
	0x1fe:timer switch?(0:off 1:on)
*/
/*
	2000.06.26	CAB		fixed compressed pcm playback
*/


/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.v037b7.sound;

import static arcadeflex.common.libc.cstring.memset;
import arcadeflex.common.ptrLib.ShortPtr;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound.c140H.*;
import static arcadeflex.v037b7.sound.mixerH.*;
import static arcadeflex.v037b7.sound.streams.*;

public class c140 extends snd_interface 
{
	
    static int MAX_VOICE = 24;
    
    public c140() {
        this.name = "C140";
        this.sound_num = SOUND_C140;
    }

    @Override
    public int chips_num(MachineSound msound) {
        return 1;
    }

    @Override
    public int chips_clock(MachineSound msound) {
        return ((C140interface) msound.sound_interface).frequency;
    }

    @Override
    public int start(MachineSound msound) {
        return C140_sh_start(msound);
    }

    @Override
    public void stop() {
        C140_sh_stop.handler();
    }

    @Override
    public void update() {
        //NO FUNCTIONALITY EXPECTED
    }

    @Override
    public void reset() {
        //NO FUNCTIONALITY EXPECTED
    }
	
	public static class voice_registers
	{
                //int _data; // added by Chuso
		int volume_right;
		int volume_left;
		int frequency_msb;
		int frequency_lsb;
		int bank;
		int mode;
		int start_msb;
		int start_lsb;
		int end_msb;
		int end_lsb;
		int loop_msb;
		int loop_lsb;
		int[] reserved = new int[4];
	};
	
	static int sample_rate;
	static int stream;
	
	/* internal buffers */
	static ShortPtr mixer_buffer_left;
	static ShortPtr mixer_buffer_right;
	
	static int baserate;
	static UBytePtr pRom = new UBytePtr();
	static int[] REG = new int[0x200];
	
	static int[] pcmtbl = new int[8];		//2000.06.26 CAB
	
	public static class VOICE
	{
		public long	ptoffset;
		long	pos;
		long	key;
		//--work
		long	lastdt;
		long	prevdt;
		long	dltdt;
		//--reg
		long	rvol;
		long	lvol;
		long	frequency;
		long	bank;
		long	mode;
	
		long	sample_start;
		long	sample_end;
		long	sample_loop;
	};
	
	static VOICE[] voi = new VOICE[MAX_VOICE];
	
	static VOICE init_voice( VOICE v )
	{
		v.key=0;
		v.ptoffset=0;
		v.rvol=0;
		v.lvol=0;
		v.frequency=0;
		v.bank=0;
		v.mode=0;
		v.sample_start=0;
		v.sample_end=0;
		v.sample_loop=0;
                
                return v;
	}
	
	public static ReadHandlerPtr C140_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
            //System.out.println("C140_r");
		offset&=0x1ff;
		return REG[offset];
	} };
	
	static long find_sample( long adrs, long bank)
	{
		adrs=(bank<<16)+adrs;
		return ((adrs&0x200000)>>2)|(adrs&0x7ffff);		//SYSTEM2 mapping
	}
        
        //static voice_registers[] _REG = new voice_registers[MAX_VOICE];
        
        static voice_registers set_voice_register(int _pos) {
            voice_registers vreg = new voice_registers();
            
            vreg.volume_right    = REG[_pos];
            vreg.volume_left     = REG[_pos + 1];
            vreg.frequency_msb   = REG[_pos + 2];
            vreg.frequency_lsb   = REG[_pos + 3];
            vreg.bank            = REG[_pos + 4];
            vreg.mode            = REG[_pos + 5];
            vreg.start_msb       = REG[_pos + 6];
            vreg.start_lsb       = REG[_pos + 7];
            vreg.end_msb         = REG[_pos + 8];
            vreg.end_lsb         = REG[_pos + 9];
            
            return vreg;
        }
	
	public static WriteHandlerPtr C140_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
            //System.out.println("C140_w "+data);
		stream_update(stream, 0);
	
		offset&=0x1ff;
                
                
		REG[offset]=data;
		if( offset<0x180 )
		{
//			VOICE *v = &voi[offset>>4];
			if( (offset&0xf)==0x5 )
			{
				if ((data & 0x80) != 0)
				{
                                    //if (_REG[REG[offset&0x1f0]] == null)
                                    //    _REG[REG[offset&0x1f0]] = new voice_registers();
                                                
                                        voice_registers vreg = set_voice_register(offset&0x1f0);
                                        
					voi[offset>>4].key=1;
					voi[offset>>4].ptoffset=0;
					voi[offset>>4].pos=0;
					voi[offset>>4].lastdt=0;
					voi[offset>>4].prevdt=0;
					voi[offset>>4].dltdt=0;
					voi[offset>>4].bank = vreg.bank;
					voi[offset>>4].mode = data;
					voi[offset>>4].sample_loop = vreg.loop_msb*256 + vreg.loop_lsb;
					voi[offset>>4].sample_start = vreg.start_msb*256 + vreg.start_lsb;
					voi[offset>>4].sample_end = vreg.end_msb*256 + vreg.end_lsb;
                                        
                                        
				}
				else
				{
					voi[offset>>4].key=0;
				}
			}
		}
	} };
	
	static int limit(int in)
	{
		if(in>0x7fff)		return 0x7fff;
		else if(in<-0x8000)	return -0x8000;
		return in;
	}
	
	static StreamInitMultiPtr update_stereo = new StreamInitMultiPtr() {
            @Override
            public void handler(int ch, ShortPtr[] buffer, int length) {
                //System.out.println("update_stereo");
                int		i,j;
	
		int	rvol,lvol;
		int	dt;
		int	sdt;
		int	st,ed,sz;
	
		UBytePtr	pSampleData;
		int	frequency,delta,offset,pos;
		int	cnt;
		int	lastdt,prevdt,dltdt;
		float	pbase=(float) ((float)baserate*2.0 / (float)sample_rate);
	
		ShortPtr	lmix, rmix;
	
		if(length>sample_rate) length=sample_rate;
	
		/* zap the contents of the mixer buffer */
		memset(mixer_buffer_left, 0, length);
		memset(mixer_buffer_right, 0, length);
	
		//--- audio update
		for( i=0;i<MAX_VOICE;i++ )
		{
			//VOICE *v = &voi[i];
			//const struct voice_registers *vreg = (struct voice_registers *)&REG[i*16];
	
			if( voi[i].key != 0 )
			{
				frequency= REG[((i*16))+2]/*.frequency_msb*/*256 + REG[((i*16))+3]/*.frequency_lsb*/;
                                
                                //System.out.println("frequency="+frequency);
	
				/* Abort voice if no frequency value set */
				if(frequency==0) continue;
	
				/* Delta =  frequency * ((8MHz/374)*2 / sample rate) */
				delta=(int) (frequency * pbase);
	
				/* Calculate left/right channel volumes */
				lvol=(REG[i*16+1]/*.volume_left*/*32)/MAX_VOICE; //32ch . 24ch
				rvol=(REG[i*16+0]/*.volume_right*/*32)/MAX_VOICE;
	
				/* Set mixer buffer base pointers */
				lmix = new ShortPtr(mixer_buffer_left);
				rmix = new ShortPtr(mixer_buffer_right);
	
				/* Retrieve sample start/end and calculate size */
				st=(int) voi[i].sample_start;
				ed=(int) voi[i].sample_end;
				sz=ed-st;
	
				/* Retrieve base pointer to the sample data */
				pSampleData=new UBytePtr(pRom, (int)find_sample(st,voi[i].bank));
	
				/* Fetch back previous data pointers */
				offset=(int) voi[i].ptoffset;
				pos=(int) voi[i].pos;
				lastdt=(int) voi[i].lastdt;
				prevdt=(int) voi[i].prevdt;
				dltdt=(int) voi[i].dltdt;
	
				/* Switch on data type */
				if((voi[i].mode&8) != 0)
				{
					//compressed PCM (maybe correct...)
					/* Loop for enough to fill sample buffer as requested */
					for(j=0;j<length;j++)
					{
						offset += delta;
						cnt = (offset>>16)&0x7fff;
						offset &= 0xffff;
						pos+=cnt;
						//for(;cnt>0;cnt--)
						{
							/* Check for the end of the sample */
							if(pos >= sz)
							{
								/* Check if its a looping sample, either stop or loop */
								if((voi[i].mode&0x10) != 0)
								{
									pos = ((int) voi[i].sample_loop - st);
								}
								else
								{
									voi[i].key=0;
									break;
								}
							}
	
							/* Read the chosen sample byte */
							dt=pSampleData.read(pos);
	
							/* decompress to 13bit range */		//2000.06.26 CAB
							sdt=dt>>3;				//signed
							if(sdt<0)	sdt = (sdt<<(dt&7)) - pcmtbl[dt&7];
							else		sdt = (sdt<<(dt&7)) + pcmtbl[dt&7];
	
							prevdt=lastdt;
							lastdt=sdt;
							dltdt=(lastdt - prevdt);
						}
	
						/* Caclulate the sample value */
						dt=((dltdt*offset)>>16)+prevdt;
	
						/* Write the data to the sample buffers */
						//*lmix++ +=(dt*lvol)>>(5+5);
                                                lmix.writeinc((short) (lmix.read(0)+(dt*lvol)>>(5+5)));
						//*rmix++ +=(dt*rvol)>>(5+5);
                                                rmix.writeinc((short) (rmix.read(0)+(dt*rvol)>>(5+5)));
					}
				}
				else
				{
					/* linear 8bit signed PCM */
	
					for(j=0;j<length;j++)
					{
						offset += delta;
						cnt = (offset>>16)&0x7fff;
						offset &= 0xffff;
						pos += cnt;
						/* Check for the end of the sample */
						if(pos >= sz)
						{
							/* Check if its a looping sample, either stop or loop */
							if(( voi[i].mode&0x10 ) != 0)
							{
								pos = (int) (voi[i].sample_loop - st);
							}
							else
							{
								voi[i].key=0;
								break;
							}
						}
	
						if (cnt != 0)
						{
							prevdt=lastdt;
							lastdt=pSampleData.read(pos);
							dltdt=(lastdt - prevdt);
						}
	
						/* Caclulate the sample value */
						dt=((dltdt*offset)>>16)+prevdt;
	
						/* Write the data to the sample buffers */
						//*lmix++ +=(dt*lvol)>>5;
                                                lmix.writeinc((short) (lmix.read(0)+(dt*lvol)>>(5)));
						//*rmix++ +=(dt*rvol)>>5;
                                                rmix.writeinc((short) (rmix.read(0)+(dt*rvol)>>(5)));
					}
				}
	
				/* Save positional data for next callback */
				voi[i].ptoffset=offset;
				voi[i].pos=pos;
				voi[i].lastdt=lastdt;
				voi[i].prevdt=prevdt;
				voi[i].dltdt=dltdt;
			}
		}
	
		/* render to MAME's stream buffer */
		lmix = new ShortPtr(mixer_buffer_left);
		rmix = new ShortPtr(mixer_buffer_right);
		{
			ShortPtr dest1 = new ShortPtr(buffer[0]);
			ShortPtr dest2 = new ShortPtr(buffer[1]);
			for (i = 0; i < length; i++)
			{
				buffer[0].writeinc((short) limit(8*(lmix.readinc())));
				buffer[1].writeinc((short) limit(8*(rmix.readinc())));
			}
		}
            }
        };
        
	static int C140_sh_start( MachineSound msound )
	{
                for (int _i=0 ; _i<MAX_VOICE ; _i++)
                    voi[_i] = new VOICE();
                
		C140interface intf = (C140interface) msound.sound_interface;
		int[] vol = new int[2];
		String stereo_names[] = { "C140 Left", "C140 Right" };
	
		vol[0] = MIXER(intf.mixing_level,MIXER_PAN_LEFT);
		vol[1] = MIXER(intf.mixing_level,MIXER_PAN_RIGHT);
	
		sample_rate=baserate=intf.frequency;
	
		stream = stream_init_multi(2,stereo_names,vol,sample_rate,0,update_stereo);
	
		pRom=new UBytePtr(memory_region(intf.region));
	
		/* make decompress pcm table */		//2000.06.26 CAB
		{
			int i;
			int segbase=0;
			for(i=0;i<8;i++)
			{
				pcmtbl[i]=segbase;	//segment base value
				segbase += 16<<i;
			}
		}
	
		//memset(REG,0,0x200 );
                REG = new int[0x200];
		{
			int i;
			for(i=0;i<MAX_VOICE;i++) 
                            voi[i] = init_voice( voi[i] );
		}
	
		/* allocate a pair of buffers to mix into - 1 second's worth should be more than enough */
		mixer_buffer_left = new ShortPtr(2 * sample_rate );
		if (mixer_buffer_left != null)
		{
			mixer_buffer_right = new ShortPtr(mixer_buffer_left, sample_rate);
			return 0;
		}
		return 1;
	}
	
	public static ShStopPtr C140_sh_stop = new ShStopPtr() { public void handler() 
	{
		mixer_buffer_left = null;
	} };
	
}
