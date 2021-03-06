/*****************************************************************************
 *
 *	 z8000.c
 *	 Portable Z8000(2) emulator
 *	 Z8000 MAME interface
 *
 *	 Copyright (C) 1998,1999 Juergen Buchmueller, all rights reserved.
 *	 Bug fixes and MSB_FIRST compliance Ernesto Corvi.
 *
 *	 - This source code is released as freeware for non-commercial purposes.
 *	 - You are free to use and redistribute this code in modified or
 *	   unmodified form, provided you list me in the credits.
 *	 - If you modify this source code, you must add a notice to each modified
 *	   source file that it has been changed.  If you're a nice person, you
 *	   will clearly mark each change too.  :)
 *	 - If you wish to use this for commercial purposes, please contact me at
 *	   pullmoll@t-online.de
 *	 - The author of this copywritten work reserves the right to change the
 *     terms of its usage and license at any time, including retroactively
 *   - This entire notice must remain in the source code.
 *
 *****************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package gr.codebb.arcadeflex.WIP.v037b7.cpu.z8000;

import static gr.codebb.arcadeflex.WIP.v037b7.cpu.z8000.z8000H.*;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.z8000.z8000cpuH.*;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_getactivecpu;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.memory.cpu_readmem16bew;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.memory.cpu_readmem16bew_word;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.memory.cpu_writemem16bew;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.memory.cpu_writemem16bew_word;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.old.mame.memoryH.cpu_readop16;

public class z8000  extends cpu_interface {
    
    public static int[] z8000_ICount = new int[1];
    
    public z8000ops _opcodes = null;
    public z8000cpuH _cpuH = null;
    public z8000tbl _tbl = null;
    
    public z8000() {
        cpu_num = CPU_Z8000;
        num_irqs = 2;
        default_vector = 0;
        overclock = 1.0;
        no_int = Z8000_INT_NONE;
        irq_int = Z8000_NVI;
        nmi_int = Z8000_NMI;
        address_shift = 0;
        address_bits = 16;
        endianess = CPU_IS_BE;
        align_unit = 2;
        max_inst_len = 6;
        abits1 = ABITS1_16;
        abits2 = ABITS2_16;
        abitsmin = ABITS_MIN_16;
        icount = z8000_ICount;
        
        _opcodes = new z8000ops(this);
        _cpuH = new z8000cpuH(this);
        _tbl = new z8000tbl(this);
    }


    @Override
    public void reset(Object param) {
        z8000_reset(param);
    }

    @Override
    public void exit() {
        z8000_exit();
    }

    @Override
    public int execute(int cycles) {
        return z8000_execute(cycles);
    }

    @Override
    public Object init_context() {
        Object reg = new z8000_Regs();
        return reg;
    }

    @Override
    public Object get_context() {
        return z8000_get_context();
    }

    @Override
    public void set_context(Object reg) {
        z8000_set_context(reg);
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
        return z8000_get_pc();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_reg(int regnum, int val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_nmi_line(int linestate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_irq_line(int irqline, int linestate) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        z8000_set_irq_line(irqline, linestate);
    }

    @Override
    public void set_irq_callback(irqcallbacksPtr callback) {
        z8000_set_irq_callback(callback);
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
        return z8000_info(context, regnum);
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
    public int internal_read(int offset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void internal_write(int offset, int data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set_op_base(int pc) {
        cpu_setOPbase16.handler(pc&0xffff);
    }
/*TODO*///	
/*TODO*///	#define VERBOSE 0
/*TODO*///	
/*TODO*///	
/*TODO*///	#if VERBOSE
/*TODO*///	#define LOG(x)	logerror x
/*TODO*///	#else
/*TODO*///	#define LOG(x)
/*TODO*///	#endif
/*TODO*///	
/*TODO*///	static UINT8 z8000_reg_layout[] = {
/*TODO*///		Z8000_PC, Z8000_NSP, Z8000_FCW, Z8000_PSAP, Z8000_REFRESH, -1,
/*TODO*///		Z8000_R0, Z8000_R1, Z8000_R2, Z8000_R3, Z8000_R4, Z8000_R5, Z8000_R6, Z8000_R7, -1,
/*TODO*///		Z8000_R8, Z8000_R9, Z8000_R10,Z8000_R11,Z8000_R12,Z8000_R13,Z8000_R14,Z8000_R15,-1,
/*TODO*///		Z8000_IRQ_REQ, Z8000_IRQ_SRV, Z8000_IRQ_VEC, Z8000_NMI_STATE, Z8000_NVI_STATE, Z8000_VI_STATE, 0
/*TODO*///	};
/*TODO*///	
/*TODO*///	static UINT8 z8000_win_layout[] = {
/*TODO*///		 0, 0,80, 4,	/* register window (top rows) */
/*TODO*///		 0, 5,26,17,	/* disassembler window (left colums) */
/*TODO*///		27, 5,53, 8,	/* memory #1 window (right, upper middle) */
/*TODO*///		27,14,53, 8,	/* memory #2 window (right, lower middle) */
/*TODO*///		 0,23,80, 1,	/* command line window (bottom rows) */
/*TODO*///	};

	/* opcode execution table */
	public static Z8000_exec[] z8000_exec = null;

   
        public static class z8000_reg_file {
	    int[] /*UINT8*/   B=new int[16]; /* RL0,RH0,RL1,RH1...RL7,RH7 */
	    int[] /*UINT16*/  W=new int[16]; /* R0,R1,R2...R15 */
	    int[] /*UINT32*/  L=new int[8];  /* RR0,RR2,RR4..RR14 */
	    int[] /*UINT64*/  Q=new int[4];  /* RQ0,RQ4,..RQ12 */
	};
	
	public static class z8000_Regs {
	    int[]  op = new int[4];      /* opcodes/data of current instruction */
            int	ppc;		/* previous program counter */
	    int  pc;         /* program counter */
	    int  psap;       /* program status pointer */
	    int  fcw;        /* flags and control word */
	    int  refresh;    /* refresh timer/counter */
	    int  nsp;        /* system stack pointer */
	    int  irq_req;    /* CPU is halted, interrupt or trap request */
	    int  irq_srv;    /* serviced interrupt request */
	    int  irq_vec;    /* interrupt vector */
	    z8000_reg_file regs=new z8000_reg_file();/* registers */
            int nmi_state;		/* NMI line state */
            int[] irq_state = new int[2];	/* IRQ line states (NVI, VI) */
	    irqcallbacksPtr irq_callback;
	};
	

	
	/* current CPU context */
	public z8000_Regs Z=new z8000_Regs();
	
	/* zero, sign and parity flags for logical byte operations */
	public static int[] z8000_zsp=new int[256];

