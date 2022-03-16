/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.machine;

import static arcadeflex.WIP.v037b7.drivers.neogeo.neogeo_ym2610_interface;
import static arcadeflex.WIP.v037b7.drivers.neogeo.neogeo_has_trackball;
import static arcadeflex.WIP.v037b7.drivers.neogeo.neogeo_irq2type;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
import static arcadeflex.v037b7.mame.input.*;
import static arcadeflex.v037b7.mame.inputH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static arcadeflex.common.libc.cstring.strcmp;
import static arcadeflex.v056.mame.timer.*;
import static arcadeflex.v056.mame.timerH.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;

public class neogeo
{
	
	//static UBytePtr memcard;
	public static UBytePtr neogeo_ram = new UBytePtr();
        public static UBytePtr neogeo_sram = new UBytePtr();

	public static int sram_locked;
	public static int sram_protection_hack;

/*TODO*///	extern int neogeo_has_trackball;
/*TODO*///	extern int neogeo_irq2type;
/*TODO*///	
/*TODO*///	
/*TODO*///	/* MARTINEZ.F 990209 Calendar */
/*TODO*///	extern int seconds;
/*TODO*///	extern int minutes;
/*TODO*///	extern int hours;
/*TODO*///	extern int days;
/*TODO*///	extern int month;
/*TODO*///	extern int year;
/*TODO*///	extern int weekday;
	/* MARTINEZ.F 990209 Calendar End */
	
	/***************** MEMCARD GLOBAL VARIABLES ******************/
	public static int			mcd_action=0;
	public static int			mcd_number=0;
	public static int			memcard_status=0;	/* 1=Inserted 0=No card */
	public static int			memcard_number=0;	/* 000...999, -1=None */
	public static int			memcard_manager=0;	/* 0=Normal boot 1=Call memcard manager */
	public static UBytePtr neogeo_memcard = new UBytePtr();	/* Pointer to 2kb RAM zone */
	
/*TODO*///	/*************** MEMCARD FUNCTION PROTOTYPES *****************/
/*TODO*///	int		neogeo_memcard_load(int);
/*TODO*///	int		neogeo_memcard_create(int);
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static 
	
	/* This function is called on every reset */
	public static InitMachinePtr neogeo_init_machine = new InitMachinePtr() { public void handler() 
	{
		int src,res;
		timer_entry		ltime;
/*TODO*///		struct tm		*today;
	
	
		/* Reset variables & RAM */
		memset (neogeo_ram, 0, 0x10000);
	
		/* Set up machine country */
                src = readinputport(5);
                res = src&0x3;
	
	    /* Console/arcade mode */
		if ((src & 0x04) != 0)	res |= 0x8000;
	
		/* write the ID in the system BIOS ROM */
		memory_region(REGION_USER1).WRITE_WORD(0x0400,res);
	
		if (memcard_manager==1)
		{
			memcard_manager=0;
			memory_region(REGION_USER1).WRITE_WORD(0x11b1a, 0x500a);
		}
		else
		{
			memory_region(REGION_USER1).WRITE_WORD(0x11b1a,0x1b6a);
		}
	
/*TODO*///		time(&ltime);
/*TODO*///		today = localtime(&ltime);
/*TODO*///	
/*TODO*///		seconds = ((today.tm_sec/10)<<4) + (today.tm_sec%10);
/*TODO*///		minutes = ((today.tm_min/10)<<4) + (today.tm_min%10);
/*TODO*///		hours = ((today.tm_hour/10)<<4) + (today.tm_hour%10);
/*TODO*///		days = ((today.tm_mday/10)<<4) + (today.tm_mday%10);
/*TODO*///		month = (today.tm_mon + 1);
/*TODO*///		year = ((today.tm_year/10)<<4) + (today.tm_year%10);
/*TODO*///		weekday = today.tm_wday;
	} };
	
	
	/* This function is only called once per game. */
	public static InitDriverPtr init_neogeo = new InitDriverPtr() { public void handler() 
	{
		UBytePtr RAM = new UBytePtr(memory_region(REGION_CPU1));
		//extern struct YM2610interface neogeo_ym2610_interface;
	
		if (memory_region(REGION_SOUND2) != null)
		{
			logerror("using memory region %d for Delta T samples\n",REGION_SOUND2);
			neogeo_ym2610_interface.pcmromb[0] = REGION_SOUND2;
		}
		else
		{
			logerror("using memory region %d for Delta T samples\n",REGION_SOUND1);
			neogeo_ym2610_interface.pcmromb[0] = REGION_SOUND1;
		}
	
	    /* Allocate ram banks */
		neogeo_ram = new UBytePtr(0x10000);
		cpu_setbank(1, neogeo_ram);
	
		/* Set the biosbank */
		cpu_setbank(3, memory_region(REGION_USER1));
	
		/* Set the 2nd ROM bank */
                RAM = new UBytePtr(memory_region(REGION_CPU1));
		if (memory_region_length(REGION_CPU1) > 0x100000)
		{
			cpu_setbank(4, new UBytePtr(RAM, 0x100000));
		}
		else
		{
			cpu_setbank(4, new UBytePtr(RAM, 0));
		}
	
		/* Set the sound CPU ROM banks */
		RAM = new UBytePtr(memory_region(REGION_CPU2));
		cpu_setbank(5,new UBytePtr(RAM, 0x08000));
		cpu_setbank(6,new UBytePtr(RAM, 0x0c000));
		cpu_setbank(7,new UBytePtr(RAM, 0x0e000));
		cpu_setbank(8,new UBytePtr(RAM, 0x0f000));
	
		/* Allocate and point to the memcard - bank 5 */
		neogeo_memcard = new UBytePtr (0x800);
                memset(neogeo_memcard, 0x800, 1);
		
                memcard_status=0;
		memcard_number=0;
	
	
		RAM = new UBytePtr(memory_region(REGION_USER1));
	
		if (RAM.READ_WORD(0x11b00) == 0x4eba)
		{
			/* standard bios */
			neogeo_has_trackball = 0;
	
			/* Remove memory check for now */
			RAM.WRITE_WORD(0x11b00,0x4e71);
			RAM.WRITE_WORD(0x11b02,0x4e71);
			RAM.WRITE_WORD(0x11b16,0x4ef9);
			RAM.WRITE_WORD(0x11b18,0x00c1);
			RAM.WRITE_WORD(0x11b1a,0x1b6a);
	
			/* Patch bios rom, for Calendar errors */
			RAM.WRITE_WORD(0x11c14,0x4e71);
			RAM.WRITE_WORD(0x11c16,0x4e71);
			RAM.WRITE_WORD(0x11c1c,0x4e71);
			RAM.WRITE_WORD(0x11c1e,0x4e71);
	
			/* Rom internal checksum fails for now.. */
			RAM.WRITE_WORD(0x11c62,0x4e71);
			RAM.WRITE_WORD(0x11c64,0x4e71);
		}
		else
		{
			/* special bios with trackball support */
			neogeo_has_trackball = 1;
	
			/* TODO: check the memcard manager patch in neogeo_init_machine(), */
			/* it probably has to be moved as well */
	
			/* Remove memory check for now */
			RAM.WRITE_WORD(0x10c2a,0x4e71);
			RAM.WRITE_WORD(0x10c2c,0x4e71);
			RAM.WRITE_WORD(0x10c40,0x4ef9);
			RAM.WRITE_WORD(0x10c42,0x00c1);
			RAM.WRITE_WORD(0x10c44,0x0c94);
	
			/* Patch bios rom, for Calendar errors */
			RAM.WRITE_WORD(0x10d3e,0x4e71);
			RAM.WRITE_WORD(0x10d40,0x4e71);
			RAM.WRITE_WORD(0x10d46,0x4e71);
			RAM.WRITE_WORD(0x10d48,0x4e71);
	
			/* Rom internal checksum fails for now.. */
			RAM.WRITE_WORD(0x10d8c,0x4e71);
			RAM.WRITE_WORD(0x10d8e,0x4e71);
		}
	
		/* Install custom memory handlers */
		neogeo_custom_memory();
	
	
		/* Flag how to handle IRQ2 raster effect */
		/* 0=write 0,2   1=write2,0 */
		if (strcmp(Machine.gamedrv.name,"neocup98")==0 ||
			strcmp(Machine.gamedrv.name,"ssideki3")==0 ||
			strcmp(Machine.gamedrv.name,"ssideki4")==0)
			neogeo_irq2type = 1;
	} };
	
/*TODO*///	/******************************************************************************/
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr bios_cycle_skip_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		cpu_spinuntil_int();
/*TODO*///		return 0;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	/******************************************************************************/
/*TODO*///	/* Routines to speed up the main processor 				      */
/*TODO*///	/******************************************************************************/
/*TODO*///	#define NEO_CYCLE_R(name,pc,hit,other) public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	#define NEO_CYCLE_RX(name,pc,hit,other,xxx) public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {if(other==xxx) cpu_spinuntil_int(); return hit;} return other;} };
	
//	NEO_CYCLE_R(puzzledp,0x12f2,1,								READ_WORD(&neogeo_ram[0x0000]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(samsho4, 0xaffc,0,								READ_WORD(&neogeo_ram[0x830c]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(karnovr, 0x5b56,0,								READ_WORD(&neogeo_ram[0x3466]))
        public static ReadHandlerPtr karnovr_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==0x5b56) {cpu_spinuntil_int(); return 0;} return neogeo_ram.READ_WORD(0x3466);} };
