package org.happysanta.gd.Menu.Views;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import static org.happysanta.gd.Helpers.runOnUiThread;

public class MenuLinearLayout extends LinearLayout {

	boolean interceptTouchEvents = false;

	public MenuLinearLayout(Context context) {
		super(context);
	}

	public MenuLinearLayout(Context context, boolean interceptTouchEvents) {
		super(context);
		this.interceptTouchEvents = interceptTouchEvents;
	}

	@Override
	public void removeAllViews() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuLinearLayout.super.removeAllViews();
			}
		});
	}

	@Override
	public void setVisibility(final int visibility) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuLinearLayout.super.setVisibility(visibility);
			}
		});
	}

	@Override
	public void addView(final View view) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuLinearLayout.super.addView(view);
			}
		});
	}

	@Override
	public void setPadding(final int left, final int top, final int right, final int bottom) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuLinearLayout.super.setPadding(left, top, right, bottom);
			}
		});
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent evt) {
		return interceptTouchEvents;
	}

}
