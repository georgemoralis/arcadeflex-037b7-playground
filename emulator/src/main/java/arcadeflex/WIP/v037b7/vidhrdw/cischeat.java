/***************************************************************************

						Cisco Heat & F1 GrandPrix Star
							(c) 1990 & 1991 Jaleco


				    driver by Luca Elia (eliavit@unina.it)


Note:	if MAME_DEBUG is defined, pressing Z with:

		Q,W,E		shows scroll 0,1,2
		R,T			shows road 0,1
		A,S,D,F		shows sprites with priority 0,1,2,3
		N			shows sprites attribute
		M			disables sprites zooming
		U			toggles the display of some hardware registers'
					values ($80000/2/4/6)

		Keys can be used togheter!
		Additionally, layers can be disabled, writing to $82400
		(fake video register):

			---- ---- --54 ----		Enable Road 1,0
			---- ---- ---- 3---		Enable Sprites
			---- ---- ---- -210		Enable Scroll 2,1,0

		0 is the same as 0x3f


----------------------------------------------------------------------
Video Section Summary		[ Cisco Heat ]			[ F1 GP Star ]
----------------------------------------------------------------------

[ 3 Scrolling Layers ]
	see Megasys1.c

		Tile Format:

				Colour		fedc b--- ---- ----		fedc ---- ---- ----
				Code		---- -a98 7654 3210		---- ba98 7654 3210

		Layer Size:			May be different from Megasys1?

[ 2 Road Layers ]
	Each of the 256 (not all visible) lines of the screen
	can display any of the lines of gfx in ROM, which are
	larger than the sceen and can therefore be scrolled

				Line Width			1024					1024
				Zoom				No						Yes

[ 256 Sprites ]
	Each Sprite is up to 256x256 pixels (!) and can be zoomed in and out.
	See below.


***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.WIP.v037b7.drivers.megasys1H.*;
import static arcadeflex.WIP.v037b7.vidhrdw.megasys1.*;
import static gr.codebb.arcadeflex.old.arcadeflex.video.osd_clearbitmap;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.common.ptrLib.UShortPtr;
import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.spriteH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.spriteC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.common.libc.cstring.memset;
import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.memcpy;
import static gr.codebb.arcadeflex.old.arcadeflex.video.osd_get_pen;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;
import static arcadeflex.v037b7.vidhrdw.generic.*;        
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.drawgfxzoom;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;        

public class cischeat
{
	
	/* Variables only used here: */
	
	static int cischeat_ip_select;
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	static int debugsprites;	// For debug purposes
/*TODO*///	#endif
	
	/* Variables that driver has access to: */
	public static UBytePtr[] cischeat_roadram = new UBytePtr[2];
	
	/* Variables defined in driver: */
	
	
	/***************************************************************************
	
									vh_start
	
	***************************************************************************/
	
	/**************************************************************************
									[ Cisco Heat ]
	**************************************************************************/
	
	/* 32 colour codes for the tiles */
	public static VhStartPtr cischeat_vh_start = new VhStartPtr() { public int handler() 
	{
                cischeat_roadram[0]=new UBytePtr(10124 * 1024);
                cischeat_roadram[1]=new UBytePtr(10124 * 1024);
                
		if (megasys1_vh_start.handler() == 0)
		{
		 	megasys1_bits_per_color_code = 5;
		 	return 0;
		}
		else	return 1;
	} };
	
	
	
	/**************************************************************************
								[ F1 GrandPrix Star ]
	**************************************************************************/
	
	/* 16 colour codes for the tiles */
	public static VhStartPtr f1gpstar_vh_start = new VhStartPtr() { public int handler() 
	{
		if (megasys1_vh_start.handler() == 0)
		{
		 	megasys1_bits_per_color_code = 4;
		 	return 0;
		}
		else	return 1;
	} };
	
	
	
	/***************************************************************************
	
							Hardware registers access
	
	***************************************************************************/
	
	/*	This function returns the status of the shift (ACTIVE_LOW):
	
			1 - low  shift
			0 - high shift
	
		and allows the shift to be handled using two buttons */
	static int ret = 1; /* start with low shift */
	        
	static int read_shift()
	{
		switch ( (readinputport(0) >> 2) & 3 )
		{
			case 1 : ret = 1;	break;	// low  shift: button 3
			case 2 : ret = 0;	break;	// high shift: button 4
		}
		return ret;
	}
	
	
	/*
		F1 GP Star has a real pedal, while Cisco Heat's is connected to
		a switch. The Former game stores, during boot, the value that
		corresponds to the pedal not pressed, and compares against it:
	
		The value returned must decrease when the pedal is pressed.
		We support just 2 values for now..
	*/
	
	static int read_accelerator()
	{
		if ((readinputport(0) & 1)!=0)	return 0x00;	// pedal pressed
		else						return 0xff;
	}
	
	/**************************************************************************
									[ Cisco Heat ]
	**************************************************************************/
	
	public static ReadHandlerPtr cischeat_vregs_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		switch (offset)
		{
			case 0x0000 : return readinputport(1);	// Coins
			case 0x0002 : return readinputport(2) + (read_shift()<<1);	// Buttons
			case 0x0004 : return readinputport(3);	// Motor Limit Switches
			case 0x0006 : return readinputport(4);	// DSW 1 & 2
			case 0x0010 :
				switch (cischeat_ip_select & 0x3)
				{
					case 0 : return readinputport(6);	// Driving Wheel
					case 1 : return 0xFFFF;				// Cockpit: Up / Down Position?
					case 2 : return 0xFFFF;				// Cockpit: Left / Right Position?
					default: return 0xFFFF;
				}
	
			case 0x2200 : return readinputport(5);	// DSW 3 (4 bits)
			case 0x2300 : return soundlatch2_r.handler(0);	// From sound cpu
	
			default:	SHOW_READ_ERROR("vreg %04X read!",offset);
					return megasys1_vregs.READ_WORD(offset);
		}
	} };
	
	/**************************************************************************
								[ F1 GrandPrix Star ]
	**************************************************************************/
	
	
	public static ReadHandlerPtr f1gpstar_vregs_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	
		switch (offset)
		{
			case 0x0000 :	// DSW 1&2: coinage changes with Country
			{
				int val = readinputport(1);
				if ((val & 0x0200) != 0)	return readinputport(6) | val; 	// JP, US
				else				return readinputport(7) | val; 	// UK, FR
			}
	
	//		case 0x0002 :	return 0xFFFF;
			case 0x0004 :	return readinputport(2) + (read_shift()<<5);	// Buttons
			case 0x0006 :	return readinputport(3);	// ? Read at boot only
			case 0x0008 :	return soundlatch2_r.handler(0);	// From sound cpu
			case 0x000c :	return readinputport(4);	// DSW 3
	
			case 0x0010 :	// Accel + Driving Wheel
				return (read_accelerator()&0xff) + ((readinputport(5)&0xff)<<8);
	
			default:		
                            SHOW_READ_ERROR("vreg %04X read!",offset);
                            return megasys1_vregs.READ_WORD(offset);
		}
	} };
	
	/**************************************************************************
									[ Cisco Heat ]
	**************************************************************************/
	
	public static WriteHandlerPtr cischeat_vregs_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	int old_data, new_data;
	
		old_data = megasys1_vregs.READ_WORD(offset);
		COMBINE_WORD_MEM(megasys1_vregs,offset,data);
		new_data  = megasys1_vregs.READ_WORD(offset);
	
		switch (offset)
		{
	 		case 0x0000   :	// leds
/*TODO*///	 			set_led_status(0,new_data & 0x10);
/*TODO*///				set_led_status(1,new_data & 0x20);
				break;
	
			case 0x0002   :	// ?? 91/1/91/1 ...
				break;
	
	 		case 0x0004   :	// motor (seat?)
/*TODO*///				set_led_status(2, (new_data != old_data) ? 1 : 0);
	 			break;
	
	 		case 0x0006   :	// motor (wheel?)
				break;
	
			case 0x0010   : cischeat_ip_select = new_data;	break;
			case 0x0012   : break; // value above + 1
	
			case 0x2000+0 : 
                            //MEGASYS1_VREG_SCROLL(0,x)		
                            megasys1_scrollx[0] = new_data;
                            break;
			case 0x2000+2 : 
                            //MEGASYS1_VREG_SCROLL(0,y)		
                            megasys1_scrolly[0] = new_data;
                            break;
			case 0x2000+4 : 
                            //MEGASYS1_VREG_FLAG(0)
                            megasys1_scroll_0_flag_w(new_data); 
                            if (megasys1_tmap_0 == null) SHOW_WRITE_ERROR("vreg %04X <- %04X NO MEMORY FOR SCREEN",offset,data);
                        break;
	
			case 0x2008+0 : 
                            //MEGASYS1_VREG_SCROLL(1,x)		
                            megasys1_scrollx[1] = new_data;
                            break;
			case 0x2008+2 : 
                            //MEGASYS1_VREG_SCROLL(1,y)		
                            megasys1_scrolly[1] = new_data;
                            break;
			case 0x2008+4 : 
                            //MEGASYS1_VREG_FLAG(1)			
                            megasys1_scroll_1_flag_w(new_data); 
                            if (megasys1_tmap_1 == null) SHOW_WRITE_ERROR("vreg %04X <- %04X NO MEMORY FOR SCREEN",offset,data);
                            break;
	
			case 0x2100+0 : 
                            //MEGASYS1_VREG_SCROLL(2,x)		
                            megasys1_scrollx[2] = new_data;
                            break;
			case 0x2100+2 : 
                            //MEGASYS1_VREG_SCROLL(2,y)		
                            megasys1_scrolly[2] = new_data;
                            break;
			case 0x2100+4 : 
                            //MEGASYS1_VREG_FLAG(2)			
                            megasys1_scroll_2_flag_w(new_data); 
                            if (megasys1_tmap_2 == null) SHOW_WRITE_ERROR("vreg %04X <- %04X NO MEMORY FOR SCREEN",offset,data);
                            break;
	
			case 0x2108   : break;	// ? written with 0 only
			case 0x2208   : break;	// watchdog reset
	
			case 0x2300   :	/* Sound CPU: reads latch during int 4, and stores command */
							soundlatch_w.handler(0,new_data);
							cpu_cause_interrupt(3,4);
							break;
	
			/* Not sure about this one.. */
			case 0x2308   :	cpu_set_reset_line(1, (new_data & 2)!=0 ? ASSERT_LINE : CLEAR_LINE );
							cpu_set_reset_line(2, (new_data & 2)!=0 ? ASSERT_LINE : CLEAR_LINE );
							cpu_set_reset_line(3, (new_data & 1)!=0 ? ASSERT_LINE : CLEAR_LINE );
							break;
	
			default: SHOW_WRITE_ERROR("vreg %04X <- %04X",offset,data);
		}
	} };
	
	
	/**************************************************************************
								[ F1 GrandPrix Star ]
	**************************************************************************/
	
	public static WriteHandlerPtr f1gpstar_vregs_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	int old_data, new_data;
	
		old_data = megasys1_vregs.READ_WORD(offset);
		COMBINE_WORD_MEM(megasys1_vregs,offset,data);
		new_data  = megasys1_vregs.READ_WORD(offset);
	
		switch (offset)
		{
	/*
	CPU #0 PC 00234A : Warning, vreg 0000 <- 0000
	CPU #0 PC 002350 : Warning, vreg 0002 <- 0000
	CPU #0 PC 00235C : Warning, vreg 0006 <- 0000
	*/
			// "shudder" motors, leds
			case 0x0004   :
			case 0x0014   :
	
/*TODO*///				set_led_status(0,new_data & 0x04);	// 1p start lamp
/*TODO*///				set_led_status(1,new_data & 0x20);	// 2p start lamp?
	
				// wheel | seat motor
/*TODO*///				set_led_status(2, ((new_data >> 3) | (new_data >> 4)) & 1 );
	
				break;
	
			/* Usually written in sequence, but not always */
			case 0x0008   :	soundlatch_w.handler(0,new_data);	break;
			case 0x0018   :	cpu_cause_interrupt(3,4);	break;
	
			case 0x0010   :	break;
	
			case 0x2000+0 : 
                            //MEGASYS1_VREG_SCROLL(0,x)		
                            megasys1_scrollx[0] = new_data;
                            break;
			case 0x2000+2 : 
                            //MEGASYS1_VREG_SCROLL(0,y)		
                            megasys1_scrolly[0] = new_data;
                            break;
			case 0x2000+4 : 
                            //MEGASYS1_VREG_FLAG(0)			
                            megasys1_scroll_0_flag_w(new_data); 
                            if (megasys1_tmap_0 == null) SHOW_WRITE_ERROR("vreg %04X <- %04X NO MEMORY FOR SCREEN",offset,data);
                            break;
	
			case 0x2008+0 : 
                            //MEGASYS1_VREG_SCROLL(1,x)		
                            megasys1_scrollx[1] = new_data;
                            break;
			case 0x2008+2 : 
                            //MEGASYS1_VREG_SCROLL(1,y)		
                            megasys1_scrolly[1] = new_data;
                            break;
			case 0x2008+4 : 
                            //MEGASYS1_VREG_FLAG(1)			
                            megasys1_scroll_1_flag_w(new_data); 
                            if (megasys1_tmap_1 == null) SHOW_WRITE_ERROR("vreg %04X <- %04X NO MEMORY FOR SCREEN",offset,data);
                            break;
	
			case 0x2100+0 : 
                            //MEGASYS1_VREG_SCROLL(2,x)		
                            megasys1_scrollx[2] = new_data;
                            break;
			case 0x2100+2 : 
                            //MEGASYS1_VREG_SCROLL(2,y)		
                            megasys1_scrolly[2] = new_data;
                            break;
			case 0x2100+4 : 
                            //MEGASYS1_VREG_FLAG(2)			
                            megasys1_scroll_2_flag_w(new_data); 
                            if (megasys1_tmap_2 == null) SHOW_WRITE_ERROR("vreg %04X <- %04X NO MEMORY FOR SCREEN",offset,data);
                            break;
	
			case 0x2108   : break;	// ? written with 0 only
			case 0x2208   : break;	// watchdog reset
	
			/* Not sure about this one. Values: $10 then 0, $7 then 0 */
			case 0x2308   :	cpu_set_reset_line(1, (new_data & 1)!=0 ? ASSERT_LINE : CLEAR_LINE );
							cpu_set_reset_line(2, (new_data & 2)!=0 ? ASSERT_LINE : CLEAR_LINE );
							cpu_set_reset_line(3, (new_data & 4)!=0 ? ASSERT_LINE : CLEAR_LINE );
							break;
	
			default:		SHOW_WRITE_ERROR("vreg %04X <- %04X",offset,data);
		}
	} };
	
	
	
	/***************************************************************************
									Road Drawing
	***************************************************************************/
	
	static int ROAD_COLOR_CODES	= 64;
	static int ROAD_COLOR(int _x_){	return((_x_) & (ROAD_COLOR_CODES-1)); }
	
	/* horizontal size of 1 line and 1 tile of the road */
	static int X_SIZE = (1024);
	static int TILE_SIZE = (64);
	
	
	/**************************************************************************
									[ Cisco Heat ]
	**************************************************************************/
	
	/*
		Road format:
	
		Offset		Data
	
		00.w		Code
	
		02.w		X Scroll
	
		04.w		fedc ---- ---- ----		unused?
					---- ba98 ---- ----		Priority
					---- ---- 76-- ----		unused?
					---- ---- --54 3210		Color
	
		06.w		Unused
	
	*/
	
	static void cischeat_mark_road_colors(int road_num)
	{
		int i, color, sy;
                int[] colmask = new int[ROAD_COLOR_CODES];
	
		int gfx_num					=	(road_num & 1)!=0 ? 4 : 3;
		GfxDecodeInfo gfx	=	Machine.drv.gfxdecodeinfo[gfx_num];
		int total_elements			=	Machine.gfx[gfx_num].total_elements;
		int[] pen_usage		=	Machine.gfx[gfx_num].pen_usage;
	//	int total_color_codes		=	gfx.total_color_codes;
		int color_codes_start		=	gfx.color_codes_start;
		UBytePtr roadram		=	new UBytePtr(cischeat_roadram[road_num & 1]);
	
		int min_y = Machine.visible_area. min_y;
		int max_y = Machine.visible_area. max_y;
	
		for (color = 0 ; color < ROAD_COLOR_CODES ; color++) colmask[color] = 0;
	
		/* Let's walk from the top to the bottom of the visible screen */
		for (sy = min_y ; sy <= max_y ; sy ++)
		{
			int code	= roadram.READ_WORD(sy*8 + 0);
			int attr	= roadram.READ_WORD(sy*8 + 4);
				color	= ROAD_COLOR(attr);
	
			/* line number converted to tile number (each tile is TILE_SIZE x 1) */
			code	= code * (X_SIZE/TILE_SIZE);
	
			for (i = 0; i < (X_SIZE/TILE_SIZE); i++)
				colmask[color] |= pen_usage[(code + i) % total_elements];
		}
	
		for (color = 0; color < ROAD_COLOR_CODES; color++)
		 for (i = 0; i < 16; i++)
		  if ((colmask[color] & (1 << i))!=0) 
                      palette_used_colors.write(16 * color + i + color_codes_start, PALETTE_COLOR_USED);
	}
	
	
	
	/*
		Draw road in the given bitmap. The priority1 and priority2 parameters
		specify the range of lines to draw
	*/
	
	static void cischeat_draw_road(osd_bitmap bitmap, int road_num, int priority1, int priority2, int transparency)
	{
	
		int curr_code,sx,sy;
		int min_priority, max_priority;
	
		rectangle rect		=	new rectangle(Machine.visible_area);
		UBytePtr roadram		=	new UBytePtr(cischeat_roadram[road_num & 1]);
		GfxElement gfx		=	Machine.gfx[(road_num & 1)!=0?4:3];
	
		int min_y = rect.min_y;
		int max_y = rect.max_y;
	
		int max_x = rect.max_x;
	
		if (priority1 < priority2)	{	min_priority = priority1;	max_priority = priority2; }
		else						{	min_priority = priority2;	max_priority = priority1; }
	
		/* Move the priority values in place */
		min_priority = (min_priority & 7) * 0x100;
		max_priority = (max_priority & 7) * 0x100;
	
		/* Let's draw from the top to the bottom of the visible screen */
		for (sy = min_y ; sy <= max_y ; sy ++)
		{
			int code	= roadram.READ_WORD(sy*8 + 0);
			int xscroll = roadram.READ_WORD(sy*8 + 2);
			int attr	= roadram.READ_WORD(sy*8 + 4);
	
			/* high byte is a priority information */
			if ( ((attr & 0x700) < min_priority) || ((attr & 0x700) > max_priority) )
				continue;
	
			/* line number converted to tile number (each tile is TILE_SIZE x 1) */
			code	= code * (X_SIZE/TILE_SIZE);
	
			xscroll %= X_SIZE;
			curr_code = code + xscroll/TILE_SIZE;
	
			for (sx = -(xscroll%TILE_SIZE) ; sx <= max_x ; sx +=TILE_SIZE)
			{
				drawgfx(bitmap,gfx,
						curr_code++,
						ROAD_COLOR(attr),
						0,0,
						sx,sy,
						rect,
						transparency,15);
	
				/* wrap around */
				if (curr_code%(X_SIZE/TILE_SIZE)==0)	curr_code = code;
			}
		}
	
	}
	
	
	
	
	
	
	
	/**************************************************************************
								[ F1 GrandPrix Star ]
	**************************************************************************/
	
	
	/*
		Road format:
	
		Offset		Data
	
		00.w		fedc ---- ---- ----		Priority
					---- ba98 7654 3210		X Scroll (After Zoom)
	
		02.w		fedc ba-- ---- ----		unused?
					---- --98 7654 3210		X Zoom
	
		04.w		fe-- ---- ---- ----		unused?
					--dc ba98 ---- ----		Color
					---- ---- 7654 3210		?
	
		06.w		Code
	
	
		Imagine an "empty" line, 2 * X_SIZE wide, with the gfx from
		the ROM - whose original size is X_SIZE - in the middle.
	
		Zooming acts on this latter and can shrink it to 1 pixel or
		widen it to 2 * X_SIZE, while *keeping it centered* in the
		empty line.	Scrolling acts on the resulting line.
	
	*/
	
	static void f1gpstar_mark_road_colors(int road_num)
	{
		int i, color, sy;
                int[] colmask = new int[ROAD_COLOR_CODES];
	
		int gfx_num					=	(road_num & 1)!=0 ? 4 : 3;
		GfxDecodeInfo gfx	=	Machine.drv.gfxdecodeinfo[gfx_num];
		int total_elements			=	Machine.gfx[gfx_num].total_elements;
		int[] pen_usage		=	Machine.gfx[gfx_num].pen_usage;
	//	int total_color_codes		=	gfx.total_color_codes;
		int color_codes_start		=	gfx.color_codes_start;
                if (cischeat_roadram[0]==null){
                    cischeat_roadram[0]=new UBytePtr(1024*1024);
                    cischeat_roadram[1]=new UBytePtr(1024*1024);
                }
                    
		UBytePtr roadram		=	new UBytePtr(cischeat_roadram[road_num & 1]);
	
		int min_y = Machine.visible_area.min_y;
		int max_y = Machine.visible_area.max_y;
	
		for (color = 0 ; color < ROAD_COLOR_CODES ; color++) colmask[color] = 0;
	
		/* Let's walk from the top to the bottom of the visible screen */
		for (sy = min_y ; sy <= max_y ; sy ++)
		{
			int code	= roadram.READ_WORD(sy*8 + 6);
			int attr	= roadram.READ_WORD(sy*8 + 4);
				color	= ROAD_COLOR(attr>>8);
	
			/* line number converted to tile number (each tile is TILE_SIZE x 1) */
			code	= code * (X_SIZE/TILE_SIZE);
	
			for (i = 0; i < (X_SIZE/TILE_SIZE); i++)
				colmask[color] |= pen_usage[(code + i) % total_elements];
		}
	
		for (color = 0; color < ROAD_COLOR_CODES; color++)
		 for (i = 0; i < 16; i++)
		  if ((colmask[color] & (1 << i))!=0) 
                      palette_used_colors.write(16 * color + i + color_codes_start, PALETTE_COLOR_USED);
	}
	
	
	
	/*
		Draw road in the given bitmap. The priority1 and priority2 parameters
		specify the range of lines to draw
	*/
	
	static void f1gpstar_draw_road(osd_bitmap bitmap, int road_num, int priority1, int priority2, int transparency)
	{
		int sx,sy;
		int xstart;
		int min_priority, max_priority;
	
		rectangle rect		=	new rectangle(Machine.visible_area);
		UBytePtr roadram		=	new UBytePtr(cischeat_roadram[road_num & 1]);
		GfxElement gfx		=	Machine.gfx[(road_num & 1)!=0?4:3];
	
		int min_y = rect.min_y;
		int max_y = rect.max_y;
	
		int max_x = rect.max_x << 16;	// use fixed point values (16.16), for accuracy
	
		if (priority1 < priority2)	{	min_priority = priority1;	max_priority = priority2; }
		else						{	min_priority = priority2;	max_priority = priority1; }
	
		/* Move the priority values in place */
		min_priority = (min_priority & 7) * 0x1000;
		max_priority = (max_priority & 7) * 0x1000;
	
		/* Let's draw from the top to the bottom of the visible screen */
		for (sy = min_y ; sy <= max_y ; sy ++)
		{
			int xscale, xdim;
	
			int xscroll	= roadram.READ_WORD(sy*8 + 0);
			int xzoom	= roadram.READ_WORD(sy*8 + 2);
			int attr	= roadram.READ_WORD(sy*8 + 4);
			int code	= roadram.READ_WORD(sy*8 + 6);
	
			/* highest nibble is a priority information */
			if ( ((xscroll & 0x7000) < min_priority) || ((xscroll & 0x7000) > max_priority) )
				continue;
	
			/* zoom code range: 000-3ff		scale range: 0.0-2.0 */
			xscale = ( ((xzoom & 0x3ff)+1) << (16+1) ) / 0x400;
	
			/* line number converted to tile number (each tile is TILE_SIZE x 1) */
			code	= code * (X_SIZE/TILE_SIZE);
	
			/* dimension of a tile after zoom */
			xdim = TILE_SIZE * xscale;
	
			xscroll %= 2 * X_SIZE;
	
			xstart  = (X_SIZE - xscroll) * 0x10000;
			xstart -= (X_SIZE * xscale) / 2;
	
			/* let's approximate to the nearest greater integer value
			   to avoid holes in between tiles */
			xscale += (1<<16)/TILE_SIZE;
	
			/* Draw the line */
			for (sx = xstart ; sx <= max_x ; sx += xdim)
			{
				drawgfxzoom(bitmap,gfx,
							code++,
							ROAD_COLOR(attr>>8),
							0,0,
							sx / 0x10000, sy,
							rect,
							transparency,15,
							xscale, 1 << 16);
	
				/* stop when the end of the line of gfx is reached */
				if ((code % (X_SIZE/TILE_SIZE)) == 0)	break;
			}
	
		}
	
	}
	
	
	
	
	/***************************************************************************
	
								Sprites Drawing
	
	***************************************************************************/
	
	static int SIGN_EXTEND_POS(int _var_){	
            _var_ &= 0x3ff; 
            if (_var_ > 0x1ff) 
                _var_ -= 0x400;
            return _var_; 
        }
        
	static int SPRITE_COLOR_CODES	= (0x80);
	static int SPRITE_COLOR(int _x_) {	return((_x_) & (SPRITE_COLOR_CODES - 1)); }
	static int SHRINK(int _org_, int _fact_){ return( ( (_org_) << 16 ) * (_fact_ & 0xff) / 0x80 ); }
	
	
	/* Mark colors used by visible sprites */
	
	static void cischeat_mark_sprite_colors()
	{
		int i, color;
                int[] colmask = new int[SPRITE_COLOR_CODES];
		int[] pen_usage	=	Machine.gfx[5].pen_usage;
		int total_elements		=	Machine.gfx[5].total_elements;
		int color_codes_start	=	Machine.drv.gfxdecodeinfo[5].color_codes_start;
	
		int xmin = Machine.visible_area.min_x;
		int xmax = Machine.visible_area.max_x;
		int ymin = Machine.visible_area.min_y;
		int ymax = Machine.visible_area.max_y;
	
		UBytePtr source	= new UBytePtr(spriteram);
		UBytePtr finish	= new UBytePtr(source, 0x1000);
	
		for (color = 0 ; color < SPRITE_COLOR_CODES ; color++) colmask[color] = 0;
	
		for (; source.offset < finish.offset; source.inc(0x10) )
		{
			int sx, sy, xzoom, yzoom;
			int xdim, ydim, xnum, ynum;
			int code, attr, size;
	
	 		size	=	source.READ_WORD(0x00);
			if ((size & 0x1000) != 0)	continue;
	
			/* number of tiles */
			xnum	=	( (size & 0x0f) >> 0 ) + 1;
			ynum	=	( (size & 0xf0) >> 4 ) + 1;
	
			xzoom	=	source.READ_WORD(0x02);
			yzoom	=	source.READ_WORD(0x04);
	
			sx		=	source.READ_WORD(0x06);
			sy		=	source.READ_WORD(0x08);
			sx = SIGN_EXTEND_POS(sx);
			sy = SIGN_EXTEND_POS(sy);
	
			/* dimension of the sprite after zoom */
			xdim	=	( SHRINK(16 * xnum, xzoom) ) >> 16;
			ydim	=	( SHRINK(16 * ynum, yzoom) ) >> 16;
	
			/* the y pos passed to the hardware is the that of the last line
			   we need the y pos of the first line  */
			sy -= ydim;
	
			if (	((sx+xdim-1) < xmin) || (sx > xmax) ||
					((sy+ydim-1) < ymin) || (sy > ymax)		)	continue;
	
			code	=	source.READ_WORD(0x0C);
			attr	=	source.READ_WORD(0x0E);
			color	=	SPRITE_COLOR(attr);
	
			for (i = 0; i < xnum * ynum; i++)
				colmask[color] |= pen_usage[(code + i) % total_elements];
		}
	
		for (color = 0; color < SPRITE_COLOR_CODES; color++)
		 for (i = 0; i < (16-1); i++)	// pen 15 is transparent
		  if ((colmask[color] & (1 << i))!=0) 
                      palette_used_colors.write(16 * color + i + color_codes_start, PALETTE_COLOR_USED);
	}
	
	
	
	
	/*
		Draw sprites, in the given priority range, to a bitmap.
	
		Priorities between 0 and 15 cover sprites whose priority nibble
		is between 0 and 15. Priorities between	0+16 and 15+16 cover
		sprites whose priority nibble is between 0 and 15 and whose
		colour code's high bit is set.
	
		Sprite Data:
	
		Offset		Data
	
		00 		fed- ---- ---- ----		unused?
		 		---c ---- ---- ----		Don't display this sprite
				---- ba98 ---- ----		unused?
				---- ---- 7654 ----		Number of tiles along Y, minus 1 (1-16)
				---- ---- ---- 3210		Number of tiles along X, minus 1 (1-16)
	
		02/04 	fed- ---- ---- ----		unused?
		 		---c ---- ---- ----		Flip X/Y
				---- ba98 ---- ----		unused?
				---- ---- 7654 3210		X/Y zoom
	
		06/08	fedc ba-- ---- ----		unused?
				---- --98 7654 3210		X/Y position
	
		0A		?
	
		0C		Code
	
		0E
				fed- ---- ---- ----		unused?
				---c ---- ---- ----		used!
				---- ba98 ---- ----		Priority
				---- ---- 7--- ----		unused?
				---- ---- -654 3210		Color
	
	*/
	static void cischeat_draw_sprites(osd_bitmap bitmap , int priority1, int priority2)
	{
		int x, y, sx, sy;
		int xzoom, yzoom, xscale, yscale, flipx, flipy;
		int xdim, ydim, xnum, ynum;
		int xstart, ystart, xend, yend, xinc, yinc;
		int code, attr, color, size;
	
		int min_priority, max_priority, high_sprites;
	
		UBytePtr source	= new UBytePtr(spriteram);
		UBytePtr finish	= new UBytePtr(source, 0x1000);
	
	
		/* Move the priority values in place */
		high_sprites = ((priority1 >= 16) | (priority2 >= 16))?1:0;
		priority1 = (priority1 & 0x0f) * 0x100;
		priority2 = (priority2 & 0x0f) * 0x100;
	
		if (priority1 < priority2)	{	min_priority = priority1;	max_priority = priority2; }
		else						{	min_priority = priority2;	max_priority = priority1; }
	
		for (; source.offset < finish.offset; source.inc(0x10) )
		{
			size	=	source.READ_WORD(0x00);
			if ((size & 0x1000) != 0)	continue;
	
			/* number of tiles */
			xnum	=	( (size & 0x0f) >> 0 ) + 1;
			ynum	=	( (size & 0xf0) >> 4 ) + 1;
	
			xzoom	=	source.READ_WORD(0x02);
			yzoom	=	source.READ_WORD(0x04);
			flipx	=	xzoom & 0x1000;
			flipy	=	yzoom & 0x1000;
	
			sx		=	source.READ_WORD(0x06);
			sy		=	source.READ_WORD(0x08);
			sx = SIGN_EXTEND_POS(sx);
			sy = SIGN_EXTEND_POS(sy);
	
			/* use fixed point values (16.16), for accuracy */
			sx <<= 16;
			sy <<= 16;
	
			/* dimension of a tile after zoom */
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///			if ( keyboard_pressed(KEYCODE_Z) && keyboard_pressed(KEYCODE_M) )
/*TODO*///			{
/*TODO*///				xdim	=	16 << 16;
/*TODO*///				ydim	=	16 << 16;
/*TODO*///			}
/*TODO*///			else
/*TODO*///	#endif
			{
				xdim	=	SHRINK(16,xzoom);
				ydim	=	SHRINK(16,yzoom);
			}
	
			if ( ( (xdim / 0x10000) == 0 ) || ( (ydim / 0x10000) == 0) )	continue;
	
			/* the y pos passed to the hardware is the that of the last line
			   we need the y pos of the first line  */
			sy -= (ydim * ynum);
	
			code	=	source.READ_WORD(0x0C);
			attr	=	source.READ_WORD(0x0E);
			color	=	SPRITE_COLOR(attr);
	
			/* high byte is a priority information */
			if ( ((attr & 0x700) < min_priority) || ((attr & 0x700) > max_priority) )
				continue;
	
			if ( high_sprites!=0 && ((color & (SPRITE_COLOR_CODES/2))==0) )
				continue;
	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	if ( (debugsprites) && ( ((attr & 0x0300)>>8) != (debugsprites-1) ) ) 	{ continue; };
/*TODO*///	#endif
	
			xscale = xdim / 16;
			yscale = ydim / 16;
	
	
			/* let's approximate to the nearest greater integer value
			   to avoid holes in between tiles */
			if ((xscale & 0xffff) != 0)	xscale += (1<<16)/16;
			if ((yscale & 0xffff) != 0)	yscale += (1<<16)/16;
	
	
			if (flipx != 0)	{ xstart = xnum-1;  xend = -1;    xinc = -1; }
			else		{ xstart = 0;       xend = xnum;  xinc = +1; }
	
			if (flipy != 0)	{ ystart = ynum-1;  yend = -1;    yinc = -1; }
			else		{ ystart = 0;       yend = ynum;  yinc = +1; }
	
			for (y = ystart; y != yend; y += yinc)
			{
				for (x = xstart; x != xend; x += xinc)
				{
					drawgfxzoom(bitmap,Machine.gfx[5],
								code++,
								color,
								flipx,flipy,
								(sx + x * xdim) / 0x10000, (sy + y * ydim) / 0x10000,
								Machine.visible_area,
								TRANSPARENCY_PEN,15,
								xscale, yscale );
				}
			}
	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///			if ( keyboard_pressed(KEYCODE_Z) && keyboard_pressed(KEYCODE_N) )
/*TODO*///			{
/*TODO*///				struct DisplayText dt[3];
/*TODO*///				char buf[40],buf1[40];
/*TODO*///	
/*TODO*///				dt[0].text = buf;	dt[1].text = buf1;	dt[2].text = 0;
/*TODO*///				dt[0].color = dt[1].color = UI_COLOR_NORMAL;
/*TODO*///				dt[0].x = sx / 0x10000;	dt[1].x = dt[0].x;
/*TODO*///				dt[0].y = sy / 0x10000;	dt[1].y = dt[0].y + 8;
/*TODO*///	
/*TODO*///				sprintf(buf, "A:%04X",attr);
/*TODO*///				sprintf(buf1,"Z:%04X",xzoom);
/*TODO*///				displaytext(Machine.scrbitmap,dt,0,0);
/*TODO*///			}
/*TODO*///	#endif
	
		}	/* end sprite loop */
	
	}
	
	
	
	
	/***************************************************************************
	
									Screen Drawing
	
	***************************************************************************/
	
