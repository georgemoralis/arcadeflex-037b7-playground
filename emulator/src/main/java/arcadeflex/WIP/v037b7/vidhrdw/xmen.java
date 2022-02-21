/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.*;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.vidhrdw.konamiicH.*;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class xmen
{
	
	
	static int[] layer_colorbase=new int[3];
        static int sprite_colorbase,bg_colorbase;
	static int[] layerpri=new int[3];
	
	
	/***************************************************************************
	
	  Callbacks for the K052109
	
	***************************************************************************/
	
	public static K052109_callbackProcPtr xmen_tile_callback = new K052109_callbackProcPtr() { public void handler(int layer,int bank,int[] code,int[] color) 
	{
		/* (color & 0x02) is flip y handled internally by the 052109 */
		if (layer == 0)
			color[0] = layer_colorbase[layer] + ((color[0] & 0xf0) >> 4);
		else
			color[0] = layer_colorbase[layer] + ((color[0] & 0x7c) >> 2);
	} };
	
	/***************************************************************************
	
	  Callbacks for the K053247
	
	***************************************************************************/
	
	static K053247_callbackProcPtr xmen_sprite_callback = new K053247_callbackProcPtr() {
            @Override
            public void handler(int[] code, int[] color, int[] priority_mask) {
                int pri = (color[0] & 0x00e0) >> 4;	/* ??????? */
		if (pri <= layerpri[2])
                    priority_mask[0] = 0;
		else if (pri > layerpri[2] && pri <= layerpri[1])
                    priority_mask[0] = 0xf0;
		else if (pri > layerpri[1] && pri <= layerpri[0])
                    priority_mask[0] = 0xf0|0xcc;
		else
                    priority_mask[0] = 0xf0|0xcc|0xaa;
	
		color[0] = sprite_colorbase + (color[0] & 0x001f);
            }
        };
        
	/***************************************************************************
	
	  Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStartPtr xmen_vh_start = new VhStartPtr() { public int handler() 
	{
		if (K052109_vh_start(REGION_GFX1,0, 1, 2, 3/*NORMAL_PLANE_ORDER*/,xmen_tile_callback) != 0)
			return 1;
		if (K053247_vh_start(REGION_GFX2,53,-2,0, 1, 2, 3/*NORMAL_PLANE_ORDER*/,xmen_sprite_callback) != 0)
		{
			K052109_vh_stop();
			return 1;
		}
		return 0;
	} };
	
	public static VhStopPtr xmen_vh_stop = new VhStopPtr() { public void handler() 
	{
		K052109_vh_stop();
		K053247_vh_stop();
	} };
	
	
	
	/***************************************************************************
	
	  Display refresh
	
	***************************************************************************/
	
	/* useful function to sort the three tile layers by priority order */
/*TODO*///	static void sortlayers(int *layer,int *pri)
/*TODO*///	{
/*TODO*///	#define SWAP(a,b) \
/*TODO*///		if (pri[a] < pri[b]) \
/*TODO*///		{ \
/*TODO*///			int t; \
/*TODO*///			t = pri[a]; pri[a] = pri[b]; pri[b] = t; \
/*TODO*///			t = layer[a]; layer[a] = layer[b]; layer[b] = t; \
/*TODO*///		}
/*TODO*///	
/*TODO*///		SWAP(0,1)
/*TODO*///		SWAP(0,2)
/*TODO*///		SWAP(1,2)
/*TODO*///	}
	
	
	public static VhUpdatePtr xmen_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int[] layer = new int[3];
	
	
		bg_colorbase       = K053251_get_palette_index(K053251_CI4);
		sprite_colorbase   = K053251_get_palette_index(K053251_CI1);
		layer_colorbase[0] = K053251_get_palette_index(K053251_CI3);
		layer_colorbase[1] = K053251_get_palette_index(K053251_CI0);
		layer_colorbase[2] = K053251_get_palette_index(K053251_CI2);
	
		K052109_tilemap_update();
	
		palette_init_used_colors();
		K053247_mark_sprites_colors();
		palette_used_colors.write(16 * bg_colorbase+1, palette_used_colors.read(16 * bg_colorbase+1) | PALETTE_COLOR_VISIBLE);
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		layer[0] = 0;
		layerpri[0] = K053251_get_priority(K053251_CI3);
		layer[1] = 1;
		layerpri[1] = K053251_get_priority(K053251_CI0);
		layer[2] = 2;
		layerpri[2] = K053251_get_priority(K053251_CI2);
	
		//sortlayers(layer,layerpri);
                if (layerpri[0] < layerpri[1]) {
                    int t;
                    t = layerpri[0];
                    layerpri[0] = layerpri[1];
                    layerpri[1] = t;
                    t = layer[0];
                    layer[0] = layer[1];
                    layer[1] = t;
                }
                if (layerpri[0] < layerpri[2]) {
                    int t;
                    t = layerpri[0];
                    layerpri[0] = layerpri[2];
                    layerpri[2] = t;
                    t = layer[0];
                    layer[0] = layer[2];
                    layer[2] = t;
                }
                if (layerpri[1] < layerpri[2]) {
                    int t;
                    t = layerpri[1];
                    layerpri[1] = layerpri[2];
                    layerpri[2] = t;
                    t = layer[1];
                    layer[1] = layer[2];
                    layer[2] = t;
                }
	
		fillbitmap(priority_bitmap,0,null);
		/* note the '+1' in the background color!!! */
		fillbitmap(bitmap,Machine.pens[16 * bg_colorbase+1],Machine.visible_area);
		K052109_tilemap_draw(bitmap,layer[0],1<<16);
		K052109_tilemap_draw(bitmap,layer[1],2<<16);
		K052109_tilemap_draw(bitmap,layer[2],4<<16);
	
		K053247_sprites_draw(bitmap);
	} };
}
