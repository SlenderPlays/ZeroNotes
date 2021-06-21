package ro.zero.zeronotes.ui.listeners.implementations;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.time.LocalDate;

import ro.zero.zeronotes.notes.Habit;
import ro.zero.zeronotes.notes.INote;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.NoteType;
import ro.zero.zeronotes.notes.Project;
import ro.zero.zeronotes.notes.SubTask;
import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.popups.NoteOptionsPopup;

public class OnNoteLongClickListenerImpl implements View.OnLongClickListener {
	public INote clickedNote;

	private final LayoutInflater inflater;
	private final Adapter adapter;

	public OnNoteLongClickListenerImpl(Adapter adapter, LayoutInflater inflater) {
		this.adapter = adapter;
		this.inflater = inflater;
	}

	@Override
	public boolean onLongClick(View v) {
		NoteOptionsPopup noteOptionsPopup = new NoteOptionsPopup();
		noteOptionsPopup.showPopupWindow(inflater,clickedNote);
		noteOptionsPopup.addOnPopupDismissListener( () -> {
			if(noteOptionsPopup.isCancelled()) return;

			if(noteOptionsPopup.isRemovalRequested()) {
				switch (clickedNote.noteType) {
					case NoteType.NOTE: {
						Note removedNote = (Note)clickedNote;

						DataStorageManager.getInstance().saveData
								.getNotes(removedNote.date).remove(removedNote);
						DataStorageManager.getInstance().save();
						break;
					}
					case NoteType.HABIT: {
						Habit removedHabit = (Habit)clickedNote;

						DataStorageManager.getInstance().saveData.habits.remove(removedHabit);
						break;
					}
					case NoteType.PROJECT: {
						Project removedProject = (Project)clickedNote;

						DataStorageManager.getInstance().saveData.projects.remove(removedProject);
						break;
					}
					case NoteType.SUBTASK: {
						SubTask removedSubTask = (SubTask)clickedNote;

						removedSubTask.parent.subTasks.remove(removedSubTask);
						DataStorageManager.getInstance().save();
					}
					default: {
						break;
					}
				}

			} else if(noteOptionsPopup.isNoteEdited()) {
				switch (clickedNote.noteType) {
					case NoteType.NOTE: {
						Note editedNote = (Note)clickedNote;
						LocalDate oldDate = editedNote.date;
						LocalDate newDate = noteOptionsPopup.getNewDate();

						editedNote.noteText = noteOptionsPopup.getNewNoteText();

						DataStorageManager.getInstance().saveData
								.getNotes(oldDate).remove(editedNote);
						DataStorageManager.getInstance().saveData
								.getNotes(newDate).add(editedNote);

						DataStorageManager.getInstance().save();
						break;
					}
					case NoteType.HABIT: {
						Habit editedHabit = (Habit)clickedNote;

						editedHabit.noteText = noteOptionsPopup.getNewNoteText();

						DataStorageManager.getInstance().save();
						break;
					}
					case NoteType.PROJECT: {
						Project editedProject = (Project)clickedNote;

						editedProject.noteText = noteOptionsPopup.getNewNoteText();

						DataStorageManager.getInstance().save();
						break;
					}
					case NoteType.SUBTASK: {
						SubTask editedSubTask = (SubTask)clickedNote;

						editedSubTask.noteText = noteOptionsPopup.getNewNoteText();

						DataStorageManager.getInstance().save();
						break;
					}
					default: {
						break;
					}
				}
			} else if(noteOptionsPopup.isSubTaskAdded()) {
				SubTask subTask = noteOptionsPopup.getNewSubTask();
				subTask.parent = clickedNote;
				clickedNote.subTasks.add(subTask);

				DataStorageManager.getInstance().save();
			}

			adapter.notifyDataSetChanged();
		});
		return true;
	}
}
