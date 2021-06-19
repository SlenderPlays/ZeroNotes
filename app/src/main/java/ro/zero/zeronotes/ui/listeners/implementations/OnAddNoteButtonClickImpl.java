package ro.zero.zeronotes.ui.listeners.implementations;

import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.time.LocalDate;

import ro.zero.zeronotes.notes.Habit;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.NoteType;
import ro.zero.zeronotes.notes.Project;
import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.popups.CreateNotePopup;

public class OnAddNoteButtonClickImpl implements View.OnClickListener {

	private final LayoutInflater inflater;
	private final LocalDate defaultDate;
	private final int defaultNoteType;
	private final Adapter adapter;

	public OnAddNoteButtonClickImpl(Adapter adapter, LayoutInflater inflater, LocalDate defaultDate, int defaultNoteType) {
		this.adapter = adapter;
		this.inflater = inflater;
		this.defaultDate = defaultDate;
		this.defaultNoteType = defaultNoteType;
	}

	@Override
	public void onClick(View v) {
		CreateNotePopup createNotePopup = new CreateNotePopup();
		createNotePopup.showPopupWindow(inflater, defaultDate, defaultNoteType);

		createNotePopup.addOnPopupDismissListener(() -> {
			if (createNotePopup.isCancelled()) return;

			switch (createNotePopup.getNoteType()) {
				case NoteType.NOTE: {
					Note newNote = createNotePopup.getNote();

					DataStorageManager.getInstance().saveData.getNotes(newNote.date).add(newNote);
					DataStorageManager.getInstance().save();
					break;
				}
				case NoteType.HABIT: {
					Habit newHabit = createNotePopup.getHabit();

					DataStorageManager.getInstance().saveData.habits.add(newHabit);
					DataStorageManager.getInstance().save();
					break;
				}
				case NoteType.PROJECT: {
					Project newProject = createNotePopup.getProject();

					DataStorageManager.getInstance().saveData.projects.add(newProject);
					DataStorageManager.getInstance().save();
					break;
				}
				default: {
					break;
				}
			}

			// For whatever reason it works without this, but to prevent future bugs, I'll let this
			// sit here unless I find that it actually does not solve any (possible) issues.
			adapter.notifyDataSetChanged();
		});
	}
}
