/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static gr.codebb.arcadeflex.common.libc.cstring.*;  
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.copyscrollbitmap;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*; 
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;        
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;        
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.old.mame.drawgfx.plot_pixel;

public class playmark
{
	
	public static UBytePtr bigtwin_bgvideoram = new UBytePtr();
	public static int[] bigtwin_bgvideoram_size = new int[1];
	public static UBytePtr wbeachvl_videoram1 = new UBytePtr(), wbeachvl_videoram2 = new UBytePtr(), wbeachvl_videoram3 = new UBytePtr();
	static osd_bitmap bgbitmap;
	static int bgscrollx,bgscrolly;
	static struct_tilemap tx_tilemap, fg_tilemap, bg_tilemap;
	
	
	
	/***************************************************************************
	
	  Callbacks for the TileMap code
	
	***************************************************************************/
	
	public static GetTileInfoPtr bigtwin_get_tx_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = wbeachvl_videoram1.READ_WORD(4*tile_index);
		int color = wbeachvl_videoram1.READ_WORD(4*tile_index+2);
		SET_TILE_INFO(2,code,color);
	} };
	
	public static GetTileInfoPtr bigtwin_get_fg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = wbeachvl_videoram2.READ_WORD(4*tile_index);
		int color = wbeachvl_videoram2.READ_WORD(4*tile_index+2);
		SET_TILE_INFO(1,code,color);
	} };
	
	
	public static GetTileInfoPtr wbeachvl_get_tx_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = wbeachvl_videoram1.READ_WORD(4*tile_index);
		int color = wbeachvl_videoram1.READ_WORD(4*tile_index+2);
		SET_TILE_INFO(2,code,color / 4);
	} };
	
	public static GetTileInfoPtr wbeachvl_get_fg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = wbeachvl_videoram2.READ_WORD(4*tile_index);
		int color = wbeachvl_videoram2.READ_WORD(4*tile_index+2);
		SET_TILE_INFO(1,code & 0x7fff,color / 4 + 8);
		tile_info.u32_flags = (code & 0x8000)!=0 ? TILE_FLIPX : 0;
	} };
	
	public static GetTileInfoPtr wbeachvl_get_bg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = wbeachvl_videoram3.READ_WORD(4*tile_index);
		int color = wbeachvl_videoram3.READ_WORD(4*tile_index+2);
		SET_TILE_INFO(1,code & 0x7fff,color / 4);
		tile_info.u32_flags = (code & 0x8000)!=0 ? TILE_FLIPX : 0;
	} };
	
	
	
	/***************************************************************************
	
	  Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStopPtr bigtwin_vh_stop = new VhStopPtr() { public void handler() 
	{
		bitmap_free(bgbitmap);
		bgbitmap = null;
	} };
	
	public static VhStartPtr bigtwin_vh_start = new VhStartPtr() { public int handler() 
	{
		bgbitmap = bitmap_alloc(512,512);
	
		tx_tilemap = tilemap_create(bigtwin_get_tx_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT, 8, 8,64,32);
		fg_tilemap = tilemap_create(bigtwin_get_fg_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,16,16,32,16);
	
		if (tx_tilemap==null || fg_tilemap==null || bgbitmap==null)
		{
			bigtwin_vh_stop.handler();
			return 1;
		}
	
		tx_tilemap.transparent_pen = 0;
		fg_tilemap.transparent_pen = 0;
	
		return 0;
	} };
	
	
	public static VhStartPtr wbeachvl_vh_start = new VhStartPtr() { public int handler() 
	{
		tx_tilemap = tilemap_create(wbeachvl_get_tx_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT, 8, 8,64,32);
		fg_tilemap = tilemap_create(wbeachvl_get_fg_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,16,16,64,32);
		bg_tilemap = tilemap_create(wbeachvl_get_bg_tile_info,tilemap_scan_rows,TILEMAP_OPAQUE,     16,16,64,32);
	
		if (tx_tilemap==null || fg_tilemap==null || bg_tilemap==null)
			return 1;
	
		tx_tilemap.transparent_pen = 0;
		fg_tilemap.transparent_pen = 0;
	
		return 0;
	} };
	
	
	
	/***************************************************************************
	
	  Memory handlers
	
	***************************************************************************/
	
	public static ReadHandlerPtr wbeachvl_txvideoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return wbeachvl_videoram1.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr wbeachvl_txvideoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = wbeachvl_videoram1.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			wbeachvl_videoram1.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(tx_tilemap,offset / 4);
		}
	} };
	
	public static ReadHandlerPtr wbeachvl_fgvideoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return wbeachvl_videoram2.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr wbeachvl_fgvideoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = wbeachvl_videoram2.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			wbeachvl_videoram2.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(fg_tilemap,offset / 4);
		}
	} };
	
	public static ReadHandlerPtr wbeachvl_bgvideoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return wbeachvl_videoram3.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr wbeachvl_bgvideoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = wbeachvl_videoram3.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			wbeachvl_videoram3.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(bg_tilemap,offset / 4);
		}
	} };
	
	
	public static WriteHandlerPtr bigtwin_paletteram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int r,g,b,val;
	
	
		COMBINE_WORD_MEM(paletteram, offset, data);
	
		val = paletteram.READ_WORD(offset);
		r = (val >> 11) & 0x1e;
		g = (val >>  7) & 0x1e;
		b = (val >>  3) & 0x1e;
	
		r |= ((val & 0x08) >> 3);
		g |= ((val & 0x04) >> 2);
		b |= ((val & 0x02) >> 1);
	
		r = (r << 3) | (r >> 2);
		g = (g << 3) | (g >> 2);
		b = (b << 3) | (b >> 2);
	
		palette_change_color(offset / 2,r,g,b);
	} };
	
	public static WriteHandlerPtr bigtwin_bgvideoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int sx,sy,color;
	
	
		COMBINE_WORD_MEM(bigtwin_bgvideoram, offset, data);
	
		sx = (offset/2) % 512;
		sy = (offset/2) / 512;
	
		color = bigtwin_bgvideoram.READ_WORD(offset) & 0xff;
	
		plot_pixel.handler(bgbitmap,sx,sy,Machine.pens[256 + color]);
	} };
	
        static UBytePtr scroll = new UBytePtr(12);
	
	public static WriteHandlerPtr bigtwin_scroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	
		COMBINE_WORD_MEM(scroll, offset, data);
		data = scroll.READ_WORD(offset);
	
		switch (offset)
		{
			case 0x00: tilemap_set_scrollx(tx_tilemap,0,data+2); break;
			case 0x02: tilemap_set_scrolly(tx_tilemap,0,data);   break;
			case 0x04: bgscrollx = -(data+4);                    break;
			case 0x06: bgscrolly = (-data) & 0x1ff;              break;
			case 0x08: tilemap_set_scrollx(fg_tilemap,0,data+6); break;
			case 0x0a: tilemap_set_scrolly(fg_tilemap,0,data);   break;
		}
	} };
	
	public static WriteHandlerPtr wbeachvl_scroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		COMBINE_WORD_MEM(scroll, offset, data);
		data = scroll.READ_WORD(offset);
	
		switch (offset)
		{
			case 0x00: tilemap_set_scrollx(tx_tilemap,0,data+2); break;
			case 0x02: tilemap_set_scrolly(tx_tilemap,0,data);   break;
			case 0x04: tilemap_set_scrollx(fg_tilemap,0,data+4); break;
			case 0x06: tilemap_set_scrolly(fg_tilemap,0,data);   break;
			case 0x08: tilemap_set_scrollx(bg_tilemap,0,data+6); break;
			case 0x0a: tilemap_set_scrolly(bg_tilemap,0,data);   break;
		}
	} };
	
	
	
	/***************************************************************************
	
	  Display refresh
	
	***************************************************************************/
	
	static void draw_sprites(osd_bitmap bitmap,int codeshift)
	{
		int offs;
		int height = Machine.gfx[0].height;
		int colordiv = Machine.gfx[0].color_granularity / 16;
	
		for (offs = 8;offs < spriteram_size[0];offs += 8)
		{
			int sx,sy,code,color,flipx;
	
			sy = spriteram.READ_WORD(offs+6-8);	/* -8? what the... ??? */
			if (sy == 0x2000) return;	/* end of list marker */
	
			flipx = sy & 0x4000;
			sx = (spriteram.READ_WORD(offs+2) & 0x01ff) - 16-7;
			sy = (256-8-height - sy) & 0xff;
			code = spriteram.READ_WORD(offs+4) >> codeshift;
			color = (spriteram.READ_WORD(offs+2) & 0xfe00) >> 9;
	
			drawgfx(bitmap,Machine.gfx[0],
					code,
					color/colordiv,
					flipx,0,
					sx,sy,
					Machine.visible_area,TRANSPARENCY_PEN,0);
		}
	}
	
	
	public static VhUpdatePtr bigtwin_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		tilemap_update(ALL_TILEMAPS);
	
		palette_used_colors.write(256, PALETTE_COLOR_TRANSPARENT);	/* keep the background black */
		if (palette_recalc() != null)
		{
			int offs;
	
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
			for (offs = 0;offs < bigtwin_bgvideoram_size[0];offs += 2)
				bigtwin_bgvideoram_w.handler(offs,bigtwin_bgvideoram.READ_WORD(offs));
		}
	
		tilemap_render(ALL_TILEMAPS);
	
		copyscrollbitmap(bitmap,bgbitmap,1,new int[]{bgscrollx},1,new int[]{bgscrolly},Machine.visible_area,TRANSPARENCY_NONE,0);
		tilemap_draw(bitmap,fg_tilemap,0);
		draw_sprites(bitmap,4);
		tilemap_draw(bitmap,tx_tilemap,0);
	} };
	
	public static VhUpdatePtr wbeachvl_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		tilemap_update(ALL_TILEMAPS);
	
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		tilemap_draw(bitmap,bg_tilemap,0);
		tilemap_draw(bitmap,fg_tilemap,0);
		draw_sprites(bitmap,0);
		tilemap_draw(bitmap,tx_tilemap,0);
	} };
}
