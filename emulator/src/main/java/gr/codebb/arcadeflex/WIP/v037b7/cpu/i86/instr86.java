/*
 * ported to v0.37b7
 *
 */
package gr.codebb.arcadeflex.WIP.v037b7.cpu.i86;

import static gr.codebb.arcadeflex.WIP.v037b7.cpu.i86.I86H.*;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.i86.eaH.EO;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.i86.eaH.GetEA;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.i86.i186.*;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.i86.modrmH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.cpu.i86.table86H.i86_instruction;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.common.libc.expressions.NOT;

public class instr86 {

    public static abstract interface InstructionPtr {

        public abstract void handler();
    }

    static void i86_interrupt(int int_num) {
        int/*unsigned*/ dest_seg, dest_off;
        int/*WORD*/ ip = (I.pc - I.base[CS]) & 0xFFFF;

        if (int_num == -1) {
            int_num = I.irq_callback.handler(0);
        }

        /*TODO*///#ifdef I286
/*TODO*///	if (PM) {
/*TODO*///		i286_interrupt_descriptor(int_num);
/*TODO*///	} else {
/*TODO*///#endif
        dest_off = ReadWord(int_num * 4);
        dest_seg = ReadWord(int_num * 4 + 2);
        i86_pushf.handler();
        I.TF = I.IF = 0;
        PUSH(I.sregs[CS]);
        PUSH(ip);
        I.sregs[CS] = dest_seg & 0xFFFF;
        I.base[CS] = SegBase(CS);
        I.pc = (I.base[CS] + dest_off) & AMASK;
        /*TODO*///#ifdef I286
/*TODO*///	}
/*TODO*///#endif
        change_pc20(I.pc);

        I.extra_cycles += cycles.exception;
    }

    /*TODO*///
/*TODO*///static void PREFIX86(_trap)(void)
/*TODO*///{
/*TODO*///	PREFIX(_instruction)[FETCHOP]();
/*TODO*///	PREFIX(_interrupt)(1);
/*TODO*///}
/*TODO*///#endif
/*TODO*///
/*TODO*///#ifndef I186
    public static void i86_rotate_shift_Byte(int ModRM, int count) {
        int src = /*(unsigned)*/ GetRMByte(ModRM);
        int dst = src;
        if (count == 0) {
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.rot_reg_base : cycles.rot_m8_base;
        } else if (count == 1) {
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.rot_reg_1 : cycles.rot_m8_1;

            switch (ModRM & 0x38) {
                case 0x00: /* ROL eb,1 */ {
                    I.CarryVal = src & 0x80;
                    dst = (src << 1) + CF();
                    PutbackRMByte(ModRM, dst & 0xFF);//unsigned??
                    I.OverVal = (src ^ dst) & 0x80;
                }
                break;
                case 0x08: /* ROR eb,1 */ {
                    I.CarryVal = src & 0x01;
                    dst = ((CF() << 8) + src) >>> 1;//unsigned?
                    PutbackRMByte(ModRM, dst & 0xFF);//unsigned??
                    I.OverVal = (src ^ dst) & 0x80;
                }
                break;
                case 0x10: /* RCL eb,1 */ {
                    dst = (src << 1) + CF();
                    PutbackRMByte(ModRM, dst & 0xFF);
                    SetCFB(dst);
                    I.OverVal = (src ^ dst) & 0x80;
                }
                break;
                case 0x18: /* RCR eb,1 */ {
                    dst = ((CF() << 8) + src) >>> 1;//unsigned shift???
                    PutbackRMByte(ModRM, dst & 0xFF);//unsigned?
                    I.CarryVal = src & 0x01;
                    I.OverVal = (src ^ dst) & 0x80;
                }
                break;
                case 0x20:
                case 0x30: {
                    dst = src << 1;
                    PutbackRMByte(ModRM, dst & 0xFF);
                    SetCFB(dst);
                    I.OverVal = (src ^ dst) & 0x80;
                    I.AuxVal = 1;
                    SetSZPF_Byte(dst);
                }
                break;
                case 0x28: {
                    /* SHR eb,1 */

                    dst = src >>> 1; //unsigned shift????
                    PutbackRMByte(ModRM, dst & 0xff);//unsigned??
                    I.CarryVal = src & 0x01;
                    I.OverVal = src & 0x80;
                    I.AuxVal = 1;
                    SetSZPF_Byte(dst);
                }
                break;
                case 0x38: /* SAR eb,1 */ {
                    dst = ((byte) src) >>> 1;
                    PutbackRMByte(ModRM, dst);
                    I.CarryVal = src & 0x01;
                    I.OverVal = 0;
                    I.AuxVal = 1;
                    SetSZPF_Byte(dst);
                }
                break;
                default:
                    System.out.println("rot1 unsupported 0x " + (Integer.toHexString((ModRM & 0x38))));
                    throw new UnsupportedOperationException("unsupported");
            }
        } else {
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.rot_reg_base + cycles.rot_reg_bit : cycles.rot_m8_base + cycles.rot_m8_bit;

            switch (ModRM & 0x38) {
                case 0x00:
                    /* ROL eb,count */
                    for (; count > 0; count--) {
                        I.CarryVal = dst & 0x80;
                        dst = (dst << 1) + CF();
                    }
                    PutbackRMByte(ModRM, dst & 0xFF);
                    break;
                case 0x08:
                    /* ROR eb,count */
                    for (; count > 0; count--) {
                        I.CarryVal = dst & 0x01;
                        dst = (dst >>> 1) + (CF() << 7);
                    }
                    PutbackRMByte(ModRM, dst & 0xFF);
                    break;
                case 0x10:
                    /* RCL eb,count */
                    for (; count > 0; count--) {
                        dst = (dst << 1) + CF();
                        SetCFB(dst);
                    }
                    PutbackRMByte(ModRM, dst & 0xFF);
                    break;
                case 0x18:
                    /* RCR eb,count */
                    for (; count > 0; count--) {
                        dst = (CF() << 8) + dst;
                        I.CarryVal = dst & 0x01;
                        dst >>>= 1;
                    }
                    PutbackRMByte(ModRM, dst & 0xFF);
                    break;
                case 0x20:
                case 0x30: /* SHL eb,count */ {
                    dst <<= count;
                    SetCFB(dst);
                    I.AuxVal = 1;
                    SetSZPF_Byte(dst);
                    PutbackRMByte(ModRM, dst & 0xFF);
                }
                break;
                case 0x28: {
                    dst >>>= count - 1;//unsigned shift?
                    I.CarryVal = dst & 0x1;//unsigned shift?
                    dst >>>= 1;//unsigned shift?
                    SetSZPF_Byte(dst);
                    I.AuxVal = 1;
                    PutbackRMByte(ModRM, dst & 0xFF);
                }
                break;
                case 0x38: /* SAR eb,count */ {
                    dst = ((byte) dst) >>> (count - 1);
                    I.CarryVal = dst & 0x1;
                    dst = ((byte) (dst & 0xFF)) >>> 1;
                    SetSZPF_Byte(dst);
                    I.AuxVal = 1;
                    PutbackRMByte(ModRM, dst & 0xFF);
                }
                break;
                default:
                    System.out.println("rot unsupported 0x " + (Integer.toHexString((ModRM & 0x38))));
                    throw new UnsupportedOperationException("unsupported");
            }
        }
    }

    static void i86_rotate_shift_Word(int/*unsigned*/ ModRM, int/*unsigned*/ count) {
        int src = GetRMWord(ModRM);
        int dst = src;

        if (count == 0) {
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.rot_reg_base : cycles.rot_m16_base;
        } else if (count == 1) {
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.rot_reg_1 : cycles.rot_m16_1;

            switch (ModRM & 0x38) {
                case 0x00: /* ROL ew,1 */ {
                    I.CarryVal = src & 0x8000;
                    dst = (src << 1) + CF();
                    PutbackRMWord(ModRM, dst & 0xFFFF);//unsigned?
                    I.OverVal = (src ^ dst) & 0x8000;
                }
                break;
                /*TODO*///		case 0x08:	/* ROR ew,1 */
/*TODO*///			I.CarryVal = src & 0x01;
/*TODO*///			dst = ((CF<<16)+src) >> 1;
/*TODO*///			PutbackRMWord(ModRM,dst);
/*TODO*///			I.OverVal = (src^dst)&0x8000;
/*TODO*///			break;
                case 0x10: /* RCL ew,1 */ {
                    dst = (src << 1) + CF();
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                    SetCFW(dst);
                    I.OverVal = (src ^ dst) & 0x8000;
                }
                break;
                case 0x18: /* RCR ew,1 */ {
                    dst = ((CF() << 16) + src) >>> 1;
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                    I.CarryVal = src & 0x01;
                    I.OverVal = (src ^ dst) & 0x8000;
                }
                break;
                case 0x20:
                case 0x30: {
                    dst = src << 1;
                    PutbackRMWord(ModRM, dst);
                    SetCFW(dst);
                    I.OverVal = (src ^ dst) & 0x8000;
                    I.AuxVal = 1;
                    SetSZPF_Word(dst);
                }
                break;
                case 0x28: {
                    dst = src >>> 1;
                    PutbackRMWord(ModRM, dst);
                    I.CarryVal = src & 0x01;
                    I.OverVal = src & 0x8000;
                    I.AuxVal = 1;
                    SetSZPF_Word(dst);
                }
                break;
                case 0x38: /* SAR ew,1 */ {
                    dst = ((short) src) >>> 1;
                    PutbackRMWord(ModRM, dst);
                    I.CarryVal = src & 0x01;
                    I.OverVal = 0;
                    I.AuxVal = 1;
                    SetSZPF_Word(dst);
                }
                break;
                default:
                    System.out.println("rot1 unsupported 0x " + (Integer.toHexString((ModRM & 0x38))));
                    throw new UnsupportedOperationException("unsupported");
            }
        } else {
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.rot_reg_base + cycles.rot_reg_bit : cycles.rot_m8_base + cycles.rot_m16_bit;
            switch (ModRM & 0x38) {
                case 0x00: /* ROL ew,count */ {
                    for (; count > 0; count--) {
                        I.CarryVal = dst & 0x8000;
                        dst = (dst << 1) + CF();
                    }
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                }
                break;
                /*TODO*///		case 0x08:	/* ROR ew,count */
/*TODO*///			for (; count > 0; count--)
/*TODO*///			{
/*TODO*///				I.CarryVal = dst & 0x01;
/*TODO*///				dst = (dst >> 1) + (CF << 15);
/*TODO*///			}
/*TODO*///			PutbackRMWord(ModRM,dst);
/*TODO*///			break;
/*TODO*///		case 0x10:  /* RCL ew,count */
/*TODO*///			for (; count > 0; count--)
/*TODO*///			{
/*TODO*///				dst = (dst << 1) + CF;
/*TODO*///				SetCFW(dst);
/*TODO*///			}
/*TODO*///			PutbackRMWord(ModRM,dst);
/*TODO*///			break;
/*TODO*///		case 0x18:	/* RCR ew,count */
/*TODO*///			for (; count > 0; count--)
/*TODO*///			{
/*TODO*///				dst = dst + (CF << 16);
/*TODO*///				I.CarryVal = dst & 0x01;
/*TODO*///				dst >>= 1;
/*TODO*///			}
/*TODO*///			PutbackRMWord(ModRM,dst);
/*TODO*///			break;
                case 0x20:
                case 0x30: {
                    /* SHL ew,count */
                    dst <<= count;
                    SetCFW(dst);
                    I.AuxVal = 1;
                    SetSZPF_Word(dst);
                    PutbackRMWord(ModRM, dst);
                }
                break;
                case 0x28: /* SHR ew,count */ {
                    dst >>>= count - 1;
                    I.CarryVal = dst & 0x1;
                    dst >>>= 1;
                    SetSZPF_Word(dst);
                    I.AuxVal = 1;
                    PutbackRMWord(ModRM, dst);
                }
                break;
                case 0x38: /* SAR ew,count */ {
                    dst = ((short) dst) >>> (count - 1);//unsigned?
                    I.CarryVal = dst & 0x01;
                    dst = ((short) (dst & 0xFFFF)) >>> 1;//unsigned?
                    SetSZPF_Word(dst);
                    I.AuxVal = 1;
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                }
                break;
                default:
                    System.out.println("rot unsupported 0x " + (Integer.toHexString((ModRM & 0x38))));
                    throw new UnsupportedOperationException("unsupported");
            }
        }
    }

