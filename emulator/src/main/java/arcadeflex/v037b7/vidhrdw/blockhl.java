/*
 * ported to v0.37b7
 * ported to v0.36
 */
package arcadeflex.v037b7.vidhrdw;

//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.commonH.*;

import gr.codebb.arcadeflex.WIP.v037b7.mame.osdependH.osd_bitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_init_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_pixels_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_render;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_IGNORE_TRANSPARENCY;
import gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051960_callbackProcPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051960_mark_sprites_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051960_sprites_draw;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051960_vh_start;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051960_vh_stop;
import gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_callbackProcPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_tilemap_draw;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_tilemap_update;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_vh_start;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_vh_stop;

public class blockhl {

    static int[] layer_colorbase = new int[3];
    public static int sprite_colorbase;

    /**
     * *************************************************************************
     *
     * Callbacks for the K052109
     *
     **************************************************************************
     */
    public static K052109_callbackProcPtr tile_callback = new K052109_callbackProcPtr() {
        public void handler(int layer, int bank, int[] code, int[] color) {
            code[0] |= ((color[0] & 0x0f) << 8);
            color[0] = layer_colorbase[layer] + ((color[0] & 0xe0) >> 5);
        }
    };

    /**
     * *************************************************************************
     *
     * Callbacks for the K051960
     *
     **************************************************************************
     */
    public static K051960_callbackProcPtr sprite_callback = new K051960_callbackProcPtr() {
        public void handler(int[] code, int[] color, int[] priority, int[] shadow) {
            priority[0] = (color[0] & 0x10) >> 4;
            color[0] = sprite_colorbase + (color[0] & 0x0f);
        }
    };

    /**
     * *************************************************************************
     *
     * Start the video hardware emulation.
     *
     **************************************************************************
     */
    public static VhStartPtr blockhl_vh_start = new VhStartPtr() {
        public int handler() {
            layer_colorbase[0] = 0;
            layer_colorbase[1] = 16;
            layer_colorbase[2] = 32;
            sprite_colorbase = 48;

            if (K052109_vh_start(REGION_GFX1, 0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, tile_callback) != 0) {
                return 1;
            }
            if (K051960_vh_start(REGION_GFX2, 0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, sprite_callback) != 0) {
                K052109_vh_stop();
                return 1;
            }

            return 0;
        }
    };

    public static VhStopPtr blockhl_vh_stop = new VhStopPtr() {
        public void handler() {
            K052109_vh_stop();
            K051960_vh_stop();
        }
    };

    public static VhUpdatePtr blockhl_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            K052109_tilemap_update();

            palette_init_used_colors();
            K051960_mark_sprites_colors();
            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            K052109_tilemap_draw(bitmap, 2, TILEMAP_IGNORE_TRANSPARENCY);
            K051960_sprites_draw(bitmap, 1, 1);
            K052109_tilemap_draw(bitmap, 1, 0);
            K051960_sprites_draw(bitmap, 0, 0);
            K052109_tilemap_draw(bitmap, 0, 0);
        }
    };
}
