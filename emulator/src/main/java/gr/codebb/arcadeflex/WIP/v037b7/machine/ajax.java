/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package gr.codebb.arcadeflex.WIP.v037b7.machine;

//common imports
import static arcadeflex.common.libc.cstdlib.*;

import static gr.codebb.arcadeflex.WIP.v037b7.cpu.konami.konamiH.KONAMI_INT_IRQ;
import static gr.codebb.arcadeflex.v037b7.cpu.m6809.m6809H.*;
import static arcadeflex.v037b7.mame.commonH.REGION_CPU1;
import static arcadeflex.v037b7.mame.commonH.REGION_CPU2;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_cause_interrupt;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_get_pc;
import static arcadeflex.v037b7.mame.cpuintrf.ignore_interrupt;
import static arcadeflex.v037b7.mame.cpuintrf.watchdog_reset_w;
import static arcadeflex.v037b7.mame.cpuintrfH.ASSERT_LINE;
import static arcadeflex.v037b7.mame.cpuintrfH.CLEAR_LINE;
import static arcadeflex.v037b7.mame.inptport.readinputport;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.set_led_status;
import static arcadeflex.v037b7.mame.memoryH.cpu_setbank;
import static arcadeflex.v037b7.mame.sndintrf.soundlatch_w;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051960_is_IRQ_enabled;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.mame.common.coin_counter_w;
import static arcadeflex.v037b7.mame.common.memory_region;
import arcadeflex.v037b7.generic.funcPtr.InitMachinePtr;
import arcadeflex.v037b7.generic.funcPtr.InterruptPtr;
import arcadeflex.v037b7.generic.funcPtr.ReadHandlerPtr;
import arcadeflex.v037b7.generic.funcPtr.WriteHandlerPtr;
import static arcadeflex.v037b7.cpu.z80.z80H.Z80_IRQ_INT;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.ajax.*;
import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.konamiic.K051316_wraparound_enable;
import static arcadeflex.v037b7.vidhrdw.konamiic.K052109_set_RMRD_line;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;

public class ajax
{
	
	public static UBytePtr ajax_sharedram=new UBytePtr();
	static int firq_enable;
	
	/*	ajax_bankswitch_w:
		Handled by the LS273 Octal +ve edge trigger D-type Flip-flop with Reset at H11:
	
		Bit	Description
		---	-----------
		7	MRB3	Selects ROM N11/N12
		6	CCOUNT2	Coin Counter 2	(*)
		5	CCOUNT1	Coin Counter 1	(*)
		4	SRESET	Slave CPU Reset?
		3	PRI0	Layer Priority Selector
		2	MRB2	\
		1	MRB1	 |	ROM Bank Select
		0	MRB0	/
	
		(*)	The Coin Counters are handled by the Konami Custom 051550
	*/
	
	public static WriteHandlerPtr ajax_bankswitch_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		UBytePtr RAM = memory_region(REGION_CPU1);
		int bankaddress = 0;
	
		/* rom select */
		if ((data & 0x80)==0)	bankaddress += 0x8000;
	
		/* coin counters */
		coin_counter_w.handler(0,data & 0x20);
		coin_counter_w.handler(1,data & 0x40);
	
		/* priority */
		u8_ajax_priority = data & 0x08;
	