    static void i86_rep(int flagval) {
        /* Handles rep- and repnz- prefixes. flagval is the value of ZF for the 
		 loop  to continue for CMPS and SCAS instructions. */

        int/*unsigned*/ next = FETCHOP();
        int/*unsigned*/ count = I.regs.w[CX];

        switch (next) {
            /*TODO*///	case 0x26:  /* ES: */ 
/*TODO*///		seg_prefix=TRUE; 
/*TODO*///		prefix_base=I.base[ES]; 
/*TODO*///		i86_ICount[0] -= cycles.override;
/*TODO*///		PREFIX(rep)(flagval); 
/*TODO*///		break; 
/*TODO*///	case 0x2e:  /* CS: */ 
/*TODO*///		seg_prefix=TRUE; 
/*TODO*///		prefix_base=I.base[CS]; 
/*TODO*///		i86_ICount[0] -= cycles.override;
/*TODO*///		PREFIX(rep)(flagval); 
/*TODO*///		break; 
/*TODO*///	case 0x36:  /* SS: */ 
/*TODO*///		seg_prefix=TRUE; 
/*TODO*///		prefix_base=I.base[SS]; 
/*TODO*///		i86_ICount[0] -= cycles.override;
/*TODO*///		PREFIX(rep)(flagval); 
/*TODO*///		break; 
/*TODO*///	case 0x3e:  /* DS: */ 
/*TODO*///		seg_prefix=TRUE; 
/*TODO*///		prefix_base=I.base[DS]; 
/*TODO*///		i86_ICount[0] -= cycles.override;
/*TODO*///		PREFIX(rep)(flagval); 
/*TODO*///		break; 
/*TODO*///#ifndef I86 
/*TODO*///	case 0x6c:	/* REP INSB */
/*TODO*///		i86_ICount[0] -= cycles.rep_ins8_base;
/*TODO*///		for (; count > 0; count--) 
/*TODO*///		{
/*TODO*///			if (i86_ICount[0] <= 0) { I.pc = I.prevpc; break; }
/*TODO*///			PutMemB(ES,I.regs.w[DI],read_port(I.regs.w[DX]));
/*TODO*///			I.regs.w[DI] += I.DirVal;
/*TODO*///			i86_ICount[0] -= cycles.rep_ins8_count;
/*TODO*///		}
/*TODO*///		I.regs.w[CX]=count; 
/*TODO*///		break; 
/*TODO*///	case 0x6d:  /* REP INSW */ 
/*TODO*///		i86_ICount[0] -= cycles.rep_ins16_base;
/*TODO*///		for (; count > 0; count--) 
/*TODO*///		{
/*TODO*///			if (i86_ICount[0] <= 0) { I.pc = I.prevpc; break; }
/*TODO*///			PutMemB(ES,I.regs.w[DI],read_port(I.regs.w[DX]));
/*TODO*///			PutMemB(ES,I.regs.w[DI]+1,read_port(I.regs.w[DX]+1));
/*TODO*///			I.regs.w[DI] += 2 * I.DirVal;
/*TODO*///			i86_ICount[0] -= cycles.rep_ins16_count;
/*TODO*///		}
/*TODO*///		I.regs.w[CX]=count; 
/*TODO*///		break; 
/*TODO*///	case 0x6e:  /* REP OUTSB */ 
/*TODO*///		i86_ICount[0] -= cycles.rep_outs8_base;
/*TODO*///		for (; count > 0; count--) 
/*TODO*///		{
/*TODO*///			if (i86_ICount[0] <= 0) { I.pc = I.prevpc; break; }
/*TODO*///			write_port(I.regs.w[DX],GetMemB(DS,I.regs.w[SI]));
/*TODO*///			I.regs.w[DI] += I.DirVal;
/*TODO*///			i86_ICount[0] -= cycles.rep_outs8_count;
/*TODO*///		}
/*TODO*///		I.regs.w[CX]=count; 
/*TODO*///		break; 
/*TODO*///	case 0x6f:  /* REP OUTSW */ 
/*TODO*///		i86_ICount[0] -= cycles.rep_outs16_base;
/*TODO*///		for (; count > 0; count--) 
/*TODO*///		{
/*TODO*///			if (i86_ICount[0] <= 0) { I.pc = I.prevpc; break; }
/*TODO*///			write_port(I.regs.w[DX],GetMemB(DS,I.regs.w[SI]));
/*TODO*///			write_port(I.regs.w[DX]+1,GetMemB(DS,I.regs.w[SI]+1));
/*TODO*///			I.regs.w[DI] += 2 * I.DirVal;
/*TODO*///			i86_ICount[0] -= cycles.rep_outs16_count;
/*TODO*///		}
/*TODO*///		I.regs.w[CX]=count; 
/*TODO*///		break; 
/*TODO*///#endif 
            case 0xa4:
                /* REP MOVSB */
                i86_ICount[0] -= cycles.rep_movs8_base;
                for (; count > 0; count--) {
                    int/*BYTE*/ tmp;

                    if (i86_ICount[0] <= 0) {
                        I.pc = I.prevpc;
                        break;
                    }
                    tmp = GetMemB(DS, I.regs.w[SI]) & 0xFF;
                    PutMemB(ES, I.regs.w[DI], tmp);
                    I.regs.SetW(DI, I.regs.w[DI] + I.DirVal);
                    I.regs.SetW(SI, I.regs.w[SI] + I.DirVal);
                    i86_ICount[0] -= cycles.rep_movs8_count;
                }
                I.regs.SetW(CX, count);
                break;
            case 0xa5:
                /* REP MOVSW */
                i86_ICount[0] -= cycles.rep_movs16_base;
                for (; count > 0; count--) {
                    int /*WORD*/ tmp;
                    int di = I.regs.w[DI];
                    int si = I.regs.w[SI];
                    if (i86_ICount[0] <= 0) {
                        I.pc = I.prevpc;
                        break;
                    }
                    tmp = GetMemW(DS, si);
                    PutMemW(ES, I.regs.w[DI], tmp);
                    di += 2 * I.DirVal;
                    si += 2 * I.DirVal;
                    I.regs.SetW(DI, di & 0xFFFF);
                    I.regs.SetW(SI, si & 0xFFFF);
                    i86_ICount[0] -= cycles.rep_movs16_count;
                }
                I.regs.SetW(CX, count);
                break;
            case 0xa6:
                /* REP(N)E CMPSB */
                i86_ICount[0] -= cycles.rep_cmps8_base;
                for (I.ZeroVal = NOT(flagval); (ZF() == flagval) && (count > 0); count--) {
                    int/*unsigned*/ dst, src;

                    if (i86_ICount[0] <= 0) {
                        I.pc = I.prevpc;
                        break;
                    }
                    dst = GetMemB(ES, I.regs.w[DI]);
                    src = GetMemB(DS, I.regs.w[SI]);
                    //SUBB(src,dst); /* opposite of the usual convention */
                    int res = src - dst;
                    SetCFB(res);
                    SetOFB_Sub(res, dst, src);
                    SetAF(res, dst, src);
                    SetSZPF_Byte(res);
                    I.regs.SetW(DI, I.regs.w[DI] + I.DirVal);
                    I.regs.SetW(SI, I.regs.w[SI] + I.DirVal);
                    i86_ICount[0] -= cycles.rep_cmps8_count;
                }
                I.regs.SetW(CX, count);
                break;
            case 0xa7:
                /* REP(N)E CMPSW */
                i86_ICount[0] -= cycles.rep_cmps16_base;
                for (I.ZeroVal = NOT(flagval); (ZF() == flagval) && (count > 0); count--) {
                    int/*unsigned*/ dst, src;

                    if (i86_ICount[0] <= 0) {
                        I.pc = I.prevpc;
                        break;
                    }
                    dst = GetMemB(ES, I.regs.w[DI]);
                    src = GetMemB(DS, I.regs.w[SI]);
                    //SUBB(src,dst); /* opposite of the usual convention */
                    int res = src - dst;
                    SetCFB(res);
                    SetOFB_Sub(res, dst, src);
                    SetAF(res, dst, src);
                    SetSZPF_Byte(res);
                    I.regs.SetW(DI, I.regs.w[DI] + 2 * I.DirVal);
                    I.regs.SetW(SI, I.regs.w[SI] + 2 * I.DirVal);
                    i86_ICount[0] -= cycles.rep_cmps16_count;
                }
                I.regs.SetW(CX, count);
                break;
            case 0xaa:
                /* REP STOSB */
                i86_ICount[0] -= cycles.rep_stos8_base;
                for (; count > 0; count--) {
                    if (i86_ICount[0] <= 0) {
                        I.pc = I.prevpc;
                        break;
                    }
                    PutMemB(ES, I.regs.w[DI], I.regs.b[AL]);
                    I.regs.SetW(DI, I.regs.w[DI] + I.DirVal);
                    i86_ICount[0] -= cycles.rep_stos8_count;
                }
                I.regs.SetW(CX, count);
                break;
            case 0xab:
                /* REP STOSW */
                i86_ICount[0] -= cycles.rep_stos16_base;
                for (; count > 0; count--) {
                    if (i86_ICount[0] <= 0) {
                        I.pc = I.prevpc;
                        break;
                    }
                    int tmp = I.regs.w[DI];
                    //PutMemB(ES,I.regs.w[DI],I.regs.b[AL]);
                    //PutMemB(ES,I.regs.w[DI]+1,I.regs.b[AH]);
                    PutMemW(ES, tmp, I.regs.w[AX]);
                    tmp += 2 * I.DirVal;
                    I.regs.SetW(DI, tmp & 0xFFFF);
                    i86_ICount[0] -= cycles.rep_stos16_count;
                }
                I.regs.SetW(CX, count);
                break;
            /*TODO*///	case 0xac:  /* REP LODSB */ 
/*TODO*///		i86_ICount[0] -= cycles.rep_lods8_base;
/*TODO*///		for (; count > 0; count--) 
/*TODO*///		{
/*TODO*///			if (i86_ICount[0] <= 0) { I.pc = I.prevpc; break; }
/*TODO*///			I.regs.b[AL] = GetMemB(DS,I.regs.w[SI]);
/*TODO*///			I.regs.w[SI] += I.DirVal;
/*TODO*///			i86_ICount[0] -= cycles.rep_lods8_count;
/*TODO*///		}
/*TODO*///		I.regs.w[CX]=count; 
/*TODO*///		break; 
/*TODO*///	case 0xad:  /* REP LODSW */ 
/*TODO*///		i86_ICount[0] -= cycles.rep_lods16_base;
/*TODO*///		for (; count > 0; count--) 
/*TODO*///		{
/*TODO*///			if (i86_ICount[0] <= 0) { I.pc = I.prevpc; break; }
/*TODO*///			I.regs.w[AX] = GetMemW(DS,I.regs.w[SI]);
/*TODO*///			I.regs.w[SI] += 2 * I.DirVal;
/*TODO*///			i86_ICount[0] -= cycles.rep_lods16_count;
/*TODO*///		}
/*TODO*///		I.regs.w[CX]=count; 
/*TODO*///		break; 
            case 0xae: /* REP(N)E SCASB */ {
                i86_ICount[0] -= cycles.rep_scas8_base;
                for (I.ZeroVal = NOT(flagval); (ZF() == flagval) && (count > 0); count--) {
                    int/*unsigned*/ src, dst;

                    if (i86_ICount[0] <= 0) {
                        I.pc = I.prevpc;
                        break;
                    }
                    src = GetMemB(ES, I.regs.w[DI]);
                    dst = I.regs.b[AL];
                    //SUBB(dst,src);
                    int res = dst - src;
                    SetCFB(res);
                    SetOFB_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Byte(res);
                    dst = res & 0xFF;
                    I.regs.SetW(DI, I.regs.w[DI] + I.DirVal);
                    i86_ICount[0] -= cycles.rep_scas8_count;
                }
                I.regs.SetW(CX, count);
            }
            break;
            case 0xaf: /* REP(N)E SCASW */ {
                i86_ICount[0] -= cycles.rep_scas16_base;
                for (I.ZeroVal = NOT(flagval); (ZF() == flagval) && (count > 0); count--) {
                    if (i86_ICount[0] <= 0) {
                        I.pc = I.prevpc;
                        break;
                    }
                    int/*unsigned*/ src = GetMemW(ES, I.regs.w[DI]);
                    int/*unsigned*/ dst = I.regs.w[AX];
                    // SUBW(dst,src);
                    int/*unsigned*/ res = dst - src;
                    SetCFW(res);
                    SetOFW_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    dst = res & 0xFFFF;
                    I.regs.SetW(DI, I.regs.w[DI] + 2 * I.DirVal);
                    i86_ICount[0] -= cycles.rep_scas16_count;
                }
                I.regs.SetW(CX, count);
            }
            break;
            default:
                System.out.println("rep 0x" + Integer.toHexString(next));
                throw new UnsupportedOperationException("Unsupported");
            /*TODO*///		PREFIX(_instruction)[next](); 
        }
    }
    ;

