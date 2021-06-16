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
			(lastFinished != LocalDate.now() &&
			 lastFinished.plusDays(1) != LocalDate.now())) {
			streak = 0;
		}
	}

	public boolean isFinished() {
		return lastFinished == LocalDate.now();
	}

	public void setFinished(boolean finished) {
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
