package com.dsign.myblankapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.widget.Toast;

import com.dsign.database.DBUtility;
import com.dsign.models.MediaInfo;
import com.example.myblankapp.R;

import java.util.List;

public class PlayMediaActivity extends AppCompatActivity implements FragmentInteractionListener{

    private List<MediaInfo> mediaInfos;
    private int mediaCount = 0;
    private int playCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_media);
    /*    ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);*/

        LoadPlayList ();
        PlayMedia(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        // your code.
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    void LoadImageFragment(Bundle savedInstanceState, MediaInfo mediaInfo){

        ImageFragment imgFragment = ImageFragment.newInstance(mediaInfo.getMediaLocalPath(), String.valueOf(mediaInfo.getDuration()));
        //if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.dframe_container ,imgFragment, null)
                    .commit();
        //}

    }
    void LoadPlayList ()
    {
        DBUtility dbUtility = new DBUtility(this);
        mediaInfos = dbUtility.getMediaInfoForPlay();
        mediaCount = mediaInfos.size();
    }

    void PlayMedia(Bundle savedInstanceState){
        MediaInfo mediaInfo = mediaInfos.get(playCount++);
        LoadImageFragment(savedInstanceState, mediaInfo);
    }

    @Override
    public void onFragmentClosed() {
        ContinuePlayMedia ();
    }

    void ContinuePlayMedia (){
        if (playCount >= mediaCount) {
            playCount = 0;
            LoadPlayList ();
        }
        PlayMedia(null);
    }
}