    static InstructionPtr i86_add_br8 = new InstructionPtr() {
        public void handler() {
            //(dst, src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_mr8;
            //ADDB(dst, src);
            int res = dst + src;
            SetCFB(res);
            SetOFB_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            PutbackRMByte(ModRM, dst);
        }
    };
    static InstructionPtr i86_add_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_mr16;
            //ADDW(dst,src);
            int res = dst + src;
            SetCFW(res);
            SetOFW_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            PutbackRMWord(ModRM, dst);
        }
    };
    static InstructionPtr i86_add_r8b = new InstructionPtr() {
        public void handler() {
            //DEF_r8b(dst,src);
            int ModRM = FETCHOP();
            int dst = RegByte(ModRM);
            int src = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            //ADDB(dst,src);
            int res = dst + src;
            SetCFB(res);
            SetOFB_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            SetRegByte(ModRM, dst);
        }
    };
    static InstructionPtr i86_add_r16w = new InstructionPtr() /* Opcode 0x03 */ {
        public void handler() {
            //DEF_r16w(dst, src);
            /*unsigned*/
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegWord(ModRM);
            /*unsigned*/
            int src = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            //ADDW(dst, src);
            /*unsigned*/
            int res = dst + src;
            SetCFW(res);
            SetOFW_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            SetRegWord(ModRM, dst);
        }
    };
    static InstructionPtr i86_add_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            //ADDB(dst,src);
            int res = dst + src;
            SetCFB(res);
            SetOFB_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            I.regs.SetB(AL, dst);
        }
    };
    static InstructionPtr i86_add_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst,src);
            /*unsigned*/
            int src = FETCHOP();
            /*unsigned*/
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            //ADDW(dst,src);
            /*unsigned*/
            int res = dst + src;
            SetCFW(res);
            SetOFW_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            I.regs.SetW(AX, dst);
        }
    };
    static InstructionPtr i86_push_es = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_seg;
            PUSH(I.sregs[ES]);
        }
    };
    static InstructionPtr i86_pop_es = new InstructionPtr() {
        public void handler() {
            I.sregs[ES] = POP();
            I.base[ES] = SegBase(ES);
            i86_ICount[0] -= cycles.pop_seg;
        }
    };
    static InstructionPtr i86_or_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst, src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_mr8;
            //ORB(dst, src);
            dst |= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            PutbackRMByte(ModRM, dst & 0xFF);
        }
    };
    static InstructionPtr i86_or_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_mr16;
            //ORW(dst,src);
            dst |= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            PutbackRMWord(ModRM, dst & 0xFFFF);
        }
    };
    static InstructionPtr i86_or_r8b = new InstructionPtr() {
        public void handler() {
            //DEF_r8b(dst, src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegByte(ModRM);
            /*unsigned*/
            int src = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            //ORB(dst, src);
            dst |= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            SetRegByte(ModRM, dst & 0xFF);
        }
    };
    static InstructionPtr i86_or_r16w = new InstructionPtr() {
        public void handler() {
            //DEF_r16w(dst,src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegWord(ModRM);
            /*unsigned*/
            int src = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            //ORW(dst,src);
            dst |= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            SetRegWord(ModRM, dst & 0xFFFF);
        }
    };
    static InstructionPtr i86_or_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            //ORB(dst,src);
            dst |= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            I.regs.SetB(AL, dst & 0xFF);
        }
    };
    static InstructionPtr i86_or_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst,src);
            int src = FETCHOP();
            /*unsigned*/
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            //ORW(dst,src);
            dst |= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            I.regs.SetW(AX, dst & 0xFFFF);
        }
    };
    static InstructionPtr i86_push_cs = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_seg;
            PUSH(I.sregs[CS]);
        }
    };
    static InstructionPtr i86_adc_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst,src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_mr8;
            src += CF();
            //ADDB(dst,src);
            int res = dst + src;
            SetCFB(res);
            SetOFB_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            PutbackRMByte(ModRM, dst);
        }
    };
    static InstructionPtr i86_adc_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_mr16;
            src += CF();
            //ADDW(dst,src);
            int res = dst + src;
            SetCFW(res);
            SetOFW_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            PutbackRMWord(ModRM, dst);
        }
    };
    static InstructionPtr i86_adc_r8b = new InstructionPtr() {
        public void handler() {
            //DEF_r8b(dst,src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegByte(ModRM);
            /*unsigned*/
            int src = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            src += CF();
            //ADDB(dst,src);
            int res = dst + src;
            SetCFB(res);
            SetOFB_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            SetRegByte(ModRM, dst);
        }
    };
    static InstructionPtr i86_adc_r16w = new InstructionPtr() {
        public void handler() {
            //DEF_r16w(dst,src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegWord(ModRM);
            /*unsigned*/
            int src = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            src += CF();
            //    ADDW(dst,src);
            int res = dst + src;
            SetCFW(res);
            SetOFW_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            SetRegWord(ModRM, dst);
        }
    };
    static InstructionPtr i86_adc_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            src += CF();
            //ADDB(dst,src);
            int res = dst + src;
            SetCFB(res);
            SetOFB_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            I.regs.SetB(AL, dst);
        }
    };
    static InstructionPtr i86_adc_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst,src);
            int src = FETCHOP();
            /*unsigned*/
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            src += CF();
            //ADDW(dst,src);
            int res = dst + src;
            SetCFW(res);
            SetOFW_Add(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            I.regs.SetW(AX, dst);
        }
    };
    static InstructionPtr i86_push_ss = new InstructionPtr() {
        public void handler() {
            PUSH(I.sregs[SS]);
            i86_ICount[0] -= cycles.push_seg;
        }
    };

    static InstructionPtr i86_pop_ss = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_pop_ss)(void)    /* Opcode 0x17 */
/*TODO*///{
/*TODO*///#ifdef I286
/*TODO*///	UINT16 tmp;
/*TODO*///	POP(tmp);
/*TODO*///	i286_data_descriptor(SS, tmp);
/*TODO*///#else
/*TODO*///	POP(I.sregs[SS]);
/*TODO*///	I.base[SS] = SegBase(SS);
/*TODO*///#endif
/*TODO*///	i86_ICount[0] -= cycles.pop_seg;
/*TODO*///	PREFIX(_instruction)[FETCHOP](); /* no interrupt before next instruction */
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_sbb_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst,src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_mr8;
            src += CF();
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            PutbackRMByte(ModRM, dst);
        }
    };
    static InstructionPtr i86_sbb_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_mr16;
            src += CF();
            //SUBW(dst,src);
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            PutbackRMWord(ModRM, dst);
        }
    };
    static InstructionPtr i86_sbb_r8b = new InstructionPtr() {
        public void handler() {
            //DEF_r8b(dst,src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegByte(ModRM);
            /*unsigned*/
            int src = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            src += CF();
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            SetRegByte(ModRM, dst);
        }
    };
    static InstructionPtr i86_sbb_r16w = new InstructionPtr() {
        public void handler() {
            //DEF_r16w(dst,src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegWord(ModRM);
            /*unsigned*/
            int src = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            src += CF();
            //SUBW(dst,src);
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            SetRegWord(ModRM, dst);
        }
    };
    static InstructionPtr i86_sbb_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            src += CF();
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            I.regs.SetB(AL, dst);
        }
    };
    static InstructionPtr i86_sbb_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst,src);
            int src = FETCHOP();
            /*unsigned*/
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            src += CF();
            //SUBW(dst,src);
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            I.regs.SetW(AX, dst);
        }
    };
    static InstructionPtr i86_push_ds = new InstructionPtr() {
        public void handler() {
            PUSH(I.sregs[DS]);
            i86_ICount[0] -= cycles.push_seg;
        }
    };
    static InstructionPtr i86_pop_ds = new InstructionPtr() {
        public void handler() {
            /*TODO*///#ifdef I286
/*TODO*///	UINT16 tmp;
/*TODO*///	POP(tmp);
/*TODO*///	i286_data_descriptor(DS,tmp);
/*TODO*///#else
            I.sregs[DS] = POP();
            I.base[DS] = SegBase(DS);
            /*TODO*///#endif
            i86_ICount[0] -= cycles.push_seg;
        }
    };
    static InstructionPtr i86_and_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst, src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_mr8;
            //ANDB(dst, src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            PutbackRMByte(ModRM, dst & 0xFF);
        }
    };
    static InstructionPtr i86_and_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_mr16;
            //ANDW(dst,src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            PutbackRMWord(ModRM, dst & 0xFFFF);
        }
    };
    static InstructionPtr i86_and_r8b = new InstructionPtr() {
        public void handler() {
            //DEF_r8b(dst,src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegByte(ModRM);
            /*unsigned*/
            int src = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            //ANDB(dst,src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            SetRegByte(ModRM, dst & 0xFF);
        }
    };
    static InstructionPtr i86_and_r16w = new InstructionPtr() {
        public void handler() {
            //DEF_r16w(dst,src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegWord(ModRM);
            /*unsigned*/
            int src = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            //ANDW(dst,src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            SetRegWord(ModRM, dst & 0xFFFF);
        }
    };
    static InstructionPtr i86_and_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            //ANDB(dst, src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            I.regs.SetB(AL, dst & 0xFF);
        }
    };
    static InstructionPtr i86_and_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst, src);
            /*unsigned*/
            int src = FETCHOP();
            /*unsigned*/
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            //ANDW(dst, src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            I.regs.SetW(AX, dst);
        }
    };
    static InstructionPtr i86_es = new InstructionPtr() {
        public void handler() {
            seg_prefix = 1;
            prefix_base = I.base[ES];
            i86_ICount[0] -= cycles.override;
            i86_instruction[FETCHOP()].handler();
        }
    };
    static InstructionPtr i86_daa = new InstructionPtr() {
        public void handler() {
            if (AF() != 0 || ((I.regs.b[AL] & 0xf) > 9)) {
                int tmp;
                tmp = I.regs.b[AL] + 6;
                I.regs.SetB(AL, tmp);
                I.AuxVal = 1;
                I.CarryVal |= tmp & 0x100;
            }

            if (CF() != 0 || (I.regs.b[AL] > 0x9f)) {
                I.regs.SetB(AL, I.regs.b[AL] + 0x60);
                I.CarryVal = 1;
            }

            SetSZPF_Byte(I.regs.b[AL]);
            i86_ICount[0] -= cycles.daa;
        }
    };
    static InstructionPtr i86_sub_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst,src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_mr8;
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            PutbackRMByte(ModRM, dst);
        }
    };
    static InstructionPtr i86_sub_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst, src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_mr16;
            //SUBW(dst, src);
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            PutbackRMWord(ModRM, dst);
        }
    };
    static InstructionPtr i86_sub_r8b = new InstructionPtr() {
        public void handler() {
            //DEF_r8b(dst,src);
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegByte(ModRM);
            /*unsigned*/
            int src = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            SetRegByte(ModRM, dst);
        }
    };
    static InstructionPtr i86_sub_r16w = new InstructionPtr() /* Opcode 0x2b */ {
        public void handler() {
            //    DEF_r16w(dst,src);
            /*unsigned*/
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegWord(ModRM);
            /*unsigned*/
            int src = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            //SUBW(dst,src);
            /*unsigned*/
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            SetRegWord(ModRM, dst);
        }
    };
    static InstructionPtr i86_sub_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
            I.regs.SetB(AL, dst);
        }
    };
    static InstructionPtr i86_sub_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst,src);
            int src = FETCHOP();
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            //SUBW(dst,src);
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            I.regs.SetW(AX, dst);
        }
    };
    static InstructionPtr i86_cs = new InstructionPtr() /* Opcode 0x2e */ {
        public void handler() {
            seg_prefix = 1;
            prefix_base = I.base[CS];
            i86_ICount[0] -= cycles.override;
            i86_instruction[FETCHOP()].handler();
        }
    };
    static InstructionPtr i86_das = new InstructionPtr() {
        public void handler() {
            if (AF() != 0 || ((I.regs.b[AL] & 0xf) > 9)) {
                int tmp;
                tmp = I.regs.b[AL] - 6;
                I.regs.SetB(AL, tmp);
                I.AuxVal = 1;
                I.CarryVal |= tmp & 0x100;
            }

            if (CF() != 0 || (I.regs.b[AL] > 0x9f)) {
                I.regs.SetB(AL, I.regs.b[AL] - 0x60);
                I.CarryVal = 1;
            }

            SetSZPF_Byte(I.regs.b[AL]);
            i86_ICount[0] -= cycles.das;
        }
    };
    static InstructionPtr i86_xor_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst,src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_mr8;
            //XORB(dst,src);
            dst ^= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            PutbackRMByte(ModRM, dst & 0xFF);
        }
    };
    static InstructionPtr i86_xor_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_mr16;
            //XORW(dst,src);
            dst ^= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            PutbackRMWord(ModRM, dst & 0xFFFF);
        }
    };
    static InstructionPtr i86_xor_r8b = new InstructionPtr() /* Opcode 0x32 */ {
        public void handler() {
            //DEF_r8b(dst,src);
            /*unsigned*/
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegByte(ModRM);
            /*unsigned*/
            int src = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            //XORB(dst,src);
            dst ^= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            SetRegByte(ModRM, dst & 0xFF);
        }
    };
    static InstructionPtr i86_xor_r16w = new InstructionPtr() /* Opcode 0x33 */ {
        public void handler() {
            //DEF_r16w(dst, src);          
            int /*unsigned*/ ModRM = FETCHOP();
            int /*unsigned*/ dst = RegWord(ModRM);
            int /*unsigned*/ src = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            //XORW(dst, src);
            dst ^= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            SetRegWord(ModRM, dst);//RegWord(ModRM) = dst;
        }
    };
    static InstructionPtr i86_xor_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            //XORB(dst,src);
            dst ^= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
            I.regs.SetB(AL, dst & 0xFF);
        }
    };
    static InstructionPtr i86_xor_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst,src);
            int src = FETCHOP();
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            //XORW(dst,src);
            dst ^= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
            I.regs.SetW(AX, dst & 0xFFFF);
        }
    };

    static InstructionPtr i86_ss = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_ss)(void)    /* Opcode 0x36 */
/*TODO*///{
/*TODO*///	seg_prefix=TRUE;
/*TODO*///	prefix_base=I.base[SS];
/*TODO*///	i86_ICount[0] -= cycles.override;
/*TODO*///	PREFIX(_instruction)[FETCHOP]();
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_aaa = new InstructionPtr() {
        public void handler() {
            if (AF() != 0 || ((I.regs.b[AL] & 0xf) > 9)) {
                I.regs.SetB(AL, I.regs.b[AL] + 6);
                I.regs.SetB(AH, I.regs.b[AH] + 1);
                I.AuxVal = 1;
                I.CarryVal = 1;
            } else {
                I.AuxVal = 0;
                I.CarryVal = 0;
            }
            I.regs.SetB(AL, I.regs.b[AL] & 0x0F);
            i86_ICount[0] -= cycles.aaa;
        }
    };
    static InstructionPtr i86_cmp_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst,src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
        }
    };
    static InstructionPtr i86_cmp_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            //SUBW(dst,src);
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
        }
    };
    static InstructionPtr i86_cmp_r8b = new InstructionPtr() {
        public void handler() {
            //DEF_r8b(dst,src);
            int ModRM = FETCHOP();
            int dst = RegByte(ModRM);
            int src = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
        }
    };
    static InstructionPtr i86_cmp_r16w = new InstructionPtr() {
        public void handler() {
            //DEF_r16w(dst,src);
            /*unsigned*/
            int ModRM = FETCHOP();
            /*unsigned*/
            int dst = RegWord(ModRM);
            /*unsigned*/
            int src = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            //SUBW(dst,src);
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);

        }
    };
    static InstructionPtr i86_cmp_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            dst = res & 0xFF;
        }
    };
    static InstructionPtr i86_cmp_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst,src);
            int src = FETCHOP();
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            //SUBW(dst,src);
            int res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
        }
    };

    static InstructionPtr i86_ds = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_ds)(void)    /* Opcode 0x3e */
/*TODO*///{
/*TODO*///	seg_prefix=TRUE;
/*TODO*///	prefix_base=I.base[DS];
/*TODO*///	i86_ICount[0] -= cycles.override;
/*TODO*///	PREFIX(_instruction)[FETCHOP]();
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_aas = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };

    /*TODO*///static void PREFIX86(_aas)(void)    /* Opcode 0x3f */
