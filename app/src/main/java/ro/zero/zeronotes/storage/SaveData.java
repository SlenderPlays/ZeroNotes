package ro.zero.zeronotes.storage;

import java.util.ArrayList;

import ro.zero.zeronotes.notes.Note;

/**
 * This class contains every bit of information and data that needs to be stored between app sessions. It is serialized and
 * deserialized by GSON.
 */
public class SaveData {
	/**
	 * A list of all of the notes under the "Notes" tab.
	 */
	public ArrayList<Note> notes;

	public SaveData() {
		notes = new ArrayList<>();
	}
}
