package com.dsign.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        String yourValue = intent.getStringExtra("MyID");

        if (downloadId != -1) {
            // Handle download completion here
            // You can retrieve the file's local URI using the downloadId
        }
    }
}