/*TODO*///{
/*TODO*///	if (AF || ((I.regs.b[AL] & 0xf) > 9))
/*TODO*///    {
/*TODO*///		I.regs.b[AL] -= 6;
/*TODO*///		I.regs.b[AH] -= 1;
/*TODO*///		I.AuxVal = 1;
/*TODO*///		I.CarryVal = 1;
/*TODO*///    }
/*TODO*///	else
/*TODO*///	{
/*TODO*///		I.AuxVal = 0;
/*TODO*///		I.CarryVal = 0;
/*TODO*///    }
/*TODO*///	I.regs.b[AL] &= 0x0F;
/*TODO*///	i86_ICount[0] -= cycles.aas;
/*TODO*///}
/*TODO*///
    static void IncWordReg(int Reg) {
        int/*unsigned*/ tmp = /*(unsigned)*/ I.regs.w[Reg];
        int/*unsigned*/ tmp1 = tmp + 1;
        SetOFW_Add(tmp1, tmp, 1);
        SetAF(tmp1, tmp, 1);
        SetSZPF_Word(tmp1);
        I.regs.SetW(Reg, tmp1 & 0xFFFF);
        i86_ICount[0] -= cycles.incdec_r16;
    }
    static InstructionPtr i86_inc_ax = new InstructionPtr() /* Opcode 0x40 */ {
        public void handler() {
            IncWordReg(AX);
        }
    };
    static InstructionPtr i86_inc_cx = new InstructionPtr() /* Opcode 0x41 */ {
        public void handler() {
            IncWordReg(CX);
        }
    };
    static InstructionPtr i86_inc_dx = new InstructionPtr() /* Opcode 0x42 */ {
        public void handler() {
            IncWordReg(DX);
        }
    };
    static InstructionPtr i86_inc_bx = new InstructionPtr() /* Opcode 0x43 */ {
        public void handler() {
            IncWordReg(BX);
        }
    };
    static InstructionPtr i86_inc_sp = new InstructionPtr() /* Opcode 0x44 */ {
        public void handler() {
            IncWordReg(SP);
        }
    };
    static InstructionPtr i86_inc_bp = new InstructionPtr() /* Opcode 0x45 */ {
        public void handler() {
            IncWordReg(BP);
        }
    };
    static InstructionPtr i86_inc_si = new InstructionPtr() /* Opcode 0x46 */ {
        public void handler() {
            IncWordReg(SI);
        }
    };
    static InstructionPtr i86_inc_di = new InstructionPtr() /* Opcode 0x47 */ {
        public void handler() {
            IncWordReg(DI);
        }
    };

    public static void DecWordReg(int Reg) {
        /*unsigned*/
        int tmp = /*(unsigned)*/ I.regs.w[Reg];
        /*unsigned*/
        int tmp1 = tmp - 1;
        SetOFW_Sub(tmp1, 1, tmp);
        SetAF(tmp1, tmp, 1);
        SetSZPF_Word(tmp1);
        I.regs.SetW(Reg, tmp1 & 0xFFFF);
        i86_ICount[0] -= cycles.incdec_r16;
    }
    static InstructionPtr i86_dec_ax = new InstructionPtr() /* Opcode 0x48 */ {
        public void handler() {
            DecWordReg(AX);
        }
    };
    static InstructionPtr i86_dec_cx = new InstructionPtr() /* Opcode 0x48 */ {
        public void handler() {
            DecWordReg(CX);
        }
    };
    static InstructionPtr i86_dec_dx = new InstructionPtr() /* Opcode 0x48 */ {
        public void handler() {
            DecWordReg(DX);
        }
    };
    static InstructionPtr i86_dec_bx = new InstructionPtr() /* Opcode 0x48 */ {
        public void handler() {
            DecWordReg(BX);
        }
    };
    static InstructionPtr i86_dec_sp = new InstructionPtr() /* Opcode 0x48 */ {
        public void handler() {
            DecWordReg(SP);
        }
    };
    static InstructionPtr i86_dec_bp = new InstructionPtr() /* Opcode 0x48 */ {
        public void handler() {
            DecWordReg(BP);
        }
    };
    static InstructionPtr i86_dec_si = new InstructionPtr() /* Opcode 0x48 */ {
        public void handler() {
            DecWordReg(SI);
        }
    };
    static InstructionPtr i86_dec_di = new InstructionPtr() /* Opcode 0x48 */ {
        public void handler() {
            DecWordReg(DI);
        }
    };
    static InstructionPtr i86_push_ax = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_r16;
            PUSH(I.regs.w[AX]);
        }
    };
    static InstructionPtr i86_push_cx = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_r16;
            PUSH(I.regs.w[CX]);
        }
    };
    static InstructionPtr i86_push_dx = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_r16;
            PUSH(I.regs.w[DX]);
        }
    };
    static InstructionPtr i86_push_bx = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_r16;
            PUSH(I.regs.w[BX]);
        }
    };
    static InstructionPtr i86_push_sp = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_r16;
            PUSH(I.regs.w[SP]);
        }
    };
    static InstructionPtr i86_push_bp = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_r16;
            PUSH(I.regs.w[BP]);
        }
    };
    static InstructionPtr i86_push_si = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_r16;
            PUSH(I.regs.w[SI]);
        }
    };
    static InstructionPtr i86_push_di = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.push_r16;
            PUSH(I.regs.w[DI]);
        }
    };
    static InstructionPtr i86_pop_ax = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pop_r16;
            I.regs.SetW(AX, POP());
        }
    };
    static InstructionPtr i86_pop_cx = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pop_r16;
            I.regs.SetW(CX, POP());
        }
    };
    static InstructionPtr i86_pop_dx = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pop_r16;
            I.regs.SetW(DX, POP());
        }
    };
    static InstructionPtr i86_pop_bx = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pop_r16;
            I.regs.SetW(BX, POP());
        }
    };
    static InstructionPtr i86_pop_sp = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pop_r16;
            I.regs.SetW(SP, POP());
        }
    };
    static InstructionPtr i86_pop_bp = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pop_r16;
            I.regs.SetW(BP, POP());
        }
    };
    static InstructionPtr i86_pop_si = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pop_r16;
            I.regs.SetW(SI, POP());
        }
    };
    static InstructionPtr i86_pop_di = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pop_r16;
            I.regs.SetW(DI, POP());
        }
    };
    static InstructionPtr i86_jo = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (OF() != 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jno = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (OF() == 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jb = new InstructionPtr() /* Opcode 0x72 */ {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (CF() != 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jnb = new InstructionPtr() /* Opcode 0x73 */ {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (CF() == 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jz = new InstructionPtr() /* Opcode 0x74 */ {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (ZF() != 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jnz = new InstructionPtr() /* Opcode 0x75 */ {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (ZF() == 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jbe = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (CF() != 0 || ZF() != 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jnbe = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (!(CF() != 0 || ZF() != 0)) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_js = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (SF() != 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jns = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (SF() == 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jp = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (PF() != 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jnp = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (PF() == 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jl = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if ((SF() != OF()) && ZF() == 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jnl = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (ZF() != 0 || (SF() == OF())) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jle = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if (ZF() != 0 || (SF() != OF())) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_jnle = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            if ((SF() == OF()) && ZF() == 0) {
                I.pc = (I.pc + tmp) & AMASK;//I.pc += tmp;
                i86_ICount[0] -= cycles.jcc_t;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcc_nt;
            }
        }
    };
    static InstructionPtr i86_80pre = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            int dst = GetRMByte(ModRM);
            int src = FETCH();

            switch (ModRM & 0x38) {
                case 0x00: {
                    /* ADD eb,d8 */
                    //ADDB(dst,src);
                    int res = dst + src;
                    SetCFB(res);
                    SetOFB_Add(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Byte(res);
                    dst = res & 0xFF;
                    PutbackRMByte(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
                }
                break;
                case 0x08: {
                    /* OR eb,d8 */
                    //ORB(dst,src);
                    dst |= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Byte(dst);
                    PutbackRMByte(ModRM, dst & 0xFF);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
                }
                break;
                case 0x10: /* ADC eb,d8 */ {
                    src += CF();
                    //ADDB(dst,src);
                    int res = dst + src;
                    SetCFB(res);
                    SetOFB_Add(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Byte(res);
                    dst = res & 0xFF;
                    PutbackRMByte(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
                }
                break;
                case 0x18: /* SBB eb,b8 */ {
                    src += CF();
                    //SUBB(dst, src);
                    int res = dst - src;
                    SetCFB(res);
                    SetOFB_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Byte(res);
                    dst = res & 0xFF;
                    PutbackRMByte(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
                }
                break;
                case 0x20: /* AND eb,d8 */ {
                    //ANDB(dst,src);
                    dst &= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Byte(dst);
                    PutbackRMByte(ModRM, dst & 0xFF);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
                }
                break;
                case 0x28: /* SUB eb,d8 */ {
                    //SUBB(dst,src);
                    int res = dst - src;
                    SetCFB(res);
                    SetOFB_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Byte(res);
                    dst = res & 0xFF;
                    PutbackRMByte(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
                }
                break;
                case 0x30: /* XOR eb,d8 */ {
                    //XORB(dst,src);
                    dst ^= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Byte(dst);
                    PutbackRMByte(ModRM, dst & 0xFF);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
                }
                break;
                case 0x38:
                    /* CMP eb,d8 */
                    //SUBB(dst,src);
                    int res = dst - src;
                    SetCFB(res);
                    SetOFB_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Byte(res);
                    dst = res & 0xFF;
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8_ro;
                    break;
                default:
                    System.out.println("unexpected i_80pre 0x" + Integer.toHexString(ModRM & 0x38));//that shouldn't happended leave it here for debug
            }
        }
    };

    static InstructionPtr i86_81pre = new InstructionPtr() /* Opcode 0x81 */ {
        public void handler() {
            int /*unsigned*/ ModRM = FETCH();
            int /*unsigned*/ dst = GetRMWord(ModRM) & 0xFFFF;
            int /*unsigned*/ src = FETCH();
            src += (FETCH() << 8);
            switch (ModRM & 0x38) {
                case 0x00: /* ADD ew,d16 */ {
                    //ADDW(dst,src);
                    /*unsigned*/
                    int res = dst + src;
                    SetCFW(res);
                    SetOFW_Add(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    dst = res & 0xFFFF;
                    PutbackRMWord(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16;
                }
                break;
                case 0x08: /* OR ew,d16 */ {
                    //ORW(dst,src);
                    dst |= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Word(dst);
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16;
                }
                break;
                /*TODO*///        ORW(dst,src);
/*TODO*///        PutbackRMWord(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16;
/*TODO*///		break;
/*TODO*///	case 0x10:	/* ADC ew,d16 */
/*TODO*///		src+=CF;
/*TODO*///		ADDW(dst,src);
/*TODO*///        PutbackRMWord(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16;
/*TODO*///		break;
/*TODO*///    case 0x18:  /* SBB ew,d16 */
/*TODO*///        src+=CF;
/*TODO*///		SUBW(dst,src);
/*TODO*///        PutbackRMWord(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16;
/*TODO*///		break;
                case 0x20: /* AND ew,d16 */ {
                    //ANDW(dst,src);
                    dst &= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Word(dst);
                    PutbackRMWord(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16;
                }
                break;
                case 0x28: {
                    /* SUB ew,d16 */
                    //SUBW(dst,src);
                    int/*unsigned*/ res = dst - src;
                    SetCFW(res);
                    SetOFW_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    dst = res & 0xFFFF;
                    PutbackRMWord(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16;
                }
                break;
                case 0x30: /* XOR ew,d16 */ {
                    //XORW(dst,src);
                    dst ^= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Word(dst);
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16;
                }
                break;
                case 0x38: /* CMP ew,d16 */ {
                    //SUBW(dst,src);
                    /*unsigned*/
                    int res = dst - src;
                    SetCFW(res);
                    SetOFW_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    dst = res & 0xFFFF;
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16_ro;
                }
                break;
                default:
                    System.out.println("i_81pre 0x" + Integer.toHexString(ModRM & 0x38));
                    throw new UnsupportedOperationException("Unsupported");
            }
        }
    };

    static InstructionPtr i86_82pre = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///
/*TODO*///static void PREFIX86(_82pre)(void)	 /* Opcode 0x82 */
/*TODO*///{
/*TODO*///	unsigned ModRM = FETCH;
/*TODO*///	unsigned dst = GetRMByte(ModRM);
/*TODO*///	unsigned src = FETCH;
/*TODO*///
/*TODO*///	switch (ModRM & 0x38)
/*TODO*///	{
/*TODO*///	case 0x00:	/* ADD eb,d8 */
/*TODO*///		ADDB(dst,src);
/*TODO*///		PutbackRMByte(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
/*TODO*///		break;
/*TODO*///	case 0x08:	/* OR eb,d8 */
/*TODO*///		ORB(dst,src);
/*TODO*///		PutbackRMByte(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
/*TODO*///		break;
/*TODO*///	case 0x10:	/* ADC eb,d8 */
/*TODO*///		src+=CF;
/*TODO*///		ADDB(dst,src);
/*TODO*///		PutbackRMByte(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
/*TODO*///		break;
/*TODO*///	case 0x18:	/* SBB eb,d8 */
/*TODO*///        src+=CF;
/*TODO*///		SUBB(dst,src);
/*TODO*///		PutbackRMByte(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
/*TODO*///		break;
/*TODO*///	case 0x20:	/* AND eb,d8 */
/*TODO*///		ANDB(dst,src);
/*TODO*///		PutbackRMByte(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
/*TODO*///		break;
/*TODO*///	case 0x28:	/* SUB eb,d8 */
/*TODO*///		SUBB(dst,src);
/*TODO*///		PutbackRMByte(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
/*TODO*///		break;
/*TODO*///	case 0x30:	/* XOR eb,d8 */
/*TODO*///		XORB(dst,src);
/*TODO*///		PutbackRMByte(ModRM,dst);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8;
/*TODO*///		break;
/*TODO*///	case 0x38:	/* CMP eb,d8 */
/*TODO*///		SUBB(dst,src);
/*TODO*///        i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8_ro;
/*TODO*///		break;
/*TODO*///	}
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_83pre = new InstructionPtr() /* Opcode 0x83 */ {
        public void handler() {
            int /*unsigned*/ ModRM = FETCH();
            int /*unsigned*/ dst = GetRMWord(ModRM) & 0xFFFF;
            int /*unsigned*/ src = ((short) ((byte) FETCH())) & 0xFFFF;
            switch (ModRM & 0x38) {
                case 0x00: /* ADD ew,d16 */ {
                    //ADDW(dst,src);                   
                    int /*unsigned*/ res = dst + src;
                    SetCFW(res);
                    SetOFW_Add(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    dst = res & 0xFFFF;
                    PutbackRMWord(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_r16i8 : cycles.alu_m16i8;
                }
                break;
                case 0x08: /* OR ew,d16 */ {
                    //ORW(dst,src);
                    dst |= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Word(dst);
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_r16i8 : cycles.alu_m16i8;
                }
                break;
                case 0x10: /* ADC ew,d16 */ {
                    src += CF();
                    //ADDW(dst,src);
                    int res = dst + src;
                    SetCFW(res);
                    SetOFW_Add(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    dst = res & 0xFFFF;
                    PutbackRMWord(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_r16i8 : cycles.alu_m16i8;
                }
                break;
                case 0x18: /* SBB ew,d16 */ {
                    src += CF();
                    //SUBW(dst,src);
                    int res = dst - src;
                    SetCFW(res);
                    SetOFW_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    dst = res & 0xFFFF;
                    PutbackRMWord(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_r16i8 : cycles.alu_m16i8;
                }
                break;
                case 0x20: /* AND ew,d16 */ {
                    //ANDW(dst,src);
                    dst &= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Word(dst);
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_r16i8 : cycles.alu_m16i8;
                }
                break;
                case 0x28: /* SUB ew,d16 */ {
                    //SUBW(dst,src);
                    int res = dst - src;
                    SetCFW(res);
                    SetOFW_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    dst = res & 0xFFFF;
                    PutbackRMWord(ModRM, dst);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_r16i8 : cycles.alu_m16i8;
                }
                break;
                case 0x30: /* XOR ew,d16 */ {
                    dst ^= src;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Word(dst);
                    PutbackRMWord(ModRM, dst & 0xFFFF);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_r16i8 : cycles.alu_m16i8;
                }
                break;
                case 0x38:
                    /* CMP ew,d16 */
                    //SUBW(dst,src);
                    int res = dst - src;
                    SetCFW(res);
                    SetOFW_Sub(res, src, dst);
                    SetAF(res, src, dst);
                    SetSZPF_Word(res);
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_r16i8 : cycles.alu_m16i8_ro;
                    break;
                default:
                    System.out.println("unexpected i_83pre 0x" + Integer.toHexString(ModRM & 0x38));//this shouldn't happend
            }
        }
    };
    static InstructionPtr i86_test_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst,src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr8 : cycles.alu_rm8;
            //ANDB(dst,src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
        }
    };
    static InstructionPtr i86_test_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_rr16 : cycles.alu_rm16;
            //ANDW(dst,src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
        }
    };
    static InstructionPtr i86_xchg_br8 = new InstructionPtr() {
        public void handler() {
            //DEF_br8(dst,src);
            int ModRM = FETCHOP();
            int src = RegByte(ModRM);
            int dst = GetRMByte(ModRM);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.xchg_rr8 : cycles.xchg_rm8;
            SetRegByte(ModRM, dst);
            PutbackRMByte(ModRM, src & 0xFF);
        }
    };
    static InstructionPtr i86_xchg_wr16 = new InstructionPtr() {
        public void handler() {
            //DEF_wr16(dst,src);
            int ModRM = FETCHOP();
            int src = RegWord(ModRM);
            int dst = GetRMWord(ModRM);
            SetRegWord(ModRM, dst & 0xFFFF);
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.xchg_rr16 : cycles.xchg_rm16;
            PutbackRMWord(ModRM, src & 0xFFFF);
        }
    };
    static InstructionPtr i86_mov_br8 = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            int src = RegByte(ModRM) & 0xFF;
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mov_rr8 : cycles.mov_mr8;
            PutRMByte(ModRM, src);
        }
    };
    static InstructionPtr i86_mov_wr16 = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            int src = RegWord(ModRM) & 0xFFFF;
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mov_rr16 : cycles.mov_mr16;
            PutRMWord(ModRM, src);
        }
    };
    static InstructionPtr i86_mov_r8b = new InstructionPtr() /* Opcode 0x8a */ {
        public void handler() {
            /*unsigned*/
            int ModRM = FETCH();
            /*BYTE*/
            int src = GetRMByte(ModRM) & 0xFF;
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mov_rr8 : cycles.mov_rm8;
            SetRegByte(ModRM, src);
        }
    };
    static InstructionPtr i86_mov_r16w = new InstructionPtr() /* Opcode 0x8b */ {
        public void handler() {
            int /*unsigned*/ ModRM = FETCH();
            int /*WORD*/ src = GetRMWord(ModRM) & 0xFFFF;
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mov_rr8 : cycles.mov_rm16;
            SetRegWord(ModRM, src);
        }
    };
    static InstructionPtr i86_mov_wsreg = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mov_rs : cycles.mov_ms;
            /*TODO*///#ifdef I286
/*TODO*///	if (ModRM & 0x20) {	/* HJB 12/13/98 1xx is invalid */
/*TODO*///		i286_trap2(ILLEGAL_INSTRUCTION);
/*TODO*///		return;
/*TODO*///	}
/*TODO*///#else
            if ((ModRM & 0x20) != 0) {
                return;
                /* HJB 12/13/98 1xx is invalid */
            }
            /*TODO*///#endif
            PutRMWord(ModRM, I.sregs[(ModRM & 0x38) >> 3]);
        }
    };
    static InstructionPtr i86_lea = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            i86_ICount[0] -= cycles.lea;
            (GetEA[ModRM]).handler();
            SetRegWord(ModRM, EO & 0xFFFF);
        }
    };
    static InstructionPtr i86_mov_sregw = new InstructionPtr() /* Opcode 0x8e */ {
        public void handler() {
            int /*unsigned*/ ModRM = FETCH();
            int /*WORD*/ src = GetRMWord(ModRM) & 0xFFFF;

            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mov_sr : cycles.mov_sm;
            /*TODO*///#ifdef I286
/*TODO*///    switch (ModRM & 0x38)
/*TODO*///    {
/*TODO*///    case 0x00:  /* mov es,ew */
/*TODO*///		i286_data_descriptor(ES,src);
/*TODO*///		break;
/*TODO*///    case 0x18:  /* mov ds,ew */
/*TODO*///		i286_data_descriptor(DS,src);
/*TODO*///		break;
/*TODO*///    case 0x10:  /* mov ss,ew */
/*TODO*///		i286_data_descriptor(SS,src);
/*TODO*///		PREFIX(_instruction)[FETCHOP]();
/*TODO*///		break;
/*TODO*///    case 0x08:  /* mov cs,ew */
/*TODO*///		break;  /* doesn't do a jump far */
/*TODO*///    }
/*TODO*///#else
            switch (ModRM & 0x38) {
                case 0x00:
                    /* mov es,ew */
                    I.sregs[ES] = src;
                    I.base[ES] = SegBase(ES);
                    break;
                case 0x18:
                    /* mov ds,ew */
                    I.sregs[DS] = src;
                    I.base[DS] = SegBase(DS);
                    break;
                case 0x10:
                    /* mov ss,ew */
                    I.sregs[SS] = src;
                    I.base[SS] = SegBase(SS);/* no interrupt allowed before next instr */
                    i86_instruction[FETCHOP()].handler();
                    break;
                case 0x08:
                    /* mov cs,ew */
                    break;
                /* doesn't do a jump far */
            }
            /*TODO*///#endif
        }
    };
    static InstructionPtr i86_popw = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            /*WORD*/
            int tmp;
            tmp = POP();
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.pop_r16 : cycles.pop_m16;
            PutRMWord(ModRM, tmp & 0xFFFF);
        }
    };

    public static void XchgAXReg(int Reg) {
        /*WORD*/
        int tmp;
        tmp = I.regs.w[Reg];
        I.regs.SetW(Reg, I.regs.w[AX]);
        I.regs.SetW(AX, tmp & 0xFFFF);
        i86_ICount[0] -= cycles.xchg_ar16;
    }
    static InstructionPtr i86_nop = new InstructionPtr() /* Opcode 0x90 */ {
        public void handler() {
            /* this is XchgAXReg(AX); */
            i86_ICount[0] -= cycles.nop;
        }
    };
    static InstructionPtr i86_xchg_axcx = new InstructionPtr() {
        public void handler() {
            XchgAXReg(CX);
        }
    };
    static InstructionPtr i86_xchg_axdx = new InstructionPtr() {
        public void handler() {
            XchgAXReg(DX);
        }
    };
    static InstructionPtr i86_xchg_axbx = new InstructionPtr() {
        public void handler() {
            XchgAXReg(BX);
        }
    };
    static InstructionPtr i86_xchg_axsp = new InstructionPtr() {
        public void handler() {
            XchgAXReg(SP);
        }
    };
    static InstructionPtr i86_xchg_axbp = new InstructionPtr() {
        public void handler() {
            XchgAXReg(BP);
        }
    };
    static InstructionPtr i86_xchg_axsi = new InstructionPtr() {
        public void handler() {
            XchgAXReg(SI);
        }
    };
    static InstructionPtr i86_xchg_axdi = new InstructionPtr() {
        public void handler() {
            XchgAXReg(DI);
        }
    };
    static InstructionPtr i86_cbw = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.cbw;
            I.regs.SetB(AH, (I.regs.b[AL] & 0x80) != 0 ? 0xff : 0);
        }
    };
    static InstructionPtr i86_cwd = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.cwd;
            I.regs.SetW(DX, (I.regs.b[AH] & 0x80) != 0 ? 0xffff : 0);
        }
    };
    static InstructionPtr i86_call_far = new InstructionPtr() {
        public void handler() {

            int tmp, tmp2;
            int /*WORD*/ ip;
            tmp = FETCH();
            tmp += FETCH() << 8;

            tmp2 = FETCH();
            tmp2 += FETCH() << 8;

            ip = (I.pc - I.base[CS]) & 0xFFFF;
            PUSH(I.sregs[CS]);
            PUSH(ip);
            /*TODO*///
/*TODO*///#ifdef I286
/*TODO*///	i286_code_descriptor(tmp2, tmp);
/*TODO*///#else
            I.sregs[CS] = tmp2 & 0xFFFF;
            I.base[CS] = SegBase(CS);
            I.pc = (I.base[CS] + (tmp & 0xFFFF)) & AMASK;
            /*TODO*///#endif
            i86_ICount[0] -= cycles.call_far;
            change_pc20(I.pc);
        }
    };
    static InstructionPtr i86_wait = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.wait;
        }
    };
    static InstructionPtr i86_pushf = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.pushf;
            /*TODO*///#ifdef I286
/*TODO*///    PUSH( CompressFlags() | 0xc000 );
/*TODO*///#elif defined V20
/*TODO*///    PUSH( CompressFlags() | 0xe000 );
/*TODO*///#else
            PUSH(CompressFlags() | 0xf000);
        }
    };
    static InstructionPtr i86_popf = new InstructionPtr() {
        public void handler() {
            int tmp;
            tmp = POP();
            i86_ICount[0] -= cycles.popf;
            ExpandFlags(tmp);

            if (I.TF != 0) {
                throw new UnsupportedOperationException("Unsupported");
                //PREFIX(_trap)();
            }

            /* if the IF is set, and an interrupt is pending, signal an interrupt */
            if (I.IF != 0 && I.irq_state != 0) {
                i86_interrupt(-1);
            }
        }
    };
    static InstructionPtr i86_sahf = new InstructionPtr() {
        public void handler() {
            int tmp = (CompressFlags() & 0xff00) | (I.regs.b[AH] & 0xd5);
            i86_ICount[0] -= cycles.sahf;
            ExpandFlags(tmp);
        }
    };
    static InstructionPtr i86_lahf = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(AH, CompressFlags() & 0xff);
            i86_ICount[0] -= cycles.lahf;
        }
    };
    static InstructionPtr i86_mov_aldisp = new InstructionPtr() /* Opcode 0xa0 */ {
        public void handler() {
            /*unsigned*/
            int addr;

            addr = FETCH();
            addr += FETCH() << 8;
            i86_ICount[0] -= cycles.mov_am8;
            I.regs.SetB(AL, GetMemB(DS, addr));
        }
    };
    static InstructionPtr i86_mov_axdisp = new InstructionPtr() /* Opcode 0xa1 */ {
        public void handler() {
            /*unsigned*/
            int addr;

            addr = FETCH();
            addr += FETCH() << 8;
            I.regs.SetB(AL, GetMemB(DS, addr));
            I.regs.SetB(AH, GetMemB(DS, addr + 1));
        }
    };
    static InstructionPtr i86_mov_dispal = new InstructionPtr() {
        public void handler() {
            int addr;
            addr = FETCH();
            addr += FETCH() << 8;
            i86_ICount[0] -= cycles.mov_ma8;
            PutMemB(DS, addr, I.regs.b[AL]);
        }
    };
    static InstructionPtr i86_mov_dispax = new InstructionPtr() {
        public void handler() {
            int addr;
            addr = FETCH();
            addr += FETCH() << 8;
            i86_ICount[0] -= cycles.mov_ma16;
            PutMemB(DS, addr, I.regs.b[AL]);
            PutMemB(DS, addr + 1, I.regs.b[AH]);
        }
    };
    static InstructionPtr i86_movsb = new InstructionPtr() {
        public void handler() {
            int/*BYTE*/ tmp = GetMemB(DS, I.regs.w[SI]) & 0xFF;
            PutMemB(ES, I.regs.w[DI], tmp);
            I.regs.SetW(DI, I.regs.w[DI] + I.DirVal);
            I.regs.SetW(SI, I.regs.w[SI] + I.DirVal);
            i86_ICount[0] -= cycles.movs8;
        }
    };
    static InstructionPtr i86_movsw = new InstructionPtr() {
        public void handler() {
            int/*WORD*/ tmp = GetMemW(DS, I.regs.w[SI]) & 0xFFFF;
            PutMemW(ES, I.regs.w[DI], tmp);
            I.regs.SetW(DI, I.regs.w[DI] + 2 * I.DirVal);
            I.regs.SetW(SI, I.regs.w[SI] + 2 * I.DirVal);
            i86_ICount[0] -= cycles.movs16;
        }
    };

    static InstructionPtr i86_cmpsb = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_cmpsb)(void)    /* Opcode 0xa6 */
/*TODO*///{
/*TODO*///	unsigned dst = GetMemB(ES, I.regs.w[DI]);
/*TODO*///	unsigned src = GetMemB(DS, I.regs.w[SI]);
/*TODO*///    SUBB(src,dst); /* opposite of the usual convention */
/*TODO*///	I.regs.w[DI] += I.DirVal;
/*TODO*///	I.regs.w[SI] += I.DirVal;
/*TODO*///	i86_ICount[0] -= cycles.cmps8;
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_cmpsw = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_cmpsw)(void)    /* Opcode 0xa7 */
/*TODO*///{
/*TODO*///	unsigned dst = GetMemW(ES, I.regs.w[DI]);
/*TODO*///	unsigned src = GetMemW(DS, I.regs.w[SI]);
/*TODO*///	SUBW(src,dst); /* opposite of the usual convention */
/*TODO*///	I.regs.w[DI] += 2 * I.DirVal;
/*TODO*///	I.regs.w[SI] += 2 * I.DirVal;
/*TODO*///	i86_ICount[0] -= cycles.cmps16;
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_test_ald8 = new InstructionPtr() {
        public void handler() {
            //DEF_ald8(dst,src);
            int src = FETCHOP();
            int dst = I.regs.b[AL];
            i86_ICount[0] -= cycles.alu_ri8;
            //ANDB(dst,src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Byte(dst);
        }
    };
    static InstructionPtr i86_test_axd16 = new InstructionPtr() {
        public void handler() {
            //DEF_axd16(dst,src);
            int src = FETCHOP();
            int dst = I.regs.w[AX];
            src += (FETCH() << 8);
            i86_ICount[0] -= cycles.alu_ri16;
            //ANDW(dst,src);
            dst &= src;
            I.CarryVal = I.OverVal = I.AuxVal = 0;
            SetSZPF_Word(dst);
        }
    };
    static InstructionPtr i86_stosb = new InstructionPtr() {
        public void handler() {
            PutMemB(ES, I.regs.w[DI], I.regs.b[AL]);
            I.regs.SetW(DI, I.regs.w[DI] + I.DirVal);
            i86_ICount[0] -= cycles.stos8;
        }
    };
    static InstructionPtr i86_stosw = new InstructionPtr() /* Opcode 0xab */ {
        public void handler() {
            PutMemB(ES, I.regs.w[DI], I.regs.b[AL]);
            PutMemB(ES, I.regs.w[DI] + 1, I.regs.b[AH]);
            I.regs.SetW(DI, I.regs.w[DI] + 2 * I.DirVal);
            i86_ICount[0] -= cycles.stos16;
        }
    };
    static InstructionPtr i86_lodsb = new InstructionPtr() /* Opcode 0xac */ {
        public void handler() {
            I.regs.SetB(AL, GetMemB(DS, I.regs.w[SI]));
            I.regs.SetW(SI, I.regs.w[SI] + I.DirVal);
            i86_ICount[0] -= cycles.lods8;
        }
    };
    static InstructionPtr i86_lodsw = new InstructionPtr() {
        public void handler() {
            I.regs.SetW(AX, GetMemW(DS, I.regs.w[SI]));
            I.regs.SetW(SI, I.regs.w[SI] + 2 * I.DirVal);
            i86_ICount[0] -= cycles.lods16;
        }
    };
    static InstructionPtr i86_scasb = new InstructionPtr() {
        public void handler() {
            int src = GetMemB(ES, I.regs.w[DI]);
            int dst = I.regs.b[AL];
            //SUBB(dst,src);
            int res = dst - src;
            SetCFB(res);
            SetOFB_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Byte(res);
            I.regs.SetW(DI, I.regs.w[DI] + I.DirVal);
            i86_ICount[0] -= cycles.scas8;
        }
    };
    static InstructionPtr i86_scasw = new InstructionPtr() {
        public void handler() {
            int/*unsigned*/ src = GetMemW(ES, I.regs.w[DI]);
            int/*unsigned*/ dst = I.regs.w[AX];
            // SUBW(dst,src);
            int/*unsigned*/ res = dst - src;
            SetCFW(res);
            SetOFW_Sub(res, src, dst);
            SetAF(res, src, dst);
            SetSZPF_Word(res);
            dst = res & 0xFFFF;
            I.regs.SetW(DI, I.regs.w[DI] + 2 * I.DirVal);
            i86_ICount[0] -= cycles.scas16;
        }
    };
    static InstructionPtr i86_mov_ald8 = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(AL, FETCH());
            i86_ICount[0] -= cycles.mov_ri8;
        }
    };
    static InstructionPtr i86_mov_cld8 = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(CL, FETCH());
            i86_ICount[0] -= cycles.mov_ri8;
        }
    };
    static InstructionPtr i86_mov_dld8 = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(DL, FETCH());
            i86_ICount[0] -= cycles.mov_ri8;
        }
    };
    static InstructionPtr i86_mov_bld8 = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(BL, FETCH());
            i86_ICount[0] -= cycles.mov_ri8;
        }
    };
    static InstructionPtr i86_mov_ahd8 = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(AH, FETCH());
            i86_ICount[0] -= cycles.mov_ri8;
        }
    };
    static InstructionPtr i86_mov_chd8 = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(CH, FETCH());
            i86_ICount[0] -= cycles.mov_ri8;
        }
    };
    static InstructionPtr i86_mov_dhd8 = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(DH, FETCH());
            i86_ICount[0] -= cycles.mov_ri8;
        }
    };
    static InstructionPtr i86_mov_bhd8 = new InstructionPtr() {
        public void handler() {
            I.regs.SetB(BH, FETCH());
            i86_ICount[0] -= cycles.mov_ri8;
        }
    };
    static InstructionPtr i86_mov_axd16 = new InstructionPtr() /* Opcode 0xb8 */ {
        public void handler() {
            I.regs.SetB(AL, FETCH());//I.regs.b[AL] = FETCH;
            I.regs.SetB(AH, FETCH());//I.regs.b[AH] = FETCH;
            i86_ICount[0] -= cycles.mov_ri16;
        }
    };
    static InstructionPtr i86_mov_cxd16 = new InstructionPtr() /* Opcode 0xb9 */ {
        public void handler() {

            I.regs.SetB(CL, FETCH());
            I.regs.SetB(CH, FETCH());
            i86_ICount[0] -= cycles.mov_ri16;
        }
    };
    static InstructionPtr i86_mov_dxd16 = new InstructionPtr() /* Opcode 0xba */ {
        public void handler() {
            I.regs.SetB(DL, FETCH());
            I.regs.SetB(DH, FETCH());
            i86_ICount[0] -= cycles.mov_ri16;
        }
    };
    static InstructionPtr i86_mov_bxd16 = new InstructionPtr() /* Opcode 0xbb */ {
        public void handler() {
            I.regs.SetB(BL, FETCH());
            I.regs.SetB(BH, FETCH());
            i86_ICount[0] -= cycles.mov_ri16;
        }
    };
    static InstructionPtr i86_mov_spd16 = new InstructionPtr() /* Opcode 0xbc */ {
        public void handler() {
            I.regs.SetB(SPL, FETCH());
            I.regs.SetB(SPH, FETCH());
            i86_ICount[0] -= cycles.mov_ri16;
        }
    };
    static InstructionPtr i86_mov_bpd16 = new InstructionPtr() /* Opcode 0xbd */ {
        public void handler() {
            I.regs.SetB(BPL, FETCH());
            I.regs.SetB(BPH, FETCH());
            i86_ICount[0] -= cycles.mov_ri16;
        }
    };
    static InstructionPtr i86_mov_sid16 = new InstructionPtr() /* Opcode 0xbe */ {
        public void handler() {
            I.regs.SetB(SIL, FETCH());
            I.regs.SetB(SIH, FETCH());
            i86_ICount[0] -= cycles.mov_ri16;
        }
    };
    static InstructionPtr i86_mov_did16 = new InstructionPtr() /* Opcode 0xbf */ {
        public void handler() {
            I.regs.SetB(DIL, FETCH());
            I.regs.SetB(DIH, FETCH());
            i86_ICount[0] -= cycles.mov_ri16;
        }
    };

    static InstructionPtr i86_ret_d16 = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///
