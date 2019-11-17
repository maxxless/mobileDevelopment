package com.leshchyshyn.mobileapp.main_group.user;

import com.leshchyshyn.mobileapp.main_group.IMainPresenter;

public interface IUserPresenter extends IMainPresenter {
    void searchUserByName(final String name);
}
