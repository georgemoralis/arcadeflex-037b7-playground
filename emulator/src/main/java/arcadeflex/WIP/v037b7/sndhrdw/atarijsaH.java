/***************************************************************************

	Atari Audio Board II Interface

****************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.sndhrdw;

import static arcadeflex.WIP.v037b7.machine.atarigen.atarigen_6502_irq_gen;
import static arcadeflex.WIP.v037b7.machine.atarigenH.*;
import static arcadeflex.WIP.v037b7.sndhrdw.atarijsa.atarijsa1_readmem;
import static arcadeflex.WIP.v037b7.sndhrdw.atarijsa.atarijsa1_writemem;
import static arcadeflex.WIP.v037b7.sndhrdw.atarijsa.atarijsa2_readmem;
import static arcadeflex.WIP.v037b7.sndhrdw.atarijsa.atarijsa2_writemem;
import static arcadeflex.WIP.v037b7.sndhrdw.atarijsa.atarijsa3_readmem;
import static arcadeflex.WIP.v037b7.sndhrdw.atarijsa.atarijsa3_writemem;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;

public class atarijsaH
{
/*TODO*///	
/*TODO*///	
/*TODO*///	void atarijsa_init(int cpunum, int inputport, int testport, int testmask);
/*TODO*///	
/*TODO*///	
/*TODO*///	extern struct MemoryReadAddress atarijsa1_readmem[];
/*TODO*///	extern struct MemoryWriteAddress atarijsa1_writemem[];
/*TODO*///	extern struct MemoryReadAddress atarijsa2_readmem[];
/*TODO*///	extern struct MemoryWriteAddress atarijsa2_writemem[];
/*TODO*///	extern struct MemoryReadAddress atarijsa3_readmem[];
/*TODO*///	extern struct MemoryWriteAddress atarijsa3_writemem[];
/*TODO*///	extern struct MemoryReadAddress atarijsa3s_readmem[];
/*TODO*///	extern struct MemoryWriteAddress atarijsa3s_writemem[];
/*TODO*///	
/*TODO*///	extern struct TMS5220interface atarijsa_tms5220_interface;
/*TODO*///	extern struct YM2151interface atarijsa_ym2151_interface_mono;
/*TODO*///	extern struct YM2151interface atarijsa_ym2151_interface_stereo;
/*TODO*///	extern struct YM2151interface atarijsa_ym2151_interface_stereo_swapped;
/*TODO*///	extern struct POKEYinterface atarijsa_pokey_interface;
/*TODO*///	extern struct OKIM6295interface atarijsa_okim6295_interface_REGION_SOUND1;
/*TODO*///	extern struct OKIM6295interface atarijsa_okim6295s_interface_REGION_SOUND1;
/*TODO*///	
/*TODO*///	
/*TODO*///	/* Used by Blasteroids */
/*TODO*///	#define JSA_I_STEREO										
/*TODO*///		SOUND_SUPPORTS_STEREO,0,0,0,							
/*TODO*///		{														
/*TODO*///			{													
/*TODO*///				SOUND_YM2151, 									
/*TODO*///				&atarijsa_ym2151_interface_stereo				
/*TODO*///			}													
/*TODO*///		}
/*TODO*///	
/*TODO*///	/* Used by Xybots */
/*TODO*///	#define JSA_I_STEREO_SWAPPED								
/*TODO*///		SOUND_SUPPORTS_STEREO,0,0,0,							
/*TODO*///		{														
/*TODO*///			{													
/*TODO*///				SOUND_YM2151, 									
/*TODO*///				&atarijsa_ym2151_interface_stereo_swapped		
/*TODO*///			}													
/*TODO*///		}
/*TODO*///	
/*TODO*///	/* Used by Toobin', Vindicators */
/*TODO*///	#define JSA_I_STEREO_WITH_POKEY								
/*TODO*///		SOUND_SUPPORTS_STEREO,0,0,0,							
/*TODO*///		{														
/*TODO*///			{													
/*TODO*///				SOUND_YM2151, 									
/*TODO*///				&atarijsa_ym2151_interface_stereo				
/*TODO*///			},													
/*TODO*///			{													
/*TODO*///				SOUND_POKEY, 									
/*TODO*///				&atarijsa_pokey_interface 						
/*TODO*///			}													
/*TODO*///		}
/*TODO*///	
/*TODO*///	/* Used by Escape from the Planet of the Robot Monsters */
/*TODO*///	#define JSA_I_MONO_WITH_SPEECH								
/*TODO*///		0,0,0,0,												
/*TODO*///		{														
/*TODO*///			{													
/*TODO*///				SOUND_YM2151, 									
/*TODO*///				&atarijsa_ym2151_interface_mono					
/*TODO*///			},													
/*TODO*///			{													
/*TODO*///				SOUND_TMS5220, 									
/*TODO*///				&atarijsa_tms5220_interface 					
/*TODO*///			}													
/*TODO*///		}
/*TODO*///	
/*TODO*///	/* Used by Cyberball 2072, STUN Runner, Skull & Crossbones, ThunderJaws, Hydra, Pit Fighter */
/*TODO*///	#define JSA_II_MONO(x)										
/*TODO*///		0,0,0,0,												
/*TODO*///		{														
/*TODO*///			{													
/*TODO*///				SOUND_YM2151, 									
/*TODO*///				&atarijsa_ym2151_interface_mono					
/*TODO*///			},													
/*TODO*///			{													
/*TODO*///				SOUND_OKIM6295,									
/*TODO*///				&atarijsa_okim6295_interface_##x				
/*TODO*///			}													
/*TODO*///		}
/*TODO*///	
/*TODO*///	/* Used by Batman, Guardians of the 'Hood, Road Riot 4WD */
/*TODO*///	#define JSA_III_MONO(x)										
/*TODO*///		0,0,0,0,												
/*TODO*///		{														
/*TODO*///			{													
/*TODO*///				SOUND_YM2151, 									
/*TODO*///				&atarijsa_ym2151_interface_mono					
/*TODO*///			},													
/*TODO*///			{													
/*TODO*///				SOUND_OKIM6295,									
/*TODO*///				&atarijsa_okim6295_interface_##x				
/*TODO*///			}													
/*TODO*///		}
/*TODO*///	
/*TODO*///	/* Used by Off the Wall */
/*TODO*///	#define JSA_III_MONO_NO_SPEECH								
/*TODO*///		0,0,0,0,												
/*TODO*///		{														
/*TODO*///			{													
/*TODO*///				SOUND_YM2151, 									
/*TODO*///				&atarijsa_ym2151_interface_mono					
/*TODO*///			}													
/*TODO*///		}
/*TODO*///	
/*TODO*///	/* Used by Space Lords, Moto Frenzy, Steel Talons, Road Riot's Revenge Rally */
/*TODO*///	#define JSA_IIIS_STEREO(x)									
/*TODO*///		SOUND_SUPPORTS_STEREO,0,0,0,							
/*TODO*///		{														
/*TODO*///			{													
/*TODO*///				SOUND_YM2151, 									
/*TODO*///				&atarijsa_ym2151_interface_stereo				
/*TODO*///			},													
/*TODO*///			{													
/*TODO*///				SOUND_OKIM6295,									
/*TODO*///				&atarijsa_okim6295s_interface_##x				
/*TODO*///			}													
/*TODO*///		}
	
	
	/* Common CPU definitions */
	public static MachineCPU JSA_I_CPU = new MachineCPU
        (											
			CPU_M6502,											
			ATARI_CLOCK_14MHz/8,								
			atarijsa1_readmem,atarijsa1_writemem,null,null,			
			null,0,												
			atarigen_6502_irq_gen,(int)(1000000000.0/((double)ATARI_CLOCK_14MHz/4/4/16/16/14)) 
        );
	
	public static MachineCPU JSA_II_CPU = new MachineCPU
        (
														
			CPU_M6502,											
			ATARI_CLOCK_14MHz/8,								
			atarijsa2_readmem,atarijsa2_writemem,null,null,
			null,0,												
			atarigen_6502_irq_gen,(int)(1000000000.0/((double)ATARI_CLOCK_14MHz/4/4/16/16/14)) 
        );
	
        public static MachineCPU JSA_III_CPU = new MachineCPU
        (											
			CPU_M6502,											
			ATARI_CLOCK_14MHz/8,								
			atarijsa3_readmem,atarijsa3_writemem,null,null,			
			null,0,												
			atarigen_6502_irq_gen,(int)(1000000000.0/((double)ATARI_CLOCK_14MHz/4/4/16/16/14)) 
        );
	
