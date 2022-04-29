/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static arcadeflex.v037b7.generic.funcPtr.*;
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
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static arcadeflex.v037b7.vidhrdw.konamiic.*;
import static arcadeflex.v037b7.vidhrdw.konamiicH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;        
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.fillbitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.*;

public class gradius3
{
	
	
	static int TOTAL_CHARS      = 0x1000;
	static int TOTAL_SPRITES    = 0x4000;
	
	public static UBytePtr gradius3_gfxram = new UBytePtr();
	public static int gradius3_priority;
	public static int[] layer_colorbase = new int[3];
        static int sprite_colorbase;
	public static int dirtygfx;
	public static UBytePtr dirtychar = new UBytePtr();
	
	
	
	/***************************************************************************
	
	  Callbacks for the K052109
	
	***************************************************************************/
	
	public static K052109_callbackProcPtr gradius3_tile_callback = new K052109_callbackProcPtr() { public void handler(int layer,int bank,int[] code,int[] color) 
	{
		/* (color & 0x02) is flip y handled internally by the 052109 */
		code[0] |= ((color[0] & 0x01) << 8) | ((color[0] & 0x1c) << 7);
		color[0] = layer_colorbase[layer] + ((color[0] & 0xe0) >> 5);
	} };
	
	
	
	/***************************************************************************
	
	  Callbacks for the K051960
	
	***************************************************************************/
	
	public static K051960_callbackProcPtr gradius3_sprite_callback = new K051960_callbackProcPtr() { public void handler(int[] code,int[] color,int[] priority_mask, int[] shadow) 
	{
		int L0 = 0xaa;
		int L1 = 0xcc;
		int L2 = 0xf0;
		int primask[][] =
		{
			{ L0|L2, L0, L0|L2, L0|L1|L2 },
			{ L1|L2, L2, 0,     L0|L1|L2 }
		};
		int pri = ((color[0] & 0x60) >> 5);
		if (gradius3_priority == 0) priority_mask[0] = primask[0][pri];
		else priority_mask[0] = primask[1][pri];
	
		code[0] |= (color[0] & 0x01) << 13;
		color[0] = sprite_colorbase + ((color[0] & 0x1e) >> 1);
	} };
	
	
	
	/***************************************************************************
	
	  Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStartPtr gradius3_vh_start = new VhStartPtr() { public int handler() 
	{
		int i;
		GfxLayout spritelayout = new GfxLayout
		(
			8,8,
			TOTAL_SPRITES,
			4,
			new int[] { 0, 1, 2, 3 },
			new int[] { 2*4, 3*4, 0*4, 1*4, 6*4, 7*4, 4*4, 5*4,
					32*8+2*4, 32*8+3*4, 32*8+0*4, 32*8+1*4, 32*8+6*4, 32*8+7*4, 32*8+4*4, 32*8+5*4 },
			new int[] { 0*32, 1*32, 2*32, 3*32, 4*32, 5*32, 6*32, 7*32,
					64*8+0*32, 64*8+1*32, 64*8+2*32, 64*8+3*32, 64*8+4*32, 64*8+5*32, 64*8+6*32, 64*8+7*32 },
			128*8
		);
	
	
		layer_colorbase[0] = 0;
		layer_colorbase[1] = 32;
		layer_colorbase[2] = 48;
		sprite_colorbase = 16;
		if (K052109_vh_start(REGION_GFX1,0, 1, 2, 3/*NORMAL_PLANE_ORDER*/,gradius3_tile_callback) != 0)
			return 1;
		if (K051960_vh_start(REGION_GFX2,3,2,1,0/*REVERSE_PLANE_ORDER*/,gradius3_sprite_callback) != 0)
		{
			K052109_vh_stop();
			return 1;
		}
	
		/* re-decode the sprites because the ROMs are connected to the custom IC differently
		   from how they are connected to the CPU. */
		for (i = 0;i < TOTAL_SPRITES;i++)
			decodechar(Machine.gfx[1],i,memory_region(REGION_GFX2),spritelayout);
	
		if ((dirtychar = new UBytePtr(TOTAL_CHARS))==null)
		{
			K052109_vh_stop();
			K051960_vh_stop();
			return 1;
		}
	
		memset(dirtychar,1,TOTAL_CHARS);
	
		return 0;
            }
        };
	
	public static VhStopPtr gradius3_vh_stop = new VhStopPtr() { public void handler() 
	{
		K052109_vh_stop();
		K051960_vh_stop();
		dirtychar = null;
	} };
	
	
	
	/***************************************************************************
	
	  Memory handlers
	
	***************************************************************************/
	
	public static ReadHandlerPtr gradius3_gfxrom_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		UBytePtr gfxdata = new UBytePtr(memory_region(REGION_GFX2));
	
		return (gfxdata.read(offset+1) << 8) | gfxdata.read(offset);
	} };
	
	public static ReadHandlerPtr gradius3_gfxram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return gradius3_gfxram.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr gradius3_gfxram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		int oldword = gradius3_gfxram.READ_WORD(offset);
		int newword = COMBINE_WORD(oldword,data);
	
		if (oldword != newword)
		{
			dirtygfx = 1;
			dirtychar.write(offset / 32, 1);
			gradius3_gfxram.WRITE_WORD(offset,newword);
		}
	} };
	
	
	
	/***************************************************************************
	
	  Display refresh
	
	***************************************************************************/
	
	public static VhUpdatePtr gradius3_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		GfxLayout charlayout = new GfxLayout
		(
			8,8,
			TOTAL_CHARS,
			4,
			new int[] { 0, 1, 2, 3 },
/*TODO*///	#ifdef LSB_FIRST
			new int[] { 2*4, 3*4, 0*4, 1*4, 6*4, 7*4, 4*4, 5*4 },
/*TODO*///	#else
/*TODO*///			new int[] { 0*4, 1*4, 2*4, 3*4, 4*4, 5*4, 6*4, 7*4 },
/*TODO*///	#endif
			new int[] { 0*32, 1*32, 2*32, 3*32, 4*32, 5*32, 6*32, 7*32 },
			32*8
		);
	
		/* TODO: this kludge enforces the char banks. For some reason, they don't work otherwise. */
		K052109_w.handler(0x1d80,0x10);
		K052109_w.handler(0x1f00,0x32);
	
		if (dirtygfx != 0)
		{
			int i;
	
			dirtygfx = 0;
	
			for (i = 0;i < TOTAL_CHARS;i++)
			{
				if (dirtychar.read(i) != 0)
				{
					dirtychar.write(i, 0);
					decodechar(Machine.gfx[0],i,gradius3_gfxram,charlayout);
				}
			}
	
			tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
		}
	
		K052109_tilemap_update();
	
		palette_init_used_colors();
		K051960_mark_sprites_colors();
		if (palette_recalc() != null)
			tilemap_mark_all_pixels_dirty(ALL_TILEMAPS);
	
		tilemap_render(ALL_TILEMAPS);
	
		fillbitmap(priority_bitmap,0,null);
		if (gradius3_priority == 0)
		{
			K052109_tilemap_draw(bitmap,1,TILEMAP_IGNORE_TRANSPARENCY|(2<<16));
			K052109_tilemap_draw(bitmap,2,4<<16);
			K052109_tilemap_draw(bitmap,0,1<<16);
		}
		else
		{
			K052109_tilemap_draw(bitmap,0,TILEMAP_IGNORE_TRANSPARENCY|(1<<16));
			K052109_tilemap_draw(bitmap,1,2<<16);
			K052109_tilemap_draw(bitmap,2,4<<16);
		}
	
		K051960_sprites_draw(bitmap,-1,-1);
            }
        };
}