//	NEO_CYCLE_R(wjammers,0x1362e,READ_WORD(&neogeo_ram[0x5a])&0x7fff,READ_WORD(&neogeo_ram[0x005a]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(strhoops,0x029a,0,								READ_WORD(&neogeo_ram[0x1200]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	//NEO_CYCLE_R(magdrop3,0xa378,READ_WORD(&neogeo_ram[0x60])&0x7fff,READ_WORD(&neogeo_ram[0x0060]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(neobombe,0x09f2,0xffff,							READ_WORD(&neogeo_ram[0x448c]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(trally, 0x1295c,READ_WORD(&neogeo_ram[0x206])-1,READ_WORD(&neogeo_ram[0x0206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	//NEO_CYCLE_R(joyjoy,  0x122c,0xffff,							READ_WORD(&neogeo_ram[0x0554]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_RX(blazstar,0x3b62,0xffff,							READ_WORD(&neogeo_ram[0x1000]),0)
        public static ReadHandlerPtr blazstar_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==0x3b62) {if(neogeo_ram.READ_WORD(0x1000)==0) cpu_spinuntil_int(); return 0xffff;} return neogeo_ram.READ_WORD(0x1000);} };
//	//NEO_CYCLE_R(ridhero, 0xedb0,0,								READ_WORD(&neogeo_ram[0x00ca]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(cyberlip,0x2218,0x0f0f,							READ_WORD(&neogeo_ram[0x7bb4]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(lbowling,0x37b0,0,								READ_WORD(&neogeo_ram[0x0098]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(superspy,0x07ca,0xffff,							READ_WORD(&neogeo_ram[0x108c]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(ttbb,    0x0a58,0xffff,							READ_WORD(&neogeo_ram[0x000e]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(alpham2,0x076e,0xffff,							READ_WORD(&neogeo_ram[0xe2fe]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(eightman,0x12fa,0xffff,							READ_WORD(&neogeo_ram[0x046e]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(roboarmy,0x08e8,0,								READ_WORD(&neogeo_ram[0x4010]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(fatfury1,0x133c,0,								READ_WORD(&neogeo_ram[0x4282]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(burningf,0x0736,0xffff,							READ_WORD(&neogeo_ram[0x000e]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(bstars,  0x133c,0,								READ_WORD(&neogeo_ram[0x000a]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(kotm,    0x1284,0,								READ_WORD(&neogeo_ram[0x0020]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(gpilots, 0x0474,0,								READ_WORD(&neogeo_ram[0xa682]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(lresort, 0x256a,0,								READ_WORD(&neogeo_ram[0x4102]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(fbfrenzy,0x07dc,0,								READ_WORD(&neogeo_ram[0x0020]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(socbrawl,0xa8dc,0xffff,							READ_WORD(&neogeo_ram[0xb20c]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(mutnat,  0x1456,0,								READ_WORD(&neogeo_ram[0x1042]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(aof,     0x6798,0,								READ_WORD(&neogeo_ram[0x8100]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	//NEO_CYCLE_R(countb,  0x16a2,0,								READ_WORD(&neogeo_ram[0x8002]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(ncombat, 0xcb3e,0,								READ_WORD(&neogeo_ram[0x0206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(sengoku, 0x12f4,0,								READ_WORD(&neogeo_ram[0x0088]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	//NEO_CYCLE_R(ncommand,0x11b44,0,								READ_WORD(&neogeo_ram[0x8206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(wh1,     0xf62d4,0xffff,						READ_WORD(&neogeo_ram[0x8206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(androdun,0x26d6,0xffff,							READ_WORD(&neogeo_ram[0x0080]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(bjourney,0xe8aa,READ_WORD(&neogeo_ram[0x206])+1,READ_WORD(&neogeo_ram[0x0206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(maglord, 0xb16a,READ_WORD(&neogeo_ram[0x206])+1,READ_WORD(&neogeo_ram[0x0206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	//NEO_CYCLE_R(janshin, 0x06a0,0,								READ_WORD(&neogeo_ram[0x0026]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_RX(pulstar, 0x2052,0xffff,							READ_WORD(&neogeo_ram[0x1000]),0)
        public static ReadHandlerPtr pulstar_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==0x2052) {if(neogeo_ram.READ_WORD(0x1000)==0) cpu_spinuntil_int(); return 0xffff;} return neogeo_ram.READ_WORD(0x1000);} };
	//NEO_CYCLE_R(mslug   ,0x200a,0xffff,							READ_WORD(&neogeo_ram[0x6ed8]))
