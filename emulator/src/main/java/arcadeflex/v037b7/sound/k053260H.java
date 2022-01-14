/*
 * ported to v0.37b7
 * ported to v0.36
 */
package arcadeflex.v037b7.sound;

import static gr.codebb.arcadeflex.v056.mame.timer.*;

/**
 * *******************************************************
 *
 * Konami 053260 PCM/ADPCM Sound Chip
 *
 ********************************************************
 */
public class k053260H {

    public static class K053260_interface {

        public K053260_interface(int clock, int region, int[] mixing_level, timer_callback irq) {
            this.clock = clock;
            this.region = region;
            this.mixing_level = mixing_level;
            this.irq = irq;
        }
        int clock;
        int region;
        int[] mixing_level;
        timer_callback irq;
    }
}
