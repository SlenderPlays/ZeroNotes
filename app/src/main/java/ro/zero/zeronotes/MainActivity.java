package ro.zero.zeronotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ro.zero.zeronotes.storage.DataStorageManager;
import ro.zero.zeronotes.ui.NavButton;
import ro.zero.zeronotes.ui.UIResourceManager;
import ro.zero.zeronotes.ui.fragments.HabitsFragment;
import ro.zero.zeronotes.ui.fragments.ProjectsFragment;
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
		ProjectsFragment projectsFragment = new ProjectsFragment();

		// Create the nav buttons and set their interaction to be exclusive to each other.
		NavButton notesNavButton = findViewById(R.id.navButton_Notes);
		NavButton notesNavHabits = findViewById(R.id.navButton_Habits);
		NavButton notesNavProjects = findViewById(R.id.navButton_Project);

		notesNavButton.setOnClickListener(v -> {
			notesNavButton.select();
			notesNavHabits.deselect();
			notesNavProjects.deselect();
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,notesFragment).commit();
		});
		notesNavHabits.setOnClickListener(v -> {
			notesNavButton.deselect();
			notesNavHabits.select();
			notesNavProjects.deselect();
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,habitsFragment).commit();
		});
		notesNavProjects.setOnClickListener(v -> {
			notesNavButton.deselect();
			notesNavHabits.deselect();
			notesNavProjects.select();
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, projectsFragment).commit();
		});

		// Initialize Activity
		notesNavButton.select();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,notesFragment).commit();
	}
}