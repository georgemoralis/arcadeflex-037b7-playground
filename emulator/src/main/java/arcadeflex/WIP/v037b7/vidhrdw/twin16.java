/* vidhrdw/twin16.c

	Known Issues:
		sprite-tilemap priority isn't right (see Devil's World intro)
		some rogue sprites in Devil's World

	to do:
		8BPP support
		assorted optimizations
*/

//#define USE_16BIT

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.WIP.v037b7.drivers.twin16.twin16_custom_vidhrdw;
import static arcadeflex.WIP.v037b7.drivers.twin16.twin16_fixram;
import static arcadeflex.WIP.v037b7.drivers.twin16.twin16_gfx_rom;
import static arcadeflex.WIP.v037b7.drivers.twin16.twin16_sprite_gfx_ram;
import static arcadeflex.WIP.v037b7.drivers.twin16.twin16_spriteram_process_enable;
import static arcadeflex.WIP.v037b7.drivers.twin16.twin16_tile_gfx_ram;
import static arcadeflex.common.libc.expressions.NOT;
import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.ptrLib.UShortPtr;
import arcadeflex.common.subArrays;
//import arcadeflex.common.subArrays.UShortPtr;
//import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.paletteH.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.common.libc.cstring.*;

public class twin16
{
	static int need_process_spriteram;
	static int gfx_bank;
	static UBytePtr scrollx=new UBytePtr(3*2), scrolly=new UBytePtr(3*2);
	static UBytePtr video_register = new UBytePtr(2);
	
	//enum {
		static int TWIN16_SCREEN_FLIPY		= 0x01;	/* ? breaks devils world text layer */
		static int TWIN16_SCREEN_FLIPX		= 0x02;	/* confirmed: Hard Puncher Intro */
		static int TWIN16_UNKNOWN1		= 0x04;	/* ?Hard Puncher uses this */
		static int TWIN16_PLANE_ORDER		= 0x08;	/* confirmed: Devil Worlds */
		static int TWIN16_TILE_FLIPY		= 0x20;	/* confirmed? Vulcan Venture */
	//};
	
	public static VhStartPtr twin16_vh_start = new VhStartPtr() { public int handler() {
		return 0;
	} };
	
	public static VhStopPtr twin16_vh_stop = new VhStopPtr() { public void handler() {
	} };
	
	/******************************************************************************************/
	
	public static WriteHandlerPtr fround_gfx_bank_w = new WriteHandlerPtr() {public void handler(int offset, int data){
		gfx_bank = COMBINE_WORD(gfx_bank,data);
	} };
	
	public static WriteHandlerPtr twin16_video_register_w = new WriteHandlerPtr() {public void handler(int offset, int data){
		switch( offset ){
			case 0x0: 
                            COMBINE_WORD_MEM( video_register, 0, data ); 
                            //COMBINE_WORD( video_register, data );
                            break;
			case 0x2: COMBINE_WORD_MEM( scrollx, 0, data ); break;
			case 0x4: COMBINE_WORD_MEM( scrolly, 0, data ); break;
			case 0x6: COMBINE_WORD_MEM( scrollx, 1, data ); break;
			case 0x8: COMBINE_WORD_MEM( scrolly, 1, data ); break;
			case 0xa: COMBINE_WORD_MEM( scrollx, 2, data ); break;
			case 0xc: COMBINE_WORD_MEM( scrolly, 2, data ); break;
	
			case 0xe:
			logerror("unknown video_register write:%d", data );
			break;
		}
	} };
	
	/******************************************************************************************/
	
