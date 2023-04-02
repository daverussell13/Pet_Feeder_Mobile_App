package com.damskuy.petfeedermobileapp.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.damskuy.petfeedermobileapp.core.Result;
import com.damskuy.petfeedermobileapp.data.auth.AuthRepository;
import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;
import com.damskuy.petfeedermobileapp.ui.auth.AuthenticatedUserView;
import com.damskuy.petfeedermobileapp.ui.auth.AuthenticationResult;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<AuthenticationResult> loginResult = new MutableLiveData<>();
    private final AuthRepository authRepository;

    public LoginViewModel(@NonNull Application application, AuthRepository authRepository) {
        super(application);
        this.authRepository = authRepository;
    }

    public LiveData<AuthenticationResult> getLoginResult() { return loginResult; }

    public void observeFirebaseLoginResult(LifecycleOwner owner) {
        authRepository.getFirebaseLoginResult().observe(owner, firebaseLoginResult -> {
            if (firebaseLoginResult instanceof Result.Success) {
                AuthenticatedUser user = ((Result.Success<AuthenticatedUser>) firebaseLoginResult).getData();
                loginResult.postValue(new AuthenticationResult(new AuthenticatedUserView(user.getName())));
            } else {
                String error = ((Result.Error<AuthenticatedUser>) firebaseLoginResult).getErrorMessage();
                loginResult.postValue(new AuthenticationResult(error));
            }
        });
    }

    public void login(String email, String password) {
        authRepository.login(email, password);
    }

}
