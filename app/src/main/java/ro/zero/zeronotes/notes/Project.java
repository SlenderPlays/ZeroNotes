package ro.zero.zeronotes.notes;

/**
 * Project - a type of note that is similar to the basic note, but it isn't date-dependent, and unique,
 * not being reset at any time.
 */
public class Project extends INote {
	public boolean finished;

	public Project() {
		super();
		noteType = NoteType.PROJECT;
	}
}
