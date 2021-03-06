/***************************************************************************

							  -= Cave Games =-

				driver by	Luca Elia (eliavit@unina.it)


Note:	if MAME_DEBUG is defined, pressing:

		X/C/V/B/Z  with  Q   shows layer 0 (tiles with priority 0/1/2/3/All)
		X/C/V/B/Z  with  W   shows layer 1 (tiles with priority 0/1/2/3/All)
		X/C/V/B/Z  with  E   shows layer 2 (tiles with priority 0/1/2/3/All)
		X/C/V/B/Z  with  A   shows sprites (tiles with priority 0/1/2/3/All)

		Keys can be used togheter!

		[ 1, 2 or 3 Scrolling Layers ]

		Layer Size:				512 x 512
		Tiles:					16x16x8 (16x16x4 in some games)

		[ 1024 Zooming Sprites ]

		There are 2 spriterams. A hardware register's bit selects
		the one to display (sprites double buffering).

		The sprites are NOT tile based: the "tile" size and start
		address is selectable for each sprite with a 16 pixel granularity.


**************************************************************************/
/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.WIP.v037b7.drivers.cave.cave_spritetype;
import static arcadeflex.common.ptrLib.*;
import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.spriteC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.spriteH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.common.libc.cstdio.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static arcadeflex.v037b7.mame.usrintrf.usrintf_showmessage;
import static gr.codebb.arcadeflex.old.arcadeflex.video.osd_clearbitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;

public class cave
{
	
	
	/* Variables that driver has access to: */
	public static UBytePtr cave_videoregs = new UBytePtr();
	
	public static UBytePtr cave_vram_0 = new UBytePtr(), cave_vctrl_0 = new UBytePtr();
	public static UBytePtr cave_vram_1 = new UBytePtr(), cave_vctrl_1 = new UBytePtr();
	public static UBytePtr cave_vram_2 = new UBytePtr(), cave_vctrl_2 = new UBytePtr();
	
	/* Variables only used here: */
	
	static sprite_list sprite_list;
	static struct_tilemap tilemap_0, tilemap_1, tilemap_2;
	
	
	
	/***************************************************************************
	
							Callbacks for the TileMap code
	
								  [ Tiles Format ]
	
	Offset:
	
	0.w			fe-- ---- ---- ---		Priority
				--dc ba98 ---- ----		Color
				---- ---- 7654 3210
	
	2.w									Code
	
	
	
	***************************************************************************/
	
	static int DIM_NX		= (0x20);
	static int DIM_NY		= (0x20);
	
	
	//CAVE_TILEMAP(0)
        public static GetTileInfoPtr get_tile_info_0 = new GetTileInfoPtr() { public void handler(int tile_index)  
	{ 
		int code		=	(cave_vram_0.READ_WORD( tile_index * 4 + 0) << 16 )+ 
							 cave_vram_0.READ_WORD( tile_index * 4 + 2); 
		SET_TILE_INFO( 0 ,  code & 0x00ffffff , (code & 0x3f000000) >> (32-8) ); 
		tile_info.u32_priority = (code & 0xc0000000)>> (32-2); 
	} }; 
	
	public static WriteHandlerPtr cave_vram_0_w = new WriteHandlerPtr() {public void handler(int offset, int data) 
	{ 
		COMBINE_WORD_MEM(cave_vram_0,offset,data); 
		if ( (offset/4) < DIM_NX * DIM_NY ) 
			tilemap_mark_tile_dirty(tilemap_0, offset/4 ); 
	} }; 
	
	public static WriteHandlerPtr cave_vram_0_8x8_w = new WriteHandlerPtr() {public void handler(int offset, int data) 
	{ 
		offset %= (DIM_NX * DIM_NY * 4 * 4); /* mirrored RAM */ 
		COMBINE_WORD_MEM(cave_vram_0,offset,data); 
		tilemap_mark_tile_dirty(tilemap_0, offset/4 ); 
	} };
        
	//CAVE_TILEMAP(1)
        public static GetTileInfoPtr get_tile_info_1 = new GetTileInfoPtr() { public void handler(int tile_index)  
	{ 
		int code		=	(cave_vram_1.READ_WORD( tile_index * 4 + 0) << 16 )+ 
							 cave_vram_1.READ_WORD( tile_index * 4 + 2); 
		SET_TILE_INFO( 1 ,  code & 0x00ffffff , (code & 0x3f000000) >> (32-8) ); 
		tile_info.u32_priority = (code & 0xc0000000)>> (32-2); 
	} }; 
	
