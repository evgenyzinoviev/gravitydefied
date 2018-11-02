package org.happysanta.gd.Menu;

import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import org.happysanta.gd.GDActivity;
import org.happysanta.gd.Menu.Views.MenuLinearLayout;

import java.util.Vector;

import static org.happysanta.gd.Helpers.getDp;
import static org.happysanta.gd.Helpers.getGDActivity;
import static org.happysanta.gd.Helpers.isSDK11OrHigher;
import static org.happysanta.gd.Helpers.logDebug;

public class MenuScreen
		implements OnMenuElementHighlightListener {

	public static final int KEY_FIRE = 5;
	public static final int KEY_UP = 2;
	public static final int KEY_DOWN = 8;
	public static final int KEY_LEFT = 4;
	public static final int KEY_RIGHT = 6;

	protected static final int LAYOUT_LEFT_PADDING = 30;
	protected static final int LAYOUT_TOP_PADDING = 0;
	protected static final int LAYOUT_BOTTOM_PADDING = 15;

	protected MenuScreen navTarget;
	protected String title;
	protected int selectedIndex;
	protected Vector menuItems;
	protected MenuLinearLayout layout;
	protected ClickableMenuElement lastHighlighted;
	protected boolean isTextScreen = false;

	public MenuScreen(String title, MenuScreen navTarget) {
		this.title = title;
		selectedIndex = -1;
		menuItems = new Vector();
		this.navTarget = navTarget;

		Context context = getGDActivity();

		layout = new MenuLinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(getDp(LAYOUT_LEFT_PADDING), getDp(LAYOUT_TOP_PADDING), getDp(LAYOUT_LEFT_PADDING), getDp(LAYOUT_BOTTOM_PADDING));

		// Disable multi-touch in menu
		if (isSDK11OrHigher())
			layout.setMotionEventSplittingEnabled(false);
	}

	public void addItem(MenuElement item) {
		layout.addView(item.getView());
		menuItems.add(item);

		if (item instanceof ClickableMenuElement)
			((ClickableMenuElement) item).setOnHighlightListener(this);
	}

	protected void scrollToItem(MenuElement item) {
		// int y = item.getView().getTop();
		// logDebug("scrollTo: y = " + y);

		// getGameMenu().scrollTo(y);
		getGDActivity().scrollToView(item.getView());
	}

	public void performAction(int k) {
		// logDebug("MenuScreen.performAction: k = " + k);
		int from = 0;
		switch (k) {
			default:
				// logDebug("selectedIndex = " + selectedIndex);
				if (selectedIndex != -1) {
					for (int i = selectedIndex; i < menuItems.size(); i++) {
						MenuElement item;
						if ((item = (MenuElement) menuItems.elementAt(i)) != null && item.isSelectable()) {
							item.performAction(k);
							return;
						}
					}
				}
				break;

			case KEY_UP:
				if (isTextScreen) {
					getGDActivity().scrollTextMenuUp();
					return;
				}

				if (selectedIndex > 0 && !elementIsFirstClickable(selectedIndex)) {
					from = selectedIndex - 1;
				} else {
					from = menuItems.size() - 1;
				}

				for (int i = from; i >= 0; i--) {
					MenuElement el = (MenuElement) menuItems.elementAt(i);
					if (!(el instanceof ClickableMenuElement) || ((ClickableMenuElement) el).isDisabled()) {
						continue;
					}

					highlightElement((ClickableMenuElement) el);
					selectedIndex = i;
					scrollToItem(el);
					break;
				}
				break;

			case KEY_DOWN:
				if (isTextScreen) {
					getGDActivity().scrollTextMenuDown();
					return;
				}

				if (selectedIndex < menuItems.size() - 1) {
					from = selectedIndex + 1;
				} else {
					from = 0;
				}
				for (int i = from; i < menuItems.size(); i++) {
					MenuElement el = (MenuElement) menuItems.elementAt(i);
					if (!(el instanceof ClickableMenuElement) || ((ClickableMenuElement) el).isDisabled()) {
						continue;
					}

					highlightElement((ClickableMenuElement) el);
					selectedIndex = i;
					scrollToItem(el);
					break;
				}
				break;
		}
	}

	protected boolean elementIsFirstClickable(int index) {
		for (int i = 0; i < menuItems.size(); i++) {
			MenuElement el = (MenuElement) menuItems.elementAt(i);
			if (!(el instanceof ClickableMenuElement) || ((ClickableMenuElement) el).isDisabled()) {
				if (i == index) {
					return false;
				}
			} else {
				if (i < index) return false;
				if (i == index) return true;
			}
		}

		return false;
	}

	public MenuScreen getNavTarget() {
		return navTarget;
	}

	public void setNavTarget(MenuScreen target) {
		navTarget = target;
	}

	/*public void setIsLevelsList(boolean is) {
		isLevelsList = is;
	}*/

	public void clear() {
		menuItems.removeAllElements();
		layout.removeAllViews();

		selectedIndex = -1;
		lastHighlighted = null;
	}

	public LinearLayout getLayout() {
		return layout;
	}

	protected void setTitle(String s) {
		title = s;
	}

	protected void updateTitle() {
		final GDActivity gd = getGDActivity();
		gd.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				gd.menuTitleTextView.setText(title);
				// activity.menuTitleTextView.invalidate();
				gd.titleLayout.invalidate();
			}
		});
	}

	public void onHide(MenuScreen newMenu) {
	}

	public void onShow() {
		updateTitle();
		highlightElement();
	}

	public void resetHighlighted() {
		lastHighlighted = null;
	}

	public void highlightElement() {
		if (lastHighlighted != null) {
			lastHighlighted.showHelmet();
			final ViewTreeObserver obs = layout.getViewTreeObserver();
			obs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
				@Override
				public boolean onPreDraw() {
					try {
						obs.removeOnPreDrawListener(this);
						scrollToItem(lastHighlighted);
					} catch (Exception e) {
					}

					return true;
				}
			});
		} else {
			for (int i = 0; i < menuItems.size(); i++) {
				if (menuItems.elementAt(i) instanceof ClickableMenuElement) {
					ClickableMenuElement item = (ClickableMenuElement) menuItems.elementAt(i);
					if (item.isDisabled()) continue;

					highlightElement(item);
					scrollToItem(lastHighlighted);
					selectedIndex = i;

					break;
				}
			}
		}
	}

	public void setSelected(int index) {
		try {
			if (menuItems.elementAt(index) instanceof ClickableMenuElement) {
				ClickableMenuElement item = (ClickableMenuElement) menuItems.elementAt(index);
				if (item.isDisabled()) return;

				highlightElement(item);
				selectedIndex = index;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void highlightElement(ClickableMenuElement el) {
		el.showHelmet();
		lastHighlighted = el;
	}

	public void onScroll(double percent) {
	}

	@Override
	public void onElementHighlight(ClickableMenuElement el) {
		lastHighlighted = el;

		int index = menuItems.indexOf(el);
		if (index != -1)
			selectedIndex = index;
	}

	public void setIsTextScreen(boolean isTextScreen) {
		this.isTextScreen = isTextScreen;
	}

	/*public boolean isTextScreen() {
		return isTextScreen;
	}*/

}
