package org.happysanta.gd.Storage;

import android.text.format.DateUtils;

import static org.happysanta.gd.Helpers.getGDActivity;

public class Level {

	private long id = 0;
	private String name;
	private String author;
	private int[] count;
	private int size = 0;
	private long addedTs = 0;
	private long installedTs = 0;
	private boolean _isDefault = false;
	private long apiId = 0;
	private int[] unlocked;
	private int selectedTrack = 0;
	private int selectedLevel = 0;
	private int selectedLeague = 0;
	private int unlockedLevels = 0;
	private int unlockedLeagues = 0;

	public Level() {
		count = new int[3];
		unlocked = new int[3];
	}

	public Level(long id, String name, String author, int countEasy, int countMedium, int countHard, int addedTs, int size, long apiId) {
		this(id, name, author, countEasy, countMedium, countHard, addedTs, size, apiId, 0, 0, 0);
	}

	public Level(long id, String name, String author, int countEasy, int countMedium, int countHard, int addedTs, int size, long apiId, int unlockedEasy, int unlockedMedium, int unlockedHard) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.count = new int[]{
				countEasy, countMedium, countHard
		};
		this.addedTs = addedTs;
		this.size = size;
		this.apiId = apiId;
		this.unlocked = new int[]{
				unlockedEasy, unlockedMedium, unlockedHard
		};
	}

	public long getId() {
		return id;
	}

	public long getAnyId() {
		return id > 0 ? id : apiId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getCountEasy() {
		return this.count[0];
	}

	public int getCountMedium() {
		return this.count[1];
	}

	public int getCountHard() {
		return this.count[2];
	}

	public int getCount(int level) {
		return this.count[level];
	}

	public void setCountEasy(int count) {
		this.count[0] = count;
	}

	public void setCountMedium(int count) {
		this.count[1] = count;
	}

	public void setCountHard(int count) {
		this.count[2] = count;
	}

	public void setCount(int easy, int medium, int hard) {
		setCountEasy(easy);
		setCountMedium(medium);
		setCountHard(hard);
	}

	public long getAddedTs() {
		return addedTs;
	}

	public void setAddedTs(long ts) {
		addedTs = ts;
	}

	public long getInstalledTs() {
		return installedTs;
	}

	public void setInstalledTs(long ts) {
		installedTs = ts;
	}

	public void setIsDefault(boolean isDefault) {
		this._isDefault = isDefault;
	}

	public boolean isDefault() {
		return _isDefault;
	}

	public long getApiId() {
		return apiId;
	}

	public void setApiId(long apiId) {
		this.apiId = apiId;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isInstalled() {
		return id > 0;
	}

	public String getSizeKb() {
		return String.valueOf(Math.round((size / 1024f) * 100f) / 100f);
	}

	public String getShortAddedDate() {
		return getShortDate(addedTs);
	}

	public String getFullAddedDate() {
		return getFullDate(addedTs);
	}

	public String getShortInstalledDate() {
		return getShortDate(installedTs);
	}

	public String getFullInstalledDate() {
		return getFullDate(installedTs);
	}

	public int getUnlockedEasy() {
		return this.unlocked[0];
	}

	public int getUnlockedMedium() {
		return this.unlocked[1];
	}

	public int getUnlockedHard() {
		return this.unlocked[2];
	}

	public int getUnlocked(int level) {
		//if (level < 3)
		return unlocked[level];

		//logDebug("Level.getUnlocked: level = " + level + ", out of bounds");
		//return 0;
	}

	public int[] getUnlockedAll() {
		return unlocked;
	}

	public void setUnlockedEasy(int unlocked) {
		this.unlocked[0] = unlocked;
	}

	public void setUnlockedMedium(int unlocked) {
		this.unlocked[1] = unlocked;
	}

	public void setUnlockedHard(int unlocked) {
		this.unlocked[2] = unlocked;
	}

	public void setUnlocked(int easy, int medium, int hard) {
		setUnlockedEasy(easy);
		setUnlockedMedium(medium);
		setUnlockedHard(hard);
	}

	public void setUnlocked(int level, int value) {
		unlocked[level] = value;
	}

	public int getSelectedTrack() {
		return selectedTrack;
	}

	public int getSelectedLevel() {
		return selectedLevel;
	}

	public int getSelectedLeague() {
		return selectedLeague;
	}

	public void setSelectedTrack(int selectedTrack) {
		this.selectedTrack = selectedTrack;
	}

	public void setSelectedLevel(int selectedLevel) {
		this.selectedLevel = selectedLevel;
	}

	public void setSelectedLeague(int selectedLeague) {
		this.selectedLeague = selectedLeague;
	}

	public int getUnlockedLevels() {
		return unlockedLevels;
	}

	public int getUnlockedLeagues() {
		return unlockedLeagues;
	}

	public void setUnlockedLevels(int unlockedLevels) {
		this.unlockedLevels = unlockedLevels;
	}

	public void setUnlockedLeagues(int unlockedLeagues) {
		this.unlockedLeagues = unlockedLeagues;
	}

	public boolean isSettingsClear() {
		return unlockedLevels == 0
				&& unlocked[0] == 0
				&& unlocked[1] == 0
				&& unlocked[2] == 0;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Storage.Level {");

		s.append("id: " + id + ", ");
		s.append("name: \"" + name + "\", ");
		s.append("author: \"" + author + "\", ");
		s.append("count: " + count[0] + "/" + count[1] + "/" + count[2] + ", ");
		s.append("added_ts: " + addedTs + ", ");
		s.append("installed_ts: " + installedTs + ", ");
		s.append("default: " + (_isDefault ? 1 : 0) + ", ");
		s.append("api_id: " + apiId + ", ");
		s.append("unlocked: " + unlocked[0] + "/" + unlocked[1] + "/" + unlocked[2] + ", ");
		s.append("selected_track: " + selectedTrack + ", ");
		s.append("selected_level: " + selectedLevel + ", ");
		s.append("selected_league: " + selectedLeague + ", ");
		s.append("unlocked_levels: " + unlockedLevels + ", ");
		s.append("unlocked_leagues: " + unlockedLeagues);

		s.append("}");
		return s.toString();
	}

	private static String getShortDate(long date) {
		return DateUtils.formatDateTime(getGDActivity(), date * 1000L, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_ABBREV_MONTH);
	}

	private static String getFullDate(long date) {
		return DateUtils.formatDateTime(getGDActivity(), date * 1000L, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
	}

}
