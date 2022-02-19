package arcadeflex.WIP.v037b7.cpu.m68000;

import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.fclose;
import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.fprintf;
import static gr.codebb.arcadeflex.v036.cpu.m68000.m68kcpu.*;
import static gr.codebb.arcadeflex.v036.cpu.m68000.m68kcpuH.*;
import static gr.codebb.arcadeflex.v036.cpu.m68000.m68kopsH.*;

public class M68KOPDM {
/*TODO*///void m68k_op_dbt_16(void)
/*TODO*///{
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbf_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///	if(res != 0xffff)
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbhi_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_HI())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbls_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_LS())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbcc_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_CC())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbcs_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_CS())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbne_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_NE())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbeq_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_EQ())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbvc_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_VC())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbvs_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_VS())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbpl_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_PL())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbmi_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_MI())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbge_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_GE())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dblt_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_LT())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dbgt_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_GT())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_dble_16(void)
/*TODO*///{
/*TODO*///	if(COND_NOT_LE())
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///		uint res = MASK_OUT_ABOVE_16(*r_dst - 1);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		if(res != 0xffff)
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_16();
/*TODO*///			REG_PC -= 2;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_16(offset);
/*TODO*///			USE_CYCLES(CYC_DBCC_F_NOEXP);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		USE_CYCLES(CYC_DBCC_F_EXP);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(DY);
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_AY_AI_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_AY_PI_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_AY_PD_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_AY_DI_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_AY_IX_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_AW_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_AL_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_PCDI_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_PCIX_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divs_16_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	sint src = MAKE_INT_16(OPER_I_16());
/*TODO*///	sint quotient;
/*TODO*///	sint remainder;
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		if((uint32)*r_dst == 0x80000000 && src == -1)
/*TODO*///		{
/*TODO*///			FLAG_Z = 0;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = 0;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		quotient = MAKE_INT_32(*r_dst) / src;
/*TODO*///		remainder = MAKE_INT_32(*r_dst) % src;
/*TODO*///
/*TODO*///		if(quotient == MAKE_INT_16(quotient))
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DY);
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_AI_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PI_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PD_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_DI_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_IX_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AW_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AL_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCDI_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCIX_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divu_16_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_I_16();
/*TODO*///
/*TODO*///	if(src != 0)
/*TODO*///	{
/*TODO*///		uint quotient = *r_dst / src;
/*TODO*///		uint remainder = *r_dst % src;
/*TODO*///
/*TODO*///		if(quotient < 0x10000)
/*TODO*///		{
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_N = NFLAG_16(quotient);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			*r_dst = MASK_OUT_ABOVE_32(MASK_OUT_ABOVE_16(quotient) | (remainder << 16));
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_V = VFLAG_SET;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_d(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor   = DY;
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = DY;
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_ai(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_AY_AI_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_AY_AI_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_pi(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_AY_PI_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_AY_PI_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_pd(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_AY_PD_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_AY_PD_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_di(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_AY_DI_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_AY_DI_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_ix(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_AY_IX_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_AY_IX_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_aw(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_AW_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_AW_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_al(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_AL_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_AL_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_pcdi(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_PCDI_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_PCDI_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_pcix(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_PCIX_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_PCIX_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_divl_32_i(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 divisor = OPER_I_32();
/*TODO*///		uint64 dividend  = 0;
/*TODO*///		uint64 quotient  = 0;
/*TODO*///		uint64 remainder = 0;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			if(BIT_A(word2))	/* 64 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[word2 & 7];
/*TODO*///				dividend <<= 32;
/*TODO*///				dividend |= REG_D[(word2 >> 12) & 7];
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)dividend / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)dividend % (sint64)((sint32)divisor));
/*TODO*///					if((sint64)quotient != (sint64)((sint32)quotient))
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					if(quotient > 0xffffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else	/* 32 bit */
/*TODO*///			{
/*TODO*///				dividend = REG_D[(word2 >> 12) & 7];
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					quotient  = (uint64)((sint64)((sint32)dividend) / (sint64)((sint32)divisor));
/*TODO*///					remainder = (uint64)((sint64)((sint32)dividend) % (sint64)((sint32)divisor));
/*TODO*///				}
/*TODO*///				else					/* unsigned */
/*TODO*///				{
/*TODO*///					quotient = dividend / divisor;
/*TODO*///					remainder = dividend % divisor;
/*TODO*///				}
/*TODO*///			}
/*TODO*///
/*TODO*///			REG_D[word2 & 7] = remainder;
/*TODO*///			REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint divisor = OPER_I_32();
/*TODO*///		uint dividend_hi = REG_D[word2 & 7];
/*TODO*///		uint dividend_lo = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint quotient = 0;
/*TODO*///		uint remainder = 0;
/*TODO*///		uint dividend_neg = 0;
/*TODO*///		uint divisor_neg = 0;
/*TODO*///		sint i;
/*TODO*///		uint overflow;
/*TODO*///
/*TODO*///		if(divisor != 0)
/*TODO*///		{
/*TODO*///			/* quad / long : long quotient, long remainder */
/*TODO*///			if(BIT_A(word2))
/*TODO*///			{
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					/* special case in signed divide */
/*TODO*///					if(dividend_hi == 0 && dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///					{
/*TODO*///						REG_D[word2 & 7] = 0;
/*TODO*///						REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///
/*TODO*///						FLAG_N = NFLAG_SET;
/*TODO*///						FLAG_Z = ZFLAG_CLEAR;
/*TODO*///						FLAG_V = VFLAG_CLEAR;
/*TODO*///						FLAG_C = CFLAG_CLEAR;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(dividend_hi))
/*TODO*///					{
/*TODO*///						dividend_neg = 1;
/*TODO*///						dividend_hi = MASK_OUT_ABOVE_32((-dividend_hi) - (dividend_lo != 0));
/*TODO*///						dividend_lo = MASK_OUT_ABOVE_32(-dividend_lo);
/*TODO*///					}
/*TODO*///					if(GET_MSB_32(divisor))
/*TODO*///					{
/*TODO*///						divisor_neg = 1;
/*TODO*///						divisor = MASK_OUT_ABOVE_32(-divisor);
/*TODO*///
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				/* if the upper long is greater than the divisor, we're overflowing. */
/*TODO*///				if(dividend_hi >= divisor)
/*TODO*///				{
/*TODO*///					FLAG_V = VFLAG_SET;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					remainder = (remainder << 1) + ((dividend_hi >> i) & 1);
/*TODO*///					if(remainder >= divisor)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///				for(i = 31; i >= 0; i--)
/*TODO*///				{
/*TODO*///					quotient <<= 1;
/*TODO*///					overflow = GET_MSB_32(remainder);
/*TODO*///					remainder = (remainder << 1) + ((dividend_lo >> i) & 1);
/*TODO*///					if(remainder >= divisor || overflow)
/*TODO*///					{
/*TODO*///						remainder -= divisor;
/*TODO*///						quotient++;
/*TODO*///					}
/*TODO*///				}
/*TODO*///
/*TODO*///				if(BIT_B(word2))	   /* signed */
/*TODO*///				{
/*TODO*///					if(quotient > 0x7fffffff)
/*TODO*///					{
/*TODO*///						FLAG_V = VFLAG_SET;
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					if(dividend_neg)
/*TODO*///					{
/*TODO*///						remainder = MASK_OUT_ABOVE_32(-remainder);
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///					}
/*TODO*///					if(divisor_neg)
/*TODO*///						quotient = MASK_OUT_ABOVE_32(-quotient);
/*TODO*///				}
/*TODO*///
/*TODO*///				REG_D[word2 & 7] = remainder;
/*TODO*///				REG_D[(word2 >> 12) & 7] = quotient;
/*TODO*///
/*TODO*///				FLAG_N = NFLAG_32(quotient);
/*TODO*///				FLAG_Z = quotient;
/*TODO*///				FLAG_V = VFLAG_CLEAR;
/*TODO*///				FLAG_C = CFLAG_CLEAR;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///
/*TODO*///			/* long / long: long quotient, maybe long remainder */
/*TODO*///			if(BIT_B(word2))	   /* signed */
/*TODO*///			{
/*TODO*///				/* Special case in divide */
/*TODO*///				if(dividend_lo == 0x80000000 && divisor == 0xffffffff)
/*TODO*///				{
/*TODO*///					FLAG_N = NFLAG_SET;
/*TODO*///					FLAG_Z = ZFLAG_CLEAR;
/*TODO*///					FLAG_V = VFLAG_CLEAR;
/*TODO*///					FLAG_C = CFLAG_CLEAR;
/*TODO*///					REG_D[(word2 >> 12) & 7] = 0x80000000;
/*TODO*///					REG_D[word2 & 7] = 0;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				REG_D[word2 & 7] = MAKE_INT_32(dividend_lo) % MAKE_INT_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MAKE_INT_32(dividend_lo) / MAKE_INT_32(divisor);
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				REG_D[word2 & 7] = MASK_OUT_ABOVE_32(dividend_lo) % MASK_OUT_ABOVE_32(divisor);
/*TODO*///				quotient = REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(dividend_lo) / MASK_OUT_ABOVE_32(divisor);
/*TODO*///			}
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(quotient);
/*TODO*///			FLAG_Z = quotient;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			FLAG_C = CFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_trap(EXCEPTION_ZERO_DIVIDE);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY ^= MASK_OUT_ABOVE_8(DX));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX ^ m68ki_read_8(ea));
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY ^= MASK_OUT_ABOVE_16(DX));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX ^ m68ki_read_16(ea));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX ^ m68ki_read_16(ea));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX ^ m68ki_read_16(ea));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX ^ m68ki_read_16(ea));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX ^ m68ki_read_16(ea));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX ^ m68ki_read_16(ea));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX ^ m68ki_read_16(ea));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY ^= DX;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint res = DX ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_32_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint res = DX ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_32_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint res = DX ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint res = DX ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint res = DX ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint res = DX ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eor_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint res = DX ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY ^= OPER_I_8());
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_8_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint res = src ^ m68ki_read_8(ea);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY ^= OPER_I_16());
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint res = src ^ m68ki_read_16(ea);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint res = src ^ m68ki_read_16(ea);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint res = src ^ m68ki_read_16(ea);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_16_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint res = src ^ m68ki_read_16(ea);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint res = src ^ m68ki_read_16(ea);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint res = src ^ m68ki_read_16(ea);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_16_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint res = src ^ m68ki_read_16(ea);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY ^= OPER_I_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint res = src ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint res = src ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint res = src ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_32_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint res = src ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint res = src ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint res = src ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_32_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint res = src ^ m68ki_read_32(ea);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_to_ccr_16(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(m68ki_get_ccr() ^ OPER_I_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_eori_to_sr_16(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint src = OPER_I_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(m68ki_get_sr() ^ src);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_exg_dd_32(void)
/*TODO*///{
/*TODO*///	uint* reg_a = &DX;
/*TODO*///	uint* reg_b = &DY;
/*TODO*///	uint tmp = *reg_a;
/*TODO*///	*reg_a = *reg_b;
/*TODO*///	*reg_b = tmp;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_exg_aa_32(void)
/*TODO*///{
/*TODO*///	uint* reg_a = &AX;
/*TODO*///	uint* reg_b = &AY;
/*TODO*///	uint tmp = *reg_a;
/*TODO*///	*reg_a = *reg_b;
/*TODO*///	*reg_b = tmp;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_exg_da_32(void)
/*TODO*///{
/*TODO*///	uint* reg_a = &DX;
/*TODO*///	uint* reg_b = &AY;
/*TODO*///	uint tmp = *reg_a;
/*TODO*///	*reg_a = *reg_b;
/*TODO*///	*reg_b = tmp;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ext_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | MASK_OUT_ABOVE_8(*r_dst) | (GET_MSB_8(*r_dst) ? 0xff00 : 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(*r_dst);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ext_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_16(*r_dst) | (GET_MSB_16(*r_dst) ? 0xffff0000 : 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(*r_dst);
/*TODO*///	FLAG_Z = *r_dst;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_extb_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint* r_dst = &DY;
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_ABOVE_8(*r_dst) | (GET_MSB_8(*r_dst) ? 0xffffff00 : 0);
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(*r_dst);
/*TODO*///		FLAG_Z = *r_dst;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_illegal(void)
/*TODO*///{
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jmp_32_ai(void)
/*TODO*///{
/*TODO*///	m68ki_jump(EA_AY_AI_32());
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jmp_32_di(void)
/*TODO*///{
/*TODO*///	m68ki_jump(EA_AY_DI_32());
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jmp_32_ix(void)
/*TODO*///{
/*TODO*///	m68ki_jump(EA_AY_IX_32());
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jmp_32_aw(void)
/*TODO*///{
/*TODO*///	m68ki_jump(EA_AW_32());
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jmp_32_al(void)
/*TODO*///{
/*TODO*///	m68ki_jump(EA_AL_32());
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jmp_32_pcdi(void)
/*TODO*///{
/*TODO*///	m68ki_jump(EA_PCDI_32());
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jmp_32_pcix(void)
/*TODO*///{
/*TODO*///	m68ki_jump(EA_PCIX_32());
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jsr_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	m68ki_jump(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jsr_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	m68ki_jump(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jsr_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	m68ki_jump(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jsr_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	m68ki_jump(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jsr_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	m68ki_jump(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jsr_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_PCDI_32();
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	m68ki_jump(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_jsr_32_pcix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_PCIX_32();
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	m68ki_jump(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lea_32_ai(void)
/*TODO*///{
/*TODO*///	AX = EA_AY_AI_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lea_32_di(void)
/*TODO*///{
/*TODO*///	AX = EA_AY_DI_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lea_32_ix(void)
/*TODO*///{
/*TODO*///	AX = EA_AY_IX_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lea_32_aw(void)
/*TODO*///{
/*TODO*///	AX = EA_AW_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lea_32_al(void)
/*TODO*///{
/*TODO*///	AX = EA_AL_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lea_32_pcdi(void)
/*TODO*///{
/*TODO*///	AX = EA_PCDI_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lea_32_pcix(void)
/*TODO*///{
/*TODO*///	AX = EA_PCIX_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_link_16_a7(void)
/*TODO*///{
/*TODO*///	REG_A[7] -= 4;
/*TODO*///	m68ki_write_32(REG_A[7], REG_A[7]);
/*TODO*///	REG_A[7] = MASK_OUT_ABOVE_32(REG_A[7] + MAKE_INT_16(OPER_I_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_link_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AY;
/*TODO*///
/*TODO*///	m68ki_push_32(*r_dst);
/*TODO*///	*r_dst = REG_A[7];
/*TODO*///	REG_A[7] = MASK_OUT_ABOVE_32(REG_A[7] + MAKE_INT_16(OPER_I_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_link_32_a7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		REG_A[7] -= 4;
/*TODO*///		m68ki_write_32(REG_A[7], REG_A[7]);
/*TODO*///		REG_A[7] = MASK_OUT_ABOVE_32(REG_A[7] + OPER_I_32());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_link_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint* r_dst = &AY;
/*TODO*///
/*TODO*///		m68ki_push_32(*r_dst);
/*TODO*///		*r_dst = REG_A[7];
/*TODO*///		REG_A[7] = MASK_OUT_ABOVE_32(REG_A[7] + OPER_I_32());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_s_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src << (9-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_s_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src << (9-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_s_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src << (9-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_r_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = DX & 0x3f;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	if(shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		if(shift <= 8)
/*TODO*///		{
/*TODO*///			*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///			FLAG_X = FLAG_C = src << (9-shift);
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst &= 0xffffff00;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_N = NFLAG_8(src);
/*TODO*///	FLAG_Z = src;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_r_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = DX & 0x3f;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	if(shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		if(shift <= 16)
/*TODO*///		{
/*TODO*///			*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///			FLAG_C = FLAG_X = (src >> (shift - 1))<<8;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst &= 0xffff0000;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_N = NFLAG_16(src);
/*TODO*///	FLAG_Z = src;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_r_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = DX & 0x3f;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	if(shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		if(shift < 32)
/*TODO*///		{
/*TODO*///			*r_dst = res;
/*TODO*///			FLAG_C = FLAG_X = (src >> (shift - 1))<<8;
/*TODO*///			FLAG_N = NFLAG_CLEAR;
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst = 0;
/*TODO*///		FLAG_X = FLAG_C = (shift == 32 ? GET_MSB_32(src)>>23 : 0);
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_N = NFLAG_32(src);
/*TODO*///	FLAG_Z = src;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsr_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_s_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src << shift);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src << shift;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_s_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << shift);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> (8-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_s_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(src << shift);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> (24-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_r_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = DX & 0x3f;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src << shift);
/*TODO*///
/*TODO*///	if(shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		if(shift <= 8)
/*TODO*///		{
/*TODO*///			*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///			FLAG_X = FLAG_C = src << shift;
/*TODO*///			FLAG_N = NFLAG_8(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst &= 0xffffff00;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_N = NFLAG_8(src);
/*TODO*///	FLAG_Z = src;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_r_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = DX & 0x3f;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << shift);
/*TODO*///
/*TODO*///	if(shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		if(shift <= 16)
/*TODO*///		{
/*TODO*///			*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///			FLAG_X = FLAG_C = (src << shift) >> 8;
/*TODO*///			FLAG_N = NFLAG_16(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst &= 0xffff0000;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_N = NFLAG_16(src);
/*TODO*///	FLAG_Z = src;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_r_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = DX & 0x3f;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(src << shift);
/*TODO*///
/*TODO*///	if(shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		if(shift < 32)
/*TODO*///		{
/*TODO*///			*r_dst = res;
/*TODO*///			FLAG_X = FLAG_C = (src >> (32 - shift)) << 8;
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst = 0;
/*TODO*///		FLAG_X = FLAG_C = ((shift == 32 ? src & 1 : 0))<<8;
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_N = NFLAG_32(src);
/*TODO*///	FLAG_Z = src;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_lsl_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src << 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_X = FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_AX_AI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi7_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_AX_PI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd7_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_AX_PD_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_AX_DI_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_AX_IX_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PI_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = OPER_A7_PD_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_8_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_a(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_16_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_a(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_16_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_16();
/*TODO*///	uint ea = EA_AX_AI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_a(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_16_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_16();
/*TODO*///	uint ea = EA_AX_PI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_a(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_16_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_16();
/*TODO*///	uint ea = EA_AX_PD_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_a(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_16_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_16();
/*TODO*///	uint ea = EA_AX_DI_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_a(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_16_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_16();
/*TODO*///	uint ea = EA_AX_IX_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_a(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_16_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_a(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_16_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_a(void)
/*TODO*///{
/*TODO*///	uint res = AY;
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_dd_32_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_32();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_a(void)
/*TODO*///{
/*TODO*///	uint res = AY;
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ai_32_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_32();
/*TODO*///	uint ea = EA_AX_AI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_a(void)
/*TODO*///{
/*TODO*///	uint res = AY;
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pi_32_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_32();
/*TODO*///	uint ea = EA_AX_PI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_a(void)
/*TODO*///{
/*TODO*///	uint res = AY;
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_pd_32_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_32();
/*TODO*///	uint ea = EA_AX_PD_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_a(void)
/*TODO*///{
/*TODO*///	uint res = AY;
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_di_32_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_32();
/*TODO*///	uint ea = EA_AX_DI_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_a(void)
/*TODO*///{
/*TODO*///	uint res = AY;
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_ix_32_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_32();
/*TODO*///	uint ea = EA_AX_IX_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_a(void)
/*TODO*///{
/*TODO*///	uint res = AY;
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_aw_32_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_a(void)
/*TODO*///{
/*TODO*///	uint res = AY;
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCDI_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_PCIX_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_al_32_i(void)
/*TODO*///{
/*TODO*///	uint res = OPER_I_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_d(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(DY);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_a(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(AY);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_ai(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_AY_AI_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_pi(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_AY_PI_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_pd(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_AY_PD_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_di(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_AY_DI_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_ix(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_AY_IX_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_aw(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_AW_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_al(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_AL_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_pcdi(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_PCDI_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_pcix(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_PCIX_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_16_i(void)
/*TODO*///{
/*TODO*///	AX = MAKE_INT_16(OPER_I_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_d(void)
/*TODO*///{
/*TODO*///	AX = DY;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_a(void)
/*TODO*///{
/*TODO*///	AX = AY;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_ai(void)
/*TODO*///{
/*TODO*///	AX = OPER_AY_AI_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_pi(void)
/*TODO*///{
/*TODO*///	AX = OPER_AY_PI_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_pd(void)
/*TODO*///{
/*TODO*///	AX = OPER_AY_PD_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_di(void)
/*TODO*///{
/*TODO*///	AX = OPER_AY_DI_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_ix(void)
/*TODO*///{
/*TODO*///	AX = OPER_AY_IX_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_aw(void)
/*TODO*///{
/*TODO*///	AX = OPER_AW_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_al(void)
/*TODO*///{
/*TODO*///	AX = OPER_AL_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_pcdi(void)
/*TODO*///{
/*TODO*///	AX = OPER_PCDI_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_pcix(void)
/*TODO*///{
/*TODO*///	AX = OPER_PCIX_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movea_32_i(void)
/*TODO*///{
/*TODO*///	AX = OPER_I_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_ccr_16_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		DY = MASK_OUT_BELOW_16(DY) | m68ki_get_ccr();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_ccr_16_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_write_16(EA_AY_AI_16(), m68ki_get_ccr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_ccr_16_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_write_16(EA_AY_PI_16(), m68ki_get_ccr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_ccr_16_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_write_16(EA_AY_PD_16(), m68ki_get_ccr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_ccr_16_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_write_16(EA_AY_DI_16(), m68ki_get_ccr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_ccr_16_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_write_16(EA_AY_IX_16(), m68ki_get_ccr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_ccr_16_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_write_16(EA_AW_16(), m68ki_get_ccr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_ccr_16_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_write_16(EA_AL_16(), m68ki_get_ccr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_d(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(DY);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_ai(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_AY_AI_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_pi(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_AY_PI_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_pd(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_AY_PD_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_di(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_AY_DI_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_ix(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_AY_IX_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_aw(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_AW_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_al(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_AL_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_pcdi(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_PCDI_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_pcix(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_PCIX_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_ccr_16_i(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(OPER_I_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_sr_16_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_000(CPU_TYPE) || FLAG_S)	/* NS990408 */
/*TODO*///	{
/*TODO*///		DY = MASK_OUT_BELOW_16(DY) | m68ki_get_sr();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_sr_16_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_000(CPU_TYPE) || FLAG_S)	/* NS990408 */
/*TODO*///	{
/*TODO*///		uint ea = EA_AY_AI_16();
/*TODO*///		m68ki_write_16(ea, m68ki_get_sr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_sr_16_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_000(CPU_TYPE) || FLAG_S)	/* NS990408 */
/*TODO*///	{
/*TODO*///		uint ea = EA_AY_PI_16();
/*TODO*///		m68ki_write_16(ea, m68ki_get_sr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_sr_16_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_000(CPU_TYPE) || FLAG_S)	/* NS990408 */
/*TODO*///	{
/*TODO*///		uint ea = EA_AY_PD_16();
/*TODO*///		m68ki_write_16(ea, m68ki_get_sr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_sr_16_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_000(CPU_TYPE) || FLAG_S)	/* NS990408 */
/*TODO*///	{
/*TODO*///		uint ea = EA_AY_DI_16();
/*TODO*///		m68ki_write_16(ea, m68ki_get_sr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_sr_16_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_000(CPU_TYPE) || FLAG_S)	/* NS990408 */
/*TODO*///	{
/*TODO*///		uint ea = EA_AY_IX_16();
/*TODO*///		m68ki_write_16(ea, m68ki_get_sr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_sr_16_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_000(CPU_TYPE) || FLAG_S)	/* NS990408 */
/*TODO*///	{
/*TODO*///		uint ea = EA_AW_16();
/*TODO*///		m68ki_write_16(ea, m68ki_get_sr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_sr_16_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_000(CPU_TYPE) || FLAG_S)	/* NS990408 */
/*TODO*///	{
/*TODO*///		uint ea = EA_AL_16();
/*TODO*///		m68ki_write_16(ea, m68ki_get_sr());
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_d(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		m68ki_set_sr(DY);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_ai(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_AY_AI_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_pi(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_AY_PI_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_pd(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_AY_PD_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_di(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_AY_DI_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_ix(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_AY_IX_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_aw(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_AW_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_al(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_AL_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_pcdi(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_PCDI_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_pcix(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_PCIX_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_sr_16_i(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_I_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_fr_usp_32(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		AY = REG_USP;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_move_to_usp_32(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_USP = AY;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movec_cr_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();		   /* auto-disable (see m68kcpu.h) */
/*TODO*///			switch (word2 & 0xfff)
/*TODO*///			{
/*TODO*///			case 0x000:			   /* SFC */
/*TODO*///				REG_DA[(word2 >> 12) & 15] = REG_SFC;
/*TODO*///				return;
/*TODO*///			case 0x001:			   /* DFC */
/*TODO*///				REG_DA[(word2 >> 12) & 15] = REG_DFC;
/*TODO*///				return;
/*TODO*///			case 0x002:			   /* CACR */
/*TODO*///				if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///				{
/*TODO*///					REG_DA[(word2 >> 12) & 15] = REG_CACR;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				return;
/*TODO*///			case 0x800:			   /* USP */
/*TODO*///				REG_DA[(word2 >> 12) & 15] = REG_USP;
/*TODO*///				return;
/*TODO*///			case 0x801:			   /* VBR */
/*TODO*///				REG_DA[(word2 >> 12) & 15] = REG_VBR;
/*TODO*///				return;
/*TODO*///			case 0x802:			   /* CAAR */
/*TODO*///				if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///				{
/*TODO*///					REG_DA[(word2 >> 12) & 15] = REG_CAAR;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				break;
/*TODO*///			case 0x803:			   /* MSP */
/*TODO*///				if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///				{
/*TODO*///					REG_DA[(word2 >> 12) & 15] = FLAG_M ? REG_SP : REG_MSP;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				return;
/*TODO*///			case 0x804:			   /* ISP */
/*TODO*///				if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///				{
/*TODO*///					REG_DA[(word2 >> 12) & 15] = FLAG_M ? REG_ISP : REG_SP;
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				return;
/*TODO*///			default:
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				return;
/*TODO*///			}
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movec_rc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();		   /* auto-disable (see m68kcpu.h) */
/*TODO*///			switch (word2 & 0xfff)
/*TODO*///			{
/*TODO*///			case 0x000:			   /* SFC */
/*TODO*///				REG_SFC = REG_DA[(word2 >> 12) & 15] & 7;
/*TODO*///				return;
/*TODO*///			case 0x001:			   /* DFC */
/*TODO*///				REG_DFC = REG_DA[(word2 >> 12) & 15] & 7;
/*TODO*///				return;
/*TODO*///			case 0x002:			   /* CACR */
/*TODO*///				if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///				{
/*TODO*///					REG_CACR = REG_DA[(word2 >> 12) & 15];
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				return;
/*TODO*///			case 0x800:			   /* USP */
/*TODO*///				REG_USP = REG_DA[(word2 >> 12) & 15];
/*TODO*///				return;
/*TODO*///			case 0x801:			   /* VBR */
/*TODO*///				REG_VBR = REG_DA[(word2 >> 12) & 15];
/*TODO*///				return;
/*TODO*///			case 0x802:			   /* CAAR */
/*TODO*///				if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///				{
/*TODO*///					REG_CAAR = REG_DA[(word2 >> 12) & 15];
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				return;
/*TODO*///			case 0x803:			   /* MSP */
/*TODO*///				if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///				{
/*TODO*///					/* we are in supervisor mode so just check for M flag */
/*TODO*///					if(!FLAG_M)
/*TODO*///					{
/*TODO*///						REG_MSP = REG_DA[(word2 >> 12) & 15];
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					REG_SP = REG_DA[(word2 >> 12) & 15];
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				return;
/*TODO*///			case 0x804:			   /* ISP */
/*TODO*///				if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///				{
/*TODO*///					if(!FLAG_M)
/*TODO*///					{
/*TODO*///						REG_SP = REG_DA[(word2 >> 12) & 15];
/*TODO*///						return;
/*TODO*///					}
/*TODO*///					REG_ISP = REG_DA[(word2 >> 12) & 15];
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				return;
/*TODO*///			default:
/*TODO*///				m68ki_exception_illegal();
/*TODO*///				return;
/*TODO*///			}
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_16_pd(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = AY;
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			ea -= 2;
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_DA[15-i]));
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///	AY = ea;
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_16_ai(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_DA[i]));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_16_di(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_DA[i]));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_16_ix(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_DA[i]));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_16_aw(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_DA[i]));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_16_al(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_DA[i]));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_32_pd(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = AY;
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			ea -= 4;
/*TODO*///			m68ki_write_32(ea, REG_DA[15-i]);
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///	AY = ea;
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_32_ai(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_32(ea, REG_DA[i]);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_32_di(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_32(ea, REG_DA[i]);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_32_ix(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_32(ea, REG_DA[i]);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_32_aw(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_32(ea, REG_DA[i]);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_re_32_al(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			m68ki_write_32(ea, REG_DA[i]);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_16_pi(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = AY;
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = MAKE_INT_16(MASK_OUT_ABOVE_16(m68ki_read_16(ea)));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///	AY = ea;
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_16_ai(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = MAKE_INT_16(MASK_OUT_ABOVE_16(m68ki_read_16(ea)));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_16_di(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = MAKE_INT_16(MASK_OUT_ABOVE_16(m68ki_read_16(ea)));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_16_ix(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = MAKE_INT_16(MASK_OUT_ABOVE_16(m68ki_read_16(ea)));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_16_aw(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = MAKE_INT_16(MASK_OUT_ABOVE_16(m68ki_read_16(ea)));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_16_al(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = MAKE_INT_16(MASK_OUT_ABOVE_16(m68ki_read_16(ea)));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_PCDI_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = MAKE_INT_16(MASK_OUT_ABOVE_16(m68ki_read_16(ea)));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_16_pcix(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_PCIX_16();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = MAKE_INT_16(MASK_OUT_ABOVE_16(m68ki_read_16(ea)));
/*TODO*///			ea += 2;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_32_pi(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = AY;
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = m68ki_read_32(ea);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///	AY = ea;
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_32_ai(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = m68ki_read_32(ea);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_32_di(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = m68ki_read_32(ea);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_32_ix(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = m68ki_read_32(ea);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_32_aw(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = m68ki_read_32(ea);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_32_al(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = m68ki_read_32(ea);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_PCDI_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = m68ki_read_32(ea);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movem_er_32_pcix(void)
/*TODO*///{
/*TODO*///	uint i = 0;
/*TODO*///	uint register_list = OPER_I_16();
/*TODO*///	uint ea = EA_PCIX_32();
/*TODO*///	uint count = 0;
/*TODO*///
/*TODO*///	for(; i < 16; i++)
/*TODO*///		if(register_list & (1 << i))
/*TODO*///		{
/*TODO*///			REG_DA[i] = m68ki_read_32(ea);
/*TODO*///			ea += 4;
/*TODO*///			count++;
/*TODO*///		}
/*TODO*///
/*TODO*///	USE_CYCLES(count<<CYC_MOVEM_L);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movep_re_16(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = DX;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, MASK_OUT_ABOVE_8(src >> 8));
/*TODO*///	m68ki_write_8(ea += 2, MASK_OUT_ABOVE_8(src));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movep_re_32(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint src = DX;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, MASK_OUT_ABOVE_8(src >> 24));
/*TODO*///	m68ki_write_8(ea += 2, MASK_OUT_ABOVE_8(src >> 16));
/*TODO*///	m68ki_write_8(ea += 2, MASK_OUT_ABOVE_8(src >> 8));
/*TODO*///	m68ki_write_8(ea += 2, MASK_OUT_ABOVE_8(src));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movep_er_16(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint* r_dst = &DX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | ((m68ki_read_8(ea) << 8) + m68ki_read_8(ea + 2));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_movep_er_32(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///
/*TODO*///	DX = (m68ki_read_8(ea) << 24) + (m68ki_read_8(ea + 2) << 16)
/*TODO*///		+ (m68ki_read_8(ea + 4) << 8) + m68ki_read_8(ea + 6);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_PI_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_pi7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_A7_PI_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_PD_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_pd7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_A7_PD_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AW_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_8_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AL_8();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_8_fc(ea, REG_DFC, MASK_OUT_ABOVE_8(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_8(m68ki_read_8_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_8(REG_D[(word2 >> 12) & 7]) | m68ki_read_8_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_16_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_AI_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_16_fc(ea, REG_DFC, MASK_OUT_ABOVE_16(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_16(m68ki_read_16_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_16(REG_D[(word2 >> 12) & 7]) | m68ki_read_16_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_16_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_PI_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_16_fc(ea, REG_DFC, MASK_OUT_ABOVE_16(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_16(m68ki_read_16_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_16(REG_D[(word2 >> 12) & 7]) | m68ki_read_16_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_16_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_PD_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_16_fc(ea, REG_DFC, MASK_OUT_ABOVE_16(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_16(m68ki_read_16_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_16(REG_D[(word2 >> 12) & 7]) | m68ki_read_16_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_16_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_DI_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_16_fc(ea, REG_DFC, MASK_OUT_ABOVE_16(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_16(m68ki_read_16_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_16(REG_D[(word2 >> 12) & 7]) | m68ki_read_16_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_16_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_IX_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_16_fc(ea, REG_DFC, MASK_OUT_ABOVE_16(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_16(m68ki_read_16_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_16(REG_D[(word2 >> 12) & 7]) | m68ki_read_16_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_16_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AW_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_16_fc(ea, REG_DFC, MASK_OUT_ABOVE_16(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_16(m68ki_read_16_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_16(REG_D[(word2 >> 12) & 7]) | m68ki_read_16_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_16_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AL_16();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_16_fc(ea, REG_DFC, MASK_OUT_ABOVE_16(REG_DA[(word2 >> 12) & 15]));
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			if(BIT_F(word2))		   /* Memory to address register */
/*TODO*///			{
/*TODO*///				REG_A[(word2 >> 12) & 7] = MAKE_INT_16(m68ki_read_16_fc(ea, REG_SFC));
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to data register */
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_BELOW_16(REG_D[(word2 >> 12) & 7]) | m68ki_read_16_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_AI_32();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_32_fc(ea, REG_DFC, REG_DA[(word2 >> 12) & 15]);
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to register */
/*TODO*///			REG_DA[(word2 >> 12) & 15] = m68ki_read_32_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_32_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_PI_32();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_32_fc(ea, REG_DFC, REG_DA[(word2 >> 12) & 15]);
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to register */
/*TODO*///			REG_DA[(word2 >> 12) & 15] = m68ki_read_32_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_32_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_PD_32();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_32_fc(ea, REG_DFC, REG_DA[(word2 >> 12) & 15]);
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to register */
/*TODO*///			REG_DA[(word2 >> 12) & 15] = m68ki_read_32_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_DI_32();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_32_fc(ea, REG_DFC, REG_DA[(word2 >> 12) & 15]);
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to register */
/*TODO*///			REG_DA[(word2 >> 12) & 15] = m68ki_read_32_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AY_IX_32();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_32_fc(ea, REG_DFC, REG_DA[(word2 >> 12) & 15]);
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to register */
/*TODO*///			REG_DA[(word2 >> 12) & 15] = m68ki_read_32_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AW_32();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_32_fc(ea, REG_DFC, REG_DA[(word2 >> 12) & 15]);
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to register */
/*TODO*///			REG_DA[(word2 >> 12) & 15] = m68ki_read_32_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moves_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(FLAG_S)
/*TODO*///		{
/*TODO*///			uint word2 = OPER_I_16();
/*TODO*///			uint ea = EA_AL_32();
/*TODO*///
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			if(BIT_B(word2))		   /* Register to memory */
/*TODO*///			{
/*TODO*///				m68ki_write_32_fc(ea, REG_DFC, REG_DA[(word2 >> 12) & 15]);
/*TODO*///				if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///					USE_CYCLES(2);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Memory to register */
/*TODO*///			REG_DA[(word2 >> 12) & 15] = m68ki_read_32_fc(ea, REG_SFC);
/*TODO*///			if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///				USE_CYCLES(2);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		m68ki_exception_privilege_violation();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_moveq_32(void)
/*TODO*///{
/*TODO*///	uint res = DX = MAKE_INT_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(DY) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_AY_AI_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_AY_PI_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_AY_PD_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_AY_DI_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_AY_IX_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_AW_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_AL_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_PCDI_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_PCIX_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_muls_16_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_32(MAKE_INT_16(OPER_I_16()) * MAKE_INT_16(MASK_OUT_ABOVE_16(*r_dst)));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY) * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_AY_AI_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_AY_PI_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_AY_PD_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_AY_DI_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_AY_IX_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_AW_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_AL_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_PCDI_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_PCIX_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mulu_16_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint res = OPER_I_16() * MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_d(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = DY;
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = DY;
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_ai(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_AY_AI_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_AY_AI_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_pi(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_AY_PI_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_AY_PI_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_pd(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_AY_PD_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_AY_PD_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_di(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_AY_DI_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_AY_DI_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_ix(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_AY_IX_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_AY_IX_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_aw(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_AW_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_AW_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_al(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_AL_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_AL_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_pcdi(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_PCDI_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_PCDI_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_pcix(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_PCIX_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_PCIX_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_mull_32_i(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint64 src = OPER_I_32();
/*TODO*///		uint64 dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint64 res;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			res = (sint64)((sint32)src) * (sint64)((sint32)dst);
/*TODO*///			if(!BIT_A(word2))
/*TODO*///			{
/*TODO*///				FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///				FLAG_N = NFLAG_32(res);
/*TODO*///				FLAG_V = ((sint64)res != (sint32)res)<<7;
/*TODO*///				REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///			FLAG_N = NFLAG_64(res);
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			REG_D[word2 & 7] = (res >> 32);
/*TODO*///			REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		res = src * dst;
/*TODO*///		if(!BIT_A(word2))
/*TODO*///		{
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_V = (res > 0xffffffff)<<7;
/*TODO*///			REG_D[(word2 >> 12) & 7] = FLAG_Z;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res) | (res>>32);
/*TODO*///		FLAG_N = NFLAG_64(res);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		REG_D[word2 & 7] = (res >> 32);
/*TODO*///		REG_D[(word2 >> 12) & 7] = MASK_OUT_ABOVE_32(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint src = OPER_I_32();
/*TODO*///		uint dst = REG_D[(word2 >> 12) & 7];
/*TODO*///		uint neg = GET_MSB_32(src ^ dst);
/*TODO*///		uint src1;
/*TODO*///		uint src2;
/*TODO*///		uint dst1;
/*TODO*///		uint dst2;
/*TODO*///		uint r1;
/*TODO*///		uint r2;
/*TODO*///		uint r3;
/*TODO*///		uint r4;
/*TODO*///		uint lo;
/*TODO*///		uint hi;
/*TODO*///
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if(BIT_B(word2))			   /* signed */
/*TODO*///		{
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				src = MASK_OUT_ABOVE_32(-src);
/*TODO*///			if(GET_MSB_32(dst))
/*TODO*///				dst = MASK_OUT_ABOVE_32(-dst);
/*TODO*///		}
/*TODO*///
/*TODO*///		src1 = MASK_OUT_ABOVE_16(src);
/*TODO*///		src2 = src>>16;
/*TODO*///		dst1 = MASK_OUT_ABOVE_16(dst);
/*TODO*///		dst2 = dst>>16;
/*TODO*///
/*TODO*///
/*TODO*///		r1 = src1 * dst1;
/*TODO*///		r2 = src1 * dst2;
/*TODO*///		r3 = src2 * dst1;
/*TODO*///		r4 = src2 * dst2;
/*TODO*///
/*TODO*///		lo = r1 + (MASK_OUT_ABOVE_16(r2)<<16) + (MASK_OUT_ABOVE_16(r3)<<16);
/*TODO*///		hi = r4 + (r2>>16) + (r3>>16) + (((r1>>16) + MASK_OUT_ABOVE_16(r2) + MASK_OUT_ABOVE_16(r3)) >> 16);
/*TODO*///
/*TODO*///		if(BIT_B(word2) && neg)
/*TODO*///		{
/*TODO*///			hi = MASK_OUT_ABOVE_32((-hi) - (lo != 0));
/*TODO*///			lo = MASK_OUT_ABOVE_32(-lo);
/*TODO*///		}
/*TODO*///
/*TODO*///		if(BIT_A(word2))
/*TODO*///		{
/*TODO*///			REG_D[word2 & 7] = hi;
/*TODO*///			REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///			FLAG_N = NFLAG_32(hi);
/*TODO*///			FLAG_Z = hi | lo;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = lo;
/*TODO*///		FLAG_N = NFLAG_32(lo);
/*TODO*///		FLAG_Z = lo;
/*TODO*///		if(BIT_B(word2))
/*TODO*///			FLAG_V = (!((GET_MSB_32(lo) && hi == 0xffffffff) || (!GET_MSB_32(lo) && !hi)))<<7;
/*TODO*///		else
/*TODO*///			FLAG_V = (hi != 0) << 7;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///
/*TODO*///#endif
/*TODO*///}
}