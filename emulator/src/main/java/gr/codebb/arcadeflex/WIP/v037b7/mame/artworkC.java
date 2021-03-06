/*
 * ported to 0.37b7 
 */
package gr.codebb.arcadeflex.WIP.v037b7.mame;

import static gr.codebb.arcadeflex.WIP.v037b7.mame.artworkH.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.options;
import static arcadeflex.v037b7.mame.osdependH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_change_color;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.palette_recalc;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.pngH.png_info;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.common.libc.cstring.*;
import static arcadeflex.v037b7.mame.driverH.ORIENTATION_SWAP_XY;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static arcadeflex.v037b7.mame.driverH.VIDEO_MODIFIES_PALETTE;
import static gr.codebb.arcadeflex.old.arcadeflex.video.*;
import static arcadeflex.v037b7.mame.driverH.VIDEO_TYPE_VECTOR;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.copyrozbitmap;

public class artworkC {

    /* the backdrop instance */
    public static artwork_info artwork_backdrop = null;

    /* the overlay instance */
    public static artwork_info artwork_overlay = null;

    public static osd_bitmap artwork_real_scrbitmap = null;

    static void brightness_update(artwork_info a) {
        int i;
        char[] r = new char[1];
        char[] g = new char[1];
        char[] b = new char[1];
        char[] pens = Machine.pens;

        /* Calculate brightness of all colors */
        if (Machine.scrbitmap.depth == 8) {
            i = Math.min(256, Machine.drv.total_colors);
        } else {
            i = Math.min(32768, Machine.drv.total_colors);
        }

        while (--i >= 0) {
            osd_get_pen(pens[i], r, g, b);
            a.u8_brightness[pens[i]] = (char) (((222 * r[0] + 707 * g[0] + 71 * b[0]) / 1000) & 0xFF);
        }
    }

    /*TODO*///
/*TODO*///static void RGBtoHSV( float r, float g, float b, float *h, float *s, float *v )
/*TODO*///{
/*TODO*///	float min, max, delta;
/*TODO*///
/*TODO*///	min = MIN( r, MIN( g, b ));
/*TODO*///	max = MAX( r, MAX( g, b ));
/*TODO*///	*v = max;
/*TODO*///
/*TODO*///	delta = max - min;
/*TODO*///
/*TODO*///	if( delta > 0  )
/*TODO*///		*s = delta / max;
/*TODO*///	else {
/*TODO*///		*s = 0;
/*TODO*///		*h = 0;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	if( r == max )
/*TODO*///		*h = ( g - b ) / delta;
/*TODO*///	else if( g == max )
/*TODO*///		*h = 2 + ( b - r ) / delta;
/*TODO*///	else
/*TODO*///		*h = 4 + ( r - g ) / delta;
/*TODO*///
/*TODO*///	*h *= 60;
/*TODO*///	if( *h < 0 )
/*TODO*///		*h += 360;
/*TODO*///}
/*TODO*///
/*TODO*///static void HSVtoRGB( float *r, float *g, float *b, float h, float s, float v )
/*TODO*///{
/*TODO*///	int i;
/*TODO*///	float f, p, q, t;
/*TODO*///
/*TODO*///	if( s == 0 ) {
/*TODO*///		*r = *g = *b = v;
/*TODO*///		return;
/*TODO*///	}
/*TODO*///
/*TODO*///	h /= 60;
/*TODO*///	i = h;
/*TODO*///	f = h - i;
/*TODO*///	p = v * ( 1 - s );
/*TODO*///	q = v * ( 1 - s * f );
/*TODO*///	t = v * ( 1 - s * ( 1 - f ) );
/*TODO*///
/*TODO*///	switch( i ) {
/*TODO*///	case 0: *r = v; *g = t; *b = p; break;
/*TODO*///	case 1: *r = q; *g = v; *b = p; break;
/*TODO*///	case 2: *r = p; *g = v; *b = t; break;
/*TODO*///	case 3: *r = p; *g = q; *b = v; break;
/*TODO*///	case 4: *r = t; *g = p; *b = v; break;
/*TODO*///	default: *r = v; *g = p; *b = q; break;
/*TODO*///	}
/*TODO*///
/*TODO*///}
/*TODO*///
/*TODO*///static UINT8 *create_15bit_palette ( void )
/*TODO*///{
/*TODO*///	int r, g, b;
/*TODO*///	UINT8 *palette, *tmp;
/*TODO*///
/*TODO*///	if ((palette = malloc (3 * 32768)) == 0)
/*TODO*///		return 0;
/*TODO*///
/*TODO*///	tmp = palette;
/*TODO*///	for (r = 0; r < 32; r++)
/*TODO*///		for (g = 0; g < 32; g++)
/*TODO*///			for (b = 0; b < 32; b++)
/*TODO*///			{
/*TODO*///				*tmp++ = (r << 3) | (r >> 2);
/*TODO*///				*tmp++ = (g << 3) | (g >> 2);
/*TODO*///				*tmp++ = (b << 3) | (b >> 2);
/*TODO*///			}
/*TODO*///	return palette;
/*TODO*///}
/*TODO*///
    static int get_new_pen(artwork_info a, int r, int g, int b, int alpha) {
        int pen;

        /* look if the color is already in the palette */
        if (Machine.scrbitmap.depth == 8) {
            pen = 0;
            while ((pen < a.num_pens_used)
                    && ((r != a.u8_orig_palette[3 * pen])
                    || (g != a.u8_orig_palette[3 * pen + 1])
                    || (b != a.u8_orig_palette[3 * pen + 2])
                    || ((alpha < 255) && (alpha != a.u8_transparency[pen])))) {
                pen++;
            }

            if (pen == a.num_pens_used) {
                a.u8_orig_palette[3 * pen] = (char) (r & 0xFF);
                a.u8_orig_palette[3 * pen + 1] = (char) (g & 0xFF);
                a.u8_orig_palette[3 * pen + 2] = (char) (b & 0xFF);
                a.num_pens_used++;
                if (alpha < 255) {
                    a.u8_transparency[pen] = (char) (alpha & 0xFF);
                    a.num_pens_trans++;
                }
            }
        } else {
            throw new UnsupportedOperationException("Unsupported");
            /*TODO*///		pen = ((r & 0xf8) << 7) | ((g & 0xf8) << 2) | (b >> 3);
        }

        return pen;
    }

    static void merge_cmy(artwork_info a, osd_bitmap source, osd_bitmap source_alpha, int sx, int sy) {
        int c1, c2, m1, m2, y1, y2, pen1, pen2, max, alpha;
        int x, y, w, h;
        //osd_bitmap dest, dest_alpha;

        //dest = a.orig_artwork;
        //dest_alpha = a.alpha;

        if ((Machine.orientation & ORIENTATION_SWAP_XY) != 0) {
            w = source.height;
            h = source.width;
        } else {
            h = source.height;
            w = source.width;
        }

        for (y = 0; y < h; y++) {
            for (x = 0; x < w; x++) {
                pen1 = read_pixel.handler(a.orig_artwork, sx + x, sy + y);

                c1 = 0xff - a.u8_orig_palette[3 * pen1];
                m1 = 0xff - a.u8_orig_palette[3 * pen1 + 1];
                y1 = 0xff - a.u8_orig_palette[3 * pen1 + 2];

                pen2 = read_pixel.handler(source, x, y);
                c2 = 0xff - a.u8_orig_palette[3 * pen2] + c1;
                m2 = 0xff - a.u8_orig_palette[3 * pen2 + 1] + m1;
                y2 = 0xff - a.u8_orig_palette[3 * pen2 + 2] + y1;

                max = Math.max(c2, Math.max(m2, y2));
                if (max > 0xff) {
                    c2 = (c2 * 0xf8) / max;
                    m2 = (m2 * 0xf8) / max;
                    y2 = (y2 * 0xf8) / max;
                }

                alpha = Math.min(0xff, read_pixel.handler(source_alpha, x, y)
                        + read_pixel.handler(a.alpha, sx + x, sy + y));
                plot_pixel.handler(a.orig_artwork, sx + x, sy + y, get_new_pen(a, 0xff - c2, 0xff - m2, 0xff - y2, alpha));
                plot_pixel.handler(a.alpha, sx + x, sy + y, alpha);
            }
        }
    }

