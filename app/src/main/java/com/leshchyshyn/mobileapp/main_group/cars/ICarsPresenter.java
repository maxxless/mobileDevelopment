package com.leshchyshyn.mobileapp.main_group.cars;

import com.leshchyshyn.mobileapp.main_group.IMainPresenter;

public interface ICarsPresenter extends IMainPresenter {
    void findCarByName(final String name);
}
