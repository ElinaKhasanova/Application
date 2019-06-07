package com.example.elina.application.presenters;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.elina.application.interfaces.SignUpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter {
    private final SignUpView mSignUpView;
    private FirebaseAuth auth;

    public SignUpPresenter(SignUpView mSignUpView) {
        this.mSignUpView = mSignUpView;
    }

    public void init() {
        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            mLoginView.openMainActivity();
//        }
    }

    public void doSignUp(String email, final String password){
        if (TextUtils.isEmpty(email)) {
            mSignUpView.showLoginError(1);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mSignUpView.showPasswordError(1);
            return;
        }
        if (password.length() < 6) {
            mSignUpView.showPasswordError(2);
            return;
        }
        mSignUpView.showLoading();
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mSignUpView.showProcessCreateUser(1, String.valueOf(task.isSuccessful()));
                        mSignUpView.hideLoading();
                        if (!task.isSuccessful()) {
                            String message = task.getException().toString();
                            mSignUpView.showProcessCreateUser(2, message);
                        } else {
                            mSignUpView.openAfterSignUpActivity();
                        }
                    }
                });
    }
}