/*TODO*///	#define CISCHEAT_LAYERSCTRL \
/*TODO*///	debugsprites = 0; \
/*TODO*///	if (keyboard_pressed(KEYCODE_Z)) \
/*TODO*///	{ \
/*TODO*///		int msk = 0; \
/*TODO*///		if (keyboard_pressed(KEYCODE_Q))	{ msk |= 0xffc1;} \
/*TODO*///		if (keyboard_pressed(KEYCODE_W))	{ msk |= 0xffc2;} \
/*TODO*///		if (keyboard_pressed(KEYCODE_E))	{ msk |= 0xffc4;} \
/*TODO*///		if (keyboard_pressed(KEYCODE_A))	{ msk |= 0xffc8; debugsprites = 1;} \
/*TODO*///		if (keyboard_pressed(KEYCODE_S))	{ msk |= 0xffc8; debugsprites = 2;} \
/*TODO*///		if (keyboard_pressed(KEYCODE_D))	{ msk |= 0xffc8; debugsprites = 3;} \
/*TODO*///		if (keyboard_pressed(KEYCODE_F))	{ msk |= 0xffc8; debugsprites = 4;} \
/*TODO*///		if (keyboard_pressed(KEYCODE_R))	{ msk |= 0xffd0;} \
/*TODO*///		if (keyboard_pressed(KEYCODE_T))	{ msk |= 0xffe0;} \
/*TODO*///	 \
/*TODO*///		if (msk != 0) megasys1_active_layers &= msk; \
/*TODO*///	} \
/*TODO*///	\
/*TODO*///	{ \
/*TODO*///		static int show_unknown; \
/*TODO*///		if ( keyboard_pressed(KEYCODE_Z) && keyboard_pressed(KEYCODE_U) ) \
/*TODO*///		{ \
/*TODO*///			while (keyboard_pressed(KEYCODE_U)); \
/*TODO*///			show_unknown ^= 1; \
/*TODO*///		} \
/*TODO*///	 \
/*TODO*///		if (show_unknown != 0) \
/*TODO*///		{ \
/*TODO*///			char buf[80]; \
/*TODO*///			sprintf(buf, "0:%04X 2:%04X 4:%04X 6:%04X", \
/*TODO*///						READ_WORD(&megasys1_vregs[0x0000]), \
/*TODO*///						READ_WORD(&megasys1_vregs[0x0002]), \
/*TODO*///						READ_WORD(&megasys1_vregs[0x0004]), \
/*TODO*///						READ_WORD(&megasys1_vregs[0x0006]) \
/*TODO*///					); \
/*TODO*///			usrintf_showmessage(buf); \
/*TODO*///		} \
/*TODO*///	}
	
	
	/**************************************************************************
									[ Cisco Heat ]
	**************************************************************************/
	
	public static VhUpdatePtr cischeat_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int megasys1_active_layers1, flag;
	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///		megasys1_active_layers = READ_WORD(&megasys1_vregs[0x2400]);
