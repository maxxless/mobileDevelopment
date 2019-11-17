package com.leshchyshyn.mobileapp.main_group.locations;

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
import com.leshchyshyn.mobileapp.data.model.Location;
import com.leshchyshyn.mobileapp.main_group.location.LocationFragment;

import java.util.Objects;

public class LocationsFragment extends Fragment implements ILocationView, View.OnClickListener {

    private View view;
    private Context mContext;
    private RecyclerView recyclerView;

    private FloatingActionButton searchFab;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LocationPresenter locationPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_locations, container, false);
        mContext = view.getContext();

        initView();
        initListeners();
        initPresenter();

        locationPresenter.loadData();

        swipeRefreshLayout.setOnRefreshListener(() -> locationPresenter.loadData());
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.location_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        searchFab = view.findViewById(R.id.location_search_fab);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLocations);
    }

    private void initListeners() {
        searchFab.setOnClickListener(this);
    }

    private void initPresenter() {
        locationPresenter = new LocationPresenter(this, mContext);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.location_search_fab) {
            final EditText idEdit = new EditText(mContext);
            idEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle("Search location")
                    .setMessage("Enter an id of location")
                    .setView(idEdit)
                    .setPositiveButton(R.string.search_label, (dialogInterface, i) -> {
                        String id = idEdit.getText().toString();
                        locationPresenter.searchLocationById(id);
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
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setEnabledSearch(boolean enabled) {
        searchFab.setEnabled(enabled);
    }

    @Override
    public void showLocation(Location location) {
        LocationFragment locationFragment = new LocationFragment();
        FragmentManager fragmentManager =
                Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, locationFragment)
                .addToBackStack(null)
                .commit();
        locationFragment.setLocation(location);
    }
}
