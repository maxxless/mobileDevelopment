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
    private Button signOutBtn;

    private Auth auth = Auth.getInstance();

    private SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initValues();
        initListeners();

        requestPermission();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    public void initView() {
        nameTextView = findViewById(R.id.name_tv);
        signOutBtn = findViewById(R.id.sign_out_btn);
    }

    private void initValues() {
        String text = "Welcome: " + sharedPrefsHelper.loadUsername(
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

        nameTextView.setText(text);
    }

    private void initListeners() {
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    public void signOut() {
        auth.signOut();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}