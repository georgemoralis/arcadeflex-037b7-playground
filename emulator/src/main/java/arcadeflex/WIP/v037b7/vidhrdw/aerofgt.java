/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;        
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.drawgfx.pdrawgfxzoom;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;        
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.drawgfxzoom;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.pdrawgfx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;        
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class aerofgt
{
	
	public static UBytePtr aerofgt_rasterram = new UBytePtr();
	public static UBytePtr aerofgt_bg1videoram = new UBytePtr(),aerofgt_bg2videoram = new UBytePtr();
	public static UBytePtr aerofgt_spriteram1 = new UBytePtr(), aerofgt_spriteram2 = new UBytePtr();
	public static int[] aerofgt_spriteram1_size = new int[1],aerofgt_spriteram2_size = new int[1];
	
	static int[] gfxbank = new int[8];
	static UBytePtr bg1scrollx = new UBytePtr(2),bg1scrolly = new UBytePtr(2),bg2scrollx = new UBytePtr(2),bg2scrolly = new UBytePtr(2);
	
	static int charpalettebank,spritepalettebank;
	
	static struct_tilemap bg1_tilemap, bg2_tilemap;
	static int sprite_gfx;
	
	
	/***************************************************************************
	
	  Callbacks for the TileMap code
	
	***************************************************************************/
	
	public static GetTileInfoPtr get_pspikes_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = aerofgt_bg1videoram.READ_WORD(2*tile_index);
		int bank = (code & 0x1000) >> 12;
		SET_TILE_INFO(0,(code & 0x0fff) + (gfxbank[bank] << 12),((code & 0xe000) >> 13) + 8 * charpalettebank);
	} };
	
	public static GetTileInfoPtr karatblz_bg1_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = aerofgt_bg1videoram.READ_WORD(2*tile_index);
		SET_TILE_INFO(0,(code & 0x1fff) + (gfxbank[0] << 13),(code & 0xe000) >> 13);
	} };
	
	/* also spinlbrk */
	public static GetTileInfoPtr karatblz_bg2_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
            aerofgt_bg2videoram.offset=0;
            try {
		int code = aerofgt_bg2videoram.READ_WORD(2*tile_index);
		SET_TILE_INFO(1,(code & 0x1fff) + (gfxbank[1] << 13),(code & 0xe000) >> 13);
            } catch (Exception e) {
                //System.out.println(aerofgt_bg2videoram.offset);
                e.printStackTrace(System.out);
            }
	} };
	
	public static GetTileInfoPtr spinlbrk_bg1_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
            aerofgt_bg1videoram.offset=0;
            try {
		int code = aerofgt_bg1videoram.READ_WORD(2*(tile_index&0xfff));
		SET_TILE_INFO(0,(code & 0x0fff) + (gfxbank[0] << 12),(code & 0xf000) >> 12);
            } catch (Exception e) {
                //System.out.println(aerofgt_bg2videoram.offset);
                e.printStackTrace(System.out);
            }
	} };
	
	public static GetTileInfoPtr get_bg1_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = aerofgt_bg1videoram.READ_WORD(2*tile_index);
		int bank = (code & 0x1800) >> 11;
		SET_TILE_INFO(0,(code & 0x07ff) + (gfxbank[bank] << 11),(code & 0xe000) >> 13);
	} };
	
	public static GetTileInfoPtr get_bg2_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int code = aerofgt_bg2videoram.READ_WORD(2*tile_index);
		int bank = 4 + ((code & 0x1800) >> 11);
		SET_TILE_INFO(1,(code & 0x07ff) + (gfxbank[bank] << 11),(code & 0xe000) >> 13);
	} };
	
	
	/***************************************************************************
	
	  Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStartPtr pspikes_vh_start = new VhStartPtr() { public int handler() 
	{
		bg1_tilemap = tilemap_create(get_pspikes_tile_info,tilemap_scan_rows,TILEMAP_OPAQUE,8,8,64,32);
		/* no bg2 in this game */
	
		if (bg1_tilemap == null)
			return 1;
	
		sprite_gfx = 1;
	
		return 0;
	} };
	
	public static VhStartPtr karatblz_vh_start = new VhStartPtr() { public int handler() 
	{
		bg1_tilemap = tilemap_create(karatblz_bg1_tile_info,tilemap_scan_rows,TILEMAP_OPAQUE,     8,8,64,64);
		bg2_tilemap = tilemap_create(karatblz_bg2_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,8,8,64,64);
	
		if (bg1_tilemap==null || bg2_tilemap==null)
			return 1;
	
		bg2_tilemap.transparent_pen = 15;
	
		spritepalettebank = 0;
	
		sprite_gfx = 2;
	
		return 0;
	} };
	
	public static VhStartPtr spinlbrk_vh_start = new VhStartPtr() { public int handler() 
	{
		int i;
	
		bg1_tilemap = tilemap_create(spinlbrk_bg1_tile_info,tilemap_scan_rows,TILEMAP_OPAQUE,     8,8,64,64);
		bg2_tilemap = tilemap_create(karatblz_bg2_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,8,8,64,64);
	
		if (bg1_tilemap==null || bg2_tilemap==null)
			return 1;
	
		bg2_tilemap.transparent_pen = 15;
	
		spritepalettebank = 0;
	
		sprite_gfx = 2;
	
	
		/* sprite maps are hardcoded in this game */
	
		/* enemy sprites use ROM instead of RAM */
		aerofgt_spriteram2 = new UBytePtr(memory_region(REGION_GFX5));
		aerofgt_spriteram2_size[0] = 0x20000;
	
		/* front sprites are direct maps */
		aerofgt_spriteram1 = new UBytePtr(aerofgt_spriteram2, aerofgt_spriteram2_size[0]);
		aerofgt_spriteram1_size[0] = 0x4000;
		for (i = 0;i < aerofgt_spriteram1_size[0]/2;i++)
			aerofgt_spriteram1.WRITE_WORD(2*i, i);
	
		return 0;
	} };
	
	public static VhStartPtr turbofrc_vh_start = new VhStartPtr() { public int handler() 
	{
		bg1_tilemap = tilemap_create(get_bg1_tile_info,tilemap_scan_rows,TILEMAP_OPAQUE,     8,8,64,64);
		bg2_tilemap = tilemap_create(get_bg2_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,8,8,64,64);
	
		if (bg1_tilemap==null || bg2_tilemap==null)
			return 1;
	
		bg2_tilemap.transparent_pen = 15;
	
		spritepalettebank = 0;
	
		sprite_gfx = 2;
	
		return 0;
	} };
	
	
	
	/***************************************************************************
	
	  Memory handlers
	
	***************************************************************************/
	
	public static ReadHandlerPtr aerofgt_rasterram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return aerofgt_rasterram.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr aerofgt_rasterram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(aerofgt_rasterram,offset,data);
	} };
	
	public static ReadHandlerPtr aerofgt_spriteram_2_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return spriteram_2.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr aerofgt_spriteram_2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(spriteram_2,offset,data);
	} };
	
	public static ReadHandlerPtr aerofgt_bg1videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return aerofgt_bg1videoram.READ_WORD(offset);
	} };
	
	public static ReadHandlerPtr aerofgt_bg2videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return aerofgt_bg2videoram.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr aerofgt_bg1videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = aerofgt_bg1videoram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			aerofgt_bg1videoram.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(bg1_tilemap,offset/2);
		}
	} };
	
	public static WriteHandlerPtr aerofgt_bg2videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = aerofgt_bg2videoram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			aerofgt_bg2videoram.WRITE_WORD(offset,newword);
			tilemap_mark_tile_dirty(bg2_tilemap,offset/2);
		}
	} };
	
	
	static void setbank(struct_tilemap tmap,int num,int bank)
	{
		if (gfxbank[num] != bank)
		{
			gfxbank[num] = bank;
			tilemap_mark_all_tiles_dirty(tmap);
		}
	}
	
	public static WriteHandlerPtr pspikes_gfxbank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0x00ff0000) == 0)
		{
			setbank(bg1_tilemap,0,(data & 0xf0) >> 4);
			setbank(bg1_tilemap,1,data & 0x0f);
		}
	} };
	
	public static WriteHandlerPtr karatblz_gfxbank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0xff000000) == 0)
		{
			setbank(bg1_tilemap,0,(data & 0x0100) >> 8);
			setbank(bg2_tilemap,1,(data & 0x0800) >> 11);
		}
	} };
	
	public static WriteHandlerPtr spinlbrk_gfxbank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0x00ff0000) == 0)
		{
			setbank(bg1_tilemap,0,(data & 0x07));
			setbank(bg2_tilemap,1,(data & 0x38) >> 3);
		}
	} };
        
        static UBytePtr old = new UBytePtr(4);
	
	public static WriteHandlerPtr turbofrc_gfxbank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{		
		int newword;
		struct_tilemap tmap = (offset < 2) ? bg1_tilemap : bg2_tilemap;
	
		COMBINE_WORD_MEM(old,offset,data);
		newword = old.READ_WORD(offset);
	
		setbank(tmap,2*offset + 0,(newword >>  0) & 0x0f);
		setbank(tmap,2*offset + 1,(newword >>  4) & 0x0f);
		setbank(tmap,2*offset + 2,(newword >>  8) & 0x0f);
		setbank(tmap,2*offset + 3,(newword >> 12) & 0x0f);
	} };
        
        static UBytePtr old_2 = new UBytePtr(8);
	
	public static WriteHandlerPtr aerofgt_gfxbank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int newword;
		struct_tilemap tmap = (offset < 4) ? bg1_tilemap : bg2_tilemap;
	
		COMBINE_WORD_MEM(old_2,offset,data);
		newword = old_2.READ_WORD(offset);
	
		setbank(tmap,offset + 0,(newword >> 8) & 0xff);
		setbank(tmap,offset + 1,(newword >> 0) & 0xff);
	} };
	
	public static WriteHandlerPtr aerofgt_bg1scrollx_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(bg1scrollx, 0, data);
	} };
	
	public static WriteHandlerPtr aerofgt_bg1scrolly_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(bg1scrolly, 0, data);
	} };
	
	public static WriteHandlerPtr aerofgt_bg2scrollx_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(bg2scrollx, 0, data);
	} };
	
	public static WriteHandlerPtr aerofgt_bg2scrolly_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(bg2scrolly, 0, data);
	} };
	
	public static WriteHandlerPtr pspikes_palette_bank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		spritepalettebank = data & 0x03;
		if (charpalettebank != (data & 0x1c) >> 2)
		{
			charpalettebank = (data & 0x1c) >> 2;
			tilemap_mark_all_tiles_dirty(bg1_tilemap);
		}
	} };
	
	
	
	/***************************************************************************
	
	  Display refresh
	
	***************************************************************************/
	
	static void aerofgt_spr_dopalette()
	{
		int offs;
		int color,i;
		int[] colmask = new int[32];
		int pal_base;
	
	
		for (color = 0;color < 32;color++) colmask[color] = 0;
	
		offs = 0;
		while (offs < 0x0800 && (spriteram_2.READ_WORD(offs) & 0x8000) == 0)
		{
			int attr_start,map_start;
	
			attr_start = 8 * (spriteram_2.READ_WORD(offs) & 0x03ff);
	
			color = (spriteram_2.READ_WORD(attr_start + 4) & 0x0f00) >> 8;
			map_start = 2 * (spriteram_2.READ_WORD(attr_start + 6) & 0x3fff);
			if (map_start >= 0x4000) color += 16;
	
			colmask[color] |= 0xffff;
	
			offs += 2;
		}
	
		pal_base = Machine.drv.gfxdecodeinfo[sprite_gfx].color_codes_start;
		for (color = 0;color < 16;color++)
		{
			for (i = 0;i < 15;i++)
			{
				if ((colmask[color] & (1 << i)) != 0)
					palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
			}
		}
		pal_base = Machine.drv.gfxdecodeinfo[sprite_gfx+1].color_codes_start;
		for (color = 0;color < 16;color++)
		{
			for (i = 0;i < 15;i++)
			{
				if ((colmask[color+16] & (1 << i)) != 0)
					palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
			}
		}
	}
	
	static void turbofrc_spr_dopalette()
	{
		int color,i;
		int[] colmask = new int[16];
		int pal_base;
		int attr_start,base,first;
	
	
		for (color = 0;color < 16;color++) colmask[color] = 0;
	
		pal_base = Machine.drv.gfxdecodeinfo[sprite_gfx].color_codes_start;
	
		base = 0;
		first = 8*spriteram_2.READ_WORD(0x3fc + base);
		for (attr_start = first + base;attr_start < base + 0x0400-8;attr_start += 8)
		{
			color = (spriteram_2.READ_WORD(attr_start + 4) & 0x000f) + 16 * spritepalettebank;
			colmask[color] |= 0xffff;
		}
	
		for (color = 0;color < 16;color++)
		{
			for (i = 0;i < 15;i++)
			{
				if ((colmask[color] & (1 << i)) != 0)
					palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
			}
		}
	
	
		if (spriteram_2_size[0] > 0x400)	/* turbofrc, not pspikes */
		{
			for (color = 0;color < 16;color++) colmask[color] = 0;
	
			pal_base = Machine.drv.gfxdecodeinfo[sprite_gfx+1].color_codes_start;
	
			base = 0x0400;
			first = 8*spriteram_2.READ_WORD(0x3fc + base);
			for (attr_start = first + base;attr_start < base + 0x0400-8;attr_start += 8)
			{
				color = (spriteram_2.READ_WORD(attr_start + 4) & 0x000f) + 16 * spritepalettebank;
				colmask[color] |= 0xffff;
			}
	
			for (color = 0;color < 16;color++)
			{
				for (i = 0;i < 15;i++)
				{
					if ((colmask[color] & (1 << i)) != 0)
						palette_used_colors.write(pal_base + 16 * color + i, PALETTE_COLOR_USED);
				}
			}
		}
	}
	
	
	static void aerofgt_drawsprites(osd_bitmap bitmap,int priority)
	{
		int offs;
	
	
		priority <<= 12;
	
		offs = 0;
		while (offs < 0x0800 && (spriteram_2.READ_WORD(offs) & 0x8000) == 0)
		{
			int attr_start;
	
			attr_start = 8 * (spriteram_2.READ_WORD(offs) & 0x03ff);
	
			/* is the way I handle priority correct? Or should I just check bit 13? */
			if ((spriteram_2.READ_WORD(attr_start + 4) & 0x3000) == priority)
			{
				int map_start;
				int ox,oy,x,y,xsize,ysize,zoomx,zoomy,flipx,flipy,color;
				/* table hand made by looking at the ship explosion in attract mode */
				/* it's almost a logarithmic scale but not exactly */
				int zoomtable[] = { 0,7,14,20,25,30,34,38,42,46,49,52,54,57,59,61 };
	
				ox = spriteram_2.READ_WORD(attr_start + 2) & 0x01ff;
				xsize = (spriteram_2.READ_WORD(attr_start + 2) & 0x0e00) >> 9;
				zoomx = (spriteram_2.READ_WORD(attr_start + 2) & 0xf000) >> 12;
				oy = spriteram_2.READ_WORD(attr_start + 0) & 0x01ff;
				ysize = (spriteram_2.READ_WORD(attr_start + 0) & 0x0e00) >> 9;
				zoomy = (spriteram_2.READ_WORD(attr_start + 0) & 0xf000) >> 12;
				flipx = spriteram_2.READ_WORD(attr_start + 4) & 0x4000;
				flipy = spriteram_2.READ_WORD(attr_start + 4) & 0x8000;
				color = (spriteram_2.READ_WORD(attr_start + 4) & 0x0f00) >> 8;
				map_start = 2 * (spriteram_2.READ_WORD(attr_start + 6) & 0x3fff);
	
				zoomx = 16 - zoomtable[zoomx]/8;
				zoomy = 16 - zoomtable[zoomy]/8;
	
				for (y = 0;y <= ysize;y++)
				{
					int sx,sy;
	
					if (flipy != 0) sy = ((oy + zoomy * (ysize - y) + 16) & 0x1ff) - 16;
					else sy = ((oy + zoomy * y + 16) & 0x1ff) - 16;
	
					for (x = 0;x <= xsize;x++)
					{
						int code;
	
						if (flipx != 0) sx = ((ox + zoomx * (xsize - x) + 16) & 0x1ff) - 16;
						else sx = ((ox + zoomx * x + 16) & 0x1ff) - 16;
	
						if (map_start < 0x4000)
							code = aerofgt_spriteram1.READ_WORD(map_start & 0x3fff) & 0x1fff;
						else
							code = aerofgt_spriteram2.READ_WORD(map_start & 0x3fff) & 0x1fff;
	
						if (zoomx == 16 && zoomy == 16)
							drawgfx(bitmap,Machine.gfx[sprite_gfx + (map_start >= 0x4000 ? 1 : 0)],
									code,
									color,
									flipx,flipy,
									sx,sy,
									Machine.visible_area,TRANSPARENCY_PEN,15);
						else
							drawgfxzoom(bitmap,Machine.gfx[sprite_gfx + (map_start >= 0x4000 ? 1 : 0)],
									code,
									color,
									flipx,flipy,
									sx,sy,
									Machine.visible_area,TRANSPARENCY_PEN,15,
									0x1000 * zoomx,0x1000 * zoomy);
						map_start += 2;
					}
				}
			}
	
			offs += 2;
		}
	}
	        
	static void turbofrc_drawsprites(osd_bitmap bitmap,int chip)
	{
            //System.out.println("turbofrc_drawsprites");
		int attr_start,base,first;
	
	
		base = chip * 0x0400;
		first = 8*spriteram_2.READ_WORD(0x3fc + base);
	
		for (attr_start = base + 0x0400-16;attr_start >= first + base;attr_start -= 8)
		{
			int map_start;
			int ox,oy,x,y,xsize,ysize,zoomx,zoomy,flipx,flipy,color,pri;
			/* table hand made by looking at the ship explosion in attract mode */
			/* it's almost a logarithmic scale but not exactly */
			int zoomtable[] = { 0,7,14,20,25,30,34,38,42,46,49,52,54,57,59,61 };
	
			if ((spriteram_2.READ_WORD(attr_start + 4) & 0x0080)==0) continue;
	
			ox = spriteram_2.READ_WORD(attr_start + 2) & 0x01ff;
			xsize = (spriteram_2.READ_WORD(attr_start + 4) & 0x0700) >> 8;
			zoomx = (spriteram_2.READ_WORD(attr_start + 2) & 0xf000) >> 12;
			oy = spriteram_2.READ_WORD(attr_start + 0) & 0x01ff;
			ysize = (spriteram_2.READ_WORD(attr_start + 4) & 0x7000) >> 12;
			zoomy = (spriteram_2.READ_WORD(attr_start + 0) & 0xf000) >> 12;
			flipx = spriteram_2.READ_WORD(attr_start + 4) & 0x0800;
			flipy = spriteram_2.READ_WORD(attr_start + 4) & 0x8000;
			color = (spriteram_2.READ_WORD(attr_start + 4) & 0x000f) + 16 * spritepalettebank;
			pri = spriteram_2.READ_WORD(attr_start + 4) & 0x0010;
			map_start = 2 * spriteram_2.READ_WORD(attr_start + 6);
	
			zoomx = 16 - zoomtable[zoomx]/8;
			zoomy = 16 - zoomtable[zoomy]/8;
	
			for (y = 0;y <= ysize;y++)
			{
				int sx,sy;
	
				if (flipy != 0) sy = ((oy + zoomy * (ysize - y) + 16) & 0x1ff) - 16;
				else sy = ((oy + zoomy * y + 16) & 0x1ff) - 16;
	
				for (x = 0;x <= xsize;x++)
				{
					int code;
	
					if (flipx != 0) sx = ((ox + zoomx * (xsize - x) + 16) & 0x1ff) - 16;
					else sx = ((ox + zoomx * x + 16) & 0x1ff) - 16;
	
					if (chip == 0)
						code = aerofgt_spriteram1.READ_WORD(map_start % aerofgt_spriteram1_size[0]);
					else
						code = aerofgt_spriteram2.READ_WORD(map_start % aerofgt_spriteram2_size[0]);
	
					if (zoomx == 16 && zoomy == 16)
						pdrawgfx(bitmap,Machine.gfx[sprite_gfx + chip],
								code,
								color,
								flipx,flipy,
								sx,sy,
								Machine.visible_area,TRANSPARENCY_PEN,15,
								pri!=0 ? 0 : 0x2);
					else
						pdrawgfxzoom(bitmap,Machine.gfx[sprite_gfx + chip],
								code,
								color,
								flipx,flipy,
								sx,sy,
								Machine.visible_area,TRANSPARENCY_PEN,15,
								0x1000 * zoomx,0x1000 * zoomy,
								pri!=0 ? 0 : 0x2);
					map_start += 2;
				}
	
				if (xsize == 2) map_start += 2;
				if (xsize == 4) map_start += 6;
				if (xsize == 5) map_start += 4;
				if (xsize == 6) map_start += 2;
			}
		}
	}
	
	
	public static VhUpdatePtr pspikes_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int i,scrolly;
	
		tilemap_set_scroll_rows(bg1_tilemap,256);
		scrolly = bg1scrolly.READ_WORD();
		for (i = 0;i < 256;i++)
			tilemap_set_scrollx(bg1_tilemap,(i + scrolly) & 0xff,aerofgt_rasterram.READ_WORD(2*i));
		tilemap_set_scrolly(bg1_tilemap,0,scrolly);
	
		tilemap_update(ALL_TILEMAPS);
	
		palette_init_used_colors();
		turbofrc_spr_dopalette();
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		fillbitmap(priority_bitmap,0,null);
	
		tilemap_draw(bitmap,bg1_tilemap,0);
		turbofrc_drawsprites(bitmap,0);
	} };
	
	public static VhUpdatePtr karatblz_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		tilemap_set_scrollx(bg1_tilemap,0,bg1scrollx.READ_WORD()-8);
		tilemap_set_scrolly(bg1_tilemap,0,bg1scrolly.READ_WORD());
		tilemap_set_scrollx(bg2_tilemap,0,bg2scrollx.READ_WORD()-4);
		tilemap_set_scrolly(bg2_tilemap,0,bg2scrolly.READ_WORD());
	
		tilemap_update(ALL_TILEMAPS);
	
		palette_init_used_colors();
		turbofrc_spr_dopalette();
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		fillbitmap(priority_bitmap,0,null);
	
		tilemap_draw(bitmap,bg1_tilemap,0);
		tilemap_draw(bitmap,bg2_tilemap,0);
	
		/* we use the priority buffer so sprites are drawn front to back */
		turbofrc_drawsprites(bitmap,1);
		turbofrc_drawsprites(bitmap,0);
	} };
	
	public static VhUpdatePtr spinlbrk_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int i,scrolly;
	
		tilemap_set_scroll_rows(bg1_tilemap,512);
		scrolly = 0;
		for (i = 0;i < 256;i++)
			tilemap_set_scrollx(bg1_tilemap,(i + scrolly) & 0x1ff,aerofgt_rasterram.READ_WORD(2*i)-8);
	//	tilemap_set_scrolly(bg1_tilemap,0,READ_WORD(bg1scrolly));
		tilemap_set_scrollx(bg2_tilemap,0,bg2scrollx.READ_WORD()-4);
	//	tilemap_set_scrolly(bg2_tilemap,0,READ_WORD(bg2scrolly));
	
		tilemap_update(ALL_TILEMAPS);
	
		palette_init_used_colors();
		turbofrc_spr_dopalette();
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		fillbitmap(priority_bitmap,0,null);
	
		tilemap_draw(bitmap,bg1_tilemap,0);
		tilemap_draw(bitmap,bg2_tilemap,0);
	
		/* we use the priority buffer so sprites are drawn front to back */
		turbofrc_drawsprites(bitmap,0);
		turbofrc_drawsprites(bitmap,1);
	} };
	
	public static VhUpdatePtr turbofrc_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int i,scrolly;
	
		tilemap_set_scroll_rows(bg1_tilemap,512);
		scrolly = bg1scrolly.READ_WORD()+2;
		for (i = 0;i < 256;i++)
	//		tilemap_set_scrollx(bg1_tilemap,(i + scrolly) & 0x1ff,READ_WORD(&aerofgt_rasterram[2*i])-11);
			tilemap_set_scrollx(bg1_tilemap,(i + scrolly) & 0x1ff,aerofgt_rasterram.READ_WORD(0xe)-11);
		tilemap_set_scrolly(bg1_tilemap,0,scrolly);
		tilemap_set_scrollx(bg2_tilemap,0,bg2scrollx.READ_WORD()-7);
		tilemap_set_scrolly(bg2_tilemap,0,bg2scrolly.READ_WORD()+2);
	
		tilemap_update(ALL_TILEMAPS);
	
		palette_init_used_colors();
		turbofrc_spr_dopalette();
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		fillbitmap(priority_bitmap,0,null);
	
		tilemap_draw(bitmap,bg1_tilemap,0);
		tilemap_draw(bitmap,bg2_tilemap,1<<16);
	
		/* we use the priority buffer so sprites are drawn front to back */
		turbofrc_drawsprites(bitmap,1);
		turbofrc_drawsprites(bitmap,0);
	} };
	
	public static VhUpdatePtr aerofgt_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		tilemap_set_scrollx(bg1_tilemap,0,aerofgt_rasterram.READ_WORD(0x0000)-18);
		tilemap_set_scrolly(bg1_tilemap,0,bg1scrolly.READ_WORD());
		tilemap_set_scrollx(bg2_tilemap,0,aerofgt_rasterram.READ_WORD(0x0400)-20);
		tilemap_set_scrolly(bg2_tilemap,0,bg2scrolly.READ_WORD());
	
		tilemap_update(ALL_TILEMAPS);
	
		palette_init_used_colors();
		aerofgt_spr_dopalette();
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		fillbitmap(priority_bitmap,0,null);
	
		tilemap_draw(bitmap,bg1_tilemap,0);
	
		aerofgt_drawsprites(bitmap,0);
		aerofgt_drawsprites(bitmap,1);
	
		tilemap_draw(bitmap,bg2_tilemap,0);
	
		aerofgt_drawsprites(bitmap,2);
		aerofgt_drawsprites(bitmap,3);
	} };
}
