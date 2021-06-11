package ro.zero.zeronotes.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import ro.zero.zeronotes.R;

/**
 * This class is used to house all of the UI resources and data that we might need when we don't have access to a context, and want to use the
 * main activitie's context.
 */
public class UIResourceManager {
	// ============================
	// Static variables and methods
	// ============================
	private static UIResourceManager _instance = null;

	public static UIResourceManager getInstance() {
		if(_instance == null) {
			throw new NullPointerException("UIResourceManager is null. You should first instantiate it with UIResourceManager.init(...) inside the main Activity.");
		} else {
			return _instance;
		}
	}
	public static void init(Context context) {
		_instance = new UIResourceManager(context);
	}
	// ============================
	// Resources
	// ============================
	Drawable note_uncheckedIcon = null;
	Drawable note_checkedIcon = null;
	int note_textColor_default = 0;
	int note_textColor_finished = 0;

	private UIResourceManager(Context context) {
		note_uncheckedIcon = ResourcesCompat.getDrawable(context.getResources(), R.drawable.checkbox_unchecked,null);
		note_checkedIcon = ResourcesCompat.getDrawable(context.getResources(),R.drawable.checkbox_checked,null);
		note_textColor_default = ResourcesCompat.getColor(context.getResources(),R.color.note_text_default,null);
		note_textColor_finished = ResourcesCompat.getColor(context.getResources(),R.color.note_text_finished,null);
	}
}
