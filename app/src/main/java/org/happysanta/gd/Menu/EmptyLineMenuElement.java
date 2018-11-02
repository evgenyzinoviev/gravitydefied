package org.happysanta.gd.Menu;

import android.view.View;
import android.view.ViewGroup;
import org.happysanta.gd.Menu.Views.MenuTextView;

import static org.happysanta.gd.Helpers.getDp;
import static org.happysanta.gd.Helpers.getGDActivity;

public class EmptyLineMenuElement implements MenuElement {

	protected String text;
	protected int offset;
	protected MenuTextView view;

	EmptyLineMenuElement(int offset) {
		this.offset = offset;

		view = new MenuTextView(getGDActivity());
		view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDp(offset)));
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public void setText(String s) {
		text = s;
	}

	@Override
	public void performAction(int k) {
	}

}
