package com.example.protosight.adapters;

import android.content.Context;
import android.content.Intent;
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

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.protosight.EditPrototype;
import com.example.protosight.PlayPrototype;
import com.example.protosight.R;

import java.util.ArrayList;

public class ListAllProjectAdapter extends RecyclerView.Adapter<ListAllProjectAdapter.ProjectViewHolder>  {
    public ArrayList<String> projects;
    private ArrayList<String> projectIDs;
    private ArrayList<String> firstImages;
    private Context context;
    private String TAG = "CreateProjectImageAdapter";


    public ListAllProjectAdapter(ArrayList<String> projects, Context context, ArrayList<String> projectIDs, ArrayList<String> firstImages) {

        this.context = context;
        this.projectIDs = projectIDs;
        this.projects = projects;
        this.firstImages = firstImages;
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

        holder.playPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Play project : " + projects.get(position));
                Intent intent = new Intent(context, PlayPrototype.class);
                intent.putExtra("projectID", projectIDs.get(position));
                intent.putExtra("firstImageRef", firstImages.get(position));
                context.startActivity(intent);
            }
        });

        holder.editPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Play project : " + projects.get(position));
                // TODO: perform deletion
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.projects.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder{

        protected SwipeRevealLayout currentProjectNameCardView;
        protected TextView currentProjectName;
        protected TextView playPrototype;
        protected TextView editPrototype;
        private String TAG = "ProjectViewHolder";


        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            currentProjectName = itemView.findViewById(R.id.single_project_name);
            currentProjectNameCardView = itemView.findViewById(R.id.single_project);
            playPrototype = itemView.findViewById(R.id.play_prototype);
            editPrototype = itemView.findViewById(R.id.delete_prototype);


        }
    }

}
