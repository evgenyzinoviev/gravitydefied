package org.happysanta.gd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import org.happysanta.gd.Game.GameView;
import org.happysanta.gd.Levels.Loader;
import org.happysanta.gd.Menu.Menu;
import org.happysanta.gd.Storage.LevelsManager;

import java.io.IOException;
import java.io.InputStream;

public class Helpers {

	private static char[] cp1251Map = new char[]{
			'\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007',
			'\u0008', '\u0009', '\n', '\u000B', '\u000C', '\r', '\u000E', '\u000F',
			'\u0010', '\u0011', '\u0012', '\u0013', '\u0014', '\u0015', '\u0016', '\u0017',
			'\u0018', '\u0019', '\u001A', '\u001B', '\u001C', '\u001D', '\u001E', '\u001F',
			'\u0020', '\u0021', '\u0022', '\u0023', '\u0024', '\u0025', '\u0026', '\'',
			'\u0028', '\u0029', '\u002A', '\u002B', '\u002C', '\u002D', '\u002E', '\u002F',
			'\u0030', '\u0031', '\u0032', '\u0033', '\u0034', '\u0035', '\u0036', '\u0037',
			'\u0038', '\u0039', '\u003A', '\u003B', '\u003C', '\u003D', '\u003E', '\u003F',
			'\u0040', '\u0041', '\u0042', '\u0043', '\u0044', '\u0045', '\u0046', '\u0047',
			'\u0048', '\u0049', '\u004A', '\u004B', '\u004C', '\u004D', '\u004E', '\u004F',
			'\u0050', '\u0051', '\u0052', '\u0053', '\u0054', '\u0055', '\u0056', '\u0057',
			'\u0058', '\u0059', '\u005A', '\u005B', '\\', '\u005D', '\u005E', '\u005F',
			'\u0060', '\u0061', '\u0062', '\u0063', '\u0064', '\u0065', '\u0066', '\u0067',
			'\u0068', '\u0069', '\u006A', '\u006B', '\u006C', '\u006D', '\u006E', '\u006F',
			'\u0070', '\u0071', '\u0072', '\u0073', '\u0074', '\u0075', '\u0076', '\u0077',
			'\u0078', '\u0079', '\u007A', '\u007B', '\u007C', '\u007D', '\u007E', '\u007F',
			'\u0402', '\u0403', '\u201A', '\u0453', '\u201E', '\u2026', '\u2020', '\u2021',
			'\u20AC', '\u2030', '\u0409', '\u2039', '\u040A', '\u040C', '\u040B', '\u040F',
			'\u0452', '\u2018', '\u2019', '\u201C', '\u201D', '\u2022', '\u2013', '\u2014',
			'\uFFFD', '\u2122', '\u0459', '\u203A', '\u045A', '\u045C', '\u045B', '\u045F',
			'\u00A0', '\u040E', '\u045E', '\u0408', '\u00A4', '\u0490', '\u00A6', '\u00A7',
			'\u0401', '\u00A9', '\u0404', '\u00AB', '\u00AC', '\u00AD', '\u00AE', '\u0407',
			'\u00B0', '\u00B1', '\u0406', '\u0456', '\u0491', '\u00B5', '\u00B6', '\u00B7',
			'\u0451', '\u2116', '\u0454', '\u00BB', '\u0458', '\u0405', '\u0455', '\u0457',
			'\u0410', '\u0411', '\u0412', '\u0413', '\u0414', '\u0415', '\u0416', '\u0417',
			'\u0418', '\u0419', '\u041A', '\u041B', '\u041C', '\u041D', '\u041E', '\u041F',
			'\u0420', '\u0421', '\u0422', '\u0423', '\u0424', '\u0425', '\u0426', '\u0427',
			'\u0428', '\u0429', '\u042A', '\u042B', '\u042C', '\u042D', '\u042E', '\u042F',
			'\u0430', '\u0431', '\u0432', '\u0433', '\u0434', '\u0435', '\u0436', '\u0437',
			'\u0438', '\u0439', '\u043A', '\u043B', '\u043C', '\u043D', '\u043E', '\u043F',
			'\u0440', '\u0441', '\u0442', '\u0443', '\u0444', '\u0445', '\u0446', '\u0447',
			'\u0448', '\u0449', '\u044A', '\u044B', '\u044C', '\u044D', '\u044E', '\u044F'
	};

	public static GDActivity getGDActivity() {
		return GDActivity.shared;
	}

	public static GameView getGDView() {
		return GDActivity.shared.gameView;
	}

	public static Menu getGameMenu() {
		return GDActivity.shared.menu;
	}

	public static Loader getLevelLoader() {
		return GDActivity.shared.levelLoader;
	}

