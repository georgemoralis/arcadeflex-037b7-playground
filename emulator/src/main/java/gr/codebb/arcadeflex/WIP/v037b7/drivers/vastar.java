/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package gr.codebb.arcadeflex.WIP.v037b7.drivers;

import static gr.codebb.arcadeflex.WIP.v037b7.vidhrdw.vastar.*;
import static arcadeflex.common.ptrLib.*;
import static arcadeflex.v037b7.mame.cpuintrfH.ASSERT_LINE;
import static arcadeflex.v037b7.mame.cpuintrfH.CLEAR_LINE;
import static arcadeflex.v037b7.mame.driverH.*;
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.common.flip_screen_w;
import static arcadeflex.v037b7.generic.funcPtr.*;
import static arcadeflex.v037b7.mame.commonH.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
import static arcadeflex.v037b7.mame.memoryH.*;
import static arcadeflex.v037b7.mame.sndintrfH.*;
import static arcadeflex.v037b7.sound.ay8910.AY8910_control_port_0_w;
import static arcadeflex.v037b7.sound.ay8910.AY8910_read_port_0_r;
import static arcadeflex.v037b7.sound.ay8910.AY8910_write_port_0_w;
import arcadeflex.v037b7.sound.ay8910H.AY8910interface;
import static arcadeflex.v037b7.vidhrdw.generic.*;


public class vastar {

  static UBytePtr vastar_sharedram = new UBytePtr();

  public static InitMachinePtr vastar_init_machine =
      new InitMachinePtr() {
        public void handler() {
          /* we must start with the second CPU halted */
          cpu_set_reset_line(1, ASSERT_LINE);
        }
      };

  public static WriteHandlerPtr vastar_hold_cpu2_w =
      new WriteHandlerPtr() {
        public void handler(int offset, int data) {
          /* I'm not sure that this works exactly like this */
          if ((data & 1) != 0) cpu_set_reset_line(1, CLEAR_LINE);
          else cpu_set_reset_line(1, ASSERT_LINE);
        }
      };

  public static ReadHandlerPtr vastar_sharedram_r =
      new ReadHandlerPtr() {
        public int handler(int offset) {
          return vastar_sharedram.read(offset);
        }
      };

  public static WriteHandlerPtr vastar_sharedram_w =
      new WriteHandlerPtr() {
        public void handler(int offset, int data) {
          vastar_sharedram.write(offset, data);
        }
      };

  static MemoryReadAddress readmem[] = {
    new MemoryReadAddress(0x0000, 0x7fff, MRA_ROM),
    new MemoryReadAddress(0x8000, 0x8fff, vastar_bg2videoram_r),
    new MemoryReadAddress(0x9000, 0x9fff, vastar_bg1videoram_r),
    new MemoryReadAddress(0xa000, 0xafff, vastar_bg2videoram_r), /* mirror address */
    new MemoryReadAddress(0xb000, 0xbfff, vastar_bg1videoram_r), /* mirror address */
    new MemoryReadAddress(0xc400, 0xcfff, MRA_RAM),
    new MemoryReadAddress(0xe000, 0xe000, watchdog_reset_r),
    new MemoryReadAddress(0xf000, 0xf0ff, vastar_sharedram_r),
    new MemoryReadAddress(0xf100, 0xf7ff, MRA_RAM),
    new MemoryReadAddress(-1) /* end of table */
  };

