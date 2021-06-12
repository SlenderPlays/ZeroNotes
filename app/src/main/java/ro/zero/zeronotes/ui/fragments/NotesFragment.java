package ro.zero.zeronotes.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.NoteRecyclerViewAdapter;

public class NotesFragment extends Fragment {

	public NotesFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notes, container, false);

		ArrayList<Note> notes = DataStorageManager.getInstance().saveData.getNotes(LocalDate.now());
		// If the save data is empty populate it with some dummy notes. This is only for debug and should be removed at launch.
		// It also exemplifies how to use the "with" methods.
		if(notes.isEmpty()) {
			ArrayList<Note> dummyNotes = new ArrayList<>();
			dummyNotes.add(new Note().withText("Walk the dog"));
			dummyNotes.add(new Note().withText("Walk the cat").withFinishedStatus(true));
			dummyNotes.add(new Note().withText("Go out with your friends"));
			dummyNotes.add(new Note().withText("Email the boss"));
			dummyNotes.add(new Note().withText("Throw a party"));

			notes.addAll(dummyNotes);
			DataStorageManager.getInstance().save();
		}

		RecyclerView recyclerView = view.findViewById(R.id.noteRecycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

		NoteRecyclerViewAdapter adapter = new NoteRecyclerViewAdapter(notes);
		recyclerView.setAdapter(adapter);

		return view;
	}
}