/** *************************************************************************
 *
 * TODO:
 * - implement shadows properly
 *
 *
 *
 * Emulated
 * |
 * board #|year    CPU      tiles        sprites  priority palette    other
 * -----|---- ------- ------------- ------------- ------ ------ ----------------
 * Hyper Crash         GX401 1985
 * Twinbee             GX412*1985   68000           GX400
 * Yie Ar Kung Fu      GX407*1985    6809
 * Gradius / Nemesis   GX456*1985   68000           GX400
 * Shao-lins Road      GX477*1985    6809
 * Jail Break          GX507*1986 KONAMI-1          005849
 * Finalizer           GX523*1985 KONAMI-1          005885
 * Konami's Ping Pong  GX555*1985     Z80
 * Iron Horse          GX560*1986    6809           005885
 * Konami GT           GX561*1985   68000           GX400
 * Green Beret         GX577*1985     Z80           005849
 * Galactic Warriors   GX578*1985   68000           GX400
 * Salamander          GX587*1986   68000           GX400
 * WEC Le Mans 24      GX602*1986 2x68000
 * BAW / Black Panther GX604 1987   68000           GX400                    007593
 * Combat School /     GX611*1987    6309           007121(x2)               007327
 * Boot Camp
 * Rock 'n Rage /      GX620*1986    6309 007342        007420               007327
 * Koi no Hotrock
 * Mr Kabuki/Mr Goemon GX621*1986     Z80           005849
 * Jackal              GX631*1986    6809?          005885(x2)
 * Contra / Gryzor     GX633*1987    6809?          007121(x2)               007593
 * Flak Attack         GX669*1987    6309           007121                   007327 007452
 * Devil World / Dark  GX687*1987 2x68000           TWIN16
 * Adventure / Majuu no Oukoku
 * Double Dribble      GX690*1986  3x6809           005885(x2)               007327 007452
 * Kitten Kaboodle /   GX712+1988                   GX400                    007593 051550
 * Nyan Nyan Panic
 * Chequered Flag      GX717*1988  052001               051960 051937(x2)           051316(x2) (zoom/rotation) 051733 (protection)
 * Fast Lane           GX752*1987    6309           007121                          051733 (protection) 007801
 * Hot Chase           GX763*1988 2x68000                                           051316(x3) (zoom/rotation) 007634 007635 007558 007557
 * Rack 'Em Up /       GX765*1987    6309 007342        007420               007327 007324
 * The Hustler
 * Haunted Castle      GX768*1988  052001           007121(x2)               007327
 * Ajax / Typhoon      GX770*1987   6309+ 052109 051962 051960 051937  PROM  007327 051316 (zoom/rotation)
 * 052001
 * Labyrinth Runner    GX771*1987    6309           007121                   007593 051733 (protection) 051550
 * Super Contra        GX775*1988  052001 052109 051962 051960 051937  PROM  007327
 * Battlantis          GX777*1987    6309 007342        007420               007327 007324
 * Vulcan Venture /    GX785*1988 2x68000           TWIN16
 * Gradius 2
 * City Bomber         GX787+1987   68000           GX400                    007593 051550
 * Over Drive          GX789 1990
 * Hyper Crash         GX790 1987
 * Blades of Steel     GX797*1987    6309 007342        007420               007327 051733 (protection)
 * The Main Event      GX799*1988    6309 052109 051962 051960 051937  PROM
 * Missing in Action   GX808*1989   68000 052109 051962 051960 051937  PROM
 * Missing in Action J GX808*1989 2x68000           TWIN16
 * Crime Fighters      GX821*1989  052526 052109 051962 051960 051937  PROM
 * Special Project Y   GX857*1989    6309 052109 051962 051960 051937  PROM         052591 (protection)
 * '88 Games           GX861*1988  052001 052109 051962 051960 051937  PROM         051316 (zoom/rotation)
 * Final Round /       GX870*1988 1x68000           TWIN16?
 * Hard Puncher
 * Thunder Cross       GX873*1988  052001 052109 051962 051960 051937  PROM  007327 052591 (protection)
 * Aliens              GX875*1990  052526 052109 051962 051960 051937  PROM
 * Gang Busters        GX878*1988  052526 052109 051962 051960 051937  PROM
 * Devastators         GX890*1988    6309 052109 051962 051960 051937  PROM         007324 051733 (protection)
 * Bottom of the Ninth GX891*1989    6809 052109 051962 051960 051937  PROM         051316 (zoom/rotation)
 * Cue Brick           GX903*1989 2x68000           TWIN16
 * Punk Shot           GX907*1990   68000 052109 051962 051960 051937 053251
 * Ultraman            GX910*1991   68000 ------ ------ 051960 051937  PROM         051316(x3) (zoom/rotation) 051550
 * Surprise Attack     GX911*1990  053248 052109 051962 053245 053244 053251
 * Lightning Fighters /GX939*1990   68000 052109 051962 053245 053244 053251
 * Trigon
 * Gradius 3           GX945*1989 2x68000 052109 051962 051960 051937  PROM
 * Parodius            GX955*1990  053248 052109 051962 053245 053244 053251
 * TMNT                GX963*1989   68000 052109 051962 051960 051937  PROM
 * Block Hole          GX973*1989  052526 052109 051962 051960 051937  PROM
 * Escape Kids         GX975 1991  053248 052109 051962 053247 053246 053251        053252 - same board as Vendetta
 * Rollergames         GX999*1991  053248 ------ ------ 053245 053244               051316 (zoom/rotation) 053252
 * Bells & Whistles /  GX060*1991   68000 052109 051962 053245 053244 053251        054000 (collision)
 * Detana!! Twin Bee
 * Golfing Greats      GX061*1991   68000 052109 051962 053245 053244 053251        053936 (3D)
 * TMNT 2              GX063*1991   68000 052109 051962 053245 053244 053251        053990
 * Sunset Riders       GX064*1991   68000 052109 051962 053245 053244 053251        054358
 * X-Men               GX065*1992   68000 052109 051962 053247 053246 053251
 * XEXEX               GX067*1991   68000 054157 054156 053247 053246 053251        054338 054539
 * Asterix             GX068+1992   68000 054157 054156 053245 053244 053251        054358
 * G.I. Joe            GX069+1992   68000 054157 054156 053247 053246 053251        054539
 * The Simpsons        GX072*1991  053248 052109 051962 053247 053246 053251
 * Thunder Cross 2     GX073*1991   68000 052109 051962 051960 051937 053251        054000 (collision)
 * Vendetta /          GX081*1991  053248 052109 051962 053247 053246 053251        054000 (collision)
 * Crime Fighters 2
 * Premier Soccer      GX101 1993   68000 052109 051962 053245 053244 053251        053936 (3D)
 * Hexion              GX122+1992     Z80                                           052591 (protection) 053252
 * Entapous /          GX123+1993   68000 054157 054156 055673 053246               053252 054000 055555
 * Gaiapolis
 * Mystic Warrior      GX128 1993
 * Cowboys of Moo Mesa GX151 1993   68000 054157 054156 053247 053246               053252 054338 053990
 * Violent Storm       GX168+1993   68000 054157 054156 055673 053246               054338 054539(x2) 055550 055555
 * Bucky 'O Hare       GX173+1992   68000 054157 054156 053247 053246 053251        054338 054539
 * Potrio              GX174 1992
 * Lethal Enforcers    GX191 1992    6309 054157(x2) 054156 053245 053244(x2)       054000 054539 054906
 * Metamorphic Force   GX224 1993
 * Martial Champion    GX234+1993   68000 054157 054156 055673 053246               053252 054338 054539 055555 053990 054986 054573
 * Run and Gun         GX247+1993   68000               055673 053246               053253(x2)
 * Polygonet CommandersGX305 1993   68020                                           056230?063936?054539?054986?
 *
 *
 * Notes:
 * - Old games use 051961 instead of 052109, it is an earlier version functionally
 * equivalent (maybe 052109 had bugs fixed). The list always shows 052109 because
 * the two are exchangeable and 052109's are found also on original boards whose
 * schematics show a 051961.
 *
 *
 *
 * Status of the ROM tests in the emulated games:
 *
 * Ajax / Typhoon      pass
 * Super Contra        pass
 * The Main Event      pass
 * Missing in Action   pass
 * Crime Fighters      pass
 * Special Project Y   pass
 * Konami 88           pass
 * Thunder Cross       pass
 * Aliens              pass
 * Gang Busters        pass
 * Devastators         pass
 * Bottom of the Ninth pass
 * Punk Shot           pass
 * Surprise Attack     fails D05-6 (052109) because it uses mirror addresses to
 * select banks, and supporting those addresses breaks the
 * normal game ;-(
 * Lightning Fighters  pass
 * Gradius 3           pass
 * Parodius            pass
 * TMNT                pass
 * Block Hole          pass
 * Rollergames         pass
 * Bells & Whistles    pass
 * Golfing Greats      fails B05..B10 (053936)
 * TMNT 2              pass
 * Sunset Riders       pass
 * X-Men               fails 1F (054544)
 * The Simpsons        pass
 * Thunder Cross 2     pass
 * Vendetta            pass
 * Xexex               pass
 *
 *
 * THE FOLLOWING INFORMATION IS PRELIMINARY AND INACCURATE. DON'T RELY ON IT.
 *
 *
 * 007121
 * ------
 * This is an interesting beast. Many games use two of these in pair.
 * It manages sprites and two 32x32 tilemaps. The tilemaps can be joined to form
 * a single 64x32 one, or one of them can be moved to the side of screen, giving
 * a high score display suitable for vertical games.
 * The chip also generates clock and interrupt signals suitable for a 6809.
 * It uses 0x2000 bytes of RAM for the tilemaps and sprites, and an additional
 * 0x100 bytes, maybe for scroll RAM and line buffers. The maximum addressable
 * ROM is 0x80000 bytes (addressed 16 bits at a time).
 * Two 256x4 lookup PROMs are also used to increase the color combinations.
 * All tilemap / sprite priority handling is done internally and the chip exports
 * 7 bits of color code, composed of 2 bits of palette bank, 1 bit indicating tile
 * or sprite, and 4 bits of ROM data remapped through the PROM.
 *
 * inputs:
 * - address lines (A0-A13)
 * - data lines (DB0-DB7)
 * - misc interface stuff
 * - data from the gfx ROMs (RDL0-RDL7, RDU0-RDU7)
 * - data from the tile lookup PROMs (VCD0-VCD3)
 * - data from the sprite lookup PROMs (OCD0-OCD3)
 *
 * outputs:
 * - address lines for tilemap RAM (AX0-AX12)
 * - data lines for tilemap RAM (VO0-VO7)
 * - address lines for the small RAM (FA0-FA7)
 * - data lines for the small RAM (FD0-FD7)
 * - address lines for the gfx ROMs (R0-R17)
 * - address lines for the tile lookup PROMs (VCF0-VCF3, VCB0-VCB3)
 * - address lines for the sprite lookup PROMs (OCB0-OCB3, OCF0-OCF3)
 * - NNMI, NIRQ, NFIR, NE, NQ for the main CPU
 * - misc interface stuff
 * - color code to be output on screen (COA0-COA6)
 *
 *
 * control registers
 * 000:          scroll x (low 8 bits)
 * 001: -------x scroll x (high bit)
 * ------x- enable rowscroll? (combasc)
 * ----x--- this probably selects an alternate screen layout used in combat
 * school where tilemap #2 is overlayed on front and doesn't scroll.
 * The 32 lines of the front layer can be individually turned on or
 * off using the second 32 bytes of scroll RAM.
 * 002:          scroll y
 * 003: -------x bit 13 of the tile code
 * ------x- unknown (contra)
 * -----x-- might be sprite / tilemap priority (0 = sprites have priority)
 * (combat school, contra, haunted castle(0/1), labyrunr)
 * ----x--- selects sprite buffer (and makes a copy to a private buffer?)
 * ---x---- screen layout selector:
 * when this is set, 5 columns are added on the left of the screen
 * (that means 5 rows at the top for vertical games), and the
 * rightmost 2 columns are chopped away.
 * Tilemap #2 is used to display the 5 additional columns on the
 * left. The rest of tilemap #2 is not used and can be used as work
 * RAM by the program.
 * The visible area becomes 280x224.
 * Note that labyrunr changes this at runtime, setting it during
 * gameplay and resetting it on the title screen and crosshatch.
 * --x----- might be sprite / tilemap priority (0 = sprites have priority)
 * (combat school, contra, haunted castle(0/1), labyrunr)
 * -x------ Chops away the leftmost and rightmost columns, switching the
 * visible area from 256 to 240 pixels. This is used by combasc on
 * the scrolling stages, and by labyrunr on the title screen.
 * At first I thought that this enabled an extra bank of 0x40
 * sprites, needed by combasc, but labyrunr proves that this is not
 * the case
 * x------- unknown (contra)
 * 004: ----xxxx bits 9-12 of the tile code. Only the bits enabled by the following
 * mask are actually used, and replace the ones selected by register
 * 005.
 * xxxx---- mask enabling the above bits
 * 005: selects where in the attribute byte to pick bits 9-12 of the tile code,
 * output to pins R12-R15. The bit of the attribute byte to use is the
 * specified bit (0-3) + 3, that is one of bits 3-6. Bit 7 is hardcoded as
 * bit 8 of the code. Bits 0-2 are used for the color, however note that
 * some games (combat school, flak attack, maybe fast lane) use bit 3 as well,
 * and indeed there are 4 lines going to the color lookup PROM, so there has
 * to be a way to select this.
 * ------xx attribute bit to use for tile code bit  9
 * ----xx-- attribute bit to use for tile code bit 10
 * --xx---- attribute bit to use for tile code bit 11
 * xx------ attribute bit to use for tile code bit 12
 * 006: ----xxxx select additional effect for bits 3-6 of the tile attribute (the
 * same ones indexed by register 005). Note that an attribute bit
 * can therefore be used at the same time to be BOTH a tile code bit
 * and an additional effect.
 * -------x bit 3 of attribute is bit 3 of color (combasc, fastlane, flkatck)
 * ------x- bit 4 of attribute is tile flip X (assumption - no game uses this)
 * -----x-- bit 5 of attribute is tile flip Y (flkatck)
 * ----x--- bit 6 of attribute is tile priority over sprites (combasc, hcastle,
 * labyrunr)
 * Note that hcastle sets this bit for layer 0, and bit 6 of the
 * attribute is also used as bit 12 of the tile code, however that
 * bit is ALWAYS set thoughout the game.
 * combasc uses the bit inthe "graduation" scene during attract mode,
 * to place soldiers behind the stand.
 * Use in labyrunr has not been investigated yet.
 * --xx---- palette bank (both tiles and sprites, see contra)
 * 007: -------x nmi enable
 * ------x- irq enable
 * -----x-- firq enable (probably)
 * ----x--- flip screen
 * ---x---- unknown (contra, labyrunr)
 *
 *
 *
 * 007342
 * ------
 * The 007342 manages 2 64x32 scrolling tilemaps with 8x8 characters, and
 * optionally generates timing clocks and interrupt signals. It uses 0x2000
 * bytes of RAM, plus 0x0200 bytes for scrolling, and a variable amount of ROM.
 * It cannot read the ROMs.
 *
 * control registers
 * 000: ------x- INT control
 * ---x---- flip screen (TODO: doesn't work with thehustl)
 * 001: Used for banking in Rock'n'Rage
 * 002: -------x MSB of x scroll 1
 * ------x- MSB of x scroll 2
 * ---xxx-- layer 1 row/column scroll control
 * 000 = disabled
 * 010 = unknown (bladestl shootout between periods)
 * 011 = 32 columns (Blades of Steel)
 * 101 = 256 rows (Battlantis, Rock 'n Rage)
 * x------- enable sprite wraparound from bottom to top (see Blades of Steel
 * high score table)
 * 003: x scroll 1
 * 004: y scroll 1
 * 005: x scroll 2
 * 006: y scroll 2
 * 007: not used
 *
 *
 * 007420
 * ------
 * Sprite generator. 8 bytes per sprite with zoom. It uses 0x200 bytes of RAM,
 * and a variable amount of ROM. Nothing is known about its external interface.
 *
 *
 *
 * 052109/051962
 * -------------
 * These work in pair.
 * The 052109 manages 3 64x32 scrolling tilemaps with 8x8 characters, and
 * optionally generates timing clocks and interrupt signals. It uses 0x4000
 * bytes of RAM, and a variable amount of ROM. It cannot read the ROMs:
 * instead, it exports 21 bits (16 from the tilemap RAM + 3 for the character
 * raster line + 2 additional ones for ROM banking) and these are externally
 * used to generate the address of the required data on the ROM; the output of
 * the ROMs is sent to the 051962, along with a color code. In theory you could
 * have any combination of bits in the tilemap RAM, as long as they add to 16.
 * In practice, all the games supported so far standardize on the same format
 * which uses 3 bits for the color code and 13 bits for the character code.
 * The 051962 multiplexes the data of the three layers and converts it into
 * palette indexes and transparency bits which will be mixed later in the video
 * chain.
 * Priority is handled externally: these chips only generate the tilemaps, they
 * don't mix them.
 * Both chips are interfaced with the main CPU. When the RMRD pin is asserted,
 * the CPU can read the gfx ROM data. This is done by telling the 052109 which
 * dword to read (this is a combination of some banking registers, and the CPU
 * address lines), and then reading it from the 051962.
 *
 * 052109 inputs:
 * - address lines (AB0-AB15, AB13-AB15 seem to have a different function)
 * - data lines (DB0-DB7)
 * - misc interface stuff
 *
 * 052109 outputs:
 * - address lines for the private RAM (RA0-RA12)
 * - data lines for the private RAM (VD0-VD15)
 * - NMI, IRQ, FIRQ for the main CPU
 * - misc interface stuff
 * - ROM bank selector (CAB1-CAB2)
 * - character "code" (VC0-VC10)
 * - character "color" (COL0-COL7); used foc color but also bank switching and tile
 * flipping. Exact meaning depends on externl connections. All evidence indicates
 * that COL2 and COL3 select the tile bank, and are replaced with the low 2 bits
 * from the bank register. The top 2 bits of the register go to CAB1-CAB2.
 * However, this DOES NOT WORK with Gradius III. "color" seems to pass through
 * unaltered.
 * - layer A horizontal scroll (ZA1H-ZA4H)
 * - layer B horizontal scroll (ZB1H-ZB4H)
 * - ????? (BEN)
 *
 * 051962 inputs:
 * - gfx data from the ROMs (VC0-VC31)
 * - color code (COL0-COL7); only COL4-COL7 seem to really be used for color; COL0
 * is tile flip X.
 * - layer A horizontal scroll (ZA1H-ZA4H)
 * - layer B horizontal scroll (ZB1H-ZB4H)
 * - let main CPU read the gfx ROMs (RMRD)
 * - address lines to be used with RMRD (AB0-AB1)
 * - data lines to be used with RMRD (DB0-DB7)
 * - ????? (BEN)
 * - misc interface stuff
 *
 * 051962 outputs:
 * - FIX layer palette index (DFI0-DFI7)
 * - FIX layer transparency (NFIC)
 * - A layer palette index (DSA0-DSAD); DSAA-DSAD seem to be unused
 * - A layer transparency (NSAC)
 * - B layer palette index (DSB0-DSBD); DSBA-DSBD seem to be unused
 * - B layer transparency (NSBC)
 * - misc interface stuff
 *
 *
 * 052109 memory layout:
 * 0000-07ff: layer FIX tilemap (attributes)
 * 0800-0fff: layer A tilemap (attributes)
 * 1000-1fff: layer B tilemap (attributes)
 * 180c-1833: A y scroll
 * 1a00-1bff: A x scroll
 * 1c00     : ?
 * 1c80     : row/column scroll control
 * ------xx layer A row scroll
 * 00 = disabled
 * 01 = disabled? (gradius3, vendetta)
 * 10 = 32 lines
 * 11 = 256 lines
 * -----x-- layer A column scroll
 * 0 = disabled
 * 1 = 64 (actually 40) columns
 * ---xx--- layer B row scroll
 * --x----- layer B column scroll
 * surpratk sets this register to 70 during the second boss. There is
 * nothing obviously wrong so it's not clear what should happen.
 * 1d00     : bits 0 & 1 might enable NMI and FIRQ, not sure
 * : bit 2 = IRQ enable
 * 1d80     : ROM bank selector bits 0-3 = bank 0 bits 4-7 = bank 1
 * 1e00     : ROM subbank selector for ROM testing
 * 1e80     : bit 0 = flip screen (applies to tilemaps only, not sprites)
 * : bit 1 = set by crimfght, mainevt, surpratk, xmen, mia, punkshot, thndrx2, spy
 * :         it seems to enable tile flip X, however flip X is handled by the
 * :         051962 and it is not hardwired to a specific tile attribute.
 * :         Note that xmen, punkshot and thndrx2 set the bit but the current
 * :         drivers don't use flip X and seem to work fine.
 * : bit 2 = enables tile flip Y when bit 1 of the tile attribute is set
 * 1f00     : ROM bank selector bits 0-3 = bank 2 bits 4-7 = bank 3
 * 2000-27ff: layer FIX tilemap (code)
 * 2800-2fff: layer A tilemap (code)
 * 3000-37ff: layer B tilemap (code)
 * 3800-3807: nothing here, so the chip can share address space with a 051937
 * 380c-3833: B y scroll
 * 3a00-3bff: B x scroll
 * 3c00-3fff: nothing here, so the chip can share address space with a 051960
 * 3d80     : mirror of 1d80, but ONLY during ROM test (surpratk)
 * 3e00     : mirror of 1e00, but ONLY during ROM test (surpratk)
 * 3f00     : mirror of 1f00, but ONLY during ROM test (surpratk)
 * EXTRA ADDRESSING SPACE USED BY X-MEN:
 * 4000-47ff: layer FIX tilemap (code high bits)
 * 4800-4fff: layer A tilemap (code high bits)
 * 5000-57ff: layer B tilemap (code high bits)
 *
 * The main CPU doesn't have direct acces to the RAM used by the 052109, it has
 * to through the chip.
 *
 *
 *
 * 051960/051937
 * -------------
 * Sprite generators. Designed to work in pair. The 051960 manages the sprite
 * list and produces and address that is fed to the gfx ROMs. The data from the
 * ROMs is sent to the 051937, along with color code and other stuff from the
 * 051960. The 051937 outputs up to 12 bits of palette index, plus "shadow" and
 * transparency information.
 * Both chips are interfaced to the main CPU, through 8-bit data buses and 11
 * bits of address space. The 051937 sits in the range 000-007, while the 051960
 * in the range 400-7ff (all RAM). The main CPU can read the gfx ROM data though
 * the 051937 data bus, while the 051960 provides the address lines.
 * The 051960 is designed to directly address 1MB of ROM space, since it produces
 * 18 address lines that go to two 16-bit wide ROMs (the 051937 has a 32-bit data
 * bus to the ROMs). However, the addressing space can be increased by using one
 * or more of the "color attribute" bits of the sprites as bank selectors.
 * Moreover a few games store the gfx data in the ROMs in a format different from
 * the one expected by the 051960, and use external logic to reorder the address
 * lines.
 * The 051960 can also genenrate IRQ, FIRQ and NMI signals.
 *
 * memory map:
 * 000-007 is for the 051937, but also seen by the 051960
 * 400-7ff is 051960 only
 * 000     R  bit 0 = unknown, looks like a status flag or something
 * aliens waits for it to be 0 before starting to copy sprite data
 * thndrx2 needs it to pulse for the startup checks to succeed
 * 000     W  bit 0 = irq enable/acknowledge?
 * bit 3 = flip screen (applies to sprites only, not tilemaps)
 * bit 4 = unknown, used by Devastators, TMNT, Aliens, Chequered Flag, maybe others
 * aliens sets it just after checking bit 0, and before copying
 * the sprite data
 * bit 5 = enable gfx ROM reading
 * 001     W  Devastators sets bit 1, function unknown.
 * Ultraman sets the register to 0x0f.
 * None of the other games I tested seem to set this register to other than 0.
 * 002-003 W  selects the portion of the gfx ROMs to be read.
 * 004     W  Aliens uses this to select the ROM bank to be read, but Punk Shot
 * and TMNT don't, they use another bit of the registers above. Many
 * other games write to this register before testing.
 * It is possible that bits 2-7 of 003 go to OC0-OC5, and bits 0-1 of
 * 004 go to OC6-OC7.
 * 004-007 R  reads data from the gfx ROMs (32 bits in total). The address of the
 * data is determined by the register above and by the last address
 * accessed on the 051960; plus bank switch bits for larger ROMs.
 * It seems that the data can also be read directly from the 051960
 * address space: 88 Games does this. First it reads 004 and discards
 * the result, then it reads from the 051960 the data at the address
 * it wants. The normal order is the opposite, read from the 051960 at
 * the address you want, discard the result, and fetch the data from
 * 004-007.
 * 400-7ff RW sprite RAM, 8 bytes per sprite
 *
 *
 *
 * 053245/053244
 * -------------
 * Sprite generators. The 053245 has a 16-bit data bus to the main CPU.
 *
 * 053244 memory map (but the 053245 sees and processes them too):
 * 000-001 W  global X offset
 * 002-003 W  global Y offset
 * 004     W  unknown
 * 005     W  bit 0 = flip screen X
 * bit 1 = flip screen Y
 * bit 2 = unknown, used by Parodius
 * bit 4 = enable gfx ROM reading
 * bit 5 = unknown, used by Rollergames
 * 006     W  unknown
 * 007     W  unknown
 * 008-009 W  low 16 bits of the ROM address to read
 * 00a-00b W  high 3 bits of the ROM address to read
 * 00c-00f R  reads data from the gfx ROMs (32 bits in total). The address of the
 * data is determined by the registers above; plus bank switch bits for
 * larger ROMs.
 *
 *
 *
 * 053247/053246
 * -------------
 * Sprite generators. Nothing is known about their external interface.
 * The sprite RAM format is very similar to the 053245.
 *
 * 053246 memory map (but the 053247 sees and processes them too):
 * 000-001 W  global X offset
 * 002-003 W  global Y offset
 * 004     W  low 8 bits of the ROM address to read
 * 005     W  bit 0 = flip screen X
 * bit 1 = flip screen Y
 * bit 2 = unknown
 * bit 4 = interrupt enable
 * bit 5 = unknown
 * 006-007 W  high 16 bits of the ROM address to read
 *
 * ???-??? R  reads data from the gfx ROMs (16 bits in total). The address of the
 * data is determined by the registers above
 *
 *
 *
 * 051316
 * ------
 * Manages a 32x32 tilemap (16x16 tiles, 512x512 pixels) which can be zoomed,
 * distorted and rotated.
 * It uses two internal 24 bit counters which are incremented while scanning the
 * picture. The coordinates of the pixel in the tilemap that has to be drawn to
 * the current beam position are the counters / (2^11).
 * The chip doesn't directly generate the color information for the pixel, it
 * just generates a 24 bit address (whose top 16 bits are the contents of the
 * tilemap RAM), and a "visible" signal. It's up to external circuitry to convert
 * the address into a pixel color. Most games seem to use 4bpp graphics, but Ajax
 * uses 7bpp.
 * If the value in the internal counters is out of the visible range (0..511), it
 * is truncated and the corresponding address is still generated, but the "visible"
 * signal is not asserted. The external circuitry might ignore that signal and
 * still generate the pixel, therefore making the tilemap a continuous playfield
 * that wraps around instead of a large sprite.
 *
 *
 * control registers
 * 000-001 X counter starting value / 256
 * 002-003 amount to add to the X counter after each horizontal pixel
 * 004-005 amount to add to the X counter after each line (0 = no rotation)
 * 006-007 Y counter starting value / 256
 * 008-009 amount to add to the Y counter after each horizontal pixel (0 = no rotation)
 * 00a-00b amount to add to the Y counter after each line
 * 00c-00d ROM bank to read, used during ROM testing
 * 00e     bit 0 = enable ROM reading (active low). This only makes the chip output the
 * requested address: the ROM is actually read externally, not through
 * the chip's data bus.
 * bit 1 = unknown
 * bit 2 = unknown
 * 00f     unused
 *
 *
 *
 * 053251
 * ------
 * Priority encoder.
 *
 * The chip has inputs for 5 layers (CI0-CI4); only 4 are used (CI1-CI4)
 * CI0-CI2 are 9(=5+4) bits inputs, CI3-CI4 8(=4+4) bits
 *
 * The input connctions change from game to game. E.g. in Simpsons,
 * CI0 = grounded (background color)
 * CI1 = sprites
 * CI2 = FIX
 * CI3 = A
 * CI4 = B
 *
 * in lgtnfght:
 * CI0 = grounded
 * CI1 = sprites
 * CI2 = FIX
 * CI3 = B
 * CI4 = A
 *
 * there are three 6 bit priority inputs, PR0-PR2
 *
 * simpsons:
 * PR0 = 111111
 * PR1 = xxxxx0 x bits coming from the sprite attributes
 * PR2 = 111111
 *
 * lgtnfght:
 * PR0 = 111111
 * PR1 = 1xx000 x bits coming from the sprite attributes
 * PR2 = 111111
 *
 * also two shadow inputs, SDI0 and SDI1 (from the sprite attributes)
 *
 * the chip outputs the 11 bit palette index, CO0-CO10, and two shadow bits.
 *
 * 16 internal registers; registers are 6 bits wide (input is D0-D5)
 * For the most part, their meaning is unknown
 * All registers are write only.
 * There must be a way to enable/disable the three external PR inputs.
 * Some games initialize the priorities of the sprite & background layers,
 * others don't. It isn't clear whether the data written to those registers is
 * actually used, since the priority is taken from the external ports.
 *
 * 0  priority of CI0 (higher = lower priority)
 * punkshot: unused?
 * lgtnfght: unused?
 * simpsons: 3f = 111111
 * xmen:     05 = 000101  default value
 * xmen:     09 = 001001  used to swap CI0 and CI2
 * 1  priority of CI1 (higher = lower priority)
 * punkshot: 28 = 101000
 * lgtnfght: unused?
 * simpsons: unused?
 * xmen:     02 = 000010
 * 2  priority of CI2 (higher = lower priority)
 * punkshot: 24 = 100100
 * lgtnfght: 24 = 100100
 * simpsons: 04 = 000100
 * xmen:     09 = 001001  default value
 * xmen:     05 = 000101  used to swap CI0 and CI2
 * 3  priority of CI3 (higher = lower priority)
 * punkshot: 34 = 110100
 * lgtnfght: 34 = 110100
 * simpsons: 28 = 101000
 * xmen:     00 = 000000
 * 4  priority of CI4 (higher = lower priority)
 * punkshot: 2c = 101100  default value
 * punkshot: 3c = 111100  used to swap CI3 and CI4
 * punkshot: 26 = 100110  used to swap CI1 and CI4
 * lgtnfght: 2c = 101100
 * simpsons: 18 = 011000
 * xmen:     fe = 111110
 * 5  unknown
 * punkshot: unused?
 * lgtnfght: 2a = 101010
 * simpsons: unused?
 * xmen: unused?
 * 6  unknown
 * punkshot: 26 = 100110
 * lgtnfght: 30 = 110000
 * simpsons: 17 = 010111
 * xmen:     03 = 000011 (written after initial tests)
 * 7  unknown
 * punkshot: unused?
 * lgtnfght: unused?
 * simpsons: 27 = 100111
 * xmen:     07 = 000111 (written after initial tests)
 * 8  unknown
 * punkshot: unused?
 * lgtnfght: unused?
 * simpsons: 37 = 110111
 * xmen:     ff = 111111 (written after initial tests)
 * 9  ----xx CI0 palette index base (CO9-CO10)
 * --xx-- CI1 palette index base (CO9-CO10)
 * xx---- CI2 palette index base (CO9-CO10)
 * 10  ---xxx CI3 palette index base (CO8-CO10)
 * xxx--- CI4 palette index base (CO8-CO10)
 * 11  unknown
 * punkshot: 00 = 000000
 * lgtnfght: 00 = 000000
 * simpsons: 00 = 000000
 * xmen:     00 = 000000 (written after initial tests)
 * 12  unknown
 * punkshot: 04 = 000100
 * lgtnfght: 04 = 000100
 * simpsons: 05 = 000101
 * xmen:     05 = 000101
 * 13  unused
 * 14  unused
 * 15  unused
 *
 *
 * 054000
 * ------
 * Sort of a protection device, used for collision detection.
 * It is passed a few parameters, and returns a boolean telling if collision
 * happened. It has no access to gfx data, it only does arithmetical operations
 * on the parameters.
 *
 * Memory map:
 * 00      unused
 * 01-03 W A center X
 * 04    W unknown, needed by thndrx2 to pass the startup check, we use a hack
 * 05      unused
 * 06    W A semiaxis X
 * 07    W A semiaxis Y
 * 08      unused
 * 09-0b W A center Y
 * 0c    W unknown, needed by thndrx2 to pass the startup check, we use a hack
 * 0d      unused
 * 0e    W B semiaxis X
 * 0f    W B semiaxis Y
 * 10      unused
 * 11-13 W B center Y
 * 14      unused
 * 15-17 W B center X
 * 18    R 0 = collision, 1 = no collision
 *
 *
 * 051733
 * ------
 * Sort of a protection device, used for collision detection, and for
 * arithmetical operations.
 * It is passed a few parameters, and returns the result.
 *
 * Memory map(preliminary):
 * ------------------------
 * 00-01 W operand 1
 * 02-03 W operand 2
 *
 * 00-01 R operand 1/operand 2
 * 02-03 R operand 1%operand 2?
 *
 * 06-07 W Radius
 * 08-09 W Y pos of obj1
 * 0a-0b W X pos of obj1
 * 0c-0d W Y pos of obj2
 * 0e-0f W X pos of obj2
 * 13	  W unknown
 *
 * 07	  R collision (0x80 = no, 0x00 = yes)
 *
 * Other addresses are unknown or unused.
 *
 * Fast Lane:
 * ----------
 * $9def:
 * This routine is called only after a collision.
 * (R) 0x0006:	unknown. Only bits 0-3 are used.
 *
 * Blades of Steel:
 * ----------------
 * $ac2f:
 * (R) 0x2f86: unknown. Only uses bit 0.
 *
 * $a5de:
 * writes to 0x2f84-0x2f85, waits a little, and then reads from 0x2f84.
 *
 * $7af3:
 * (R) 0x2f86: unknown. Only uses bit 0.
 *
 *
 * Devastators:
 * ------------
 * $6ce8:
 * reads from 0x0006, and only uses bit 1.
 *
 ************************************************************************** */

