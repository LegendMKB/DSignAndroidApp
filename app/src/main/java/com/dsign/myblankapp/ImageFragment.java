package com.dsign.myblankapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myblankapp.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MEDIA_FILE = "media_file";
    private static final String ARG_MEDIA_DURATION = "media_duration";

    private FragmentInteractionListener interactionListener;
    private Handler handler;

    // TODO: Rename and change types of parameters
    private String mediaFile;
    private String mediaDuration;

    public ImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(String mediaFile, String mediaDuration) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDIA_FILE, mediaFile);
        args.putString(ARG_MEDIA_DURATION, mediaDuration);
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
            mediaDuration = getArguments().getString(ARG_MEDIA_DURATION);
        }

        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView tview = view.findViewById(R.id.media_image_view);
        tview.setImageURI(Uri.parse(mediaFile));
       // closeFragmentAfterDelay(Long.parseLong(mediaDuration) * 1000);
        closeFragmentAfterDelay(1 * 1000);
        return  view;
    }

    private void closeFragmentAfterDelay(long delayMillis) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Close the fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ImageFragment.this).commit();
                closeFragment();
            }
        }, delayMillis);
    }

    public void closeFragment() {
        // FragmentA cleanup logic here
        interactionListener.onFragmentClosed(); // Signal MainActivity
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove any pending callbacks to prevent leaks
        handler.removeCallbacksAndMessages(null);
    }
}