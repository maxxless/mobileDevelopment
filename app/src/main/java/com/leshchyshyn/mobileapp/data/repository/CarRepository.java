package com.leshchyshyn.mobileapp.data.repository;

import com.leshchyshyn.mobileapp.data.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarRepository implements IRepository {

    private List<Car> carList;

    public CarRepository(List<Car> list) {
        this.carList = list;
    }

    public Car getById(final int id) {
        Car carToBeFound = null;

        for (Car car : carList) {
            if (car.getId() == id) {
                carToBeFound = car;
            }
        }

        return carToBeFound;
    }

    public List<Car> getByName(final String name) {
        List<Car> cars = new ArrayList<>();

        for (Car car : carList) {
            if (car.getName().equals(name)) {
                cars.add(car);
            }
        }

        return cars;
    }

    @Override
    public List<Car> getList() {
        return carList;
    }

    @Override
    public void create(Object item) {
        carList.add((Car) item);
    }
}
