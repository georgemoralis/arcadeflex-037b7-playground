/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.sndhrdw;

//common imports
import static arcadeflex.WIP.v037b7.sndhrdw.williams.*;
import static arcadeflex.common.libc.expressions.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//machine imports
import static arcadeflex.v037b7.machine._6821pia.*;
import static arcadeflex.v037b7.machine._6821piaH.*;
//mame imports
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v056.mame.timer.*;
import static arcadeflex.v056.mame.timerH.*;
//sndhrdw imports
import static arcadeflex.v037b7.sndhrdw.mcrH.*;
//sound imports
import static arcadeflex.v037b7.sound.ay8910.*;
import static arcadeflex.v037b7.sound.ay8910H.*;
import static arcadeflex.v037b7.sound.dac.*;
import static arcadeflex.v037b7.sound.dacH.*;
import static arcadeflex.v037b7.sound.mixerH.*;
import static arcadeflex.v037b7.sound._5220intf.*;
import arcadeflex.v037b7.sound._5220intfH.TMS5220interface;
//to be organized
import static gr.codebb.arcadeflex.old.sound.mixer.mixer_sound_enable_global_w;
import static gr.codebb.arcadeflex.v037b7.cpu.m6809.m6809H.M6809_IRQ_LINE;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.m6800.m6800Η.M6800_IRQ_LINE;

public class mcr {

    /**
     * ***********************************
     *
     * Global variables
     *
     ************************************
     */
    public static int mcr_sound_config;

    /**
     * ***********************************
     *
     * Statics
     *
     ************************************
     */
    static char dacval;

    /* SSIO-specific globals */
    static int /*UINT8*/ ssio_sound_cpu;
    static /*UINT8*/ int[] u8_ssio_data = new int[4];
    static /*UINT8*/ int u8_ssio_status;
    static /*UINT8*/ int[][] u8_ssio_duty_cycle = new int[2][3];

    /* Chip Squeak Deluxe-specific globals */
    static int/*UINT8*/ u8_csdeluxe_sound_cpu;
    static int/*UINT8*/ u8_csdeluxe_dac_index;
    /* Turbo Chip Squeak-specific globals */
    static int/*UINT8*/ u8_turbocs_sound_cpu;

    static int/*UINT8*/ u8_turbocs_dac_index;
    static int/*UINT8*/ u8_turbocs_status;

    /* Sounds Good-specific globals */
    static int/*UINT8*/ u8_soundsgood_sound_cpu;
    static int/*UINT8*/ u8_soundsgood_dac_index;
    static int/*UINT8*/ u8_soundsgood_status;

    /* Squawk n' Talk-specific globals */
    static int/*UINT8*/ u8_squawkntalk_sound_cpu;
    static int/*UINT8*/ u8_squawkntalk_tms_command;
    static int/*UINT8*/ u8_squawkntalk_tms_strobes;

