package com.leshchyshyn.mobileapp.auth.signin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leshchyshyn.mobileapp.R;

public class SignInFragment extends Fragment {

    private SignInContract.ISignInView view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signin, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    private void initView(View root) {
        view = new SignInView();
        view.init(root);
    }

    private void initPresenter() {
        SignInContract.ISignInPresenter presenter = new SignInPresenter(view, this);
        view.setPresenter(presenter);
    }

}