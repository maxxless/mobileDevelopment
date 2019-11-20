package com.leshchyshyn.mobileapp.main_group.images;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.leshchyshyn.mobileapp.api.ApiService;
import com.leshchyshyn.mobileapp.api.RetrofitClient;
import com.leshchyshyn.mobileapp.data.model.Image;
import com.leshchyshyn.mobileapp.data.repository.ImageRepository;
import com.leshchyshyn.mobileapp.main_group.adapter.ImageAdapter;
import com.leshchyshyn.mobileapp.utils.InternetConnection;
import com.leshchyshyn.mobileapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesPresenter implements IImagesPresenter {

    private IImagesView imageView;
    private Context context;

    private ImageAdapter adapter;
    private ImageRepository imageRepository;
    private String responseBody;

    public ImagesPresenter(IImagesView imageView, Context context) {
        this.imageView = imageView;
        this.context = context;
        this.adapter = new ImageAdapter(new ArrayList<>());
        this.imageView.setAdapter(adapter);
    }

    public void loadData() {
        imageView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(context)) {
            imageView.showProgress();

            ApiService api = RetrofitClient.getImagesRetroClient();

            Call<JsonArray> jsonArrayCall = api.getPhotos();

            jsonArrayCall.enqueue(new Callback<JsonArray>() {

                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try {
                        responseBody = Objects.requireNonNull(response.body()).toString();
                        Type type = new TypeToken<List<Image>>() {
                        }.getType();
                        List<Image> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                        imageRepository = new ImageRepository(arrayList);

                        setList();
                        imageView.setEnabledSearch(true);
                        imageView.hideProgress();
                        imageView.hideRefreshing();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    imageView.hideProgress();
                }
            });
        } else {
            imageView.showNotInternetConnection(context);
            imageView.setEnabledSearch(false);
            imageView.hideRefreshing();
        }
    }

    @Override
    public void setList() {
        adapter.updateImages(imageRepository.getList());
    }

    @Override
    public void searchImageByTitle(final String title) {
        if (imageRepository.getByName(title) == null) {
            imageView.showNotFound(context);
        } else {
            adapter = new ImageAdapter(
                    imageRepository.getByName(title));
            imageView.setAdapter(adapter);
        }
    }
}
