package com.leshchyshyn.mobileapp.main_group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.Location;
import com.leshchyshyn.mobileapp.main_group.locations.LocationsFragment;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> locationList;

    private LocationsFragment locationFragment;

    public LocationAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_locations, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Context mContext = holder.itemView.getContext();

        final Location location = locationList.get(position);
        holder.nameTv.setText(location.getName());
        holder.descriptionTv.setText(location.getDescription());

        holder.seeDetailsBtn.setOnClickListener(view -> {
            locationFragment = new LocationsFragment();

            FragmentManager fragmentManager =
                    ((AppCompatActivity) mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main_container, locationFragment)
                    .addToBackStack(null)
                    .commit();
            fragmentManager.executePendingTransactions();
            locationFragment.showLocation(location);
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public void updateLocation(final List<Location> locations) {
        this.locationList = locations;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private TextView descriptionTv;
        private Button seeDetailsBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.row_view_location_name_tv);
            descriptionTv = itemView.findViewById(R.id.row_view_location_description_tv);

            seeDetailsBtn = itemView.findViewById(R.id.row_location_see_details_btn);
        }
    }
}
