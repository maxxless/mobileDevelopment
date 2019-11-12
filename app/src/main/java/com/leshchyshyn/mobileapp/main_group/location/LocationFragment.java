package com.leshchyshyn.mobileapp.main_group.location;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.Location;

public class LocationFragment extends Fragment {

    private TextView nameTv;
    private TextView descriptionTv;
    private TextView idTv;

    private Location location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        nameTv = view.findViewById(R.id.location_name);
        idTv = view.findViewById(R.id.location_id);
        descriptionTv = view.findViewById(R.id.location_description);

        setData(location);
        return view;
    }

    public void setData(Location location) {
        nameTv.setText(location.getName());
        descriptionTv.setText(location.getDescription());
        idTv.setText(String.valueOf(location.getId()));
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
