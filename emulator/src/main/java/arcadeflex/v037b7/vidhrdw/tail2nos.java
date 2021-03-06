/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.vidhrdw;

//generic imports
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.memory_region;
//mame imports
import static arcadeflex.v037b7.mame.commonH.*;
import arcadeflex.v037b7.mame.drawgfxH.GfxLayout;
import static arcadeflex.v037b7.mame.drawgfxH.TRANSPARENCY_PEN;
import static arcadeflex.v037b7.mame.memoryH.COMBINE_WORD;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.vidhrdw.generic.spriteram;
import static arcadeflex.v037b7.vidhrdw.generic.spriteram_size;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.decodechar;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.*;
import static gr.codebb.arcadeflex.common.libc.cstring.memset;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class tail2nos {

    public static UBytePtr tail2nos_bgvideoram = new UBytePtr();

    static struct_tilemap bg_tilemap;

    static int charbank, charpalette, video_enable;
    static UBytePtr zoomdata;
    static int dirtygfx;
    static char[] dirtychar;

    public static final int TOTAL_CHARS = 0x400;

    /**
     * *************************************************************************
     *
     * Callbacks for the TileMap code
     *
     **************************************************************************
     */
    public static GetTileInfoPtr get_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int code = tail2nos_bgvideoram.READ_WORD(2 * tile_index);
            SET_TILE_INFO(0, (code & 0x1fff) + (charbank << 13), ((code & 0xe000) >> 13) + charpalette * 16);
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
            code[0] |= ((color[0] & 0x03) << 8);
            color[0] = 32 + ((color[0] & 0x38) >> 3);
        }
    };

    /**
     * *************************************************************************
     *
     * Start the video hardware emulation.
     *
     **************************************************************************
     */
    public static VhStartPtr tail2nos_vh_start = new VhStartPtr() {
        public int handler() {
            bg_tilemap = tilemap_create(get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 8, 8, 64, 32);

            if (bg_tilemap == null) {
                return 1;
            }

            if (K051316_vh_start_0(REGION_GFX3, 4, zoom_callback) != 0) {
                return 1;
            }

            if ((dirtychar = new char[TOTAL_CHARS]) == null) {
                K051316_vh_stop_0();
                return 1;
            }
            memset(dirtychar, 1, TOTAL_CHARS);

            bg_tilemap.transparent_pen = 15;

            K051316_wraparound_enable(0, 1);
            K051316_set_offset(0, -89, -14);
            zoomdata = memory_region(REGION_GFX3);

            return 0;
        }
    };

    public static VhStopPtr tail2nos_vh_stop = new VhStopPtr() {
        public void handler() {
            K051316_vh_stop_0();
            dirtychar = null;
        }
    };

    /**
     * *************************************************************************
     *
     * Memory handlers
     *
     **************************************************************************
     */
    public static ReadHandlerPtr tail2nos_bgvideoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return tail2nos_bgvideoram.READ_WORD(offset);
        }
    };

    public static WriteHandlerPtr tail2nos_bgvideoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = tail2nos_bgvideoram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);

            if (oldword != newword) {
                tail2nos_bgvideoram.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(bg_tilemap, offset / 2);
            }
        }
    };

    public static ReadHandlerPtr tail2nos_zoomdata_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return zoomdata.READ_WORD(offset);
        }
    };

    public static WriteHandlerPtr tail2nos_zoomdata_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = zoomdata.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);

            if (oldword != newword) {
                dirtygfx = 1;
                dirtychar[offset / 128] = 1;
                zoomdata.WRITE_WORD(offset, newword);
            }
        }
    };

    public static WriteHandlerPtr tail2nos_gfxbank_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if ((data & 0x00ff0000) == 0) {
                int bank;

                /* bits 0 and 2 select char bank */
                if ((data & 0x04) != 0) {
                    bank = 2;
                } else if ((data & 0x01) != 0) {
                    bank = 1;
                } else {
                    bank = 0;
                }

                if (charbank != bank) {
                    charbank = bank;
                    tilemap_mark_all_tiles_dirty(bg_tilemap);
                }

                /* bit 5 seems to select palette bank (used on startup) */
                if ((data & 0x20) != 0) {
                    bank = 7;
                } else {
                    bank = 3;
                }

                if (charpalette != bank) {
                    charpalette = bank;
                    tilemap_mark_all_tiles_dirty(bg_tilemap);
                }

                /* bit 4 seems to be video enable */
                video_enable = data & 0x10;
            }
        }
    };

    /**
     * *************************************************************************
     *
     * Display Refresh
     *
     **************************************************************************
     */
    static void drawsprites(osd_bitmap bitmap) {
        int offs;

        for (offs = 0; offs < spriteram_size[0]; offs += 8) {
            int sx, sy, flipx, flipy, code, color;

            sx = spriteram.READ_WORD(offs + 2);
            if (sx >= 0x8000) {
                sx -= 0x10000;
            }
            sy = 0x10000 - spriteram.READ_WORD(offs + 0);
            if (sy >= 0x8000) {
                sy -= 0x10000;
            }
            code = spriteram.READ_WORD(offs + 4) & 0x07ff;
            color = (spriteram.READ_WORD(offs + 4) & 0xe000) >> 13;
            flipx = spriteram.READ_WORD(offs + 4) & 0x1000;
            flipy = spriteram.READ_WORD(offs + 4) & 0x0800;

            drawgfx(bitmap, Machine.gfx[1],
                    code,
                    40 + color,
                    flipx, flipy,
                    sx + 3, sy + 1, /* placement relative to zoom layer verified on the real thing */
                    Machine.visible_area, TRANSPARENCY_PEN, 15);
        }
    }

    public static VhUpdatePtr tail2nos_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            GfxLayout tilelayout = new GfxLayout(
                    16, 16,
                    TOTAL_CHARS,
                    4,
                    new int[]{0, 1, 2, 3},
                    new int[]{2 * 4, 3 * 4, 0 * 4, 1 * 4, 6 * 4, 7 * 4, 4 * 4, 5 * 4,
                        10 * 4, 11 * 4, 8 * 4, 9 * 4, 14 * 4, 15 * 4, 12 * 4, 13 * 4},
                    new int[]{0 * 64, 1 * 64, 2 * 64, 3 * 64, 4 * 64, 5 * 64, 6 * 64, 7 * 64,
                        8 * 64, 9 * 64, 10 * 64, 11 * 64, 12 * 64, 13 * 64, 14 * 64, 15 * 64},
                    128 * 8
            );

            if (dirtygfx != 0) {
                int i;

                dirtygfx = 0;

                for (i = 0; i < TOTAL_CHARS; i++) {
                    if (dirtychar[i] != 0) {
                        dirtychar[i] = 0;
                        decodechar(Machine.gfx[2], i, zoomdata, tilelayout);
                    }
                }

                tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
            }

            K051316_tilemap_update_0();
            tilemap_update(bg_tilemap);

            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            if (video_enable != 0) {
                K051316_zoom_draw_0(bitmap, 0);
                drawsprites(bitmap);
                tilemap_draw(bitmap, bg_tilemap, 0);
            } else {
                fillbitmap(bitmap, Machine.pens[0], Machine.visible_area);
            }
        }
    };
}