    /**
     * *******************************************************************
     * allocate_artwork_mem
     *
     * Allocates memory for all the bitmaps.
     * *******************************************************************
     */
    static artwork_info allocate_artwork_mem(int width, int height) {
        artwork_info a;
        if ((Machine.orientation & ORIENTATION_SWAP_XY) != 0) {
            int temp;

            temp = height;
            height = width;
            width = temp;
        }
        a = new artwork_info();
        if (a == null) {
            logerror("Not enough memory for artwork!\n");
            return null;
        }
        a.u8_transparency = null;
        a.u8_orig_palette = null;
        a.u8_pTable = null;
        a.u8_brightness = null;

        if ((a.orig_artwork = bitmap_alloc(width, height)) == null) {
            logerror("Not enough memory for artwork!\n");
            artwork_free(a);
            return null;
        }
        fillbitmap(a.orig_artwork, 0, null);

        if ((a.alpha = bitmap_alloc(width, height)) == null) {
            logerror("Not enough memory for artwork!\n");
            artwork_free(a);
            return null;
        }
        fillbitmap(a.alpha, 0, null);

        if ((a.artwork = bitmap_alloc(width, height)) == null) {
            logerror("Not enough memory for artwork!\n");
            artwork_free(a);
            return null;
        }

        if ((a.artwork1 = bitmap_alloc(width, height)) == null) {
            logerror("Not enough memory for artwork!\n");
            artwork_free(a);
            return null;
        }

        if ((a.u8_pTable = new char[256 * 256]) == null) {
            logerror("Not enough memory.\n");
            artwork_free(a);
            return null;
        }

        if ((a.u8_brightness = new char[256 * 256]) == null) {
            logerror("Not enough memory.\n");
            artwork_free(a);
            return null;
        }
        memset(a.u8_brightness, 0, 256 * 256);
        /*TODO*///
/*TODO*///	if (((*a)->rgb = (UINT64*)malloc(width*height*sizeof(UINT64)))==0)
/*TODO*///	{
/*TODO*///		logerror("Not enough memory.\n");
/*TODO*///		artwork_free(a);
/*TODO*///		return;
/*TODO*///	}
        return a;
    }

    /*TODO*///
/*TODO*////*********************************************************************
/*TODO*///  create_disk
/*TODO*///
/*TODO*///  Creates a disk with radius r in the color of pen. A new bitmap
/*TODO*///  is allocated for the disk.
/*TODO*///
/*TODO*///*********************************************************************/
/*TODO*///static struct osd_bitmap *create_disk (int r, int fg, int bg)
/*TODO*///{
/*TODO*///	struct osd_bitmap *disk;
/*TODO*///
/*TODO*///	int x = 0, twox = 0;
/*TODO*///	int y = r;
/*TODO*///	int twoy = r+r;
/*TODO*///	int p = 1 - r;
/*TODO*///	int i;
/*TODO*///
/*TODO*///	if ((disk = bitmap_alloc(twoy, twoy)) == 0)
/*TODO*///	{
/*TODO*///		logerror("Not enough memory for artwork!\n");
/*TODO*///		return NULL;
/*TODO*///	}
/*TODO*///
/*TODO*///	/* background */
/*TODO*///	fillbitmap (disk, bg, 0);
/*TODO*///
/*TODO*///	while (x < y)
/*TODO*///	{
/*TODO*///		x++;
/*TODO*///		twox +=2;
/*TODO*///		if (p < 0)
/*TODO*///			p += twox + 1;
/*TODO*///		else
/*TODO*///		{
/*TODO*///			y--;
/*TODO*///			twoy -= 2;
/*TODO*///			p += twox - twoy + 1;
/*TODO*///		}
/*TODO*///
/*TODO*///		for (i = 0; i < twox; i++)
/*TODO*///		{
/*TODO*///			plot_pixel(disk, r-x+i, r-y  , fg);
/*TODO*///			plot_pixel(disk, r-x+i, r+y-1, fg);
/*TODO*///		}
/*TODO*///
/*TODO*///		for (i = 0; i < twoy; i++)
/*TODO*///		{
/*TODO*///			plot_pixel(disk, r-y+i, r-x  , fg);
/*TODO*///			plot_pixel(disk, r-y+i, r+x-1, fg);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	return disk;
/*TODO*///}
/*TODO*///
/*TODO*////*********************************************************************
/*TODO*///  transparency_hist
/*TODO*///
/*TODO*///  Calculates a histogram of all transparent pixels in the overlay.
/*TODO*///  The function returns a array of ints with the number of shades
/*TODO*///  for each transparent color based on the color histogram.
/*TODO*/// *********************************************************************/
/*TODO*///static unsigned int *transparency_hist (struct artwork_info *a, int num_shades)
/*TODO*///{
/*TODO*///	int i, j;
/*TODO*///	unsigned int *hist;
/*TODO*///	int num_pix=0, min_shades;
/*TODO*///	UINT8 pen;
/*TODO*///
/*TODO*///	if ((hist = (unsigned int *)malloc(a->num_pens_trans*sizeof(unsigned int)))==NULL)
/*TODO*///	{
/*TODO*///		logerror("Not enough memory!\n");
/*TODO*///		return NULL;
/*TODO*///	}
/*TODO*///	memset (hist, 0, a->num_pens_trans*sizeof(int));
/*TODO*///
/*TODO*///	if (a->orig_artwork->depth == 8)
/*TODO*///	{
/*TODO*///		for ( j=0; j<a->orig_artwork->height; j++)
/*TODO*///			for (i=0; i<a->orig_artwork->width; i++)
/*TODO*///			{
/*TODO*///				pen = a->orig_artwork->line[j][i];
/*TODO*///				if (pen < a->num_pens_trans)
/*TODO*///				{
/*TODO*///					hist[pen]++;
/*TODO*///					num_pix++;
/*TODO*///				}
/*TODO*///			}
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		for ( j=0; j<a->orig_artwork->height; j++)
/*TODO*///			for (i=0; i<a->orig_artwork->width; i++)
/*TODO*///			{
/*TODO*///				pen = ((UINT16 *)a->orig_artwork->line[j])[i];
/*TODO*///				if (pen < a->num_pens_trans)
/*TODO*///				{
/*TODO*///					hist[pen]++;
/*TODO*///					num_pix++;
/*TODO*///				}
/*TODO*///			}
/*TODO*///	}
/*TODO*///
/*TODO*///	/* we try to get at least 3 shades per transparent color */
/*TODO*///	min_shades = ((num_shades-a->num_pens_used-3*a->num_pens_trans) < 0) ? 0 : 3;
/*TODO*///
/*TODO*///	if (min_shades==0)
/*TODO*///		logerror("Too many colors in overlay. Vector colors may be wrong.\n");
/*TODO*///
/*TODO*///	num_pix /= num_shades-(a->num_pens_used-a->num_pens_trans)
/*TODO*///		-min_shades*a->num_pens_trans;
/*TODO*///
/*TODO*///	if (num_pix)
/*TODO*///		for (i=0; i < a->num_pens_trans; i++)
/*TODO*///			hist[i] = hist[i]/num_pix + min_shades;
/*TODO*///
/*TODO*///	return hist;
/*TODO*///}
    /**
     * *******************************************************************
     * load_palette
     *
     * This sets the palette colors used by the backdrop to the new colors
     * passed in as palette. The values should be stored as one byte of red, one
     * byte of blue, one byte of green. This could hopefully be used for special
     * effects, like lightening and darkening the backdrop.
     * *******************************************************************
     */
    static void load_palette(artwork_info a, char[] palette) {
        int i;
        
        /* Load colors into the palette */
        if ((Machine.drv.video_attributes & VIDEO_MODIFIES_PALETTE) != 0) {
            
            for (i = 0; i < a.num_pens_used; i++) {
                palette_change_color(i + a.start_pen, palette[i * 3], palette[i * 3 + 1], palette[i * 3 + 2]);
            }

            palette_recalc();
        }
        
    }

