package ro.zero.zeronotes.ui.popups;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.INote;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.NoteType;

public class NoteOptionsPopup {

	private boolean isRemovalRequested = false;
	private boolean noteEdited = false;

	private String newContents;
	private LocalDate newDate;
	private int newNoteType;

	private List<OnPopupDismissListener> listeners = new ArrayList<>();

	public void showPopupWindow(LayoutInflater inflater, INote note) {
		showPopupWindow(inflater, note, LocalDate.now());
	}

	public void showPopupWindow(LayoutInflater inflater, INote note, LocalDate currentDate) {
		// Create View
		View view = inflater.inflate(R.layout.popup_note_options, null);
		TextView editButton = view.findViewById(R.id.popup_note_options_edit);
		TextView removeButton = view.findViewById(R.id.popup_note_options_remove);
		TextView cancelButton = view.findViewById(R.id.popup_note_options_cancel);

		newDate = currentDate;
		newContents = note.noteText;
		newNoteType = note.noteType;

		// Create Popup Window
		final PopupWindow popupWindow = new PopupWindow(
				view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				true
		);

		// Set up view interactions
		view.setOnClickListener(v -> {
			popupWindow.dismiss();
		});

		cancelButton.setOnClickListener(v -> {
			popupWindow.dismiss();
		});

		removeButton.setOnClickListener(v -> {
			isRemovalRequested = true;
			popupWindow.dismiss();
		});

		editButton.setOnClickListener(v -> {
			EditNotePopup editNotePopup = new EditNotePopup();
			editNotePopup.showPopupWindow(inflater, note, currentDate);
			editNotePopup.setOnPopupDismissListener(() -> {
				if (editNotePopup.isCancelled()) return;
				noteEdited = true;

				newContents = editNotePopup.getContents();
				newNoteType = editNotePopup.getNoteType();
				if (newNoteType == NoteType.NOTE || newNoteType == NoteType.HABIT) {
					newDate = editNotePopup.getDate();
				}
				popupWindow.dismiss();
			});
		});

		// On popup dismiss
		popupWindow.setOnDismissListener(() -> {
			for (OnPopupDismissListener listener : listeners) {
				listener.onDismiss();
			}
		});

		// Show Popup
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}

	public void setOnPopupDismissListener(OnPopupDismissListener listener) {
		this.listeners.add(listener);
	}

	public boolean isNoteEdited() {
		return noteEdited;
	}

	public boolean isRemovalRequested() {
		return isRemovalRequested;
	}

	public int getNewNoteType() {
		return newNoteType;
	}

	public LocalDate getNewDate() {
		return newDate;
	}

	public String getNewContents() {
		return newContents;
	}
}
