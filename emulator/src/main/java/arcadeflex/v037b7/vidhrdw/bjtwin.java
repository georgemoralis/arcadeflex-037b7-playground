/*
 * ported to v0.037b7
 * ported to v0.36
 *
 */
package arcadeflex.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.libc.cstring.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.mame.common.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_change_color;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_init_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_used_colors;
import static arcadeflex.v037b7.mame.palette.paletteram;
import static gr.codebb.arcadeflex.old.mame.drawgfx.copybitmap;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;

public class bjtwin {

    public static UBytePtr bjtwin_cmdram = new UBytePtr();
    public static UBytePtr bjtwin_workram = new UBytePtr();
    public static UBytePtr bjtwin_spriteram;
    public static UBytePtr bjtwin_txvideoram = new UBytePtr();
    public static UBytePtr bjtwin_videocontrol = new UBytePtr();
    public static int[] bjtwin_txvideoram_size = new int[1];

    static char[] dirtybuffer;
    static osd_bitmap tmpbitmap;
    static int flipscreen = 0;

    public static VhStartPtr bjtwin_vh_start = new VhStartPtr() {
        public int handler() {
            dirtybuffer = new char[bjtwin_txvideoram_size[0] / 2];
            tmpbitmap = bitmap_alloc(Machine.drv.screen_width, Machine.drv.screen_height);

            if (dirtybuffer == null || tmpbitmap == null) {
                if (tmpbitmap != null) {
                    bitmap_free(tmpbitmap);
                }
                if (dirtybuffer != null) {
                    dirtybuffer = null;
                }
                return 1;
            }

            bjtwin_spriteram = new UBytePtr(bjtwin_workram, 0x8000);

            return 0;
        }
    };

    public static VhStopPtr bjtwin_vh_stop = new VhStopPtr() {
        public void handler() {
            bitmap_free(tmpbitmap);
            dirtybuffer = null;

            dirtybuffer = null;
            tmpbitmap = null;
        }
    };

