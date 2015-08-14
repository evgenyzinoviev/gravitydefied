package org.happysanta.gd.API;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static org.happysanta.gd.Helpers.getDeviceName;

public class API {

	public static final String URL = "http://gdtr.net/api.php";
	public static final String DEBUG_URL = "http://dev.gdtr.net/api.php";
	public static final String MRG_URL = "http://gdtr.net/mrg/%d.mrg";
	public static final int VERSION = 2;

	public static Request getLevels(int offset, int limit, LevelsSortType sort, ResponseHandler handler)
			throws Exception {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("sort", sort.toString()));
		params.add(new BasicNameValuePair("offset", String.valueOf(offset)));
		params.add(new BasicNameValuePair("limit", String.valueOf(limit)));

		return new Request("getLevels", params, handler);
	}

	public static Request getNotifications(boolean installedFromAPK, ResponseHandler handler) {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("apk", String.valueOf(installedFromAPK ? 1 : 0)));
		return new Request("getNotifications", params, handler);
	}

	public static Request sendStats(String statsJSON, String installationID, int useCheats, ResponseHandler handler) {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("stats", statsJSON));
		params.add(new BasicNameValuePair("id", installationID));
		params.add(new BasicNameValuePair("use_cheats", String.valueOf(useCheats)));
		return new Request("sendStats", params, handler);
	}

	public static Request sendKeyboardLogs(String log, ResponseHandler handler) {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("log", log));
		params.add(new BasicNameValuePair("device", getDeviceName()));
		return new Request("sendKeyboardLogs", params, handler, true);
	}

	public static DownloadFile downloadMrg(long id, FileOutputStream output, DownloadHandler handler) {
		return new DownloadFile(String.format(MRG_URL, id), output, handler);
	}

	public static String getMrgURL(long id) {
		return String.format(MRG_URL, id);
	}

	public static enum LevelsSortType {
		POPULAR("popular"), TRACKS("tracks"), RECENT("recent"), OLDEST("oldest");

		private final String text;

		private LevelsSortType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public static LevelsSortType getSortTypeById(int id) {
		switch (id) {
			case 0:
				return LevelsSortType.POPULAR;
			case 1:
				return LevelsSortType.RECENT;
			case 2:
				return LevelsSortType.OLDEST;
			case 3:
				return LevelsSortType.TRACKS;
		}
		return null;
	}

	public static int getIdBySortType(LevelsSortType type) {
		switch (type) {
			case POPULAR:
				return 0;
			case RECENT:
				return 1;
			case OLDEST:
				return 2;
			case TRACKS:
				return 3;
		}
		return 0;
	}

}
