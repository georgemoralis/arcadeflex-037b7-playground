/*
 * ported to v0.37b7
 */
package arcadeflex.v037b7.vidhrdw;

//common imports
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.common.subArrays.*;
import static arcadeflex.common.libc.expressions.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.paletteH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.mameH.*;
import static arcadeflex.v037b7.mame.osdependH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.common.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.copyrozbitmap;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.pdrawgfx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_transparent_pen;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_used_colors;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.tilemap_mark_all_tiles_dirty;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.ALL_TILEMAPS;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_ctrlram;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_offset;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_tilemap;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_wraparound;
import static gr.codebb.arcadeflex.common.libc.cstring.memset;
import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.sizeof;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.old.mame.drawgfx.decodegfx;
import static arcadeflex.v037b7.mame.drawgfx.pdrawgfxzoom;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K052109_RMRD_line;

public class konamiic {

    /*TODO*///#define VERBOSE 0
/*TODO*///
/*TODO*///static void shuffle(UINT16 *buf,int len)
/*TODO*///{
/*TODO*///	int i;
/*TODO*///	UINT16 t;
/*TODO*///
/*TODO*///	if (len == 2) return;
/*TODO*///
/*TODO*///	if (len % 4) exit(1);   /* must not happen */
/*TODO*///
/*TODO*///	len /= 2;
/*TODO*///
/*TODO*///	for (i = 0;i < len/2;i++)
/*TODO*///	{
/*TODO*///		t = buf[len/2 + i];
/*TODO*///		buf[len/2 + i] = buf[len + i];
/*TODO*///		buf[len + i] = t;
/*TODO*///	}
/*TODO*///
/*TODO*///	shuffle(buf,len);
/*TODO*///	shuffle(buf + len,len);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////* helper function to join two 16-bit ROMs and form a 32-bit data stream */
/*TODO*///void konami_rom_deinterleave_2(int mem_region)
/*TODO*///{
/*TODO*///	shuffle((UINT16 *)memory_region(mem_region),memory_region_length(mem_region)/2);
/*TODO*///}
/*TODO*///
/*TODO*////* helper function to join four 16-bit ROMs and form a 64-bit data stream */
/*TODO*///void konami_rom_deinterleave_4(int mem_region)
/*TODO*///{
/*TODO*///	konami_rom_deinterleave_2(mem_region);
/*TODO*///	konami_rom_deinterleave_2(mem_region);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////*#define MAX_K007121 2*/
/*TODO*///
/*TODO*////*static*/ unsigned char K007121_ctrlram[MAX_K007121][8];
/*TODO*///static int K007121_flipscreen[MAX_K007121];
/*TODO*///
/*TODO*///
/*TODO*///void K007121_ctrl_w(int chip,int offset,int data)
/*TODO*///{
/*TODO*///	switch (offset)
/*TODO*///	{
/*TODO*///		case 6:
/*TODO*////* palette bank change */
/*TODO*///if ((K007121_ctrlram[chip][offset] & 0x30) != (data & 0x30))
/*TODO*///	tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
/*TODO*///			break;
/*TODO*///		case 7:
/*TODO*///			K007121_flipscreen[chip] = data & 0x08;
/*TODO*///			break;
/*TODO*///	}
/*TODO*///
/*TODO*///	K007121_ctrlram[chip][offset] = data;
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K007121_ctrl_0_w )
/*TODO*///{
/*TODO*///	K007121_ctrl_w(0,offset,data);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K007121_ctrl_1_w )
/*TODO*///{
/*TODO*///	K007121_ctrl_w(1,offset,data);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////*
/*TODO*/// * Sprite Format
/*TODO*/// * ------------------
/*TODO*/// *
/*TODO*/// * There are 0x40 sprites, each one using 5 bytes. However the number of
/*TODO*/// * sprites can be increased to 0x80 with a control register (Combat School
/*TODO*/// * sets it on and off during the game).
/*TODO*/// *
/*TODO*/// * Byte | Bit(s)   | Use
/*TODO*/// * -----+-76543210-+----------------
/*TODO*/// *   0  | xxxxxxxx | sprite code
/*TODO*/// *   1  | xxxx---- | color
/*TODO*/// *   1  | ----xx-- | sprite code low 2 bits for 16x8/8x8 sprites
/*TODO*/// *   1  | ------xx | sprite code bank bits 1/0
/*TODO*/// *   2  | xxxxxxxx | y position
/*TODO*/// *   3  | xxxxxxxx | x position (low 8 bits)
/*TODO*/// *   4  | xx------ | sprite code bank bits 3/2
/*TODO*/// *   4  | --x----- | flip y
/*TODO*/// *   4  | ---x---- | flip x
/*TODO*/// *   4  | ----xxx- | sprite size 000=16x16 001=16x8 010=8x16 011=8x8 100=32x32
/*TODO*/// *   4  | -------x | x position (high bit)
/*TODO*/// *
/*TODO*/// * Flack Attack uses a different, "wider" layout with 32 bytes per sprites,
/*TODO*/// * mapped as follows, and the priority order is reversed. Maybe it is a
/*TODO*/// * compatibility mode with an older custom IC. It is not known how this
/*TODO*/// * alternate layout is selected.
/*TODO*/// *
/*TODO*/// * 0 -> e
/*TODO*/// * 1 -> f
/*TODO*/// * 2 -> 6
/*TODO*/// * 3 -> 4
/*TODO*/// * 4 -> 8
/*TODO*/// *
/*TODO*/// */
/*TODO*///
/*TODO*///void K007121_sprites_draw(int chip,struct osd_bitmap *bitmap,
/*TODO*///		const unsigned char *source,int base_color,int global_x_offset,int bank_base,
/*TODO*///		UINT32 pri_mask)
/*TODO*///{
/*TODO*///	const struct GfxElement *gfx = Machine->gfx[chip];
/*TODO*///	int flipscreen = K007121_flipscreen[chip];
/*TODO*///	int i,num,inc,offs[5],trans;
/*TODO*///	int is_flakatck = K007121_ctrlram[chip][0x06] & 0x04;	/* WRONG!!!! */
/*TODO*///
/*TODO*///#if 0
/*TODO*///usrintf_showmessage("%02x-%02x-%02x-%02x-%02x-%02x-%02x-%02x  %02x-%02x-%02x-%02x-%02x-%02x-%02x-%02x",
/*TODO*///	K007121_ctrlram[0][0x00],K007121_ctrlram[0][0x01],K007121_ctrlram[0][0x02],K007121_ctrlram[0][0x03],K007121_ctrlram[0][0x04],K007121_ctrlram[0][0x05],K007121_ctrlram[0][0x06],K007121_ctrlram[0][0x07],
/*TODO*///	K007121_ctrlram[1][0x00],K007121_ctrlram[1][0x01],K007121_ctrlram[1][0x02],K007121_ctrlram[1][0x03],K007121_ctrlram[1][0x04],K007121_ctrlram[1][0x05],K007121_ctrlram[1][0x06],K007121_ctrlram[1][0x07]);
/*TODO*///#endif
/*TODO*///#if 0
/*TODO*///if (keyboard_pressed(KEYCODE_D))
/*TODO*///{
/*TODO*///	FILE *fp;
/*TODO*///	fp=fopen(chip?"SPRITE1.DMP":"SPRITE0.DMP", "w+b");
/*TODO*///	if (fp)
/*TODO*///	{
/*TODO*///		fwrite(source, 0x800, 1, fp);
/*TODO*///		usrintf_showmessage("saved");
/*TODO*///		fclose(fp);
/*TODO*///	}
/*TODO*///}
/*TODO*///#endif
/*TODO*///
/*TODO*///	if (is_flakatck)
/*TODO*///	{
/*TODO*///		num = 0x40;
/*TODO*///		inc = -0x20;
/*TODO*///		source += 0x3f*0x20;
/*TODO*///		offs[0] = 0x0e;
/*TODO*///		offs[1] = 0x0f;
/*TODO*///		offs[2] = 0x06;
/*TODO*///		offs[3] = 0x04;
/*TODO*///		offs[4] = 0x08;
/*TODO*///		/* Flak Attack doesn't use a lookup PROM, it maps the color code directly */
/*TODO*///		/* to a palette entry */
/*TODO*///		trans = TRANSPARENCY_PEN;
/*TODO*///	}
/*TODO*///	else	/* all others */
/*TODO*///	{
/*TODO*///		num = (K007121_ctrlram[chip][0x03] & 0x40) ? 0x80 : 0x40;	/* WRONG!!! (needed by combasc)  */
/*TODO*///		inc = 5;
/*TODO*///		offs[0] = 0x00;
/*TODO*///		offs[1] = 0x01;
/*TODO*///		offs[2] = 0x02;
/*TODO*///		offs[3] = 0x03;
/*TODO*///		offs[4] = 0x04;
/*TODO*///		trans = TRANSPARENCY_COLOR;
/*TODO*///		/* when using priority buffer, draw front to back */
/*TODO*///		if (pri_mask != -1)
/*TODO*///		{
/*TODO*///			source += (num-1)*inc;
/*TODO*///			inc = -inc;
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	for (i = 0;i < num;i++)
/*TODO*///	{
/*TODO*///		int number = source[offs[0]];				/* sprite number */
/*TODO*///		int sprite_bank = source[offs[1]] & 0x0f;	/* sprite bank */
/*TODO*///		int sx = source[offs[3]];					/* vertical position */
/*TODO*///		int sy = source[offs[2]];					/* horizontal position */
/*TODO*///		int attr = source[offs[4]];				/* attributes */
/*TODO*///		int xflip = source[offs[4]] & 0x10;		/* flip x */
/*TODO*///		int yflip = source[offs[4]] & 0x20;		/* flip y */
/*TODO*///		int color = base_color + ((source[offs[1]] & 0xf0) >> 4);
/*TODO*///		int width,height;
/*TODO*///		static int x_offset[4] = {0x0,0x1,0x4,0x5};
/*TODO*///		static int y_offset[4] = {0x0,0x2,0x8,0xa};
/*TODO*///		int x,y, ex, ey;
/*TODO*///
/*TODO*///		if (attr & 0x01) sx -= 256;
/*TODO*///		if (sy >= 240) sy -= 256;
/*TODO*///
/*TODO*///		number += ((sprite_bank & 0x3) << 8) + ((attr & 0xc0) << 4);
/*TODO*///		number = number << 2;
/*TODO*///		number += (sprite_bank >> 2) & 3;
/*TODO*///
/*TODO*///		if (!is_flakatck || source[0x00])	/* Flak Attack needs this */
/*TODO*///		{
/*TODO*///			number += bank_base;
/*TODO*///
/*TODO*///			switch( attr&0xe )
/*TODO*///			{
/*TODO*///				case 0x06: width = height = 1; break;
/*TODO*///				case 0x04: width = 1; height = 2; number &= (~2); break;
/*TODO*///				case 0x02: width = 2; height = 1; number &= (~1); break;
/*TODO*///				case 0x00: width = height = 2; number &= (~3); break;
/*TODO*///				case 0x08: width = height = 4; number &= (~3); break;
/*TODO*///				default: width = 1; height = 1;
/*TODO*/////					logerror("Unknown sprite size %02x\n",attr&0xe);
/*TODO*/////					usrintf_showmessage("Unknown sprite size %02x\n",attr&0xe);
/*TODO*///			}
/*TODO*///
/*TODO*///			for (y = 0;y < height;y++)
/*TODO*///			{
/*TODO*///				for (x = 0;x < width;x++)
/*TODO*///				{
/*TODO*///					ex = xflip ? (width-1-x) : x;
/*TODO*///					ey = yflip ? (height-1-y) : y;
/*TODO*///
/*TODO*///					if (flipscreen)
/*TODO*///					{
/*TODO*///						if (pri_mask != -1)
/*TODO*///							pdrawgfx(bitmap,gfx,
/*TODO*///								number + x_offset[ex] + y_offset[ey],
/*TODO*///								color,
/*TODO*///								!xflip,!yflip,
/*TODO*///								248-(sx+x*8),248-(sy+y*8),
/*TODO*///								&Machine->visible_area,trans,0,
/*TODO*///								pri_mask);
/*TODO*///						else
/*TODO*///							drawgfx(bitmap,gfx,
/*TODO*///								number + x_offset[ex] + y_offset[ey],
/*TODO*///								color,
/*TODO*///								!xflip,!yflip,
/*TODO*///								248-(sx+x*8),248-(sy+y*8),
/*TODO*///								&Machine->visible_area,trans,0);
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						if (pri_mask != -1)
/*TODO*///							pdrawgfx(bitmap,gfx,
/*TODO*///								number + x_offset[ex] + y_offset[ey],
/*TODO*///								color,
/*TODO*///								xflip,yflip,
/*TODO*///								global_x_offset+sx+x*8,sy+y*8,
/*TODO*///								&Machine->visible_area,trans,0,
/*TODO*///								pri_mask);
/*TODO*///						else
/*TODO*///							drawgfx(bitmap,gfx,
/*TODO*///								number + x_offset[ex] + y_offset[ey],
/*TODO*///								color,
/*TODO*///								xflip,yflip,
/*TODO*///								global_x_offset+sx+x*8,sy+y*8,
/*TODO*///								&Machine->visible_area,trans,0);
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///
/*TODO*///		source += inc;
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///void K007121_mark_sprites_colors(int chip,
/*TODO*///		const unsigned char *source,int base_color,int bank_base)
/*TODO*///{
/*TODO*///	int i,num,inc,offs[5];
/*TODO*///	int is_flakatck = K007121_ctrlram[chip][0x06] & 0x04;	/* WRONG!!!! */
/*TODO*///
/*TODO*///	unsigned short palette_map[512];
/*TODO*///
/*TODO*///	if (is_flakatck)
/*TODO*///	{
/*TODO*///		num = 0x40;
/*TODO*///		inc = -0x20;
/*TODO*///		source += 0x3f*0x20;
/*TODO*///		offs[0] = 0x0e;
/*TODO*///		offs[1] = 0x0f;
/*TODO*///		offs[2] = 0x06;
/*TODO*///		offs[3] = 0x04;
/*TODO*///		offs[4] = 0x08;
/*TODO*///	}
/*TODO*///	else	/* all others */
/*TODO*///	{
/*TODO*///		num = (K007121_ctrlram[chip][0x03] & 0x40) ? 0x80 : 0x40;
/*TODO*///		inc = 5;
/*TODO*///		offs[0] = 0x00;
/*TODO*///		offs[1] = 0x01;
/*TODO*///		offs[2] = 0x02;
/*TODO*///		offs[3] = 0x03;
/*TODO*///		offs[4] = 0x04;
/*TODO*///	}
/*TODO*///
/*TODO*///	memset (palette_map, 0, sizeof (palette_map));
/*TODO*///
/*TODO*///	/* sprites */
/*TODO*///	for (i = 0;i < num;i++)
/*TODO*///	{
/*TODO*///		int color;
/*TODO*///
/*TODO*///		color = base_color + ((source[offs[1]] & 0xf0) >> 4);
/*TODO*///		palette_map[color] |= 0xffff;
/*TODO*///
/*TODO*///		source += inc;
/*TODO*///	}
/*TODO*///
/*TODO*///	/* now build the final table */
/*TODO*///	for (i = 0; i < 512; i++)
/*TODO*///	{
/*TODO*///		int usage = palette_map[i], j;
/*TODO*///		if (usage)
/*TODO*///		{
/*TODO*///			for (j = 0; j < 16; j++)
/*TODO*///				if (usage & (1 << j))
/*TODO*///					palette_used_colors[i * 16 + j] |= PALETTE_COLOR_VISIBLE;
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///static unsigned char *K007342_ram,*K007342_scroll_ram;
/*TODO*///static int K007342_gfxnum;
/*TODO*///static int K007342_int_enabled;
/*TODO*///static int K007342_flipscreen;
/*TODO*///static int K007342_scrollx[2];
/*TODO*///static int K007342_scrolly[2];
/*TODO*///static unsigned char *K007342_videoram_0,*K007342_colorram_0;
/*TODO*///static unsigned char *K007342_videoram_1,*K007342_colorram_1;
/*TODO*///static int K007342_regs[8];
/*TODO*///static void (*K007342_callback)(int tilemap, int bank, int *code, int *color);
/*TODO*///static struct tilemap *K007342_tilemap[2];
/*TODO*///
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///  Callbacks for the TileMap code
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*////*
/*TODO*///  data format:
/*TODO*///  video RAM     xxxxxxxx    tile number (bits 0-7)
/*TODO*///  color RAM     x-------    tiles with priority over the sprites
/*TODO*///  color RAM     -x------    depends on external conections
/*TODO*///  color RAM     --x-----    flip Y
/*TODO*///  color RAM     ---x----    flip X
/*TODO*///  color RAM     ----xxxx    depends on external connections (usually color and banking)
/*TODO*///*/
/*TODO*///
/*TODO*///static unsigned char *colorram,*videoram1,*videoram2;
/*TODO*///static int layer;
/*TODO*///
/*TODO*///static void tilemap_0_preupdate(void)
/*TODO*///{
/*TODO*///	colorram = K007342_colorram_0;
/*TODO*///	videoram1 = K007342_videoram_0;
/*TODO*///	layer = 0;
/*TODO*///}
/*TODO*///
/*TODO*///static void tilemap_1_preupdate(void)
/*TODO*///{
/*TODO*///	colorram = K007342_colorram_1;
/*TODO*///	videoram1 = K007342_videoram_1;
/*TODO*///	layer = 1;
/*TODO*///}
/*TODO*///
/*TODO*///static UINT32 K007342_scan(UINT32 col,UINT32 row,UINT32 num_cols,UINT32 num_rows)
/*TODO*///{
/*TODO*///	/* logical (col,row) -> memory offset */
/*TODO*///	return (col & 0x1f) + ((row & 0x1f) << 5) + ((col & 0x20) << 5);
/*TODO*///}
/*TODO*///
/*TODO*///static void K007342_get_tile_info(int tile_index)
/*TODO*///{
/*TODO*///	int color, code;
/*TODO*///
/*TODO*///	color = colorram[tile_index];
/*TODO*///	code = videoram1[tile_index];
/*TODO*///
/*TODO*///	tile_info.flags = TILE_FLIPYX((color & 0x30) >> 4);
/*TODO*///	tile_info.priority = (color & 0x80) >> 7;
/*TODO*///
/*TODO*///	(*K007342_callback)(layer, K007342_regs[1], &code, &color);
/*TODO*///
/*TODO*///	SET_TILE_INFO(K007342_gfxnum,code,color);
/*TODO*///}
/*TODO*///
/*TODO*///int K007342_vh_start(int gfx_index, void (*callback)(int tilemap, int bank, int *code, int *color))
/*TODO*///{
/*TODO*///	K007342_gfxnum = gfx_index;
/*TODO*///	K007342_callback = callback;
/*TODO*///
/*TODO*///	K007342_tilemap[0] = tilemap_create(K007342_get_tile_info,K007342_scan,TILEMAP_TRANSPARENT,8,8,64,32);
/*TODO*///	K007342_tilemap[1] = tilemap_create(K007342_get_tile_info,K007342_scan,TILEMAP_TRANSPARENT,8,8,64,32);
/*TODO*///
/*TODO*///	K007342_ram = malloc(0x2000);
/*TODO*///	K007342_scroll_ram = malloc(0x0200);
/*TODO*///
/*TODO*///	if (!K007342_ram || !K007342_scroll_ram || !K007342_tilemap[0] || !K007342_tilemap[1])
/*TODO*///	{
/*TODO*///		K007342_vh_stop();
/*TODO*///		return 1;
/*TODO*///	}
/*TODO*///
/*TODO*///	memset(K007342_ram,0,0x2000);
/*TODO*///
/*TODO*///	K007342_colorram_0 = &K007342_ram[0x0000];
/*TODO*///	K007342_colorram_1 = &K007342_ram[0x1000];
/*TODO*///	K007342_videoram_0 = &K007342_ram[0x0800];
/*TODO*///	K007342_videoram_1 = &K007342_ram[0x1800];
/*TODO*///
/*TODO*///	K007342_tilemap[0]->transparent_pen = 0;
/*TODO*///	K007342_tilemap[1]->transparent_pen = 0;
/*TODO*///
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
/*TODO*///void K007342_vh_stop(void)
/*TODO*///{
/*TODO*///	free(K007342_ram);
/*TODO*///	K007342_ram = 0;
/*TODO*///	free(K007342_scroll_ram);
/*TODO*///	K007342_scroll_ram = 0;
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K007342_r )
/*TODO*///{
/*TODO*///	return K007342_ram[offset];
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K007342_w )
/*TODO*///{
/*TODO*///	if (offset < 0x1000)
/*TODO*///	{		/* layer 0 */
/*TODO*///		if (K007342_ram[offset] != data)
/*TODO*///		{
/*TODO*///			K007342_ram[offset] = data;
/*TODO*///			tilemap_mark_tile_dirty(K007342_tilemap[0],offset & 0x7ff);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{						/* layer 1 */
/*TODO*///		if (K007342_ram[offset] != data)
/*TODO*///		{
/*TODO*///			K007342_ram[offset] = data;
/*TODO*///			tilemap_mark_tile_dirty(K007342_tilemap[1],offset & 0x7ff);
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K007342_scroll_r )
/*TODO*///{
/*TODO*///	return K007342_scroll_ram[offset];
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K007342_scroll_w )
/*TODO*///{
/*TODO*///	K007342_scroll_ram[offset] = data;
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K007342_vreg_w )
/*TODO*///{
/*TODO*///	switch(offset)
/*TODO*///	{
/*TODO*///		case 0x00:
/*TODO*///			/* bit 1: INT control */
/*TODO*///			K007342_int_enabled = data & 0x02;
/*TODO*///			K007342_flipscreen = data & 0x10;
/*TODO*///			tilemap_set_flip(K007342_tilemap[0],K007342_flipscreen ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
/*TODO*///			tilemap_set_flip(K007342_tilemap[1],K007342_flipscreen ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
/*TODO*///			break;
/*TODO*///		case 0x01:  /* used for banking in Rock'n'Rage */
/*TODO*///			if (data != K007342_regs[1])
/*TODO*///				tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
/*TODO*///		case 0x02:
/*TODO*///			K007342_scrollx[0] = (K007342_scrollx[0] & 0xff) | ((data & 0x01) << 8);
/*TODO*///			K007342_scrollx[1] = (K007342_scrollx[1] & 0xff) | ((data & 0x02) << 7);
/*TODO*///			break;
/*TODO*///		case 0x03:  /* scroll x (register 0) */
/*TODO*///			K007342_scrollx[0] = (K007342_scrollx[0] & 0x100) | data;
/*TODO*///			break;
/*TODO*///		case 0x04:  /* scroll y (register 0) */
/*TODO*///			K007342_scrolly[0] = data;
/*TODO*///			break;
/*TODO*///		case 0x05:  /* scroll x (register 1) */
/*TODO*///			K007342_scrollx[1] = (K007342_scrollx[1] & 0x100) | data;
/*TODO*///			break;
/*TODO*///		case 0x06:  /* scroll y (register 1) */
/*TODO*///			K007342_scrolly[1] = data;
/*TODO*///		case 0x07:  /* unused */
/*TODO*///			break;
/*TODO*///	}
/*TODO*///	K007342_regs[offset] = data;
/*TODO*///}
/*TODO*///
/*TODO*///void K007342_tilemap_update(void)
/*TODO*///{
/*TODO*///	int offs;
/*TODO*///
/*TODO*///
/*TODO*///	/* update scroll */
/*TODO*///	switch (K007342_regs[2] & 0x1c)
/*TODO*///	{
/*TODO*///		case 0x00:
/*TODO*///		case 0x08:	/* unknown, blades of steel shootout between periods */
/*TODO*///			tilemap_set_scroll_rows(K007342_tilemap[0],1);
/*TODO*///			tilemap_set_scroll_cols(K007342_tilemap[0],1);
/*TODO*///			tilemap_set_scrollx(K007342_tilemap[0],0,K007342_scrollx[0]);
/*TODO*///			tilemap_set_scrolly(K007342_tilemap[0],0,K007342_scrolly[0]);
/*TODO*///			break;
/*TODO*///
/*TODO*///		case 0x0c:	/* 32 columns */
/*TODO*///			tilemap_set_scroll_rows(K007342_tilemap[0],1);
/*TODO*///			tilemap_set_scroll_cols(K007342_tilemap[0],512);
/*TODO*///			tilemap_set_scrollx(K007342_tilemap[0],0,K007342_scrollx[0]);
/*TODO*///			for (offs = 0;offs < 256;offs++)
/*TODO*///				tilemap_set_scrolly(K007342_tilemap[0],(offs + K007342_scrollx[0]) & 0x1ff,
/*TODO*///						K007342_scroll_ram[2*(offs/8)] + 256 * K007342_scroll_ram[2*(offs/8)+1]);
/*TODO*///			break;
/*TODO*///
/*TODO*///		case 0x14:	/* 256 rows */
/*TODO*///			tilemap_set_scroll_rows(K007342_tilemap[0],256);
/*TODO*///			tilemap_set_scroll_cols(K007342_tilemap[0],1);
/*TODO*///			tilemap_set_scrolly(K007342_tilemap[0],0,K007342_scrolly[0]);
/*TODO*///			for (offs = 0;offs < 256;offs++)
/*TODO*///				tilemap_set_scrollx(K007342_tilemap[0],(offs + K007342_scrolly[0]) & 0xff,
/*TODO*///						K007342_scroll_ram[2*offs] + 256 * K007342_scroll_ram[2*offs+1]);
/*TODO*///			break;
/*TODO*///
/*TODO*///		default:
/*TODO*///usrintf_showmessage("unknown scroll ctrl %02x",K007342_regs[2] & 0x1c);
/*TODO*///			break;
/*TODO*///	}
/*TODO*///
/*TODO*///	tilemap_set_scrollx(K007342_tilemap[1],0,K007342_scrollx[1]);
/*TODO*///	tilemap_set_scrolly(K007342_tilemap[1],0,K007342_scrolly[1]);
/*TODO*///
/*TODO*///	/* update all layers */
/*TODO*///	tilemap_0_preupdate(); tilemap_update(K007342_tilemap[0]);
/*TODO*///	tilemap_1_preupdate(); tilemap_update(K007342_tilemap[1]);
/*TODO*///
/*TODO*///#if 0
/*TODO*///	{
/*TODO*///		static int current_layer = 0;
/*TODO*///
/*TODO*///		if (keyboard_pressed_memory(KEYCODE_Z)) current_layer = !current_layer;
/*TODO*///		tilemap_set_enable(K007342_tilemap[current_layer], 1);
/*TODO*///		tilemap_set_enable(K007342_tilemap[!current_layer], 0);
/*TODO*///
/*TODO*///		usrintf_showmessage("regs:%02x %02x %02x %02x-%02x %02x %02x %02x:%02x",
/*TODO*///			K007342_regs[0], K007342_regs[1], K007342_regs[2], K007342_regs[3],
/*TODO*///			K007342_regs[4], K007342_regs[5], K007342_regs[6], K007342_regs[7],
/*TODO*///			current_layer);
/*TODO*///	}
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///void K007342_tilemap_set_enable(int tilemap, int enable)
/*TODO*///{
/*TODO*///	tilemap_set_enable(K007342_tilemap[tilemap], enable);
/*TODO*///}
/*TODO*///
/*TODO*///void K007342_tilemap_draw(struct osd_bitmap *bitmap,int num,int flags)
/*TODO*///{
/*TODO*///	tilemap_draw(bitmap,K007342_tilemap[num],flags);
/*TODO*///}
/*TODO*///
/*TODO*///int K007342_is_INT_enabled(void)
/*TODO*///{
/*TODO*///	return K007342_int_enabled;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///static struct GfxElement *K007420_gfx;
/*TODO*///static void (*K007420_callback)(int *code,int *color);
/*TODO*///static unsigned char *K007420_ram;
/*TODO*///
/*TODO*///int K007420_vh_start(int gfxnum, void (*callback)(int *code,int *color))
/*TODO*///{
/*TODO*///	K007420_gfx = Machine->gfx[gfxnum];
/*TODO*///	K007420_callback = callback;
/*TODO*///	K007420_ram = malloc(0x200);
/*TODO*///	if (!K007420_ram) return 1;
/*TODO*///
/*TODO*///	memset(K007420_ram,0,0x200);
/*TODO*///
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
/*TODO*///void K007420_vh_stop(void)
/*TODO*///{
/*TODO*///	free(K007420_ram);
/*TODO*///	K007420_ram = 0;
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K007420_r )
/*TODO*///{
/*TODO*///	return K007420_ram[offset];
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K007420_w )
/*TODO*///{
/*TODO*///	K007420_ram[offset] = data;
/*TODO*///}
/*TODO*///
/*TODO*////*
/*TODO*/// * Sprite Format
/*TODO*/// * ------------------
/*TODO*/// *
/*TODO*/// * Byte | Bit(s)   | Use
/*TODO*/// * -----+-76543210-+----------------
/*TODO*/// *   0  | xxxxxxxx | y position
/*TODO*/// *   1  | xxxxxxxx | sprite code (low 8 bits)
/*TODO*/// *   2  | xxxxxxxx | depends on external conections. Usually banking
/*TODO*/// *   3  | xxxxxxxx | x position (low 8 bits)
/*TODO*/// *   4  | x------- | x position (high bit)
/*TODO*/// *   4  | -xxx---- | sprite size 000=16x16 001=8x16 010=16x8 011=8x8 100=32x32
/*TODO*/// *   4  | ----x--- | flip y
/*TODO*/// *   4  | -----x-- | flip x
/*TODO*/// *   4  | ------xx | zoom (bits 8 & 9)
/*TODO*/// *   5  | xxxxxxxx | zoom (low 8 bits)  0x080 = normal, < 0x80 enlarge, > 0x80 reduce
/*TODO*/// *   6  | xxxxxxxx | unused
/*TODO*/// *   7  | xxxxxxxx | unused
/*TODO*/// */
/*TODO*///
/*TODO*///void K007420_sprites_draw(struct osd_bitmap *bitmap)
/*TODO*///{
/*TODO*///#define K007420_SPRITERAM_SIZE 0x200
/*TODO*///	int offs;
/*TODO*///
/*TODO*///	for (offs = K007420_SPRITERAM_SIZE - 8; offs >= 0; offs -= 8)
/*TODO*///	{
/*TODO*///		int ox,oy,code,color,flipx,flipy,zoom,w,h,x,y;
/*TODO*///		static int xoffset[4] = { 0, 1, 4, 5 };
/*TODO*///		static int yoffset[4] = { 0, 2, 8, 10 };
/*TODO*///
/*TODO*///		code = K007420_ram[offs+1];
/*TODO*///		color = K007420_ram[offs+2];
/*TODO*///		ox = K007420_ram[offs+3] - ((K007420_ram[offs+4] & 0x80) << 1);
/*TODO*///		oy = 256 - K007420_ram[offs+0];
/*TODO*///		flipx = K007420_ram[offs+4] & 0x04;
/*TODO*///		flipy = K007420_ram[offs+4] & 0x08;
/*TODO*///
/*TODO*///		(*K007420_callback)(&code,&color);
/*TODO*///
/*TODO*///		/* kludge for rock'n'rage */
/*TODO*///		if ((K007420_ram[offs+4] == 0x40) && (K007420_ram[offs+1] == 0xff) &&
/*TODO*///			(K007420_ram[offs+2] == 0x00) && (K007420_ram[offs+5] == 0xf0)) continue;
/*TODO*///
/*TODO*///		/* 0x080 = normal scale, 0x040 = double size, 0x100 half size */
/*TODO*///		zoom = K007420_ram[offs+5] | ((K007420_ram[offs+4] & 0x03) << 8);
/*TODO*///		if (!zoom) continue;
/*TODO*///		zoom = 0x10000 * 128 / zoom;
/*TODO*///
/*TODO*///		switch (K007420_ram[offs+4] & 0x70)
/*TODO*///		{
/*TODO*///			case 0x30: w = h = 1; break;
/*TODO*///			case 0x20: w = 2; h = 1; code &= (~1); break;
/*TODO*///			case 0x10: w = 1; h = 2; code &= (~2); break;
/*TODO*///			case 0x00: w = h = 2; code &= (~3); break;
/*TODO*///			case 0x40: w = h = 4; code &= (~3); break;
/*TODO*///			default: w = 1; h = 1;
/*TODO*/////logerror("Unknown sprite size %02x\n",(K007420_ram[offs+4] & 0x70)>>4);
/*TODO*///		}
/*TODO*///
/*TODO*///		if (K007342_flipscreen)
/*TODO*///		{
/*TODO*///			ox = 256 - ox - ((zoom * w + (1<<12)) >> 13);
/*TODO*///			oy = 256 - oy - ((zoom * h + (1<<12)) >> 13);
/*TODO*///			flipx = !flipx;
/*TODO*///			flipy = !flipy;
/*TODO*///		}
/*TODO*///
/*TODO*///		if (zoom == 0x10000)
/*TODO*///		{
/*TODO*///			int sx,sy;
/*TODO*///
/*TODO*///			for (y = 0;y < h;y++)
/*TODO*///			{
/*TODO*///				sy = oy + 8 * y;
/*TODO*///
/*TODO*///				for (x = 0;x < w;x++)
/*TODO*///				{
/*TODO*///					int c = code;
/*TODO*///
/*TODO*///					sx = ox + 8 * x;
/*TODO*///					if (flipx) c += xoffset[(w-1-x)];
/*TODO*///					else c += xoffset[x];
/*TODO*///					if (flipy) c += yoffset[(h-1-y)];
/*TODO*///					else c += yoffset[y];
/*TODO*///
/*TODO*///					drawgfx(bitmap,K007420_gfx,
/*TODO*///						c,
/*TODO*///						color,
/*TODO*///						flipx,flipy,
/*TODO*///						sx,sy,
/*TODO*///						&Machine->visible_area,TRANSPARENCY_PEN,0);
/*TODO*///
/*TODO*///					if (K007342_regs[2] & 0x80)
/*TODO*///						drawgfx(bitmap,K007420_gfx,
/*TODO*///							c,
/*TODO*///							color,
/*TODO*///							flipx,flipy,
/*TODO*///							sx,sy-256,
/*TODO*///							&Machine->visible_area,TRANSPARENCY_PEN,0);
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			int sx,sy,zw,zh;
/*TODO*///			for (y = 0;y < h;y++)
/*TODO*///			{
/*TODO*///				sy = oy + ((zoom * y + (1<<12)) >> 13);
/*TODO*///				zh = (oy + ((zoom * (y+1) + (1<<12)) >> 13)) - sy;
/*TODO*///
/*TODO*///				for (x = 0;x < w;x++)
/*TODO*///				{
/*TODO*///					int c = code;
/*TODO*///
/*TODO*///					sx = ox + ((zoom * x + (1<<12)) >> 13);
/*TODO*///					zw = (ox + ((zoom * (x+1) + (1<<12)) >> 13)) - sx;
/*TODO*///					if (flipx) c += xoffset[(w-1-x)];
/*TODO*///					else c += xoffset[x];
/*TODO*///					if (flipy) c += yoffset[(h-1-y)];
/*TODO*///					else c += yoffset[y];
/*TODO*///
/*TODO*///					drawgfxzoom(bitmap,K007420_gfx,
/*TODO*///						c,
/*TODO*///						color,
/*TODO*///						flipx,flipy,
/*TODO*///						sx,sy,
/*TODO*///						&Machine->visible_area,TRANSPARENCY_PEN,0,
/*TODO*///						(zw << 16) / 8,(zh << 16) / 8);
/*TODO*///
/*TODO*///					if (K007342_regs[2] & 0x80)
/*TODO*///						drawgfxzoom(bitmap,K007420_gfx,
/*TODO*///							c,
/*TODO*///							color,
/*TODO*///							flipx,flipy,
/*TODO*///							sx,sy-256,
/*TODO*///							&Machine->visible_area,TRANSPARENCY_PEN,0,
/*TODO*///							(zw << 16) / 8,(zh << 16) / 8);
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///#if 0
/*TODO*///	{
/*TODO*///		static int current_sprite = 0;
/*TODO*///
/*TODO*///		if (keyboard_pressed_memory(KEYCODE_Z)) current_sprite = (current_sprite+1) & ((K007420_SPRITERAM_SIZE/8)-1);
/*TODO*///		if (keyboard_pressed_memory(KEYCODE_X)) current_sprite = (current_sprite-1) & ((K007420_SPRITERAM_SIZE/8)-1);
/*TODO*///
/*TODO*///		usrintf_showmessage("%02x:%02x %02x %02x %02x %02x %02x %02x %02x", current_sprite,
/*TODO*///			K007420_ram[(current_sprite*8)+0], K007420_ram[(current_sprite*8)+1],
/*TODO*///			K007420_ram[(current_sprite*8)+2], K007420_ram[(current_sprite*8)+3],
/*TODO*///			K007420_ram[(current_sprite*8)+4], K007420_ram[(current_sprite*8)+5],
/*TODO*///			K007420_ram[(current_sprite*8)+6], K007420_ram[(current_sprite*8)+7]);
/*TODO*///	}
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///static int K052109_memory_region;
/*TODO*///static int K052109_gfxnum;
/*TODO*///static void (*K052109_callback)(int tilemap,int bank,int *code,int *color);
/*TODO*///static unsigned char *K052109_ram;
/*TODO*///static unsigned char *K052109_videoram_F,*K052109_videoram2_F,*K052109_colorram_F;
/*TODO*///static unsigned char *K052109_videoram_A,*K052109_videoram2_A,*K052109_colorram_A;
/*TODO*///static unsigned char *K052109_videoram_B,*K052109_videoram2_B,*K052109_colorram_B;
/*TODO*///static unsigned char K052109_charrombank[4];
/*TODO*///static int has_extra_video_ram;
/*TODO*///static int K052109_RMRD_line;
/*TODO*///static int K052109_tileflip_enable;
/*TODO*///static int K052109_irq_enabled;
/*TODO*///static unsigned char K052109_romsubbank,K052109_scrollctrl;
/*TODO*///static struct tilemap *K052109_tilemap[3];
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///  Callbacks for the TileMap code
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*////*
/*TODO*///  data format:
/*TODO*///  video RAM    xxxxxxxx  tile number (low 8 bits)
/*TODO*///  color RAM    xxxx----  depends on external connections (usually color and banking)
/*TODO*///  color RAM    ----xx--  bank select (0-3): these bits are replaced with the 2
/*TODO*///                         bottom bits of the bank register before being placed on
/*TODO*///                         the output pins. The other two bits of the bank register are
/*TODO*///                         placed on the CAB1 and CAB2 output pins.
/*TODO*///  color RAM    ------xx  depends on external connections (usually banking, flip)
/*TODO*///*/
/*TODO*///
/*TODO*///static void tilemap0_preupdate(void)
/*TODO*///{
/*TODO*///	colorram = K052109_colorram_F;
/*TODO*///	videoram1 = K052109_videoram_F;
/*TODO*///	videoram2 = K052109_videoram2_F;
/*TODO*///	layer = 0;
/*TODO*///}
/*TODO*///
/*TODO*///static void tilemap1_preupdate(void)
/*TODO*///{
/*TODO*///	colorram = K052109_colorram_A;
/*TODO*///	videoram1 = K052109_videoram_A;
/*TODO*///	videoram2 = K052109_videoram2_A;
/*TODO*///	layer = 1;
/*TODO*///}
/*TODO*///
/*TODO*///static void tilemap2_preupdate(void)
/*TODO*///{
/*TODO*///	colorram = K052109_colorram_B;
/*TODO*///	videoram1 = K052109_videoram_B;
/*TODO*///	videoram2 = K052109_videoram2_B;
/*TODO*///	layer = 2;
/*TODO*///}
/*TODO*///
/*TODO*///static void K052109_get_tile_info(int tile_index)
/*TODO*///{
/*TODO*///	int flipy = 0;
/*TODO*///	int code = videoram1[tile_index] + 256 * videoram2[tile_index];
/*TODO*///	int color = colorram[tile_index];
/*TODO*///	int bank = K052109_charrombank[(color & 0x0c) >> 2];
/*TODO*///if (has_extra_video_ram) bank = (color & 0x0c) >> 2;	/* kludge for X-Men */
/*TODO*///	color = (color & 0xf3) | ((bank & 0x03) << 2);
/*TODO*///	bank >>= 2;
/*TODO*///
/*TODO*///	flipy = color & 0x02;
/*TODO*///
/*TODO*///	tile_info.flags = 0;
/*TODO*///
/*TODO*///	(*K052109_callback)(layer,bank,&code,&color);
/*TODO*///
/*TODO*///	SET_TILE_INFO(K052109_gfxnum,code,color);
/*TODO*///
/*TODO*///	/* if the callback set flip X but it is not enabled, turn it off */
/*TODO*///	if (!(K052109_tileflip_enable & 1)) tile_info.flags &= ~TILE_FLIPX;
/*TODO*///
/*TODO*///	/* if flip Y is enabled and the attribute but is set, turn it on */
/*TODO*///	if (flipy && (K052109_tileflip_enable & 2)) tile_info.flags |= TILE_FLIPY;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///int K052109_vh_start(int gfx_memory_region,int plane0,int plane1,int plane2,int plane3,
/*TODO*///		void (*callback)(int tilemap,int bank,int *code,int *color))
/*TODO*///{
/*TODO*///	int gfx_index;
/*TODO*///	static struct GfxLayout charlayout =
/*TODO*///	{
/*TODO*///		8,8,
/*TODO*///		0,				/* filled in later */
/*TODO*///		4,
/*TODO*///		{ 0, 0, 0, 0 },	/* filled in later */
/*TODO*///		{ 0, 1, 2, 3, 4, 5, 6, 7 },
/*TODO*///		{ 0*32, 1*32, 2*32, 3*32, 4*32, 5*32, 6*32, 7*32 },
/*TODO*///		32*8
/*TODO*///	};
/*TODO*///
/*TODO*///
/*TODO*///	/* find first empty slot to decode gfx */
/*TODO*///	for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++)
/*TODO*///		if (Machine->gfx[gfx_index] == 0)
/*TODO*///			break;
/*TODO*///	if (gfx_index == MAX_GFX_ELEMENTS)
/*TODO*///		return 1;
/*TODO*///
/*TODO*///	/* tweak the structure for the number of tiles we have */
/*TODO*///	charlayout.total = memory_region_length(gfx_memory_region) / 32;
/*TODO*///	charlayout.planeoffset[0] = plane3 * 8;
/*TODO*///	charlayout.planeoffset[1] = plane2 * 8;
/*TODO*///	charlayout.planeoffset[2] = plane1 * 8;
/*TODO*///	charlayout.planeoffset[3] = plane0 * 8;
/*TODO*///
/*TODO*///	/* decode the graphics */
/*TODO*///	Machine->gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region),&charlayout);
/*TODO*///	if (!Machine->gfx[gfx_index])
/*TODO*///		return 1;
/*TODO*///
/*TODO*///	/* set the color information */
/*TODO*///	Machine->gfx[gfx_index]->colortable = Machine->remapped_colortable;
/*TODO*///	Machine->gfx[gfx_index]->total_colors = Machine->drv->color_table_len / 16;
/*TODO*///
/*TODO*///	K052109_memory_region = gfx_memory_region;
/*TODO*///	K052109_gfxnum = gfx_index;
/*TODO*///	K052109_callback = callback;
/*TODO*///	K052109_RMRD_line = CLEAR_LINE;
/*TODO*///
/*TODO*///	has_extra_video_ram = 0;
/*TODO*///
/*TODO*///	K052109_tilemap[0] = tilemap_create(K052109_get_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,8,8,64,32);
/*TODO*///	K052109_tilemap[1] = tilemap_create(K052109_get_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,8,8,64,32);
/*TODO*///	K052109_tilemap[2] = tilemap_create(K052109_get_tile_info,tilemap_scan_rows,TILEMAP_TRANSPARENT,8,8,64,32);
/*TODO*///
/*TODO*///	K052109_ram = malloc(0x6000);
/*TODO*///
/*TODO*///	if (!K052109_ram || !K052109_tilemap[0] || !K052109_tilemap[1] || !K052109_tilemap[2])
/*TODO*///	{
/*TODO*///		K052109_vh_stop();
/*TODO*///		return 1;
/*TODO*///	}
/*TODO*///
/*TODO*///	memset(K052109_ram,0,0x6000);
/*TODO*///
/*TODO*///	K052109_colorram_F = &K052109_ram[0x0000];
/*TODO*///	K052109_colorram_A = &K052109_ram[0x0800];
/*TODO*///	K052109_colorram_B = &K052109_ram[0x1000];
/*TODO*///	K052109_videoram_F = &K052109_ram[0x2000];
/*TODO*///	K052109_videoram_A = &K052109_ram[0x2800];
/*TODO*///	K052109_videoram_B = &K052109_ram[0x3000];
/*TODO*///	K052109_videoram2_F = &K052109_ram[0x4000];
/*TODO*///	K052109_videoram2_A = &K052109_ram[0x4800];
/*TODO*///	K052109_videoram2_B = &K052109_ram[0x5000];
/*TODO*///
/*TODO*///	K052109_tilemap[0]->transparent_pen = 0;
/*TODO*///	K052109_tilemap[1]->transparent_pen = 0;
/*TODO*///	K052109_tilemap[2]->transparent_pen = 0;
/*TODO*///
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
/*TODO*///void K052109_vh_stop(void)
/*TODO*///{
/*TODO*///	free(K052109_ram);
/*TODO*///	K052109_ram = 0;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///READ_HANDLER( K052109_r )
/*TODO*///{
/*TODO*///	if (K052109_RMRD_line == CLEAR_LINE)
/*TODO*///	{
/*TODO*///		if ((offset & 0x1fff) >= 0x1800)
/*TODO*///		{
/*TODO*///			if (offset >= 0x180c && offset < 0x1834)
/*TODO*///			{	/* A y scroll */	}
/*TODO*///			else if (offset >= 0x1a00 && offset < 0x1c00)
/*TODO*///			{	/* A x scroll */	}
/*TODO*///			else if (offset == 0x1d00)
/*TODO*///			{	/* read for bitwise operations before writing */	}
/*TODO*///			else if (offset >= 0x380c && offset < 0x3834)
/*TODO*///			{	/* B y scroll */	}
/*TODO*///			else if (offset >= 0x3a00 && offset < 0x3c00)
/*TODO*///			{	/* B x scroll */	}
/*TODO*///			else
/*TODO*///logerror("%04x: read from unknown 052109 address %04x\n",cpu_get_pc(),offset);
/*TODO*///		}
/*TODO*///
/*TODO*///		return K052109_ram[offset];
/*TODO*///	}
/*TODO*///	else	/* Punk Shot and TMNT read from 0000-1fff, Aliens from 2000-3fff */
/*TODO*///	{
/*TODO*///		int code = (offset & 0x1fff) >> 5;
/*TODO*///		int color = K052109_romsubbank;
/*TODO*///		int bank = K052109_charrombank[(color & 0x0c) >> 2] >> 2;   /* discard low bits (TMNT) */
/*TODO*///		int addr;
/*TODO*///
/*TODO*///if (has_extra_video_ram) code |= color << 8;	/* kludge for X-Men */
/*TODO*///else
/*TODO*///		(*K052109_callback)(0,bank,&code,&color);
/*TODO*///
/*TODO*///		addr = (code << 5) + (offset & 0x1f);
/*TODO*///		addr &= memory_region_length(K052109_memory_region)-1;
/*TODO*///
/*TODO*///#if 0
/*TODO*///	usrintf_showmessage("%04x: off%04x sub%02x (bnk%x) adr%06x",cpu_get_pc(),offset,K052109_romsubbank,bank,addr);
/*TODO*///#endif
/*TODO*///
/*TODO*///		return memory_region(K052109_memory_region)[addr];
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K052109_w )
/*TODO*///{
/*TODO*///	if ((offset & 0x1fff) < 0x1800) /* tilemap RAM */
/*TODO*///	{
/*TODO*///		if (K052109_ram[offset] != data)
/*TODO*///		{
/*TODO*///			if (offset >= 0x4000) has_extra_video_ram = 1;  /* kludge for X-Men */
/*TODO*///			K052109_ram[offset] = data;
/*TODO*///			tilemap_mark_tile_dirty(K052109_tilemap[(offset & 0x1800) >> 11],offset & 0x7ff);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else	/* control registers */
/*TODO*///	{
/*TODO*///		K052109_ram[offset] = data;
/*TODO*///
/*TODO*///		if (offset >= 0x180c && offset < 0x1834)
/*TODO*///		{	/* A y scroll */	}
/*TODO*///		else if (offset >= 0x1a00 && offset < 0x1c00)
/*TODO*///		{	/* A x scroll */	}
/*TODO*///		else if (offset == 0x1c80)
/*TODO*///		{
/*TODO*///if (K052109_scrollctrl != data)
/*TODO*///{
/*TODO*///#if 0
/*TODO*///usrintf_showmessage("scrollcontrol = %02x",data);
/*TODO*///#endif
/*TODO*///logerror("%04x: rowscrollcontrol = %02x\n",cpu_get_pc(),data);
/*TODO*///			K052109_scrollctrl = data;
/*TODO*///}
/*TODO*///		}
/*TODO*///		else if (offset == 0x1d00)
/*TODO*///		{
/*TODO*///#if VERBOSE
/*TODO*///logerror("%04x: 052109 register 1d00 = %02x\n",cpu_get_pc(),data);
/*TODO*///#endif
/*TODO*///			/* bit 2 = irq enable */
/*TODO*///			/* the custom chip can also generate NMI and FIRQ, for use with a 6809 */
/*TODO*///			K052109_irq_enabled = data & 0x04;
/*TODO*///		}
/*TODO*///		else if (offset == 0x1d80)
/*TODO*///		{
/*TODO*///			int dirty = 0;
/*TODO*///
/*TODO*///			if (K052109_charrombank[0] != (data & 0x0f)) dirty |= 1;
/*TODO*///			if (K052109_charrombank[1] != ((data >> 4) & 0x0f)) dirty |= 2;
/*TODO*///			if (dirty)
/*TODO*///			{
/*TODO*///				int i;
/*TODO*///
/*TODO*///				K052109_charrombank[0] = data & 0x0f;
/*TODO*///				K052109_charrombank[1] = (data >> 4) & 0x0f;
/*TODO*///
/*TODO*///				for (i = 0;i < 0x1800;i++)
/*TODO*///				{
/*TODO*///					int bank = (K052109_ram[i]&0x0c) >> 2;
/*TODO*///					if ((bank == 0 && (dirty & 1)) || (bank == 1 && dirty & 2))
/*TODO*///					{
/*TODO*///						tilemap_mark_tile_dirty(K052109_tilemap[(i & 0x1800) >> 11],i & 0x7ff);
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///		else if (offset == 0x1e00)
/*TODO*///		{
/*TODO*///logerror("%04x: 052109 register 1e00 = %02x\n",cpu_get_pc(),data);
/*TODO*///			K052109_romsubbank = data;
/*TODO*///		}
/*TODO*///		else if (offset == 0x1e80)
/*TODO*///		{
/*TODO*///if ((data & 0xfe)) logerror("%04x: 052109 register 1e80 = %02x\n",cpu_get_pc(),data);
/*TODO*///			tilemap_set_flip(K052109_tilemap[0],(data & 1) ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
/*TODO*///			tilemap_set_flip(K052109_tilemap[1],(data & 1) ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
/*TODO*///			tilemap_set_flip(K052109_tilemap[2],(data & 1) ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
/*TODO*///			if (K052109_tileflip_enable != ((data & 0x06) >> 1))
/*TODO*///			{
/*TODO*///				K052109_tileflip_enable = ((data & 0x06) >> 1);
/*TODO*///
/*TODO*///				tilemap_mark_all_tiles_dirty(K052109_tilemap[0]);
/*TODO*///				tilemap_mark_all_tiles_dirty(K052109_tilemap[1]);
/*TODO*///				tilemap_mark_all_tiles_dirty(K052109_tilemap[2]);
/*TODO*///			}
/*TODO*///		}
/*TODO*///		else if (offset == 0x1f00)
/*TODO*///		{
/*TODO*///			int dirty = 0;
/*TODO*///
/*TODO*///			if (K052109_charrombank[2] != (data & 0x0f)) dirty |= 1;
/*TODO*///			if (K052109_charrombank[3] != ((data >> 4) & 0x0f)) dirty |= 2;
/*TODO*///			if (dirty)
/*TODO*///			{
/*TODO*///				int i;
/*TODO*///
/*TODO*///				K052109_charrombank[2] = data & 0x0f;
/*TODO*///				K052109_charrombank[3] = (data >> 4) & 0x0f;
/*TODO*///
/*TODO*///				for (i = 0;i < 0x1800;i++)
/*TODO*///				{
/*TODO*///					int bank = (K052109_ram[i] & 0x0c) >> 2;
/*TODO*///					if ((bank == 2 && (dirty & 1)) || (bank == 3 && dirty & 2))
/*TODO*///						tilemap_mark_tile_dirty(K052109_tilemap[(i & 0x1800) >> 11],i & 0x7ff);
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///		else if (offset >= 0x380c && offset < 0x3834)
/*TODO*///		{	/* B y scroll */	}
/*TODO*///		else if (offset >= 0x3a00 && offset < 0x3c00)
/*TODO*///		{	/* B x scroll */	}
/*TODO*///		else
/*TODO*///logerror("%04x: write %02x to unknown 052109 address %04x\n",cpu_get_pc(),data,offset);
/*TODO*///	}
/*TODO*///}
/*TODO*///
    public static void K052109_set_RMRD_line(int state) {
        K052109_RMRD_line = state;
    }


    /*TODO*///void K052109_tilemap_update(void)