    /*TODO*///
/*TODO*////*********************************************************************
/*TODO*///
/*TODO*///  Reads a PNG for a artwork struct and checks if it has the right
/*TODO*///  format.
/*TODO*///
/*TODO*/// *********************************************************************/
/*TODO*///static int decode_png(const char *file_name, struct osd_bitmap **bitmap, struct osd_bitmap **alpha, struct png_info *p)
/*TODO*///{
/*TODO*///	UINT8 *tmp;
/*TODO*///	int x, y, pen;
/*TODO*///	void *fp;
/*TODO*///	int file_name_len;
/*TODO*///	char file_name2[256];
/*TODO*///
/*TODO*///	/* check for .png */
/*TODO*///	strcpy(file_name2, file_name);
/*TODO*///	file_name_len = strlen(file_name2);
/*TODO*///	if ((file_name_len < 4) || stricmp(&file_name2[file_name_len - 4], ".png"))
/*TODO*///	{
/*TODO*///		strcat(file_name2, ".png");
/*TODO*///	}
/*TODO*///
/*TODO*///	if (!(fp = osd_fopen(Machine.gamedrv->name, file_name2, OSD_FILETYPE_ARTWORK, 0)))
/*TODO*///	{
/*TODO*///		logerror("Unable to open PNG %s\n", file_name);
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///
/*TODO*///	if (!png_read_file(fp, p))
/*TODO*///	{
/*TODO*///		osd_fclose (fp);
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	osd_fclose (fp);
/*TODO*///
/*TODO*///	if (p->bit_depth > 8)
/*TODO*///	{
/*TODO*///		logerror("Unsupported bit depth %i (8 bit max.)\n", p->bit_depth);
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///
/*TODO*///	if (p->interlace_method != 0)
/*TODO*///	{
/*TODO*///		logerror("Interlace unsupported\n");
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///
/*TODO*///	if (Machine->scrbitmap->depth == 8 && p->color_type != 3)
/*TODO*///	{
/*TODO*///		logerror("Use 8bit artwork for 8bpp modes. Artwork disabled.\n");
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///
/*TODO*///	switch (p->color_type)
/*TODO*///	{
/*TODO*///	case 3:
/*TODO*///		/* Convert to 8 bit */
/*TODO*///		png_expand_buffer_8bit (p);
/*TODO*///
/*TODO*///		png_delete_unused_colors (p);
/*TODO*///
/*TODO*///		if ((*bitmap = bitmap_alloc(p->width,p->height)) == 0)
/*TODO*///		{
/*TODO*///			logerror("Unable to allocate memory for artwork\n");
/*TODO*///			return 0;
/*TODO*///		}
/*TODO*///
/*TODO*///		tmp = p->image;
/*TODO*///		if ((*bitmap)->depth == 8)
/*TODO*///		{
/*TODO*///			for (y=0; y<p->height; y++)
/*TODO*///				for (x=0; x<p->width; x++)
/*TODO*///				{
/*TODO*///					plot_pixel(*bitmap, x, y, *tmp++);
/*TODO*///				}
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			/* convert to 15bit */
/*TODO*///			if (p->num_trans > 0)
/*TODO*///				if ((*alpha = bitmap_alloc(p->width,p->height)) == 0)
/*TODO*///				{
/*TODO*///					logerror("Unable to allocate memory for artwork\n");
/*TODO*///					return 0;
/*TODO*///				}
/*TODO*///
/*TODO*///			for (y=0; y<p->height; y++)
/*TODO*///				for (x=0; x<p->width; x++)
/*TODO*///				{
/*TODO*///					pen = ((p->palette[*tmp * 3] & 0xf8) << 7) | ((p->palette[*tmp * 3 + 1] & 0xf8) << 2) | (p->palette[*tmp * 3 + 2] >> 3);
/*TODO*///					plot_pixel(*bitmap, x, y, pen);
/*TODO*///
/*TODO*///					if (p->num_trans > 0)
/*TODO*///					{
/*TODO*///						if (*tmp < p->num_trans)
/*TODO*///							plot_pixel(*alpha, x, y, p->trans[*tmp]);
/*TODO*///						else
/*TODO*///							plot_pixel(*alpha, x, y, 255);
/*TODO*///					}
/*TODO*///					tmp++;
/*TODO*///				}
/*TODO*///
/*TODO*///			free (p->palette);
/*TODO*///
/*TODO*///			/* create 15 bit palette */
/*TODO*///			if ((p->palette = create_15bit_palette()) == 0)
/*TODO*///			{
/*TODO*///				logerror("Unable to allocate memory for artwork\n");
/*TODO*///				return 0;
/*TODO*///			}
/*TODO*///			p->num_palette = 32768;
/*TODO*///		}
/*TODO*///		break;
/*TODO*///
/*TODO*///	case 6:
/*TODO*///		if ((*alpha = bitmap_alloc(p->width,p->height)) == 0)
/*TODO*///		{
/*TODO*///			logerror("Unable to allocate memory for artwork\n");
/*TODO*///			return 0;
/*TODO*///		}
/*TODO*///
/*TODO*///	case 2:
/*TODO*///		if ((*bitmap = bitmap_alloc(p->width,p->height)) == 0)
/*TODO*///		{
/*TODO*///			logerror("Unable to allocate memory for artwork\n");
/*TODO*///			return 0;
/*TODO*///		}
/*TODO*///
/*TODO*///		/* create 15 bit palette */
/*TODO*///		if ((p->palette = create_15bit_palette()) == 0)
/*TODO*///		{
/*TODO*///			logerror("Unable to allocate memory for artwork\n");
/*TODO*///			return 0;
/*TODO*///		}
/*TODO*///
/*TODO*///		p->num_palette = 32768;
/*TODO*///		p->trans = NULL;
/*TODO*///		p->num_trans = 0;
/*TODO*///
/*TODO*///		/* reduce true color to 15 bit */
/*TODO*///		tmp = p->image;
/*TODO*///		for (y=0; y<p->height; y++)
/*TODO*///			for (x=0; x<p->width; x++)
/*TODO*///			{
/*TODO*///				pen = ((tmp[0] & 0xf8) << 7) | ((tmp[1] & 0xf8) << 2) | (tmp[2] >> 3);
/*TODO*///				plot_pixel(*bitmap, x, y, pen);
/*TODO*///
/*TODO*///				if (p->color_type == 6)
/*TODO*///				{
/*TODO*///					plot_pixel(*alpha, x, y, tmp[3]);
/*TODO*///					tmp += 4;
/*TODO*///				}
/*TODO*///				else
/*TODO*///					tmp += 3;
/*TODO*///			}
/*TODO*///
/*TODO*///		break;
/*TODO*///
/*TODO*///	default:
/*TODO*///		logerror("Unsupported color type %i \n", p->color_type);
/*TODO*///		return 0;
/*TODO*///		break;
/*TODO*///	}
/*TODO*///	free (p->image);
/*TODO*///	return 1;
/*TODO*///}

