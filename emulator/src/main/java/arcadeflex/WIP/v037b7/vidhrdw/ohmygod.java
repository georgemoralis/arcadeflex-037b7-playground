/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static arcadeflex.v037b7.mame.memoryH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;

public class ohmygod
{
	
	
	public static UBytePtr ohmygod_videoram = new UBytePtr();
	
	static int spritebank;
	static struct_tilemap bg_tilemap;
	
	
	
	/***************************************************************************
	
	  Callbacks for the TileMap code
	
	***************************************************************************/
	
	public static GetTileInfoPtr get_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = ohmygod_videoram.READ_WORD(4*tile_index+2);
		int attr = ohmygod_videoram.READ_WORD(4*tile_index);
		SET_TILE_INFO(0,code,(attr & 0x0f00) >> 8);
	} };
	
	
	
	/***************************************************************************
	
	  Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStartPtr ohmygod_vh_start = new VhStartPtr() { public int handler() 
	{
		bg_tilemap = tilemap_create(get_tile_info,tilemap_scan_rows,TILEMAP_OPAQUE,8,8,64,64);
	
		if (bg_tilemap==null)
			return 1;
	
		return 0;
	} };
	
	
	
	/***************************************************************************
	
	  Memory handlers
	
	***************************************************************************/
	
	public static ReadHandlerPtr ohmygod_videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	   return ohmygod_videoram.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr ohmygod_videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = ohmygod_videoram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			ohmygod_videoram.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(bg_tilemap,offset/4);
		}
	} };
	
	public static WriteHandlerPtr ohmygod_spritebank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		spritebank = data & 0x8000;
	} };
	
	public static WriteHandlerPtr ohmygod_scroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (offset == 0) tilemap_set_scrollx(bg_tilemap,0,data - 0x81ec);
		else tilemap_set_scrolly(bg_tilemap,0,data - 0x81ef);
	} };
	
	
	/***************************************************************************
	
	  Display refresh
	
	***************************************************************************/
	
	static void draw_sprites(osd_bitmap bitmap)
	{
		int offs;
	
		for (offs = 0;offs < spriteram_size[0];offs += 8)
		{
			int sx,sy,code,color,flipx;
			UBytePtr sr;
	
			sr = spritebank!=0 ? new UBytePtr(spriteram_2) : new UBytePtr(spriteram);
	
			code = sr.READ_WORD(offs+6) & 0x0fff;
			color = sr.READ_WORD(offs+4) & 0x000f;
			sx = sr.READ_WORD(offs+0) - 29;
			sy = sr.READ_WORD(offs+2);
			if (sy >= 32768) sy -= 65536;
			flipx = sr.READ_WORD(offs+6) & 0x8000;
	
			drawgfx(bitmap,Machine.gfx[1],
					code,
					color,
					flipx,0,
					sx,sy,
					Machine.visible_area,TRANSPARENCY_PEN,0);
		}
	}
	
	public static VhUpdatePtr ohmygod_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		tilemap_update(ALL_TILEMAPS);
	
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		tilemap_draw(bitmap,bg_tilemap,0);
		draw_sprites(bitmap);
	} };
}
