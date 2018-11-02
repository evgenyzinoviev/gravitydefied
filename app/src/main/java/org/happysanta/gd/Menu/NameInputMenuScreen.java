package org.happysanta.gd.Menu;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.happysanta.gd.Global;
import org.happysanta.gd.Menu.Views.MenuLinearLayout;
import org.happysanta.gd.Menu.Views.MenuTextView;

import static org.happysanta.gd.Helpers.getDp;
import static org.happysanta.gd.Helpers.getGDActivity;
import static org.happysanta.gd.Helpers.getGameMenu;

public class NameInputMenuScreen extends MenuScreen {

	protected static final String CURSOR = "^";
	protected static final int WORD_SPACE = 3;

	protected static int wordWidth = 0;

	protected int cursorPosition = 0;
	protected byte chars[];

	protected MenuTextView nameTextViews[];
	protected MenuTextView cursorTextViews[];
	protected MenuLinearLayout nameLayout;
	protected MenuLinearLayout cursorLayout;

	static {
		wordWidth = getWordWidth();
	}

	public NameInputMenuScreen(String title, MenuScreen navTarget, byte nameChars[]) {
		super(title, navTarget);

		chars = nameChars;

		Context context = getGDActivity();

		nameTextViews = new MenuTextView[3];
		cursorTextViews = new MenuTextView[3];

		nameLayout = new MenuLinearLayout(context);
		nameLayout.setOrientation(LinearLayout.HORIZONTAL);

		cursorLayout = new MenuLinearLayout(context);
		cursorLayout.setOrientation(LinearLayout.HORIZONTAL);

		for (int i = 0; i < 3; i++) {
			nameTextViews[i] = createTextView();
			nameLayout.addView(nameTextViews[i]);

			cursorTextViews[i] = createTextView();
			cursorLayout.addView(cursorTextViews[i]);
		}

		layout.addView(nameLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(cursorLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		updateText();
		updateCursorPosition();
	}

	protected MenuTextView createTextView() {
		Context context = getGDActivity();
		MenuTextView textView = new MenuTextView(context);
		textView.setTextColor(0xff000000);
		textView.setTypeface(Global.robotoCondensedTypeface);
		textView.setTextSize(ClickableMenuElement.TEXT_SIZE);
		textView.setLayoutParams(new LinearLayout.LayoutParams(
				wordWidth,
				ViewGroup.LayoutParams.WRAP_CONTENT
		));
		return textView;
	}

	protected static int getWordWidth() {
		Context context = getGDActivity();

		String text = "W";
		TextView textView = new TextView(context);
		textView.setTextSize(ClickableMenuElement.TEXT_SIZE);
		textView.setTypeface(Global.robotoCondensedTypeface);

		Rect bounds = new Rect();

		Paint textPaint = textView.getPaint();
		textPaint.getTextBounds(text, 0, text.length(), bounds);

		return bounds.width() + getDp(WORD_SPACE);
	}

	@Override
	public void performAction(int k) {
		switch (k) {
			default:
				break;

			case MenuScreen.KEY_FIRE: // select
				if (cursorPosition == 2) {
					getGameMenu().setCurrentMenu(navTarget, false);
				} else {
					cursorPosition++;
					updateCursorPosition();
				}
				break;

			case MenuScreen.KEY_RIGHT: // right
				cursorPosition++;
				if (cursorPosition > 2) {
					cursorPosition = 2;
				}
				updateCursorPosition();
				break;

			case MenuScreen.KEY_LEFT: // left
				cursorPosition--;
				if (cursorPosition < 0)
					cursorPosition = 0;
				updateCursorPosition();
				break;

			case MenuScreen.KEY_UP: // up
				if (chars[cursorPosition] == 32) {
					chars[cursorPosition] = 65;
					updateText();
					break;
				}
				chars[cursorPosition]++;
				if (chars[cursorPosition] > 90) {
					chars[cursorPosition] = 32;
				}
				updateText();
				break;

			case MenuScreen.KEY_DOWN: // down
				if (chars[cursorPosition] == 32) {
					chars[cursorPosition] = 90;
					updateText();
					break;
				}
				chars[cursorPosition]--;
				if (chars[cursorPosition] < 65) {
					chars[cursorPosition] = 32;
				}
				updateText();
				break;
		}
	}

	protected void updateText() {
		for (int i = 0; i < nameTextViews.length; i++) {
			nameTextViews[i].setTextOnUiThread(String.valueOf((char) chars[i]));
		}
	}

	protected void updateCursorPosition() {
		for (int i = 0; i < cursorTextViews.length; i++) {
			cursorTextViews[i].setTextOnUiThread(i == cursorPosition ? CURSOR : "");
		}
	}

	public byte[] getChars() {
		return chars;
	}

	public void resetCursorPosition() {
		cursorPosition = 0;
		updateCursorPosition();
	}

}
