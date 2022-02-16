/***************************************************************************

	Ninja Gaiden / Tecmo Knights Video Hardware

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.common.ptrLib.*;
import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_getcurrentframe;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.pdrawgfx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;

public class gaiden
{
	
	public static UBytePtr gaiden_videoram = new UBytePtr();
	public static UBytePtr gaiden_videoram2 = new UBytePtr();
	public static UBytePtr gaiden_videoram3 = new UBytePtr();
	
	static struct_tilemap text_layer, foreground, background;
	
	
	/***************************************************************************
	
	  Callbacks for the TileMap code
	
	***************************************************************************/
	
	public static GetTileInfoPtr get_bg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		UShortPtr videoram1 = new UShortPtr(gaiden_videoram3, 0x1000);
		UShortPtr videoram2 = new UShortPtr(gaiden_videoram3);
		SET_TILE_INFO(1,videoram1.read(tile_index) & 0xfff,(videoram2.read(tile_index) & 0xf0) >> 4);
	} };
	
	public static GetTileInfoPtr get_fg_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		UShortPtr videoram1 = new UShortPtr(gaiden_videoram2, 0x1000);
		UShortPtr videoram2 = new UShortPtr(gaiden_videoram2);
		SET_TILE_INFO(2,videoram1.read(tile_index) & 0xfff,(videoram2.read(tile_index) & 0xf0) >> 4);
	} };
	
	public static GetTileInfoPtr get_tx_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
	{
		UShortPtr videoram1 = new UShortPtr(gaiden_videoram, 0x0800);
		UShortPtr videoram2 = new UShortPtr(gaiden_videoram);
		SET_TILE_INFO(0,videoram1.read(tile_index) & 0x7ff,(videoram2.read(tile_index) & 0xf0) >> 4);
	} };
	
	
	/***************************************************************************
	
	  Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStartPtr gaiden_vh_start = new VhStartPtr() { public int handler() 
	{
		background = tilemap_create(get_bg_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,16,16,64,32);
		foreground = tilemap_create(get_fg_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,16,16,64,32);
		text_layer = tilemap_create(get_tx_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT, 8, 8,32,32);
	
		if (text_layer==null || foreground==null || background==null)
			return 1;
	
		background.transparent_pen = 0;
		foreground.transparent_pen = 0;
		text_layer.transparent_pen = 0;
		palette_transparent_color = 0x200; /* background color */
		return 0;
	} };
	
	
	
	/***************************************************************************
	
	  Memory handlers
	
	***************************************************************************/
	static int oldword_txscrollx_w;
        
	public static WriteHandlerPtr gaiden_txscrollx_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		oldword_txscrollx_w = COMBINE_WORD(oldword_txscrollx_w,data);
		tilemap_set_scrollx(text_layer,0,oldword_txscrollx_w);
	} };
        
        static int oldword_txscrolly_w;
	
	public static WriteHandlerPtr gaiden_txscrolly_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		oldword_txscrolly_w = COMBINE_WORD(oldword_txscrolly_w,data);
		tilemap_set_scrolly(text_layer,0,oldword_txscrolly_w);
	} };
        
        static int oldword_fgscrollx_w;
	
	public static WriteHandlerPtr gaiden_fgscrollx_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		oldword_fgscrollx_w = COMBINE_WORD(oldword_fgscrollx_w,data);
		tilemap_set_scrollx(foreground,0,oldword_fgscrollx_w);
	} };
        
        static int oldword_fgscrolly_w;
	
	public static WriteHandlerPtr gaiden_fgscrolly_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		oldword_fgscrolly_w = COMBINE_WORD(oldword_fgscrolly_w,data);
		tilemap_set_scrolly(foreground,0,oldword_fgscrolly_w);
	} };
        
        static int oldword_bgscrollx_w;
	
	public static WriteHandlerPtr gaiden_bgscrollx_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		oldword_bgscrollx_w = COMBINE_WORD(oldword_bgscrollx_w,data);
		tilemap_set_scrollx(background,0,oldword_bgscrollx_w);
	} };
        
        static int oldword_bgscrolly_w;
	
	public static WriteHandlerPtr gaiden_bgscrolly_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		
		oldword_bgscrolly_w = COMBINE_WORD(oldword_bgscrolly_w,data);
		tilemap_set_scrolly(background,0,oldword_bgscrolly_w);
	} };
	
	public static WriteHandlerPtr gaiden_videoram3_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = gaiden_videoram3.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			int tile_index = (offset/2)&0x7ff;
			gaiden_videoram3.WRITE_WORD(offset, newword);
			tilemap_mark_tile_dirty(background,tile_index);
		}
	} };
	
	public static ReadHandlerPtr gaiden_videoram3_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	   return gaiden_videoram3.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr gaiden_videoram2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = gaiden_videoram2.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			int tile_index = (offset/2)&0x7ff;
			gaiden_videoram2.WRITE_WORD(offset, newword);
			tilemap_mark_tile_dirty(foreground,tile_index);
		}
	} };
	
	public static ReadHandlerPtr gaiden_videoram2_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	   return gaiden_videoram2.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr gaiden_videoram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = gaiden_videoram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			int tile_index = (offset/2)&0x3ff;
			gaiden_videoram.WRITE_WORD(offset, newword);
			tilemap_mark_tile_dirty(text_layer,tile_index);
		}
	} };
	
	public static ReadHandlerPtr gaiden_videoram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return gaiden_videoram.READ_WORD(offset);
	} };
	
	
	
	/***************************************************************************
	
	  Display refresh
	
	***************************************************************************/
	
	/* sprite format:
	 *
	 *	word		bit					usage
	 * --------+-fedcba9876543210-+----------------
	 *    0    | ---------------x | flip x
	 *         | --------------x- | flip y
	 *         | -------------x-- | enable
	 *         | ----------x----- | flicker
	 *         | --------xx------ | sprite-tile priority
	 *    1    | xxxxxxxxxxxxxxxx | number
	 *    2    | --------xxxx---- | palette
	 *         | --------------xx | size: 8x8, 16x16, 32x32, 64x64
	 *    3    | xxxxxxxxxxxxxxxx | y position
	 *    4    | xxxxxxxxxxxxxxxx | x position
	 *    5,6,7|                  | unused
	 */
	
	static final int NUM_SPRITES = 128;
	
	static void mark_sprite_colors()
	{
		UShortPtr source = new UShortPtr(spriteram);
		GfxElement gfx = Machine.gfx[3];
		int i;
		for (i = 0;i < NUM_SPRITES;i++)
		{
			int attributes = source.read(0);
			if ((attributes & 0x04) != 0)	/* visible */
			{
				int pen_usage = 0xfffe;
				int color = (source.read(2) >> 4) & 0xf;
				UShortArray pal_data = new UShortArray(gfx.colortable, gfx.color_granularity * color);
				int indx = pal_data.offset - Machine.remapped_colortable.offset;
				while (pen_usage != 0)
				{
					if ((pen_usage & 1) != 0) palette_used_colors.write(indx, PALETTE_COLOR_USED);
					pen_usage >>= 1;
					indx++;
				}
			}
			source.offset += 8;
		}
	}
	
	static void draw_sprites( osd_bitmap bitmap )
	{
		int layout[][] =
		{
			{0,1,4,5,16,17,20,21},
			{2,3,6,7,18,19,22,23},
			{8,9,12,13,24,25,28,29},
			{10,11,14,15,26,27,30,31},
			{32,33,36,37,48,49,52,53},
			{34,35,38,39,50,51,54,55},
			{40,41,44,45,56,57,60,61},
			{42,43,46,47,58,59,62,63}
		};
	
		rectangle clip = new rectangle(Machine.visible_area);
		GfxElement gfx = Machine.gfx[3];
		UShortPtr source = new UShortPtr(spriteram, (NUM_SPRITES-1)*8);
		int count = NUM_SPRITES;
	
		/* draw all sprites from front to back */
		while( count-- != 0)
		{
			int attributes = source.read(0);
			if ( (attributes&0x04)!=0 && ((attributes&0x20)==0 || (cpu_getcurrentframe() & 1)!=0) )
			{
				int priority = (attributes>>6)&3;
				int number = (source.read(1)&0x7fff);
				int color = source.read(2);
				int size = 1<<(color&0x3); // 1,2,4,8
				int flipx = (attributes&1);
				int flipy = (attributes&2);
				int priority_mask;
				int ypos = source.read(3) & 0x1ff;
				int xpos = source.read(4) & 0x1ff;
				int col,row;
				color = (color>>4)&0xf;
	
				/* wraparound */
				if( xpos >= 256) xpos -= 512;
				if( ypos >= 256) ypos -= 512;
	
				/* bg: 1; fg:2; text: 4 */
				switch( priority )
				{
					default:
					case 0x0: priority_mask = 0; break;
					case 0x1: priority_mask = 0xf0; break; /* obscured by text layer */
					case 0x2: priority_mask = 0xf0|0xcc; break;	/* obscured by foreground */
					case 0x3: priority_mask = 0xf0|0xcc|0xaa; break; /* obscured by bg and fg */
				}
	
				for( row=0; row<size; row++ )
				{
					for( col=0; col<size; col++ )
					{
						int sx = xpos + 8*(flipx!=0?(size-1-col):col);
						int sy = ypos + 8*(flipy!=0?(size-1-row):row);
						pdrawgfx(bitmap,gfx,
							number + layout[row][col],
							color,
							flipx,flipy,
							sx,sy,
							clip,TRANSPARENCY_PEN,0,
							priority_mask);
					}
				}
			}
			source.offset -= 8;
		}
	}
	
	public static VhUpdatePtr gaiden_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		tilemap_update(ALL_TILEMAPS);
	
		palette_init_used_colors();
		mark_sprite_colors();
		palette_used_colors.write(0x200, PALETTE_COLOR_USED);
	
		if (palette_recalc() != null) tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		fillbitmap(priority_bitmap,0,null);
		fillbitmap(bitmap,Machine.pens[0x200],Machine.visible_area);
		tilemap_draw(bitmap,background,1<<16);
		tilemap_draw(bitmap,foreground,2<<16);
		tilemap_draw(bitmap,text_layer,4<<16);
	
		draw_sprites( bitmap );
	} };
}
