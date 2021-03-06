package gr.codebb.arcadeflex.WIP.v037b7.cpu.tms32010;

import static gr.codebb.arcadeflex.WIP.v037b7.cpu.tms32010.tms32010H.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.memory.cpu_setOPbase16;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;

public class tms32010 extends cpu_interface {

    public static int[] tms320c10_icount = new int[1];

    public tms32010() {
        cpu_num = CPU_TMS320C10;
        num_irqs = 2;
        default_vector = 0;
        overclock = 1.0;
        no_int = TMS320C10_INT_NONE;
        irq_int = -1;
        nmi_int = -1;
        address_bits = 16;
        address_shift = -1;
        endianess = CPU_IS_BE;
        align_unit = 2;
        max_inst_len = 4;
        abits1 = ABITS1_16;
        abits2 = ABITS2_16;
        abitsmin = ABITS_MIN_16;
        icount = tms320c10_icount;
    }

    @Override
    public int internal_read(int offset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void internal_write(int offset, int data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class tms320c10_Regs {

        int u16_PREPC;
        /* previous program counter */
        int u16_PC;
        int ACC, Preg;
        int ALU;
        int u16_Treg;
        int u16_AR[] = new int[2];
        int u16_STACK[] = new int[4];
        int u16_STR;
        int pending_irq, BIO_pending_irq;
        int irq_state;
        public irqcallbacksPtr irq_callback;
    }

    /**
     * ****** The following is the Status (Flag) register definition. ********
     */
    /* 15 | 14  |  13  | 12 | 11 | 10 | 9 |  8  | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0  */
 /* OV | OVM | INTM |  1 |  1 |  1 | 1 | ARP | 1 | 1 | 1 | 1 | 1 | 1 | 1 | DP */
    public static final int OV_FLAG = 0x8000;
    /* OV	(Overflow flag) 1 indicates an overflow */
    public static final int OVM_FLAG = 0x4000;
    /* OVM	(Overflow Mode bit) 1 forces ACC overflow to greatest positive or negative saturation value */
    public static final int INTM_FLAG = 0x2000;
    /* INTM	(Interrupt Mask flag) 0 enables maskable interrupts */
    public static final int ARP_REG = 0x0100;
    /* ARP	(Auxiliary Register Pointer) */
    public static final int DP_REG = 0x0001;
    /* DP	(Data memory Pointer (bank) bit) */

 /*TODO*///static UINT8 tms320c10_reg_layout[] = {
/*TODO*///	TMS320C10_PC,TMS320C10_SP,TMS320C10_STR,TMS320C10_ACC,-1,
/*TODO*///	TMS320C10_PREG,TMS320C10_TREG,TMS320C10_AR0,TMS320C10_AR1,-1,
/*TODO*///	TMS320C10_STK0,TMS320C10_STK1,TMS320C10_STK2,TMS320C10_STK3,0
/*TODO*///};
/*TODO*///
/*TODO*///static UINT8 tms320c10_win_layout[] = {
/*TODO*///	28, 0,52, 4,	/* register window (top rows) */
/*TODO*///	 0, 0,27,22,	/* disassembler window (left colums) */
/*TODO*///	28, 5,52, 8,	/* memory #1 window (right, upper middle) */
/*TODO*///	28,14,52, 8,	/* memory #2 window (right, lower middle) */
/*TODO*///	 0,23,80, 1,	/* command line window (bottom rows) */
/*TODO*///};
    static int u16_opcode = 0;
    static int u8_opcode_major = 0;
    static int u8_opcode_minor;
    static int u8_opcode_minr;
    /* opcode split into MSB and LSB */
    static tms320c10_Regs R = new tms320c10_Regs();
    static int tmpacc;

    public static abstract interface opcode_fn {

        public abstract void handler();
    }

    @Override
    public void reset(Object param) {
        R.u16_PC = 0;
        R.u16_STR = 0x0fefe;
        R.ACC = 0;
        R.pending_irq = TMS320C10_NOT_PENDING;
        R.BIO_pending_irq = TMS320C10_NOT_PENDING;
    }

    @Override
    public void exit() {
        /* nothing to do ? */
    }

    @Override
    public int execute(int cycles) {
        tms320c10_icount[0] = cycles;

        do {
            if ((R.pending_irq & TMS320C10_PENDING) != 0) {
                int type = (R.irq_callback).handler(0);
                R.pending_irq |= type;
            }

            if (R.pending_irq != 0) {
                /* Dont service INT if prev instruction was MPY, MPYK or EINT */
                if ((u8_opcode_major != 0x6d) || ((u8_opcode_major & 0xe0) != 0x80) || (u16_opcode != 0x7f82)) {
                    tms320c10_icount[0] -= Ext_IRQ();
                }
            }

            R.u16_PREPC = R.u16_PC & 0xFFFF;

            //CALL_MAME_DEBUG;
            u16_opcode = TMS320C10_RDOP(R.u16_PC);
            u8_opcode_major = ((u16_opcode & 0x0ff00) >> 8);
            u8_opcode_minor = (u16_opcode & 0x0ff);

            R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            if (u8_opcode_major != 0x07f) {
                /* Do all opcodes except the 7Fxx ones */
                tms320c10_icount[0] -= cycles_main[u8_opcode_major];
                ((opcode_main[u8_opcode_major])).handler();
            } else {
                /* Opcode major byte 7Fxx has many opcodes in its minor byte */
                u8_opcode_minr = (u16_opcode & 0x001f);
                tms320c10_icount[0] -= cycles_7F_other[u8_opcode_minr];
                ((opcode_7F_other[u8_opcode_minr])).handler();
            }
        } while (tms320c10_icount[0] > 0);

        return cycles - tms320c10_icount[0];
    }

    @Override
    public Object init_context() {
        Object reg = new tms320c10_Regs();
        return reg;
    }

    @Override
    public Object get_context() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_context(Object reg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] get_cycle_table(int which) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_cycle_table(int which, int[] new_table) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int get_pc() {
        return R.u16_PC & 0xFFFF;
    }

    @Override
    public void set_pc(int val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int get_sp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_sp(int val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int get_reg(int regnum) {
        	switch( regnum )
	{
		case TMS320C10_PC: return R.u16_PC;
		/* This is actually not a stack pointer, but the stack contents */
		case TMS320C10_STK3: return R.u16_STACK[3];
		case TMS320C10_ACC: return R.ACC;
		case TMS320C10_STR: return R.u16_STR;
		case TMS320C10_PREG: return R.Preg;
		case TMS320C10_TREG: return R.u16_Treg;
		case TMS320C10_AR0: return R.u16_AR[0];
		case TMS320C10_AR1: return R.u16_AR[1];
		default:
			if( regnum <= REG_SP_CONTENTS )
			{
				int/*unsigned*/ offset = (REG_SP_CONTENTS - regnum);
				if( offset < 4 )
					return R.u16_STACK[offset];
			}
	}
	return 0;
    }

    @Override
    public void set_reg(int regnum, int val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_nmi_line(int linestate) {
        /* TMS320C10 does not have a NMI line */
    }

    @Override
    public void set_irq_line(int irqline, int state) {
        if (irqline == TMS320C10_ACTIVE_INT) {
            R.irq_state = state;
            if (state == CLEAR_LINE) {
                R.pending_irq &= ~TMS320C10_PENDING;
            }
            if (state == ASSERT_LINE) {
                R.pending_irq |= TMS320C10_PENDING;
            }
        }
        if (irqline == TMS320C10_ACTIVE_BIO) {
            if (state == CLEAR_LINE) {
                R.BIO_pending_irq &= ~TMS320C10_PENDING;
            }
            if (state == ASSERT_LINE) {
                R.BIO_pending_irq |= TMS320C10_PENDING;
            }
        }
    }

    @Override
    public void set_irq_callback(irqcallbacksPtr callback) {
        R.irq_callback = callback;
    }

    @Override
    public void internal_interrupt(int type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cpu_state_save(Object file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cpu_state_load(Object file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String cpu_info(Object context, int regnum) {
        /*TODO*///	static char buffer[16][47+1];
/*TODO*///	static int which;
/*TODO*///	tms320c10_Regs *r = context;
/*TODO*///
/*TODO*///	which = ++which % 16;
/*TODO*///	buffer[which][0] = '\0';
/*TODO*///	if( !context )
/*TODO*///		r = &R;
/*TODO*///
        switch (regnum) {
            /*TODO*///		case CPU_INFO_REG+TMS320C10_PC: sprintf(buffer[which], "PC:%04X",  r->PC); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_SP: sprintf(buffer[which], "SP:%X", 0); /* fake stack pointer */ break;
/*TODO*///        case CPU_INFO_REG+TMS320C10_STR: sprintf(buffer[which], "STR:%04X", r->STR); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_ACC: sprintf(buffer[which], "ACC:%08X", r->ACC); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_PREG: sprintf(buffer[which], "P:%08X",   r->Preg); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_TREG: sprintf(buffer[which], "T:%04X",   r->Treg); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_AR0: sprintf(buffer[which], "AR0:%04X", r->AR[0]); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_AR1: sprintf(buffer[which], "AR1:%04X", r->AR[1]); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_STK0: sprintf(buffer[which], "STK0:%04X", r->STACK[0]); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_STK1: sprintf(buffer[which], "STK1:%04X", r->STACK[1]); break;
/*TODO*///		case CPU_INFO_REG+TMS320C10_STK2: sprintf(buffer[which], "STK2:%04X", r->STACK[2]); break;
/*TODO*///        case CPU_INFO_REG+TMS320C10_STK3: sprintf(buffer[which], "STK3:%04X", r->STACK[3]); break;
/*TODO*///        case CPU_INFO_FLAGS:
/*TODO*///            sprintf(buffer[which], "%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c",
/*TODO*///                r->STR & 0x8000 ? 'O':'.',
/*TODO*///                r->STR & 0x4000 ? 'M':'.',
/*TODO*///                r->STR & 0x2000 ? 'I':'.',
/*TODO*///				r->STR & 0x1000 ? '.':'?',
/*TODO*///				r->STR & 0x0800 ? 'a':'?',
/*TODO*///				r->STR & 0x0400 ? 'r':'?',
/*TODO*///				r->STR & 0x0200 ? 'p':'?',
/*TODO*///				r->STR & 0x0100 ? '1':'0',
/*TODO*///				r->STR & 0x0080 ? '.':'?',
/*TODO*///				r->STR & 0x0040 ? '.':'?',
/*TODO*///				r->STR & 0x0020 ? '.':'?',
/*TODO*///				r->STR & 0x0010 ? '.':'?',
/*TODO*///				r->STR & 0x0008 ? '.':'?',
/*TODO*///				r->STR & 0x0004 ? 'd':'?',
/*TODO*///				r->STR & 0x0002 ? 'p':'?',
/*TODO*///				r->STR & 0x0001 ? '1':'0');
/*TODO*///            break;
            case CPU_INFO_NAME:
                return "320C10";
            case CPU_INFO_FAMILY:
                return "Texas Instruments 320C10";
            case CPU_INFO_VERSION:
                return "1.02";
            case CPU_INFO_FILE:
                return "tms32010.java";
            case CPU_INFO_CREDITS:
                return "Copyright (C) 1999 by Quench";
            /*TODO*///        case CPU_INFO_REG_LAYOUT: return (const char*)tms320c10_reg_layout;
/*TODO*///        case CPU_INFO_WIN_LAYOUT: return (const char*)tms320c10_win_layout;
        }
        /*TODO*///	return buffer[which];
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public int memory_read(int offset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void memory_write(int offset, int data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_op_base(int pc) {
        cpu_setOPbase16.handler(pc);
    }

    public static boolean OV() {
        return (R.u16_STR & OV_FLAG) != 0;
    }

    /* OV	(Overflow flag) */
    public static boolean OVM() {
        return (R.u16_STR & OVM_FLAG) != 0;
    }

    /* OVM	(Overflow Mode bit) 1 indicates an overflow */
    public static boolean INTM() {
        return (R.u16_STR & INTM_FLAG) != 0;
    }

    /* INTM	(Interrupt enable flag) 0 enables maskable interrupts */
    public static int ARP = ((R.u16_STR & ARP_REG) >> 8);
    /* ARP	(Auxiliary Register Pointer) */
    public static int DP = ((R.u16_STR & DP_REG) << 7);

    /* DP	(Data memory Pointer bit) */
    public static int /*UINT16*/ u16_memaccess;

    public static void CLR(/*UINT16*/int flag) {
        R.u16_STR = (R.u16_STR & ~flag) & 0xFFFF;
        R.u16_STR = (R.u16_STR | 0x1efe) & 0xFFFF;
    }

    public static void SET(/*UINT16*/int flag) {
        R.u16_STR = (R.u16_STR | flag) & 0xFFFF;
        R.u16_STR = (R.u16_STR | 0x1efe) & 0xFFFF;
    }

    public static void getdata(/*UINT8*/int shift,/*UINT8*/ int signext) {
        if ((u8_opcode_minor & 0x80) != 0) {
            u16_memaccess = (R.u16_AR[ARP] & 0x00ff);
        } else {
            u16_memaccess = (DP | (u8_opcode_minor & 0x07f)) & 0xFFFF;
        }
        R.ALU = TMS320C10_RAM_RDMEM(u16_memaccess & 0xFFFF);
        if ((signext) != 0 && (R.ALU & 0x8000) != 0) {
            R.ALU |= 0xffff0000;
        } else {
            R.ALU &= 0x0000ffff;
        }
        R.ALU <<= shift;
        if ((u8_opcode_minor & 0x80) != 0) {
            if ((u8_opcode_minor & 0x20) != 0 || (u8_opcode_minor & 0x10) != 0) {
                int/*UINT16*/ tmpAR = R.u16_AR[ARP];
                if ((u8_opcode_minor & 0x20) != 0) {
                    tmpAR = (tmpAR + 1) & 0xFFFF;
                }
                if ((u8_opcode_minor & 0x10) != 0) {
                    tmpAR = (tmpAR - 1) & 0xFFFF;
                }
                R.u16_AR[ARP] = ((R.u16_AR[ARP] & 0xfe00) | (tmpAR & 0x01ff)) & 0xFFFF;
            }
            if ((~u8_opcode_minor & 0x08) != 0) {
                if ((u8_opcode_minor & 1) != 0) {
                    SET(ARP_REG);
                } else {
                    CLR(ARP_REG);
                }
            }
        }
    }

    public static void getdata_lar() {
        if ((u8_opcode_minor & 0x80) != 0) {
            u16_memaccess = (R.u16_AR[ARP] & 0x00ff);
        } else {
            u16_memaccess = (DP | (u8_opcode_minor & 0x07f)) & 0xFFFF;
        }
        R.ALU = TMS320C10_RAM_RDMEM(u16_memaccess & 0xFFFF);
        if ((u8_opcode_minor & 0x80) != 0) {
            if ((u8_opcode_minor & 0x20) != 0 || (u8_opcode_minor & 0x10) != 0) {
                if ((u8_opcode_major & 1) != ARP) {
                    int/*UINT16*/ tmpAR = R.u16_AR[ARP];
                    if ((u8_opcode_minor & 0x20) != 0) {
                        tmpAR = (tmpAR + 1) & 0xFFFF;
                    }
                    if ((u8_opcode_minor & 0x10) != 0) {
                        tmpAR = (tmpAR - 1) & 0xFFFF;
                    }
                    R.u16_AR[ARP] = ((R.u16_AR[ARP] & 0xfe00) | (tmpAR & 0x01ff)) & 0xFFFF;
                }
            }
            if ((~u8_opcode_minor & 0x08) != 0) {
                if ((u8_opcode_minor & 1) != 0) {
                    SET(ARP_REG);
                } else {
                    CLR(ARP_REG);
                }
            }
        }
    }

    public static void putdata(/*UINT16*/int data) {
        if ((u8_opcode_minor & 0x80) != 0) {
            u16_memaccess = (R.u16_AR[ARP] & 0x00ff);
        } else {
            u16_memaccess = (DP | (u8_opcode_minor & 0x07f)) & 0xFFFF;
        }
        if ((u8_opcode_minor & 0x80) != 0) {
            if ((u8_opcode_minor & 0x20) != 0 || (u8_opcode_minor & 0x10) != 0) {
                int/*UINT16*/ tmpAR = R.u16_AR[ARP];
                if ((u8_opcode_minor & 0x20) != 0) {
                    tmpAR = (tmpAR + 1) & 0xFFFF;
                }
                if ((u8_opcode_minor & 0x10) != 0) {
                    tmpAR = (tmpAR - 1) & 0xFFFF;
                }
                R.u16_AR[ARP] = ((R.u16_AR[ARP] & 0xfe00) | (tmpAR & 0x01ff)) & 0xFFFF;
            }
            if ((~u8_opcode_minor & 0x08) != 0) {
                if ((u8_opcode_minor & 1) != 0) {
                    SET(ARP_REG);
                } else {
                    CLR(ARP_REG);
                }
            }
        }
        if ((u8_opcode_major == 0x30) || (u8_opcode_major == 0x31)) {
            TMS320C10_RAM_WRMEM(u16_memaccess & 0xFFFF, (R.u16_AR[data]) & 0xFFFF);
        } else {
            TMS320C10_RAM_WRMEM(u16_memaccess & 0xFFFF, (data & 0xffff));
        }
    }

    public static void putdata_sst(/*UINT16*/int data) {
        if ((u8_opcode_minor & 0x80) != 0) {
            u16_memaccess = (R.u16_AR[ARP] & 0x00ff);
        } else {
            u16_memaccess = ((0x80 | u8_opcode_minor)) & 0xFFFF;
        }
        if ((u8_opcode_minor & 0x80) != 0) {
            if ((u8_opcode_minor & 0x20) != 0 || (u8_opcode_minor & 0x10) != 0) {
                int/*UINT16*/ tmpAR = R.u16_AR[ARP];
                if ((u8_opcode_minor & 0x20) != 0) {
                    tmpAR = (tmpAR + 1) & 0xFFFF;
                }
                if ((u8_opcode_minor & 0x10) != 0) {
                    tmpAR = (tmpAR - 1) & 0xFFFF;
                }
                R.u16_AR[ARP] = ((R.u16_AR[ARP] & 0xfe00) | (tmpAR & 0x01ff)) & 0xFFFF;
            }
        }
        TMS320C10_RAM_WRMEM(u16_memaccess & 0xFFFF, (data & 0xffff));
    }

    public static void M_ILLEGAL() {
        logerror("TMS320C10:  PC = %04x,  Illegal opcode = %04x\n", (R.u16_PC - 1), u16_opcode);
    }


    /* This following function is here to fill in the void for */
 /* the opcode call function. This function is never called. */
    public static opcode_fn other_7F_opcodes = new opcode_fn() {
        public void handler() {
        }
    };
    public static opcode_fn illegal = new opcode_fn() {
        public void handler() {
            M_ILLEGAL();
        }
    };
    public static opcode_fn abst = new opcode_fn() {
        public void handler() {
            if (R.ACC >= 0x80000000) {
                R.ACC = ~R.ACC;
                R.ACC++;
                if (OVM() && (R.ACC == 0x80000000)) {
                    R.ACC--;
                }
            }
        }
    };

    /* ** The manual does not mention overflow with the ADD? instructions *****
   ** however i implelemted overflow, coz it doesnt make sense otherwise **
   ** and newer generations of this type of chip supported it. I think ****
   ** the manual is wrong (apart from other errors the manual has). *******

public static opcode_fn add_sh= new opcode_fn() { public void handler() 	{ getdata(opcode_major,1); R.ACC += R.ALU; }
public static opcode_fn addh= new opcode_fn() { public void handler() 		{ getdata(0,0); R.ACC += (R.ALU << 16); }
     */
    public static opcode_fn add_sh = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata(u8_opcode_major & 0xFF, 1);
            R.ACC += R.ALU;
            if (tmpacc > R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = 0x7fffffff;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn addh = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata(0, 0);
            R.ACC += (R.ALU << 16);
            R.ACC &= 0xffff0000;
            R.ACC += (tmpacc & 0x0000ffff);
            if (tmpacc > R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC &= 0x0000ffff;
                    R.ACC |= 0x7fff0000;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn adds = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata(0, 0);
            R.ACC += R.ALU;
            if (tmpacc > R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = 0x7fffffff;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn and = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            R.ACC &= R.ALU;
            R.ACC &= 0x0000ffff;
        }
    };
    public static opcode_fn apac = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            R.ACC += R.Preg;
            if (tmpacc > R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = 0x7fffffff;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn br = new opcode_fn() {
        public void handler() {
            R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
        }
    };
    public static opcode_fn banz = new opcode_fn() {
        public void handler() {
            if ((R.u16_AR[ARP] & 0x01ff) == 0) {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            } else {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
            }
            R.ALU = R.u16_AR[ARP];
            R.ALU--;
            R.u16_AR[ARP] = ((R.u16_AR[ARP] & 0xfe00) | (R.ALU & 0x01ff)) & 0xFFFF;
        }
    };
    public static opcode_fn bgez = new opcode_fn() {
        public void handler() {
            if (R.ACC >= 0) {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
            } else {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            }
        }
    };
    public static opcode_fn bgz = new opcode_fn() {
        public void handler() {
            if (R.ACC > 0) {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
            } else {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            }
        }
    };
    public static opcode_fn bioz = new opcode_fn() {
        public void handler() {
            if (R.BIO_pending_irq != 0) {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
            } else {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            }
        }
    };
    public static opcode_fn blez = new opcode_fn() {
        public void handler() {
            if (R.ACC <= 0) {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
            } else {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            }
        }
    };
    public static opcode_fn blz = new opcode_fn() {
        public void handler() {
            if (R.ACC < 0) {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
            } else {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            }
        }
    };
    public static opcode_fn bnz = new opcode_fn() {
        public void handler() {
            if (R.ACC != 0) {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
            } else {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            }
        }
    };
    public static opcode_fn bv = new opcode_fn() {
        public void handler() {
            if (OV()) {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
                CLR(OV_FLAG);
            } else {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            }
        }
    };
    public static opcode_fn bz = new opcode_fn() {
        public void handler() {
            if (R.ACC == 0) {
                R.u16_PC = TMS320C10_RDOP_ARG(R.u16_PC);
            } else {
                R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            }
        }
    };
    public static opcode_fn cala = new opcode_fn() {
        public void handler() {
            R.u16_STACK[0] = R.u16_STACK[1] & 0xFFFF;
            R.u16_STACK[1] = R.u16_STACK[2] & 0xFFFF;
            R.u16_STACK[2] = R.u16_STACK[3] & 0xFFFF;
            R.u16_STACK[3] = (R.u16_PC & TMS320C10_ADDR_MASK) & 0xFFFF;
            R.u16_PC = (R.ACC & TMS320C10_ADDR_MASK) & 0xFFFF;
        }
    };
    public static opcode_fn call = new opcode_fn() {
        public void handler() {
            R.u16_PC = (R.u16_PC + 1) & 0xFFFF;
            R.u16_STACK[0] = R.u16_STACK[1] & 0xFFFF;
            R.u16_STACK[1] = R.u16_STACK[2] & 0xFFFF;
            R.u16_STACK[2] = R.u16_STACK[3] & 0xFFFF;
            R.u16_STACK[3] = (R.u16_PC & TMS320C10_ADDR_MASK) & 0xFFFF;
            R.u16_PC = (TMS320C10_RDOP_ARG((R.u16_PC - 1) & 0xFFFF) & TMS320C10_ADDR_MASK) & 0xFFFF;
        }
    };
    public static opcode_fn dint = new opcode_fn() {
        public void handler() {
            SET(INTM_FLAG);
        }
    };
    public static opcode_fn dmov = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            TMS320C10_RAM_WRMEM((u16_memaccess + 1) & 0xFFFF, R.ALU);
        }
    };
    public static opcode_fn eint = new opcode_fn() {
        public void handler() {
            CLR(INTM_FLAG);
        }
    };
    public static opcode_fn in_p = new opcode_fn() {
        public void handler() {
            R.ALU = TMS320C10_In((u8_opcode_major & 7));
            putdata((R.ALU & 0x0000ffff));
        }
    };
    public static opcode_fn lac_sh = new opcode_fn() {
        public void handler() {
            getdata((u8_opcode_major & 0x0f), 1);
            R.ACC = R.ALU;
        }
    };
    public static opcode_fn lack = new opcode_fn() {
        public void handler() {
            R.ACC = (u8_opcode_minor & 0x000000ff);
        }
    };
    public static opcode_fn lar_ar0 = new opcode_fn() {
        public void handler() {
            getdata_lar();
            R.u16_AR[0] = R.ALU & 0xFFFF;
        }
    };
    public static opcode_fn lar_ar1 = new opcode_fn() {
        public void handler() {
            getdata_lar();
            R.u16_AR[1] = R.ALU & 0xFFFF;
        }
    };
    public static opcode_fn lark_ar0 = new opcode_fn() {
        public void handler() {
            R.u16_AR[0] = (u8_opcode_minor & 0x00ff);
        }
    };
    public static opcode_fn lark_ar1 = new opcode_fn() {
        public void handler() {
            R.u16_AR[1] = (u8_opcode_minor & 0x00ff);
        }
    };
    public static opcode_fn larp_mar = new opcode_fn() {
        public void handler() {
            if ((u8_opcode_minor & 0x80) != 0) {
                if ((u8_opcode_minor & 0x20) != 0 || (u8_opcode_minor & 0x10) != 0) {
                    int/*UINT16*/ tmpAR = R.u16_AR[ARP];
                    if ((u8_opcode_minor & 0x20) != 0) {
                        tmpAR = (tmpAR + 1) & 0xFFFF;
                    }
                    if ((u8_opcode_minor & 0x10) != 0) {
                        tmpAR = (tmpAR - 1) & 0xFFFF;
                    }
                    R.u16_AR[ARP] = ((R.u16_AR[ARP] & 0xfe00) | (tmpAR & 0x01ff)) & 0xFFFF;
                }
                if ((~u8_opcode_minor & 0x08) != 0) {
                    if ((u8_opcode_minor & 0x01) != 0) {
                        SET(ARP_REG);
                    } else {
                        CLR(ARP_REG);
                    }
                }
            }
        }
    };
    public static opcode_fn ldp = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            if ((R.ALU & 1) != 0) {
                SET(DP_REG);
            } else {
                CLR(DP_REG);
            }
        }
    };
    public static opcode_fn ldpk = new opcode_fn() {
        public void handler() {
            if ((u8_opcode_minor & 1) != 0) {
                SET(DP_REG);
            } else {
                CLR(DP_REG);
            }
        }
    };
    public static opcode_fn lst = new opcode_fn() {
        public void handler() {
            tmpacc = R.u16_STR;
            u8_opcode_minor = (u8_opcode_minor | 0x08) & 0xFF;
            /* This dont support next arp, so make sure it dont happen */
            getdata(0, 0);
            R.u16_STR = R.ALU & 0xFFFF;
            tmpacc &= INTM_FLAG;
            R.u16_STR = (R.u16_STR | tmpacc) & 0xFFFF;
            R.u16_STR = (R.u16_STR | 0x1efe) & 0xFFFF;
        }
    };
    public static opcode_fn lt = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            R.u16_Treg = R.ALU & 0xFFFF;
        }
    };
    public static opcode_fn lta = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata(0, 0);
            R.u16_Treg = R.ALU & 0xFFFF;
            R.ACC += R.Preg;
            if (tmpacc > R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = 0x7fffffff;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn ltd = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata(0, 0);
            R.u16_Treg = R.ALU & 0xFFFF;
            R.ACC += R.Preg;
            if (tmpacc > R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = 0x7fffffff;
                }
            } else {
                CLR(OV_FLAG);
            }
            TMS320C10_RAM_WRMEM((u16_memaccess + 1) & 0xFFFF, R.ALU);
        }
    };
    public static opcode_fn mpy = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            if ((R.ALU == 0x00008000) && (R.u16_Treg == 0x8000)) {
                R.Preg = 0xc0000000;
            } else {
                R.Preg = R.ALU * R.u16_Treg;
            }
        }
    };
    public static opcode_fn mpyk = new opcode_fn() {
        public void handler() {
            if ((u16_opcode & 0x1000) != 0) {
                R.Preg = R.u16_Treg * ((u16_opcode & 0x1fff) | 0xe000);
            } else {
                R.Preg = R.u16_Treg * (u16_opcode & 0x1fff);
            }
        }
    };
    public static opcode_fn nop = new opcode_fn() {
        public void handler() {
        }
    };
    public static opcode_fn or = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            R.ALU &= 0x0000ffff;
            R.ACC |= R.ALU;
        }
    };
    public static opcode_fn out_p = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            TMS320C10_Out((u8_opcode_major & 7), (R.ALU & 0x0000ffff));
        }
    };
    public static opcode_fn pac = new opcode_fn() {
        public void handler() {
            R.ACC = R.Preg;
        }
    };
    public static opcode_fn pop = new opcode_fn() {
        public void handler() {
            R.ACC = R.u16_STACK[3] & TMS320C10_ADDR_MASK;
            R.u16_STACK[3] = R.u16_STACK[2] & 0xFFFF;
            R.u16_STACK[2] = R.u16_STACK[1] & 0xFFFF;
            R.u16_STACK[1] = R.u16_STACK[0] & 0xFFFF;
        }
    };
    public static opcode_fn push = new opcode_fn() {
        public void handler() {
            R.u16_STACK[0] = R.u16_STACK[1] & 0xFFFF;
            R.u16_STACK[1] = R.u16_STACK[2] & 0xFFFF;
            R.u16_STACK[2] = R.u16_STACK[3] & 0xFFFF;
            R.u16_STACK[3] = (R.ACC & TMS320C10_ADDR_MASK) & 0xFFFF;
        }
    };
    public static opcode_fn ret = new opcode_fn() {
        public void handler() {
            R.u16_PC = (R.u16_STACK[3] & TMS320C10_ADDR_MASK) & 0xFFFF;
            R.u16_STACK[3] = R.u16_STACK[2] & 0xFFFF;
            R.u16_STACK[2] = R.u16_STACK[1] & 0xFFFF;
            R.u16_STACK[1] = R.u16_STACK[0] & 0xFFFF;
        }
    };
    public static opcode_fn rovm = new opcode_fn() {
        public void handler() {
            CLR(OVM_FLAG);
        }
    };
    public static opcode_fn sach_sh = new opcode_fn() {
        public void handler() {
            putdata(((R.ACC << (u8_opcode_major & 7)) >> 16) & 0xFFFF);
        }
    };
    public static opcode_fn sacl = new opcode_fn() {
        public void handler() {
            putdata((R.ACC & 0x0000ffff));
        }
    };
    public static opcode_fn sar_ar0 = new opcode_fn() {
        public void handler() {
            putdata(0);
        }
    };
    public static opcode_fn sar_ar1 = new opcode_fn() {
        public void handler() {
            putdata(1);
        }
    };
    public static opcode_fn sovm = new opcode_fn() {
        public void handler() {
            SET(OVM_FLAG);
        }
    };
    public static opcode_fn spac = new opcode_fn() {
        public void handler() {
            int tmpPreg = R.Preg;
            tmpacc = R.ACC;
            /* if (tmpPreg & 0x8000) tmpPreg |= 0xffff0000; */
            R.ACC -= tmpPreg;
            if (tmpacc < R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = 0x80000000;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn sst = new opcode_fn() {
        public void handler() {
            putdata_sst(R.u16_STR & 0xFFFF);
        }
    };
    public static opcode_fn sub_sh = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata((u8_opcode_major & 0x0f), 1);
            R.ACC -= R.ALU;
            if (tmpacc < R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = 0x80000000;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn subc = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata(15, 0);
            tmpacc -= R.ALU;
            if (tmpacc < 0) {
                R.ACC <<= 1;
                SET(OV_FLAG);
            } else {
                R.ACC = ((tmpacc << 1) + 1);
            }
        }
    };
    public static opcode_fn subh = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata(0, 0);
            R.ACC -= (R.ALU << 16);
            R.ACC &= 0xffff0000;
            R.ACC += (tmpacc & 0x0000ffff);
            if ((tmpacc & 0xffff0000) < (R.ACC & 0xffff0000)) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = (tmpacc & 0x0000ffff);
                    R.ACC |= 0x80000000;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn subs = new opcode_fn() {
        public void handler() {
            tmpacc = R.ACC;
            getdata(0, 0);
            R.ACC -= R.ALU;
            if (tmpacc < R.ACC) {
                SET(OV_FLAG);
                if (OVM()) {
                    R.ACC = 0x80000000;
                }
            } else {
                CLR(OV_FLAG);
            }
        }
    };
    public static opcode_fn tblr = new opcode_fn() {
        public void handler() {
            R.ALU = TMS320C10_ROM_RDMEM((R.ACC & TMS320C10_ADDR_MASK));
            putdata(R.ALU & 0xFFFF);
            R.u16_STACK[0] = R.u16_STACK[1] & 0xFFFF;
        }
    };
    public static opcode_fn tblw = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            TMS320C10_ROM_WRMEM(((R.ACC & TMS320C10_ADDR_MASK)), R.ALU);
            R.u16_STACK[0] = R.u16_STACK[1] & 0xFFFF;
        }
    };
    public static opcode_fn xor = new opcode_fn() {
        public void handler() {
            tmpacc = (R.ACC & 0xffff0000);
            getdata(0, 0);
            R.ACC ^= R.ALU;
            R.ACC &= 0x0000ffff;
            R.ACC |= tmpacc;
        }
    };
    public static opcode_fn zac = new opcode_fn() {
        public void handler() {
            R.ACC = 0;
        }
    };
    public static opcode_fn zalh = new opcode_fn() {
        public void handler() {
            getdata(16, 0);
            R.ACC = R.ALU;
        }
    };
    public static opcode_fn zals = new opcode_fn() {
        public void handler() {
            getdata(0, 0);
            R.ACC = R.ALU;
        }
    };

    static int cycles_main[]
            = {
                /*00*/1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                /*10*/ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                /*20*/ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                /*30*/ 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
                /*40*/ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                /*50*/ 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1,
                /*60*/ 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1,
                /*70*/ 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 3, 1, 0,
                /*80*/ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                /*90*/ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                /*A0*/ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                /*B0*/ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                /*C0*/ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                /*D0*/ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                /*E0*/ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                /*F0*/ 0, 0, 0, 0, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2
            };

    static int cycles_7F_other[]
            = {
                /*80*/1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 1,
                /*90*/ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 1,};

    static opcode_fn opcode_main[]
            = {
                /*00*/add_sh, add_sh, add_sh, add_sh, add_sh, add_sh, add_sh, add_sh /*08*/,
                add_sh, add_sh, add_sh, add_sh, add_sh, add_sh, add_sh, add_sh /*10*/,
                sub_sh, sub_sh, sub_sh, sub_sh, sub_sh, sub_sh, sub_sh, sub_sh /*18*/,
                sub_sh, sub_sh, sub_sh, sub_sh, sub_sh, sub_sh, sub_sh, sub_sh /*20*/,
                lac_sh, lac_sh, lac_sh, lac_sh, lac_sh, lac_sh, lac_sh, lac_sh /*28*/,
                lac_sh, lac_sh, lac_sh, lac_sh, lac_sh, lac_sh, lac_sh, lac_sh /*30*/,
                sar_ar0, sar_ar1, illegal, illegal, illegal, illegal, illegal, illegal /*38*/,
                lar_ar0, lar_ar1, illegal, illegal, illegal, illegal, illegal, illegal /*40*/,
                in_p, in_p, in_p, in_p, in_p, in_p, in_p, in_p /*48*/,
                out_p, out_p, out_p, out_p, out_p, out_p, out_p, out_p /*50*/,
                sacl, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*58*/,
                sach_sh, sach_sh, sach_sh, sach_sh, sach_sh, sach_sh, sach_sh, sach_sh /*60*/,
                addh, adds, subh, subs, subc, zalh, zals, tblr /*68*/,
                larp_mar, dmov, lt, ltd, lta, mpy, ldpk, ldp /*70*/,
                lark_ar0, lark_ar1, illegal, illegal, illegal, illegal, illegal, illegal /*78*/,
                xor, and, or, lst, sst, tblw, lack, other_7F_opcodes /*80*/,
                mpyk, mpyk, mpyk, mpyk, mpyk, mpyk, mpyk, mpyk /*88*/,
                mpyk, mpyk, mpyk, mpyk, mpyk, mpyk, mpyk, mpyk /*90*/,
                mpyk, mpyk, mpyk, mpyk, mpyk, mpyk, mpyk, mpyk /*98*/,
                mpyk, mpyk, mpyk, mpyk, mpyk, mpyk, mpyk, mpyk /*A0*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*A8*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*B0*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*B8*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*C0*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*C8*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*D0*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*D8*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*E0*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*E8*/,
                illegal, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*F0*/,
                illegal, illegal, illegal, illegal, banz, bv, bioz, illegal /*F8*/,
                call, br, blz, blez, bgz, bgez, bnz, bz
            };

    static opcode_fn opcode_7F_other[]
            = {
                /*80*/nop, dint, eint, illegal, illegal, illegal, illegal, illegal /*88*/,
                abst, zac, rovm, sovm, cala, ret, pac, apac /*90*/,
                spac, illegal, illegal, illegal, illegal, illegal, illegal, illegal /*98*/,
                illegal, illegal, illegal, illegal, push, pop, illegal, illegal
            };

    /**
     * **************************************************************************
     * Issue an interrupt if necessary
     * **************************************************************************
     */
    static int Ext_IRQ() {
        if (!INTM()) {
            logerror("TMS320C10:  EXT INTERRUPT\n");
            SET(INTM_FLAG);
            R.u16_STACK[0] = R.u16_STACK[1] & 0xFFFF;
            R.u16_STACK[1] = R.u16_STACK[2] & 0xFFFF;
            R.u16_STACK[2] = R.u16_STACK[3] & 0xFFFF;
            R.u16_STACK[3] = (R.u16_PC & TMS320C10_ADDR_MASK) & 0xFFFF;
            R.u16_PC = 0x0002;
            R.pending_irq = TMS320C10_NOT_PENDING;
            return 3;
            /* 3 clock cycles used due to PUSH and DINT operation ? */
        }
        return 0;
    }

    /*TODO*////****************************************************************************
