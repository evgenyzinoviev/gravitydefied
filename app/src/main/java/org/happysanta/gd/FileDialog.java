package org.happysanta.gd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileDialog {
    private static final String PARENT_DIR = "..";
    private final String TAG = getClass().getName();
    private String[] fileList;
    private File currentPath;

    public interface FileSelectedListener {
        void fileSelected(File file);
    }

    public interface DirectorySelectedListener {
        void directorySelected(File directory);
    }

    private ListenerList<FileSelectedListener> fileListenerList = new ListenerList<>();
    private ListenerList<DirectorySelectedListener> dirListenerList = new ListenerList<>();
    private final Activity activity;
    private boolean selectDirectoryOption = false;
    private String fileEndsWith;

    public FileDialog(Activity activity, File path, String fileEndsWith) {
        this.activity = activity;
        this.fileEndsWith = fileEndsWith != null ? fileEndsWith.toLowerCase() : null;
        if (!path.exists()) path = Environment.getExternalStorageDirectory();
        // logDebug("FileDialog contrustor, path = " + path);
        loadFileList(path);
    }

    /**
     * @return file dialog
     */
    public Dialog createFileDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(currentPath.getPath());
        if (selectDirectoryOption) {
            builder.setPositiveButton("Select directory", (dialog1, which) -> {
                Helpers.logDebug(currentPath.getParent());
                // Log.d(TAG, currentPath.getPath());
                fireDirectorySelectedEvent(currentPath);
            });
        }

        builder.setItems(fileList, (dialog12, which) -> {
            String fileChosen = fileList[which];
            File chosenFile = getChosenFile(fileChosen);
            if (chosenFile.isDirectory()) {
                loadFileList(chosenFile);
                dialog12.cancel();
                dialog12.dismiss();
                showDialog();
            } else fireFileSelectedEvent(chosenFile);
        });

        dialog = builder.show();
        return dialog;
    }

    public void addFileListener(FileSelectedListener listener) {
        fileListenerList.add(listener);
    }

    public void removeFileListener(FileSelectedListener listener) {
        fileListenerList.remove(listener);
    }

    public void setSelectDirectoryOption(boolean selectDirectoryOption) {
        this.selectDirectoryOption = selectDirectoryOption;
    }

    public void addDirectoryListener(DirectorySelectedListener listener) {
        dirListenerList.add(listener);
    }

    public void removeDirectoryListener(DirectorySelectedListener listener) {
        dirListenerList.remove(listener);
    }

    /**
     * Show file dialog
     */
    public void showDialog() {
        createFileDialog().show();
    }

    private void fireFileSelectedEvent(final File file) {
        fileListenerList.fireEvent(listener -> listener.fileSelected(file));
    }

    private void fireDirectorySelectedEvent(final File directory) {
        dirListenerList.fireEvent(listener -> listener.directorySelected(directory));
    }

    private void loadFileList(File path) {
        this.currentPath = path;

        ArrayList<String> dirs = new ArrayList<>();
        ArrayList<String> files = new ArrayList<>();
        ArrayList<String> totalList = new ArrayList<>();

        if (path.exists()) {
            if (path.getParentFile() != null) dirs.add(PARENT_DIR);
            FilenameFilter filter = (dir, filename) -> {
                File sel = new File(dir, filename);
                if (!sel.canRead()) return false;
                if (sel.isHidden()) return false;
                if (selectDirectoryOption) return sel.isDirectory();
                else {
                    boolean endsWith = fileEndsWith == null || filename.toLowerCase().endsWith(fileEndsWith);
                    return endsWith || sel.isDirectory();
                }
            };

            File[] list = path.listFiles(filter);
            try {
                if (list != null && list.length > 0) {
                    for (File file : list) {
                        if (file.isDirectory())
                            dirs.add(file.getName() + "/");
                        else
                            files.add(file.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Comparator<String> comparator = String::compareTo;

            Collections.sort(dirs, comparator);
            Collections.sort(files, comparator);

            totalList.addAll(dirs);
            totalList.addAll(files);
        }

        fileList = totalList.toArray(new String[]{});
    }

    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) return currentPath.getParentFile();
        else return new File(currentPath, fileChosen);
    }
}

class ListenerList<L> {
    private List<L> listenerList = new ArrayList<>();

    public interface FireHandler<L> {
        void fireEvent(L listener);
    }

    public void add(L listener) {
        listenerList.add(listener);
    }

    public void fireEvent(FireHandler<L> fireHandler) {
        List<L> copy = new ArrayList<>(listenerList);
        for (L l : copy) {
            fireHandler.fireEvent(l);
        }
    }

    public void remove(L listener) {
        listenerList.remove(listener);
    }

    public List<L> getListenerList() {
        return listenerList;
    }
}
