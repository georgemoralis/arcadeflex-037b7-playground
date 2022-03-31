package arcadeflex.WIP.v037b7.cpu.t11;

import static arcadeflex.WIP.v037b7.cpu.t11.t11H.*;

public class t11ops {
    
    /*** t11: Portable DEC T-11 emulator ******************************************
    
    	Copyright (C) Aaron Giles 1998
    
        Actual opcode implementation.  Excuse the excessive use of macros, it
        was the only way I could bear to type all this in!
    
    *****************************************************************************/
    
    
    /*TODO*////* given a register index 'r', this computes the effective address for a byte-sized operation
    /*TODO*///   and puts the result in 'ea' */
    /*TODO*///#define MAKE_EAB_RGD(r) ea = REGD(r)
    /*TODO*///#define MAKE_EAB_IN(r)  ea = REGD(r); REGW(r) += ((r) < 6 ? 1 : 2)
    /*TODO*///#define MAKE_EAB_INS(r) ea = REGD(r); REGW(r) += ((r) < 6 ? 1 : 2)
    /*TODO*///#define MAKE_EAB_IND(r) ea = REGD(r); REGW(r) += 2; ea = RWORD(ea)
    /*TODO*///#define MAKE_EAB_DE(r)  REGW(r) -= ((r) < 6 ? 1 : 2); ea = REGD(r)
    /*TODO*///#define MAKE_EAB_DED(r) REGW(r) -= 2; ea = REGD(r); ea = RWORD(ea)
    /*TODO*///#define MAKE_EAB_IX(r)  ea = ROPCODE(); ea = (ea + REGD(r)) & 0xffff
    /*TODO*///#define MAKE_EAB_IXD(r) ea = ROPCODE(); ea = (ea + REGD(r)) & 0xffff; ea = RWORD(ea)
    /*TODO*///
    /*TODO*////* given a register index 'r', this computes the effective address for a word-sized operation
    /*TODO*///   and puts the result in 'ea' */
    /*TODO*////* note that word accesses ignore the low bit!! this fixes APB! */
    /*TODO*///#define MAKE_EAW_RGD(r) MAKE_EAB_RGD(r)
    /*TODO*///#define MAKE_EAW_IN(r)  ea = REGD(r); REGW(r) += 2
    /*TODO*///#define MAKE_EAW_IND(r) MAKE_EAB_IND(r)
    /*TODO*///#define MAKE_EAW_DE(r)  REGW(r) -= 2; ea = REGD(r)
    /*TODO*///#define MAKE_EAW_DED(r) MAKE_EAB_DED(r)
    /*TODO*///#define MAKE_EAW_IX(r)  MAKE_EAB_IX(r)
    /*TODO*///#define MAKE_EAW_IXD(r) MAKE_EAB_IXD(r)
    /*TODO*///
    /*TODO*////* extracts the source/destination register index from the opcode into 'sreg' or 'dreg' */
    /*TODO*///#define GET_SREG sreg = (t11.op >> 6) & 7
    /*TODO*///#define GET_DREG dreg = t11.op & 7
    /*TODO*///
    /*TODO*////* for a byte-sized source operand: extracts 'sreg', computes 'ea', and loads the value into 'source' */
    /*TODO*///#define GET_SB_RG  GET_SREG; source = REGB(sreg)
    /*TODO*///#define GET_SB_RGD GET_SREG; MAKE_EAB_RGD(sreg); source = RBYTE(ea)
    /*TODO*///#define GET_SB_IN  GET_SREG; if (sreg == 7) { source = ROPCODE(); } else { MAKE_EAB_IN(sreg); source = RBYTE(ea); }
    /*TODO*///#define GET_SB_IND GET_SREG; if (sreg == 7) { ea = ROPCODE(); } else { MAKE_EAB_IND(sreg); } source = RBYTE(ea)
    /*TODO*///#define GET_SB_DE  GET_SREG; MAKE_EAB_DE(sreg); source = RBYTE(ea)
    /*TODO*///#define GET_SB_DED GET_SREG; MAKE_EAB_DED(sreg); source = RBYTE(ea)
    /*TODO*///#define GET_SB_IX  GET_SREG; MAKE_EAB_IX(sreg); source = RBYTE(ea)
    /*TODO*///#define GET_SB_IXD GET_SREG; MAKE_EAB_IXD(sreg); source = RBYTE(ea)
    /*TODO*///
    /*TODO*////* for a word-sized source operand: extracts 'sreg', computes 'ea', and loads the value into 'source' */
    /*TODO*///#define GET_SW_RG  GET_SREG; source = REGD(sreg)
    /*TODO*///#define GET_SW_RGD GET_SREG; MAKE_EAW_RGD(sreg); source = RWORD(ea)
    /*TODO*///#define GET_SW_IN  GET_SREG; if (sreg == 7) { source = ROPCODE(); } else { MAKE_EAW_IN(sreg); source = RWORD(ea); }
    /*TODO*///#define GET_SW_IND GET_SREG; if (sreg == 7) { ea = ROPCODE(); } else { MAKE_EAW_IND(sreg); } source = RWORD(ea)
    /*TODO*///#define GET_SW_DE  GET_SREG; MAKE_EAW_DE(sreg); source = RWORD(ea)
    /*TODO*///#define GET_SW_DED GET_SREG; MAKE_EAW_DED(sreg); source = RWORD(ea)
    /*TODO*///#define GET_SW_IX  GET_SREG; MAKE_EAW_IX(sreg); source = RWORD(ea)
    /*TODO*///#define GET_SW_IXD GET_SREG; MAKE_EAW_IXD(sreg); source = RWORD(ea)
    /*TODO*///
    /*TODO*////* for a byte-sized destination operand: extracts 'dreg', computes 'ea', and loads the value into 'dest' */
    /*TODO*///#define GET_DB_RG  GET_DREG; dest = REGB(dreg)
    /*TODO*///#define GET_DB_RGD GET_DREG; MAKE_EAB_RGD(dreg); dest = RBYTE(ea)
    /*TODO*///#define GET_DB_IN  GET_DREG; MAKE_EAB_IN(dreg); dest = RBYTE(ea)
    /*TODO*///#define GET_DB_IND GET_DREG; if (dreg == 7) { ea = ROPCODE(); } else { MAKE_EAB_IND(dreg); } dest = RBYTE(ea)
    /*TODO*///#define GET_DB_DE  GET_DREG; MAKE_EAB_DE(dreg); dest = RBYTE(ea)
    /*TODO*///#define GET_DB_DED GET_DREG; MAKE_EAB_DED(dreg); dest = RBYTE(ea)
    /*TODO*///#define GET_DB_IX  GET_DREG; MAKE_EAB_IX(dreg); dest = RBYTE(ea)
    /*TODO*///#define GET_DB_IXD GET_DREG; MAKE_EAB_IXD(dreg); dest = RBYTE(ea)
    /*TODO*///
    /*TODO*////* for a word-sized destination operand: extracts 'dreg', computes 'ea', and loads the value into 'dest' */
    /*TODO*///#define GET_DW_RG  GET_DREG; dest = REGD(dreg)
    /*TODO*///#define GET_DW_RGD GET_DREG; MAKE_EAW_RGD(dreg); dest = RWORD(ea)
    /*TODO*///#define GET_DW_IN  GET_DREG; MAKE_EAW_IN(dreg); dest = RWORD(ea)
    /*TODO*///#define GET_DW_IND GET_DREG; if (dreg == 7) { ea = ROPCODE(); } else { MAKE_EAW_IND(dreg); } dest = RWORD(ea)
    /*TODO*///#define GET_DW_DE  GET_DREG; MAKE_EAW_DE(dreg); dest = RWORD(ea)
    /*TODO*///#define GET_DW_DED GET_DREG; MAKE_EAW_DED(dreg); dest = RWORD(ea)
    /*TODO*///#define GET_DW_IX  GET_DREG; MAKE_EAW_IX(dreg); dest = RWORD(ea)
    /*TODO*///#define GET_DW_IXD GET_DREG; MAKE_EAW_IXD(dreg); dest = RWORD(ea)
    /*TODO*///
    /*TODO*////* writes a value to a previously computed 'ea' */
    /*TODO*///#define PUT_DB_EA(v) WBYTE(ea, (v))
    /*TODO*///#define PUT_DW_EA(v) WWORD(ea, (v))
    /*TODO*///
    /*TODO*////* writes a value to a previously computed 'dreg' register */
    /*TODO*///#define PUT_DB_DREG(v) REGB(dreg) = (v)
    /*TODO*///#define PUT_DW_DREG(v) REGW(dreg) = (v)
    /*TODO*///
    /*TODO*////* for a byte-sized destination operand: extracts 'dreg', computes 'ea', and writes 'v' to it */
    /*TODO*///#define PUT_DB_RG(v)  GET_DREG; REGB(dreg) = (v)
    /*TODO*///#define PUT_DB_RGD(v) GET_DREG; MAKE_EAB_RGD(dreg); WBYTE(ea, (v))
    /*TODO*///#define PUT_DB_IN(v)  GET_DREG; MAKE_EAB_IN(dreg); WBYTE(ea, (v))
    /*TODO*///#define PUT_DB_IND(v) GET_DREG; if (dreg == 7) { ea = ROPCODE(); } else { MAKE_EAB_IND(dreg); } WBYTE(ea, (v))
    /*TODO*///#define PUT_DB_DE(v)  GET_DREG; MAKE_EAB_DE(dreg); WBYTE(ea, (v))
    /*TODO*///#define PUT_DB_DED(v) GET_DREG; MAKE_EAB_DED(dreg); WBYTE(ea, (v))
    /*TODO*///#define PUT_DB_IX(v)  GET_DREG; MAKE_EAB_IX(dreg); WBYTE(ea, (v))
    /*TODO*///#define PUT_DB_IXD(v) GET_DREG; MAKE_EAB_IXD(dreg); WBYTE(ea, (v))
    /*TODO*///
    /*TODO*////* for a word-sized destination operand: extracts 'dreg', computes 'ea', and writes 'v' to it */
    /*TODO*///#define PUT_DW_RG(v)  GET_DREG; REGW(dreg) = (v)
    /*TODO*///#define PUT_DW_RGD(v) GET_DREG; MAKE_EAW_RGD(dreg); WWORD(ea, (v))
    /*TODO*///#define PUT_DW_IN(v)  GET_DREG; MAKE_EAW_IN(dreg); WWORD(ea, (v))
    /*TODO*///#define PUT_DW_IND(v) GET_DREG; if (dreg == 7) { ea = ROPCODE(); } else { MAKE_EAW_IND(dreg); } WWORD(ea, (v))
    /*TODO*///#define PUT_DW_DE(v)  GET_DREG; MAKE_EAW_DE(dreg); WWORD(ea, (v))
    /*TODO*///#define PUT_DW_DED(v) GET_DREG; MAKE_EAW_DED(dreg); WWORD(ea, (v))
    /*TODO*///#define PUT_DW_IX(v)  GET_DREG; MAKE_EAW_IX(dreg); WWORD(ea, (v))
    /*TODO*///#define PUT_DW_IXD(v) GET_DREG; MAKE_EAW_IXD(dreg); WWORD(ea, (v))
    /*TODO*///
    /*TODO*////* flag clearing; must be done before setting */
    /*TODO*///#define CLR_ZV   (PSW &= ~(ZFLAG | VFLAG))
    /*TODO*///#define CLR_NZV  (PSW &= ~(NFLAG | ZFLAG | VFLAG))
    /*TODO*///#define CLR_NZVC (PSW &= ~(NFLAG | ZFLAG | VFLAG | CFLAG))
    /*TODO*///
    /*TODO*////* set individual flags byte-sized */
    /*TODO*///#define SETB_N (PSW |= (result >> 4) & 0x08)
    /*TODO*///#define SETB_Z (PSW |= ((result & 0xff) == 0) << 2)
    /*TODO*///#define SETB_V (PSW |= ((source ^ dest ^ result ^ (result >> 1)) >> 6) & 0x02)
    /*TODO*///#define SETB_C (PSW |= (result >> 8) & 0x01)
    /*TODO*///#define SETB_NZ SETB_N; SETB_Z
    /*TODO*///#define SETB_NZV SETB_N; SETB_Z; SETB_V
    /*TODO*///#define SETB_NZVC SETB_N; SETB_Z; SETB_V; SETB_C
    /*TODO*///
    /*TODO*////* set individual flags word-sized */
    /*TODO*///#define SETW_N (PSW |= (result >> 12) & 0x08)
    /*TODO*///#define SETW_Z (PSW |= ((result & 0xffff) == 0) << 2)
    /*TODO*///#define SETW_V (PSW |= ((source ^ dest ^ result ^ (result >> 1)) >> 14) & 0x02)
    /*TODO*///#define SETW_C (PSW |= (result >> 16) & 0x01)
    /*TODO*///#define SETW_NZ SETW_N; SETW_Z
    /*TODO*///#define SETW_NZV SETW_N; SETW_Z; SETW_V
    /*TODO*///#define SETW_NZVC SETW_N; SETW_Z; SETW_V; SETW_C
    /*TODO*///
    /*TODO*////* operations */
    /*TODO*////* ADC: dst += C */
    /*TODO*///#define ADC_R(d)    int dreg, source, dest, result;     source = GET_C; GET_DW_##d; CLR_NZVC; result = dest + source; SETW_NZVC; PUT_DW_DREG(result)
    /*TODO*///#define ADC_M(d)    int dreg, source, dest, result, ea; source = GET_C; GET_DW_##d; CLR_NZVC; result = dest + source; SETW_NZVC; PUT_DW_EA(result)
    /*TODO*///#define ADCB_R(d)   int dreg, source, dest, result;     source = GET_C; GET_DB_##d; CLR_NZVC; result = dest + source; SETB_NZVC; PUT_DB_DREG(result)
    /*TODO*///#define ADCB_M(d)   int dreg, source, dest, result, ea; source = GET_C; GET_DB_##d; CLR_NZVC; result = dest + source; SETB_NZVC; PUT_DB_EA(result)
    /*TODO*////* ADD: dst += src */
    /*TODO*///#define ADD_R(s,d)  int sreg, dreg, source, dest, result;     GET_SW_##s; GET_DW_##d; CLR_NZVC; result = dest + source; SETW_NZVC; PUT_DW_DREG(result)
    /*TODO*///#define ADD_X(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZVC; result = dest + source; SETW_NZVC; PUT_DW_DREG(result)
    /*TODO*///#define ADD_M(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZVC; result = dest + source; SETW_NZVC; PUT_DW_EA(result)
    /*TODO*////* ASL: dst = (dst << 1); C = (dst >> 7) */
    /*TODO*///#define ASL_R(d)    int dreg, dest, result;     GET_DW_##d; CLR_NZVC; result = dest << 1; SETW_NZ; PSW |= (dest >> 15) & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DW_DREG(result)
    /*TODO*///#define ASL_M(d)    int dreg, dest, result, ea; GET_DW_##d; CLR_NZVC; result = dest << 1; SETW_NZ; PSW |= (dest >> 15) & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DW_EA(result)
    /*TODO*///#define ASLB_R(d)   int dreg, dest, result;     GET_DB_##d; CLR_NZVC; result = dest << 1; SETB_NZ; PSW |= (dest >> 7) & 1;  PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DB_DREG(result)
    /*TODO*///#define ASLB_M(d)   int dreg, dest, result, ea; GET_DB_##d; CLR_NZVC; result = dest << 1; SETB_NZ; PSW |= (dest >> 7) & 1;  PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DB_EA(result)
    /*TODO*////* ASR: dst = (dst << 1); C = (dst >> 7) */
    /*TODO*///#define ASR_R(d)    int dreg, dest, result;     GET_DW_##d; CLR_NZVC; result = (dest >> 1) | (dest & 0x8000); SETW_NZ; PSW |= dest & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DW_DREG(result)
    /*TODO*///#define ASR_M(d)    int dreg, dest, result, ea; GET_DW_##d; CLR_NZVC; result = (dest >> 1) | (dest & 0x8000); SETW_NZ; PSW |= dest & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DW_EA(result)
    /*TODO*///#define ASRB_R(d)   int dreg, dest, result;     GET_DB_##d; CLR_NZVC; result = (dest >> 1) | (dest & 0x80);   SETB_NZ; PSW |= dest & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DB_DREG(result)
    /*TODO*///#define ASRB_M(d)   int dreg, dest, result, ea; GET_DB_##d; CLR_NZVC; result = (dest >> 1) | (dest & 0x80);   SETB_NZ; PSW |= dest & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DB_EA(result)
    /*TODO*////* BIC: dst &= ~src */
    /*TODO*///#define BIC_R(s,d)  int sreg, dreg, source, dest, result;     GET_SW_##s; GET_DW_##d; CLR_NZV; result = dest & ~source; SETW_NZ; PUT_DW_DREG(result)
    /*TODO*///#define BIC_X(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZV; result = dest & ~source; SETW_NZ; PUT_DW_DREG(result)
    /*TODO*///#define BIC_M(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZV; result = dest & ~source; SETW_NZ; PUT_DW_EA(result)
    /*TODO*///#define BICB_R(s,d) int sreg, dreg, source, dest, result;     GET_SB_##s; GET_DB_##d; CLR_NZV; result = dest & ~source; SETB_NZ; PUT_DB_DREG(result)
    /*TODO*///#define BICB_X(s,d) int sreg, dreg, source, dest, result, ea; GET_SB_##s; GET_DB_##d; CLR_NZV; result = dest & ~source; SETB_NZ; PUT_DB_DREG(result)
    /*TODO*///#define BICB_M(s,d) int sreg, dreg, source, dest, result, ea; GET_SB_##s; GET_DB_##d; CLR_NZV; result = dest & ~source; SETB_NZ; PUT_DB_EA(result)
    /*TODO*////* BIS: dst |= src */
    /*TODO*///#define BIS_R(s,d)  int sreg, dreg, source, dest, result;     GET_SW_##s; GET_DW_##d; CLR_NZV; result = dest | source; SETW_NZ; PUT_DW_DREG(result)
    /*TODO*///#define BIS_X(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZV; result = dest | source; SETW_NZ; PUT_DW_DREG(result)
    /*TODO*///#define BIS_M(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZV; result = dest | source; SETW_NZ; PUT_DW_EA(result)
    /*TODO*///#define BISB_R(s,d) int sreg, dreg, source, dest, result;     GET_SB_##s; GET_DB_##d; CLR_NZV; result = dest | source; SETB_NZ; PUT_DB_DREG(result)
    /*TODO*///#define BISB_X(s,d) int sreg, dreg, source, dest, result, ea; GET_SB_##s; GET_DB_##d; CLR_NZV; result = dest | source; SETB_NZ; PUT_DB_DREG(result)
    /*TODO*///#define BISB_M(s,d) int sreg, dreg, source, dest, result, ea; GET_SB_##s; GET_DB_##d; CLR_NZV; result = dest | source; SETB_NZ; PUT_DB_EA(result)
    /*TODO*////* BIT: flags = dst & src */
    /*TODO*///#define BIT_R(s,d)  int sreg, dreg, source, dest, result;     GET_SW_##s; GET_DW_##d; CLR_NZV; result = dest & source; SETW_NZ;
    /*TODO*///#define BIT_M(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZV; result = dest & source; SETW_NZ;
    /*TODO*///#define BITB_R(s,d) int sreg, dreg, source, dest, result;     GET_SB_##s; GET_DB_##d; CLR_NZV; result = dest & source; SETB_NZ;
    /*TODO*///#define BITB_M(s,d) int sreg, dreg, source, dest, result, ea; GET_SB_##s; GET_DB_##d; CLR_NZV; result = dest & source; SETB_NZ;
    /*TODO*////* BR: if (condition != 0) branch */
    /*TODO*///#define BR(c)		if (c != 0) { PC += 2 * (signed char)(t11.op & 0xff); }
    /*TODO*////* CLR: dst = 0 */
    /*TODO*///#define CLR_R(d)    int dreg;     PUT_DW_##d(0); CLR_NZVC; SET_Z
    /*TODO*///#define CLR_M(d)    int dreg, ea; PUT_DW_##d(0); CLR_NZVC; SET_Z
    /*TODO*///#define CLRB_R(d)   int dreg;     PUT_DB_##d(0); CLR_NZVC; SET_Z
    /*TODO*///#define CLRB_M(d)   int dreg, ea; PUT_DB_##d(0); CLR_NZVC; SET_Z
    /*TODO*////* CMP: flags = src - dst */
    /*TODO*///#define CMP_R(s,d)  int sreg, dreg, source, dest, result;     GET_SW_##s; GET_DW_##d; CLR_NZVC; result = source - dest; SETW_NZVC;
    /*TODO*///#define CMP_M(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZVC; result = source - dest; SETW_NZVC;
    /*TODO*///#define CMPB_R(s,d) int sreg, dreg, source, dest, result;     GET_SB_##s; GET_DB_##d; CLR_NZVC; result = source - dest; SETB_NZVC;
    /*TODO*///#define CMPB_M(s,d) int sreg, dreg, source, dest, result, ea; GET_SB_##s; GET_DB_##d; CLR_NZVC; result = source - dest; SETB_NZVC;
    /*TODO*////* COM: dst = ~dst */
    /*TODO*///#define COM_R(d)    int dreg, dest, result;     GET_DW_##d; CLR_NZVC; result = ~dest; SETW_NZ; SET_C; PUT_DW_DREG(result)
    /*TODO*///#define COM_M(d)    int dreg, dest, result, ea; GET_DW_##d; CLR_NZVC; result = ~dest; SETW_NZ; SET_C; PUT_DW_EA(result)
    /*TODO*///#define COMB_R(d)   int dreg, dest, result;     GET_DB_##d; CLR_NZVC; result = ~dest; SETB_NZ; SET_C; PUT_DB_DREG(result)
    /*TODO*///#define COMB_M(d)   int dreg, dest, result, ea; GET_DB_##d; CLR_NZVC; result = ~dest; SETB_NZ; SET_C; PUT_DB_EA(result)
    /*TODO*////* DEC: dst -= 1 */
    /*TODO*///#define DEC_R(d)    int dreg, dest, result;     GET_DW_##d; CLR_NZV; result = dest - 1; SETW_NZ; if (dest == 0x8000) SET_V; PUT_DW_DREG(result)
    /*TODO*///#define DEC_M(d)    int dreg, dest, result, ea; GET_DW_##d; CLR_NZV; result = dest - 1; SETW_NZ; if (dest == 0x8000) SET_V; PUT_DW_EA(result)
    /*TODO*///#define DECB_R(d)   int dreg, dest, result;     GET_DB_##d; CLR_NZV; result = dest - 1; SETB_NZ; if (dest == 0x80)   SET_V; PUT_DB_DREG(result)
    /*TODO*///#define DECB_M(d)   int dreg, dest, result, ea; GET_DB_##d; CLR_NZV; result = dest - 1; SETB_NZ; if (dest == 0x80)   SET_V; PUT_DB_EA(result)
    /*TODO*////* INC: dst += 1 */
    /*TODO*///#define INC_R(d)    int dreg, dest, result;     GET_DW_##d; CLR_NZV; result = dest + 1; SETW_NZ; if (dest == 0x7fff) SET_V; PUT_DW_DREG(result)
    /*TODO*///#define INC_M(d)    int dreg, dest, result, ea; GET_DW_##d; CLR_NZV; result = dest + 1; SETW_NZ; if (dest == 0x7fff) SET_V; PUT_DW_EA(result)
    /*TODO*///#define INCB_R(d)   int dreg, dest, result;     GET_DB_##d; CLR_NZV; result = dest + 1; SETB_NZ; if (dest == 0x7f)   SET_V; PUT_DB_DREG(result)
    /*TODO*///#define INCB_M(d)   int dreg, dest, result, ea; GET_DB_##d; CLR_NZV; result = dest + 1; SETB_NZ; if (dest == 0x7f)   SET_V; PUT_DB_EA(result)
    /*TODO*////* JMP: PC = ea */
    /*TODO*///#define JMP(d)      int dreg, ea; GET_DREG; MAKE_EAW_##d(dreg); PC = ea
    /*TODO*////* JSR: PUSH src, src = PC, PC = ea */
    /*TODO*///#define JSR(d)      int sreg, dreg, ea; GET_SREG; GET_DREG; MAKE_EAW_##d(dreg); PUSH(REGW(sreg)); REGW(sreg) = PC; PC = ea
    /*TODO*////* MFPS: dst = flags */
    /*TODO*///#define MFPS_R(d)   int dreg, result;     result = PSW; CLR_NZV; SETB_NZ; PUT_DW_##d((signed char)result)
    /*TODO*///#define MFPS_M(d)   int dreg, result, ea; result = PSW; CLR_NZV; SETB_NZ; PUT_DB_##d(result)
    /*TODO*////* MOV: dst = src */
    /*TODO*///#define MOV_R(s,d)  int sreg, dreg, source, result;     GET_SW_##s; CLR_NZV; result = source; SETW_NZ; PUT_DW_##d(result)
    /*TODO*///#define MOV_M(s,d)  int sreg, dreg, source, result, ea; GET_SW_##s; CLR_NZV; result = source; SETW_NZ; PUT_DW_##d(result)
    /*TODO*///#define MOVB_R(s,d) int sreg, dreg, source, result;     GET_SB_##s; CLR_NZV; result = source; SETB_NZ; PUT_DW_##d((signed char)result)
    /*TODO*///#define MOVB_X(s,d) int sreg, dreg, source, result, ea; GET_SB_##s; CLR_NZV; result = source; SETB_NZ; PUT_DW_##d((signed char)result)
    /*TODO*///#define MOVB_M(s,d) int sreg, dreg, source, result, ea; GET_SB_##s; CLR_NZV; result = source; SETB_NZ; PUT_DB_##d(result)
    /*TODO*////* MTPS: flags = src */
    /*TODO*///#define MTPS_R(d)   int dreg, dest;     GET_DW_##d; PSW = (PSW & ~0xef) | (dest & 0xef); t11_check_irqs()
    /*TODO*///#define MTPS_M(d)   int dreg, dest, ea; GET_DW_##d; PSW = (PSW & ~0xef) | (dest & 0xef); t11_check_irqs()
    /*TODO*////* NEG: dst = -dst */
    /*TODO*///#define NEG_R(d)    int dreg, dest, result;     GET_DW_##d; CLR_NZVC; result = -dest; SETW_NZ; if (dest == 0x8000) SET_V; if (result != 0) SET_C; PUT_DW_DREG(result)
    /*TODO*///#define NEG_M(d)    int dreg, dest, result, ea; GET_DW_##d; CLR_NZVC; result = -dest; SETW_NZ; if (dest == 0x8000) SET_V; if (result != 0) SET_C; PUT_DW_EA(result)
    /*TODO*///#define NEGB_R(d)   int dreg, dest, result;     GET_DB_##d; CLR_NZVC; result = -dest; SETB_NZ; if (dest == 0x80)   SET_V; if (result != 0) SET_C; PUT_DB_DREG(result)
    /*TODO*///#define NEGB_M(d)   int dreg, dest, result, ea; GET_DB_##d; CLR_NZVC; result = -dest; SETB_NZ; if (dest == 0x80)   SET_V; if (result != 0) SET_C; PUT_DB_EA(result)
    /*TODO*////* ROL: dst = (dst << 1) | C; C = (dst >> 7) */
    /*TODO*///#define ROL_R(d)    int dreg, dest, result;     GET_DW_##d; result = (dest << 1) | GET_C; CLR_NZVC; SETW_NZ; PSW |= (dest >> 15) & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DW_DREG(result)
    /*TODO*///#define ROL_M(d)    int dreg, dest, result, ea; GET_DW_##d; result = (dest << 1) | GET_C; CLR_NZVC; SETW_NZ; PSW |= (dest >> 15) & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DW_EA(result)
    /*TODO*///#define ROLB_R(d)   int dreg, dest, result;     GET_DB_##d; result = (dest << 1) | GET_C; CLR_NZVC; SETB_NZ; PSW |= (dest >> 7) & 1;  PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DB_DREG(result)
    /*TODO*///#define ROLB_M(d)   int dreg, dest, result, ea; GET_DB_##d; result = (dest << 1) | GET_C; CLR_NZVC; SETB_NZ; PSW |= (dest >> 7) & 1;  PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DB_EA(result)
    /*TODO*////* ROR: dst = (dst >> 1) | (C << 7); C = dst & 1 */
    /*TODO*///#define ROR_R(d)    int dreg, dest, result;     GET_DW_##d; result = (dest >> 1) | (GET_C << 15); CLR_NZVC; SETW_NZ; PSW |= dest & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DW_DREG(result)
    /*TODO*///#define ROR_M(d)    int dreg, dest, result, ea; GET_DW_##d; result = (dest >> 1) | (GET_C << 15); CLR_NZVC; SETW_NZ; PSW |= dest & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DW_EA(result)
    /*TODO*///#define RORB_R(d)   int dreg, dest, result;     GET_DB_##d; result = (dest >> 1) | (GET_C << 7);  CLR_NZVC; SETB_NZ; PSW |= dest & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DB_DREG(result)
    /*TODO*///#define RORB_M(d)   int dreg, dest, result, ea; GET_DB_##d; result = (dest >> 1) | (GET_C << 7);  CLR_NZVC; SETB_NZ; PSW |= dest & 1; PSW |= ((PSW << 1) ^ (PSW >> 2)) & 2; PUT_DB_EA(result)
    /*TODO*////* SBC: dst -= C */
    /*TODO*///#define SBC_R(d)    int dreg, source, dest, result;     source = GET_C; GET_DW_##d; CLR_NZVC; result = dest - source; SETW_NZVC; PUT_DW_DREG(result)
    /*TODO*///#define SBC_M(d)    int dreg, source, dest, result, ea; source = GET_C; GET_DW_##d; CLR_NZVC; result = dest - source; SETW_NZVC; PUT_DW_EA(result)
    /*TODO*///#define SBCB_R(d)   int dreg, source, dest, result;     source = GET_C; GET_DB_##d; CLR_NZVC; result = dest - source; SETB_NZVC; PUT_DB_DREG(result)
    /*TODO*///#define SBCB_M(d)   int dreg, source, dest, result, ea; source = GET_C; GET_DB_##d; CLR_NZVC; result = dest - source; SETB_NZVC; PUT_DB_EA(result)
    /*TODO*////* SUB: dst -= src */
    /*TODO*///#define SUB_R(s,d)  int sreg, dreg, source, dest, result;     GET_SW_##s; GET_DW_##d; CLR_NZVC; result = dest - source; SETW_NZVC; PUT_DW_DREG(result)
    /*TODO*///#define SUB_X(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZVC; result = dest - source; SETW_NZVC; PUT_DW_DREG(result)
    /*TODO*///#define SUB_M(s,d)  int sreg, dreg, source, dest, result, ea; GET_SW_##s; GET_DW_##d; CLR_NZVC; result = dest - source; SETW_NZVC; PUT_DW_EA(result)
    /*TODO*///#define SUBB_R(s,d) int sreg, dreg, source, dest, result;     GET_SB_##s; GET_DB_##d; CLR_NZVC; result = dest - source; SETB_NZVC; PUT_DB_DREG(result)
    /*TODO*///#define SUBB_X(s,d) int sreg, dreg, source, dest, result, ea; GET_SB_##s; GET_DB_##d; CLR_NZVC; result = dest - source; SETB_NZVC; PUT_DB_DREG(result)
    /*TODO*///#define SUBB_M(s,d) int sreg, dreg, source, dest, result, ea; GET_SB_##s; GET_DB_##d; CLR_NZVC; result = dest - source; SETB_NZVC; PUT_DB_EA(result)
    /*TODO*////* SWAB: dst = (dst >> 8) + (dst << 8) */
    /*TODO*///#define SWAB_R(d)   int dreg, dest, result;     GET_DW_##d; CLR_NZVC; result = ((dest >> 8) & 0xff) + (dest << 8); SETB_NZ; PUT_DW_DREG(result)
    /*TODO*///#define SWAB_M(d)   int dreg, dest, result, ea; GET_DW_##d; CLR_NZVC; result = ((dest >> 8) & 0xff) + (dest << 8); SETB_NZ; PUT_DW_EA(result)
    /*TODO*////* SXT: dst = sign-extend dst */
    /*TODO*///#define SXT_R(d)    int dreg, result;     CLR_ZV; if (GET_N != 0) result = -1; else { result = 0; SET_Z; } PUT_DW_##d(result)
    /*TODO*///#define SXT_M(d)    int dreg, result, ea; CLR_ZV; if (GET_N != 0) result = -1; else { result = 0; SET_Z; } PUT_DW_##d(result)
    /*TODO*////* TST: dst = ~dst */
    /*TODO*///#define TST_R(d)    int dreg, dest, result;     GET_DW_##d; CLR_NZVC; result = dest; SETW_NZ;
    /*TODO*///#define TST_M(d)    int dreg, dest, result, ea; GET_DW_##d; CLR_NZVC; result = dest; SETW_NZ;
    /*TODO*///#define TSTB_R(d)   int dreg, dest, result;     GET_DB_##d; CLR_NZVC; result = dest; SETB_NZ;
    /*TODO*///#define TSTB_M(d)   int dreg, dest, result, ea; GET_DB_##d; CLR_NZVC; result = dest; SETB_NZ;
    /*TODO*////* XOR: dst ^= src */
    /*TODO*///#define XOR_R(d)    int sreg, dreg, source, dest, result;     GET_SREG; source = REGW(sreg); GET_DW_##d; CLR_NZV; result = dest ^ source; SETW_NZ; PUT_DW_DREG(result)
    /*TODO*///#define XOR_M(d)    int sreg, dreg, source, dest, result, ea; GET_SREG; source = REGW(sreg); GET_DW_##d; CLR_NZV; result = dest ^ source; SETW_NZ; PUT_DW_EA(result)
    
    
    
