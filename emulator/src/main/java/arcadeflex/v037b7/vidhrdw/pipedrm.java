/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.libc.expressions.*;
import static arcadeflex.common.libc.cstring.*;
//drivers imports
import static arcadeflex.v037b7.drivers.pipedrm.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.mame.paletteH.*;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.generic.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.copyscrollbitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.drawgfxzoom;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_init_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_transparent_pen;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_used_colors;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;


public class pipedrm {

    /**
     * ***********************************
     *
     * Statics
     *
     ************************************
     */
    static osd_bitmap[] background = new osd_bitmap[2];
    static UBytePtr[] background_dirty = new UBytePtr[2];
    static UBytePtr[] local_videoram = new UBytePtr[2];

    static char[]/*UINT8*/ scroll_regs = new char[4];

    public static VhStartPtr pipedrm_vh_start = new VhStartPtr() {
        public int handler() {
            /* allocate videoram */
            local_videoram[0] = new UBytePtr(videoram_size[0]);
            local_videoram[1] = new UBytePtr(videoram_size[0]);

            /* allocate background bitmaps */
            background[0] = bitmap_alloc(64 * 8, 64 * 4);
            background[1] = bitmap_alloc(64 * 8, 64 * 4);

            /* allocate dirty buffers */
            background_dirty[0] = new UBytePtr(64 * 64);
            background_dirty[1] = new UBytePtr(64 * 64);

            /* handle errors */
            if (local_videoram[0] == null || local_videoram[1] == null || background[0] == null || background[1] == null || background_dirty[0] == null || background_dirty[1] == null) {
                pipedrm_vh_stop.handler();
                return 1;
            }

            /* reset the system */
            memset(background_dirty[0], 1, 64 * 64);
            memset(background_dirty[1], 1, 64 * 64);
            return 0;
        }
    };

    /**
     * ***********************************
     *
     * Video system shutdown
     *
     ************************************
     */
    public static VhStopPtr pipedrm_vh_stop = new VhStopPtr() {
        public void handler() {
            /* free the dirty buffers */
            if (background_dirty[1] != null) {
                background_dirty[1] = null;
            }

            if (background_dirty[0] != null) {
                background_dirty[0] = null;
            }

            /* free the background bitmaps */
            if (background[1] != null) {
                bitmap_free(background[1]);
            }
            background[1] = null;

            if (background[0] != null) {
                bitmap_free(background[0]);
            }
            background[0] = null;

            /* free videoram */
            if (local_videoram[1] != null) {
                local_videoram[1] = null;
            }

            if (local_videoram[0] != null) {
                local_videoram[0] = null;
            }
        }
    };

