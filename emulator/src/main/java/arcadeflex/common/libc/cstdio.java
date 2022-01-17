package arcadeflex.common.libc;

/**
 *
 * @author shadow
 */
public class cstdio {

    /**
     *
     * function equals to c printf syntax
     *
     * @param str
     * @param arguments
     */
    public static void printf(String str, Object... arguments) {
        System.out.printf(str, arguments);
    }

    /**
     * Write formatted data to string
     *
     * @param str
     * @param arguments
     * @return
     */
    public static String sprintf(String str, Object... arguments) {
        return String.format(str, arguments);
    }
}