/*
 * ported to v0.37b7
 * using automatic conversion tool v0.01
 */
package gr.codebb.arcadeflex.WIP.v037b7.vidhrdw;

import static arcadeflex.v037b7.mame.cpuintrf.cpu_get_pc;
import static arcadeflex.v037b7.mame.cpuintrf.cpu_getcurrentframe;
import static arcadeflex.v037b7.mame.cpuintrfH.CLEAR_LINE;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.drawgfxzoom;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.drawgfx.pdrawgfx;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapC.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.tilemapH.*;
import arcadeflex.common.ptrLib.UBytePtr;
import static gr.codebb.arcadeflex.old.mame.drawgfx.*;
import static arcadeflex.v037b7.mame.drawgfxH.*;
import static arcadeflex.v037b7.mame.mameH.MAX_GFX_ELEMENTS;
import static arcadeflex.v037b7.generic.funcPtr.*;
import arcadeflex.v037b7.mame.osdependH.osd_bitmap;
import static arcadeflex.common.libc.cstring.*;
import static gr.codebb.arcadeflex.WIP.v037b7.mame.palette.*;
import static arcadeflex.v037b7.mame.paletteH.*;
import arcadeflex.common.ptrLib.UShortPtr;
import arcadeflex.common.subArrays.UShortArray;
import static arcadeflex.common.libc.expressions.NOT;
import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.sizeof;
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;
import static arcadeflex.v037b7.mame.common.memory_region;
import static arcadeflex.v037b7.mame.common.memory_region_length;
import static arcadeflex.v037b7.mame.drawgfx.pdrawgfxzoom;
import static arcadeflex.v037b7.vidhrdw.konamiic.K051316_zoom_draw;

public class konamiic {

    /*
     This recursive function doesn't use additional memory
     (it could be easily converted into an iterative one).
     It's called shuffle because it mimics the shuffling of a deck of cards.
     */
    static void shuffle(UShortPtr buf, int len) {
        int i;
        char t;

        if (len == 2) {
            return;
        }

        if ((len % 4) != 0) {
            throw new UnsupportedOperationException("Error in shuffle konamicc");
        }
        /* must not happen */

        len /= 2;

        for (i = 0; i < len / 2; i++) {
            t = buf.read(len / 2 + i);
            buf.write(len / 2 + i, buf.read(len + i));
            buf.write(len + i, t);
        }

        shuffle(buf, len);
        shuffle(new UShortPtr(buf, len * 2), len);//len*2 ??
    }


    /* helper function to join two 16-bit ROMs and form a 32-bit data stream */
    public static void konami_rom_deinterleave_2(int mem_region) {
        shuffle(new UShortPtr(memory_region(mem_region).memory, memory_region(mem_region).offset), memory_region_length(mem_region) / 2);
    }

    /* helper function to join four 16-bit ROMs and form a 64-bit data stream */
    public static void konami_rom_deinterleave_4(int mem_region) {
        konami_rom_deinterleave_2(mem_region);
        konami_rom_deinterleave_2(mem_region);
    }

    public static final int MAX_K007121 = 2;

    public static /*unsigned*/ char[][] K007121_ctrlram = new char[MAX_K007121][8];
    public static int[] K007121_flipscreen = new int[MAX_K007121];

    public static void K007121_ctrl_w(int chip, int offset, int data) {
        switch (offset) {
            case 6:
                /* palette bank change */
                if ((K007121_ctrlram[chip][offset] & 0x30) != (data & 0x30)) {
                    tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
                }
                break;
            case 7:
                K007121_flipscreen[chip] = data & 0x08;
                break;
        }

        K007121_ctrlram[chip][offset] = (char) (data & 0xFF);
    }

