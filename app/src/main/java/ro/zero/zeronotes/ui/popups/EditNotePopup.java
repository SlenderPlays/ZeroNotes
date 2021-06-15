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
import ro.zero.zeronotes.notes.INote;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.NoteType;

public class EditNotePopup {

	private String contents;
	private int noteType = -1;
	private LocalDate date;
	private boolean cancelled = true;

	private List<OnPopupDismissListener> listeners = new ArrayList<>();

	public void showPopupWindow(LayoutInflater inflater, INote note, LocalDate currentDate) {
		// Create View
		// We are going to re-use the create note popup, with some slight modifications we can
		// easily do.
		View view = inflater.inflate(R.layout.popup_create_note, null);
		TextView noteContents = view.findViewById(R.id.popup_create_note_note_text);
		Spinner noteTypeSelector = view.findViewById(R.id.popup_create_note_note_type);
		TextView dateTextButton = view.findViewById(R.id.popup_create_note_date_text);
		Button createButton = view.findViewById(R.id.popup_create_note_create_button);
		Button cancelButton = view.findViewById(R.id.popup_create_note_cancel_button);

		ConstraintLayout dateContainer = view.findViewById(R.id.popup_create_note_date_container);

		date = currentDate;

		((TextView)view.findViewById(R.id.popup_create_note_title)).setText("Edit a note");
		((Button)view.findViewById(R.id.popup_create_note_create_button)).setText("Edit");

		noteContents.setText(note.noteText);
		dateTextButton.setText(date.toString());
		noteTypeSelector.setSelection(note.noteType);
		// Create Popup Window
		final PopupWindow popupWindow = new PopupWindow(
				view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				true
		);

		// Set up view interactions
		cancelButton.setOnClickListener(v -> {
			cancelled = true;
			popupWindow.dismiss();
		});
		dateTextButton.setOnClickListener(v -> {
			SelectDatePopup datePopup = new SelectDatePopup();
			datePopup.showPopupWindow(inflater,currentDate);
			datePopup.setOnPopupDismissListener(() -> {
				if(datePopup.isCancelled()) return;
				date = datePopup.getSelectedDate();
				dateTextButton.setText(date.toString());
			});
		});

		noteTypeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(position == NoteType.NOTE || position == NoteType.HABIT) {
					dateContainer.setVisibility(View.VISIBLE);
				} else {
					dateContainer.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				dateContainer.setVisibility(View.INVISIBLE);
			}
		});

		createButton.setOnClickListener(v -> {
			cancelled = false;
			popupWindow.dismiss();
		});
		// On popup dismiss

		popupWindow.setOnDismissListener(() -> {
			contents = noteContents.getText().toString();
			noteType = noteTypeSelector.getSelectedItemPosition();
			for(OnPopupDismissListener listener : listeners) {
				listener.onDismiss();
			}
		});

		// Show Popup
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}

	public void setOnPopupDismissListener(OnPopupDismissListener listener) {
		this.listeners.add(listener);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public int getNoteType() {
		return noteType;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getContents() {
		return contents;
	}
}
