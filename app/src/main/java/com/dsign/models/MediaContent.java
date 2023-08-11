
package com.dsign.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class MediaContent {

    @SerializedName("media")
    private List<Medium> mMedia;

    public List<Medium> getMedia() {
        return mMedia;
    }

    public void setMedia(List<Medium> media) {
        mMedia = media;
    }

}
