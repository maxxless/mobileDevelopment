package com.leshchyshyn.mobileapp.main_group.cars;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.leshchyshyn.mobileapp.api.ApiService;
import com.leshchyshyn.mobileapp.api.RetrofitClient;
import com.leshchyshyn.mobileapp.data.model.Car;
import com.leshchyshyn.mobileapp.data.repository.CarRepository;
import com.leshchyshyn.mobileapp.main_group.adapter.CarAdapter;
import com.leshchyshyn.mobileapp.utils.InternetConnection;
import com.leshchyshyn.mobileapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsPresenter implements ICarsPresenter {

    private ICarsView carsView;
    private Context context;

    private CarAdapter adapter;
    private CarRepository carRepository;
    private String responseBody;

    public CarsPresenter(ICarsView iCarsView, Context context) {
        this.carsView = iCarsView;
        this.context = context;
        this.adapter = new CarAdapter(new ArrayList<>());
        this.carsView.setAdapter(this.adapter);
    }

    @Override
    public void loadData() {
        carsView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(context)) {
            carsView.showProgress();

            ApiService api = RetrofitClient.getRetroClient();

            Call<JsonArray> jsonArrayCall = api.getCars();

            jsonArrayCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try {
                        responseBody = Objects.requireNonNull(response.body()).toString();

                        Type type = new TypeToken<List<Car>>() {
                        }.getType();
                        List<Car> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                        carRepository = new CarRepository(arrayList);

                        setList();
                        carsView.hideRefreshing();
                        carsView.setEnabledSearch(true);
                        carsView.hideProgress();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    carsView.hideRefreshing();
                    carsView.hideProgress();

                }
            });
        } else {
            carsView.showNotInternetConnection(context);
            carsView.hideRefreshing();
        }
    }

    @Override
    public void setList() {
        adapter.updateCars(carRepository.getList());
    }

    @Override
    public void findCarByName(final String name) {
        if (name.isEmpty() || carRepository.getByName(name) == null) {
            carsView.showNotFound(context);
        } else {
            adapter = new CarAdapter(carRepository.getByName(name));
            carsView.setAdapter(adapter);
        }
    }
}