  static MemoryWriteAddress writemem[] = {
    new MemoryWriteAddress(0x0000, 0x7fff, MWA_ROM),
    new MemoryWriteAddress(0x8000, 0x8fff, vastar_bg2videoram_w, vastar_bg2videoram),
    new MemoryWriteAddress(0x9000, 0x9fff, vastar_bg1videoram_w, vastar_bg1videoram),
    new MemoryWriteAddress(0xa000, 0xafff, vastar_bg2videoram_w), /* mirror address */
    new MemoryWriteAddress(0xb000, 0xbfff, vastar_bg1videoram_w), /* mirror address */
    new MemoryWriteAddress(
        0xc000, 0xc000, MWA_RAM, vastar_sprite_priority), /* sprite/BG priority */
    new MemoryWriteAddress(0xc7c0, 0xc7df, vastar_bg1_scroll_w, vastar_bg1_scroll),
    new MemoryWriteAddress(0xc7e0, 0xc7ff, vastar_bg2_scroll_w, vastar_bg2_scroll),
    new MemoryWriteAddress(0xc400, 0xcfff, vastar_fgvideoram_w, vastar_fgvideoram),
    new MemoryWriteAddress(0xe000, 0xe000, watchdog_reset_w),
    new MemoryWriteAddress(0xf000, 0xf0ff, vastar_sharedram_w, vastar_sharedram),
    new MemoryWriteAddress(0xf100, 0xf7ff, MWA_RAM),

    /* in hidden portions of video RAM: */
    new MemoryWriteAddress(
        0xc400, 0xc43f, MWA_RAM, spriteram, spriteram_size), /* actually c410-c41f and c430-c43f */
    new MemoryWriteAddress(
        0xc800, 0xc83f, MWA_RAM, spriteram_2), /* actually c810-c81f and c830-c83f */
    new MemoryWriteAddress(
        0xcc00, 0xcc3f, MWA_RAM, spriteram_3), /* actually cc10-cc1f and cc30-cc3f */
    new MemoryWriteAddress(-1) /* end of table */
  };

  static IOWritePort writeport[] = {
    new IOWritePort(0x00, 0x00, interrupt_enable_w),
    new IOWritePort(0x01, 0x01, flip_screen_w),
    new IOWritePort(0x02, 0x02, vastar_hold_cpu2_w),
    new IOWritePort(-1) /* end of table */
  };

  static MemoryReadAddress cpu2_readmem[] = {
    new MemoryReadAddress(0x0000, 0x1fff, MRA_ROM),
    new MemoryReadAddress(0x4000, 0x40ff, vastar_sharedram_r),
    new MemoryReadAddress(0x8000, 0x8000, input_port_1_r),
    new MemoryReadAddress(0x8040, 0x8040, input_port_0_r),
    new MemoryReadAddress(0x8080, 0x8080, input_port_2_r),
    new MemoryReadAddress(-1) /* end of table */
  };

  static MemoryWriteAddress cpu2_writemem[] = {
    new MemoryWriteAddress(0x0000, 0x1fff, MWA_ROM),
    new MemoryWriteAddress(0x4000, 0x40ff, vastar_sharedram_w),
    new MemoryWriteAddress(-1) /* end of table */
  };

  static IOReadPort cpu2_readport[] = {
    new IOReadPort(0x02, 0x02, AY8910_read_port_0_r), new IOReadPort(-1) /* end of table */
  };

  static IOWritePort cpu2_writeport[] = {
    new IOWritePort(0x00, 0x00, AY8910_control_port_0_w),
    new IOWritePort(0x01, 0x01, AY8910_write_port_0_w),
    new IOWritePort(-1) /* end of table */
  };

