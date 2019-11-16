package com.leshchyshyn.mobileapp.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitClient {

    private static ApiService service;
    private static ApiService imageService;

    //URL
    private static final String IMAGES_URL = "http://jsonplaceholder.typicode.com";
    private static final String ROOT_URL = "https://lab2-256619.appspot.com/";

    public static ApiService getRetroClient() {
        if (service == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createDefaultOkHttpClient())
                    .build();

            service = retrofit.create(ApiService.class);
        }
        return service;
    }

    public static ApiService getImagesRetroClient() {
        if (imageService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(IMAGES_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createDefaultOkHttpClient())
                    .build();

            imageService = retrofit.create(ApiService.class);
        }
        return imageService;
    }

    private static OkHttpClient createDefaultOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build();
    }
}
