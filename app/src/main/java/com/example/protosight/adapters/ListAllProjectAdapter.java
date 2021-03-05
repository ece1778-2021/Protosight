package com.example.protosight.adapters;

import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.R;

import java.util.ArrayList;

public class ListAllProjectAdapter extends RecyclerView.Adapter<ListAllProjectAdapter.ProjectViewHolder>  {
    private ArrayList<String> projects;
    private Context context;
    private String TAG = "CreateProjectImageAdapter";


    public ListAllProjectAdapter(ArrayList<String> projects, Context context) {

        this.context = context;

        this.projects = projects;
    }

    @NonNull
    @Override
    public ListAllProjectAdapter.ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_project_view, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAllProjectAdapter.ProjectViewHolder holder, int position) {
        holder.currentProjectName.setText(this.projects.get(position));
        holder.currentProjectNameCardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation((float) 0.5, 0); //to change visibility from visible to invisible
                animation.setDuration(500); //1 second duration for each animation cycle
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(0); //repeating indefinitely
                holder.currentProjectNameCardView.setAnimation(animation);

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.projects.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder{

        protected CardView currentProjectNameCardView;
        protected TextView currentProjectName;


        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            currentProjectName = itemView.findViewById(R.id.single_project_name);
            currentProjectNameCardView = itemView.findViewById(R.id.single_project);

//            linearLayout = itemView.findViewById(R.id.add_project);

        }
    }
}
