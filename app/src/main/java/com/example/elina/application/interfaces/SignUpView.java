package com.example.elina.application.interfaces;

public interface SignUpView extends LoadingView {
    void openLoginActivity();
    void openAfterSignUpActivity();
    void showLoginError(int i);
    void showPasswordError(int i);
    void showProcessCreateUser(int i, String msg);
}
