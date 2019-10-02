package com.leshchyshyn.mobileapp.auth.signup;

import android.app.Activity;
import android.text.TextUtils;

import com.leshchyshyn.mobileapp.auth.IAuthenticationView;

import static com.leshchyshyn.mobileapp.utils.Utils.isValidEmail;
import static com.leshchyshyn.mobileapp.utils.Utils.isValidPassword;
import static com.leshchyshyn.mobileapp.utils.Utils.isValidPhone;

public class SignUpPresenter implements SignUpContract.ISignUpPresenter {

    private SignUpContract.ISignUpView view;
    private IAuthenticationView authenticationView;

    public SignUpPresenter(SignUpContract.ISignUpView view, Activity activity) {
        this.view = view;
        authenticationView = (IAuthenticationView) activity;
    }

    public void signUp(final String username, final String email, final String phone,
                       final String password, final String confirmPassword) {
        if (validateInput(username, email, phone, password, confirmPassword)) {
            authenticationView.signUp(email, password, username, phone);
        }
    }

    public void showSignIn() {
        authenticationView.showSignIn();
    }

    private boolean validateInput(final String username, final String email, final String phone,
                                  final String password, final String confirmPassword) {
        boolean isUsernameOk = !TextUtils.isEmpty(username);
        boolean isEmailOk = isValidEmail(email);
        boolean isPhoneOk = isValidPhone(phone);
        boolean isPasswordOk = isValidPassword(password);
        boolean isConfirmPasswordOk =
                isValidPassword(confirmPassword) && confirmPassword.equals(password);

        if (!isUsernameOk) {
            view.showUsernameError();
        }

        if (!isEmailOk) {
            view.showEmailError();
        }

        if (!isPhoneOk) {
            view.showPhoneError();
        }

        if (!isPasswordOk) {
            view.showPasswordError();
        }

        if (!isConfirmPasswordOk) {
            view.showConfirmPasswordError();
        }

        return (isConfirmPasswordOk && isEmailOk && isPasswordOk && isPhoneOk && isUsernameOk);
    }
}
