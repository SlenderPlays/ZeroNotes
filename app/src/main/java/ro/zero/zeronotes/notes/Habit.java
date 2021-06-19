package ro.zero.zeronotes.notes;

import java.time.LocalDate;

/**
 * Habit - a type of note that is similar to the note, but isn't date-dependent and is reset every
 * day.
 */
public class Habit extends INote {
	/**
	 * How many days in a row has this task been completed.
 	 */
	public int streak = 0;
	/**
	 * The date that was last marked as "finished".
	 */
	public LocalDate lastFinished = null;

	public Habit() {
		super();
		noteType = NoteType.HABIT;
	}

	public void validateStreak() {
		if 	(lastFinished == null ||
			(!lastFinished.equals(LocalDate.now()) &&
			 !lastFinished.plusDays(1).equals(LocalDate.now()))) {
			streak = 0;
		}
	}

	public boolean isFinished() {
		// Warning! use .equals and not ==
		// This caused errors...
		// Also make sure to call the equals() on the current date, because
		// lastFinished can be null!
		return LocalDate.now().equals(lastFinished);
	}

	public void setFinished(boolean finished) {
		// If the habit is finished and we want to make it finished,
		// or if the note is NOT finished yet and we want to make it NOT finished
		// (aka finished == isFinished)
		// then we break before we do anything else because we would otherwise break the streak.
		if (finished == isFinished()) return;

		if(finished) {
			lastFinished = LocalDate.now();
			streak++;
		} else {
			// This checks if yesterday the check was done successfully
			// If so, set the last set date to yesterday, to keep the streak.
			if (lastFinished != null && streak > 1) {
				lastFinished = lastFinished.minusDays(1);
				streak--;
			} else {
				lastFinished = null;
				streak = 0;
			}

		}
	}
}
