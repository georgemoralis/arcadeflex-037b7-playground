/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.libc.cstring.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
//sound imports
import static arcadeflex.v037b7.sound.okim6295.*;
import static arcadeflex.v037b7.sound.okim6295H.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tile_info;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_create;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_draw;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_pixels_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_tile_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_render;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_scan_rows;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_flip;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_update;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.GetTileInfoPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.SET_TILE_INFO;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_FLIPX;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_FLIPY;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_OPAQUE;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILE_FLIPX;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.struct_tilemap;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;

public class mitchell {

    /* Globals */
    public static int[] pang_videoram_size = new int[1];
    public static UBytePtr pang_videoram = new UBytePtr();
    public static UBytePtr pang_colorram = new UBytePtr();

    /* Private */
    static UBytePtr pang_objram;/* Sprite RAM */

    static struct_tilemap bg_tilemap;
    static int flipscreen;

    /**
     * *************************************************************************
     *
     * Callbacks for the TileMap code
     *
     **************************************************************************
     */
    public static GetTileInfoPtr get_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            /*unsigned*/ char attr = pang_colorram.read(tile_index);
            int code = pang_videoram.read(2 * tile_index) + (pang_videoram.read(2 * tile_index + 1) << 8);
            SET_TILE_INFO(0, code, attr & 0x7f);
            tile_info.u32_flags = (attr & 0x80) != 0 ? TILE_FLIPX : 0;
        }
    };

    /**
     * *************************************************************************
     *
     * Start the video hardware emulation.
     *
     **************************************************************************
     */
    public static VhStartPtr pang_vh_start = new VhStartPtr() {
        public int handler() {
            pang_objram = null;
            paletteram = null;

            bg_tilemap = tilemap_create(get_tile_info, tilemap_scan_rows, TILEMAP_OPAQUE, 8, 8, 64, 32);

            if (bg_tilemap == null) {
                return 1;
            }

            /*
			OBJ RAM
             */
            pang_objram = new UBytePtr(pang_videoram_size[0]);
            if (pang_objram == null) {
                pang_vh_stop.handler();
                return 1;
            }
            memset(pang_objram, 0, pang_videoram_size[0]);

            /*
			Palette RAM
             */
            paletteram = new UBytePtr(2 * Machine.drv.total_colors);
            if (paletteram == null) {
                pang_vh_stop.handler();
                return 1;
            }
            memset(paletteram, 0, 2 * Machine.drv.total_colors);

            palette_transparent_color = 0;
            /* background color (Block Block uses this on the title screen) */

            return 0;
        }
    };

    public static VhStopPtr pang_vh_stop = new VhStopPtr() {
        public void handler() {
            pang_objram = null;
            paletteram = null;
        }
    };

    /**
     * *************************************************************************
     *
     * Memory handlers
     *
     **************************************************************************
     */
    /**
     * *************************************************************************
     * OBJ / CHAR RAM HANDLERS (BANK 0 = CHAR, BANK 1=OBJ)
     * *************************************************************************
     */
    static int video_bank;

    public static WriteHandlerPtr pang_video_bank_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* Bank handler (sets base pointers for video write) (doesn't apply to mgakuen) */
            video_bank = data;
        }
    };

    public static WriteHandlerPtr mgakuen_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (pang_videoram.read(offset) != data) {
                pang_videoram.write(offset, data);
                tilemap_mark_tile_dirty(bg_tilemap, offset / 2);
            }
        }
    };

    public static ReadHandlerPtr mgakuen_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return pang_videoram.read(offset);
        }
    };

    public static WriteHandlerPtr mgakuen_objram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            pang_objram.write(offset, data);
        }
    };

    public static ReadHandlerPtr mgakuen_objram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return pang_objram.read(offset);
        }
    };

    public static WriteHandlerPtr pang_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (video_bank != 0) {
                mgakuen_objram_w.handler(offset, data);
            } else {
                mgakuen_videoram_w.handler(offset, data);
            }
        }
    };

    public static ReadHandlerPtr pang_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (video_bank != 0) {
                return mgakuen_objram_r.handler(offset);
            } else {
                return mgakuen_videoram_r.handler(offset);
            }
        }
    };

    /**
     * *************************************************************************
     * COLOUR RAM
     * **************************************************************************
     */
    public static WriteHandlerPtr pang_colorram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (pang_colorram.read(offset) != data) {
                pang_colorram.write(offset, data);
                tilemap_mark_tile_dirty(bg_tilemap, offset);
            }
        }
    };

    public static ReadHandlerPtr pang_colorram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return pang_colorram.read(offset);
        }
    };

    /**
     * *************************************************************************
     * PALETTE HANDLERS (COLOURS: BANK 0 = 0x00-0x3f BANK 1=0x40-0xff)
     * **************************************************************************
     */
    static int paletteram_bank;

    public static WriteHandlerPtr pang_gfxctrl_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            logerror("PC %04x: pang_gfxctrl_w %02x\n", cpu_get_pc(), data);

            /* bit 0 is unknown (used, maybe back color enable?) */
 /* bit 1 is coin counter */
            coin_counter_w.handler(0, data & 2);

            /* bit 2 is flip screen */
            if (flipscreen != (data & 0x04)) {
                flipscreen = data & 0x04;
                tilemap_set_flip(ALL_TILEMAPS, flipscreen != 0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
            }

            /* bit 3 is unknown (used, e.g. marukin pulses it on the title screen) */
 /* bit 4 selects OKI M6295 bank */
            OKIM6295_set_bank_base(0, ALL_VOICES, (data & 0x10) != 0 ? 0x40000 : 0x00000);

            /* bit 5 is palette RAM bank selector (doesn't apply to mgakuen) */
            paletteram_bank = data & 0x20;

            /* bits 6 and 7 are unknown, used in several places. At first I thought */
 /* they were bg and sprites enable, but this screws up spang (screen flickers */
 /* every time you pop a bubble). However, not using them as enable bits screws */
 /* up marukin - you can see partially built up screens during attract mode. */
        }
    };

    public static WriteHandlerPtr pang_paletteram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (paletteram_bank != 0) {
                paletteram_xxxxRRRRGGGGBBBB_w.handler(offset + 0x800, data);
            } else {
                paletteram_xxxxRRRRGGGGBBBB_w.handler(offset, data);
            }
        }
    };

    public static ReadHandlerPtr pang_paletteram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (paletteram_bank != 0) {
                return paletteram_r.handler(offset + 0x800);
            }
            return paletteram_r.handler(offset);
        }
    };

    public static WriteHandlerPtr mgakuen_paletteram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            paletteram_xxxxRRRRGGGGBBBB_w.handler(offset, data);
        }
    };

    public static ReadHandlerPtr mgakuen_paletteram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return paletteram_r.handler(offset);
        }
    };

    /**
     * *************************************************************************
     *
     * Display refresh
     *
     **************************************************************************
     */
    static void mark_sprites_palette() {
        int offs, color, code, attr, i;
        int[] colmask = new int[16];
        int pal_base;

        pal_base = Machine.drv.gfxdecodeinfo[1].color_codes_start;

        for (color = 0; color < 16; color++) {
            colmask[color] = 0;
        }

        /* the last entry is not a sprite, we skip it otherwise spang shows a bubble */
 /* moving diagonally across the screen */
        for (offs = 0x1000 - 0x40; offs >= 0; offs -= 0x20) {
            attr = pang_objram.read(offs + 1);
            code = pang_objram.read(offs) + ((attr & 0xe0) << 3);
            color = attr & 0x0f;

            colmask[color] |= Machine.gfx[1].pen_usage[code];
        }

        for (color = 0; color < 16; color++) {
            for (i = 0; i < 15; i++) {
                if ((colmask[color] & (1 << i)) != 0) {
                    palette_used_colors.or(pal_base + 16 * color + i, PALETTE_COLOR_VISIBLE);
                }
            }
        }
    }

    static void draw_sprites(osd_bitmap bitmap) {
        int offs, sx, sy;

        /* the last entry is not a sprite, we skip it otherwise spang shows a bubble */
 /* moving diagonally across the screen */
        for (offs = 0x1000 - 0x40; offs >= 0; offs -= 0x20) {
            int code = pang_objram.read(offs);
            int attr = pang_objram.read(offs + 1);
            int color = attr & 0x0f;
            sx = pang_objram.read(offs + 3) + ((attr & 0x10) << 4);
            sy = ((pang_objram.read(offs + 2) + 8) & 0xff) - 8;
            code += (attr & 0xe0) << 3;
            if (flipscreen != 0) {
                sx = 496 - sx;
                sy = 240 - sy;
            }
            drawgfx(bitmap, Machine.gfx[1],
                    code,
                    color,
                    flipscreen, flipscreen,
                    sx, sy,
                    Machine.visible_area, TRANSPARENCY_PEN, 15);
        }
    }

    public static VhUpdatePtr pang_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int i;

            tilemap_update(ALL_TILEMAPS);

            palette_init_used_colors();

            mark_sprites_palette();

            /* the following is required to make the colored background work */
            for (i = 15; i < Machine.drv.total_colors; i += 16) {
                palette_used_colors.write(i, PALETTE_COLOR_TRANSPARENT);
            }

            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            tilemap_draw(bitmap, bg_tilemap, 0);

            draw_sprites(bitmap);
        }
    };
}
