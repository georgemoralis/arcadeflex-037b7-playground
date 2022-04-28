package gr.codebb.arcadeflex.WIP.v037b7.cpu.m6502;

import static gr.codebb.arcadeflex.WIP.v037b7.cpu.m6502.m6502.*;

public class opsc02H {
    
    /*****************************************************************************
     *
     *	 m6502ops.h
     *	 Addressing mode and opcode macros for 6502,65c02,65sc02,6510,n2a03 CPUs
     *
     *	 Copyright (c) 1998,1999,2000 Juergen Buchmueller, all rights reserved.
     *	 65sc02 core Copyright (c) 2000 Peter Trauner, all rights reserved.
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
     *	   terms of its usage and license at any time, including retroactively
     *	 - This entire notice must remain in the source code.
     *
     *****************************************************************************/
    
    /*TODO*////***************************************************************
    /*TODO*/// *	EA = indirect (only used by JMP)
    /*TODO*/// * correct overflow handling
    /*TODO*/// ***************************************************************/
    /*TODO*///#undef EA_IND
    /*TODO*///#define EA_IND													
    /*TODO*///	EA_ABS; 													
    /*TODO*///	tmp = RDMEM(EAD);											
    /*TODO*///	if (EAL==0xff) m6502_ICount++;								
    /*TODO*///	EAD++;														
    /*TODO*///	EAH = RDMEM(EAD);											
    /*TODO*///	EAL = tmp
    
    /***************************************************************
     *	EA = zero page indirect (65c02 pre indexed w/o X)
     ***************************************************************/
    public static void EA_ZPI() {
    	m6502.zp.SetL( RDOPARG() );
    	m6502.ea.SetL( RDMEM(m6502.zp.D) );
    	m6502.zp.AddL(1);
    	m6502.ea.SetH( RDMEM(m6502.zp.D) );
    }
    
    /***************************************************************
     *	EA = indirect plus x (only used by 65c02 JMP)
     ***************************************************************/
    public static void EA_IAX() {
    	 EA_ABS();													
    	 if (m6502.ea.L + m6502.u8_x > 0xff) /* assumption; probably wrong ? */ 	
    		 m6502_ICount[0]--;										
    	 m6502.ea.AddL( m6502.u8_x);
    	 int tmp = RDMEM(m6502.ea.D);											
    	 if (m6502.ea.L==0xff) m6502_ICount[0]++; 							
    	 m6502.ea.AddD(1); 													
    	 m6502.ea.SetH( RDMEM(m6502.ea.D) );
    	 m6502.ea.SetL( tmp );
    }
    
