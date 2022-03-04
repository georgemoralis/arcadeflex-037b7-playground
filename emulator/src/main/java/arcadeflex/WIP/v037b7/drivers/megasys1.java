/***************************************************************************

						-= Jaleco Mega System 1 =-

				driver by	Luca Elia (eliavit@unina.it)


To enter service mode in some games hit 5+F3.


Game						Year	System	Dumped by			Supported ?
---------------------------------------------------------------------------
64th Street  (World) /		1991	C		AraCORN 		*	Yes
64th Street  (Japan)		1991	C		J-Rom	 		*	Yes
Astyanax          (World) /	1989	A		-				*	Yes (encrypted)
The Lord of King  (Japan)	1989	A	 	J-Rom			*	Yes (encrypted)
Avenging Spirit (World) /	1991	B		AraCORN 		*	Yes
Phantasm        (Japan)		1990	A		J-Rom 			*	Yes (encrypted)
Big Striker					1992	C		ShinobiZ & COY	*	Yes
Chimera Beast				1993	C		J-Rom	 		*	Yes
Cybattler					1993	C		AraCORN			*	Yes
Earth Defense Force			1991	B		AraCORN			*	Yes
Hachoo!						1989	A		AraCORN 		*	Yes (encrypted)
Iga Ninjyutsuden (Japan)	1988	A		J-Rom			*	Yes (encrypted)
Kick Off (Japan)			1988	A		AraCORN			*	Yes
Legend of Makai (World) /	1988	Z		AraCORN				Yes
Makai Densetsu  (Japan)		1988	Z		-					Yes
P-47  (World) /				1988	A	 	-					Yes
P-47  (Japan)				1988	A		J-rom				Yes
Peek-a-Boo!					1993	D		Bart				Yes
Plus Alpha					1989	A		J-Rom 				Yes (encrypted)
RodLand  (World) /			1990	A		AraCORN 		*	Yes (encrypted)
RodLand  (Japan)			1990	A		-				*	Yes
Saint Dragon				1989	A		J-Rom 				Yes (encrypted)
Soldam (Japan)				1992	A		J-Rom			*	Yes (encrypted)
Takeda Shingen (Japan)		1988	A		J-Rom			*	Yes (encrypted)
------------------------------------------------------------^--------------
															|
							The Priority Prom is missing for these games !



Hardware	Main CPU	Sound CPU	Sound Chips
----------------------------------------------------------------
MS1 - Z		68000		Z80			YM2203c
MS1 - A		68000		68000		YM2151	OKI-M6295 x 2 YM3012
MS1 - B		68000		68000		YM2151	OKI-M6295 x 2 YM3012
MS1 - C		68000		68000		YM2151	OKI-M6295 x 2 YM3012
MS1 - D		68000		-			-		OKI-M6295 x 1
----------------------------------------------------------------



Main CPU	RW		MS1-A/Z			MS1-B			MS1-C			MS1-D
-----------------------------------------------------------------------------------
ROM			R	000000-03ffff	000000-03ffff	000000-07ffff	000000-03ffff
								080000-0bffff
Video Regs	 W	084000-0843ff	044000-0443ff	0c0000-0cffff	0c0000-0cffff
Palette		RW	088000-0887ff	048000-0487ff	0f8000-0f87ff	0d8000-0d87ff
Object RAM	RW	08e000-08ffff	04e000-04ffff	0d2000-0d3fff	0ca000-0cbfff
Scroll 0	RW	090000-093fff	050000-053fff	0e0000-0e3fff	0d0000-0d3fff
Scroll 1	RW	094000-097fff	054000-057fff	0e8000-0ebfff	0e8000-0ebfff
Scroll 2	RW	098000-09bfff	058000-05bfff	0f0000-0f3fff	-
Work RAM	RW	0f0000-0fffff*	060000-07ffff*	ff0000-ffffff*	1f0000-1fffff
Input Ports R	080000-080009	0e0000-0e0001**	0d8000-d80001**	100000-100001**
-----------------------------------------------------------------------------------
* Can vary 				** Through protection.



Sound CPU		RW		MS1-A			MS1-B			MS1-C			MS1-D
-----------------------------------------------------------------------------------
ROM				R	000000-01ffff	000000-01ffff	000000-01ffff	 No Sound CPU
Latch #1		R	040000-040001	<				060000-060001
Latch #2		 W	060000-060001	<				<
2151 reg		 W	080000-080001	<				<
2151 data		 W	080002-080003	<				<
2151 status		R 	080002-080003	<				<
6295 #1 data	 W 	0a0000-0a0003	<				<
6295 #1 status	R 	0a0000-0a0001	<				<
6295 #2 data	 W 	0c0000-0c0003	<				<
6295 #2 status	R 	0c0000-0c0001	<				<
RAM				RW	0f0000-0f3fff	0e0000-0effff?	<
-----------------------------------------------------------------------------------


								Issues / To Do
								--------------

- There's a 512 byte PROM in the video section (different for every game)
  that controls the priorities. It's been dumped for only a few games, so
  we have to fake their data.

- Making the M6295 status register return 0 fixes the music tempo in
  avspirit, 64street, astyanax etc. but makes most of the effects in
  hachoo disappear! Define SOUND_HACK to 0 to turn this hack off

- Iganinju doesn't work properly: I have to patch lev3 irq and it severely
  slows down at times (and it gets worse if I increase the clock)

- Sprite / Sprite and Sprite / Layers priorities must be made orthogonal
  (Hachoo! player passes over the portal at the end of level 2 otherwise)

- VERY bad sprite lag in iganinju and plusalph and generally others.
  Is this a sprites buffer issue ?

- Understand an handful of unknown bits in video regs


***************************************************************************/



/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.drivers;

import static arcadeflex.WIP.v037b7.drivers.megasys1H.*;
import static arcadeflex.WIP.v037b7.vidhrdw.megasys1.*;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound._2151intf.*;
import static arcadeflex.v037b7.sound._2151intfH.*;
import static arcadeflex.v037b7.sound._2203intf.*;
import static arcadeflex.v037b7.sound._2203intfH.YM2203_VOL;
import arcadeflex.v037b7.sound._2203intfH.YM2203interface;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static arcadeflex.v037b7.sound.mixerH.*;
import static arcadeflex.v037b7.sound.okim6295.*;
import static arcadeflex.v037b7.sound.okim6295H.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;

public class megasys1
{
	// This will fix tempo and samples in at least avsprit, 64street, astyanax
	// but will break at least one game: hachoo

	static int SOUND_HACK = 1;
	
	/* Variables only used here: */
	static int ip_select;
        static int[] ip_select_values = new int[5];

/*TODO*///	/* Variables defined in vidhrdw: */
/*TODO*///	
/*TODO*///	/* Functions defined in vidhrdw: */
/*TODO*///	void megasys1_convert_prom(UBytePtr palette, unsigned short *colortable,const UBytePtr prom);
	
	
	
	
	public static InitMachinePtr megasys1_init_machine = new InitMachinePtr() { public void handler() 
	{
		ip_select = 0;	/* reset protection */
	} };
	
	
	
	/*
	**
	**	Main cpu data
	**
	**
	*/
	
	
	/***************************************************************************
							[ Main CPU - System A / Z ]
	***************************************************************************/
	
	public static ReadHandlerPtr coins_r  = new ReadHandlerPtr() { public int handler(int offset)   {return input_port_0_r.handler(0);} };	// < 00 | Coins >
	public static ReadHandlerPtr player1_r  = new ReadHandlerPtr() { public int handler(int offset) {return input_port_1_r.handler(0);} };	// < 00 | Player 1 >
	public static ReadHandlerPtr player2_r  = new ReadHandlerPtr() { public int handler(int offset) {return (input_port_2_r.handler(0)<<8)+input_port_3_r.handler(0);} };		// < Reserve | Player 2 >
	public static ReadHandlerPtr dsw1_r  = new ReadHandlerPtr() { public int handler(int offset)	 {return input_port_4_r.handler(0);} };							//   DSW 1
	public static ReadHandlerPtr dsw2_r  = new ReadHandlerPtr() { public int handler(int offset)	 {return input_port_5_r.handler(0);} };							//   DSW 2
	public static ReadHandlerPtr dsw_r  = new ReadHandlerPtr() { public int handler(int offset)	 {return (input_port_4_r.handler(0)<<8)+input_port_5_r.handler(0);} };		// < DSW 1 | DSW 2 >
	
	
	static int INTERRUPT_NUM_A		= 3;
	static InterruptPtr interrupt_A = new InterruptPtr() { public int handler() 
	{
		switch ( cpu_getiloops() )
		{
			case 0:		return 3;
			case 1:		return 2;
			case 2:		return 1;
			default:	return ignore_interrupt.handler();
		}
	}};

	static int INTERRUPT_NUM_Z		= INTERRUPT_NUM_A;
	static InterruptPtr interrupt_Z = interrupt_A;
/*TODO*///	
/*TODO*///	
/*TODO*///	#define MEMORYMAP_A(_shortname_,_ram_start_,_ram_end_) 
/*TODO*///	static MemoryReadAddress readmem_##_shortname_[] =
/*TODO*///	{ 
/*TODO*///		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
/*TODO*///		new MemoryReadAddress( _ram_start_, _ram_end_, MRA_BANK1				), 
/*TODO*///		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
/*TODO*///		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
/*TODO*///		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
/*TODO*///		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
/*TODO*///		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
/*TODO*///		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
/*TODO*///		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
/*TODO*///		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
/*TODO*///		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
/*TODO*///		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
/*TODO*///		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
/*TODO*///		new MemoryReadAddress( -1 ) 
/*TODO*///	}; 
/*TODO*///	static MemoryWriteAddress writemem_##_shortname_[] =
/*TODO*///	{ 
/*TODO*///	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
/*TODO*///		new MemoryWriteAddress( _ram_start_, _ram_end_, MWA_BANK1,			megasys1_ram			), 
/*TODO*///		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
/*TODO*///		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
/*TODO*///		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
/*TODO*///		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0,0	), 
/*TODO*///		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1,0	), 
/*TODO*///		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2,0	), 
/*TODO*///		new MemoryWriteAddress( -1 ) 
/*TODO*///	};
/*TODO*///	
/*TODO*///	#define MEMORYMAP_Z		MEMORYMAP_A
/*TODO*///	
	
	
	/***************************************************************************
								[ Main CPU - System B ]
	***************************************************************************/
	
	static int INTERRUPT_NUM_B		= 3;
	static InterruptPtr interrupt_B = new InterruptPtr() { public int handler() 
	{
		switch (cpu_getiloops())
		{
			case 0:		return 4;
			case 1:		return 1;
			default:	return 2;
		}
	}};
	
	
	
	/*			 Read the input ports, through a protection device:
	
	 ip_select_values must contain the 5 codes sent to the protection device
	 in order to obtain the status of the following 5 input ports:
	
			 Coins	Player1		Player2		DSW1		DSW2
	
	 in that order.			*/
	
	public static ReadHandlerPtr ip_select_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	int i;
	
	//	Coins	P1		P2		DSW1	DSW2
	//	57		53		54		55		56		< 64street
	//	37		35		36		33		34		< avspirit
	//	58		54		55		56		57		< bigstrik
	//	56		52		53		54		55		< cybattlr
	// 	20		21		22		23		24		< edf
	
		/* f(x) = ((x*x)>>4)&0xFF ; f(f($D)) == 6 */
		if ((ip_select & 0xF0) == 0xF0) return 0x0D;
	
		for (i = 0; i < 5; i++)	if (ip_select == ip_select_values[i]) break;
	
		switch (i)
		{
				case 0x0 :	return coins_r.handler(0);		//break;
				case 0x1 :	return player1_r.handler(0);	//break;
				case 0x2 :	return player2_r.handler(0);	//break;
				case 0x3 :	return dsw1_r.handler(0);		//break;
				case 0x4 :	return dsw2_r.handler(0);		//break;
				default	 :	return 0x06;
		}
                
	} };
	
	public static WriteHandlerPtr ip_select_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		ip_select = COMBINE_WORD(ip_select,data);
		cpu_cause_interrupt(0,2);
	} };
	
	
/*TODO*///	#define MEMORYMAP_B(_shortname_,_ram_start_,_ram_end_) 
/*TODO*///	static MemoryReadAddress readmem_##_shortname_[] =
/*TODO*///	{
/*TODO*///		new MemoryReadAddress( 0x000000, 0x03ffff, MRA_ROM					),
/*TODO*///		new MemoryReadAddress( 0x080000, 0x0bffff, MRA_ROM					),
/*TODO*///		new MemoryReadAddress( _ram_start_, _ram_end_,	MRA_BANK1			),
/*TODO*///		new MemoryReadAddress( 0x044000, 0x044fff, MRA_BANK2					),
/*TODO*///		new MemoryReadAddress( 0x048000, 0x0487ff, paletteram_word_r			),
/*TODO*///		new MemoryReadAddress( 0x04e000, 0x04ffff, MRA_BANK3					),
/*TODO*///		new MemoryReadAddress( 0x050000, 0x053fff, megasys1_scrollram_0_r	),
/*TODO*///		new MemoryReadAddress( 0x054000, 0x057fff, megasys1_scrollram_1_r	),
/*TODO*///		new MemoryReadAddress( 0x058000, 0x05bfff, megasys1_scrollram_2_r	),
/*TODO*///		new MemoryReadAddress( 0x0e0000, 0x0e0001, ip_select_r				),
/*TODO*///		new MemoryReadAddress( -1 )
/*TODO*///	};
/*TODO*///	static MemoryWriteAddress writemem_##_shortname_[] =
/*TODO*///	{
/*TODO*///	 	new MemoryWriteAddress( 0x000000, 0x03ffff, MWA_ROM											),
/*TODO*///		new MemoryWriteAddress( 0x080000, 0x0bffff, MWA_ROM											),
/*TODO*///		new MemoryWriteAddress( _ram_start_, _ram_end_, MWA_BANK1,			megasys1_ram			),
/*TODO*///		new MemoryWriteAddress( 0x044000, 0x0443ff, megasys1_vregs_A_w,		megasys1_vregs			),
/*TODO*///		new MemoryWriteAddress( 0x048000, 0x0487ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	),
/*TODO*///		new MemoryWriteAddress( 0x04e000, 0x04ffff, MWA_BANK3,				megasys1_objectram		),
/*TODO*///		new MemoryWriteAddress( 0x050000, 0x053fff, megasys1_scrollram_0_w ,	megasys1_scrollram_0	),
/*TODO*///		new MemoryWriteAddress( 0x054000, 0x057fff, megasys1_scrollram_1_w ,	megasys1_scrollram_1	),
/*TODO*///		new MemoryWriteAddress( 0x058000, 0x05bfff, megasys1_scrollram_2_w ,	megasys1_scrollram_2	),
/*TODO*///		new MemoryWriteAddress( 0x0e0000, 0x0e0001, ip_select_w										),
/*TODO*///		new MemoryWriteAddress( -1 )
/*TODO*///	};
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///								[ Main CPU - System C ]
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	
/*TODO*///	#define INTERRUPT_NUM_C	INTERRUPT_NUM_B
/*TODO*///	#define interrupt_C		interrupt_B
/*TODO*///	
/*TODO*///	#define MEMORYMAP_C(_shortname_,_ram_start_,_ram_end_) 
/*TODO*///	static MemoryReadAddress readmem_##_shortname_[] =
/*TODO*///	{
/*TODO*///		new MemoryReadAddress( 0x000000, 0x07ffff, MRA_ROM					), 
/*TODO*///		new MemoryReadAddress( _ram_start_, _ram_end_, MRA_BANK1				), 
/*TODO*///		new MemoryReadAddress( 0x0c0000, 0x0cffff, megasys1_vregs_C_r		), 
/*TODO*///		new MemoryReadAddress( 0x0d2000, 0x0d3fff, MRA_BANK3					), 
/*TODO*///		new MemoryReadAddress( 0x0e0000, 0x0e3fff, megasys1_scrollram_0_r	), 
/*TODO*///		new MemoryReadAddress( 0x0e8000, 0x0ebfff, megasys1_scrollram_1_r	), 
/*TODO*///		new MemoryReadAddress( 0x0f0000, 0x0f3fff, megasys1_scrollram_2_r	), 
/*TODO*///		new MemoryReadAddress( 0x0f8000, 0x0f87ff, paletteram_word_r			), 
/*TODO*///		new MemoryReadAddress( 0x0d8000, 0x0d8001, ip_select_r				), 
/*TODO*///		new MemoryReadAddress( -1 ) 
/*TODO*///	}; 
/*TODO*///	static MemoryWriteAddress writemem_##_shortname_[] =
/*TODO*///	{ 
/*TODO*///	 	new MemoryWriteAddress( 0x000000, 0x07ffff, MWA_ROM											), 
/*TODO*///		new MemoryWriteAddress( _ram_start_, _ram_end_, MWA_BANK1,			megasys1_ram			), 
/*TODO*///		new MemoryWriteAddress( 0x0c0000, 0x0cffff, megasys1_vregs_C_w,		megasys1_vregs			), 
/*TODO*///		new MemoryWriteAddress( 0x0d2000, 0x0d3fff, MWA_BANK3,				megasys1_objectram		), 
/*TODO*///		new MemoryWriteAddress( 0x0e0000, 0x0e3fff, megasys1_scrollram_0_w,	megasys1_scrollram_0	), 
/*TODO*///		new MemoryWriteAddress( 0x0e8000, 0x0ebfff, megasys1_scrollram_1_w,	megasys1_scrollram_1	), 
/*TODO*///		new MemoryWriteAddress( 0x0f0000, 0x0f3fff, megasys1_scrollram_2_w,	megasys1_scrollram_2	), 
/*TODO*///		new MemoryWriteAddress( 0x0f8000, 0x0f87ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
/*TODO*///		new MemoryWriteAddress( 0x0d8000, 0x0d8001, ip_select_w										), 
/*TODO*///		new MemoryWriteAddress( -1 ) 
/*TODO*///	};
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///								[ Main CPU - System D ]
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	#define INTERRUPT_NUM_D		1
/*TODO*///	int interrupt_D(void)
/*TODO*///	{
/*TODO*///		return 2;
/*TODO*///	}
/*TODO*///	
/*TODO*///	#define MEMORYMAP_D(_shortname_,_ram_start_,_ram_end_) 
/*TODO*///	static MemoryReadAddress readmem_##_shortname_[] =
/*TODO*///	{ 
/*TODO*///		new MemoryReadAddress( 0x000000, 0x03ffff, MRA_ROM						), 
/*TODO*///		new MemoryReadAddress( _ram_start_, _ram_end_, MRA_BANK1					), 
/*TODO*///		new MemoryReadAddress( 0x0c0000, 0x0c9fff, MRA_BANK2						), 
/*TODO*///		new MemoryReadAddress( 0x0ca000, 0x0cbfff, MRA_BANK3						), 
/*TODO*///		new MemoryReadAddress( 0x0d0000, 0x0d3fff, MRA_BANK4						), 
/*TODO*///		new MemoryReadAddress( 0x0d4000, 0x0d7fff, MRA_BANK5						), 
/*TODO*///		new MemoryReadAddress( 0x0d8000, 0x0d87ff, paletteram_word_r				), 
/*TODO*///		new MemoryReadAddress( 0x0db000, 0x0db7ff, paletteram_word_r				), 
/*TODO*///		new MemoryReadAddress( 0x0e0000, 0x0e0001, dsw_r							), 
/*TODO*///		new MemoryReadAddress( 0x0e8000, 0x0ebfff, MRA_BANK7						), 
/*TODO*///		new MemoryReadAddress( 0x0f0000, 0x0f0001, coins_r						), /* Coins + P1P2 Buttons */ 
/*TODO*///		new MemoryReadAddress( 0x0f8000, 0x0f8001, OKIM6295_status_0_r			), 
/*TODO*///		new MemoryReadAddress( 0x100000, 0x100001, protection_##_shortname_##_r	), /* Protection */ 
/*TODO*///		new MemoryReadAddress( -1 ) 
/*TODO*///	}; 
/*TODO*///	static MemoryWriteAddress writemem_##_shortname_[] =
/*TODO*///	{ 
/*TODO*///	 	new MemoryWriteAddress( 0x000000, 0x03ffff, MWA_ROM											), 
/*TODO*///		new MemoryWriteAddress( _ram_start_, _ram_end_, MWA_BANK1,			megasys1_ram			), 
/*TODO*///		new MemoryWriteAddress( 0x0c0000, 0x0c9fff, megasys1_vregs_D_w,		megasys1_vregs			), 
/*TODO*///		new MemoryWriteAddress( 0x0ca000, 0x0cbfff, MWA_BANK3, 				megasys1_objectram		), 
/*TODO*///		new MemoryWriteAddress( 0x0d0000, 0x0d3fff, megasys1_scrollram_1_w,	megasys1_scrollram_1	), 
/*TODO*///		new MemoryWriteAddress( 0x0d4000, 0x0d7fff, megasys1_scrollram_2_w,	megasys1_scrollram_2	), 
/*TODO*///		new MemoryWriteAddress( 0x0d8000, 0x0d87ff, paletteram_RRRRRGGGGGBBBBBx_word_w				), 
/*TODO*///		new MemoryWriteAddress( 0x0db000, 0x0db7ff, paletteram_RRRRRGGGGGBBBBBx_word_w, paletteram	), 
/*TODO*///		new MemoryWriteAddress( 0x0e8000, 0x0ebfff, megasys1_scrollram_0_w,	megasys1_scrollram_0	), 
/*TODO*///		new MemoryWriteAddress( 0x0f8000, 0x0f8001, ms_OKIM6295_data_0_w								), 
/*TODO*///		new MemoryWriteAddress( 0x100000, 0x100001, protection_##_shortname_##_w						), /* Protection */ 
/*TODO*///		new MemoryWriteAddress( -1 ) 
/*TODO*///	};
	
	
	
	
	/*
	**
	**	Sound cpu data
	**
	**
	*/
	
	/*
								[ Sound CPU interrupts ]
	
		[MS1-A]
			astyanax				all rte
			hachoo					all reset the program, but the status
									register is set to 2700
			iganinju				all rte
			p47 & p47j				all rte
			phantasm				all rte (4 is different, but rte)
			plusalph				all rte
			rodland	& rodlandj		all rte (4 is different, but rte)
			stdragon				4]read & store sound command and echo to main cpu
									rest: rte
		[MS1-B]
			avspirit				all rte (4 is different, but rte)
			edf						all rte (4 is different, but rte)
	
		[MS1-C]
			64street				all rte (4 is different, but rte)
			chimerab				all rte
			cybattlr
				1;3;5-7]400	busy loop
				2]40c	read & store sound command and echo to main cpu
				4]446	rte
	
	
	 These games almost always don't use the interrupts to drive the music
	 tempo (cybattlr and stdragon do!) but use the YM2151 timers instead
	 (they poll the status register). Since those timers are affected by
	 the YM2151 clock, it's this latter that ultimately decides the music
	 tempo.
	
	 Note that some games' music is severely slowed down and out of sync
	 (avspirit, 64street) by the fact that the game waits for some samples
	 to be played entirely (M6295 status register polled) but they take
	 too much time (and raising the M6295 clock rate would, on the other
	 hand, screw the pitch of the samples)
	
	 A temporary fix is to make the status of this chip return 0...
	 unfortunately, this trick makes most of the effects disappear in at
	 least one game: hachoo!
	
	*/
	
	/* See stdragon, which is interrupt driven */
	static int SOUND_INTERRUPT_NUM		= 8;
	public static InterruptPtr sound_interrupt = new InterruptPtr() { public int handler() 
	{
		return 4;
	} };
	
	
	public static WriteHandlerPtr ms_YM2151_register_port_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0x00ff0000) == 0)	YM2151_register_port_0_w.handler(0,data & 0xff);
		else							logerror("%06x: write %02x to YM2151_register_port_0_wn",cpu_get_pc(),data);
	} };
	
	public static WriteHandlerPtr ms_YM2151_data_port_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0x00ff0000) == 0)	YM2151_data_port_0_w.handler(0,data & 0xff);
		else							logerror("%06x: write %02x to YM2151_data_port_0_wn",cpu_get_pc(),data);
	} };
	
	public static WriteHandlerPtr ms_OKIM6295_data_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0x00ff0000) == 0)	OKIM6295_data_0_w.handler(0,data & 0xff);
		else							logerror("%06x: write %02x to OKIM6295_data_0_wn",cpu_get_pc(),data);
	} };
	
	public static WriteHandlerPtr ms_OKIM6295_data_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0x00ff0000) == 0)	OKIM6295_data_1_w.handler(0,data & 0xff);
		else							logerror("%06x: write %02x to OKIM6295_data_1_wn",cpu_get_pc(),data);
	} };
	
	
	
	public static WriteHandlerPtr ms_soundlatch_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		UBytePtr val=new UBytePtr(2);
		COMBINE_WORD_MEM(val,0, data);
		soundlatch_w.handler(0, val.READ_WORD() );
	} };
        
        static UBytePtr val = new UBytePtr(2);
	
	public static WriteHandlerPtr ms_soundlatch2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		COMBINE_WORD_MEM(val,0,data);
		soundlatch2_w.handler(0, val.READ_WORD() );
	} };
	
	/***************************************************************************
								[ Sound CPU - System A ]
	***************************************************************************/
	
	
	static MemoryReadAddress sound_readmem_A[] =
	{
		new MemoryReadAddress( 0x000000, 0x01ffff, MRA_ROM					),
		new MemoryReadAddress( 0x040000, 0x040001, soundlatch_r				),
		new MemoryReadAddress( 0x080002, 0x080003, YM2151_status_port_0_r	),
/*TODO*///	#if SOUND_HACK
/*TODO*///		new MemoryReadAddress( 0x0a0000, 0x0a0001, MRA_NOP					),
/*TODO*///		new MemoryReadAddress( 0x0c0000, 0x0c0001, MRA_NOP					),
/*TODO*///	#else
		new MemoryReadAddress( 0x0a0000, 0x0a0001, OKIM6295_status_0_r		),
		new MemoryReadAddress( 0x0c0000, 0x0c0001, OKIM6295_status_1_r		),
/*TODO*///	#endif
		new MemoryReadAddress( 0x0e0000, 0x0fffff, MRA_BANK8					),
		new MemoryReadAddress( -1 )
	};
	
	static MemoryWriteAddress sound_writemem_A[] =
	{
		new MemoryWriteAddress( 0x000000, 0x01ffff, MWA_ROM						),
		new MemoryWriteAddress( 0x060000, 0x060001, ms_soundlatch2_w				),	// to main cpu
		new MemoryWriteAddress( 0x080000, 0x080001, ms_YM2151_register_port_0_w	),
		new MemoryWriteAddress( 0x080002, 0x080003, ms_YM2151_data_port_0_w		),
		new MemoryWriteAddress( 0x0a0000, 0x0a0003, ms_OKIM6295_data_0_w			),
		new MemoryWriteAddress( 0x0c0000, 0x0c0003, ms_OKIM6295_data_1_w			),
		new MemoryWriteAddress( 0x0e0000, 0x0fffff, MWA_BANK8						),
		new MemoryWriteAddress( -1 )
	};
	
	
	
	
	
	
	/***************************************************************************
							[ Sound CPU - System B / C ]
	***************************************************************************/
	
	
	static MemoryReadAddress sound_readmem_B[] =
	{
		new MemoryReadAddress( 0x000000, 0x01ffff, MRA_ROM					),
		new MemoryReadAddress( 0x040000, 0x040001, soundlatch_r				),	/* from main cpu */
		new MemoryReadAddress( 0x060000, 0x060001, soundlatch_r				),	/* from main cpu */
		new MemoryReadAddress( 0x080002, 0x080003, YM2151_status_port_0_r	),
/*TODO*///	#if SOUND_HACK
/*TODO*///		new MemoryReadAddress( 0x0a0000, 0x0a0001, MRA_NOP					),
/*TODO*///		new MemoryReadAddress( 0x0c0000, 0x0c0001, MRA_NOP					),
/*TODO*///	#else
		new MemoryReadAddress( 0x0a0000, 0x0a0001, OKIM6295_status_0_r		),
		new MemoryReadAddress( 0x0c0000, 0x0c0001, OKIM6295_status_1_r		),
/*TODO*///	#endif
		new MemoryReadAddress( 0x0e0000, 0x0effff, MRA_BANK8					),
		new MemoryReadAddress( -1 )
	};
	
	static MemoryWriteAddress sound_writemem_B[] =
	{
		new MemoryWriteAddress( 0x000000, 0x01ffff, MWA_ROM						),
		new MemoryWriteAddress( 0x040000, 0x040001, ms_soundlatch2_w				),	/* to main cpu */
		new MemoryWriteAddress( 0x060000, 0x060001, ms_soundlatch2_w				),	/* to main cpu */
		new MemoryWriteAddress( 0x080000, 0x080001, ms_YM2151_register_port_0_w	),
		new MemoryWriteAddress( 0x080002, 0x080003, ms_YM2151_data_port_0_w		),
		new MemoryWriteAddress( 0x0a0000, 0x0a0003, ms_OKIM6295_data_0_w			),
		new MemoryWriteAddress( 0x0c0000, 0x0c0003, ms_OKIM6295_data_1_w			),
		new MemoryWriteAddress( 0x0e0000, 0x0effff, MWA_BANK8						),
		new MemoryWriteAddress( -1 )
	};
	
