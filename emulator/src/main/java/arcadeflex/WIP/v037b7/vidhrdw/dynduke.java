
/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;        
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class dynduke
{
	
	static struct_tilemap bg_layer, fg_layer, tx_layer;
	public static UBytePtr dynduke_back_data = new UBytePtr(), dynduke_fore_data = new UBytePtr(), dynduke_scroll_ram = new UBytePtr(), dynduke_control_ram = new UBytePtr();
	
	static int flipscreen,back_bankbase,fore_bankbase,back_palbase;
	static int back_enable,fore_enable,sprite_enable;
	
	/******************************************************************************/
	
	public static WriteHandlerPtr dynduke_paletteram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int r,g,b;
	
		paletteram.write(offset,data);
		data=paletteram.read(offset&0xffe)|(paletteram.read(offset|1)<<8);
	
		r = (data >> 0) & 0x0f;
		g = (data >> 4) & 0x0f;
		b = (data >> 8) & 0x0f;
	
		r = (r << 4) | r;
		g = (g << 4) | g;
		b = (b << 4) | b;
	
		palette_change_color(offset/2,r,g,b);
	
		/* This is a kludge to handle 5bpp graphics but 4bpp palette data */
		if (offset<1024) {
			palette_change_color(((offset&0x1f)/2) | (offset&0xffe0) | 2048,r,g,b);
			palette_change_color(((offset&0x1f)/2) | (offset&0xffe0) | 2048 | 16,r,g,b);
		}
	} };
	
	public static ReadHandlerPtr dynduke_background_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return dynduke_back_data.read(offset);
	} };
	
	public static ReadHandlerPtr dynduke_foreground_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return dynduke_fore_data.read(offset);
	} };
	
	public static WriteHandlerPtr dynduke_background_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		dynduke_back_data.write(offset, data);
		tilemap_mark_tile_dirty(bg_layer,offset/2);
	} };
	
	public static WriteHandlerPtr dynduke_foreground_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		dynduke_fore_data.write(offset, data);
		tilemap_mark_tile_dirty(fg_layer,offset/2);
	} };
	
	public static WriteHandlerPtr dynduke_text_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		videoram.write(offset,data);
		tilemap_mark_tile_dirty(tx_layer,offset/2);
	} };
	
	public static GetTileInfoPtr get_bg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile=dynduke_back_data.read(2*tile_index)+(dynduke_back_data.read(2*tile_index+1)<<8);
		int color=tile >> 12;
	
		tile=tile&0xfff;
	
		SET_TILE_INFO(1,tile+back_bankbase,color+back_palbase);
	} };
	
	public static GetTileInfoPtr get_fg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile=dynduke_fore_data.read(2*tile_index)+(dynduke_fore_data.read(2*tile_index+1)<<8);
		int color=tile >> 12;
	
		tile=tile&0xfff;
	
		SET_TILE_INFO(2,tile+fore_bankbase,color);
	} };
	
	public static GetTileInfoPtr get_tx_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile=videoram.read(2*tile_index)+((videoram.read(2*tile_index+1)&0xc0)<<2);
		int color=videoram.read(2*tile_index+1)&0xf;
	
		SET_TILE_INFO(0,tile,color);
	} };
	
	public static VhStartPtr dynduke_vh_start = new VhStartPtr() { public int handler() 
	{
		bg_layer = tilemap_create(get_bg_tile_info,tilemap_scan_cols,TILEMAP_SPLIT,      16,16,32,32);
		fg_layer = tilemap_create(get_fg_tile_info,tilemap_scan_cols,TILEMAP_TRANSPARENT,16,16,32,32);
		tx_layer = tilemap_create(get_tx_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT, 8, 8,32,32);
	
		bg_layer.u32_transmask[0] = 0x0000ffff; /* 4bpp */
		bg_layer.u32_transmask[1] = 0xffff0000; /* The rest - 1bpp */
	
		fg_layer.transparent_pen = 15;
		tx_layer.transparent_pen = 15;
	
		return 0;
	} };
        
        static int old_back,old_fore;
	
	public static WriteHandlerPtr dynduke_gfxbank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		if ((data & 0x01) != 0) back_bankbase=0x1000; else back_bankbase=0;
		if ((data & 0x10) != 0) fore_bankbase=0x1000; else fore_bankbase=0;
	
		if (back_bankbase!=old_back)
			tilemap_mark_all_tiles_dirty(bg_layer);
		if (fore_bankbase!=old_fore)
			tilemap_mark_all_tiles_dirty(fg_layer);
	
		old_back=back_bankbase;
		old_fore=fore_bankbase;
	} };
        
        static int old_bpal;
	
	public static WriteHandlerPtr dynduke_control_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		dynduke_control_ram.write(offset, data);
	
		if (offset!=6) return;
	
		if ((data & 0x1) != 0) back_enable=0; else back_enable=1;
		if ((data & 0x2) != 0) back_palbase=16; else back_palbase=0;
		if ((data & 0x4) != 0) fore_enable=0; else fore_enable=1;
		if ((data & 0x8) != 0) sprite_enable=0; else sprite_enable=1;
	
		if (back_palbase!=old_bpal)
			tilemap_mark_all_tiles_dirty(bg_layer);
	
		old_bpal=back_palbase;
		flipscreen=data&0x40;
		tilemap_set_flip(ALL_TILEMAPS,flipscreen!=0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
	} };
	
	static void draw_sprites(osd_bitmap bitmap,int pri)
	{
		int offs,fx,fy,x,y,color,sprite;
	
		if (sprite_enable==0) return;
	
		for (offs = 0x1000-8;offs >= 0;offs -= 8)
		{
			/* Don't draw empty sprite table entries */
			if (buffered_spriteram.read(offs+7)!=0xf) continue;
			if (buffered_spriteram.read(offs+0)==0xf0f) continue;
			if (((buffered_spriteram.read(offs+5)>>5)&3)!=pri) continue;
	
			fx= buffered_spriteram.read(offs+1)&0x20;
			fy= buffered_spriteram.read(offs+1)&0x40;
			y = buffered_spriteram.read(offs+0);
			x = buffered_spriteram.read(offs+4);
	
			if ((buffered_spriteram.read(offs+5)&1)!=0) x=0-(0x100-x);
	
			color = buffered_spriteram.read(offs+1)&0x1f;
			sprite = buffered_spriteram.read(offs+2)+(buffered_spriteram.read(offs+3)<<8);
			sprite &= 0x3fff;
	
			if (flipscreen != 0) {
				x=240-x;
				y=240-y;
				if (fx != 0) fx=0; else fx=1;
				if (fy != 0) fy=0; else fy=1;
			}
	
			drawgfx(bitmap,Machine.gfx[3],
					sprite,
					color,fx,fy,x,y,
					Machine.visible_area,TRANSPARENCY_PEN,15);
		}
	}
	
	public static VhUpdatePtr dynduke_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int color,offs,sprite;
		int[] colmask = new int[32];
                int i,pal_base;
	
		/* Setup the tilemaps */
		tilemap_set_scrolly( bg_layer,0, ((dynduke_scroll_ram.read(0x02)&0x30)<<4)+((dynduke_scroll_ram.read(0x04)&0x7f)<<1)+((dynduke_scroll_ram.read(0x04)&0x80)>>7) );
		tilemap_set_scrollx( bg_layer,0, ((dynduke_scroll_ram.read(0x12)&0x30)<<4)+((dynduke_scroll_ram.read(0x14)&0x7f)<<1)+((dynduke_scroll_ram.read(0x14)&0x80)>>7) );
		tilemap_set_scrolly( fg_layer,0, ((dynduke_scroll_ram.read(0x22)&0x30)<<4)+((dynduke_scroll_ram.read(0x24)&0x7f)<<1)+((dynduke_scroll_ram.read(0x24)&0x80)>>7) );
		tilemap_set_scrollx( fg_layer,0, ((dynduke_scroll_ram.read(0x32)&0x30)<<4)+((dynduke_scroll_ram.read(0x34)&0x7f)<<1)+((dynduke_scroll_ram.read(0x34)&0x80)>>7) );
		tilemap_set_enable( bg_layer,back_enable);
		tilemap_set_enable( fg_layer,fore_enable);
		tilemap_update(ALL_TILEMAPS);
	
		/* Build the dynamic palette */
		palette_init_used_colors();
	
		/* Sprites */
		pal_base = Machine.drv.gfxdecodeinfo[3].color_codes_start;
		for (color = 0;color < 32;color++) colmask[color] = 0;
		for (offs = 0;offs <0x1000;offs += 8)
		{
			color = spriteram.read(offs+1)&0x1f;
			sprite = buffered_spriteram.read(offs+2)+(buffered_spriteram.read(offs+3)<<8);
			sprite &= 0x3fff;
			colmask[color] |= Machine.gfx[3].pen_usage[sprite];
		}
		for (color = 0;color < 32;color++)
		{
			for (i = 0;i < 15;i++)
			{
				if ((colmask[color] & (1 << i)) != 0)
					palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
			}
		}
	
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		if (back_enable != 0)
			tilemap_draw(bitmap,bg_layer,TILEMAP_BACK);
		else
			fillbitmap(bitmap,palette_transparent_pen,Machine.visible_area);
	
		draw_sprites(bitmap,0); /* Untested: does anything use it? Could be behind background */
		draw_sprites(bitmap,1);
		tilemap_draw(bitmap,bg_layer,TILEMAP_FRONT);
		draw_sprites(bitmap,2);
		tilemap_draw(bitmap,fg_layer,0);
		draw_sprites(bitmap,3);
		tilemap_draw(bitmap,tx_layer,0);
	} };
}
