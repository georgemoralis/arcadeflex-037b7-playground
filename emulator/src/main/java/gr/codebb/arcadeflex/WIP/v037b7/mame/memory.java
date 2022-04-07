/*
 * ported to 0.37b7 
 */
package gr.codebb.arcadeflex.WIP.v037b7.mame;

import static arcadeflex.v037b7.mame.memory.BYTE_XOR_BE;
import static arcadeflex.v037b7.mame.memory.HT_BANKMAX;
import static arcadeflex.v037b7.mame.memory.HT_RAM;
import static arcadeflex.v037b7.mame.memory.OPbasefunc;
import static arcadeflex.v037b7.mame.memory.SET_OP_RAMROM;
import static arcadeflex.v037b7.mame.memory.TYPE_16BIT_BE;
import static arcadeflex.v037b7.mame.memory.TYPE_8BIT;
import static arcadeflex.v037b7.mame.memory.cpu_bankbase;
import static arcadeflex.v037b7.mame.memory.memoryreadhandler;
import static arcadeflex.v037b7.mame.memory.memoryreadoffset;
import static arcadeflex.v037b7.mame.memory.memorywritehandler;
import static arcadeflex.v037b7.mame.memory.memorywriteoffset;
import static arcadeflex.v037b7.mame.memory.u8_cur_mrhard;
import static arcadeflex.v037b7.mame.memory.u8_cur_mwhard;
import static arcadeflex.v037b7.mame.memory.u8_readhardware;
import static arcadeflex.v037b7.mame.memory.u8_writehardware;
import static arcadeflex.v037b7.mame.memory.u8_ophw;
import static arcadeflex.common.ptrLib.*;
import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.memoryH.*;

public class memory {