/*TODO*///static void PREFIX86(_ret_d16)(void)    /* Opcode 0xc2 */
/*TODO*///{
/*TODO*///	unsigned count = FETCH;
/*TODO*///	count += FETCH << 8;
/*TODO*///	POP(I.pc);
/*TODO*///	I.pc = (I.pc + I.base[CS]) & AMASK;
/*TODO*///	I.regs.w[SP]+=count;
/*TODO*///	i86_ICount[0] -= cycles.ret_near_imm;
/*TODO*///	CHANGE_PC(I.pc);
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_ret = new InstructionPtr() {
        public void handler() {
            I.pc = POP();
            I.pc = (I.pc + I.base[CS]) & AMASK;
            i86_ICount[0] -= cycles.ret_near;
            change_pc20(I.pc);
        }
    };
    static InstructionPtr i86_les_dw = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            /*WORD*/
            int tmp = GetRMWord(ModRM);

            SetRegWord(ModRM, tmp & 0xFFFF);
            /*TODO*///#ifdef I286
/*TODO*///	i286_data_descriptor(ES,GetnextRMWord);
/*TODO*///#else
            I.sregs[ES] = GetnextRMWord();
            I.base[ES] = SegBase(ES);
            /*TODO*///#endif
            i86_ICount[0] -= cycles.load_ptr;
        }
    };

    static InstructionPtr i86_lds_dw = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_lds_dw)(void)    /* Opcode 0xc5 */
