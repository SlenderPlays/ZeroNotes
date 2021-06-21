package ro.zero.zeronotes.ui.popups;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.SubTask;
import ro.zero.zeronotes.ui.listeners.OnPopupDismissListener;

public class EditSubTaskPopup {
	private String newNoteText;
	private boolean cancelled = true;

	private final List<OnPopupDismissListener> listeners = new ArrayList<>();

	public void showPopupWindow(LayoutInflater inflater, String defaultText) {
		// Create View
		View view = inflater.inflate(R.layout.popup_edit_subtask, null);

		TextView subtaskTextView = view.findViewById(R.id.popup_edit_subtask_text);

		Button continueButton = view.findViewById(R.id.popup_edit_subtask_continue_button);
		Button cancelButton = view.findViewById(R.id.popup_edit_subtask_cancel_button);

		newNoteText = defaultText;
		subtaskTextView.setText(defaultText);

		// Create Popup Window
		final PopupWindow popupWindow = new PopupWindow(
				view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				true
		);

		// Set up view interactions
		continueButton.setOnClickListener(v -> {
			cancelled = false;
			popupWindow.dismiss();
		});
		cancelButton.setOnClickListener(v -> {
			cancelled = true;
			popupWindow.dismiss();
		});

		// On popup dismiss

		popupWindow.setOnDismissListener(() -> {
			newNoteText = subtaskTextView.getText().toString();
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

	public String getNewNoteText() {
		return newNoteText;
	}

	public SubTask getNewSubTask() {
		SubTask task = new SubTask();
		task.noteText = newNoteText;
		return task;
	}
}
