package ro.zero.zeronotes.notes;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.UUID;

/**
 * The regular Note class, used in the "Notes" section of the program. This class is mostly used for data-storage rather then logic implementation.
 * <br><br>
 * The Note contains an UUID by which it can be identified, the text of the note, the date of creation (used to show the note only on that day) and
 * a boolean depicting whether it has been finished or not.
 * <br>
 * The UUID is set automatically by the constructor.
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
public class Note {
	/**
	 * The Unique identifier by which the note is identified by. If two notes have the same id they are considered the same (equal).
	 */
	public UUID id;
	public String noteText;
	public boolean finished = false;
	public Date date;

	public Note() {
		id = UUID.randomUUID();
		date = new Date(System.currentTimeMillis());
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
	}
	/**
	 * Set the date of creation of the note, and return this object (the note) to be able to chain multiple with commands.
	 * @param str Date of creation
	 * @return this note
	 */
	public Note withDate(Date date) {
		this.date = date;
		return this;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if(obj == null) return false;
		if(obj.getClass().equals(this.getClass())) return false;
		return this.id == ((Note) obj).id;
	}
}
