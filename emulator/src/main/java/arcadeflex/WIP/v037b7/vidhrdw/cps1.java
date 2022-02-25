/***************************************************************************

Thse are some of the CPS-B chip numbers:

NAME                                        CPS-B #                     C-board PAL's  B-board PAL's
Lost Worlds                                 CPS-B-01  ?                 None           ?
Ghouls 'n Ghosts                            CPS-B-01                    None           ?
Strider                                     CPS-B-01  DL-0411-1000      None           ST24N1 & LW10
Final Fight                                 CPS-B-04  ?                 None           ?
Area 88                                     CPS-B-11  ?                 C626           ?
Mercs (US)                                  CPS-B-12  DL-0411-10007     C626orC628?    ?
Magic Sword (US)                            CPS-B-13  ?                 None           ?
Street Fighter II                           CPS-B-14  DL-0411-10029     C632           ?
Carrier Air Wing                            CPS-B-16  DL-0411-10011     ?              ?
Street Fighter II                           CPS-B-17  DL-0411-10012     C632           ?
Captain Commando*(US)                       CPS-B-21  DL-0921-10014     10C1 & C632    CC63B, CCPRG & 10B1
King of Dragons*                            CPS-B-21  DL-0921-10014     10C1 & C632    KR63B, BPRG1 & 10B1
Knights of the Round*                       CPS-B-21  DL-0921-10014     10C1 & C632    ?
Street Fighter II' Champion Edition         CPS-B-21  DL-0921-10014     10C1 & C632    ?
Street Fighter II Turbo Hyper Fighting      CPS-B-21  DL-0921-10014     10C1 & C632    S9263B, BPRG1 & 10B1
Saturday Night Slam Masters*                CPS-B-21  DL-0921-10014     10C1           MB63B, BPRG1 & 10B1

*denotes Suicide Battery



OUTPUT PORTS
0x00-0x01     OBJ RAM base (/256)
0x02-0x03     Scroll1 (8x8) RAM base (/256)
0x04-0x05     Scroll2 (16x16) RAM base (/256)
0x06-0x07     Scroll3 (32x32) RAM base (/256)
0x08-0x09     rowscroll RAM base (/256)
0x0a-0x0b     Palette base (/256)
0x0c-0x0d     Scroll 1 X
0x0e-0x0f     Scroll 1 Y
0x10-0x11     Scroll 2 X
0x12-0x13     Scroll 2 Y
0x14-0x15     Scroll 3 X
0x16-0x17     Scroll 3 Y
0x18-0x19     Starfield 1 X
0x1a-0x1b     Starfield 1 Y
0x1c-0x1d     Starfield 2 X
0x1e-0x1f     Starfield 2 Y
0x20-0x21     start offset for the rowscroll matrix
0x22-0x23     unknown but widely used - usually 0x0e. bit 0 enables rowscroll
              on layer 2. bit 15 is flip screen.


Some registers move from game to game.. following example strider
0x66-0x67	Layer control register
			bits 14-15 seem to be unused
			bits 6-13 (4 groups of 2 bits) select layer draw order
			bits 1-5 enable the three tilemap layers and the two starfield
				layers (the bit order changes from game to game).
				Only Forgotten Worlds and Strider use the starfield.
			bit 0 could be rowscroll related. It is set by bionic commando,
			varth, mtwins, mssword, cawing while rowscroll is active. However
			kodj and sf2 do NOT set this bit while they are using rowscroll.
0x68-0x69	Priority mask \   Tiles in the layer just below sprites can have
0x6a-0x6b	Priority mask |   four priority levels, each one associated with one
0x6c-0x6d	Priority mask |   of these masks. The masks indicate pens in the tile
0x6e-0x6f	Priority mask /   that have priority over sprites.
0x70-0x71	Control register (usually 0x003f). The function of this register
			is unknown; experiments on the real board show that values
			different from 0x3f in the low 6 bits cause wrong colors. The
			other bits seem to be unused.
			The only place where this register seems to be set to a value
			different from 0x3f is during startup tests. Examples:
			ghouls  0x02
			strider 0x02
			unsquad 0x0f
			kod     0x0f
			mtwins  0x0f

Fixed registers
0x80-0x81     Sound command
0x88-0x89     Sound fade

Known Bug List
==============
All games
* Large sprites don't exit smoothly on the left side of the screen (e.g. Car
in Mega Man attract mode, cadillac in Cadillacs and Dinosaurs)
* There might be problems if high priority tiles (over sprites) and rowscroll
are used at the same time, because the priority buffer is not rowscrolled.
The only place I know were this might cause problems is the cave in mtwins,
which waves to simulate heat. I haven't noticed anything wrong, though.

Magic Sword.
* during attract mode, characters are shown with a black background. There is
a background, but the layers are disabled. I think this IS the correct
behaviour.

King of Dragons (World).
* Distortion effect missing on character description screen during attract
mode. The game rapidly toggles on and off the layer enable bit. Again, I
think this IS the correct behaviour. The Japanese version does the
distortion as expected.

3wonders
* one bad tile at the end of level 1
* writes to output ports 42, 44, 46.

qad
* layer enable mask incomplete

dino
* in level 6, the legs of the big dino which stomps you are almost entirely
missing.
* in level 6, palette changes due to lightnings cause a lot of tiling effects
on scroll2.

wof
* In round 8, when the player goes over a bridge, there is a problem with
some sprites. When an enemy falls to the floor near the edge of the bridge,
parts of it become visible under the bridge.


Unknown issues
==============

There are often some redundant high bits in the scroll layer's attributes.
I think that these are spare bits that the game uses for to store additional
information, not used by the hardware.
The games seem to use them to mark platforms, kill zones and no-go areas.

***************************************************************************/

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */ 
package arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.WIP.v037b7.drivers.cps1.cps1_eeprom_port_r;
import static arcadeflex.WIP.v037b7.drivers.cps1.cps1_eeprom_port_w;
import static arcadeflex.WIP.v037b7.vidhrdw.cps1draw.*;
import arcadeflex.common.ptrLib.UBytePtr;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.common.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.memory.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.v037b7.mame.usrintrf.usrintf_showmessage; 
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.common.libc.cstring.*;
import static arcadeflex.common.libc.cstring.strcmp;
import arcadeflex.common.subArrays.UShortArray;
//import arcadeflex.common.subArrays.UShortArray;
//import arcadeflex.common.ptrLib.UShortPtr;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.*;

public class cps1
{

        	
/*TODO*///	#define VERBOSE 0
/*TODO*///	
/*TODO*///	#define CPS1_DUMP_VIDEO 0
	
	
	/********************************************************************
	
				Configuration table:
	
	********************************************************************/
	
	/* Game specific data */
	public static class CPS1config
	{
		String name;             /* game driver name */
	
		/* Some games interrogate a couple of registers on bootup. */
		/* These are CPS1 board B self test checks. They wander from game to */
		/* game. */
		int cpsb_addr;        /* CPS board B test register address */
		int cpsb_value;       /* CPS board B test register expected value */
	
		/* some games use as a protection check the ability to do 16-bit multiplies */
		/* with a 32-bit result, by writing the factors to two ports and reading the */
		/* result from two other ports. */
		/* It looks like this feature was introduced with 3wonders (CPSB ID = 08xx) */
		int mult_factor1;
		int mult_factor2;
		int mult_result_lo;
		int mult_result_hi;
	
		int layer_control;
		int priority0;
		int priority1;
		int priority2;
		int priority3;
		int control_reg;  /* Control register? seems to be always 0x3f */
	
		/* ideally, the layer enable masks should consist of only one bit, */
		/* but in many cases it is unknown which bit is which. */
		int scrl1_enable_mask;
		int scrl2_enable_mask;
		int scrl3_enable_mask;
		int stars_enable_mask;
	
		int bank_scroll1;
		int bank_scroll2;
		int bank_scroll3;
	
		/* Some characters aren't visible */
		int space_scroll1;
		int start_scroll2;
		int end_scroll2;
		int start_scroll3;
		int end_scroll3;
	
		int kludge;  /* Ghouls n Ghosts sprite kludge */

            private CPS1config(String name, int cpsb_addr, int cpsb_value, int mult_factor1, int mult_factor2, int mult_result_lo, int mult_result_hi,
                int layer_control, int priority0, int priority1, int priority2, int priority3, int control_reg, int scrl1_enable_mask, int scrl2_enable_mask,
                int scrl3_enable_mask, int stars_enable_mask, int bank_scroll1, int bank_scroll2, int bank_scroll3, int space_scroll1,
                int start_scroll2, int end_scroll2, int start_scroll3, int end_scroll3, int kludge) {
                this.name = name;
                this.cpsb_addr = cpsb_addr;
                this.cpsb_value = cpsb_value;
                this.mult_factor1 = mult_factor1;
                this.mult_factor2 = mult_factor2;
                this.mult_result_lo = mult_result_lo;
                this.mult_result_hi = mult_result_hi;
                this.layer_control = layer_control;
                this.priority0 = priority0;
                this.priority1 = priority1;
                this.priority2 = priority2;
                this.priority3 = priority3;
                this.control_reg = control_reg;
                this.scrl1_enable_mask = scrl1_enable_mask;
                this.scrl2_enable_mask = scrl2_enable_mask;
                this.scrl3_enable_mask = scrl3_enable_mask;
                this.stars_enable_mask = stars_enable_mask;
                this.bank_scroll1 = bank_scroll1;
                this.bank_scroll2 = bank_scroll2;
                this.bank_scroll3 = bank_scroll3;
                this.space_scroll1 = space_scroll1;
                this.start_scroll2 = start_scroll2;
                this.end_scroll2 = end_scroll2;
                this.start_scroll3 = start_scroll3;
                this.end_scroll3 = end_scroll3;
                this.kludge = kludge;
            }
	};
	
	static CPS1config cps1_game_config = null;
	
