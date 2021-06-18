package ro.zero.zeronotes.ui.listeners.implementations;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import ro.zero.zeronotes.notes.INote;

public class OnNoteLongClickListenerImpl implements View.OnLongClickListener {
	public INote clickedNote;
	public LayoutInflater inflater;

	public OnNoteLongClickListenerImpl(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	@Override
	public boolean onLongClick(View v) {

		return false;
	}
}
