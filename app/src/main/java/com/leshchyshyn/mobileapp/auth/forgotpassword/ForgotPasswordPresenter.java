package com.leshchyshyn.mobileapp.auth.forgotpassword;

import android.app.Activity;

import com.leshchyshyn.mobileapp.auth.AuthenticationActivity;
import com.leshchyshyn.mobileapp.auth.IAuthenticationView;

import static com.leshchyshyn.mobileapp.utils.Utils.isValidEmail;

public class ForgotPasswordPresenter extends AuthenticationActivity
        implements ForgotPasswordContract.IForgotPasswordPresenter {

    private ForgotPasswordContract.IForgotPasswordView view;
    private IAuthenticationView authenticationView;

    public ForgotPasswordPresenter(ForgotPasswordContract.IForgotPasswordView view, Activity activity){
        this.view = view;
        authenticationView = (IAuthenticationView) activity;
    }

    public void showSignIn (){
        authenticationView.showSignIn();
    }

    public void sendRecoveryCode(String email) {
        if(isValidEmail(email)){
            authenticationView.sendRecoveryCode(email);
            view.recoveryCodeIsSent();
        }
        else{
            view.showEmailError();
        }
    }
}