//        public static ReadHandlerPtr mslug_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==0x200a) {cpu_spinuntil_int(); return 0xffff;} return neogeo_ram.READ_WORD(0x6ed8);} };
//	NEO_CYCLE_R(neodrift,0x0b76,0xffff,							READ_WORD(&neogeo_ram[0x0424]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(spinmast,0x00f6,READ_WORD(&neogeo_ram[0xf0])+1,	READ_WORD(&neogeo_ram[0x00f0]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(sonicwi2,0x1e6c8,0xffff,						READ_WORD(&neogeo_ram[0xe5b6]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(sonicwi3,0x20bac,0xffff,						READ_WORD(&neogeo_ram[0xea2e]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(goalx3,  0x5298,READ_WORD(&neogeo_ram[0x6])+1,	READ_WORD(&neogeo_ram[0x0006]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	//NEO_CYCLE_R(turfmast,0xd5a8,0xffff,							READ_WORD(&neogeo_ram[0x2e54]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(kabukikl,0x10b0,0,								READ_WORD(&neogeo_ram[0x428a]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(panicbom,0x3ee6,0xffff,							READ_WORD(&neogeo_ram[0x009c]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(wh2,     0x2063fc,READ_WORD(&neogeo_ram[0x8206])+1,READ_WORD(&neogeo_ram[0x8206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(wh2j,    0x109f4,READ_WORD(&neogeo_ram[0x8206])+1,READ_WORD(&neogeo_ram[0x8206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(aodk,    0xea62,READ_WORD(&neogeo_ram[0x8206])+1,READ_WORD(&neogeo_ram[0x8206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(whp,     0xeace,READ_WORD(&neogeo_ram[0x8206])+1,READ_WORD(&neogeo_ram[0x8206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(overtop, 0x1736,READ_WORD(&neogeo_ram[0x8202])+1,READ_WORD(&neogeo_ram[0x8202]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(twinspri,0x492e,READ_WORD(&neogeo_ram[0x8206])+1,READ_WORD(&neogeo_ram[0x8206]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(stakwin, 0x0596,0xffff,							READ_WORD(&neogeo_ram[0x0b92]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
//	NEO_CYCLE_R(shocktro,0xdd28,0,								READ_WORD(&neogeo_ram[0x8344]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
        //	NEO_CYCLE_R(tws96,   0x17f4,0xffff,							READ_WORD(&neogeo_ram[0x010e]))
        public static ReadHandlerPtr tws96_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==0x17f4) {cpu_spinuntil_int(); return 0xffff;} return neogeo_ram.READ_WORD(0x010e);} };
/*TODO*///	//public static ReadHandlerPtr zedblade_cycle_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	//{
/*TODO*///	//	int pc=cpu_get_pc();
/*TODO*///	//	if (pc==0xa2fa || pc==0xa2a0 || pc==0xa2ce || pc==0xa396 || pc==0xa3fa) {cpu_spinuntil_int(); return 0;}
/*TODO*///	//	return READ_WORD(&neogeo_ram[0x9004]);
/*TODO*///	//} };
/*TODO*///	//NEO_CYCLE_R(doubledr,0x3574,0,								READ_WORD(&neogeo_ram[0x1c30]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(galaxyfg,0x09ea,READ_WORD(&neogeo_ram[0x1858])+1,READ_WORD(&neogeo_ram[0x1858]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(wakuwak7,0x1a3c,READ_WORD(&neogeo_ram[0x0bd4])+1,READ_WORD(&neogeo_ram[0x0bd4]))
        //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	public static ReadHandlerPtr mahretsu_cycle_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		int pc=cpu_get_pc();
/*TODO*///		if (pc==0x1580 || pc==0xf3ba ) {cpu_spinuntil_int(); return 0;}
/*TODO*///		return READ_WORD(&neogeo_ram[0x13b2]);
/*TODO*///	} };
                //NEO_CYCLE_R(nam1975, 0x0a1c,0xffff,							READ_WORD(&neogeo_ram[0x12e0]))
                public static ReadHandlerPtr nam1975_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==0x0a1c) {cpu_spinuntil_int(); return 0xffff;} return neogeo_ram.READ_WORD(0x12e0);} };        
