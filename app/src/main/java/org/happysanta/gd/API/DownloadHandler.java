package org.happysanta.gd.API;

/**
 * Created by evgeny on 6/10/14.
 */
public interface DownloadHandler {

    void onStart();

    void onFinish(Throwable error);

    void onProgress(int progress);
}