package com.damskuy.petfeedermobileapp.data.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.damskuy.petfeedermobileapp.core.Result;
import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;
import com.damskuy.petfeedermobileapp.data.entity.FirebaseUserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthDataSource {

    private final MutableLiveData<Result<AuthenticatedUser>> firebaseRegisterResult = new MutableLiveData<>();
    private final MutableLiveData<Result<AuthenticatedUser>> firebaseLoginResult = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase firebaseDb;
    private static final String FIREBASE_REALTIME_URL = "https://petfeeder-71649-default-rtdb.asia-southeast1.firebasedatabase.app";

    public AuthDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDb = FirebaseDatabase.getInstance(FIREBASE_REALTIME_URL);
    }

    public LiveData<Result<AuthenticatedUser>> getFirebaseRegisterResult() { return firebaseRegisterResult; }

    public LiveData<Result<AuthenticatedUser>> getFirebaseLoginResult() { return firebaseLoginResult; }

    public void registerFirebase(String name, String email, String password, AuthRepository.OnAuthSuccess callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(authTask -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (authTask.isSuccessful() && user != null) {
                DatabaseReference usersRef = firebaseDb.getReference("users");
                FirebaseUserEntity userEntity = new FirebaseUserEntity(name);
                usersRef.child(user.getUid()).setValue(userEntity).addOnCompleteListener(storeTask -> {
                    if (storeTask.isSuccessful()) {
                        AuthenticatedUser authUser = new AuthenticatedUser(user.getUid(), name, user.getEmail());
                        firebaseRegisterResult.postValue(new Result.Success<>(authUser));
                        callback.onSuccess(authUser);
                    } else {
                        user.delete();
                        firebaseRegisterResult.postValue(new Result.Error<>(storeTask.getException()));
                    }
                });
            }
            else firebaseRegisterResult.postValue(new Result.Error<>(authTask.getException()));
        });
    }

    public void loginFirebase(String email, String password, AuthRepository.OnAuthSuccess callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (task.isSuccessful() && user != null) {
                AuthenticatedUser authUser = new AuthenticatedUser(user.getUid(), user.getDisplayName(), user.getEmail());
                firebaseLoginResult.postValue(new Result.Success<>(authUser));
                callback.onSuccess(authUser);
//                DatabaseReference usersRef = firebaseDb.getReference("users").child(user.getUid());
//                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        FirebaseUserEntity userEntity = snapshot.getValue(FirebaseUserEntity.class);
//                        if (userEntity != null) {
//                            AuthenticatedUser authUser = new AuthenticatedUser(user.getUid(), userEntity.getName(), user.getEmail());
//                            firebaseLoginResult.postValue(new Result.Success<>(authUser));
//                            callback.onSuccess(authUser);
//                        }
//                        firebaseLoginResult.postValue(new Result.Error<>(new Exception("Failed to fetch user")));
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        firebaseLoginResult.postValue(new Result.Error<>(error.toException()));
//                    }
//                });
            } else {
                firebaseLoginResult.postValue(new Result.Error<>(task.getException()));
            }
        });
    }
}