/*TODO*///{
/*TODO*///	unsigned ModRM = FETCH;
/*TODO*///    WORD tmp = GetRMWord(ModRM);
/*TODO*///
/*TODO*///    RegWord(ModRM)=tmp;
/*TODO*///#ifdef I286
/*TODO*///	i286_data_descriptor(DS,GetnextRMWord);
/*TODO*///#else
/*TODO*///	I.sregs[DS] = GetnextRMWord;
/*TODO*///	I.base[DS] = SegBase(DS);
/*TODO*///#endif
/*TODO*///	i86_ICount[0] -= cycles.load_ptr;
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_mov_bd8 = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mov_ri8 : cycles.mov_mi8;
            PutImmRMByte(ModRM);
        }
    };
    static InstructionPtr i86_mov_wd16 = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mov_ri16 : cycles.mov_mi16;
            PutImmRMWord(ModRM);
        }
    };
    static InstructionPtr i86_retf_d16 = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_retf_d16)(void)    /* Opcode 0xca */
/*TODO*///{
/*TODO*///	unsigned count = FETCH;
/*TODO*///	count += FETCH << 8;
/*TODO*///
/*TODO*///#ifdef I286
/*TODO*///	{ 
/*TODO*///		int tmp, tmp2;
/*TODO*///		POP(tmp2);
/*TODO*///		POP(tmp);
/*TODO*///		i286_code_descriptor(tmp, tmp2);
/*TODO*///	}
/*TODO*///#else
/*TODO*///	POP(I.pc);
/*TODO*///	POP(I.sregs[CS]);
/*TODO*///	I.base[CS] = SegBase(CS);
/*TODO*///	I.pc = (I.pc + I.base[CS]) & AMASK;
/*TODO*///#endif
/*TODO*///	I.regs.w[SP]+=count;
/*TODO*///	i86_ICount[0] -= cycles.ret_far_imm;
/*TODO*///	CHANGE_PC(I.pc);
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_retf = new InstructionPtr() {
        public void handler() {
            /*TODO*///#ifdef I286
/*TODO*///	{ 
/*TODO*///		int tmp, tmp2;
/*TODO*///		POP(tmp2);
/*TODO*///		POP(tmp);
/*TODO*///		i286_code_descriptor(tmp, tmp2);
/*TODO*///	}
/*TODO*///#else
            I.pc = POP();
            I.sregs[CS] = POP();
            I.base[CS] = SegBase(CS);
            I.pc = (I.pc + I.base[CS]) & AMASK;
            /*TODO*///#endif
            i86_ICount[0] -= cycles.ret_far;
            change_pc20(I.pc);
        }
    };
    static InstructionPtr i86_int3 = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///
/*TODO*///static void PREFIX86(_int3)(void)    /* Opcode 0xcc */
/*TODO*///{
/*TODO*///	i86_ICount[0] -= cycles.int3;
/*TODO*///#ifdef V20
/*TODO*///	PREFIX(_interrupt)(3,0);
/*TODO*///#else
/*TODO*///	PREFIX(_interrupt)(3);
/*TODO*///#endif
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_int = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_int)(void)    /* Opcode 0xcd */
/*TODO*///{
/*TODO*///	unsigned int_num = FETCH;
/*TODO*///	i86_ICount[0] -= cycles.int_imm;
/*TODO*///#ifdef V20
/*TODO*///	PREFIX(_interrupt)(int_num,0);
/*TODO*///#else
/*TODO*///	PREFIX(_interrupt)(int_num);
/*TODO*///#endif
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_into = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_into)(void)    /* Opcode 0xce */
/*TODO*///{
/*TODO*///	if (OF) {
/*TODO*///		i86_ICount[0] -= cycles.into_t;
/*TODO*///#ifdef V20
/*TODO*///		PREFIX(_interrupt)(4,0);
/*TODO*///#else
/*TODO*///		PREFIX(_interrupt)(4);
/*TODO*///#endif
/*TODO*///	} else i86_ICount[0] -= cycles.into_nt;
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_iret = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.iret;
            /*TODO*///#ifdef I286