    /*TODO*///#define RD_ZPI	EA_ZPI; tmp = RDMEM(EAD)
    /*TODO*///
    /*TODO*////* write a value from tmp */
    /*TODO*///#define WR_ZPI	EA_ZPI; WRMEM(EAD, tmp)
    /*TODO*///
    /*TODO*////***************************************************************
    /*TODO*/// ***************************************************************
    /*TODO*/// *			Macros to emulate the 65C02 opcodes
    /*TODO*/// ***************************************************************
    /*TODO*/// ***************************************************************/
    /*TODO*///
    /*TODO*///
    /*TODO*////* 65C02 ********************************************************
    /*TODO*/// *	ADC Add with carry
    /*TODO*/// * different setting of flags in decimal mode
    /*TODO*/// ***************************************************************/
    /*TODO*///#undef ADC
    /*TODO*///#define ADC 													
    /*TODO*///	if ((P & F_D) != 0)												
    /*TODO*///	{															
    /*TODO*///		int c = (P & F_C);										
    /*TODO*///		int lo = (A & 0x0f) + (tmp & 0x0f) + c; 				
    /*TODO*///		int hi = (A & 0xf0) + (tmp & 0xf0); 					
    /*TODO*///		P &= ~(F_V | F_C);										
    /*TODO*///		if( lo > 0x09 ) 										
    /*TODO*///		{														
    /*TODO*///			hi += 0x10; 										
    /*TODO*///			lo += 0x06; 										
    /*TODO*///		}														
    /*TODO*///		if( ~(A^tmp) & (A^hi) & F_N )							
    /*TODO*///			P |= F_V;											
    /*TODO*///		if( hi > 0x90 ) 										
    /*TODO*///			hi += 0x60; 										
    /*TODO*///		if ((hi & 0xff00) != 0)										
    /*TODO*///			P |= F_C;											
    /*TODO*///		A = (lo & 0x0f) + (hi & 0xf0);							
    /*TODO*///	}															
    /*TODO*///	else														
    /*TODO*///	{															
    /*TODO*///		int c = (P & F_C);										
    /*TODO*///		int sum = A + tmp + c;									
    /*TODO*///		P &= ~(F_V | F_C);										
    /*TODO*///		if( ~(A^tmp) & (A^sum) & F_N )							
    /*TODO*///			P |= F_V;											
    /*TODO*///		if ((sum & 0xff00) != 0)										
    /*TODO*///			P |= F_C;											
    /*TODO*///		A = (UINT8) sum;										
    /*TODO*///	}															
    /*TODO*///	SET_NZ(A)
    /*TODO*///
    /*TODO*////* 65C02 ********************************************************
    /*TODO*/// *	SBC Subtract with carry
    /*TODO*/// * different setting of flags in decimal mode
    /*TODO*/// ***************************************************************/
    /*TODO*///#undef SBC
    /*TODO*///#define SBC 													
    /*TODO*///	if ((P & F_D) != 0)												
    /*TODO*///	{															
    /*TODO*///		int c = (P & F_C) ^ F_C;								
    /*TODO*///		int sum = A - tmp - c;									
    /*TODO*///		int lo = (A & 0x0f) - (tmp & 0x0f) - c; 				
    /*TODO*///		int hi = (A & 0xf0) - (tmp & 0xf0); 					
    /*TODO*///		P &= ~(F_V | F_C);										
    /*TODO*///		if( (A^tmp) & (A^sum) & F_N )							
    /*TODO*///			P |= F_V;											
    /*TODO*///		if ((lo & 0xf0) != 0) 										
    /*TODO*///			lo -= 6;											
    /*TODO*///		if ((lo & 0x80) != 0) 										
    /*TODO*///			hi -= 0x10; 										
    /*TODO*///		if ((hi & 0x0f00) != 0)										
    /*TODO*///			hi -= 0x60; 										
    /*TODO*///		if( (sum & 0xff00) == 0 )								
    /*TODO*///			P |= F_C;											
    /*TODO*///		A = (lo & 0x0f) + (hi & 0xf0);							
    /*TODO*///	}															
    /*TODO*///	else														
    /*TODO*///	{															
    /*TODO*///		int c = (P & F_C) ^ F_C;								
    /*TODO*///		int sum = A - tmp - c;									
    /*TODO*///		P &= ~(F_V | F_C);										
    /*TODO*///		if( (A^tmp) & (A^sum) & F_N )							
    /*TODO*///			P |= F_V;											
    /*TODO*///		if( (sum & 0xff00) == 0 )								
    /*TODO*///			P |= F_C;											
    /*TODO*///		A = (UINT8) sum;										
    /*TODO*///	}															
    /*TODO*///	SET_NZ(A)
    
    /* 65C02 *******************************************************
     *	BBR Branch if bit is reset
     ***************************************************************/
    public static void BBR(int tmp, int bit) {
    	BRA((tmp & (1<<bit))!=0?false:true);
    }
    
    /* 65C02 *******************************************************
     *	BBS Branch if bit is set
     ***************************************************************/
    public static void BBS(int tmp, int bit) {
    	BRA((tmp & (1<<bit))!=0 ? true : false);
    }
    
    /*TODO*////* 65c02 ********************************************************
    /*TODO*/// *	BRK Break
    /*TODO*/// *	increment PC, push PC hi, PC lo, flags (with B bit set),
    /*TODO*/// *	set I flag, reset D flag and jump via IRQ vector
    /*TODO*/// ***************************************************************/
    /*TODO*///#undef BRK
    /*TODO*///#define BRK 													
    /*TODO*///	PCW++;														
    /*TODO*///	PUSH(PCH);													
    /*TODO*///	PUSH(PCL);													
    /*TODO*///	PUSH(P | F_B);												
    /*TODO*///	P = (P | F_I) & ~F_D;										
    /*TODO*///	PCL = RDMEM(M6502_IRQ_VEC); 								
    /*TODO*///	PCH = RDMEM(M6502_IRQ_VEC+1);								
    /*TODO*///	CHANGE_PC
    
    
    /* 65C02 *******************************************************
     *	DEA Decrement accumulator
     ***************************************************************/
    public static void DEA() {
    	m6502.u8_a = --m6502.u8_a & 0xff;
    	SET_NZ(m6502.u8_a);
    }
    
    /* 65C02 *******************************************************
     *	INA Increment accumulator
     ***************************************************************/
    public static void INA() {
    	m6502.u8_a = ++m6502.u8_a & 0xff;
    	SET_NZ(m6502.u8_a);
    }
    
    /* 65C02 *******************************************************
     *	PHX Push index X
     ***************************************************************/
    public static void PHX() {
    	PUSH(m6502.u8_x & 0xff);
    }
    
