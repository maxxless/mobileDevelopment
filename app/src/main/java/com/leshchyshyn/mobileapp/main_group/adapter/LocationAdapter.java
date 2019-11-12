package com.leshchyshyn.mobileapp.main_group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.Location;
import com.leshchyshyn.mobileapp.main_group.locations.LocationsFragment;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private Context mContext;
    private List<Location> locationList;

    private LocationsFragment locationFragment;

    public LocationAdapter(Context context, List<Location> locationList) {
        this.mContext = context;
        this.locationList = locationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_locations, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Location location = locationList.get(position);
        holder.name_tv.setText(location.getName());
        holder.description_tv.setText(location.getDescription());

        holder.see_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationFragment = new LocationsFragment();

                FragmentManager fragmentManager =
                        ((AppCompatActivity) mContext).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_container, locationFragment)
                        .addToBackStack(null)
                        .commit();
                fragmentManager.executePendingTransactions();
                locationFragment.showLocation(location);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name_tv;
        public TextView description_tv;
        public Button see_details_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.row_view_location_name_tv);
            description_tv = itemView.findViewById(R.id.row_view_location_description_tv);

            see_details_btn = itemView.findViewById(R.id.row_location_see_details_btn);
        }
    }
}