/*TODO*///	{ 
/*TODO*///		int tmp, tmp2;
/*TODO*///		POP(tmp2);
/*TODO*///		POP(tmp);
/*TODO*///		i286_code_descriptor(tmp, tmp2);
/*TODO*///	}
/*TODO*///#else
            I.pc = POP();
            I.sregs[CS] = POP();
            I.base[CS] = SegBase(CS);
            I.pc = (I.pc + I.base[CS]) & AMASK;
            /*TODO*///#endif
            i86_popf.handler();
            change_pc20(I.pc);

            /* if the IF is set, and an interrupt is pending, signal an interrupt */
            if (I.IF != 0 && I.irq_state != 0) {
                i86_interrupt(-1);
            }
        }
    };
    static InstructionPtr i86_rotshft_b = new InstructionPtr() {
        public void handler() {
            i86_rotate_shift_Byte(FETCHOP(), 1);
        }
    };
    static InstructionPtr i86_rotshft_w = new InstructionPtr() {
        public void handler() {
            i86_rotate_shift_Word(FETCHOP(), 1);
        }
    };
    static InstructionPtr i86_rotshft_bcl = new InstructionPtr() {
        public void handler() {
            i86_rotate_shift_Byte(FETCHOP(), I.regs.b[CL]);
        }
    };
    static InstructionPtr i86_rotshft_wcl = new InstructionPtr() {
        public void handler() {
            i86_rotate_shift_Word(FETCHOP(), I.regs.b[CL]);
        }
    };

    static InstructionPtr i86_aam = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*////* OB: Opcode works on NEC V-Series but not the Variants              */
/*TODO*////*     one could specify any byte value as operand but the NECs */
/*TODO*////*     always substitute 0x0a.              */
/*TODO*///static void PREFIX86(_aam)(void)    /* Opcode 0xd4 */
/*TODO*///{
/*TODO*///	unsigned mult = FETCH;
/*TODO*///
/*TODO*///	i86_ICount[0] -= cycles.aam;
/*TODO*///#ifndef V20
/*TODO*///	if (mult == 0)
/*TODO*///		PREFIX(_interrupt)(0);
/*TODO*///	else
/*TODO*///	{
/*TODO*///		I.regs.b[AH] = I.regs.b[AL] / mult;
/*TODO*///		I.regs.b[AL] %= mult;
/*TODO*///
/*TODO*///		SetSZPF_Word(I.regs.w[AX]);
/*TODO*///	}
/*TODO*///#else
/*TODO*/// 
/*TODO*///	if (mult == 0) 
/*TODO*///		PREFIX(_interrupt)(0,0); 
/*TODO*///    else 
/*TODO*///    { 
/*TODO*///		I.regs.b[AH] = I.regs.b[AL] / 10; 
/*TODO*///		I.regs.b[AL] %= 10; 
/*TODO*///		SetSZPF_Word(I.regs.w[AX]); 
/*TODO*///    } 
/*TODO*///#endif
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_aad = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_aad)(void)    /* Opcode 0xd5 */
/*TODO*///{
/*TODO*///	unsigned mult = FETCH;
/*TODO*///
/*TODO*///	i86_ICount[0] -= cycles.aad;
/*TODO*///
/*TODO*///#ifndef V20
/*TODO*///	I.regs.b[AL] = I.regs.b[AH] * mult + I.regs.b[AL];
/*TODO*///	I.regs.b[AH] = 0;
/*TODO*///
/*TODO*///	SetZF(I.regs.b[AL]);
/*TODO*///	SetPF(I.regs.b[AL]);
/*TODO*///	I.SignVal = 0;
/*TODO*///#else
/*TODO*////* OB: Opcode works on NEC V-Series but not the Variants 	*/ 
/*TODO*////*     one could specify any byte value as operand but the NECs */ 
/*TODO*////*     always substitute 0x0a.					*/ 
/*TODO*///	I.regs.b[AL] = I.regs.b[AH] * 10 + I.regs.b[AL]; 
/*TODO*///	I.regs.b[AH] = 0; 
/*TODO*/// 
/*TODO*///	SetZF(I.regs.b[AL]); 
/*TODO*///	SetPF(I.regs.b[AL]); 
/*TODO*///	I.SignVal = 0; 
/*TODO*///	mult=0; 
/*TODO*///#endif
/*TODO*///} 
/*TODO*///
/*TODO*///
    static InstructionPtr i86_xlat = new InstructionPtr() {
        public void handler() {
            int dest = I.regs.w[BX] + I.regs.b[AL];
            i86_ICount[0] -= cycles.xlat;
            I.regs.SetB(AL, GetMemB(DS, dest) & 0xFF);
        }
    };
    static InstructionPtr i86_escape = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_escape)(void)    /* Opcodes 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde and 0xdf */
/*TODO*///{
/*TODO*///	unsigned ModRM = FETCH;
/*TODO*///	i86_ICount[0] -= cycles.nop;
/*TODO*///    GetRMByte(ModRM);
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_loopne = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_loopne)(void)    /* Opcode 0xe0 */
/*TODO*///{
/*TODO*///	int disp = (int)((INT8)FETCH);
/*TODO*///	unsigned tmp = I.regs.w[CX]-1;
/*TODO*///
/*TODO*///	I.regs.w[CX]=tmp;
/*TODO*///
/*TODO*///    if (!ZF && tmp) {
/*TODO*///		i86_ICount[0] -= cycles.loop_t;
/*TODO*///		I.pc += disp;
/*TODO*////* ASG - can probably assume this is safe
/*TODO*///		CHANGE_PC(I.pc);*/
/*TODO*///	} else i86_ICount[0] -= cycles.loop_nt;
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_loope = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_loope)(void)    /* Opcode 0xe1 */
/*TODO*///{
/*TODO*///	int disp = (int)((INT8)FETCH);
/*TODO*///	unsigned tmp = I.regs.w[CX]-1;
/*TODO*///
/*TODO*///	I.regs.w[CX]=tmp;
/*TODO*///
/*TODO*///	if (ZF && tmp) {
/*TODO*///		i86_ICount[0] -= cycles.loope_t;
/*TODO*///		 I.pc += disp;
/*TODO*////* ASG - can probably assume this is safe
/*TODO*///		 CHANGE_PC(I.pc);*/
/*TODO*///	 } else i86_ICount[0] -= cycles.loope_nt;
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_loop = new InstructionPtr()/* Opcode 0xe2 */ {
        public void handler() {
            int disp = (int) ((byte) FETCH());
            int/*unsigned*/ tmp = I.regs.w[CX] - 1;
            I.regs.SetW(CX, tmp);
            if (tmp != 0) {
                i86_ICount[0] -= cycles.loop_t;
                I.pc = (I.pc + disp) & AMASK;//I.pc += disp;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.loop_nt;
            }
        }
    };
    static InstructionPtr i86_jcxz = new InstructionPtr() {
        public void handler() {
            int disp = (int) ((byte) FETCH());

            if (I.regs.w[CX] == 0) {
                i86_ICount[0] -= cycles.jcxz_t;
                I.pc = (I.pc + disp) & AMASK;//I.pc += disp;
                /* ASG - can probably assume this is safe
		CHANGE_PC(I.pc);*/
            } else {
                i86_ICount[0] -= cycles.jcxz_nt;
            }
        }
    };
    static InstructionPtr i86_inal = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_inal)(void)    /* Opcode 0xe4 */
/*TODO*///{
/*TODO*///	unsigned port = FETCH;
/*TODO*///
/*TODO*///	i86_ICount[0] -= cycles.in_imm8;
/*TODO*///	I.regs.b[AL] = read_port(port);
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_inax = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_inax)(void)    /* Opcode 0xe5 */
/*TODO*///{
/*TODO*///	unsigned port = FETCH;
/*TODO*///
/*TODO*///	i86_ICount[0] -= cycles.in_imm16;
/*TODO*///	I.regs.b[AL] = read_port(port);
/*TODO*///	I.regs.b[AH] = read_port(port+1);
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_outal = new InstructionPtr() {
        public void handler() {
            int/*unsigned*/ port = FETCH();
            i86_ICount[0] -= cycles.out_imm8;
            write_port(port, I.regs.b[AL]);
        }
    };

    static InstructionPtr i86_outax = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///static void PREFIX86(_outax)(void)    /* Opcode 0xe7 */
/*TODO*///{
/*TODO*///	unsigned port = FETCH;
/*TODO*///
/*TODO*///	i86_ICount[0] -= cycles.out_imm16;
/*TODO*///	write_port(port, I.regs.b[AL]);
/*TODO*///	write_port(port+1, I.regs.b[AH]);
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_call_d16 = new InstructionPtr() {
        public void handler() {
            int/*/WORD*/ ip, tmp;

            tmp = FETCHWORD() & 0xFFFF;
            ip = (I.pc - I.base[CS]) & 0xFFFF;
            PUSH(ip);
            ip = (ip + tmp) & 0xFFFF;
            I.pc = (ip + I.base[CS]) & AMASK;
            i86_ICount[0] -= cycles.call_near;
            change_pc20(I.pc);
        }
    };
    static InstructionPtr i86_jmp_d16 = new InstructionPtr() {
        public void handler() {
            int/*WORD*/ ip, tmp;

            tmp = FETCHWORD() & 0xFFFF;
            ip = (I.pc - I.base[CS] + tmp) & 0xFFFF;
            I.pc = (ip + I.base[CS]) & AMASK;
            i86_ICount[0] -= cycles.jmp_near;
            change_pc20(I.pc);
        }
    };
    static InstructionPtr i86_jmp_far = new InstructionPtr() /* Opcode 0xea */ {
        public void handler() {
            int/*unsigned*/ tmp, tmp1;

            tmp = FETCH();
            tmp += FETCH() << 8;

            tmp1 = FETCH();
            tmp1 += FETCH() << 8;

            /*TODO*///#ifdef I286
/*TODO*///	i286_code_descriptor(tmp1,tmp);
/*TODO*///#else
            I.sregs[CS] = tmp1 & 0xFFFF;
            I.base[CS] = SegBase(CS);
            I.pc = (I.base[CS] + tmp) & AMASK;
            /*TODO*///#endif
            i86_ICount[0] -= cycles.jmp_far;
            change_pc20(I.pc);
        }
    };
    static InstructionPtr i86_jmp_d8 = new InstructionPtr() {
        public void handler() {
            int tmp = (int) ((byte) FETCH());
            I.pc += tmp;
            /* ASG - can probably assume this is safe
	CHANGE_PC(I.pc);*/
            i86_ICount[0] -= cycles.jmp_short;
        }
    };

    static InstructionPtr i86_inaldx = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.in_dx8;
            I.regs.b[AL] = read_port(I.regs.w[DX]);
        }
    };
    /*TODO*///
/*TODO*///static void PREFIX86(_inaldx)(void)    /* Opcode 0xec */
/*TODO*///{
/*TODO*///	i86_ICount[0] -= cycles.in_dx8;
/*TODO*///	I.regs.b[AL] = read_port(I.regs.w[DX]);
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_inaxdx = new InstructionPtr() {
        public void handler() {
            int port = I.regs.w[DX];
            
            i86_ICount[0] -= cycles.in_dx16;
            I.regs.b[AL] = read_port(port);
            I.regs.b[AH] = read_port(port+1);
            
        }
    };
    /*TODO*///static void PREFIX86(_inaxdx)(void)    /* Opcode 0xed */
/*TODO*///{
/*TODO*///	unsigned port = I.regs.w[DX];
/*TODO*///
/*TODO*///	i86_ICount[0] -= cycles.in_dx16;
/*TODO*///	I.regs.b[AL] = read_port(port);
/*TODO*///	I.regs.b[AH] = read_port(port+1);
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_outdxal = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.out_dx8;
            write_port(I.regs.w[DX], I.regs.b[AL]);
        }
    };
    static InstructionPtr i86_outdxax = new InstructionPtr() {/* Opcode 0xef */
        public void handler() {
            int/*unsigned*/ port = I.regs.w[DX];

            i86_ICount[0] -= cycles.out_dx16;
            write_port(port, I.regs.b[AL]);
            write_port(port + 1, I.regs.b[AH]);
        }
    };

    static InstructionPtr i86_lock = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.nop;
            i86_instruction[FETCHOP()].handler();/* un-interruptible */
        }
    };

    static InstructionPtr i86_repne = new InstructionPtr() {
        public void handler() {
            i86_rep(0);
        }
    };

    static InstructionPtr i86_repe = new InstructionPtr() {
        public void handler() {
            i86_rep(1);
        }
    };

    static InstructionPtr i86_hlt = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///#ifndef I186
