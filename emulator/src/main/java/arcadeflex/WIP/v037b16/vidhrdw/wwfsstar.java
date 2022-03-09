/*******************************************************************************
 WWF Superstars (C) 1989 Technos Japan  (vidhrdw/wwfsstar.c)
********************************************************************************
 driver by David Haywood

 see (drivers/wwfsstar.c) for more notes
*******************************************************************************/

/*
 * ported to v0.37b16
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b16.vidhrdw;

import static arcadeflex.WIP.v037b16.drivers.wwfsstar.bg0_videoram;
import static arcadeflex.WIP.v037b16.drivers.wwfsstar.fg0_videoram;
import static arcadeflex.WIP.v037b16.drivers.wwfsstar.wwfsstar_scrollx;
import static arcadeflex.WIP.v037b16.drivers.wwfsstar.wwfsstar_scrolly;
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.drawgfx;

public class wwfsstar
{
	
	static struct_tilemap fg0_tilemap, bg0_tilemap;
	
	/*******************************************************************************
	 Write Handlers
	********************************************************************************
	 for writes to Video Ram
	*******************************************************************************/
	
	public static WriteHandlerPtr wwfsstar_fg0_videoram_w = new WriteHandlerPtr() {
            @Override
            public void handler(int offset, int data) {
                int oldword = fg0_videoram.read(offset);
		fg0_videoram.write(offset, (char) COMBINE_WORD(fg0_videoram.read(offset), data));
		if (oldword != fg0_videoram.read(offset))
			tilemap_mark_tile_dirty(fg0_tilemap,offset/2);
            }
        };
	
	public static WriteHandlerPtr wwfsstar_bg0_videoram_w = new WriteHandlerPtr() {
            @Override
            public void handler(int offset, int data) {
		int oldword =bg0_videoram.read(offset);
		//COMBINE_DATA(&bg0_videoram[offset]);
                bg0_videoram.write(offset, (char) COMBINE_WORD(bg0_videoram.read(offset), data));
		if (oldword != bg0_videoram.read(offset))
			tilemap_mark_tile_dirty(bg0_tilemap,offset/2);
            }
        };
	
	/*******************************************************************************
	 Tilemap Related Functions
	*******************************************************************************/
	
	public static GetTileInfoPtr get_fg0_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		/*- FG0 RAM Format -**
	
		  0x1000 sized region (4096 bytes)
	
		  32x32 tilemap, 4 bytes per tile
	
		  ---- ----  CCCC TTTT  ---- ----  TTTT TTTT
	
		  C = Colour Bank (0-15)
		  T = Tile Number (0 - 4095)
	
		  other bits unknown / unused
	
		**- End of Comments -*/
	
		UShortPtr tilebase;
		int tileno;
		int colbank;
		tilebase =  new UShortPtr(fg0_videoram, tile_index*2);
		tileno =  (tilebase.read(1) & 0x00ff) | ((tilebase.read(0) & 0x000f) << 8);
		colbank = (tilebase.read(0) & 0x00f0) >> 4;
		SET_TILE_INFO(
				0,
				tileno,
				colbank
                                /*, 0*/
                );
	} };
	
	static GetMemoryOffsetPtr bg0_scan = new GetMemoryOffsetPtr() {
            @Override
            public int handler(int col, int row, int num_cols, int num_rows) {
                return (col & 0x0f) + ((row & 0x0f) << 4) + ((col & 0x10) << 4) + ((row & 0x10) << 5);
            }
        };
        
	public static GetTileInfoPtr get_bg0_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		/*- BG0 RAM Format -**
	
		  0x1000 sized region (4096 bytes)
	
		  32x32 tilemap, 4 bytes per tile
	
		  ---- ----  FCCC TTTT  ---- ----  TTTT TTTT
	
		  C = Colour Bank (0-7)
		  T = Tile Number (0 - 4095)
		  F = FlipX
	
		  other bits unknown / unused
	
		**- End of Comments -*/
	
		UShortPtr tilebase;
		int tileno, colbank, flipx;
		tilebase =  new UShortPtr(bg0_videoram, tile_index*2);
		tileno =  (tilebase.read(1) & 0x00ff) | ((tilebase.read(0) & 0x000f) << 8);
		colbank = (tilebase.read(0) & 0x0070) >> 4;
		flipx   = (tilebase.read(0) & 0x0080) >> 7;
		SET_TILE_INFO(
				2,
				tileno,
				colbank
                                /*,flipx!=0 ? TILE_FLIPX : 0*/
                );
	} };
	
	/*******************************************************************************
	 Sprite Related Functions
	********************************************************************************
	 sprite colour marking could probably be improved..
	*******************************************************************************/
	
	static void wwfsstar_sprites_mark_colors()
	{
		/* this is very crude */
		int i;
		UShortPtr source = new UShortPtr(spriteram/*16*/);
		UShortPtr finish = new UShortPtr(source, 0x3ff/2);
	
		while( source.offset<finish.offset )
		{
			int colourbank;
			colourbank = (source .read(1) & 0x00f0) >> 4;
			for (i = 0;i < 16;i++)
				palette_used_colors.write(128 + 16 * colourbank + i, PALETTE_COLOR_USED);
	
			source.inc(5);
		}
	}
	
	static void wwfsstar_drawsprites( osd_bitmap bitmap )
	{
		/*- SPR RAM Format -**
	
		  0x3FF sized region (1024 bytes)
	
		  10 bytes per sprite
	
		  ---- ---- yyyy yyyy ---- ---- CCCC XYLE ---- ---- fFNN NNNN ---- ---- nnnn nnnn ---- ---- xxxx xxxx
	
		  Yy = sprite Y Position
		  Xx = sprite X Position
		  C  = colour bank
		  f  = flip Y
		  F  = flip X
		  L  = chain sprite (32x16)
		  E  = sprite enable
		  Nn = Sprite Number
	
		  other bits unused
	
		**- End of Comments -*/
	
		rectangle clip = new rectangle(Machine.visible_area);
		GfxElement gfx = Machine.gfx[1];
		UShortPtr source = new UShortPtr(spriteram/*16*/);
		UShortPtr finish = new UShortPtr(source, 0x3ff/2);
	
		while( source.offset<finish.offset )
		{
			int xpos, ypos, colourbank, flipx, flipy, chain, enable, number;
	
			ypos = ((source .read(0) & 0x00ff) | ((source .read(1) & 0x0004) << 6) );
                        ypos=(((256-ypos)&0x1ff)-16) ;
			xpos = ((source .read(4) & 0x00ff) | ((source .read(1) & 0x0008) << 5) );
			xpos = (((256-xpos)&0x1ff)-16);
			colourbank = (source .read(1) & 0x00f0) >> 4;
			flipx = (source .read(2) & 0x0080 ) >> 7;
			flipy = (source .read(2) & 0x0040 ) >> 6;
			chain = (source .read(1) & 0x0002 ) >> 1;
			enable = (source .read(1) & 0x0001);
			number = (source .read(3) & 0x00ff) | ((source .read(2) & 0x003f) << 8);
	
			 if (enable != 0) {
				if (chain != 0){
					drawgfx(bitmap,gfx,number,colourbank,flipx,flipy,xpos,ypos-16,clip,TRANSPARENCY_PEN,0);
					drawgfx(bitmap,gfx,number+1,colourbank,flipx,flipy,xpos,ypos,clip,TRANSPARENCY_PEN,0);
				} else {
					drawgfx(bitmap,gfx,number,colourbank,flipx,flipy,xpos,ypos,clip,TRANSPARENCY_PEN,0);
				}
			}
	
		source.inc(5);
		}
	}
	
	/*******************************************************************************
	 Video Start and Refresh Functions
	********************************************************************************
	 Drawing Order is simple
	 BG0 - Back
	 SPR - Middle
	 FG0 - Front
	*******************************************************************************/
	
	
	public static VhStartPtr wwfsstar_vh_start = new VhStartPtr() { public int handler() 
	{
		fg0_tilemap = tilemap_create(get_fg0_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT, 8, 8,32,32);
		//tilemap_set_transparent_pen(fg0_tilemap,0);
                fg0_tilemap.transparent_pen=0;
	
		bg0_tilemap = tilemap_create(get_bg0_tile_info,bg0_scan,TILEMAP_OPAQUE, 16, 16,32,32);
		//tilemap_set_transparent_pen(fg0_tilemap,0);
                bg0_tilemap.transparent_pen=0;
	
		if (fg0_tilemap==null || bg0_tilemap==null)
			return 1;
	
		return 0;
	} };
	
	public static VhUpdatePtr wwfsstar_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		tilemap_set_scrolly( bg0_tilemap, 0, wwfsstar_scrolly  );
		tilemap_set_scrollx( bg0_tilemap, 0, wwfsstar_scrollx  );
	
		tilemap_update(ALL_TILEMAPS);
	
		palette_init_used_colors();
	
		wwfsstar_sprites_mark_colors();
		palette_recalc();
	
		tilemap_draw(bitmap,bg0_tilemap,0/*,0*/);
		wwfsstar_drawsprites( bitmap );
		tilemap_draw(bitmap,fg0_tilemap,0/*,0*/);
	} };
}
