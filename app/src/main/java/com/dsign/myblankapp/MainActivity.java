package com.dsign.myblankapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


import com.dsign.models.DeviceInfo;
import com.dsign.receivers.AlarmReceiver;
import com.dsign.database.DBUtility;
import com.dsign.receivers.DownloadAlarmReceiver;
import com.example.myblankapp.R;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends AppCompatActivity{
//public class MainActivity extends FragmentActivity {

    int status = 0;
    TextView tview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //Database
       DBUtility dbutil = new DBUtility(this);
        DeviceInfo deviceinfo = dbutil.getDeviceInfo();


        //Start Background Service for Device API call
       /* AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        long intervalMillis = 3 * 60 * 1000; // 3 minutes in milliseconds

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervalMillis, pendingIntent);*/
        /////Background Service for Device API call END


        //Start Background Service for Download of media files
        AlarmManager downloadAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent downloadAlarmIntent = new Intent(this, DownloadAlarmReceiver.class);
        PendingIntent downloadPendingIntent = PendingIntent.getBroadcast(this, 0, downloadAlarmIntent, 0);

        long downloadIntervalMillis = 3 * 60 * 1000; // 3 minutes in milliseconds

        downloadAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), downloadIntervalMillis, downloadPendingIntent);
        /////Background Service Download of media files END



        ///Play Media Files Start

        ///Play Media Files End

        //if(deviceinfo == null)
       // {
        setContentView(R.layout.activity_not_registered);
        SetDeviceText ("Waiting");


       /* Intent serviceIntent = new Intent(this,
                DeviceService.class);
        startService(serviceIntent);*/


       //}

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CheckAPIStatus();
            }
        });
       // if (savedInstanceState == null) {
           /* getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_browse_fragment, new MainFragment())
                    .commitNow();*/
       // }

/*        Intent playMediaIntent = new Intent(this, PlayMediaActivity.class);
        startActivity(playMediaIntent);*/
    }



    private void SetDeviceText (String msg){
        TextView tview = findViewById(R.id.textView);
        tview.setText(msg);
    }
    private void CheckAPIStatus(){
        int delayInMillis = 10000; // 3 seconds

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Code to be executed after the delay
                // Insert your logic here
                SetDeviceText("NMNy7823w7838983");
            }
        }, delayInMillis);
    }
}