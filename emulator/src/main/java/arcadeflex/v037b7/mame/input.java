/*
 * ported to 0.37b7 
 */
package arcadeflex.v037b7.mame;

//mame imports
import static arcadeflex.v037b7.mame.inputH.*;
import static arcadeflex.v037b7.mame.inptport.*;
import static arcadeflex.v037b7.mame.inptportH.*;
//to be organized
import static gr.codebb.arcadeflex.WIP.v037b7.mame.mame.Machine;
import static gr.codebb.arcadeflex.common.libc.cstring.strlen;
import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.UCLOCKS_PER_SEC;
import static gr.codebb.arcadeflex.old.arcadeflex.libc_old.uclock;
import static gr.codebb.arcadeflex.old.arcadeflex.input.osd_get_key_list;
import static gr.codebb.arcadeflex.old.arcadeflex.input.osd_is_key_pressed;

public class input {

    /* Codes */

 /* Subtype of codes */
    public static final int CODE_TYPE_NONE = 0;/* code not assigned */
    public static final int CODE_TYPE_KEYBOARD = 1;/* keyboard code */
    public static final int CODE_TYPE_JOYSTICK = 2;/* joystick code */

 /* Informations for every input code */
    public static class code_info {

        int memory;/* boolean memory */
        int oscode;/* osdepend code */
        int type;/* subtype: CODE_TYPE_KEYBOARD or CODE_TYPE_JOYSTICK */
    }

    /* Main code table, generic KEYCODE_*, JOYCODE_* are index in this table */
    public static code_info[] code_map;

    /* Size of the table */
    static int code_mac;

    /* Create the code table */
    public static int code_init() {
        int i;

        /* allocate */
        code_map = new code_info[(int) __code_max];//(struct code_info*)malloc( __code_max * sizeof(struct code_info) );
        for (int k = 0; k < code_map.length; k++) {
            code_map[k] = new code_info();
        }
        if (code_map == null) {
            return -1;
        }

        code_mac = 0;

        /* insert all known codes */
        for (i = 0; i < __code_max; ++i) {
            code_map[code_mac].memory = 0;
            code_map[code_mac].oscode = 0;
            /* not used */

            if (__code_key_first <= i && i <= __code_key_last) {
                code_map[code_mac].type = CODE_TYPE_KEYBOARD;
            } else if (__code_joy_first <= i && i <= __code_joy_last) {
                code_map[code_mac].type = CODE_TYPE_JOYSTICK;
            } else {
                code_map[code_mac].type = CODE_TYPE_NONE;
                /* never happen */
            }
            ++code_mac;
        }

        return 0;
    }

    /* Find the OSD record of a specific standard oscode */
    public static KeyboardInfo internal_oscode_find_keyboard(int oscode) {
        KeyboardInfo[] keyinfo;
        keyinfo = osd_get_key_list();
        int ptr = 0;
        while (keyinfo[ptr].name != null) {
            if (keyinfo[ptr].code == oscode) {
                return keyinfo[ptr];
            }
            ++ptr;
        }
        return null;
    }

    /*TODO*///INLINE const struct JoystickInfo* internal_oscode_find_joystick(unsigned oscode)
/*TODO*///{
/*TODO*///	const struct JoystickInfo *joyinfo;
/*TODO*///	joyinfo = osd_get_joy_list();
/*TODO*///	while (joyinfo->name)
/*TODO*///	{
/*TODO*///		if (joyinfo->code == oscode)
/*TODO*///			return joyinfo;
/*TODO*///		++joyinfo;
/*TODO*///	}
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
    /* Find a osdepend code in the table */
    static int internal_oscode_find(int oscode, int type) {
        int i;
        KeyboardInfo keyinfo;
        /*TODO*///	const struct JoystickInfo *joyinfo;

        /* Search on the main table */
        for (i = __code_max; i < code_mac; ++i) {
            if (code_map[i].type == type && code_map[i].oscode == oscode) {
                return i;
            }
        }

        /* Search in the OSD tables for a standard code */
        switch (type) {
            case CODE_TYPE_KEYBOARD:
                keyinfo = internal_oscode_find_keyboard(oscode);
                if (keyinfo != null && keyinfo.standardcode != CODE_OTHER) {
                    return keyinfo.standardcode;
                }
                break;
            case CODE_TYPE_JOYSTICK:
                throw new UnsupportedOperationException("CODE_TYPE unimplemented");
            /*TODO*///			joyinfo = internal_oscode_find_joystick(oscode);
/*TODO*///			if (joyinfo && joyinfo->standardcode != CODE_OTHER)
/*TODO*///				return joyinfo->standardcode;
/*TODO*///			break;
        }

        /* os code not found */
        return CODE_NONE;
    }

