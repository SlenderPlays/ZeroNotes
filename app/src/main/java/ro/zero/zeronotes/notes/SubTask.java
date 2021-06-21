package ro.zero.zeronotes.notes;

import java.util.ArrayList;

import ro.zero.zeronotes.serialization.Exclude;

/**
 * SubTask - Not exactly a note, more like an extension of a note which is directly connected to
 * regular note types.
 */
public class SubTask extends INote {
	@Exclude
	public INote parent;
	public boolean finished = false;

	public SubTask() {
		super();
		noteType = NoteType.SUBTASK;
	}
}
