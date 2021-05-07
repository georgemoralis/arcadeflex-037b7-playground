/*
 * ported to v0.37b7
*/
package gr.codebb.arcadeflex.v037b7.sound;

public class tms5220 {
/*TODO*////* these contain data that describes the 128-bit data FIFO */
/*TODO*///#define FIFO_SIZE 16
/*TODO*///static UINT8 fifo[FIFO_SIZE];
/*TODO*///static UINT8 fifo_head;
/*TODO*///static UINT8 fifo_tail;
/*TODO*///static UINT8 fifo_count;
/*TODO*///static UINT8 bits_taken;
/*TODO*///
/*TODO*///
/*TODO*////* these contain global status bits */
/*TODO*///static UINT8 speak_external;
/*TODO*///static UINT8 speak_delay_frames;
/*TODO*///static UINT8 talk_status;
/*TODO*///static UINT8 buffer_low;
/*TODO*///static UINT8 buffer_empty;
/*TODO*///static UINT8 irq_pin;
/*TODO*///
/*TODO*///static void (*irq_func)(int state);
/*TODO*///
/*TODO*///
/*TODO*////* these contain data describing the current and previous voice frames */
/*TODO*///static UINT16 old_energy;
/*TODO*///static UINT16 old_pitch;
/*TODO*///static int old_k[10];
/*TODO*///
/*TODO*///static UINT16 new_energy;
/*TODO*///static UINT16 new_pitch;
/*TODO*///static int new_k[10];
/*TODO*///
/*TODO*///
/*TODO*////* these are all used to contain the current state of the sound generation */
/*TODO*///static UINT16 current_energy;
/*TODO*///static UINT16 current_pitch;
/*TODO*///static int current_k[10];
/*TODO*///
/*TODO*///static UINT16 target_energy;
/*TODO*///static UINT16 target_pitch;
/*TODO*///static int target_k[10];
/*TODO*///
/*TODO*///static UINT8 interp_count;       /* number of interp periods (0-7) */
/*TODO*///static UINT8 sample_count;       /* sample number within interp (0-24) */
/*TODO*///static int pitch_count;
/*TODO*///
/*TODO*///static int u[11];
/*TODO*///static int x[10];
/*TODO*///
/*TODO*///static INT8 randbit;
/*TODO*///
/*TODO*///
/*TODO*////* Static function prototypes */
/*TODO*///static void process_command(void);
/*TODO*///static int extract_bits(int count);
/*TODO*///static int parse_frame(int removeit);
/*TODO*///static void check_buffer_low(void);
/*TODO*///static void set_interrupt_state(int state);
/*TODO*///
/*TODO*///
/*TODO*///#define DEBUG_5220	0
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     tms5220_reset -- resets the TMS5220
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///void tms5220_reset(void)
/*TODO*///{
/*TODO*///    /* initialize the FIFO */
/*TODO*///    memset(fifo, 0, sizeof(fifo));
/*TODO*///    fifo_head = fifo_tail = fifo_count = bits_taken = 0;
/*TODO*///
/*TODO*///    /* initialize the chip state */
/*TODO*///    speak_external = speak_delay_frames = talk_status = irq_pin = 0;
/*TODO*///	buffer_empty = buffer_low = 1;
/*TODO*///
/*TODO*///    /* initialize the energy/pitch/k states */
/*TODO*///    old_energy = new_energy = current_energy = target_energy = 0;
/*TODO*///    old_pitch = new_pitch = current_pitch = target_pitch = 0;
/*TODO*///    memset(old_k, 0, sizeof(old_k));
/*TODO*///    memset(new_k, 0, sizeof(new_k));
/*TODO*///    memset(current_k, 0, sizeof(current_k));
/*TODO*///    memset(target_k, 0, sizeof(target_k));
/*TODO*///
/*TODO*///    /* initialize the sample generators */
/*TODO*///    interp_count = sample_count = pitch_count = 0;
/*TODO*///    randbit = 0;
/*TODO*///    memset(u, 0, sizeof(u));
/*TODO*///    memset(x, 0, sizeof(x));
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     tms5220_reset -- reset the TMS5220
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///void tms5220_set_irq(void (*func)(int))
/*TODO*///{
/*TODO*///    irq_func = func;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     tms5220_data_write -- handle a write to the TMS5220
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///void tms5220_data_write(int data)
/*TODO*///{
/*TODO*///    /* add this byte to the FIFO */
/*TODO*///    if (fifo_count < FIFO_SIZE)
/*TODO*///    {
/*TODO*///        fifo[fifo_tail] = data;
/*TODO*///        fifo_tail = (fifo_tail + 1) % FIFO_SIZE;
/*TODO*///        fifo_count++;
/*TODO*///
/*TODO*///		/* if we were speaking, then we're no longer empty */
/*TODO*///		if (speak_external)
/*TODO*///			buffer_empty = 0;
/*TODO*///
/*TODO*///        if (DEBUG_5220) logerror("Added byte to FIFO (size=%2d)\n", fifo_count);
/*TODO*///    }
/*TODO*///    else
/*TODO*///    {
/*TODO*///        if (DEBUG_5220) logerror("Ran out of room in the FIFO!\n");
/*TODO*///    }
/*TODO*///
/*TODO*///    /* update the buffer low state */
/*TODO*///    check_buffer_low();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     tms5220_status_read -- read status from the TMS5220
/*TODO*///
/*TODO*///	  From the data sheet:
/*TODO*///        bit 0 = TS - Talk Status is active (high) when the VSP is processing speech data.
/*TODO*///                Talk Status goes active at the initiation of a Speak command or after nine
/*TODO*///                bytes of data are loaded into the FIFO following a Speak External command. It
/*TODO*///                goes inactive (low) when the stop code (Energy=1111) is processed, or
/*TODO*///                immediately by a buffer empty condition or a reset command.
/*TODO*///        bit 1 = BL - Buffer Low is active (high) when the FIFO buffer is more than half empty.
/*TODO*///                Buffer Low is set when the "Last-In" byte is shifted down past the half-full
/*TODO*///                boundary of the stack. Buffer Low is cleared when data is loaded to the stack
/*TODO*///                so that the "Last-In" byte lies above the half-full boundary and becomes the
/*TODO*///                ninth data byte of the stack.
/*TODO*///        bit 2 = BE - Buffer Empty is active (high) when the FIFO buffer has run out of data
/*TODO*///                while executing a Speak External command. Buffer Empty is set when the last bit
/*TODO*///                of the "Last-In" byte is shifted out to the Synthesis Section. This causes
/*TODO*///                Talk Status to be cleared. Speed is terminated at some abnormal point and the
/*TODO*///                Speak External command execution is terminated.
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///int tms5220_status_read(void)
/*TODO*///{
/*TODO*///    /* clear the interrupt pin */
/*TODO*///    set_interrupt_state(0);
/*TODO*///
/*TODO*///    if (DEBUG_5220) logerror("Status read: TS=%d BL=%d BE=%d\n", talk_status, buffer_low, buffer_empty);
/*TODO*///
/*TODO*///    return (talk_status << 7) | (buffer_low << 6) | (buffer_empty << 5);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     tms5220_ready_read -- returns the ready state of the TMS5220
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///int tms5220_ready_read(void)
/*TODO*///{
/*TODO*///    return (fifo_count < FIFO_SIZE-1);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     tms5220_int_read -- returns the interrupt state of the TMS5220
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///int tms5220_int_read(void)
/*TODO*///{
/*TODO*///    return irq_pin;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     tms5220_process -- fill the buffer with a specific number of samples
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///void tms5220_process(INT16 *buffer, unsigned int size)
/*TODO*///{
/*TODO*///    int buf_count=0;
/*TODO*///    int i, interp_period;
/*TODO*///
/*TODO*///tryagain:
/*TODO*///
/*TODO*///    /* if we're not speaking, parse commands */
/*TODO*///    while (!speak_external && fifo_count > 0)
/*TODO*///        process_command();
/*TODO*///
/*TODO*///    /* if we're empty and still not speaking, fill with nothingness */
/*TODO*///    if (!speak_external)
/*TODO*///        goto empty;
/*TODO*///
/*TODO*///    /* if we're to speak, but haven't started, wait for the 9th byte */
/*TODO*///    if (!talk_status)
/*TODO*///    {
/*TODO*///        if (fifo_count < 9)
/*TODO*///           goto empty;
/*TODO*///
/*TODO*///        /* parse but don't remove the first frame, and set the status to 1 */
/*TODO*///        parse_frame(0);
/*TODO*///        talk_status = 1;
/*TODO*///        buffer_empty = 0;
/*TODO*///    }
/*TODO*///
/*TODO*///    /* apply some delay before we actually consume data; Victory requires this */
/*TODO*///    if (speak_delay_frames)
/*TODO*///    {
/*TODO*///    	if (size <= speak_delay_frames)
/*TODO*///    	{
/*TODO*///    		speak_delay_frames -= size;
/*TODO*///    		size = 0;
/*TODO*///    	}
/*TODO*///    	else
/*TODO*///    	{
/*TODO*///    		size -= speak_delay_frames;
/*TODO*///    		speak_delay_frames = 0;
/*TODO*///    	}
/*TODO*///    }
/*TODO*///
/*TODO*///    /* loop until the buffer is full or we've stopped speaking */
/*TODO*///    while ((size > 0) && speak_external)
/*TODO*///    {
/*TODO*///        int current_val;
/*TODO*///
/*TODO*///        /* if we're ready for a new frame */
/*TODO*///        if ((interp_count == 0) && (sample_count == 0))
/*TODO*///        {
/*TODO*///            /* Parse a new frame */
/*TODO*///            if (!parse_frame(1))
/*TODO*///                break;
/*TODO*///
/*TODO*///            /* Set old target as new start of frame */
/*TODO*///            current_energy = old_energy;
/*TODO*///            current_pitch = old_pitch;
/*TODO*///            for (i = 0; i < 10; i++)
/*TODO*///                current_k[i] = old_k[i];
/*TODO*///
/*TODO*///            /* is this a zero energy frame? */
/*TODO*///            if (current_energy == 0)
/*TODO*///            {
/*TODO*///                /*printf("processing frame: zero energy\n");*/
/*TODO*///                target_energy = 0;
/*TODO*///                target_pitch = current_pitch;
/*TODO*///                for (i = 0; i < 10; i++)
/*TODO*///                    target_k[i] = current_k[i];
/*TODO*///            }
/*TODO*///
/*TODO*///            /* is this a stop frame? */
/*TODO*///            else if (current_energy == (energytable[15] >> 6))
/*TODO*///            {
/*TODO*///                /*printf("processing frame: stop frame\n");*/
/*TODO*///                current_energy = energytable[0] >> 6;
/*TODO*///                target_energy = current_energy;
/*TODO*///                speak_external = talk_status = 0;
/*TODO*///                interp_count = sample_count = pitch_count = 0;
/*TODO*///
/*TODO*///                /* generate an interrupt if necessary */
/*TODO*///                set_interrupt_state(1);
/*TODO*///
/*TODO*///                /* try to fetch commands again */
/*TODO*///                goto tryagain;
/*TODO*///            }
/*TODO*///            else
/*TODO*///            {
/*TODO*///                /* is this the ramp down frame? */
/*TODO*///                if (new_energy == (energytable[15] >> 6))
/*TODO*///                {
/*TODO*///                    /*printf("processing frame: ramp down\n");*/
/*TODO*///                    target_energy = 0;
/*TODO*///                    target_pitch = current_pitch;
/*TODO*///                    for (i = 0; i < 10; i++)
/*TODO*///                        target_k[i] = current_k[i];
/*TODO*///                }
/*TODO*///                /* Reset the step size */
/*TODO*///                else
/*TODO*///                {
/*TODO*///                    /*printf("processing frame: Normal\n");*/
/*TODO*///                    /*printf("*** Energy = %d\n",current_energy);*/
/*TODO*///                    /*printf("proc: %d %d\n",last_fbuf_head,fbuf_head);*/
/*TODO*///
/*TODO*///                    target_energy = new_energy;
/*TODO*///                    target_pitch = new_pitch;
/*TODO*///
/*TODO*///                    for (i = 0; i < 4; i++)
/*TODO*///                        target_k[i] = new_k[i];
/*TODO*///                    if (current_pitch == 0)
/*TODO*///                        for (i = 4; i < 10; i++)
/*TODO*///                        {
/*TODO*///                            target_k[i] = current_k[i] = 0;
/*TODO*///                        }
/*TODO*///                    else
/*TODO*///                        for (i = 4; i < 10; i++)
/*TODO*///                            target_k[i] = new_k[i];
/*TODO*///                }
/*TODO*///            }
/*TODO*///        }
/*TODO*///        else if (interp_count == 0)
/*TODO*///        {
/*TODO*///            /* Update values based on step values */
/*TODO*///            /*printf("\n");*/
/*TODO*///
/*TODO*///            interp_period = sample_count / 25;
/*TODO*///            current_energy += (target_energy - current_energy) / interp_coeff[interp_period];
/*TODO*///            if (old_pitch != 0)
/*TODO*///                current_pitch += (target_pitch - current_pitch) / interp_coeff[interp_period];
/*TODO*///
/*TODO*///            /*printf("*** Energy = %d\n",current_energy);*/
/*TODO*///
/*TODO*///            for (i = 0; i < 10; i++)
/*TODO*///            {
/*TODO*///                current_k[i] += (target_k[i] - current_k[i]) / interp_coeff[interp_period];
/*TODO*///            }
/*TODO*///        }
/*TODO*///
/*TODO*///        if (old_energy == 0)
/*TODO*///        {
/*TODO*///            /* generate silent samples here */
/*TODO*///            current_val = 0x00;
/*TODO*///        }
/*TODO*///        else if (old_pitch == 0)
/*TODO*///        {
/*TODO*///            /* generate unvoiced samples here */
/*TODO*///            randbit = (rand() % 2) * 2 - 1;
/*TODO*///            current_val = (randbit * current_energy) / 4;
/*TODO*///        }
/*TODO*///        else
/*TODO*///        {
/*TODO*///            /* generate voiced samples here */
/*TODO*///            if (pitch_count < sizeof(chirptable))
/*TODO*///                current_val = (chirptable[pitch_count] * current_energy) / 256;
/*TODO*///            else
/*TODO*///                current_val = 0x00;
/*TODO*///        }
/*TODO*///
/*TODO*///        /* Lattice filter here */
/*TODO*///
/*TODO*///        u[10] = current_val;
/*TODO*///
/*TODO*///        for (i = 9; i >= 0; i--)
/*TODO*///        {
/*TODO*///            u[i] = u[i+1] - ((current_k[i] * x[i]) / 32768);
/*TODO*///        }
/*TODO*///        for (i = 9; i >= 1; i--)
/*TODO*///        {
/*TODO*///            x[i] = x[i-1] + ((current_k[i-1] * u[i-1]) / 32768);
/*TODO*///        }
/*TODO*///
/*TODO*///        x[0] = u[0];
/*TODO*///
/*TODO*///        /* clipping, just like the chip */
/*TODO*///
/*TODO*///        if (u[0] > 511)
/*TODO*///            buffer[buf_count] = 127<<8;
/*TODO*///        else if (u[0] < -512)
/*TODO*///            buffer[buf_count] = -128<<8;
/*TODO*///        else
/*TODO*///            buffer[buf_count] = u[0] << 6;
/*TODO*///
/*TODO*///        /* Update all counts */
/*TODO*///
/*TODO*///        size--;
/*TODO*///        sample_count = (sample_count + 1) % 200;
/*TODO*///
/*TODO*///        if (current_pitch != 0)
/*TODO*///            pitch_count = (pitch_count + 1) % current_pitch;
/*TODO*///        else
/*TODO*///            pitch_count = 0;
/*TODO*///
/*TODO*///        interp_count = (interp_count + 1) % 25;
/*TODO*///        buf_count++;
/*TODO*///    }
/*TODO*///
/*TODO*///empty:
/*TODO*///
/*TODO*///    while (size > 0)
/*TODO*///    {
/*TODO*///        buffer[buf_count] = 0x00;
/*TODO*///        buf_count++;
/*TODO*///        size--;
/*TODO*///    }
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     process_command -- extract a byte from the FIFO and interpret it as a command
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///static void process_command(void)
/*TODO*///{
/*TODO*///    unsigned char cmd;
/*TODO*///
/*TODO*///    /* if there are stray bits, ignore them */
/*TODO*///    if (bits_taken)
/*TODO*///    {
/*TODO*///        bits_taken = 0;
/*TODO*///        fifo_count--;
/*TODO*///        fifo_head = (fifo_head + 1) % FIFO_SIZE;
/*TODO*///    }
/*TODO*///
/*TODO*///    /* grab a full byte from the FIFO */
/*TODO*///    if (fifo_count > 0)
/*TODO*///    {
/*TODO*///        cmd = fifo[fifo_head] & 0x70;
/*TODO*///        fifo_count--;
/*TODO*///        fifo_head = (fifo_head + 1) % FIFO_SIZE;
/*TODO*///
/*TODO*///        /* only real command we handle now is speak external */
/*TODO*///        if (cmd == 0x60)
/*TODO*///        {
/*TODO*///            speak_external = 1;
/*TODO*///            speak_delay_frames = 10;
/*TODO*///
/*TODO*///            /* according to the datasheet, this will cause an interrupt due to a BE condition */
/*TODO*///            if (!buffer_empty)
/*TODO*///            {
/*TODO*///                buffer_empty = 1;
/*TODO*///                set_interrupt_state(1);
/*TODO*///            }
/*TODO*///        }
/*TODO*///    }
/*TODO*///
/*TODO*///    /* update the buffer low state */
/*TODO*///    check_buffer_low();
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     extract_bits -- extract a specific number of bits from the FIFO
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///static int extract_bits(int count)
/*TODO*///{
/*TODO*///    int val = 0;
/*TODO*///
/*TODO*///    while (count--)
/*TODO*///    {
/*TODO*///        val = (val << 1) | ((fifo[fifo_head] >> bits_taken) & 1);
/*TODO*///        bits_taken++;
/*TODO*///        if (bits_taken >= 8)
/*TODO*///        {
/*TODO*///            fifo_count--;
/*TODO*///            fifo_head = (fifo_head + 1) % FIFO_SIZE;
/*TODO*///            bits_taken = 0;
/*TODO*///        }
/*TODO*///    }
/*TODO*///    return val;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     parse_frame -- parse a new frame's worth of data; returns 0 if not enough bits in buffer
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///static int parse_frame(int removeit)
/*TODO*///{
/*TODO*///    int old_head, old_taken, old_count;
/*TODO*///    int bits, indx, i, rep_flag;
/*TODO*///
/*TODO*///    /* remember previous frame */
/*TODO*///    old_energy = new_energy;
/*TODO*///    old_pitch = new_pitch;
/*TODO*///    for (i = 0; i < 10; i++)
/*TODO*///        old_k[i] = new_k[i];
/*TODO*///
/*TODO*///    /* clear out the new frame */
/*TODO*///    new_energy = 0;
/*TODO*///    new_pitch = 0;
/*TODO*///    for (i = 0; i < 10; i++)
/*TODO*///        new_k[i] = 0;
/*TODO*///
/*TODO*///    /* if the previous frame was a stop frame, don't do anything */
/*TODO*///    if (old_energy == (energytable[15] >> 6))
/*TODO*///        return 1;
/*TODO*///
/*TODO*///    /* remember the original FIFO counts, in case we don't have enough bits */
/*TODO*///    old_count = fifo_count;
/*TODO*///    old_head = fifo_head;
/*TODO*///    old_taken = bits_taken;
/*TODO*///
/*TODO*///    /* count the total number of bits available */
/*TODO*///    bits = fifo_count * 8 - bits_taken;
/*TODO*///
/*TODO*///    /* attempt to extract the energy index */
/*TODO*///    bits -= 4;
/*TODO*///    if (bits < 0)
/*TODO*///        goto ranout;
/*TODO*///    indx = extract_bits(4);
/*TODO*///    new_energy = energytable[indx] >> 6;
/*TODO*///
/*TODO*///	/* if the index is 0 or 15, we're done */
/*TODO*///	if (indx == 0 || indx == 15)
/*TODO*///	{
/*TODO*///		if (DEBUG_5220) logerror("  (4-bit energy=%d frame)\n",new_energy);
/*TODO*///
/*TODO*///		/* clear fifo if stop frame encountered */
/*TODO*///		if (indx == 15)
/*TODO*///		{
/*TODO*///			fifo_head = fifo_tail = fifo_count = bits_taken = 0;
/*TODO*///			removeit = 1;
/*TODO*///		}
/*TODO*///		goto done;
/*TODO*///	}
/*TODO*///
/*TODO*///    /* attempt to extract the repeat flag */
/*TODO*///    bits -= 1;
/*TODO*///    if (bits < 0)
/*TODO*///        goto ranout;
/*TODO*///    rep_flag = extract_bits(1);
/*TODO*///
/*TODO*///    /* attempt to extract the pitch */
/*TODO*///    bits -= 6;
/*TODO*///    if (bits < 0)
/*TODO*///        goto ranout;
/*TODO*///    indx = extract_bits(6);
/*TODO*///    new_pitch = pitchtable[indx] / 256;
/*TODO*///
/*TODO*///    /* if this is a repeat frame, just copy the k's */
/*TODO*///    if (rep_flag)
/*TODO*///    {
/*TODO*///        for (i = 0; i < 10; i++)
/*TODO*///            new_k[i] = old_k[i];
/*TODO*///
/*TODO*///        if (DEBUG_5220) logerror("  (11-bit energy=%d pitch=%d rep=%d frame)\n", new_energy, new_pitch, rep_flag);
/*TODO*///        goto done;
/*TODO*///    }
/*TODO*///
/*TODO*///    /* if the pitch index was zero, we need 4 k's */
/*TODO*///    if (indx == 0)
/*TODO*///    {
/*TODO*///        /* attempt to extract 4 K's */
/*TODO*///        bits -= 18;
/*TODO*///        if (bits < 0)
/*TODO*///            goto ranout;
/*TODO*///        new_k[0] = k1table[extract_bits(5)];
/*TODO*///        new_k[1] = k2table[extract_bits(5)];
/*TODO*///        new_k[2] = k3table[extract_bits(4)];
/*TODO*///        new_k[3] = k4table[extract_bits(4)];
/*TODO*///
/*TODO*///        if (DEBUG_5220) logerror("  (29-bit energy=%d pitch=%d rep=%d 4K frame)\n", new_energy, new_pitch, rep_flag);
/*TODO*///        goto done;
/*TODO*///    }
/*TODO*///
/*TODO*///    /* else we need 10 K's */
/*TODO*///    bits -= 39;
/*TODO*///    if (bits < 0)
/*TODO*///        goto ranout;
/*TODO*///    new_k[0] = k1table[extract_bits(5)];
/*TODO*///    new_k[1] = k2table[extract_bits(5)];
/*TODO*///    new_k[2] = k3table[extract_bits(4)];
/*TODO*///    new_k[3] = k4table[extract_bits(4)];
/*TODO*///    new_k[4] = k5table[extract_bits(4)];
/*TODO*///    new_k[5] = k6table[extract_bits(4)];
/*TODO*///    new_k[6] = k7table[extract_bits(4)];
/*TODO*///    new_k[7] = k8table[extract_bits(3)];
/*TODO*///    new_k[8] = k9table[extract_bits(3)];
/*TODO*///    new_k[9] = k10table[extract_bits(3)];
/*TODO*///
/*TODO*///    if (DEBUG_5220) logerror("  (50-bit energy=%d pitch=%d rep=%d 10K frame)\n", new_energy, new_pitch, rep_flag);
/*TODO*///
/*TODO*///done:
/*TODO*///
/*TODO*///    if (DEBUG_5220) logerror("Parsed a frame successfully - %d bits remaining\n", bits);
/*TODO*///
/*TODO*///    /* if we're not to remove this one, restore the FIFO */
/*TODO*///    if (!removeit)
/*TODO*///    {
/*TODO*///        fifo_count = old_count;
/*TODO*///        fifo_head = old_head;
/*TODO*///        bits_taken = old_taken;
/*TODO*///    }
/*TODO*///
/*TODO*///    /* update the buffer_low status */
/*TODO*///    check_buffer_low();
/*TODO*///    return 1;
/*TODO*///
/*TODO*///ranout:
/*TODO*///
/*TODO*///    if (DEBUG_5220) logerror("Ran out of bits on a parse!\n");
/*TODO*///
/*TODO*///    /* this is an error condition; mark the buffer empty and turn off speaking */
/*TODO*///    buffer_empty = 1;
/*TODO*///    talk_status = speak_external = 0;
/*TODO*///    fifo_count = fifo_head = fifo_tail = 0;
/*TODO*///
/*TODO*///    /* generate an interrupt if necessary */
/*TODO*///    set_interrupt_state(1);
/*TODO*///    return 0;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     check_buffer_low -- check to see if the buffer low flag should be on or off
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///static void check_buffer_low(void)
/*TODO*///{
/*TODO*///    /* did we just become low? */
/*TODO*///    if (fifo_count <= 8)
/*TODO*///    {
/*TODO*///        /* generate an interrupt if necessary */
/*TODO*///        if (!buffer_low)
/*TODO*///            set_interrupt_state(1);
/*TODO*///        buffer_low = 1;
/*TODO*///
/*TODO*///        if (DEBUG_5220) logerror("Buffer low set\n");
/*TODO*///    }
/*TODO*///
/*TODO*///    /* did we just become full? */
/*TODO*///    else
/*TODO*///    {
/*TODO*///        buffer_low = 0;
/*TODO*///
/*TODO*///        if (DEBUG_5220) logerror("Buffer low cleared\n");
/*TODO*///    }
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////**********************************************************************************************
/*TODO*///
/*TODO*///     set_interrupt_state -- generate an interrupt
/*TODO*///
/*TODO*///***********************************************************************************************/
/*TODO*///
/*TODO*///static void set_interrupt_state(int state)
/*TODO*///{
/*TODO*///    if (irq_func && state != irq_pin)
/*TODO*///    	irq_func(state);
/*TODO*///    irq_pin = state;
/*TODO*///}
/*TODO*///    
}
