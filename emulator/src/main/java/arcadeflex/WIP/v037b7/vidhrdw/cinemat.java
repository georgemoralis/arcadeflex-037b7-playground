/***************************************************************************

  vidhrdw.c

  Generic functions used by the Cinematronics Vector games

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.WIP.v037b7.cpu.ccpu.ccpuH.*;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.artworkC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.artworkH.*;        
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.vector.*;
import static gr.codebb.arcadeflex.common.libc.cstdio.*;        
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static gr.codebb.arcadeflex.old.arcadeflex.video.osd_mark_dirty;
import static gr.codebb.arcadeflex.old.arcadeflex.video.osd_skip_this_frame;

public class cinemat
{
	
	public static int VEC_SHIFT = 16;
	
	public static int RED   = 0x04;
	public static int GREEN = 0x02;
	public static int BLUE  = 0x01;
	public static int WHITE = RED|GREEN|BLUE;
	
	static int cinemat_monitor_type;
	static int cinemat_overlay_req;
	static int cinemat_backdrop_req;
	static int cinemat_screenh;
	static artwork_element[] cinemat_simple_overlay;
	
	static boolean color_display;
	static artwork_info spacewar_panel;
	static artwork_info spacewar_pressed_panel;
	
	public static artwork_element starcas_overlay[]=
	{
		new artwork_element(new rectangle( 0, 400-1, 0, 300-1),0x00,  61,  0xff, OVERLAY_DEFAULT_OPACITY),
		new artwork_element(new rectangle( 200, 49, 150, -2),  0xff, 0x20, 0x20, OVERLAY_DEFAULT_OPACITY),
		new artwork_element(new rectangle( 200, 29, 150, -2),  0xff, 0xff, 0xff, OVERLAY_DEFAULT_OPACITY), /* punch hole in outer circle */
		new artwork_element(new rectangle( 200, 38, 150, -1),  0xe0, 0xff, 0x00, OVERLAY_DEFAULT_OPACITY),
		new artwork_element(new rectangle(-1,-1,-1,-1),0,0,0,0)
	};
	
	public static artwork_element tailg_overlay[]=
	{
		new artwork_element(new rectangle(0, 400-1, 0, 300-1), 0, 64, 64, OVERLAY_DEFAULT_OPACITY),
		new artwork_element(new rectangle(-1,-1,-1,-1),0,0,0,0)
	};
	
	public static artwork_element sundance_overlay[]=
	{
		new artwork_element(new rectangle(0, 400-1, 0, 300-1), 0xff, 0xff, 0x20, OVERLAY_DEFAULT_OPACITY),
		new artwork_element(new rectangle(-1,-1,-1,-1),0,0,0,0)
	};
	
	public static artwork_element solarq_overlay[]=
	{
		new artwork_element(new rectangle(0, 400-1, 30, 300-1),0x20, 0x20, 0xff, OVERLAY_DEFAULT_OPACITY),
		new artwork_element(new rectangle( 0,  399, 0,    29), 0xff, 0x20, 0x20, OVERLAY_DEFAULT_OPACITY),
		new artwork_element(new rectangle( 200, 12, 150,  -2), 0xff, 0xff, 0x20, OVERLAY_DEFAULT_OPACITY),
		new artwork_element(new rectangle(-1,-1,-1,-1),0,0,0,0)
        };
	
	static int lastx, lasty;
        
	public static void CinemaVectorData (int fromx, int fromy, int tox, int toy, int color)
	{
	    
	
		fromy = cinemat_screenh - fromy;
		toy = cinemat_screenh - toy;
	
		if (fromx != lastx || fromx != lasty)
			vector_add_point (fromx << VEC_SHIFT, fromy << VEC_SHIFT, 0, 0);
	
	    if (color_display != false)
	        vector_add_point (tox << VEC_SHIFT, toy << VEC_SHIFT, color & 0x07, (color & 0x08)!=0 ? 0x80: 0x40);
	    else
	        vector_add_point (tox << VEC_SHIFT, toy << VEC_SHIFT, WHITE, color * 12);
	
		lastx = tox;
		lasty = toy;
	}
	
	/* This is called by the game specific init function and sets the local
	 * parameters for the generic function cinemat_init_colors() */
	public static void cinemat_select_artwork (int monitor_type, int overlay_req, int backdrop_req, artwork_element[] simple_overlay)
	{
		cinemat_monitor_type = monitor_type;
		cinemat_overlay_req = overlay_req;
		cinemat_backdrop_req = backdrop_req;
		cinemat_simple_overlay = simple_overlay;
	}
	
	static void shade_fill (char[] palette, int rgb, int start_index, int end_index, int start_inten, int end_inten)
	{
		int i, inten, index_range, inten_range;
	
		index_range = end_index-start_index;
		inten_range = end_inten-start_inten;
		for (i = start_index; i <= end_index; i++)
		{
			inten = start_inten + (inten_range) * (i-start_index) / (index_range);
			palette[3*i  ] = (char) ((rgb & RED  )!=0? inten : 0);
			palette[3*i+1] = (char) ((rgb & GREEN)!=0? inten : 0);
			palette[3*i+2] = (char) ((rgb & BLUE )!=0? inten : 0);
		}
	}
	
	
	public static VhConvertColorPromPtr cinemat_init_colors = new VhConvertColorPromPtr() { public void handler(char []palette, char []colortable, UBytePtr color_prom) 
	{
		int i,j,k, nextcol;
		String filename="";
	
		int trcl1[] = { 0,0,2,2,1,1 };
		int trcl2[] = { 1,2,0,1,0,2 };
		int trcl3[] = { 2,1,1,0,2,0 };
	
		/* initialize the first 8 colors with the basic colors */
		for (i = 0; i < 8; i++)
		{
			palette[3*i  ] = (char) ((i & RED  )!=0 ? 0xff : 0);
			palette[3*i+1] = (char) ((i & GREEN)!=0 ? 0xff : 0);
			palette[3*i+2] = (char) ((i & BLUE )!=0 ? 0xff : 0);
		}
	
		shade_fill (palette, WHITE, 8, 23, 0, 255);
                nextcol = 24;
	
		/* fill the rest of the 256 color entries depending on the game */
		switch (cinemat_monitor_type)
		{
			case  CCPU_MONITOR_BILEV:
			case  CCPU_MONITOR_16LEV:
                                color_display = false;
				/* Attempt to load backdrop if requested */
				if (cinemat_backdrop_req != 0)
				{
                                        filename = sprintf ("%sb.png", Machine.gamedrv.name );
					backdrop_load(filename, nextcol, Machine.drv.total_colors-nextcol);
					if (artwork_backdrop!=null)
                                        {
						memcpy (palette,3*artwork_backdrop.start_pen, artwork_backdrop.u8_orig_palette, 3*artwork_backdrop.num_pens_used);
						if (Machine.scrbitmap.depth == 8)
							nextcol += artwork_backdrop.num_pens_used;
                                        }
				}
				/* Attempt to load overlay if requested */
				if (cinemat_overlay_req != 0)
				{
					if (cinemat_simple_overlay != null)
					{
						/* use simple overlay */
/*TODO*///						artwork_elements_scale(cinemat_simple_overlay,
/*TODO*///											   Machine.scrbitmap.width,
/*TODO*///											   Machine.scrbitmap.height);
/*TODO*///						overlay_create(cinemat_simple_overlay, nextcol,Machine.drv.total_colors-nextcol);
					}
					else
					{
						/* load overlay from file */
/*TODO*///                                                sprintf (filename, "%so.png", Machine.gamedrv.name );
/*TODO*///						overlay_load(filename, nextcol, Machine.drv.total_colors-nextcol);
					}
	
/*TODO*///					if ((Machine.scrbitmap.depth == 8) || (artwork_backdrop == null))
/*TODO*///						overlay_set_palette (palette, (Machine.drv.total_colors > 256 ? 256 : Machine.drv.total_colors) - nextcol);
				}
				break;
	
			case  CCPU_MONITOR_WOWCOL:
                                color_display = true;
				/* TODO: support real color */
				/* put in 40 shades for red, blue and magenta */
				shade_fill (palette, RED       ,   8,  47, 10, 250);
				shade_fill (palette, BLUE      ,  48,  87, 10, 250);
				shade_fill (palette, RED|BLUE  ,  88, 127, 10, 250);
	
				/* put in 20 shades for yellow and green */
				shade_fill (palette, GREEN     , 128, 147, 10, 250);
				shade_fill (palette, RED|GREEN , 148, 167, 10, 250);
	
				/* and 14 shades for cyan and white */
				shade_fill (palette, BLUE|GREEN, 168, 181, 10, 250);
				shade_fill (palette, WHITE     , 182, 194, 10, 250);
	
				/* Fill in unused gaps with more anti-aliasing colors. */
				/* There are 60 slots available.           .ac JAN2498 */
				i=195;
				for (j=0; j<6; j++)
				{
					for (k=7; k<=16; k++)
					{
						palette[3*i+trcl1[j]] = (char) (((256*k)/16)-1);
						palette[3*i+trcl2[j]] = (char) (((128*k)/16)-1);
						palette[3*i+trcl3[j]] = 0;
						i++;
					}
				}
				break;
		}
	} };
	
	
	public static VhConvertColorPromPtr spacewar_init_colors = new VhConvertColorPromPtr() { public void handler(char []palette, char []colortable, UBytePtr color_prom) 
	{
		int width, height, i, nextcol;
	
                color_display = false;
	
		/* initialize the first 8 colors with the basic colors */
		for (i = 0; i < 8; i++)
		{
			palette[3*i  ] = (char) ((i & RED  )!=0 ? 0xff : 0);
			palette[3*i+1] = (char) ((i & GREEN)!=0 ? 0xff : 0);
			palette[3*i+2] = (char) ((i & BLUE )!=0 ? 0xff : 0);
		}
	
		for (i = 0; i < 16; i++)
			palette[3*(i+8)]=palette[3*(i+8)+1]=palette[3*(i+8)+2]= (char) ((255*i)/15);
	
		spacewar_pressed_panel = null;
		width = Machine.scrbitmap.width;
		height = (int) (0.16 * width);
	
		nextcol = 24;
	
		artwork_load_size(spacewar_panel, "spacewr1.png", nextcol, Machine.drv.total_colors - nextcol, width, height);
		if (spacewar_panel != null)
		{
			if (Machine.scrbitmap.depth == 8)
				nextcol += spacewar_panel.num_pens_used;
	
			artwork_load_size(spacewar_pressed_panel, "spacewr2.png", nextcol, Machine.drv.total_colors - nextcol, width, height);
			if (spacewar_pressed_panel == null)
			{
				artwork_free (spacewar_panel);
				return ;
			}
		}
		else
			return;
	
		memcpy (palette,3*spacewar_panel.start_pen, spacewar_panel.u8_orig_palette,
				3*spacewar_panel.num_pens_used);
	
		if (Machine.scrbitmap.depth == 8)
			memcpy (palette,3*spacewar_pressed_panel.start_pen, spacewar_pressed_panel.u8_orig_palette,
					3*spacewar_pressed_panel.num_pens_used);
	} };
	
	/***************************************************************************
	
	  Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStartPtr cinemat_vh_start = new VhStartPtr() { public int handler() 
	{
		vector_set_shift (VEC_SHIFT);
		cinemat_screenh = Machine.visible_area.max_y - Machine.visible_area.min_y;
		return vector_vh_start.handler();
	} };
	
	public static VhStartPtr spacewar_vh_start = new VhStartPtr() { public int handler() 
	{
		vector_set_shift (VEC_SHIFT);
		if (spacewar_panel != null) backdrop_refresh(spacewar_panel);
		if (spacewar_pressed_panel != null) backdrop_refresh(spacewar_pressed_panel);
		cinemat_screenh = Machine.visible_area.max_y - Machine.visible_area.min_y;
		return vector_vh_start.handler();
	} };
	
	
	public static VhStopPtr cinemat_vh_stop = new VhStopPtr() { public void handler() 
	{
		vector_vh_stop.handler();
	} };
	
	public static VhStopPtr spacewar_vh_stop = new VhStopPtr() { public void handler() 
	{
		if (spacewar_panel != null)
			artwork_free(spacewar_panel);
	
		if (spacewar_pressed_panel != null)
			artwork_free(spacewar_pressed_panel);
	
		vector_vh_stop.handler();
	} };
	
	public static VhUpdatePtr cinemat_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
	    vector_vh_screenrefresh.handler(bitmap, full_refresh);
	    vector_clear_list ();
	} };
        
        static int sw_option_change;
	
	public static VhUpdatePtr spacewar_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
	    int tk[] = {3, 8, 4, 9, 1, 6, 2, 7, 5, 0};
		int i, pwidth, pheight, key, row, col, sw_option;
		float scale;
		osd_bitmap vector_bitmap=new osd_bitmap();
		rectangle rect = new rectangle();
	
		if (spacewar_panel == null)
		{
                        vector_vh_screenrefresh.handler(bitmap, full_refresh);
                        vector_clear_list ();
			return;
		}
	
		pwidth = spacewar_panel.artwork.width;
		pheight = spacewar_panel.artwork.height;
	
		vector_bitmap.width = bitmap.width;
		vector_bitmap.height = bitmap.height - pheight;
		vector_bitmap._private = bitmap._private;
		vector_bitmap.line = bitmap.line;
	
		vector_vh_screenrefresh.handler(vector_bitmap,full_refresh);
                vector_clear_list ();
	
		if (full_refresh != 0)
			copybitmap(bitmap,spacewar_panel.artwork,0,0,
					   0,bitmap.height - pheight, null,TRANSPARENCY_NONE,0);
	
		scale = (float) (pwidth/1024.0);
	
	    sw_option = input_port_1_r.handler(0);
	
	    /* move bits 10-11 to position 8-9, so we can just use a simple 'for' loop */
	    sw_option = (sw_option & 0xff) | ((sw_option >> 2) & 0x0300);
	
	    sw_option_change ^= sw_option;
	
		for (i = 0; i < 10; i++)
		{
			if ((sw_option_change & (1 << i)) !=0 || full_refresh!=0)
			{
                                key = tk[i];
                                col = key % 5;
                                row = key / 5;
				rect.min_x = (int) (scale * (465 + 20 * col));
				rect.max_x = (int) (scale * (465 + 20 * col + 18));
				rect.min_y = (int) (scale * (39  + 20 * row) + vector_bitmap.height);
				rect.max_y = (int) (scale * (39  + 20 * row + 18) + vector_bitmap.height);
	
				if ((sw_option & (1 << i)) != 0)
	            {
					copybitmap(bitmap,spacewar_panel.artwork,0,0,
							   0, vector_bitmap.height, rect,TRANSPARENCY_NONE,0);
	            }
				else
	            {
					copybitmap(bitmap,spacewar_pressed_panel.artwork,0,0,
							   0, vector_bitmap.height, rect,TRANSPARENCY_NONE,0);
	            }
	
				osd_mark_dirty (rect.min_x, rect.min_y,
	                            rect.max_x, rect.max_y, 0);
			}
		}
	    sw_option_change = sw_option;
	} };
	
	public static InterruptPtr cinemat_clear_list = new InterruptPtr() {
            @Override
            public int handler() {
                if (osd_skip_this_frame() != 0)
	        vector_clear_list ();
	    return ignore_interrupt.handler();
            }
        };
	
}
