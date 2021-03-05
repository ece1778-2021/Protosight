package com.example.protosight.adapters;

import android.content.Context;
import android.content.Intent;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.protosight.R;
import com.example.protosight.SelectHotspot;


import java.util.ArrayList;

public class CreateProjectImageAdapter extends RecyclerView.Adapter<CreateProjectImageAdapter.ImageViewHolder> {

    private ArrayList<String> images;
    private Context context;
    private String TAG = "CreateProjectImageAdapter";
    private String projectName;
    private ArrayList<String> imageNames;

    public CreateProjectImageAdapter(ArrayList<String> images, Context context, String projectName) {
        this.images = images;
        this.context = context;
        this.projectName = projectName;
        this.imageNames = new ArrayList<>();
    }


    @NonNull
    @Override
    public CreateProjectImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cardview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateProjectImageAdapter.ImageViewHolder holder, int position) {
        holder.currentImage.setImageBitmap(BitmapFactory.decodeFile(images.get(position)));
        ImageView iv = holder.itemView.findViewById(R.id.single_upload_image);

        holder.currentImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation((float) 0.5, 0); //to change visibility from visible to invisible
                animation.setDuration(500); //1 second duration for each animation cycle
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(0); //repeating indefinitely

                holder.currentImage.startAnimation(animation); //to start animation

//                Project project = new Project(projectName, images);
//                project.setCurrentImage(images.get(position));
                Intent i = new Intent(context, SelectHotspot.class);
                Bundle b = new Bundle();
                ArrayList<String> linkImages = (ArrayList<String>) images.clone();
                linkImages.remove(images.get(position));

                b.putStringArrayList("images", linkImages);

                i.putExtras(b);



                i.putExtra("selectedImage", images.get(position));
                i.putExtra("projectName", projectName);



                context.startActivity(i);
            }
        });

        holder.currentImage.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //your stuff
                Log.d(TAG, "long...");

                return true;
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
