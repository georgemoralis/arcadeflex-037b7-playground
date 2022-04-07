/*** T-11: Portable DEC T-11 emulator ******************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.cpu.t11;

public class t11H
{
        
        public static abstract interface InstructionPtr {
            public abstract void handler();
        }
        
/*TODO*///	
/*TODO*///	enum {
/*TODO*///		T11_R0=1, T11_R1, T11_R2, T11_R3, T11_R4, T11_R5, T11_SP, T11_PC, T11_PSW,
/*TODO*///		T11_IRQ0_STATE, T11_IRQ1_STATE, T11_IRQ2_STATE, T11_IRQ3_STATE,
/*TODO*///		T11_BANK0, T11_BANK1, T11_BANK2, T11_BANK3,
/*TODO*///		T11_BANK4, T11_BANK5, T11_BANK6, T11_BANK7 };

	public static final int T11_INT_NONE    = -1;      /* No interrupt requested */
        public static final int T11_IRQ0        = 0;      /* IRQ0 */
        public static final int T11_IRQ1        = 1;	   /* IRQ1 */
        public static final int T11_IRQ2        = 2;	   /* IRQ2 */
        public static final int T11_IRQ3        = 3;	   /* IRQ3 */

/*TODO*///	#define T11_RESERVED    0x000   /* Reserved vector */
/*TODO*///	#define T11_TIMEOUT     0x004   /* Time-out/system error vector */
/*TODO*///	#define T11_ILLINST     0x008   /* Illegal and reserved instruction vector */
/*TODO*///	#define T11_BPT         0x00C   /* BPT instruction vector */
/*TODO*///	#define T11_IOT         0x010   /* IOT instruction vector */
/*TODO*///	#define T11_PWRFAIL     0x014   /* Power fail vector */
/*TODO*///	#define T11_EMT         0x018   /* EMT instruction vector */
/*TODO*///	#define T11_TRAP        0x01C   /* TRAP instruction vector */
/*TODO*///	
/*TODO*///	/* PUBLIC GLOBALS */
/*TODO*///	extern int  t11_ICount;
/*TODO*///	
/*TODO*///	
/*TODO*///	/* PUBLIC FUNCTIONS */
/*TODO*///	extern void t11_reset(void *param);
/*TODO*///	extern extern int t11_execute(int cycles);    /* NS 970908 */
/*TODO*///	extern unsigned t11_get_context(void *dst);
/*TODO*///	extern void t11_set_context(void *src);
/*TODO*///	extern unsigned t11_get_pc(void);
/*TODO*///	extern void t11_set_pc(unsigned val);
/*TODO*///	extern unsigned t11_get_sp(void);
/*TODO*///	extern void t11_set_sp(unsigned val);
/*TODO*///	extern unsigned t11_get_reg(int regnum);
/*TODO*///	extern void t11_set_reg(int regnum, unsigned val);
/*TODO*///	extern void t11_set_nmi_line(int state);
/*TODO*///	extern void t11_set_irq_line(int irqline, int state);
/*TODO*///	extern void t11_set_irq_callback(int (*callback)(int irqline));
/*TODO*///	extern const char *t11_info(void *context, int regnum);
/*TODO*///	extern unsigned t11_dasm(char *buffer, unsigned pc);
/*TODO*///	
/*TODO*///	extern void t11_SetBank(int banknum, UBytePtr address);
/*TODO*///	
/*TODO*///	/****************************************************************************/
/*TODO*///	/* Read a byte from given memory location                                   */
/*TODO*///	/****************************************************************************/
	public static int T11_RDMEM(int A) { 
            throw new UnsupportedOperationException("Not Supported!");            
/*TODO*///            return (cpu_readmem16lew(A)); 
        }
/*TODO*///	#define T11_RDMEM_WORD(A) ((unsigned)cpu_readmem16lew_word(A))
/*TODO*///	
/*TODO*///	/****************************************************************************/
/*TODO*///	/* Write a byte to given memory location                                    */
/*TODO*///	/****************************************************************************/
/*TODO*///	#define T11_WRMEM(A,V) (cpu_writemem16lew(A,V))
/*TODO*///	#define T11_WRMEM_WORD(A,V) (cpu_writemem16lew_word(A,V))
/*TODO*///	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	extern unsigned DasmT11(char *buffer, unsigned pc);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///	#endif /* _T11_H */
}
