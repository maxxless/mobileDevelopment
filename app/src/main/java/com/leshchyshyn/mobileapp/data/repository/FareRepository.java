package com.leshchyshyn.mobileapp.data.repository;

import com.leshchyshyn.mobileapp.data.model.Fare;

import java.util.ArrayList;
import java.util.List;

public class FareRepository implements IRepository {

    private List<Fare> fareList;

    public FareRepository(List<Fare> list) {
        this.fareList = list;
    }

    public List<Fare> getByUuid(final String uuid) {
        List<Fare> fares = new ArrayList<>();

        for (Fare a : fareList) {
            if (a.getUuid().equals(uuid)) {
                fares.add(a);
            }
        }

        return fares;
    }

    @Override
    public List<Fare> getList() {
        return fareList;
    }

    @Override
    public void create(Object item) {
        fareList.add((Fare) item);
    }
}
