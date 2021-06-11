package ro.zero.zeronotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.NavButton;
import ro.zero.zeronotes.ui.UIResourceManager;
import ro.zero.zeronotes.ui.fragments.HabitsFragment;
import ro.zero.zeronotes.ui.fragments.MonthlyFragment;
import ro.zero.zeronotes.ui.fragments.NotesFragment;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize the singletons
		UIResourceManager.init(this);
		DataStorageManager.init(this);

		// Create teh fragments
		NotesFragment notesFragment = new NotesFragment();
		HabitsFragment habitsFragment = new HabitsFragment();
		MonthlyFragment monthlyFragment = new MonthlyFragment();

		// Create the nav buttons and set their interaction to be exclusive to each other.
		NavButton notesNavButton = findViewById(R.id.navButton_Notes);
		NavButton notesNavHabits = findViewById(R.id.navButton_Habits);
		NavButton notesNavCalendar = findViewById(R.id.navButton_Monthly);

		notesNavButton.setOnClickListener(v -> {
			notesNavButton.select();
			notesNavHabits.deselect();
			notesNavCalendar.deselect();
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,notesFragment).commit();
		});
		notesNavHabits.setOnClickListener(v -> {
			notesNavButton.deselect();
			notesNavHabits.select();
			notesNavCalendar.deselect();
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,habitsFragment).commit();
		});
		notesNavCalendar.setOnClickListener(v -> {
			notesNavButton.deselect();
			notesNavHabits.deselect();
			notesNavCalendar.select();
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, monthlyFragment).commit();
		});

		// Initialize Activity
		notesNavButton.select();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,notesFragment).commit();
	}
}