package arcadeflex.WIP.v037b7.cpu.m68000;

public class M68KOPAC {
    /*TODO*///void m68k_op_1010(void)
/*TODO*///{
/*TODO*///	m68ki_exception_1010();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_1111(void)
/*TODO*///{
/*TODO*///	m68ki_exception_1111();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_abcd_rr_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = DY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = LOW_NIBBLE(src) + LOW_NIBBLE(dst) + XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res += 6;
/*TODO*///	res += HIGH_NIBBLE(src) + HIGH_NIBBLE(dst);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res -= 0xa0;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_abcd_mm_8_ax7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint ea  = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = LOW_NIBBLE(src) + LOW_NIBBLE(dst) + XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res += 6;
/*TODO*///	res += HIGH_NIBBLE(src) + HIGH_NIBBLE(dst);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res -= 0xa0;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_abcd_mm_8_ay7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint ea  = EA_AX_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = LOW_NIBBLE(src) + LOW_NIBBLE(dst) + XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res += 6;
/*TODO*///	res += HIGH_NIBBLE(src) + HIGH_NIBBLE(dst);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res -= 0xa0;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_abcd_mm_8_axy7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint ea  = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = LOW_NIBBLE(src) + LOW_NIBBLE(dst) + XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res += 6;
/*TODO*///	res += HIGH_NIBBLE(src) + HIGH_NIBBLE(dst);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res -= 0xa0;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_abcd_mm_8(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint ea  = EA_AX_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = LOW_NIBBLE(src) + LOW_NIBBLE(dst) + XFLAG_AS_1();
/*TODO*///
/*TODO*///	if(res > 9)
/*TODO*///		res += 6;
/*TODO*///	res += HIGH_NIBBLE(src) + HIGH_NIBBLE(dst);
/*TODO*///	FLAG_X = FLAG_C = (res > 0x99) << 8;
/*TODO*///	if(FLAG_C)
/*TODO*///		res -= 0xa0;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res); /* officially undefined */
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_AI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_pi7(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_A7_PI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_pd7(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_DI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_IX_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AW_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AL_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCDI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCIX_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_8_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_AI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PD_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_DI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_IX_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AW_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AL_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCDI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCIX_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_16_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = DY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = AY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_AI_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PI_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_PD_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_DI_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AY_IX_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AW_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_AL_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCDI_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_PCIX_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_er_32_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_32_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_32_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_add_re_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint src = DX;
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(DY));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(AY));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_AY_AI_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_AY_PI_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_AY_PD_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_AY_DI_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_AY_IX_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_AW_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_AL_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_PCDI_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_PCIX_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_16_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + MAKE_INT_16(OPER_I_16()));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + DY);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + AY);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_ai(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_AY_AI_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_pi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_AY_PI_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_pd(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_AY_PD_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_di(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_AY_DI_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_ix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_AY_IX_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_aw(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_AW_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_al(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_AL_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_PCDI_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_pcix(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_PCIX_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_adda_32_i(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AX;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + OPER_I_32());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_8_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_16_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_16_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_32_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addi_32_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_di(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_8_al(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AY;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + (((REG_IR >> 9) - 1) & 7) + 1);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_di(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_16_al(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	*r_dst = FLAG_Z;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_a(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &AY;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_ABOVE_32(*r_dst + (((REG_IR >> 9) - 1) & 7) + 1);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_di(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addq_32_al(void)
/*TODO*///{
/*TODO*///	uint src = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst;
/*TODO*///
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///
/*TODO*///	m68ki_write_32(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_rr_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_rr_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_rr_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DX;
/*TODO*///	uint src = DY;
/*TODO*///	uint dst = *r_dst;
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_mm_8_ax7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint ea  = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_mm_8_ay7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint ea  = EA_AX_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_mm_8_axy7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint ea  = EA_A7_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_mm_8(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint ea  = EA_AX_PD_8();
/*TODO*///	uint dst = m68ki_read_8(ea);
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_V = VFLAG_ADD_8(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_8(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_mm_16(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_16();
/*TODO*///	uint ea  = EA_AX_PD_16();
/*TODO*///	uint dst = m68ki_read_16(ea);
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_V = VFLAG_ADD_16(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_16(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_addx_mm_32(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_32();
/*TODO*///	uint ea  = EA_AX_PD_32();
/*TODO*///	uint dst = m68ki_read_32(ea);
/*TODO*///	uint res = src + dst + XFLAG_AS_1();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_V = VFLAG_ADD_32(src, dst, res);
/*TODO*///	FLAG_X = FLAG_C = CFLAG_ADD_32(src, dst, res);
/*TODO*///
/*TODO*///	res = MASK_OUT_ABOVE_32(res);
/*TODO*///	if(res)
/*TODO*///		FLAG_Z = ZFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_d(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (DY | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_ai(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_AY_AI_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_pi(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_AY_PI_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_pi7(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_A7_PI_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_pd(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_AY_PD_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_pd7(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_A7_PD_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_di(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_AY_DI_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_ix(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_AY_IX_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_aw(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_AW_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_al(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_AL_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_pcdi(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_PCDI_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_pcix(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_PCIX_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_8_i(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DX &= (OPER_I_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_d(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (DY | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_ai(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_AY_AI_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_pi(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_AY_PI_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_pd(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_AY_PD_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_di(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_AY_DI_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_ix(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_AY_IX_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_aw(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_AW_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_al(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_AL_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_pcdi(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_PCDI_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_pcix(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_PCIX_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_16_i(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DX &= (OPER_I_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_d(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= DY;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_ai(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_AY_AI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_pi(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_AY_PI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_pd(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_AY_PD_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_di(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_AY_DI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_ix(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_AY_IX_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_aw(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_AW_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_al(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_AL_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_pcdi(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_PCDI_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_pcix(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_PCIX_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_er_32_i(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DX &= OPER_I_32();
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint res = DX & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///
/*TODO*///	m68ki_write_8(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint res = DX & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint res = DX & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint res = DX & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint res = DX & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint res = DX & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint res = DX & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint res = DX & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///
/*TODO*///	m68ki_write_16(ea, FLAG_Z);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_32_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint res = DX & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_32_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint res = DX & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_32_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint res = DX & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_32_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint res = DX & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_32_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint res = DX & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_32_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint res = DX & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_and_re_32_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint res = DX & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_d(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(DY &= (OPER_I_8() | 0xffffff00));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_8_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint res = src & m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_8(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_16_d(void)
/*TODO*///{
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(DY &= (OPER_I_16() | 0xffff0000));
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint res = src & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint res = src & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint res = src & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_16_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint res = src & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint res = src & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint res = src & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_16_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint res = src & m68ki_read_16(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_32_d(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DY &= (OPER_I_32());
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(FLAG_Z);
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_AI_32();
/*TODO*///	uint res = src & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PI_32();
/*TODO*///	uint res = src & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_PD_32();
/*TODO*///	uint res = src & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_32_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_DI_32();
/*TODO*///	uint res = src & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AY_IX_32();
/*TODO*///	uint res = src & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AW_32();
/*TODO*///	uint res = src & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_32_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint ea = EA_AL_32();
/*TODO*///	uint res = src & m68ki_read_32(ea);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///
/*TODO*///	m68ki_write_32(ea, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_to_ccr_16(void)
/*TODO*///{
/*TODO*///	m68ki_set_ccr(m68ki_get_ccr() & OPER_I_16());
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_andi_to_sr_16(void)
/*TODO*///{
/*TODO*///	if(FLAG_S)
/*TODO*///	{
/*TODO*///		uint src = OPER_I_16();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_set_sr(m68ki_get_sr() & src);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_privilege_violation();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_s_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	if(GET_MSB_8(src))
/*TODO*///		res |= m68ki_shift_8_table[shift];
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_X = FLAG_C = src << (9-shift);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_s_16(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_16(*r_dst);
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	if(GET_MSB_16(src))
/*TODO*///		res |= m68ki_shift_16_table[shift];
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_X = FLAG_C = src << (9-shift);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_s_32(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = *r_dst;
/*TODO*///	uint res = src >> shift;
/*TODO*///
/*TODO*///	if(GET_MSB_32(src))
/*TODO*///		res |= m68ki_shift_32_table[shift];
/*TODO*///
/*TODO*///	*r_dst = res;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_X = FLAG_C = src << (9-shift);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_r_8(void)
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
/*TODO*///		if(shift < 8)
/*TODO*///		{
/*TODO*///			if(GET_MSB_8(src))
/*TODO*///				res |= m68ki_shift_8_table[shift];
/*TODO*///
/*TODO*///			*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///			FLAG_X = FLAG_C = src << (9-shift);
/*TODO*///			FLAG_N = NFLAG_8(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		if(GET_MSB_8(src))
/*TODO*///		{
/*TODO*///			*r_dst |= 0xff;
/*TODO*///			FLAG_C = CFLAG_SET;
/*TODO*///			FLAG_X = XFLAG_SET;
/*TODO*///			FLAG_N = NFLAG_SET;
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst &= 0xffffff00;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
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
/*TODO*///void m68k_op_asr_r_16(void)
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
/*TODO*///		if(shift < 16)
/*TODO*///		{
/*TODO*///			if(GET_MSB_16(src))
/*TODO*///				res |= m68ki_shift_16_table[shift];
/*TODO*///
/*TODO*///			*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///
/*TODO*///			FLAG_C = FLAG_X = (src >> (shift - 1))<<8;
/*TODO*///			FLAG_N = NFLAG_16(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		if(GET_MSB_16(src))
/*TODO*///		{
/*TODO*///			*r_dst |= 0xffff;
/*TODO*///			FLAG_C = CFLAG_SET;
/*TODO*///			FLAG_X = XFLAG_SET;
/*TODO*///			FLAG_N = NFLAG_SET;
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst &= 0xffff0000;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
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
/*TODO*///void m68k_op_asr_r_32(void)
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
/*TODO*///			if(GET_MSB_32(src))
/*TODO*///				res |= m68ki_shift_32_table[shift];
/*TODO*///
/*TODO*///			*r_dst = res;
/*TODO*///
/*TODO*///			FLAG_C = FLAG_X = (src >> (shift - 1))<<8;
/*TODO*///			FLAG_N = NFLAG_32(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		if(GET_MSB_32(src))
/*TODO*///		{
/*TODO*///			*r_dst = 0xffffffff;
/*TODO*///			FLAG_C = CFLAG_SET;
/*TODO*///			FLAG_X = XFLAG_SET;
/*TODO*///			FLAG_N = NFLAG_SET;
/*TODO*///			FLAG_Z = ZFLAG_CLEAR;
/*TODO*///			FLAG_V = VFLAG_CLEAR;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst = 0;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///		FLAG_X = XFLAG_CLEAR;
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
/*TODO*///void m68k_op_asr_16_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	if(GET_MSB_16(src))
/*TODO*///		res |= 0x8000;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_16_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	if(GET_MSB_16(src))
/*TODO*///		res |= 0x8000;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_16_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	if(GET_MSB_16(src))
/*TODO*///		res |= 0x8000;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_16_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	if(GET_MSB_16(src))
/*TODO*///		res |= 0x8000;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_16_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	if(GET_MSB_16(src))
/*TODO*///		res |= 0x8000;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_16_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	if(GET_MSB_16(src))
/*TODO*///		res |= 0x8000;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asr_16_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_16();
/*TODO*///	uint src = m68ki_read_16(ea);
/*TODO*///	uint res = src >> 1;
/*TODO*///
/*TODO*///	if(GET_MSB_16(src))
/*TODO*///		res |= 0x8000;
/*TODO*///
/*TODO*///	m68ki_write_16(ea, res);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = FLAG_X = src << 8;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_s_8(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint shift = (((REG_IR >> 9) - 1) & 7) + 1;
/*TODO*///	uint src = MASK_OUT_ABOVE_8(*r_dst);
/*TODO*///	uint res = MASK_OUT_ABOVE_8(src << shift);
/*TODO*///
/*TODO*///	*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///
/*TODO*///	FLAG_X = FLAG_C = src << shift;
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = res;
/*TODO*///	src &= m68ki_shift_8_table[shift + 1];
/*TODO*///	FLAG_V = (!(src == 0 || (src == m68ki_shift_8_table[shift + 1] && shift < 8)))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_s_16(void)
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
/*TODO*///	src &= m68ki_shift_16_table[shift + 1];
/*TODO*///	FLAG_V = (!(src == 0 || src == m68ki_shift_16_table[shift + 1]))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_s_32(void)
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
/*TODO*///	src &= m68ki_shift_32_table[shift + 1];
/*TODO*///	FLAG_V = (!(src == 0 || src == m68ki_shift_32_table[shift + 1]))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_r_8(void)
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
/*TODO*///		if(shift < 8)
/*TODO*///		{
/*TODO*///			*r_dst = MASK_OUT_BELOW_8(*r_dst) | res;
/*TODO*///			FLAG_X = FLAG_C = src << shift;
/*TODO*///			FLAG_N = NFLAG_8(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			src &= m68ki_shift_8_table[shift + 1];
/*TODO*///			FLAG_V = (!(src == 0 || src == m68ki_shift_8_table[shift + 1]))<<7;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst &= 0xffffff00;
/*TODO*///		FLAG_X = FLAG_C = ((shift == 8 ? src & 1 : 0))<<8;
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = (!(src == 0))<<7;
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
/*TODO*///void m68k_op_asl_r_16(void)
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
/*TODO*///		if(shift < 16)
/*TODO*///		{
/*TODO*///			*r_dst = MASK_OUT_BELOW_16(*r_dst) | res;
/*TODO*///			FLAG_X = FLAG_C = (src << shift) >> 8;
/*TODO*///			FLAG_N = NFLAG_16(res);
/*TODO*///			FLAG_Z = res;
/*TODO*///			src &= m68ki_shift_16_table[shift + 1];
/*TODO*///			FLAG_V = (!(src == 0 || src == m68ki_shift_16_table[shift + 1]))<<7;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst &= 0xffff0000;
/*TODO*///		FLAG_X = FLAG_C = ((shift == 16 ? src & 1 : 0))<<8;
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = (!(src == 0))<<7;
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
/*TODO*///void m68k_op_asl_r_32(void)
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
/*TODO*///			src &= m68ki_shift_32_table[shift + 1];
/*TODO*///			FLAG_V = (!(src == 0 || src == m68ki_shift_32_table[shift + 1]))<<7;
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		*r_dst = 0;
/*TODO*///		FLAG_X = FLAG_C = ((shift == 32 ? src & 1 : 0))<<8;
/*TODO*///		FLAG_N = NFLAG_CLEAR;
/*TODO*///		FLAG_Z = ZFLAG_SET;
/*TODO*///		FLAG_V = (!(src == 0))<<7;
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
/*TODO*///void m68k_op_asl_16_ai(void)
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
/*TODO*///	src &= 0xc000;
/*TODO*///	FLAG_V = (!(src == 0 || src == 0xc000))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_16_pi(void)
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
/*TODO*///	src &= 0xc000;
/*TODO*///	FLAG_V = (!(src == 0 || src == 0xc000))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_16_pd(void)
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
/*TODO*///	src &= 0xc000;
/*TODO*///	FLAG_V = (!(src == 0 || src == 0xc000))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_16_di(void)
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
/*TODO*///	src &= 0xc000;
/*TODO*///	FLAG_V = (!(src == 0 || src == 0xc000))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_16_ix(void)
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
/*TODO*///	src &= 0xc000;
/*TODO*///	FLAG_V = (!(src == 0 || src == 0xc000))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_16_aw(void)
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
/*TODO*///	src &= 0xc000;
/*TODO*///	FLAG_V = (!(src == 0 || src == 0xc000))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_asl_16_al(void)
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
/*TODO*///	src &= 0xc000;
/*TODO*///	FLAG_V = (!(src == 0 || src == 0xc000))<<7;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bhi_8(void)
/*TODO*///{
/*TODO*///	if(COND_HI())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bls_8(void)
/*TODO*///{
/*TODO*///	if(COND_LS())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bcc_8(void)
/*TODO*///{
/*TODO*///	if(COND_CC())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bcs_8(void)
/*TODO*///{
/*TODO*///	if(COND_CS())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bne_8(void)
/*TODO*///{
/*TODO*///	if(COND_NE())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_beq_8(void)
/*TODO*///{
/*TODO*///	if(COND_EQ())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bvc_8(void)
/*TODO*///{
/*TODO*///	if(COND_VC())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bvs_8(void)
/*TODO*///{
/*TODO*///	if(COND_VS())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bpl_8(void)
/*TODO*///{
/*TODO*///	if(COND_PL())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bmi_8(void)
/*TODO*///{
/*TODO*///	if(COND_MI())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bge_8(void)
/*TODO*///{
/*TODO*///	if(COND_GE())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_blt_8(void)
/*TODO*///{
/*TODO*///	if(COND_LT())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bgt_8(void)
/*TODO*///{
/*TODO*///	if(COND_GT())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ble_8(void)
/*TODO*///{
/*TODO*///	if(COND_LE())
/*TODO*///	{
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_B);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bhi_16(void)
/*TODO*///{
/*TODO*///	if(COND_HI())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bls_16(void)
/*TODO*///{
/*TODO*///	if(COND_LS())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bcc_16(void)
/*TODO*///{
/*TODO*///	if(COND_CC())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bcs_16(void)
/*TODO*///{
/*TODO*///	if(COND_CS())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bne_16(void)
/*TODO*///{
/*TODO*///	if(COND_NE())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_beq_16(void)
/*TODO*///{
/*TODO*///	if(COND_EQ())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bvc_16(void)
/*TODO*///{
/*TODO*///	if(COND_VC())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bvs_16(void)
/*TODO*///{
/*TODO*///	if(COND_VS())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bpl_16(void)
/*TODO*///{
/*TODO*///	if(COND_PL())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bmi_16(void)
/*TODO*///{
/*TODO*///	if(COND_MI())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bge_16(void)
/*TODO*///{
/*TODO*///	if(COND_GE())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_blt_16(void)
/*TODO*///{
/*TODO*///	if(COND_LT())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bgt_16(void)
/*TODO*///{
/*TODO*///	if(COND_GT())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ble_16(void)
/*TODO*///{
/*TODO*///	if(COND_LE())
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_16();
/*TODO*///		REG_PC -= 2;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_16(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	REG_PC += 2;
/*TODO*///	USE_CYCLES(CYC_BCC_NOTAKE_W);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bhi_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_HI())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bls_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LS())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bcc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_CC())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bcs_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bne_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_NE())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_beq_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_EQ())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bvc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_VC())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bvs_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_VS())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bpl_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_PL())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bmi_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_MI())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bge_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_GE())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_blt_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LT())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bgt_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_GT())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_ble_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		if(COND_LE())
/*TODO*///		{
/*TODO*///			uint offset = OPER_I_32();
/*TODO*///			REG_PC -= 4;
/*TODO*///			m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///			m68ki_branch_32(offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		REG_PC += 4;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint mask = 1 << (DX & 0x1f);
/*TODO*///
/*TODO*///	FLAG_Z = *r_dst & mask;
/*TODO*///	*r_dst ^= mask;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_r_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint mask = 1 << (OPER_I_8() & 0x1f);
/*TODO*///
/*TODO*///	FLAG_Z = *r_dst & mask;
/*TODO*///	*r_dst ^= mask;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_ai(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_pi(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_pi7(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_pd(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_pd7(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_di(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_ix(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_aw(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bchg_s_8_al(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src ^ mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint mask = 1 << (DX & 0x1f);
/*TODO*///
/*TODO*///	FLAG_Z = *r_dst & mask;
/*TODO*///	*r_dst &= ~mask;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_r_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint mask = 1 << (OPER_I_8() & 0x1f);
/*TODO*///
/*TODO*///	FLAG_Z = *r_dst & mask;
/*TODO*///	*r_dst &= ~mask;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_ai(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_pi(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_pi7(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_pd(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_pd7(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_di(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_ix(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_aw(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bclr_s_8_al(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src & ~mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfchg_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint* data = &DY;
/*TODO*///		uint64 mask;
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = REG_D[offset&7];
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		offset &= 31;
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask = ROR_32(mask, offset);
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(*data<<offset);
/*TODO*///		FLAG_Z = *data & mask;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		*data ^= mask;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfchg_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long ^ mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte ^ mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfchg_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long ^ mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte ^ mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfchg_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long ^ mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte ^ mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfchg_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long ^ mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte ^ mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfchg_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long ^ mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte ^ mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfclr_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint* data = &DY;
/*TODO*///		uint64 mask;
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = REG_D[offset&7];
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///
/*TODO*///		offset &= 31;
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask = ROR_32(mask, offset);
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(*data<<offset);
/*TODO*///		FLAG_Z = *data & mask;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		*data &= ~mask;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfclr_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long & ~mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte & ~mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfclr_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long & ~mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte & ~mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfclr_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long & ~mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte & ~mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfclr_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long & ~mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte & ~mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfclr_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long & ~mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte & ~mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfexts_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint64 data = DY;
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = REG_D[offset&7];
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		offset &= 31;
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = ROL_32(data, offset);
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data = MAKE_INT_32(data) >> (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfexts_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  = MAKE_INT_32(data) >> (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfexts_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  = MAKE_INT_32(data) >> (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfexts_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  = MAKE_INT_32(data) >> (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfexts_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  = MAKE_INT_32(data) >> (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfexts_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  = MAKE_INT_32(data) >> (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfexts_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_PCDI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  = MAKE_INT_32(data) >> (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfexts_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_PCIX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  = MAKE_INT_32(data) >> (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfextu_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint64 data = DY;
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = REG_D[offset&7];
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		offset &= 31;
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = ROL_32(data, offset);
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data >>= 32 - width;
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfextu_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///		offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfextu_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///		offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfextu_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///		offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfextu_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///		offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfextu_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///		offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfextu_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_PCDI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///		offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfextu_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint ea = EA_PCIX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///		offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<offset);
/*TODO*///
/*TODO*///		if((offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		REG_D[(word2 >> 12) & 7] = data;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfffo_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint64 data = DY;
/*TODO*///		uint bit;
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = REG_D[offset&7];
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		offset &= 31;
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = ROL_32(data, offset);
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data >>= 32 - width;
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		for(bit = 1<<(width-1);bit && !(data & bit);bit>>= 1)
/*TODO*///			offset++;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = offset;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfffo_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		sint local_offset;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint bit;
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		local_offset = offset % 8;
/*TODO*///		if(local_offset < 0)
/*TODO*///		{
/*TODO*///			local_offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<local_offset);
/*TODO*///
/*TODO*///		if((local_offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << local_offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		for(bit = 1<<(width-1);bit && !(data & bit);bit>>= 1)
/*TODO*///			offset++;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = offset;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfffo_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		sint local_offset;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint bit;
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		local_offset = offset % 8;
/*TODO*///		if(local_offset < 0)
/*TODO*///		{
/*TODO*///			local_offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<local_offset);
/*TODO*///
/*TODO*///		if((local_offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << local_offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		for(bit = 1<<(width-1);bit && !(data & bit);bit>>= 1)
/*TODO*///			offset++;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = offset;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfffo_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		sint local_offset;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint bit;
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		local_offset = offset % 8;
/*TODO*///		if(local_offset < 0)
/*TODO*///		{
/*TODO*///			local_offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<local_offset);
/*TODO*///
/*TODO*///		if((local_offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << local_offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		for(bit = 1<<(width-1);bit && !(data & bit);bit>>= 1)
/*TODO*///			offset++;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = offset;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfffo_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		sint local_offset;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint bit;
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		local_offset = offset % 8;
/*TODO*///		if(local_offset < 0)
/*TODO*///		{
/*TODO*///			local_offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<local_offset);
/*TODO*///
/*TODO*///		if((local_offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << local_offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		for(bit = 1<<(width-1);bit && !(data & bit);bit>>= 1)
/*TODO*///			offset++;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = offset;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfffo_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		sint local_offset;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint bit;
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		local_offset = offset % 8;
/*TODO*///		if(local_offset < 0)
/*TODO*///		{
/*TODO*///			local_offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<local_offset);
/*TODO*///
/*TODO*///		if((local_offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << local_offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		for(bit = 1<<(width-1);bit && !(data & bit);bit>>= 1)
/*TODO*///			offset++;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = offset;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfffo_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		sint local_offset;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint bit;
/*TODO*///		uint ea = EA_PCDI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		local_offset = offset % 8;
/*TODO*///		if(local_offset < 0)
/*TODO*///		{
/*TODO*///			local_offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<local_offset);
/*TODO*///
/*TODO*///		if((local_offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << local_offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		for(bit = 1<<(width-1);bit && !(data & bit);bit>>= 1)
/*TODO*///			offset++;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = offset;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfffo_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		sint local_offset;
/*TODO*///		uint width = word2;
/*TODO*///		uint data;
/*TODO*///		uint bit;
/*TODO*///		uint ea = EA_PCIX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		local_offset = offset % 8;
/*TODO*///		if(local_offset < 0)
/*TODO*///		{
/*TODO*///			local_offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		data = m68ki_read_32(ea);
/*TODO*///		data = MASK_OUT_ABOVE_32(data<<local_offset);
/*TODO*///
/*TODO*///		if((local_offset+width) > 32)
/*TODO*///			data |= (m68ki_read_8(ea+4) << local_offset) >> 8;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(data);
/*TODO*///		data  >>= (32 - width);
/*TODO*///
/*TODO*///		FLAG_Z = data;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		for(bit = 1<<(width-1);bit && !(data & bit);bit>>= 1)
/*TODO*///			offset++;
/*TODO*///
/*TODO*///		REG_D[(word2>>12)&7] = offset;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfins_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint* data = &DY;
/*TODO*///		uint64 mask;
/*TODO*///		uint64 insert = REG_D[(word2>>12)&7];
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = REG_D[offset&7];
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///
/*TODO*///		offset &= 31;
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask = ROR_32(mask, offset);
/*TODO*///
/*TODO*///		insert = MASK_OUT_ABOVE_32(insert << (32 - width));
/*TODO*///		FLAG_N = NFLAG_32(insert);
/*TODO*///		FLAG_Z = insert;
/*TODO*///		insert = ROR_32(insert, offset);
/*TODO*///
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		*data &= ~mask;
/*TODO*///		*data |= insert;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfins_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint insert_base = REG_D[(word2>>12)&7];
/*TODO*///		uint insert_long;
/*TODO*///		uint insert_byte;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		insert_base = MASK_OUT_ABOVE_32(insert_base << (32 - width));
/*TODO*///		FLAG_N = NFLAG_32(insert_base);
/*TODO*///		FLAG_Z = insert_base;
/*TODO*///		insert_long = insert_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, (data_long & ~mask_long) | insert_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			insert_byte = MASK_OUT_ABOVE_8(insert_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, (data_byte & ~mask_byte) | insert_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfins_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint insert_base = REG_D[(word2>>12)&7];
/*TODO*///		uint insert_long;
/*TODO*///		uint insert_byte;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		insert_base = MASK_OUT_ABOVE_32(insert_base << (32 - width));
/*TODO*///		FLAG_N = NFLAG_32(insert_base);
/*TODO*///		FLAG_Z = insert_base;
/*TODO*///		insert_long = insert_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, (data_long & ~mask_long) | insert_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			insert_byte = MASK_OUT_ABOVE_8(insert_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, (data_byte & ~mask_byte) | insert_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfins_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint insert_base = REG_D[(word2>>12)&7];
/*TODO*///		uint insert_long;
/*TODO*///		uint insert_byte;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		insert_base = MASK_OUT_ABOVE_32(insert_base << (32 - width));
/*TODO*///		FLAG_N = NFLAG_32(insert_base);
/*TODO*///		FLAG_Z = insert_base;
/*TODO*///		insert_long = insert_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, (data_long & ~mask_long) | insert_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			insert_byte = MASK_OUT_ABOVE_8(insert_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, (data_byte & ~mask_byte) | insert_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfins_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint insert_base = REG_D[(word2>>12)&7];
/*TODO*///		uint insert_long;
/*TODO*///		uint insert_byte;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		insert_base = MASK_OUT_ABOVE_32(insert_base << (32 - width));
/*TODO*///		FLAG_N = NFLAG_32(insert_base);
/*TODO*///		FLAG_Z = insert_base;
/*TODO*///		insert_long = insert_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, (data_long & ~mask_long) | insert_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			insert_byte = MASK_OUT_ABOVE_8(insert_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, (data_byte & ~mask_byte) | insert_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfins_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint insert_base = REG_D[(word2>>12)&7];
/*TODO*///		uint insert_long;
/*TODO*///		uint insert_byte;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		insert_base = MASK_OUT_ABOVE_32(insert_base << (32 - width));
/*TODO*///		FLAG_N = NFLAG_32(insert_base);
/*TODO*///		FLAG_Z = insert_base;
/*TODO*///		insert_long = insert_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, (data_long & ~mask_long) | insert_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			insert_byte = MASK_OUT_ABOVE_8(insert_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, (data_byte & ~mask_byte) | insert_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfset_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint* data = &DY;
/*TODO*///		uint64 mask;
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = REG_D[offset&7];
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///
/*TODO*///		offset &= 31;
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask = ROR_32(mask, offset);
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(*data<<offset);
/*TODO*///		FLAG_Z = *data & mask;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		*data |= mask;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfset_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long | mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte | mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfset_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long | mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte | mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfset_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long | mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte | mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfset_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long | mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte | mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bfset_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = NFLAG_32(data_long << offset);
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		m68ki_write_32(ea, data_long | mask_long);
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///			m68ki_write_8(ea+4, data_byte | mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bftst_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint* data = &DY;
/*TODO*///		uint64 mask;
/*TODO*///
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = REG_D[offset&7];
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///
/*TODO*///		offset &= 31;
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask = ROR_32(mask, offset);
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(*data<<offset);
/*TODO*///		FLAG_Z = *data & mask;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bftst_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = ((data_long & (0x80000000 >> offset))<<offset)>>24;
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bftst_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = ((data_long & (0x80000000 >> offset))<<offset)>>24;
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bftst_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = ((data_long & (0x80000000 >> offset))<<offset)>>24;
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bftst_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = ((data_long & (0x80000000 >> offset))<<offset)>>24;
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bftst_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = ((data_long & (0x80000000 >> offset))<<offset)>>24;
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bftst_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_PCDI_8();
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = ((data_long & (0x80000000 >> offset))<<offset)>>24;
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bftst_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		sint offset = (word2>>6)&31;
/*TODO*///		uint width = word2;
/*TODO*///		uint mask_base;
/*TODO*///		uint data_long;
/*TODO*///		uint mask_long;
/*TODO*///		uint data_byte = 0;
/*TODO*///		uint mask_byte = 0;
/*TODO*///		uint ea = EA_PCIX_8();
/*TODO*///
/*TODO*///		if(BIT_B(word2))
/*TODO*///			offset = MAKE_INT_32(REG_D[offset&7]);
/*TODO*///		if(BIT_5(word2))
/*TODO*///			width = REG_D[width&7];
/*TODO*///
/*TODO*///		/* Offset is signed so we have to use ugly math =( */
/*TODO*///		ea += offset / 8;
/*TODO*///		offset %= 8;
/*TODO*///		if(offset < 0)
/*TODO*///		{
/*TODO*///			offset += 8;
/*TODO*///			ea--;
/*TODO*///		}
/*TODO*///		width = ((width-1) & 31) + 1;
/*TODO*///
/*TODO*///
/*TODO*///		mask_base = MASK_OUT_ABOVE_32(0xffffffff << (32 - width));
/*TODO*///		mask_long = mask_base >> offset;
/*TODO*///
/*TODO*///		data_long = m68ki_read_32(ea);
/*TODO*///		FLAG_N = ((data_long & (0x80000000 >> offset))<<offset)>>24;
/*TODO*///		FLAG_Z = data_long & mask_long;
/*TODO*///		FLAG_V = VFLAG_CLEAR;
/*TODO*///		FLAG_C = CFLAG_CLEAR;
/*TODO*///
/*TODO*///		if((width + offset) > 32)
/*TODO*///		{
/*TODO*///			mask_byte = MASK_OUT_ABOVE_8(mask_base);
/*TODO*///			data_byte = m68ki_read_8(ea+4);
/*TODO*///			FLAG_Z |= (data_byte & mask_byte);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bkpt(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_010_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		m68ki_bkpt_ack(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE) ? REG_IR & 7 : 0);	/* auto-disable (see m68kcpu.h) */
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bra_8(void)
/*TODO*///{
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bra_16(void)
/*TODO*///{
/*TODO*///	uint offset = OPER_I_16();
/*TODO*///	REG_PC -= 2;
/*TODO*///	m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_branch_16(offset);
/*TODO*///	if(REG_PC == REG_PPC)
/*TODO*///		USE_ALL_CYCLES();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bra_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_32();
/*TODO*///		REG_PC -= 4;
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_branch_32(offset);
/*TODO*///		if(REG_PC == REG_PPC)
/*TODO*///			USE_ALL_CYCLES();
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint mask = 1 << (DX & 0x1f);
/*TODO*///
/*TODO*///	FLAG_Z = *r_dst & mask;
/*TODO*///	*r_dst |= mask;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_ai(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_pi(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_pi7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_pd(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_pd7(void)
/*TODO*///{
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_di(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_ix(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_aw(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_r_8_al(void)
/*TODO*///{
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///	uint mask = 1 << (DX & 7);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_32_d(void)
/*TODO*///{
/*TODO*///	uint* r_dst = &DY;
/*TODO*///	uint mask = 1 << (OPER_I_8() & 0x1f);
/*TODO*///
/*TODO*///	FLAG_Z = *r_dst & mask;
/*TODO*///	*r_dst |= mask;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_ai(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_AI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_pi(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_pi7(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_A7_PI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_pd(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_pd7(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_A7_PD_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_di(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_DI_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_ix(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AY_IX_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_aw(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AW_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bset_s_8_al(void)
/*TODO*///{
/*TODO*///	uint mask = 1 << (OPER_I_8() & 7);
/*TODO*///	uint ea = EA_AL_8();
/*TODO*///	uint src = m68ki_read_8(ea);
/*TODO*///
/*TODO*///	FLAG_Z = src & mask;
/*TODO*///	m68ki_write_8(ea, src | mask);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bsr_8(void)
/*TODO*///{
/*TODO*///	m68ki_trace_t0();				   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	m68ki_branch_8(MASK_OUT_ABOVE_8(REG_IR));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bsr_16(void)
/*TODO*///{
/*TODO*///	uint offset = OPER_I_16();
/*TODO*///	m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///	m68ki_push_32(REG_PC);
/*TODO*///	REG_PC -= 2;
/*TODO*///	m68ki_branch_16(offset);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_bsr_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint offset = OPER_I_32();
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		m68ki_push_32(REG_PC);
/*TODO*///		REG_PC -= 4;
/*TODO*///		m68ki_branch_32(offset);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_32_d(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DY & (1 << (DX & 0x1f));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_ai(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_AY_AI_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_pi(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_AY_PI_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_pi7(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_A7_PI_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_pd(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_AY_PD_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_pd7(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_A7_PD_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_di(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_AY_DI_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_ix(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_AY_IX_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_aw(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_AW_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_al(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_AL_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_pcdi(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_PCDI_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_pcix(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_PCIX_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_r_8_i(void)
/*TODO*///{
/*TODO*///	FLAG_Z = OPER_I_8() & (1 << (DX & 7));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_32_d(void)
/*TODO*///{
/*TODO*///	FLAG_Z = DY & (1 << (OPER_I_8() & 0x1f));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_ai(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_AY_AI_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_pi(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_AY_PI_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_pi7(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_A7_PI_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_pd(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_AY_PD_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_pd7(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_A7_PD_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_di(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_AY_DI_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_ix(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_AY_IX_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_aw(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_AW_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_al(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_AL_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_PCDI_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_btst_s_8_pcix(void)
/*TODO*///{
/*TODO*///	uint bit = OPER_I_8() & 7;
/*TODO*///
/*TODO*///	FLAG_Z = OPER_PCIX_8() & (1 << bit);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_callm_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint ea = EA_AY_AI_32();
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_PC += 2;
/*TODO*///(void)ea;	/* just to avoid an 'unused variable' warning */
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_callm_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint ea = EA_AY_DI_32();
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_PC += 2;
/*TODO*///(void)ea;	/* just to avoid an 'unused variable' warning */
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_callm_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint ea = EA_AY_IX_32();
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_PC += 2;
/*TODO*///(void)ea;	/* just to avoid an 'unused variable' warning */
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_callm_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint ea = EA_AW_32();
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_PC += 2;
/*TODO*///(void)ea;	/* just to avoid an 'unused variable' warning */
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_callm_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint ea = EA_AL_32();
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_PC += 2;
/*TODO*///(void)ea;	/* just to avoid an 'unused variable' warning */
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_callm_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint ea = EA_PCDI_32();
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_PC += 2;
/*TODO*///(void)ea;	/* just to avoid an 'unused variable' warning */
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_callm_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_020_VARIANT(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint ea = EA_PCIX_32();
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		REG_PC += 2;
/*TODO*///(void)ea;	/* just to avoid an 'unused variable' warning */
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_PI_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_pi7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_A7_PI_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_PD_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_pd7(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_A7_PD_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_8_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///		uint dest = m68ki_read_8(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_8(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_8(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_8(ea, MASK_OUT_ABOVE_8(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_16_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_AI_16();
/*TODO*///		uint dest = m68ki_read_16(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_16(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_16(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_16_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_PI_16();
/*TODO*///		uint dest = m68ki_read_16(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_16(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_16(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_16_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_PD_16();
/*TODO*///		uint dest = m68ki_read_16(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_16(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_16(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_16_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_DI_16();
/*TODO*///		uint dest = m68ki_read_16(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_16(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_16(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_16_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_IX_16();
/*TODO*///		uint dest = m68ki_read_16(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_16(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_16(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_16_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AW_16();
/*TODO*///		uint dest = m68ki_read_16(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_16(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_16(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_16_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AL_16();
/*TODO*///		uint dest = m68ki_read_16(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - MASK_OUT_ABOVE_16(*compare);
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = MASK_OUT_BELOW_16(*compare) | dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_16(ea, MASK_OUT_ABOVE_16(REG_D[(word2 >> 6) & 7]));
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_AI_32();
/*TODO*///		uint dest = m68ki_read_32(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - *compare;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(*compare, dest, res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_32(ea, REG_D[(word2 >> 6) & 7]);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_32_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_PI_32();
/*TODO*///		uint dest = m68ki_read_32(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - *compare;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(*compare, dest, res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_32(ea, REG_D[(word2 >> 6) & 7]);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_32_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_PD_32();
/*TODO*///		uint dest = m68ki_read_32(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - *compare;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(*compare, dest, res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_32(ea, REG_D[(word2 >> 6) & 7]);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_DI_32();
/*TODO*///		uint dest = m68ki_read_32(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - *compare;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(*compare, dest, res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_32(ea, REG_D[(word2 >> 6) & 7]);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AY_IX_32();
/*TODO*///		uint dest = m68ki_read_32(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - *compare;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(*compare, dest, res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_32(ea, REG_D[(word2 >> 6) & 7]);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AW_32();
/*TODO*///		uint dest = m68ki_read_32(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - *compare;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(*compare, dest, res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_32(ea, REG_D[(word2 >> 6) & 7]);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint ea = EA_AL_32();
/*TODO*///		uint dest = m68ki_read_32(ea);
/*TODO*///		uint* compare = &REG_D[word2 & 7];
/*TODO*///		uint res = dest - *compare;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(*compare, dest, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(*compare, dest, res);
/*TODO*///
/*TODO*///		if(COND_NE())
/*TODO*///			*compare = dest;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			USE_CYCLES(3);
/*TODO*///			m68ki_write_32(ea, REG_D[(word2 >> 6) & 7]);
/*TODO*///		}
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas2_16(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_32();
/*TODO*///		uint* compare1 = &REG_D[(word2 >> 16) & 7];
/*TODO*///		uint ea1 = REG_DA[(word2 >> 28) & 15];
/*TODO*///		uint dest1 = m68ki_read_16(ea1);
/*TODO*///		uint res1 = dest1 - MASK_OUT_ABOVE_16(*compare1);
/*TODO*///		uint* compare2 = &REG_D[word2 & 7];
/*TODO*///		uint ea2 = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint dest2 = m68ki_read_16(ea2);
/*TODO*///		uint res2;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_16(res1);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res1);
/*TODO*///		FLAG_V = VFLAG_SUB_16(*compare1, dest1, res1);
/*TODO*///		FLAG_C = CFLAG_16(res1);
/*TODO*///
/*TODO*///		if(COND_EQ())
/*TODO*///		{
/*TODO*///			res2 = dest2 - MASK_OUT_ABOVE_16(*compare2);
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_16(res2);
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_16(res2);
/*TODO*///			FLAG_V = VFLAG_SUB_16(*compare2, dest2, res2);
/*TODO*///			FLAG_C = CFLAG_16(res2);
/*TODO*///
/*TODO*///			if(COND_EQ())
/*TODO*///			{
/*TODO*///				USE_CYCLES(3);
/*TODO*///				m68ki_write_16(ea1, REG_D[(word2 >> 22) & 7]);
/*TODO*///				m68ki_write_16(ea2, REG_D[(word2 >> 6) & 7]);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///		}
/*TODO*///		*compare1 = BIT_1F(word2) ? MAKE_INT_16(dest1) : MASK_OUT_BELOW_16(*compare1) | dest1;
/*TODO*///		*compare2 = BIT_F(word2) ? MAKE_INT_16(dest2) : MASK_OUT_BELOW_16(*compare2) | dest2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cas2_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_32();
/*TODO*///		uint* compare1 = &REG_D[(word2 >> 16) & 7];
/*TODO*///		uint ea1 = REG_DA[(word2 >> 28) & 15];
/*TODO*///		uint dest1 = m68ki_read_32(ea1);
/*TODO*///		uint res1 = dest1 - *compare1;
/*TODO*///		uint* compare2 = &REG_D[word2 & 7];
/*TODO*///		uint ea2 = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint dest2 = m68ki_read_32(ea2);
/*TODO*///		uint res2;
/*TODO*///
/*TODO*///		m68ki_trace_t0();			   /* auto-disable (see m68kcpu.h) */
/*TODO*///		FLAG_N = NFLAG_32(res1);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res1);
/*TODO*///		FLAG_V = VFLAG_SUB_32(*compare1, dest1, res1);
/*TODO*///		FLAG_C = CFLAG_SUB_32(*compare1, dest1, res1);
/*TODO*///
/*TODO*///		if(COND_EQ())
/*TODO*///		{
/*TODO*///			res2 = dest2 - *compare2;
/*TODO*///
/*TODO*///			FLAG_N = NFLAG_32(res2);
/*TODO*///			FLAG_Z = MASK_OUT_ABOVE_32(res2);
/*TODO*///			FLAG_V = VFLAG_SUB_32(*compare2, dest2, res2);
/*TODO*///			FLAG_C = CFLAG_SUB_32(*compare2, dest2, res2);
/*TODO*///
/*TODO*///			if(COND_EQ())
/*TODO*///			{
/*TODO*///				USE_CYCLES(3);
/*TODO*///				m68ki_write_32(ea1, REG_D[(word2 >> 22) & 7]);
/*TODO*///				m68ki_write_32(ea2, REG_D[(word2 >> 6) & 7]);
/*TODO*///				return;
/*TODO*///			}
/*TODO*///		}
/*TODO*///		*compare1 = dest1;
/*TODO*///		*compare2 = dest2;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_d(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(DY);
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_ai(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_AY_AI_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_pi(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_AY_PI_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_pd(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_AY_PD_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_di(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_AY_DI_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_ix(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_AY_IX_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_aw(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_AW_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_al(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_AL_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_pcdi(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_PCDI_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_pcix(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_PCIX_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_16_i(void)
/*TODO*///{
/*TODO*///	sint src = MAKE_INT_16(DX);
/*TODO*///	sint bound = MAKE_INT_16(OPER_I_16());
/*TODO*///
/*TODO*///	if(src >= 0 && src <= bound)
/*TODO*///	{
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	FLAG_N = (src < 0)<<7;
/*TODO*///	m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_d(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(DY);
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_AY_AI_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_pi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_AY_PI_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_pd(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_AY_PD_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_AY_DI_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_AY_IX_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_AW_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_AL_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_PCDI_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_PCIX_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk_32_i(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		sint src = MAKE_INT_32(DX);
/*TODO*///		sint bound = MAKE_INT_32(OPER_I_32());
/*TODO*///
/*TODO*///		if(src >= 0 && src <= bound)
/*TODO*///		{
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		FLAG_N = (src < 0)<<7;
/*TODO*///		m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_8_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_AI_8();
/*TODO*///		uint lower_bound = m68ki_read_8(ea);
/*TODO*///		uint upper_bound = m68ki_read_8(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_8(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_8_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_DI_8();
/*TODO*///		uint lower_bound = m68ki_read_8(ea);
/*TODO*///		uint upper_bound = m68ki_read_8(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_8(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_8_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_IX_8();
/*TODO*///		uint lower_bound = m68ki_read_8(ea);
/*TODO*///		uint upper_bound = m68ki_read_8(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_8(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_8_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AW_8();
/*TODO*///		uint lower_bound = m68ki_read_8(ea);
/*TODO*///		uint upper_bound = m68ki_read_8(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_8(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_8_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AL_8();
/*TODO*///		uint lower_bound = m68ki_read_8(ea);
/*TODO*///		uint upper_bound = m68ki_read_8(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_8(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_8_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_PCDI_8();
/*TODO*///		uint lower_bound = m68ki_read_8(ea);
/*TODO*///		uint upper_bound = m68ki_read_8(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_8(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_8_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_PCIX_8();
/*TODO*///		uint lower_bound = m68ki_read_8(ea);
/*TODO*///		uint upper_bound = m68ki_read_8(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_8(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_16_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_AI_16();
/*TODO*///		uint lower_bound = m68ki_read_16(ea);
/*TODO*///		uint upper_bound = m68ki_read_16(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_16(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_16_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_DI_16();
/*TODO*///		uint lower_bound = m68ki_read_16(ea);
/*TODO*///		uint upper_bound = m68ki_read_16(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_16(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_16_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_IX_16();
/*TODO*///		uint lower_bound = m68ki_read_16(ea);
/*TODO*///		uint upper_bound = m68ki_read_16(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_16(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_16_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AW_16();
/*TODO*///		uint lower_bound = m68ki_read_16(ea);
/*TODO*///		uint upper_bound = m68ki_read_16(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_16(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_16_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AL_16();
/*TODO*///		uint lower_bound = m68ki_read_16(ea);
/*TODO*///		uint upper_bound = m68ki_read_16(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_16(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_16_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_PCDI_16();
/*TODO*///		uint lower_bound = m68ki_read_16(ea);
/*TODO*///		uint upper_bound = m68ki_read_16(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_16(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_16_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_PCIX_16();
/*TODO*///		uint lower_bound = m68ki_read_16(ea);
/*TODO*///		uint upper_bound = m68ki_read_16(ea + 1);
/*TODO*///
/*TODO*///		if(!BIT_F(word2))
/*TODO*///			compare = MAKE_INT_16(compare);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_16(FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_32_ai(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_AI_32();
/*TODO*///		uint lower_bound = m68ki_read_32(ea);
/*TODO*///		uint upper_bound = m68ki_read_32(ea + 1);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(lower_bound, compare, FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(compare, upper_bound, FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_32_di(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_DI_32();
/*TODO*///		uint lower_bound = m68ki_read_32(ea);
/*TODO*///		uint upper_bound = m68ki_read_32(ea + 1);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(lower_bound, compare, FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(compare, upper_bound, FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_32_ix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AY_IX_32();
/*TODO*///		uint lower_bound = m68ki_read_32(ea);
/*TODO*///		uint upper_bound = m68ki_read_32(ea + 1);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(lower_bound, compare, FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(compare, upper_bound, FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_32_aw(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AW_32();
/*TODO*///		uint lower_bound = m68ki_read_32(ea);
/*TODO*///		uint upper_bound = m68ki_read_32(ea + 1);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(lower_bound, compare, FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(compare, upper_bound, FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_32_al(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_AL_32();
/*TODO*///		uint lower_bound = m68ki_read_32(ea);
/*TODO*///		uint upper_bound = m68ki_read_32(ea + 1);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(lower_bound, compare, FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(compare, upper_bound, FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_PCDI_32();
/*TODO*///		uint lower_bound = m68ki_read_32(ea);
/*TODO*///		uint upper_bound = m68ki_read_32(ea + 1);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(lower_bound, compare, FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(compare, upper_bound, FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_chk2_cmp2_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint word2 = OPER_I_16();
/*TODO*///		uint compare = REG_DA[(word2 >> 12) & 15];
/*TODO*///		uint ea = EA_PCIX_32();
/*TODO*///		uint lower_bound = m68ki_read_32(ea);
/*TODO*///		uint upper_bound = m68ki_read_32(ea + 1);
/*TODO*///
/*TODO*///		FLAG_C = compare - lower_bound;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(lower_bound, compare, FLAG_C);
/*TODO*///		if(COND_CS())
/*TODO*///		{
/*TODO*///			if(BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///
/*TODO*///		FLAG_C = upper_bound - compare;
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(FLAG_C);
/*TODO*///		FLAG_C = CFLAG_SUB_32(compare, upper_bound, FLAG_C);
/*TODO*///		if(COND_CS() && BIT_B(word2))
/*TODO*///				m68ki_exception_trap(EXCEPTION_CHK);
/*TODO*///
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_d(void)
/*TODO*///{
/*TODO*///	DY &= 0xffffff00;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_AI_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PI_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_pi7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PI_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_PD_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_pd7(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_A7_PD_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_DI_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AY_IX_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AW_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_8_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_8(EA_AL_8(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_16_d(void)
/*TODO*///{
/*TODO*///	DY &= 0xffff0000;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_16_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_16(EA_AY_AI_16(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_16_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_16(EA_AY_PI_16(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_16_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_16(EA_AY_PD_16(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_16_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_16(EA_AY_DI_16(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_16_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_16(EA_AY_IX_16(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_16_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_16(EA_AW_16(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_16_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_16(EA_AL_16(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_32_d(void)
/*TODO*///{
/*TODO*///	DY = 0;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_32_ai(void)
/*TODO*///{
/*TODO*///	m68ki_write_32(EA_AY_AI_32(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_32_pi(void)
/*TODO*///{
/*TODO*///	m68ki_write_32(EA_AY_PI_32(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_32_pd(void)
/*TODO*///{
/*TODO*///	m68ki_write_32(EA_AY_PD_32(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_32_di(void)
/*TODO*///{
/*TODO*///	m68ki_write_32(EA_AY_DI_32(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_32_ix(void)
/*TODO*///{
/*TODO*///	m68ki_write_32(EA_AY_IX_32(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_32_aw(void)
/*TODO*///{
/*TODO*///	m68ki_write_32(EA_AW_32(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_clr_32_al(void)
/*TODO*///{
/*TODO*///	m68ki_write_32(EA_AL_32(), 0);
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_CLEAR;
/*TODO*///	FLAG_V = VFLAG_CLEAR;
/*TODO*///	FLAG_C = CFLAG_CLEAR;
/*TODO*///	FLAG_Z = ZFLAG_SET;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_d(void)
/*TODO*///{
/*TODO*///	uint src = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_AI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PD_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_DI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_IX_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AW_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AL_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_pcdi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_PCDI_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_pcix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_PCIX_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_8_i(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_d(void)
/*TODO*///{
/*TODO*///	uint src = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_a(void)
/*TODO*///{
/*TODO*///	uint src = MASK_OUT_ABOVE_16(AY);
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_AI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_DI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_IX_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AW_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AL_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_PCDI_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_pcix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_PCIX_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_16_i(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DX);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_d(void)
/*TODO*///{
/*TODO*///	uint src = DY;
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_a(void)
/*TODO*///{
/*TODO*///	uint src = AY;
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_AI_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PI_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_DI_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_IX_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AW_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AL_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_PCDI_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_pcix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_PCIX_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmp_32_i(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = DX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_d(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(DY);
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_a(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(AY);
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_AY_AI_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_AY_PI_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_AY_PD_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_di(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_AY_DI_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_AY_IX_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_AW_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_al(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_AL_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_pcdi(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_PCDI_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_pcix(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_PCIX_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_16_i(void)
/*TODO*///{
/*TODO*///	uint src = MAKE_INT_16(OPER_I_16());
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_d(void)
/*TODO*///{
/*TODO*///	uint src = DY;
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_a(void)
/*TODO*///{
/*TODO*///	uint src = AY;
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_AI_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PI_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PD_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_DI_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_IX_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AW_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AL_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_pcdi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_PCDI_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_pcix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_PCIX_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpa_32_i(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = AX;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_d(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = MASK_OUT_ABOVE_8(DY);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_AY_AI_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_AY_PI_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_pi7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_A7_PI_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_AY_PD_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_pd7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_A7_PD_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_AY_DI_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_AY_IX_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_AW_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_8();
/*TODO*///	uint dst = OPER_AL_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint src = OPER_I_8();
/*TODO*///		uint dst = OPER_PCDI_8();
/*TODO*///		uint res = dst - src;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_8_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint src = OPER_I_8();
/*TODO*///		uint dst = OPER_PCIX_8();
/*TODO*///		uint res = dst - src;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_8(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///		FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///		FLAG_C = CFLAG_8(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_d(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = MASK_OUT_ABOVE_16(DY);
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = OPER_AY_AI_16();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = OPER_AY_PI_16();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = OPER_AY_PD_16();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = OPER_AY_DI_16();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = OPER_AY_IX_16();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = OPER_AW_16();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_16();
/*TODO*///	uint dst = OPER_AL_16();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint src = OPER_I_16();
/*TODO*///		uint dst = OPER_PCDI_16();
/*TODO*///		uint res = dst - src;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_16_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint src = OPER_I_16();
/*TODO*///		uint dst = OPER_PCIX_16();
/*TODO*///		uint res = dst - src;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_16(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///		FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///		FLAG_C = CFLAG_16(res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_d(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = DY;
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_ai(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = OPER_AY_AI_32();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_pi(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = OPER_AY_PI_32();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_pd(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = OPER_AY_PD_32();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_di(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = OPER_AY_DI_32();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_ix(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = OPER_AY_IX_32();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_aw(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = OPER_AW_32();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_al(void)
/*TODO*///{
/*TODO*///	uint src = OPER_I_32();
/*TODO*///	uint dst = OPER_AL_32();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_pcdi(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint src = OPER_I_32();
/*TODO*///		uint dst = OPER_PCDI_32();
/*TODO*///		uint res = dst - src;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpi_32_pcix(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		uint src = OPER_I_32();
/*TODO*///		uint dst = OPER_PCIX_32();
/*TODO*///		uint res = dst - src;
/*TODO*///
/*TODO*///		FLAG_N = NFLAG_32(res);
/*TODO*///		FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///		FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///		FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_illegal();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpm_8_ax7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PI_8();
/*TODO*///	uint dst = OPER_A7_PI_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpm_8_ay7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PI_8();
/*TODO*///	uint dst = OPER_AX_PI_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpm_8_axy7(void)
/*TODO*///{
/*TODO*///	uint src = OPER_A7_PI_8();
/*TODO*///	uint dst = OPER_A7_PI_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpm_8(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PI_8();
/*TODO*///	uint dst = OPER_AX_PI_8();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_8(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_8(res);
/*TODO*///	FLAG_V = VFLAG_SUB_8(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_8(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpm_16(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PI_16();
/*TODO*///	uint dst = OPER_AX_PI_16();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_16(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_16(res);
/*TODO*///	FLAG_V = VFLAG_SUB_16(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_16(res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cmpm_32(void)
/*TODO*///{
/*TODO*///	uint src = OPER_AY_PI_32();
/*TODO*///	uint dst = OPER_AX_PI_32();
/*TODO*///	uint res = dst - src;
/*TODO*///
/*TODO*///	FLAG_N = NFLAG_32(res);
/*TODO*///	FLAG_Z = MASK_OUT_ABOVE_32(res);
/*TODO*///	FLAG_V = VFLAG_SUB_32(src, dst, res);
/*TODO*///	FLAG_C = CFLAG_SUB_32(src, dst, res);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cpbcc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_1111();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cpdbcc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_1111();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cpgen_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_1111();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cpscc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_1111();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void m68k_op_cptrapcc_32(void)
/*TODO*///{
/*TODO*///	if(CPU_TYPE_IS_EC020_PLUS(CPU_TYPE))
/*TODO*///	{
/*TODO*///		M68K_DO_LOG((M68K_LOG_FILEHANDLE "%s at %08x: called unimplemented instruction %04x (%s)\n",
/*TODO*///					 m68ki_cpu_names[CPU_TYPE], ADDRESS_68K(REG_PC - 2), REG_IR,
/*TODO*///					 m68k_disassemble_quick(ADDRESS_68K(REG_PC - 2))));
/*TODO*///		return;
/*TODO*///	}
/*TODO*///	m68ki_exception_1111();
/*TODO*///}
/*TODO*///
}
