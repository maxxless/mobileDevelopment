package com.leshchyshyn.mobileapp.main_group;

import androidx.recyclerview.widget.RecyclerView;

public interface IMainView {
    void showProgress();

    void hideProgress();

    void hideRefreshing();

    void showNotFound();

    void showNotInternetConnection();

    void setAdapter(RecyclerView.Adapter adapter);

    void setEnabledSearch(boolean enabled);
}