    public static WriteHandlerPtr K007121_ctrl_0_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            K007121_ctrl_w(0, offset, data);
        }
    };

    public static WriteHandlerPtr K007121_ctrl_1_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            K007121_ctrl_w(1, offset, data);
        }
    };

    public static void K007121_sprites_draw(int chip, osd_bitmap bitmap,
            UBytePtr source, int base_color, int global_x_offset, int bank_base,
            /*UINT32*/ int pri_mask) {
        GfxElement gfx = Machine.gfx[chip];
        int flipscreen = K007121_flipscreen[chip];
        int i, num, inc, trans;
        int[] offs = new int[5];
        int is_flakatck = K007121_ctrlram[chip][0x06] & 0x04;
        /* WRONG!!!! */

        if (is_flakatck != 0) {
            num = 0x40;
            inc = -0x20;
            source.offset += 0x3f * 0x20;
            offs[0] = 0x0e;
            offs[1] = 0x0f;
            offs[2] = 0x06;
            offs[3] = 0x04;
            offs[4] = 0x08;
            /* Flak Attack doesn't use a lookup PROM, it maps the color code directly */
 /* to a palette entry */
            trans = TRANSPARENCY_PEN;
        } else /* all others */ {
            num = (K007121_ctrlram[chip][0x03] & 0x40) != 0 ? 0x80 : 0x40;
            /* WRONG!!! (needed by combasc)  */
            inc = 5;
            offs[0] = 0x00;
            offs[1] = 0x01;
            offs[2] = 0x02;
            offs[3] = 0x03;
            offs[4] = 0x04;
            trans = TRANSPARENCY_COLOR;
            /* when using priority buffer, draw front to back */
            if (pri_mask != -1) {
                source.offset += (num - 1) * inc;
                inc = -inc;
            }
        }

        for (i = 0; i < num; i++) {
            int number = source.read(offs[0]);
            /* sprite number */
            int sprite_bank = source.read(offs[1]) & 0x0f;
            /* sprite bank */
            int sx = source.read(offs[3]);
            /* vertical position */
            int sy = source.read(offs[2]);
            /* horizontal position */
            int attr = source.read(offs[4]);
            /* attributes */
            int xflip = source.read(offs[4]) & 0x10;
            /* flip x */
            int yflip = source.read(offs[4]) & 0x20;
            /* flip y */
            int color = base_color + ((source.read(offs[1]) & 0xf0) >> 4);
            int width, height;
            int x_offset[] = {0x0, 0x1, 0x4, 0x5};
            int y_offset[] = {0x0, 0x2, 0x8, 0xa};
            int x, y, ex, ey;

            if ((attr & 0x01) != 0) {
                sx -= 256;
            }
            if (sy >= 240) {
                sy -= 256;
            }

            number += ((sprite_bank & 0x3) << 8) + ((attr & 0xc0) << 4);
            number = number << 2;
            number += (sprite_bank >> 2) & 3;

            if (is_flakatck == 0 || source.read(0x00) != 0) /* Flak Attack needs this */ {
                number += bank_base;

                switch (attr & 0xe) {
                    case 0x06:
                        width = height = 1;
                        break;
                    case 0x04:
                        width = 1;
                        height = 2;
                        number &= (~2);
                        break;
                    case 0x02:
                        width = 2;
                        height = 1;
                        number &= (~1);
                        break;
                    case 0x00:
                        width = height = 2;
                        number &= (~3);
                        break;
                    case 0x08:
                        width = height = 4;
                        number &= (~3);
                        break;
                    default:
                        width = 1;
                        height = 1;
                    //					logerror("Unknown sprite size %02x\n",attr&0xe);
                    //					usrintf_showmessage("Unknown sprite size %02x\n",attr&0xe);
                }

                for (y = 0; y < height; y++) {
                    for (x = 0; x < width; x++) {
                        ex = xflip != 0 ? (width - 1 - x) : x;
                        ey = yflip != 0 ? (height - 1 - y) : y;

                        if (flipscreen != 0) {
                            if (pri_mask != -1) {
                                pdrawgfx(bitmap, gfx,
                                        number + x_offset[ex] + y_offset[ey],
                                        color,
                                        xflip != 0 ? 0 : 1, yflip != 0 ? 0 : 1,
                                        248 - (sx + x * 8), 248 - (sy + y * 8),
                                        Machine.visible_area, trans, 0,
                                        pri_mask);
                            } else {
                                drawgfx(bitmap, gfx,
                                        number + x_offset[ex] + y_offset[ey],
                                        color,
                                        xflip != 0 ? 0 : 1, yflip != 0 ? 0 : 1,
                                        248 - (sx + x * 8), 248 - (sy + y * 8),
                                        Machine.visible_area, trans, 0);
                            }
                        } else {
                            if (pri_mask != -1) {
                                pdrawgfx(bitmap, gfx,
                                        number + x_offset[ex] + y_offset[ey],
                                        color,
                                        xflip, yflip,
                                        global_x_offset + sx + x * 8, sy + y * 8,
                                        Machine.visible_area, trans, 0,
                                        pri_mask);
                            } else {
                                drawgfx(bitmap, gfx,
                                        number + x_offset[ex] + y_offset[ey],
                                        color,
                                        xflip, yflip,
                                        global_x_offset + sx + x * 8, sy + y * 8,
                                        Machine.visible_area, trans, 0);
                            }
                        }
                    }
                }
            }

            source.offset += inc;
        }
    }

    public static void K007121_mark_sprites_colors(int chip,
            UBytePtr source, int base_color, int bank_base) {
        int i, num, inc;
        int[] offs = new int[5];
        int is_flakatck = K007121_ctrlram[chip][0x06] & 0x04;
        /* WRONG!!!! */

        char[] palette_map = new char[512];

        if (is_flakatck != 0) {
            num = 0x40;
            inc = -0x20;
            source.offset += 0x3f * 0x20;
            offs[0] = 0x0e;
            offs[1] = 0x0f;
            offs[2] = 0x06;
            offs[3] = 0x04;
            offs[4] = 0x08;
        } else /* all others */ {
            num = (K007121_ctrlram[chip][0x03] & 0x40) != 0 ? 0x80 : 0x40;
            inc = 5;
            offs[0] = 0x00;
            offs[1] = 0x01;
            offs[2] = 0x02;
            offs[3] = 0x03;
            offs[4] = 0x04;
        }

        memset(palette_map, 0, palette_map.length);

        /* sprites */
        for (i = 0; i < num; i++) {
            int color;

            color = base_color + ((source.read(offs[1]) & 0xf0) >> 4);
            palette_map[color] |= 0xffff;

            source.offset += inc;
        }

        /* now build the final table */
        for (i = 0; i < 512; i++) {
            int usage = palette_map[i], j;
            if (usage != 0) {
                for (j = 0; j < 16; j++) {
                    if ((usage & (1 << j)) != 0) {
                        palette_used_colors.write(i * 16 + j, palette_used_colors.read(i * 16 + j) | PALETTE_COLOR_VISIBLE);
                    }
                }
            }
        }
    }

    /*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static UBytePtr K007342_ram,*K007342_scroll_ram;
/*TODO*///	static int K007342_gfxnum;
/*TODO*///	static int K007342_int_enabled;
/*TODO*///	static int K007342_flipscreen;
/*TODO*///	static int K007342_scrollx[2];
/*TODO*///	static int K007342_scrolly[2];
/*TODO*///	static UBytePtr K007342_videoram_0,*K007342_colorram_0;
/*TODO*///	static UBytePtr K007342_videoram_1,*K007342_colorram_1;
/*TODO*///	static int K007342_regs[8];
/*TODO*///	static void (*K007342_callback)(int tilemap, int bank, int *code, int *color);
/*TODO*///	static struct tilemap *K007342_tilemap[2];
/*TODO*///	
/*TODO*///	/***************************************************************************
/*TODO*///	
/*TODO*///	  Callbacks for the TileMap code
/*TODO*///	
/*TODO*///	***************************************************************************/
/*TODO*///	
/*TODO*///	/*
/*TODO*///	  data format:
/*TODO*///	  video RAM     xxxxxxxx    tile number (bits 0-7)
/*TODO*///	  color RAM     x-------    tiles with priority over the sprites
/*TODO*///	  color RAM     -x------    depends on external conections
/*TODO*///	  color RAM     --x-----    flip Y
/*TODO*///	  color RAM     ---x----    flip X
/*TODO*///	  color RAM     ----xxxx    depends on external connections (usually color and banking)
/*TODO*///	*/
/*TODO*///	
/*TODO*///	static UBytePtr colorram,*videoram1,*videoram2;
/*TODO*///	static int layer;
/*TODO*///	
/*TODO*///	static void tilemap_0_preupdate(void)
/*TODO*///	{
/*TODO*///		colorram = K007342_colorram_0;
/*TODO*///		videoram1 = K007342_videoram_0;
/*TODO*///		layer = 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	static void tilemap_1_preupdate(void)
/*TODO*///	{
/*TODO*///		colorram = K007342_colorram_1;
/*TODO*///		videoram1 = K007342_videoram_1;
/*TODO*///		layer = 1;
/*TODO*///	}
/*TODO*///	
/*TODO*///	static UINT32 K007342_scan(UINT32 col,UINT32 row,UINT32 num_cols,UINT32 num_rows)
/*TODO*///	{
/*TODO*///		/* logical (col,row) . memory offset */
/*TODO*///		return (col & 0x1f) + ((row & 0x1f) << 5) + ((col & 0x20) << 5);
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static GetTileInfoPtr K007342_get_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
/*TODO*///	{
/*TODO*///		int color, code;
/*TODO*///	
/*TODO*///		color = colorram.read(tile_index);
/*TODO*///		code = videoram1[tile_index];
/*TODO*///	
/*TODO*///		tile_info.flags = TILE_FLIPYX((color & 0x30) >> 4);
/*TODO*///		tile_info.priority = (color & 0x80) >> 7;
/*TODO*///	
/*TODO*///		(*K007342_callback)(layer, K007342_regs[1], &code, &color);
/*TODO*///	
/*TODO*///		SET_TILE_INFO(K007342_gfxnum,code,color);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	int K007342_vh_start(int gfx_index, void (*callback)(int tilemap, int bank, int *code, int *color))
/*TODO*///	{
/*TODO*///		K007342_gfxnum = gfx_index;
/*TODO*///		K007342_callback = callback;
/*TODO*///	
/*TODO*///		K007342_tilemap[0] = tilemap_create(K007342_get_tile_info,K007342_scan,TILEMAP_TRANSPARENT,8,8,64,32);
/*TODO*///		K007342_tilemap[1] = tilemap_create(K007342_get_tile_info,K007342_scan,TILEMAP_TRANSPARENT,8,8,64,32);
/*TODO*///	
/*TODO*///		K007342_ram = malloc(0x2000);
/*TODO*///		K007342_scroll_ram = malloc(0x0200);
/*TODO*///	
/*TODO*///		if (!K007342_ram || !K007342_scroll_ram || !K007342_tilemap[0] || !K007342_tilemap[1])
/*TODO*///		{
/*TODO*///			K007342_vh_stop();
/*TODO*///			return 1;
/*TODO*///		}
/*TODO*///	
/*TODO*///		memset(K007342_ram,0,0x2000);
/*TODO*///	
/*TODO*///		K007342_colorram_0 = &K007342_ram[0x0000];
/*TODO*///		K007342_colorram_1 = &K007342_ram[0x1000];
/*TODO*///		K007342_videoram_0 = &K007342_ram[0x0800];
/*TODO*///		K007342_videoram_1 = &K007342_ram[0x1800];
/*TODO*///	
/*TODO*///		K007342_tilemap[0].transparent_pen = 0;
/*TODO*///		K007342_tilemap[1].transparent_pen = 0;
/*TODO*///	
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static VhStopPtr K007342_vh_stop = new VhStopPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		free(K007342_ram);
/*TODO*///		K007342_ram = 0;
/*TODO*///		free(K007342_scroll_ram);
/*TODO*///		K007342_scroll_ram = 0;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K007342_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		return K007342_ram[offset];
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K007342_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		if (offset < 0x1000)
/*TODO*///		{		/* layer 0 */
/*TODO*///			if (K007342_ram[offset] != data)
/*TODO*///			{
/*TODO*///				K007342_ram[offset] = data;
/*TODO*///				tilemap_mark_tile_dirty(K007342_tilemap[0],offset & 0x7ff);
/*TODO*///			}
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{						/* layer 1 */
/*TODO*///			if (K007342_ram[offset] != data)
/*TODO*///			{
/*TODO*///				K007342_ram[offset] = data;
/*TODO*///				tilemap_mark_tile_dirty(K007342_tilemap[1],offset & 0x7ff);
/*TODO*///			}
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K007342_scroll_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		return K007342_scroll_ram[offset];
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K007342_scroll_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		K007342_scroll_ram[offset] = data;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K007342_vreg_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		switch(offset)
/*TODO*///		{
/*TODO*///			case 0x00:
/*TODO*///				/* bit 1: INT control */
/*TODO*///				K007342_int_enabled = data & 0x02;
/*TODO*///				K007342_flipscreen = data & 0x10;
/*TODO*///				tilemap_set_flip(K007342_tilemap[0],K007342_flipscreen ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
/*TODO*///				tilemap_set_flip(K007342_tilemap[1],K007342_flipscreen ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
/*TODO*///				break;
/*TODO*///			case 0x01:  /* used for banking in Rock'n'Rage */
/*TODO*///				if (data != K007342_regs[1])
/*TODO*///					tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
/*TODO*///			case 0x02:
/*TODO*///				K007342_scrollx[0] = (K007342_scrollx[0] & 0xff) | ((data & 0x01) << 8);
/*TODO*///				K007342_scrollx[1] = (K007342_scrollx[1] & 0xff) | ((data & 0x02) << 7);
/*TODO*///				break;
/*TODO*///			case 0x03:  /* scroll x (register 0) */
/*TODO*///				K007342_scrollx[0] = (K007342_scrollx[0] & 0x100) | data;
/*TODO*///				break;
/*TODO*///			case 0x04:  /* scroll y (register 0) */
/*TODO*///				K007342_scrolly[0] = data;
/*TODO*///				break;
/*TODO*///			case 0x05:  /* scroll x (register 1) */
/*TODO*///				K007342_scrollx[1] = (K007342_scrollx[1] & 0x100) | data;
/*TODO*///				break;
/*TODO*///			case 0x06:  /* scroll y (register 1) */
/*TODO*///				K007342_scrolly[1] = data;
/*TODO*///			case 0x07:  /* unused */
/*TODO*///				break;
/*TODO*///		}
/*TODO*///		K007342_regs[offset] = data;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	void K007342_tilemap_update(void)
/*TODO*///	{
/*TODO*///		int offs;
/*TODO*///	
/*TODO*///	
/*TODO*///		/* update scroll */
/*TODO*///		switch (K007342_regs[2] & 0x1c)
/*TODO*///		{
/*TODO*///			case 0x00:
/*TODO*///			case 0x08:	/* unknown, blades of steel shootout between periods */
/*TODO*///				tilemap_set_scroll_rows(K007342_tilemap[0],1);
/*TODO*///				tilemap_set_scroll_cols(K007342_tilemap[0],1);
/*TODO*///				tilemap_set_scrollx(K007342_tilemap[0],0,K007342_scrollx[0]);
/*TODO*///				tilemap_set_scrolly(K007342_tilemap[0],0,K007342_scrolly[0]);
/*TODO*///				break;
/*TODO*///	
/*TODO*///			case 0x0c:	/* 32 columns */
/*TODO*///				tilemap_set_scroll_rows(K007342_tilemap[0],1);
/*TODO*///				tilemap_set_scroll_cols(K007342_tilemap[0],512);
/*TODO*///				tilemap_set_scrollx(K007342_tilemap[0],0,K007342_scrollx[0]);
/*TODO*///				for (offs = 0;offs < 256;offs++)
/*TODO*///					tilemap_set_scrolly(K007342_tilemap[0],(offs + K007342_scrollx[0]) & 0x1ff,
/*TODO*///							K007342_scroll_ram[2*(offs/8)] + 256 * K007342_scroll_ram[2*(offs/8)+1]);
/*TODO*///				break;
/*TODO*///	
/*TODO*///			case 0x14:	/* 256 rows */
/*TODO*///				tilemap_set_scroll_rows(K007342_tilemap[0],256);
/*TODO*///				tilemap_set_scroll_cols(K007342_tilemap[0],1);
/*TODO*///				tilemap_set_scrolly(K007342_tilemap[0],0,K007342_scrolly[0]);
/*TODO*///				for (offs = 0;offs < 256;offs++)
/*TODO*///					tilemap_set_scrollx(K007342_tilemap[0],(offs + K007342_scrolly[0]) & 0xff,
/*TODO*///							K007342_scroll_ram[2*offs] + 256 * K007342_scroll_ram[2*offs+1]);
/*TODO*///				break;
/*TODO*///	
/*TODO*///			default:
/*TODO*///	usrintf_showmessage("unknown scroll ctrl %02x",K007342_regs[2] & 0x1c);
/*TODO*///				break;
/*TODO*///		}
/*TODO*///	
/*TODO*///		tilemap_set_scrollx(K007342_tilemap[1],0,K007342_scrollx[1]);
/*TODO*///		tilemap_set_scrolly(K007342_tilemap[1],0,K007342_scrolly[1]);
/*TODO*///	
/*TODO*///		/* update all layers */
/*TODO*///		tilemap_0_preupdate(); tilemap_update(K007342_tilemap[0]);
/*TODO*///		tilemap_1_preupdate(); tilemap_update(K007342_tilemap[1]);
/*TODO*///	
/*TODO*///	#if 0
/*TODO*///		{
/*TODO*///			static int current_layer = 0;
/*TODO*///	
/*TODO*///			if (keyboard_pressed_memory(KEYCODE_Z)) current_layer = !current_layer;
/*TODO*///			tilemap_set_enable(K007342_tilemap[current_layer], 1);
/*TODO*///			tilemap_set_enable(K007342_tilemap[!current_layer], 0);
/*TODO*///	
/*TODO*///			usrintf_showmessage("regs:%02x %02x %02x %02x-%02x %02x %02x %02x:%02x",
/*TODO*///				K007342_regs[0], K007342_regs[1], K007342_regs[2], K007342_regs[3],
/*TODO*///				K007342_regs[4], K007342_regs[5], K007342_regs[6], K007342_regs[7],
/*TODO*///				current_layer);
/*TODO*///		}
/*TODO*///	#endif
/*TODO*///	}
/*TODO*///	
/*TODO*///	void K007342_tilemap_set_enable(int tilemap, int enable)
/*TODO*///	{
/*TODO*///		tilemap_set_enable(K007342_tilemap[tilemap], enable);
/*TODO*///	}
/*TODO*///	
/*TODO*///	void K007342_tilemap_draw(struct osd_bitmap *bitmap,int num,int flags)
/*TODO*///	{
/*TODO*///		tilemap_draw(bitmap,K007342_tilemap[num],flags);
/*TODO*///	}
/*TODO*///	
/*TODO*///	int K007342_is_INT_enabled(void)
/*TODO*///	{
/*TODO*///		return K007342_int_enabled;
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static struct GfxElement *K007420_gfx;
/*TODO*///	static void (*K007420_callback)(int *code,int *color);
/*TODO*///	static UBytePtr K007420_ram;
/*TODO*///	
/*TODO*///	int K007420_vh_start(int gfxnum, void (*callback)(int *code,int *color))
/*TODO*///	{
/*TODO*///		K007420_gfx = Machine.gfx[gfxnum];
/*TODO*///		K007420_callback = callback;
/*TODO*///		K007420_ram = malloc(0x200);
/*TODO*///		if (!K007420_ram) return 1;
/*TODO*///	
/*TODO*///		memset(K007420_ram,0,0x200);
/*TODO*///	
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static VhStopPtr K007420_vh_stop = new VhStopPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		free(K007420_ram);
/*TODO*///		K007420_ram = 0;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K007420_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		return K007420_ram[offset];
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K007420_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		K007420_ram[offset] = data;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	/*
/*TODO*///	 * Sprite Format
/*TODO*///	 * ------------------
/*TODO*///	 *
/*TODO*///	 * Byte | Bit(s)   | Use
/*TODO*///	 * -----+-76543210-+----------------
/*TODO*///	 *   0  | xxxxxxxx | y position
/*TODO*///	 *   1  | xxxxxxxx | sprite code (low 8 bits)
/*TODO*///	 *   2  | xxxxxxxx | depends on external conections. Usually banking
/*TODO*///	 *   3  | xxxxxxxx | x position (low 8 bits)
/*TODO*///	 *   4  | x------- | x position (high bit)
/*TODO*///	 *   4  | -xxx---- | sprite size 000=16x16 001=8x16 010=16x8 011=8x8 100=32x32
/*TODO*///	 *   4  | ----x--- | flip y
/*TODO*///	 *   4  | -----x-- | flip x
/*TODO*///	 *   4  | ------xx | zoom (bits 8 & 9)
/*TODO*///	 *   5  | xxxxxxxx | zoom (low 8 bits)  0x080 = normal, < 0x80 enlarge, > 0x80 reduce
/*TODO*///	 *   6  | xxxxxxxx | unused
/*TODO*///	 *   7  | xxxxxxxx | unused
/*TODO*///	 */
/*TODO*///	
/*TODO*///	void K007420_sprites_draw(struct osd_bitmap *bitmap)
/*TODO*///	{
/*TODO*///	#define K007420_SPRITERAM_SIZE 0x200
/*TODO*///		int offs;
/*TODO*///	
/*TODO*///		for (offs = K007420_SPRITERAM_SIZE - 8; offs >= 0; offs -= 8)
/*TODO*///		{
/*TODO*///			int ox,oy,code,color,flipx,flipy,zoom,w,h,x,y;
/*TODO*///			static int xoffset[4] = { 0, 1, 4, 5 };
/*TODO*///			static int yoffset[4] = { 0, 2, 8, 10 };
/*TODO*///	
/*TODO*///			code = K007420_ram[offs+1];
/*TODO*///			color = K007420_ram[offs+2];
/*TODO*///			ox = K007420_ram[offs+3] - ((K007420_ram[offs+4] & 0x80) << 1);
/*TODO*///			oy = 256 - K007420_ram[offs+0];
/*TODO*///			flipx = K007420_ram[offs+4] & 0x04;
/*TODO*///			flipy = K007420_ram[offs+4] & 0x08;
/*TODO*///	
/*TODO*///			(*K007420_callback)(&code,&color);
/*TODO*///	
/*TODO*///			/* kludge for rock'n'rage */
/*TODO*///			if ((K007420_ram[offs+4] == 0x40) && (K007420_ram[offs+1] == 0xff) &&
/*TODO*///				(K007420_ram[offs+2] == 0x00) && (K007420_ram[offs+5] == 0xf0)) continue;
/*TODO*///	
/*TODO*///			/* 0x080 = normal scale, 0x040 = double size, 0x100 half size */
/*TODO*///			zoom = K007420_ram[offs+5] | ((K007420_ram[offs+4] & 0x03) << 8);
/*TODO*///			if (!zoom) continue;
/*TODO*///			zoom = 0x10000 * 128 / zoom;
/*TODO*///	
/*TODO*///			switch (K007420_ram[offs+4] & 0x70)
/*TODO*///			{
/*TODO*///				case 0x30: w = h = 1; break;
/*TODO*///				case 0x20: w = 2; h = 1; code &= (~1); break;
/*TODO*///				case 0x10: w = 1; h = 2; code &= (~2); break;
/*TODO*///				case 0x00: w = h = 2; code &= (~3); break;
/*TODO*///				case 0x40: w = h = 4; code &= (~3); break;
/*TODO*///				default: w = 1; h = 1;
/*TODO*///	//logerror("Unknown sprite size %02x\n",(K007420_ram[offs+4] & 0x70)>>4);
/*TODO*///			}
/*TODO*///	
/*TODO*///			if (K007342_flipscreen != 0)
/*TODO*///			{
/*TODO*///				ox = 256 - ox - ((zoom * w + (1<<12)) >> 13);
/*TODO*///				oy = 256 - oy - ((zoom * h + (1<<12)) >> 13);
/*TODO*///				flipx = !flipx;
/*TODO*///				flipy = !flipy;
/*TODO*///			}
/*TODO*///	
/*TODO*///			if (zoom == 0x10000)
/*TODO*///			{
/*TODO*///				int sx,sy;
/*TODO*///	
/*TODO*///				for (y = 0;y < h;y++)
/*TODO*///				{
/*TODO*///					sy = oy + 8 * y;
/*TODO*///	
/*TODO*///					for (x = 0;x < w;x++)
/*TODO*///					{
/*TODO*///						int c = code;
/*TODO*///	
/*TODO*///						sx = ox + 8 * x;
/*TODO*///						if (flipx != 0) c += xoffset[(w-1-x)];
/*TODO*///						else c += xoffset[x];
/*TODO*///						if (flipy != 0) c += yoffset[(h-1-y)];
/*TODO*///						else c += yoffset[y];
/*TODO*///	
/*TODO*///						drawgfx(bitmap,K007420_gfx,
/*TODO*///							c,
/*TODO*///							color,
/*TODO*///							flipx,flipy,
/*TODO*///							sx,sy,
/*TODO*///							&Machine.visible_area,TRANSPARENCY_PEN,0);
/*TODO*///	
/*TODO*///						if (K007342_regs[2] & 0x80)
/*TODO*///							drawgfx(bitmap,K007420_gfx,
/*TODO*///								c,
/*TODO*///								color,
/*TODO*///								flipx,flipy,
/*TODO*///								sx,sy-256,
/*TODO*///								&Machine.visible_area,TRANSPARENCY_PEN,0);
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///			else
/*TODO*///			{
/*TODO*///				int sx,sy,zw,zh;
/*TODO*///				for (y = 0;y < h;y++)
/*TODO*///				{
/*TODO*///					sy = oy + ((zoom * y + (1<<12)) >> 13);
/*TODO*///					zh = (oy + ((zoom * (y+1) + (1<<12)) >> 13)) - sy;
/*TODO*///	
/*TODO*///					for (x = 0;x < w;x++)
/*TODO*///					{
/*TODO*///						int c = code;
/*TODO*///	
/*TODO*///						sx = ox + ((zoom * x + (1<<12)) >> 13);
/*TODO*///						zw = (ox + ((zoom * (x+1) + (1<<12)) >> 13)) - sx;
/*TODO*///						if (flipx != 0) c += xoffset[(w-1-x)];
/*TODO*///						else c += xoffset[x];
/*TODO*///						if (flipy != 0) c += yoffset[(h-1-y)];
/*TODO*///						else c += yoffset[y];
/*TODO*///	
/*TODO*///						drawgfxzoom(bitmap,K007420_gfx,
/*TODO*///							c,
/*TODO*///							color,
/*TODO*///							flipx,flipy,
/*TODO*///							sx,sy,
/*TODO*///							&Machine.visible_area,TRANSPARENCY_PEN,0,
/*TODO*///							(zw << 16) / 8,(zh << 16) / 8);
/*TODO*///	
/*TODO*///						if (K007342_regs[2] & 0x80)
/*TODO*///							drawgfxzoom(bitmap,K007420_gfx,
/*TODO*///								c,
/*TODO*///								color,
/*TODO*///								flipx,flipy,
/*TODO*///								sx,sy-256,
/*TODO*///								&Machine.visible_area,TRANSPARENCY_PEN,0,
/*TODO*///								(zw << 16) / 8,(zh << 16) / 8);
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	#if 0
/*TODO*///		{
/*TODO*///			static int current_sprite = 0;
/*TODO*///	
/*TODO*///			if (keyboard_pressed_memory(KEYCODE_Z)) current_sprite = (current_sprite+1) & ((K007420_SPRITERAM_SIZE/8)-1);
/*TODO*///			if (keyboard_pressed_memory(KEYCODE_X)) current_sprite = (current_sprite-1) & ((K007420_SPRITERAM_SIZE/8)-1);
/*TODO*///	
/*TODO*///			usrintf_showmessage("%02x:%02x %02x %02x %02x %02x %02x %02x %02x", current_sprite,
/*TODO*///				K007420_ram[(current_sprite*8)+0], K007420_ram[(current_sprite*8)+1],
/*TODO*///				K007420_ram[(current_sprite*8)+2], K007420_ram[(current_sprite*8)+3],
/*TODO*///				K007420_ram[(current_sprite*8)+4], K007420_ram[(current_sprite*8)+5],
/*TODO*///				K007420_ram[(current_sprite*8)+6], K007420_ram[(current_sprite*8)+7]);
/*TODO*///		}
/*TODO*///	#endif
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	
    public static abstract interface K052109_callbackProcPtr {

        public abstract void handler(int layer, int bank, int[] code, int[] color);
    }

    public static UBytePtr colorram, videoram1, videoram2;
    public static int layer;

    static int K052109_memory_region;
    static int K052109_gfxnum;
    static K052109_callbackProcPtr K052109_callback;
    static UBytePtr K052109_ram;
    static UBytePtr K052109_videoram_F, K052109_videoram2_F, K052109_colorram_F;
    static UBytePtr K052109_videoram_A, K052109_videoram2_A, K052109_colorram_A;
    static UBytePtr K052109_videoram_B, K052109_videoram2_B, K052109_colorram_B;
    static /*unsigned*/ char[] K052109_charrombank = new char[4];
    static int has_extra_video_ram;
    public static int K052109_RMRD_line;
    static int K052109_tileflip_enable;
    static int K052109_irq_enabled;
    static /*unsigned*/ char K052109_romsubbank, K052109_scrollctrl;
    static struct_tilemap[] K052109_tilemap = new struct_tilemap[3];

    static void tilemap0_preupdate() {
        colorram = K052109_colorram_F;
        videoram1 = K052109_videoram_F;
        videoram2 = K052109_videoram2_F;
        layer = 0;
    }

    static void tilemap1_preupdate() {
        colorram = K052109_colorram_A;
        videoram1 = K052109_videoram_A;
        videoram2 = K052109_videoram2_A;
        layer = 1;
    }

    static void tilemap2_preupdate() {
        colorram = K052109_colorram_B;
        videoram1 = K052109_videoram_B;
        videoram2 = K052109_videoram2_B;
        layer = 2;
    }

    public static GetTileInfoPtr K052109_get_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int flipy = 0;
            int[] code = new int[1];
            int[] color = new int[1];
            code[0] = videoram1.read(tile_index) + 256 * videoram2.read(tile_index);
            color[0] = colorram.read(tile_index);
            int bank = K052109_charrombank[(color[0] & 0x0c) >> 2] & 0xFF;
            if (has_extra_video_ram != 0) {
                bank = (color[0] & 0x0c) >> 2;
                /* kludge for X-Men */

            }
            color[0] = (color[0] & 0xf3) | ((bank & 0x03) << 2);
            bank >>= 2;

            flipy = color[0] & 0x02;

            tile_info.u32_flags = 0;

            K052109_callback.handler(layer, bank, code, color);///(*K052109_callback)(layer,bank,&code,&color);

            SET_TILE_INFO(K052109_gfxnum, code[0], color[0]);

            /* if the callback set flip X but it is not enabled, turn it off */
            if ((K052109_tileflip_enable & 1) == 0) {
                tile_info.u32_flags &= ~TILE_FLIPX;
            }

            /* if flip Y is enabled and the attribute but is set, turn it on */
            if (flipy != 0 && (K052109_tileflip_enable & 2) != 0) {
                tile_info.u32_flags |= TILE_FLIPY;
            }
        }
    };

    public static int K052109_vh_start(int gfx_memory_region, int plane0, int plane1, int plane2, int plane3, K052109_callbackProcPtr callback) {
        int gfx_index;
        GfxLayout charlayout = new GfxLayout(
                8, 8,
                0, /* filled in later */
                4,
                new int[]{0, 0, 0, 0}, /* filled in later */
                new int[]{0, 1, 2, 3, 4, 5, 6, 7},
                new int[]{0 * 32, 1 * 32, 2 * 32, 3 * 32, 4 * 32, 5 * 32, 6 * 32, 7 * 32},
                32 * 8
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
        charlayout.total = memory_region_length(gfx_memory_region) / 32;
        charlayout.planeoffset[0] = plane3 * 8;
        charlayout.planeoffset[1] = plane2 * 8;
        charlayout.planeoffset[2] = plane1 * 8;
        charlayout.planeoffset[3] = plane0 * 8;

        /* decode the graphics */
        Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region), charlayout);
        if (Machine.gfx[gfx_index] == null) {
            return 1;
        }

        /* set the color information */
        Machine.gfx[gfx_index].colortable = new UShortArray(Machine.remapped_colortable);
        Machine.gfx[gfx_index].total_colors = Machine.drv.color_table_len / 16;

        K052109_memory_region = gfx_memory_region;
        K052109_gfxnum = gfx_index;
        K052109_callback = callback;
        K052109_RMRD_line = CLEAR_LINE;

        has_extra_video_ram = 0;

        K052109_tilemap[0] = tilemap_create(K052109_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 8, 8, 64, 32);
        K052109_tilemap[1] = tilemap_create(K052109_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 8, 8, 64, 32);
        K052109_tilemap[2] = tilemap_create(K052109_get_tile_info, tilemap_scan_rows, TILEMAP_TRANSPARENT, 8, 8, 64, 32);

        K052109_ram = new UBytePtr(0x6000);

        if (K052109_ram == null || K052109_tilemap[0] == null || K052109_tilemap[1] == null || K052109_tilemap[2] == null) {
            K052109_vh_stop();
            return 1;
        }

        for (int i = 0; i < 0x6000; i++) {
            K052109_ram.write(i, 0);//memset(K052109_ram,0,0x6000);
        }
        K052109_colorram_F = new UBytePtr(K052109_ram, 0x0000);
        K052109_colorram_A = new UBytePtr(K052109_ram, 0x0800);
        K052109_colorram_B = new UBytePtr(K052109_ram, 0x1000);
        K052109_videoram_F = new UBytePtr(K052109_ram, 0x2000);
        K052109_videoram_A = new UBytePtr(K052109_ram, 0x2800);
        K052109_videoram_B = new UBytePtr(K052109_ram, 0x3000);
        K052109_videoram2_F = new UBytePtr(K052109_ram, 0x4000);
        K052109_videoram2_A = new UBytePtr(K052109_ram, 0x4800);
        K052109_videoram2_B = new UBytePtr(K052109_ram, 0x5000);

        K052109_tilemap[0].transparent_pen = 0;
        K052109_tilemap[1].transparent_pen = 0;
        K052109_tilemap[2].transparent_pen = 0;

        return 0;
    }

    public static void K052109_vh_stop() {
        K052109_ram = null;
    }
    public static ReadHandlerPtr K052109_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (K052109_RMRD_line == CLEAR_LINE) {
                if ((offset & 0x1fff) >= 0x1800) {
                    if (offset >= 0x180c && offset < 0x1834) {
                        /* A y scroll */                    } else if (offset >= 0x1a00 && offset < 0x1c00) {
                        /* A x scroll */                    } else if (offset == 0x1d00) {
                        /* read for bitwise operations before writing */                    } else if (offset >= 0x380c && offset < 0x3834) {
                        /* B y scroll */                    } else if (offset >= 0x3a00 && offset < 0x3c00) {
                        /* B x scroll */                    } else {
                        logerror("%04x: read from unknown 052109 address %04x\n", cpu_get_pc(), offset);
                    }
                }

                return K052109_ram.read(offset);
            } else /* Punk Shot and TMNT read from 0000-1fff, Aliens from 2000-3fff */ {
                int[] code = new int[1];
                int[] color = new int[1];
                code[0] = (offset & 0x1fff) >> 5;
                color[0] = K052109_romsubbank;
                int bank = (K052109_charrombank[(color[0] & 0x0c) >> 2] >> 2) & 0xFF;
                /* discard low bits (TMNT) */

                int addr;

                if (has_extra_video_ram != 0) {
                    code[0] |= color[0] << 8;
                    /* kludge for X-Men */

                } else {
                    K052109_callback.handler(0, bank, code, color);//(*K052109_callback)(0,bank,&code,&color);
                }
                addr = (code[0] << 5) + (offset & 0x1f);
                addr &= memory_region_length(K052109_memory_region) - 1;

                return memory_region(K052109_memory_region).read(addr);
            }
        }
    };
    public static WriteHandlerPtr K052109_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if ((offset & 0x1fff) < 0x1800) /* tilemap RAM */ {
                if (K052109_ram.read(offset) != data) {
                    if (offset >= 0x4000) {
                        has_extra_video_ram = 1;
                        /* kludge for X-Men */

                    }
                    K052109_ram.write(offset, data);
                    tilemap_mark_tile_dirty(K052109_tilemap[(offset & 0x1800) >> 11], offset & 0x7ff);
                }
            } else /* control registers */ {
                K052109_ram.write(offset, data);

                if (offset >= 0x180c && offset < 0x1834) {
                    /* A y scroll */                } else if (offset >= 0x1a00 && offset < 0x1c00) {
                    /* A x scroll */                } else if (offset == 0x1c80) {
                    if (K052109_scrollctrl != data) {
                        logerror("%04x: rowscrollcontrol = %02x\n", cpu_get_pc(), data);
                        K052109_scrollctrl = (char) (data & 0xFF);
                    }
                } else if (offset == 0x1d00) {
                    //#if VERBOSE
                    //if (errorlog) fprintf(errorlog,"%04x: 052109 register 1d00 = %02x\n",cpu_get_pc(),data);
                    //#endif
                    /* bit 2 = irq enable */
 /* the custom chip can also generate NMI and FIRQ, for use with a 6809 */
                    K052109_irq_enabled = data & 0x04;
                } else if (offset == 0x1d80) {
                    int dirty = 0;

                    if (K052109_charrombank[0] != (data & 0x0f)) {
                        dirty |= 1;
                    }
                    if (K052109_charrombank[1] != ((data >> 4) & 0x0f)) {
                        dirty |= 2;
                    }
                    if (dirty != 0) {
                        int i;

                        K052109_charrombank[0] = (char) ((data & 0x0f) & 0xFF);
                        K052109_charrombank[1] = (char) (((data >> 4) & 0x0f) & 0xFF);

                        for (i = 0; i < 0x1800; i++) {
                            int bank = (K052109_ram.read(i) & 0x0c) >> 2;
                            if ((bank == 0 && (dirty & 1) != 0) || (bank == 1 && (dirty & 2) != 0)) {
                                tilemap_mark_tile_dirty(K052109_tilemap[(i & 0x1800) >> 11], i & 0x7ff);
                            }
                        }
                    }
                } else if (offset == 0x1e00) {
                    logerror("%04x: 052109 register 1e00 = %02x\n", cpu_get_pc(), data);
                    K052109_romsubbank = (char) (data & 0xFF);
                } else if (offset == 0x1e80) {
                    if ((data & 0xfe) != 0) {
                        logerror("%04x: 052109 register 1e80 = %02x\n", cpu_get_pc(), data);
                    }
                    tilemap_set_flip(K052109_tilemap[0], (data & 1) != 0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
                    tilemap_set_flip(K052109_tilemap[1], (data & 1) != 0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
                    tilemap_set_flip(K052109_tilemap[2], (data & 1) != 0 ? (TILEMAP_FLIPY | TILEMAP_FLIPX) : 0);
                    if (K052109_tileflip_enable != ((data & 0x06) >> 1)) {
                        K052109_tileflip_enable = ((data & 0x06) >> 1);

                        tilemap_mark_all_tiles_dirty(K052109_tilemap[0]);
                        tilemap_mark_all_tiles_dirty(K052109_tilemap[1]);
                        tilemap_mark_all_tiles_dirty(K052109_tilemap[2]);
                    }
                } else if (offset == 0x1f00) {
                    int dirty = 0;

                    if (K052109_charrombank[2] != (data & 0x0f)) {
                        dirty |= 1;
                    }
                    if (K052109_charrombank[3] != ((data >> 4) & 0x0f)) {
                        dirty |= 2;
                    }
                    if (dirty != 0) {
                        int i;

                        K052109_charrombank[2] = (char) ((data & 0x0f) & 0xFF);
                        K052109_charrombank[3] = (char) (((data >> 4) & 0x0f) & 0xFF);

                        for (i = 0; i < 0x1800; i++) {
                            int bank = (K052109_ram.read(i) & 0x0c) >> 2;
                            if ((bank == 2 && (dirty & 1) != 0) || (bank == 3 && (dirty & 2) != 0)) {
                                tilemap_mark_tile_dirty(K052109_tilemap[(i & 0x1800) >> 11], i & 0x7ff);
                            }
                        }
                    }
                } else if (offset >= 0x380c && offset < 0x3834) {
                    /* B y scroll */                } else if (offset >= 0x3a00 && offset < 0x3c00) {
                    /* B x scroll */                } else {
                    logerror("%04x: write %02x to unknown 052109 address %04x\n", cpu_get_pc(), data, offset);
                }
            }
        }
    };

    public static void K052109_tilemap_update() {

        if ((K052109_scrollctrl & 0x03) == 0x02) {
            int xscroll, yscroll, offs;
            UBytePtr scrollram = new UBytePtr(K052109_ram, 0x1a00);

            tilemap_set_scroll_rows(K052109_tilemap[1], 256);
            tilemap_set_scroll_cols(K052109_tilemap[1], 1);
            yscroll = K052109_ram.read(0x180c);
            tilemap_set_scrolly(K052109_tilemap[1], 0, yscroll);
            for (offs = 0; offs < 256; offs++) {
                xscroll = scrollram.read(2 * (offs & 0xfff8) + 0) + 256 * scrollram.read(2 * (offs & 0xfff8) + 1);
                xscroll -= 6;
                tilemap_set_scrollx(K052109_tilemap[1], (offs + yscroll) & 0xff, xscroll);
            }
        } else if ((K052109_scrollctrl & 0x03) == 0x03) {
            int xscroll, yscroll, offs;
            UBytePtr scrollram = new UBytePtr(K052109_ram, 0x1a00);

            tilemap_set_scroll_rows(K052109_tilemap[1], 256);
            tilemap_set_scroll_cols(K052109_tilemap[1], 1);
            yscroll = K052109_ram.read(0x180c);
            tilemap_set_scrolly(K052109_tilemap[1], 0, yscroll);
            for (offs = 0; offs < 256; offs++) {
                xscroll = scrollram.read(2 * offs + 0) + 256 * scrollram.read(2 * offs + 1);
                xscroll -= 6;
                tilemap_set_scrollx(K052109_tilemap[1], (offs + yscroll) & 0xff, xscroll);
            }
        } else if ((K052109_scrollctrl & 0x04) == 0x04) {
            int xscroll, yscroll, offs;
            UBytePtr scrollram = new UBytePtr(K052109_ram, 0x1800);

            tilemap_set_scroll_rows(K052109_tilemap[1], 1);
            tilemap_set_scroll_cols(K052109_tilemap[1], 512);
            xscroll = K052109_ram.read(0x1a00) + 256 * K052109_ram.read(0x1a01);
            xscroll -= 6;
            tilemap_set_scrollx(K052109_tilemap[1], 0, xscroll);
            for (offs = 0; offs < 512; offs++) {
                yscroll = scrollram.read(offs / 8);
                tilemap_set_scrolly(K052109_tilemap[1], (offs + xscroll) & 0x1ff, yscroll);
            }
        } else {
            int xscroll, yscroll;
            UBytePtr scrollram = new UBytePtr(K052109_ram, 0x1a00);

            tilemap_set_scroll_rows(K052109_tilemap[1], 1);
            tilemap_set_scroll_cols(K052109_tilemap[1], 1);
            xscroll = scrollram.read(0) + 256 * scrollram.read(1);
            xscroll -= 6;
            yscroll = K052109_ram.read(0x180c);
            tilemap_set_scrollx(K052109_tilemap[1], 0, xscroll);
            tilemap_set_scrolly(K052109_tilemap[1], 0, yscroll);
        }

        if ((K052109_scrollctrl & 0x18) == 0x10) {
            int xscroll, yscroll, offs;
            UBytePtr scrollram = new UBytePtr(K052109_ram, 0x3a00);

            tilemap_set_scroll_rows(K052109_tilemap[2], 256);
            tilemap_set_scroll_cols(K052109_tilemap[2], 1);
            yscroll = K052109_ram.read(0x380c);
            tilemap_set_scrolly(K052109_tilemap[2], 0, yscroll);
            for (offs = 0; offs < 256; offs++) {
                xscroll = scrollram.read(2 * (offs & 0xfff8) + 0) + 256 * scrollram.read(2 * (offs & 0xfff8) + 1);
                xscroll -= 6;
                tilemap_set_scrollx(K052109_tilemap[2], (offs + yscroll) & 0xff, xscroll);
            }
        } else if ((K052109_scrollctrl & 0x18) == 0x18) {
            int xscroll, yscroll, offs;
            UBytePtr scrollram = new UBytePtr(K052109_ram, 0x3a00);

            tilemap_set_scroll_rows(K052109_tilemap[2], 256);
            tilemap_set_scroll_cols(K052109_tilemap[2], 1);
            yscroll = K052109_ram.read(0x380c);
            tilemap_set_scrolly(K052109_tilemap[2], 0, yscroll);
            for (offs = 0; offs < 256; offs++) {
                xscroll = scrollram.read(2 * offs + 0) + 256 * scrollram.read(2 * offs + 1);
                xscroll -= 6;
                tilemap_set_scrollx(K052109_tilemap[2], (offs + yscroll) & 0xff, xscroll);
            }
        } else if ((K052109_scrollctrl & 0x20) == 0x20) {
            int xscroll, yscroll, offs;
            UBytePtr scrollram = new UBytePtr(K052109_ram, 0x3800);

            tilemap_set_scroll_rows(K052109_tilemap[2], 1);
            tilemap_set_scroll_cols(K052109_tilemap[2], 512);
            xscroll = K052109_ram.read(0x3a00) + 256 * K052109_ram.read(0x3a01);
            xscroll -= 6;
            tilemap_set_scrollx(K052109_tilemap[2], 0, xscroll);
            for (offs = 0; offs < 512; offs++) {
                yscroll = scrollram.read(offs / 8);
                tilemap_set_scrolly(K052109_tilemap[2], (offs + xscroll) & 0x1ff, yscroll);
            }
        } else {
            int xscroll, yscroll;
            UBytePtr scrollram = new UBytePtr(K052109_ram, 0x3a00);

            tilemap_set_scroll_rows(K052109_tilemap[2], 1);
            tilemap_set_scroll_cols(K052109_tilemap[2], 1);
            xscroll = scrollram.read(0) + 256 * scrollram.read(1);
            xscroll -= 6;
            yscroll = K052109_ram.read(0x380c);
            tilemap_set_scrollx(K052109_tilemap[2], 0, xscroll);
            tilemap_set_scrolly(K052109_tilemap[2], 0, yscroll);
        }

        tilemap0_preupdate();
        tilemap_update(K052109_tilemap[0]);
        tilemap1_preupdate();
        tilemap_update(K052109_tilemap[1]);
        tilemap2_preupdate();
        tilemap_update(K052109_tilemap[2]);

        /*#ifdef MAME_DEBUG
         if ((K052109_scrollctrl & 0x03) == 0x01 ||
         (K052109_scrollctrl & 0x18) == 0x08 ||
         ((K052109_scrollctrl & 0x04) && (K052109_scrollctrl & 0x03)) ||
         ((K052109_scrollctrl & 0x20) && (K052109_scrollctrl & 0x18)) ||
         (K052109_scrollctrl & 0xc0) != 0)
         usrintf_showmessage("scrollcontrol = %02x",K052109_scrollctrl);
         #endif*/
    }

    public static void K052109_tilemap_draw(osd_bitmap bitmap, int num, int flags) {
        tilemap_draw(bitmap, K052109_tilemap[num], flags);
    }

    public static int K052109_is_IRQ_enabled() {
        return K052109_irq_enabled;
    }

    public static abstract interface K051960_callbackProcPtr {

        public abstract void handler(int[] code, int[] color, int[] priority, int[] shadow);
    }

    static int K051960_memory_region;
    static GfxElement K051960_gfx;
    static K051960_callbackProcPtr K051960_callback;//static void (*K051960_callback)(int *code,int *color,int *priority);
    static int K051960_romoffset;
    static int K051960_spriteflip, K051960_readroms;
    static /*unsigned*/ char[] K051960_spriterombank = new char[3];
    static UBytePtr K051960_ram;
    static int K051960_irq_enabled, K051960_nmi_enabled;

    public static int K051960_vh_start(int gfx_memory_region, int plane0, int plane1, int plane2, int plane3, K051960_callbackProcPtr callback) {
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
        spritelayout.planeoffset[0] = plane0 * 8;
        spritelayout.planeoffset[1] = plane1 * 8;
        spritelayout.planeoffset[2] = plane2 * 8;
        spritelayout.planeoffset[3] = plane3 * 8;

        /* decode the graphics */
        Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region), spritelayout);
        if (Machine.gfx[gfx_index] == null) {
            return 1;
        }

        /* set the color information */
        Machine.gfx[gfx_index].colortable = new UShortArray(Machine.remapped_colortable);
        Machine.gfx[gfx_index].total_colors = Machine.drv.color_table_len / 16;

        K051960_memory_region = gfx_memory_region;
        K051960_gfx = Machine.gfx[gfx_index];
        K051960_callback = callback;
        K051960_ram = new UBytePtr(0x400);
        if (K051960_ram == null) {
            return 1;
        }

        for (int i = 0; i < 0x400; i++) {
            K051960_ram.write(i, 0);//memset(K051960_ram,0,0x400);
        }
        return 0;
    }

    public static void K051960_vh_stop() {
        K051960_ram = null;
    }

    public static int K051960_fetchromdata(int _byte) {
        int[] code = new int[1];
        int[] color = new int[1];
        int[] pri = new int[1];
        int[] shadow = new int[1];
        int off1, addr;

        addr = K051960_romoffset + (K051960_spriterombank[0] << 8)
                + ((K051960_spriterombank[1] & 0x03) << 16);
        code[0] = (addr & 0x3ffe0) >> 5;
        off1 = addr & 0x1f;
        color[0] = ((K051960_spriterombank[1] & 0xfc) >> 2) + ((K051960_spriterombank[2] & 0x03) << 6);
        pri[0] = 0;
        shadow[0] = color[0] & 0x80;
        K051960_callback.handler(code, color, pri, shadow);//(*K051960_callback)(&code,&color,&pri);

        addr = (code[0] << 7) | (off1 << 2) | _byte;
        addr &= memory_region_length(K051960_memory_region) - 1;

        return memory_region(K051960_memory_region).read(addr);
    }

    public static ReadHandlerPtr K051960_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (K051960_readroms != 0) {
                /* the 051960 remembers the last address read and uses it when reading the sprite ROMs */
                K051960_romoffset = (offset & 0x3fc) >> 2;
                return K051960_fetchromdata(offset & 3);
                /* only 88 Games reads the ROMs from here */

            } else {
                return K051960_ram.read(offset);
            }
        }
    };
    public static WriteHandlerPtr K051960_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            K051960_ram.write(offset, data);
        }
    };

    	public static ReadHandlerPtr K051960_word_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return K051960_r.handler(offset + 1) | (K051960_r.handler(offset) << 8);
	} };
	
	public static WriteHandlerPtr K051960_word_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0xff000000) == 0)
			K051960_w.handler(offset,(data >> 8) & 0xff);
		if ((data & 0x00ff0000) == 0)
			K051960_w.handler(offset + 1,data & 0xff);
	} };
	
    static int K051937_counter;
    public static ReadHandlerPtr K051937_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (K051960_readroms != 0 && offset >= 4 && offset < 8) {
                return K051960_fetchromdata(offset & 3);
            } else {
                if (offset == 0) {


                    /* some games need bit 0 to pulse */
                    return (K051937_counter++) & 1;
                }
                logerror("%04x: read unknown 051937 address %x\n", cpu_get_pc(), offset);
                return 0;
            }
        }
    };
    public static WriteHandlerPtr K051937_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (offset == 0) {
                /* bit 0 is IRQ enable */
                K051960_irq_enabled = (data & 0x01);

                /* bit 1: probably FIRQ enable */

 /* bit 2 is NMI enable */
                K051960_nmi_enabled = (data & 0x04);

                /* bit 3 = flip screen */
                K051960_spriteflip = data & 0x08;

                /* bit 4 used by Devastators and TMNT, unknown */

 /* bit 5 = enable gfx ROM reading */
                K051960_readroms = data & 0x20;
//#if VERBOSE
//if (errorlog) fprintf(errorlog,"%04x: write %02x to 051937 address %x\n",cpu_get_pc(),data,offset);
//#endif
            } else if (offset >= 2 && offset < 5) {
                K051960_spriterombank[offset - 2] = (char) (data & 0xFF);
            } else {
                logerror("%04x: write %02x to unknown 051937 address %x\n", cpu_get_pc(), data, offset);
            }
        }
    };

    	
	public static ReadHandlerPtr K051937_word_r  = new ReadHandlerPtr() { public int handler(int offset)
	{
		return K051937_r.handler(offset + 1) | (K051937_r.handler(offset) << 8);
	} };
	
	public static WriteHandlerPtr K051937_word_w = new WriteHandlerPtr() {public void handler(int offset, int data)
	{
		if ((data & 0xff000000) == 0)
			K051937_w.handler(offset,(data >> 8) & 0xff);
		if ((data & 0x00ff0000) == 0)
			K051937_w.handler(offset + 1,data & 0xff);
	} };
	
	
