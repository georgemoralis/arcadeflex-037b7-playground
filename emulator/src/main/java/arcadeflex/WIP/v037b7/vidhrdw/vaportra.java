/***************************************************************************

   Vapour Trail Video emulation - Bryan McPhail, mish@tendril.co.uk

****************************************************************************

	2 Data East 55 chips for playfields (same as Dark Seal, etc)
	1 Data East MXC-06 chip for sprites (same as Bad Dudes, etc)

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */

package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static gr.codebb.arcadeflex.common.libc.cstring.*;        
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

public class vaportra
{
	
	public static UBytePtr vaportra_pf1_data = new UBytePtr(), vaportra_pf2_data = new UBytePtr(), vaportra_pf3_data = new UBytePtr(), vaportra_pf4_data = new UBytePtr();
	
	public static UBytePtr vaportra_control_0 = new UBytePtr(16);
	public static UBytePtr vaportra_control_1 = new UBytePtr(16);
	public static UBytePtr vaportra_control_2 = new UBytePtr(4);
	
	static struct_tilemap pf1_tilemap, pf2_tilemap, pf3_tilemap, pf4_tilemap;
	static UBytePtr gfx_base = new UBytePtr();
	static int gfx_bank,flipscreen;
	
	static UBytePtr vaportra_spriteram = new UBytePtr();
	
	
	
	/* Function for all 16x16 1024x1024 layers */
	static GetMemoryOffsetPtr vaportra_scan = new GetMemoryOffsetPtr() {
            @Override
            public int handler(int col, int row, int num_cols, int num_rows) {
                /* logical (col,row) . memory offset */
		return (col & 0x1f) + ((row & 0x1f) << 5) + ((col & 0x20) << 5) + ((row & 0x20) << 6);
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
	
	/* 8x8 top layer */
	public static GetTileInfoPtr get_fg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile=vaportra_pf1_data.READ_WORD(2*tile_index);
		int color=tile >> 12;
	
		tile=tile&0xfff;
	
		SET_TILE_INFO(0,tile,color);
	} };
	
	/******************************************************************************/
	
	public static VhStopPtr vaportra_vh_stop = new VhStopPtr() { public void handler() 
	{
		vaportra_spriteram=null;
	} };
	
	public static VhStartPtr vaportra_vh_start = new VhStartPtr() { public int handler() 
	{
		pf1_tilemap = tilemap_create(get_fg_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT, 8, 8,64,64);
		pf2_tilemap = tilemap_create(get_bg_tile_info,vaportra_scan,    TILEMAP_TRANSPARENT,16,16,64,32);
		pf3_tilemap = tilemap_create(get_bg_tile_info,vaportra_scan,    TILEMAP_TRANSPARENT,16,16,64,32);
		pf4_tilemap = tilemap_create(get_bg_tile_info,vaportra_scan,    TILEMAP_TRANSPARENT,16,16,64,32);
	
		if (pf1_tilemap==null || pf2_tilemap==null || pf3_tilemap==null || pf4_tilemap==null)
			return 1;
	
		vaportra_spriteram = new UBytePtr(0x800);
		if (vaportra_spriteram==null)
			return 1;
	
		pf1_tilemap.transparent_pen = 0;
		pf2_tilemap.transparent_pen = 0;
		pf3_tilemap.transparent_pen = 0;
		pf4_tilemap.transparent_pen = 0;
	
		return 0;
	} };
	
	/******************************************************************************/
	
