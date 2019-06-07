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
import com.example.elina.application.interfaces.SignUpView;
import com.example.elina.application.presenters.SignUpPresenter;

public class SignupActivity extends AppCompatActivity
                            implements SignUpView, View.OnClickListener{

    private SignUpPresenter mSignUpPresenter;

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;

    @Override
    public void openLoginActivity() {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void openAfterSignUpActivity() {
        startActivity(new Intent(SignupActivity.this, AfterSignUpActivity.class));
        finish();
    }

    @Override
    public void showLoginError(int i) {
        switch (i){
            case 1:
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showPasswordError(int i) {
        switch (i){
            case 1:
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
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
    public void showProcessCreateUser(int i, String msg) {
        switch (i){
            case 1:
                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete: " + msg , Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(SignupActivity.this, "Sign up failed." + msg,
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        auth = FirebaseAuth.getInstance();
        mSignUpPresenter = new SignUpPresenter(this);
        mSignUpPresenter.init();

        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_button:
                mSignUpPresenter.doSignUp(inputEmail.getText().toString().trim(),
                                          inputPassword.getText().toString().trim());
                break;
            case R.id.sign_in_button:
                openLoginActivity();
                break;
//            case R.id.btn_reset_password:
//                mSignUpPresenter.resetPassword();
////                resetPassword();
////                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
//                break;
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
//        final ProgressBar progressBar1 = dialogView.findViewById(R.id.progressBar);
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
//                                    Toast.makeText(SignupActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(SignupActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
//                                }
//                                progressBar1.setVisibility(View.GONE);
//                                dialog.dismiss();
//                            }
//                        });
//            }
//        });
//        dialog.show();
//    }

