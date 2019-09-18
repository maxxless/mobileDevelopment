package com.leshchyshyn.mobileapp.auth.forgotpassword;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leshchyshyn.mobileapp.R;


public class ForgotPasswordFragment extends Fragment {

    private ForgotPasswordView view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    private void initView(View root) {
        view = new ForgotPasswordView();
        view.init(root);
    }

    private void initPresenter() {
        ForgotPasswordPresenter presenter = new ForgotPasswordPresenter(this, view);
        view.setPresenter(presenter);
    }
}
