/***************************************************************************

Shanghai

driver by Nicola Salmoria

this is mostly working, but the HD63484 emulation is incomplete. The continue
yes/no display is wrong, I think this is caused by missing "window" emulation.

Also, the game locks up after you win a round.

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.drivers;

import static arcadeflex.common.libc.cstring.*;
import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.subArrays.IntSubArray;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;        
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound._2203intf.*;
import static arcadeflex.v037b7.sound._2203intfH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;

public class shanghai
{
	
	/* the on-chip FIFO is 16 bytes long, but we use a larger one to simplify */
	/* decoding of long commands. Commands can be up to 64KB long... but Shanghai */
	/* doesn't reach that length. */
	static int FIFO_LENGTH = 50;
	static int fifo_counter;
	static int[] fifo = new int[FIFO_LENGTH];
	static UBytePtr HD63484_ram = new UBytePtr();
	static int[] HD63484_reg = new int[256/2];
	static int[] org=new int[1],rwp=new int[1];
	static int cl0,cl1,ccmp;
	static int cpx,cpy;
	
	
	static int instruction_length[] =
	{
		 0, 3, 2, 1,	/* 0x */
		 0, 0,-1, 2,	/* 1x */
		 0, 3, 3, 3,	/* 2x */
		 0, 0, 0, 0,	/* 3x */
		 0, 1, 2, 2,	/* 4x */
		 0, 0, 4, 4,	/* 5x */
		 5, 5, 5, 5,	/* 6x */
		 5, 5, 5, 5,	/* 7x */
		 3, 3, 3, 3, 	/* 8x */
		 3, 3,-2,-2,	/* 9x */
		-2,-2, 2, 4,	/* Ax */
		 5, 5, 7, 7,	/* Bx */
		 3, 3, 1, 1,	/* Cx */
		 2, 2, 2, 2,	/* Dx */
		 5, 5, 5, 5,	/* Ex */
		 5, 5, 5, 5 	/* Fx */
	};
	
	static String instruction_name[] =
	{
		"undef","ORG  ","WPR  ","RPR  ",	/* 0x */
		"undef","undef","WPTN ","RPTN ",	/* 1x */
		"undef","DRD  ","DWT  ","DMOD ",	/* 2x */
		"undef","undef","undef","undef",	/* 3x */
		"undef","RD   ","WT   ","MOD  ",	/* 4x */
		"undef","undef","CLR  ","SCLR ",	/* 5x */
		"CPY  ","CPY  ","CPY  ","CPY  ",	/* 6x */
		"SCPY ","SCPY ","SCPY ","SCPY ",	/* 7x */
		"AMOVE","RMOVE","ALINE","RLINE", 	/* 8x */
		"ARCT ","RRCT ","APLL ","RPLL ",	/* 9x */
		"APLG ","RPLG ","CRCL ","ELPS ",	/* Ax */
		"AARC ","RARC ","AEARC","REARC",	/* Bx */
		"AFRCT","RFRCT","PAINT","DOT  ",	/* Cx */
		"PTN  ","PTN  ","PTN  ","PTN  ",	/* Dx */
		"AGCPY","AGCPY","AGCPY","AGCPY",	/* Ex */
		"RGCPY","RGCPY","RGCPY","RGCPY" 	/* Fx */
	};
	
	static int HD63484_start()
	{
		fifo_counter = 0;
		HD63484_ram = new UBytePtr(0x200000);
		if (HD63484_ram==null) return 1;
		memset(HD63484_ram,0,0x200000);
		return 0;
	}
	
	static void HD63484_stop()
	{
		HD63484_ram = null;
	}
	
	static void doclr(int opcode,int fill,IntSubArray dst,int _ax,int _ay)
	{
		int ax,ay;
	
		ax = _ax;
		ay = _ay;
	
		for (;;)
		{
			for (;;)
			{
				switch (opcode & 0x0003)
				{
					case 0:
						HD63484_ram.write(dst.read(), fill); break;
					case 1:
						HD63484_ram.write(dst.read(), HD63484_ram.read(dst.read()) | fill); break;
					case 2:
						HD63484_ram.write(dst.read(), HD63484_ram.read(dst.read()) & fill); break;
					case 3:
						HD63484_ram.write(dst.read(), HD63484_ram.read(dst.read()) ^ fill); break;
				}
				if (ax == 0) break;
				else if (ax > 0)
				{
					dst.write(0, (dst.read() + 1) & 0x1fffff);
					ax--;
				}
				else
				{
					dst.write(0, (dst.read() - 1) & 0x1fffff);
					ax++;
				}
			}
	
			ax = _ax;
			if (_ay < 0)
			{
				dst.write(0, (dst.read() + 384 - ax) & 0x1fffff);
				if (ay == 0) break;
				ay++;
			}
			else
			{
				dst.write(0, (dst.read() - 384 - ax) & 0x1fffff);
				if (ay == 0) break;
				ay--;
			}
		}
	}
	
	static void docpy(int opcode,int src,IntSubArray dst,int _ax,int _ay)
	{
		int dstep1,dstep2;
		int ax = _ax;
		int ay = _ay;
	
		switch (opcode & 0x0700)
		{
			default:
			case 0x0000: dstep1 =  1; dstep2 = -384; break;
			case 0x0100: dstep1 =  1; dstep2 =  384; break;
			case 0x0200: dstep1 = -1; dstep2 = -384; break;
			case 0x0300: dstep1 = -1; dstep2 =  384; break;
			case 0x0400: dstep1 = -384; dstep2 =  1; break;
			case 0x0500: dstep1 =  384; dstep2 =  1; break;
			case 0x0600: dstep1 = -384; dstep2 = -1; break;
			case 0x0700: dstep1 =  384; dstep2 = -1; break;
		}
		dstep2 -= ax * dstep1;
	
		for (;;)
		{
			for (;;)
			{
				switch (opcode & 0x0007)
				{
					case 0:
						HD63484_ram.write(dst.read(), HD63484_ram.read(src)); break;
					case 1:
						HD63484_ram.write(dst.read(), HD63484_ram.read(dst.read()) | HD63484_ram.read(src)); break;
					case 2:
						HD63484_ram.write(dst.read(), HD63484_ram.read(dst.read()) & HD63484_ram.read(src)); break;
					case 3:
						HD63484_ram.write(dst.read(), HD63484_ram.read(dst.read()) ^ HD63484_ram.read(src)); break;
					case 4:
						if (HD63484_ram.read(dst.read()) == (ccmp & 0xff))
							HD63484_ram.write(dst.read(), HD63484_ram.read(src));
						break;
					case 5:
						if (HD63484_ram.read(dst.read()) != (ccmp & 0xff))
							HD63484_ram.write(dst.read(), HD63484_ram.read(src));
						break;
					case 6:
						if (HD63484_ram.read(dst.read()) < HD63484_ram.read(src))
							HD63484_ram.write(dst.read(), HD63484_ram.read(src));
						break;
					case 7:
						if (HD63484_ram.read(dst.read()) > HD63484_ram.read(src))
							HD63484_ram.write(dst.read(), HD63484_ram.read(src));
						break;
				}
	
				if ((opcode & 0x0800) != 0)
				{
					if (ay == 0) break;
					else if (ay > 0)
					{
						src = (src - 384) & 0x1fffff;
						dst.write(0,(dst.read() + dstep1) & 0x1fffff);
						ay--;
					}
					else
					{
						src = (src + 384) & 0x1fffff;
						dst.write(0,(dst.read() + dstep1) & 0x1fffff);
						ay++;
					}
				}
				else
				{
					if (ax == 0) break;
					else if (ax > 0)
					{
						src = (src + 1) & 0x1fffff;
						dst.write(0,(dst.read() + dstep1) & 0x1fffff);
						ax--;
					}
					else
					{
						src = (src - 1) & 0x1fffff;
						dst.write(0,(dst.read() + dstep1) & 0x1fffff);
						ax++;
					}
				}
			}
	
			if ((opcode & 0x0800) != 0)
			{
				ay = _ay;
				if (_ax < 0)
				{
					src = (src - 1 - ay) & 0x1fffff;
					dst.write(0,(dst.read() + dstep2) & 0x1fffff);
					if (ax == 0) break;
					ax++;
				}
				else
				{
					src = (src + 1 - ay) & 0x1fffff;
					dst.write(0,(dst.read() + dstep2) & 0x1fffff);
					if (ax == 0) break;
					ax--;
				}
			}
			else
			{
				ax = _ax;
				if (_ay < 0)
				{
					src = (src + 384 - ax) & 0x1fffff;
					dst.write(0,(dst.read() + dstep2) & 0x1fffff);
					if (ay == 0) break;
					ay++;
				}
				else
				{
					src = (src - 384 - ax) & 0x1fffff;
					dst.write(0,(dst.read() + dstep2) & 0x1fffff);
					if (ay == 0) break;
					ay--;
				}
			}
		}
	}
	
	
	static void HD63484_command_w(int cmd)
	{
		int len;
	
		fifo[fifo_counter++] = cmd;
	
		len = instruction_length[fifo[0]>>10];
		if (len == -1)
		{
			if (fifo_counter < 2) return;
			else len = fifo[1]+2;
		}
		else if (len == -2)
		{
			if (fifo_counter < 2) return;
			else len = 2*fifo[1]+2;
		}
	
		if (fifo_counter >= len)
		{
			int i;
	
			logerror("PC %05x: HD63484 command %s (%04x) ",cpu_get_pc(),instruction_name[fifo[0]>>10],fifo[0]);
			for (i = 1;i < fifo_counter;i++)
				logerror("%04x ",fifo[i]);
			logerror("\n");
	
			if (fifo[0] == 0x0400)	/* ORG */
				org[0] = ((fifo[1] & 0x00ff) << 12) | ((fifo[2] & 0xfff0) >> 4);
			else if (fifo[0] == 0x0800)
				cl0 = fifo[1];
			else if (fifo[0] == 0x0801)
				cl1 = fifo[1];
			else if (fifo[0] == 0x0802)
				ccmp = fifo[1];
			else if (fifo[0] == 0x080c)
				rwp[0] = (rwp[0] & 0x00fff) | ((fifo[1] & 0x00ff) << 12);
			else if (fifo[0] == 0x080d)
				rwp[0] = (rwp[0] & 0xff000) | ((fifo[1] & 0xfff0) >> 4);
			else if (fifo[0] == 0x4800)	/* WT */
			{
				HD63484_ram.write(2*rwp[0], fifo[1] & 0x00ff);
				HD63484_ram.write(2*rwp[0]+1, (fifo[1] & 0xff00) >> 8);
				rwp[0] = (rwp[0] + 1) & 0xfffff;
			}
			else if (fifo[0] == 0x5800)	/* CLR */
			{
                                rwp[0] *= 2;
				doclr(fifo[0],fifo[1],new IntSubArray(rwp),2*fifo[2]+1,fifo[3]);
                                rwp[0] /= 2;
			}
			else if ((fifo[0] & 0xfffc) == 0x5c00)	/* SCLR */
			{
                                rwp[0] *= 2;
				doclr(fifo[0],fifo[1],new IntSubArray(rwp),2*fifo[2]+1,fifo[3]);
                                rwp[0] /= 2;
			}
			else if ((fifo[0] & 0xf0ff) == 0x6000)	/* CPY */
			{
				int src;
	
				src = ((fifo[1] & 0x00ff) << 12) | ((fifo[2] & 0xfff0) >> 4);
                                rwp[0] *= 2;
				docpy(fifo[0],2*src,new IntSubArray(rwp),2*fifo[3]+1,fifo[4]);
                                rwp[0] /= 2;
			}
			else if ((fifo[0] & 0xf0fc) == 0x7000)	/* SCPY */
			{
				int src;
	
				src = ((fifo[1] & 0x00ff) << 12) | ((fifo[2] & 0xfff0) >> 4);
                                rwp[0] *= 2;
				docpy(fifo[0],2*src,new IntSubArray(rwp),2*fifo[3]+1,fifo[4]);
                                rwp[0] /= 2;
			}
			else if (fifo[0] == 0x8000)	/* AMOVE */
			{
				cpx = fifo[1];
				cpy = fifo[2];
			}
	//		else if ((fifo[0] & 0xff00) == 0xc000)	/* AFRCT */
			else if ((fifo[0] & 0xfff8) == 0xc000)	/* AFRCT */
			{
				int pcx,pcy;
				int ax,ay;
				int dst;
	
				pcx = fifo[1];
				pcy = fifo[2];
				ax = pcx - cpx;
				ay = pcy - cpy;
				dst = (2*org[0] + cpx - cpy * 384) & 0x1fffff;
	
				for (;;)
				{
					for (;;)
					{
						switch (fifo[0] & 0x0007)
						{
							case 0:
								HD63484_ram.write(dst, cl0); break;
							case 1:
								HD63484_ram.write(dst, HD63484_ram.read(dst) | cl0); break;
							case 2:
								HD63484_ram.write(dst, HD63484_ram.read(dst) & cl0); break;
							case 3:
								HD63484_ram.write(dst, HD63484_ram.read(dst) ^ cl0); break;
							case 4:
								if (HD63484_ram.read(dst) == (ccmp & 0xff))
									HD63484_ram.write(dst, cl0);
								break;
							case 5:
								if (HD63484_ram.read(dst) != (ccmp & 0xff))
									HD63484_ram.write(dst, cl0);
								break;
							case 6:
								if (HD63484_ram.read(dst) < (cl0 & 0xff))
									HD63484_ram.write(dst, cl0);
								break;
							case 7:
								if (HD63484_ram.read(dst) > (cl0 & 0xff))
									HD63484_ram.write(dst, cl0);
								break;
						}
	
						if (ax == 0) break;
						else if (ax > 0)
						{
							dst = (dst + 1) & 0x1fffff;
							ax--;
						}
						else
						{
							dst = (dst - 1) & 0x1fffff;
							ax++;
						}
					}
	
					ax = pcx - cpx;
					if (pcy < cpy)
					{
						dst = (dst + 384 - ax) & 0x1fffff;
						if (ay == 0) break;
						ay++;
					}
					else
					{
						dst = (dst - 384 - ax) & 0x1fffff;
						if (ay == 0) break;
						ay--;
					}
				}
			}
	//		else if ((fifo[0] & 0xf000) == 0xe000)	/* AGCPY */
			else if ((fifo[0] & 0xf0f8) == 0xe000)	/* AGCPY */
			{
				int pcx,pcy;
				int src,dst;
	
				pcx = fifo[1];
				pcy = fifo[2];
				src = (2*org[0] + pcx - pcy * 384) & 0x1fffff;
				dst = (2*org[0] + cpx - cpy * 384) & 0x1fffff;
	
				docpy(fifo[0],src,new IntSubArray(dst),fifo[3],fifo[4]);
	
				cpx = (dst - 2*org[0]) % 384;
				cpy = (dst - 2*org[0]) / 384;
			}
			else
	logerror("unsupported command\n");
	
			fifo_counter = 0;
		}
	}
	
	static int regno;
	
	public static ReadHandlerPtr HD63484_status_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		if (offset == 1) return 0xff;	/* high 8 bits - not used */
	
		if (cpu_get_pc() != 0xfced6) logerror("%05x: HD63484 status read\n",cpu_get_pc());
		return 0x22;	/* write FIFO ready + command end */
	} };
        
        static int[] reg=new int[2];
	
	public static WriteHandlerPtr HD63484_address_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
	
		reg[offset] = data;
		regno = reg[0];	/* only low 8 bits are used */
	//if (offset == 0)
	//	logerror("PC %05x: HD63484 select register %02x\n",cpu_get_pc(),regno);
	} };
        
        static int[] dat = new int[2];
	
	public static WriteHandlerPtr HD63484_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
	
		dat[offset] = data;
		if (offset == 1)
		{
			int val = dat[0] + 256 * dat[1];
	
			if (regno == 0)	/* FIFO */
				HD63484_command_w(val);
			else
			{
	logerror("PC %05x: HD63484 register %02x write %04x\n",cpu_get_pc(),regno,val);
				HD63484_reg[regno/2] = val;
				if ((regno & 0x80) != 0) regno += 2;	/* autoincrement */
			}
		}
	} };
	
	public static ReadHandlerPtr HD63484_data_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int res;
	
		if (regno == 0x80)
		{
			res = cpu_getscanline();
		}
		else
		{
	logerror("%05x: HD63484 read register %02x\n",cpu_get_pc(),regno);
			res = 0;
		}
	
		if (offset == 0)
			return res & 0xff;
		else
			return (res >> 8) & 0xff;
	} };
	
	
	
	
	public static VhConvertColorPromPtr shanghai_vh_convert_color_prom = new VhConvertColorPromPtr() { public void handler(char []palette, char []colortable, UBytePtr color_prom) 
	{
		int i;
                int _palette = 0;
	
		for (i = 0;i < Machine.drv.total_colors;i++)
		{
			int bit0,bit1,bit2;
	
	
			/* red component */
			bit0 = (i >> 2) & 0x01;
			bit1 = (i >> 3) & 0x01;
			bit2 = (i >> 4) & 0x01;
			palette[_palette++] = (char) (0x21 * bit0 + 0x47 * bit1 + 0x97 * bit2);
			/* green component */
			bit0 = (i >> 5) & 0x01;
			bit1 = (i >> 6) & 0x01;
			bit2 = (i >> 7) & 0x01;
			palette[_palette++] = (char) (0x21 * bit0 + 0x47 * bit1 + 0x97 * bit2);
			/* blue component */
			bit0 = 0;
			bit1 = (i >> 0) & 0x01;
			bit2 = (i >> 1) & 0x01;
			palette[_palette++] = (char) (0x21 * bit0 + 0x47 * bit1 + 0x97 * bit2);
		}
	} };
	
	public static VhStartPtr shanghai_vh_start = new VhStartPtr() { public int handler() 
	{
		return HD63484_start();
	} };
	
	public static VhStopPtr shanghai_vh_stop = new VhStopPtr() { public void handler() 
	{
		HD63484_stop();
	} };
	
	public static VhUpdatePtr shanghai_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int x,y,b;
	
		b = 2 * (((HD63484_reg[0xcc/2] & 0x001f) << 16) + HD63484_reg[0xce/2]);
		for (y = 0;y < 280;y++)
		{
			for (x = 0;x < 384;x++)
			{
				b &= 0x1fffff;
				plot_pixel.handler(bitmap,x,y,Machine.pens[HD63484_ram.read(b)]);
				b++;
			}
		}
	
		if ((HD63484_reg[0x06/2] & 0x0300) == 0x0300)
		{
			b = 2 * (((HD63484_reg[0xdc/2] & 0x001f) << 16) + HD63484_reg[0xde/2]);
			for (y = 0;y < 280;y++)
			{
				for (x = 0;x < 384;x++)
				{
					b &= 0x1fffff;
					if (HD63484_ram.read(b) != 0)
						plot_pixel.handler(bitmap,x,y,Machine.pens[HD63484_ram.read(b)]);
					b++;
				}
			}
		}
	} };
	
	
	public static InterruptPtr shanghai_interrupt = new InterruptPtr() { public int handler() 
	{
		interrupt_vector_w.handler(0,0x80);
		return interrupt.handler();
	} };
	
	public static WriteHandlerPtr shanghai_coin_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		coin_counter_w.handler(0,data & 1);
		coin_counter_w.handler(1,data & 2);
	} };
	
	static MemoryReadAddress readmem[] =
	{
		new MemoryReadAddress( 0x00000, 0x03fff, MRA_RAM ),
		new MemoryReadAddress( 0xa0000, 0xfffff, MRA_ROM ),
		new MemoryReadAddress( -1 )	/* end of table */
	};
	
	static MemoryWriteAddress writemem[] =
	{
		new MemoryWriteAddress( 0x00000, 0x03fff, MWA_RAM ),
		new MemoryWriteAddress( 0xa0000, 0xfffff, MWA_ROM ),
		new MemoryWriteAddress( -1 )	/* end of table */
	};
	
	static IOReadPort readport[] =
	{
		new IOReadPort( 0x00, 0x01, HD63484_status_r ),
		new IOReadPort( 0x02, 0x03, HD63484_data_r ),
		new IOReadPort( 0x20, 0x20, YM2203_status_port_0_r ),
		new IOReadPort( 0x22, 0x22, YM2203_read_port_0_r ),
		new IOReadPort( 0x40, 0x40, input_port_0_r ),
		new IOReadPort( 0x44, 0x44, input_port_1_r ),
		new IOReadPort( 0x48, 0x48, input_port_2_r ),
		new IOReadPort( -1 )  /* end of table */
	};
	
	static IOWritePort writeport[] =
	{
		new IOWritePort( 0x00, 0x01, HD63484_address_w ),
		new IOWritePort( 0x02, 0x03, HD63484_data_w ),
		new IOWritePort( 0x20, 0x20, YM2203_control_port_0_w ),
		new IOWritePort( 0x22, 0x22, YM2203_write_port_0_w ),
		new IOWritePort( 0x4c, 0x4c, shanghai_coin_w ),
		new IOWritePort( -1 )  /* end of table */
	};
	
	
	
	static InputPortPtr input_ports_shanghai = new InputPortPtr(){ public void handler() { 
		PORT_START(); 	/* IN0 */
		PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY );
		PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY );
		PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY );
		PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY );
		PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_BUTTON1 );
		PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_BUTTON2 );
		PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_BUTTON3 );
		PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_UNKNOWN );
	
		PORT_START(); 	/* IN1 */
		PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY | IPF_COCKTAIL );
		PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY | IPF_COCKTAIL );
		PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY | IPF_COCKTAIL );
		PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_COCKTAIL );
		PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_COCKTAIL );
		PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_COCKTAIL );
		PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_BUTTON3 | IPF_COCKTAIL );
		PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_UNKNOWN );
	
		PORT_START(); 	/* IN2 */
		PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_COIN1 );
		PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_COIN2 );
		PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_COIN3 );
		PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_START1 );
		PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_START2 );
		PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_UNKNOWN );
		PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_UNKNOWN );
		PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_UNKNOWN );
	
		PORT_START(); 	/* DSW0 */
		PORT_SERVICE( 0x01, IP_ACTIVE_LOW );
		PORT_DIPNAME( 0x02, 0x02, "Allow Continue" );
		PORT_DIPSETTING(    0x00, DEF_STR( "No") );
		PORT_DIPSETTING(    0x02, DEF_STR( "Yes") );
		PORT_DIPNAME( 0x1c, 0x1c, DEF_STR( "Coin_B") );
		PORT_DIPSETTING(    0x00, DEF_STR( "5C_1C") );
		PORT_DIPSETTING(    0x04, DEF_STR( "4C_1C") );
		PORT_DIPSETTING(    0x08, DEF_STR( "3C_1C") );
		PORT_DIPSETTING(    0x0c, DEF_STR( "2C_1C") );
		PORT_DIPSETTING(    0x1c, DEF_STR( "1C_1C") );
		PORT_DIPSETTING(    0x18, DEF_STR( "1C_2C") );
		PORT_DIPSETTING(    0x14, DEF_STR( "1C_3C") );
		PORT_DIPSETTING(    0x10, DEF_STR( "1C_4C") );
		PORT_DIPNAME( 0xe0, 0xe0, DEF_STR( "Coin_A") );
		PORT_DIPSETTING(    0x00, DEF_STR( "5C_1C") );
		PORT_DIPSETTING(    0x20, DEF_STR( "4C_1C") );
		PORT_DIPSETTING(    0x40, DEF_STR( "3C_1C") );
		PORT_DIPSETTING(    0x60, DEF_STR( "2C_1C") );
		PORT_DIPSETTING(    0xe0, DEF_STR( "1C_1C") );
		PORT_DIPSETTING(    0xc0, DEF_STR( "1C_2C") );
		PORT_DIPSETTING(    0xa0, DEF_STR( "1C_3C") );
		PORT_DIPSETTING(    0x80, DEF_STR( "1C_4C") );
	
		PORT_START(); 	/* DSW1 */
		PORT_DIPNAME( 0x01, 0x01, "Confirmation" );
		PORT_DIPSETTING(    0x01, DEF_STR( "No") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Yes") );
		PORT_DIPNAME( 0x02, 0x02, "Help" );
		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x02, DEF_STR( "On") );
		PORT_DIPNAME( 0x0c, 0x0c, "2 Players Move Time" );
		PORT_DIPSETTING(    0x0c, "8" );
		PORT_DIPSETTING(    0x08, "10" );
		PORT_DIPSETTING(    0x04, "12" );
		PORT_DIPSETTING(    0x00, "14" );
		PORT_DIPNAME( 0x30, 0x30, "Bonus Time for Making Pair" );
		PORT_DIPSETTING(    0x30, "3" );
		PORT_DIPSETTING(    0x20, "4" );
		PORT_DIPSETTING(    0x10, "5" );
		PORT_DIPSETTING(    0x00, "6" );
		PORT_DIPNAME( 0xc0, 0xc0, "Start Time" );
		PORT_DIPSETTING(    0xc0, "30" );
		PORT_DIPSETTING(    0x80, "60" );
		PORT_DIPSETTING(    0x40, "90" );
		PORT_DIPSETTING(    0x00, "120" );
	INPUT_PORTS_END(); }}; 
	
	
	
	static YM2203interface ym2203_interface = new YM2203interface
	(
		1,			/* 1 chip */
		1500000,	/* ??? */
		new int[] { YM2203_VOL(80,15) },
		new ReadHandlerPtr[] { input_port_3_r },
		new ReadHandlerPtr[] { input_port_4_r },
		new WriteHandlerPtr[] { null },
		new WriteHandlerPtr[] { null }
	);
	
	
	
	static MachineDriver machine_driver_shanghai = new MachineDriver
	(
		/* basic machine hardware */
		new MachineCPU[] {
			new MachineCPU(
				CPU_V30,
				16000000,	/* ??? */
				readmem,writemem,readport,writeport,
				shanghai_interrupt,1,
				null,0
			)
		},
		30, DEFAULT_60HZ_VBLANK_DURATION,	/* frames per second, vblank duration */
		1,	/* single CPU, no need for interleaving */
		null,
	
		/* video hardware */
		384, 280, new rectangle( 0, 384-1, 0, 280-1 ),
		null,
		256,0,
		shanghai_vh_convert_color_prom,
	
		VIDEO_TYPE_RASTER,
		null,
		shanghai_vh_start,
		shanghai_vh_stop,
		shanghai_vh_screenrefresh,
	
		/* sound hardware */
		0,0,0,0,
		new MachineSound[] {
			new MachineSound(
				SOUND_YM2203,
				ym2203_interface
			)
		}
	);
	
	
	
	/***************************************************************************
	
	  Game driver(s)
	
	***************************************************************************/
	
	static RomLoadPtr rom_shanghai = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x100000, REGION_CPU1 );
		ROM_LOAD_V20_EVEN( "shg-22a.rom", 0xa0000, 0x10000, 0xe0a085be );
		ROM_LOAD_V20_ODD ( "shg-21a.rom", 0xa0000, 0x10000, 0x4ab06d32 );
		ROM_LOAD_V20_EVEN( "shg-28a.rom", 0xc0000, 0x10000, 0x983ec112 );
		ROM_LOAD_V20_ODD ( "shg-27a.rom", 0xc0000, 0x10000, 0x41af0945 );
		ROM_LOAD_V20_EVEN( "shg-37b.rom", 0xe0000, 0x10000, 0x3f192da0 );
		ROM_LOAD_V20_ODD ( "shg-36b.rom", 0xe0000, 0x10000, 0xa1d6af96 );
	ROM_END(); }}; 
	
	
	
	public static GameDriver driver_shanghai	   = new GameDriver("1988"	,"shanghai"	,"shanghai.java"	,rom_shanghai,null	,machine_driver_shanghai	,input_ports_shanghai	,null	,ROT0	,	"Sunsoft", "Shanghai", GAME_NOT_WORKING );
}