/*TODO*///	#define sound_readmem_C		sound_readmem_B
/*TODO*///	#define sound_writemem_C	sound_writemem_B
/*TODO*///	
/*TODO*///	
	
	
	
	
	/***************************************************************************
							[ Sound CPU - System Z ]
	***************************************************************************/
	
	
	
	static MemoryReadAddress sound_readmem_z80[] =
	{
		new MemoryReadAddress( 0x0000, 0x3fff, MRA_ROM		),
		new MemoryReadAddress( 0xc000, 0xc7ff, MRA_RAM		),
		new MemoryReadAddress( 0xe000, 0xe000, soundlatch_r	),
		new MemoryReadAddress( -1 )
	};
	
	static MemoryWriteAddress sound_writemem_z80[] =
	{
		new MemoryWriteAddress( 0x0000, 0x3fff, MWA_ROM	),
		new MemoryWriteAddress( 0xc000, 0xc7ff, MWA_RAM	),
		new MemoryWriteAddress( 0xf000, 0xf000, MWA_NOP	), /* ?? */
		new MemoryWriteAddress( -1 )
	};
	
	
	static IOReadPort sound_readport[] =
	{
		new IOReadPort( 0x00, 0x00, YM2203_status_port_0_r	),
		new IOReadPort( -1 )
	};
	
	static IOWritePort sound_writeport[] =
	{
		new IOWritePort( 0x00, 0x00, YM2203_control_port_0_w	),
		new IOWritePort( 0x01, 0x01, YM2203_write_port_0_w		),
		new IOWritePort( -1 )
	};
	
	
	
	
	
	
	
	/*
	**
	**	Gfx data
	**
	*/
	
	/* Tiles are 8x8 */
        //MEGASYS1_LAYOUT_8x8( tilelayout_01, 0x010000 )
        static GfxLayout tilelayout_01 = new GfxLayout
        (
                8,8,
                (0x010000)*8/(8*8*4),
                4,
                new int[] {0, 1, 2, 3},
                new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4},
                new int[] {0*32,1*32,2*32,3*32,4*32,5*32,6*32,7*32},
                8*8*4
        );
        //MEGASYS1_LAYOUT_8x8( tilelayout_02, 0x020000 )
        static GfxLayout tilelayout_02 = new GfxLayout
        (
                8,8,
                (0x020000)*8/(8*8*4),
                4,
                new int[] {0, 1, 2, 3},
                new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4},
                new int[] {0*32,1*32,2*32,3*32,4*32,5*32,6*32,7*32},
                8*8*4
        );
        //MEGASYS1_LAYOUT_8x8( tilelayout_08, 0x080000 )
        static GfxLayout tilelayout_08 = new GfxLayout
        (
                8,8,
                (0x080000)*8/(8*8*4),
                4,
                new int[] {0, 1, 2, 3},
                new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4},
                new int[] {0*32,1*32,2*32,3*32,4*32,5*32,6*32,7*32},
                8*8*4
        );

        /* Sprites are 16x16 - formed by four 8x8 tiles */
        //MEGASYS1_LAYOUT_16x16_QUAD( spritelayout_02, 0x020000 )	// MS1 Z
        static GfxLayout spritelayout_02 = new GfxLayout
        (
                16,16,
                (0x020000)*8/(16*16*4),
                4,
                new int[] {0, 1, 2, 3},
                new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4,
                 0*4+32*16,1*4+32*16,2*4+32*16,3*4+32*16,4*4+32*16,5*4+32*16,6*4+32*16,7*4+32*16},
                new int[] {0*32,1*32,2*32,3*32,4*32,5*32,6*32,7*32,
                 8*32,9*32,10*32,11*32,12*32,13*32,14*32,15*32},
                16*16*4
        );
        //MEGASYS1_LAYOUT_16x16_QUAD( spritelayout_08, 0x080000 )	// MS1 A & B
        static GfxLayout spritelayout_08 = new GfxLayout
        (
                16,16,
                (0x080000)*8/(16*16*4),
                4,
                new int[] {0, 1, 2, 3},
                new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4,
                 0*4+32*16,1*4+32*16,2*4+32*16,3*4+32*16,4*4+32*16,5*4+32*16,6*4+32*16,7*4+32*16},
                new int[] {0*32,1*32,2*32,3*32,4*32,5*32,6*32,7*32,
                 8*32,9*32,10*32,11*32,12*32,13*32,14*32,15*32},
                16*16*4
        );
        //MEGASYS1_LAYOUT_16x16_QUAD( spritelayout_10, 0x100000 )	// MS1 C
        static GfxLayout _nspritelayout_10ame_ = new GfxLayout
        (
                16,16,
                (0x100000)*8/(16*16*4),
                4,
                new int[] {0, 1, 2, 3},
                new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4,
                 0*4+32*16,1*4+32*16,2*4+32*16,3*4+32*16,4*4+32*16,5*4+32*16,6*4+32*16,7*4+32*16},
                new int[] {0*32,1*32,2*32,3*32,4*32,5*32,6*32,7*32,
                 8*32,9*32,10*32,11*32,12*32,13*32,14*32,15*32},
                16*16*4
        );
	
	
	static GfxDecodeInfo gfxdecodeinfo_Z[] =
	{
		new GfxDecodeInfo( REGION_GFX1, 0, tilelayout_02,	256*0, 16 ),	// [0] Scroll 0
		new GfxDecodeInfo( REGION_GFX2, 0, tilelayout_01,	256*2, 16 ),	// [1] Scroll 1
		new GfxDecodeInfo( REGION_GFX3, 0, spritelayout_02,	256*1, 16 ),	// [2] Sprites
		new GfxDecodeInfo( -1 )
	};
	
	static GfxDecodeInfo gfxdecodeinfo_A[] =
	{
		new GfxDecodeInfo( REGION_GFX1, 0, tilelayout_08,	256*0, 16 ),	// [0] Scroll 0
		new GfxDecodeInfo( REGION_GFX2, 0, tilelayout_08,	256*1, 16 ),	// [1] Scroll 1
		new GfxDecodeInfo( REGION_GFX3, 0, tilelayout_02,	256*2, 16 ),	// [2] Scroll 2
		new GfxDecodeInfo( REGION_GFX4, 0, spritelayout_08,	256*3, 16 ),	// [3] Sprites
		new GfxDecodeInfo( -1 )
	};
        
        static GfxDecodeInfo gfxdecodeinfo_B[] =
	{
		new GfxDecodeInfo( REGION_GFX1, 0, tilelayout_08,	256*0, 16 ),	// [0] Scroll 0
		new GfxDecodeInfo( REGION_GFX2, 0, tilelayout_08,	256*1, 16 ),	// [1] Scroll 1
		new GfxDecodeInfo( REGION_GFX3, 0, tilelayout_02,	256*2, 16 ),	// [2] Scroll 2
		new GfxDecodeInfo( REGION_GFX4, 0, spritelayout_08,	256*3, 16 ),	// [3] Sprites
		new GfxDecodeInfo( -1 )
	};
	
/*TODO*///	#define gfxdecodeinfo_B		gfxdecodeinfo_A
/*TODO*///	
/*TODO*///	static GfxDecodeInfo gfxdecodeinfo_C[] =
/*TODO*///	{
/*TODO*///		new GfxDecodeInfo( REGION_GFX1, 0, tilelayout_08,	256*0, 16 ),	// [0] Scroll 0
/*TODO*///		new GfxDecodeInfo( REGION_GFX2, 0, tilelayout_08,	256*1, 16 ),	// [1] Scroll 1
/*TODO*///		new GfxDecodeInfo( REGION_GFX3, 0, tilelayout_02,	256*2, 16 ),	// [2] Scroll 2
/*TODO*///		new GfxDecodeInfo( REGION_GFX4, 0, spritelayout_10,	256*3, 16 ),	// [3] Sprites
/*TODO*///		new GfxDecodeInfo( -1 )
/*TODO*///	};
/*TODO*///	
/*TODO*///	static GfxDecodeInfo gfxdecodeinfo_D[] =
/*TODO*///	{
/*TODO*///		new GfxDecodeInfo( REGION_GFX1, 0, tilelayout_08,	256*0, 16 ),	// [0] scroll 0
/*TODO*///		new GfxDecodeInfo( REGION_GFX2, 0, tilelayout_08,	256*1, 16 ),	// [1] scroll 1
/*TODO*///		new GfxDecodeInfo( REGION_GFX3, 0, tilelayout_01,	256*1, 16 ),	// [2] UNUSED
/*TODO*///		new GfxDecodeInfo( REGION_GFX4, 0, spritelayout_08,	256*3, 16 ),	// [3] sprites
/*TODO*///		new GfxDecodeInfo( -1 )
/*TODO*///	};
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								Machine Driver Macros
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///							[  Mega System 1 A,B and C ]
/*TODO*///	
/*TODO*///							  2x68000 2xM6295 1xYM2151
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	#define MEGASYS1_MACHINE(	_type_, 
/*TODO*///								_shortname_,_main_clock_,_sound_clock_, 
/*TODO*///								_fm_clock_,_oki1_clock_,_oki2_clock_) 
/*TODO*///	static YM2151interface ym2151_interface_##_shortname_ = new YM2151interface
/*TODO*///	( 
/*TODO*///		1, 
/*TODO*///		_fm_clock_, 
/*TODO*///		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
/*TODO*///		new WriteYmHandlerPtr[] { 0 } 
/*TODO*///	); 
/*TODO*///	 
/*TODO*///	static OKIM6295interface okim6295_interface_##_shortname_ = new OKIM6295interface
/*TODO*///	( 
/*TODO*///		2, 
/*TODO*///		new int[] {_oki1_clock_, _oki2_clock_},
/*TODO*///		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
/*TODO*///		new int[] { 30, 30 } 
/*TODO*///	); 
/*TODO*///	 
/*TODO*///	static MachineDriver machine_driver_##_shortname_ = new MachineDriver
/*TODO*///	( 
/*TODO*///		new MachineCPU[] { 
/*TODO*///			new MachineCPU( 
/*TODO*///				CPU_M68000, 
/*TODO*///				_main_clock_, 
/*TODO*///				readmem_##_shortname_,writemem_##_shortname_,null,null, 
/*TODO*///				interrupt_##_type_,INTERRUPT_NUM_##_type_ 
/*TODO*///			), 
/*TODO*///			new MachineCPU( 
/*TODO*///				CPU_M68000 | CPU_AUDIO_CPU, 
/*TODO*///				_sound_clock_, 
/*TODO*///				sound_readmem_##_type_,sound_writemem_##_type_,null,null, 
/*TODO*///				sound_interrupt,SOUND_INTERRUPT_NUM, 
/*TODO*///			), 
/*TODO*///		}, 
/*TODO*///		60, DEFAULT_60HZ_VBLANK_DURATION, 
/*TODO*///		1, 
/*TODO*///		megasys1_init_machine, 
/*TODO*///		/* video hardware */ 
/*TODO*///		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
/*TODO*///		gfxdecodeinfo_##_type_, 
/*TODO*///		1024, 1024, 
/*TODO*///		megasys1_convert_prom, 
/*TODO*///		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
/*TODO*///		null, 
/*TODO*///		megasys1_vh_start, 
/*TODO*///		null, 
/*TODO*///		megasys1_vh_screenrefresh, 
/*TODO*///		/* sound hardware */ 
/*TODO*///		0,0,0,0, 
/*TODO*///		new MachineSound[] { 
/*TODO*///			new MachineSound( 
/*TODO*///				SOUND_YM2151, 
/*TODO*///				ym2151_interface_##_shortname_ 
/*TODO*///			),
/*TODO*///			new MachineSound(
/*TODO*///				SOUND_OKIM6295, 
/*TODO*///				okim6295_interface_##_shortname_ 
/*TODO*///			) 
/*TODO*///		} 
/*TODO*///	);
/*TODO*///	
/*TODO*///	#define MEGASYS1_MACHINE_A	MEGASYS1_MACHINE
/*TODO*///	#define MEGASYS1_MACHINE_B	MEGASYS1_MACHINE
/*TODO*///	#define MEGASYS1_MACHINE_C	MEGASYS1_MACHINE
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								[ Mega System 1 D ]
/*TODO*///	
/*TODO*///								  1x68000 1xM6295
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	
/*TODO*///	#define MEGASYS1_MACHINE_D(	_type_, 
/*TODO*///								_shortname_,_main_clock_,_sound_clock_, 
/*TODO*///								_fm_clock_,_oki1_clock_,_oki2_clock_) 
/*TODO*///	static OKIM6295interface okim6295_interface_##_shortname_ = new OKIM6295interface
/*TODO*///	( 
/*TODO*///		1, 
/*TODO*///		new int[] {_oki1_clock_},
/*TODO*///		new int[] { REGION_SOUND1 }, 
/*TODO*///		new int[] { 100 } 
/*TODO*///	); 
/*TODO*///	 
/*TODO*///	static MachineDriver machine_driver_##_shortname_ = new MachineDriver
/*TODO*///	( 
/*TODO*///		new MachineCPU[] {
/*TODO*///			new MachineCPU(
/*TODO*///				CPU_M68000,
/*TODO*///				_main_clock_, 
/*TODO*///				readmem_##_shortname_,writemem_##_shortname_,null,null, 
/*TODO*///				interrupt_##_type_,INTERRUPT_NUM_##_type_ 
/*TODO*///			) 
/*TODO*///		},
/*TODO*///		60,DEFAULT_60HZ_VBLANK_DURATION,
/*TODO*///		1,
/*TODO*///		megasys1_init_machine,
/*TODO*///		/* video hardware */ 
/*TODO*///		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ),
/*TODO*///		gfxdecodeinfo_##_type_,
/*TODO*///		1024, 1024,
/*TODO*///		megasys1_convert_prom,
/*TODO*///		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE,
/*TODO*///		null,
/*TODO*///		megasys1_vh_start,
/*TODO*///		null,
/*TODO*///		megasys1_vh_screenrefresh,
/*TODO*///		/* sound hardware */ 
/*TODO*///		0,0,0,0,
/*TODO*///		new MachineSound[] { 
/*TODO*///			new MachineSound(
/*TODO*///				SOUND_OKIM6295, 
/*TODO*///				okim6295_interface_##_shortname_ 
/*TODO*///			) 
/*TODO*///		}
/*TODO*///	);
/*TODO*///	
/*TODO*///	
	
	
	/***************************************************************************
	
								[  Mega System 1 Z ]
	
								 68000+Z80 1xYM2203
	
	***************************************************************************/
	
	
	static WriteYmHandlerPtr irq_handler = new WriteYmHandlerPtr() {
            @Override
            public void handler(int irq) {
                cpu_set_irq_line(1,0,irq!=0 ? ASSERT_LINE : CLEAR_LINE);
            }
        };
        
/*TODO*///	
/*TODO*///	#define MEGASYS1_MACHINE_Z(	_type_, 
/*TODO*///								_shortname_,_main_clock_,_sound_clock_, 
/*TODO*///								_fm_clock_,_oki1_clock_,_oki2_clock_) 
/*TODO*///	static YM2203interface ym2203_interface_##_shortname_ = new YM2203interface
/*TODO*///	( 
/*TODO*///		1, 
/*TODO*///		_fm_clock_, 
/*TODO*///		new int[] { YM2203_VOL(50,50) }, 
/*TODO*///		new ReadHandlerPtr[] { 0 }, 
/*TODO*///		new ReadHandlerPtr[] { 0 }, 
/*TODO*///		new WriteHandlerPtr[] { 0	}, 
/*TODO*///		new WriteHandlerPtr[] { 0 }, 
/*TODO*///		new WriteYmHandlerPtr[] { irq_handler } 
/*TODO*///	); 
/*TODO*///	
/*TODO*///	static MachineDriver machine_driver_##_shortname_ = new MachineDriver
/*TODO*///	(
/*TODO*///		new MachineCPU[] {
/*TODO*///			new MachineCPU(
/*TODO*///				CPU_M68000,
/*TODO*///				_main_clock_, 
/*TODO*///				readmem_##_shortname_,writemem_##_shortname_,null,null, 
/*TODO*///				interrupt_##_type_,INTERRUPT_NUM_##_type_ 
/*TODO*///			),
/*TODO*///			new MachineCPU(
/*TODO*///				CPU_Z80 | CPU_AUDIO_CPU,
/*TODO*///				_sound_clock_, 
/*TODO*///				sound_readmem_z80,sound_writemem_z80,sound_readport,sound_writeport,
/*TODO*///				ignore_interrupt,1	/* irq generated by YM2203 */	
/*TODO*///			),
/*TODO*///		},
/*TODO*///		60,DEFAULT_60HZ_VBLANK_DURATION,
/*TODO*///		1,
/*TODO*///		null,
/*TODO*///		/* video hardware */ 
/*TODO*///		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ),
/*TODO*///		gfxdecodeinfo_Z,
/*TODO*///		256*3, 256*3,
/*TODO*///		null	/*megasys1_convert_prom*/,
/*TODO*///		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE,
/*TODO*///		null,
/*TODO*///		megasys1_vh_start,
/*TODO*///		null,
/*TODO*///		megasys1_vh_screenrefresh,
/*TODO*///		/* sound hardware */ 
/*TODO*///		0,0,0,0,
/*TODO*///		new MachineSound[] {
/*TODO*///			new MachineSound( 
/*TODO*///				SOUND_YM2203, 
/*TODO*///				ym2203_interface_##_shortname_ 
/*TODO*///			),
/*TODO*///		}
/*TODO*///	);
/*TODO*///	
/*TODO*///	
/*TODO*///	
	
	
	/***************************************************************************
	
								Game Driver Macros
	
	***************************************************************************/
	
	
	
	/* Provided by Jim Hernandez: 3.5MHz for FM, 30KHz (!) for ADPCM */
	static final int STD_FM_CLOCK	= 3500000;
	static final int STD_OKI_CLOCK	= 30000;
	
	
/*TODO*///	/*
/*TODO*///	
/*TODO*///	 This is a general purpose macro to define a game.
/*TODO*///	
/*TODO*///	 There is a different macro for clones (defined below), but if the
/*TODO*///	 parent and clone game run on different hardwares, you can use this
/*TODO*///	 macro for the clone (to describe its hardware) and specify the
/*TODO*///	 parent's driver as a parameter
/*TODO*///	
/*TODO*///	*/
/*TODO*///	#define MEGASYS1_GAME(_shortname_, 
/*TODO*///						  _type_,_ram_start_, _ram_end_, 
/*TODO*///						  _main_clock_,_sound_clock_, 
/*TODO*///						  _fm_clock_,_oki1_clock_,_oki2_clock_) 
/*TODO*///	 
/*TODO*///		MEMORYMAP_##_type_(_shortname_,_ram_start_,_ram_end_) 
/*TODO*///	 
/*TODO*///		MEGASYS1_MACHINE_##_type_(	_type_, 
/*TODO*///									_shortname_,_main_clock_,_sound_clock_, 
/*TODO*///									_fm_clock_,_oki1_clock_,_oki2_clock_)
/*TODO*///	
/*TODO*///	
/*TODO*///	/* OSC:	? (Main 12, Sound 10 MHz according to KLOV) */
/*TODO*///	MEGASYS1_GAME(	64street, C,0xff0000,0xffffff,
/*TODO*///					12000000,8000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
/*TODO*///	/* OSC:	? */
/*TODO*///	MEGASYS1_GAME(	astyanax, A,0xff0000,0xffffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	#define MEGASYS1_MACHINE(	_type_, 
/*TODO*///								_shortname_,_main_clock_,_sound_clock_, 
/*TODO*///								_fm_clock_,_oki1_clock_,_oki2_clock_) 
	static YM2151interface ym2151_interface_astyanax = new YM2151interface
	( 
		1, 
		STD_FM_CLOCK, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	 
	static OKIM6295interface okim6295_interface_astyanax = new OKIM6295interface
	( 
		2, 
		new int[] {STD_OKI_CLOCK, STD_OKI_CLOCK},
		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
		new int[] { 30, 30 } 
	); 
        
        /*TODO*///	#define MEMORYMAP_A(_shortname_,_ram_start_,_ram_end_) 
	static MemoryReadAddress readmem_astyanax[] =
	{ 
		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
		new MemoryReadAddress( 0xff0000, 0xffffff, MRA_BANK1				), 
		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
		new MemoryReadAddress( -1 ) 
	}; 
	static MemoryWriteAddress writemem_astyanax[] =
	{ 
	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
		new MemoryWriteAddress( 0xff0000, 0xffffff, MWA_BANK1,			megasys1_ram			), 
		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0/*,0*/	), 
		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1/*,0*/	), 
		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2/*,0*/	), 
		new MemoryWriteAddress( -1 ) 
	};
	 
	static MachineDriver machine_driver_astyanax = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				12000000, 
				readmem_astyanax,writemem_astyanax,null,null, 
				interrupt_A,INTERRUPT_NUM_A 
			), 
			new MachineCPU( 
				CPU_M68000 | CPU_AUDIO_CPU, 
				7000000, 
				sound_readmem_A,sound_writemem_A,null,null, 
				sound_interrupt,SOUND_INTERRUPT_NUM
			), 
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_A, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
		new MachineSound[] { 
			new MachineSound( 
				SOUND_YM2151, 
				ym2151_interface_astyanax 
			),
			new MachineSound(
				SOUND_OKIM6295, 
				okim6295_interface_astyanax 
			) 
		} 
	);
        
