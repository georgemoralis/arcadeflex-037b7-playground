/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package gr.codebb.arcadeflex.WIP.v037b7.drivers;

import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import gr.codebb.arcadeflex.WIP.v037b7.cpu.konami.konami.konami_cpu_setlines_callbackPtr;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.konami.konamiH.KONAMI_INT_IRQ;
import static arcadeflex.v037b7.mame.commonH.REGION_CPU1;
import static arcadeflex.v037b7.mame.commonH.REGION_CPU2;
import static arcadeflex.v037b7.mame.commonH.REGION_GFX1;
import static arcadeflex.v037b7.mame.commonH.REGION_GFX2;
import static arcadeflex.v037b7.mame.commonH.REGION_PROMS;
import static arcadeflex.v037b7.mame.commonH.REGION_SOUND1;
import static arcadeflex.v037b7.mame.commonH.ROM_CONTINUE;
import static arcadeflex.v037b7.mame.commonH.ROM_END;
import static arcadeflex.v037b7.mame.commonH.ROM_LOAD;
import static arcadeflex.v037b7.mame.commonH.ROM_REGION;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_get_pc;
import static arcadeflex.v037b7.mame.cpuintrf.ignore_interrupt;
import static arcadeflex.v037b7.mame.cpuintrf.watchdog_reset_w;
import static arcadeflex.v037b7.mame.cpuintrfH.ASSERT_LINE;
import static arcadeflex.v037b7.mame.cpuintrfH.CLEAR_LINE;
import arcadeflex.v037b7.mame.drawgfxH.rectangle;
import static arcadeflex.v037b7.mame.inptport.input_port_0_r;
import static arcadeflex.v037b7.mame.inptport.input_port_1_r;
import static arcadeflex.v037b7.mame.inptport.input_port_2_r;
import static arcadeflex.v037b7.mame.inptport.input_port_3_r;
import static arcadeflex.v037b7.mame.inptport.input_port_4_r;
import static arcadeflex.v037b7.mame.inptport.input_port_5_r;
import static arcadeflex.v037b7.mame.inptportH.DEF_STR;
import static arcadeflex.v037b7.mame.inptportH.INPUT_PORTS_END;
import static arcadeflex.v037b7.mame.inptportH.IPF_8WAY;
import static arcadeflex.v037b7.mame.inptportH.IPF_PLAYER1;
import static arcadeflex.v037b7.mame.inptportH.IPF_PLAYER2;
import static arcadeflex.v037b7.mame.inptportH.IPT_BUTTON1;
import static arcadeflex.v037b7.mame.inptportH.IPT_BUTTON2;
import static arcadeflex.v037b7.mame.inptportH.IPT_COIN1;
import static arcadeflex.v037b7.mame.inptportH.IPT_COIN2;
import static arcadeflex.v037b7.mame.inptportH.IPT_COIN3;
import static arcadeflex.v037b7.mame.inptportH.IPT_JOYSTICK_DOWN;
import static arcadeflex.v037b7.mame.inptportH.IPT_JOYSTICK_LEFT;
import static arcadeflex.v037b7.mame.inptportH.IPT_JOYSTICK_RIGHT;
import static arcadeflex.v037b7.mame.inptportH.IPT_JOYSTICK_UP;
import static arcadeflex.v037b7.mame.inptportH.IPT_START1;
import static arcadeflex.v037b7.mame.inptportH.IPT_START2;
import static arcadeflex.v037b7.mame.inptportH.IPT_UNKNOWN;
import static arcadeflex.v037b7.mame.inptportH.IPT_UNUSED;
import static arcadeflex.v037b7.mame.inptportH.IP_ACTIVE_LOW;
import static arcadeflex.v037b7.mame.inptportH.PORT_BIT;
import static arcadeflex.v037b7.mame.inptportH.PORT_DIPNAME;
import static arcadeflex.v037b7.mame.inptportH.PORT_DIPSETTING;
import static arcadeflex.v037b7.mame.inptportH.PORT_SERVICE;
import static arcadeflex.v037b7.mame.inptportH.PORT_START;
import static arcadeflex.v037b7.mame.memoryH.MRA_BANK1;
import static arcadeflex.v037b7.mame.memoryH.MRA_RAM;
import static arcadeflex.v037b7.mame.memoryH.MRA_ROM;
import static arcadeflex.v037b7.mame.memoryH.MWA_RAM;
import static arcadeflex.v037b7.mame.memoryH.MWA_ROM;
import arcadeflex.v037b7.mame.memoryH.MemoryReadAddress;
import arcadeflex.v037b7.mame.memoryH.MemoryWriteAddress;
import static arcadeflex.v037b7.mame.memoryH.cpu_setbank;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.sndintrf.soundlatch_r;
import static arcadeflex.v037b7.mame.sndintrf.soundlatch_w;
import static arcadeflex.v037b7.sound._2151intf.YM2151_data_port_0_w;
import static arcadeflex.v037b7.sound._2151intf.YM2151_register_port_0_w;
import static arcadeflex.v037b7.sound._2151intf.YM2151_status_port_0_r;
import arcadeflex.v037b7.sound._2151intfH.YM2151interface;
import static arcadeflex.v037b7.sound._2151intfH.YM3012_VOL;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.gbusters.*;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.*;
import arcadeflex.common.ptrLib.UBytePtr;
import static gr.codebb.arcadeflex.common.libc.cstring.memcpy;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static arcadeflex.v037b7.mame.common.coin_counter_w;
import static arcadeflex.v037b7.mame.common.memory_region;
import static arcadeflex.v037b7.sound.mixerH.MIXER_PAN_CENTER;
import static arcadeflex.v037b7.sound.mixerH.MIXER_PAN_LEFT;
import static arcadeflex.v037b7.sound.mixerH.MIXER_PAN_RIGHT;
import arcadeflex.v037b7.generic.funcPtr.InitDriverPtr;
import arcadeflex.v037b7.generic.funcPtr.InitMachinePtr;
import arcadeflex.v037b7.generic.funcPtr.InputPortPtr;
import arcadeflex.v037b7.generic.funcPtr.InterruptPtr;
import arcadeflex.v037b7.generic.funcPtr.ReadHandlerPtr;
import arcadeflex.v037b7.generic.funcPtr.RomLoadPtr;
import arcadeflex.v037b7.generic.funcPtr.WriteHandlerPtr;
import arcadeflex.v037b7.generic.funcPtr.WriteYmHandlerPtr;
import static arcadeflex.v037b7.mame.driverH.CPU_KONAMI;
import static arcadeflex.v037b7.mame.driverH.DEFAULT_60HZ_VBLANK_DURATION;
import arcadeflex.v037b7.mame.driverH.GameDriver;
import arcadeflex.v037b7.mame.driverH.MachineCPU;
import arcadeflex.v037b7.mame.driverH.MachineDriver;
import static arcadeflex.v037b7.mame.driverH.ROT90;
import static arcadeflex.v037b7.mame.driverH.VIDEO_MODIFIES_PALETTE;
import static arcadeflex.v037b7.mame.driverH.VIDEO_TYPE_RASTER;
import static arcadeflex.v037b7.mame.palette.paletteram_r;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.konami.konami.*;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.k007232.K007232_bankswitch;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.k007232.K007232_read_port_0_r;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.k007232.K007232_set_volume;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.k007232.K007232_write_port_0_w;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.k007232H.K007232_VOL;
import gr.codebb.arcadeflex.WIP.v037b7.sound.k007232H.K007232_interface;
import gr.codebb.arcadeflex.WIP.v037b7.sound.k007232H.portwritehandlerPtr;


