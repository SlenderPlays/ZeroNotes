package ro.zero.zeronotes.notes;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.UUID;

public class Note {
	public UUID id;
	public String noteText;
	public boolean finished = false;
	public Date date;

	public Note() {
		id = UUID.randomUUID();
		date = new Date(System.currentTimeMillis());
	}

	public Note withText(String str) {
		noteText = str;
		return this;
	}
	public Note withFinishedStatus(boolean finished) {
		this.finished = finished;
		return this;
	}
	public Note withDate(Date date) {
		this.date = date;
		return this;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if(obj == null) return false;
		if(obj.getClass().equals(this.getClass())) return false;
		return this.id == ((Note) obj).id;
	}
}
