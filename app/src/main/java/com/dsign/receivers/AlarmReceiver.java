package com.dsign.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dsign.services.DeviceService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Start your background service here
        Intent serviceIntent = new Intent(context, DeviceService.class);
        context.startService(serviceIntent);
    }
}
