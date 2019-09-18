package com.leshchyshyn.mobileapp.auth.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leshchyshyn.mobileapp.R;

public class SignUpFragment extends Fragment {

    private SignUpContract.ISignUpView view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    private void initView(View root) {
        view = new SignUpView();
        view.init(root);
    }

    private void initPresenter() {
        SignUpContract.ISignUpPresenter presenter = new SignUpPresenter(this, view);
        view.setPresenter(presenter);
    }
}