/*TODO*///{
/*TODO*///#if 0
/*TODO*///{
/*TODO*///usrintf_showmessage("%x %x %x %x",
/*TODO*///	K052109_charrombank[0],
/*TODO*///	K052109_charrombank[1],
/*TODO*///	K052109_charrombank[2],
/*TODO*///	K052109_charrombank[3]);
/*TODO*///}
/*TODO*///#endif
/*TODO*///	if ((K052109_scrollctrl & 0x03) == 0x02)
/*TODO*///	{
/*TODO*///		int xscroll,yscroll,offs;
/*TODO*///		unsigned char *scrollram = &K052109_ram[0x1a00];
/*TODO*///
/*TODO*///
/*TODO*///		tilemap_set_scroll_rows(K052109_tilemap[1],256);
/*TODO*///		tilemap_set_scroll_cols(K052109_tilemap[1],1);
/*TODO*///		yscroll = K052109_ram[0x180c];
/*TODO*///		tilemap_set_scrolly(K052109_tilemap[1],0,yscroll);
/*TODO*///		for (offs = 0;offs < 256;offs++)
/*TODO*///		{
/*TODO*///			xscroll = scrollram[2*(offs&0xfff8)+0] + 256 * scrollram[2*(offs&0xfff8)+1];
/*TODO*///			xscroll -= 6;
/*TODO*///			tilemap_set_scrollx(K052109_tilemap[1],(offs+yscroll)&0xff,xscroll);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else if ((K052109_scrollctrl & 0x03) == 0x03)
/*TODO*///	{
/*TODO*///		int xscroll,yscroll,offs;
/*TODO*///		unsigned char *scrollram = &K052109_ram[0x1a00];
/*TODO*///
/*TODO*///
/*TODO*///		tilemap_set_scroll_rows(K052109_tilemap[1],256);
/*TODO*///		tilemap_set_scroll_cols(K052109_tilemap[1],1);
/*TODO*///		yscroll = K052109_ram[0x180c];
/*TODO*///		tilemap_set_scrolly(K052109_tilemap[1],0,yscroll);
/*TODO*///		for (offs = 0;offs < 256;offs++)
/*TODO*///		{
/*TODO*///			xscroll = scrollram[2*offs+0] + 256 * scrollram[2*offs+1];
/*TODO*///			xscroll -= 6;
/*TODO*///			tilemap_set_scrollx(K052109_tilemap[1],(offs+yscroll)&0xff,xscroll);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else if ((K052109_scrollctrl & 0x04) == 0x04)
/*TODO*///	{
/*TODO*///		int xscroll,yscroll,offs;
/*TODO*///		unsigned char *scrollram = &K052109_ram[0x1800];
/*TODO*///
/*TODO*///
/*TODO*///		tilemap_set_scroll_rows(K052109_tilemap[1],1);
/*TODO*///		tilemap_set_scroll_cols(K052109_tilemap[1],512);
/*TODO*///		xscroll = K052109_ram[0x1a00] + 256 * K052109_ram[0x1a01];
/*TODO*///		xscroll -= 6;
/*TODO*///		tilemap_set_scrollx(K052109_tilemap[1],0,xscroll);
/*TODO*///		for (offs = 0;offs < 512;offs++)
/*TODO*///		{
/*TODO*///			yscroll = scrollram[offs/8];
/*TODO*///			tilemap_set_scrolly(K052109_tilemap[1],(offs+xscroll)&0x1ff,yscroll);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		int xscroll,yscroll;
/*TODO*///		unsigned char *scrollram = &K052109_ram[0x1a00];
/*TODO*///
/*TODO*///
/*TODO*///		tilemap_set_scroll_rows(K052109_tilemap[1],1);
/*TODO*///		tilemap_set_scroll_cols(K052109_tilemap[1],1);
/*TODO*///		xscroll = scrollram[0] + 256 * scrollram[1];
/*TODO*///		xscroll -= 6;
/*TODO*///		yscroll = K052109_ram[0x180c];
/*TODO*///		tilemap_set_scrollx(K052109_tilemap[1],0,xscroll);
/*TODO*///		tilemap_set_scrolly(K052109_tilemap[1],0,yscroll);
/*TODO*///	}
/*TODO*///
/*TODO*///	if ((K052109_scrollctrl & 0x18) == 0x10)
/*TODO*///	{
/*TODO*///		int xscroll,yscroll,offs;
/*TODO*///		unsigned char *scrollram = &K052109_ram[0x3a00];
/*TODO*///
/*TODO*///
/*TODO*///		tilemap_set_scroll_rows(K052109_tilemap[2],256);
/*TODO*///		tilemap_set_scroll_cols(K052109_tilemap[2],1);
/*TODO*///		yscroll = K052109_ram[0x380c];
/*TODO*///		tilemap_set_scrolly(K052109_tilemap[2],0,yscroll);
/*TODO*///		for (offs = 0;offs < 256;offs++)
/*TODO*///		{
/*TODO*///			xscroll = scrollram[2*(offs&0xfff8)+0] + 256 * scrollram[2*(offs&0xfff8)+1];
/*TODO*///			xscroll -= 6;
/*TODO*///			tilemap_set_scrollx(K052109_tilemap[2],(offs+yscroll)&0xff,xscroll);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else if ((K052109_scrollctrl & 0x18) == 0x18)
/*TODO*///	{
/*TODO*///		int xscroll,yscroll,offs;
/*TODO*///		unsigned char *scrollram = &K052109_ram[0x3a00];
/*TODO*///
/*TODO*///
/*TODO*///		tilemap_set_scroll_rows(K052109_tilemap[2],256);
/*TODO*///		tilemap_set_scroll_cols(K052109_tilemap[2],1);
/*TODO*///		yscroll = K052109_ram[0x380c];
/*TODO*///		tilemap_set_scrolly(K052109_tilemap[2],0,yscroll);
/*TODO*///		for (offs = 0;offs < 256;offs++)
/*TODO*///		{
/*TODO*///			xscroll = scrollram[2*offs+0] + 256 * scrollram[2*offs+1];
/*TODO*///			xscroll -= 6;
/*TODO*///			tilemap_set_scrollx(K052109_tilemap[2],(offs+yscroll)&0xff,xscroll);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else if ((K052109_scrollctrl & 0x20) == 0x20)
/*TODO*///	{
/*TODO*///		int xscroll,yscroll,offs;
/*TODO*///		unsigned char *scrollram = &K052109_ram[0x3800];
/*TODO*///
/*TODO*///
/*TODO*///		tilemap_set_scroll_rows(K052109_tilemap[2],1);
/*TODO*///		tilemap_set_scroll_cols(K052109_tilemap[2],512);
/*TODO*///		xscroll = K052109_ram[0x3a00] + 256 * K052109_ram[0x3a01];
/*TODO*///		xscroll -= 6;
/*TODO*///		tilemap_set_scrollx(K052109_tilemap[2],0,xscroll);
/*TODO*///		for (offs = 0;offs < 512;offs++)
/*TODO*///		{
/*TODO*///			yscroll = scrollram[offs/8];
/*TODO*///			tilemap_set_scrolly(K052109_tilemap[2],(offs+xscroll)&0x1ff,yscroll);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		int xscroll,yscroll;
/*TODO*///		unsigned char *scrollram = &K052109_ram[0x3a00];
/*TODO*///
/*TODO*///
/*TODO*///		tilemap_set_scroll_rows(K052109_tilemap[2],1);
/*TODO*///		tilemap_set_scroll_cols(K052109_tilemap[2],1);
/*TODO*///		xscroll = scrollram[0] + 256 * scrollram[1];
/*TODO*///		xscroll -= 6;
/*TODO*///		yscroll = K052109_ram[0x380c];
/*TODO*///		tilemap_set_scrollx(K052109_tilemap[2],0,xscroll);
/*TODO*///		tilemap_set_scrolly(K052109_tilemap[2],0,yscroll);
/*TODO*///	}
/*TODO*///
/*TODO*///	tilemap0_preupdate(); tilemap_update(K052109_tilemap[0]);
/*TODO*///	tilemap1_preupdate(); tilemap_update(K052109_tilemap[1]);
/*TODO*///	tilemap2_preupdate(); tilemap_update(K052109_tilemap[2]);
/*TODO*///
/*TODO*///#ifdef MAME_DEBUG
/*TODO*///if ((K052109_scrollctrl & 0x03) == 0x01 ||
/*TODO*///		(K052109_scrollctrl & 0x18) == 0x08 ||
/*TODO*///		((K052109_scrollctrl & 0x04) && (K052109_scrollctrl & 0x03)) ||
/*TODO*///		((K052109_scrollctrl & 0x20) && (K052109_scrollctrl & 0x18)) ||
/*TODO*///		(K052109_scrollctrl & 0xc0) != 0)
/*TODO*///	usrintf_showmessage("scrollcontrol = %02x",K052109_scrollctrl);
/*TODO*///#endif
/*TODO*///
/*TODO*///#if 0
/*TODO*///if (keyboard_pressed(KEYCODE_F))
/*TODO*///{
/*TODO*///	FILE *fp;
/*TODO*///	fp=fopen("TILE.DMP", "w+b");
/*TODO*///	if (fp)
/*TODO*///	{
/*TODO*///		fwrite(K052109_ram, 0x6000, 1, fp);
/*TODO*///		usrintf_showmessage("saved");
/*TODO*///		fclose(fp);
/*TODO*///	}
/*TODO*///}
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///void K052109_tilemap_draw(struct osd_bitmap *bitmap,int num,int flags)
/*TODO*///{
/*TODO*///	tilemap_draw(bitmap,K052109_tilemap[num],flags);
/*TODO*///}
/*TODO*///
/*TODO*///int K052109_is_IRQ_enabled(void)
/*TODO*///{
/*TODO*///	return K052109_irq_enabled;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///static int K051960_memory_region;
/*TODO*///static struct GfxElement *K051960_gfx;
/*TODO*///static void (*K051960_callback)(int *code,int *color,int *priority,int *shadow);
/*TODO*///static int K051960_romoffset;
/*TODO*///static int K051960_spriteflip,K051960_readroms;
/*TODO*///static unsigned char K051960_spriterombank[3];
/*TODO*///static unsigned char *K051960_ram;
/*TODO*///static int K051960_irq_enabled, K051960_nmi_enabled;
/*TODO*///
/*TODO*///
/*TODO*///int K051960_vh_start(int gfx_memory_region,int plane0,int plane1,int plane2,int plane3,
/*TODO*///		void (*callback)(int *code,int *color,int *priority,int *shadow))
/*TODO*///{
/*TODO*///	int gfx_index;
/*TODO*///	static struct GfxLayout spritelayout =
/*TODO*///	{
/*TODO*///		16,16,
/*TODO*///		0,				/* filled in later */
/*TODO*///		4,
/*TODO*///		{ 0, 0, 0, 0 },	/* filled in later */
/*TODO*///		{ 0, 1, 2, 3, 4, 5, 6, 7,
/*TODO*///				8*32+0, 8*32+1, 8*32+2, 8*32+3, 8*32+4, 8*32+5, 8*32+6, 8*32+7 },
/*TODO*///		{ 0*32, 1*32, 2*32, 3*32, 4*32, 5*32, 6*32, 7*32,
/*TODO*///				16*32, 17*32, 18*32, 19*32, 20*32, 21*32, 22*32, 23*32 },
/*TODO*///		128*8
/*TODO*///	};
/*TODO*///
/*TODO*///
/*TODO*///	/* find first empty slot to decode gfx */
/*TODO*///	for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++)
/*TODO*///		if (Machine->gfx[gfx_index] == 0)
/*TODO*///			break;
/*TODO*///	if (gfx_index == MAX_GFX_ELEMENTS)
/*TODO*///		return 1;
/*TODO*///
/*TODO*///	/* tweak the structure for the number of tiles we have */
/*TODO*///	spritelayout.total = memory_region_length(gfx_memory_region) / 128;
/*TODO*///	spritelayout.planeoffset[0] = plane0 * 8;
/*TODO*///	spritelayout.planeoffset[1] = plane1 * 8;
/*TODO*///	spritelayout.planeoffset[2] = plane2 * 8;
/*TODO*///	spritelayout.planeoffset[3] = plane3 * 8;
/*TODO*///
/*TODO*///	/* decode the graphics */
/*TODO*///	Machine->gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region),&spritelayout);
/*TODO*///	if (!Machine->gfx[gfx_index])
/*TODO*///		return 1;
/*TODO*///
/*TODO*///	/* set the color information */
/*TODO*///	Machine->gfx[gfx_index]->colortable = Machine->remapped_colortable;
/*TODO*///	Machine->gfx[gfx_index]->total_colors = Machine->drv->color_table_len / 16;
/*TODO*///
/*TODO*///	K051960_memory_region = gfx_memory_region;
/*TODO*///	K051960_gfx = Machine->gfx[gfx_index];
/*TODO*///	K051960_callback = callback;
/*TODO*///	K051960_ram = malloc(0x400);
/*TODO*///	if (!K051960_ram) return 1;
/*TODO*///	memset(K051960_ram,0,0x400);
/*TODO*///
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
/*TODO*///void K051960_vh_stop(void)
/*TODO*///{
/*TODO*///	free(K051960_ram);
/*TODO*///	K051960_ram = 0;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///static int K051960_fetchromdata(int byte)
/*TODO*///{
/*TODO*///	int code,color,pri,shadow,off1,addr;
/*TODO*///
/*TODO*///
/*TODO*///	addr = K051960_romoffset + (K051960_spriterombank[0] << 8) +
/*TODO*///			((K051960_spriterombank[1] & 0x03) << 16);
/*TODO*///	code = (addr & 0x3ffe0) >> 5;
/*TODO*///	off1 = addr & 0x1f;
/*TODO*///	color = ((K051960_spriterombank[1] & 0xfc) >> 2) + ((K051960_spriterombank[2] & 0x03) << 6);
/*TODO*///	pri = 0;
/*TODO*///	shadow = color & 0x80;
/*TODO*///	(*K051960_callback)(&code,&color,&pri,&shadow);
/*TODO*///
/*TODO*///	addr = (code << 7) | (off1 << 2) | byte;
/*TODO*///	addr &= memory_region_length(K051960_memory_region)-1;
/*TODO*///
/*TODO*///#if 0
/*TODO*///	usrintf_showmessage("%04x: addr %06x",cpu_get_pc(),addr);
/*TODO*///#endif
/*TODO*///
/*TODO*///	return memory_region(K051960_memory_region)[addr];
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051960_r )
/*TODO*///{
/*TODO*///	if (K051960_readroms)
/*TODO*///	{
/*TODO*///		/* the 051960 remembers the last address read and uses it when reading the sprite ROMs */
/*TODO*///		K051960_romoffset = (offset & 0x3fc) >> 2;
/*TODO*///		return K051960_fetchromdata(offset & 3);	/* only 88 Games reads the ROMs from here */
/*TODO*///	}
/*TODO*///	else
/*TODO*///		return K051960_ram[offset];
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051960_w )
/*TODO*///{
/*TODO*///	K051960_ram[offset] = data;
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051960_word_r )
/*TODO*///{
/*TODO*///	return K051960_r(offset + 1) | (K051960_r(offset) << 8);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051960_word_w )
/*TODO*///{
/*TODO*///	if ((data & 0xff000000) == 0)
/*TODO*///		K051960_w(offset,(data >> 8) & 0xff);
/*TODO*///	if ((data & 0x00ff0000) == 0)
/*TODO*///		K051960_w(offset + 1,data & 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051937_r )
/*TODO*///{
/*TODO*///	if (K051960_readroms && offset >= 4 && offset < 8)
/*TODO*///	{
/*TODO*///		return K051960_fetchromdata(offset & 3);
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		if (offset == 0)
/*TODO*///		{
/*TODO*///			static int counter;
/*TODO*///
/*TODO*///			/* some games need bit 0 to pulse */
/*TODO*///			return (counter++) & 1;
/*TODO*///		}
/*TODO*///logerror("%04x: read unknown 051937 address %x\n",cpu_get_pc(),offset);
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051937_w )
/*TODO*///{
/*TODO*///	if (offset == 0)
/*TODO*///	{
/*TODO*///#ifdef MAME_DEBUG
/*TODO*///if (data & 0xc6)
/*TODO*///	usrintf_showmessage("051937 reg 00 = %02x",data);
/*TODO*///#endif
/*TODO*///		/* bit 0 is IRQ enable */
/*TODO*///		K051960_irq_enabled = (data & 0x01);
/*TODO*///
/*TODO*///		/* bit 1: probably FIRQ enable */
/*TODO*///
/*TODO*///		/* bit 2 is NMI enable */
/*TODO*///		K051960_nmi_enabled = (data & 0x04);
/*TODO*///
/*TODO*///		/* bit 3 = flip screen */
/*TODO*///		K051960_spriteflip = data & 0x08;
/*TODO*///
/*TODO*///		/* bit 4 used by Devastators and TMNT, unknown */
/*TODO*///
/*TODO*///		/* bit 5 = enable gfx ROM reading */
/*TODO*///		K051960_readroms = data & 0x20;
/*TODO*///#if VERBOSE
/*TODO*///logerror("%04x: write %02x to 051937 address %x\n",cpu_get_pc(),data,offset);
/*TODO*///#endif
/*TODO*///	}
/*TODO*///	else if (offset == 1)
/*TODO*///	{
/*TODO*///#if 0
/*TODO*///	usrintf_showmessage("%04x: write %02x to 051937 address %x",cpu_get_pc(),data,offset);
/*TODO*///#endif
/*TODO*///logerror("%04x: write %02x to unknown 051937 address %x\n",cpu_get_pc(),data,offset);
/*TODO*///	}
/*TODO*///	else if (offset >= 2 && offset < 5)
/*TODO*///	{
/*TODO*///		K051960_spriterombank[offset - 2] = data;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///#if 0
/*TODO*///	usrintf_showmessage("%04x: write %02x to 051937 address %x",cpu_get_pc(),data,offset);
/*TODO*///#endif
/*TODO*///logerror("%04x: write %02x to unknown 051937 address %x\n",cpu_get_pc(),data,offset);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051937_word_r )
/*TODO*///{
/*TODO*///	return K051937_r(offset + 1) | (K051937_r(offset) << 8);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051937_word_w )
/*TODO*///{
/*TODO*///	if ((data & 0xff000000) == 0)
/*TODO*///		K051937_w(offset,(data >> 8) & 0xff);
/*TODO*///	if ((data & 0x00ff0000) == 0)
/*TODO*///		K051937_w(offset + 1,data & 0xff);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////*
/*TODO*/// * Sprite Format
/*TODO*/// * ------------------
/*TODO*/// *
/*TODO*/// * Byte | Bit(s)   | Use
/*TODO*/// * -----+-76543210-+----------------
/*TODO*/// *   0  | x------- | active (show this sprite)
/*TODO*/// *   0  | -xxxxxxx | priority order
/*TODO*/// *   1  | xxx----- | sprite size (see below)
/*TODO*/// *   1  | ---xxxxx | sprite code (high 5 bits)
/*TODO*/// *   2  | xxxxxxxx | sprite code (low 8 bits)
/*TODO*/// *   3  | xxxxxxxx | "color", but depends on external connections (see below)
/*TODO*/// *   4  | xxxxxx-- | zoom y (0 = normal, >0 = shrink)
/*TODO*/// *   4  | ------x- | flip y
/*TODO*/// *   4  | -------x | y position (high bit)
/*TODO*/// *   5  | xxxxxxxx | y position (low 8 bits)
/*TODO*/// *   6  | xxxxxx-- | zoom x (0 = normal, >0 = shrink)
/*TODO*/// *   6  | ------x- | flip x
/*TODO*/// *   6  | -------x | x position (high bit)
/*TODO*/// *   7  | xxxxxxxx | x position (low 8 bits)
/*TODO*/// *
/*TODO*/// * Example of "color" field for Punk Shot:
/*TODO*/// *   3  | x------- | shadow
/*TODO*/// *   3  | -xx----- | priority
/*TODO*/// *   3  | ---x---- | use second gfx ROM bank
/*TODO*/// *   3  | ----xxxx | color code
/*TODO*/// *
/*TODO*/// * shadow enables transparent shadows. Note that it applies to pen 0x0f ONLY.
/*TODO*/// * The rest of the sprite remains normal.
/*TODO*/// * Note that Aliens also uses the shadow bit to select the second sprite bank.
/*TODO*/// */
/*TODO*///
/*TODO*///void K051960_sprites_draw(struct osd_bitmap *bitmap,int min_priority,int max_priority)
/*TODO*///{
/*TODO*///#define NUM_SPRITES 128
/*TODO*///	int offs,pri_code;
/*TODO*///	int sortedlist[NUM_SPRITES];
/*TODO*///
/*TODO*///	for (offs = 0;offs < NUM_SPRITES;offs++)
/*TODO*///		sortedlist[offs] = -1;
/*TODO*///
/*TODO*///	/* prebuild a sorted table */
/*TODO*///	for (offs = 0;offs < 0x400;offs += 8)
/*TODO*///	{
/*TODO*///		if (K051960_ram[offs] & 0x80)
/*TODO*///		{
/*TODO*///			if (max_priority == -1)	/* draw front to back when using priority buffer */
/*TODO*///				sortedlist[(K051960_ram[offs] & 0x7f) ^ 0x7f] = offs;
/*TODO*///			else
/*TODO*///				sortedlist[K051960_ram[offs] & 0x7f] = offs;
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	for (pri_code = 0;pri_code < NUM_SPRITES;pri_code++)
/*TODO*///	{
/*TODO*///		int ox,oy,code,color,pri,shadow,size,w,h,x,y,flipx,flipy,zoomx,zoomy;
/*TODO*///		/* sprites can be grouped up to 8x8. The draw order is
/*TODO*///			 0  1  4  5 16 17 20 21
/*TODO*///			 2  3  6  7 18 19 22 23
/*TODO*///			 8  9 12 13 24 25 28 29
/*TODO*///			10 11 14 15 26 27 30 31
/*TODO*///			32 33 36 37 48 49 52 53
/*TODO*///			34 35 38 39 50 51 54 55
/*TODO*///			40 41 44 45 56 57 60 61
/*TODO*///			42 43 46 47 58 59 62 63
/*TODO*///		*/
/*TODO*///		static int xoffset[8] = { 0, 1, 4, 5, 16, 17, 20, 21 };
/*TODO*///		static int yoffset[8] = { 0, 2, 8, 10, 32, 34, 40, 42 };
/*TODO*///		static int width[8] =  { 1, 2, 1, 2, 4, 2, 4, 8 };
/*TODO*///		static int height[8] = { 1, 1, 2, 2, 2, 4, 4, 8 };
/*TODO*///
/*TODO*///
/*TODO*///		offs = sortedlist[pri_code];
/*TODO*///		if (offs == -1) continue;
/*TODO*///
/*TODO*///		code = K051960_ram[offs+2] + ((K051960_ram[offs+1] & 0x1f) << 8);
/*TODO*///		color = K051960_ram[offs+3] & 0xff;
/*TODO*///		pri = 0;
/*TODO*///		shadow = color & 0x80;
/*TODO*///		(*K051960_callback)(&code,&color,&pri,&shadow);
/*TODO*///
/*TODO*///		if (max_priority != -1)
/*TODO*///			if (pri < min_priority || pri > max_priority) continue;
/*TODO*///
/*TODO*///		size = (K051960_ram[offs+1] & 0xe0) >> 5;
/*TODO*///		w = width[size];
/*TODO*///		h = height[size];
/*TODO*///
/*TODO*///		if (w >= 2) code &= ~0x01;
/*TODO*///		if (h >= 2) code &= ~0x02;
/*TODO*///		if (w >= 4) code &= ~0x04;
/*TODO*///		if (h >= 4) code &= ~0x08;
/*TODO*///		if (w >= 8) code &= ~0x10;
/*TODO*///		if (h >= 8) code &= ~0x20;
/*TODO*///
/*TODO*///		ox = (256 * K051960_ram[offs+6] + K051960_ram[offs+7]) & 0x01ff;
/*TODO*///		oy = 256 - ((256 * K051960_ram[offs+4] + K051960_ram[offs+5]) & 0x01ff);
/*TODO*///		flipx = K051960_ram[offs+6] & 0x02;
/*TODO*///		flipy = K051960_ram[offs+4] & 0x02;
/*TODO*///		zoomx = (K051960_ram[offs+6] & 0xfc) >> 2;
/*TODO*///		zoomy = (K051960_ram[offs+4] & 0xfc) >> 2;
/*TODO*///		zoomx = 0x10000 / 128 * (128 - zoomx);
/*TODO*///		zoomy = 0x10000 / 128 * (128 - zoomy);
/*TODO*///
/*TODO*///		if (K051960_spriteflip)
/*TODO*///		{
/*TODO*///			ox = 512 - (zoomx * w >> 12) - ox;
/*TODO*///			oy = 256 - (zoomy * h >> 12) - oy;
/*TODO*///			flipx = !flipx;
/*TODO*///			flipy = !flipy;
/*TODO*///		}
/*TODO*///
/*TODO*///		if (zoomx == 0x10000 && zoomy == 0x10000)
/*TODO*///		{
/*TODO*///			int sx,sy;
/*TODO*///
/*TODO*///			for (y = 0;y < h;y++)
/*TODO*///			{
/*TODO*///				sy = oy + 16 * y;
/*TODO*///
/*TODO*///				for (x = 0;x < w;x++)
/*TODO*///				{
/*TODO*///					int c = code;
/*TODO*///
/*TODO*///					sx = ox + 16 * x;
/*TODO*///					if (flipx) c += xoffset[(w-1-x)];
/*TODO*///					else c += xoffset[x];
/*TODO*///					if (flipy) c += yoffset[(h-1-y)];
/*TODO*///					else c += yoffset[y];
/*TODO*///
/*TODO*///					/* hack to simulate shadow */
/*TODO*///					if (shadow)
/*TODO*///					{
/*TODO*///						int o = K051960_gfx->colortable[16*color+15];
/*TODO*///						K051960_gfx->colortable[16*color+15] = palette_transparent_pen;
/*TODO*///						if (max_priority == -1)
/*TODO*///							pdrawgfx(bitmap,K051960_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									flipx,flipy,
/*TODO*///									sx & 0x1ff,sy,
/*TODO*///									&Machine->visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,pri);
/*TODO*///						else
/*TODO*///							drawgfx(bitmap,K051960_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									flipx,flipy,
/*TODO*///									sx & 0x1ff,sy,
/*TODO*///									&Machine->visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001);
/*TODO*///						K051960_gfx->colortable[16*color+15] = o;
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						if (max_priority == -1)
/*TODO*///							pdrawgfx(bitmap,K051960_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									flipx,flipy,
/*TODO*///									sx & 0x1ff,sy,
/*TODO*///									&Machine->visible_area,TRANSPARENCY_PEN,0,pri);
/*TODO*///						else
/*TODO*///							drawgfx(bitmap,K051960_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									flipx,flipy,
/*TODO*///									sx & 0x1ff,sy,
/*TODO*///									&Machine->visible_area,TRANSPARENCY_PEN,0);
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			int sx,sy,zw,zh;
/*TODO*///
/*TODO*///			for (y = 0;y < h;y++)
/*TODO*///			{
/*TODO*///				sy = oy + ((zoomy * y + (1<<11)) >> 12);
/*TODO*///				zh = (oy + ((zoomy * (y+1) + (1<<11)) >> 12)) - sy;
/*TODO*///
/*TODO*///				for (x = 0;x < w;x++)
/*TODO*///				{
/*TODO*///					int c = code;
/*TODO*///
/*TODO*///					sx = ox + ((zoomx * x + (1<<11)) >> 12);
/*TODO*///					zw = (ox + ((zoomx * (x+1) + (1<<11)) >> 12)) - sx;
/*TODO*///					if (flipx) c += xoffset[(w-1-x)];
/*TODO*///					else c += xoffset[x];
/*TODO*///					if (flipy) c += yoffset[(h-1-y)];
/*TODO*///					else c += yoffset[y];
/*TODO*///
/*TODO*///					/* hack to simulate shadow */
/*TODO*///					if (shadow)
/*TODO*///					{
/*TODO*///						int o = K051960_gfx->colortable[16*color+15];
/*TODO*///						K051960_gfx->colortable[16*color+15] = palette_transparent_pen;
/*TODO*///						if (max_priority == -1)
/*TODO*///							pdrawgfxzoom(bitmap,K051960_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									flipx,flipy,
/*TODO*///									sx & 0x1ff,sy,
/*TODO*///									&Machine->visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,
/*TODO*///									(zw << 16) / 16,(zh << 16) / 16,pri);
/*TODO*///						else
/*TODO*///							drawgfxzoom(bitmap,K051960_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									flipx,flipy,
/*TODO*///									sx & 0x1ff,sy,
/*TODO*///									&Machine->visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,
/*TODO*///									(zw << 16) / 16,(zh << 16) / 16);
/*TODO*///						K051960_gfx->colortable[16*color+15] = o;
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						if (max_priority == -1)
/*TODO*///							pdrawgfxzoom(bitmap,K051960_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									flipx,flipy,
/*TODO*///									sx & 0x1ff,sy,
/*TODO*///									&Machine->visible_area,TRANSPARENCY_PEN,0,
/*TODO*///									(zw << 16) / 16,(zh << 16) / 16,pri);
/*TODO*///						else
/*TODO*///							drawgfxzoom(bitmap,K051960_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									flipx,flipy,
/*TODO*///									sx & 0x1ff,sy,
/*TODO*///									&Machine->visible_area,TRANSPARENCY_PEN,0,
/*TODO*///									(zw << 16) / 16,(zh << 16) / 16);
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///#if 0
/*TODO*///if (keyboard_pressed(KEYCODE_D))
/*TODO*///{
/*TODO*///	FILE *fp;
/*TODO*///	fp=fopen("SPRITE.DMP", "w+b");
/*TODO*///	if (fp)
/*TODO*///	{
/*TODO*///		fwrite(K051960_ram, 0x400, 1, fp);
/*TODO*///		usrintf_showmessage("saved");
/*TODO*///		fclose(fp);
/*TODO*///	}
/*TODO*///}
/*TODO*///#endif
/*TODO*///#undef NUM_SPRITES
/*TODO*///}
/*TODO*///
/*TODO*///void K051960_mark_sprites_colors(void)
/*TODO*///{
/*TODO*///	int offs,i;
/*TODO*///
/*TODO*///	unsigned short palette_map[512];
/*TODO*///
/*TODO*///	memset (palette_map, 0, sizeof (palette_map));
/*TODO*///
/*TODO*///	/* sprites */
/*TODO*///	for (offs = 0x400-8;offs >= 0;offs -= 8)
/*TODO*///	{
/*TODO*///		if (K051960_ram[offs] & 0x80)
/*TODO*///		{
/*TODO*///			int code,color,pri,shadow;
/*TODO*///
/*TODO*///			code = K051960_ram[offs+2] + ((K051960_ram[offs+1] & 0x1f) << 8);
/*TODO*///			color = (K051960_ram[offs+3] & 0xff);
/*TODO*///			pri = 0;
/*TODO*///			shadow = color & 0x80;
/*TODO*///			(*K051960_callback)(&code,&color,&pri,&shadow);
/*TODO*///			palette_map[color] |= 0xffff;
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	/* now build the final table */
/*TODO*///	for (i = 0; i < 512; i++)
/*TODO*///	{
/*TODO*///		int usage = palette_map[i], j;
/*TODO*///		if (usage)
/*TODO*///		{
/*TODO*///			for (j = 1; j < 16; j++)
/*TODO*///				if (usage & (1 << j))
/*TODO*///					palette_used_colors[i * 16 + j] |= PALETTE_COLOR_VISIBLE;
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///int K051960_is_IRQ_enabled(void)
/*TODO*///{
/*TODO*///	return K051960_irq_enabled;
/*TODO*///}
/*TODO*///
/*TODO*///int K051960_is_NMI_enabled(void)
/*TODO*///{
/*TODO*///	return K051960_nmi_enabled;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///READ_HANDLER( K052109_051960_r )
/*TODO*///{
/*TODO*///	if (K052109_RMRD_line == CLEAR_LINE)
/*TODO*///	{
/*TODO*///		if (offset >= 0x3800 && offset < 0x3808)
/*TODO*///			return K051937_r(offset - 0x3800);
/*TODO*///		else if (offset < 0x3c00)
/*TODO*///			return K052109_r(offset);
/*TODO*///		else
/*TODO*///			return K051960_r(offset - 0x3c00);
/*TODO*///	}
/*TODO*///	else return K052109_r(offset);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K052109_051960_w )
/*TODO*///{
/*TODO*///	if (offset >= 0x3800 && offset < 0x3808)
/*TODO*///		K051937_w(offset - 0x3800,data);
/*TODO*///	else if (offset < 0x3c00)
/*TODO*///		K052109_w(offset,data);
/*TODO*///	else
/*TODO*///		K051960_w(offset - 0x3c00,data);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
    public static abstract interface K053245_callbackProcPtr {

        public abstract void handler(int[] code, int[] color, int[] priority);
    }
    static int K053245_memory_region = 2;
    static GfxElement K053245_gfx;
    static K053245_callbackProcPtr K053245_callback;
    static int K053244_romoffset, K053244_rombank;
    static int K053244_readroms;
    static int K053245_flipscreenX, K053245_flipscreenY;
    static int K053245_spriteoffsX, K053245_spriteoffsY;
    static UBytePtr K053245_ram;

    public static int K053245_vh_start(int gfx_memory_region, int plane0, int plane1, int plane2, int plane3,
            K053245_callbackProcPtr callback) {
        int gfx_index;
        GfxLayout spritelayout = new GfxLayout(
                16, 16,
                0, /* filled in later */
                4,
                new int[]{0, 0, 0, 0}, /* filled in later */
                new int[]{0, 1, 2, 3, 4, 5, 6, 7,
                    8 * 32 + 0, 8 * 32 + 1, 8 * 32 + 2, 8 * 32 + 3, 8 * 32 + 4, 8 * 32 + 5, 8 * 32 + 6, 8 * 32 + 7},
                new int[]{0 * 32, 1 * 32, 2 * 32, 3 * 32, 4 * 32, 5 * 32, 6 * 32, 7 * 32,
                    16 * 32, 17 * 32, 18 * 32, 19 * 32, 20 * 32, 21 * 32, 22 * 32, 23 * 32},
                128 * 8
        );

        /* find first empty slot to decode gfx */
        for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++) {
            if (Machine.gfx[gfx_index] == null) {
                break;
            }
        }
        if (gfx_index == MAX_GFX_ELEMENTS) {
            return 1;
        }

        /* tweak the structure for the number of tiles we have */
        spritelayout.total = memory_region_length(gfx_memory_region) / 128;
        spritelayout.planeoffset[0] = plane3 * 8;
        spritelayout.planeoffset[1] = plane2 * 8;
        spritelayout.planeoffset[2] = plane1 * 8;
        spritelayout.planeoffset[3] = plane0 * 8;

        /* decode the graphics */
        Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region), spritelayout);
        if (Machine.gfx[gfx_index] == null) {
            return 1;
        }

        /* set the color information */
        Machine.gfx[gfx_index].colortable = new UShortArray(Machine.remapped_colortable);
        Machine.gfx[gfx_index].total_colors = Machine.drv.color_table_len / 16;

        K053245_memory_region = gfx_memory_region;
        K053245_gfx = Machine.gfx[gfx_index];
        K053245_callback = callback;
        K053244_rombank = 0;
        K053245_ram = new UBytePtr(0x800);
        if (K053245_ram == null) {
            return 1;
        }

        for (int i = 0; i < 0x800; i++) {
            K053245_ram.write(i, 0);//memset(K053245_ram,0,0x800);
        }
        return 0;
    }

    public static void K053245_vh_stop() {
        K053245_ram = null;
    }
    /*TODO*///
