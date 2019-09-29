package com.leshchyshyn.mobileapp.utils;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.fragment.app.FragmentManager;

import java.util.regex.Pattern;

public class Utils {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9!@#$]{8,24}");

    private Utils() {

    }

    public static boolean isFragmentInBackStack(final FragmentManager fragmentManager,
                                                final String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }
}
