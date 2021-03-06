/*
 * ported to v0.37b7 
*/
package arcadeflex.v037b7.sound;

//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//sound imports
import static arcadeflex.v037b7.sound.ay8910H.*;

public class _2203intfH {

    public static final int MAX_2203 = 4;

    /* volume level for YM2203 */
    public static int YM2203_VOL(int FM_VOLUME, int SSG_VOLUME) {
        return (((FM_VOLUME) << 16) + (SSG_VOLUME));
    }

    public static class YM2203interface extends AY8910interface {

        public YM2203interface(int num, int baseclock, int[] mixing_level, ReadHandlerPtr[] pAr, ReadHandlerPtr[] pBr, WriteHandlerPtr[] pAw, WriteHandlerPtr[] pBw, WriteYmHandlerPtr[] ym_handler) {
            super(num, baseclock, mixing_level, pAr, pBr, pAw, pBw, ym_handler);
        }

        public YM2203interface(int num, int baseclock, int[] mixing_level, ReadHandlerPtr[] pAr, ReadHandlerPtr[] pBr, WriteHandlerPtr[] pAw, WriteHandlerPtr[] pBw) {
            super(num, baseclock, mixing_level, pAr, pBr, pAw, pBw);
        }
    }
}