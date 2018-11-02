package org.happysanta.gd;

import android.graphics.Typeface;

public class Global {

	public static final boolean DEBUG = false;
	public static final boolean DISABLE_SCALING = false;
	public static final boolean INSTALLED_FROM_APK = true;
	public static final boolean ACRA_ENABLED = true;
	// public static final boolean ENABLE_TOUCH_HACK_FOR_ALL = false;

	public static float density = 0;
	public static Typeface robotoCondensedTypeface;

	static {
		density = Helpers.getGDActivity().getResources().getDisplayMetrics().density;
		robotoCondensedTypeface = Typeface.createFromAsset(Helpers.getGDActivity().getAssets(), "RobotoCondensed-Regular.ttf");
	}

}
