/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
//vidrdw imports
import static arcadeflex.v037b7.vidhrdw.generic.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class meteor {

    public static UBytePtr meteor_scrollram = new UBytePtr();

    /**
     * *************************************************************************
     *
     * Draw the game screen in the given osd_bitmap. Do NOT call
     * osd_update_display() from this function, it will be called by the main
     * emulation engine.
     *
     **************************************************************************
     */
    public static VhUpdatePtr meteor_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int offs;

            /* draw the characters as sprites because they could be overlapping */
            fillbitmap(bitmap, Machine.pens[0], Machine.visible_area);

            for (offs = 0; offs < videoram_size[0]; offs++) {
                int code, sx, sy, col;

                sy = 8 * (offs / 32) - (meteor_scrollram.read(offs) & 0x0f);
                sx = 8 * (offs % 32) + ((meteor_scrollram.read(offs) >> 4) & 0x0f);

                code = videoram.read(offs) + ((colorram.read(offs) & 0x01) << 8);
                col = (~colorram.read(offs) >> 4) & 0x07;

                drawgfx(bitmap, Machine.gfx[0],
                        code,
                        col,
                        0, 0,
                        sx, sy,
                        Machine.visible_area, TRANSPARENCY_PEN, 0);
            }
        }
    };
}