/*TODO*///		if (megasys1_active_layers == 0)	megasys1_active_layers = 0x3f;
/*TODO*///	#else
		megasys1_active_layers = 0x3f;
/*TODO*///	#endif
	
		megasys1_active_layers1 = megasys1_active_layers;
	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///		CISCHEAT_LAYERSCTRL
/*TODO*///	#endif
	
		//MEGASYS1_TMAP_SET_SCROLL(0)
                if (megasys1_tmap_0 != null) 
                { 
                        tilemap_set_scrollx(megasys1_tmap_0, 0, megasys1_scrollx[0]); 
                        tilemap_set_scrolly(megasys1_tmap_0, 0, megasys1_scrolly[0]); 
                }
		//MEGASYS1_TMAP_SET_SCROLL(1)
                if (megasys1_tmap_1 != null) 
                { 
                        tilemap_set_scrollx(megasys1_tmap_1, 0, megasys1_scrollx[1]); 
                        tilemap_set_scrolly(megasys1_tmap_1, 0, megasys1_scrolly[1]); 
                }
		//MEGASYS1_TMAP_SET_SCROLL(2)
                if (megasys1_tmap_2 != null) 
                { 
                        tilemap_set_scrollx(megasys1_tmap_2, 0, megasys1_scrollx[2]); 
                        tilemap_set_scrolly(megasys1_tmap_2, 0, megasys1_scrolly[2]); 
                }
	
		//MEGASYS1_TMAP_UPDATE(0)
                if ( (megasys1_tmap_0)!=null && (megasys1_active_layers & (1 << 0) )!=0 ) 
                    tilemap_update(megasys1_tmap_0);
		//MEGASYS1_TMAP_UPDATE(1)
                if ( (megasys1_tmap_1)!=null && (megasys1_active_layers & (1 << 1) )!=0 ) 
                    tilemap_update(megasys1_tmap_1);
		//MEGASYS1_TMAP_UPDATE(2)
                if ( (megasys1_tmap_2)!=null && (megasys1_active_layers & (1 << 2) )!=0 ) 
                    tilemap_update(megasys1_tmap_2);
	
		palette_init_used_colors();
	
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_mark_sprite_colors();
		if ((megasys1_active_layers & 0x10) != 0)	cischeat_mark_road_colors(0);	// road 0
		if ((megasys1_active_layers & 0x20) != 0)	cischeat_mark_road_colors(1);	// road 1
	
		if (palette_recalc() != null)	tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		//MEGASYS1_TMAP_RENDER(0)
                if ( (megasys1_tmap_0)!=null && (megasys1_active_layers & (1 << 0) )!=0 )
                    tilemap_render(megasys1_tmap_0);
		//MEGASYS1_TMAP_RENDER(1)
                if ( (megasys1_tmap_1)!=null && (megasys1_active_layers & (1 << 1) )!=0 )
                    tilemap_render(megasys1_tmap_1);
		//MEGASYS1_TMAP_RENDER(2)
                if ( (megasys1_tmap_2)!=null && (megasys1_active_layers & (1 << 2) )!=0 )
                    tilemap_render(megasys1_tmap_2);
	
		osd_clearbitmap(Machine.scrbitmap);
	
											/* bitmap, road, priority, transparency */
		if ((megasys1_active_layers & 0x10) != 0)	cischeat_draw_road(bitmap,0,7,5,TRANSPARENCY_NONE);
		if ((megasys1_active_layers & 0x20) != 0)	cischeat_draw_road(bitmap,1,7,5,TRANSPARENCY_PEN);
	
		flag = 0;
		//MEGASYS1_TMAP_DRAW(0)
                if ( (megasys1_tmap_0)!=null && (megasys1_active_layers & (1 << 0) )!=0 ) 
                { 
                        tilemap_draw(bitmap, megasys1_tmap_0, flag ); 
                        flag = 0; 
                }                
	//	else	osd_clearbitmap(Machine.scrbitmap);
		//MEGASYS1_TMAP_DRAW(1)
                if ( (megasys1_tmap_1)!=null && (megasys1_active_layers & (1 << 1) )!=0 ) 
                { 
                        tilemap_draw(bitmap, megasys1_tmap_1, flag ); 
                        flag = 0; 
                }
	
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_draw_sprites(bitmap,15,3);
		if ((megasys1_active_layers & 0x10) != 0)	cischeat_draw_road(bitmap,0,4,1,TRANSPARENCY_PEN);
		if ((megasys1_active_layers & 0x20) != 0)	cischeat_draw_road(bitmap,1,4,1,TRANSPARENCY_PEN);
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_draw_sprites(bitmap,2,2);
		if ((megasys1_active_layers & 0x10) != 0)	cischeat_draw_road(bitmap,0,0,0,TRANSPARENCY_PEN);
		if ((megasys1_active_layers & 0x20) != 0)	cischeat_draw_road(bitmap,1,0,0,TRANSPARENCY_PEN);
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_draw_sprites(bitmap,1,0);
		//MEGASYS1_TMAP_DRAW(2)
                if ( (megasys1_tmap_2)!=null && (megasys1_active_layers & (1 << 2) )!=0 ) 
                { 
                        tilemap_draw(bitmap, megasys1_tmap_2, flag ); 
                        flag = 0; 
                }
	
		/* for the map screen */
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_draw_sprites(bitmap,0+16,0+16);
	
	
		megasys1_active_layers = megasys1_active_layers1;
	} };
	
	
	
	
	
	/**************************************************************************
								[ F1 GrandPrix Star ]
	**************************************************************************/
	
	
	public static VhUpdatePtr f1gpstar_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int megasys1_active_layers1, flag;
	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///		megasys1_active_layers = READ_WORD(&megasys1_vregs[0x2400]);
