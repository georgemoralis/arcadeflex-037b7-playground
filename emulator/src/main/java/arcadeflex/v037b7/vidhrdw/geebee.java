/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.libc.expressions.*;
import static arcadeflex.common.libc.cstring.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//machine imports
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
//vidrdw imports
import static arcadeflex.v037b7.vidhrdw.generic.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.machine.geebee.geebee_ball_h;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.geebee.geebee_ball_on;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.geebee.geebee_ball_v;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.geebee.geebee_bgw;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.geebee.geebee_inv;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.old.mame.drawgfx.plot_pixel;


public class geebee {

    static char palette[]
            = {
                0x00, 0x00, 0x00, /* black */
                0xff, 0xff, 0xff, /* white */
                0x7f, 0x7f, 0x7f /* grey  */};

    static char geebee_colortable[]
            = {
                0, 1,
                0, 2,
                1, 0,
                2, 0
            };

    static char navalone_colortable[]
            = {
                0, 1,
                0, 2,
                0, 1,
                0, 2
            };

    /*TODO*///#define PINK1	0xa0,0x00,0xe0,OVERLAY_DEFAULT_OPACITY
    /*TODO*///#define PINK2 	0xe0,0x00,0xf0,OVERLAY_DEFAULT_OPACITY
    /*TODO*///#define ORANGE	0xff,0xd0,0x00,OVERLAY_DEFAULT_OPACITY
    /*TODO*///#define BLUE	0x00,0x00,0xff,OVERLAY_DEFAULT_OPACITY
    /*TODO*///#define	END  {{ -1, -1, -1, -1}, 0,0,0,0}
    /*TODO*///static const struct artwork_element geebee_overlay[]=
    /*TODO*///{
    /*TODO*///	{{  1*8,  4*8-1,    0,32*8-1 }, PINK2  },
    /*TODO*///	{{  4*8,  5*8-1,    0, 6*8-1 }, PINK1  },
    /*TODO*///	{{  4*8,  5*8-1, 26*8,32*8-1 }, PINK1  },
    /*TODO*///	{{  4*8,  5*8-1,  6*8,26*8-1 }, ORANGE },
    /*TODO*///	{{  5*8, 28*8-1,    0, 3*8-1 }, PINK1  },
    /*TODO*///	{{  5*8, 28*8-1, 29*8,32*8-1 }, PINK1  },
    /*TODO*///	{{  5*8, 28*8-1,  3*8, 6*8-1 }, BLUE   },
    /*TODO*///	{{  5*8, 28*8-1, 26*8,29*8-1 }, BLUE   },
    /*TODO*///	{{ 12*8, 13*8-1, 15*8,17*8-1 }, BLUE   },
    /*TODO*///	{{ 21*8, 23*8-1, 12*8,14*8-1 }, BLUE   },
    /*TODO*///	{{ 21*8, 23*8-1, 18*8,20*8-1 }, BLUE   },
    /*TODO*///	{{ 28*8, 29*8-1,    0,32*8-1 }, PINK2  },
    /*TODO*///	{{ 29*8, 32*8-1,    0,32*8-1 }, PINK1  },
    /*TODO*///	END
    /*TODO*///};
    public static VhStartPtr geebee_vh_start = new VhStartPtr() {
        public int handler() {
            if (generic_vh_start.handler() != 0) {
                return 1;
            }

            /* use an overlay only in upright mode */
 /*TODO*///if( (readinputport(2) & 0x01) == 0 )
            /*TODO*///{
            /*TODO*///	overlay_create(geebee_overlay, 3, Machine.drv.total_colors-3);
            /*TODO*///}
            return 0;
        }
    };

    public static VhStartPtr navalone_vh_start = new VhStartPtr() {
        public int handler() {
            if (generic_vh_start.handler() != 0) {
                return 1;
            }

            /* overlay? */
            return 0;
        }
    };

    public static VhStartPtr sos_vh_start = new VhStartPtr() {
        public int handler() {
            if (generic_vh_start.handler() != 0) {
                return 1;
            }

            /* overlay? */
            return 0;
        }
    };

