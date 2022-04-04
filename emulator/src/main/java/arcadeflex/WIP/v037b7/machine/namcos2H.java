package arcadeflex.WIP.v037b7.machine;

import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;

public class namcos2H {
    /***************************************************************************

      namcos2.h

      Common functions & declarations for the Namco System 2 driver

    ***************************************************************************/

    // #define NAMCOS2_DEBUG_MODE


    /* CPU reference numbers */

    public static final int NAMCOS2_CPU1	= 0;
    public static final int NAMCOS2_CPU2	= 1;
    public static final int NAMCOS2_CPU3	= 2;
    public static final int NAMCOS2_CPU4	= 3;
    
    public static final int CPU_MASTER	= NAMCOS2_CPU1;
    public static final int CPU_SLAVE	= NAMCOS2_CPU2;
    public static final int CPU_SOUND	= NAMCOS2_CPU3;
    public static final int CPU_MCU 	= NAMCOS2_CPU4;
    
    /* VIDHRDW */
    
    public static final int GFX_OBJ1	= 0;
    public static final int GFX_OBJ2	= 1;
    public static final int GFX_CHR 	= 2;
    public static final int GFX_ROZ 	= 3;
    
    /*********************************************/
    /* IF GAME SPECIFIC HACKS ARE REQUIRED THEN  */
    /* USE THE namcos2_gametype VARIABLE TO FIND */
    /* OUT WHAT GAME IS RUNNING 				 */
    /*********************************************/
    
    public static final int NAMCOS2_ASSAULT 			= 0x1000;
    public static final int NAMCOS2_ASSAULT_JP			= 0x1001;
    public static final int NAMCOS2_ASSAULT_PLUS		= 0x1002;
    public static final int NAMCOS2_BUBBLE_TROUBLE		= 0x1003;
    public static final int NAMCOS2_BURNING_FORCE		= 0x1004;
    public static final int NAMCOS2_COSMO_GANG			= 0x1005;
    public static final int NAMCOS2_COSMO_GANG_US		= 0x1006;
    public static final int NAMCOS2_DIRT_FOX			= 0x1007;
    public static final int NAMCOS2_DIRT_FOX_JP 		= 0x1008;
    public static final int NAMCOS2_DRAGON_SABER		= 0x1009;
    public static final int NAMCOS2_DRAGON_SABER_JP             = 0x100a;
    public static final int NAMCOS2_FINAL_LAP			= 0x100b;
    public static final int NAMCOS2_FINAL_LAP_2 		= 0x100c;
    public static final int NAMCOS2_FINAL_LAP_3 		= 0x100d;
    public static final int NAMCOS2_FINEST_HOUR 		= 0x100e;
    public static final int NAMCOS2_FOUR_TRAX			= 0x100f;
    public static final int NAMCOS2_GOLLY_GHOST 		= 0x1010;
    public static final int NAMCOS2_LUCKY_AND_WILD		= 0x1011;
    public static final int NAMCOS2_MARVEL_LAND 		= 0x1012;
    public static final int NAMCOS2_METAL_HAWK			= 0x1013;
    public static final int NAMCOS2_MIRAI_NINJA 		= 0x1014;
    public static final int NAMCOS2_ORDYNE			= 0x1015;
    public static final int NAMCOS2_PHELIOS 			= 0x1016;
    public static final int NAMCOS2_ROLLING_THUNDER_2           = 0x1017;
    public static final int NAMCOS2_STEEL_GUNNER		= 0x1018;
    public static final int NAMCOS2_STEEL_GUNNER_2		= 0x1019;
    public static final int NAMCOS2_SUPER_WSTADIUM		= 0x101a;
    public static final int NAMCOS2_SUPER_WSTADIUM_92           = 0x101b;
    public static final int NAMCOS2_SUPER_WSTADIUM_93           = 0x101c;
    public static final int NAMCOS2_SUZUKA_8_HOURS		= 0x101d;
    public static final int NAMCOS2_SUZUKA_8_HOURS_2            = 0x101e;
    public static final int NAMCOS2_VALKYRIE			= 0x101f;
    public static final int NAMCOS2_KYUUKAI_DOUCHUUKI           = 0x1020;
    /*TODO*///
    /*TODO*///extern int namcos2_gametype;
    /*TODO*///
    /*TODO*////*********************************************/
    /*TODO*///
    /*TODO*///
    /*TODO*////* MACHINE */
    /*TODO*///
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////* Dual port memory handlers								  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern UBytePtr namcos2_dpram;
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////* Sprite memory handlers									  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern UBytePtr namcos2_sprite_ram;
    /*TODO*///extern int namcos2_sprite_bank;
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////*	EEPROM memory function handlers 						  */
    /*TODO*////**************************************************************/
    /*TODO*///#define NAMCOS2_68K_EEPROM_W	namcos2_68k_eeprom_w, &namcos2_eeprom, &namcos2_eeprom_size
    /*TODO*///#define NAMCOS2_68K_EEPROM_R	namcos2_68k_eeprom_r
    /*TODO*///extern UBytePtr namcos2_eeprom;
    /*TODO*///extern size_t namcos2_eeprom_size;
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////*	Shared video memory function handlers					  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern size_t namcos2_68k_vram_size;
    /*TODO*///
    /*TODO*///
    /*TODO*///extern unsigned char namcos2_68k_vram_ctrl[];
    /*TODO*///
    /*TODO*///extern struct tilemap *namcos2_tilemap0;
    /*TODO*///extern struct tilemap *namcos2_tilemap1;
    /*TODO*///extern struct tilemap *namcos2_tilemap2;
    /*TODO*///extern struct tilemap *namcos2_tilemap3;
    /*TODO*///extern struct tilemap *namcos2_tilemap4;
    /*TODO*///extern struct tilemap *namcos2_tilemap5;
    /*TODO*///
    /*TODO*///extern int namcos2_tilemap0_flip;
    /*TODO*///extern int namcos2_tilemap1_flip;
    /*TODO*///extern int namcos2_tilemap2_flip;
    /*TODO*///extern int namcos2_tilemap3_flip;
    /*TODO*///extern int namcos2_tilemap4_flip;
    /*TODO*///extern int namcos2_tilemap5_flip;
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////*	Shared video palette function handlers					  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///#define NAMCOS2_COLOUR_CODES	0x20
    /*TODO*///extern UBytePtr namcos2_68k_palette_ram;
    /*TODO*///extern size_t namcos2_68k_palette_size;
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////*	Shared data ROM memory handlerhandlers					  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////* Shared serial communications processory (CPU5 ????)		  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern unsigned char  namcos2_68k_serial_comms_ctrl[0x10];
    /*TODO*///extern UBytePtr namcos2_68k_serial_comms_ram;
    /*TODO*///
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////* Shared protection/random number generator				  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern unsigned char namcos2_68k_key[];
    /*TODO*///
    /*TODO*///
    /*TODO*///extern int namcos2_68k_protect;
    
    
    