/*TODO*///		if (megasys1_active_layers == 0)	megasys1_active_layers = 0x3f;
/*TODO*///	#else
		megasys1_active_layers = 0x3f;
/*TODO*///	#endif
	
		megasys1_active_layers1 = megasys1_active_layers;
	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///		CISCHEAT_LAYERSCTRL
/*TODO*///	#endif
	
		//MEGASYS1_TMAP_SET_SCROLL(0)
                if (megasys1_tmap_0 != null) 
                { 
                        tilemap_set_scrollx(megasys1_tmap_0, 0, megasys1_scrollx[0]); 
                        tilemap_set_scrolly(megasys1_tmap_0, 0, megasys1_scrolly[0]); 
                }
		//MEGASYS1_TMAP_SET_SCROLL(1)
                if (megasys1_tmap_1 != null) 
                { 
                        tilemap_set_scrollx(megasys1_tmap_1, 0, megasys1_scrollx[1]); 
                        tilemap_set_scrolly(megasys1_tmap_1, 0, megasys1_scrolly[1]); 
                }
		//MEGASYS1_TMAP_SET_SCROLL(2)
                if (megasys1_tmap_2 != null) 
                { 
                        tilemap_set_scrollx(megasys1_tmap_2, 0, megasys1_scrollx[2]); 
                        tilemap_set_scrolly(megasys1_tmap_2, 0, megasys1_scrolly[2]); 
                }
	
		//MEGASYS1_TMAP_UPDATE(0)
                if ( (megasys1_tmap_0)!=null && (megasys1_active_layers & (1 << 0) )!=0 ) 
                    tilemap_update(megasys1_tmap_0);
		//MEGASYS1_TMAP_UPDATE(1)
                if ( (megasys1_tmap_1)!=null && (megasys1_active_layers & (1 << 1) )!=0 ) 
                    tilemap_update(megasys1_tmap_1);
		//MEGASYS1_TMAP_UPDATE(2)
                if ( (megasys1_tmap_2)!=null && (megasys1_active_layers & (1 << 2) )!=0 ) 
                    tilemap_update(megasys1_tmap_2);
	
		palette_init_used_colors();
	
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_mark_sprite_colors();
		if ((megasys1_active_layers & 0x10) != 0)	f1gpstar_mark_road_colors(0);	// road 0
		if ((megasys1_active_layers & 0x20) != 0)	f1gpstar_mark_road_colors(1);	// road 1
	
		if (palette_recalc() != null)	tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		//MEGASYS1_TMAP_RENDER(0)
                if ( (megasys1_tmap_0)!=null && (megasys1_active_layers & (1 << 0) )!=0 )
                    tilemap_render(megasys1_tmap_0);
		//MEGASYS1_TMAP_RENDER(1)
                if ( (megasys1_tmap_1)!=null && (megasys1_active_layers & (1 << 1) )!=0 )
                    tilemap_render(megasys1_tmap_1);
		//MEGASYS1_TMAP_RENDER(2)
                if ( (megasys1_tmap_2)!=null && (megasys1_active_layers & (1 << 2) )!=0 )
                    tilemap_render(megasys1_tmap_2);
	
		osd_clearbitmap(Machine.scrbitmap);
	
	/*	1: clouds 5, grad 7, road 0		2: clouds 5, grad 7, road 0, tunnel roof 0 */
	
		/* NOTE: TRANSPARENCY_NONE isn't supported by drawgfxzoom    */
		/* (the function used in f1gpstar_drawroad to draw the road) */
	
		/* road 1!! 0!! */					/* bitmap, road, min_priority, max_priority, transparency */
		if ((megasys1_active_layers & 0x20) != 0)	f1gpstar_draw_road(bitmap,1,6,7,TRANSPARENCY_PEN);
		if ((megasys1_active_layers & 0x10) != 0)	f1gpstar_draw_road(bitmap,0,6,7,TRANSPARENCY_PEN);
	
		flag = 0;
		//MEGASYS1_TMAP_DRAW(0)
                if ( (megasys1_tmap_0)!=null && (megasys1_active_layers & (1 << 0) )!=0 ) 
                { 
                        tilemap_draw(bitmap, megasys1_tmap_0, flag ); 
                        flag = 0; 
                }
	//	else	osd_clearbitmap(Machine.scrbitmap);
		//MEGASYS1_TMAP_DRAW(1)
                if ( (megasys1_tmap_1)!=null && (megasys1_active_layers & (1 << 1) )!=0 ) 
                { 
                        tilemap_draw(bitmap, megasys1_tmap_1, flag ); 
                        flag = 0; 
                }
	
		/* road 1!! 0!! */					/* bitmap, road, min_priority, max_priority, transparency */
		if ((megasys1_active_layers & 0x20) != 0)	f1gpstar_draw_road(bitmap,1,1,5,TRANSPARENCY_PEN);
		if ((megasys1_active_layers & 0x10) != 0)	f1gpstar_draw_road(bitmap,0,1,5,TRANSPARENCY_PEN);
	
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_draw_sprites(bitmap,15,2);
	
		/* road 1!! 0!! */					/* bitmap, road, min_priority, max_priority, transparency */
		if ((megasys1_active_layers & 0x20) != 0)	f1gpstar_draw_road(bitmap,1,0,0,TRANSPARENCY_PEN);
		if ((megasys1_active_layers & 0x10) != 0)	f1gpstar_draw_road(bitmap,0,0,0,TRANSPARENCY_PEN);
	
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_draw_sprites(bitmap,1,1);
		//MEGASYS1_TMAP_DRAW(2)
                if ( (megasys1_tmap_2)!=null && (megasys1_active_layers & (1 << 2) )!=0 ) 
                { 
                        tilemap_draw(bitmap, megasys1_tmap_2, flag ); 
                        flag = 0; 
                }
		if ((megasys1_active_layers & 0x08) != 0)	cischeat_draw_sprites(bitmap,0,0);
	
	
		megasys1_active_layers = megasys1_active_layers1;
	} };
}
