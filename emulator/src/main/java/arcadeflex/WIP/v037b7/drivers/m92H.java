package arcadeflex.WIP.v037b7.drivers;

import static arcadeflex.v037b7.mame.inptportH.*;

public class m92H {
    /*******************************************************************************

            Irem M92 input port macros

            Can probably be used for other Irem games..

    *******************************************************************************/

    static void PORT_PLAYER1_2BUTTON_JOYSTICK() {
            PORT_START(); 	
            PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY | IPF_PLAYER1 );
            PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY | IPF_PLAYER1 );
            PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY | IPF_PLAYER1 );
            PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER1 );
            PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_UNUSED );
            PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_UNUSED );
            PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER1 );
            PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER1 );
    }

    static void PORT_PLAYER2_2BUTTON_JOYSTICK() { 
            PORT_START(); 	
            PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY | IPF_PLAYER2 );
            PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY | IPF_PLAYER2 );
            PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY | IPF_PLAYER2 );
            PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER2 );
            PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_UNUSED );
            PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_UNUSED );
            PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER2 );
            PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER2 );
    }

    static void PORT_PLAYER3_2BUTTON_JOYSTICK() { 
            PORT_START(); 	
            PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY | IPF_PLAYER3 );
            PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY | IPF_PLAYER3 );
            PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY | IPF_PLAYER3 );
            PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER3 );
            PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_START3 );/* If common slots, Coin3 if separate */ 
            PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER3 );
            PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER3 );
    }

    static void PORT_PLAYER4_2BUTTON_JOYSTICK() { 
            PORT_START(); 	
            PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_JOYSTICK_UP    | IPF_8WAY | IPF_PLAYER4 );
            PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_JOYSTICK_DOWN  | IPF_8WAY | IPF_PLAYER4 );
            PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_JOYSTICK_LEFT  | IPF_8WAY | IPF_PLAYER4 );
            PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_JOYSTICK_RIGHT | IPF_8WAY | IPF_PLAYER4 );
            PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_START4 );/* If common slots, Coin4 is separate */ 
            PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_UNKNOWN );
            PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_BUTTON2 | IPF_PLAYER4 );
            PORT_BIT( 0x80, IP_ACTIVE_LOW, IPT_BUTTON1 | IPF_PLAYER4 );
    }

    static void PORT_COINS_VBLANK() { 
            PORT_START(); 	
            PORT_BIT( 0x01, IP_ACTIVE_LOW, IPT_START1 );
            PORT_BIT( 0x02, IP_ACTIVE_LOW, IPT_START2 );
            PORT_BIT( 0x04, IP_ACTIVE_LOW, IPT_COIN1 );
            PORT_BIT( 0x08, IP_ACTIVE_LOW, IPT_COIN2 );
            PORT_BIT( 0x10, IP_ACTIVE_LOW, IPT_UNKNOWN );/* Coin3 if 2 Players */ 
            PORT_BIT( 0x20, IP_ACTIVE_LOW, IPT_UNKNOWN ); 
            PORT_BIT( 0x40, IP_ACTIVE_LOW, IPT_UNKNOWN ); 
            PORT_BIT( 0x80, IP_ACTIVE_HIGH, IPT_UNUSED );/* Actually vblank, handled above */ 
    }

    static void PORT_SYSTEM_DIPSWITCH() { 
            PORT_START();  
            PORT_DIPNAME( 0x01, 0x01, DEF_STR( "Flip_Screen") ); 
            PORT_DIPSETTING(    0x01, DEF_STR( "Off") ); 
            PORT_DIPSETTING(    0x00, DEF_STR( "On") ); 
            PORT_DIPNAME( 0x02, 0x02, DEF_STR( "Cabinet") ); 
            PORT_DIPSETTING(    0x02, "2 Players" );
            PORT_DIPSETTING(    0x00, "4 Players" );
            PORT_DIPNAME( 0x04, 0x04, "Coin Slots" );
            PORT_DIPSETTING(    0x04, "Common" );
            PORT_DIPSETTING(    0x00, "Seperate" );
            PORT_DIPNAME( 0x08, 0x08, "Coin Mode" );/* Default 1 */ 
            PORT_DIPSETTING(    0x08, "1" );
            PORT_DIPSETTING(    0x00, "2" );
            /* Coin Mode 1 */ 
            PORT_DIPNAME( 0xf0, 0xf0, DEF_STR( "Coinage") ); 
            PORT_DIPSETTING(    0x50, DEF_STR( "6C_1C") ); 
            PORT_DIPSETTING(    0x40, DEF_STR( "5C_1C") ); 
            PORT_DIPSETTING(    0x30, DEF_STR( "4C_1C") ); 
            PORT_DIPSETTING(    0x20, DEF_STR( "3C_1C") ); 
            PORT_DIPSETTING(    0x10, DEF_STR( "2C_1C") ); 
            PORT_DIPSETTING(    0xe0, "2 to Start/1 to Continue" );
            PORT_DIPSETTING(    0xc0, DEF_STR( "3C_2C") ); 
            PORT_DIPSETTING(    0xd0, DEF_STR( "4C_3C") ); 
            PORT_DIPSETTING(    0xf0, DEF_STR( "1C_1C") ); 
            PORT_DIPSETTING(    0xb0, DEF_STR( "2C_3C") ); 
            PORT_DIPSETTING(    0x60, DEF_STR( "1C_2C") ); 
            PORT_DIPSETTING(    0x70, DEF_STR( "1C_3C") ); 
            PORT_DIPSETTING(    0x80, DEF_STR( "1C_4C") ); 
            PORT_DIPSETTING(    0x90, DEF_STR( "1C_5C") ); 
            PORT_DIPSETTING(    0xa0, DEF_STR( "1C_6C") ); 
            PORT_DIPSETTING(    0x00, DEF_STR( "Free_Play") );

/*TODO*///    #if 0
/*TODO*///            /* Coin Mode 2 */ 
/*TODO*///            PORT_DIPNAME( 0x30, 0x30, DEF_STR( "Coin_A") ); 
/*TODO*///            PORT_DIPSETTING(    0x00, DEF_STR( "5C_1C") ); 
/*TODO*///            PORT_DIPSETTING(    0x10, DEF_STR( "3C_1C") ); 
/*TODO*///            PORT_DIPSETTING(    0x20, DEF_STR( "2C_1C") ); 
/*TODO*///            PORT_DIPSETTING(    0x30, DEF_STR( "1C_1C") ); 
/*TODO*///            PORT_DIPNAME( 0xc0, 0xc0, DEF_STR( "Coin_B") ); 
/*TODO*///            PORT_DIPSETTING(    0xc0, DEF_STR( "1C_2C") ); 
/*TODO*///            PORT_DIPSETTING(    0x80, DEF_STR( "1C_3C") ); 
/*TODO*///            PORT_DIPSETTING(    0x40, DEF_STR( "1C_5C") ); 
/*TODO*///            PORT_DIPSETTING(    0x00, DEF_STR( "1C_6C") );
/*TODO*///    #endif
    }


    static void PORT_UNUSED() { 
            PORT_START(); 	
            PORT_BIT( 0xff, IP_ACTIVE_LOW, IPT_UNUSED );
    }

    
}