/*TODO*/// * Get all registers in given buffer
/*TODO*/// ****************************************************************************/
/*TODO*///unsigned tms320c10_get_context (void *dst)
/*TODO*///{
/*TODO*///    if( dst )
/*TODO*///        *(tms320c10_Regs*)dst = R;
/*TODO*///    return sizeof(tms320c10_Regs);
/*TODO*///}
/*TODO*///
/*TODO*////****************************************************************************
/*TODO*/// * Set all registers to given values
/*TODO*/// ****************************************************************************/
/*TODO*///void tms320c10_set_context (void *src)
/*TODO*///{
/*TODO*///	if( src )
/*TODO*///		R = *(tms320c10_Regs*)src;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////****************************************************************************
/*TODO*/// * Set program counter
/*TODO*/// ****************************************************************************/
/*TODO*///void tms320c10_set_pc (unsigned val)
/*TODO*///{
/*TODO*///	R.PC = val;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////****************************************************************************
/*TODO*/// * Return stack pointer
/*TODO*/// ****************************************************************************/
/*TODO*///unsigned tms320c10_get_sp (void)
/*TODO*///{
/*TODO*///	return R.STACK[3];
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////****************************************************************************
/*TODO*/// * Set stack pointer
/*TODO*/// ****************************************************************************/
/*TODO*///void tms320c10_set_sp (unsigned val)
/*TODO*///{
/*TODO*///	R.STACK[3] = val;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////****************************************************************************
/*TODO*/// * Set a specific register
/*TODO*/// ****************************************************************************/
/*TODO*///void tms320c10_set_reg(int regnum, unsigned val)
/*TODO*///{
/*TODO*///	switch( regnum )
/*TODO*///	{
/*TODO*///		case TMS320C10_PC: R.PC = val; break;
/*TODO*///		/* This is actually not a stack pointer, but the stack contents */
/*TODO*///		case TMS320C10_STK3: R.STACK[3] = val; break;
/*TODO*///		case TMS320C10_STR: R.STR = val; break;
/*TODO*///		case TMS320C10_ACC: R.ACC = val; break;
/*TODO*///		case TMS320C10_PREG: R.Preg = val; break;
/*TODO*///		case TMS320C10_TREG: R.Treg = val; break;
/*TODO*///		case TMS320C10_AR0: R.AR[0] = val; break;
/*TODO*///		case TMS320C10_AR1: R.AR[1] = val; break;
/*TODO*///		default:
/*TODO*///			if( regnum <= REG_SP_CONTENTS )
/*TODO*///			{
/*TODO*///				unsigned offset = (REG_SP_CONTENTS - regnum);
/*TODO*///				if( offset < 4 )
/*TODO*///					R.STACK[offset] = val;
/*TODO*///			}
/*TODO*///    }
/*TODO*///}
/*TODO*///
/*TODO*///
}
