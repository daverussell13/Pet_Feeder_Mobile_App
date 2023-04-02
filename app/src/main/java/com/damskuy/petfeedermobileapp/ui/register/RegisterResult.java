package com.damskuy.petfeedermobileapp.ui.register;

import androidx.annotation.Nullable;

import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;
import com.damskuy.petfeedermobileapp.ui.auth.AuthenticatedUserView;

public class RegisterResult {

    @Nullable
    private AuthenticatedUserView success;
    @Nullable
    private String error;

    public RegisterResult(@Nullable String error) { this.error = error; }

    public RegisterResult(@Nullable AuthenticatedUserView success) { this.success = success; }

    @Nullable
    public AuthenticatedUserView getSuccess() { return success; }

    @Nullable
    public String getError() { return error; }
}
