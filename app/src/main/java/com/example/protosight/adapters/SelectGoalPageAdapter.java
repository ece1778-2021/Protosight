package com.example.protosight.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.R;
import com.example.protosight.SelectGoalPage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StreamDownloadTask;

import java.util.ArrayList;

public class SelectGoalPageAdapter extends RecyclerView.Adapter<SelectGoalPageAdapter.ImageViewHolder>{
    private ArrayList<String> images;
    private SelectGoalPage lastPage;
    private String TAG = "CreateProjectImageAdapter";
    private String projectName;
    private ArrayList<String> imageNames;
    private FirebaseStorage storage;

    public SelectGoalPageAdapter(ArrayList<String> images, SelectGoalPage lastPage, String projectName) {
        this.images = images;
        this.lastPage = lastPage;
        this.projectName = projectName;
        this.imageNames = new ArrayList<>();
        this.storage = FirebaseStorage.getInstance();
    }


    @NonNull
    @Override
    public SelectGoalPageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cardview, parent, false);
        return new SelectGoalPageAdapter.ImageViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SelectGoalPageAdapter.ImageViewHolder holder, int position) {
        holder.currentImage.setImageBitmap(BitmapFactory.decodeFile(images.get(position)));
        ImageView iv = holder.itemView.findViewById(R.id.single_upload_image);
        String imageURL = images.get(position);
        storage.getReferenceFromUrl(imageURL).
                getBytes(1024*1024).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.currentImage.setImageBitmap(bitmap);
                    }
                });

        holder.currentImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                lastPage.callback((String) images.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        protected ImageView currentImage;
        protected CardView currentCardView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            currentImage = itemView.findViewById(R.id.single_upload_image);

            currentCardView = itemView.findViewById(R.id.single_cardview);
        }
    }
}
