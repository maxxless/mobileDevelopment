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
import com.leshchyshyn.mobileapp.data.model.Car;
import com.leshchyshyn.mobileapp.main_group.cars.CarsFragment;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private List<Car> carList;

    private CarsFragment carWithDetailsFragment;

    public CarAdapter(List<Car> list) {
        this.carList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_car, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Context mContext = holder.itemView.getContext();
        final Car car = carList.get(position);

        holder.nameTv.setText(car.getName());

        holder.idTv.setText(String.valueOf(car.getId()));
        holder.typeTv.setText(car.getType());
        holder.colourTv.setText(car.getColour());

        holder.seeDetailsBtn.setOnClickListener(view -> {
            carWithDetailsFragment = new CarsFragment();

            FragmentManager fragmentManager =
                    ((AppCompatActivity) mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main_container, carWithDetailsFragment)
                    .addToBackStack(null)
                    .commit();
            fragmentManager.executePendingTransactions();
            carWithDetailsFragment.showCar(car);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void updateCars(final List<Car> cars) {
        this.carList = cars;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private TextView idTv;
        private TextView typeTv;
        private TextView colourTv;

        private Button seeDetailsBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.row_car_name_tv);
            idTv = itemView.findViewById(R.id.row_car_id_tv);
            typeTv = itemView.findViewById(R.id.row_car_type_tv);
            colourTv = itemView.findViewById(R.id.row_car_colour_tv);

            seeDetailsBtn = itemView.findViewById(R.id.row_car_see_details_btn);
        }
    }
}