/*TODO*///	
/*TODO*///	/* OSC:	8,12,7 MHz */
/*TODO*///	MEGASYS1_GAME(	avspirit, B,0x070000,0x07ffff,
/*TODO*///					12000000,8000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
	static YM2151interface ym2151_interface_avspirit = new YM2151interface
	( 
		1, 
		STD_FM_CLOCK, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	 
	static OKIM6295interface okim6295_interface_avspirit = new OKIM6295interface
	( 
		2, 
		new int[] {STD_OKI_CLOCK, STD_OKI_CLOCK},
		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
		new int[] { 30, 30 } 
	); 

	static MemoryReadAddress readmem_avspirit[] =
	{
		new MemoryReadAddress( 0x000000, 0x03ffff, MRA_ROM					),
		new MemoryReadAddress( 0x080000, 0x0bffff, MRA_ROM					),
		new MemoryReadAddress( 0x070000, 0x07ffff,	MRA_BANK1			),
		new MemoryReadAddress( 0x044000, 0x044fff, MRA_BANK2					),
		new MemoryReadAddress( 0x048000, 0x0487ff, paletteram_word_r			),
		new MemoryReadAddress( 0x04e000, 0x04ffff, MRA_BANK3					),
		new MemoryReadAddress( 0x050000, 0x053fff, megasys1_scrollram_0_r	),
		new MemoryReadAddress( 0x054000, 0x057fff, megasys1_scrollram_1_r	),
		new MemoryReadAddress( 0x058000, 0x05bfff, megasys1_scrollram_2_r	),
		new MemoryReadAddress( 0x0e0000, 0x0e0001, ip_select_r				),
		new MemoryReadAddress( -1 )
	};
	static MemoryWriteAddress writemem_avspirit[] =
	{
	 	new MemoryWriteAddress( 0x000000, 0x03ffff, MWA_ROM											),
		new MemoryWriteAddress( 0x080000, 0x0bffff, MWA_ROM											),
		new MemoryWriteAddress( 0x070000, 0x07ffff, MWA_BANK1,			megasys1_ram			),
		new MemoryWriteAddress( 0x044000, 0x0443ff, megasys1_vregs_A_w,		megasys1_vregs			),
		new MemoryWriteAddress( 0x048000, 0x0487ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	),
		new MemoryWriteAddress( 0x04e000, 0x04ffff, MWA_BANK3,				megasys1_objectram		),
		new MemoryWriteAddress( 0x050000, 0x053fff, megasys1_scrollram_0_w ,	megasys1_scrollram_0	),
		new MemoryWriteAddress( 0x054000, 0x057fff, megasys1_scrollram_1_w ,	megasys1_scrollram_1	),
		new MemoryWriteAddress( 0x058000, 0x05bfff, megasys1_scrollram_2_w ,	megasys1_scrollram_2	),
		new MemoryWriteAddress( 0x0e0000, 0x0e0001, ip_select_w										),
		new MemoryWriteAddress( -1 )
	};        
	 
	static MachineDriver machine_driver_avspirit = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				12000000, 
				readmem_avspirit,writemem_avspirit,null,null, 
				interrupt_B,INTERRUPT_NUM_B 
			), 
			new MachineCPU( 
				CPU_M68000 | CPU_AUDIO_CPU, 
				8000000, 
				sound_readmem_B,sound_writemem_B,null,null, 
				sound_interrupt,SOUND_INTERRUPT_NUM
			), 
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_B, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
		new MachineSound[] { 
			new MachineSound( 
				SOUND_YM2151, 
				ym2151_interface_avspirit 
			),
			new MachineSound(
				SOUND_OKIM6295, 
				okim6295_interface_avspirit 
			) 
		} 
	);        
/*TODO*///	
/*TODO*///	/* This is a clone of Avenging Spirit but runs on different hardware */
/*TODO*///	/* OSC: same as avspirit(8,12,7 MHz)? (Music tempo driven by timers of the 2151) */
/*TODO*///	MEGASYS1_GAME(	phantasm, A,0xff0000,0xffffff,
/*TODO*///					12000000,8000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
	
	static YM2151interface ym2151_interface_phantasm = new YM2151interface
	( 
		1, 
		STD_FM_CLOCK, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	 
	static OKIM6295interface okim6295_interface_phantasm = new OKIM6295interface
	( 
		2, 
		new int[] {STD_OKI_CLOCK, STD_OKI_CLOCK},
		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
		new int[] { 30, 30 } 
	); 
        
	static MemoryReadAddress readmem_phantasm[] =
	{ 
		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
		new MemoryReadAddress( 0xff0000, 0xffffff, MRA_BANK1				), 
		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
		new MemoryReadAddress( -1 ) 
	}; 
	static MemoryWriteAddress writemem_phantasm[] =
	{ 
	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
		new MemoryWriteAddress( 0xff0000, 0xffffff, MWA_BANK1,			megasys1_ram			), 
		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0/*,0*/	), 
		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1/*,0*/	), 
		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2/*,0*/	), 
		new MemoryWriteAddress( -1 ) 
	};        
	 
	static MachineDriver machine_driver_phantasm = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				12000000, 
				readmem_phantasm,writemem_phantasm,null,null, 
				interrupt_A,INTERRUPT_NUM_A 
			), 
			new MachineCPU( 
				CPU_M68000 | CPU_AUDIO_CPU, 
				8000000, 
				sound_readmem_A,sound_writemem_A,null,null, 
				sound_interrupt,SOUND_INTERRUPT_NUM
			), 
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_A, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
		new MachineSound[] { 
			new MachineSound( 
				SOUND_YM2151, 
				ym2151_interface_phantasm 
			),
			new MachineSound(
				SOUND_OKIM6295, 
				okim6295_interface_phantasm
			) 
		} 
	);        
/*TODO*///	/* OSC: 7.000 MHz + 24.000 MHz */
/*TODO*///	MEGASYS1_GAME(	bigstrik, C,0xff0000,0xffffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
/*TODO*///	/* OSC:	? */
/*TODO*///	MEGASYS1_GAME(	chimerab, C,0xff0000,0xffffff,
/*TODO*///					12000000,8000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
/*TODO*///	/* OSC:	? */
/*TODO*///	MEGASYS1_GAME(	cybattlr, C,0x1f0000,0x01fffff,
/*TODO*///					12000000,8000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
/*TODO*///	/* OSC:	8,12,7 MHz */
/*TODO*///	MEGASYS1_GAME(	edf, B,0x060000,0x07ffff,
/*TODO*///					12000000,8000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
/*TODO*///	/* OSC:	4,7,12 MHz */
/*TODO*///	MEGASYS1_GAME(	hachoo, A,0x0f0000,0x0fffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )

	static YM2151interface ym2151_interface_hachoo = new YM2151interface
	( 
		1, 
		STD_FM_CLOCK, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	 
	static OKIM6295interface okim6295_interface_hachoo = new OKIM6295interface
	( 
		2, 
		new int[] {STD_OKI_CLOCK, STD_OKI_CLOCK},
		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
		new int[] { 30, 30 } 
	); 
        
        static MemoryReadAddress readmem_hachoo[] =
	{ 
		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
		new MemoryReadAddress( 0x0f0000, 0x0fffff, MRA_BANK1				), 
		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
		new MemoryReadAddress( -1 ) 
	}; 
	static MemoryWriteAddress writemem_hachoo[] =
	{ 
	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
		new MemoryWriteAddress( 0x0f0000, 0x0fffff, MWA_BANK1,			megasys1_ram			), 
		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0/*,0*/	), 
		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1/*,0*/	), 
		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2/*,0*/	), 
		new MemoryWriteAddress( -1 ) 
	};
	 
	static MachineDriver machine_driver_hachoo = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				12000000, 
				readmem_hachoo,writemem_hachoo,null,null, 
				interrupt_A,INTERRUPT_NUM_A 
			), 
			new MachineCPU( 
				CPU_M68000 | CPU_AUDIO_CPU, 
				7000000, 
				sound_readmem_A,sound_writemem_A,null,null, 
				sound_interrupt,SOUND_INTERRUPT_NUM
			), 
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_A, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
		new MachineSound[] { 
			new MachineSound( 
				SOUND_YM2151, 
				ym2151_interface_hachoo 
			),
			new MachineSound(
				SOUND_OKIM6295, 
				okim6295_interface_hachoo 
			) 
		} 
	);
        
/*TODO*///	/* OSC:	? */
/*TODO*///	MEGASYS1_GAME(	iganinju, A,0x0f0000,0x0fffff,
/*TODO*///					7000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	// This game doesn't work properly: it severely slows down (and gets worse bumping the clock)
	static YM2151interface ym2151_interface_iganinju = new YM2151interface
	( 
		1, 
		STD_FM_CLOCK, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	 
	static OKIM6295interface okim6295_interface_iganinju = new OKIM6295interface
	( 
		2, 
		new int[] {STD_OKI_CLOCK, STD_OKI_CLOCK},
		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
		new int[] { 30, 30 } 
	); 
        
        static MemoryReadAddress readmem_iganinju[] =
	{ 
		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
		new MemoryReadAddress( 0x0f0000, 0x0fffff, MRA_BANK1				), 
		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
		new MemoryReadAddress( -1 ) 
	}; 
	static MemoryWriteAddress writemem_iganinju[] =
	{ 
	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
		new MemoryWriteAddress( 0x0f0000, 0x0fffff, MWA_BANK1,			megasys1_ram			), 
		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0/*,0*/	), 
		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1/*,0*/	), 
		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2/*,0*/	), 
		new MemoryWriteAddress( -1 ) 
	};
	 
	static MachineDriver machine_driver_iganinju = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				7000000, 
				readmem_iganinju,writemem_iganinju,null,null, 
				interrupt_A,INTERRUPT_NUM_A 
			), 
			new MachineCPU( 
				CPU_M68000 | CPU_AUDIO_CPU, 
				7000000, 
				sound_readmem_A,sound_writemem_A,null,null, 
				sound_interrupt,SOUND_INTERRUPT_NUM
			), 
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_A, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
		new MachineSound[] { 
			new MachineSound( 
				SOUND_YM2151, 
				ym2151_interface_iganinju 
			),
			new MachineSound(
				SOUND_OKIM6295, 
				okim6295_interface_iganinju
			) 
		} 
	);        

/*TODO*///	/* OSC:	4, 7, 12 MHz */
/*TODO*///	MEGASYS1_GAME(	kickoff, A,0x0f0000,0x0fffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
	
	static YM2151interface ym2151_interface_kickoff = new YM2151interface
	( 
		1, 
		STD_FM_CLOCK, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	 
	static OKIM6295interface okim6295_interface_kickoff = new OKIM6295interface
	( 
		2, 
		new int[] {STD_OKI_CLOCK, STD_OKI_CLOCK},
		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
		new int[] { 30, 30 } 
	);
        
        static MemoryReadAddress readmem_kickoff[] =
	{ 
		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
		new MemoryReadAddress( 0x0f0000, 0x0fffff, MRA_BANK1				), 
		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
		new MemoryReadAddress( -1 ) 
	}; 
	static MemoryWriteAddress writemem_kickoff[] =
	{ 
	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
		new MemoryWriteAddress( 0x0f0000, 0x0fffff, MWA_BANK1,			megasys1_ram			), 
		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0/*,0*/	), 
		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1/*,0*/	), 
		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2/*,0*/	), 
		new MemoryWriteAddress( -1 ) 
	};
	 
	static MachineDriver machine_driver_kickoff = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				12000000, 
				readmem_kickoff,writemem_kickoff,null,null, 
				interrupt_A,INTERRUPT_NUM_A 
			), 
			new MachineCPU( 
				CPU_M68000 | CPU_AUDIO_CPU, 
				7000000, 
				sound_readmem_A,sound_writemem_A,null,null, 
				sound_interrupt,SOUND_INTERRUPT_NUM
			), 
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_A, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
		new MachineSound[] { 
			new MachineSound( 
				SOUND_YM2151, 
				ym2151_interface_kickoff 
			),
			new MachineSound(
				SOUND_OKIM6295, 
				okim6295_interface_kickoff
			) 
		} 
	);
	/* OSC:	5,12 MHz */
	//MEGASYS1_GAME(	lomakai, Z,0x0f0000,0x0fffff,
	//				6000000,3000000,	1200000,0,0 )	// no oki chips
        //#define MEGASYS1_MACHINE(	_type_, 
	//							_shortname_,_main_clock_,_sound_clock_, 
	//							_fm_clock_,_oki1_clock_,_oki2_clock_) 
	static YM2151interface ym2151_interface_lomakai = new YM2151interface
	( 
		1, 
		1200000, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	
        //#define MEMORYMAP_A(_shortname_,_ram_start_,_ram_end_) 
	static MemoryReadAddress readmem_lomakai[] =
	{ 
		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
		new MemoryReadAddress( 0x0f0000, 0x0fffff, MRA_BANK1				), 
		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
		new MemoryReadAddress( -1 ) 
	}; 
	static MemoryWriteAddress writemem_lomakai[] =
	{ 
	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
		new MemoryWriteAddress( 0x0f0000, 0x0fffff, MWA_BANK1,			megasys1_ram			), 
		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0 /*,0*/	), 
		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1 /*,0*/	), 
		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2 /*,0*/	), 
		new MemoryWriteAddress( -1 ) 
	};
        
	static YM2203interface ym2203_interface_lomakai = new YM2203interface
	( 
		1, 
		1200000, 
		new int[] { YM2203_VOL(50,50) }, 
		new ReadHandlerPtr[] { null }, 
		new ReadHandlerPtr[] { null }, 
		new WriteHandlerPtr[] { null }, 
		new WriteHandlerPtr[] { null }, 
		new WriteYmHandlerPtr[] { irq_handler } 
	);
        	 
	static MachineDriver machine_driver_lomakai = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				6000000, 
				readmem_lomakai,writemem_lomakai,null,null, 
				interrupt_Z,INTERRUPT_NUM_Z 
			), 
			new MachineCPU(
				CPU_Z80 | CPU_AUDIO_CPU,
				3000000,
				sound_readmem_z80,sound_writemem_z80,sound_readport,sound_writeport,
				ignore_interrupt,1	/* irq generated by YM2203 */	
			)
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_Z, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
                new MachineSound[] {
			new MachineSound( 
				SOUND_YM2203, 
				ym2203_interface_lomakai 
			),
		}

	);
        
	
	/*
	 OSC:	? (YM2151 timers drive the music tempo)
	
	 KLOV: This game is based on a standard Jaleco platform (using twin 68K CPU's) that is
	 used on a few other games (e.g. Astyanax). It is coded MB8842 with an MB8843 ROM
	 daughter card. Changing the ROM card changes the game.
	*/
/*TODO*///	MEGASYS1_GAME(	p47, A,0x0f0000,0x0fffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
	static YM2151interface ym2151_interface_p47 = new YM2151interface
	( 
		1, 
		STD_FM_CLOCK, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	 
	static OKIM6295interface okim6295_interface_p47 = new OKIM6295interface
	( 
		2, 
		new int[] {STD_OKI_CLOCK, STD_OKI_CLOCK},
		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
		new int[] { 30, 30 } 
	); 
        
        static MemoryReadAddress readmem_p47[] =
	{ 
		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
		new MemoryReadAddress( 0x0f0000, 0x0fffff, MRA_BANK1				), 
		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
		new MemoryReadAddress( -1 ) 
	}; 
	static MemoryWriteAddress writemem_p47[] =
	{ 
	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
		new MemoryWriteAddress( 0x0f0000, 0x0fffff, MWA_BANK1,			megasys1_ram			), 
		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0/*,0*/	), 
		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1/*,0*/	), 
		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2/*,0*/	), 
		new MemoryWriteAddress( -1 ) 
	};        
	 
	static MachineDriver machine_driver_p47 = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				12000000, 
				readmem_p47,writemem_p47,null,null, 
				interrupt_A,INTERRUPT_NUM_A 
			), 
			new MachineCPU( 
				CPU_M68000 | CPU_AUDIO_CPU, 
				7000000, 
				sound_readmem_A,sound_writemem_A,null,null, 
				sound_interrupt,SOUND_INTERRUPT_NUM
			), 
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_A, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
		new MachineSound[] { 
			new MachineSound( 
				SOUND_YM2151, 
				ym2151_interface_p47 
			),
			new MachineSound(
				SOUND_OKIM6295, 
				okim6295_interface_p47 
			) 
		} 
	);
        
/*TODO*///	/* OSC:	? */
/*TODO*///	/* KLOV: Jaleco board no. PB-92127A. Main CPU: Motorola 68000P10 */
/*TODO*///	MEGASYS1_GAME(	peekaboo, D,0x1f0000,0x1fffff,
/*TODO*///					10000000,0,	0,12000,0 )	// 1 cpu, no fm, 1 oki
/*TODO*///	
/*TODO*///	/* OSC:	? */
/*TODO*///	MEGASYS1_GAME(	plusalph, A,0x0f0000,0x0fffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
/*TODO*///	/* OSC:	4,12,7 MHz */
/*TODO*///	MEGASYS1_GAME(	rodland, A,0x0f0000,0x0fffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
        
        
/*TODO*///	/* OSC:	? */
/*TODO*///	MEGASYS1_GAME(	stdragon, A,0x0f0000,0x0fffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	#define MEGASYS1_MACHINE(	_type_, 
/*TODO*///								_shortname_,_main_clock_,_sound_clock_, 
/*TODO*///								_fm_clock_,_oki1_clock_,_oki2_clock_) 
	static YM2151interface ym2151_interface_stdragon = new YM2151interface
	( 
		1, 
		STD_FM_CLOCK, 
		new int[] { YM3012_VOL(80,MIXER_PAN_LEFT,80,MIXER_PAN_RIGHT) }, 
		new WriteYmHandlerPtr[] { null } 
	); 
	 
	static OKIM6295interface okim6295_interface_stdragon = new OKIM6295interface
	( 
		2, 
		new int[] {STD_OKI_CLOCK, STD_OKI_CLOCK},
		new int[] { REGION_SOUND1, REGION_SOUND2 }, 
		new int[] { 30, 30 } 
	); 
        
        /*TODO*///	#define MEMORYMAP_A(_shortname_,_ram_start_,_ram_end_) 
	static MemoryReadAddress readmem_stdragon[] =
	{ 
		new MemoryReadAddress( 0x000000, 0x05ffff, MRA_ROM					), 
		new MemoryReadAddress( 0x0f0000, 0x0fffff, MRA_BANK1				), 
		new MemoryReadAddress( 0x080000, 0x080001, coins_r					), 
		new MemoryReadAddress( 0x080002, 0x080003, player1_r					), 
		new MemoryReadAddress( 0x080004, 0x080005, player2_r					), 
		new MemoryReadAddress( 0x080006, 0x080007, dsw_r						), 
		new MemoryReadAddress( 0x080008, 0x080009, soundlatch2_r				), /* from sound cpu */ 
		new MemoryReadAddress( 0x084000, 0x084fff, MRA_BANK2					), 
		new MemoryReadAddress( 0x088000, 0x0887ff, paletteram_word_r			), 
		new MemoryReadAddress( 0x08e000, 0x08ffff, MRA_BANK3					), 
		new MemoryReadAddress( 0x090000, 0x093fff, megasys1_scrollram_0_r	), 
		new MemoryReadAddress( 0x094000, 0x097fff, megasys1_scrollram_1_r	), 
		new MemoryReadAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_r	), 
		new MemoryReadAddress( -1 ) 
	}; 
	static MemoryWriteAddress writemem_stdragon[] =
	{ 
	 	new MemoryWriteAddress( 0x000000, 0x05ffff, MWA_ROM											), 
		new MemoryWriteAddress( 0x0f0000, 0x0fffff, MWA_BANK1,			megasys1_ram			), 
		new MemoryWriteAddress( 0x084000, 0x0843ff, megasys1_vregs_A_w,		megasys1_vregs			), 
		new MemoryWriteAddress( 0x088000, 0x0887ff, paletteram_RRRRGGGGBBBBRGBx_word_w, paletteram	), 
		new MemoryWriteAddress( 0x08e000, 0x08ffff, MWA_BANK3,				megasys1_objectram		), 
		new MemoryWriteAddress( 0x090000, 0x093fff, megasys1_scrollram_0_w,	megasys1_scrollram_0/*,0*/	), 
		new MemoryWriteAddress( 0x094000, 0x097fff, megasys1_scrollram_1_w,	megasys1_scrollram_1/*,0*/	), 
		new MemoryWriteAddress( 0x098000, 0x09bfff, megasys1_scrollram_2_w,	megasys1_scrollram_2/*,0*/	), 
		new MemoryWriteAddress( -1 ) 
	};
	 
	static MachineDriver machine_driver_stdragon = new MachineDriver
	( 
		new MachineCPU[] { 
			new MachineCPU( 
				CPU_M68000, 
				12000000, 
				readmem_stdragon,writemem_stdragon,null,null, 
				interrupt_A,INTERRUPT_NUM_A 
			), 
			new MachineCPU( 
				CPU_M68000 | CPU_AUDIO_CPU, 
				7000000, 
				sound_readmem_A,sound_writemem_A,null,null, 
				sound_interrupt,SOUND_INTERRUPT_NUM
			), 
		}, 
		60, DEFAULT_60HZ_VBLANK_DURATION, 
		1, 
		megasys1_init_machine, 
		/* video hardware */ 
		256, 256,new rectangle( 0, 256-1, 0+16, 256-16-1 ), 
		gfxdecodeinfo_A, 
		1024, 1024, 
		megasys1_convert_prom, 
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE, 
		null, 
		megasys1_vh_start, 
		null, 
		megasys1_vh_screenrefresh, 
		/* sound hardware */ 
		0,0,0,0, 
		new MachineSound[] { 
			new MachineSound( 
				SOUND_YM2151, 
				ym2151_interface_stdragon 
			),
			new MachineSound(
				SOUND_OKIM6295, 
				okim6295_interface_stdragon 
			) 
		} 
	);

        