/*TODO*///	#define JSA_IIIS_CPU										
/*TODO*///		{														
/*TODO*///			CPU_M6502,											
/*TODO*///			ATARI_CLOCK_14MHz/8,								
/*TODO*///			atarijsa3s_readmem,atarijsa3s_writemem,0,0,			
/*TODO*///			0,0,												
/*TODO*///			atarigen_6502_irq_gen,(UINT32)(1000000000.0/((double)ATARI_CLOCK_14MHz/4/4/16/16/14)) 
/*TODO*///		}
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	/* Board-specific port definitions */
/*TODO*///	#define JSA_I_PORT											
/*TODO*///		PORT_START(); 												
/*TODO*///		PORT_BIT( 0x01, IP_ACTIVE_HIGH, IPT_COIN1 );			
/*TODO*///		PORT_BIT( 0x02, IP_ACTIVE_HIGH, IPT_COIN2 );			
/*TODO*///		PORT_BIT( 0x04, IP_ACTIVE_HIGH, IPT_COIN3 );			
/*TODO*///		PORT_BIT( 0x08, IP_ACTIVE_HIGH, IPT_UNUSED );		
/*TODO*///		PORT_BIT( 0x10, IP_ACTIVE_HIGH, IPT_UNUSED );/* speech chip ready */
/*TODO*///		PORT_BIT( 0x20, IP_ACTIVE_HIGH, IPT_UNUSED );/* output buffer full */
/*TODO*///		PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_UNUSED );	/* input buffer full */
/*TODO*///		PORT_BIT( 0x80, IP_ACTIVE_HIGH, IPT_UNUSED );/* self test */
	
	/* used by Xybots */
	public static void JSA_I_PORT_SWAPPED() {									
		PORT_START(); 												
		PORT_BIT( 0x01, IP_ACTIVE_HIGH, IPT_COIN2 );			
		PORT_BIT( 0x02, IP_ACTIVE_HIGH, IPT_COIN1 );			
		PORT_BIT( 0x04, IP_ACTIVE_HIGH, IPT_COIN3 );			
		PORT_BIT( 0x08, IP_ACTIVE_HIGH, IPT_UNUSED );		
		PORT_BIT( 0x10, IP_ACTIVE_HIGH, IPT_UNUSED );/* speech chip ready */
		PORT_BIT( 0x20, IP_ACTIVE_HIGH, IPT_UNUSED );/* output buffer full */
		PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_UNUSED );	/* input buffer full */
		PORT_BIT( 0x80, IP_ACTIVE_HIGH, IPT_UNUSED );/* self test */
        }
	
	public static void JSA_II_PORT() {
		PORT_START(); 												
		PORT_BIT( 0x01, IP_ACTIVE_HIGH, IPT_COIN1 );			
		PORT_BIT( 0x02, IP_ACTIVE_HIGH, IPT_COIN2 );			
		PORT_BIT( 0x04, IP_ACTIVE_HIGH, IPT_COIN3 );			
		PORT_BIT( 0x08, IP_ACTIVE_HIGH, IPT_UNUSED );		
		PORT_BIT( 0x10, IP_ACTIVE_HIGH, IPT_UNUSED );		
		PORT_BIT( 0x20, IP_ACTIVE_HIGH, IPT_UNUSED );/* output buffer full */
		PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_UNUSED );	/* input buffer full */
		PORT_BIT( 0x80, IP_ACTIVE_HIGH, IPT_UNUSED );/* self test */
        }
	
        public static void JSA_III_PORT() {
		PORT_START(); 												
		PORT_BIT( 0x01, IP_ACTIVE_HIGH, IPT_COIN2 );			
		PORT_BIT( 0x02, IP_ACTIVE_HIGH, IPT_COIN1 );			
		PORT_BIT( 0x04, IP_ACTIVE_HIGH, IPT_TILT );			
		PORT_BIT( 0x08, IP_ACTIVE_HIGH, IPT_SERVICE );		
		PORT_BIT( 0x10, IP_ACTIVE_HIGH, IPT_UNUSED );/* self test */
		PORT_BIT( 0x20, IP_ACTIVE_HIGH, IPT_UNUSED );/* output buffer full */
		PORT_BIT( 0x40, IP_ACTIVE_HIGH, IPT_UNUSED );/* input buffer full */
		PORT_BIT( 0x80, IP_ACTIVE_HIGH, IPT_UNUSED );/* self test */
        }
	
}