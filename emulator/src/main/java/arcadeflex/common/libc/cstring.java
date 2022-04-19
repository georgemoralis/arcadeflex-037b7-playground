package arcadeflex.common.libc;

import static arcadeflex.common.ptrLib.*;
import arcadeflex.common.subArrays.IntSubArray;
import arcadeflex.common.subArrays.UShortArray;

/**
 *
 * @author shadow
 */
public class cstring {

    /**
     * Get string length
     *
     * @param str
     * @return
     */
    public static int strlen(String str) {
        return str.length();
    }

    /**
     * memcpy
     */
    public static void memcpy(char[] dst, UBytePtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst[i] = src.read(i);
        }
    }

    public static void memcpy(UBytePtr dst, char[] src, int size) {
        for (int i = 0; i < Math.min(size, src.length); i++) {
            dst.write(i, src[i]);
        }
    }

    public static void memcpy(UBytePtr dst, UBytePtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i, src.read(i));
        }
    }
    
    public static void memcpy(UShortPtr dst, UBytePtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i, src.read(i));
        }
    }
    
    public static void memcpy(IntSubArray dst, UBytePtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i, src.read(i));
        }
    }
    
    public static void memcpy(IntSubArray dst, IntSubArray src, int size) {
        for (int i = 0; i < Math.min(size, src.buffer.length); i++) {
            dst.write(i, src.read(i));
        }
    }
    
    public static void memcpy(UShortArray dst, UBytePtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i, src.read(i));
        }
    }
    
    public static void memcpy(UShortArray dst, UShortArray src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i, src.read(i));
        }
    }
    
    public static void memcpy(UShortPtr dst, UShortPtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i, src.read(i));
        }
    }
    
    public static void memcpy(ShortPtr dst, ShortPtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i, src.read(i));
        }
    }
    
    public static void memcpy(ShortPtr dst, UBytePtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i, (short) src.read(i));
        }
    }

    public static void memcpy(UBytePtr dst, int dstoffs, UBytePtr src, int srcoffs, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i + dstoffs, src.read(i + srcoffs));
        }
    }

    public static void memcpy(UBytePtr dst, int dstoffs, int[] src, int size) {
        for (int i = 0; i < Math.min(size, src.length); i++) {
            dst.write(i + dstoffs, src[i]);
        }
    }

    public static void memcpy(char[] dst, char[] src, int size) {
        for (int i = 0; i < Math.min(size, src.length); i++) {
            dst[i] = src[i];
        }
    }

    public static void memcpy(UBytePtr dst, int dstoffs, UBytePtr src, int size) {
        for (int i = 0; i < Math.min(size, src.memory.length); i++) {
            dst.write(i + dstoffs, src.read(i));
        }
    }

    public static void memcpy(UBytePtr dst, int dstoffs, char[] src, int size) {
        for (int i = 0; i < Math.min(size, src.length); i++) {
            dst.write(i + dstoffs, src[i]);
        }
    }

    /**
     * memset
     */
    public static void memset(UBytePtr ptr, int value, int length) {
        for (int i = 0; i < length; i++) {
            ptr.write(i, value);
        }
    }

    public static void memset(UBytePtr ptr, int offset, int value, int length) {
        for (int i = 0; i < length; i++) {
            ptr.write(i + offset, value);
        }
    }

    public static void memset(char[] dst, int value, int size) {
        for (int mem = 0; mem < size; mem++) {
            dst[mem] = (char) value;
        }
    }

    public static void memset(int[] dst, int value, int size) {
        for (int mem = 0; mem < size; mem++) {
            dst[mem] = value;
        }
    }

    public static void memset(short[] dst, int value, int size) {
        for (int mem = 0; mem < size; mem++) {
            dst[mem] = (short) value;
        }
    }

    public static void memset(ShortPtr buf, int value, int size) {
        for (int i = 0; i < size; i++) {
            buf.write(i, (short) value);
        }
    }

    /**
     * memcmp
     */
    public static int memcmp(char[] dist, int dstoffs, String src, int size) {
        char[] srcc = src.toCharArray();
        for (int i = 0; i < size; i++) {
            if (dist[(dstoffs + i)] != srcc[i]) {
                return -1;

            }
        }
        return 0;
    }

    public static int memcmp(char[] dst, char[] src, int size) {
        for (int i = 0; i < size; i++) {
            if (dst[i] != src[i]) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * strcmp
     */
    public static int strcmp(String str1, String str2) {
        return str1.compareTo(str2);
    }

    /**
     * strncmp
     *
     * @param s1
     * @param s2
     * @param n
     * @return
     */
    public static boolean strncmp(char[] s1, String s2, int n) {
        if (n > s2.length()) {
            n = s2.length();
        }
        String s1s = new String(s1).substring(0, n);//not proper but should work that way
        int compare = s1s.compareTo(s2.substring(0, n));
        if (compare == 0) {
            return false;//should be true , but for matching c format return false
        }
        return true;
    }
}
