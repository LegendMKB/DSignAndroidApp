package com.dsign.models;

import com.google.gson.annotations.SerializedName;

public class MediaInfo {
    @SerializedName("gid")
    private int gID;

    @SerializedName("sid")
    private int sID;

    @SerializedName("mid")
    private int mID;
    @SerializedName("duration")
    private Long Duration;
    @SerializedName("mediaurl")
    private String Mediaurl;

    @SerializedName("mediafilename")
    private String MediaFileName;

    @SerializedName("medialocalpath")
    private String MediaLocalPath;
    @SerializedName("playindex")
    private Long Playindex;
    @SerializedName("type")
    private String Type;

    public int getgID() {
        return gID;
    }

    public int getmID() {
        return mID;
    }

    public Long getDuration() {
        return Duration;
    }

    public String getMediaurl() {
        return Mediaurl;
    }

    public Long getPlayindex() {
        return Playindex;
    }

    public String getType() {
        return Type;
    }
    public void setmID(int mID) {
        this.mID = mID;
    }

    public void setgID(int gID) {
        this.gID = gID;
    }
    public void setDuration(Long duration) {
        Duration = duration;
    }

    public void setMediaurl(String mediaurl) {
        Mediaurl = mediaurl;
    }

    public void setPlayindex(Long playindex) {
        Playindex = playindex;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public String getMediaFileName() {
        return MediaFileName;
    }

    public void setMediaFileName(String mediaFileName) {
        MediaFileName = mediaFileName;
    }

    public String getMediaLocalPath() {
        return MediaLocalPath;
    }

    public void setMediaLocalPath(String mediaLocalPath) {
        MediaLocalPath = mediaLocalPath;
    }
}