		/* bank # (ROMS N11 and N12) */
		bankaddress += 0x10000 + (data & 0x07)*0x2000;
		cpu_setbank(2,new UBytePtr(RAM,bankaddress));
	} };
	
	/*	ajax_lamps_w:
	  	Handled by the LS273 Octal +ve edge trigger D-type Flip-flop with Reset at B9:
	
		Bit	Description
		---	-----------
		7	LAMP7 & LAMP8 - Game over lamps (*)
		6	LAMP3 & LAMP4 - Game over lamps (*)
		5	LAMP1 - Start lamp (*)
		4	Control panel quaking (**)
		3	Joystick vibration (**)
		2	LAMP5 & LAMP6 - Power up lamps (*)
		1	LAMP2 - Super weapon lamp (*)
		0	unused
	
		(*) The Lamps are handled by the M54585P
		(**)Vibration/Quaking handled by these chips:
			Chip		Location	Description
			----		--------	-----------
			PS2401-4	B21			???
			UPA1452H	B22			???
			LS74		H2			Dual +ve edge trigger D-type Flip-flop with SET and RESET
			LS393		C20			Dual -ve edge trigger 4-bit Binary Ripple Counter with Resets
	*/
	
	public static WriteHandlerPtr ajax_lamps_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		set_led_status(1,data & 0x02);	/* super weapon lamp */
		set_led_status(2,data & 0x04);	/* power up lamps */
		set_led_status(5,data & 0x04);	/* power up lamps */
		set_led_status(0,data & 0x20);	/* start lamp */
		set_led_status(3,data & 0x40);	/* game over lamps */
		set_led_status(6,data & 0x40);	/* game over lamps */
		set_led_status(4,data & 0x80);	/* game over lamps */
		set_led_status(7,data & 0x80);	/* game over lamps */
	} };
	
	/*	ajax_ls138_f10:
		The LS138 1-of-8 Decoder/Demultiplexer at F10 selects what to do:
	
		Address	R/W	Description
		-------	---	-----------
		0x0000	(r)	???	I think this read is because a CPU core bug
				(w)	0x0000	NSFIRQ	Trigger FIRQ on the M6809
					0x0020	AFR		Watchdog reset (handled by the 051550)
		0x0040	(w)	SOUND			Cause interrupt on the Z80
		0x0080	(w)	SOUNDDATA		Sound code number
		0x00c0	(w) MBL1			Enables the LS273 at H11 (Banking + Coin counters)
		0x0100	(r) MBL2			Enables 2P Inputs reading
		0x0140	(w) MBL3			Enables the LS273 at B9 (Lamps + Vibration)
		0x0180	(r) MIO1			Enables 1P Inputs + DIPSW #1 & #2 reading
		0x01c0	(r) MIO2			Enables DIPSW #3 reading
	*/
	
	public static ReadHandlerPtr ajax_ls138_f10_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int data = 0;
	
		switch ((offset & 0x01c0) >> 6){
			case 0x00:	/* ??? */
				data = rand();
				break;
			case 0x04:	/* 2P inputs */
				data = readinputport(5);
				break;
			case 0x06:	/* 1P inputs + DIPSW #1 & #2 */
				if ((offset & 0x02) != 0)
					data = readinputport(offset & 0x01);
				else
					data = readinputport(3 + (offset & 0x01));
				break;
			case 0x07:	/* DIPSW #3 */
				data = readinputport(2);
				break;
	
			default:
				logerror("%04x: (ls138_f10) read from an unknown address %02x\n",cpu_get_pc(), offset);
		}
	
		return data;
	} };
	
	public static WriteHandlerPtr ajax_ls138_f10_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		switch ((offset & 0x01c0) >> 6){
			case 0x00:	/* NSFIRQ + AFR */
				if (offset != 0)
					watchdog_reset_w.handler(0, data);
				else{
					if (firq_enable != 0)	/* Cause interrupt on slave CPU */
						cpu_cause_interrupt(1,M6809_INT_FIRQ);
				}
				break;
			case 0x01:	/* Cause interrupt on audio CPU */
				cpu_cause_interrupt(2,Z80_IRQ_INT);
				break;
			case 0x02:	/* Sound command number */
				soundlatch_w.handler(offset,data);
				break;
			case 0x03:	/* Bankswitch + coin counters + priority*/
				ajax_bankswitch_w.handler(0, data);
				break;
			case 0x05:	/* Lamps + Joystick vibration + Control panel quaking */
				ajax_lamps_w.handler(0, data);
				break;
	
			default:
				logerror("%04x: (ls138_f10) write %02x to an unknown address %02x\n",cpu_get_pc(), data, offset);
		}
	} };
	
	/* Shared RAM between the 052001 and the 6809 (6264SL at I8) */
	public static ReadHandlerPtr ajax_sharedram_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return ajax_sharedram.read(offset);
	} };
	
	public static WriteHandlerPtr ajax_sharedram_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		ajax_sharedram.write(offset, data);
	} };
	
	/*	ajax_bankswitch_w_2:
		Handled by the LS273 Octal +ve edge trigger D-type Flip-flop with Reset at K14:
	
		Bit	Description
		---	-----------
		7	unused
		6	RMRD	Enable char ROM reading through the video RAM
		5	RVO		enables 051316 wraparound
		4	FIRQST	FIRQ control
		3	SRB3	\
		2	SRB2	 |
		1	SRB1	 |	ROM Bank Select
		0	SRB0	/
	*/
	
	public static WriteHandlerPtr ajax_bankswitch_2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		UBytePtr RAM = memory_region(REGION_CPU2);
		int bankaddress;
	
		/* enable char ROM reading through the video RAM */
		K052109_set_RMRD_line((data & 0x40)!=0 ? ASSERT_LINE : CLEAR_LINE);
	
		/* bit 5 enables 051316 wraparound */
		K051316_wraparound_enable(0, data & 0x20);
	
		/* FIRQ control */
		firq_enable = data & 0x10;
	
		/* bank # (ROMS G16 and I16) */
		bankaddress = 0x10000 + (data & 0x0f)*0x2000;
		cpu_setbank(1,new UBytePtr(RAM,bankaddress));
	} };
	
	
	public static InitMachinePtr ajax_init_machine = new InitMachinePtr() { public void handler() 
	{
		firq_enable = 1;
	} };
	
	public static InterruptPtr ajax_interrupt = new InterruptPtr() { public int handler() 
	{
		if (K051960_is_IRQ_enabled()!=0)
			return KONAMI_INT_IRQ;
		else
			return ignore_interrupt.handler();
	} };
}
