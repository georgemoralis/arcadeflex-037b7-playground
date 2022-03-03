/***************************************************************************

	Midway/Williams Audio Board
	---------------------------

	6809 MEMORY MAP

	Function                                  Address     R/W  Data
	---------------------------------------------------------------
	Program RAM                               0000-07FF   R/W  D0-D7

	Music (YM-2151)                           2000-2001   R/W  D0-D7

	6821 PIA                                  4000-4003   R/W  D0-D7

	HC55516 clock low, digit latch            6000        W    D0
	HC55516 clock high                        6800        W    xx

	Bank select                               7800        W    D0-D2

	Banked Program ROM                        8000-FFFF   R    D0-D7

****************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.sndhrdw;

import arcadeflex.common.ptrLib;
import arcadeflex.common.ptrLib.ShortPtr;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.machine._6821pia.*;
import static arcadeflex.v037b7.machine._6821piaH.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.driverH.CPU_FLAGS_MASK;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.sound._2151intf.*;
import static arcadeflex.v037b7.sound.dac.*;
import static arcadeflex.v056.mame.timer.*;
import static arcadeflex.v056.mame.timerH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.ym2151.*;
import static gr.codebb.arcadeflex.v037b7.cpu.m6809.m6809H.M6809_CC;
import static gr.codebb.arcadeflex.v037b7.cpu.m6809.m6809H.M6809_FIRQ_LINE;
import static gr.codebb.arcadeflex.v037b7.cpu.m6809.m6809H.M6809_Y;
import static arcadeflex.v037b7.sound.streams.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.v037b7.cpu.m6809.m6809H.M6809_IRQ_LINE;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static arcadeflex.v037b7.machine._6821pia.*;
import static arcadeflex.v037b7.mame.sndintrf.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound._2151intfH.*;
import arcadeflex.v037b7.sound.dacH.DACinterface;
import static arcadeflex.v037b7.sound.mixerH.*;
import static gr.codebb.arcadeflex.WIP.v037b7.sound.hc55516.*;
import gr.codebb.arcadeflex.WIP.v037b7.sound.hc55516H.hc55516_interface;
import static gr.codebb.arcadeflex.common.libc.cstring.*;

public class williams
{
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///		COMPILER SWITCHES
/*TODO*///	****************************************************************************/
/*TODO*///	
	static int DISABLE_FIRQ_SPEEDUP		= 0;
	static int DISABLE_LOOP_CATCHERS	= 0;
/*TODO*///	#define DISABLE_DCS_SPEEDUP			0
	
	
	/***************************************************************************
		CONSTANTS (from HC55516.c)
	****************************************************************************/
	
        public static final double INTEGRATOR_LEAK_TC		= 0.001;
        public static final double FILTER_DECAY_TC              = 0.004;
        public static final double FILTER_CHARGE_TC		= 0.004;
        public static final double FILTER_MIN                   = 0.0416;
        public static final double FILTER_MAX                   = 1.0954;
        public static final double SAMPLE_GAIN                  = 10000.0;

	public static final int WILLIAMS_CVSD			= 0;
        public static final int WILLIAMS_ADPCM			= 1;
        public static final int WILLIAMS_NARC			= 2;
        public static final int WILLIAMS_DCS			= 3;

        public static final int DCS_BUFFER_SIZE			= 4096;
        public static final int DCS_BUFFER_MASK			= (DCS_BUFFER_SIZE - 1);
	
	
	/***************************************************************************
		STRUCTURES
	****************************************************************************/
	
	public static class ym2151_state
	{
		double	timer_base;
		double[]	timer_period=new double[3];
		int[]	timer_value=new int[2];
		int[]	timer_is_active=new int[2];
		int	current_register;
		int	active_timer;
	};
	
	public static class counter_state
	{
		UBytePtr	downcount = new UBytePtr();
		UBytePtr	divisor = new UBytePtr();
		UBytePtr	value = new UBytePtr();
		int	adjusted_divisor;
		int	last_hotspot_counter;
		int	hotspot_hit_count;
		int	hotspot_start;
		int	hotspot_stop;
		int	last_read_pc;
		double	time_leftover;
		timer_entry	update_timer;
		timer_entry	enable_timer;
		int	invalid;
	};
	
	public static class cvsd_state
	{
		UBytePtr state = new UBytePtr();
		UBytePtr address = new UBytePtr();
		UBytePtr end = new UBytePtr();
		UBytePtr bank = new UBytePtr();
		int	bits_per_firq;
		int	sample_step;
		int	sample_position;
		int	current_sample;
		int	invalid;
		double	charge;
		double	decay;
		double	leak;
		double	integrator;
		double	filter;
		int	shiftreg;
	};
	
	public static class dac_state
	{
		UBytePtr	state_bank = new UBytePtr();
		UBytePtr	address = new UBytePtr();
		UBytePtr	end = new UBytePtr();
		UBytePtr	volume = new UBytePtr();
		int	bytes_per_firq;
		int	sample_step;
		int	sample_position;
		int	current_sample;
		int	invalid;
	};
	
	public static class dcs_state
	{
		UBytePtr  mem = new UBytePtr();
		int	size;
		int	incs;
		timer_entry reg_timer;
		int		ireg;
		int	ireg_base;
		int[]	control_regs=new int[32];
		int	bank;
		int	enabled;
	
		ShortPtr 	buffer;
		int	buffer_in;
		int	sample_step;
		int	sample_position;
		int	current_sample;
		int	latch_control;
	};
	
	
	/***************************************************************************
		STATIC GLOBALS
	****************************************************************************/
	
	static int williams_sound_int_state;

	static int williams_cpunum;
	static int williams_pianum;

	static int williams_audio_type;
/*TODO*///	static UINT8 adpcm_bank_count;

	static counter_state counter = new counter_state();
	static ym2151_state _ym2151 = new ym2151_state();
	static cvsd_state cvsd = new cvsd_state();
	static dac_state _dac = new dac_state();
	static dcs_state _dcs = new dcs_state();

	static int dac_stream;
	static int cvsd_stream;

/*TODO*///	static UINT8 *dcs_speedup1;
/*TODO*///	static UINT8 *dcs_speedup2;
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///		PROTOTYPES
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	static void locate_audio_hotspot(UINT8 *base, UINT16 start);
/*TODO*///	
/*TODO*///	static 
/*TODO*///	static static 
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static static void cvsd_update(int num, INT16 *buffer, int length);
/*TODO*///	static void dac_update(int num, INT16 *buffer, int length);
/*TODO*///	
/*TODO*///	static INT16 get_next_cvsd_sample(int bit);
/*TODO*///	
/*TODO*///	/* ADSP */
/*TODO*///	
/*TODO*///	static void sound_tx_callback( int port, INT32 data );
/*TODO*///	
/*TODO*///	static void dcs_dac_update(int num, INT16 *buffer, int length);
/*TODO*///	static 
        
        /***************************************************************************
		YM2151 INTERFACES
	****************************************************************************/
	
	public static ReadHandlerPtr williams_ym2151_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return YM2151_status_port_0_r.handler(offset);
	} };
	
	public static WriteHandlerPtr williams_ym2151_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((offset & 1) != 0)
		{
			/* handle timer registers here */
			switch (_ym2151.current_register)
			{
				case 0x10:	/* timer A, upper 8 bits */
					update_counter();
					_ym2151.timer_value[0] = (_ym2151.timer_value[0] & 0x003) | (data << 2);
					_ym2151.timer_period[0] = _ym2151.timer_base * (double)(1024 - _ym2151.timer_value[0]);
					break;
	
				case 0x11:	/* timer A, upper 8 bits */
					update_counter();
					_ym2151.timer_value[0] = (_ym2151.timer_value[0] & 0x3fc) | (data & 0x03);
					_ym2151.timer_period[0] = _ym2151.timer_base * (double)(1024 - _ym2151.timer_value[0]);
					break;
	
				case 0x12:	/* timer B */
					update_counter();
					_ym2151.timer_value[1] = data;
					_ym2151.timer_period[1] = _ym2151.timer_base * (double)((256 - _ym2151.timer_value[1]) << 4);
					break;
	
				case 0x14:	/* timer control */
	
					/* enable/disable timer A */
					if ((data & 0x01)!=0 && (data & 0x04)!=0 && _ym2151.timer_is_active[0]==0)
					{
						update_counter();
						_ym2151.timer_is_active[0] = 1;
						_ym2151.active_timer = 0;
					}
					else if ((data & 0x01)==0 && _ym2151.timer_is_active[0]!=0)
					{
						_ym2151.timer_is_active[0] = 0;
						_ym2151.active_timer = _ym2151.timer_is_active[1]!=0 ? 1 : 2;
					}
	
					/* enable/disable timer B */
					if ((data & 0x02)!=0 && (data & 0x08)!=0 && _ym2151.timer_is_active[1]==0)
					{
						update_counter();
						_ym2151.timer_is_active[1] = 1;
						_ym2151.active_timer = 1;
					}
					else if ((data & 0x02)==0 && _ym2151.timer_is_active[1]!=0)
					{
						_ym2151.timer_is_active[1] = 0;
						_ym2151.active_timer = _ym2151.timer_is_active[0]!=0 ? 0 : 2;
					}
					break;
	
				default:	/* pass through everything else */
					YM2151_data_port_0_w.handler(offset, data);
					break;
			}
		}
		else
		{
			if (DISABLE_FIRQ_SPEEDUP==0)
				_ym2151.current_register = data;
	
			/* only pass through register writes for non-timer registers */
			if (DISABLE_FIRQ_SPEEDUP!=0 || _ym2151.current_register < 0x10 || _ym2151.current_register > 0x14)
				YM2151_register_port_0_w.handler(offset, data);
		}
	} };
        
        /***************************************************************************
		PIA INTERFACES
	****************************************************************************/
	
	public static ReadHandlerPtr williams_cvsd_pia_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return pia_read(williams_pianum, offset);
	} };
	
	public static WriteHandlerPtr williams_cvsd_pia_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		pia_write(williams_pianum, offset, data);
	} };
        
        /***************************************************************************
		CVSD BANK SELECT
	****************************************************************************/
	
	public static WriteHandlerPtr williams_cvsd_bank_select_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		cpu_setbank(6, get_cvsd_bank_base(data));
	} };
	
	/***************************************************************************
		PROCESSOR STRUCTURES
	****************************************************************************/
	
	/* CVSD readmem/writemem structures */
	public static MemoryReadAddress williams_cvsd_readmem[] =
	{
		new MemoryReadAddress( 0x0000, 0x07ff, MRA_RAM ),
		new MemoryReadAddress( 0x2000, 0x2001, williams_ym2151_r ),
		new MemoryReadAddress( 0x4000, 0x4003, williams_cvsd_pia_r ),
		new MemoryReadAddress( 0x8000, 0xffff, MRA_BANK6 ),
		new MemoryReadAddress( -1 )
	};
	
	public static MemoryWriteAddress williams_cvsd_writemem[] =
	{
		new MemoryWriteAddress( 0x0000, 0x07ff, MWA_RAM ),
		new MemoryWriteAddress( 0x2000, 0x2001, williams_ym2151_w ),
		new MemoryWriteAddress( 0x4000, 0x4003, williams_cvsd_pia_w ),
		new MemoryWriteAddress( 0x6000, 0x6000, hc55516_0_digit_clock_clear_w ),
		new MemoryWriteAddress( 0x6800, 0x6800, hc55516_0_clock_set_w ),
		new MemoryWriteAddress( 0x7800, 0x7800, williams_cvsd_bank_select_w ),
		new MemoryWriteAddress( 0x8000, 0xffff, MWA_ROM ),
		new MemoryWriteAddress( -1 )
	};
	
