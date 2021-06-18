package ro.zero.zeronotes.ui.popups;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Habit;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.NoteType;
import ro.zero.zeronotes.notes.Project;
import ro.zero.zeronotes.ui.listeners.OnPopupDismissListener;

public class CreateNotePopup {
	private String noteText;
	private int noteType = -1;
	private LocalDate date;
	private boolean cancelled = true;

	private final List<OnPopupDismissListener> listeners = new ArrayList<>();

	public void showPopupWindow(LayoutInflater inflater, LocalDate defaultDate, int defaultNoteType) {
		// Create View
		View view = inflater.inflate(R.layout.popup_create_note, null);

		TextView noteContents = view.findViewById(R.id.popup_create_note_note_text);
		Spinner noteTypeSelector = view.findViewById(R.id.popup_create_note_note_type);
		TextView dateTextButton = view.findViewById(R.id.popup_create_note_date_text);

		Button createButton = view.findViewById(R.id.popup_create_note_create_button);
		Button cancelButton = view.findViewById(R.id.popup_create_note_cancel_button);

		ConstraintLayout dateContainer = view.findViewById(R.id.popup_create_note_date_container);

		noteType = defaultNoteType;
		noteTypeSelector.setSelection(noteType);

		date = defaultDate;
		dateTextButton.setText(date.toString());

		// Create Popup Window
		final PopupWindow popupWindow = new PopupWindow(
				view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				true
		);

		// Set up view interactions
		noteTypeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(position == NoteType.NOTE) {
					dateContainer.setVisibility(View.VISIBLE);
				} else {
					dateContainer.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				dateContainer.setVisibility(View.VISIBLE);
			}
		});

		dateTextButton.setOnClickListener(v -> {
			SelectDatePopup datePopup = new SelectDatePopup();
			datePopup.showPopupWindow(inflater,defaultDate);
			datePopup.addOnPopupDismissListener(() -> {
				if(datePopup.isCancelled()) return;
				date = datePopup.getSelectedDate();
				dateTextButton.setText(date.toString());
			});
		});

		createButton.setOnClickListener(v -> {
			cancelled = false;
			popupWindow.dismiss();
		});
		cancelButton.setOnClickListener(v -> {
			cancelled = true;
			popupWindow.dismiss();
		});
		// On popup dismiss

		popupWindow.setOnDismissListener(() -> {
			noteText = noteContents.getText().toString();
			noteType = noteTypeSelector.getSelectedItemPosition();
			for(OnPopupDismissListener listener : listeners) {
				listener.onDismiss();
			}
		});

		// Show Popup
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}

	public void addOnPopupDismissListener(OnPopupDismissListener listener) {
		this.listeners.add(listener);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public Note getNote() {
		Note note = new Note();

		note.date = date;
		note.noteText = noteText;

		return note;
	}

	public Habit getHabit() {
		Habit habit = new Habit();

		habit.noteText = noteText;

		return habit;
	}

	public Project getProject() {
		Project project = new Project();

		project.noteText = noteText;

		return project;
	}

	public int getNoteType() {
		return noteType;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getNoteText() {
		return noteText;
	}
}