/*TODO*///	/*
/*TODO*///	 * Sprite Format
/*TODO*///	 * ------------------
/*TODO*///	 *
/*TODO*///	 * Byte | Bit(s)   | Use
/*TODO*///	 * -----+-76543210-+----------------
/*TODO*///	 *   0  | x------- | active (show this sprite)
/*TODO*///	 *   0  | -xxxxxxx | priority order
/*TODO*///	 *   1  | xxx----- | sprite size (see below)
/*TODO*///	 *   1  | ---xxxxx | sprite code (high 5 bits)
/*TODO*///	 *   2  | xxxxxxxx | sprite code (low 8 bits)
/*TODO*///	 *   3  | xxxxxxxx | "color", but depends on external connections (see below)
/*TODO*///	 *   4  | xxxxxx-- | zoom y (0 = normal, >0 = shrink)
/*TODO*///	 *   4  | ------x- | flip y
/*TODO*///	 *   4  | -------x | y position (high bit)
/*TODO*///	 *   5  | xxxxxxxx | y position (low 8 bits)
/*TODO*///	 *   6  | xxxxxx-- | zoom x (0 = normal, >0 = shrink)
/*TODO*///	 *   6  | ------x- | flip x
/*TODO*///	 *   6  | -------x | x position (high bit)
/*TODO*///	 *   7  | xxxxxxxx | x position (low 8 bits)
/*TODO*///	 *
/*TODO*///	 * Example of "color" field for Punk Shot:
/*TODO*///	 *   3  | x------- | shadow
/*TODO*///	 *   3  | -xx----- | priority
/*TODO*///	 *   3  | ---x---- | use second gfx ROM bank
/*TODO*///	 *   3  | ----xxxx | color code
/*TODO*///	 *
/*TODO*///	 * shadow enables transparent shadows. Note that it applies to pen 0x0f ONLY.
/*TODO*///	 * The rest of the sprite remains normal.
/*TODO*///	 * Note that Aliens also uses the shadow bit to select the second sprite bank.
/*TODO*///	 */
/*TODO*///	
    public static void K051960_sprites_draw(osd_bitmap bitmap, int min_priority, int max_priority) {
        int NUM_SPRITES = 128;
        int offs, pri_code;
        int[] sortedlist = new int[NUM_SPRITES];

        for (offs = 0; offs < NUM_SPRITES; offs++) {
            sortedlist[offs] = -1;
        }

        /* prebuild a sorted table */
        for (offs = 0; offs < 0x400; offs += 8) {
            if ((K051960_ram.read(offs) & 0x80) != 0) {
                if (max_priority == -1) /* draw front to back when using priority buffer */ {
                    sortedlist[(K051960_ram.read(offs) & 0x7f) ^ 0x7f] = offs;
                } else {
                    sortedlist[K051960_ram.read(offs) & 0x7f] = offs;
                }
            }
        }
        for (pri_code = 0; pri_code < NUM_SPRITES; pri_code++) {
            int ox, oy, size, w, h, x, y, flipx, flipy, zoomx, zoomy;
            int[] code = new int[1];
            int[] color = new int[1];
            int[] pri = new int[1];
            int[] shadow = new int[1];

            int xoffset[] = {0, 1, 4, 5, 16, 17, 20, 21};
            int yoffset[] = {0, 2, 8, 10, 32, 34, 40, 42};
            int width[] = {1, 2, 1, 2, 4, 2, 4, 8};
            int height[] = {1, 1, 2, 2, 2, 4, 4, 8};

            offs = sortedlist[pri_code];
            if (offs == -1) {
                continue;
            }

            code[0] = K051960_ram.read(offs + 2) + ((K051960_ram.read(offs + 1) & 0x1f) << 8);
            color[0] = K051960_ram.read(offs + 3) & 0xff;
            pri[0] = 0;
            shadow[0] = color[0] & 0x80;
            K051960_callback.handler(code, color, pri, shadow);

            if (max_priority != -1) {
                if (pri[0] < min_priority || pri[0] > max_priority) {
                    continue;
                }
            }

            size = (K051960_ram.read(offs + 1) & 0xe0) >> 5;
            w = width[size];
            h = height[size];

            if (w >= 2) {
                code[0] &= ~0x01;
            }
            if (h >= 2) {
                code[0] &= ~0x02;
            }
            if (w >= 4) {
                code[0] &= ~0x04;
            }
            if (h >= 4) {
                code[0] &= ~0x08;
            }
            if (w >= 8) {
                code[0] &= ~0x10;
            }
            if (h >= 8) {
                code[0] &= ~0x20;
            }

            ox = (256 * K051960_ram.read(offs + 6) + K051960_ram.read(offs + 7)) & 0x01ff;
            oy = 256 - ((256 * K051960_ram.read(offs + 4) + K051960_ram.read(offs + 5)) & 0x01ff);
            flipx = K051960_ram.read(offs + 6) & 0x02;
            flipy = K051960_ram.read(offs + 4) & 0x02;
            zoomx = (K051960_ram.read(offs + 6) & 0xfc) >> 2;
            zoomy = (K051960_ram.read(offs + 4) & 0xfc) >> 2;
            zoomx = 0x10000 / 128 * (128 - zoomx);
            zoomy = 0x10000 / 128 * (128 - zoomy);

            if (K051960_spriteflip != 0) {
                ox = 512 - (zoomx * w >> 12) - ox;
                oy = 256 - (zoomy * h >> 12) - oy;
                flipx = NOT(flipx);
                flipy = NOT(flipy);
            }

            if (zoomx == 0x10000 && zoomy == 0x10000) {
                int sx, sy;

                for (y = 0; y < h; y++) {
                    sy = oy + 16 * y;

                    for (x = 0; x < w; x++) {
                        int c = code[0];

                        sx = ox + 16 * x;
                        if (flipx != 0) {
                            c += xoffset[(w - 1 - x)];
                        } else {
                            c += xoffset[x];
                        }
                        if (flipy != 0) {
                            c += yoffset[(h - 1 - y)];
                        } else {
                            c += yoffset[y];
                        }

                        /* hack to simulate shadow */
                        if (shadow[0] != 0) {
                            int o = K051960_gfx.colortable.read(16 * color[0] + 15);
                            K051960_gfx.colortable.write(16 * color[0] + 15, palette_transparent_pen);
                            if (max_priority == -1) {
                                pdrawgfx(bitmap, K051960_gfx,
                                        c,
                                        color[0],
                                        flipx, flipy,
                                        sx & 0x1ff, sy,
                                        Machine.visible_area, TRANSPARENCY_PENS, (cpu_getcurrentframe() & 1) != 0 ? 0x8001 : 0x0001, pri[0]);
                            } else {
                                drawgfx(bitmap, K051960_gfx,
                                        c,
                                        color[0],
                                        flipx, flipy,
                                        sx & 0x1ff, sy,
                                        Machine.visible_area, TRANSPARENCY_PENS, (cpu_getcurrentframe() & 1) != 0 ? 0x8001 : 0x0001);
                            }
                            K051960_gfx.colortable.write(16 * color[0] + 15, o);
                        } else {
                            if (max_priority == -1) {
                                pdrawgfx(bitmap, K051960_gfx,
                                        c,
                                        color[0],
                                        flipx, flipy,
                                        sx & 0x1ff, sy,
                                        Machine.visible_area, TRANSPARENCY_PEN, 0, pri[0]);
                            } else {
                                drawgfx(bitmap, K051960_gfx,
                                        c,
                                        color[0],
                                        flipx, flipy,
                                        sx & 0x1ff, sy,
                                        Machine.visible_area, TRANSPARENCY_PEN, 0);
                            }
                        }
                    }
                }
            } else {
                				int sx,sy,zw,zh;
	
				for (y = 0;y < h;y++)
				{
					sy = oy + ((zoomy * y + (1<<11)) >> 12);
					zh = (oy + ((zoomy * (y+1) + (1<<11)) >> 12)) - sy;
	
					for (x = 0;x < w;x++)
					{
						int c = code[0];
	
						sx = ox + ((zoomx * x + (1<<11)) >> 12);
						zw = (ox + ((zoomx * (x+1) + (1<<11)) >> 12)) - sx;
						if (flipx != 0) c += xoffset[(w-1-x)];
						else c += xoffset[x];
						if (flipy != 0) c += yoffset[(h-1-y)];
						else c += yoffset[y];
	
						/* hack to simulate shadow */
						if (shadow[0] != 0)
						{
							int o = K051960_gfx.colortable.read(16*color[0]+15);
							K051960_gfx.colortable.write(16*color[0]+15,palette_transparent_pen);
							if (max_priority == -1)
								pdrawgfxzoom(bitmap,K051960_gfx,
										c,
										color[0],
										flipx,flipy,
										sx & 0x1ff,sy,
										Machine.visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1)!=0 ? 0x8001 : 0x0001,
										(zw << 16) / 16,(zh << 16) / 16,pri[0]);
							else
								drawgfxzoom(bitmap,K051960_gfx,
										c,
										color[0],
										flipx,flipy,
										sx & 0x1ff,sy,
										Machine.visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1)!=0 ? 0x8001 : 0x0001,
										(zw << 16) / 16,(zh << 16) / 16);
							K051960_gfx.colortable.write(16*color[0]+15,o);
						}
						else
						{
							if (max_priority == -1)
								pdrawgfxzoom(bitmap,K051960_gfx,
										c,
										color[0],
										flipx,flipy,
										sx & 0x1ff,sy,
										Machine.visible_area,TRANSPARENCY_PEN,0,
										(zw << 16) / 16,(zh << 16) / 16,pri[0]);
							else
								drawgfxzoom(bitmap,K051960_gfx,
										c,
										color[0],
										flipx,flipy,
										sx & 0x1ff,sy,
										Machine.visible_area,TRANSPARENCY_PEN,0,
										(zw << 16) / 16,(zh << 16) / 16);
						}
					}
				}
            }
        }
    }

    public static void K051960_mark_sprites_colors() {
        int offs, i;

        /*unsigned short*/ char[] palette_map = new char[512];

        memset(palette_map, 0, sizeof(palette_map));

        /* sprites */
        for (offs = 0x400 - 8; offs >= 0; offs -= 8) {
            if ((K051960_ram.read(offs) & 0x80) != 0) {
                //int code,color,pri;
                int[] code = new int[1];
                int[] color = new int[1];
                int[] pri = new int[1];
                int[] shadow = new int[1];

                code[0] = K051960_ram.read(offs + 2) + ((K051960_ram.read(offs + 1) & 0x1f) << 8);
                color[0] = (K051960_ram.read(offs + 3) & 0xff);
                pri[0] = 0;
                shadow[0] = color[0] & 0x80;
                K051960_callback.handler(code, color, pri, shadow);//(*K051960_callback)(&code,&color,&pri);
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

    public static int K051960_is_IRQ_enabled() {
        return K051960_irq_enabled;
    }
    /*TODO*///	
/*TODO*///	int K051960_is_NMI_enabled(void)
/*TODO*///	{
/*TODO*///		return K051960_nmi_enabled;
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
    public static ReadHandlerPtr K052109_051960_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            if (K052109_RMRD_line == CLEAR_LINE) {
                if (offset >= 0x3800 && offset < 0x3808) {
                    return K051937_r.handler(offset - 0x3800);
                } else if (offset < 0x3c00) {
                    return K052109_r.handler(offset);
                } else {
                    return K051960_r.handler(offset - 0x3c00);
                }
            } else {
                return K052109_r.handler(offset);
            }
        }
    };
    public static WriteHandlerPtr K052109_051960_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            if (offset >= 0x3800 && offset < 0x3808) {
                K051937_w.handler(offset - 0x3800, data);
            } else if (offset < 0x3c00) {
                K052109_w.handler(offset, data);
            } else {
                K051960_w.handler(offset - 0x3c00, data);
            }
        }
    };

    /*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static int K053245_memory_region=2;
/*TODO*///	static struct GfxElement *K053245_gfx;
/*TODO*///	static void (*K053245_callback)(int *code,int *color,int *priority);
/*TODO*///	static int K053244_romoffset,K053244_rombank;
/*TODO*///	static int K053244_readroms;
/*TODO*///	static int K053245_flipscreenX,K053245_flipscreenY;
/*TODO*///	static int K053245_spriteoffsX,K053245_spriteoffsY;
/*TODO*///	static UBytePtr K053245_ram;
/*TODO*///	
/*TODO*///	int K053245_vh_start(int gfx_memory_region,int plane0,int plane1,int plane2,int plane3,
/*TODO*///			void (*callback)(int *code,int *color,int *priority))
/*TODO*///	{
/*TODO*///		int gfx_index;
/*TODO*///		static GfxLayout spritelayout = new GfxLayout
/*TODO*///		(
/*TODO*///			16,16,
/*TODO*///			0,				/* filled in later */
/*TODO*///			4,
/*TODO*///			new int[] { 0, 0, 0, 0 },	/* filled in later */
/*TODO*///			new int[] { 0, 1, 2, 3, 4, 5, 6, 7,
/*TODO*///					8*32+0, 8*32+1, 8*32+2, 8*32+3, 8*32+4, 8*32+5, 8*32+6, 8*32+7 },
/*TODO*///			new int[] { 0*32, 1*32, 2*32, 3*32, 4*32, 5*32, 6*32, 7*32,
/*TODO*///					16*32, 17*32, 18*32, 19*32, 20*32, 21*32, 22*32, 23*32 },
/*TODO*///			128*8
/*TODO*///		);
/*TODO*///	
/*TODO*///	
/*TODO*///		/* find first empty slot to decode gfx */
/*TODO*///		for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++)
/*TODO*///			if (Machine.gfx[gfx_index] == 0)
/*TODO*///				break;
/*TODO*///		if (gfx_index == MAX_GFX_ELEMENTS)
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		/* tweak the structure for the number of tiles we have */
/*TODO*///		spritelayout.total = memory_region_length(gfx_memory_region) / 128;
/*TODO*///		spritelayout.planeoffset[0] = plane3 * 8;
/*TODO*///		spritelayout.planeoffset[1] = plane2 * 8;
/*TODO*///		spritelayout.planeoffset[2] = plane1 * 8;
/*TODO*///		spritelayout.planeoffset[3] = plane0 * 8;
/*TODO*///	
/*TODO*///		/* decode the graphics */
/*TODO*///		Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region),&spritelayout);
/*TODO*///		if (!Machine.gfx[gfx_index])
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		/* set the color information */
/*TODO*///		Machine.gfx[gfx_index].colortable = Machine.remapped_colortable;
/*TODO*///		Machine.gfx[gfx_index].total_colors = Machine.drv.color_table_len / 16;
/*TODO*///	
/*TODO*///		K053245_memory_region = gfx_memory_region;
/*TODO*///		K053245_gfx = Machine.gfx[gfx_index];
/*TODO*///		K053245_callback = callback;
/*TODO*///		K053244_rombank = 0;
/*TODO*///		K053245_ram = malloc(0x800);
/*TODO*///		if (!K053245_ram) return 1;
/*TODO*///	
/*TODO*///		memset(K053245_ram,0,0x800);
/*TODO*///	
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static VhStopPtr K053245_vh_stop = new VhStopPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		free(K053245_ram);
/*TODO*///		K053245_ram = 0;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K053245_word_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		return READ_WORD(&K053245_ram[offset]);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K053245_word_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		COMBINE_WORD_MEM(&K053245_ram[offset],data);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K053245_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		int shift = ((offset & 1) ^ 1) << 3;
/*TODO*///		return (READ_WORD(&K053245_ram[offset & ~1]) >> shift) & 0xff;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K053245_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		int shift = ((offset & 1) ^ 1) << 3;
/*TODO*///		offset &= ~1;
/*TODO*///		COMBINE_WORD_MEM(&K053245_ram[offset],(0xff000000 >> shift) | ((data & 0xff) << shift));
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K053244_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		if (K053244_readroms && offset >= 0x0c && offset < 0x10)
/*TODO*///		{
/*TODO*///			int addr;
/*TODO*///	
/*TODO*///	
/*TODO*///			addr = 0x200000 * K053244_rombank + 4 * (K053244_romoffset & 0x7ffff) + ((offset & 3) ^ 1);
/*TODO*///			addr &= memory_region_length(K053245_memory_region)-1;
/*TODO*///	
/*TODO*///	#if 0
/*TODO*///		usrintf_showmessage("%04x: offset %02x addr %06x",cpu_get_pc(),offset&3,addr);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///			return memory_region(K053245_memory_region)[addr];
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///	logerror("%04x: read from unknown 053244 address %x\n",cpu_get_pc(),offset);
/*TODO*///			return 0;
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K053244_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		if (offset == 0x00)
/*TODO*///			K053245_spriteoffsX = (K053245_spriteoffsX & 0x00ff) | (data << 8);
/*TODO*///		else if (offset == 0x01)
/*TODO*///			K053245_spriteoffsX = (K053245_spriteoffsX & 0xff00) | data;
/*TODO*///		else if (offset == 0x02)
/*TODO*///			K053245_spriteoffsY = (K053245_spriteoffsY & 0x00ff) | (data << 8);
/*TODO*///		else if (offset == 0x03)
/*TODO*///			K053245_spriteoffsY = (K053245_spriteoffsY & 0xff00) | data;
/*TODO*///		else if (offset == 0x05)
/*TODO*///		{
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	if ((data & 0xc8) != 0)
/*TODO*///		usrintf_showmessage("053244 reg 05 = %02x",data);
/*TODO*///	#endif
/*TODO*///			/* bit 0/1 = flip screen */
/*TODO*///			K053245_flipscreenX = data & 0x01;
/*TODO*///			K053245_flipscreenY = data & 0x02;
/*TODO*///	
/*TODO*///			/* bit 2 = unknown, Parodius uses it */
/*TODO*///	
/*TODO*///			/* bit 4 = enable gfx ROM reading */
/*TODO*///			K053244_readroms = data & 0x10;
/*TODO*///	
/*TODO*///			/* bit 5 = unknown, Rollergames uses it */
/*TODO*///	#if VERBOSE
/*TODO*///	logerror("%04x: write %02x to 053244 address 5\n",cpu_get_pc(),data);
/*TODO*///	#endif
/*TODO*///		}
/*TODO*///		else if (offset >= 0x08 && offset < 0x0c)
/*TODO*///		{
/*TODO*///			offset = 8*((offset & 0x03) ^ 0x01);
/*TODO*///			K053244_romoffset = (K053244_romoffset & ~(0xff << offset)) | (data << offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		else
/*TODO*///	logerror("%04x: write %02x to unknown 053244 address %x\n",cpu_get_pc(),data,offset);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	void K053244_bankselect(int bank)   /* used by TMNT2 for ROM testing */
/*TODO*///	{
/*TODO*///		K053244_rombank = bank;
/*TODO*///	}
/*TODO*///	
/*TODO*///	/*
/*TODO*///	 * Sprite Format
/*TODO*///	 * ------------------
/*TODO*///	 *
/*TODO*///	 * Word | Bit(s)           | Use
/*TODO*///	 * -----+-fedcba9876543210-+----------------
/*TODO*///	 *   0  | x--------------- | active (show this sprite)
/*TODO*///	 *   0  | -x-------------- | maintain aspect ratio (when set, zoom y acts on both axis)
/*TODO*///	 *   0  | --x------------- | flip y
/*TODO*///	 *   0  | ---x------------ | flip x
/*TODO*///	 *   0  | ----xxxx-------- | sprite size (see below)
/*TODO*///	 *   0  | ---------xxxxxxx | priority order
/*TODO*///	 *   1  | --xxxxxxxxxxxxxx | sprite code. We use an additional bit in TMNT2, but this is
/*TODO*///	 *                           probably not accurate (protection related so we can't verify)
/*TODO*///	 *   2  | ------xxxxxxxxxx | y position
/*TODO*///	 *   3  | ------xxxxxxxxxx | x position
/*TODO*///	 *   4  | xxxxxxxxxxxxxxxx | zoom y (0x40 = normal, <0x40 = enlarge, >0x40 = reduce)
/*TODO*///	 *   5  | xxxxxxxxxxxxxxxx | zoom x (0x40 = normal, <0x40 = enlarge, >0x40 = reduce)
/*TODO*///	 *   6  | ------x--------- | mirror y (top half is drawn as mirror image of the bottom)
/*TODO*///	 *   6  | -------x-------- | mirror x (right half is drawn as mirror image of the left)
/*TODO*///	 *   6  | --------x------- | shadow
/*TODO*///	 *   6  | ---------xxxxxxx | "color", but depends on external connections
/*TODO*///	 *   7  | ---------------- |
/*TODO*///	 *
/*TODO*///	 * shadow enables transparent shadows. Note that it applies to pen 0x0f ONLY.
/*TODO*///	 * The rest of the sprite remains normal.
/*TODO*///	 */
/*TODO*///	
/*TODO*///	void K053245_sprites_draw(struct osd_bitmap *bitmap)
/*TODO*///	{
/*TODO*///	#define NUM_SPRITES 128
/*TODO*///		int offs,pri_code;
/*TODO*///		int sortedlist[NUM_SPRITES];
/*TODO*///	
/*TODO*///		for (offs = 0;offs < NUM_SPRITES;offs++)
/*TODO*///			sortedlist[offs] = -1;
/*TODO*///	
/*TODO*///		/* prebuild a sorted table */
/*TODO*///		for (offs = 0;offs < 0x800;offs += 16)
/*TODO*///		{
/*TODO*///			if (READ_WORD(&K053245_ram[offs]) & 0x8000)
/*TODO*///			{
/*TODO*///				sortedlist[READ_WORD(&K053245_ram[offs]) & 0x007f] = offs;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	
/*TODO*///		for (pri_code = NUM_SPRITES-1;pri_code >= 0;pri_code--)
/*TODO*///		{
/*TODO*///			int ox,oy,color,code,size,w,h,x,y,flipx,flipy,mirrorx,mirrory,zoomx,zoomy,pri;
/*TODO*///	
/*TODO*///	
/*TODO*///			offs = sortedlist[pri_code];
/*TODO*///			if (offs == -1) continue;
/*TODO*///	
/*TODO*///			/* the following changes the sprite draw order from
/*TODO*///				 0  1  4  5 16 17 20 21
/*TODO*///				 2  3  6  7 18 19 22 23
/*TODO*///				 8  9 12 13 24 25 28 29
/*TODO*///				10 11 14 15 26 27 30 31
/*TODO*///				32 33 36 37 48 49 52 53
/*TODO*///				34 35 38 39 50 51 54 55
/*TODO*///				40 41 44 45 56 57 60 61
/*TODO*///				42 43 46 47 58 59 62 63
/*TODO*///	
/*TODO*///				to
/*TODO*///	
/*TODO*///				 0  1  2  3  4  5  6  7
/*TODO*///				 8  9 10 11 12 13 14 15
/*TODO*///				16 17 18 19 20 21 22 23
/*TODO*///				24 25 26 27 28 29 30 31
/*TODO*///				32 33 34 35 36 37 38 39
/*TODO*///				40 41 42 43 44 45 46 47
/*TODO*///				48 49 50 51 52 53 54 55
/*TODO*///				56 57 58 59 60 61 62 63
/*TODO*///			*/
/*TODO*///	
/*TODO*///			/* NOTE: from the schematics, it looks like the top 2 bits should be ignored */
/*TODO*///			/* (there are not output pins for them), and probably taken from the "color" */
/*TODO*///			/* field to do bank switching. However this applies only to TMNT2, with its */
/*TODO*///			/* protection mcu creating the sprite table, so we don't know where to fetch */
/*TODO*///			/* the bits from. */
/*TODO*///			code = READ_WORD(&K053245_ram[offs+0x02]);
/*TODO*///			code = ((code & 0xffe1) + ((code & 0x0010) >> 2) + ((code & 0x0008) << 1)
/*TODO*///					 + ((code & 0x0004) >> 1) + ((code & 0x0002) << 2));
/*TODO*///			color = READ_WORD(&K053245_ram[offs+0x0c]) & 0x00ff;
/*TODO*///			pri = 0;
/*TODO*///	
/*TODO*///			(*K053245_callback)(&code,&color,&pri);
/*TODO*///	
/*TODO*///			size = (READ_WORD(&K053245_ram[offs]) & 0x0f00) >> 8;
/*TODO*///	
/*TODO*///			w = 1 << (size & 0x03);
/*TODO*///			h = 1 << ((size >> 2) & 0x03);
/*TODO*///	
/*TODO*///			/* zoom control:
/*TODO*///			   0x40 = normal scale
/*TODO*///			  <0x40 enlarge (0x20 = double size)
/*TODO*///			  >0x40 reduce (0x80 = half size)
/*TODO*///			*/
/*TODO*///			zoomy = READ_WORD(&K053245_ram[offs+0x08]);
/*TODO*///			if (zoomy > 0x2000) continue;
/*TODO*///			if (zoomy != 0) zoomy = (0x400000+zoomy/2) / zoomy;
/*TODO*///			else zoomy = 2 * 0x400000;
/*TODO*///			if ((READ_WORD(&K053245_ram[offs]) & 0x4000) == 0)
/*TODO*///			{
/*TODO*///				zoomx = READ_WORD(&K053245_ram[offs+0x0a]);
/*TODO*///				if (zoomx > 0x2000) continue;
/*TODO*///				if (zoomx != 0) zoomx = (0x400000+zoomx/2) / zoomx;
/*TODO*///	//			else zoomx = 2 * 0x400000;
/*TODO*///	else zoomx = zoomy; /* workaround for TMNT2 */
/*TODO*///			}
/*TODO*///			else zoomx = zoomy;
/*TODO*///	
/*TODO*///			ox = READ_WORD(&K053245_ram[offs+0x06]) + K053245_spriteoffsX;
/*TODO*///			oy = READ_WORD(&K053245_ram[offs+0x04]);
/*TODO*///	
/*TODO*///			flipx = READ_WORD(&K053245_ram[offs]) & 0x1000;
/*TODO*///			flipy = READ_WORD(&K053245_ram[offs]) & 0x2000;
/*TODO*///			mirrorx = READ_WORD(&K053245_ram[offs+0x0c]) & 0x0100;
/*TODO*///			mirrory = READ_WORD(&K053245_ram[offs+0x0c]) & 0x0200;
/*TODO*///	
/*TODO*///			if (K053245_flipscreenX != 0)
/*TODO*///			{
/*TODO*///				ox = 512 - ox;
/*TODO*///				if (!mirrorx) flipx = !flipx;
/*TODO*///			}
/*TODO*///			if (K053245_flipscreenY != 0)
/*TODO*///			{
/*TODO*///				oy = -oy;
/*TODO*///				if (!mirrory) flipy = !flipy;
/*TODO*///			}
/*TODO*///	
/*TODO*///			ox = (ox + 0x5d) & 0x3ff;
/*TODO*///			if (ox >= 768) ox -= 1024;
/*TODO*///			oy = (-(oy + K053245_spriteoffsY + 0x07)) & 0x3ff;
/*TODO*///			if (oy >= 640) oy -= 1024;
/*TODO*///	
/*TODO*///			/* the coordinates given are for the *center* of the sprite */
/*TODO*///			ox -= (zoomx * w) >> 13;
/*TODO*///			oy -= (zoomy * h) >> 13;
/*TODO*///	
/*TODO*///			for (y = 0;y < h;y++)
/*TODO*///			{
/*TODO*///				int sx,sy,zw,zh;
/*TODO*///	
/*TODO*///				sy = oy + ((zoomy * y + (1<<11)) >> 12);
/*TODO*///				zh = (oy + ((zoomy * (y+1) + (1<<11)) >> 12)) - sy;
/*TODO*///	
/*TODO*///				for (x = 0;x < w;x++)
/*TODO*///				{
/*TODO*///					int c,fx,fy;
/*TODO*///	
/*TODO*///					sx = ox + ((zoomx * x + (1<<11)) >> 12);
/*TODO*///					zw = (ox + ((zoomx * (x+1) + (1<<11)) >> 12)) - sx;
/*TODO*///					c = code;
/*TODO*///					if (mirrorx != 0)
/*TODO*///					{
/*TODO*///						if ((flipx == 0) ^ (2*x < w))
/*TODO*///						{
/*TODO*///							/* mirror left/right */
/*TODO*///							c += (w-x-1);
/*TODO*///							fx = 1;
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							c += x;
/*TODO*///							fx = 0;
/*TODO*///						}
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						if (flipx != 0) c += w-1-x;
/*TODO*///						else c += x;
/*TODO*///						fx = flipx;
/*TODO*///					}
/*TODO*///					if (mirrory != 0)
/*TODO*///					{
/*TODO*///						if ((flipy == 0) ^ (2*y >= h))
/*TODO*///						{
/*TODO*///							/* mirror top/bottom */
/*TODO*///							c += 8*(h-y-1);
/*TODO*///							fy = 1;
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							c += 8*y;
/*TODO*///							fy = 0;
/*TODO*///						}
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						if (flipy != 0) c += 8*(h-1-y);
/*TODO*///						else c += 8*y;
/*TODO*///						fy = flipy;
/*TODO*///					}
/*TODO*///	
/*TODO*///					/* the sprite can start at any point in the 8x8 grid, but it must stay */
/*TODO*///					/* in a 64 entries window, wrapping around at the edges. The animation */
/*TODO*///					/* at the end of the saloon level in SUnset Riders breaks otherwise. */
/*TODO*///					c = (c & 0x3f) | (code & ~0x3f);
/*TODO*///	
/*TODO*///					if (zoomx == 0x10000 && zoomy == 0x10000)
/*TODO*///					{
/*TODO*///						/* hack to simulate shadow */
/*TODO*///						if (READ_WORD(&K053245_ram[offs+0x0c]) & 0x0080)
/*TODO*///						{
/*TODO*///							int o = K053245_gfx.colortable[16*color+15];
/*TODO*///							K053245_gfx.colortable[16*color+15] = palette_transparent_pen;
/*TODO*///							pdrawgfx(bitmap,K053245_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									fx,fy,
/*TODO*///									sx,sy,
/*TODO*///									&Machine.visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,pri);
/*TODO*///							K053245_gfx.colortable[16*color+15] = o;
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							pdrawgfx(bitmap,K053245_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									fx,fy,
/*TODO*///									sx,sy,
/*TODO*///									&Machine.visible_area,TRANSPARENCY_PEN,0,pri);
/*TODO*///						}
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						/* hack to simulate shadow */
/*TODO*///						if (READ_WORD(&K053245_ram[offs+0x0c]) & 0x0080)
/*TODO*///						{
/*TODO*///							int o = K053245_gfx.colortable[16*color+15];
/*TODO*///							K053245_gfx.colortable[16*color+15] = palette_transparent_pen;
/*TODO*///							pdrawgfxzoom(bitmap,K053245_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									fx,fy,
/*TODO*///									sx,sy,
/*TODO*///									&Machine.visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,
/*TODO*///									(zw << 16) / 16,(zh << 16) / 16,pri);
/*TODO*///							K053245_gfx.colortable[16*color+15] = o;
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							pdrawgfxzoom(bitmap,K053245_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									fx,fy,
/*TODO*///									sx,sy,
/*TODO*///									&Machine.visible_area,TRANSPARENCY_PEN,0,
/*TODO*///									(zw << 16) / 16,(zh << 16) / 16,pri);
/*TODO*///						}
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	#if 0
/*TODO*///	if (keyboard_pressed(KEYCODE_D))
/*TODO*///	{
/*TODO*///		FILE *fp;
/*TODO*///		fp=fopen("SPRITE.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(K053245_ram, 0x800, 1, fp);
/*TODO*///			usrintf_showmessage("saved");
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	#endif
/*TODO*///	#undef NUM_SPRITES
/*TODO*///	}
/*TODO*///	
/*TODO*///	void K053245_mark_sprites_colors(void)
/*TODO*///	{
/*TODO*///		int offs,i;
/*TODO*///	
/*TODO*///		unsigned short palette_map[512];
/*TODO*///	
/*TODO*///		memset (palette_map, 0, sizeof (palette_map));
/*TODO*///	
/*TODO*///		/* sprites */
/*TODO*///		for (offs = 0x800-16;offs >= 0;offs -= 16)
/*TODO*///		{
/*TODO*///			if (READ_WORD(&K053245_ram[offs]) & 0x8000)
/*TODO*///			{
/*TODO*///				int code,color,pri;
/*TODO*///	
/*TODO*///				code = READ_WORD(&K053245_ram[offs+0x02]);
/*TODO*///				code = ((code & 0xffe1) + ((code & 0x0010) >> 2) + ((code & 0x0008) << 1)
/*TODO*///						 + ((code & 0x0004) >> 1) + ((code & 0x0002) << 2));
/*TODO*///				color = READ_WORD(&K053245_ram[offs+0x0c]) & 0x00ff;
/*TODO*///				pri = 0;
/*TODO*///				(*K053245_callback)(&code,&color,&pri);
/*TODO*///				palette_map[color] |= 0xffff;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	
/*TODO*///		/* now build the final table */
/*TODO*///		for (i = 0; i < 512; i++)
/*TODO*///		{
/*TODO*///			int usage = palette_map[i], j;
/*TODO*///			if (usage != 0)
/*TODO*///			{
/*TODO*///				for (j = 1; j < 16; j++)
/*TODO*///					if (usage & (1 << j))
/*TODO*///						palette_used_colors[i * 16 + j] |= PALETTE_COLOR_VISIBLE;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static int K053247_memory_region, K053247_dx, K053247_dy;
/*TODO*///	static struct GfxElement *K053247_gfx;
/*TODO*///	static void (*K053247_callback)(int *code,int *color,int *priority);
/*TODO*///	static int K053246_OBJCHA_line;
/*TODO*///	static int K053246_romoffset;
/*TODO*///	static int K053246_flipscreenX,K053246_flipscreenY;
/*TODO*///	static int K053246_spriteoffsX,K053246_spriteoffsY;
/*TODO*///	static UBytePtr K053247_ram;
/*TODO*///	static int K053246_irq_enabled;
/*TODO*///	
/*TODO*///	
/*TODO*///	int K053247_vh_start(int gfx_memory_region, int dx, int dy, int plane0,int plane1,int plane2,int plane3,
/*TODO*///						 void (*callback)(int *code,int *color,int *priority))
/*TODO*///	{
/*TODO*///		int gfx_index;
/*TODO*///		static GfxLayout spritelayout = new GfxLayout
/*TODO*///		(
/*TODO*///			16,16,
/*TODO*///			0,				/* filled in later */
/*TODO*///			4,
/*TODO*///			new int[] { 0, 0, 0, 0 },	/* filled in later */
/*TODO*///			new int[] { 2*4, 3*4, 0*4, 1*4, 6*4, 7*4, 4*4, 5*4,
/*TODO*///					10*4, 11*4, 8*4, 9*4, 14*4, 15*4, 12*4, 13*4 },
/*TODO*///			new int[] { 0*64, 1*64, 2*64, 3*64, 4*64, 5*64, 6*64, 7*64,
/*TODO*///					8*64, 9*64, 10*64, 11*64, 12*64, 13*64, 14*64, 15*64 },
/*TODO*///			128*8
/*TODO*///		);
/*TODO*///	
/*TODO*///	
/*TODO*///		/* find first empty slot to decode gfx */
/*TODO*///		for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++)
/*TODO*///			if (Machine.gfx[gfx_index] == 0)
/*TODO*///				break;
/*TODO*///		if (gfx_index == MAX_GFX_ELEMENTS)
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		/* tweak the structure for the number of tiles we have */
/*TODO*///		spritelayout.total = memory_region_length(gfx_memory_region) / 128;
/*TODO*///		spritelayout.planeoffset[0] = plane0;
/*TODO*///		spritelayout.planeoffset[1] = plane1;
/*TODO*///		spritelayout.planeoffset[2] = plane2;
/*TODO*///		spritelayout.planeoffset[3] = plane3;
/*TODO*///	
/*TODO*///		/* decode the graphics */
/*TODO*///		Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region),&spritelayout);
/*TODO*///		if (!Machine.gfx[gfx_index])
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		/* set the color information */
/*TODO*///		Machine.gfx[gfx_index].colortable = Machine.remapped_colortable;
/*TODO*///		Machine.gfx[gfx_index].total_colors = Machine.drv.color_table_len / 16;
/*TODO*///	
/*TODO*///		K053247_dx = dx;
/*TODO*///		K053247_dy = dy;
/*TODO*///		K053247_memory_region = gfx_memory_region;
/*TODO*///		K053247_gfx = Machine.gfx[gfx_index];
/*TODO*///		K053247_callback = callback;
/*TODO*///		K053246_OBJCHA_line = CLEAR_LINE;
/*TODO*///		K053247_ram = malloc(0x1000);
/*TODO*///		if (!K053247_ram) return 1;
/*TODO*///	
/*TODO*///		memset(K053247_ram,0,0x1000);
/*TODO*///	
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static VhStopPtr K053247_vh_stop = new VhStopPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		free(K053247_ram);
/*TODO*///		K053247_ram = 0;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K053247_word_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		return READ_WORD(&K053247_ram[offset]);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K053247_word_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		COMBINE_WORD_MEM(&K053247_ram[offset],data);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K053247_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		int shift = ((offset & 1) ^ 1) << 3;
/*TODO*///		return (READ_WORD(&K053247_ram[offset & ~1]) >> shift) & 0xff;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K053247_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		int shift = ((offset & 1) ^ 1) << 3;
/*TODO*///		offset &= ~1;
/*TODO*///		COMBINE_WORD_MEM(&K053247_ram[offset],(0xff000000 >> shift) | ((data & 0xff) << shift));
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K053246_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		if (K053246_OBJCHA_line == ASSERT_LINE)
/*TODO*///		{
/*TODO*///			int addr;
/*TODO*///	
/*TODO*///	
/*TODO*///			addr = 2 * K053246_romoffset + ((offset & 1) ^ 1);
/*TODO*///			addr &= memory_region_length(K053247_memory_region)-1;
/*TODO*///	
/*TODO*///	#if 0
/*TODO*///		usrintf_showmessage("%04x: offset %02x addr %06x",cpu_get_pc(),offset,addr);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///			return memory_region(K053247_memory_region)[addr];
/*TODO*///		}
/*TODO*///		else
/*TODO*///		{
/*TODO*///	logerror("%04x: read from unknown 053244 address %x\n",cpu_get_pc(),offset);
/*TODO*///			return 0;
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K053246_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		if (offset == 0x00)
/*TODO*///			K053246_spriteoffsX = (K053246_spriteoffsX & 0x00ff) | (data << 8);
/*TODO*///		else if (offset == 0x01)
/*TODO*///			K053246_spriteoffsX = (K053246_spriteoffsX & 0xff00) | data;
/*TODO*///		else if (offset == 0x02)
/*TODO*///			K053246_spriteoffsY = (K053246_spriteoffsY & 0x00ff) | (data << 8);
/*TODO*///		else if (offset == 0x03)
/*TODO*///			K053246_spriteoffsY = (K053246_spriteoffsY & 0xff00) | data;
/*TODO*///		else if (offset == 0x05)
/*TODO*///		{
/*TODO*///	#ifdef MAME_DEBUG
/*TODO*///	if ((data & 0xc8) != 0)
/*TODO*///		usrintf_showmessage("053246 reg 05 = %02x",data);
/*TODO*///	#endif
/*TODO*///			/* bit 0/1 = flip screen */
/*TODO*///			K053246_flipscreenX = data & 0x01;
/*TODO*///			K053246_flipscreenY = data & 0x02;
/*TODO*///	
/*TODO*///			/* bit 2 = unknown */
/*TODO*///	
/*TODO*///			/* bit 4 = interrupt enable */
/*TODO*///			K053246_irq_enabled = data & 0x10;
/*TODO*///	
/*TODO*///			/* bit 5 = unknown */
/*TODO*///	
/*TODO*///	logerror("%04x: write %02x to 053246 address 5\n",cpu_get_pc(),data);
/*TODO*///		}
/*TODO*///		else if (offset >= 0x04 && offset < 0x08)   /* only 4,6,7 - 5 is handled above */
/*TODO*///		{
/*TODO*///			offset = 8*(((offset & 0x03) ^ 0x01) - 1);
/*TODO*///			K053246_romoffset = (K053246_romoffset & ~(0xff << offset)) | (data << offset);
/*TODO*///			return;
/*TODO*///		}
/*TODO*///		else
/*TODO*///	logerror("%04x: write %02x to unknown 053246 address %x\n",cpu_get_pc(),data,offset);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K053246_word_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		return K053246_r(offset + 1) | (K053246_r(offset) << 8);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K053246_word_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		if ((data & 0xff000000) == 0)
/*TODO*///			K053246_w(offset,(data >> 8) & 0xff);
/*TODO*///		if ((data & 0x00ff0000) == 0)
/*TODO*///			K053246_w(offset + 1,data & 0xff);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	void K053246_set_OBJCHA_line(int state)
/*TODO*///	{
/*TODO*///		K053246_OBJCHA_line = state;
/*TODO*///	}
/*TODO*///	
/*TODO*///	int K053246_is_IRQ_enabled(void)
/*TODO*///	{
/*TODO*///		return K053246_irq_enabled;
/*TODO*///	}
/*TODO*///	
/*TODO*///	/*
/*TODO*///	 * Sprite Format
/*TODO*///	 * ------------------
/*TODO*///	 *
/*TODO*///	 * Word | Bit(s)           | Use
/*TODO*///	 * -----+-fedcba9876543210-+----------------
/*TODO*///	 *   0  | x--------------- | active (show this sprite)
/*TODO*///	 *   0  | -x-------------- | maintain aspect ratio (when set, zoom y acts on both axis)
/*TODO*///	 *   0  | --x------------- | flip y
/*TODO*///	 *   0  | ---x------------ | flip x
/*TODO*///	 *   0  | ----xxxx-------- | sprite size (see below)
/*TODO*///	 *   0  | --------xxxxxxxx | priority order
/*TODO*///	 *   1  | xxxxxxxxxxxxxxxx | sprite code
/*TODO*///	 *   2  | ------xxxxxxxxxx | y position
/*TODO*///	 *   3  | ------xxxxxxxxxx | x position
/*TODO*///	 *   4  | xxxxxxxxxxxxxxxx | zoom y (0x40 = normal, <0x40 = enlarge, >0x40 = reduce)
/*TODO*///	 *   5  | xxxxxxxxxxxxxxxx | zoom x (0x40 = normal, <0x40 = enlarge, >0x40 = reduce)
/*TODO*///	 *   6  | x--------------- | mirror y (top half is drawn as mirror image of the bottom)
/*TODO*///	 *   6  | -x-------------- | mirror x (right half is drawn as mirror image of the left)
/*TODO*///	 *   6  | -----x---------- | shadow
/*TODO*///	 *   6  | xxxxxxxxxxxxxxxx | "color", but depends on external connections
/*TODO*///	 *   7  | ---------------- |
/*TODO*///	 *
/*TODO*///	 * shadow enables transparent shadows. Note that it applies to pen 0x0f ONLY.
/*TODO*///	 * The rest of the sprite remains normal.
/*TODO*///	 */
/*TODO*///	
/*TODO*///	void K053247_sprites_draw(struct osd_bitmap *bitmap)
/*TODO*///	{
/*TODO*///	#define NUM_SPRITES 256
/*TODO*///		int offs,pri_code;
/*TODO*///		int sortedlist[NUM_SPRITES];
/*TODO*///	
/*TODO*///		for (offs = 0;offs < NUM_SPRITES;offs++)
/*TODO*///			sortedlist[offs] = -1;
/*TODO*///	
/*TODO*///		/* prebuild a sorted table */
/*TODO*///		for (offs = 0;offs < 0x1000;offs += 16)
/*TODO*///		{
/*TODO*///	//		if (READ_WORD(&K053247_ram[offs]) & 0x8000)
/*TODO*///			sortedlist[READ_WORD(&K053247_ram[offs]) & 0x00ff] = offs;
/*TODO*///		}
/*TODO*///	
/*TODO*///		for (pri_code = 0;pri_code < NUM_SPRITES;pri_code++)
/*TODO*///		{
/*TODO*///			int ox,oy,color,code,size,w,h,x,y,xa,ya,flipx,flipy,mirrorx,mirrory,zoomx,zoomy,pri;
/*TODO*///			/* sprites can be grouped up to 8x8. The draw order is
/*TODO*///				 0  1  4  5 16 17 20 21
/*TODO*///				 2  3  6  7 18 19 22 23
/*TODO*///				 8  9 12 13 24 25 28 29
/*TODO*///				10 11 14 15 26 27 30 31
/*TODO*///				32 33 36 37 48 49 52 53
/*TODO*///				34 35 38 39 50 51 54 55
/*TODO*///				40 41 44 45 56 57 60 61
/*TODO*///				42 43 46 47 58 59 62 63
/*TODO*///			*/
/*TODO*///			static int xoffset[8] = { 0, 1, 4, 5, 16, 17, 20, 21 };
/*TODO*///			static int yoffset[8] = { 0, 2, 8, 10, 32, 34, 40, 42 };
/*TODO*///	
/*TODO*///	
/*TODO*///			offs = sortedlist[pri_code];
/*TODO*///			if (offs == -1) continue;
/*TODO*///	
/*TODO*///			if ((READ_WORD(&K053247_ram[offs]) & 0x8000) == 0) continue;
/*TODO*///	
/*TODO*///			code = READ_WORD(&K053247_ram[offs+0x02]);
/*TODO*///			color = READ_WORD(&K053247_ram[offs+0x0c]);
/*TODO*///			pri = 0;
/*TODO*///	
/*TODO*///			(*K053247_callback)(&code,&color,&pri);
/*TODO*///	
/*TODO*///			size = (READ_WORD(&K053247_ram[offs]) & 0x0f00) >> 8;
/*TODO*///	
/*TODO*///			w = 1 << (size & 0x03);
/*TODO*///			h = 1 << ((size >> 2) & 0x03);
/*TODO*///	
/*TODO*///			/* the sprite can start at any point in the 8x8 grid. We have to */
/*TODO*///			/* adjust the offsets to draw it correctly. Simpsons does this all the time. */
/*TODO*///			xa = 0;
/*TODO*///			ya = 0;
/*TODO*///			if ((code & 0x01) != 0) xa += 1;
/*TODO*///			if ((code & 0x02) != 0) ya += 1;
/*TODO*///			if ((code & 0x04) != 0) xa += 2;
/*TODO*///			if ((code & 0x08) != 0) ya += 2;
/*TODO*///			if ((code & 0x10) != 0) xa += 4;
/*TODO*///			if ((code & 0x20) != 0) ya += 4;
/*TODO*///			code &= ~0x3f;
/*TODO*///	
/*TODO*///	
/*TODO*///			/* zoom control:
/*TODO*///			   0x40 = normal scale
/*TODO*///			  <0x40 enlarge (0x20 = double size)
/*TODO*///			  >0x40 reduce (0x80 = half size)
/*TODO*///			*/
/*TODO*///			zoomy = READ_WORD(&K053247_ram[offs+0x08]);
/*TODO*///			if (zoomy > 0x2000) continue;
/*TODO*///			if (zoomy != 0) zoomy = (0x400000+zoomy/2) / zoomy;
/*TODO*///			else zoomy = 2 * 0x400000;
/*TODO*///			if ((READ_WORD(&K053247_ram[offs]) & 0x4000) == 0)
/*TODO*///			{
/*TODO*///				zoomx = READ_WORD(&K053247_ram[offs+0x0a]);
/*TODO*///				if (zoomx > 0x2000) continue;
/*TODO*///				if (zoomx != 0) zoomx = (0x400000+zoomx/2) / zoomx;
/*TODO*///				else zoomx = 2 * 0x400000;
/*TODO*///			}
/*TODO*///			else zoomx = zoomy;
/*TODO*///	
/*TODO*///			ox = READ_WORD(&K053247_ram[offs+0x06]);
/*TODO*///			oy = READ_WORD(&K053247_ram[offs+0x04]);
/*TODO*///	
/*TODO*///			flipx = READ_WORD(&K053247_ram[offs]) & 0x1000;
/*TODO*///			flipy = READ_WORD(&K053247_ram[offs]) & 0x2000;
/*TODO*///			mirrorx = READ_WORD(&K053247_ram[offs+0x0c]) & 0x4000;
/*TODO*///			mirrory = READ_WORD(&K053247_ram[offs+0x0c]) & 0x8000;
/*TODO*///	
/*TODO*///			if (K053246_flipscreenX != 0)
/*TODO*///			{
/*TODO*///				ox = -ox;
/*TODO*///				if (!mirrorx) flipx = !flipx;
/*TODO*///			}
/*TODO*///			if (K053246_flipscreenY != 0)
/*TODO*///			{
/*TODO*///				oy = -oy;
/*TODO*///				if (!mirrory) flipy = !flipy;
/*TODO*///			}
/*TODO*///	
/*TODO*///			ox = (K053247_dx + ox - K053246_spriteoffsX) & 0x3ff;
/*TODO*///			if (ox >= 768) ox -= 1024;
/*TODO*///			oy = (-(K053247_dy + oy + K053246_spriteoffsY)) & 0x3ff;
/*TODO*///			if (oy >= 640) oy -= 1024;
/*TODO*///	
/*TODO*///			/* the coordinates given are for the *center* of the sprite */
/*TODO*///			ox -= (zoomx * w) >> 13;
/*TODO*///			oy -= (zoomy * h) >> 13;
/*TODO*///	
/*TODO*///			for (y = 0;y < h;y++)
/*TODO*///			{
/*TODO*///				int sx,sy,zw,zh;
/*TODO*///	
/*TODO*///				sy = oy + ((zoomy * y + (1<<11)) >> 12);
/*TODO*///				zh = (oy + ((zoomy * (y+1) + (1<<11)) >> 12)) - sy;
/*TODO*///	
/*TODO*///				for (x = 0;x < w;x++)
/*TODO*///				{
/*TODO*///					int c,fx,fy;
/*TODO*///	
/*TODO*///					sx = ox + ((zoomx * x + (1<<11)) >> 12);
/*TODO*///					zw = (ox + ((zoomx * (x+1) + (1<<11)) >> 12)) - sx;
/*TODO*///					c = code;
/*TODO*///					if (mirrorx != 0)
/*TODO*///					{
/*TODO*///						if ((flipx == 0) ^ (2*x < w))
/*TODO*///						{
/*TODO*///							/* mirror left/right */
/*TODO*///							c += xoffset[(w-1-x+xa)&7];
/*TODO*///							fx = 1;
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							c += xoffset[(x+xa)&7];
/*TODO*///							fx = 0;
/*TODO*///						}
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						if (flipx != 0) c += xoffset[(w-1-x+xa)&7];
/*TODO*///						else c += xoffset[(x+xa)&7];
/*TODO*///						fx = flipx;
/*TODO*///					}
/*TODO*///					if (mirrory != 0)
/*TODO*///					{
/*TODO*///						if ((flipy == 0) ^ (2*y >= h))
/*TODO*///						{
/*TODO*///							/* mirror top/bottom */
/*TODO*///							c += yoffset[(h-1-y+ya)&7];
/*TODO*///							fy = 1;
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							c += yoffset[(y+ya)&7];
/*TODO*///							fy = 0;
/*TODO*///						}
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						if (flipy != 0) c += yoffset[(h-1-y+ya)&7];
/*TODO*///						else c += yoffset[(y+ya)&7];
/*TODO*///						fy = flipy;
/*TODO*///					}
/*TODO*///	
/*TODO*///					if (zoomx == 0x10000 && zoomy == 0x10000)
/*TODO*///					{
/*TODO*///						/* hack to simulate shadow */
/*TODO*///						if (READ_WORD(&K053247_ram[offs+0x0c]) & 0x0400)
/*TODO*///						{
/*TODO*///							int o = K053247_gfx.colortable[16*color+15];
/*TODO*///							K053247_gfx.colortable[16*color+15] = palette_transparent_pen;
/*TODO*///							pdrawgfx(bitmap,K053247_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									fx,fy,
/*TODO*///									sx,sy,
/*TODO*///									&Machine.visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,pri);
/*TODO*///							K053247_gfx.colortable[16*color+15] = o;
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							pdrawgfx(bitmap,K053247_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									fx,fy,
/*TODO*///									sx,sy,
/*TODO*///									&Machine.visible_area,TRANSPARENCY_PEN,0,pri);
/*TODO*///						}
/*TODO*///					}
/*TODO*///					else
/*TODO*///					{
/*TODO*///						/* hack to simulate shadow */
/*TODO*///						if (READ_WORD(&K053247_ram[offs+0x0c]) & 0x0400)
/*TODO*///						{
/*TODO*///							int o = K053247_gfx.colortable[16*color+15];
/*TODO*///							K053247_gfx.colortable[16*color+15] = palette_transparent_pen;
/*TODO*///							pdrawgfxzoom(bitmap,K053247_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									fx,fy,
/*TODO*///									sx,sy,
/*TODO*///									&Machine.visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,
/*TODO*///									(zw << 16) / 16,(zh << 16) / 16,pri);
/*TODO*///							K053247_gfx.colortable[16*color+15] = o;
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							pdrawgfxzoom(bitmap,K053247_gfx,
/*TODO*///									c,
/*TODO*///									color,
/*TODO*///									fx,fy,
/*TODO*///									sx,sy,
/*TODO*///									&Machine.visible_area,TRANSPARENCY_PEN,0,
/*TODO*///									(zw << 16) / 16,(zh << 16) / 16,pri);
/*TODO*///						}
/*TODO*///					}
/*TODO*///	
/*TODO*///					if (mirrory && h == 1)  /* Simpsons shadows */
/*TODO*///					{
/*TODO*///						if (zoomx == 0x10000 && zoomy == 0x10000)
/*TODO*///						{
/*TODO*///							/* hack to simulate shadow */
/*TODO*///							if (READ_WORD(&K053247_ram[offs+0x0c]) & 0x0400)
/*TODO*///							{
/*TODO*///								int o = K053247_gfx.colortable[16*color+15];
/*TODO*///								K053247_gfx.colortable[16*color+15] = palette_transparent_pen;
/*TODO*///								pdrawgfx(bitmap,K053247_gfx,
/*TODO*///										c,
/*TODO*///										color,
/*TODO*///										fx,!fy,
/*TODO*///										sx,sy,
/*TODO*///										&Machine.visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,pri);
/*TODO*///								K053247_gfx.colortable[16*color+15] = o;
/*TODO*///							}
/*TODO*///							else
/*TODO*///							{
/*TODO*///								pdrawgfx(bitmap,K053247_gfx,
/*TODO*///										c,
/*TODO*///										color,
/*TODO*///										fx,!fy,
/*TODO*///										sx,sy,
/*TODO*///										&Machine.visible_area,TRANSPARENCY_PEN,0,pri);
/*TODO*///							}
/*TODO*///						}
/*TODO*///						else
/*TODO*///						{
/*TODO*///							/* hack to simulate shadow */
/*TODO*///							if (READ_WORD(&K053247_ram[offs+0x0c]) & 0x0400)
/*TODO*///							{
/*TODO*///								int o = K053247_gfx.colortable[16*color+15];
/*TODO*///								K053247_gfx.colortable[16*color+15] = palette_transparent_pen;
/*TODO*///								pdrawgfxzoom(bitmap,K053247_gfx,
/*TODO*///										c,
/*TODO*///										color,
/*TODO*///										fx,!fy,
/*TODO*///										sx,sy,
/*TODO*///										&Machine.visible_area,TRANSPARENCY_PENS,(cpu_getcurrentframe() & 1) ? 0x8001 : 0x0001,
/*TODO*///										(zw << 16) / 16,(zh << 16) / 16,pri);
/*TODO*///								K053247_gfx.colortable[16*color+15] = o;
/*TODO*///							}
/*TODO*///							else
/*TODO*///							{
/*TODO*///								pdrawgfxzoom(bitmap,K053247_gfx,
/*TODO*///										c,
/*TODO*///										color,
/*TODO*///										fx,!fy,
/*TODO*///										sx,sy,
/*TODO*///										&Machine.visible_area,TRANSPARENCY_PEN,0,
/*TODO*///										(zw << 16) / 16,(zh << 16) / 16,pri);
/*TODO*///							}
/*TODO*///						}
/*TODO*///					}
/*TODO*///				}
/*TODO*///			}
/*TODO*///		}
/*TODO*///	#if 0
/*TODO*///	if (keyboard_pressed(KEYCODE_D))
/*TODO*///	{
/*TODO*///		FILE *fp;
/*TODO*///		fp=fopen("SPRITE.DMP", "w+b");
/*TODO*///		if (fp != 0)
/*TODO*///		{
/*TODO*///			fwrite(K053247_ram, 0x1000, 1, fp);
/*TODO*///			usrintf_showmessage("saved");
/*TODO*///			fclose(fp);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	#endif
/*TODO*///	#undef NUM_SPRITES
/*TODO*///	}
/*TODO*///	
/*TODO*///	void K053247_mark_sprites_colors(void)
/*TODO*///	{
/*TODO*///		int offs,i;
/*TODO*///	
/*TODO*///		unsigned short palette_map[512];
/*TODO*///	
/*TODO*///		memset (palette_map, 0, sizeof (palette_map));
/*TODO*///	
/*TODO*///		/* sprites */
/*TODO*///		for (offs = 0x1000-16;offs >= 0;offs -= 16)
/*TODO*///		{
/*TODO*///			if (READ_WORD(&K053247_ram[offs]) & 0x8000)
/*TODO*///			{
/*TODO*///				int code,color,pri;
/*TODO*///	
/*TODO*///				code = READ_WORD(&K053247_ram[offs+0x02]);
/*TODO*///				color = READ_WORD(&K053247_ram[offs+0x0c]);
/*TODO*///				pri = 0;
/*TODO*///				(*K053247_callback)(&code,&color,&pri);
/*TODO*///				palette_map[color] |= 0xffff;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	
/*TODO*///		/* now build the final table */
/*TODO*///		for (i = 0; i < 512; i++)
/*TODO*///		{
/*TODO*///			int usage = palette_map[i], j;
/*TODO*///			if (usage != 0)
/*TODO*///			{
/*TODO*///				for (j = 1; j < 16; j++)
/*TODO*///					if (usage & (1 << j))
/*TODO*///						palette_used_colors[i * 16 + j] |= PALETTE_COLOR_VISIBLE;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
    public static abstract interface K051316_callbackProcPtr {

        public abstract void handler(int[] code, int[] color);
    }

    public static int MAX_K051316 = 3;
    public static int[] K051316_memory_region = new int[MAX_K051316];
    static int[] K051316_gfxnum = new int[MAX_K051316];
    public static int[] K051316_wraparound = new int[MAX_K051316];
    public static int[][] K051316_offset = new int[MAX_K051316][];
    static int[] K051316_bpp = new int[MAX_K051316];
    static K051316_callbackProcPtr[] K051316_callback = new K051316_callbackProcPtr[MAX_K051316];
    static UBytePtr[] K051316_ram = new UBytePtr[MAX_K051316];
    public static char[][] K051316_ctrlram = new char[MAX_K051316][];
    public static struct_tilemap[] K051316_tilemap = new struct_tilemap[MAX_K051316];
    static int K051316_chip_selected;

    static {
        for (int i = 0; i < 3; i++) {
            K051316_offset[i] = new int[2];
        }
        for (int i = 0; i < 3; i++) {
            K051316_ctrlram[i] = new char[16];
        }

    }

    /**
     * *************************************************************************
     *
     * Callbacks for the TileMap code
     *
     **************************************************************************
     */
    public static void K051316_preupdate(int chip) {
        K051316_chip_selected = chip;
    }

    public static GetTileInfoPtr K051316_get_tile_info = new GetTileInfoPtr() {
        public void handler(int tile_index) {
            int[] code = new int[1];
            int[] color = new int[1];
            code[0] = K051316_ram[K051316_chip_selected].read(tile_index);
            color[0] = K051316_ram[K051316_chip_selected].read(tile_index + 0x400);

            K051316_callback[K051316_chip_selected].handler(code, color);//(*K051316_callback[K051316_chip_selected])(&code,&color);

            SET_TILE_INFO(K051316_gfxnum[K051316_chip_selected], code[0], color[0]);
        }
    };

    public static int K051316_vh_start(int chip, int gfx_memory_region, int bpp, K051316_callbackProcPtr callback) {
        int gfx_index;


        /* find first empty slot to decode gfx */
        for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++) {
            if (Machine.gfx[gfx_index] == null) {
                break;
            }
        }
        if (gfx_index == MAX_GFX_ELEMENTS) {
            return 1;
        }

        if (bpp == 4) {
            GfxLayout charlayout = new GfxLayout(
                    16, 16,
                    0, /* filled in later */
                    4,
                    new int[]{0, 1, 2, 3},
                    new int[]{0 * 4, 1 * 4, 2 * 4, 3 * 4, 4 * 4, 5 * 4, 6 * 4, 7 * 4,
                        8 * 4, 9 * 4, 10 * 4, 11 * 4, 12 * 4, 13 * 4, 14 * 4, 15 * 4},
                    new int[]{0 * 64, 1 * 64, 2 * 64, 3 * 64, 4 * 64, 5 * 64, 6 * 64, 7 * 64,
                        8 * 64, 9 * 64, 10 * 64, 11 * 64, 12 * 64, 13 * 64, 14 * 64, 15 * 64},
                    128 * 8
            );


            /* tweak the structure for the number of tiles we have */
            charlayout.total = memory_region_length(gfx_memory_region) / 128;

            /* decode the graphics */
            Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region), charlayout);
        } else if (bpp == 7) {
            GfxLayout charlayout = new GfxLayout(
                    16, 16,
                    0, /* filled in later */
                    7,
                    new int[]{1, 2, 3, 4, 5, 6, 7},
                    new int[]{0 * 8, 1 * 8, 2 * 8, 3 * 8, 4 * 8, 5 * 8, 6 * 8, 7 * 8,
                        8 * 8, 9 * 8, 10 * 8, 11 * 8, 12 * 8, 13 * 8, 14 * 8, 15 * 8},
                    new int[]{0 * 128, 1 * 128, 2 * 128, 3 * 128, 4 * 128, 5 * 128, 6 * 128, 7 * 128,
                        8 * 128, 9 * 128, 10 * 128, 11 * 128, 12 * 128, 13 * 128, 14 * 128, 15 * 128},
                    256 * 8
            );


            /* tweak the structure for the number of tiles we have */
            charlayout.total = memory_region_length(gfx_memory_region) / 256;

            /* decode the graphics */
            Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region), charlayout);
        } else {
            logerror("K051316_vh_start supports only 4 or 7 bpp\n");
            return 1;
        }

        if (Machine.gfx[gfx_index] == null) {
            return 1;
        }

        /* set the color information */
        Machine.gfx[gfx_index].colortable = new UShortArray(Machine.remapped_colortable);
        Machine.gfx[gfx_index].total_colors = Machine.drv.color_table_len / (1 << bpp);

        K051316_memory_region[chip] = gfx_memory_region;
        K051316_gfxnum[chip] = gfx_index;
        K051316_bpp[chip] = bpp;
        K051316_callback[chip] = callback;

        K051316_tilemap[chip] = tilemap_create(K051316_get_tile_info, tilemap_scan_rows, TILEMAP_OPAQUE, 16, 16, 32, 32);

        K051316_ram[chip] = new UBytePtr(0x800);

        if (K051316_ram[chip] == null || K051316_tilemap[chip] == null) {
            K051316_vh_stop(chip);
            return 1;
        }

        tilemap_set_clip(K051316_tilemap[chip], null);

        K051316_wraparound[chip] = 0;
        /* default = no wraparound */

        K051316_offset[chip][0] = K051316_offset[chip][1] = 0;

        return 0;
    }

    public static int K051316_vh_start_0(int gfx_memory_region, int bpp,
            K051316_callbackProcPtr callback) {
        return K051316_vh_start(0, gfx_memory_region, bpp, callback);
    }

    public static int K051316_vh_start_1(int gfx_memory_region, int bpp,
            K051316_callbackProcPtr callback) {
        return K051316_vh_start(1, gfx_memory_region, bpp, callback);
    }

    public static int K051316_vh_start_2(int gfx_memory_region, int bpp,
            K051316_callbackProcPtr callback) {
        return K051316_vh_start(2, gfx_memory_region, bpp, callback);
    }

    public static void K051316_vh_stop(int chip) {
        K051316_ram[chip] = null;
    }

    public static void K051316_vh_stop_0() {
        K051316_vh_stop(0);
    }

    public static void K051316_vh_stop_1() {
        K051316_vh_stop(1);
    }

    public static void K051316_vh_stop_2() {
        K051316_vh_stop(2);
    }

    public static int K051316_r(int chip, int offset) {
        return K051316_ram[chip].read(offset);
    }

    public static ReadHandlerPtr K051316_0_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return K051316_r(0, offset);
        }
    };

    public static ReadHandlerPtr K051316_1_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return K051316_r(1, offset);
        }
    };

    public static ReadHandlerPtr K051316_2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return K051316_r(2, offset);
        }
    };

    public static void K051316_w(int chip, int offset, int data) {
        if (K051316_ram[chip].read(offset) != data) {
            K051316_ram[chip].write(offset, data);
            tilemap_mark_tile_dirty(K051316_tilemap[chip], offset & 0x3ff);
        }
    }

    public static WriteHandlerPtr K051316_0_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            K051316_w(0, offset, data);
        }
    };

    public static WriteHandlerPtr K051316_1_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            K051316_w(1, offset, data);
        }
    };

    public static WriteHandlerPtr K051316_2_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            K051316_w(2, offset, data);
        }
    };

    public static int K051316_rom_r(int chip, int offset) {
        if ((K051316_ctrlram[chip][0x0e] & 0x01) == 0) {
            int addr;

            addr = offset + (K051316_ctrlram[chip][0x0c] << 11) + (K051316_ctrlram[chip][0x0d] << 19);
            if (K051316_bpp[chip] <= 4) {
                addr /= 2;
            }
            addr &= memory_region_length(K051316_memory_region[chip]) - 1;

            /* #if 0
             usrintf_showmessage("%04x: offset %04x addr %04x",cpu_get_pc(),offset,addr);
             #endif*/
            return memory_region(K051316_memory_region[chip]).read(addr);
        } else {
            logerror("%04x: read 051316 ROM offset %04x but reg 0x0c bit 0 not clear\n", cpu_get_pc(), offset);
            return 0;
        }
    }

    public static ReadHandlerPtr K051316_rom_0_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return K051316_rom_r(0, offset);
        }
    };

    public static ReadHandlerPtr K051316_rom_1_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return K051316_rom_r(1, offset);
        }
    };

    public static ReadHandlerPtr K051316_rom_2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            return K051316_rom_r(2, offset);
        }
    };

    public static void K051316_ctrl_w(int chip, int offset, int data) {
        K051316_ctrlram[chip][offset] = (char) (data & 0xFF);
        if (offset >= 0x0c) {
            logerror("%04x: write %02x to 051316 reg %x\n", cpu_get_pc(), data, offset);
        }
    }

    public static WriteHandlerPtr K051316_ctrl_0_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            K051316_ctrl_w(0, offset, data);
        }
    };
    	
    public static WriteHandlerPtr K051316_ctrl_1_w = new WriteHandlerPtr() {public void handler(int offset, int data)
    {
            K051316_ctrl_w(1,offset,data);
    } };
	