/*TODO*///	/* conversion table for Z8000 DAB opcode */
/*TODO*///	
/*TODO*///	/**************************************************************************
/*TODO*///	 * This is the register file layout:
/*TODO*///	 *
/*TODO*///	 * BYTE 	   WORD 		LONG		   QUAD
/*TODO*///	 * msb	 lsb	   bits 		  bits			 bits
/*TODO*///	 * RH0 - RL0   R 0 15- 0	RR 0  31-16    RQ 0  63-48
/*TODO*///	 * RH1 - RL1   R 1 15- 0		  15- 0 		 47-32
/*TODO*///	 * RH2 - RL2   R 2 15- 0	RR 2  31-16 		 31-16
/*TODO*///	 * RH3 - RL3   R 3 15- 0		  15- 0 		 15- 0
/*TODO*///	 * RH4 - RL4   R 4 15- 0	RR 4  31-16    RQ 4  63-48
/*TODO*///	 * RH5 - RL5   R 5 15- 0		  15- 0 		 47-32
/*TODO*///	 * RH6 - RL6   R 6 15- 0	RR 6  31-16 		 31-16
/*TODO*///	 * RH7 - RL7   R 7 15- 0		  15- 0 		 15- 0
/*TODO*///	 *			   R 8 15- 0	RR 8  31-16    RQ 8  63-48
/*TODO*///	 *			   R 9 15- 0		  15- 0 		 47-32
/*TODO*///	 *			   R10 15- 0	RR10  31-16 		 31-16
/*TODO*///	 *			   R11 15- 0		  15- 0 		 15- 0
/*TODO*///	 *			   R12 15- 0	RR12  31-16    RQ12  63-48
/*TODO*///	 *			   R13 15- 0		  15- 0 		 47-32
/*TODO*///	 *			   R14 15- 0	RR14  31-16 		 31-16
/*TODO*///	 *			   R15 15- 0		  15- 0 		 15- 0
/*TODO*///	 *
/*TODO*///	 * Note that for LSB_FIRST machines we have the case that the RR registers
/*TODO*///	 * use the lower numbered R registers in the higher bit positions.
/*TODO*///	 * And also the RQ registers use the lower numbered RR registers in the
/*TODO*///	 * higher bit positions.
/*TODO*///	 * That's the reason for the ordering in the following pointer table.
/*TODO*///	 **************************************************************************/
/*TODO*///	#ifdef	LSB_FIRST
/*TODO*///		/* pointers to byte (8bit) registers */
/*TODO*///		static UINT8	*pRB[16] =
/*TODO*///		{
/*TODO*///			&Z.regs.B[ 7],&Z.regs.B[ 5],&Z.regs.B[ 3],&Z.regs.B[ 1],
/*TODO*///			&Z.regs.B[15],&Z.regs.B[13],&Z.regs.B[11],&Z.regs.B[ 9],
/*TODO*///			&Z.regs.B[ 6],&Z.regs.B[ 4],&Z.regs.B[ 2],&Z.regs.B[ 0],
/*TODO*///			&Z.regs.B[14],&Z.regs.B[12],&Z.regs.B[10],&Z.regs.B[ 8]
/*TODO*///		};
/*TODO*///	
/*TODO*///              static UINT16	*pRW[16] =
/*TODO*///		{
/*TODO*///	        &Z.regs.W[ 3],&Z.regs.W[ 2],&Z.regs.W[ 1],&Z.regs.W[ 0],
/*TODO*///	        &Z.regs.W[ 7],&Z.regs.W[ 6],&Z.regs.W[ 5],&Z.regs.W[ 4],
/*TODO*///	        &Z.regs.W[11],&Z.regs.W[10],&Z.regs.W[ 9],&Z.regs.W[ 8],
/*TODO*///	        &Z.regs.W[15],&Z.regs.W[14],&Z.regs.W[13],&Z.regs.W[12]
/*TODO*///	    };
/*TODO*///	
/*TODO*///	    /* pointers to long (32bit) registers */
/*TODO*///		static UINT32	*pRL[16] =
/*TODO*///		{
/*TODO*///			&Z.regs.L[ 1],&Z.regs.L[ 1],&Z.regs.L[ 0],&Z.regs.L[ 0],
/*TODO*///			&Z.regs.L[ 3],&Z.regs.L[ 3],&Z.regs.L[ 2],&Z.regs.L[ 2],
/*TODO*///			&Z.regs.L[ 5],&Z.regs.L[ 5],&Z.regs.L[ 4],&Z.regs.L[ 4],
/*TODO*///			&Z.regs.L[ 7],&Z.regs.L[ 7],&Z.regs.L[ 6],&Z.regs.L[ 6]
/*TODO*///	    };
/*TODO*///	
/*TODO*///	#else	/* MSB_FIRST */
	
	    /* pointers to byte (8bit) registers */
		public int pRB(int _val)
		{
                    switch (_val){
			case 0:
                            return (Z.regs.B[ 0]&0xff);
                        case 1:
                            return (Z.regs.B[ 2]&0xff);
                        case 2:
                            return (Z.regs.B[ 4]&0xff);
                        case 3:
                            return (Z.regs.B[ 6]&0xff);
			case 4:
                            return (Z.regs.B[ 8]&0xff);
                        case 5:
                            return (Z.regs.B[10]&0xff);
                        case 6:
                            return (Z.regs.B[12]&0xff);
                        case 7:
                            return (Z.regs.B[14]&0xff);
			case 8:
                            return (Z.regs.B[ 1]&0xff);
                        case 9:
                            return (Z.regs.B[ 3]&0xff);
                        case 10:
                            return (Z.regs.B[ 5]&0xff);
                        case 11:
                            return (Z.regs.B[ 7]&0xff);
			case 12:
                            return (Z.regs.B[ 9]&0xff);
                        case 13:
                            return (Z.regs.B[11]&0xff);
                        case 14:
                            return (Z.regs.B[13]&0xff);
                        case 15:
                            return (Z.regs.B[15]&0xff);
                        default:
                            throw new UnsupportedOperationException("unsupported");
                    }
		};
                
                public void pRB(int _pos, int _val)
		{
                    switch (_pos){
                        case 0:
                            Z.regs.B[ 0] = _val & 0xFF;
                            break;
                        case 1:
                            Z.regs.B[ 2] = _val & 0xFF;
                            break;
                        case 2:
                            Z.regs.B[ 4] = _val & 0xFF;
                            break;
                        case 3:
                            Z.regs.B[ 6] = _val & 0xFF;
                            break;
                        case 4:
                            Z.regs.B[ 8] = _val & 0xFF;
                            break;
                        case 5:
                            Z.regs.B[ 10] = _val & 0xFF;
                            break;
                        case 6:
                            Z.regs.B[ 12] = _val & 0xFF;
                            break;
                        case 7:
                            Z.regs.B[ 14] = _val & 0xFF;
                            break;
                        case 8:
                            Z.regs.B[ 1] = _val & 0xFF;
                            break;
                        case 9:
                            Z.regs.B[ 3] = _val & 0xFF;
                            break;
                        case 10:
                            Z.regs.B[ 5] = _val & 0xFF;
                            break;
                        case 11:
                            Z.regs.B[ 7] = _val & 0xFF;
                            break;
                        case 12:
                            Z.regs.B[ 9] = _val & 0xFF;
                            break;
                        case 13:
                            Z.regs.B[ 11] = _val & 0xFF;
                            break;
                        case 14:
                            Z.regs.B[ 13] = _val & 0xFF;
                            break;
                        case 15:
                            Z.regs.B[ 15] = _val & 0xFF;
                            break;
                        default:
                            throw new UnsupportedOperationException("unsupported");
                    }
	    };
	
                public int pRW(int _val)
		{
                    switch (_val){
                        case 0:
                            return (Z.regs.W[ 0] & 0xFFFF);
                            //break;
                        case 1:
                            return (Z.regs.W[ 1] & 0xFFFF);
                        case 2:
                            return (Z.regs.W[ 2] & 0xFFFF);
                        case 3:
                            return (Z.regs.W[ 3] & 0xFFFF);
                        case 4:
                            return (Z.regs.W[ 4] & 0xFFFF);
                        case 5:
                            return (Z.regs.W[ 5] & 0xFFFF);
                        case 6:
                            return (Z.regs.W[ 6] & 0xFFFF);
                        case 7:
                            return (Z.regs.W[ 7] & 0xFFFF);
                        case 8:
                            return (Z.regs.W[ 8] & 0xFFFF);
                        case 9:
                            return (Z.regs.W[ 9] & 0xFFFF);
                        case 10:
                            return (Z.regs.W[10] & 0xFFFF);
                        case 11:
                            return (Z.regs.W[11] & 0xFFFF);
                        case 12:
                            return (Z.regs.W[12] & 0xFFFF);
                        case 13:
                            return (Z.regs.W[13] & 0xFFFF);
                        case 14:
                            return (Z.regs.W[14] & 0xFFFF);
                        case 15:
                            return (Z.regs.W[15] & 0xFFFF);
                        default:
                            throw new UnsupportedOperationException("unsupported");
                    }
	    };
            
            public void pRW(int _pos, int _val)
		{
                    switch (_pos){
                        case 0:
                            Z.regs.W[ 0] = _val & 0xFFFF;
                            break;
                        case 1:
                            Z.regs.W[ 1] = _val & 0xFFFF;
                            break;
                        case 2:
                            Z.regs.W[ 2] = _val & 0xFFFF;
                            break;
                        case 3:
                            Z.regs.W[ 3] = _val & 0xFFFF;
                            break;
                        case 4:
                            Z.regs.W[ 4] = _val & 0xFFFF;
                            break;
                        case 5:
                            Z.regs.W[ 5] = _val & 0xFFFF;
                            break;
                        case 6:
                            Z.regs.W[ 6] = _val & 0xFFFF;
                            break;
                        case 7:
                            Z.regs.W[ 7] = _val & 0xFFFF;
                            break;
                        case 8:
                            Z.regs.W[ 8] = _val & 0xFFFF;
                            break;
                        case 9:
                            Z.regs.W[ 9] = _val & 0xFFFF;
                            break;
                        case 10:
                            Z.regs.W[ 10] = _val & 0xFFFF;
                            break;
                        case 11:
                            Z.regs.W[ 11] = _val & 0xFFFF;
                            break;
                        case 12:
                            Z.regs.W[ 12] = _val & 0xFFFF;
                            break;
                        case 13:
                            Z.regs.W[ 13] = _val & 0xFFFF;
                            break;
                        case 14:
                            Z.regs.W[ 14] = _val & 0xFFFF;
                            break;
                        case 15:
                            Z.regs.W[ 15] = _val & 0xFFFF;
                            break;
                        default:
                            throw new UnsupportedOperationException("unsupported");
                    }
	    };            
