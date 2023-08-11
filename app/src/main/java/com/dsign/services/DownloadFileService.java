package com.dsign.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.dsign.API.APIRequest;
import com.dsign.Download.DownloadFile;
import com.dsign.database.DBUtility;
import com.dsign.models.MediaInfo;

import java.util.List;

public class DownloadFileService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public  int onStartCommand(Intent intent, int flags, int startId){
        DBUtility dbutil = new DBUtility(getApplicationContext());
        List<MediaInfo> downloadMediaFileList = dbutil.getDownloadMediaFileList();
        if(downloadMediaFileList.size() > 0){
            for(int i = 0; i < downloadMediaFileList.size(); i++){
                DownloadFile downloadFile = new DownloadFile();
                downloadFile.downloadFile(getApplicationContext(), downloadMediaFileList.get(i).getMediaurl(), downloadMediaFileList.get(i).getMediaFileName(), String.valueOf(downloadMediaFileList.get(i).getmID()),"Downloading", "Downloading");
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }
}
