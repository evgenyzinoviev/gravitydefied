package org.happysanta.gd.API;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import org.apache.http.HttpException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.happysanta.gd.Helpers.getGDActivity;

public class DownloadFile {

	private String urlString;
	private DownloadHandler handler;
	private FileOutputStream output;
	private AsyncDownloadTask task;

	public DownloadFile(String url, FileOutputStream output) {
		this.urlString = url;
		this.output = output;
	}

	public DownloadFile(String url, FileOutputStream output, DownloadHandler handler) {
		this(url, output);
		this.handler = handler;
	}

	public void setDownloadHandler(DownloadHandler handler) {
		this.handler = handler;
	}

	public void start() {
		task = new AsyncDownloadTask();
		task.execute();
	}

	public void cancel() {
		if (task != null) {
			task.cancel(true);
			task = null;
		}
	}

	protected class AsyncDownloadTask extends AsyncTask<Void, Integer, Throwable> {

		private PowerManager.WakeLock lock;

		@Override
		protected Throwable doInBackground(Void... params) {
			// OutputStream output = (FileOutputStream)params[1];
			InputStream input = null;
			HttpURLConnection connection = null;

			try {
				URL url = new URL(urlString);
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();

				// expect HTTP 200 OK, so we don't mistakenly save error report
				// instead of the file
				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return new HttpException("Server returned HTTP " + connection.getResponseCode()
							+ " " + connection.getResponseMessage());
				}

				// this will be useful to display download percentage
				// might be -1: server did not report the length
				int fileLength = connection.getContentLength();

				// download the file
				input = connection.getInputStream();

				byte data[] = new byte[4096];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					// allow canceling with back button
					if (isCancelled()) {
						input.close();
						return null;
					}

					total += count;

					// publishing the progress....
					if (fileLength > 0) // only if total length is known
						publishProgress((int) (total * 100 / fileLength));

					output.write(data, 0, count);
				}
			} catch (Exception e) {
				return e;
			} finally {
				try {
					if (output != null)
						output.close();
					if (input != null)
						input.close();
				} catch (IOException ignored) {
				}

				if (connection != null)
					connection.disconnect();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// take CPU lock to prevent CPU from going off if the user
			// presses the power button during download
			PowerManager pm = (PowerManager) getGDActivity().getSystemService(Context.POWER_SERVICE);
			lock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					getClass().getName());
			lock.acquire();

			handler.onStart();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);

			handler.onProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(Throwable error) {
			lock.release();
			handler.onFinish(error);
		}

	}


}