	public static LevelsManager getLevelsManager() {
		return GDActivity.shared.levelsManager;
	}

	public static int getDp(int px) {
		return Math.round(px * Global.density);
	}

	public static int getDp(float px) {
		return Math.round(px * Global.density);
	}

	public static String getCurrentStackTrace() {
		String del = "\n";
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] list = Thread.currentThread().getStackTrace();
		// for (StackTraceElement e: list) {
		for (int i = 0; i < list.length; i++) {
			sb.append(list[i].toString() + (i < list.length - 1 ? del : ""));
		}
		return sb.toString();
	}

	public static Bitmap loadBitmapFromDrawable(int id) {
		BitmapFactory.Options options = null;
		if (!isSDK11OrHigher()) {
			options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		}
		return BitmapFactory.decodeResource(getGDActivity().getResources(), id);
	}

	public static Bitmap loadBitmapFromAsset(String name) throws IOException {
		if (name.startsWith("/")) name = name.substring(1);
		Bitmap bmp = null;
		InputStream s = getGDActivity().getAssets().open(name);
		bmp = BitmapFactory.decodeStream(s);
		s.close();
		return bmp;
	}

	public static void logDebug(String s) {
		Log.d("AGDTR<" + Thread.currentThread().getName() + ">", s);
	}

	public static void logDebug(Object s) {
		Log.d("AGDTR<" + Thread.currentThread().getName() + ">", s.toString());
	}

	public static boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getGDActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	/*public static String trimLine(String s, Paint f, int w) {
		// logDebug("trimLine; s = " + s + ", w = " + w + "; measure = " + f.measureText(s));
		String points = "...", result = s;
		float avgWidth = f.measureText("o"), pointsWidth = f.measureText(points);
		float diff;
		int i = 0,
			len = s.length(),
			half = 0,
			c = 0,
			st1 = 0,
			st2 = len - 1;

		if (s.equals("") || f.measureText(s) <= w) return s;

		w -= pointsWidth;

		int tmpMax = (int)Math.round(Math.floor(w / Math.round(avgWidth / 1.5)) * 2);
		if (len > tmpMax) {
			s = s.substring(0, tmpMax);
			len = s.length();
		}

		while (true) {
			i++;
			// if (i >= 100) return s;
			if (half == 0) {
				c = Math.round(len / 2);
			} else {
				switch (half) {
					case 1:
						st2 = c;
						c -= (st2 - st1) / 2;
						break;

					case 2:
						st1 = c;
						c += (st2 - st1) / 2;
						break;
				}
			}

			String sub = s.substring(0, c > len - 1 ? len - 1 : c);
			float subWidth = f.measureText(sub);

			if (subWidth > w + avgWidth) half = 1;
			else if (subWidth < w - avgWidth) half = 2;
			else {
				diff = subWidth - w;

				if (diff > 0) {
					result = s.substring(0, c - 1);
					if (!result.equals(s)) result += points;
				} else  {
					result = s.substring(0, c + 1);
					if (!result.equals(s)) result += points;
				}

				return result;
			}
		}

		// return s;
	}*/

	public static String getString(int r) {
		return GDActivity.shared.getString(r);
	}

	public static String[] getStringArray(int r) {
		return GDActivity.shared.getResources().getStringArray(r);
	}

	public static void runOnUiThread(Runnable runnable) {
		GDActivity.shared.runOnUiThread(runnable);
	}

	public static long getTimestamp() {
		return System.currentTimeMillis() / 1000L;
	}

	public static void showAlert(String title, String message, final Runnable listener) {
		Context context = getGDActivity();
		AlertDialog alertDialog = new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (listener != null) listener.run();
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (listener != null) listener.run();
					}
				})
				.create();
		alertDialog.show();
	}

	public static void showConfirm(String title, String message, final Runnable onOk, final Runnable onCancel) {
		Context context = getGDActivity();
		AlertDialog.Builder alert = new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (onOk != null) onOk.run();
					}
				})
				.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (onCancel != null) onCancel.run();
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (onCancel != null) onCancel.run();
					}
				});
		alert.show();
	}

	public static boolean isSDK11OrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static boolean isSDK10OrLower() {
		return Build.VERSION.SDK_INT <= 10;
	}

	public static String getAppVersion() {
		String v = "0.0";
		try {
			PackageInfo pInfo = GDActivity.shared.getPackageManager().getPackageInfo(GDActivity.shared.getPackageName(), 0);
			v = pInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
		}
		return v;
	}

	public static String decodeCp1251(byte[] data) {
		if (data == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer(data.length);
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 0) break;
			sb.append(cp1251Map[data[i] & 0xFF]);
		}
		return sb.toString();
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

}
