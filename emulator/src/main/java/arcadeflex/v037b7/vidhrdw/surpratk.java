/*
 * ported to v0.37b7
 * ported to v0.36
 *
 */
package arcadeflex.v037b7.vidhrdw;

//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import static arcadeflex.v037b7.vidhrdw.konamiicH.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.commonH.REGION_GFX1;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.commonH.REGION_GFX2;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import gr.codebb.arcadeflex.WIP.v037b7.mame.osdependH.osd_bitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_init_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.priority_bitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tile_info;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_pixels_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_render;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILE_FLIPX;
import gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_callbackProcPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_tilemap_draw;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_tilemap_update;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_vh_start;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_vh_stop;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class surpratk {

    static int[] layer_colorbase = new int[3];
    public static int sprite_colorbase, bg_colorbase;
    static int[] layerpri = new int[3];

    /**
     * *************************************************************************
     *
     * Callbacks for the K052109
     *
     **************************************************************************
     */
    public static K052109_callbackProcPtr tile_callback = new K052109_callbackProcPtr() {
        public void handler(int layer, int bank, int[] code, int[] color) {
            tile_info.u32_flags = (color[0] & 0x80) != 0 ? (char) TILE_FLIPX : 0;
            code[0] |= ((color[0] & 0x03) << 8) | ((color[0] & 0x10) << 6) | ((color[0] & 0x0c) << 9) | (bank << 13);
            color[0] = layer_colorbase[layer] + ((color[0] & 0x60) >> 5);
        }
    };

    /**
     * *************************************************************************
     *
     * Callbacks for the K053245
     *
     **************************************************************************
     */
    public static K053245_callbackProcPtr sprite_callback = new K053245_callbackProcPtr() {
        public void handler(int[] code, int[] color, int[] priority_mask) {
            int pri = 0x20 | ((color[0] & 0x60) >> 2);
            if (pri <= layerpri[2]) {
                priority_mask[0] = 0;
            } else if (pri > layerpri[2] && pri <= layerpri[1]) {
                priority_mask[0] = 0xf0;
            } else if (pri > layerpri[1] && pri <= layerpri[0]) {
                priority_mask[0] = 0xf0 | 0xcc;
            } else {
                priority_mask[0] = 0xf0 | 0xcc | 0xaa;
            }

            color[0] = sprite_colorbase + (color[0] & 0x1f);
        }
    };

    /**
     * *************************************************************************
     *
     * Start the video hardware emulation.
     *
     **************************************************************************
     */
    public static VhStartPtr surpratk_vh_start = new VhStartPtr() {
        public int handler() {
            if (K052109_vh_start(REGION_GFX1, 0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, tile_callback) != 0) {
                return 1;
            }
            if (K053245_vh_start(REGION_GFX2, 0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, sprite_callback) != 0) {
                K052109_vh_stop();
                return 1;
            }

            return 0;
        }
    };

    public static VhStopPtr surpratk_vh_stop = new VhStopPtr() {
        public void handler() {
            K052109_vh_stop();
            K053245_vh_stop();
        }
    };

    /* useful function to sort the three tile layers by priority order */
 /*static void sortlayers(int *layer,int *pri)
	{
	#define SWAP(a,b) \
		if (pri[a] < pri[b]) \
		{ \
			int t; \
			t = pri[a]; pri[a] = pri[b]; pri[b] = t; \
			t = layer[a]; layer[a] = layer[b]; layer[b] = t; \
		}
	
		SWAP(0,1)
		SWAP(0,2)
		SWAP(1,2)
	}*/
    public static VhUpdatePtr surpratk_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int[] layer = new int[3];

            bg_colorbase = K053251_get_palette_index(K053251_CI0);
            sprite_colorbase = K053251_get_palette_index(K053251_CI1);
            layer_colorbase[0] = K053251_get_palette_index(K053251_CI2);
            layer_colorbase[1] = K053251_get_palette_index(K053251_CI4);
            layer_colorbase[2] = K053251_get_palette_index(K053251_CI3);

            K052109_tilemap_update();

            palette_init_used_colors();
            K053245_mark_sprites_colors();
            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            layer[0] = 0;
            layerpri[0] = K053251_get_priority(K053251_CI2);
            layer[1] = 1;
            layerpri[1] = K053251_get_priority(K053251_CI4);
            layer[2] = 2;
            layerpri[2] = K053251_get_priority(K053251_CI3);

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

            fillbitmap(priority_bitmap, 0, null);
            fillbitmap(bitmap, Machine.pens[16 * bg_colorbase], Machine.visible_area);
            K052109_tilemap_draw(bitmap, layer[0], 1 << 16);
            K052109_tilemap_draw(bitmap, layer[1], 2 << 16);
            K052109_tilemap_draw(bitmap, layer[2], 4 << 16);

            K053245_sprites_draw(bitmap);
        }
    };
}
