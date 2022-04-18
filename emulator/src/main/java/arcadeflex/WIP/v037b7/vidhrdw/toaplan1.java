/***************************************************************************

  vidhrdw/toaplan1.c

  Functions to emulate the video hardware of the machine.

  There are 4 scrolling layers of graphics, stored in planes of 64x64 tiles.
  Each tile in each plane is assigned a priority between 1 and 15, higher
  numbers have greater priority.

  Each tile takes up 32 bits, the format is:

  0         1         2         3
  ---- ---- ---- ---- -xxx xxxx xxxx xxxx = tile number (0 - $7fff)
  ---- ---- ---- ---- ?--- ---- ---- ---- = unknown / unused
  ---- ---- --xx xxxx ---- ---- ---- ---- = color (0 - $3f)
  ---- ???? ??-- ---- ---- ---- ---- ---- = unknown / unused
  xxxx ---- ---- ---- ---- ---- ---- ---- = priority (0-$f)

  BG Scroll Reg

  0         1         2         3
  xxxx xxxx x--- ---- ---- ---- ---- ---- = x
  ---- ---- ---- ---- yyyy yyyy ---- ---- = y


 Sprite RAM format  (except Rally Bike)

  0         1         2         3
  ---- ---- ---- ---- -xxx xxxx xxxx xxxx = tile number (0 - $7fff)
  ---- ---- ---- ---- x--- ---- ---- ---- = hidden
  ---- ---- --xx xxxx ---- ---- ---- ---- = color (0 - $3f)
  ---- xxxx xx-- ---- ---- ---- ---- ---- = sprite number
  xxxx ---- ---- ---- ---- ---- ---- ---- = priority (0-$f)

  4         5         6         7
  ---- ---- ---- ---- xxxx xxxx x--- ---- = x
  yyyy yyyy y--- ---- ---- ---- ---- ---- = y

  The tiles use a palette of 1024 colors, the sprites use a different palette
  of 1024 colors.

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.common.libc.cstring.*;
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.v037b7.generic.funcPtr.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.paletteram_xBBBBBGGGGGRRRRR_word_w;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static gr.codebb.arcadeflex.v036.cpu.m68000.m68000H.*;

public class toaplan1
{
	
	public static int VIDEORAM1_SIZE	= 0x1000;		/* size in bytes - sprite ram */
	public static int VIDEORAM2_SIZE	= 0x100;		/* size in bytes - sprite size ram */
	public static int VIDEORAM3_SIZE	= 0x4000;		/* size in bytes - tile ram */
	
	public static UBytePtr toaplan1_videoram1 = new UBytePtr();
	public static UBytePtr toaplan1_videoram2 = new UBytePtr();
	public static UBytePtr toaplan1_videoram3 = new UBytePtr();
	public static UBytePtr toaplan1_buffered_videoram1 = new UBytePtr();
	public static UBytePtr toaplan1_buffered_videoram2 = new UBytePtr();
	
	public static UBytePtr toaplan1_colorram1 = new UBytePtr();
	public static UBytePtr toaplan1_colorram2 = new UBytePtr();
	
	public static int[] colorram1_size = new int[1];
	public static int[] colorram2_size = new int[1];
	
	public static int[] scrollregs = new int[8];
	public static int vblank;
	public static int num_tiles;
	
	public static int video_ofs;
	public static int video_ofs3;
	
	public static int toaplan1_flipscreen;
	public static int tiles_offsetx;
	public static int tiles_offsety;
	public static int[] layers_offset = new int[4];
	
	
	public static class tile_struct
	{
		int tile_num;
		int color;
		int priority;
		int xpos;
		int ypos;
	};
	
	static tile_struct[][] bg_list = new tile_struct[4][];
	
	static tile_struct[][] tile_list = new tile_struct[32][];
	static tile_struct[] temp_list;
	static int[] max_list_size = new int[32];
	static int[] tile_count = new int[32];
	
	static osd_bitmap tmpbitmap1;
	static osd_bitmap tmpbitmap2;
	static osd_bitmap tmpbitmap3;
	
	
	public static VhStartPtr rallybik_vh_start = new VhStartPtr() { public int handler() 
	{
		int i;
	
		if ((toaplan1_videoram3 = new UBytePtr(VIDEORAM3_SIZE * 4)) == null) /* 4 layers */
		{
			return 1;
		}
	
		logerror("colorram_size: %08x\n", colorram1_size[0] + colorram2_size[0]);
		if ((paletteram = new UBytePtr(colorram1_size[0] + colorram2_size[0])) == null)
		{
			toaplan1_videoram3=null;
			return 1;
		}
	
		for (i=0; i<4; i++)
		{
			if ((bg_list[i]=new tile_struct[ 33 * 44 ]) == null)
			{
				paletteram=null;
				toaplan1_videoram3=null;
				return 1;
			}
			//memset(bg_list[i], 0, 33 * 44 * sizeof(tile_struct));
                        for (int _i=0 ; _i<(33*44) ; _i++)
                            bg_list[i][_i] = new tile_struct();
		}
	
		for (i=0; i<16; i++)
		{
			max_list_size[i] = 8192;
			if ((tile_list[i]=new tile_struct[max_list_size[i]]) == null)
			{
				for (i=3; i>=0; i--)
					bg_list[i]=null;
				paletteram=null;
				toaplan1_videoram3=null;
				return 1;
			}
			//memset(tile_list[i],0,max_list_size[i]*sizeof(tile_struct));
                        for (int _i=0 ; _i<(max_list_size[i]) ; _i++)
                            tile_list[i][_i] = new tile_struct();
		}
	
		max_list_size[16] = 65536;
		if ((tile_list[16]=new tile_struct[max_list_size[16]]) == null)
		{
			for (i=15; i>=0; i--)
				tile_list[i]=null;
			for (i=3; i>=0; i--)
				bg_list[i]=null;
			paletteram=null;
			toaplan1_videoram3=null;
			return 1;
		}
		//memset(tile_list[16],0,max_list_size[16]*sizeof(tile_struct));
                for (int _i=0 ; _i<(max_list_size[16]) ; _i++)
                            tile_list[16][_i] = new tile_struct();
	
		num_tiles = (Machine.drv.screen_width/8+1)*(Machine.drv.screen_height/8) ;
	
		video_ofs = video_ofs3 = 0;
	
		return 0;
	} };
	
	public static VhStopPtr rallybik_vh_stop = new VhStopPtr() { public void handler() 
	{
		int i ;
	
		for (i=16; i>=0; i--)
		{
			tile_list[i]=null;
			logerror("max_list_size[%d]=%08x\n",i,max_list_size[i]);
		}
	
		for (i=3; i>=0; i--)
			bg_list[i]=null;
	
		paletteram=null;
		toaplan1_videoram3=null;
	} };
	
	public static VhStartPtr toaplan1_vh_start = new VhStartPtr() { public int handler() 
	{
		tmpbitmap1 = bitmap_alloc(Machine.drv.screen_width,Machine.drv.screen_height);
		tmpbitmap2 = bitmap_alloc(Machine.drv.screen_width,Machine.drv.screen_height);
		tmpbitmap3 = bitmap_alloc(Machine.drv.screen_width,Machine.drv.screen_height);
	
	
		if ((toaplan1_videoram1 = new UBytePtr(VIDEORAM1_SIZE)) == null)
		{
			return 1;
		}
		if ((toaplan1_buffered_videoram1 = new UBytePtr(VIDEORAM1_SIZE)) == null)
		{
			toaplan1_videoram1=null;
			return 1;
		}
		if ((toaplan1_videoram2 = new UBytePtr(VIDEORAM2_SIZE)) == null)
		{
			toaplan1_buffered_videoram1=null;
			toaplan1_videoram1=null;
			return 1;
		}
		if ((toaplan1_buffered_videoram2 = new UBytePtr(VIDEORAM2_SIZE)) == null)
		{
			toaplan1_videoram2=null;
			toaplan1_buffered_videoram1=null;
			toaplan1_videoram1=null;
			return 1;
		}
	
		/* Also include all allocated stuff in Rally Bike startup */
		return rallybik_vh_start.handler();
	} };
	
	public static VhStopPtr toaplan1_vh_stop = new VhStopPtr() { public void handler() 
	{
		rallybik_vh_stop.handler();
	
		toaplan1_buffered_videoram2=null;
		toaplan1_videoram2=null;
		toaplan1_buffered_videoram1=null;
		toaplan1_videoram1=null;
		bitmap_free(tmpbitmap3);
		bitmap_free(tmpbitmap2);
		bitmap_free(tmpbitmap1);
	} };
	
	
	
	
	public static ReadHandlerPtr toaplan1_vblank_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return vblank ^= 1;
	} };
	
	public static WriteHandlerPtr toaplan1_flipscreen_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		toaplan1_flipscreen = data; /* 8000 flip, 0000 dont */
	} };
	
	public static ReadHandlerPtr video_ofs_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return video_ofs ;
	} };
	
	public static WriteHandlerPtr video_ofs_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		video_ofs = data ;
	} };
	
	/* tile palette */
	public static ReadHandlerPtr toaplan1_colorram1_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return toaplan1_colorram1.READ_WORD (offset);
	} };
	
	public static WriteHandlerPtr toaplan1_colorram1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		toaplan1_colorram1.WRITE_WORD (offset, data);
		paletteram_xBBBBBGGGGGRRRRR_word_w.handler(offset,data);
	} };
	
	/* sprite palette */
	public static ReadHandlerPtr toaplan1_colorram2_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return toaplan1_colorram2.READ_WORD (offset);
	} };
	
	public static WriteHandlerPtr toaplan1_colorram2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		toaplan1_colorram2.WRITE_WORD (offset, data);
		paletteram_xBBBBBGGGGGRRRRR_word_w.handler(offset+colorram1_size[0],data);
	} };
	
	public static ReadHandlerPtr toaplan1_videoram1_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return toaplan1_videoram1.READ_WORD (2*(video_ofs & (VIDEORAM1_SIZE-1)));
	} };
	
	public static WriteHandlerPtr toaplan1_videoram1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = toaplan1_videoram1.READ_WORD (2*video_ofs & (VIDEORAM1_SIZE-1));
		int newword = COMBINE_WORD (oldword, data);
	
