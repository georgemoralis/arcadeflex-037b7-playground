package arcadeflex.WIP.v037b7.cpu.m68000;

import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.fprintf;
import static gr.codebb.arcadeflex.v036.cpu.m68000.m68kcpu.*;
import static gr.codebb.arcadeflex.v036.cpu.m68000.m68kcpuH.*;
import static gr.codebb.arcadeflex.v036.cpu.m68000.m68kopsH.*;

public class M68KOPNZ {
    /*TODO*///void m68k_op_nbcd_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nbcd_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(0x9a - dst - XFLAG_AS_1());
/*TODO*///
/*TODO*///	if(res != 0x9a)
/*TODO*///	{
/*TODO*///		if((res & 0x0f) == 0xa)
/*TODO*///			res = (res & 0xf0) + 0x10;
/*TODO*///
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		m68ki_write_8(ea, MASK_OUT_ABOVE_8(res));
/*TODO*///
/*TODO*///		if(res != 0)
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_SET;
/*TODO*///		FLAG_X = XFLAG_SET;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
/*TODO*///	}
/*TODO*///	FLAG_N = NFLAG_8(res);	/* officially undefined */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = *r_dst & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_16(res);
/*TODO*///	FLAG_V = (*r_dst & res)>>8;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = 0 - *r_dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_SUB_32(*r_dst, 0, res);
/*TODO*///	FLAG_V = (*r_dst & res)>>24;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_32_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_32_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_neg_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_C = FLAG_X = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_8(*r_dst) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = *r_dst & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint res = 0 - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = src & res;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(*r_dst) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = (*r_dst & res)>>8;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_16_di(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_16_al(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_16(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = (src & res)>>8;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_32(*r_dst) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(*r_dst, 0, res);
/*TODO*///	FLAG_V = (*r_dst & res)>>24;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_AI_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_32(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_32_pi(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_PI_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_32(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_32_pd(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_PD_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_32(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_32_di(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_DI_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_32(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AY_IX_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_32(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AW_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_32(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_negx_32_al(void)
/*TODO*///{
/*TODO*///	uint ea  = EA_AL_32();
/*TODO*///	uint src = m68ki_read_32(ea);
/*TODO*///	uint res = 0 - MASK_OUT_ABOVE_32(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, 0, res);
/*TODO*///	FLAG_V = (src & res)>>24;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_nop(void)
/*TODO*///{
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_not_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~*r_dst);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_not_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(~m68ki_read_8(ea));
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
/*TODO*///void m68k_op_not_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = MASK_OUT_ABOVE_16(~*r_dst);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_not_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(~m68ki_read_16(ea));
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
/*TODO*///void m68k_op_not_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(~m68ki_read_16(ea));
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
/*TODO*///void m68k_op_not_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(~m68ki_read_16(ea));
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
/*TODO*///void m68k_op_not_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(~m68ki_read_16(ea));
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
/*TODO*///void m68k_op_not_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(~m68ki_read_16(ea));
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
/*TODO*///void m68k_op_not_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(~m68ki_read_16(ea));
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
/*TODO*///void m68k_op_not_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(~m68ki_read_16(ea));
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
/*TODO*///void m68k_op_not_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint res = *r_dst = MASK_OUT_ABOVE_32(~*r_dst);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_not_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint res = MASK_OUT_ABOVE_32(~m68ki_read_32(ea));
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
/*TODO*///void m68k_op_not_32_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint res = MASK_OUT_ABOVE_32(~m68ki_read_32(ea));
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
/*TODO*///void m68k_op_not_32_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint res = MASK_OUT_ABOVE_32(~m68ki_read_32(ea));
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
/*TODO*///void m68k_op_not_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint res = MASK_OUT_ABOVE_32(~m68ki_read_32(ea));
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
/*TODO*///void m68k_op_not_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint res = MASK_OUT_ABOVE_32(~m68ki_read_32(ea));
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
/*TODO*///void m68k_op_not_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint res = MASK_OUT_ABOVE_32(~m68ki_read_32(ea));
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
/*TODO*///void m68k_op_not_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint res = MASK_OUT_ABOVE_32(~m68ki_read_32(ea));
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
/*TODO*///void m68k_op_or_er_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= MASK_OUT_ABOVE_8(DY)));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_ai(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_AY_AI_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_pi(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_AY_PI_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_pi7(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_A7_PI_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_pd(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_AY_PD_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_pd7(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_A7_PD_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_di(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_AY_DI_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_ix(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_AY_IX_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_aw(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_AW_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_al(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_AL_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_PCDI_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_pcix(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_PCIX_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_8_i(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DX |= OPER_I_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= MASK_OUT_ABOVE_16(DY)));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_AY_AI_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_AY_PI_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_AY_PD_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_di(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_AY_DI_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_AY_IX_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_AW_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_al(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_AL_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_PCDI_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_pcix(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_PCIX_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_16_i(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16((DX |= OPER_I_16()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DX |= DY;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_AY_AI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_AY_PI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_AY_PD_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_di(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_AY_DI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_AY_IX_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_AW_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_al(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_AL_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_PCDI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_pcix(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_PCIX_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_er_32_i(void)
/*TODO*///{
/*TODO*///	uint res = DX |= OPER_I_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_or_re_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DX | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_or_re_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_or_re_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_or_re_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_or_re_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_or_re_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_or_re_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_or_re_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DX | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_or_re_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint res = DX | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_or_re_32_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint res = DX | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_or_re_32_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint res = DX | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_or_re_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint res = DX | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_or_re_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint res = DX | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_or_re_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint res = DX | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_or_re_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint res = DX | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_ori_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8((DY |= OPER_I_8()));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ori_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_8_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_8_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src | m68ki_read_8(ea));
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
/*TODO*///void m68k_op_ori_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY |= OPER_I_16());
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ori_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_ori_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_ori_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_ori_16_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_ori_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_ori_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_ori_16_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint res = MASK_OUT_ABOVE_16(src | m68ki_read_16(ea));
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
/*TODO*///void m68k_op_ori_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY |= OPER_I_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ori_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint res = src | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_ori_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint res = src | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_ori_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint res = src | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_ori_32_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint res = src | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_ori_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint res = src | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_ori_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint res = src | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_ori_32_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint res = src | m68ki_read_32(ea);
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
/*TODO*///void m68k_op_ori_to_ccr_16(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(m68ki_get_ccr() | OPER_I_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ori_to_sr_16(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint src = OPER_I_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(m68ki_get_sr() | src);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pack_rr_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		/* Note: DX and DY are reversed in Motorola's docs */
/*TODO*///		uint src = DY + OPER_I_16();
/*TODO*///		uint* r_dst = &DX;
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_8(*r_dst) | ((src >> 4) & 0x00f0) | (src & 0x000f);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pack_mm_16_ax7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		/* Note: AX and AY are reversed in Motorola's docs */
/*TODO*///		uint ea_src = EA_AY_PD_8();
/*TODO*///		uint src = m68ki_read_8(ea_src);
/*TODO*///		ea_src = EA_AY_PD_8();
/*TODO*///		src = ((src << 8) | m68ki_read_8(ea_src)) + OPER_I_16();
/*TODO*///
/*TODO*///		m68ki_write_8(EA_A7_PD_8(), ((src >> 4) & 0x00f0) | (src & 0x000f));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pack_mm_16_ay7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		/* Note: AX and AY are reversed in Motorola's docs */
/*TODO*///		uint ea_src = EA_A7_PD_8();
/*TODO*///		uint src = m68ki_read_8(ea_src);
/*TODO*///		ea_src = EA_A7_PD_8();
/*TODO*///		src = ((src << 8) | m68ki_read_8(ea_src)) + OPER_I_16();
/*TODO*///
/*TODO*///		m68ki_write_8(EA_AX_PD_8(), ((src >> 4) & 0x00f0) | (src & 0x000f));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pack_mm_16_axy7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint ea_src = EA_A7_PD_8();
/*TODO*///		uint src = m68ki_read_8(ea_src);
/*TODO*///		ea_src = EA_A7_PD_8();
/*TODO*///		src = ((src << 8) | m68ki_read_8(ea_src)) + OPER_I_16();
/*TODO*///
/*TODO*///		m68ki_write_8(EA_A7_PD_8(), ((src >> 4) & 0x00f0) | (src & 0x000f));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pack_mm_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		/* Note: AX and AY are reversed in Motorola's docs */
/*TODO*///		uint ea_src = EA_AY_PD_8();
/*TODO*///		uint src = m68ki_read_8(ea_src);
/*TODO*///		ea_src = EA_AY_PD_8();
/*TODO*///		src = ((src << 8) | m68ki_read_8(ea_src)) + OPER_I_16();
/*TODO*///
/*TODO*///		m68ki_write_8(EA_AX_PD_8(), ((src >> 4) & 0x00f0) | (src & 0x000f));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pea_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///
/*TODO*///	m68ki_push_32(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pea_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///
/*TODO*///	m68ki_push_32(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pea_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///
/*TODO*///	m68ki_push_32(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pea_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///
/*TODO*///	m68ki_push_32(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pea_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///
/*TODO*///	m68ki_push_32(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pea_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_PCDI_32();
/*TODO*///
/*TODO*///	m68ki_push_32(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_pea_32_pcix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_PCIX_32();
/*TODO*///
/*TODO*///	m68ki_push_32(ea);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_reset(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		m68ki_output_reset();		   /* auto-disable (see m68kcpu.h) */
/*TODO*///		USE_CYCLES(CYC_RESET);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_s_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint shift = orig_shift & 7;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = ROR_8(src, shift);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << (9-orig_shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_s_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = ROR_16(src, shift);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << (9-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_s_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint64 src = *r_dst;
/*TODO*///	uint res = ROR_32(src, shift);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << (9-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_r_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///	uint shift = orig_shift & 7;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = ROR_8(src, shift);
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///		FLAG_C = src << (8-((shift-1)&7));
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = res;
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
/*TODO*///void m68k_op_ror_r_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///	uint shift = orig_shift & 15;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = ROR_16(src, shift);
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		FLAG_C = (src >> ((shift - 1) & 15)) << 8;
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = res;
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
/*TODO*///void m68k_op_ror_r_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///	uint shift = orig_shift & 31;
/*TODO*///	uint64 src = *r_dst;
/*TODO*///	uint res = ROR_32(src, shift);
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		*r_dst = res;
/*TODO*///		FLAG_C = (src >> ((shift - 1) & 31)) << 8;
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = res;
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
/*TODO*///void m68k_op_ror_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_16(src, 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_16(src, 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_16(src, 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_16(src, 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_16(src, 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_16(src, 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ror_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_16(src, 1);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << 8;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_s_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint shift = orig_shift & 7;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = ROL_8(src, shift);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src << orig_shift;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_s_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = ROL_16(src, shift);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> (8-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_s_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint64 src = *r_dst;
/*TODO*///	uint res = ROL_32(src, shift);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> (24-shift);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_r_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///	uint shift = orig_shift & 7;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = ROL_8(src, shift);
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		if(shift != 0)
/*TODO*///		{
/*TODO*///			*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///			FLAG_C = src << shift;
/*TODO*///			FLAG_N = NFLAG_8(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_C = (src & 1)<<8;
/*TODO*///		FLAG_N = NFLAG_8(src);
/*TODO*///		FLAG_Z = src;
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
/*TODO*///void m68k_op_rol_r_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///	uint shift = orig_shift & 15;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(ROL_16(src, shift));
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		if(shift != 0)
/*TODO*///		{
/*TODO*///			*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///			FLAG_C = (src << shift) >> 8;
/*TODO*///			FLAG_N = NFLAG_16(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_C = (src & 1)<<8;
/*TODO*///		FLAG_N = NFLAG_16(src);
/*TODO*///		FLAG_Z = src;
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
/*TODO*///void m68k_op_rol_r_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///	uint shift = orig_shift & 31;
/*TODO*///	uint64 src = *r_dst;
/*TODO*///	uint res = ROL_32(src, shift);
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		*r_dst = res;
/*TODO*///
/*TODO*///		FLAG_C = (src >> (32 - shift)) << 8;
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = res;
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
/*TODO*///void m68k_op_rol_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(ROL_16(src, 1));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(ROL_16(src, 1));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(ROL_16(src, 1));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(ROL_16(src, 1));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(ROL_16(src, 1));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(ROL_16(src, 1));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rol_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = MASK_OUT_ABOVE_16(ROL_16(src, 1));
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = src >> 7;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_s_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = ROR_9(src | (XFLAG_AS_1() << 8), shift);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res;
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_s_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = ROR_17(src | (XFLAG_AS_1() << 16), shift);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_s_32(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	uint*  r_dst = &DY;
/*TODO*///	uint   shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint64 src   = *r_dst;
/*TODO*///	uint64 res   = src | (((uint64)XFLAG_AS_1()) << 32);
/*TODO*///
/*TODO*///	res = ROR_33_64(res, shift);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 24;
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst =  res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = MASK_OUT_ABOVE_32((ROR_33(src, shift) & ~(1 << (32 - shift))) | (XFLAG_AS_1() << (32 - shift)));
/*TODO*///	uint new_x_flag = src & (1 << (shift - 1));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = (new_x_flag != 0)<<8;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_r_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		uint shift = orig_shift % 9;
/*TODO*///		uint src   = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///		uint res   = ROR_9(src | (XFLAG_AS_1() << 8), shift);
/*TODO*///
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		FLAG_C = FLAG_X = res;
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X;
/*TODO*///	FLAG_N = NFLAG_8(*r_dst);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_r_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		uint shift = orig_shift % 17;
/*TODO*///		uint src   = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///		uint res   = ROR_17(src | (XFLAG_AS_1() << 16), shift);
/*TODO*///
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		FLAG_C = FLAG_X = res >> 8;
/*TODO*///		res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X;
/*TODO*///	FLAG_N = NFLAG_16(*r_dst);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_r_32(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	uint*  r_dst = &DY;
/*TODO*///	uint   orig_shift = DX & 0x3f;
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		uint   shift = orig_shift % 33;
/*TODO*///		uint64 src   = *r_dst;
/*TODO*///		uint64 res   = src | (((uint64)XFLAG_AS_1()) << 32);
/*TODO*///
/*TODO*///		res = ROR_33_64(res, shift);
/*TODO*///
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		FLAG_C = FLAG_X = res >> 24;
/*TODO*///		res = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///		*r_dst = res;
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X;
/*TODO*///	FLAG_N = NFLAG_32(*r_dst);
/*TODO*///	FLAG_Z = *r_dst;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///	uint shift = orig_shift % 33;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = MASK_OUT_ABOVE_32((ROR_33(src, shift) & ~(1 << (32 - shift))) | (XFLAG_AS_1() << (32 - shift)));
/*TODO*///	uint new_x_flag = src & (1 << (shift - 1));
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///	if(shift != 0)
/*TODO*///	{
/*TODO*///		*r_dst = res;
/*TODO*///		FLAG_X = (new_x_flag != 0)<<8;
/*TODO*///	}
/*TODO*///	else
/*TODO*///		res = src;
/*TODO*///	FLAG_C = FLAG_X;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxr_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROR_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_s_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = ROL_9(src | (XFLAG_AS_1() << 8), shift);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res;
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_s_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = ROL_17(src | (XFLAG_AS_1() << 16), shift);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_s_32(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	uint*  r_dst = &DY;
/*TODO*///	uint   shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint64 src   = *r_dst;
/*TODO*///	uint64 res   = src | (((uint64)XFLAG_AS_1()) << 32);
/*TODO*///
/*TODO*///	res = ROL_33_64(res, shift);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 24;
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = MASK_OUT_ABOVE_32((ROL_33(src, shift) & ~(1 << (shift - 1))) | (XFLAG_AS_1() << (shift - 1)));
/*TODO*///	uint new_x_flag = src & (1 << (32 - shift));
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = (new_x_flag != 0)<<8;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_r_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		uint shift = orig_shift % 9;
/*TODO*///		uint src   = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///		uint res   = ROL_9(src | (XFLAG_AS_1() << 8), shift);
/*TODO*///
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		FLAG_C = FLAG_X = res;
/*TODO*///		res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X;
/*TODO*///	FLAG_N = NFLAG_8(*r_dst);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_r_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		uint shift = orig_shift % 17;
/*TODO*///		uint src   = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///		uint res   = ROL_17(src | (XFLAG_AS_1() << 16), shift);
/*TODO*///
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		FLAG_C = FLAG_X = res >> 8;
/*TODO*///		res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X;
/*TODO*///	FLAG_N = NFLAG_16(*r_dst);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_r_32(void)
/*TODO*///{
/*TODO*///#if M68K_USE_64_BIT
/*TODO*///
/*TODO*///	uint*  r_dst = &DY;
/*TODO*///	uint   orig_shift = DX & 0x3f;
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///	{
/*TODO*///		uint   shift = orig_shift % 33;
/*TODO*///		uint64 src   = *r_dst;
/*TODO*///		uint64 res   = src | (((uint64)XFLAG_AS_1()) << 32);
/*TODO*///
/*TODO*///		res = ROL_33_64(res, shift);
/*TODO*///
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///		FLAG_C = FLAG_X = res >> 24;
/*TODO*///		res = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///		*r_dst = res;
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X;
/*TODO*///	FLAG_N = NFLAG_32(*r_dst);
/*TODO*///	FLAG_Z = *r_dst;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint orig_shift = DX & 0x3f;
/*TODO*///	uint shift = orig_shift % 33;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = MASK_OUT_ABOVE_32((ROL_33(src, shift) & ~(1 << (shift - 1))) | (XFLAG_AS_1() << (shift - 1)));
/*TODO*///	uint new_x_flag = src & (1 << (32 - shift));
/*TODO*///
/*TODO*///	if(orig_shift != 0)
/*TODO*///		USE_CYCLES(orig_shift<<CYC_SHIFT);
/*TODO*///
/*TODO*///	if(shift != 0)
/*TODO*///	{
/*TODO*///		*r_dst = res;
/*TODO*///		FLAG_X = (new_x_flag != 0)<<8;
/*TODO*///	}
/*TODO*///	else
/*TODO*///		res = src;
/*TODO*///	FLAG_C = FLAG_X;
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROL_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROL_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROL_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROL_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROL_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROL_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_roxl_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = ROL_17(src | (XFLAG_AS_1() << 16), 1);
/*TODO*///
/*TODO*///	FLAG_C = FLAG_X = res >> 8;
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rtd_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint new_pc = m68ki_pull_32();
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_A[7] = MASK_OUT_ABOVE_32(REG_A[7] + MAKE_INT_16(OPER_I_16()));
/*TODO*///		m68ki_jump(new_pc);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rte_32(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr;
/*TODO*///		uint new_pc;
/*TODO*///		uint format_word;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///
/*TODO*///		if(CPU_TYPE_IS_000(CPU_TYPE))
/*TODO*///		{
/*TODO*///			new_sr = m68ki_pull_16();
/*TODO*///			new_pc = m68ki_pull_32();
/*TODO*///			m68ki_jump(new_pc);
/*TODO*///			m68ki_set_sr(new_sr);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		if(CPU_TYPE_IS_010(CPU_TYPE))
/*TODO*///		{
/*TODO*///			format_word = m68ki_read_16(REG_A[7]+6) >> 12;
/*TODO*///			if(format_word == 0)
/*TODO*///			{
/*TODO*///				new_sr = m68ki_pull_16();
/*TODO*///				new_pc = m68ki_pull_32();
/*TODO*///				m68ki_fake_pull_16();	/* format word */
/*TODO*///				m68ki_jump(new_pc);
/*TODO*///				m68ki_set_sr(new_sr);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			/* Not handling bus fault (9) */
/*TODO*///			m68ki_exception_format_error();
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		/* Otherwise it's 020 */
/*TODO*///rte_loop:
/*TODO*///		format_word = m68ki_read_16(REG_A[7]+6) >> 12;
/*TODO*///		switch(format_word)
/*TODO*///		{
/*TODO*///			case 0: /* Normal */
/*TODO*///				new_sr = m68ki_pull_16();
/*TODO*///				new_pc = m68ki_pull_32();
/*TODO*///				m68ki_fake_pull_16();	/* format word */
/*TODO*///				m68ki_jump(new_pc);
/*TODO*///				m68ki_set_sr(new_sr);
/*TODO*///				return;
/*TODO*///			case 1: /* Throwaway */
/*TODO*///				new_sr = m68ki_pull_16();
/*TODO*///				m68ki_fake_pull_32();	/* program counter */
/*TODO*///				m68ki_fake_pull_16();	/* format word */
/*TODO*///				m68ki_set_sr_noint(new_sr);
/*TODO*///				goto rte_loop;
/*TODO*///			case 2: /* Trap */
/*TODO*///				new_sr = m68ki_pull_16();
/*TODO*///				new_pc = m68ki_pull_32();
/*TODO*///				m68ki_fake_pull_16();	/* format word */
/*TODO*///				m68ki_fake_pull_32();	/* address */
/*TODO*///				m68ki_jump(new_pc);
/*TODO*///				m68ki_set_sr(new_sr);
/*TODO*///				return;
/*TODO*///		}
/*TODO*///		/* Not handling long or short bus fault */
/*TODO*///		m68ki_exception_format_error();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rtm_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rtr_32(void)
/*TODO*///{
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_set_ccr(m68ki_pull_16());
/*TODO*///	m68ki_jump(m68ki_pull_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_rts_32(void)
/*TODO*///{
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_jump(m68ki_pull_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sbcd_rr_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = DY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = LOW_NIBBLE(dst) - LOW_NIBBLE(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res -= 6;
/*TODO*///	res += HIGH_NIBBLE(dst) - HIGH_NIBBLE(src);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res += 0xa0;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sbcd_mm_8_ax7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint ea  = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = LOW_NIBBLE(dst) - LOW_NIBBLE(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res -= 6;
/*TODO*///	res += HIGH_NIBBLE(dst) - HIGH_NIBBLE(src);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res += 0xa0;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sbcd_mm_8_ay7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint ea  = EA_AX_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = LOW_NIBBLE(dst) - LOW_NIBBLE(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res -= 6;
/*TODO*///	res += HIGH_NIBBLE(dst) - HIGH_NIBBLE(src);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res += 0xa0;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sbcd_mm_8_axy7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint ea  = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = LOW_NIBBLE(dst) - LOW_NIBBLE(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res -= 6;
/*TODO*///	res += HIGH_NIBBLE(dst) - HIGH_NIBBLE(src);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res += 0xa0;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sbcd_mm_8(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint ea  = EA_AX_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = LOW_NIBBLE(dst) - LOW_NIBBLE(src) - XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res -= 6;
/*TODO*///	res += HIGH_NIBBLE(dst) - HIGH_NIBBLE(src);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res += 0xa0;
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_d(void)
/*TODO*///{
/*TODO*///	DY |= 0xff;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_st_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_d(void)
/*TODO*///{
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sf_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_HI())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_LS())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_CC())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_CS())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_NE())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_EQ())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_VC())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_VS())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_PL())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_MI())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_GE())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_LT())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_GT())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_d(void)
/*TODO*///{
/*TODO*///	if(COND_LE())
/*TODO*///	{
/*TODO*///		DY |= 0xff;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	DY &= 0xffffff00;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_shi_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_HI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sls_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_LS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scc_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_CC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_scs_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_CS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sne_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_NE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_seq_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_EQ() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svc_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_VC() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_svs_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_VS() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_spl_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_PL() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_smi_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_MI() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sge_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_GE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_slt_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_LT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sgt_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_GT() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sle_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), COND_LE() ? 0xff : 0);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_stop(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint new_sr = OPER_I_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		CPU_STOPPED |= STOP_LEVEL_STOP;
/*TODO*///		m68ki_set_sr(new_sr);
/*TODO*///		m68ki_remaining_cycles = 0;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_AI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_pi7(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_A7_PI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_pd7(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_DI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_IX_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AW_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AL_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCDI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCIX_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_8_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_AI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PD_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_DI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_IX_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AW_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AL_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCDI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCIX_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_16_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = DY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = AY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_AI_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PI_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PD_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_DI_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_IX_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AW_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AL_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCDI_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCIX_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_er_32_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_32_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_32_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_sub_re_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(DY));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(AY));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_AY_AI_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_AY_PI_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_AY_PD_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_AY_DI_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_AY_IX_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_AW_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_AL_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_PCDI_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_PCIX_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_16_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - MAKE_INT_16(OPER_I_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - DY);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - AY);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_AY_AI_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_AY_PI_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_AY_PD_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_AY_DI_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_AY_IX_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_AW_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_AL_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_PCDI_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_PCIX_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_suba_32_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - OPER_I_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_8_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_16_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_16_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_32_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subi_32_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_di(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_8_al(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AY;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - ((((REG_IR >> 9) - 1) & 7) + 1));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_di(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_16_al(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AY;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst - ((((REG_IR >> 9) - 1) & 7) + 1));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_di(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subq_32_al(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_rr_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_rr_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_rr_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = DY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_mm_8_ax7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint ea  = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_mm_8_ay7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint ea  = EA_AX_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_mm_8_axy7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint ea  = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_mm_8(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint ea  = EA_AX_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_mm_16(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_16();
/*TODO*///	uint ea  = EA_AX_PD_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_subx_mm_32(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_32();
/*TODO*///	uint ea  = EA_AX_PD_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = dst - src - XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_swap_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(*r_dst<<16);
/*TODO*///	*r_dst = (*r_dst>>16) | FLAG_Z;
/*TODO*///
/*TODO*///	FLAG_Z = *r_dst;
/*TODO*///	FLAG_N = NFLAG_32(*r_dst);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	FLAG_N = NFLAG_8(*r_dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	*r_dst |= 0x80;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tas_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = dst;
/*TODO*///	FLAG_N = NFLAG_8(dst);
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	m68ki_write_8(ea, dst | 0x80);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trap(void)
/*TODO*///{
/*TODO*///	/* Trap#n stacks exception frame type 0 */
/*TODO*///	m68ki_exception_trapN(EXCEPTION_TRAP_BASE + (REG_IR & 0xf));	/* HJB 990403 */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapt(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapt_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapt_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapf(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapf_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapf_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traphi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_HI())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapls(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LS())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapcc(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_CC())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapcs(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_CS())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapne(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_NE())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapeq(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_EQ())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapvc(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_VC())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapvs(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_VS())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trappl(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_PL())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapmi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_MI())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapge(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_GE())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traplt(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LT())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapgt(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_GT())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traple(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LE())
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traphi_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_HI())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapls_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LS())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapcc_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_CC())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapcs_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapne_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_NE())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapeq_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_EQ())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapvc_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_VC())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapvs_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_VS())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trappl_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_PL())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapmi_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_MI())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapge_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_GE())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traplt_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LT())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapgt_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_GT())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traple_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LE())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traphi_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_HI())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapls_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LS())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapcc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_CC())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapcs_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapne_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_NE())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapeq_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_EQ())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapvc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_VC())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapvs_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_VS())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trappl_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_PL())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapmi_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_MI())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapge_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_GE())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traplt_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LT())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapgt_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_GT())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_traple_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LE())
/*TODO*///		{
/*TODO*///			m68ki_exception_trap(EXCEPTION_TRAPV);	/* HJB 990403 */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_trapv(void)
/*TODO*///{
/*TODO*///	if(COND_VC())
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_trap(EXCEPTION_TRAPV);  /* HJB 990403 */
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_8(DY);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint res = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_PCDI_8();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_PCIX_8();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_8_i(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_I_8();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_d(void)
/*TODO*///{
/*TODO*///	uint res = MASK_OUT_ABOVE_16(DY);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_a(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = MAKE_INT_16(AY);
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_16();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_16();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_16();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_16();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_16();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_16();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_16();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_PCDI_16();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_PCIX_16();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_16_i(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_I_16();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_d(void)
/*TODO*///{
/*TODO*///	uint res = DY;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_a(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = AY;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_ai(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_AI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_pi(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_pd(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_PD_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_di(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_DI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_ix(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AY_IX_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_aw(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AW_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_al(void)
/*TODO*///{
/*TODO*///	uint res = OPER_AL_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_PCDI_32();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_PCIX_32();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_tst_32_i(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint res = OPER_I_32();
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = res;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_unlk_32_a7(void)
/*TODO*///{
/*TODO*///	REG_A[7] = m68ki_read_32(REG_A[7]);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_unlk_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AY;
/*TODO*///
/*TODO*///	REG_A[7] = *r_dst;
/*TODO*///	*r_dst = m68ki_pull_32();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_unpk_rr_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		/* Note: DX and DY are reversed in Motorola's docs */
/*TODO*///		uint src = DY;
/*TODO*///		uint* r_dst = &DX;
/*TODO*///
/*TODO*///		*r_dst = MASK_OUT_BELOW_16(*r_dst) | (((((src << 4) & 0x0f00) | (src & 0x000f)) + OPER_I_16()) & 0xffff);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_unpk_mm_16_ax7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		/* Note: AX and AY are reversed in Motorola's docs */
/*TODO*///		uint src = OPER_AY_PD_8();
/*TODO*///		uint ea_dst;
/*TODO*///
/*TODO*///		src = (((src << 4) & 0x0f00) | (src & 0x000f)) + OPER_I_16();
/*TODO*///		ea_dst = EA_A7_PD_8();
/*TODO*///		m68ki_write_8(ea_dst, (src >> 8) & 0xff);
/*TODO*///		ea_dst = EA_A7_PD_8();
/*TODO*///		m68ki_write_8(ea_dst, src & 0xff);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_unpk_mm_16_ay7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		/* Note: AX and AY are reversed in Motorola's docs */
/*TODO*///		uint src = OPER_A7_PD_8();
/*TODO*///		uint ea_dst;
/*TODO*///
/*TODO*///		src = (((src << 4) & 0x0f00) | (src & 0x000f)) + OPER_I_16();
/*TODO*///		ea_dst = EA_AX_PD_8();
/*TODO*///		m68ki_write_8(ea_dst, (src >> 8) & 0xff);
/*TODO*///		ea_dst = EA_AX_PD_8();
/*TODO*///		m68ki_write_8(ea_dst, src & 0xff);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_unpk_mm_16_axy7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint src = OPER_A7_PD_8();
/*TODO*///		uint ea_dst;
/*TODO*///
/*TODO*///		src = (((src << 4) & 0x0f00) | (src & 0x000f)) + OPER_I_16();
/*TODO*///		ea_dst = EA_A7_PD_8();
/*TODO*///		m68ki_write_8(ea_dst, (src >> 8) & 0xff);
/*TODO*///		ea_dst = EA_A7_PD_8();
/*TODO*///		m68ki_write_8(ea_dst, src & 0xff);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_unpk_mm_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		/* Note: AX and AY are reversed in Motorola's docs */
/*TODO*///		uint src = OPER_AY_PD_8();
/*TODO*///		uint ea_dst;
/*TODO*///
/*TODO*///		src = (((src << 4) & 0x0f00) | (src & 0x000f)) + OPER_I_16();
/*TODO*///		ea_dst = EA_AX_PD_8();
/*TODO*///		m68ki_write_8(ea_dst, (src >> 8) & 0xff);
/*TODO*///		ea_dst = EA_AX_PD_8();
/*TODO*///		m68ki_write_8(ea_dst, src & 0xff);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
}