    /* Add a new oscode in the table */
    static int internal_oscode_add(int oscode, int type) {
        throw new UnsupportedOperationException("code_add_os unimplemented");
        /*TODO*///	struct code_info* new_code_map;
/*TODO*///	new_code_map = realloc( code_map, (code_mac+1) * sizeof(struct code_info) );
/*TODO*///	if (new_code_map)
/*TODO*///	{
/*TODO*///		code_map = new_code_map;
/*TODO*///		code_map[code_mac].memory = 0;
/*TODO*///		code_map[code_mac].oscode = oscode;
/*TODO*///		code_map[code_mac].type = type;
/*TODO*///		return code_mac++;
/*TODO*///	} else {
/*TODO*///		return CODE_NONE;
/*TODO*///        }
    }

    /* Find the osd record of a standard code */
    public static KeyboardInfo internal_code_find_keyboard(int code) {
        KeyboardInfo[] keyinfo;
        keyinfo = osd_get_key_list();
        int ptr = 0;
        if (code < __code_max) {
            while (keyinfo[ptr].name != null) {
                if (keyinfo[ptr].standardcode == code) {
                    return keyinfo[ptr];
                }
                ++ptr;
            }
        } else {
            while (keyinfo[ptr].name != null) {
                if (keyinfo[ptr].standardcode == CODE_OTHER && keyinfo[ptr].code == code_map[code].oscode) {
                    return keyinfo[ptr];
                }
                ++ptr;
            }
        }
        return null;
    }

    /*TODO*///INLINE const struct JoystickInfo* internal_code_find_joystick(InputCode code)
/*TODO*///{
/*TODO*///	const struct JoystickInfo *joyinfo;
/*TODO*///	joyinfo = osd_get_joy_list();
/*TODO*///
/*TODO*///	assert( code < code_mac );
/*TODO*///
/*TODO*///	if (code < __code_max)
/*TODO*///	{
/*TODO*///		while (joyinfo->name)
/*TODO*///		{
/*TODO*///			if (joyinfo->standardcode == code)
/*TODO*///				return joyinfo;
/*TODO*///			++joyinfo;
/*TODO*///		}
/*TODO*///	} else {
/*TODO*///		while (joyinfo->name)
/*TODO*///		{
/*TODO*///			if (joyinfo->standardcode == CODE_OTHER && joyinfo->code == code_map[code].oscode)
/*TODO*///				return joyinfo;
/*TODO*///			++joyinfo;
/*TODO*///		}
/*TODO*///	}
/*TODO*///	return 0;
/*TODO*///}

    /* Check if a code is pressed */
    public static int internal_code_pressed(int code) {
        KeyboardInfo keyinfo;
        /*TODO*///	const struct JoystickInfo *joyinfo;
/*TODO*///
        if (code < __code_max) {
            switch (code_map[code].type) {
                case CODE_TYPE_KEYBOARD:
                    keyinfo = internal_code_find_keyboard(code);
                    if (keyinfo != null) {
                        return osd_is_key_pressed(keyinfo.code);
                    }
                    break;
                /*TODO*///			case CODE_TYPE_JOYSTICK :
/*TODO*///				joyinfo = internal_code_find_joystick(code);
/*TODO*///				if (joyinfo)
/*TODO*///					return osd_is_joy_pressed(joyinfo->code);
/*TODO*///				break;
            }
        } else {
            switch (code_map[code].type) {
                case CODE_TYPE_KEYBOARD:
                    return osd_is_key_pressed(code_map[code].oscode);
                /*TODO*///			case CODE_TYPE_JOYSTICK :
/*TODO*///				return osd_is_joy_pressed(code_map[code].oscode);
            }
        }
        return 0;
    }

