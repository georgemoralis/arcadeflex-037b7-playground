/*
 * ported to 0.37b7 
 */
package arcadeflex.v037b7.mame;

public class info {
/*TODO*///#include <ctype.h>
/*TODO*///
/*TODO*///#include "driver.h"
/*TODO*///#include "sound/samples.h"
/*TODO*///#include "info.h"
/*TODO*///#include "datafile.h"
/*TODO*///
/*TODO*////* Output format indentation */
/*TODO*///
/*TODO*////* Indentation */
/*TODO*///#define INDENT "\t"
/*TODO*///
/*TODO*////* Possible output format */
/*TODO*///#define OUTPUT_FORMAT_UNFORMATTED 0
/*TODO*///#define OUTPUT_FORMAT_ONE_LEVEL 1
/*TODO*///#define OUTPUT_FORMAT_TWO_LEVEL 2
/*TODO*///
/*TODO*////* Output format */
/*TODO*///#define OUTPUT_FORMAT OUTPUT_FORMAT_ONE_LEVEL
/*TODO*///
/*TODO*////* Output format configuration
/*TODO*///	L list
/*TODO*///	1,2 levels
/*TODO*///	B,S,E Begin, Separator, End
/*TODO*///*/
/*TODO*///
/*TODO*///#if OUTPUT_FORMAT == OUTPUT_FORMAT_UNFORMATTED
/*TODO*///#define L1B "("
/*TODO*///#define L1P " "
/*TODO*///#define L1N ""
/*TODO*///#define L1E ")"
/*TODO*///#define L2B "("
/*TODO*///#define L2P " "
/*TODO*///#define L2N ""
/*TODO*///#define L2E ")"
/*TODO*///#elif OUTPUT_FORMAT == OUTPUT_FORMAT_ONE_LEVEL
/*TODO*///#define L1B " (\n"
/*TODO*///#define L1P INDENT
/*TODO*///#define L1N "\n"
/*TODO*///#define L1E ")\n\n"
/*TODO*///#define L2B " ("
/*TODO*///#define L2P " "
/*TODO*///#define L2N ""
/*TODO*///#define L2E " )"
/*TODO*///#elif OUTPUT_FORMAT == OUTPUT_FORMAT_TWO_LEVEL
/*TODO*///#define L1B " (\n"
/*TODO*///#define L1P INDENT
/*TODO*///#define L1N "\n"
/*TODO*///#define L1E ")\n\n"
/*TODO*///#define L2B " (\n"
/*TODO*///#define L2P INDENT INDENT
/*TODO*///#define L2N "\n"
/*TODO*///#define L2E INDENT ")"
/*TODO*///#else
/*TODO*///#error Wrong OUTPUT_FORMAT
/*TODO*///#endif
/*TODO*///
/*TODO*////* Print a string in C format */
/*TODO*///static void print_c_string(FILE* out, const char* s) {
/*TODO*///	fprintf(out, "\"");
/*TODO*///	if (s) {
/*TODO*///		while (*s) {
/*TODO*///			switch (*s) {
/*TODO*///				case '\a' : fprintf(out, "\\a"); break;
/*TODO*///				case '\b' : fprintf(out, "\\b"); break;
/*TODO*///				case '\f' : fprintf(out, "\\f"); break;
/*TODO*///				case '\n' : fprintf(out, "\\n"); break;
/*TODO*///				case '\r' : fprintf(out, "\\r"); break;
/*TODO*///				case '\t' : fprintf(out, "\\t"); break;
/*TODO*///				case '\v' : fprintf(out, "\\v"); break;
/*TODO*///				case '\\' : fprintf(out, "\\\\"); break;
/*TODO*///				case '\"' : fprintf(out, "\\\""); break;
/*TODO*///				default:
/*TODO*///					if (*s>=' ' && *s<='~')
/*TODO*///						fprintf(out, "%c", *s);
/*TODO*///					else
/*TODO*///						fprintf(out, "\\x%02x", (unsigned)(unsigned char)*s);
/*TODO*///			}
/*TODO*///			++s;
/*TODO*///		}
/*TODO*///	}
/*TODO*///	fprintf(out, "\"");
/*TODO*///}
/*TODO*///
/*TODO*////* Print a string in statement format (remove space, parentesis, ") */
/*TODO*///static void print_statement_string(FILE* out, const char* s) {
/*TODO*///	if (s) {
/*TODO*///		while (*s) {
/*TODO*///			if (isspace(*s)) {
/*TODO*///				fprintf(out, "_");
/*TODO*///			} else {
/*TODO*///				switch (*s) {
/*TODO*///					case '(' :
/*TODO*///					case ')' :
/*TODO*///					case '"' :
/*TODO*///						fprintf(out, "_");
/*TODO*///						break;
/*TODO*///					default:
/*TODO*///						fprintf(out, "%c", *s);
/*TODO*///				}
/*TODO*///			}
/*TODO*///			++s;
/*TODO*///		}
/*TODO*///	} else {
/*TODO*///		fprintf(out, "null");
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///static void print_game_switch(FILE* out, const struct GameDriver* game) {
/*TODO*///	const struct InputPortTiny* input = game->input_ports;
/*TODO*///
/*TODO*///	while ((input->type & ~IPF_MASK) != IPT_END) {
/*TODO*///		if ((input->type & ~IPF_MASK)==IPT_DIPSWITCH_NAME) {
/*TODO*///			int def = input->default_value;
/*TODO*///			const char* def_name = 0;
/*TODO*///
/*TODO*///			fprintf(out, L1P "dipswitch" L2B);
/*TODO*///
/*TODO*///			fprintf(out, L2P "name " );
/*TODO*///			print_c_string(out,input->name);
/*TODO*///			fprintf(out, "%s", L2N);
/*TODO*///			++input;
/*TODO*///
/*TODO*///			while ((input->type & ~IPF_MASK)==IPT_DIPSWITCH_SETTING) {
/*TODO*///				if (def == input->default_value)
/*TODO*///					def_name = input->name;
/*TODO*///				fprintf(out, L2P "entry " );
/*TODO*///				print_c_string(out,input->name);
/*TODO*///				fprintf(out, "%s", L2N);
/*TODO*///				++input;
/*TODO*///			}
/*TODO*///
/*TODO*///			if (def_name) {
/*TODO*///				fprintf(out, L2P "default ");
/*TODO*///				print_c_string(out,def_name);
/*TODO*///				fprintf(out, "%s", L2N);
/*TODO*///			}
/*TODO*///
/*TODO*///			fprintf(out, L2E L1N);
/*TODO*///		}
/*TODO*///		else
/*TODO*///			++input;
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///static void print_game_input(FILE* out, const struct GameDriver* game) {
/*TODO*///	const struct InputPortTiny* input = game->input_ports;
/*TODO*///	int nplayer = 0;
/*TODO*///	const char* control = 0;
/*TODO*///	int nbutton = 0;
/*TODO*///	int ncoin = 0;
/*TODO*///	const char* service = 0;
/*TODO*///	const char* tilt = 0;
/*TODO*///
/*TODO*///	while ((input->type & ~IPF_MASK) != IPT_END) {
/*TODO*///		switch (input->type & IPF_PLAYERMASK) {
/*TODO*///			case IPF_PLAYER1:
/*TODO*///				if (nplayer<1) nplayer = 1;
/*TODO*///				break;
/*TODO*///			case IPF_PLAYER2:
/*TODO*///				if (nplayer<2) nplayer = 2;
/*TODO*///				break;
/*TODO*///			case IPF_PLAYER3:
/*TODO*///				if (nplayer<3) nplayer = 3;
/*TODO*///				break;
/*TODO*///			case IPF_PLAYER4:
/*TODO*///				if (nplayer<4) nplayer = 4;
/*TODO*///				break;
/*TODO*///		}
/*TODO*///		switch (input->type & ~IPF_MASK) {
/*TODO*///			case IPT_JOYSTICK_UP:
/*TODO*///			case IPT_JOYSTICK_DOWN:
/*TODO*///			case IPT_JOYSTICK_LEFT:
/*TODO*///			case IPT_JOYSTICK_RIGHT:
/*TODO*///				if (input->type & IPF_2WAY)
/*TODO*///					control = "joy2way";
/*TODO*///				else if (input->type & IPF_4WAY)
/*TODO*///					control = "joy4way";
/*TODO*///				else
/*TODO*///					control = "joy8way";
/*TODO*///				break;
/*TODO*///			case IPT_JOYSTICKRIGHT_UP:
/*TODO*///			case IPT_JOYSTICKRIGHT_DOWN:
/*TODO*///			case IPT_JOYSTICKRIGHT_LEFT:
/*TODO*///			case IPT_JOYSTICKRIGHT_RIGHT:
/*TODO*///			case IPT_JOYSTICKLEFT_UP:
/*TODO*///			case IPT_JOYSTICKLEFT_DOWN:
/*TODO*///			case IPT_JOYSTICKLEFT_LEFT:
/*TODO*///			case IPT_JOYSTICKLEFT_RIGHT:
/*TODO*///				if (input->type & IPF_2WAY)
/*TODO*///					control = "doublejoy2way";
/*TODO*///				else if (input->type & IPF_4WAY)
/*TODO*///					control = "doublejoy4way";
/*TODO*///				else
/*TODO*///					control = "doublejoy8way";
/*TODO*///				break;
/*TODO*///			case IPT_BUTTON1:
/*TODO*///				if (nbutton<1) nbutton = 1;
/*TODO*///				break;
/*TODO*///			case IPT_BUTTON2:
/*TODO*///				if (nbutton<2) nbutton = 2;
/*TODO*///				break;
/*TODO*///			case IPT_BUTTON3:
/*TODO*///				if (nbutton<3) nbutton = 3;
/*TODO*///				break;
/*TODO*///			case IPT_BUTTON4:
/*TODO*///				if (nbutton<4) nbutton = 4;
/*TODO*///				break;
/*TODO*///			case IPT_BUTTON5:
/*TODO*///				if (nbutton<5) nbutton = 5;
/*TODO*///				break;
/*TODO*///			case IPT_BUTTON6:
/*TODO*///				if (nbutton<6) nbutton = 6;
/*TODO*///				break;
/*TODO*///			case IPT_BUTTON7:
/*TODO*///				if (nbutton<7) nbutton = 7;
/*TODO*///				break;
/*TODO*///			case IPT_BUTTON8:
/*TODO*///				if (nbutton<8) nbutton = 8;
/*TODO*///				break;
/*TODO*///			case IPT_PADDLE:
/*TODO*///				control = "paddle";
/*TODO*///				break;
/*TODO*///			case IPT_DIAL:
/*TODO*///				control = "dial";
/*TODO*///				break;
/*TODO*///			case IPT_TRACKBALL_X:
/*TODO*///			case IPT_TRACKBALL_Y:
/*TODO*///				control = "trackball";
/*TODO*///				break;
/*TODO*///			case IPT_AD_STICK_X:
/*TODO*///			case IPT_AD_STICK_Y:
/*TODO*///				control = "stick";
/*TODO*///				break;
/*TODO*///			case IPT_COIN1:
/*TODO*///				if (ncoin < 1) ncoin = 1;
/*TODO*///				break;
/*TODO*///			case IPT_COIN2:
/*TODO*///				if (ncoin < 2) ncoin = 2;
/*TODO*///				break;
/*TODO*///			case IPT_COIN3:
/*TODO*///				if (ncoin < 3) ncoin = 3;
/*TODO*///				break;
/*TODO*///			case IPT_COIN4:
/*TODO*///				if (ncoin < 4) ncoin = 4;
/*TODO*///				break;
/*TODO*///			case IPT_SERVICE :
/*TODO*///				service = "yes";
/*TODO*///				break;
/*TODO*///			case IPT_TILT :
/*TODO*///				tilt = "yes";
/*TODO*///				break;
/*TODO*///		}
/*TODO*///		++input;
/*TODO*///	}
/*TODO*///
/*TODO*///	fprintf(out, L1P "input" L2B);
/*TODO*///	fprintf(out, L2P "players %d" L2N, nplayer );
/*TODO*///	if (control)
/*TODO*///		fprintf(out, L2P "control %s" L2N, control );
/*TODO*///	if (nbutton)
/*TODO*///		fprintf(out, L2P "buttons %d" L2N, nbutton );
/*TODO*///	if (ncoin)
/*TODO*///		fprintf(out, L2P "coins %d" L2N, ncoin );
/*TODO*///	if (service)
/*TODO*///		fprintf(out, L2P "service %s" L2N, service );
/*TODO*///	if (tilt)
/*TODO*///		fprintf(out, L2P "tilt %s" L2N, tilt );
/*TODO*///	fprintf(out, L2E L1N);
/*TODO*///}
/*TODO*///
/*TODO*///static void print_game_rom(FILE* out, const struct GameDriver* game) {
/*TODO*///	const struct RomModule *rom = game->rom, *p_rom = NULL;
/*TODO*///	extern struct GameDriver driver_0;
/*TODO*///
/*TODO*///	if (!rom) return;
/*TODO*///
/*TODO*///	if (game->clone_of && game->clone_of != &driver_0) {
/*TODO*///		fprintf(out, L1P "romof %s" L1N, game->clone_of->name);
/*TODO*///	}
/*TODO*///
/*TODO*///	while (rom->name || rom->offset || rom->length) {
/*TODO*///		int region = rom->crc;
/*TODO*///		rom++;
/*TODO*///
/*TODO*///		while (rom->length) {
/*TODO*///			char name[100];
/*TODO*///			int offset, length, crc, in_parent;
/*TODO*///
/*TODO*///			sprintf(name,rom->name,game->name);
/*TODO*///			offset = rom->offset;
/*TODO*///			crc = rom->crc;
/*TODO*///
/*TODO*///			in_parent = 0;
/*TODO*///			length = 0;
/*TODO*///			do {
/*TODO*///				if (rom->name == (char *)-1)
/*TODO*///					length = 0; /* restart */
/*TODO*///				length += rom->length & ~ROMFLAG_MASK;
/*TODO*///				rom++;
/*TODO*///			} while (rom->length && (rom->name == 0 || rom->name == (char *)-1));
/*TODO*///
/*TODO*///			if(game->clone_of && crc)
/*TODO*///			{
/*TODO*///				p_rom = game->clone_of->rom;
/*TODO*///				if (p_rom)
/*TODO*///					while( !in_parent && (p_rom->name || p_rom->offset || p_rom->length) )
/*TODO*///					{
/*TODO*///						p_rom++;
/*TODO*///						while(!in_parent && p_rom->length) {
/*TODO*///							do {
/*TODO*///								if (p_rom->crc == crc)
/*TODO*///									in_parent = 1;
/*TODO*///								else
/*TODO*///									p_rom++;
/*TODO*///							} while (!in_parent && p_rom->length && (p_rom->name == 0 || p_rom->name == (char *)-1));
/*TODO*///						}
/*TODO*///					}
/*TODO*///			}
/*TODO*///
/*TODO*///			fprintf(out, L1P "rom" L2B);
/*TODO*///			if (*name)
/*TODO*///				fprintf(out, L2P "name %s" L2N, name);
/*TODO*///			if(in_parent && p_rom && p_rom->name)
/*TODO*///				fprintf(out, L2P "merge %s" L2N, p_rom->name);
/*TODO*///			fprintf(out, L2P "size %d" L2N, length);
/*TODO*///			fprintf(out, L2P "crc %08x" L2N, crc);
/*TODO*///			switch (region & ~REGIONFLAG_MASK)
/*TODO*///			{
/*TODO*///			case REGION_CPU1: fprintf(out, L2P "region cpu1" L2N); break;
/*TODO*///			case REGION_CPU2: fprintf(out, L2P "region cpu2" L2N); break;
/*TODO*///			case REGION_CPU3: fprintf(out, L2P "region cpu3" L2N); break;
/*TODO*///			case REGION_CPU4: fprintf(out, L2P "region cpu4" L2N); break;
/*TODO*///			case REGION_CPU5: fprintf(out, L2P "region cpu5" L2N); break;
/*TODO*///			case REGION_CPU6: fprintf(out, L2P "region cpu6" L2N); break;
/*TODO*///			case REGION_CPU7: fprintf(out, L2P "region cpu7" L2N); break;
/*TODO*///			case REGION_CPU8: fprintf(out, L2P "region cpu8" L2N); break;
/*TODO*///			case REGION_GFX1: fprintf(out, L2P "region gfx1" L2N); break;
/*TODO*///			case REGION_GFX2: fprintf(out, L2P "region gfx2" L2N); break;
/*TODO*///			case REGION_GFX3: fprintf(out, L2P "region gfx3" L2N); break;
/*TODO*///			case REGION_GFX4: fprintf(out, L2P "region gfx4" L2N); break;
/*TODO*///			case REGION_GFX5: fprintf(out, L2P "region gfx5" L2N); break;
/*TODO*///			case REGION_GFX6: fprintf(out, L2P "region gfx6" L2N); break;
/*TODO*///			case REGION_GFX7: fprintf(out, L2P "region gfx7" L2N); break;
/*TODO*///			case REGION_GFX8: fprintf(out, L2P "region gfx8" L2N); break;
/*TODO*///			case REGION_PROMS: fprintf(out, L2P "region proms" L2N); break;
/*TODO*///			case REGION_SOUND1: fprintf(out, L2P "region sound1" L2N); break;
/*TODO*///			case REGION_SOUND2: fprintf(out, L2P "region sound2" L2N); break;
/*TODO*///			case REGION_SOUND3: fprintf(out, L2P "region sound3" L2N); break;
/*TODO*///			case REGION_SOUND4: fprintf(out, L2P "region sound4" L2N); break;
/*TODO*///			case REGION_SOUND5: fprintf(out, L2P "region sound5" L2N); break;
/*TODO*///			case REGION_SOUND6: fprintf(out, L2P "region sound6" L2N); break;
/*TODO*///			case REGION_SOUND7: fprintf(out, L2P "region sound7" L2N); break;
/*TODO*///			case REGION_SOUND8: fprintf(out, L2P "region sound8" L2N); break;
/*TODO*///			case REGION_USER1: fprintf(out, L2P "region user1" L2N); break;
/*TODO*///			case REGION_USER2: fprintf(out, L2P "region user2" L2N); break;
/*TODO*///			case REGION_USER3: fprintf(out, L2P "region user3" L2N); break;
/*TODO*///			case REGION_USER4: fprintf(out, L2P "region user4" L2N); break;
/*TODO*///			case REGION_USER5: fprintf(out, L2P "region user5" L2N); break;
/*TODO*///			case REGION_USER6: fprintf(out, L2P "region user6" L2N); break;
/*TODO*///			case REGION_USER7: fprintf(out, L2P "region user7" L2N); break;
/*TODO*///			case REGION_USER8: fprintf(out, L2P "region user8" L2N); break;
/*TODO*///			default: fprintf(out, L2P "region 0x%x" L2N, region & ~REGIONFLAG_MASK);
/*TODO*///            }
/*TODO*///			switch (region & REGIONFLAG_MASK)
/*TODO*///			{
/*TODO*///			case 0:
/*TODO*///				break;
/*TODO*///			case REGIONFLAG_SOUNDONLY:
/*TODO*///				fprintf(out, L2P "flags soundonly" L2N);
/*TODO*///                break;
/*TODO*///			case REGIONFLAG_DISPOSE:
/*TODO*///				fprintf(out, L2P "flags dispose" L2N);
/*TODO*///				break;
/*TODO*///			default:
/*TODO*///				fprintf(out, L2P "flags 0x%x" L2N, region & REGIONFLAG_MASK);
/*TODO*///            }
/*TODO*///			fprintf(out, L2P "offs %x", offset);
/*TODO*///            fprintf(out, L2E L1N);
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///static void print_game_sample(FILE* out, const struct GameDriver* game) {
/*TODO*///#if (HAS_SAMPLES || HAS_VLM5030)
/*TODO*///	int i;
/*TODO*///	for( i = 0; game->drv->sound[i].sound_type && i < MAX_SOUND; i++ )
/*TODO*///	{
/*TODO*///		const char **samplenames = NULL;
/*TODO*///#if (HAS_SAMPLES)
/*TODO*///		if( game->drv->sound[i].sound_type == SOUND_SAMPLES )
/*TODO*///			samplenames = ((struct Samplesinterface *)game->drv->sound[i].sound_interface)->samplenames;
/*TODO*///#endif
/*TODO*///#if (HAS_VLM5030)
/*TODO*///		if( game->drv->sound[i].sound_type == SOUND_VLM5030 )
/*TODO*///			samplenames = ((struct VLM5030interface *)game->drv->sound[i].sound_interface)->samplenames;
/*TODO*///#endif
/*TODO*///		if (samplenames != 0 && samplenames[0] != 0) {
/*TODO*///			int k = 0;
/*TODO*///			if (samplenames[k][0]=='*') {
/*TODO*///				/* output sampleof only if different from game name */
/*TODO*///				if (strcmp(samplenames[k] + 1, game->name)!=0) {
/*TODO*///					fprintf(out, L1P "sampleof %s" L1N, samplenames[k] + 1);
/*TODO*///				}
/*TODO*///				++k;
/*TODO*///			}
/*TODO*///			while (samplenames[k] != 0) {
/*TODO*///				/* Check if is not empty */
/*TODO*///				if (*samplenames[k]) {
/*TODO*///					/* Check if sample is duplicate */
/*TODO*///					int l = 0;
/*TODO*///					while (l<k && strcmp(samplenames[k],samplenames[l])!=0)
/*TODO*///						++l;
/*TODO*///					if (l==k) {
/*TODO*///						fprintf(out, L1P "sample %s" L1N, samplenames[k]);
/*TODO*///					}
/*TODO*///				}
/*TODO*///				++k;
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///#endif
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///static void print_game_micro(FILE* out, const struct GameDriver* game)
/*TODO*///{
/*TODO*///	const struct MachineDriver* driver = game->drv;
/*TODO*///	const struct MachineCPU* cpu = driver->cpu;
/*TODO*///	const struct MachineSound* sound = driver->sound;
/*TODO*///	int j;
/*TODO*///
/*TODO*///	for(j=0;j<MAX_CPU;++j)
/*TODO*///	{
/*TODO*///		if (cpu[j].cpu_type!=0)
/*TODO*///		{
/*TODO*///			fprintf(out, L1P "chip" L2B);
/*TODO*///			if (cpu[j].cpu_type & CPU_AUDIO_CPU)
/*TODO*///				fprintf(out, L2P "type cpu flags audio" L2N);
/*TODO*///			else
/*TODO*///				fprintf(out, L2P "type cpu" L2N);
/*TODO*///
/*TODO*///			fprintf(out, L2P "name ");
/*TODO*///			print_statement_string(out, cputype_name(cpu[j].cpu_type));
/*TODO*///			fprintf(out, "%s", L2N);
/*TODO*///
/*TODO*///			fprintf(out, L2P "clock %d" L2N, cpu[j].cpu_clock);
/*TODO*///			fprintf(out, L2E L1N);
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///	for(j=0;j<MAX_SOUND;++j) if (sound[j].sound_type)
/*TODO*///	{
/*TODO*///		if (sound[j].sound_type)
/*TODO*///		{
/*TODO*///			int num = sound_num(&sound[j]);
/*TODO*///			int l;
/*TODO*///
/*TODO*///			if (num == 0) num = 1;
/*TODO*///
/*TODO*///			for(l=0;l<num;++l)
/*TODO*///			{
/*TODO*///				fprintf(out, L1P "chip" L2B);
/*TODO*///				fprintf(out, L2P "type audio" L2N);
/*TODO*///				fprintf(out, L2P "name ");
/*TODO*///				print_statement_string(out, sound_name(&sound[j]));
/*TODO*///				fprintf(out, "%s", L2N);
/*TODO*///				if (sound_clock(&sound[j]))
/*TODO*///					fprintf(out, L2P "clock %d" L2N, sound_clock(&sound[j]));
/*TODO*///				fprintf(out, L2E L1N);
/*TODO*///			}
/*TODO*///		}
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///static void print_game_video(FILE* out, const struct GameDriver* game)
/*TODO*///{
/*TODO*///	const struct MachineDriver* driver = game->drv;
/*TODO*///
/*TODO*///	int dx;
/*TODO*///	int dy;
/*TODO*///	int showxy;
/*TODO*///	int orientation;
/*TODO*///
/*TODO*///	fprintf(out, L1P "video" L2B);
/*TODO*///	if (driver->video_attributes & VIDEO_TYPE_VECTOR)
/*TODO*///	{
/*TODO*///		fprintf(out, L2P "screen vector" L2N);
/*TODO*///		showxy = 0;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		fprintf(out, L2P "screen raster" L2N);
/*TODO*///		showxy = 1;
/*TODO*///	}
/*TODO*///
/*TODO*///	if (game->flags & ORIENTATION_SWAP_XY)
/*TODO*///	{
/*TODO*///		dx = driver->default_visible_area.max_y - driver->default_visible_area.min_y + 1;
/*TODO*///		dy = driver->default_visible_area.max_x - driver->default_visible_area.min_x + 1;
/*TODO*///		orientation = 1;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		dx = driver->default_visible_area.max_x - driver->default_visible_area.min_x + 1;
/*TODO*///		dy = driver->default_visible_area.max_y - driver->default_visible_area.min_y + 1;
/*TODO*///		orientation = 0;
/*TODO*///	}
/*TODO*///
/*TODO*///
/*TODO*///	fprintf(out, L2P "orientation %s" L2N, orientation ? "vertical" : "horizontal" );
/*TODO*///	if (showxy)
/*TODO*///	{
/*TODO*///		fprintf(out, L2P "x %d" L2N, dx);
/*TODO*///		fprintf(out, L2P "y %d" L2N, dy);
/*TODO*///	}
/*TODO*///
/*TODO*///	fprintf(out, L2P "colors %d" L2N, driver->total_colors);
/*TODO*///	fprintf(out, L2P "freq %f" L2N, driver->frames_per_second);
/*TODO*///	fprintf(out, L2E L1N);
/*TODO*///}
/*TODO*///
/*TODO*///static void print_game_sound(FILE* out, const struct GameDriver* game) {
/*TODO*///	const struct MachineDriver* driver = game->drv;
/*TODO*///	const struct MachineCPU* cpu = driver->cpu;
/*TODO*///	const struct MachineSound* sound = driver->sound;
/*TODO*///
/*TODO*///	/* check if the game have sound emulation */
/*TODO*///	int has_sound = 0;
/*TODO*///	int i;
/*TODO*///
/*TODO*///	i = 0;
/*TODO*///	while (i < MAX_SOUND && !has_sound)
/*TODO*///	{
/*TODO*///		if (sound[i].sound_type)
/*TODO*///			has_sound = 1;
/*TODO*///		++i;
/*TODO*///	}
/*TODO*///	i = 0;
/*TODO*///	while (i < MAX_CPU && !has_sound)
/*TODO*///	{
/*TODO*///		if  ((cpu[i].cpu_type & CPU_AUDIO_CPU)!=0)
/*TODO*///			has_sound = 1;
/*TODO*///		++i;
/*TODO*///	}
/*TODO*///
/*TODO*///	fprintf(out, L1P "sound" L2B);
/*TODO*///
/*TODO*///	/* sound channel */
/*TODO*///	if (has_sound) {
/*TODO*///		if (driver->sound_attributes & SOUND_SUPPORTS_STEREO)
/*TODO*///			fprintf(out, L2P "channels 2" L2N);
/*TODO*///		else
/*TODO*///			fprintf(out, L2P "channels 1" L2N);
/*TODO*///	} else
/*TODO*///		fprintf(out, L2P "channels 0" L2N);
/*TODO*///
/*TODO*///	fprintf(out, L2E L1N);
/*TODO*///}
/*TODO*///
/*TODO*///#define HISTORY_BUFFER_MAX 16384
/*TODO*///
/*TODO*///static void print_game_history(FILE* out, const struct GameDriver* game) {
/*TODO*///	char buffer[HISTORY_BUFFER_MAX];
/*TODO*///
/*TODO*///	if (load_driver_history(game,buffer,HISTORY_BUFFER_MAX)==0) {
/*TODO*///		fprintf(out, L1P "history ");
/*TODO*///		print_c_string(out, buffer);
/*TODO*///		fprintf(out, "%s", L1N);
/*TODO*///	}
/*TODO*///}
/*TODO*///
/*TODO*///static void print_game_driver(FILE* out, const struct GameDriver* game) {
/*TODO*///	fprintf(out, L1P "driver" L2B);
/*TODO*///	if (game->flags & GAME_NOT_WORKING)
/*TODO*///		fprintf(out, L2P "status preliminary" L2N);
/*TODO*///	else
/*TODO*///		fprintf(out, L2P "status good" L2N);
/*TODO*///
/*TODO*///	if (game->flags & GAME_WRONG_COLORS)
/*TODO*///		fprintf(out, L2P "color preliminary" L2N);
/*TODO*///	else if (game->flags & GAME_IMPERFECT_COLORS)
/*TODO*///		fprintf(out, L2P "color imperfect" L2N);
/*TODO*///	else
/*TODO*///		fprintf(out, L2P "color good" L2N);
/*TODO*///
/*TODO*///	if (game->flags & GAME_NO_SOUND)
/*TODO*///		fprintf(out, L2P "sound preliminary" L2N);
/*TODO*///	else if (game->flags & GAME_IMPERFECT_SOUND)
/*TODO*///		fprintf(out, L2P "sound imperfect" L2N);
/*TODO*///	else
/*TODO*///		fprintf(out, L2P "sound good" L2N);
/*TODO*///
/*TODO*///	if (game->flags & GAME_REQUIRES_16BIT)
/*TODO*///		fprintf(out, L2P "colordeep 16" L2N);
/*TODO*///	else
/*TODO*///		fprintf(out, L2P "colordeep 8" L2N);
/*TODO*///
/*TODO*///	fprintf(out, L2E L1N);
/*TODO*///}
/*TODO*///
/*TODO*////* Print the MAME info record for a game */
/*TODO*///static void print_game_info(FILE* out, const struct GameDriver* game) {
/*TODO*///
/*TODO*///	#ifndef MESS
/*TODO*///	fprintf(out, "game" L1B );
/*TODO*///	#else
/*TODO*///	fprintf(out, "machine" L1B );
/*TODO*///	#endif
/*TODO*///
/*TODO*///	fprintf(out, L1P "name %s" L1N, game->name );
/*TODO*///
/*TODO*///	if (game->description) {
/*TODO*///		fprintf(out, L1P "description ");
/*TODO*///		print_c_string(out, game->description );
/*TODO*///		fprintf(out, "%s", L1N);
/*TODO*///	}
/*TODO*///
/*TODO*///	/* print the year only if is a number */
/*TODO*///	if (game->year && strspn(game->year,"0123456789")==strlen(game->year)) {
/*TODO*///		fprintf(out, L1P "year %s" L1N, game->year );
/*TODO*///	}
/*TODO*///
/*TODO*///	if (game->manufacturer) {
/*TODO*///		fprintf(out, L1P "manufacturer ");
/*TODO*///		print_c_string(out, game->manufacturer );
/*TODO*///		fprintf(out, "%s", L1N);
/*TODO*///	}
/*TODO*///
/*TODO*///	print_game_history(out,game);
/*TODO*///
/*TODO*///	if (game->clone_of && !(game->clone_of->flags & NOT_A_DRIVER)) {
/*TODO*///		fprintf(out, L1P "cloneof %s" L1N, game->clone_of->name);
/*TODO*///	}
/*TODO*///
/*TODO*///	print_game_rom(out,game);
/*TODO*///	print_game_sample(out,game);
/*TODO*///	print_game_micro(out,game);
/*TODO*///	print_game_video(out,game);
/*TODO*///	print_game_sound(out,game);
/*TODO*///	print_game_input(out,game);
/*TODO*///	print_game_switch(out,game);
/*TODO*///	print_game_driver(out,game);
/*TODO*///
/*TODO*///	fprintf(out, L1E);
/*TODO*///}
/*TODO*///
/*TODO*////* Print all the MAME info database */
/*TODO*///void print_mame_info(FILE* out, const struct GameDriver* games[]) {
/*TODO*///	int j;
/*TODO*///
/*TODO*///	for(j=0;games[j];++j)
/*TODO*///		print_game_info( out, games[j] );
/*TODO*///
/*TODO*///	#ifndef MESS
/*TODO*///	/* addictional fixed record */
/*TODO*///	fprintf(out, "resource" L1B);
/*TODO*///	fprintf(out, L1P "name neogeo" L1N);
/*TODO*///	fprintf(out, L1P "description \"Neo Geo BIOS\"" L1N);
/*TODO*///	fprintf(out, L1P "rom" L2B);
/*TODO*///	fprintf(out, L2P "name neo-geo.rom" L2N);
/*TODO*///	fprintf(out, L2P "size 131072" L2N);
/*TODO*///	fprintf(out, L2P "crc 9036d879" L2N);
/*TODO*///	fprintf(out, L2E L1N);
/*TODO*///	fprintf(out, L1P "rom" L2B);
/*TODO*///	fprintf(out, L2P "name ng-sm1.rom" L2N);
/*TODO*///	fprintf(out, L2P "size 131072" L2N);
/*TODO*///	fprintf(out, L2P "crc 97cf998b" L2N);
/*TODO*///	fprintf(out, L2E L1N);
/*TODO*///	fprintf(out, L1P "rom" L2B);
/*TODO*///	fprintf(out, L2P "name ng-sfix.rom" L2N);
/*TODO*///	fprintf(out, L2P "size 131072" L2N);
/*TODO*///	fprintf(out, L2P "crc 354029fc" L2N);
/*TODO*///	fprintf(out, L2E L1N);
/*TODO*///	fprintf(out, L1E);
/*TODO*///	#endif
/*TODO*///}
/*TODO*///    
}
