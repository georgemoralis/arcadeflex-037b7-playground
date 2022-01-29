/** changelog 28/08/2017 - Added ShortPtr(short[] m) constructor */
package gr.codebb.arcadeflex.old2.arcadeflex;

import static arcadeflex.common.ptrLib.*;

/** @author shadow */
public class libc_v2 {

  public static int argc;
  public static String[] argv;

  /*
   *  Convert command-line parameters
   */
  public static void ConvertArguments(String mainClass, String[] arguments) {
    argc = arguments.length + 1;
    argv = new String[argc];
    argv[0] = mainClass;
    for (int i = 1; i < argc; i++) {
      argv[i] = arguments[i - 1];
    }
  }

  /** function equals to c memcmp function */
  public static int memcmp(char[] dist, int dstoffs, String src, int size) {
    char[] srcc = src.toCharArray();
    for (int i = 0; i < size; i++) {
      if (dist[(dstoffs + i)] != srcc[i]) {
        return -1;
      }
    }
    return 0;
  }

  /** Returns the sizeof an char array */
  public static int sizeof(char[] array) {
    return array.length;
  }

  public static int sizeof(int[] array) {
    return array.length;
  }

  /** memset */
  public static void memset(UBytePtr buf, int offset, int value, int size) {
    for (int i = 0; i < size; i++) {
      buf.write(i + offset, value);
    }
  }
}
