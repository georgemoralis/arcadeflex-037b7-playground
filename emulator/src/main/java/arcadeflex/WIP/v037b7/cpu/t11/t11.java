/*** t11: Portable DEC T-11 emulator ******************************************

	Copyright (C) Aaron Giles 1998

	System dependencies:	long must be at least 32 bits
	                        word must be 16 bit unsigned int
							byte must be 8 bit unsigned int
							long must be more than 16 bits
							arrays up to 65536 bytes must be supported
							machine must be twos complement

*****************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.cpu.t11;

import static arcadeflex.WIP.v037b7.cpu.t11.t11H.*;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.memory.*;


public class t11 extends cpu_interface 
{
    /*TODO*///#define CPU0(cpu,name,nirq=4,dirq=0,oc=1.00,i0=T11_INT_NONE,i1=-1,i2=-1,mem=16lew,shift=0,bits=16,endian=LE,align=2,maxinst=6,MEM=16LEW) 
    //CPU0(V20,	   v20, 	 1,  0,1.00,NEC_INT_NONE,	   -1000,		   NEC_NMI_INT,    20,	  0,20,LE,1, 5,20	),
    public t11() {
        cpu_num = CPU_T11;
        num_irqs = 4;
        default_vector = 0;
        overclock = 1.0;
        no_int = T11_INT_NONE;
        irq_int = -1;
        nmi_int = -1;
        address_shift = 0;
        address_bits = 16;
        endianess = CPU_IS_LE;
        align_unit = 2;
        max_inst_len = 6;
        abits1 = ABITS1_16LEW;
        abits2 = ABITS2_16LEW;
        abitsmin = ABITS_MIN_16LEW;
        icount = t11_ICount;
    }

    @Override
    public void reset(Object param) {
        t11_reset(param);
    }

    @Override
    public void exit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int execute(int cycles) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object init_context() {
        Object reg = new t11_Regs();
        return reg;
    }

    @Override
    public Object get_context() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void set_context(Object reg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int[] get_cycle_table(int which) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void set_cycle_table(int which, int[] new_table) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int get_pc() {
        return t11_get_pc();
    }

    @Override
    public void set_pc(int val) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int get_sp() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void set_sp(int val) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int get_reg(int regnum) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void set_reg(int regnum, int val) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void set_nmi_line(int linestate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void set_irq_line(int irqline, int linestate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void set_irq_callback(irqcallbacksPtr callback) {
        t11_set_irq_callback(callback);
    }

    @Override
    public void internal_interrupt(int type) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cpu_state_save(Object file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cpu_state_load(Object file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String cpu_info(Object context, int regnum) {
        return t11_info(context, regnum);
    }

    @Override
    public int memory_read(int offset) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void memory_write(int offset, int data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int internal_read(int offset) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void internal_write(int offset, int data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void set_op_base(int pc) {
        System.out.println("cpu_setOPbase16bew is correct????????");
        //cpu_setOPbase16bew.handler(pc);
        cpu_setOPbase16.handler(pc);
    }
/*TODO*///	
/*TODO*///	
/*TODO*///	static UINT8 t11_reg_layout[] = {
/*TODO*///		T11_PC, T11_SP, T11_PSW, T11_IRQ0_STATE, T11_IRQ1_STATE, T11_IRQ2_STATE, T11_IRQ3_STATE, -1,
/*TODO*///		T11_R0,T11_R1,T11_R2,T11_R3,T11_R4,T11_R5, -1,
/*TODO*///		T11_BANK0,T11_BANK1,T11_BANK2,T11_BANK3, T11_BANK4,T11_BANK5,T11_BANK6,T11_BANK7, 0
/*TODO*///	};
/*TODO*///	
/*TODO*///	static UINT8 t11_win_layout[] = {
/*TODO*///		 0, 0,80, 4,	/* register window (top rows) */
/*TODO*///		 0, 5,31,17,	/* disassembler window (left colums) */
/*TODO*///		32, 5,48, 8,	/* memory #1 window (right, upper middle) */
/*TODO*///		32,14,48, 8,	/* memory #2 window (right, lower middle) */
/*TODO*///		 0,23,80, 1,	/* command line window (bottom rows) */
/*TODO*///	};
    
        public static class PAIR {
            //L = low 8 bits
            //H = high 8 bits
            //D = whole 16 bits

            public int H, L, D;

            public void SetH(int val) {
                H = val;
                D = (H << 8) | L;
            }

            public void SetL(int val) {
                L = val;
                D = (H << 8) | L;
            }

            public void SetD(int val) {
                D = val;
                H = D >> 8 & 0xFF;
                L = D & 0xFF;
            }

            public void AddH(int val) {
                H = (H + val) & 0xFF;
                D = (H << 8) | L;
            }

            public void AddL(int val) {
                L = (L + val) & 0xFF;
                D = (H << 8) | L;
            }

            public void AddD(int val) {
                D = (D + val) & 0xFFFF;
                H = D >> 8 & 0xFF;
                L = D & 0xFF;
            }
        };
	
	/* T-11 Registers */
	public static class t11_Regs
	{
            PAIR            ppc = new PAIR();	/* previous program counter */
	    PAIR[]          reg = new PAIR[8];
	    PAIR            psw = new PAIR();
            int /*UINT16*/  op;
	    int /*UINT8*/   wait_state;
	    UBytePtr[]      bank        = new UBytePtr[8];
	    int[] /*INT8*/  irq_state   = new int[4];
	    int             interrupt_cycles;
            public irqcallbacksPtr irq_callback;
	};

	static t11_Regs t11 = new t11_Regs();

	/* public globals */
	static int[] t11_ICount = {50000};

/*TODO*///	/* register definitions and shortcuts */
/*TODO*///	#define REGD(x) t11.reg[x].d
/*TODO*///	#define REGW(x) t11.reg[x].w.l
/*TODO*///	#define REGB(x) t11.reg[x].b.l
	public void SP(int _val) { 
            //REGW(6); 
            t11.reg[6].SetD(_val);
        }
        public int SP() { 
            //REGW(6);
            return t11.reg[6].D; 
        }

        public void PC(int _val) { 
            //REGW(7); 
            t11.reg[7].SetD(_val);
        }
        public int PC() { 
            //REGW(7);
            return t11.reg[7].D; 
        }
/*TODO*///	#define SPD REGD(6)
        public void PCD(int _val) { 
            //REGD(7); 
            t11.reg[7].SetD(_val);
        }
        public int PCD() { 
            //REGD(7);
            return t11.reg[7].D; 
        }
        
        public void PSW(int _val) { 
            //t11.psw.b.l; 
            t11.reg[7].SetL(_val & 0xff);
        }
        public int PSW() { 
            //t11.psw.b.l;
            return t11.reg[7].L; 
        }
/*TODO*///	
/*TODO*///	/* shortcuts for reading opcodes */
/*TODO*///	INLINE int ROPCODE(void)
/*TODO*///	{
/*TODO*///		int pc = PCD;
/*TODO*///		PC += 2;
/*TODO*///		return READ_WORD(&t11.bank[pc >> 13][pc & 0x1fff]);
/*TODO*///	}
/*TODO*///	
/*TODO*///	/* shortcuts for reading/writing memory bytes */
/*TODO*///	#define RBYTE(addr)      T11_RDMEM(addr)
/*TODO*///	#define WBYTE(addr,data) T11_WRMEM((addr), (data))
/*TODO*///	
/*TODO*///	/* shortcuts for reading/writing memory words */
/*TODO*///	INLINE int RWORD(int addr)
/*TODO*///	{
/*TODO*///		return T11_RDMEM_WORD(addr & 0xfffe);
/*TODO*///	}
/*TODO*///	
/*TODO*///	INLINE void WWORD(int addr, int data)
/*TODO*///	{
/*TODO*///		T11_WRMEM_WORD(addr & 0xfffe, data);
/*TODO*///	}
/*TODO*///	
/*TODO*///	/* pushes/pops a value from the stack */
/*TODO*///	INLINE void PUSH(int val)
/*TODO*///	{
/*TODO*///		SP -= 2;
/*TODO*///		WWORD(SPD, val);
/*TODO*///	}
/*TODO*///	
/*TODO*///	INLINE int POP(void)
/*TODO*///	{
/*TODO*///		int result = RWORD(SPD);
/*TODO*///		SP += 2;
/*TODO*///		return result;
/*TODO*///	}
/*TODO*///	
/*TODO*///	/* flag definitions */
/*TODO*///	#define CFLAG 1
/*TODO*///	#define VFLAG 2
/*TODO*///	#define ZFLAG 4
/*TODO*///	#define NFLAG 8
/*TODO*///	
/*TODO*///	/* extracts flags */
/*TODO*///	#define GET_C (PSW & CFLAG)
/*TODO*///	#define GET_V (PSW & VFLAG)
/*TODO*///	#define GET_Z (PSW & ZFLAG)
/*TODO*///	#define GET_N (PSW & NFLAG)
/*TODO*///	
/*TODO*///	/* clears flags */
/*TODO*///	#define CLR_C (PSW &= ~CFLAG)
/*TODO*///	#define CLR_V (PSW &= ~VFLAG)
/*TODO*///	#define CLR_Z (PSW &= ~ZFLAG)
/*TODO*///	#define CLR_N (PSW &= ~NFLAG)
/*TODO*///	
/*TODO*///	/* sets flags */
/*TODO*///	#define SET_C (PSW |= CFLAG)
/*TODO*///	#define SET_V (PSW |= VFLAG)
/*TODO*///	#define SET_Z (PSW |= ZFLAG)
/*TODO*///	#define SET_N (PSW |= NFLAG)
/*TODO*///	
/*TODO*///	/****************************************************************************
/*TODO*///	 * Checks active interrupts for a valid one
/*TODO*///	 ****************************************************************************/
/*TODO*///	static void t11_check_irqs(void)
/*TODO*///	{
/*TODO*///		int priority = PSW & 0xe0;
/*TODO*///		int irq;
/*TODO*///	
/*TODO*///		/* loop over IRQs from highest to lowest */
/*TODO*///		for (irq = 0; irq < 4; irq++)
/*TODO*///		{
/*TODO*///		    if (t11.irq_state[irq] != CLEAR_LINE)
/*TODO*///			{
/*TODO*///				/* get the priority of this interrupt */
/*TODO*///				int new_pc = RWORD(0x38 + (irq * 0x10));
/*TODO*///				int new_psw = RWORD(0x3a + (irq * 0x10));
/*TODO*///	
/*TODO*///				/* if it's greater than the current priority, take it */
/*TODO*///				if ((new_psw & 0xe0) > priority)
/*TODO*///				{
/*TODO*///					if (t11.irq_callback)
/*TODO*///						(*t11.irq_callback)(irq);
/*TODO*///	
/*TODO*///					/* push the old state, set the new one */
/*TODO*///					PUSH(PSW);
/*TODO*///					PUSH(PC);
/*TODO*///					PCD = new_pc;
/*TODO*///					PSW = new_psw;
/*TODO*///					priority = new_psw & 0xe0;
/*TODO*///	
/*TODO*///					/* count 50 cycles (who knows what it really is) and clear the WAIT flag */
/*TODO*///					t11.interrupt_cycles += 50;
/*TODO*///					t11.wait_state = 0;
/*TODO*///				}
/*TODO*///		    }
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	/* includes the static function prototypes and the master opcode table */
/*TODO*///	
/*TODO*///	/* includes the actual opcode implementations */
/*TODO*///	
/*TODO*///	/****************************************************************************
/*TODO*///	 * Get all registers in given buffer
/*TODO*///	 ****************************************************************************/
/*TODO*///	unsigned t11_get_context(void *dst)
/*TODO*///	{
/*TODO*///		if (dst != 0)
/*TODO*///			*(t11_Regs*)dst = t11;
/*TODO*///		return sizeof(t11_Regs);
/*TODO*///	}
/*TODO*///	
/*TODO*///	/****************************************************************************
/*TODO*///	 * Set all registers to given values
/*TODO*///	 ****************************************************************************/
/*TODO*///	void t11_set_context(void *src)
/*TODO*///	{
/*TODO*///		if (src != 0)
/*TODO*///			t11 = *(t11_Regs*)src;
/*TODO*///		t11_check_irqs();
/*TODO*///	}
	
	/****************************************************************************
	 * Return program counter
	 ****************************************************************************/
	public int t11_get_pc()
	{
		return PCD();
	}
	
/*TODO*///	/****************************************************************************
/*TODO*///	 * Set program counter
/*TODO*///	 ****************************************************************************/
/*TODO*///	void t11_set_pc(unsigned val)
/*TODO*///	{
/*TODO*///		PC = val;
/*TODO*///	}
/*TODO*///	
/*TODO*///	/****************************************************************************
/*TODO*///	 * Return stack pointer
/*TODO*///	 ****************************************************************************/
/*TODO*///	unsigned t11_get_sp(void)
/*TODO*///	{
/*TODO*///		return SPD;
/*TODO*///	}
/*TODO*///	
/*TODO*///	/****************************************************************************
/*TODO*///	 * Set stack pointer
/*TODO*///	 ****************************************************************************/
/*TODO*///	void t11_set_sp(unsigned val)
/*TODO*///	{
/*TODO*///		SP = val;
/*TODO*///	}
/*TODO*///	
/*TODO*///	/****************************************************************************
/*TODO*///	 * Return a specific register
/*TODO*///	 ****************************************************************************/
/*TODO*///	unsigned t11_get_reg(int regnum)
/*TODO*///	{
/*TODO*///		switch( regnum )
/*TODO*///		{
/*TODO*///			case T11_PC: return PCD;
/*TODO*///			case T11_SP: return SPD;
/*TODO*///			case T11_PSW: return PSW;
/*TODO*///			case T11_R0: return REGD(0);
/*TODO*///			case T11_R1: return REGD(1);
/*TODO*///			case T11_R2: return REGD(2);
/*TODO*///			case T11_R3: return REGD(3);
/*TODO*///			case T11_R4: return REGD(4);
/*TODO*///			case T11_R5: return REGD(5);
/*TODO*///			case T11_IRQ0_STATE: return t11.irq_state[T11_IRQ0];
/*TODO*///			case T11_IRQ1_STATE: return t11.irq_state[T11_IRQ1];
/*TODO*///			case T11_IRQ2_STATE: return t11.irq_state[T11_IRQ2];
/*TODO*///			case T11_IRQ3_STATE: return t11.irq_state[T11_IRQ3];
/*TODO*///			case T11_BANK0: return (unsigned)(t11.bank[0] - OP_RAM);
/*TODO*///			case T11_BANK1: return (unsigned)(t11.bank[1] - OP_RAM);
/*TODO*///			case T11_BANK2: return (unsigned)(t11.bank[2] - OP_RAM);
/*TODO*///			case T11_BANK3: return (unsigned)(t11.bank[3] - OP_RAM);
/*TODO*///			case T11_BANK4: return (unsigned)(t11.bank[4] - OP_RAM);
/*TODO*///			case T11_BANK5: return (unsigned)(t11.bank[5] - OP_RAM);
/*TODO*///			case T11_BANK6: return (unsigned)(t11.bank[6] - OP_RAM);
/*TODO*///			case T11_BANK7: return (unsigned)(t11.bank[7] - OP_RAM);
/*TODO*///			case REG_PREVIOUSPC: return t11.ppc.w.l;
/*TODO*///			default:
/*TODO*///				if( regnum <= REG_SP_CONTENTS )
/*TODO*///				{
/*TODO*///					unsigned offset = SPD + 2 * (REG_SP_CONTENTS - regnum);
/*TODO*///					if( offset < 0xffff )
/*TODO*///						return RWORD( offset );
/*TODO*///				}
/*TODO*///		}
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	/****************************************************************************
/*TODO*///	 * Set a specific register
/*TODO*///	 ****************************************************************************/
/*TODO*///	void t11_set_reg(int regnum, unsigned val)
/*TODO*///	{
/*TODO*///		switch( regnum )
/*TODO*///		{
/*TODO*///			case T11_PC: PC = val; /* change_pc not needed */ break;
/*TODO*///			case T11_SP: SP = val; break;
/*TODO*///			case T11_PSW: PSW = val; break;
/*TODO*///			case T11_R0: REGW(0) = val; break;
/*TODO*///			case T11_R1: REGW(1) = val; break;
/*TODO*///			case T11_R2: REGW(2) = val; break;
/*TODO*///			case T11_R3: REGW(3) = val; break;
/*TODO*///			case T11_R4: REGW(4) = val; break;
/*TODO*///			case T11_R5: REGW(5) = val; break;
/*TODO*///			case T11_IRQ0_STATE: t11_set_irq_line(T11_IRQ0,val); break;
/*TODO*///			case T11_IRQ1_STATE: t11_set_irq_line(T11_IRQ1,val); break;
/*TODO*///			case T11_IRQ2_STATE: t11_set_irq_line(T11_IRQ2,val); break;
/*TODO*///			case T11_IRQ3_STATE: t11_set_irq_line(T11_IRQ3,val); break;
/*TODO*///			case T11_BANK0: t11.bank[0] = &OP_RAM[val]; break;
/*TODO*///			case T11_BANK1: t11.bank[1] = &OP_RAM[val]; break;
/*TODO*///			case T11_BANK2: t11.bank[2] = &OP_RAM[val]; break;
/*TODO*///			case T11_BANK3: t11.bank[3] = &OP_RAM[val]; break;
/*TODO*///			case T11_BANK4: t11.bank[4] = &OP_RAM[val]; break;
/*TODO*///			case T11_BANK5: t11.bank[5] = &OP_RAM[val]; break;
/*TODO*///			case T11_BANK6: t11.bank[6] = &OP_RAM[val]; break;
/*TODO*///			case T11_BANK7: t11.bank[7] = &OP_RAM[val]; break;
/*TODO*///			default:
/*TODO*///				if( regnum < REG_SP_CONTENTS )
/*TODO*///				{
/*TODO*///					unsigned offset = SPD + 2 * (REG_SP_CONTENTS - regnum);
/*TODO*///					if( offset < 0xffff )
/*TODO*///						WWORD( offset, val & 0xffff );
/*TODO*///				}
/*TODO*///	    }
/*TODO*///	}
	
	/****************************************************************************
	 * Sets the banking
	 ****************************************************************************/
	public static void t11_SetBank(int offset, UBytePtr base)
	{
		t11.bank[offset >> 13] = base;
	}
	
	
	public void t11_reset(Object param)
	{
		int i;
	
		//memset(&t11, 0, sizeof(t11));
                t11 = new t11_Regs();
                
                for (int _i=0 ; _i<8 ; _i++)
                    t11.reg[_i] = new PAIR();
                    
		SP(0x0400);
		PC(0x8000);
		PSW(0xe0);
	
		for (i = 0; i < 8; i++)
			t11.bank[i] = new UBytePtr(OP_RAM, i * 0x2000);
		for (i = 0; i < 4; i++)
			t11.irq_state[i] = CLEAR_LINE;
	}
	
/*TODO*///	void t11_exit(void)
/*TODO*///	{
/*TODO*///		/* nothing to do */
/*TODO*///	}
/*TODO*///	
/*TODO*///	void t11_set_nmi_line(int state)
/*TODO*///	{
/*TODO*///		/* T-11 has no dedicated NMI line */
/*TODO*///	}
/*TODO*///	
/*TODO*///	void t11_set_irq_line(int irqline, int state)
/*TODO*///	{
/*TODO*///	    t11.irq_state[irqline] = state;
/*TODO*///	    if (state != CLEAR_LINE)
/*TODO*///	    	t11_check_irqs();
/*TODO*///	}
	
	public void t11_set_irq_callback(irqcallbacksPtr callback)
	{
		t11.irq_callback = callback;
	}
	
	
/*TODO*///	/* execute instructions on this CPU until icount expires */
/*TODO*///	int t11_execute(int cycles)
/*TODO*///	{
/*TODO*///		t11_ICount = cycles;
/*TODO*///		t11_ICount -= t11.interrupt_cycles;
/*TODO*///		t11.interrupt_cycles = 0;
/*TODO*///	
/*TODO*///		if (t11.wait_state)
/*TODO*///		{
/*TODO*///			t11_ICount = 0;
/*TODO*///			goto getout;
/*TODO*///		}
/*TODO*///	
/*TODO*///	change_pc(0xffff);
/*TODO*///		do
/*TODO*///		{
/*TODO*///			t11.ppc = t11.reg[7];	/* copy PC to previous PC */
/*TODO*///	
/*TODO*///			CALL_MAME_DEBUG;
/*TODO*///	
/*TODO*///			t11.op = ROPCODE();
/*TODO*///			(*opcode_table[t11.op >> 3])();
/*TODO*///	
/*TODO*///			t11_ICount -= 22;
/*TODO*///	
/*TODO*///		} while (t11_ICount > 0);
/*TODO*///	
/*TODO*///	getout:
/*TODO*///	
/*TODO*///		t11_ICount -= t11.interrupt_cycles;
/*TODO*///		t11.interrupt_cycles = 0;
/*TODO*///	
/*TODO*///		return cycles - t11_ICount;
/*TODO*///	}
	
	/****************************************************************************
	 * Return a formatted string for a register
	 ****************************************************************************/
	public String t11_info(Object context, int regnum)
	{
/*TODO*///		static char buffer[16][47+1];
/*TODO*///		static int which = 0;
/*TODO*///		t11_Regs *r = context;
/*TODO*///	
/*TODO*///		which = ++which % 16;
/*TODO*///	    buffer[which][0] = '\0';
/*TODO*///	
/*TODO*///		if( !context )
/*TODO*///			r = &t11;
/*TODO*///	
	    switch( regnum )
            {
/*TODO*///			case CPU_INFO_REG+T11_PC: sprintf(buffer[which], "PC:%04X", r.reg[7].w.l); break;
/*TODO*///			case CPU_INFO_REG+T11_SP: sprintf(buffer[which], "SP:%04X", r.reg[6].w.l); break;
/*TODO*///			case CPU_INFO_REG+T11_PSW: sprintf(buffer[which], "PSW:%02X", r.psw.b.l); break;
/*TODO*///			case CPU_INFO_REG+T11_R0: sprintf(buffer[which], "R0:%04X", r.reg[0].w.l); break;
/*TODO*///			case CPU_INFO_REG+T11_R1: sprintf(buffer[which], "R1:%04X", r.reg[1].w.l); break;
/*TODO*///			case CPU_INFO_REG+T11_R2: sprintf(buffer[which], "R2:%04X", r.reg[2].w.l); break;
/*TODO*///			case CPU_INFO_REG+T11_R3: sprintf(buffer[which], "R3:%04X", r.reg[3].w.l); break;
/*TODO*///			case CPU_INFO_REG+T11_R4: sprintf(buffer[which], "R4:%04X", r.reg[4].w.l); break;
/*TODO*///			case CPU_INFO_REG+T11_R5: sprintf(buffer[which], "R5:%04X", r.reg[5].w.l); break;
/*TODO*///			case CPU_INFO_REG+T11_IRQ0_STATE: sprintf(buffer[which], "IRQ0:%X", r.irq_state[T11_IRQ0]); break;
/*TODO*///			case CPU_INFO_REG+T11_IRQ1_STATE: sprintf(buffer[which], "IRQ1:%X", r.irq_state[T11_IRQ1]); break;
/*TODO*///			case CPU_INFO_REG+T11_IRQ2_STATE: sprintf(buffer[which], "IRQ2:%X", r.irq_state[T11_IRQ2]); break;
/*TODO*///			case CPU_INFO_REG+T11_IRQ3_STATE: sprintf(buffer[which], "IRQ3:%X", r.irq_state[T11_IRQ3]); break;
/*TODO*///			case CPU_INFO_REG+T11_BANK0: sprintf(buffer[which], "B0:%06X", (unsigned)(r.bank[0] - OP_RAM)); break;
/*TODO*///			case CPU_INFO_REG+T11_BANK1: sprintf(buffer[which], "B1:%06X", (unsigned)(r.bank[1] - OP_RAM)); break;
/*TODO*///			case CPU_INFO_REG+T11_BANK2: sprintf(buffer[which], "B2:%06X", (unsigned)(r.bank[2] - OP_RAM)); break;
/*TODO*///			case CPU_INFO_REG+T11_BANK3: sprintf(buffer[which], "B3:%06X", (unsigned)(r.bank[3] - OP_RAM)); break;
/*TODO*///			case CPU_INFO_REG+T11_BANK4: sprintf(buffer[which], "B4:%06X", (unsigned)(r.bank[4] - OP_RAM)); break;
/*TODO*///			case CPU_INFO_REG+T11_BANK5: sprintf(buffer[which], "B5:%06X", (unsigned)(r.bank[5] - OP_RAM)); break;
/*TODO*///			case CPU_INFO_REG+T11_BANK6: sprintf(buffer[which], "B6:%06X", (unsigned)(r.bank[6] - OP_RAM)); break;
/*TODO*///			case CPU_INFO_REG+T11_BANK7: sprintf(buffer[which], "B7:%06X", (unsigned)(r.bank[7] - OP_RAM)); break;
/*TODO*///			case CPU_INFO_FLAGS:
/*TODO*///				sprintf(buffer[which], "%c%c%c%c%c%c%c%c",
/*TODO*///					r.psw.b.l & 0x80 ? '?':'.',
/*TODO*///					r.psw.b.l & 0x40 ? 'I':'.',
/*TODO*///					r.psw.b.l & 0x20 ? 'I':'.',
/*TODO*///					r.psw.b.l & 0x10 ? 'T':'.',
/*TODO*///					r.psw.b.l & 0x08 ? 'N':'.',
/*TODO*///					r.psw.b.l & 0x04 ? 'Z':'.',
/*TODO*///					r.psw.b.l & 0x02 ? 'V':'.',
/*TODO*///					r.psw.b.l & 0x01 ? 'C':'.');
/*TODO*///				break;
			case CPU_INFO_NAME: return "T11";
			case CPU_INFO_FAMILY: return "DEC T-11";
			case CPU_INFO_VERSION: return "1.0";
			case CPU_INFO_FILE: return "t11.java";
			case CPU_INFO_CREDITS: return "Copyright (C) Aaron Giles 1998";
/*TODO*///			case CPU_INFO_REG_LAYOUT: return (const char*)t11_reg_layout;
/*TODO*///			case CPU_INFO_WIN_LAYOUT: return (const char*)t11_win_layout;
	    }

            throw new UnsupportedOperationException("unsupported t11 cpu_info");
            /*TODO*///    return buffer[which];
	}
	
/*TODO*///	unsigned t11_dasm(char *buffer, unsigned pc)
/*TODO*///	{
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	    return DasmT11(buffer,pc);
/*TODO*///	#else
/*TODO*///		sprintf( buffer, "$%04X", cpu_readmem16lew_word(pc) );
/*TODO*///		return 2;
/*TODO*///	#endif
/*TODO*///	}
	
}
