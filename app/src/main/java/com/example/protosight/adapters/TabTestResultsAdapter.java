package com.example.protosight.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.R;
import com.example.protosight.TabTestResultsFragment;

import java.util.ArrayList;

public class TabTestResultsAdapter extends RecyclerView.Adapter<TabTestResultsAdapter.myViewHolder> {

    private TabTestResultsFragment resultsFragment;
    private ArrayList<String> testerNames;

    public TabTestResultsAdapter(TabTestResultsFragment resultFragment, ArrayList<String> testerNames){
        this.resultsFragment = resultFragment;
        this.testerNames = testerNames;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_test_results_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String testerName = testerNames.get(position);
        holder.textView.setText(testerName);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsFragment.callBack(testerName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testerNames.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public myViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.test_results_testerName_item);
        }
    }
}
