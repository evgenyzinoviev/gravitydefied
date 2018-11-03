package org.happysanta.gd.Menu;

import android.view.View;

/**
 * Author: ch1p
 */
public interface MenuElement {

    //void setText(String s);

    boolean isSelectable();

    View getView();

    void setText(String text);

    void performAction(int k);
}
