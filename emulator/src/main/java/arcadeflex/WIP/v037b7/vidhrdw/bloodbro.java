/*
 * ported to v0.037b7
 * ported to v0.36
 *
 */
package arcadeflex.WIP.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.libc.cstring.*;
import static arcadeflex.common.libc.expressions.NOT;
import static arcadeflex.common.subArrays.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.bitmap_alloc;
import static arcadeflex.v037b7.mame.common.bitmap_free;
//mame imports
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.mame.paletteH.*;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.generic.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.copyscrollbitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.spriteC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.spriteH.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class bloodbro {

    public static final int NUM_SPRITES = 128;

    public static UBytePtr textlayoutram = new UBytePtr();
    static char[] dirtybuffer2;
    static osd_bitmap tmpbitmap2;
    public static UBytePtr bloodbro_videoram2 = new UBytePtr();
    public static UBytePtr bloodbro_scroll = new UBytePtr();
    static sprite_list sprite_list;

    public static ReadHandlerPtr bloodbro_background_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return videoram.READ_WORD(offset);
        }
    };

    public static WriteHandlerPtr bloodbro_background_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = videoram.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);
            if (oldword != newword) {
                videoram.WRITE_WORD(offset, newword);
                dirtybuffer[offset / 2] = 1;
            }
        }
    };

    public static ReadHandlerPtr bloodbro_foreground_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return bloodbro_videoram2.READ_WORD(offset);
        }
    };

    public static WriteHandlerPtr bloodbro_foreground_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = bloodbro_videoram2.READ_WORD(offset);
            int newword = COMBINE_WORD(oldword, data);
            if (oldword != newword) {
                bloodbro_videoram2.WRITE_WORD(offset, newword);
                dirtybuffer2[offset / 2] = 1;
            }
        }
    };

    /**
     * ***********************************************************************
     */
    public static VhStopPtr bloodbro_vh_stop = new VhStopPtr() {
        public void handler() {
            if (tmpbitmap != null) {
                bitmap_free(tmpbitmap);
            }
            if (tmpbitmap2 != null) {
                bitmap_free(tmpbitmap2);
            }
            dirtybuffer2 = null;
            dirtybuffer = null;
        }
    };
    public static VhStartPtr bloodbro_vh_start = new VhStartPtr() {
        public int handler() {
            tmpbitmap = bitmap_alloc(512, 256);
            dirtybuffer = new char[32 * 16];
            tmpbitmap2 = bitmap_alloc(512, 256);
            dirtybuffer2 = new char[32 * 16];
            sprite_list = sprite_list_create(NUM_SPRITES, SPRITE_LIST_FRONT_TO_BACK);

            if (tmpbitmap != null && tmpbitmap2 != null && dirtybuffer != null && dirtybuffer2 != null) {
                memset(dirtybuffer, 1, 32 * 16);
                memset(dirtybuffer2, 1, 32 * 16);
                sprite_list.transparent_pen = 0xf;
                sprite_list.max_priority = 1;
                sprite_list.sprite_type = SPRITE_TYPE_STACK;
                return 0;
            }
            bloodbro_vh_stop.handler();
            return 1;
        }
    };

    /**
     * ***********************************************************************
     */
    static void draw_text(osd_bitmap bitmap) {
        rectangle clip = Machine.visible_area;
        UShortPtr source = new UShortPtr(textlayoutram);
        int sx, sy;
        for (sy = 0; sy < 32; sy++) {
            for (sx = 0; sx < 32; sx++) {
                char data = source.read(0);//*source++;
                source.offset += 2;

                drawgfx(bitmap, Machine.gfx[0],
                        data & 0xfff, /* tile number */
                        data >> 12, /* color */
                        0, 0, /* no flip */
                        8 * sx, 8 * sy,
                        clip, TRANSPARENCY_PEN, 0xf);
            }
        }
    }

    static void draw_background(osd_bitmap bitmap) {
        GfxElement gfx = Machine.gfx[1];
        UShortPtr source = new UShortPtr(videoram);
        int offs;
        for (offs = 0; offs < 32 * 16; offs++) {
            if (dirtybuffer[offs] != 0) {
                int sx = 16 * (offs % 32);
                int sy = 16 * (offs / 32);
                char data = source.read(offs);
                dirtybuffer[offs] = 0;

                drawgfx(tmpbitmap, gfx,
                        data & 0xfff, /* tile number */
                        (data & 0xf000) >> 12, /* color */
                        0, 0, /* no flip */
                        sx, sy,
                        null, TRANSPARENCY_NONE, 0);
            }
        }
        {
            int scrollx = -bloodbro_scroll.READ_WORD(0x20);
            /**
             * ? *
             */
            int scrolly = -bloodbro_scroll.READ_WORD(0x22);
            /**
             * ? *
             */

            copyscrollbitmap(bitmap, tmpbitmap,
                    1, new int[]{scrollx}, 1, new int[]{scrolly},
                    Machine.visible_area,
                    TRANSPARENCY_NONE, 0);
        }
    }

    static void draw_foreground(osd_bitmap bitmap) {
        rectangle r = new rectangle();
        GfxElement gfx = Machine.gfx[2];
        UShortPtr source = new UShortPtr(bloodbro_videoram2);
        int offs;
        for (offs = 0; offs < 32 * 16; offs++) {
            if (dirtybuffer2[offs] != 0) {
                int sx = 16 * (offs % 32);
                int sy = 16 * (offs / 32);
                char data = source.read(offs);
                dirtybuffer2[offs] = 0;

                /* Necessary to use TRANSPARENCY_PEN here */
                r.min_x = sx;
                r.max_x = sx + 15;
                r.min_y = sy;
                r.max_y = sy + 15;
                fillbitmap(tmpbitmap2, 0xf, r);
                /**
                 * ***************************************
                 */

                drawgfx(tmpbitmap2, gfx,
                        data & 0xfff, /* tile number */
                        (data & 0xf000) >> 12, /* color */
                        0, 0,
                        sx, sy,
                        null, TRANSPARENCY_PEN, 0xf);
            }
        }
        {
            int scrollx = -bloodbro_scroll.READ_WORD(0x24);
            int scrolly = -bloodbro_scroll.READ_WORD(0x26);

            copyscrollbitmap(bitmap, tmpbitmap2, 1, new int[]{scrollx}, 1, new int[]{scrolly}, Machine.visible_area,
                    TRANSPARENCY_PEN, 0xf);
        }
    }

    /* SPRITE INFO (8 bytes)

   --F???SS SSSSCCCC
   ---TTTTT TTTTTTTT
   -------X XXXXXXXX
   -------- YYYYYYYY */
    static void get_sprite_info() {
        GfxElement gfx = Machine.gfx[3];
        UShortPtr source = new UShortPtr(spriteram);
        sprite[] sprite = sprite_list.sprite;
        int count = NUM_SPRITES;
        int sprite_ptr = 0;
        int attributes, flags, number, color, vertical_size, horizontal_size, i;
        while ((count--) != 0) {
            attributes = source.read(0);
            flags = 0;
            if ((attributes & 0x8000) == 0) {
                flags |= SPRITE_VISIBLE;
                horizontal_size = 1 + ((attributes >> 7) & 7);
                vertical_size = 1 + ((attributes >> 4) & 7);
                sprite[sprite_ptr].priority = (attributes >> 11) & 1;
                number = source.read(1) & 0x1fff;
                sprite[sprite_ptr].x = source.read(2) & 0x1ff;
                sprite[sprite_ptr].y = source.read(3) & 0x1ff;

                /* wraparound - could be handled by Sprite Manager?*/
                if (sprite[sprite_ptr].x >= 256) {
                    sprite[sprite_ptr].x -= 512;
                }
                if (sprite[sprite_ptr].y >= 256) {
                    sprite[sprite_ptr].y -= 512;
                }

                sprite[sprite_ptr].total_width = 16 * horizontal_size;
                sprite[sprite_ptr].total_height = 16 * vertical_size;

                sprite[sprite_ptr].tile_width = 16;
                sprite[sprite_ptr].tile_height = 16;
                sprite[sprite_ptr].line_offset = 16;

                if ((attributes & 0x2000) != 0) {
                    flags |= SPRITE_FLIPX;
                }
                if ((attributes & 0x4000) != 0) {
                    flags |= SPRITE_FLIPY;
                    /* ? */
                }
                color = attributes & 0xf;

                sprite[sprite_ptr].pen_data = new UBytePtr(gfx.gfxdata, number * gfx.char_modulo);
                sprite[sprite_ptr].pal_data = new UShortArray(gfx.colortable, gfx.color_granularity * color);

                sprite[sprite_ptr].pen_usage = 0;
                for (i = 0; i < vertical_size * horizontal_size; i++) {
                    sprite[sprite_ptr].pen_usage |= gfx.pen_usage[number++];
                }
            }
            sprite[sprite_ptr].flags = flags;

            sprite_ptr++;
            source.offset += 4 * 2;
        }
    }

    static void bloodbro_mark_used_colors() {
        int offs, i;
        int code, color;
        int[] colmask = new int[0x80];
        int pal_base = Machine.drv.gfxdecodeinfo[0].color_codes_start;

        /* Build the dynamic palette */
        palette_init_used_colors();

        /* Text layer */
        pal_base = Machine.drv.gfxdecodeinfo[0].color_codes_start;
        for (color = 0; color < 16; color++) {
            colmask[color] = 0;
        }
        for (offs = 0; offs < 0x800; offs += 2) {
            code = textlayoutram.READ_WORD(offs);
            color = code >> 12;
            if ((code & 0xfff) == 0xd) {
                continue;
            }
            colmask[color] |= Machine.gfx[0].pen_usage[code & 0xfff];
        }
        for (color = 0; color < 16; color++) {
            for (i = 0; i < 15; i++) {
                if ((colmask[color] & (1 << i)) != 0) {
                    palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
                }
            }
        }

        /* Tiles - bottom layer */
        pal_base = Machine.drv.gfxdecodeinfo[1].color_codes_start;
        for (offs = 0; offs < 256; offs++) {
            palette_used_colors.write(pal_base + offs, PALETTE_COLOR_USED);
        }

        /* Tiles - top layer */
        pal_base = Machine.drv.gfxdecodeinfo[2].color_codes_start;
        for (color = 0; color < 16; color++) {
            colmask[color] = 0;
        }
        for (offs = 0x0000; offs < 0x400; offs += 2) {
            code = bloodbro_videoram2.READ_WORD(offs);
            color = code >> 12;
            colmask[color] |= Machine.gfx[2].pen_usage[code & 0xfff];
        }

        for (color = 0; color < 16; color++) {
            for (i = 0; i < 15; i++) {
                if ((colmask[color] & (1 << i)) != 0) {
                    palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
                }
            }
            /* kludge */
            palette_used_colors.write(pal_base + 16 * color + 15, PALETTE_COLOR_TRANSPARENT);
            palette_change_color(pal_base + 16 * color + 15, 0, 0, 0);
        }
    }

    public static VhUpdatePtr bloodbro_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            get_sprite_info();

            bloodbro_mark_used_colors();
            sprite_update();

            if (palette_recalc() != null) {
                memset(dirtybuffer, 1, 32 * 16);
                memset(dirtybuffer2, 1, 32 * 16);
            }
            draw_background(bitmap);
            sprite_draw(sprite_list, 1);
            draw_foreground(bitmap);
            sprite_draw(sprite_list, 0);
            draw_text(bitmap);
        }
    };

    /* SPRITE INFO (8 bytes)

   -------- YYYYYYYY
   ---TTTTT TTTTTTTT
   CCCC--F? -?--????  Priority??
   -------X XXXXXXXX
     */
    static void weststry_draw_sprites(osd_bitmap bitmap, int priority) {
        int offs;
        for (offs = 0x800 - 8; offs > 0; offs -= 8) {
            int data = spriteram.READ_WORD(offs + 4);
            int data0 = spriteram.READ_WORD(offs + 0);
            int tile_number = spriteram.READ_WORD(offs + 2) & 0x1fff;
            int sx = spriteram.READ_WORD(offs + 6) & 0xff;
            int sy = 0xf0 - (data0 & 0xff);
            int flipx = (data & 0x200) >> 9;
            int datax = (spriteram.READ_WORD(offs + 6) & 0x100);
            int color = (data & 0xf000) >> 12;

            /* Remap sprites */
            switch (tile_number & 0x1800) {
                case 0x0000:
                    break;
                case 0x0800:
                    tile_number = (tile_number & 0x7ff) | 0x1000;
                    break;
                case 0x1000:
                    tile_number = (tile_number & 0x7ff) | 0x800;
                    break;
                case 0x1800:
                    break;
            }

            if ((NOT(data0 & 0x8000)) != 0 && (NOT(datax)) != 0) {
                drawgfx(bitmap, Machine.gfx[3],
                        tile_number, color, flipx, 0,
                        sx, sy,
                        Machine.visible_area, TRANSPARENCY_PEN, 0xf);
            }
        }
    }

    static void weststry_mark_used_colors() {
        int offs, i;
        int[] colmask = new int[0x80];
        int code, pal_base, color;

        /* Build the dynamic palette */
        palette_init_used_colors();

        /* Text layer */
        pal_base = Machine.drv.gfxdecodeinfo[0].color_codes_start;
        for (color = 0; color < 16; color++) {
            colmask[color] = 0;
        }
        for (offs = 0; offs < 0x800; offs += 2) {
            code = textlayoutram.READ_WORD(offs);
            color = code >> 12;
            if ((code & 0xfff) == 0xd) {
                continue;
            }
            colmask[color] |= Machine.gfx[0].pen_usage[code & 0xfff];
        }
        for (color = 0; color < 16; color++) {
            for (i = 0; i < 15; i++) {
                if ((colmask[color] & (1 << i)) != 0) {
                    palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
                }
            }
        }

        /* Tiles - bottom layer */
        pal_base = Machine.drv.gfxdecodeinfo[1].color_codes_start;
        for (offs = 0; offs < 256; offs++) {
            palette_used_colors.write(pal_base + offs, PALETTE_COLOR_USED);
        }

        /* Tiles - top layer */
        pal_base = Machine.drv.gfxdecodeinfo[2].color_codes_start;
        for (color = 0; color < 16; color++) {
            colmask[color] = 0;
        }
        for (offs = 0x0000; offs < 0x400; offs += 2) {
            code = bloodbro_videoram2.READ_WORD(offs);
            color = code >> 12;
            colmask[color] |= Machine.gfx[2].pen_usage[code & 0xfff];
        }
        for (color = 0; color < 16; color++) {
            for (i = 0; i < 15; i++) {
                if ((colmask[color] & (1 << i)) != 0) {
                    palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
                }
            }

            /* kludge */
            palette_used_colors.write(pal_base + 16 * color + 15, PALETTE_COLOR_TRANSPARENT);
            palette_change_color(pal_base + 16 * color + 15, 0, 0, 0);
        }

        /* Sprites */
        pal_base = Machine.drv.gfxdecodeinfo[3].color_codes_start;
        for (color = 0; color < 16; color++) {
            colmask[color] = 0;
        }
        for (offs = 0; offs < 0x800; offs += 8) {
            color = spriteram.READ_WORD(offs + 4) >> 12;
            code = spriteram.READ_WORD(offs + 2) & 0x1fff;
            /* Remap code 0x800 <. 0x1000 */
            code = (code & 0x7ff) | ((code & 0x800) << 1) | ((code & 0x1000) >> 1);

            colmask[color] |= Machine.gfx[3].pen_usage[code];
        }
        for (color = 0; color < 16; color++) {
            for (i = 0; i < 15; i++) {
                if ((colmask[color] & (1 << i)) != 0) {
                    palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
                }
            }
        }

        if (palette_recalc() != null) {
            memset(dirtybuffer, 1, 32 * 16);
            memset(dirtybuffer2, 1, 32 * 16);
        }
    }
    public static VhUpdatePtr weststry_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            weststry_mark_used_colors();
            draw_background(bitmap);
            //weststry_draw_sprites(bitmap,0);
            draw_foreground(bitmap);
            weststry_draw_sprites(bitmap, 1);
            draw_text(bitmap);
        }
    };
}