/*TODO*///	
/*TODO*///	/* ADPCM readmem/writemem structures */
/*TODO*///	static MemoryReadAddress williams_adpcm_readmem[] =
/*TODO*///	{
/*TODO*///		new MemoryReadAddress( 0x0000, 0x1fff, MRA_RAM ),
/*TODO*///		new MemoryReadAddress( 0x2401, 0x2401, williams_ym2151_r ),
/*TODO*///		new MemoryReadAddress( 0x2c00, 0x2c00, OKIM6295_status_0_r ),
/*TODO*///		new MemoryReadAddress( 0x3000, 0x3000, williams_adpcm_command_r ),
/*TODO*///		new MemoryReadAddress( 0x4000, 0xbfff, MRA_BANK6 ),
/*TODO*///		new MemoryReadAddress( 0xc000, 0xffff, MRA_ROM ),
/*TODO*///		new MemoryReadAddress( -1 )
/*TODO*///	};
/*TODO*///	
/*TODO*///	static MemoryWriteAddress williams_adpcm_writemem[] =
/*TODO*///	{
/*TODO*///		new MemoryWriteAddress( 0x0000, 0x1fff, MWA_RAM ),
/*TODO*///		new MemoryWriteAddress( 0x2000, 0x2000, williams_adpcm_bank_select_w ),
/*TODO*///		new MemoryWriteAddress( 0x2400, 0x2401, williams_ym2151_w ),
/*TODO*///		new MemoryWriteAddress( 0x2800, 0x2800, williams_dac_data_w ),
/*TODO*///		new MemoryWriteAddress( 0x2c00, 0x2c00, OKIM6295_data_0_w ),
/*TODO*///		new MemoryWriteAddress( 0x3400, 0x3400, williams_adpcm_6295_bank_select_w ),
/*TODO*///		new MemoryWriteAddress( 0x3c00, 0x3c00, MWA_NOP ),/*mk_sound_talkback_w }, -- talkback port? */
/*TODO*///		{ 0x4000, 0xffff, MWA_ROM },
/*TODO*///		{ -1 }
/*TODO*///	};
/*TODO*///	
/*TODO*///	
/*TODO*///	/* NARC master readmem/writemem structures */
/*TODO*///	static MemoryReadAddress williams_narc_master_readmem[] =
/*TODO*///	{
/*TODO*///		new MemoryReadAddress( 0x0000, 0x1fff, MRA_RAM ),
/*TODO*///		new MemoryReadAddress( 0x2001, 0x2001, williams_ym2151_r ),
/*TODO*///		new MemoryReadAddress( 0x3000, 0x3000, MRA_NOP ),
/*TODO*///		new MemoryReadAddress( 0x3400, 0x3400, williams_narc_command_r ),
/*TODO*///		new MemoryReadAddress( 0x4000, 0xbfff, MRA_BANK6 ),
/*TODO*///		new MemoryReadAddress( 0xc000, 0xffff, MRA_ROM ),
/*TODO*///		new MemoryReadAddress( -1 )
/*TODO*///	};
/*TODO*///	
/*TODO*///	static MemoryWriteAddress williams_narc_master_writemem[] =
/*TODO*///	{
/*TODO*///		new MemoryWriteAddress( 0x0000, 0x1fff, MWA_RAM ),
/*TODO*///		new MemoryWriteAddress( 0x2000, 0x2001, williams_ym2151_w ),
/*TODO*///		new MemoryWriteAddress( 0x2800, 0x2800, MWA_NOP ),/*mk_sound_talkback_w }, -- talkback port? */
/*TODO*///		{ 0x2c00, 0x2c00, williams_narc_command2_w },
/*TODO*///		{ 0x3000, 0x3000, williams_dac_data_w },
/*TODO*///		{ 0x3800, 0x3800, williams_narc_master_bank_select_w },
/*TODO*///		{ 0x4000, 0xffff, MWA_ROM },
/*TODO*///		{ -1 }
/*TODO*///	};
/*TODO*///	
/*TODO*///	
/*TODO*///	/* NARC slave readmem/writemem structures */
/*TODO*///	static MemoryReadAddress williams_narc_slave_readmem[] =
/*TODO*///	{
/*TODO*///		new MemoryReadAddress( 0x0000, 0x1fff, MRA_RAM ),
/*TODO*///		new MemoryReadAddress( 0x3000, 0x3000, MRA_NOP ),
/*TODO*///		new MemoryReadAddress( 0x3400, 0x3400, williams_narc_command2_r ),
/*TODO*///		new MemoryReadAddress( 0x4000, 0xbfff, MRA_BANK5 ),
/*TODO*///		new MemoryReadAddress( 0xc000, 0xffff, MRA_ROM ),
/*TODO*///		new MemoryReadAddress( -1 )
/*TODO*///	};
/*TODO*///	
/*TODO*///	static MemoryWriteAddress williams_narc_slave_writemem[] =
/*TODO*///	{
/*TODO*///		new MemoryWriteAddress( 0x0000, 0x1fff, MWA_RAM ),
/*TODO*///		new MemoryWriteAddress( 0x2000, 0x2000, hc55516_0_clock_set_w ),
/*TODO*///		new MemoryWriteAddress( 0x2400, 0x2400, hc55516_0_digit_clock_clear_w ),
/*TODO*///		new MemoryWriteAddress( 0x3000, 0x3000, williams_dac2_data_w ),
/*TODO*///		new MemoryWriteAddress( 0x3800, 0x3800, williams_narc_slave_bank_select_w ),
/*TODO*///		new MemoryWriteAddress( 0x3c00, 0x3c00, MWA_NOP ),
/*TODO*///		new MemoryWriteAddress( 0x4000, 0xffff, MWA_ROM ),
/*TODO*///		new MemoryWriteAddress( -1 )
/*TODO*///	};
/*TODO*///	
/*TODO*///	
/*TODO*///	/* DCS readmem/writemem structures */
/*TODO*///	static MemoryReadAddress williams_dcs_readmem[] =
/*TODO*///	{
/*TODO*///		new MemoryReadAddress( ADSP_DATA_ADDR_RANGE(0x0000, 0x1fff), MRA_RAM ),						/* ??? */
/*TODO*///		new MemoryReadAddress( ADSP_DATA_ADDR_RANGE(0x2000, 0x2fff), williams_dcs_bank_r ),			/* banked roms read */
/*TODO*///		new MemoryReadAddress( ADSP_DATA_ADDR_RANGE(0x3400, 0x3400), williams_dcs_latch_r ),			/* soundlatch read */
/*TODO*///		new MemoryReadAddress( ADSP_DATA_ADDR_RANGE(0x3800, 0x39ff), MRA_RAM ),						/* internal data ram */
/*TODO*///		new MemoryReadAddress( ADSP_PGM_ADDR_RANGE (0x0000, 0x1fff), MRA_RAM ),						/* internal/external program ram */
/*TODO*///		new MemoryReadAddress( -1 )  /* end of table */
/*TODO*///	};
/*TODO*///	
/*TODO*///	
/*TODO*///	static MemoryWriteAddress williams_dcs_writemem[] =
/*TODO*///	{
/*TODO*///		new MemoryWriteAddress( ADSP_DATA_ADDR_RANGE(0x0000, 0x1fff), MWA_RAM ),						/* ??? */
/*TODO*///		new MemoryWriteAddress( ADSP_DATA_ADDR_RANGE(0x3000, 0x3000), williams_dcs_bank_select_w ),	/* bank selector */
/*TODO*///		new MemoryWriteAddress( ADSP_DATA_ADDR_RANGE(0x3400, 0x3400), williams_dcs_latch_w ),			/* soundlatch write */
/*TODO*///		new MemoryWriteAddress( ADSP_DATA_ADDR_RANGE(0x3800, 0x39ff), MWA_RAM ),						/* internal data ram */
/*TODO*///		new MemoryWriteAddress( ADSP_DATA_ADDR_RANGE(0x3fe0, 0x3fff), williams_dcs_control_w ),		/* adsp control regs */
/*TODO*///		new MemoryWriteAddress( ADSP_PGM_ADDR_RANGE (0x0000, 0x1fff), MWA_RAM ),						/* internal/external program ram */
/*TODO*///		new MemoryWriteAddress( -1 )  /* end of table */
/*TODO*///	};
	
	
	
	
	
	/***************************************************************************
		AUDIO STRUCTURES
	****************************************************************************/
	
	static WriteYmHandlerPtr williams_cvsd_ym2151_irq = new WriteYmHandlerPtr() {
            @Override
            public void handler(int state) {
                pia_set_input_ca1(williams_pianum, state!=0?0:1);
            }
        };
	
	/* YM2151 structure (CVSD variant) */
	public static YM2151interface williams_cvsd_ym2151_interface = new YM2151interface
	(
		1,			/* 1 chip */
		3579580,
		new int[] { YM3012_VOL(10,MIXER_PAN_CENTER,10,MIXER_PAN_CENTER) },
		new WriteYmHandlerPtr[] { williams_cvsd_ym2151_irq }
	);
	
/*TODO*///	/* YM2151 structure (ADPCM variant) */
/*TODO*///	static YM2151interface williams_adpcm_ym2151_interface = new YM2151interface
/*TODO*///	(
/*TODO*///		1,			/* 1 chip */
/*TODO*///		3579580,
/*TODO*///		new int[] { YM3012_VOL(10,MIXER_PAN_CENTER,10,MIXER_PAN_CENTER) },
/*TODO*///		new WriteYmHandlerPtr[] { williams_adpcm_ym2151_irq }
/*TODO*///	);
	
	/* DAC structure (CVSD variant) */
	public static DACinterface williams_cvsd_dac_interface = new DACinterface
	(
		1,
		new int[] { 50 }
	);
	
/*TODO*///	/* DAC structure (ADPCM variant) */
/*TODO*///	static DACinterface williams_adpcm_dac_interface = new DACinterface
/*TODO*///	(
/*TODO*///		1,
/*TODO*///		new int[] { 50 }
/*TODO*///	);
/*TODO*///	
/*TODO*///	/* DAC structure (NARC variant) */
/*TODO*///	static DACinterface williams_narc_dac_interface = new DACinterface
/*TODO*///	(
/*TODO*///		2,
/*TODO*///		new int[] { 50, 50 }
/*TODO*///	);
	
	/* CVSD structure */
	public static hc55516_interface williams_cvsd_interface = new hc55516_interface
	(
		1,			/* 1 chip */
		new int[] { 80 }
	);
	
