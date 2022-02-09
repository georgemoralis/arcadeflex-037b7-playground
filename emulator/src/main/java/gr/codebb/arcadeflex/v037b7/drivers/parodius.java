/*
 * ported to v0.37b7
 * ported to v0.36
 */
package gr.codebb.arcadeflex.v037b7.drivers;

import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.konami.konami.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_cause_interrupt;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_get_pc;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_set_nmi_line;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_spinuntil_int;
import static arcadeflex.v037b7.mame.cpuintrf.ignore_interrupt;
import static arcadeflex.v037b7.mame.cpuintrf.interrupt;
import static arcadeflex.v037b7.mame.cpuintrf.watchdog_reset_r;
import static arcadeflex.v037b7.mame.cpuintrfH.ASSERT_LINE;
import static arcadeflex.v037b7.mame.cpuintrfH.CLEAR_LINE;
import arcadeflex.v037b7.mame.drawgfxH.rectangle;
import static arcadeflex.v037b7.mame.inptport.input_port_0_r;
import static arcadeflex.v037b7.mame.inptport.input_port_1_r;
import static arcadeflex.v037b7.mame.inptport.input_port_2_r;
import static arcadeflex.v037b7.mame.inptport.input_port_3_r;
import static arcadeflex.v037b7.mame.inptport.input_port_4_r;
import static arcadeflex.v037b7.mame.inptportH.DEF_STR;
import static arcadeflex.v037b7.mame.inptportH.INPUT_PORTS_END;
import static arcadeflex.v037b7.mame.inptportH.IPF_8WAY;
import static arcadeflex.v037b7.mame.inptportH.IPF_PLAYER1;
import static arcadeflex.v037b7.mame.inptportH.IPF_PLAYER2;
import static arcadeflex.v037b7.mame.inptportH.IPT_BUTTON1;
import static arcadeflex.v037b7.mame.inptportH.IPT_BUTTON2;
import static arcadeflex.v037b7.mame.inptportH.IPT_BUTTON3;
import static arcadeflex.v037b7.mame.inptportH.IPT_COIN1;
import static arcadeflex.v037b7.mame.inptportH.IPT_COIN2;
import static arcadeflex.v037b7.mame.inptportH.IPT_COIN3;
import static arcadeflex.v037b7.mame.inptportH.IPT_JOYSTICK_DOWN;
import static arcadeflex.v037b7.mame.inptportH.IPT_JOYSTICK_LEFT;
import static arcadeflex.v037b7.mame.inptportH.IPT_JOYSTICK_RIGHT;
import static arcadeflex.v037b7.mame.inptportH.IPT_JOYSTICK_UP;
import static arcadeflex.v037b7.mame.inptportH.IPT_SERVICE;
import static arcadeflex.v037b7.mame.inptportH.IPT_START1;
import static arcadeflex.v037b7.mame.inptportH.IPT_START2;
import static arcadeflex.v037b7.mame.inptportH.IP_ACTIVE_LOW;
import static arcadeflex.v037b7.mame.inptportH.PORT_BIT;
import static arcadeflex.v037b7.mame.inptportH.PORT_DIPNAME;
import static arcadeflex.v037b7.mame.inptportH.PORT_DIPSETTING;
import static arcadeflex.v037b7.mame.inptportH.PORT_SERVICE;
import static arcadeflex.v037b7.mame.inptportH.PORT_START;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static arcadeflex.v037b7.mame.memoryH.MRA_BANK1;
import static arcadeflex.v037b7.mame.memoryH.MRA_RAM;
import static arcadeflex.v037b7.mame.memoryH.MRA_ROM;
import static arcadeflex.v037b7.mame.memoryH.MWA_RAM;
import static arcadeflex.v037b7.mame.memoryH.MWA_ROM;
import arcadeflex.v037b7.mame.memoryH.MemoryReadAddress;
import arcadeflex.v037b7.mame.memoryH.MemoryWriteAddress;
import static arcadeflex.v037b7.mame.memoryH.cpu_setbank;
import static arcadeflex.v037b7.mame.palette.paletteram_r;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.paletteram_xBBBBBGGGGGRRRRR_swap_w;
import static arcadeflex.v037b7.sound._2151intf.YM2151_data_port_0_w;
import static arcadeflex.v037b7.sound._2151intf.YM2151_register_port_0_w;
import static arcadeflex.v037b7.sound._2151intf.YM2151_status_port_0_r;
import arcadeflex.v037b7.sound._2151intfH.YM2151interface;
import static arcadeflex.v037b7.sound._2151intfH.YM3012_VOL;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_is_IRQ_enabled;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_r;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_w;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.konami_rom_deinterleave_2;
import arcadeflex.common.ptrLib.UBytePtr;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static arcadeflex.v037b7.mame.common.coin_counter_w;
import static arcadeflex.v037b7.mame.common.memory_region;
import static arcadeflex.v037b7.sound.mixerH.MIXER;
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
import static arcadeflex.v037b7.mame.driverH.CPU_AUDIO_CPU;
import static arcadeflex.v037b7.mame.driverH.CPU_KONAMI;
import static arcadeflex.v037b7.mame.driverH.CPU_Z80;
import static arcadeflex.v037b7.mame.driverH.DEFAULT_60HZ_VBLANK_DURATION;
import arcadeflex.v037b7.mame.driverH.GameDriver;
import arcadeflex.v037b7.mame.driverH.MachineCPU;
import arcadeflex.v037b7.mame.driverH.MachineDriver;
import static arcadeflex.v037b7.mame.driverH.ROT0;
import static arcadeflex.v037b7.mame.driverH.VIDEO_MODIFIES_PALETTE;
import static arcadeflex.v037b7.mame.driverH.VIDEO_TYPE_RASTER;
import arcadeflex.v037b7.mame.sndintrfH.MachineSound;
import static arcadeflex.v037b7.mame.sndintrfH.SOUND_K053260;
import static arcadeflex.v037b7.mame.sndintrfH.SOUND_YM2151;
import static arcadeflex.v037b7.sound.k053260.K053260_r;
import static arcadeflex.v037b7.sound.k053260.K053260_w;
import arcadeflex.v037b7.sound.k053260H.K053260_interface;
import static gr.codebb.arcadeflex.v037b7.vidhrdw.parodius.*;
import arcadeflex.v056.mame.timer.timer_callback;
import static arcadeflex.v056.mame.timer.timer_set;
import static arcadeflex.v056.mame.timerH.TIME_IN_USEC;
import static arcadeflex.v037b7.mame.driverH.SOUND_SUPPORTS_STEREO;

