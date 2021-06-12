package ro.zero.zeronotes.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.zero.zeronotes.notes.Note;

/**
 * This class contains every bit of information and data that needs to be stored between app sessions. It is serialized and
 * deserialized by GSON.
 */
public class SaveData {
	/**
	 * A list of all of the notes under the "Notes" tab.
	 */
	public Map<LocalDate, ArrayList<Note>> noteMap;

	public SaveData() {
		noteMap = new HashMap<>();
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
