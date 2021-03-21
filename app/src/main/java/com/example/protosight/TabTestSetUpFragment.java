package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class TabTestSetUpFragment extends Fragment {

    private String testID;
    private String lastActivity;
    private String projectCode;
    private FirebaseFirestore db;
    private final String[] taskTitle = new String[]{"task 1", "task 2", "task 3"};
    private int taskTitleIndex;
    private LinearLayout linearLayout;
    private final int maxTaskNumber = 3;
    private boolean ifFistTimeEnter;


    public TabTestSetUpFragment(String testID, String lastActivity, String projectCode){
        this.testID = testID;
        this.lastActivity = lastActivity;
        this.projectCode = projectCode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            taskTitleIndex = 0;
            db = FirebaseFirestore.getInstance();
            ifFistTimeEnter = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_test_setup, container, false);
        linearLayout = view.findViewById(R.id.linearLayoutTask);


        Button generateTestCodeButton = view.findViewById(R.id.generate_testcode);
        TextView testCodeDisplay = view.findViewById(R.id.test_code);
        generateTestCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCodeDisplay.setText(testID);
                db.collection("tests").
                        document(testID).
                        update("whetherAllowChange", "1");
            }
        });


        //This is the case the user enters this page for the first time
        if (lastActivity.equals("NameTheTest")) {
            //The first button added
            taskTitleIndex = 1;
            View task_1 = getLayoutInflater().inflate(R.layout.task_item, null);
            Button button = task_1.findViewById(R.id.taskCreation_task_1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FillTheTask.class);
                    intent.putExtra("testID", testID);
                    intent.putExtra("lastActivity", lastActivity);
                    intent.putExtra("projectCode", projectCode);
                    startActivity(intent);
                }
            });

            //the Add task button added
            View add_task_buttom = getLayoutInflater().inflate(R.layout.add_task_item, null);
            Button addTaskButton = add_task_buttom.findViewById(R.id.taskCreation_add_task);
            addTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTaskForFirstTime();
                }
            });
            linearLayout.addView(task_1);
            linearLayout.addView(add_task_buttom);
        } else {
            //This is the case the user re-enters this page to change things
            ArrayList taskData = new ArrayList();
            db.collection("tasks").
                    whereEqualTo("testID", testID).
                    get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Map theMap = document.getData();
                                theMap.put("taskID", document.getId());
                                taskData.add(theMap);
                            }
                            projectCode = (String) ((Map) taskData.get(0)).get("projectCode");
                            for (int i=0;i<taskData.size(); i++){
                                addTaskComingBackForEditing((Map) taskData.get(i));
                            }
                            View add_task_buttom = getLayoutInflater().inflate(R.layout.add_task_item, null);
                            Button addTaskButton = add_task_buttom.findViewById(R.id.taskCreation_add_task);
                            addTaskButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addTaskForFirstTime();
                                }
                            });
                            linearLayout.addView(add_task_buttom);
                        }
                    });
        }
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (!ifFistTimeEnter) {
            linearLayout.removeAllViews();
            ArrayList taskData = new ArrayList();
            db.collection("tasks").
                    whereEqualTo("testID", testID).
                    get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map theMap = document.getData();
                                theMap.put("taskID", document.getId());
                                taskData.add(theMap);
                            }
                            for (int i = 0; i < taskData.size(); i++) {
                                addTaskComingBackForEditing((Map) taskData.get(i));
                            }
                            View add_task_buttom = getLayoutInflater().inflate(R.layout.add_task_item, null);
                            Button addTaskButton = add_task_buttom.findViewById(R.id.taskCreation_add_task);
                            addTaskButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addTaskForFirstTime();
                                }
                            });
                            linearLayout.addView(add_task_buttom);
                        }
                    });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ifFistTimeEnter = false;
        taskTitleIndex = 0;
    }


    private void addTaskForFirstTime() {
        if ((linearLayout.getChildCount() - 1) < maxTaskNumber) {
            View taskButtonView = getLayoutInflater().inflate(R.layout.task_item, null);
            Button taskButton = taskButtonView.findViewById(R.id.taskCreation_task_1);
            taskButton.setText(taskTitle[taskTitleIndex]);
            taskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FillTheTask.class);
                    intent.putExtra("testID", testID);
                    intent.putExtra("lastActivity", "NameTheTest");
                    intent.putExtra("projectCode", projectCode);
                    startActivity(intent);
                }
            });
            taskTitleIndex += 1;
            linearLayout.addView(taskButtonView, linearLayout.getChildCount() - 1);
        }
    }

    private void addTaskComingBackForEditing(Map<String, String> taskMap){
        if ((linearLayout.getChildCount() - 1) < maxTaskNumber) {
            View taskButtonView = LayoutInflater.from(getActivity()).inflate(R.layout.task_item, null);
            Button taskButton = taskButtonView.findViewById(R.id.taskCreation_task_1);
            taskButton.setText(taskTitle[taskTitleIndex]);
            taskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FillTheTask.class);
                    intent.putExtra("testID", testID);
                    intent.putExtra("lastActivity", lastActivity);
                    intent.putExtra("projectCode", taskMap.get("projectCode"));
                    intent.putExtra("taskID", taskMap.get("taskID"));
                    startActivity(intent);
                }
            });
            taskTitleIndex += 1;
            linearLayout.addView(taskButtonView, linearLayout.getChildCount() - 1);
        }
    }
}