/*TODO*///	
/*TODO*///	
/*TODO*///	/* OSC:	? */
/*TODO*///	MEGASYS1_GAME(	soldamj, A,0x0f0000,0x0fffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
/*TODO*///	
/*TODO*///	/* OSC:	? */
/*TODO*///	MEGASYS1_GAME(	tshingen, A, 0x0f0000, 0x0fffff,
/*TODO*///					12000000,7000000,STD_FM_CLOCK,STD_OKI_CLOCK,STD_OKI_CLOCK )
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///									ROMs Loading
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								[ 64th Street ]
/*TODO*///	
/*TODO*///	(World version)
/*TODO*///	interrupts:	1] 10eac:	disabled while b6c4=6 (10fb6 test)
/*TODO*///							if (8b1c != 0)	8b1c<-0
/*TODO*///								color cycle
/*TODO*///								copies 800 bytes 98da.8008
/*TODO*///	
/*TODO*///				2] 10f28:	switch b6c4
/*TODO*///							0	RTE
/*TODO*///							2	10f44:	M[b6c2]<-d8000; b6c4<-4
/*TODO*///							4	10f6c:	next b6c2 & d8000.	if (b6c2>A)	b6c2,4<-0
/*TODO*///															else		b6c4  <-2
/*TODO*///							6	10f82: b6c6<-(d8001) b6c7<-FF (test)
/*TODO*///	
/*TODO*///				4] 10ed0:	disabled while b6c4=6 (10fb6 test)
/*TODO*///							watchdog 8b1e
/*TODO*///							many routines...
/*TODO*///							b6c2<-0
/*TODO*///	
/*TODO*///	13ca	print a string: a7.screen disp.l(base=f0004),src.l
/*TODO*///	13ea	print a string: a1.(chars)*
/*TODO*///	1253c	hw test (table of tests at 125c6)		*TRAP#D*
/*TODO*///	125f8	mem test (table of mem tests at 126d4)
/*TODO*///	1278e	input test (table of tests at 12808)
/*TODO*///	128a8	sound test	12a08	crt test
/*TODO*///	12aca	dsw test (b68e.w = dswa.b|dswb.b)
/*TODO*///	
/*TODO*///	ff8b1e.w	incremented by int4, when >= b TRAP#E (software watchdog error)
/*TODO*///	ff9df8.w	*** level ***
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	#define ROM_LOAD_64STREET 
/*TODO*///		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */ 
/*TODO*///		ROM_LOAD_EVEN( "64th_08.rom", 0x000000, 0x010000, 0x632be0c1 );
/*TODO*///		ROM_LOAD_ODD(  "64th_07.rom", 0x000000, 0x010000, 0x13595d01 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x80000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */ 
/*TODO*///		ROM_LOAD( "64th_01.rom", 0x000000, 0x080000, 0x06222f90 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x80000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */ 
/*TODO*///		ROM_LOAD( "64th_06.rom", 0x000000, 0x080000, 0x2bfcdc75 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */ 
/*TODO*///		ROM_LOAD( "64th_09.rom", 0x000000, 0x020000, 0xa4a97db4 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x100000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */ 
/*TODO*///		ROM_LOAD( "64th_05.rom", 0x000000, 0x080000, 0xa89a7020 );
/*TODO*///		ROM_LOAD( "64th_04.rom", 0x080000, 0x080000, 0x98f83ef6 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x40000, REGION_SOUND1 );	/* Samples */ 
/*TODO*///		ROM_LOAD( "64th_11.rom", 0x000000, 0x020000, 0xb0b8a65c );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x40000, REGION_SOUND2 );	/* Samples */ 
/*TODO*///		ROM_LOAD( "64th_10.rom", 0x000000, 0x040000, 0xa3390561 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */ 
/*TODO*///		ROM_LOAD( "prom",        0x0000, 0x0200, 0x00000000 );
/*TODO*///	
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_64street = new RomLoadPtr(){ public void handler(){ 
/*TODO*///	
/*TODO*///		ROM_REGION( 0x80000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "64th_03.rom", 0x000000, 0x040000, 0xed6c6942 );
/*TODO*///		ROM_LOAD_ODD(  "64th_02.rom", 0x000000, 0x040000, 0x0621ed1d );
/*TODO*///	
/*TODO*///		ROM_LOAD_64STREET
/*TODO*///	
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_64streej = new RomLoadPtr(){ public void handler(){ 
/*TODO*///	
/*TODO*///		ROM_REGION( 0x80000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "91105-3.bin", 0x000000, 0x040000, 0xa211a83b );
/*TODO*///		ROM_LOAD_ODD(  "91105-2.bin", 0x000000, 0x040000, 0x27c1f436 );
/*TODO*///	
/*TODO*///		ROM_LOAD_64STREET
/*TODO*///	
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_64street = new InputPortPtr(){ public void handler() { 
/*TODO*///		COINS
/*TODO*///	//	fire	jump
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER1)
/*TODO*///		RESERVE				// Unused
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER2)
/*TODO*///	
/*TODO*///		PORT_START(); 
/*TODO*///		COINAGE_8BITS
/*TODO*///	
/*TODO*///		PORT_START(); 
/*TODO*///		PORT_DIPNAME( 0x01, 0x01, DEF_STR( "Flip_Screen") );
/*TODO*///		PORT_DIPSETTING(    0x01, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x02, 0x00, DEF_STR( "Demo_Sounds") );
/*TODO*///		PORT_DIPSETTING(    0x02, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x04, 0x04, "Allow Continue" );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x04, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x18, 0x18, DEF_STR( "Difficulty") );
/*TODO*///		PORT_DIPSETTING(    0x10, "Easy" );
/*TODO*///		PORT_DIPSETTING(    0x18, "Normal" );
/*TODO*///		PORT_DIPSETTING(    0x08, "Hard" );
/*TODO*///		PORT_DIPSETTING(    0x00, "Hardest" );
/*TODO*///		PORT_DIPNAME( 0x60, 0x20, DEF_STR( "Lives") );
/*TODO*///		PORT_DIPSETTING(    0x40, "1" );
/*TODO*///		PORT_DIPSETTING(    0x60, "2" );
/*TODO*///		PORT_DIPSETTING(    0x20, "3" );
/*TODO*///		PORT_DIPSETTING(    0x00, "5" );
/*TODO*///		PORT_SERVICE( 0x80, IP_ACTIVE_LOW );
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_64street = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///	//	UBytePtr RAM = memory_region(REGION_CPU1);
/*TODO*///	//	WRITE_WORD (&RAM[0x006b8],0x6004);		// d8001 test
/*TODO*///	//	WRITE_WORD (&RAM[0x10EDE],0x6012);		// watchdog
/*TODO*///	
/*TODO*///		ip_select_values[0] = 0x57;
/*TODO*///		ip_select_values[1] = 0x53;
/*TODO*///		ip_select_values[2] = 0x54;
/*TODO*///		ip_select_values[3] = 0x55;
/*TODO*///		ip_select_values[4] = 0x56;
/*TODO*///	} };
	
	
	/***************************************************************************
	
						[ The Astyanax ] / [ The Lord of King ]
	
	interrupts:	1] 1aa	2] 1b4
	
	***************************************************************************/
	
	
	static void ASTYANAX_ROM_LOAD() {
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */ 
		ROM_LOAD_EVEN( "astyan5.bin",  0x000000, 0x010000, 0x11c74045 );
		ROM_LOAD_ODD(  "astyan6.bin",  0x000000, 0x010000, 0xeecd4b16 );
	
		ROM_REGION( 0x80000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */ 
		ROM_LOAD( "astyan11.bin", 0x000000, 0x020000, 0x5593fec9 );
		ROM_LOAD( "astyan12.bin", 0x020000, 0x020000, 0xe8b313ec );
		ROM_LOAD( "astyan13.bin", 0x040000, 0x020000, 0x5f3496c6 );
		ROM_LOAD( "astyan14.bin", 0x060000, 0x020000, 0x29a09ec2 );
	
		ROM_REGION( 0x80000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */ 
		ROM_LOAD( "astyan15.bin", 0x000000, 0x020000, 0x0d316615 );
		ROM_LOAD( "astyan16.bin", 0x020000, 0x020000, 0xba96e8d9 );
		ROM_LOAD( "astyan17.bin", 0x040000, 0x020000, 0xbe60ba06 );
		ROM_LOAD( "astyan18.bin", 0x060000, 0x020000, 0x3668da3d );
	
		ROM_REGION( 0x20000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */ 
		ROM_LOAD( "astyan19.bin", 0x000000, 0x020000, 0x98158623 );
	
		ROM_REGION( 0x80000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */ 
		ROM_LOAD( "astyan20.bin", 0x000000, 0x020000, 0xc1ad9aa0 );
		ROM_LOAD( "astyan21.bin", 0x020000, 0x020000, 0x0bf498ee );
		ROM_LOAD( "astyan22.bin", 0x040000, 0x020000, 0x5f04d9b1 );
		ROM_LOAD( "astyan23.bin", 0x060000, 0x020000, 0x7bd4d1e7 );
	
		ROM_REGION( 0x40000, REGION_SOUND1 );	/* Samples */ 
		ROM_LOAD( "astyan9.bin",  0x000000, 0x020000, 0xa10b3f17 );
		ROM_LOAD( "astyan10.bin", 0x020000, 0x020000, 0x4f704e7a );
	
		ROM_REGION( 0x40000, REGION_SOUND2 );	/* Samples */ 
		ROM_LOAD( "astyan7.bin",  0x000000, 0x020000, 0x319418cc );
		ROM_LOAD( "astyan8.bin",  0x020000, 0x020000, 0x5e5d2a22 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */ 
		ROM_LOAD( "prom",         0x0000, 0x0200, 0x00000000 );
        }
	
	
	static RomLoadPtr rom_astyanax = new RomLoadPtr(){ public void handler(){ 
	
		ROM_REGION( 0x60000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "astyan2.bin", 0x00000, 0x20000, 0x1b598dcc );
		ROM_LOAD_ODD(  "astyan1.bin", 0x00000, 0x20000, 0x1a1ad3cf );
		ROM_LOAD_EVEN( "astyan3.bin", 0x40000, 0x10000, 0x097b53a6 );
		ROM_LOAD_ODD(  "astyan4.bin", 0x40000, 0x10000, 0x1e1cbdb2 );
	
		ASTYANAX_ROM_LOAD();
	
	ROM_END(); }}; 
	
	
/*TODO*///	static RomLoadPtr rom_lordofk = new RomLoadPtr(){ public void handler(){ 
/*TODO*///	
/*TODO*///		ROM_REGION( 0x80000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "lokj02.bin", 0x00000, 0x20000, 0x0d7f9b4a );
/*TODO*///		ROM_LOAD_ODD(  "lokj01.bin", 0x00000, 0x20000, 0xbed3cb93 );
/*TODO*///		ROM_LOAD_EVEN( "lokj03.bin", 0x40000, 0x20000, 0xd8702c91 );
/*TODO*///		ROM_LOAD_ODD(  "lokj04.bin", 0x40000, 0x20000, 0xeccbf8c9 );
/*TODO*///	
/*TODO*///		ASTYANAX_ROM_LOAD
/*TODO*///	
/*TODO*///	ROM_END(); }}; 
	
	
	static InputPortPtr input_ports_astyanax = new InputPortPtr(){ public void handler() { 
		COINS();						/* IN0 0x80001.b */
	//	fire	jump	magic
		JOY_3BUTTONS(IPF_PLAYER1);	/* IN1 0x80003.b */
		RESERVE();						/* IN2 0x80004.b */
		JOY_3BUTTONS(IPF_PLAYER2);	/* IN3 0x80005.b */
	
		PORT_START(); 			/* IN4 0x80006.b */
		PORT_DIPNAME( 0x07, 0x07, DEF_STR( "Coin_A") );
		PORT_DIPSETTING(    0x00, DEF_STR( "4C_1C") );
		PORT_DIPSETTING(    0x04, DEF_STR( "3C_1C") );
		PORT_DIPSETTING(    0x02, DEF_STR( "2C_1C") );
		PORT_DIPSETTING(    0x07, DEF_STR( "1C_1C") );
	//	PORT_DIPSETTING(    0x03, DEF_STR( "1C_1C") );	// 1_2 shown in test mode
	//	PORT_DIPSETTING(    0x05, DEF_STR( "1C_1C") );	// 1_3
	//	PORT_DIPSETTING(    0x01, DEF_STR( "1C_1C") );	// 1_4
	//	PORT_DIPSETTING(    0x06, DEF_STR( "1C_1C") );	// 1_5
		PORT_DIPNAME( 0x38, 0x38, DEF_STR( "Coin_B") );
		PORT_DIPSETTING(    0x00, DEF_STR( "4C_1C") );
		PORT_DIPSETTING(    0x20, DEF_STR( "3C_1C") );
		PORT_DIPSETTING(    0x10, DEF_STR( "2C_1C") );
		PORT_DIPSETTING(    0x38, DEF_STR( "1C_1C") );
	//	PORT_DIPSETTING(    0x18, DEF_STR( "1C_1C") );	// 1_2 shown in test mode
	//	PORT_DIPSETTING(    0x28, DEF_STR( "1C_1C") );	// 1_3
	//	PORT_DIPSETTING(    0x08, DEF_STR( "1C_1C") );	// 1_4
	//	PORT_DIPSETTING(    0x30, DEF_STR( "1C_1C") );	// 1_5
		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Unknown") );	// according to manual
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
		PORT_START(); 			/* IN5 0x80007.b */
		PORT_DIPNAME( 0x01, 0x01, DEF_STR( "Unknown") ); // according to manual
		PORT_DIPSETTING(    0x01, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x02, 0x02, DEF_STR( "Unknown") ); // according to manual
		PORT_DIPSETTING(    0x02, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x04, 0x04, DEF_STR( "Bonus_Life") );
		PORT_DIPSETTING(    0x04, "30k 70k 110k then every 30k" );
		PORT_DIPSETTING(    0x00, "50k 100k then every 40k" );
		PORT_DIPNAME( 0x18, 0x18, DEF_STR( "Lives") );
		PORT_DIPSETTING(    0x08, "2" );
		PORT_DIPSETTING(    0x18, "3" );
		PORT_DIPSETTING(    0x10, "4" );
		PORT_DIPSETTING(    0x00, "5" );
		PORT_DIPNAME( 0x20, 0x20, DEF_STR( "Difficulty") );
		PORT_DIPSETTING(    0x20, "Normal" );
		PORT_DIPSETTING(    0x00, "Hard" );
		PORT_DIPNAME( 0x40, 0x40, "Swap 1P/2P Controls" );
		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
	INPUT_PORTS_END(); }}; 
	
	
	static void astyanax_rom_decode(int cpu)
	{
		UBytePtr RAM	=	new UBytePtr(memory_region(REGION_CPU1+cpu));
		int i,			size	=	memory_region_length(REGION_CPU1+cpu);
		if (size > 0x40000)	size = 0x40000;
	
		for (i = 0 ; i < size ; i+=2)
		{
			int x,y;
	
			x = RAM.READ_WORD(i);
	
	// [0] def0 a981 65cb 7234
	// [1] fdb9 7531 8ace 0246
	// [2] 4567 0123 ba98 fedc
	
                int BITSWAP_0 = BITSWAP(x,0xd,0xe,0xf,0x0,0xa,0x9,0x8,0x1,0x6,0x5,0xc,0xb,0x7,0x2,0x3,0x4);
                int BITSWAP_1 =	BITSWAP(x,0xf,0xd,0xb,0x9,0x7,0x5,0x3,0x1,0x8,0xa,0xc,0xe,0x0,0x2,0x4,0x6);
                int BITSWAP_2 = BITSWAP(x,0x4,0x5,0x6,0x7,0x0,0x1,0x2,0x3,0xb,0xa,0x9,0x8,0xf,0xe,0xd,0xc);
	
			if		(i < 0x08000)	{ if ( (i | 0x248) != i ) {y = BITSWAP_0;} else {y = BITSWAP_1;} }
			else if	(i < 0x10000)	{ y = BITSWAP_2; }
			else if	(i < 0x18000)	{ if ( (i | 0x248) != i ) {y = BITSWAP_0;} else {y = BITSWAP_1;} }
			else if	(i < 0x20000)	{ y = BITSWAP_1; }
			else 					{ y = BITSWAP_2; }
	
/*TODO*///	#undef	BITSWAP_0
/*TODO*///	#undef	BITSWAP_1
/*TODO*///	#undef	BITSWAP_2
	
			RAM.WRITE_WORD(i, y);
		}
	}
	
	
	public static InitDriverPtr init_astyanax = new InitDriverPtr() { public void handler() 
	{
		UBytePtr RAM;
	
		astyanax_rom_decode(0);
	
		RAM  = new UBytePtr(memory_region(REGION_CPU1));
		RAM.WRITE_WORD(0x0004e6,0x6040);	// protection
	} };
	
	
	/***************************************************************************
	
						[ Avenging Spirit ] / [ Phantasm ]
	
	(Avspirit)
	interrupts:	2,3, 5,6,7]		move.w  $e0000.l, $78e9e.l
								andi.w  #$ff, $78e9e.l
				4] 78b20 software watchdog (78ea0 enables it)
	
	
	fd6		reads e0000 (values FF,06,34,35,36,37)
	ffa		e0000<-6 test
	
	79584.w *** level ***
	
	phntsm08.bin            spirit13.rom            IDENTICAL
	phntsm10.bin            spirit14.rom            IDENTICAL
	phntsm14.bin            spirit12.rom            IDENTICAL
	phntsm18.bin            spirit11.rom            IDENTICAL
	phntsm19.bin            spirit09.rom            IDENTICAL
	phntsm23.bin            spirit10.rom            IDENTICAL
	phntsm05.bin            spirit01.rom [1st half] IDENTICAL
	phntsm06.bin            spirit02.rom [1st half] IDENTICAL
	phntsm01.bin            spirit05.rom [1st half] 38.631%
	phntsm02.bin            spirit06.rom [1st half] 22.478%
	phntsm03.bin            spirit02.rom [2nd half] 14.528%
	phntsm03.bin            spirit01.rom [2nd half] 14.528%
	phntsm04.bin                                    NO MATCH
	
	1] E9C
	2] ED4
	3] F4C		rte
	4-7] ED2	rte
	
	***************************************************************************/
	
	static RomLoadPtr rom_avspirit = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0xc0000, REGION_CPU1 );	/* Main CPU Code: 00000-3ffff & 80000-bffff */
		ROM_LOAD_EVEN( "spirit05.rom",  0x000000, 0x020000, 0xb26a341a );
		ROM_CONTINUE (                  0x080000 & ~1, 0x020000 | ROMFLAG_ALTERNATE );
		ROM_LOAD_ODD(  "spirit06.rom",  0x000000, 0x020000, 0x609f71fe );
		ROM_CONTINUE (                  0x080000 | 1, 0x020000 | ROMFLAG_ALTERNATE );
	
		ROM_REGION( 0x40000, REGION_CPU2 );	/* Sound CPU Code */
		ROM_LOAD_EVEN( "spirit01.rom",  0x000000, 0x020000, 0xd02ec045 );
		ROM_LOAD_ODD(  "spirit02.rom",  0x000000, 0x020000, 0x30213390 );
	
		ROM_REGION( 0x80000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
		ROM_LOAD( "spirit12.rom",  0x000000, 0x080000, 0x728335d4 );
	
		ROM_REGION( 0x80000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
		ROM_LOAD( "spirit11.rom",  0x000000, 0x080000, 0x7896f6b0 );
	
		ROM_REGION( 0x20000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
		ROM_LOAD( "spirit09.rom",  0x000000, 0x020000, 0x0c37edf7 );
	
		ROM_REGION( 0x80000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
		ROM_LOAD( "spirit10.rom",  0x000000, 0x080000, 0x2b1180b3 );
	
		ROM_REGION( 0x40000, REGION_SOUND1 );	/* Samples */
		ROM_LOAD( "spirit14.rom",  0x000000, 0x040000, 0x13be9979 );
	
		ROM_REGION( 0x40000, REGION_SOUND2 );	/* Samples */
		ROM_LOAD( "spirit13.rom",  0x000000, 0x040000, 0x05bc04d9 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
		ROM_LOAD( "ph.bin",        0x0000, 0x0200, 0x8359650a );
	ROM_END(); }}; 
	
	
	static RomLoadPtr rom_phantasm = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x60000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "phntsm02.bin", 0x000000, 0x020000, 0xd96a3584 );
		ROM_LOAD_ODD(  "phntsm01.bin", 0x000000, 0x020000, 0xa54b4b87 );
		ROM_LOAD_EVEN( "phntsm03.bin", 0x040000, 0x010000, 0x1d96ce20 );
		ROM_LOAD_ODD(  "phntsm04.bin", 0x040000, 0x010000, 0xdc0c4994 );
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
		ROM_LOAD_EVEN( "phntsm05.bin", 0x000000, 0x010000, 0x3b169b4a );
		ROM_LOAD_ODD(  "phntsm06.bin", 0x000000, 0x010000, 0xdf2dfb2e );
	
		ROM_REGION( 0x80000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
	//	ROM_LOAD( "phntsm14.bin",  0x000000, 0x080000, 0x728335d4 );
		ROM_LOAD( "spirit12.rom",  0x000000, 0x080000, 0x728335d4 );
	
		ROM_REGION( 0x80000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
	//	ROM_LOAD( "phntsm18.bin",  0x000000, 0x080000, 0x7896f6b0 );
		ROM_LOAD( "spirit11.rom",  0x000000, 0x080000, 0x7896f6b0 );
	
		ROM_REGION( 0x20000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
	//	ROM_LOAD( "phntsm19.bin",  0x000000, 0x020000, 0x0c37edf7 );
		ROM_LOAD( "spirit09.rom",  0x000000, 0x020000, 0x0c37edf7 );
	
		ROM_REGION( 0x80000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
	//	ROM_LOAD( "phntsm23.bin",  0x000000, 0x080000, 0x2b1180b3 );
		ROM_LOAD( "spirit10.rom",  0x000000, 0x080000, 0x2b1180b3 );
	
		ROM_REGION( 0x40000, REGION_SOUND1 );	/* Samples */
	//	ROM_LOAD( "phntsm10.bin", 0x000000, 0x040000, 0x13be9979 );
		ROM_LOAD( "spirit14.rom", 0x000000, 0x040000, 0x13be9979 );
	
		ROM_REGION( 0x40000, REGION_SOUND2 );	/* Samples */
	//	ROM_LOAD( "phntsm08.bin", 0x000000, 0x040000, 0x05bc04d9 );
		ROM_LOAD( "spirit13.rom", 0x000000, 0x040000, 0x05bc04d9 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
		ROM_LOAD( "ph.bin",        0x0000, 0x0200, 0x8359650a );
	ROM_END(); }}; 
	
	//	fire	jump
	static void INPUT_PORTS_AVSPIRIT() {
		COINS(); 
		JOY_2BUTTONS(IPF_PLAYER1); 
		RESERVE(); 
		JOY_2BUTTONS(IPF_PLAYER2); 
	
		PORT_START();  
		COINAGE_8BITS(); 
	
		PORT_START();  
		PORT_DIPNAME( 0x01, 0x01, DEF_STR( "Flip_Screen") ); 
		PORT_DIPSETTING(    0x01, DEF_STR( "Off") ); 
		PORT_DIPSETTING(    0x00, DEF_STR( "On") ); 
		PORT_DIPNAME( 0x02, 0x00, DEF_STR( "Demo_Sounds") ); 
		PORT_DIPSETTING(    0x02, DEF_STR( "Off") ); 
		PORT_DIPSETTING(    0x00, DEF_STR( "On") ); 
		PORT_DIPNAME( 0x04, 0x04, "Allow Continue" );
		PORT_DIPSETTING(    0x00, DEF_STR( "Off") ); 
		PORT_DIPSETTING(    0x04, DEF_STR( "On") ); 
		PORT_DIPNAME( 0x18, 0x18, DEF_STR( "Difficulty") ); 
		PORT_DIPSETTING(    0x08, "Easy" );
		PORT_DIPSETTING(    0x18, "Normal" );
		PORT_DIPSETTING(    0x10, "Hard" );
		PORT_DIPSETTING(    0x00, "Hardest" );
		PORT_DIPNAME( 0x20, 0x20, DEF_STR( "Cabinet") ); 
		PORT_DIPSETTING(    0x20, DEF_STR( "Upright") ); 
		PORT_DIPSETTING(    0x00, DEF_STR( "Cocktail") ); 
		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Unknown") ); 
		PORT_DIPSETTING(    0x40, DEF_STR( "Off") ); 
		PORT_DIPSETTING(    0x00, DEF_STR( "On") ); 
		PORT_SERVICE( 0x80, IP_ACTIVE_LOW );
        }
	
	
	static InputPortPtr input_ports_avspirit = new InputPortPtr(){ public void handler() { 
		INPUT_PORTS_AVSPIRIT();
	INPUT_PORTS_END(); }}; 
	
	static InputPortPtr input_ports_phantasm = new InputPortPtr(){ public void handler() { 
		INPUT_PORTS_AVSPIRIT();
	INPUT_PORTS_END(); }}; 
	
	public static InitDriverPtr init_avspirit = new InitDriverPtr() { public void handler() 
	{
		ip_select_values[0] = 0x37;
		ip_select_values[1] = 0x35;
		ip_select_values[2] = 0x36;
		ip_select_values[3] = 0x33;
		ip_select_values[4] = 0x34;
	} };
	
	
	
	static void phantasm_rom_decode(int cpu)
	{
		UBytePtr RAM	=	new UBytePtr(memory_region(REGION_CPU1+cpu));
		int i,			size	=	memory_region_length(REGION_CPU1+cpu);
		if (size > 0x40000)	size = 0x40000;
	
		for (i = 0 ; i < size ; i+=2)
		{
			int x,y;
	
			x = RAM.READ_WORD(i);
	
	// [0] def0 189a bc56 7234
	// [1] fdb9 7531 eca8 6420
	// [2] 0123 4567 ba98 fedc
	int BITSWAP_0	= BITSWAP(x,0xd,0xe,0xf,0x0,0x1,0x8,0x9,0xa,0xb,0xc,0x5,0x6,0x7,0x2,0x3,0x4);
	int BITSWAP_1	= BITSWAP(x,0xf,0xd,0xb,0x9,0x7,0x5,0x3,0x1,0xe,0xc,0xa,0x8,0x6,0x4,0x2,0x0);
	int BITSWAP_2	= BITSWAP(x,0x0,0x1,0x2,0x3,0x4,0x5,0x6,0x7,0xb,0xa,0x9,0x8,0xf,0xe,0xd,0xc);
	
			if		(i < 0x08000)	{ if ( (i | 0x248) != i ) {y = BITSWAP_0;} else {y = BITSWAP_1;} }
			else if	(i < 0x10000)	{ y = BITSWAP_2; }
			else if	(i < 0x18000)	{ if ( (i | 0x248) != i ) {y = BITSWAP_0;} else {y = BITSWAP_1;} }
			else if	(i < 0x20000)	{ y = BITSWAP_1; }
			else 					{ y = BITSWAP_2; }
	
/*TODO*///	#undef	BITSWAP_0
/*TODO*///	#undef	BITSWAP_1
/*TODO*///	#undef	BITSWAP_2
	
			RAM.WRITE_WORD(i, y);
		}
	
	}
	
	
	public static InitDriverPtr init_phantasm = new InitDriverPtr() { public void handler() 
	{
		phantasm_rom_decode(0);
	} };
	
	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								[ Big Striker ]
/*TODO*///	
/*TODO*///	PCB: RB-91105A EB911009-20045
/*TODO*///	
/*TODO*///	Note: RAM is ff0000-ffffff while sprites live in 1f8000-1f87ff
/*TODO*///	
/*TODO*///	interrupts:	1]
/*TODO*///				2]
/*TODO*///				4]
/*TODO*///	
/*TODO*///	$885c/e.w	*** time (BCD) ***
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_bigstrik = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "91105v11.3", 0x000000, 0x020000, 0x5d6e08ec );
/*TODO*///		ROM_LOAD_ODD(  "91105v11.2", 0x000000, 0x020000, 0x2120f05b );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "91105v10.8", 0x000000, 0x010000, 0x7dd69ece );
/*TODO*///		ROM_LOAD_ODD(  "91105v10.7", 0x000000, 0x010000, 0xbc2c1508 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x80000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "91021.01",   0x000000, 0x080000, 0xf1945858 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x80000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "91021.03",   0x000000, 0x080000, 0xe88821e5 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		ROM_LOAD( "91105v11.9", 0x000000, 0x020000, 0x7be1c50c );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x100000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "91021.02",   0x000000, 0x080000, 0x199819ca );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x40000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "91105v10.11", 0x000000, 0x040000, BADCRC(0xa1f13dd5));	// 1xxxxxxxxxxxxxxxxx = 0xFF
/*TODO*///	
/*TODO*///		ROM_REGION( 0x40000, REGION_SOUND2 );	/* Samples */
/*TODO*///		ROM_LOAD( "91105v10.10", 0x000000, 0x040000, BADCRC(0xe4f8fc8d));	// 1xxxxxxxxxxxxxxxxx = 0xFF
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "prom",         0x0000, 0x0200, 0x00000000 );
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_bigstrik = new InputPortPtr(){ public void handler() { 
/*TODO*///		COINS
/*TODO*///	//	pass	shoot	feint
/*TODO*///		JOY_3BUTTONS(IPF_PLAYER1)
/*TODO*///		RESERVE
/*TODO*///		JOY_3BUTTONS(IPF_PLAYER2)
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN4 */
/*TODO*///		PORT_DIPNAME( 0x0f, 0x0f, DEF_STR( "Coin_A") );
/*TODO*///		PORT_DIPSETTING(    0x07, DEF_STR( "4C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x08, DEF_STR( "3C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x09, DEF_STR( "2C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x0f, DEF_STR( "1C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x05, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x04, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x03, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x02, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x01, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x06, DEF_STR( "2C_3C") );
/*TODO*///		PORT_DIPSETTING(    0x0e, DEF_STR( "1C_2C") );
/*TODO*///		PORT_DIPSETTING(    0x0d, DEF_STR( "1C_3C") );
/*TODO*///		PORT_DIPSETTING(    0x0c, DEF_STR( "1C_4C") );
/*TODO*///		PORT_DIPSETTING(    0x0b, DEF_STR( "1C_5C") );
/*TODO*///		PORT_DIPSETTING(    0x0a, DEF_STR( "1C_6C") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );
/*TODO*///		PORT_DIPNAME( 0xf0, 0xf0, DEF_STR( "Coin_B") );
/*TODO*///		PORT_DIPSETTING(    0x70, DEF_STR( "4C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "3C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x90, DEF_STR( "2C_1C") );
/*TODO*///		PORT_DIPSETTING(    0xf0, DEF_STR( "1C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x50, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x40, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x30, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x20, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x10, DEF_STR( "2C_3C") );
/*TODO*///	//	PORT_DIPSETTING(    0x60, DEF_STR( "2C_3C") );
/*TODO*///		PORT_DIPSETTING(    0xe0, DEF_STR( "1C_2C") );
/*TODO*///		PORT_DIPSETTING(    0xd0, DEF_STR( "1C_3C") );
/*TODO*///		PORT_DIPSETTING(    0xc0, DEF_STR( "1C_4C") );
/*TODO*///		PORT_DIPSETTING(    0xb0, DEF_STR( "1C_5C") );
/*TODO*///		PORT_DIPSETTING(    0xa0, DEF_STR( "1C_6C") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN5 */
/*TODO*///		PORT_DIPNAME( 0x01, 0x01, DEF_STR( "Flip_Screen") );
/*TODO*///		PORT_DIPSETTING(    0x01, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x06, 0x06, DEF_STR( "Difficulty") );
/*TODO*///		PORT_DIPSETTING(    0x02, "Easy"    );
/*TODO*///		PORT_DIPSETTING(    0x06, "Normal"  );
/*TODO*///		PORT_DIPSETTING(    0x04, "Hard"    );
/*TODO*///		PORT_DIPSETTING(    0x00, "Hardest" );
/*TODO*///		PORT_DIPNAME( 0x18, 0x18, "Time" );
/*TODO*///		PORT_DIPSETTING(    0x00, "Very Short" );
/*TODO*///		PORT_DIPSETTING(    0x10, "Short" );
/*TODO*///		PORT_DIPSETTING(    0x18, "Normal" );
/*TODO*///		PORT_DIPSETTING(    0x08, "Long" );
/*TODO*///		PORT_DIPNAME( 0x20, 0x20, DEF_STR( "Demo_Sounds") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x20, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, "1 Credit 2 Play" );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_SERVICE( 0x80, IP_ACTIVE_LOW );
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr bigstrik_spriteram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		COMBINE_WORD_MEM(&spriteram.read(offset),data);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_bigstrik = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		ip_select_values[0] = 0x58;
/*TODO*///		ip_select_values[1] = 0x54;
/*TODO*///		ip_select_values[2] = 0x55;
/*TODO*///		ip_select_values[3] = 0x56;
/*TODO*///		ip_select_values[4] = 0x57;
/*TODO*///	
/*TODO*///		install_mem_write_handler(0, 0x1f8000, 0x1f87ff, bigstrik_spriteram_w);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								[ Chimera Beast ]
/*TODO*///	
/*TODO*///	interrupts:	1,3]
/*TODO*///				2, 5,6]
/*TODO*///				4]
/*TODO*///	
/*TODO*///	Note: This game was a prototype
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_chimerab = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0x80000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "prg3.bin", 0x000000, 0x040000, 0x70f1448f );
/*TODO*///		ROM_LOAD_ODD(  "prg2.bin", 0x000000, 0x040000, 0x821dbb85 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "prg8.bin", 0x000000, 0x010000, 0xa682b1ca );
/*TODO*///		ROM_LOAD_ODD(  "prg7.bin", 0x000000, 0x010000, 0x83b9982d );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "s1.bin",   0x000000, 0x080000, 0xe4c2ac77 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "s2.bin",   0x000000, 0x080000, 0xfafb37a5 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		ROM_LOAD( "scr3.bin", 0x000000, 0x020000, 0x5fe38a83 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x100000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "b2.bin",   0x000000, 0x080000, 0x6e7f1778 );
/*TODO*///		ROM_LOAD( "b1.bin",   0x080000, 0x080000, 0x29c0385e );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "voi11.bin", 0x000000, 0x040000, 0x14b3afe6 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
/*TODO*///		ROM_LOAD( "voi10.bin", 0x000000, 0x040000, 0x67498914 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "prom",         0x0000, 0x0200, 0x00000000 );
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_chimerab = new InputPortPtr(){ public void handler() { 
/*TODO*///	
/*TODO*///		COINS
/*TODO*///	//	fire	jump	unused?(shown in service mode, but not in instructions)
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER1)
/*TODO*///		RESERVE				// Unused
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER2)
/*TODO*///	
/*TODO*///		PORT_START(); 			/* DSW A */
/*TODO*///		PORT_DIPNAME( 0x01, 0x01, DEF_STR( "Flip_Screen") );
/*TODO*///		PORT_DIPSETTING(    0x01, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x02, 0x00, DEF_STR( "Demo_Sounds") );
/*TODO*///		PORT_DIPSETTING(    0x02, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x04, 0x04, "Allow Continue" );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x04, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x18, 0x18, DEF_STR( "Difficulty") );
/*TODO*///		PORT_DIPSETTING(    0x10, "Easy" );
/*TODO*///		PORT_DIPSETTING(    0x18, "Normal" );
/*TODO*///		PORT_DIPSETTING(    0x08, "Hard" );
/*TODO*///		PORT_DIPSETTING(    0x00, "Hardest" );
/*TODO*///		PORT_DIPNAME( 0x60, 0x20, DEF_STR( "Lives") );
/*TODO*///		PORT_DIPSETTING(    0x40, "1" );
/*TODO*///		PORT_DIPSETTING(    0x60, "2" );
/*TODO*///		PORT_DIPSETTING(    0x20, "3" );
/*TODO*///		PORT_DIPSETTING(    0x00, "4" );
/*TODO*///		PORT_SERVICE( 0x80, IP_ACTIVE_LOW );
/*TODO*///	
/*TODO*///		PORT_START(); 			/* DSW B */
/*TODO*///		COINAGE_8BITS
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_chimerab = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		/* same as cybattlr */
/*TODO*///		ip_select_values[0] = 0x56;
/*TODO*///		ip_select_values[1] = 0x52;
/*TODO*///		ip_select_values[2] = 0x53;
/*TODO*///		ip_select_values[3] = 0x54;
/*TODO*///		ip_select_values[4] = 0x55;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///									[ Cybattler ]
/*TODO*///	
/*TODO*///	interrupts:	1,3]	408
/*TODO*///				2, 5,6]	498
/*TODO*///						1fd2c2.w routine index:
/*TODO*///						0:	4be>	1fd2c0.w <- d8000
/*TODO*///						2:	4ca>	1fd2d0+(1fd2c4.w) <- d8000.	next
/*TODO*///						4:	4ee>	1fd2c4.w += 2.
/*TODO*///												S	P1	P2	DB	DA
/*TODO*///									d8000 <-	56	52	53	55	54
/*TODO*///									1fd000+ 	00	02	04	06	08
/*TODO*///									depending on 1fd2c4.		previous
/*TODO*///						6:	4be again
/*TODO*///	
/*TODO*///				4]		452
/*TODO*///	
/*TODO*///	c2208 <- 1fd040	(layers enable)
/*TODO*///	c2200 <- 1fd042	(sprite control)
/*TODO*///	c2308 <- 1fd046	(screen control)
/*TODO*///	c2004 <- 1fd054 (scroll 0 ctrl)	c2000 <- 1fd220 (scroll 0 x)	c2002 <- 1fd222 (scroll 1 y)
/*TODO*///	c200c <- 1fd05a (scroll 1 ctrl)	c2008 <- 1fd224 (scroll 1 x)	c200a <- 1fd226 (scroll 2 y)
/*TODO*///	c2104 <- 1fd060 (scroll 2 ctrl)	c2100 <- 1fd228 (scroll 2 x)	c2102 <- 1fd22a (scroll 3 y)
/*TODO*///	
/*TODO*///	1f0010.w	*** level (0,1,..) ***
/*TODO*///	1fb044.l	*** score / 10 ***
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_cybattlr = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0x80000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "cb_03.rom", 0x000000, 0x040000, 0xbee20587 );
/*TODO*///		ROM_LOAD_ODD(  "cb_02.rom", 0x000000, 0x040000, 0x2ed14c50 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "cb_08.rom", 0x000000, 0x010000, 0xbf7b3558 );
/*TODO*///		ROM_LOAD_ODD(  "cb_07.rom", 0x000000, 0x010000, 0x85d219d7 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "cb_m01.rom", 0x000000, 0x080000, 0x1109337f );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "cb_m04.rom", 0x000000, 0x080000, 0x0c91798e );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		ROM_LOAD( "cb_09.rom",  0x000000, 0x020000, 0x37b1f195 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x100000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "cb_m03.rom", 0x000000, 0x080000, 0x4cd49f58 );
/*TODO*///		ROM_LOAD( "cb_m02.rom", 0x080000, 0x080000, 0x882825db );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "cb_11.rom", 0x000000, 0x040000, 0x59d62d1f );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
/*TODO*///		ROM_LOAD( "cb_10.rom", 0x000000, 0x040000, 0x8af95eed );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "prom",         0x0000, 0x0200, 0x00000000 );
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_cybattlr = new InputPortPtr(){ public void handler() { 
/*TODO*///	
/*TODO*///		COINS
/*TODO*///	//	fire	sword
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER1)
/*TODO*///		RESERVE				// Unused
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER2)
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN - DSW 1 - 1fd2d9.b, !1fd009.b */
/*TODO*///		PORT_DIPNAME( 0x07, 0x07, DEF_STR( "Coin_A") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "4C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x01, DEF_STR( "3C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x03, DEF_STR( "2C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x07, DEF_STR( "1C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x02, DEF_STR( "2C_3C") );
/*TODO*///		PORT_DIPSETTING(    0x06, DEF_STR( "1C_2C") );
/*TODO*///		PORT_DIPSETTING(    0x05, DEF_STR( "1C_3C") );
/*TODO*///		PORT_DIPSETTING(    0x04, DEF_STR( "1C_4C") );
/*TODO*///		PORT_DIPNAME( 0x38, 0x38, DEF_STR( "Coin_B") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "4C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x08, DEF_STR( "3C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x18, DEF_STR( "2C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x38, DEF_STR( "1C_1C") );
/*TODO*///		PORT_DIPSETTING(    0x10, DEF_STR( "2C_3C") );
/*TODO*///		PORT_DIPSETTING(    0x30, DEF_STR( "1C_2C") );
/*TODO*///		PORT_DIPSETTING(    0x28, DEF_STR( "1C_3C") );
/*TODO*///		PORT_DIPSETTING(    0x20, DEF_STR( "1C_4C") );
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Free_Play") );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_SERVICE( 0x80, IP_ACTIVE_LOW );
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN - DSW 2 - 1fd2d7.b, !1fd007.b */
/*TODO*///		PORT_DIPNAME( 0x01, 0x01, "Unknown 2-0" );
/*TODO*///		PORT_DIPSETTING(    0x01, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x02, 0x02, "Unknown 2-1" );
/*TODO*///		PORT_DIPSETTING(    0x02, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x04, 0x04, "Unknown 2-2" );// 1 bit
/*TODO*///		PORT_DIPSETTING(    0x04, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x08, 0x08, "Allow Continue" );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x08, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x10, 0x10, "Unknown 2-4" );
/*TODO*///		PORT_DIPSETTING(    0x10, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x20, 0x20, DEF_STR( "Demo_Sounds") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x20, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, "Unknown 2-6" );// 1 bit
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_cybattlr = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		ip_select_values[0] = 0x56;
/*TODO*///		ip_select_values[1] = 0x52;
/*TODO*///		ip_select_values[2] = 0x53;
/*TODO*///		ip_select_values[3] = 0x54;
/*TODO*///		ip_select_values[4] = 0x55;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///							 [ Earth Defense Force ]
/*TODO*///	
/*TODO*///	interrupts:	2,3]	543C>	move.w  $e0000.l,	$60da6.l
/*TODO*///								move.w  #$ffff,		$60da8.l
/*TODO*///				4,5,6]	5928 +	move.w	#$ffff,		$60010.l
/*TODO*///	
/*TODO*///	89e			(a7)+ . 44000.w & 6000e.w
/*TODO*///	8cc			(a7)+ . 44204.w ; 4420c.w ; 4400c.w
/*TODO*///	fc0			(a7)+ . 58000 (string)
/*TODO*///	
/*TODO*///	616f4.w		*** lives ***
/*TODO*///	60d8a.w		*** level(1..) ***
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_edf = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0xc0000, REGION_CPU1 );	/* Main CPU Code: 00000-3ffff & 80000-bffff */
/*TODO*///		ROM_LOAD_EVEN( "edf_05.rom",  0x000000, 0x020000, 0x105094d1 );
/*TODO*///		ROM_CONTINUE (                0x080000 & ~1, 0x020000 | ROMFLAG_ALTERNATE );
/*TODO*///		ROM_LOAD_ODD(  "edf_06.rom",  0x000000, 0x020000, 0x94da2f0c );
/*TODO*///		ROM_CONTINUE (                0x080000 | 1, 0x020000 | ROMFLAG_ALTERNATE );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x40000, REGION_CPU2 );	/* Sound CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "edf_01.rom",  0x000000, 0x020000, 0x2290ea19 );
/*TODO*///		ROM_LOAD_ODD(  "edf_02.rom",  0x000000, 0x020000, 0xce93643e );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "edf_m04.rom",  0x000000, 0x080000, 0x6744f406 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "edf_m05.rom",  0x000000, 0x080000, 0x6f47e456 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		ROM_LOAD( "edf_09.rom",   0x000000, 0x020000, 0x96e38983 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "edf_m03.rom",  0x000000, 0x080000, 0xef469449 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "edf_m02.rom",  0x000000, 0x040000, 0xfc4281d2 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
/*TODO*///		ROM_LOAD( "edf_m01.rom",  0x000000, 0x040000, 0x9149286b );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "prom.14m",    0x0000, 0x0200, 0x1d877538 );
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_edf = new InputPortPtr(){ public void handler() { 
/*TODO*///		COINS
/*TODO*///	//	fire	unfold_weapons
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER1)
/*TODO*///		RESERVE
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER2)
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN4 */
/*TODO*///		COINAGE_6BITS
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Demo_Sounds") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "On") );
/*TODO*///		PORT_SERVICE( 0x80, IP_ACTIVE_LOW );
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN5 0x66007.b */
/*TODO*///		PORT_DIPNAME( 0x07, 0x07, "DSW-B bits 2-0" );
/*TODO*///		PORT_DIPSETTING(    0x00, "0" );
/*TODO*///		PORT_DIPSETTING(    0x01, "1" );
/*TODO*///		PORT_DIPSETTING(    0x02, "2" );
/*TODO*///		PORT_DIPSETTING(    0x03, "3" );
/*TODO*///		PORT_DIPSETTING(    0x04, "4" );
/*TODO*///		PORT_DIPSETTING(    0x05, "5" );
/*TODO*///		PORT_DIPSETTING(    0x06, "6" );
/*TODO*///		PORT_DIPSETTING(    0x07, "7" );
/*TODO*///		PORT_DIPNAME( 0x08, 0x08, DEF_STR( "Lives") );
/*TODO*///		PORT_DIPSETTING(    0x08, "3" );
/*TODO*///		PORT_DIPSETTING(    0x00, "4" );
/*TODO*///		PORT_DIPNAME( 0x30, 0x30, DEF_STR( "Difficulty") );
/*TODO*///		PORT_DIPSETTING(    0x00, "0" );
/*TODO*///		PORT_DIPSETTING(    0x30, "1" );
/*TODO*///		PORT_DIPSETTING(    0x10, "2" );
/*TODO*///		PORT_DIPSETTING(    0x20, "3" );
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, "DSW-B bit 6" );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_edf = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		ip_select_values[0] = 0x20;
/*TODO*///		ip_select_values[1] = 0x21;
/*TODO*///		ip_select_values[2] = 0x22;
/*TODO*///		ip_select_values[3] = 0x23;
/*TODO*///		ip_select_values[4] = 0x24;
/*TODO*///	} };
	
	
	
	/***************************************************************************
	
									[ Hachoo! ]
	
	***************************************************************************/
	
	
	static void HACHOO_ROM_LOAD() {
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */ 
		ROM_LOAD_EVEN( "hacho05.rom", 0x000000, 0x010000, 0x6271f74f );
		ROM_LOAD_ODD(  "hacho06.rom", 0x000000, 0x010000, 0xdb9e743c );
	
		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */ 
		ROM_LOAD( "hacho14.rom", 0x000000, 0x080000, 0x10188483 );
	
		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */ 
		ROM_LOAD( "hacho15.rom", 0x000000, 0x020000, 0xe559347e );
		ROM_LOAD( "hacho16.rom", 0x020000, 0x020000, 0x105fd8b5 );
		ROM_LOAD( "hacho17.rom", 0x040000, 0x020000, 0x77f46174 );
		ROM_LOAD( "hacho18.rom", 0x060000, 0x020000, 0x0be21111 );
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */ 
		ROM_LOAD( "hacho19.rom", 0x000000, 0x020000, 0x33bc9de3 );
	
		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */ 
		ROM_LOAD( "hacho20.rom", 0x000000, 0x020000, 0x2ae2011e );
		ROM_LOAD( "hacho21.rom", 0x020000, 0x020000, 0x6dcfb8d5 );
		ROM_LOAD( "hacho22.rom", 0x040000, 0x020000, 0xccabf0e0 );
		ROM_LOAD( "hacho23.rom", 0x060000, 0x020000, 0xff5f77aa );
	
		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */ 
		ROM_LOAD( "hacho09.rom", 0x000000, 0x020000, 0xe9f35c90 );
		ROM_LOAD( "hacho10.rom", 0x020000, 0x020000, 0x1aeaa188 );
	
		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */ 
		ROM_LOAD( "hacho07.rom", 0x000000, 0x020000, 0x06e6ca7f );
		ROM_LOAD( "hacho08.rom", 0x020000, 0x020000, 0x888a6df1 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */ 
		ROM_LOAD( "prom",         0x0000, 0x0200, 0x00000000 );
        }
	
	
	static RomLoadPtr rom_hachoo = new RomLoadPtr(){ public void handler(){ 
	
		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "hacho02.rom", 0x000000, 0x020000, 0x49489c27 );
		ROM_LOAD_ODD(  "hacho01.rom", 0x000000, 0x020000, 0x97fc9515 );
	
		HACHOO_ROM_LOAD();
	
	ROM_END(); }}; 
	
	
	static InputPortPtr input_ports_hachoo = new InputPortPtr(){ public void handler() { 
		COINS();						/* IN0 0x80001.b */
	//	fire	jump
		JOY_2BUTTONS(IPF_PLAYER1);	/* IN1 0x80003.b */
		RESERVE();						/* IN2 0x80004.b */
		JOY_2BUTTONS(IPF_PLAYER2);	/* IN3 0x80005.b */
	
		PORT_START(); 			/* IN4 0x80006.b */
		COINAGE_6BITS();
		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x40, DEF_STR( "On") );
		PORT_DIPNAME( 0x80, 0x80, "Unknown 1-0" );// unused?
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
		PORT_START(); 			/* IN5 0x80007.b */
		PORT_DIPNAME( 0x01, 0x01, "Unknown 2-0" );// unused?
		PORT_DIPSETTING(    0x01, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x02, 0x02, "Unknown 2-1" );// unused?
		PORT_DIPSETTING(    0x02, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x04, 0x04, "Unknown 2-2" );// unused?
		PORT_DIPSETTING(    0x04, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x08, 0x08, "Unknown 2-3" );// unused?
		PORT_DIPSETTING(    0x08, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x30, 0x30, "?Difficulty?" );// 4 & 5
		PORT_DIPSETTING(    0x30, "3" );
		PORT_DIPSETTING(    0x20, "2" );
		PORT_DIPSETTING(    0x10, "1" );
		PORT_DIPSETTING(    0x00, "0" );
		PORT_DIPNAME( 0x40, 0x40, "Unknown 2-6" );// unused?
		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
	INPUT_PORTS_END(); }}; 
	
	public static InitDriverPtr init_hachoo = new InitDriverPtr() { public void handler() 
	{
		UBytePtr RAM;
	
		astyanax_rom_decode(0);
	
		RAM  = new UBytePtr(memory_region(REGION_CPU1));
		RAM.WRITE_WORD(0x0006da,0x6000);	// protection
	} };
	
	
	
/*TODO*///	/* There's another revision, but it's a bad dump (top half of the code is FF) */
/*TODO*///	/* There's the priority PROM 82s131.14m in there, all FFs though :( */
/*TODO*///	#if 0
/*TODO*///	static RomLoadPtr rom_hachoo1 = new RomLoadPtr(){ public void handler(){ 
/*TODO*///	
/*TODO*///		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///			ROM_LOAD_EVEN( "hacho02.rom", 0x000000, 0x020000, 0x49489c27 );
/*TODO*///			ROM_LOAD_ODD(  "hacho01.rom", 0x000000, 0x020000, 0x97fc9515 );
/*TODO*///			ROM_LOAD_EVEN( "rom-2", 0x000000, 0x010000, 0x3f56efb8 );
/*TODO*///			ROM_LOAD_ODD(  "rom-1", 0x000000, 0x010000, 0xc44bd605 );
/*TODO*///	
/*TODO*///		HACHOO_ROM_LOAD
/*TODO*///	
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	#endif

	
	/***************************************************************************
	
								[ Iga Ninjyutsuden ]
	
	interrupts:	1] 420(does nothing)
				2] 500
				3] 410(it doesn't save registers on the stack!!)
	
	f0004.l		*** hi score (BCD) ***
	f000c.l		*** score (BCD) ***
	f002a.w		*** lives ***
	f010c.w		credits
	
	***************************************************************************/
	
	
	static RomLoadPtr rom_iganinju = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x60000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "iga_02.bin", 0x000000, 0x020000, 0xbd00c280 );
		ROM_LOAD_ODD(  "iga_01.bin", 0x000000, 0x020000, 0xfa416a9e );
		ROM_LOAD_EVEN( "iga_03.bin", 0x040000, 0x010000, 0xde5937ad );
		ROM_LOAD_ODD(  "iga_04.bin", 0x040000, 0x010000, 0xafaf0480 );
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
		ROM_LOAD_EVEN( "iga_05.bin", 0x000000, 0x010000, 0x13580868 );
		ROM_LOAD_ODD(  "iga_06.bin", 0x000000, 0x010000, 0x7904d5dd );
	
		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
		ROM_LOAD( "iga_14.bin", 0x000000, 0x040000, 0xc707d513 );
	
		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
		ROM_LOAD( "iga_18.bin", 0x000000, 0x080000, 0x6c727519 );
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
		ROM_LOAD( "iga_19.bin", 0x000000, 0x020000, 0x98a7e998 );
	
		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
		ROM_LOAD( "iga_23.bin", 0x000000, 0x080000, 0xfb58c5f4 );
	
		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
		ROM_LOAD( "iga_10.bin", 0x000000, 0x040000, 0x67a89e0d );
	
		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
		ROM_LOAD( "iga_08.bin", 0x000000, 0x040000, 0x857dbf60 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
		ROM_LOAD( "prom",         0x0000, 0x0200, 0x00000000 );
	ROM_END(); }}; 
	
	
	static InputPortPtr input_ports_iganinju = new InputPortPtr(){ public void handler() { 
	
		COINS();						/* IN0 0x80001.b */
	//	fire	jump
		JOY_2BUTTONS(IPF_PLAYER1);	/* IN1 0x80003.b */
		RESERVE();						/* IN2 0x80004.b */
		JOY_2BUTTONS(IPF_PLAYER2);	/* IN3 0x80005.b */
	
		PORT_START();  			/* IN4 0x80006.b */
		COINAGE_6BITS();
		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_BITX(    0x80, 0x80, IPT_DIPSWITCH_NAME | IPF_CHEAT, "Freeze Screen", IP_KEY_NONE, IP_JOY_NONE );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
		PORT_START(); 			/* IN5 - 0x80007.b */
		PORT_DIPNAME( 0x03, 0x03, DEF_STR( "Lives") );
		PORT_DIPSETTING(    0x03, "2" );
		PORT_DIPSETTING(    0x01, "3" );
		PORT_DIPSETTING(    0x02, "4" );
		PORT_BITX( 0,       0x00, IPT_DIPSWITCH_SETTING | IPF_CHEAT, "Inifinite", IP_KEY_NONE, IP_JOY_NONE );
		PORT_DIPNAME( 0x04, 0x04, DEF_STR( "Bonus_Life") );
		PORT_DIPSETTING(    0x04, "50k" );
		PORT_DIPSETTING(    0x00, "200k" );
		PORT_DIPNAME( 0x08, 0x08, "Allow Continue" );
		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x08, DEF_STR( "On") );
		PORT_DIPNAME( 0x30, 0x30, "?Difficulty?" );
		PORT_DIPSETTING(    0x30, "3" );
		PORT_DIPSETTING(    0x20, "2" );
		PORT_DIPSETTING(    0x10, "1" );
		PORT_DIPSETTING(    0x00, "0" );
		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Cabinet") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Upright") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Cocktail") );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
	INPUT_PORTS_END(); }}; 
	
	public static InitDriverPtr init_iganinju = new InitDriverPtr() { public void handler() 
	{
		UBytePtr RAM;
	
		phantasm_rom_decode(0);
	
		RAM  = new UBytePtr(memory_region(REGION_CPU1));
		RAM.WRITE_WORD(0x02f000,0x835d);	// protection
	
		RAM.WRITE_WORD(0x00006e,0x0420);	// the only game that does
											// not like lev 3 interrupts
	} };
	
	
	
	/***************************************************************************
	
								[ Kick Off ]
	
	WARNING: The sound CPU writes and read in the 9000-ffff area
	
	interrupts:	1-2]	rte
				3]		timer
				4-7]	loop forever
	
	f0128/a.w	*** Time (minutes/seconds BCD) ***
	f012c/e.w	*** Goals (P1/P2) ***
	
	Notes:
		* Coin B and Test are ignored
		* The alternate control method (selectable through a DSW)
		  isn't implemented: the program tests the low 4 bits of
		  the joystick inputs ($80002, $80004) but not the buttons.
		  I can't get the players to move
		* Pressing P1 or P2 Start while the game boots pops up
		  a rudimental sprites or tiles browser
	
	***************************************************************************/
	
	static RomLoadPtr rom_kickoff = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x20000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "kioff03.rom", 0x000000, 0x010000, 0x3b01be65 );
		ROM_LOAD_ODD(  "kioff01.rom", 0x000000, 0x010000, 0xae6e68a1 );
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
		ROM_LOAD_EVEN( "kioff09.rom", 0x000000, 0x010000, 0x1770e980 );
		ROM_LOAD_ODD(  "kioff19.rom", 0x000000, 0x010000, 0x1b03bbe4 );
	
		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
		ROM_LOAD( "kioff05.rom", 0x000000, 0x020000, 0xe7232103 );
		ROM_LOAD( "kioff06.rom", 0x020000, 0x020000, 0xa0b3cb75 );
		ROM_LOAD( "kioff07.rom", 0x040000, 0x020000, 0xed649919 );
		ROM_LOAD( "kioff10.rom", 0x060000, 0x020000, 0xfd739fec );
	
		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
		// scroll 1 is unused
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
		ROM_LOAD( "kioff16.rom", 0x000000, 0x020000, 0x22c46314 );
	
		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
		ROM_LOAD( "kioff27.rom", 0x000000, 0x020000, 0xca221ae2 );
		ROM_LOAD( "kioff18.rom", 0x020000, 0x020000, 0xd7909ada );
		ROM_LOAD( "kioff17.rom", 0x040000, 0x020000, 0xf171559e );
		ROM_LOAD( "kioff26.rom", 0x060000, 0x020000, 0x2a90df1b );
	
		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
		ROM_LOAD( "kioff20.rom", 0x000000, 0x020000, 0x5c28bd2d );
		ROM_LOAD( "kioff21.rom", 0x020000, 0x020000, 0x195940cf );
	
		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
		// same rom for 2 oki chips ?? Unlikely
		ROM_LOAD( "kioff20.rom", 0x000000, 0x020000, 0x5c28bd2d );
		ROM_LOAD( "kioff21.rom", 0x020000, 0x020000, 0x195940cf );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
		ROM_LOAD( "prom",    0x0000, 0x0200, 0x00000000 );
	ROM_END(); }}; 
	
	static InputPortPtr input_ports_kickoff = new InputPortPtr(){ public void handler() { 
		COINS();						/* IN0 0x80001.b .  !f0008/a.w  */
	//	shoot	pass
		JOY_2BUTTONS(IPF_PLAYER1);	/* IN1 0x80003.b .  !f000c/e.w  */
		RESERVE();						/* IN2 0x80004.b -. !f0010/11.w */
		JOY_2BUTTONS(IPF_PLAYER2);	/* IN3 0x80005.b /               */
	
		PORT_START(); 			/* IN4 0x80006.b */
		PORT_DIPNAME( 0x07, 0x07, DEF_STR( "Coin_A") );
		PORT_DIPSETTING(    0x01, DEF_STR( "4C_1C") );
		PORT_DIPSETTING(    0x02, DEF_STR( "3C_1C") );
		PORT_DIPSETTING(    0x03, DEF_STR( "2C_1C") );
		PORT_DIPSETTING(    0x07, DEF_STR( "1C_1C") );
		PORT_DIPSETTING(    0x06, DEF_STR( "1C_2C") );
		PORT_DIPSETTING(    0x05, DEF_STR( "1C_3C") );
		PORT_DIPSETTING(    0x04, DEF_STR( "1C_4C") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );
		PORT_DIPNAME( 0x08, 0x08, "Unknown 1-3" );// unused?
		PORT_DIPSETTING(    0x08, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x10, 0x10, "Unknown 1-4" );// unused?
		PORT_DIPSETTING(    0x10, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_BITX(    0x20, 0x20, IPT_DIPSWITCH_NAME | IPF_CHEAT, "Freeze Screen", IP_KEY_NONE, IP_JOY_NONE );
		PORT_DIPSETTING(    0x20, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x80, 0x80, "Text" );
		PORT_DIPSETTING(    0x80, "Japanese" );
		PORT_DIPSETTING(    0x00, "English" );// show "Japan Only" warning
	
		PORT_START(); 			/* IN5 0x80007.b */
		PORT_DIPNAME( 0x03, 0x03, "Time" );// . !f0082.w
		PORT_DIPSETTING(    0x03, "3'" );
		PORT_DIPSETTING(    0x02, "4'" );
		PORT_DIPSETTING(    0x01, "5'" );
		PORT_DIPSETTING(    0x00, "6'" );
		PORT_DIPNAME( 0x04, 0x04, "Unknown 2-2" );// unused?
		PORT_DIPSETTING(    0x04, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x08, 0x08, "Unknown 2-3" );// unused?
		PORT_DIPSETTING(    0x08, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x30, 0x30, "?Difficulty?" );// . !f0084.w
		PORT_DIPSETTING(    0x30, "0" );
		PORT_DIPSETTING(    0x20, "1" );
		PORT_DIPSETTING(    0x10, "2" );
		PORT_DIPSETTING(    0x00, "3" );
		PORT_DIPNAME( 0x40, 0x00, "Controls" );
		PORT_DIPSETTING(    0x40, DEF_STR( "Unknown") );
		PORT_DIPSETTING(    0x00, "Joystick" );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
	INPUT_PORTS_END(); }}; 
	
	
	/***************************************************************************
	
								[ Legend of Makai ]
	
	***************************************************************************/
	
	static RomLoadPtr rom_lomakai = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "lom_30.rom", 0x000000, 0x020000, 0xba6d65b8 );
		ROM_LOAD_ODD(  "lom_20.rom", 0x000000, 0x020000, 0x56a00dc2 );
	
		ROM_REGION( 0x10000, REGION_CPU2 );	/* Sound CPU Code (Z80) */
		ROM_LOAD( "lom_01.rom",  0x0000, 0x10000, 0x46e85e90 );
	
		ROM_REGION( 0x020000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
		ROM_LOAD( "lom_05.rom", 0x000000, 0x020000, 0xd04fc713 );
	
		ROM_REGION( 0x010000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
		ROM_LOAD( "lom_08.rom", 0x000000, 0x010000, 0xbdb15e67 );
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Sprites */
		ROM_LOAD( "lom_06.rom", 0x000000, 0x020000, 0xf33b6eed );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Unknown PROMs */
		ROM_LOAD( "makaiden.9",  0x0000, 0x0100, 0x3567065d );
		ROM_LOAD( "makaiden.10", 0x0100, 0x0100, 0xe6709c51 );
	ROM_END(); }}; 
	
/*TODO*///	static RomLoadPtr rom_makaiden = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "makaiden.3a", 0x000000, 0x020000, 0x87cf81d1 );
/*TODO*///		ROM_LOAD_ODD(  "makaiden.2a", 0x000000, 0x020000, 0xd40e0fea );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x10000, REGION_CPU2 );	/* Sound CPU Code (Z80) */
/*TODO*///		ROM_LOAD( "lom_01.rom",  0x0000, 0x10000, 0x46e85e90 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "lom_05.rom", 0x000000, 0x020000, 0xd04fc713 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x010000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "makaiden.8", 0x000000, 0x010000, 0xa7f623f9 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "lom_06.rom", 0x000000, 0x020000, 0xf33b6eed );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Unknown PROMs */
/*TODO*///		ROM_LOAD( "makaiden.9",  0x0000, 0x0100, 0x3567065d );
/*TODO*///		ROM_LOAD( "makaiden.10", 0x0100, 0x0100, 0xe6709c51 );
/*TODO*///	ROM_END(); }}; 
	
	static InputPortPtr input_ports_lomakai = new InputPortPtr(){ public void handler() { 
		COINS();						/* IN0 0x80001.b */
	//	fire	jump
		JOY_2BUTTONS(IPF_PLAYER1);	/* IN1 0x80003.b */
		RESERVE();						/* IN2 0x80004.b */
		JOY_2BUTTONS(IPF_PLAYER2);	/* IN3 0x80005.b */
	
		PORT_START(); 			/* IN4 0x80006.b */
		COINAGE_6BITS_2();
		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_BITX(    0x80, 0x80, IPT_DIPSWITCH_NAME | IPF_CHEAT, "Invulnerability", IP_KEY_NONE, IP_JOY_NONE );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
		PORT_START(); 			/* IN5 0x80007.b */
		PORT_DIPNAME( 0x03, 0x03, DEF_STR( "Lives") );
		PORT_DIPSETTING(    0x00, "2" );
		PORT_DIPSETTING(    0x03, "3" );
		PORT_DIPSETTING(    0x02, "4" );
		PORT_DIPSETTING(    0x01, "5" );
		PORT_DIPNAME( 0x04, 0x04, DEF_STR( "Unknown") );
		PORT_DIPSETTING(    0x04, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x08, 0x08, DEF_STR( "Unknown") );
		PORT_DIPSETTING(    0x08, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x30, 0x30, "?Difficulty?" );
		PORT_DIPSETTING(    0x00, "Easy" );
		PORT_DIPSETTING(    0x30, "Normal" );
		PORT_DIPSETTING(    0x20, "Hard" );
		PORT_DIPSETTING(    0x10, "Hardest" );
		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Cabinet") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Upright") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Cocktail") );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
	INPUT_PORTS_END(); }}; 
	
	
	/***************************************************************************
	
								 [ P - 47 ]
	
	(Japan version)
	interrupts:	1]	53e		2] 540
	
	517a		print word string: (a6)+,(a5)+$40. FFFF ends
	5dbc		print string(s) to (a1)+$40: a6. len.b,x.b,y.b,(chars.b)*
	726a		prints screen
	7300		ram test
	7558		ip test
	75e6(7638 loop)	sound test
		84300.w		<-f1002.w	?portrait F/F on(0x0100)/off(0x0000)
		84308.w		<-f1004.w	sound code
	
	7736(7eb4 loop)	scroll 0 test
		9809c		color
		980a0		hscroll
		980a4		vscroll
		980a8		charsize
	
		7e1e		prepare screen
		7e84		get user input
		7faa		vhscroll
		80ce		print value.l from a0
	
	785c(78b8 loop)	obj check 1		84000.w	<-0x0E	84100.w	<-0x101
		9804c	size
		98050	number		(0e.w bit 11-0)
		98054	color code	(08.w bit 2-0)
		98058	H flip		(08.w bit 6)
		9805c	V flip		(08.w bit 7)
		98060	priority	(08.w bit 3)
		98064	mosaic		(08.w bit 11-8)
		98068	mosaic sol.	(08.w bit 12)
	
	7afe(7cfe loop)	obj check 2		84000.w	<-0x0f	84100.w	<-0x00
		9804a	obj num	(a4-8e000)/8
		9804e	H-rev	a4+02.w
		98052	V-rev	a4+04.w
		98056	CG-rev	a4+06.w
		9805a	Rem.Eff bit   4 of 84100
		98060	Rem.Num bit 3-0 of 84100 (see 7dd4)
	
	TRAP#2		pause?
	f0104.w		*** initial lives ***
	f002a/116.w	<-!80000
	f0810.w		<-!80002
	f0c00.w		<-!80004
	f0018.w		*** level ***
	
	
	***************************************************************************/
	
	static RomLoadPtr rom_p47 = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "p47us3.bin", 0x000000, 0x020000, 0x022e58b8 );
		ROM_LOAD_ODD(  "p47us1.bin", 0x000000, 0x020000, 0xed926bd8 );
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
		ROM_LOAD_EVEN( "p47j_9.bin",  0x000000, 0x010000, 0xffcf318e );
		ROM_LOAD_ODD(  "p47j_19.bin", 0x000000, 0x010000, 0xadb8c12e );
	
		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
		ROM_LOAD( "p47j_5.bin",  0x000000, 0x020000, 0xfe65b65c );
		ROM_LOAD( "p47j_6.bin",  0x020000, 0x020000, 0xe191d2d2 );
		ROM_LOAD( "p47j_7.bin",  0x040000, 0x020000, 0xf77723b7 );
	
		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
		ROM_LOAD( "p47j_23.bin", 0x000000, 0x020000, 0x6e9bc864 );
		ROM_RELOAD(              0x020000, 0x020000 );/* why? */
		ROM_LOAD( "p47j_12.bin", 0x040000, 0x020000, 0x5268395f );
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
		ROM_LOAD( "p47us16.bin", 0x000000, 0x010000, 0x5a682c8f );
	
		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
		ROM_LOAD( "p47j_27.bin", 0x000000, 0x020000, 0x9e2bde8e );
		ROM_LOAD( "p47j_18.bin", 0x020000, 0x020000, 0x29d8f676 );
		ROM_LOAD( "p47j_26.bin", 0x040000, 0x020000, 0x4d07581a );
		ROM_RELOAD(              0x060000, 0x020000 );/* why? */
	
		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
		ROM_LOAD( "p47j_20.bin", 0x000000, 0x020000, 0x2ed53624 );
		ROM_LOAD( "p47j_21.bin", 0x020000, 0x020000, 0x6f56b56d );
	
		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
		ROM_LOAD( "p47j_10.bin", 0x000000, 0x020000, 0xb9d79c1e );
		ROM_LOAD( "p47j_11.bin", 0x020000, 0x020000, 0xfa0d1887 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
		ROM_LOAD( "prom.14m",    0x0000, 0x0200, 0x1d877538 );
	ROM_END(); }}; 
	
	
	static RomLoadPtr rom_p47j = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "p47j_3.bin", 0x000000, 0x020000, 0x11c655e5 );
		ROM_LOAD_ODD(  "p47j_1.bin", 0x000000, 0x020000, 0x0a5998de );
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
		ROM_LOAD_EVEN( "p47j_9.bin",  0x000000, 0x010000, 0xffcf318e );
		ROM_LOAD_ODD(  "p47j_19.bin", 0x000000, 0x010000, 0xadb8c12e );
	
		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
		ROM_LOAD( "p47j_5.bin",  0x000000, 0x020000, 0xfe65b65c );
		ROM_LOAD( "p47j_6.bin",  0x020000, 0x020000, 0xe191d2d2 );
		ROM_LOAD( "p47j_7.bin",  0x040000, 0x020000, 0xf77723b7 );
	
		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
		ROM_LOAD( "p47j_23.bin", 0x000000, 0x020000, 0x6e9bc864 );
		ROM_RELOAD(              0x020000, 0x020000 );/* why? */
		ROM_LOAD( "p47j_12.bin", 0x040000, 0x020000, 0x5268395f );
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
		ROM_LOAD( "p47j_16.bin", 0x000000, 0x010000, 0x30e44375 );
	
		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
		ROM_LOAD( "p47j_27.bin", 0x000000, 0x020000, 0x9e2bde8e );
		ROM_LOAD( "p47j_18.bin", 0x020000, 0x020000, 0x29d8f676 );
		ROM_LOAD( "p47j_26.bin", 0x040000, 0x020000, 0x4d07581a );
		ROM_RELOAD(              0x060000, 0x020000 );/* why? */
	
		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
		ROM_LOAD( "p47j_20.bin", 0x000000, 0x020000, 0x2ed53624 );
		ROM_LOAD( "p47j_21.bin", 0x020000, 0x020000, 0x6f56b56d );
	
		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
		ROM_LOAD( "p47j_10.bin", 0x000000, 0x020000, 0xb9d79c1e );
		ROM_LOAD( "p47j_11.bin", 0x020000, 0x020000, 0xfa0d1887 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
		ROM_LOAD( "prom.14m",    0x0000, 0x0200, 0x1d877538 );
	ROM_END(); }}; 
	
	static InputPortPtr input_ports_p47 = new InputPortPtr(){ public void handler() { 
	
		COINS();						/* IN0 0x80001.b */
	//	fire	bomb
		JOY_2BUTTONS(IPF_PLAYER1);	/* IN1 0x80003.b */
		RESERVE();						/* IN2 0x80004.b */
		JOY_2BUTTONS(IPF_PLAYER2);	/* IN3 0x80005.b */
	
		PORT_START(); 			/* IN4 0x80006.b */
		COINAGE_6BITS_2();
		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x40, DEF_STR( "On") );
		PORT_BITX(    0x80, 0x80, IPT_DIPSWITCH_NAME | IPF_CHEAT, "Invulnerability", IP_KEY_NONE, IP_JOY_NONE );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
		PORT_START(); 			/* IN5 0x80007.b */
		PORT_DIPNAME( 0x03, 0x03, DEF_STR( "Lives") );
		PORT_DIPSETTING(    0x02, "2" );
		PORT_DIPSETTING(    0x03, "3" );
		PORT_DIPSETTING(    0x01, "4" );
		PORT_DIPSETTING(    0x00, "5" );
		PORT_DIPNAME( 0x04, 0x04, DEF_STR( "Unknown") );
		PORT_DIPSETTING(    0x04, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x08, 0x08, DEF_STR( "Unknown") );
		PORT_DIPSETTING(    0x08, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x30, 0x30, DEF_STR( "Difficulty") );
		PORT_DIPSETTING(    0x00, "Easy" );
		PORT_DIPSETTING(    0x30, "Normal" );
		PORT_DIPSETTING(    0x20, "Hard" );
		PORT_DIPSETTING(    0x10, "Hardest" );
		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Unknown") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
	INPUT_PORTS_END(); }}; 
	
	
	
	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								[ Peek-a-Boo! ]
