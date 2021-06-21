package ro.zero.zeronotes.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.zero.zeronotes.notes.Habit;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.Project;

/**
 * This class contains every bit of information and data that needs to be stored between app sessions. It is serialized and
 * deserialized by GSON.
 */
public class SaveData {
	/**
	 * A list of all of the notes under the "Notes" tab.
	 */
	public Map<LocalDate, ArrayList<Note>> noteMap;
	public ArrayList<Habit> habits;
	public ArrayList<Project> projects;

	public SaveData() {
		noteMap = new HashMap<>();
		habits = new ArrayList<>();
		projects = new ArrayList<>();
	}

	/**
	 * Do all of the necessary post-serialization actions to prepare the data for being properly handled.
	 */
	public void prepareData() {
		for (Map.Entry<LocalDate,ArrayList<Note>> entry: noteMap.entrySet()) {
			LocalDate date = entry.getKey();
			for (Note note: entry.getValue()) {
				note.date = date;
				note.setSubTaskParent();
			}
		}

		for (Habit habit : habits) {
			habit.validateStreak();
			habit.setSubTaskParent();
		}

		for (Project project: projects) {
			project.setSubTaskParent();
		}
	}

	public ArrayList<Note> getNotes(LocalDate date) {
		if(noteMap.containsKey(date)) {
			return noteMap.get(date);
		} else {
			ArrayList<Note> out = new ArrayList<>();
			noteMap.put(date,out);
			return out;
		}
	}
}
