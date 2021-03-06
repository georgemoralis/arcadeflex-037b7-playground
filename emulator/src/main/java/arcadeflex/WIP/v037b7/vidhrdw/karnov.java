/*******************************************************************************

	Karnov - Bryan McPhail, mish@tendril.co.uk

*******************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.vidhrdw.generic.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static gr.codebb.arcadeflex.common.libc.cstring.memset;

public class karnov
{
	
	public static UBytePtr karnov_foreground=new UBytePtr(), dirty_f=new UBytePtr();
	public static osd_bitmap bitmap_f;
	public static UBytePtr karnov_scroll=new UBytePtr(4);
	
	/***************************************************************************
	
	  Convert the color PROMs into a more useable format.
	
	  Karnov has two 1024x8 palette PROM.
	  I don't know the exact values of the resistors between the RAM and the
	  RGB output. I assumed these values (the same as Commando)
	
	  bit 7 -- 220 ohm resistor  -- GREEN
	        -- 470 ohm resistor  -- GREEN
	        -- 1  kohm resistor  -- GREEN
	        -- 2.2kohm resistor  -- GREEN
	        -- 220 ohm resistor  -- RED
	        -- 470 ohm resistor  -- RED
	        -- 1  kohm resistor  -- RED
	  bit 0 -- 2.2kohm resistor  -- RED
	
	  bit 7 -- unused
	        -- unused
	        -- unused
	        -- unused
	        -- 220 ohm resistor  -- BLUE
	        -- 470 ohm resistor  -- BLUE
	        -- 1  kohm resistor  -- BLUE
	  bit 0 -- 2.2kohm resistor  -- BLUE
	
	***************************************************************************/
	public static VhConvertColorPromPtr karnov_vh_convert_color_prom = new VhConvertColorPromPtr() { public void handler(char []palette, char []colortable, UBytePtr color_prom) 
	{
		int i;
                int _palette=0;
	
		for (i = 0;i < Machine.drv.total_colors;i++)
		{
			int bit0,bit1,bit2,bit3;
	
			bit0 = (color_prom.read(0)>> 0) & 0x01;
			bit1 = (color_prom.read(0)>> 1) & 0x01;
			bit2 = (color_prom.read(0)>> 2) & 0x01;
			bit3 = (color_prom.read(0)>> 3) & 0x01;
			palette[_palette++] = (char) (0x0e * bit0 + 0x1f * bit1 + 0x43 * bit2 + 0x8f * bit3);
			bit0 = (color_prom.read(0)>> 4) & 0x01;
			bit1 = (color_prom.read(0)>> 5) & 0x01;
			bit2 = (color_prom.read(0)>> 6) & 0x01;
			bit3 = (color_prom.read(0)>> 7) & 0x01;
			palette[_palette++] = (char) (0x0e * bit0 + 0x1f * bit1 + 0x43 * bit2 + 0x8f * bit3);
			bit0 = (color_prom.read(Machine.drv.total_colors)>> 0) & 0x01;
			bit1 = (color_prom.read(Machine.drv.total_colors)>> 1) & 0x01;
			bit2 = (color_prom.read(Machine.drv.total_colors)>> 2) & 0x01;
			bit3 = (color_prom.read(Machine.drv.total_colors)>> 3) & 0x01;
			palette[_palette++] = (char) (0x0e * bit0 + 0x1f * bit1 + 0x43 * bit2 + 0x8f * bit3);
	
			color_prom.inc();
		}
	} };
	
	/******************************************************************************/
	
	public static VhUpdatePtr karnov_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int my,mx,offs,color,tile;
		int scrollx=karnov_scroll.READ_WORD (0);
		int scrolly=karnov_scroll.READ_WORD (2);
	
		/* 1st area is stored along X-axis... */
		mx=-1; my=0;
		for (offs = 0;offs < 0x800; offs += 2) {
			mx++;
			if (mx==32) {mx=0; my++;}
	
			if (dirty_f.read(offs)==0) continue; else dirty_f.write(offs, 0);
	
			tile=karnov_foreground.READ_WORD(offs);
			color = tile >> 12;
			tile = tile&0x7ff;
	
			drawgfx(bitmap_f,Machine.gfx[1],tile,
				color, 0,0, 16*mx,16*my,
			 	null,TRANSPARENCY_NONE,0);
		}
	
		/* 2nd area is stored along Y-axis... */
		mx=0; my=-1;
		for (offs = 0x800 ;offs < 0x1000; offs += 2) {
			my++;
			if (my==32) {my=0; mx++;}
	
			if (dirty_f.read(offs)==0) continue; else dirty_f.write(offs, 0);
	
			tile=karnov_foreground.READ_WORD(offs);
			color = tile >> 12;
			tile=tile&0x7ff;
	
			drawgfx(bitmap_f,Machine.gfx[1],tile,
				color, 0,0, 16*mx,16*my,
			 	null,TRANSPARENCY_NONE,0);
		}
	
		scrolly=-scrolly;
		scrollx=-scrollx;
		copyscrollbitmap(bitmap,bitmap_f,1,new int[]{scrollx},1,new int[]{scrolly},null,TRANSPARENCY_NONE,0);
	
		/* Sprites */
		for (offs = 0;offs <0x800;offs += 8) {
			int x,y,sprite,sprite2,colour,fx,fy,extra;
	
		    y=buffered_spriteram.READ_WORD(offs);
		    if ((y&0x8000)==0) continue;
	
		    y=y&0x1ff;
		    sprite=buffered_spriteram.READ_WORD(offs+6);
		    colour=sprite>>12;
		    sprite=sprite&0xfff;
		    x=buffered_spriteram.READ_WORD(offs+4)&0x1ff;
	
                    fx=buffered_spriteram.READ_WORD(offs+2);
		    if ((fx&0x10)!=0) extra=1; else extra=0;
			fy=fx&0x2;
			fx=fx&0x4;
	
			if (extra != 0) y=y+16;
	
		    /* Convert the co-ords..*/
			x=(x+16)%0x200;
			y=(y+16)%0x200;
			x=256 - x;
			y=256 - y;
	
			/* Y Flip determines order of multi-sprite */
			if (extra!=0 && fy!=0) {
				sprite2=sprite;
				sprite++;
			}
			else sprite2=sprite+1;
	
			drawgfx(bitmap,Machine.gfx[2],
					sprite,
					colour,fx,fy,x,y,
					null,TRANSPARENCY_PEN,0);
	
	    	/* 1 more sprite drawn underneath */
	    	if (extra != 0)
	    		drawgfx(bitmap,Machine.gfx[2],
					sprite2,
					colour,fx,fy,x,y+16,
					null,TRANSPARENCY_PEN,0);
		}
	
		/* Draw character tiles */
		for (offs = videoram_size[0] - 2;offs >= 0;offs -= 2) {
			tile=videoram.READ_WORD(offs);
			if (tile==0) continue;
			color=tile>>14;
			tile=tile&0xfff;
			mx = (offs/2) % 32;
			my = (offs/2) / 32;
			drawgfx(bitmap,Machine.gfx[0],
				tile,color,0,0,8*mx,8*my,
				Machine.visible_area,TRANSPARENCY_PEN,0);
		}
	} };
	
	public static VhUpdatePtr wndrplnt_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		int my,mx,offs,color,tile;
		int scrollx=karnov_scroll.READ_WORD(0);
		int scrolly=karnov_scroll.READ_WORD(2);
	
		/* 1st area is stored along X-axis... */
		mx=-1; my=0;
		for (offs = 0;offs < 0x800; offs += 2) {
			mx++;
			if (mx==32) {mx=0; my++;}
	
			if (dirty_f.read(offs)==0) continue; else dirty_f.write(offs, 0);
	
			tile=karnov_foreground.READ_WORD (offs);
			color = tile >> 12;
			tile = tile&0x7ff;
	
			drawgfx(bitmap_f,Machine.gfx[1],tile,
				color, 0,0, 16*mx,16*my,
			 	null,TRANSPARENCY_NONE,0);
		}
	
		/* 2nd area is stored along Y-axis... */
		mx=0; my=-1;
		for (offs = 0x800 ;offs < 0x1000; offs += 2) {
			my++;
			if (my==32) {my=0; mx++;}
	
			if (dirty_f.read(offs)==0) continue; else dirty_f.write(offs, 0);
	
			tile=karnov_foreground.READ_WORD(offs);
			color = tile >> 12;
			tile=tile&0x7ff;
	
			drawgfx(bitmap_f,Machine.gfx[1],tile,
				color, 0,0, 16*mx,16*my,
			 	null,TRANSPARENCY_NONE,0);
		}
	
		scrolly=-scrolly;
		scrollx=-scrollx;
		copyscrollbitmap(bitmap,bitmap_f,1,new int[]{scrollx},1,new int[]{scrolly},null,TRANSPARENCY_NONE,0);
	
		/* Sprites */
		for (offs = 0;offs <0x800;offs += 8) {
			int x,y,sprite,sprite2,colour,fx,fy,extra;
	
		    y=buffered_spriteram.READ_WORD(offs);
		    if ((y&0x8000)==0) continue;
	
		    y=y&0x1ff;
		    sprite=buffered_spriteram.READ_WORD(offs+6);
		    colour=sprite>>12;
		    sprite=sprite&0xfff;
		    x=buffered_spriteram.READ_WORD(offs+4)&0x1ff;
	
                    fx=buffered_spriteram.READ_WORD(offs+2);
		    if ((fx&0x10)!=0) extra=1; else extra=0;
			fy=fx&0x2;
			fx=fx&0x4;
	
			if (extra != 0) y=y+16;
	
		    /* Convert the co-ords..*/
			x=(x+16)%0x200;
			y=(y+16)%0x200;
			x=256 - x;
			y=256 - y;
	
			/* Y Flip determines order of multi-sprite */
			if (extra!=0 && fy!=0) {
				sprite2=sprite;
				sprite++;
			}
			else sprite2=sprite+1;
	
			drawgfx(bitmap,Machine.gfx[2],
					sprite,
					colour,fx,fy,x,y,
					null,TRANSPARENCY_PEN,0);
	
	    	/* 1 more sprite drawn underneath */
	    	if (extra != 0)
	    		drawgfx(bitmap,Machine.gfx[2],
					sprite2,
					colour,fx,fy,x,y+16,
					null,TRANSPARENCY_PEN,0);
		}
	
		/* Draw character tiles */
		for (offs = videoram_size[0] - 2;offs >= 0;offs -= 2) {
			tile=videoram.READ_WORD(offs);
			if (tile==0) continue;
			color=tile>>14;
			tile=tile&0xfff;
			my = (offs/2) % 32;
			mx = (offs/2) / 32;
			drawgfx(bitmap,Machine.gfx[0],
				tile,color,0,0,8*mx,8*my,
				Machine.visible_area,TRANSPARENCY_PEN,0);
		}
	} };
	
	/******************************************************************************/
	
	public static WriteHandlerPtr karnov_foreground_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		COMBINE_WORD_MEM(karnov_foreground,offset,data);
		dirty_f.write(offset, 1);
	} };
	
	/******************************************************************************/
	
	public static VhStopPtr karnov_vh_stop = new VhStopPtr() { public void handler() 
	{
		if (dirty_f != null) dirty_f=null;
		if (karnov_foreground != null) karnov_foreground=null;
		if (bitmap_f != null) bitmap_free (bitmap_f);
	} };
	
	public static VhStartPtr karnov_vh_start = new VhStartPtr() { public int handler() 
	{
		/* Allocate bitmaps */
		if ((bitmap_f = bitmap_alloc(512,512)) == null) {
			karnov_vh_stop.handler();
			return 1;
		}
	
		dirty_f=new UBytePtr(0x1000);
		karnov_foreground=new UBytePtr(0x1000);
		memset(karnov_foreground,0,0x1000);
		memset(dirty_f,1,0x1000);
	
		return 0;
	} };
	
	/******************************************************************************/
}
