package com.damskuy.petfeedermobileapp.api.retrofit.model;

public class RealtimeResponse {

    private Data data;
    private boolean error;

    public static class Data {
        private String device_id;
        private int feed_amount;
        private String created_at;
    }
}
