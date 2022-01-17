/**
 * ported to 0.37b7
 */
package arcadeflex.v037b7.mame;

public class auditH {
/*TODO*///
/*TODO*///#ifndef AUDIT_H
/*TODO*///#define AUDIT_H
/*TODO*///
/*TODO*////* return values from VerifyRomSet and VerifySampleSet */
/*TODO*///#define CORRECT   		0
/*TODO*///#define NOTFOUND  		1
/*TODO*///#define INCORRECT 		2
/*TODO*///#define CLONE_NOTFOUND	3
/*TODO*///#define BEST_AVAILABLE	4
/*TODO*///
/*TODO*////* rom status values for tAuditRecord.status */
/*TODO*///#define AUD_ROM_GOOD		0x00000001
/*TODO*///#define AUD_ROM_NEED_REDUMP	0x00000002
/*TODO*///#define AUD_ROM_NOT_FOUND	0x00000004
/*TODO*///#define AUD_NOT_AVAILABLE	0x00000008
/*TODO*///#define AUD_BAD_CHECKSUM	0x00000010
/*TODO*///#define AUD_MEM_ERROR		0x00000020
/*TODO*///#define AUD_LENGTH_MISMATCH	0x00000040
/*TODO*///#define AUD_ROM_NEED_DUMP	0x00000080
/*TODO*///
/*TODO*///#define AUD_MAX_ROMS		100	/* maximum roms per driver */
/*TODO*///#define AUD_MAX_SAMPLES		200	/* maximum samples per driver */
/*TODO*///
/*TODO*///
/*TODO*///typedef struct
/*TODO*///{
/*TODO*///	char rom[20];				/* name of rom file */
/*TODO*///	unsigned int explength;		/* expected length of rom file */
/*TODO*///	unsigned int length;		/* actual length of rom file */
/*TODO*///	unsigned int expchecksum;	/* expected checksum of rom file */
/*TODO*///	unsigned int checksum;		/* actual checksum of rom file */
/*TODO*///	int status;					/* status of rom file */
/*TODO*///} tAuditRecord;
/*TODO*///
/*TODO*///typedef struct
/*TODO*///{
/*TODO*///	char	name[20];		/* name of missing sample file */
/*TODO*///} tMissingSample;
/*TODO*///
/*TODO*///typedef void (CLIB_DECL *verify_printf_proc)(char *fmt,...);
/*TODO*///
/*TODO*///int AuditRomSet (int game, tAuditRecord **audit);
/*TODO*///int VerifyRomSet(int game,verify_printf_proc verify_printf);
/*TODO*///int AuditSampleSet (int game, tMissingSample **audit);
/*TODO*///int VerifySampleSet(int game,verify_printf_proc verify_printf);
/*TODO*///int RomInSet (const struct GameDriver *gamedrv, unsigned int crc);
/*TODO*///int RomsetMissing (int game);
/*TODO*///
/*TODO*///
/*TODO*///#endif
/*TODO*///
}
