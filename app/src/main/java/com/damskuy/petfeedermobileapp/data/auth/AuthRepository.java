package com.damskuy.petfeedermobileapp.data.auth;

import androidx.lifecycle.LiveData;

import com.damskuy.petfeedermobileapp.core.Result;
import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;

public class AuthRepository {

    private static AuthRepository instance;
    private final AuthDataSource dataSource;
    private final LiveData<Result<AuthenticatedUser>> firebaseRegisterResult;
    private final LiveData<Result<AuthenticatedUser>> firebaseLoginResult;
    private AuthenticatedUser user = null;

    private AuthRepository(AuthDataSource dataSource) {
        this.dataSource = dataSource;
        this.firebaseRegisterResult = dataSource.getFirebaseRegisterResult();
        this.firebaseLoginResult = dataSource.getFirebaseLoginResult();
    }

    public static synchronized AuthRepository getInstance() {
        return instance;
    }

    public static synchronized AuthRepository getInstance(AuthDataSource dataSource) {
        if (instance == null) {
            instance = new AuthRepository(dataSource);
        }
        return instance;
    }

    public LiveData<Result<AuthenticatedUser>> getFirebaseRegisterResult() { return firebaseRegisterResult; }

    public LiveData<Result<AuthenticatedUser>> getFirebaseLoginResult() { return firebaseLoginResult; }

    private void setLoggedInUser(AuthenticatedUser user) { this.user = user; }

    public static interface OnAuthSuccess {
        void onSuccess(AuthenticatedUser user);
    }

    public void register(String name, String email, String password) {
        dataSource.registerFirebase(name, email, password, this::setLoggedInUser);
    }

    public void login(String email, String password) {
        dataSource.loginFirebase(email, password, this::setLoggedInUser);
    }

    public boolean isAuthenticated() {
        return user != null;
    }
}