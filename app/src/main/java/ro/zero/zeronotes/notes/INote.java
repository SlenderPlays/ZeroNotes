package ro.zero.zeronotes.notes;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Base note class that is inherited by all other types of notes.
 */
public class INote {
	/**
	 * The Unique identifier by which the note is identified by.
	 * If two notes have the same id they are considered the same (equal).
	 */
	public UUID id;
	/**
	 * The main contenst of the note - what it <i>actually</i> says to do!
	 */
	public String noteText;
	/**
	 * The Type of note we are dealing with:
	 * Note = 0
	 * Habit = 1
	 * Project = 2
	 */
	public int noteType;

	public ArrayList<SubTask> subTasks = new ArrayList<>();

	public INote() {
		id = UUID.randomUUID();
	}

	public void setSubTaskParent() {
		for (SubTask task: subTasks) {
			task.parent = this;
			task.setSubTaskParent();
		}
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof INote)) return false;
		return this.id == ((INote) obj).id;
	}
}
