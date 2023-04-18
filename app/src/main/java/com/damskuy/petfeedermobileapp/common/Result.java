package com.damskuy.petfeedermobileapp.common;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Result<T> {

    private Result() {}

    @NonNull
    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success<T> success = (Result.Success<T>) this;
            return success.getData().toString();
        } else if (this instanceof Result.Error) {
            Result.Error<T> error = (Result.Error<T>) this;
            return error.getErrorMessage();
        }
        return "";
    }

    public static final class Success<T> extends Result<T> {

        private final T data;

        public Success(T data) { this.data = data; }

        public T getData() { return this.data;}
    }

    public static final class Error<T> extends Result<T> {

        private final Exception error;

        public Error(Exception error) { this.error = error; }

        public String getErrorMessage() { return this.error.getMessage(); }
    }
}
