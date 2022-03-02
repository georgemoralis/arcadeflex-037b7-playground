/*
 * ported to 0.37b7 
 */
package arcadeflex.v037b7.mame;

//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v056.mame.timer.*;
import static arcadeflex.v056.mame.timerH.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.driverH.*;
//sound imports
import static arcadeflex.v037b7.sound.streams.*;
import arcadeflex.v037b7.sound.CustomSound;
import arcadeflex.v037b7.sound.Dummy_snd;
import arcadeflex.v037b7.sound._5110intf;
import arcadeflex.v037b7.sound._5220intf;
import arcadeflex.v037b7.sound.ay8910;
import arcadeflex.v037b7.sound.dac;
import arcadeflex.v037b7.sound.k053260;
import arcadeflex.v058.sound.sn76496;
import arcadeflex.v058.sound.tms36xx;
import arcadeflex.v058.sound.vlm5030;
import arcadeflex.v037b7.sound.samples;
import arcadeflex.v037b7.sound.sn76477;
import arcadeflex.v037b7.sound.okim6295;
import arcadeflex.v037b7.sound._2203intf;
import arcadeflex.v037b7.sound._2608intf;
import arcadeflex.v037b7.sound._2610intf;
import arcadeflex.v037b7.sound._3526intf;
import arcadeflex.v037b7.sound._3812intf;
import arcadeflex.v037b7.sound.y8950intf;
import arcadeflex.v037b7.sound.ym2413;
import arcadeflex.v037b7.sound.MSM5205;
import arcadeflex.v037b7.sound._2151intf;
import arcadeflex.v037b7.sound.cem3394;
import arcadeflex.v037b7.sound.namco;
import arcadeflex.v037b7.sound.rf5c68;
import arcadeflex.v037b7.sound._2612intf;
import arcadeflex.v037b7.sound.qsound;
import arcadeflex.v037b7.sound.segapcm;
//to be orgnanized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import gr.codebb.arcadeflex.WIP.v037b7.sound.adpcm;
import gr.codebb.arcadeflex.WIP.v037b7.sound.astrocde;
import gr.codebb.arcadeflex.WIP.v037b7.sound.hc55516;
import gr.codebb.arcadeflex.WIP.v037b7.sound.k007232;
import gr.codebb.arcadeflex.WIP.v037b7.sound.nes_apu;
import gr.codebb.arcadeflex.WIP.v037b7.sound.pokey;
import gr.codebb.arcadeflex.WIP.v037b7.sound.upd7759;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.old.sound.mixer.mixer_sh_start;
import static gr.codebb.arcadeflex.old.sound.mixer.mixer_sh_stop;
import static gr.codebb.arcadeflex.old.sound.mixer.mixer_sh_update;

public class sndintrf {

    /**
     * *************************************************************************
     *
     * Many games use a master-slave CPU setup. Typically, the main CPU writes a
     * command to some register, and then writes to another register to trigger
     * an interrupt on the slave CPU (the interrupt might also be triggered by
     * the first write). The slave CPU, notified by the interrupt, goes and
     * reads the command.
     *
     **************************************************************************
     */
    static int cleared_value = 0x00;

    static int latch, read_debug;

