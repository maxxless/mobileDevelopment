package com.leshchyshyn.mobileapp.main_group.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.Fare;

import java.util.List;

public class FareAdapter extends RecyclerView.Adapter<FareAdapter.ViewHolder> {

    private List<Fare> fareList;

    public FareAdapter(List<Fare> fareList) {
        this.fareList = fareList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_fare, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Fare fare = fareList.get(position);
        holder.uuidTv.setText(fare.getUuid());
        holder.statusTv.setText(fare.getStatus());
        holder.userTv.setText(fare.getUser().getEmail());
        holder.createdAtTv.setText(fare.getCreatedAt());
        holder.updatedAtTv.setText(fare.getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        return fareList.size();
    }

    public void updateFares(final List<Fare> fares) {
        this.fareList = fares;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView uuidTv;
        private TextView statusTv;
        private TextView userTv;
        private TextView createdAtTv;
        private TextView updatedAtTv;

        private ViewHolder(View view) {
            super(view);

            uuidTv = view.findViewById(R.id.fare_uuid_tv);
            statusTv = view.findViewById(R.id.fare_status_tv);
            userTv = view.findViewById(R.id.fare_user_tv);
            createdAtTv = view.findViewById(R.id.fare_created_tv);
            updatedAtTv = view.findViewById(R.id.fare_updated_tv);
        }
    }
}
