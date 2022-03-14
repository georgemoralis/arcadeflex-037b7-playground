/***************************************************************************

  vidhrdw/toobin.c

  Functions to emulate the video hardware of the machine.

****************************************************************************

	Playfield encoding
	------------------
		2 16-bit words are used

		Word 1:
			Bits  4-7  = priority of the image
			Bits  0-3  = palette

		Word 2:
			Bit  15    = vertical flip
			Bit  14    = horizontal flip
			Bits  0-13 = image index


	Motion Object encoding
	----------------------
		4 16-bit words are used

		Word 1:
			Bit  15    = absolute/relative positioning (1 = absolute)
			Bits  6-14 = Y position
			Bits  3-5  = height in tiles
			Bits  0-2  = width in tiles

		Word 2:
			Bit  15    = vertical flip
			Bit  14    = horizontal flip
			Bits  0-13 = image index

		Word 3:
			Bits 12-15 = priority (only upper 2 bits used)
			Bits  0-7  = link to the next image to display

		Word 4:
			Bits  6-15 = X position
			Bits  0-3  = palette


	Alpha layer encoding
	--------------------
		1 16-bit word is used

		Word 1:
			Bit  12-15 = palette
			Bit  11    = horizontal flip
			Bits  0-10 = image index

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;
        
import static arcadeflex.WIP.v037b7.machine.atarigen.*;
import static arcadeflex.WIP.v037b7.machine.atarigenH.*;        
import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.subArrays;
import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.generic.funcPtr.*;
import arcadeflex.v037b7.mame.drawgfxH;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v056.mame.timer;
import arcadeflex.v056.mame.timer.timer_callback;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.common.libc.cstring.memset;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.copyscrollbitmap;        

public class toobin
{
	
	static int XCHARS = 64;
	static int YCHARS = 48;
	
	static int XDIM = (XCHARS*8);
	static int YDIM = (YCHARS*8);
	
	
	
	/*************************************
	 *
	 *	Globals we own
	 *
	 *************************************/
	
	public static UBytePtr toobin_intensity = new UBytePtr();
	public static UBytePtr toobin_moslip = new UBytePtr();
	
	
	
	/*************************************
	 *
	 *	Statics
	 *
	 *************************************/
	
	static atarigen_pf_state pf_state = new atarigen_pf_state();
	static int last_intensity;
	
	
	
	
	
	/*************************************
	 *
	 *	Video system start
	 *
	 *************************************/
	
	public static VhStartPtr toobin_vh_start = new VhStartPtr() { public int handler() 
	{
		atarigen_mo_desc mo_desc = new atarigen_mo_desc
		(
			256,                 /* maximum number of MO's */
			8,                   /* number of bytes per MO entry */
			2,                   /* number of bytes between MO words */
			2,                   /* ignore an entry if this word == 0xffff */
			2, 0, 0xff,          /* link = (data[linkword] >> linkshift) & linkmask */
			0                    /* render in reverse link order */
		);
	
		atarigen_pf_desc pf_desc = new atarigen_pf_desc
		(
			8, 8,				/* width/height of each tile */
			128, 64,				/* number of tiles in each direction */
                        0
		);
	
		/* reset statics */
		last_intensity = 0;
	
		/* initialize the playfield */
		if (atarigen_pf_init(pf_desc) != 0)
			return 1;
	
		/* initialize the motion objects */
		if (atarigen_mo_init(mo_desc) != 0)
		{
			atarigen_pf_free();
			return 1;
		}
	
		return 0;
	} };
	
	
	
	/*************************************
	 *
	 *	Video system shutdown
	 *
	 *************************************/
	
	public static VhStopPtr toobin_vh_stop = new VhStopPtr() { public void handler() 
	{
		atarigen_pf_free();
		atarigen_mo_free();
	} };
	
	
	
	/*************************************
	 *
	 *	Playfield RAM write handler
	 *
	 *************************************/
	
	public static WriteHandlerPtr toobin_playfieldram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = atarigen_playfieldram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
	
		if (oldword != newword)
		{
			atarigen_playfieldram.WRITE_WORD(offset, newword);
			atarigen_pf_dirty.write(offset / 4, 1);
		}
	} };
	
	
	
	/*************************************
	 *
	 *	Palette RAM write handler
	 *
	 *************************************/
	
	public static WriteHandlerPtr toobin_paletteram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = paletteram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
		paletteram.WRITE_WORD(offset, newword);
	
		{
			int red =   (((newword >> 10) & 31) * 224) >> 5;
			int green = (((newword >>  5) & 31) * 224) >> 5;
			int blue =  (((newword      ) & 31) * 224) >> 5;
	
			if (red != 0) red += 38;
			if (green != 0) green += 38;
			if (blue != 0) blue += 38;
	
			if ((newword & 0x8000)==0)
			{
				red = (red * last_intensity) >> 5;
				green = (green * last_intensity) >> 5;
				blue = (blue * last_intensity) >> 5;
			}
	
			palette_change_color((offset / 2) & 0x3ff, red, green, blue);
		}
	} };
	
	
	
	/*************************************
	 *
	 *	Periodic scanline updater
	 *
	 *************************************/
	
	public static timer_callback toobin_scanline_update = new timer_callback() {
            @Override
            public void handler(int scanline) {
                int link = toobin_moslip.READ_WORD(0) & 0xff;
	
		/* update the playfield */
		if (scanline == 0)
		{
			pf_state.hscroll = (atarigen_hscroll.READ_WORD(0) >> 6) & 0x3ff;
			pf_state.vscroll = (atarigen_vscroll.READ_WORD(0) >> 6) & 0x1ff;
			atarigen_pf_update(pf_state, scanline);
		}
	
		/* update the motion objects */
		if (scanline < YDIM)
			atarigen_mo_update(atarigen_spriteram, link, scanline);
            }
        };
        
	
	/*************************************
	 *
	 *	MO SLIP write handler
	 *
	 *************************************/
	
	public static WriteHandlerPtr toobin_moslip_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(toobin_moslip,offset, data);
		toobin_scanline_update.handler(cpu_getscanline());
	} };
	
	
	
	/*************************************
	 *
	 *	Main refresh
	 *
	 *************************************/
	
	public static VhUpdatePtr toobin_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		/* update the palette */
		if (update_palette() != null)
			memset(atarigen_pf_dirty, 1, atarigen_playfieldram_size[0] / 4);
	
		/* render the playfield */
		memset(atarigen_pf_visit, 0, 128*64);
		atarigen_pf_process(pf_render_callback, bitmap, Machine.visible_area);
	
		/* render the motion objects */
		atarigen_mo_process(mo_render_callback, bitmap);
	
		/* render the alpha layer */
		{
			GfxElement gfx = Machine.gfx[2];
			int sx, sy, offs;
	
			for (sy = 0; sy < YCHARS; sy++)
				for (sx = 0, offs = sy * 64; sx < XCHARS; sx++, offs++)
				{
					int data = atarigen_alpharam.READ_WORD(offs * 2);
					int code = data & 0x3ff;
	
					/* if there's a non-zero code, draw the tile */
					if (code != 0)
					{
						int color = (data >> 12) & 15;
						int hflip = data & 0x400;
	
						/* draw the character */
						drawgfx(bitmap, gfx, code, color, hflip, 0, 8 * sx, 8 * sy, null, TRANSPARENCY_PEN, 0);
					}
				}
		}
	
		/* update onscreen messages */
		atarigen_update_messages();
	} };
	
	
	
	/*************************************
	 *
	 *	Palette management
	 *
	 *************************************/
	
	static UBytePtr update_palette()
	{
		int[] al_map=new int[16], pf_map=new int[16], mo_map=new int[16];
		int intensity, i, j;
	
		/* compute the intensity and modify the palette if it's different */
		intensity = 0x1f - (toobin_intensity.READ_WORD(0) & 0x1f);
		if (intensity != last_intensity)
		{
			last_intensity = intensity;
			for (i = 0; i < 256+256+64; i++)
			{
				int newword = paletteram.READ_WORD(i*2);
				int red =   (((newword >> 10) & 31) * 224) >> 5;
				int green = (((newword >>  5) & 31) * 224) >> 5;
				int blue =  (((newword      ) & 31) * 224) >> 5;
	
				if (red != 0) red += 38;
				if (green != 0) green += 38;
				if (blue != 0) blue += 38;
	
				if ((newword & 0x8000)==0)
				{
					red = (red * last_intensity) >> 5;
					green = (green * last_intensity) >> 5;
					blue = (blue * last_intensity) >> 5;
				}
	
				palette_change_color(i, red, green, blue);
			}
		}
	
		/* reset color tracking */
		memset(mo_map, 0, mo_map.length);
		memset(pf_map, 0, pf_map.length);
		memset(al_map, 0, al_map.length);
		palette_init_used_colors();
	
		/* update color usage for the playfield */
		atarigen_pf_process(pf_color_callback, pf_map, Machine.visible_area);
	
		/* update color usage for the mo's */
		atarigen_mo_process(mo_color_callback, mo_map);
	
		/* update color usage for the alphanumerics */
		{
			int[] usage = Machine.gfx[2].pen_usage;
			int offs, sx, sy;
	
			for (sy = 0; sy < YCHARS; sy++)
				for (sx = 0, offs = sy * 64; sx < XCHARS; sx++, offs++)
				{
					int data = atarigen_alpharam.READ_WORD(offs * 2);
					int color = (data >> 12) & 0x000f;
					int code = data & 0x03ff;
					al_map[color] |= usage[code];
				}
		}
	
		/* rebuild the playfield palette */
		for (i = 0; i < 16; i++)
		{
			int used = pf_map[i];
			if (used != 0)
				for (j = 0; j < 16; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x000 + i * 16 + j, PALETTE_COLOR_USED);
		}
	
		/* rebuild the motion object palette */
		for (i = 0; i < 16; i++)
		{
			int used = mo_map[i];
			if (used != 0)
			{
				palette_used_colors.write(0x100 + i * 16 + 0, PALETTE_COLOR_TRANSPARENT);
				for (j = 1; j < 16; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x100 + i * 16 + j, PALETTE_COLOR_USED);
			}
		}
	
		/* rebuild the alphanumerics palette */
		for (i = 0; i < 16; i++)
		{
			int used = al_map[i];
			if (used != 0)
				for (j = 0; j < 4; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x200 + i * 4 + j, PALETTE_COLOR_USED);
		}
	
		return palette_recalc();
	}
	
	
	
	/*************************************
	 *
	 *	Playfield palette
	 *
	 *************************************/
	
	static atarigen_pf_callback pf_color_callback = new atarigen_pf_callback() {
            @Override
            public void handler(rectangle clip, rectangle tiles, atarigen_pf_state state, Object param) {
                int[] usage = Machine.gfx[0].pen_usage;
		int[] colormap = (int[]) param;
		int x, y;
	
		/* standard loop over tiles */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 127)
			{
				int offs = y * 128 + x;
				int data1 = atarigen_playfieldram.READ_WORD(offs * 4);
				int data2 = atarigen_playfieldram.READ_WORD(offs * 4 + 2);
				int color = data1 & 0x000f;
				int code = data2 & 0x3fff;
	
				/* mark the colors used by this tile */
				colormap[color] |= usage[code];
	
				/* also mark unvisited tiles dirty */
				if (atarigen_pf_visit.read(offs)==0) 
                                    atarigen_pf_dirty.write(offs, 0xff);
			}
            }
        };
        	
	
	/*************************************
	 *
	 *	Playfield rendering
	 *
	 *************************************/
	
	static atarigen_pf_callback pf_render_callback = new atarigen_pf_callback() {
            @Override
            public void handler(rectangle clip, rectangle tiles, atarigen_pf_state state, Object param) {
                GfxElement gfx = Machine.gfx[0];
		osd_bitmap bitmap = (osd_bitmap) param;
		int x, y;
	
		/* standard loop over tiles */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 127)
			{
				int offs = y * 128 + x;
	
				/* update only if dirty */
				if (atarigen_pf_dirty.read(offs) != 0)
				{
					int data1 = atarigen_playfieldram.READ_WORD(offs * 4);
					int data2 = atarigen_playfieldram.READ_WORD(offs * 4 + 2);
					int color = data1 & 0x000f;
	/*				int priority = (data1 >> 4) & 3;*/
					int vflip = data2 & 0x8000;
					int hflip = data2 & 0x4000;
					int code = data2 & 0x3fff;
	
					drawgfx(atarigen_pf_bitmap, gfx, code, color, hflip, vflip, 8 * x, 8 * y, null, TRANSPARENCY_NONE, 0);
					atarigen_pf_dirty.write(offs, 0);
				}
	
				/* track the tiles we've visited */
				atarigen_pf_visit.write(offs, 1);
			}
	
		/* then blast the result */
		x = -state.hscroll;
		y = -state.vscroll;
		copyscrollbitmap(bitmap, atarigen_pf_bitmap, 1, new int[]{x}, 1, new int[]{y}, clip, TRANSPARENCY_NONE, 0);
            }
        };
        
	
	/*************************************
	 *
	 *	Playfield overrendering
	 *
	 *************************************/
	
	static atarigen_pf_callback pf_overrender_callback = new atarigen_pf_callback() {
            @Override
            public void handler(rectangle clip, rectangle tiles, atarigen_pf_state state, Object param) {
                GfxElement gfx = Machine.gfx[0];
		osd_bitmap bitmap = (osd_bitmap) param;
		int x, y;
	
		/* standard loop over tiles */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
		{
			int sy = (8 * y - state.vscroll) & 0x1ff;
			if (sy >= YDIM) sy -= 0x200;
	
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 127)
			{
				int offs = y * 128 + x;
				int data1 = atarigen_playfieldram.READ_WORD(offs * 4);
				int priority = (data1 >> 4) & 3;
	
				/* overrender if there is a non-zero priority for this tile */
				/* not perfect, but works for the most obvious cases */
				if (priority != 0)
				{
					int data2 = atarigen_playfieldram.READ_WORD(offs * 4 + 2);
					int color = data1 & 0x000f;
					int vflip = data2 & 0x8000;
					int hflip = data2 & 0x4000;
					int code = data2 & 0x3fff;
					int sx = (8 * x - state.hscroll) & 0x1ff;
					if (sx >= XDIM) sx -= 0x400;
	
					drawgfx(bitmap, gfx, code, color, hflip, vflip, sx, sy, clip, TRANSPARENCY_PENS, 0x000000ff);
				}
			}
		}
            }
        };
        	
	
	/*************************************
	 *
	 *	Motion object palette
	 *
	 *************************************/
	
	static atarigen_mo_callback mo_color_callback = new atarigen_mo_callback() {
            @Override
            public void handler(UShortArray data, rectangle clip, Object param) {
                int []usage = Machine.gfx[1].pen_usage;
		int[] colormap = (int[]) param;
		int hsize = (data.read(0) & 7) + 1;
		int vsize = ((data.read(0) >> 3) & 7) + 1;
		int code = data.read(1) & 0x3fff;
		int color = data.read(3) & 0x000f;
		int tiles = hsize * vsize;
		int temp = 0;
		int i;
	
		for (i = 0; i < tiles; i++)
			temp |= usage[code++];
		colormap[color] |= temp;
            }
        };
        
	
	/*************************************
	 *
	 *	Motion object rendering
	 *
	 *************************************/
	
	static atarigen_mo_callback mo_render_callback = new atarigen_mo_callback() {
            @Override
            public void handler(UShortArray data, rectangle clip, Object param) {
                GfxElement gfx = Machine.gfx[1];
		osd_bitmap bitmap = (osd_bitmap) param;
		rectangle pf_clip = new rectangle();
	
		/* extract data from the various words */
		int absolute = data.read(0) & 0x8000;
		int ypos = -(data.read(0) >> 6);
		int hsize = (data.read(0) & 7) + 1;
		int vsize = ((data.read(0) >> 3) & 7) + 1;
		int vflip = data.read(1) & 0x8000;
		int hflip = data.read(1) & 0x4000;
		int code = data.read(1) & 0x3fff;
		int xpos = data.read(3) >> 6;
		int color = data.read(3) & 0x000f;
	//	int priority = (data[3] >> 4) & 3;
	
		/* adjust for height */
		ypos -= vsize * 16;
	
		/* adjust position if relative */
		if (absolute==0)
		{
			xpos -= pf_state.hscroll;
			ypos -= pf_state.vscroll;
		}
	
		/* adjust the final coordinates */
		xpos &= 0x3ff;
		ypos &= 0x1ff;
		if (xpos >= XDIM) xpos -= 0x400;
		if (ypos >= YDIM) ypos -= 0x200;
	
		/* determine the bounding box */
		pf_clip = atarigen_mo_compute_clip_16x16(xpos, ypos, hsize, vsize, clip);
	
		/* draw the motion object */
		{
			int tilex, tiley, screeny, screendx, screendy;
			int screenx = xpos;
			int starty = ypos;
			int tile = code;
	
			/* adjust for h flip */
			if (hflip != 0) {
				screenx += (hsize - 1) * 16;
                                screendx = -16;
                        } else {
				screendx = 16;
                        }
	
			/* adjust for v flip */
			if (vflip != 0) {
				starty += (vsize - 1) * 16;
                                screendy = -16;
                        } else {
				screendy = 16;
                        }
	
			/* loop over the height */
			for (tilex = 0; tilex < hsize; tilex++, screenx += screendx)
			{
				/* clip the x coordinate */
				if (screenx <= clip.min_x - 16)
				{
					tile += vsize;
					continue;
				}
				else if (screenx > clip.max_x)
					break;
	
				/* loop over the width */
				for (tiley = 0, screeny = starty; tiley < vsize; tiley++, screeny += screendy, tile++)
				{
					/* clip the y coordinate */
					if (screeny <= clip.min_y - 16 || screeny > clip.max_y)
						continue;
	
					/* draw the sprite */
					drawgfx(bitmap, gfx, tile, color, hflip, vflip, screenx, screeny, clip, TRANSPARENCY_PEN, 0);
				}
			}
		}
	
		/* overrender the playfield */
		atarigen_pf_process(pf_overrender_callback, bitmap, pf_clip);
            }
        };
        
}
