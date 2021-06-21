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
import ro.zero.zeronotes.notes.SubTask;
import ro.zero.zeronotes.ui.listeners.OnPopupDismissListener;

public class NoteOptionsPopup {
	private boolean isRemovalRequested = false;
	private boolean noteEdited = false;
	private boolean subTaskAdded = false;
	private boolean cancelled = false;

	private String newNoteText;
	private LocalDate newDate;
	private SubTask newSubTask;

	private List<OnPopupDismissListener> listeners = new ArrayList<>();

	public void showPopupWindow(LayoutInflater inflater, INote note) {
		// Create View
		View view = inflater.inflate(R.layout.popup_note_options, null);
		TextView editButton = view.findViewById(R.id.popup_note_options_edit);
		TextView addSubTaskButton = view.findViewById(R.id.popup_note_options_add_subtask);
		TextView removeButton = view.findViewById(R.id.popup_note_options_remove);
		TextView cancelButton = view.findViewById(R.id.popup_note_options_cancel);

		newNoteText = note.noteText;

		if (note.noteType == NoteType.NOTE) {
			newDate = ((Note) note).date;
		} else {
			newDate = LocalDate.now();
		}

		// Create Popup Window
		final PopupWindow popupWindow = new PopupWindow(
				view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				true
		);

		// Set up view interactions
		view.setOnClickListener(v -> {
			cancelled = true;
			popupWindow.dismiss();
		});

		cancelButton.setOnClickListener(v -> {
			cancelled = true;
			popupWindow.dismiss();
		});
		removeButton.setOnClickListener(v -> {
			isRemovalRequested = true;
			popupWindow.dismiss();
		});

		addSubTaskButton.setOnClickListener(v -> {
			EditSubTaskPopup createSubTaskPopup = new EditSubTaskPopup();
			createSubTaskPopup.showPopupWindow(inflater,"");
			createSubTaskPopup.addOnPopupDismissListener(() -> {
				if(createSubTaskPopup.isCancelled()) return;
				subTaskAdded = true;

				newSubTask = createSubTaskPopup.getNewSubTask();

				popupWindow.dismiss();
			});
		});

		editButton.setOnClickListener(v -> {
			if(note.noteType == NoteType.SUBTASK) {
				EditSubTaskPopup editSubTaskPopup = new EditSubTaskPopup();
				editSubTaskPopup.showPopupWindow(inflater,newNoteText);
				editSubTaskPopup.addOnPopupDismissListener(() -> {
					if(editSubTaskPopup.isCancelled()) return;
					noteEdited = true;

					newNoteText = editSubTaskPopup.getNewNoteText();

					popupWindow.dismiss();
				});
			} else {
				EditNotePopup editNotePopup = new EditNotePopup();
				editNotePopup.showPopupWindow(inflater, newNoteText, newDate, note.noteType);
				editNotePopup.addOnPopupDismissListener(() -> {
					if (editNotePopup.isCancelled()) return;
					noteEdited = true;

					newNoteText = editNotePopup.getNewNoteText();
					newDate = editNotePopup.getNewDate();

					popupWindow.dismiss();
				});
			}
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

	public void addOnPopupDismissListener(OnPopupDismissListener listener) {
		this.listeners.add(listener);
	}

	public boolean isNoteEdited() {
		return noteEdited;
	}

	public boolean isRemovalRequested() {
		return isRemovalRequested;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public boolean isSubTaskAdded() {
		return subTaskAdded;
	}

	public void applyChanges(INote note) {
		note.noteText = newNoteText;
		if (note.noteType == NoteType.NOTE) {
			((Note) note).date = newDate;
		}
	}

	public LocalDate getNewDate() {
		return newDate;
	}

	public String getNewNoteText() {
		return newNoteText;
	}

	public SubTask getNewSubTask() {
		return newSubTask;
	}
}