/*TODO*///		/* pointers to word (16bit) registers */
/*TODO*///		static UINT16	*pRW[16] =
/*TODO*///		{
/*TODO*///			&Z.regs.W[ 0],&Z.regs.W[ 1],&Z.regs.W[ 2],&Z.regs.W[ 3],
/*TODO*///			&Z.regs.W[ 4],&Z.regs.W[ 5],&Z.regs.W[ 6],&Z.regs.W[ 7],
/*TODO*///			&Z.regs.W[ 8],&Z.regs.W[ 9],&Z.regs.W[10],&Z.regs.W[11],
/*TODO*///			&Z.regs.W[12],&Z.regs.W[13],&Z.regs.W[14],&Z.regs.W[15]
/*TODO*///		};
	
		/* pointers to long (32bit) registers */
		public int pRL(int _val)
		{
                    switch (_val){
                        case 0:
                            return Z.regs.L[ 0];
			case 1:
                            return Z.regs.L[ 0];
                        case 2:
                            return Z.regs.L[ 1];
                        case 3:
                            return Z.regs.L[ 1];
			case 4:
                            return Z.regs.L[ 2];
                        case 5:
                            return Z.regs.L[ 2];
                        case 6:
                            return Z.regs.L[ 3];
                        case 7:
                            return Z.regs.L[ 3];
			case 8:
                            return Z.regs.L[ 4];
                        case 9:
                            return Z.regs.L[ 4];
                        case 10:
                            return Z.regs.L[ 5];
                        case 11:
                            return Z.regs.L[ 5];
			case 12:
                            return Z.regs.L[ 6];
                        case 13:
                            return Z.regs.L[ 6];
                        case 14:
                            return Z.regs.L[ 7];
                        case 15:
                            return Z.regs.L[ 7];
                        default:
                            throw new UnsupportedOperationException("unsupported");
                    }
                    
                    //return 0;
		};

                public void pRL(int _pos, int _val)
		{
                    switch (_pos){
                        case 0:
                            Z.regs.L[ 0] = _val;
                            break;
			case 1:
                            Z.regs.L[ 0] = _val;
                            break;
                        case 2:
                            Z.regs.L[ 1] = _val;
                            break;
                        case 3:
                            Z.regs.L[ 1] = _val;
                            break;
			case 4:
                            Z.regs.L[ 2] = _val;
                            break;
                        case 5:
                            Z.regs.L[ 2] = _val;
                            break;
                        case 6:
                            Z.regs.L[ 3] = _val;
                            break;
                        case 7:
                            Z.regs.L[ 3] = _val;
                            break;
			case 8:
                            Z.regs.L[ 4] = _val;
                            break;
                        case 9:
                            Z.regs.L[ 4] = _val;
                            break;
                        case 10:
                            Z.regs.L[ 5] = _val;
                            break;
                        case 11:
                            Z.regs.L[ 5] = _val;
                            break;
			case 12:
                            Z.regs.L[ 6] = _val;
                            break;
                        case 13:
                            Z.regs.L[ 6] = _val;
                            break;
                        case 14:
                            Z.regs.L[ 7] = _val;
                            break;
                        case 15:
                            Z.regs.L[ 7] = _val;
                            break;
                        default:
                            throw new UnsupportedOperationException("unsupported");
                    }
                    
		};
