package com.leshchyshyn.mobileapp.main_group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.Image;
import com.leshchyshyn.mobileapp.main_group.images.ImagesFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Image> imageList;

    private ImagesFragment imageWithDetailsFragment;

    public ImageAdapter(List<Image> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        final Context mContext = holder.itemView.getContext();
        final Image image = imageList.get(position);

        Picasso.get()
                .load(image.getUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view -> {
            imageWithDetailsFragment = new ImagesFragment();

            FragmentManager fragmentManager =
                    ((AppCompatActivity) mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main_container, imageWithDetailsFragment)
                    .addToBackStack(null)
                    .commit();
            fragmentManager.executePendingTransactions();
            imageWithDetailsFragment.showImage(image);
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void updateImages(final List<Image> images) {
        this.imageList = images;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_iv);
        }
    }
}