  static InputPortPtr input_ports_vastar =
      new InputPortPtr() {
        public void handler() {
          PORT_START(); /* IN0 */
          PORT_BIT(0x01, IP_ACTIVE_HIGH, IPT_JOYSTICK_LEFT | IPF_8WAY);
          PORT_BIT(0x02, IP_ACTIVE_HIGH, IPT_JOYSTICK_RIGHT | IPF_8WAY);
          PORT_BIT(0x04, IP_ACTIVE_HIGH, IPT_JOYSTICK_UP | IPF_8WAY);
          PORT_BIT(0x08, IP_ACTIVE_HIGH, IPT_JOYSTICK_DOWN | IPF_8WAY);
          PORT_BIT(0x10, IP_ACTIVE_HIGH, IPT_BUTTON1);
          PORT_BIT(0x20, IP_ACTIVE_HIGH, IPT_BUTTON2);
          PORT_BIT(0x40, IP_ACTIVE_HIGH, IPT_UNKNOWN);
          PORT_BIT(0x80, IP_ACTIVE_HIGH, IPT_UNKNOWN);

          PORT_START(); /* IN1 */
          PORT_BIT(0x01, IP_ACTIVE_HIGH, IPT_JOYSTICK_LEFT | IPF_8WAY | IPF_COCKTAIL);
          PORT_BIT(0x02, IP_ACTIVE_HIGH, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_COCKTAIL);
          PORT_BIT(0x04, IP_ACTIVE_HIGH, IPT_JOYSTICK_UP | IPF_8WAY | IPF_COCKTAIL);
          PORT_BIT(0x08, IP_ACTIVE_HIGH, IPT_JOYSTICK_DOWN | IPF_8WAY | IPF_COCKTAIL);
          PORT_BIT(0x10, IP_ACTIVE_HIGH, IPT_BUTTON1 | IPF_COCKTAIL);
          PORT_BIT(0x20, IP_ACTIVE_HIGH, IPT_BUTTON2 | IPF_COCKTAIL);
          PORT_BIT(0x40, IP_ACTIVE_HIGH, IPT_UNKNOWN);
          PORT_BIT(0x80, IP_ACTIVE_HIGH, IPT_UNKNOWN);

          PORT_START();
          PORT_BIT(0x01, IP_ACTIVE_HIGH, IPT_COIN1);
          PORT_BIT(0x02, IP_ACTIVE_HIGH, IPT_COIN2);
          PORT_BIT(0x04, IP_ACTIVE_HIGH, IPT_COIN3);
          PORT_BIT(0x08, IP_ACTIVE_HIGH, IPT_START1);
          PORT_BIT(0x10, IP_ACTIVE_HIGH, IPT_START2);
          PORT_BIT(0x20, IP_ACTIVE_HIGH, IPT_UNKNOWN);
          PORT_BIT(0x40, IP_ACTIVE_HIGH, IPT_UNKNOWN);
          PORT_BIT(0x80, IP_ACTIVE_HIGH, IPT_UNKNOWN);

          PORT_START(); /* DSW0 */
          PORT_DIPNAME(0x03, 0x03, DEF_STR("Lives"));
          PORT_DIPSETTING(0x03, "3");
          PORT_DIPSETTING(0x02, "4");
          PORT_DIPSETTING(0x01, "5");
          PORT_DIPSETTING(0x00, "6");
          PORT_DIPNAME(0x04, 0x04, DEF_STR("Unknown"));
          PORT_DIPSETTING(0x00, DEF_STR("Off"));
          PORT_DIPSETTING(0x04, DEF_STR("On"));
          PORT_DIPNAME(0x08, 0x08, "Show Author Credits");
          PORT_DIPSETTING(0x08, DEF_STR("No"));
          PORT_DIPSETTING(0x00, DEF_STR("Yes"));
          PORT_BITX(
              0x10, 0x10, IPT_DIPSWITCH_NAME | IPF_CHEAT, "Slow Motion", IP_KEY_NONE, IP_JOY_NONE);
          PORT_DIPSETTING(0x10, DEF_STR("Off"));
          PORT_DIPSETTING(0x00, DEF_STR("On"));
          PORT_DIPNAME(0x20, 0x20, DEF_STR("Unknown"));
          PORT_DIPSETTING(0x00, DEF_STR("Off"));
          PORT_DIPSETTING(0x20, DEF_STR("On"));
          PORT_DIPNAME(0x40, 0x00, DEF_STR("Cabinet"));
          PORT_DIPSETTING(0x00, DEF_STR("Upright"));
          PORT_DIPSETTING(0x40, DEF_STR("Cocktail"));
          PORT_DIPNAME(0x80, 0x80, "Freeze");
          PORT_DIPSETTING(0x80, DEF_STR("Off"));
          PORT_DIPSETTING(0x00, DEF_STR("On"));

          PORT_START(); /* DSW1 */
          PORT_DIPNAME(0x07, 0x07, DEF_STR("Coin_A"));
          PORT_DIPSETTING(0x02, DEF_STR("2C_1C"));
          PORT_DIPSETTING(0x01, DEF_STR("3C_2C"));
          PORT_DIPSETTING(0x07, DEF_STR("1C_1C"));
          PORT_DIPSETTING(0x06, DEF_STR("1C_2C"));
          PORT_DIPSETTING(0x05, DEF_STR("1C_3C"));
          PORT_DIPSETTING(0x04, DEF_STR("1C_4C"));
          PORT_DIPSETTING(0x03, DEF_STR("1C_6C"));
          PORT_DIPSETTING(0x00, DEF_STR("Free_Play"));
          PORT_DIPNAME(0x38, 0x38, DEF_STR("Coin_B"));
          PORT_DIPSETTING(0x00, DEF_STR("5C_1C"));
          PORT_DIPSETTING(0x08, DEF_STR("4C_1C"));
          PORT_DIPSETTING(0x10, DEF_STR("3C_1C"));
          PORT_DIPSETTING(0x18, DEF_STR("2C_1C"));
          PORT_DIPSETTING(0x38, DEF_STR("1C_1C"));
          PORT_DIPSETTING(0x20, DEF_STR("3C_4C"));
          PORT_DIPSETTING(0x30, DEF_STR("1C_2C"));
          PORT_DIPSETTING(0x28, DEF_STR("1C_3C"));
          PORT_DIPNAME(0x40, 0x40, DEF_STR("Unknown"));
          PORT_DIPSETTING(0x00, DEF_STR("Off"));
          PORT_DIPSETTING(0x40, DEF_STR("On"));
          PORT_DIPNAME(0x80, 0x80, DEF_STR("Unknown"));
          PORT_DIPSETTING(0x00, DEF_STR("Off"));
          PORT_DIPSETTING(0x80, DEF_STR("On"));
          INPUT_PORTS_END();
        }
      };

