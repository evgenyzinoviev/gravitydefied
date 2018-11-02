package org.happysanta.gd.Menu;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import org.happysanta.gd.Global;
import org.happysanta.gd.Menu.Views.MenuTextView;

import java.util.TimerTask;

import static org.happysanta.gd.Helpers.getGDActivity;
import static org.happysanta.gd.Helpers.getGDView;

public class SimpleMenuElement extends TimerTask
		implements MenuElementOld {

	// public static final int LINE_SPACING = 15;
	protected static Paint gFont;

	public int x;
	public int y;
	public int m_bI;
	public int m_eI;
	public int m_dI;
	public int m_gotoI;
	public int m_nullI;
	public int m_longI;
	public int m_fI;
	int m_cI;
	// GDActivity activity;
	protected String text;
	protected MenuScreen m_we;
	protected MenuHandler m_hc;
	protected Paint font;
	protected boolean isPressed = false;
	protected MenuTextView textView;

	/*static {
		gFont = ActionMenuElement.getGFont();
	}*/

	public SimpleMenuElement() {
		init();
	}

	public SimpleMenuElement(int k) {
		m_cI = k;
		font = gFont;
	}

	public SimpleMenuElement(String s, MenuScreen e1, MenuHandler c1) {
		text = s + ">";
		m_we = e1;
		m_hc = c1;
		font = gFont;

		textView = new MenuTextView(getGDActivity());
		textView.setText(text);
		textView.setTextColor(0xff000000);
		// textView.setTextColor(R.drawable.menu_item_color);
		textView.setTypeface(Global.robotoCondensedTypeface);
		textView.setTextSize(20);
		textView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
		));
	}

	public void init() {
		x = y = m_bI = 0;
		m_eI = m_dI = m_gotoI = 0;
		m_nullI = m_longI = m_fI = 0;
	}

	@Override
	public View getView() {
		return textView;
	}

	@Override
	public void run() {
		getGDView()._tryIV(m_cI);
	}

	@Override
	public void setText(String s) {
		text = s + ">";
	}

	public String getText() {
		return text;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	@Override
	public void performAction(int k) {
		/*switch (k) {
			case MenuScreen.KEY_FIRE:
			case MenuScreen.KEY_RIGHT:
				m_hc.handleAction(this);
				m_we.setNavTarget(m_hc.getCurrentMenu());
				m_hc.setCurrentMenu(m_we, false);
				// fall through

			case 3: // '\003'
			default:
				return;
		}*/
	}

	/* public void _aeV(MenuScreen e1) {
		screen = e1;
	} */

	@Override
	public void draw(Canvas g, int y, int x) {
		// if (isPressed)
		// setPressedColor();
		g.drawText(text, x, y - font.ascent(), font);
		// if (isPressed)
		// setNormalColor();
	}

	@Override
	public int getLineSpacing() {
		return ActionMenuElement.LINE_SPACING;
	}

	@Override
	public void setFont(Paint font) {
		this.font = font;
	}

	@Override
	public int getHeight() {
		return Math.round(font.descent() - font.ascent());
	}

	@Override
	public int getFirstLineHeight() {
		return getHeight();
	}

	/* @Override
	public int getHeight() {
		return getHeight() + getLineSpacing();
	} */

	@Override
	public int getXOffset() {
		return ActionMenuElement.X_OFFSET;
	}

	@Override
	public void setPressed(boolean flag) {
		isPressed = flag;
	}

	/*protected void setPressedColor() {
		font.setColor(ActionMenuElement.PRESSED_COLOR);
	}

	protected void setNormalColor() {
		font.setColor(ActionMenuElement.NORMAL_COLOR);
	}*/

}
