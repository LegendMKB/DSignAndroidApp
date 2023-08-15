package com.dsign.myblankapp;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myblankapp.R;

import java.io.InputStream;

public class ImageOldActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Bundle extras = getIntent().getExtras();
        String imgPath = "";
        if (extras != null) {
            imgPath = extras.getString("ImgItem");
            // Use the value as needed
        }
        int FinishTime = 10;
        int countDownInterval = 1000;

        ImageView imgview = (ImageView) findViewById(R.id.image_view);
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
        imgview.setImageBitmap(bitmap);
        CountDownTimer counterTimer = new CountDownTimer(FinishTime * 1000, countDownInterval) {
            public void onFinish() {
                //finish your activity here
                finish();
            }

            public void onTick(long millisUntilFinished) {
                //called every 1 sec coz countDownInterval = 1000 (1 sec)
            }
        };
        counterTimer.start();
    }



    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}