
package com.dsign.models;


import com.google.gson.annotations.SerializedName;

public class Medium {

    @SerializedName("sid")
    private int sID;
    @SerializedName("gid")
    private int gID;
    @SerializedName("mid")
    private int mID;
    @SerializedName("duration")
    private Long Duration;
    @SerializedName("mediaurl")
    private String Mediaurl;

    @SerializedName("mediafilename")
    private String MediaFileName;
    @SerializedName("playindex")
    private Long Playindex;
    @SerializedName("type")
    private String Type;

    @SerializedName("changestatus")
    private int Changestatus;

    public int getsID() {
        return sID;
    }

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

    public int getChangestatus() {
        return Changestatus;
    }

    public String getMediaFileName() {
        return MediaFileName;
    }
}
