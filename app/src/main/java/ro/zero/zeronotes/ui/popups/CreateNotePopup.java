package ro.zero.zeronotes.ui.popups;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import ro.zero.zeronotes.R;

public class CreateNotePopup {
	private String contents;
	private int noteType = -1;
	private LocalDate date;
	private boolean cancelled = true;

	private List<OnPopupDismissListener> listeners = new ArrayList<>();

	public void showPopupWindow(LayoutInflater inflater, LocalDate currentDate) {
		// Create View
		View view = inflater.inflate(R.layout.popup_create_note, null);
		TextView noteContents = view.findViewById(R.id.popup_create_note_note_text);
		Spinner noteTypeSelector = view.findViewById(R.id.popup_create_note_note_type);
		TextView dateTextButton = view.findViewById(R.id.popup_create_note_date_text);
		Button createButton = view.findViewById(R.id.popup_create_note_create_button);
		Button cancelButton = view.findViewById(R.id.popup_create_note_cancel_button);

		date = currentDate;
		dateTextButton.setText(date.toString());
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
