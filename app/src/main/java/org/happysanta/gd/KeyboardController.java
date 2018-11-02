package org.happysanta.gd;

import android.graphics.Rect;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import org.happysanta.gd.Game.GameView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.happysanta.gd.Helpers.getDp;

public class KeyboardController implements View.OnTouchListener {

	private static final int MAX_POINTERS = 10;
	public static final int PADDING = 15;
	private static final boolean DISABLE_MOVE = false;

	private static int PADDING_DP = 0;

	private GDActivity gd;
	private int[] buf;
	private LinearLayout[] btns;
	private PointerInfo[] pointers;
	private StringBuffer logBuffer;

	static {
		PADDING_DP = getDp(PADDING);
	}

	KeyboardController(GDActivity gd) {
		this.gd = gd;
		buf = new int[2];
		btns = new LinearLayout[9];
		pointers = new PointerInfo[MAX_POINTERS];
		for (int i = 0; i < MAX_POINTERS; i++) {
			pointers[i] = new PointerInfo(i);
		}

		logBuffer = new StringBuffer();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		GameView gameView = gd.gameView;

		v.getLocationOnScreen(buf);
		Rect rect = new Rect(buf[0], buf[1], buf[0] + v.getWidth(), buf[1] + v.getHeight());

		rect.left += PADDING_DP;
		rect.right -= PADDING_DP;
		rect.top += PADDING_DP;
		rect.bottom -= PADDING_DP;

		/*int actionRaw = MotionEventCompat.getAction/event.getAction();
		if (actionRaw != MotionEvent.ACTION_MOVE) {
			log("EVT_RAW " + actionRaw + " " + actionToString(actionRaw));
		}*/

		// int action = actionRaw & MotionEvent.ACTION_MASK;
		int action = event.getActionMasked();
		if (action == MotionEvent.ACTION_DOWN
				|| action == MotionEvent.ACTION_POINTER_DOWN
				|| action == MotionEvent.ACTION_UP
				|| action == MotionEvent.ACTION_POINTER_UP
//				|| action == MotionEvent.ACTION_CANCEL
				/*|| action == MotionEvent.ACTION_POINTER_2_DOWN
				|| action == MotionEvent.ACTION_POINTER_3_DOWN
				|| action == MotionEvent.ACTION_POINTER_2_UP
				|| action == MotionEvent.ACTION_POINTER_3_UP*/) {
			/*int index = event.getActionIndex();
			int pointerId = event.getPointerId(index);*/

			int index = event.getActionIndex();
			int pointerId = event.getPointerId(index);
//			int pointerId = event.getPointerId(index);

			if (pointerId >= MAX_POINTERS) {
				return true;
			}

			int x = Math.round(event.getX(index));
			int y = Math.round(event.getY(index));

			//if (Global.DEBUG || true) {
//				log("EVT [ind=" + index + ", pntId=" + pointerId + "] " + actionToString(action) + " "
//						+ "(x=" + x + ", y=" + y + "), "
//						+ "(rx=" + event.getRawX() + ", ry=" + event.getRawY() + ")"
//				);
//				log("EVT DUMP " + event.toString());
//				log("RECT l="+rect.left+", r="+rect.right+", t="+rect.top+", b="+rect.bottom);
			// }

			LinearLayout btn;
			PointerInfo pointer = pointers[pointerId];

			int btnIndex = whichButton(rect, x, y);

//			log("BTN index="+btnIndex, true);

			// logBuffer("x="+x+", y="+y+", btn="+btnIndex);

			switch (action) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					press(v);

					pointer.setButtonIndex(btnIndex);
					btn = pointer.getButton();

					btn.setPressed(true);
					gameView.keyPressed(gameKeyCode(btnIndex));
					break;

				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					// case MotionEvent.ACTION_CANCEL:
					btn = pointer.getButton();
					if (btn != null) {
						btn.setPressed(false);
						if (DISABLE_MOVE) {
							btnIndex = pointer.btnIndex;
						}
						gameView.keyReleased(gameKeyCode(btnIndex));
						pointer.finish();
					}
					break;
			}
		} else if (action == MotionEvent.ACTION_MOVE && !gd.isMenuShown() && !DISABLE_MOVE) {
			int pointerCount = event.getPointerCount();
//			int pointerCount = event.getPointerCount();
			LinearLayout btn, oldBtn;
			PointerInfo pointer;

			for (int pointerIndex = 0; pointerIndex < pointerCount; pointerIndex++) {
				int pointerId = event.getPointerId(pointerIndex);
				if (pointerId >= MAX_POINTERS) continue;

				int x = Math.round(event.getX(pointerIndex));
				int y = Math.round(event.getY(pointerIndex));

//				int x = Math.round(event.getX(pointerIndex));
//				int y = Math.round(event.getY(pointerIndex));
				int btnIndex = whichButton(rect, x, y);

				pointer = pointers[pointerId];
				if (btnIndex != pointer.btnIndex) {
					oldBtn = btns[pointer.btnIndex];
					oldBtn.setPressed(false);
					gameView.keyReleased(gameKeyCode(pointer.btnIndex));

					press(v);

					pointer.setButtonIndex(btnIndex);
					btn = pointer.getButton();
					btn.setPressed(true);
					gameView.keyPressed(gameKeyCode(pointer.btnIndex));
				}
			}
		}

