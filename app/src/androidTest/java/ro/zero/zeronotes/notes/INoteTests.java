package ro.zero.zeronotes.notes;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class INoteTests {
	@Test
	public void testEquals() {
		UUID id = UUID.randomUUID();

		INote iNote1 = new INote();
		INote iNote2 = new INote();

		Note note = new Note();
		Habit habit = new Habit();
		Project project = new Project();

		assertFalse(iNote1.equals(iNote2));
		assertFalse(iNote2.equals(iNote1));

		assertFalse(iNote1.equals(note));
		assertFalse(note.equals(iNote1));

		assertFalse(iNote1.equals(habit));
		assertFalse(habit.equals(iNote1));

		assertFalse(iNote1.equals(project));
		assertFalse(project.equals(iNote1));

		assertFalse(note.equals(habit));
		assertFalse(habit.equals(note));

		assertFalse(note.equals(project));
		assertFalse(project.equals(note));

		assertFalse(habit.equals(project));
		assertFalse(project.equals(habit));

		iNote1.id = id;
		iNote2.id = id;
		note.id = id;
		habit.id = id;
		project.id = id;

		assertTrue(iNote1.equals(iNote2));
		assertTrue(iNote2.equals(iNote1));

		assertTrue(iNote1.equals(note));
		assertTrue(note.equals(iNote1));

		assertTrue(iNote1.equals(habit));
		assertTrue(habit.equals(iNote1));

		assertTrue(iNote1.equals(project));
		assertTrue(project.equals(iNote1));

		assertTrue(note.equals(habit));
		assertTrue(habit.equals(note));

		assertTrue(note.equals(project));
		assertTrue(project.equals(note));

		assertTrue(habit.equals(project));
		assertTrue(project.equals(habit));

	}
}
