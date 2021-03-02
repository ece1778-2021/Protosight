package com.example.protosight.adapters;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.CreateProject;
import com.example.protosight.HotspotsLinkScreen;
import com.example.protosight.R;
import com.example.protosight.SelectHotspot;
import com.example.protosight.models.HotSpot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HotspotLinkScreenAdapter extends RecyclerView.Adapter<HotspotLinkScreenAdapter.ImageViewHolder>  {

    private ArrayList<String> images;
    private ArrayList<String> imageNames;
    private Context context;
    private String TAG = "HotspotLinkScreenAdapter";

    private HotSpot hotSpot;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    public HotspotLinkScreenAdapter(ArrayList<String> images,
                                    Context context,
                                    HotSpot hotSpot) {
        this.images = images;
        this.context = context;

        this.hotSpot = hotSpot;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cardview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        holder.currentImage.setImageBitmap(BitmapFactory.decodeFile(images.get(position)));


        holder.currentImage.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Link ");
                Animation animation = new AlphaAnimation((float) 0.5, 0); //to change visibility from visible to invisible
                animation.setDuration(500); //1 second duration for each animation cycle
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(0); //repeating indefinitely

                holder.currentImage.startAnimation(animation); //to start animation
                hotSpot.setLinkImage(images.get(position));
                if (SelectHotspot.getHotSpots().isEmpty()) hotSpot.setFirst(true);
                DocumentReference docRef = db.collection("creators").document(mAuth.getCurrentUser().getUid());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot dc = task.getResult();
                            if (dc.exists()) {

                                hotSpot.setCreator(dc.get("username").toString());
                                hotSpot.setProjectID(CreateProject.generateUUID());
                                Log.d(TAG, hotSpot.toMap().toString());
                                SelectHotspot.addHotspot(hotSpot);

                                Intent intent = new Intent(context, SelectHotspot.class);
                                Bundle b = new Bundle();
                                b.putStringArrayList("images", images);

                                intent.putExtras(b);
                                intent.putExtra("selectedImage", hotSpot.getRelatedImage());
                                intent.putExtra("projectName", CreateProject.getProjectName());
                                context.startActivity(intent);

                            }
                        }
                    }

                });
            }

        });

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

//            linearLayout = itemView.findViewById(R.id.add_project);

        }
    }

}
