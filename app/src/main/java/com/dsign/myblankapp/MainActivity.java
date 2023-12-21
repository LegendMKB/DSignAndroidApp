package com.dsign.myblankapp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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

    private static final int PERMISSION_REQUEST_CODE = 123;
    private PowerManager.WakeLock wakeLock;
    private MediaChecker mediaChecker;
    private Handler handler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Acquire a WakeLock to prevent the screen from sleeping
      /*  PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::WakeLockTag");
        wakeLock.acquire();*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, PERMISSION_REQUEST_CODE);
            }
        }




       setContentView(R.layout.activity_main);


        //Database
       DBUtility dbutil = new DBUtility(this);
        DeviceInfo deviceinfo = dbutil.getDeviceInfo();


        //Start Background Service for Device API call
       AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent;
        if(Build.VERSION.SDK_INT >= 31){
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);
        }
        else {
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        }

        long intervalMillis = 3 * 60 * 1000; // 3 minutes in milliseconds

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervalMillis, pendingIntent);
        /////Background Service for Device API call END


        //Start Background Service for Download of media files
        AlarmManager downloadAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent downloadAlarmIntent = new Intent(this, DownloadAlarmReceiver.class);
        PendingIntent downloadPendingIntent;
        if(Build.VERSION.SDK_INT >= 31){
            downloadPendingIntent = PendingIntent.getBroadcast(this, 0, downloadAlarmIntent, PendingIntent.FLAG_IMMUTABLE);
        }
        else{
            downloadPendingIntent = PendingIntent.getBroadcast(this, 0, downloadAlarmIntent, 0);
        }


        long downloadIntervalMillis = 3 * 60 * 1000; // 3 minutes in milliseconds

        downloadAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), downloadIntervalMillis, downloadPendingIntent);
        /////Background Service Download of media files END



        ///Play Media Files Start

        // Initialize your databaseChecker instance here.
        mediaChecker = new MediaChecker(handler);

        // Start the periodic checking.
       handler.post(mediaChecker);
        ///Play Media Files End

        //if(deviceinfo == null)
       // {
        setContentView(R.layout.activity_not_registered);
        SetDeviceText ("Please wait while downloading the media Files");

        /*Button myButton = findViewById(R.id.buttonStartPlay);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to execute when the button is clicked
                // For example, you can show a toast message
                StartPlay();
            }
        });*/

       //}

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CheckAPIStatus();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with your logic
            } else {
                // Permission denied, handle accordingly
            }
        }
    }


    @Override
    protected void onDestroy() {
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
        super.onDestroy();
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

    private void StartPlay(){
        Intent playMediaIntent = new Intent(getApplicationContext(), PlayMediaActivity.class);
        startActivity(playMediaIntent);
    }

    public class MediaChecker implements Runnable {
        private final Handler handler;
        private boolean stopChecking = false;

        public MediaChecker(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            if (stopChecking) {
                return;
            }
            DBUtility dbutil = new DBUtility(getApplicationContext());

            if (dbutil.isMediaAvailableForPlay()) {
                stopChecking = true;
                handler.removeCallbacks(this); // Stop further execution of this Runnable.
               /* Intent playMediaIntent = new Intent(getApplicationContext(), PlayMediaActivity.class);
                startActivity(playMediaIntent);*/
                StartPlay();
                // Proceed with your desired action here.
            } else {
                handler.postDelayed(this, 5000); // Repeat the check every 1000 milliseconds.
            }
        }
    }

}