    /*********************************************************************
      load_png

      This is what loads your backdrop in from disk.
      start_pen = the first pen available for the backdrop to use
      max_pens = the number of pens the backdrop can use
      So, for example, suppose you want to load "dotron.png", have it
      start with color 192, and only use 48 pens.  You would call
      backdrop = backdrop_load("dotron.png",192,48);
     *********************************************************************/

    static void load_png(String filename, int start_pen, int max_pens,
                                             int width, int height, artwork_info a)
    {
            osd_bitmap picture = null, alpha = null;
            png_info p=new png_info();
            int scalex, scaley;

            /* If the user turned artwork off, bail */
            if (options.use_artwork==0) return;

/*TODO*///            if (!decode_png(filename, &picture, &alpha, &p))
/*TODO*///                    return;
            System.out.println("decode_png NOT IMPLEMENTED!!!!");
            
            a = allocate_artwork_mem(width, height);

            if (a==null)
                    return;

            (a).start_pen = start_pen;

            (a).num_pens_used = p.num_palette;
            (a).num_pens_trans = p.num_trans;
            (a).u8_orig_palette = p.palette;
            (a).u8_transparency = p.trans;

            /* Make sure we don't have too many colors */
            if ((a).num_pens_used > max_pens)
            {
                    logerror("Too many colors in artwork.\n");
                    logerror("Colors found: %d  Max Allowed: %d\n",
                                     (a).num_pens_used,max_pens);
                    artwork_free(a);
                    bitmap_free(picture);
                    return;
            }

            /* Scale the original picture to be the same size as the visible area */
            scalex = 0x10000 * picture.width  / (a).orig_artwork.width;
            scaley = 0x10000 * picture.height / (a).orig_artwork.height;

            if ((Machine.orientation & ORIENTATION_SWAP_XY) != 0)
            {
                    int tmp;
                    tmp = scalex;
                    scalex = scaley;
                    scaley = tmp;
            }

            copyrozbitmap((a).orig_artwork, picture, 0, 0, scalex, 0, 0, scaley, 0, null, TRANSPARENCY_NONE, 0, 0);
            /* We don't need the original any more */
            bitmap_free(picture);

            if (alpha != null)
            {
                    copyrozbitmap((a).alpha, alpha, 0, 0, scalex, 0, 0, scaley, 0, null, TRANSPARENCY_NONE, 0, 0);
                    bitmap_free(alpha);
            }

            /* If the game uses dynamic colors, we assume that it's safe
               to init the palette and remap the colors now */
            if ((Machine.drv.video_attributes & VIDEO_MODIFIES_PALETTE) != 0)
            {
                    load_palette(a,(a).u8_orig_palette);
                    backdrop_refresh(a);
            }

    }

    static void load_png_fit(String filename, int start_pen, int max_pens, artwork_info a)
    {
        load_png(filename, start_pen, max_pens, Machine.scrbitmap.width, Machine.scrbitmap.height, a);
    }

    /**
     * *******************************************************************
     * backdrop_refresh
     *
     * This remaps the "original" palette indexes to the abstract OS indexes
     * used by MAME. This needs to be called every time palette_recalc returns a
     * non-zero value, since the mappings will have changed.
     * *******************************************************************
     */
    public static void backdrop_refresh(artwork_info a) {
        int i, j, height, width, offset;
        osd_bitmap back, orig;

        offset = a.start_pen;
        back = a.artwork;
        orig = a.orig_artwork;
        height = a.artwork.height;
        width = a.artwork.width;

        if (back.depth == 8) {
            for (j = 0; j < height; j++) {
                for (i = 0; i < width; i++) {
                    back.line[j].write(i, Machine.pens[orig.line[j].read(i) + offset]);
                }
            }
        } else {
            throw new UnsupportedOperationException("Unsupported");
            /*TODO*///		for ( j = 0; j < height; j++)
/*TODO*///			for (i = 0; i < width; i++)
/*TODO*///				((UINT16 *)back->line[j])[i] = Machine->pens[((UINT16 *)orig->line[j])[i] + offset];
        }
    }

    /*TODO*///
/*TODO*///static void backdrop_remap(void)
/*TODO*///{
/*TODO*///	backdrop_refresh(artwork_backdrop);
/*TODO*///	brightness_update (artwork_backdrop);
/*TODO*///}
    /**
     * *******************************************************************
     * overlay_remap
     *
     * This remaps the "original" palette indexes to the abstract OS indexes
     * used by MAME. The alpha channel is also taken into account.
     * *******************************************************************
     */
    static void overlay_remap() {
        int i, j;
        /*TODO*///UINT8 r,g,b;
        float h, s, v, rf, gf, bf;
        int offset, height, width;
        osd_bitmap overlay, overlay1, orig;

        offset = artwork_overlay.start_pen;
        height = artwork_overlay.artwork.height;
        width = artwork_overlay.artwork.width;
        overlay = artwork_overlay.artwork;
        overlay1 = artwork_overlay.artwork1;
        orig = artwork_overlay.orig_artwork;

        if (overlay.depth == 8) {
            for (j = 0; j < height; j++) {
                for (i = 0; i < width; i++) {
                    overlay.line[j].write(i, Machine.pens[orig.line[j].read(i) + offset]);
                }
            }
        } else {
            throw new UnsupportedOperationException("Unsupported");
            /*TODO*///		if (artwork_overlay->alpha)
/*TODO*///		{
/*TODO*///			for ( j=0; j<height; j++)
/*TODO*///				for (i=0; i<width; i++)
/*TODO*///				{
/*TODO*///					UINT64 v1,v2;
/*TODO*///					UINT16 alpha = ((UINT16 *)artwork_overlay->alpha->line[j])[i];
/*TODO*///
/*TODO*///					osd_get_pen (Machine->pens[((UINT16 *)orig->line[j])[i]+offset], &r, &g, &b);
/*TODO*///					v1 = MAX(r, MAX(g, b));
/*TODO*///					v2 = (v1 * alpha) / 255;
/*TODO*///					artwork_overlay->rgb[j*width+i] = (v1 << 32) | (v2 << 24) | ((UINT64)r << 16) |
/*TODO*///													  ((UINT64)g << 8) | (UINT64)b;
/*TODO*///
/*TODO*///					RGBtoHSV( r/255.0, g/255.0, b/255.0, &h, &s, &v );
/*TODO*///
/*TODO*///					HSVtoRGB( &rf, &gf, &bf, h, s, v * alpha/255.0);
/*TODO*///					r = rf*255; g = gf*255; b = bf*255;
/*TODO*///					((UINT16 *)overlay->line[j])[i] = Machine->pens[(((r & 0xf8) << 7) | ((g & 0xf8) << 2) | (b >> 3)) + artwork_overlay->start_pen];
/*TODO*///
/*TODO*///					HSVtoRGB( &rf, &gf, &bf, h, s, 1);
/*TODO*///					r = rf*255; g = gf*255; b = bf*255;
/*TODO*///					((UINT16 *)overlay1->line[j])[i] = Machine->pens[(((r & 0xf8) << 7) | ((g & 0xf8) << 2) | (b >> 3)) + artwork_overlay->start_pen];
/*TODO*///				}
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			for ( j=0; j<height; j++)
/*TODO*///				for (i=0; i<width; i++)
/*TODO*///					((UINT16 *)overlay->line[j])[i] = Machine->pens[((UINT16 *)orig->line[j])[i]+offset];
/*TODO*///		}
        }

        /* Calculate brightness of all colors */
        brightness_update(artwork_overlay);
    }

