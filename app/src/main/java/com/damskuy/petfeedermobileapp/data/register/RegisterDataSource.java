package com.damskuy.petfeedermobileapp.data.register;

import androidx.annotation.NonNull;

import com.damskuy.petfeedermobileapp.core.Result;
import com.damskuy.petfeedermobileapp.data.register.model.RegisteredUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterDataSource {

    private final FirebaseAuth auth;

    public RegisterDataSource() {
        auth = FirebaseAuth.getInstance();
    }

    private static class RegisterCompleteHandler implements OnCompleteListener<AuthResult> {

        private Result<RegisteredUser> result;

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                RegisteredUser user = new RegisteredUser("titit");
                result = new Result.Success<>(user);
            } else {
                result = new Result.Error<>(task.getException());
            }
        }

        public Result<RegisteredUser> getResult() { return result; }
    }

    public Result<RegisteredUser> register(String email, String pass) {
        RegisterCompleteHandler handler = new RegisterCompleteHandler();
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(handler);
        return handler.getResult();
    }
}
