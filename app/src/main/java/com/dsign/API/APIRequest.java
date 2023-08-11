package com.dsign.API;

import android.content.Context;
import android.widget.Toast;

import com.dsign.models.MediaRequest;
import com.dsign.Retrofit.RetrofitClient;
import com.dsign.database.DBUtility;
import com.dsign.models.MediaContent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIRequest {
    public void GetMediaInfo(Context context){
        MediaRequest mreq = new MediaRequest();
        mreq.DeviceId = "1234";
        Call<MediaContent> cal = RetrofitClient.getInstance().getMyApi().postMediaInfo(mreq);
        cal.enqueue(new Callback<MediaContent>() {
            @Override
            public void onResponse(Call<MediaContent> call, Response<MediaContent> response) {
                MediaContent mres = response.body();

                //Update database
                DBUtility dbutil = new DBUtility(context);
                dbutil.updateMediaInfo(mres);

            }

            @Override
            public void onFailure(Call<MediaContent> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