/*TODO*///	
/*TODO*///	interrupts:
/*TODO*///		1] 		506>	rte
/*TODO*///		2] 		50a>	move.w  #$ffff, $1f0006.l
/*TODO*///						jsr     $46e0.l				rte
/*TODO*///		3] 		51c>	rte
/*TODO*///		4] 		520>	move.w  #$ffff, $1f000a.l	rte
/*TODO*///		5-7]	53c>	rte
/*TODO*///	
/*TODO*///	3832	Show error (d7 = ram segment where error occurred)
/*TODO*///			1 after d8000 ok. 3 after e0000&d0000 ok. 4 after ram&rom ok
/*TODO*///	
/*TODO*///	003E5E: 0000 3E72	[0]	Color Ram
/*TODO*///	003E62: 0000 3E86	[1]	Video Ram
/*TODO*///	003E66: 0000 3E9A	[2]	Sprite Ram
/*TODO*///	003E6A: 0000 3EB0	[3]	Work Ram
/*TODO*///	003E6E: 0000 3EC4	[4]	ROM
/*TODO*///	
/*TODO*///	000000-03ffff	rom (3f760 chksum)
/*TODO*///	1f0000-1fffff	ram
/*TODO*///	0d0000-0d3fff	text
/*TODO*///	0d8000-0d87ff	palette (+200 = text palette)
/*TODO*///	0e8000-0ebfff	layer
/*TODO*///	0e0000-0e0001	2 dips, 1f003a<-!
/*TODO*///	0f0000-0f0001	2 controls
/*TODO*///	0f8000-0f8001	???
/*TODO*///	
/*TODO*///	010000-010001	protectionwatchdog;
/*TODO*///		fb . fb
/*TODO*///		9x .	0		watchdog reset?
/*TODO*///				else	samples bank?
/*TODO*///						$1ff010 = sample
/*TODO*///						$1ff014 = bank = sample - $22 (33DC: 1 1 2 3 4 5 6 6 6 6)
/*TODO*///							samples:	bank:
/*TODO*///							$00-21		0
/*TODO*///							$22-2b		1-6
/*TODO*///	000000-01ffff
/*TODO*///	020000-03ffff	banked
/*TODO*///	
/*TODO*///		51 . paddle p1
/*TODO*///		52 . paddle p2
/*TODO*///		4bba waits for 1f000a to go !0, then clears 1f000a (int 4)
/*TODO*///		4bca waits (100000) & FF == 3
/*TODO*///		sequence $81, $71, $67 written
/*TODO*///	
/*TODO*///	
/*TODO*///	Scroll x,y,ctrl:
/*TODO*///	c2000<-1f0010		c2002<-1f0014		c2004<-1f000c
/*TODO*///	
/*TODO*///	Scroll x,y,ctrl:
/*TODO*///	c2008<-1f0018		c200a<-1f001c		c200c<-1f000e
/*TODO*///	
/*TODO*///	Layers ctrl:
/*TODO*///	c2208<-1f0024<<8 + 1f0026		c2308<-1f0022 | 1f002c
/*TODO*///	
/*TODO*///	Sprite bank + ??
/*TODO*///	c2108<-1f005a + 1f0060 + 1f0062 + 1f0068
/*TODO*///	
/*TODO*///	Sprite ctrl:
/*TODO*///	c2200<-0
/*TODO*///	
/*TODO*///	1f0000.w	routine index, table at $fae:
/*TODO*///		0: 4E40
/*TODO*///		1: 4EC2
/*TODO*///		2: 4F2C
/*TODO*///		3: 4F70
/*TODO*///		4: 4FBC
/*TODO*///		5: 533A
/*TODO*///		6: 5382
/*TODO*///		7: 556E
/*TODO*///	
/*TODO*///	1f003c/40	paddle p1/p2
/*TODO*///	1f0260/4.l	*** p1/p2 score/10 (BCD) ***
/*TODO*///	1f02e6/8.w	*** p1/p2 current lives ***
/*TODO*///				Bonus lives:	20K  100K  250K  500K 1000K
/*TODO*///	1f02ee		current player (0/1)
/*TODO*///	1f0380		hi score
/*TODO*///	
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_peekaboo = new RomLoadPtr(){ public void handler(){ 
/*TODO*///	
/*TODO*///		ROM_REGION( 0x40000, REGION_CPU1 );	/* 68000 CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "j3", 0x000000, 0x020000, 0xf5f4cf33 );
/*TODO*///		ROM_LOAD_ODD(  "j2", 0x000000, 0x020000, 0x7b3d430d );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "5",       0x000000, 0x080000, 0x34fa07bb );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "4",       0x000000, 0x020000, 0xf037794b );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		// Unused
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "1",       0x000000, 0x080000, 0x5a444ecf );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x120000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "peeksamp.124", 0x000000, 0x020000, 0xe1206fa8 );
/*TODO*///		ROM_CONTINUE(             0x040000, 0x0e0000             );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "priority.69",    0x000000, 0x200, 0xb40bff56 );
/*TODO*///	
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_peekaboo = new InputPortPtr(){ public void handler() { 
/*TODO*///	
/*TODO*///		PORT_START(); 		/* IN0 - COINS + P1&P2 Buttons - .b */
/*TODO*///		PORT_BIT(  0x0001, IP_ACTIVE_LOW, IPT_COIN3 );	// called "service"
/*TODO*///		PORT_BIT(  0x0002, IP_ACTIVE_LOW, IPT_COIN4 );	// called "test"
/*TODO*///		PORT_BIT(  0x0004, IP_ACTIVE_LOW, IPT_COIN1 );
/*TODO*///		PORT_BIT(  0x0008, IP_ACTIVE_LOW, IPT_COIN2 );
/*TODO*///		PORT_BIT(  0x0010, IP_ACTIVE_LOW, IPT_START1 );
/*TODO*///		PORT_BIT(  0x0020, IP_ACTIVE_LOW, IPT_START2 );
/*TODO*///		PORT_BIT(  0x0040, IP_ACTIVE_LOW, IPT_UNKNOWN );
/*TODO*///		PORT_BIT(  0x0080, IP_ACTIVE_LOW, IPT_UNKNOWN );
/*TODO*///		PORT_BIT(  0x0100, IP_ACTIVE_LOW, IPT_BUTTON1 );
/*TODO*///		PORT_BIT(  0x0200, IP_ACTIVE_LOW, IPT_BUTTON2 );
/*TODO*///		PORT_BIT(  0x0400, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2 );
/*TODO*///		PORT_BIT(  0x0800, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2 );
/*TODO*///		PORT_BIT(  0x1000, IP_ACTIVE_LOW, IPT_BUTTON3 );	// called "stage clear"
/*TODO*///		PORT_BIT(  0x2000, IP_ACTIVE_LOW, IPT_BUTTON4 );	// called "option"
/*TODO*///		PORT_BIT(  0x4000, IP_ACTIVE_LOW, IPT_UNKNOWN );
/*TODO*///		PORT_BIT(  0x8000, IP_ACTIVE_LOW, IPT_UNKNOWN );
/*TODO*///	
/*TODO*///	#define PEEKABOO_PADDLE(_FLAG_)	
/*TODO*///		PORT_ANALOG( 0x00ff, 0x0080, IPT_PADDLE | _FLAG_, 50, 10, 0x0018, 0x00e0 );
/*TODO*///	
/*TODO*///		PORT_START();       	/* IN1 - paddle p1 */
/*TODO*///		PEEKABOO_PADDLE(IPF_PLAYER1)
/*TODO*///	
/*TODO*///		RESERVE				/* IN2 - fake port */
/*TODO*///		PORT_START();       	/* IN3 - paddle p2 */
/*TODO*///		PEEKABOO_PADDLE(/*IPF_PLAYER2*/ IPF_COCKTAIL)
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN4 - DSW 1 - 1f003a.b<-e0000.b */
/*TODO*///		COINAGE_6BITS_2
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Demo_Sounds") );		// 1f0354<-
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );		// 1f0022/6e<-!
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN5 - DSW 2 - 1f003b.b<-e0001.b */
/*TODO*///		PORT_DIPNAME( 0x03, 0x03, "Unknown 2-0&1" );			// 1f0358<-!
/*TODO*///		PORT_DIPSETTING(    0x03, "3" );
/*TODO*///		PORT_DIPSETTING(    0x02, "2" );
/*TODO*///		PORT_DIPSETTING(    0x01, "1" );
/*TODO*///		PORT_DIPSETTING(    0x00, "0" );
/*TODO*///		PORT_SERVICE( 0x04, IP_ACTIVE_LOW );
/*TODO*///		PORT_DIPNAME( 0x08, 0x08, "Movement?" );				// 1f0392<-!
/*TODO*///		PORT_DIPSETTING(    0x08, "Paddles" );
/*TODO*///		PORT_DIPSETTING(    0x00, "Buttons" );
/*TODO*///		PORT_DIPNAME( 0x30, 0x30, "Nudity" );				// 1f0356<-!
/*TODO*///		PORT_DIPSETTING(    0x30, "Female and Male (Full); )
/*TODO*///		PORT_DIPSETTING(    0x20, "Female (Full); )
/*TODO*///		PORT_DIPSETTING(    0x10, "Female (Partial); )
/*TODO*///		PORT_DIPSETTING(    0x00, "None" );
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Cabinet") );			// 1f006a<-!
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "Upright") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Cocktail") );
/*TODO*///		PORT_DIPNAME( 0x80, 0x80, "(controls?);nknown 2-7" )	// 1f0074<-!
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );	// num of controls?
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static int protection_val;
/*TODO*///	
/*TODO*///	/* Read the input ports, through a protection device */
/*TODO*///	public static ReadHandlerPtr protection_peekaboo_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		switch (protection_val)
/*TODO*///		{
/*TODO*///			case 0x02:	return 0x03;
/*TODO*///			case 0x51:	return player1_r(0);
/*TODO*///			case 0x52:	return player2_r(0);
/*TODO*///			default:	return protection_val;
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	public static WriteHandlerPtr protection_peekaboo_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		static int bank;
/*TODO*///		protection_val = data;
/*TODO*///	
/*TODO*///		if ((protection_val & 0x90) == 0x90)
/*TODO*///		{
/*TODO*///			UBytePtr RAM = memory_region(okim6295_interface_peekaboo.region[0]);
/*TODO*///			int new_bank = (protection_val & 0x7) % 7;
/*TODO*///	
/*TODO*///			if (bank != new_bank)
/*TODO*///			{
/*TODO*///				memcpy(&RAM[0x20000],&RAM[0x40000 + 0x20000*new_bank],0x20000);
/*TODO*///				bank = new_bank;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	
/*TODO*///		cpu_cause_interrupt(0,4);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								[ Plus Alpha ]
/*TODO*///							  (aka Flight Alpha)
/*TODO*///	
/*TODO*///	f2ef8.w		bombs
/*TODO*///	f309e.w		*** lives       ***
/*TODO*///	f30a4.l		*** score (BCD) ***
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_plusalph = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0x60000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "pa-rom2.bin", 0x000000, 0x020000, 0x33244799 );
/*TODO*///		ROM_LOAD_ODD(  "pa-rom1.bin", 0x000000, 0x020000, 0xa32fdcae );
/*TODO*///		ROM_LOAD_EVEN( "pa-rom3.bin", 0x040000, 0x010000, 0x1b739835 );
/*TODO*///		ROM_LOAD_ODD(  "pa-rom4.bin", 0x040000, 0x010000, 0xff760e80 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "pa-rom5.bin", 0x000000, 0x010000, 0xddc2739b );
/*TODO*///		ROM_LOAD_ODD(  "pa-rom6.bin", 0x000000, 0x010000, 0xf6f8a167 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "pa-rom11.bin", 0x000000, 0x020000, 0xeb709ae7 );
/*TODO*///		ROM_LOAD( "pa-rom12.bin", 0x020000, 0x020000, 0xcacbc350 );
/*TODO*///		ROM_LOAD( "pa-rom13.bin", 0x040000, 0x020000, 0xfad093dd );
/*TODO*///		ROM_LOAD( "pa-rom14.bin", 0x060000, 0x020000, 0xd3676cd1 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "pa-rom15.bin", 0x000000, 0x020000, 0x8787735b );
/*TODO*///		ROM_LOAD( "pa-rom16.bin", 0x020000, 0x020000, 0xa06b813b );
/*TODO*///		ROM_LOAD( "pa-rom17.bin", 0x040000, 0x020000, 0xc6b38a4b );
/*TODO*///		/* empty place */
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		ROM_LOAD( "pa-rom19.bin", 0x000000, 0x010000, 0x39ef193c );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "pa-rom20.bin", 0x000000, 0x020000, 0x86c557a8 );
/*TODO*///		ROM_LOAD( "pa-rom21.bin", 0x020000, 0x020000, 0x81140a88 );
/*TODO*///		ROM_LOAD( "pa-rom22.bin", 0x040000, 0x020000, 0x97e39886 );
/*TODO*///		ROM_LOAD( "pa-rom23.bin", 0x060000, 0x020000, 0x0383fb65 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "pa-rom9.bin",  0x000000, 0x020000, 0x065364bd );
/*TODO*///		ROM_LOAD( "pa-rom10.bin", 0x020000, 0x020000, 0x395df3b2 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
/*TODO*///		ROM_LOAD( "pa-rom7.bin",  0x000000, 0x020000, 0x9f5d800e );
/*TODO*///		ROM_LOAD( "pa-rom8.bin",  0x020000, 0x020000, 0xae007750 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "prom.14m",     0x0000, 0x0200, 0x1d877538 );
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_plusalph = new InputPortPtr(){ public void handler() { 
/*TODO*///		COINS						/* IN0 0x80001.b */
/*TODO*///	//	fire	bomb
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER1)	/* IN1 0x80003.b */
/*TODO*///		RESERVE						/* IN2 0x80004.b */
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER2)	/* IN3 0x80005.b */
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN4 0x80006.b */
/*TODO*///		COINAGE_6BITS
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Demo_Sounds") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "On") );
/*TODO*///		PORT_BITX(    0x80, 0x80, IPT_DIPSWITCH_NAME | IPF_CHEAT, "Freeze Screen", IP_KEY_NONE, IP_JOY_NONE );
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN5 0x80007.b */
/*TODO*///		PORT_DIPNAME( 0x03, 0x03, DEF_STR( "Lives") );
/*TODO*///		PORT_DIPSETTING(    0x03, "3" );
/*TODO*///		PORT_DIPSETTING(    0x02, "4" );
/*TODO*///		PORT_DIPSETTING(    0x01, "5" );
/*TODO*///		PORT_BITX( 0,       0x00, IPT_DIPSWITCH_SETTING | IPF_CHEAT, "Inifinite", IP_KEY_NONE, IP_JOY_NONE );
/*TODO*///		PORT_DIPNAME( 0x04, 0x04, "Bombs" );
/*TODO*///		PORT_DIPSETTING(    0x00, "2" );
/*TODO*///		PORT_DIPSETTING(    0x04, "3" );
/*TODO*///		PORT_DIPNAME( 0x08, 0x08, DEF_STR( "Bonus_Life") );
/*TODO*///		PORT_DIPSETTING(    0x08, "70k and every 130k");
/*TODO*///		PORT_DIPSETTING(    0x00, "100k and every 200k");
/*TODO*///		PORT_DIPNAME( 0x30, 0x30, "?Difficulty?" );
/*TODO*///		PORT_DIPSETTING(    0x30, "3" );// 1
/*TODO*///		PORT_DIPSETTING(    0x20, "2" );// 3
/*TODO*///		PORT_DIPSETTING(    0x10, "1" );// 2
/*TODO*///		PORT_DIPSETTING(    0x00, "0" );// 0
/*TODO*///		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Cabinet") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Upright") );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "Cocktail") );
/*TODO*///		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_plusalph = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		UBytePtr RAM;
/*TODO*///	
/*TODO*///		astyanax_rom_decode(0);
/*TODO*///	
/*TODO*///		RAM  = memory_region(REGION_CPU1);
/*TODO*///		WRITE_WORD(&RAM[0x0012b6],0x0000);	// protection
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								[ RodLand ]
/*TODO*///	
/*TODO*///	(World version)
/*TODO*///	interrupts:	1] 418.3864: rts	2] 420: move.w #-1,f0010; jsr 3866	3] rte
/*TODO*///	
/*TODO*///	213da	print test error (20c12 = string address 0-4)
/*TODO*///	
/*TODO*///	f0018.84200	f0020.84208	f0028.84008
/*TODO*///	f001c.84202	f0024.8420a	f002c.8400a
/*TODO*///	f0012.84204	f0014.8420c	f0016.8400c
/*TODO*///	
/*TODO*///	7fe		d0.w . 84000.w & f000e.w
/*TODO*///	81a		d0/d1/d2 & $D . 84204 / 8420c /8400c
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_rodland = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0x60000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "rl_02.rom", 0x000000, 0x020000, 0xc7e00593 );
/*TODO*///		ROM_LOAD_ODD(  "rl_01.rom", 0x000000, 0x020000, 0x2e748ca1 );
/*TODO*///		ROM_LOAD_EVEN( "rl_03.rom", 0x040000, 0x010000, 0x62fdf6d7 );
/*TODO*///		ROM_LOAD_ODD(  "rl_04.rom", 0x040000, 0x010000, 0x44163c86 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "rl_05.rom", 0x000000, 0x010000, 0xc1617c28 );
/*TODO*///		ROM_LOAD_ODD(  "rl_06.rom", 0x000000, 0x010000, 0x663392b2 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "rl_23.rom", 0x000000, 0x020000, 0xac60e771 );
/*TODO*///		ROM_CONTINUE(          0x030000, 0x010000             );
/*TODO*///		ROM_CONTINUE(          0x050000, 0x010000             );
/*TODO*///		ROM_CONTINUE(          0x020000, 0x010000             );
/*TODO*///		ROM_CONTINUE(          0x040000, 0x010000             );
/*TODO*///		ROM_CONTINUE(          0x060000, 0x020000             );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "rl_18.rom", 0x000000, 0x080000, 0xf3b30ca6 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		ROM_LOAD( "rl_19.rom", 0x000000, 0x020000, 0x1b718e2a );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "rl_14.rom", 0x000000, 0x080000, 0x08d01bf4 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "rl_10.rom", 0x000000, 0x040000, 0xe1d1cd99 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
/*TODO*///		ROM_LOAD( "rl_08.rom", 0x000000, 0x040000, 0x8a49d3a7 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "rl.bin",    0x0000, 0x0200, 0x8914e72d );
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_rodlandj = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0x60000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "rl19.bin", 0x000000, 0x010000, 0x028de21f );
/*TODO*///		ROM_LOAD_ODD(  "rl17.bin", 0x000000, 0x010000, 0x9c720046 );
/*TODO*///		ROM_LOAD_EVEN( "rl20.bin", 0x020000, 0x010000, 0x3f536d07 );
/*TODO*///		ROM_LOAD_ODD(  "rl18.bin", 0x020000, 0x010000, 0x5aa61717 );
/*TODO*///		ROM_LOAD_EVEN( "rl12.bin", 0x040000, 0x010000, 0xc5b1075f );// ~ rl_03.rom
/*TODO*///		ROM_LOAD_ODD(  "rl11.bin", 0x040000, 0x010000, 0x9ec61048 );// ~ rl_04.rom
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "rl02.bin", 0x000000, 0x010000, 0xd26eae8f );
/*TODO*///		ROM_LOAD_ODD(  "rl01.bin", 0x000000, 0x010000, 0x04cf24bc );
/*TODO*///	
/*TODO*///		// bg RL_23 = 27, 28, 29b, 29a, 31a?, 30?, ?, 31b?
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "rl27.bin",  0x000000, 0x010000, 0x673a5986 );
/*TODO*///		ROM_LOAD( "rl28.bin",  0x010000, 0x010000, 0x523a731d );
/*TODO*///		ROM_LOAD( "rl26.bin",  0x020000, 0x010000, 0x4d0a5c97 );
/*TODO*///		ROM_LOAD( "rl29a.bin", 0x030000, 0x010000, 0x9fd628f1 );
/*TODO*///		ROM_LOAD( "rl29b.bin", 0x040000, 0x010000, 0x2279cb76 );
/*TODO*///		ROM_LOAD( "rl30.bin",  0x050000, 0x010000, 0xb155f39e );
/*TODO*///		ROM_LOAD( "rl31a.bin", 0x060000, 0x010000, 0xa9bc5b84 );
/*TODO*///		ROM_LOAD( "rl31b.bin", 0x070000, 0x010000, 0xfb2faa69 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "rl21.bin",  0x000000, 0x010000, 0x32fc0bc6 );// bg = RL_18
/*TODO*///		ROM_LOAD( "rl22.bin",  0x010000, 0x010000, 0x0969daa9 );
/*TODO*///		ROM_LOAD( "rl13.bin",  0x020000, 0x010000, 0x1203cdf6 );
/*TODO*///		ROM_LOAD( "rl14.bin",  0x030000, 0x010000, 0xd53e094b );
/*TODO*///		ROM_LOAD( "rl24.bin",  0x040000, 0x010000, 0xb04343e6 );
/*TODO*///		ROM_LOAD( "rl23.bin",  0x050000, 0x010000, 0x70aa7e2c );
/*TODO*///		ROM_LOAD( "rl15.bin",  0x060000, 0x010000, 0x38ac846e );
/*TODO*///		ROM_LOAD( "rl16.bin",  0x070000, 0x010000, 0x5e31f0b2 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		ROM_LOAD( "rl25.bin",  0x000000, 0x010000, 0x4ca57cb6 );// txt = RL_19
/*TODO*///		// Filled with pen 15 at startup
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "rl04.bin",  0x000000, 0x010000, 0xcfcf9f97 );// sprites = RL_14
/*TODO*///		ROM_LOAD( "rl05.bin",  0x010000, 0x010000, 0x38c05d15 );
/*TODO*///		ROM_LOAD( "rl07.bin",  0x020000, 0x010000, 0xe117cb72 );
/*TODO*///		ROM_LOAD( "rl08.bin",  0x030000, 0x010000, 0x2f9b40c3 );
/*TODO*///		ROM_LOAD( "rl03.bin",  0x040000, 0x010000, 0xf6a88efd );
/*TODO*///		ROM_LOAD( "rl06.bin",  0x050000, 0x010000, 0x90a78af1 );
/*TODO*///		ROM_LOAD( "rl09.bin",  0x060000, 0x010000, 0x427a0908 );
/*TODO*///		ROM_LOAD( "rl10.bin",  0x070000, 0x010000, 0x53cc2c11 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "rl_10.rom", 0x000000, 0x040000, 0xe1d1cd99 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
/*TODO*///		ROM_LOAD( "rl_08.rom", 0x000000, 0x040000, 0x8a49d3a7 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "rl.bin",    0x0000, 0x0200, 0x8914e72d );
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_rodland = new InputPortPtr(){ public void handler() { 
/*TODO*///	
/*TODO*///		COINS						/* IN0 0x80001.b */
/*TODO*///	//	fire	ladder
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER1)	/* IN1 0x80003.b */
/*TODO*///		RESERVE						/* IN2 0x80004.b */
/*TODO*///		JOY_2BUTTONS(IPF_PLAYER2)	/* IN3 0x80005.b */
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN4 0x80006.b */
/*TODO*///		COINAGE_6BITS
/*TODO*///		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Demo_Sounds") );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_SERVICE( 0x80, IP_ACTIVE_LOW );
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN5 0x80007.b */
/*TODO*///		PORT_DIPNAME( 0x01, 0x01, DEF_STR( "Unknown") ); /* according to manual */
/*TODO*///		PORT_DIPSETTING(    0x01, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x02, 0x02, DEF_STR( "Unknown") ); /* according to manual */
/*TODO*///		PORT_DIPSETTING(    0x02, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///		PORT_DIPNAME( 0x0c, 0x0c, DEF_STR( "Lives") );
/*TODO*///		PORT_DIPSETTING(    0x04, "2" );
/*TODO*///		PORT_DIPSETTING(    0x0c, "3" );
/*TODO*///		PORT_DIPSETTING(    0x08, "4" );
/*TODO*///		PORT_BITX( 0,       0x00, IPT_DIPSWITCH_SETTING | IPF_CHEAT, "Inifinite", IP_KEY_NONE, IP_JOY_NONE );
/*TODO*///		PORT_DIPNAME( 0x10, 0x10, "Default episode" );
/*TODO*///		PORT_DIPSETTING(    0x10, "1" );
/*TODO*///		PORT_DIPSETTING(    0x00, "2" );
/*TODO*///		PORT_DIPNAME( 0x60, 0x60, DEF_STR( "Difficulty") );
/*TODO*///		PORT_DIPSETTING(    0x20, "Easy?" );
/*TODO*///		PORT_DIPSETTING(    0x60, "Normal" );
/*TODO*///		PORT_DIPSETTING(    0x40, "Hard?" );
/*TODO*///		PORT_DIPSETTING(    0x00, "Hardest?" );
/*TODO*///		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	
/*TODO*///	void rodland_rom_decode(int cpu)
/*TODO*///	{
/*TODO*///		UBytePtr RAM	=	memory_region(REGION_CPU1+cpu);
/*TODO*///		int i,			size	=	memory_region_length(REGION_CPU1+cpu);
/*TODO*///		if (size > 0x40000)	size = 0x40000;
/*TODO*///	
/*TODO*///		for (i = 0 ; i < size ; i+=2)
/*TODO*///		{
/*TODO*///			int x,y;
/*TODO*///	
/*TODO*///			x = READ_WORD(&RAM[i]);
/*TODO*///	
/*TODO*///	// [0] d0a9 6ebf 5c72 3814	[1] 4567 0123 ba98 fedc
/*TODO*///	// [2] fdb9 ce07 5318 a246	[3] 4512 ed3b a967 08fc
/*TODO*///	#define BITSWAP_0	BITSWAP(x,0xd,0x0,0xa,0x9,0x6,0xe,0xb,0xf,0x5,0xc,0x7,0x2,0x3,0x8,0x1,0x4);
/*TODO*///	#define BITSWAP_1	BITSWAP(x,0x4,0x5,0x6,0x7,0x0,0x1,0x2,0x3,0xb,0xa,0x9,0x8,0xf,0xe,0xd,0xc);
/*TODO*///	#define	BITSWAP_2	BITSWAP(x,0xf,0xd,0xb,0x9,0xc,0xe,0x0,0x7,0x5,0x3,0x1,0x8,0xa,0x2,0x4,0x6);
/*TODO*///	#define	BITSWAP_3	BITSWAP(x,0x4,0x5,0x1,0x2,0xe,0xd,0x3,0xb,0xa,0x9,0x6,0x7,0x0,0x8,0xf,0xc);
/*TODO*///	
/*TODO*///			if		(i < 0x08000)	{	if ( (i | 0x248) != i ) {y = BITSWAP_0;} else {y = BITSWAP_1;} }
/*TODO*///			else if	(i < 0x10000)	{	if ( (i | 0x248) != i ) {y = BITSWAP_2;} else {y = BITSWAP_3;} }
/*TODO*///			else if	(i < 0x18000)	{	if ( (i | 0x248) != i ) {y = BITSWAP_0;} else {y = BITSWAP_1;} }
/*TODO*///			else if	(i < 0x20000)	{ y = BITSWAP_1; }
/*TODO*///			else 					{ y = BITSWAP_3; }
/*TODO*///	
/*TODO*///	#undef	BITSWAP_0
/*TODO*///	#undef	BITSWAP_1
/*TODO*///	#undef	BITSWAP_2
/*TODO*///	#undef	BITSWAP_3
/*TODO*///	
/*TODO*///			WRITE_WORD(&RAM[i],y);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_rodland = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		UBytePtr RAM = memory_region(REGION_GFX3);	// scroll 2
/*TODO*///	
/*TODO*///		/* Second half of the text gfx should be all FF's, not 0's
/*TODO*///		   (is this right ? Otherwise the subtitle is wrong) */
/*TODO*///		memset( &RAM[0x10000], 0xFF , 0x10000);
/*TODO*///	
/*TODO*///		rodland_rom_decode(0);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_rodlandj = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		UBytePtr RAM = memory_region(REGION_GFX3);	// scroll 2
/*TODO*///	
/*TODO*///		/* Second half of the text gfx should be all FF's, not 0's
/*TODO*///		   (is this right ? Otherwise the subtitle is wrong) */
/*TODO*///		memset( &RAM[0x10000], 0xFF , 0x10000);
/*TODO*///	} };
	
	
	/***************************************************************************
	
								[ Saint Dragon ]
	
				*** Press coin on startup to enter test mode ***
	
	interrupts:	1] rte	2] 620	3] 5e6
	
	***************************************************************************/
	
	static RomLoadPtr rom_stdragon = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "jsd-02.bin", 0x000000, 0x020000, 0xcc29ab19 );
		ROM_LOAD_ODD(  "jsd-01.bin", 0x000000, 0x020000, 0x67429a57 );
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
		ROM_LOAD_EVEN( "jsd-05.bin", 0x000000, 0x010000, 0x8c04feaa );
		ROM_LOAD_ODD(  "jsd-06.bin", 0x000000, 0x010000, 0x0bb62f3a );
	
		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
		ROM_LOAD( "jsd-11.bin", 0x000000, 0x020000, 0x2783b7b1 );
		ROM_LOAD( "jsd-12.bin", 0x020000, 0x020000, 0x89466ab7 );
		ROM_LOAD( "jsd-13.bin", 0x040000, 0x020000, 0x9896ae82 );
		ROM_LOAD( "jsd-14.bin", 0x060000, 0x020000, 0x7e8da371 );
	
		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
		ROM_LOAD( "jsd-15.bin", 0x000000, 0x020000, 0xe296bf59 );
		ROM_LOAD( "jsd-16.bin", 0x020000, 0x020000, 0xd8919c06 );
		ROM_LOAD( "jsd-17.bin", 0x040000, 0x020000, 0x4f7ad563 );
		ROM_LOAD( "jsd-18.bin", 0x060000, 0x020000, 0x1f4da822 );
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
		ROM_LOAD( "jsd-19.bin", 0x000000, 0x010000, 0x25ce807d );
	
		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
		ROM_LOAD( "jsd-20.bin", 0x000000, 0x020000, 0x2c6e93bb );
		ROM_LOAD( "jsd-21.bin", 0x020000, 0x020000, 0x864bcc61 );
		ROM_LOAD( "jsd-22.bin", 0x040000, 0x020000, 0x44fe2547 );
		ROM_LOAD( "jsd-23.bin", 0x060000, 0x020000, 0x6b010e1a );
	
		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
		ROM_LOAD( "jsd-09.bin", 0x000000, 0x020000, 0xe366bc5a );
		ROM_LOAD( "jsd-10.bin", 0x020000, 0x020000, 0x4a8f4fe6 );
	
		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
		ROM_LOAD( "jsd-07.bin", 0x000000, 0x020000, 0x6a48e979 );
		ROM_LOAD( "jsd-08.bin", 0x020000, 0x020000, 0x40704962 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
		ROM_LOAD( "prom.14m",    0x0000, 0x0200, 0x1d877538 );
	ROM_END(); }}; 
	
	static InputPortPtr input_ports_stdragon = new InputPortPtr(){ public void handler() { 
		COINS();						/* IN0 0x80001.b */
	//	fire	fire
		JOY_2BUTTONS(IPF_PLAYER1);	/* IN1 0x80003.b */
		RESERVE();						/* IN2 0x80004.b */
		JOY_2BUTTONS(IPF_PLAYER2);	/* IN3 0x80005.b */
	
		PORT_START(); 			/* IN4 0x80006.b */
		COINAGE_6BITS_2();
		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x40, DEF_STR( "On") );
		PORT_DIPNAME( 0x80, 0x80, "Unknown 1-7" );// used?
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
		PORT_START(); 			/* IN5 0x80007.b */
		PORT_DIPNAME( 0x03, 0x03, DEF_STR( "Lives") );
		PORT_DIPSETTING(    0x02, "2" );
		PORT_DIPSETTING(    0x03, "3" );
		PORT_DIPSETTING(    0x01, "4" );
		PORT_DIPSETTING(    0x00, "5" );
		PORT_DIPNAME( 0x04, 0x04, "Unknown 2-2" );// unused?
		PORT_DIPSETTING(    0x04, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x08, 0x08, "Unknown 2-3" );// unused?
		PORT_DIPSETTING(    0x08, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x30, 0x30, "?Difficulty?" );
		PORT_DIPSETTING(    0x30, "3" );
		PORT_DIPSETTING(    0x20, "2" );
		PORT_DIPSETTING(    0x10, "1" );
		PORT_DIPSETTING(    0x00, "0" );
		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Cabinet") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Upright") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Cocktail") );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
	INPUT_PORTS_END(); }}; 
	
	public static InitDriverPtr init_stdragon = new InitDriverPtr() { public void handler() 
	{
		UBytePtr RAM;
	
		phantasm_rom_decode(0);
	
		RAM  = new UBytePtr(memory_region(REGION_CPU1));
		RAM.WRITE_WORD(0x00045e,0x0098);	// protection
	} };
	
	
	/***************************************************************************
	
									[ Soldam ]
	
	(Japan version)
	f00c2.l	*** score/10 (BCD) ***
	
	The country code is at ROM address $3a9d, copied to RAM address
	f0025: 0 = japan, 1 = USA. Change f0025 to 1 to have all the
	text in english.
	
	***************************************************************************/
	
	static RomLoadPtr rom_soldamj = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x60000, REGION_CPU1 );	/* Main CPU Code */
		ROM_LOAD_EVEN( "soldam2.bin", 0x000000, 0x020000, 0xc73d29e4 );
		ROM_LOAD_ODD(  "soldam1.bin", 0x000000, 0x020000, 0xe7cb0c20 );
		ROM_LOAD_EVEN( "soldam3.bin", 0x040000, 0x010000, 0xc5382a07 );
		ROM_LOAD_ODD(  "soldam4.bin", 0x040000, 0x010000, 0x1df7816f );
	
		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
		ROM_LOAD_EVEN( "soldam5.bin", 0x000000, 0x010000, 0xd1019a67 );
		ROM_LOAD_ODD(  "soldam6.bin", 0x000000, 0x010000, 0x3ed219b4 );
	
		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
		ROM_LOAD( "soldam14.bin", 0x000000, 0x080000, 0x26cea54a );
	
		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
		ROM_LOAD( "soldam18.bin", 0x000000, 0x080000, 0x7d8e4712 );
	
		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
		ROM_LOAD( "soldam19.bin", 0x000000, 0x020000, 0x38465da1 );
	
		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
		ROM_LOAD( "soldam23.bin", 0x000000, 0x080000, 0x0ca09432 );
	
		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
		ROM_LOAD( "soldam10.bin", 0x000000, 0x040000, 0x8d5613bf );
	
		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
		ROM_LOAD( "soldam8.bin",  0x000000, 0x040000, 0xfcd36019 );
	
		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
		ROM_LOAD( "prom",    0x0000, 0x0200, 0x00000000 );
	ROM_END(); }}; 
	
	static InputPortPtr input_ports_soldamj = new InputPortPtr(){ public void handler() { 
		COINS();						/* IN0 0x80001.b */
		//	turn	turn	(3rd button is shown in service mode, but seems unused)
		JOY_2BUTTONS(IPF_PLAYER1);	/* IN1 0x80003.b */
		RESERVE();						/* IN2 0x80004.b */
		JOY_2BUTTONS(IPF_PLAYER2);	/* IN3 0x80005.b */
	
		PORT_START(); 			/* IN4 0x80006.b */
		COINAGE_6BITS_2();
		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x40, DEF_STR( "On") );
		PORT_SERVICE( 0x80, IP_ACTIVE_LOW );
	
		PORT_START(); 			/* IN5 0x80007.b */
		PORT_DIPNAME( 0x03, 0x03, DEF_STR( "Difficulty") );
		PORT_DIPSETTING(    0x00, "Easy"   );
		PORT_DIPSETTING(    0x03, "Normal" );
		PORT_DIPSETTING(    0x02, "Hard"   );
		PORT_DIPSETTING(    0x01, "Hadest" );
		PORT_DIPNAME( 0x0c, 0x0c, "Games To Play (Vs)" );
		PORT_DIPSETTING(    0x00, "1" );
		PORT_DIPSETTING(    0x0c, "2" );
		PORT_DIPSETTING(    0x08, "3" );
		PORT_DIPSETTING(    0x04, "4" );
		PORT_DIPNAME( 0x10, 0x10, "Allow Continue" );
		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x10, DEF_STR( "On") );
		PORT_DIPNAME( 0x20, 0x20, "Credits To Start (Vs)" );
		PORT_DIPSETTING(    0x20, "1" );
		PORT_DIPSETTING(    0x00, "2" );
		PORT_DIPNAME( 0x40, 0x40, "Credits To Continue (Vs)" );
		PORT_DIPSETTING(    0x40, "1" );
		PORT_DIPSETTING(    0x00, "2" );
		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
	INPUT_PORTS_END(); }}; 
	
	public static ReadHandlerPtr soldamj_spriteram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return spriteram.READ_WORD(offset);
	} };
        
	public static WriteHandlerPtr soldamj_spriteram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (offset < 0x800)	COMBINE_WORD_MEM(spriteram,offset,data);
	} };
	
	public static InitDriverPtr init_soldam = new InitDriverPtr() { public void handler() 
	{
		astyanax_rom_decode(0);
	
		/* Sprite RAM is mirrored. Why? */
		install_mem_read_handler (0, 0x8c000, 0x8cfff, soldamj_spriteram_r);
		install_mem_write_handler(0, 0x8c000, 0x8cfff, soldamj_spriteram_w);
	} };
	
	
	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///								[ Takeda Shingen ]
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	static RomLoadPtr rom_tshingen = new RomLoadPtr(){ public void handler(){ 
/*TODO*///		ROM_REGION( 0x40000, REGION_CPU1 );	/* Main CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "takeda2.bin", 0x000000, 0x020000, 0x6ddfc9f3 );
/*TODO*///		ROM_LOAD_ODD(  "takeda1.bin", 0x000000, 0x020000, 0x1afc6b7d );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x20000, REGION_CPU2 );	/* Sound CPU Code */
/*TODO*///		ROM_LOAD_EVEN( "takeda5.bin", 0x000000, 0x010000, 0xfbdc51c0 );
/*TODO*///		ROM_LOAD_ODD(  "takeda6.bin", 0x000000, 0x010000, 0x8fa65b69 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* Scroll 0 */
/*TODO*///		ROM_LOAD( "takeda11.bin", 0x000000, 0x020000, 0xbf0b40a6 );
/*TODO*///		ROM_LOAD( "takeda12.bin", 0x020000, 0x020000, 0x07987d89 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* Scroll 1 */
/*TODO*///		ROM_LOAD( "takeda15.bin", 0x000000, 0x020000, 0x4c316b79 );
/*TODO*///		ROM_LOAD( "takeda16.bin", 0x020000, 0x020000, 0xceda9dd6 );
/*TODO*///		ROM_LOAD( "takeda17.bin", 0x040000, 0x020000, 0x3d4371dc );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x020000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* Scroll 2 */
/*TODO*///		ROM_LOAD( "takeda19.bin", 0x000000, 0x010000, 0x2ca2420d );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x080000, REGION_GFX4 | REGIONFLAG_DISPOSE );/* Sprites */
/*TODO*///		ROM_LOAD( "takeda20.bin", 0x000000, 0x020000, 0x1bfd636f );
/*TODO*///		ROM_LOAD( "takeda21.bin", 0x020000, 0x020000, 0x12fb006b );
/*TODO*///		ROM_LOAD( "takeda22.bin", 0x040000, 0x020000, 0xb165b6ae );
/*TODO*///		ROM_LOAD( "takeda23.bin", 0x060000, 0x020000, 0x37cb9214 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND1 );	/* Samples */
/*TODO*///		ROM_LOAD( "takeda9.bin",  0x000000, 0x020000, 0xdb7f3f4f );
/*TODO*///		ROM_LOAD( "takeda10.bin", 0x020000, 0x020000, 0xc9959d71 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x040000, REGION_SOUND2 );	/* Samples */
/*TODO*///		ROM_LOAD( "takeda8.bin",  0x000000, 0x040000, 0xdde779d2 );
/*TODO*///	
/*TODO*///		ROM_REGION( 0x0200, REGION_PROMS );	/* Priority PROM */
/*TODO*///		ROM_LOAD( "prom",    0x0000, 0x0200, 0x00000000 );
/*TODO*///	ROM_END(); }}; 
/*TODO*///	
/*TODO*///	static InputPortPtr input_ports_tshingen = new InputPortPtr(){ public void handler() { 
/*TODO*///		COINS						/* IN0 0x80001.b */
/*TODO*///		// sword_left	sword_right		jump
/*TODO*///		JOY_3BUTTONS(IPF_PLAYER1)	/* IN1 0x80003.b */
/*TODO*///		RESERVE						/* IN2 0x80004.b */
/*TODO*///		JOY_3BUTTONS(IPF_PLAYER2)	/* IN3 0x80005.b */
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN4 0x80006.b */
/*TODO*///		COINAGE_6BITS
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, DEF_STR( "Demo_Sounds") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "On") ); )
/*TODO*///		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Unknown") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "On") ); )
/*TODO*///	
/*TODO*///		PORT_START(); 			/* IN5 0x80007.b */
/*TODO*///		PORT_DIPNAME( 0x03, 0x01, DEF_STR( "Lives") );
/*TODO*///		PORT_DIPSETTING(    0x03, "2" );
/*TODO*///		PORT_DIPSETTING(    0x01, "3" );
/*TODO*///		PORT_DIPSETTING(    0x02, "4" );
/*TODO*///		PORT_BITX( 0,       0x00, IPT_DIPSWITCH_SETTING | IPF_CHEAT, "Inifinite", IP_KEY_NONE, IP_JOY_NONE );
/*TODO*///		PORT_DIPNAME( 0x0c, 0x0c, DEF_STR( "Bonus_Life") );
/*TODO*///		PORT_DIPSETTING(    0x0c, "20k" );
/*TODO*///		PORT_DIPSETTING(    0x04, "30k" );
/*TODO*///		PORT_DIPSETTING(    0x08, "40k" );
/*TODO*///		PORT_DIPSETTING(    0x00, "50k" );
/*TODO*///		PORT_DIPNAME( 0x30, 0x10, DEF_STR( "Difficulty") ); // damage when hit
/*TODO*///		PORT_DIPSETTING(    0x30, "Easy"    );// 0
/*TODO*///		PORT_DIPSETTING(    0x10, "Normal"  );// 1
/*TODO*///		PORT_DIPSETTING(    0x20, "Hard"    );// 2
/*TODO*///		PORT_DIPSETTING(    0x00, "Hardest" );// 3
/*TODO*///		PORT_DIPNAME( 0x40, 0x40, "Allow Continue" );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x40, DEF_STR( "On") ); )
/*TODO*///		PORT_DIPNAME( 0x80, 0x80, DEF_STR( "Flip_Screen") );
/*TODO*///		PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
/*TODO*///		PORT_DIPSETTING(    0x00, DEF_STR( "On") ); )
/*TODO*///	
/*TODO*///	INPUT_PORTS_END(); }}; 
/*TODO*///	
/*TODO*///	static public static InitDriverPtr init_tshingen = new InitDriverPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		phantasm_rom_decode(0);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///									Game drivers
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	
/*TODO*///	public static GameDriver driver_64street	   = new GameDriver("1991"	,"64street"	,"megasys1.java"	,rom_64street,null	,machine_driver_64street	,input_ports_64street	,init_64street	,ROT0	,	"Jaleco", "64th. Street - A Detective Story (World)" )
/*TODO*///	public static GameDriver driver_64streej	   = new GameDriver("1991"	,"64streej"	,"megasys1.java"	,rom_64streej,driver_64street	,machine_driver_64street	,input_ports_64street	,init_64street	,ROT0	,	"Jaleco", "64th. Street - A Detective Story (Japan)" )
	public static GameDriver driver_astyanax	   = new GameDriver("1989"	,"astyanax"	,"megasys1.java"	,rom_astyanax,null	,machine_driver_astyanax	,input_ports_astyanax	,init_astyanax	,ROT0_16BIT	,	"Jaleco", "The Astyanax" );