/*TODO*///	/* OKIM6295 structure(s) */
/*TODO*///	static OKIM6295interface williams_adpcm_6295_interface_REGION_SOUND1 = new OKIM6295interface
/*TODO*///	(
/*TODO*///		1,          	/* 1 chip */
/*TODO*///		new int[] { 8000 },       /* 8000 Hz frequency */
/*TODO*///		new int[] { REGION_SOUND1 },  /* memory */
/*TODO*///		new int[] { 50 }
/*TODO*///	);
/*TODO*///	
/*TODO*///	/* Custom structure (DCS variant) */
/*TODO*///	static CustomSound_interface williams_dcs_custom_interface = new CustomSound_interface
/*TODO*///	(
/*TODO*///		dcs_custom_start,dcs_custom_stop,null
/*TODO*///	);
	
	
	/***************************************************************************
		INLINES
	****************************************************************************/
	
	static int get_cvsd_address()
	{
		if (cvsd.address != null)
			return cvsd.address.read(0) * 256 + cvsd.address.read(1);
		else
			return cpunum_get_reg(williams_cpunum, M6809_Y);
	}
	
	static void set_cvsd_address(int address)
	{
		if (cvsd.address != null)
		{
			cvsd.address.write(0, address >> 8);
			cvsd.address.write(1, address);
		}
		else
			cpunum_set_reg(williams_cpunum, M6809_Y, address);
	}
	
	static int get_dac_address()
	{
		if (_dac.address != null)
			return _dac.address.read(0) * 256 + _dac.address.read(1);
		else
			return cpunum_get_reg(williams_cpunum, M6809_Y);
	}
	
	static void set_dac_address(int address)
	{
		if (_dac.address != null)
		{
			_dac.address.write(0, address >> 8);
			_dac.address.write(1, address);
		}
		else
			cpunum_set_reg(williams_cpunum, M6809_Y, address);
	}
	
	static UBytePtr get_cvsd_bank_base(int data)
	{
		UBytePtr RAM = new UBytePtr(memory_region(REGION_CPU1+williams_cpunum));
		int bank = data & 3;
		int quarter = (data >> 2) & 3;
		if (bank == 3) bank = 0;
		return new UBytePtr(RAM, 0x10000 + (bank * 0x20000) + (quarter * 0x8000));
	}
	
	static UBytePtr get_adpcm_bank_base(int data)
	{
		UBytePtr RAM = new UBytePtr(memory_region(REGION_CPU1+williams_cpunum));
		int bank = data & 7;
		return new UBytePtr(RAM, 0x10000 + (bank * 0x8000));
	}
	
	static UBytePtr get_narc_master_bank_base(int data)
	{
		UBytePtr RAM = new UBytePtr(memory_region(REGION_CPU1+williams_cpunum));
		int bank = data & 3;
		if ((data & 4)==0) bank = 0;
		return new UBytePtr(RAM, 0x10000 + (bank * 0x8000));
	}
	
/*TODO*///	INLINE UINT8 *get_narc_slave_bank_base(int data)
/*TODO*///	{
/*TODO*///		UINT8 *RAM = memory_region(REGION_CPU1+williams_cpunum + 1);
/*TODO*///		int bank = data & 7;
/*TODO*///		return &RAM[0x10000 + (bank * 0x8000)];
/*TODO*///	}
	
	
	/***************************************************************************
		INITIALIZATION
	****************************************************************************/
	
	public static void williams_cvsd_init(int cpunum, int pianum)
	{
		int entry_point;
		UBytePtr RAM = new UBytePtr();
	
		/* configure the CPU */
		williams_cpunum = cpunum;
		williams_audio_type = WILLIAMS_CVSD;
	
		/* configure the PIA */
		williams_pianum = pianum;
		pia_config(pianum, PIA_STANDARD_ORDERING | PIA_8BIT, williams_cvsd_pia_intf);
	
		/* reset the chip */
		williams_cvsd_reset_w(1);
		williams_cvsd_reset_w(0);
	
		/* determine the entry point; from there, we can choose the speedup addresses */
		RAM = memory_region(REGION_CPU1+cpunum);
		entry_point = RAM.read(0x17ffe) * 256 + RAM.read(0x17fff);
		switch (entry_point)
		{
			/* Joust 2 case */
			case 0x8045:
				counter.downcount = install_mem_write_handler(cpunum, 0x217, 0x217, counter_down_w);
				counter.divisor = install_mem_write_handler(cpunum, 0x216, 0x216, counter_divisor_w);
				counter.value 	= install_mem_write_handler(cpunum, 0x214, 0x215, counter_value_w);
	
				install_mem_read_handler(cpunum, 0x217, 0x217, counter_down_r);
				install_mem_read_handler(cpunum, 0x214, 0x215, counter_value_r);
	
				cvsd.state		= install_mem_write_handler(cpunum, 0x220, 0x221, cvsd_state_w);
				cvsd.address	= null;	/* in Y */
				cvsd.end		= new UBytePtr(RAM, 0x21d);
				cvsd.bank		= new UBytePtr(RAM, 0x21f);
				cvsd.bits_per_firq = 1;
	
				_dac.state_bank	= null;
				_dac.address		= null;
				_dac.end			= null;
				_dac.volume		= null;
				_dac.bytes_per_firq = 0;
				break;
	
			/* Arch Rivals case */
			case 0x8067:
				counter.downcount = install_mem_write_handler(cpunum, 0x239, 0x239, counter_down_w);
				counter.divisor = install_mem_write_handler(cpunum, 0x238, 0x238, counter_divisor_w);
				counter.value 	= install_mem_write_handler(cpunum, 0x236, 0x237, counter_value_w);
	
				install_mem_read_handler(cpunum, 0x239, 0x239, counter_down_r);
				install_mem_read_handler(cpunum, 0x236, 0x237, counter_value_r);
	
				cvsd.state		= install_mem_write_handler(cpunum, 0x23e, 0x23f, cvsd_state_w);
				cvsd.address	= null;	/* in Y */
				cvsd.end		= new UBytePtr(RAM, 0x242);
				cvsd.bank		= new UBytePtr(RAM, 0x22b);
				cvsd.bits_per_firq = 1;
	
				_dac.state_bank	= null;
				_dac.address		= null;
				_dac.end			= null;
				_dac.volume		= null;
				_dac.bytes_per_firq = 0;
				break;
	
			/* General CVSD case */
			case 0x80c8:
				counter.downcount = install_mem_write_handler(cpunum, 0x23a, 0x23a, counter_down_w);
				counter.divisor = install_mem_write_handler(cpunum, 0x238, 0x238, counter_divisor_w);
				counter.value 	= install_mem_write_handler(cpunum, 0x236, 0x237, counter_value_w);
	
				install_mem_read_handler(cpunum, 0x23a, 0x23a, counter_down_r);
				install_mem_read_handler(cpunum, 0x236, 0x237, counter_value_r);
	
				cvsd.state		= install_mem_write_handler(cpunum, 0x23f, 0x240, cvsd_state_w);
				cvsd.address	= new UBytePtr(RAM, 0x241);
				cvsd.end		= new UBytePtr(RAM, 0x243);
				cvsd.bank		= new UBytePtr(RAM, 0x22b);
				cvsd.bits_per_firq = 4;
	
				_dac.state_bank	= install_mem_write_handler(cpunum, 0x22c, 0x22c, dac_state_bank_w);
				_dac.address		= null;	/* in Y */
				_dac.end			= new UBytePtr(RAM, 0x234);
				_dac.volume		= new UBytePtr(RAM, 0x231);
				_dac.bytes_per_firq = 2;
				break;
		}
	
		/* find the hotspot for optimization */
		locate_audio_hotspot(new UBytePtr(RAM, 0x8000), 0x8000);
	
		/* reset the IRQ state */
		pia_set_input_ca1(williams_pianum, 1);
	
		/* initialize the global variables */
		init_audio_state(1);
	}
	
