package org.happysanta.gd.Menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import org.happysanta.gd.API.*;
import org.happysanta.gd.GDActivity;
import org.happysanta.gd.Menu.Views.MenuImageView;
import org.happysanta.gd.R;
import org.happysanta.gd.API.Response;
import org.happysanta.gd.Settings;
import org.happysanta.gd.WaitForNetworkConnection;

import static org.happysanta.gd.Helpers.*;
import static org.happysanta.gd.Helpers.getDp;

public class DownloadLevelsMenuScreen extends LevelsMenuScreen {

	protected final static int API_LIMIT = 100;
	public static API.LevelsSortType sort;

	protected MenuImageView sortImage;
	// protected API api;
	protected Request request;
	protected int offset = 0;
	protected boolean isLoading = false;
	protected boolean fullLoaded = false;
	protected WaitForNetworkConnection waitForNetworkConnection = null;
	protected Toast toast;

	public DownloadLevelsMenuScreen(String title, MenuScreen navTarget) {
		super(title, navTarget);

		// api = new API();
		// api.setSort(sort);

		Context context = getGDActivity();

		// Sort icon
		sortImage = new MenuImageView(context);
		sortImage.setImageResource(R.drawable.ic_sort);
		sortImage.setAdjustViewBounds(true);
		sortImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showSortDialog();
			}
		});
		sortImage.setVisibility(View.GONE);
		sortImage.setPadding(getDp(10), 0, 0, 0);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				getDp(40)
		);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		sortImage.setLayoutParams(params);
	}

	@Override
	protected void loadLevels() {
		try {
			if (!checkNetwork())
				return;

			showLoading();
			isLoading = true;
			request = API.getLevels(offset, API_LIMIT, sort, new ResponseHandler() {
				@Override
				public void onResponse(final Response response) {
					final LevelsResponse levelsResponse = new LevelsResponse(response);
					if (status != Statuses.NORMAL) {
						clearList();
						setStatus(Statuses.NORMAL);
					}

					hideLoading();

					addElements = new AsyncAddElements() {
						@Override
						protected void onPostExecute(Void v) {
							logDebug("offset = " + offset + ", totalCount = " + levelsResponse.getTotalCount());
							fullLoaded = offset >= levelsResponse.getTotalCount();
							if (!fullLoaded)
								showLoading();

							isLoading = false;
						}
					};
					addElements.execute(levelsResponse.getLevels());
				}

				@Override
				public void onError(APIException error) {
					showError(error.getMessage());
					isLoading = false;
				}
			});

			offset += API_LIMIT;
		} catch (Exception e) {
			e.printStackTrace();
			showError(getString(R.string.download_error));

			isLoading = false;
		}
	}

	@Override
	public void reloadLevels() {
		if (request != null) request.cancel();
		offset = 0;
		isLoading = false;
		fullLoaded = false;

		super.reloadLevels();
	}

	protected void showSortDialog() {
		final CharSequence[] items = getStringArray(R.array.sort_variants);

		AlertDialog dialog = new AlertDialog.Builder(getGDActivity())
				.setTitle(getString(R.string.sort_by))
				.setSingleChoiceItems(items, API.getIdBySortType(sort), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						API.LevelsSortType newSort = API.getSortTypeById(item);

						if (newSort != sort) {
							sort = newSort;
							// api.setSort(newSort);
							Settings.setLevelsSort(sort);
							reloadLevels();
						}

						dialog.dismiss();
					}
				})
				.create();

		dialog.show();
	}

	protected boolean checkNetwork() {
		if (!isOnline()) {
			if (elements.isEmpty()) {
				showError(getString(R.string.waiting_for_network));

				if (waitForNetworkConnection != null)
					waitForNetworkConnection.cancel(true);

				waitForNetworkConnection = new WaitForNetworkConnection();
				waitForNetworkConnection.execute(null, new Runnable() {
					@Override
					public void run() {
						reloadLevels();
					}
				});
			} else {
				if (toast != null) {
					toast.cancel();
				}

				toast = Toast.makeText(getGDActivity().getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT);
				toast.show();
			}

			return false;
		}

		return true;
	}

	@Override
	public void onHide(MenuScreen newMenu) {
		logDebug("onHide");
		super.onHide(newMenu);

		GDActivity activity = getGDActivity();

		if (newMenu != getGameMenu().levelScreen) {
			offset = 0;
			isLoading = false;
			fullLoaded = false;

			if (request != null) request.cancel();
			// api.cancelRequest();
			if (waitForNetworkConnection != null)
				waitForNetworkConnection.cancel(true);
		}

		activity.titleLayout.removeView(sortImage);
		sortImage.setVisibility(View.GONE);
	}

	@Override
	public void onShow() {
		super.onShow();

		GDActivity activity = getGDActivity();

		activity.titleLayout.addView(sortImage);
		sortImage.setVisibility(View.VISIBLE);
	}

	@Override
	public void onScroll(double percent) {
		if (percent >= 97 && !isLoading && !fullLoaded) {
			loadLevels();
		}
	}

	@Override
	public void deleteElement(LevelMenuElement el) {
		super.deleteElement(el);
		offset--;
	}

}