/*TODO*///	#ifdef DEBUG
/*TODO*///		if (2*video_ofs >= VIDEORAM1_SIZE)
/*TODO*///		{
/*TODO*///			logerror("videoram1_w, %08x\n", 2*video_ofs);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///	#endif
	
		toaplan1_videoram1.WRITE_WORD (2*video_ofs & (VIDEORAM1_SIZE-1),newword);
		video_ofs++;
	} };
	
	public static ReadHandlerPtr toaplan1_videoram2_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return toaplan1_videoram2.READ_WORD (2*video_ofs & (VIDEORAM2_SIZE-1));
	} };
	
	public static WriteHandlerPtr toaplan1_videoram2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = toaplan1_videoram2.READ_WORD (2*video_ofs & (VIDEORAM2_SIZE-1));
		int newword = COMBINE_WORD (oldword, data);
	
/*TODO*///	#ifdef DEBUG
/*TODO*///		if (2*video_ofs >= VIDEORAM2_SIZE)
/*TODO*///		{
/*TODO*///			logerror("videoram2_w, %08x\n", 2*video_ofs);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///	#endif
	
		toaplan1_videoram2.WRITE_WORD (2*video_ofs & (VIDEORAM2_SIZE-1),newword);
		video_ofs++;
	} };
	
	public static ReadHandlerPtr video_ofs3_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return video_ofs3;
	} };
	
	public static WriteHandlerPtr video_ofs3_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		video_ofs3 = data ;
	} };
	
	public static ReadHandlerPtr rallybik_videoram3_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int rb_tmp_vid;
	
		rb_tmp_vid = toaplan1_videoram3.READ_WORD ((video_ofs3 & (VIDEORAM3_SIZE-1))*4 + offset);
	
		if (offset == 0)
		{
			rb_tmp_vid |= ((rb_tmp_vid & 0xf000) >> 4);
			rb_tmp_vid |= ((rb_tmp_vid & 0x0030) << 2);
		}
		return rb_tmp_vid;
	} };
	
	public static ReadHandlerPtr toaplan1_videoram3_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return toaplan1_videoram3.READ_WORD ((video_ofs3 & (VIDEORAM3_SIZE-1))*4 + offset);
	} };
	
	public static WriteHandlerPtr toaplan1_videoram3_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = toaplan1_videoram3.READ_WORD ((video_ofs3 & (VIDEORAM3_SIZE-1))*4 + offset);
		int newword = COMBINE_WORD (oldword, data);
	
