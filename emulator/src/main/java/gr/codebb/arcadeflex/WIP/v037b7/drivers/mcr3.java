/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package gr.codebb.arcadeflex.WIP.v037b7.drivers;

import static gr.codebb.arcadeflex.WIP.v037b7.machine.mcr.*;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.z80fmly.*;
import static gr.codebb.arcadeflex.WIP.v037b7.sndhrdw.mcr.*;
import static gr.codebb.arcadeflex.WIP.v037b7.sndhrdw.mcrH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.mcr3.*;
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static gr.codebb.arcadeflex.old.arcadeflex.fileio.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.inputH.*;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static arcadeflex.v037b7.mame.cpuintrfH.cpu_getpreviouspc;
import static arcadeflex.v037b7.mame.palette.paletteram;

public class mcr3 {

    /**
     * ***********************************
     *
     * Local variables and tables
     *
     ************************************
     */
    static int/*UINT8*/ u8_input_mux;
    static int/*UINT8*/ u8_maxrpm_last_shift;
    static byte maxrpm_p1_shift;
    static byte maxrpm_p2_shift;

    /* Translation table for one-joystick emulation */
    static int one_joy_trans[]
            = {
                0x00, 0x05, 0x0A, 0x00, 0x06, 0x04, 0x08, 0x00,
                0x09, 0x01, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00
            };

    /**
     * ***********************************
     *
     * Discs of Tron input ports
     *
     ************************************
     */
    public static InterruptPtr dotron_interrupt = new InterruptPtr() {
        public int handler() {
            /* pulse the CTC line 2 to enable Platform Poles */
            z80ctc_0_trg2_w.handler(0, 1);
            z80ctc_0_trg2_w.handler(0, 0);
            return mcr_interrupt.handler();
        }
    };

    static char lastfake = 0;
    static int mask = 0x00FF;
    static int count = 0;
    static int delta = 0;

    public static ReadHandlerPtr dotron_port_2_r = new ReadHandlerPtr() {
        public int handler(int offset) {

            int data;
            char fake;

            /* remap up and down on the mouse to aim up and down */
            data = input_port_2_r.handler(offset);
            fake = (char) input_port_6_r.handler(offset);

            delta += (fake - lastfake);
            lastfake = fake;

            /* Map to "aim up" */
            if (delta > 5) {
                mask = 0x00EF;
                count = 5;
                delta = 0;
            } /* Map to "aim down" */ else if (delta < -5) {
                mask = 0x00DF;
                count = 5;
                delta = 0;
            }

            if ((count--) <= 0) {
                count = 0;
                mask = 0x00FF;
            }

            data &= mask;

            return data;
        }
    };

    public static WriteHandlerPtr dotron_port_4_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* light control is in the top 2 bits */
            dotron_change_light(data >> 6);

