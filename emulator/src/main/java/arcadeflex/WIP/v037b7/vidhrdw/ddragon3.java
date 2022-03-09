/***************************************************************************

  Video Hardware for Double Dragon 3

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.common.ptrLib.*;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;

public class ddragon3
{
	
	public static UBytePtr ddragon3_bg_videoram = new UBytePtr();
	static int ddragon3_bg_scrollx;
	static int ddragon3_bg_scrolly;
	
	static int ddragon3_bg_tilebase;
	static int old_ddragon3_bg_tilebase;
	
	public static UBytePtr ddragon3_fg_videoram = new UBytePtr();
	static int ddragon3_fg_scrollx;
	static int ddragon3_fg_scrolly;
	public static int ddragon3_vreg;
	
	static struct_tilemap background, foreground;
	
	/* scroll write function */
	public static WriteHandlerPtr ddragon3_scroll_w = new WriteHandlerPtr() {public void handler(int offset, int data){
		switch (offset) {
			case 0x0: /* Scroll X, BG1 */
			ddragon3_fg_scrollx = data;
			return;
	
			case 0x2: /* Scroll Y, BG1 */
			ddragon3_fg_scrolly = data;
			return;
	
			case 0x4: /* Scroll X, BG0 */
			ddragon3_bg_scrollx = data;
			return;
	
			case 0x6: /* Scroll Y, BG0 */
			ddragon3_bg_scrolly = data;
			return;
	
			case 0xc: /* BG Tile Base */
			ddragon3_bg_tilebase = COMBINE_WORD(ddragon3_bg_tilebase, data)&0x1ff;
			return;
	
			default:  /* Unknown */
			logerror("OUTPUT c00[%02x] %02x \n", offset,data);
			break;
		}
	} };
	
	/* background */
	public static GetTileInfoPtr get_bg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int data = new UShortPtr(ddragon3_bg_videoram).read(tile_index);
		SET_TILE_INFO( 0, (data&0xfff) | ((ddragon3_bg_tilebase&1)<<12), ((data&0xf000)>>12)+16 );  // GFX,NUMBER,COLOR
	} };
	
	public static WriteHandlerPtr ddragon3_bg_videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = ddragon3_bg_videoram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
		if( oldword != newword )
		{
			ddragon3_bg_videoram.WRITE_WORD(offset,newword);
			offset = offset/2;
			tilemap_mark_tile_dirty(background,offset);
		}
	} };
	
	public static ReadHandlerPtr ddragon3_bg_videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return ddragon3_bg_videoram.READ_WORD( offset );
	} };
	
	/* foreground */
	public static GetTileInfoPtr get_fg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		int data0 = new UShortPtr(ddragon3_fg_videoram).read(2*tile_index);
		int data1 = new UShortPtr(ddragon3_fg_videoram).read(2*tile_index+1);
		SET_TILE_INFO( 0, data1&0x1fff , data0&0xf );  // GFX,NUMBER,COLOR
	        tile_info.u32_flags = ((data0&0x40) >> 6);  // FLIPX
	} };
	
	public static WriteHandlerPtr ddragon3_fg_videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = ddragon3_fg_videoram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
		if( oldword != newword )
		{
			ddragon3_fg_videoram.WRITE_WORD(offset,newword);
			offset = offset/4;
			tilemap_mark_tile_dirty(foreground,offset);
		}
	} };
	
	public static ReadHandlerPtr ddragon3_fg_videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return ddragon3_fg_videoram.READ_WORD( offset );
	} };
	
	/* start & stop */
	public static VhStartPtr ddragon3_vh_start = new VhStartPtr() { public int handler() {
		ddragon3_bg_tilebase = 0;
		old_ddragon3_bg_tilebase = -1;
	
		background = tilemap_create(get_bg_tile_info,tilemap_scan_rows,TILEMAP_OPAQUE,     16,16,32,32);
		foreground = tilemap_create(get_fg_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,16,16,32,32);
	
		if (background==null || foreground==null)
			return 1;
	
		foreground.transparent_pen = 0;
		return 0;
	} };
	
	/*
	 * Sprite Format
	 * ----------------------------------
	 *
	 * Word | Bit(s)           | Use
	 * -----+-fedcba9876543210-+----------------
	 *   0	| --------xxxxxxxx | ypos (signed)
	 * -----+------------------+
	 *   1	| --------xxx----- | height
	 *   1  | -----------xx--- | yflip, xflip
	 *   1  | -------------x-- | msb x
	 *   1  | --------------x- | msb y?
	 *   1  | ---------------x | enable
	 * -----+------------------+
	 *   2  | --------xxxxxxxx | tile number
	 * -----+------------------+
	 *   3  | --------xxxxxxxx | bank
	 * -----+------------------+
	 *   4  | ------------xxxx |color
	 * -----+------------------+
	 *   5  | --------xxxxxxxx | xpos
	 * -----+------------------+
	 *   6,7| unused
	 */
	
	static void draw_sprites( osd_bitmap bitmap ){
		rectangle clip = new rectangle(Machine.visible_area);
		GfxElement gfx = Machine.gfx[1];
		UShortPtr source = new UShortPtr(spriteram);
		UShortPtr finish = new UShortPtr(source, 0x800);
	
		while( source.offset<finish.offset ){
			int attributes = source.read(1);
			if ((attributes & 0x01) != 0){ /* enable */
				int flipx = attributes&0x10;
				int flipy = attributes&0x08;
				int height = (attributes>>5)&0x7;
	
				int sy = source.read(0)&0xff;
				int sx = source.read(5)&0xff;
				int tile_number = source.read(2)&0xff;
				int color = source.read(4)&0xf;
				int bank = source.read(3)&0xff;
				int i;
	
				if ((attributes & 0x04) != 0) sx|=0x100;
				if ((attributes & 0x02) != 0) sy=239+(0x100-sy); else sy=240-sy;
				if (sx>0x17f) sx=0-(0x200-sx);
	
				tile_number += (bank*256);
	
				for( i=0; i<=height; i++ ){
					int tile_index = tile_number + i;
	
					drawgfx(bitmap,gfx,
						tile_index,
						color,
						flipx,flipy,
						sx,sy-i*16,
						clip,TRANSPARENCY_PEN,0);
				}
			}
			source.inc(8);
		}
	}
	
	static void mark_sprite_colors()
	{
		int offs,color,i,pal_base,sprite,multi,attr;
		int[] colmask = new int[16];
                int[] pen_usage; /* Save some struct derefs */
	
		/* Sprites */
		pal_base = Machine.drv.gfxdecodeinfo[1].color_codes_start;
		pen_usage=Machine.gfx[1].pen_usage;
		for (color = 0;color < 16;color++) colmask[color] = 0;
		for (offs = 0;offs < 0x1000;offs += 16)
		{
			attr = spriteram.READ_WORD (offs+2);
			if ((attr&1)==0) continue;
	
			multi = (attr>>5)&0x7;
			sprite = spriteram.READ_WORD (offs+4) & 0xff;
			sprite += ((spriteram.READ_WORD (offs+6) & 0xff)<<8);
			color = spriteram.READ_WORD (offs+8) & 0xf;
	
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
	
	public static VhUpdatePtr ddragon3_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		if( ddragon3_bg_tilebase != old_ddragon3_bg_tilebase )
		{
			old_ddragon3_bg_tilebase = ddragon3_bg_tilebase;
			tilemap_mark_all_tiles_dirty( background );
		}
	
		tilemap_set_scrolly( background, 0, ddragon3_bg_scrolly );
		tilemap_set_scrollx( background, 0, ddragon3_bg_scrollx );
	
		tilemap_set_scrolly( foreground, 0, ddragon3_fg_scrolly );
		tilemap_set_scrollx( foreground, 0, ddragon3_fg_scrollx );
	
		tilemap_update( background );
		tilemap_update( foreground );
	
		palette_init_used_colors();
		mark_sprite_colors();
		if( palette_recalc() != null ) tilemap_mark_all_pixels_dirty( ALL_TILEMAPS );
	
		tilemap_render( background );
		tilemap_render( foreground );
	
		if ((ddragon3_vreg & 0x40) != 0) {
			tilemap_draw( bitmap, background, 0 );
			tilemap_draw( bitmap, foreground, 0 );
			draw_sprites( bitmap );
		}
		else {
			tilemap_draw( bitmap, background, 0 );
			draw_sprites( bitmap );
			tilemap_draw( bitmap, foreground, 0 );
		}
	} };
	
	public static VhUpdatePtr ctribe_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		if( ddragon3_bg_tilebase != old_ddragon3_bg_tilebase )
		{
			old_ddragon3_bg_tilebase = ddragon3_bg_tilebase;
			tilemap_mark_all_tiles_dirty( background );
		}
	
		tilemap_set_scrolly( background, 0, ddragon3_bg_scrolly );
		tilemap_set_scrollx( background, 0, ddragon3_bg_scrollx );
		tilemap_set_scrolly( foreground, 0, ddragon3_fg_scrolly );
		tilemap_set_scrollx( foreground, 0, ddragon3_fg_scrollx );
	
		tilemap_update( background );
		tilemap_update( foreground );
	
		palette_init_used_colors();
		mark_sprite_colors();
		if( palette_recalc() != null ) tilemap_mark_all_pixels_dirty( ALL_TILEMAPS );
	
		tilemap_render( background );
		tilemap_render( foreground );
	
		tilemap_draw( bitmap, background, 0 );
		tilemap_draw( bitmap, foreground, 0 );
		draw_sprites( bitmap );
	} };
}
