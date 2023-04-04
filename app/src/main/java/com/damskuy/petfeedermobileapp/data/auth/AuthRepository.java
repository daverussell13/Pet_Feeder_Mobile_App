package com.damskuy.petfeedermobileapp.data.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.damskuy.petfeedermobileapp.common.Result;
import com.damskuy.petfeedermobileapp.data.entity.FirebaseUserEntity;
import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;
import com.damskuy.petfeedermobileapp.data.user.UserDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AuthRepository {

    private final AuthDataSource authDataSource;
    private final UserDataSource userDataSource;
    private static AuthRepository instance;
    private AuthenticatedUser user = null;

    private AuthRepository() {
        this.authDataSource = new AuthDataSource();
        this.userDataSource = new UserDataSource();
    }

    public static synchronized AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    public AuthenticatedUser getAuthenticatedUser() { return user; }

    public boolean isAuthenticated() { return user != null; }

    public void setLoggedInUser(AuthenticatedUser user) { this.user = user; }

    public void register(
            String name,
            String email,
            String password,
            MutableLiveData<Result<AuthenticatedUser>> result
    ) {
        authDataSource.registerFirebase(email, password, task -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (task.isSuccessful() && user != null) {
                AuthenticatedUser authUser = new AuthenticatedUser(user.getUid(), name, email);
                FirebaseUserEntity userEntity = new FirebaseUserEntity(name);
                storeUserToRealtimeDB(authUser, userEntity, result);
            }
            else result.postValue(new Result.Error<>(task.getException()));
        });
    }

    private void storeUserToRealtimeDB(
            AuthenticatedUser user,
            FirebaseUserEntity userEntity,
            MutableLiveData<Result<AuthenticatedUser>> result
    ) {
        userDataSource.storeUserData(user.getUserId(), userEntity, (error, ref) -> {
            if (error == null) {
                setLoggedInUser(user);
                result.postValue(new Result.Success<>(user));
            }
            else result.postValue(new Result.Error<>(error.toException()));
        });
    }

    public void login(
            String email,
            String password,
            MutableLiveData<Result<AuthenticatedUser>> result
    ) {
        authDataSource.loginFirebase(email, password, task -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (task.isSuccessful() && user != null) {
                fetchUserFromRealtimeDB(user, result);
            } else {
                result.postValue(new Result.Error<>(new Exception("Something went wrong!")));
            }
        });
    }

    private void fetchUserFromRealtimeDB(
            FirebaseUser user,
            MutableLiveData<Result<AuthenticatedUser>> result
    ) {
        userDataSource.fetchUserData(user.getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUserEntity userEntity = snapshot.getValue(FirebaseUserEntity.class);
                if (userEntity != null) {
                    AuthenticatedUser authUser = new AuthenticatedUser(
                            user.getUid(),
                            userEntity.getName(),
                            user.getEmail()
                    );
                    setLoggedInUser(authUser);
                    result.postValue(new Result.Success<>(authUser));
                }
                else result.postValue(new Result.Error<>(new Exception("Failed to get user")));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                result.postValue(new Result.Error<>(new Exception("Something went wrong!")));
            }
        });
    }
}