/*TODO*///	void williams_adpcm_init(int cpunum)
/*TODO*///	{
/*TODO*///		UINT16 entry_point;
/*TODO*///		UINT8 *RAM;
/*TODO*///		int i;
/*TODO*///	
/*TODO*///		/* configure the CPU */
/*TODO*///		williams_cpunum = cpunum;
/*TODO*///		williams_audio_type = WILLIAMS_ADPCM;
/*TODO*///	
/*TODO*///		/* install the fixed ROM */
/*TODO*///		RAM = memory_region(REGION_CPU1+cpunum);
/*TODO*///		memcpy(&RAM[0xc000], &RAM[0x4c000], 0x4000);
/*TODO*///	
/*TODO*///		/* reset the chip */
/*TODO*///		williams_adpcm_reset_w(1);
/*TODO*///		williams_adpcm_reset_w(0);
/*TODO*///	
/*TODO*///		/* determine the entry point; from there, we can choose the speedup addresses */
/*TODO*///		entry_point = RAM[0xfffe] * 256 + RAM[0xffff];
/*TODO*///		switch (entry_point)
/*TODO*///		{
/*TODO*///			/* General ADPCM case */
/*TODO*///			case 0xdc51:
/*TODO*///			case 0xdd51:
/*TODO*///			case 0xdd55:
/*TODO*///				counter.downcount 	= install_mem_write_handler(cpunum, 0x238, 0x238, counter_down_w);
/*TODO*///				counter.divisor = install_mem_write_handler(cpunum, 0x236, 0x236, counter_divisor_w);
/*TODO*///				counter.value 	= install_mem_write_handler(cpunum, 0x234, 0x235, counter_value_w);
/*TODO*///	
/*TODO*///				install_mem_read_handler(cpunum, 0x238, 0x238, counter_down_r);
/*TODO*///				install_mem_read_handler(cpunum, 0x234, 0x235, counter_value_r);
/*TODO*///	
/*TODO*///				cvsd.state		= NULL;
/*TODO*///				cvsd.address	= NULL;
/*TODO*///				cvsd.end		= NULL;
/*TODO*///				cvsd.bank		= NULL;
/*TODO*///				cvsd.bits_per_firq = 0;
/*TODO*///	
/*TODO*///				dac.state_bank	= install_mem_write_handler(cpunum, 0x22a, 0x22a, dac_state_bank_w);
/*TODO*///				dac.address		= NULL;	/* in Y */
/*TODO*///				dac.end			= &RAM[0x232];
/*TODO*///				dac.volume		= &RAM[0x22f];
/*TODO*///				dac.bytes_per_firq = 1;
/*TODO*///				break;
/*TODO*///	
/*TODO*///			/* Unknown case */
/*TODO*///			default:
/*TODO*///				break;
/*TODO*///		}
/*TODO*///	
/*TODO*///		/* find the number of banks in the ADPCM space */
/*TODO*///		for (i = 0; i < MAX_SOUND; i++)
/*TODO*///			if (Machine.drv.sound[i].sound_type == SOUND_OKIM6295)
/*TODO*///			{
/*TODO*///				struct OKIM6295interface *intf = (struct OKIM6295interface *)Machine.drv.sound[i].sound_interface;
/*TODO*///				adpcm_bank_count = memory_region_length(intf.region[0]) / 0x40000;
/*TODO*///			}
/*TODO*///	
/*TODO*///		/* find the hotspot for optimization */
/*TODO*///	//	locate_audio_hotspot(&RAM[0x40000], 0xc000);
/*TODO*///	
/*TODO*///		/* initialize the global variables */
/*TODO*///		init_audio_state(1);
/*TODO*///	}
/*TODO*///	
/*TODO*///	void williams_narc_init(int cpunum)
/*TODO*///	{
/*TODO*///		UINT16 entry_point;
/*TODO*///		UINT8 *RAM;
/*TODO*///	
/*TODO*///		/* configure the CPU */
/*TODO*///		williams_cpunum = cpunum;
/*TODO*///		williams_audio_type = WILLIAMS_NARC;
/*TODO*///	
/*TODO*///		/* install the fixed ROM */
/*TODO*///		RAM = memory_region(REGION_CPU1+cpunum + 1);
/*TODO*///		memcpy(&RAM[0xc000], &RAM[0x4c000], 0x4000);
/*TODO*///		RAM = memory_region(REGION_CPU1+cpunum);
/*TODO*///		memcpy(&RAM[0xc000], &RAM[0x2c000], 0x4000);
/*TODO*///	
/*TODO*///		/* reset the chip */
/*TODO*///		williams_narc_reset_w(1);
/*TODO*///		williams_narc_reset_w(0);
/*TODO*///	
/*TODO*///		/* determine the entry point; from there, we can choose the speedup addresses */
/*TODO*///		entry_point = RAM[0xfffe] * 256 + RAM[0xffff];
/*TODO*///		switch (entry_point)
/*TODO*///		{
/*TODO*///			/* General ADPCM case */
/*TODO*///			case 0xc060:
/*TODO*///				counter.downcount 	= install_mem_write_handler(cpunum, 0x249, 0x249, counter_down_w);
/*TODO*///				counter.divisor = install_mem_write_handler(cpunum, 0x248, 0x248, counter_divisor_w);
/*TODO*///				counter.value 	= install_mem_write_handler(cpunum, 0x246, 0x247, counter_value_w);
/*TODO*///	
/*TODO*///				install_mem_read_handler(cpunum, 0x249, 0x249, counter_down_r);
/*TODO*///				install_mem_read_handler(cpunum, 0x246, 0x247, counter_value_r);
/*TODO*///	
/*TODO*///				cvsd.state		= NULL;
/*TODO*///				cvsd.address	= NULL;
/*TODO*///				cvsd.end		= NULL;
/*TODO*///				cvsd.bank		= NULL;
/*TODO*///				cvsd.bits_per_firq = 0;
/*TODO*///	
/*TODO*///				dac.state_bank	= install_mem_write_handler(cpunum, 0x23c, 0x23c, dac_state_bank_w);
/*TODO*///				dac.address		= NULL;	/* in Y */
/*TODO*///				dac.end			= &RAM[0x244];
/*TODO*///				dac.volume		= &RAM[0x241];
/*TODO*///				dac.bytes_per_firq = 1;
/*TODO*///				break;
/*TODO*///	
/*TODO*///			/* Unknown case */
/*TODO*///			default:
/*TODO*///				break;
/*TODO*///		}
/*TODO*///	
/*TODO*///		/* find the hotspot for optimization */
/*TODO*///		locate_audio_hotspot(&RAM[0x0000], 0xc000);
/*TODO*///	
/*TODO*///		/* initialize the global variables */
/*TODO*///		init_audio_state(1);
/*TODO*///	}
/*TODO*///	
/*TODO*///	static void williams_dcs_boot( void )
/*TODO*///	{
/*TODO*///		UINT32	bank_offset = ( dcs.bank & 0x7ff ) << 12;
/*TODO*///		UINT32	*src = ( UINT32 * )( ( memory_region( REGION_CPU1+williams_cpunum ) + ADSP2100_SIZE + bank_offset ) );
/*TODO*///		UINT32	*dst = ( UINT32 * )( ( memory_region( REGION_CPU1+williams_cpunum ) + ADSP2100_PGM_OFFSET ) );
/*TODO*///		UINT32	size;
/*TODO*///		UINT32	i, data;
/*TODO*///	
/*TODO*///		/* see how many words we need to copy */
/*TODO*///		data = src[0];
/*TODO*///	#ifdef LSB_FIRST // ************** not really tested yet ****************
/*TODO*///		data = ( ( data & 0xff ) << 24 ) | ( ( data & 0xff00 ) << 8 ) | ( ( data >> 8 ) & 0xff00 ) | ( ( data >> 24 ) & 0xff );
/*TODO*///	#endif
/*TODO*///		size = ( ( data & 0xff ) + 1 ) * 8;
/*TODO*///	
/*TODO*///		for( i = 0; i < size; i++ )
/*TODO*///		{
/*TODO*///			data = src[i];
/*TODO*///	#ifdef LSB_FIRST // ************** not really tested yet ****************
/*TODO*///			data = ( ( data & 0xff ) << 24 ) | ( ( data & 0xff00 ) << 8 ) | ( ( data >> 8 ) & 0xff00 ) | ( ( data >> 24 ) & 0xff );
/*TODO*///	#endif
/*TODO*///			data >>= 8;
/*TODO*///			dst[i] = data;
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	void williams_dcs_init(int cpunum)
/*TODO*///	{
/*TODO*///		int i;
/*TODO*///	
/*TODO*///		/* configure the CPU */
/*TODO*///		williams_cpunum = cpunum;
/*TODO*///		williams_audio_type = WILLIAMS_DCS;
/*TODO*///	
/*TODO*///		/* initialize our state structure and install the transmit callback */
/*TODO*///		dcs.mem = 0;
/*TODO*///		dcs.size = 0;
/*TODO*///		dcs.incs = 0;
/*TODO*///		dcs.ireg = 0;
/*TODO*///	
/*TODO*///		/* initialize the ADSP control regs */
/*TODO*///		for( i = 0; i < sizeof(dcs.control_regs) / sizeof(dcs.control_regs[0]); i++ )
/*TODO*///			dcs.control_regs[i] = 0;
/*TODO*///	
/*TODO*///		/* initialize banking */
/*TODO*///		dcs.bank = 0;
/*TODO*///	
/*TODO*///		/* start with no sound output */
/*TODO*///		dcs.enabled = 0;
/*TODO*///	
/*TODO*///		/* reset DAC generation */
/*TODO*///		dcs.buffer_in = 0;
/*TODO*///		dcs.sample_step = 0x10000;
/*TODO*///		dcs.sample_position = 0;
/*TODO*///		dcs.current_sample = 0;
/*TODO*///	
/*TODO*///		/* initialize the ADSP Tx callback */
/*TODO*///		adsp2105_set_tx_callback( sound_tx_callback );
/*TODO*///	
/*TODO*///		/* clear all interrupts */
/*TODO*///		cpu_set_irq_line( williams_cpunum, ADSP2105_IRQ0, CLEAR_LINE );
/*TODO*///		cpu_set_irq_line( williams_cpunum, ADSP2105_IRQ1, CLEAR_LINE );
/*TODO*///		cpu_set_irq_line( williams_cpunum, ADSP2105_IRQ2, CLEAR_LINE );
/*TODO*///	
/*TODO*///		/* install the speedup handler */
/*TODO*///	#if (!DISABLE_DCS_SPEEDUP)
/*TODO*///		dcs_speedup1 = install_mem_write_handler(williams_cpunum, ADSP_DATA_ADDR_RANGE(0x04f8, 0x04f8), dcs_speedup1_w);
/*TODO*///		dcs_speedup2 = install_mem_write_handler(williams_cpunum, ADSP_DATA_ADDR_RANGE(0x063d, 0x063d), dcs_speedup2_w);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///		/* initialize the comm bits */
/*TODO*///		dcs.latch_control = 0x0c00;
/*TODO*///	
/*TODO*///		/* boot */
/*TODO*///		williams_dcs_boot();
/*TODO*///	}
	
	static void init_audio_state(int first_time)
	{
		/* reset the YM2151 state */
		_ym2151.timer_base = 1.0 / (3579580.0 / 64.0);
		_ym2151.timer_period[0] = _ym2151.timer_period[1] = _ym2151.timer_period[2] = 1.0;
		_ym2151.timer_value[0] = _ym2151.timer_value[1] = 0;
		_ym2151.timer_is_active[0] = _ym2151.timer_is_active[1] = 0;
		_ym2151.current_register = 0x13;
		_ym2151.active_timer = 2;
		YM2151_sh_reset();
	
		/* reset the counter state */
		counter.adjusted_divisor = 256;
		counter.last_hotspot_counter = 0xffff;
		counter.hotspot_hit_count = 0;
		counter.time_leftover = 0;
		counter.last_read_pc = 0x0000;
		if (first_time != 0)
			counter.update_timer = timer_set(TIME_NEVER, 0, null);
		counter.invalid = 1;
		if (first_time==0 && counter.enable_timer!=null)
			timer_remove(counter.enable_timer);
		counter.enable_timer = timer_set(TIME_IN_SEC(3), 0, counter_enable);
	
		/* reset the CVSD generator */
		cvsd.sample_step = 0;
		cvsd.sample_position = 0x10000;
		cvsd.current_sample = 0;
		cvsd.invalid = 1;
		cvsd.charge = pow(exp(-1), 1.0 / (FILTER_CHARGE_TC * 16000.0));
		cvsd.decay = pow(exp(-1), 1.0 / (FILTER_DECAY_TC * 16000.0));
		cvsd.leak = pow(exp(-1), 1.0 / (INTEGRATOR_LEAK_TC * 16000.0));
		cvsd.integrator = 0;
		cvsd.filter = FILTER_MIN;
		cvsd.shiftreg = 0xaa;
	
		/* reset the DAC generator */
		_dac.sample_step = 0;
		_dac.sample_position = 0x10000;
		_dac.current_sample = 0;
		_dac.invalid = 1;
	
		/* clear all the interrupts */
		williams_sound_int_state = 0;
		cpu_set_irq_line(williams_cpunum, M6809_FIRQ_LINE, CLEAR_LINE);
		cpu_set_irq_line(williams_cpunum, M6809_IRQ_LINE, CLEAR_LINE);
		cpu_set_nmi_line(williams_cpunum, CLEAR_LINE);
		if (williams_audio_type == WILLIAMS_NARC)
		{
			cpu_set_irq_line(williams_cpunum + 1, M6809_FIRQ_LINE, CLEAR_LINE);
			cpu_set_irq_line(williams_cpunum + 1, M6809_IRQ_LINE, CLEAR_LINE);
			cpu_set_nmi_line(williams_cpunum + 1, CLEAR_LINE);
		}
	}
	
	static void locate_audio_hotspot(UBytePtr base, int start)
	{
		int i;
	
		/* search for the loop that kills performance so we can optimize it */
		for (i = start; i < 0x10000; i++)
		{
			if (base.read(i + 0) == 0x1a && base.read(i + 1) == 0x50 &&			/* 1A 50       ORCC  #$0050  */
				base.read(i + 2) == 0x93 &&									/* 93 xx       SUBD  $xx     */
				base.read(i + 4) == 0xe3 && base.read(i + 5) == 0x4c &&			/* E3 4C       ADDD  $000C,U */
				base.read(i + 6) == 0x9e && base.read(i + 7) == base.read(i + 3) &&	/* 9E xx       LDX   $xx     */
				base.read(i + 8) == 0xaf && base.read(i + 9) == 0x4c &&			/* AF 4C       STX   $000C,U */
				base.read(i +10) == 0x1c && base.read(i +11) == 0xaf)				/* 1C AF       ANDCC #$00AF  */
			{
				counter.hotspot_start = i;
				counter.hotspot_stop = i + 12;
				logerror("Found hotspot @ %04X", i);
				return;
			}
		}
		logerror("Found no hotspot!");
	}
	
	
	
        
