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
        holder.textViewUuid.setText(fare.getUuid());
        holder.textViewStatus.setText(fare.getStatus());
        holder.textViewUser.setText(fare.getUser().getEmail());
        holder.textViewCreatedAt.setText(fare.getCreatedAt());
        holder.textViewUpdatedAt.setText(fare.getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        return fareList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUuid;
        public TextView textViewStatus;
        public TextView textViewUser;
        public TextView textViewCreatedAt;
        public TextView textViewUpdatedAt;

        public ViewHolder(View view) {
            super(view);

            textViewUuid = view.findViewById(R.id.fare_uuid_tv);
            textViewStatus = view.findViewById(R.id.fare_status_tv);
            textViewUser = view.findViewById(R.id.fare_user_tv);
            textViewCreatedAt = view.findViewById(R.id.fare_created_tv);
            textViewUpdatedAt = view.findViewById(R.id.fare_updated_tv);
        }
    }
}