public class parodius {

    public static konami_cpu_setlines_callbackPtr parodius_banking = new konami_cpu_setlines_callbackPtr() {
        public void handler(int lines) {
            UBytePtr RAM = memory_region(REGION_CPU1);
            int offs = 0;

            if ((lines & 0xf0) != 0) {
                logerror("%04x: setlines %02x\n", cpu_get_pc(), lines);
            }

            offs = 0x10000 + (((lines & 0x0f) ^ 0x0f) * 0x4000);
            if (offs >= 0x48000) {
                offs -= 0x40000;
            }
            cpu_setbank(1, new UBytePtr(RAM, offs));
        }
    };

    public static InitMachinePtr parodius_init_machine = new InitMachinePtr() {
        public void handler() {
            UBytePtr RAM = memory_region(REGION_CPU1);

            konami_cpu_setlines_callback = parodius_banking;

            paletteram = new UBytePtr(memory_region(REGION_CPU1), 0x48000);

            videobank = 0;

            /* init the default bank */
            cpu_setbank(1, new UBytePtr(RAM, 0x10000));
        }
    };

    static int videobank;
    static UBytePtr ram = new UBytePtr();

    public static InterruptPtr parodius_interrupt = new InterruptPtr() {
        public int handler() {
            if (K052109_is_IRQ_enabled() != 0) {
                return interrupt.handler();
            } else {
                return ignore_interrupt.handler();
            }
        }
    };