/*TODO*///	static int dcs_custom_start(const struct MachineSound *msound)
/*TODO*///	{
/*TODO*///		/* allocate a DAC stream */
/*TODO*///		dac_stream = stream_init("DCS DAC", 100, Machine.sample_rate, 0, dcs_dac_update);
/*TODO*///	
/*TODO*///		/* allocate memory for our buffer */
/*TODO*///		dcs.buffer = malloc(DCS_BUFFER_SIZE * sizeof(INT16));
/*TODO*///		if (!dcs.buffer)
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	static void dcs_custom_stop(void)
/*TODO*///	{
/*TODO*///		if (dcs.buffer)
/*TODO*///			free(dcs.buffer);
/*TODO*///		dcs.buffer = NULL;
/*TODO*///	}
	
	
	/***************************************************************************
		CVSD IRQ GENERATION CALLBACKS
	****************************************************************************/
	
	static IrqfuncPtr williams_cvsd_irqa = new IrqfuncPtr() {
            @Override
            public void handler(int state) {
                cpu_set_irq_line(williams_cpunum, M6809_FIRQ_LINE, state!=0 ? ASSERT_LINE : CLEAR_LINE);
            }
        };
        
	static IrqfuncPtr williams_cvsd_irqb = new IrqfuncPtr() {
            @Override
            public void handler(int state) {
		cpu_set_nmi_line(williams_cpunum, state!=0 ? ASSERT_LINE : CLEAR_LINE);
            }
        };
	
/*TODO*///	/***************************************************************************
/*TODO*///		ADPCM IRQ GENERATION CALLBACKS
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	static void williams_adpcm_ym2151_irq(int state)
/*TODO*///	{
/*TODO*///		cpu_set_irq_line(williams_cpunum, M6809_FIRQ_LINE, state ? ASSERT_LINE : CLEAR_LINE);
/*TODO*///	}
	
	
	
	
	
/*TODO*///	/***************************************************************************
/*TODO*///		ADPCM BANK SELECT
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_adpcm_bank_select_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		cpu_setbank(6, get_adpcm_bank_base(data));
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_adpcm_6295_bank_select_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		if (adpcm_bank_count <= 3)
/*TODO*///		{
/*TODO*///			if (!(data & 0x04))
/*TODO*///				OKIM6295_set_bank_base(0, ALL_VOICES, 0x00000);
/*TODO*///			else if ((data & 0x01) != 0)
/*TODO*///				OKIM6295_set_bank_base(0, ALL_VOICES, 0x40000);
/*TODO*///			else
/*TODO*///				OKIM6295_set_bank_base(0, ALL_VOICES, 0x80000);
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			data &= 7;
/*TODO*///			if (data != 0)
/*TODO*///				OKIM6295_set_bank_base(0, ALL_VOICES, (data - 1) * 0x40000);
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
	
	/***************************************************************************
		NARC BANK SELECT
	****************************************************************************/
	
	public static WriteHandlerPtr williams_narc_master_bank_select_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		cpu_setbank(6, get_narc_master_bank_base(data));
	} };
	
/*TODO*///	public static WriteHandlerPtr williams_narc_slave_bank_select_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		cpu_setbank(5, get_narc_slave_bank_base(data));
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///		DCS BANK SELECT
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_dcs_bank_select_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		dcs.bank = data & 0x7ff;
/*TODO*///	
/*TODO*///		/* bit 11 = sound board led */
/*TODO*///	#if 0
/*TODO*///		set_led_status(2,data & 0x800);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///	#if (!DISABLE_DCS_SPEEDUP)
/*TODO*///		/* they write 0x800 here just before entering the stall loop */
/*TODO*///		if (data == 0x800)
/*TODO*///		{
/*TODO*///			/* calculate the next buffer address */
/*TODO*///			int source = cpu_get_reg( ADSP2100_I0+dcs.ireg );
/*TODO*///			int ar = source + dcs.size / 2;
/*TODO*///	
/*TODO*///			/* check for wrapping */
/*TODO*///			if ( ar >= ( dcs.ireg_base + dcs.size ) )
/*TODO*///				ar = dcs.ireg_base;
/*TODO*///	
/*TODO*///			/* set it */
/*TODO*///			cpu_set_reg( ADSP2100_AR, ar );
/*TODO*///	
/*TODO*///			/* go around the buffer syncing code, we sync manually */
/*TODO*///			cpu_set_reg( ADSP2100_PC, cpu_get_pc() + 8 );
/*TODO*///			cpu_spinuntil_int();
/*TODO*///		}
/*TODO*///	#endif
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr williams_dcs_bank_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		UINT8	*banks = memory_region( REGION_CPU1+williams_cpunum ) + ADSP2100_SIZE;
/*TODO*///	
/*TODO*///		offset >>= 1;
/*TODO*///		offset += ( dcs.bank & 0x7ff ) << 12;
/*TODO*///		return banks[offset];
/*TODO*///	} };
/*TODO*///	
	
	/***************************************************************************
		CVSD COMMUNICATIONS
	****************************************************************************/
	
	static timer_callback williams_cvsd_delayed_data_w = new timer_callback() {
            @Override
            public void handler(int param) {
                pia_set_input_b(williams_pianum, param & 0xff);
		pia_set_input_cb1(williams_pianum, param & 0x100);
		pia_set_input_cb2(williams_pianum, param & 0x200);
            }
        };
        
	public static WriteHandlerPtr williams_cvsd_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		timer_set(TIME_NOW, data, williams_cvsd_delayed_data_w);
	} };
	
	public static void williams_cvsd_reset_w(int state)
	{
		/* going high halts the CPU */
		if (state != 0)
		{
			williams_cvsd_bank_select_w.handler(0, 0);
			init_audio_state(0);
			cpu_set_reset_line(williams_cpunum, ASSERT_LINE);
		}
		/* going low resets and reactivates the CPU */
		else
			cpu_set_reset_line(williams_cpunum, CLEAR_LINE);
	}
	
	
/*TODO*///	/***************************************************************************
/*TODO*///		ADPCM COMMUNICATIONS
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr williams_adpcm_command_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		cpu_set_irq_line(williams_cpunum, M6809_IRQ_LINE, CLEAR_LINE);
/*TODO*///		williams_sound_int_state = 0;
/*TODO*///		return soundlatch_r(0);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_adpcm_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		soundlatch_w.handler(0, data & 0xff);
/*TODO*///		if (!(data & 0x200))
/*TODO*///		{
/*TODO*///			cpu_set_irq_line(williams_cpunum, M6809_IRQ_LINE, ASSERT_LINE);
/*TODO*///			williams_sound_int_state = 1;
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	void williams_adpcm_reset_w(int state)
/*TODO*///	{
/*TODO*///		/* going high halts the CPU */
/*TODO*///		if (state != 0)
/*TODO*///		{
/*TODO*///			williams_adpcm_bank_select_w(0, 0);
/*TODO*///			init_audio_state(0);
/*TODO*///			cpu_set_reset_line(williams_cpunum, ASSERT_LINE);
/*TODO*///		}
/*TODO*///		/* going low resets and reactivates the CPU */
/*TODO*///		else
/*TODO*///			cpu_set_reset_line(williams_cpunum, CLEAR_LINE);
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///		NARC COMMUNICATIONS
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr williams_narc_command_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		cpu_set_nmi_line(williams_cpunum, CLEAR_LINE);
/*TODO*///		cpu_set_irq_line(williams_cpunum, M6809_IRQ_LINE, CLEAR_LINE);
/*TODO*///		williams_sound_int_state = 0;
/*TODO*///		return soundlatch_r(0);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_narc_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		soundlatch_w.handler(0, data & 0xff);
/*TODO*///		if (!(data & 0x100))
/*TODO*///			cpu_set_nmi_line(williams_cpunum, ASSERT_LINE);
/*TODO*///		if (!(data & 0x200))
/*TODO*///		{
/*TODO*///			cpu_set_irq_line(williams_cpunum, M6809_IRQ_LINE, ASSERT_LINE);
/*TODO*///			williams_sound_int_state = 1;
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	void williams_narc_reset_w(int state)
/*TODO*///	{
/*TODO*///		/* going high halts the CPU */
/*TODO*///		if (state != 0)
/*TODO*///		{
/*TODO*///			williams_narc_master_bank_select_w(0, 0);
/*TODO*///			williams_narc_slave_bank_select_w(0, 0);
/*TODO*///			init_audio_state(0);
/*TODO*///			cpu_set_reset_line(williams_cpunum, ASSERT_LINE);
/*TODO*///			cpu_set_reset_line(williams_cpunum + 1, ASSERT_LINE);
/*TODO*///		}
/*TODO*///		/* going low resets and reactivates the CPU */
/*TODO*///		else
/*TODO*///		{
/*TODO*///			cpu_set_reset_line(williams_cpunum, CLEAR_LINE);
/*TODO*///			cpu_set_reset_line(williams_cpunum + 1, CLEAR_LINE);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr williams_narc_command2_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		cpu_set_irq_line(williams_cpunum + 1, M6809_FIRQ_LINE, CLEAR_LINE);
/*TODO*///		return soundlatch2_r(0);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_narc_command2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		soundlatch2_w.handler(0, data & 0xff);
/*TODO*///		cpu_set_irq_line(williams_cpunum + 1, M6809_FIRQ_LINE, ASSERT_LINE);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///		DCS COMMUNICATIONS
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr williams_dcs_data_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		/* data is actually only 8 bit (read from d8-d15) */
/*TODO*///		dcs.latch_control |= 0x0400;
/*TODO*///	
/*TODO*///		return soundlatch2_r( 0 ) & 0xff;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_dcs_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		/* data is actually only 8 bit (read from d8-d15) */
/*TODO*///		dcs.latch_control &= ~0x800;
/*TODO*///		soundlatch_w.handler( 0, data );
/*TODO*///		cpu_set_irq_line( williams_cpunum, ADSP2105_IRQ2, ASSERT_LINE );
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr williams_dcs_control_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		/* this is read by the TMS before issuing a command to check */
/*TODO*///		/* if the ADSP has read the last one yet. We give 50 usec to */
/*TODO*///		/* the ADSP to read the latch and thus preventing any sound  */
/*TODO*///		/* loss */
/*TODO*///		if ( ( dcs.latch_control & 0x800 ) == 0 )
/*TODO*///			cpu_spinuntil_time( TIME_IN_USEC(50) );
/*TODO*///	
/*TODO*///		return dcs.latch_control;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	void williams_dcs_reset_w(int state)
/*TODO*///	{
/*TODO*///		logerror( "%08x: DCS reset\n", cpu_get_pc() );
/*TODO*///	
/*TODO*///		/* going high halts the CPU */
/*TODO*///		if (state != 0)
/*TODO*///		{
/*TODO*///			/* just run through the init code again */
/*TODO*///			williams_dcs_init( williams_cpunum );
/*TODO*///			cpu_set_reset_line(williams_cpunum, ASSERT_LINE);
/*TODO*///		}
/*TODO*///		/* going low resets and reactivates the CPU */
/*TODO*///		else
/*TODO*///		{
/*TODO*///			cpu_set_reset_line(williams_cpunum, CLEAR_LINE);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr williams_dcs_latch_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		dcs.latch_control |= 0x800;
/*TODO*///		/* clear the irq line */
/*TODO*///		cpu_set_irq_line( williams_cpunum, ADSP2105_IRQ2, CLEAR_LINE );
/*TODO*///		return soundlatch_r( 0 );
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_dcs_latch_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		dcs.latch_control &= ~0x400;
/*TODO*///		soundlatch2_w.handler( 0, data );
/*TODO*///	} };
	
	
	
	
	
	/***************************************************************************
		DAC INTERFACES
	****************************************************************************/
	
	public static WriteHandlerPtr williams_dac_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		DAC_data_w.handler(0, data);
	} };
	