	public static ReadHandlerPtr vaportra_pf1_data_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return vaportra_pf1_data.READ_WORD(offset);
	} };
	
	public static ReadHandlerPtr vaportra_pf2_data_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return vaportra_pf2_data.READ_WORD(offset);
	} };
	
	public static ReadHandlerPtr vaportra_pf3_data_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return vaportra_pf3_data.READ_WORD(offset);
	} };
	
	public static ReadHandlerPtr vaportra_pf4_data_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return vaportra_pf4_data.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr vaportra_pf1_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(vaportra_pf1_data,offset,data);
		tilemap_mark_tile_dirty(pf1_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr vaportra_pf2_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(vaportra_pf2_data,offset,data);
		tilemap_mark_tile_dirty(pf2_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr vaportra_pf3_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(vaportra_pf3_data,offset,data);
		tilemap_mark_tile_dirty(pf3_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr vaportra_pf4_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(vaportra_pf4_data,offset,data);
		tilemap_mark_tile_dirty(pf4_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr vaportra_control_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(vaportra_control_0,offset,data);
	} };
	
	public static WriteHandlerPtr vaportra_control_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(vaportra_control_1,offset,data);
	} };
	
	public static WriteHandlerPtr vaportra_control_2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(vaportra_control_2,offset,data);
	} };
	
	/******************************************************************************/
	
	public static WriteHandlerPtr vaportra_update_sprites_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		memcpy(vaportra_spriteram,spriteram,0x800);
	} };
	
	static void update_24bitcol(int offset)
	{
		int r,g,b;
	
		r = (paletteram.READ_WORD(offset) >> 0) & 0xff;
		g = (paletteram.READ_WORD(offset) >> 8) & 0xff;
		b = (paletteram_2.READ_WORD(offset) >> 0) & 0xff;
	
		palette_change_color(offset / 2,r,g,b);
	}
	
	public static WriteHandlerPtr vaportra_palette_24bit_rg_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(paletteram,offset,data);
		update_24bitcol(offset);
	} };
	
	public static WriteHandlerPtr vaportra_palette_24bit_b_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(paletteram_2,offset,data);
		update_24bitcol(offset);
	} };
	
	/******************************************************************************/
	
	static void vaportra_update_palette()
	{
		int offs,color,i,pal_base;
		int[] colmask = new int[16];
	
		palette_init_used_colors();
	
		/* Sprites */
		pal_base = Machine.drv.gfxdecodeinfo[4].color_codes_start;
		for (color = 0;color < 16;color++) colmask[color] = 0;
		for (offs = 0;offs < 0x800;offs += 8)
		{
			int x,y,sprite,multi;
	
			y = vaportra_spriteram.READ_WORD(offs);
			if ((y&0x8000) == 0) continue;
	
			sprite = vaportra_spriteram.READ_WORD (offs+2) & 0x1fff;
	
			x = vaportra_spriteram.READ_WORD(offs+4);
			color = (x >> 12) &0xf;
	
			x = x & 0x01ff;
			if (x >= 256) x -= 512;
			x = 240 - x;
			if (x>256) continue; /* Speedup */
	
			multi = (1 << ((y & 0x1800) >> 11)) - 1;	/* 1x, 2x, 4x, 8x height */
			sprite &= ~multi;
	
			while (multi >= 0)
			{
				colmask[color] |= Machine.gfx[4].pen_usage[sprite + multi];
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
	
	static void vaportra_drawsprites(osd_bitmap bitmap, int pri)
	{
		int offs,priority_value;
	
		priority_value=vaportra_control_2.READ_WORD(2);
	
		for (offs = 0;offs < 0x800;offs += 8)
		{
			int x,y,sprite,colour,multi,fx,fy,inc,flash,mult;
	
			y = vaportra_spriteram.READ_WORD(offs);
			if ((y&0x8000) == 0) continue;
	
			sprite = vaportra_spriteram.READ_WORD (offs+2) & 0x1fff;
			x = vaportra_spriteram.READ_WORD(offs+4);
			colour = (x >> 12) &0xf;
			if (pri!=0 && (colour>=priority_value)) continue;
			if (pri==0 && !(colour>=priority_value)) continue;
	
			flash=x&0x800;
			if (flash!=0 && (cpu_getcurrentframe() & 1)!=0) continue;
	
			fx = y & 0x2000;
			fy = y & 0x4000;
			multi = (1 << ((y & 0x1800) >> 11)) - 1;	/* 1x, 2x, 4x, 8x height */
	
			x = x & 0x01ff;
			y = y & 0x01ff;
			if (x >= 256) x -= 512;
			if (y >= 256) y -= 512;
			x = 240 - x;
			y = 240 - y;
	
			if (x>256) continue; /* Speedup */
	
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
				x=240-x;
				if (fx != 0) fx=0; else fx=1;
				if (fy != 0) fy=0; else fy=1;
				mult=16;
			}
			else mult=-16;
	
			while (multi >= 0)
			{
				drawgfx(bitmap,Machine.gfx[4],
						sprite - multi * inc,
						colour,
						fx,fy,
						x,y + mult * multi,
						Machine.visible_area,TRANSPARENCY_PEN,0);
	
				multi--;
			}
		}
	}
	
	static int last_pri=0;
        
	public static VhUpdatePtr vaportra_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int pri=vaportra_control_2.READ_WORD(0);
	
		/* Update flipscreen */
		if ((vaportra_control_1.READ_WORD(0)&0x80) != 0)
			flipscreen=0;
		else
			flipscreen=1;
	
		tilemap_set_flip(ALL_TILEMAPS,flipscreen!=0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
	
		/* Update scroll registers */
		tilemap_set_scrollx( pf1_tilemap,0, vaportra_control_1.READ_WORD(2) );
		tilemap_set_scrolly( pf1_tilemap,0, vaportra_control_1.READ_WORD(4) );
		tilemap_set_scrollx( pf2_tilemap,0, vaportra_control_0.READ_WORD(2) );
		tilemap_set_scrolly( pf2_tilemap,0, vaportra_control_0.READ_WORD(4) );
		tilemap_set_scrollx( pf3_tilemap,0, vaportra_control_1.READ_WORD(6) );
		tilemap_set_scrolly( pf3_tilemap,0, vaportra_control_1.READ_WORD(8) );
		tilemap_set_scrollx( pf4_tilemap,0, vaportra_control_0.READ_WORD(6) );
		tilemap_set_scrolly( pf4_tilemap,0, vaportra_control_0.READ_WORD(8) );
	
		pri&=0x3;
		if (pri!=last_pri)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
		last_pri=pri;
	
		gfx_bank=1;
		gfx_base=vaportra_pf2_data;
		tilemap_update(pf2_tilemap);
	
		gfx_bank=2;
		gfx_base=vaportra_pf3_data;
		tilemap_update(pf3_tilemap);
	
		gfx_bank=3;
		gfx_base=vaportra_pf4_data;
		tilemap_update(pf4_tilemap);
	
		tilemap_update(pf1_tilemap);
		vaportra_update_palette();
	
		/* Draw playfields */
		tilemap_render(ALL_TILEMAPS);
	
		if (pri==0) {
			tilemap_draw(bitmap,pf4_tilemap,TILEMAP_IGNORE_TRANSPARENCY);
			tilemap_draw(bitmap,pf2_tilemap,0);
			vaportra_drawsprites(bitmap,0);
			tilemap_draw(bitmap,pf3_tilemap,0);
		}
		else if (pri==1) {
			tilemap_draw(bitmap,pf2_tilemap,TILEMAP_IGNORE_TRANSPARENCY);
			tilemap_draw(bitmap,pf4_tilemap,0);
			vaportra_drawsprites(bitmap,0);
			tilemap_draw(bitmap,pf3_tilemap,0);
		}
		else if (pri==2) {
			tilemap_draw(bitmap,pf4_tilemap,TILEMAP_IGNORE_TRANSPARENCY);
			tilemap_draw(bitmap,pf3_tilemap,0);
			vaportra_drawsprites(bitmap,0);
			tilemap_draw(bitmap,pf2_tilemap,0);
		}
		else {
			tilemap_draw(bitmap,pf2_tilemap,TILEMAP_IGNORE_TRANSPARENCY);
			tilemap_draw(bitmap,pf3_tilemap,0);
			vaportra_drawsprites(bitmap,0);
			tilemap_draw(bitmap,pf4_tilemap,0);
		}
	
		vaportra_drawsprites(bitmap,1);
		tilemap_draw(bitmap,pf1_tilemap,0);
	} };
}
