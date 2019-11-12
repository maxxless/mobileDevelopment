package com.leshchyshyn.mobileapp.main_group.locations;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.leshchyshyn.mobileapp.api.ApiService;
import com.leshchyshyn.mobileapp.api.RetrofitClient;
import com.leshchyshyn.mobileapp.data.model.Location;
import com.leshchyshyn.mobileapp.data.repository.LocationRepository;
import com.leshchyshyn.mobileapp.main_group.adapter.LocationAdapter;

import com.leshchyshyn.mobileapp.utils.InternetConnection;
import com.leshchyshyn.mobileapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class LocationPresenter implements ILocationPresenter {

    private ILocationView locationView;
    private Context mContext;
    private String responseBody;
    private LocationRepository locationRepository;

    public LocationPresenter(ILocationView iLocationView, Context mContext) {
        this.locationView = iLocationView;
        this.mContext = mContext;
    }

    @Override
    public void loadData() {
        locationView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(mContext)) {
            locationView.showProgress();

            ApiService api = RetrofitClient.getRetroClient();

            Call<JsonArray> jsonArrayCall = api.getLocations();

            jsonArrayCall.enqueue(new Callback<JsonArray>() {

                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try {
                        responseBody = Objects.requireNonNull(response.body()).toString();
                        Type type = new TypeToken<List<Location>>() {
                        }.getType();
                        List<Location> arrayList =
                                JSONParser.getFromJSONtoArrayList(responseBody, type);
                        locationRepository = new LocationRepository(arrayList);

                        setList();
                        locationView.setEnabledSearch(true);
                        locationView.hideProgress();
                        locationView.hideRefreshing();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    locationView.hideProgress();
                }
            });
        } else
            locationView.showNotInternetConnection();
        locationView.setEnabledSearch(false);
        locationView.hideRefreshing();
    }

    @Override
    public void setList() {
        RecyclerView.Adapter adapter = new LocationAdapter(mContext, locationRepository.getList());
        locationView.setAdapter(adapter);
    }

    @Override
    public void searchLocationById(String id) {
        if (id.matches("") || locationRepository.getById(parseInt(id)) == null) {
            locationView.showNotFound();
        } else {
            locationView.showLocation(locationRepository.getById(parseInt(id)));
        }
    }
}
