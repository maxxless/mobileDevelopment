package com.leshchyshyn.mobileapp.auth.signup;

import android.view.View;

public interface SignUpContract {

    interface ISignUpView{
        void init(View root);

        void setPresenter(ISignUpPresenter presenter);

        void hideUsernameError();

        void showUsernameError();

        void hideEmailError();

        void showEmailError();
        
        void hidePhoneError();
        
        void showPhoneError();

        void showPasswordError();

        void hidePasswordError();

        void showConfirmError();

        void hideConfirmError();

        String getUsername();

        String getPassword();

        String getEmail();

        String getPhone();

        String getConfirmPassword();
    }

    interface ISignUpPresenter{
        void signUpClick();

        void showSignIn();
    }
}