		return true;
	}

	private synchronized void log(Object o, boolean last) {
		String logStr = o.toString();
		Log.d("GD Keyboard", o.toString());

		if (last)
			Log.d("", "");

		/*if (!gd.isMenuShown()) {
			logBuffer.append("<");
			logBuffer.append(getCurrentTime());
			logBuffer.append("> ");

			logBuffer.append(logStr);
			logBuffer.append("\n");

			if (last)
				logBuffer.append("\n");
		}*/
	}

	private void log(Object o) {
		log(o, false);
	}

	public synchronized void clearLogBuffer() {
		logBuffer = null;
		logBuffer = new StringBuffer();
	}

	public String getLog() {
		return logBuffer.toString();
	}

	private static String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	private static String actionToString(int action) {
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				return "ACTION_DOWN";
			case MotionEvent.ACTION_POINTER_DOWN:
				return "ACTION_POINTER_DOWN";
			case MotionEvent.ACTION_POINTER_2_DOWN:
				return "ACTION_POINTER_2_DOWN";
			case MotionEvent.ACTION_POINTER_2_UP:
				return "ACTION_POINTER_2_UP";
			case MotionEvent.ACTION_POINTER_3_DOWN:
				return "ACTION_POINTER_3_DOWN";
			case MotionEvent.ACTION_POINTER_3_UP:
				return "ACTION_POINTER_3_UP";
			case MotionEvent.ACTION_UP:
				return "ACTION_UP";
			case MotionEvent.ACTION_POINTER_UP:
				return "ACTION_POINTER_UP";
			case MotionEvent.ACTION_MOVE:
				return "ACTION_MOVE";
		}
		return "?";
	}

	private static int whichButton(Rect rect, int x, int y) {
		int cellW = rect.width() / 3,
				cellH = rect.height() / 3;

		// logBuffer("cellW="+cellW+", cellH="+cellH);

		int posX = 0, posY = 0;

		if (x < PADDING_DP + cellW)
			posX = 0;
		else if (x < PADDING_DP + cellW * 2)
			posX = 1;
		else
			posX = 2;

		if (y < PADDING_DP + cellH)
			posY = 0;
		else if (y < PADDING_DP + cellH * 2)
			posY = 1;
		else
			posY = 2;

		return posY * 3 + posX;
	}

	private static int gameKeyCode(int btnIndex) {
		return btnIndex + 49;
	}

	private static void press(View v) {
		if (Settings.isVibrateOnTouchEnabled()) {
			v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
		}
	}

	public void addButton(LinearLayout btn, int x, int y) {
		btns[y * 3 + x] = btn;
	}

	private class PointerInfo {

		private int id;
		private int btnIndex = -1;
		private boolean active = false;

		PointerInfo(int id) {
			this.id = id;
		}

		void finish() {
			active = false;
			btnIndex = -1;
		}

		/*void setActive() {
			active = true;
		}*/

		void setButtonIndex(int index) {
			active = true;
			btnIndex = index;
		}

		LinearLayout getButton() {
			if (!active)
				return null;
			return btns[btnIndex];
		}

	}

}
