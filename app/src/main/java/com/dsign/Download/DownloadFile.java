package com.dsign.Download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.dsign.receivers.DownloadReceiver;

import java.io.File;

public class DownloadFile {
    public void downloadFile(Context context, String remotefileUrl, String destFileName, String cusId, String title, String description){

        // Get the DownloadManager service
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        // Create a request for the download
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(remotefileUrl));
        request.setTitle(title);
        request.setDescription(description);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Save the file in the "Downloads" directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, destFileName);
        //request.setDestinationInExternalFilesDir()

        Intent intent = new Intent(context, DownloadReceiver.class);
        intent.putExtra("MyID", cusId);
        // Enqueue the download and get a download ID
        long downloadId = downloadManager.enqueue(request);


       /* BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctxt, Intent intent) {
                // Handle download completion
                // Move the downloaded file from public external storage to internal storage

                long receivedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (receivedDownloadId == downloadId) {
                    // Download completed, handle the downloaded file here
                    File sourceFile = new File(ctxt.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "temp_filename");
                    File targetFile = new File(ctxt.getFilesDir(), "downloaded_filename");

                    if (sourceFile.renameTo(targetFile)) {
                        // File moved successfully
                    } else {
                        // Error moving the file
                    }
                }

            }
        };*/
        //registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