	static CPS1config cps1_config_table[]=
	{
		/* name       CPSB ID    multiply protection  ctrl    priority masks  unknwn     layer enable     banks spacechr kludge */
		new CPS1config("forgottn",0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x30, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 7),
		new CPS1config("lostwrld",0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x30, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 7),
		new CPS1config("ghouls",  0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 1),
		new CPS1config("ghoulsu", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 1),
		new CPS1config("ghoulsj", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 1),
		new CPS1config("strider", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x30, 1,0,1,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("striderj",0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x30, 1,0,1,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("stridrja",0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x30, 1,0,1,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("dwj",     0x00,0x0000, 0x00,0x00,0x00,0x00, 0x6c,0x6a,0x68,0x66,0x64,0x62, 0x02,0x04,0x08,0x00, 0,1,1,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("willow",  0x00,0x0000, 0x00,0x00,0x00,0x00, 0x70,0x6e,0x6c,0x6a,0x68,0x66, 0x20,0x10,0x08,0x00, 0,1,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("willowj", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x70,0x6e,0x6c,0x6a,0x68,0x66, 0x20,0x10,0x08,0x00, 0,1,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("unsquad", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x38,0x38,0x38,0x00, 0,0,0,     -1,0x0000,0xffff,0x0001,0xffff, 0 ),
		new CPS1config("area88",  0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x38,0x38,0x38,0x00, 0,0,0,     -1,0x0000,0xffff,0x0001,0xffff, 0 ),
		new CPS1config("ffight",  0x60,0x0004, 0x00,0x00,0x00,0x00, 0x6e,0x66,0x70,0x68,0x72,0x6a, 0x02,0x0c,0x0c,0x00, 0,0,0,     -1,0x0001,0xffff,0x0001,0xffff, 0 ),
		new CPS1config("ffightu", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x0c,0x0c,0x00, 0,0,0,     -1,0x0001,0xffff,0x0001,0xffff, 0 ),
		new CPS1config("ffightj", 0x60,0x0004, 0x00,0x00,0x00,0x00, 0x6e,0x66,0x70,0x68,0x72,0x6a, 0x02,0x0c,0x0c,0x00, 0,0,0,     -1,0x0001,0xffff,0x0001,0xffff, 0 ),
		new CPS1config("1941",    0x60,0x0005, 0x00,0x00,0x00,0x00, 0x68,0x6a,0x6c,0x6e,0x70,0x72, 0x02,0x08,0x20,0x14, 0,0,0,     -1,0x0000,0xffff,0x0400,0x07ff, 0 ),
		new CPS1config("1941j",   0x60,0x0005, 0x00,0x00,0x00,0x00, 0x68,0x6a,0x6c,0x6e,0x70,0x72, 0x02,0x08,0x20,0x14, 0,0,0,     -1,0x0000,0xffff,0x0400,0x07ff, 0 ),
		new CPS1config("mercs",   0x60,0x0402, 0x00,0x00,0x00,0x00, 0x6c,0x00,0x00,0x00,0x00,0x62, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0600,0x5bff,0x0700,0x17ff, 4 ),	/* (uses port 74) */
		new CPS1config("mercsu",  0x60,0x0402, 0x00,0x00,0x00,0x00, 0x6c,0x00,0x00,0x00,0x00,0x62, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0600,0x5bff,0x0700,0x17ff, 4 ),	/* (uses port 74) */
		new CPS1config("mercsj",  0x60,0x0402, 0x00,0x00,0x00,0x00, 0x6c,0x00,0x00,0x00,0x00,0x62, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0600,0x5bff,0x0700,0x17ff, 4 ),	/* (uses port 74) */
		new CPS1config("mtwins",  0x5e,0x0404, 0x00,0x00,0x00,0x00, 0x52,0x54,0x56,0x58,0x5a,0x5c, 0x38,0x38,0x38,0x00, 0,0,0,     -1,0x0000,0x3fff,0x0e00,0xffff, 0 ),
		new CPS1config("chikij",  0x5e,0x0404, 0x00,0x00,0x00,0x00, 0x52,0x54,0x56,0x58,0x5a,0x5c, 0x38,0x38,0x38,0x00, 0,0,0,     -1,0x0000,0x3fff,0x0e00,0xffff, 0 ),
		new CPS1config("msword",  0x00,0x0000, 0x00,0x00,0x00,0x00, 0x62,0x64,0x66,0x68,0x6a,0x6c, 0x20,0x06,0x06,0x00, 0,0,0,     -1,0x2800,0x37ff,0x0000,0xffff, 0 ),
		new CPS1config("mswordu", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x62,0x64,0x66,0x68,0x6a,0x6c, 0x20,0x06,0x06,0x00, 0,0,0,     -1,0x2800,0x37ff,0x0000,0xffff, 0 ),
		new CPS1config("mswordj", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x62,0x64,0x66,0x68,0x6a,0x6c, 0x20,0x06,0x06,0x00, 0,0,0,     -1,0x2800,0x37ff,0x0000,0xffff, 0 ),
		new CPS1config("cawing",  0x40,0x0406, 0x00,0x00,0x00,0x00, 0x4c,0x4a,0x48,0x46,0x44,0x42, 0x10,0x0a,0x0a,0x00, 0,0,0, 0x0002,0x0000,0xffff,0x0000,0xffff, 6),	/* row scroll used at the beginning of mission 8, put 07 at ff8501 to jump there */
		new CPS1config("cawingj", 0x40,0x0406, 0x00,0x00,0x00,0x00, 0x4c,0x4a,0x48,0x46,0x44,0x42, 0x10,0x0a,0x0a,0x00, 0,0,0, 0x0002,0x0000,0xffff,0x0000,0xffff, 6),	/* row scroll used at the beginning of mission 8, put 07 at ff8501 to jump there */
		new CPS1config("nemo",    0x4e,0x0405, 0x00,0x00,0x00,0x00, 0x42,0x44,0x46,0x48,0x4a,0x4c, 0x04,0x22,0x22,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("nemoj",   0x4e,0x0405, 0x00,0x00,0x00,0x00, 0x42,0x44,0x46,0x48,0x4a,0x4c, 0x04,0x22,0x22,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2",     0x48,0x0407, 0x00,0x00,0x00,0x00, 0x54,0x52,0x50,0x4e,0x4c,0x4a, 0x08,0x10,0x02,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2a",    0x48,0x0407, 0x00,0x00,0x00,0x00, 0x54,0x52,0x50,0x4e,0x4c,0x4a, 0x08,0x10,0x02,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2b",    0x48,0x0407, 0x00,0x00,0x00,0x00, 0x54,0x52,0x50,0x4e,0x4c,0x4a, 0x08,0x10,0x02,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2e",    0xd0,0x0408, 0x00,0x00,0x00,0x00, 0xdc,0xda,0xd8,0xd6,0xd4,0xd2, 0x10,0x0a,0x0a,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2j",    0x6e,0x0403, 0x00,0x00,0x00,0x00, 0x62,0x64,0x66,0x68,0x6a,0x6c, 0x20,0x06,0x06,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2jb",   0x48,0x0407, 0x00,0x00,0x00,0x00, 0x54,0x52,0x50,0x4e,0x4c,0x4a, 0x08,0x10,0x02,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		/* from here onwards the CPS-B board has suicide battery and multiply protection */
		new CPS1config("3wonders",0x72,0x0800, 0x4e,0x4c,0x4a,0x48, 0x68,0x66,0x64,0x62,0x60,0x70, 0x20,0x04,0x08,0x12, 0,1,1,     -1,0x0000,0xffff,0x0000,0xffff, 2),
		new CPS1config("wonder3", 0x72,0x0800, 0x4e,0x4c,0x4a,0x48, 0x68,0x66,0x64,0x62,0x60,0x70, 0x20,0x04,0x08,0x12, 0,1,1,     -1,0x0000,0xffff,0x0000,0xffff, 2),
		new CPS1config("kod",     0x00,0x0000, 0x5e,0x5c,0x5a,0x58, 0x60,0x6e,0x6c,0x6a,0x68,0x70, 0x30,0x08,0x30,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("kodj",    0x00,0x0000, 0x5e,0x5c,0x5a,0x58, 0x60,0x6e,0x6c,0x6a,0x68,0x70, 0x30,0x08,0x30,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("kodb",    0x00,0x0000, 0x00,0x00,0x00,0x00, 0x60,0x6e,0x6c,0x6a,0x68,0x70, 0x30,0x08,0x30,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("captcomm",0x00,0x0000, 0x46,0x44,0x42,0x40, 0x60,0x6e,0x6c,0x6a,0x68,0x70, 0x20,0x12,0x12,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),	/* multiply is used only to center the startup text */
		new CPS1config("captcomu",0x00,0x0000, 0x46,0x44,0x42,0x40, 0x60,0x6e,0x6c,0x6a,0x68,0x70, 0x20,0x12,0x12,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),	/* multiply is used only to center the startup text */
		new CPS1config("captcomj",0x00,0x0000, 0x46,0x44,0x42,0x40, 0x60,0x6e,0x6c,0x6a,0x68,0x70, 0x20,0x12,0x12,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),	/* multiply is used only to center the startup text */
		new CPS1config("knights", 0x00,0x0000, 0x46,0x44,0x42,0x40, 0x68,0x66,0x64,0x62,0x60,0x70, 0x20,0x10,0x02,0x00, 0,0,0, 0xf020,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("knightsu",0x00,0x0000, 0x46,0x44,0x42,0x40, 0x68,0x66,0x64,0x62,0x60,0x70, 0x20,0x10,0x02,0x00, 0,0,0, 0xf020,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("knightsj",0x00,0x0000, 0x46,0x44,0x42,0x40, 0x68,0x66,0x64,0x62,0x60,0x70, 0x20,0x10,0x02,0x00, 0,0,0, 0xf020,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2ce",   0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2cea",  0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2ceb",  0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2cej",  0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2rb",   0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2red",  0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2accp2",0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("varth",   0x00,0x0000, 0x00,0x00,0x00,0x00, 0x6e,0x66,0x70,0x68,0x72,0x6a, 0x02,0x0c,0x0c,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),	/* CPSB test has been patched out (60=0008) */
		new CPS1config("varthu",  0x00,0x0000, 0x00,0x00,0x00,0x00, 0x6e,0x66,0x70,0x68,0x72,0x6a, 0x02,0x0c,0x0c,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),	/* CPSB test has been patched out (60=0008) */
		new CPS1config("varthj",  0x00,0x0000, 0x4e,0x4c,0x4a,0x48, 0x60,0x6e,0x6c,0x6a,0x68,0x70, 0x20,0x06,0x06,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),	/* CPSB test has been patched out (72=0001) */
		new CPS1config("cworld2j",0x00,0x0000, 0x00,0x00,0x00,0x00, 0x60,0x6e,0x6c,0x6a,0x68,0x70, 0x20,0x14,0x14,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),  /* The 0x76 priority values are incorrect values */
		new CPS1config("wof",     0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("wofa",    0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("wofj",    0x00,0x0000, 0x00,0x00,0x00,0x00, 0x62,0x64,0x66,0x68,0x6a,0x6c, 0x10,0x08,0x04,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("dino",    0x00,0x0000, 0x00,0x00,0x00,0x00, 0x4a,0x4c,0x4e,0x40,0x42,0x44, 0x16,0x16,0x16,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),	/* layer enable never used */
		new CPS1config("dinoj",   0x00,0x0000, 0x00,0x00,0x00,0x00, 0x4a,0x4c,0x4e,0x40,0x42,0x44, 0x16,0x16,0x16,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),	/* layer enable never used */
		new CPS1config("punisher",0x4e,0x0c00, 0x00,0x00,0x00,0x00, 0x52,0x54,0x56,0x48,0x4a,0x4c, 0x04,0x02,0x20,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("punishru",0x4e,0x0c00, 0x00,0x00,0x00,0x00, 0x52,0x54,0x56,0x48,0x4a,0x4c, 0x04,0x02,0x20,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("punishrj",0x4e,0x0c00, 0x00,0x00,0x00,0x00, 0x52,0x54,0x56,0x48,0x4a,0x4c, 0x04,0x02,0x20,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("slammast",0x6e,0x0c01, 0x00,0x00,0x00,0x00, 0x56,0x40,0x42,0x68,0x6a,0x6c, 0x04,0x08,0x10,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("slammasu",0x6e,0x0c01, 0x00,0x00,0x00,0x00, 0x56,0x40,0x42,0x68,0x6a,0x6c, 0x04,0x08,0x10,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("mbomberj",0x6e,0x0c01, 0x00,0x00,0x00,0x00, 0x56,0x40,0x42,0x68,0x6a,0x6c, 0x04,0x08,0x10,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("mbombrd", 0x5e,0x0c02, 0x00,0x00,0x00,0x00, 0x6a,0x6c,0x6e,0x70,0x72,0x5c, 0x04,0x08,0x10,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("mbombrdj",0x5e,0x0c02, 0x00,0x00,0x00,0x00, 0x6a,0x6c,0x6e,0x70,0x72,0x5c, 0x04,0x08,0x10,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2t",    0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sf2tj",   0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 2,2,2,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("pnickj",  0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x00,0x00,0x00,0x00,0x70, 0x0e,0x0e,0x0e,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("qad",     0x00,0x0000, 0x00,0x00,0x00,0x00, 0x6c,0x00,0x00,0x00,0x00,0x52, 0x14,0x02,0x14,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("qadj",    0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x0e,0x0e,0x0e,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("qtono2",  0x00,0x0000, 0x40,0x42,0x44,0x46, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x06,0x06,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("pang3",   0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x0c,0x0c,0x30, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 5),	/* EEPROM port is among the CPS registers */
		new CPS1config("pang3j",  0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x0c,0x0c,0x30, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 5), /* EEPROM port is among the CPS registers */
		new CPS1config("megaman", 0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("rockmanj",0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		new CPS1config("sfzch",   0x00,0x0000, 0x00,0x00,0x00,0x00, 0x66,0x68,0x6a,0x6c,0x6e,0x70, 0x02,0x04,0x08,0x00, 0,0,0,     -1,0x0000,0xffff,0x0000,0xffff, 0 ),
		null		/* End of table */
	};
	
	public static InitMachinePtr cps1_init_machine = new InitMachinePtr() { public void handler() 
	{
		String gamename = Machine.gamedrv.name;
		UBytePtr RAM = memory_region(REGION_CPU1);
	
	
		//CPS1config pCFG=cps1_config_table[0];
                int _pCFG=0;
		while(cps1_config_table[_pCFG].name != null)
		{
			if (strcmp(cps1_config_table[_pCFG].name, gamename) == 0)
			{
				break;
			}
			_pCFG++;
		}
		cps1_game_config=cps1_config_table[_pCFG];
	
		if (strcmp(gamename, "sf2rb" )==0)
		{
			/* Patch out protection check */
			RAM.WRITE_WORD(0xe5464,0x6012);
		}
/*TODO*///	#if 0
/*TODO*///		else if (strcmp(gamename, "ghouls" )==0)
/*TODO*///		{
/*TODO*///			/* Patch out self-test... it takes forever */
/*TODO*///			WRITE_WORD(&RAM[0x61964+0], 0x4ef9);
/*TODO*///			WRITE_WORD(&RAM[0x61964+2], 0x0000);
/*TODO*///			WRITE_WORD(&RAM[0x61964+4], 0x0400);
/*TODO*///		}
/*TODO*///	#endif
	} };
	
	
	
	static int cps1_port(int offset)
	{
		return cps1_output.READ_WORD(offset);
	}
	
	static UBytePtr cps1_base(int offset,int boundary)
	{
		int base=cps1_port(offset)*256;
		/*
		The scroll RAM must start on a 0x4000 boundary.
		Some games do not do this.
		For example:
		   Captain commando     - continue screen will not display
		   Muscle bomber games  - will animate garbage during gameplay
		Mask out the irrelevant bits.
		*/
		base &= ~(boundary-1);
	 	return new UBytePtr(cps1_gfxram, base&0x3ffff);
	}
	
	
	
	public static ReadHandlerPtr cps1_output_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
/*TODO*///	#if VERBOSE
/*TODO*///	if (offset >= 0x18) logerror("PC %06x: read output port %02x\n",cpu_get_pc(),offset);
/*TODO*///	#endif
	
		/* Some games interrogate a couple of registers on bootup. */
		/* These are CPS1 board B self test checks. They wander from game to */
		/* game. */
		if (offset!=0 && offset == cps1_game_config.cpsb_addr)
			return cps1_game_config.cpsb_value;
	
		/* some games use as a protection check the ability to do 16-bit multiplies */
		/* with a 32-bit result, by writing the factors to two ports and reading the */
		/* result from two other ports. */
		if (offset!=0 && offset == cps1_game_config.mult_result_lo)
			return (cps1_output.READ_WORD(cps1_game_config.mult_factor1) *
					cps1_output.READ_WORD(cps1_game_config.mult_factor2)) & 0xffff;
		if (offset!=0 && offset == cps1_game_config.mult_result_hi)
			return (cps1_output.READ_WORD(cps1_game_config.mult_factor1) *
					cps1_output.READ_WORD(cps1_game_config.mult_factor2)) >> 16;
	
		/* Pang 3 EEPROM interface */
		if (cps1_game_config.kludge == 5 && offset == 0x7a)
			return cps1_eeprom_port_r.handler(0);
	
		return cps1_output.READ_WORD(offset);
	} };
	
	public static WriteHandlerPtr cps1_output_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		/* Pang 3 EEPROM interface */
		if (cps1_game_config.kludge == 5 && offset == 0x7a)
		{
			cps1_eeprom_port_w.handler(0,data);
			return;
		}
	
		COMBINE_WORD_MEM(cps1_output,offset,data);
	
		data = cps1_output.READ_WORD(offset);
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	if (cps1_game_config.control_reg && offset == cps1_game_config.control_reg && data != 0x3f)
/*TODO*///		usrintf_showmessage("control_reg = %04x",data);
/*TODO*///	#endif
/*TODO*///	#if VERBOSE
/*TODO*///	if (offset >= 0x22 &&
/*TODO*///			offset != cps1_game_config.layer_control &&
/*TODO*///			offset != cps1_game_config.priority0 &&
/*TODO*///			offset != cps1_game_config.priority1 &&
/*TODO*///			offset != cps1_game_config.priority2 &&
/*TODO*///			offset != cps1_game_config.priority3 &&
/*TODO*///			offset != cps1_game_config.control_reg)
/*TODO*///		logerror("PC %06x: write %02x to output port %02x\n",cpu_get_pc(),data,offset);
/*TODO*///	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	if (offset == 0x22 && (data & ~0x8001) != 0x0e)
/*TODO*///		usrintf_showmessage("port 22 = %02x",data);
/*TODO*///	if (cps1_game_config.priority0 && offset == cps1_game_config.priority0 && data != 0x00)
/*TODO*///		usrintf_showmessage("priority0 %04x",data);
/*TODO*///	#endif
/*TODO*///	#endif
	} };
	
	
	
	/* Public variables */
        public static UBytePtr cps1_gfxram = new UBytePtr();
	public static UBytePtr cps1_output = new UBytePtr();
	
	
	public static int[] cps1_gfxram_size = new int[1];
	public static int[] cps1_output_size = new int[1];
	
	/* Private */

	/* Offset of each palette entry */
	static int cps1_obj_palette     = 0*32;
	static int cps1_scroll1_palette = 1*32;
        static int cps1_scroll2_palette = 2*32;
        static int cps1_scroll3_palette = 3*32;
        static int cps1_stars1_palette  = 4*32;
        static int cps1_stars2_palette  = 5*32;
        static int cps1_palette_entries = (32*8);  /* Number colour schemes in palette */
	
        static int cps1_scroll1_size=0x4000;
        static int cps1_scroll2_size=0x4000;
        static int cps1_scroll3_size=0x4000;
        static int cps1_obj_size    =0x0800;
        static int cps1_other_size  =0x0800;
        static int cps1_palette_size=cps1_palette_entries*32; /* Size of palette RAM */
        static int cps1_flip_screen;    /* Flip screen on / off */

	static UBytePtr cps1_scroll1 = new UBytePtr();
	static UBytePtr cps1_scroll2 = new UBytePtr();
	static UBytePtr cps1_scroll3 = new UBytePtr();
	static UBytePtr cps1_obj = new UBytePtr();
	static UBytePtr cps1_buffered_obj = new UBytePtr();
	static UBytePtr cps1_palette = new UBytePtr();
	static UBytePtr cps1_other = new UBytePtr();
	static UBytePtr cps1_old_palette = new UBytePtr();
	
	/* Working variables */
	static int cps1_last_sprite_offset;     /* Offset of the last sprite */
	static int[] cps1_layer_enabled = new int[4];       /* Layer enabled [Y/N] */
	static int cps1_stars_enabled;          /* Layer enabled [Y/N] */

	static int scroll1x, scroll1y, scroll2x, scroll2y, scroll3x, scroll3y;
	static int stars1x, stars1y, stars2x, stars2y;
	static UBytePtr cps1_scroll2_old = new UBytePtr();
	static osd_bitmap cps1_scroll2_bitmap;
	
	
	/* Output ports */
	static int CPS1_OBJ_BASE        = 0x00;    /* Base address of objects */
	static int CPS1_SCROLL1_BASE    = 0x02;    /* Base address of scroll 1 */
        static int CPS1_SCROLL2_BASE    = 0x04;    /* Base address of scroll 2 */
        static int CPS1_SCROLL3_BASE    = 0x06;    /* Base address of scroll 3 */
        static int CPS1_OTHER_BASE      = 0x08;    /* Base address of other video */
        static int CPS1_PALETTE_BASE    = 0x0a;    /* Base address of palette */
        static int CPS1_SCROLL1_SCROLLX = 0x0c;    /* Scroll 1 X */
        static int CPS1_SCROLL1_SCROLLY = 0x0e;    /* Scroll 1 Y */
        static int CPS1_SCROLL2_SCROLLX = 0x10;    /* Scroll 2 X */
        static int CPS1_SCROLL2_SCROLLY = 0x12;    /* Scroll 2 Y */
        static int CPS1_SCROLL3_SCROLLX = 0x14;    /* Scroll 3 X */
        static int CPS1_SCROLL3_SCROLLY = 0x16;    /* Scroll 3 Y */
        static int CPS1_STARS1_SCROLLX  = 0x18;    /* Stars 1 X */
        static int CPS1_STARS1_SCROLLY  = 0x1a;    /* Stars 1 Y */
        static int CPS1_STARS2_SCROLLX  = 0x1c;    /* Stars 2 X */
        static int CPS1_STARS2_SCROLLY  = 0x1e;    /* Stars 2 Y */

        static int CPS1_ROWSCROLL_OFFS  = 0x20;    /* base of row scroll offsets in other RAM */

        static int CPS1_SCROLL2_WIDTH   = 0x40;
        static int CPS1_SCROLL2_HEIGHT  = 0x40;
	
	
	/*
	CPS1 VIDEO RENDERER
	
	*/
	static int[] cps1_gfx;		 /* Converted GFX memory */
        static int[] cps1_char_pen_usage;	/* pen usage array */
        static int[] cps1_tile16_pen_usage;      /* pen usage array */
        static int[] cps1_tile32_pen_usage;      /* pen usage array */
	static int cps1_max_char;	       /* Maximum number of 8x8 chars */
	static int cps1_max_tile16;	     /* Maximum number of 16x16 tiles */
	static int cps1_max_tile32;	     /* Maximum number of 32x32 tiles */
        static UBytePtr stars1_rom, stars2_rom;
	
	/* first 0x4000 of gfx ROM are used, but 0x0000-0x1fff is == 0x2000-0x3fff */
	static int stars_rom_size = 0x2000;
	
	
	static int cps1_gfx_start()
	{
		int dwval;
		int size=memory_region_length(REGION_GFX1);
		UBytePtr data = memory_region(REGION_GFX1);
		int i,j,nchar,penusage,gfxsize;
	
		gfxsize=size/4;
	
		/* Set up maximum values */
		cps1_max_char  =(gfxsize/2)/8;
		cps1_max_tile16=(gfxsize/4)/8;
		cps1_max_tile32=(gfxsize/16)/8;
	
		cps1_gfx=new int[gfxsize];
		if (cps1_gfx==null)
		{
			return -1;
		}
	
		cps1_char_pen_usage=new int[cps1_max_char];
		if (cps1_char_pen_usage==null)
		{
			return -1;
		}
		memset(cps1_char_pen_usage, 0, cps1_max_char);
	
		cps1_tile16_pen_usage=new int[(cps1_max_tile16)];
		if (cps1_tile16_pen_usage==null)
			return -1;
		memset(cps1_tile16_pen_usage, 0, cps1_max_tile16);
	
		cps1_tile32_pen_usage=new int[(cps1_max_tile32)];
		if (cps1_tile32_pen_usage==null)
		{
			return -1;
		}
		memset(cps1_tile32_pen_usage, 0, cps1_max_tile32);
	
		stars1_rom = new UBytePtr(stars_rom_size);
		if (stars1_rom==null) return -1;
		memcpy(new UBytePtr(stars1_rom), new UBytePtr(memory_region(REGION_GFX1), memory_region_length(REGION_GFX1)/4),stars_rom_size);
	
		stars2_rom = new UBytePtr(stars_rom_size);
		if (stars2_rom==null) return -1;
		memcpy(stars2_rom,memory_region(REGION_GFX1),stars_rom_size);
	
		{
			for (i=0; i<gfxsize/2; i++)
			{
				nchar=i/8;  /* 8x8 char number */
			   dwval=0;
			   for (j=0; j<8; j++)
			   {
					int n,mask;
					n=0;
					mask=0x80>>j;
					if ((data.read(size/4)&mask)!=0)	   n|=1;
					if ((data.read(size/4+1)&mask)!=0)	 n|=2;
					if ((data.read(size/2+size/4)&mask)!=0)    n|=4;
					if ((data.read(size/2+size/4+1)&mask)!=0)  n|=8;
					dwval|=n<<(28-j*4);
					penusage=1<<n;
					cps1_char_pen_usage[nchar]|=penusage;
					cps1_tile16_pen_usage[nchar/2]|=penusage;
					cps1_tile32_pen_usage[nchar/8]|=penusage;
			   }
			   cps1_gfx[2*i]=dwval;
			   dwval=0;
			   for (j=0; j<8; j++)
			   {
					int n,mask;
					n=0;
					mask=0x80>>j;
					if ((data.read()&mask)!=0)	  n|=1;
					if ((data.read(1)&mask)!=0)	n|=2;
					if ((data.read(size/2)&mask)!=0)   n|=4;
					if ((data.read(size/2+1)&mask)!=0) n|=8;
					dwval|=n<<(28-j*4);
					penusage=1<<n;
					cps1_char_pen_usage[nchar]|=penusage;
					cps1_tile16_pen_usage[nchar/2]|=penusage;
					cps1_tile32_pen_usage[nchar/8]|=penusage;
			   }
			   cps1_gfx[2*i+1]=dwval;
			   data.inc(2);
			}
		}
		return 0;
	}
	
	static void cps1_gfx_stop()
	{
		cps1_gfx = null;
		cps1_char_pen_usage = null;
		cps1_tile16_pen_usage = null;
		cps1_tile32_pen_usage = null;
		stars1_rom = null;
		stars2_rom = null;
	}
	
	
/*TODO*///	void cps1_draw_gfx(
/*TODO*///		struct osd_bitmap *dest,const struct GfxElement *gfx,
/*TODO*///		unsigned int code,
/*TODO*///		int color,
/*TODO*///		int flipx,int flipy,
/*TODO*///		int sx,int sy,
/*TODO*///		int tpens,
/*TODO*///		int *pusage,
/*TODO*///		const int size,
/*TODO*///		const int max,
/*TODO*///		const int delta,
/*TODO*///		const int srcdelta)
/*TODO*///	{
/*TODO*///		#define DATATYPE unsigned char
/*TODO*///		#define IF_NOT_TRANSPARENT(n,x,y) if (tpens & (0x01 << n))
/*TODO*///		if (dest == priority_bitmap)
/*TODO*///		{
/*TODO*///		#define PALDATA(n) 1
/*TODO*///		#define SELF_INCLUDE
/*TODO*///			#undef SELF_INCLUDE
/*TODO*///		#undef PALDATA
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///		#define PALDATA(n) paldata[n]
/*TODO*///		#define SELF_INCLUDE
/*TODO*///			#undef SELF_INCLUDE
/*TODO*///		#undef PALDATA
/*TODO*///		}
/*TODO*///	
/*TODO*///		#undef DATATYPE
/*TODO*///		#undef IF_NOT_TRANSPARENT
/*TODO*///	}
/*TODO*///	
/*TODO*///	void cps1_draw_gfx16(
/*TODO*///		struct osd_bitmap *dest,const struct GfxElement *gfx,
/*TODO*///		unsigned int code,
/*TODO*///		int color,
/*TODO*///		int flipx,int flipy,
/*TODO*///		int sx,int sy,
/*TODO*///		int tpens,
/*TODO*///		int *pusage,
/*TODO*///		const int size,
/*TODO*///		const int max,
/*TODO*///		const int delta,
/*TODO*///		const int srcdelta)
/*TODO*///	{
/*TODO*///		#define DATATYPE unsigned short
/*TODO*///		#define IF_NOT_TRANSPARENT(n,x,y) if (tpens & (0x01 << n))
/*TODO*///		if (dest == priority_bitmap)
/*TODO*///		{
/*TODO*///		#define PALDATA(n) 1
/*TODO*///		#define SELF_INCLUDE
/*TODO*///			#undef SELF_INCLUDE
/*TODO*///		#undef PALDATA
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///		#define PALDATA(n) paldata[n]
/*TODO*///		#define SELF_INCLUDE
/*TODO*///			#undef SELF_INCLUDE
/*TODO*///		#undef PALDATA
/*TODO*///		}
/*TODO*///		#undef DATATYPE
/*TODO*///		#undef IF_NOT_TRANSPARENT
/*TODO*///	}
/*TODO*///	
/*TODO*///	void cps1_draw_gfx_pri(
/*TODO*///		struct osd_bitmap *dest,const struct GfxElement *gfx,
/*TODO*///		unsigned int code,
/*TODO*///		int color,
/*TODO*///		int flipx,int flipy,
/*TODO*///		int sx,int sy,
/*TODO*///		int tpens,
/*TODO*///		int *pusage,
/*TODO*///		const int size,
/*TODO*///		const int max,
/*TODO*///		const int delta,
/*TODO*///		const int srcdelta,
/*TODO*///		struct osd_bitmap *pribm)
/*TODO*///	{
/*TODO*///		#define DATATYPE unsigned char
/*TODO*///		#define IF_NOT_TRANSPARENT(n,x,y) if ((tpens & (0x01 << n)) && pribm.line[y][x] == 0)
/*TODO*///		#define PALDATA(n) paldata[n]
/*TODO*///		#define SELF_INCLUDE
/*TODO*///			#undef SELF_INCLUDE
/*TODO*///		#undef DATATYPE
/*TODO*///		#undef IF_NOT_TRANSPARENT
/*TODO*///		#undef PALDATA
/*TODO*///	}
/*TODO*///	
/*TODO*///	static void cps1_draw_gfx16_pri(
/*TODO*///		struct osd_bitmap *dest,const struct GfxElement *gfx,
/*TODO*///		unsigned int code,
/*TODO*///		int color,
/*TODO*///		int flipx,int flipy,
/*TODO*///		int sx,int sy,
/*TODO*///		int tpens,
/*TODO*///		int *pusage,
/*TODO*///		const int size,
/*TODO*///		const int max,
/*TODO*///		const int delta,
/*TODO*///		const int srcdelta,
/*TODO*///		struct osd_bitmap *pribm)
/*TODO*///	{
/*TODO*///		#define DATATYPE unsigned short
/*TODO*///		#define IF_NOT_TRANSPARENT(n,x,y) if ((tpens & (0x01 << n)) && pribm.line[y][x] == 0)
/*TODO*///		#define PALDATA(n) paldata[n]
/*TODO*///		#define SELF_INCLUDE
/*TODO*///			#undef SELF_INCLUDE
/*TODO*///		#undef DATATYPE
/*TODO*///		#undef IF_NOT_TRANSPARENT
/*TODO*///		#undef PALDATA
/*TODO*///	}
/*TODO*///	
/*TODO*///	/*
/*TODO*///	
/*TODO*///	This is an optimized version that doesn't take into account transparency
/*TODO*///	
/*TODO*///	Draws complete tiles without checking transparency. Used for scroll 2 low
/*TODO*///	priority rendering.
/*TODO*///	
/*TODO*///	*/
/*TODO*///	void cps1_draw_gfx_opaque(
/*TODO*///		struct osd_bitmap *dest,const struct GfxElement *gfx,
/*TODO*///		unsigned int code,
/*TODO*///		int color,
/*TODO*///		int flipx,int flipy,
/*TODO*///		int sx,int sy,
/*TODO*///		int tpens,
/*TODO*///		int *pusage,
/*TODO*///		const int size,
/*TODO*///		const int max,
/*TODO*///		const int delta,
/*TODO*///		const int srcdelta)
/*TODO*///	{
/*TODO*///		#define DATATYPE unsigned char
/*TODO*///		#define IF_NOT_TRANSPARENT(n,x,y)
/*TODO*///		#define PALDATA(n) paldata[n]
/*TODO*///		#define SELF_INCLUDE
/*TODO*///			#undef SELF_INCLUDE
/*TODO*///		#undef DATATYPE
/*TODO*///		#undef IF_NOT_TRANSPARENT
/*TODO*///		#undef PALDATA
/*TODO*///	}
/*TODO*///	
/*TODO*///	void cps1_draw_gfx_opaque16(
/*TODO*///		struct osd_bitmap *dest,const struct GfxElement *gfx,
/*TODO*///		unsigned int code,
/*TODO*///		int color,
/*TODO*///		int flipx,int flipy,
/*TODO*///		int sx,int sy,
/*TODO*///		int tpens,
/*TODO*///		int *pusage,
/*TODO*///		const int size,
/*TODO*///		const int max,
/*TODO*///		const int delta,
/*TODO*///		const int srcdelta)
/*TODO*///	{
/*TODO*///		#define DATATYPE unsigned short
/*TODO*///		#define IF_NOT_TRANSPARENT(n,x,y)
/*TODO*///		#define PALDATA(n) paldata[n]
/*TODO*///		#define SELF_INCLUDE
/*TODO*///			#undef SELF_INCLUDE
/*TODO*///		#undef DATATYPE
/*TODO*///		#undef IF_NOT_TRANSPARENT
/*TODO*///		#undef PALDATA
/*TODO*///	}
	
	
	
	static void cps1_draw_scroll1(
		osd_bitmap dest,
		int code, int color,
		int flipx, int flipy,int sx, int sy, int tpens)
	{
		if (dest.depth==16)
		{
			cps1_draw_gfx16(dest,
				Machine.gfx[1],
				code,color,flipx,flipy,sx,sy,
				tpens,cps1_char_pen_usage,8, cps1_max_char, 16, 1);
		}
		else
		{
			cps1_draw_gfx(dest,
				Machine.gfx[1],
				code,color,flipx,flipy,sx,sy,
				tpens,cps1_char_pen_usage,8, cps1_max_char, 16, 1);
		}
	}
	
	
	static void cps1_draw_tile16(osd_bitmap dest,
		GfxElement gfx,
		int code, int color,
		int flipx, int flipy,int sx, int sy, int tpens)
	{
		if (dest.depth==16)
		{
			cps1_draw_gfx16(dest,
				gfx,
				code,color,flipx,flipy,sx,sy,
				tpens,cps1_tile16_pen_usage,16, cps1_max_tile16, 16*2,0);
		}
		else
		{
			cps1_draw_gfx(dest,
				gfx,
				code,color,flipx,flipy,sx,sy,
				tpens,cps1_tile16_pen_usage,16, cps1_max_tile16, 16*2,0);
		}
	}
	
	static void cps1_draw_tile16_pri(osd_bitmap dest,
		GfxElement gfx,
		int code, int color,
		int flipx, int flipy,int sx, int sy, int tpens)
	{
		if (dest.depth==16)
		{
			cps1_draw_gfx16_pri(dest,
				gfx,
				code,color,flipx,flipy,sx,sy,
				tpens,cps1_tile16_pen_usage,16, cps1_max_tile16, 16*2,0, priority_bitmap);
		}
		else
		{
			cps1_draw_gfx_pri(dest,
				gfx,
				code,color,flipx,flipy,sx,sy,
				tpens,cps1_tile16_pen_usage,16, cps1_max_tile16, 16*2,0, priority_bitmap);
		}
	}
	
	static void cps1_draw_tile32(osd_bitmap dest,
		GfxElement gfx,
		int code, int color,
		int flipx, int flipy,int sx, int sy, int tpens)
	{
		if (dest.depth==16)
		{
			cps1_draw_gfx16(dest,
				gfx,
				code,color,flipx,flipy,sx,sy,
				tpens,cps1_tile32_pen_usage,32, cps1_max_tile32, 16*2*4,0);
		}
		else
		{
			cps1_draw_gfx(dest,
				gfx,
				code,color,flipx,flipy,sx,sy,
				tpens,cps1_tile32_pen_usage,32, cps1_max_tile32, 16*2*4,0);
		}
	}
	
	
	static void cps1_draw_blank16(osd_bitmap dest, int sx, int sy )
	{
		int i,j;
	
		if ((Machine.orientation & ORIENTATION_SWAP_XY) != 0)
		{
			int temp;
			temp=sx;
			sx=sy;
			sy=dest.height-temp-16;
		}
	
		if (cps1_flip_screen != 0)
		{
			/* Handle flipped screen */
			sx=dest.width-sx-16;
			sy=dest.height-sy-16;
		}
	
		if (dest.depth==16)
		{
			for (i=15; i>=0; i--)
			{
				UShortArray bm=new UShortArray(dest.line[sy+i], sx);
				for (j=15; j>=0; j--)
				{
					bm.write(0, palette_transparent_pen);
					bm.inc();
				}
			}
		}
		else
		{
			for (i=15; i>=0; i--)
			{
				UBytePtr bm=new UBytePtr(dest.line[sy+i], sx);
				for (j=15; j>=0; j--)
				{
					bm.write(0, palette_transparent_pen);
					bm.inc();
				}
			}
		}
	}
	
	
	
	static void cps1_draw_tile16_bmp(osd_bitmap dest,
		GfxElement gfx,
		int code, int color,
		int flipx, int flipy,int sx, int sy)
	{
		if (dest.depth==16)
		{
			cps1_draw_gfx_opaque16(dest,
				gfx,
				code,color,flipx,flipy,sx,sy,
				-1,cps1_tile16_pen_usage,16, cps1_max_tile16, 16*2,0);
		}
		else
		{
			cps1_draw_gfx_opaque(dest,
				gfx,
				code,color,flipx,flipy,sx,sy,
				-1,cps1_tile16_pen_usage,16, cps1_max_tile16, 16*2,0);
		}
	}
	
	
	
	
	static int[] cps1_transparency_scroll = new int[4];
	
	
	
/*TODO*///	#if CPS1_DUMP_VIDEO
/*TODO*///	void cps1_dump_video(void)
/*TODO*///	{
/*TODO*///		FILE *fp;
/*TODO*///		fp=fopen("SCROLL1.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(cps1_scroll1, cps1_scroll1_size, 1, fp);
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///		fp=fopen("SCROLL2.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(cps1_scroll2, cps1_scroll2_size, 1, fp);
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///		fp=fopen("SCROLL3.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(cps1_scroll3, cps1_scroll3_size, 1, fp);
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///		fp=fopen("OBJ.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(cps1_obj, cps1_obj_size, 1, fp);
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///	
/*TODO*///		fp=fopen("OTHER.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(cps1_other, cps1_other_size, 1, fp);
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///	
/*TODO*///		fp=fopen("PALETTE.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(cps1_palette, cps1_palette_size, 1, fp);
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///	
/*TODO*///		fp=fopen("OUTPUT.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(cps1_output, cps1_output_size, 1, fp);
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///		fp=fopen("VIDEO.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(cps1_gfxram, cps1_gfxram_size, 1, fp);
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///	
/*TODO*///	}
/*TODO*///	#endif
	
	
	static void cps1_get_video_base()
	{
		int layercontrol;
	
		/* Re-calculate the VIDEO RAM base */
		cps1_scroll1=cps1_base(CPS1_SCROLL1_BASE,cps1_scroll1_size);
		cps1_scroll2=cps1_base(CPS1_SCROLL2_BASE,cps1_scroll2_size);
		cps1_scroll3=cps1_base(CPS1_SCROLL3_BASE,cps1_scroll3_size);
		cps1_obj=cps1_base(CPS1_OBJ_BASE,cps1_obj_size);
		cps1_palette=cps1_base(CPS1_PALETTE_BASE,cps1_palette_size);
		cps1_other=cps1_base(CPS1_OTHER_BASE,cps1_other_size);
	
		/* Get scroll values */
		scroll1x=cps1_port(CPS1_SCROLL1_SCROLLX);
		scroll1y=cps1_port(CPS1_SCROLL1_SCROLLY);
		scroll2x=cps1_port(CPS1_SCROLL2_SCROLLX);
		scroll2y=cps1_port(CPS1_SCROLL2_SCROLLY);
		scroll3x=cps1_port(CPS1_SCROLL3_SCROLLX);
		scroll3y=cps1_port(CPS1_SCROLL3_SCROLLY);
		stars1x =cps1_port(CPS1_STARS1_SCROLLX);
		stars1y =cps1_port(CPS1_STARS1_SCROLLY);
		stars2x =cps1_port(CPS1_STARS2_SCROLLX);
		stars2y =cps1_port(CPS1_STARS2_SCROLLY);
	
		/* Get transparency registers */
		if (cps1_game_config.priority1 != 0)
		{
			cps1_transparency_scroll[0]=cps1_port(cps1_game_config.priority0);
			cps1_transparency_scroll[1]=cps1_port(cps1_game_config.priority1);
			cps1_transparency_scroll[2]=cps1_port(cps1_game_config.priority2);
			cps1_transparency_scroll[3]=cps1_port(cps1_game_config.priority3);
		}
	
		/* Get layer enable bits */
		layercontrol=cps1_port(cps1_game_config.layer_control);
		cps1_layer_enabled[0]=1;
		cps1_layer_enabled[1]=layercontrol & cps1_game_config.scrl1_enable_mask;
		cps1_layer_enabled[2]=layercontrol & cps1_game_config.scrl2_enable_mask;
		cps1_layer_enabled[3]=layercontrol & cps1_game_config.scrl3_enable_mask;
		cps1_stars_enabled   =layercontrol & cps1_game_config.stars_enable_mask;
	
	
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	{
/*TODO*///		int enablemask;
/*TODO*///	
/*TODO*///	if (keyboard_pressed(KEYCODE_Z))
/*TODO*///	{
/*TODO*///		if (keyboard_pressed(KEYCODE_Q)) cps1_layer_enabled[3]=0;
/*TODO*///		if (keyboard_pressed(KEYCODE_W)) cps1_layer_enabled[2]=0;
/*TODO*///		if (keyboard_pressed(KEYCODE_E)) cps1_layer_enabled[1]=0;
/*TODO*///		if (keyboard_pressed(KEYCODE_R)) cps1_layer_enabled[0]=0;
/*TODO*///		if (keyboard_pressed(KEYCODE_T))
/*TODO*///		{
/*TODO*///			usrintf_showmessage("%d %d %d %d layer %02x",
/*TODO*///				(layercontrol>>0x06)&03,
/*TODO*///				(layercontrol>>0x08)&03,
/*TODO*///				(layercontrol>>0x0a)&03,
/*TODO*///				(layercontrol>>0x0c)&03,
/*TODO*///				layercontrol&0xc03f
/*TODO*///				);
/*TODO*///		}
/*TODO*///	
/*TODO*///	}
/*TODO*///	
/*TODO*///		enablemask = 0;
/*TODO*///		if (cps1_game_config.scrl1_enable_mask == cps1_game_config.scrl2_enable_mask)
/*TODO*///			enablemask = cps1_game_config.scrl1_enable_mask;
/*TODO*///		if (cps1_game_config.scrl1_enable_mask == cps1_game_config.scrl3_enable_mask)
/*TODO*///			enablemask = cps1_game_config.scrl1_enable_mask;
/*TODO*///		if (cps1_game_config.scrl2_enable_mask == cps1_game_config.scrl3_enable_mask)
/*TODO*///			enablemask = cps1_game_config.scrl2_enable_mask;
/*TODO*///		if (enablemask != 0)
/*TODO*///		{
/*TODO*///			if (((layercontrol & enablemask) && (layercontrol & enablemask) != enablemask))
/*TODO*///				usrintf_showmessage("layer %02x",layercontrol&0xc03f);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	#endif
	
	{
		int enablemask;
		enablemask = cps1_game_config.scrl1_enable_mask | cps1_game_config.scrl2_enable_mask
				| cps1_game_config.scrl3_enable_mask | cps1_game_config.stars_enable_mask;
		if (((layercontrol & ~enablemask) & 0xc03e) != 0)
			usrintf_showmessage("layer %02x contact MAMEDEV",layercontrol&0xc03f);
	}
	
	}
	
	
	/***************************************************************************
	
	  Start the video hardware emulation.
	
	***************************************************************************/
	
	public static VhStartPtr cps1_vh_start = new VhStartPtr() { public int handler() 
	{
		int i;
	
		cps1_init_machine.handler();
	
		if (cps1_gfx_start() != 0)
		{
			return -1;
		}
	
		cps1_scroll2_bitmap=bitmap_alloc(CPS1_SCROLL2_WIDTH*16,CPS1_SCROLL2_HEIGHT*16);
		if (cps1_scroll2_bitmap==null)
		{
			return -1;
		}
		cps1_scroll2_old=new UBytePtr(cps1_scroll2_size);
		if (cps1_scroll2_old==null)
		{
			return -1;
		}
		memset(cps1_scroll2_old, 0xff, cps1_scroll2_size);
	
	
		cps1_old_palette=new UBytePtr(cps1_palette_size);
		if (cps1_old_palette==null)
		{
			return -1;
		}
		memset(cps1_old_palette, 0x00, cps1_palette_size);
		for (i = 0;i < cps1_palette_entries*16;i++)
		{
			palette_change_color(i,0,0,0);
		}
	
		cps1_buffered_obj = new UBytePtr(cps1_obj_size);
		if (cps1_buffered_obj==null)
		{
			return -1;
		}
		memset(cps1_buffered_obj, 0x00, cps1_obj_size);
	
		memset(cps1_gfxram, 0, cps1_gfxram_size[0]);   /* Clear GFX RAM */
		memset(cps1_output, 0, cps1_output_size[0]);   /* Clear output ports */
	
		/* Put in some defaults */
		cps1_output.WRITE_WORD(0x00, 0x9200);
		cps1_output.WRITE_WORD(0x02, 0x9000);
		cps1_output.WRITE_WORD(0x04, 0x9040);
		cps1_output.WRITE_WORD(0x06, 0x9080);
		cps1_output.WRITE_WORD(0x08, 0x9100);
		cps1_output.WRITE_WORD(0x0a, 0x90c0);
	
		if (cps1_game_config==null)
		{
			logerror("cps1_game_config hasn't been set up yet");
			return -1;
		}
	
	
		/* Set up old base */
		cps1_get_video_base();   /* Calculate base pointers */
		cps1_get_video_base();   /* Calculate old base pointers */
	
	
		for (i=0; i<4; i++)
		{
			cps1_transparency_scroll[i]=0x0000;
		}
		return 0;
	} };
	
	/***************************************************************************
	
	  Stop the video hardware emulation.
	
	***************************************************************************/
	public static VhStopPtr cps1_vh_stop = new VhStopPtr() { public void handler() 
	{
		if (cps1_old_palette != null)
			cps1_old_palette=null;
		if (cps1_scroll2_bitmap != null)
			bitmap_free(cps1_scroll2_bitmap);
		if (cps1_scroll2_old != null)
			cps1_scroll2_old=null;
		if (cps1_buffered_obj != null)
			cps1_buffered_obj=null;
		cps1_gfx_stop();
	} };
	
	/***************************************************************************
	
	  Build palette from palette RAM
	
	  12 bit RGB with a 4 bit brightness value.
	
	***************************************************************************/
	
	static void cps1_build_palette()
	{
		int offset;
	
		for (offset = 0; offset < cps1_palette_entries*16; offset++)
		{
			int palette = cps1_palette.READ_WORD (offset * 2);
	
			if (palette != cps1_old_palette.READ_WORD (offset * 2) )
			{
			   int red, green, blue, bright;
	
			   bright= (palette>>12);
			   if (bright != 0) bright += 2;
	
			   red   = ((palette>>8)&0x0f) * bright;
			   green = ((palette>>4)&0x0f) * bright;
			   blue  = (palette&0x0f) * bright;
	
			   palette_change_color (offset, red, green, blue);
			   cps1_old_palette.WRITE_WORD(offset * 2, palette);
			}
		}
	}
	
	/***************************************************************************
	
	  Scroll 1 (8x8)
	
	  Attribute word layout:
	  0x0001	colour
	  0x0002	colour
	  0x0004	colour
	  0x0008	colour
	  0x0010	colour
	  0x0020	X Flip
	  0x0040	Y Flip
	  0x0080
	  0x0100
	  0x0200
	  0x0400
	  0x0800
	  0x1000
	  0x2000
	  0x4000
	  0x8000
	
	
	***************************************************************************/
	
	static void cps1_palette_scroll1(char[] base, int offset) {
        int x, y, offs, offsx;

        int scrlxrough = (scroll1x >> 3) + 8;
        int scrlyrough = (scroll1y >> 3);
        int basecode = cps1_game_config.bank_scroll1 * 0x08000;

        for (x = 0; x < 0x36; x++) {
            offsx = (scrlxrough + x) * 0x80;
            offsx &= 0x1fff;

            for (y = 0; y < 0x20; y++) {
                int code, colour, offsy;
                int n = scrlyrough + y;
                offsy = ((n & 0x1f) * 4 | ((n & 0x20) * 0x100)) & 0x3fff;
                offs = offsy + offsx;
                offs &= 0x3fff;
                code = basecode + cps1_scroll1.READ_WORD(offs);
                colour = cps1_scroll1.READ_WORD(offs + 2);
                if (code < cps1_max_char) {
                    base[offset + (colour & 0x1f)]
                            |= cps1_char_pen_usage[code] & 0x7fff;
                }
            }
        }
    }
	
	static void cps1_render_scroll1(osd_bitmap bitmap,int priority)
	{
		int x,y, offs, offsx, sx, sy, ytop;
	
		int scrlxrough=(scroll1x>>3)+4;
		int scrlyrough=(scroll1y>>3);
		int base=cps1_game_config.bank_scroll1*0x08000;
		int spacechar=cps1_game_config.space_scroll1;
	
	
		sx=-(scroll1x&0x07);
		ytop=-(scroll1y&0x07)+32;
	
		for (x=0; x<0x35; x++)
		{
			 sy=ytop;
			 offsx=(scrlxrough+x)*0x80;
			 offsx&=0x1fff;
	
			 for (y=0; y<0x20; y++)
			 {
				int code, offsy, colour;
				int n=scrlyrough+y;
				offsy=( (n&0x1f)*4 | ((n&0x20)*0x100)) & 0x3fff;
				offs=offsy+offsx;
				offs &= 0x3fff;
	
				code  =cps1_scroll1.READ_WORD(offs);
				colour=cps1_scroll1.READ_WORD(offs+2);
	
				if (code != 0x20 && code != spacechar)
				{
					int transp;
	
					/* 0x0020 appears to never be drawn */
					if (priority != 0)
					{
						transp=cps1_transparency_scroll[(colour & 0x0180)>>7];
						cps1_draw_scroll1(priority_bitmap,
								code+base,
								colour&0x1f,
								colour&0x20,
								colour&0x40,
								sx,sy,transp);
					}
					else
					{
						transp = 0x7fff;
						cps1_draw_scroll1(bitmap,
								code+base,
								colour&0x1f,
								colour&0x20,
								colour&0x40,
								sx,sy,transp);
					}
				 }
				 sy+=8;
			 }
			 sx+=8;
		}
	}
	
	
	
	/***************************************************************************
	
									Sprites
									=======
	
	  Sprites are represented by a number of 8 byte values
	
	  xx xx yy yy nn nn aa aa
	
	  where xxxx = x position
			yyyy = y position
			nnnn = tile number
			aaaa = attribute word
						0x0001	colour
						0x0002	colour
						0x0004	colour
						0x0008	colour
						0x0010	colour
						0x0020	X Flip
						0x0040	Y Flip
						0x0080	unknown
						0x0100	X block size (in sprites)
						0x0200	X block size
						0x0400	X block size
						0x0800	X block size
						0x1000	Y block size (in sprites)
						0x2000	Y block size
						0x4000	Y block size
						0x8000	Y block size
	
	  The end of the table (may) be marked by an attribute value of 0xff00.
	
	***************************************************************************/
	
	static void cps1_find_last_sprite()    /* Find the offset of last sprite */
	{
		int offset=6;
		/* Locate the end of table marker */
		while (offset < cps1_obj_size)
		{
			int colour=cps1_buffered_obj.READ_WORD(offset);
			if (colour == 0xff00)
			{
				/* Marker found. This is the last sprite. */
				cps1_last_sprite_offset=offset-6-8;
				return;
			}
			offset+=8;
		}
		/* Sprites must use full sprite RAM */
		cps1_last_sprite_offset=cps1_obj_size-8;
	}
	
	/* Find used colours */
	static void cps1_palette_sprites(char[] base, int offset) {
        int i;

        for (i = cps1_last_sprite_offset; i >= 0; i -= 8) {
            int x = cps1_buffered_obj.READ_WORD(i);
            int y = cps1_buffered_obj.READ_WORD(i + 2);
            if (x != 0 && y != 0) {
                int colour = cps1_buffered_obj.READ_WORD(i + 6);
                int col = colour & 0x1f;
                /*unsigned*/ int code = Math.abs(cps1_buffered_obj.READ_WORD(i + 4));//get a non negative value
                if (cps1_game_config.kludge == 7) {
                    code += 0x4000;
                }
                if (cps1_game_config.kludge == 1 && code >= 0x01000) {
                    code += 0x4000;
                }
                if (cps1_game_config.kludge == 2 && code >= 0x02a00) {
                    code += 0x4000;
                }

                if ((colour & 0xff00) != 0) {
                    int nys, nxs;
                    int nx = (colour & 0x0f00) >> 8;
                    int ny = (colour & 0xf000) >> 12;
                    nx++;
                    ny++;

                    if ((colour & 0x40) != 0) /* Y Flip */ /* Y flip */ {
                        if ((colour & 0x20) != 0) {
                            for (nys = 0; nys < ny; nys++) {
                                for (nxs = 0; nxs < nx; nxs++) {
                                    int cod = code + (nx - 1) - nxs + 0x10 * (ny - 1 - nys);
                                    base[col + offset]
                                            |= cps1_tile16_pen_usage[cod % cps1_max_tile16];
                                }
                            }
                        } else {
                            for (nys = 0; nys < ny; nys++) {
                                for (nxs = 0; nxs < nx; nxs++) {
                                    int cod = code + nxs + 0x10 * (ny - 1 - nys);
                                    base[col + offset]
                                            |= cps1_tile16_pen_usage[cod % cps1_max_tile16];
                                }
                            }
                        }
                    } else {
                        if ((colour & 0x20) != 0) {
                            for (nys = 0; nys < ny; nys++) {
                                for (nxs = 0; nxs < nx; nxs++) {
                                    int cod = code + (nx - 1) - nxs + 0x10 * nys;
                                    base[col + offset]
                                            |= cps1_tile16_pen_usage[cod % cps1_max_tile16];
                                }
                            }
                        } else {
                            for (nys = 0; nys < ny; nys++) {
                                for (nxs = 0; nxs < nx; nxs++) {
                                    int cod = code + nxs + 0x10 * nys;
                                    base[col + offset]
                                            |= cps1_tile16_pen_usage[cod % cps1_max_tile16];
                                }
                            }
                        }
                    }
                    base[col + offset] &= 0x7fff;
                } else {
                    base[col + offset]
                            |= cps1_tile16_pen_usage[code % cps1_max_tile16] & 0x7fff;
                }
            }
        }
    }

	
	

	static void cps1_render_sprites(osd_bitmap bitmap)
	{
		int mask=0x7fff;
		int i;
	//mish
		/* Draw the sprites */
		for (i=cps1_last_sprite_offset; i>=0; i-=8)
		{
			int x=cps1_buffered_obj.READ_WORD(i);
			int y=cps1_buffered_obj.READ_WORD(i+2);
			if (x!=0 && y!=0 )
			{
				int code=cps1_buffered_obj.READ_WORD(i+4);
				int colour=cps1_buffered_obj.READ_WORD(i+6);
				int col=colour&0x1f;
	
				y &= 0x1ff;
				if (y > 450) y -= 0x200;
	
				/* in cawing, skyscrapers parts on level 2 have all the top bits of the */
				/* x coordinate set. Does this have a special meaning? */
				x &= 0x1ff;
				if (x > 450) x -= 0x200;
	
				x-=0x20;
				y+=0x20;
	
				if (cps1_game_config.kludge == 7)
				{
					code += 0x4000;
				}
				if (cps1_game_config.kludge == 1 && code >= 0x01000)
				{
					code += 0x4000;
				}
				if (cps1_game_config.kludge == 2 && code >= 0x02a00)
				{
					code += 0x4000;
				}
	
				if ((colour & 0xff00) != 0)
				{
					/* handle blocked sprites */
					int nx=(colour & 0x0f00) >> 8;
					int ny=(colour & 0xf000) >> 12;
					int nxs,nys,sx,sy;
					nx++;
					ny++;
	
					if ((colour & 0x40) != 0)
					{
						/* Y flip */
						if ((colour & 0x20) != 0)
						{
							for (nys=0; nys<ny; nys++)
							{
								for (nxs=0; nxs<nx; nxs++)
								{
									sx = x+nxs*16;
									sy = y+nys*16;
									if (sx > 450) sx -= 0x200;
									if (sy > 450) sy -= 0x200;
	
									cps1_draw_tile16_pri(bitmap,Machine.gfx[0],
										code+(nx-1)-nxs+0x10*(ny-1-nys),
										col&0x1f,
										1,1,
										sx,sy,mask);
								}
							}
						}
						else
						{
							for (nys=0; nys<ny; nys++)
							{
								for (nxs=0; nxs<nx; nxs++)
								{
									sx = x+nxs*16;
									sy = y+nys*16;
									if (sx > 450) sx -= 0x200;
									if (sy > 450) sy -= 0x200;
	
									cps1_draw_tile16_pri(bitmap,Machine.gfx[0],
										code+nxs+0x10*(ny-1-nys),
										col&0x1f,
										0,1,
										sx,sy,mask );
								}
							}
						}
					}
					else
					{
						if ((colour & 0x20) != 0)
						{
							for (nys=0; nys<ny; nys++)
							{
								for (nxs=0; nxs<nx; nxs++)
								{
									sx = x+nxs*16;
									sy = y+nys*16;
									if (sx > 450) sx -= 0x200;
									if (sy > 450) sy -= 0x200;
	
									cps1_draw_tile16_pri(bitmap,Machine.gfx[0],
										code+(nx-1)-nxs+0x10*nys,
										col&0x1f,
										1,0,
										sx,sy,mask
										);
								}
							}
						}
						else
						{
							for (nys=0; nys<ny; nys++)
							{
								for (nxs=0; nxs<nx; nxs++)
								{
									sx = x+nxs*16;
									sy = y+nys*16;
									if (sx > 450) sx -= 0x200;
									if (sy > 450) sy -= 0x200;
	
									cps1_draw_tile16_pri(bitmap,Machine.gfx[0],
										code+nxs+0x10*nys,
										col&0x1f,
										0,0,
										sx,sy, mask);
								}
							}
						}
					}
				}
				else
				{
					/* Simple case... 1 sprite */
					cps1_draw_tile16_pri(bitmap,Machine.gfx[0],
						   code,
						   col&0x1f,
						   colour&0x20,colour&0x40,
						   x,y,mask);
				}
			}
		}
	}
	
	
	
	/***************************************************************************
	
	  Scroll 2 (16x16 layer)
	
	  Attribute word layout:
	  0x0001	colour
	  0x0002	colour
	  0x0004	colour
	  0x0008	colour
	  0x0010	colour
	  0x0020	X Flip
	  0x0040	Y Flip
	  0x0080	??? Priority
	  0x0100	??? Priority
	  0x0200
	  0x0400
	  0x0800
	  0x1000
	  0x2000
	  0x4000
	  0x8000
	
	
	***************************************************************************/
	
	static void cps1_palette_scroll2(char[] base, int offset) {
        int offs, code, colour;
        int basecode = cps1_game_config.bank_scroll2 * 0x04000;

        for (offs = cps1_scroll2_size - 4; offs >= 0; offs -= 4) {
            code = basecode + cps1_scroll2.READ_WORD(offs);
            colour = cps1_scroll2.READ_WORD(offs + 2) & 0x1f;
            if (code < cps1_max_tile16) {
                base[colour + offset] |= cps1_tile16_pen_usage[code];
            }
        }
    }
	
	static void cps1_render_scroll2_bitmap(osd_bitmap bitmap)
	{
		int sx, sy;
		int ny=(scroll2y>>4);	  /* Rough Y */
		int base=cps1_game_config.bank_scroll2*0x04000;
		int startcode=cps1_game_config.start_scroll2;
		int endcode=cps1_game_config.end_scroll2;
		int kludge=cps1_game_config.kludge;
	
		for (sx=CPS1_SCROLL2_WIDTH-1; sx>=0; sx--)
		{
			int n=ny;
			for (sy=0x09*2-1; sy>=0; sy--)
			{
				long newvalue;
				int offsy, offsx, offs, colour, code;
	
				n&=0x3f;
				offsy  = ((n&0x0f)*4 | ((n&0x30)*0x100))&0x3fff;
				offsx=(sx*0x040)&0xfff;
				offs=offsy+offsx;
	
				colour=cps1_scroll2.READ_WORD(offs+2);
	
				newvalue=cps1_scroll2.read(offs);
				if ( newvalue != cps1_scroll2_old.read(offs) )
				{
					cps1_scroll2_old.write(offs, (int) newvalue);
					code=cps1_scroll2.READ_WORD(offs);
					if ( code >= startcode && code <= endcode
						/*
						MERCS has an gap in the scroll 2 layout
						(bad tiles at start of level 2)*/
						&&	!(kludge == 4 && (code >= 0x1e00 && code < 0x5400))
						)
					{
						code += base;
						cps1_draw_tile16_bmp(bitmap,
							Machine.gfx[2],
							code,
							colour&0x1f,
							colour&0x20,colour&0x40,
							16*sx, 16*n);
					}
					else
					{
						cps1_draw_blank16(bitmap, 16*sx, 16*n);
					}
					//cps1_print_debug_tile_info(bitmap, 16*sx, 16*n, colour,1);
				}
				n++;
			}
		}
	}
	
	
	static void cps1_render_scroll2_high(osd_bitmap bitmap)
	{
/*TODO*///	#ifdef LAYER_DEBUG
/*TODO*///		static int s=0;
/*TODO*///	#endif
		int sx, sy;
		int nxoffset=(scroll2x&0x0f)+32;    /* Smooth X */
		int nyoffset=(scroll2y&0x0f);    /* Smooth Y */
		int nx=(scroll2x>>4);	  /* Rough X */
		int ny=(scroll2y>>4)-4;	/* Rough Y */
		int base=cps1_game_config.bank_scroll2*0x04000;
		int startcode=cps1_game_config.start_scroll2;
		int endcode=cps1_game_config.end_scroll2;
		int kludge=cps1_game_config.kludge;
	
		for (sx=0; sx<0x32/2+4; sx++)
		{
			for (sy=0; sy<0x09*2; sy++)
			{
				int offsy, offsx, offs, colour, code, transp;
				int n;
				n=ny+sy+2;
				offsy  = ((n&0x0f)*4 | ((n&0x30)*0x100))&0x3fff;
				offsx=((nx+sx)*0x040)&0xfff;
				offs=offsy+offsx;
				offs &= 0x3fff;
	
				code=cps1_scroll2.READ_WORD(offs);
	
				if ( code >= startcode && code <= endcode
					/*
					MERCS has an gap in the scroll 2 layout
					(bad tiles at start of level 2)*/
					&&	!(kludge == 4 && (code >= 0x1e00 && code < 0x5400))
					)
				{
					colour=cps1_scroll2.READ_WORD(offs+2);
	
					transp=cps1_transparency_scroll[(colour & 0x0180)>>7];
	
					cps1_draw_tile16(priority_bitmap,
								Machine.gfx[2],
								code+base,
								colour&0x1f,
								colour&0x20,colour&0x40,
								16*sx-nxoffset,
								16*sy-nyoffset,
								transp);
				}
			}
		}
	}
	
	static void cps1_render_scroll2_low(osd_bitmap bitmap)
	{
		int scrly=-(scroll2y-0x20);
		int scrlx=-(scroll2x+0x40-0x20);
	
		if (cps1_flip_screen != 0)
		{
			scrly=(CPS1_SCROLL2_HEIGHT*16)-scrly;
		}
	
		cps1_render_scroll2_bitmap(cps1_scroll2_bitmap);
	
		copyscrollbitmap(bitmap,cps1_scroll2_bitmap,1,new int[]{scrlx},1,new int[]{scrly},Machine.visible_area,TRANSPARENCY_PEN,palette_transparent_pen);
	}
	
	
	static void cps1_render_scroll2_distort(osd_bitmap bitmap)
	{
		int scrly=-scroll2y;
		int i;
                int[] scrollx=new int[1024];
		int otheroffs;
	
	/*
		Games known to use row scrolling:
	
		SF2
		Mega Twins (underwater, cave)
		Carrier Air Wing (hazy background at beginning of mission 8)
		Magic Sword (fire on floor 3; screen distort after continue)
		Varth (title screen)
		Bionic Commando (end game sequence)
	*/
	
		if (cps1_flip_screen != 0)
			scrly=(CPS1_SCROLL2_HEIGHT*16)-scrly;
	
		cps1_render_scroll2_bitmap(cps1_scroll2_bitmap);
	
		otheroffs = cps1_port(CPS1_ROWSCROLL_OFFS);
	
		for (i = 0;i < 256;i++)
			scrollx[(i - scrly) & 0x3ff] = -(scroll2x+0x40-0x20) - cps1_other.READ_WORD((2*(i + otheroffs)) & 0x7ff);
	
		scrly+=0x20;
	
		copyscrollbitmap(bitmap,cps1_scroll2_bitmap,1024,scrollx,1,new int[]{scrly},Machine.visible_area,TRANSPARENCY_PEN,palette_transparent_pen);
	}
	
	
	/***************************************************************************
	
	  Scroll 3 (32x32 layer)
	
	  Attribute word layout:
	  0x0001	colour
	  0x0002	colour
	  0x0004	colour
	  0x0008	colour
	  0x0010	colour
	  0x0020	X Flip
	  0x0040	Y Flip
	  0x0080
	  0x0100
	  0x0200
	  0x0400
	  0x0800
	  0x1000
	  0x2000
	  0x4000
	  0x8000
	
	***************************************************************************/
	
	static void cps1_palette_scroll3(char[] base, int offset) {
        int sx, sy;
        int nx = (scroll3x >> 5) + 1;
        int ny = (scroll3y >> 5) - 1;
        int basecode = cps1_game_config.bank_scroll3 * 0x01000;

        for (sx = 0; sx < 0x32 / 4 + 2; sx++) {
            for (sy = 0; sy < 0x20 / 4 + 2; sy++) {
                int offsy, offsx, offs, colour, code;
                int n;
                n = ny + sy;
                offsy = ((n & 0x07) * 4 | ((n & 0xf8) * 0x0100)) & 0x3fff;
                offsx = ((nx + sx) * 0x020) & 0x7ff;
                offs = offsy + offsx;
                offs &= 0x3fff;
                code = basecode + cps1_scroll3.READ_WORD(offs);
                if (cps1_game_config.kludge == 2 && code >= 0x01500) {
                    code -= 0x1000;
                }
                colour = cps1_scroll3.READ_WORD(offs + 2);
                if (code < cps1_max_tile32) {
                    base[offset + (colour & 0x1f)] |= cps1_tile32_pen_usage[code];
                }
            }
        }
    }

	
	
	static void cps1_render_scroll3(osd_bitmap bitmap, int priority)
	{
		int sx,sy;
		int nxoffset=scroll3x&0x1f;
		int nyoffset=scroll3y&0x1f;
		int nx=(scroll3x>>5)+1;
		int ny=(scroll3y>>5)-1;
		int basecode=cps1_game_config.bank_scroll3*0x01000;
		int startcode=cps1_game_config.start_scroll3;
		int endcode=cps1_game_config.end_scroll3;
	
		for (sx=1; sx<0x32/4+2; sx++)
		{
			for (sy=1; sy<0x20/4+2; sy++)
			{
				int offsy, offsx, offs, colour, code;
				int n;
				n=ny+sy;
				offsy  = ((n&0x07)*4 | ((n&0xf8)*0x0100))&0x3fff;
				offsx=((nx+sx)*0x020)&0x7ff;
				offs=offsy+offsx;
				offs &= 0x3fff;
				code=cps1_scroll3.READ_WORD(offs);
				if (code >= startcode && code <= endcode)
				{
					int transp;
	
					code+=basecode;
					if (cps1_game_config.kludge == 2 && code >= 0x01500)
					{
						code -= 0x1000;
					}
					colour=cps1_scroll3.READ_WORD(offs+2);
					if (priority != 0)
					{
						transp=cps1_transparency_scroll[(colour & 0x0180)>>7];
						cps1_draw_tile32(priority_bitmap,Machine.gfx[3],
								code,
								colour&0x1f,
								colour&0x20,colour&0x40,
								32*sx-nxoffset,32*sy-nyoffset,
								transp);
					}
					else
					{
						transp = 0x7fff;
						cps1_draw_tile32(bitmap,Machine.gfx[3],
								code,
								colour&0x1f,
								colour&0x20,colour&0x40,
								32*sx-nxoffset,32*sy-nyoffset,
								transp);
					}
				}
			}
		}
	}
	
	
	/* the following is COMPLETELY WRONG. It's there just to draw something */
	static void cps1_render_stars(osd_bitmap bitmap)
	{
		if (cps1_stars_enabled != 0)
		{
			int offs;
	
	
			for (offs = 0;offs < stars_rom_size;offs += 2)
			{
				int col = stars2_rom.read(offs);
				if (col != 0x0f)
				{
					int sx = ((offs/2) / 256) * 32;
					int sy = ((offs/2) % 256);
					sx = (sx - stars2x - 64 + (col & 0x1f)) & 0x1ff;
					sy = (sy - stars2y) & 0xff;
					if (cps1_flip_screen != 0)
					{
						sx = 383 - sx;
						sy = 255 - sy;
					}
	
					col = ((col & 0xe0) >> 1) + (cpu_getcurrentframe()/16 & 0x0f);
	
					if (sx+32 <= Machine.visible_area.max_x &&
							sy+32 <= Machine.visible_area.max_y)
						plot_pixel.handler(bitmap,sx+32,sy+32,Machine.pens[0xa00+col]);
				}
			}
	
			for (offs = 0;offs < stars_rom_size;offs += 2)
			{
				int col = stars1_rom.read(offs);
				if (col != 0x0f)
				{
					int sx = ((offs/2) / 256) * 32;
					int sy = ((offs/2) % 256);
					sx = (sx - stars1x - 64+ (col & 0x1f)) & 0x1ff;
					sy = (sy - stars1y) & 0xff;
					if (cps1_flip_screen != 0)
					{
						sx = 383 - sx;
						sy = 255 - sy;
					}
	
					col = ((col & 0xe0) >> 1) + (cpu_getcurrentframe()/16 & 0x0f);
	
					if (sx+32 <= Machine.visible_area.max_x &&
							sy+32 <= Machine.visible_area.max_y)
						plot_pixel.handler(bitmap,sx+32,sy+32,Machine.pens[0x800+col]);
				}
			}
		}
	}
	
	
	static void cps1_render_layer(osd_bitmap bitmap, int layer, int distort)
	{
		if (cps1_layer_enabled[layer] != 0)
		{
			switch (layer)
			{
				case 0:
					cps1_render_sprites(bitmap);
					break;
				case 1:
					cps1_render_scroll1(bitmap, 0);
					break;
				case 2:
					if (distort != 0)
						cps1_render_scroll2_distort(bitmap);
					else
						cps1_render_scroll2_low(bitmap);
					break;
				case 3:
					cps1_render_scroll3(bitmap, 0);
					break;
			}
		}
	}
	
	static void cps1_render_high_layer(osd_bitmap bitmap, int layer)
	{
		if (cps1_layer_enabled[layer] != 0)
		{
			switch (layer)
			{
				case 0:
					/* there are no high priority sprites */
					break;
				case 1:
					cps1_render_scroll1(bitmap, 1);
					break;
				case 2:
					cps1_render_scroll2_high(bitmap);
					break;
				case 3:
					cps1_render_scroll3(bitmap, 1);
					break;
			}
		}
	}
	
	
	/***************************************************************************
	
		Refresh screen
	
	***************************************************************************/
	
	public static VhUpdatePtr cps1_vh_screenrefresh = new VhUpdatePtr() { public void handler(osd_bitmap bitmap,int full_refresh) 
	{
		char[] palette_usage=new char[cps1_palette_entries];
		int layercontrol,l0,l1,l2,l3;
		int i,offset;
		int distort_scroll2=0;
		int videocontrol=cps1_port(0x22);
		int old_flip;
	
	
		old_flip=cps1_flip_screen;
		cps1_flip_screen=videocontrol&0x8000;
		if (old_flip != cps1_flip_screen)
		{
			 /* Mark all of scroll 2 as dirty */
			memset(cps1_scroll2_old, 0xff, cps1_scroll2_size);
		}
	
		layercontrol = cps1_output.READ_WORD(cps1_game_config.layer_control);
	
		distort_scroll2 = videocontrol & 0x01;
	
		/* Get video memory base registers */
		cps1_get_video_base();
	
		/* Find the offset of the last sprite in the sprite table */
		cps1_find_last_sprite();
	
		/* Build palette */
		cps1_build_palette();
	
		/* Compute the used portion of the palette */
		memset (palette_usage, 0, palette_usage.length);
		cps1_palette_sprites (palette_usage, cps1_obj_palette);
		if (cps1_layer_enabled[1] != 0)
			cps1_palette_scroll1 (palette_usage, cps1_scroll1_palette);
		if (cps1_layer_enabled[2] != 0)
			cps1_palette_scroll2 (palette_usage, cps1_scroll2_palette);
		else
			memset(cps1_scroll2_old, 0xff, cps1_scroll2_size);
		if (cps1_layer_enabled[3] != 0)
			cps1_palette_scroll3 (palette_usage, cps1_scroll3_palette);
	
		for (i = offset = 0; i < cps1_palette_entries; i++)
		{
			int usage = palette_usage[i];
			if (usage != 0)
			{
				int j;
				for (j = 0; j < 15; j++)
				{
					if ((usage & (1 << j)) != 0)
						palette_used_colors.write(offset++, PALETTE_COLOR_USED);
					else
						palette_used_colors.write(offset++, PALETTE_COLOR_UNUSED);
				}
				palette_used_colors.write(offset++, PALETTE_COLOR_TRANSPARENT);
			}
			else
			{
				memset (new UBytePtr(palette_used_colors, offset), PALETTE_COLOR_UNUSED, 16);
				offset += 16;
			}
		}
	
		if (cps1_stars_enabled != 0)
		{
			for (i = 0;i < 128;i++)
			{
				palette_used_colors.write(0x800+i, PALETTE_COLOR_VISIBLE);
				palette_used_colors.write(0xa00+i, PALETTE_COLOR_VISIBLE);
			}
		}
	
		if (palette_recalc () != null)
		{
			 /* Mark all of scroll 2 as dirty */
			memset(cps1_scroll2_old, 0xff, cps1_scroll2_size);
		}
	
		/* Blank screen */
	//	fillbitmap(bitmap,palette_transparent_pen,&Machine.visible_area);
	// TODO: the draw functions don't clip correctly at the sides of the screen, so
	// for now let's clear the whole bitmap otherwise ctrl-f11 would show wrong counts
		fillbitmap(bitmap,palette_transparent_pen,null);
	
		cps1_render_stars(bitmap);
	
		/* Draw layers (0 = sprites, 1-3 = tilemaps) */
		l0 = (layercontrol >> 0x06) & 03;
		l1 = (layercontrol >> 0x08) & 03;
		l2 = (layercontrol >> 0x0a) & 03;
		l3 = (layercontrol >> 0x0c) & 03;
	
		fillbitmap(priority_bitmap,0,null);
	
		cps1_render_layer(bitmap,l0,distort_scroll2);
		if (l1 == 0) cps1_render_high_layer(bitmap,l0);	/* prepare mask for sprites */
		cps1_render_layer(bitmap,l1,distort_scroll2);
		if (l2 == 0) cps1_render_high_layer(bitmap,l1);	/* prepare mask for sprites */
		cps1_render_layer(bitmap,l2,distort_scroll2);
		if (l3 == 0) cps1_render_high_layer(bitmap,l2);	/* prepare mask for sprites */
		cps1_render_layer(bitmap,l3,distort_scroll2);
	
/*TODO*///	#if CPS1_DUMP_VIDEO
/*TODO*///		if (keyboard_pressed(KEYCODE_F))
/*TODO*///		{
/*TODO*///			cps1_dump_video();
/*TODO*///		}
/*TODO*///	#endif
	} };
	
	public static VhEofCallbackPtr cps1_eof_callback = new VhEofCallbackPtr() { public void handler() 
	{
		/* Get video memory base registers */
		cps1_get_video_base();
	
		/* Mish: 181099: Buffer sprites for next frame - the hardware must do
			this at the end of vblank */
		memcpy(cps1_buffered_obj,cps1_obj,cps1_obj_size);
	} };
	
	
	
	
	
/*TODO*///	#else	/* SELF_INCLUDE */
/*TODO*///	/* this is #included several times generate 8-bit and 16-bit versions */
/*TODO*///	
/*TODO*///	{
/*TODO*///		int i, j;
/*TODO*///		UINT32 dwval;
/*TODO*///		UINT32 *src;
/*TODO*///		const unsigned short *paldata;
/*TODO*///		UINT32 n;
/*TODO*///		DATATYPE *bm;
/*TODO*///	
/*TODO*///		if ( code > max || (tpens & pusage[code])==0)
/*TODO*///		{
/*TODO*///			/* Do not draw blank object */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///	
/*TODO*///		if (Machine.orientation & ORIENTATION_SWAP_XY)
/*TODO*///		{
/*TODO*///			int temp;
/*TODO*///			temp=sx;
/*TODO*///			sx=sy;
/*TODO*///			sy=dest.height-temp-size;
/*TODO*///			temp=flipx;
/*TODO*///			flipx=flipy;
/*TODO*///			flipy=!temp;
/*TODO*///		}
/*TODO*///	
/*TODO*///		if (cps1_flip_screen != 0)
/*TODO*///		{
/*TODO*///			/* Handle flipped screen */
/*TODO*///			flipx=!flipx;
/*TODO*///			flipy=!flipy;
/*TODO*///			sx=dest.width-sx-size;
/*TODO*///			sy=dest.height-sy-size;
/*TODO*///		}
/*TODO*///	
/*TODO*///		if (sx<0 || sx > dest.width-size || sy<0 || sy>dest.height-size )
/*TODO*///		{
/*TODO*///			/* Don't draw clipped tiles (for sprites) */
/*TODO*///			return;
/*TODO*///		}
/*TODO*///	
/*TODO*///		paldata=&gfx.colortable[gfx.color_granularity * color];
/*TODO*///		src = cps1_gfx+code*delta;
/*TODO*///	
/*TODO*///		if (Machine.orientation & ORIENTATION_SWAP_XY)
/*TODO*///		{
/*TODO*///			int bmdelta,dir;
/*TODO*///	
/*TODO*///			bmdelta = (dest.line[1] - dest.line[0]);
/*TODO*///			dir = 1;
/*TODO*///			if (flipy != 0)
/*TODO*///			{
/*TODO*///				bmdelta = -bmdelta;
/*TODO*///				dir = -1;
/*TODO*///				sy += size-1;
/*TODO*///			}
/*TODO*///			if (flipx != 0) sx+=size-1;
/*TODO*///			for (i=0; i<size; i++)
/*TODO*///			{
/*TODO*///				int ny=sy;
/*TODO*///				for (j=0; j<size/8; j++)
/*TODO*///				{
/*TODO*///					dwval=*src;
/*TODO*///					n=(dwval>>28)&0x0f;
/*TODO*///					bm = (DATATYPE *)dest.line[ny]+sx;
/*TODO*///					IF_NOT_TRANSPARENT(n,sx,ny) bm[0]=PALDATA(n);
/*TODO*///					n=(dwval>>24)&0x0f;
/*TODO*///					bm = (DATATYPE *)(((UBytePtr )bm) + bmdelta);
/*TODO*///					IF_NOT_TRANSPARENT(n,sx,ny+dir) bm[0]=PALDATA(n);
/*TODO*///					n=(dwval>>20)&0x0f;
/*TODO*///					bm = (DATATYPE *)(((UBytePtr )bm) + bmdelta);
/*TODO*///					IF_NOT_TRANSPARENT(n,sx,ny+2*dir) bm[0]=PALDATA(n);
/*TODO*///					n=(dwval>>16)&0x0f;
/*TODO*///					bm = (DATATYPE *)(((UBytePtr )bm) + bmdelta);
/*TODO*///					IF_NOT_TRANSPARENT(n,sx,ny+3*dir) bm[0]=PALDATA(n);
/*TODO*///					n=(dwval>>12)&0x0f;
/*TODO*///					bm = (DATATYPE *)(((UBytePtr )bm) + bmdelta);
/*TODO*///					IF_NOT_TRANSPARENT(n,sx,ny+4*dir) bm[0]=PALDATA(n);
/*TODO*///					n=(dwval>>8)&0x0f;
/*TODO*///					bm = (DATATYPE *)(((UBytePtr )bm) + bmdelta);
/*TODO*///					IF_NOT_TRANSPARENT(n,sx,ny+5*dir) bm[0]=PALDATA(n);
/*TODO*///					n=(dwval>>4)&0x0f;
/*TODO*///					bm = (DATATYPE *)(((UBytePtr )bm) + bmdelta);
/*TODO*///					IF_NOT_TRANSPARENT(n,sx,ny+6*dir) bm[0]=PALDATA(n);
/*TODO*///					n=dwval&0x0f;
/*TODO*///					bm = (DATATYPE *)(((UBytePtr )bm) + bmdelta);
/*TODO*///					IF_NOT_TRANSPARENT(n,sx,ny+7*dir) bm[0]=PALDATA(n);
/*TODO*///					if (flipy != 0) ny-=8;
/*TODO*///					else ny+=8;
/*TODO*///					src++;
/*TODO*///				}
/*TODO*///				if (flipx != 0) sx--;
/*TODO*///				else sx++;
/*TODO*///				src+=srcdelta;
/*TODO*///			}
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///			if (flipy != 0) sy+=size-1;
/*TODO*///			if (flipx != 0)
/*TODO*///			{
/*TODO*///				sx+=size;
/*TODO*///				for (i=0; i<size; i++)
/*TODO*///				{
/*TODO*///					int x,y;
/*TODO*///					x=sx;
/*TODO*///					if (flipy != 0) y=sy-i;
/*TODO*///					else y=sy+i;
/*TODO*///					bm=(DATATYPE *)dest.line[y]+sx;
/*TODO*///					for (j=0; j<size/8; j++)
/*TODO*///					{
/*TODO*///						dwval=*src;
/*TODO*///						n=(dwval>>28)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x-1,y) bm[-1]=PALDATA(n);
/*TODO*///						n=(dwval>>24)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x-2,y) bm[-2]=PALDATA(n);
/*TODO*///						n=(dwval>>20)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x-3,y) bm[-3]=PALDATA(n);
/*TODO*///						n=(dwval>>16)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x-4,y) bm[-4]=PALDATA(n);
/*TODO*///						n=(dwval>>12)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x-5,y) bm[-5]=PALDATA(n);
/*TODO*///						n=(dwval>>8)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x-6,y) bm[-6]=PALDATA(n);
/*TODO*///						n=(dwval>>4)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x-7,y) bm[-7]=PALDATA(n);
/*TODO*///						n=dwval&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x-8,y) bm[-8]=PALDATA(n);
/*TODO*///						bm-=8;
/*TODO*///						x-=8;
/*TODO*///						src++;
/*TODO*///					}
/*TODO*///					src+=srcdelta;
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				for (i=0; i<size; i++)
/*TODO*///				{
/*TODO*///					int x,y;
/*TODO*///					x=sx;
/*TODO*///					if (flipy != 0) y=sy-i;
/*TODO*///					else y=sy+i;
/*TODO*///					bm=(DATATYPE *)dest.line[y]+sx;
/*TODO*///					for (j=0; j<size/8; j++)
/*TODO*///					{
/*TODO*///						dwval=*src;
/*TODO*///						n=(dwval>>28)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x+0,y) bm[0]=PALDATA(n);
/*TODO*///						n=(dwval>>24)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x+1,y) bm[1]=PALDATA(n);
/*TODO*///						n=(dwval>>20)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x+2,y) bm[2]=PALDATA(n);
/*TODO*///						n=(dwval>>16)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x+3,y) bm[3]=PALDATA(n);
/*TODO*///						n=(dwval>>12)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x+4,y) bm[4]=PALDATA(n);
/*TODO*///						n=(dwval>>8)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x+5,y) bm[5]=PALDATA(n);
/*TODO*///						n=(dwval>>4)&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x+6,y) bm[6]=PALDATA(n);
/*TODO*///						n=dwval&0x0f;
/*TODO*///						IF_NOT_TRANSPARENT(n,x+7,y) bm[7]=PALDATA(n);
/*TODO*///						bm+=8;
/*TODO*///						x+=8;
/*TODO*///						src++;
/*TODO*///					}
/*TODO*///					src+=srcdelta;
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///	#endif	/* SELF_INCLUDE */
}
