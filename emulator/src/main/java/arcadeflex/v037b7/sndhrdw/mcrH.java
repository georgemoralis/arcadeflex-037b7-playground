/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.sndhrdw;

//mame imports
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
//sndhrdw imports
import static arcadeflex.v037b7.sndhrdw.mcr.*;

public class mcrH {

    /**
     * ********** Sound Configuration **************
     */
    public static final int MCR_SSIO = 0x01;
    public static final int MCR_CHIP_SQUEAK_DELUXE = 0x02;
    public static final int MCR_SOUNDS_GOOD = 0x04;
    public static final int MCR_TURBO_CHIP_SQUEAK = 0x08;
    public static final int MCR_SQUAWK_N_TALK = 0x10;
    public static final int MCR_WILLIAMS_SOUND = 0x20;

    public static void MCR_CONFIGURE_SOUND(int x) {
        mcr_sound_config = x;
    }

    /**
     * ********** SSIO CPU and sound definitions **************
     */
    public static MachineCPU SOUND_CPU_SSIO = new MachineCPU(
            CPU_Z80 | CPU_AUDIO_CPU,
            2000000, /* 2 MHz */
            ssio_readmem, ssio_writemem, null, null,
            interrupt, 26
    );

    public static MachineSound SOUND_SSIO = new MachineSound(
            SOUND_AY8910,
            ssio_ay8910_interface
    );

    /**
     * ********** Chip Squeak Deluxe CPU and sound definitions **************
     */
    public static MachineCPU SOUND_CPU_CHIP_SQUEAK_DELUXE = new MachineCPU(
            CPU_M68000 | CPU_AUDIO_CPU,
            15000000 / 2, /* 7.5 MHz */
            csdeluxe_readmem, csdeluxe_writemem, null, null,
            ignore_interrupt, 1
    );

    public static MachineSound SOUND_CHIP_SQUEAK_DELUXE = new MachineSound(
            SOUND_DAC,
            mcr_dac_interface
    );

    /**
     * ********** Sounds Good CPU and sound definitions **************
     */
    public static MachineCPU SOUND_CPU_SOUNDS_GOOD = new MachineCPU(
            CPU_M68000 | CPU_AUDIO_CPU,
            16000000 / 2,
            soundsgood_readmem, soundsgood_writemem, null, null,
            ignore_interrupt, 1
    );

    public static MachineSound SOUND_SOUNDS_GOOD = new MachineSound(
            SOUND_DAC,
            mcr_dac_interface
    );

    /**
     * ********** Turbo Chip Squeak CPU and sound definitions **************
     */
    public static MachineCPU SOUND_CPU_TURBO_CHIP_SQUEAK = new MachineCPU(
            CPU_M6809 | CPU_AUDIO_CPU,
            9000000 / 4, /* 2.25 MHz */
            turbocs_readmem, turbocs_writemem, null, null,
            ignore_interrupt, 1
    );

    public static MachineSound SOUND_TURBO_CHIP_SQUEAK = new MachineSound(
            SOUND_DAC,
            mcr_dac_interface
    );
    /*TODO*///
    /*TODO*///#define SOUND_CPU_TURBO_CHIP_SQUEAK_PLUS_SOUNDS_GOOD \
    /*TODO*///	SOUND_CPU_TURBO_CHIP_SQUEAK,					\
    /*TODO*///	SOUND_CPU_SOUNDS_GOOD
    /*TODO*///
    /*TODO*///#define SOUND_TURBO_CHIP_SQUEAK_PLUS_SOUNDS_GOOD	\
    /*TODO*///	{												\
    /*TODO*///		SOUND_DAC,									\
    /*TODO*///		&mcr_dual_dac_interface						\
    /*TODO*///	}
    /*TODO*///
    /*TODO*///

    /**
     * ********** Squawk & Talk CPU and sound definitions **************
     */
    public static MachineCPU SOUND_CPU_SQUAWK_N_TALK = new MachineCPU(
            CPU_M6802 | CPU_AUDIO_CPU,
            3580000 / 4, /* .8 MHz */
            squawkntalk_readmem, squawkntalk_writemem, null, null,
            ignore_interrupt, 1
    );

    public static MachineSound SOUND_SQUAWK_N_TALK = new MachineSound(
            SOUND_TMS5220,
            squawkntalk_tms5220_interface
    );
}
