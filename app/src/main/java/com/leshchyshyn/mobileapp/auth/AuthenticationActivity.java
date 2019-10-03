package com.leshchyshyn.mobileapp.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.leshchyshyn.mobileapp.MainActivity;
import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.utils.Utils;
import com.leshchyshyn.mobileapp.auth.forgotpassword.ForgotPasswordFragment;
import com.leshchyshyn.mobileapp.auth.signin.SignInFragment;
import com.leshchyshyn.mobileapp.auth.signup.SignUpFragment;

public class AuthenticationActivity extends AppCompatActivity implements IAuthenticationView {

    private Auth auth = Auth.getInstance();
    private Utils utils = Utils.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        if (auth.isUserAuth()) {
            startMainActivity();
        } else {
            replaceFragment(new SignInFragment());
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (utils.isFragmentInBackStack(getSupportFragmentManager(),
                fragment.getClass().getName())) {
            getSupportFragmentManager().popBackStackImmediate(fragment.getClass().getName(), 0);
        } else {
            transaction.replace(R.id.fragment, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        }
    }

    public void showSignIn() {
        replaceFragment(new SignInFragment());
    }

    public void showSignUp() {
        replaceFragment(new SignUpFragment());
    }

    public void showForgotPassword() {
        replaceFragment(new ForgotPasswordFragment());
    }

    public void signIn(String email, String password) {
        auth.signIn(email, password, this);
    }

    public void signUp(String email, String password, String username, String phone) {
        auth.signUp(email, password, username, phone, this);
    }

    public void googleSignIn() {
        auth.googleSignIn(this);
    }

    public void facebookSignIn() {
        auth.facebookSignIn(this);
    }

    public void sendRecoveryCode(String email) {
        auth.sendRecoveryCode(email);
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        auth.onActivityResult(requestCode, resultCode, data, this);
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