	public static WriteHandlerPtr cave_vram_1_w = new WriteHandlerPtr() {public void handler(int offset, int data) 
	{ 
		COMBINE_WORD_MEM(cave_vram_1,offset,data); 
		if ( (offset/4) < DIM_NX * DIM_NY ) 
			tilemap_mark_tile_dirty(tilemap_1, offset/4 ); 
	} }; 
	
	public static WriteHandlerPtr cave_vram_1_8x8_w = new WriteHandlerPtr() {public void handler(int offset, int data) 
	{ 
		offset %= (DIM_NX * DIM_NY * 4 * 4); /* mirrored RAM */ 
		COMBINE_WORD_MEM(cave_vram_1,offset,data); 
		tilemap_mark_tile_dirty(tilemap_1, offset/4 ); 
	} };
        
	//CAVE_TILEMAP(2)
	public static GetTileInfoPtr get_tile_info_2 = new GetTileInfoPtr() { public void handler(int tile_index)  
	{ 
		int code		=	(cave_vram_2.READ_WORD( tile_index * 4 + 0) << 16 )+ 
							 cave_vram_2.READ_WORD( tile_index * 4 + 2); 
		SET_TILE_INFO( 2 ,  code & 0x00ffffff , (code & 0x3f000000) >> (32-8) ); 
		tile_info.u32_priority = (code & 0xc0000000)>> (32-2); 
	} }; 
	
	public static WriteHandlerPtr cave_vram_2_w = new WriteHandlerPtr() {public void handler(int offset, int data) 
	{ 
		COMBINE_WORD_MEM(cave_vram_2,offset,data); 
		if ( (offset/4) < DIM_NX * DIM_NY ) 
			tilemap_mark_tile_dirty(tilemap_2, offset/4 ); 
	} }; 
	
	public static WriteHandlerPtr cave_vram_2_8x8_w = new WriteHandlerPtr() {public void handler(int offset, int data) 
	{ 
		offset %= (DIM_NX * DIM_NY * 4 * 4); /* mirrored RAM */ 
		COMBINE_WORD_MEM(cave_vram_2,offset,data); 
		tilemap_mark_tile_dirty(tilemap_2, offset/4 ); 
	} };
	
	
	
	
	
