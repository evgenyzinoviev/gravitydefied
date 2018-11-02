package org.happysanta.gd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import org.happysanta.gd.API.API;

import static org.happysanta.gd.Helpers.getGDActivity;

public class Settings {

	private static final String LEVEL_ID = "level_id";
	private static final int LEVEL_ID_DEFAULT = 0;

	private static final String PERSPECTIVE_ENABLED = "perspective_enabled";
	private static final boolean PERSPECTIVE_ENABLED_DEFAULT = true;

	private static final String SHADOWS_ENABLED = "shadows_enabled";
	private static final boolean SHADOWS_ENABLED_DEFAULT = true;

	private static final String DRIVER_SPRITE_ENABLED = "driver_sprite_enabled";
	private static final boolean DRIVER_SPRITE_ENABLED_DEFAULT = true;

	private static final String BIKE_SPRITE_ENABLED = "bike_sprite_enabled";
	private static final boolean BIKE_SPRITE_ENABLED_DEFAULT = true;

	private static final String INPUT_OPTION = "input_option";
	private static final int INPUT_OPTION_DEFAULT = 0;

	private static final String LOOK_AHEAD_ENABLED = "look_ahead_enabled";
	private static final boolean LOOK_AHEAD_ENABLED_DEFAULT = true;

	private static final String VIBRATE_ENABLED = "vibrate_enabled";
	private static final boolean VIBRATE_ENABLED_DEFAULT = true;

	private static final String KEYBOARD_IN_MENU_ENABLED = "keyboard_enabled";
	private static final boolean KEYBOARD_IN_MENU_ENABLED_DEFAULT = true;

	private static final String LAST_SEND_STATS = "last_send_stats";
	private static final long LAST_SEND_STATS_DEFAULT = 0;

	private static final String NAME = "name";
	public static final String NAME_DEFAULT = "AAA";
	public static final byte[] NAME_CHARS_DEFALUT = new byte[]{65, 65, 65};

	private static final String LEVELS_SORT = "level_sort"; // in download list
	private static final int LEVELS_SORT_DEFAULT = 0;

	private static SharedPreferences preferences;

	static {
		preferences = getGDActivity().getSharedPreferences("GDSettings", Context.MODE_PRIVATE);
	}

	public static void resetAll() {
		setPerspectiveEnabled(PERSPECTIVE_ENABLED_DEFAULT);
		setShadowsEnabled(SHADOWS_ENABLED_DEFAULT);
		setDriverSpriteEnabled(DRIVER_SPRITE_ENABLED_DEFAULT);
		setBikeSpriteEnabled(BIKE_SPRITE_ENABLED_DEFAULT);
		setLookAheadEnabled(LOOK_AHEAD_ENABLED_DEFAULT);
		setVibrateOnTouchEnabled(VIBRATE_ENABLED_DEFAULT);
		setKeyboardInMenuEnabled(KEYBOARD_IN_MENU_ENABLED_DEFAULT);
		setInputOption(INPUT_OPTION_DEFAULT);
		setLevelsSort(LEVELS_SORT_DEFAULT);
		setName(NAME_CHARS_DEFALUT);
	}

	public static long getLevelId() {
		return preferences.getLong(LEVEL_ID, LEVEL_ID_DEFAULT);
	}

	public static void setLevelId(long levelId) {
		setLong(LEVEL_ID, levelId);
	}

	public static boolean isPerspectiveEnabled() {
		return preferences.getBoolean(PERSPECTIVE_ENABLED, PERSPECTIVE_ENABLED_DEFAULT);
	}

	public static void setPerspectiveEnabled(boolean enabled) {
		setBoolean(PERSPECTIVE_ENABLED, enabled);
	}

	public static boolean isShadowsEnabled() {
		return preferences.getBoolean(SHADOWS_ENABLED, SHADOWS_ENABLED_DEFAULT);
	}

	public static void setShadowsEnabled(boolean enabled) {
		setBoolean(SHADOWS_ENABLED, enabled);
	}

	public static boolean isDriverSpriteEnabled() {
		return preferences.getBoolean(DRIVER_SPRITE_ENABLED, DRIVER_SPRITE_ENABLED_DEFAULT);
	}

	public static void setDriverSpriteEnabled(boolean enabled) {
		setBoolean(DRIVER_SPRITE_ENABLED, enabled);
	}

	public static boolean isBikeSpriteEnabled() {
		return preferences.getBoolean(BIKE_SPRITE_ENABLED, BIKE_SPRITE_ENABLED_DEFAULT);
	}

	public static void setBikeSpriteEnabled(boolean enabled) {
		setBoolean(BIKE_SPRITE_ENABLED, enabled);
	}

	public static boolean isLookAheadEnabled() {
		return preferences.getBoolean(LOOK_AHEAD_ENABLED, LOOK_AHEAD_ENABLED_DEFAULT);
	}

	public static void setLookAheadEnabled(boolean enabled) {
		setBoolean(LOOK_AHEAD_ENABLED, enabled);
	}

	public static boolean isKeyboardInMenuEnabled() {
		return preferences.getBoolean(KEYBOARD_IN_MENU_ENABLED, KEYBOARD_IN_MENU_ENABLED_DEFAULT);
	}

	public static void setKeyboardInMenuEnabled(boolean enabled) {
		setBoolean(KEYBOARD_IN_MENU_ENABLED, enabled);
	}

	public static boolean isVibrateOnTouchEnabled() {
		return preferences.getBoolean(VIBRATE_ENABLED, VIBRATE_ENABLED_DEFAULT);
	}

	public static void setVibrateOnTouchEnabled(boolean enabled) {
		setBoolean(VIBRATE_ENABLED, enabled);
	}

	public static int getInputOption() {
		return preferences.getInt(INPUT_OPTION, INPUT_OPTION_DEFAULT);
	}

	public static void setInputOption(int value) {
		setInt(INPUT_OPTION, value);
	}

	public static long getLastSendStats() {
		return preferences.getLong(LAST_SEND_STATS, LAST_SEND_STATS_DEFAULT);
	}

	public static void setLastSendStats(long value) {
		setLong(LAST_SEND_STATS, value);
	}

	public static API.LevelsSortType getLevelsSort() {
		return API.getSortTypeById(preferences.getInt(LEVELS_SORT, LEVELS_SORT_DEFAULT));
	}

	public static void setLevelsSort(API.LevelsSortType type) {
		setInt(LEVELS_SORT, API.getIdBySortType(type));
	}

	public static void setLevelsSort(int type) {
		setInt(LEVELS_SORT, type);
	}

	public static byte[] getName() {
		String name = preferences.getString(NAME, NAME_DEFAULT);
		if (name.length() < 3) {
			name = NAME_DEFAULT;
		}
		return new byte[]{
				(byte) name.charAt(0),
				(byte) name.charAt(1),
				(byte) name.charAt(2)
		};
	}

	public static void setName(byte[] chars) {
		if (chars.length < 3) {
			setString(NAME, NAME_DEFAULT);
		} else {
			String name = "";
			for (int i = 0; i < 3; i++) {
				name += String.valueOf((char) chars[i]);
			}
			setString(NAME, name);
		}
	}

	private static void setLong(String key, long value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(key, value);
		editorApply(editor);
	}

	private static void setInt(String key, int value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editorApply(editor);
	}

	private static void setBoolean(String key, boolean value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editorApply(editor);
	}

	private static void setString(String key, String value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editorApply(editor);
	}

	private static void editorApply(SharedPreferences.Editor editor) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
			editor.apply();
		else
			editor.commit();
	}

}
