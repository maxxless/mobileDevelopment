package com.leshchyshyn.mobileapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.leshchyshyn.mobileapp.auth.Auth;
import com.leshchyshyn.mobileapp.auth.AuthenticationActivity;
import com.leshchyshyn.mobileapp.utils.SharedPrefsHelper;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private TextView nameTextView;
    private TextView phoneTextView;

    private Button signOutBtn;

    private Auth auth = Auth.getInstance();
    private SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper();

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    private void initValues() {
        String displayName = Objects.requireNonNull(
                FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();

        String welcome = getString(R.string.welcome) + displayName;

        String phone = getString(R.string.userPhoneLabel) + sharedPrefsHelper.loadPhone();

        nameTextView.setText(welcome);
        phoneTextView.setText(phone);
    }

    private void initListeners() {
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        auth.signOut();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initValues();
        initListeners();

        requestPermission();
    }

    public void initView() {
        nameTextView = findViewById(R.id.name_tv);
        phoneTextView = findViewById(R.id.phone_tv);

        signOutBtn = findViewById(R.id.sign_out_btn);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
