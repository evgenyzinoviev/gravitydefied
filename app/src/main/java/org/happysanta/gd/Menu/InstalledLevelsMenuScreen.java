package org.happysanta.gd.Menu;

import android.os.AsyncTask;
import android.view.ViewTreeObserver;
import org.happysanta.gd.Storage.Level;
import org.happysanta.gd.Storage.LevelsManager;

import static org.happysanta.gd.Helpers.getGDActivity;

public class InstalledLevelsMenuScreen extends LevelsMenuScreen {

	LevelsManager levelsManager;
	protected boolean isLoading = false;
	AsyncLoadLevels asyncLoadLevels = null;

	public InstalledLevelsMenuScreen(String title, MenuScreen navTarget) {
		super(title, navTarget);
		levelsManager = getGDActivity().levelsManager;
	}

	@Override
	public void loadLevels() {
		showLoading();
		isLoading = true;

		asyncLoadLevels = new AsyncLoadLevels() {
			@Override
			public void onPostExecute(Level[] levels) {
				if (status != Statuses.NORMAL) {
					clearList();
					setStatus(Statuses.NORMAL);
				}
				hideLoading();

				addElements = new AsyncAddElements() {
					@Override
					protected void onPostExecute(Void v) {
						isLoading = false;
						if (selectedIndex != -1) {
							// listLayout.requestLayout();
							// listLayout.invalidate();

							// View someView = findViewById(R.id.someView);
							final ViewTreeObserver obs = listLayout.getViewTreeObserver();
							obs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

								public boolean onPreDraw() {
									scrollToItem(selectedIndex);
									try {
										obs.removeOnPreDrawListener(this);
									} catch (IllegalStateException e) {
									}

									return true;
								}
							});

							// scrollToItem(selectedIndex);
						}
					}
				};
				addElements.execute(levels);
			}
		};
		asyncLoadLevels.execute();
	}

	@Override
	public void onShow() {
		super.onShow();
	}

	@Override
	public void onHide(MenuScreen newMenu) {
		super.onHide(newMenu);
	}

	@Override
	protected boolean hideDate() {
		return true;
	}

	@Override
	public void reloadLevels() {
		if (asyncLoadLevels != null) {
			asyncLoadLevels.cancel(true);
			asyncLoadLevels = null;
		}

		isLoading = false;
		super.reloadLevels();
	}

	private class AsyncLoadLevels extends AsyncTask<Void, Void, Level[]> {

		@Override
		protected Level[] doInBackground(Void... params) {
			return levelsManager.getAllInstalledLevels();
		}

	}

}