    /**
     * ***********************************
     *
     * Generic MCR sound initialization
     *
     ************************************
     */
    public static void mcr_sound_init() {
        int sound_cpu = 1;
        int dac_index = 0;

        /* SSIO */
        if ((mcr_sound_config & MCR_SSIO) != 0) {
            ssio_sound_cpu = sound_cpu++;
            ssio_reset_w(1);
            ssio_reset_w(0);
        }

        /* Turbo Chip Squeak */
        if ((mcr_sound_config & MCR_TURBO_CHIP_SQUEAK) != 0) {
            pia_unconfig();
            pia_config(0, PIA_ALTERNATE_ORDERING, turbocs_pia_intf);
            u8_turbocs_dac_index = dac_index++;
            u8_turbocs_sound_cpu = sound_cpu++;
            turbocs_reset_w(1);
            turbocs_reset_w(0);
            /* reset any PIAs */
            pia_reset();
        }
        if ((mcr_sound_config & MCR_CHIP_SQUEAK_DELUXE) != 0) {
            pia_config(0, PIA_ALTERNATE_ORDERING | PIA_16BIT_AUTO, csdeluxe_pia_intf);
            u8_csdeluxe_dac_index = dac_index++;
            u8_csdeluxe_sound_cpu = sound_cpu++;
            csdeluxe_reset_w(1);
            csdeluxe_reset_w(0);
        }

        /* Sounds Good */
        if ((mcr_sound_config & MCR_SOUNDS_GOOD) != 0) {
            /* special case: Spy Hunter 2 has both Turbo CS and Sounds Good, so we use PIA slot 1 */
            pia_config(1, PIA_ALTERNATE_ORDERING | PIA_16BIT_UPPER, soundsgood_pia_intf);
            u8_soundsgood_dac_index = dac_index++;
            u8_soundsgood_sound_cpu = sound_cpu++;
            soundsgood_reset_w(1);
            soundsgood_reset_w(0);
        }

        /* Squawk n Talk */
        if ((mcr_sound_config & MCR_SQUAWK_N_TALK) != 0) {
            pia_config(0, PIA_STANDARD_ORDERING | PIA_8BIT, squawkntalk_pia0_intf);
            pia_config(1, PIA_STANDARD_ORDERING | PIA_8BIT, squawkntalk_pia1_intf);
            u8_squawkntalk_sound_cpu = sound_cpu++;
            squawkntalk_reset_w(1);
            squawkntalk_reset_w(0);
        }
        /* Advanced Audio */
        if ((mcr_sound_config & MCR_WILLIAMS_SOUND) != 0) {
                williams_cvsd_init(sound_cpu++, 0);
                dac_index++;
                williams_cvsd_reset_w(1);
                williams_cvsd_reset_w(0);
        }
    }

