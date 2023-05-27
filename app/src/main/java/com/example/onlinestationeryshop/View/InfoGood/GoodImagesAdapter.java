package com.example.onlinestationeryshop.View.InfoGood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.databinding.ImageFullBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class GoodImagesAdapter extends RecyclerView.Adapter<GoodImagesAdapter.ViewHolder> {

    Context ctx;


    LayoutInflater lInflater;
    ArrayList<String> objects;

    // Array of images
    // Adding images from drawable folder

    // Constructor of our GoodImagesAdapter class
    GoodImagesAdapter(Context ctx, ArrayList<String> goods) {
        this.ctx = ctx;
        objects = goods;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageFullBinding mBinding = ImageFullBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(mBinding.getRoot());
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        Picasso.with(ctx).load(objects.get(position)).into(holder.binding.ima);
    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return objects.size();
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageFullBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ImageFullBinding.bind(itemView);
        }
    }
}