    public static ReadHandlerPtr bankedram_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if ((videobank & 0x01) != 0) {
                if ((videobank & 0x04) != 0) {
                    return paletteram_r.handler(offset + 0x0800);
                } else {
                    return paletteram_r.handler(offset);
                }
            } else {
                return ram.read(offset);
            }
        }
    };

    public static WriteHandlerPtr bankedram_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if ((videobank & 0x01) != 0) {
                if ((videobank & 0x04) != 0) {
                    paletteram_xBBBBBGGGGGRRRRR_swap_w.handler(offset + 0x0800, data);
                } else {
                    paletteram_xBBBBBGGGGGRRRRR_swap_w.handler(offset, data);
                }
            } else {
                ram.write(offset, data);
            }
        }
    };

    public static ReadHandlerPtr parodius_052109_053245_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if ((videobank & 0x02) != 0) {
                return K053245_r.handler(offset);
            } else {
                return K052109_r.handler(offset);
            }
        }
    };

    public static WriteHandlerPtr parodius_052109_053245_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if ((videobank & 0x02) != 0) {
                K053245_w.handler(offset, data);
            } else {
                K052109_w.handler(offset, data);
            }
        }
    };

    public static WriteHandlerPtr parodius_videobank_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if ((videobank & 0xf8) != 0) {
                logerror("%04x: videobank = %02x\n", cpu_get_pc(), data);
            }

            /* bit 0 = select palette or work RAM at 0000-07ff */
 /* bit 1 = select 052109 or 053245 at 2000-27ff */
 /* bit 2 = select palette bank 0 or 1 */
            videobank = data;
        }
    };

    public static WriteHandlerPtr parodius_3fc0_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if ((data & 0xf4) != 0x10) {
                logerror("%04x: 3fc0 = %02x\n", cpu_get_pc(), data);
            }

            /* bit 0/1 = coin counters */
            coin_counter_w.handler(0, data & 0x01);
            coin_counter_w.handler(1, data & 0x02);

            /* bit 3 = enable char ROM reading through the video RAM */
            K052109_set_RMRD_line((data & 0x08) != 0 ? ASSERT_LINE : CLEAR_LINE);

            /* other bits unknown */
        }
    };

    public static ReadHandlerPtr parodius_sound_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            /* If the sound CPU is running, read the status, otherwise
		   just make it pass the test */
            if (Machine.sample_rate != 0) {
                return K053260_r.handler(2 + offset);
            } else {
                return offset != 0 ? 0x00 : 0x80;
            }
        }
    };

    public static WriteHandlerPtr parodius_sh_irqtrigger_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            cpu_cause_interrupt(1, 0xff);
        }
    };

    static int nmi_enabled;

    static void sound_nmi_callback(int param) {
        cpu_set_nmi_line(1, (nmi_enabled) != 0 ? CLEAR_LINE : ASSERT_LINE);

        nmi_enabled = 0;
    }

    public static timer_callback nmi_callback = new timer_callback() {
        public void handler(int trigger) {
            cpu_set_nmi_line(1, ASSERT_LINE);
        }
    };

    public static WriteHandlerPtr sound_arm_nmi_w = new WriteHandlerPtr() {
        public void handler(int offs, int data) {
            //	sound_nmi_enabled = 1;
            cpu_set_nmi_line(1, CLEAR_LINE);
            timer_set(TIME_IN_USEC(50), 0, nmi_callback);
            /* kludge until the K053260 is emulated correctly */
        }
    };

    public static ReadHandlerPtr speedup_r = new ReadHandlerPtr() {
        public int handler(int offs) {
            int data = memory_region(REGION_CPU1).read(0x1837);

            if (cpu_get_pc() == 0xa400 && data == 0) {
                cpu_spinuntil_int();
            }

            return data;
        }
    };

    /**
     * *****************************************
     */
    static MemoryReadAddress parodius_readmem[]
            = {
                new MemoryReadAddress(0x0000, 0x07ff, bankedram_r),
                new MemoryReadAddress(0x1837, 0x1837, speedup_r),
                new MemoryReadAddress(0x0800, 0x1fff, MRA_RAM),
                new MemoryReadAddress(0x3f8c, 0x3f8c, input_port_0_r),
                new MemoryReadAddress(0x3f8d, 0x3f8d, input_port_1_r),
                new MemoryReadAddress(0x3f8e, 0x3f8e, input_port_4_r),
                new MemoryReadAddress(0x3f8f, 0x3f8f, input_port_2_r),
                new MemoryReadAddress(0x3f90, 0x3f90, input_port_3_r),
                new MemoryReadAddress(0x3fa0, 0x3faf, K053244_r),
                new MemoryReadAddress(0x3fc0, 0x3fc0, watchdog_reset_r),
                new MemoryReadAddress(0x3fcc, 0x3fcd, parodius_sound_r), /* K053260 */
                new MemoryReadAddress(0x2000, 0x27ff, parodius_052109_053245_r),
                new MemoryReadAddress(0x2000, 0x5fff, K052109_r),
                new MemoryReadAddress(0x6000, 0x9fff, MRA_BANK1), /* banked ROM */
                new MemoryReadAddress(0xa000, 0xffff, MRA_ROM), /* ROM */
                new MemoryReadAddress(-1) /* end of table */};

    static MemoryWriteAddress parodius_writemem[]
            = {
                new MemoryWriteAddress(0x0000, 0x07ff, bankedram_w, ram),
                new MemoryWriteAddress(0x0800, 0x1fff, MWA_RAM),
                new MemoryWriteAddress(0x3fa0, 0x3faf, K053244_w),
                new MemoryWriteAddress(0x3fb0, 0x3fbf, K053251_w),
                new MemoryWriteAddress(0x3fc0, 0x3fc0, parodius_3fc0_w),
                new MemoryWriteAddress(0x3fc4, 0x3fc4, parodius_videobank_w),
                new MemoryWriteAddress(0x3fc8, 0x3fc8, parodius_sh_irqtrigger_w),
                new MemoryWriteAddress(0x3fcc, 0x3fcd, K053260_w),
                new MemoryWriteAddress(0x2000, 0x27ff, parodius_052109_053245_w),
                new MemoryWriteAddress(0x2000, 0x5fff, K052109_w),
                new MemoryWriteAddress(0x6000, 0x9fff, MWA_ROM), /* banked ROM */
                new MemoryWriteAddress(0xa000, 0xffff, MWA_ROM), /* ROM */
                new MemoryWriteAddress(-1) /* end of table */};

    static MemoryReadAddress parodius_readmem_sound[]
            = {
                new MemoryReadAddress(0x0000, 0xefff, MRA_ROM),
                new MemoryReadAddress(0xf000, 0xf7ff, MRA_RAM),
                new MemoryReadAddress(0xf801, 0xf801, YM2151_status_port_0_r),
                new MemoryReadAddress(0xfc00, 0xfc2f, K053260_r),
                new MemoryReadAddress(-1) /* end of table */};

    static MemoryWriteAddress parodius_writemem_sound[]
            = {
                new MemoryWriteAddress(0x0000, 0xefff, MWA_ROM),
                new MemoryWriteAddress(0xf000, 0xf7ff, MWA_RAM),
                new MemoryWriteAddress(0xf800, 0xf800, YM2151_register_port_0_w),
                new MemoryWriteAddress(0xf801, 0xf801, YM2151_data_port_0_w),
                new MemoryWriteAddress(0xfa00, 0xfa00, sound_arm_nmi_w),
                new MemoryWriteAddress(0xfc00, 0xfc2f, K053260_w),
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * *************************************************************************
     *
     * Input Ports
     *
     **************************************************************************
     */
    static InputPortPtr input_ports_parodius = new InputPortPtr() {
        public void handler() {
            PORT_START();
            /* PLAYER 1 INPUTS */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_START1);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER1);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON3 | IPF_PLAYER1);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1);

            PORT_START();
            /* PLAYER 2 INPUTS */
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_START2);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x10, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER2);
            PORT_BIT(0x20, IP_ACTIVE_LOW, IPT_BUTTON3 | IPF_PLAYER2);
            PORT_BIT(0x40, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2);
            PORT_BIT(0x80, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2);

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
            //	PORT_DIPSETTING(    0x00, "No Use" );

            PORT_START();
            /* DSW #2 */
            PORT_DIPNAME(0x03, 0x02, DEF_STR("Lives"));
            PORT_DIPSETTING(0x03, "2");
            PORT_DIPSETTING(0x02, "3");
            PORT_DIPSETTING(0x01, "4");
            PORT_DIPSETTING(0x00, "7");
            PORT_DIPNAME(0x04, 0x00, DEF_STR("Cabinet"));
            PORT_DIPSETTING(0x00, DEF_STR("Upright"));
            PORT_DIPSETTING(0x04, DEF_STR("Cocktail"));
            PORT_DIPNAME(0x18, 0x18, DEF_STR("Bonus_Life"));
            PORT_DIPSETTING(0x18, "20000 80000");
            PORT_DIPSETTING(0x10, "30000 100000");
            PORT_DIPSETTING(0x08, "20000");
            PORT_DIPSETTING(0x00, "70000");
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
            PORT_BIT(0x01, IP_ACTIVE_LOW, IPT_COIN3);
            PORT_BIT(0x02, IP_ACTIVE_LOW, IPT_SERVICE);
            PORT_BIT(0x04, IP_ACTIVE_LOW, IPT_COIN1);
            PORT_BIT(0x08, IP_ACTIVE_LOW, IPT_COIN2);
            PORT_DIPNAME(0x10, 0x10, DEF_STR("Flip_Screen"));
            PORT_DIPSETTING(0x10, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
            PORT_DIPNAME(0x20, 0x20, "Upright Controls");
            PORT_DIPSETTING(0x20, "Single");
            PORT_DIPSETTING(0x00, "Dual");
            PORT_SERVICE(0x40, IP_ACTIVE_LOW);
            PORT_DIPNAME(0x80, 0x80, DEF_STR("Unknown"));
            PORT_DIPSETTING(0x80, DEF_STR("Off"));
            PORT_DIPSETTING(0x00, DEF_STR("On"));
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
    static YM2151interface ym2151_interface = new YM2151interface(
            1, /* 1 chip */
            3579545, /* 3.579545 MHz */
            new int[]{YM3012_VOL(100, MIXER_PAN_LEFT, 100, MIXER_PAN_RIGHT)},
            new WriteYmHandlerPtr[]{null}
    );

    static K053260_interface k053260_interface = new K053260_interface(
            3579545,
            REGION_SOUND1, /* memory region */
            new int[]{MIXER(70, MIXER_PAN_LEFT), MIXER(70, MIXER_PAN_RIGHT)},
            null
    //	sound_nmi_callback
    );

    static MachineDriver machine_driver_parodius = new MachineDriver(
            /* basic machine hardware */
            new MachineCPU[]{
                new MachineCPU(
                        CPU_KONAMI, /* 053248 */
                        3000000, /* ? */
                        parodius_readmem, parodius_writemem, null, null,
                        parodius_interrupt, 1
                ),
                new MachineCPU(
                        CPU_Z80 | CPU_AUDIO_CPU,
                        3579545,
                        parodius_readmem_sound, parodius_writemem_sound, null, null,
                        ignore_interrupt, 0 /* IRQs are triggered by the main CPU */
                /* NMIs are triggered by the 053260 */
                )
            },
            60, DEFAULT_60HZ_VBLANK_DURATION, /* frames per second, vblank duration */
            1, /* 1 CPU slice per frame - interleaving is forced when a sound command is written */
            parodius_init_machine,
            /* video hardware */
            64 * 8, 32 * 8, new rectangle(14 * 8, (64 - 14) * 8 - 1, 2 * 8, 30 * 8 - 1),
            null, /* gfx decoded by konamiic.c */
            2048, 2048,
            null,
            VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE,
            null,
            parodius_vh_start,
            parodius_vh_stop,
            parodius_vh_screenrefresh,
            /* sound hardware */
            SOUND_SUPPORTS_STEREO,0,0,0,
            new MachineSound[]{
                new MachineSound(
                        SOUND_YM2151,
                        ym2151_interface
                ),
                new MachineSound(
                        SOUND_K053260,
                        k053260_interface
                )
            }
    );

    /**
     * *************************************************************************
     *
     * Game ROMs
     *
     **************************************************************************
     */
    static RomLoadPtr rom_parodius = new RomLoadPtr() {
        public void handler() {
            ROM_REGION(0x51000, REGION_CPU1);/* code + banked roms + palette RAM */
            ROM_LOAD("955e01.bin", 0x10000, 0x20000, 0x49baa334);
            ROM_LOAD("955e02.bin", 0x30000, 0x18000, 0x14010d6f);
            ROM_CONTINUE(0x08000, 0x08000);

            ROM_REGION(0x10000, REGION_CPU2);/* 64k for the sound CPU */
            ROM_LOAD("955e03.bin", 0x0000, 0x10000, 0x940aa356);

            ROM_REGION(0x100000, REGION_GFX1);/* graphics ( don't dispose as the program can read them ) */
            ROM_LOAD("955d07.bin", 0x000000, 0x080000, 0x89473fec);/* characters */
            ROM_LOAD("955d08.bin", 0x080000, 0x080000, 0x43d5cda1);/* characters */

            ROM_REGION(0x100000, REGION_GFX2);/* graphics ( don't dispose as the program can read them ) */
            ROM_LOAD("955d05.bin", 0x000000, 0x080000, 0x7a1e55e0);/* sprites */
            ROM_LOAD("955d06.bin", 0x080000, 0x080000, 0xf4252875);/* sprites */

            ROM_REGION(0x80000, REGION_SOUND1);/* 053260 samples */
            ROM_LOAD("955d04.bin", 0x00000, 0x80000, 0xe671491a);
            ROM_END();
        }
    };

    /**
     * *************************************************************************
     *
     * Game driver(s)
     *
     **************************************************************************
     */
    public static InitDriverPtr init_parodius = new InitDriverPtr() {
        public void handler() {
            konami_rom_deinterleave_2(REGION_GFX1);
            konami_rom_deinterleave_2(REGION_GFX2);
        }
    };

    public static GameDriver driver_parodius = new GameDriver("1990", "parodius", "parodius.java", rom_parodius, null, machine_driver_parodius, input_ports_parodius, init_parodius, ROT0, "Konami", "Parodius DA! (Japan)");
}