  static GfxLayout charlayout =
      new GfxLayout(
          8,
          8,
          RGN_FRAC(1, 1),
          2,
          new int[] {0, 4},
          new int[] {0, 1, 2, 3, 8 * 8 + 0, 8 * 8 + 1, 8 * 8 + 2, 8 * 8 + 3},
          new int[] {0 * 8, 1 * 8, 2 * 8, 3 * 8, 4 * 8, 5 * 8, 6 * 8, 7 * 8},
          16 * 8);

  static GfxLayout spritelayout =
      new GfxLayout(
          16,
          16,
          RGN_FRAC(1, 1),
          2,
          new int[] {0, 4},
          new int[] {
            0,
            1,
            2,
            3,
            8 * 8 + 0,
            8 * 8 + 1,
            8 * 8 + 2,
            8 * 8 + 3,
            16 * 8 + 0,
            16 * 8 + 1,
            16 * 8 + 2,
            16 * 8 + 3,
            24 * 8 + 0,
            24 * 8 + 1,
            24 * 8 + 2,
            24 * 8 + 3
          },
          new int[] {
            0 * 8, 1 * 8, 2 * 8, 3 * 8, 4 * 8, 5 * 8, 6 * 8, 7 * 8, 32 * 8, 33 * 8, 34 * 8, 35 * 8,
            36 * 8, 37 * 8, 38 * 8, 39 * 8
          },
          64 * 8);

  static GfxLayout spritelayoutdw =
      new GfxLayout(
          16,
          32,
          RGN_FRAC(1, 1),
          2,
          new int[] {0, 4},
          new int[] {
            0,
            1,
            2,
            3,
            8 * 8 + 0,
            8 * 8 + 1,
            8 * 8 + 2,
            8 * 8 + 3,
            16 * 8 + 0,
            16 * 8 + 1,
            16 * 8 + 2,
            16 * 8 + 3,
            24 * 8 + 0,
            24 * 8 + 1,
            24 * 8 + 2,
            24 * 8 + 3
          },
          new int[] {
            0 * 8, 1 * 8, 2 * 8, 3 * 8, 4 * 8, 5 * 8, 6 * 8, 7 * 8, 32 * 8, 33 * 8, 34 * 8, 35 * 8,
            36 * 8, 37 * 8, 38 * 8, 39 * 8, 64 * 8, 65 * 8, 66 * 8, 67 * 8, 68 * 8, 69 * 8, 70 * 8,
            71 * 8, 96 * 8, 97 * 8, 98 * 8, 99 * 8, 100 * 8, 101 * 8, 102 * 8, 103 * 8
          },
          128 * 8);

