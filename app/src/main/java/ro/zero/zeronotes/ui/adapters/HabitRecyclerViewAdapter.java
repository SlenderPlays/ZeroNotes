package ro.zero.zeronotes.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Habit;
import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.UIResourceManager;
import ro.zero.zeronotes.ui.listeners.implementations.OnNoteLongClickListenerImpl;

public class HabitRecyclerViewAdapter extends RecyclerView.Adapter<HabitRecyclerViewAdapter.ViewHolder> {
	private static final int ITEM_TYPE = 0;
	private static final int ADD_NOTE_TYPE = 1;

	private final List<Habit> habitList;

	private View.OnClickListener addNoteButtonClickListener;
	private OnNoteLongClickListenerImpl noteLongClickListener;
	
	public HabitRecyclerViewAdapter(List<Habit> habitList) {
		this.habitList = habitList;
	}

	public void setOnAddButtonClickListener(View.OnClickListener listener) {
		addNoteButtonClickListener = listener;
	}
	public void setNoteLongClickListener(OnNoteLongClickListenerImpl listener) {
		noteLongClickListener = listener;
	}

	// When the view holders are created, this function is called.
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		if(viewType == ITEM_TYPE) {
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.component_habit, viewGroup, false);

			return new ItemViewHolder(view,noteLongClickListener);
		} else if(viewType == ADD_NOTE_TYPE) {
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.component_add_note, viewGroup, false);

			return new AddNoteViewHolder(view,addNoteButtonClickListener);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		int type = getItemViewType(position);
		if (type == ITEM_TYPE) {
			ItemViewHolder itemHolder = (ItemViewHolder) viewHolder;

			itemHolder.setHabit(habitList.get(position));
			itemHolder.setText(habitList.get(position).noteText);
			itemHolder.setCheckboxStatus(habitList.get(position).isFinished());
			itemHolder.updateStreak();
			itemHolder.updateSubTasks();
		} else if (type == ADD_NOTE_TYPE) {
			// ... we don't need to bind any data to it since it's a static element, with no data.
		}
	}

	// Return the size of the dataset. We return one more to have space for the "add note" button.
	@Override
	public int getItemCount() {
		return habitList.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if(position < habitList.size()) return ITEM_TYPE;
		else return ADD_NOTE_TYPE;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View view) {
			super(view);
		}
	}
	public static class AddNoteViewHolder extends ViewHolder {
		ImageView addNoteImage;

		public AddNoteViewHolder(View view, View.OnClickListener listener) {
			super(view);

			addNoteImage = view.findViewById(R.id.add_note_button);
			if(listener != null) {
				addNoteImage.setOnClickListener(listener);
			}
		}
	}
	public static class ItemViewHolder extends ViewHolder {
		private final ConstraintLayout noteContainer;
		private final ConstraintLayout streakContainer;
		private final TextView streakTextView;
		private final TextView noteTextView;
		private final ImageView checkBoxView;
		private final SubTaskRecyclerViewAdapter subTaskAdapter;
		private Habit habit = null;

		public ItemViewHolder(View view, OnNoteLongClickListenerImpl listener) {
			super(view);

			noteContainer = view.findViewById(R.id.note_content_container);
			noteTextView = view.findViewById(R.id.note_content);
			checkBoxView = view.findViewById(R.id.note_checkBox);

			streakContainer = view.findViewById(R.id.streak_container);
			streakTextView = view.findViewById(R.id.streak_text);

			subTaskAdapter = new SubTaskRecyclerViewAdapter();
			subTaskAdapter.setNoteLongClickListener(listener);

			RecyclerView subTaskRecycler = view.findViewById(R.id.subtaskRecycler);
			subTaskRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
			subTaskRecycler.setAdapter(subTaskAdapter);

			checkBoxView.setOnClickListener(v -> {
				if(habit != null) {
					habit.setFinished(!habit.isFinished());

					setCheckboxStatus(habit.isFinished());
					updateStreak();

					// TODO: perhaps cache these changes and mass-commit them?
					DataStorageManager.getInstance().save();
				}
			});

			if(listener != null) {
				view.setOnLongClickListener(v -> {
					listener.clickedNote = habit;
					return listener.onLongClick(v);
				});
			}
		}

		public void updateStreak() {
			if(habit.streak == 0) {
				streakContainer.setVisibility(View.INVISIBLE);
			} else {
				streakContainer.setVisibility(View.VISIBLE);

				String text = "x"+String.valueOf(habit.streak);
				streakTextView.setText(text);
			}
		}
		public void setText(String str) {
			noteTextView.setText(str);
		}
		public void setCheckboxStatus(boolean checked) {
			if(checked) {
				checkBoxView.setImageDrawable(UIResourceManager.getInstance().note_checkedIcon);
				noteContainer.setBackgroundResource(R.color.note_finished);
				noteTextView.setTextColor(UIResourceManager.getInstance().note_textColor_finished);
			} else {
				checkBoxView.setImageDrawable(UIResourceManager.getInstance().note_uncheckedIcon);
				noteContainer.setBackgroundResource(R.color.note_default);
				noteTextView.setTextColor(UIResourceManager.getInstance().note_textColor_default);
			}
		}
		public void setHabit(Habit habit) {
			this.habit = habit;
		}
		public void updateSubTasks() {
			if(habit != null) {
				subTaskAdapter.setSubTaskList(habit.subTasks);
				subTaskAdapter.notifyDataSetChanged();
			}
		}
	}
}