/*TODO*///	NEO_CYCLE_R(tpgolf,  0x105c,0,								READ_WORD(&neogeo_ram[0x00a4]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(legendos,0x1864,0xffff,							READ_WORD(&neogeo_ram[0x0002]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	//NEO_CYCLE_R(viewpoin,0x0c16,0,								READ_WORD(&neogeo_ram[0x1216]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(fatfury2,0x10ea,0,								READ_WORD(&neogeo_ram[0x418c]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(bstars2, 0x7e30,0xffff,							READ_WORD(&neogeo_ram[0x001c]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(ssideki, 0x20b0,0xffff,							READ_WORD(&neogeo_ram[0x8c84]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(kotm2,   0x045a,0,								READ_WORD(&neogeo_ram[0x1000]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	public static ReadHandlerPtr samsho_cycle_r  = new ReadHandlerPtr() { public int handler(int offset)
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	{
/*TODO*///		int pc=cpu_get_pc();
/*TODO*///		if (pc==0x3580 || pc==0x0f84 ) {cpu_spinuntil_int(); return 0xffff;}
/*TODO*///		return READ_WORD(&neogeo_ram[0x0a76]);
/*TODO*///	} };
/*TODO*///	NEO_CYCLE_R(fatfursp,0x10da,0,								READ_WORD(&neogeo_ram[0x418c]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(fatfury3,0x9c50,0,								READ_WORD(&neogeo_ram[0x418c]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(tophuntr,0x0ce0,0xffff,							READ_WORD(&neogeo_ram[0x008e]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(savagere,0x056e,0,								READ_WORD(&neogeo_ram[0x8404]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(aof2,    0x8c74,0,								READ_WORD(&neogeo_ram[0x8280]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	//NEO_CYCLE_R(ssideki2,0x7850,0xffff,							READ_WORD(&neogeo_ram[0x4292]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(samsho2, 0x1432,0xffff,							READ_WORD(&neogeo_ram[0x0a30]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(samsho3, 0x0858,0,								READ_WORD(&neogeo_ram[0x8408]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(kof95,   0x39474,0xffff,						READ_WORD(&neogeo_ram[0xa784]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(rbff1,   0x80a2,0,								READ_WORD(&neogeo_ram[0x418c]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	//NEO_CYCLE_R(aof3,    0x15d6,0,								READ_WORD(&neogeo_ram[0x4ee8]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(ninjamas,0x2436,READ_WORD(&neogeo_ram[0x8206])+1,READ_WORD(&neogeo_ram[0x8206]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(kof96,   0x8fc4,0xffff,							READ_WORD(&neogeo_ram[0xa782]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(rbffspec,0x8704,0,								READ_WORD(&neogeo_ram[0x418c]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(kizuna,  0x0840,0,								READ_WORD(&neogeo_ram[0x8808]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(kof97,   0x9c54,0xffff,							READ_WORD(&neogeo_ram[0xa784]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	//NEO_CYCLE_R(mslug2,  0x1656,0xffff,						READ_WORD(&neogeo_ram[0x008c]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(rbff2,   0xc5d0,0,								READ_WORD(&neogeo_ram[0x418c]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(ragnagrd,0xc6c0,0,								READ_WORD(&neogeo_ram[0x0042]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(lastblad,0x1868,0xffff,							READ_WORD(&neogeo_ram[0x9d4e]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(gururin, 0x0604,0xffff,							READ_WORD(&neogeo_ram[0x1002]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	//NEO_CYCLE_R(magdrop2,0x1cf3a,0,								READ_WORD(&neogeo_ram[0x0064]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	//NEO_CYCLE_R(miexchng,0x,,READ_WORD(&neogeo_ram[0x]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	
/*TODO*///	NEO_CYCLE_R(kof98,       0xa146,0xfff,  READ_WORD(&neogeo_ram[0xa784]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(marukodq,0x070e,0,          READ_WORD(&neogeo_ram[0x0210]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	public static ReadHandlerPtr minasan_cycle_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///	        int mem;
/*TODO*///	        if (cpu_get_pc()==0x17766)
/*TODO*///	        {
/*TODO*///	                cpu_spinuntil_int();
/*TODO*///	                mem=READ_WORD(&neogeo_ram[0x00ca]);
/*TODO*///	                mem--;
/*TODO*///	                WRITE_WORD(&neogeo_ram[0x00ca],mem);
/*TODO*///	                return mem;
/*TODO*///	        }
/*TODO*///	        return READ_WORD(&neogeo_ram[0x00ca]);
/*TODO*///	} };
/*TODO*///	NEO_CYCLE_R(stakwin2,0x0b8c,0,          READ_WORD(&neogeo_ram[0x0002]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	public static ReadHandlerPtr bakatono_cycle_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///	        int mem;
/*TODO*///	        if (cpu_get_pc()==0x197cc)
/*TODO*///	        {
/*TODO*///	                cpu_spinuntil_int();
/*TODO*///	                mem=READ_WORD(&neogeo_ram[0x00fa]);
/*TODO*///	                mem--;
/*TODO*///	                WRITE_WORD(&neogeo_ram[0x00fa],mem);
/*TODO*///	                return mem;
/*TODO*///	        }
/*TODO*///	        return READ_WORD(&neogeo_ram[0x00fa]);
/*TODO*///	} };
/*TODO*///	NEO_CYCLE_R(quizkof,0x0450,0,           READ_WORD(&neogeo_ram[0x4464]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(quizdais,   0x0730,0,       READ_WORD(&neogeo_ram[0x59f2]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(quizdai2,   0x1afa,0xffff,  READ_WORD(&neogeo_ram[0x0960]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(popbounc,   0x1196,0xffff,  READ_WORD(&neogeo_ram[0x1008]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(sdodgeb,    0xc22e,0,       READ_WORD(&neogeo_ram[0x1104]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(shocktr2,   0xf410,0,       READ_WORD(&neogeo_ram[0x8348]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(figfever,   0x20c60,0,      READ_WORD(&neogeo_ram[0x8100]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	NEO_CYCLE_R(irrmaze,    0x104e,0,       READ_WORD(&neogeo_ram[0x4b6e]))
                //public static ReadHandlerPtr name##_cycle_r  = new ReadHandlerPtr() { public int handler(int offset) {	if (cpu_get_pc()==pc) {cpu_spinuntil_int(); return hit;} return other;} };
/*TODO*///	
/*TODO*///	/******************************************************************************/
/*TODO*///	/* Routines to speed up the sound processor AVDB 24-10-1998		      */
/*TODO*///	/******************************************************************************/
/*TODO*///	
/*TODO*///	/*
/*TODO*///	 *	Sound V3.0
/*TODO*///	 *
/*TODO*///	 *	Used by puzzle de pon and Super Sidekicks 2
/*TODO*///	 *
/*TODO*///	 */
/*TODO*///	public static ReadHandlerPtr cycle_v3_sr  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		UBytePtr RAM = memory_region(REGION_CPU2);
/*TODO*///	
/*TODO*///		if (cpu_get_pc()==0x0137) {
/*TODO*///			cpu_spinuntil_int();
/*TODO*///			return RAM[0xfeb1];
/*TODO*///		}
/*TODO*///		return RAM[0xfeb1];
/*TODO*///	} };
/*TODO*///	
/*TODO*///	/*
/*TODO*///	 *	Also sound revision no 3.0, but different types.
/*TODO*///	 */
/*TODO*///	public static ReadHandlerPtr ssideki_cycle_sr  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		UBytePtr RAM = memory_region(REGION_CPU2);
/*TODO*///	
/*TODO*///		if (cpu_get_pc()==0x015A) {
/*TODO*///			cpu_spinuntil_int();
/*TODO*///			return RAM[0xfef3];
/*TODO*///		}
/*TODO*///		return RAM[0xfef3];
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr aof_cycle_sr  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		UBytePtr RAM = memory_region(REGION_CPU2);
/*TODO*///	
/*TODO*///		if (cpu_get_pc()==0x0143) {
/*TODO*///			cpu_spinuntil_int();
/*TODO*///			return RAM[0xfef3];
/*TODO*///		}
/*TODO*///		return RAM[0xfef3];
/*TODO*///	} };
	
	/*
	 *	Sound V2.0
	 *
	 *	Used by puzzle Bobble and Goal Goal Goal
	 *
	 */
	
	public static ReadHandlerPtr cycle_v2_sr  = new ReadHandlerPtr() { public int handler(int offset)
	{
		UBytePtr RAM = memory_region(REGION_CPU2);
	
		if (cpu_get_pc()==0x0143) {
			cpu_spinuntil_int();
			return RAM.read(0xfeef);
		}
		return RAM.read(0xfeef);
	} };
	
/*TODO*///	public static ReadHandlerPtr vwpoint_cycle_sr  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		UBytePtr RAM = memory_region(REGION_CPU2);
/*TODO*///	
/*TODO*///		if (cpu_get_pc()==0x0143) {
/*TODO*///			cpu_spinuntil_int();
/*TODO*///			return RAM[0xfe46];
/*TODO*///		}
/*TODO*///		return RAM[0xfe46];
/*TODO*///	} };
/*TODO*///	
/*TODO*///	/*
/*TODO*///	 *	Sound revision no 1.5, and some 2.0 versions,
/*TODO*///	 *	are not fit for speedups, it results in sound drops !
/*TODO*///	 *	Games that use this one are : Ghost Pilots, Joy Joy, Nam 1975
/*TODO*///	 */
/*TODO*///	
/*TODO*///	/*
/*TODO*///	public static ReadHandlerPtr cycle_v15_sr  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		UBytePtr RAM = memory_region(REGION_CPU2);
/*TODO*///	
/*TODO*///		if (cpu_get_pc()==0x013D) {
/*TODO*///			cpu_spinuntil_int();
/*TODO*///			return RAM[0xfe46];
/*TODO*///		}
/*TODO*///		return RAM[0xfe46];
/*TODO*///	} };
/*TODO*///	*/
/*TODO*///	
/*TODO*///	/*
/*TODO*///	 *	Magician Lord uses a different sound core from all other
/*TODO*///	 *	Neo Geo Games.
/*TODO*///	 */
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr maglord_cycle_sr  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		UBytePtr RAM = memory_region(REGION_CPU2);
/*TODO*///	
/*TODO*///		if (cpu_get_pc()==0xd487) {
/*TODO*///			cpu_spinuntil_int();
/*TODO*///			return RAM[0xfb91];
/*TODO*///		}
/*TODO*///		return RAM[0xfb91];
/*TODO*///	} };
	
	/******************************************************************************/
	
	static int prot_data;
	
	public static ReadHandlerPtr fatfury2_protection_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int res = (prot_data >> 24) & 0xff;
	
		switch (offset)
		{
			case 0x55550:
			case 0xffff0:
			case 0x00000:
			case 0xff000:
			case 0x36000:
			case 0x36008:
				return res;
	
			case 0x36004:
			case 0x3600c:
				return ((res & 0xf0) >> 4) | ((res & 0x0f) << 4);
	
			default:
	logerror("unknown protection read at pc %06x, offset %08x\n",cpu_get_pc(),offset);
				return 0;
		}
	} };
	
	public static WriteHandlerPtr fatfury2_protection_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		switch (offset)
		{
			case 0x55552:	/* data == 0x5555; read back from 55550, ffff0, 00000, ff000 */
				prot_data = 0xff00ff00;
				break;
	
			case 0x56782:	/* data == 0x1234; read back from 36000 *or* 36004 */
				prot_data = 0xf05a3601;
				break;
	
			case 0x42812:	/* data == 0x1824; read back from 36008 *or* 3600c */
				prot_data = 0x81422418;
				break;
	
			case 0x55550:
			case 0xffff0:
			case 0xff000:
			case 0x36000:
			case 0x36004:
			case 0x36008:
			case 0x3600c:
				prot_data <<= 8;
				break;
	
			default:
	logerror("unknown protection write at pc %06x, offset %08x, data %02x\n",cpu_get_pc(),offset,data);
				break;
		}
	} };
	
	public static ReadHandlerPtr popbounc_sfix_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	        if (cpu_get_pc()==0x6b10) {return 0;}
	        return neogeo_ram.READ_WORD(0x4fbc);
	} };
	
	static void neogeo_custom_memory()
	{
		/* NeoGeo intro screen cycle skip, used by all games */
	//	install_mem_read_handler(0, 0x10fe8c, 0x10fe8d, bios_cycle_skip_r);
	
	    /* Individual games can go here... */
	
/*TODO*///	#if 1
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"joyjoy"))   install_mem_read_handler(0, 0x100554, 0x100555, joyjoy_cycle_r);	// Slower
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"ridhero"))  install_mem_read_handler(0, 0x1000ca, 0x1000cb, ridhero_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"bstars"))   install_mem_read_handler(0, 0x10000a, 0x10000b, bstars_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"cyberlip")) install_mem_read_handler(0, 0x107bb4, 0x107bb5, cyberlip_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"lbowling")) install_mem_read_handler(0, 0x100098, 0x100099, lbowling_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"superspy")) install_mem_read_handler(0, 0x10108c, 0x10108d, superspy_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"ttbb"))     install_mem_read_handler(0, 0x10000e, 0x10000f, ttbb_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"alpham2"))  install_mem_read_handler(0, 0x10e2fe, 0x10e2ff, alpham2_cycle_r);	// Very little increase.
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"eightman")) install_mem_read_handler(0, 0x10046e, 0x10046f, eightman_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"roboarmy")) install_mem_read_handler(0, 0x104010, 0x104011, roboarmy_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"fatfury1")) install_mem_read_handler(0, 0x104282, 0x104283, fatfury1_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"burningf")) install_mem_read_handler(0, 0x10000e, 0x10000f, burningf_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"kotm"))     install_mem_read_handler(0, 0x100020, 0x100021, kotm_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"gpilots"))  install_mem_read_handler(0, 0x10a682, 0x10a683, gpilots_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"lresort"))  install_mem_read_handler(0, 0x104102, 0x104103, lresort_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"fbfrenzy")) install_mem_read_handler(0, 0x100020, 0x100021, fbfrenzy_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"socbrawl")) install_mem_read_handler(0, 0x10b20c, 0x10b20d, socbrawl_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"mutnat"))   install_mem_read_handler(0, 0x101042, 0x101043, mutnat_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"aof"))      install_mem_read_handler(0, 0x108100, 0x108101, aof_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"countb"))   install_mem_read_handler(0, 0x108002, 0x108003, countb_cycle_r);   // doesn't seem to speed it up.
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"ncombat"))  install_mem_read_handler(0, 0x100206, 0x100207, ncombat_cycle_r);
/*TODO*///	//**	if (!strcmp(Machine.gamedrv.name,"crsword"))  install_mem_read_handler(0, 0x10, 0x10, crsword_cycle_r);			// Can't find this one :-(
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"trally"))   install_mem_read_handler(0, 0x100206, 0x100207, trally_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"sengoku"))  install_mem_read_handler(0, 0x100088, 0x100089, sengoku_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"ncommand")) install_mem_read_handler(0, 0x108206, 0x108207, ncommand_cycle_r);	// Slower
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"wh1"))      install_mem_read_handler(0, 0x108206, 0x108207, wh1_cycle_r);
/*TODO*///	//**	if (!strcmp(Machine.gamedrv.name,"sengoku2")) install_mem_read_handler(0, 0x10, 0x10, sengoku2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"androdun")) install_mem_read_handler(0, 0x100080, 0x100081, androdun_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"bjourney")) install_mem_read_handler(0, 0x100206, 0x100207, bjourney_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"maglord"))  install_mem_read_handler(0, 0x100206, 0x100207, maglord_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"janshin"))  install_mem_read_handler(0, 0x100026, 0x100027, janshin_cycle_r);	// No speed difference
		if (strcmp(Machine.gamedrv.name,"pulstar")==0)  install_mem_read_handler(0, 0x101000, 0x101001, pulstar_cycle_r);
		if (strcmp(Machine.gamedrv.name,"blazstar")==0) install_mem_read_handler(0, 0x101000, 0x101001, blazstar_cycle_r);
	//**	if (!strcmp(Machine.gamedrv.name,"pbobble"))  install_mem_read_handler(0, 0x10, 0x10, pbobble_cycle_r);		// Can't find this one :-(
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"puzzledp")) install_mem_read_handler(0, 0x100000, 0x100001, puzzledp_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"neodrift")) install_mem_read_handler(0, 0x100424, 0x100425, neodrift_cycle_r);
/*TODO*///	//**	if (!strcmp(Machine.gamedrv.name,"neomrdo"))  install_mem_read_handler(0, 0x10, 0x10, neomrdo_cycle_r);		// Can't find this one :-(
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"spinmast")) install_mem_read_handler(0, 0x100050, 0x100051, spinmast_cycle_r);
		if (strcmp(Machine.gamedrv.name,"karnovr")==0)  install_mem_read_handler(0, 0x103466, 0x103467, karnovr_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"wjammers")) install_mem_read_handler(0, 0x10005a, 0x10005b, wjammers_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"strhoops")) install_mem_read_handler(0, 0x101200, 0x101201, strhoops_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"magdrop3")) install_mem_read_handler(0, 0x100060, 0x100061, magdrop3_cycle_r);	// The game starts glitching.
