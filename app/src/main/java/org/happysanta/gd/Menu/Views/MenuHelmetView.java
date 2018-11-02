package org.happysanta.gd.Menu.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import org.happysanta.gd.Game.Bitmap;
import org.happysanta.gd.Global;

import static org.happysanta.gd.Helpers.getDp;
import static org.happysanta.gd.Helpers.isSDK11OrHigher;
import static org.happysanta.gd.Helpers.logDebug;

public class MenuHelmetView extends View {

	protected static final int WIDTH = 8;
	protected static final int HEIGHT = 8;
	/*protected static final int PADDING_LEFT = 0;
	protected static final int PADDING_TOP = 5;
	protected static final int PADDING_RIGHT = 5;
	protected static final int PADDING_BOTTOM = 0;*/

	protected static int angle = 0;
	protected static long angleLastMs = 0;
	protected static final int angleInterval = 50;
	protected static final int angleDelta = 10;

	protected boolean show = false;
	protected boolean _setMeasuredHeight = false;
	protected Bitmap helmet = Bitmap.get(Bitmap.HELMET);
	protected static MenuHelmetView lastActive = null;

	public static void clearStaticFields() {
		lastActive = null;
		angle = 0;
		angleLastMs = 0;
	}

	public MenuHelmetView(Context context) {
		super(context);
	}

	public MenuHelmetView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.save();
		canvas.scale(Global.density, Global.density);

		drawHelmet(canvas);

		canvas.restore();
		invalidate();
	}

	protected void drawHelmet(Canvas canvas) {
		if (show) {
			long ms = System.currentTimeMillis();
			if (angleLastMs == 0 || ms - angleLastMs >= angleInterval) {
				angle += angleDelta;
				if (angle >= 360) angle -= 360;
				angleLastMs = ms;
			}

			int y = getScaledHeight() / 2 - helmet.getHeightDp() / 2;

			canvas.save();
			canvas.rotate(angle, helmet.getWidthDp() / 2, y + helmet.getHeightDp() / 2);
			canvas.drawBitmap(helmet.bitmap, new Rect(0, 0, helmet.getWidth(), helmet.getHeight()), new RectF(0, y, helmet.getWidthDp(), y + helmet.getHeightDp()), null);
			canvas.restore();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(getDp(WIDTH * 2.2f));
		int height = heightMeasureSpec;
		if (_setMeasuredHeight)
			height = MeasureSpec.getSize(getDp(HEIGHT * 2.2f));
		else if (!isSDK11OrHigher()) {
			height = MeasureSpec.getSize(getDp(HEIGHT * 4.5f));
		}
		setMeasuredDimension(width, height);
	}

	public void setShow(boolean show) {
		setShow(show, true);
	}

	public void setShow(boolean show, boolean checkLast) {
		if (checkLast && lastActive != null) {
			lastActive.setShow(false, false);
		}
		this.show = show;
		lastActive = this;
	}

	protected int getScaledHeight() {
		return Math.round(getHeight() / Global.density);
	}

	public void setMeasuredHeight(boolean setMeasuredHeight) {
		this._setMeasuredHeight = setMeasuredHeight;
	}

}
