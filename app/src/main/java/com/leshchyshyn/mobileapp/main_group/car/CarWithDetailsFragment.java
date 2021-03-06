package com.leshchyshyn.mobileapp.main_group.car;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.Car;
import com.squareup.picasso.Picasso;

public class CarWithDetailsFragment extends Fragment {

    private View view;
    private Car car;

    private TextView fullCarNameTv;
    private TextView fullCarIdTv;
    private TextView fullCarTypeTv;
    private TextView fullCarColourTv;

    private ImageView carImageIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car, container, false);

        initView();
        setInfoToCar(car);
        return view;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    private void initView() {
        RecyclerView recyclerView = view.findViewById(R.id.car_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        fullCarNameTv = view.findViewById(R.id.full_car_name_tv);
        fullCarIdTv = view.findViewById(R.id.full_car_id_tv);
        fullCarTypeTv = view.findViewById(R.id.full_car_type_tv);
        fullCarColourTv = view.findViewById(R.id.full_car_colour_tv);

        carImageIv = view.findViewById(R.id.car_image_iv);
    }

    private void setInfoToCar(Car car) {
        fullCarNameTv.setText(car.getName());
        fullCarIdTv.setText(String.valueOf(car.getId()));
        fullCarTypeTv.setText(car.getType());
        fullCarColourTv.setText(car.getColour());

        Picasso.get()
                .load(car.getImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(carImageIv);
    }
}

