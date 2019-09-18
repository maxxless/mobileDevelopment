package com.leshchyshyn.mobileapp.auth.forgotpassword;

import android.text.TextUtils;

import com.leshchyshyn.mobileapp.auth.AuthenticationActivity;

import java.util.Objects;

public class ForgotPasswordPresenter implements ForgotPasswordContract.IForgotPasswordPresenter {

    private ForgotPasswordFragment fragment;
    private ForgotPasswordContract.IForgotPasswordView view;

    ForgotPasswordPresenter(ForgotPasswordFragment fragment, ForgotPasswordContract.IForgotPasswordView view){
        this.fragment = fragment;
        this.view = view;
    }

    @Override
    public void sendRecoverCodeClick() {
        validateInput();
    }

    @Override
    public void showSignIn() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).showSignIn();
    }

    private void validateInput() {
        view.hideEmailError();

        String email = view.getEmail();

        if (TextUtils.isEmpty(email)) {
            view.showEmailError();
            return;
        }

        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).sendRecoverCode(view.getEmail());
    }
}
