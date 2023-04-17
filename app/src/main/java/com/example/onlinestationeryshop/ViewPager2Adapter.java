package com.example.onlinestationeryshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestationeryshop.databinding.ImageFullBinding;
import com.example.onlinestationeryshop.databinding.ImageItemBinding;

import java.util.ArrayList;

class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    Context ctx;


    LayoutInflater lInflater;
    ArrayList<Integer> objects;

    // Array of images
    // Adding images from drawable folder

    // Constructor of our ViewPager2Adapter class
    ViewPager2Adapter(Context ctx, ArrayList<Integer> goods) {
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
        holder.binding.ima.setImageResource(objects.get(position));
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

