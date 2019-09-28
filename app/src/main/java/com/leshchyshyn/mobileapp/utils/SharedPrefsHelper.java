package com.leshchyshyn.mobileapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SharedPrefsHelper {
    private static final String SHARED_PREFS = "SHARED_PREFS";
    private static final String SAVED_USERNAME = "SAVED_USERNAME";
    private Context mContext;

    public SharedPrefsHelper() {
        mContext = getApplicationContext();
    }

    public void saveUsername(String username) {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFS, 0);
        SharedPreferences.Editor prefsEdit = prefs.edit();

        prefsEdit.putString(SAVED_USERNAME, username);
        prefsEdit.apply();
    }

    public String loadUsername(String email) {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFS, 0);

        return prefs.getString(SAVED_USERNAME, email);
    }

    public void clearPrefs() {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFS, 0);
        prefs.edit()
                .clear()
                .apply();
    }
}