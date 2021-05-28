package ro.zero.zeronotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NotesFragment notesFragment = new NotesFragment();
		HabitsFragment habitsFragment = new HabitsFragment();
		CalendarFragment calendarFragment = new CalendarFragment();

		NavButton notesNavButton = findViewById(R.id.navButton_Notes);
		NavButton notesNavHabits = findViewById(R.id.navButton_Habits);
		NavButton notesNavCalendar = findViewById(R.id.navButton_Calendar);

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
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,calendarFragment).commit();
		});

		// Initialize Activity
		notesNavButton.select();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,notesFragment).commit();
	}
}