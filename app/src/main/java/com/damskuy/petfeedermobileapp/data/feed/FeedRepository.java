package com.damskuy.petfeedermobileapp.data.feed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.damskuy.petfeedermobileapp.data.model.Result;
import com.damskuy.petfeedermobileapp.data.model.ScheduleFeed;
import com.damskuy.petfeedermobileapp.data.model.ScheduleFeedRequest;
import com.damskuy.petfeedermobileapp.data.model.ScheduleFeedResponse;
import com.damskuy.petfeedermobileapp.utils.JsonUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedRepository {

    private final FeedDataSource feedDataSource;

    public FeedRepository() {
        feedDataSource = new FeedDataSource();
    }

    public void scheduleFeed(
            MutableLiveData<Result<ScheduleFeed>> result,
            ScheduleFeedRequest request
    ) {
        feedDataSource.scheduleFeed(new Callback<ScheduleFeedResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<ScheduleFeedResponse> call,
                    @NonNull Response<ScheduleFeedResponse> response
            ) {
                if (!response.isSuccessful()) {
                    ResponseBody errorBody = response.errorBody();
                    if (errorBody == null) {
                        result.postValue(new Result.Error<>(new Exception("Failed to add schedule")));
                        return;
                    }
                    try {
                        String errorBodyString = errorBody.string();
                        String errorMessage = JsonUtils.getJsonErrorField(errorBodyString);
                        result.postValue(new Result.Error<>(new Exception(errorMessage)));
                    } catch (IOException e) {
                        Log.e("JsonError", e.getMessage());
                        result.postValue(new Result.Error<>(new Exception("Failed to add schedule")));
                    }
                    return;
                }
                ScheduleFeedResponse responseData = response.body();
                if (responseData == null) {
                    result.postValue(new Result.Error<>(new Exception("Unable to get response data")));
                    return;
                }
                ScheduleFeed scheduleFeed = new ScheduleFeed()
                        .setId(responseData.getData().getScheduleId())
                        .setFeed(request.getFeedAmount())
                        .setDay(request.getDayOfWeek())
                        .setScheduleTime(request.getFeedTime());
                result.postValue(new Result.Success<>(scheduleFeed));
            }

            @Override
            public void onFailure(
                    @NonNull Call<ScheduleFeedResponse> call,
                    @NonNull Throwable t
            ) {
                result.postValue(new Result.Error<>(new Exception("Connection error")));
            }
        }, request);
    }
}