    public static void artwork_remap() {

        /*TODO*///	if (artwork_backdrop) backdrop_remap();
        if (artwork_overlay != null) {
            overlay_remap();
        }
    }

    /*TODO*///
/*TODO*////*********************************************************************
/*TODO*///  overlay_draw
/*TODO*///
/*TODO*///  Supports different levels of intensity on the screen and different
/*TODO*///  levels of transparancy of the overlay (only in 16 bpp modes).
/*TODO*/// *********************************************************************/
/*TODO*///
    public static void overlay_draw(osd_bitmap dest, osd_bitmap source) {
        int i, j;
        int height, width;

        /* the colors could have changed so update the brightness table */
        if ((Machine.drv.video_attributes & VIDEO_MODIFIES_PALETTE) != 0) {
            brightness_update(artwork_overlay);
        }

        height = artwork_overlay.artwork.height;
        width = artwork_overlay.artwork.width;

        if (dest.depth == 8) {
            if ((Machine.drv.video_attributes & VIDEO_TYPE_VECTOR) != 0) {
                throw new UnsupportedOperationException("Unsupported");
                /*TODO*///			UINT8 *dst, *ovr, *src;
/*TODO*///			UINT8 *bright = artwork_overlay.brightness;
/*TODO*///			UINT8 *tab = artwork_overlay.pTable;
/*TODO*///			int bp;
/*TODO*///
/*TODO*///			copybitmap(dest, artwork_overlay.artwork ,0,0,0,0,NULL,TRANSPARENCY_NONE,0);
/*TODO*///			for ( j = 0; j < height; j++)
/*TODO*///			{
/*TODO*///				dst = dest.line[j];
/*TODO*///				src = source.line[j];
/*TODO*///				ovr = artwork_overlay.orig_artwork.line[j];
/*TODO*///				for (i = 0; i < width; i++)
/*TODO*///				{
/*TODO*///					bp = bright[*src++];
/*TODO*///					if (bp > 0)
/*TODO*///						dst[i] = Machine.pens[tab[(ovr[i] << 8) + bp]];
/*TODO*///				}
/*TODO*///			}
            } else /* !VECTOR */ {
                UBytePtr dst, ovr, src;
                int black = Machine.pens[0];

                for (j = 0; j < height; j++) {
                    dst = new UBytePtr(dest.line[j]);
                    src = new UBytePtr(source.line[j]);
                    ovr = new UBytePtr(artwork_overlay.artwork.line[j]);
                    for (i = width; i > 0; i--) {
                        if (src.read() != black) {
                            dst.write(ovr.read());
                        } else {
                            dst.write(black);
                        }
                        dst.inc();
                        src.inc();
                        ovr.inc();
                    }
                }
            }
        } else /* 16 bit */ {
            throw new UnsupportedOperationException("Unsupported");
            /*TODO*///		if (artwork_overlay.start_pen == 2)
/*TODO*///		{
/*TODO*///			/* fast version */
/*TODO*///			UINT16 *dst, *bg, *fg, *src;
/*TODO*///			int black = Machine->pens[0];
/*TODO*///
/*TODO*///			height = artwork_overlay->artwork->height;
/*TODO*///			width = artwork_overlay->artwork->width;
/*TODO*///
/*TODO*///			for ( j = 0; j < height; j++)
/*TODO*///			{
/*TODO*///				dst = (UINT16 *)dest->line[j];
/*TODO*///				src = (UINT16 *)source->line[j];
/*TODO*///				bg = (UINT16 *)artwork_overlay->artwork->line[j];
/*TODO*///				fg = (UINT16 *)artwork_overlay->artwork1->line[j];
/*TODO*///				for (i = width; i > 0; i--)
/*TODO*///				{
/*TODO*///					if (*src!=black)
/*TODO*///						*dst = *fg;
/*TODO*///					else
/*TODO*///						*dst = *bg;
/*TODO*///					dst++;
/*TODO*///					src++;
/*TODO*///					fg++;
/*TODO*///					bg++;
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///		else /* slow version */
/*TODO*///		{
/*TODO*///			UINT16 *src, *dst;
/*TODO*///			UINT64 *rgb = artwork_overlay->rgb;
/*TODO*///			UINT8 *bright = artwork_overlay->brightness;
/*TODO*///			unsigned short *pens = &Machine->pens[artwork_overlay->start_pen];
/*TODO*///
/*TODO*///			copybitmap(dest, artwork_overlay->artwork ,0,0,0,0,NULL,TRANSPARENCY_NONE,0);
/*TODO*///
/*TODO*///			for ( j = 0; j < height; j++)
/*TODO*///			{
/*TODO*///				dst = (UINT16 *)dest->line[j];
/*TODO*///				src = (UINT16 *)source->line[j];
/*TODO*///				for (i = width; i > 0; i--)
/*TODO*///				{
/*TODO*///					int bp = bright[*src++];
/*TODO*///					if (bp > 0)
/*TODO*///					{
/*TODO*///						if (*rgb & 0x00ffffff)
/*TODO*///						{
/*TODO*///							int v = *rgb >> 32;
/*TODO*///							int vn =(*rgb >> 24) & 0xff;
/*TODO*///							UINT8 r = *rgb >> 16;
/*TODO*///							UINT8 g = *rgb >> 8;
/*TODO*///							UINT8 b = *rgb;
/*TODO*///
/*TODO*///							vn += ((255 - vn) * bp) / 255;
/*TODO*///							r = (r * vn) / v;
/*TODO*///							g = (g * vn) / v;
/*TODO*///							b = (b * vn) / v;
/*TODO*///							*dst = pens[(((r & 0xf8) << 7) | ((g & 0xf8) << 2) | (b >> 3))];
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							int vn =(*rgb >> 24) & 0xff;
/*TODO*///
/*TODO*///							vn += ((255 - vn) * bp) / 255;
/*TODO*///							*dst = pens[(((vn & 0xf8) << 7) | ((vn & 0xf8) << 2) | (vn >> 3))];
/*TODO*///						}
/*TODO*///					}
/*TODO*///					dst++;
/*TODO*///					rgb++;
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
        }
    }

