package com.leshchyshyn.mobileapp.main_group.images;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.Image;
import com.leshchyshyn.mobileapp.main_group.image.ImageWithDetailsFragment;

import java.util.Objects;

public class ImagesFragment extends Fragment implements IImagesView, View.OnClickListener {

    private View view;
    private Context mContext;
    private RecyclerView recyclerView;
    private FloatingActionButton searchFab;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ImagesPresenter imagesPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);

        mContext = view.getContext();

        initView();
        initPresenter();
        initListener();

        imagesPresenter.loadData();

        swipeRefreshLayout.setOnRefreshListener(() -> imagesPresenter.loadData());

        return view;
    }

    private void initView() {
        searchFab = view.findViewById(R.id.photo_search_fab);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshImages);
        recyclerView = view.findViewById(R.id.photo_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        imagesPresenter = new ImagesPresenter(this, mContext);
    }

    private void initListener() {
        searchFab.setOnClickListener(this);
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
    public void onClick(View view) {
        if (view.getId() == R.id.photo_search_fab) {
            final EditText titleEdit = new EditText(mContext);
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle(R.string.search_image)
                    .setMessage(R.string.search_image_message)
                    .setView(titleEdit)
                    .setPositiveButton(R.string.search_label, (dialogInterface, i) -> {
                        String title = String.valueOf(titleEdit.getText());
                        imagesPresenter.searchImageByTitle(title);
                    })
                    .setNegativeButton(R.string.close, null)
                    .create();
            dialog.show();
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

    public void showImage(Image image) {
        ImageWithDetailsFragment imageWithDetailsFragment = new ImageWithDetailsFragment();
        FragmentManager fragmentManager =
                Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, imageWithDetailsFragment)
                .addToBackStack(null)
                .commit();
        imageWithDetailsFragment.setImage(image);
    }
}
