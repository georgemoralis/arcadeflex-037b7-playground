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
import static arcadeflex.v037b7.mame.cpuintrf.cpu_get_pc;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.mame.paletteH.PALETTE_COLOR_USED;
import static arcadeflex.v037b7.mame.sndintrf.soundlatch_w;
//vidhrdw imports
import static arcadeflex.v037b7.vidhrdw.generic.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_init_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_used_colors;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.old.arcadeflex.video.osd_clearbitmap;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;

public class bssoccer {

    public static UBytePtr bssoccer_vregs = new UBytePtr();
    static int flipscreen;

    /* Variables and functions defined in driver */
    public static WriteHandlerPtr bssoccer_vregs_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {

            switch (offset) {
                case 0x00:
                    soundlatch_w.handler(0, data & 0xff);
                    break;
                case 0x02:
                    flipscreen = data & 1;
                    break;	// other bits are used!
                case 0x04:
                    break;	// bits 4&0 used!
                case 0x06:
                    break;	// lev 1 ack
                case 0x08:
                    break;	// lev 2 ack
                default:
                    logerror("CPU#0 PC %06X - Written vreg %02X <- %02X\n", cpu_get_pc(), offset, data);
            }
        }
    };

    static void bssoccer_draw_sprites(osd_bitmap bitmap) {
        int offs;

        int max_x = Machine.drv.screen_width - 8;
        int max_y = Machine.drv.screen_height - 8;

        for (offs = 0xfc00; offs < 0x10000; offs += 4) {
            int srcpg, srcx, srcy, dimx, dimy;
            int tile_x, tile_xinc, tile_xstart;
            int tile_y, tile_yinc;
            int dx, dy;
            int flipx, y0;

            int y = spriteram.READ_WORD(offs + 0);
            int x = spriteram.READ_WORD(offs + 2);
            int dim = spriteram.READ_WORD(offs + 0 + 0x10000);

            int bank = (x >> 12) & 0xf;

            srcpg = ((y & 0xf000) >> 12) + ((x & 0x0200) >> 5);	// src page
            srcx = ((y >> 8) & 0xf) * 2;						// src col
            srcy = ((dim >> 0) & 0xf) * 2;						// src row

            switch ((dim >> 4) & 0xf) {
                case 0x0:
                    dimx = 2;
                    dimy = 2;
                    y0 = 0x100;
                    break;
                case 0x4:
                    dimx = 4;
                    dimy = 4;
                    y0 = 0x100;
                    break;
                case 0x8:
                    dimx = 2;
                    dimy = 32;
                    y0 = 0x130;
                    break;
                case 0xc:
                    dimx = 4;
                    dimy = 32;
                    y0 = 0x120;
                    break;
                default:
                    dimx = 0;
                    dimy = 0;
                    y0 = 0;
            }

            if (dimx == 4) {
                flipx = srcx & 2;
                srcx &= ~2;
            } else {
                flipx = 0;
            }

            x = (x & 0xff) - (x & 0x100);
            y = (y0 - (y & 0xff) - dimy * 8) & 0xff;

            if (flipx != 0) {
                tile_xstart = dimx - 1;
                tile_xinc = -1;
            } else {
                tile_xstart = 0;
                tile_xinc = +1;
            }

            tile_y = 0;
            tile_yinc = +1;

            for (dy = 0; dy < dimy * 8; dy += 8) {
                tile_x = tile_xstart;

                for (dx = 0; dx < dimx * 8; dx += 8) {
                    int addr = ((srcpg * 0x20 * 0x20)
                            + ((srcx + tile_x) & 0x1f) * 0x20
                            + ((srcy + tile_y) & 0x1f)) * 2;

                    int tile = spriteram.READ_WORD(addr);
                    int attr = spriteram.READ_WORD(addr + 0x10000);

                    int sx = x + dx;
                    int sy = (y + dy) & 0xff;

                    int tile_flipx = tile & 0x4000;
                    int tile_flipy = tile & 0x8000;

                    if (flipx != 0) {
                        tile_flipx = NOT(tile_flipx);
                    }

                    if (flipscreen != 0) {
                        sx = max_x - sx;
                        sy = max_y - sy;
                        tile_flipx = NOT(tile_flipx);
                        tile_flipy = NOT(tile_flipy);
                    }

                    drawgfx(bitmap, Machine.gfx[0],
                            (tile & 0x3fff) + bank * 0x4000,
                            attr,
                            tile_flipx, tile_flipy,
                            sx, sy,
                            Machine.visible_area, TRANSPARENCY_PEN, 15);

                    tile_x += tile_xinc;
                }

                tile_y += tile_yinc;
            }

        }

    }

    static void bssoccer_mark_sprite_colors() {
        int offs, i, col;
        int[] colmask = new int[0x100];
        int count = 0;

        int[] pen_usage = Machine.gfx[0].pen_usage;
        int total_elements = Machine.gfx[0].total_elements;
        int color_codes_start = Machine.drv.gfxdecodeinfo[0].color_codes_start;
        int total_color_codes = Machine.drv.gfxdecodeinfo[0].total_color_codes;

        int xmin = Machine.visible_area.min_x;
        int xmax = Machine.visible_area.max_x;
        int ymin = Machine.visible_area.min_y;
        int ymax = Machine.visible_area.max_y;

        memset(colmask, 0, sizeof(colmask));

        for (offs = 0xfc00; offs < 0x10000; offs += 4) {
            int srcpg, srcx, srcy, dimx, dimy;
            int tile_x, tile_xinc, tile_xstart;
            int tile_y, tile_yinc;
            int dx, dy;
            int flipx, y0;

            int y = spriteram.READ_WORD(offs + 0);
            int x = spriteram.READ_WORD(offs + 2);
            int dim = spriteram.READ_WORD(offs + 0 + 0x10000);

            int bank = (x >> 12) & 0xf;

            srcpg = ((y & 0xf000) >> 12) + ((x & 0x0200) >> 5);	// src page
            srcx = ((y >> 8) & 0xf) * 2;						// src col
            srcy = ((dim >> 0) & 0xf) * 2;						// src row

            switch ((dim >> 4) & 0xf) {
                case 0x0:
                    dimx = 2;
                    dimy = 2;
                    y0 = 0x100;
                    break;
                case 0x4:
                    dimx = 4;
                    dimy = 4;
                    y0 = 0x100;
                    break;
                case 0x8:
                    dimx = 2;
                    dimy = 32;
                    y0 = 0x130;
                    break;
                case 0xc:
                    dimx = 4;
                    dimy = 32;
                    y0 = 0x120;
                    break;
                default:
                    dimx = 0;
                    dimy = 0;
                    y0 = 0;
            }

            if (dimx == 4) {
                flipx = srcx & 2;
                srcx &= ~2;
            } else {
                flipx = 0;
            }

            x = (x & 0xff) - (x & 0x100);
            y = (y0 - (y & 0xff) - dimy * 8) & 0xff;

            if (flipx != 0) {
                tile_xstart = dimx - 1;
                tile_xinc = -1;
            } else {
                tile_xstart = 0;
                tile_xinc = +1;
            }

            tile_y = 0;
            tile_yinc = +1;

            /* Mark the pens used by the visible portion of this sprite */
            for (dy = 0; dy < dimy * 8; dy += 8) {
                tile_x = tile_xstart;

                for (dx = 0; dx < dimx * 8; dx += 8) {
                    int addr = ((srcpg * 0x20 * 0x20)
                            + ((srcx + tile_x) & 0x1f) * 0x20
                            + ((srcy + tile_y) & 0x1f)) * 2;

                    int tile = spriteram.READ_WORD(addr);
                    int attr = spriteram.READ_WORD(addr + 0x10000);

                    int color = attr % total_color_codes;

                    int sx = x + dx;
                    int sy = (y + dy) & 0xff;

                    tile = (tile & 0x3fff) + bank * 0x4000;

                    if (((sx + 7) >= xmin) && (sx <= xmax)
                            && ((sy + 7) >= ymin) && (sy <= ymax)) {
                        colmask[color] |= pen_usage[tile % total_elements];
                    }

                    tile_x += tile_xinc;
                }

                tile_y += tile_yinc;
            }

        }

        for (col = 0; col < total_color_codes; col++) {
            for (i = 0; i < 15; i++) // pen 15 is transparent
            {
                if ((colmask[col] & (1 << i)) != 0) {
                    palette_used_colors.write(16 * col + i + color_codes_start, PALETTE_COLOR_USED);
                    count++;
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
    public static VhUpdatePtr bssoccer_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {

            /* Mark the sprites' colors */
            palette_init_used_colors();
            bssoccer_mark_sprite_colors();
            palette_recalc();

            /* Draw the sprites */
            osd_clearbitmap(Machine.scrbitmap);
            /* I believe it's black */
            bssoccer_draw_sprites(bitmap);

        }
    };
}
