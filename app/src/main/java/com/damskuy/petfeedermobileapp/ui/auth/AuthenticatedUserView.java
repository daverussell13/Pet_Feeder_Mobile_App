package com.damskuy.petfeedermobileapp.ui.auth;

public class AuthenticatedUserView {

    private final String displayName;

    public AuthenticatedUserView(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}