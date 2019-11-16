package com.leshchyshyn.mobileapp.main_group.fare;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.leshchyshyn.mobileapp.api.ApiService;
import com.leshchyshyn.mobileapp.api.RetrofitClient;
import com.leshchyshyn.mobileapp.data.model.Fare;
import com.leshchyshyn.mobileapp.data.repository.FareRepository;
import com.leshchyshyn.mobileapp.main_group.adapter.FareAdapter;
import com.leshchyshyn.mobileapp.utils.InternetConnection;
import com.leshchyshyn.mobileapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarePresenter implements IFarePresenter {

    private IFareView fareView;
    private Context context;

    private FareAdapter adapter;
    private FareRepository fareRepository;
    private String responseBody;

    public FarePresenter(IFareView fareView, Context context) {
        this.fareView = fareView;
        this.context = context;
        this.adapter = new FareAdapter(new ArrayList<>());
        this.fareView.setAdapter(adapter);
    }

    @Override
    public void loadData() {
        fareView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(context)) {
            fareView.showProgress();

            ApiService api = RetrofitClient.getRetroClient();

            Call<JsonArray> jsonArrayCall = api.getFares();

            jsonArrayCall.enqueue(new Callback<JsonArray>() {

                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try {
                        responseBody = Objects.requireNonNull(response.body()).toString();
                        Type type = new TypeToken<List<Fare>>() {
                        }.getType();
                        List<Fare> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                        fareRepository = new FareRepository(arrayList);

                        setList();
                        fareView.setEnabledSearch(true);
                        fareView.hideProgress();
                        fareView.hideRefreshing();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    fareView.hideProgress();
                    fareView.hideProgress();
                }
            });
        } else {
            fareView.showNotInternetConnection(context);
            fareView.setEnabledSearch(false);
            fareView.hideProgress();
            fareView.hideRefreshing();
        }
    }

    @Override
    public void setList() {
        adapter.updateFares(fareRepository.getList());
    }

    @Override
    public void searchFareByUuid(final String title) {
        if (fareRepository.getByUuid(title) == null) {
            fareView.showNotFound(context);
        } else {
            adapter = new FareAdapter(
                    fareRepository.getByUuid(title));
            fareView.setAdapter(adapter);
        }
    }
}