    /* Return the name of the code */
    public static String internal_code_name(int code) {
        KeyboardInfo keyinfo;
        /*TODO*///	const struct JoystickInfo *joyinfo;
/*TODO*///
        switch (code_map[code].type) {
            case CODE_TYPE_KEYBOARD:
                keyinfo = internal_code_find_keyboard(code);
                if (keyinfo != null) {
                    return keyinfo.name;
                }
                break;
            /*TODO*///		case CODE_TYPE_JOYSTICK :
/*TODO*///			joyinfo = internal_code_find_joystick(code);
/*TODO*///			if (joyinfo)
/*TODO*///				return joyinfo->name;
/*TODO*///			break;
        }
        return "n/a";
    }

    /* Update the code table */
    public static void internal_code_update() {
        KeyboardInfo[] keyinfo;
        /*TODO*///	const struct JoystickInfo *joyinfo;
/*TODO*///
        /* add only oscode because all standard codes are already present */

        keyinfo = osd_get_key_list();
        int ptr = 0;
        while (keyinfo[ptr].name != null) {
            if (keyinfo[ptr].standardcode == CODE_OTHER) {
                if (internal_oscode_find(keyinfo[ptr].code, CODE_TYPE_KEYBOARD) == CODE_NONE) {
                    internal_oscode_add(keyinfo[ptr].code, CODE_TYPE_KEYBOARD);
                }
            }
            ++ptr;
        }
        /*TODO*///
/*TODO*///	joyinfo = osd_get_joy_list();
/*TODO*///	while (joyinfo->name)
/*TODO*///	{
/*TODO*///		if (joyinfo->standardcode == CODE_OTHER)
/*TODO*///                        if (internal_oscode_find(joyinfo->code,CODE_TYPE_JOYSTICK)==CODE_NONE)
/*TODO*///				internal_oscode_add(joyinfo->code,CODE_TYPE_JOYSTICK);
/*TODO*///		++joyinfo;
/*TODO*///	}
    }

    /* Delete the code table */
    public static void code_close() {
        code_mac = 0;
        code_map = null;
    }

    /*TODO*///
/*TODO*////***************************************************************************/
/*TODO*////* Save support */
/*TODO*///
/*TODO*////* Flags used for saving codes to file */
/*TODO*///#define SAVECODE_FLAGS_TYPE_STANDARD 0x10000000 /* code */
/*TODO*///#define SAVECODE_FLAGS_TYPE_KEYBOARD 0x20000000 /* keyboard oscode */
/*TODO*///#define SAVECODE_FLAGS_TYPE_JOYSTICK 0x30000000 /* joystick oscode */
/*TODO*///#define SAVECODE_FLAGS_TYPE_MASK     0xF0000000
/*TODO*///
/*TODO*////* Convert one key oscode to one standard code */
/*TODO*///InputCode keyoscode_to_code(unsigned oscode)
/*TODO*///{
/*TODO*///	InputCode code;
/*TODO*///
/*TODO*///	code = internal_oscode_find(oscode,CODE_TYPE_KEYBOARD);
/*TODO*///
/*TODO*///	/* insert if missing */
/*TODO*///	if (code == CODE_NONE)
/*TODO*///		code = internal_oscode_add(oscode,CODE_TYPE_KEYBOARD);
/*TODO*///
/*TODO*///	return code;
/*TODO*///}
/*TODO*///
/*TODO*////* Convert one joystick oscode to one code */
/*TODO*///InputCode joyoscode_to_code(unsigned oscode)
/*TODO*///{
/*TODO*///	InputCode code = internal_oscode_find(oscode,CODE_TYPE_JOYSTICK);
/*TODO*///
/*TODO*///	/* insert if missing */
/*TODO*///	if (code == CODE_NONE)
/*TODO*///		code = internal_oscode_add(oscode,CODE_TYPE_JOYSTICK);
/*TODO*///
/*TODO*///	return code;
/*TODO*///}
/*TODO*///
/*TODO*////* Convert one saved code to one code */
/*TODO*///InputCode savecode_to_code(unsigned savecode)
/*TODO*///{
/*TODO*///	unsigned type = savecode & SAVECODE_FLAGS_TYPE_MASK;
/*TODO*///	InputCode code = savecode & ~SAVECODE_FLAGS_TYPE_MASK;
/*TODO*///
/*TODO*///	switch (type)
/*TODO*///	{
/*TODO*///		case SAVECODE_FLAGS_TYPE_STANDARD :
/*TODO*///			return code;
/*TODO*///		case SAVECODE_FLAGS_TYPE_KEYBOARD :
/*TODO*///			return keyoscode_to_code(code);
/*TODO*///		case SAVECODE_FLAGS_TYPE_JOYSTICK :
/*TODO*///			return joyoscode_to_code(code);
/*TODO*///	}
/*TODO*///
/*TODO*///	/* never happen */
/*TODO*///	assert(0);
/*TODO*///	return CODE_NONE;
/*TODO*///}
/*TODO*///
/*TODO*////* Convert one code to one saved code */
/*TODO*///unsigned code_to_savecode(InputCode code)
/*TODO*///{
/*TODO*///	if (code < __code_max || code >= code_mac)
/*TODO*///               	/* if greather than code_mac is a special CODE like CODE_OR */
/*TODO*///		return code | SAVECODE_FLAGS_TYPE_STANDARD;
/*TODO*///
/*TODO*///	switch (code_map[code].type)
/*TODO*///	{
/*TODO*///		case CODE_TYPE_KEYBOARD : return code_map[code].oscode | SAVECODE_FLAGS_TYPE_KEYBOARD;
/*TODO*///		case CODE_TYPE_JOYSTICK : return code_map[code].oscode | SAVECODE_FLAGS_TYPE_JOYSTICK;
/*TODO*///	}
/*TODO*///
/*TODO*///	/* never happen */
/*TODO*///	assert(0);
/*TODO*///	return 0;
/*TODO*///}
    /**
     * ************************************************************************
     */
    /* Interface */
    public static String code_name(int code) {
        if (code < code_mac) {
            return internal_code_name(code);
        }

        switch (code) {
            case CODE_NONE:
                return "None";
            case CODE_NOT:
                return "not";
            case CODE_OR:
                return "or";
        }

        return "n/a";
    }

