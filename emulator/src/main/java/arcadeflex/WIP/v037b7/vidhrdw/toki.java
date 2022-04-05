/***************************************************************************

  vidhrdw.c

  Functions to emulate the video hardware of the machine.

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.generic.funcPtr.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.copyscrollbitmap;
import static gr.codebb.arcadeflex.old.mame.drawgfx.copybitmap;

public class toki
{
	
	public static int SPRITE_Y		= 0;
	public static int SPRITE_TILE		= 2;
	public static int SPRITE_FLIP_X		= 2;
	public static int SPRITE_PAL_BANK	= 4;
	public static int SPRITE_X		= 6;
	
	public static int XBG1SCROLL_ADJUST(int x) { return (-(x)+0x103); }
	public static int XBG2SCROLL_ADJUST(int x) { return (-(x)+0x101); }
	public static int YBGSCROLL_ADJUST(int x) { return (-(x)-1); }
	
	public static UBytePtr toki_foreground_videoram = new UBytePtr();
	public static UBytePtr toki_background1_videoram = new UBytePtr();
	public static UBytePtr toki_background2_videoram = new UBytePtr();
	public static UBytePtr toki_sprites_dataram = new UBytePtr();
	public static UBytePtr toki_scrollram = new UBytePtr();
	public static int[] toki_linescroll = new int[256];
	
	public static int[] toki_foreground_videoram_size = new int[1];
	public static int[] toki_background1_videoram_size = new int[1];
	public static int[] toki_background2_videoram_size = new int[1];
	public static int[] toki_sprites_dataram_size = new int[1];
	
	static UBytePtr frg_dirtybuffer = new UBytePtr();		/* foreground */
	static UBytePtr bg1_dirtybuffer = new UBytePtr();		/* background 1 */
	static UBytePtr bg2_dirtybuffer = new UBytePtr();		/* background 2 */
	
	static osd_bitmap bitmap_frg;		/* foreground bitmap */
	static osd_bitmap bitmap_bg1;		/* background bitmap 1 */
	static osd_bitmap bitmap_bg2;		/* background bitmap 2 */
	
	static int bg1_scrollx, bg1_scrolly;
	static int bg2_scrollx, bg2_scrolly;
	
	
	
	/*************************************
	 *
	 *		Start/Stop
	 *
	 *************************************/
	
	public static VhStartPtr toki_vh_start = new VhStartPtr() { public int handler() 
	{
		if ((frg_dirtybuffer = new UBytePtr (toki_foreground_videoram_size[0] / 2)) == null)
		{
			return 1;
		}
		if ((bg1_dirtybuffer = new UBytePtr (toki_background1_videoram_size[0] / 2)) == null)
		{
			frg_dirtybuffer = null;
			return 1;
		}
		if ((bg2_dirtybuffer = new UBytePtr (toki_background2_videoram_size[0] / 2)) == null)
		{
			bg1_dirtybuffer = null;
			frg_dirtybuffer = null;
			return 1;
		}
	
		/* foreground bitmap */
		if ((bitmap_frg = bitmap_alloc(Machine.drv.screen_width,Machine.drv.screen_height)) == null)
		{
			bg1_dirtybuffer = null;
			bg2_dirtybuffer = null;
			frg_dirtybuffer = null;
			return 1;
		}
	
		/* background1 bitmap */
		if ((bitmap_bg1 = bitmap_alloc(Machine.drv.screen_width*2,Machine.drv.screen_height*2)) == null)
		{
			bg1_dirtybuffer = null;
			bg2_dirtybuffer = null;
			frg_dirtybuffer = null;
			bitmap_free (bitmap_frg);
			return 1;
		}
	
		/* background2 bitmap */
		if ((bitmap_bg2 = bitmap_alloc(Machine.drv.screen_width*2,Machine.drv.screen_height*2)) == null)
		{
			bg1_dirtybuffer = null;
			bg2_dirtybuffer = null;
			frg_dirtybuffer = null;
			bitmap_free (bitmap_bg1);
			bitmap_free (bitmap_frg);
			return 1;
		}
		memset (frg_dirtybuffer,1,toki_foreground_videoram_size[0] / 2);
		memset (bg2_dirtybuffer,1,toki_background1_videoram_size[0] / 2);
		memset (bg1_dirtybuffer,1,toki_background2_videoram_size[0] / 2);
		return 0;
	
	} };
	
	public static VhStopPtr toki_vh_stop = new VhStopPtr() { public void handler() 
	{
		bitmap_free (bitmap_frg);
		bitmap_free (bitmap_bg1);
		bitmap_free (bitmap_bg2);
		bg1_dirtybuffer = null;
		bg2_dirtybuffer = null;
		frg_dirtybuffer = null;
	} };
	
	
	
	/*************************************
	 *
	 *		Foreground RAM
	 *
	 *************************************/
	
	public static WriteHandlerPtr toki_foreground_videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	   int oldword = toki_foreground_videoram.READ_WORD (offset);
	   int newword = COMBINE_WORD (oldword, data);
	
	   if (oldword != newword)
	   {
			toki_foreground_videoram.WRITE_WORD (offset, data);
			frg_dirtybuffer.write(offset/2, 1);
	   }
	} };
	
	public static ReadHandlerPtr toki_foreground_videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	   return toki_foreground_videoram.READ_WORD (offset);
	} };
	
	
	
	/*************************************
	 *
	 *		Background 1 RAM
	 *
	 *************************************/
	
	public static WriteHandlerPtr toki_background1_videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	   int oldword = toki_background1_videoram.READ_WORD (offset);
	   int newword = COMBINE_WORD (oldword, data);
	
	   if (oldword != newword)
	   {
			toki_background1_videoram.WRITE_WORD (offset, data);
			bg1_dirtybuffer.write(offset/2, 1);
	   }
	} };
	
	public static ReadHandlerPtr toki_background1_videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	   return toki_background1_videoram.READ_WORD (offset);
	} };
	
	
	
	/*************************************
	 *
	 *		Background 2 RAM
	 *
	 *************************************/
	
	public static WriteHandlerPtr toki_background2_videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	   int oldword = toki_background2_videoram.READ_WORD (offset);
	   int newword = COMBINE_WORD (oldword, data);
	
	   if (oldword != newword)
	   {
			toki_background2_videoram.WRITE_WORD (offset, data);
			bg2_dirtybuffer.write(offset/2, 1);
	   }
	} };
	
	public static ReadHandlerPtr toki_background2_videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	   return toki_background2_videoram.READ_WORD (offset);
	} };
	
	
	
	/*************************************
	 *
	 *		Sprite rendering
	 *
	 *************************************/
	
	static void toki_render_sprites (osd_bitmap bitmap)
	{
		int SprX,SprY,SprTile,SprFlipX,SprPalette,offs;
		UBytePtr SprRegs;
	
		/* Draw the sprites. 256 sprites in total */
	
		for (offs = 0;offs < toki_sprites_dataram_size[0];offs += 8)
		{
			SprRegs = new UBytePtr(toki_sprites_dataram, offs);
	
			if (SprRegs.READ_WORD (SPRITE_Y)==0xf100) break;
			if (SprRegs.READ_WORD (SPRITE_PAL_BANK) != 0)
			{
	
				SprX = SprRegs.READ_WORD (SPRITE_X) & 0x1ff;
				if (SprX > 256)
					SprX -= 512;
	
				SprY = SprRegs.READ_WORD (SPRITE_Y) & 0x1ff;
				if (SprY > 256)
				  SprY = (512-SprY)+240;
				else
		       		  SprY = 240-SprY;
	
				SprFlipX   = SprRegs.READ_WORD (SPRITE_FLIP_X) & 0x4000;
				SprTile    = SprRegs.READ_WORD (SPRITE_TILE) & 0x1fff;
				SprPalette = SprRegs.READ_WORD (SPRITE_PAL_BANK)>>12;
	
				drawgfx (bitmap,Machine.gfx[1],
						SprTile,
						SprPalette,
						SprFlipX,0,
						SprX,SprY-1,
						Machine.visible_area,TRANSPARENCY_PEN,15);
			}
		}
	}
	
	
	
	/*************************************
	 *
	 *		Background rendering
	 *
	 *************************************/
	
	static void toki_draw_background1 (osd_bitmap bitmap)
	{
		int sx,sy,code,palette,offs;
	
		for (offs = 0;offs < toki_background1_videoram_size[0] / 2;offs++)
		{
			if (bg1_dirtybuffer.read(offs) != 0)
			{
				code = toki_background1_videoram.READ_WORD (offs*2);
				palette = code>>12;
				sx = (offs  % 32) << 4;
				sy = (offs >>  5) << 4;
				bg1_dirtybuffer.write(offs, 0);
				drawgfx (bitmap,Machine.gfx[2],
						code & 0xfff,
						palette,
						0,0,sx,sy,
						null,TRANSPARENCY_NONE,0);
			}
		}
	}
	
	
	static void toki_draw_background2 (osd_bitmap bitmap)
	{
		int sx,sy,code,palette,offs;
	
		for (offs = 0;offs < toki_background2_videoram_size[0] / 2;offs++)
		{
			if (bg2_dirtybuffer.read(offs) != 0)
			{
				code = toki_background2_videoram.READ_WORD (offs*2);
				palette = code>>12;
				sx = (offs  % 32) << 4;
				sy = (offs >>  5) << 4;
				bg2_dirtybuffer.write(offs, 0);
				drawgfx (bitmap,Machine.gfx[3],
						code & 0xfff,
						palette,
						0,0,sx,sy,
						null,TRANSPARENCY_NONE,0);
			}
		}
	}
	
	
	static void toki_draw_foreground (osd_bitmap bitmap)
	{
		int sx,sy,code,palette,offs;
	
		for (offs = 0;offs < toki_foreground_videoram_size[0] / 2;offs++)
		{
			if (frg_dirtybuffer.read(offs) != 0)
			{
				code = toki_foreground_videoram.READ_WORD (offs*2);
				palette = code>>12;
	
				sx = (offs % 32) << 3;
				sy = (offs >> 5) << 3;
				frg_dirtybuffer.write(offs, 0);
				drawgfx (bitmap,Machine.gfx[0],
						code & 0xfff,
						palette,
						0,0,sx,sy,
						null,TRANSPARENCY_NONE,0);
			}
		}
	}
	
	
	
	/*************************************
	 *
	 *		Master update function
	 *
	 *************************************/
	
	public static VhUpdatePtr toki_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int title_on; 			/* title on screen flag */
	
		bg1_scrolly = YBGSCROLL_ADJUST  (toki_scrollram.READ_WORD (0));
		bg1_scrollx = XBG1SCROLL_ADJUST (toki_scrollram.READ_WORD (2));
		bg2_scrolly = YBGSCROLL_ADJUST  (toki_scrollram.READ_WORD (4));
		bg2_scrollx = XBG2SCROLL_ADJUST (toki_scrollram.READ_WORD (6));
	
		/* Palette mapping first */
		{
			UShortArray palette_map = new UShortArray(16*4);
			int code, palette, offs;
	
			memset (palette_map, 0, palette_map.memory.length);
	
			for (offs = 0; offs < toki_foreground_videoram_size[0] / 2; offs++)
			{
				/* foreground */
				code = toki_foreground_videoram.READ_WORD (offs * 2);
				palette = code >> 12;
				palette_map.write(16 + palette, palette_map.read(16 + palette) | Machine.gfx[0].pen_usage[code & 0xfff]);
				/* background 1 */
				code = toki_background1_videoram.READ_WORD (offs * 2);
				palette = code >> 12;
				palette_map.write(32 + palette, palette_map.read(32 + palette) | Machine.gfx[2].pen_usage[code & 0xfff]);
				/* background 2 */
				code = toki_background2_videoram.READ_WORD (offs * 2);
				palette = code >> 12;
				palette_map.write(48 + palette, palette_map.read(48 + palette) | Machine.gfx[3].pen_usage[code & 0xfff]);
			}
	
			/* sprites */
			for (offs = 0;offs < toki_sprites_dataram_size[0];offs += 8)
			{
				UBytePtr data = new UBytePtr(toki_sprites_dataram, offs);
	
				if (data.READ_WORD (SPRITE_Y) == 0xf100)
					break;
				palette = data.READ_WORD (SPRITE_PAL_BANK);
				if (palette != 0)
				{
					code = data.READ_WORD (SPRITE_TILE) & 0x1fff;
					palette_map.write(0 + (palette >> 12), palette_map.read(0 + (palette >> 12)) | Machine.gfx[1].pen_usage[code]);
				}
			}
	
			/* expand it */
			for (palette = 0; palette < 16 * 4; palette++)
			{
				int usage = palette_map.read(palette);
	
				if (usage != 0)
				{
					int i;
	
					for (i = 0; i < 15; i++)
						if ((usage & (1 << i)) != 0)
							palette_used_colors.write(palette * 16 + i, PALETTE_COLOR_USED);
						else
							palette_used_colors.write(palette * 16 + i, PALETTE_COLOR_UNUSED);
					palette_used_colors.write(palette * 16 + 15, PALETTE_COLOR_TRANSPARENT);
				}
				else
					memset (new UBytePtr(palette_used_colors, palette * 16 + 0), PALETTE_COLOR_UNUSED, 16);
			}
	
			/* recompute */
			if (palette_recalc () != null)
			{
				memset (frg_dirtybuffer, 1, toki_foreground_videoram_size[0] / 2);
				memset (bg1_dirtybuffer, 1, toki_background1_videoram_size[0] / 2);
				memset (bg2_dirtybuffer, 1, toki_background2_videoram_size[0] / 2);
			}
		}
	
	
		title_on = (toki_foreground_videoram.READ_WORD (0x710)==0x44) ? 1:0;
	
	 	toki_draw_foreground (bitmap_frg);
		toki_draw_background1 (bitmap_bg1);
	 	toki_draw_background2 (bitmap_bg2);
	
		if (title_on != 0)
		{
			int i;
                        int[] scrollx = new int[512];
	
			for (i = 0;i < 256;i++)
				scrollx[i] = bg2_scrollx - toki_linescroll[i];
	
			copyscrollbitmap (bitmap,bitmap_bg1,1,new int[]{bg1_scrollx},1,new int[]{bg1_scrolly},Machine.visible_area,TRANSPARENCY_NONE,0);
			if (bg2_scrollx!=-32768)
				copyscrollbitmap (bitmap,bitmap_bg2,512,scrollx,1,new int[]{bg2_scrolly},Machine.visible_area,TRANSPARENCY_PEN,palette_transparent_pen);
		} else
		{
			copyscrollbitmap (bitmap,bitmap_bg2,1,new int[]{bg2_scrollx},1,new int[]{bg2_scrolly},Machine.visible_area,TRANSPARENCY_NONE,0);
			copyscrollbitmap (bitmap,bitmap_bg1,1,new int[]{bg1_scrollx},1,new int[]{bg1_scrolly},Machine.visible_area,TRANSPARENCY_PEN,palette_transparent_pen);
		}
	
		toki_render_sprites (bitmap);
	   	copybitmap (bitmap,bitmap_frg,0,0,0,0,Machine.visible_area,TRANSPARENCY_PEN,palette_transparent_pen);
	} };
	
	
	
	static int lastline,lastdata;
	
	public static WriteHandlerPtr toki_linescroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (offset == 2)
		{
			int currline;
	
			currline = cpu_getscanline();
	
			if (currline < lastline)
			{
				while (lastline < 256)
					toki_linescroll[lastline++] = lastdata;
				lastline = 0;
			}
			while (lastline < currline)
				toki_linescroll[lastline++] = lastdata;
	
			lastdata = data & 0x7f;
		}
		else
		{
			/* this is the sign, it is either 0x00 or 0xff */
			if (data != 0) lastdata |= 0x80;
		}
	} };
	
	public static InterruptPtr toki_interrupt = new InterruptPtr() { public int handler() 
	{
		while (lastline < 256)
			toki_linescroll[lastline++] = lastdata;
		lastline = 0;
		return 1;  /*Interrupt vector 1*/
	} };
}