  static GfxDecodeInfo gfxdecodeinfo[] = {
    new GfxDecodeInfo(REGION_GFX1, 0, charlayout, 0, 64),
    new GfxDecodeInfo(REGION_GFX2, 0, spritelayout, 0, 64),
    new GfxDecodeInfo(REGION_GFX2, 0, spritelayoutdw, 0, 64),
    new GfxDecodeInfo(REGION_GFX3, 0, charlayout, 0, 64),
    new GfxDecodeInfo(REGION_GFX4, 0, charlayout, 0, 64),
    new GfxDecodeInfo(-1) /* end of array */
  };

  static AY8910interface ay8910_interface =
      new AY8910interface(
          1, /* 1 chip */
          1500000, /* 1.5 MHz??????? */
          new int[] {50},
          new ReadHandlerPtr[] {input_port_3_r},
          new ReadHandlerPtr[] {input_port_4_r},
          new WriteHandlerPtr[] {null},
          new WriteHandlerPtr[] {null});

  static MachineDriver machine_driver_vastar =
      new MachineDriver(
          /* basic machine hardware */
          new MachineCPU[] {
            new MachineCPU(
                CPU_Z80,
                3072000, /* 3.072 MHz ???? */
                readmem,
                writemem,
                null,
                writeport,
                nmi_interrupt,
                1),
            new MachineCPU(
                CPU_Z80,
                3072000, /* 3.072 MHz ???? */
                cpu2_readmem,
                cpu2_writemem,
                cpu2_readport,
                cpu2_writeport,
                interrupt,
                4 /* ??? */)
          },
          60,
          DEFAULT_60HZ_VBLANK_DURATION, /* frames per second, vblank duration */
          10, /* 10 CPU slices per frame - seems enough to ensure proper */
          /* synchronization of the CPUs */
          vastar_init_machine,

          /* video hardware */
          32 * 8,
          32 * 8,
          new rectangle(0 * 8, 32 * 8 - 1, 2 * 8, 30 * 8 - 1),
          gfxdecodeinfo,
          256,
          256,
          vastar_vh_convert_color_prom,
          VIDEO_TYPE_RASTER,
          null,
          vastar_vh_start,
          null,
          vastar_vh_screenrefresh,

          /* sound hardware */
          0,
          0,
          0,
          0,
          new MachineSound[] {new MachineSound(SOUND_AY8910, ay8910_interface)});

  /***************************************************************************
   *
   * Game driver(s)
   *
   ***************************************************************************/

  static RomLoadPtr rom_vastar =
      new RomLoadPtr() {
        public void handler() {
          ROM_REGION(0x10000, REGION_CPU1); /* 64k for code */
          ROM_LOAD("e_f4.rom", 0x0000, 0x1000, 0x45fa5075);
          ROM_LOAD("e_k4.rom", 0x1000, 0x1000, 0x84531982);
          ROM_LOAD("e_h4.rom", 0x2000, 0x1000, 0x94a4f778);
          ROM_LOAD("e_l4.rom", 0x3000, 0x1000, 0x40e4d57b);
          ROM_LOAD("e_j4.rom", 0x4000, 0x1000, 0xbd607651);
          ROM_LOAD("e_n4.rom", 0x5000, 0x1000, 0x7a3779a4);
          ROM_LOAD("e_n7.rom", 0x6000, 0x1000, 0x31b6be39);
          ROM_LOAD("e_n5.rom", 0x7000, 0x1000, 0xf63f0e78);

          ROM_REGION(0x10000, REGION_CPU2); /* 64k for the second CPU */
          ROM_LOAD("e_f2.rom", 0x0000, 0x1000, 0x713478d8);
          ROM_LOAD("e_j2.rom", 0x1000, 0x1000, 0xe4535442);

          ROM_REGION(0x2000, REGION_GFX1 | REGIONFLAG_DISPOSE);
          ROM_LOAD("c_c9.rom", 0x0000, 0x2000, 0x34f067b6);

          ROM_REGION(0x4000, REGION_GFX2 | REGIONFLAG_DISPOSE);
          ROM_LOAD("c_f7.rom", 0x0000, 0x2000, 0xedbf3b13);
          ROM_LOAD("c_f9.rom", 0x2000, 0x2000, 0x8f309e22);

          ROM_REGION(0x2000, REGION_GFX3 | REGIONFLAG_DISPOSE);
          ROM_LOAD("c_n4.rom", 0x0000, 0x2000, 0xb5f9c866);

          ROM_REGION(0x2000, REGION_GFX4 | REGIONFLAG_DISPOSE);
          ROM_LOAD("c_s4.rom", 0x0000, 0x2000, 0xc9fbbfc9);

          ROM_REGION(0x0400, REGION_PROMS);
          ROM_LOAD("tbp24s10.6p", 0x0000, 0x0100, 0xa712d73a); /* red component */
          ROM_LOAD("tbp24s10.6s", 0x0100, 0x0100, 0x0a7d48ec); /* green component */
          ROM_LOAD("tbp24s10.6m", 0x0200, 0x0100, 0x4c3db907); /* blue component */
          ROM_LOAD("tbp24s10.8n", 0x0300, 0x0100, 0xb5297a3b); /* ???? */
          ROM_END();
        }
      };

