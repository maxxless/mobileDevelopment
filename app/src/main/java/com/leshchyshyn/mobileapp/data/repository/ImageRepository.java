package com.leshchyshyn.mobileapp.data.repository;

import com.leshchyshyn.mobileapp.data.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageRepository implements IRepository {

    private List<Image> imageList;

    public ImageRepository(List<Image> list) {
        this.imageList = list;
    }

    public List<Image> getByName(final String name) {
        List<Image> images = new ArrayList<>();

        for (Image image : imageList) {
            if (image.getTitle().equals(name)) {
                images.add(image);
            }
        }

        return images;
    }

    @Override
    public List<Image> getList() {
        return imageList;
    }

    @Override
    public void create(Object item) {
        imageList.add((Image) item);
    }
}
