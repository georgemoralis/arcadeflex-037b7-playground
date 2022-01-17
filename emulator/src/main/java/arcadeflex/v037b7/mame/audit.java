/**
 * ported to 0.37b7
 */
package arcadeflex.v037b7.mame;

public class audit {
/*TODO*///#include "driver.h"
/*TODO*///#include "strings.h"
/*TODO*///#include "audit.h"
/*TODO*///
/*TODO*///
/*TODO*///static tAuditRecord *gAudits = NULL;
/*TODO*///
/*TODO*///
/*TODO*///
/*TODO*////* returns 1 if rom is defined in this set */
/*TODO*///int RomInSet (const struct GameDriver *gamedrv, unsigned int crc)
/*TODO*///{
/*TODO*///	const struct RomModule *romp = gamedrv->rom;
/*TODO*///
/*TODO*///	if (!romp) return 0;
/*TODO*///
/*TODO*///	while (romp->name || romp->offset || romp->length)
/*TODO*///	{
/*TODO*///		romp++;	/* skip ROM_REGION */
/*TODO*///
/*TODO*///		while (romp->length)
/*TODO*///		{
/*TODO*///			if (romp->crc == crc) return 1;
/*TODO*///			do
/*TODO*///			{
/*TODO*///				romp++;
/*TODO*///				/* skip ROM_CONTINUEs and ROM_RELOADs */
/*TODO*///			}
/*TODO*///			while (romp->length && (romp->name == 0 || romp->name == (char *)-1));
/*TODO*///		}
/*TODO*///	}
/*TODO*///	return 0;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////* returns nonzero if romset is missing */
/*TODO*///int RomsetMissing (int game)
/*TODO*///{
/*TODO*///	const struct GameDriver *gamedrv = drivers[game];
/*TODO*///
/*TODO*///	if (gamedrv->clone_of)
/*TODO*///	{
/*TODO*///		tAuditRecord	*aud;
/*TODO*///		int				count;
/*TODO*///		int 			i;
/*TODO*///		int 			cloneRomsFound = 0;
/*TODO*///
/*TODO*///		if ((count = AuditRomSet (game, &aud)) == 0)
/*TODO*///			return 1;
/*TODO*///
/*TODO*///		if (count == -1) return 0;
/*TODO*///
/*TODO*///		/* count number of roms found that are unique to clone */
/*TODO*///		for (i = 0; i < count; i++)
/*TODO*///			if (aud[i].status != AUD_ROM_NOT_FOUND)
/*TODO*///				if (!RomInSet (gamedrv->clone_of, aud[i].expchecksum))
/*TODO*///					cloneRomsFound++;
/*TODO*///
/*TODO*///		return !cloneRomsFound;
/*TODO*///	}
/*TODO*///	else
/*TODO*///		return !osd_faccess (gamedrv->name, OSD_FILETYPE_ROM);
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////* Fills in an audit record for each rom in the romset. Sets 'audit' to
/*TODO*///   point to the list of audit records. Returns total number of roms
/*TODO*///   in the romset (same as number of audit records), 0 if romset missing. */
/*TODO*///int AuditRomSet (int game, tAuditRecord **audit)
/*TODO*///{
/*TODO*///	const struct RomModule *romp;
/*TODO*///	const char *name;
/*TODO*///	const struct GameDriver *gamedrv;
/*TODO*///
/*TODO*///	int count = 0;
/*TODO*///	tAuditRecord *aud;
/*TODO*///	int	err;
/*TODO*///
/*TODO*///	if (!gAudits)
/*TODO*///		gAudits = (tAuditRecord *)malloc (AUD_MAX_ROMS * sizeof (tAuditRecord));
/*TODO*///
/*TODO*///	if (gAudits)
/*TODO*///		*audit = aud = gAudits;
/*TODO*///	else
/*TODO*///		return 0;
/*TODO*///
/*TODO*///
/*TODO*///	gamedrv = drivers[game];
/*TODO*///	romp = gamedrv->rom;
/*TODO*///
/*TODO*///	if (!romp) return -1;
/*TODO*///
/*TODO*///	/* check for existence of romset */
/*TODO*///	if (!osd_faccess (gamedrv->name, OSD_FILETYPE_ROM))
/*TODO*///	{
/*TODO*///		/* if the game is a clone, check for parent */
/*TODO*///		if (gamedrv->clone_of == 0 || (gamedrv->clone_of->flags & NOT_A_DRIVER) ||
/*TODO*///				!osd_faccess(gamedrv->clone_of->name,OSD_FILETYPE_ROM))
/*TODO*///			return 0;
/*TODO*///	}
/*TODO*///
/*TODO*///	while (romp->name || romp->offset || romp->length)
/*TODO*///	{
/*TODO*///		if (romp->name || romp->length) return 0; /* expecting ROM_REGION */
/*TODO*///
/*TODO*///		romp++;
/*TODO*///
/*TODO*///		while (romp->length)
/*TODO*///		{
/*TODO*///			const struct GameDriver *drv;
/*TODO*///
/*TODO*///
/*TODO*///			if (romp->name == 0)
/*TODO*///				return 0;	/* ROM_CONTINUE not preceded by ROM_LOAD */
/*TODO*///			else if (romp->name == (char *)-1)
/*TODO*///				return 0;	/* ROM_RELOAD not preceded by ROM_LOAD */
/*TODO*///
/*TODO*///			name = romp->name;
/*TODO*///			strcpy (aud->rom, name);
/*TODO*///			aud->explength = 0;
/*TODO*///			aud->length = 0;
/*TODO*///			aud->expchecksum = romp->crc;
/*TODO*///			/* NS981003: support for "load by CRC" */
/*TODO*///			aud->checksum = romp->crc;
/*TODO*///			count++;
/*TODO*///
/*TODO*///			/* obtain CRC-32 and length of ROM file */
/*TODO*///			drv = gamedrv;
/*TODO*///			do
/*TODO*///			{
/*TODO*///				err = osd_fchecksum (drv->name, name, &aud->length, &aud->checksum);
/*TODO*///				drv = drv->clone_of;
/*TODO*///			} while (err && drv);
/*TODO*///
/*TODO*///			/* spin through ROM_CONTINUEs and ROM_RELOADs, totaling length */
/*TODO*///			do
/*TODO*///			{
/*TODO*///				if (romp->name != (char *)-1) /* ROM_RELOAD */
/*TODO*///					aud->explength += romp->length & ~ROMFLAG_MASK;
/*TODO*///				romp++;
/*TODO*///			}
/*TODO*///			while (romp->length && (romp->name == 0 || romp->name == (char *)-1));
/*TODO*///
/*TODO*///			if (err)
/*TODO*///			{
/*TODO*///				if (!aud->expchecksum)
/*TODO*///					/* not found but it's not good anyway */
/*TODO*///					aud->status = AUD_NOT_AVAILABLE;
/*TODO*///				else
/*TODO*///					/* not found */
/*TODO*///					aud->status = AUD_ROM_NOT_FOUND;
/*TODO*///			}
/*TODO*///			/* all cases below assume the ROM was at least found */
/*TODO*///			else if (aud->explength != aud->length)
/*TODO*///				aud->status = AUD_LENGTH_MISMATCH;
/*TODO*///			else if (aud->checksum != aud->expchecksum)
/*TODO*///			{
/*TODO*///				if (!aud->expchecksum)
/*TODO*///					aud->status = AUD_ROM_NEED_DUMP; /* new case - found but not known to be dumped */
/*TODO*///				else if (aud->checksum == BADCRC (aud->expchecksum))
/*TODO*///					aud->status = AUD_ROM_NEED_REDUMP;
/*TODO*///				else
/*TODO*///					aud->status = AUD_BAD_CHECKSUM;
/*TODO*///			}
/*TODO*///			else
/*TODO*///				aud->status = AUD_ROM_GOOD;
/*TODO*///
/*TODO*///			aud++;
/*TODO*///		}
/*TODO*///	}
/*TODO*///
/*TODO*///        #ifdef MESS
/*TODO*///        if (!count)
/*TODO*///                return -1;
/*TODO*///        else
/*TODO*///        #endif
/*TODO*///	return count;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////* Generic function for evaluating a romset. Some platforms may wish to
/*TODO*///   call AuditRomSet() instead and implement their own reporting (like MacMAME). */
/*TODO*///int VerifyRomSet (int game, verify_printf_proc verify_printf)
/*TODO*///{
/*TODO*///	tAuditRecord			*aud;
/*TODO*///	int						count;
/*TODO*///	int						archive_status = 0;
/*TODO*///	const struct GameDriver *gamedrv = drivers[game];
/*TODO*///
/*TODO*///	if ((count = AuditRomSet (game, &aud)) == 0)
/*TODO*///		return NOTFOUND;
/*TODO*///
/*TODO*///	if (count == -1) return CORRECT;
/*TODO*///
/*TODO*///        if (gamedrv->clone_of)
/*TODO*///	{
/*TODO*///		int i;
/*TODO*///		int cloneRomsFound = 0;
/*TODO*///
/*TODO*///		/* count number of roms found that are unique to clone */
/*TODO*///		for (i = 0; i < count; i++)
/*TODO*///			if (aud[i].status != AUD_ROM_NOT_FOUND)
/*TODO*///				if (!RomInSet (gamedrv->clone_of, aud[i].expchecksum))
/*TODO*///					cloneRomsFound++;
/*TODO*///
/*TODO*///                #ifndef MESS
/*TODO*///                /* Different MESS systems can use the same ROMs */
/*TODO*///		if (cloneRomsFound == 0)
/*TODO*///			return CLONE_NOTFOUND;
/*TODO*///                #endif
/*TODO*///	}
/*TODO*///
/*TODO*///	while (count--)
/*TODO*///	{
/*TODO*///		archive_status |= aud->status;
/*TODO*///
/*TODO*///		switch (aud->status)
/*TODO*///		{
/*TODO*///			case AUD_ROM_NOT_FOUND:
/*TODO*///				verify_printf ("%-8s: %-12s %7d bytes %08x NOT FOUND\n",
/*TODO*///					drivers[game]->name, aud->rom, aud->explength, aud->expchecksum);
/*TODO*///				break;
/*TODO*///			case AUD_NOT_AVAILABLE:
/*TODO*///				verify_printf ("%-8s: %-12s %7d bytes NOT FOUND - NO GOOD DUMP KNOWN\n",
/*TODO*///					drivers[game]->name, aud->rom, aud->explength);
/*TODO*///				break;
/*TODO*///			case AUD_ROM_NEED_DUMP:
/*TODO*///				verify_printf ("%-8s: %-12s %7d bytes NO GOOD DUMP KNOWN\n",
/*TODO*///					drivers[game]->name, aud->rom, aud->explength);
/*TODO*///				break;
/*TODO*///			case AUD_BAD_CHECKSUM:
/*TODO*///				verify_printf ("%-8s: %-12s %7d bytes %08x INCORRECT CHECKSUM: %08x\n",
/*TODO*///					drivers[game]->name, aud->rom, aud->explength, aud->expchecksum,aud->checksum);
/*TODO*///				break;
/*TODO*///			case AUD_ROM_NEED_REDUMP:
/*TODO*///				verify_printf ("%-8s: %-12s %7d bytes ROM NEEDS REDUMP\n",
/*TODO*///					drivers[game]->name, aud->rom, aud->explength);
/*TODO*///				break;
/*TODO*///			case AUD_MEM_ERROR:
/*TODO*///				verify_printf ("Out of memory reading ROM %s\n", aud->rom);
/*TODO*///				break;
/*TODO*///			case AUD_LENGTH_MISMATCH:
/*TODO*///				verify_printf ("%-8s: %-12s %7d bytes %08x INCORRECT LENGTH: %8d\n",
/*TODO*///					drivers[game]->name, aud->rom, aud->explength, aud->expchecksum,aud->length);
/*TODO*///				break;
/*TODO*///			case AUD_ROM_GOOD:
/*TODO*///#if 0    /* if you want a full accounting of roms */
/*TODO*///				verify_printf ("%-8s: %-12s %7d bytes %08x ROM GOOD\n",
/*TODO*///					drivers[game]->name, aud->rom, aud->explength, aud->expchecksum);
/*TODO*///#endif
/*TODO*///				break;
/*TODO*///		}
/*TODO*///		aud++;
/*TODO*///	}
/*TODO*///
/*TODO*///	if (archive_status & (AUD_ROM_NOT_FOUND|AUD_BAD_CHECKSUM|AUD_MEM_ERROR|AUD_LENGTH_MISMATCH))
/*TODO*///		return INCORRECT;
/*TODO*///	if (archive_status & (AUD_ROM_NEED_DUMP|AUD_ROM_NEED_REDUMP|AUD_NOT_AVAILABLE))
/*TODO*///		return BEST_AVAILABLE;
/*TODO*///
/*TODO*///	return CORRECT;
/*TODO*///
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*///static tMissingSample *gMissingSamples = NULL;
/*TODO*///
/*TODO*////* Builds a list of every missing sample. Returns total number of missing
/*TODO*///   samples, or -1 if no samples were found. Sets audit to point to the
/*TODO*///   list of missing samples. */
/*TODO*///int AuditSampleSet (int game, tMissingSample **audit)
/*TODO*///{
/*TODO*///	int skipfirst;
/*TODO*///	void *f;
/*TODO*///	const char **samplenames, *sharedname;
/*TODO*///	int exist;
/*TODO*///	static const struct GameDriver *gamedrv;
/*TODO*///	int j;
/*TODO*///	int count = 0;
/*TODO*///	tMissingSample *aud;
/*TODO*///
/*TODO*///	gamedrv = drivers[game];
/*TODO*///	samplenames = NULL;
/*TODO*///#if (HAS_SAMPLES || HAS_VLM5030)
/*TODO*///	for( j = 0; gamedrv->drv->sound[j].sound_type && j < MAX_SOUND; j++ )
/*TODO*///	{
/*TODO*///#if (HAS_SAMPLES)
/*TODO*///		if( gamedrv->drv->sound[j].sound_type == SOUND_SAMPLES )
/*TODO*///			samplenames = ((struct Samplesinterface *)gamedrv->drv->sound[j].sound_interface)->samplenames;
/*TODO*///#endif
/*TODO*///#if (HAS_VLM5030)
/*TODO*///		if( gamedrv->drv->sound[j].sound_type == SOUND_VLM5030 )
/*TODO*///			samplenames = ((struct VLM5030interface *)gamedrv->drv->sound[j].sound_interface)->samplenames;
/*TODO*///#endif
/*TODO*///	}
/*TODO*///#endif
/*TODO*///    /* does the game use samples at all? */
/*TODO*///	if (samplenames == 0 || samplenames[0] == 0)
/*TODO*///		return 0;
/*TODO*///
/*TODO*///	/* take care of shared samples */
/*TODO*///	if (samplenames[0][0] == '*')
/*TODO*///	{
/*TODO*///		sharedname=samplenames[0]+1;
/*TODO*///		skipfirst = 1;
/*TODO*///	}
/*TODO*///	else
/*TODO*///	{
/*TODO*///		sharedname = NULL;
/*TODO*///		skipfirst = 0;
/*TODO*///	}
/*TODO*///
/*TODO*///	/* do we have samples for this game? */
/*TODO*///	exist = osd_faccess (gamedrv->name, OSD_FILETYPE_SAMPLE);
/*TODO*///
/*TODO*///	/* try shared samples */
/*TODO*///	if (!exist && skipfirst)
/*TODO*///		exist = osd_faccess (sharedname, OSD_FILETYPE_SAMPLE);
/*TODO*///
/*TODO*///	/* if still not found, we're done */
/*TODO*///	if (!exist)
/*TODO*///		return -1;
/*TODO*///
/*TODO*///	/* allocate missing samples list (if necessary) */
/*TODO*///	if (!gMissingSamples)
/*TODO*///		gMissingSamples = (tMissingSample *)malloc (AUD_MAX_SAMPLES * sizeof (tMissingSample));
/*TODO*///
/*TODO*///	if (gMissingSamples)
/*TODO*///		*audit = aud = gMissingSamples;
/*TODO*///	else
/*TODO*///		return 0;
/*TODO*///
/*TODO*///	for (j = skipfirst; samplenames[j] != 0; j++)
/*TODO*///	{
/*TODO*///		/* skip empty definitions */
/*TODO*///		if (strlen (samplenames[j]) == 0)
/*TODO*///			continue;
/*TODO*///		f = osd_fopen (gamedrv->name, samplenames[j], OSD_FILETYPE_SAMPLE, 0);
/*TODO*///		if (f == NULL && skipfirst)
/*TODO*///			f = osd_fopen (sharedname, samplenames[j], OSD_FILETYPE_SAMPLE, 0);
/*TODO*///
/*TODO*///		if (f)
/*TODO*///			osd_fclose(f);
/*TODO*///		else
/*TODO*///		{
/*TODO*///			strcpy (aud->name, samplenames[j]);
/*TODO*///			count++;
/*TODO*///			aud++;
/*TODO*///		}
/*TODO*///	}
/*TODO*///	return count;
/*TODO*///}
/*TODO*///
/*TODO*///
/*TODO*////* Generic function for evaluating a sampleset. Some platforms may wish to
/*TODO*///   call AuditSampleSet() instead and implement their own reporting (like MacMAME). */
/*TODO*///int VerifySampleSet (int game, verify_printf_proc verify_printf)
/*TODO*///{
/*TODO*///	tMissingSample	*aud;
/*TODO*///	int				count;
/*TODO*///
/*TODO*///	count = AuditSampleSet (game, &aud);
/*TODO*///	if (count==-1)
/*TODO*///		return NOTFOUND;
/*TODO*///	else if (count==0)
/*TODO*///		return CORRECT;
/*TODO*///
/*TODO*///	/* list missing samples */
/*TODO*///	while (count--)
/*TODO*///	{
/*TODO*///		verify_printf ("%-8s: %s NOT FOUND\n", drivers[game]->name, aud->name);
/*TODO*///		aud++;
/*TODO*///	}
/*TODO*///
/*TODO*///	return INCORRECT;
/*TODO*///}
/*TODO*///    
}
