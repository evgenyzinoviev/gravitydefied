package org.happysanta.gd.Menu;

import android.text.Spanned;
import org.happysanta.gd.Global;
import org.happysanta.gd.Menu.Views.MenuTextView;

public class BigTextMenuElement
		extends TextMenuElement {

	public static final int TEXT_SIZE = 19;

	public BigTextMenuElement(String s) {
		super(s);
		createTextView();
		setTextParams(textView);
	}

	public BigTextMenuElement(Spanned s) {
		super(s);
		createTextView();
		setTextParams(textView);
	}

	protected static void setTextParams(MenuTextView textView) {
		textView.setTextSize(TEXT_SIZE);
		textView.setTypeface(Global.robotoCondensedTypeface);
		textView.setLineSpacing(0f, 1.2f);
	}

}