    /**************************************************************/
    /* Non-shared memory custom IO device - IRQ/Inputs/Outputs	 */
    /**************************************************************/
    
    /*TODO*///#define NAMCOS2_C148_0			0		/* 0x1c0000 */
    /*TODO*///#define NAMCOS2_C148_1			1
    /*TODO*///#define NAMCOS2_C148_2			2
    /*TODO*///#define NAMCOS2_C148_CPUIRQ 	3
    /*TODO*///#define NAMCOS2_C148_EXIRQ		4		/* 0x1c8000 */
    public static final int NAMCOS2_C148_POSIRQ 	= 5;
    /*TODO*///#define NAMCOS2_C148_SERIRQ 	6
    public static final int NAMCOS2_C148_VBLANKIRQ	= 7;
    /*TODO*///
    /*TODO*///extern int	namcos2_68k_master_C148[32];
    /*TODO*///extern int	namcos2_68k_slave_C148[32];
    /*TODO*///
    /*TODO*///void namcos2_68k_master_posirq( int moog );
    /*TODO*///
    /*TODO*///void namcos2_68k_slave_posirq( int moog );
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////* MASTER CPU RAM MEMORY									  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern UBytePtr namcos2_68k_master_ram;
    /*TODO*///
    /*TODO*///#define NAMCOS2_68K_MASTER_RAM_W	MWA_BANK3, &namcos2_68k_master_ram
    public static final int NAMCOS2_68K_MASTER_RAM_R	= MRA_BANK3;
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////* SLAVE CPU RAM MEMORY 									  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern UBytePtr namcos2_68k_slave_ram;
    /*TODO*///
    /*TODO*///#define NAMCOS2_68K_SLAVE_RAM_W 	MWA_BANK4, &namcos2_68k_slave_ram
    public static final int NAMCOS2_68K_SLAVE_RAM_R 	= MRA_BANK4;
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////*	ROZ - Rotate & Zoom memory function handlers			  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern unsigned char namcos2_68k_roz_ctrl[];
    /*TODO*///
    /*TODO*///extern size_t namcos2_68k_roz_ram_size;
    /*TODO*///extern UBytePtr namcos2_68k_roz_ram;
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////* FINAL LAP road generator definitions.....				  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///extern UBytePtr namcos2_68k_roadtile_ram;
    /*TODO*///extern size_t namcos2_68k_roadtile_ram_size;
    /*TODO*///
    /*TODO*///extern UBytePtr namcos2_68k_roadgfx_ram;
    /*TODO*///extern size_t namcos2_68k_roadgfx_ram_size;
    
    
    
    /**************************************************************/
    /*															  */
    /**************************************************************/
    public static final int BANKED_SOUND_ROM_R		= MRA_BANK6;
    public static final int CPU3_ROM1				= 6;			/* Bank number */
    
    
    
    /*TODO*////**************************************************************/
    /*TODO*////* Sound CPU support handlers - 6809						  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///
    /*TODO*///
    /*TODO*////**************************************************************/
    /*TODO*////* MCU Specific support handlers - HD63705					  */
    /*TODO*////**************************************************************/
    /*TODO*///
    /*TODO*///
    /*TODO*///
    /*TODO*///
    /*TODO*///
    
}