/*TODO*///	#endif
/*TODO*///	
	/* pointers to quad word (64bit) registers */
        public int pRQ(int _val)
        {
            switch (_val){
                case 0:
                    return Z.regs.Q[ 0];
                case 1:
                    return Z.regs.Q[ 0];
                case 2:
                    return Z.regs.Q[ 0];
                case 3:
                    return Z.regs.Q[ 0];
                case 4:
                    return Z.regs.Q[ 1];
                case 5:
                    return Z.regs.Q[ 1];
                case 6:
                    return Z.regs.Q[ 1];
                case 7:
                    return Z.regs.Q[ 1];
                case 8:
                    return Z.regs.Q[ 2];
                case 9:
                    return Z.regs.Q[ 2];
                case 10:
                    return Z.regs.Q[ 2];
                case 11:
                    return Z.regs.Q[ 2];
                case 12:
                    return Z.regs.Q[ 3];
                case 13:
                    return Z.regs.Q[ 3];
                case 14:
                    return Z.regs.Q[ 3];
                case 15:
                    return Z.regs.Q[ 3];
                default:
                    throw new UnsupportedOperationException("unsupported");
            }
        };
        
        public void pRQ(int pos, int _val)
        {
            switch (pos){
                case 0:
                    Z.regs.Q[ 0]=_val;
                    break;
                case 1:
                    Z.regs.Q[ 0]=_val;
                    break;
                case 2:
                    Z.regs.Q[ 0]=_val;
                    break;
                case 3:
                    Z.regs.Q[ 0]=_val;
                    break;
                case 4:
                    Z.regs.Q[ 1]=_val;
                    break;
                case 5:
                    Z.regs.Q[ 1]=_val;
                    break;
                case 6:
                    Z.regs.Q[ 1]=_val;
                    break;
                case 7:
                    Z.regs.Q[ 1]=_val;
                    break;
                case 8:
                    Z.regs.Q[ 2]=_val;
                    break;
                case 9:
                    Z.regs.Q[ 2]=_val;
                    break;
                case 10:
                    Z.regs.Q[ 2]=_val;
                    break;
                case 11:
                    Z.regs.Q[ 2]=_val;
                    break;
                case 12:
                    Z.regs.Q[ 3]=_val;
                    break;
                case 13:
                    Z.regs.Q[ 3]=_val;
                    break;
                case 14:
                    Z.regs.Q[ 3]=_val;
                    break;
                case 15:
                    Z.regs.Q[ 3]=_val;
                    break;
                default:
                    throw new UnsupportedOperationException("unsupported");
            }
        };
	
	public int RDOP()
	{
            ///if ((Z.pc == 0xffff)/* || (Z.pc == 0xfffe)*/)
            //    Z.pc = 0;
            
            int res = cpu_readop16(Z.pc & 0xffff);
	    //Z.pc = (Z.pc & 0xffff) + 2;
            
            //Z.pc &= 0xffff;
            Z.pc += 2;
            
	    return res;
	}
	
        public int RDMEM_B(int addr)
	{
		return (cpu_readmem16bew(addr & 0xffff) & 0xff);
	}
	
	public int RDMEM_W(int addr)
	{
		addr &= ~1;
		return (cpu_readmem16bew_word(addr & 0xffff) & 0xffff);
	}
	
	public int RDMEM_L(int addr)
	{
		int result;
		addr &= ~1;
		result = cpu_readmem16bew_word(addr & 0xffff) << 16;
		return (result + cpu_readmem16bew_word((addr + 2) & 0xffff) & 0xffffffff);
	}
	
	public void WRMEM_B(int addr, int value)
	{
		cpu_writemem16bew(addr&0xffff, value&0xff);
	}
	
	public void WRMEM_W(int addr, int value)
	{
		addr = (addr & 0xffff) & ~1;
		cpu_writemem16bew_word(addr&0xffff, value& 0xffff);
	}
	
	public void WRMEM_L(int addr, int value)
	{
		addr &= ~1;
		cpu_writemem16bew_word(addr&0xffff, value >> 16);
		cpu_writemem16bew_word((addr + 2)&0xffff, value /*& 0xffff*/);
	}
	
/*TODO*///	INLINE UINT8 RDPORT_B(int mode, UINT16 addr)
/*TODO*///	{
/*TODO*///		if( mode == 0 )
/*TODO*///		{
/*TODO*///			return cpu_readport(addr);
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			/* how to handle MMU reads? */
/*TODO*///			return 0x00;
/*TODO*///		}
/*TODO*///	}
	
	public int RDPORT_W(int mode, int addr)
	{
		if( mode == 0 )
		{
			return cpu_readport((addr & 0xffff)) +
				  (cpu_readport((addr+1)&0xffff) << 8);
		}
		else
		{
			/* how to handle MMU reads? */
			return 0x0000;
		}
	}
	
/*TODO*///	INLINE UINT32 RDPORT_L(int mode, UINT16 addr)
/*TODO*///	{
/*TODO*///		if( mode == 0 )
/*TODO*///		{
/*TODO*///			return	cpu_readport((UINT16)(addr)) +
/*TODO*///				   (cpu_readport((UINT16)(addr+1)) <<  8) +
/*TODO*///				   (cpu_readport((UINT16)(addr+2)) << 16) +
/*TODO*///				   (cpu_readport((UINT16)(addr+3)) << 24);
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			/* how to handle MMU reads? */
/*TODO*///			return 0x00000000;
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	INLINE void WRPORT_B(int mode, UINT16 addr, UINT8 value)
/*TODO*///	{
/*TODO*///		if( mode == 0 )
/*TODO*///		{
/*TODO*///	        cpu_writeport(addr,value);
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			/* how to handle MMU writes? */
/*TODO*///	    }
/*TODO*///	}
	
	public void WRPORT_W(int mode, int addr, int value)
	{
		if( mode == 0 )
		{
			cpu_writeport((addr /*& 0xffff*/),value & 0xff);
			cpu_writeport((addr+1)/*&0xffff*/,(value >> 8) & 0xff);
		}
		else
		{
			/* how to handle MMU writes? */
	    }
	}
	
