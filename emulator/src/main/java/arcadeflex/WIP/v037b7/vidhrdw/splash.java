/***************************************************************************

  vidhrdw.c

  Functions to emulate the video hardware of the machine.

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.common.ptrLib.*;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static arcadeflex.v037b7.mame.common.bitmap_alloc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;

public class splash
{
	
	public static UBytePtr splash_vregs = new UBytePtr();
	public static UBytePtr splash_videoram = new UBytePtr();
	public static UBytePtr splash_spriteram = new UBytePtr();
	public static UBytePtr splash_pixelram = new UBytePtr();
	
	static struct_tilemap[] screen = new struct_tilemap[2];
	static osd_bitmap screen2;
	
	
	/***************************************************************************
	
		Callbacks for the TileMap code
	
	***************************************************************************/
	
	/*
		Tile format
		-----------
	
		Screen 0: (64*32, 8x8 tiles)
	
		Byte | Bit(s)			 | Description
		-----+-FEDCBA98-76543210-+--------------------------
		  0  | -------- xxxxxxxx | sprite code (low 8 bits)
		  0  | ----xxxx -------- | sprite code (high 4 bits)
		  0  | xxxx---- -------- | color
	
		Screen 1: (32*32, 16x16 tiles)
	
		Byte | Bit(s)			 | Description
		-----+-FEDCBA98-76543210-+--------------------------
		  0  | -------- -------x | flip y
		  0  | -------- ------x- | flip x
		  0  | -------- xxxxxx-- | sprite code (low 6 bits)
		  0  | ----xxxx -------- | sprite code (high 4 bits)
		  0  | xxxx---- -------- | color
	*/
	
	public static GetTileInfoPtr get_tile_info_splash_screen0 = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int data = splash_videoram.READ_WORD(2*tile_index);
		int attr = data >> 8;
		int code = data & 0xff;
	
		SET_TILE_INFO(0, code + ((0x20 + (attr & 0x0f)) << 8), (attr & 0xf0) >> 4);
	} };
	
	public static GetTileInfoPtr get_tile_info_splash_screen1 = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int data = splash_videoram.READ_WORD(0x1000 + 2*tile_index);
		int attr = data >> 8;
		int code = data & 0xff;
	
		tile_info.u32_flags = TILE_FLIPXY(code & 0x03);
	
		SET_TILE_INFO(1, (code >> 2) + ((0x30 + (attr & 0x0f)) << 6), (attr & 0xf0) >> 4);
	} };
	
	/***************************************************************************
	
		Memory Handlers
	
	***************************************************************************/
	
	public static ReadHandlerPtr splash_vram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return splash_videoram.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr splash_vram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(splash_videoram,offset,data);
		tilemap_mark_tile_dirty(screen[offset >> 12],(offset & 0x0fff) >> 1);
	} };
	
	public static ReadHandlerPtr splash_pixelram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return splash_pixelram.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr splash_pixelram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int sx,sy,color;
	
		COMBINE_WORD_MEM(splash_pixelram,offset,data);
	
		sx = (offset >> 1) & 0x1ff;
		sy = (offset >> 10);
	
		color = splash_pixelram.READ_WORD(offset);
	
		plot_pixel.handler(screen2, sx-9, sy, Machine.pens[0x300 + (color & 0xff)]);
	} };
	
	
	/***************************************************************************
	
		Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStartPtr splash_vh_start = new VhStartPtr() { public int handler() 
	{
		screen[0] = tilemap_create(get_tile_info_splash_screen0,tilemap_scan_rows,TILEMAP_TRANSPARENT, 8, 8,64,32);
		screen[1] = tilemap_create(get_tile_info_splash_screen1,tilemap_scan_rows,TILEMAP_TRANSPARENT,16,16,32,32);
		screen2 = bitmap_alloc (512, 256);
	
		if (screen[0]==null || screen[1]==null || screen2==null)
			return 1;
	
		screen[0].transparent_pen = 0;
		screen[1].transparent_pen = 0;
	
		tilemap_set_scrollx(screen[0], 0, 4);
	
		return 0;
	} };
	
	/***************************************************************************
	
		Sprites
	
	***************************************************************************/
	
	/*
		Sprite Format
		-------------
	
		Byte | Bit(s)   | Description
		-----+-76543210-+--------------------------
		  0  | xxxxxxxx | sprite number (low 8 bits)
		  1  | xxxxxxxx | y position
		  2  | xxxxxxxx | x position (low 8 bits)
		  3  | ----xxxx | sprite number (high 4 bits)
		  3  | --xx---- | unknown
		  3  | -x------ | flip x
		  3  | x------- | flip y
		  4  | ----xxxx | sprite color
		  4  | -xxx---- | unknown
		  4  | x------- | x position (high bit)
	*/
	
	static void splash_draw_sprites(osd_bitmap bitmap)
	{
		int i;
		GfxElement gfx = Machine.gfx[1];
	
		for (i = 0; i < 0x800; i += 8){
			int sx = splash_spriteram.READ_WORD(i+4) & 0xff;
			int sy = (240 - (splash_spriteram.READ_WORD(i+2) & 0xff)) & 0xff;
			int attr = splash_spriteram.READ_WORD(i+6) & 0xff;
			int attr2 = splash_spriteram.READ_WORD(i+0x800) >> 8;
			int number = (splash_spriteram.READ_WORD(i) & 0xff) + (attr & 0xf)*256;
	
			if ((attr2 & 0x80) != 0) sx += 256;
	
			drawgfx(bitmap,gfx,number,
				0x10 + (attr2 & 0x0f),attr & 0x40,attr & 0x80,
				sx-8,sy,
				Machine.visible_area,TRANSPARENCY_PEN,0);
		}
	}
	
	/***************************************************************************
	
		Display Refresh
	
	***************************************************************************/
	
	public static VhUpdatePtr splash_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		/* set scroll registers */
		tilemap_set_scrolly(screen[0], 0, splash_vregs.READ_WORD(0));
		tilemap_set_scrolly(screen[1], 0, splash_vregs.READ_WORD(2));
	
		tilemap_update(ALL_TILEMAPS);
	
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		copybitmap(bitmap,screen2,0,0,0,0,Machine.visible_area,TRANSPARENCY_NONE,0);
	
		tilemap_draw(bitmap,screen[1],0);
		splash_draw_sprites(bitmap);
		tilemap_draw(bitmap,screen[0],0);
	} };
}