/*TODO*///	#ifdef DEBUG
/*TODO*///		if ((video_ofs3 & (VIDEORAM3_SIZE-1))*4 + offset >= VIDEORAM3_SIZE*4)
/*TODO*///		{
/*TODO*///			logerror("videoram3_w, %08x\n", 2*video_ofs3);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///	#endif
	
		toaplan1_videoram3.WRITE_WORD ((video_ofs3 & (VIDEORAM3_SIZE-1))*4 + offset,newword);
		if ( offset == 2 ) video_ofs3++;
	} };
	
	public static ReadHandlerPtr scrollregs_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return scrollregs[offset>>1];
	} };
	
	public static WriteHandlerPtr scrollregs_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		scrollregs[offset>>1] = data ;
	} };
	
	public static WriteHandlerPtr offsetregs_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ( offset == 0 )
			tiles_offsetx = data ;
		else
			tiles_offsety = data ;
	} };
	
	public static WriteHandlerPtr layers_offset_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		switch (offset)
		{
	/*		case 0:
				layers_offset[0] = (data&0xff) - 0xdb ;
				break ;
			case 2:
				layers_offset[1] = (data&0xff) - 0x14 ;
				break ;
			case 4:
				layers_offset[2] = (data&0xff) - 0x85 ;
				break ;
			case 6:
				layers_offset[3] = (data&0xff) - 0x07 ;
				break ;
	*/
			case 0:
				layers_offset[0] = data;
				break ;
			case 2:
				layers_offset[1] = data;
				break ;
			case 4:
				layers_offset[2] = data;
				break ;
			case 6:
				layers_offset[3] = data;
				break ;
		}
	
		logerror("layers_offset[0]:%08x\n",layers_offset[0]);
		logerror("layers_offset[1]:%08x\n",layers_offset[1]);
		logerror("layers_offset[2]:%08x\n",layers_offset[2]);
		logerror("layers_offset[3]:%08x\n",layers_offset[3]);
	
	} };
	
	/***************************************************************************
	
	  Draw the game screen in the given osd_bitmap.
	
	***************************************************************************/
	
	
	static void toaplan1_update_palette ()
	{
		int i;
		int priority;
		int color;
		int[] palette_map = new int[64*2];
	
		memset (palette_map, 0, palette_map.length);
	
		/* extract color info from priority layers in order */
		for (priority = 0; priority < 17; priority++ )
		{
			tile_struct tinfo;
                        int _tinfo=0;
	
			//tinfo = (tile_list[priority][0]);
			/* draw only tiles in list */
			for ( i = 0; i < tile_count[priority]; i++ )
			{
                                tinfo = (tile_list[priority][0 + _tinfo]);
				int bank;
	
				bank  = (tinfo.color >> 7) & 1;
				color = (tinfo.color & 0x3f);
				palette_map[color + bank*64] |= Machine.gfx[bank].pen_usage[tinfo.tile_num & (Machine.gfx[bank].total_elements-1)];
	
				_tinfo++;
			}
		}
	
		/* Tell MAME about the color usage */
		for (i = 0; i < 64*2; i++)
		{
			int usage = palette_map[i];
			int j;
	
			if (usage != 0)
			{
				palette_used_colors.write(i * 16 + 0, PALETTE_COLOR_TRANSPARENT);
				for (j = 0; j < 16; j++)
				{
					if ((usage & (1 << j)) != 0)
						palette_used_colors.write(i * 16 + j, PALETTE_COLOR_USED);
					else
						palette_used_colors.write(i * 16 + j, PALETTE_COLOR_UNUSED);
				}
			}
			else
				memset(new UBytePtr(palette_used_colors, i * 16),PALETTE_COLOR_UNUSED,16);
		}
	
		palette_recalc ();
	}
	
	
	
	static int[] 	layer_scrollx = new int[4];
	static int[] 	layer_scrolly = new int[4];
	
	static void toaplan1_find_tiles(int xoffs,int yoffs)
	{
		int priority;
		int layer;
		tile_struct tinfo;
		UBytePtr t_info;
	
		if (toaplan1_flipscreen != 0){
			layer_scrollx[0] = ((scrollregs[0]) >> 7) + (523 - xoffs);
			layer_scrollx[1] = ((scrollregs[2]) >> 7) + (525 - xoffs);
			layer_scrollx[2] = ((scrollregs[4]) >> 7) + (527 - xoffs);
			layer_scrollx[3] = ((scrollregs[6]) >> 7) + (529 - xoffs);
	
			layer_scrolly[0] = ((scrollregs[1]) >> 7) +	(256 - yoffs);
			layer_scrolly[1] = ((scrollregs[3]) >> 7) + (256 - yoffs);
			layer_scrolly[2] = ((scrollregs[5]) >> 7) + (256 - yoffs);
			layer_scrolly[3] = ((scrollregs[7]) >> 7) + (256 - yoffs);
		}else{
			layer_scrollx[0] = ((scrollregs[0]) >> 7) +(495 - xoffs + 6);
			layer_scrollx[1] = ((scrollregs[2]) >> 7) +(495 - xoffs + 4);
			layer_scrollx[2] = ((scrollregs[4]) >> 7) +(495 - xoffs + 2);
			layer_scrollx[3] = ((scrollregs[6]) >> 7) +(495 - xoffs);
	
			layer_scrolly[0] = ((scrollregs[1]) >> 7) +	(0x101 - yoffs);
			layer_scrolly[1] = ((scrollregs[3]) >> 7) + (0x101 - yoffs);
			layer_scrolly[2] = ((scrollregs[5]) >> 7) + (0x101 - yoffs);
			layer_scrolly[3] = ((scrollregs[7]) >> 7) + (0x101 - yoffs);
		}
	
		for ( layer = 3 ; layer >= 0 ; layer-- )
		{
			int scrolly,scrollx,offsetx,offsety;
			int sx,sy,tattr;
			int i;
	
			t_info = new UBytePtr(toaplan1_videoram3, layer * VIDEORAM3_SIZE);
			scrollx = layer_scrollx[layer];
			offsetx = scrollx / 8 ;
			scrolly = layer_scrolly[layer];
			offsety = scrolly / 8 ;
	
			for ( sy = 0 ; sy < 32 ; sy++ )
			{
				for ( sx = 0 ; sx <= 40 ; sx++ )
				{
					i = ((sy+offsety)&0x3f)*256 + ((sx+offsetx)&0x3f)*4 ;
					tattr = t_info.READ_WORD(i);
					priority = (tattr >> 12);
	
					tinfo = (bg_list[layer][sy*41+sx]) ;
					tinfo.tile_num = t_info.READ_WORD(i+2) ;
					tinfo.priority = priority ;
					tinfo.color = tattr & 0x3f ;
					tinfo.xpos = (sx*8)-(scrollx&0x7) ;
					tinfo.ypos = (sy*8)-(scrolly&0x7) ;
	
					if ( (priority!=0) || (layer == 0) )	/* if priority 0 draw layer 0 only */
					{
	
						tinfo = (tile_list[priority][tile_count[priority]]) ;
						tinfo.tile_num = t_info.READ_WORD(i+2) ;
						if ( (tinfo.tile_num & 0x8000) == 0 )
						{
							tinfo.priority = priority ;
							tinfo.color = tattr & 0x3f ;
							tinfo.color |= layer<<8;
							tinfo.xpos = (sx*8)-(scrollx&0x7) ;
							tinfo.ypos = (sy*8)-(scrolly&0x7) ;
							tile_count[priority]++ ;
							if(tile_count[priority]==max_list_size[priority])
							{
								/*reallocate tile_list[priority] to larger size */
								temp_list= new tile_struct[(max_list_size[priority]+512)] ;
								//memcpy(temp_list,tile_list[priority],max_list_size[priority]);
                                                                for (int _i=0 ; _i<max_list_size[priority] ; _i++)
                                                                    temp_list[_i] = tile_list[priority][_i];
								max_list_size[priority]+=512;
								tile_list[priority]=null;
								tile_list[priority] = temp_list ;
							}
						}
                                                //(tile_list[priority][tile_count[priority]]) = tinfo;
					}
                                        
                                        //(bg_list[layer][sy*41+sx]) = tinfo;
				}
			}
		}
		for ( layer = 3 ; layer >= 0 ; layer-- )
		{
			layer_scrollx[layer] &= 0x7;
			layer_scrolly[layer] &= 0x7;
		}
	}
	
	static void rallybik_find_tiles()
	{
		int priority;
		int layer;
		tile_struct tinfo;
		UBytePtr t_info;
	
		for ( priority = 0 ; priority < 16 ; priority++ )
		{
			tile_count[priority]=0;
		}
	
		for ( layer = 3 ; layer >= 0 ; layer-- )
		{
			int scrolly,scrollx,offsetx,offsety;
			int sx,sy,tattr;
			int i;
	
			t_info = new UBytePtr(toaplan1_videoram3, layer * VIDEORAM3_SIZE);
			scrollx = scrollregs[layer*2];
			scrolly = scrollregs[(layer*2)+1];
	
			scrollx >>= 7 ;
			scrollx += 43 ;
			if ( layer == 0 ) scrollx += 2 ;
			if ( layer == 2 ) scrollx -= 2 ;
			if ( layer == 3 ) scrollx -= 4 ;
			offsetx = scrollx / 8 ;
	
			scrolly >>= 7 ;
			scrolly += 21 ;
			offsety = scrolly / 8 ;
	
			for ( sy = 0 ; sy < 32 ; sy++ )
			{
				for ( sx = 0 ; sx <= 40 ; sx++ )
				{
					i = ((sy+offsety)&0x3f)*256 + ((sx+offsetx)&0x3f)*4 ;
					tattr = t_info.READ_WORD(i);
					priority = tattr >> 12 ;
					if ( (priority!=0) || (layer == 0) )	/* if priority 0 draw layer 0 only */
					{
						tinfo = (tile_list[priority][tile_count[priority]]) ;
						tinfo.tile_num = t_info.READ_WORD(i+2) ;
	
						if ( !((priority!=0) && (tinfo.tile_num & 0x8000)!=0) )
						{
							tinfo.tile_num &= 0x3fff ;
							tinfo.color = tattr & 0x3f ;
							tinfo.xpos = (sx*8)-(scrollx&0x7) ;
							tinfo.ypos = (sy*8)-(scrolly&0x7) ;
							tile_count[priority]++ ;
							if(tile_count[priority]==max_list_size[priority])
							{
								/*reallocate tile_list[priority] to larger size */
								temp_list= new tile_struct[max_list_size[priority]+512] ;
								//memcpy(temp_list,tile_list[priority],sizeof(tile_struct)*max_list_size[priority]);
                                                                for (int _i=0 ; _i<max_list_size[priority] ; _i++)
                                                                    temp_list[_i] = tile_list[priority][_i];
								max_list_size[priority]+=512;
								tile_list[priority]=null;
								tile_list[priority] = temp_list ;
							}
						}
                                                //(tile_list[priority][tile_count[priority]]) = tinfo;
					}
				}
			}
		}
	}
	
	static int toaplan_sp_ram_dump = 0;
	
	static void toaplan1_find_sprites ()
	{
		int priority;
		int sprite;
		UBytePtr s_info, s_size;
	
	
		for ( priority = 0 ; priority < 17 ; priority++ )
		{
			tile_count[priority]=0;
		}
	
	
/*TODO*///	#if 0		// sp ram dump start
/*TODO*///		s_size = (toaplan1_buffered_videoram2);		/* sprite block size */
/*TODO*///		s_info = (toaplan1_buffered_videoram1);		/* start of sprite ram */
/*TODO*///		if( (toaplan_sp_ram_dump == 0)
/*TODO*///		 && (keyboard_pressed(KEYCODE_N)) )
/*TODO*///		{
/*TODO*///			toaplan_sp_ram_dump = 1;
/*TODO*///			for ( sprite = 0 ; sprite < 256 ; sprite++ )
/*TODO*///			{
/*TODO*///				int tattr,tchar;
/*TODO*///				tchar = READ_WORD (&s_info[0]) & 0xffff;
/*TODO*///				tattr = READ_WORD (&s_info[2]);
/*TODO*///				logerror("%08x: %04x %04x\n", sprite, tattr, tchar);
/*TODO*///				s_info += 8 ;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	#endif		// end
	
		s_size = new UBytePtr(toaplan1_buffered_videoram2);		/* sprite block size */
		s_info = new UBytePtr(toaplan1_buffered_videoram1);		/* start of sprite ram */
	
		for ( sprite = 0 ; sprite < 256 ; sprite++ )
		{
			int tattr,tchar;
	
			tchar = s_info.READ_WORD (0) & 0xffff;
			tattr = s_info.READ_WORD (2);
	
			if ( (tattr & 0xf000)!=0 && ((tchar & 0x8000) == 0) )
			{
				int sx,sy,dx,dy,s_sizex,s_sizey;
				int sprite_size_ptr;
	
				sx=s_info.READ_WORD(4);
				sx >>= 7 ;
				if ( sx > 416 ) sx -= 512 ;
	
				sy=s_info.READ_WORD(6);
				sy >>= 7 ;
				if ( sy > 416 ) sy -= 512 ;
	
				priority = (tattr >> 12);
	
				sprite_size_ptr = (tattr>>6)&0x3f ;
				s_sizey = (s_size.READ_WORD(2*sprite_size_ptr)>>4)&0xf ;
				s_sizex = (s_size.READ_WORD(2*sprite_size_ptr))&0xf ;
	
				for ( dy = s_sizey ; dy > 0 ; dy-- )
				for ( dx = s_sizex; dx > 0 ; dx-- )
				{
					tile_struct tinfo;
	
					tinfo = (tile_list[16][tile_count[16]]) ;
					tinfo.priority = priority;
					tinfo.tile_num = tchar;
					tinfo.color = 0x80 | (tattr & 0x3f) ;
					tinfo.xpos = sx-dx*8+s_sizex*8 ;
					tinfo.ypos = sy-dy*8+s_sizey*8 ;
					tile_count[16]++ ;
					if(tile_count[16]==max_list_size[16])
					{
						/*reallocate tile_list[priority] to larger size */
						temp_list= new tile_struct[max_list_size[16]+512];
						//memcpy(temp_list,tile_list[16],sizeof(tile_struct)*max_list_size[16]);
                                                for (int _i=0 ; _i<max_list_size[16] ; _i++)
                                                    temp_list[_i] = tile_list[16][_i];
						max_list_size[16]+=512;
						tile_list[16]=null;
						tile_list[16] = temp_list ;
					}
					tchar++;
                                        
                                        //tile_list[16][tile_count[16]] = tinfo;
				}
			}
			s_info.inc(8);
		}
	}
	
	static void rallybik_find_sprites ()
	{
		int offs;
		int tattr;
		int sx,sy,tchar;
		int priority;
		tile_struct tinfo;
	
		for (offs = 0;offs < spriteram_size[0];offs += 8)
		{
			tattr = buffered_spriteram.READ_WORD(offs+2);
			if (tattr != 0)	/* no need to render hidden sprites */
			{
				sx=buffered_spriteram.READ_WORD(offs+4);
				sx >>= 7 ;
				sx &= 0x1ff ;
				if ( sx > 416 ) sx -= 512 ;
	
				sy=buffered_spriteram.READ_WORD(offs+6);
				sy >>= 7 ;
				sy &= 0x1ff ;
				if ( sy > 416 ) sy -= 512 ;
	
				priority = (tattr>>8) & 0xc ;
				tchar = buffered_spriteram.READ_WORD(offs+0);
				tinfo = (tile_list[priority][tile_count[priority]]) ;
				tinfo.tile_num = tchar & 0x7ff ;
				tinfo.color = 0x80 | (tattr&0x3f) ;
				tinfo.color |= (tattr & 0x0100) ;
				tinfo.color |= (tattr & 0x0200) ;
				if ((tinfo.color & 0x0100)!=0) sx -= 15;
	
				tinfo.xpos = sx-31 ;
				tinfo.ypos = sy-16 ;
				tile_count[priority]++ ;
				if(tile_count[priority]==max_list_size[priority])
				{
					/*reallocate tile_list[priority] to larger size */
					temp_list= new tile_struct[max_list_size[priority]+512] ;
					//memcpy(temp_list,tile_list[priority],sizeof(tile_struct)*max_list_size[priority]);
                                        for (int _i=0 ; _i<max_list_size[priority] ; _i++)
                                            temp_list[_i] = tile_list[priority][_i];
					max_list_size[priority]+=512;
					tile_list[priority]=null;
					tile_list[priority] = temp_list ;
				}
                                
                                //tile_list[priority][tile_count[priority]] = tinfo;
			}  // if tattr
		} // for sprite
	}
	
	
	static void toaplan1_fillbgmask(osd_bitmap dest_bmp, osd_bitmap source_bmp,
	 rectangle clip,int transparent_color)
	{
		rectangle myclip = new rectangle();
		int sx=0;
		int sy=0;
	
		if ((Machine.orientation & ORIENTATION_SWAP_XY) != 0)
		{
			int temp;
	
			/* clip and myclip might be the same, so we need a temporary storage */
			temp = clip.min_x;
			myclip.min_x = clip.min_y;
			myclip.min_y = temp;
			temp = clip.max_x;
			myclip.max_x = clip.max_y;
			myclip.max_y = temp;
			clip = new rectangle(myclip);
		}
		if ((Machine.orientation & ORIENTATION_FLIP_X) != 0)
		{
			int temp;
	
			sx = -sx;
	
			/* clip and myclip might be the same, so we need a temporary storage */
			temp = clip.min_x;
			myclip.min_x = dest_bmp.width-1 - clip.max_x;
			myclip.max_x = dest_bmp.width-1 - temp;
			myclip.min_y = clip.min_y;
			myclip.max_y = clip.max_y;
			clip = new rectangle(myclip);
		}
		if ((Machine.orientation & ORIENTATION_FLIP_Y) != 0)
		{
			int temp;
	
			sy = -sy;
	
			myclip.min_x = clip.min_x;
			myclip.max_x = clip.max_x;
			/* clip and myclip might be the same, so we need a temporary storage */
			temp = clip.min_y;
			myclip.min_y = dest_bmp.height-1 - clip.max_y;
			myclip.max_y = dest_bmp.height-1 - temp;
			clip = new rectangle(myclip);
		}
	
		if (dest_bmp.depth != 16)
		{
			int ex = sx+source_bmp.width;
			int ey = sy+source_bmp.height;
	
			if( sx < clip.min_x)
			{ /* clip left */
				sx = clip.min_x;
			}
			if( sy < clip.min_y )
			{ /* clip top */
				sy = clip.min_y;
			}
			if( ex > clip.max_x+1 )
			{ /* clip right */
				ex = clip.max_x + 1;
			}
			if( ey > clip.max_y+1 )
			{ /* clip bottom */
				ey = clip.max_y + 1;
			}
	
			if( ex>sx )
			{ /* skip if inner loop doesn't draw anything */
				int y;
				for( y=sy; y<ey; y++ )
				{
					UBytePtr dest = new UBytePtr(dest_bmp.line[y]);
					UBytePtr source = new UBytePtr(source_bmp.line[y]);
					int x;
	
					for( x=sx; x<ex; x++ )
					{
						int c = source.read(x);
						if( c != transparent_color )
							dest.write(x, transparent_color);
					}
				}
			}
		}
	
		else
		{
			int ex = sx+source_bmp.width;
			int ey = sy+source_bmp.height;
	
			if( sx < clip.min_x)
			{ /* clip left */
				sx = clip.min_x;
			}
			if( sy < clip.min_y )
			{ /* clip top */
				sy = clip.min_y;
			}
			if( ex > clip.max_x+1 )
			{ /* clip right */
				ex = clip.max_x + 1;
			}
			if( ey > clip.max_y+1 )
			{ /* clip bottom */
				ey = clip.max_y + 1;
			}
	
			if( ex>sx )
			{ /* skip if inner loop doesn't draw anything */
				int y;
	
				for( y=sy; y<ey; y++ )
				{
					UShortPtr dest = new UShortPtr(dest_bmp.line[y]);
					UBytePtr source = new UBytePtr(source_bmp.line[y]);
					int x;
	
					for( x=sx; x<ex; x++ )
					{
						int c = source.read(x);
						if( c != transparent_color )
							dest.write(x, (char) transparent_color);
					}
				}
			}
		}
	}
	
	
	//#define BGDBG
