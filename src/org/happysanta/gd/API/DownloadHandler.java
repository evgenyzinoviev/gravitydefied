package org.happysanta.gd.API;

/**
 * Created by evgeny on 6/10/14.
 */
public interface DownloadHandler {

	public abstract void onStart();

	public abstract void onFinish(Throwable error);

	public abstract void onProgress(int progress);

}