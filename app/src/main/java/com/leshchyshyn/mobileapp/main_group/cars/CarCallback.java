package com.leshchyshyn.mobileapp.main_group.cars;

import androidx.annotation.NonNull;

public interface CarCallback {
    void onSuccess(@NonNull String value);

    void onError(@NonNull Throwable throwable);
}