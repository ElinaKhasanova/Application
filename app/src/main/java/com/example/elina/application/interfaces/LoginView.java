package com.example.elina.application.interfaces;

public interface LoginView extends LoadingView {
    void openMainActivity();
    void openSignUpActivity();
    void showLoginError(int i);
    void showPasswordError(int i);
}