            /* low 5 bits go to control the Squawk & Talk */
            squawkntalk_data_w.handler(offset, data);
        }
    };

    /**
     * ***********************************
     *
     * Sarge input ports
     *
     ************************************
     */
    public static ReadHandlerPtr sarge_port_1_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return readinputport(1) & ~one_joy_trans[readinputport(6) & 0x0f];
        }
    };

    public static ReadHandlerPtr sarge_port_2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return readinputport(2) & ~one_joy_trans[(readinputport(6) >> 4) & 0x0f];
        }
    };

    /**
     * ***********************************
     *
     * Power Drive input ports
     *
     ************************************
     */
    public static InterruptPtr powerdrv_interrupt = new InterruptPtr() {
        public int handler() {
            /* pulse the CTC line 2 periodically */
            z80ctc_0_trg2_w.handler(0, 1);
            z80ctc_0_trg2_w.handler(0, 0);

            if (cpu_getiloops() == 0) {
                return mcr_interrupt.handler();
            } else {
                return ignore_interrupt.handler();
            }
        }
    };

    public static ReadHandlerPtr powerdrv_port_2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int result = input_port_2_r.handler(offset) & 0x7f;
            return result | (u8_input_mux & 0x80);
        }
    };

    public static WriteHandlerPtr powerdrv_port_7_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* use input_mux for scratch */
            u8_input_mux = ~u8_input_mux & 0x80;
        }
    };

    /**
     * ***********************************
     *
     * Max RPM input ports
     *
     ************************************
     */
    public static WriteHandlerPtr maxrpm_mux_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            u8_input_mux = (data >> 1) & 3;
        }
    };

    public static ReadHandlerPtr maxrpm_port_1_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            /* multiplexed steering wheel/gas pedal */
            return readinputport(6 + u8_input_mux);
        }
    };

    static int shift_bits[] = {0x00, 0x05, 0x06, 0x01, 0x02};
    public static ReadHandlerPtr maxrpm_port_2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int/*UINT8*/ start = readinputport(0) & 0xFF;
            int/*UINT8*/ shift = readinputport(10) & 0xFF;

            /* reset on a start */
            if ((start & 0x08) == 0) {
                maxrpm_p1_shift = 0;
            }
            if ((start & 0x04) == 0) {
                maxrpm_p2_shift = 0;
            }

            /* increment, decrement on falling edge */
            if ((shift & 0x01) == 0 && (u8_maxrpm_last_shift & 0x01) != 0) {
                maxrpm_p1_shift++;
                if (maxrpm_p1_shift > 4) {
                    maxrpm_p1_shift = 4;
                }
            }
            if ((shift & 0x02) == 0 && (u8_maxrpm_last_shift & 0x02) != 0) {
                maxrpm_p1_shift--;
                if (maxrpm_p1_shift < 0) {
                    maxrpm_p1_shift = 0;
                }
            }
            if ((shift & 0x04) == 0 && (u8_maxrpm_last_shift & 0x04) != 0) {
                maxrpm_p2_shift++;
                if (maxrpm_p2_shift > 4) {
                    maxrpm_p2_shift = 4;
                }
            }
            if ((shift & 0x08) == 0 && (u8_maxrpm_last_shift & 0x08) != 0) {
                maxrpm_p2_shift--;
                if (maxrpm_p2_shift < 0) {
                    maxrpm_p2_shift = 0;
                }
            }

            u8_maxrpm_last_shift = shift & 0xFF;

            return ~((shift_bits[maxrpm_p1_shift] << 4) + shift_bits[maxrpm_p2_shift]);
        }
    };

    /**
     * ***********************************
     *
     * Spy Hunter input ports
     *
     ************************************
     */
    public static ReadHandlerPtr spyhunt_port_2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            /* multiplexed steering wheel/gas pedal */
            return readinputport(6 + u8_input_mux);
        }
    };

    static int u8_lastport4;

    public static WriteHandlerPtr spyhunt_port_4_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {

            /* Spy Hunter uses port 4 for talking to the Chip Squeak Deluxe */
 /* (and for toggling the lamps and muxing the analog inputs) */
 /* mux select is in bit 7 */
            u8_input_mux = (data >> 7) & 1;

            /* lamp driver command triggered by bit 5, data is in low four bits */
            if (((u8_lastport4 ^ data) & 0x20) != 0 && (data & 0x20) == 0) {
                u8_spyhunt_lamp[data & 7] = (data >> 3) & 1;
            }

            /* low 5 bits go to control the Chip Squeak Deluxe */
            csdeluxe_data_w.handler(offset, data);

            /* remember the last data */
            u8_lastport4 = data;
        }
    };

    /**
     * ***********************************
     *
     * Turbo Tag kludges
     *
     ************************************
     */
    public static ReadHandlerPtr turbotag_kludge_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            /* The checksum on the ttprog1.bin ROM seems to be bad by 1 bit */
 /* The checksum should come out to $82 but it should be $92     */
 /* Unfortunately, the game refuses to start if any bad ROM is   */
 /* found; to work around this, we catch the checksum byte read  */
 /* and modify it to what we know we will be getting.            */
            if (cpu_getpreviouspc() == 0xb29) {
                return 0x82;
            } else {
                return 0x92;
            }
        }
    };

    /**
     * ***********************************
     *
     * NVRAM save/load
     *
     ************************************
     */
    public static nvramPtr mcr3_nvram_handler = new nvramPtr() {
        public void handler(Object file, int read_or_write) {
            UBytePtr ram = memory_region(REGION_CPU1);
            if (read_or_write != 0) {
                osd_fwrite(file, ram, 0xe000, 0x800);
            } else if (file != null) {
                osd_fread(file, ram, 0xe000, 0x800);
            }
        }
    };

    public static nvramPtr spyhunt_nvram_handler = new nvramPtr() {
        public void handler(Object file, int read_or_write) {
            UBytePtr ram = memory_region(REGION_CPU1);

            if (read_or_write != 0) {
                osd_fwrite(file, ram, 0xf000, 0x800);
            } else if (file != null) {
                osd_fread(file, ram, 0xf000, 0x800);
            }
        }
    };

    /**
     * ***********************************
     *
     * Main CPU memory handlers
     *
     ************************************
     */
    static MemoryReadAddress readmem[]
            = {
                new MemoryReadAddress(0x0000, 0xdfff, MRA_ROM),
                new MemoryReadAddress(0xe000, 0xe9ff, MRA_RAM),
                new MemoryReadAddress(0xf000, 0xf7ff, MRA_RAM),
                new MemoryReadAddress(-1) /* end of table */};

    static MemoryWriteAddress writemem[]
            = {
                new MemoryWriteAddress(0x0000, 0xdfff, MWA_ROM),
                new MemoryWriteAddress(0xe000, 0xe7ff, MWA_RAM),
                new MemoryWriteAddress(0xe800, 0xe9ff, MWA_RAM, spriteram, spriteram_size),
                new MemoryWriteAddress(0xf000, 0xf7ff, mcr3_videoram_w, videoram, videoram_size),
                new MemoryWriteAddress(0xf800, 0xf8ff, mcr3_paletteram_w, paletteram),
                new MemoryWriteAddress(-1) /* end of table */};

    static IOReadPort readport[]
            = {
                new IOReadPort(0x00, 0x00, input_port_0_r),
                new IOReadPort(0x01, 0x01, input_port_1_r),
                new IOReadPort(0x02, 0x02, input_port_2_r),
                new IOReadPort(0x03, 0x03, input_port_3_r),
                new IOReadPort(0x04, 0x04, input_port_4_r),
                new IOReadPort(0x07, 0x07, ssio_status_r),
                new IOReadPort(0x10, 0x10, input_port_0_r),
                new IOReadPort(0xf0, 0xf3, z80ctc_0_r),
                new IOReadPort(-1)
            };

    static IOWritePort writeport[]
            = {
                new IOWritePort(0x00, 0x00, mcr_control_port_w),
                new IOWritePort(0x1c, 0x1f, ssio_data_w),
                new IOWritePort(0x84, 0x86, mcr_scroll_value_w),
                new IOWritePort(0xe0, 0xe0, watchdog_reset_w),
                new IOWritePort(0xe8, 0xe8, MWA_NOP),
                new IOWritePort(0xf0, 0xf3, z80ctc_0_w),
                new IOWritePort(-1)
            };

    /**
     * ***********************************
     *
     * MCR Monoboard CPU memory handlers
     *
     ************************************
     */
    static MemoryWriteAddress mcrmono_writemem[]
            = {
                new MemoryWriteAddress(0x0000, 0xdfff, MWA_ROM),
                new MemoryWriteAddress(0xe000, 0xe7ff, MWA_RAM),
                new MemoryWriteAddress(0xe800, 0xebff, MWA_RAM, spriteram, spriteram_size),
                new MemoryWriteAddress(0xec00, 0xecff, mcr3_paletteram_w, paletteram),
                new MemoryWriteAddress(0xf000, 0xf7ff, mcr3_videoram_w, videoram, videoram_size),
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * ***********************************
     *
     * Spy Hunter main CPU memory handlers
     *
     ************************************
     */
    static MemoryReadAddress spyhunt_readmem[]
            = {
                new MemoryReadAddress(0x0000, 0xdfff, MRA_ROM),
                new MemoryReadAddress(0xe000, 0xebff, MRA_RAM),
                new MemoryReadAddress(0xf000, 0xffff, MRA_RAM),
                new MemoryReadAddress(-1) /* end of table */};

    static MemoryWriteAddress spyhunt_writemem[]
            = {
                new MemoryWriteAddress(0x0000, 0xdfff, MWA_ROM),
                new MemoryWriteAddress(0xe000, 0xe7ff, videoram_w, videoram, videoram_size),
                new MemoryWriteAddress(0xe800, 0xebff, MWA_RAM, spyhunt_alpharam, spyhunt_alpharam_size),
                new MemoryWriteAddress(0xf000, 0xf7ff, MWA_RAM),
                new MemoryWriteAddress(0xf800, 0xf9ff, MWA_RAM, spriteram, spriteram_size),
                new MemoryWriteAddress(0xfa00, 0xfaff, mcr3_paletteram_w, paletteram),
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * ***********************************
     *
     * Port definitions
     *
     ************************************
     */
    static InputPortPtr input_ports_tapper = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_TILT);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_SERVICE(0x80, IP_ACTIVE_LOW);

            PORT_START();
            /* IN1 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_4WAY);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_4WAY);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_4WAY);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_4WAY);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1);
            PORT_BIT(0xe0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN2 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_4WAY | IPF_COCKTAIL);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_4WAY | IPF_COCKTAIL);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_4WAY | IPF_COCKTAIL);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_4WAY | IPF_COCKTAIL);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_COCKTAIL);
            PORT_BIT(0xe0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_DIPNAME(0x04, 0x00, DEF_STR("Demo_Sounds"));
            PORT_DIPSETTING(0x04, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_DIPNAME(0x40, 0x40, DEF_STR("Cabinet"));
            PORT_DIPSETTING(0x40, DEF_STR("Upright"));
            PORT_DIPSETTING(0x00, DEF_STR("Cocktail"));
            PORT_BIT(0xbb, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_timber = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_TILT);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_SERVICE(0x80, IP_ACTIVE_LOW);

            PORT_START();
            /* IN1 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_4WAY | IPF_PLAYER1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_4WAY | IPF_PLAYER1);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_4WAY | IPF_PLAYER1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_4WAY | IPF_PLAYER1);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN2 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_4WAY | IPF_PLAYER2);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_4WAY | IPF_PLAYER2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_4WAY | IPF_PLAYER2);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_4WAY | IPF_PLAYER2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_DIPNAME(0x04, 0x00, DEF_STR("Demo_Sounds"));
            PORT_DIPSETTING(0x04, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_BIT(0xfb, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_dotron = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_TILT);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_COIN3);
            PORT_SERVICE(0x80, IP_ACTIVE_LOW);

            PORT_START();
            /* IN1 */
            PORT_ANALOGX(0xff, 0x00, IPT_DIAL | IPF_REVERSE, 50, 10, 0, 0, KEYCODE_Z, KEYCODE_X, 0, 0);

            PORT_START();
            /* IN2 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_8WAY);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_8WAY);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_8WAY);
            PORT_BITX(0x10, IP_ACTIVE_LOW, IPT_BUTTON3, "Aim Down", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BITX(0x20, IP_ACTIVE_LOW, IPT_BUTTON4, "Aim Up", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_BUTTON2);
            /* we default to Environmental otherwise speech is disabled */
            PORT_DIPNAME(0x80, 0x00, DEF_STR("Cabinet"));
            PORT_DIPSETTING(0x00, "Environmental");
            PORT_DIPSETTING(0x80, DEF_STR("Upright"));

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_DIPNAME(0x01, 0x01, "Coin Meters");
            PORT_DIPSETTING(0x01, "1");
            PORT_DIPSETTING(0x00, "2");
            PORT_BIT(0xfe, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* fake port to make aiming up & down easier */
            PORT_ANALOG(0xff, 0x00, IPT_TRACKBALL_Y, 100, 10, 0, 0);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_destderb = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_SERVICE(0x20, IP_ACTIVE_LOW);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_TILT);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN1 -- the high 6 bits contain the steering wheel value */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1);
            PORT_ANALOG(0xfc, 0x00, IPT_DIAL | IPF_REVERSE, 50, 10, 0, 0);

            PORT_START();
            /* IN2 -- the high 6 bits contain the steering wheel value */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2);
            PORT_ANALOG(0xfc, 0x00, IPT_DIAL | IPF_REVERSE | IPF_PLAYER2, 50, 10, 0, 0);

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_DIPNAME(0x01, 0x01, DEF_STR("Cabinet"));
            PORT_DIPSETTING(0x01, "2P Upright");
            PORT_DIPSETTING(0x00, "4P Cocktail");
            PORT_DIPNAME(0x02, 0x02, DEF_STR("Difficulty"));
            PORT_DIPSETTING(0x02, "Normal");
            PORT_DIPSETTING(0x00, "Harder");
            PORT_DIPNAME(0x04, 0x04, DEF_STR("Free_Play"));
            PORT_DIPSETTING(0x04, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_DIPNAME(0x08, 0x08, "Reward Screen");
            PORT_DIPSETTING(0x08, "Expanded");
            PORT_DIPSETTING(0x00, "Limited");
            PORT_DIPNAME(0x30, 0x30, DEF_STR("Coinage"));
            PORT_DIPSETTING(0x20, DEF_STR("2C_1C"));
            PORT_DIPSETTING(0x00, DEF_STR("2C_2C"));
            PORT_DIPSETTING(0x30, DEF_STR("1C_1C"));
            PORT_DIPSETTING(0x10, DEF_STR("1C_2C"));
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN4 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN3);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN4);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START3);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START4);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER3);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER3);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER4);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER4);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_sarge = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_TILT);
            PORT_SERVICE(0x20, IP_ACTIVE_LOW);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN1 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICKLEFT_UP | IPF_2WAY | IPF_PLAYER1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICKLEFT_DOWN | IPF_2WAY | IPF_PLAYER1);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICKRIGHT_UP | IPF_2WAY | IPF_PLAYER1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICKRIGHT_DOWN | IPF_2WAY | IPF_PLAYER1);
            PORT_BIT(0x30, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1);

            PORT_START();
            /* IN2 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICKLEFT_UP | IPF_2WAY | IPF_PLAYER2);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICKLEFT_DOWN | IPF_2WAY | IPF_PLAYER2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICKRIGHT_UP | IPF_2WAY | IPF_PLAYER2);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICKRIGHT_DOWN | IPF_2WAY | IPF_PLAYER2);
            PORT_BIT(0x30, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2);

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_DIPNAME(0x08, 0x08, DEF_STR("Free_Play"));
            PORT_DIPSETTING(0x08, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_DIPNAME(0x30, 0x30, DEF_STR("Coinage"));
            PORT_DIPSETTING(0x20, DEF_STR("2C_1C"));
            PORT_DIPSETTING(0x30, DEF_STR("1C_1C"));
            PORT_DIPSETTING(0x10, DEF_STR("1C_2C"));
            /* 0x00 says 2 Coins/2 Credits in service mode, but gives 1 Coin/1 Credit */
            PORT_BIT(0xc7, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* fake port for single joystick control */
 /* This fake port is handled via sarge_port_1_r and sarge_port_2_r */
            PORT_BIT(0x01, IP_ACTIVE_HIGH, IPT_JOYSTICK_UP | IPF_8WAY | IPF_CHEAT | IPF_PLAYER1);
            PORT_BIT(0x02, IP_ACTIVE_HIGH, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_CHEAT | IPF_PLAYER1);
            PORT_BIT(0x04, IP_ACTIVE_HIGH, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_CHEAT | IPF_PLAYER1);
            PORT_BIT(0x08, IP_ACTIVE_HIGH, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_CHEAT | IPF_PLAYER1);
            PORT_BIT(0x10, IP_ACTIVE_HIGH, IPT_JOYSTICK_UP | IPF_8WAY | IPF_CHEAT | IPF_PLAYER2);
            PORT_BIT(0x20, IP_ACTIVE_HIGH, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_CHEAT | IPF_PLAYER2);
            PORT_BIT(0x40, IP_ACTIVE_HIGH, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_CHEAT | IPF_PLAYER2);
            PORT_BIT(0x80, IP_ACTIVE_HIGH, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_CHEAT | IPF_PLAYER2);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_rampage = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x0c, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_TILT);
            PORT_SERVICE(0x20, IP_ACTIVE_LOW);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN1 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN2 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_DIPNAME(0x03, 0x03, DEF_STR("Difficulty"));
            PORT_DIPSETTING(0x02, "Easy");
            PORT_DIPSETTING(0x03, "Normal");
            PORT_DIPSETTING(0x01, "Hard");
            PORT_DIPSETTING(0x00, DEF_STR("Free_Play"));
            PORT_DIPNAME(0x04, 0x04, "Score Option");
            PORT_DIPSETTING(0x04, "Keep score when continuing");
            PORT_DIPSETTING(0x00, "Lose score when continuing");
            PORT_DIPNAME(0x08, 0x08, DEF_STR("Coin_A"));
            PORT_DIPSETTING(0x00, DEF_STR("2C_1C"));
            PORT_DIPSETTING(0x08, DEF_STR("1C_1C"));
            PORT_DIPNAME(0x70, 0x70, DEF_STR("Coin_B"));
            PORT_DIPSETTING(0x00, DEF_STR("3C_1C"));
            PORT_DIPSETTING(0x10, DEF_STR("2C_1C"));
            PORT_DIPSETTING(0x70, DEF_STR("1C_1C"));
            PORT_DIPSETTING(0x60, DEF_STR("1C_2C"));
            PORT_DIPSETTING(0x50, DEF_STR("1C_3C"));
            PORT_DIPSETTING(0x40, DEF_STR("1C_4C"));
            PORT_DIPSETTING(0x30, DEF_STR("1C_5C"));
            PORT_DIPSETTING(0x20, DEF_STR("1C_6C"));
            PORT_BITX(0x80, 0x80, IPT_DIPSWITCH_NAME | IPF_CHEAT, "Rack Advance", KEYCODE_F1, IP_JOY_NONE);
            PORT_DIPSETTING(0x80, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));

            PORT_START();
            /* IN4 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_4WAY | IPF_PLAYER3);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_4WAY | IPF_PLAYER3);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_4WAY | IPF_PLAYER3);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_4WAY | IPF_PLAYER3);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER3);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER3);
            PORT_BIT(0xc0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_powerdrv = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_COIN3);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_TILT);
            PORT_SERVICE(0x20, IP_ACTIVE_LOW);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_COIN4);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN1 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_BUTTON3 | IPF_PLAYER1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_BUTTON4 | IPF_PLAYER1 | IPF_TOGGLE);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON3 | IPF_PLAYER2);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON4 | IPF_PLAYER2 | IPF_TOGGLE);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2);

            PORT_START();
            /* IN2 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_BUTTON3 | IPF_PLAYER3);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_BUTTON4 | IPF_PLAYER3 | IPF_TOGGLE);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER3);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER3);
            PORT_BIT(0xf0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_DIPNAME(0x03, 0x03, DEF_STR("Coinage"));
            PORT_DIPSETTING(0x02, DEF_STR("2C_1C"));
            /*	PORT_DIPSETTING(    0x00, DEF_STR( "1C_1C") );*/
            PORT_DIPSETTING(0x03, DEF_STR("1C_1C"));
            PORT_DIPSETTING(0x01, DEF_STR("1C_2C"));
            PORT_BIT(0x0c, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_DIPNAME(0x30, 0x30, DEF_STR("Difficulty"));
            PORT_DIPSETTING(0x20, "Easy");
            PORT_DIPSETTING(0x30, "Factory");
            PORT_DIPSETTING(0x10, "Hard");
            PORT_DIPSETTING(0x00, "Hardest");
            PORT_DIPNAME(0x40, 0x40, DEF_STR("Demo_Sounds"));
            PORT_DIPSETTING(0x00, DEF_STR("Off"));
            PORT_DIPSETTING(0x40, DEF_STR("On"));
            PORT_BITX(0x80, 0x80, IPT_DIPSWITCH_NAME | IPF_CHEAT, "Rack Advance", KEYCODE_F1, IP_JOY_NONE);
            PORT_DIPSETTING(0x80, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_HIGH, IPT_UNKNOWN);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_maxrpm = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_TILT);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_UNKNOWN);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_UNKNOWN);
            PORT_SERVICE(0x80, IP_ACTIVE_LOW);

            PORT_START();
            /* IN1 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN2 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_DIPNAME(0x08, 0x08, DEF_STR("Free_Play"));
            PORT_DIPSETTING(0x08, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_DIPNAME(0x30, 0x30, DEF_STR("Coinage"));
            PORT_DIPSETTING(0x20, DEF_STR("2C_1C"));
            PORT_DIPSETTING(0x30, DEF_STR("1C_1C"));
            PORT_DIPSETTING(0x10, DEF_STR("1C_2C"));
            /* 0x00 says 2 Coins/2 Credits in service mode, but gives 1 Coin/1 Credit */
            PORT_BIT(0xc7, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* new fake for acceleration */
            PORT_ANALOG(0xff, 0x30, IPT_PEDAL | IPF_REVERSE | IPF_PLAYER2, 100, 10, 0x30, 0xff);

            PORT_START();
            /* new fake for acceleration */
            PORT_ANALOG(0xff, 0x30, IPT_PEDAL | IPF_REVERSE | IPF_PLAYER1, 100, 10, 0x30, 0xff);

            PORT_START();
            /* new fake for steering */
            PORT_ANALOG(0xff, 0x74, IPT_PADDLE | IPF_PLAYER2 | IPF_REVERSE, 40, 10, 0x34, 0xb4);

            PORT_START();
            /* new fake for steering */
            PORT_ANALOG(0xff, 0x74, IPT_PADDLE | IPF_PLAYER1 | IPF_REVERSE, 40, 10, 0x34, 0xb4);

            PORT_START();
            /* fake for shifting */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2);
            PORT_BIT(0xf0, IP_ACTIVE_LOW, IPT_UNKNOWN);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_spyhunt = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x0c, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BITX(0x10, IP_ACTIVE_LOW, IPT_BUTTON6 | IPF_TOGGLE, "Gear Shift", KEYCODE_ENTER, IP_JOY_DEFAULT);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_TILT);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_SERVICE);
            PORT_SERVICE(0x80, IP_ACTIVE_LOW);

            PORT_START();
            /* IN1 -- various buttons, low 5 bits */
            PORT_BITX(0x01, IP_ACTIVE_LOW, IPT_BUTTON4, "Oil Slick", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BITX(0x02, IP_ACTIVE_LOW, IPT_BUTTON5, "Missiles", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BITX(0x04, IP_ACTIVE_LOW, IPT_BUTTON3, "Weapon Truck", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START1);
            PORT_BITX(0x08, IP_ACTIVE_LOW, IPT_BUTTON2, "Smoke Screen", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BITX(0x10, IP_ACTIVE_LOW, IPT_BUTTON1, "Machine Guns", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BIT(0xe0, IP_ACTIVE_HIGH, IPT_UNUSED);

            PORT_START();
            /* IN2 -- actually not used at all, but read as a trakport */
            PORT_BIT(0xff, IP_ACTIVE_HIGH, IPT_UNUSED);

            PORT_START();
            /* IN3 -- dipswitches -- low 4 bits only */
            PORT_DIPNAME(0x01, 0x01, "Game Timer");
            PORT_DIPSETTING(0x00, "1:00");
            PORT_DIPSETTING(0x01, "1:30");
            PORT_DIPNAME(0x02, 0x00, DEF_STR("Demo_Sounds"));
            PORT_DIPSETTING(0x02, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_UNKNOWN);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_UNKNOWN);
            PORT_BIT(0xf0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* new fake for acceleration */
            PORT_ANALOG(0xff, 0x30, IPT_PEDAL, 100, 10, 0x30, 0xff);

            PORT_START();
            /* new fake for steering */
            PORT_ANALOG(0xff, 0x74, IPT_PADDLE, 40, 10, 0x34, 0xb4);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_turbotag = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x0c, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BITX(0x10, IP_ACTIVE_LOW, IPT_BUTTON6 | IPF_TOGGLE, "Gear Shift", KEYCODE_ENTER, IP_JOY_DEFAULT);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_TILT);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_SERVICE);
            PORT_SERVICE(0x80, IP_ACTIVE_LOW);

            PORT_START();
            /* IN1 -- various buttons, low 5 bits */
            PORT_BITX(0x01, IP_ACTIVE_LOW, IPT_BUTTON4, "Left Button", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_START1);
            PORT_BITX(0x02, IP_ACTIVE_LOW, IPT_BUTTON5, "Left Trigger", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BITX(0x04, IP_ACTIVE_LOW, IPT_BUTTON3, "Center Button", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BITX(0x08, IP_ACTIVE_LOW, IPT_BUTTON2, "Right Button", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BITX(0x10, IP_ACTIVE_LOW, IPT_BUTTON1, "Right Trigger", IP_KEY_DEFAULT, IP_JOY_DEFAULT);
            PORT_BIT(0xe0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN2 -- actually not used at all, but read as a trakport */
            PORT_BIT(0xff, IP_ACTIVE_HIGH, IPT_UNUSED);

            PORT_START();
            /* IN3 -- dipswitches -- low 4 bits only */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_UNKNOWN);
            PORT_DIPNAME(0x02, 0x00, DEF_STR("Demo_Sounds"));
            PORT_DIPSETTING(0x02, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_UNKNOWN);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_UNKNOWN);
            PORT_BIT(0xf0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* new fake for acceleration */
            PORT_ANALOG(0xff, 0x30, IPT_PEDAL, 100, 10, 0x30, 0xc0);

            PORT_START();
            /* new fake for steering */
            PORT_ANALOG(0xff, 0x74, IPT_PADDLE, 40, 10, 0x34, 0xb4);
            INPUT_PORTS_END();
        }
    };

    static InputPortPtr input_ports_crater = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* IN0 */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_TILT);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_SERVICE(0x80, IP_ACTIVE_LOW);

            PORT_START();
            /* IN1 */
            PORT_ANALOGX(0xff, 0x00, IPT_DIAL | IPF_REVERSE, 25, 10, 0, 0, KEYCODE_Z, KEYCODE_X, 0, 0);

            PORT_START();
            /* IN2 */
            PORT_BIT(0x03, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_2WAY);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_2WAY);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON3);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_BUTTON2);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* IN3 -- dipswitches */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);

            PORT_START();
            /* IN4 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* AIN0 */
            PORT_BIT(0xff, IP_ACTIVE_LOW, IPT_UNKNOWN);
            INPUT_PORTS_END();
        }
    };

    /**
     * ***********************************
     *
     * Graphics definitions
     *
     ************************************
     */
    static GfxLayout spyhunt_charlayout = new GfxLayout(
            64, 32,
            RGN_FRAC(1, 2),
            4,
            new int[]{RGN_FRAC(1, 2), RGN_FRAC(1, 2) + 1, 0, 1},
            new int[]{0, 0, 2, 2, 4, 4, 6, 6, 8, 8, 10, 10, 12, 12, 14, 14,
                16, 16, 18, 18, 20, 20, 22, 22, 24, 24, 26, 26, 28, 28, 30, 30,
                32, 32, 34, 34, 36, 36, 38, 38, 40, 40, 42, 42, 44, 44, 46, 46,
                48, 48, 50, 50, 52, 52, 54, 54, 56, 56, 58, 58, 60, 60, 62, 62},
            new int[]{0 * 32, 0 * 32, 2 * 32, 2 * 32, 4 * 32, 4 * 32, 6 * 32, 6 * 32,
                8 * 32, 8 * 32, 10 * 32, 10 * 32, 12 * 32, 12 * 32, 14 * 32, 14 * 32,
                16 * 32, 16 * 32, 18 * 32, 18 * 32, 20 * 32, 20 * 32, 22 * 32, 22 * 32,
                24 * 32, 24 * 32, 26 * 32, 26 * 32, 28 * 32, 28 * 32, 30 * 32, 30 * 32},
            128 * 8
    );

    static GfxLayout spyhunt_alphalayout = new GfxLayout(
            16, 16,
            RGN_FRAC(1, 1),
            2,
            new int[]{0, 1},
            new int[]{0, 0, 2, 2, 4, 4, 6, 6, 8, 8, 10, 10, 12, 12, 14, 14},
            new int[]{0, 0, 2 * 8, 2 * 8, 4 * 8, 4 * 8, 6 * 8, 6 * 8, 8 * 8, 8 * 8, 10 * 8, 10 * 8, 12 * 8, 12 * 8, 14 * 8, 14 * 8},
            16 * 8
    );

    static GfxDecodeInfo gfxdecodeinfo[]
            = {
                new GfxDecodeInfo(REGION_GFX1, 0, mcr_bg_layout, 0, 4),
                new GfxDecodeInfo(REGION_GFX2, 0, mcr_sprite_layout, 0, 4),
                new GfxDecodeInfo(-1) /* end of array */};

    static GfxDecodeInfo spyhunt_gfxdecodeinfo[]
            = {
                new GfxDecodeInfo(REGION_GFX1, 0, spyhunt_charlayout, 1 * 16, 1),
                new GfxDecodeInfo(REGION_GFX2, 0, mcr_sprite_layout, 0 * 16, 4),
                new GfxDecodeInfo(REGION_GFX3, 0, spyhunt_alphalayout, 8 * 16, 1),
                new GfxDecodeInfo(-1) /* end of array */};

    /**
     * ***********************************
     *
     * Machine drivers
     *
     ************************************
     */
    /* General MCR3 system */
    static MachineDriver machine_driver_mcr3 = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        readmem, writemem, readport, writeport,
                        mcr_interrupt, 1,
                        null, 0, mcr_daisy_chain
                ),
                SOUND_CPU_SSIO
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION,
            1,
            mcr_init_machine,
            /* video hardware */
            32 * 16, 30 * 16, new rectangle(0 * 16, 32 * 16 - 1, 0 * 16, 30 * 16 - 1),
            gfxdecodeinfo,
            8 * 16, 8 * 16,
            null,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            generic_vh_start,
            generic_vh_stop,
            mcr3_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            new MachineSound[]{
                SOUND_SSIO
            },
            mcr3_nvram_handler
    );

    /* Discs of Tron = General MCR3 with Squawk & Talk, and backdrop support */
    static MachineDriver machine_driver_dotron = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                //MAIN_CPU(dotron_interrupt),
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        readmem, writemem, readport, writeport,
                        dotron_interrupt, 1,
                        null, 0, mcr_daisy_chain),
                SOUND_CPU_SSIO,
                SOUND_CPU_SQUAWK_N_TALK
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION, /* frames per second, vblank duration */
            1,
            mcr_init_machine,
            /* video hardware */
            800, 600, new rectangle(0, 800 - 1, 0, 600 - 1),
            gfxdecodeinfo,
            4 * 16 + 32768, 4 * 16, /* The extra colors are for the backdrop */
            null,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            dotron_vh_start,
            generic_vh_stop,
            dotron_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            new MachineSound[]{
                SOUND_SSIO,
                SOUND_SQUAWK_N_TALK
            },
            mcr3_nvram_handler
    );

    /* Destruction Derby = General MCR3 with Turbo Chip Squeak instead of SSIO */
    static MachineDriver machine_driver_destderb = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        readmem, writemem, readport, writeport,
                        mcr_interrupt, 1,
                        null, 0, mcr_daisy_chain
                )
                ,SOUND_CPU_TURBO_CHIP_SQUEAK
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION,
            1,
            mcr_init_machine,
            /* video hardware */
            32 * 16, 30 * 16, new rectangle(0 * 16, 32 * 16 - 1, 0 * 16, 30 * 16 - 1),
            gfxdecodeinfo,
            8 * 16, 8 * 16,
            null,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            generic_vh_start,
            generic_vh_stop,
            mcr3_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            new MachineSound[]{
                SOUND_TURBO_CHIP_SQUEAK
            },
            
            mcr3_nvram_handler
    );

    /* Sarge/Demolition Derby Mono/Max RPM = MCR monoboardmonoboard = MCR3 with no SSIO */
 /* in this case, Turbo Chip Squeak is used for sound */
    static MachineDriver machine_driver_sarge = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        readmem, mcrmono_writemem, readport, writeport,
                        mcr_interrupt, 1,
                        null, 0, mcr_daisy_chain
                )
            ,SOUND_CPU_TURBO_CHIP_SQUEAK
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION,
            1,
            mcr_init_machine,
            /* video hardware */
            32 * 16, 30 * 16, new rectangle(0 * 16, 32 * 16 - 1, 0 * 16, 30 * 16 - 1),
            gfxdecodeinfo,
            8 * 16, 8 * 16,
            null,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            generic_vh_start,
            generic_vh_stop,
            mcrmono_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            new MachineSound[]{
                SOUND_TURBO_CHIP_SQUEAK
            },
            
            null
    );

    /* Rampage = MCR monoboard with Sounds Good */
    static MachineDriver machine_driver_rampage = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                //MONO_CPU(mcr_interrupt)
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        readmem, mcrmono_writemem, readport, writeport,
                        mcr_interrupt, 1,
                        null, 0, mcr_daisy_chain)
            /*TODO*///			,SOUND_CPU_SOUNDS_GOOD
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION,
            1,
            mcr_init_machine,
            /* video hardware */
            32 * 16, 30 * 16, new rectangle(0 * 16, 32 * 16 - 1, 0 * 16, 30 * 16 - 1),
            gfxdecodeinfo,
            8 * 16, 8 * 16,
            null,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            generic_vh_start,
            generic_vh_stop,
            mcrmono_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            /*TODO*///		new MachineSound[] {
            /*TODO*///			SOUND_SOUNDS_GOOD
            /*TODO*///		},
            null,
            null
    );

    /* Power Drive = MCR monoboard with Sounds Good and external interrupts */
    static MachineDriver machine_driver_powerdrv = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        readmem, mcrmono_writemem, readport, writeport,
                        powerdrv_interrupt, 2,
                        null, 0, mcr_daisy_chain
                )
            /*TODO*///			,SOUND_CPU_SOUNDS_GOOD
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION,
            1,
            mcr_init_machine,
            /* video hardware */
            32 * 16, 30 * 16, new rectangle(0 * 16, 32 * 16 - 1, 0 * 16, 30 * 16 - 1),
            gfxdecodeinfo,
            8 * 16, 8 * 16,
            null,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            generic_vh_start,
            generic_vh_stop,
            mcrmono_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            /*TODO*///		new MachineSound[] {
            /*TODO*///			SOUND_SOUNDS_GOOD
            /*TODO*///		},
            null,
            null
    );

    /* Spy Hunter = MCR3 with altered memory map, scrolling, special lamps, and a chip squeak deluxe */
    static MachineDriver machine_driver_spyhunt = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                //SPYHUNT_CPU(mcr_interrupt)
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        spyhunt_readmem, spyhunt_writemem, readport, writeport,
                        mcr_interrupt, 1,
                        null, 0, mcr_daisy_chain)

            /*TODO*///			,SOUND_CPU_SSIO,
