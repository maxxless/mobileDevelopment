package com.leshchyshyn.mobileapp.auth.signin;

import android.app.Activity;

import com.leshchyshyn.mobileapp.auth.IAuthenticationView;

import static com.leshchyshyn.mobileapp.utils.Utils.isValidEmail;
import static com.leshchyshyn.mobileapp.utils.Utils.isValidPassword;

public class SignInPresenter implements SignInContract.ISignInPresenter {

    private SignInContract.ISignInView view;
    private IAuthenticationView authenticationView;

    public SignInPresenter(SignInContract.ISignInView view, Activity activity) {
        this.view = view;
        authenticationView = (IAuthenticationView) activity;
    }

    public void signIn(final String email, final String password) {
        if (validateInput(email, password)) {
            authenticationView.signIn(email, password);
        }
    }

    public void googleSignIn() {
        authenticationView.googleSignIn();
    }

    public void facebookSignIn() {
        authenticationView.facebookSignIn();
    }

    private boolean validateInput(final String email, final String password) {
        boolean isEmailOk = isValidEmail(email);
        boolean isPasswordOk = isValidPassword(password);

        if (!isEmailOk) {
            view.showEmailError();
        }

        if (!isPasswordOk) {
            view.showPasswordError();
        }

        return (isEmailOk && isPasswordOk);
    }

    public void showForgotPassword() {
        authenticationView.showForgotPassword();
    }

    public void showSignUp() {
        authenticationView.showSignUp();
    }
}