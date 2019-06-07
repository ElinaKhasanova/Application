package com.example.elina.application.presenters;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.elina.application.interfaces.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private final LoginView mLoginView;
    private FirebaseAuth auth;

    public LoginPresenter(@NonNull LoginView mLoginView) {
        this.mLoginView = mLoginView;
    }

    public void init() {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            mLoginView.openMainActivity();
        }
    }

//    public void openSignUpActivity() {
//        mLoginView.openSignUpActivity();
//    }

    public void doLogin(String email, final String password){
        if (TextUtils.isEmpty(email)) {
            mLoginView.showLoginError(2);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mLoginView.showLoginError(2);
            return;
        }
        mLoginView.showLoading();
        //login user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mLoginView.hideLoading();
                        if (!task.isSuccessful()){
                            if (password.length() < 6){
                                mLoginView.showPasswordError(1);
                            }
                            else {
                                mLoginView.showLoginError(1);
                            }
                        }
                        else {
                            mLoginView.openMainActivity();
                        }
                    }
                });
    }

    public void resetPassword(){
//        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder();
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
    }
}
