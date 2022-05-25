/***************************************************************************

   Tumblepop Video emulation - Bryan McPhail, mish@tendril.co.uk

*********************************************************************

Uses Data East custom chip 55 for backgrounds, custom chip 52 for sprites.

See Dark Seal & Caveman Ninja drivers for info on these chips.

Tumblepop is one of few games to take advantage of the playfields ability
to switch between 8*8 tiles and 16*16 tiles.

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
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

public class tumblep
{
	
	static UBytePtr tumblep_control_0 = new UBytePtr(16);
	public static UBytePtr tumblep_pf1_data=new UBytePtr(), tumblep_pf2_data=new UBytePtr();
	static struct_tilemap pf1_tilemap, pf1_alt_tilemap, pf2_tilemap;
	static UBytePtr gfx_base=new UBytePtr();
	static int gfx_bank,flipscreen;
	
	/******************************************************************************/
	
	static void tumblep_mark_sprite_colours()
	{
		int offs,color,i,pal_base;
                int[] colmask=new int[16];
                int[] pen_usage;
	
		palette_init_used_colors();
	
		pen_usage=Machine.gfx[3].pen_usage;
		pal_base = Machine.drv.gfxdecodeinfo[3].color_codes_start;
		for (color = 0;color < 16;color++) colmask[color] = 0;
	
		for (offs = 0;offs < 0x800;offs += 8)
		{
			int x,y,sprite,multi;
	
			sprite = spriteram.READ_WORD (offs+2) & 0x3fff;
			if (sprite==0) continue;
	
			y = spriteram.READ_WORD(offs);
			x = spriteram.READ_WORD(offs+4);
			color = (x >>9) &0xf;
	
			multi = (1 << ((y & 0x0600) >> 9)) - 1;	/* 1x, 2x, 4x, 8x height */
	
			sprite &= ~multi;
	
			while (multi >= 0)
			{
				colmask[color] |= pen_usage[sprite + multi];
				multi--;
			}
		}
	
		for (color = 0;color < 16;color++)
		{
			for (i = 1;i < 16;i++)
			{
				if ((colmask[color] & (1 << i)) != 0)
					palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
			}
		}
	
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	}
	
	static void tumblep_drawsprites(osd_bitmap bitmap)
	{
		int offs;
	
		for (offs = 0;offs < 0x800;offs += 8)
		{
			int x,y,sprite,colour,multi,fx,fy,inc,flash,mult;
	
			sprite = spriteram.READ_WORD (offs+2) & 0x3fff;
			if (sprite==0) continue;
	
			y = spriteram.READ_WORD(offs);
			flash=y&0x1000;
			if (flash!=0 && (cpu_getcurrentframe() & 1)!=0) continue;
	
			x = spriteram.READ_WORD(offs+4);
			colour = (x >>9) & 0xf;
	
			fx = y & 0x2000;
			fy = y & 0x4000;
			multi = (1 << ((y & 0x0600) >> 9)) - 1;	/* 1x, 2x, 4x, 8x height */
	
			x = x & 0x01ff;
			y = y & 0x01ff;
			if (x >= 320) x -= 512;
			if (y >= 256) y -= 512;
			y = 240 - y;
	        x = 304 - x;
	
			sprite &= ~multi;
			if (fy != 0)
				inc = -1;
			else
			{
				sprite += multi;
				inc = 1;
			}
	
			if (flipscreen != 0)
			{
				y=240-y;
				x=304-x;
				if (fx != 0) fx=0; else fx=1;
				if (fy != 0) fy=0; else fy=1;
				mult=16;
			}
			else mult=-16;
	
			while (multi >= 0)
			{
				drawgfx(bitmap,Machine.gfx[3],
						sprite - multi * inc,
						colour,
						fx,fy,
						x,y + mult * multi,
						Machine.visible_area,TRANSPARENCY_PEN,0);
	
				multi--;
			}
		}
	}
	
	/******************************************************************************/
	
	public static WriteHandlerPtr tumblep_pf1_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(tumblep_pf1_data,offset,data);
		tilemap_mark_tile_dirty(pf1_tilemap,offset/2);
		tilemap_mark_tile_dirty(pf1_alt_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr tumblep_pf2_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(tumblep_pf2_data,offset,data);
		tilemap_mark_tile_dirty(pf2_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr tumblep_control_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(tumblep_control_0,offset,data);
	} };
	
	/******************************************************************************/
	
	static GetMemoryOffsetPtr tumblep_scan = new GetMemoryOffsetPtr() {
            @Override
            public int handler(int col, int row, int num_cols, int num_rows) {
                /* logical (col,row) . memory offset */
		return (col & 0x1f) + ((row & 0x1f) << 5) + ((col & 0x20) << 5);
            }
        };
        
	public static GetTileInfoPtr get_bg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile,color;
	
		tile=gfx_base.READ_WORD(2*tile_index);
		color=tile >> 12;
		tile=tile&0xfff;
	
		SET_TILE_INFO(gfx_bank,tile,color);
	} };
	
	public static GetTileInfoPtr get_fg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile=tumblep_pf1_data.READ_WORD(2*tile_index);
		int color=tile >> 12;
	
		tile=tile&0xfff;
		SET_TILE_INFO(0,tile,color);
	} };
	
	public static VhStartPtr tumblep_vh_start = new VhStartPtr() { public int handler() 
	{
		pf1_tilemap =     tilemap_create(get_fg_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT, 8, 8,64,32);
		pf1_alt_tilemap = tilemap_create(get_bg_tile_info,tumblep_scan,TILEMAP_TRANSPARENT,16,16,64,32);
		pf2_tilemap =     tilemap_create(get_bg_tile_info,tumblep_scan,TILEMAP_OPAQUE,     16,16,64,32);
	
		if (pf1_tilemap==null || pf1_alt_tilemap==null || pf2_tilemap==null)
			return 1;
	
		pf1_tilemap.transparent_pen = 0;
		pf1_alt_tilemap.transparent_pen = 0;
	
		return 0;
	} };
	
	/******************************************************************************/
	
	public static VhUpdatePtr tumblep_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int offs;
	
		flipscreen=(tumblep_control_0.READ_WORD(0)&0x80);
		tilemap_set_flip(ALL_TILEMAPS,flipscreen!=0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
		if (flipscreen != 0) offs=1; else offs=-1;
	
		tilemap_set_scrollx( pf1_tilemap,0, tumblep_control_0.READ_WORD(2)+offs );
		tilemap_set_scrolly( pf1_tilemap,0, tumblep_control_0.READ_WORD(4) );
		tilemap_set_scrollx( pf1_alt_tilemap,0, tumblep_control_0.READ_WORD(2)+offs );
		tilemap_set_scrolly( pf1_alt_tilemap,0, tumblep_control_0.READ_WORD(4) );
		tilemap_set_scrollx( pf2_tilemap,0, tumblep_control_0.READ_WORD(6)+offs );
		tilemap_set_scrolly( pf2_tilemap,0, tumblep_control_0.READ_WORD(8) );
	
		gfx_bank=1;
		gfx_base=tumblep_pf2_data;
		tilemap_update(pf2_tilemap);
		gfx_bank=2;
		gfx_base=tumblep_pf1_data;
		tilemap_update(pf1_alt_tilemap);
		tilemap_update(pf1_tilemap);
	
		tumblep_mark_sprite_colours();
		tilemap_render(ALL_TILEMAPS);
	
		tilemap_draw(bitmap,pf2_tilemap,0);
		if ((tumblep_control_0.READ_WORD(0xc)&0x80) != 0)
			tilemap_draw(bitmap,pf1_tilemap,0);
		else
			tilemap_draw(bitmap,pf1_alt_tilemap,0);
		tumblep_drawsprites(bitmap);
	} };
	
	public static VhUpdatePtr tumblepb_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int offs,offs2;
	
		flipscreen=(tumblep_control_0.READ_WORD(0)&0x80);
		tilemap_set_flip(ALL_TILEMAPS,flipscreen!=0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
		if (flipscreen != 0) offs=1; else offs=-1;
		if (flipscreen != 0) offs2=-3; else offs2=-5;
	
		tilemap_set_scrollx( pf1_tilemap,0, tumblep_control_0.READ_WORD(2)+offs2 );
		tilemap_set_scrolly( pf1_tilemap,0, tumblep_control_0.READ_WORD(4) );
		tilemap_set_scrollx( pf1_alt_tilemap,0, tumblep_control_0.READ_WORD(2)+offs2 );
		tilemap_set_scrolly( pf1_alt_tilemap,0, tumblep_control_0.READ_WORD(4) );
		tilemap_set_scrollx( pf2_tilemap,0, tumblep_control_0.READ_WORD(6)+offs );
		tilemap_set_scrolly( pf2_tilemap,0, tumblep_control_0.READ_WORD(8) );
	
		gfx_bank=1;
		gfx_base=tumblep_pf2_data;
		tilemap_update(pf2_tilemap);
		gfx_bank=2;
		gfx_base=tumblep_pf1_data;
		tilemap_update(pf1_tilemap);
		tilemap_update(pf1_alt_tilemap);
	
		tumblep_mark_sprite_colours();
		tilemap_render(ALL_TILEMAPS);
	
		tilemap_draw(bitmap,pf2_tilemap,0);
		if ((tumblep_control_0.READ_WORD(0xc)&0x80) != 0)
			tilemap_draw(bitmap,pf1_tilemap,0);
		else
			tilemap_draw(bitmap,pf1_alt_tilemap,0);
		tumblep_drawsprites(bitmap);
	} };
}
