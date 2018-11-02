package org.happysanta.gd.Menu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.text.Html;
import android.text.InputType;
import android.widget.EditText;
import org.happysanta.gd.Command;
import org.happysanta.gd.FileDialog;
import org.happysanta.gd.GDActivity;
import org.happysanta.gd.Game.GameView;
import org.happysanta.gd.Global;
import org.happysanta.gd.Levels.InvalidTrackException;
import org.happysanta.gd.Levels.Loader;
import org.happysanta.gd.R;
import org.happysanta.gd.Settings;
import org.happysanta.gd.Storage.HighScores;
import org.happysanta.gd.Storage.Level;
import org.happysanta.gd.Storage.LevelsManager;
import org.acra.ACRA;

import java.io.File;
import java.io.UnsupportedEncodingException;

import static org.happysanta.gd.Helpers.*;
import static org.happysanta.gd.Helpers.logDebug;

public class Menu
		implements MenuHandler {

	// private final static int SETTINGS_LENGTH = 21;
	// private final static boolean ENABLE_MANAGER = true;

	public MenuScreen currentMenu;
	public Level level;
	private HighScores currentScores;
	public int selectedLeague = 0;
	public boolean m_blZ = false;
	public boolean menuDisabled = false;
	// byte[] unlockedTracks = new byte[3];
	// byte leaguesUnlockedCount = 0;
	// byte levelsUnlockedCount = 0;
	int[] selectedTrack = {
			0, 0, 0
	};
	String[][] trackNames;
	String[] leagues = new String[3];
	String[] fullLeaguesList = new String[4];
	// private byte[] settings;
	// private SaveManager saveManager;
	private Command okCommand;
	private Command backCommand;
	private MenuScreen mainMenu;
	private MenuScreen playMenu;
	private MenuScreen optionsMenu;
	private MenuScreen aboutScreen;
	private MenuScreen helpMenu;
	private MenuScreen eraseScreen;
	private MenuScreen resetScreen;
	private MenuScreen finishedMenu;
	private MenuScreen ingameScreen;
	private SimpleMenuElementNew gameMenuItem;
	// private SimpleMenuElementNew optionsMenuItem;
	// private SimpleMenuElementNew helpMenuItem;
	private OptionsMenuElement levelSelector;
	private MenuScreen levelSelectorCurrentMenu;
	private OptionsMenuElement trackSelector;
	private MenuScreen trackSelectorCurrentMenu;
	private OptionsMenuElement leagueSelector;
	private MenuScreen leagueSelectorCurrentMenu;
	private MenuScreen highScoreMenu;
	private SimpleMenuElementNew highscoreItem;
	private ActionMenuElement startItem;
	private OptionsMenuElement perspectiveOptionItem;
	private OptionsMenuElement shadowsOptionItem;
	private OptionsMenuElement driverSpriteOptionItem;
	private OptionsMenuElement bikeSpriteOptionItem;
	private OptionsMenuElement inputOptionItem;
	private OptionsMenuElement lookAheadOptionItem;
	private OptionsMenuElement keyboardInMenuOptionItem;
	private OptionsMenuElement vibrateOnTouchOptionItem;
	private SimpleMenuElementNew clearHighscoreOptionItem;
	private SimpleMenuElementNew fullResetItem;
	// private ActionMenuElement yesAction;
	// private ActionMenuElement noAction;
	private SimpleMenuElementNew aboutMenuItem;
	private MenuScreen objectiveHelpScreen;
	private SimpleMenuElementNew objectiveHelpItem;
	private MenuScreen keysHelpScreen;
	private SimpleMenuElementNew keysHelpItem;
	private MenuScreen unlockingHelpScreen;
	private SimpleMenuElementNew unlockingHelpItem;
	private MenuScreen highscoreHelpScreen;
	private SimpleMenuElementNew highscoreHelpItem;
	private MenuScreen optionsHelpScreen;
	private SimpleMenuElementNew optionsHelpItem;
	private NameInputMenuScreen nameScreen;
	private ActionMenuElement continueAction;
	// private ActionMenuElement goToMainAction;
	// private ActionMenuElement exitMenuItem;
	private ActionMenuElement ingameRestartAction;
	private ActionMenuElement finishedRestartAction;
	private ActionMenuElement nextAction;
	// private ActionMenuElement okAction;
	private ActionMenuElement nameAction;
	private long lastTrackTime;
	private int m_ajI;
	private int m_atI;
	private String finishedTime;
	private byte[] nameChars;
	// private RecordStore recordStore;
	// private int m_afI = -1;
	private boolean settingsLoadedOK;
	private int levelIndex = 0;
	private int track = 0;
	private boolean leagueCompleted = false;
	private boolean m_SZ = false;
	private Object m_BObject;
	private String[] difficultyLevels = null; /* {
			"Easy", "Medium", "Hard"
	}; */
	// private long finishTime = 0L;
	/*private byte perspectiveOptionEnabled = 0;
	private byte shadowsOptionEnabled = 0;
	private byte driverSpriteOptionEnabled = 0;
	private byte bikerSpriteOptionEnabled = 0;
	private byte inputOptionValue = 0;
	private byte lookAheadOptionEnabled = 0;
	private byte keyboardInMenuEnabled = 1;
	private byte vibrateOnTouchEnabled = 1;*/
	// private byte selectedTrackIndex = 0;
	// private byte selectedLevel = 0;
	// private byte selectedLeague = 0;
	// private byte m_aTB = 0;
	// private byte m_arB = 0;
	private String[] onOffStrings = null;
	private String[] keysetStrings = null;
	// private EmptyLineMenuElement emptyLine;
	// private EmptyLineMenuElement emptyLineBeforeAction;
	// private AlertDialog alertDialog = null;
	private Paint bgPaint;
	public MenuScreen managerScreen;
	public InstalledLevelsMenuScreen managerInstalledScreen;
	public DownloadLevelsMenuScreen managerDownloadScreen;
	// private MenuScreen managerDownloadOptionsScreen;
	private SimpleMenuElementNew managerMenuItem;
	public MenuScreen levelScreen;
	// private SimpleMenuElement managerInstalledItem;
	// private SimpleMenuElement managerDownloadItem;
	// private HelmetRotationTask helmetRotationTask;
	// private Timer helmetRotationTimer;
	// public int helmetAngle;

	public Menu() {
		// Background color (instead of raster.png)
		bgPaint = new Paint();
		bgPaint.setColor(0x80FFFFFF);
	}

	public void load(int step) {
		GDActivity activity = getGDActivity();
		Loader loader = getLevelLoader();
		LevelsManager levelsManager = getLevelsManager();

		level = levelsManager.getCurrentLevel();

		switch (step) {
			case 1:
				m_BObject = new Object();
				nameChars = new byte[]{
						65, 65, 65 // A A A
				};
				onOffStrings = getStringArray(R.array.on_off);
				keysetStrings = getStringArray(R.array.keyset);
				difficultyLevels = getStringArray(R.array.difficulty);

				// saveManager = new SaveManager();
				lastTrackTime = -1L;
				m_ajI = -1;
				m_atI = -1;
				finishedTime = null;
				// settingsLoadedOK = false;
				/*settings = new byte[SETTINGS_LENGTH];
				for (int l = 0; l < SETTINGS_LENGTH; l++)
					settings[l] = -127;*/

				settingsLoadedOK = true;
				/*try {
					recordStore = RecordStore.openRecordStore(*//*(Loader.levelsFile != null ? Loader.levelsFile.getName().hashCode() : "") + *//*
							getLevelsManager().getCurrentId() + "_" + "GDTRStates", true);
					settingsLoadedOK = true;
					return;
				} catch (Exception _ex) {
					settingsLoadedOK = false;
				}*/

				break;

			case 2:
				// m_afI = -1;
				/*RecordEnumeration enumeration;
				try {
					enumeration = recordStore.enumerateRecords(null, null, false);
				} catch (*//*RecordStoreNotOpen*//*Exception _ex) {
					return;
				}
				if (enumeration.numRecords() > 0) {
					byte[] abyte0;
					try {
						abyte0 = enumeration.nextRecord();
						enumeration.reset();
						m_afI = enumeration.nextRecordId();
					} catch (Exception _ex) {
						return;
					}
					if (abyte0.length <= SETTINGS_LENGTH)
						System.arraycopy(abyte0, 0, settings, 0, abyte0.length);
					enumeration.destroy();
				}*/

				/*byte[] chars;
				if ((chars = readNameChars(16, (byte) -1)) != null && chars[0] != -1) {
					for (int i = 0; i < 3; i++)
						nameChars[i] = chars[i];

				}*/

				nameChars = Settings.getName();
				// if (nameChars[0] == 82 && nameChars[1] == 75 && nameChars[2] == 69) {
				if (isNameCheat(nameChars)) {
					// Unlock everything for cheat
					level.setUnlockedLeagues(3);
					level.setUnlockedLevels(2);
					level.setUnlocked(
							loader.names[0].length - 1,
							loader.names[1].length - 1,
							loader.names[2].length - 1
					);
					// logDebug(level);
					// leaguesUnlockedCount = 3;
					// levelsUnlockedCount = 2;
					/*unlockedTracks[0] = (byte) (loader.names[0].length - 1);
					unlockedTracks[1] = (byte) (loader.names[1].length - 1);
					unlockedTracks[2] = (byte) (loader.names[2].length - 1);*/
				} else if (level.isSettingsClear()) {
					level.setUnlockedLeagues(0);
					level.setUnlockedLevels(1);
					level.setUnlocked(0, 0, -1);
					// leaguesUnlockedCount = 0;
					// levelsUnlockedCount = 1;
					/*unlockedTracks[0] = 0;
					unlockedTracks[1] = 0;
					unlockedTracks[2] = -1;*/
				}
				break;

			case 3:
				// Load settings
				/*perspectiveOptionEnabled = readSetting(0, perspectiveOptionEnabled);
				shadowsOptionEnabled = readSetting(1, shadowsOptionEnabled);
				driverSpriteOptionEnabled = readSetting(2, driverSpriteOptionEnabled);
				bikerSpriteOptionEnabled = readSetting(3, bikerSpriteOptionEnabled);
				lookAheadOptionEnabled = readSetting(4, lookAheadOptionEnabled);

				keyboardInMenuEnabled = readSetting(13, keyboardInMenuEnabled);
				inputOptionValue = readSetting(14, inputOptionValue);*/
				// m_arB = readSetting(15, m_arB); // nonsense

				/*vibrateOnTouchEnabled = readSetting(19, keyboardInMenuEnabled);

				selectedLevel = readSetting(10, selectedLevel);
				selectedTrackIndex = readSetting(11, selectedTrackIndex);
				selectedLeague = readSetting(12, selectedLeague);*/

				// byte levelsSort = readSetting(20, (byte)0);

				DownloadLevelsMenuScreen.sort = Settings.getLevelsSort();


				levelIndex = level.getSelectedLevel();
				track = level.getSelectedTrack();

				if (nameChars[0] != 82 || nameChars[1] != 75 || nameChars[2] != 69) {
					//level.setUnlockedLeagues();
					/*leaguesUnlockedCount = readSetting(5, leaguesUnlockedCount);
					levelsUnlockedCount = readSetting(6, levelsUnlockedCount);
					for (int i = 0; i < 3; i++)
						unlockedTracks[i] = readSetting(7 + i, unlockedTracks[i]);*/
				}

				try {
					selectedTrack[level.getSelectedLevel()] = level.getSelectedTrack();
				} catch (ArrayIndexOutOfBoundsException _ex) {
					level.setSelectedLevel(0);
					level.setSelectedTrack(0);
					selectedTrack[level.getSelectedLevel()] = level.getSelectedTrack();
				}
				getLevelLoader().setPerspectiveEnabled(Settings.isPerspectiveEnabled());
				getLevelLoader().setShadowsEnabled(Settings.isShadowsEnabled());
				activity.physEngine._ifZV(Settings.isLookAheadEnabled());
				getGDView().setInputOption(Settings.getInputOption());
				// getGDView()._aZV(m_aTB == 0);
				getGDView()._aZV(true);
				String[] leaguesList = getStringArray(R.array.leagues);
				fullLeaguesList = getStringArray(R.array.leagues_full);
				trackNames = getLevelLoader().names;

				if (level.getUnlockedLeagues() < 3) {
					leagues = leaguesList;
				} else {
					leagues = fullLeaguesList;
				}

				selectedLeague = level.getSelectedLeague();

				break;

			case 4:
				mainMenu = new MenuScreen(getString(R.string.main), null);
				playMenu = new MenuScreen(getString(R.string.play), mainMenu);
				managerScreen = new MenuScreen(getString(R.string.mods), mainMenu);
				optionsMenu = new MenuScreen(getString(R.string.options), mainMenu);
				aboutScreen = new MenuScreen(getString(R.string.about) + " v" + getAppVersion(), mainMenu);
				helpMenu = new MenuScreen(getString(R.string.help), mainMenu);

				continueAction = new ActionMenuElement(getString(R.string._continue), ActionMenuElement.CONTINUE, this);
				nextAction = new ActionMenuElement(getString(R.string.track) + ": " + getLevelLoader().getLevelName(0, 1), ActionMenuElement.NEXT, this);
				ingameRestartAction = new ActionMenuElement(getString(R.string.restart) + ": " + getLevelLoader().getLevelName(0, 0), ActionMenuElement.RESTART, this);
				finishedRestartAction = new ActionMenuElement(getString(R.string.restart) + ": " + getLevelLoader().getLevelName(0, 0), ActionMenuElement.RESTART, this);

				/*nextAction = new ActionMenuElement(getString(R.string.track) + ": DEFAULT", ActionMenuElement.NEXT, this);
				ingameRestartAction = new ActionMenuElement(getString(R.string.restart) + ": DEFAULT", ActionMenuElement.RESTART, this);
				finishedRestartAction = new ActionMenuElement(getString(R.string.restart) + ": DEFAULT", ActionMenuElement.RESTART, this);*/

				highScoreMenu = new MenuScreen(getString(R.string.highscores), playMenu);
				finishedMenu = new MenuScreen(getString(R.string.finished), playMenu);
				ingameScreen = new MenuScreen(getString(R.string.ingame), playMenu);
				nameScreen = new NameInputMenuScreen(getString(R.string.enter_name), finishedMenu, nameChars);
				eraseScreen = new MenuScreen(getString(R.string.confirm_clear), optionsMenu);
				resetScreen = new MenuScreen(getString(R.string.confirm_reset), eraseScreen);

				gameMenuItem = new SimpleMenuElementNew(getString(R.string.play_menu), playMenu, this);
				managerMenuItem = new SimpleMenuElementNew(getString(R.string.mods), managerScreen, this);
				aboutMenuItem = new SimpleMenuElementNew(getString(R.string.about), aboutScreen, this);

				mainMenu.addItem(gameMenuItem);
				//if (ENABLE_MANAGER)
				mainMenu.addItem(new SimpleMenuElementNew(getString(R.string.mods), managerScreen, this));
				mainMenu.addItem(new SimpleMenuElementNew(getString(R.string.options), optionsMenu, this));
				mainMenu.addItem(new SimpleMenuElementNew(getString(R.string.help), helpMenu, this));
				mainMenu.addItem(aboutMenuItem);
				if (Global.DEBUG) {
					// mainMenu.addItem(createAction(ActionMenuElement.RESTART_WITH_NEW_LEVEL));
					mainMenu.addItem(createAction(ActionMenuElement.SEND_LOGS));
				}
				mainMenu.addItem(createAction(ActionMenuElement.EXIT));

				levelSelector = new OptionsMenuElement(getString(R.string.level), level.getSelectedLevel(), this, difficultyLevels, false, playMenu);
				trackSelector = new OptionsMenuElement(getString(R.string.track), selectedTrack[level.getSelectedLevel()], this, trackNames[level.getSelectedLevel()], false, playMenu);
				leagueSelector = new OptionsMenuElement(getString(R.string.league), selectedLeague, this, leagues, false, playMenu);
				try {
					trackSelector.setUnlockedCount(level.getUnlocked(level.getSelectedLevel()));
				} catch (ArrayIndexOutOfBoundsException _ex) {
					trackSelector.setUnlockedCount(0);
				}
				levelSelector.setUnlockedCount(level.getUnlockedLevels());
				leagueSelector.setUnlockedCount(level.getUnlockedLeagues());
				highscoreItem = new SimpleMenuElementNew(getString(R.string.highscores), highScoreMenu, this);
				highScoreMenu.addItem(createAction(ActionMenuElement.BACK));
				startItem = new ActionMenuElement(getString(R.string.start) + ">", this);
				playMenu.addItem(startItem);
				playMenu.addItem(levelSelector);
				playMenu.addItem(trackSelector);
				playMenu.addItem(leagueSelector);
				playMenu.addItem(highscoreItem);
				playMenu.addItem(createAction(ActionMenuElement.GO_TO_MAIN));
				// if (hasPointer)
				// 	softwareJoystickOptionItem = new ActionMenuElement("Software Joystick", m_aTB, this, onOffStrings, true, activity, optionsMenu, false);
				perspectiveOptionItem = new OptionsMenuElement(getString(R.string.perspective), Settings.isPerspectiveEnabled() ? 0 : 1, this, onOffStrings, true, optionsMenu);
				shadowsOptionItem = new OptionsMenuElement(getString(R.string.shadows), Settings.isShadowsEnabled() ? 0 : 1, this, onOffStrings, true, optionsMenu);
				driverSpriteOptionItem = new OptionsMenuElement(getString(R.string.driver_sprite), Settings.isDriverSpriteEnabled() ? 0 : 1, this, onOffStrings, true, optionsMenu);
				bikeSpriteOptionItem = new OptionsMenuElement(getString(R.string.bike_sprite), Settings.isBikeSpriteEnabled() ? 0 : 1, this, onOffStrings, true, optionsMenu);
				inputOptionItem = new OptionsMenuElement(getString(R.string.input), Settings.getInputOption(), this, keysetStrings, false, optionsMenu);
				lookAheadOptionItem = new OptionsMenuElement(getString(R.string.look_ahead), Settings.isLookAheadEnabled() ? 0 : 1, this, onOffStrings, true, optionsMenu);
				vibrateOnTouchOptionItem = new OptionsMenuElement(getString(R.string.vibrate_on_touch), Settings.isVibrateOnTouchEnabled() ? 0 : 1, this, onOffStrings, true, optionsMenu);
				keyboardInMenuOptionItem = new OptionsMenuElement(getString(R.string.keyboard_in_menu), Settings.isKeyboardInMenuEnabled() ? 0 : 1, this, onOffStrings, true, optionsMenu);
				clearHighscoreOptionItem = new SimpleMenuElementNew(getString(R.string.clear_highscore), eraseScreen, this);

				// if (hasPointer)
				//	optionsMenu.addItem(softwareJoystickOptionItem);
				optionsMenu.addItem(perspectiveOptionItem);
				optionsMenu.addItem(shadowsOptionItem);
				optionsMenu.addItem(driverSpriteOptionItem);
				optionsMenu.addItem(bikeSpriteOptionItem);
				optionsMenu.addItem(inputOptionItem);
				optionsMenu.addItem(lookAheadOptionItem);
				optionsMenu.addItem(vibrateOnTouchOptionItem);
				optionsMenu.addItem(keyboardInMenuOptionItem);
				optionsMenu.addItem(clearHighscoreOptionItem);
				optionsMenu.addItem(createAction(ActionMenuElement.BACK));

				// noAction = new ActionMenuElement(getString(R.string.no), 0, this, null, false, mainMenu, true);
				// yesAction = new ActionMenuElement(getString(R.string.yes), 0, this, null, false, mainMenu, true);
				fullResetItem = new SimpleMenuElementNew(getString(R.string.full_reset), resetScreen, this);
				eraseScreen.addItem(new TextMenuElement(getString(R.string.erase_text1)));
				eraseScreen.addItem(new TextMenuElement(getString(R.string.erase_text2)));
				eraseScreen.addItem(createEmptyLine(true));
				eraseScreen.addItem(createAction(ActionMenuElement.NO));
				eraseScreen.addItem(createAction(ActionMenuElement.YES));
				eraseScreen.addItem(fullResetItem);
				resetScreen.addItem(new TextMenuElement(getString(R.string.reset_text1)));
				resetScreen.addItem(new TextMenuElement(getString(R.string.reset_text2)));
				resetScreen.addItem(createEmptyLine(true));
				resetScreen.addItem(createAction(ActionMenuElement.NO));
				resetScreen.addItem(createAction(ActionMenuElement.YES));

				objectiveHelpScreen = new MenuScreen(getString(R.string.objective), helpMenu);
				objectiveHelpScreen.setIsTextScreen(true);
				objectiveHelpItem = new SimpleMenuElementNew(getString(R.string.objective), objectiveHelpScreen, this);
				objectiveHelpScreen.addItem(new TextMenuElement(Html.fromHtml(getString(R.string.objective_text))));
				objectiveHelpScreen.addItem(createAction(ActionMenuElement.BACK));

				keysHelpScreen = new MenuScreen(getString(R.string.keys), helpMenu);
				keysHelpScreen.setIsTextScreen(true);
				keysHelpItem = new SimpleMenuElementNew(getString(R.string.keys), keysHelpScreen, this);
				keysHelpScreen.addItem(new TextMenuElement(Html.fromHtml(getString(R.string.keyset_text))));
				keysHelpScreen.addItem(new ActionMenuElement(getString(R.string.back), ActionMenuElement.BACK, this));

				unlockingHelpScreen = new MenuScreen(getString(R.string.unlocking), helpMenu);
				unlockingHelpScreen.setIsTextScreen(true);
				unlockingHelpItem = new SimpleMenuElementNew(getString(R.string.unlocking), unlockingHelpScreen, this);
				unlockingHelpScreen.addItem(new TextMenuElement(Html.fromHtml(getString(R.string.unlocking_text))));
				unlockingHelpScreen.addItem(createAction(ActionMenuElement.BACK));

				highscoreHelpScreen = new MenuScreen(getString(R.string.highscores), helpMenu);
				highscoreHelpScreen.setIsTextScreen(true);
				highscoreHelpItem = new SimpleMenuElementNew(getString(R.string.highscores), highscoreHelpScreen, this);
				highscoreHelpScreen.addItem(new TextMenuElement(Html.fromHtml(getString(R.string.highscore_text))));
				highscoreHelpScreen.addItem(createAction(ActionMenuElement.BACK));

				optionsHelpScreen = new MenuScreen(getString(R.string.options), helpMenu);
				optionsHelpScreen.setIsTextScreen(true);
				optionsHelpItem = new SimpleMenuElementNew(getString(R.string.options), optionsHelpScreen, this);
				optionsHelpScreen.addItem(new TextMenuElement(Html.fromHtml(getString(R.string.options_text))));
				optionsHelpScreen.addItem(createAction(ActionMenuElement.BACK));

				helpMenu.addItem(objectiveHelpItem);
				helpMenu.addItem(keysHelpItem);
				helpMenu.addItem(unlockingHelpItem);
				helpMenu.addItem(highscoreHelpItem);
				helpMenu.addItem(optionsHelpItem);
				helpMenu.addItem(createAction(ActionMenuElement.BACK));

				aboutScreen.setIsTextScreen(true);
				aboutScreen.addItem(new TextMenuElement(Html.fromHtml(getString(R.string.about_text))));
				aboutScreen.addItem(createAction(ActionMenuElement.BACK));

				ingameScreen.addItem(continueAction);
				ingameScreen.addItem(ingameRestartAction);
				ingameScreen.addItem(new SimpleMenuElementNew(getString(R.string.options), optionsMenu, this));
				ingameScreen.addItem(new SimpleMenuElementNew(getString(R.string.help), helpMenu, this));
				ingameScreen.addItem(createAction(ActionMenuElement.PLAY_MENU));
				nameAction = new ActionMenuElement(getString(R.string.name) + " - " + new String(nameChars), 0, this);
				okCommand = new Command(getString(R.string.ok), 4, 1);
				backCommand = new Command(getString(R.string.back), 2, 1);
				setCurrentMenu(mainMenu, false);

				// LevelsManager
				managerInstalledScreen = new InstalledLevelsMenuScreen(getString(R.string.installed_mods), managerScreen);
				managerDownloadScreen = new DownloadLevelsMenuScreen(getString(R.string.download_mods), managerScreen);
				// managerDownloadOptionsScreen = new MenuScreen(getString(R.string.download_options), managerDownloadScreen);

				/*managerInstalledScreen.setIsLevelsList(true);
				managerDownloadScreen.setIsLevelsList(true);*/

				// LevelsManager
				managerScreen.addItem(new SimpleMenuElementNew(getString(R.string.download_mods), managerDownloadScreen, this));
				managerScreen.addItem(new SimpleMenuElementNew(getString(R.string.installed_mods), managerInstalledScreen, this));
				managerScreen.addItem(createEmptyLine(true));
				// managerScreen.addItem(new ActionMenuElement(getString(R.string.install_mrg), this));
				managerScreen.addItem(new ActionMenuElement(getString(R.string.install_mrg), ActionMenuElement.SELECT_FILE, this));

				// LevelsManager installed
				// managerInstalledScreen.addItem(new TextMenuElement(getString(R.string.installed_levels_text)));

				// Level screen
				levelScreen = new MenuScreen("", null);
				break;
		}
	}

	/*public void reloadLevels() {
		Loader loader = getLevelLoader();
		trackNames = loader.names;
		setUnlockedLevels();
	}*/

	protected ActionMenuElement createAction(int action) {
		int r;
		switch (action) {
			case ActionMenuElement.BACK:
				r = R.string.back;
				break;

			case ActionMenuElement.NO:
				r = R.string.no;
				break;

			case ActionMenuElement.YES:
				r = R.string.yes;
				break;

			case ActionMenuElement.EXIT:
				r = R.string.exit;
				break;

			case ActionMenuElement.OK:
				r = R.string.ok;
				break;

			case ActionMenuElement.PLAY_MENU:
				r = R.string.play_menu;
				break;

			case ActionMenuElement.GO_TO_MAIN:
				r = R.string.go_to_main;
				break;

			case ActionMenuElement.RESTART:
				r = R.string.restart;
				break;

			case ActionMenuElement.NEXT:
				r = R.string.next;
				break;

			case ActionMenuElement.CONTINUE:
				r = R.string._continue;
				break;

			case ActionMenuElement.LOAD:
				r = R.string.load_this_game;
				break;

			case ActionMenuElement.INSTALL:
				r = R.string.install_kb;
				break;

			case ActionMenuElement.DELETE:
				r = R.string.delete;
				break;

			case ActionMenuElement.RESTART_WITH_NEW_LEVEL:
				r = R.string.restart_with_new_level;
				break;

			case ActionMenuElement.SEND_LOGS:
				r = R.string.send_logs;
				break;

			default:
				return null;
		}

		return new ActionMenuElement(getString(r), action, this);
	}

	public EmptyLineMenuElement createEmptyLine(boolean beforeAction) {
		return new EmptyLineMenuElement(beforeAction ? 10 : 20);
	}

	public int getSelectedLevel() {
		return levelSelector.getSelectedOption();
	}

	public int getSelectedTrack() {
		return trackSelector.getSelectedOption();
	}

	// not sure about this name
	public boolean canStartTrack() {
		if (m_SZ) {
			m_SZ = false;
			return true;
		} else {
			return false;
		}
	}

	private void saveCompletedTrack() {
		// ATTENTION!!!
		// WHEN CHANGING THIS CODE, COPY-PASTE TO startTrack() !!!

		LevelsManager levelsManager = getLevelsManager();

		try {
			currentScores.saveHighScore(leagueSelector.getSelectedOption(), new String(nameChars, "UTF-8"), lastTrackTime);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			showAlert(getString(R.string.error), e.getMessage(), null);
		}
		// saveManager.write();
		levelsManager.saveHighScores(currentScores);

		leagueCompleted = false;

		finishedMenu.clear();
		finishedMenu.addItem(new TextMenuElement(Html.fromHtml("<b>" + getString(R.string.time) + "</b>: " + finishedTime)));

		System.gc();
		String[] as = currentScores.getScores(leagueSelector.getSelectedOption());
		for (int k = 0; k < as.length; k++)
			if (as[k] != null)
				finishedMenu.addItem(new TextMenuElement("" + (k + 1) + ". " + as[k]));

		byte byte0 = -1;
		// logDebug("trackSelector.getUnlockedCount() = " + trackSelector.getUnlockedCount());
		// logDebug("trackSelector.getSelectedOption() = " + trackSelector.getSelectedOption());

		// {
		// int unlockedTracks = trackSelector.getUnlockedCount();
		// int selectedTrack = trackSelector.getSelectedOption();
		// int selectedLevel = levelSelector.getSelectedOption();
		// int unlockedInLevel = level.getUnlocked(selectedLevel);

		// logDebug("unlockedTracks (trackSelector) = " + trackSelector.getUnlockedCount());
		// logDebug("selectedTrack (trackSelector) = " + selectedTrack);
		// logDebug("selectedLevel (levelSelector) = " + levelSelector.getSelectedOption());
		// logDebug("unlockedInLevel (level.getUnlocked()) = " + level.getUnlocked(levelSelector.getSelectedOption()));

		if (trackSelector.getUnlockedCount() >= trackSelector.getSelectedOption()) {
			trackSelector.setUnlockedCount(
					trackSelector.getSelectedOption() + 1 >= level.getUnlocked(levelSelector.getSelectedOption())
							? trackSelector.getSelectedOption() + 1
							: level.getUnlocked(levelSelector.getSelectedOption())
			);
			level.setUnlocked(levelSelector.getSelectedOption(),
					trackSelector.getUnlockedCount() >= level.getUnlocked(levelSelector.getSelectedOption())
							? trackSelector.getUnlockedCount()
							: level.getUnlocked(levelSelector.getSelectedOption())
			);
		}
		// }

		// Completed league
		if (trackSelector.getSelectedOption() == trackSelector.getOptionCount()) {
			leagueCompleted = true;
			switch (levelSelector.getSelectedOption()) {
				default:
					break;

				case 0:
					if (level.getUnlockedLeagues() < 1) {
						byte0 = 1;
						level.setUnlockedLeagues(1);
						// leaguesUnlockedCount = 1;
						leagueSelector.setUnlockedCount(level.getUnlockedLeagues());
					}
					break;

				case 1:
					if (level.getUnlockedLeagues() < 2) {
						byte0 = 2;
						level.setUnlockedLeagues(2);
						// leaguesUnlockedCount = 2;
						leagueSelector.setUnlockedCount(level.getUnlockedLeagues());
					}
					break;

				case 2:
					if (level.getUnlockedLeagues() < 3) {
						byte0 = 3;
						level.setUnlockedLeagues(3);
						leagueSelector.setOptions(fullLeaguesList);
						leagues = fullLeaguesList;
						leagueSelector.setUnlockedCount(level.getUnlockedLeagues());
					}
					break;
			}

			levelSelector.setUnlockedCount(levelSelector.getUnlockedCount() + 1);

			int newUnlocked = level.getUnlocked(levelSelector.getSelectedOption()) + 1,
					tracksCount = level.getCount(levelSelector.getSelectedOption());

			if (newUnlocked > tracksCount)
				newUnlocked = tracksCount;

			level.setUnlocked(levelSelector.getSelectedOption(), newUnlocked);
			if (level.getUnlocked(levelSelector.getUnlockedCount()) == -1) {
				level.setUnlocked(levelSelector.getUnlockedCount(), 0);
			}
			// if (unlockedTracks[levelSelector.getUnlockedCount()] == -1)
			//	unlockedTracks[levelSelector.getUnlockedCount()] = 0;
		} else {
			trackSelector.performAction(MenuScreen.KEY_RIGHT);
		}

		// int completedCount = _bbII(levelSelector.getSelectedOption());
		int completedCount = level.getUnlocked(levelSelector.getSelectedOption()); // TODO test
		finishedMenu.addItem(new TextMenuElement(Html.fromHtml(String.format(getString(R.string.tracks_completed_tpl),
				completedCount, trackNames[levelSelector.getSelectedOption()].length, difficultyLevels[levelSelector.getSelectedOption()]))));
		System.gc();

		if (!leagueCompleted) {
			ingameRestartAction.setText(getString(R.string.restart) + ": " + getLevelLoader().getLevelName(levelSelector.getSelectedOption(), trackSelector.getSelectedOption()));
			nextAction.setText(getString(R.string.next) + ": " + getLevelLoader().getLevelName(levelIndex, track + 1));

			// getLevelsManager().updateLevelSettings();
			saveAll();
		} else {
			// League completed
			if (levelSelector.getSelectedOption() < levelSelector.getOptionCount()) {
				levelSelector.setSelectedOption(levelSelector.getSelectedOption() + 1);
				trackSelector.setSelectedOption(0);
				trackSelector.setUnlockedCount(level.getUnlocked(levelSelector.getSelectedOption()));
			}

			if (byte0 != -1) {
				finishedMenu.addItem(new TextMenuElement(getString(R.string.congratulations) + leagues[byte0]));
				if (byte0 == 3)
					finishedMenu.addItem(new TextMenuElement(getString(R.string.enjoy)));
				showAlert(getString(R.string.league_unlocked), getString(R.string.league_unlocked_text) + leagues[byte0], null);

				// getLevelsManager().updateLevelSettings();
				saveAll();
			} else {
				boolean flag = true;
				for (int i1 = 0; i1 < 3; i1++)
					if (level.getUnlocked(i1) != getLevelLoader().names[i1].length - 1)
						flag = false;

				if (!flag)
					finishedMenu.addItem(new TextMenuElement(getString(R.string.level_completed_text)));
			}
		}

		if (!leagueCompleted)
			finishedMenu.addItem(nextAction);

		finishedRestartAction.setText(getString(R.string.restart) + ": " + getLevelLoader().getLevelName(levelIndex, track));
		finishedMenu.addItem(finishedRestartAction);
		finishedMenu.addItem(createAction(ActionMenuElement.PLAY_MENU));

		setCurrentMenu(finishedMenu, false);
	}

	//public void _hvV() {
	//	getGDActivity().m_di.postInvalidate();
	//}

	/* public int getGameViewScaledHeight() {
		return getGDView().getScaledHeight();
	}

	public int getGameViewScaledWidth() {
		return getGDView().getScaledWidth();
	} */

	public void showMenu(int k) {
		logDebug("[Menu] showMenu()");
		// k = 2;

		GDActivity gd = getGDActivity();
		GameView view = getGDView();
		Loader loader = getLevelLoader();

		m_blZ = false;
		menuDisabled = false;
		switch (k) {
			case 0: // Just started
				setCurrentMenu(mainMenu, false);
				gd.physEngine._casevV();
				m_SZ = true;
				break;

			case 1: // Ingame
				levelIndex = levelSelector.getSelectedOption();
				track = trackSelector.getSelectedOption();
				ingameRestartAction.setText(getString(R.string.restart) + ": " + loader.getLevelName(levelIndex, track));
				m_SZ = false;
				ingameScreen.resetHighlighted();
				setCurrentMenu(ingameScreen, false);
				break;

			case 2: // Finished
				// finishTime = System.currentTimeMillis();
				finishedMenu.clear();

				levelIndex = levelSelector.getSelectedOption();
				track = trackSelector.getSelectedOption();
				HighScores scores = getLevelsManager().getHighScores(levelSelector.getSelectedOption(), trackSelector.getSelectedOption());
				currentScores = scores;

				// saveManager.setTrack(levelSelector.getSelectedOption(), trackSelector.getSelectedOption());
				int place = scores.getPlace(leagueSelector.getSelectedOption(), lastTrackTime);
				finishedTime = getDurationString(lastTrackTime);

				if (place >= 0 && place <= 2) {
					HighScoreTextMenuElement placeText = new HighScoreTextMenuElement("");
					placeText.setText(getStringArray(R.array.finished_places)[place]);
					placeText.setMedal(true, place);

					finishedMenu.addItem(placeText);

					TextMenuElement h2 = new TextMenuElement(finishedTime);
					finishedMenu.addItem(h2);

					// finishedMenu.addItem(createEmptyLine(true));
					finishedMenu.addItem(createAction(ActionMenuElement.OK));
					finishedMenu.addItem(nameAction);

					setCurrentMenu(finishedMenu, false);
					m_blZ = false;
				} else {
					saveCompletedTrack();
				}
				break;

			default:
				setCurrentMenu(mainMenu, false);
				break;
		}

		long l1 = System.currentTimeMillis();
		view.drawTimer = false;
		long l4 = 0L;
		int i1 = 50;
		gd.physEngine._charvV();
		gd.gameToMenu();

		do {
			if (!gd.isMenuShown() || !gd.alive || currentMenu == null)
				break;

			if (gd.m_cZ) {
				while (gd.m_cZ) {
					// logDebug("[Menu] showMenu() waiting loop");
					if (!gd.alive || currentMenu == null) {
						break;
					}

					try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
					}
				}
			}
			if (gd.physEngine != null && gd.physEngine._gotovZ()) {
				int j1;
				if ((j1 = gd.physEngine._dovI()) != 0 && j1 != 4)
					try {
						gd.physEngine._doZV(true);
					} catch (NullPointerException e) {
					}
				gd.physEngine._charvV();
				// _hvV();
				long l2;
				if ((l2 = System.currentTimeMillis()) - l4 < (long) i1) {
					try {
						synchronized (m_BObject) {
							m_BObject.wait((long) i1 - (l2 - l4) >= 1L ? (long) i1 - (l2 - l4) : 1L);
						}
					} catch (InterruptedException e) {
					}
					l4 = System.currentTimeMillis();
				} else {
					l4 = l2;
				}
			} else {
				i1 = 50;
				long l3;
				if ((l3 = System.currentTimeMillis()) - l4 < (long) i1) {
					Object obj;
					try {
						synchronized (obj = new Object()) {
							obj.wait((long) i1 - (l3 - l4) >= 1L ? (long) i1 - (l3 - l4) : 1L);
						}
					} catch (InterruptedException e) {
					}
					l4 = System.currentTimeMillis();
				} else {
					l4 = l3;
				}
			}
		} while (true);

		logDebug("[Menu.showMenu] out loop");

		gd.m_forJ += System.currentTimeMillis() - l1;
		if (view != null)
			view.drawTimer = true;

		if (currentMenu == null && gd != null) {
			logDebug("[Menu.showMenu] currentMenu == null, set alive = false");
			gd.exiting = true;
			gd.alive = false;
		}
	}

	public synchronized void draw(Canvas g1) {
		if (currentMenu != null && !m_blZ) {
			getGDView().drawGame(g1);
			drawBackgroundColor(g1);
			// currentMenu.draw(g1);
		}
	}

	private void drawBackgroundColor(Canvas g1) {
		g1.drawRect(0, 0, getGDView().getScaledWidth(), getGDView().getScaledHeight(), bgPaint);
	}

	public void _tryIV(int k) {
		// logDebug("_tryIV k = " + k);
		if (getGDView().getGameAction(k) != 8)
			keyPressed(k);
	}

	public void keyPressed(int k) {
		if (currentMenu != null && !menuDisabled)
			switch (getGDView().getGameAction(k)) {
				case MenuScreen.KEY_UP: // up
					currentMenu.performAction(MenuScreen.KEY_UP);
					return;

				case MenuScreen.KEY_DOWN: // down
					currentMenu.performAction(MenuScreen.KEY_DOWN);
					return;

				case MenuScreen.KEY_FIRE: // fire
					currentMenu.performAction(MenuScreen.KEY_FIRE);
					return;

				case MenuScreen.KEY_RIGHT: // right
					currentMenu.performAction(MenuScreen.KEY_RIGHT);
					if (currentMenu == highScoreMenu) {
						selectedLeague++;
						if (selectedLeague > leagueSelector.getUnlockedCount())
							selectedLeague = leagueSelector.getUnlockedCount();
						showHighScoreMenu(selectedLeague);
						return;
					}
					break;

				case MenuScreen.KEY_LEFT: // left
					currentMenu.performAction(MenuScreen.KEY_LEFT);
					if (currentMenu != highScoreMenu)
						break;
					selectedLeague--;
					if (selectedLeague < 0)
						selectedLeague = 0;
					showHighScoreMenu(selectedLeague);
					break;
			}
	}

	public void onCommand(Command command) {
		if (command == okCommand) {
			ok();
		} else if (command == backCommand && currentMenu != null) {
			back();
		}
	}

	public void back() {
		if (currentMenu == ingameScreen) {
			getGDActivity().menuToGame();
			return;
		}
		if (currentMenu != null)
			setCurrentMenu(currentMenu.getNavTarget(), true);
	}

	public void ok() {
		if (currentMenu != null) {
			currentMenu.performAction(1);
			return;
		}
	}

	public MenuScreen getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(MenuScreen newMenu, boolean flag) {
		menuDisabled = false;
		GDActivity gd = getGDActivity();
		GameView view = getGDView();

		if (!Settings.isKeyboardInMenuEnabled()) {
			if (newMenu == nameScreen) {
				gd.showKeyboardLayout();
			} else {
				gd.hideKeyboardLayout();
			}
		}

		view.removeCommand(backCommand);
		if (newMenu != mainMenu && newMenu != finishedMenu && newMenu != null)
			view.addCommand(backCommand);

		if (newMenu == highScoreMenu) {
			selectedLeague = leagueSelector.getSelectedOption();
			showHighScoreMenu(selectedLeague);
		} else if (newMenu == finishedMenu) {
			// logDebug("it's finished!!!");
			nameChars = nameScreen.getChars();
			nameAction.setText(getString(R.string.name) + " - " + new String(nameChars));
		} else if (newMenu == playMenu) {
			trackSelector.setOptions(getLevelLoader().names[levelSelector.getSelectedOption()], false);
			if (currentMenu == trackSelectorCurrentMenu) {
				selectedTrack[levelSelector.getSelectedOption()] = trackSelector.getSelectedOption();
			}
			trackSelector.setUnlockedCount(level.getUnlocked(levelSelector.getSelectedOption()));
			trackSelector.setSelectedOption(selectedTrack[levelSelector.getSelectedOption()]);
		}
		if (newMenu == mainMenu || newMenu == playMenu && gd.physEngine != null)
			gd.physEngine._casevV();

		if (currentMenu != null)
			currentMenu.onHide(newMenu);

		currentMenu = newMenu;
		if (currentMenu != null) {
			gd.setMenu(currentMenu.getLayout());
			currentMenu.onShow();

			// getGDActivity().setMenu(currentMenu.getTable());
			// if (!isOnOffToggle) currentMenu.scrollUp();
		}

		// getGDActivity().physEngine._casevV();
		m_blZ = false;

		// */
	}

	public void showHighScoreMenu(int league) {
		HighScores highScores = getLevelsManager().getHighScores(levelSelector.getSelectedOption(), trackSelector.getSelectedOption());

		highScoreMenu.clear();
		highScoreMenu.setTitle(getString(R.string.highscores) + ": " + getLevelLoader().getLevelName(levelSelector.getSelectedOption(), trackSelector.getSelectedOption()));

		HighScoreTextMenuElement subtitle = new HighScoreTextMenuElement(Html.fromHtml(getString(R.string.league) + ": " + leagueSelector.getOptions()[league]));
		subtitle.setIsSubtitle(true);

		highScoreMenu.addItem(subtitle);

		String[] scores = highScores.getScores(league);

		for (int place = 0; place < scores.length; place++) {
			if (scores[place] == null)
				continue;

			HighScoreTextMenuElement h1 = new HighScoreTextMenuElement("" + (place + 1) + ". " + scores[place]);
			if (place == 0)
				h1.setMedal(true, 0);
			else if (place == 1)
				h1.setMedal(true, 1);
			else if (place == 2)
				h1.setMedal(true, 2);

			h1.setLayoutPadding(true);
			highScoreMenu.addItem(h1);
		}

		// saveManager.closeRecordStore();
		if (scores[0] == null)
			highScoreMenu.addItem(new TextMenuElement(getString(R.string.no_highscores)));

		highScoreMenu.addItem(createAction(ActionMenuElement.BACK));
		highScoreMenu.highlightElement();

		// System.gc();
	}

	public synchronized void destroy() {
		currentMenu = null;
	}

	public synchronized void saveAll() {
		logDebug("saveAll()");

		try {
			if (level != null) {
				Settings.setName(nameChars);

				level.setUnlockedLeagues(leagueSelector.getUnlockedCount());
				level.setUnlockedLevels(levelSelector.getUnlockedCount());

				level.setSelectedLevel(levelSelector.getSelectedOption());
				level.setSelectedTrack(trackSelector.getSelectedOption());
				level.setSelectedLeague(leagueSelector.getSelectedOption());

				getLevelsManager().updateLevelSettings();
			} else {
				logDebug("saveAll(): level == null");
			}
		} catch (Exception e) {
			logDebug("saveAll exception: " + e);
		}
	}

	public void handleAction(MenuElement item) {
		final GDActivity gd = getGDActivity();

		if (currentMenu == null) {
			return;
		}

		if (item == startItem)
			if (levelSelector.getSelectedOption() > levelSelector.getUnlockedCount() || trackSelector.getSelectedOption() > trackSelector.getUnlockedCount() || leagueSelector.getSelectedOption() > leagueSelector.getUnlockedCount()) {
				showAlert("GD Classic", getString(R.string.complete_to_unlock), null);
				return;
			} else {
				gd.physEngine._avV();
				startTrack(levelSelector.getSelectedOption(), trackSelector.getSelectedOption());
				gd.physEngine.setLeague(leagueSelector.getSelectedOption());
				m_SZ = true;
				gd.menuToGame();
				return;
			}

		if (item == vibrateOnTouchOptionItem) {
			Settings.setVibrateOnTouchEnabled(((OptionsMenuElement) item).getSelectedOption() == 0);
		}
		if (item == keyboardInMenuOptionItem) {
			boolean enabled = ((OptionsMenuElement) item).getSelectedOption() == 0;
			Settings.setKeyboardInMenuEnabled(enabled);
			if (enabled) gd.showKeyboardLayout();
			else gd.hideKeyboardLayout();
		}
		if (item == perspectiveOptionItem) {
			gd.physEngine._aZV(perspectiveOptionItem.getSelectedOption() == 0);
			getLevelLoader().setPerspectiveEnabled(perspectiveOptionItem.getSelectedOption() == 0);
			Settings.setPerspectiveEnabled(perspectiveOptionItem.getSelectedOption() == 0);
			return;
		}
		if (item == shadowsOptionItem) {
			getLevelLoader().setShadowsEnabled(shadowsOptionItem.getSelectedOption() == 0);
			Settings.setShadowsEnabled(shadowsOptionItem.getSelectedOption() == 0);
			return;
		}
		if (item == driverSpriteOptionItem) {
			if (driverSpriteOptionItem._charvZ()) {
				driverSpriteOptionItem.setSelectedOption(driverSpriteOptionItem.getSelectedOption() + 1);
			}
			Settings.setDriverSpriteEnabled(driverSpriteOptionItem.getSelectedOption() == 0);
		} else if (item == bikeSpriteOptionItem) {
			if (bikeSpriteOptionItem._charvZ()) {
				bikeSpriteOptionItem.setSelectedOption(bikeSpriteOptionItem.getSelectedOption() + 1);
			}
			Settings.setBikeSpriteEnabled(bikeSpriteOptionItem.getSelectedOption() == 0);
		} else {
			if (item == inputOptionItem) {
				if (inputOptionItem._charvZ())
					inputOptionItem.setSelectedOption(inputOptionItem.getSelectedOption() + 1);
				getGDView().setInputOption(inputOptionItem.getSelectedOption());
				Settings.setInputOption(inputOptionItem.getSelectedOption());
				return;
			}
			if (item == lookAheadOptionItem) {
				gd.physEngine._ifZV(lookAheadOptionItem.getSelectedOption() == 0);
				Settings.setLookAheadEnabled(lookAheadOptionItem.getSelectedOption() == 0);
				return;
			}
			if (item instanceof ActionMenuElement) {
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.RESTART_WITH_NEW_LEVEL) {
					LevelsManager manager = gd.levelsManager;
					long nextId = manager.getCurrentId() == 1 ? 2 : 1;
					gd.levelsManager.load(manager.getLeveL(nextId));
				}
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.SEND_LOGS) {
					gd.sendKeyboardLogs();
				}
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.SELECT_FILE) {
					installFromFileBrowse();
					return;
				}
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.YES) {
					if (currentMenu == eraseScreen) {
						getLevelsManager().clearHighScores();
						showAlert(getString(R.string.cleared), getString(R.string.cleared_text), null);
					} else if (currentMenu == resetScreen) {
						showAlert(getString(R.string.reset), getString(R.string.reset_text), new Runnable() {
							@Override
							public void run() {
								resetAll();
							}
						});
					}
					setCurrentMenu(currentMenu.getNavTarget(), false);
					return;
				}
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.NO) {
					setCurrentMenu(currentMenu.getNavTarget(), false);
					return;
				}
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.BACK) {
					setCurrentMenu(currentMenu.getNavTarget(), true);
					return;
				}
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.PLAY_MENU) {
					levelSelector.setSelectedOption(levelIndex);
					trackSelector.setUnlockedCount(level.getUnlocked(levelIndex));
					trackSelector.setSelectedOption(track);
					setCurrentMenu(currentMenu.getNavTarget(), false);
					return;
				}
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.GO_TO_MAIN) {
					setCurrentMenu(mainMenu, false);
					return;
				}
				if (((ActionMenuElement) item).getActionValue() == ActionMenuElement.EXIT) {
					getGDActivity().exiting = true;
					if (currentMenu != null) {
						setCurrentMenu(currentMenu.getNavTarget(), false);
					} else {
						setCurrentMenu(null, false);
					}
					return;
				}
			}

			if (item == ingameRestartAction || item == finishedRestartAction) {
				if (leagueSelector.getSelectedOption() <= leagueSelector.getUnlockedCount()) {
					levelSelector.setSelectedOption(levelIndex);
					trackSelector.setUnlockedCount(level.getUnlocked(levelIndex));
					trackSelector.setSelectedOption(track);
					gd.physEngine.setLeague(leagueSelector.getSelectedOption());
					m_SZ = true;
					gd.menuToGame();
					return;
				}
			} else {
				if (item == nextAction) {
					// if (!leagueCompleted)
					//	trackSelector.performAction(MenuScreen.KEY_RIGHT);
					startTrack(levelSelector.getSelectedOption(), trackSelector.getSelectedOption());
					gd.physEngine.setLeague(leagueSelector.getSelectedOption());
					// saveAll();
					// getLevelsManager().updateLevelSettings();
					m_SZ = true;
					gd.menuToGame();
					return;
				}
				if (item == continueAction) {
					// _hvV();
					gd.menuToGame();
					return;
				}
				if (item == nameAction) {
					nameScreen.resetCursorPosition();
					setCurrentMenu(nameScreen, false);
					return;
				}
				if (item instanceof ActionMenuElement && ((ActionMenuElement) item).getActionValue() == ActionMenuElement.OK) {
					saveCompletedTrack();
					return;
				}
				if (item == trackSelector) {
					if (trackSelector._charvZ()) {
						trackSelector.setUnlockedCount(level.getUnlocked(levelSelector.getSelectedOption()));
						trackSelector.update();
						trackSelectorCurrentMenu = trackSelector.getCurrentMenu();
						setCurrentMenu(trackSelectorCurrentMenu, false);
						// trackSelectorCurrentMenu._doIV(trackSelector.getSelectedOption());
					}
					selectedTrack[levelSelector.getSelectedOption()] = trackSelector.getSelectedOption();
					return;
				}
				if (item == levelSelector) {
					if (levelSelector._charvZ()) {
						levelSelectorCurrentMenu = levelSelector.getCurrentMenu();
						setCurrentMenu(levelSelectorCurrentMenu, false);
					}
					trackSelector.setOptions(getLevelLoader().names[levelSelector.getSelectedOption()], false);
					trackSelector.setUnlockedCount(level.getUnlocked(levelSelector.getSelectedOption()));
					trackSelector.setSelectedOption(selectedTrack[levelSelector.getSelectedOption()]);
					// trackSelector.update();
					// logDebug("update tracks ");
					return;
				}
				if (item == leagueSelector && leagueSelector._charvZ()) {
					leagueSelectorCurrentMenu = leagueSelector.getCurrentMenu();
					// leagueSelector.update();
					leagueSelector.setScreen(currentMenu);
					setCurrentMenu(leagueSelectorCurrentMenu, false);
					// leagueSelectorCurrentMenu._doIV(leagueSelector.getSelectedOption());
				}
			}
		}
	}

	protected void startTrack(int levelIndex, int trackIndex) {
		// ATTENTION!!!
		// WHEN CHANGING THIS CODE, COPY-PASTE TO saveCompletedTrack() !!!

		if (Global.ACRA_ENABLED) {
			ACRA.getErrorReporter().putCustomData("level_index:", String.valueOf(levelIndex));
			ACRA.getErrorReporter().putCustomData("track_index:", String.valueOf(trackIndex));
		}

		/*Menu _menu = null;
		_menu.back();*/

		try {
			getLevelLoader()._doIII(levelIndex, trackIndex);
		} catch (InvalidTrackException e) {
			showConfirm(getString(R.string.oops), getString(R.string.e_level_damaged), new Runnable() {
				@Override
				public void run() {
					if (trackSelector.getSelectedOption() + 1 < level.getCount(levelSelector.getSelectedOption())) {
						trackSelector.setUnlockedCount(trackSelector.getSelectedOption() + 1);
						level.setUnlocked(levelSelector.getSelectedOption(), trackSelector.getUnlockedCount());
					} else {
						switch (levelSelector.getSelectedOption()) {
							case 0:
								if (level.getUnlockedLeagues() < 1) {
									level.setUnlockedLeagues(1);
									leagueSelector.setUnlockedCount(level.getUnlockedLeagues());
								}
								break;

							case 1:
								if (level.getUnlockedLeagues() < 2) {
									level.setUnlockedLeagues(2);
									leagueSelector.setUnlockedCount(level.getUnlockedLeagues());
								}
								break;

							case 2:
								if (level.getUnlockedLeagues() < 3) {
									level.setUnlockedLeagues(3);
									leagueSelector.setOptions(fullLeaguesList);
									leagues = fullLeaguesList;
									leagueSelector.setUnlockedCount(level.getUnlockedLeagues());
								}
								break;
						}

						int newUnlocked = level.getUnlocked(levelSelector.getSelectedOption()) + 1,
								tracksCount = level.getCount(levelSelector.getSelectedOption());

						if (newUnlocked > tracksCount)
							newUnlocked = tracksCount;

						levelSelector.setUnlockedCount(levelSelector.getUnlockedCount() + 1);
						level.setUnlocked(levelSelector.getSelectedOption(), newUnlocked);
					}
				}
			}, null);
		}
	}

	public int _jvI() {
		int k = 0;
		if (driverSpriteOptionItem.getSelectedOption() == 0)
			k |= 2;
		if (bikeSpriteOptionItem.getSelectedOption() == 0)
			k |= 1;
		return k;
	}

	public void _intIV(int k) {
		bikeSpriteOptionItem.setSelectedOption(1);
		driverSpriteOptionItem.setSelectedOption(1);
		if ((k & 1) > 0)
			bikeSpriteOptionItem.setSelectedOption(0);
		if ((k & 2) > 0)
			driverSpriteOptionItem.setSelectedOption(0);
	}

	/*public int _ovI() {
		return levelSelector.getSelectedOption();
	}

	public int _nvI() {
		return trackSelector.getSelectedOption();
	}

	public int _lvI() {
		return leagueSelector.getSelectedOption();
	}*/

	public void setLastTrackTime(long l) {
		lastTrackTime = l;
	}

	/*private byte[] readNameChars(int pos, byte defaultValue) {
		switch (pos) {
			case 16: // '\020'
				byte[] abyte0 = new byte[3];
				for (int l = 0; l < 3; l++)
					abyte0[l] = settings[16 + l];

				if (abyte0[0] == -127)
					abyte0[0] = defaultValue;
				return abyte0;
		}
		return null;
	}

	private byte readSetting(int index, byte defaultValue) {
		if (settings[index] == -127)
			return defaultValue;
		else
			return settings[index];
	}

	private void saveNameChars(int pos, byte[] chars) {
		if (settingsLoadedOK && pos == 16) {
			for (int l = 0; l < 3; l++)
				settings[16 + l] = chars[l];

		}
	}*/

	private String getDurationString(long l) {
		m_ajI = (int) (l / 100L);
		m_atI = (int) (l % 100L);
		String s;
		if (m_ajI / 60 < 10)
			s = " 0" + m_ajI / 60;
		else
			s = " " + m_ajI / 60;
		if (m_ajI % 60 < 10)
			s = s + ":0" + m_ajI % 60;
		else
			s = s + ":" + m_ajI % 60;
		if (m_atI < 10)
			s = s + ".0" + m_atI;
		else
			s = s + "." + m_atI;
		return s;
	}

	/*private void setSetting(int k, byte byte0) {
		if (settingsLoadedOK)
			settings[k] = byte0;
	}*/

	private void resetAll() {
		Settings.resetAll();
		getLevelsManager().resetAllLevelsSettings();
		getLevelsManager().clearAllHighScores();

		getGDActivity().fullResetting = true;
		getGDActivity().destroyApp(true);
	}

	public void removeCommands() {
		getGDView().removeCommand(okCommand);
		getGDView().removeCommand(backCommand);
	}

	public void addCommands() {
		if (currentMenu != mainMenu && currentMenu != finishedMenu && currentMenu != null)
			getGDView().addCommand(backCommand);
		getGDView().addCommand(okCommand);
	}

	/*private int _bbII(int k) {
		String[] as = RecordStore.listRecordStores();
		if (saveManager == null || as == null)
			return 0;
		int l = 0;
		for (int i1 = 0; i1 < as.length; i1++)
			if (as[i1].startsWith("" + k))
				l++;

		return l;
	}*/

	/*public boolean isKeyboardEnabled() {
		return keyboardInMenuEnabled == 0;
	}*/

	// public boolean isVibrateOnTouchEnabled() {
	//	return vibrateOnTouchEnabled == 0;
	//}

	/*public void hideKeyboard(boolean firstRun) {
		if (!Settings.isKeyboardInMenuEnabled()) {
			getGDActivity().hideKeyboardLayout();
			// MenuScreen.setSize(getGDView().getScaledWidth(), getGDView().getScaledHeight());
		} else if (firstRun) {
			getGDActivity().showKeyboardLayout();
		}*//*else {
			// MenuScreen.setSize(getGDView().getScaledWidth(), getGDView().getScaledHeight() - getGDActivity().getButtonsLayoutHeight());
		}*//*
	}

	public void showKeyboard() {
		getGDActivity().showKeyboardLayout();
		// MenuScreen.setSize(getGDView().getScaledWidth(), getGDView().getScaledHeight() - getGDActivity().getButtonsLayoutHeight());
	}*/

	public void installFromFileBrowse() {
		if (!LevelsManager.isExternalStorageReadable()) {
			showAlert(getString(R.string.error), getString(R.string.e_external_storage_is_not_readable), null);
			return;
		}

		final GDActivity gd = getGDActivity();
		FileDialog fileDialog = new FileDialog(gd, Environment.getExternalStorageDirectory(), ".mrg");
		fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
			public void fileSelected(final File file) {
				final EditText input = new EditText(gd);
				input.setInputType(InputType.TYPE_CLASS_TEXT);

				AlertDialog.Builder alert = new AlertDialog.Builder(gd)
						.setTitle(getString(R.string.enter_levels_name_title))
						.setMessage(getString(R.string.enter_levels_name))
						.setView(input)
						.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								boolean ok = true;
								String name = input.getText().toString();
								if (name.equals("")) name = file.getName();

								ProgressDialog progressDialog = ProgressDialog.show(gd, getString(R.string.install), getString(R.string.installing), true);

								try {
									gd.levelsManager.install(file, name, "", 0);
								} catch (Exception e) {
									ok = false;
									e.printStackTrace();
									showAlert(getString(R.string.error), e.getMessage(), null);
								} finally {
									progressDialog.dismiss();
								}

								if (ok) {
									gd.levelsManager.showSuccessfullyInstalledDialog();
								}
							}
						})
						.setNegativeButton(getString(R.string.cancel), null);
				alert.show();
			}
		});
		fileDialog.showDialog();
	}

	public static boolean isNameCheat(byte[] chars) {
		return chars[0] == 82 && chars[1] == 75 && chars[2] == 69;
	}

}
