package org.happysanta.gd.Menu.Views;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import static org.happysanta.gd.Helpers.runOnUiThread;

public class MenuRelativeLayout extends RelativeLayout {

	public MenuRelativeLayout(Context context) {
		super(context);
	}

	@Override
	public void removeAllViews() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuRelativeLayout.super.removeAllViews();
			}
		});
	}

	@Override
	public void setVisibility(final int visibility) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuRelativeLayout.super.setVisibility(visibility);
			}
		});
	}

	@Override
	public void addView(final View view) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuRelativeLayout.super.addView(view);
			}
		});
	}

	@Override
	public void setPadding(final int left, final int top, final int right, final int bottom) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuRelativeLayout.super.setPadding(left, top, right, bottom);
			}
		});
	}

}
