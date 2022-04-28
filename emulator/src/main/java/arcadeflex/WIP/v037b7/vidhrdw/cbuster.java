/***************************************************************************

   Crude Buster Video emulation - Bryan McPhail, mish@tendril.co.uk

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
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;

public class cbuster
{
	
	public static UBytePtr twocrude_pf1_data=new UBytePtr(),twocrude_pf2_data=new UBytePtr(),twocrude_pf3_data=new UBytePtr(),twocrude_pf4_data=new UBytePtr();
	
	static struct_tilemap pf1_tilemap,pf2_tilemap,pf3_tilemap,pf4_tilemap;
	static UBytePtr gfx_base=new UBytePtr();
	static int gfx_bank,twocrude_pri,flipscreen,last_flip;
	
	static UBytePtr twocrude_control_0=new UBytePtr(16);
	static UBytePtr twocrude_control_1=new UBytePtr(16);
	
	public static UBytePtr twocrude_pf1_rowscroll=new UBytePtr(),twocrude_pf2_rowscroll=new UBytePtr();
	public static UBytePtr twocrude_pf3_rowscroll=new UBytePtr(),twocrude_pf4_rowscroll=new UBytePtr();
	
	static UBytePtr twocrude_spriteram=new UBytePtr();
	
	
	
	/* Function for all 16x16 1024 by 512 layers */
	static GetMemoryOffsetPtr back_scan = new GetMemoryOffsetPtr() {
            @Override
            public int handler(int col, int row, int num_cols, int num_rows) {
                /* logical (col,row) . memory offset */
		return (col & 0x1f) + ((row & 0x1f) << 5) + ((col & 0x20) << 5);
            }
        };
        
	public static GetTileInfoPtr get_back_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile,color;
	
		tile=gfx_base.READ_WORD(2*tile_index);
		color=tile >> 12;
		tile=tile&0xfff;
	
		SET_TILE_INFO(gfx_bank,tile,color);
	} };
	
	/* 8x8 top layer */
	public static GetTileInfoPtr get_fore_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile=twocrude_pf1_data.READ_WORD(2*tile_index);
		int color=tile >> 12;
	
		tile=tile&0xfff;
	
		SET_TILE_INFO(0,tile,color);
	} };
	
	/******************************************************************************/
	
	public static VhStopPtr twocrude_vh_stop = new VhStopPtr() { public void handler() 
	{
		twocrude_spriteram=null;
	} };
	
	public static VhStartPtr twocrude_vh_start = new VhStartPtr() { public int handler() 
	{
		pf2_tilemap = tilemap_create(get_back_tile_info,back_scan,        TILEMAP_OPAQUE,16,16,64,32);
		pf3_tilemap = tilemap_create(get_back_tile_info,back_scan,        TILEMAP_TRANSPARENT,16,16,64,32);
		pf4_tilemap = tilemap_create(get_back_tile_info,back_scan,        TILEMAP_TRANSPARENT,16,16,64,32);
		pf1_tilemap = tilemap_create(get_fore_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,8,8,64,32);
	
		if (pf1_tilemap==null || pf2_tilemap==null || pf3_tilemap==null || pf4_tilemap==null)
			return 1;
	
		pf1_tilemap.transparent_pen = 0;
		pf3_tilemap.transparent_pen = 0;
		pf4_tilemap.transparent_pen = 0;
	
		twocrude_spriteram = new UBytePtr(0x800);
	
		return 0;
	} };
	
	/******************************************************************************/
	
	public static WriteHandlerPtr twocrude_update_sprites_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		memcpy(twocrude_spriteram,spriteram,0x800);
	} };
	
	static void update_24bitcol(int offset)
	{
		int r,g,b;
	
		r = (paletteram.READ_WORD(offset) >> 0) & 0xff;
		g = (paletteram.READ_WORD(offset) >> 8) & 0xff;
		b = (paletteram_2.READ_WORD(offset) >> 0) & 0xff;
	
		palette_change_color(offset / 2,r,g,b);
	}
	
	public static WriteHandlerPtr twocrude_palette_24bit_rg_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(paletteram,offset,data);
		update_24bitcol(offset);
	} };
	
	public static WriteHandlerPtr twocrude_palette_24bit_b_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(paletteram_2,offset,data);
		update_24bitcol(offset);
	} };
	
	public static ReadHandlerPtr twocrude_palette_24bit_rg_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return paletteram.READ_WORD(offset);
	} };
	
	public static ReadHandlerPtr twocrude_palette_24bit_b_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return paletteram_2.READ_WORD(offset);
	} };
	
	/******************************************************************************/
	
	public static void twocrude_pri_w(int pri)
	{
		twocrude_pri=pri;
	}
	
	public static WriteHandlerPtr twocrude_pf1_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = twocrude_pf1_data.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			twocrude_pf1_data.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(pf1_tilemap,offset/2);
		}
	} };
	
	public static WriteHandlerPtr twocrude_pf2_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = twocrude_pf2_data.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			twocrude_pf2_data.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(pf2_tilemap,offset/2);
		}
	} };
	
	public static WriteHandlerPtr twocrude_pf3_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = twocrude_pf3_data.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			twocrude_pf3_data.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(pf3_tilemap,offset/2);
		}
	} };
	
	public static WriteHandlerPtr twocrude_pf4_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = twocrude_pf4_data.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			twocrude_pf4_data.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(pf4_tilemap,offset/2);
		}
	} };
	
	public static WriteHandlerPtr twocrude_control_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(twocrude_control_0,offset,data);
	} };
	
	public static WriteHandlerPtr twocrude_control_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(twocrude_control_1,offset,data);
	} };
	
	public static WriteHandlerPtr twocrude_pf1_rowscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(twocrude_pf1_rowscroll,offset,data);
	} };
	
	public static WriteHandlerPtr twocrude_pf2_rowscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(twocrude_pf2_rowscroll,offset,data);
	} };
	
	public static WriteHandlerPtr twocrude_pf3_rowscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(twocrude_pf3_rowscroll,offset,data);
	} };
	
	public static WriteHandlerPtr twocrude_pf4_rowscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(twocrude_pf4_rowscroll,offset,data);
	} };
	
	/******************************************************************************/
	
	static void twocrude_update_palette()
	{
		int offs,color,i,pal_base;
		int[] colmask = new int[32];
	
		palette_init_used_colors();
	
		/* Sprites */
		pal_base = Machine.drv.gfxdecodeinfo[4].color_codes_start;
		for (color = 0;color < 32;color++) colmask[color] = 0;
		for (offs = 0;offs < 0x800;offs += 8)
		{
			int x,y,sprite,multi;
	
			sprite = twocrude_spriteram.READ_WORD (offs+2) & 0x7fff;
			if (sprite==0) continue;
	
			y = twocrude_spriteram.READ_WORD(offs);
			x = twocrude_spriteram.READ_WORD(offs+4);
			color = (x >> 9) &0x1f;
	
			multi = (1 << ((y & 0x0600) >> 9)) - 1;	/* 1x, 2x, 4x, 8x height */
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
	
		for (color = 16;color < 32;color++)
		{
			for (i = 1;i < 16;i++)
			{
				if ((colmask[color] & (1 << i)) != 0)
					palette_used_colors.write(256 + 1024 + 16 * (color-16) + i, PALETTE_COLOR_USED);
			}
		}
	
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	}
	
	static void twocrude_drawsprites(osd_bitmap bitmap, int pri)
	{
		int offs;
	
		for (offs = 0;offs < 0x800;offs += 8)
		{
			int x,y,sprite,colour,multi,fx,fy,inc,flash,mult;
	
			sprite = twocrude_spriteram.READ_WORD (offs+2) & 0x7fff;
			if (sprite==0) continue;
	
			y = twocrude_spriteram.READ_WORD(offs);
			x = twocrude_spriteram.READ_WORD(offs+4);
	
			if ((y&0x8000)!=0 && pri==1) continue;
			if ((y&0x8000)==0 && pri==0) continue;
	
			colour = (x >> 9) &0xf;
			if ((x & 0x2000) != 0) colour+=64;
	
			flash=y&0x1000;
			if (flash!=0 && (cpu_getcurrentframe() & 1)!=0) continue;
	
			fx = y & 0x2000;
			fy = y & 0x4000;
			multi = (1 << ((y & 0x0600) >> 9)) - 1;	/* 1x, 2x, 4x, 8x height */
	
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
	
			if (flipscreen != 0) {
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
	
	/******************************************************************************/
	
	public static VhUpdatePtr twocrude_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int offs;
		int pf23_control,pf14_control;
	
		/* Update flipscreen */
		if ((twocrude_control_1.READ_WORD(0)&0x80) != 0)
			flipscreen=0;
		else
			flipscreen=1;
	
		if (last_flip!=flipscreen)
			tilemap_set_flip(ALL_TILEMAPS,flipscreen!=0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
		last_flip=flipscreen;
	
		pf23_control=twocrude_control_0.READ_WORD (0xc);
		pf14_control=twocrude_control_1.READ_WORD (0xc);
	
		/* Background - Rowscroll enable */
		if ((pf23_control & 0x4000) != 0) {
			int scrollx=twocrude_control_0.READ_WORD(6),rows;
			tilemap_set_scroll_cols(pf2_tilemap,1);
			tilemap_set_scrolly( pf2_tilemap,0, twocrude_control_0.READ_WORD(8) );
	
			/* Several different rowscroll styles! */
			switch ((twocrude_control_0.READ_WORD (0xa)>>11)&7) {
				case 0: rows=512; break;/* Every line of 512 height bitmap */
				case 1: rows=256; break;
				case 2: rows=128; break;
				case 3: rows=64; break;
				case 4: rows=32; break;
				case 5: rows=16; break;
				case 6: rows=8; break;
				case 7: rows=4; break;
				default: rows=1; break;
			}
	
			tilemap_set_scroll_rows(pf2_tilemap,rows);
			for (offs = 0;offs < rows;offs++)
				tilemap_set_scrollx( pf2_tilemap,offs, scrollx + twocrude_pf2_rowscroll.READ_WORD(2*offs) );
		}
		else {
			tilemap_set_scroll_rows(pf2_tilemap,1);
			tilemap_set_scroll_cols(pf2_tilemap,1);
			tilemap_set_scrollx( pf2_tilemap,0, twocrude_control_0.READ_WORD(6) );
			tilemap_set_scrolly( pf2_tilemap,0, twocrude_control_0.READ_WORD(8) );
		}
	
		/* Playfield 3 */
		if ((pf23_control & 0x40) != 0) { /* Rowscroll */
			int scrollx=twocrude_control_0.READ_WORD(2),rows;
			tilemap_set_scroll_cols(pf3_tilemap,1);
			tilemap_set_scrolly( pf3_tilemap,0, twocrude_control_0.READ_WORD(4) );
	
			/* Several different rowscroll styles! */
			switch ((twocrude_control_0.READ_WORD (0xa)>>3)&7) {
				case 0: rows=512; break;/* Every line of 512 height bitmap */
				case 1: rows=256; break;
				case 2: rows=128; break;
				case 3: rows=64; break;
				case 4: rows=32; break;
				case 5: rows=16; break;
				case 6: rows=8; break;
				case 7: rows=4; break;
				default: rows=1; break;
			}
	
			tilemap_set_scroll_rows(pf3_tilemap,rows);
			for (offs = 0;offs < rows;offs++)
				tilemap_set_scrollx( pf3_tilemap,offs, scrollx + twocrude_pf3_rowscroll.READ_WORD(2*offs) );
		}
		else if ((pf23_control & 0x20) != 0) { /* Colscroll */
			int scrolly=twocrude_control_0.READ_WORD(4),cols;
			tilemap_set_scroll_rows(pf3_tilemap,1);
			tilemap_set_scrollx( pf3_tilemap,0, twocrude_control_0.READ_WORD(2) );
	
			/* Several different colscroll styles! */
			switch ((twocrude_control_0.READ_WORD (0xa)>>0)&7) {
				case 0: cols=64; break;
				case 1: cols=32; break;
				case 2: cols=16; break;
				case 3: cols=8; break;
				case 4: cols=4; break;
				case 5: cols=2; break;
				case 6: cols=1; break;
				case 7: cols=1; break;
				default: cols=1; break;
			}
	
			tilemap_set_scroll_cols(pf3_tilemap,cols);
			for (offs = 0;offs < cols;offs++)
				tilemap_set_scrolly( pf3_tilemap,offs,scrolly + twocrude_pf3_rowscroll.READ_WORD(2*offs+0x400) );
		}
		else {
			tilemap_set_scroll_rows(pf3_tilemap,1);
			tilemap_set_scroll_cols(pf3_tilemap,1);
			tilemap_set_scrollx( pf3_tilemap,0, twocrude_control_0.READ_WORD(2) );
			tilemap_set_scrolly( pf3_tilemap,0, twocrude_control_0.READ_WORD(4) );
		}
	
		/* Playfield 4 - Rowscroll enable */
		if ((pf14_control & 0x4000) != 0) {
			int scrollx=twocrude_control_1.READ_WORD(6),rows;
			tilemap_set_scroll_cols(pf4_tilemap,1);
			tilemap_set_scrolly( pf4_tilemap,0, twocrude_control_1.READ_WORD(8) );
	
			/* Several different rowscroll styles! */
			switch ((twocrude_control_1.READ_WORD (0xa)>>11)&7) {
				case 0: rows=512; break;/* Every line of 512 height bitmap */
				case 1: rows=256; break;
				case 2: rows=128; break;
				case 3: rows=64; break;
				case 4: rows=32; break;
				case 5: rows=16; break;
				case 6: rows=8; break;
				case 7: rows=4; break;
				default: rows=1; break;
			}
	
			tilemap_set_scroll_rows(pf4_tilemap,rows);
			for (offs = 0;offs < rows;offs++)
				tilemap_set_scrollx( pf4_tilemap,offs, scrollx + twocrude_pf4_rowscroll.READ_WORD(2*offs) );
		}
		else {
			tilemap_set_scroll_rows(pf4_tilemap,1);
			tilemap_set_scroll_cols(pf4_tilemap,1);
			tilemap_set_scrollx( pf4_tilemap,0, twocrude_control_1.READ_WORD(6) );
			tilemap_set_scrolly( pf4_tilemap,0, twocrude_control_1.READ_WORD(8) );
		}
	
		/* Playfield 1 */
		if ((pf14_control & 0x40) != 0) { /* Rowscroll */
			int scrollx=twocrude_control_1.READ_WORD(2),rows;
			tilemap_set_scroll_cols(pf1_tilemap,1);
			tilemap_set_scrolly( pf1_tilemap,0, twocrude_control_1.READ_WORD(4) );
	
			/* Several different rowscroll styles! */
			switch ((twocrude_control_1.READ_WORD (0xa)>>3)&7) {
				case 0: rows=256; break;/* Every line of 256 height bitmap */
				case 1: rows=128; break;
				case 2: rows=64; break;
				case 3: rows=32; break;
				case 4: rows=16; break;
				case 5: rows=8; break;
				case 6: rows=4; break;
				case 7: rows=2; break;
				default: rows=1; break;
			}
	
			tilemap_set_scroll_rows(pf1_tilemap,rows);
			for (offs = 0;offs < rows;offs++)
				tilemap_set_scrollx( pf1_tilemap,offs, scrollx + twocrude_pf1_rowscroll.READ_WORD(2*offs) );
		}
		else {
			tilemap_set_scroll_rows(pf1_tilemap,1);
			tilemap_set_scroll_cols(pf1_tilemap,1);
			tilemap_set_scrollx( pf1_tilemap,0, twocrude_control_1.READ_WORD(2) );
			tilemap_set_scrolly( pf1_tilemap,0, twocrude_control_1.READ_WORD(4) );
		}
	
		/* Update playfields */
		gfx_bank=1;
		gfx_base=twocrude_pf2_data;
		tilemap_update(pf2_tilemap);
	
		gfx_bank=2;
		gfx_base=twocrude_pf3_data;
		tilemap_update(pf3_tilemap);
	
		gfx_bank=3;
		gfx_base=twocrude_pf4_data;
		tilemap_update(pf4_tilemap);
		tilemap_update(pf1_tilemap);
		twocrude_update_palette();
	
		/* Draw playfields & sprites */
		tilemap_render(ALL_TILEMAPS);
		tilemap_draw(bitmap,pf2_tilemap,0);
		twocrude_drawsprites(bitmap,0);
	
		if (twocrude_pri != 0) {
			tilemap_draw(bitmap,pf4_tilemap,0);
			tilemap_draw(bitmap,pf3_tilemap,0);
		}
		else {
			tilemap_draw(bitmap,pf3_tilemap,0);
			tilemap_draw(bitmap,pf4_tilemap,0);
		}
	
		twocrude_drawsprites(bitmap,1);
		tilemap_draw(bitmap,pf1_tilemap,0);
	} };
}
