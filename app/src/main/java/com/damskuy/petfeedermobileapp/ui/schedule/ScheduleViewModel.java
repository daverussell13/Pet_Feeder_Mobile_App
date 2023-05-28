package com.damskuy.petfeedermobileapp.ui.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.damskuy.petfeedermobileapp.data.model.Result;
import com.damskuy.petfeedermobileapp.data.model.Schedule;
import com.damskuy.petfeedermobileapp.data.schedule.ScheduleRepository;

import java.util.ArrayList;

public class ScheduleViewModel extends ViewModel {

    private final ScheduleRepository scheduleRepository;
    private final MutableLiveData<Result<ArrayList<Schedule>>> fetchScheduleResult = new MutableLiveData<>();

    public ScheduleViewModel(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public LiveData<Result<ArrayList<Schedule>>> getFetchScheduleResult() {
        return fetchScheduleResult;
    }

    public void fetchSchedule(String deviceId) {
        scheduleRepository.fetchDeviceSchedule(fetchScheduleResult, deviceId);
    }
}
