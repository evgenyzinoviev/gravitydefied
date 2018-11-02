package org.happysanta.gd.Storage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import org.happysanta.gd.API.API;
import org.happysanta.gd.API.DownloadFile;
import org.happysanta.gd.API.DownloadHandler;
import org.happysanta.gd.Callback;
import org.happysanta.gd.DoubleCallback;
import org.happysanta.gd.GDActivity;
import org.happysanta.gd.Global;
import org.happysanta.gd.Levels.LevelHeader;
import org.happysanta.gd.Levels.Reader;
import org.happysanta.gd.Menu.Menu;
import org.happysanta.gd.Menu.MenuScreen;
import org.happysanta.gd.R;
import org.happysanta.gd.Settings;
import org.acra.ACRA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.happysanta.gd.Helpers.getGDActivity;
import static org.happysanta.gd.Helpers.getGameMenu;
import static org.happysanta.gd.Helpers.getString;
import static org.happysanta.gd.Helpers.getTimestamp;
import static org.happysanta.gd.Helpers.isOnline;
import static org.happysanta.gd.Helpers.logDebug;
import static org.happysanta.gd.Helpers.showAlert;

public class LevelsManager {

	private LevelsDataSource dataSource;
	private boolean dbOK = false;
	private Level currentLevel;

	public LevelsManager() {
		GDActivity gd = getGDActivity();
		dataSource = new LevelsDataSource(gd);

		try {
			dataSource.open();

			if (!dataSource.isDefaultLevelCreated()) {
				Level level = dataSource.createLevel("GDTR original", "Codebrew Software", 10, 10, 10, 0, 0, true, 1);
				logDebug("LevelsManager: Default level created!");
				logDebug(level);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logDebug("LevelsManager: db feels bad :(");
			// return;
		}

		logDebug("LevelsManager: db feels OK :)");

		// Shared prefs
		// SharedPreferences settings = getSharedPreferences();
		// long levelId = settings.getLong(PREFS_LEVEL_ID, 0);
		long levelId = Settings.getLevelId();
		if (levelId < 1 || !mrgIsAvailable(levelId)) {
			logDebug("LevelsManager: levelId = " + levelId + ", < 1 or mrg is not available; now: reset id");
			/*SharedPreferences.Editor editor = settings.edit();
			editor.putLong(PREFS_LEVEL_ID, 1);
			editor.commit();*/
			resetId();
		}

		reload();
		dbOK = true;
	}

	public void resetId() {
		Settings.setLevelId(1);
	}

	public void reload() {
		long id = Settings.getLevelId();
		currentLevel = dataSource.getLevel(id);

		if (currentLevel == null) {
			logDebug("LevelsManager: failed to load currentLevel; currentId = " + id);
		} else {
			logDebug("LevelsManager: level = " + currentLevel);
		}

		if (Global.ACRA_ENABLED) {
			ACRA.getErrorReporter().putCustomData("level_api_id:", String.valueOf(currentLevel.getApiId()));
		}
	}

	public void closeDataSource() {
		dataSource.close();
	}

	public long getCurrentId() {
		return currentLevel.getId();
	}

	public void setCurrentId(long id) {
		// currentId = id;
		Settings.setLevelId(id);
		/*SharedPreferences settings = getSharedPreferences();
		SharedPreferences.Editor edit = settings.edit();
		edit.putLong(PREFS_LEVEL_ID, id);
		edit.commit();*/
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public File getCurrentLevelsFile() {
		if (currentLevel.getId() > 1)
			return getMrgFileById(currentLevel.getId());

		return null;
	}

	private boolean mrgIsAvailable(long id) {
		if (id == 1) // This is default built-in levels.mrg
			return true;

		File file = getMrgFileById(id);
		return isExternalStorageReadable() && file.exists();
	}

	public boolean isDbOK() {
		return dbOK;
	}

	public long install(File file, String name, String author, long apiId) throws Exception {
		if (!isSpaceAvailable(file.length())) {
			throw new Exception(getString(R.string.e_no_space_left));
		}

		InputStream inputStream = new FileInputStream(file);
		LevelHeader header = Reader.readHeader(inputStream);
		try {
			inputStream.close();
		} catch (IOException e) {
		}

		if (!header.isCountsOk()) {
			throw new IOException(file.getName() + " is not valid");
		}

		Level level = dataSource.createLevel(name, author, header.getCount(0), header.getCount(1), header.getCount(2), 0, getTimestamp(), false, apiId);
		long id = level.getId();
		if (id < 1) {
			throw new Exception(getString(R.string.e_cannot_save_level));
		}

		File newFile = getMrgFileById(id);
		copy(file, newFile);

		return id;
	}

	public void installAsync(File file, String name, String author, long apiId, final DoubleCallback callback) {
		GDActivity gd = getGDActivity();
		final ProgressDialog progressDialog = ProgressDialog.show(gd, getString(R.string.install), getString(R.string.installing), true);

		new AsyncInstallLevel() {
			@Override
			protected void onPostExecute(Object result) {
				progressDialog.dismiss();

				if (result instanceof Throwable) {
					Throwable throwable = (Throwable) result;
					throwable.printStackTrace();
					showAlert(getString(R.string.error), throwable.getMessage(), null);
					if (callback != null)
						callback.onFail();
					return;
				}

				if (callback != null)
					callback.onDone((long) result);
			}
		}.execute(file, name, author, apiId);
	}

	public void load(Level level) throws RuntimeException {
		/*File file = getMrgFileById(level.getId());
		if (!mrgIsAvailable(level.getId())) {
			throw new RuntimeException("Unable to load levels \"" +level.getName() + "\"");
		}*/

		// Loader loader = getLevelLoader();
		// Menu menu = getGameMenu();

		// loader.setLevelsFile(file);
		// menu.reloadLevels();

		setCurrentId(level.getId());
		getGDActivity().restartApp();
	}

	public boolean isApiIdInstalled(long apiId) {
		return dataSource.isApiIdInstalled(apiId);
	}

	public Level[] getInstalledLevels(int offset, int count) {
		return dataSource.getLevels(offset, count).toArray(new Level[0]);
	}

	public Level getLeveL(long id) {
		return dataSource.getLevel(id);
	}

	public Level[] getAllInstalledLevels() {
		return dataSource.getAllLevels().toArray(new Level[0]);
	}

	public synchronized HashMap<String, Double> getLevelsStat() {
		Level[] levels = getAllInstalledLevels();
		HashMap<String, Double> stat = new HashMap<>();
		if (levels.length > 0) {
			for (Level level : levels) {
				int[] completed = level.getUnlockedAll();
				int completedCount = 0;
				for (int i = 0; i < completed.length; i++) {
					if (completed[i] < 0) completed[i] = 0;
					completedCount += completed[i];
				}

				double totalCount = level.getCountEasy() + level.getCountMedium() + level.getCountHard();
				double per = completedCount / totalCount * 100;

				stat.put(String.valueOf(level.getApiId()), per);
			}
		}
		return stat;
	}

	public void delete(Level level) {
		dataSource.deleteLevel(level);
		File file = getMrgFileById(level.getId());
		try {
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAsync(Level level, final Runnable callback) {
		GDActivity gd = getGDActivity();
		final ProgressDialog progressDialog = ProgressDialog.show(gd, getString(R.string.delete), getString(R.string.deleting), true);

		new AsyncDeleteLevel() {
			@Override
			protected void onPostExecute(Void v) {
				progressDialog.dismiss();
				if (callback != null)
					callback.run();
			}
		}.execute(level);
	}

	public void updateLevelSettings() {
		dataSource.updateLevel(currentLevel);
	}

	public void downloadLevel(final Level level, final Callback successCallback) {
		final GDActivity gd = getGDActivity();
		File outputDir = gd.getCacheDir();

		try {
			boolean readable = isExternalStorageReadable();
			if (!readable) {
				throw new Exception(getString(R.string.e_external_storage_is_not_readable));
			}

			if (!isOnline()) {
				throw new Exception(getString(R.string.e_no_network_connection));
			}

			if (!isSpaceAvailable(level.getSize())) {
				throw new Exception(getString(R.string.e_no_space_left));
			}

			final File outputFile = File.createTempFile("levels" + level.getApiId(), "mrg", outputDir);
			FileOutputStream out = new FileOutputStream(outputFile);

			// logDebug("downloadLevel: 4");
			// final API api = new API();
			final ProgressDialog progress;
			final DownloadFile downloadFile = new DownloadFile(API.getMrgURL(level.getApiId()), out);

			progress = new ProgressDialog(gd);
			progress.setMessage(getString(R.string.downloading));
			progress.setIndeterminate(true);
			progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progress.setCancelable(true);

			final DownloadHandler handler = new DownloadHandler() {
				@Override
				public void onFinish(Throwable error) {
					progress.dismiss();

					if (error != null) {
						// error.printStackTrace();
						error.printStackTrace();
						showAlert(getString(R.string.error), error.getMessage(), null);

						outputFile.delete();
						return;
					}

					// Install
					installAsync(outputFile, level.getName(), level.getAuthor(), level.getApiId(), new DoubleCallback() {
						@Override
						public void onDone(Object... objects) {
							long id = (long) objects[0];
							outputFile.delete();

							if (successCallback != null)
								successCallback.onDone(id);
						}

						@Override
						public void onFail() {
							outputFile.delete();
						}
					});
				}

				@Override
				public void onStart() {
					progress.show();
				}

				@Override
				public void onProgress(int pr) {
					progress.setIndeterminate(false);
					progress.setMax(100);
					progress.setProgress(pr);
				}
			};
			progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					downloadFile.cancel();
					handler.onFinish(new InterruptedException(getString(R.string.e_downloading_was_interrupted)));
				}
			});

			downloadFile.setDownloadHandler(handler);
			downloadFile.start();
		} catch (Exception e) {
			showAlert(getString(R.string.error), e.getMessage(), null);
		}
	}

	public void showSuccessfullyInstalledDialog() {
		GDActivity gd = getGDActivity();
		AlertDialog success = new AlertDialog.Builder(gd)
				.setTitle(getString(R.string.installed))
				.setMessage(getString(R.string.successfully_installed))
				.setPositiveButton(getString(R.string.ok), null)
				.setNegativeButton(getString(R.string.open_installed), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Menu menu = getGameMenu();
						MenuScreen currentMenu = getGameMenu().getCurrentMenu(),
								newMenu = menu.managerInstalledScreen;

						if (currentMenu == menu.managerDownloadScreen || currentMenu.getNavTarget() == menu.managerDownloadScreen) {
							menu.managerDownloadScreen.onHide(menu.managerScreen);
						}

						menu.setCurrentMenu(newMenu, false);
					}
				})
				.create();
		success.show();
	}

	public HashMap<Long, Long> findInstalledLevels(ArrayList<Long> apiIds) {
		return dataSource.findInstalledLevels(apiIds);
	}

	public HighScores getHighScores(int level, int track) {
		HighScores scores = dataSource.getHighScores(currentLevel.getId(), level, track);
		// logDebug("LevelsManager.getHighScores: " + scores);
		return scores;
	}

	public void saveHighScores(HighScores scores) {
		dataSource.updateHighScores(scores);
	}

	public void clearHighScores() {
		dataSource.clearHighScores(currentLevel.getId());
	}

	public void clearAllHighScores() {
		dataSource.clearHighScores(0);
	}

	public void resetAllLevelsSettings() {
		dataSource.resetAllLevelsSettings();

		logDebug("All levels now: " + dataSource.getAllLevels());
		logDebug("Level#1: " + dataSource.getLevel(1));
	}

	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	public static File getLevelsDirectory() {
		File file = new File(Environment.getExternalStorageDirectory(), "GDLevels");
		if (!file.mkdirs()) {
			logDebug("LevelsManager.getLevelsDirectory: directory not created");
		}
		return file;
	}

	public static String getMrgFileNameById(long id) {
		return getLevelsDirectory().getAbsolutePath() + "/" + id + ".mrg";
	}

	public static File getMrgFileById(long id) {
		if (id == 1) return null;
		return new File(getMrgFileNameById(id));
	}

	public static void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}

		in.close();
		out.close();
	}

	public static boolean isSpaceAvailable(long bytes) {
		StatFs stat = new StatFs(getLevelsDirectory().getPath());
		long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
		return bytesAvailable >= bytes;
	}

	private class AsyncDeleteLevel extends AsyncTask<Level, Void, Void> {
		@Override
		protected Void doInBackground(Level... levels) {
			delete(levels[0]);
			return null;
		}
	}

	private class AsyncInstallLevel extends AsyncTask<Object, Void, Object> {
		@Override
		protected Object doInBackground(Object... objects) {
			File file = (File) objects[0];
			String name = (String) objects[1];
			String author = (String) objects[2];
			long apiId = (long) objects[3];

			long id = 0;
			try {
				id = install(file, name, author, apiId);
			} catch (Throwable e) {
				return e;
			}

			return id;
		}
	}

}