/*TODO*///static void PREFIX86(_hlt)(void)    /* Opcode 0xf4 */
/*TODO*///{
/*TODO*///	I.pc--;
/*TODO*///	i86_ICount[0] = 0;
/*TODO*///}
/*TODO*///
    static InstructionPtr i86_cmc = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.flag_ops;
            I.CarryVal = NOT(CF());
        }
    };

    static InstructionPtr i86_f6pre = new InstructionPtr() {
        public void handler() {
            /* Opcode 0xf6 */
            int /*unsigned*/ ModRM = FETCH();
            int /*unsigned*/ tmp = GetRMByte(ModRM);
            int /*unsigned*/ tmp2;

            switch (ModRM & 0x38) {
                case 0x00:
                /* TEST Eb, data8 */
                case 0x08:
                    /* ??? */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri8 : cycles.alu_mi8_ro;
                    tmp &= FETCH();

                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Byte(tmp);
                    break;
                case 0x10:
                    /* NOT Eb */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.negnot_r8 : cycles.negnot_m8;
                    PutbackRMByte(ModRM, (~tmp) & 0xFF);
                    break;
                case 0x18: /* NEG Eb */ {
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.negnot_r8 : cycles.negnot_m8;
                    tmp2 = 0;
                    //SUBB(tmp2,tmp);
                    int res = tmp2 - tmp;
                    SetCFB(res);
                    SetOFB_Sub(res, tmp, tmp2);
                    SetAF(res, tmp, tmp2);
                    SetSZPF_Byte(res);
                    tmp2 = res & 0xFF;
                    PutbackRMByte(ModRM, tmp2);
                }
                break;
                case 0x20:
                    /* MUL AL, Eb */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mul_r8 : cycles.mul_m8;
                     {
                        int/*UINT16*/ result;
                        tmp2 = I.regs.b[AL];

                        SetSF((byte) tmp2);
                        SetPF(tmp2);

                        result = (tmp2 & 0xFFFF) * tmp;
                        I.regs.SetW(AX, result & 0xFFFF);

                        SetZF(I.regs.w[AX]);
                        I.CarryVal = I.OverVal = (I.regs.b[AH] != 0) ? 1 : 0;
                    }
                    break;
                case 0x28:
                    /* IMUL AL, Eb */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.imul_r8 : cycles.imul_m8;
                     {
                        short result;
                        tmp2 = /*(unsigned)*/ I.regs.b[AL];
                        SetSF((byte) tmp2);
                        SetPF(tmp2);
                        result = (short) ((short) ((byte) tmp2) * (short) ((byte) tmp));
                        I.regs.SetW(AX, result & 0xFFFF);
                        SetZF(I.regs.w[AX]);
                        I.CarryVal = I.OverVal = ((result >> 7 != 0) && (result >> 7 != -1)) ? 1 : 0;
                    }
                    break;
                case 0x30: {
                    /* DIV AL, Ew */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.div_r8 : cycles.div_m8;
                    {
                        int/*UINT16*/ result;

                        result = I.regs.w[AX] & 0xFFFF;

                        if (tmp != 0) {
                            if ((result / tmp) > 0xff) {
                                i86_interrupt(0);
                                break;
                            } else {
                                I.regs.SetB(AH, (result % tmp) & 0xFF);
                                I.regs.SetB(AL, (result / tmp) & 0xFF);
                            }
                        } else {
                            i86_interrupt(0);
                            break;
                        }
                    }
                }
                break;
                /*TODO*///    case 0x38:  /* IDIV AL, Ew */
/*TODO*///		i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.idiv_r8 : cycles.idiv_m8;
/*TODO*///		{
/*TODO*///
/*TODO*///			INT16 result;
/*TODO*///
/*TODO*///			result = I.regs.w[AX];
/*TODO*///
/*TODO*///			if (tmp)
/*TODO*///			{
/*TODO*///				tmp2 = result % (INT16)((INT8)tmp);
/*TODO*///
/*TODO*///				if ((result /= (INT16)((INT8)tmp)) > 0xff)
/*TODO*///				{
/*TODO*///#ifdef V20
/*TODO*///					PREFIX(_interrupt)(0,0);
/*TODO*///#else
/*TODO*///					PREFIX(_interrupt)(0);
/*TODO*///#endif
/*TODO*///					break;
/*TODO*///				}
/*TODO*///				else
/*TODO*///				{
/*TODO*///					I.regs.b[AL] = result;
/*TODO*///					I.regs.b[AH] = tmp2;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///#ifdef V20
/*TODO*///				PREFIX(_interrupt)(0,0);
/*TODO*///#else
/*TODO*///				PREFIX(_interrupt)(0);
/*TODO*///#endif
/*TODO*///				break;
/*TODO*///			}
/*TODO*///		}
/*TODO*///		break;
                default:
                    System.out.println("i_f6pre 0x" + Integer.toHexString(ModRM & 0x38));
                    throw new UnsupportedOperationException("Unsupported");
            }
        }
    };

    static InstructionPtr i86_f7pre = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            int tmp = GetRMWord(ModRM);
            int tmp2;

            switch (ModRM & 0x38) {
                case 0x00:
                /* TEST Ew, data16 */
                case 0x08: /* ??? */ {
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.alu_ri16 : cycles.alu_mi16_ro;
                    tmp2 = FETCH();
                    tmp2 += FETCH() << 8;
                    tmp &= tmp2;
                    I.CarryVal = I.OverVal = I.AuxVal = 0;
                    SetSZPF_Word(tmp);
                }
                break;
                case 0x10: /* NOT Ew */ {
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.negnot_r16 : cycles.negnot_m16;
                    tmp = ~tmp;
                    PutbackRMWord(ModRM, tmp & 0xFFFF);
                }
                break;
                case 0x18: /* NEG Ew */ {
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.negnot_r16 : cycles.negnot_m16;
                    tmp2 = 0;
                    //SUBW(tmp2,tmp);
                    int res = tmp2 - tmp;
                    SetCFW(res);
                    SetOFW_Sub(res, tmp, tmp2);
                    SetAF(res, tmp, tmp2);
                    SetSZPF_Word(res);
                    tmp2 = res & 0xFFFF;
                    PutbackRMWord(ModRM, tmp2);
                }
                break;
                case 0x20:
                    /* MUL AX, Ew */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.mul_r16 : cycles.mul_m16;
                     {
                        int/*UINT32*/ result;
                        tmp2 = I.regs.w[AX];

                        SetSF((tmp2 & 0xFFFF));
                        SetPF(tmp2);

                        result = /*(UINT32)*/ tmp2 * tmp;
                        I.regs.SetW(AX, result & 0xFFFF);
                        result >>>= 16;
                        I.regs.SetW(DX, result & 0xFFFF);

                        SetZF(I.regs.w[AX] | I.regs.w[DX]);
                        I.CarryVal = I.OverVal = (I.regs.w[DX] != 0) ? 1 : 0;
                    }
                    break;

                case 0x28:
                    /* IMUL AX, Ew */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.imul_r16 : cycles.imul_m16;
                     {
                        int result;
                        tmp2 = I.regs.w[AX];
                        SetSF((short) tmp2);
                        SetPF(tmp2);
                        result = (int) ((short) tmp2) * (int) ((short) tmp);
                        I.CarryVal = I.OverVal = ((result >> 15 != 0) && (result >> 15 != -1)) ? 1 : 0;
                        I.regs.SetW(AX, result & 0xFFFF);
                        result = (result >> 16) & 0xFFFF;
                        I.regs.SetW(DX, result);
                        SetZF(I.regs.w[AX] | I.regs.w[DX]);
                    }
                    break;
                case 0x30: /* DIV AX, Ew */ {
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.div_r16 : cycles.div_m16;
                    {
                        int/*UINT32*/ result;

                        result = ((/*(UINT32)*/I.regs.w[DX]) << 16) | I.regs.w[AX];

                        if (tmp != 0) {
                            tmp2 = result % tmp;
                            if ((result / tmp) > 0xffff) {
                                i86_interrupt(0);
                                break;
                            } else {
                                I.regs.SetW(DX, tmp2 & 0xFFFF);
                                result /= tmp;
                                I.regs.SetW(AX, result & 0xFFFF);
                            }
                        } else {
                            i86_interrupt(0);
                            break;
                        }
                    }
                }
                break;
                case 0x38:
                    /* IDIV AX, Ew */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.idiv_r16 : cycles.idiv_m16;
                     {
                        int result;

                        result = (I.regs.w[DX] << 16) + I.regs.w[AX];

                        if (tmp != 0) {
                            tmp2 = result % (int) ((short) tmp);
                            if ((result /= (int) ((short) tmp)) > 0xffff) {
                                i86_interrupt(0);
                                break;
                            } else {
                                I.regs.SetW(AX, result & 0xFFFF);
                                I.regs.SetW(DX, tmp2 & 0xFFFF);
                            }
                        } else {
                            i86_interrupt(0);
                            break;
                        }
                    }
                    break;
                default:
                    System.out.println("unexpected i_f7pre 0x" + Integer.toHexString(ModRM & 0x38));//should now reach here
            }
        }
    };
    static InstructionPtr i86_clc = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.flag_ops;
            I.CarryVal = 0;
        }
    };
    static InstructionPtr i86_stc = new InstructionPtr() {
        public void handler() {
            i86_ICount[0] -= cycles.flag_ops;
            I.CarryVal = 1;
        }
    };
    static InstructionPtr i86_cli = new InstructionPtr() /* Opcode 0xfa */ {
        public void handler() {
            i86_ICount[0] -= cycles.flag_ops;
            SetIF(0);
        }
    };
    static InstructionPtr i86_sti = new InstructionPtr() /* Opcode 0xfb */ {
        public void handler() {
            i86_ICount[0] -= cycles.flag_ops;
            SetIF(1);
            i86_instruction[FETCHOP()].handler();/* no interrupt before next instruction */

 /* if an interrupt is pending, signal an interrupt */
            if (I.irq_state != 0) {
                i86_interrupt(-1);
            }
        }
    };
    static InstructionPtr i86_cld = new InstructionPtr() /* Opcode 0xfc */ {
        public void handler() {
            i86_ICount[0] -= cycles.flag_ops;
            SetDF(0);
        }
    };
    static InstructionPtr i86_std = new InstructionPtr() /* Opcode 0xfd */ {
        public void handler() {
            i86_ICount[0] -= cycles.flag_ops;
            SetDF(1);
        }
    };
    static InstructionPtr i86_fepre = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCH();
            int tmp = GetRMByte(ModRM);
            int tmp1;
            i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.incdec_r8 : cycles.incdec_m8;
            if ((ModRM & 0x38) == 0) /* INC eb */ {
                tmp1 = tmp + 1;
                SetOFB_Add(tmp1, tmp, 1);
            } else /* DEC eb */ {
                tmp1 = tmp - 1;
                SetOFB_Sub(tmp1, 1, tmp);
            }

            SetAF(tmp1, tmp, 1);
            SetSZPF_Byte(tmp1);

            PutbackRMByte(ModRM, tmp1 & 0xFF);

        }
    };
    static InstructionPtr i86_ffpre = new InstructionPtr() {
        public void handler() {
            int ModRM = FETCHOP();
            int tmp;
            int tmp1;
            int /*WORD*/ ip;
            switch (ModRM & 0x38) {
                case 0x00: {
                    /* INC ew */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.incdec_r16 : cycles.incdec_m16;
                    tmp = GetRMWord(ModRM);
                    tmp1 = tmp + 1;
                    SetOFW_Add(tmp1, tmp, 1);
                    SetAF(tmp1, tmp, 1);
                    SetSZPF_Word(tmp1);
                    PutbackRMWord(ModRM, tmp1 & 0xFFFF);
                }
                break;
                case 0x08: {
                    /* DEC ew */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.incdec_r16 : cycles.incdec_m16;
                    tmp = GetRMWord(ModRM);
                    tmp1 = tmp - 1;

                    SetOFW_Sub(tmp1, 1, tmp);
                    SetAF(tmp1, tmp, 1);
                    SetSZPF_Word(tmp1);
                    PutbackRMWord(ModRM, tmp1 & 0xFFFF);
                }
                break;

                case 0x10: {
                    /* CALL ew */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.call_r16 : cycles.call_m16;
                    tmp = GetRMWord(ModRM);
                    ip = (I.pc - I.base[CS]) & 0xFFFF;
                    PUSH(ip);
                    I.pc = (I.base[CS] + (tmp & 0xFFFF)) & AMASK;
                    change_pc20(I.pc);
                }
                break;
                /*TODO*///
/*TODO*///	case 0x18:  /* CALL FAR ea */
/*TODO*///		i86_ICount[0] -= cycles.call_m32;
/*TODO*///		tmp = I.sregs[CS];	/* HJB 12/13/98 need to skip displacements of EA */
/*TODO*///		tmp1 = GetRMWord(ModRM);
/*TODO*///		ip = I.pc - I.base[CS];
/*TODO*///		PUSH(tmp);
/*TODO*///		PUSH(ip);
/*TODO*///#ifdef I286
/*TODO*///		i286_code_descriptor(GetnextRMWord, tmp1);
/*TODO*///#else
/*TODO*///		I.sregs[CS] = GetnextRMWord;
/*TODO*///		I.base[CS] = SegBase(CS);
/*TODO*///		I.pc = (I.base[CS] + tmp1) & AMASK;
/*TODO*///#endif
/*TODO*///		CHANGE_PC(I.pc);
/*TODO*///		break;
/*TODO*///
                case 0x20:
                    /* JMP ea */
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.jmp_r16 : cycles.jmp_m16;
                    ip = GetRMWord(ModRM) & 0xFFFF;
                    I.pc = (I.base[CS] + ip) & AMASK;
                    change_pc20(I.pc);
                    break;
                /*TODO*///
/*TODO*///    case 0x28:  /* JMP FAR ea */
/*TODO*///		i86_ICount[0] -= cycles.jmp_m32;
/*TODO*///		
/*TODO*///#ifdef I286
/*TODO*///		tmp = GetRMWord(ModRM);
/*TODO*///		i286_code_descriptor(GetnextRMWord, tmp);
/*TODO*///#else
/*TODO*///		I.pc = GetRMWord(ModRM);
/*TODO*///		I.sregs[CS] = GetnextRMWord;
/*TODO*///		I.base[CS] = SegBase(CS);
/*TODO*///		I.pc = (I.pc + I.base[CS]) & AMASK;
/*TODO*///#endif
/*TODO*///		CHANGE_PC(I.pc);
/*TODO*///		break;
/*TODO*///
                case 0x30: /* PUSH ea */ {
                    i86_ICount[0] -= (ModRM >= 0xc0) ? cycles.push_r16 : cycles.push_m16;
                    tmp = GetRMWord(ModRM);
                    PUSH(tmp);
                }
                break;
                default:
                    System.out.println("i_ffpre 0x" + Integer.toHexString(ModRM & 0x38));
                    throw new UnsupportedOperationException("Unsupported");
            }
        }
    };

    static InstructionPtr i86_invalid = new InstructionPtr() {
        public void handler() {
            throw new UnsupportedOperationException("Unsupported");
        }
    };
    /*TODO*///
/*TODO*///
/*TODO*///static void PREFIX86(_invalid)(void)
/*TODO*///{
/*TODO*///#ifdef I286
/*TODO*///	i286_trap2(ILLEGAL_INSTRUCTION);
/*TODO*///#else
/*TODO*///	 /* makes the cpu loops forever until user resets it */
/*TODO*///	/*{ extern int debug_key_pressed; debug_key_pressed = 1; } */
/*TODO*///	logerror("illegal instruction %.2x at %.5x\n",PEEKBYTE(I.pc), I.pc);
/*TODO*///	I.pc--;
/*TODO*///	i86_ICount[0] -= 10;
/*TODO*///#endif
/*TODO*///}
/*TODO*///#endif
/*TODO*///    
}
