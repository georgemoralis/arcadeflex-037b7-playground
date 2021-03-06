/*
 * ported to v0.37b7
 *
 */
package gr.codebb.arcadeflex.WIP.v037b7.cpu.m6800;

import static gr.codebb.arcadeflex.WIP.v037b7.cpu.m6800.m6800Η.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.driverH.*;

public class m6808 extends m6800 {

    public m6808() {
        cpu_num = CPU_M6808;
        num_irqs = 1;
        default_vector = 0;
        overclock = 1.0;
        no_int = M6800_INT_NONE;
        irq_int = M6800_INT_IRQ;
        nmi_int = M6800_INT_NMI;
        address_shift = 0;
        address_bits = 16;
        endianess = CPU_IS_BE;
        align_unit = 1;
        max_inst_len = 4;
        abits1 = ABITS1_16;
        abits2 = ABITS2_16;
        abitsmin = ABITS_MIN_16;
        icount = m6800_ICount;
         m6800_ICount[0] = 50000;
    }

     public String cpu_info(Object context, int regnum) {
        switch (regnum) {
            case CPU_INFO_NAME:
                return "M6808";
        }
        return super.cpu_info(context, regnum);
    }
}
