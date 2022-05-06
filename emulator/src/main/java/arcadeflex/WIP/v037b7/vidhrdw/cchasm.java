/*
 * Cosmic Chasm video hardware emulation
 *
 * Jul 15 1999 by Mathis Rosenhauer
 *
 */

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.vidhrdw.vector.*;
import static arcadeflex.v056.mame.timer.*;
import static arcadeflex.v056.mame.timerH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static java.lang.Math.abs;

public class cchasm
{
	
	public static final int HALT   = 0;
	public static final int JUMP   = 1;
	public static final int COLOR  = 2;
	public static final int SCALEY = 3;
	public static final int POSY   = 4;
	public static final int SCALEX = 5;
	public static final int POSX   = 6;
	public static final int LENGTH = 7;
	
	public static final int VEC_SHIFT = 16;
	
	public static UBytePtr cchasm_ram = new UBytePtr();
	
	static int xcenter, ycenter;
	
	static timer_callback cchasm_refresh_end = new timer_callback() {
            @Override
            public void handler(int dummy) {
                cpu_set_irq_line (0, 2, ASSERT_LINE);
            }
        };
         
	static void cchasm_refresh ()
	{
	
		int pc = 0;
	    int done = 0;
	    int opcode, data;
	    int currentx = 0, currenty = 0;
	    int scalex = 0, scaley = 0;
	    int color = 0;
	    int total_length = 1;   /* length of all lines drawn in a frame */
	    int move = 0;
	
		vector_clear_list();
	
		while (done==0)
		{
	
	        data = cchasm_ram.READ_WORD (pc);
	   		opcode = data >> 12;
	        data &= 0xfff;
	        if ((opcode > COLOR) && (data & 0x800)!=0)
	          data |= 0xfffff000;
	
			pc += 2;
	
			switch (opcode)
			{
	        case HALT:
	            done=1;
	            break;
	        case JUMP:
	            pc = data - 0xb00;
	            logerror("JUMP to %x\n", data);
	            break;
	        case COLOR:
	            data = data ^ 0xfff;
	            color = ((data >> 4) & 0xe0) | ((data >> 3 ) &0x1c) | ((data >> 2 ) &0x3);
	            break;
	        case SCALEY:
	            scaley = data << 5;
	            break;
	        case POSY:
	            move = 1;
	            currenty = ycenter + (data << 16);
	            break;
	        case SCALEX:
	            scalex = data << 5;
	            break;
	        case POSX:
	            move = 1;
	            currentx = xcenter - (data << 16);
	            break;
	        case LENGTH:
	            if (move != 0)
	            {
	                vector_add_point (currentx, currenty, 0, 0);
	                move = 0;
	            }
	
	            currentx -= data * scalex;
	            currenty += data * scaley;
	
	            total_length += abs(data);
	
	            if (color != 0)
	                vector_add_point (currentx, currenty, color, 0xff);
	            else
	                move = 1;
	            break;
	        default:
	            logerror("Unknown refresh proc opcode %x with data %x at pc = %x\n", opcode, data, pc-2);
	            done = 1;
	            break;
			}
		}
	    /* Refresh processor runs with 6 MHz */
	    timer_set (TIME_IN_NSEC(166) * total_length, 0, cchasm_refresh_end);
	}
	
	
	public static WriteHandlerPtr cchasm_refresh_control_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	    switch (data)
	    {
	    case 0x37ff:
	        cchasm_refresh();
	        break;
	    case 0xf7ff:
	        cpu_set_irq_line (0, 2, CLEAR_LINE);
	        break;
	    }
	} };
	
	public static VhConvertColorPromPtr cchasm_init_colors = new VhConvertColorPromPtr() { public void handler(char []palette, char []colortable, UBytePtr color_prom) 
	{
	    int i= 0, r, g, b;
	
	    for (r=0; r<8; r++)
	        for (g=0; g<8; g++)
	            for (b=0; b<4; b++)
	            {
	                palette[3*i  ] = (char) ((255 * r) / 7);
	                palette[3*i+1] = (char) ((255 * g) / 7);
	                palette[3*i+2] = (char) ((255 * b) /3);
	                i++;
	            }
	} };
	
	public static VhStartPtr cchasm_vh_start = new VhStartPtr() { public int handler() 
	{
	    int xmin, xmax, ymin, ymax;
	
		xmin=Machine.visible_area.min_x;
		ymin=Machine.visible_area.min_y;
		xmax=Machine.visible_area.max_x;
		ymax=Machine.visible_area.max_y;
	
		xcenter=((xmax+xmin)/2) << VEC_SHIFT;
		ycenter=((ymax+ymin)/2) << VEC_SHIFT;
	
		vector_set_shift (VEC_SHIFT);
		return vector_vh_start.handler();
	} };
	
	
	public static VhStopPtr cchasm_vh_stop = new VhStopPtr() { public void handler() 
	{
		vector_vh_stop.handler();
	} };
}
