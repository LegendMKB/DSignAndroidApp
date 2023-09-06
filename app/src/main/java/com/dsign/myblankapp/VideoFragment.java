package com.dsign.myblankapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.myblankapp.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MEDIA_FILE = "media_file";
    //private static final String ARG_PARAM2 = "param2";

    private FragmentInteractionListener interactionListener;
    private Handler handler;

    // TODO: Rename and change types of parameters
    private String mediaFile;
    //private String mParam2;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDIA_FILE, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private VideoView videoView;
    private MediaPlayer mediaPlayer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mediaFile = getArguments().getString(ARG_MEDIA_FILE);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_video, container, false);

        View view = inflater.inflate(R.layout.fragment_video, container, false);

       // videoView = view.findViewById(R.id.videoView);

        // Set the video path or URL
       // String videoPath = "android.resource://" + requireContext().getPackageName() + "/" + R.raw.your_video; // Replace with your video resource

        String vpath = "file:///storage/emulated/0/Android/data/com.example.myblankapp/files/Pictures/video2.mp4";
      //  String vpath = "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4";
        //videoView.setVideoPath(vpath);
       //videoView.setVideoURI(Uri.parse(mediaFile));
        Uri videoUri = Uri.parse(vpath);
       // videoView.setVideoURI(Uri.parse(vpath));
        videoView.setVideoURI(videoUri);

        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // Handle the error here
                return false;
            }
        });
        // Start playing the video
        videoView.start();
        // Initialize MediaPlayer
      //  mediaPlayer = new MediaPlayer();
       // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    /*    try {
            mediaPlayer.setDataSource(requireContext(), Uri.parse(mediaFile));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        //videoView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
       // mediaPlayer.start();
        // Start playing


        return view;
    }

  /*  @Override
    public void onResume() {
        super.onResume();
       // String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.my_video; // Example video path
       // videoView.setVideoURI(Uri.parse(mediaFile));
        videoView.setVideoPath(mediaFile);
        videoView.start();
    }*/

    @Override
    public void onPause() {
        super.onPause();
       // videoView.pause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}