package arcadeflex.WIP.v037b7.cpu.nec;

import static arcadeflex.WIP.v037b7.cpu.nec.necH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.*;

public class v33 extends nec {
    
    public v33() {
        cpu_num = CPU_V33;
        num_irqs = 1;
        default_vector = 0;
        overclock = 1.0;
        no_int = INT_IRQ;
        irq_int = -1000;
        nmi_int = NMI_IRQ;
        address_shift = 0;
        address_bits = 20;
        endianess = CPU_IS_LE;
        align_unit = 1;
        max_inst_len = 5;
        abits1 = ABITS1_20;
        abits2 = ABITS2_20;
        abitsmin = ABITS_MIN_20;
        icount = nec_ICount;
    }

    @Override
    public String cpu_info(Object context, int regnum) {
        return cpu_info_v33(context, regnum);
    }

    @Override
    public void reset(Object param) {
        v33_reset(param);
    }

    @Override
    public int get_pc() {
        return v33_get_pc();
    }

    @Override
    public void set_irq_callback(irqcallbacksPtr callback) {
        v33_set_irq_callback(callback);
    }
    
}