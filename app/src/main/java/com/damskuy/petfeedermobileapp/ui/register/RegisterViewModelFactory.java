package com.damskuy.petfeedermobileapp.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.damskuy.petfeedermobileapp.data.register.RegisterDataSource;
import com.damskuy.petfeedermobileapp.data.register.RegisterRepository;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public RegisterViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(
                    application,
                    RegisterRepository.getInstance(new RegisterDataSource())
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