    public static int code_pressed(int code) {
        int pressed;

        pressed = internal_code_pressed(code);

        return pressed;
    }

    public static int code_pressed_memory(int code) {
        int pressed;

        pressed = internal_code_pressed(code);

        if (pressed != 0) {
            if (code_map[code].memory == 0) {
                code_map[code].memory = 1;
            } else {
                pressed = 0;
            }
        } else {
            code_map[code].memory = 0;
        }

        return pressed;
    }

    /* Report the pressure only if isn't already signaled with one of the */
 /* functions code_memory and code_memory_repeat */
    public static int code_pressed_not_memorized(int code) {
        int pressed;

        pressed = internal_code_pressed(code);

        if (pressed != 0) {
            if (code_map[code].memory != 0) {
                pressed = 0;
            }
        } else {
            code_map[code].memory = 0;
        }

        return pressed;
    }

    static int counter_c;
    static int keydelay_c;

    public static int code_pressed_memory_repeat(int code, int speed) {

        int pressed;

        pressed = internal_code_pressed(code);

        if (pressed != 0) {
            if (code_map[code].memory == 0) {
                code_map[code].memory = 1;
                keydelay_c = 3;
                counter_c = 0;
            } else if (++counter_c > keydelay_c * speed * Machine.drv.frames_per_second / 60) {
                keydelay_c = 1;
                counter_c = 0;
            } else {
                pressed = 0;
            }
        } else {
            code_map[code].memory = 0;
        }

        return pressed;
    }

    public static int code_read_async() {
        int i;

        /* Update the table */
        internal_code_update();

        for (i = 0; i < code_mac; ++i) {
            if (code_pressed_memory(i) != 0) {
                return i;
            }
        }

        return CODE_NONE;
    }

