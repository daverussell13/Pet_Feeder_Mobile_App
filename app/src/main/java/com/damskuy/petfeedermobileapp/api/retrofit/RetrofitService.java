package com.damskuy.petfeedermobileapp.api.retrofit;

import com.damskuy.petfeedermobileapp.data.dto.RealtimeFeedRequest;
import com.damskuy.petfeedermobileapp.data.dto.RealtimeFeedResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("api/v1/realtime")
    Call<RealtimeFeedResponse> realtimeFeed(@Body RealtimeFeedRequest request);
}
