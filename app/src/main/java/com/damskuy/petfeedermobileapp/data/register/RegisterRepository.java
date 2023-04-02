package com.damskuy.petfeedermobileapp.data.register;

import androidx.lifecycle.LiveData;

import com.damskuy.petfeedermobileapp.core.Result;
import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;

public class RegisterRepository {

    private static RegisterRepository instance;
    private final RegisterDataSource dataSource;
    private final LiveData<Result<AuthenticatedUser>> firebaseRegisterResult;

    private RegisterRepository(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
        this.firebaseRegisterResult = dataSource.getFirebaseRegisterResult();
    }

    public static synchronized RegisterRepository getInstance(RegisterDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    public void register(String name, String email, String password) {
        dataSource.registerFirebase(name, email, password);
    }

    public LiveData<Result<AuthenticatedUser>> getRegisterResult() { return firebaseRegisterResult; }
}