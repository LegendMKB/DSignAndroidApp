package com.dsign.Retrofit;

import com.dsign.models.MediaRequest;
import com.dsign.models.MediaContent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    String BASE_URL = "http://test.dsignplay.com/api/MediaAPI/";
    @POST("getmedia")
    Call<MediaContent> postMediaInfo(@Body MediaRequest requestModel);
}