    static InstructionPtr op_0000 = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	switch (t11.op & 0x3f)
    /*TODO*///	{
    /*TODO*///		case 0x00:	/* HALT  */ t11_ICount = 0; break;
    /*TODO*///		case 0x01:	/* WAIT  */ t11.wait_state = 1; t11_ICount = 0; break;
    /*TODO*///		case 0x02:	/* RTI   */ PC = POP(); PSW = POP(); t11_check_irqs(); break;
    /*TODO*///		case 0x03:	/* BPT   */ PUSH(PSW); PUSH(PC); PC = RWORD(0x0c); PSW = RWORD(0x0e); t11_check_irqs(); break;
    /*TODO*///		case 0x04:	/* IOT   */ PUSH(PSW); PUSH(PC); PC = RWORD(0x10); PSW = RWORD(0x12); t11_check_irqs(); break;
    /*TODO*///		case 0x05:	/* RESET */ break;
    /*TODO*///		case 0x06:	/* RTT   */ PC = POP(); PSW = POP(); t11_check_irqs(); break;
    /*TODO*///		default: 	illegal(); break;
    /*TODO*///	}
        }
    };
    
    static InstructionPtr illegal = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	PUSH(PSW);
    /*TODO*///	PUSH(PC);
    /*TODO*///	PC = RWORD(0x08);
    /*TODO*///	PSW = RWORD(0x0a);
    /*TODO*///	t11_check_irqs();
    /*TODO*///PC = 0;
        }
    };
    
    static InstructionPtr jmp_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
            /*TODO*/// JMP(RGD); }
        }
    };
    
    static InstructionPtr jmp_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
            /*TODO*///JMP(IN);
        }
    };
        
    static InstructionPtr jmp_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
            /*TODO*///JMP(IND);
        }
    };
        
    static InstructionPtr jmp_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
            /*TODO*///JMP(DE);
        }
    };
    
    static InstructionPtr jmp_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
            /*TODO*///JMP(DED);
        }
    };
        
    static InstructionPtr jmp_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
            /*TODO*///JMP(IX);  
        }
    };
        
    static InstructionPtr jmp_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
            /*TODO*///JMP(IXD); 
        }
    };
    
    static InstructionPtr rts = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
            /*TODO*///int dreg;
    /*TODO*///	GET_DREG;
    /*TODO*///	PC = REGD(dreg);
    /*TODO*///	REGW(dreg) = POP();
        }
    };
    
    static InstructionPtr ccc = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	PSW &= ~(t11.op & 15); 
        }
    };
    
    static InstructionPtr scc = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	PSW |=  (t11.op & 15); 
        }
    };

    static InstructionPtr swab_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SWAB_R(RG); 
        }
    };
    
    static InstructionPtr swab_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SWAB_M(RGD); 
        }
    };
    
    static InstructionPtr swab_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SWAB_M(IN); 
        }
    };
    
    static InstructionPtr swab_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SWAB_M(IND); 
        }
    };
    
    static InstructionPtr swab_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SWAB_M(DE); 
        }
    };
    
    static InstructionPtr swab_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SWAB_M(DED); 
        }
    };
    
    static InstructionPtr swab_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SWAB_M(IX); 
        }
    };
    
    static InstructionPtr swab_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SWAB_M(IXD); 
        }
    };
    
    static InstructionPtr br = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	BR(1); 
        }
    };
    
    static InstructionPtr bne = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	BR(!GET_Z); 
        }
    };
    
    static InstructionPtr beq = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	BR( GET_Z); 
        }
    };
    
    static InstructionPtr bge = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	BR(!((GET_N >> 2) ^ GET_V)); 
        }
    };
    
    static InstructionPtr blt = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	BR(((GET_N >> 2) ^ GET_V)); 
        }
    };
    
    static InstructionPtr bgt = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	BR(!GET_Z && !((GET_N >> 2) ^ GET_V)); 
        }
    };
    
    static InstructionPtr ble = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	BR( GET_Z || ((GET_N >> 2) ^ GET_V)); 
        }
    };
    
    static InstructionPtr jsr_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	JSR(RGD); 
        }
    };
    
    static InstructionPtr jsr_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	JSR(IN);  
        }
    };
    
    static InstructionPtr jsr_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	JSR(IND); 
        }
    };
    
    static InstructionPtr jsr_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	JSR(DE);  
        }
    };
    
    static InstructionPtr jsr_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	JSR(DED); 
        }
    };
    
    static InstructionPtr jsr_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	JSR(IX);  
        }
    };
    
    static InstructionPtr jsr_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	JSR(IXD); 
        }
    };
    
    static InstructionPtr clr_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CLR_R(RG);  
        }
    };
    
    static InstructionPtr clr_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CLR_M(RGD); 
        }
    };
    
    static InstructionPtr clr_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CLR_M(IN);  
        }
    };
    
    static InstructionPtr clr_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CLR_M(IND); 
        }
    };
    
    static InstructionPtr clr_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CLR_M(DE);  
        }
    };
    
    static InstructionPtr clr_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CLR_M(DED); 
        }
    };
    
    static InstructionPtr clr_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CLR_M(IX);  
        }
    };
    
    static InstructionPtr clr_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CLR_M(IXD); 
        }
    };
    
    static InstructionPtr com_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	COM_R(RG);  
        }
    };
    
    static InstructionPtr com_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	COM_M(RGD); 
        }
    };
    
    static InstructionPtr com_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	COM_M(IN);  
        }
    };
    
    static InstructionPtr com_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	COM_M(IND); 
        }
    };
    
    static InstructionPtr com_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	COM_M(DE);  
        }
    };
    
    static InstructionPtr com_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	COM_M(DED); 
        }
    };
    
    static InstructionPtr com_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	COM_M(IX);  
        }
    };
    
    static InstructionPtr com_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	COM_M(IXD); 
        }
    };
    
    
    static InstructionPtr inc_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	INC_R(RG);  
        }
    };
    
    static InstructionPtr inc_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	INC_M(RGD); 
        }
    };
    
    static InstructionPtr inc_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	INC_M(IN);  
        }
    };
    
    static InstructionPtr inc_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	INC_M(IND); 
        }
    };
    
    static InstructionPtr inc_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	INC_M(DE);  
        }
    };
    
    static InstructionPtr inc_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	INC_M(DED); 
        }
    };
    
    static InstructionPtr inc_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	INC_M(IX);  
        }
    };
    
    static InstructionPtr inc_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	INC_M(IXD); 
        }
    };
    
    static InstructionPtr dec_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	DEC_R(RG);  
        }
    };
    
    static InstructionPtr dec_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	DEC_M(RGD); 
        }
    };
    
    static InstructionPtr dec_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	DEC_M(IN);  
        }
    };
    
    static InstructionPtr dec_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	DEC_M(IND); 
        }
    };
    
    static InstructionPtr dec_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	DEC_M(DE);  
        }
    };
    
    static InstructionPtr dec_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	DEC_M(DED); 
        }
    };
    
    static InstructionPtr dec_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	DEC_M(IX);  
        }
    };
    
    static InstructionPtr dec_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	DEC_M(IXD); 
        }
    };
    
    
    static InstructionPtr neg_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	NEG_R(RG);  
        }
    };
    
    static InstructionPtr neg_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	NEG_M(RGD); 
        }
    };
    
    static InstructionPtr neg_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	NEG_M(IN);  
        }
    };
    
    static InstructionPtr neg_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	NEG_M(IND); 
        }
    };
    
    static InstructionPtr neg_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	NEG_M(DE);  
        }
    };
    
    static InstructionPtr neg_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	NEG_M(DED); 
        }
    };
    
    static InstructionPtr neg_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	NEG_M(IX);  
        }
    };
    
    static InstructionPtr neg_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	NEG_M(IXD); 
        }
    };
    
    
    static InstructionPtr adc_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ADC_R(RG);  
        }
    };
    
    static InstructionPtr adc_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ADC_M(RGD); 
        }
    };
    
    static InstructionPtr adc_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ADC_M(IN);  
        }
    };
    
    static InstructionPtr adc_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ADC_M(IND); 
        }
    };
    
    static InstructionPtr adc_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ADC_M(DE);  
        }
    };
    
    static InstructionPtr adc_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ADC_M(DED); 
        }
    };
    
    static InstructionPtr adc_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ADC_M(IX);  
        }
    };
    
    static InstructionPtr adc_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ADC_M(IXD); 
        }
    };
    
    static InstructionPtr sbc_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SBC_R(RG);  
        }
    };
    
    static InstructionPtr sbc_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SBC_M(RGD); 
        }
    };
    
    static InstructionPtr sbc_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SBC_M(IN);  
        }
    };
    
    static InstructionPtr sbc_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SBC_M(IND); 
        }
    };
    
    static InstructionPtr sbc_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SBC_M(DE);  
        }
    };
    
    static InstructionPtr sbc_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SBC_M(DED); 
        }
    };
    
    static InstructionPtr sbc_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SBC_M(IX);  
        }
    };
    
    static InstructionPtr sbc_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SBC_M(IXD); 
        }
    };
    
    static InstructionPtr tst_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	TST_R(RG); 
        }
    };
    
    static InstructionPtr tst_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	TST_M(RGD); 
        }
    };
    
    static InstructionPtr tst_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	TST_M(IN); 
        }
    };
    
    static InstructionPtr tst_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	TST_M(IND); 
        }
    };
    
    static InstructionPtr tst_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	TST_M(DE); 
        }
    };
    
    static InstructionPtr tst_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	TST_M(DED); 
        }
    };
    
    static InstructionPtr tst_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	TST_M(IX); 
        }
    };
    
    static InstructionPtr tst_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	TST_M(IXD); 
        }
    };
    
    
    static InstructionPtr ror_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROR_R(RG); 
        }
    };
    
    static InstructionPtr ror_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROR_M(RGD); 
        }
    };
    
    static InstructionPtr ror_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROR_M(IN); 
        }
    };
    
    static InstructionPtr ror_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROR_M(IND); 
        }
    };
    
    static InstructionPtr ror_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROR_M(DE); 
        }
    };
    
    static InstructionPtr ror_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROR_M(DED); 
        }
    };
    
    static InstructionPtr ror_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROR_M(IX); 
        }
    };
    
    static InstructionPtr ror_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROR_M(IXD); 
        }
    };
    
    
    static InstructionPtr rol_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROL_R(RG); 
        }
    };
    
    static InstructionPtr rol_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROL_M(RGD); 
        }
    };
    
    static InstructionPtr rol_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROL_M(IN); 
        }
    };
    
    static InstructionPtr rol_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROL_M(IND); 
        }
    };
    
    static InstructionPtr rol_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROL_M(DE); 
        }
    };
    
    static InstructionPtr rol_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROL_M(DED); 
        }
    };
    
    static InstructionPtr rol_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROL_M(IX); 
        }
    };
    
    static InstructionPtr rol_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ROL_M(IXD); 
        }
    };
    
    
    static InstructionPtr asr_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASR_R(RG); 
        }
    };
    
    static InstructionPtr asr_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASR_M(RGD); 
        }
    };
    
    static InstructionPtr asr_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASR_M(IN); 
        }
    };
    
    static InstructionPtr asr_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASR_M(IND); 
        }
    };
    
    static InstructionPtr asr_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASR_M(DE); 
        }
    };
    
    static InstructionPtr asr_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASR_M(DED); 
        }
    };
    
    static InstructionPtr asr_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASR_M(IX); 
        }
    };
    
    static InstructionPtr asr_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASR_M(IXD); 
        }
    };
    
    
    static InstructionPtr asl_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASL_R(RG); 
        }
    };
    
    static InstructionPtr asl_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASL_M(RGD); 
        }
    };
    
    static InstructionPtr asl_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASL_M(IN); 
        }
    };
    
    static InstructionPtr asl_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASL_M(IND); 
        }
    };
    
    static InstructionPtr asl_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASL_M(DE); 
        }
    };
    
    static InstructionPtr asl_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASL_M(DED); 
        }
    };
    
    static InstructionPtr asl_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASL_M(IX); 
        }
    };
    
    static InstructionPtr asl_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	ASL_M(IXD); 
        }
    };
    
    
    static InstructionPtr sxt_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SXT_R(RG); 
        }
    };
    
    static InstructionPtr sxt_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SXT_M(RGD); 
        }
    };
    
    static InstructionPtr sxt_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SXT_M(IN); 
        }
    };
    
    static InstructionPtr sxt_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SXT_M(IND); 
        }
    };
    
    static InstructionPtr sxt_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SXT_M(DE); 
        }
    };
    
    static InstructionPtr sxt_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SXT_M(DED); 
        }
    };
    
    static InstructionPtr sxt_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SXT_M(IX); 
        }
    };
    
    static InstructionPtr sxt_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	SXT_M(IXD); 
        }
    };
    
    
    static InstructionPtr mov_rg_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_R(RG,RG); 
        }
    };
    
    static InstructionPtr mov_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RG,RGD); 
        }
    };
    
    static InstructionPtr mov_rg_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RG,IN); 
        }
    };
    
    static InstructionPtr mov_rg_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RG,IND); 
        }
    };
    
    static InstructionPtr mov_rg_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RG,DE); 
        }
    };
    
    static InstructionPtr mov_rg_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RG,DED); 
        }
    };
    
    static InstructionPtr mov_rg_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RG,IX); 
        }
    };
    
    static InstructionPtr mov_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RG,IXD); 
        }
    };
    
    static InstructionPtr mov_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RGD,RG); 
        }
    };
    
    static InstructionPtr mov_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RGD,RGD); 
        }
    };
    
    static InstructionPtr mov_rgd_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RGD,IN); 
        }
    };
    
    static InstructionPtr mov_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RGD,IND); 
        }
    };
    
    static InstructionPtr mov_rgd_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RGD,DE); 
        }
    };
    
    static InstructionPtr mov_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RGD,DED); 
        }
    };
    
    static InstructionPtr mov_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RGD,IX); 
        }
    };
    
    static InstructionPtr mov_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(RGD,IXD); 
        }
    };
    
    static InstructionPtr mov_in_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IN,RG); 
        }
    };
    
    static InstructionPtr mov_in_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IN,RGD); 
        }
    };
    
    static InstructionPtr mov_in_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IN,IN); 
        }
    };
    
    static InstructionPtr mov_in_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IN,IND); 
        }
    };
    
    static InstructionPtr mov_in_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IN,DE); 
        }
    };
    
    static InstructionPtr mov_in_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IN,DED); 
        }
    };
    
    static InstructionPtr mov_in_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IN,IX); 
        }
    };
    
    static InstructionPtr mov_in_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IN,IXD); 
        }
    };
    
    static InstructionPtr mov_ind_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IND,RG); 
        }
    };
    
    static InstructionPtr mov_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IND,RGD); 
        }
    };
    
    static InstructionPtr mov_ind_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IND,IN); 
        }
    };
    
    static InstructionPtr mov_ind_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IND,IND); 
        }
    };
    
    static InstructionPtr mov_ind_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IND,DE); 
        }
    };
    
    static InstructionPtr mov_ind_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IND,DED); 
        }
    };
    
    static InstructionPtr mov_ind_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IND,IX); 
        }
    };
    
    static InstructionPtr mov_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IND,IXD); 
        }
    };
    
    static InstructionPtr mov_de_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DE,RG); 
        }
    };
    
    static InstructionPtr mov_de_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DE,RGD); 
        }
    };
    
    static InstructionPtr mov_de_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DE,IN); 
        }
    };
    
    static InstructionPtr mov_de_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DE,IND); 
        }
    };
    
    static InstructionPtr mov_de_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DE,DE); 
        }
    };
    
    static InstructionPtr mov_de_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DE,DED); 
        }
    };
    
    static InstructionPtr mov_de_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DE,IX); 
        }
    };
    
    static InstructionPtr mov_de_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DE,IXD); 
        }
    };
    
    static InstructionPtr mov_ded_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DED,RG); 
        }
    };
    
    static InstructionPtr mov_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DED,RGD); 
        }
    };
    
    static InstructionPtr mov_ded_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DED,IN); 
        }
    };
    
    static InstructionPtr mov_ded_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DED,IND); 
        }
    };
    
    static InstructionPtr mov_ded_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DED,DE); 
        }
    };
    
    static InstructionPtr mov_ded_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DED,DED); 
        }
    };
    
    static InstructionPtr mov_ded_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DED,IX); 
        }
    };
    
    static InstructionPtr mov_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(DED,IXD); 
        }
    };
    
    static InstructionPtr mov_ix_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IX,RG); 
        }
    };
    
    static InstructionPtr mov_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IX,RGD); 
        }
    };
    
    static InstructionPtr mov_ix_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IX,IN); 
        }
    };
    
    static InstructionPtr mov_ix_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IX,IND); 
        }
    };
    
    static InstructionPtr mov_ix_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IX,DE); 
        }
    };
    
    static InstructionPtr mov_ix_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IX,DED); 
        }
    };
    
    static InstructionPtr mov_ix_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IX,IX); 
        }
    };
    
    static InstructionPtr mov_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IX,IXD); 
        }
    };
    
    static InstructionPtr mov_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IXD,RG); 
        }
    };
    
    static InstructionPtr mov_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IXD,RGD); 
        }
    };
    
    static InstructionPtr mov_ixd_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IXD,IN); 
        }
    };
    
    static InstructionPtr mov_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IXD,IND); 
        }
    };
    
    static InstructionPtr mov_ixd_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IXD,DE); 
        }
    };
    
    static InstructionPtr mov_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IXD,DED); 
        }
    };
    
    static InstructionPtr mov_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IXD,IX); 
        }
    };
    
    static InstructionPtr mov_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	MOV_M(IXD,IXD); 
        }
    };
    
    
    static InstructionPtr cmp_rg_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_R(RG,RG); 
        }
    };
    
    static InstructionPtr cmp_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RG,RGD); 
        }
    };
    
    static InstructionPtr cmp_rg_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RG,IN); 
        }
    };
    
    static InstructionPtr cmp_rg_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RG,IND); 
        }
    };
    
    static InstructionPtr cmp_rg_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RG,DE); 
        }
    };
    
    static InstructionPtr cmp_rg_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RG,DED); 
        }
    };
    
    static InstructionPtr cmp_rg_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RG,IX); 
        }
    };
    
    static InstructionPtr cmp_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RG,IXD); 
        }
    };
    
    static InstructionPtr cmp_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RGD,RG); 
        }
    };
    
    static InstructionPtr cmp_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RGD,RGD); 
        }
    };
    
    static InstructionPtr cmp_rgd_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RGD,IN); 
        }
    };
    
    static InstructionPtr cmp_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RGD,IND); 
        }
    };
    
    static InstructionPtr cmp_rgd_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RGD,DE); 
        }
    };
    
    static InstructionPtr cmp_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RGD,DED); 
        }
    };
    
    static InstructionPtr cmp_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RGD,IX); 
        }
    };
    
    static InstructionPtr cmp_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(RGD,IXD); 
        }
    };
    
    static InstructionPtr cmp_in_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IN,RG); 
        }
    };
    
    static InstructionPtr cmp_in_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IN,RGD); 
        }
    };
    
    static InstructionPtr cmp_in_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IN,IN); 
        }
    };
    
    static InstructionPtr cmp_in_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IN,IND); 
        }
    };
    
    static InstructionPtr cmp_in_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IN,DE); 
        }
    };
    
    static InstructionPtr cmp_in_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IN,DED); 
        }
    };
    
    static InstructionPtr cmp_in_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IN,IX); 
        }
    };
    
    static InstructionPtr cmp_in_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IN,IXD); 
        }
    };
    
    static InstructionPtr cmp_ind_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IND,RG); 
        }
    };
    
    static InstructionPtr cmp_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IND,RGD); 
        }
    };
    
    static InstructionPtr cmp_ind_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IND,IN); 
        }
    };
    
    static InstructionPtr cmp_ind_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IND,IND); 
        }
    };
    
    static InstructionPtr cmp_ind_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IND,DE); 
        }
    };
    
    static InstructionPtr cmp_ind_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IND,DED); 
        }
    };
    
    static InstructionPtr cmp_ind_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IND,IX); 
        }
    };
    
    static InstructionPtr cmp_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IND,IXD); 
        }
    };
    
    static InstructionPtr cmp_de_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DE,RG); 
        }
    };
    
    static InstructionPtr cmp_de_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DE,RGD); 
        }
    };
    
    static InstructionPtr cmp_de_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DE,IN); 
        }
    };
    
    static InstructionPtr cmp_de_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DE,IND); 
        }
    };
    
    static InstructionPtr cmp_de_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DE,DE); 
        }
    };
    
    static InstructionPtr cmp_de_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DE,DED); 
        }
    };
    
    static InstructionPtr cmp_de_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DE,IX); 
        }
    };
    
    static InstructionPtr cmp_de_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DE,IXD); 
        }
    };
    
    static InstructionPtr cmp_ded_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DED,RG); 
        }
    };
    
    static InstructionPtr cmp_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DED,RGD); 
        }
    };
    
    static InstructionPtr cmp_ded_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DED,IN); 
        }
    };
    
    static InstructionPtr cmp_ded_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DED,IND); 
        }
    };
    
    static InstructionPtr cmp_ded_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DED,DE); 
        }
    };
    
    static InstructionPtr cmp_ded_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DED,DED); 
        }
    };
    
    static InstructionPtr cmp_ded_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DED,IX); 
        }
    };
    
    static InstructionPtr cmp_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(DED,IXD); 
        }
    };
    
    static InstructionPtr cmp_ix_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IX,RG); 
        }
    };
    
    static InstructionPtr cmp_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IX,RGD); 
        }
    };
    
    static InstructionPtr cmp_ix_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IX,IN); 
        }
    };
    
    static InstructionPtr cmp_ix_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IX,IND); 
        }
    };
    
    static InstructionPtr cmp_ix_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IX,DE); 
        }
    };
    
    static InstructionPtr cmp_ix_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IX,DED); 
        }
    };
    
    static InstructionPtr cmp_ix_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IX,IX); 
        }
    };
    
    static InstructionPtr cmp_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IX,IXD); 
        }
    };
    
    static InstructionPtr cmp_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IXD,RG); 
        }
    };
    
    static InstructionPtr cmp_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IXD,RGD); 
        }
    };
    
    static InstructionPtr cmp_ixd_in = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IXD,IN); 
        }
    };
    
    static InstructionPtr cmp_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IXD,IND); 
        }
    };
    
    static InstructionPtr cmp_ixd_de = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IXD,DE); 
        }
    };
    
    static InstructionPtr cmp_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IXD,DED); 
        }
    };
    
    static InstructionPtr cmp_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IXD,IX); 
        }
    };
    
    static InstructionPtr cmp_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ {
        public void handler() {
            throw new UnsupportedOperationException("Not Supported!");
    /*TODO*///	CMP_M(IXD,IXD); 
        }
    };
    
    
    static InstructionPtr bit_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_R(RG,RG); */ 
        }
    };
    static InstructionPtr bit_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(RG,RGD); */ 
        }
    };
    static InstructionPtr bit_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(RG,IN); */ 
        }
    };
    static InstructionPtr bit_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(RG,IND); */ 
        }
    };
    static InstructionPtr bit_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(RG,DE); */ 
        }
    };
    static InstructionPtr bit_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(RG,DED); */ 
        }
    };
    static InstructionPtr bit_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(RG,IX); */ 
        }
    };
    static InstructionPtr bit_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(RG,IXD); */ 
        }
    };
    static InstructionPtr bit_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(RGD,RG); */ 
        }
    };
    static InstructionPtr bit_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr bit_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(RGD,IN); */ 
        }
    };
    static InstructionPtr bit_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(RGD,IND); */ 
        }
    };
    static InstructionPtr bit_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(RGD,DE); */ 
        }
    };
    static InstructionPtr bit_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(RGD,DED); */ 
        }
    };
    static InstructionPtr bit_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(RGD,IX); */ 
        }
    };
    static InstructionPtr bit_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr bit_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(IN,RG); */ 
        }
    };
    static InstructionPtr bit_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IN,RGD); */ 
        }
    };
    static InstructionPtr bit_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(IN,IN); */ 
        }
    };
    static InstructionPtr bit_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IN,IND); */ 
        }
    };
    static InstructionPtr bit_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(IN,DE); */ 
        }
    };
    static InstructionPtr bit_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IN,DED); */ 
        }
    };
    static InstructionPtr bit_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(IN,IX); */ 
        }
    };
    static InstructionPtr bit_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IN,IXD); */ 
        }
    };
    static InstructionPtr bit_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IND,RG); */ 
        }
    };
    static InstructionPtr bit_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(IND,RGD); */ 
        }
    };
    static InstructionPtr bit_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IND,IN); */ 
        }
    };
    static InstructionPtr bit_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(IND,IND); */ 
        }
    };
    static InstructionPtr bit_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IND,DE); */ 
        }
    };
    static InstructionPtr bit_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(IND,DED); */ 
        }
    };
    static InstructionPtr bit_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IND,IX); */ 
        }
    };
    static InstructionPtr bit_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(IND,IXD); */ 
        }
    };
    static InstructionPtr bit_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(DE,RG); */ 
        }
    };
    static InstructionPtr bit_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(DE,RGD); */ 
        }
    };
    static InstructionPtr bit_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(DE,IN); */ 
        }
    };
    static InstructionPtr bit_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(DE,IND); */ 
        }
    };
    static InstructionPtr bit_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(DE,DE); */ 
        }
    };
    static InstructionPtr bit_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(DE,DED); */ 
        }
    };
    static InstructionPtr bit_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(DE,IX); */ 
        }
    };
    static InstructionPtr bit_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(DE,IXD); */ 
        }
    };
    static InstructionPtr bit_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(DED,RG); */ 
        }
    };
    static InstructionPtr bit_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(DED,RGD); */ 
        }
    };
    static InstructionPtr bit_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(DED,IN); */ 
        }
    };
    static InstructionPtr bit_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(DED,IND); */ 
        }
    };
    static InstructionPtr bit_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(DED,DE); */ 
        }
    };
    static InstructionPtr bit_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(DED,DED); */ 
        }
    };
    static InstructionPtr bit_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(DED,IX); */ 
        }
    };
    static InstructionPtr bit_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(DED,IXD); */ 
        }
    };
    static InstructionPtr bit_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(IX,RG); */ 
        }
    };
    static InstructionPtr bit_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IX,RGD); */ 
        }
    };
    static InstructionPtr bit_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(IX,IN); */ 
        }
    };
    static InstructionPtr bit_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IX,IND); */ 
        }
    };
    static InstructionPtr bit_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(IX,DE); */ 
        }
    };
    static InstructionPtr bit_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IX,DED); */ 
        }
    };
    static InstructionPtr bit_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIT_M(IX,IX); */ 
        }
    };
    static InstructionPtr bit_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IX,IXD); */ 
        }
    };
    static InstructionPtr bit_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IXD,RG); */ 
        }
    };
    static InstructionPtr bit_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr bit_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IXD,IN); */ 
        }
    };
    static InstructionPtr bit_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(IXD,IND); */ 
        }
    };
    static InstructionPtr bit_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IXD,DE); */ 
        }
    };
    static InstructionPtr bit_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(IXD,DED); */ 
        }
    };
    static InstructionPtr bit_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIT_M(IXD,IX); */ 
        }
    };
    static InstructionPtr bit_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIT_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr bic_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_R(RG,RG); */ 
        }
    };
    static InstructionPtr bic_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(RG,RGD); */ 
        }
    };
    static InstructionPtr bic_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(RG,IN); */ 
        }
    };
    static InstructionPtr bic_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(RG,IND); */ 
        }
    };
    static InstructionPtr bic_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(RG,DE); */ 
        }
    };
    static InstructionPtr bic_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(RG,DED); */ 
        }
    };
    static InstructionPtr bic_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(RG,IX); */ 
        }
    };
    static InstructionPtr bic_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(RG,IXD); */ 
        }
    };
    static InstructionPtr bic_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_X(RGD,RG); */ 
        }
    };
    static InstructionPtr bic_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr bic_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(RGD,IN); */ 
        }
    };
    static InstructionPtr bic_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(RGD,IND); */ 
        }
    };
    static InstructionPtr bic_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(RGD,DE); */ 
        }
    };
    static InstructionPtr bic_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(RGD,DED); */ 
        }
    };
    static InstructionPtr bic_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(RGD,IX); */ 
        }
    };
    static InstructionPtr bic_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr bic_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_X(IN,RG); */ 
        }
    };
    static InstructionPtr bic_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IN,RGD); */ 
        }
    };
    static InstructionPtr bic_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(IN,IN); */ 
        }
    };
    static InstructionPtr bic_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IN,IND); */ 
        }
    };
    static InstructionPtr bic_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(IN,DE); */ 
        }
    };
    static InstructionPtr bic_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IN,DED); */ 
        }
    };
    static InstructionPtr bic_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(IN,IX); */ 
        }
    };
    static InstructionPtr bic_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IN,IXD); */ 
        }
    };
    static InstructionPtr bic_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_X(IND,RG); */ 
        }
    };
    static InstructionPtr bic_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(IND,RGD); */ 
        }
    };
    static InstructionPtr bic_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IND,IN); */ 
        }
    };
    static InstructionPtr bic_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(IND,IND); */ 
        }
    };
    static InstructionPtr bic_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IND,DE); */ 
        }
    };
    static InstructionPtr bic_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(IND,DED); */ 
        }
    };
    static InstructionPtr bic_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IND,IX); */ 
        }
    };
    static InstructionPtr bic_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(IND,IXD); */ 
        }
    };
    static InstructionPtr bic_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_X(DE,RG); */ 
        }
    };
    static InstructionPtr bic_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(DE,RGD); */ 
        }
    };
    static InstructionPtr bic_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(DE,IN); */ 
        }
    };
    static InstructionPtr bic_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(DE,IND); */ 
        }
    };
    static InstructionPtr bic_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(DE,DE); */ 
        }
    };
    static InstructionPtr bic_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(DE,DED); */ 
        }
    };
    static InstructionPtr bic_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(DE,IX); */ 
        }
    };
    static InstructionPtr bic_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(DE,IXD); */ 
        }
    };
    static InstructionPtr bic_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_X(DED,RG); */ 
        }
    };
    static InstructionPtr bic_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(DED,RGD); */ 
        }
    };
    static InstructionPtr bic_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(DED,IN); */ 
        }
    };
    static InstructionPtr bic_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(DED,IND); */ 
        }
    };
    static InstructionPtr bic_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(DED,DE); */ 
        }
    };
    static InstructionPtr bic_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(DED,DED); */ 
        }
    };
    static InstructionPtr bic_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(DED,IX); */ 
        }
    };
    static InstructionPtr bic_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(DED,IXD); */ 
        }
    };
    static InstructionPtr bic_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_X(IX,RG); */ 
        }
    };
    static InstructionPtr bic_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IX,RGD); */ 
        }
    };
    static InstructionPtr bic_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(IX,IN); */ 
        }
    };
    static InstructionPtr bic_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IX,IND); */ 
        }
    };
    static InstructionPtr bic_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(IX,DE); */ 
        }
    };
    static InstructionPtr bic_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IX,DED); */ 
        }
    };
    static InstructionPtr bic_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIC_M(IX,IX); */ 
        }
    };
    static InstructionPtr bic_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IX,IXD); */ 
        }
    };
    static InstructionPtr bic_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_X(IXD,RG); */ 
        }
    };
    static InstructionPtr bic_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr bic_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IXD,IN); */ 
        }
    };
    static InstructionPtr bic_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(IXD,IND); */ 
        }
    };
    static InstructionPtr bic_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IXD,DE); */ 
        }
    };
    static InstructionPtr bic_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(IXD,DED); */ 
        }
    };
    static InstructionPtr bic_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIC_M(IXD,IX); */ 
        }
    };
    static InstructionPtr bic_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIC_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr bis_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_R(RG,RG); */ 
        }
    };
    static InstructionPtr bis_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(RG,RGD); */ 
        }
    };
    static InstructionPtr bis_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(RG,IN); */ 
        }
    };
    static InstructionPtr bis_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(RG,IND); */ 
        }
    };
    static InstructionPtr bis_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(RG,DE); */ 
        }
    };
    static InstructionPtr bis_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(RG,DED); */ 
        }
    };
    static InstructionPtr bis_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(RG,IX); */ 
        }
    };
    static InstructionPtr bis_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(RG,IXD); */ 
        }
    };
    static InstructionPtr bis_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_X(RGD,RG); */ 
        }
    };
    static InstructionPtr bis_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr bis_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(RGD,IN); */ 
        }
    };
    static InstructionPtr bis_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(RGD,IND); */ 
        }
    };
    static InstructionPtr bis_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(RGD,DE); */ 
        }
    };
    static InstructionPtr bis_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(RGD,DED); */ 
        }
    };
    static InstructionPtr bis_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(RGD,IX); */ 
        }
    };
    static InstructionPtr bis_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr bis_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_X(IN,RG); */ 
        }
    };
    static InstructionPtr bis_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IN,RGD); */ 
        }
    };
    static InstructionPtr bis_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(IN,IN); */ 
        }
    };
    static InstructionPtr bis_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IN,IND); */ 
        }
    };
    static InstructionPtr bis_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(IN,DE); */ 
        }
    };
    static InstructionPtr bis_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IN,DED); */ 
        }
    };
    static InstructionPtr bis_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(IN,IX); */ 
        }
    };
    static InstructionPtr bis_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IN,IXD); */ 
        }
    };
    static InstructionPtr bis_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_X(IND,RG); */ 
        }
    };
    static InstructionPtr bis_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(IND,RGD); */ 
        }
    };
    static InstructionPtr bis_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IND,IN); */ 
        }
    };
    static InstructionPtr bis_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(IND,IND); */ 
        }
    };
    static InstructionPtr bis_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IND,DE); */ 
        }
    };
    static InstructionPtr bis_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(IND,DED); */ 
        }
    };
    static InstructionPtr bis_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IND,IX); */ 
        }
    };
    static InstructionPtr bis_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(IND,IXD); */ 
        }
    };
    static InstructionPtr bis_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_X(DE,RG); */ 
        }
    };
    static InstructionPtr bis_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(DE,RGD); */ 
        }
    };
    static InstructionPtr bis_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(DE,IN); */ 
        }
    };
    static InstructionPtr bis_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(DE,IND); */ 
        }
    };
    static InstructionPtr bis_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(DE,DE); */ 
        }
    };
    static InstructionPtr bis_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(DE,DED); */ 
        }
    };
    static InstructionPtr bis_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(DE,IX); */ 
        }
    };
    static InstructionPtr bis_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(DE,IXD); */ 
        }
    };
    static InstructionPtr bis_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_X(DED,RG); */ 
        }
    };
    static InstructionPtr bis_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(DED,RGD); */ 
        }
    };
    static InstructionPtr bis_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(DED,IN); */ 
        }
    };
    static InstructionPtr bis_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(DED,IND); */ 
        }
    };
    static InstructionPtr bis_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(DED,DE); */ 
        }
    };
    static InstructionPtr bis_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(DED,DED); */ 
        }
    };
    static InstructionPtr bis_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(DED,IX); */ 
        }
    };
    static InstructionPtr bis_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(DED,IXD); */ 
        }
    };
    static InstructionPtr bis_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_X(IX,RG); */ 
        }
    };
    static InstructionPtr bis_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IX,RGD); */ 
        }
    };
    static InstructionPtr bis_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(IX,IN); */ 
        }
    };
    static InstructionPtr bis_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IX,IND); */ 
        }
    };
    static InstructionPtr bis_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(IX,DE); */ 
        }
    };
    static InstructionPtr bis_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IX,DED); */ 
        }
    };
    static InstructionPtr bis_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BIS_M(IX,IX); */ 
        }
    };
    static InstructionPtr bis_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IX,IXD); */ 
        }
    };
    static InstructionPtr bis_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_X(IXD,RG); */ 
        }
    };
    static InstructionPtr bis_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr bis_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IXD,IN); */ 
        }
    };
    static InstructionPtr bis_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(IXD,IND); */ 
        }
    };
    static InstructionPtr bis_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IXD,DE); */ 
        }
    };
    static InstructionPtr bis_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(IXD,DED); */ 
        }
    };
    static InstructionPtr bis_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BIS_M(IXD,IX); */ 
        }
    };
    static InstructionPtr bis_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BIS_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr add_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_R(RG,RG); */ 
        }
    };
    static InstructionPtr add_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(RG,RGD); */ 
        }
    };
    static InstructionPtr add_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(RG,IN); */ 
        }
    };
    static InstructionPtr add_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(RG,IND); */ 
        }
    };
    static InstructionPtr add_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(RG,DE); */ 
        }
    };
    static InstructionPtr add_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(RG,DED); */ 
        }
    };
    static InstructionPtr add_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(RG,IX); */ 
        }
    };
    static InstructionPtr add_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(RG,IXD); */ 
        }
    };
    static InstructionPtr add_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_X(RGD,RG); */ 
        }
    };
    static InstructionPtr add_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr add_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(RGD,IN); */ 
        }
    };
    static InstructionPtr add_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(RGD,IND); */ 
        }
    };
    static InstructionPtr add_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(RGD,DE); */ 
        }
    };
    static InstructionPtr add_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(RGD,DED); */ 
        }
    };
    static InstructionPtr add_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(RGD,IX); */ 
        }
    };
    static InstructionPtr add_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr add_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_X(IN,RG); */ 
        }
    };
    static InstructionPtr add_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IN,RGD); */ 
        }
    };
    static InstructionPtr add_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(IN,IN); */ 
        }
    };
    static InstructionPtr add_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IN,IND); */ 
        }
    };
    static InstructionPtr add_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(IN,DE); */ 
        }
    };
    static InstructionPtr add_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IN,DED); */ 
        }
    };
    static InstructionPtr add_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(IN,IX); */ 
        }
    };
    static InstructionPtr add_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IN,IXD); */ 
        }
    };
    static InstructionPtr add_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_X(IND,RG); */ 
        }
    };
    static InstructionPtr add_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(IND,RGD); */ 
        }
    };
    static InstructionPtr add_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IND,IN); */ 
        }
    };
    static InstructionPtr add_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(IND,IND); */ 
        }
    };
    static InstructionPtr add_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IND,DE); */ 
        }
    };
    static InstructionPtr add_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(IND,DED); */ 
        }
    };
    static InstructionPtr add_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IND,IX); */ 
        }
    };
    static InstructionPtr add_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(IND,IXD); */ 
        }
    };
    static InstructionPtr add_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_X(DE,RG); */ 
        }
    };
    static InstructionPtr add_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(DE,RGD); */ 
        }
    };
    static InstructionPtr add_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(DE,IN); */ 
        }
    };
    static InstructionPtr add_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(DE,IND); */ 
        }
    };
    static InstructionPtr add_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(DE,DE); */ 
        }
    };
    static InstructionPtr add_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(DE,DED); */ 
        }
    };
    static InstructionPtr add_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(DE,IX); */ 
        }
    };
    static InstructionPtr add_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(DE,IXD); */ 
        }
    };
    static InstructionPtr add_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_X(DED,RG); */ 
        }
    };
    static InstructionPtr add_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(DED,RGD); */ 
        }
    };
    static InstructionPtr add_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(DED,IN); */ 
        }
    };
    static InstructionPtr add_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(DED,IND); */ 
        }
    };
    static InstructionPtr add_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(DED,DE); */ 
        }
    };
    static InstructionPtr add_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(DED,DED); */ 
        }
    };
    static InstructionPtr add_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(DED,IX); */ 
        }
    };
    static InstructionPtr add_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(DED,IXD); */ 
        }
    };
    static InstructionPtr add_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_X(IX,RG); */ 
        }
    };
    static InstructionPtr add_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IX,RGD); */ 
        }
    };
    static InstructionPtr add_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(IX,IN); */ 
        }
    };
    static InstructionPtr add_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IX,IND); */ 
        }
    };
    static InstructionPtr add_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(IX,DE); */ 
        }
    };
    static InstructionPtr add_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IX,DED); */ 
        }
    };
    static InstructionPtr add_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { ADD_M(IX,IX); */ 
        }
    };
    static InstructionPtr add_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IX,IXD); */ 
        }
    };
    static InstructionPtr add_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_X(IXD,RG); */ 
        }
    };
    static InstructionPtr add_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr add_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IXD,IN); */ 
        }
    };
    static InstructionPtr add_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(IXD,IND); */ 
        }
    };
    static InstructionPtr add_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IXD,DE); */ 
        }
    };
    static InstructionPtr add_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(IXD,DED); */ 
        }
    };
    static InstructionPtr add_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { ADD_M(IXD,IX); */ 
        }
    };
    static InstructionPtr add_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { ADD_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr xor_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*        { XOR_R(RG); */ 
        }
    };
    static InstructionPtr xor_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { XOR_M(RGD); */ 
        }
    };
    static InstructionPtr xor_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*        { XOR_M(IN); */ 
        }
    };
    static InstructionPtr xor_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { XOR_M(IND); */ 
        }
    };
    static InstructionPtr xor_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*        { XOR_M(DE); */ 
        }
    };
    static InstructionPtr xor_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { XOR_M(DED); */ 
        }
    };
    static InstructionPtr xor_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*        { XOR_M(IX); */ 
        }
    };
    static InstructionPtr xor_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { XOR_M(IXD); */ 
        }
    };
    
    static InstructionPtr sob = new InstructionPtr() /* Opcode 0x?? */ { 
        public void handler() { 
            throw new UnsupportedOperationException("Not Supported!");
    
/*TODO*///    	int sreg, source;
/*TODO*///    
/*TODO*///    	GET_SREG; source = REGD(sreg); */
/*TODO*///    	source -= 1;
/*TODO*///    	REGW(sreg) = source;
/*TODO*///    	if (source != 0)
/*TODO*///    		PC -= 2 * (t11.op & 0x3f); */
     
        }
    };
    
    static InstructionPtr bpl = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*           { BR(!GET_N); */ 
        }
    };
    static InstructionPtr bmi = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*           { BR( GET_N); */ 
        }
    };
    static InstructionPtr bhi = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*           { BR(!GET_C && !GET_Z); */ 
        }
    };
    static InstructionPtr blos = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*          { BR( GET_C ||  GET_Z); */ 
        }
    };
    static InstructionPtr bvc = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*           { BR(!GET_V); */ 
        }
    };
    static InstructionPtr bvs = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*           { BR( GET_V); */ 
        }
    };
    static InstructionPtr bcc = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*           { BR(!GET_C); */ 
        }
    };
    static InstructionPtr bcs = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*           { BR( GET_C); */ 
        }
    };
    
    static InstructionPtr emt = new InstructionPtr() /* Opcode 0x?? */ { 
        public void handler() { throw new UnsupportedOperationException("Not Supported!");
    
/*TODO*///            PUSH(PSW);
/*TODO*///            PUSH(PC); 
/*TODO*///            PC = RWORD(0x18); 
/*TODO*///            PSW = RWORD(0x1a); 
/*TODO*///            t11_check_irqs(); 
     
        }
    };
    
    static InstructionPtr trap = new InstructionPtr() /* Opcode 0x?? */ { 
        public void handler() { 
            throw new UnsupportedOperationException("Not Supported!");
/*TODO*///            PUSH(PSW);
/*TODO*///            PUSH(PC);
/*TODO*///            PC = RWORD(0x1c);
/*TODO*///            PSW = RWORD(0x1e);
/*TODO*///            t11_check_irqs();
     
        }
    };
    
    static InstructionPtr clrb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { CLRB_R(RG); */ 
        }
    };
    static InstructionPtr clrb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { CLRB_M(RGD); */ 
        }
    };
    static InstructionPtr clrb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { CLRB_M(IN); */ 
        }
    };
    static InstructionPtr clrb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { CLRB_M(IND); */ 
        }
    };
    static InstructionPtr clrb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { CLRB_M(DE); */ 
        }
    };
    static InstructionPtr clrb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { CLRB_M(DED); */ 
        }
    };
    static InstructionPtr clrb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { CLRB_M(IX); */ 
        }
    };
    static InstructionPtr clrb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { CLRB_M(IXD); */ 
        }
    };
    
    static InstructionPtr comb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { COMB_R(RG); */ 
        }
    };
    static InstructionPtr comb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { COMB_M(RGD); */ 
        }
    };
    static InstructionPtr comb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { COMB_M(IN); */ 
        }
    };
    static InstructionPtr comb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { COMB_M(IND); */ 
        }
    };
    static InstructionPtr comb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { COMB_M(DE); */ 
        }
    };
    static InstructionPtr comb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { COMB_M(DED); */ 
        }
    };
    static InstructionPtr comb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { COMB_M(IX); */ 
        }
    };
    static InstructionPtr comb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { COMB_M(IXD); */ 
        }
    };
    
    static InstructionPtr incb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { INCB_R(RG); */ 
        }
    };
    static InstructionPtr incb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { INCB_M(RGD); */ 
        }
    };
    static InstructionPtr incb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { INCB_M(IN); */ 
        }
    };
    static InstructionPtr incb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { INCB_M(IND); */ 
        }
    };
    static InstructionPtr incb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { INCB_M(DE); */ 
        }
    };
    static InstructionPtr incb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { INCB_M(DED); */ 
        }
    };
    static InstructionPtr incb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { INCB_M(IX); */ 
        }
    };
    static InstructionPtr incb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { INCB_M(IXD); */ 
        }
    };
    
    static InstructionPtr decb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { DECB_R(RG); */ 
        }
    };
    static InstructionPtr decb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { DECB_M(RGD); */ 
        }
    };
    static InstructionPtr decb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { DECB_M(IN); */ 
        }
    };
    static InstructionPtr decb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { DECB_M(IND); */ 
        }
    };
    static InstructionPtr decb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { DECB_M(DE); */ 
        }
    };
    static InstructionPtr decb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { DECB_M(DED); */ 
        }
    };
    static InstructionPtr decb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { DECB_M(IX); */ 
        }
    };
    static InstructionPtr decb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { DECB_M(IXD); */ 
        }
    };
    
    static InstructionPtr negb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { NEGB_R(RG); */ 
        }
    };
    static InstructionPtr negb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { NEGB_M(RGD); */ 
        }
    };
    static InstructionPtr negb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { NEGB_M(IN); */ 
        }
    };
    static InstructionPtr negb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { NEGB_M(IND); */ 
        }
    };
    static InstructionPtr negb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { NEGB_M(DE); */ 
        }
    };
    static InstructionPtr negb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { NEGB_M(DED); */ 
        }
    };
    static InstructionPtr negb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { NEGB_M(IX); */ 
        }
    };
    static InstructionPtr negb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { NEGB_M(IXD); */ 
        }
    };
    
    static InstructionPtr adcb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ADCB_R(RG); */ 
        }
    };
    static InstructionPtr adcb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ADCB_M(RGD); */ 
        }
    };
    static InstructionPtr adcb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ADCB_M(IN); */ 
        }
    };
    static InstructionPtr adcb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ADCB_M(IND); */ 
        }
    };
    static InstructionPtr adcb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ADCB_M(DE); */ 
        }
    };
    static InstructionPtr adcb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ADCB_M(DED); */ 
        }
    };
    static InstructionPtr adcb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ADCB_M(IX); */ 
        }
    };
    static InstructionPtr adcb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ADCB_M(IXD); */ 
        }
    };
    
    static InstructionPtr sbcb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { SBCB_R(RG); */ 
        }
    };
    static InstructionPtr sbcb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { SBCB_M(RGD); */ 
        }
    };
    static InstructionPtr sbcb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { SBCB_M(IN); */ 
        }
    };
    static InstructionPtr sbcb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { SBCB_M(IND); */ 
        }
    };
    static InstructionPtr sbcb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { SBCB_M(DE); */ 
        }
    };
    static InstructionPtr sbcb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { SBCB_M(DED); */ 
        }
    };
    static InstructionPtr sbcb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { SBCB_M(IX); */ 
        }
    };
    static InstructionPtr sbcb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { SBCB_M(IXD); */ 
        }
    };
    
    static InstructionPtr tstb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { TSTB_R(RG); */ 
        }
    };
    static InstructionPtr tstb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { TSTB_M(RGD); */ 
        }
    };
    static InstructionPtr tstb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { TSTB_M(IN); */ 
        }
    };
    static InstructionPtr tstb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { TSTB_M(IND); */ 
        }
    };
    static InstructionPtr tstb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { TSTB_M(DE); */ 
        }
    };
    static InstructionPtr tstb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { TSTB_M(DED); */ 
        }
    };
    static InstructionPtr tstb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { TSTB_M(IX); */ 
        }
    };
    static InstructionPtr tstb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { TSTB_M(IXD); */ 
        }
    };
    
    static InstructionPtr rorb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { RORB_R(RG); */ 
        }
    };
    static InstructionPtr rorb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { RORB_M(RGD); */ 
        }
    };
    static InstructionPtr rorb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { RORB_M(IN); */ 
        }
    };
    static InstructionPtr rorb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { RORB_M(IND); */ 
        }
    };
    static InstructionPtr rorb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { RORB_M(DE); */ 
        }
    };
    static InstructionPtr rorb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { RORB_M(DED); */ 
        }
    };
    static InstructionPtr rorb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { RORB_M(IX); */ 
        }
    };
    static InstructionPtr rorb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { RORB_M(IXD); */ 
        }
    };
    
    static InstructionPtr rolb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ROLB_R(RG); */ 
        }
    };
    static InstructionPtr rolb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ROLB_M(RGD); */ 
        }
    };
    static InstructionPtr rolb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ROLB_M(IN); */ 
        }
    };
    static InstructionPtr rolb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ROLB_M(IND); */ 
        }
    };
    static InstructionPtr rolb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ROLB_M(DE); */ 
        }
    };
    static InstructionPtr rolb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ROLB_M(DED); */ 
        }
    };
    static InstructionPtr rolb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ROLB_M(IX); */ 
        }
    };
    static InstructionPtr rolb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ROLB_M(IXD); */ 
        }
    };
    
    static InstructionPtr asrb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ASRB_R(RG); */ 
        }
    };
    static InstructionPtr asrb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ASRB_M(RGD); */ 
        }
    };
    static InstructionPtr asrb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ASRB_M(IN); */ 
        }
    };
    static InstructionPtr asrb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ASRB_M(IND); */ 
        }
    };
    static InstructionPtr asrb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ASRB_M(DE); */ 
        }
    };
    static InstructionPtr asrb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ASRB_M(DED); */ 
        }
    };
    static InstructionPtr asrb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ASRB_M(IX); */ 
        }
    };
    static InstructionPtr asrb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ASRB_M(IXD); */ 
        }
    };
    
    static InstructionPtr aslb_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ASLB_R(RG); */ 
        }
    };
    static InstructionPtr aslb_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ASLB_M(RGD); */ 
        }
    };
    static InstructionPtr aslb_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ASLB_M(IN); */ 
        }
    };
    static InstructionPtr aslb_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ASLB_M(IND); */ 
        }
    };
    static InstructionPtr aslb_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ASLB_M(DE); */ 
        }
    };
    static InstructionPtr aslb_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ASLB_M(DED); */ 
        }
    };
    static InstructionPtr aslb_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { ASLB_M(IX); */ 
        }
    };
    static InstructionPtr aslb_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { ASLB_M(IXD); */ 
        }
    };
    
    static InstructionPtr mtps_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { MTPS_R(RG); */ 
        }
    };
    static InstructionPtr mtps_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { MTPS_M(RGD); */ 
        }
    };
    static InstructionPtr mtps_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { MTPS_M(IN); */ 
        }
    };
    static InstructionPtr mtps_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { MTPS_M(IND); */ 
        }
    };
    static InstructionPtr mtps_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { MTPS_M(DE); */ 
        }
    };
    static InstructionPtr mtps_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { MTPS_M(DED); */ 
        }
    };
    static InstructionPtr mtps_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { MTPS_M(IX); */ 
        }
    };
    static InstructionPtr mtps_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { MTPS_M(IXD); */ 
        }
    };
    
    static InstructionPtr mfps_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { MFPS_R(RG); */ 
        }
    };
    static InstructionPtr mfps_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { MFPS_M(RGD); */ 
        }
    };
    static InstructionPtr mfps_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { MFPS_M(IN); */ 
        }
    };
    static InstructionPtr mfps_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { MFPS_M(IND); */ 
        }
    };
    static InstructionPtr mfps_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { MFPS_M(DE); */ 
        }
    };
    static InstructionPtr mfps_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { MFPS_M(DED); */ 
        }
    };
    static InstructionPtr mfps_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*       { MFPS_M(IX); */ 
        }
    };
    static InstructionPtr mfps_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*      { MFPS_M(IXD); */ 
        }
    };
    
    static InstructionPtr movb_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_R(RG,RG); */ 
        }
    };
    static InstructionPtr movb_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(RG,RGD); */ 
        }
    };
    static InstructionPtr movb_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(RG,IN); */ 
        }
    };
    static InstructionPtr movb_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(RG,IND); */ 
        }
    };
    static InstructionPtr movb_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(RG,DE); */ 
        }
    };
    static InstructionPtr movb_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(RG,DED); */ 
        }
    };
    static InstructionPtr movb_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(RG,IX); */ 
        }
    };
    static InstructionPtr movb_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(RG,IXD); */ 
        }
    };
    static InstructionPtr movb_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_X(RGD,RG); */ 
        }
    };
    static InstructionPtr movb_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr movb_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(RGD,IN); */ 
        }
    };
    static InstructionPtr movb_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(RGD,IND); */ 
        }
    };
    static InstructionPtr movb_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(RGD,DE); */ 
        }
    };
    static InstructionPtr movb_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(RGD,DED); */ 
        }
    };
    static InstructionPtr movb_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(RGD,IX); */ 
        }
    };
    static InstructionPtr movb_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr movb_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_X(IN,RG); */ 
        }
    };
    static InstructionPtr movb_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IN,RGD); */ 
        }
    };
    static InstructionPtr movb_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(IN,IN); */ 
        }
    };
    static InstructionPtr movb_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IN,IND); */ 
        }
    };
    static InstructionPtr movb_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(IN,DE); */ 
        }
    };
    static InstructionPtr movb_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IN,DED); */ 
        }
    };
    static InstructionPtr movb_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(IN,IX); */ 
        }
    };
    static InstructionPtr movb_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IN,IXD); */ 
        }
    };
    static InstructionPtr movb_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_X(IND,RG); */ 
        }
    };
    static InstructionPtr movb_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(IND,RGD); */ 
        }
    };
    static InstructionPtr movb_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IND,IN); */ 
        }
    };
    static InstructionPtr movb_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(IND,IND); */ 
        }
    };
    static InstructionPtr movb_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IND,DE); */ 
        }
    };
    static InstructionPtr movb_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(IND,DED); */ 
        }
    };
    static InstructionPtr movb_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IND,IX); */ 
        }
    };
    static InstructionPtr movb_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(IND,IXD); */ 
        }
    };
    static InstructionPtr movb_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_X(DE,RG); */ 
        }
    };
    static InstructionPtr movb_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(DE,RGD); */ 
        }
    };
    static InstructionPtr movb_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(DE,IN); */ 
        }
    };
    static InstructionPtr movb_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(DE,IND); */ 
        }
    };
    static InstructionPtr movb_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(DE,DE); */ 
        }
    };
    static InstructionPtr movb_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(DE,DED); */ 
        }
    };
    static InstructionPtr movb_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(DE,IX); */ 
        }
    };
    static InstructionPtr movb_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(DE,IXD); */ 
        }
    };
    static InstructionPtr movb_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_X(DED,RG); */ 
        }
    };
    static InstructionPtr movb_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(DED,RGD); */ 
        }
    };
    static InstructionPtr movb_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(DED,IN); */ 
        }
    };
    static InstructionPtr movb_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(DED,IND); */ 
        }
    };
    static InstructionPtr movb_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(DED,DE); */ 
        }
    };
    static InstructionPtr movb_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(DED,DED); */ 
        }
    };
    static InstructionPtr movb_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(DED,IX); */ 
        }
    };
    static InstructionPtr movb_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(DED,IXD); */ 
        }
    };
    static InstructionPtr movb_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_X(IX,RG); */ 
        }
    };
    static InstructionPtr movb_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IX,RGD); */ 
        }
    };
    static InstructionPtr movb_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(IX,IN); */ 
        }
    };
    static InstructionPtr movb_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IX,IND); */ 
        }
    };
    static InstructionPtr movb_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(IX,DE); */ 
        }
    };
    static InstructionPtr movb_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IX,DED); */ 
        }
    };
    static InstructionPtr movb_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { MOVB_M(IX,IX); */ 
        }
    };
    static InstructionPtr movb_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IX,IXD); */ 
        }
    };
    static InstructionPtr movb_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_X(IXD,RG); */ 
        }
    };
    static InstructionPtr movb_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr movb_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IXD,IN); */ 
        }
    };
    static InstructionPtr movb_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(IXD,IND); */ 
        }
    };
    static InstructionPtr movb_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IXD,DE); */ 
        }
    };
    static InstructionPtr movb_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(IXD,DED); */ 
        }
    };
    static InstructionPtr movb_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { MOVB_M(IXD,IX); */ 
        }
    };
    static InstructionPtr movb_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { MOVB_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr cmpb_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_R(RG,RG); */ 
        }
    };
    static InstructionPtr cmpb_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(RG,RGD); */ 
        }
    };
    static InstructionPtr cmpb_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(RG,IN); */ 
        }
    };
    static InstructionPtr cmpb_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(RG,IND); */ 
        }
    };
    static InstructionPtr cmpb_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(RG,DE); */ 
        }
    };
    static InstructionPtr cmpb_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(RG,DED); */ 
        }
    };
    static InstructionPtr cmpb_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(RG,IX); */ 
        }
    };
    static InstructionPtr cmpb_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(RG,IXD); */ 
        }
    };
    static InstructionPtr cmpb_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(RGD,RG); */ 
        }
    };
    static InstructionPtr cmpb_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr cmpb_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(RGD,IN); */ 
        }
    };
    static InstructionPtr cmpb_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(RGD,IND); */ 
        }
    };
    static InstructionPtr cmpb_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(RGD,DE); */ 
        }
    };
    static InstructionPtr cmpb_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(RGD,DED); */ 
        }
    };
    static InstructionPtr cmpb_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(RGD,IX); */ 
        }
    };
    static InstructionPtr cmpb_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr cmpb_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(IN,RG); */ 
        }
    };
    static InstructionPtr cmpb_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IN,RGD); */ 
        }
    };
    static InstructionPtr cmpb_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(IN,IN); */ 
        }
    };
    static InstructionPtr cmpb_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IN,IND); */ 
        }
    };
    static InstructionPtr cmpb_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(IN,DE); */ 
        }
    };
    static InstructionPtr cmpb_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IN,DED); */ 
        }
    };
    static InstructionPtr cmpb_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(IN,IX); */ 
        }
    };
    static InstructionPtr cmpb_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IN,IXD); */ 
        }
    };
    static InstructionPtr cmpb_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IND,RG); */ 
        }
    };
    static InstructionPtr cmpb_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(IND,RGD); */ 
        }
    };
    static InstructionPtr cmpb_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IND,IN); */ 
        }
    };
    static InstructionPtr cmpb_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(IND,IND); */ 
        }
    };
    static InstructionPtr cmpb_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IND,DE); */ 
        }
    };
    static InstructionPtr cmpb_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(IND,DED); */ 
        }
    };
    static InstructionPtr cmpb_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IND,IX); */ 
        }
    };
    static InstructionPtr cmpb_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(IND,IXD); */ 
        }
    };
    static InstructionPtr cmpb_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(DE,RG); */ 
        }
    };
    static InstructionPtr cmpb_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(DE,RGD); */ 
        }
    };
    static InstructionPtr cmpb_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(DE,IN); */ 
        }
    };
    static InstructionPtr cmpb_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(DE,IND); */ 
        }
    };
    static InstructionPtr cmpb_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(DE,DE); */ 
        }
    };
    static InstructionPtr cmpb_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(DE,DED); */ 
        }
    };
    static InstructionPtr cmpb_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(DE,IX); */ 
        }
    };
    static InstructionPtr cmpb_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(DE,IXD); */ 
        }
    };
    static InstructionPtr cmpb_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(DED,RG); */ 
        }
    };
    static InstructionPtr cmpb_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(DED,RGD); */ 
        }
    };
    static InstructionPtr cmpb_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(DED,IN); */ 
        }
    };
    static InstructionPtr cmpb_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(DED,IND); */ 
        }
    };
    static InstructionPtr cmpb_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(DED,DE); */ 
        }
    };
    static InstructionPtr cmpb_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(DED,DED); */ 
        }
    };
    static InstructionPtr cmpb_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(DED,IX); */ 
        }
    };
    static InstructionPtr cmpb_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(DED,IXD); */ 
        }
    };
    static InstructionPtr cmpb_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(IX,RG); */ 
        }
    };
    static InstructionPtr cmpb_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IX,RGD); */ 
        }
    };
    static InstructionPtr cmpb_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(IX,IN); */ 
        }
    };
    static InstructionPtr cmpb_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IX,IND); */ 
        }
    };
    static InstructionPtr cmpb_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(IX,DE); */ 
        }
    };
    static InstructionPtr cmpb_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IX,DED); */ 
        }
    };
    static InstructionPtr cmpb_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { CMPB_M(IX,IX); */ 
        }
    };
    static InstructionPtr cmpb_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IX,IXD); */ 
        }
    };
    static InstructionPtr cmpb_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IXD,RG); */ 
        }
    };
    static InstructionPtr cmpb_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr cmpb_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IXD,IN); */ 
        }
    };
    static InstructionPtr cmpb_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(IXD,IND); */ 
        }
    };
    static InstructionPtr cmpb_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IXD,DE); */ 
        }
    };
    static InstructionPtr cmpb_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(IXD,DED); */ 
        }
    };
    static InstructionPtr cmpb_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { CMPB_M(IXD,IX); */ 
        }
    };
    static InstructionPtr cmpb_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { CMPB_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr bitb_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_R(RG,RG); */ 
        }
    };
    static InstructionPtr bitb_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(RG,RGD); */ 
        }
    };
    static InstructionPtr bitb_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(RG,IN); */ 
        }
    };
    static InstructionPtr bitb_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(RG,IND); */ 
        }
    };
    static InstructionPtr bitb_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(RG,DE); */ 
        }
    };
    static InstructionPtr bitb_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(RG,DED); */ 
        }
    };
    static InstructionPtr bitb_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(RG,IX); */ 
        }
    };
    static InstructionPtr bitb_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(RG,IXD); */ 
        }
    };
    static InstructionPtr bitb_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(RGD,RG); */ 
        }
    };
    static InstructionPtr bitb_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr bitb_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(RGD,IN); */ 
        }
    };
    static InstructionPtr bitb_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(RGD,IND); */ 
        }
    };
    static InstructionPtr bitb_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(RGD,DE); */ 
        }
    };
    static InstructionPtr bitb_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(RGD,DED); */ 
        }
    };
    static InstructionPtr bitb_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(RGD,IX); */ 
        }
    };
    static InstructionPtr bitb_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr bitb_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(IN,RG); */ 
        }
    };
    static InstructionPtr bitb_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IN,RGD); */ 
        }
    };
    static InstructionPtr bitb_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(IN,IN); */ 
        }
    };
    static InstructionPtr bitb_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IN,IND); */ 
        }
    };
    static InstructionPtr bitb_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(IN,DE); */ 
        }
    };
    static InstructionPtr bitb_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IN,DED); */ 
        }
    };
    static InstructionPtr bitb_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(IN,IX); */ 
        }
    };
    static InstructionPtr bitb_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IN,IXD); */ 
        }
    };
    static InstructionPtr bitb_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IND,RG); */ 
        }
    };
    static InstructionPtr bitb_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(IND,RGD); */ 
        }
    };
    static InstructionPtr bitb_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IND,IN); */ 
        }
    };
    static InstructionPtr bitb_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(IND,IND); */ 
        }
    };
    static InstructionPtr bitb_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IND,DE); */ 
        }
    };
    static InstructionPtr bitb_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(IND,DED); */ 
        }
    };
    static InstructionPtr bitb_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IND,IX); */ 
        }
    };
    static InstructionPtr bitb_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(IND,IXD); */ 
        }
    };
    static InstructionPtr bitb_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(DE,RG); */ 
        }
    };
    static InstructionPtr bitb_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(DE,RGD); */ 
        }
    };
    static InstructionPtr bitb_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(DE,IN); */ 
        }
    };
    static InstructionPtr bitb_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(DE,IND); */ 
        }
    };
    static InstructionPtr bitb_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(DE,DE); */ 
        }
    };
    static InstructionPtr bitb_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(DE,DED); */ 
        }
    };
    static InstructionPtr bitb_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(DE,IX); */ 
        }
    };
    static InstructionPtr bitb_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(DE,IXD); */ 
        }
    };
    static InstructionPtr bitb_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(DED,RG); */ 
        }
    };
    static InstructionPtr bitb_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(DED,RGD); */ 
        }
    };
    static InstructionPtr bitb_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(DED,IN); */ 
        }
    };
    static InstructionPtr bitb_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(DED,IND); */ 
        }
    };
    static InstructionPtr bitb_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(DED,DE); */ 
        }
    };
    static InstructionPtr bitb_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(DED,DED); */ 
        }
    };
    static InstructionPtr bitb_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(DED,IX); */ 
        }
    };
    static InstructionPtr bitb_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(DED,IXD); */ 
        }
    };
    static InstructionPtr bitb_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(IX,RG); */ 
        }
    };
    static InstructionPtr bitb_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IX,RGD); */ 
        }
    };
    static InstructionPtr bitb_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(IX,IN); */ 
        }
    };
    static InstructionPtr bitb_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IX,IND); */ 
        }
    };
    static InstructionPtr bitb_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(IX,DE); */ 
        }
    };
    static InstructionPtr bitb_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IX,DED); */ 
        }
    };
    static InstructionPtr bitb_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BITB_M(IX,IX); */ 
        }
    };
    static InstructionPtr bitb_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IX,IXD); */ 
        }
    };
    static InstructionPtr bitb_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IXD,RG); */ 
        }
    };
    static InstructionPtr bitb_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr bitb_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IXD,IN); */ 
        }
    };
    static InstructionPtr bitb_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(IXD,IND); */ 
        }
    };
    static InstructionPtr bitb_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IXD,DE); */ 
        }
    };
    static InstructionPtr bitb_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(IXD,DED); */ 
        }
    };
    static InstructionPtr bitb_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BITB_M(IXD,IX); */ 
        }
    };
    static InstructionPtr bitb_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BITB_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr bicb_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_R(RG,RG); */ 
        }
    };
    static InstructionPtr bicb_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(RG,RGD); */ 
        }
    };
    static InstructionPtr bicb_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(RG,IN); */ 
        }
    };
    static InstructionPtr bicb_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(RG,IND); */ 
        }
    };
    static InstructionPtr bicb_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(RG,DE); */ 
        }
    };
    static InstructionPtr bicb_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(RG,DED); */ 
        }
    };
    static InstructionPtr bicb_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(RG,IX); */ 
        }
    };
    static InstructionPtr bicb_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(RG,IXD); */ 
        }
    };
    static InstructionPtr bicb_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_X(RGD,RG); */ 
        }
    };
    static InstructionPtr bicb_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr bicb_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(RGD,IN); */ 
        }
    };
    static InstructionPtr bicb_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(RGD,IND); */ 
        }
    };
    static InstructionPtr bicb_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(RGD,DE); */ 
        }
    };
    static InstructionPtr bicb_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(RGD,DED); */ 
        }
    };
    static InstructionPtr bicb_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(RGD,IX); */ 
        }
    };
    static InstructionPtr bicb_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr bicb_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_X(IN,RG); */ 
        }
    };
    static InstructionPtr bicb_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IN,RGD); */ 
        }
    };
    static InstructionPtr bicb_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(IN,IN); */ 
        }
    };
    static InstructionPtr bicb_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IN,IND); */ 
        }
    };
    static InstructionPtr bicb_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(IN,DE); */ 
        }
    };
    static InstructionPtr bicb_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IN,DED); */ 
        }
    };
    static InstructionPtr bicb_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(IN,IX); */ 
        }
    };
    static InstructionPtr bicb_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IN,IXD); */ 
        }
    };
    static InstructionPtr bicb_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_X(IND,RG); */ 
        }
    };
    static InstructionPtr bicb_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(IND,RGD); */ 
        }
    };
    static InstructionPtr bicb_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IND,IN); */ 
        }
    };
    static InstructionPtr bicb_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(IND,IND); */ 
        }
    };
    static InstructionPtr bicb_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IND,DE); */ 
        }
    };
    static InstructionPtr bicb_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(IND,DED); */ 
        }
    };
    static InstructionPtr bicb_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IND,IX); */ 
        }
    };
    static InstructionPtr bicb_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(IND,IXD); */ 
        }
    };
    static InstructionPtr bicb_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_X(DE,RG); */ 
        }
    };
    static InstructionPtr bicb_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(DE,RGD); */ 
        }
    };
    static InstructionPtr bicb_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(DE,IN); */ 
        }
    };
    static InstructionPtr bicb_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(DE,IND); */ 
        }
    };
    static InstructionPtr bicb_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(DE,DE); */ 
        }
    };
    static InstructionPtr bicb_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(DE,DED); */ 
        }
    };
    static InstructionPtr bicb_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(DE,IX); */ 
        }
    };
    static InstructionPtr bicb_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(DE,IXD); */ 
        }
    };
    static InstructionPtr bicb_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_X(DED,RG); */ 
        }
    };
    static InstructionPtr bicb_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(DED,RGD); */ 
        }
    };
    static InstructionPtr bicb_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(DED,IN); */ 
        }
    };
    static InstructionPtr bicb_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(DED,IND); */ 
        }
    };
    static InstructionPtr bicb_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(DED,DE); */ 
        }
    };
    static InstructionPtr bicb_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(DED,DED); */ 
        }
    };
    static InstructionPtr bicb_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(DED,IX); */ 
        }
    };
    static InstructionPtr bicb_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(DED,IXD); */ 
        }
    };
    static InstructionPtr bicb_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_X(IX,RG); */ 
        }
    };
    static InstructionPtr bicb_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IX,RGD); */ 
        }
    };
    static InstructionPtr bicb_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(IX,IN); */ 
        }
    };
    static InstructionPtr bicb_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IX,IND); */ 
        }
    };
    static InstructionPtr bicb_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(IX,DE); */ 
        }
    };
    static InstructionPtr bicb_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IX,DED); */ 
        }
    };
    static InstructionPtr bicb_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BICB_M(IX,IX); */ 
        }
    };
    static InstructionPtr bicb_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IX,IXD); */ 
        }
    };
    static InstructionPtr bicb_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_X(IXD,RG); */ 
        }
    };
    static InstructionPtr bicb_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr bicb_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IXD,IN); */ 
        }
    };
    static InstructionPtr bicb_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(IXD,IND); */ 
        }
    };
    static InstructionPtr bicb_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IXD,DE); */ 
        }
    };
    static InstructionPtr bicb_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(IXD,DED); */ 
        }
    };
    static InstructionPtr bicb_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BICB_M(IXD,IX); */ 
        }
    };
    static InstructionPtr bicb_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BICB_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr bisb_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_R(RG,RG); */ 
        }
    };
    static InstructionPtr bisb_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(RG,RGD); */ 
        }
    };
    static InstructionPtr bisb_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(RG,IN); */ 
        }
    };
    static InstructionPtr bisb_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(RG,IND); */ 
        }
    };
    static InstructionPtr bisb_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(RG,DE); */ 
        }
    };
    static InstructionPtr bisb_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(RG,DED); */ 
        }
    };
    static InstructionPtr bisb_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(RG,IX); */ 
        }
    };
    static InstructionPtr bisb_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(RG,IXD); */ 
        }
    };
    static InstructionPtr bisb_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_X(RGD,RG); */ 
        }
    };
    static InstructionPtr bisb_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr bisb_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(RGD,IN); */ 
        }
    };
    static InstructionPtr bisb_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(RGD,IND); */ 
        }
    };
    static InstructionPtr bisb_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(RGD,DE); */ 
        }
    };
    static InstructionPtr bisb_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(RGD,DED); */ 
        }
    };
    static InstructionPtr bisb_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(RGD,IX); */ 
        }
    };
    static InstructionPtr bisb_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr bisb_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_X(IN,RG); */ 
        }
    };
    static InstructionPtr bisb_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IN,RGD); */ 
        }
    };
    static InstructionPtr bisb_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(IN,IN); */ 
        }
    };
    static InstructionPtr bisb_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IN,IND); */ 
        }
    };
    static InstructionPtr bisb_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(IN,DE); */ 
        }
    };
    static InstructionPtr bisb_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IN,DED); */ 
        }
    };
    static InstructionPtr bisb_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(IN,IX); */ 
        }
    };
    static InstructionPtr bisb_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IN,IXD); */ 
        }
    };
    static InstructionPtr bisb_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_X(IND,RG); */ 
        }
    };
    static InstructionPtr bisb_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(IND,RGD); */ 
        }
    };
    static InstructionPtr bisb_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IND,IN); */ 
        }
    };
    static InstructionPtr bisb_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(IND,IND); */ 
        }
    };
    static InstructionPtr bisb_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IND,DE); */ 
        }
    };
    static InstructionPtr bisb_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(IND,DED); */ 
        }
    };
    static InstructionPtr bisb_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IND,IX); */ 
        }
    };
    static InstructionPtr bisb_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(IND,IXD); */ 
        }
    };
    static InstructionPtr bisb_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_X(DE,RG); */ 
        }
    };
    static InstructionPtr bisb_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(DE,RGD); */ 
        }
    };
    static InstructionPtr bisb_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(DE,IN); */ 
        }
    };
    static InstructionPtr bisb_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(DE,IND); */ 
        }
    };
    static InstructionPtr bisb_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(DE,DE); */ 
        }
    };
    static InstructionPtr bisb_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(DE,DED); */ 
        }
    };
    static InstructionPtr bisb_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(DE,IX); */ 
        }
    };
    static InstructionPtr bisb_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(DE,IXD); */ 
        }
    };
    static InstructionPtr bisb_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_X(DED,RG); */ 
        }
    };
    static InstructionPtr bisb_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(DED,RGD); */ 
        }
    };
    static InstructionPtr bisb_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(DED,IN); */ 
        }
    };
    static InstructionPtr bisb_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(DED,IND); */ 
        }
    };
    static InstructionPtr bisb_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(DED,DE); */ 
        }
    };
    static InstructionPtr bisb_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(DED,DED); */ 
        }
    };
    static InstructionPtr bisb_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(DED,IX); */ 
        }
    };
    static InstructionPtr bisb_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(DED,IXD); */ 
        }
    };
    static InstructionPtr bisb_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_X(IX,RG); */ 
        }
    };
    static InstructionPtr bisb_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IX,RGD); */ 
        }
    };
    static InstructionPtr bisb_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(IX,IN); */ 
        }
    };
    static InstructionPtr bisb_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IX,IND); */ 
        }
    };
    static InstructionPtr bisb_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(IX,DE); */ 
        }
    };
    static InstructionPtr bisb_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IX,DED); */ 
        }
    };
    static InstructionPtr bisb_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { BISB_M(IX,IX); */ 
        }
    };
    static InstructionPtr bisb_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IX,IXD); */ 
        }
    };
    static InstructionPtr bisb_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_X(IXD,RG); */ 
        }
    };
    static InstructionPtr bisb_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr bisb_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IXD,IN); */ 
        }
    };
    static InstructionPtr bisb_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(IXD,IND); */ 
        }
    };
    static InstructionPtr bisb_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IXD,DE); */ 
        }
    };
    static InstructionPtr bisb_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(IXD,DED); */ 
        }
    };
    static InstructionPtr bisb_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { BISB_M(IXD,IX); */ 
        }
    };
    static InstructionPtr bisb_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { BISB_M(IXD,IXD); */ 
        }
    };
    
    static InstructionPtr sub_rg_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_R(RG,RG); */ 
        }
    };
    static InstructionPtr sub_rg_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(RG,RGD); */ 
        }
    };
    static InstructionPtr sub_rg_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(RG,IN); */ 
        }
    };
    static InstructionPtr sub_rg_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(RG,IND); */ 
        }
    };
    static InstructionPtr sub_rg_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(RG,DE); */ 
        }
    };
    static InstructionPtr sub_rg_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(RG,DED); */ 
        }
    };
    static InstructionPtr sub_rg_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(RG,IX); */ 
        }
    };
    static InstructionPtr sub_rg_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(RG,IXD); */ 
        }
    };
    static InstructionPtr sub_rgd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_X(RGD,RG); */ 
        }
    };
    static InstructionPtr sub_rgd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(RGD,RGD); */ 
        }
    };
    static InstructionPtr sub_rgd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(RGD,IN); */ 
        }
    };
    static InstructionPtr sub_rgd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(RGD,IND); */ 
        }
    };
    static InstructionPtr sub_rgd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(RGD,DE); */ 
        }
    };
    static InstructionPtr sub_rgd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(RGD,DED); */ 
        }
    };
    static InstructionPtr sub_rgd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(RGD,IX); */ 
        }
    };
    static InstructionPtr sub_rgd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(RGD,IXD); */ 
        }
    };
    static InstructionPtr sub_in_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_X(IN,RG); */ 
        }
    };
    static InstructionPtr sub_in_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IN,RGD); */ 
        }
    };
    static InstructionPtr sub_in_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(IN,IN); */ 
        }
    };
    static InstructionPtr sub_in_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IN,IND); */ 
        }
    };
    static InstructionPtr sub_in_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(IN,DE); */ 
        }
    };
    static InstructionPtr sub_in_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IN,DED); */ 
        }
    };
    static InstructionPtr sub_in_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(IN,IX); */ 
        }
    };
    static InstructionPtr sub_in_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IN,IXD); */ 
        }
    };
    static InstructionPtr sub_ind_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_X(IND,RG); */ 
        }
    };
    static InstructionPtr sub_ind_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(IND,RGD); */ 
        }
    };
    static InstructionPtr sub_ind_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IND,IN); */ 
        }
    };
    static InstructionPtr sub_ind_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(IND,IND); */ 
        }
    };
    static InstructionPtr sub_ind_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IND,DE); */ 
        }
    };
    static InstructionPtr sub_ind_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(IND,DED); */ 
        }
    };
    static InstructionPtr sub_ind_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IND,IX); */ 
        }
    };
    static InstructionPtr sub_ind_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(IND,IXD); */ 
        }
    };
    static InstructionPtr sub_de_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_X(DE,RG); */ 
        }
    };
    static InstructionPtr sub_de_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(DE,RGD); */ 
        }
    };
    static InstructionPtr sub_de_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(DE,IN); */ 
        }
    };
    static InstructionPtr sub_de_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(DE,IND); */ 
        }
    };
    static InstructionPtr sub_de_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(DE,DE); */ 
        }
    };
    static InstructionPtr sub_de_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(DE,DED); */ 
        }
    };
    static InstructionPtr sub_de_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(DE,IX); */ 
        }
    };
    static InstructionPtr sub_de_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(DE,IXD); */ 
        }
    };
    static InstructionPtr sub_ded_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_X(DED,RG); */ 
        }
    };
    static InstructionPtr sub_ded_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(DED,RGD); */ 
        }
    };
    static InstructionPtr sub_ded_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(DED,IN); */ 
        }
    };
    static InstructionPtr sub_ded_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(DED,IND); */ 
        }
    };
    static InstructionPtr sub_ded_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(DED,DE); */ 
        }
    };
    static InstructionPtr sub_ded_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(DED,DED); */ 
        }
    };
    static InstructionPtr sub_ded_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(DED,IX); */ 
        }
    };
    static InstructionPtr sub_ded_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(DED,IXD); */ 
        }
    };
    static InstructionPtr sub_ix_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_X(IX,RG); */ 
        }
    };
    static InstructionPtr sub_ix_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IX,RGD); */ 
        }
    };
    static InstructionPtr sub_ix_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(IX,IN); */ 
        }
    };
    static InstructionPtr sub_ix_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IX,IND); */ 
        }
    };
    static InstructionPtr sub_ix_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(IX,DE); */ 
        }
    };
    static InstructionPtr sub_ix_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IX,DED); */ 
        }
    };
    static InstructionPtr sub_ix_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*     { SUB_M(IX,IX); */ 
        }
    };
    static InstructionPtr sub_ix_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IX,IXD); */ 
        }
    };
    static InstructionPtr sub_ixd_rg = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_X(IXD,RG); */ 
        }
    };
    static InstructionPtr sub_ixd_rgd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(IXD,RGD); */ 
        }
    };
    static InstructionPtr sub_ixd_in = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IXD,IN); */ 
        }
    };
    static InstructionPtr sub_ixd_ind = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(IXD,IND); */ 
        }
    };
    static InstructionPtr sub_ixd_de = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IXD,DE); */ 
        }
    };
    static InstructionPtr sub_ixd_ded = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(IXD,DED); */ 
        }
    };
    static InstructionPtr sub_ixd_ix = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*    { SUB_M(IXD,IX); */ 
        }
    };
    static InstructionPtr sub_ixd_ixd = new InstructionPtr() /* Opcode 0x?? */ { public void handler() { throw new UnsupportedOperationException("Not Supported!");/*   { SUB_M(IXD,IXD); */ 
        }
    };
    
}
