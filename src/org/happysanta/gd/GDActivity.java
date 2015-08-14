package org.happysanta.gd;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.happysanta.gd.API.*;
import org.happysanta.gd.Game.*;
import org.happysanta.gd.Levels.Loader;
import org.happysanta.gd.Menu.Views.MenuHelmetView;
import org.happysanta.gd.Menu.Views.MenuImageView;
import org.happysanta.gd.Menu.Views.MenuLinearLayout;
import org.happysanta.gd.Menu.Views.MenuTextView;
import org.happysanta.gd.Menu.Views.MenuTitleLinearLayout;
import org.happysanta.gd.Menu.Views.ObservableScrollView;
import org.happysanta.gd.Storage.LevelsManager;
import org.acra.util.Installation;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.happysanta.gd.Helpers.logDebug;

public class GDActivity extends Activity implements Runnable {

	public static GDActivity shared = null;
	public static final int MENU_TITLE_LAYOUT_TOP_PADDING = 25;
	public static final int MENU_TITLE_LAYOUT_BOTTOM_PADDING = 13;
	public static final int MENU_TITLE_LAYOUT_X_PADDING = 30;
	public static final int MENU_TITLE_FONT_SIZE = 30;
	public static final int GAME_MENU_BUTTON_LAYOUT_WIDTH = 40;
	public static final int GAME_MENU_BUTTON_LAYOUT_HEIGHT = 56;

	private static final long IMAGES_DELAY = 1000L;
	private static final long IMAGES_DELAY_DEBUG = 100L;

	public int m_longI = 0;

	private boolean wasPaused = false;
	private boolean wasStarted = false;
	private boolean wasDestroyed = false;
	private boolean restartingStarted = false;
	public boolean alive = false;
	public boolean m_cZ = true;
	private boolean menuShown = false;
	public boolean fullResetting = false;
	public boolean exiting = false;

	public GameView gameView = null;
	// public MenuView menuView = null;
	public Loader levelLoader;
	public Physics physEngine;
	public org.happysanta.gd.Menu.Menu menu;
	public boolean m_caseZ;
	public int m_nullI;
	public long m_forJ;
	// public long seconds;
	public long startedTime = 0;
	public long finishedTime = 0;
	public long pausedTime = 0;
	public long pausedTimeStarted = 0;
	public long m_byteJ;
	public boolean inited = false;
	public boolean m_ifZ;
	private Thread thread;
	private MenuImageView menuBtn;
	public MenuTitleLinearLayout titleLayout;
	public ObservableScrollView scrollView;
	private FrameLayout frame;
	private MenuLinearLayout menuLayout;
	private KeyboardController keyboardController;
	private boolean isNormalAndroid = true;
	private boolean buttonCoordsCalculated = false;
	public TextView menuTitleTextView;
	private boolean menuReady = false;
	private ArrayList<Command> commands = new ArrayList<Command>();
	private MenuLinearLayout keyboardLayout;
	private MenuTextView portedTextView;
	private int buttonHeight = 60;
	public LevelsManager levelsManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		shared = this;

		if (Helpers.isSDK10OrLower()) {
			isNormalAndroid = false;
		}