/*TODO*///READ_HANDLER( K053245_word_r )
/*TODO*///{
/*TODO*///	return READ_WORD(&K053245_ram[offset]);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K053245_word_w )
/*TODO*///{
/*TODO*///	COMBINE_WORD_MEM(&K053245_ram[offset],data);
/*TODO*///}
/*TODO*///
    public static ReadHandlerPtr K053245_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int shift = ((offset & 1) ^ 1) << 3;
            return (K053245_ram.READ_WORD(offset & ~1) >>> shift) & 0xff;
        }
    };

    public static WriteHandlerPtr K053245_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int shift = ((offset & 1) ^ 1) << 3;
            offset &= ~1;
            COMBINE_WORD_MEM(K053245_ram, offset, (0xff000000 >>> shift) | ((data & 0xff) << shift));
        }
    };

    public static ReadHandlerPtr K053244_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (K053244_readroms != 0 && offset >= 0x0c && offset < 0x10) {
                int addr;

                addr = 0x200000 * K053244_rombank + 4 * (K053244_romoffset & 0x7ffff) + ((offset & 3) ^ 1);
                addr &= memory_region_length(K053245_memory_region) - 1;
                return memory_region(K053245_memory_region).read(addr);
            } else {
                logerror("%04x: read from unknown 053244 address %x\n", cpu_get_pc(), offset);
                return 0;
            }
        }
    };
    public static WriteHandlerPtr K053244_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (offset == 0x00) {
                K053245_spriteoffsX = (K053245_spriteoffsX & 0x00ff) | (data << 8);
            } else if (offset == 0x01) {
                K053245_spriteoffsX = (K053245_spriteoffsX & 0xff00) | data;
            } else if (offset == 0x02) {
                K053245_spriteoffsY = (K053245_spriteoffsY & 0x00ff) | (data << 8);
            } else if (offset == 0x03) {
                K053245_spriteoffsY = (K053245_spriteoffsY & 0xff00) | data;
            } else if (offset == 0x05) {
                /*#ifdef MAME_DEBUG
                 if (data & 0xc8)
                 usrintf_showmessage("053244 reg 05 = %02x",data);
                 #endif*/
 /* bit 0/1 = flip screen */
                K053245_flipscreenX = data & 0x01;
                K053245_flipscreenY = data & 0x02;

                /* bit 2 = unknown, Parodius uses it */

 /* bit 4 = enable gfx ROM reading */
                K053244_readroms = data & 0x10;

                /* bit 5 = unknown, Rollergames uses it */
 /*#if VERBOSE
                 if (errorlog) fprintf(errorlog,"%04x: write %02x to 053244 address 5\n",cpu_get_pc(),data);
                 #endif*/
            } else if (offset >= 0x08 && offset < 0x0c) {
                offset = 8 * ((offset & 0x03) ^ 0x01);
                K053244_romoffset = (K053244_romoffset & ~(0xff << offset)) | (data << offset);
                return;
            } else {
                logerror("%04x: write %02x to unknown 053244 address %x\n", cpu_get_pc(), data, offset);
            }
        }
    };

    /*TODO*///
