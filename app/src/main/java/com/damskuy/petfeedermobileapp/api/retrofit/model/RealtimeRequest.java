package com.damskuy.petfeedermobileapp.api.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class RealtimeRequest {

    @SerializedName("device_id")
    private final String deviceId;

    @SerializedName("feed_amount")
    private final int feedAmount;

    public RealtimeRequest(String deviceId, int feedAmount) {
        this.deviceId = deviceId;
        this.feedAmount = feedAmount;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public int getFeedAmount() {
        return feedAmount;
    }
}
