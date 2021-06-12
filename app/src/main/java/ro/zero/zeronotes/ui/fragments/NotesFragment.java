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

public class NotesFragment extends Fragment {

	private LocalDate selectedDate = LocalDate.now();
	private LocalDate tempCalendarDate = selectedDate;
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
			View popup = inflater.inflate(R.layout.popup_select_date,null);

			final PopupWindow popupWindow = new PopupWindow(popup, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
			popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);



			popup.setOnClickListener(v1 -> {
				popupWindow.dismiss();
			});
			// Store the newly selected date into a temp variable and assign it to the thing we are working with only and only when
			// we press the "select" button.
			CalendarView calendarView = popup.findViewById(R.id.popup_calendar);
			calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
				tempCalendarDate = LocalDate.of(year,month + 1,dayOfMonth);
			});
			popup.findViewById(R.id.popup_select_button).setOnClickListener(v1 -> {
				selectedDate = tempCalendarDate;
				popupWindow.dismiss();
			});

			// On dismiss, update the date.
			popupWindow.setOnDismissListener(() -> {
				updateDate(selectedDate);
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