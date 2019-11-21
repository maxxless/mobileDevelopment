package com.leshchyshyn.mobileapp.main_group.image;

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
import com.leshchyshyn.mobileapp.data.model.Image;
import com.squareup.picasso.Picasso;

public class ImageWithDetailsFragment extends Fragment {

    private View view;
    private Image image;

    private ImageView imageView;
    private TextView fullImgIdTv;
    private TextView fullImgTitleTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image, container, false);

        initView();
        setInfoToImage(image);
        return view;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private void initView() {
        RecyclerView recyclerView = view.findViewById(R.id.image_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        fullImgIdTv = view.findViewById(R.id.full_img_title_tv);
        fullImgTitleTv = view.findViewById(R.id.full_img_id_tv);
        imageView = view.findViewById(R.id.full_image_iv);
    }

    private void setInfoToImage(Image image) {
        fullImgIdTv.setText(String.valueOf(image.getId()));
        fullImgTitleTv.setText(image.getTitle());

        Picasso.get()
                .load(image.getUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }
}

