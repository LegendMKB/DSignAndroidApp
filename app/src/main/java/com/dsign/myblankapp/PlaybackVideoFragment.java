package com.dsign.myblankapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.MediaPlayerAdapter;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.PlaybackControlsRow;

import com.example.myblankapp.R;

/**
 * Handles video playback with media controls.
 */
//public class PlaybackVideoFragment extends Fragment
public class PlaybackVideoFragment extends VideoSupportFragment
{

    private PlaybackTransportControlGlue<MediaPlayerAdapter> mTransportControlGlue;
    private static final String ARG_MEDIA_FILE = "media_file";
    private String mediaFile;
    private VideoView mVideoView;
    public PlaybackVideoFragment() {
        // Required empty public constructor
    }
    public static PlaybackVideoFragment newInstance(String mediaFile) {
        PlaybackVideoFragment fragment = new PlaybackVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDIA_FILE, mediaFile);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mediaFile = getArguments().getString(ARG_MEDIA_FILE);
            mediaFile = "file:///storage/emulated/0/Android/data/com.example.myblankapp/files/Pictures/video1.mp4";
        }
       /* mediaFile = "file:///storage/emulated/0/Android/data/com.example.myblankapp/files/Pictures/video1.mp4";*/
        mediaFile =  "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4";
       /* final Movie movie =
                (Movie) getActivity().getIntent().getSerializableExtra(DetailsActivity.MOVIE);*/

        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(PlaybackVideoFragment.this);

        MediaPlayerAdapter playerAdapter = new MediaPlayerAdapter(getActivity());
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE);

        mTransportControlGlue = new PlaybackTransportControlGlue<>(getActivity(), playerAdapter);
        mTransportControlGlue.setHost(glueHost);
        mTransportControlGlue.setTitle("");
        mTransportControlGlue.setSubtitle("");
        mTransportControlGlue.playWhenPrepared();
        mTransportControlGlue.addPlayerCallback(new PlaybackGlue.PlayerCallback() {
            @Override
            public void onPlayCompleted(PlaybackGlue glue) {
                // Playback completed logic here
                // You can perform any actions or checks when the playback is completed
                // getActivity().cl
                getActivity().finish();
            }
        });

        playerAdapter.setDataSource(Uri.parse(mediaFile));
    }

  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_video, container, false);
        mVideoView = root.findViewById(R.id.videoView);
        setupVideoPlayback();
        return root;
    }*/

    private void setupVideoPlayback() {
        // Set the video URI (replace with your video file's URI)
        Uri videoUri = Uri.parse(mediaFile);
        mVideoView.setVideoURI(videoUri);

        // Start playing the video
        mVideoView.start();
    }

    // Create a custom PlayerCallback implementation
 /*   PlaybackGlue.PlayerCallback playerCallback = new PlaybackGlue.PlayerCallback() {
        @Override
        public void onPlayCompleted(PlaybackGlue glue) {
            // Playback completed logic here
            // You can perform any actions or checks when the playback is completed
           // getActivity().cl
            getActivity().finish();
        }
    };*/

    @Override
    public void onPause() {
        super.onPause();
        // Pause video playback when the fragment is paused.
        if (mVideoView != null && mVideoView.isPlaying()) {
            mVideoView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume video playback when the fragment is resumed.
        if (mVideoView != null && !mVideoView.isPlaying()) {
            mVideoView.start();
        }
    }
}
