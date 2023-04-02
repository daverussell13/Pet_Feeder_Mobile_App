package com.damskuy.petfeedermobileapp.data.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.damskuy.petfeedermobileapp.core.Result;
import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;
import com.damskuy.petfeedermobileapp.data.entity.FirebaseUserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterDataSource {

    private final MutableLiveData<Result<AuthenticatedUser>> firebaseRegisterResult = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase firebaseDb;
    private static final String FIREBASE_REALTIME_URL = "https://petfeeder-71649-default-rtdb.asia-southeast1.firebasedatabase.app";

    public RegisterDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDb = FirebaseDatabase.getInstance(FIREBASE_REALTIME_URL);
    }

    public LiveData<Result<AuthenticatedUser>> getFirebaseRegisterResult() { return firebaseRegisterResult; }

    public void registerFirebase(String name, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(authTask -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (authTask.isSuccessful() && user != null) {
                DatabaseReference usersRef = firebaseDb.getReference("users");
                FirebaseUserEntity userEntity = new FirebaseUserEntity(name);
                usersRef.child(user.getUid()).setValue(userEntity).addOnCompleteListener(storeTask -> {
                    if (storeTask.isSuccessful()) {
                        AuthenticatedUser authUser = new AuthenticatedUser(user.getUid(), name, user.getEmail());
                        firebaseRegisterResult.postValue(new Result.Success<>(authUser));
                    } else {
                        user.delete();
                        firebaseRegisterResult.postValue(new Result.Error<>(storeTask.getException()));
                    }
                });
            } else {
                firebaseRegisterResult.postValue(new Result.Error<>(authTask.getException()));
            }
        });
    }
}