/*TODO*///	//**	if (!strcmp(Machine.gamedrv.name,"pspikes2")) install_mem_read_handler(0, 0x10, 0x10, pspikes2_cycle_r);		// Can't find this one :-(
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"sonicwi2")) install_mem_read_handler(0, 0x10e5b6, 0x10e5b7, sonicwi2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"sonicwi3")) install_mem_read_handler(0, 0x10ea2e, 0x10ea2f, sonicwi3_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"goalx3"))   install_mem_read_handler(0, 0x100006, 0x100007, goalx3_cycle_r);
	//	if (strcmp(Machine.gamedrv.name,"mslug")==0)    install_mem_read_handler(0, 0x106ed8, 0x106ed9, mslug_cycle_r);		// Doesn't work properly.
	//	if (!strcmp(Machine.gamedrv.name,"turfmast")) install_mem_read_handler(0, 0x102e54, 0x102e55, turfmast_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"kabukikl")) install_mem_read_handler(0, 0x10428a, 0x10428b, kabukikl_cycle_r);
/*TODO*///	
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"panicbom")) install_mem_read_handler(0, 0x10009c, 0x10009d, panicbom_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"neobombe")) install_mem_read_handler(0, 0x10448c, 0x10448d, neobombe_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"wh2"))      install_mem_read_handler(0, 0x108206, 0x108207, wh2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"wh2j"))     install_mem_read_handler(0, 0x108206, 0x108207, wh2j_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"aodk"))     install_mem_read_handler(0, 0x108206, 0x108207, aodk_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"whp"))      install_mem_read_handler(0, 0x108206, 0x108207, whp_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"overtop"))  install_mem_read_handler(0, 0x108202, 0x108203, overtop_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"twinspri")) install_mem_read_handler(0, 0x108206, 0x108207, twinspri_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"stakwin"))  install_mem_read_handler(0, 0x100b92, 0x100b93, stakwin_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"shocktro")) install_mem_read_handler(0, 0x108344, 0x108345, shocktro_cycle_r);
		if (strcmp(Machine.gamedrv.name,"tws96")==0)    install_mem_read_handler(0, 0x10010e, 0x10010f, tws96_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"zedblade")) install_mem_read_handler(0, 0x109004, 0x109005, zedblade_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"doubledr")) install_mem_read_handler(0, 0x101c30, 0x101c31, doubledr_cycle_r);
