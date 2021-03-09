package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;



public class TabTestSetUpFragment extends Fragment {

    private String testName;
    private String lastActivity;
    private LinearLayout linearLayout;

    public TabTestSetUpFragment(String testName, String lastActivity){
        this.testName = testName;
        this.lastActivity = lastActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_test_setup, container, false);
        linearLayout = view.findViewById(R.id.linearLayoutTask);
        //This is the case the user enters this page for the first time
        if (lastActivity.equals("NameTheTest")) {
            //The first button added
            View task_1 = getLayoutInflater().inflate(R.layout.task_item, null);
            Button button = task_1.findViewById(R.id.taskCreation_task_1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FillTheTask.class);
                    startActivity(intent);
                }
            });

            //the Add task button added
            View add_task_buttom = getLayoutInflater().inflate(R.layout.add_task_item, null);
            Button addTaskButton = add_task_buttom.findViewById(R.id.taskCreation_add_task);
            addTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            linearLayout.addView(task_1);
            linearLayout.addView(add_task_buttom);
        } else {
            //This is the case the user re-enters this page to change things
        }
        return view;
    }



}
