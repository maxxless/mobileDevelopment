package com.leshchyshyn.mobileapp.main_group.user;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.leshchyshyn.mobileapp.api.ApiService;
import com.leshchyshyn.mobileapp.api.RetrofitClient;
import com.leshchyshyn.mobileapp.data.model.User;
import com.leshchyshyn.mobileapp.data.repository.UserRepository;
import com.leshchyshyn.mobileapp.main_group.adapter.UserAdapter;
import com.leshchyshyn.mobileapp.utils.InternetConnection;
import com.leshchyshyn.mobileapp.utils.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter implements IUserPresenter {

    private IUserView userView;
    private Context mContext;

    private String responseBody;
    private UserRepository userRepository;
    private UserAdapter adapter;

    public UserPresenter(IUserView userView, Context context) {
        this.userView = userView;
        this.mContext = context;
        this.adapter = new UserAdapter(new ArrayList<>());
        this.userView.setAdapter(adapter);
    }

    @Override
    public void loadData() {
        userView.setEnabledSearch(false);

        if (InternetConnection.checkConnection(mContext)) {
            userView.showProgress();

            ApiService api = RetrofitClient.getRetroClient();

            Call<JsonArray> jsonArrayCall = api.getUsers();

            jsonArrayCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try {
                        responseBody = Objects.requireNonNull(response.body()).toString();

                        Type type = new TypeToken<List<User>>() {
                        }.getType();
                        List<User> arrayList = JSONParser.getFromJSONtoArrayList(responseBody, type);
                        userRepository = new UserRepository(arrayList);

                        setList();
                        userView.setEnabledSearch(true);
                        userView.hideProgress();
                        userView.hideRefreshing();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    userView.hideProgress();
                    userView.hideProgress();
                }
            });
        } else {
            userView.showNotInternetConnection(mContext);
            userView.setEnabledSearch(false);
            userView.hideProgress();
            userView.hideRefreshing();
        }
    }

    @Override
    public void setList() {
        adapter.updateUsers(userRepository.getList());
    }

    @Override
    public void searchUserByName(String name) {
        if (name.isEmpty() || userRepository.getByName(name) == null) {
            userView.showNotFound(mContext);
        } else {
            adapter = new UserAdapter(userRepository.getByName(name));
            userView.setAdapter(adapter);
        }
    }
}
