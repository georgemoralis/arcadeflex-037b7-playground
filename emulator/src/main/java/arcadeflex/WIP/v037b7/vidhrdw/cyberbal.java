/***************************************************************************

	vidhrdw/cyberbal.c

	Functions to emulate the video hardware of the machine.

****************************************************************************

	Playfield encoding
	------------------
		1 16-bit word is used

		Word 1:
			Bit  15    = horizontal flip
			Bits 11-14 = palette
			Bits  0-12 = image index


	Motion Object encoding
	----------------------
		4 16-bit words are used

		Word 1:
			Bit  15    = horizontal flip
			Bits  0-14 = image index

		Word 2:
			Bits  7-15 = Y position
			Bits  0-3  = height in tiles

		Word 3:
			Bits  3-10 = link to the next image to display

		Word 4:
			Bits  6-14 = X position
			Bit   4    = use current X position + 16 for next sprite
			Bits  0-3  = palette


	Alpha layer encoding
	--------------------
		1 16-bit word is used

		Word 1:
			Bit  15    = horizontal flip
			Bit  12-14 = palette
			Bits  0-11 = image index

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;
        
import static arcadeflex.WIP.v037b7.machine.atarigen.*;
import static arcadeflex.WIP.v037b7.machine.atarigenH.*;
import static arcadeflex.common.ptrLib.*;
import arcadeflex.common.subArrays;
import arcadeflex.common.subArrays.IntSubArray;
import arcadeflex.common.subArrays.UShortArray;
//import arcadeflex.common.subArrays.IntSubArray;
//import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static arcadeflex.v056.mame.timer.*;
import static arcadeflex.v056.mame.timerH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;
import static gr.codebb.arcadeflex.common.libc.cstring.memset;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;        

public class cyberbal
{
	
	static final int XCHARS = 42;
	static final int YCHARS = 30;
	
	static final int XDIM = (XCHARS*16);
	static final int YDIM = (YCHARS*8);
	
	
	
	/*************************************
	 *
	 *	Structures
	 *
	 *************************************/
	
	public static class mo_params
	{
		int xhold;
		osd_bitmap bitmap;
	};
	
	
	
	/*************************************
	 *
	 *	Globals we own
	 *
	 *************************************/
	
	public static UBytePtr cyberbal_playfieldram_1 = new UBytePtr();
	public static UBytePtr cyberbal_playfieldram_2 = new UBytePtr();
	
	
	
	/*************************************
	 *
	 *	Statics
	 *
	 *************************************/
	
	static atarigen_pf_state pf_state = new atarigen_pf_state();
	static int current_slip;
	static UBytePtr active_palette = new UBytePtr();
	
	
	/*************************************
	 *
	 *	Video system start
	 *
	 *************************************/
	
	public static VhStartPtr cyberbal_vh_start = new VhStartPtr() { public int handler() 
	{
		atarigen_mo_desc mo_desc = new atarigen_mo_desc
		(
			256,				/* maximum number of MO's */
			8,					/* number of bytes per MO entry */
			2,					/* number of bytes between MO words */
			0,					/* ignore an entry if this word == 0xffff */
			2, 3, 0xff,			/* link = (data[linkword] >> linkshift) & linkmask */
			0					/* reverse order */
		);
	
		atarigen_pf_desc pf_desc = new atarigen_pf_desc
		(
			16, 8,				/* width/height of each tile */
			64, 64,				/* number of tiles in each direction */
                        0
		);
	
		/* reset statics */
		//memset(&pf_state, 0, sizeof(pf_state));
                pf_state = new atarigen_pf_state();
		current_slip = 0;
	
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
	
	public static VhStopPtr cyberbal_vh_stop = new VhStopPtr() { public void handler() 
	{
		atarigen_pf_free();
		atarigen_mo_free();
	} };
	
	
	
	/*************************************
	 *
	 *	Palette tweaker
	 *
	 *************************************/
	
	static void set_palette_entry(int entry, int value)
	{
		int r, g, b;
	
		r = ((value >> 9) & 0x3e) | ((value >> 15) & 1);
		g = ((value >> 4) & 0x3e) | ((value >> 15) & 1);
		b = ((value << 1) & 0x3e) | ((value >> 15) & 1);
	
		r = (r << 2) | (r >> 4);
		g = (g << 2) | (g >> 4);
		b = (b << 2) | (b >> 4);
	
		palette_change_color(entry, r, g, b);
	}
	
	
	
	/*************************************
	 *
	 *	Screen switcher
	 *
	 *************************************/
        
        static int _currScreen = 0;
	
	public static void cyberbal_set_screen(int which)
	{
		int i;
                
                System.out.println("cyberbal_set_screen="+which);
                _currScreen = which;
	
		/* update the video memory areas */
		atarigen_playfieldram = which!=0 ? new UBytePtr(cyberbal_playfieldram_2) : new UBytePtr(cyberbal_playfieldram_1);
		atarigen_playfieldram_size[0] = 0x2000;
		atarigen_alpharam = new UBytePtr(atarigen_playfieldram/*, atarigen_playfieldram_size[0]*/);
		atarigen_alpharam_size[0] = 0x1000;
		atarigen_spriteram = new UBytePtr(atarigen_alpharam/*, atarigen_alpharam_size[0]*/);
		atarigen_spriteram_size[0] = 0x1000;
	
		/* pick the active palette */
		active_palette = which!=0 ? new UBytePtr(paletteram_2) : new UBytePtr(paletteram);
	
		/* re-init the palette */
		for (i = 0; i < 2048; i++)
			set_palette_entry(i, active_palette.READ_WORD(i * 2));
	
		/* invalidate the screen */
		memset(atarigen_pf_dirty, 0xff, atarigen_playfieldram_size[0] / 2);
	}
	
	
	
	/*************************************
	 *
	 *	Playfield RAM write handlers
	 *
	 *************************************/
	
	public static WriteHandlerPtr cyberbal_playfieldram_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = cyberbal_playfieldram_1.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
	
		if (oldword != newword)
		{
			cyberbal_playfieldram_1.WRITE_WORD(offset, newword);
			//if (cyberbal_playfieldram_1.offset == atarigen_playfieldram.offset)
                        if (_currScreen==0)
				atarigen_pf_dirty.write(offset / 2, 0xff);
		}
	} };
	
	
	public static WriteHandlerPtr cyberbal_playfieldram_2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = cyberbal_playfieldram_2.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
	
		if (oldword != newword)
		{
			cyberbal_playfieldram_2.WRITE_WORD(offset, newword);
			//if (cyberbal_playfieldram_2.offset == atarigen_playfieldram.offset)
                        if (_currScreen!=0)
				atarigen_pf_dirty.write(offset / 2, 0xff);
		}
	} };
	
	
	
	/*************************************
	 *
	 *	Palette RAM write handlers
	 *
	 *************************************/
	
	public static WriteHandlerPtr cyberbal_paletteram_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = paletteram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
	
		if (oldword != newword)
		{
			paletteram.WRITE_WORD(offset, newword);
			//if (paletteram.offset == active_palette.offset)
                        if (_currScreen==0)
				set_palette_entry(offset / 2, newword);
		}
	} };
	
	public static ReadHandlerPtr cyberbal_paletteram_1_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return paletteram.READ_WORD(offset);
	} };
	
	
	public static WriteHandlerPtr cyberbal_paletteram_2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = paletteram_2.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword, data);
	
		if (oldword != newword)
		{
			paletteram_2.WRITE_WORD(offset, newword);
			//if (paletteram_2.offset == active_palette.offset)
                        if (_currScreen!=0)
				set_palette_entry(offset / 2, newword);
		}
	} };
	
	public static ReadHandlerPtr cyberbal_paletteram_2_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return paletteram_2.READ_WORD(offset);
	} };
	
	
	
	/*************************************
	 *
	 *	Periodic scanline updater
	 *
	 *************************************/
	
	public static timer_callback cyberbal_scanline_update = new timer_callback() {
            @Override
            public void handler(int scanline) {
                //UINT16 *base = (UINT16 *)&atarigen_alpharam[((scanline / 8) * 64 + 47) * 2];
                UShortArray base = new UShortArray(atarigen_alpharam, ((scanline / 8) * 64 + 47) * 2);
	
		/* keep in range */
		//if ((UINT8 *)base.offset >= &atarigen_alpharam[atarigen_alpharam_size])
/*TODO*///                if (((base.offset)) >= atarigen_alpharam_size[0])
/*TODO*///			return;
                if ((atarigen_alpharam.offset+((scanline / 8) * 64 + 47) * 2) >= (atarigen_alpharam_size[0]))
                    return;
	
		/* update the playfield with the previous parameters */
		atarigen_pf_update(pf_state, scanline);
	
		/* update the MOs with the previous parameters */
		atarigen_mo_update(atarigen_spriteram, current_slip, scanline);
	
		/* update the current parameters */
		if ((base.read(3) & 1)==0)
			pf_state.param[0] = (base.read(3) >> 1) & 7;
		if ((base.read(4) & 1)==0)
			pf_state.hscroll = 2 * (((base.read(4) >> 7) + 4) & 0x1ff);
		if ((base.read(5) & 1)==0)
		{
			/* a new vscroll latches the offset into a counter; we must adjust for this */
			int offset = scanline + 8;
			if (offset >= 256)
				offset -= 256;
			pf_state.vscroll = ((base.read(5) >> 7) - offset) & 0x1ff;
		}
		if ((base.read(6) & 1)==0)
			pf_state.param[1] = (base.read(6) >> 1) & 0xff;
		if ((base.read(7) & 1)==0)
			current_slip = (base.read(7) >> 3) & 0xff;
            }
        };
        
	
	
	/*************************************
	 *
	 *	Main refresh
	 *
	 *************************************/
	
	public static VhUpdatePtr cyberbal_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		mo_params modata = new mo_params();
		GfxElement gfx;
		int x, y, offs;
	
		/* update the palette, and mark things dirty */
		if (update_palette() != null)
			memset(atarigen_pf_dirty, 0xff, atarigen_playfieldram_size[0] / 2);
	
		/* draw the playfield */
		memset(atarigen_pf_visit, 0, 64*64);
		atarigen_pf_process(pf_render_callback, bitmap, Machine.visible_area);
	
		/* draw the motion objects */
		modata.xhold = 1000;
		modata.bitmap = bitmap;
		atarigen_mo_process(mo_render_callback, modata);
	
		/* draw the alphanumerics */
                //atarigen_alpharam.offset=0;
		gfx = Machine.gfx[2];
		for (y = 0; y < YCHARS; y++)
			for (x = 0, offs = y * 64; x < XCHARS; x++, offs++)
			{
				int data = atarigen_alpharam.READ_WORD(offs * 2);
				int code = data & 0xfff;
				int hflip = data & 0x8000;
				int color = (data >> 12) & 7;
				drawgfx(bitmap, gfx, code, color, hflip, 0, 16 * x, 8 * y, null, TRANSPARENCY_PEN, 0);
			}
	
		/* update onscreen messages */
		atarigen_update_messages();
	} };
	
	
	
	/*************************************
	 *
	 *	Palette management
	 *
	 *************************************/
        
        static UBytePtr update_palette_new()
	{
		//int[] pf_map=new int[8];
                IntSubArray pf_map = new IntSubArray(16 * 8);
                int[] mo_map=new int[16];
		int i, j;
	
		/* reset color tracking */
		memset(mo_map, 0, mo_map.length);
		memset(pf_map, 0, pf_map.buffer.length);
		palette_init_used_colors();
	
		/* update color usage for the playfield */
		atarigen_pf_process(pf_color_callback, pf_map, Machine.visible_area);
	
		/* update color usage for the mo's */
		atarigen_mo_process(mo_color_callback, mo_map);
	
		/* rebuild the playfield palette */
		for (i = 0; i < 8; i++)
		{
			int used = pf_map.read(i);
			if (used != 0)
				for (j = 0; j < 16; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x100 + i * 16 + j, PALETTE_COLOR_USED);
		}
	
		/* rebuild the motion object palette */
		for (i = 0; i < 16; i++)
		{
			int used = mo_map[i];
			if (used != 0)
			{
				palette_used_colors.write(0x000 + i * 16 + 0, PALETTE_COLOR_TRANSPARENT);
				for (j = 1; j < 16; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x000 + i * 16 + j, PALETTE_COLOR_USED);
			}
		}
	
		return palette_recalc();
	}
	
	
	
	static UBytePtr update_palette()
	{
		int[] mo_map=new int[16], al_map=new int[8];
                IntSubArray pf_map = new IntSubArray(16 * 8);
		int[] usage;
		int i, j, x, y, offs;
	
		/* reset color tracking */
		memset(mo_map, 0, mo_map.length);
		memset(pf_map, 0, pf_map.buffer.length);
		memset(al_map, 0, al_map.length);
		palette_init_used_colors();
	
		/* update color usage for the playfield */
		atarigen_pf_process(pf_color_callback, pf_map, Machine.visible_area);
	
		/* update color usage for the mo's */
		atarigen_mo_process(mo_color_callback, mo_map);
	
		/* update color usage for the alphanumerics */
		usage = Machine.gfx[2].pen_usage;
                
                //UShortPtr _upr = new UShortPtr(atarigen_alpharam);
                //_upr.offset=0;
                //atarigen_alpharam.offset=0;
                
		for (y = 0; y < YCHARS; y++)
			for (x = 0, offs = y * 64; x < XCHARS; x++, offs++)
			{
                            try {
				        int data = atarigen_alpharam.READ_WORD(offs * 2);
                            //int data = _upr.read(offs);
                                    int code = data & 0xfff;
                                    int color = (data >> 12) & 7;
                                    al_map[color] |= usage[code];
                            } catch (Exception e) {
                                System.out.println("x="+x);
                                System.out.println("y="+y);
                                System.out.println("offs="+offs * 2);
                                System.out.println("off_gen="+atarigen_alpharam.offset);
                                
                            }
                                
			}
	
		/* rebuild the playfield palette */
		for (i = 0; i < 16 * 8; i++)
		{
			int used = pf_map.read(i);
			if (used != 0)
				for (j = 0; j < 16; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(i * 16 + j, PALETTE_COLOR_USED);
		}
	
		/* rebuild the motion object palette */
		for (i = 0; i < 16; i++)
		{
			int used = mo_map[i];
			if (used != 0)
			{
				palette_used_colors.write(0x600 + i * 16 + 0, PALETTE_COLOR_TRANSPARENT);
				for (j = 1; j < 16; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x600 + i * 16 + j, PALETTE_COLOR_USED);
			}
		}
	
		/* rebuild the alphanumerics palette */
		for (i = 0; i < 8; i++)
		{
			int used = al_map[i];
			if (used != 0)
			{
				palette_used_colors.write(0x780 + i * 16 + 0, PALETTE_COLOR_TRANSPARENT);
				for (j = 1; j < 16; j++)
					if ((used & (1 << j)) != 0)
						palette_used_colors.write(0x780 + i * 16 + j, PALETTE_COLOR_USED);
			}
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
                IntSubArray colormap = new IntSubArray((IntSubArray)param, 16 * state.param[0]);
		int[] usage = Machine.gfx[0].pen_usage;
		int x, y;
                
                //atarigen_playfieldram.offset=0;
	
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63){
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 63)
			{
				int offs = y * 64 + x;
				int data = atarigen_playfieldram.READ_WORD(offs * 2);
				int code = data & 0x1fff;
				int color = (data >> 11) & 15;
				colormap.write(color, colormap.read(color)  | usage[code]);
	
				/* also mark unvisited tiles dirty */
				if (atarigen_pf_visit.read(offs)==0) 
                                    atarigen_pf_dirty.write(offs, 0xff);
			}
                }
                
                param = new IntSubArray(colormap);
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
		int colorbase = 16 * state.param[0];
		osd_bitmap bitmap = (osd_bitmap) param;
		int x, y;
                
                //atarigen_playfieldram.offset=0;
	
		/* first update any tiles whose color is out of date */
		for (y = tiles.min_y; y != tiles.max_y; y = (y + 1) & 63)
			for (x = tiles.min_x; x != tiles.max_x; x = (x + 1) & 63)
			{
				int offs = y * 64 + x;
				int data = atarigen_playfieldram.READ_WORD(offs * 2);
				int color = colorbase + ((data >> 11) & 15);
	
				if (atarigen_pf_dirty.read(offs) != color)
				{
					int code = data & 0x1fff;
					int hflip = data & 0x8000;
	
					drawgfx(atarigen_pf_bitmap, gfx, code, color, hflip, 0, 16 * x, 8 * y, null, TRANSPARENCY_NONE, 0);
					atarigen_pf_dirty.write(offs, color);
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
	 *	Motion object palette
	 *
	 *************************************/
	
	static atarigen_mo_callback mo_color_callback = new atarigen_mo_callback() {
            @Override
            public void handler(UShortArray data, rectangle clip, Object param) {
                int[] usage = Machine.gfx[1].pen_usage;
		int[] colormap = (int[]) param;
		int temp = 0;
		int y;
	
		int code = data.read(0) & 0x7fff;
		int vsize = (data.read(1) & 15) + 1;
		int color = data.read(3) & 0x0f;
	
		for (y = 0; y < vsize; y++)
			temp |= usage[code++];
		colormap[color] |= temp;
                
                param = colormap;
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
		mo_params modata = (mo_params) param;
		osd_bitmap bitmap = modata.bitmap;
	
		/* extract data from the various words */
		int hflip = data.read(0) & 0x8000;
		int code = data.read(0) & 0x7fff;
		int ypos = -(data.read(1) >> 7);
		int vsize = (data.read(1) & 0x000f) + 1;
		int xpos = (data.read(3) >> 6) - 4;
		int hold_next = data.read(3) & 0x0010;
		int color = data.read(3) & 0x000f;
	
		/* adjust for height */
		ypos -= vsize * 8;
	
		/* if we've got a hold position from the last MO, use that */
		if (modata.xhold != 1000)
			xpos = modata.xhold;
	
		/* if we've got a hold position for the next MO, set it now */
		if (hold_next != 0)
			modata.xhold = xpos + 16;
		else
			modata.xhold = 1000;
	
		/* adjust the final coordinates */
		xpos &= 0x3ff;
		ypos &= 0x1ff;
		if (xpos >= XDIM) xpos -= 0x400;
		if (ypos >= YDIM) ypos -= 0x200;
	
		/* draw the motion object */
		atarigen_mo_draw_16x8_strip(bitmap, gfx, code, color, hflip, 0, xpos, ypos, vsize, clip, TRANSPARENCY_PEN, 0);
                
                param = modata;
            }
        };
        
}
