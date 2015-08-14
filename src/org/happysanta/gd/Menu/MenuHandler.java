package org.happysanta.gd.Menu;

public interface MenuHandler {

	public abstract MenuScreen getCurrentMenu();

	public abstract void setCurrentMenu(MenuScreen e, boolean flag);

	// public abstract void destroy();

	public abstract void handleAction(MenuElement item);
}