    /**
     * ***********************************
     *
     * MCR SSIO communications
     *
     * Z80, 2 AY-3812
     *
     ************************************
     */
    /**
     * ******* internal interfaces **********
     */
    public static WriteHandlerPtr ssio_status_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            u8_ssio_status = data & 0xFF;
        }
    };

    public static ReadHandlerPtr ssio_data_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return u8_ssio_data[offset] & 0xFF;
        }
    };
    public static timer_callback ssio_delayed_data_w = new timer_callback() {
        public void handler(int param) {

            u8_ssio_data[param >> 8] = param & 0xff;
        }
    };

    static void ssio_update_volumes() {
        int chip, chan;
        for (chip = 0; chip < 2; chip++) {
            for (chan = 0; chan < 3; chan++) {
                AY8910_set_volume(chip, chan, (u8_ssio_duty_cycle[chip][chan] ^ 15) * 100 / 15);
            }
        }
    }

    public static WriteHandlerPtr ssio_porta0_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            u8_ssio_duty_cycle[0][0] = data & 15;
            u8_ssio_duty_cycle[0][1] = (data >> 4) & 0xFF;
            ssio_update_volumes();
        }
    };

    public static WriteHandlerPtr ssio_portb0_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            u8_ssio_duty_cycle[0][2] = data & 15;
            ssio_update_volumes();
        }
    };

    public static WriteHandlerPtr ssio_porta1_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            u8_ssio_duty_cycle[1][0] = data & 15;
            u8_ssio_duty_cycle[1][1] = (data >> 4) & 0xFF;
            ssio_update_volumes();
        }
    };

    public static WriteHandlerPtr ssio_portb1_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            u8_ssio_duty_cycle[1][2] = data & 15;
            mixer_sound_enable_global_w(NOT(data & 0x80));
            ssio_update_volumes();
        }
    };

    /**
     * ******* external interfaces **********
     */
    public static WriteHandlerPtr ssio_data_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            timer_set(TIME_NOW, (offset << 8) | (data & 0xff), ssio_delayed_data_w);
        }
    };

    public static ReadHandlerPtr ssio_status_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return u8_ssio_status;
        }
    };

    public static void ssio_reset_w(int state) {
        /* going high halts the CPU */
        if (state != 0) {
            int i;

            cpu_set_reset_line(ssio_sound_cpu, ASSERT_LINE);

            /* latches also get reset */
            for (i = 0; i < 4; i++) {
                u8_ssio_data[i] = 0;
            }
            u8_ssio_status = 0;
        } /* going low resets and reactivates the CPU */ else {
            cpu_set_reset_line(ssio_sound_cpu, CLEAR_LINE);
        }
    }

    /**
     * ******* sound interfaces **********
     */
    public static AY8910interface ssio_ay8910_interface = new AY8910interface(
            2, /* 2 chips */
            2000000, /* 2 MHz ?? */
            new int[]{MIXER(33, MIXER_PAN_LEFT), MIXER(33, MIXER_PAN_RIGHT)}, /* dotron clips with anything higher */
            new ReadHandlerPtr[]{null, null},
            new ReadHandlerPtr[]{null, null},
            new WriteHandlerPtr[]{ssio_porta0_w, ssio_porta1_w},
            new WriteHandlerPtr[]{ssio_portb0_w, ssio_portb1_w}
    );

    /**
     * ******* memory interfaces **********
     */
    public static MemoryReadAddress ssio_readmem[]
            = {
                new MemoryReadAddress(0x0000, 0x3fff, MRA_ROM),
                new MemoryReadAddress(0x8000, 0x83ff, MRA_RAM),
                new MemoryReadAddress(0x9000, 0x9003, ssio_data_r),
                new MemoryReadAddress(0xa001, 0xa001, AY8910_read_port_0_r),
                new MemoryReadAddress(0xb001, 0xb001, AY8910_read_port_1_r),
                new MemoryReadAddress(0xe000, 0xe000, MRA_NOP),
                new MemoryReadAddress(0xf000, 0xf000, input_port_5_r),
                new MemoryReadAddress(-1) /* end of table */};

    public static MemoryWriteAddress ssio_writemem[]
            = {
                new MemoryWriteAddress(0x0000, 0x3fff, MWA_ROM),
                new MemoryWriteAddress(0x8000, 0x83ff, MWA_RAM),
                new MemoryWriteAddress(0xa000, 0xa000, AY8910_control_port_0_w),
                new MemoryWriteAddress(0xa002, 0xa002, AY8910_write_port_0_w),
                new MemoryWriteAddress(0xb000, 0xb000, AY8910_control_port_1_w),
                new MemoryWriteAddress(0xb002, 0xb002, AY8910_write_port_1_w),
                new MemoryWriteAddress(0xc000, 0xc000, ssio_status_w),
                new MemoryWriteAddress(0xe000, 0xe000, MWA_NOP),
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * ***********************************
     *
     * Chip Squeak Deluxe communications
     *
     * MC68000, 1 PIA, 10-bit DAC
     *
     ************************************
     */
    /**
     * ******* internal interfaces **********
     */
    public static WriteHandlerPtr csdeluxe_porta_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            dacval = (char) ((dacval & ~0x3fc) | (data << 2));
            DAC_signed_data_16_w.handler(u8_csdeluxe_dac_index, dacval << 6);
        }
    };

    public static WriteHandlerPtr csdeluxe_portb_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            dacval = (char) ((dacval & ~0x003) | (data >> 6));
            DAC_signed_data_16_w.handler(u8_csdeluxe_dac_index, dacval << 6);
        }
    };

    static IrqfuncPtr csdeluxe_irq = new IrqfuncPtr() {
        @Override
        public void handler(int state) {
            cpu_set_irq_line(u8_csdeluxe_sound_cpu, 4, state != 0 ? ASSERT_LINE : CLEAR_LINE);
        }
    };

    static timer_callback csdeluxe_delayed_data_w = new timer_callback() {
        @Override
        public void handler(int param) {
            pia_0_portb_w.handler(0, param & 0x0f);
            pia_0_ca1_w.handler(0, ~param & 0x10);
        }
    };
    /**
     * ******* external interfaces **********
     */
    public static WriteHandlerPtr csdeluxe_data_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            timer_set(TIME_NOW, data, csdeluxe_delayed_data_w);
        }
    };

    public static void csdeluxe_reset_w(int state) {
        cpu_set_reset_line(u8_csdeluxe_sound_cpu, state != 0 ? ASSERT_LINE : CLEAR_LINE);
    }

    /**
     * ******* sound interfaces **********
     */
    public static DACinterface mcr_dac_interface = new DACinterface(
            1,
            new int[]{100}
    );

    public static DACinterface mcr_dual_dac_interface = new DACinterface(
            2,
            new int[]{75, 75}
    );

    /**
     * ******* memory interfaces **********
     */
    public static MemoryReadAddress csdeluxe_readmem[]
            = {
                new MemoryReadAddress(0x000000, 0x007fff, MRA_ROM),
                new MemoryReadAddress(0x018000, 0x018007, pia_0_r),
                new MemoryReadAddress(0x01c000, 0x01cfff, MRA_BANK1),
                new MemoryReadAddress(-1) /* end of table */};

    public static MemoryWriteAddress csdeluxe_writemem[]
            = {
                new MemoryWriteAddress(0x000000, 0x007fff, MWA_ROM),
                new MemoryWriteAddress(0x018000, 0x018007, pia_0_w),
                new MemoryWriteAddress(0x01c000, 0x01cfff, MWA_BANK1),
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * ******* PIA interfaces **********
     */
    static pia6821_interface csdeluxe_pia_intf = new pia6821_interface(
            /*inputs : A/B,CA/B1,CA/B2 */null, null, null, null, null, null,
            /*outputs: A/B,CA/B2       */ csdeluxe_porta_w, csdeluxe_portb_w, null, null,
            /*irqs   : A/B             */ csdeluxe_irq, csdeluxe_irq
    );

    /**
     * ***********************************
     *
     * MCR Sounds Good communications
     *
     * MC68000, 1 PIA, 10-bit DAC
     *
     ************************************
     */
    /**
     * ******* internal interfaces **********
     */
    public static WriteHandlerPtr soundsgood_porta_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            dacval = (char) ((dacval & ~0x3fc) | (data << 2));
            DAC_signed_data_16_w.handler(u8_soundsgood_dac_index, dacval << 6);
        }
    };

    public static WriteHandlerPtr soundsgood_portb_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            dacval = (char) ((dacval & ~0x003) | (data >> 6));
            DAC_signed_data_16_w.handler(u8_soundsgood_dac_index, dacval << 6);
            u8_soundsgood_status = (data >> 4) & 3;
        }
    };

    static IrqfuncPtr soundsgood_irq = new IrqfuncPtr() {
        @Override
        public void handler(int state) {
            cpu_set_irq_line(u8_soundsgood_sound_cpu, 4, state != 0 ? ASSERT_LINE : CLEAR_LINE);
        }
    };

    static timer_callback soundsgood_delayed_data_w = new timer_callback() {
        @Override
        public void handler(int param) {
            pia_1_portb_w.handler(0, (param >> 1) & 0x0f);
            pia_1_ca1_w.handler(0, ~param & 0x01);
        }
    };
    /**
     * ******* external interfaces **********
     */
    public static WriteHandlerPtr soundsgood_data_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            timer_set(TIME_NOW, data, soundsgood_delayed_data_w);
        }
    };

    public static ReadHandlerPtr soundsgood_status_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return u8_soundsgood_status;
        }
    };

    public static void soundsgood_reset_w(int state) {
        cpu_set_reset_line(u8_soundsgood_sound_cpu, state != 0 ? ASSERT_LINE : CLEAR_LINE);
    }

    /**
     * ******* sound interfaces **********
     */
    static DACinterface turbocs_plus_soundsgood_dac_interface = new DACinterface(
            2,
            new int[]{80, 80}
    );

    /**
     * ******* memory interfaces **********
     */
    static MemoryReadAddress soundsgood_readmem[]
            = {
                new MemoryReadAddress(0x000000, 0x03ffff, MRA_ROM),
                new MemoryReadAddress(0x060000, 0x060007, pia_1_r),
                new MemoryReadAddress(0x070000, 0x070fff, MRA_BANK1),
                new MemoryReadAddress(-1) /* end of table */};

    static MemoryWriteAddress soundsgood_writemem[]
            = {
                new MemoryWriteAddress(0x000000, 0x03ffff, MWA_ROM),
                new MemoryWriteAddress(0x060000, 0x060007, pia_1_w),
                new MemoryWriteAddress(0x070000, 0x070fff, MWA_BANK1),
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * ******* PIA interfaces **********
     */
    /* Note: we map this board to PIA #1. It is only used in Spy Hunter and Spy Hunter 2 */
 /* For Spy Hunter 2, we also have a Turbo Chip Squeak in PIA slot 0, so we don't want */
 /* to interfere */
    static pia6821_interface soundsgood_pia_intf = new pia6821_interface(
            /*inputs : A/B,CA/B1,CA/B2 */null, null, null, null, null, null,
            /*outputs: A/B,CA/B2       */ soundsgood_porta_w, soundsgood_portb_w, null, null,
            /*irqs   : A/B             */ soundsgood_irq, soundsgood_irq
    );

    /**
     * ***********************************
     *
     * MCR Turbo Chip Squeak communications
     *
     * MC6809, 1 PIA, 8-bit DAC
     *
     ************************************
     */
    /**
     * ******* internal interfaces **********
     */
    public static WriteHandlerPtr turbocs_porta_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            dacval = (char) ((dacval & ~0x3fc) | (data << 2));
            DAC_signed_data_16_w.handler(u8_turbocs_dac_index, dacval << 6);
        }
    };

    public static WriteHandlerPtr turbocs_portb_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            dacval = (char) ((dacval & ~0x003) | (data >> 6));
            DAC_signed_data_16_w.handler(u8_turbocs_dac_index, dacval << 6);
            u8_turbocs_status = (data >> 4) & 3;
        }
    };

    public static IrqfuncPtr turbocs_irq = new IrqfuncPtr() {
        public void handler(int state) {
            cpu_set_irq_line(u8_turbocs_sound_cpu, M6809_IRQ_LINE, state != 0 ? ASSERT_LINE : CLEAR_LINE);
        }
    };
    public static timer_callback turbocs_delayed_data_w = new timer_callback() {
        public void handler(int param) {
            pia_0_portb_w.handler(0, (param >> 1) & 0x0f);
            pia_0_ca1_w.handler(0, ~param & 0x01);
        }
    };

    /**
     * ******* external interfaces **********
     */
    public static WriteHandlerPtr turbocs_data_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            timer_set(TIME_NOW, data, turbocs_delayed_data_w);
        }
    };

    public static ReadHandlerPtr turbocs_status_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return u8_turbocs_status;
        }
    };

    static void turbocs_reset_w(int state) {
        cpu_set_reset_line(u8_turbocs_sound_cpu, state != 0 ? ASSERT_LINE : CLEAR_LINE);
    }

    /**
     * ******* memory interfaces **********
     */
    public static MemoryReadAddress turbocs_readmem[]
            = {
                new MemoryReadAddress(0x0000, 0x07ff, MRA_RAM),
                new MemoryReadAddress(0x4000, 0x4003, pia_0_r), /* Max RPM accesses the PIA here */
                new MemoryReadAddress(0x6000, 0x6003, pia_0_r),
                new MemoryReadAddress(0x8000, 0xffff, MRA_ROM),
                new MemoryReadAddress(-1) /* end of table */};

    public static MemoryWriteAddress turbocs_writemem[]
            = {
                new MemoryWriteAddress(0x0000, 0x07ff, MWA_RAM),
                new MemoryWriteAddress(0x4000, 0x4003, pia_0_w), /* Max RPM accesses the PIA here */
                new MemoryWriteAddress(0x6000, 0x6003, pia_0_w),
                new MemoryWriteAddress(0x8000, 0xffff, MWA_ROM),
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * ******* PIA interfaces **********
     */
    static pia6821_interface turbocs_pia_intf = new pia6821_interface(
            /*inputs : A/B,CA/B1,CA/B2 */null, null, null, null, null, null,
            /*outputs: A/B,CA/B2       */ turbocs_porta_w, turbocs_portb_w, null, null,
            /*irqs   : A/B             */ turbocs_irq, turbocs_irq
    );

    /**
     * ***********************************
     *
     * MCR Squawk n Talk communications
     *
     * MC6802, 2 PIAs, TMS5220, AY8912 (not used), 8-bit DAC (not used)
     *
     ************************************
     */
    /**
     * ******* internal interfaces **********
     */
    public static WriteHandlerPtr squawkntalk_porta1_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            logerror("Write to AY-8912\n");
        }
    };

    public static WriteHandlerPtr squawkntalk_porta2_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            u8_squawkntalk_tms_command = data & 0xFF;
        }
    };

    public static WriteHandlerPtr squawkntalk_portb2_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* bits 0-1 select read/write strobes on the TMS5220 */
            data &= 0x03;

            /* write strobe -- pass the current command to the TMS5220 */
            if (((data ^ u8_squawkntalk_tms_strobes) & 0x02) != 0 && (data & 0x02) == 0) {
                tms5220_data_w.handler(offset, u8_squawkntalk_tms_command);

                /* DoT expects the ready line to transition on a command/write here, so we oblige */
                pia_1_ca2_w.handler(0, 1);
                pia_1_ca2_w.handler(0, 0);
            } /* read strobe -- read the current status from the TMS5220 */ else if (((data ^ u8_squawkntalk_tms_strobes) & 0x01) != 0 && (data & 0x01) == 0) {
                pia_1_porta_w.handler(0, tms5220_status_r.handler(offset));

                /* DoT expects the ready line to transition on a command/write here, so we oblige */
                pia_1_ca2_w.handler(0, 1);
                pia_1_ca2_w.handler(0, 0);
            }

            /* remember the state */
            u8_squawkntalk_tms_strobes = data & 0xFF;
        }
    };
    public static IrqfuncPtr squawkntalk_irq = new IrqfuncPtr() {
        public void handler(int state) {
            cpu_set_irq_line(u8_squawkntalk_sound_cpu, M6800_IRQ_LINE, state != 0 ? ASSERT_LINE : CLEAR_LINE);
        }
    };

    static timer_callback squawkntalk_delayed_data_w = new timer_callback() {
        @Override
        public void handler(int param) {
            pia_0_porta_w.handler(0, ~param & 0x0f);
            pia_0_cb1_w.handler(0, ~param & 0x10);
        }
    };
    /**
     * ******* external interfaces **********
     */
    public static WriteHandlerPtr squawkntalk_data_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            timer_set(TIME_NOW, data, squawkntalk_delayed_data_w);
        }
    };

    public static void squawkntalk_reset_w(int state) {
        cpu_set_reset_line(u8_squawkntalk_sound_cpu, state != 0 ? ASSERT_LINE : CLEAR_LINE);
    }

    /**
     * ******* sound interfaces **********
     */
    static TMS5220interface squawkntalk_tms5220_interface = new TMS5220interface(
            640000,
            MIXER(60, MIXER_PAN_LEFT),
            null
    );

    /**
     * ******* memory interfaces **********
     */
    static MemoryReadAddress squawkntalk_readmem[]
            = {
                new MemoryReadAddress(0x0000, 0x007f, MRA_RAM),
                new MemoryReadAddress(0x0080, 0x0083, pia_0_r),
                new MemoryReadAddress(0x0090, 0x0093, pia_1_r),
                new MemoryReadAddress(0xd000, 0xffff, MRA_ROM),
                new MemoryReadAddress(-1) /* end of table */};

    static MemoryWriteAddress squawkntalk_writemem[]
            = {
                new MemoryWriteAddress(0x0000, 0x007f, MWA_RAM),
                new MemoryWriteAddress(0x0080, 0x0083, pia_0_w),
                new MemoryWriteAddress(0x0090, 0x0093, pia_1_w),
                new MemoryWriteAddress(0xd000, 0xffff, MWA_ROM),
                new MemoryWriteAddress(-1) /* end of table */};

    /**
     * ******* PIA interfaces **********
     */
    static pia6821_interface squawkntalk_pia0_intf = new pia6821_interface(
            /*inputs : A/B,CA/B1,CA/B2 */null, null, null, null, null, null,
            /*outputs: A/B,CA/B2       */ squawkntalk_porta1_w, null, null, null,
            /*irqs   : A/B             */ squawkntalk_irq, squawkntalk_irq
    );
    static pia6821_interface squawkntalk_pia1_intf = new pia6821_interface(
            /*inputs : A/B,CA/B1,CA/B2 */null, null, null, null, null, null,
            /*outputs: A/B,CA/B2       */ squawkntalk_porta2_w, squawkntalk_portb2_w, null, null,
            /*irqs   : A/B             */ squawkntalk_irq, squawkntalk_irq
    );
}
