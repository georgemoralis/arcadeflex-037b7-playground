/*
 * ported to v0.37b7
 * ported to v0.36
 *
 */
package arcadeflex.v037b7.vidhrdw;

//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.commonH.*;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import static arcadeflex.v037b7.vidhrdw.konamiicH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class vendetta {

    static int[] layer_colorbase = new int[3];
    static int bg_colorbase, sprite_colorbase;
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
            code[0] |= ((color[0] & 0x03) << 8) | ((color[0] & 0x30) << 6)
                    | ((color[0] & 0x0c) << 10) | (bank << 14);
            color[0] = layer_colorbase[layer] + ((color[0] & 0xc0) >> 6);
        }
    };

    /**
     * *************************************************************************
     *
     * Callbacks for the K053247
     *
     **************************************************************************
     */
    public static K053247_callbackProcPtr sprite_callback = new K053247_callbackProcPtr() {
        public void handler(int[] code, int[] color, int[] priority_mask) {
            int pri = (color[0] & 0x03e0) >> 4;
            /* ??????? */
            if (pri <= layerpri[2]) {
                priority_mask[0] = 0;
            } else if (pri > layerpri[2] && pri <= layerpri[1]) {
                priority_mask[0] = 0xf0;
            } else if (pri > layerpri[1] && pri <= layerpri[0]) {
                priority_mask[0] = 0xf0 | 0xcc;
            } else {
                priority_mask[0] = 0xf0 | 0xcc | 0xaa;
            }

            color[0] = sprite_colorbase + (color[0] & 0x001f);
        }
    };

    /**
     * *************************************************************************
     *
     * Start the video hardware emulation.
     *
     **************************************************************************
     */
    public static VhStartPtr vendetta_vh_start = new VhStartPtr() {
        public int handler() {
            if (K052109_vh_start(REGION_GFX1, 0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, tile_callback) != 0) {
                return 1;
            }
            if (K053247_vh_start(REGION_GFX2, 53, 6, 0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, sprite_callback) != 0) {
                K052109_vh_stop();
                return 1;
            }
            return 0;
        }
    };

    public static VhStopPtr vendetta_vh_stop = new VhStopPtr() {
        public void handler() {
            K052109_vh_stop();
            K053247_vh_stop();
        }
    };

    /**
     * *************************************************************************
     *
     * Display refresh
     *
     **************************************************************************
     */
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
    public static VhUpdatePtr vendetta_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int[] layer = new int[3];

            bg_colorbase = K053251_get_palette_index(K053251_CI0);
            sprite_colorbase = K053251_get_palette_index(K053251_CI1);
            layer_colorbase[0] = K053251_get_palette_index(K053251_CI2);
            layer_colorbase[1] = K053251_get_palette_index(K053251_CI3);
            layer_colorbase[2] = K053251_get_palette_index(K053251_CI4);

            K052109_tilemap_update();

            palette_init_used_colors();
            K053247_mark_sprites_colors();
            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            layer[0] = 0;
            layerpri[0] = K053251_get_priority(K053251_CI2);
            layer[1] = 1;
            layerpri[1] = K053251_get_priority(K053251_CI3);
            layer[2] = 2;
            layerpri[2] = K053251_get_priority(K053251_CI4);

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
            K052109_tilemap_draw(bitmap, layer[0], TILEMAP_IGNORE_TRANSPARENCY | (1 << 16));
            K052109_tilemap_draw(bitmap, layer[1], 2 << 16);
            K052109_tilemap_draw(bitmap, layer[2], 4 << 16);

            K053247_sprites_draw(bitmap);
        }
    };
}