/*TODO*///	public static WriteHandlerPtr K051316_ctrl_2_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		K051316_ctrl_w(2,offset,data);
/*TODO*///	} };
/*TODO*///	
    public static void K051316_wraparound_enable(int chip, int status) {
        K051316_wraparound[chip] = status;
    }

    	
/*TODO*///    public static void K051316_set_offset(int chip, int xoffs, int yoffs)
/*TODO*///    {
/*TODO*///            K051316_offset[chip][0] = xoffs;
/*TODO*///            K051316_offset[chip][1] = yoffs;
/*TODO*///    }
    
    public static void K051316_tilemap_update(int chip) {
        K051316_preupdate(chip);
        tilemap_update(K051316_tilemap[chip]);
    }

    public static void K051316_tilemap_update_0() {
        K051316_tilemap_update(0);
    }

    public static void K051316_tilemap_update_1() {
        K051316_tilemap_update(1);
    }

    public static void K051316_tilemap_update_2() {
        K051316_tilemap_update(2);
    }


    public static void K051316_zoom_draw_0(osd_bitmap bitmap, int/*UINT32*/ priority) {
        K051316_zoom_draw(0, bitmap, priority);
    }
    	
    public static void K051316_zoom_draw_1(osd_bitmap bitmap, int priority)
    {
            K051316_zoom_draw(1,bitmap,priority);
    }
	
