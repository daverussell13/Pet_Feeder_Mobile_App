package com.damskuy.petfeedermobileapp.ui.feed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.damskuy.petfeedermobileapp.data.feed.FeedRepository;
import com.damskuy.petfeedermobileapp.data.model.Result;
import com.damskuy.petfeedermobileapp.data.model.ScheduleFeed;
import com.damskuy.petfeedermobileapp.data.model.ScheduleFeedRequest;

public class ScheduleFeedViewModel extends AndroidViewModel {

    private final FeedRepository feedRepository;
    private final MutableLiveData<Result<ScheduleFeed>> addScheduleResult = new MutableLiveData<>();

    public ScheduleFeedViewModel(@NonNull Application application, FeedRepository feedRepository) {
        super(application);
        this.feedRepository = feedRepository;
    }

    public LiveData<Result<ScheduleFeed>> getAddScheduleFeedResult() {
        return addScheduleResult;
    }

    public void addNewSchedule(String deviceId, ScheduleFeed scheduleFeed) {
        ScheduleFeedRequest request = new ScheduleFeedRequest(
                deviceId,
                scheduleFeed.getDay(),
                scheduleFeed.getScheduledTime(),
                scheduleFeed.getFeed().getFeedAmount()
        );
        feedRepository.scheduleFeed(addScheduleResult, request);
    }
}