    public static ReadHandlerPtr bjtwin_txvideoram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return bjtwin_txvideoram.READ_WORD(offset);//READ_WORD(&bjtwin_txvideoram[offset]);
        }
    };

    public static WriteHandlerPtr bjtwin_txvideoram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int oldword = bjtwin_txvideoram.READ_WORD(offset);//READ_WORD(&bjtwin_txvideoram[offset]);
            int newword = COMBINE_WORD(oldword, data);

            if (oldword != newword) {
                bjtwin_txvideoram.WRITE_WORD(offset, newword);//WRITE_WORD(&bjtwin_txvideoram[offset],newword);
                dirtybuffer[offset / 2] = 1;
            }
        }
    };

    public static WriteHandlerPtr bjtwin_paletteram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int r, g, b;
            int oldword = paletteram.READ_WORD(offset);///READ_WORD(&paletteram[offset]);
            int newword = COMBINE_WORD(oldword, data);

            paletteram.WRITE_WORD(offset, newword);//WRITE_WORD(&paletteram[offset],newword);

            r = ((newword >> 11) & 0x1e) | ((newword >> 3) & 0x01);
            g = ((newword >> 7) & 0x1e) | ((newword >> 2) & 0x01);
            b = ((newword >> 3) & 0x1e) | ((newword >> 1) & 0x01);

            r = (r << 3) | (r >> 2);
            g = (g << 3) | (g >> 2);
            b = (b << 3) | (b >> 2);

            palette_change_color(offset / 2, r, g, b);
        }
    };

    public static WriteHandlerPtr bjtwin_flipscreen_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if ((data & 1) != flipscreen) {
                flipscreen = data & 1;
                memset(dirtybuffer, 1, bjtwin_txvideoram_size[0] / 2);
            }
        }
    };
    static int oldbgstart = -1;
    public static VhUpdatePtr bjtwin_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {

            int offs, bgstart;

            bgstart = 2048 * (bjtwin_videocontrol.READ_WORD(0) & 0x0f);

            palette_init_used_colors();

            for (offs = (bjtwin_txvideoram_size[0] / 2) - 1; offs >= 0; offs--) {
                int color = (bjtwin_txvideoram.READ_WORD(offs * 2) >> 12);
                //memset(&palette_used_colors[16 * color],PALETTE_COLOR_USED,16);
                for (int i = 0; i < 16; i++) {
                    palette_used_colors.write((16 * color) + i, PALETTE_COLOR_USED);
                }
            }

            for (offs = 0; offs < 256 * 16; offs += 16) {
                if (bjtwin_spriteram.READ_WORD(offs) != 0) {
                    //memset(&palette_used_colors[256 + 16*READ_WORD(&bjtwin_spriteram[offs+14])],PALETTE_COLOR_USED,16);
                    for (int i = 0; i < 16; i++) {
                        palette_used_colors.write((256 + 16 * bjtwin_spriteram.READ_WORD(offs + 14)) + i, PALETTE_COLOR_USED);
                    }
                }
            }

            if (palette_recalc() != null || (oldbgstart != bgstart)) {
                oldbgstart = bgstart;
                memset(dirtybuffer, 1, bjtwin_txvideoram_size[0] / 2);
            }

            for (offs = (bjtwin_txvideoram_size[0] / 2) - 1; offs >= 0; offs--) {
                if (dirtybuffer[offs] != 0) {
                    int sx = offs / 32;
                    int sy = offs % 32;

                    int tilecode = bjtwin_txvideoram.READ_WORD(offs * 2);
                    int bank = (tilecode & 0x800) != 0 ? 1 : 0;

                    if (flipscreen != 0) {
                        sx = 47 - sx;
                        sy = 31 - sy;
                    }

                    drawgfx(tmpbitmap, Machine.gfx[bank],
                            (tilecode & 0x7ff) + ((bank) != 0 ? bgstart : 0),
                            tilecode >> 12,
                            flipscreen, flipscreen,
                            8 * sx, 8 * sy,
                            null, TRANSPARENCY_NONE, 0);

                    dirtybuffer[offs] = 0;
                }
            }

            /* copy the character mapped graphics */
            copybitmap(bitmap, tmpbitmap, 0, 0, 0, 0, Machine.visible_area, TRANSPARENCY_NONE, 0);

            for (offs = 0; offs < 256 * 16; offs += 16) {
                if (bjtwin_spriteram.READ_WORD(offs)/*READ_WORD(&bjtwin_spriteram[offs])*/ != 0) {
                    int sx = (bjtwin_spriteram.READ_WORD(offs + 8) & 0x1ff) + 64;
                    int sy = (bjtwin_spriteram.READ_WORD(offs + 12) & 0x1ff);
                    int tilecode = bjtwin_spriteram.READ_WORD(offs + 6);
                    int xx = (bjtwin_spriteram.READ_WORD(offs + 2) & 0x0f) + 1;
                    int yy = (bjtwin_spriteram.READ_WORD(offs + 2) >> 4) + 1;
                    int width = xx;
                    int delta = 16;
                    int startx = sx;

                    if (flipscreen != 0) {
                        sx = 367 - sx;
                        sy = 239 - sy;
                        delta = -16;
                        startx = sx;
                    }

                    do {
                        do {
                            drawgfx(bitmap, Machine.gfx[2],
                                    tilecode & 0x1fff,
                                    bjtwin_spriteram.READ_WORD(offs + 14),//READ_WORD(&bjtwin_spriteram[offs+14]),
                                    flipscreen, flipscreen,
                                    sx & 0x1ff, sy & 0x1ff,
                                    Machine.visible_area, TRANSPARENCY_PEN, 15);

                            tilecode++;
                            sx += delta;
                        } while ((--xx) != 0);

                        sy += delta;
                        sx = startx;
                        xx = width;
                    } while ((--yy) != 0);
                }
            }
        }
    };
}
