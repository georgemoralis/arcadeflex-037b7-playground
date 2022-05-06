/***************************************************************************
 *
 * Retrocade - Video game emulator
 * Copyright 1998, The Retrocade group
 *
 * Permission to distribute the Retrocade executable *WITHOUT GAME ROMS* is
 * granted to all. It may not be sold without the express written permission
 * of the Retrocade development team or appointed representative.
 *
 * Source code is *NOT* distributable without the express written
 * permission of the Retrocade group.
 *
 * Cinematronics CPU header file
 *
 ***************************************************************************/


/*============================================================================================*

	HERE BEGINS THE MAME-SPECIFIC ADDITIONS TO THE CCPU INTERFACE.

 *============================================================================================*/

/* added these includes */
/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.cpu.ccpu;

public class ccpuH
{
/*TODO*///	
/*TODO*///	enum {
/*TODO*///		CCPU_PC=1, CCPU_ACC, CCPU_CMP, CCPU_PA0, CCPU_CFLAG,
/*TODO*///		CCPU_A, CCPU_B, CCPU_I, CCPU_J, CCPU_P, CCPU_CSTATE };
/*TODO*///	
/*TODO*///	#ifndef FALSE
/*TODO*///	#define FALSE	0
/*TODO*///	#endif
/*TODO*///	#ifndef TRUE
/*TODO*///	#define TRUE	(!FALSE)
/*TODO*///	#endif
/*TODO*///	
/*TODO*///	/* an ICount variable (mostly irrelevant) */
/*TODO*///	extern int ccpu_icount;

        public static int CCPU_DATA_OFFSET    = 0x0000;
	public static int CCPU_PGM_OFFSET     = 0x8000;

/*TODO*///	/* MAME interface functions */
/*TODO*///	void ccpu_reset(void *param);
/*TODO*///	int ccpu_execute(int cycles);
/*TODO*///	unsigned ccpu_get_context(void *dst);
/*TODO*///	void ccpu_set_context(void *src);
/*TODO*///	unsigned ccpu_get_pc(void);
/*TODO*///	void ccpu_set_pc(unsigned val);
/*TODO*///	unsigned ccpu_get_sp(void);
/*TODO*///	void ccpu_set_sp(unsigned val);
/*TODO*///	unsigned ccpu_get_reg(int regnum);
/*TODO*///	void ccpu_set_reg(int regnum, unsigned val);
/*TODO*///	void ccpu_set_nmi_line(int state);
/*TODO*///	void ccpu_set_irq_line(int irqline, int state);
/*TODO*///	void ccpu_set_irq_callback(int (*callback)(int irqline));
/*TODO*///	const char *ccpu_info(void *context, int regnum);
/*TODO*///	unsigned ccpu_dasm(char *buffer, unsigned pc);
/*TODO*///	
/*TODO*///	/* I/O routine */
/*TODO*///	void ccpu_SetInputs(int inputs, int switches);
	
	/* constants for configuring the system */
	public static final int CCPU_PORT_IOSWITCHES   	= 0;
	public static final int CCPU_PORT_IOINPUTS     	= 1;
	public static final int CCPU_PORT_IOOUTPUTS    	= 2;
	public static final int CCPU_PORT_IN_JOYSTICKX 	= 3;
	public static final int CCPU_PORT_IN_JOYSTICKY 	= 4;
	public static final int CCPU_PORT_MAX          	= 5;
	
	public static final int CCPU_MEMSIZE_4K        	= 0;
	public static final int CCPU_MEMSIZE_8K        	= 1;
	public static final int CCPU_MEMSIZE_16K       	= 2;
	public static final int CCPU_MEMSIZE_32K       	= 3;
	
	public static final int CCPU_MONITOR_BILEV  	= 0;
	public static final int CCPU_MONITOR_16LEV  	= 1;
	public static final int CCPU_MONITOR_64LEV  	= 2;
	public static final int CCPU_MONITOR_WOWCOL 	= 3;
	
/*TODO*///	/* nicer config function */
/*TODO*///	void ccpu_Config (int jmi, int msize, int monitor);
/*TODO*///	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	extern unsigned DasmCCPU(char *buffer, unsigned pc);
/*TODO*///	#endif
	
	/*============================================================================================*
	
		BELOW LIES THE CORE OF THE CCPU. THE CODE WAS KINDLY GIVEN TO MAME BY ZONN MOORE,
		JEFF MITCHELL, AND NEIL BRADLEY. I HAVE PRETTY HEAVILY CLEANED IT UP.
	
	 *============================================================================================*/
	
	
	/* Define new types for the c-cpu emulator */
        //typedef short unsigned int CINEWORD;      /* 12bits on the C-CPU */
        public static class CINEWORD {
            int _v = 0;
            public CINEWORD(int _v) {
                set(_v & 0x0fff);
            }
            public int get() {
                return _v & 0x0fff;
            }
            public void set(int _v) {
                this._v = _v & 0x0fff;
            }
        }
        //typedef unsigned char      CINEBYTE;      /* 8 (or less) bits on the C-CPU */
        public static class CINEBYTE {
            int _v = 0;
            public CINEBYTE(int _v) {
                set(_v);
            }
            public int get() {
                return _v & 0xff;
            }
            public void set(int _v) {
                this._v = _v & 0xff;
            }
        }
        //typedef short signed int   CINESWORD;     /* 12bits on the C-CPU */
        public static class CINESWORD {
            int _v = 0;
            public int get() {
                return _v & 0xffff;
            }
            public void set(int _v) {
                this._v = _v & 0xffff;
            }
        }
        //typedef signed char        CINESBYTE;     /* 8 (or less) bits on the C-CPU */
        public static class CINESBYTE {
            int _v = 0;
            public int get() {
                return _v & 0xff;
            }
            public void set(int _v) {
                this._v = _v & 0xff;
            }
        }
        //typedef unsigned long int  CINELONG;
        public static class CINELONG {
            int _v = 0;
            public int get() {
                return _v & 0xffffffff;
            }
            public void set(int _v) {
                this._v = _v & 0xffffffff;
            }
        }
	
	//public static enum CINESTATE
	//{
		public static final int state_A   = 0;
		public static final int state_AA  = 1;
		public static final int state_B   = 2;
		public static final int state_BB  = 3;
	//};                              /* current state */
	
	/* NOTE: These MUST be in this order! */

	public static class CONTEXTCCPU
	{
		CINEWORD	accVal;				/* CCPU Accumulator value */
		CINEWORD	cmpVal;				/* Comparison value */
		CINEBYTE	pa0;
		CINEBYTE	cFlag;
		CINEWORD	eRegPC;
		CINEWORD	eRegA;
		CINEWORD	eRegB;
		CINEWORD	eRegI;
		CINEWORD	eRegJ;
		CINEBYTE	eRegP;
		int/*CINESTATE*/	eCState;
	};
	
/*TODO*///	typedef struct scCpuStruct CONTEXTCCPU;
	

}
