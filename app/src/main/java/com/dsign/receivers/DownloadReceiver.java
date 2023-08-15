package com.dsign.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.dsign.DownloadStatus;
import com.dsign.database.DBUtility;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            updateDownloadStatus(context, downloadId);
        }
    }


    private void updateDownloadStatus(Context context, long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        int status = 0;

        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            status = cursor.getInt(columnIndex);
            DBUtility dbUtility = new DBUtility(context);
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                int fileUriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                String downloadedUriString = cursor.getString(fileUriIndex);
                Uri downloadedUri = Uri.parse(downloadedUriString);

                // Convert the Uri to a file path
                String filePath = downloadedUri.getPath();
                dbUtility.updateFileDownloadStatus(downloadId, filePath, DownloadStatus.COMPLETED);
                // Download completed successfully
            } else if (status == DownloadManager.STATUS_FAILED) {
                // Download failed
                dbUtility.updateFileDownloadStatus(downloadId, "", DownloadStatus.FAILED);
            } else if (status == DownloadManager.STATUS_PAUSED) {
                // Download paused
            } else if (status == DownloadManager.STATUS_PENDING) {
                // Download pending
            } else if (status == DownloadManager.STATUS_RUNNING) {
                // Download is currently running
                dbUtility.updateFileDownloadStatus(downloadId, "", DownloadStatus.IN_PROGRESS);
            }
        }
        cursor.close();
    }



}