    /*TODO*///
/*TODO*////*********************************************************************
/*TODO*///  backdrop_draw
/*TODO*///
/*TODO*///  Very simple, no translucency.
/*TODO*/// *********************************************************************/
/*TODO*///
/*TODO*///static void backdrop_draw(struct osd_bitmap *dest, struct osd_bitmap *source)
/*TODO*///{
/*TODO*///	int i, j;
/*TODO*///	UINT8 *brightness = artwork_backdrop->brightness;
/*TODO*///
/*TODO*///	/* the colors could have changed so update the brightness table */
/*TODO*///	if (Machine->drv->video_attributes & VIDEO_MODIFIES_PALETTE)
/*TODO*///		brightness_update(artwork_backdrop);
/*TODO*///
/*TODO*///	copybitmap(dest, artwork_backdrop->artwork ,0,0,0,0,NULL,TRANSPARENCY_NONE,0);
/*TODO*///
/*TODO*///	if (dest->depth == 8)
/*TODO*///	{
/*TODO*///		UINT8 *dst, *bdr, *src;
/*TODO*///
/*TODO*///		for ( j = 0; j < source->height; j++)
/*TODO*///		{
/*TODO*///			dst = dest->line[j];
/*TODO*///			src = source->line[j];
/*TODO*///			bdr = artwork_backdrop->artwork->line[j];
/*TODO*///			for (i = 0; i < source->width; i++)
/*TODO*///			{
/*TODO*///				if (brightness[*src] > brightness[*bdr])
/*TODO*/////				if (brightness[*src] > 10)
/*TODO*///					*dst = *src;
/*TODO*///				dst++;
/*TODO*///				src++;
/*TODO*///				bdr++;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		UINT16 *dst, *bdr, *src;
/*TODO*///
/*TODO*///		for ( j = 0; j < source->height; j++)
/*TODO*///		{
/*TODO*///			dst = (UINT16 *)dest->line[j];
/*TODO*///			src = (UINT16 *)source->line[j];
/*TODO*///			bdr = (UINT16 *)artwork_backdrop->artwork->line[j];
/*TODO*///			for (i = 0; i < source->width; i++)
/*TODO*///			{
/*TODO*///				if (brightness[*src] > brightness[*bdr])
/*TODO*///					*dst = *src;
/*TODO*///				dst++;
/*TODO*///				src++;
/*TODO*///				bdr++;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
    public static void artwork_draw(osd_bitmap dest, osd_bitmap source, int _bitmap_dirty) {
        	if (_bitmap_dirty!=0)
	{
		artwork_remap();
		osd_mark_dirty (0, 0, dest.width-1, dest.height-1, 0);
	}
/*TODO*///
/*TODO*///	if (artwork_backdrop) backdrop_draw(dest, source);
	if (artwork_overlay!=null) overlay_draw(dest, source);
    }

    /*TODO*///
/*TODO*////*********************************************************************
/*TODO*///  artwork_free
/*TODO*///
/*TODO*///  Don't forget to clean up when you're done with the backdrop!!!
/*TODO*/// *********************************************************************/
/*TODO*///
    public static void artwork_free(artwork_info a) {
        /*TODO*///	if (*a)
/*TODO*///	{
/*TODO*///		if ((*a)->artwork)
/*TODO*///			bitmap_free((*a)->artwork);
/*TODO*///		if ((*a)->artwork1)
/*TODO*///			bitmap_free((*a)->artwork1);
/*TODO*///		if ((*a)->alpha)
/*TODO*///			bitmap_free((*a)->alpha);
/*TODO*///		if ((*a)->orig_artwork)
/*TODO*///			bitmap_free((*a)->orig_artwork);
/*TODO*///		if ((*a)->orig_palette)
/*TODO*///			free ((*a)->orig_palette);
/*TODO*///		if ((*a)->transparency)
/*TODO*///			free ((*a)->transparency);
/*TODO*///		if ((*a)->brightness)
/*TODO*///			free ((*a)->brightness);
/*TODO*///		if ((*a)->rgb)
/*TODO*///			free ((*a)->rgb);
/*TODO*///		if ((*a)->pTable)
/*TODO*///			free ((*a)->pTable);
/*TODO*///		free(*a);
/*TODO*///
/*TODO*///		*a = NULL;
/*TODO*///	}
    }

    public static void artwork_kill() {
        if (artwork_backdrop != null || artwork_overlay != null) {
            bitmap_free(artwork_real_scrbitmap);
        }

        if (artwork_backdrop != null) {
            artwork_free(artwork_backdrop);
        }
        if (artwork_overlay != null) {
            artwork_free(artwork_overlay);
        }
    }

    /*TODO*///
/*TODO*////*********************************************************************
/*TODO*///  overlay_set_palette
/*TODO*///
/*TODO*///  Generates a palette for vector games with an overlay.
/*TODO*///
/*TODO*///  The 'glowing' effect is simulated by alpha blending the transparent
/*TODO*///  colors with a black (the screen) background. Then different shades
/*TODO*///  of each transparent color are calculated by alpha blending this
/*TODO*///  color with different levels of brightness (values in HSV) of the
/*TODO*///  transparent color from v=0 to v=1. This doesn't work very well with
/*TODO*///  blue. The number of shades is proportional to the number of pixels of
/*TODO*///  that color. A look up table is also generated to map beam
/*TODO*///  brightness and overlay colors to pens. If you have a beam brightness
/*TODO*///  of 128 under a transparent pixel of pen 7 then
/*TODO*///     Table (7,128)
/*TODO*///  returns the pen of the resulting color. The table is usually
/*TODO*///  converted to OS colors later.
/*TODO*/// *********************************************************************/
/*TODO*///int overlay_set_palette (UINT8 *palette, int num_shades)
/*TODO*///{
/*TODO*///	unsigned int i,j, shades = 0, step;
/*TODO*///	unsigned int *hist;
/*TODO*///	float h, s, v, r, g, b;
/*TODO*///
/*TODO*///	/* adjust palette start */
/*TODO*///
/*TODO*///	palette += 3 * artwork_overlay->start_pen;
/*TODO*///
/*TODO*///	if (Machine->scrbitmap->depth == 8)
/*TODO*///	{
/*TODO*///		if((hist = transparency_hist (artwork_overlay, num_shades))==NULL)
/*TODO*///			return 0;
/*TODO*///
/*TODO*///		/* Copy all artwork colors to the palette */
/*TODO*///		memcpy (palette, artwork_overlay->orig_palette, 3 * artwork_overlay->num_pens_used);
/*TODO*///
/*TODO*///		/* Fill the palette with shades of the transparent colors */
/*TODO*///		for (i = 0; i < artwork_overlay->num_pens_trans; i++)
/*TODO*///		{
/*TODO*///			RGBtoHSV( artwork_overlay->orig_palette[i * 3]/255.0,
/*TODO*///					  artwork_overlay->orig_palette[i * 3 + 1] / 255.0,
/*TODO*///					  artwork_overlay->orig_palette[i * 3 + 2] / 255.0, &h, &s, &v );
/*TODO*///
/*TODO*///			/* blend transparent entries with black background */
/*TODO*///			/* we don't need the original palette entry any more */
/*TODO*///			HSVtoRGB ( &r, &g, &b, h, s, v*artwork_overlay->transparency[i]/255.0);
/*TODO*///			palette [i * 3] = r * 255.0;
/*TODO*///			palette [i * 3 + 1] = g * 255.0;
/*TODO*///			palette [i * 3 + 2] = b * 255.0;
/*TODO*///			if (hist[i] > 1)
/*TODO*///			{
/*TODO*///				for (j = 0; j < hist[i] - 1; j++)
/*TODO*///				{
/*TODO*///					/* we start from 1 because the 0 level is already in the palette */
/*TODO*///					HSVtoRGB ( &r, &g, &b, h, s, v * artwork_overlay->transparency[i]/255.0 +
/*TODO*///							   ((1.0-(v*artwork_overlay->transparency[i]/255.0))*(j+1))/(hist[i]-1));
/*TODO*///					palette [(artwork_overlay->num_pens_used + shades + j) * 3] = r * 255.0;
/*TODO*///					palette [(artwork_overlay->num_pens_used + shades + j) * 3 + 1] = g * 255.0;
/*TODO*///					palette [(artwork_overlay->num_pens_used + shades + j) * 3 + 2] = b * 255.0;
/*TODO*///				}
/*TODO*///
/*TODO*///				/* create alpha LUT for quick alpha blending */
/*TODO*///				for (j = 0; j < 256; j++)
/*TODO*///				{
/*TODO*///					step = hist[i] * j / 256.0;
/*TODO*///					if (step == 0)
/*TODO*///						/* no beam, just overlay over black screen */
/*TODO*///						artwork_overlay->pTable[i * 256 + j] = i + artwork_overlay->start_pen;
/*TODO*///					else
/*TODO*///						artwork_overlay->pTable[i * 256 + j] = artwork_overlay->num_pens_used +
/*TODO*///															   shades + step - 1 + artwork_overlay->start_pen;
/*TODO*///				}
/*TODO*///				shades += hist[i] - 1;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///	else
/*TODO*///		memcpy (palette, artwork_overlay->orig_palette, 3 * artwork_overlay->num_pens_used);
/*TODO*///	return 1;
/*TODO*///}

