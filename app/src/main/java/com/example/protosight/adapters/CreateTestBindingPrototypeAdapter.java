package com.example.protosight.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.CreateTest;
import com.example.protosight.R;

import java.util.ArrayList;

public class CreateTestBindingPrototypeAdapter extends RecyclerView.Adapter<CreateTestBindingPrototypeAdapter.ViewHolder> {

    private CreateTest createTest;
    private ArrayList<String[]> data;
    private final LayoutInflater mInflater;

    public CreateTestBindingPrototypeAdapter(CreateTest createTest, ArrayList<String[]> data){
        this.data = data;
        this.mInflater = LayoutInflater.from(createTest);
        this.createTest = createTest;
    }


    @NonNull
    @Override
    public CreateTestBindingPrototypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.prototype_name_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateTestBindingPrototypeAdapter.ViewHolder holder, int position) {
        String prototypeName = data.get(position)[0];
        String projectCode = data.get(position)[1];
        holder.prototypeButton.setText(prototypeName);
        holder.prototypeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                createTest.callback(prototypeName, projectCode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //customize the ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button prototypeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prototypeButton = itemView.findViewById(R.id.task_1);
        }
    }
}
