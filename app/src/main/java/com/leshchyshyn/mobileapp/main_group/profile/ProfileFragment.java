package com.leshchyshyn.mobileapp.main_group.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.utils.SharedPrefsHelper;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private TextView nameTextView;
    private TextView phoneTextView;

    private SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = view.findViewById(R.id.name_tv);
        phoneTextView = view.findViewById(R.id.phone_tv);

        initValues();
        return view;
    }

    private void initValues() {
        String displayName = Objects.requireNonNull(
                FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();

        String welcome = getString(R.string.welcome) + displayName;

        String phone = getString(R.string.user_phone_label) + sharedPrefsHelper.loadPhone();

        nameTextView.setText(welcome);
        phoneTextView.setText(phone);
    }
}
