/*
 * ported to v0.37b7  -- MISSING SPRITES IMPLEMENTATION TODO
 * using automatic conversion tool v0.01
 */
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.ptrLib.UShortPtr;
import arcadeflex.v037b7.generic.funcPtr.ReadHandlerPtr;
import arcadeflex.v037b7.generic.funcPtr.VhStartPtr;
import arcadeflex.v037b7.generic.funcPtr.VhStopPtr;
import arcadeflex.v037b7.generic.funcPtr.VhUpdatePtr;
import arcadeflex.v037b7.generic.funcPtr.WriteHandlerPtr;
import static arcadeflex.v037b7.mame.common.memory_region;
import static arcadeflex.v037b7.mame.commonH.REGION_CPU1;
import static arcadeflex.v037b7.mame.memoryH.COMBINE_WORD;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.paletteH.PALETTE_COLOR_USED;
import static arcadeflex.v037b7.vidhrdw.generic.videoram;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_init_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_create;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_draw;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_pixels_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_tile_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_render;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_scan_cols;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_enable;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scrollx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_set_scrolly;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_update;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.GetMemoryOffsetPtr;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.GetTileInfoPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.SET_TILE_INFO;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_OPAQUE;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.TILEMAP_TRANSPARENT;
import gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.struct_tilemap;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class armedf {

    static int scroll_type;

    public static char armedf_vreg;

    public static UBytePtr armedf_bg_videoram = new UBytePtr();
    public static char armedf_bg_scrollx;
    public static char armedf_bg_scrolly;

    public static UBytePtr armedf_fg_videoram = new UBytePtr();
    public static char armedf_fg_scrollx;
    public static char armedf_fg_scrolly;

    public static char terraf_scroll_msb;

    static struct_tilemap background, foreground, text_layer;

    /**
     * ***************************************************************
     */
    public static GetMemoryOffsetPtr armedf_scan = new GetMemoryOffsetPtr() {
        public int handler(int u32_col, int u32_row, int u32_num_cols, int u32_num_rows) {
            /* logical (col,row) . memory offset */
            return 32 * u32_col + u32_row + 0x80;
        }
    };

    public static GetTileInfoPtr get_tx_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            UShortPtr source = new UShortPtr(videoram);
            char attributes = (char) (source.read(tile_index + 0x800) & 0xff);
            int tile_number = (source.read(tile_index) & 0xff) + 256 * (attributes & 3);
            int color = attributes >> 4;
            SET_TILE_INFO(0, tile_number, color);
        }
    };

    public static WriteHandlerPtr armedf_text_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = videoram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);
            if (oldword != newword) {
                videoram.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(text_layer, (offset / 2) & 0x7ff);
            }
        }
    };

    public static ReadHandlerPtr armedf_text_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return videoram.READ_WORD(offset);
        }
    };

    public static ReadHandlerPtr terraf_text_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return videoram.READ_WORD(offset);
        }
    };

    public static WriteHandlerPtr terraf_text_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = videoram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);
            if (oldword != newword) {
                videoram.WRITE_WORD(offset, newword);
                offset = offset / 2;
                tilemap_mark_tile_dirty(text_layer, offset & 0xbff);
            }
        }
    };

    public static GetMemoryOffsetPtr terraf_scan = new GetMemoryOffsetPtr() {
        public int handler(int u32_col, int u32_row, int u32_num_cols, int u32_num_rows) {
            /* logical (col,row) . memory offset */
            int tile_index = 32 * (31 - u32_row);
            if (u32_col < 3) {
                tile_index += 0x800 + u32_col + 29;
            } else if (u32_col < 35) {
                tile_index += (u32_col - 3);
            } else {
                tile_index += 0x800 + u32_col - 35;
            }
            return tile_index;
        }
    };

    public static GetTileInfoPtr terraf_get_tx_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            UShortPtr source = new UShortPtr(videoram);
            char attributes = (char) (source.read(tile_index + 0x400) & 0xff);
            int tile_number = source.read(tile_index) & 0xff;

            SET_TILE_INFO(0, tile_number + 256 * (attributes & 0x3), attributes >> 4);
        }
    };

    /**
     * ***************************************************************
     */
    public static GetTileInfoPtr get_fg_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int data = armedf_fg_videoram.READ_WORD(tile_index);
            SET_TILE_INFO(1, data & 0x7ff, data >> 11);
        }
    };

    public static WriteHandlerPtr armedf_fg_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = armedf_fg_videoram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);
            if (oldword != newword) {
                armedf_fg_videoram.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(foreground, offset / 2);
            }
        }
    };

    public static ReadHandlerPtr armedf_fg_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return armedf_fg_videoram.READ_WORD(offset);
        }
    };

    /**
     * ***************************************************************
     */
    public static GetTileInfoPtr get_bg_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int data = armedf_bg_videoram.READ_WORD(tile_index);
            SET_TILE_INFO(2, data & 0x3ff, data >> 11);
        }
    };

    public static WriteHandlerPtr armedf_bg_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = armedf_bg_videoram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);
            if (oldword != newword) {
                armedf_bg_videoram.WRITE_WORD(offset, newword);
                tilemap_mark_tile_dirty(background, offset / 2);
            }
        }
    };

    public static ReadHandlerPtr armedf_bg_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return armedf_bg_videoram.READ_WORD(offset);
        }
    };

    /**
     * ***************************************************************
     */
    public static VhStartPtr terraf_vh_start = new VhStartPtr() {
        public int handler() {
            scroll_type = 0;

            text_layer = tilemap_create(terraf_get_tx_tile_info, terraf_scan, TILEMAP_TRANSPARENT, 8, 8, 38, 32);
            background = tilemap_create(get_bg_tile_info, tilemap_scan_cols, TILEMAP_OPAQUE, 16, 16, 64, 32);
            foreground = tilemap_create(get_fg_tile_info, tilemap_scan_cols, TILEMAP_TRANSPARENT, 16, 16, 64, 32);

            if (background == null || foreground == null || text_layer == null) {
                return 1;
            }

            foreground.transparent_pen = 0xf;
            text_layer.transparent_pen = 0xf;

            return 0;
        }
    };

    public static VhStartPtr armedf_vh_start = new VhStartPtr() {
        public int handler() {
            scroll_type = 1;

            text_layer = tilemap_create(get_tx_tile_info, armedf_scan, TILEMAP_TRANSPARENT, 8, 8, 38, 32);
            background = tilemap_create(get_bg_tile_info, tilemap_scan_cols, TILEMAP_OPAQUE, 16, 16, 64, 32);
            foreground = tilemap_create(get_fg_tile_info, tilemap_scan_cols, TILEMAP_TRANSPARENT, 16, 16, 64, 32);

            if (background == null || foreground == null || text_layer == null) {
                return 1;
            }

            foreground.transparent_pen = 0xf;
            text_layer.transparent_pen = 0xf;

            return 0;
        }
    };

    public static VhStartPtr kodure_vh_start = new VhStartPtr() {
        public int handler() {
            scroll_type = 2;

            text_layer = tilemap_create(terraf_get_tx_tile_info, terraf_scan, TILEMAP_TRANSPARENT, 8, 8, 38, 32);
            background = tilemap_create(get_bg_tile_info, tilemap_scan_cols, TILEMAP_OPAQUE, 16, 16, 64, 32);
            foreground = tilemap_create(get_fg_tile_info, tilemap_scan_cols, TILEMAP_TRANSPARENT, 16, 16, 64, 32);

            if (background == null || foreground == null || text_layer == null) {
                return 1;
            }

            foreground.transparent_pen = 0xf;
            text_layer.transparent_pen = 0xf;

            return 0;
        }
    };

    public static VhStopPtr armedf_vh_stop = new VhStopPtr() {
        public void handler() {
        }
    };

    static void draw_sprites(osd_bitmap bitmap, int priority) {
        /*	const struct rectangle *clip = &Machine.visible_area;
		const struct GfxElement *gfx = Machine.gfx[3];
	
		UINT16 *source = (UINT16 *)spriteram;
		UINT16 *finish = source+512;
	
		while( source<finish )
		{
			int sy = 128+240-(source[0]&0x1ff);
			int tile_number = source[1]; /* ??YX?TTTTTTTTTTT */

 /*		int color = (source[2]>>8)&0x1f;
			int sx = source[3] - 0x60;
	
			if( ((source[0]&0x2000)?0:1) == priority )
			{
				drawgfx(bitmap,gfx,
					tile_number,
					color,
	 				tile_number&0x2000,tile_number&0x1000, /* flip */
 /*				sx,sy,
					clip,TRANSPARENCY_PEN,0xf);
			}
	
			source+=4;
		}*/
    }

    static void mark_sprite_colors() {
        /*	UINT16 *source = (UINT16 *)spriteram;
		UINT16 *finish = source+512;
		int i;
		char flag[32];
	
		for( i=0; i<32; i++ ) flag[i] = 0;
	
		while( source<finish )
		{
			int color = (source[2]>>8)&0x1f;
			flag[color] = 1;
			source+=4;
		}
	
		{
			UBytePtr pen_ptr = &palette_used_colors[Machine.drv.gfxdecodeinfo[3].color_codes_start];
			int pen;
			for( i = 0; i<32; i++ )
			{
				if( flag[i] )
				{
					for( pen = 0; pen<0xf; pen++ ) pen_ptr[pen] = PALETTE_COLOR_USED;
				}
				pen_ptr += 16;
			}
		}*/
    }

    public static VhUpdatePtr armedf_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int sprite_enable = armedf_vreg & 0x200;

            tilemap_set_enable(background, armedf_vreg & 0x800);
            tilemap_set_enable(foreground, armedf_vreg & 0x400);
            tilemap_set_enable(text_layer, armedf_vreg & 0x100);

            tilemap_set_scrollx(background, 0, armedf_bg_scrollx + 96);
            tilemap_set_scrolly(background, 0, armedf_bg_scrolly);

            switch (scroll_type) {
                case 0:
                    /* terra force */
                    tilemap_set_scrollx(foreground, 0, (armedf_fg_scrolly >> 8) + ((terraf_scroll_msb >> 12) & 3) * 256 - 160 - 256 * 3);
                    tilemap_set_scrolly(foreground, 0, (armedf_fg_scrollx >> 8) + ((terraf_scroll_msb >> 8) & 3) * 256);
                    break;
                case 1:
                /* armed formation */
                case 2:
                    /* kodure ookami */
                    tilemap_set_scrollx(foreground, 0, armedf_fg_scrollx + 96);
                    tilemap_set_scrolly(foreground, 0, armedf_fg_scrolly);
            }

            if (scroll_type == 2) /* kodure ookami */ {
                tilemap_set_scrollx(text_layer, 0, -8);
                tilemap_set_scrolly(text_layer, 0, 0);
            }

            tilemap_update(ALL_TILEMAPS);

            palette_init_used_colors();
            mark_sprite_colors();
            palette_used_colors.write(0, PALETTE_COLOR_USED);
            /* background */

            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            if ((armedf_vreg & 0x0800) != 0) {
                tilemap_draw(bitmap, background, 0);
            } else {
                fillbitmap(bitmap, Machine.pens[0], null); /* disabled background - all black? */
            }

            if (sprite_enable != 0) {
                draw_sprites(bitmap, 0);
            }
            tilemap_draw(bitmap, foreground, 0);
            if (sprite_enable != 0) {
                draw_sprites(bitmap, 1);
            }
            tilemap_draw(bitmap, text_layer, 0);
        }
    };

    static void cclimbr2_draw_sprites(osd_bitmap bitmap, int priority) {
        /*	const struct rectangle *clip = &Machine.visible_area;
		const struct GfxElement *gfx = Machine.gfx[3];
	
		UINT16 *source = (UINT16 *)spriteram;
		UINT16 *finish = source+1024;
	
		while( source<finish )
		{
			int sy = 240-(source[0]&0x1ff);				// ???
			int tile_number = source[1]; /* ??YX?TTTTTTTTTTT */

 /*		int color = (source[2]>>8)&0x1f;
			int sx = source[3] - 0x68;
	
			if (((source[0] & 0x3000) >> 12) == priority)
			{
				drawgfx(bitmap,gfx,
					tile_number,
					color,
	 				tile_number&0x2000,tile_number&0x1000, /* flip */
 /*				sx,sy,
					clip,TRANSPARENCY_PEN,0xf);
			}
	
			source+=4;
		}*/
    }

    public static VhUpdatePtr cclimbr2_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            UBytePtr RAM;
            int sprite_enable = armedf_vreg & 0x200;

            tilemap_set_enable(background, armedf_vreg & 0x800);
            tilemap_set_enable(foreground, armedf_vreg & 0x400);
            tilemap_set_enable(text_layer, armedf_vreg & 0x100);

            tilemap_set_scrollx(text_layer, 0, 0);
            tilemap_set_scrolly(text_layer, 0, 0);

            tilemap_set_scrollx(background, 0, armedf_bg_scrollx + 104);
            tilemap_set_scrolly(background, 0, armedf_bg_scrolly);

            RAM = memory_region(REGION_CPU1);
            tilemap_set_scrollx(foreground, 0, RAM.READ_WORD(0x6123c) - (160 + 256 * 3) + 8);	// ???
            tilemap_set_scrolly(foreground, 0, RAM.READ_WORD(0x6123e) - 1);			// ???

            tilemap_update(ALL_TILEMAPS);

            palette_init_used_colors();
            mark_sprite_colors();
            palette_used_colors.write(0, PALETTE_COLOR_USED);
            /* background */

            if (palette_recalc() != null) {
                tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
            }

            tilemap_render(ALL_TILEMAPS);

            if ((armedf_vreg & 0x0800) != 0) {
                tilemap_draw(bitmap, background, 0);
            } else {
                fillbitmap(bitmap, Machine.pens[0], null); /* disabled background - all black? */
            }

            if (sprite_enable != 0) {
                cclimbr2_draw_sprites(bitmap, 2);
            }
            tilemap_draw(bitmap, foreground, 0);
            if (sprite_enable != 0) {
                cclimbr2_draw_sprites(bitmap, 1);
            }
            tilemap_draw(bitmap, text_layer, 0);
            if (sprite_enable != 0) {
                cclimbr2_draw_sprites(bitmap, 0);
            }
        }
    };
}
