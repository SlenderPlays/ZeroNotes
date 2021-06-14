package ro.zero.zeronotes.ui.popups;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import ro.zero.zeronotes.R;

public class SelectDatePopup {

	private LocalDate selectedDate;
	private boolean cancelled = true;

	private List<OnPopupDismissListener> listeners = new ArrayList<OnPopupDismissListener>();

	public void showPopupWindow(LayoutInflater inflater, LocalDate currentDate) {
		// Create View
		View view = inflater.inflate(R.layout.popup_select_date, null);
		CalendarView calendarView = view.findViewById(R.id.popup_select_date_calendar);
		Button selectButton = view.findViewById(R.id.popup_select_date_select_button);

		calendarView.setDate(
				currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
		);
		// Create Popup Window
		final PopupWindow popupWindow = new PopupWindow(
				view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				true
		);

		// Set up view interactions
		view.setOnClickListener(v -> {
			popupWindow.dismiss();
		});

		calendarView.setOnDateChangeListener((v, year, month, dayOfMonth) -> {
			selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
		});

		selectButton.setOnClickListener(v -> {
			cancelled = false;
			popupWindow.dismiss();
		});
		// On popup dismiss

		popupWindow.setOnDismissListener(() -> {
			for(OnPopupDismissListener listener : listeners) {
				listener.onDismiss();
			}
		});

		// Show Popup
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}

	public void setOnPopupDismissListener(OnPopupDismissListener listener) {
		this.listeners.add(listener);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public LocalDate getSelectedDate() {
		return selectedDate;
	}
}