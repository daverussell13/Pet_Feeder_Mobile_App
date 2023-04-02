package com.damskuy.petfeedermobileapp.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.data.model.AuthenticatedUser;
import com.damskuy.petfeedermobileapp.helpers.ViewHelper;
import com.damskuy.petfeedermobileapp.ui.auth.AuthenticatedUserView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout edtLayoutName, edtLayoutEmail, edtLayoutPassword, edtLayoutConfPassword;
    private TextInputEditText edtName, edtEmail, edtPassword, edtConfPassword;
    private AppCompatButton btnRegister;
    private RegisterViewModel registerViewModel;
    private boolean formValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();

        ViewModelProvider registerViewModelProvider = new ViewModelProvider(this, new RegisterViewModelFactory(getApplication()));
        registerViewModel = registerViewModelProvider.get(RegisterViewModel.class);

        observeRegisterFormState();
        observeRegisterResult();
        registerViewModel.observeFirebaseRegisterResult(this);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                notifyRegisterInputChanged();
            }
        };

        edtName.addTextChangedListener(afterTextChangedListener);
        edtEmail.addTextChangedListener(afterTextChangedListener);
        edtPassword.addTextChangedListener(afterTextChangedListener);
        edtConfPassword.addTextChangedListener(afterTextChangedListener);

        btnRegister.setOnClickListener(v -> {
            if (!formValid) {
                notifyRegisterInputChanged();
                ViewHelper.vibratePhone(
                        getApplicationContext(),
                        (Vibrator) getSystemService(Context.VIBRATOR_SERVICE),
                        findViewById(R.id.register_container),
                        500
                );
            } else {
                String name = ViewHelper.getEdtText(edtName);
                String email = ViewHelper.getEdtText(edtEmail);
                String password = ViewHelper.getEdtText(edtPassword);
                registerViewModel.register(name, email, password);
            }
        });
    }

    private void notifyRegisterInputChanged() {
        registerViewModel.registerInputChanged(
                ViewHelper.getEdtText(edtName),
                ViewHelper.getEdtText(edtEmail),
                ViewHelper.getEdtText(edtPassword),
                ViewHelper.getEdtText(edtConfPassword)
        );
    }

    private void observeRegisterFormState() {
        registerViewModel.getRegisterFormState().observe(this, registerFormState -> {
            if (registerFormState.getNameError() == null) edtLayoutName.setErrorEnabled(false);
            else edtLayoutName.setError(registerFormState.getNameError());
            if (registerFormState.getEmailError() == null) edtLayoutEmail.setErrorEnabled(false);
            else edtLayoutEmail.setError(registerFormState.getEmailError());
            if (registerFormState.getPasswordError() == null) edtLayoutPassword.setErrorEnabled(false);
            else edtLayoutPassword.setError(registerFormState.getPasswordError());
            if (registerFormState.getConfPasswordError() == null) edtLayoutConfPassword.setErrorEnabled(false);
            else edtLayoutConfPassword.setError(registerFormState.getConfPasswordError());
            formValid = registerFormState.isFormValid();
        });
    }

    private void observeRegisterResult() {
        registerViewModel.getRegisterResult().observe(this, registerResult -> {
            AuthenticatedUserView userView = registerResult.getSuccess();
            String error = registerResult.getError();
            if (userView != null) {
                Toast.makeText(this, "Success Register " + userView.getDisplayName(), Toast.LENGTH_SHORT).show();
            }
            if (error != null) {
                Toast.makeText(this, "Failed " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        edtName = findViewById(R.id.edt_name_register);
        edtLayoutName = findViewById(R.id.edt_layout_name_register);
        edtEmail = findViewById(R.id.edt_email_register);
        edtLayoutEmail = findViewById(R.id.edt_layout_email_register);
        edtPassword = findViewById(R.id.edt_password_register);
        edtLayoutPassword = findViewById(R.id.edt_layout_password_register);
        edtConfPassword = findViewById(R.id.edt_conf_password_register);
        edtLayoutConfPassword = findViewById(R.id.edt_layout_conf_password_register);
        btnRegister = findViewById(R.id.btn_register);
        textInputConfiguration();
    }

    private void textInputConfiguration() {
        String edtNameHint = getString(R.string.name_edt_placeholder);
        String edtEmailHint = getString(R.string.email_edt_placeholder);
        String edtPasswordHint = getString(R.string.password_edt_placeholder);

        edtName.setOnFocusChangeListener((v, hasFocus) ->
                ViewHelper.hideTextInputHint(hasFocus, edtName, edtLayoutName, edtNameHint));

        edtEmail.setOnFocusChangeListener((v, hasFocus) ->
                ViewHelper.hideTextInputHint(hasFocus, edtEmail, edtLayoutEmail, edtEmailHint));

        edtPassword.setOnFocusChangeListener((v, hasFocus) ->
                ViewHelper.hideTextInputHint(hasFocus, edtPassword, edtLayoutPassword, edtPasswordHint));

        edtConfPassword.setOnFocusChangeListener(((v, hasFocus) ->
                ViewHelper.hideTextInputHint(hasFocus, edtConfPassword, edtLayoutConfPassword, edtPasswordHint)));
    }
}