/*TODO*///	public static GameDriver driver_lordofk	   = new GameDriver("1989"	,"lordofk"	,"megasys1.java"	,rom_lordofk,driver_astyanax	,machine_driver_astyanax	,input_ports_astyanax	,init_astyanax	,ROT0_16BIT	,	"Jaleco", "The Lord of King (Japan)" )
	public static GameDriver driver_avspirit	   = new GameDriver("1991"	,"avspirit"	,"megasys1.java"	,rom_avspirit,null	,machine_driver_avspirit	,input_ports_avspirit	,init_avspirit	,ROT0	,	"Jaleco", "Avenging Spirit" );
	public static GameDriver driver_phantasm	   = new GameDriver("1990"	,"phantasm"	,"megasys1.java"	,rom_phantasm,driver_avspirit	,machine_driver_phantasm	,input_ports_phantasm	,init_phantasm	,ROT0	,	"Jaleco", "Phantasm (Japan)" );
/*TODO*///	public static GameDriver driver_bigstrik	   = new GameDriver("1992"	,"bigstrik"	,"megasys1.java"	,rom_bigstrik,null	,machine_driver_bigstrik	,input_ports_bigstrik	,init_bigstrik	,ROT0	,	"Jaleco", "Big Striker" )
/*TODO*///	public static GameDriver driver_chimerab	   = new GameDriver("1993"	,"chimerab"	,"megasys1.java"	,rom_chimerab,null	,machine_driver_chimerab	,input_ports_chimerab	,init_chimerab	,ROT0	,	"Jaleco", "Chimera Beast" )
/*TODO*///	public static GameDriver driver_cybattlr	   = new GameDriver("1993"	,"cybattlr"	,"megasys1.java"	,rom_cybattlr,null	,machine_driver_cybattlr	,input_ports_cybattlr	,init_cybattlr	,ROT90	,	"Jaleco", "Cybattler" )
/*TODO*///	public static GameDriver driver_edf	   = new GameDriver("1991"	,"edf"	,"megasys1.java"	,rom_edf,null	,machine_driver_edf	,input_ports_edf	,init_edf	,ROT0	,	"Jaleco", "Earth Defense Force" )
	public static GameDriver driver_hachoo	   = new GameDriver("1989"	,"hachoo"	,"megasys1.java"	,rom_hachoo,null	,machine_driver_hachoo	,input_ports_hachoo	,init_hachoo	,ROT0	,	"Jaleco", "Hachoo!" );
	public static GameDriver driver_iganinju	   = new GameDriver("1988"	,"iganinju"	,"megasys1.java"	,rom_iganinju,null	,machine_driver_iganinju	,input_ports_iganinju	,init_iganinju	,ROT0	,	"Jaleco", "Iga Ninjyutsuden (Japan)" );
	public static GameDriver driver_kickoff	   = new GameDriver("1988"	,"kickoff"	,"megasys1.java"	,rom_kickoff,null	,machine_driver_kickoff	,input_ports_kickoff	,null	,ROT0	,	"Jaleco", "Kick Off (Japan)" );
	public static GameDriver driver_lomakai	   = new GameDriver("1988"	,"lomakai"	,"megasys1.java"	,rom_lomakai,null	,machine_driver_lomakai	,input_ports_lomakai	,null	,ROT0	,	"Jaleco", "Legend of Makai (World)" );
