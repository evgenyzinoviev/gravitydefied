package org.happysanta.gd.Menu.Views;

import android.app.Activity;
import android.widget.RelativeLayout;

import static org.happysanta.gd.Helpers.logDebug;

public class MenuTitleLinearLayout extends RelativeLayout {

	private Callback onSizeChangedCallback = null;

	public MenuTitleLinearLayout(Activity activity) {
		super(activity);
	}

	@Override
	public void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		if (onSizeChangedCallback != null) {
			onSizeChangedCallback.run(w, h, oldw, oldh);
		}
	}

	/*public void forceInvokeOnSizeChangedCallback() {
		if (onSizeChangedCallback != null) {
			onSizeChangedCallback.run(getWidth(), getHeight(), 0, 0);
		}
	}*/

	public void setOnSizeChangedCallback(Callback callback) {
		onSizeChangedCallback = callback;
	}

	public interface Callback {
		public abstract void run(int w, int h, int oldw, int oldh);
	}

}