/*TODO*///	//**	if (!strcmp(Machine.gamedrv.name,"gowcaizr")) install_mem_read_handler(0, 0x10, 0x10, gowcaizr_cycle_r);		// Can't find this one :-(
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"galaxyfg")) install_mem_read_handler(0, 0x101858, 0x101859, galaxyfg_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"wakuwak7")) install_mem_read_handler(0, 0x100bd4, 0x100bd5, wakuwak7_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"mahretsu")) install_mem_read_handler(0, 0x1013b2, 0x1013b3, mahretsu_cycle_r);
		if (strcmp(Machine.gamedrv.name,"nam1975")==0)  install_mem_read_handler(0, 0x1012e0, 0x1012e1, nam1975_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"tpgolf"))   install_mem_read_handler(0, 0x1000a4, 0x1000a5, tpgolf_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"legendos")) install_mem_read_handler(0, 0x100002, 0x100003, legendos_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"viewpoin")) install_mem_read_handler(0, 0x101216, 0x101217, viewpoin_cycle_r);	// Doesn't work
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"fatfury2")) install_mem_read_handler(0, 0x10418c, 0x10418d, fatfury2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"bstars2"))  install_mem_read_handler(0, 0x10001c, 0x10001d, bstars2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"ssideki"))  install_mem_read_handler(0, 0x108c84, 0x108c85, ssideki_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"kotm2"))    install_mem_read_handler(0, 0x101000, 0x101001, kotm2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"samsho"))   install_mem_read_handler(0, 0x100a76, 0x100a77, samsho_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"fatfursp")) install_mem_read_handler(0, 0x10418c, 0x10418d, fatfursp_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"fatfury3")) install_mem_read_handler(0, 0x10418c, 0x10418d, fatfury3_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"tophuntr")) install_mem_read_handler(0, 0x10008e, 0x10008f, tophuntr_cycle_r);	// Can't test this at the moment, it crashes.
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"savagere")) install_mem_read_handler(0, 0x108404, 0x108405, savagere_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"kof94"))    install_mem_read_handler(0, 0x10, 0x10, kof94_cycle_r);				// Can't do this I think. There seems to be too much code in the idle loop.
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"aof2"))     install_mem_read_handler(0, 0x108280, 0x108281, aof2_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"ssideki2")) install_mem_read_handler(0, 0x104292, 0x104293, ssideki2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"samsho2"))  install_mem_read_handler(0, 0x100a30, 0x100a31, samsho2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"samsho3"))  install_mem_read_handler(0, 0x108408, 0x108409, samsho3_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"kof95"))    install_mem_read_handler(0, 0x10a784, 0x10a785, kof95_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"rbff1"))    install_mem_read_handler(0, 0x10418c, 0x10418d, rbff1_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"aof3"))     install_mem_read_handler(0, 0x104ee8, 0x104ee9, aof3_cycle_r);		// Doesn't work properly.
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"ninjamas")) install_mem_read_handler(0, 0x108206, 0x108207, ninjamas_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"kof96"))    install_mem_read_handler(0, 0x10a782, 0x10a783, kof96_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"samsho4"))  install_mem_read_handler(0, 0x10830c, 0x10830d, samsho4_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"rbffspec")) install_mem_read_handler(0, 0x10418c, 0x10418d, rbffspec_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"kizuna"))   install_mem_read_handler(0, 0x108808, 0x108809, kizuna_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"kof97"))    install_mem_read_handler(0, 0x10a784, 0x10a785, kof97_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"mslug2"))   install_mem_read_handler(0, 0x10008c, 0x10008d, mslug2_cycle_r);	// Breaks the game
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"rbff2"))    install_mem_read_handler(0, 0x10418c, 0x10418d, rbff2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"ragnagrd")) install_mem_read_handler(0, 0x100042, 0x100043, ragnagrd_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"lastblad")) install_mem_read_handler(0, 0x109d4e, 0x109d4f, lastblad_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"gururin"))  install_mem_read_handler(0, 0x101002, 0x101003, gururin_cycle_r);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"magdrop2")) install_mem_read_handler(0, 0x100064, 0x100065, magdrop2_cycle_r);	// Graphic Glitches
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"miexchng")) install_mem_read_handler(0, 0x10, 0x10, miexchng_cycle_r);			// Can't do this.
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"kof98"))    install_mem_read_handler(0, 0x10a784, 0x10a785, kof98_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"marukodq")) install_mem_read_handler(0, 0x100210, 0x100211, marukodq_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"minasan"))  install_mem_read_handler(0, 0x1000ca, 0x1000cb, minasan_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"stakwin2")) install_mem_read_handler(0, 0x100002, 0x100003, stakwin2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"bakatono")) install_mem_read_handler(0, 0x1000fa, 0x1000fb, bakatono_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"quizkof"))  install_mem_read_handler(0, 0x104464, 0x104465, quizkof_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"quizdais")) install_mem_read_handler(0, 0x1059f2, 0x1059f3, quizdais_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"quizdai2")) install_mem_read_handler(0, 0x100960, 0x100961, quizdai2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"popbounc")) install_mem_read_handler(0, 0x101008, 0x101009, popbounc_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"sdodgeb"))  install_mem_read_handler(0, 0x101104, 0x101105, sdodgeb_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"shocktr2")) install_mem_read_handler(0, 0x108348, 0x108349, shocktr2_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"figfever")) install_mem_read_handler(0, 0x108100, 0x108101, figfever_cycle_r);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"irrmaze"))  install_mem_read_handler(0, 0x104b6e, 0x104b6f, irrmaze_cycle_r);
/*TODO*///	
/*TODO*///	#endif
/*TODO*///	
/*TODO*///		/* AVDB cpu spins based on sound processor status */
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"puzzledp")) install_mem_read_handler(1, 0xfeb1, 0xfeb1, cycle_v3_sr);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"ssideki2")) install_mem_read_handler(1, 0xfeb1, 0xfeb1, cycle_v3_sr);
/*TODO*///	
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"ssideki"))  install_mem_read_handler(1, 0xfef3, 0xfef3, ssideki_cycle_sr);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"aof"))      install_mem_read_handler(1, 0xfef3, 0xfef3, aof_cycle_sr);
/*TODO*///	
		if (strcmp(Machine.gamedrv.name,"pbobble")==0) install_mem_read_handler(1, 0xfeef, 0xfeef, cycle_v2_sr);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"goalx3")) install_mem_read_handler(1, 0xfeef, 0xfeef, cycle_v2_sr);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"fatfury1")) install_mem_read_handler(1, 0xfeef, 0xfeef, cycle_v2_sr);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"mutnat")) install_mem_read_handler(1, 0xfeef, 0xfeef, cycle_v2_sr);
