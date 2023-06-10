package com.damskuy.petfeedermobileapp.ui.schedule;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.damskuy.petfeedermobileapp.data.schedule.ScheduleRepository;

public class ScheduleViewModelFactory implements ViewModelProvider.Factory {

    private final ScheduleRepository scheduleRepository;

    public ScheduleViewModelFactory() {
       this.scheduleRepository = ScheduleRepository.getInstance();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ScheduleViewModel.class)) {
            return (T) new ScheduleViewModel(scheduleRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
