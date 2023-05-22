package com.damskuy.petfeedermobileapp.data.model;

import androidx.annotation.NonNull;

public class Feed {

    public static final int MIN_SERVING = 1;
    public static final int MAX_SERVING = 7;

    @NonNull
    private final Integer servings;

    public Feed(@NonNull Integer servings) {
        this.servings = servings;
    }

    @NonNull
    public Integer getServings() {
        return servings;
    }
}