/*TODO*///	public static GameDriver driver_makaiden	   = new GameDriver("1988"	,"makaiden"	,"megasys1.java"	,rom_makaiden,driver_lomakai	,machine_driver_lomakai	,input_ports_lomakai	,null	,ROT0	,	"Jaleco", "Makai Densetsu (Japan)" )
        public static GameDriver driver_p47	   = new GameDriver("1988"	,"p47"	,"megasys1.java"	,rom_p47,null	,machine_driver_p47	,input_ports_p47	,null	,ROT0	,	"Jaleco", "P-47 - The Phantom Fighter (World)" );
	public static GameDriver driver_p47j	   = new GameDriver("1988"	,"p47j"	,"megasys1.java"	,rom_p47j,driver_p47	,machine_driver_p47	,input_ports_p47	,null	,ROT0	,	"Jaleco", "P-47 - The Freedom Fighter (Japan)" );
/*TODO*///	public static GameDriver driver_peekaboo	   = new GameDriver("1993"	,"peekaboo"	,"megasys1.java"	,rom_peekaboo,null	,machine_driver_peekaboo	,input_ports_peekaboo	,null	,ROT0	,	"Jaleco", "Peek-a-Boo!" )
/*TODO*///	public static GameDriver driver_plusalph	   = new GameDriver("1989"	,"plusalph"	,"megasys1.java"	,rom_plusalph,null	,machine_driver_plusalph	,input_ports_plusalph	,init_plusalph	,ROT270	,	"Jaleco", "Plus Alpha" )
/*TODO*///	public static GameDriver driver_rodland	   = new GameDriver("1990"	,"rodland"	,"megasys1.java"	,rom_rodland,null	,machine_driver_rodland	,input_ports_rodland	,init_rodland	,ROT0	,	"Jaleco", "RodLand (World)" )
/*TODO*///	public static GameDriver driver_rodlandj	   = new GameDriver("1990"	,"rodlandj"	,"megasys1.java"	,rom_rodlandj,driver_rodland	,machine_driver_rodland	,input_ports_rodland	,init_rodlandj	,ROT0	,	"Jaleco", "RodLand (Japan)" )
	public static GameDriver driver_stdragon	   = new GameDriver("1989"	,"stdragon"	,"megasys1.java"	,rom_stdragon,null	,machine_driver_stdragon	,input_ports_stdragon	,init_stdragon	,ROT0	,	"Jaleco", "Saint Dragon" );
/*TODO*///	public static GameDriver driver_soldamj	   = new GameDriver("1992"	,"soldamj"	,"megasys1.java"	,rom_soldamj,null	,machine_driver_soldamj	,input_ports_soldamj	,init_soldam	,ROT0	,	"Jaleco", "Soldam (Japan)" );
/*TODO*///	public static GameDriver driver_tshingen	   = new GameDriver("1988"	,"tshingen"	,"megasys1.java"	,rom_tshingen,null	,machine_driver_tshingen	,input_ports_tshingen	,init_tshingen	,ROT0	,	"Jaleco", "Takeda Shingen (Japan)" )
}
