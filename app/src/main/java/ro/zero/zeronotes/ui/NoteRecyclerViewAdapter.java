package ro.zero.zeronotes.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Note;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {
	private List<Note> noteList;
	Drawable uncheckedIcon = null;
	Drawable checkedIcon = null;

	public NoteRecyclerViewAdapter(Context context, List<Note> noteList) {
		this.noteList = noteList;
		uncheckedIcon = ResourcesCompat.getDrawable(context.getResources(),R.drawable.checkbox_unchecked,null);
		checkedIcon = ResourcesCompat.getDrawable(context.getResources(),R.drawable.checkbox_checked,null);
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
		viewHolder.setText(noteList.get(position).noteText);
		viewHolder.setCheckboxStatus(this,noteList.get(position).finished);
	}

	// Return the size of the dataset
	@Override
	public int getItemCount() {
		return noteList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final TextView noteTextView;
		private final ImageView checkBoxView;

		public ViewHolder(View view) {
			super(view);

			noteTextView = view.findViewById(R.id.note_content);
			checkBoxView = view.findViewById(R.id.note_checkBox);

			checkBoxView.setOnClickListener(v -> {
				Toast.makeText(view.getContext(),"Not implemented",Toast.LENGTH_SHORT).show();
			});
		}

		public void setText(String str) {
			noteTextView.setText(str);
		}
		public void setCheckboxStatus(NoteRecyclerViewAdapter adapter,boolean checked) {
			if(checked) {
				checkBoxView.setImageDrawable(adapter.checkedIcon);
			} else {
				checkBoxView.setImageDrawable(adapter.uncheckedIcon);
			}
		}
	}
}
