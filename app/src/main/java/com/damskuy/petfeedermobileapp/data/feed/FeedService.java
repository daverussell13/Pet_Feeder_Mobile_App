package com.damskuy.petfeedermobileapp.data.feed;

import com.damskuy.petfeedermobileapp.data.dto.RealtimeFeedRequest;
import com.damskuy.petfeedermobileapp.data.dto.RealtimeFeedResponse;
import com.damskuy.petfeedermobileapp.data.dto.ScheduleFeedRequest;
import com.damskuy.petfeedermobileapp.data.dto.ScheduleFeedResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface FeedService {
    @POST("api/v1/realtime")
    Call<RealtimeFeedResponse> realtimeFeed(@Body RealtimeFeedRequest request);

    @POST("api/v1/schedule")
    Call<ScheduleFeedResponse> scheduleFeed(@Body ScheduleFeedRequest request);
}