    /*TODO*///
/*TODO*////* returns the numerical value of a typed hex digit, or -1 if none */
/*TODO*///INT8 code_read_hex_async(void)
/*TODO*///{
/*TODO*///	unsigned i;
/*TODO*///
/*TODO*///	profiler_mark(PROFILER_INPUT);
/*TODO*///
/*TODO*///	/* update the table */
/*TODO*///	internal_code_update();
/*TODO*///
/*TODO*///	for(i=0;i<code_mac;++i)
/*TODO*///		if (code_pressed_memory(i))
/*TODO*///		{
/*TODO*///			if ((i >= KEYCODE_A) && (i <= KEYCODE_F))
/*TODO*///				return i - KEYCODE_A + 10;
/*TODO*///			else if ((i >= KEYCODE_0) && (i <= KEYCODE_9))
/*TODO*///				return i - KEYCODE_0;
/*TODO*///			else
/*TODO*///				return -1;
/*TODO*///		}
/*TODO*///
/*TODO*///	profiler_mark(PROFILER_END);
/*TODO*///
/*TODO*///	return -1;
/*TODO*///}
    /**
     * ************************************************************************
     */
    /* Sequences */
    public static void seq_set_0(int[] a) {
        for (int j = 0; j < SEQ_MAX; ++j) {
            a[j] = CODE_TYPE_NONE;
        }
    }

    public static void seq_set_1(int[] a, int code) {
        int j;
        a[0] = code;
        for (j = 1; j < SEQ_MAX; ++j) {
            a[j] = CODE_NONE;
        }
    }

    public static void seq_set_2(int[] a, int code1, int code2) {
        int j;
        a[0] = code1;
        a[1] = code2;
        for (j = 2; j < SEQ_MAX; ++j) {
            a[j] = CODE_NONE;
        }
    }

    public static void seq_set_3(int[] a, int code1, int code2, int code3) {
        int j;
        a[0] = code1;
        a[1] = code2;
        a[2] = code3;
        for (j = 3; j < SEQ_MAX; ++j) {
            a[j] = CODE_NONE;
        }
    }

    /*TODO*///
/*TODO*///void seq_copy(InputSeq* a, InputSeq* b)
/*TODO*///{
/*TODO*///	int j;
/*TODO*///	for(j=0;j<SEQ_MAX;++j)
/*TODO*///		(*a)[j] = (*b)[j];
/*TODO*///}
/*TODO*///
/*TODO*///int seq_cmp(InputSeq* a, InputSeq* b)
/*TODO*///{
/*TODO*///	int j;
/*TODO*///	for(j=0;j<SEQ_MAX;++j)
/*TODO*///		if ((*a)[j] != (*b)[j])
/*TODO*///			return -1;
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
    static String seq_name(int[] code, int max) {
        int j;
        String buffer = "";
        StringBuilder dest = new StringBuilder();
        for (j = 0; j < SEQ_MAX; ++j) {
            String name;

            if ((code)[j] == CODE_NONE) {
                break;
            }

            if (j != 0 && 1 + 1 <= max) {
                dest.append(' ');//*dest = ' ';
                //dest += 1;
                max -= 1;
            }

            name = code_name(code[j]);
            if (name == null) {
                break;
            }

            if (name.length() + 1 <= max)//if (strlen(name) + 1 <= max)
            {
                dest.append(name);
                //dest += strlen(name);
                max -= strlen(name);
            }
        }
        if (dest.toString().equals(buffer) && 4 + 1 <= max) {
            return dest.append("None").toString();
        } else {
            return dest.toString();
        }
    }

    public static boolean seq_pressed(int[] code) {
        int j;
        boolean res = true;
        boolean invert = false;
        int count = 0;

        for (j = 0; j < SEQ_MAX; ++j) {
            switch (code[j]) {
                case CODE_NONE:
                    return res && count != 0;
                case CODE_OR:
                    if (res && count != 0) {
                        return true;
                    }
                    res = true;
                    count = 0;
                    break;
                case CODE_NOT:
                    invert = !invert;
                    break;
                default:
                    if (res) {
                        int pressed = code_pressed_not_memorized(code[j]);
                        if ((pressed != 0) == invert) {
                            res = false;
                        }
                    }
                    invert = false;
                    ++count;
            }
        }
        return res && count != 0;
    }

    /* Static informations used in key/joy recording */
    static int[] record_seq = new int[SEQ_MAX];/* buffer for key recording */
    static int record_count;/* number of key/joy press recorded */
    static long record_last;/* time of last key/joy press */

    public static final int RECORD_TIME = (UCLOCKS_PER_SEC * 2 / 3);/* max time between key press */

