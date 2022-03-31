/***************************************************************************

	Atari System 2 hardware

****************************************************************************/


/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.common.libc.expressions.*;
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.pokey.*;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.pokeyH.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
import static arcadeflex.WIP.v037b7.machine.atarigen.*;
import static arcadeflex.WIP.v037b7.machine.atarigenH.*;
import static arcadeflex.WIP.v037b7.vidhrdw.atarisy2.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.ptrLib.UShortPtr;
import arcadeflex.common.subArrays.IntSubArray;
import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.slapstic.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.*;

public class atarisy2
{
	
	static int XCHARS   = 64;
	static int YCHARS   = 48;
	
	static int XDIM     = (XCHARS*8);
	static int YDIM     = (YCHARS*8);
	
	
	
	/*************************************
	 *
	 *	Constants
	 *
	 *************************************/
	
	static int PFRAM_SIZE	= 0x4000;
	static int ANRAM_SIZE	= 0x1800;
	static int MORAM_SIZE	= 0x0800;
	
	
	
	/*************************************
	 *
	 *	Structures
	 *
	 *************************************/
	
	public static class mo_data
	{
		osd_bitmap bitmap;
		int xhold;
	};
	
	
	public static class pf_overrender_data
	{
		osd_bitmap bitmap;
		int mo_priority;
	};
	
	
	
	/*************************************
	 *
	 *	Globals we own
	 *
	 *************************************/
	
	public static UBytePtr atarisys2_slapstic = new UBytePtr();
	
	
	
	/*************************************
	 *
	 *	Statics
	 *
	 *************************************/
	
	public static UBytePtr playfieldram = new UBytePtr();
	public static UBytePtr alpharam = new UBytePtr();
	
	static int videobank;
	
	static atarigen_pf_state pf_state = new atarigen_pf_state();
	static int latched_vscroll;
	
	
	
	/*************************************
	 *
	 *	Video system start
	 *
	 *************************************/
	
	public static VhStartPtr atarisys2_vh_start = new VhStartPtr() { public int handler() 
	{
		atarigen_mo_desc mo_desc = new atarigen_mo_desc
		(
			256,                 /* maximum number of MO's */
			8,                   /* number of bytes per MO entry */
			2,                   /* number of bytes between MO words */
			3,                   /* ignore an entry if this word == 0xffff */
			3, 3, 0xff,          /* link = (data[linkword] >> linkshift) & linkmask */
			0                    /* render in reverse link order */
		);
	
		atarigen_pf_desc pf_desc = new atarigen_pf_desc
		(
			8, 8,				/* width/height of each tile */
			128, 64,				/* number of tiles in each direction */
                        0
		);
	
		/* allocate banked memory */
		alpharam = new UBytePtr(0x8000); //calloc(0x8000, 1);
		if (alpharam==null)
			return 1;
	
		spriteram = new UBytePtr(alpharam, ANRAM_SIZE);
		playfieldram = new UBytePtr(alpharam, 0x4000);
	
		/* reset the videoram banking */
		videoram = alpharam;
		videobank = 0;
	
		/*
		 * if we are palette reducing, do the simple thing by marking everything used except for
		 * the transparent sprite and alpha colors; this should give some leeway for machines
		 * that can't give up all 256 colors
		 */
		if (palette_used_colors != null)
		{
			int i;
			memset(palette_used_colors, PALETTE_COLOR_USED, Machine.drv.total_colors);
			for (i = 0; i < 4; i++)
				palette_used_colors.write(15 + i * 16, PALETTE_COLOR_TRANSPARENT);
			for (i = 0; i < 8; i++)
				palette_used_colors.write(64 + i * 4, PALETTE_COLOR_TRANSPARENT);
		}
	
		/* initialize the playfield */
		if (atarigen_pf_init(pf_desc) != 0)
		{
			alpharam=null;
			return 1;
		}
	
		/* initialize the motion objects */
		if (atarigen_mo_init(mo_desc) != 0)
		{
			atarigen_pf_free();
			alpharam = null;
			return 1;
		}
	
		return 0;
	} };
	
	
	
	/*************************************
	 *
	 *	Video system shutdown
	 *
	 *************************************/
	
	public static VhStopPtr atarisys2_vh_stop = new VhStopPtr() { public void handler() 
	{
		/* free memory */
		if (alpharam != null)
			alpharam = null;
		alpharam = playfieldram = spriteram = null;
	
		atarigen_pf_free();
		atarigen_mo_free();
	} };
	
	
	
	/*************************************
	 *
	 *	Scroll/playfield bank write
	 *
	 *************************************/
	
