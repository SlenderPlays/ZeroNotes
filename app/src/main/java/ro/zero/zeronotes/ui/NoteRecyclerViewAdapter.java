package ro.zero.zeronotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Note;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {
	private List<Note> noteList;

	public NoteRecyclerViewAdapter(List<Note> noteList) {
		this.noteList = noteList;
	}

	// When the view holder is created
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.component_note, viewGroup, false);

		return new ViewHolder(view);
	}

	// when a view is getting it's data
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		viewHolder.setNote(noteList.get(position));
		viewHolder.setText(noteList.get(position).noteText);
		viewHolder.setCheckboxStatus(noteList.get(position).finished);
	}

	// Return the size of the dataset
	@Override
	public int getItemCount() {
		return noteList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final TextView noteTextView;
		private final ImageView checkBoxView;
		private Note note = null;

		public ViewHolder(View view) {
			super(view);

			noteTextView = view.findViewById(R.id.note_content);
			checkBoxView = view.findViewById(R.id.note_checkBox);

			checkBoxView.setOnClickListener(v -> {
				if(note != null) {
					note.finished = !note.finished;
					setCheckboxStatus(note.finished);

					// TODO: update file storage with the new note
					// note.updateData();
				}
			});
		}

		public void setText(String str) {
			noteTextView.setText(str);
		}
		public void setCheckboxStatus(boolean checked) {
			if(checked) {
				checkBoxView.setImageDrawable(UIResourceManager.getInstance().note_checkedIcon);
				noteTextView.setBackgroundResource(R.color.note_finished);
				noteTextView.setTextColor(UIResourceManager.getInstance().note_textColor_finished);
			} else {
				checkBoxView.setImageDrawable(UIResourceManager.getInstance().note_uncheckedIcon);
				noteTextView.setBackgroundResource(R.color.note_default);
				noteTextView.setTextColor(UIResourceManager.getInstance().note_textColor_default);
			}
		}
		public void setNote(Note note) {
			this.note = note;
		}
	}
}
