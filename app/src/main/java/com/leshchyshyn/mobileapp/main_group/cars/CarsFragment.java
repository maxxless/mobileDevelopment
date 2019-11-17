package com.leshchyshyn.mobileapp.main_group.cars;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.Car;
import com.leshchyshyn.mobileapp.main_group.car.CarWithDetailsFragment;

import java.util.Objects;

public class CarsFragment extends Fragment implements ICarsView, View.OnClickListener {

    private View view;
    private Context mContext;

    private RecyclerView recyclerView;
    private FloatingActionButton searchFab;

    private CarsPresenter carsPresenter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cars, container, false);
        mContext = view.getContext();

        initView();
        initListener();
        initPresenter();

        carsPresenter.loadData();

        swipeRefreshLayout.setOnRefreshListener(() -> carsPresenter.loadData());

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.car_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        searchFab = view.findViewById(R.id.car_search_fab);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshCars);
    }

    private void initListener() {
        searchFab.setOnClickListener(this);
    }

    private void initPresenter() {
        carsPresenter = new CarsPresenter(this, mContext);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.car_search_fab) {
            final EditText editText = new EditText(mContext);
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle(R.string.search_car)
                    .setMessage(R.string.search_car_message)
                    .setView(editText)
                    .setPositiveButton(R.string.search_label, (dialogInterface, i) -> {
                        String id = editText.getText().toString();
                        carsPresenter.findCarByName(id);
                    })
                    .setNegativeButton(R.string.close, null)
                    .create();
            dialog.show();
        }
    }

    private void showProgressLoaderWithBackground(boolean visibility, String text) {
        if (text == null) {
            text = "";
        }

        ((TextView) view.findViewById(R.id.progress_bar_text)).setText(text);

        if (visibility) {
            view.findViewById(R.id.container_progress_bar).setVisibility(View.VISIBLE);
            Objects.requireNonNull(getActivity()).getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            view.findViewById(R.id.container_progress_bar).setVisibility(View.GONE);
            Objects.requireNonNull(getActivity()).getWindow()
                    .clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void showProgress() {
        showProgressLoaderWithBackground(true, mContext.getString(R.string.load_data));
    }

    @Override
    public void hideProgress() {
        showProgressLoaderWithBackground(false, mContext.getString(R.string.load_data));
    }

    @Override
    public void hideRefreshing() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setEnabledSearch(boolean enabled) {
        searchFab.setEnabled(enabled);
    }

    public void showCar(Car car) {
        CarWithDetailsFragment carWithDetailsFragment = new CarWithDetailsFragment();
        FragmentManager fragmentManager =
                Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, carWithDetailsFragment)
                .addToBackStack(null)
                .commit();
        carWithDetailsFragment.setCar(car);
    }
}