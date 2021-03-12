package com.example.protosight.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.CreateTask;
import com.example.protosight.R;
import com.example.protosight.tabTaskFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAllTestAdapter extends RecyclerView.Adapter<ListAllTestAdapter.ViewHolder> {

    private final ArrayList data;
    private tabTaskFragment tabTaskFragment;

    public ListAllTestAdapter(tabTaskFragment tabTaskFragment, ArrayList<Map> data) {
        this.data = data;
        this.tabTaskFragment = tabTaskFragment;
    }

    @NonNull
    @Override
    public ListAllTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
        return new ListAllTestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String testName = (String) ((HashMap)data.get(position)).get("testName");
        String testID = (String) ((HashMap) data.get(position)).get("testID");
        String lastActivity = "comingBack";
        holder.testButton.setText(testName);
        holder.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = tabTaskFragment.getContext();
                Intent intent = new Intent(context , CreateTask.class);
                intent.putExtra("testName",testName);
                intent.putExtra("testID", testID);
                intent.putExtra("lastActivity", lastActivity);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private Button testButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            testButton=itemView.findViewById(R.id.test_buttom);
        }
    }
}
