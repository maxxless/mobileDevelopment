package com.leshchyshyn.mobileapp.auth.signin;

import android.text.TextUtils;
import android.util.Patterns;

import com.leshchyshyn.mobileapp.auth.AuthenticationActivity;

import java.util.Objects;

import static com.leshchyshyn.mobileapp.Utils.isValidPassword;

public class SignInPresenter implements SignInContract.ISignInPresenter {

    private SignInContract.ISignInView view;
    private SignInFragment fragment;

    SignInPresenter(SignInContract.ISignInView view, SignInFragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    @Override
    public void signInClick() {
        validateInput();
    }


    @Override
    public void googleSignInClick()
    {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).googleSignIn();
    }

    @Override
    public void facebookSignInClick(){
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).facebookSignIn();
    }

    private void validateInput() {
        view.hideLoginError();
        view.hidePasswordError();

        String login = view.getLoginText();
        String password = view.getPasswordText();
        if (TextUtils.isEmpty(login)) {
            view.showLoginError();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
            view.showLoginError();
            return;
        }

        if (!isValidPassword(password)) {
            view.showPasswordError();
            return;
        }

        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).signIn(login, password);
    }

    @Override
    public void showSignUp() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).showSignUp();
    }

    @Override
    public void showForgotPassword() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).showForgotPassword();
    }
}