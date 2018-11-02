package org.happysanta.gd.Storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LevelsSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "levels.db";

	public static final String TABLE_LEVELS = "levels";
	public static final String TABLE_HIGHSCORES = "highscores";

	public static final String LEVELS_COLUMN_ID = "_id";
	public static final String LEVELS_COLUMN_NAME = "name";
	public static final String LEVELS_COLUMN_AUTHOR = "author";
	public static final String LEVELS_COLUMN_COUNT_EASY = "count_easy";
	public static final String LEVELS_COLUMN_COUNT_MEDIUM = "count_medium";
	public static final String LEVELS_COLUMN_COUNT_HARD = "count_hard";
	public static final String LEVELS_COLUMN_ADDED = "added_ts";
	public static final String LEVELS_COLUMN_INSTALLED = "installed_ts";
	public static final String LEVELS_COLUMN_IS_DEFAULT = "is_default";
	public static final String LEVELS_COLUMN_API_ID = "api_id";
	public static final String LEVELS_COLUMN_UNLOCKED_EASY = "unlocked_easy";
	public static final String LEVELS_COLUMN_UNLOCKED_MEDIUM = "unlocked_medium";
	public static final String LEVELS_COLUMN_UNLOCKED_HARD = "unlocked_hard";
	public static final String LEVELS_COLUMN_SELECTED_LEVEL = "selected_level";
	public static final String LEVELS_COLUMN_SELECTED_TRACK = "selected_track";
	public static final String LEVELS_COLUMN_SELECTED_LEAGUE = "selected_league";
	public static final String LEVELS_COLUMN_UNLOCKED_LEVELS = "unlocked_levels";
	public static final String LEVELS_COLUMN_UNLOCKED_LEAGUES = "unlocked_leagues";

	public static final String HIGHSCORES_COLUMN_ID = "_id";
	public static final String HIGHSCORES_COLUMN_LEVEL_ID = "level_id";
	public static final String HIGHSCORES_COLUMN_LEVEL = "level";
	public static final String HIGHSCORES_COLUMN_TRACK = "track";

	private static final String TABLE_LEVELS_CREATE = "CREATE TABLE "
			+ TABLE_LEVELS + "("
			+ LEVELS_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
			+ LEVELS_COLUMN_NAME + " TEXT NOT NULL, "
			+ LEVELS_COLUMN_AUTHOR + " TEXT NOT NULL, "
			+ LEVELS_COLUMN_COUNT_EASY + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_COUNT_MEDIUM + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_COUNT_HARD + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_ADDED + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_INSTALLED + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_IS_DEFAULT + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_API_ID + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_UNLOCKED_EASY + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_UNLOCKED_MEDIUM + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_UNLOCKED_HARD + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_SELECTED_LEVEL + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_SELECTED_TRACK + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_SELECTED_LEAGUE + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_UNLOCKED_LEVELS + " INTEGER NOT NULL, "
			+ LEVELS_COLUMN_UNLOCKED_LEAGUES + " INTEGER NOT NULL"
			+ ");";

	private static final String TABLE_HIGHSCORES_CREATE = " CREATE TABLE "
			+ TABLE_HIGHSCORES + "("
			+ HIGHSCORES_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
			+ HIGHSCORES_COLUMN_LEVEL_ID + " INTEGER NOT NULL, "
			+ HIGHSCORES_COLUMN_LEVEL + " INTEGER NOT NULL, "
			+ HIGHSCORES_COLUMN_TRACK + " INTEGER NOT NULL, "

			// 100cc
			+ getHighscoresTimeColumn(0, 0) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(0, 0) + " TEXT, "
			+ getHighscoresTimeColumn(0, 1) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(0, 1) + " TEXT, "
			+ getHighscoresTimeColumn(0, 2) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(0, 2) + " TEXT, "

			// 175cc
			+ getHighscoresTimeColumn(1, 0) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(1, 0) + " TEXT, "
			+ getHighscoresTimeColumn(1, 1) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(1, 1) + " TEXT, "
			+ getHighscoresTimeColumn(1, 2) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(1, 2) + " TEXT, "

			// 220cc
			+ getHighscoresTimeColumn(2, 0) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(2, 0) + " TEXT, "
			+ getHighscoresTimeColumn(2, 1) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(2, 1) + " TEXT, "
			+ getHighscoresTimeColumn(2, 2) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(2, 2) + " TEXT, "

			// 325cc
			+ getHighscoresTimeColumn(3, 0) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(3, 0) + " TEXT, "
			+ getHighscoresTimeColumn(3, 1) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(3, 1) + " TEXT, "
			+ getHighscoresTimeColumn(3, 2) + " INTEGER NOT NULL, "
			+ getHighscoresNameColumn(3, 2) + " TEXT"

			+ ")";

	LevelsSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_LEVELS_CREATE);
		createLevelsIndexes(db);

		db.execSQL(TABLE_HIGHSCORES_CREATE);
		createHighscoresIndexes(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private void createLevelsIndexes(SQLiteDatabase db) {
		db.execSQL("CREATE INDEX " + LEVELS_COLUMN_API_ID + "_index ON " + TABLE_LEVELS + "(" + LEVELS_COLUMN_API_ID + ")");
		db.execSQL("CREATE INDEX " + LEVELS_COLUMN_IS_DEFAULT + "_index ON " + TABLE_LEVELS + "(" + LEVELS_COLUMN_IS_DEFAULT + ")");
	}

	private void createHighscoresIndexes(SQLiteDatabase db) {
		db.execSQL("CREATE INDEX level_id_level_track_index ON " + TABLE_HIGHSCORES + "("
				+ HIGHSCORES_COLUMN_LEVEL_ID + ", "
				+ HIGHSCORES_COLUMN_LEVEL + ", "
				+ HIGHSCORES_COLUMN_TRACK
				+ ")");
	}

	public static String getHighscoresTimeColumn(int league, int place) {
		return "l" + league + "_p" + place + "_time";
	}

	public static String getHighscoresNameColumn(int league, int place) {
		return "l" + league + "_p" + place + "_name";
	}

}
