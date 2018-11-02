package org.happysanta.gd.Menu.Views;

import android.content.Context;
import android.widget.ScrollView;

public class ObservableScrollView
		extends ScrollView {

	private OnScrollListener scrollListener = null;

	public ObservableScrollView(Context context) {
		super(context);
	}

	public void setOnScrollListener(OnScrollListener scrollListener) {
		this.scrollListener = scrollListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollListener != null) {
			scrollListener.onScroll(this, x, y, oldx, oldy);
		}
	}

	public interface OnScrollListener {
		public abstract void onScroll(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
	}

}
