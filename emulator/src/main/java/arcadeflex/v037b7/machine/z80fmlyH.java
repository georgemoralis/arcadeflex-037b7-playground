/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package arcadeflex.v037b7.machine;

//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;

public class z80fmlyH {

    public static final int MAX_CTC = 2;

    public static final int NOTIMER_0 = (1 << 0);
    public static final int NOTIMER_1 = (1 << 1);
    public static final int NOTIMER_2 = (1 << 2);
    public static final int NOTIMER_3 = (1 << 3);

    public static abstract interface IntrPtr {

        public abstract void handler(int which);
    }

    public static class z80ctc_interface {

        public z80ctc_interface(int num, int[] baseclock, int[] notimer, IntrPtr[] intr, WriteHandlerPtr zc0[], WriteHandlerPtr zc1[], WriteHandlerPtr zc2[]) {
            this.num = num;
            this.baseclock = baseclock;
            this.notimer = notimer;
            this.intr = intr;
            this.zc0 = zc0;
            this.zc1 = zc1;
            this.zc2 = zc2;
        }
        public int num;/* number of CTCs to emulate */
        public int[] baseclock;//[MAX_CTC];                           /* timer clock */
        public int[] notimer;//[MAX_CTC];                         /* timer disablers */
        public IntrPtr[] intr;//void (*intr[MAX_CTC])(int which);             /* callback when change interrupt status */
        public WriteHandlerPtr zc0[];//[MAX_CTC];   /* ZC/TO0 callback */
        public WriteHandlerPtr zc1[];//[MAX_CTC];   /* ZC/TO1 callback */
        public WriteHandlerPtr zc2[];//[MAX_CTC];   /* ZC/TO2 callback */
    }


    /*--------------------------------------------------------------------*/
    public static final int MAX_PIO = 1;
    /*TODO*///
/*TODO*///typedef struct
/*TODO*///{
/*TODO*///	int num;                                      /* number of PIOs to emulate */
/*TODO*///	void (*intr[MAX_CTC])(int which);             /* callback when change interrupt status */
/*TODO*///	void (*rdyA[MAX_PIO])(int data );             /* portA ready active callback (do not support yet)*/
/*TODO*///	void (*rdyB[MAX_PIO])(int data );             /* portB ready active callback (do not support yet)*/
/*TODO*///} z80pio_interface;
/*TODO*///    
}
