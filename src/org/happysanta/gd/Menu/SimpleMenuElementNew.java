package org.happysanta.gd.Menu;

import static org.happysanta.gd.Helpers.logDebug;

public class SimpleMenuElementNew extends ClickableMenuElement
		implements MenuElement {

	// protected static Paint gFont;

	public int x;
	public int y;
	/* public int m_bI;
	public int m_eI;
	public int m_dI;
	public int m_gotoI;
	public int m_nullI;
	public int m_longI;
	public int m_fI;
	int m_cI; */
	protected MenuScreen screen;
	protected MenuHandler handler;
	// protected Paint font;
	// protected boolean isPressed = false;

	/* static {
		gFont = ActionMenuElement.getGFont();
	} */

	/* public SimpleMenuElementNew() {
		init();
	}

	public SimpleMenuElementNew(int k) {
		m_cI = k;
		// font = gFont;
	} */

	public SimpleMenuElementNew(String text, MenuScreen screen, MenuHandler handler) {
		this.text = text + ">";
		this.screen = screen;
		this.handler = handler;

		createAllViews();

		// textView = createAllViews();
	}

	/* public void init() {
		x = y = m_bI = 0;
		m_eI = m_dI = m_gotoI = 0;
		m_nullI = m_longI = m_fI = 0;
	} */

	@Override
	public void setText(String s) {
		super.setText(s + ">");
	}

	// @Override
	public void performAction(int k) {
		logDebug("SimpleMenuElementNew performAction k = " + k);

		switch (k) {
			case MenuScreen.KEY_FIRE:
			case MenuScreen.KEY_RIGHT:
				handler.handleAction(this);
				screen.setNavTarget(handler.getCurrentMenu());
				handler.setCurrentMenu(screen, false);
				break;
		}
	}

	/* public void _aeV(MenuScreen e1) {
		screen = e1;
	} */

}
