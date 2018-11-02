package org.happysanta.gd.Menu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import org.happysanta.gd.Global;
import org.happysanta.gd.Menu.Views.MenuImageView;
import org.happysanta.gd.Menu.Views.MenuTextView;
import org.happysanta.gd.R;
// import com.grishka.agdtr.R;

import static org.happysanta.gd.Helpers.getDp;
import static org.happysanta.gd.Helpers.getGDActivity;
import static org.happysanta.gd.Helpers.getString;
import static org.happysanta.gd.Helpers.logDebug;

public class OptionsMenuElement
		extends ClickableMenuElement
		implements MenuElement, MenuHandler {

	protected int selectedIndex;
	protected String options[];
	protected int unlockedCount;
	protected MenuHandler handler;
	protected MenuScreen optionsScreen = null;
	protected MenuScreen screen = null;
	protected boolean isOnOffToggle;
	protected boolean m_oZ = false;
	protected String selectedOption;
	protected ActionMenuElement optionsScreenItems[] = null;
	protected MenuImageView lockImage = null;
	protected MenuTextView optionTextView = null;

	public OptionsMenuElement(String text, int selectedIndex, MenuHandler handler, String options[], boolean isOnOffToggle, MenuScreen screen) {
		this.text = text;
		this.selectedIndex = selectedIndex;
		this.handler = handler;
		this.options = options;
		if (this.options == null) this.options = new String[]{""};
		unlockedCount = this.options.length - 1;
		this.isOnOffToggle = isOnOffToggle;

		createAllViews();
		setSelectedOption(selectedIndex);

		if (isOnOffToggle) {
			if (selectedIndex == 1) {
				selectedOption = getString(R.string.off);
			} else {
				selectedOption = getString(R.string.on);
			}
		} else {
			this.screen = screen;
			updateSelectedOption();
			update();
		}
	}

	@Override
	protected void createAllViews() {
		Context context = getGDActivity();

		super.createAllViews();

		textView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		));

		optionTextView = new MenuTextView(context);
		optionTextView.setText(selectedOption);
		optionTextView.setTextColor(getMenuTextView().getTextColors());
		optionTextView.setTextSize(TEXT_SIZE);
		optionTextView.setTypeface(Global.robotoCondensedTypeface);
		optionTextView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		));
		optionTextView.setPadding(
				textView.getPaddingLeft(),
				textView.getPaddingTop(),
				textView.getPaddingRight(),
				textView.getPaddingBottom()
		);

		lockImage = new MenuImageView(context);
		lockImage.setImageResource(ActionMenuElement.locks[0]);
		lockImage.setScaleType(ImageView.ScaleType.CENTER);
		lockImage.setVisibility(View.GONE);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
		lp.setMargins(0, 0, getDp(ActionMenuElement.LOCK_IMAGE_MARGIN_RIGHT), 0);
		lockImage.setLayoutParams(lp);
		lockImage.setVisibility(View.GONE);

		layout.addView(lockImage);
		layout.addView(optionTextView);
	}

	private void updateSelectedOption() {
		selectedOption = options[selectedIndex];
		updateViewText();

		if (selectedIndex > unlockedCount && !isOnOffToggle) {
			lockImage.setVisibility(View.VISIBLE);
		} else {
			lockImage.setVisibility(View.GONE);
		}
	}

	public int getUnlockedCount() {
		return unlockedCount;
	}

	public void setUnlockedCount(int k) {
		unlockedCount = k;
		if (unlockedCount > options.length - 1)
			unlockedCount = options.length - 1;
		if (optionsScreen != null) {
			for (int l = 0; l < optionsScreenItems.length; l++)
				if (l > k)
					optionsScreenItems[l].setLock(true, true);
				else
					optionsScreenItems[l].setLock(false, false);
		}
		updateSelectedOption();
	}

	public int getOptionCount() {
		return options.length - 1;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String as[]) {
		setOptions(as, true);
	}

	public void setOptions(String as[], boolean update) {
		options = as;
		if (selectedIndex > options.length - 1)
			selectedIndex = options.length - 1;
		if (unlockedCount > options.length - 1)
			unlockedCount = options.length - 1;
		updateSelectedOption();
		if (update) update();
	}

	public int getSelectedOption() {
		return selectedIndex;
	}

	public void setSelectedOption(int k) {
		selectedIndex = k;
		if (selectedIndex > options.length - 1)
			selectedIndex = 0;
		if (selectedIndex < 0)
			selectedIndex = options.length - 1;
		updateSelectedOption();
	}

	public void update() {
		optionsScreen = new MenuScreen(text, screen);
		optionsScreenItems = new ActionMenuElement[options.length];
		for (int k = 0; k < optionsScreenItems.length; k++) {
			if (k > unlockedCount) {
				optionsScreenItems[k] = new ActionMenuElement(options[k], this);
				optionsScreenItems[k].setLock(true, true);
			} else {
				optionsScreenItems[k] = new ActionMenuElement(options[k], this);
			}
			optionsScreen.addItem(optionsScreenItems[k]);
		}
		optionsScreen.setSelected(selectedIndex);

		// System.gc();
	}

	public boolean _charvZ() {
		if (m_oZ) {
			m_oZ = false;
			return true;
		} else {
			return m_oZ;
		}
	}

	@Override
	public void handleAction(MenuElement item) {
		int k = 0;
		do {
			if (k >= optionsScreenItems.length)
				break;
			if (item == optionsScreenItems[k]) {
				selectedIndex = k;
				updateSelectedOption();
				break;
			}
			k++;
		} while (true);

		handler.setCurrentMenu(screen, true);
		handler.handleAction(this);
	}

	@Override
	public MenuScreen getCurrentMenu() {
		return optionsScreen;
	}

	@Override
	public void setCurrentMenu(MenuScreen e1, boolean flag) {
	}

	@Override
	protected void updateViewText() {
		if (textView != null && textView instanceof MenuTextView)
			((MenuTextView) textView).setTextOnUiThread(getTextForView());
		if (optionTextView != null) optionTextView.setTextOnUiThread(selectedOption);
	}

	@Override
	public void performAction(int k) {
		// logDebug("OptionMenuElement performAction: k = " + k);
		switch (k) {
			case MenuScreen.KEY_FIRE:
				if (isOnOffToggle) {
					selectedIndex++;
					if (selectedIndex > 1)
						selectedIndex = 0;
					if (selectedIndex == 1)
						selectedOption = getString(R.string.off);
					else
						selectedOption = getString(R.string.on);
					updateViewText();
					handler.handleAction(this);
					return;
				} else {
					m_oZ = true;
					handler.handleAction(this);
					return;
				}

			case MenuScreen.KEY_RIGHT:
				if (isOnOffToggle) {
					if (selectedIndex == 1) {
						selectedIndex = 0;
						selectedOption = getString(R.string.on);
						handler.handleAction(this);
						updateViewText();
					}
					return;
				}
				selectedIndex++;
				if (selectedIndex > options.length - 1)
					selectedIndex = options.length - 1;
				else
					handler.handleAction(this);
				updateSelectedOption();
				return;

			case MenuScreen.KEY_LEFT: // '\003'
				if (isOnOffToggle) {
					if (selectedIndex == 0) {
						selectedIndex = 1;
						selectedOption = getString(R.string.off);
						handler.handleAction(this);
						updateViewText();
					}
					return;
				}
				selectedIndex--;
				if (selectedIndex < 0) {
					selectedIndex = 0;
				} else {
					updateSelectedOption();
					handler.handleAction(this);
				}
				updateSelectedOption();
				break;
		}
	}

	public void setScreen(MenuScreen screen) {
		this.screen = screen;
	}

	@Override
	protected String getTextForView() {
		return text + ": ";
	}

	@Override
	protected void onHighlightChanged() {
		lockImage.setImageResource(ActionMenuElement.locks[isHighlighted ? 2 : 0]);
	}

}
