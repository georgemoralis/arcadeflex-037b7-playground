/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.libc.expressions.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.generic.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tile_info;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_create;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_draw;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_tile_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_render;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scrollx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scrolly;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_update;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.GetMemoryOffsetPtr;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.GetTileInfoPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.SET_TILE_INFO;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_OPAQUE;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_TRANSPARENT;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILE_FLIPXY;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.struct_tilemap;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;

public class firetrap {

    public static UBytePtr firetrap_bg1videoram = new UBytePtr();
    public static UBytePtr firetrap_bg2videoram = new UBytePtr();
    public static UBytePtr firetrap_fgvideoram = new UBytePtr();

    static struct_tilemap fg_tilemap, bg1_tilemap, bg2_tilemap;

    /**
     * *************************************************************************
     *
     * Convert the color PROMs into a more useable format.
     *
     * Fire Trap has one 256x8 and one 256x4 palette PROMs. I don't know for
     * sure how the palette PROMs are connected to the RGB output, but it's
     * probably the usual:
     *
     * bit 7 -- 220 ohm resistor -- GREEN -- 470 ohm resistor -- GREEN -- 1 kohm
     * resistor -- GREEN -- 2.2kohm resistor -- GREEN -- 220 ohm resistor -- RED
     * -- 470 ohm resistor -- RED -- 1 kohm resistor -- RED bit 0 -- 2.2kohm
     * resistor -- RED
     *
     * bit 3 -- 220 ohm resistor -- BLUE -- 470 ohm resistor -- BLUE -- 1 kohm
     * resistor -- BLUE bit 0 -- 2.2kohm resistor -- BLUE
     *
     **************************************************************************
     */
    public static VhConvertColorPromPtr firetrap_vh_convert_color_prom = new VhConvertColorPromPtr() {
        public void handler(char[] palette, char[] colortable, UBytePtr color_prom) {
            int i;

            int p_inc = 0;
            for (i = 0; i < 256; i++) {
                int bit0, bit1, bit2, bit3;

                bit0 = (color_prom.read(0) >> 0) & 0x01;
                bit1 = (color_prom.read(0) >> 1) & 0x01;
                bit2 = (color_prom.read(0) >> 2) & 0x01;
                bit3 = (color_prom.read(0) >> 3) & 0x01;
                palette[p_inc++] = ((char) (0x0e * bit0 + 0x1f * bit1 + 0x43 * bit2 + 0x8f * bit3));
                bit0 = (color_prom.read(0) >> 4) & 0x01;
                bit1 = (color_prom.read(0) >> 5) & 0x01;
                bit2 = (color_prom.read(0) >> 6) & 0x01;
                bit3 = (color_prom.read(0) >> 7) & 0x01;
                palette[p_inc++] = ((char) (0x0e * bit0 + 0x1f * bit1 + 0x43 * bit2 + 0x8f * bit3));
                bit0 = (color_prom.read(256) >> 0) & 0x01;
                bit1 = (color_prom.read(256) >> 1) & 0x01;
                bit2 = (color_prom.read(256) >> 2) & 0x01;
                bit3 = (color_prom.read(256) >> 3) & 0x01;
                palette[p_inc++] = ((char) (0x0e * bit0 + 0x1f * bit1 + 0x43 * bit2 + 0x8f * bit3));

                color_prom.inc();
            }
        }
    };

    /**
     * *************************************************************************
     *
     * Callbacks for the TileMap code
     *
     **************************************************************************
     */
    public static GetMemoryOffsetPtr get_fg_memory_offset = new GetMemoryOffsetPtr() {
        public int handler(int u32_col, int u32_row, int u32_num_cols, int u32_num_rows) {
            return (u32_row ^ 0x1f) + (u32_col << 5);
        }
    };
    public static GetMemoryOffsetPtr get_bg_memory_offset = new GetMemoryOffsetPtr() {
        public int handler(int u32_col, int u32_row, int u32_num_cols, int u32_num_rows) {
            return ((u32_row & 0x0f) ^ 0x0f) | ((u32_col & 0x0f) << 4)
                    | /* hole at bit 8 */ ((u32_row & 0x10) << 5) | ((u32_col & 0x10) << 6);
        }
    };

    public static GetTileInfoPtr get_fg_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int code, color;

