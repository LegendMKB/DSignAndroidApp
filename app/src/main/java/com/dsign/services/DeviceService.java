package com.dsign.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.dsign.API.APIRequest;

public class DeviceService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public  int onStartCommand(Intent intent, int flags, int startId){
        APIRequest request = new APIRequest();
        request.GetMediaInfo(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

}