    public static void overlay_load(String filename, int start_pen, int max_pens)
    {
            int width, height;

            /* replace the real display with a fake one, this way drivers can access Machine->scrbitmap
               the same way as before */

            width = Machine.scrbitmap.width;
            height = Machine.scrbitmap.height;

            if ((Machine.orientation & ORIENTATION_SWAP_XY) != 0)
            {
                    int temp;

                    temp = height;
                    height = width;
                    width = temp;
            }

            load_png_fit(filename, start_pen, max_pens, artwork_overlay);

            if (artwork_overlay != null)
            {
                    if ((artwork_real_scrbitmap = bitmap_alloc(width, height)) == null)
                    {
                            artwork_kill();
                            logerror("Not enough memory for artwork!\n");
                            return;
                    }
            }
    }

    public static void backdrop_load(String filename, int start_pen, int max_pens)
    {
            int width, height;

            /* replace the real display with a fake one, this way drivers can access Machine->scrbitmap
               the same way as before */

            load_png_fit(filename, start_pen, max_pens, artwork_backdrop);

            if (artwork_backdrop != null)
            {
                    width = artwork_backdrop.artwork.width;
                    height = artwork_backdrop.artwork.height;

                    if ((Machine.orientation & ORIENTATION_SWAP_XY) != 0)
                    {
                            int temp;

                            temp = height;
                            height = width;
                            width = temp;
                    }

                    if ((artwork_real_scrbitmap = bitmap_alloc(width, height)) == null)
                    {
                            artwork_kill();
                            logerror("Not enough memory for artwork!\n");
                            return;
                    }
            }
    }

    public static void artwork_load(artwork_info a, String filename, int start_pen, int max_pens)
    {
            load_png_fit(filename, start_pen, max_pens, a);
    }