    /**
     * ***********************************
     *
     * Write handlers
     *
     ************************************
     */
    public static WriteHandlerPtr pipedrm_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int which = (u8_pipedrm_video_control >> 3) & 1;
            if (local_videoram[which].read(offset) != data) {
                local_videoram[which].write(offset, data);
                background_dirty[which].write(offset & 0xfff, 1);
            }
        }
    };

    public static WriteHandlerPtr hatris_videoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int which = (((~u8_pipedrm_video_control) & 0xFF) >> 1) & 1;
            if (local_videoram[which].read(offset) != data) {
                local_videoram[which].write(offset, data);
                background_dirty[which].write(offset & 0xfff, 1);
            }
        }
    };

    public static WriteHandlerPtr pipedrm_scroll_regs_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            scroll_regs[offset] = (char) (data & 0xFF);
        }
    };

    public static ReadHandlerPtr pipedrm_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int which = (u8_pipedrm_video_control >> 3) & 1;
            return local_videoram[which].read(offset);
        }
    };

    public static ReadHandlerPtr hatris_videoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int which = (((~u8_pipedrm_video_control) & 0xFF) >> 1) & 1;
            return local_videoram[which].read(offset);
        }
    };

    /**
     * ***********************************
     *
     * Palette marking
     *
     ************************************
     */
    static void mark_background_colors() {
        int mask1 = Machine.gfx[0].total_elements - 1;
        int mask2 = Machine.gfx[1].total_elements - 1;
        int colormask = Machine.gfx[0].total_colors - 1;
        char[] used_colors = new char[128];
        int code, color, offs;

        /* reset all color codes */
        memset(used_colors, 0, sizeof(used_colors));

        /* loop over tiles */
        for (offs = 0; offs < 64 * 64; offs++) {
            /* consider background 0 */
            color = local_videoram[0].read(offs) & colormask;
            code = (local_videoram[0].read(offs + 0x1000) << 8) | local_videoram[0].read(offs + 0x2000);
            used_colors[color] |= Machine.gfx[0].pen_usage[code & mask1];

            /* consider background 1 */
            color = local_videoram[1].read(offs) & colormask;
            code = (local_videoram[1].read(offs + 0x1000) << 8) | local_videoram[1].read(offs + 0x2000);
            used_colors[color] |= Machine.gfx[1].pen_usage[code & mask2] & 0x7fff;
        }

        /* fill in the final table */
        for (offs = 0; offs <= colormask; offs++) {
            char used = used_colors[offs];
            if (used != 0) {
                for (color = 0; color < 15; color++) {
                    if ((used & (1 << color)) != 0) {
                        palette_used_colors.write(offs * 16 + color, PALETTE_COLOR_USED);
                    }
                }
                if ((used & 0x8000) != 0) {
                    palette_used_colors.write(offs * 16 + 15, PALETTE_COLOR_USED);
                } else {
                    palette_used_colors.write(offs * 16 + 15, PALETTE_COLOR_TRANSPARENT);
                }
            }
        }
    }

    static void mark_sprite_palette() {
        char[] used_colors = new char[0x20];
        int offs, i;

        /* clear the color array */
        memset(used_colors, 0, sizeof(used_colors));

        /* find the used sprites */
        for (offs = 0; offs < spriteram_size[0]; offs += 8) {
            int data2 = spriteram.read(offs + 4) | (spriteram.read(offs + 5) << 8);

            /* turns out the sprites are the same as in aerofgt.c */
            if ((data2 & 0x80) != 0) {
                int data3 = spriteram.read(offs + 6) | (spriteram.read(offs + 7) << 8);
                int code = data3 & 0xfff;
                int color = data2 & 0x0f;
                int xtiles = ((data2 >> 8) & 7) + 1;
                int ytiles = ((data2 >> 12) & 7) + 1;
                int tiles = xtiles * ytiles;
                int t;

                /* normal case */
                for (t = 0; t < tiles; t++) {
                    used_colors[color] |= Machine.gfx[2].pen_usage[code++];
                }
            }
        }

        /* now mark the pens */
        for (offs = 0; offs < 0x20; offs++) {
            char used = used_colors[offs];
            if (used != 0) {
                for (i = 0; i < 15; i++) {
                    if ((used & (1 << i)) != 0) {
                        palette_used_colors.write(1024 + offs * 16 + i, PALETTE_COLOR_USED);
                    }
                }
                palette_used_colors.write(1024 + offs * 16 + 15, PALETTE_COLOR_TRANSPARENT);
            }
        }
    }

    /**
     * ***********************************
     *
     * Sprite routines
     *
     ************************************
     */
    static void draw_sprites(osd_bitmap bitmap, int draw_priority) {
        int zoomtable[] = {0, 7, 14, 20, 25, 30, 34, 38, 42, 46, 49, 52, 54, 57, 59, 61};
        int offs;

        /* draw the sprites */
        for (offs = 0; offs < spriteram_size[0]; offs += 8) {
            int data2 = spriteram.read(offs + 4) | (spriteram.read(offs + 5) << 8);
            int priority = (data2 >> 4) & 1;

            /* turns out the sprites are the same as in aerofgt.c */
            if ((data2 & 0x80) != 0 && priority == draw_priority) {
                int data0 = spriteram.read(offs + 0) | (spriteram.read(offs + 1) << 8);
                int data1 = spriteram.read(offs + 2) | (spriteram.read(offs + 3) << 8);
                int data3 = spriteram.read(offs + 6) | (spriteram.read(offs + 7) << 8);
                int code = data3 & 0xfff;
                int color = data2 & 0x0f;
                int y = (data0 & 0x1ff) - 6;
                int x = (data1 & 0x1ff) - 13;
                int yzoom = (data0 >> 12) & 15;
                int xzoom = (data1 >> 12) & 15;
                int zoomed = (xzoom | yzoom);
                int ytiles = ((data2 >> 12) & 7) + 1;
                int xtiles = ((data2 >> 8) & 7) + 1;
                int yflip = (data2 >> 15) & 1;
                int xflip = (data2 >> 11) & 1;
                int xt, yt;

                /* compute the zoom factor -- stolen from aerofgt.c */
                xzoom = 16 - zoomtable[xzoom] / 8;
                yzoom = 16 - zoomtable[yzoom] / 8;

                /* wrap around */
                if (x > Machine.visible_area.max_x) {
                    x -= 0x200;
                }
                if (y > Machine.visible_area.max_y) {
                    y -= 0x200;
                }

                /* normal case */
                if (xflip == 0 && yflip == 0) {
                    for (yt = 0; yt < ytiles; yt++) {
                        for (xt = 0; xt < xtiles; xt++, code++) {
                            if (zoomed == 0) {
                                drawgfx(bitmap, Machine.gfx[2], code, color, 0, 0,
                                        x + xt * 16, y + yt * 16, null, TRANSPARENCY_PEN, 15);
                            } else {
                                drawgfxzoom(bitmap, Machine.gfx[2], code, color, 0, 0,
                                        x + xt * xzoom, y + yt * yzoom, null, TRANSPARENCY_PEN, 15,
                                        0x1000 * xzoom, 0x1000 * yzoom);
                            }
                        }
                    }
                } /* xflipped case */ else if (xflip != 0 && yflip == 0) {
                    for (yt = 0; yt < ytiles; yt++) {
                        for (xt = 0; xt < xtiles; xt++, code++) {
                            if (zoomed == 0) {
                                drawgfx(bitmap, Machine.gfx[2], code, color, 1, 0,
                                        x + (xtiles - 1 - xt) * 16, y + yt * 16, null, TRANSPARENCY_PEN, 15);
                            } else {
                                drawgfxzoom(bitmap, Machine.gfx[2], code, color, 1, 0,
                                        x + (xtiles - 1 - xt) * xzoom, y + yt * yzoom, null, TRANSPARENCY_PEN, 15,
                                        0x1000 * xzoom, 0x1000 * yzoom);
                            }
                        }
                    }
                } /* yflipped case */ else if (xflip == 0 && yflip != 0) {
                    for (yt = 0; yt < ytiles; yt++) {
                        for (xt = 0; xt < xtiles; xt++, code++) {
                            if (zoomed == 0) {
                                drawgfx(bitmap, Machine.gfx[2], code, color, 0, 1,
                                        x + xt * 16, y + (ytiles - 1 - yt) * 16, null, TRANSPARENCY_PEN, 15);
                            } else {
                                drawgfxzoom(bitmap, Machine.gfx[2], code, color, 0, 1,
                                        x + xt * xzoom, y + (ytiles - 1 - yt) * yzoom, null, TRANSPARENCY_PEN, 15,
                                        0x1000 * xzoom, 0x1000 * yzoom);
                            }
                        }
                    }
                } /* x & yflipped case */ else {
                    for (yt = 0; yt < ytiles; yt++) {
                        for (xt = 0; xt < xtiles; xt++, code++) {
                            if (zoomed == 0) {
                                drawgfx(bitmap, Machine.gfx[2], code, color, 1, 1,
                                        x + (xtiles - 1 - xt) * 16, y + (ytiles - 1 - yt) * 16, null, TRANSPARENCY_PEN, 15);
                            } else {
                                drawgfxzoom(bitmap, Machine.gfx[2], code, color, 1, 1,
                                        x + (xtiles - 1 - xt) * xzoom, y + (ytiles - 1 - yt) * yzoom, null, TRANSPARENCY_PEN, 15,
                                        0x1000 * xzoom, 0x1000 * yzoom);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * ***********************************
     *
     * Main screen refresh
     *
     ************************************
     */
    static void common_screenrefresh(int full_refresh) {
        char[] saved_pens = new char[64];
        int offs;

        /* update the palette usage */
        palette_init_used_colors();
        mark_background_colors();
        if (Machine.gfx[2] != null) {
            mark_sprite_palette();
        }

        /* handle full refresh */
        if (palette_recalc() != null || full_refresh != 0) {
            memset(background_dirty[0], 1, 64 * 64);
            memset(background_dirty[1], 1, 64 * 64);
        }

        /* update background 1 (opaque) */
        for (offs = 0; offs < 64 * 64; offs++) {
            if (background_dirty[0].read(offs) != 0) {
                int color = local_videoram[0].read(offs);
                int code = (local_videoram[0].read(offs + 0x1000) << 8) | local_videoram[0].read(offs + 0x2000);
                int sx = offs % 64;
                int sy = offs / 64;
                drawgfx(background[0], Machine.gfx[0], code, color, 0, 0, sx * 8, sy * 4, null, TRANSPARENCY_NONE, 0);
                background_dirty[0].write(offs, 0);
            }
        }

        /* mark the transparent pens transparent before drawing to background 2 */
        for (offs = 0; offs < 64; offs++) {
            saved_pens[offs] = Machine.gfx[0].colortable.read(offs * 16 + 15);
            Machine.gfx[0].colortable.write(offs * 16 + 15, palette_transparent_pen);
        }

        /* update background 2 (transparent) */
        for (offs = 0; offs < 64 * 64; offs++) {
            if (background_dirty[1].read(offs) != 0) {
                int color = local_videoram[1].read(offs);
                int code = (local_videoram[1].read(offs + 0x1000) << 8) | local_videoram[1].read(offs + 0x2000);
                int sx = offs % 64;
                int sy = offs / 64;
                drawgfx(background[1], Machine.gfx[1], code, color, 0, 0, sx * 8, sy * 4, null, TRANSPARENCY_NONE, 0);
                background_dirty[1].write(offs, 0);
            }
        }

        /* restore the saved pens */
        for (offs = 0; offs < 64; offs++) {
            Machine.gfx[0].colortable.write(offs * 16 + 15, saved_pens[offs]);
        }

    }

    public static VhUpdatePtr pipedrm_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int x, y;

            common_screenrefresh(full_refresh);

            /* draw the lower background */
            x = 0;
            y = (-scroll_regs[1] - 7) & 0xFF;
            copyscrollbitmap(bitmap, background[0], 1, new int[]{x}, 1, new int[]{y}, Machine.visible_area, TRANSPARENCY_NONE, 0);

            /* draw the upper background */
            x = 0;
            y = (-scroll_regs[3] - 7) & 0xFF;
            copyscrollbitmap(bitmap, background[1], 1, new int[]{x}, 1, new int[]{y}, Machine.visible_area, TRANSPARENCY_PEN, palette_transparent_pen);

            /* draw all the sprites (priority doesn't seem to matter) */
            if (Machine.gfx[2] != null) {
                draw_sprites(bitmap, 0);
                draw_sprites(bitmap, 1);
            }
        }
    };

    public static VhUpdatePtr hatris_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int x, y;

            common_screenrefresh(full_refresh);

            /* draw the lower background */
            x = 0;
            y = (-scroll_regs[3] - 7) & 0xFF;
            copyscrollbitmap(bitmap, background[0], 1, new int[]{x}, 1, new int[]{y}, Machine.visible_area, TRANSPARENCY_NONE, 0);

            /* draw the upper background */
            x = 0;
            y = (-scroll_regs[1] - 7) & 0xFF;
            copyscrollbitmap(bitmap, background[1], 1, new int[]{x}, 1, new int[]{y}, Machine.visible_area, TRANSPARENCY_PEN, palette_transparent_pen);
        }
    };
}