/*TODO*///void K053244_bankselect(int bank)   /* used by TMNT2 for ROM testing */
/*TODO*///{
/*TODO*///	K053244_rombank = bank;
/*TODO*///}
/*TODO*///
    public static void K053245_sprites_draw(osd_bitmap bitmap) {
        int NUM_SPRITES = 128;
        int offs, pri_code;
        int[] sortedlist = new int[NUM_SPRITES];

        for (offs = 0; offs < NUM_SPRITES; offs++) {
            sortedlist[offs] = -1;
        }

        /* prebuild a sorted table */
        for (offs = 0; offs < 0x800; offs += 16) {
            if ((K053245_ram.READ_WORD(offs) & 0x8000) != 0) {
                sortedlist[K053245_ram.READ_WORD(offs) & 0x007f] = offs;
            }
        }

        for (pri_code = NUM_SPRITES - 1; pri_code >= 0; pri_code--) {
            int ox, oy, size, w, h, x, y, flipx, flipy, mirrorx, mirrory, zoomx, zoomy;
            int[] color = new int[1];
            int[] code = new int[1];
            int[] pri = new int[1];

            offs = sortedlist[pri_code];
            if (offs == -1) {
                continue;
            }

            code[0] = K053245_ram.READ_WORD(offs + 0x02);
            code[0] = ((code[0] & 0xffe1) + ((code[0] & 0x0010) >> 2) + ((code[0] & 0x0008) << 1)
                    + ((code[0] & 0x0004) >> 1) + ((code[0] & 0x0002) << 2));
            color[0] = K053245_ram.READ_WORD(offs + 0x0c) & 0x00ff;
            pri[0] = 0;

            (K053245_callback).handler(code, color, pri);

            size = (K053245_ram.READ_WORD(offs) & 0x0f00) >> 8;

            w = 1 << (size & 0x03);
            h = 1 << ((size >> 2) & 0x03);

            /* zoom control:
		   0x40 = normal scale
		  <0x40 enlarge (0x20 = double size)
		  >0x40 reduce (0x80 = half size)
             */
            zoomy = K053245_ram.READ_WORD(offs + 0x08);
            if (zoomy > 0x2000) {
                continue;
            }
            if (zoomy != 0) {
                zoomy = (0x400000 + zoomy / 2) / zoomy;
            } else {
                zoomy = 2 * 0x400000;
            }
            if ((K053245_ram.READ_WORD(offs) & 0x4000) == 0) {
                zoomx = K053245_ram.READ_WORD(offs + 0x0a);
                if (zoomx > 0x2000) {
                    continue;
                }
                if (zoomx != 0) {
                    zoomx = (0x400000 + zoomx / 2) / zoomx;
                } //			else zoomx = 2 * 0x400000;
                else {
                    zoomx = zoomy;
                    /* workaround for TMNT2 */

                }
            } else {
                zoomx = zoomy;
            }

            ox = K053245_ram.READ_WORD(offs + 0x06) + K053245_spriteoffsX;
            oy = K053245_ram.READ_WORD(offs + 0x04);

            flipx = K053245_ram.READ_WORD(offs) & 0x1000;
            flipy = K053245_ram.READ_WORD(offs) & 0x2000;
            mirrorx = K053245_ram.READ_WORD(offs + 0x0c) & 0x0100;
            mirrory = K053245_ram.READ_WORD(offs + 0x0c) & 0x0200;

            if (K053245_flipscreenX != 0) {
                ox = 512 - ox;
                if (mirrorx == 0) {
                    flipx = NOT(flipx);
                }
            }
            if (K053245_flipscreenY != 0) {
                oy = -oy;
                if (mirrory == 0) {
                    flipy = NOT(flipy);
                }
            }

            ox = (ox + 0x5d) & 0x3ff;
            if (ox >= 768) {
                ox -= 1024;
            }
            oy = (-(oy + K053245_spriteoffsY + 0x07)) & 0x3ff;
            if (oy >= 640) {
                oy -= 1024;
            }

            /* the coordinates given are for the *center* of the sprite */
            ox -= (zoomx * w) >> 13;
            oy -= (zoomy * h) >> 13;

            for (y = 0; y < h; y++) {
                int sx, sy, zw, zh;

                sy = oy + ((zoomy * y + (1 << 11)) >> 12);
                zh = (oy + ((zoomy * (y + 1) + (1 << 11)) >> 12)) - sy;

                for (x = 0; x < w; x++) {
                    int c, fx, fy;

                    sx = ox + ((zoomx * x + (1 << 11)) >> 12);
                    zw = (ox + ((zoomx * (x + 1) + (1 << 11)) >> 12)) - sx;
                    c = code[0];
                    if (mirrorx != 0) {
                        if ((flipx == 0) ^ (2 * x < w)) {
                            /* mirror left/right */
                            c += (w - x - 1);
                            fx = 1;
                        } else {
                            c += x;
                            fx = 0;
                        }
                    } else {
                        if (flipx != 0) {
                            c += w - 1 - x;
                        } else {
                            c += x;
                        }
                        fx = flipx;
                    }
                    if (mirrory != 0) {
                        if ((flipy == 0) ^ (2 * y >= h)) {
                            /* mirror top/bottom */
                            c += 8 * (h - y - 1);
                            fy = 1;
                        } else {
                            c += 8 * y;
                            fy = 0;
                        }
                    } else {
                        if (flipy != 0) {
                            c += 8 * (h - 1 - y);
                        } else {
                            c += 8 * y;
                        }
                        fy = flipy;
                    }

                    /* the sprite can start at any point in the 8x8 grid, but it must stay */
 /* in a 64 entries window, wrapping around at the edges. The animation */
 /* at the end of the saloon level in SUnset Riders breaks otherwise. */
                    c = (c & 0x3f) | (code[0] & ~0x3f);

                    if (zoomx == 0x10000 && zoomy == 0x10000) {
                        /* hack to simulate shadow */
                        if ((K053245_ram.READ_WORD(offs + 0x0c) & 0x0080) != 0) {
                            int o = K053245_gfx.colortable.read(16 * color[0] + 15);
                            K053245_gfx.colortable.write(16 * color[0] + 15, palette_transparent_pen);
                            pdrawgfx(bitmap, K053245_gfx,
                                    c,
                                    color[0],
                                    fx, fy,
                                    sx, sy,
                                    Machine.visible_area, TRANSPARENCY_PENS, (cpu_getcurrentframe() & 1) != 0 ? 0x8001 : 0x0001, pri[0]);
                            K053245_gfx.colortable.write(16 * color[0] + 15, o);
                        } else {
                            pdrawgfx(bitmap, K053245_gfx,
                                    c,
                                    color[0],
                                    fx, fy,
                                    sx, sy,
                                    Machine.visible_area, TRANSPARENCY_PEN, 0, pri[0]);
                        }
                    } else {
                        /* hack to simulate shadow */
                        if ((K053245_ram.READ_WORD(offs + 0x0c) & 0x0080) != 0) {
                            int o = K053245_gfx.colortable.read(16 * color[0] + 15);
                            K053245_gfx.colortable.write(16 * color[0] + 15, palette_transparent_pen);
                            pdrawgfxzoom(bitmap, K053245_gfx,
                                    c,
                                    color[0],
                                    fx, fy,
                                    sx, sy,
                                    Machine.visible_area, TRANSPARENCY_PENS, (cpu_getcurrentframe() & 1) != 0 ? 0x8001 : 0x0001,
                                    (zw << 16) / 16, (zh << 16) / 16, pri[0]);
                            K053245_gfx.colortable.write(16 * color[0] + 15, o);
                        } else {
                            pdrawgfxzoom(bitmap, K053245_gfx,
                                    c,
                                    color[0],
                                    fx, fy,
                                    sx, sy,
                                    Machine.visible_area, TRANSPARENCY_PEN, 0,
                                    (zw << 16) / 16, (zh << 16) / 16, pri[0]);
                        }
                    }
                }
            }
        }
    }

    public static void K053245_mark_sprites_colors() {
        int offs, i;

        char[] palette_map = new char[512];

        memset(palette_map, 0, sizeof(palette_map));

        /* sprites */
        for (offs = 0x800 - 16; offs >= 0; offs -= 16) {
            if ((K053245_ram.READ_WORD(offs) & 0x8000) != 0) {
                int[] code = new int[1];
                int[] color = new int[1];
                int[] pri = new int[1];

                code[0] = K053245_ram.READ_WORD(offs + 0x02);
                code[0] = ((code[0] & 0xffe1) + ((code[0] & 0x0010) >> 2) + ((code[0] & 0x0008) << 1)
                        + ((code[0] & 0x0004) >> 1) + ((code[0] & 0x0002) << 2));
                color[0] = K053245_ram.READ_WORD(offs + 0x0c) & 0x00ff;
                pri[0] = 0;
                (K053245_callback).handler(code, color, pri);
                palette_map[color[0]] |= 0xffff;
            }
        }

        /* now build the final table */
        for (i = 0; i < 512; i++) {
            int usage = palette_map[i], j;
            if (usage != 0) {
                for (j = 1; j < 16; j++) {
                    if ((usage & (1 << j)) != 0) {
                        palette_used_colors.write(i * 16 + j, palette_used_colors.read(i * 16 + j) | PALETTE_COLOR_VISIBLE);
                    }
                }
            }
        }
    }

    static int K053247_memory_region, K053247_dx, K053247_dy;
    static GfxElement K053247_gfx;

    public static abstract interface K053247_callbackProcPtr {

        public abstract void handler(int[] code, int[] color, int[] priority);
    }
    static K053247_callbackProcPtr K053247_callback;
    static int K053246_OBJCHA_line;
    static int K053246_romoffset;
    static int K053246_flipscreenX, K053246_flipscreenY;
    static int K053246_spriteoffsX, K053246_spriteoffsY;
    static UBytePtr K053247_ram;
    static int K053246_irq_enabled;

    public static int K053247_vh_start(int gfx_memory_region, int dx, int dy, int plane0, int plane1, int plane2, int plane3, K053247_callbackProcPtr callback) {
        int gfx_index;
        GfxLayout spritelayout = new GfxLayout(
                16, 16,
                0, /* filled in later */
                4,
                new int[]{0, 0, 0, 0}, /* filled in later */
                new int[]{2 * 4, 3 * 4, 0 * 4, 1 * 4, 6 * 4, 7 * 4, 4 * 4, 5 * 4,
                    10 * 4, 11 * 4, 8 * 4, 9 * 4, 14 * 4, 15 * 4, 12 * 4, 13 * 4},
                new int[]{0 * 64, 1 * 64, 2 * 64, 3 * 64, 4 * 64, 5 * 64, 6 * 64, 7 * 64,
                    8 * 64, 9 * 64, 10 * 64, 11 * 64, 12 * 64, 13 * 64, 14 * 64, 15 * 64},
                128 * 8
        );


        /* find first empty slot to decode gfx */
        for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++) {
            if (Machine.gfx[gfx_index] == null) {
                break;
            }
        }
        if (gfx_index == MAX_GFX_ELEMENTS) {
            return 1;
        }

        /* tweak the structure for the number of tiles we have */
        spritelayout.total = memory_region_length(gfx_memory_region) / 128;
        spritelayout.planeoffset[0] = plane0;
        spritelayout.planeoffset[1] = plane1;
        spritelayout.planeoffset[2] = plane2;
        spritelayout.planeoffset[3] = plane3;

        /* decode the graphics */
        Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region), spritelayout);
        if (Machine.gfx[gfx_index] == null) {
            return 1;
        }

        /* set the color information */
        Machine.gfx[gfx_index].colortable = new UShortArray(Machine.remapped_colortable);
        Machine.gfx[gfx_index].total_colors = Machine.drv.color_table_len / 16;

        K053247_dx = dx;
        K053247_dy = dy;
        K053247_memory_region = gfx_memory_region;
        K053247_gfx = Machine.gfx[gfx_index];
        K053247_callback = callback;
        K053246_OBJCHA_line = CLEAR_LINE;
        K053247_ram = new UBytePtr(0x1000);

        memset(K053247_ram, 0, 0x1000);

        return 0;
    }

    public static void K053247_vh_stop() {
        K053247_ram = null;
    }

    public static ReadHandlerPtr K053247_word_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return K053247_ram.READ_WORD(offset);
        }
    };

    public static WriteHandlerPtr K053247_word_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            COMBINE_WORD_MEM(K053247_ram, offset, data);
        }
    };

    public static ReadHandlerPtr K053247_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int shift = ((offset & 1) ^ 1) << 3;
            return (K053247_ram.READ_WORD(offset & ~1) >>> shift) & 0xff;
        }
    };

    public static WriteHandlerPtr K053247_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            int shift = ((offset & 1) ^ 1) << 3;
            offset &= ~1;
            COMBINE_WORD_MEM(K053247_ram, offset, (0xff000000 >>> shift) | ((data & 0xff) << shift));
        }
    };

    public static ReadHandlerPtr K053246_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (K053246_OBJCHA_line == ASSERT_LINE) {
                int addr;

                addr = 2 * K053246_romoffset + ((offset & 1) ^ 1);
                addr &= memory_region_length(K053247_memory_region) - 1;

                return memory_region(K053247_memory_region).read(addr);
            } else {
                logerror("%04x: read from unknown 053244 address %x\n", cpu_get_pc(), offset);
                return 0;
            }
        }
    };

    public static WriteHandlerPtr K053246_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (offset == 0x00) {
                K053246_spriteoffsX = (K053246_spriteoffsX & 0x00ff) | (data << 8);
            } else if (offset == 0x01) {
                K053246_spriteoffsX = (K053246_spriteoffsX & 0xff00) | data;
            } else if (offset == 0x02) {
                K053246_spriteoffsY = (K053246_spriteoffsY & 0x00ff) | (data << 8);
            } else if (offset == 0x03) {
                K053246_spriteoffsY = (K053246_spriteoffsY & 0xff00) | data;
            } else if (offset == 0x05) {
                /* bit 0/1 = flip screen */
                K053246_flipscreenX = data & 0x01;
                K053246_flipscreenY = data & 0x02;

                /* bit 2 = unknown */

 /* bit 4 = interrupt enable */
                K053246_irq_enabled = data & 0x10;

                /* bit 5 = unknown */
                logerror("%04x: write %02x to 053246 address 5\n", cpu_get_pc(), data);
            } else if (offset >= 0x04 && offset < 0x08) /* only 4,6,7 - 5 is handled above */ {
                offset = 8 * (((offset & 0x03) ^ 0x01) - 1);
                K053246_romoffset = (K053246_romoffset & ~(0xff << offset)) | (data << offset);
                return;
            } else {
                logerror("%04x: write %02x to unknown 053246 address %x\n", cpu_get_pc(), data, offset);
            }
        }
    };

    public static ReadHandlerPtr K053246_word_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return K053246_r.handler(offset + 1) | (K053246_r.handler(offset) << 8);
        }
    };

    public static WriteHandlerPtr K053246_word_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if ((data & 0xff000000) == 0)
                    K053246_w.handler(offset,(data >> 8) & 0xff);
            if ((data & 0x00ff0000) == 0)
                    K053246_w.handler(offset + 1,data & 0xff);
        }
    };
    
    public static void K053246_set_OBJCHA_line(int state) {
        K053246_OBJCHA_line = state;
    }

    public static int K053246_is_IRQ_enabled() {
        return K053246_irq_enabled;
    }

    public static void K053247_sprites_draw(osd_bitmap bitmap) {
        int NUM_SPRITES = 256;
        int offs, pri_code;
        int[] sortedlist = new int[NUM_SPRITES];

        for (offs = 0; offs < NUM_SPRITES; offs++) {
            sortedlist[offs] = -1;
        }

        /* prebuild a sorted table */
        for (offs = 0; offs < 0x1000; offs += 16) {
//		if (READ_WORD(&K053247_ram[offs]) & 0x8000)
            sortedlist[K053247_ram.READ_WORD(offs) & 0x00ff] = offs;
        }

        for (pri_code = 0; pri_code < NUM_SPRITES; pri_code++) {
            int ox, oy, size, w, h, x, y, xa, ya, flipx, flipy, mirrorx, mirrory, zoomx, zoomy;
            int[] code = new int[1];
            int[] color = new int[1];
            int[] pri = new int[1];
            int xoffset[] = {0, 1, 4, 5, 16, 17, 20, 21};
            int yoffset[] = {0, 2, 8, 10, 32, 34, 40, 42};

            offs = sortedlist[pri_code];
            if (offs == -1) {
                continue;
            }

            if ((K053247_ram.READ_WORD(offs) & 0x8000) == 0) {
                continue;
            }

            code[0] = K053247_ram.READ_WORD(offs + 0x02);
            color[0] = K053247_ram.READ_WORD(offs + 0x0c);
            pri[0] = 0;

            K053247_callback.handler(code, color, pri);

            size = (K053247_ram.READ_WORD(offs) & 0x0f00) >> 8;

            w = 1 << (size & 0x03);
            h = 1 << ((size >> 2) & 0x03);

            /* the sprite can start at any point in the 8x8 grid. We have to */
 /* adjust the offsets to draw it correctly. Simpsons does this all the time. */
            xa = 0;
            ya = 0;
            if ((code[0] & 0x01) != 0) {
                xa += 1;
            }
            if ((code[0] & 0x02) != 0) {
                ya += 1;
            }
            if ((code[0] & 0x04) != 0) {
                xa += 2;
            }
            if ((code[0] & 0x08) != 0) {
                ya += 2;
            }
            if ((code[0] & 0x10) != 0) {
                xa += 4;
            }
            if ((code[0] & 0x20) != 0) {
                ya += 4;
            }
            code[0] &= ~0x3f;


            /* zoom control:
		   0x40 = normal scale
		  <0x40 enlarge (0x20 = double size)
		  >0x40 reduce (0x80 = half size)
             */
            zoomy = K053247_ram.READ_WORD(offs + 0x08);
            if (zoomy > 0x2000) {
                continue;
            }
            if (zoomy != 0) {
                zoomy = (0x400000 + zoomy / 2) / zoomy;
            } else {
                zoomy = 2 * 0x400000;
            }
            if ((K053247_ram.READ_WORD(offs) & 0x4000) == 0) {
                zoomx = K053247_ram.READ_WORD(offs + 0x0a);
                if (zoomx > 0x2000) {
                    continue;
                }
                if (zoomx != 0) {
                    zoomx = (0x400000 + zoomx / 2) / zoomx;
                } else {
                    zoomx = 2 * 0x400000;
                }
            } else {
                zoomx = zoomy;
            }

            ox = K053247_ram.READ_WORD(offs + 0x06);
            oy = K053247_ram.READ_WORD(offs + 0x04);

            flipx = K053247_ram.READ_WORD(offs) & 0x1000;
            flipy = K053247_ram.READ_WORD(offs) & 0x2000;
            mirrorx = K053247_ram.READ_WORD(offs + 0x0c) & 0x4000;
            mirrory = K053247_ram.READ_WORD(offs + 0x0c) & 0x8000;

            if (K053246_flipscreenX != 0) {
                ox = -ox;
                if (mirrorx == 0) {
                    flipx = NOT(flipx);
                }
            }
            if (K053246_flipscreenY != 0) {
                oy = -oy;
                if (mirrory == 0) {
                    flipy = NOT(flipy);
                }
            }

            ox = (K053247_dx + ox - K053246_spriteoffsX) & 0x3ff;
            if (ox >= 768) {
                ox -= 1024;
            }
            oy = (-(K053247_dy + oy + K053246_spriteoffsY)) & 0x3ff;
            if (oy >= 640) {
                oy -= 1024;
            }

            /* the coordinates given are for the *center* of the sprite */
            ox -= (zoomx * w) >> 13;
            oy -= (zoomy * h) >> 13;

            for (y = 0; y < h; y++) {
                int sx, sy, zw, zh;

                sy = oy + ((zoomy * y + (1 << 11)) >> 12);
                zh = (oy + ((zoomy * (y + 1) + (1 << 11)) >> 12)) - sy;

                for (x = 0; x < w; x++) {
                    int c, fx, fy;

                    sx = ox + ((zoomx * x + (1 << 11)) >> 12);
                    zw = (ox + ((zoomx * (x + 1) + (1 << 11)) >> 12)) - sx;
                    c = code[0];
                    if (mirrorx != 0) {
                        if ((flipx == 0) ^ (2 * x < w)) {
                            /* mirror left/right */
                            c += xoffset[(w - 1 - x + xa) & 7];
                            fx = 1;
                        } else {
                            c += xoffset[(x + xa) & 7];
                            fx = 0;
                        }
                    } else {
                        if (flipx != 0) {
                            c += xoffset[(w - 1 - x + xa) & 7];
                        } else {
                            c += xoffset[(x + xa) & 7];
                        }
                        fx = flipx;
                    }
                    if (mirrory != 0) {
                        if ((flipy == 0) ^ (2 * y >= h)) {
                            /* mirror top/bottom */
                            c += yoffset[(h - 1 - y + ya) & 7];
                            fy = 1;
                        } else {
                            c += yoffset[(y + ya) & 7];
                            fy = 0;
                        }
                    } else {
                        if (flipy != 0) {
                            c += yoffset[(h - 1 - y + ya) & 7];
                        } else {
                            c += yoffset[(y + ya) & 7];
                        }
                        fy = flipy;
                    }

                    if (zoomx == 0x10000 && zoomy == 0x10000) {
                        /* hack to simulate shadow */
                        if ((K053247_ram.READ_WORD(offs + 0x0c) & 0x0400) != 0) {
                            int o = K053247_gfx.colortable.read(16 * color[0] + 15);
                            K053247_gfx.colortable.write(16 * color[0] + 15, palette_transparent_pen);
                            pdrawgfx(bitmap, K053247_gfx,
                                    c,
                                    color[0],
                                    fx, fy,
                                    sx, sy,
                                    Machine.visible_area, TRANSPARENCY_PENS, (cpu_getcurrentframe() & 1) != 0 ? 0x8001 : 0x0001, pri[0]);
                            K053247_gfx.colortable.write(16 * color[0] + 15, o);
                        } else {
                            pdrawgfx(bitmap, K053247_gfx,
                                    c,
                                    color[0],
                                    fx, fy,
                                    sx, sy,
                                    Machine.visible_area, TRANSPARENCY_PEN, 0, pri[0]);
                        }
                    } else {
                        /* hack to simulate shadow */
                        if ((K053247_ram.READ_WORD(offs + 0x0c) & 0x0400) != 0) {
                            int o = K053247_gfx.colortable.read(16 * color[0] + 15);
                            K053247_gfx.colortable.write(16 * color[0] + 15, palette_transparent_pen);
                            pdrawgfxzoom(bitmap, K053247_gfx,
                                    c,
                                    color[0],
                                    fx, fy,
                                    sx, sy,
                                    Machine.visible_area, TRANSPARENCY_PENS, (cpu_getcurrentframe() & 1) != 0 ? 0x8001 : 0x0001,
                                    (zw << 16) / 16, (zh << 16) / 16, pri[0]);
                            K053247_gfx.colortable.write(16 * color[0] + 15, o);
                        } else {
                            pdrawgfxzoom(bitmap, K053247_gfx,
                                    c,
                                    color[0],
                                    fx, fy,
                                    sx, sy,
                                    Machine.visible_area, TRANSPARENCY_PEN, 0,
                                    (zw << 16) / 16, (zh << 16) / 16, pri[0]);
                        }
                    }

                    if (mirrory != 0 && h == 1) /* Simpsons shadows */ {
                        if (zoomx == 0x10000 && zoomy == 0x10000) {
                            /* hack to simulate shadow */
                            if ((K053247_ram.READ_WORD(offs + 0x0c) & 0x0400) != 0) {
                                int o = K053247_gfx.colortable.read(16 * color[0] + 15);
                                K053247_gfx.colortable.write(16 * color[0] + 15, palette_transparent_pen);
                                pdrawgfx(bitmap, K053247_gfx,
                                        c,
                                        color[0],
                                        fx, NOT(fy),
                                        sx, sy,
                                        Machine.visible_area, TRANSPARENCY_PENS, (cpu_getcurrentframe() & 1) != 0 ? 0x8001 : 0x0001, pri[0]);
                                K053247_gfx.colortable.write(16 * color[0] + 15, o);
                            } else {
                                pdrawgfx(bitmap, K053247_gfx,
                                        c,
                                        color[0],
                                        fx, NOT(fy),
                                        sx, sy,
                                        Machine.visible_area, TRANSPARENCY_PEN, 0, pri[0]);
                            }
                        } else {
                            /* hack to simulate shadow */
                            if ((K053247_ram.READ_WORD(offs + 0x0c) & 0x0400) != 0) {
                                int o = K053247_gfx.colortable.read(16 * color[0] + 15);
                                K053247_gfx.colortable.write(16 * color[0] + 15, palette_transparent_pen);
                                pdrawgfxzoom(bitmap, K053247_gfx,
                                        c,
                                        color[0],
                                        fx, NOT(fy),
                                        sx, sy,
                                        Machine.visible_area, TRANSPARENCY_PENS, (cpu_getcurrentframe() & 1) != 0 ? 0x8001 : 0x0001,
                                        (zw << 16) / 16, (zh << 16) / 16, pri[0]);
                                K053247_gfx.colortable.write(16 * color[0] + 15, o);
                            } else {
                                pdrawgfxzoom(bitmap, K053247_gfx,
                                        c,
                                        color[0],
                                        fx, NOT(fy),
                                        sx, sy,
                                        Machine.visible_area, TRANSPARENCY_PEN, 0,
                                        (zw << 16) / 16, (zh << 16) / 16, pri[0]);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void K053247_mark_sprites_colors() {
        int offs, i;

        char[] palette_map = new char[512];

        memset(palette_map, 0, sizeof(palette_map));

        /* sprites */
        for (offs = 0x1000 - 16; offs >= 0; offs -= 16) {
            if ((K053247_ram.READ_WORD(offs) & 0x8000) != 0) {
                int[] code = new int[1];
                int[] color = new int[1];
                int[] pri = new int[1];

                code[0] = K053247_ram.READ_WORD(offs + 0x02);
                color[0] = K053247_ram.READ_WORD(offs + 0x0c);
                pri[0] = 0;
                K053247_callback.handler(code, color, pri);
                palette_map[color[0]] |= 0xffff;
            }
        }

        /* now build the final table */
        for (i = 0; i < 512; i++) {
            int usage = palette_map[i], j;
            if (usage != 0) {
                for (j = 1; j < 16; j++) {
                    if ((usage & (1 << j)) != 0) {
                        palette_used_colors.write(i * 16 + j, palette_used_colors.read(i * 16 + j) | PALETTE_COLOR_VISIBLE);
                    }
                }
            }
        }
    }

    /*TODO*///
/*TODO*///#define MAX_K051316 3
/*TODO*///
/*TODO*///static int K051316_memory_region[MAX_K051316];
/*TODO*///static int K051316_gfxnum[MAX_K051316];
/*TODO*///static int K051316_wraparound[MAX_K051316];
/*TODO*///static int K051316_offset[MAX_K051316][2];
/*TODO*///static int K051316_bpp[MAX_K051316];
/*TODO*///static void (*K051316_callback[MAX_K051316])(int *code,int *color);
/*TODO*///static unsigned char *K051316_ram[MAX_K051316];
/*TODO*///static unsigned char K051316_ctrlram[MAX_K051316][16];
/*TODO*///static struct tilemap *K051316_tilemap[MAX_K051316];
/*TODO*///static int K051316_chip_selected;
/*TODO*///
/*TODO*///void K051316_vh_stop(int chip);
/*TODO*///
/*TODO*////***************************************************************************
/*TODO*///
/*TODO*///  Callbacks for the TileMap code
/*TODO*///
/*TODO*///***************************************************************************/
/*TODO*///
/*TODO*///static void K051316_preupdate(int chip)
/*TODO*///{
/*TODO*///	K051316_chip_selected = chip;
/*TODO*///}
/*TODO*///
/*TODO*///static void K051316_get_tile_info(int tile_index)
/*TODO*///{
/*TODO*///	int code = K051316_ram[K051316_chip_selected][tile_index];
/*TODO*///	int color = K051316_ram[K051316_chip_selected][tile_index + 0x400];
/*TODO*///
/*TODO*///	(*K051316_callback[K051316_chip_selected])(&code,&color);
/*TODO*///
/*TODO*///	SET_TILE_INFO(K051316_gfxnum[K051316_chip_selected],code,color);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///int K051316_vh_start(int chip, int gfx_memory_region,int bpp,
/*TODO*///		void (*callback)(int *code,int *color))
/*TODO*///{
/*TODO*///	int gfx_index;
/*TODO*///
/*TODO*///
/*TODO*///	/* find first empty slot to decode gfx */
/*TODO*///	for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++)
/*TODO*///		if (Machine->gfx[gfx_index] == 0)
/*TODO*///			break;
/*TODO*///	if (gfx_index == MAX_GFX_ELEMENTS)
/*TODO*///		return 1;
/*TODO*///
/*TODO*///	if (bpp == 4)
/*TODO*///	{
/*TODO*///		static struct GfxLayout charlayout =
/*TODO*///		{
/*TODO*///			16,16,
/*TODO*///			0,				/* filled in later */
/*TODO*///			4,
/*TODO*///			{ 0, 1, 2, 3 },
/*TODO*///			{ 0*4, 1*4, 2*4, 3*4, 4*4, 5*4, 6*4, 7*4,
/*TODO*///					8*4, 9*4, 10*4, 11*4, 12*4, 13*4, 14*4, 15*4 },
/*TODO*///			{ 0*64, 1*64, 2*64, 3*64, 4*64, 5*64, 6*64, 7*64,
/*TODO*///					8*64, 9*64, 10*64, 11*64, 12*64, 13*64, 14*64, 15*64 },
/*TODO*///			128*8
/*TODO*///		};
/*TODO*///
/*TODO*///
/*TODO*///		/* tweak the structure for the number of tiles we have */
/*TODO*///		charlayout.total = memory_region_length(gfx_memory_region) / 128;
/*TODO*///
/*TODO*///		/* decode the graphics */
/*TODO*///		Machine->gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region),&charlayout);
/*TODO*///	}
/*TODO*///	else if (bpp == 7)
/*TODO*///	{
/*TODO*///		static struct GfxLayout charlayout =
/*TODO*///		{
/*TODO*///			16,16,
/*TODO*///			0,				/* filled in later */
/*TODO*///			7,
/*TODO*///			{ 1, 2, 3, 4, 5, 6, 7 },
/*TODO*///			{ 0*8, 1*8, 2*8, 3*8, 4*8, 5*8, 6*8, 7*8,
/*TODO*///					8*8, 9*8, 10*8, 11*8, 12*8, 13*8, 14*8, 15*8 },
/*TODO*///			{ 0*128, 1*128, 2*128, 3*128, 4*128, 5*128, 6*128, 7*128,
/*TODO*///					8*128, 9*128, 10*128, 11*128, 12*128, 13*128, 14*128, 15*128 },
/*TODO*///			256*8
/*TODO*///		};
/*TODO*///
/*TODO*///
/*TODO*///		/* tweak the structure for the number of tiles we have */
/*TODO*///		charlayout.total = memory_region_length(gfx_memory_region) / 256;
/*TODO*///
/*TODO*///		/* decode the graphics */
/*TODO*///		Machine->gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region),&charlayout);
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///logerror("K051316_vh_start supports only 4 or 7 bpp\n");
/*TODO*///		return 1;
/*TODO*///	}
/*TODO*///
/*TODO*///	if (!Machine->gfx[gfx_index])
/*TODO*///		return 1;
/*TODO*///
/*TODO*///	/* set the color information */
/*TODO*///	Machine->gfx[gfx_index]->colortable = Machine->remapped_colortable;
/*TODO*///	Machine->gfx[gfx_index]->total_colors = Machine->drv->color_table_len / (1 << bpp);
/*TODO*///
/*TODO*///	K051316_memory_region[chip] = gfx_memory_region;
/*TODO*///	K051316_gfxnum[chip] = gfx_index;
/*TODO*///	K051316_bpp[chip] = bpp;
/*TODO*///	K051316_callback[chip] = callback;
/*TODO*///
/*TODO*///	K051316_tilemap[chip] = tilemap_create(K051316_get_tile_info,tilemap_scan_rows,TILEMAP_OPAQUE,16,16,32,32);
/*TODO*///
/*TODO*///	K051316_ram[chip] = malloc(0x800);
/*TODO*///
/*TODO*///	if (!K051316_ram[chip] || !K051316_tilemap[chip])
/*TODO*///	{
/*TODO*///		K051316_vh_stop(chip);
/*TODO*///		return 1;
/*TODO*///	}
/*TODO*///
/*TODO*///	tilemap_set_clip(K051316_tilemap[chip],0);
/*TODO*///
/*TODO*///	K051316_wraparound[chip] = 0;	/* default = no wraparound */
/*TODO*///	K051316_offset[chip][0] = K051316_offset[chip][1] = 0;
/*TODO*///
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
/*TODO*///int K051316_vh_start_0(int gfx_memory_region,int bpp,
/*TODO*///		void (*callback)(int *code,int *color))
/*TODO*///{
/*TODO*///	return K051316_vh_start(0,gfx_memory_region,bpp,callback);
/*TODO*///}
/*TODO*///
/*TODO*///int K051316_vh_start_1(int gfx_memory_region,int bpp,
/*TODO*///		void (*callback)(int *code,int *color))
/*TODO*///{
/*TODO*///	return K051316_vh_start(1,gfx_memory_region,bpp,callback);
/*TODO*///}
/*TODO*///
/*TODO*///int K051316_vh_start_2(int gfx_memory_region,int bpp,
/*TODO*///		void (*callback)(int *code,int *color))
/*TODO*///{
/*TODO*///	return K051316_vh_start(2,gfx_memory_region,bpp,callback);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void K051316_vh_stop(int chip)
/*TODO*///{
/*TODO*///	free(K051316_ram[chip]);
/*TODO*///	K051316_ram[chip] = 0;
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_vh_stop_0(void)
/*TODO*///{
/*TODO*///	K051316_vh_stop(0);
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_vh_stop_1(void)
/*TODO*///{
/*TODO*///	K051316_vh_stop(1);
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_vh_stop_2(void)
/*TODO*///{
/*TODO*///	K051316_vh_stop(2);
/*TODO*///}
/*TODO*///
/*TODO*///int K051316_r(int chip, int offset)
/*TODO*///{
/*TODO*///	return K051316_ram[chip][offset];
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051316_0_r )
/*TODO*///{
/*TODO*///	return K051316_r(0, offset);
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051316_1_r )
/*TODO*///{
/*TODO*///	return K051316_r(1, offset);
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051316_2_r )
/*TODO*///{
/*TODO*///	return K051316_r(2, offset);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///void K051316_w(int chip,int offset,int data)
/*TODO*///{
/*TODO*///	if (K051316_ram[chip][offset] != data)
/*TODO*///	{
/*TODO*///		K051316_ram[chip][offset] = data;
/*TODO*///		tilemap_mark_tile_dirty(K051316_tilemap[chip],offset & 0x3ff);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051316_0_w )
/*TODO*///{
/*TODO*///	K051316_w(0,offset,data);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051316_1_w )
/*TODO*///{
/*TODO*///	K051316_w(1,offset,data);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051316_2_w )
/*TODO*///{
/*TODO*///	K051316_w(2,offset,data);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///int K051316_rom_r(int chip, int offset)
/*TODO*///{
/*TODO*///	if ((K051316_ctrlram[chip][0x0e] & 0x01) == 0)
/*TODO*///	{
/*TODO*///		int addr;
/*TODO*///
/*TODO*///		addr = offset + (K051316_ctrlram[chip][0x0c] << 11) + (K051316_ctrlram[chip][0x0d] << 19);
/*TODO*///		if (K051316_bpp[chip] <= 4) addr /= 2;
/*TODO*///		addr &= memory_region_length(K051316_memory_region[chip])-1;
/*TODO*///
/*TODO*///#if 0
/*TODO*///	usrintf_showmessage("%04x: offset %04x addr %04x",cpu_get_pc(),offset,addr);
/*TODO*///#endif
/*TODO*///
/*TODO*///		return memory_region(K051316_memory_region[chip])[addr];
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///logerror("%04x: read 051316 ROM offset %04x but reg 0x0c bit 0 not clear\n",cpu_get_pc(),offset);
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051316_rom_0_r )
/*TODO*///{
/*TODO*///	return K051316_rom_r(0,offset);
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051316_rom_1_r )
/*TODO*///{
/*TODO*///	return K051316_rom_r(1,offset);
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051316_rom_2_r )
/*TODO*///{
/*TODO*///	return K051316_rom_r(2,offset);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///void K051316_ctrl_w(int chip,int offset,int data)
/*TODO*///{
/*TODO*///	K051316_ctrlram[chip][offset] = data;
/*TODO*///if (offset >= 0x0c) logerror("%04x: write %02x to 051316 reg %x\n",cpu_get_pc(),data,offset);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051316_ctrl_0_w )
/*TODO*///{
/*TODO*///	K051316_ctrl_w(0,offset,data);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051316_ctrl_1_w )
/*TODO*///{
/*TODO*///	K051316_ctrl_w(1,offset,data);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K051316_ctrl_2_w )
/*TODO*///{
/*TODO*///	K051316_ctrl_w(2,offset,data);
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_wraparound_enable(int chip, int status)
/*TODO*///{
/*TODO*///	K051316_wraparound[chip] = status;
/*TODO*///}
/*TODO*///
    public static void K051316_set_offset(int chip, int xoffs, int yoffs) {
        K051316_offset[chip][0] = xoffs;
        K051316_offset[chip][1] = yoffs;
    }

    /*TODO*///
/*TODO*///void K051316_tilemap_update(int chip)
/*TODO*///{
/*TODO*///	K051316_preupdate(chip);
/*TODO*///	tilemap_update(K051316_tilemap[chip]);
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_tilemap_update_0(void)
/*TODO*///{
/*TODO*///	K051316_tilemap_update(0);
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_tilemap_update_1(void)
/*TODO*///{
/*TODO*///	K051316_tilemap_update(1);
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_tilemap_update_2(void)
/*TODO*///{
/*TODO*///	K051316_tilemap_update(2);
/*TODO*///}
/*TODO*///
/*TODO*///
    public static void K051316_zoom_draw(int chip, osd_bitmap bitmap, int/*UINT32*/ priority) {
        long startx, starty;
        int incxx, incxy, incyx, incyy;
        osd_bitmap srcbitmap = K051316_tilemap[chip].pixmap;

        startx = ((256 * ((short) (256 * K051316_ctrlram[chip][0x00] + K051316_ctrlram[chip][0x01])))) & 0xFFFFFFFFL;
        incxx = (short) (256 * K051316_ctrlram[chip][0x02] + K051316_ctrlram[chip][0x03]);
        incyx = (short) (256 * K051316_ctrlram[chip][0x04] + K051316_ctrlram[chip][0x05]);
        starty = ((256 * ((short) (256 * K051316_ctrlram[chip][0x06] + K051316_ctrlram[chip][0x07])))) & 0xFFFFFFFFL;
        incxy = (short) (256 * K051316_ctrlram[chip][0x08] + K051316_ctrlram[chip][0x09]);
        incyy = (short) (256 * K051316_ctrlram[chip][0x0a] + K051316_ctrlram[chip][0x0b]);

        startx = (startx - (16 + K051316_offset[chip][1]) * incyx) & 0xFFFFFFFFL;
        starty = (starty - (16 + K051316_offset[chip][1]) * incyy) & 0xFFFFFFFFL;

        startx = (startx - (89 + K051316_offset[chip][0]) * incxx) & 0xFFFFFFFFL;
        starty = (starty - (89 + K051316_offset[chip][0]) * incxy) & 0xFFFFFFFFL;

        copyrozbitmap(bitmap, srcbitmap, startx << 5, starty << 5,
                incxx << 5, incxy << 5, incyx << 5, incyy << 5, K051316_wraparound[chip],
                Machine.visible_area, TRANSPARENCY_PEN, palette_transparent_pen, priority);
    }
    /*TODO*///
/*TODO*///void K051316_zoom_draw_0(struct osd_bitmap *bitmap,UINT32 priority)
/*TODO*///{
/*TODO*///	K051316_zoom_draw(0,bitmap,priority);
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_zoom_draw_1(struct osd_bitmap *bitmap,UINT32 priority)
/*TODO*///{
/*TODO*///	K051316_zoom_draw(1,bitmap,priority);
/*TODO*///}
/*TODO*///
/*TODO*///void K051316_zoom_draw_2(struct osd_bitmap *bitmap,UINT32 priority)
/*TODO*///{
/*TODO*///	K051316_zoom_draw(2,bitmap,priority);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*///
    static /*unsigned char*/ int[] u8_K053251_ram = new int[16];
    static int[] K053251_palette_index = new int[5];

    public static WriteHandlerPtr K053251_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            data &= 0x3f;

            if (u8_K053251_ram[offset] != data) {
                u8_K053251_ram[offset] = data & 0xFF;
                if (offset == 9) {
                    /* palette base index */
                    K053251_palette_index[0] = 32 * ((data >> 0) & 0x03);
                    K053251_palette_index[1] = 32 * ((data >> 2) & 0x03);
                    K053251_palette_index[2] = 32 * ((data >> 4) & 0x03);
                    tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
                } else if (offset == 10) {
                    /* palette base index */
                    K053251_palette_index[3] = 16 * ((data >> 0) & 0x07);
                    K053251_palette_index[4] = 16 * ((data >> 3) & 0x07);
                    tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
                }
            }
        }
    };

    public static int K053251_get_priority(int ci) {
        return u8_K053251_ram[ci];
    }

    public static int K053251_get_palette_index(int ci) {
        return K053251_palette_index[ci];
    }

    static UBytePtr K054000_ram = new UBytePtr(0x20);

    public static WriteHandlerPtr collision_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
        }
    };

    public static WriteHandlerPtr K054000_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            //#if VERBOSE
            //logerror("%04x: write %02x to 054000 address %02x\n",cpu_get_pc(),data,offset);
            //#endif
            K054000_ram.write(offset, data);
        }
    };

    public static ReadHandlerPtr K054000_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int Acx, Acy, Aax, Aay;
            int Bcx, Bcy, Bax, Bay;
            //#if VERBOSE
            //logerror("%04x: read 054000 address %02x\n",cpu_get_pc(),offset);
            //#endif
            if (offset != 0x18) {
                return 0;
            }

            Acx = (K054000_ram.read(0x01) << 16) | (K054000_ram.read(0x02) << 8) | K054000_ram.read(0x03);
            Acy = (K054000_ram.read(0x09) << 16) | (K054000_ram.read(0x0a) << 8) | K054000_ram.read(0x0b);
            /* TODO: this is a hack to make thndrx2 pass the startup check. It is certainly wrong. */
            if (K054000_ram.read(0x04) == 0xff) {
                Acx += 3;
            }
            if (K054000_ram.read(0x0c) == 0xff) {
                Acy += 3;
            }
            Aax = K054000_ram.read(0x06) + 1;
            Aay = K054000_ram.read(0x07) + 1;

            Bcx = (K054000_ram.read(0x15) << 16) | (K054000_ram.read(0x16) << 8) | K054000_ram.read(0x17);
            Bcy = (K054000_ram.read(0x11) << 16) | (K054000_ram.read(0x12) << 8) | K054000_ram.read(0x13);
            Bax = K054000_ram.read(0x0e) + 1;
            Bay = K054000_ram.read(0x0f) + 1;

            if (Acx + Aax < Bcx - Bax) {
                return 1;
            }

            if (Bcx + Bax < Acx - Aax) {
                return 1;
            }

            if (Acy + Aay < Bcy - Bay) {
                return 1;
            }

            if (Bcy + Bay < Acy - Aay) {
                return 1;
            }

            return 0;
        }
    };
    /*TODO*///