	static void draw_text( osd_bitmap bitmap ){
		rectangle clip = new rectangle(Machine.visible_area);
		UShortPtr source = new UShortPtr(twin16_fixram);
		int i;
	
		int tile_flipx = 0;
		int tile_flipy = 0;
		//if ((video_register & TWIN16_SCREEN_FLIPY) != 0) tile_flipy = !tile_flipy;
		if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPX) != 0) tile_flipx = tile_flipx!=0?0:1;
	
		for( i=0; i<64*64; i++ ){
			int code = source.read(i);
	
			int sx = (i%64)*8;
			int sy = (i/64)*8;
	
			if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPY) != 0) sy = 256-8 - sy;
			if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPX) != 0) sx = 320-8 - sx;
	
			drawgfx( bitmap, Machine.gfx[0],
				code&0x1ff, /* tile number */
				(code>>9)&0xf, /* color */
				tile_flipx,tile_flipy,
				sx,sy,
				clip,TRANSPARENCY_PEN,0);
		}
	}
	
	/******************************************************************************************/
	
	static void draw_sprite( /* slow slow slow, but it's ok for now */
		osd_bitmap bitmap,
		UShortPtr pen_data,
		UShortPtr pal_data,
		int xpos, int ypos,
		int width, int height,
		int flipx, int flipy ){
	//System.out.println("Draw Sprite");
		int x,y;
		if( xpos>=320 ) xpos -= 512;
		if( ypos>=256 ) ypos -= 512;
	
		if( xpos+width>=0 && xpos<320 && ypos+height>16 && ypos<256-16 )
		{
			if (false &&(bitmap.depth == 16))
			{
				for( y=0; y<height; y++ )
				{
					int sy = (flipy!=0)?(ypos+height-1-y):(ypos+y);
					if( sy>=16 && sy<256-16 )
					{
						UShortPtr dest = new UShortPtr(bitmap.line[sy]);
						for( x=0; x<width; x++ )
						{
							int sx = (flipx!=0)?(xpos+width-1-x):(xpos+x);
							if( sx>=0 && sx<320 )
							{
								int pen = pen_data.read(x/4);
								switch( x%4 )
								{
								case 0: pen = pen>>12; break;
								case 1: pen = (pen>>8)&0xf; break;
								case 2: pen = (pen>>4)&0xf; break;
								case 3: pen = pen&0xf; break;
								}
								if (pen != 0) dest.write(sx, pal_data.read(pen));
							}
						}
					}
					pen_data.inc(width/4);
				}
			}
			else
			{
				for( y=0; y<height; y++ )
				{
					int sy = (flipy!=0)?(ypos+height-1-y):(ypos+y);
					if( sy>=16 && sy<256-16 )
					{
						UBytePtr dest = new UBytePtr(bitmap.line[sy]);
						for( x=0; x<width; x++ )
						{
							int sx = (flipx!=0)?(xpos+width-1-x):(xpos+x);
							if( sx>=0 && sx<320 )
							{
								int pen = pen_data.read(x/4);
								switch( x%4 )
								{
								case 0: pen = pen>>12; break;
								case 1: pen = (pen>>8)&0xf; break;
								case 2: pen = (pen>>4)&0xf; break;
								case 3: pen = pen&0xf; break;
								}
								if (pen != 0) dest.write(sx, pal_data.read(pen));
							}
						}
					}
					pen_data.inc(width/4);
				}
			}
		}
	}
	
	public static void twin16_spriteram_process(){
		int dx = scrollx.read(0);
		int dy = scrolly.read(0);
	
		UShortPtr source = new UShortPtr(spriteram, 0x0000);
		UShortPtr finish = new UShortPtr(spriteram, 0x3000);
	
		memset( new UShortPtr(spriteram, 0x3000), 0, 0x800 );
		while( source.offset<finish.offset ){
			int priority = source.read(0);
			if ((priority & 0x8000) != 0){
				UShortPtr dest = new UShortPtr(spriteram, 0x3000 + 8*(priority&0xff));
	
				int xpos = (0x10000*source.read(4))|source.read(5);
				int ypos = (0x10000*source.read(6))|source.read(7);
	
				int attributes = source.read(2)&0x03ff; /* scale,size,color */
				if ((priority & 0x0200) != 0) attributes |= 0x4000;
				attributes |= 0x8000;
	
				dest.write(0, source.read(3)); /* gfx data */
				dest.write(1, (char) (((xpos>>8) - dx)&0xffff));
				dest.write(2, (char) (((ypos>>8) - dy)&0xffff));
				dest.write(3, (char) attributes);
			}
			source.inc(0x50/2);
		}
		need_process_spriteram = 0;
	}
	
	/*
	 * Sprite Format
	 * ----------------------------------
	 *
	 * Word | Bit(s)           | Use
	 * -----+-fedcba9876543210-+----------------
	 *   0  | --xxxxxxxxxxxxxx | code
	 * -----+------------------+
	 *   1  | -------xxxxxxxxx | ypos
	 * -----+------------------+
	 *   2  | -------xxxxxxxxx | xpos
	 * -----+------------------+
	 *   3  | x--------------- | enble
	 *   3  | -x-------------- | priority?
	 *   3  | ------x--------- | yflip?
	 *   3  | -------x-------- | xflip
	 *   3  | --------xx------ | height
	 *   3  | ----------xx---- | width
	 *   3  | ------------xxxx | color
	 */
	
	static void draw_sprites( osd_bitmap bitmap, int pri ){
		int count = 0;
		UShortPtr source = new UShortPtr(spriteram, 0x1800);
		UShortPtr finish = new UShortPtr(source, 0x800);
		pri = pri!=0?0x4000:0x0000;
	
		while( source.offset<finish.offset ){
			int attributes = source.read(3);
			int code = source.read(0);
                        	
			if( code!=0xffff && (attributes&0x8000)!=0 && (attributes&0x4000)==pri ){
                            //System.out.println("IN!");
				int xpos = source.read(1);
				int ypos = source.read(2);
	
				UShortPtr pal_data = new UShortPtr(Machine.pens, ((attributes&0xf)+0x10)*16);
				int height	= 16<<((attributes>>6)&0x3);
				int width	= 16<<((attributes>>4)&0x3);
				UShortPtr pen_data = null;
				int flipy = attributes&0x0200;
				int flipx = attributes&0x0100;
	
				if (twin16_custom_vidhrdw != 0){
                                    //System.out.println("x");
					pen_data = new UShortPtr(twin16_gfx_rom, 0x80000);
				}
				else {
					switch( (code>>12)&0x3 ){ /* bank select */
						case 0:
						pen_data = new UShortPtr(twin16_gfx_rom, 0);
						break;
	
						case 1:
						pen_data = new UShortPtr(twin16_gfx_rom, 0x40000);
						break;
	
						case 2:
						pen_data = new UShortPtr(twin16_gfx_rom, 0x80000);
						if ((code & 0x4000) != 0) pen_data.inc(0x40000);
						break;
	
						case 3:
						pen_data = new UShortPtr(twin16_sprite_gfx_ram);
						break;
					}
					code &= 0xfff;
				}
				pen_data.inc(code*0x40);
                                
                                //System.out.println("a");
	
				if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPY) != 0){
					ypos = 256-ypos-height;
					flipy = NOT(flipy);
				}
				if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPX) != 0){
					xpos = 320-xpos-width;
					flipx = NOT(flipx);
				}
	
                                //System.out.println("b");
                                
				//if( sprite_which==count || !keyboard_pressed( KEYCODE_B ) )
				draw_sprite( bitmap, pen_data, pal_data, xpos, ypos, width, height, flipx, flipy );
                                //System.out.println("c");
			}
				count++;
			source.inc(4);
		}
	}
	
	static void show_video_register( osd_bitmap bitmap ){
/*TODO*///	#if 0
/*TODO*///		int n;
/*TODO*///		for( n=0; n<4; n++ ){
/*TODO*///			drawgfx( bitmap, Machine.uifont,
/*TODO*///				"0123456789abcdef"[(video_register>>(12-4*n))&0xf],
/*TODO*///				0,
/*TODO*///				0,0,
/*TODO*///				n*6+8,16,
/*TODO*///				0,TRANSPARENCY_NONE,0);
/*TODO*///		}
/*TODO*///	#endif
	}
	
	static void draw_layer( osd_bitmap bitmap, int opaque ){
            //System.out.println("depth="+bitmap.depth);
		UShortPtr gfx_base;
		UShortPtr source = new UShortPtr(videoram);
                videoram.offset=0;
		int i, y1, y2, yd;
		int[] bank_table = new int[4];
		int dx, dy, palette;
		int tile_flipx = 0; // video_register&TWIN16_TILE_FLIPX;
		int tile_flipy = video_register.READ_WORD()&TWIN16_TILE_FLIPY;
	
		if( ((video_register.READ_WORD()&TWIN16_PLANE_ORDER)!=0?1:0) != opaque ){
			source.inc(0x1000);
			dx = scrollx.read(2);
			dy = scrolly.read(2);
			palette = 1;
		}
		else {
			source.inc(0x0000);
			dx = scrollx.read(1);
			dy = scrolly.read(1);
			palette = 0;
		}
	
		if (twin16_custom_vidhrdw != 0){
			gfx_base = new UShortPtr(twin16_gfx_rom);
			bank_table[3] = (gfx_bank>>(4*3))&0xf;
			bank_table[2] = (gfx_bank>>(4*2))&0xf;
			bank_table[1] = (gfx_bank>>(4*1))&0xf;
			bank_table[0] = (gfx_bank>>(4*0))&0xf;
		}
		else {
			gfx_base = new UShortPtr(twin16_tile_gfx_ram);
			bank_table[0] = 0;
			bank_table[1] = 1;
			bank_table[2] = 2;
			bank_table[3] = 3;
		}
	
		if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPX) != 0){
			dx = 256-dx-64;
			tile_flipx = NOT(tile_flipx);
		}
	
		if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPY) != 0){
			dy = 256-dy;
			tile_flipy = NOT(tile_flipy);
		}
	
		if (tile_flipy != 0){
			y1 = 7; y2 = -1; yd = -1;
		}
		else {
			y1 = 0; y2 = 8; yd = 1;
		}
	
		for( i=0; i<64*64; i++ ){
			int sx = (i%64)*8;
			int sy = (i/64)*8;
			int xpos,ypos;
	
			if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPX) != 0) sx = 63*8 - sx;
			if ((video_register.READ_WORD() & TWIN16_SCREEN_FLIPY) != 0) sy = 63*8 - sy;
	
			xpos = (sx-dx)&0x1ff;
			ypos = (sy-dy)&0x1ff;
			if( xpos>=320 ) xpos -= 512;
			if( ypos>=256 ) ypos -= 512;
	
			if(  xpos>-8 && ypos>8 && xpos<320 && ypos<256-16 ){
				int code = source.read(i);
				/*
					xxx-------------	color
					---xx-----------	tile bank
					-----xxxxxxxxxxx	tile number
				*/
                                UShortPtr gfx_data = new UShortPtr(gfx_base, (code&0x7ff)*16 + bank_table[(code>>11)&0x3]*0x8000);
				int color = (code>>13);
				UShortPtr pal_data = new UShortPtr(Machine.pens, 16*(0x20+color+8*palette));
	
				{
					int y;
					int data;
					int pen;
	
					if (tile_flipx != 0)
					{
						if (opaque != 0)
						{
							if (bitmap.depth == 16)
							{
								for( y=y1; y!=y2; y+=yd )
								{
									UShortPtr dest = new UShortPtr(bitmap.line[ypos+y], xpos);
									data = gfx_data.readinc();
									dest.write(7, pal_data.read((data>>4*3)&0xf));
									dest.write(6, pal_data.read((data>>4*2)&0xf));
									dest.write(5, pal_data.read((data>>4*1)&0xf));
									dest.write(4, pal_data.read((data>>4*0)&0xf));
									data = gfx_data.readinc();
									dest.write(3, pal_data.read((data>>4*3)&0xf));
									dest.write(2, pal_data.read((data>>4*2)&0xf));
									dest.write(1, pal_data.read((data>>4*1)&0xf));
									dest.write(0, pal_data.read((data>>4*0)&0xf));
								}
							}
							else
							{
                                                            System.out.println("a");
								for( y=y1; y!=y2; y+=yd )
								{
									UBytePtr dest = new UBytePtr(bitmap.line[ypos+y], xpos);
									data = gfx_data.readinc();
									dest.write(7, pal_data.read((data>>4*3)&0xf));
									dest.write(6, pal_data.read((data>>4*2)&0xf));
									dest.write(5, pal_data.read((data>>4*1)&0xf));
									dest.write(4, pal_data.read((data>>4*0)&0xf));
									data = gfx_data.readinc();
									dest.write(3, pal_data.read((data>>4*3)&0xf));
									dest.write(2, pal_data.read((data>>4*2)&0xf));
									dest.write(1, pal_data.read((data>>4*1)&0xf));
									dest.write(0, pal_data.read((data>>4*0)&0xf));
								}
							}
						}
						else
						{
							if (bitmap.depth == 16)
							{
								for( y=y1; y!=y2; y+=yd )
								{
									UBytePtr dest = new UBytePtr(bitmap.line[ypos+y], xpos);
									data = gfx_data.readinc();
									if (data != 0)
									{
										pen = (data>>4*3)&0xf; if (pen != 0) dest.write(7, pal_data.read(pen));
										pen = (data>>4*2)&0xf; if (pen != 0) dest.write(6, pal_data.read(pen));
										pen = (data>>4*1)&0xf; if (pen != 0) dest.write(5, pal_data.read(pen));
										pen = (data>>4*0)&0xf; if (pen != 0) dest.write(4, pal_data.read(pen));
									}
									data = gfx_data.readinc();
									if (data != 0)
									{
										pen = (data>>4*3)&0xf; if (pen != 0) dest.write(3, pal_data.read(pen));
										pen = (data>>4*2)&0xf; if (pen != 0) dest.write(2, pal_data.read(pen));
										pen = (data>>4*1)&0xf; if (pen != 0) dest.write(1, pal_data.read(pen));
										pen = (data>>4*0)&0xf; if (pen != 0) dest.write(0, pal_data.read(pen));
									}
								}
							}
							else
							{
                                                            System.out.println("b");
								for( y=y1; y!=y2; y+=yd )
								{
									UShortPtr dest = new UShortPtr(bitmap.line[ypos+y], xpos);
									data = gfx_data.readinc();
									if (data != 0)
									{
										pen = (data>>4*3)&0xf; if (pen != 0) dest.write(7, pal_data.read(pen));
										pen = (data>>4*2)&0xf; if (pen != 0) dest.write(6, pal_data.read(pen));
										pen = (data>>4*1)&0xf; if (pen != 0) dest.write(5, pal_data.read(pen));
										pen = (data>>4*0)&0xf; if (pen != 0) dest.write(4, pal_data.read(pen));
									}
									data = gfx_data.readinc();
									if (data != 0)
									{
										pen = (data>>4*3)&0xf; if (pen != 0) dest.write(3, pal_data.read(pen));
										pen = (data>>4*2)&0xf; if (pen != 0) dest.write(2, pal_data.read(pen));
										pen = (data>>4*1)&0xf; if (pen != 0) dest.write(1, pal_data.read(pen));
										pen = (data>>4*0)&0xf; if (pen != 0) dest.write(0, pal_data.read(pen));
									}
								}
							}
						}
					}
					else
					{
						if (opaque != 0)
						{
							if (bitmap.depth == 16)
							{
								for( y=y1; y!=y2; y+=yd )
								{
									UShortPtr dest = new UShortPtr(bitmap.line[ypos+y], xpos);
									data = gfx_data.readinc();
                                                                        dest.writeinc(pal_data.read((data>>4*3)&0xf));
									dest.writeinc(pal_data.read((data>>4*2)&0xf));
									dest.writeinc(pal_data.read((data>>4*1)&0xf));
									dest.writeinc(pal_data.read((data>>4*0)&0xf));
									data = gfx_data.readinc();
									dest.writeinc(pal_data.read((data>>4*3)&0xf));
									dest.writeinc(pal_data.read((data>>4*2)&0xf));
									dest.writeinc(pal_data.read((data>>4*1)&0xf));
									dest.writeinc(pal_data.read((data>>4*0)&0xf));
								}
							}
							else
							{
                                                            //System.out.println("c");
								for( y=y1; y!=y2; y+=yd )
								{
									UBytePtr dest = new UBytePtr(bitmap.line[ypos+y], xpos);
									data = gfx_data.readinc();
									dest.writeinc(pal_data.read((data>>4*3)&0xf));
									dest.writeinc(pal_data.read((data>>4*2)&0xf));
									dest.writeinc(pal_data.read((data>>4*1)&0xf));
									dest.writeinc(pal_data.read((data>>4*0)&0xf));
									data = gfx_data.readinc();
									dest.writeinc(pal_data.read((data>>4*3)&0xf));
									dest.writeinc(pal_data.read((data>>4*2)&0xf));
									dest.writeinc(pal_data.read((data>>4*1)&0xf));
									dest.writeinc(pal_data.read((data>>4*0)&0xf));
								}
							}
						}
						else
						{
							if (bitmap.depth == 16)
							{
								for( y=y1; y!=y2; y+=yd )
								{
									UShortPtr dest = new UShortPtr(bitmap.line[ypos+y], xpos);
									data = gfx_data.readinc();
									if (data != 0)
									{
										pen = (data>>4*3)&0xf; if (pen != 0) dest.write(0, pal_data.read(pen));
										pen = (data>>4*2)&0xf; if (pen != 0) dest.write(1, pal_data.read(pen));
										pen = (data>>4*1)&0xf; if (pen != 0) dest.write(2, pal_data.read(pen));
										pen = (data>>4*0)&0xf; if (pen != 0) dest.write(3, pal_data.read(pen));
									}
									data = gfx_data.readinc();
									if (data != 0)
									{
										pen = (data>>4*3)&0xf; if (pen != 0) dest.write(4, pal_data.read(pen));
										pen = (data>>4*2)&0xf; if (pen != 0) dest.write(5, pal_data.read(pen));
										pen = (data>>4*1)&0xf; if (pen != 0) dest.write(6, pal_data.read(pen));
										pen = (data>>4*0)&0xf; if (pen != 0) dest.write(7, pal_data.read(pen));
									}
								}
							}
							else
							{
                                                            //System.out.println("e");
								for( y=y1; y!=y2; y+=yd )
								{
									UBytePtr dest = new UBytePtr(bitmap.line[ypos+y], xpos);
									data = gfx_data.readinc();
									if (data != 0)
									{
										pen = (data>>4*3)&0xf; if (pen != 0) dest.write(0, pal_data.read(pen));
										pen = (data>>4*2)&0xf; if (pen != 0) dest.write(1, pal_data.read(pen));
										pen = (data>>4*1)&0xf; if (pen != 0) dest.write(2, pal_data.read(pen));
										pen = (data>>4*0)&0xf; if (pen != 0) dest.write(3, pal_data.read(pen));
									}
									data = gfx_data.readinc();
									if (data != 0)
									{
										pen = (data>>4*3)&0xf; if (pen != 0) dest.write(4, pal_data.read(pen));
										pen = (data>>4*2)&0xf; if (pen != 0) dest.write(5, pal_data.read(pen));
										pen = (data>>4*1)&0xf; if (pen != 0) dest.write(6, pal_data.read(pen));
										pen = (data>>4*0)&0xf; if (pen != 0) dest.write(7, pal_data.read(pen));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	static void mark_used_colors(){
		UShortPtr source, finish;
		UBytePtr used=new UBytePtr(0x40), used_ptr=new UBytePtr();
		memset( used, 0, 0x40 );
	
		/* text layer */
		used_ptr = new UBytePtr(used, 0x00);
		source = new UShortPtr(twin16_fixram);
		finish = new UShortPtr(source, 0x1000);
		while( source.offset<finish.offset ){
			int code = source.readinc();
			used_ptr.write((code>>9)&0xf, 1);
		}
	
		/* sprites */
		used_ptr = new UBytePtr(used, 0x10);
		source = new UShortPtr(spriteram, 0x1800);
		finish = new UShortPtr(source, 0x800);
		while( source.offset<finish.offset ){
			int attributes = source.read(3);
			int code = source.read(0);
			if( code!=0xffff && (attributes&0x8000)!=0 ) used_ptr.write(attributes&0xf, 1);
			source.inc();
		}
	
		/* plane#0 */
		used_ptr = new UBytePtr(used, 0x20);
		source = new UShortPtr(videoram);
		finish = new UShortPtr(source, 0x1000);
		while( source.offset<finish.offset ){
			int code = source.readinc();
			used_ptr.write(code>>13, 1);
		}
	
		/* plane#1 */
		used_ptr = new UBytePtr(used, 0x28);
		source = new UShortPtr(videoram, 0x1000);
		finish = new UShortPtr(source, 0x1000);
		while( source.offset<finish.offset ){
			int code = source.readinc();
			used_ptr.write(code>>13, 1);
		}
	
		{
			int i;
			memset( palette_used_colors, PALETTE_COLOR_UNUSED, 0x400 );
			for( i=0; i<0x40; i++ ){
				if( used.read(i) != 0 ){
					memset( new UBytePtr(palette_used_colors, i*16), PALETTE_COLOR_VISIBLE, 0x10 );
				}
			}
		}
	}
	
	public static VhUpdatePtr twin16_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) {
		if( twin16_spriteram_process_enable()!=0 && need_process_spriteram!=0 ) twin16_spriteram_process();
		need_process_spriteram = 1;
	
		mark_used_colors();
		palette_recalc();
	
		draw_layer( bitmap,1 );
		draw_sprites( bitmap, 1 );
		draw_layer( bitmap,0 );
		draw_sprites( bitmap, 0 );
		draw_text( bitmap );
	
		show_video_register( bitmap );
	} };
}