/*TODO*///	
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"maglord")) install_mem_read_handler(1, 0xfb91, 0xfb91, maglord_cycle_sr);
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"vwpoint")) install_mem_read_handler(1, 0xfe46, 0xfe46, vwpoint_cycle_sr);
/*TODO*///	
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"joyjoy")) install_mem_read_handler(1, 0xfe46, 0xfe46, cycle_v15_sr);
	//	if (!strcmp(Machine.gamedrv.name,"nam1975")) install_mem_read_handler(1, 0xfe46, 0xfe46, cycle_v15_sr);
/*TODO*///	//	if (!strcmp(Machine.gamedrv.name,"gpilots")) install_mem_read_handler(1, 0xfe46, 0xfe46, cycle_v15_sr);
/*TODO*///	
/*TODO*///		/* kludges */
/*TODO*///	
/*TODO*///		if (!strcmp(Machine.gamedrv.name,"gururin"))
/*TODO*///		{
/*TODO*///			/* Fix a really weird problem. The game clears the video RAM but goes */
/*TODO*///			/* beyond the tile RAM, corrupting the zoom control RAM. After that it */
/*TODO*///			/* initializes the control RAM, but then corrupts it again! */
/*TODO*///			UBytePtr RAM = memory_region(REGION_CPU1);
/*TODO*///			WRITE_WORD(&RAM[0x1328],0x4e71);
/*TODO*///			WRITE_WORD(&RAM[0x132a],0x4e71);
/*TODO*///			WRITE_WORD(&RAM[0x132c],0x4e71);
/*TODO*///			WRITE_WORD(&RAM[0x132e],0x4e71);
/*TODO*///		}
	
		if (Machine.sample_rate==0 &&
				strcmp(Machine.gamedrv.name,"popbounc")==0)
		/* the game hangs after a while without this patch */
			install_mem_read_handler(0, 0x104fbc, 0x104fbd, popbounc_sfix_r);
	
		/* hacks to make the games which do protection checks run in arcade mode */
		/* we write protect a SRAM location so it cannot be set to 1 */
		sram_protection_hack = -1;
		if (strcmp(Machine.gamedrv.name,"fatfury3")==0 ||
				 strcmp(Machine.gamedrv.name,"samsho3")==0 ||
				 strcmp(Machine.gamedrv.name,"samsho4")==0 ||
				 strcmp(Machine.gamedrv.name,"aof3")==0 ||
				 strcmp(Machine.gamedrv.name,"rbff1")==0 ||
				 strcmp(Machine.gamedrv.name,"rbffspec")==0 ||
				 strcmp(Machine.gamedrv.name,"kof95")==0 ||
				 strcmp(Machine.gamedrv.name,"kof96")==0 ||
				 strcmp(Machine.gamedrv.name,"kof97")==0 ||
				 strcmp(Machine.gamedrv.name,"kof98")==0 ||
				 strcmp(Machine.gamedrv.name,"kof99")==0 ||
				 strcmp(Machine.gamedrv.name,"kizuna")==0 ||
				 strcmp(Machine.gamedrv.name,"lastblad")==0 ||
				 strcmp(Machine.gamedrv.name,"lastbld2")==0 ||
				 strcmp(Machine.gamedrv.name,"rbff2")==0 ||
				 strcmp(Machine.gamedrv.name,"mslug2")==0 ||
				 strcmp(Machine.gamedrv.name,"garou")==0)
			sram_protection_hack = 0x100;
	
		if (strcmp(Machine.gamedrv.name,"pulstar")==0)
			sram_protection_hack = 0x35a;
	
		if (strcmp(Machine.gamedrv.name,"ssideki")==0)
		{
			/* patch out protection check */
			/* the protection routines are at 0x25dcc and involve reading and writing */
			/* addresses in the 0x2xxxxx range */
			UBytePtr RAM = new UBytePtr(memory_region(REGION_CPU1));
			RAM.WRITE_WORD(0x2240,0x4e71);
		}
	
		/* Hacks the program rom of Fatal Fury 2, needed either in arcade or console mode */
		/* otherwise at level 2 you cannot hit the opponent and other problems */
		if (strcmp(Machine.gamedrv.name,"fatfury2")==0)
		{
			/* there seems to also be another protection check like the countless ones */
			/* patched above by protectiong a SRAM location, but that trick doesn't work */
			/* here (or maybe the SRAM location to protect is different), so I patch out */
			/* the routine which trashes memory. Without this, the game goes nuts after */
			/* the first bonus stage. */
			UBytePtr RAM = new UBytePtr(memory_region(REGION_CPU1));
			RAM.WRITE_WORD(0xb820,0x4e71);
			RAM.WRITE_WORD(0xb822,0x4e71);
	
			/* again, the protection involves reading and writing addresses in the */
			/* 0x2xxxxx range. There are several checks all around the code. */
			install_mem_read_handler (0, 0x200000, 0x2fffff, fatfury2_protection_r);
			install_mem_write_handler(0, 0x200000, 0x2fffff, fatfury2_protection_w);
		}
	
		if (strcmp(Machine.gamedrv.name,"fatfury3")==0)
		{
			/* patch the first word, it must be 0x0010 not 0x0000 (initial stack pointer) */
			UBytePtr RAM = new UBytePtr(memory_region(REGION_CPU1));
			RAM.WRITE_WORD(0x0000,0x0010);
		}
	
		if (strcmp(Machine.gamedrv.name,"mslugx")==0)
		{
			/* patch out protection checks */
			int i;
			UBytePtr RAM = new UBytePtr(memory_region(REGION_CPU1));
	
			for (i = 0;i < 0x100000;i+=2)
			{
				if (RAM.READ_WORD(i+0) == 0x0243 &&
					RAM.READ_WORD(i+2) == 0x0001 &&	/* andi.w  #$1, D3 */
					RAM.READ_WORD(i+4) == 0x6600)		/* bne xxxx */
				{
					RAM.WRITE_WORD(i+4,0x4e71);
					RAM.WRITE_WORD(i+6,0x4e71);
				}
			}
	
			RAM.WRITE_WORD(0x3bdc,0x4e71);
			RAM.WRITE_WORD(0x3bde,0x4e71);
			RAM.WRITE_WORD(0x3be0,0x4e71);
			RAM.WRITE_WORD(0x3c0c,0x4e71);
			RAM.WRITE_WORD(0x3c0e,0x4e71);
			RAM.WRITE_WORD(0x3c10,0x4e71);
	
			RAM.WRITE_WORD(0x3c36,0x4e71);
			RAM.WRITE_WORD(0x3c38,0x4e71);
		}
	}
	
	
	
	public static WriteHandlerPtr neogeo_sram_lock_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		sram_locked = 1;
	} };
	
	public static WriteHandlerPtr neogeo_sram_unlock_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		sram_locked = 0;
	} };
	
	public static ReadHandlerPtr neogeo_sram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return neogeo_sram.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr neogeo_sram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (sram_locked != 0)
		{
	logerror("PC %06x: warning: write %02x to SRAM %04x while it was protected\n",cpu_get_pc(),data,offset);
		}
		else
		{
			if (offset == sram_protection_hack)
			{
				if (data == 0x0001 || data == 0xff000001)
					return;	/* fake protection pass */
			}
	
			COMBINE_WORD_MEM(neogeo_sram,offset,data);
		}
	} };
	
	public static nvramPtr neogeo_nvram_handler  = new nvramPtr() { public void handler(Object file, int read_or_write) 
	{
/*TODO*///		if (read_or_write != 0)
/*TODO*///		{
/*TODO*///			/* Save the SRAM settings */
/*TODO*///			osd_fwrite_msbfirst(file,neogeo_sram,0x2000);
/*TODO*///	
/*TODO*///			/* save the memory card */
/*TODO*///			neogeo_memcard_save();
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			/* Load the SRAM settings for this game */
/*TODO*///			if (file != 0)
/*TODO*///				osd_fread_msbfirst(file,neogeo_sram,0x2000);
/*TODO*///			else
				memset(neogeo_sram,0,0x10000);
/*TODO*///	
/*TODO*///			/* load the memory card */
/*TODO*///			neogeo_memcard_load(memcard_number);
/*TODO*///		}
	} };
	
	
	
	/*
	    INFORMATION:
	
	    Memory card is a 2kb battery backed RAM.
	    It is accessed thru 0x800000-0x800FFF.
	    Even bytes are always 0xFF
	    Odd bytes are memcard data (0x800 bytes)
	
	    Status byte at 0x380000: (BITS ARE ACTIVE *LOW*)
	
	    0 PAD1 START
	    1 PAD1 SELECT
	    2 PAD2 START
	    3 PAD2 SELECT
	    4 --\  MEMORY CARD
	    5 --/  INSERTED
	    6 MEMORY CARD WRITE PROTECTION
	    7 UNUSED (?)
	*/
	
	
	
	
	/********************* MEMCARD ROUTINES **********************/
	public static ReadHandlerPtr neogeo_memcard_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		if (memcard_status==1)
		{
			return (neogeo_memcard.read(offset >> 1) | 0xFF00);
		}
		else
			return 0xFFFF;
	} };
	
	public static WriteHandlerPtr neogeo_memcard_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (memcard_status==1)
		{
			neogeo_memcard.write(offset>>1, (data&0xFF));
		}
	} };
	
