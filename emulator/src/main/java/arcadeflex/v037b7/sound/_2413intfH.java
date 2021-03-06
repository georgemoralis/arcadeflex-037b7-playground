/*
 * ported to v0.37b7
 *
 */
package arcadeflex.v037b7.sound;

//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//sound imports
import static arcadeflex.v037b7.sound._3812intfH.*;

public class _2413intfH {

    public static final int MAX_2413 = MAX_3812;

    public static class YM2413interface extends YM3812interface {

        public YM2413interface(int num, int baseclock, int[] mixing_level, WriteYmHandlerPtr[] handler) {
            super(num, baseclock, mixing_level, handler);
        }

        public YM2413interface(int num, int baseclock, int[] mixing_level) {
            super(num, baseclock, mixing_level);
        }
    }
}
