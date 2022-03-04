package arcadeflex.WIP.v037b7.drivers;

import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
import static gr.codebb.arcadeflex.common.libc.cstdio.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;

public class megasys1H {
/***************************************************************************

						-= Jaleco Mega System 1 =-

				driver by	Luca Elia (eliavit@unina.it)


	This file contains definitions used across multiple megasys1
	and non megasys1 Jaleco games:

	* Gfx layouts
	* Input ports
	* Read and write errors logging
	* Scrolling layers handling

***************************************************************************/


/***************************************************************************

							 Sound Chips Access

***************************************************************************/



/***************************************************************************

							 Code Decryption

***************************************************************************/

/*
 This macro is used to decrypt the code roms:
 the first parameter is the encrypted word, the other parameters specify
 the bits layout to build the word in clear from the encrypted one
*/
public static int BITSWAP(int _x,int _f,int _e,int _d,int _c,int _b,int _a,int _9,int _8,int _7,int _6,int _5,int _4,int _3,int _2,int _1,int _0) {
		return (((_x & (1 << _0))!=0?(1<<0x0):0) + 
		 ((_x & (1 << _1))!=0?(1<<0x1):0) + 
		 ((_x & (1 << _2))!=0?(1<<0x2):0) + 
		 ((_x & (1 << _3))!=0?(1<<0x3):0) + 
		 ((_x & (1 << _4))!=0?(1<<0x4):0) + 
		 ((_x & (1 << _5))!=0?(1<<0x5):0) + 
		 ((_x & (1 << _6))!=0?(1<<0x6):0) + 
		 ((_x & (1 << _7))!=0?(1<<0x7):0) + 
		 ((_x & (1 << _8))!=0?(1<<0x8):0) + 
		 ((_x & (1 << _9))!=0?(1<<0x9):0) + 
		 ((_x & (1 << _a))!=0?(1<<0xa):0) + 
		 ((_x & (1 << _b))!=0?(1<<0xb):0) + 
		 ((_x & (1 << _c))!=0?(1<<0xc):0) + 
		 ((_x & (1 << _d))!=0?(1<<0xd):0) + 
		 ((_x & (1 << _e))!=0?(1<<0xe):0) + 
		 ((_x & (1 << _f))!=0?(1<<0xf):0));
}


/*TODO*///void astyanax_rom_decode(int cpu);
/*TODO*///void phantasm_rom_decode(int cpu);
/*TODO*///void rodland_rom_decode(int cpu);
/*TODO*///
/*TODO*///
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///								Gfx Layouts
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*///
/*TODO*////* 8x8x4 layout - straightforward arrangement */
/*TODO*///#define MEGASYS1_LAYOUT_8x8(_name_,_romsize_)
/*TODO*///static GfxLayout _name_ = new GfxLayout
/*TODO*///(
/*TODO*///	8,8,
/*TODO*///	(_romsize_)*8/(8*8*4),
/*TODO*///	4,
/*TODO*///	new int[] {0, 1, 2, 3},
/*TODO*///	new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4},
/*TODO*///	new int[] {0*32,1*32,2*32,3*32,4*32,5*32,6*32,7*32},
/*TODO*///	8*8*4
/*TODO*///);
/*TODO*///
/*TODO*///
/*TODO*////* 16x16x4 layout - straightforward arrangement */
/*TODO*///#define MEGASYS1_LAYOUT_16x16(_name_,_romsize_) 
/*TODO*///static GfxLayout _name_ = new GfxLayout
/*TODO*///(
/*TODO*///	16,16,
/*TODO*///	(_romsize_)*8/(16*16*4),
/*TODO*///	4,
/*TODO*///	new int[] {0, 1, 2, 3},
/*TODO*///	new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4, 
/*TODO*///	 8*4,9*4,10*4,11*4,12*4,13*4,14*4,15*4}, 
/*TODO*///	new int[] {0*64,1*64,2*64,3*64,4*64,5*64,6*64,7*64,
/*TODO*///	 8*64,9*64,10*64,11*64,12*64,13*64,14*64,15*64},
/*TODO*///	16*16*4
/*TODO*///);
/*TODO*///
/*TODO*///
/*TODO*////* 16x16x4 layout - formed by four 8x8x4 tiles  */
/*TODO*///#define MEGASYS1_LAYOUT_16x16_QUAD(_name_,_romsize_)
/*TODO*///static GfxLayout _name_ = new GfxLayout
/*TODO*///(
/*TODO*///	16,16,
/*TODO*///	(_romsize_)*8/(16*16*4),
/*TODO*///	4,
/*TODO*///	new int[] {0, 1, 2, 3},
/*TODO*///	new int[] {0*4,1*4,2*4,3*4,4*4,5*4,6*4,7*4,
/*TODO*///	 0*4+32*16,1*4+32*16,2*4+32*16,3*4+32*16,4*4+32*16,5*4+32*16,6*4+32*16,7*4+32*16},
/*TODO*///	new int[] {0*32,1*32,2*32,3*32,4*32,5*32,6*32,7*32,
/*TODO*///	 8*32,9*32,10*32,11*32,12*32,13*32,14*32,15*32},
/*TODO*///	16*16*4
/*TODO*///);



/***************************************************************************

								Input Ports

***************************************************************************/


/* IN0 - COINS */
    public static void COINS() {
            PORT_START();
            PORT_BIT(  0x01, IP_ACTIVE_LOW, IPT_START1 );
            PORT_BIT(  0x02, IP_ACTIVE_LOW, IPT_START2 );
            PORT_BIT(  0x04, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT(  0x08, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT(  0x10, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT(  0x20, IP_ACTIVE_LOW, IPT_COIN3 );
            PORT_BIT(  0x40, IP_ACTIVE_LOW, IPT_COIN1 );
            PORT_BIT(  0x80, IP_ACTIVE_LOW, IPT_COIN2 );
    }

/*TODO*////* IN1/3 - PLAYER 1/2 */
/*TODO*///#define JOY_4BUTTONS(_flag_) 
/*TODO*///	PORT_START
/*TODO*///	PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | _flag_ );
/*TODO*///	PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY | _flag_ );
/*TODO*///	PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY | _flag_ );
/*TODO*///	PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY | _flag_ );
/*TODO*///	PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | _flag_ );
/*TODO*///	PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | _flag_ );
/*TODO*///	PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_BUTTON3 | _flag_ );
/*TODO*///	PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_BUTTON4 | _flag_ );
/*TODO*///
/*TODO*///#define JOY_3BUTTONS(_flag_) 
/*TODO*///	PORT_START
/*TODO*///	PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | _flag_ );
/*TODO*///	PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY | _flag_ );
/*TODO*///	PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY | _flag_ );
/*TODO*///	PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY | _flag_ );
/*TODO*///	PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | _flag_ );
/*TODO*///	PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | _flag_ );
/*TODO*///	PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_BUTTON3 | _flag_ );
/*TODO*///	PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_UNKNOWN );

    public static void JOY_2BUTTONS(int _flag_) {
            PORT_START();
            PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | _flag_ );
            PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY | _flag_ );
            PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY | _flag_ );
            PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY | _flag_ );
            PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_BUTTON1 | _flag_ );
            PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_BUTTON2 | _flag_ );
            PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_UNKNOWN );
    }

    /* IN2 - RESERVE */
    public static void RESERVE() {
            PORT_START();
            PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_UNKNOWN );/* Reserve 1P */
            PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_UNKNOWN );/* Reserve 2P */
            PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_UNKNOWN );
    }

