package ro.zero.zeronotes.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.storage.StorageManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.NoteType;
import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.NoteRecyclerViewAdapter;
import ro.zero.zeronotes.ui.popups.CreateNotePopup;
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
				updateDate(inflater,selectDatePopup.getSelectedDate());
			});
		});
		daySelectorLeft.setOnClickListener(v -> {
			updateDate(inflater,selectedDate.minusDays(1));
		});
		daySelectorRight.setOnClickListener(v -> {
			updateDate(inflater,selectedDate.plusDays(1));
		});

		updateDate(inflater,selectedDate);
		return view;
	}
	private void updateDate(LayoutInflater inflater, LocalDate date) {
		selectedDate = date;
		dateTextView.setText(selectedDate.toString());
		updateRecyclerView(inflater);
	}

	private void updateRecyclerView(LayoutInflater inflater) {
		if(recyclerView == null) return;

		ArrayList<Note> notes = DataStorageManager.getInstance().saveData.getNotes(selectedDate);
		NoteRecyclerViewAdapter adapter = new NoteRecyclerViewAdapter(notes, v -> {
			CreateNotePopup createNotePopup = new CreateNotePopup();
			createNotePopup.showPopupWindow(inflater,selectedDate);

			createNotePopup.setOnPopupDismissListener(() -> {
				if(createNotePopup.isCancelled()) return;

				switch (createNotePopup.getNoteType()) {
					case NoteType.NOTE: {
						Note newNote = new Note().withText(createNotePopup.getContents());
						LocalDate noteDate = createNotePopup.getDate();
						DataStorageManager.getInstance().saveData.getNotes(noteDate).add(newNote);
						DataStorageManager.getInstance().save();
						break;
					}
					case NoteType.HABIT: {
						Toast.makeText(getContext(),"Can't make Habits now.",Toast.LENGTH_SHORT).show();
						break;
					}
					case NoteType.MONTHLY: {
						Toast.makeText(getContext(),"Can't make Monthly now.",Toast.LENGTH_SHORT).show();
						break;
					}
					default: {
						break;
					}
				}
			});
		});
		recyclerView.setAdapter(adapter);
	}
}