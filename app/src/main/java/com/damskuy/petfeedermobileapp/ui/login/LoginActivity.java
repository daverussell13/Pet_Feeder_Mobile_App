package com.damskuy.petfeedermobileapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.data.auth.AuthRepository;
import com.damskuy.petfeedermobileapp.helpers.ViewHelper;
import com.damskuy.petfeedermobileapp.ui.MainActivity;
import com.damskuy.petfeedermobileapp.ui.auth.AuthenticatedUserView;
import com.damskuy.petfeedermobileapp.ui.register.RegisterActivity;
import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout edtLayoutPassword;
    private EditText edtEmail, edtPassword;
    private AppCompatButton btnLogin;
    private TextView registerLink;
    private LoginViewModel loginViewModel;
    private SweetAlertDialog alertDialog;

    @Override
    protected void onStart() {
        super.onStart();
        if (AuthRepository.getInstance() != null && AuthRepository.getInstance().isAuthenticated()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();

        ViewModelProvider loginViewModelProvider = new ViewModelProvider(this, new LoginViewModelFactory(getApplication()));
        loginViewModel = loginViewModelProvider.get(LoginViewModel.class);

        observeLoginResult();
        loginViewModel.observeFirebaseLoginResult(this);

        registerLink.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v -> {
            String email = ViewHelper.getEdtText(edtEmail);
            String password = ViewHelper.getEdtText(edtPassword);
            loginViewModel.login(email, password);
            showLoadingDialog();
        });
    }

    private void observeLoginResult() {
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            hideLoadingDialog();
            AuthenticatedUserView userView = loginResult.getSuccess();
            String error = loginResult.getError();
            if (userView != null) {
                ViewHelper.fireSuccessAlert(this, "Successfully Logged in");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            if (error != null) {
                ViewHelper.fireErrorAlert(this, error);
            }
        });
    }

    private void initUI() {
        edtEmail = findViewById(R.id.edt_email_login);
        edtPassword = findViewById(R.id.edt_password_login);
        btnLogin = findViewById(R.id.btn_login);
        registerLink = findViewById(R.id.create);
        edtLayoutPassword = findViewById(R.id.edt_layout_password_login);
        textInputConfiguration();
    }

    private void textInputConfiguration() {
        String edtPasswordHint = getString(R.string.email_edt_placeholder);
        edtPassword.setOnFocusChangeListener((v, hasFocus) ->
                ViewHelper.hideTextInputHint(hasFocus, edtPassword, edtLayoutPassword, edtPasswordHint));
    }

    private void showLoadingDialog() {
        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void hideLoadingDialog() {
        alertDialog.dismissWithAnimation();
    }
}