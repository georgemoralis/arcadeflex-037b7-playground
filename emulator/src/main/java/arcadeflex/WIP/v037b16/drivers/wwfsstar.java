/*******************************************************************************
 WWF Superstars (C) 1989 Technos Japan  (drivers/wwfsstar.c)
********************************************************************************
 driver by David Haywood

 Special Thanks to:

 Richard Bush & the Rest of the Raine Team - Raine's WWF Superstars driver on
 which most of this driver has been based.

********************************************************************************

 Hardware:

 Primary CPU : 68000 - Clock Speed Unknown

 Sound CPUs : Z80

 Sound Chips : YM2151, M6295

 3 Layers from now on if mentioned will be refered to as

 BG0 - Background
 SPR - Sprites
 FG0 - Foreground / Text Layer

********************************************************************************

 Change Log:
 18 Jun 2001 | Changed Interrupt Function .. its not fully understood whats
			 | is meant to be going on ..
 15 Jun 2001 | Cleaned up Sprite Drawing a bit, correcting some clipping probs,
			 | mapped DSW's
 15 Jun 2001 | First Submission of Driver,
 14 Jun 2001 | Started Driver, using Raine Source as a reference for getting it
			 | up and running

********************************************************************************

 Notes:

 - Scrolling *might* be slightly off, i'm not sure
 - Maybe Palette Marking could be Improved
 - There seems to be a bad tile during one of Macho Man's Moves (where does
   it come from?)
 - How should the interrupts work .. the current way is a bit of a guess based
   on how the game runs, if standard IPT_VBLANK and 2 different interrupts per
   frame are used the game either runs well and hangs on a game 'Time Out' end
   of match scenario, or the other way round the game is very sluggish and
   non-responsive to controls.  It seems both interrupts must happen during the
   vblank period or something.

*******************************************************************************/

