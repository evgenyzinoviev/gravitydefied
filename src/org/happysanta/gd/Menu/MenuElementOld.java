package org.happysanta.gd.Menu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public interface MenuElementOld {

	public abstract void setText(String s);

	// Why y before x?!
	public abstract void draw(Canvas g, int y, int x);

	public abstract boolean isSelectable();

	public abstract int getLineSpacing();

	public abstract void performAction(int i);

	public abstract void setFont(Paint font);

	public abstract int getHeight();

	public abstract int getFirstLineHeight();

	public abstract int getXOffset();

	public abstract void setPressed(boolean flag);

	public abstract View getView();

}