    /* 65C02 *******************************************************
     *	PHY Push index Y
     ***************************************************************/
    public static void PHY() {
    	PUSH(m6502.u8_y & 0xff);
    }
    
    /* 65C02 *******************************************************
     *	PLX Pull index X
     ***************************************************************/
    public static void PLX() {
    	m6502.u8_x = PULL(); 
    	SET_NZ(m6502.u8_x);
    }
    
    /* 65C02 *******************************************************
     *	PLY Pull index Y
     ***************************************************************/
    public static void PLY() {
    	m6502.u8_y = PULL(); 
    	SET_NZ(m6502.u8_y);
    }
    
    /* 65C02 *******************************************************
     *	RMB Reset memory bit
     ***************************************************************/
    public static int RMB(int tmp, int bit) {
        return tmp &= ~(1<<bit);
    }
    
    /* 65C02 *******************************************************
     *	SMB Set memory bit
     ***************************************************************/
    public static int SMB(int tmp, int bit) {
    	return tmp |= (1<<bit);
    }
    
    /*TODO*////* 65C02 *******************************************************
    /*TODO*/// * STZ	Store zero
    /*TODO*/// ***************************************************************/
    /*TODO*///#define STZ 													
    /*TODO*///	tmp = 0
    /*TODO*///
    /*TODO*////* 65C02 *******************************************************
    /*TODO*/// * TRB	Test and reset bits
    /*TODO*/// ***************************************************************/
    /*TODO*///#define TRB 													
    /*TODO*///	SET_Z(tmp&A); tmp &= ~A
    /*TODO*///
    /*TODO*////* 65C02 *******************************************************
    /*TODO*/// * TSB	Test and set bits
    /*TODO*/// ***************************************************************/
    /*TODO*///#define TSB 													
    /*TODO*///	SET_Z(tmp&A); tmp |= A
    /*TODO*///
    /*TODO*///
    /*TODO*////***************************************************************
    /*TODO*/// ***************************************************************
    /*TODO*/// *			Macros to emulate the N2A03 opcodes
    /*TODO*/// ***************************************************************
    /*TODO*/// ***************************************************************/
    /*TODO*///
    /*TODO*///
    /*TODO*////* N2A03 *******************************************************
    /*TODO*/// *	ADC Add with carry - no decimal mode
    /*TODO*/// ***************************************************************/
    /*TODO*///#define ADC_NES 												
    /*TODO*///	{															
    /*TODO*///		int c = (P & F_C);										
    /*TODO*///		int sum = A + tmp + c;									
    /*TODO*///		P &= ~(F_V | F_C);										
    /*TODO*///		if( ~(A^tmp) & (A^sum) & F_N )							
    /*TODO*///			P |= F_V;											
    /*TODO*///		if ((sum & 0xff00) != 0)										
    /*TODO*///			P |= F_C;											
    /*TODO*///		A = (UINT8) sum;										
    /*TODO*///	}															
    /*TODO*///	SET_NZ(A)
    /*TODO*///
    /*TODO*////* N2A03 *******************************************************
    /*TODO*/// *	SBC Subtract with carry - no decimal mode
    /*TODO*/// ***************************************************************/
    /*TODO*///#define SBC_NES 												
    /*TODO*///	{															
    /*TODO*///		int c = (P & F_C) ^ F_C;								
    /*TODO*///		int sum = A - tmp - c;									
    /*TODO*///		P &= ~(F_V | F_C);										
    /*TODO*///		if( (A^tmp) & (A^sum) & F_N )							
    /*TODO*///			P |= F_V;											
    /*TODO*///		if( (sum & 0xff00) == 0 )								
    /*TODO*///			P |= F_C;											
    /*TODO*///		A = (UINT8) sum;										
    /*TODO*///	}															
    /*TODO*///	SET_NZ(A)
    /*TODO*///
    /*TODO*///
    /*TODO*////***************************************************************
    /*TODO*/// ***************************************************************
    /*TODO*/// *			Macros to emulate the 65sc02 opcodes
    /*TODO*/// ***************************************************************
    /*TODO*/// ***************************************************************/
    /*TODO*///
    /*TODO*///
    /*TODO*////* 65sc02 ********************************************************
    /*TODO*/// *	BSR Branch to subroutine
    /*TODO*/// ***************************************************************/
    /*TODO*///#define BSR 													
    /*TODO*///	EAL = RDOPARG();											
    /*TODO*///	PUSH(PCH);													
    /*TODO*///	PUSH(PCL);													
    /*TODO*///	EAH = RDOPARG();											
    /*TODO*///	EAW = PCW + (INT16)(EAW-1); 								
    /*TODO*///	PCD = EAD;													
    /*TODO*///	CHANGE_PC
    /*TODO*///
    
}
