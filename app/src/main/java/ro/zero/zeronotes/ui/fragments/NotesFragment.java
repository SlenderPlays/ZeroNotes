package ro.zero.zeronotes.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.NoteRecyclerViewAdapter;
import ro.zero.zeronotes.ui.popups.SelectDatePopup;

public class NotesFragment extends Fragment {

	private LocalDate selectedDate = LocalDate.now();
	private RecyclerView recyclerView;
	private TextView dateTextView;

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

		// Recycler View
		// ===============
		recyclerView = view.findViewById(R.id.noteRecycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

		ArrayList<Note> notes = DataStorageManager.getInstance().saveData.getNotes(selectedDate);
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

		NoteRecyclerViewAdapter adapter = new NoteRecyclerViewAdapter(notes);
		recyclerView.setAdapter(adapter);
		// ==========
		// Calendar
		// ==========
		dateTextView = view.findViewById(R.id.day_selector_text);
		ImageView daySelectorLeft = view.findViewById(R.id.day_selector_left);
		ImageView daySelectorRight = view.findViewById(R.id.day_selector_right);

		dateTextView.setOnClickListener(v -> {
			SelectDatePopup selectDatePopup = new SelectDatePopup();
			selectDatePopup.showPopupWindow(inflater,selectedDate);

			selectDatePopup.setOnPopupDismissListener(() -> {
				if(selectDatePopup.isCancelled()) return;
				updateDate(selectDatePopup.getSelectedDate());
			});
		});
		daySelectorLeft.setOnClickListener(v -> {
			updateDate(selectedDate.minusDays(1));
		});
		daySelectorRight.setOnClickListener(v -> {
			updateDate(selectedDate.plusDays(1));
		});

		updateDate(selectedDate);
		return view;
	}
	private void updateDate(LocalDate date) {
		selectedDate = date;
		dateTextView.setText(selectedDate.toString());
		updateRecyclerView();
	}

	private void updateRecyclerView() {
		if(recyclerView == null) return;

		ArrayList<Note> notes = DataStorageManager.getInstance().saveData.getNotes(selectedDate);
		NoteRecyclerViewAdapter adapter = new NoteRecyclerViewAdapter(notes);
		recyclerView.setAdapter(adapter);
	}
}