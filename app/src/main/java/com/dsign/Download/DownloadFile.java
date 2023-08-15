package com.dsign.Download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.dsign.DownloadStatus;
import com.dsign.database.DBUtility;
import com.dsign.models.MediaInfo;
import com.dsign.receivers.DownloadReceiver;

import java.io.File;

public class DownloadFile {
    public void downloadFile(Context context, MediaInfo mediaInfo, String destFileName, String title, String description){
        // Get the DownloadManager service
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        // Create a request for the download
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mediaInfo.getMediaurl()));
        request.setTitle(title);
        request.setDescription(description);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        File destinationFile = new File(context.getFilesDir(), destFileName);
        // Save the file in the "Downloads" directory
        //request.setDestinationUri(Uri.fromFile(destinationFile));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, destFileName);
        //request.setDestinationInExternalFilesDir()

        // Enqueue the download and get a download ID
        long downloadId = downloadManager.enqueue(request);
        DBUtility dbUtility = new DBUtility(context);
        dbUtility.updateFileDownloadInfo(mediaInfo, downloadId, DownloadStatus.IN_PROGRESS);

        //Update File Status
    }
}
