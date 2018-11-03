package org.happysanta.gd.Menu;

public interface MenuHandler {

    MenuScreen getCurrentMenu();

    void setCurrentMenu(MenuScreen e, boolean flag);

    //void destroy();

    void handleAction(MenuElement item);
}