/*TODO*///	INLINE void WRPORT_L(int mode, UINT16 addr, UINT32 value)
/*TODO*///	{
/*TODO*///		if( mode == 0 )
/*TODO*///		{
/*TODO*///			cpu_writeport((UINT16)(addr),value & 0xff);
/*TODO*///			cpu_writeport((UINT16)(addr+1),(value >> 8) & 0xff);
/*TODO*///			cpu_writeport((UINT16)(addr+2),(value >> 16) & 0xff);
/*TODO*///			cpu_writeport((UINT16)(addr+3),(value >> 24) & 0xff);
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			/* how to handle MMU writes? */
/*TODO*///		}
/*TODO*///	}
	
	
	public void set_irq(int type)
	{
            //System.out.println("set_irq!!!!");
        
	    switch ((type >> 8) & 255)
	    {
	        case Z8000_INT_NONE >> 8:
	            return;
	        case Z8000_TRAP >> 8:
	            if (_cpuH.IRQ_SRV() >= Z8000_TRAP)
	                return; /* double TRAP.. very bad :( */
	            _cpuH.IRQ_REQ( type );
	            break;
	        case Z8000_NMI >> 8:
	            if (_cpuH.IRQ_SRV() >= Z8000_NMI)
	                return; /* no NMIs inside trap */
	            _cpuH.IRQ_REQ( type );
	            break;
	        case Z8000_SEGTRAP >> 8:
	            if (_cpuH.IRQ_SRV() >= Z8000_SEGTRAP)
	                return; /* no SEGTRAPs inside NMI/TRAP */
	            _cpuH.IRQ_REQ( type );
	            break;
	        case Z8000_NVI >> 8:
	            if (_cpuH.IRQ_SRV() >= Z8000_NVI)
	                return; /* no NVIs inside SEGTRAP/NMI/TRAP */
	            _cpuH.IRQ_REQ( type );
	            break;
	        case Z8000_VI >> 8:
	            if (_cpuH.IRQ_SRV() >= Z8000_VI)
	                return; /* no VIs inside NVI/SEGTRAP/NMI/TRAP */
	            _cpuH.IRQ_REQ( type );
	            break;
	        case Z8000_SYSCALL >> 8:
/*TODO*///	            LOG(("Z8K#%d SYSCALL $%02x\n", cpu_getactivecpu(), type & 0xff));
	            _cpuH.IRQ_REQ( type );
	            break;
	        default:
	            logerror("Z8000 invalid Cause_Interrupt %04x\n", type);
	            return;
	    }
	    /* set interrupt request flag, reset HALT flag */
	    _cpuH.IRQ_REQ( type & ~Z8000_HALT );
	}
	
	
	public void Interrupt()
	{
            
            //System.out.println("INTERRUPT method!!!!");
            //throw new UnsupportedOperationException("unsupported");
	    int fcw = Z.fcw & 0xffff;
	
	    if ((_cpuH.IRQ_REQ() & Z8000_NVI) != 0)
	    {
	        int type = (Z.irq_callback).handler(0);
	        set_irq(type);
	    }
	
	    if ((_cpuH.IRQ_REQ() & Z8000_VI) != 0)
	    {
	        int type = (Z.irq_callback).handler(1);
	        set_irq(type);
	    }
	
	   /* trap ? */
	   if ((_cpuH.IRQ_REQ() & Z8000_TRAP) != 0)
	   {
	        _opcodes.CHANGE_FCW(fcw | F_S_N);/* swap to system stack */
	        _opcodes.PUSHW( _cpuH.SP, Z.pc );        /* save current PC */
	        _opcodes.PUSHW( _cpuH.SP, fcw );       /* save current FCW */
	        _opcodes.PUSHW( _cpuH.SP, _cpuH.IRQ_REQ() );   /* save interrupt/trap type tag */
	        _cpuH.IRQ_SRV( _cpuH.IRQ_REQ() );
	        _cpuH.IRQ_REQ( _cpuH.IRQ_REQ() & ~Z8000_TRAP );
	        Z.pc = _cpuH.TRAP();
/*TODO*///	        LOG(("Z8K#%d trap $%04x\n", cpu_getactivecpu(), PC ));
	   }
	   else
	   if ((_cpuH.IRQ_REQ() & Z8000_SYSCALL) != 0)
	   {
	        _opcodes.CHANGE_FCW(fcw | F_S_N);/* swap to system stack */
	        _opcodes.PUSHW( _cpuH.SP, Z.pc );        /* save current PC */
	        _opcodes.PUSHW( _cpuH.SP, fcw );       /* save current FCW */
	        _opcodes.PUSHW( _cpuH.SP, _cpuH.IRQ_REQ() );   /* save interrupt/trap type tag */
	        _cpuH.IRQ_SRV( _cpuH.IRQ_REQ() );
	        _cpuH.IRQ_REQ( _cpuH.IRQ_REQ() & ~Z8000_SYSCALL );
	        Z.pc = _cpuH.SYSCALL();
/*TODO*///	        LOG(("Z8K#%d syscall $%04x\n", cpu_getactivecpu(), PC ));
	   }
	   else
	   if ((_cpuH.IRQ_REQ() & Z8000_SEGTRAP) != 0)
	   {
	        _opcodes.CHANGE_FCW(fcw | F_S_N);/* swap to system stack */
	        _opcodes.PUSHW( _cpuH.SP, Z.pc );        /* save current PC */
	        _opcodes.PUSHW( _cpuH.SP, fcw );       /* save current FCW */
	        _opcodes.PUSHW( _cpuH.SP, _cpuH.IRQ_REQ() );   /* save interrupt/trap type tag */
	        _cpuH.IRQ_SRV( _cpuH.IRQ_REQ() );
	        _cpuH.IRQ_REQ( _cpuH.IRQ_REQ() & ~Z8000_SEGTRAP );
	        Z.pc = _cpuH.SEGTRAP();
/*TODO*///	        LOG(("Z8K#%d segtrap $%04x\n", cpu_getactivecpu(), PC ));
	   }
	   else
	   if ((_cpuH.IRQ_REQ() & Z8000_NMI) != 0)
	   {
	        _opcodes.CHANGE_FCW(fcw | F_S_N);/* swap to system stack */
	        _opcodes.PUSHW( _cpuH.SP, Z.pc );        /* save current PC */
	        _opcodes.PUSHW( _cpuH.SP, fcw );       /* save current FCW */
	        _opcodes.PUSHW( _cpuH.SP, _cpuH.IRQ_REQ() );   /* save interrupt/trap type tag */
	        _cpuH.IRQ_SRV( _cpuH.IRQ_REQ() );
	        fcw = RDMEM_W( _cpuH.NMI() );
	        Z.pc = RDMEM_W( _cpuH.NMI() + 2 );
	        _cpuH.IRQ_REQ( _cpuH.IRQ_REQ() & ~Z8000_NMI );
	        _opcodes.CHANGE_FCW(fcw);
	        Z.pc = _cpuH.NMI();
/*TODO*///	        LOG(("Z8K#%d NMI $%04x\n", cpu_getactivecpu(), PC ));
	    }
	    else
	    if ( (_cpuH.IRQ_REQ() & Z8000_NVI)!=0 && (Z.fcw & F_NVIE)!=0 )
	    {
	        _opcodes.CHANGE_FCW(fcw | F_S_N);/* swap to system stack */
	        _opcodes.PUSHW( _cpuH.SP, Z.pc );        /* save current PC */
	        _opcodes.PUSHW( _cpuH.SP, fcw );       /* save current FCW */
	        _opcodes.PUSHW( _cpuH.SP, _cpuH.IRQ_REQ() );   /* save interrupt/trap type tag */
	        _cpuH.IRQ_SRV( _cpuH.IRQ_REQ() );
	        fcw = RDMEM_W( _cpuH.NVI() );
	        Z.pc = RDMEM_W( _cpuH.NVI() + 2 );
	        _cpuH.IRQ_REQ( _cpuH.IRQ_REQ() & ~Z8000_NVI );
	        _opcodes.CHANGE_FCW(fcw);
/*TODO*///	        LOG(("Z8K#%d NVI $%04x\n", cpu_getactivecpu(), PC ));
	    }
	    else
	    if ( (_cpuH.IRQ_REQ() & Z8000_VI)!=0 && (Z.fcw & F_VIE)!=0 )
	    {
	        _opcodes.CHANGE_FCW(fcw | F_S_N);/* swap to system stack */
	        _opcodes.PUSHW( _cpuH.SP, Z.pc );        /* save current PC */
	        _opcodes.PUSHW( _cpuH.SP, fcw );       /* save current FCW */
	        _opcodes.PUSHW( _cpuH.SP, _cpuH.IRQ_REQ() );   /* save interrupt/trap type tag */
	        _cpuH.IRQ_SRV( _cpuH.IRQ_REQ() );
	        fcw = RDMEM_W( _cpuH.IRQ_VEC() );
	        Z.pc = RDMEM_W( _cpuH.VEC00() + 2 * (_cpuH.IRQ_REQ() & 0xff) );
	        _cpuH.IRQ_REQ( _cpuH.IRQ_REQ() & ~Z8000_VI );
	        _opcodes.CHANGE_FCW(fcw);
/*TODO*///	        LOG(("Z8K#%d VI [$%04x/$%04x] fcw $%04x, pc $%04x\n", cpu_getactivecpu(), IRQ_VEC, VEC00 + VEC00 + 2 * (IRQ_REQ & 0xff), FCW, PC ));
	    }
	}
	
	
	public void z8000_reset(Object param)
	{
            //System.out.println("z8000_reset");
	    _tbl.z8000_init();
/*TODO*///            memset(&Z, 0, sizeof(z8000_Regs));
            Z = new z8000_Regs();
            /*irqcallbacksPtr _tmpIRQ = null;
            if (Z!=null){
                _tmpIRQ = Z.irq_callback;
            }
                
            Z = new z8000_Regs();
            Z.irq_callback=_tmpIRQ;*/
            
            Z.fcw = RDMEM_W( 2 ) & 0xffff; /* get reset FCW */
            Z.pc = RDMEM_W( 4 ) & 0xffff; /* get reset PC  */
            change_pc16bew(Z.pc & 0xffff);
            
	}
        
        private String _kkStr;
	
	public void z8000_exit()
	{
		_tbl.z8000_deinit();
	}
        
        public int _lastOpcode=0;
        public boolean _bTrace=true;
	
	public int z8000_execute(int cycles)
	{
            //System.out.println("z8000_execute");
	    z8000_ICount[0] = cycles;
	
	    do
	    {
	        /* any interrupt request pending? */
                /*if (Z.irq_req != 0)
                    System.out.println(Z.irq_req);*/
	        if (_cpuH.IRQ_REQ() != 0)
				Interrupt();
	
/*TODO*///			CALL_MAME_DEBUG;
	
		if ((_cpuH.IRQ_REQ() & Z8000_HALT) != 0)
	        {
	            z8000_ICount[0] = 0;
	        }
	        else
	        {
	            Z8000_exec exec;
	            Z.op[0] = RDOP();
                    _kkStr="opcode="+z8000_exec[Z.op[0]].dasm;
                    if (_bTrace && cpu_getactivecpu()==2){
                        //System.out.println(_kkStr);
                    }
	            exec = z8000_exec[Z.op[0]];
	
	            if (exec.size > 1)
	                Z.op[1] = RDOP();
	            if (exec.size > 2)
	                Z.op[2] = RDOP();
	
	            z8000_ICount[0] -= exec.cycles;
	            (exec.opcode).handler();
                    
                    _lastOpcode=Z.op[0];
	        }
	    } while (z8000_ICount[0] > 0);
	
	    return cycles - z8000_ICount[0];
	
	}
	
	public Object z8000_get_context()
	{
/*TODO*///		if (dst != 0)
/*TODO*///			*(z8000_Regs*)dst = Z;
/*TODO*///	    return sizeof(z8000_Regs);
            return Z;
	}
	
	public void z8000_set_context(Object src)
	{
		if (src != null)
		{
			Z = (z8000_Regs)src;
                        change_pc16bew(Z.pc & 0xffff);                        
		}
	}
	
	public int z8000_get_pc()
	{
	    return (Z.pc & 0xffff);
	}
	
