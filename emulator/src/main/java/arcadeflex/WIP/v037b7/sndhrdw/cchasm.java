/*
 * Cosmic Chasm sound and other IO hardware emulation
 *
 * Jul 15 1999 by Mathis Rosenhauer
 *
 */

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.sndhrdw;
        
import arcadeflex.common.ptrLib;
import arcadeflex.common.ptrLib.ShortPtr;
import static arcadeflex.v037b7.cpu.z80.z80H.Z80_NMI_INT;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound.ay8910.*;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.z80fmly.*;
import static gr.codebb.arcadeflex.WIP.v037b7.machine.z80fmlyH.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static arcadeflex.v037b7.sound.streams.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;

public class cchasm
{
	
	static int[] sound_status = new int[2];
	static int[] sound_command = new int[2];
	static int sound_flags;
	
	public static ReadHandlerPtr cchasm_snd_io_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	    int coin;
	
	    switch (offset & 0x61 )
	    {
	    case 0x00:
	        coin = (input_port_3_r.handler (offset) >> 4) & 0x7;
	        if (coin != 0x7) coin |= 0x8;
	        return sound_flags | coin;
	
	    case 0x01:
	        return AY8910_read_port_0_r.handler(offset);
	
	    case 0x21:
	        return AY8910_read_port_1_r.handler(offset);
	
	    case 0x40:
	        return sound_command[0] & 0xff;
	
	    case 0x41:
	        sound_flags &= ~0x80;
	        z80ctc_0_trg2_w.handler(0, 0);
	        return sound_command[1] & 0xff;
	    default:
	        logerror("Read from unmapped internal IO device at 0x%x\n", offset + 0x6000);
	        return 0;
	    }
	} };
	
	public static WriteHandlerPtr cchasm_snd_io_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	    switch (offset & 0x61 )
	    {
	    case 0x00:
	        AY8910_control_port_0_w.handler(offset, data);
	        break;
	
	    case 0x01:
	        AY8910_write_port_0_w.handler(offset, data);
	        break;
	
	    case 0x20:
	        AY8910_control_port_1_w.handler(offset, data);
	        break;
	
	    case 0x21:
	        AY8910_write_port_1_w.handler(offset, data);
	        break;
	
	    case 0x40:
	        sound_status[0] = data;
	        break;
	
	    case 0x41:
	        sound_flags |= 0x40;
	        sound_status[1] = data;
	        cpu_cause_interrupt(0,1);
	        break;
	
	    case 0x61:
	        z80ctc_0_trg0_w.handler(0, 0);
	        break;
	
	    default:
	        logerror("Write %x to unmapped internal IO device at 0x%x\n", data, offset + 0x6000);
	    }
	} };
        
        static int led;
	
	public static WriteHandlerPtr cchasm_io_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	    
	    switch ((offset >> 1) & 0xf)
	    {
	    case 0:
	        sound_command[0] = data >> 8;
	        break;
	    case 1:
	        sound_flags |= 0x80;
	        sound_command[1] = data >> 8;
	        z80ctc_0_trg2_w.handler(0, 1);
	        cpu_cause_interrupt( 1, Z80_NMI_INT );
	        break;
	    case 2:
	        led = data;
	        break;
	    }
	} };
	
	public static ReadHandlerPtr cchasm_io_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
	    switch ((offset >> 1) & 0xf)
	    {
	    case 0x0:
	        return sound_status[0] << 8;
	    case 0x1:
	        sound_flags &= ~0x40;
	        return sound_status[1] << 8;
	    case 0x2:
	        return (sound_flags| (input_port_3_r.handler (offset) & 0x07) | 0x08) << 8;
	    case 0x5:
	        return input_port_2_r.handler (offset) << 8;
	    case 0x8:
	        return input_port_1_r.handler (offset) << 8;
	    default:
	        return 0xff << 8;
	    }
	} };
	
	static int[] channel=new int[2], channel_active=new int[2];
	static int[] output=new int[2];
	
	static IntrPtr ctc_interrupt = new IntrPtr() {
            @Override
            public void handler(int state) {
                cpu_cause_interrupt (1, Z80_VECTOR(0,state) );
            }
        };
        
	public static WriteHandlerPtr ctc_timer_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	
	    if (data != 0) /* rising edge */
	    {
	        output[0] ^= 0x7f;
	        channel_active[0] = 1;
	        stream_update(channel[0], 0);
	    }
	} };
	
	public static WriteHandlerPtr ctc_timer_2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
	
	    if (data != 0) /* rising edge */
	    {
	        output[1] ^= 0x7f;
	        channel_active[1] = 1;
	        stream_update(channel[1], 0);
	    }
	} };
	
	static z80ctc_interface ctc_intf = new z80ctc_interface
	(
		1,                   /* 1 chip */
		new int[]{ 0 },               /* clock (filled in from the CPU 0 clock */
		new int[]{ 0 },               /* timer disables */
		new IntrPtr[]{ ctc_interrupt },   /* interrupt handler */
		new WriteHandlerPtr[]{ null },               /* ZC/TO0 callback */
		new WriteHandlerPtr[]{ ctc_timer_1_w },     /* ZC/TO1 callback */
		new WriteHandlerPtr[]{ ctc_timer_2_w }      /* ZC/TO2 callback */
	);
	
	static StreamInitPtr tone_update = new StreamInitPtr() {
            @Override
            public void handler(int num, ShortPtr buffer, int length) {
                int out = 0;
	
		if (channel_active[num] != 0)
			out = output[num] << 8;
	
		while (length-- != 0) buffer.writeinc((short) out);
		channel_active[num] = 0;
            }
        };
        	
	public static ShStartPtr cchasm_sh_start = new ShStartPtr() { public int handler(MachineSound msound) 
	{
	    sound_status[0] = 0; sound_status[1] = 0;
	    sound_command[0] = 0; sound_command[1] = 0;
	    sound_flags = 0;
	    output[0] = 0; output[1] = 0;
	
	    channel[0] = stream_init("CTC sound 1", 50, Machine.sample_rate, 0, tone_update);
	    channel[1] = stream_init("CTC sound 2", 50, Machine.sample_rate, 1, tone_update);
	
		ctc_intf.baseclock[0] = Machine.drv.cpu[1].cpu_clock;
		z80ctc_init (ctc_intf);
	
		return 0;
	} };
	
	public static ShUpdatePtr cchasm_sh_update = new ShUpdatePtr() { public void handler() 
	{
	    if ((input_port_3_r.handler(0) & 0x70) != 0x70)
	        z80ctc_0_trg0_w.handler(0, 1);
	} };
	
	
}
