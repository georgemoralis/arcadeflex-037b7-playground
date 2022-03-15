/***************************************************************************

	vidhrdw/vindictr.c

	Functions to emulate the video hardware of the machine.

****************************************************************************

	Playfield encoding
	------------------
		1 16-bit word is used

		Word 1:
			Bit  15    = horizontal flip
			Bits 13-15 = palette
			Bits  0-12 = image index


	Motion Object encoding
	----------------------
		4 16-bit words are used

		Word 1:
			Bits  0-14 = image index

		Word 2:
			Bits  7-15 = X position
			Bits  0-3  = palette

		Word 3:
			Bits  7-14 = Y position
			Bit   6    = horizontal flip
			Bits  3-5  = width in tiles (ranges from 1-8)
			Bits  0-2  = height in tiles (ranges from 1-8)

		Word 4:
			Bits  0-9  = link to the next image to display


	Alpha layer encoding
	--------------------
		1 16-bit word is used

		Word 1:
			Bit  15    = transparent/opaque
			Bit  10-13 = palette
			Bits  0-9  = image index

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;
        
import static arcadeflex.WIP.v037b7.machine.atarigen.*;
import static arcadeflex.WIP.v037b7.machine.atarigenH.*;        
import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.ptrLib.UShortPtr;
import arcadeflex.common.subArrays;
import arcadeflex.common.subArrays.IntSubArray;
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

public class vindictr
{
	
	static int XCHARS = 42;
	static int YCHARS = 30;
	
	static int XDIM = (XCHARS*8);
	static int YDIM = (YCHARS*8);
	
	
	/*************************************
	 *
	 *	Statics
	 *
	 *************************************/
	
	public static class mo_data
	{
		osd_bitmap bitmap;
		int color_xor;
	};
	
	static atarigen_pf_state pf_state = new atarigen_pf_state();
	
	
	
	/*************************************
	 *
	 *	Video system start
	 *
	 *************************************/
	
	public static VhStartPtr vindictr_vh_start = new VhStartPtr() { public int handler() 
	{
		atarigen_mo_desc mo_desc = new atarigen_mo_desc
		(
			1024,                /* maximum number of MO's */
			8,                   /* number of bytes per MO entry */
			2,                   /* number of bytes between MO words */
			0,                   /* ignore an entry if this word == 0xffff */
			3, 0, 0x3ff,         /* link = (data[linkword] >> linkshift) & linkmask */
			0                    /* reverse order */
		);
	
		atarigen_pf_desc pf_desc = new atarigen_pf_desc
		(
			8, 8,				/* width/height of each tile */
			64, 64,				/* number of tiles in each direction */
                        0
		);
	
		/* reset statics */
		//memset(&pf_state, 0, sizeof(pf_state));
                pf_state = new atarigen_pf_state();
	
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
	
	public static VhStopPtr vindictr_vh_stop = new VhStopPtr() { public void handler() 
	{
		atarigen_pf_free();
		atarigen_mo_free();
	} };
	
	
	
	/*************************************
	 *
	 *	Playfield RAM write handler
	 *
	 *************************************/
	
	public static WriteHandlerPtr vindictr_playfieldram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = atarigen_playfieldram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
	
		if (oldword != newword)
		{
			atarigen_playfieldram.WRITE_WORD(offset, newword);
			atarigen_pf_dirty.write(offset / 2, 0xff);
		}
	} };
	
	
	
	/*************************************
	 *
	 *	Palette RAM write handler
	 *
	 *************************************/
	
	public static WriteHandlerPtr vindictr_paletteram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int ztable[] =
			{ 0x0, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 0x10, 0x11 };
	
		int oldword = paletteram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
		int i, r, g, b;
	
		paletteram.WRITE_WORD(offset, newword);
	
		i = ztable[(newword >> 12) & 15];
		r = ((newword >> 8) & 15) * i;
		g = ((newword >> 4) & 15) * i;
		b = ((newword >> 0) & 15) * i;
	
		offset /= 2;
		palette_change_color(offset, r, g, b);
	} };
	
	
	
	/*************************************
	 *
	 *	Periodic scanline updater
	 *
	 *************************************/
	
	public static timer_callback vindictr_scanline_update = new timer_callback() {
            @Override
            public void handler(int scanline) {
                UShortPtr base = new UShortPtr(atarigen_alpharam, ((scanline / 8) * 64 + XCHARS) * 2);
		int x;
	
		/* update the playfield with the previous parameters */
		atarigen_pf_update(pf_state, scanline);
	
		/* update the MOs from the SLIP table */
		atarigen_mo_update_slip_512(new UBytePtr(atarigen_spriteram), pf_state.vscroll, scanline, new UBytePtr(atarigen_alpharam, 0xf80));
	
		/* update the current parameters */
/*TODO*///		if ((UINT8 *)base < &atarigen_alpharam[0xf80])
                    if (base.offset<4096)
			for (x = XCHARS; x < 64; x++)
			{
				int data = base.readinc();
				int command = data & 0x7e00;
	
				if (command == 0x7400)
					pf_state.param[0] = data & 7;
				else if (command == 0x7600)
					pf_state.hscroll = data & 0x1ff;
				else if (command == 0x7800)
					;
				else if (command == 0x7a00)
					;
				else if (command == 0x7c00)
					;
				else if (command == 0x7e00)
				{
					/* a new vscroll latches the offset into a counter; we must adjust for this */
					int offset = scanline + 8;
					if (offset >= 240)
						offset -= 240;
					pf_state.vscroll = (data - offset) & 0x1ff;
				}
			}
            }
        };
        
	
	/*************************************
	 *
	 *	Main refresh
	 *
	 *************************************/
	
	public static VhUpdatePtr vindictr_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		/* update the palette, and mark things dirty */
		if (update_palette() != null)
			memset(atarigen_pf_dirty, 0xff, atarigen_playfieldram_size[0] / 2);
	
		/* draw the playfield */
		memset(atarigen_pf_visit, 0, 64*64);
		atarigen_pf_process(pf_render_callback, bitmap, Machine.visible_area);
	
		/* draw the motion objects */
		atarigen_mo_process(mo_render_callback, bitmap);
	
		/* draw the alphanumerics */
		{
			GfxElement gfx = Machine.gfx[1];
			int x, y, offs;
	
			for (y = 0; y < YCHARS; y++)
				for (x = 0, offs = y * 64; x < XCHARS; x++, offs++)
				{
					int data = atarigen_alpharam.READ_WORD(offs * 2);
					int code = data & 0x3ff;
					int opaque = data & 0x8000;
	
					if (code!=0 || opaque!=0)
					{
						int color = ((data >> 10) & 0xf) | ((data >> 9) & 0x20);
						drawgfx(bitmap, gfx, code, color, 0, 0, 8 * x, 8 * y, null, opaque!=0 ? TRANSPARENCY_NONE : TRANSPARENCY_PEN, 0);
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
		int[] pf_map=new int[16], al_map=new int[64], mo_map=new int[16];
		int i, j;
	
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
			int[] usage = Machine.gfx[1].pen_usage;
			int x, y, offs;
	
			for (y = 0; y < YCHARS; y++)
				for (x = 0, offs = y * 64; x < XCHARS; x++, offs++)
				{
					int data = atarigen_alpharam.READ_WORD(offs * 2);
					int code = data & 0x3ff;
					int color = ((data >> 10) & 0xf) | ((data >> 9) & 0x20);
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
						palette_used_colors.write(0x200 + i * 16 + j, PALETTE_COLOR_USED);
		}
	
		/* rebuild the motion object palette */
		for (i = 0; i < 16; i++)
		{
			int used = mo_map[i];
			if (used != 0)
			{
				palette_used_colors.write(0x100 + i * 16 + 0, PALETTE_COLOR_TRANSPARENT);
				palette_used_colors.write(0x100 + i * 16 + 1, PALETTE_COLOR_TRANSPARENT);
				for (j = 2; j < 16; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x100 + i * 16 + j, PALETTE_COLOR_USED);
			}
		}
	
		/* rebuild the alphanumerics palette */
		for (i = 0; i < 64; i++)
		{
			int used = al_map[i];
			if (used != 0)
				for (j = 0; j < 4; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x000 + i * 4 + j, PALETTE_COLOR_USED);
		}
	
		/* recalc */
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
                IntSubArray usage = new IntSubArray(Machine.gfx[0].pen_usage, state.param[0] * 0x1000);
		int[] colormap = (int[]) param;
		int x, y;
	
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 63)
			{
				int offs = x * 64 + y;
				int data = atarigen_playfieldram.READ_WORD(offs * 2);
				int code = data & 0xfff;
				int color = (data >> 11) & 14;
				colormap[color] |= usage.read(code);
				colormap[color ^ 1] |= usage.read(code);
	
				/* also mark unvisited tiles dirty */
				if (atarigen_pf_visit.read(offs)==0) atarigen_pf_dirty.write(offs, 0xff);
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
		int bank = state.param[0];
		int x, y;
	
		/* first update any tiles whose color is out of date */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 63)
			{
				int offs = x * 64 + y;
				int data = atarigen_playfieldram.READ_WORD(offs * 2);
	
				/* update only if dirty */
				if (atarigen_pf_dirty.read(offs) != bank)
				{
					int color = 16 + ((data >> 11) & 14);
					int code = bank * 0x1000 + (data & 0xfff);
					int hflip = data & 0x8000;
	
					drawgfx(atarigen_pf_bitmap, gfx, code, color, hflip, 0, 8 * x, 8 * y, null, TRANSPARENCY_NONE, 0);
					atarigen_pf_dirty.write(offs, bank);
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
		mo_data modata = (mo_data) param;
		osd_bitmap bitmap = modata.bitmap;
		int color_xor = modata.color_xor;
		int bank = state.param[0];
		int x, y;
	
		/* first update any tiles whose color is out of date */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
		{
			int sy = (8 * y - state.vscroll) & 0x1ff;
			if (sy >= YDIM) sy -= 0x200;
	
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 63)
			{
				int offs = x * 64 + y;
				int data = atarigen_playfieldram.READ_WORD(offs * 2);
				int color = 16 + ((data >> 11) & 14);
				int code = bank * 0x1000 + (data & 0xfff);
				int hflip = data & 0x8000;
				int sx = (8 * x - state.hscroll) & 0x1ff;
				if (sx >= XDIM) sx -= 0x200;
	
				drawgfx(bitmap, gfx, code, color ^ color_xor, hflip, 0, sx, sy, null, TRANSPARENCY_THROUGH, palette_transparent_pen);
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
                int usage[] = Machine.gfx[0].pen_usage;
		int[] colormap = (int[]) param;
		int code = data.read(0) & 0x7fff;
		int hsize = ((data.read(2) >> 3) & 7) + 1;
		int vsize = (data.read(2) & 7) + 1;
		int color = data.read(1) & 0x000f;
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
                GfxElement gfx = Machine.gfx[0];
		int[] usage = gfx.pen_usage;
		osd_bitmap bitmap = (osd_bitmap) param;
		int total_usage = 0;
		rectangle pf_clip = new rectangle();
		int x, y, sx, sy;
	
		/* extract data from the various words */
		int code = data.read(0) & 0x7fff;
		int color = data.read(1) & 0x000f;
		int ypos = -pf_state.vscroll - (data.read(2) >> 7);
		int hflip = data.read(2) & 0x0040;
		int hsize = ((data.read(2) >> 3) & 7) + 1;
		int vsize = (data.read(2) & 7) + 1;
		int xpos = -pf_state.hscroll + (data.read(1) >> 7);
		int xadv;
	
		/* adjust for height */
		ypos -= vsize * 8;
	
		/* adjust the final coordinates */
		xpos &= 0x1ff;
		ypos &= 0x1ff;
		if (xpos >= XDIM) xpos -= 0x200;
		if (ypos >= YDIM) ypos -= 0x200;
	
		/* determine the bounding box */
		pf_clip = atarigen_mo_compute_clip_8x8(xpos, ypos, hsize, vsize, clip);
	
		/* adjust for h flip */
		if (hflip != 0) {
			xpos += (hsize - 1) * 8;
                        xadv = -8;
                } else {
			xadv = 8;
                }
	
		/* loop over the height */
		for (y = 0, sy = ypos; y < vsize; y++, sy += 8)
		{
			/* clip the Y coordinate */
			if (sy <= clip.min_y - 8)
			{
				code += hsize;
				continue;
			}
			else if (sy > clip.max_y)
				break;
	
			/* loop over the width */
			for (x = 0, sx = xpos; x < hsize; x++, sx += xadv, code++)
			{
				/* clip the X coordinate */
				if (sx <= -8 || sx >= XDIM)
					continue;
	
				/* draw the sprite */
				drawgfx(bitmap, gfx, code, color, hflip, 0, sx, sy, clip, TRANSPARENCY_PEN, 0);
				total_usage |= usage[code];
			}
		}
	
		/* overrender the playfield */
		if ((total_usage & 0x0002) != 0)
		{
			mo_data modata = new mo_data();
			modata.bitmap = bitmap;
			modata.color_xor = (color == 0) ? 0 : 1;
			atarigen_pf_process(pf_overrender_callback, modata, pf_clip);
		}
            }
        };
        
}