	/***************************************************************************
	
									Vh_Start
	
	***************************************************************************/
	
	
	/* 3 Layers (layer 3 is made of 8x8 tiles!) */
	public static VhStartPtr ddonpach_vh_start = new VhStartPtr() { public int handler() 
	{
		tilemap_0 = tilemap_create(	get_tile_info_0,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		tilemap_1 = tilemap_create(	get_tile_info_1,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		/* 8x8 tiles here! */
		tilemap_2 = tilemap_create(	get_tile_info_2,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									8,8,
									DIM_NX*2,DIM_NY*2 );
	
	
		sprite_list = sprite_list_create(spriteram_size[0] / 0x10 / 2, SPRITE_LIST_BACK_TO_FRONT | SPRITE_LIST_RAW_DATA );
	
		if (tilemap_0!=null && tilemap_1!=null && tilemap_2!=null && sprite_list!=null)
		{
			tilemap_set_scroll_rows(tilemap_0,1);
			tilemap_set_scroll_cols(tilemap_0,1);
			tilemap_0.transparent_pen = 0;
	
			tilemap_set_scroll_rows(tilemap_1,1);
			tilemap_set_scroll_cols(tilemap_1,1);
			tilemap_1.transparent_pen = 0;
	
			tilemap_set_scroll_rows(tilemap_2,1);
			tilemap_set_scroll_cols(tilemap_2,1);
			tilemap_2.transparent_pen = 0;
	
			tilemap_set_scrolldx( tilemap_0, -0x6c, -0x57 );
			tilemap_set_scrolldx( tilemap_1, -0x6d, -0x56 );
	//		tilemap_set_scrolldx( tilemap_2, -0x6e, -0x55 );
			tilemap_set_scrolldx( tilemap_2, -0x6e -7, -0x55 +7-1 );
	
			tilemap_set_scrolldy( tilemap_0, -0x11, -0x100 );
			tilemap_set_scrolldy( tilemap_1, -0x11, -0x100 );
			tilemap_set_scrolldy( tilemap_2, -0x11, -0x100 );
	
			sprite_list.max_priority = 3;
			sprite_list.sprite_type = SPRITE_TYPE_ZOOM;
	
			return 0;
		}
		else return 1;
	} };
	
	/* 3 Layers (like esprade but with different scroll delta's) */
	public static VhStartPtr guwange_vh_start = new VhStartPtr() { public int handler() 
	{
		tilemap_0 = tilemap_create(	get_tile_info_0,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		tilemap_1 = tilemap_create(	get_tile_info_1,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		tilemap_2 = tilemap_create(	get_tile_info_2,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
	
		sprite_list = sprite_list_create(spriteram_size[0] / 0x10 / 2, SPRITE_LIST_BACK_TO_FRONT | SPRITE_LIST_RAW_DATA );
	
		if (tilemap_0!=null && tilemap_1!=null && tilemap_2!=null && sprite_list!=null)
		{
			tilemap_set_scroll_rows(tilemap_0,1);
			tilemap_set_scroll_cols(tilemap_0,1);
			tilemap_0.transparent_pen = 0;
	
			tilemap_set_scroll_rows(tilemap_1,1);
			tilemap_set_scroll_cols(tilemap_1,1);
			tilemap_1.transparent_pen = 0;
	
			tilemap_set_scroll_rows(tilemap_2,1);
			tilemap_set_scroll_cols(tilemap_2,1);
			tilemap_2.transparent_pen = 0;
	
	//		tilemap_set_scrolldx( tilemap_0, -0x6c, -0x57 );
	//		tilemap_set_scrolldx( tilemap_1, -0x6d, -0x56 );
	//		tilemap_set_scrolldx( tilemap_2, -0x6e, -0x55 );
	tilemap_set_scrolldx( tilemap_0, -0x6c +2, -0x57 -2 );
	tilemap_set_scrolldx( tilemap_1, -0x6d +2, -0x56 -2 );
	tilemap_set_scrolldx( tilemap_2, -0x6e +2, -0x55 -2 );
	
			tilemap_set_scrolldy( tilemap_0, -0x11, -0x100 );
			tilemap_set_scrolldy( tilemap_1, -0x11, -0x100 );
			tilemap_set_scrolldy( tilemap_2, -0x11, -0x100 );
	//tilemap_set_scrolldy( tilemap_2, -0x11 +8, -0x100 -8 );
	
			sprite_list.max_priority = 3;
			sprite_list.sprite_type = SPRITE_TYPE_ZOOM;
	
			return 0;
		}
		else return 1;
	} };
	
	
	
	/* 3 Layers */
	public static VhStartPtr esprade_vh_start = new VhStartPtr() { public int handler() 
	{
		tilemap_0 = tilemap_create(	get_tile_info_0,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		tilemap_1 = tilemap_create(	get_tile_info_1,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		tilemap_2 = tilemap_create(	get_tile_info_2,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
	
		sprite_list = sprite_list_create(spriteram_size[0] / 0x10 / 2, SPRITE_LIST_BACK_TO_FRONT | SPRITE_LIST_RAW_DATA );
	
		if (tilemap_0!=null && tilemap_1!=null && tilemap_2!=null && sprite_list!=null)
		{
			tilemap_set_scroll_rows(tilemap_0,1);
			tilemap_set_scroll_cols(tilemap_0,1);
			tilemap_0.transparent_pen = 0;
	
			tilemap_set_scroll_rows(tilemap_1,1);
			tilemap_set_scroll_cols(tilemap_1,1);
			tilemap_1.transparent_pen = 0;
	
			tilemap_set_scroll_rows(tilemap_2,1);
			tilemap_set_scroll_cols(tilemap_2,1);
			tilemap_2.transparent_pen = 0;
	
			tilemap_set_scrolldx( tilemap_0, -0x6c, -0x57 );
			tilemap_set_scrolldx( tilemap_1, -0x6d, -0x56 );
			tilemap_set_scrolldx( tilemap_2, -0x6e, -0x55 );
	
			tilemap_set_scrolldy( tilemap_0, -0x11, -0x100 );
			tilemap_set_scrolldy( tilemap_1, -0x11, -0x100 );
			tilemap_set_scrolldy( tilemap_2, -0x11, -0x100 );
	
			sprite_list.max_priority = 3;
			sprite_list.sprite_type = SPRITE_TYPE_ZOOM;
	
			return 0;
		}
		else return 1;
	} };
	
	
	
	/* 2 Layers */
	public static VhStartPtr dfeveron_vh_start = new VhStartPtr() { public int handler() 
	{
		tilemap_0 = tilemap_create(	get_tile_info_0,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		tilemap_1 = tilemap_create(	get_tile_info_1,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		tilemap_2 = null;
	
		sprite_list = sprite_list_create(spriteram_size[0] / 0x10 / 2, SPRITE_LIST_BACK_TO_FRONT | SPRITE_LIST_RAW_DATA );
	
		if (tilemap_0!=null && tilemap_1!=null && sprite_list!=null)
		{
			tilemap_set_scroll_rows(tilemap_0,1);
			tilemap_set_scroll_cols(tilemap_0,1);
			tilemap_0.transparent_pen = 0;
	
			tilemap_set_scroll_rows(tilemap_1,1);
			tilemap_set_scroll_cols(tilemap_1,1);
			tilemap_1.transparent_pen = 0;
	
	/*
		Scroll registers (on dfeveron logo screen):
			8195	a1f7 (both)	=	200-6b	200-9	(flip off)
			01ac	2108 (both)	=	200-54	100+8	(flip on)
		Video registers:
			0183	0001		=	200-7d	001		(flip off)
			81bf	80f0		=	200-41	100-10	(flip on)
	*/
	
			tilemap_set_scrolldx( tilemap_0, -0x6c, -0x54 );
			tilemap_set_scrolldx( tilemap_1, -0x6d, -0x53 );
	
			tilemap_set_scrolldy( tilemap_0, -0x11, -0x100 );
			tilemap_set_scrolldy( tilemap_1, -0x11, -0x100 );
	
			sprite_list.max_priority = 3;
			sprite_list.sprite_type = SPRITE_TYPE_ZOOM;
	
			return 0;
		}
		else return 1;
	} };
	
	
	
	/* 1 Layer */
	public static VhStartPtr uopoko_vh_start = new VhStartPtr() { public int handler() 
	{
		tilemap_0 = tilemap_create(	get_tile_info_0,
									tilemap_scan_rows,
									TILEMAP_TRANSPARENT,
									16,16,
									DIM_NX,DIM_NY );
	
		tilemap_1 = null;
	
		tilemap_2 = null;
	
		sprite_list = sprite_list_create(spriteram_size[0] / 0x10 / 2, SPRITE_LIST_BACK_TO_FRONT | SPRITE_LIST_RAW_DATA );
	
		if (tilemap_0!=null && sprite_list!=null)
		{
			tilemap_set_scroll_rows(tilemap_0,1);
			tilemap_set_scroll_cols(tilemap_0,1);
			tilemap_0.transparent_pen = 0;
	
			tilemap_set_scrolldx( tilemap_0, -0x6d, -0x54 );
	
			tilemap_set_scrolldy( tilemap_0, -0x11, -0x100 );
	
			sprite_list.max_priority = 3;
			sprite_list.sprite_type = SPRITE_TYPE_ZOOM;
	
			return 0;
		}
		else return 1;
	} };
	
	
	
	/***************************************************************************
	
								Vh_Init_Palette
	
	***************************************************************************/
	
	/* Function needed for games with 4 bit sprites, rather than 8 bit */
	
	
	public static VhConvertColorPromPtr dfeveron_vh_init_palette = new VhConvertColorPromPtr() { public void handler(char []palette, char []colortable, UBytePtr color_prom) 
	{
		int color, pen;
	
		/* Fill the 0-3fff range, used by sprites ($40 color codes * $100 pens)
		   Here sprites have 16 pens, but the sprite drawing routine always
		   multiplies the color code by $100 (for consistency).
		   That's why we need this function.	*/
	
		for( color = 0; color < 0x40; color++ )
			for( pen = 0; pen < 16; pen++ )
				colortable[color * 256 + pen] = (char) (color * 16 + pen);
	} };
	
	
	
	public static VhConvertColorPromPtr ddonpach_vh_init_palette = new VhConvertColorPromPtr() { public void handler(char []palette, char []colortable, UBytePtr color_prom) 
	{
		int color, pen;
	
		/* Fill the 8000-83ff range ($40 color codes * $10 pens) for
		   layers 1 & 2 which are 4 bits deep rather than 8 bits deep
		   like layer 3, but use the first 16 color of every 256 for
		   any given color code. */
	
		for( color = 0; color < 0x40; color++ )
			for( pen = 0; pen < 16; pen++ )
				colortable[color * 16 + pen + 0x8000] = (char) (0x4000 + color * 256 + pen);
	} };
	
	
	
	
	
	/***************************************************************************
	
								Sprites Drawing
	
	***************************************************************************/
	
	
	/* --------------------------[ Sprites Format ]----------------------------
	
	Offset:		Format:					Value:
	
	00.w		fedc ba98 76-- ----		X Position
				---- ---- --54 3210
	
	02.w		fedc ba98 76-- ----		Y Position
				---- ---- --54 3210
	
	04.w		fe-- ---- ---- ----
				--dc ba98 ---- ----		Color
				---- ---- 76-- ----
				---- ---- --54 ----		Priority
				---- ---- ---- 3---		Flip X
				---- ---- ---- -2--		Flip Y
				---- ---- ---- --10		Code High Bit(s?)
	
	06.w								Code Low Bits
	
	08/0A.w								Zoom X / Y
	
	0C.w		fedc ba98 ---- ----		Tile Size X
				---- ---- 7654 3210		Tile Size Y
	
	0E.w								Unused
	
	------------------------------------------------------------------------ */
	
	static void get_sprite_info()
	{
		int region				=	REGION_GFX4;
	
		UShortArray base_pal	=	new UShortArray(Machine.remapped_colortable, 0);
		UBytePtr base_gfx	=	new UBytePtr(memory_region(region));
		UBytePtr gfx_max	=	new UBytePtr(base_gfx, memory_region_length(region));
	
		int sprite_bank					=	cave_videoregs.READ_WORD(8) & 1;
	
		UBytePtr source			=	new UBytePtr(spriteram, (spriteram_size[0] / 2) * sprite_bank);
		sprite[] sprite			=	sprite_list.sprite;
                int _sprite=0;
		sprite finish		=	/*sprite[ spriteram_size[0] / 0x10 / 2 ]*/ sprite[sprite.length-1];
                int _finish=spriteram_size[0] / 0x10 / 2;
	
		int	glob_flipx	=	cave_videoregs.READ_WORD(0) & 0x8000;
		int	glob_flipy	=	cave_videoregs.READ_WORD(2) & 0x8000;
	
		int max_x		=	Machine.drv.screen_width;
		int max_y		=	Machine.drv.screen_height;
	
		for (; _sprite < _finish; _sprite++,source.inc(0x10) )
		{
			int x,y,attr,code,zoomx,zoomy,size,flipx,flipy;
			if ( cave_spritetype == 0)	// most of the games
			{
				x			=		source.READ_WORD( 0x00 );
				y			=		source.READ_WORD( 0x02 );
				attr		=		source.READ_WORD( 0x04 );
				code		=		source.READ_WORD( 0x06 );
				zoomx		=		source.READ_WORD( 0x08 );
				zoomy		=		source.READ_WORD( 0x0a );
				size		=		source.READ_WORD( 0x0c );
			}
			else						// ddonpach
			{
				attr		=		source.READ_WORD( 0x00 );
				code		=		source.READ_WORD( 0x02 );
				x			=		source.READ_WORD( 0x04 ) << 6;
				y			=		source.READ_WORD( 0x06 ) << 6;
				size		=		source.READ_WORD( 0x08 );
				zoomx		=		0x100;
				zoomy		=		0x100;
			}
	
			code		+=		(attr & 3) << 16;
	
			flipx		=		attr & 0x0008;
			flipy		=		attr & 0x0004;
	
			if ((x & 0x8000) != 0)	x -= 0x10000;
			if ((y & 0x8000) != 0)	y -= 0x10000;
	
			x /= 0x40;		y /= 0x40;
	
			sprite[_sprite].priority		=	(attr & 0x0030) >> 4;
			sprite[_sprite].flags			=	SPRITE_VISIBLE;
	
			sprite[_sprite].tile_width		=	( (size >> 8) & 0x1f ) * 16;
			sprite[_sprite].tile_height		=	( (size >> 0) & 0x1f ) * 16;
	
			sprite[_sprite].total_width		=	(sprite[_sprite].tile_width  * zoomx) / 0x100;
			sprite[_sprite].total_height	=	(sprite[_sprite].tile_height * zoomy) / 0x100;
	
			sprite[_sprite].pen_data		=	new UBytePtr(base_gfx, (16*16) * code);
			sprite[_sprite].line_offset		=	sprite[_sprite].tile_width;
	
			sprite[_sprite].pal_data		=	new UShortArray(base_pal, (attr & 0x3f00));	// first 0x4000 colors
	
			/* Bound checking */
/*TODO*///			if ((sprite[_sprite].pen_data + sprite[_sprite].tile_width * sprite[_sprite].tile_height - 1) >= gfx_max )
/*TODO*///				{sprite[_sprite].flags = 0;	continue;}
	
			if (glob_flipx != 0)	{ x = max_x - x - sprite[_sprite].total_width;	flipx = flipx!=0?0:1; }
			if (glob_flipy != 0)	{ y = max_y - y - sprite[_sprite].total_height;	flipy = flipy!=0?0:1; }
	
			sprite[_sprite].x				=	x;
			sprite[_sprite].y				=	y;
	
			if (flipx != 0)	sprite[_sprite].flags |= SPRITE_FLIPX;
			if (flipy != 0)	sprite[_sprite].flags |= SPRITE_FLIPY;
	
	
/*TODO*///	#if 0
/*TODO*///	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///			if ( keyboard_pressed(KEYCODE_Z) && keyboard_pressed(KEYCODE_N) )
/*TODO*///			{
/*TODO*///				struct DisplayText dt[3];
/*TODO*///				char buf1[40],buf2[40];
/*TODO*///	
/*TODO*///				if ( (zoomx == 0x100) && (zoomy == 0x100) )	continue;
/*TODO*///	
/*TODO*///				dt[0].text = buf1;		dt[1].text = buf2;	dt[2].text = 0;
/*TODO*///				dt[0].color			=	dt[1].color = UI_COLOR_NORMAL;
/*TODO*///				dt[0].x = y;			dt[1].x = dt[0].x;
/*TODO*///				dt[0].y = max_x-x;		dt[1].y = dt[0].y + 8;
/*TODO*///	
/*TODO*///				sprintf(buf1, "1:%04X", zoomx);
/*TODO*///				sprintf(buf2, "2:%04X", zoomy);
/*TODO*///				displaytext(dt,0,0);
/*TODO*///			}
/*TODO*///	#endif
/*TODO*///	
/*TODO*///	#endif
	
		}
	}
	
	
	
	/***************************************************************************
	
									Screen Drawing
	
	***************************************************************************/
	
	public static VhUpdatePtr cave_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int pri;
		int layers_ctrl = -1;
	
		int	glob_flipx	=	cave_videoregs.READ_WORD(0) & 0x8000;
		int	glob_flipy	=	cave_videoregs.READ_WORD(2) & 0x8000;
	
		tilemap_set_flip(ALL_TILEMAPS, (glob_flipx!=0 ? TILEMAP_FLIPX : 0) | (glob_flipy!=0 ? TILEMAP_FLIPY : 0) );
	
		tilemap_set_enable( tilemap_0, cave_vctrl_0.READ_WORD(4) & 1 );
		tilemap_set_scrollx(tilemap_0, 0, cave_vctrl_0.READ_WORD(0) );
		tilemap_set_scrolly(tilemap_0, 0, cave_vctrl_0.READ_WORD(2) );
	
		if (tilemap_1 != null)
		{
			tilemap_set_enable( tilemap_1, cave_vctrl_1.READ_WORD(4) & 1 );
			tilemap_set_scrollx(tilemap_1, 0, cave_vctrl_1.READ_WORD(0) );
			tilemap_set_scrolly(tilemap_1, 0, cave_vctrl_1.READ_WORD(2) );
		}
	
		if (tilemap_2 != null)
		{
			tilemap_set_enable( tilemap_2, cave_vctrl_2.READ_WORD(4) & 1 );
			tilemap_set_scrollx(tilemap_2, 0, cave_vctrl_2.READ_WORD(0) );
			tilemap_set_scrolly(tilemap_2, 0, cave_vctrl_2.READ_WORD(2) );
		}
	
	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	if ( keyboard_pressed(KEYCODE_Z) || keyboard_pressed(KEYCODE_X) || keyboard_pressed(KEYCODE_C) ||
/*TODO*///	     keyboard_pressed(KEYCODE_V) || keyboard_pressed(KEYCODE_B) )
/*TODO*///	{
/*TODO*///		int msk = 0, val = 0;
/*TODO*///	
/*TODO*///		if (keyboard_pressed(KEYCODE_X))	val = 1;	// priority 0 only
/*TODO*///		if (keyboard_pressed(KEYCODE_C))	val = 2;	// ""       1
/*TODO*///		if (keyboard_pressed(KEYCODE_V))	val = 4;	// ""       2
/*TODO*///		if (keyboard_pressed(KEYCODE_B))	val = 8;	// ""       3
/*TODO*///	
/*TODO*///		if (keyboard_pressed(KEYCODE_Z))	val = 1|2|4|8;	// All of the above priorities
/*TODO*///	
/*TODO*///		if (keyboard_pressed(KEYCODE_Q))	msk |= val << 0;	// for layer 0
/*TODO*///		if (keyboard_pressed(KEYCODE_W))	msk |= val << 4;	// for layer 1
/*TODO*///		if (keyboard_pressed(KEYCODE_E))	msk |= val << 8;	// for layer 2
/*TODO*///		if (keyboard_pressed(KEYCODE_A))	msk |= val << 12;	// for sprites
/*TODO*///		if (msk != 0) layers_ctrl &= msk;
/*TODO*///	
/*TODO*///	#if 1
/*TODO*///		{
/*TODO*///			String buf="";
/*TODO*///			buf = sprintf("%04X %04X %04X %04X %04X %04X %04X %04X",
/*TODO*///				cave_videoregs.READ_WORD(0x0),cave_videoregs.READ_WORD(0x2),
/*TODO*///				cave_videoregs.READ_WORD(0x4),cave_videoregs.READ_WORD(0x6),
/*TODO*///				cave_videoregs.READ_WORD(0x8),cave_videoregs.READ_WORD(0xa),
/*TODO*///				cave_videoregs.READ_WORD(0xc),cave_videoregs.READ_WORD(0xe) );
/*TODO*///			usrintf_showmessage(buf);
/*TODO*///		}
/*TODO*///	#endif
/*TODO*///	
/*TODO*///	}
/*TODO*///	#endif
	
		tilemap_update(ALL_TILEMAPS);
	
		palette_init_used_colors();
	
		get_sprite_info();
	
		sprite_update();
	
		if (palette_recalc() != null)	tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		/* Clear the background if at least one of layer 0's tile priorities
		   is lacking */
	
		if ((layers_ctrl & 0xf) != 0xf)	osd_clearbitmap(Machine.scrbitmap);
	
		/* Pen 0 of layer 0's tiles (any priority) goes below anything else */
	
		for ( pri = 0; pri < 4; pri++ )
			if ((layers_ctrl&(1<<(pri+0)))!=0&&tilemap_0!=null)	tilemap_draw(bitmap, tilemap_0, TILEMAP_IGNORE_TRANSPARENCY | pri);
	
		/* Draw the rest with transparency */
	
		for ( pri = 0; pri < 4; pri++ )
		{
			if (((layers_ctrl&(1<<(pri+12))))!=0)			sprite_draw(sprite_list, pri);
			if ((layers_ctrl&(1<<(pri+0)))!=0&&tilemap_0!=null)	tilemap_draw(bitmap, tilemap_0, pri);
			if ((layers_ctrl&(1<<(pri+4)))!=0&&tilemap_1!=null)	tilemap_draw(bitmap, tilemap_1, pri);
			if ((layers_ctrl&(1<<(pri+8)))!=0&&tilemap_2!=null)	tilemap_draw(bitmap, tilemap_2, pri);
		}
	} };
}
