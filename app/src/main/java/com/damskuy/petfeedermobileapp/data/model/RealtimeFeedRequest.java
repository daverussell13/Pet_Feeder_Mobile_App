package com.damskuy.petfeedermobileapp.data.model;

import com.google.gson.annotations.SerializedName;

public class RealtimeFeedRequest {

    @SerializedName("device_id")
    private final String deviceId;

    @SerializedName("feed_amount")
    private final int feedAmount;

    public RealtimeFeedRequest(String deviceId, int feedAmount) {
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