	public static WriteHandlerPtr atarisys2_hscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = atarigen_hscroll.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
		atarigen_hscroll.WRITE_WORD(offset, newword);
	
		/* update the playfield parameters - hscroll is clocked on the following scanline */
		pf_state.hscroll = (newword >> 6) & 0x03ff;
		pf_state.param[0] = newword & 0x000f;
		atarigen_pf_update(pf_state, cpu_getscanline() + 1);
	} };
	
	
	public static WriteHandlerPtr atarisys2_vscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = atarigen_vscroll.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
		atarigen_vscroll.WRITE_WORD(offset, newword);
	
		/* if bit 4 is zero, the scroll value is clocked in right away */
		latched_vscroll = (newword >> 6) & 0x01ff;
		if ((newword & 0x10)==0) pf_state.vscroll = latched_vscroll;
	
		/* update the playfield parameters */
		pf_state.param[1] = newword & 0x000f;
		atarigen_pf_update(pf_state, cpu_getscanline() + 1);
	} };
	
	
	
	/*************************************
	 *
	 *	Palette RAM write handler
	 *
	 *************************************/
        
        static int ZB = 115;
        static int Z3 = 78;
        static int Z2 = 37;
        static int Z1 = 17;
        static int Z0 = 9;

        static int[] intensity_table =
        {

                0, ZB+Z0, ZB+Z1, ZB+Z1+Z0, ZB+Z2, ZB+Z2+Z0, ZB+Z2+Z1, ZB+Z2+Z1+Z0,
                ZB+Z3, ZB+Z3+Z0, ZB+Z3+Z1, ZB+Z3+Z1+Z0,ZB+ Z3+Z2, ZB+Z3+Z2+Z0, ZB+Z3+Z2+Z1, ZB+Z3+Z2+Z1+Z0
        };

        static int[] color_table = { 0x0, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xe, 0xf, 0xf };
	
	public static WriteHandlerPtr atarisys2_paletteram_w = new WriteHandlerPtr() {
            public void handler(int offset, int data)
            {
                
	
		int inten, red, green, blue;
	
		int oldword = paletteram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
		int indx = offset / 2;
	
		paletteram.WRITE_WORD(offset, newword);
	
		inten = intensity_table[newword & 15];
		red = (color_table[(newword >> 12) & 15] * inten) >> 4;
		green = (color_table[(newword >> 8) & 15] * inten) >> 4;
		blue = (color_table[(newword >> 4) & 15] * inten) >> 4;
		palette_change_color(indx, red, green, blue);
	
            } 
        };
	
	
	
	/*************************************
	 *
	 *	Video RAM bank read/write handlers
	 *
	 *************************************/
	
	public static ReadHandlerPtr atarisys2_slapstic_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		slapstic_tweak(offset / 2);
	
		/* an extra tweak for the next opcode fetch */
		videobank = slapstic_tweak(0x1234);
		videoram = new UBytePtr(alpharam, videobank * 0x2000);
	
		return atarisys2_slapstic.READ_WORD(offset);
	} };
	
	
	public static WriteHandlerPtr atarisys2_slapstic_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		slapstic_tweak(offset / 2);
	
		/* an extra tweak for the next opcode fetch */
		videobank = slapstic_tweak(0x1234);
		videoram = new UBytePtr(alpharam, videobank * 0x2000);
	} };
	
	
	
	/*************************************
	 *
	 *	Video RAM read/write handlers
	 *
	 *************************************/
	
	public static ReadHandlerPtr atarisys2_videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return videoram.READ_WORD(offset);
	} };
	
	
	public static WriteHandlerPtr atarisys2_videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = videoram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
		videoram.WRITE_WORD(offset, newword);
	
		/* mark the playfield dirty if we write to it */
		if (videobank >= 2)
			if ((oldword & 0x3fff) != (newword & 0x3fff))
			{
				int offs = (videoram.offset- playfieldram.offset) / 2;
				atarigen_pf_dirty.write(offs, 0xff);
			}
	
		/* force an update if the link of object 0 changes */
		if (videobank == 0 && offset == 0x1806)
			atarigen_mo_update(spriteram, 0, cpu_getscanline() + 1);
	} };
	
	
	
	/*************************************
	 *
	 *	Periodic scanline updater
	 *
	 *************************************/
	
	public static void atarisys2_scanline_update(int scanline)
	{
		/* update the playfield */
		if (scanline == 0)
		{
			pf_state.vscroll = latched_vscroll;
			atarigen_pf_update(pf_state, scanline);
		}
	
		/* update the motion objects */
		if (scanline < YDIM)
			atarigen_mo_update(spriteram, 0, scanline);
	}
	
	
	
	/*************************************
	 *
	 *	Main refresh
	 *
	 *************************************/
	
	public static VhUpdatePtr atarisys2_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		mo_data modata = new mo_data();
		int i;
	
		/* recalc the palette if necessary */
		if (palette_recalc() != null)
			memset(atarigen_pf_dirty, 0xff, PFRAM_SIZE / 2);
	
		/* set up the all-transparent overrender palette */
		for (i = 0; i < 16; i++)
			atarigen_overrender_colortable.write(i, palette_transparent_pen);
	
		/* render the playfield */
		atarigen_pf_process(pf_render_callback, bitmap, Machine.visible_area);
	
		/* render the motion objects */
		modata.xhold = 0;
		modata.bitmap = bitmap;
		atarigen_mo_process(mo_render_callback, modata);
	
		/* render the alpha layer */
		{
			GfxElement gfx = Machine.gfx[2];
			int sx, sy, offs;
	
			for (sy = 0; sy < YCHARS; sy++)
				for (sx = 0, offs = sy * 64; sx < XCHARS; sx++, offs++)
				{
					int data = alpharam.READ_WORD(offs * 2);
					int code = data & 0x3ff;
	
					/* if there's a non-zero code, draw the tile */
					if (code != 0)
					{
						int color = (data >> 13) & 7;
	
						/* draw the character */
						drawgfx(bitmap, gfx, code, color, 0, 0, 8 * sx, 8 * sy, null, TRANSPARENCY_PEN, 0);
					}
				}
		}
	
		/* update onscreen messages */
		atarigen_update_messages();
	} };
	
	
	
	/*************************************
	 *
	 *	Playfield rendering
	 *
	 *************************************/
	
	static atarigen_pf_callback pf_render_callback = new atarigen_pf_callback() {
            @Override
            public void handler(rectangle clip, rectangle tiles, atarigen_pf_state state, java.lang.Object param) {
                GfxElement gfx = Machine.gfx[0];
		osd_bitmap bitmap = (osd_bitmap) param;
		int x, y;
	
		/* standard loop over tiles */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 127)
			{
				int offs = y * 128 + x;
				int data = playfieldram.READ_WORD(offs * 2);
				int pfbank = state.param[(data >> 10) & 1];
	
				/* update only if dirty */
				if (atarigen_pf_dirty.read(offs) != pfbank)
				{
					int code = (pfbank << 10) + (data & 0x3ff);
					int color = (data >> 11) & 7;
	
					drawgfx(atarigen_pf_bitmap, gfx, code, color, 0, 0, 8 * x, 8 * y, null, TRANSPARENCY_NONE, 0);
					atarigen_pf_dirty.write(offs, pfbank);
				}
			}
	
		/* then blast the result */
		x = -state.hscroll;
		y = -state.vscroll;
		copyscrollbitmap(bitmap, atarigen_pf_bitmap, 1, new int[]{x}, 1, new int[]{y}, clip, TRANSPARENCY_NONE, 0);
            }
        };
        
	
	/*************************************
	 *
	 *	Playfield overrender check
	 *
	 *************************************/
	
	static atarigen_pf_callback pf_check_overrender_callback = new atarigen_pf_callback() {
            @Override
            public void handler(rectangle clip, rectangle tiles, atarigen_pf_state state, java.lang.Object param) {
                pf_overrender_data overrender_data = (pf_overrender_data) param;
		GfxElement gfx = Machine.gfx[0];
		int mo_priority = overrender_data.mo_priority;
		int x, y;
	
		/* if we've already decided, bail */
		if (mo_priority == -1)
			return;
	
		/* standard loop over tiles */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 127)
			{
				int offs = y * 128 + x;
				int data = playfieldram.READ_WORD(offs * 2);
				int pf_priority = ((~data >> 13) & 6) | 1;
	
				if (((mo_priority + pf_priority) & 4) != 0)
				{
					int pfbank = state.param[(data >> 10) & 1];
					int code = (pfbank << 10) + (data & 0x3ff);
					if ((gfx.pen_usage[code] & 0xff00) != 0)
					{
						overrender_data.mo_priority = -1;
						return;
					}
				}
			}
            }
        };
        
	
	/*************************************
	 *
	 *	Playfield overrendering
	 *
	 *************************************/
	
	static atarigen_pf_callback pf_overrender_callback = new atarigen_pf_callback() {
            @Override
            public void handler(rectangle clip, rectangle tiles, atarigen_pf_state state, java.lang.Object param) {
                pf_overrender_data overrender_data = (pf_overrender_data) param;
		GfxElement gfx = Machine.gfx[0];
		osd_bitmap bitmap = overrender_data.bitmap;
		int mo_priority = overrender_data.mo_priority;
		int x, y;
	
		/* standard loop over tiles */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
		{
			int sy = (8 * y - state.vscroll) & 0x1ff;
			if (sy >= YDIM) sy -= 0x200;
	
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 127)
			{
				int offs = y * 128 + x;
				int data = playfieldram.READ_WORD(offs * 2);
				int pf_priority = ((~data >> 13) & 6) | 1;
	
				if (((mo_priority + pf_priority) & 4) != 0)
				{
					int pfbank = state.param[(data >> 10) & 1];
					int code = (pfbank << 10) + (data & 0x3ff);
					int color = (data >> 11) & 7;
					int sx = (8 * x - state.hscroll) & 0x1ff;
					if (sx >= XDIM) sx -= 0x400;
	
					drawgfx(bitmap, gfx, code, color, 0, 0, sx, sy, clip, TRANSPARENCY_PENS, 0x00ff);
				}
			}
		}
            }
        };
        
	
	/*************************************
	 *
	 *	Motion object rendering
	 *
	 *************************************/
	
	static atarigen_mo_callback mo_render_callback = new atarigen_mo_callback() {
            @Override
            public void handler(UShortArray data, rectangle clip, java.lang.Object param) {
                GfxElement gfx = Machine.gfx[1];
		mo_data modata = (mo_data) param;
		osd_bitmap bitmap = modata.bitmap;
		pf_overrender_data overrender_data = new pf_overrender_data();
		rectangle pf_clip;
	
		/* extract data from the various words */
		int ypos = -(data.read(0) >> 6);
		int hold = data.read(1) & 0x8000;
		int hflip = data.read(1) & 0x4000;
		int vsize = ((data.read(1) >> 11) & 7) + 1;
		int code = (data.read(1) & 0x7ff) + ((data.read(0) & 7) << 11);
		int xpos = (data.read(2) >> 6);
		int color = (data.read(3) >> 12) & 3;
		int priority = (data.read(3) >> 13) & 6;
	
		/* adjust for height */
		ypos -= vsize * 16;
	
		/* adjust x position for holding */
		if (hold != 0)
			xpos = modata.xhold;
		modata.xhold = xpos + 16;
	
		/* adjust the final coordinates */
		xpos &= 0x3ff;
		ypos &= 0x1ff;
		if (xpos >= XDIM) xpos -= 0x400;
		if (ypos >= YDIM) ypos -= 0x200;
	
		/* clip the X coordinate */
		if (xpos <= -16 || xpos >= XDIM)
			return;
	
		/* determine the bounding box */
		pf_clip = atarigen_mo_compute_clip_16x16(xpos, ypos, 1, vsize, clip);
	
		/* determine if we need to overrender */
		overrender_data.mo_priority = priority;
		atarigen_pf_process(pf_check_overrender_callback, overrender_data, pf_clip);
	
		/* if not, do it simply */
		if (overrender_data.mo_priority == priority)
		{
			atarigen_mo_draw_16x16_strip(bitmap, gfx, code, color, hflip, 0, xpos, ypos, vsize, clip, TRANSPARENCY_PEN, 15);
		}
	
		/* otherwise, make it tricky */
		else
		{
			/* draw an instance of the object in all transparent pens */
			atarigen_mo_draw_transparent_16x16_strip(bitmap, gfx, code, hflip, 0, xpos, ypos, vsize, clip, TRANSPARENCY_PEN, 15);
	
			/* and then draw it normally on the temp bitmap */
			atarigen_mo_draw_16x16_strip(atarigen_pf_overrender_bitmap, gfx, code, color, hflip, 0, xpos, ypos, vsize, clip, TRANSPARENCY_NONE, 0);
	
			/* overrender the playfield on top of that that */
			overrender_data.mo_priority = priority;
			overrender_data.bitmap = atarigen_pf_overrender_bitmap;
			atarigen_pf_process(pf_overrender_callback, overrender_data, pf_clip);
	
			/* finally, copy this chunk to the real bitmap */
			copybitmap(bitmap, atarigen_pf_overrender_bitmap, 0, 0, 0, 0, pf_clip, TRANSPARENCY_THROUGH, palette_transparent_pen);
		}
            }
        };
        
}
