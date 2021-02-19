package com.example.protosight.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.R;

import java.util.ArrayList;

public class HotspotLinkScreenAdapter extends RecyclerView.Adapter<HotspotLinkScreenAdapter.ImageViewHolder>  {

    private ArrayList<String> images;
    private Context context;
    private String TAG = "HotspotLinkScreenAdapter";
    private String projectName;

    public HotspotLinkScreenAdapter(ArrayList<String> images, Context context, String projectName) {
        this.images = images;
        this.context = context;
        this.projectName = projectName;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cardview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Log.d(TAG, images.get(position));
        holder.currentImage.setImageBitmap(BitmapFactory.decodeFile(images.get(position)));
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
        protected LinearLayout linearLayout;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            currentImage = itemView.findViewById(R.id.single_upload_image);
            label = itemView.findViewById(R.id.single_upload_image_label);
//            linearLayout = itemView.findViewById(R.id.add_project);

        }
    }

}
