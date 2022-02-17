/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.libc.cstring.*;
import static arcadeflex.common.libc.expressions.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
//sound imports
import static arcadeflex.v037b7.sound.okim6295.*;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.generic.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_create;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_draw;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_pixels_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_tiles_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_tile_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_render;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_scan_cols;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_flip;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scroll_cols;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scroll_rows;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scrollx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scrolly;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_update;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.GetMemoryOffsetPtr;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.GetTileInfoPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.SET_TILE_INFO;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_FLIPX;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_FLIPY;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_OPAQUE;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_TRANSPARENT;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.struct_tilemap;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.old.arcadeflex.video.osd_clearbitmap;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;

public class powerins {

    /* Variables that driver has access to: */
    public static UBytePtr powerins_vram_0 = new UBytePtr();
    public static UBytePtr powerins_vctrl_0 = new UBytePtr();
    public static UBytePtr powerins_vram_1 = new UBytePtr();
    public static UBytePtr powerins_vctrl_1 = new UBytePtr();
    public static UBytePtr powerins_vregs = new UBytePtr();

    /* Variables only used here: */
    static struct_tilemap tilemap_0, tilemap_1;
    static int flipscreen, tile_bank;
    static int oki_bank;

    /**
     * *************************************************************************
     *
     * Hardware registers access
     *
     **************************************************************************
     */
    public static ReadHandlerPtr powerins_vregs_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return powerins_vregs.READ_WORD(offset);
        }
    };

    public static WriteHandlerPtr powerins_vregs_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            COMBINE_WORD_MEM(powerins_vregs, offset, data);

            switch (offset) {
                case 0x14:	// Flipscreen
                    flipscreen = data & 1;
                    tilemap_set_flip(ALL_TILEMAPS, flipscreen != 0 ? (TILEMAP_FLIPX | TILEMAP_FLIPY) : 0);
                    break;

                //		case 0x16:	// ? always 1
                case 0x18:	// Tiles Banking (VRAM 0)
                    if (data != tile_bank) {
                        tile_bank = data;
                        tilemap_mark_all_tiles_dirty(tilemap_0);
                    }
                    break;

                //		case 0x1e:	// ? there is an hidden test mode screen (set 18ff08 to 4
                //   during test mode) that calls this: sound code (!?)
                case 0x30: // OKI banking
                {
                    UBytePtr RAM = memory_region(REGION_SOUND1);
                    int new_bank = data & 0x7;

                    if (new_bank != oki_bank) {
                        oki_bank = new_bank;
                        memcpy(RAM, 0x30000, RAM, 0x40000 + 0x10000 * new_bank, 0x10000);
                    }
                }
                break;

                case 0x3e:	// OKI data
                    OKIM6295_data_0_w.handler(0, data);
                    break;

                default:
                    logerror("PC %06X - Register %02X <- %02X !\n", cpu_get_pc(), offset, data);
            }
        }
    };

    /**
     * *************************************************************************
     *
     * Palette
     *
     **************************************************************************
     */
    public static WriteHandlerPtr powerins_paletteram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /*	byte 0    byte 1	*/
 /*	RRRR GGGG BBBB RGBx	*/
 /*	4321 4321 4321 000x	*/

            int oldword = paletteram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);

            int r = ((newword >> 8) & 0xF0) | ((newword << 0) & 0x08);
            int g = ((newword >> 4) & 0xF0) | ((newword << 1) & 0x08);
            int b = ((newword >> 0) & 0xF0) | ((newword << 2) & 0x08);

            palette_change_color(offset / 2, r, g, b);

            paletteram.WRITE_WORD(offset, newword);
        }
    };

    /**
     * *************************************************************************
     *
     * Callbacks for the TileMap code
     *
     **************************************************************************
     */
    /**
     * *************************************************************************
     * [ Tiles Format VRAM 0]
     *
     * Offset:
     *
     * 0.w	fedc ---- ---- ----	Color Low Bits ---- b--- ---- ----	Color High Bit
     * ---- -a98 7654 3210	Code (Banked)
     *
     *
     **************************************************************************
     */
    /* Layers are made of 256x256 pixel pages */
    public static final int TILES_PER_PAGE_X = (0x10);
    public static final int TILES_PER_PAGE_Y = (0x10);
    public static final int TILES_PER_PAGE = (TILES_PER_PAGE_X * TILES_PER_PAGE_Y);

    public static final int DIM_NX_0 = (0x100);
    public static final int DIM_NY_0 = (0x20);

    public static GetTileInfoPtr get_tile_info_0 = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int code = powerins_vram_0.READ_WORD(tile_index * 2);
            SET_TILE_INFO(0, (code & 0x07ff) + (tile_bank * 0x800), ((code & 0xf000) >> (16 - 4)) + ((code & 0x0800) >> (11 - 4)));
        }
    };

    public static WriteHandlerPtr powerins_vram_0_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            COMBINE_WORD_MEM(powerins_vram_0, offset, data);
            tilemap_mark_tile_dirty(tilemap_0, offset / 2);
        }
    };
    public static GetMemoryOffsetPtr powerins_get_memory_offset_0 = new GetMemoryOffsetPtr() {
        public int handler(int u32_col, int u32_row, int u32_num_cols, int u32_num_rows) {
            return (u32_col * TILES_PER_PAGE_Y)
                    + (u32_row % TILES_PER_PAGE_Y)
                    + (u32_row / TILES_PER_PAGE_Y) * (TILES_PER_PAGE * 16);
        }
    };

    /**
     * *************************************************************************
     * [ Tiles Format VRAM 1]
     *
     * Offset:
     *
     * 0.w	fedc ---- ---- ----	Color ---- ba98 7654 3210	Code
     *
     *
     **************************************************************************
     */
    public static final int DIM_NX_1 = (0x40);
    public static final int DIM_NY_1 = (0x20);

    public static GetTileInfoPtr get_tile_info_1 = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int code = powerins_vram_1.READ_WORD(tile_index * 2);
            SET_TILE_INFO(1, code & 0x0fff, (code & 0xf000) >> (16 - 4));
        }
    };
    public static WriteHandlerPtr powerins_vram_1_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            COMBINE_WORD_MEM(powerins_vram_1, offset, data);
            tilemap_mark_tile_dirty(tilemap_1, offset / 2);
        }
    };

    /**
     * *************************************************************************
     *
     *
     * Vh_Start
     *
     *
     **************************************************************************
     */
    public static VhStartPtr powerins_vh_start = new VhStartPtr() {
        public int handler() {
            tilemap_0 = tilemap_create(get_tile_info_0,
                    powerins_get_memory_offset_0,
                    TILEMAP_OPAQUE,
                    16, 16,
                    DIM_NX_0, DIM_NY_0);

            tilemap_1 = tilemap_create(get_tile_info_1,
                    tilemap_scan_cols,
                    TILEMAP_TRANSPARENT,
                    8, 8,
                    DIM_NX_1, DIM_NY_1);

            if (tilemap_0 != null && tilemap_1 != null) {
                tilemap_set_scroll_rows(tilemap_0, 1);
                tilemap_set_scroll_cols(tilemap_0, 1);
                tilemap_0.transparent_pen = 15;

                tilemap_set_scroll_rows(tilemap_1, 1);
                tilemap_set_scroll_cols(tilemap_1, 1);
                tilemap_1.transparent_pen = 15;

                oki_bank = -1;	// samples bank "unitialised"
                return 0;
            } else {
                return 1;
            }
        }
    };

    /**
     * *************************************************************************
     *
     *
     * Sprites Drawing
     *
     *
     **************************************************************************
     */
    /* --------------------------[ Sprites Format ]----------------------------
	
	Offset:		Format:					Value:
	
		00 		fedc ba98 7654 321-		-
		 		---- ---- ---- ---0		Display this sprite
	
		02 		fed- ---- ---- ----		-
		 		---c ---- ---- ----		Flip X
		 		---- ba9- ---- ----		-
		 		---- ---8 ---- ----		Code High Bit
				---- ---- 7654 ----		Number of tiles along Y, minus 1 (1-16)
				---- ---- ---- 3210		Number of tiles along X, minus 1 (1-16)
	
		04 								Unused?
	
		06		f--- ---- ---- ----		-
				-edc ba98 7654 3210		Code Low Bits
	
		08 								X
	
		0A 								Unused?
	
		0C								Y
	
		0E		fedc ba98 76-- ----		-
				---- ---- --54 3210		Color
	
	
	------------------------------------------------------------------------ */
    static void powerins_mark_sprite_colors() {
        int i, col;
        int[] colmask = new int[0x100];

        int[] pen_usage = Machine.gfx[2].pen_usage;
        int total_elements = Machine.gfx[2].total_elements;
        int color_codes_start = Machine.drv.gfxdecodeinfo[2].color_codes_start;
        int total_color_codes = Machine.drv.gfxdecodeinfo[2].total_color_codes;

        UBytePtr source = new UBytePtr(spriteram, 0x8000);
        UBytePtr finish = new UBytePtr(spriteram, 0x9000);

        int xmin = Machine.visible_area.min_x;
        int xmax = Machine.visible_area.max_x;
        int ymin = Machine.visible_area.min_y;
        int ymax = Machine.visible_area.max_y;

        memset(colmask, 0, sizeof(colmask));

        for (; source.offset < finish.offset; source.offset += 16) {
            int x, y;

            int attr = source.READ_WORD(0x0);
            int size = source.READ_WORD(0x2);
            int code = source.READ_WORD(0x6);
            int sx = source.READ_WORD(0x8);
            int sy = source.READ_WORD(0xc);
            int color = source.READ_WORD(0xe) % total_color_codes;

            int dimx = ((size >> 0) & 0xf) + 1;
            int dimy = ((size >> 4) & 0xf) + 1;

            if ((attr & 1) == 0) {
                continue;
            }

            //SIGN_EXTEND_POS(sx)
            sx &= 0x3ff;
            if (sx > 0x1ff) {
                sx -= 0x400;
            }
            //SIGN_EXTEND_POS(sy)
            sy &= 0x3ff;
            if (sy > 0x1ff) {
                sy -= 0x400;
            }

            sx += 32;

            code = (code & 0x7fff) + ((size & 0x0100) << 7);

            for (x = 0; x < dimx * 16; x += 16) {
                for (y = 0; y < dimy * 16; y += 16) {
                    if (((sx + x + 15) < xmin) || ((sx + x) > xmax)
                            || ((sy + y + 15) < ymin) || ((sy + y) > ymax)) {
                        continue;
                    }

                    colmask[color] |= pen_usage[(code++) % total_elements];
                }
            }
        }

        for (col = 0; col < total_color_codes; col++) {
            for (i = 0; i < 15; i++) // pen 15 is transparent
            {
                if ((colmask[col] & (1 << i)) != 0) {
                    palette_used_colors.write(16 * col + i + color_codes_start, PALETTE_COLOR_USED);
                }
            }
        }
    }

    static void powerins_draw_sprites(osd_bitmap bitmap) {
        UBytePtr source = new UBytePtr(spriteram, 0x8000);
        UBytePtr finish = new UBytePtr(spriteram, 0x9000);

        int screen_w = Machine.drv.screen_width;
        int screen_h = Machine.drv.screen_height;

        for (; source.offset < finish.offset; source.offset += 16) {
            int x, y, inc;

            int attr = source.READ_WORD(0x0);
            int size = source.READ_WORD(0x2);
            int code = source.READ_WORD(0x6);
            int sx = source.READ_WORD(0x8);
            int sy = source.READ_WORD(0xc);
            int color = source.READ_WORD(0xe);

            int flipx = size & 0x1000;
            int flipy = 0;	// ??

            int dimx = ((size >> 0) & 0xf) + 1;
            int dimy = ((size >> 4) & 0xf) + 1;

            if ((attr & 1) == 0) {
                continue;
            }

            //SIGN_EXTEND_POS(sx)
            sx &= 0x3ff;
            if (sx > 0x1ff) {
                sx -= 0x400;
            }
            //SIGN_EXTEND_POS(sy)
            sy &= 0x3ff;
            if (sy > 0x1ff) {
                sy -= 0x400;
            }

            /* Handle Flipscreen. Apply a global offset of 32 pixels along x too */
            if (flipscreen != 0) {
                sx = screen_w - sx - dimx * 16 - 32;
                flipx = NOT(flipx);
                sy = screen_h - sy - dimy * 16;
                flipy = NOT(flipy);
                code += dimx * dimy - 1;
                inc = -1;
            } else {
                sx += 32;
                inc = +1;
            }

            code = (code & 0x7fff) + ((size & 0x0100) << 7);

            for (x = 0; x < dimx; x++) {
                for (y = 0; y < dimy; y++) {
                    drawgfx(bitmap, Machine.gfx[2],
                            code,
                            color,
                            flipx, flipy,
                            sx + x * 16,
                            sy + y * 16,
                            Machine.visible_area, TRANSPARENCY_PEN, 15);

                    code += inc;
                }
            }

        }
    }

    /**
     * *************************************************************************
     *
     *
     * Screen Drawing
     *
     *
     **************************************************************************
     */
    public static VhUpdatePtr powerins_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int layers_ctrl = -1;

            int scrollx = (powerins_vctrl_0.READ_WORD(2) & 0xff) + (powerins_vctrl_0.READ_WORD(0) & 0xff) * 256;
            int scrolly = (powerins_vctrl_0.READ_WORD(6) & 0xff) + (powerins_vctrl_0.READ_WORD(4) & 0xff) * 256;
            tilemap_set_scrollx(tilemap_0, 0, scrollx - 0x20);
            tilemap_set_scrolly(tilemap_0, 0, scrolly);

            tilemap_set_scrollx(tilemap_1, 0, -0x20);	// fixed offset
            tilemap_set_scrolly(tilemap_1, 0, 0x00);

            tilemap_update(ALL_TILEMAPS);

            palette_init_used_colors();

            powerins_mark_sprite_colors();

            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            if ((layers_ctrl & 1) != 0) {
                tilemap_draw(bitmap, tilemap_0, 0);
            } else {
                osd_clearbitmap(bitmap);
            }
            if ((layers_ctrl & 8) != 0) {
                powerins_draw_sprites(bitmap);
            }
            if ((layers_ctrl & 2) != 0) {
                tilemap_draw(bitmap, tilemap_1, 0);
            }
        }
    };
}