    public static int cpu_readmem20(int address) {
        char u8_hw;

        /* first-level lookup */
        u8_hw = u8_cur_mrhard[/*(UINT32)*/address >>> (ABITS2_20 + ABITS_MIN_20)];

        /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
        if (u8_hw == HT_RAM) {
            //return cpu_bankbase[HT_RAM][address];
            return cpu_bankbase[HT_RAM].read(address);
        }

        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_readhardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/address >>> ABITS_MIN_20) & MHMASK(ABITS2_20))];

            /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
            if (u8_hw == HT_RAM) {
                return cpu_bankbase[HT_RAM].read(address);
            }
        }

        /* fall back to handler */
        return (memoryreadhandler[u8_hw]).handler(address - memoryreadoffset[u8_hw]);
    }

    public static int cpu_readmem21(int address) {
        char u8_hw;

        /* first-level lookup */
        u8_hw = u8_cur_mrhard[/*(UINT32)*/address >>> (ABITS2_21 + ABITS_MIN_21)];

        /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
        if (u8_hw == HT_RAM) {
            //return cpu_bankbase[HT_RAM][address];
            return cpu_bankbase[HT_RAM].read(address);
        }

        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_readhardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/address >>> ABITS_MIN_21) & MHMASK(ABITS2_21))];

            /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
            if (u8_hw == HT_RAM) {
                return cpu_bankbase[HT_RAM].read(address);
            }
        }

        /* fall back to handler */
        return (memoryreadhandler[u8_hw]).handler(address - memoryreadoffset[u8_hw]);
    }

    /*TODO*///READBYTE(cpu_readmem16,    TYPE_8BIT,	  16)
    /*TODO*///READBYTE(cpu_readmem20,    TYPE_8BIT,	  20)
    /*TODO*///READBYTE(cpu_readmem21,    TYPE_8BIT,	  21)
    /*TODO*///
    /*TODO*///READBYTE(cpu_readmem16bew, TYPE_16BIT_BE, 16BEW)
    /*TODO*///#define READBYTE(name,type,abits)														
    public static int cpu_readmem16bew(int address) {
        char u8_hw;

        /* first-level lookup */
        u8_hw = u8_cur_mrhard[/*(UINT32)*/address >>> (ABITS2_16 + ABITS_MIN_16)];

        /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
        if (u8_hw <= HT_RAM) {
            //return cpu_bankbase[HT_RAM][address];
            return cpu_bankbase[u8_hw].read(BYTE_XOR_BE(address) - memoryreadoffset[u8_hw]);
        }

        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_readhardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/address >>> ABITS_MIN_16) & MHMASK(ABITS2_16))];

            /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
            if (u8_hw <= HT_RAM) {
                return cpu_bankbase[u8_hw].read(BYTE_XOR_BE(address) - memoryreadoffset[u8_hw]);
            }
        }

        /* fall back to handler */
        if (memoryreadhandler[u8_hw] != null) {
            int shift = (address & 1) << 3;
            int data = (memoryreadhandler[u8_hw]).handler((address & ~1) - memoryreadoffset[u8_hw]);

            return (data >>> (shift ^ 8)) & 0xff;
        } else {
            return 0;
        }
    }

    /*TODO*///READWORD(cpu_readmem16bew, TYPE_16BIT_BE, 16BEW, ALWAYS_ALIGNED)
    /*TODO*///#define READWORD(name,type,abits,align) 												
    public static int cpu_readmem16bew_word(int address) {
        char u8_hw;

        /* handle aligned case first */
 /* first-level lookup */
        u8_hw = u8_cur_mrhard[/*(UINT32)*/address >>> (ABITS2_16BEW + ABITS_MIN_16BEW)];

        if (u8_hw <= HT_BANKMAX) {
            return cpu_bankbase[u8_hw].READ_WORD(address - memoryreadoffset[u8_hw]);
        }

        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_readhardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/address >>> ABITS_MIN_16BEW) & MHMASK(ABITS2_16BEW))];
            if (u8_hw <= HT_BANKMAX) {
                return cpu_bankbase[u8_hw].READ_WORD(address - memoryreadoffset[u8_hw]);
            }
        }

        /* fall back to handler */
        return (memoryreadhandler[u8_hw]).handler(address - memoryreadoffset[u8_hw]);

        //return 0;
    }

    public static int cpu_readmem24(int address) {
        char hw;

        /* first-level lookup */
        hw=u8_cur_mrhard[address >>> (ABITS2_24 + ABITS_MIN_24)];

        if (hw <= HT_BANKMAX) {
            return cpu_bankbase[hw].memory[BYTE_XOR_BE(address) - memoryreadoffset[hw]];
        }

        /* second-level lookup */
        if (hw >= MH_HARDMAX) {
            hw=(char)(hw - MH_HARDMAX);
            hw=u8_readhardware[(hw << MH_SBITS) + ((address >>> ABITS_MIN_24) & MHMASK(ABITS2_24))];

            if (hw <= HT_BANKMAX) {
                return cpu_bankbase[hw].memory[BYTE_XOR_BE(address) - memoryreadoffset[hw]];
            }
        }
        int shift = (address & 1) << 3;
        int data = (memoryreadhandler[hw]).handler((address & ~1) - memoryreadoffset[hw]);
        return (data >> (shift ^ 8)) & 0xff;
    }

    public static int cpu_readmem24_word(int address) {
        char hw;
        /* handle aligned case first */
        if ((address & 1) == 0) {
            /* first-level lookup */
            hw=u8_cur_mrhard[address >>> (ABITS2_24 + ABITS_MIN_24)];
            if (hw <= HT_BANKMAX) {
                return cpu_bankbase[hw].READ_WORD(address - memoryreadoffset[hw]);
            }

            /* second-level lookup */
            if (hw >= MH_HARDMAX) {
                hw=(char) (hw - MH_HARDMAX);
                hw=u8_readhardware[(hw << MH_SBITS) + ((address >>> ABITS_MIN_24) & MHMASK(ABITS2_24))];
                if (hw <= HT_BANKMAX) {
                    return cpu_bankbase[hw].READ_WORD(address - memoryreadoffset[hw]);
                }
            }

            /* fall back to handler */
            return (memoryreadhandler[hw]).handler(address - memoryreadoffset[hw]);
        } /* unaligned case */ else {
            int data = cpu_readmem24(address) << 8;
            return data | (cpu_readmem24(address + 1) & 0xff);
        }

    }

    public static int cpu_readmem24_dword(int address) {
        int word1, word2;
        char hw1;
        char hw2;
        if (/*align == ALWAYS_ALIGNED || */(address & 1) == 0) {
            //int address2 = (address + 2) & ADDRESS_MASK(24);								
            int address2 = (int) ((address + 2) & ((1 << (ABITS1_24 + ABITS2_24 + ABITS_MIN_24 - 1)) | ((1 << (ABITS1_24 + ABITS2_24 + ABITS_MIN_24 - 1)) - 1)));
            /* first-level lookup */
            hw1=u8_cur_mrhard[address >> (ABITS2_24 + ABITS_MIN_24)];
            hw2=u8_cur_mrhard[address2 >> (ABITS2_24 + ABITS_MIN_24)];

            /* second-level lookup */
            if (hw1 >= MH_HARDMAX) {
                hw1=(char) (hw1 - MH_HARDMAX);
                hw1=u8_readhardware[(hw1 << MH_SBITS) + ((address >>> ABITS_MIN_24) & MHMASK(ABITS2_24))];
            }
            if (hw2 >= MH_HARDMAX) {
                hw2=(char) (hw2 - MH_HARDMAX);
                hw2=u8_readhardware[(hw2 << MH_SBITS) + ((address2 >> ABITS_MIN_24) & MHMASK(ABITS2_24))];
            }

            /* process each word */
            if (hw1 <= HT_BANKMAX) {
                word1 = cpu_bankbase[hw1].READ_WORD(address - memoryreadoffset[hw1]);
            } else {
                word1 = (memoryreadhandler[hw1]).handler(address - memoryreadoffset[hw1]);
            }
            if (hw2 <= HT_BANKMAX) {
                word2 = cpu_bankbase[hw2].READ_WORD(address2 - memoryreadoffset[hw2]);
            } else {
                word2 = (memoryreadhandler[hw2]).handler(address2 - memoryreadoffset[hw2]);
            }

            /* fall back to handler */
            return ((word1 << 16)) | (word2 & 0xffff);
        } else {
            int data = cpu_readmem24(address) << 24;
            data |= cpu_readmem24_word(address + 1) << 8;
            return data | (cpu_readmem24(address + 3) & 0xff);
        }

    }

    /*TODO*///
    /*TODO*///READBYTE(cpu_readmem16lew, TYPE_16BIT_LE, 16LEW)
    /*TODO*///READWORD(cpu_readmem16lew, TYPE_16BIT_LE, 16LEW, ALWAYS_ALIGNED)
    /*TODO*///
    /*TODO*///READBYTE(cpu_readmem24,     TYPE_8BIT,	  24)
    /*TODO*///
    /*TODO*///READBYTE(cpu_readmem24bew, TYPE_16BIT_BE, 24BEW)
    /*TODO*///READWORD(cpu_readmem24bew, TYPE_16BIT_BE, 24BEW, CAN_BE_MISALIGNED)
    /*TODO*///READLONG(cpu_readmem24bew, TYPE_16BIT_BE, 24BEW, CAN_BE_MISALIGNED)
    /*TODO*///
    /*TODO*///READBYTE(cpu_readmem26lew, TYPE_16BIT_LE, 26LEW)
    /*TODO*///READWORD(cpu_readmem26lew, TYPE_16BIT_LE, 26LEW, ALWAYS_ALIGNED)
    /*TODO*///READLONG(cpu_readmem26lew, TYPE_16BIT_LE, 26LEW, ALWAYS_ALIGNED)
    /*TODO*///
    /*TODO*///READBYTE(cpu_readmem29,    TYPE_16BIT_LE, 29)
    /*TODO*///READWORD(cpu_readmem29,    TYPE_16BIT_LE, 29,	 CAN_BE_MISALIGNED)
    /*TODO*///READLONG(cpu_readmem29,    TYPE_16BIT_LE, 29,	 CAN_BE_MISALIGNED)
    /*TODO*///
    /*TODO*///READBYTE(cpu_readmem32,    TYPE_16BIT_BE, 32)
    /*TODO*///READWORD(cpu_readmem32,    TYPE_16BIT_BE, 32,	 CAN_BE_MISALIGNED)
    /*TODO*///READLONG(cpu_readmem32,    TYPE_16BIT_BE, 32,	 CAN_BE_MISALIGNED)
    /*TODO*///
    /*TODO*///READBYTE(cpu_readmem32lew, TYPE_16BIT_LE, 32LEW)
    /*TODO*///READWORD(cpu_readmem32lew, TYPE_16BIT_LE, 32LEW, CAN_BE_MISALIGNED)
    /*TODO*///READLONG(cpu_readmem32lew, TYPE_16BIT_LE, 32LEW, CAN_BE_MISALIGNED)
    /*TODO*///
    /*TODO*///
    /*TODO*////***************************************************************************
    /*TODO*///
    /*TODO*///  Perform a memory write. This function is called by the CPU emulation.
    /*TODO*///
    /*TODO*///***************************************************************************/
    /*TODO*///
    /*TODO*////* generic byte-sized write handler */
    /*TODO*///#define WRITEBYTE(name,type,abits)														
    /*TODO*///void name(offs_t address,data_t data)													
    /*TODO*///{																						
    /*TODO*///	MHELE hw;																			
    /*TODO*///																						
    /*TODO*///	/* first-level lookup */															
    /*TODO*///	hw = cur_mwhard[(UINT32)address >> (ABITS2_##abits + ABITS_MIN_##abits)];			
    /*TODO*///																						
    /*TODO*///	/* for compatibility with setbankhandler, 8-bit systems must call handlers */		
    /*TODO*///	/* for banked memory reads/writes */												
    /*TODO*///	if (type == TYPE_8BIT && hw == HT_RAM)												
    /*TODO*///	{																					
    /*TODO*///		cpu_bankbase[HT_RAM][address] = data;											
    /*TODO*///		return; 																		
    /*TODO*///	}																					
    /*TODO*///	else if (type != TYPE_8BIT && hw <= HT_BANKMAX) 									
    /*TODO*///	{																					
    /*TODO*///		if (type == TYPE_16BIT_BE)														
    /*TODO*///			cpu_bankbase[hw][BYTE_XOR_BE(address) - memorywriteoffset[hw]] = data;		
    /*TODO*///		else if (type == TYPE_16BIT_LE) 												
    /*TODO*///			cpu_bankbase[hw][BYTE_XOR_LE(address) - memorywriteoffset[hw]] = data;		
    /*TODO*///		return; 																		
    /*TODO*///	}																					
    /*TODO*///																						
    /*TODO*///	/* second-level lookup */															
    /*TODO*///	if (hw >= MH_HARDMAX)																
    /*TODO*///	{																					
    /*TODO*///		hw -= MH_HARDMAX;																
    /*TODO*///		hw = writehardware[(hw << MH_SBITS) + (((UINT32)address >> ABITS_MIN_##abits) & MHMASK(ABITS2_##abits))];	
    /*TODO*///																						
    /*TODO*///		/* for compatibility with setbankhandler, 8-bit systems must call handlers */	
    /*TODO*///		/* for banked memory reads/writes */											
    /*TODO*///		if (type == TYPE_8BIT && hw == HT_RAM)											
    /*TODO*///		{																				
    /*TODO*///			cpu_bankbase[HT_RAM][address] = data;										
    /*TODO*///			return; 																	
    /*TODO*///		}																				
    /*TODO*///		else if (type != TYPE_8BIT && hw <= HT_BANKMAX) 								
    /*TODO*///		{																				
    /*TODO*///			if (type == TYPE_16BIT_BE)													
    /*TODO*///				cpu_bankbase[hw][BYTE_XOR_BE(address) - memorywriteoffset[hw]] = data;	
    /*TODO*///			else if (type == TYPE_16BIT_LE) 											
    /*TODO*///				cpu_bankbase[hw][BYTE_XOR_LE(address) - memorywriteoffset[hw]] = data;	
    /*TODO*///			return; 																	
    /*TODO*///		}																				
    /*TODO*///	}																					
    /*TODO*///																						
    /*TODO*///	/* fall back to handler */															
    /*TODO*///	if (type != TYPE_8BIT)																
    /*TODO*///	{																					
    /*TODO*///		int shift = (address & 1) << 3; 												
    /*TODO*///		if (type == TYPE_16BIT_BE)														
    /*TODO*///			shift ^= 8; 																
    /*TODO*///		data = (0xff000000 >> shift) | ((data & 0xff) << shift);						
    /*TODO*///		address &= ~1;																	
    /*TODO*///	}																					
    /*TODO*///	(*memorywritehandler[hw])(address - memorywriteoffset[hw], data);					
    /*TODO*///}
    /*TODO*///
    /*TODO*////* generic word-sized write handler (16-bit aligned only!) */
    /*TODO*///#define WRITEWORD(name,type,abits,align)												
    /*TODO*///void name##_word(offs_t address,data_t data)											
    /*TODO*///{																						
    /*TODO*///	MHELE hw;																			
    /*TODO*///																						
    /*TODO*///	/* only supports 16-bit memory systems */											
    /*TODO*///	if (type == TYPE_8BIT)																
    /*TODO*///		printf("Unsupported type for WRITEWORD macro!n");                              
    /*TODO*///																						
    /*TODO*///	/* handle aligned case first */ 													
    /*TODO*///	if (align == ALWAYS_ALIGNED || !(address & 1))										
    /*TODO*///	{																					
    /*TODO*///		/* first-level lookup */														
    /*TODO*///		hw = cur_mwhard[(UINT32)address >> (ABITS2_##abits + ABITS_MIN_##abits)];		
    /*TODO*///		if (hw <= HT_BANKMAX)															
    /*TODO*///		{																				
    /*TODO*///			WRITE_WORD(&cpu_bankbase[hw][address - memorywriteoffset[hw]], data);		
    /*TODO*///			return; 																	
    /*TODO*///		}																				
    /*TODO*///																						
    /*TODO*///		/* second-level lookup */														
    /*TODO*///		if (hw >= MH_HARDMAX)															
    /*TODO*///		{																				
    /*TODO*///			hw -= MH_HARDMAX;															
    /*TODO*///			hw = writehardware[(hw << MH_SBITS) + (((UINT32)address >> ABITS_MIN_##abits) & MHMASK(ABITS2_##abits))]; 
    /*TODO*///			if (hw <= HT_BANKMAX)														
    /*TODO*///			{																			
    /*TODO*///				WRITE_WORD(&cpu_bankbase[hw][address - memorywriteoffset[hw]], data);	
    /*TODO*///				return; 																
    /*TODO*///			}																			
    /*TODO*///		}																				
    /*TODO*///																						
    /*TODO*///		/* fall back to handler */														
    /*TODO*///		(*memorywritehandler[hw])(address - memorywriteoffset[hw], data & 0xffff);		
    /*TODO*///	}																					
    /*TODO*///																						
    /*TODO*///	/* unaligned case */																
    /*TODO*///	else if (type == TYPE_16BIT_BE) 													
    /*TODO*///	{																					
    /*TODO*///		name(address, data >> 8);														
    /*TODO*///		name(address + 1, data & 0xff); 												
    /*TODO*///	}																					
    /*TODO*///	else if (type == TYPE_16BIT_LE) 													
    /*TODO*///	{																					
    /*TODO*///		name(address, data & 0xff); 													
    /*TODO*///		name(address + 1, data >> 8);													
    /*TODO*///	}																					
    /*TODO*///}
    /*TODO*///
    /*TODO*////* generic dword-sized write handler (16-bit aligned only!) */
    /*TODO*///#define WRITELONG(name,type,abits,align)												
    /*TODO*///void name##_dword(offs_t address,data_t data)											
    /*TODO*///{																						
    /*TODO*///	UINT16 word1, word2;																
    /*TODO*///	MHELE hw1, hw2; 																	
    /*TODO*///																						
    /*TODO*///	/* only supports 16-bit memory systems */											
    /*TODO*///	if (type == TYPE_8BIT)																
    /*TODO*///		printf("Unsupported type for WRITEWORD macro!n");                              
    /*TODO*///																						
    /*TODO*///	/* handle aligned case first */ 													
    /*TODO*///	if (align == ALWAYS_ALIGNED || !(address & 1))										
    /*TODO*///	{																					
    /*TODO*///		int address2 = (address + 2) & ADDRESS_MASK(abits); 							
    /*TODO*///																						
    /*TODO*///		/* first-level lookup */														
    /*TODO*///		hw1 = cur_mwhard[(UINT32)address >> (ABITS2_##abits + ABITS_MIN_##abits)];		
    /*TODO*///		hw2 = cur_mwhard[(UINT32)address2 >> (ABITS2_##abits + ABITS_MIN_##abits)]; 	
    /*TODO*///																						
    /*TODO*///		/* second-level lookup */														
    /*TODO*///		if (hw1 >= MH_HARDMAX)															
    /*TODO*///		{																				
    /*TODO*///			hw1 -= MH_HARDMAX;															
    /*TODO*///			hw1 = writehardware[(hw1 << MH_SBITS) + (((UINT32)address >> ABITS_MIN_##abits) & MHMASK(ABITS2_##abits))]; 
    /*TODO*///		}																				
    /*TODO*///		if (hw2 >= MH_HARDMAX)															
    /*TODO*///		{																				
    /*TODO*///			hw2 -= MH_HARDMAX;															
    /*TODO*///			hw2 = writehardware[(hw2 << MH_SBITS) + (((UINT32)address2 >> ABITS_MIN_##abits) & MHMASK(ABITS2_##abits))];	
    /*TODO*///		}																				
    /*TODO*///																						
    /*TODO*///		/* extract words */ 															
    /*TODO*///		if (type == TYPE_16BIT_BE)														
    /*TODO*///		{																				
    /*TODO*///			word1 = data >> 16; 														
    /*TODO*///			word2 = data & 0xffff;														
    /*TODO*///		}																				
    /*TODO*///		else if (type == TYPE_16BIT_LE) 												
    /*TODO*///		{																				
    /*TODO*///			word1 = data & 0xffff;														
    /*TODO*///			word2 = data >> 16; 														
    /*TODO*///		}																				
    /*TODO*///																						
    /*TODO*///		/* process each word */ 														
    /*TODO*///		if (hw1 <= HT_BANKMAX)															
    /*TODO*///			WRITE_WORD(&cpu_bankbase[hw1][address - memorywriteoffset[hw1]], word1);	
    /*TODO*///		else																			
    /*TODO*///			(*memorywritehandler[hw1])(address - memorywriteoffset[hw1], word1);		
    /*TODO*///		if (hw2 <= HT_BANKMAX)															
    /*TODO*///			WRITE_WORD(&cpu_bankbase[hw2][address2 - memorywriteoffset[hw2]], word2);	
    /*TODO*///		else																			
    /*TODO*///			(*memorywritehandler[hw2])(address2 - memorywriteoffset[hw2], word2);		
    /*TODO*///	}																					
    /*TODO*///																						
    /*TODO*///	/* unaligned case */																
    /*TODO*///	else if (type == TYPE_16BIT_BE) 													
    /*TODO*///	{																					
    /*TODO*///		name(address, data >> 24);														
    /*TODO*///		name##_word(address + 1, (data >> 8) & 0xffff); 								
    /*TODO*///		name(address + 3, data & 0xff); 												
    /*TODO*///	}																					
    /*TODO*///	else if (type == TYPE_16BIT_LE) 													
    /*TODO*///	{																					
    /*TODO*///		name(address, data & 0xff); 													
    /*TODO*///		name##_word(address + 1, (data >> 8) & 0xffff); 								
    /*TODO*///		name(address + 3, data >> 24);													
    /*TODO*///	}																					
    /*TODO*///}
    /*TODO*///
    /*TODO*///
    public static void cpu_writemem20(int address, int data) {
        char u8_hw;

        /* first-level lookup */
        u8_hw = u8_cur_mwhard[/*(UINT32)*/address >>> (ABITS2_20 + ABITS_MIN_20)];

        /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
        if (u8_hw == HT_RAM) {
            cpu_bankbase[HT_RAM].write(address, data);
            return;
        }
        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_writehardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/address >>> ABITS_MIN_20) & MHMASK(ABITS2_20))];

            /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
            if (u8_hw == HT_RAM) {
                cpu_bankbase[HT_RAM].write(address, data);
                return;
            }
        }

        /* fall back to handler */
        (memorywritehandler[u8_hw]).handler(address - memorywriteoffset[u8_hw], data);
    }

    public static void cpu_writemem21(int address, int data) {
        char u8_hw;

        /* first-level lookup */
        u8_hw = u8_cur_mwhard[/*(UINT32)*/address >>> (ABITS2_21 + ABITS_MIN_21)];

        /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
        if (u8_hw == HT_RAM) {
            cpu_bankbase[HT_RAM].write(address, data);
            return;
        }
        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_writehardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/address >>> ABITS_MIN_21) & MHMASK(ABITS2_21))];

            /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
            if (u8_hw == HT_RAM) {
                cpu_bankbase[HT_RAM].write(address, data);
                return;
            }
        }

        /* fall back to handler */
        (memorywritehandler[u8_hw]).handler(address - memorywriteoffset[u8_hw], data);
    }

    /*TODO*///WRITEBYTE(cpu_writemem16,	 TYPE_8BIT, 	16)