/*TODO*///
/*TODO*///static unsigned char K051733_ram[0x20];
/*TODO*///
/*TODO*///WRITE_HANDLER( K051733_w )
/*TODO*///{
/*TODO*///#if VERBOSE
/*TODO*///logerror("%04x: write %02x to 051733 address %02x\n",cpu_get_pc(),data,offset);
/*TODO*///#endif
/*TODO*///
/*TODO*///	K051733_ram[offset] = data;
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K051733_r )
/*TODO*///{
/*TODO*///	int op1 = (K051733_ram[0x00] << 8) | K051733_ram[0x01];
/*TODO*///	int op2 = (K051733_ram[0x02] << 8) | K051733_ram[0x03];
/*TODO*///
/*TODO*///	int rad = (K051733_ram[0x06] << 8) | K051733_ram[0x07];
/*TODO*///	int yobj1c = (K051733_ram[0x08] << 8) | K051733_ram[0x09];
/*TODO*///	int xobj1c = (K051733_ram[0x0a] << 8) | K051733_ram[0x0b];
/*TODO*///	int yobj2c = (K051733_ram[0x0c] << 8) | K051733_ram[0x0d];
/*TODO*///	int xobj2c = (K051733_ram[0x0e] << 8) | K051733_ram[0x0f];
/*TODO*///
/*TODO*///#if VERBOSE
/*TODO*///logerror("%04x: read 051733 address %02x\n",cpu_get_pc(),offset);
/*TODO*///#endif
/*TODO*///
/*TODO*///	switch(offset){
/*TODO*///		case 0x00:
/*TODO*///			if (op2) return	((op1/op2) >> 8);
/*TODO*///			else return 0xff;
/*TODO*///		case 0x01:
/*TODO*///			if (op2) return	op1/op2;
/*TODO*///			else return 0xff;
/*TODO*///
/*TODO*///		/* this is completely unverified */
/*TODO*///		case 0x02:
/*TODO*///			if (op2) return	((op1%op2) >> 8);
/*TODO*///			else return 0xff;
/*TODO*///		case 0x03:
/*TODO*///			if (op2) return	op1%op2;
/*TODO*///			else return 0xff;
/*TODO*///
/*TODO*///		case 0x07:{
/*TODO*///			if (xobj1c + rad < xobj2c - rad)
/*TODO*///				return 0x80;
/*TODO*///
/*TODO*///			if (xobj2c + rad < xobj1c - rad)
/*TODO*///				return 0x80;
/*TODO*///
/*TODO*///			if (yobj1c + rad < yobj2c - rad)
/*TODO*///				return 0x80;
/*TODO*///
/*TODO*///			if (yobj2c + rad < yobj1c - rad)
/*TODO*///				return 0x80;
/*TODO*///
/*TODO*///			return 0;
/*TODO*///		}
/*TODO*///		default:
/*TODO*///			return K051733_ram[offset];
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///static struct tilemap *K054157_tilemap[4], *K054157_cur_tilemap;
/*TODO*///static int K054157_rambank, K054157_cur_rambank, K054157_rombank, K054157_cur_rombank, K054157_romnbbanks;
/*TODO*///static int K054157_cur_layer, K054157_gfxnum, K054157_memory_region, K054157_cur_offset, K054157_control0;
/*TODO*///static unsigned char *K054157_rambase, *K054157_cur_lbase, *K054157_cur_rambase, *K054157_rombase;
/*TODO*///static int K054157_scrollx[4], K054157_scrolly[4];
/*TODO*///static void (*K054157_callback)(int, int *, int *);
/*TODO*///static int (*K054157_scrolld)[4][2];
/*TODO*///
/*TODO*///static void K054157_get_tile_info(int tile_index)
/*TODO*///{
/*TODO*///	unsigned char *addr;
/*TODO*///	int attr, code;
/*TODO*///	if(tile_index < 64*32)
/*TODO*///		addr = K054157_cur_lbase + (tile_index<<2);
/*TODO*///	else
/*TODO*///		addr = K054157_cur_lbase + (tile_index<<2) + 0x4000 - 64*32*4;
/*TODO*///
/*TODO*///	attr = READ_WORD(addr);
/*TODO*///	code = READ_WORD(addr+2);
/*TODO*///	tile_info.flags = 0;
/*TODO*///
/*TODO*///	(*K054157_callback)(K054157_cur_layer, &code, &attr);
/*TODO*///	SET_TILE_INFO (K054157_gfxnum, code, attr);
/*TODO*///}
/*TODO*///
/*TODO*///void K054157_vh_stop(void)
/*TODO*///{
/*TODO*///	if(K054157_rambase) {
/*TODO*///		free(K054157_rambase);
/*TODO*///		K054157_rambase = 0;
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///int K054157_vh_start(int rambank, int rombank, int gfx_memory_region, int (*scrolld)[4][2], int plane0,int plane1,int plane2,int plane3, void (*callback)(int, int *, int *))
/*TODO*///{
/*TODO*///	int gfx_index;
/*TODO*///	static struct GfxLayout charlayout =
/*TODO*///	{
/*TODO*///		8, 8,
/*TODO*///		0,				/* filled in later */
/*TODO*///		4,
/*TODO*///		{ 0, 0, 0, 0 },	/* filled in later */
/*TODO*///		{ 2*4, 3*4, 0*4, 1*4, 6*4, 7*4, 4*4, 5*4 },
/*TODO*///		{ 0*8*4, 1*8*4, 2*8*4, 3*8*4, 4*8*4, 5*8*4, 6*8*4, 7*8*4 },
/*TODO*///		8*8*4
/*TODO*///	};
/*TODO*///
/*TODO*///	/* find first empty slot to decode gfx */
/*TODO*///	for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++)
/*TODO*///		if (Machine->gfx[gfx_index] == 0)
/*TODO*///			break;
/*TODO*///	if (gfx_index == MAX_GFX_ELEMENTS)
/*TODO*///		return 1;
/*TODO*///
/*TODO*///	/* tweak the structure for the number of tiles we have */
/*TODO*///	charlayout.total = memory_region_length(gfx_memory_region) / (8*4);
/*TODO*///	charlayout.planeoffset[0] = plane0;
/*TODO*///	charlayout.planeoffset[1] = plane1;
/*TODO*///	charlayout.planeoffset[2] = plane2;
/*TODO*///	charlayout.planeoffset[3] = plane3;
/*TODO*///
/*TODO*///	/* decode the graphics */
/*TODO*///	Machine->gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region), &charlayout);
/*TODO*///	if (!Machine->gfx[gfx_index])
/*TODO*///		return 1;
/*TODO*///
/*TODO*///	/* set the color information */
/*TODO*///	Machine->gfx[gfx_index]->colortable = Machine->remapped_colortable;
/*TODO*///	Machine->gfx[gfx_index]->total_colors = Machine->drv->color_table_len / 16;
/*TODO*///
/*TODO*///	K054157_scrolld = scrolld;
/*TODO*///	K054157_memory_region = gfx_memory_region;
/*TODO*///	K054157_gfxnum = gfx_index;
/*TODO*///	K054157_callback = callback;
/*TODO*///
/*TODO*///	K054157_tilemap[0] = tilemap_create(K054157_get_tile_info, tilemap_scan_rows,
/*TODO*///										TILEMAP_TRANSPARENT, 8, 8, 64, 64);
/*TODO*///	K054157_tilemap[1] = tilemap_create(K054157_get_tile_info, tilemap_scan_rows,
/*TODO*///										TILEMAP_TRANSPARENT, 8, 8, 64, 64);
/*TODO*///	K054157_tilemap[2] = tilemap_create(K054157_get_tile_info, tilemap_scan_rows,
/*TODO*///										TILEMAP_TRANSPARENT, 8, 8, 64, 64);
/*TODO*///	K054157_tilemap[3] = tilemap_create(K054157_get_tile_info, tilemap_scan_rows,
/*TODO*///										TILEMAP_TRANSPARENT, 8, 8, 64, 64);
/*TODO*///
/*TODO*///	K054157_rambase = malloc(0x10000);
/*TODO*///
/*TODO*///	if(!K054157_rambase || !K054157_tilemap[0] || !K054157_tilemap[1] || !K054157_tilemap[2] || !K054157_tilemap[3]) {
/*TODO*///		K054157_vh_stop();
/*TODO*///		return 1;
/*TODO*///	}
/*TODO*///
/*TODO*///	memset(K054157_rambase, 0, 0x10000);
/*TODO*///
/*TODO*///	K054157_tilemap[0]->transparent_pen = 0;
/*TODO*///	K054157_tilemap[1]->transparent_pen = 0;
/*TODO*///	K054157_tilemap[2]->transparent_pen = 0;
/*TODO*///	K054157_tilemap[3]->transparent_pen = 0;
/*TODO*///
/*TODO*///	K054157_rambank = rambank;
/*TODO*///	K054157_cur_rambank = 0;
/*TODO*///	K054157_cur_rambase = K054157_rambase;
/*TODO*///	K054157_cur_tilemap = K054157_tilemap[0];
/*TODO*///	K054157_cur_offset = 0;
/*TODO*///	cpu_setbank(K054157_rambank, K054157_cur_rambase);
/*TODO*///
/*TODO*///	K054157_rombank = rombank;
/*TODO*///	K054157_cur_rombank = 0;
/*TODO*///	K054157_rombase = memory_region(gfx_memory_region);
/*TODO*///	K054157_romnbbanks = memory_region_length(gfx_memory_region)/0x2000;
/*TODO*///	cpu_setbank(K054157_rombank, K054157_rombase);
/*TODO*///
/*TODO*///	K054157_control0 = 0;
/*TODO*///
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///READ_HANDLER( K054157_ram_word_r )
/*TODO*///{
/*TODO*///	unsigned char *adr = K054157_cur_rambase + offset;
/*TODO*///	return READ_WORD(adr);
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K054157_ram_word_w )
/*TODO*///{
/*TODO*///	unsigned char *adr = K054157_cur_rambase + offset;
/*TODO*///	int old = READ_WORD(adr);
/*TODO*///	COMBINE_WORD_MEM(adr, data);
/*TODO*///
/*TODO*///	if(READ_WORD(adr) != old)
/*TODO*///		tilemap_mark_tile_dirty(K054157_cur_tilemap, offset/4 + K054157_cur_offset);
/*TODO*///}
/*TODO*///
/*TODO*///READ_HANDLER( K054157_r )
/*TODO*///{
/*TODO*///	logerror("K054157: unhandled read(%02x), pc=%08x\n", offset, cpu_get_pc());
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
/*TODO*///static void K054157_reset_scroll(void)
/*TODO*///{
/*TODO*///	int i;
/*TODO*///	for(i=0; i<4; i++) {
/*TODO*///		tilemap_set_scrollx(K054157_tilemap[i], 0, K054157_scrollx[i] + (K054157_control0 & 0x20 ? K054157_scrolld[1][i][0] : K054157_scrolld[0][i][0]));
/*TODO*///		tilemap_set_scrolly(K054157_tilemap[i], 0, K054157_control0 & 0x20 ? K054157_scrolly[i] + K054157_scrolld[1][i][1] : K054157_scrolly[i] + K054157_scrolld[0][i][1]);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///static void K054157_set_scrolly(int plane, int pos)
/*TODO*///{
/*TODO*///	if(K054157_scrolly[plane] != pos) {
/*TODO*///		K054157_scrolly[plane] = pos;
/*TODO*///		tilemap_set_scrolly(K054157_tilemap[plane], 0, K054157_control0 & 0x20 ? pos + K054157_scrolld[1][plane][1] : pos + K054157_scrolld[0][plane][1]);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///static void K054157_set_scrollx(int plane, int pos)
/*TODO*///{
/*TODO*///	if(K054157_scrollx[plane] != pos) {
/*TODO*///		K054157_scrollx[plane] = pos;
/*TODO*///		tilemap_set_scrollx(K054157_tilemap[plane], 0, pos + (K054157_control0 & 0x20 ? K054157_scrolld[1][plane][0] : K054157_scrolld[0][plane][0]));
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///WRITE_HANDLER( K054157_w )
/*TODO*///{
/*TODO*///	switch(offset) {
/*TODO*///	case 0x00:
/*TODO*///		data &= 0xff;
/*TODO*///		if(K054157_control0 != data) {
/*TODO*///			int flip;
/*TODO*///			K054157_control0 = data;
/*TODO*///			flip = 0;
/*TODO*///			if(K054157_control0 & 0x20)
/*TODO*///				flip |= TILEMAP_FLIPY;
/*TODO*///			if(K054157_control0 & 0x10)
/*TODO*///				flip |= TILEMAP_FLIPX;
/*TODO*///			tilemap_set_flip(K054157_tilemap[0], flip);
/*TODO*///			tilemap_set_flip(K054157_tilemap[1], flip);
/*TODO*///			tilemap_set_flip(K054157_tilemap[2], flip);
/*TODO*///			tilemap_set_flip(K054157_tilemap[3], flip);
/*TODO*///			K054157_reset_scroll();
/*TODO*///		}
/*TODO*///		break;
/*TODO*///	case 0x20:
/*TODO*///		K054157_set_scrolly(3, data);
/*TODO*///		break;
/*TODO*///	case 0x22:
/*TODO*///		K054157_set_scrolly(0, data);
/*TODO*///		break;
/*TODO*///	case 0x24:
/*TODO*///		K054157_set_scrolly(2, data);
/*TODO*///		break;
/*TODO*///	case 0x26:
/*TODO*///		K054157_set_scrolly(1, data);
/*TODO*///		break;
/*TODO*///	case 0x28:
/*TODO*///		K054157_set_scrollx(3, data);
/*TODO*///		break;
/*TODO*///	case 0x2a:
/*TODO*///		K054157_set_scrollx(0, data);
/*TODO*///		break;
/*TODO*///	case 0x2c:
/*TODO*///		K054157_set_scrollx(2, data);
/*TODO*///		break;
/*TODO*///	case 0x2e:
/*TODO*///		K054157_set_scrollx(1, data);
/*TODO*///		break;
/*TODO*///	case 0x32: {
/*TODO*///		data &= 0xff;
/*TODO*///		if(data & 0xe6)
/*TODO*///			logerror("Graphic bankswitching to unknown bank %02x (pc=%08x)\n", data, cpu_get_pc());
/*TODO*///
/*TODO*///		K054157_cur_rambank = data;
/*TODO*///		K054157_cur_rambase = K054157_rambase + (((data>>2) & 6) | (data & 1))*0x2000;
/*TODO*///		K054157_cur_tilemap = K054157_tilemap[((data>>3) & 2) | (data & 1)];
/*TODO*///		K054157_cur_offset = data & 8 ? 64*32 : 0;
/*TODO*///
/*TODO*///		cpu_setbank(K054157_rambank, K054157_cur_rambase);
/*TODO*///		break;
/*TODO*///	}
/*TODO*///	case 0x34: {
/*TODO*///		K054157_cur_rombank = data % K054157_romnbbanks;
/*TODO*///		cpu_setbank(K054157_rombank, K054157_rombase + 0x2000*K054157_cur_rombank);
/*TODO*///		break;
/*TODO*///	}
/*TODO*///	default:
/*TODO*///		logerror("K054157: unhandled write(%02x, %04x), pc=%08x\n", offset, data & 0xffff, cpu_get_pc());
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///void K054157_tilemap_update(void)
/*TODO*///{
/*TODO*///	for(K054157_cur_layer=0; K054157_cur_layer<4; K054157_cur_layer++) {
/*TODO*///		K054157_cur_lbase = K054157_rambase +
/*TODO*///			(K054157_cur_layer & 1 ? 0x2000 : 0) +
/*TODO*///			(K054157_cur_layer & 2 ? 0x8000 : 0);
/*TODO*///		tilemap_update(K054157_tilemap[K054157_cur_layer]);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///void K054157_tilemap_draw(struct osd_bitmap *bitmap, int num, int flags)
/*TODO*///{
/*TODO*///	tilemap_draw(bitmap, K054157_tilemap[num], flags);
/*TODO*///}
/*TODO*///    
}