/*TODO*///	int	neogeo_memcard_load(int number)
/*TODO*///	{
/*TODO*///	    char name[16];
/*TODO*///	    void *f;
/*TODO*///	
/*TODO*///	    sprintf(name, "MEMCARD.%03d", number);
/*TODO*///	    if ((f=osd_fopen(0, name, OSD_FILETYPE_MEMCARD,0))!=0)
/*TODO*///	    {
/*TODO*///	        osd_fread(f,neogeo_memcard,0x800);
/*TODO*///	        osd_fclose(f);
/*TODO*///	        return 1;
/*TODO*///	    }
/*TODO*///	    return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	void	neogeo_memcard_save(void)
/*TODO*///	{
/*TODO*///	    char name[16];
/*TODO*///	    void *f;
/*TODO*///	
/*TODO*///	    if (memcard_number!=-1)
/*TODO*///	    {
/*TODO*///	        sprintf(name, "MEMCARD.%03d", memcard_number);
/*TODO*///	        if ((f=osd_fopen(0, name, OSD_FILETYPE_MEMCARD,1))!=0)
/*TODO*///	        {
/*TODO*///	            osd_fwrite(f,neogeo_memcard,0x800);
/*TODO*///	            osd_fclose(f);
/*TODO*///	        }
/*TODO*///	    }
/*TODO*///	}
/*TODO*///	
/*TODO*///	void	neogeo_memcard_eject(void)
/*TODO*///	{
/*TODO*///	   if (memcard_number!=-1)
/*TODO*///	   {
/*TODO*///	       neogeo_memcard_save();
/*TODO*///	       memset(neogeo_memcard, 0, 0x800);
/*TODO*///	       memcard_status=0;
/*TODO*///	       memcard_number=-1;
/*TODO*///	   }
/*TODO*///	}
/*TODO*///	
/*TODO*///	int neogeo_memcard_create(int number)
/*TODO*///	{
/*TODO*///	    char buf[0x800];
/*TODO*///	    char name[16];
/*TODO*///	    void *f1, *f2;
/*TODO*///	
/*TODO*///	    sprintf(name, "MEMCARD.%03d", number);
/*TODO*///	    if ((f1=osd_fopen(0, name, OSD_FILETYPE_MEMCARD,0))==0)
/*TODO*///	    {
/*TODO*///	        if ((f2=osd_fopen(0, name, OSD_FILETYPE_MEMCARD,1))!=0)
/*TODO*///	        {
/*TODO*///	            osd_fwrite(f2,buf,0x800);
/*TODO*///	            osd_fclose(f2);
/*TODO*///	            return 1;
/*TODO*///	        }
/*TODO*///	    }
/*TODO*///	    else
/*TODO*///	        osd_fclose(f1);
/*TODO*///	
/*TODO*///	    return 0;
/*TODO*///	}
/*TODO*///	
}
