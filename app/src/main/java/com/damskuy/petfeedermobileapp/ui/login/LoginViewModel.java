package com.damskuy.petfeedermobileapp.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.damskuy.petfeedermobileapp.common.Result;
import com.damskuy.petfeedermobileapp.data.auth.AuthRepository;
import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<Result<AuthenticatedUser>> loginResult = new MutableLiveData<>();
    private final AuthRepository authRepository;

    public LoginViewModel(@NonNull Application application, AuthRepository authRepository) {
        super(application);
        this.authRepository = authRepository;
    }

    public LiveData<Result<AuthenticatedUser>> getLoginResult() { return loginResult; }

    public void login(String email, String password) {
        authRepository.login(email, password, loginResult);
    }

}
