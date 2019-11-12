package com.leshchyshyn.mobileapp.main_group.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leshchyshyn.mobileapp.R;

public class ProfileEditFragment extends Fragment {

    private View view;
    private TextView editDoneTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        initView();
        initListeners();

        return view;
    }

    private void initView() {
        editDoneTv = view.findViewById(R.id.profile_edit_done_iv);
    }

    private void initListeners() {
        editDoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
    }

    private void saveProfile() {
        Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
    }
}
