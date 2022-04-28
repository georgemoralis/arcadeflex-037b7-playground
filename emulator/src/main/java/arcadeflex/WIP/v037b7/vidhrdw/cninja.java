/***************************************************************************

   Caveman Ninja Video emulation - Bryan McPhail, mish@tendril.co.uk

****************************************************************************

Data East custom chip 55:  Generates two playfields, playfield 1 is underneath
playfield 2.  Caveman Ninja uses two of these chips.

	16 bytes of control registers per chip.

	Word 0:
		Mask 0x0080: Flip screen
		Mask 0x007f: ?
	Word 2:
		Mask 0xffff: Playfield 2 X scroll (top playfield)
	Word 4:
		Mask 0xffff: Playfield 2 Y scroll (top playfield)
	Word 6:
		Mask 0xffff: Playfield 1 X scroll (bottom playfield)
	Word 8:
		Mask 0xffff: Playfield 1 Y scroll (bottom playfield)
	Word 0xa:
		Mask 0xc000: Playfield 1 shape??
		Mask 0x3800: Playfield 1 rowscroll style
		Mask 0x0700: Playfield 1 colscroll style

		Mask 0x00c0: Playfield 2 shape??
		Mask 0x0038: Playfield 2 rowscroll style
		Mask 0x0007: Playfield 2 colscroll style
	Word 0xc:
		Mask 0x8000: Playfield 1 is 8*8 tiles else 16*16
		Mask 0x4000: Playfield 1 rowscroll enabled
		Mask 0x2000: Playfield 1 colscroll enabled
		Mask 0x1f00: ?

		Mask 0x0080: Playfield 2 is 8*8 tiles else 16*16
		Mask 0x0040: Playfield 2 rowscroll enabled
		Mask 0x0020: Playfield 2 colscroll enabled
		Mask 0x001f: ?
	Word 0xe:
		??

Colscroll style:
	0	64 columns across bitmap
	1	32 columns across bitmap

Rowscroll style:
	0	512 rows across bitmap


Locations 0 & 0xe are mostly unknown:

							 0		14
Caveman Ninja (bottom):		0053	1100 (see below)
Caveman Ninja (top):		0010	0081
Two Crude (bottom):			0053	0000
Two Crude (top):			0010	0041
Dark Seal (bottom):			0010	0000
Dark Seal (top):			0053	4101
Tumblepop:					0010	0000
Super Burger Time:			0010	0000

Location 14 in Cninja (bottom):
 1100 = pf2 uses graphics rom 1, pf3 uses graphics rom 2
 0000 = pf2 uses graphics rom 2, pf3 uses graphics rom 2
 1111 = pf2 uses graphics rom 1, pf3 uses graphics rom 1

**************************************************************************

Sprites - Data East custom chip 52

	8 bytes per sprite, unknowns bits seem unused.

	Word 0:
		Mask 0x8000 - ?
		Mask 0x4000 - Y flip
		Mask 0x2000 - X flip
		Mask 0x1000 - Sprite flash
		Mask 0x0800 - ?
		Mask 0x0600 - Sprite height (1x, 2x, 4x, 8x)
		Mask 0x01ff - Y coordinate

	Word 2:
		Mask 0xffff - Sprite number

	Word 4:
		Mask 0x8000 - ?
		Mask 0x4000 - Sprite is drawn beneath top 8 pens of playfield 4
		Mask 0x3e00 - Colour (32 palettes, most games only use 16)
		Mask 0x01ff - X coordinate

	Word 6:
		Always unused.

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
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;

public class cninja
{
	
	public static UBytePtr cninja_pf1_data=new UBytePtr(),cninja_pf2_data=new UBytePtr();
	public static UBytePtr cninja_pf3_data=new UBytePtr(),cninja_pf4_data=new UBytePtr();
	public static UBytePtr cninja_pf1_rowscroll=new UBytePtr(),cninja_pf2_rowscroll=new UBytePtr();
	public static UBytePtr cninja_pf3_rowscroll=new UBytePtr(),cninja_pf4_rowscroll=new UBytePtr();
	
	static struct_tilemap pf1_tilemap,pf2_tilemap,pf3_tilemap,pf4_tilemap;
	static UBytePtr gfx_base=new UBytePtr();
	static int gfx_bank;
	
	static UBytePtr cninja_control_0=new UBytePtr(16);
	static UBytePtr cninja_control_1=new UBytePtr(16);
	
	static int cninja_pf2_bank,cninja_pf3_bank;
	static int bootleg,spritemask,color_base,flipscreen;
	
	
	/* Function for all 16x16 1024x512 layers */
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
	
		SET_TILE_INFO(gfx_bank,tile,color+color_base);
	} };
	
	/* 8x8 top layer */
	public static GetTileInfoPtr get_fore_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int tile=cninja_pf1_data.READ_WORD(2*tile_index);
		int color=tile >> 12;
	
		tile=tile&0xfff;
	
		SET_TILE_INFO(0,tile,color);
	} };
	
	/******************************************************************************/
	
	public static VhStartPtr common_vh_start = new VhStartPtr() { public int handler() 
	{
		cninja_pf2_bank=1;
		cninja_pf3_bank=2;
	
		pf2_tilemap = tilemap_create(get_back_tile_info,back_scan,TILEMAP_OPAQUE,16,16,64,32);
		pf3_tilemap = tilemap_create(get_back_tile_info,back_scan,TILEMAP_TRANSPARENT,16,16,64,32);
		pf4_tilemap = tilemap_create(get_back_tile_info,back_scan,TILEMAP_TRANSPARENT | TILEMAP_SPLIT,16,16,64,32);
		pf1_tilemap = tilemap_create(get_fore_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,8,8,64,32);
	
		if (pf1_tilemap==null || pf2_tilemap==null || pf3_tilemap==null || pf4_tilemap==null)
			return 1;
	
		pf1_tilemap.transparent_pen = 0;
		pf3_tilemap.transparent_pen = 0;
		pf4_tilemap.transparent_pen = 0;
		pf4_tilemap.u32_transmask[0] = 0x00ff;
		pf4_tilemap.u32_transmask[1] = 0xff00;
	
		return 0;
	} };
	
	public static VhStartPtr cninja_vh_start = new VhStartPtr() { public int handler() 
	{
		spritemask=0x3fff;
		bootleg=0;
		return common_vh_start.handler();
	} };
	
	public static VhStartPtr stoneage_vh_start = new VhStartPtr() { public int handler() 
	{
		spritemask=0x3fff;
		bootleg=1; /* The bootleg has broken scroll registers */
		return common_vh_start.handler();
	} };
	
	public static VhStartPtr edrandy_vh_start = new VhStartPtr() { public int handler() 
	{
		spritemask=0xffff;
		bootleg=0;
		return common_vh_start.handler();
	} };
	
	/******************************************************************************/
	
	public static WriteHandlerPtr cninja_pf1_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(cninja_pf1_data,offset,data);
		tilemap_mark_tile_dirty(pf1_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr cninja_pf2_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(cninja_pf2_data,offset,data);
		tilemap_mark_tile_dirty(pf2_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr cninja_pf3_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(cninja_pf3_data,offset,data);
		tilemap_mark_tile_dirty(pf3_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr cninja_pf4_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(cninja_pf4_data,offset,data);
		tilemap_mark_tile_dirty(pf4_tilemap,offset/2);
	} };
	
	public static WriteHandlerPtr cninja_control_0_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (bootleg!=0 && offset==6) {
			COMBINE_WORD_MEM(cninja_control_0,offset,data+0xa);
			return;
		}
		COMBINE_WORD_MEM(cninja_control_0,offset,data);
	} };
	
	public static WriteHandlerPtr cninja_control_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if (bootleg != 0) {
			switch (offset) {
				case 2:
					COMBINE_WORD_MEM(cninja_control_1,offset,data-2);
					return;
				case 6:
					COMBINE_WORD_MEM(cninja_control_1,offset,data+0xa);
					return;
			}
		}
		COMBINE_WORD_MEM(cninja_control_1,offset,data);
	} };
	
	public static ReadHandlerPtr cninja_pf1_data_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return cninja_pf1_data.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr cninja_pf1_rowscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(cninja_pf1_rowscroll,offset,data);
	} };
	
	public static WriteHandlerPtr cninja_pf2_rowscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(cninja_pf2_rowscroll,offset,data);
	} };
	
	public static WriteHandlerPtr cninja_pf3_rowscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(cninja_pf3_rowscroll,offset,data);
	} };
	
	public static ReadHandlerPtr cninja_pf3_rowscroll_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return cninja_pf3_rowscroll.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr cninja_pf4_rowscroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(cninja_pf4_rowscroll,offset,data);
	} };
	
	/******************************************************************************/
	
	public static WriteHandlerPtr cninja_palette_24bit_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int r,g,b;
	
		COMBINE_WORD_MEM(paletteram,offset,data);
		if ((offset%4)!=0) offset-=2;
	
		b = (paletteram.READ_WORD(offset) >> 0) & 0xff;
		g = (paletteram.READ_WORD(offset+2) >> 8) & 0xff;
		r = (paletteram.READ_WORD(offset+2) >> 0) & 0xff;
	
		palette_change_color(offset / 4,r,g,b);
	} };
	
	/******************************************************************************/
	
	static void mark_sprites_colors()
	{
		int offs,color,i,pal_base;
		int[] colmask = new int[16];
                int[] pen_usage; /* Save some struct derefs */
	
		/* Sprites */
		pal_base = Machine.drv.gfxdecodeinfo[4].color_codes_start;
		pen_usage=Machine.gfx[4].pen_usage;
		for (color = 0;color < 16;color++) colmask[color] = 0;
	
		for (offs = 0;offs < 0x800;offs += 8)
		{
			int x,y,sprite,multi;
	
			sprite = buffered_spriteram.READ_WORD (offs+2) & spritemask;
			if (sprite==0) continue;
	
			x = buffered_spriteram.READ_WORD(offs+4);
			y = buffered_spriteram.READ_WORD(offs);
	
			color = (x >> 9) &0xf;
			multi = (1 << ((y & 0x0600) >> 9)) - 1;
			sprite &= ~multi;
	
			/* Save palette by missing offscreen sprites */
			x = x & 0x01ff;
			y = y & 0x01ff;
			if (x >= 256) x -= 512;
			x = 240 - x;
			if (x>256) continue;
	
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
	}
	
	static void cninja_drawsprites(osd_bitmap bitmap, int pri)
	{
		int offs;
	
		for (offs = 0;offs < 0x800;offs += 8)
		{
			int x,y,sprite,colour,multi,fx,fy,inc,flash,mult;
			sprite = buffered_spriteram.READ_WORD (offs+2) & spritemask;
			if (sprite==0) continue;
	
			x = buffered_spriteram.READ_WORD(offs+4);
	
			/* Sprite/playfield priority */
			if ((x&0x4000)!=0 && pri==1) continue;
			if ((x&0x4000)==0 && pri==0) continue;
	
			y = buffered_spriteram.READ_WORD(offs);
			flash=y&0x1000;
			if (flash!=0 && (cpu_getcurrentframe() & 1)!=0) continue;
			colour = (x >> 9) &0xf;
	
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
	
	public static VhUpdatePtr cninja_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int offs;
		int pf23_control,pf1_control;
	
		/* Update flipscreen */
		flipscreen = cninja_control_1.READ_WORD(0)&0x80;
		tilemap_set_flip(ALL_TILEMAPS,flipscreen!=0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
	
		/* Handle gfx rom switching */
		pf23_control=cninja_control_0.READ_WORD (0xe);
		if ((pf23_control&0xff)==0x00)
			cninja_pf3_bank=2;
		else
			cninja_pf3_bank=1;
	
		if ((pf23_control&0xff00)==0x00)
			cninja_pf2_bank=2;
		else
			cninja_pf2_bank=1;
	
		/* Setup scrolling */
		pf23_control=cninja_control_0.READ_WORD (0xc);
		pf1_control=cninja_control_1.READ_WORD (0xc);
	
		/* Background - Rowscroll enable */
		if ((pf23_control & 0x4000) != 0) {
			int scrollx=cninja_control_0.READ_WORD(6),rows;
			tilemap_set_scroll_cols(pf2_tilemap,1);
			tilemap_set_scrolly( pf2_tilemap,0, cninja_control_0.READ_WORD(8) );
	
			/* Several different rowscroll styles! */
			switch ((cninja_control_0.READ_WORD (0xa)>>11)&7) {
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
				tilemap_set_scrollx( pf2_tilemap,offs, scrollx + cninja_pf2_rowscroll.READ_WORD(2*offs) );
		}
		else {
			tilemap_set_scroll_rows(pf2_tilemap,1);
			tilemap_set_scroll_cols(pf2_tilemap,1);
			tilemap_set_scrollx( pf2_tilemap,0, cninja_control_0.READ_WORD(6) );
			tilemap_set_scrolly( pf2_tilemap,0, cninja_control_0.READ_WORD(8) );
		}
	
		/* Playfield 3 */
		if ((pf23_control & 0x40) != 0) { /* Rowscroll */
			int scrollx=cninja_control_0.READ_WORD(2),rows;
			tilemap_set_scroll_cols(pf3_tilemap,1);
			tilemap_set_scrolly( pf3_tilemap,0, cninja_control_0.READ_WORD(4) );
	
			/* Several different rowscroll styles! */
			switch ((cninja_control_0.READ_WORD (0xa)>>3)&7) {
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
				tilemap_set_scrollx( pf3_tilemap,offs, scrollx + cninja_pf3_rowscroll.READ_WORD(2*offs) );
		}
		else if ((pf23_control & 0x20) != 0) { /* Colscroll */
			int scrolly=cninja_control_0.READ_WORD(4);
			tilemap_set_scroll_rows(pf3_tilemap,1);
			tilemap_set_scroll_cols(pf3_tilemap,64);
			tilemap_set_scrollx( pf3_tilemap,0, cninja_control_0.READ_WORD(2) );
	
			/* Used in lava level & Level 1 */
			for (offs=0 ; offs < 32;offs++)
				tilemap_set_scrolly( pf3_tilemap,offs+32, scrolly + cninja_pf3_rowscroll.READ_WORD((2*offs)+0x400) );
		}
		else {
			tilemap_set_scroll_rows(pf3_tilemap,1);
			tilemap_set_scroll_cols(pf3_tilemap,1);
			tilemap_set_scrollx( pf3_tilemap,0, cninja_control_0.READ_WORD(2) );
			tilemap_set_scrolly( pf3_tilemap,0, cninja_control_0.READ_WORD(4) );
		}
	
		/* Top foreground */
		if ((pf1_control & 0x4000) != 0) {
			int scrollx=cninja_control_1.READ_WORD(6),rows;
			tilemap_set_scroll_cols(pf4_tilemap,1);
			tilemap_set_scrolly( pf4_tilemap,0, cninja_control_1.READ_WORD(8) );
	
			/* Several different rowscroll styles! */
			switch ((cninja_control_1.READ_WORD (0xa)>>11)&7) {
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
				tilemap_set_scrollx( pf4_tilemap,offs, scrollx + cninja_pf4_rowscroll.READ_WORD(2*offs) );
		}
		else if ((pf1_control & 0x2000) != 0) { /* Colscroll */
			int scrolly=cninja_control_1.READ_WORD(8);
			tilemap_set_scroll_rows(pf4_tilemap,1);
			tilemap_set_scroll_cols(pf4_tilemap,64);
			tilemap_set_scrollx( pf4_tilemap,0, cninja_control_0.READ_WORD(2) );
	
			/* Used in first lava level */
			for (offs=0 ; offs < 64;offs++)
				tilemap_set_scrolly( pf4_tilemap,offs, scrolly + cninja_pf4_rowscroll.READ_WORD((2*offs)+0x400) );
		}
		else {
			tilemap_set_scroll_rows(pf4_tilemap,1);
			tilemap_set_scroll_cols(pf4_tilemap,1);
			tilemap_set_scrollx( pf4_tilemap,0, cninja_control_1.READ_WORD(6) );
			tilemap_set_scrolly( pf4_tilemap,0, cninja_control_1.READ_WORD(8) );
		}
	
		/* Playfield 1 - 8 * 8 Text */
		if ((pf1_control & 0x40) != 0) { /* Rowscroll */
			int scrollx=cninja_control_1.READ_WORD(2),rows;
			tilemap_set_scroll_cols(pf1_tilemap,1);
			tilemap_set_scrolly( pf1_tilemap,0, cninja_control_1.READ_WORD(4) );
	
			/* Several different rowscroll styles! */
			switch ((cninja_control_1.READ_WORD (0xa)>>3)&7) {
				case 0: rows=256; break;
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
				tilemap_set_scrollx( pf1_tilemap,offs, scrollx + cninja_pf1_rowscroll.READ_WORD(2*offs) );
		}
		else {
			tilemap_set_scroll_rows(pf1_tilemap,1);
			tilemap_set_scroll_cols(pf1_tilemap,1);
			tilemap_set_scrollx( pf1_tilemap,0, cninja_control_1.READ_WORD(2) );
			tilemap_set_scrolly( pf1_tilemap,0, cninja_control_1.READ_WORD(4) );
		}
	
		/* Update playfields */
		gfx_bank=cninja_pf2_bank;
		gfx_base=cninja_pf2_data;
		color_base=48;
		tilemap_update(pf2_tilemap);
	
		gfx_bank=cninja_pf3_bank;
		gfx_base=cninja_pf3_data;
		color_base=0;
		tilemap_update(pf3_tilemap);
	
		gfx_bank=3;
		gfx_base=cninja_pf4_data;
		color_base=0;
		tilemap_update(pf4_tilemap);
		tilemap_update(pf1_tilemap);
	
		palette_init_used_colors();
		mark_sprites_colors();
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		/* Draw playfields */
		tilemap_render(ALL_TILEMAPS);
		tilemap_draw(bitmap,pf2_tilemap,0);
		tilemap_draw(bitmap,pf3_tilemap,0);
		tilemap_draw(bitmap,pf4_tilemap,TILEMAP_BACK);
		cninja_drawsprites(bitmap,0);
		tilemap_draw(bitmap,pf4_tilemap,TILEMAP_FRONT);
		cninja_drawsprites(bitmap,1);
		tilemap_draw(bitmap,pf1_tilemap,0);
	} };
}
