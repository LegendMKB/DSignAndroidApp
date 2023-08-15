package com.dsign.myblankapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.widget.Toast;

import com.dsign.database.DBUtility;
import com.dsign.models.MediaInfo;
import com.example.myblankapp.R;

import java.util.List;

public class PlayMediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_media);
    /*    ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);*/


    }

    @Override
    public void onBackPressed() {
        // your code.
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    void LoadImageFragment(Bundle savedInstanceState, MediaInfo mediaInfo){
        ImageFragment imgFragment = ImageFragment.newInstance(mediaInfo.getMediaFileName(), String.valueOf(mediaInfo.getDuration()));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.dframe_container ,imgFragment, null)
                    .commit();
        }

    }

    void PlayMedia(Bundle savedInstanceState){
        DBUtility dbUtility = new DBUtility(this);
        List<MediaInfo> mediaInfos = dbUtility.getMediaInfoForPlay();
        MediaInfo mediaInfo = mediaInfos.get(0);
        LoadImageFragment(savedInstanceState, mediaInfo);
    }
}