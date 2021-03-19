package com.example.protosight.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.ParticipantEnterSingleTask;
import com.example.protosight.ParticipantListTasksPage;
import com.example.protosight.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParticipantListTaskAdapter extends RecyclerView.Adapter<ParticipantListTaskAdapter.ViewHolder> {

    private ArrayList<Map<String, Object>> tasks;
    private Context context;
    private ArrayList<String> taskIds;
    private String TAG = "ParticipantListTaskAdapter";
    public static int[] status = {0,0,0};
    public static int tempPos;

    public ParticipantListTaskAdapter(ArrayList<Map<String, Object>> tasks, Context context, ArrayList<String> taskIds) {
        this.tasks = tasks;
        this.context = context;
        this.taskIds = taskIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_single_task_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskNum.setText("Task " + (position+1));
        if (status[position] == 1){
            holder.taskComplete.setVisibility(View.VISIBLE);
        }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "task click " + tasks.get(position).toString());
                    String projectCode = (String) ((HashMap) tasks.get(position)).get("projectCode");
                    String testID = (String) ((HashMap) tasks.get(position)).get("testID");
                    String taskScenario = (String) ((HashMap) tasks.get(position)).get("taskScenario");
                    String userTask = (String) ((HashMap) tasks.get(position)).get("userTask");
                    HashMap<String, Object> task = (HashMap<String, Object>) tasks.get(position);
                    Intent intent = new Intent(context, ParticipantEnterSingleTask.class);
                    intent.putExtra("task", task);
                    intent.putExtra("taskID", taskIds.get(position));
//                    intent.putExtra("pos", position);
                    tempPos = position;

                    context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView taskNum;
        private TextView taskComplete;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNum = itemView.findViewById(R.id.task_num);
            taskComplete = itemView.findViewById(R.id.task_complete);
            linearLayout = itemView.findViewById(R.id.participant_task);

        }
    }
}
