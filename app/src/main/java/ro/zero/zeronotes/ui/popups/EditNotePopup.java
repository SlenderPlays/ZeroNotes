package ro.zero.zeronotes.ui.popups;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.NoteType;
import ro.zero.zeronotes.ui.listeners.OnPopupDismissListener;

public class EditNotePopup {
	private String newNoteText;
	private LocalDate newDate;
	private boolean cancelled = true;

	private final List<OnPopupDismissListener> listeners = new ArrayList<>();

	public void showPopupWindow(LayoutInflater inflater,String defaultText, LocalDate defaultDate, int noteType) {
		// Create View
		View view = inflater.inflate(R.layout.popup_edit_note, null);

		TextView noteContents = view.findViewById(R.id.popup_edit_note_note_text);
		TextView dateTextButton = view.findViewById(R.id.popup_edit_note_date_text);

		Button editButton = view.findViewById(R.id.popup_edit_note_edit_button);
		Button cancelButton = view.findViewById(R.id.popup_edit_note_cancel_button);

		ConstraintLayout dateContainer = view.findViewById(R.id.popup_edit_note_date_container);

		newDate = defaultDate;
		dateTextButton.setText(newDate.toString());

		newNoteText = defaultText;

		noteContents.setText(defaultText);
		if (noteType == NoteType.NOTE) {
			dateContainer.setVisibility(View.VISIBLE);
		}
		else {
			dateContainer.setVisibility(View.INVISIBLE);
		}

		// Create Popup Window
		final PopupWindow popupWindow = new PopupWindow(
				view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				true
		);

		// Set up view interactions
		editButton.setOnClickListener(v -> {
			cancelled = false;
			popupWindow.dismiss();
		});
		cancelButton.setOnClickListener(v -> {
			cancelled = true;
			popupWindow.dismiss();
		});

		dateTextButton.setOnClickListener(v -> {
			SelectDatePopup datePopup = new SelectDatePopup();
			datePopup.showPopupWindow(inflater,newDate);
			datePopup.addOnPopupDismissListener(() -> {
				if(datePopup.isCancelled()) return;

				newDate = datePopup.getSelectedDate();
				dateTextButton.setText(newDate.toString());
			});
		});

		// On popup dismiss

		popupWindow.setOnDismissListener(() -> {
			newNoteText = noteContents.getText().toString();
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

	public LocalDate getNewDate() {
		return newDate;
	}

	public String getNewNoteText() {
		return newNoteText;
	}
}