		final GDActivity self = this;
		Request request = API.getNotifications(Global.INSTALLED_FROM_APK, new ResponseHandler() {
			@Override
			public void onResponse(final Response apiResponse) {
				try {
					final NotificationsResponse response = new NotificationsResponse(apiResponse);
					if (!response.isEmpty()) {
						final Runnable onOk = new Runnable() {
							@Override
							public void run() {
								if (response.hasURL()) {
									String url = response.getURL();
									try {
										Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
										startActivity(browserIntent);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						};

						if (response.hasTwoButtons()) {
							AlertDialog.Builder alert = new AlertDialog.Builder(self)
									.setTitle(response.getTitle())
									.setMessage(response.getMessage())
									.setPositiveButton(response.getOKButton(), new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											onOk.run();
										}
									})
									.setNegativeButton(response.getCancelButton(), new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
										}
									})
									.setOnCancelListener(new DialogInterface.OnCancelListener() {
										@Override
										public void onCancel(DialogInterface dialog) {

										}
									});
							alert.show();
						} else {
							AlertDialog alertDialog = new AlertDialog.Builder(self)
									.setTitle(response.getTitle())
									.setMessage(response.getMessage())
									.setPositiveButton(response.getOKButton(), new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											onOk.run();
										}
									})
									.setOnCancelListener(new DialogInterface.OnCancelListener() {
										@Override
										public void onCancel(DialogInterface dialog) {
										}
									})
									.create();
							alertDialog.show();
						}
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}

			@Override
			public void onError(APIException error) {

			}
		});

		if (true) {
			gameView = new GameView(this);

			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

			scrollView = new ObservableScrollView(this);
			scrollView.setBackgroundColor(0x00ffffff);
			scrollView.setFillViewport(true);
			scrollView.setOnScrollListener(new ObservableScrollView.OnScrollListener() {
				@Override
				public void onScroll(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
					if (isMenuShown() && menu != null && menu.currentMenu != null) {
						int h = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
						double p = 100.0 * y / h;
						if (p > 100f)
							p = 100f;

						menu.currentMenu.onScroll(p);
					}
				}
			});
			scrollView.setVisibility(View.GONE);

			frame = new FrameLayout(this);
			frame.setBackgroundColor(0xffffffff);

			titleLayout = new MenuTitleLinearLayout(this);
			titleLayout.setBackgroundColor(0x00ffffff);
			titleLayout.setGravity(Gravity.TOP);
			titleLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			titleLayout.setPadding(Helpers.getDp(MENU_TITLE_LAYOUT_X_PADDING), Helpers.getDp(MENU_TITLE_LAYOUT_TOP_PADDING), Helpers.getDp(MENU_TITLE_LAYOUT_X_PADDING), Helpers.getDp(MENU_TITLE_LAYOUT_BOTTOM_PADDING));

			menuTitleTextView = new TextView(this);
			menuTitleTextView.setText(getString(R.string.main));
			menuTitleTextView.setTextColor(0xff000000);
			menuTitleTextView.setTypeface(Global.robotoCondensedTypeface);
			menuTitleTextView.setTextSize(MENU_TITLE_FONT_SIZE);
			menuTitleTextView.setLineSpacing(0f, 1.1f);
			menuTitleTextView.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT
			));
			menuTitleTextView.setVisibility(android.view.View.GONE);

			titleLayout.addView(menuTitleTextView);

			scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

			// Keyboard
			String[] buttonResources = {
					"btn_br", "btn_br", "btn_b",
					"btn_br", "btn_br", "btn_b",
					"btn_r", "btn_r", "btn_n"
			};
			if (getString(R.string.screen_type).equals("tablet")) {
				buttonHeight = 85;
			} else if (Global.density < 1.5) {
				buttonHeight = 55;
			}

			keyboardLayout = new MenuLinearLayout(this, true);
			keyboardLayout.setOrientation(LinearLayout.VERTICAL);

			keyboardController = new KeyboardController(this);

			for (int i = 0; i < 3; i++) {
				LinearLayout row = new LinearLayout(this);
				row.setPadding(Helpers.getDp(KeyboardController.PADDING), i == 0 ? Helpers.getDp(KeyboardController.PADDING) : 0, Helpers.getDp(KeyboardController.PADDING), 0);
				row.setOrientation(LinearLayout.HORIZONTAL);
				row.setBackgroundColor(0xc6ffffff);
				for (int j = 0; j < 3; j++) {
					LinearLayout btn = new LinearLayout(this);
					TextView btnText = new TextView(this);
					btnText.setText(String.valueOf(i * 3 + j + 1));
					btnText.setTextColor(0xff000000);
					btnText.setTextSize(17);
					btn.setBackgroundResource(getResources().getIdentifier(buttonResources[i * 3 + j], "drawable", getPackageName()));
					btn.addView(btnText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					btn.setGravity(Gravity.CENTER);
					btn.setWeightSum(1);

					row.addView(btn, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Helpers.getDp(buttonHeight), 1));

					keyboardController.addButton(btn, j, i);
				}

				keyboardLayout.addView(row);
			}

			keyboardLayout.setGravity(Gravity.BOTTOM);
			keyboardLayout.setPadding(0, 0, 0, Helpers.getDp(KeyboardController.PADDING));
			keyboardLayout.setOnTouchListener(keyboardController);
			keyboardLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));

			hideKeyboardLayout();

			menuBtn = new MenuImageView(this);
			menuBtn.setImageResource(R.drawable.ic_menu);
			menuBtn.setScaleType(ImageView.ScaleType.CENTER);
			menuBtn.setLayoutParams(new FrameLayout.LayoutParams(Helpers.getDp(GAME_MENU_BUTTON_LAYOUT_WIDTH), Helpers.getDp(GAME_MENU_BUTTON_LAYOUT_HEIGHT), Gravity.RIGHT | Gravity.TOP));
			menuBtn.setOnClickListener(new android.view.View.OnClickListener() {
				@Override
				public void onClick(android.view.View v) {
					gameView.showMenu();
				}
			});
			menuBtn.setVisibility(android.view.View.GONE);

			menuLayout = new MenuLinearLayout(this);
			menuLayout.setOrientation(LinearLayout.VERTICAL);
			menuLayout.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT
			));

			portedTextView = new MenuTextView(this);
			portedTextView.setTypeface(Global.robotoCondensedTypeface);
			portedTextView.setTextSize(15);
			portedTextView.setLineSpacing(0f, 1.2f);
			portedTextView.setText(Html.fromHtml(getString(R.string.ported_text)));
			portedTextView.setGravity(Gravity.CENTER);
			portedTextView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
			portedTextView.setPadding(0, 0, 0, Helpers.getDp(10));

			menuLayout.addView(titleLayout);
			menuLayout.addView(scrollView);

			frame.addView(menuLayout);
			frame.addView(keyboardLayout);
			frame.addView(menuBtn);
			frame.addView(portedTextView);

			gameView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, 1));
			frame.addView(gameView, 0);

			setContentView(frame);

			gameView._doIV(1); // flag for 1st image, as I understand..
			thread = null;
			m_caseZ = false;
			m_nullI = 2;
			m_forJ = 0;
			m_byteJ = 0;
			inited = false;
			m_ifZ = false;
			wasDestroyed = false;
			restartingStarted = false;

			frame.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
				@Override
				public boolean onPreDraw() {
					frame.getViewTreeObserver().removeOnPreDrawListener(this);
					// setButtonsLayoutHeight();
					doStart();
					return true;
				}
			});



		/* gameView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				gameView.getViewTreeObserver().removeOnPreDrawListener(this);
				doStart();
				return true;
			}
		}); */

			/* alive = true;
			m_cZ = false;

			Thread.currentThread().setName("main_thread");

			if (thread == null) {
				thread = new Thread(this);
				thread.setName("game_thread");
			} */

			/*synchronized (thread) {
				thread.start();
				try {
					thread.wait();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}

			for (int i = 1; i <= 4; i++) {
				menu.load(i);
			}

			wasStarted = true;*/
		}
	}

	protected void doStart() {
		alive = true;
		m_cZ = false;

		Thread.currentThread().setName("main_thread");

		if (thread == null) {
			thread = new Thread(this);
			thread.setName("game_thread");
			thread.start();
		}

		wasStarted = true;
	}

	// protected boolean viewDone = false;

	@Override
	public void run() {
		Helpers.logDebug("!!! run()");
		long l1;

		if (!inited) {
			Helpers.logDebug("run(): initing");
			try {
				// Game view
				/* gameView = new GameView(shared);
				gameView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, 1));
				frame.addView(gameView, 0); */

				/* gameView._doIV(1);
				thread = null;
				m_caseZ = false;
				m_nullI = 2;
				m_forJ = 0L;
				seconds = 0L;
				m_byteJ = 0L;
				inited = false;
				m_ifZ = false; */

				long imageDelay = Global.DEBUG ? IMAGES_DELAY_DEBUG : IMAGES_DELAY; // delay of first image
				Thread.yield();

				/*gameView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						gameView.getViewTreeObserver().removeOnPreDrawListener(this);
						viewDone = true;
						logDebug("gameView is ready");
						//doStart();
						return true;
					}
				});

				logDebug("before while..");
				while (!viewDone) {
					// Thread.sleep(1);
				}
				logDebug("after while..");*/

				// do we really need this?!
				/*while (gameView == null || gameView.getParent() == null) {
					try {
						Thread.sleep(100);
					} catch (Exception x) {}
				}*/

				MenuHelmetView.clearStaticFields();

				levelsManager = new LevelsManager();
				try {
					levelLoader = new Loader(levelsManager.getCurrentLevelsFile());
				} catch (IOException e) {
					e.printStackTrace();
					// logDebug("Reset level id now");
					levelsManager.resetId();
					levelsManager.reload();

					levelLoader = new Loader(levelsManager.getCurrentLevelsFile());
				}

				physEngine = new Physics(levelLoader);
				gameView.setPhysicsEngine(physEngine);

				// logDebug(levelsManager.getLevelsStat());
				sendStats();

				/* synchronized (Thread.currentThread()) {
					Thread.currentThread().notify();
				} */
				menu = new org.happysanta.gd.Menu.Menu();
				// menu = null;
				// menu.hideKeyboard();
				for (int i = 1; i <= 4; i++) {
					menu.load(i);
				}

				// menu = new Menu();
				// menu.hideKeyboard();

				/*menu.load(1);
				menu.load(2);
				menu.load(3);

				Runnable createMenuRunnable = new Runnable() {
					@Override
					public void run() {
						menu.load(4);
						synchronized (this) {
							notify();
						}
					}
				};

				synchronized (createMenuRunnable) {
					// logDebug("before runOnUiThread()");
					runOnUiThread(createMenuRunnable);
					try {
						// logDebug("before wait()");
						createMenuRunnable.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/

				portedTextView.setVisibility(View.VISIBLE);

				gameView.setMenu(menu);
				gameView._doIIV(-50, 150);
				setMode(1);

				// Show first image
				Helpers.logDebug("show first image");
				long l2;
				for (; imageDelay > 0L; imageDelay -= l2)
					l2 = _avJ();

				// Show second image
				portedTextView.setVisibility(View.GONE);
				Helpers.logDebug("show second image");
				imageDelay = Global.DEBUG ? IMAGES_DELAY_DEBUG : IMAGES_DELAY;
				gameView._doIV(2);
				long l3;
				for (long l4 = imageDelay; l4 > 0L; l4 -= l3)
					l3 = _avJ();

				while (m_longI < 10)
					_avJ();

				gameView._doIV(0);
				Helpers.logDebug("images DONE");
				inited = true;

			} catch (Exception _ex) {
				_ex.printStackTrace();
				// Log.w("GDTR", _ex);
				throw new RuntimeException(_ex);
			}
		}

		// logDebug("inited, continue");

		restart(false);
		// logDebug("showMenu() now");

		/*if (menu != null) */
		menu.showMenu(0);
		if (/*menu != null && */menu.canStartTrack())
			restart(true);
		l1 = 0L;

		// try {
		Helpers.logDebug("start main loop");
		while (alive) {
			/*if (!alive) {
				logDebug("!alive");
				break;
			}*/

			// try {
			if (physEngine._bytevI() != menu._jvI()) {
				int j = gameView._intII(menu._jvI());
				physEngine._doIV(j);
				menu._intIV(j);
			}

			if (menuShown) {
				menu.showMenu(1);
				if (menu.canStartTrack())
					restart(true);
			}

			for (int i1 = m_nullI; i1 > 0 && alive; i1--) {
			/* if (m_ifZ)
				seconds += 20L; */
				if (m_forJ == 0L)
					m_forJ = System.currentTimeMillis();
				int k = 0;
				if (/*physEngine != null && */(k = physEngine._dovI()) == 3 && m_byteJ == 0L) {
					m_byteJ = System.currentTimeMillis() + 3000L;
					gameView.showInfoMessage(getString(R.string.crashed), 3000);
					//m_di.postInvalidate();
					//m_di.serviceRepaints();
				}
				if (m_byteJ != 0L && m_byteJ < System.currentTimeMillis())
					restart(true);
				if (k == 5) {
					finishedTime = System.currentTimeMillis();
					gameView.showInfoMessage(getString(R.string.crashed), 3000);
					//m_di.postInvalidate();
					//m_di.serviceRepaints();
					try {
						long l2 = 1000L;
						if (m_byteJ > 0L)
							l2 = Math.min(m_byteJ - System.currentTimeMillis(), 1000L);
						if (l2 > 0L)
							Thread.sleep(l2);
					} catch (InterruptedException _ex) {
					}
					restart(true);
				} else if (k == 4) {
					// logDebug("k == 4");
					m_forJ = 0;
					// seconds = 0;
					startedTime = 0;
					finishedTime = 0;
					pausedTime = 0;
				} else if (k == 1 || k == 2) {
					finishedTime = System.currentTimeMillis();
					// logDebug("game-run: k = " + k);
				/* if (k == 2)
					seconds -= 10L; */
					goalLoop();
					// menu.setLastTrackTime(seconds / 10L);
					menu.setLastTrackTime((finishedTime - startedTime) / 10);
					menu.showMenu(2);

					if (menu.canStartTrack())
						restart(true);
					if (!alive) {
						Helpers.logDebug("!alive (2)");
						break;
					}
				}
				m_ifZ = k != 4;
				if (m_ifZ && startedTime == 0) {
					startedTime = System.currentTimeMillis();
				}
			}

			if (!alive) {
				Helpers.logDebug("!alive (3)");
				break;
			}

			//try {
			/*if (physEngine != null)*/
			physEngine._charvV();
			long l;
			if ((l = System.currentTimeMillis()) - l1 < 30L) {
				try {
					synchronized (this) {
						wait(Math.max(30L - (l - l1), 1L));
					}
				} catch (InterruptedException interruptedexception) {
				}
				l1 = System.currentTimeMillis();
			} else {
				l1 = l;
			}
			//m_di.postInvalidate();
		/*} catch (Exception exception) {
			exception.printStackTrace();
		}*/
		}
		// } catch (Exception e) {
		//	e.printStackTrace();
		//}

		Helpers.logDebug("game thread finished, destroyApp(false) next");

		// finish();
		destroyApp(false);
		// return;
	}

	@Override
	protected void onResume() {
		Helpers.logDebug("@@@ [GDActivity \"+hashCode()+\"] onResume()");
		super.onResume();
		Helpers.logDebug("[GDActivity \"+hashCode()+\"] onResume(), inited = " + inited);
		if (wasPaused && wasStarted) {
			// logDebug("onResume(): wasPaused && wasResumed");
			// start();
			m_cZ = false;
			wasPaused = false;

			// Menu.HelmetRotation.start();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		Helpers.logDebug("@@@ [GDActivity " + hashCode() + "] onPause()");

		wasPaused = true;
		m_cZ = true;
		Helpers.logDebug("inited : " + inited);
		if (!menuShown && inited)
			gameToMenu();

		// menu.helmetRotationStop();
		// Menu.HelmetRotation.stop();
		// if (menu != null)
		// 	menu.saveAll();
		// levelsManager.updateLevelSettings();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Helpers.logDebug("@@@ [GDActivity " + hashCode() + "] onDestroy()");
		destroyApp(false);
	}

	@Override
	protected void onStop() {
		super.onStop();
		Helpers.logDebug("@@@ [GDActivity " + hashCode() + "] onStop()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Helpers.logDebug("@@@ [GDActivity " + hashCode() + "] onStart()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Helpers.logDebug("@@@ [GDActivity " + hashCode() + "] onRestart()");
	}

	@Override
	public void onBackPressed() {
		if (gameView != null && menu != null && inited) {
			if (menuShown)
				menu.back();
			else
				gameView.showMenu();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		menu.clear();
		int id = 1;
		for (Command cmd : commands) {
			MenuItem item = menu.add(0, id, 0, cmd.title);
			id++;
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		gameView.commandAction(commands.get(item.getItemId() - 1));
		return true;
	}

	public void setMode(int j) {
		physEngine._byteIV(j);
	}

	public boolean isMenuShown() {
		return menuShown;
	}

	// @UiThread
	public void setMenu(final LinearLayout layout) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				scrollView.removeAllViews();
				if (layout.getParent() != null) {
					((ViewManager) layout.getParent()).removeView(layout);
				}
				scrollView.addView(layout);
			}
		});
	}

	public void goalLoop() {
		if (!alive) {
			return;
		}

		long l1 = 0L;
		if (!physEngine.m_NZ)
			gameView.showInfoMessage(getString(R.string.wheelie), 1000);
		else
			gameView.showInfoMessage(getString(R.string.finished1), 1000);
		for (long l2 = System.currentTimeMillis() + 1000L; l2 > System.currentTimeMillis(); gameView.postInvalidate()) {
			if (menuShown) {
				//m_di.postInvalidate();
				return;
			}
			for (int j = m_nullI; j > 0; j--)
				if (physEngine._dovI() == 5)
					try {
						long l3;
						if ((l3 = l2 - System.currentTimeMillis()) > 0L)
							Thread.sleep(l3);
						return;
					} catch (InterruptedException _ex) {
						return;
					}

			physEngine._charvV();
			long l;
			if ((l = System.currentTimeMillis()) - l1 < 30L) {
				try {
					synchronized (this) {
						wait(Math.max(30L - (l - l1), 1L));
					}
				} catch (InterruptedException interruptedexception) {
				}
				l1 = System.currentTimeMillis();
			} else {
				l1 = l;
			}
		}
	}

	public void restart(boolean flag) {
		// logDebug("[GDActivity] restart()");
		if (!alive) {
			return;
		}

		physEngine._doZV(true);
		// logDebug("[GDActivity] restart(): 1");
		m_forJ = 0;
		// seconds = 0;
		startedTime = 0;
		finishedTime = 0;
		pausedTime = 0;
		m_byteJ = 0;
		if (flag)
			gameView.showInfoMessage(levelLoader.getLevelName(menu.getSelectedLevel(), menu.getSelectedTrack()), 3000);
		// logDebug("[GDActivity] restart(): 2");
		gameView._casevV();
		// logDebug("[GDActivity] restart(): 3");
	}

	public void destroyApp(final boolean restart) {
		if (wasDestroyed) {
			return;
		}

		wasDestroyed = true;
		alive = false;

		final GDActivity self = this;

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Helpers.logDebug("[GDActivity " + self.hashCode() + "] destroyApp()");
				inited = false;
				m_caseZ = true;

				synchronized (gameView) {
					destroyResources();

					if (exiting || restart) {
						finish();
					}

					if (restart) {
						doRestartApp();
					}
				}
			}
		});
	}

	private void destroyResources() {
		Helpers.logDebug("[GDActivity " + hashCode() + "]  destroyResources()");

		// if (thread != null) thread.interrupt();
		if (gameView != null) gameView.destroy();

		menuShown = false;
		if (menu != null) {
			if (!fullResetting) menu.saveAll();
			menu.destroy();
		}

		if (levelsManager != null) levelsManager.closeDataSource();
	}

	public int getButtonsLayoutHeight() {
		return buttonHeight * 3 + KeyboardController.PADDING * 2;
	}

	// @UiThread
	public void hideKeyboardLayout() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				keyboardLayout.setVisibility(android.view.View.GONE);

				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
				params.setMargins(0, 0, 0, 0);
				scrollView.setLayoutParams(params);
			}
		});
	}

	// @UiThread
	public void showKeyboardLayout() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				keyboardLayout.setVisibility(android.view.View.VISIBLE);

				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
				params.setMargins(0, 0, 0, Helpers.getDp(getButtonsLayoutHeight()));
				scrollView.setLayoutParams(params);
			}
		});
	}

	public void addCommand(Command cmd) {
		if (!commands.contains(cmd))
			commands.add(cmd);
		if (isNormalAndroid)
			invalidateOptionsMenu();
	}

	public void removeCommand(Command cmd) {
		commands.remove(cmd);
		if (isNormalAndroid)
			invalidateOptionsMenu();
	}

	public void gameToMenu() {
		Helpers.logDebug("gameToMenu()");

		if (gameView == null) {
			Helpers.logDebug("gameToMenu(): gameView == null");
			return;
		}

		pausedTimeStarted = System.currentTimeMillis();

		gameView.removeMenuCommand();
		menuShown = true;
		// menu.helmetRotationStart();
		// Menu.HelmetRotation.start();
		if (menu != null)
			menu.addCommands();

		// hideKeyboardLayout();
		if (!Settings.isKeyboardInMenuEnabled())
			hideKeyboardLayout();
		else
			showKeyboardLayout();

		gameToMenuUpdateUi();
	}

	// @UiThread
	protected void gameToMenuUpdateUi() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				menuBtn.setVisibility(android.view.View.GONE);
				menuTitleTextView.setVisibility(android.view.View.VISIBLE);
				scrollView.setVisibility(android.view.View.VISIBLE);
			}
		});
	}

	public void menuToGame() {
		Helpers.logDebug("menuToGame()");

		if (pausedTimeStarted > 0 && startedTime > 0) {
			pausedTime += (System.currentTimeMillis() - pausedTimeStarted);
			pausedTimeStarted = 0;
		}

		if (menu != null) menu.removeCommands();
		menuShown = false;
		// menu.helmetRotationStop();
		// Menu.HelmetRotation.stop();
		if (gameView != null) gameView.addMenuCommand();
		showKeyboardLayout();
		// menu.showKeyboard();

		menuToGameUpdateUi();

		keyboardController.clearLogBuffer();
	}

	// @UiThread
	protected void menuToGameUpdateUi() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				menuBtn.setVisibility(android.view.View.VISIBLE);
				menuTitleTextView.setVisibility(android.view.View.GONE);
				scrollView.setVisibility(android.view.View.GONE);

				// Clear menu
				scrollView.removeAllViews();
				menuTitleTextView.setText("");
				menu.menuDisabled = true;
				// menu.currentMenu = null;
			}
		});
	}

	public void scrollTextMenuUp() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int y = scrollView.getScrollY();
				scrollView.scrollTo(0, y - Helpers.getDp(20));
			}
		});
	}

	public void scrollTextMenuDown() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int y = scrollView.getScrollY();
				scrollView.scrollTo(0, y + Helpers.getDp(20));
			}
		});
	}

	public void scrollToView(final View view) {
		final GDActivity gd = Helpers.getGDActivity();
		final ObservableScrollView scrollView = gd.scrollView;

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Rect scrollBounds = new Rect();
				scrollView.getHitRect(scrollBounds);

				if (!view.getLocalVisibleRect(scrollBounds)
						|| scrollBounds.height() < view.getHeight()) {
					int top = view.getTop(),
							height = view.getHeight(),
							scrollY = scrollView.getScrollY(),
							scrollHeight = scrollView.getHeight(),
							y = top;

					/*logDebug("top = " + top);
					logDebug("height = " + height);
					logDebug("scrollY = " + scrollY);
					logDebug("scrollHeight = " + scrollHeight);*/

					if (top < scrollY) {
						// scroll to y
					} else if (top + height > scrollY + scrollHeight) {
						y = top + height - scrollHeight;
						if (y < 0)
							y = 0;
					}

					// logDebug("View is not visible, scroll to y = " + y);
					scrollView.scrollTo(0, y);
				} else {
					// logDebug("View is visible");
				}
			}
		});
	}

	private long _avJ() {
		m_longI++;
		long l = System.currentTimeMillis();
		if (m_longI < 1 || m_longI > 10) { // maybe < 1 not needed?
			m_longI--;
			try {
				Thread.sleep(100L);
			} catch (InterruptedException _ex) {
			}
		}
		return System.currentTimeMillis() - l;
	}

	public void restartApp() {
		if (!restartingStarted) {
			destroyApp(true);
			restartingStarted = true;
		}
	}

	private void doRestartApp() {
		Intent mStartActivity = new Intent(this, GDActivity.class);
		int mPendingIntentId = 123456;
		PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
	}

	private void sendStats() {
		long lastTs = Settings.getLastSendStats();
		Helpers.logDebug("sendStats: lastTs = " + lastTs);
		if (lastTs == 0) {
			Helpers.logDebug("sendStats: set it to current ts and return");
			Settings.setLastSendStats(Helpers.getTimestamp());
			return;
		}

		// if (Helpers.getTimestamp() < lastTs + 3600 * 8) {
		if (Helpers.getTimestamp() < lastTs + 10) {
			Helpers.logDebug("sendStats: just return");
			return;
		}

		final GDActivity self = this;
		Thread statsThread = new Thread() {
			@Override
			public void run() {
				try {
					HashMap<String, Double> stats = levelsManager.getLevelsStat();

					JSONObject statsJSON = new JSONObject(stats);
					String id = Installation.id(self);
					int useCheats = org.happysanta.gd.Menu.Menu.isNameCheat(Settings.getName()) ? 1 : 0;

					API.sendStats(statsJSON.toString(), id, useCheats, new ResponseHandler() {
						@Override
						public void onResponse(Response response) {
							Helpers.logDebug("send stats OK");
							Settings.setLastSendStats(Helpers.getTimestamp());
						}

						@Override
						public void onError(APIException error) {
							Helpers.logDebug("send stats error: " + error.getMessage());
							// logDebug(error);
							// error.printStackTrace();
						}
					});
				} catch (Exception e) {
					Helpers.logDebug("send stats exception: " + e.getMessage());
					// e.printStackTrace();
				}
			}
		};
		statsThread.start();
	}

	public void sendKeyboardLogs() {
		final ProgressDialog progressDialog = ProgressDialog.show(this, getString(R.string.please_wait), getString(R.string.please_wait), true);
		API.sendKeyboardLogs(keyboardController.getLog(), new ResponseHandler() {
			@Override
			public void onResponse(Response response) {
				progressDialog.dismiss();
				keyboardController.clearLogBuffer();
				Helpers.showAlert(getString(R.string.ok), "Done", null);
			}

			@Override
			public void onError(APIException error) {
				progressDialog.dismiss();
				Helpers.showAlert(getString(R.string.error), "Unable to send logs. Maybe log is empty?", null);
			}
		});
	}

	private class ButtonCoords {

		public int x = 0;
		public int y = 0;
		public int w = 0;
		public int h = 0;

		public ButtonCoords() {
		}

		public boolean in(float x, float y) {
			if (x < this.x || x > this.x + this.w || y < this.y || y > this.y + this.h) {
				return false;
			}
			return true;
		}

	}

}