/*TODO*///	void K051316_zoom_draw_2(struct osd_bitmap *bitmap,UINT32 priority)
/*TODO*///	{
/*TODO*///		K051316_zoom_draw(2,bitmap,priority);
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static unsigned char K053251_ram[16];
/*TODO*///	static int K053251_palette_index[5];
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K053251_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		data &= 0x3f;
/*TODO*///	
/*TODO*///		if (K053251_ram[offset] != data)
/*TODO*///		{
/*TODO*///			K053251_ram[offset] = data;
/*TODO*///			if (offset == 9)
/*TODO*///			{
/*TODO*///				/* palette base index */
/*TODO*///				K053251_palette_index[0] = 32 * ((data >> 0) & 0x03);
/*TODO*///				K053251_palette_index[1] = 32 * ((data >> 2) & 0x03);
/*TODO*///				K053251_palette_index[2] = 32 * ((data >> 4) & 0x03);
/*TODO*///				tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
/*TODO*///			}
/*TODO*///			else if (offset == 10)
/*TODO*///			{
/*TODO*///				/* palette base index */
/*TODO*///				K053251_palette_index[3] = 16 * ((data >> 0) & 0x07);
/*TODO*///				K053251_palette_index[4] = 16 * ((data >> 3) & 0x07);
/*TODO*///				tilemap_mark_all_tiles_dirty(ALL_TILEMAPS);
/*TODO*///			}
/*TODO*///	#if 0
/*TODO*///	else
/*TODO*///	{
/*TODO*///	logerror("%04x: write %02x to K053251 register %04x\n",cpu_get_pc(),data&0xff,offset);
/*TODO*///	usrintf_showmessage("pri = %02x%02x%02x%02x %02x%02x%02x%02x %02x%02x%02x%02x %02x%02x%02x%02x",
/*TODO*///		K053251_ram[0],K053251_ram[1],K053251_ram[2],K053251_ram[3],
/*TODO*///		K053251_ram[4],K053251_ram[5],K053251_ram[6],K053251_ram[7],
/*TODO*///		K053251_ram[8],K053251_ram[9],K053251_ram[10],K053251_ram[11],
/*TODO*///		K053251_ram[12],K053251_ram[13],K053251_ram[14],K053251_ram[15]
/*TODO*///		);
/*TODO*///	}
/*TODO*///	#endif
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	int K053251_get_priority(int ci)
/*TODO*///	{
/*TODO*///		return K053251_ram[ci];
/*TODO*///	}
/*TODO*///	
/*TODO*///	int K053251_get_palette_index(int ci)
/*TODO*///	{
/*TODO*///		return K053251_palette_index[ci];
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static unsigned char K054000_ram[0x20];
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr collision_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K054000_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///	#if VERBOSE
/*TODO*///	logerror("%04x: write %02x to 054000 address %02x\n",cpu_get_pc(),data,offset);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///		K054000_ram[offset] = data;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K054000_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		int Acx,Acy,Aax,Aay;
/*TODO*///		int Bcx,Bcy,Bax,Bay;
/*TODO*///	
/*TODO*///	
/*TODO*///	#if VERBOSE
/*TODO*///	logerror("%04x: read 054000 address %02x\n",cpu_get_pc(),offset);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///		if (offset != 0x18) return 0;
/*TODO*///	
/*TODO*///	
/*TODO*///		Acx = (K054000_ram[0x01] << 16) | (K054000_ram[0x02] << 8) | K054000_ram[0x03];
/*TODO*///		Acy = (K054000_ram[0x09] << 16) | (K054000_ram[0x0a] << 8) | K054000_ram[0x0b];
/*TODO*///	/* TODO: this is a hack to make thndrx2 pass the startup check. It is certainly wrong. */
/*TODO*///	if (K054000_ram[0x04] == 0xff) Acx+=3;
/*TODO*///	if (K054000_ram[0x0c] == 0xff) Acy+=3;
/*TODO*///		Aax = K054000_ram[0x06] + 1;
/*TODO*///		Aay = K054000_ram[0x07] + 1;
/*TODO*///	
/*TODO*///		Bcx = (K054000_ram[0x15] << 16) | (K054000_ram[0x16] << 8) | K054000_ram[0x17];
/*TODO*///		Bcy = (K054000_ram[0x11] << 16) | (K054000_ram[0x12] << 8) | K054000_ram[0x13];
/*TODO*///		Bax = K054000_ram[0x0e] + 1;
/*TODO*///		Bay = K054000_ram[0x0f] + 1;
/*TODO*///	
/*TODO*///		if (Acx + Aax < Bcx - Bax)
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		if (Bcx + Bax < Acx - Aax)
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		if (Acy + Aay < Bcy - Bay)
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		if (Bcy + Bay < Acy - Aay)
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		return 0;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	
/*TODO*///	static unsigned char K051733_ram[0x20];
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K051733_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///	#if VERBOSE
/*TODO*///	logerror("%04x: write %02x to 051733 address %02x\n",cpu_get_pc(),data,offset);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///		K051733_ram[offset] = data;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K051733_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		int op1 = (K051733_ram[0x00] << 8) | K051733_ram[0x01];
/*TODO*///		int op2 = (K051733_ram[0x02] << 8) | K051733_ram[0x03];
/*TODO*///	
/*TODO*///		int rad = (K051733_ram[0x06] << 8) | K051733_ram[0x07];
/*TODO*///		int yobj1c = (K051733_ram[0x08] << 8) | K051733_ram[0x09];
/*TODO*///		int xobj1c = (K051733_ram[0x0a] << 8) | K051733_ram[0x0b];
/*TODO*///		int yobj2c = (K051733_ram[0x0c] << 8) | K051733_ram[0x0d];
/*TODO*///		int xobj2c = (K051733_ram[0x0e] << 8) | K051733_ram[0x0f];
/*TODO*///	
/*TODO*///	#if VERBOSE
/*TODO*///	logerror("%04x: read 051733 address %02x\n",cpu_get_pc(),offset);
/*TODO*///	#endif
/*TODO*///	
/*TODO*///		switch(offset){
/*TODO*///			case 0x00:
/*TODO*///				if (op2 != 0) return	((op1/op2) >> 8);
/*TODO*///				else return 0xff;
/*TODO*///			case 0x01:
/*TODO*///				if (op2 != 0) return	op1/op2;
/*TODO*///				else return 0xff;
/*TODO*///	
/*TODO*///			/* this is completely unverified */
/*TODO*///			case 0x02:
/*TODO*///				if (op2 != 0) return	((op1%op2) >> 8);
/*TODO*///				else return 0xff;
/*TODO*///			case 0x03:
/*TODO*///				if (op2 != 0) return	op1%op2;
/*TODO*///				else return 0xff;
/*TODO*///	
/*TODO*///			case 0x07:{
/*TODO*///				if (xobj1c + rad < xobj2c - rad)
/*TODO*///					return 0x80;
/*TODO*///	
/*TODO*///				if (xobj2c + rad < xobj1c - rad)
/*TODO*///					return 0x80;
/*TODO*///	
/*TODO*///				if (yobj1c + rad < yobj2c - rad)
/*TODO*///					return 0x80;
/*TODO*///	
/*TODO*///				if (yobj2c + rad < yobj1c - rad)
/*TODO*///					return 0x80;
/*TODO*///	
/*TODO*///				return 0;
/*TODO*///			}
/*TODO*///			default:
/*TODO*///				return K051733_ram[offset];
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	
/*TODO*///	static struct tilemap *K054157_tilemap[4], *K054157_cur_tilemap;
/*TODO*///	static int K054157_rambank, K054157_cur_rambank, K054157_rombank, K054157_cur_rombank, K054157_romnbbanks;
/*TODO*///	static int K054157_cur_layer, K054157_gfxnum, K054157_memory_region, K054157_cur_offset, K054157_control0;
/*TODO*///	static UBytePtr K054157_rambase, *K054157_cur_lbase, *K054157_cur_rambase, *K054157_rombase;
/*TODO*///	static int K054157_scrollx[4], K054157_scrolly[4];
/*TODO*///	static void (*K054157_callback)(int, int *, int *);
/*TODO*///	static int (*K054157_scrolld)[4][2];
/*TODO*///	
/*TODO*///	public static GetTileInfoPtr K054157_get_tile_info = new GetTileInfoPtr() { public void handler(int tile_index) 
/*TODO*///	{
/*TODO*///		UBytePtr addr;
/*TODO*///		int attr, code;
/*TODO*///		if(tile_index < 64*32)
/*TODO*///			addr = K054157_cur_lbase + (tile_index<<2);
/*TODO*///		else
/*TODO*///			addr = K054157_cur_lbase + (tile_index<<2) + 0x4000 - 64*32*4;
/*TODO*///	
/*TODO*///		attr = READ_WORD(addr);
/*TODO*///		code = READ_WORD(addr+2);
/*TODO*///		tile_info.flags = 0;
/*TODO*///	
/*TODO*///		(*K054157_callback)(K054157_cur_layer, &code, &attr);
/*TODO*///		SET_TILE_INFO (K054157_gfxnum, code, attr);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static VhStopPtr K054157_vh_stop = new VhStopPtr() { public void handler() 
/*TODO*///	{
/*TODO*///		if (K054157_rambase != 0) {
/*TODO*///			free(K054157_rambase);
/*TODO*///			K054157_rambase = 0;
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	int K054157_vh_start(int rambank, int rombank, int gfx_memory_region, int (*scrolld)[4][2], int plane0,int plane1,int plane2,int plane3, void (*callback)(int, int *, int *))
/*TODO*///	{
/*TODO*///		int gfx_index;
/*TODO*///		static GfxLayout charlayout = new GfxLayout
/*TODO*///		(
/*TODO*///			8, 8,
/*TODO*///			0,				/* filled in later */
/*TODO*///			4,
/*TODO*///			new int[] { 0, 0, 0, 0 },	/* filled in later */
/*TODO*///			new int[] { 2*4, 3*4, 0*4, 1*4, 6*4, 7*4, 4*4, 5*4 },
/*TODO*///			new int[] { 0*8*4, 1*8*4, 2*8*4, 3*8*4, 4*8*4, 5*8*4, 6*8*4, 7*8*4 },
/*TODO*///			8*8*4
/*TODO*///		);
/*TODO*///	
/*TODO*///		/* find first empty slot to decode gfx */
/*TODO*///		for (gfx_index = 0; gfx_index < MAX_GFX_ELEMENTS; gfx_index++)
/*TODO*///			if (Machine.gfx[gfx_index] == 0)
/*TODO*///				break;
/*TODO*///		if (gfx_index == MAX_GFX_ELEMENTS)
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		/* tweak the structure for the number of tiles we have */
/*TODO*///		charlayout.total = memory_region_length(gfx_memory_region) / (8*4);
/*TODO*///		charlayout.planeoffset[0] = plane0;
/*TODO*///		charlayout.planeoffset[1] = plane1;
/*TODO*///		charlayout.planeoffset[2] = plane2;
/*TODO*///		charlayout.planeoffset[3] = plane3;
/*TODO*///	
/*TODO*///		/* decode the graphics */
/*TODO*///		Machine.gfx[gfx_index] = decodegfx(memory_region(gfx_memory_region), &charlayout);
/*TODO*///		if (!Machine.gfx[gfx_index])
/*TODO*///			return 1;
/*TODO*///	
/*TODO*///		/* set the color information */
/*TODO*///		Machine.gfx[gfx_index].colortable = Machine.remapped_colortable;
/*TODO*///		Machine.gfx[gfx_index].total_colors = Machine.drv.color_table_len / 16;
/*TODO*///	
/*TODO*///		K054157_scrolld = scrolld;
/*TODO*///		K054157_memory_region = gfx_memory_region;
/*TODO*///		K054157_gfxnum = gfx_index;
/*TODO*///		K054157_callback = callback;
/*TODO*///	
/*TODO*///		K054157_tilemap[0] = tilemap_create(K054157_get_tile_info, tilemap_scan_rows,
/*TODO*///											TILEMAP_TRANSPARENT, 8, 8, 64, 64);
/*TODO*///		K054157_tilemap[1] = tilemap_create(K054157_get_tile_info, tilemap_scan_rows,
/*TODO*///											TILEMAP_TRANSPARENT, 8, 8, 64, 64);
/*TODO*///		K054157_tilemap[2] = tilemap_create(K054157_get_tile_info, tilemap_scan_rows,
/*TODO*///											TILEMAP_TRANSPARENT, 8, 8, 64, 64);
/*TODO*///		K054157_tilemap[3] = tilemap_create(K054157_get_tile_info, tilemap_scan_rows,
/*TODO*///											TILEMAP_TRANSPARENT, 8, 8, 64, 64);
/*TODO*///	
/*TODO*///		K054157_rambase = malloc(0x10000);
/*TODO*///	
/*TODO*///		if(!K054157_rambase || !K054157_tilemap[0] || !K054157_tilemap[1] || !K054157_tilemap[2] || !K054157_tilemap[3]) {
/*TODO*///			K054157_vh_stop();
/*TODO*///			return 1;
/*TODO*///		}
/*TODO*///	
/*TODO*///		memset(K054157_rambase, 0, 0x10000);
/*TODO*///	
/*TODO*///		K054157_tilemap[0].transparent_pen = 0;
/*TODO*///		K054157_tilemap[1].transparent_pen = 0;
/*TODO*///		K054157_tilemap[2].transparent_pen = 0;
/*TODO*///		K054157_tilemap[3].transparent_pen = 0;
/*TODO*///	
/*TODO*///		K054157_rambank = rambank;
/*TODO*///		K054157_cur_rambank = 0;
/*TODO*///		K054157_cur_rambase = K054157_rambase;
/*TODO*///		K054157_cur_tilemap = K054157_tilemap[0];
/*TODO*///		K054157_cur_offset = 0;
/*TODO*///		cpu_setbank(K054157_rambank, K054157_cur_rambase);
/*TODO*///	
/*TODO*///		K054157_rombank = rombank;
/*TODO*///		K054157_cur_rombank = 0;
/*TODO*///		K054157_rombase = memory_region(gfx_memory_region);
/*TODO*///		K054157_romnbbanks = memory_region_length(gfx_memory_region)/0x2000;
/*TODO*///		cpu_setbank(K054157_rombank, K054157_rombase);
/*TODO*///	
/*TODO*///		K054157_control0 = 0;
/*TODO*///	
/*TODO*///		return 0;
/*TODO*///	}
/*TODO*///	
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K054157_ram_word_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		UBytePtr adr = K054157_cur_rambase + offset;
/*TODO*///		return READ_WORD(adr);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K054157_ram_word_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		UBytePtr adr = K054157_cur_rambase + offset;
/*TODO*///		int old = READ_WORD(adr);
/*TODO*///		COMBINE_WORD_MEM(adr, data);
/*TODO*///	
/*TODO*///		if(READ_WORD(adr) != old)
/*TODO*///			tilemap_mark_tile_dirty(K054157_cur_tilemap, offset/4 + K054157_cur_offset);
/*TODO*///	} };
/*TODO*///	
/*TODO*///	public static ReadHandlerPtr K054157_r  = new ReadHandlerPtr() { public int handler(int offset)
/*TODO*///	{
/*TODO*///		logerror("K054157: unhandled read(%02x), pc=%08x\n", offset, cpu_get_pc());
/*TODO*///		return 0;
/*TODO*///	} };
/*TODO*///	
/*TODO*///	static void K054157_reset_scroll(void)
/*TODO*///	{
/*TODO*///		int i;
/*TODO*///		for(i=0; i<4; i++) {
/*TODO*///			tilemap_set_scrollx(K054157_tilemap[i], 0, K054157_scrollx[i] + (K054157_control0 & 0x20 ? K054157_scrolld[1][i][0] : K054157_scrolld[0][i][0]));
/*TODO*///			tilemap_set_scrolly(K054157_tilemap[i], 0, K054157_control0 & 0x20 ? K054157_scrolly[i] + K054157_scrolld[1][i][1] : K054157_scrolly[i] + K054157_scrolld[0][i][1]);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	static void K054157_set_scrolly(int plane, int pos)
/*TODO*///	{
/*TODO*///		if(K054157_scrolly[plane] != pos) {
/*TODO*///			K054157_scrolly[plane] = pos;
/*TODO*///			tilemap_set_scrolly(K054157_tilemap[plane], 0, K054157_control0 & 0x20 ? pos + K054157_scrolld[1][plane][1] : pos + K054157_scrolld[0][plane][1]);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	static void K054157_set_scrollx(int plane, int pos)
/*TODO*///	{
/*TODO*///		if(K054157_scrollx[plane] != pos) {
/*TODO*///			K054157_scrollx[plane] = pos;
/*TODO*///			tilemap_set_scrollx(K054157_tilemap[plane], 0, pos + (K054157_control0 & 0x20 ? K054157_scrolld[1][plane][0] : K054157_scrolld[0][plane][0]));
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	public static WriteHandlerPtr K054157_w = new WriteHandlerPtr() {public void handler(int offset, int data)
/*TODO*///	{
/*TODO*///		switch(offset) {
/*TODO*///		case 0x00:
/*TODO*///			data &= 0xff;
/*TODO*///			if(K054157_control0 != data) {
/*TODO*///				int flip;
/*TODO*///				K054157_control0 = data;
/*TODO*///				flip = 0;
/*TODO*///				if ((K054157_control0 & 0x20) != 0)
/*TODO*///					flip |= TILEMAP_FLIPY;
/*TODO*///				if ((K054157_control0 & 0x10) != 0)
/*TODO*///					flip |= TILEMAP_FLIPX;
/*TODO*///				tilemap_set_flip(K054157_tilemap[0], flip);
/*TODO*///				tilemap_set_flip(K054157_tilemap[1], flip);
/*TODO*///				tilemap_set_flip(K054157_tilemap[2], flip);
/*TODO*///				tilemap_set_flip(K054157_tilemap[3], flip);
/*TODO*///				K054157_reset_scroll();
/*TODO*///			}
/*TODO*///			break;
/*TODO*///		case 0x20:
/*TODO*///			K054157_set_scrolly(3, data);
/*TODO*///			break;
/*TODO*///		case 0x22:
/*TODO*///			K054157_set_scrolly(0, data);
/*TODO*///			break;
/*TODO*///		case 0x24:
/*TODO*///			K054157_set_scrolly(2, data);
/*TODO*///			break;
/*TODO*///		case 0x26:
/*TODO*///			K054157_set_scrolly(1, data);
/*TODO*///			break;
/*TODO*///		case 0x28:
/*TODO*///			K054157_set_scrollx(3, data);
/*TODO*///			break;
/*TODO*///		case 0x2a:
/*TODO*///			K054157_set_scrollx(0, data);
/*TODO*///			break;
/*TODO*///		case 0x2c:
/*TODO*///			K054157_set_scrollx(2, data);
/*TODO*///			break;
/*TODO*///		case 0x2e:
/*TODO*///			K054157_set_scrollx(1, data);
/*TODO*///			break;
/*TODO*///		case 0x32: {
/*TODO*///			data &= 0xff;
/*TODO*///			if ((data & 0xe6) != 0)
/*TODO*///				logerror("Graphic bankswitching to unknown bank %02x (pc=%08x)\n", data, cpu_get_pc());
/*TODO*///	
/*TODO*///			K054157_cur_rambank = data;
/*TODO*///			K054157_cur_rambase = K054157_rambase + (((data>>2) & 6) | (data & 1))*0x2000;
/*TODO*///			K054157_cur_tilemap = K054157_tilemap[((data>>3) & 2) | (data & 1)];
/*TODO*///			K054157_cur_offset = data & 8 ? 64*32 : 0;
/*TODO*///	
/*TODO*///			cpu_setbank(K054157_rambank, K054157_cur_rambase);
/*TODO*///			break;
/*TODO*///		}
/*TODO*///		case 0x34: {
/*TODO*///			K054157_cur_rombank = data % K054157_romnbbanks;
/*TODO*///			cpu_setbank(K054157_rombank, K054157_rombase + 0x2000*K054157_cur_rombank);
/*TODO*///			break;
/*TODO*///		}
/*TODO*///		default:
/*TODO*///			logerror("K054157: unhandled write(%02x, %04x), pc=%08x\n", offset, data & 0xffff, cpu_get_pc());
/*TODO*///		}
/*TODO*///	} };
/*TODO*///	
/*TODO*///	void K054157_tilemap_update(void)
/*TODO*///	{
/*TODO*///		for(K054157_cur_layer=0; K054157_cur_layer<4; K054157_cur_layer++) {
/*TODO*///			K054157_cur_lbase = K054157_rambase +
/*TODO*///				(K054157_cur_layer & 1 ? 0x2000 : 0) +
/*TODO*///				(K054157_cur_layer & 2 ? 0x8000 : 0);
/*TODO*///			tilemap_update(K054157_tilemap[K054157_cur_layer]);
/*TODO*///		}
/*TODO*///	}
/*TODO*///	
/*TODO*///	void K054157_tilemap_draw(struct osd_bitmap *bitmap, int num, int flags)
/*TODO*///	{
/*TODO*///		tilemap_draw(bitmap, K054157_tilemap[num], flags);
/*TODO*///	}
}
