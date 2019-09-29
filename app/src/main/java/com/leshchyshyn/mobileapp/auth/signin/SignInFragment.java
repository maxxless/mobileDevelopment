package com.leshchyshyn.mobileapp.auth.signin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.leshchyshyn.mobileapp.R;

import java.util.Collections;

public class SignInFragment extends Fragment implements SignInContract.ISignInView {

    private View view;
    private SignInPresenter signInPresenter;

    private EditText loginEt;
    private EditText passwordEt;

    private Button signInBtn;

    private TextView forgotPasswordTv;
    private TextView signUpTv;

    private SignInButton googleImg;
    private LoginButton facebookBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signin, container, false);

        initView();
        initPresenter();
        initListeners();

        return view;
    }

    private void initView() {
        loginEt =  view.findViewById(R.id.username_et);
        passwordEt =  view.findViewById(R.id.password_et);

        signInBtn =  view.findViewById(R.id.signin_btn);

        forgotPasswordTv = view.findViewById(R.id.forgot_password_txt);
        signUpTv =  view.findViewById(R.id.signup_txt);

        googleImg = view.findViewById(R.id.google_signIn_button);
        facebookBtn = view.findViewById(R.id.facebook_btn);
        facebookBtn.setReadPermissions("email");

        loginEt.setError(null);
        passwordEt.setError(null);
    }

    private void initPresenter() {
        signInPresenter = new SignInPresenter(this, getActivity());
    }

    private void initListeners() {
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEt.getText().toString();
                String password = passwordEt.getText().toString();
                signInPresenter.signIn(email, password);
            }
        });

        forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInPresenter.showForgotPassword();
            }
        });

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInPresenter.showSignUp();
            }
        });

        googleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInPresenter.googleSignIn();
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInPresenter.facebookSignIn();
            }
        });
    }

    @Override
    public void showEmailError() {
        loginEt.setError("Please enter valid email");
    }

    @Override
    public void showPasswordError() {
        passwordEt.setError("Please enter valid password");
    }

}