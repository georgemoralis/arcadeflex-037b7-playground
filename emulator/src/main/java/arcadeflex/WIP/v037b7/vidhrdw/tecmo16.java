/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.WIP.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.libc.expressions.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.mame.paletteH.*;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.generic.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.pdrawgfx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.priority_bitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_create;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_draw;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_pixels_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_tile_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_render;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_scan_rows;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scrollx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scrolly;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_update;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.GetTileInfoPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.SET_TILE_INFO;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_TRANSPARENT;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.struct_tilemap;
import static gr.codebb.arcadeflex.common.libc.cstring.memset;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class tecmo16 {

    public static UBytePtr tecmo16_videoram = new UBytePtr();
    public static UBytePtr tecmo16_colorram = new UBytePtr();
    public static UBytePtr tecmo16_videoram2 = new UBytePtr();
    public static UBytePtr tecmo16_colorram2 = new UBytePtr();
    public static UBytePtr tecmo16_charram = new UBytePtr();

    static struct_tilemap fg_tilemap, bg_tilemap, tx_tilemap;

    /**
     * ***************************************************************************
     */
    public static GetTileInfoPtr fg_get_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int offs = tile_index * 2;
            int tile = tecmo16_videoram.READ_WORD(offs) & 0x1fff;
            int color = tecmo16_colorram.READ_WORD(offs) & 0x7f;

            SET_TILE_INFO(1, tile, color);
        }
    };

    public static GetTileInfoPtr bg_get_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int offs = tile_index * 2;
            int tile = tecmo16_videoram2.READ_WORD(offs) & 0x1fff;
            int color = (tecmo16_colorram2.READ_WORD(offs) & 0x7f) + 0x10;

            SET_TILE_INFO(1, tile, color);
        }
    };

    public static GetTileInfoPtr tx_get_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int offs = tile_index * 2;
            int tile = tecmo16_charram.READ_WORD(offs) & 0x0fff;
            int color = (tecmo16_charram.READ_WORD(offs) >> 12) & 0x0f;

            SET_TILE_INFO(0, tile, color);
        }
    };

    /**
     * ***************************************************************************
     */
    public static VhStartPtr fstarfrc_vh_start = new VhStartPtr() {
        public int handler() {
            fg_tilemap = tilemap_create(fg_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 16, 16, 32, 32);
            bg_tilemap = tilemap_create(bg_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 16, 16, 32, 32);
            tx_tilemap = tilemap_create(tx_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 8, 8, 64, 32);

            if (fg_tilemap == null || bg_tilemap == null || tx_tilemap == null) {
                return 1;
            }

            fg_tilemap.transparent_pen = 0;
            bg_tilemap.transparent_pen = 0;
            tx_tilemap.transparent_pen = 0;

            tilemap_set_scrolly(tx_tilemap, 0, -16);

            return 0;
        }
    };

    public static VhStartPtr ginkun_vh_start = new VhStartPtr() {
        public int handler() {
            fg_tilemap = tilemap_create(fg_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 16, 16, 64, 32);
            bg_tilemap = tilemap_create(bg_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 16, 16, 64, 32);
            tx_tilemap = tilemap_create(tx_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 8, 8, 64, 32);

            if (fg_tilemap == null || bg_tilemap == null || tx_tilemap == null) {
                return 1;
            }

            fg_tilemap.transparent_pen = 0;
            bg_tilemap.transparent_pen = 0;
            tx_tilemap.transparent_pen = 0;

            return 0;
        }
    };

    /**
     * ***************************************************************************
     */
    public static ReadHandlerPtr tecmo16_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return tecmo16_videoram.READ_WORD(offset);
        }
    };

    public static ReadHandlerPtr tecmo16_colorram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return tecmo16_colorram.READ_WORD(offset);
        }
    };

    public static ReadHandlerPtr tecmo16_videoram2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return tecmo16_videoram2.READ_WORD(offset);
        }
    };

    public static ReadHandlerPtr tecmo16_colorram2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return tecmo16_colorram2.READ_WORD(offset);
        }
    };

    public static ReadHandlerPtr tecmo16_charram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return tecmo16_charram.READ_WORD(offset);
        }
    };

    public static ReadHandlerPtr tecmo16_spriteram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return spriteram.READ_WORD(offset);
        }
    };

    public static WriteHandlerPtr tecmo16_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = tecmo16_videoram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);

            if (oldword != newword) {
                tecmo16_videoram.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(fg_tilemap, offset / 2);
            }
        }
    };

    public static WriteHandlerPtr tecmo16_colorram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = tecmo16_colorram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);

            if (oldword != newword) {
                tecmo16_colorram.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(fg_tilemap, offset / 2);
            }
        }
    };

    public static WriteHandlerPtr tecmo16_videoram2_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = tecmo16_videoram2.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);

            if (oldword != newword) {
                tecmo16_videoram2.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(bg_tilemap, offset / 2);
            }
        }
    };

    public static WriteHandlerPtr tecmo16_colorram2_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = tecmo16_colorram2.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);

            if (oldword != newword) {
                tecmo16_colorram2.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(bg_tilemap, offset / 2);
            }
        }
    };

    public static WriteHandlerPtr tecmo16_charram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = tecmo16_charram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);

            if (oldword != newword) {
                tecmo16_charram.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(tx_tilemap, offset / 2);
            }
        }
    };

    public static WriteHandlerPtr tecmo16_spriteram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            COMBINE_WORD_MEM(spriteram, offset, data);
        }
    };

    /**
     * ***************************************************************************
     */
    public static WriteHandlerPtr tecmo16_scroll_x_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            tilemap_set_scrollx(fg_tilemap, 0, data);
        }
    };

    public static WriteHandlerPtr tecmo16_scroll_y_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            tilemap_set_scrolly(fg_tilemap, 0, data);
        }
    };

    public static WriteHandlerPtr tecmo16_scroll2_x_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            tilemap_set_scrollx(bg_tilemap, 0, data);
        }
    };

    public static WriteHandlerPtr tecmo16_scroll2_y_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            tilemap_set_scrolly(bg_tilemap, 0, data);
        }
    };

    public static WriteHandlerPtr tecmo16_scroll_char_x_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            tilemap_set_scrollx(tx_tilemap, 0, data);
        }
    };

    public static WriteHandlerPtr tecmo16_scroll_char_y_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            tilemap_set_scrolly(tx_tilemap, 0, data - 16);
        }
    };

    /**
     * ***************************************************************************
     */
    static void draw_sprites(osd_bitmap bitmap) {
        int offs;
        int layout[][]
                = {
                    {0, 1, 4, 5, 16, 17, 20, 21},
                    {2, 3, 6, 7, 18, 19, 22, 23},
                    {8, 9, 12, 13, 24, 25, 28, 29},
                    {10, 11, 14, 15, 26, 27, 30, 31},
                    {32, 33, 36, 37, 48, 49, 52, 53},
                    {34, 35, 38, 39, 50, 51, 54, 55},
                    {40, 41, 44, 45, 56, 57, 60, 61},
                    {42, 43, 46, 47, 58, 59, 62, 63}
                };

        for (offs = spriteram_size[0] - 0x10; offs >= 0; offs -= 0x10) {
            if ((spriteram.READ_WORD(offs) & 0x04) != 0) /* enable */ {
                int code, color, sizex, sizey, flipx, flipy, xpos, ypos;
                int x, y, priority, priority_mask;

                code = spriteram.READ_WORD(offs + 0x02);
                color = (spriteram.READ_WORD(offs + 0x04) & 0xf0) >> 4;
                sizex = 1 << ((spriteram.READ_WORD(offs + 0x04) & 0x03) >> 0);
                sizey = 1 << ((spriteram.READ_WORD(offs + 0x04) & 0x0c) >> 2);
                if (sizex >= 2) {
                    code &= ~0x01;
                }
                if (sizey >= 2) {
                    code &= ~0x02;
                }
                if (sizex >= 4) {
                    code &= ~0x04;
                }
                if (sizey >= 4) {
                    code &= ~0x08;
                }
                if (sizex >= 8) {
                    code &= ~0x10;
                }
                if (sizey >= 8) {
                    code &= ~0x20;
                }
                flipx = spriteram.READ_WORD(offs) & 0x01;
                flipy = spriteram.READ_WORD(offs) & 0x02;
                xpos = spriteram.READ_WORD(offs + 0x08);
                if (xpos >= 0x8000) {
                    xpos -= 0x10000;
                }
                ypos = spriteram.READ_WORD(offs + 0x06);
                if (ypos >= 0x8000) {
                    ypos -= 0x10000;
                }
                priority = (spriteram.READ_WORD(offs) & 0xc0) >> 6;

                /* bg: 1; fg:2; text: 4 */
                switch (priority) {
                    default:
                    case 0x0:
                        priority_mask = 0;
                        break;
                    case 0x1:
                        priority_mask = 0xf0;
                        break;
                    /* obscured by text layer */
                    case 0x2:
                        priority_mask = 0xf0 | 0xcc;
                        break;
                    /* obscured by foreground */
                    case 0x3:
                        priority_mask = 0xf0 | 0xcc | 0xaa;
                        break;
                    /* obscured by bg and fg */
                }

                for (y = 0; y < sizey; y++) {
                    for (x = 0; x < sizex; x++) {
                        int sx = xpos + 8 * (flipx != 0 ? (sizex - 1 - x) : x);
                        int sy = ypos + 8 * (flipy != 0 ? (sizey - 1 - y) : y);
                        pdrawgfx(bitmap, Machine.gfx[2],
                                code + layout[y][x],
                                color,
                                flipx, flipy,
                                sx, sy,
                                Machine.visible_area, TRANSPARENCY_PEN, 0,
                                priority_mask);
                    }
                }
            }
        }
    }

    static void mark_sprites_colors() {
        int offs, i;
        char[] palette_map = new char[16];

        memset(palette_map, 0, sizeof(palette_map));

        for (offs = 0; offs < spriteram_size[0]; offs += 0x10) {
            if ((spriteram.READ_WORD(offs) & 0x04) != 0) /* visible */ {
                int color;

                color = (spriteram.READ_WORD(offs + 0x04) & 0xf0) >> 4;
                palette_map[color] |= 0xffff;
            }
        }

        /* now build the final table */
        for (i = 0; i < 16; i++) {
            int usage = palette_map[i], j;
            if (usage != 0) {
                for (j = 1; j < 16; j++) {
                    if ((usage & (1 << j)) != 0) {
                        palette_used_colors.or(i * 16 + j, PALETTE_COLOR_VISIBLE);
                    }
                }
            }
        }
    }

    /**
     * ***************************************************************************
     */
    public static VhUpdatePtr tecmo16_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            tilemap_update(ALL_TILEMAPS);

            palette_init_used_colors();
            mark_sprites_colors();
            palette_used_colors.write(0x300, PALETTE_COLOR_USED);
            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            fillbitmap(priority_bitmap, 0, null);
            fillbitmap(bitmap, Machine.pens[0x300], Machine.visible_area);
            tilemap_draw(bitmap, bg_tilemap, 1 << 16);
            tilemap_draw(bitmap, fg_tilemap, 2 << 16);
            tilemap_draw(bitmap, tx_tilemap, 4 << 16);

            draw_sprites(bitmap);
        }
    };
}