/*TODO*////* IN4 - Coinage DSWs */
/*TODO*/////	1]	01-41 02-31 03-21 07-11 06-12 05-13 04-14 00-FC	* 2
/*TODO*/////	2]	04-31 02-21 07-11 03-12 05-13 01-14 06-15 00-FC
/*TODO*/////		00-41 20-31 10-21 38-11 18-12 28-13 08-14 30-15
/*TODO*///
/*TODO*///
/*TODO*///#define COINAGE_6BITS 
/*TODO*///	PORT_DIPNAME( 0x07, 0x07, DEF_STR( "Coin_A") );
/*TODO*///	PORT_DIPSETTING(    0x04, DEF_STR( "3C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x02, DEF_STR( "2C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x07, DEF_STR( "1C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x03, DEF_STR( "1C_2C") );
/*TODO*///	PORT_DIPSETTING(    0x05, DEF_STR( "1C_3C") );
/*TODO*///	PORT_DIPSETTING(    0x01, DEF_STR( "1C_4C") );
/*TODO*///	PORT_DIPSETTING(    0x06, DEF_STR( "1C_5C") );
/*TODO*///	PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );
/*TODO*///	PORT_DIPNAME( 0x38, 0x38, DEF_STR( "Coin_B") );
/*TODO*///	PORT_DIPSETTING(    0x00, DEF_STR( "4C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x20, DEF_STR( "3C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x10, DEF_STR( "2C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x38, DEF_STR( "1C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x18, DEF_STR( "1C_2C") );
/*TODO*///	PORT_DIPSETTING(    0x28, DEF_STR( "1C_3C") );
/*TODO*///	PORT_DIPSETTING(    0x08, DEF_STR( "1C_4C") );
/*TODO*///	PORT_DIPSETTING(    0x30, DEF_STR( "1C_5C") );

    public static void COINAGE_6BITS_2() { 
            PORT_DIPNAME( 0x07, 0x07, DEF_STR( "Coin_A") );
            PORT_DIPSETTING(    0x01, DEF_STR( "4C_1C") );
            PORT_DIPSETTING(    0x02, DEF_STR( "3C_1C") );
            PORT_DIPSETTING(    0x03, DEF_STR( "2C_1C") );
            PORT_DIPSETTING(    0x07, DEF_STR( "1C_1C") );
            PORT_DIPSETTING(    0x06, DEF_STR( "1C_2C") );
            PORT_DIPSETTING(    0x05, DEF_STR( "1C_3C") );
            PORT_DIPSETTING(    0x04, DEF_STR( "1C_4C") );
            PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );
            PORT_DIPNAME( 0x38, 0x38, DEF_STR( "Coin_B") );
            PORT_DIPSETTING(    0x08, DEF_STR( "4C_1C") );
            PORT_DIPSETTING(    0x10, DEF_STR( "3C_1C") );
            PORT_DIPSETTING(    0x18, DEF_STR( "2C_1C") );
            PORT_DIPSETTING(    0x38, DEF_STR( "1C_1C") );
            PORT_DIPSETTING(    0x30, DEF_STR( "1C_2C") );
            PORT_DIPSETTING(    0x28, DEF_STR( "1C_3C") );
            PORT_DIPSETTING(    0x20, DEF_STR( "1C_4C") );
            PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );
    }

