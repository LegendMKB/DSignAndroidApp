package com.dsign.models;

import com.google.gson.annotations.SerializedName;

public class MediaRequest {

    @SerializedName("deviceId")
    public String DeviceId;

    public String getDeviceId() {
        return DeviceId;
    }
}
