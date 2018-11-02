package org.happysanta.gd.Menu;

import android.view.View;

/**
 * Author: ch1p
 */
public interface MenuElement {

	// public abstract void setText(String s);

	public abstract boolean isSelectable();

	public abstract View getView();

	public abstract void setText(String text);

	public void performAction(int k);

}
