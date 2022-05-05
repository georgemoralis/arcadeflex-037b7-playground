/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import static arcadeflex.v037b7.vidhrdw.konamiicH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class xexex
{
	
	
	static int sprite_colorbase, bg_colorbase;
	static int[] layer_colorbase = new int[4], layerpri = new int[4];
	
	
	static K053247_callbackProcPtr xexex_sprite_callback = new K053247_callbackProcPtr() {
            @Override
            public void handler(int[] code, int[] color, int[] priority_mask) {
                int pri = (color[0] & 0x00e0) >> 4;	/* ??????? */
		if (pri <= layerpri[2])
                    priority_mask[0] = 0;
		else if (pri > layerpri[2] && pri <= layerpri[1])	priority_mask[0] = 0xf0;
		else if (pri > layerpri[1] && pri <= layerpri[0])	priority_mask[0] = 0xf0|0xcc;
		else 												
                    priority_mask[0] = 0xf0|0xcc|0xaa;
	
		color[0] = sprite_colorbase | (color[0] & 0x001f);
            }
        };
        
	static K054157_callbackProcPtr xexex_tile_callback = new K054157_callbackProcPtr() {
            @Override
            public void handler(int layer, int[] code, int[] color) {
                tile_info.u32_flags = TILE_FLIPYX((color[0]) & 3);
		color[0] = layer_colorbase[layer] | ((color[0] & 0xf0) >> 4);
            }
        };
        
	static int xexex_scrolld[][][] = {
		{{ 53-64, 16 }, {53-64, 16}, {53-64, 16}, {53-64, 16}},
		{{ 42-64, 16 }, {42-64-4, 16}, {42-64-2, 16}, {42-64, 16}}
	};
	
	public static VhStartPtr xexex_vh_start = new VhStartPtr() { public int handler() 
	{
		K054157_vh_start(2, 6, REGION_GFX1, xexex_scrolld,0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, xexex_tile_callback);
		if (K053247_vh_start(REGION_GFX2, -28, 32,0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, xexex_sprite_callback) != 0)
		{
			K054157_vh_stop();
			return 1;
		}
		return 0;
	} };
	
	public static VhStopPtr xexex_vh_stop = new VhStopPtr() { public void handler() 
	{
		K054157_vh_stop();
		K053247_vh_stop();
	} };
	
	
	
	public static ReadHandlerPtr xexex_palette_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return paletteram.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr xexex_palette_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int r, g, b;
		int data0, data1;
	
		COMBINE_WORD_MEM(paletteram,offset, data);
	
		offset &= ~3;
	
		data0 = paletteram.READ_WORD(offset);
		data1 = paletteram.READ_WORD(offset + 2);
	
		r = data0 & 0xff;
		g = data1 >> 8;
		b = data1 & 0xff;
	
		palette_change_color(offset>>2, r, g, b);
	} };
	
	
	
	/* useful function to sort the four tile layers by priority order */
	/* suboptimal, but for such a size who cares ? */
/*TODO*///	static void sortlayers(int *layer, int *pri)
/*TODO*///	{
/*TODO*///	#define SWAP(a,b) \
/*TODO*///		if (pri[a] < pri[b]) \
/*TODO*///		{ \
/*TODO*///			int t; \
/*TODO*///			t = pri[a]; pri[a] = pri[b]; pri[b] = t; \
/*TODO*///			t = layer[a]; layer[a] = layer[b]; layer[b] = t; \
/*TODO*///		}
/*TODO*///	
/*TODO*///		SWAP(0, 1)
/*TODO*///		SWAP(0, 2)
/*TODO*///		SWAP(0, 3)
/*TODO*///		SWAP(1, 2)
/*TODO*///		SWAP(1, 3)
/*TODO*///		SWAP(2, 3)
/*TODO*///	}
	
	public static VhUpdatePtr xexex_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int[] layer=new int[4];
	
		bg_colorbase       = K053251_get_palette_index(K053251_CI1);
		sprite_colorbase   = K053251_get_palette_index(K053251_CI0);
		layer_colorbase[0] = K053251_get_palette_index(K053251_CI2);
		layer_colorbase[1] = K053251_get_palette_index(K053251_CI4);
		layer_colorbase[2] = K053251_get_palette_index(K053251_CI3);
		layer_colorbase[3] = 0x70;
	
		K054157_tilemap_update();
	
		palette_init_used_colors();
		K053247_mark_sprites_colors();
	
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		layer[0] = 0;
		layerpri[0] = K053251_get_priority(K053251_CI2);
		layer[1] = 1;
		layerpri[1] = K053251_get_priority(K053251_CI4);
		layer[2] = 2;
		layerpri[2] = K053251_get_priority(K053251_CI3);
		layer[3] = -1;
		layerpri[3] = -1 /*K053251_get_priority(K053251_CI1)*/;
	
		//sortlayers(layer, layerpri);
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
                if (layerpri[0] < layerpri[3]) {
                    int t;
                    t = layerpri[0];
                    layerpri[0] = layerpri[3];
                    layerpri[3] = t;
                    t = layer[0];
                    layer[0] = layer[3];
                    layer[3] = t;
                }
                //SWAP(1, 2)
                if (layerpri[1] < layerpri[2]) {
                    int t;
                    t = layerpri[1];
                    layerpri[1] = layerpri[2];
                    layerpri[2] = t;
                    t = layer[1];
                    layer[1] = layer[2];
                    layer[2] = t;
                }
		//SWAP(1, 3)
                if (layerpri[1] < layerpri[3]) {
                    int t;
                    t = layerpri[1];
                    layerpri[1] = layerpri[3];
                    layerpri[3] = t;
                    t = layer[1];
                    layer[1] = layer[3];
                    layer[3] = t;
                }
		//SWAP(2, 3)
                if (layerpri[2] < layerpri[3]) {
                    int t;
                    t = layerpri[2];
                    layerpri[2] = layerpri[3];
                    layerpri[3] = t;
                    t = layer[2];
                    layer[2] = layer[3];
                    layer[3] = t;
                }
	
		fillbitmap(priority_bitmap, 0, null);
		fillbitmap(bitmap, Machine.pens[0], Machine.visible_area);
		K054157_tilemap_draw(bitmap, layer[0], 1<<16);
		K054157_tilemap_draw(bitmap, layer[1], 2<<16);
		K054157_tilemap_draw(bitmap, layer[2], 4<<16);
	
		K053247_sprites_draw(bitmap);
	
		K054157_tilemap_draw(bitmap, 3, 0);
	} };
}
