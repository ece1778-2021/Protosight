package com.example.protosight.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.R;

import java.util.ArrayList;

public class CreateProjectImageAdapter extends RecyclerView.Adapter<CreateProjectImageAdapter.ImageViewHolder> {

    private ArrayList<Bitmap> images;
    private Context context;

    public CreateProjectImageAdapter(ArrayList<Bitmap> images, Context context) {
        this.images = images;
        this.context = context;
    }


    @NonNull
    @Override
    public CreateProjectImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cardview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateProjectImageAdapter.ImageViewHolder holder, int position) {
        holder.currentImage.setImageBitmap(images.get(position));
        String text = "Image " + position;
        holder.label.setText(text);
    }

    @Override
    public int getItemCount() {
        return this.images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        protected ImageView currentImage;
        protected TextView label;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            currentImage = itemView.findViewById(R.id.single_upload_image);
            label = itemView.findViewById(R.id.single_upload_image_label);

        }
    }
}
