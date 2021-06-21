package ro.zero.zeronotes.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.SubTask;
import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.UIResourceManager;
import ro.zero.zeronotes.ui.listeners.implementations.OnNoteLongClickListenerImpl;

public class SubTaskRecyclerViewAdapter extends RecyclerView.Adapter<SubTaskRecyclerViewAdapter.ViewHolder> {

	private List<SubTask> subTaskList;

	private OnNoteLongClickListenerImpl noteLongClickListener;

	public SubTaskRecyclerViewAdapter() {
		this.subTaskList = new ArrayList<>();
	}
	public SubTaskRecyclerViewAdapter(List<SubTask> subTaskList) {
		this.subTaskList = subTaskList;
	}

	public void setSubTaskList(List<SubTask> subTaskList) {
		this.subTaskList = subTaskList;
	}

	public void setNoteLongClickListener(OnNoteLongClickListenerImpl listener) {
		noteLongClickListener = listener;
	}
	// When the view holders are created, this function is called.
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.component_subtask, viewGroup, false);

		return new ViewHolder(view, noteLongClickListener);
	}

	// When the view holder needs new data, this function is called.
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		viewHolder.setSubTask(subTaskList.get(position));
		viewHolder.setText(subTaskList.get(position).noteText);
		viewHolder.setCheckboxStatus(subTaskList.get(position).finished);
		viewHolder.updateSubTasks();
	}

	// Return the size of the dataset. We return one more to have space for the "add note" button.
	@Override
	public int getItemCount() {
		return subTaskList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final TextView subTaskTextView;
		private final ImageView checkBoxView;
		private final SubTaskRecyclerViewAdapter subTaskAdapter;
		private SubTask subTask = null;

		public ViewHolder(View view, OnNoteLongClickListenerImpl listener) {
			super(view);

			subTaskTextView = view.findViewById(R.id.subTask_text);
			checkBoxView = view.findViewById(R.id.subTask_checkBox);
			subTaskAdapter = new SubTaskRecyclerViewAdapter();
			subTaskAdapter.setNoteLongClickListener(listener);

			RecyclerView subTaskRecycler = view.findViewById(R.id.subtaskRecycler);
			subTaskRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
			subTaskRecycler.setAdapter(subTaskAdapter);

			checkBoxView.setOnClickListener(v -> {
				if(subTask != null) {
					subTask.finished = !subTask.finished;
					setCheckboxStatus(subTask.finished);

					// TODO: perhaps cache these changes and mass-commit them?
					DataStorageManager.getInstance().save();
				}
			});

			// TODO: edit this
			if(listener != null) {
				view.setOnLongClickListener(v -> {
					listener.clickedNote = subTask;
					return listener.onLongClick(v);
				});
			}
		}

		public void setText(String str) {
			subTaskTextView.setText(str);
		}
		public void setCheckboxStatus(boolean checked) {
			if(checked) {
				checkBoxView.setImageDrawable(UIResourceManager.getInstance().note_checkedIcon);
				subTaskTextView.setBackgroundResource(R.color.grey_25);
			} else {
				checkBoxView.setImageDrawable(UIResourceManager.getInstance().note_uncheckedIcon);
				subTaskTextView.setBackgroundResource(R.color.grey_40);
			}
		}
		public void setSubTask(SubTask subTask) {
			this.subTask = subTask;
		}
		public void updateSubTasks() {
			if(subTask != null) {
				subTaskAdapter.setSubTaskList(subTask.subTasks);
				subTaskAdapter.notifyDataSetChanged();
			}
		}
	}
}