/*TODO*///	void z8000_set_pc(unsigned val)
/*TODO*///	{
/*TODO*///		PC = val;
/*TODO*///		change_pc16bew(PC);
/*TODO*///	}
/*TODO*///	
/*TODO*///	unsigned z8000_get_sp(void)
/*TODO*///	{
/*TODO*///		return NSP;
/*TODO*///	}
/*TODO*///	
/*TODO*///	void z8000_set_sp(unsigned val)
/*TODO*///	{
/*TODO*///		NSP = val;
/*TODO*///	}
/*TODO*///	
/*TODO*///	unsigned z8000_get_reg(int regnum)
/*TODO*///	{
/*TODO*///		switch( regnum )
/*TODO*///		{
/*TODO*///			case Z8000_PC: return PC;
/*TODO*///	        case Z8000_NSP: return NSP;
/*TODO*///	        case Z8000_FCW: return FCW;
/*TODO*///			case Z8000_PSAP: return PSAP;
/*TODO*///			case Z8000_REFRESH: return REFRESH;
/*TODO*///			case Z8000_IRQ_REQ: return IRQ_REQ;
/*TODO*///			case Z8000_IRQ_SRV: return IRQ_SRV;
/*TODO*///			case Z8000_IRQ_VEC: return IRQ_VEC;
/*TODO*///			case Z8000_R0: return RW( 0);
/*TODO*///			case Z8000_R1: return RW( 1);
/*TODO*///			case Z8000_R2: return RW( 2);
/*TODO*///			case Z8000_R3: return RW( 3);
/*TODO*///			case Z8000_R4: return RW( 4);
/*TODO*///			case Z8000_R5: return RW( 5);
/*TODO*///			case Z8000_R6: return RW( 6);
/*TODO*///			case Z8000_R7: return RW( 7);
/*TODO*///			case Z8000_R8: return RW( 8);
/*TODO*///			case Z8000_R9: return RW( 9);
/*TODO*///			case Z8000_R10: return RW(10);
/*TODO*///			case Z8000_R11: return RW(11);
/*TODO*///			case Z8000_R12: return RW(12);
/*TODO*///			case Z8000_R13: return RW(13);
/*TODO*///			case Z8000_R14: return RW(14);
/*TODO*///			case Z8000_R15: return RW(15);
/*TODO*///			case Z8000_NMI_STATE: return Z.nmi_state;
/*TODO*///			case Z8000_NVI_STATE: return Z.irq_state[0];
/*TODO*///			case Z8000_VI_STATE: return Z.irq_state[1];
/*TODO*///			case REG_PREVIOUSPC: return PPC;
/*TODO*///			default:
/*TODO*///				if( regnum <= REG_SP_CONTENTS )
/*TODO*///				{
/*TODO*///					unsigned offset = NSP + 2 * (REG_SP_CONTENTS - regnum);
/*TODO*///					if( offset < 0xffff )
/*TODO*///						return RDMEM_W( offset );
/*TODO*///				}
/*TODO*///		}
/*TODO*///	    return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	void z8000_set_reg(int regnum, unsigned val)
/*TODO*///	{
/*TODO*///		switch( regnum )
/*TODO*///		{
/*TODO*///			case Z8000_PC: PC = val; break;
/*TODO*///			case Z8000_NSP: NSP = val; break;
/*TODO*///			case Z8000_FCW: FCW = val; break;
/*TODO*///			case Z8000_PSAP: PSAP = val; break;
/*TODO*///			case Z8000_REFRESH: REFRESH = val; break;
/*TODO*///			case Z8000_IRQ_REQ: IRQ_REQ = val; break;
/*TODO*///			case Z8000_IRQ_SRV: IRQ_SRV = val; break;
/*TODO*///			case Z8000_IRQ_VEC: IRQ_VEC = val; break;
/*TODO*///			case Z8000_R0: RW( 0) = val; break;
/*TODO*///			case Z8000_R1: RW( 1) = val; break;
/*TODO*///			case Z8000_R2: RW( 2) = val; break;
/*TODO*///			case Z8000_R3: RW( 3) = val; break;
/*TODO*///			case Z8000_R4: RW( 4) = val; break;
/*TODO*///			case Z8000_R5: RW( 5) = val; break;
/*TODO*///			case Z8000_R6: RW( 6) = val; break;
/*TODO*///			case Z8000_R7: RW( 7) = val; break;
/*TODO*///			case Z8000_R8: RW( 8) = val; break;
/*TODO*///			case Z8000_R9: RW( 9) = val; break;
/*TODO*///			case Z8000_R10: RW(10) = val; break;
/*TODO*///			case Z8000_R11: RW(11) = val; break;
/*TODO*///			case Z8000_R12: RW(12) = val; break;
/*TODO*///			case Z8000_R13: RW(13) = val; break;
/*TODO*///			case Z8000_R14: RW(14) = val; break;
/*TODO*///			case Z8000_R15: RW(15) = val; break;
/*TODO*///			case Z8000_NMI_STATE: Z.nmi_state = val; break;
/*TODO*///			case Z8000_NVI_STATE: Z.irq_state[0] = val; break;
/*TODO*///			case Z8000_VI_STATE: Z.irq_state[1] = val; break;
/*TODO*///			default:
/*TODO*///				if( regnum < REG_SP_CONTENTS )
/*TODO*///				{
/*TODO*///					unsigned offset = NSP + 2 * (REG_SP_CONTENTS - regnum);
/*TODO*///					if( offset < 0xffff )
/*TODO*///						WRMEM_W( offset, val & 0xffff );
/*TODO*///				}
/*TODO*///	    }
/*TODO*///	}
/*TODO*///	
/*TODO*///	void z8000_set_nmi_line(int state)
/*TODO*///	{
/*TODO*///		if (Z.nmi_state == state)
/*TODO*///			return;
/*TODO*///	
/*TODO*///	    Z.nmi_state = state;
/*TODO*///	
/*TODO*///	    if (state != CLEAR_LINE)
/*TODO*///		{
/*TODO*///			if (IRQ_SRV >= Z8000_NMI)	/* no NMIs inside trap */
/*TODO*///				return;
/*TODO*///			IRQ_REQ = Z8000_NMI;
/*TODO*///			IRQ_VEC = NMI;
/*TODO*///		}
/*TODO*///	}
	
	public void z8000_set_irq_line(int irqline, int state)
	{
            //System.out.println(">>>>>>>>>>>>>>>>z8000_set_irq_line!!!! "+irqline);
		Z.irq_state[irqline] = state;
		if (irqline == 0)
		{
			if (state == CLEAR_LINE)
			{
				if ((Z.fcw & F_NVIE)==0)
					_cpuH.IRQ_REQ( _cpuH.IRQ_REQ() & ~Z8000_NVI );
			}
			else
			{
				if ((Z.fcw & F_NVIE) != 0)
					_cpuH.IRQ_REQ( _cpuH.IRQ_REQ() | Z8000_NVI );
	        }
		}
		else
		{
			if (state == CLEAR_LINE)
			{
				if ((Z.fcw & F_VIE)==0)
					_cpuH.IRQ_REQ( _cpuH.IRQ_REQ() & ~Z8000_VI );
			}
			else
			{
				if ((Z.fcw & F_VIE) != 0)
					_cpuH.IRQ_REQ( _cpuH.IRQ_REQ() | Z8000_VI );
			}
		}
	}
	
	public void z8000_set_irq_callback(irqcallbacksPtr callback)
	{
            //System.out.println("z8000_set_irq_callback="+callback);
		Z.irq_callback = callback;
	}
	
	/****************************************************************************
	 * Return a formatted string for a register
	 ****************************************************************************/
	public String z8000_info(Object context, int regnum)
	{
/*TODO*///		static char buffer[32][47+1];
/*TODO*///		static int which = 0;
		z8000_Regs r = (z8000_Regs)context;

/*TODO*///		which = ++which % 32;
/*TODO*///	    buffer[which][0] = '\0';
		if( context == null )
			r = Z;
	//System.out.println("REGNUM: "+regnum);
	    switch( regnum )
		{
			case CPU_INFO_NAME: return "Z8002";
			case CPU_INFO_FAMILY: return "Zilog Z8000";
			case CPU_INFO_VERSION: return "1.1";
			case CPU_INFO_FILE: return "z8000.java";
			case CPU_INFO_CREDITS: return "Copyright (C) 1998,1999 Juergen Buchmueller, all rights reserved.";
	
/*TODO*///			case CPU_INFO_REG_LAYOUT: return (const char*)z8000_reg_layout;
/*TODO*///			case CPU_INFO_WIN_LAYOUT: return (const char*)z8000_win_layout;
/*TODO*///	
/*TODO*///	        case CPU_INFO_FLAGS:
/*TODO*///				sprintf(buffer[which], "%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c",
/*TODO*///					r.fcw & 0x8000 ? 's':'.',
/*TODO*///					r.fcw & 0x4000 ? 'n':'.',
/*TODO*///					r.fcw & 0x2000 ? 'e':'.',
/*TODO*///					r.fcw & 0x1000 ? '2':'.',
/*TODO*///					r.fcw & 0x0800 ? '1':'.',
/*TODO*///					r.fcw & 0x0400 ? '?':'.',
/*TODO*///					r.fcw & 0x0200 ? '?':'.',
/*TODO*///					r.fcw & 0x0100 ? '?':'.',
/*TODO*///					r.fcw & 0x0080 ? 'C':'.',
/*TODO*///					r.fcw & 0x0040 ? 'Z':'.',
/*TODO*///					r.fcw & 0x0020 ? 'S':'.',
/*TODO*///					r.fcw & 0x0010 ? 'V':'.',
/*TODO*///					r.fcw & 0x0008 ? 'D':'.',
/*TODO*///					r.fcw & 0x0004 ? 'H':'.',
/*TODO*///					r.fcw & 0x0002 ? '?':'.',
/*TODO*///					r.fcw & 0x0001 ? '?':'.');
/*TODO*///	            break;
/*TODO*///			case CPU_INFO_REG+Z8000_PC: sprintf(buffer[which], "PC :%04X", r.pc); break;
/*TODO*///			case CPU_INFO_REG+Z8000_NSP: sprintf(buffer[which], "SP :%04X", r.nsp); break;
/*TODO*///			case CPU_INFO_REG+Z8000_FCW: sprintf(buffer[which], "FCW:%04X", r.fcw); break;
/*TODO*///			case CPU_INFO_REG+Z8000_PSAP: sprintf(buffer[which], "NSP:%04X", r.psap); break;
/*TODO*///			case CPU_INFO_REG+Z8000_REFRESH: sprintf(buffer[which], "REFR:%04X", r.refresh); break;
/*TODO*///			case CPU_INFO_REG+Z8000_IRQ_REQ: sprintf(buffer[which], "IRQR:%04X", r.irq_req); break;
/*TODO*///			case CPU_INFO_REG+Z8000_IRQ_SRV: sprintf(buffer[which], "IRQS:%04X", r.irq_srv); break;
/*TODO*///			case CPU_INFO_REG+Z8000_IRQ_VEC: sprintf(buffer[which], "IRQV:%04X", r.irq_vec); break;
/*TODO*///	#ifdef	LSB_FIRST
/*TODO*///	#define REG_XOR 3
/*TODO*///	#else
/*TODO*///	#define REG_XOR 0
/*TODO*///	#endif
/*TODO*///			case CPU_INFO_REG+Z8000_R0: sprintf(buffer[which], "R0 :%04X", r.regs.W[ 0^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R1: sprintf(buffer[which], "R1 :%04X", r.regs.W[ 1^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R2: sprintf(buffer[which], "R2 :%04X", r.regs.W[ 2^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R3: sprintf(buffer[which], "R3 :%04X", r.regs.W[ 3^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R4: sprintf(buffer[which], "R4 :%04X", r.regs.W[ 4^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R5: sprintf(buffer[which], "R5 :%04X", r.regs.W[ 5^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R6: sprintf(buffer[which], "R6 :%04X", r.regs.W[ 6^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R7: sprintf(buffer[which], "R7 :%04X", r.regs.W[ 7^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R8: sprintf(buffer[which], "R8 :%04X", r.regs.W[ 8^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R9: sprintf(buffer[which], "R9 :%04X", r.regs.W[ 9^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R10: sprintf(buffer[which], "R10:%04X", r.regs.W[10^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R11: sprintf(buffer[which], "R11:%04X", r.regs.W[11^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R12: sprintf(buffer[which], "R12:%04X", r.regs.W[12^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R13: sprintf(buffer[which], "R13:%04X", r.regs.W[13^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R14: sprintf(buffer[which], "R14:%04X", r.regs.W[14^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_R15: sprintf(buffer[which], "R15:%04X", r.regs.W[15^REG_XOR]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_NMI_STATE: sprintf(buffer[which], "NMI:%X", r.nmi_state); break;
/*TODO*///			case CPU_INFO_REG+Z8000_NVI_STATE: sprintf(buffer[which], "NVI:%X", r.irq_state[0]); break;
/*TODO*///			case CPU_INFO_REG+Z8000_VI_STATE: sprintf(buffer[which], "VI :%X", r.irq_state[1]); break;

                        }
        /*TODO*///	return buffer[which];
        throw new UnsupportedOperationException("Not supported yet.");
	}
	
	
/*TODO*///	unsigned z8000_dasm(char *buffer, unsigned pc)
/*TODO*///	{
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	    return DasmZ8000(buffer,pc);
/*TODO*///	#else
/*TODO*///		sprintf( buffer, "$%04X", cpu_readop16(pc) );
/*TODO*///		return 2;
/*TODO*///	#endif
/*TODO*///	}
/*TODO*///	
}
