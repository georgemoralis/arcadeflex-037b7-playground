package arcadeflex.WIP.v037b7.sndhrdw;

import static arcadeflex.WIP.v037b7.sndhrdw.williams.williams_cvsd_readmem;
import static arcadeflex.WIP.v037b7.sndhrdw.williams.williams_cvsd_writemem;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.cpuintrfH.*;
import static arcadeflex.v037b7.mame.driverH.*;

public class williamsH {
    
/***************************************************************************

	Midway/Williams Audio Board

****************************************************************************/

/*TODO*///extern struct MemoryReadAddress williams_cvsd_readmem[];
/*TODO*///extern struct MemoryWriteAddress williams_cvsd_writemem[];
/*TODO*///extern struct MemoryReadAddress williams_adpcm_readmem[];
/*TODO*///extern struct MemoryWriteAddress williams_adpcm_writemem[];
/*TODO*///extern struct MemoryReadAddress williams_narc_master_readmem[];
/*TODO*///extern struct MemoryWriteAddress williams_narc_master_writemem[];
/*TODO*///extern struct MemoryReadAddress williams_narc_slave_readmem[];
/*TODO*///extern struct MemoryWriteAddress williams_narc_slave_writemem[];
/*TODO*///extern struct MemoryReadAddress williams_dcs_readmem[];
/*TODO*///extern struct MemoryWriteAddress williams_dcs_writemem[];
/*TODO*///
/*TODO*///
/*TODO*///extern struct CustomSound_interface williams_custom_interface;
/*TODO*///extern struct YM2151interface williams_cvsd_ym2151_interface;
/*TODO*///extern struct YM2151interface williams_adpcm_ym2151_interface;
/*TODO*///extern struct DACinterface williams_cvsd_dac_interface;
/*TODO*///extern struct DACinterface williams_adpcm_dac_interface;
/*TODO*///extern struct DACinterface williams_narc_dac_interface;
/*TODO*///extern struct hc55516_interface williams_cvsd_interface;
/*TODO*///extern struct OKIM6295interface williams_adpcm_6295_interface_REGION_SOUND1;
/*TODO*///extern struct CustomSound_interface williams_dcs_custom_interface;
/*TODO*///
/*TODO*///void williams_cvsd_init(int cpunum, int pianum);
/*TODO*///void williams_cvsd_reset_w(int state);
/*TODO*///
/*TODO*///void williams_adpcm_init(int cpunum);
/*TODO*///void williams_adpcm_reset_w(int state);
/*TODO*///
/*TODO*///void williams_narc_init(int cpunum);
/*TODO*///void williams_narc_reset_w(int state);
/*TODO*///
/*TODO*///void williams_dcs_init(int cpunum);
/*TODO*///void williams_dcs_reset_w(int state);


        public static MachineCPU SOUND_CPU_WILLIAMS_CVSD = new MachineCPU
        (
		CPU_M6809 | CPU_AUDIO_CPU,							
		8000000/4,	/* 2 MHz */								
		williams_cvsd_readmem,williams_cvsd_writemem,null,null,	
		ignore_interrupt,1									
        );

/*TODO*///#define SOUND_WILLIAMS_CVSD = new MachineSound(									
/*TODO*///	{														
/*TODO*///		SOUND_CUSTOM,										
/*TODO*///		&williams_custom_interface							
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_YM2151,										
/*TODO*///		&williams_cvsd_ym2151_interface						
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_DAC,											
/*TODO*///		&williams_cvsd_dac_interface						
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_HC55516,										
/*TODO*///		&williams_cvsd_interface							
/*TODO*///	}
/*TODO*///
/*TODO*///
/*TODO*///#define SOUND_CPU_WILLIAMS_ADPCM							
/*TODO*///	{														
/*TODO*///		CPU_M6809 | CPU_AUDIO_CPU,							
/*TODO*///		8000000/4,	/* 2 MHz */								
/*TODO*///		williams_adpcm_readmem,williams_adpcm_writemem,0,0,	
/*TODO*///		ignore_interrupt,1									
/*TODO*///	}
/*TODO*///
/*TODO*///#define SOUND_WILLIAMS_ADPCM(rgn)							
/*TODO*///	{														
/*TODO*///		SOUND_CUSTOM,										
/*TODO*///		&williams_custom_interface							
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_YM2151,										
/*TODO*///		&williams_adpcm_ym2151_interface					
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_DAC,											
/*TODO*///		&williams_adpcm_dac_interface						
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_OKIM6295,										
/*TODO*///		&williams_adpcm_6295_interface_##rgn				
/*TODO*///	}
/*TODO*///
/*TODO*///
/*TODO*///#define SOUND_CPU_WILLIAMS_NARC								
/*TODO*///	{														
/*TODO*///		CPU_M6809 | CPU_AUDIO_CPU,							
/*TODO*///		8000000/4,	/* 2 MHz */								
/*TODO*///		williams_narc_master_readmem,williams_narc_master_writemem,0,0,
/*TODO*///		ignore_interrupt,1									
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		CPU_M6809 | CPU_AUDIO_CPU,							
/*TODO*///		8000000/4,	/* 2 MHz */								
/*TODO*///		williams_narc_slave_readmem,williams_narc_slave_writemem,0,0,
/*TODO*///		ignore_interrupt,1									
/*TODO*///	}
/*TODO*///
/*TODO*///#define SOUND_WILLIAMS_NARC									
/*TODO*///	{														
/*TODO*///		SOUND_CUSTOM,										
/*TODO*///		&williams_custom_interface							
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_YM2151,										
/*TODO*///		&williams_adpcm_ym2151_interface					
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_DAC,											
/*TODO*///		&williams_narc_dac_interface						
/*TODO*///	},														
/*TODO*///	{														
/*TODO*///		SOUND_HC55516,										
/*TODO*///		&williams_cvsd_interface							
/*TODO*///	}
/*TODO*///#define SOUND_CPU_WILLIAMS_DCS								
/*TODO*///	{														
/*TODO*///		CPU_ADSP2105 | CPU_AUDIO_CPU,						
/*TODO*///		10240000,	/* 10.24 MHz */							
/*TODO*///		williams_dcs_readmem,williams_dcs_writemem,0,0,		
/*TODO*///		ignore_interrupt,0									
/*TODO*///	}
/*TODO*///
/*TODO*///#define SOUND_WILLIAMS_DCS									
/*TODO*///	{														
/*TODO*///		SOUND_CUSTOM,										
/*TODO*///		&williams_dcs_custom_interface						
/*TODO*///	}
    
}