/*TODO*///			SOUND_CPU_CHIP_SQUEAK_DELUXE
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION,
            1,
            mcr_init_machine,
            /* video hardware */
            31 * 16, 30 * 16, new rectangle(0, 31 * 16 - 1, 0, 30 * 16 - 1),
            spyhunt_gfxdecodeinfo,
            8 * 16 + 4, 8 * 16 + 4,
            spyhunt_vh_convert_color_prom,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            spyhunt_vh_start,
            spyhunt_vh_stop,
            spyhunt_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            /*TODO*///		new MachineSound[] {
            /*TODO*///			SOUND_SSIO,
            /*TODO*///			SOUND_CHIP_SQUEAK_DELUXE
            /*TODO*///		},
            null,
            spyhunt_nvram_handler
    );

    /* Turbo Tag = Spy Hunter with no SSIO */
    static MachineDriver machine_driver_turbotag = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                //SPYHUNT_CPU(mcr_interrupt),
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        spyhunt_readmem, spyhunt_writemem, readport, writeport,
                        mcr_interrupt, 1,
                        null, 0, mcr_daisy_chain)
            /*TODO*///			,SOUND_CPU_CHIP_SQUEAK_DELUXE
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION,
            1,
            mcr_init_machine,
            /* video hardware */
            30 * 16, 30 * 16, new rectangle(0, 30 * 16 - 1, 0, 30 * 16 - 1),
            spyhunt_gfxdecodeinfo,
            8 * 16 + 4, 8 * 16 + 4,
            spyhunt_vh_convert_color_prom,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            spyhunt_vh_start,
            spyhunt_vh_stop,
            spyhunt_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            /*TODO*///		new MachineSound[] {
            /*TODO*///			SOUND_CHIP_SQUEAK_DELUXE
            /*TODO*///		},
            null,
            spyhunt_nvram_handler
    );

    /* Crater Raider = Spy Hunter with no Chip Squeak Deluxe */
    static MachineDriver machine_driver_crater = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                new MachineCPU(
                        CPU_Z80,
                        5000000, /* 5 MHz */
                        spyhunt_readmem, spyhunt_writemem, readport, writeport,
                        mcr_interrupt, 1,
                        null, 0, mcr_daisy_chain
                ),
                SOUND_CPU_SSIO
            },
            30, DEFAULT_REAL_30HZ_VBLANK_DURATION,
            1,
            mcr_init_machine,
            /* video hardware */
            30 * 16, 30 * 16, new rectangle(0, 30 * 16 - 1, 0, 30 * 16 - 1),
            spyhunt_gfxdecodeinfo,
            8 * 16 + 4, 8 * 16 + 4,
            spyhunt_vh_convert_color_prom,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE | VIDEO_UPDATE_BEFORE_VBLANK,
            null,
            spyhunt_vh_start,
            spyhunt_vh_stop,
            spyhunt_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO, 0, 0, 0,
            new MachineSound[]{
                SOUND_SSIO
            },
            spyhunt_nvram_handler
    );

    /**
     * ***********************************
     *
     * ROM definitions
     *
     ************************************
     */
    static RomLoadPtr rom_tapper = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("tappg0.bin", 0x00000, 0x4000, 0x127171d1);
            ROM_LOAD("tappg1.bin", 0x04000, 0x4000, 0x9d6a47f7);
            ROM_LOAD("tappg2.bin", 0x08000, 0x4000, 0x3a1f8778);
            ROM_LOAD("tappg3.bin", 0x0c000, 0x2000, 0xe8dcdaa4);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("tapsnda7.bin", 0x0000, 0x1000, 0x0e8bb9d5);
            ROM_LOAD("tapsnda8.bin", 0x1000, 0x1000, 0x0cf0e29b);
            ROM_LOAD("tapsnda9.bin", 0x2000, 0x1000, 0x31eb6dc6);
            ROM_LOAD("tapsda10.bin", 0x3000, 0x1000, 0x01a9be6a);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("tapbg1.bin", 0x00000, 0x4000, 0x2a30238c);
            ROM_LOAD("tapbg0.bin", 0x04000, 0x4000, 0x394ab576);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("tapfg1.bin", 0x00000, 0x4000, 0x32509011);
            ROM_LOAD("tapfg0.bin", 0x04000, 0x4000, 0x8412c808);
            ROM_LOAD("tapfg3.bin", 0x08000, 0x4000, 0x818fffd4);
            ROM_LOAD("tapfg2.bin", 0x0c000, 0x4000, 0x67e37690);
            ROM_LOAD("tapfg5.bin", 0x10000, 0x4000, 0x800f7c8a);
            ROM_LOAD("tapfg4.bin", 0x14000, 0x4000, 0x32674ee6);
            ROM_LOAD("tapfg7.bin", 0x18000, 0x4000, 0x070b4c81);
            ROM_LOAD("tapfg6.bin", 0x1c000, 0x4000, 0xa37aef36);
            ROM_END();
        }
    };

    static RomLoadPtr rom_tappera = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("pr00_1c.128", 0x00000, 0x4000, 0xbb060bb0);
            ROM_LOAD("pr01_2c.128", 0x04000, 0x4000, 0xfd9acc22);
            ROM_LOAD("pr02_3c.128", 0x08000, 0x4000, 0xb3755d41);
            ROM_LOAD("pr03_4c.64", 0x0c000, 0x2000, 0x77273096);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("tapsnda7.bin", 0x0000, 0x1000, 0x0e8bb9d5);
            ROM_LOAD("tapsnda8.bin", 0x1000, 0x1000, 0x0cf0e29b);
            ROM_LOAD("tapsnda9.bin", 0x2000, 0x1000, 0x31eb6dc6);
            ROM_LOAD("tapsda10.bin", 0x3000, 0x1000, 0x01a9be6a);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("tapbg1.bin", 0x00000, 0x4000, 0x2a30238c);
            ROM_LOAD("tapbg0.bin", 0x04000, 0x4000, 0x394ab576);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("fg1_a7.128", 0x00000, 0x4000, 0xbac70b69);
            ROM_LOAD("fg0_a8.128", 0x04000, 0x4000, 0xc300925d);
            ROM_LOAD("fg3_a5.128", 0x08000, 0x4000, 0xecff6c23);
            ROM_LOAD("fg2_a6.128", 0x0c000, 0x4000, 0xa4f2d1be);
            ROM_LOAD("fg5_a3.128", 0x10000, 0x4000, 0x16ce38cb);
            ROM_LOAD("fg4_a4.128", 0x14000, 0x4000, 0x082a4059);
            ROM_LOAD("fg7_a1.128", 0x18000, 0x4000, 0x3b476abe);
            ROM_LOAD("fg6_a2.128", 0x1c000, 0x4000, 0x6717264c);
            ROM_END();
        }
    };

    static RomLoadPtr rom_sutapper = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("5791", 0x0000, 0x4000, 0x87119cc4);
            ROM_LOAD("5792", 0x4000, 0x4000, 0x4c23ad89);
            ROM_LOAD("5793", 0x8000, 0x4000, 0xfecbf683);
            ROM_LOAD("5794", 0xc000, 0x2000, 0x5bdc1916);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("5788", 0x00000, 0x1000, 0x5c1d0982);
            ROM_LOAD("5787", 0x01000, 0x1000, 0x09e74ed8);
            ROM_LOAD("5786", 0x02000, 0x1000, 0xc3e98284);
            ROM_LOAD("5785", 0x03000, 0x1000, 0xced2fd47);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("5790", 0x00000, 0x4000, 0xac1558c1);
            ROM_LOAD("5789", 0x04000, 0x4000, 0xfa66cab5);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("5795", 0x00000, 0x4000, 0x5d987c92);
            ROM_LOAD("5796", 0x04000, 0x4000, 0xde5700b4);
            ROM_LOAD("5797", 0x08000, 0x4000, 0xf10a1d05);
            ROM_LOAD("5798", 0x0c000, 0x4000, 0x614990cd);
            ROM_LOAD("5799", 0x10000, 0x4000, 0x02c69432);
            ROM_LOAD("5800", 0x14000, 0x4000, 0xebf1f948);
            ROM_LOAD("5801", 0x18000, 0x4000, 0xd70defa7);
            ROM_LOAD("5802", 0x1c000, 0x4000, 0xd4f114b9);
            ROM_END();
        }
    };

    static RomLoadPtr rom_rbtapper = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("rbtpg0.bin", 0x00000, 0x4000, 0x20b9adf4);
            ROM_LOAD("rbtpg1.bin", 0x04000, 0x4000, 0x87e616c2);
            ROM_LOAD("rbtpg2.bin", 0x08000, 0x4000, 0x0b332c97);
            ROM_LOAD("rbtpg3.bin", 0x0c000, 0x2000, 0x698c06f2);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("5788", 0x00000, 0x1000, 0x5c1d0982);
            ROM_LOAD("5787", 0x01000, 0x1000, 0x09e74ed8);
            ROM_LOAD("5786", 0x02000, 0x1000, 0xc3e98284);
            ROM_LOAD("5785", 0x03000, 0x1000, 0xced2fd47);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("rbtbg1.bin", 0x00000, 0x4000, 0x44dfa483);
            ROM_LOAD("rbtbg0.bin", 0x04000, 0x4000, 0x510b13de);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("rbtfg1.bin", 0x00000, 0x4000, 0x1c0b8791);
            ROM_LOAD("rbtfg0.bin", 0x04000, 0x4000, 0xe99f6018);
            ROM_LOAD("rbtfg3.bin", 0x08000, 0x4000, 0x3e725e77);
            ROM_LOAD("rbtfg2.bin", 0x0c000, 0x4000, 0x4ee8b624);
            ROM_LOAD("rbtfg5.bin", 0x10000, 0x4000, 0x9eeca46e);
            ROM_LOAD("rbtfg4.bin", 0x14000, 0x4000, 0x8c79e7d7);
            ROM_LOAD("rbtfg7.bin", 0x18000, 0x4000, 0x8dbf0c36);
            ROM_LOAD("rbtfg6.bin", 0x1c000, 0x4000, 0x441201a0);
            ROM_END();
        }
    };

    static RomLoadPtr rom_timber = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("timpg0.bin", 0x00000, 0x4000, 0x377032ab);
            ROM_LOAD("timpg1.bin", 0x04000, 0x4000, 0xfd772836);
            ROM_LOAD("timpg2.bin", 0x08000, 0x4000, 0x632989f9);
            ROM_LOAD("timpg3.bin", 0x0c000, 0x2000, 0xdae8a0dc);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("tima7.bin", 0x00000, 0x1000, 0xc615dc3e);
            ROM_LOAD("tima8.bin", 0x01000, 0x1000, 0x83841c87);
            ROM_LOAD("tima9.bin", 0x02000, 0x1000, 0x22bcdcd3);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("timbg1.bin", 0x00000, 0x4000, 0xb1cb2651);
            ROM_LOAD("timbg0.bin", 0x04000, 0x4000, 0x2ae352c4);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("timfg1.bin", 0x00000, 0x4000, 0x81de4a73);
            ROM_LOAD("timfg0.bin", 0x04000, 0x4000, 0x7f3a4f59);
            ROM_LOAD("timfg3.bin", 0x08000, 0x4000, 0x37c03272);
            ROM_LOAD("timfg2.bin", 0x0c000, 0x4000, 0xe2c2885c);
            ROM_LOAD("timfg5.bin", 0x10000, 0x4000, 0xeb636216);
            ROM_LOAD("timfg4.bin", 0x14000, 0x4000, 0xb7105eb7);
            ROM_LOAD("timfg7.bin", 0x18000, 0x4000, 0xd9c27475);
            ROM_LOAD("timfg6.bin", 0x1c000, 0x4000, 0x244778e8);
            ROM_END();
        }
    };

    static RomLoadPtr rom_dotron = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("loc-pg0.1c", 0x0000, 0x4000, 0xba0da15f);
            ROM_LOAD("loc-pg1.2c", 0x4000, 0x4000, 0xdc300191);
            ROM_LOAD("loc-pg2.3c", 0x8000, 0x4000, 0xab0b3800);
            ROM_LOAD("loc-pg1.4c", 0xc000, 0x2000, 0xf98c9f8e);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("sound0.a7", 0x00000, 0x1000, 0x6d39bf19);
            ROM_LOAD("sound1.a8", 0x01000, 0x1000, 0xac872e1d);
            ROM_LOAD("sound2.a9", 0x02000, 0x1000, 0xe8ef6519);
            ROM_LOAD("sound3.a10", 0x03000, 0x1000, 0x6b5aeb02);

            ROM_REGION(0x10000, REGION_CPU3);/* 64k for the audio CPU */
            ROM_LOAD("pre.u3", 0x0d000, 0x1000, 0xc3d0f762);
            ROM_LOAD("pre.u4", 0x0e000, 0x1000, 0x7ca79b43);
            ROM_LOAD("pre.u5", 0x0f000, 0x1000, 0x24e9618e);

            ROM_REGION(0x04000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("loc-bg2.6f", 0x00000, 0x2000, 0x40167124);
            ROM_LOAD("loc-bg1.5f", 0x02000, 0x2000, 0xbb2d7a5d);

            ROM_REGION(0x10000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("loc-g.cp4", 0x00000, 0x2000, 0x57a2b1ff);
            ROM_LOAD("loc-h.cp3", 0x02000, 0x2000, 0x3bb4d475);
            ROM_LOAD("loc-e.cp6", 0x04000, 0x2000, 0xce957f1a);
            ROM_LOAD("loc-f.cp5", 0x06000, 0x2000, 0xd26053ce);
            ROM_LOAD("loc-c.cp8", 0x08000, 0x2000, 0xef45d146);
            ROM_LOAD("loc-d.cp7", 0x0a000, 0x2000, 0x5e8a3ef3);
            ROM_LOAD("loc-a.cp0", 0x0c000, 0x2000, 0xb35f5374);
            ROM_LOAD("loc-b.cp9", 0x0e000, 0x2000, 0x565a5c48);
            ROM_END();
        }
    };

    static RomLoadPtr rom_dotrone = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("loc-cpu1", 0x00000, 0x4000, 0xeee31b8c);
            ROM_LOAD("loc-cpu2", 0x04000, 0x4000, 0x75ba6ad3);
            ROM_LOAD("loc-cpu3", 0x08000, 0x4000, 0x94bb1a0e);
            ROM_LOAD("loc-cpu4", 0x0c000, 0x2000, 0xc137383c);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("loc-a", 0x00000, 0x1000, 0x2de6a8a8);
            ROM_LOAD("loc-b", 0x01000, 0x1000, 0x4097663e);
            ROM_LOAD("loc-c", 0x02000, 0x1000, 0xf576b9e7);
            ROM_LOAD("loc-d", 0x03000, 0x1000, 0x74b0059e);

            ROM_REGION(0x10000, REGION_CPU3);/* 64k for the audio CPU */
            ROM_LOAD("pre.u3", 0x0d000, 0x1000, 0xc3d0f762);
            ROM_LOAD("pre.u4", 0x0e000, 0x1000, 0x7ca79b43);
            ROM_LOAD("pre.u5", 0x0f000, 0x1000, 0x24e9618e);

            ROM_REGION(0x04000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("loc-bg2.6f", 0x00000, 0x2000, 0x40167124);
            ROM_LOAD("loc-bg1.5f", 0x02000, 0x2000, 0xbb2d7a5d);

            ROM_REGION(0x10000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("loc-g.cp4", 0x00000, 0x2000, 0x57a2b1ff);
            ROM_LOAD("loc-h.cp3", 0x02000, 0x2000, 0x3bb4d475);
            ROM_LOAD("loc-e.cp6", 0x04000, 0x2000, 0xce957f1a);
            ROM_LOAD("loc-f.cp5", 0x06000, 0x2000, 0xd26053ce);
            ROM_LOAD("loc-c.cp8", 0x08000, 0x2000, 0xef45d146);
            ROM_LOAD("loc-d.cp7", 0x0a000, 0x2000, 0x5e8a3ef3);
            ROM_LOAD("loc-a.cp0", 0x0c000, 0x2000, 0xb35f5374);
            ROM_LOAD("loc-b.cp9", 0x0e000, 0x2000, 0x565a5c48);
            ROM_END();
        }
    };

    static RomLoadPtr rom_destderb = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("dd_pro", 0x00000, 0x4000, 0x8781b367);
            ROM_LOAD("dd_pro1", 0x04000, 0x4000, 0x4c713bfe);
            ROM_LOAD("dd_pro2", 0x08000, 0x4000, 0xc2cbd2a4);

            ROM_REGION(0x10000, REGION_CPU2);
            /* 64k for the Turbo Cheap Squeak */
            ROM_LOAD("tcs_u5.bin", 0x0c000, 0x2000, 0xeca33b2c);
            ROM_LOAD("tcs_u4.bin", 0x0e000, 0x2000, 0x3490289a);

            ROM_REGION(0x04000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("dd_bg0.6f", 0x00000, 0x2000, 0xcf80be19);
            ROM_LOAD("dd_bg1.5f", 0x02000, 0x2000, 0x4e173e52);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("dd_fg-0.a4", 0x00000, 0x4000, 0xe57a4de6);
            ROM_LOAD("dd_fg-4.a3", 0x04000, 0x4000, 0x55aa667f);
            ROM_LOAD("dd_fg-1.a6", 0x08000, 0x4000, 0x70259651);
            ROM_LOAD("dd_fg-5.a5", 0x0c000, 0x4000, 0x5fe99007);
            ROM_LOAD("dd_fg-2.a8", 0x10000, 0x4000, 0x6cab7b95);
            ROM_LOAD("dd_fg-6.a7", 0x14000, 0x4000, 0xabfb9a8b);
            ROM_LOAD("dd_fg-3.a10", 0x18000, 0x4000, 0x801d9b86);
            ROM_LOAD("dd_fg-7.a9", 0x1c000, 0x4000, 0x0ec3f60a);
            ROM_END();
        }
    };

    static RomLoadPtr rom_destderm = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("pro0.3b", 0x00000, 0x8000, 0x2e24527b);
            ROM_LOAD("pro1.5b", 0x08000, 0x8000, 0x034c00fc);

            ROM_REGION(0x10000, REGION_CPU2);
            /* 64k for the Turbo Cheap Squeak */
            ROM_LOAD("tcs_u5.bin", 0x0c000, 0x2000, 0xeca33b2c);
            ROM_LOAD("tcs_u4.bin", 0x0e000, 0x2000, 0x3490289a);

            ROM_REGION(0x04000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("bg0.15a", 0x00000, 0x2000, 0xa35d13b8);
            ROM_LOAD("bg1.14b", 0x02000, 0x2000, 0x22ca93f3);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("dd_fg-0.a4", 0x00000, 0x4000, 0xe57a4de6);
            ROM_LOAD("dd_fg-4.a3", 0x04000, 0x4000, 0x55aa667f);
            ROM_LOAD("dd_fg-1.a6", 0x08000, 0x4000, 0x70259651);
            ROM_LOAD("dd_fg-5.a5", 0x0c000, 0x4000, 0x5fe99007);
            ROM_LOAD("dd_fg-2.a8", 0x10000, 0x4000, 0x6cab7b95);
            ROM_LOAD("dd_fg-6.a7", 0x14000, 0x4000, 0xabfb9a8b);
            ROM_LOAD("dd_fg-3.a10", 0x18000, 0x4000, 0x801d9b86);
            ROM_LOAD("dd_fg-7.a9", 0x1c000, 0x4000, 0x0ec3f60a);
            ROM_END();
        }
    };

    static RomLoadPtr rom_sarge = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("cpu_3b.bin", 0x0000, 0x8000, 0xda31a58f);
            ROM_LOAD("cpu_5b.bin", 0x8000, 0x8000, 0x6800e746);

            ROM_REGION(0x10000, REGION_CPU2);
            /* 64k for the Turbo Cheap Squeak */
            ROM_LOAD("tcs_u5.bin", 0xc000, 0x2000, 0xa894ef8a);
            ROM_LOAD("tcs_u4.bin", 0xe000, 0x2000, 0x6ca6faf3);

            ROM_REGION(0x04000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("til_15a.bin", 0x00000, 0x2000, 0x685001b8);
            ROM_LOAD("til_14b.bin", 0x02000, 0x2000, 0x8449eb45);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("spr_8e.bin", 0x00000, 0x8000, 0x93fac29d);
            ROM_LOAD("spr_6e.bin", 0x08000, 0x8000, 0x7cc6fb28);
            ROM_LOAD("spr_5e.bin", 0x10000, 0x8000, 0xc832375c);
            ROM_LOAD("spr_4e.bin", 0x18000, 0x8000, 0xc382267d);
            ROM_END();
        }
    };

    static RomLoadPtr rom_rampage = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("pro0rev3.3b", 0x00000, 0x08000, 0x2f7ca03c);
            ROM_LOAD("pro1rev3.5b", 0x08000, 0x08000, 0xd89bd9a4);

            ROM_REGION(0x20000, REGION_CPU2);
            /* 128k for the Sounds Good board */
            ROM_LOAD_EVEN("ramp_u7.snd", 0x00000, 0x8000, 0xcffd7fa5);/* these are Revision 2 sound ROMs */
            ROM_LOAD_ODD("ramp_u17.snd", 0x00000, 0x8000, 0xe92c596b);
            ROM_LOAD_EVEN("ramp_u8.snd", 0x10000, 0x8000, 0x11f787e4);
            ROM_LOAD_ODD("ramp_u18.snd", 0x10000, 0x8000, 0x6b8bf5e1);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("bg-0", 0x00000, 0x04000, 0xc0d8b7a5);
            ROM_LOAD("bg-1", 0x04000, 0x04000, 0x2f6e3aa1);

            ROM_REGION(0x40000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("fg-0", 0x00000, 0x10000, 0x0974be5d);
            ROM_LOAD("fg-1", 0x10000, 0x10000, 0x8728532b);
            ROM_LOAD("fg-2", 0x20000, 0x10000, 0x9489f714);
            ROM_LOAD("fg-3", 0x30000, 0x10000, 0x81e1de40);
            ROM_END();
        }
    };

    static RomLoadPtr rom_rampage2 = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("pro0rev2.3b", 0x0000, 0x8000, 0x3f1d0293);
            ROM_LOAD("pro1rev2.5b", 0x8000, 0x8000, 0x58523d75);

            ROM_REGION(0x20000, REGION_CPU2);
            /* 128k for the Sounds Good board */
            ROM_LOAD_EVEN("ramp_u7.snd", 0x00000, 0x8000, 0xcffd7fa5);
            /* these are Revision 2 sound ROMs */
            ROM_LOAD_ODD("ramp_u17.snd", 0x00000, 0x8000, 0xe92c596b);
            ROM_LOAD_EVEN("ramp_u8.snd", 0x10000, 0x8000, 0x11f787e4);
            ROM_LOAD_ODD("ramp_u18.snd", 0x10000, 0x8000, 0x6b8bf5e1);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("bg-0", 0x00000, 0x04000, 0xc0d8b7a5);
            ROM_LOAD("bg-1", 0x04000, 0x04000, 0x2f6e3aa1);

            ROM_REGION(0x40000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("fg-0", 0x00000, 0x10000, 0x0974be5d);
            ROM_LOAD("fg-1", 0x10000, 0x10000, 0x8728532b);
            ROM_LOAD("fg-2", 0x20000, 0x10000, 0x9489f714);
            ROM_LOAD("fg-3", 0x30000, 0x10000, 0x81e1de40);
            ROM_END();
        }
    };

    static RomLoadPtr rom_powerdrv = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("pdrv3b.bin", 0x0000, 0x8000, 0xd870b704);
            ROM_LOAD("pdrv5b.bin", 0x8000, 0x8000, 0xfa0544ad);

            ROM_REGION(0x20000, REGION_CPU2);
            /* 128k for the Sounds Good board */
            ROM_LOAD_EVEN("pdsndu7.bin", 0x00000, 0x8000, 0x78713e78);
            ROM_LOAD_ODD("pdsndu17.bin", 0x00000, 0x8000, 0xc41de6e4);
            ROM_LOAD_EVEN("pdsndu8.bin", 0x10000, 0x8000, 0x15714036);
            ROM_LOAD_ODD("pdsndu18.bin", 0x10000, 0x8000, 0xcae14c70);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("pdrv15a.bin", 0x00000, 0x04000, 0xb858b5a8);
            ROM_LOAD("pdrv14b.bin", 0x04000, 0x04000, 0x12ee7fc2);

            ROM_REGION(0x40000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("pdrv8e.bin", 0x00000, 0x10000, 0xdd3a2adc);
            ROM_LOAD("pdrv6e.bin", 0x10000, 0x10000, 0x1a1f7f81);
            ROM_LOAD("pdrv5e.bin", 0x20000, 0x10000, 0x4cb4780e);
            ROM_LOAD("pdrv4e.bin", 0x30000, 0x10000, 0xde400335);
            ROM_END();
        }
    };

    static RomLoadPtr rom_maxrpm = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x12000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("pro.0", 0x00000, 0x8000, 0x3f9ec35f);
            ROM_LOAD("pro.1", 0x08000, 0x6000, 0xf628bb30);
            ROM_CONTINUE(0x10000, 0x2000);/* unused? but there seems to be stuff in here */
 /* loading it at e000 causes rogue sprites to appear on screen */

            ROM_REGION(0x10000, REGION_CPU2);
            /* 64k for the Turbo Cheap Squeak */
            ROM_LOAD("turbskwk.u5", 0x8000, 0x4000, 0x55c3b759);
            ROM_LOAD("turbskwk.u4", 0xc000, 0x4000, 0x31a2da2e);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("bg-0", 0x00000, 0x4000, 0xe3fb693a);
            ROM_LOAD("bg-1", 0x04000, 0x4000, 0x50d1db6c);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("fg-0", 0x00000, 0x8000, 0x1d1435c1);
            ROM_LOAD("fg-1", 0x08000, 0x8000, 0xe54b7f2a);
            ROM_LOAD("fg-2", 0x10000, 0x8000, 0x38be8505);
            ROM_LOAD("fg-3", 0x18000, 0x8000, 0x9ae3eb52);
            ROM_END();
        }
    };

    static RomLoadPtr rom_spyhunt = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("cpu_pg0.6d", 0x0000, 0x2000, 0x1721b88f);
            ROM_LOAD("cpu_pg1.7d", 0x2000, 0x2000, 0x909d044f);
            ROM_LOAD("cpu_pg2.8d", 0x4000, 0x2000, 0xafeeb8bd);
            ROM_LOAD("cpu_pg3.9d", 0x6000, 0x2000, 0x5e744381);
            ROM_LOAD("cpu_pg4.10d", 0x8000, 0x2000, 0xa3033c15);
            ROM_LOAD("cpu_pg5.11d", 0xa000, 0x4000, 0x88aa1e99);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("snd_0sd.a8", 0x0000, 0x1000, 0xc95cf31e);
            ROM_LOAD("snd_1sd.a7", 0x1000, 0x1000, 0x12aaa48e);

            ROM_REGION(0x8000, REGION_CPU3);
            /* 32k for the Chip Squeak Deluxe */
            ROM_LOAD_EVEN("csd_u7a.u7", 0x00000, 0x2000, 0x6e689fe7);
            ROM_LOAD_ODD("csd_u17b.u17", 0x00000, 0x2000, 0x0d9ddce6);
            ROM_LOAD_EVEN("csd_u8c.u8", 0x04000, 0x2000, 0x35563cd0);
            ROM_LOAD_ODD("csd_u18d.u18", 0x04000, 0x2000, 0x63d3f5b1);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("cpu_bg0.3a", 0x00000, 0x2000, 0xdea34fed);
            ROM_LOAD("cpu_bg1.4a", 0x02000, 0x2000, 0x8f64525f);
            ROM_LOAD("cpu_bg2.5a", 0x04000, 0x2000, 0xba0fd626);
            ROM_LOAD("cpu_bg3.6a", 0x06000, 0x2000, 0x7b482d61);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("vid_0fg.a8", 0x00000, 0x4000, 0x292c5466);
            ROM_LOAD("vid_1fg.a7", 0x04000, 0x4000, 0x9fe286ec);
            ROM_LOAD("vid_2fg.a6", 0x08000, 0x4000, 0x62c8bfa5);
            ROM_LOAD("vid_3fg.a5", 0x0c000, 0x4000, 0xb894934d);
            ROM_LOAD("vid_4fg.a4", 0x10000, 0x4000, 0x7ca4941b);
            ROM_LOAD("vid_5fg.a3", 0x14000, 0x4000, 0x2d9fbcec);
            ROM_LOAD("vid_6fg.a2", 0x18000, 0x4000, 0x8cb8a066);
            ROM_LOAD("vid_7fg.a1", 0x1c000, 0x4000, 0x940fe17e);

            ROM_REGION(0x01000, REGION_GFX3 | REGIONFLAG_DISPOSE);
            ROM_LOAD("cpu_alph.10g", 0x00000, 0x1000, 0x936dc87f);
            ROM_END();
        }
    };

    static RomLoadPtr rom_turbotag = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("ttprog0.bin", 0x0000, 0x2000, 0x6110fd80);
            ROM_LOAD("ttprog1.bin", 0x2000, 0x2000, BADCRC(0xb0505e18));
            ROM_LOAD("ttprog2.bin", 0x4000, 0x2000, 0xc4141237);
            ROM_LOAD("ttprog3.bin", 0x6000, 0x2000, 0xaf294c6e);
            ROM_LOAD("ttprog4.bin", 0x8000, 0x2000, 0x8c5bc1a4);
            ROM_LOAD("ttprog5.bin", 0xa000, 0x2000, 0x11e62fe4);
            ROM_RELOAD(0xc000, 0x2000);

            ROM_REGION(0x8000, REGION_CPU2);
            /* 32k for the Chip Squeak Deluxe */
            ROM_LOAD_EVEN("ttu7.bin", 0x00000, 0x2000, 0x8ebb3302);
            ROM_LOAD_ODD("ttu17.bin", 0x00000, 0x2000, 0x605d6c74);
            ROM_LOAD_EVEN("ttu8.bin", 0x04000, 0x2000, 0x6bfcb22a);
            ROM_LOAD_ODD("ttu18.bin", 0x04000, 0x2000, 0xbb25852c);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("ttbg0.bin", 0x00000, 0x2000, 0x1cd2023f);
            ROM_LOAD("ttbg1.bin", 0x02000, 0x2000, 0x784e84cd);
            ROM_LOAD("ttbg2.bin", 0x04000, 0x2000, 0xda9d47d2);
            ROM_LOAD("ttbg3.bin", 0x06000, 0x2000, 0x367e06a5);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("ttfg0.bin", 0x00000, 0x4000, 0xed69e1a8);
            ROM_LOAD("ttfg1.bin", 0x04000, 0x4000, 0x9d7e6ebc);
            ROM_LOAD("ttfg2.bin", 0x08000, 0x4000, 0x037ec6fc);
            ROM_LOAD("ttfg3.bin", 0x0c000, 0x4000, 0x74e21c1c);
            ROM_LOAD("ttfg4.bin", 0x10000, 0x4000, 0x6fdb0c13);
            ROM_LOAD("ttfg5.bin", 0x14000, 0x4000, 0x8b718879);
            ROM_LOAD("ttfg6.bin", 0x18000, 0x4000, 0x4094e996);
            ROM_LOAD("ttfg7.bin", 0x1c000, 0x4000, 0x212019dc);

            ROM_REGION(0x01000, REGION_GFX3 | REGIONFLAG_DISPOSE);
            ROM_LOAD("ttan.bin", 0x00000, 0x1000, 0xaa0b1471);
            ROM_END();
        }
    };

    static RomLoadPtr rom_crater = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x10000, REGION_CPU1);/* 64k for code */
            ROM_LOAD("crcpu.6d", 0x0000, 0x2000, 0xad31f127);
            ROM_LOAD("crcpu.7d", 0x2000, 0x2000, 0x3743c78f);
            ROM_LOAD("crcpu.8d", 0x4000, 0x2000, 0xc95f9088);
            ROM_LOAD("crcpu.9d", 0x6000, 0x2000, 0xa03c4b11);
            ROM_LOAD("crcpu.10d", 0x8000, 0x2000, 0x44ae4cbd);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the audio CPU */
            ROM_LOAD("crsnd4.a7", 0x0000, 0x1000, 0xfd666cb5);
            ROM_LOAD("crsnd1.a8", 0x1000, 0x1000, 0x90bf2c4c);
            ROM_LOAD("crsnd2.a9", 0x2000, 0x1000, 0x3b8deef1);
            ROM_LOAD("crsnd3.a10", 0x3000, 0x1000, 0x05803453);

            ROM_REGION(0x08000, REGION_GFX1 | REGIONFLAG_DISPOSE);
            ROM_LOAD("crcpu.3a", 0x00000, 0x2000, 0x9d73504a);
            ROM_LOAD("crcpu.4a", 0x02000, 0x2000, 0x42a47dff);
            ROM_LOAD("crcpu.5a", 0x04000, 0x2000, 0x2fe4a6e1);
            ROM_LOAD("crcpu.6a", 0x06000, 0x2000, 0xd0659042);

            ROM_REGION(0x20000, REGION_GFX2 | REGIONFLAG_DISPOSE);
            ROM_LOAD("crvid.a3", 0x00000, 0x4000, 0x2c2f5b29);
            ROM_LOAD("crvid.a4", 0x04000, 0x4000, 0x579a8e36);
            ROM_LOAD("crvid.a5", 0x08000, 0x4000, 0x9bdec312);
            ROM_LOAD("crvid.a6", 0x0c000, 0x4000, 0x5bf954e0);
            ROM_LOAD("crvid.a7", 0x10000, 0x4000, 0x9fa307d5);
            ROM_LOAD("crvid.a8", 0x14000, 0x4000, 0x4b913498);
            ROM_LOAD("crvid.a9", 0x18000, 0x4000, 0x811f152d);
            ROM_LOAD("crvid.a10", 0x1c000, 0x4000, 0x7a22d6bc);

            ROM_REGION(0x01000, REGION_GFX3 | REGIONFLAG_DISPOSE);
            ROM_LOAD("crcpu.10g", 0x00000, 0x1000, 0x6fe53c8d);
            ROM_END();
        }
    };

    /**
     * ***********************************
     *
     * Driver initialization
     *
     ************************************
     */
    static void mcrmono_decode() {
        int i;

        /* Rampage tile graphics are inverted */
        for (i = 0; i < memory_region_length(REGION_GFX1); i++) {
            memory_region(REGION_GFX1).write(i, memory_region(REGION_GFX1).read(i) ^ 0xff);
        }
    }

    static void spyhunt_decode() {
        UBytePtr RAM = memory_region(REGION_CPU1);

        /* some versions of rom 11d have the top and bottom 8k swapped; to enable us to work with either
		   a correct set or a swapped set (both of which pass the checksum!), we swap them here */
        if (RAM.read(0xa000) != 0x0c) {
            int i;

            for (i = 0; i < 0x2000; i++) {
                int temp = RAM.read(0xa000 + i);
                RAM.write(0xa000 + i, RAM.read(0xc000 + i));
                RAM.write(0xc000 + i, temp);
            }
        }
    }

    public static InitDriverPtr init_tapper = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_SSIO);
        }
    };

    public static InitDriverPtr init_timber = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_SSIO);

            /* Timber uses a modified SSIO with RAM in place of one of the ROMs */
            install_mem_read_handler(1, 0x3000, 0x3fff, MRA_RAM);
            install_mem_write_handler(1, 0x3000, 0x3fff, MWA_RAM);
        }
    };

    public static InitDriverPtr init_dotron = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_SSIO | MCR_SQUAWK_N_TALK);
            install_port_read_handler(0, 0x02, 0x02, dotron_port_2_r);
            install_port_write_handler(0, 0x04, 0x04, dotron_port_4_w);
        }
    };

    public static InitDriverPtr init_destderb = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_TURBO_CHIP_SQUEAK);
            install_port_write_handler(0, 0x04, 0x04, turbocs_data_w);
        }
    };

    public static InitDriverPtr init_destderm = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_TURBO_CHIP_SQUEAK);
            install_port_write_handler(0, 0x06, 0x06, turbocs_data_w);
            mcrmono_decode();
        }
    };

    public static InitDriverPtr init_sarge = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_TURBO_CHIP_SQUEAK);
            install_port_read_handler(0, 0x01, 0x01, sarge_port_1_r);
            install_port_read_handler(0, 0x02, 0x02, sarge_port_2_r);
            install_port_write_handler(0, 0x06, 0x06, turbocs_data_w);
            mcrmono_decode();
        }
    };

    public static InitDriverPtr init_rampage = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_SOUNDS_GOOD);
            install_port_write_handler(0, 0x06, 0x06, soundsgood_data_w);
            mcrmono_decode();
        }
    };

    public static InitDriverPtr init_powerdrv = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_SOUNDS_GOOD);
            install_port_read_handler(0, 0x02, 0x02, powerdrv_port_2_r);
            install_port_write_handler(0, 0x06, 0x06, soundsgood_data_w);
            install_port_write_handler(0, 0x07, 0x07, powerdrv_port_7_w);
            mcrmono_decode();
        }
    };

    public static InitDriverPtr init_maxrpm = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_TURBO_CHIP_SQUEAK);
            install_port_read_handler(0, 0x01, 0x01, maxrpm_port_1_r);
            install_port_read_handler(0, 0x02, 0x02, maxrpm_port_2_r);
            install_port_write_handler(0, 0x05, 0x05, maxrpm_mux_w);
            install_port_write_handler(0, 0x06, 0x06, turbocs_data_w);
            mcrmono_decode();
        }
    };

    public static InitDriverPtr init_spyhunt = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_SSIO | MCR_CHIP_SQUEAK_DELUXE);
            install_port_read_handler(0, 0x02, 0x02, spyhunt_port_2_r);
            install_port_write_handler(0, 0x04, 0x04, spyhunt_port_4_w);

            u8_spyhunt_sprite_color_mask = 0x00;
            spyhunt_scroll_offset = -16;
            u8_spyhunt_draw_lamps = 1;

            spyhunt_decode();
        }
    };

    public static InitDriverPtr init_turbotag = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_CHIP_SQUEAK_DELUXE);
            install_port_read_handler(0, 0x02, 0x02, spyhunt_port_2_r);
            install_port_write_handler(0, 0x04, 0x04, spyhunt_port_4_w);

            u8_spyhunt_sprite_color_mask = 0x00;
            spyhunt_scroll_offset = -88;
            u8_spyhunt_draw_lamps = 0;

            /* kludge for bad ROM read */
            install_mem_read_handler(0, 0x0b53, 0x0b53, turbotag_kludge_r);
        }
    };

    public static InitDriverPtr init_crater = new InitDriverPtr() {
        public void handler() {
            MCR_CONFIGURE_SOUND(MCR_SSIO);

            u8_spyhunt_sprite_color_mask = 0x03;
            spyhunt_scroll_offset = -96;
            u8_spyhunt_draw_lamps = 0;
        }
    };

    /**
     * ***********************************
     *
     * Game drivers
     *
     ************************************
     */
    public static GameDriver driver_tapper = new GameDriver("1983", "tapper", "mcr3.java", rom_tapper, null, machine_driver_mcr3, input_ports_tapper, init_tapper, ROT0, "Bally Midway", "Tapper (Budweiser)");
    public static GameDriver driver_tappera = new GameDriver("1983", "tappera", "mcr3.java", rom_tappera, driver_tapper, machine_driver_mcr3, input_ports_tapper, init_tapper, ROT0, "Bally Midway", "Tapper (alternate)");
    public static GameDriver driver_sutapper = new GameDriver("1983", "sutapper", "mcr3.java", rom_sutapper, driver_tapper, machine_driver_mcr3, input_ports_tapper, init_tapper, ROT0, "Bally Midway", "Tapper (Suntory)");
    public static GameDriver driver_rbtapper = new GameDriver("1984", "rbtapper", "mcr3.java", rom_rbtapper, driver_tapper, machine_driver_mcr3, input_ports_tapper, init_tapper, ROT0, "Bally Midway", "Tapper (Root Beer)");
    public static GameDriver driver_timber = new GameDriver("1984", "timber", "mcr3.java", rom_timber, null, machine_driver_mcr3, input_ports_timber, init_timber, ROT0, "Bally Midway", "Timber");
    public static GameDriver driver_dotron = new GameDriver("1983", "dotron", "mcr3.java", rom_dotron, null, machine_driver_dotron, input_ports_dotron, init_dotron, ORIENTATION_FLIP_X, "Bally Midway", "Discs of Tron (Upright)");
    public static GameDriver driver_dotrone = new GameDriver("1983", "dotrone", "mcr3.java", rom_dotrone, driver_dotron, machine_driver_dotron, input_ports_dotron, init_dotron, ORIENTATION_FLIP_X, "Bally Midway", "Discs of Tron (Environmental)");
    public static GameDriver driver_destderb = new GameDriver("1984", "destderb", "mcr3.java", rom_destderb, null, machine_driver_destderb, input_ports_destderb, init_destderb, ROT0, "Bally Midway", "Demolition Derby");
    public static GameDriver driver_destderm = new GameDriver("1984", "destderm", "mcr3.java", rom_destderm, driver_destderb, machine_driver_sarge, input_ports_destderb, init_destderm, ROT0, "Bally Midway", "Demolition Derby (2-Player Mono Board Version)");
    public static GameDriver driver_sarge = new GameDriver("1985", "sarge", "mcr3.java", rom_sarge, null, machine_driver_sarge, input_ports_sarge, init_sarge, ROT0, "Bally Midway", "Sarge");
    public static GameDriver driver_rampage = new GameDriver("1986", "rampage", "mcr3.java", rom_rampage, null, machine_driver_rampage, input_ports_rampage, init_rampage, ROT0, "Bally Midway", "Rampage (revision 3)");
    public static GameDriver driver_rampage2 = new GameDriver("1986", "rampage2", "mcr3.java", rom_rampage2, driver_rampage, machine_driver_rampage, input_ports_rampage, init_rampage, ROT0, "Bally Midway", "Rampage (revision 2)");
    public static GameDriver driver_powerdrv = new GameDriver("1986", "powerdrv", "mcr3.java", rom_powerdrv, null, machine_driver_powerdrv, input_ports_powerdrv, init_powerdrv, ROT0, "Bally Midway", "Power Drive");
    public static GameDriver driver_maxrpm = new GameDriver("1986", "maxrpm", "mcr3.java", rom_maxrpm, null, machine_driver_sarge, input_ports_maxrpm, init_maxrpm, ROT0, "Bally Midway", "Max RPM");
    public static GameDriver driver_spyhunt = new GameDriver("1983", "spyhunt", "mcr3.java", rom_spyhunt, null, machine_driver_spyhunt, input_ports_spyhunt, init_spyhunt, ROT90, "Bally Midway", "Spy Hunter");
    public static GameDriver driver_turbotag = new GameDriver("1985", "turbotag", "mcr3.java", rom_turbotag, null, machine_driver_turbotag, input_ports_turbotag, init_turbotag, ROT90, "Bally Midway", "Turbo Tag (Prototype)");
    public static GameDriver driver_crater = new GameDriver("1984", "crater", "mcr3.java", rom_crater, null, machine_driver_crater, input_ports_crater, init_crater, ORIENTATION_FLIP_X, "Bally Midway", "Crater Raider");
}