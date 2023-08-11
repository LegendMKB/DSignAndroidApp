package com.dsign.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dsign.services.DeviceService;
import com.dsign.services.DownloadFileService;

public class DownloadAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Start your background service here
        Intent serviceIntent = new Intent(context, DownloadFileService.class);
        context.startService(serviceIntent);
    }
}