    public static VhStartPtr kaitei_vh_start = new VhStartPtr() {
        public int handler() {
            if (generic_vh_start.handler() != 0) {
                return 1;
            }

            /* overlay? */
            return 0;
        }
    };

    /* Initialise the palette */
    public static VhConvertColorPromPtr geebee_init_palette = new VhConvertColorPromPtr() {
        public void handler(char[] sys_palette, char[] sys_colortable, UBytePtr color_prom) {
            memcpy(sys_palette, palette, sizeof(palette));
            memcpy(sys_colortable, geebee_colortable, sizeof(geebee_colortable));
        }
    };

    /* Initialise the palette */
    public static VhConvertColorPromPtr navalone_init_palette = new VhConvertColorPromPtr() {
        public void handler(char[] sys_palette, char[] sys_colortable, UBytePtr color_prom) {
            memcpy(sys_palette, palette, sizeof(palette));
            memcpy(sys_colortable, navalone_colortable, sizeof(navalone_colortable));
        }
    };

    public static void geebee_plot(osd_bitmap bitmap, int x, int y) {
        rectangle r = Machine.visible_area;
        if (x >= r.min_x && x <= r.max_x && y >= r.min_y && y <= r.max_y) {
            plot_pixel.handler(bitmap, x, y, Machine.pens[1]);
        }
    }

    public static void mark_dirty(int x, int y) {
        int cx, cy, offs;
        cy = y / 8;
        cx = x / 8;
        if (geebee_inv != 0) {
            offs = (32 - cx) + (31 - cy) * 32;
            dirtybuffer[offs % videoram_size[0]] = 1;
            dirtybuffer[(offs - 1) & (videoram_size[0] - 1)] = 1;
            dirtybuffer[(offs - 32) & (videoram_size[0] - 1)] = 1;
            dirtybuffer[(offs - 32 - 1) & (videoram_size[0] - 1)] = 1;
        } else {
            offs = (cx - 1) + cy * 32;
            dirtybuffer[offs & (videoram_size[0] - 1)] = 1;
            dirtybuffer[(offs + 1) & (videoram_size[0] - 1)] = 1;
            dirtybuffer[(offs + 32) & (videoram_size[0] - 1)] = 1;
            dirtybuffer[(offs + 32 + 1) & (videoram_size[0] - 1)] = 1;
        }
    }

    public static VhUpdatePtr geebee_vh_screenrefresh = new VhUpdatePtr() {
        public void handler(osd_bitmap bitmap, int full_refresh) {
            int offs;

            if (palette_recalc() != null || full_refresh != 0) {
                memset(dirtybuffer, 1, videoram_size[0]);
            }

            for (offs = 0; offs < videoram_size[0]; offs++) {
                if (dirtybuffer[offs] != 0) {
                    int mx, my, sx, sy, code, color;

                    dirtybuffer[offs] = 0;

                    mx = offs % 32;
                    my = offs / 32;

                    if (my == 0) {
                        sx = 8 * 33;
                        sy = 8 * mx;
                    } else if (my == 1) {
                        sx = 0;
                        sy = 8 * mx;
                    } else {
                        sx = 8 * (mx + 1);
                        sy = 8 * my;
                    }

                    if (geebee_inv != 0) {
                        sx = 33 * 8 - sx;
                        sy = 31 * 8 - sy;
                    }

                    code = videoram.read(offs);
                    color = ((geebee_bgw & 1) << 1) | ((code & 0x80) >> 7);
                    drawgfx(bitmap, Machine.gfx[0],
                            code, color,
                            geebee_inv, geebee_inv, sx, sy,
                            Machine.visible_area, TRANSPARENCY_NONE, 0);
                }
            }

            if (geebee_ball_on != 0) {
                int x, y;

                mark_dirty(geebee_ball_h + 5, geebee_ball_v - 2);
                for (y = 0; y < 4; y++) {
                    for (x = 0; x < 4; x++) {
                        geebee_plot(bitmap, geebee_ball_h + x + 5, geebee_ball_v + y - 2);
                    }
                }
            }
        }
    };
}
