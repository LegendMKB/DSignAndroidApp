package com.example.myblankapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.database.DBUtility;
import com.example.database.DatabaseHelper;
import com.example.models.DeviceInfo;

import java.io.File;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    int status = 0;
    TextView tview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //setContentView(R.layout.activity_main);

        //Database
       DBUtility dbutil = new DBUtility(this);
        DeviceInfo deviceinfo = dbutil.getDeviceInfo();
        //if(deviceinfo == null)
       // {
            setContentView(R.layout.activity_not_registered);
        SetDeviceText ("Waiting");

       //}

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CheckAPIStatus();
            }
        });
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_browse_fragment, new MainFragment())
                    .commitNow();
        }*/
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