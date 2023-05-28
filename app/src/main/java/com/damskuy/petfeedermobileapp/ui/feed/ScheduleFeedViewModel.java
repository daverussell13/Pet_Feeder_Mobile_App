package com.damskuy.petfeedermobileapp.ui.feed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.damskuy.petfeedermobileapp.data.feed.FeedRepository;
import com.damskuy.petfeedermobileapp.data.model.Result;
import com.damskuy.petfeedermobileapp.data.model.Schedule;
import com.damskuy.petfeedermobileapp.data.dto.ScheduleFeedRequest;

public class ScheduleFeedViewModel extends AndroidViewModel {

    private final FeedRepository feedRepository;
    private final MutableLiveData<Result<Schedule>> addScheduleResult = new MutableLiveData<>();

    public ScheduleFeedViewModel(@NonNull Application application, FeedRepository feedRepository) {
        super(application);
        this.feedRepository = feedRepository;
    }

    public LiveData<Result<Schedule>> getAddScheduleFeedResult() {
        return addScheduleResult;
    }

    public void addNewSchedule(String deviceId, Schedule schedule) {
        ScheduleFeedRequest request = new ScheduleFeedRequest(
                deviceId,
                schedule.getDay(),
                schedule.getScheduledTime(),
                schedule.getFeed().getFeedAmount()
        );
        feedRepository.scheduleFeed(addScheduleResult, request);
    }
}