/*
 * ported to v0.37b16
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b16.drivers;

import static arcadeflex.WIP.v037b16.vidhrdw.wwfsstar.*;
import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.ptrLib.UShortPtr;
import static arcadeflex.v037b7.cpu.z80.z80H.Z80_NMI_INT;
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
import static arcadeflex.v037b7.sound.mixerH.*;
import static arcadeflex.v037b7.sound.okim6295.*;
import static arcadeflex.v037b7.sound.okim6295H.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.v036.cpu.m68000.m68000H.*;

public class wwfsstar
{
	static int vbl;
	
	
	/*******************************************************************************
	 Memory Maps
	********************************************************************************
	 Pretty Straightforward
	
	 some unknown writes in the 0x180000 region
	*******************************************************************************/
	
	public static /*UShortPtr*/ UBytePtr fg0_videoram=new UBytePtr(), bg0_videoram=new UBytePtr();
        
        /*******************************************************************************
	 Read / Write Handlers
	********************************************************************************
	 as used by the above memory map
	*******************************************************************************/
	public static WriteHandlerPtr wwfsstar_soundwrite = new WriteHandlerPtr() {
            @Override
            public void handler(int offset, int data) {
                soundlatch_w.handler(1,data & 0xff);
		cpu_cause_interrupt( 1, Z80_NMI_INT );
            }
        };
	        
        static ReadHandlerPtr input_port_2_word_r_cust = new ReadHandlerPtr() {
            @Override
            public int handler(int offset) {
                return vbl | (readinputport(2) & 0xfe);
            }
        };
        
        public static int wwfsstar_scrollx, wwfsstar_scrolly; /* used in (vidhrdw/wwfsstar.c) */
	
	static WriteHandlerPtr wwfsstar_scrollwrite = new WriteHandlerPtr() {
            @Override
            public void handler(int offset, int data) {
		switch (offset)
		{
			case 0x00:
			wwfsstar_scrollx = data;
			break;
			case 0x01:
			wwfsstar_scrolly = data;
			break;
		}
            }
        };
	
	static MemoryReadAddress readmem[] =
	{
		new MemoryReadAddress( 0x000000, 0x03ffff, MRA_ROM ),	/* Rom */
		new MemoryReadAddress( 0x080000, 0x080fff, MRA_RAM ),	/* FG0 Ram */
		new MemoryReadAddress( 0x0c0000, 0x0c0fff, MRA_RAM ),	/* BG0 Ram */
		new MemoryReadAddress( 0x100000, 0x1003ff, MRA_RAM ),	/* SPR Ram */
		new MemoryReadAddress( 0x180000, 0x180001, input_port_3_r ),	/* DSW0 */
		new MemoryReadAddress( 0x180002, 0x180003, input_port_4_r ),	/* DSW1 */
		new MemoryReadAddress( 0x180004, 0x180005, input_port_0_r ),	/* CTRLS0 */
		new MemoryReadAddress( 0x180006, 0x180007, input_port_1_r ),	/* CTRLS1 */
		new MemoryReadAddress( 0x180008, 0x180009, input_port_2_word_r_cust ),	/* MISC */
		new MemoryReadAddress( 0x1c0000, 0x1c3fff, MRA_RAM ),	/* Work Ram */
                new MemoryReadAddress( -1 )
	};
	
	static MemoryWriteAddress writemem[] =
	{
		new MemoryWriteAddress( 0x000000, 0x07ffff, MWA_ROM ),	/* Rom */
		new MemoryWriteAddress( 0x080000, 0x080fff, wwfsstar_fg0_videoram_w, fg0_videoram ),	/* FG0 Ram */
		new MemoryWriteAddress( 0x0c0000, 0x0c0fff, wwfsstar_bg0_videoram_w, bg0_videoram ),	/* BG0 Ram */
		new MemoryWriteAddress( 0x100000, 0x1003ff, MWA_RAM, spriteram ),	/* SPR Ram */
		new MemoryWriteAddress( 0x140000, 0x140fff, paletteram_xxxxBBBBGGGGRRRR_word_w, paletteram ),
		new MemoryWriteAddress( 0x180004, 0x180007, wwfsstar_scrollwrite ),
		new MemoryWriteAddress( 0x180008, 0x180009, wwfsstar_soundwrite ),
		new MemoryWriteAddress( 0x1c0000, 0x2c3fff, MWA_RAM ),	/* Work Ram */
                new MemoryWriteAddress( -1 )
	};
	
	public static MemoryReadAddress readmem_sound[]={
		//new MemoryReadAddress(MEMPORT_MARKER, MEMPORT_DIRECTION_READ | MEMPORT_TYPE_MEM | MEMPORT_WIDTH_8),	new Memory_ReadAddress( 0x0000, 0x7fff, MRA_ROM ),
		new MemoryReadAddress( 0x8000, 0x87ff, MRA_RAM ),
		new MemoryReadAddress( 0x8801, 0x8801, YM2151_status_port_0_r ),
		new MemoryReadAddress( 0x9800, 0x9800, OKIM6295_status_0_r ),
		new MemoryReadAddress( 0xa000, 0xa000, soundlatch_r ),
		new MemoryReadAddress( -1 )
	};
	public static MemoryWriteAddress writemem_sound[]={
		//new Memory_WriteAddress(MEMPORT_MARKER, MEMPORT_DIRECTION_WRITE | MEMPORT_TYPE_MEM | MEMPORT_WIDTH_8),	new Memory_WriteAddress( 0x0000, 0x7fff, MWA_ROM ),
		new MemoryWriteAddress( 0x8000, 0x87ff, MWA_RAM ),
		new MemoryWriteAddress( 0x8800, 0x8800, YM2151_register_port_0_w ),
		new MemoryWriteAddress( 0x8801, 0x8801, YM2151_data_port_0_w ),
		new MemoryWriteAddress( 0x9800, 0x9800, OKIM6295_data_0_w ),
		new MemoryWriteAddress( -1 )
	};
	
	
	/*******************************************************************************
	 Input Ports
	********************************************************************************
	 2 Sets of Player Controls
	 A Misc Inputs inc. Coins
	 2 Sets of Dipswitches
	*******************************************************************************/
	
	static InputPortPtr input_ports_wwfsstar = new InputPortPtr(){ public void handler() { 
	PORT_START(); 
		PORT_BIT( 0x0001, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT );	PORT_BIT( 0x0002, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT );	PORT_BIT( 0x0004, IP_ACTIVE_LOW, IPT_JOYSTICK_UP );	PORT_BIT( 0x0008, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN );	PORT_BIT( 0x0010, IP_ACTIVE_LOW, IPT_BUTTON1 );	PORT_BIT( 0x0020, IP_ACTIVE_LOW, IPT_BUTTON2 );	PORT_BIT( 0x0040, IP_ACTIVE_LOW, IPT_BUTTON3 );	PORT_BIT( 0x0080, IP_ACTIVE_LOW, IPT_START1 );
		PORT_START(); 
		PORT_BIT( 0x0001, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_PLAYER2 );	PORT_BIT( 0x0002, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT | IPF_PLAYER2 );	PORT_BIT( 0x0004, IP_ACTIVE_LOW, IPT_JOYSTICK_UP | IPF_PLAYER2 );	PORT_BIT( 0x0008, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN | IPF_PLAYER2 );	PORT_BIT( 0x0010, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2 );	PORT_BIT( 0x0020, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2 );	PORT_BIT( 0x0040, IP_ACTIVE_LOW, IPT_BUTTON3 | IPF_PLAYER2 );	PORT_BIT( 0x0080, IP_ACTIVE_LOW, IPT_START2 );
		PORT_START(); 
		PORT_BIT( 0x0001, IP_ACTIVE_LOW, IPT_VBLANK ); /* IPT_VBLANK is ignored for custom indicator */
		PORT_BIT( 0x0002, IP_ACTIVE_LOW, IPT_UNKNOWN );	PORT_BIT( 0x0004, IP_ACTIVE_LOW, IPT_COIN1 );	PORT_BIT( 0x0008, IP_ACTIVE_LOW, IPT_COIN2 );	PORT_BIT( 0x0010, IP_ACTIVE_LOW, IPT_UNKNOWN );	PORT_BIT( 0x0020, IP_ACTIVE_LOW, IPT_UNKNOWN );	PORT_BIT( 0x0040, IP_ACTIVE_LOW, IPT_UNKNOWN );	PORT_BIT( 0x0080, IP_ACTIVE_LOW, IPT_UNKNOWN );
		PORT_START(); 
		/* Note: This is what Raine says but it doesn't appear to be correct? */
		PORT_DIPNAME( 0x07, 0x07, DEF_STR( "Coin_A") );
		PORT_DIPSETTING(	0x00,  DEF_STR( "4C_1C") ); 
		PORT_DIPSETTING(	0x01,  DEF_STR( "3C_1C") ); 
		PORT_DIPSETTING(	0x02,  DEF_STR( "2C_1C") ); 
		PORT_DIPSETTING(	0x07,  DEF_STR( "1C_1C") ); 
		PORT_DIPSETTING(	0x06,  DEF_STR( "1C_2C") ); 
		PORT_DIPSETTING(	0x05,  DEF_STR( "1C_3C") ); 
		PORT_DIPSETTING(	0x04,  DEF_STR( "1C_4C") ); 
		PORT_DIPSETTING(	0x03,  DEF_STR( "1C_5C") ); 
	
		PORT_DIPNAME( 0x38, 0x38, DEF_STR( "Coin_B") );
		PORT_DIPSETTING(	0x00,  DEF_STR( "4C_1C") ); 
		PORT_DIPSETTING(	0x08,  DEF_STR( "3C_1C") ); 
		PORT_DIPSETTING(	0x10,  DEF_STR( "2C_1C") ); 
		PORT_DIPSETTING(	0x38,  DEF_STR( "1C_1C") ); 
		PORT_DIPSETTING(	0x30,  DEF_STR( "1C_2C") ); 
		PORT_DIPSETTING(	0x28,  DEF_STR( "1C_3C") ); 
		PORT_DIPSETTING(	0x20,  DEF_STR( "1C_4C") ); 
		PORT_DIPSETTING(	0x18,  DEF_STR( "1C_5C") ); 
		PORT_DIPNAME( 0x40, 0x00, DEF_STR( "Cabinet") );
		PORT_DIPSETTING(    0x00, DEF_STR( "Upright") );
		PORT_DIPSETTING(    0x40, DEF_STR( "Cocktail") );
		PORT_DIPNAME( 0x80, 0x80, "Screen Invert?" );	PORT_DIPSETTING(    0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(    0x00, DEF_STR( "On") );
	
		PORT_START(); 
		PORT_DIPNAME( 0x03, 0x03, DEF_STR( "Difficulty") );
		PORT_DIPSETTING(	0x02, "Easy" );	PORT_DIPSETTING(	0x03, "Normal" );	PORT_DIPSETTING(	0x01, "Hard" );	PORT_DIPSETTING(	0x00, "Hardest" );	PORT_DIPNAME( 0x04, 0x04, DEF_STR( "Demo_Sounds") );
		PORT_DIPSETTING(	0x00, DEF_STR( "Off") );
		PORT_DIPSETTING(	0x04, DEF_STR( "On") );
		PORT_DIPNAME( 0x08, 0x08, "Super Techniques" );	PORT_DIPSETTING(	0x08, "Normal" );	PORT_DIPSETTING(	0x00, "Hard" );	PORT_DIPNAME( 0x30, 0x30, "Game Timer Change" );	PORT_DIPSETTING(	0x00, "-30" );	PORT_DIPSETTING(	0x10, "-15" );	PORT_DIPSETTING(	0x30, "0" );	PORT_DIPSETTING(	0x20, "+15" );	PORT_DIPNAME( 0x40, 0x00, "3 Button Mode" );	PORT_DIPSETTING(	0x40, DEF_STR( "Off") );
		PORT_DIPSETTING(	0x00, DEF_STR( "On") );
		PORT_DIPNAME( 0x80, 0x80, "Game Clear" );	PORT_DIPSETTING(	0x80, DEF_STR( "Off") );
		PORT_DIPSETTING(	0x00, DEF_STR( "On") );
	INPUT_PORTS_END(); }}; 
	
	/*******************************************************************************
	 Graphic Decoding
	********************************************************************************
	 Tiles are decoded the same as Double Dragon, Strangely Enough another
	 Technos Game ;)
	*******************************************************************************/
	
	static GfxLayout tiles8x8_layout = new GfxLayout
	(
		8,8,
		RGN_FRAC(1,1),
		4,
		new int[] { 0, 2, 4, 6 },
		new int[] { 1, 0, 8*8+1, 8*8+0, 16*8+1, 16*8+0, 24*8+1, 24*8+0 },
		new int[] { 0*8, 1*8, 2*8, 3*8, 4*8, 5*8, 6*8, 7*8 },
		32*8
	);
	
	static GfxLayout tiles16x16_layout = new GfxLayout
	(
		16,16,
		RGN_FRAC(1,2),
		4,
		new int[] { RGN_FRAC(1,2)+0, RGN_FRAC(1,2)+4, 0, 4 },
		new int[] { 3, 2, 1, 0, 16*8+3, 16*8+2, 16*8+1, 16*8+0,
			  32*8+3, 32*8+2, 32*8+1, 32*8+0, 48*8+3, 48*8+2, 48*8+1, 48*8+0 },
		new int[] { 0*8, 1*8, 2*8, 3*8, 4*8, 5*8, 6*8, 7*8,
			  8*8, 9*8, 10*8, 11*8, 12*8, 13*8, 14*8, 15*8 },
		64*8
	);
	
	static GfxDecodeInfo gfxdecodeinfo[] =
	{
		new GfxDecodeInfo( REGION_GFX1, 0, tiles8x8_layout,    0, 16 ),	/* colors   0-255 */
		new GfxDecodeInfo( REGION_GFX2, 0, tiles16x16_layout,   128, 16 ),	/* colors   128-383 */
		new GfxDecodeInfo( REGION_GFX3, 0, tiles16x16_layout,   256, 8 ),	/* colors   256-383 */
		new GfxDecodeInfo( -1 )
	};
	
	/*******************************************************************************
	 Interrupt Function
	********************************************************************************
	 2 different interrupts.. it seems they must both happen during the vblank
	 period or something for the game to function correctly ? (see notes at top of
	 file)
	*******************************************************************************/
	
	public static InterruptPtr wwfsstar_interrupt = new InterruptPtr() { public int handler()  {
		if( cpu_getiloops() == 0 ){
			vbl = 1;
		}
	
		if( cpu_getiloops() == 240 ){
			vbl = 0;
			return MC68000_IRQ_5;
		}
	
		if( cpu_getiloops() == 250 ){
			 return MC68000_IRQ_6;
		}
	
		return MC68000_INT_NONE;
	} };
	
	/*******************************************************************************
	 Sound Stuff..
	********************************************************************************
	 Straight from Ddragon 3
	*******************************************************************************/
	
	static WriteYmHandlerPtr wwfsstar_ymirq_handler = new WriteYmHandlerPtr() {
            @Override
            public void handler(int irq) {
                cpu_set_irq_line( 1, 0 , irq!=0 ? ASSERT_LINE : CLEAR_LINE );
            }
        };
        
	static YM2151interface ym2151_interface = new YM2151interface
	(
		1,			/* 1 chip */
		3579545,	/* Guess */
		new int[] { YM3012_VOL(45,MIXER_PAN_LEFT,45,MIXER_PAN_RIGHT) },
		new WriteYmHandlerPtr[] { wwfsstar_ymirq_handler }
	);
	
	static OKIM6295interface okim6295_interface = new OKIM6295interface
	(
		1,				/* 1 chip */
		new int[] { 8500 },		/* frequency (Hz) */
		new int[] { REGION_SOUND1 },	/* memory region */
		new int[] { 47 }
	);
	
	/*******************************************************************************
	 Machine Driver(s)
	********************************************************************************
	 Clock Speeds are currently Unknown
	*******************************************************************************/
	
	static MachineDriver machine_driver_wwfsstar = new MachineDriver
	(
		/* basic machine hardware */
		new MachineCPU[] {
	
			new MachineCPU(
				CPU_M68000,
				12000000,	/* unknown */
				readmem,writemem,null,null,
				wwfsstar_interrupt,262
			),
			new MachineCPU(
				CPU_Z80 | CPU_AUDIO_CPU,
				3579545,	/* unknown */
				readmem_sound, writemem_sound,null,null,
				ignore_interrupt,0
			),
	
		},
		60, DEFAULT_60HZ_VBLANK_DURATION,
		1,
		null,
	
		/* video hardware */
		32*8, 32*8, new rectangle( 0*8, 32*8-1, 0*8, 32*8-1 ),
		gfxdecodeinfo,
		24*16, 24*16,
		null,
	
		VIDEO_TYPE_RASTER | VIDEO_MODIFIES_PALETTE,
		null,
		wwfsstar_vh_start,
		null,
		wwfsstar_vh_screenrefresh,
	
		/* sound hardware */
		SOUND_SUPPORTS_STEREO,0,0,0,
		new MachineSound[] {
			new MachineSound(
				SOUND_YM2151,
				ym2151_interface
			),
			new MachineSound(
				SOUND_OKIM6295,
				okim6295_interface
			)
		}
	);
	
	/*******************************************************************************
	 Rom Loaders / Game Drivers
	********************************************************************************
	 just the 1 sets supported
	*******************************************************************************/
	
	static RomLoadPtr rom_wwfsstar = new RomLoadPtr(){ public void handler(){ 
		ROM_REGION( 0x40000, REGION_CPU1/*, 0*/ );/* Main CPU  (68000) */
		ROM_LOAD_EVEN( "24ac-04.34", 0x00000, 0x20000, 0xee9b850e );
		ROM_LOAD_ODD( "24ad-04.35", 0x00000, 0x20000, 0x057c2eef );
	
		ROM_REGION( 0x10000, REGION_CPU2/*, 0*/ );/* Sound CPU (Z80)  */
		ROM_LOAD( "b.12",    0x00000, 0x08000, 0x1e44f8aa );
		ROM_REGION( 0x40000, REGION_SOUND1/*, 0*/ );/* ADPCM samples */
		ROM_LOAD( "24a9.46",	   0x00000, 0x20000, 0x703ff08f );	ROM_LOAD( "wwfs03.bin",    0x20000, 0x10000, 0x8a35a20e );	ROM_LOAD( "wwfs05.bin",    0x30000, 0x10000, 0x6df08962 );
		ROM_REGION( 0x20000, REGION_GFX1 | REGIONFLAG_DISPOSE );/* FG0 Tiles (8x8) */
		ROM_LOAD( "24a4-0.58",    0x00000, 0x20000, 0xcb12ba40 );
		ROM_REGION( 0x200000, REGION_GFX2 | REGIONFLAG_DISPOSE );/* SPR Tiles (16x16) */
		ROM_LOAD( "wwfs39.bin",    0x000000, 0x010000, 0xd807b09a );	ROM_LOAD( "wwfs38.bin",    0x010000, 0x010000, 0xd8ea94d3 );	ROM_LOAD( "wwfs37.bin",    0x020000, 0x010000, 0x5e8d7407 );	ROM_LOAD( "wwfs36.bin",    0x030000, 0x010000, 0x9005e942 );	ROM_LOAD( "wwfs43.bin",    0x040000, 0x010000, 0xaafc4a38 );	ROM_LOAD( "wwfs42.bin",    0x050000, 0x010000, 0xe48b88fb );	ROM_LOAD( "wwfs41.bin",    0x060000, 0x010000, 0xed7f69d5 );	ROM_LOAD( "wwfs40.bin",    0x070000, 0x010000, 0x4d75fd89 );	ROM_LOAD( "wwfs19.bin",    0x080000, 0x010000, 0x7426d444 );	ROM_LOAD( "wwfs18.bin",    0x090000, 0x010000, 0xaf11ad2a );	ROM_LOAD( "wwfs17.bin",    0x0a0000, 0x010000, 0xef12069f );	ROM_LOAD( "wwfs16.bin",    0x0b0000, 0x010000, 0x08343e7f );	ROM_LOAD( "wwfs15.bin",    0x0c0000, 0x010000, 0xaac5a928 );	ROM_LOAD( "wwfs14.bin",    0x0d0000, 0x010000, 0x67eb7bea );	ROM_LOAD( "wwfs13.bin",    0x0e0000, 0x010000, 0x970b6e76 );	ROM_LOAD( "wwfs12.bin",    0x0f0000, 0x010000, 0x242caff5 );	ROM_LOAD( "wwfs27.bin",    0x100000, 0x010000, 0xf3eb8ab9 );	ROM_LOAD( "wwfs26.bin",    0x110000, 0x010000, 0x2ca91eaf );	ROM_LOAD( "wwfs25.bin",    0x120000, 0x010000, 0xbbf69c6a );	ROM_LOAD( "wwfs24.bin",    0x130000, 0x010000, 0x76b08bcd );	ROM_LOAD( "wwfs23.bin",    0x140000, 0x010000, 0x681f5b5e );	ROM_LOAD( "wwfs22.bin",    0x150000, 0x010000, 0x81fe1bf7 );	ROM_LOAD( "wwfs21.bin",    0x160000, 0x010000, 0xc52eee5e );	ROM_LOAD( "wwfs20.bin",    0x170000, 0x010000, 0xb2a8050e );	ROM_LOAD( "wwfs35.bin",    0x180000, 0x010000, 0x9d648d82 );	ROM_LOAD( "wwfs34.bin",    0x190000, 0x010000, 0x742a79db );	ROM_LOAD( "wwfs33.bin",    0x1a0000, 0x010000, 0xf6923db6 );	ROM_LOAD( "wwfs32.bin",    0x1b0000, 0x010000, 0x9becd621 );	ROM_LOAD( "wwfs31.bin",    0x1c0000, 0x010000, 0xf94c74d5 );	ROM_LOAD( "wwfs30.bin",    0x1d0000, 0x010000, 0x94094518 );	ROM_LOAD( "wwfs29.bin",    0x1e0000, 0x010000, 0x7b5b9d83 );	ROM_LOAD( "wwfs28.bin",    0x1f0000, 0x010000, 0x70fda626 );
		ROM_REGION( 0x80000, REGION_GFX3 | REGIONFLAG_DISPOSE );/* BG0 Tiles (16x16) */
		ROM_LOAD( "wwfs51.bin",    0x00000, 0x10000, 0x51157385 );	ROM_LOAD( "wwfs50.bin",    0x10000, 0x10000, 0x7fc79df5 );	ROM_LOAD( "wwfs49.bin",    0x20000, 0x10000, 0xa14076b0 );	ROM_LOAD( "wwfs48.bin",    0x30000, 0x10000, 0x251372fd );	ROM_LOAD( "wwfs47.bin",    0x40000, 0x10000, 0x6fd7b6ea );	ROM_LOAD( "wwfs46.bin",    0x50000, 0x10000, 0x985e5180 );	ROM_LOAD( "wwfs45.bin",    0x60000, 0x10000, 0xb2fad792 );	ROM_LOAD( "wwfs44.bin",    0x70000, 0x10000, 0x4f965fa9 );ROM_END(); }}; 
	
	
	public static GameDriver driver_wwfsstar	   = new GameDriver("1989"	,"wwfsstar"	,"wwfsstar.java"	,rom_wwfsstar,null	,machine_driver_wwfsstar	,input_ports_wwfsstar	,null	,ROT0	,	"Technos Japan", "WWF Superstars (US)" );
}
