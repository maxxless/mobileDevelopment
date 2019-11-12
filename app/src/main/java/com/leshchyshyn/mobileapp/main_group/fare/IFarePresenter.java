package com.leshchyshyn.mobileapp.main_group.fare;

import com.leshchyshyn.mobileapp.main_group.IMainPresenter;

public interface IFarePresenter extends IMainPresenter {
    void searchFareByUuid(final String uuid);
}
