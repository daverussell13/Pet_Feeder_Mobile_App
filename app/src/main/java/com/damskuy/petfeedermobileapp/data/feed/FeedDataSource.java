package com.damskuy.petfeedermobileapp.data.feed;

import com.damskuy.petfeedermobileapp.BuildConfig;
import com.damskuy.petfeedermobileapp.data.dto.RealtimeFeedRequest;
import com.damskuy.petfeedermobileapp.data.dto.RealtimeFeedResponse;
import com.damskuy.petfeedermobileapp.data.dto.ScheduleFeedRequest;
import com.damskuy.petfeedermobileapp.data.dto.ScheduleFeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedDataSource {

    private final FeedService feedService;

    public FeedDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        feedService = retrofit.create(FeedService.class);
    }

    public void realtimeFeed(Callback<RealtimeFeedResponse> callback, RealtimeFeedRequest request) {
        Call<RealtimeFeedResponse> call = feedService.realtimeFeed(request);
        call.enqueue(callback);
    }

    public void scheduleFeed(Callback<ScheduleFeedResponse> callback, ScheduleFeedRequest request) {
        Call<ScheduleFeedResponse> call = feedService.scheduleFeed(request);
        call.enqueue(callback);
    }
}
