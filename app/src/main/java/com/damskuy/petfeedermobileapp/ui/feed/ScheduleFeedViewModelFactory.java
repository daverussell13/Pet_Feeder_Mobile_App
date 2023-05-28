package com.damskuy.petfeedermobileapp.ui.feed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.damskuy.petfeedermobileapp.data.feed.FeedRepository;

public class ScheduleFeedViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public ScheduleFeedViewModelFactory(Application application) {
       this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ScheduleFeedViewModel.class)) {
            return (T) new ScheduleFeedViewModel(application, new FeedRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