/*TODO*///#define COINAGE_8BITS 
/*TODO*///	PORT_DIPNAME( 0x0f, 0x0f, DEF_STR( "Coin_A") );
/*TODO*///	PORT_DIPSETTING(    0x07, DEF_STR( "4C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x08, DEF_STR( "3C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x09, DEF_STR( "2C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x0f, DEF_STR( "1C_1C") );
/*TODO*////*	PORT_DIPSETTING(    0x05, DEF_STR( "1C_1C") );*/	
/*TODO*////*	PORT_DIPSETTING(    0x04, DEF_STR( "1C_1C") );*/	
/*TODO*////*	PORT_DIPSETTING(    0x03, DEF_STR( "1C_1C") );*/	
/*TODO*////*	PORT_DIPSETTING(    0x02, DEF_STR( "1C_1C") );*/	
/*TODO*////*	PORT_DIPSETTING(    0x01, DEF_STR( "1C_1C") );*/	
/*TODO*///	PORT_DIPSETTING(    0x06, DEF_STR( "2C_3C") );
/*TODO*///	PORT_DIPSETTING(    0x0e, DEF_STR( "1C_2C") );
/*TODO*///	PORT_DIPSETTING(    0x0d, DEF_STR( "1C_3C") );
/*TODO*///	PORT_DIPSETTING(    0x0c, DEF_STR( "1C_4C") );
/*TODO*///	PORT_DIPSETTING(    0x0b, DEF_STR( "1C_5C") );
/*TODO*///	PORT_DIPSETTING(    0x0a, DEF_STR( "1C_6C") );
/*TODO*///	PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );
/*TODO*///	PORT_DIPNAME( 0xf0, 0xf0, DEF_STR( "Coin_B") );
/*TODO*///	PORT_DIPSETTING(    0x70, DEF_STR( "4C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x80, DEF_STR( "3C_1C") );
/*TODO*///	PORT_DIPSETTING(    0x90, DEF_STR( "2C_1C") );
/*TODO*///	PORT_DIPSETTING(    0xf0, DEF_STR( "1C_1C") );
/*TODO*////*	PORT_DIPSETTING(    0x50, DEF_STR( "1C_1C") );*/	
/*TODO*////*	PORT_DIPSETTING(    0x40, DEF_STR( "1C_1C") );*/	
/*TODO*////*	PORT_DIPSETTING(    0x30, DEF_STR( "1C_1C") );*/	
/*TODO*////*	PORT_DIPSETTING(    0x20, DEF_STR( "1C_1C") );*/	
/*TODO*////*	PORT_DIPSETTING(    0x10, DEF_STR( "1C_1C") );*/	
/*TODO*///	PORT_DIPSETTING(    0x60, DEF_STR( "2C_3C") );
/*TODO*///	PORT_DIPSETTING(    0xe0, DEF_STR( "1C_2C") );
/*TODO*///	PORT_DIPSETTING(    0xd0, DEF_STR( "1C_3C") );
/*TODO*///	PORT_DIPSETTING(    0xc0, DEF_STR( "1C_4C") );
/*TODO*///	PORT_DIPSETTING(    0xb0, DEF_STR( "1C_5C") );
/*TODO*///	PORT_DIPSETTING(    0xa0, DEF_STR( "1C_6C") );
/*TODO*///	PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );
/*TODO*///
/*TODO*///
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///						Read and Write Errors Logging
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*///
/*TODO*///#ifdef MAME_DEBUG
/*TODO*///#define SHOW_READ_ERROR(_format_,_offset_)
/*TODO*///{
/*TODO*///	char buf[80];
/*TODO*///	sprintf(buf,_format_,_offset_);
/*TODO*///	usrintf_showmessage(buf);
/*TODO*///	logerror("CPU #0 PC %06X : Warning, %sn",cpu_get_pc(), buf); 
/*TODO*///}
/*TODO*///
/*TODO*///#define SHOW_WRITE_ERROR(_format_,_offset_,_data_)
/*TODO*///{
/*TODO*///	char buf[80];
/*TODO*///	sprintf(buf,_format_,_offset_,_data_);
/*TODO*///	usrintf_showmessage(buf);
/*TODO*///	logerror("CPU #0 PC %06X : Warning, %sn",cpu_get_pc(), buf); 
/*TODO*///}
/*TODO*///
/*TODO*///#else
/*TODO*///
/*TODO*///#define SHOW_READ_ERROR(_format_,_offset_)
/*TODO*///{
/*TODO*///	char buf[80];
/*TODO*///	sprintf(buf,_format_,_offset_);
/*TODO*///	logerror("CPU #0 PC %06X : Warning, %sn",cpu_get_pc(), buf);
/*TODO*///}

    public static void SHOW_WRITE_ERROR(String _format_, int _offset_, int _data_)
    {
            String buf="";
            buf = sprintf(/*buf,*/_format_,_offset_,_data_); 
            logerror("CPU #0 PC %06X : Warning, %sn",cpu_get_pc(), buf); 
    }