    public static timer_callback soundlatch_callback = new timer_callback() {
        public void handler(int param) {
            if (read_debug == 0 && latch != param) {
                logerror("Warning: sound latch written before being read. Previous: %02x, new: %02x\n", latch, param);
            }
            latch = param;
            read_debug = 0;
        }
    };
    public static WriteHandlerPtr soundlatch_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* make all the CPUs synchronize, and only AFTER that write the new command to the latch */
            timer_set(TIME_NOW, data, soundlatch_callback);
        }
    };

    public static ReadHandlerPtr soundlatch_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            read_debug = 1;
            return latch;
        }
    };

    public static WriteHandlerPtr soundlatch_clear_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            latch = cleared_value;
        }
    };

    static int latch2, read_debug2;

    public static timer_callback soundlatch2_callback = new timer_callback() {
        public void handler(int param) {
            if (read_debug2 == 0 && latch2 != param) {
                logerror("Warning: sound latch 2 written before being read. Previous: %02x, new: %02x\n", latch2, param);
            }
            latch2 = param;
            read_debug2 = 0;
        }
    };

    public static WriteHandlerPtr soundlatch2_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* make all the CPUs synchronize, and only AFTER that write the new command to the latch */
            timer_set(TIME_NOW, data, soundlatch2_callback);
        }
    };

    public static ReadHandlerPtr soundlatch2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            read_debug2 = 1;
            return latch2;
        }
    };

    public static WriteHandlerPtr soundlatch2_clear_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            latch2 = cleared_value;
        }
    };

    static int latch3, read_debug3;

    public static timer_callback soundlatch3_callback = new timer_callback() {
        public void handler(int param) {
            if (read_debug3 == 0 && latch3 != param) {
                logerror("Warning: sound latch 3 written before being read. Previous: %02x, new: %02x\n", latch3, param);
            }
            latch3 = param;
            read_debug3 = 0;
        }
    };

    public static WriteHandlerPtr soundlatch3_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* make all the CPUs synchronize, and only AFTER that write the new command to the latch */
            timer_set(TIME_NOW, data, soundlatch3_callback);
        }
    };

    public static ReadHandlerPtr soundlatch3_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            read_debug3 = 1;
            return latch3;
        }
    };

    public static WriteHandlerPtr soundlatch3_clear_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            latch3 = cleared_value;
        }
    };

    static int latch4, read_debug4;

    public static timer_callback soundlatch4_callback = new timer_callback() {
        public void handler(int param) {
            if (read_debug4 == 0 && latch4 != param) {
                logerror("Warning: sound latch 4 written before being read. Previous: %02x, new: %02x\n", latch2, param);
            }
            latch4 = param;
            read_debug4 = 0;
        }
    };

    public static WriteHandlerPtr soundlatch4_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            /* make all the CPUs synchronize, and only AFTER that write the new command to the latch */
            timer_set(TIME_NOW, data, soundlatch4_callback);
        }
    };

    public static ReadHandlerPtr soundlatch4_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            read_debug4 = 1;
            return latch4;
        }
    };

    public static WriteHandlerPtr soundlatch4_clear_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            latch4 = cleared_value;
        }
    };

    void soundlatch_setclearedvalue(int value) {
        cleared_value = value;
    }

    /**
     * *************************************************************************
     *
     *
     *
     **************************************************************************
     */
    static timer_entry sound_update_timer;
    static double refresh_period;
    static double refresh_period_inv;

    public static abstract class snd_interface {

        public int sound_num;/* ID */
        public String name;/* description */
        public abstract int chips_num(MachineSound msound);/* returns number of chips if applicable */
        public abstract int chips_clock(MachineSound msound);/* returns chips clock if applicable */
        public abstract int start(MachineSound msound);/* starts sound emulation */
        public abstract void stop();/* stops sound emulation */
        public abstract void update();/* updates emulation once per frame if necessary */
        public abstract void reset();/* resets sound emulation */
    }

    /*TODO*///#if (HAS_ADPCM)
