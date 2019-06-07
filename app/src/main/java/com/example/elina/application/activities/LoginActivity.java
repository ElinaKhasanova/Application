package com.example.elina.application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.elina.application.R;
import com.example.elina.application.interfaces.LoginView;
import com.example.elina.application.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity
                            implements LoginView, View.OnClickListener {

    private LoginPresenter mLoginPresenter;

    private EditText inputEmail, inputPassword;
    //    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    public void openMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void openSignUpActivity() {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    @Override
    public void showLoginError(int i) {
        switch (i) {
            case 1:
                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showPasswordError(int i) {
        switch (i) {
            case 1:
                inputPassword.setError(getString(R.string.minimum_password));
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));;
//            finish();
//        }

        mLoginPresenter = new LoginPresenter(this);
        mLoginPresenter.init();

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);

//        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
//                mLoginPresenter.openSignUpActivity();
                openSignUpActivity();
                break;
            case R.id.btn_login:
                mLoginPresenter.doLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
                break;
            case R.id.btn_reset_password:
                mLoginPresenter.resetPassword();
//                resetPassword();
//                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;
        }
    }
}

    //    private void resetPassword() {
//        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.activity_reset_password, null);
//        dialogBuilder.setView(dialogView);
//        final EditText editEmail = dialogView.findViewById(R.id.email);
//        final Button btnReset = dialogView.findViewById(R.id.btn_reset_password);
//        final ProgressBar progressBar1 = (ProgressBar) dialogView.findViewById(R.id.progressBar);
//
//        final AlertDialog dialog = dialogBuilder.create();
//        btnReset.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String email = editEmail.getText().toString().trim();
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                progressBar1.setVisibility(View.VISIBLE);
//                auth.sendPasswordResetEmail(email)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
//                                }
//                                progressBar1.setVisibility(View.GONE);
//                                dialog.dismiss();
//                            }
//                        });
//            }
//        });
//        dialog.show();
//    }