  static RomLoadPtr rom_vastar2 =
      new RomLoadPtr() {
        public void handler() {
          ROM_REGION(0x10000, REGION_CPU1); /* 64k for code */
          ROM_LOAD("3.4f", 0x0000, 0x1000, 0x6741ff9c);
          ROM_LOAD("6.4k", 0x1000, 0x1000, 0x5027619b);
          ROM_LOAD("4.4h", 0x2000, 0x1000, 0xfdaa44e6);
          ROM_LOAD("7.4l", 0x3000, 0x1000, 0x29bef91c);
          ROM_LOAD("5.4j", 0x4000, 0x1000, 0xc17c2458);
          ROM_LOAD("8.4n", 0x5000, 0x1000, 0x8ca25c37);
          ROM_LOAD("10.6n", 0x6000, 0x1000, 0x80df74ba);
          ROM_LOAD("9.5n", 0x7000, 0x1000, 0x239ec84e);

          ROM_REGION(0x10000, REGION_CPU2); /* 64k for the second CPU */
          ROM_LOAD("e_f2.rom", 0x0000, 0x1000, 0x713478d8);
          ROM_LOAD("e_j2.rom", 0x1000, 0x1000, 0xe4535442);

          ROM_REGION(0x2000, REGION_GFX1 | REGIONFLAG_DISPOSE);
          ROM_LOAD("c_c9.rom", 0x0000, 0x2000, 0x34f067b6);

          ROM_REGION(0x4000, REGION_GFX2 | REGIONFLAG_DISPOSE);
          ROM_LOAD("c_f7.rom", 0x0000, 0x2000, 0xedbf3b13);
          ROM_LOAD("c_f9.rom", 0x2000, 0x2000, 0x8f309e22);

          ROM_REGION(0x2000, REGION_GFX3 | REGIONFLAG_DISPOSE);
          ROM_LOAD("c_n4.rom", 0x0000, 0x2000, 0xb5f9c866);

          ROM_REGION(0x2000, REGION_GFX4 | REGIONFLAG_DISPOSE);
          ROM_LOAD("c_s4.rom", 0x0000, 0x2000, 0xc9fbbfc9);

          ROM_REGION(0x0400, REGION_PROMS);
          ROM_LOAD("tbp24s10.6p", 0x0000, 0x0100, 0xa712d73a); /* red component */
          ROM_LOAD("tbp24s10.6s", 0x0100, 0x0100, 0x0a7d48ec); /* green component */
          ROM_LOAD("tbp24s10.6m", 0x0200, 0x0100, 0x4c3db907); /* blue component */
          ROM_LOAD("tbp24s10.8n", 0x0300, 0x0100, 0xb5297a3b); /* ???? */
          ROM_END();
        }
      };

  public static GameDriver driver_vastar =
      new GameDriver(
          "1983",
          "vastar",
          "vastar.java",
          rom_vastar,
          null,
          machine_driver_vastar,
          input_ports_vastar,
          null,
          ROT90,
          "Sesame Japan",
          "Vastar (set 1)");
  public static GameDriver driver_vastar2 =
      new GameDriver(
          "1983",
          "vastar2",
          "vastar.java",
          rom_vastar2,
          driver_vastar,
          machine_driver_vastar,
          input_ports_vastar,
          null,
          ROT90,
          "Sesame Japan",
          "Vastar (set 2)");
}
