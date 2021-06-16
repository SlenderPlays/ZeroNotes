package ro.zero.zeronotes.notes;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import ro.zero.zeronotes.serialization.Exclude;

/**
 * The regular Note class, used in the "Notes" section of the program. This class is mostly used for
 * data-storage rather then logic implementation.
 * <br><br>
 * The Note extends the base INote class, with the addition of a "finished" logic variable that
 * stores whether the note has been completed or not. It also has a field "date" which is not stored,
 * rather it is acquired at runtime, just after serialization.
 * <br><br>
 * To add values to a note you can use directly access it's members
 * <br>
 * <code>Note.noteText</code>
 * <br>
 * <br>
 * Alternatively, you can use the <i>with</i> methods for easy Note Creation
 * <br>
 * <code>new Note().withText(...).withFinishedStatus(...)</code> and so on.
 */
public class Note extends INote {
	public boolean finished = false;
	@Exclude // Exclude from serialization, date is set at runtime only.
	public LocalDate date;

	public Note() {
		super();
		noteType = NoteType.NOTE;
	}
	/**
	 * Set the text of the note, and return this object (the note) to be able to chain multiple with commands.
	 * @param str The text of the note
	 * @return this note
	 */
	public Note withText(String str) {
		noteText = str;
		return this;
	}
	/**
	 * Set the finished status of the note, and return this object (the note) to be able to chain multiple with commands.
	 * @param finished True if the note is "done"/"finished", and false otherwise
	 * @return this note
	 */
	public Note withFinishedStatus(boolean finished) {
		this.finished = finished;
		return this;
	}/**
	 * Set the date of the note, and return this object (the note) to be able to chain multiple with commands.
	 * @param date The local date of when this note is needed.
	 * @return this note
	 */

	public Note withDate(LocalDate date) {
		this.date = date;
		return this;
	}
}