/*TODO*///int ADPCM_num(const struct MachineSound *msound) { return ((struct ADPCMinterface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_HC55516)
/*TODO*///int HC55516_num(const struct MachineSound *msound) { return ((struct hc55516_interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_K007232)
/*TODO*///int K007232_num(const struct MachineSound *msound) { return ((struct K007232_interface*)msound->sound_interface)->num_chips; }
/*TODO*///#endif
/*TODO*///#if (HAS_YM2203)
/*TODO*///int YM2203_clock(const struct MachineSound *msound) { return ((struct YM2203interface*)msound->sound_interface)->baseclock; }
/*TODO*///int YM2203_num(const struct MachineSound *msound) { return ((struct YM2203interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_YM2413)
/*TODO*///int YM2413_clock(const struct MachineSound *msound) { return ((struct YM2413interface*)msound->sound_interface)->baseclock; }
/*TODO*///int YM2413_num(const struct MachineSound *msound) { return ((struct YM2413interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_YM2608)
/*TODO*///int YM2608_clock(const struct MachineSound *msound) { return ((struct YM2608interface*)msound->sound_interface)->baseclock; }
/*TODO*///int YM2608_num(const struct MachineSound *msound) { return ((struct YM2608interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_YM2610)
/*TODO*///int YM2610_clock(const struct MachineSound *msound) { return ((struct YM2610interface*)msound->sound_interface)->baseclock; }
/*TODO*///int YM2610_num(const struct MachineSound *msound) { return ((struct YM2610interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_YM2612 || HAS_YM3438)
/*TODO*///int YM2612_clock(const struct MachineSound *msound) { return ((struct YM2612interface*)msound->sound_interface)->baseclock; }
/*TODO*///int YM2612_num(const struct MachineSound *msound) { return ((struct YM2612interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_POKEY)
/*TODO*///int POKEY_clock(const struct MachineSound *msound) { return ((struct POKEYinterface*)msound->sound_interface)->baseclock; }
/*TODO*///int POKEY_num(const struct MachineSound *msound) { return ((struct POKEYinterface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_YM3812)
/*TODO*///int YM3812_clock(const struct MachineSound *msound) { return ((struct YM3812interface*)msound->sound_interface)->baseclock; }
/*TODO*///int YM3812_num(const struct MachineSound *msound) { return ((struct YM3812interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_YMZ280B)
/*TODO*///int YMZ280B_clock(const struct MachineSound *msound) { return ((struct YMZ280Binterface*)msound->sound_interface)->baseclock[0]; }
/*TODO*///int YMZ280B_num(const struct MachineSound *msound) { return ((struct YMZ280Binterface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_YM2151 || HAS_YM2151_ALT)
/*TODO*///int YM2151_clock(const struct MachineSound *msound) { return ((struct YM2151interface*)msound->sound_interface)->baseclock; }
/*TODO*///int YM2151_num(const struct MachineSound *msound) { return ((struct YM2151interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_NES)
/*TODO*///int NES_num(const struct MachineSound *msound) { return ((struct NESinterface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_SN76477)
/*TODO*///int SN76477_num(const struct MachineSound *msound) { return ((struct SN76477interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_UPD7759)
/*TODO*///int UPD7759_clock(const struct MachineSound *msound) { return ((struct UPD7759_interface*)msound->sound_interface)->clock_rate; }
/*TODO*///#endif
/*TODO*///#if (HAS_ASTROCADE)
/*TODO*///int ASTROCADE_clock(const struct MachineSound *msound) { return ((struct astrocade_interface*)msound->sound_interface)->baseclock; }
/*TODO*///int ASTROCADE_num(const struct MachineSound *msound) { return ((struct astrocade_interface*)msound->sound_interface)->num; }
/*TODO*///#endif
/*TODO*///#if (HAS_K051649)
/*TODO*///int K051649_clock(const struct MachineSound *msound) { return ((struct k051649_interface*)msound->sound_interface)->master_clock; }
/*TODO*///#endif
/*TODO*///#if (HAS_CEM3394)
/*TODO*///int cem3394_num(const struct MachineSound *msound) { return ((struct cem3394_interface*)msound->sound_interface)->numchips; }
/*TODO*///#endif
/*TODO*///#if (HAS_QSOUND)
/*TODO*///int qsound_clock(const struct MachineSound *msound) { return ((struct QSound_interface*)msound->sound_interface)->clock; }
/*TODO*///#endif
/*TODO*///#if (HAS_SAA1099)
/*TODO*///int saa1099_num(const struct MachineSound *msound) { return ((struct SAA1099_interface*)msound->sound_interface)->numchips; }
/*TODO*///#endif
    public static snd_interface sndintf[] = {
        new Dummy_snd(),
        new CustomSound(),
        new samples(),
        new dac(),
        new ay8910(),
        new _2203intf(),
        new _2151intf(),
        new _2608intf(),
        new _2610intf(),
        new Dummy_snd(),
        /*TODO*///#if (HAS_YM2610B)
        /*TODO*///    {
        /*TODO*///		SOUND_YM2610B,
        /*TODO*///		"YM-2610B",
        /*TODO*///		YM2610_num,
        /*TODO*///		YM2610_clock,
        /*TODO*///		YM2610B_sh_start,
        /*TODO*///		YM2610_sh_stop,
        /*TODO*///		0,
        /*TODO*///		YM2610_sh_reset
        /*TODO*///	},
        /*TODO*///#endif
        new _2612intf(),        
        new _2612intf(),        
        new ym2413(),
        new _3812intf(),
        new _3526intf(),
        new Dummy_snd(),
        /*TODO*///#if (HAS_YMZ280B)
        /*TODO*///    {
        /*TODO*///		SOUND_YMZ280B,
        /*TODO*///		"YMZ280B",
        /*TODO*///		YMZ280B_num,
        /*TODO*///		YMZ280B_clock,
        /*TODO*///		YMZ280B_sh_start,
        /*TODO*///		YMZ280B_sh_stop,
        /*TODO*///		0,
        /*TODO*///		0
        /*TODO*///	},
        /*TODO*///#endif
        new y8950intf(),
        new sn76477(),
        new sn76496(),
        new pokey(),
        new nes_apu(),
        new astrocde(),
        new namco(),
        new tms36xx(),
        new _5110intf(),
        new _5220intf(),
        new vlm5030(),
        new adpcm(),
        new okim6295(),
        new MSM5205(),
        new upd7759(),
        new hc55516(),
        new Dummy_snd(),
        /*TODO*///#if (HAS_K005289)
        /*TODO*///    {
        /*TODO*///		SOUND_K005289,
        /*TODO*///		"005289",
        /*TODO*///		0,
        /*TODO*///		0,
        /*TODO*///		K005289_sh_start,
        /*TODO*///		K005289_sh_stop,
        /*TODO*///		0,
        /*TODO*///		0
        /*TODO*///	},
        /*TODO*///#endif
        new k007232(),
        new Dummy_snd(),
        /*TODO*///#if (HAS_K051649)
        /*TODO*///    {
        /*TODO*///		SOUND_K051649,
        /*TODO*///		"051649",
        /*TODO*///		0,
        /*TODO*///		K051649_clock,
        /*TODO*///		K051649_sh_start,
        /*TODO*///		K051649_sh_stop,
        /*TODO*///		0,
        /*TODO*///		0
        /*TODO*///	},
        /*TODO*///#endif
        new k053260(),
        new segapcm(),        
        new rf5c68(),        
        new cem3394(),
        new Dummy_snd(),
        /*TODO*///#if (HAS_C140)
        /*TODO*///	{
        /*TODO*///		SOUND_C140,
        /*TODO*///		"C140",
        /*TODO*///		0,
        /*TODO*///		0,
        /*TODO*///		C140_sh_start,
        /*TODO*///		C140_sh_stop,
        /*TODO*///		0,
        /*TODO*///		0
        /*TODO*///	},
        /*TODO*///#endif
        new qsound(),
        new Dummy_snd(), /*TODO*///#if (HAS_SAA1099)
    /*TODO*///	{
    /*TODO*///		SOUND_SAA1099,
    /*TODO*///		"SAA1099",
    /*TODO*///		saa1099_num,
    /*TODO*///		0,
    /*TODO*///		saa1099_sh_start,
    /*TODO*///		saa1099_sh_stop,
    /*TODO*///		0,
    /*TODO*///		0
    /*TODO*///	},
    /*TODO*///#endif
    };

    public static int sound_start() {
        int totalsound = 0;
        int i;
        /* samples will be read later if needed */
        Machine.samples = null;

        refresh_period = TIME_IN_HZ(Machine.drv.frames_per_second);
        refresh_period_inv = 1.0 / refresh_period;
        sound_update_timer = timer_set(TIME_NEVER, 0, null);
        if (mixer_sh_start() != 0) {
            return 1;
        }

        if (streams_sh_start() != 0) {
            return 1;
        }

        while (totalsound < MAX_SOUND && Machine.drv.sound[totalsound].sound_type != 0) {
            if ((sndintf[Machine.drv.sound[totalsound].sound_type].start(Machine.drv.sound[totalsound]))
                    != 0) {
                return 1; // goto getout;
            }
            totalsound++;
        }
        return 0;
    }

    public static void sound_stop() {
        int totalsound = 0;

        while (totalsound < MAX_SOUND && Machine.drv.sound[totalsound].sound_type != 0) {
            sndintf[Machine.drv.sound[totalsound].sound_type].stop();
            totalsound++;
        }

        streams_sh_stop();
        mixer_sh_stop();

        if (sound_update_timer != null) {
            timer_remove(sound_update_timer);
            sound_update_timer = null;
        }

        /* free audio samples */
        freesamples(Machine.samples);
        Machine.samples = null;
    }

    public static void sound_update() {
        int totalsound = 0;

        while (totalsound < MAX_SOUND && Machine.drv.sound[totalsound].sound_type != 0) {
            sndintf[Machine.drv.sound[totalsound].sound_type].update();
            totalsound++;
        }

        streams_sh_update();
        mixer_sh_update();

        timer_reset(sound_update_timer, TIME_NEVER);
    }

    public static void sound_reset() {
        int totalsound = 0;

        while (totalsound < MAX_SOUND && Machine.drv.sound[totalsound].sound_type != 0) {
            sndintf[Machine.drv.sound[totalsound].sound_type].reset();
            totalsound++;
        }
    }

    public static String sound_name(MachineSound msound) {
        if (msound.sound_type < SOUND_COUNT) {
            return sndintf[msound.sound_type].name;
        } else {
            return "";
        }
    }

    public static int sound_num(MachineSound msound) {
        if (msound.sound_type < SOUND_COUNT && sndintf[msound.sound_type].chips_num(msound) != 0) {
            return sndintf[msound.sound_type].chips_num(msound);
        } else {
            return 0;
        }
    }

    public static int sound_clock(MachineSound msound) {
        if (msound.sound_type < SOUND_COUNT && sndintf[msound.sound_type].chips_clock(msound) != 0) {
            return sndintf[msound.sound_type].chips_clock(msound);
        } else {
            return 0;
        }
    }

    public static int sound_scalebufferpos(int value) {
        int result = (int) ((double) value * timer_timeelapsed(sound_update_timer) * refresh_period_inv);
        if (value >= 0) {
            return (result < value) ? result : value;
        } else {
            return (result > value) ? result : value;
        }
    }
}
