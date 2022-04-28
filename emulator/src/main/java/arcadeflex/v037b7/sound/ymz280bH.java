/**********************************************************************************************
 *
 *   Yamaha YMZ280B driver
 *   by Aaron Giles
 *
 **********************************************************************************************/

package arcadeflex.v037b7.sound;

import static arcadeflex.v037b7.generic.funcPtr.*;
import arcadeflex.v037b7.mame.cpuintrfH.irqcallbacksPtr;

public class ymz280bH {

    public static final int MAX_YMZ280B = 2;

    public static class YMZ280Binterface
    {
            int num;                  						/* total number of chips */
            int baseclock; /* = new int[MAX_YMZ280B];*/						/* input clock */
            int[] region = new int[MAX_YMZ280B];						/* memory region where the sample ROM lives */
            int[] mixing_level=new int[MAX_YMZ280B];					/* master volume */
            irqcallbacksPtr[] irq_callback = new irqcallbacksPtr[MAX_YMZ280B];	/* irq callback */

        public YMZ280Binterface(int num, int baseclock, int[] region, int[] mixing_level, irqcallbacksPtr[] irq_callback) {
            this.num = num;
            this.baseclock = baseclock;
            this.region = region;
            this.mixing_level = mixing_level;
            this.irq_callback = irq_callback;
        }
    };

}