/*TODO*///	public static WriteHandlerPtr williams_dac2_data_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		DAC_data_w(1, data);
/*TODO*///	} };
	
	
	/***************************************************************************
		SPEEDUP KLUDGES
	****************************************************************************/
	
	static timer_callback counter_enable = new timer_callback() {
            @Override
            public void handler(int param) {
                counter.invalid = 0;
		counter.enable_timer = null;
		timer_reset(counter.update_timer, TIME_NEVER);
	
		/* the counter routines all reset the bank, but NARC is the only one that cares */
		if (williams_audio_type == WILLIAMS_NARC)
			williams_narc_master_bank_select_w.handler(0, 5);
            }
        };
        
	static void update_counter()
	{
		double time_since_update, timer_period = _ym2151.timer_period[_ym2151.active_timer];
		int firqs_since_update, complete_ticks, downcounter;
	
		if (DISABLE_FIRQ_SPEEDUP!=0 || _ym2151.active_timer == 2 || counter.invalid!=0)
			return;
	
		/* compute the time since we last updated; if it's less than one period, do nothing */
		time_since_update = timer_timeelapsed(counter.update_timer) + counter.time_leftover;
		if (time_since_update < timer_period)
			return;
	
		/* compute the integral number of FIRQ interrupts that have occured since the last update */
		firqs_since_update = (int)(time_since_update / timer_period);
	
		/* keep track of any additional time */
		counter.time_leftover = time_since_update - (double)firqs_since_update * timer_period;
	
		/* reset the timer */
		timer_reset(counter.update_timer, TIME_NEVER);
	
		/* determine the number of complete ticks that have occurred */
		complete_ticks = firqs_since_update / counter.adjusted_divisor;
	
		/* subtract any remainder from the down counter, and carry if appropriate */
		downcounter = counter.downcount.read(0) - (firqs_since_update - complete_ticks * counter.adjusted_divisor);
		if (downcounter < 0)
		{
			downcounter += counter.adjusted_divisor;
			complete_ticks++;
		}
	
		/* add in the previous value of the counter */
		complete_ticks += counter.value.read(0) * 256 + counter.value.read(1);
	
		/* store the results */
		counter.value.write(0, complete_ticks >> 8);
		counter.value.write(1, complete_ticks);
		counter.downcount.write(0, downcounter);
	}
	
	public static WriteHandlerPtr counter_divisor_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		update_counter();
		counter.divisor.write(offset, data);
		counter.adjusted_divisor = data!=0 ? data : 256;
	} };
	
	public static ReadHandlerPtr counter_down_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		update_counter();
		return counter.downcount.read(offset);
	} };
	
	public static WriteHandlerPtr counter_down_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		update_counter();
		counter.downcount.write(offset, data);
	} };
	
	public static ReadHandlerPtr counter_value_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		int pc = cpu_getpreviouspc();
	
		/* only update the counter on the MSB */
		/* also, don't update it if we just read from it a few instructions back */
		if (offset == 0 && (pc <= counter.last_read_pc || pc > counter.last_read_pc + 16))
			update_counter();
		counter.last_read_pc = pc;
	
		/* on the second LSB read in the hotspot, check to see if we will be looping */
		if (offset == 1 && pc == counter.hotspot_start + 6 && DISABLE_LOOP_CATCHERS==0)
		{
			int new_counter = counter.value.read(0) * 256 + counter.value.read(1);
	
			/* count how many hits in a row we got the same value */
			if (new_counter == counter.last_hotspot_counter)
				counter.hotspot_hit_count++;
			else
				counter.hotspot_hit_count = 0;
			counter.last_hotspot_counter = new_counter;
	
			/* more than 3 hits in a row and we optimize */
			if (counter.hotspot_hit_count > 3)
				cpuintf[Machine.drv.cpu[williams_cpunum].cpu_type & ~CPU_FLAGS_MASK].icount[0] -= 50;
		}
	
		return counter.value.read(offset);
	} };
	
	public static WriteHandlerPtr counter_value_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		/* only update the counter after the LSB is written */
		if (offset == 1)
			update_counter();
		counter.value.write(offset, data);
		counter.hotspot_hit_count = 0;
	} };
	
	
	/***************************************************************************
		CVSD KLUDGES
	****************************************************************************/
	
	static timer_callback cvsd_start = new timer_callback() {
            @Override
            public void handler(int param) {
                double sample_rate;
		int start, end;
	
		/* if interrupts are disabled, try again later */
		if ((cpunum_get_reg(williams_cpunum, M6809_CC) & 0x40) != 0)
		{
			timer_set(TIME_IN_MSEC(1), 0, cvsd_start);
			return;
		}
	
		/* determine the start and end addresses */
		start = get_cvsd_address();
		end = cvsd.end.read(0) * 256 + cvsd.end.read(1);
	
		/* compute the effective sample rate */
		sample_rate = (double)cvsd.bits_per_firq / _ym2151.timer_period[_ym2151.active_timer];
		cvsd.sample_step = (int)(sample_rate * 65536.0 / (double)Machine.sample_rate);
		cvsd.invalid = 0;
            }
        };
        
	public static WriteHandlerPtr cvsd_state_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		/* if we write a value here with a non-zero high bit, prepare to start playing */
		stream_update(cvsd_stream, 0);
		if (offset == 0 && (data & 0x80)==0 && _ym2151.active_timer != 2)
		{
			cvsd.invalid = 1;
			timer_set(TIME_IN_MSEC(1), 0, cvsd_start);
		}
	
		cvsd.state.write(offset, data);
	} };
	
	
	/***************************************************************************
		DAC KLUDGES
	****************************************************************************/
	
	static timer_callback dac_start = new timer_callback() {
            @Override
            public void handler(int param) {
                double sample_rate;
		int start, end;
	
		/* if interrupts are disabled, try again later */
		if ((cpunum_get_reg(williams_cpunum, M6809_CC) & 0x40) != 0)
		{
			timer_set(TIME_IN_MSEC(1), 0, dac_start);
			return;
		}
	
		/* determine the start and end addresses */
		start = get_dac_address();
		end = _dac.end.read(0) * 256 + _dac.end.read(1);
	
		/* compute the effective sample rate */
		sample_rate = (double)_dac.bytes_per_firq / _ym2151.timer_period[_ym2151.active_timer];
		_dac.sample_step = (int)(sample_rate * 65536.0 / (double)Machine.sample_rate);
		_dac.invalid = 0;
            }
        };
        
	public static WriteHandlerPtr dac_state_bank_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		/* if we write a value here with a non-zero high bit, prepare to start playing */
		stream_update(dac_stream, 0);
		if ((data & 0x80)==0 && _ym2151.active_timer != 2)
		{
			_dac.invalid = 1;
			timer_set(TIME_IN_MSEC(1), 0, dac_start);
		}
	
		_dac.state_bank.write(offset, data);
	} };
	
	
	/***************************************************************************
		SOUND GENERATION
	****************************************************************************/
	
	static StreamInitPtr cvsd_update = new StreamInitPtr() {
            @Override
            public void handler(int num, ShortPtr buffer, int length) {
		UBytePtr bank_base=new UBytePtr(), source=new UBytePtr(), end=new UBytePtr();
		int current;
		int i;
	
		/* CVSD generation */
		if (cvsd.state!=null && (cvsd.state.read(0) & 0x80)==0)
		{
			int bits_left = cvsd.state.read(0);
			int current_byte = cvsd.state.read(1);
	
			/* determine start and end points */
			bank_base = new UBytePtr(get_cvsd_bank_base(cvsd.bank.read(0)), - 0x8000);
			source = new UBytePtr(bank_base, get_cvsd_address());
			end = new UBytePtr(bank_base, cvsd.end.read(0) * 256 + cvsd.end.read(1));
	
			current = cvsd.sample_position;
                        
                        boolean _finished = false;
	
			/* fill in with samples until we hit the end or run out */
			for (i = 0; i < length; i++)
			{
				/* if we overflow to the next sample, process it */
				while ((current > 0xffff) && !_finished)
				{
					if ((bits_left-- == 0) && !_finished)
					{
						if ((source.offset >= end.offset) && !_finished)
						{
							bits_left |= 0x80;
							cvsd.sample_position = 0x10000;
							cvsd.integrator = 0;
							cvsd.filter = FILTER_MIN;
							cvsd.shiftreg = 0xaa;
							memset(buffer, 0, (length - i));
							//goto finished;
                                                        _finished=true;
						}
						current_byte = source.readinc();
						bits_left = 7;
					}
					
                                        if (!_finished) {
                                            cvsd.current_sample = get_next_cvsd_sample(current_byte & (0x80 >> bits_left));
                                            current -= 0x10000;
                                        }
				}
	
                                if (!_finished) {
                                    buffer.writeinc((short) cvsd.current_sample);
                                    current += cvsd.sample_step;
                                }
			}
	
			/* update the final values */
		//finished:
			set_cvsd_address(source.offset - bank_base.offset);
			cvsd.sample_position = current;
			cvsd.state.write(0, bits_left);
			cvsd.state.write(1, current_byte);
		}
		else
			memset(buffer, 0, length);
            }
        };
	
	static StreamInitPtr dac_update = new StreamInitPtr() {
            @Override
            public void handler(int num, ShortPtr buffer, int length) {
                UBytePtr bank_base=new UBytePtr(), source=new UBytePtr(), end=new UBytePtr();
		int current;
		int i;
	
		/* DAC generation */
		if (_dac.state_bank!=null && (_dac.state_bank.read(0) & 0x80)==0)
		{
			int volume = _dac.volume.read(0) / 2;
	
			/* determine start and end points */
			if (williams_audio_type == WILLIAMS_CVSD)
				bank_base = new UBytePtr(get_cvsd_bank_base(_dac.state_bank.read(0)), - 0x8000);
			else if (williams_audio_type == WILLIAMS_ADPCM)
				bank_base = new UBytePtr(get_adpcm_bank_base(_dac.state_bank.read(0)), - 0x4000);
			else
				bank_base = new UBytePtr(get_narc_master_bank_base(_dac.state_bank.read(0)), - 0x4000);
			source = new UBytePtr(bank_base, get_dac_address());
			end = new UBytePtr(bank_base, _dac.end.read(0) * 256 + _dac.end.read(1));
	
			current = _dac.sample_position;
                        
                        boolean _finished = false;
	
			/* fill in with samples until we hit the end or run out */
			for (i = 0; i < length; i++)
			{
				/* if we overflow to the next sample, process it */
				while ((current > 0xffff) && !_finished)
				{
					if (source.offset >= end.offset)
					{
						_dac.state_bank.write(0, _dac.state_bank.read(0) | 0x80);
						_dac.sample_position = 0x10000;
						memset(buffer, 0, (length - i));
						//goto finished;
                                                _finished = true;
                                                //continue;
					}
					if (!_finished) {
                                            _dac.current_sample = source.readinc() * volume;
                                            current -= 0x10000;
                                        }
				}
                                
                                if (!_finished) {
                                    buffer.writeinc((short) _dac.current_sample);
                                    current += _dac.sample_step;
                                }
			}
	
			/* update the final values */
		//finished:
			set_dac_address(source.offset - bank_base.offset);
			_dac.sample_position = current;
		}
		else
			memset(buffer, 0, length);
            }
        };
        
	static StreamInitPtr dcs_dac_update = new StreamInitPtr() {
            @Override
            public void handler(int num, ShortPtr buffer, int length) {
                int current, step, indx;
		ShortPtr source;
		int i;
	
		/* DAC generation */
		if (_dcs.enabled != 0)
		{
			source = _dcs.buffer;
			current = _dcs.sample_position;
			step = _dcs.sample_step;
	
			/* fill in with samples until we hit the end or run out */
			for (i = 0; i < length; i++)
			{
				indx = current >> 16;
				if (indx >= _dcs.buffer_in)
					break;
				current += step;
				buffer.writeinc( source.read(indx & DCS_BUFFER_MASK) );
			}
	
			/* fill the rest with the last sample */
			for ( ; i < length; i++)
				buffer.writeinc( source.read((_dcs.buffer_in - 1) & DCS_BUFFER_MASK) );
	
			/* mask off extra bits */
			while (current >= (DCS_BUFFER_SIZE << 16))
			{
				current -= DCS_BUFFER_SIZE << 16;
				_dcs.buffer_in -= DCS_BUFFER_SIZE;
			}
	
			/* update the final values */
			_dcs.sample_position = current;
		}
		else
			memset(buffer, 0, length);
            }
        };
        
	
	/***************************************************************************
		CVSD DECODING (cribbed from HC55516.c)
	****************************************************************************/
	
	static int get_next_cvsd_sample(int bit)
	{
		double temp;
	
		/* move the estimator up or down a step based on the bit */
		if (bit != 0)
		{
			cvsd.shiftreg = ((cvsd.shiftreg << 1) | 1) & 7;
			cvsd.integrator += cvsd.filter;
		}
		else
		{
			cvsd.shiftreg = (cvsd.shiftreg << 1) & 7;
			cvsd.integrator -= cvsd.filter;
		}
	
		/* simulate leakage */
		cvsd.integrator *= cvsd.leak;
	
		/* if we got all 0's or all 1's in the last n bits, bump the step up */
		if (cvsd.shiftreg == 0 || cvsd.shiftreg == 7)
		{
			cvsd.filter = FILTER_MAX - ((FILTER_MAX - cvsd.filter) * cvsd.charge);
			if (cvsd.filter > FILTER_MAX)
				cvsd.filter = FILTER_MAX;
		}
	
		/* simulate decay */
		else
		{
			cvsd.filter *= cvsd.decay;
			if (cvsd.filter < FILTER_MIN)
				cvsd.filter = FILTER_MIN;
		}
	
		/* compute the sample as a 32-bit word */
		temp = cvsd.integrator * SAMPLE_GAIN;
	
		/* compress the sample range to fit better in a 16-bit word */
		if (temp < 0)
			return (int)(temp / (-temp * (1.0 / 32768.0) + 1.0));
		else
			return (int)(temp / (temp * (1.0 / 32768.0) + 1.0));
	}
	
	