public class gbusters {

    static int palette_selected;
    static UBytePtr ram = new UBytePtr();

    public static InitMachinePtr gbusters_init_machine = new InitMachinePtr() {
        public void handler() {
            UBytePtr RAM = memory_region(REGION_CPU1);

            konami_cpu_setlines_callback = gbusters_banking;

            /* mirror address for banked ROM */
            memcpy(RAM, 0x18000, RAM, 0x10000, 0x08000);

            paletteram = new UBytePtr(RAM, 0x30000);
        }
    };

    public static InterruptPtr gbusters_interrupt = new InterruptPtr() {
        public int handler() {
            if (K052109_is_IRQ_enabled() != 0) {
                return KONAMI_INT_IRQ;
            } else {
                return ignore_interrupt.handler();
            }
        }
    };

    public static ReadHandlerPtr bankedram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (palette_selected != 0) {
                return paletteram_r.handler(offset);
            } else {
                return ram.read(offset);
            }
        }
    };

    public static WriteHandlerPtr bankedram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (palette_selected != 0) {
                paletteram_xBBBBBGGGGGRRRRR_swap_w.handler(offset, data);
            } else {
                ram.write(offset, data);
            }
        }
    };

    public static WriteHandlerPtr gbusters_1f98_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {

            /* bit 0 = enable char ROM reading through the video RAM */
            K052109_set_RMRD_line((data & 0x01) != 0 ? ASSERT_LINE : CLEAR_LINE);

            /* bit 7 used (during gfx rom tests), but unknown */
 /* other bits unused/unknown */
            if ((data & 0xfe) != 0) {
                //logerror("%04x: (1f98) write %02x\n",cpu_get_pc(), data);
                //usrintf_showmessage("$1f98 = %02x", data);
            }
        }
    };

    public static WriteHandlerPtr gbusters_coin_counter_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* bit 0 select palette RAM  or work RAM at 5800-5fff */
            palette_selected = ~data & 0x01;

            /* bits 1 & 2 = coin counters */
            coin_counter_w.handler(0, data & 0x02);
            coin_counter_w.handler(1, data & 0x04);

            /* bits 3 selects tilemap priority */
            gbusters_priority = data & 0x08;

            /* bit 7 is used but unknown */
 /* other bits unused/unknown */
            if ((data & 0xf8) != 0) {
                //char baf[40];
                //logerror("%04x: (ccount) write %02x\n",cpu_get_pc(), data);
                //sprintf(baf,"ccnt = %02x", data);
                //		usrintf_showmessage(baf);
            }
        }
    };

    public static WriteHandlerPtr gbusters_unknown_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            logerror("%04x: (???) write %02x\n", cpu_get_pc(), data);

            //char baf[40];
            //	sprintf(baf,"??? = %02x", data);
            //	usrintf_showmessage(baf);
        }
    };

    public static WriteHandlerPtr gbusters_sh_irqtrigger_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
           // cpu_cause_interrupt(1, 0xff);
        }
    };

    public static WriteHandlerPtr gbusters_snd_bankswitch_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            UBytePtr RAM = memory_region(REGION_SOUND1);

            int bank_B = 0x20000 * ((data >> 2) & 0x01);
            /* ?? */
            int bank_A = 0x20000 * ((data) & 0x01);
            /* ?? */

            K007232_bankswitch(0, new UBytePtr(RAM, bank_A), new UBytePtr(RAM, bank_B));

        }
    };

    static MemoryReadAddress gbusters_readmem[]
            = {
                new MemoryReadAddress(0x1f90, 0x1f90, input_port_3_r), /* coinsw  startsw */
                new MemoryReadAddress(0x1f91, 0x1f91, input_port_4_r), /* Player 1 inputs */
                new MemoryReadAddress(0x1f92, 0x1f92, input_port_5_r), /* Player 2 inputs */
                new MemoryReadAddress(0x1f93, 0x1f93, input_port_2_r), /* DIPSW #3 */
                new MemoryReadAddress(0x1f94, 0x1f94, input_port_0_r), /* DIPSW #1 */
                new MemoryReadAddress(0x1f95, 0x1f95, input_port_1_r), /* DIPSW #2 */
                new MemoryReadAddress(0x0000, 0x3fff, K052109_051960_r), /* tiles + sprites (RAM H21, G21  H6) */
                new MemoryReadAddress(0x4000, 0x57ff, MRA_RAM), /* RAM I12 */
                new MemoryReadAddress(0x5800, 0x5fff, bankedram_r), /* palette + work RAM (RAM D16  C16) */
                new MemoryReadAddress(0x6000, 0x7fff, MRA_BANK1), /* banked ROM */
                new MemoryReadAddress(0x8000, 0xffff, MRA_ROM), /* ROM 878n02.rom */
                new MemoryReadAddress(-1) /* end of table */};

    static MemoryWriteAddress gbusters_writemem[]
            = {
                new MemoryWriteAddress(0x1f80, 0x1f80, gbusters_coin_counter_w), /* coin counters */
                new MemoryWriteAddress(0x1f84, 0x1f84, soundlatch_w), /* sound code # */
                new MemoryWriteAddress(0x1f88, 0x1f88, gbusters_sh_irqtrigger_w), /* cause interrupt on audio CPU */
                new MemoryWriteAddress(0x1f8c, 0x1f8c, watchdog_reset_w), /* watchdog reset */
                new MemoryWriteAddress(0x1f98, 0x1f98, gbusters_1f98_w), /* enable gfx ROM read through VRAM */
                new MemoryWriteAddress(0x1f9c, 0x1f9c, gbusters_unknown_w), /* ??? */
                new MemoryWriteAddress(0x0000, 0x3fff, K052109_051960_w), /* tiles + sprites (RAM H21, G21  H6) */
                new MemoryWriteAddress(0x4000, 0x57ff, MWA_RAM), /* RAM I12 */
                new MemoryWriteAddress(0x5800, 0x5fff, bankedram_w, ram), /* palette + work RAM (RAM D16  C16) */
                new MemoryWriteAddress(0x6000, 0x7fff, MWA_ROM), /* banked ROM */
                new MemoryWriteAddress(0x8000, 0xffff, MWA_ROM), /* ROM 878n02.rom */
                new MemoryWriteAddress(-1) /* end of table */};

    static MemoryReadAddress gbusters_readmem_sound[]
            = {
                new MemoryReadAddress(0x0000, 0x7fff, MRA_ROM), /* ROM 878h01.rom */
                new MemoryReadAddress(0x8000, 0x87ff, MRA_RAM), /* RAM */
                new MemoryReadAddress(0xa000, 0xa000, soundlatch_r), /* soundlatch_r */
                new MemoryReadAddress(0xb000, 0xb00d, K007232_read_port_0_r), /* 007232 registers */
                new MemoryReadAddress(0xc001, 0xc001, YM2151_status_port_0_r), /* YM 2151 */
                new MemoryReadAddress(-1) /* end of table */};

    static MemoryWriteAddress gbusters_writemem_sound[]
            = {
                new MemoryWriteAddress(0x0000, 0x7fff, MWA_ROM), /* ROM 878h01.rom */
                new MemoryWriteAddress(0x8000, 0x87ff, MWA_RAM), /* RAM */
                new MemoryWriteAddress(0xb000, 0xb00d, K007232_write_port_0_w), /* 007232 registers */
                new MemoryWriteAddress(0xc000, 0xc000, YM2151_register_port_0_w), /* YM 2151 */
                new MemoryWriteAddress(0xc001, 0xc001, YM2151_data_port_0_w), /* YM 2151 */
                new MemoryWriteAddress(0xf000, 0xf000, gbusters_snd_bankswitch_w), /* 007232 bankswitch? */
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * *************************************************************************
     *
     * Input Ports
     *
     **************************************************************************
     */
    static InputPortPtr input_ports_gbusters = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* DSW #1 */
            PORT_DIPNAME(0x0f, 0x0f, DEF_STR("Coin_A"));
            PORT_DIPSETTING(0x02, DEF_STR("4C_1C"));
            PORT_DIPSETTING(0x05, DEF_STR("3C_1C"));
            PORT_DIPSETTING(0x08, DEF_STR("2C_1C"));
            PORT_DIPSETTING(0x04, DEF_STR("3C_2C"));
            PORT_DIPSETTING(0x01, DEF_STR("4C_3C"));
            PORT_DIPSETTING(0x0f, DEF_STR("1C_1C"));
            PORT_DIPSETTING(0x03, DEF_STR("3C_4C"));
            PORT_DIPSETTING(0x07, DEF_STR("2C_3C"));
            PORT_DIPSETTING(0x0e, DEF_STR("1C_2C"));
            PORT_DIPSETTING(0x06, DEF_STR("2C_5C"));
            PORT_DIPSETTING(0x0d, DEF_STR("1C_3C"));
            PORT_DIPSETTING(0x0c, DEF_STR("1C_4C"));
            PORT_DIPSETTING(0x0b, DEF_STR("1C_5C"));
            PORT_DIPSETTING(0x0a, DEF_STR("1C_6C"));
            PORT_DIPSETTING(0x09, DEF_STR("1C_7C"));
            PORT_DIPSETTING(0x00, DEF_STR("Free_Play"));
            PORT_DIPNAME(0xf0, 0xf0, DEF_STR("Coin_B"));
            PORT_DIPSETTING(0x20, DEF_STR("4C_1C"));
            PORT_DIPSETTING(0x50, DEF_STR("3C_1C"));
            PORT_DIPSETTING(0x80, DEF_STR("2C_1C"));
            PORT_DIPSETTING(0x40, DEF_STR("3C_2C"));
            PORT_DIPSETTING(0x10, DEF_STR("4C_3C"));
            PORT_DIPSETTING(0xf0, DEF_STR("1C_1C"));
            PORT_DIPSETTING(0x30, DEF_STR("3C_4C"));
            PORT_DIPSETTING(0x70, DEF_STR("2C_3C"));
            PORT_DIPSETTING(0xe0, DEF_STR("1C_2C"));
            PORT_DIPSETTING(0x60, DEF_STR("2C_5C"));
            PORT_DIPSETTING(0xd0, DEF_STR("1C_3C"));
            PORT_DIPSETTING(0xc0, DEF_STR("1C_4C"));
            PORT_DIPSETTING(0xb0, DEF_STR("1C_5C"));
            PORT_DIPSETTING(0xa0, DEF_STR("1C_6C"));
            PORT_DIPSETTING(0x90, DEF_STR("1C_7C"));
            //	PORT_DIPSETTING(    0x00, "Invalid" );

            PORT_START();
            /* DSW #2 */
            PORT_DIPNAME(0x03, 0x02, DEF_STR("Lives"));
            PORT_DIPSETTING(0x03, "2");
            PORT_DIPSETTING(0x02, "3");
            PORT_DIPSETTING(0x01, "5");
            PORT_DIPSETTING(0x00, "7");
            PORT_DIPNAME(0x04, 0x04, "Bullets");
            PORT_DIPSETTING(0x04, "50");
            PORT_DIPSETTING(0x00, "60");
            PORT_DIPNAME(0x18, 0x10, DEF_STR("Bonus_Life"));
            PORT_DIPSETTING(0x18, "50k, 200k & 400k");
            PORT_DIPSETTING(0x10, "70k, 250k & 500k");
            PORT_DIPSETTING(0x08, "50k");
            PORT_DIPSETTING(0x00, "70k");
            PORT_DIPNAME(0x60, 0x40, DEF_STR("Difficulty"));
            PORT_DIPSETTING(0x60, "Easy");
            PORT_DIPSETTING(0x40, "Normal");
            PORT_DIPSETTING(0x20, "Difficult");
            PORT_DIPSETTING(0x00, "Very Difficult");
            PORT_DIPNAME(0x80, 0x00, DEF_STR("Demo_Sounds"));
            PORT_DIPSETTING(0x80, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));

            PORT_START();
            /* DSW #3 */
            PORT_DIPNAME(0x01, 0x01, DEF_STR("Flip_Screen"));
            PORT_DIPSETTING(0x01, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_DIPNAME(0x02, 0x02, DEF_STR("Unknown"));
            PORT_DIPSETTING(0x02, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_SERVICE(0x04, IP_ACTIVE_LOW);
            PORT_DIPNAME(0x08, 0x08, DEF_STR("Unknown"));
            PORT_DIPSETTING(0x08, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_BIT(0xf0, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* COINSW */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_COIN3);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_UNKNOWN);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* PLAYER 1 INPUTS */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_UNUSED);

            PORT_START();
            /* PLAYER 2 INPUTS */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_UNUSED);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_UNUSED);

            INPUT_PORTS_END();
        }
    };

    /**
     * *************************************************************************
     *
     * Machine Driver
     *
     **************************************************************************
     */
    public static portwritehandlerPtr volume_callback = new portwritehandlerPtr() {
        public void handler(int v) {
            K007232_set_volume(0, 0, (v >> 4) * 0x11, 0);
            K007232_set_volume(0, 1, 0, (v & 0x0f) * 0x11);
        }
    };

    static K007232_interface k007232_interface = new K007232_interface(
            1, /* number of chips */
            new int[]{REGION_SOUND1}, /* memory regions */
            new int[]{K007232_VOL(30, MIXER_PAN_CENTER, 30, MIXER_PAN_CENTER)}, /* volume */
            new portwritehandlerPtr[]{volume_callback} /* external port callback */
    );

    static YM2151interface ym2151_interface = new YM2151interface(
            1, /* 1 chip */
            3579545, /* 3.579545 MHz */
            new int[]{YM3012_VOL(60, MIXER_PAN_LEFT, 60, MIXER_PAN_RIGHT)},
            new WriteYmHandlerPtr[]{null},
            new WriteHandlerPtr[]{null}
    );

    static MachineDriver machine_driver_gbusters = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                new MachineCPU(
                        CPU_KONAMI, /* Konami custom 052526 */
                        3000000, /* ? */
                        gbusters_readmem, gbusters_writemem, null, null,
                        gbusters_interrupt, 1
                )/*,
                new MachineCPU(
                        CPU_Z80 | CPU_AUDIO_CPU,
                        3579545, /* ? */
             /*           gbusters_readmem_sound, gbusters_writemem_sound, null, null,
                        ignore_interrupt, 0 /* interrupts are triggered by the main CPU */
                //)*/
            },
            60, DEFAULT_60HZ_VBLANK_DURATION, /* frames per second, vblank duration */
            1, /* 1 CPU slice per frame - interleaving is forced when a sound command is written */
            gbusters_init_machine,
            /* video hardware */
            64 * 8, 32 * 8, new rectangle(14 * 8, (64 - 14) * 8 - 1, 2 * 8, 30 * 8 - 1),
            null, /* gfx decoded by konamiic.c */
            1024, 1024,
            null,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE,
            null,
            gbusters_vh_start,
            gbusters_vh_stop,
            gbusters_vh_screenrefresh,
            /* sound hardware */
            0, 0, 0, 0,null
            /*new MachineSound[]{
                new MachineSound(
                        SOUND_YM2151,
                        ym2151_interface
                ),
                new MachineSound(
                        SOUND_K007232,
                        k007232_interface
                )
            }*/
    );

    /**
     * *************************************************************************
     *
     * Game ROMs
     *
     **************************************************************************
     */
    static RomLoadPtr rom_gbusters = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x30800, REGION_CPU1);/* code + banked roms + space for banked RAM */
            ROM_LOAD("878n02.rom", 0x10000, 0x08000, 0x51697aaa);/* ROM K13 */
            ROM_CONTINUE(0x08000, 0x08000);
            ROM_LOAD("878j03.rom", 0x20000, 0x10000, 0x3943a065);/* ROM K15 */

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the sound CPU */
            ROM_LOAD("878h01.rom", 0x00000, 0x08000, 0x96feafaa);

            ROM_REGION(0x80000, REGION_GFX1);/* graphics (addressable by the main CPU) */
            ROM_LOAD("878c07.rom", 0x00000, 0x40000, 0xeeed912c);/* tiles */
            ROM_LOAD("878c08.rom", 0x40000, 0x40000, 0x4d14626d);/* tiles */

            ROM_REGION(0x80000, REGION_GFX2);/* graphics (addressable by the main CPU) */
            ROM_LOAD("878c05.rom", 0x00000, 0x40000, 0x01f4aea5);/* sprites */
            ROM_LOAD("878c06.rom", 0x40000, 0x40000, 0xedfaaaaf);/* sprites */

            ROM_REGION(0x0100, REGION_PROMS);
            ROM_LOAD("878a09.rom", 0x0000, 0x0100, 0xe2d09a1b);/* priority encoder (not used) */

            ROM_REGION(0x40000, REGION_SOUND1);/* samples for 007232 */
            ROM_LOAD("878c04.rom", 0x00000, 0x40000, 0x9e982d1c);
            ROM_END();
        }
    };

    static RomLoadPtr rom_crazycop = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x30800, REGION_CPU1);/* code + banked roms + space for banked RAM */
            ROM_LOAD("878m02.bin", 0x10000, 0x08000, 0x9c1c9f52);/* ROM K13 */
            ROM_CONTINUE(0x08000, 0x08000);
            ROM_LOAD("878j03.rom", 0x20000, 0x10000, 0x3943a065);/* ROM K15 */

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the sound CPU */
            ROM_LOAD("878h01.rom", 0x00000, 0x08000, 0x96feafaa);

            ROM_REGION(0x80000, REGION_GFX1);/* graphics (addressable by the main CPU) */
            ROM_LOAD("878c07.rom", 0x00000, 0x40000, 0xeeed912c);/* tiles */
            ROM_LOAD("878c08.rom", 0x40000, 0x40000, 0x4d14626d);/* tiles */

            ROM_REGION(0x80000, REGION_GFX2);/* graphics (addressable by the main CPU) */
            ROM_LOAD("878c05.rom", 0x00000, 0x40000, 0x01f4aea5);/* sprites */
            ROM_LOAD("878c06.rom", 0x40000, 0x40000, 0xedfaaaaf);/* sprites */

            ROM_REGION(0x0100, REGION_PROMS);
            ROM_LOAD("878a09.rom", 0x0000, 0x0100, 0xe2d09a1b);/* priority encoder (not used) */

            ROM_REGION(0x40000, REGION_SOUND1);/* samples for 007232 */
            ROM_LOAD("878c04.rom", 0x00000, 0x40000, 0x9e982d1c);
            ROM_END();
        }
    };

    static konami_cpu_setlines_callbackPtr gbusters_banking = new konami_cpu_setlines_callbackPtr() {
        public void handler(int lines) {
            UBytePtr RAM = memory_region(REGION_CPU1);
            int offs = 0x10000;

            /* bits 0-3 ROM bank */
            offs += (lines & 0x0f) * 0x2000;
            cpu_setbank(1, new UBytePtr(RAM, offs));

            if ((lines & 0xf0) != 0) {
                //logerror("%04x: (lines) write %02x\n",cpu_get_pc(), lines);
                //usrintf_showmessage("lines = %02x", lines);
            }

            /* other bits unknown */
        }
    };

    public static InitDriverPtr init_gbusters = new InitDriverPtr() {
        public void handler() {
            konami_rom_deinterleave_2(REGION_GFX1);
            konami_rom_deinterleave_2(REGION_GFX2);
        }
    };

    public static GameDriver driver_gbusters = new GameDriver("1988", "gbusters", "gbusters.java", rom_gbusters, null, machine_driver_gbusters, input_ports_gbusters, init_gbusters, ROT90, "Konami", "Gang Busters");
    public static GameDriver driver_crazycop = new GameDriver("1988", "crazycop", "gbusters.java", rom_crazycop, driver_gbusters, machine_driver_gbusters, input_ports_gbusters, init_gbusters, ROT90, "Konami", "Crazy Cop (Japan)");
}
