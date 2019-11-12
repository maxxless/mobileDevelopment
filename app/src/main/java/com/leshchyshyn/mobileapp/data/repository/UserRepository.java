package com.leshchyshyn.mobileapp.data.repository;

import com.leshchyshyn.mobileapp.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IRepository {

    private List<User> userList;

    public UserRepository(List<User> list) {
        this.userList = list;
    }

    public List<User> getByName(final String name) {
        List<User> users = new ArrayList<>();

        for (User user : userList) {
            if (user.getFirstName().equals(name)) {
                users.add(user);
            }
        }

        return users;
    }

    @Override
    public List<User> getList() {
        return userList;
    }

    @Override
    public void create(Object item) {
        userList.add((User) item);
    }
}