/*TODO*///	/***************************************************************************
/*TODO*///		ADSP CONTROL & TRANSMIT CALLBACK
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	/*
/*TODO*///		The ADSP2105 memory map when in boot rom mode is as follows:
/*TODO*///	
/*TODO*///		Program Memory:
/*TODO*///		0x0000-0x03ff = Internal Program Ram (contents of boot rom gets copied here)
/*TODO*///		0x0400-0x07ff = Reserved
/*TODO*///		0x0800-0x3fff = External Program Ram
/*TODO*///	
/*TODO*///		Data Memory:
/*TODO*///		0x0000-0x03ff = External Data - 0 Waitstates
/*TODO*///		0x0400-0x07ff = External Data - 1 Waitstates
/*TODO*///		0x0800-0x2fff = External Data - 2 Waitstates
/*TODO*///		0x3000-0x33ff = External Data - 3 Waitstates
/*TODO*///		0x3400-0x37ff = External Data - 4 Waitstates
/*TODO*///		0x3800-0x39ff = Internal Data Ram
/*TODO*///		0x3a00-0x3bff = Reserved (extra internal ram space on ADSP2101, etc)
/*TODO*///		0x3c00-0x3fff = Memory Mapped control registers & reserved.
/*TODO*///	*/
/*TODO*///	
/*TODO*///	/* These are the some of the control register, we dont use them all */
/*TODO*///	enum {
/*TODO*///		S1_AUTOBUF_REG = 15,
/*TODO*///		S1_RFSDIV_REG,
/*TODO*///		S1_SCLKDIV_REG,
/*TODO*///		S1_CONTROL_REG,
/*TODO*///		S0_AUTOBUF_REG,
/*TODO*///		S0_RFSDIV_REG,
/*TODO*///		S0_SCLKDIV_REG,
/*TODO*///		S0_CONTROL_REG,
/*TODO*///		S0_MCTXLO_REG,
/*TODO*///		S0_MCTXHI_REG,
/*TODO*///		S0_MCRXLO_REG,
/*TODO*///		S0_MCRXHI_REG,
/*TODO*///		TIMER_SCALE_REG,
/*TODO*///		TIMER_COUNT_REG,
/*TODO*///		TIMER_PERIOD_REG,
/*TODO*///		WAITSTATES_REG,
/*TODO*///		SYSCONTROL_REG
/*TODO*///	};
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr williams_dcs_control_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		dcs.control_regs[offset>>1] = data;
/*TODO*///	
/*TODO*///		switch( offset >> 1 ) {
/*TODO*///			case SYSCONTROL_REG:
/*TODO*///				if ((data & 0x0200) != 0) {
/*TODO*///					/* boot force */
/*TODO*///					cpu_set_reset_line( williams_cpunum, PULSE_LINE );
/*TODO*///					williams_dcs_boot();
/*TODO*///					dcs.control_regs[SYSCONTROL_REG] &= ~0x0200;
/*TODO*///				}
/*TODO*///	
/*TODO*///				/* see if SPORT1 got disabled */
/*TODO*///				stream_update(dac_stream, 0);
/*TODO*///				if ( ( data & 0x0800 ) == 0 ) {
/*TODO*///					dcs.enabled = 0;
/*TODO*///	
/*TODO*///					/* nuke the timer */
/*TODO*///					if ( dcs.reg_timer ) {
/*TODO*///						timer_remove( dcs.reg_timer );
/*TODO*///						dcs.reg_timer = 0;
/*TODO*///					}
/*TODO*///				}
/*TODO*///			break;
/*TODO*///	
/*TODO*///			case S1_AUTOBUF_REG:
/*TODO*///				/* autobuffer off: nuke the timer, and disable the DAC */
/*TODO*///				stream_update(dac_stream, 0);
/*TODO*///				if ( ( data & 0x0002 ) == 0 ) {
/*TODO*///					dcs.enabled = 0;
/*TODO*///	
/*TODO*///					if ( dcs.reg_timer ) {
/*TODO*///						timer_remove( dcs.reg_timer );
/*TODO*///						dcs.reg_timer = 0;
/*TODO*///					}
/*TODO*///				}
/*TODO*///			break;
/*TODO*///	
/*TODO*///			case S1_CONTROL_REG:
/*TODO*///				if ( ( ( data >> 4 ) & 3 ) == 2 )
/*TODO*///					logerror( "Oh no!, the data is compresed with u-law encoding\n" );
/*TODO*///				if ( ( ( data >> 4 ) & 3 ) == 3 )
/*TODO*///					logerror( "Oh no!, the data is compresed with A-law encoding\n" );
/*TODO*///			break;
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///		DCS IRQ GENERATION CALLBACKS
/*TODO*///	****************************************************************************/
/*TODO*///	
/*TODO*///	static void williams_dcs_irq(int state)
/*TODO*///	{
/*TODO*///		/* get the index register */
/*TODO*///		int reg = cpunum_get_reg( williams_cpunum, ADSP2100_I0+dcs.ireg );
/*TODO*///	
/*TODO*///		/* translate into data memory bus address */
/*TODO*///		int source = ADSP2100_DATA_OFFSET + ( reg << 1 );
/*TODO*///		int i;
/*TODO*///	
/*TODO*///		/* copy the current data into the buffer */
/*TODO*///		for (i = 0; i < dcs.size / 2; i++)
/*TODO*///			dcs.buffer[dcs.buffer_in++ & DCS_BUFFER_MASK] = READ_WORD(&dcs.mem[source + i * 2]);
/*TODO*///	
/*TODO*///		/* increment it */
/*TODO*///		reg += dcs.incs * dcs.size / 2;
/*TODO*///	
/*TODO*///		/* check for wrapping */
/*TODO*///		if ( reg >= ( dcs.ireg_base + dcs.size ) )
/*TODO*///		{
/*TODO*///			/* reset the base pointer */
/*TODO*///			reg = dcs.ireg_base;
/*TODO*///	
/*TODO*///			/* generate the (internal, thats why the pulse) irq */
/*TODO*///			cpu_set_irq_line( williams_cpunum, ADSP2105_IRQ1, PULSE_LINE );
/*TODO*///		}
/*TODO*///	
/*TODO*///		/* store it */
/*TODO*///		cpunum_set_reg( williams_cpunum, ADSP2100_I0+dcs.ireg, reg );
/*TODO*///	
/*TODO*///	#if (!DISABLE_DCS_SPEEDUP)
/*TODO*///		/* this is the same trigger as an interrupt */
/*TODO*///		cpu_trigger( -2000 + williams_cpunum );
/*TODO*///	#endif
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	static void sound_tx_callback( int port, INT32 data )
/*TODO*///	{
/*TODO*///		/* check if it's for SPORT1 */
/*TODO*///		if ( port != 1 )
/*TODO*///			return;
/*TODO*///	
/*TODO*///		/* check if SPORT1 is enabled */
/*TODO*///		if ( dcs.control_regs[SYSCONTROL_REG] & 0x0800 ) /* bit 11 */
/*TODO*///		{
/*TODO*///			/* we only support autobuffer here (wich is what this thing uses), bail if not enabled */
/*TODO*///			if ( dcs.control_regs[S1_AUTOBUF_REG] & 0x0002 ) /* bit 1 */
/*TODO*///			{
/*TODO*///				/* get the autobuffer registers */
/*TODO*///				int		mreg, lreg;
/*TODO*///				UINT16	source;
/*TODO*///				int		sample_rate;
/*TODO*///	
/*TODO*///				stream_update(dac_stream, 0);
/*TODO*///	
/*TODO*///				dcs.ireg = ( dcs.control_regs[S1_AUTOBUF_REG] >> 9 ) & 7;
/*TODO*///				mreg = ( dcs.control_regs[S1_AUTOBUF_REG] >> 7 ) & 3;
/*TODO*///				mreg |= dcs.ireg & 0x04; /* msb comes from ireg */
/*TODO*///				lreg = dcs.ireg;
/*TODO*///	
/*TODO*///				/* now get the register contents in a more legible format */
/*TODO*///				/* we depend on register indexes to be continuous (wich is the case in our core) */
/*TODO*///				source = cpunum_get_reg( williams_cpunum, ADSP2100_I0+dcs.ireg );
/*TODO*///				dcs.incs = cpunum_get_reg( williams_cpunum, ADSP2100_M0+mreg );
/*TODO*///				dcs.size = cpunum_get_reg( williams_cpunum, ADSP2100_L0+lreg );
/*TODO*///	
/*TODO*///				/* get the base value, since we need to keep it around for wrapping */
/*TODO*///				source--;
/*TODO*///	
/*TODO*///				/* make it go back one so we dont loose the first sample */
/*TODO*///				cpunum_set_reg( williams_cpunum, ADSP2100_I0+dcs.ireg, source );
/*TODO*///	
/*TODO*///				/* save it as it is now */
/*TODO*///				dcs.ireg_base = source;
/*TODO*///	
/*TODO*///				/* get the memory chunk to read the data from */
/*TODO*///				dcs.mem = memory_region( REGION_CPU1+williams_cpunum );
/*TODO*///	
/*TODO*///				/* enable the dac playing */
/*TODO*///				dcs.enabled = 1;
/*TODO*///	
/*TODO*///				/* calculate how long until we generate an interrupt */
/*TODO*///	
/*TODO*///				/* frequency in Hz per each bit sent */
/*TODO*///				sample_rate = Machine.drv.cpu[williams_cpunum].cpu_clock / ( 2 * ( dcs.control_regs[S1_SCLKDIV_REG] + 1 ) );
/*TODO*///	
/*TODO*///				/* now put it down to samples, so we know what the channel frequency has to be */
/*TODO*///				sample_rate /= 16;
/*TODO*///	
/*TODO*///				/* fire off a timer wich will hit every half-buffer */
/*TODO*///				dcs.reg_timer = timer_pulse(TIME_IN_HZ(sample_rate) * (dcs.size / 2), 0, williams_dcs_irq);
/*TODO*///	
/*TODO*///				/* configure the DAC generator */
/*TODO*///				dcs.sample_step = (int)(sample_rate * 65536.0 / (double)Machine.sample_rate);
/*TODO*///				dcs.sample_position = 0;
/*TODO*///				dcs.buffer_in = 0;
/*TODO*///	
/*TODO*///				return;
/*TODO*///			}
/*TODO*///			else
/*TODO*///				logerror( "ADSP SPORT1: trying to transmit and autobuffer not enabled!\n" );
/*TODO*///		}
/*TODO*///	
/*TODO*///		/* if we get there, something went wrong. Disable playing */
/*TODO*///		stream_update(dac_stream, 0);
/*TODO*///		dcs.enabled = 0;
/*TODO*///	
/*TODO*///		/* remove timer */
/*TODO*///		if ( dcs.reg_timer )
/*TODO*///		{
/*TODO*///			timer_remove( dcs.reg_timer );
/*TODO*///			dcs.reg_timer = 0;
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr dcs_speedup1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///	/*
/*TODO*///		UMK3:	trigger = $04F8 = 2, PC = $00FD, SKIPTO = $0128
/*TODO*///		MAXHANG:trigger = $04F8 = 2, PC = $00FD, SKIPTO = $0128
/*TODO*///		OPENICE:trigger = $04F8 = 2, PC = $00FD, SKIPTO = $0128
/*TODO*///	*/
/*TODO*///		COMBINE_WORD_MEM(&dcs_speedup1[offset], data);
/*TODO*///		if (data == 2 && cpu_get_pc() == 0xfd)
/*TODO*///			dcs_speedup_common();
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr dcs_speedup2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///	/*
/*TODO*///		MK2:	trigger = $063D = 2, PC = $00F6, SKIPTO = $0121
/*TODO*///	*/
/*TODO*///		COMBINE_WORD_MEM(&dcs_speedup2[offset], data);
/*TODO*///		if (data == 2 && cpu_get_pc() == 0xf6)
/*TODO*///			dcs_speedup_common();
/*TODO*///	} };
/*TODO*///	
/*TODO*///	static void dcs_speedup_common()
/*TODO*///	{
/*TODO*///	/*
/*TODO*///		00F4: AR = $0002
/*TODO*///		00F5: DM($063D) = AR
/*TODO*///		00F6: SI = $0040
/*TODO*///		00F7: DM($063E) = SI
/*TODO*///		00F8: SR = LSHIFT SI BY -1 (LO)
/*TODO*///		00F9: DM($063F) = SR0
/*TODO*///		00FA: M0 = $3FFF
/*TODO*///		00FB: CNTR = $0006
/*TODO*///		00FC: DO $0120 UNTIL CE
/*TODO*///			00FD: I4 = $0780
/*TODO*///			00FE: I5 = $0700
/*TODO*///			00FF: I0 = $3800
/*TODO*///			0100: I1 = $3800
/*TODO*///			0101: AY0 = DM($063E)
/*TODO*///			0102: M2 = AY0
/*TODO*///			0103: MODIFY (I1,M2)
/*TODO*///			0104: I2 = I1
/*TODO*///			0105: AR = AY0 - 1
/*TODO*///			0106: M3 = AR
/*TODO*///			0107: CNTR = DM($063D)
/*TODO*///			0108: DO $0119 UNTIL CE
/*TODO*///				0109: CNTR = DM($063F)
/*TODO*///				010A: MY0 = DM(I4,M5)
/*TODO*///				010B: MY1 = DM(I5,M5)
/*TODO*///				010C: MX0 = DM(I1,M1)
/*TODO*///				010D: DO $0116 UNTIL CE
/*TODO*///					010E: MR = MX0 * MY0 (SS), MX1 = DM(I1,M1)
/*TODO*///					010F: MR = MR - MX1 * MY1 (RND), AY0 = DM(I0,M1)
/*TODO*///					0110: MR = MX1 * MY0 (SS), AX0 = MR1
/*TODO*///					0111: MR = MR + MX0 * MY1 (RND), AY1 = DM(I0,M0)
/*TODO*///					0112: AR = AY0 - AX0, MX0 = DM(I1,M1)
/*TODO*///					0113: AR = AX0 + AY0, DM(I0,M1) = AR
/*TODO*///					0114: AR = AY1 - MR1, DM(I2,M1) = AR
/*TODO*///					0115: AR = MR1 + AY1, DM(I0,M1) = AR
/*TODO*///					0116: DM(I2,M1) = AR
/*TODO*///				0117: MODIFY (I2,M2)
/*TODO*///				0118: MODIFY (I1,M3)
/*TODO*///				0119: MODIFY (I0,M2)
/*TODO*///			011A: SI = DM($063D)
/*TODO*///			011B: SR = LSHIFT SI BY 1 (LO)
/*TODO*///			011C: DM($063D) = SR0
/*TODO*///			011D: SI = DM($063F)
/*TODO*///			011E: DM($063E) = SI
/*TODO*///			011F: SR = LSHIFT SI BY -1 (LO)
/*TODO*///			0120: DM($063F) = SR0
/*TODO*///	*/
/*TODO*///	
/*TODO*///		INT16 *source = (INT16 *)memory_region(REGION_CPU1 + williams_cpunum);
/*TODO*///		int mem63d = 2;
/*TODO*///		int mem63e = 0x40;
/*TODO*///		int mem63f = mem63e >> 1;
/*TODO*///		int i, j, k;
/*TODO*///	
/*TODO*///		for (i = 0; i < 6; i++)
/*TODO*///		{
/*TODO*///			INT16 *i4 = &source[0x780];
/*TODO*///			INT16 *i5 = &source[0x700];
/*TODO*///			INT16 *i0 = &source[0x3800];
/*TODO*///			INT16 *i1 = &source[0x3800 + mem63e];
/*TODO*///			INT16 *i2 = i1;
/*TODO*///	
/*TODO*///			for (j = 0; j < mem63d; j++)
/*TODO*///			{
/*TODO*///				INT32 mx0, mx1, my0, my1, ax0, ay0, ay1, mr1, temp;
/*TODO*///	
/*TODO*///				my0 = *i4++;
/*TODO*///				my1 = *i5++;
/*TODO*///	
/*TODO*///				for (k = 0; k < mem63f; k++)
/*TODO*///				{
/*TODO*///					mx0 = *i1++;
/*TODO*///					mx1 = *i1++;
/*TODO*///					ax0 = (mx0 * my0 - mx1 * my1) >> 15;
/*TODO*///					mr1 = (mx1 * my0 + mx0 * my1) >> 15;
/*TODO*///					ay0 = i0[0];
/*TODO*///					ay1 = i0[1];
/*TODO*///	
/*TODO*///					temp = ay0 - ax0;
/*TODO*///					if (temp < -32768) temp = -32768;
/*TODO*///					else if (temp > 32767) temp = 32767;
/*TODO*///					*i0++ = temp;
/*TODO*///	
/*TODO*///					temp = ax0 + ay0;
/*TODO*///					if (temp < -32768) temp = -32768;
/*TODO*///					else if (temp > 32767) temp = 32767;
/*TODO*///					*i2++ = temp;
/*TODO*///	
/*TODO*///					temp = ay1 - mr1;
/*TODO*///					if (temp < -32768) temp = -32768;
/*TODO*///					else if (temp > 32767) temp = 32767;
/*TODO*///					*i0++ = temp;
/*TODO*///	
/*TODO*///					temp = ay1 + mr1;
/*TODO*///					if (temp < -32768) temp = -32768;
/*TODO*///					else if (temp > 32767) temp = 32767;
/*TODO*///					*i2++ = temp;
/*TODO*///				}
/*TODO*///				i2 += mem63e;
/*TODO*///				i1 += mem63e;
/*TODO*///				i0 += mem63e;
/*TODO*///			}
/*TODO*///			mem63d <<= 1;
/*TODO*///			mem63e = mem63f;
/*TODO*///			mem63f >>= 1;
/*TODO*///		}
/*TODO*///		cpu_set_reg(ADSP2100_PC, cpu_get_pc() + 0x121 - 0xf6);
/*TODO*///	}
        
        /* PIA structure */
	static pia6821_interface williams_cvsd_pia_intf = new pia6821_interface
	(
		/*inputs : A/B,CA/B1,CA/B2 */ null, null, null, null, null, null,
		/*outputs: A/B,CA/B2       */ williams_dac_data_w, null, null, null,
		/*irqs   : A/B             */ williams_cvsd_irqa, williams_cvsd_irqb
	);
        
        /***************************************************************************
		CUSTOM SOUND INTERFACES
	****************************************************************************/
	
	static ShStartPtr williams_custom_start = new ShStartPtr() {
            @Override
            public int handler(MachineSound msound) {
                /* allocate a DAC stream */
		dac_stream = stream_init("Accelerated DAC", 50, Machine.sample_rate, 0, dac_update);
	
		/* allocate a CVSD stream */
		cvsd_stream = stream_init("Accelerated CVSD", 40, Machine.sample_rate, 0, cvsd_update);
	
		return 0;
            }
        };
        
        /* Custom structure (all non-DCS variants) */
	public static CustomSound_interface williams_custom_interface = new CustomSound_interface
	(
		williams_custom_start,null,null
	);
}
