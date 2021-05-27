package ro.zero.zeronotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NavButton notesNavButton = findViewById(R.id.navButton_Notes);
		notesNavButton.select();
		NavButton notesNavHabits = findViewById(R.id.navButton_Habits);
		NavButton notesNavCalendar = findViewById(R.id.navButton_Calendar);

		notesNavButton.setOnClickListener(v -> {
			notesNavButton.select();
			notesNavHabits.deselect();
			notesNavCalendar.deselect();
		});
		notesNavHabits.setOnClickListener(v -> {
			notesNavButton.deselect();
			notesNavHabits.select();
			notesNavCalendar.deselect();
		});
		notesNavCalendar.setOnClickListener(v -> {
			notesNavButton.deselect();
			notesNavHabits.deselect();
			notesNavCalendar.select();
		});
	}
}