            code = firetrap_fgvideoram.read(tile_index);
            color = firetrap_fgvideoram.read(tile_index + 0x400);
            SET_TILE_INFO(0, code | ((color & 0x01) << 8), color >> 4);
        }
    };

    public static void get_bg_tile_info(int tile_index, UBytePtr bgvideoram, int gfx_region) {
        int code, color;

        code = bgvideoram.read(tile_index);
        color = bgvideoram.read(tile_index + 0x100);
        SET_TILE_INFO(gfx_region, code + ((color & 0x03) << 8), (color & 0x30) >> 4);
        tile_info.u32_flags = TILE_FLIPXY((color & 0x0c) >> 2);
    }

    public static GetTileInfoPtr get_bg1_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            get_bg_tile_info(tile_index, firetrap_bg1videoram, 1);
        }
    };

    public static GetTileInfoPtr get_bg2_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            get_bg_tile_info(tile_index, firetrap_bg2videoram, 2);
        }
    };

    /**
     * *************************************************************************
     *
     * Start the video hardware emulation.
     *
     **************************************************************************
     */
    public static VhStartPtr firetrap_vh_start = new VhStartPtr() {
        public int handler() {
            fg_tilemap = tilemap_create(get_fg_tile_info, get_fg_memory_offset, TILEMAP_TRANSPARENT, 8, 8, 32, 32);
            bg1_tilemap = tilemap_create(get_bg1_tile_info, get_bg_memory_offset, TILEMAP_TRANSPARENT, 16, 16, 32, 32);
            bg2_tilemap = tilemap_create(get_bg2_tile_info, get_bg_memory_offset, TILEMAP_OPAQUE, 16, 16, 32, 32);

            if (fg_tilemap == null || bg1_tilemap == null || bg2_tilemap == null) {
                return 1;
            }

            fg_tilemap.transparent_pen = 0;
            bg1_tilemap.transparent_pen = 0;

            return 0;
        }
    };

    /**
     * *************************************************************************
     *
     * Memory handlers
     *
     **************************************************************************
     */
    public static WriteHandlerPtr firetrap_fgvideoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            firetrap_fgvideoram.write(offset, data);
            tilemap_mark_tile_dirty(fg_tilemap, offset & 0x3ff);
        }
    };

    public static WriteHandlerPtr firetrap_bg1videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            firetrap_bg1videoram.write(offset, data);
            tilemap_mark_tile_dirty(bg1_tilemap, offset & 0x6ff);
        }
    };

    public static WriteHandlerPtr firetrap_bg2videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            firetrap_bg2videoram.write(offset, data);
            tilemap_mark_tile_dirty(bg2_tilemap, offset & 0x6ff);
        }
    };

    static /*unsigned*/ char[] scroll_1 = new char[2];
    public static WriteHandlerPtr firetrap_bg1_scrollx_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            scroll_1[offset] = (char) (data & 0xFF);
            tilemap_set_scrollx(bg1_tilemap, 0, scroll_1[0] | (scroll_1[1] << 8));
        }
    };
    static /*unsigned*/ char[] scroll_2 = new char[2];
    public static WriteHandlerPtr firetrap_bg1_scrolly_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            scroll_2[offset] = (char) (data & 0xFF);
            tilemap_set_scrolly(bg1_tilemap, 0, -(scroll_2[0] | (scroll_2[1] << 8)));
        }
    };
    static /*unsigned*/ char[] scroll_3 = new char[2];
    public static WriteHandlerPtr firetrap_bg2_scrollx_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            scroll_3[offset] = (char) (data & 0xFF);
            tilemap_set_scrollx(bg2_tilemap, 0, scroll_3[0] | (scroll_3[1] << 8));
        }
    };
    static /*unsigned*/ char[] scroll_4 = new char[2];
    public static WriteHandlerPtr firetrap_bg2_scrolly_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            scroll_4[offset] = (char) (data & 0xFF);
            tilemap_set_scrolly(bg2_tilemap, 0, -(scroll_4[0] | (scroll_4[1] << 8)));
        }
    };

    /**
     * *************************************************************************
     *
     * Display refresh
     *
     **************************************************************************
     */
    static void draw_sprites(osd_bitmap bitmap) {
        int offs;

        for (offs = 0; offs < spriteram_size[0]; offs += 4) {
            int sx, sy, flipx, flipy, code, color;

            /* the meaning of bit 3 of [offs] is unknown */
            sy = spriteram.read(offs);
            sx = spriteram.read(offs + 2);
            code = spriteram.read(offs + 3) + 4 * (spriteram.read(offs + 1) & 0xc0);
            color = ((spriteram.read(offs + 1) & 0x08) >> 2) | (spriteram.read(offs + 1) & 0x01);
            flipx = spriteram.read(offs + 1) & 0x04;
            flipy = spriteram.read(offs + 1) & 0x02;
            if (flip_screen() != 0) {
                sx = 240 - sx;
                sy = 240 - sy;
                flipx = NOT(flipx);
                flipy = NOT(flipy);
            }

            if ((spriteram.read(offs + 1) & 0x10) != 0) /* double width */ {
                if (flip_screen() != 0) {
                    sy -= 16;
                }

                drawgfx(bitmap, Machine.gfx[3],
                        code & ~1,
                        color,
                        flipx, flipy,
                        sx, flipy != 0 ? sy : sy + 16,
                        Machine.visible_area, TRANSPARENCY_PEN, 0);
                drawgfx(bitmap, Machine.gfx[3],
                        code | 1,
                        color,
                        flipx, flipy,
                        sx, flipy != 0 ? sy + 16 : sy,
                        Machine.visible_area, TRANSPARENCY_PEN, 0);

                /* redraw with wraparound */
                drawgfx(bitmap, Machine.gfx[3],
                        code & ~1,
                        color,
                        flipx, flipy,
                        sx - 256, flipy != 0 ? sy : sy + 16,
                        Machine.visible_area, TRANSPARENCY_PEN, 0);
                drawgfx(bitmap, Machine.gfx[3],
                        code | 1,
                        color,
                        flipx, flipy,
                        sx - 256, flipy != 0 ? sy + 16 : sy,
                        Machine.visible_area, TRANSPARENCY_PEN, 0);
            } else {
                drawgfx(bitmap, Machine.gfx[3],
                        code,
                        color,
                        flipx, flipy,
                        sx, sy,
                        Machine.visible_area, TRANSPARENCY_PEN, 0);

                /* redraw with wraparound */
                drawgfx(bitmap, Machine.gfx[3],
                        code,
                        color,
                        flipx, flipy,
                        sx - 256, sy,
                        Machine.visible_area, TRANSPARENCY_PEN, 0);
            }
        }
    }

    public static VhUpdatePtr firetrap_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            tilemap_update(ALL_TILEMAPS);
            tilemap_render(ALL_TILEMAPS);

            tilemap_draw(bitmap, bg2_tilemap, 0);
            tilemap_draw(bitmap, bg1_tilemap, 0);
            draw_sprites(bitmap);
            tilemap_draw(bitmap, fg_tilemap, 0);
        }
    };
}
