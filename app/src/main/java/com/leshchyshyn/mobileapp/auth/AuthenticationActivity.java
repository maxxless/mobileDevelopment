package com.leshchyshyn.mobileapp.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.leshchyshyn.mobileapp.MainActivity;
import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.Utils;
import com.leshchyshyn.mobileapp.auth.forgotpassword.ForgotPasswordFragment;
import com.leshchyshyn.mobileapp.auth.signin.SignInFragment;
import com.leshchyshyn.mobileapp.auth.signup.SignUpFragment;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        if (Auth.isUserAuth()) {
            startMainActivity();
        } else {
            showSignIn();
        }
    }

    public void showSignIn() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                SignInFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(SignInFragment.class.getName(), 0);
        } else {
            Fragment fragment = new SignInFragment();
            transaction.replace(R.id.fragment, fragment)
                    .addToBackStack(SignInFragment.class.getName())
                    .commit();
        }
    }

    public void showSignUp() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                SignUpFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(SignUpFragment.class.getName(), 0);
        } else {
            Fragment fragment = new SignUpFragment();
            transaction.replace(R.id.fragment, fragment)
                    .addToBackStack(SignUpFragment.class.getName())
                    .commit();
        }
    }

    public void showForgotPassword() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (Utils.isFragmentInBackstack(getSupportFragmentManager(),
                ForgotPasswordFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(ForgotPasswordFragment.class.getName(), 0);
        } else {
            Fragment fragment = new ForgotPasswordFragment();
            transaction.replace(R.id.fragment, fragment)
                    .addToBackStack(ForgotPasswordFragment.class.getName())
                    .commit();
        }
    }

    public void signIn(String email, String password) {
        Auth.signIn(email, password, this);
    }

    public void signUp(String email, String password) {
        Auth.signUp(email, password, this);
    }

    public void googleSignIn() {
        Auth.googleSignIn(this);
    }

    public void facebookSignIn() {
        Auth.facebookSignIn(this);
    }

    public void sendRecoverCode(String email) {
        Auth.sendRecoverCode(email);
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Auth.onActivityResult(requestCode, resultCode, data, this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
