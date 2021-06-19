package ro.zero.zeronotes.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import ro.zero.zeronotes.R;
import ro.zero.zeronotes.notes.Habit;
import ro.zero.zeronotes.notes.Note;
import ro.zero.zeronotes.notes.NoteType;
import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.adapters.HabitRecyclerViewAdapter;
import ro.zero.zeronotes.ui.adapters.NoteRecyclerViewAdapter;
import ro.zero.zeronotes.ui.listeners.implementations.OnAddNoteButtonClickImpl;
import ro.zero.zeronotes.ui.listeners.implementations.OnNoteLongClickListenerImpl;
import ro.zero.zeronotes.ui.popups.SelectDatePopup;

public class HabitsFragment extends Fragment {
	private RecyclerView recyclerView;

	public HabitsFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_habits, container, false);

		// Recycler View
		// ===============
		recyclerView = view.findViewById(R.id.habitRecycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

		updateRecyclerView(inflater);

		return view;
	}

	private void updateRecyclerView(LayoutInflater inflater) {
		if (recyclerView == null) return;

		ArrayList<Habit> habits = DataStorageManager.getInstance().saveData.habits;
		HabitRecyclerViewAdapter adapter = new HabitRecyclerViewAdapter(habits);

		adapter.setOnAddButtonClickListener(new OnAddNoteButtonClickImpl(adapter, inflater, LocalDate.now(), NoteType.HABIT));
		adapter.setNoteLongClickListener(new OnNoteLongClickListenerImpl(adapter, inflater));

		recyclerView.setAdapter(adapter);
	}
}