 /* Start a sequence recording */
    public static void seq_read_async_start() {
        int i;

        record_count = 0;
        record_last = uclock();

        /* reset code memory, otherwise this memory may interferes with the input memory */
        for (i = 0; i < code_mac; ++i) {
            code_map[i].memory = 1;
        }
    }

    /* Check that almost one key/joy must be pressed */
    static boolean seq_valid(int[] seq) {
        int j;
        boolean positive = false;
        boolean pred_not = false;
        boolean operand = false;
        for (j = 0; j < SEQ_MAX; ++j) {
            switch ((seq)[j]) {
                case CODE_NONE:
                    break;
                case CODE_OR:
                    if (!operand || !positive) {
                        return false;
                    }
                    pred_not = false;
                    positive = false;
                    operand = false;
                    break;
                case CODE_NOT:
                    if (pred_not) {
                        return false;
                    }
                    pred_not = !pred_not;
                    operand = false;
                    break;
                default:
                    if (!pred_not) {
                        positive = true;
                    }
                    pred_not = false;
                    operand = true;
                    break;
            }
        }
        return positive && operand;
    }

    /* Record a key/joy sequence
    	return <0 if more input is needed
    	return ==0 if sequence succesfully recorded
    	return >0 if aborted
     */
    static int seq_read_async(int[] seq, int first) {
        int newkey;

        if (input_ui_pressed(IPT_UI_CANCEL) != 0) {
            return 1;
        }

        if (record_count == SEQ_MAX
                || (record_count > 0 && uclock() > record_last + RECORD_TIME)) {
            int k = 0;
            if (first == 0) {
                /* search the first space free */
                while (k < SEQ_MAX && (seq)[k] != CODE_NONE) {
                    ++k;
                }
            }

            /* if no space restart */
            if (k + record_count + ((k != 0) ? 1 : 0) > SEQ_MAX) {
                k = 0;
            }

            /* insert */
            if (k + record_count + ((k != 0) ? 1 : 0) <= SEQ_MAX) {
                int j;
                if (k != 0) {
                    (seq)[k++] = CODE_OR;
                }
                for (j = 0; j < record_count; ++j, ++k) {
                    (seq)[k] = record_seq[j];
                }
            }
            /* fill to end */
            while (k < SEQ_MAX) {
                (seq)[k] = CODE_NONE;
                ++k;
            }

            if (!seq_valid(seq)) {
                seq_set_1(seq, CODE_NONE);
            }

            return 0;
        }

        newkey = code_read_async();

        if (newkey != CODE_NONE) {
            /* if code is duplicate negate the code */
            if (record_count != 0 && newkey == record_seq[record_count - 1]) {
                record_seq[record_count - 1] = CODE_NOT;
            }

            record_seq[record_count++] = newkey;
            record_last = uclock();
        }

        return -1;
    }

    /**
     * ************************************************************************
     */
    /* input ui */

 /* Static buffer for memory input */
    public static class ui_info {

        int memory;

        public static ui_info[] create(int n) {
            ui_info[] a = new ui_info[n];
            for (int k = 0; k < n; k++) {
                a[k] = new ui_info();
            }
            return a;
        }
    }
    static ui_info[] ui_map = ui_info.create(__ipt_max);

    public static int input_ui_pressed(int code) {

        int pressed;

        pressed = seq_pressed(input_port_type_seq(code)) ? 1 : 0;

        if (pressed != 0) {
            if (ui_map[code].memory == 0) {
                ui_map[code].memory = 1;
            } else {
                pressed = 0;
            }
        } else {
            ui_map[code].memory = 0;
        }
        return pressed;
    }
    static int repeat_counter, repeat_inputdelay;

    public static int input_ui_pressed_repeat(int code, int speed) {

        int pressed;

        pressed = seq_pressed(input_port_type_seq(code)) ? 1 : 0;

        if (pressed != 0) {
            if (ui_map[code].memory == 0) {
                ui_map[code].memory = 1;
                repeat_inputdelay = 3;
                repeat_counter = 0;
            } else if (++repeat_counter > repeat_inputdelay * speed * Machine.drv.frames_per_second / 60) {
                repeat_inputdelay = 1;
                repeat_counter = 0;
            } else {
                pressed = 0;
            }
        } else {
            ui_map[code].memory = 0;
        }

        return pressed;
    }
}