    public static void artwork_load_size(artwork_info a, String filename, int start_pen, int max_pens, int width, int height)
    {
	load_png(filename, start_pen, max_pens, width, height, a);
    }

/*TODO*////*********************************************************************
/*TODO*///  artwork_elements scale
/*TODO*///
/*TODO*///  scales an array of artwork elements to width and height. The first
/*TODO*///  element (which has to be a box) is used as reference. This is useful
/*TODO*///  for atwork with disks.
/*TODO*///
/*TODO*///*********************************************************************/
/*TODO*///
/*TODO*///void artwork_elements_scale(struct artwork_element *ae, int width, int height)
/*TODO*///{
/*TODO*///	int scale_w, scale_h;
/*TODO*///
/*TODO*///	if (Machine->orientation & ORIENTATION_SWAP_XY)
/*TODO*///	{
/*TODO*///		scale_w = (height << 16)/(ae->box.max_x + 1);
/*TODO*///		scale_h = (width << 16)/(ae->box.max_y + 1);
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		scale_w = (width << 16)/(ae->box.max_x + 1);
/*TODO*///		scale_h = (height << 16)/(ae->box.max_y + 1);
/*TODO*///	}
/*TODO*///	while (ae->box.min_x >= 0)
/*TODO*///	{
/*TODO*///		ae->box.min_x = (ae->box.min_x * scale_w) >> 16;
/*TODO*///		ae->box.max_x = (ae->box.max_x * scale_w) >> 16;
/*TODO*///		ae->box.min_y = (ae->box.min_y * scale_h) >> 16;
/*TODO*///		if (ae->box.max_y >= 0)
/*TODO*///			ae->box.max_y = (ae->box.max_y * scale_h) >> 16;
/*TODO*///		ae++;
/*TODO*///	}
/*TODO*///}
    /**
     * *******************************************************************
     * overlay_create
     *
     * This works similar to artwork_load but generates artwork from an array of
     * artwork_element. This is useful for very simple artwork like the overlay
     * in the Space invaders series of games. The overlay is defined to be the
     * same size as the screen. The end of the array is marked by an entry with
     * negative coordinates. Boxes and disks are supported. Disks are marked
     * max_y == -1, min_x == x coord. of center, min_y == y coord. of center,
     * max_x == radius. If there are transparent and opaque overlay elements,
     * the opaque ones have to be at the end of the list to stay compatible with
     * the PNG artwork.
     * *******************************************************************
     */
    public static void overlay_create(artwork_element[] ae, /*unsigned*/ int start_pen, /*unsigned*/ int max_pens) {
        osd_bitmap disk, disk_alpha, box, box_alpha;
        int pen, transparent_pen = -1, disk_type, white_pen;
        int width, height;

        artwork_overlay = allocate_artwork_mem(Machine.scrbitmap.width, Machine.scrbitmap.height);
        
        if (artwork_overlay == null) {
            System.out.println("is null");
            return;
        }
        
        /* replace the real display with a fake one, this way drivers can access Machine->scrbitmap
	   the same way as before */
        width = Machine.scrbitmap.width;
        height = Machine.scrbitmap.height;

        if ((Machine.orientation & ORIENTATION_SWAP_XY) != 0) {
            int temp;

            temp = height;
            height = width;
            width = temp;
        }
        
        
        if ((artwork_real_scrbitmap = bitmap_alloc(width, height)) == null) {
            artwork_kill();
            logerror("Not enough memory for artwork!\n");
            return;
        }
                
        artwork_overlay.start_pen = start_pen;

        if (Machine.scrbitmap.depth == 8) {
            
            if ((artwork_overlay.u8_orig_palette = new char[256 * 3]) == null) {
                logerror("Not enough memory for overlay!\n");
                artwork_kill();
                return;
            }

            if ((artwork_overlay.u8_transparency = new char[256]) == null) {
                logerror("Not enough memory for overlay!\n");
                artwork_kill();
                return;
            }

            transparent_pen = 255;
            /* init with transparent white */
            memset(artwork_overlay.u8_orig_palette, 255, 3);
            artwork_overlay.u8_transparency[0] = 0;
            artwork_overlay.num_pens_used = 1;
            artwork_overlay.num_pens_trans = 1;
            white_pen = 0;
            fillbitmap(artwork_overlay.orig_artwork, 0, null);
            fillbitmap(artwork_overlay.alpha, 0, null);
        } else {
            throw new UnsupportedOperationException("Unsupported");
            /*TODO*///		if ((artwork_overlay.orig_palette = create_15bit_palette()) == 0)
/*TODO*///		{
/*TODO*///			logerror("Unable to allocate memory for artwork\n");
/*TODO*///			artwork_kill();
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		artwork_overlay.num_pens_used = 32768;
/*TODO*///		transparent_pen = 0xffff;
/*TODO*///		white_pen = 0x7fff;
/*TODO*///		fillbitmap (artwork_overlay.orig_artwork, white_pen, 0);
/*TODO*///		fillbitmap (artwork_overlay.alpha, 0, 0);
        }
        
        int ae_ptr = 0;
        while (ae[ae_ptr].box.min_x >= 0) {
            
            int alpha = ae[ae_ptr].alpha;

            if (alpha == OVERLAY_DEFAULT_OPACITY) {
                alpha = 0x18;
            }

            pen = get_new_pen(artwork_overlay, ae[ae_ptr].red, ae[ae_ptr].green, ae[ae_ptr].blue, alpha);
            
            if (ae[ae_ptr].box.max_y < 0) /* disk */ {
                int r = ae[ae_ptr].box.max_x;
                disk_type = ae[ae_ptr].box.max_y;
                throw new UnsupportedOperationException("Unsupported");
                /*TODO*///                			switch (disk_type)
/*TODO*///			{
/*TODO*///			case -1: /* disk overlay */
/*TODO*///				if ((disk = create_disk (r, pen, white_pen)) == NULL)
/*TODO*///				{
/*TODO*///					artwork_kill();
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				if ((disk_alpha = create_disk (r, alpha, 0)) == NULL)
/*TODO*///				{
/*TODO*///					artwork_kill();
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				merge_cmy (artwork_overlay, disk, disk_alpha, ae[ae_ptr].box.min_x - r, ae[ae_ptr].box.min_y - r);
/*TODO*///				bitmap_free(disk_alpha);
/*TODO*///				bitmap_free(disk);
/*TODO*///				break;
/*TODO*///
/*TODO*///			case -2: /* punched disk */
/*TODO*///				if ((disk = create_disk (r, pen, transparent_pen)) == NULL)
/*TODO*///				{
/*TODO*///					artwork_kill();
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				copybitmap(artwork_overlay->orig_artwork,disk,0, 0,
/*TODO*///						   ae[ae_ptr].box.min_x - r,
/*TODO*///						   ae[ae_ptr].box.min_y - r,
/*TODO*///						   0,TRANSPARENCY_PEN, transparent_pen);
/*TODO*///				/* alpha */
/*TODO*///				if ((disk_alpha = create_disk (r, alpha, transparent_pen)) == NULL)
/*TODO*///				{
/*TODO*///					artwork_kill();
/*TODO*///					return;
/*TODO*///				}
/*TODO*///				copybitmap(artwork_overlay->alpha,disk_alpha,0, 0,
/*TODO*///						   ae[ae_ptr].box.min_x - r,
/*TODO*///						   ae[ae_ptr].box.min_y - r,
/*TODO*///						   0,TRANSPARENCY_PEN, transparent_pen);
/*TODO*///				bitmap_free(disk_alpha);
/*TODO*///				bitmap_free(disk);
/*TODO*///				break;
/*TODO*///
/*TODO*///			}
            } else {
                
                if ((box = bitmap_alloc(ae[ae_ptr].box.max_x - ae[ae_ptr].box.min_x + 1,
                        ae[ae_ptr].box.max_y - ae[ae_ptr].box.min_y + 1)) == null) {
                    logerror("Not enough memory for artwork!\n");
                    artwork_kill();
                    return;
                }
                
                if ((box_alpha = bitmap_alloc(ae[ae_ptr].box.max_x - ae[ae_ptr].box.min_x + 1,
                        ae[ae_ptr].box.max_y - ae[ae_ptr].box.min_y + 1)) == null) {
                    logerror("Not enough memory for artwork!\n");
                    artwork_kill();
                    return;
                }
                
                fillbitmap(box, pen, null);
                fillbitmap(box_alpha, alpha, null);
                merge_cmy(artwork_overlay, box, box_alpha, ae[ae_ptr].box.min_x, ae[ae_ptr].box.min_y);
                bitmap_free(box);
                bitmap_free(box_alpha);
                
            }
            ae_ptr++;
            
        }

        /* Make sure we don't have too many colors */
        if (artwork_overlay.num_pens_used > max_pens) {
            logerror("Too many colors in overlay.\n");
            logerror("Colors found: %d  Max Allowed: %d\n",
                    artwork_overlay.num_pens_used, max_pens);
            artwork_kill();
            return;
        }
        
        if ((Machine.drv.video_attributes & VIDEO_MODIFIES_PALETTE) != 0) {
            load_palette(artwork_overlay, artwork_overlay.u8_orig_palette);
            backdrop_refresh(artwork_overlay);
        }
        
        if ((Machine.drv.video_attributes & VIDEO_MODIFIES_PALETTE) != 0) {
            overlay_remap();            
        }
        
    }

    /*TODO*///int artwork_get_size_info(const char *file_name, struct artwork_size_info *a)
/*TODO*///{
/*TODO*///	void *fp;
/*TODO*///	struct png_info p;
/*TODO*///	int file_name_len;
/*TODO*///	char file_name2[256];
/*TODO*///
/*TODO*///	/* If the user turned artwork off, bail */
/*TODO*///	if (!options.use_artwork) return 0;
/*TODO*///
/*TODO*///	/* check for .png */
/*TODO*///	strcpy(file_name2, file_name);
/*TODO*///	file_name_len = strlen(file_name2);
/*TODO*///	if ((file_name_len < 4) || stricmp(&file_name2[file_name_len - 4], ".png"))
/*TODO*///	{
/*TODO*///		strcat(file_name2, ".png");
/*TODO*///	}
/*TODO*///
/*TODO*///	if (!(fp = osd_fopen(Machine->gamedrv->name, file_name2, OSD_FILETYPE_ARTWORK, 0)))
/*TODO*///	{
/*TODO*///		logerror("Unable to open PNG %s\n", file_name);
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///
/*TODO*///	if (!png_read_info(fp, &p))
/*TODO*///	{
/*TODO*///		osd_fclose (fp);
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	osd_fclose (fp);
/*TODO*///
/*TODO*///	a->width = p.width;
/*TODO*///	a->height = p.height;
/*TODO*///	a->screen = p.screen;
/*TODO*///
/*TODO*///	return 1;
/*TODO*///}
/*TODO*///
/*TODO*///    
}
