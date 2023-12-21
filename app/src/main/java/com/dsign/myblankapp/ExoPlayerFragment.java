package com.dsign.myblankapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SimpleExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.ui.PlayerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myblankapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExoPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExoPlayerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ExoPlayer player;
    private PlayerView playerView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mediaFile;
    private static final String ARG_MEDIA_FILE = "media_file";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentInteractionListener interactionListener;
    public ExoPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment ExoPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExoPlayerFragment newInstance(String param1) {
        ExoPlayerFragment fragment = new ExoPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDIA_FILE, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            interactionListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mediaFile = getArguments().getString(ARG_MEDIA_FILE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_exo_player, container, false);
        View rootView = inflater.inflate(R.layout.fragment_exo_player, container, false);

        playerView = new PlayerView(requireContext());
        playerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        playerView.setUseController(false);
        // Find the FrameLayout placeholder in the layout and add PlayerView to it
        ViewGroup playerContainer = rootView.findViewById(R.id.playerContainer);
        playerContainer.addView(playerView);

        return rootView;
    }

    @OptIn(markerClass = UnstableApi.class) @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        player = new ExoPlayer.Builder(getContext()).build();

        // Bind the player to the view
        playerView.setPlayer(player);

        // Define media source (replace with your local video URI)
        Uri videoUri = Uri.parse(mediaFile);
        MediaItem firstItem = MediaItem.fromUri(videoUri);

        //MediaSource mediaSource = buildMediaSource(videoUri);

        // Prepare the player with the media source
        player.addMediaItem(firstItem);
        player.prepare();
        player.play(); // Auto play when ready

      /*  // Detect screen size and enter fullscreen mode for larger screens
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        int smallestWidth = Math.min(width, height);

        if (smallestWidth >= 600) {
            enterFullScreen();
        }*/


        // Update player view size based on video aspect ratio
        playerView.addOnLayoutChangeListener((view1, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            int videoWidth = player.getVideoFormat().width;
            int f = player.getVideoFormat().width;
            int videoHeight = player.getVideoFormat().height;
            float aspectRatio = (float) videoWidth / videoHeight;

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                ViewGroup.LayoutParams params = playerView.getLayoutParams();
                params.width = playerView.getWidth();
                params.height = (int) (playerView.getWidth() / aspectRatio);
                playerView.setLayoutParams(params);
            }
        });

        // Set a listener for player events
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    // Video playback has ended, close the Fragment
                    getParentFragmentManager().beginTransaction().remove(ExoPlayerFragment.this).commit();
                    closeFragment();
                }
            }
        });
    }
    public void closeFragment() {
        // FragmentA cleanup logic here
        interactionListener.onFragmentClosed(); // Signal MainActivity
    }

    private void enterFullScreen() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        playerView.setLayoutParams(params);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release the player when the Fragment is destroyed
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
