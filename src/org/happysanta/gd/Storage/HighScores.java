package org.happysanta.gd.Storage;

import org.happysanta.gd.Settings;

public class HighScores {

	private static final long MAX_TIME = 0xffff28L;

	private long id;
	private long levelId = 0;
	private int level = 0;
	private int track = 0;
	private long[][] times = new long[4][3];
	private String[][] names = new String[4][3];

	public HighScores() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLevelId() {
		return levelId;
	}

	public void setLevelId(long levelId) {
		this.levelId = levelId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public long getTime(int league, int place) {
		return times[league][place];
	}

	public void setTime(int league, int place, long value) {
		times[league][place] = value;
	}

	public String getName(int league, int place) {
		return names[league][place];
	}

	public void setName(int league, int place, String value) {
		names[league][place] = value;
	}

	public String[] getScores(int league) {
		String[] scores = new String[3];
		for (int places = 0; places < 3; places++) {
			if (times[league][places] != 0L) {
				int k = (int) times[league][places] / 100;
				int l = (int) times[league][places] % 100;
				scores[places] = names[league][places] + " ";
				if (k / 60 < 10)
					scores[places] += " 0" + k / 60;
				else
					scores[places] += " " + k / 60;
				if (k % 60 < 10)
					scores[places] += ":0" + k % 60;
				else
					scores[places] += ":" + k % 60;
				if (l < 10)
					scores[places] += ".0" + l;
				else
					scores[places] += "." + l;
			} else {
				scores[places] = null;
			}
		}

		return scores;
	}

	public int getPlace(int league, long time) {
		for (int place = 0; place < 3; place++)
			if (times[league][place] > time || times[league][place] == 0L)
				return place;

		return 3;
	}

	private void clearTimes() {
		for (int leagues = 0; leagues < 4; leagues++) {
			for (int places = 0; places < 3; places++)
				times[leagues][places] = 0L;
		}
	}

	public void saveHighScore(int league, String name, long time) {
		name = trimName(name);
		int place;
		if ((place = getPlace(league, time)) != 3) {
			if (time > MAX_TIME)
				time = MAX_TIME;
			moveScoreEntries(league, place);

			times[league][place] = time;
			names[league][place] = name;
		}
	}

	private void moveScoreEntries(int league, int i) {
		for (int place = 2; place > i; place--) {
			times[league][place] = times[league][place - 1];
			names[league][place] = names[league][place - 1];
		}
	}

	private static String trimName(String name) {
		if (name.length() > 3)
			name = name.substring(0, 3);
		else if (name.length() < 3)
			name = Settings.NAME_DEFAULT;

		return name.toUpperCase();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Storage.HighScores {");

		s.append("id: " + id + ", ");
		s.append("level_id: " + levelId + ", ");
		s.append("level: " + level + ", ");
		s.append("track: " + track);

		s.append("}");
		return s.toString();
	}

}