/*TODO*///#endif
/*TODO*///
/*TODO*///
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///						Scrolling Layers Handling
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*////* Variables */
/*TODO*///extern struct tilemap *megasys1_tmap_0, *megasys1_tmap_1, *megasys1_tmap_2;
/*TODO*///extern UBytePtr megasys1_scrollram_0, *megasys1_scrollram_1, *megasys1_scrollram_2;
/*TODO*///extern UBytePtr megasys1_objectram, *megasys1_vregs, *megasys1_ram;
/*TODO*///extern int megasys1_scroll_flag[3], megasys1_scrollx[3], megasys1_scrolly[3], megasys1_pages_per_tmap_x[3], megasys1_pages_per_tmap_y[3];
/*TODO*///extern int megasys1_active_layers, megasys1_sprite_bank;
/*TODO*///extern int megasys1_screen_flag, megasys1_sprite_flag;
/*TODO*///extern int megasys1_bits_per_color_code;
/*TODO*///extern int megasys1_8x8_scroll_0_factor, megasys1_16x16_scroll_0_factor;
/*TODO*///extern int megasys1_8x8_scroll_1_factor, megasys1_16x16_scroll_1_factor;
/*TODO*///extern int megasys1_8x8_scroll_2_factor, megasys1_16x16_scroll_2_factor;
/*TODO*///
/*TODO*///
/*TODO*////* Functions */
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///void megasys1_scroll_0_flag_w(int data);
/*TODO*///void megasys1_scroll_1_flag_w(int data);
/*TODO*///void megasys1_scroll_2_flag_w(int data);
/*TODO*///
/*TODO*///
/*TODO*///#define MEGASYS1_VREG_FLAG(_n_) 
/*TODO*///		megasys1_scroll_##_n_##_flag_w(new_data); 
/*TODO*///		if (megasys1_tmap_##_n_ == 0) SHOW_WRITE_ERROR("vreg %04X <- %04X NO MEMORY FOR SCREEN",offset,data);
/*TODO*///
/*TODO*///#define MEGASYS1_VREG_SCROLL(_n_, _dir_)	megasys1_scroll##_dir_[_n_] = new_data;
/*TODO*///
/*TODO*///
/*TODO*///#define MEGASYS1_TMAP_SET_SCROLL(_n_) 
/*TODO*///	if (megasys1_tmap_##_n_ != 0) 
/*TODO*///	{ 
/*TODO*///		tilemap_set_scrollx(megasys1_tmap_##_n_, 0, megasys1_scrollx[_n_]); 
/*TODO*///		tilemap_set_scrolly(megasys1_tmap_##_n_, 0, megasys1_scrolly[_n_]); 
/*TODO*///	}
/*TODO*///
/*TODO*///#define MEGASYS1_TMAP_UPDATE(_n_) 
/*TODO*///	if ( (megasys1_tmap_##_n_) && (megasys1_active_layers & (1 << _n_) ) ) 
/*TODO*///		tilemap_update(megasys1_tmap_##_n_);
/*TODO*///
/*TODO*///
/*TODO*///#define MEGASYS1_TMAP_RENDER(_n_) 
/*TODO*///	if ( (megasys1_tmap_##_n_) && (megasys1_active_layers & (1 << _n_) ) )
/*TODO*///		tilemap_render(megasys1_tmap_##_n_);
/*TODO*///
/*TODO*///
/*TODO*///#define MEGASYS1_TMAP_DRAW(_n_) 
/*TODO*///	if ( (megasys1_tmap_##_n_) && (megasys1_active_layers & (1 << _n_) ) ) 
/*TODO*///	{ 
/*TODO*///		tilemap_draw(bitmap, megasys1_tmap_##_n_, flag ); 
/*TODO*///		flag = 0; 
/*TODO*///	}
    
}
