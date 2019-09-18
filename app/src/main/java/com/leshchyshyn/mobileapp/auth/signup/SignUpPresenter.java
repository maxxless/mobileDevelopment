package com.leshchyshyn.mobileapp.auth.signup;

import android.text.TextUtils;
import android.util.Patterns;

import com.leshchyshyn.mobileapp.auth.AuthenticationActivity;

import java.util.Objects;

import static com.leshchyshyn.mobileapp.Utils.isValidPassword;

public class SignUpPresenter implements SignUpContract.ISignUpPresenter {

    private SignUpFragment fragment;
    private SignUpContract.ISignUpView view;

    SignUpPresenter(SignUpFragment fragment, SignUpContract.ISignUpView view) {
        this.fragment = fragment;
        this.view = view;
    }

    @Override
    public void signUpClick() {
        validateInput();
    }

    @Override
    public void showSignIn() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).showSignIn();
    }

    private void validateInput() {
        view.hideUsernameError();
        view.hideEmailError();
        view.hidePhoneError();
        view.hidePasswordError();
        view.hideConfirmError();

        String login = view.getUsername();
        String email = view.getEmail();
        String phone = view.getPhone();
        String password = view.getPassword();
        String confirmPassword = view.getConfirmPassword();

        if (TextUtils.isEmpty(login)) {
            view.showUsernameError();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            view.showEmailError();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            view.showPhoneError();
            return;
        }

        if (!isValidPassword(password)) {
            view.showPasswordError();
            return;
        }

        if (!isValidPassword(confirmPassword)) {
            view.showConfirmError();
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showConfirmError();
            return;
        }


        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity())).signUp(email, password);

    }
}