/*TODO*///	#ifdef BGDBG
/*TODO*///	int	toaplan_dbg_priority = 0;
/*TODO*///	#endif
	
	
	static void toaplan1_render (osd_bitmap bitmap)
	{
		int i,j;
		int priority,pen;
		int	flip;
		tile_struct tinfo;
		tile_struct tinfo2;
		rectangle sp_rect = new rectangle();
	
		fillbitmap (bitmap, palette_transparent_pen, Machine.visible_area);
	
/*TODO*///	#ifdef BGDBG
/*TODO*///	
/*TODO*///	if (keyboard_pressed(KEYCODE_Q)) { toaplan_dbg_priority = 0; }
/*TODO*///	if (keyboard_pressed(KEYCODE_W)) { toaplan_dbg_priority = 1; }
/*TODO*///	if (keyboard_pressed(KEYCODE_E)) { toaplan_dbg_priority = 2; }
/*TODO*///	if (keyboard_pressed(KEYCODE_R)) { toaplan_dbg_priority = 3; }
/*TODO*///	if (keyboard_pressed(KEYCODE_T)) { toaplan_dbg_priority = 4; }
/*TODO*///	if (keyboard_pressed(KEYCODE_Y)) { toaplan_dbg_priority = 5; }
/*TODO*///	if (keyboard_pressed(KEYCODE_U)) { toaplan_dbg_priority = 6; }
/*TODO*///	if (keyboard_pressed(KEYCODE_I)) { toaplan_dbg_priority = 7; }
/*TODO*///	if (keyboard_pressed(KEYCODE_A)) { toaplan_dbg_priority = 8; }
/*TODO*///	if (keyboard_pressed(KEYCODE_S)) { toaplan_dbg_priority = 9; }
/*TODO*///	if (keyboard_pressed(KEYCODE_D)) { toaplan_dbg_priority = 10; }
/*TODO*///	if (keyboard_pressed(KEYCODE_F)) { toaplan_dbg_priority = 11; }
/*TODO*///	if (keyboard_pressed(KEYCODE_G)) { toaplan_dbg_priority = 12; }
/*TODO*///	if (keyboard_pressed(KEYCODE_H)) { toaplan_dbg_priority = 13; }
/*TODO*///	if (keyboard_pressed(KEYCODE_J)) { toaplan_dbg_priority = 14; }
/*TODO*///	if (keyboard_pressed(KEYCODE_K)) { toaplan_dbg_priority = 15; }
/*TODO*///	
/*TODO*///	if( toaplan_dbg_priority != 0 ){
/*TODO*///	
/*TODO*///		priority = toaplan_dbg_priority;
/*TODO*///		{
/*TODO*///			tinfo = (tile_struct *)&(tile_list[priority][0]) ;
/*TODO*///			/* hack to fix black blobs in Demon's World sky */
/*TODO*///			pen = TRANSPARENCY_NONE ;
/*TODO*///			for ( i = 0 ; i < tile_count[priority] ; i++ ) /* draw only tiles in list */
/*TODO*///			{
/*TODO*///				/* hack to fix blue blobs in Zero Wing attract mode */
/*TODO*///	//			if ((pen == TRANSPARENCY_NONE) && ((tinfo.color&0x3f)==0))
/*TODO*///	//				pen = TRANSPARENCY_PEN ;
/*TODO*///				drawgfx(bitmap,Machine.gfx[0],
/*TODO*///					tinfo.tile_num,
/*TODO*///					(tinfo.color&0x3f),
/*TODO*///					0,0,						/* flipx,flipy */
/*TODO*///					tinfo.xpos,tinfo.ypos,
/*TODO*///					&Machine.visible_area,pen,0);
/*TODO*///				tinfo++ ;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	
/*TODO*///	}else{
/*TODO*///	
/*TODO*///	#endif
	
	//	if (toaplan1_flipscreen != 0)
	//		flip = 1;
	//	else
			flip = 0;
	//
		priority = 0;
		while ( priority < 16 )			/* draw priority layers in order */
		{
			int	layer;
	
			tinfo = (tile_list[priority][0]) ;
                        int _tinfo=0;
                        
			layer = (tinfo.color >> 8);
			if ( (layer < 3) && (priority < 2))
				pen = TRANSPARENCY_NONE ;
			else
				pen = TRANSPARENCY_PEN ;
	
			for ( i = 0 ; i < tile_count[priority] ; i++ ) /* draw only tiles in list */
			{
				int	xpos,ypos;
	
				/* hack to fix blue blobs in Zero Wing attract mode */
	//			if ((pen == TRANSPARENCY_NONE) && ((tinfo.color&0x3f)==0))
	//				pen = TRANSPARENCY_PEN ;
	
	//			if (flip != 0){
	//				xpos = tinfo.xpos;
	//				ypos = tinfo.ypos;
	//			}
	//			else{
					xpos = tinfo.xpos;
					ypos = tinfo.ypos;
	//			}
	
				drawgfx(bitmap,Machine.gfx[0],
					tinfo.tile_num,
					(tinfo.color&0x3f),
					flip,flip,							/* flipx,flipy */
					tinfo.xpos,tinfo.ypos,
					Machine.visible_area,pen,0);
                                
                                //tile_list[priority][0 + _tinfo] = tinfo;
				_tinfo++ ;
                                tinfo = tile_list[priority][0 + _tinfo];
			}
			priority++;
                        
                        //tile_list[priority][0 + _tinfo] = tinfo;
		}
	
		tinfo2 = (tile_list[16][0]) ;
                int _tinfo2=0;
                
		for ( i = 0; i < tile_count[16]; i++ )	/* draw sprite No. in order */
		{
			int	flipx,flipy;
	
			sp_rect.min_x = tinfo2.xpos;
			sp_rect.min_y = tinfo2.ypos;
			sp_rect.max_x = tinfo2.xpos + 7;
			sp_rect.max_y = tinfo2.ypos + 7;
	
			fillbitmap (tmpbitmap2, palette_transparent_pen, sp_rect);
	
			flipx = (tinfo2.color & 0x0100);
			flipy = (tinfo2.color & 0x0200);
	//		if (toaplan1_flipscreen != 0){
	//			flipx = !flipx;
	//			flipy = !flipy;
	//		}
			drawgfx(tmpbitmap2,Machine.gfx[1],
				tinfo2.tile_num,
				(tinfo2.color&0x3f), 			/* bit 7 not for colour */
				flipx,flipy,					/* flipx,flipy */
				tinfo2.xpos,tinfo2.ypos,
				Machine.visible_area,TRANSPARENCY_PEN,0);
	
			priority = tinfo2.priority;
			{
			int ix0,ix1,ix2,ix3;
			int dirty;
	
			dirty = 0;
			fillbitmap (tmpbitmap3, palette_transparent_pen, sp_rect);
			for ( j = 0 ; j < 4 ; j++ )
			{
				int x,y;
	
				y = tinfo2.ypos+layer_scrolly[j];
				x = tinfo2.xpos+layer_scrollx[j];
				ix0 = ( y   /8) * 41 +  x   /8;
				ix1 = ( y   /8) * 41 + (x+7)/8;
				ix2 = ((y+7)/8) * 41 +  x   /8;
				ix3 = ((y+7)/8) * 41 + (x+7)/8;
	
				if(	(ix0 >= 0) && (ix0 < 32*41) ){
					tinfo = (bg_list[j][ix0]) ;
					if( (tinfo.priority >= tinfo2.priority) ){
						drawgfx(tmpbitmap3,Machine.gfx[0],
	//					drawgfx(tmpbitmap2,Machine.gfx[0],
							tinfo.tile_num,
							(tinfo.color&0x3f),
							flip,flip,
							tinfo.xpos,tinfo.ypos,
							sp_rect,TRANSPARENCY_PEN,0);
						dirty=1;
					}
                                        //(bg_list[j][ix0]) = tinfo;
				}
				if(	(ix1 >= 0) && (ix1 < 32*41) ){
					tinfo = (bg_list[j][ix1]) ;
	//				tinfo++;
					if( (ix0 != ix1)
					 && (tinfo.priority >= tinfo2.priority) ){
						drawgfx(tmpbitmap3,Machine.gfx[0],
	//					drawgfx(tmpbitmap2,Machine.gfx[0],
							tinfo.tile_num,
							(tinfo.color&0x3f),
							flip,flip,
							tinfo.xpos,tinfo.ypos,
							sp_rect,TRANSPARENCY_PEN,0);
						dirty=1;
					}
                                        //(bg_list[j][ix1]) = tinfo;
				}
				if(	(ix2 >= 0) && (ix2 < 32*41) ){
					tinfo = (bg_list[j][ix2]) ;
	//				tinfo += 40;
					if( (ix0 != ix2)
					 && (tinfo.priority >= tinfo2.priority) ){
						drawgfx(tmpbitmap3,Machine.gfx[0],
	//					drawgfx(tmpbitmap2,Machine.gfx[0],
							tinfo.tile_num,
							(tinfo.color&0x3f),
							flip,flip,
							tinfo.xpos,tinfo.ypos,
							sp_rect,TRANSPARENCY_PEN,0);
						dirty=1;
					}
                                        //(bg_list[j][ix2]) = tinfo;
				}
				if(	(ix3 >= 0) && (ix3 < 32*41) ){
					tinfo = (bg_list[j][ix3]) ;
	//				tinfo++;
					if( (ix0 != ix3) && (ix1 != ix3) && (ix2 != ix3)
					 && (tinfo.priority >= tinfo2.priority) ){
						drawgfx(tmpbitmap3,Machine.gfx[0],
	//					drawgfx(tmpbitmap2,Machine.gfx[0],
							tinfo.tile_num,
							(tinfo.color&0x3f),
							flip,flip,
							tinfo.xpos,tinfo.ypos,
							sp_rect,TRANSPARENCY_PEN,0);
						dirty=1;
					}
                                        //(bg_list[j][ix3]) = tinfo;
				}
			}
			if(	dirty != 0 )
			{
				toaplan1_fillbgmask(
					tmpbitmap2,				// dist
					tmpbitmap3,				// mask
					sp_rect,
					palette_transparent_pen
				);
			}
			copybitmap(bitmap, tmpbitmap2, 0, 0, 0, 0, sp_rect, TRANSPARENCY_PEN, palette_transparent_pen);
                        //tile_list[16][0 + _tinfo2] = tinfo2;
			_tinfo2++;
                        tinfo2 = tile_list[16][0 + _tinfo2];
			}
		}
                //ile_list[16][0 + _tinfo2] = tinfo2;
	
/*TODO*///	#ifdef BGDBG
/*TODO*///	}
/*TODO*///	#endif
	
	}
	
	
	static void rallybik_render (osd_bitmap bitmap)
	{
		int i;
		int priority,pen;
		tile_struct tinfo;
	
		fillbitmap (bitmap, palette_transparent_pen, Machine.visible_area);
	
		for ( priority = 0 ; priority < 16 ; priority++ )	/* draw priority layers in order */
		{
			tinfo = (tile_list[priority][0]) ;
                        int _tinfo=0;
			/* hack to fix black blobs in Demon's World sky */
			if ( priority == 1 )
				pen = TRANSPARENCY_NONE ;
			else
				pen = TRANSPARENCY_PEN ;
			for ( i = 0 ; i < tile_count[priority] ; i++ ) /* draw only tiles in list */
			{
				/* hack to fix blue blobs in Zero Wing attract mode */
				if ((pen == TRANSPARENCY_NONE) && ((tinfo.color&0x3f)==0))
					pen = TRANSPARENCY_PEN ;
	
				drawgfx(bitmap,Machine.gfx[(tinfo.color>>7)&1],	/* bit 7 set for sprites */
					tinfo.tile_num,
					(tinfo.color&0x3f), 			/* bit 7 not for colour */
					(tinfo.color & 0x0100),(tinfo.color & 0x0200),	/* flipx,flipy */
					tinfo.xpos,tinfo.ypos,
					Machine.visible_area,pen,0);
                                //(tile_list[priority][0 + _tinfo]) = tinfo;
				_tinfo++ ;
                                tinfo = (tile_list[priority][0 + _tinfo]) ;
			}
                        
                        tinfo = (tile_list[priority][0 + _tinfo]) ;
		}
	}
	
	
	public static VhUpdatePtr toaplan1_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		/* discover what data will be drawn */
		toaplan1_find_sprites();
		toaplan1_find_tiles(tiles_offsetx,tiles_offsety);
	
		toaplan1_update_palette();
		toaplan1_render(bitmap);
	} };
	
	public static VhUpdatePtr rallybik_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		/* discover what data will be drawn */
		rallybik_find_tiles();
		rallybik_find_sprites();
	
		toaplan1_update_palette();
		rallybik_render(bitmap);
	} };
	
	
	
	/****************************************************************************
		Spriteram is always 1 frame ahead, suggesting spriteram buffering.
		There are no CPU output registers that control this so we
		assume it happens automatically every frame, at the end of vblank
	****************************************************************************/
	
	public static VhEofCallbackPtr toaplan1_eof_callback = new VhEofCallbackPtr() { public void handler() 
	{
		memcpy(toaplan1_buffered_videoram1,toaplan1_videoram1,VIDEORAM1_SIZE);
		memcpy(toaplan1_buffered_videoram2,toaplan1_videoram2,VIDEORAM2_SIZE);
	} };
	
	public static VhEofCallbackPtr rallybik_eof_callback = new VhEofCallbackPtr() { public void handler() 
	{
		buffer_spriteram_w.handler(0,0);
	} };
	
	public static VhEofCallbackPtr samesame_eof_callback = new VhEofCallbackPtr() { public void handler() 
	{
		memcpy(toaplan1_buffered_videoram1,toaplan1_videoram1,VIDEORAM1_SIZE);
		memcpy(toaplan1_buffered_videoram2,toaplan1_videoram2,VIDEORAM2_SIZE);
		cpu_set_irq_line(0, MC68000_IRQ_2, HOLD_LINE);  /* Frame done */
	} };
	
}
