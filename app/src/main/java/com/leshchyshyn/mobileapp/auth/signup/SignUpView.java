package com.leshchyshyn.mobileapp.auth.signup;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.leshchyshyn.mobileapp.R;

public class SignUpView implements SignUpContract.ISignUpView {

    private EditText usernameEt, emailEt, phoneEt, passwordEt, confirmPasswordEt;
    private SignUpContract.ISignUpPresenter presenter;

    @Override
    public void init(View root) {
        Button signUpBtn = root.findViewById(R.id.signup_btn);
        TextView signInTv = root.findViewById(R.id.signin_txt);
        usernameEt = root.findViewById(R.id.username_et);
        emailEt = root.findViewById(R.id.email_et);
        phoneEt = root.findViewById(R.id.phone_et);
        passwordEt = root.findViewById(R.id.password_et);
        confirmPasswordEt = root.findViewById(R.id.confirm_password_et);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signUpClick();
            }
        });

        signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showSignIn();
            }
        });
    }

    @Override
    public void setPresenter(SignUpContract.ISignUpPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void hideUsernameError() {
        usernameEt.setError(null);
    }

    @Override
    public void showUsernameError() {
        usernameEt.setError("Please enter valid username!");
    }

    @Override
    public void hideEmailError() {
        emailEt.setError(null);
    }

    @Override
    public void showEmailError() {
        emailEt.setError("Please enter valid email!");
    }

    @Override
    public void hidePhoneError() {
        phoneEt.setError(null);
    }

    @Override
    public void showPhoneError() {
        phoneEt.setError("Please enter valid phone number!");
    }

    @Override
    public void showPasswordError() {
        passwordEt.setError("Please enter valid password!");
    }

    @Override
    public void hidePasswordError() {
        passwordEt.setError(null);
    }

    @Override
    public void showConfirmError() {
        confirmPasswordEt.setError("Passwords aren't equal!");
    }

    @Override
    public void hideConfirmError() {
        confirmPasswordEt.setError(null);
    }

    @Override
    public String getUsername() {
        return usernameEt.getText().toString();
    }

    @Override
    public String getPhone() {
        return phoneEt.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEt.getText().toString();
    }

    @Override
    public String getEmail() {
        return emailEt.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return confirmPasswordEt.getText().toString();
    }
}
