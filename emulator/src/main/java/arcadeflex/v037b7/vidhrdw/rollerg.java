/*
 * ported to v0.37b7
 * ported to v0.36
 */
package arcadeflex.v037b7.vidhrdw;

//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.mame.commonH.*;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import static arcadeflex.v037b7.mame.osdependH.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_init_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.priority_bitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tile_info;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_pixels_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_render;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILE_FLIPYX;
import gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_callbackProcPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_tilemap_update_0;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_vh_start_0;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_vh_stop_0;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_zoom_draw_0;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class rollerg {

    static int bg_colorbase, sprite_colorbase, zoom_colorbase;

    /**
     * *************************************************************************
     *
     * Callbacks for the K053245
     *
     **************************************************************************
     */
    public static K053245_callbackProcPtr sprite_callback = new K053245_callbackProcPtr() {
        public void handler(int[] code, int[] color, int[] priority_mask) {
            priority_mask[0] = (color[0] & 0x10) != 0 ? 0 : 0x02;
            color[0] = sprite_colorbase + (color[0] & 0x0f);
        }
    };

    /**
     * *************************************************************************
     *
     * Callbacks for the K051316
     *
     **************************************************************************
     */
    public static K051316_callbackProcPtr zoom_callback = new K051316_callbackProcPtr() {
        public void handler(int[] code, int[] color) {
            tile_info.u32_flags = (char) (TILE_FLIPYX((color[0] & 0xc0) >> 6));
            code[0] |= ((color[0] & 0x0f) << 8);
            color[0] = zoom_colorbase + ((color[0] & 0x30) >> 4);
        }
    };

    /**
     * *************************************************************************
     *
     * Start the video hardware emulation.
     *
     **************************************************************************
     */
    public static VhStartPtr rollerg_vh_start = new VhStartPtr() {
        public int handler() {
            bg_colorbase = 16;
            sprite_colorbase = 16;
            zoom_colorbase = 0;

            if (K053245_vh_start(REGION_GFX1, 0, 1, 2, 3/*NORMAL_PLANE_ORDER*/, sprite_callback) != 0) {
                return 1;
            }
            if (K051316_vh_start_0(REGION_GFX2, 4, zoom_callback) != 0) {
                K053245_vh_stop();
                return 1;
            }

            K051316_set_offset(0, 22, 1);
            return 0;
        }
    };

    public static VhStopPtr rollerg_vh_stop = new VhStopPtr() {
        public void handler() {
            K053245_vh_stop();
            K051316_vh_stop_0();
        }
    };

    /**
     * *************************************************************************
     *
     * Display refresh
     *
     **************************************************************************
     */
    public static VhUpdatePtr rollerg_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int i;

            K051316_tilemap_update_0();

            palette_init_used_colors();
            K053245_mark_sprites_colors();
            /* set back pen for the zoom layer */
            for (i = 0; i < 16; i++) {
                palette_used_colors.write((zoom_colorbase + i) * 16, PALETTE_COLOR_TRANSPARENT);
            }
            palette_used_colors.write(16 * bg_colorbase, palette_used_colors.read(16 * bg_colorbase) | PALETTE_COLOR_VISIBLE);
            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            fillbitmap(priority_bitmap, 0, null);
            fillbitmap(bitmap, Machine.pens[16 * bg_colorbase], Machine.visible_area);
            K051316_zoom_draw_0(bitmap, 1);

            K053245_sprites_draw(bitmap);
        }
    };
}
