package org.happysanta.gd.Menu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public interface MenuElementOld {

    void setText(String s);

    // Why y before x?!
    void draw(Canvas g, int y, int x);

    boolean isSelectable();

    int getLineSpacing();

    void performAction(int i);

    void setFont(Paint font);

    int getHeight();

    int getFirstLineHeight();

    int getXOffset();

    void setPressed(boolean flag);

    View getView();
}
