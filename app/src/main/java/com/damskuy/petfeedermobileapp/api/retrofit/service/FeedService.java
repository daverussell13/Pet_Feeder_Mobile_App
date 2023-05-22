package com.damskuy.petfeedermobileapp.api.retrofit.service;

import com.damskuy.petfeedermobileapp.api.retrofit.model.RealtimeRequest;
import com.damskuy.petfeedermobileapp.api.retrofit.model.RealtimeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FeedService {
    @POST("api/v1/realtime")
    Call<RealtimeResponse> realtimeFeed(@Body RealtimeRequest request);
}