/*TODO*///WRITEBYTE(cpu_writemem20,	 TYPE_8BIT, 	20)
/*TODO*///WRITEBYTE(cpu_writemem21,	 TYPE_8BIT, 	21)
    //WRITEBYTE(cpu_writemem16bew, TYPE_16BIT_BE, 16BEW)
    /*TODO*///#define WRITEBYTE(name,type,abits)														
    public static void cpu_writemem16bew(int address, int data) {
        char u8_hw;

        /* first-level lookup */
        u8_hw = u8_cur_mwhard[/*(UINT32)*/address >>> (ABITS2_16BEW + ABITS_MIN_16BEW)];

        /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
        if (u8_hw <= HT_RAM) {
            cpu_bankbase[u8_hw].write(BYTE_XOR_BE(address) - memorywriteoffset[u8_hw], data);
            return;
        }
        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_writehardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/address >>> ABITS_MIN_16BEW) & MHMASK(ABITS2_16BEW))];

            /* for compatibility with setbankhandler, 8-bit systems must call handlers */
 /* for banked memory reads/writes */
            if (u8_hw <= HT_RAM) {
                cpu_bankbase[u8_hw].write(BYTE_XOR_BE(address) - memorywriteoffset[u8_hw], data);
                return;
            }
        }

        /* fall back to handler */
        int shift = (address & 1) << 3;
        shift ^= 8;

        data = (0xff000000 >>> shift) | ((data & 0xff) << shift);
        address &= ~1;

        if (memorywritehandler[u8_hw] != null) {
            (memorywritehandler[u8_hw]).handler(address - memorywriteoffset[u8_hw], data);
        }
    }
    
    public static void cpu_writemem24bew_word(int address, int data) {
        char u8_hw;

        /* only supports 16-bit memory systems */
        if (TYPE_16BIT_BE == TYPE_8BIT) {
            printf("Unsupported type for WRITEWORD macro!n");
        }

        /* handle aligned case first */
 /* first-level lookup */
        u8_hw = u8_cur_mwhard[address >>> (ABITS2_24BEW + ABITS_MIN_24BEW)];
        if (u8_hw <= HT_BANKMAX) {
            cpu_bankbase[u8_hw].WRITE_WORD(address - memorywriteoffset[u8_hw], data);
            return;
        }

        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_writehardware[(u8_hw << MH_SBITS) + ((address >>> ABITS_MIN_24BEW) & MHMASK(ABITS2_24BEW))];
            if (u8_hw <= HT_BANKMAX) {
                cpu_bankbase[u8_hw].WRITE_WORD(address - memorywriteoffset[u8_hw], data);
                return;
            }
        }

        /* fall back to handler */
        (memorywritehandler[u8_hw]).handler(address - memorywriteoffset[u8_hw], data & 0xffff);

    }
    

    //WRITEWORD(cpu_writemem16bew, TYPE_16BIT_BE, 16BEW, ALWAYS_ALIGNED)
    //#define WRITEWORD(name,type,abits,align)												
    public static void cpu_writemem16bew_word(int address, int data) {
        char u8_hw;

        /* only supports 16-bit memory systems */
        if (TYPE_16BIT_BE == TYPE_8BIT) {
            printf("Unsupported type for WRITEWORD macro!n");
        }

        /* handle aligned case first */
 /* first-level lookup */
        u8_hw = u8_cur_mwhard[address >>> (ABITS2_16BEW + ABITS_MIN_16BEW)];
        if (u8_hw <= HT_BANKMAX) {
            cpu_bankbase[u8_hw].WRITE_WORD(address - memorywriteoffset[u8_hw], data);
            return;
        }

        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = u8_writehardware[(u8_hw << MH_SBITS) + ((address >>> ABITS_MIN_16BEW) & MHMASK(ABITS2_16BEW))];
            if (u8_hw <= HT_BANKMAX) {
                cpu_bankbase[u8_hw].WRITE_WORD(address - memorywriteoffset[u8_hw], data);
                return;
            }
        }

        /* fall back to handler */
        (memorywritehandler[u8_hw]).handler(address - memorywriteoffset[u8_hw], data & 0xffff);

    }
    
    public static int cpu_readmem24bew_word(int address) {
        char u8_hw;

        /* handle aligned case first */
 /* first-level lookup */
        u8_hw = (char) (u8_cur_mrhard[/*(UINT32)*/address >>> (ABITS2_24BEW + ABITS_MIN_24BEW)]);

        if (u8_hw <= HT_BANKMAX) {
            //System.out.println(memoryreadoffset[u8_hw]);
            //System.out.println(address - memoryreadoffset[u8_hw]);
            //System.out.println(cpu_bankbase[u8_hw]);
            return cpu_bankbase[u8_hw].READ_WORD((address - memoryreadoffset[u8_hw])&0x3ffff);
        }

        /* second-level lookup */
        if (u8_hw >= MH_HARDMAX) {
            u8_hw -= MH_HARDMAX;
            u8_hw = (char) (u8_readhardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/address >>> ABITS_MIN_24BEW) & MHMASK(ABITS2_24BEW))]);
            if (u8_hw <= HT_BANKMAX) {
                return cpu_bankbase[u8_hw].READ_WORD(address - memoryreadoffset[u8_hw]);
            }
        }

        /* fall back to handler */
        return (memoryreadhandler[u8_hw]).handler(address - memoryreadoffset[u8_hw]);
        /*+++++++++++++++++++++++++++++++++++++*/
        
    }

    public static void cpu_writemem24(int address, int data) {
        char hw;

        /* first-level lookup */
        hw=u8_cur_mwhard[address >>> (ABITS2_24 + ABITS_MIN_24)];
        if (hw <= HT_BANKMAX) {
            cpu_bankbase[hw].memory[BYTE_XOR_BE(address) - memorywriteoffset[hw]] = (char) data;
            return;
        }

        /* second-level lookup */
        if (hw >= MH_HARDMAX) {
            hw=(char) (hw - MH_HARDMAX);
            hw=u8_writehardware[(hw << MH_SBITS) + ((address >>> ABITS_MIN_24) & MHMASK(ABITS2_24))];

            if (hw <= HT_BANKMAX) {
                cpu_bankbase[hw].memory[BYTE_XOR_BE(address) - memorywriteoffset[hw]] = (char) data;
                return;
            }
        }

        /* fall back to handler */
        int shift = (address & 1) << 3;
        shift ^= 8;
        data = (0xff000000 >>> shift) | ((data & 0xff) << shift);	//unsigned??					
        address &= ~1;
        (memorywritehandler[hw]).handler(address - memorywriteoffset[hw], data);
    }

    public static void cpu_writemem24_word(int address, int data) {
        char hw;

        /* handle aligned case first */
        if ((address & 1) == 0) {
            /* first-level lookup */
            hw=u8_cur_mwhard[address >>> (ABITS2_24 + ABITS_MIN_24)];
            if (hw <= HT_BANKMAX) {
                cpu_bankbase[hw].WRITE_WORD(address - memorywriteoffset[hw], data);
                return;
            }

            /* second-level lookup */
            if (hw >= MH_HARDMAX) {
                hw=(char) (hw - MH_HARDMAX);
                hw=u8_writehardware[(hw << MH_SBITS) + ((address >>> ABITS_MIN_24) & MHMASK(ABITS2_24))];
                if (hw <= HT_BANKMAX) {
                    cpu_bankbase[hw].WRITE_WORD(address - memorywriteoffset[hw], data);
                    return;
                }
            }

            /* fall back to handler */
            (memorywritehandler[hw]).handler(address - memorywriteoffset[hw], data & 0xffff);
        } /* unaligned case */ else {
            cpu_writemem24(address, data >> 8);
            cpu_writemem24(address + 1, data & 0xff);
        }
    }

    public static void cpu_writemem24_dword(int address, int data) {
        int word1, word2;
        char hw1;
        char hw2;

        /* handle aligned case first */
        if ((address & 1) == 0) {
            //int address2 = (address + 2) & ADDRESS_MASK(24);								
            int address2 = (int) ((address + 2) & ((1 << (ABITS1_24 + ABITS2_24 + ABITS_MIN_24 - 1)) | ((1 << (ABITS1_24 + ABITS2_24 + ABITS_MIN_24 - 1)) - 1)));

            /* first-level lookup */
            hw1=u8_cur_mwhard[address >>> (ABITS2_24 + ABITS_MIN_24)];
            hw2=u8_cur_mwhard[address2 >>> (ABITS2_24 + ABITS_MIN_24)];

            /* second-level lookup */
            if (hw1 >= MH_HARDMAX) {
                hw1=(char) (hw1 - MH_HARDMAX);
                hw1=u8_writehardware[(hw1 << MH_SBITS) + ((address >>> ABITS_MIN_24) & MHMASK(ABITS2_24))];
            }
            if (hw2 >= MH_HARDMAX) {
                hw2=(char) (hw2 - MH_HARDMAX);
                hw2=u8_writehardware[(hw2 << MH_SBITS) + ((address2 >>> ABITS_MIN_24) & MHMASK(ABITS2_24))];
            }
            /* extract words */

            word1 = (data >> 16) & 0xffff;
            word2 = data & 0xffff;

            /* process each word */
            if (hw1 <= HT_BANKMAX) {
                cpu_bankbase[hw1].WRITE_WORD(address - memorywriteoffset[hw1], word1);
            } else {
                (memorywritehandler[hw1]).handler(address - memorywriteoffset[hw1], word1);
            }
            if (hw2 <= HT_BANKMAX) {
                cpu_bankbase[hw2].WRITE_WORD(address2 - memorywriteoffset[hw2], word2);
            } else {
                (memorywritehandler[hw2]).handler(address2 - memorywriteoffset[hw2], word2);
            }
        } /* unaligned case */ else {
            cpu_writemem24(address, (data >> 24));
            cpu_writemem24_word(address + 1, (data >> 8) & 0xffff);
            cpu_writemem24(address + 3, data & 0xff);
        }

    }
    /*TODO*///WRITEBYTE(cpu_writemem16lew, TYPE_16BIT_LE, 16LEW)
