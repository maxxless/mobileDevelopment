package com.leshchyshyn.mobileapp.data.repository;

import com.leshchyshyn.mobileapp.data.model.Location;

import java.util.List;

public class LocationRepository implements IRepository {

    private List<Location> locationList;

    public LocationRepository(List<Location> list) {
        this.locationList = list;
    }

    public Location getById(int id) {
        Location temp = null;

        for (Location location : locationList) {
            if (location.getId() == id) {
                temp = location;
            }
        }
        return temp;
    }

    @Override
    public List<Location> getList() {
        return locationList;
    }

    @Override
    public void create(Object item) {
        locationList.add((Location) item);
    }
}
