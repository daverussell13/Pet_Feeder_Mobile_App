package com.damskuy.petfeedermobileapp.data.register;

import com.damskuy.petfeedermobileapp.core.Result;
import com.damskuy.petfeedermobileapp.data.register.model.RegisteredUser;

public class RegisterRepository {

    private static RegisterRepository instance;

    private final RegisterDataSource dataSource;

    private RegisterRepository(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static synchronized RegisterRepository getInstance(RegisterDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    public Result<RegisteredUser> login(String email, String password) {
        Result<RegisteredUser> result = dataSource.register(email, password);
        if (result instanceof Result.Success) {
        }
        return result;
    }
}