/*TODO*///WRITEWORD(cpu_writemem16lew, TYPE_16BIT_LE, 16LEW, ALWAYS_ALIGNED)
/*TODO*///
/*TODO*///WRITEBYTE(cpu_writemem24,	  TYPE_8BIT, 	24)
/*TODO*///
/*TODO*///WRITEBYTE(cpu_writemem24bew, TYPE_16BIT_BE, 24BEW)
/*TODO*///WRITEWORD(cpu_writemem24bew, TYPE_16BIT_BE, 24BEW, CAN_BE_MISALIGNED)
/*TODO*///WRITELONG(cpu_writemem24bew, TYPE_16BIT_BE, 24BEW, CAN_BE_MISALIGNED)
/*TODO*///
/*TODO*///WRITEBYTE(cpu_writemem26lew, TYPE_16BIT_LE, 26LEW)
/*TODO*///WRITEWORD(cpu_writemem26lew, TYPE_16BIT_LE, 26LEW, ALWAYS_ALIGNED)
/*TODO*///WRITELONG(cpu_writemem26lew, TYPE_16BIT_LE, 26LEW, ALWAYS_ALIGNED)
/*TODO*///
/*TODO*///WRITEBYTE(cpu_writemem29,    TYPE_16BIT_LE, 29)
/*TODO*///WRITEWORD(cpu_writemem29,	 TYPE_16BIT_LE, 29,    CAN_BE_MISALIGNED)
/*TODO*///WRITELONG(cpu_writemem29,	 TYPE_16BIT_LE, 29,    CAN_BE_MISALIGNED)
/*TODO*///
/*TODO*///WRITEBYTE(cpu_writemem32,	 TYPE_16BIT_BE, 32)
/*TODO*///WRITEWORD(cpu_writemem32,	 TYPE_16BIT_BE, 32,    CAN_BE_MISALIGNED)
/*TODO*///WRITELONG(cpu_writemem32,	 TYPE_16BIT_BE, 32,    CAN_BE_MISALIGNED)
/*TODO*///
/*TODO*///WRITEBYTE(cpu_writemem32lew, TYPE_16BIT_LE, 32LEW)
/*TODO*///WRITEWORD(cpu_writemem32lew, TYPE_16BIT_LE, 32LEW, CAN_BE_MISALIGNED)
/*TODO*///WRITELONG(cpu_writemem32lew, TYPE_16BIT_LE, 32LEW, CAN_BE_MISALIGNED)
/*TODO*///
/*TODO*///
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///  Opcode base changers. This function is called by the CPU emulation.
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*////* generic opcode base changer */
/*TODO*///#define SETOPBASE(name,abits,shift) 													
/*TODO*///void name(int pc)																		
/*TODO*///{																						
/*TODO*///	MHELE hw;																			
/*TODO*///																						
/*TODO*///	pc = (UINT32)pc >> shift;															
/*TODO*///																						
/*TODO*///	/* allow overrides */																
/*TODO*///	if (OPbasefunc) 																	
/*TODO*///	{																					
/*TODO*///		pc = OPbasefunc(pc);															
/*TODO*///		if (pc == -1)																	
/*TODO*///			return; 																	
/*TODO*///	}																					
/*TODO*///																						
/*TODO*///	/* perform the lookup */															
/*TODO*///	hw = cur_mrhard[(UINT32)pc >> (ABITS2_##abits + ABITS_MIN_##abits)];				
/*TODO*///	if (hw >= MH_HARDMAX)																
/*TODO*///	{																					
/*TODO*///		hw -= MH_HARDMAX;																
/*TODO*///		hw = readhardware[(hw << MH_SBITS) + (((UINT32)pc >> ABITS_MIN_##abits) & MHMASK(ABITS2_##abits))]; 
/*TODO*///	}																					
/*TODO*///	ophw = hw;																			
/*TODO*///																						
/*TODO*///	/* RAM or banked memory */															
/*TODO*///	if (hw <= HT_BANKMAX)																
/*TODO*///	{																					
/*TODO*///		SET_OP_RAMROM(cpu_bankbase[hw] - memoryreadoffset[hw])							
/*TODO*///		return; 																		
/*TODO*///	}																					
/*TODO*///																						
/*TODO*///	/* do not support on callback memory region */										
/*TODO*///	logerror("CPU #%d PC %04x: warning - op-code execute on mapped i/on",              
/*TODO*///				cpu_getactivecpu(),cpu_get_pc());										
/*TODO*///}
/*TODO*///
/*TODO*///
    public static setopbase cpu_setOPbase20 = new setopbase() {
        public void handler(int pc) {
            char u8_hw;

            //not shift neccesary pc = (UINT32)pc >> shift;															
            /* allow overrides */
            if (OPbasefunc != null) {
                pc = OPbasefunc.handler(pc);
                if (pc == -1) {
                    return;
                }
            }

            /* perform the lookup */
            u8_hw = u8_cur_mrhard[/*(UINT32)*/pc >>> (ABITS2_20 + ABITS_MIN_20)];
            if (u8_hw >= MH_HARDMAX) {
                u8_hw -= MH_HARDMAX;
                u8_hw = u8_readhardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/pc >>> ABITS_MIN_20) & MHMASK(ABITS2_20))];
            }
            u8_ophw = (char) (u8_hw & 0xFF);

            /* RAM or banked memory */
            if (u8_hw <= HT_BANKMAX) {
                SET_OP_RAMROM(new UBytePtr(cpu_bankbase[u8_hw], -memoryreadoffset[u8_hw]));
                return;
            }

            /* do not support on callback memory region */
            logerror("CPU #%d PC %04x: warning - op-code execute on mapped i/on",
                    cpu_getactivecpu(), cpu_get_pc());

        }
    };

    public static setopbase cpu_setOPbase21 = new setopbase() {
        public void handler(int pc) {
            char u8_hw;

            //not shift neccesary pc = (UINT32)pc >> shift;															
            /* allow overrides */
            if (OPbasefunc != null) {
                pc = OPbasefunc.handler(pc);
                if (pc == -1) {
                    return;
                }
            }

            /* perform the lookup */
            u8_hw = u8_cur_mrhard[/*(UINT32)*/pc >>> (ABITS2_21 + ABITS_MIN_21)];
            if (u8_hw >= MH_HARDMAX) {
                u8_hw -= MH_HARDMAX;
                u8_hw = u8_readhardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/pc >>> ABITS_MIN_21) & MHMASK(ABITS2_21))];
            }
            u8_ophw = (char) (u8_hw & 0xFF);

            /* RAM or banked memory */
            if (u8_hw <= HT_BANKMAX) {
                SET_OP_RAMROM(new UBytePtr(cpu_bankbase[u8_hw], -memoryreadoffset[u8_hw]));
                return;
            }

            /* do not support on callback memory region */
            logerror("CPU #%d PC %04x: warning - op-code execute on mapped i/on",
                    cpu_getactivecpu(), cpu_get_pc());

        }
    };

    /*TODO*///SETOPBASE(cpu_setOPbase16bew, 16BEW, 0)
    //#define SETOPBASE(name,abits,shift) 													
    public static setopbase cpu_setOPbase16bew = new setopbase() {
        public void handler(int pc) {
            char u8_hw;

            //not shift neccesary pc = (UINT32)pc >> 0;															

            /* allow overrides */
            if (OPbasefunc != null) {
                pc = OPbasefunc.handler(pc);
                if (pc == -1) {
                    return;
                }
            }

            /* perform the lookup */
            u8_hw = u8_cur_mrhard[/*(UINT32)*/pc >>> (ABITS2_16BEW + ABITS_MIN_16BEW)];
            if (u8_hw >= MH_HARDMAX) {
                u8_hw -= MH_HARDMAX;
                u8_hw = u8_readhardware[(u8_hw << MH_SBITS) + ((/*(UINT32)*/pc >>> ABITS_MIN_16BEW) & MHMASK(ABITS2_16BEW))];
            }

            u8_ophw = (char) (u8_hw & 0xFF);

            /* RAM or banked memory */
            if (u8_hw <= HT_BANKMAX) {
                SET_OP_RAMROM(new UBytePtr(cpu_bankbase[u8_hw], -memoryreadoffset[u8_hw]));
                return;
            }

            /* do not support on callback memory region */
            logerror("CPU #%d PC %04x: warning - op-code execute on mapped i/on",
                    cpu_getactivecpu(), cpu_get_pc());
        }
    };

    public static setopbase cpu_setOPbase24 = new setopbase() {
        public void handler(int pc) {
            char hw;

            /* allow overrides */
            if (OPbasefunc != null) {
                pc = (int) OPbasefunc.handler((int) pc);
                if (pc == -1) {
                    return;
                }
            }

            /* perform the lookup */
            hw=u8_cur_mrhard[pc >>> (ABITS2_24 + ABITS_MIN_24)];
            if (hw >= MH_HARDMAX) {
                hw=(char) (hw - MH_HARDMAX);
                hw=u8_readhardware[(hw << MH_SBITS) + ((pc >>> ABITS_MIN_24) & MHMASK(ABITS2_24))];
            }
            u8_ophw = (char) (hw & 0xFF);

            /* RAM or banked memory */
            if (hw <= HT_BANKMAX) {
                SET_OP_RAMROM(new UBytePtr(cpu_bankbase[hw], (-memoryreadoffset[hw])));
                return;
            }

            /* do not support on callback memory region */
            printf("CPU #%d PC %04x: warning - op-code execute on mapped i/o\n", cpu_getactivecpu(), cpu_get_pc());
        }
    };

}
