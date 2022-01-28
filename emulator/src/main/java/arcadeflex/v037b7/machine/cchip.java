/*
 * ported to v0.37b7
 * ported to v0.36
 * 
 *
 */
package arcadeflex.v037b7.machine;

//common imports
import static arcadeflex.common.ptrLib.*;
//generic imports
import static arcadeflex.v037b7.generic.funcPtr.*;
//mame imports
import static arcadeflex.v037b7.mame.cpuintrf.*;
import static arcadeflex.v037b7.mame.inptport.*;
//to be organized
import static gr.codebb.arcadeflex.old.arcadeflex.osdepend.logerror;

public class cchip {

    static int cchip1_bank;
    static int[] cchip = new int[3];

    /* Circular buffer, not FILO. Doh!. */
    static int cchip1_code[]
            = {
                0x48, 0xe7, 0x80, 0x80, /* MOVEM.L D0/A0,-(A7)    ( Preserve Regs ) */
                0x20, 0x6d, 0x1c, 0x40, /* MOVEA.L ($1C40,A5),A0  ( Load sound pointer in A0 ) */
                0x30, 0x2f, 0x00, 0x0c, /* MOVE.W ($0C,A7),D0	  ( Fetch sound number ) */
                0x10, 0x80, /* MOVE.B D0,(A0)		  ( Store it on sound pointer ) */
                0x52, 0x88, /* ADDQ.W #1,A0			  ( Increment sound pointer ) */
                0x20, 0x3c, 0x00, 0xf0, 0x1c, 0x40, /* MOVE.L #$F01C40,D0	  ( Load top of buffer in D0 ) */
                0xb1, 0xc0, /* CMPA.L   D0,A0		  ( Are we there yet? ) */
                0x66, 0x04, /* BNE.S    *+$6		  ( No, we arent, skip next line ) */
                0x41, 0xed, 0x1c, 0x20, /* LEA      ($1C20,A5),A0 ( Point to the start of the buffer ) */
                0x2b, 0x48, 0x1c, 0x40, /* MOVE.L A0,($1C40,A5)   ( Store new sound pointer ) */
                0x4c, 0xdf, 0x01, 0x01, /* MOVEM.L (A7)+, D0/A0	  ( Restore Regs ) */
                0x4e, 0x75, /* RTS					  ( Return ) */};

    public static InitMachinePtr cchip1_init_machine = new InitMachinePtr() {
        public void handler() {
            /* init custom cchip values */
            cchip[0] = cchip[1] = cchip[2] = 0;

            /* make sure we point to controls */
            cchip1_bank = 0;
        }
    };

    public static ReadHandlerPtr cchip1_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int ret = 0;

            switch (offset) {
                case 0x000:
                    /* Player 1 */
                    if (cchip1_bank == 1) {
                        ret = cchip1_code[offset / 2];
                    } else if (cchip[0] != 0) {
                        ret = cchip[0];
                        cchip[0] = 0;
                    } else {
                        ret = readinputport(4);
                    }
                    break;
                case 0x002:
                    /* Player 2 */
                    if (cchip1_bank == 1) {
                        ret = cchip1_code[offset / 2];
                    } else if (cchip[1] != 0) {
                        ret = cchip[1];
                        cchip[1] = 0;
                    } else {
                        ret = readinputport(5);
                    }
                    break;
                case 0x004:
                    /* Coins */
                    logerror("cchip1_r (coin) pc: %06x, offset: %04x\n", cpu_get_pc(), offset);
                    if (cchip1_bank == 1) {
                        ret = cchip1_code[offset / 2];
                    } else if (cchip[2] != 0) {
                        ret = cchip[2];
                        cchip[2] = 0;
                    } else {
                        ret = readinputport(6);
                    }
                    break;
                case 0x802:
                    /* C-Chip ID */
                    ret = 0x01;
                    break;
                case 0xc00:
                    ret = cchip1_bank;
                    break;
                default:
                    if (offset < 0x1f0 && cchip1_bank == 1) {
                        if ((offset / 2) > 39)//check if the it's out of bounds (shadow)
                        {
                            ret = 0;
                        } else {
                            ret = cchip1_code[offset / 2];
                        }
                    } else {
                        logerror("cchip1_r offset: %04x\n", offset);
                        ret = 0xff;
                    }
                    break;
            }

            return ret;
        }
    };

    public static WriteHandlerPtr cchip1_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            logerror("cchip1_w pc: %06x, %04x:%02x\n", cpu_get_pc(), offset, data);
            switch (offset) {
                case 0x0000:
                    if ((data & 0xff) == 0x4a) {
                        cchip[0] = 0x47;
                    } else {
                        cchip[0] = data;
                    }
                    break;

                case 0x0002:
                    if ((data & 0xff) == 0x46) {
                        cchip[1] = 0x57;
                    } else {
                        cchip[1] = data;
                    }
                    break;

                case 0x0004:
                    if ((data & 0xff) == 0x34) {
                        cchip[2] = 0x4b;
                    } else {
                        cchip[2] = data;
                    }
                    break;
                case 0xc00:
                    cchip1_bank = data & 0x07;
                    break;
                default:
                    break;
            }
        }
    };

    /* Mega Blast */
    public static UBytePtr cchip_ram = new UBytePtr();

    public static ReadHandlerPtr cchip2_r = new ReadHandlerPtr() {
        public int handler(int offset) {
            int ret = 0;

            switch (offset) {
                case 0x802:
                    /* C-Chip ID */
                    ret = 0x01;
                    break;
                default:
                    logerror("cchip2_r offset: %04x\n", offset);
                    ret = cchip_ram.read(offset);
                    break;
            }

            return ret;
        }
    };

    public static WriteHandlerPtr cchip2_w = new WriteHandlerPtr() {
        public void handler(int offset, int data) {
            logerror("cchip2_w pc: %06x, %04x:%02x\n", cpu_get_pc(), offset, data);
            cchip_ram.write(offset, data);
        }
    };
}