package com.example.protosight;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;


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
    private ClipData clipData;


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
        TextView testCodeView = view.findViewById(R.id.test_code);
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        Button copyTestCodeButton = view.findViewById(R.id.copy_testCode_button);
        copyTestCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testCode = testCodeView.getText().toString();
                if (!testCode.equals("")) {
                    ClipData clipData = ClipData.newPlainText("text", testCode);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getContext(), "Share code has been successfully copied.", Toast.LENGTH_SHORT).show();
                }
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
                    intent.putExtra("taskCode", 1);
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
                    orderBy("taskCode", Query.Direction.ASCENDING).
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
                    orderBy("taskCode", Query.Direction.ASCENDING).
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
                            //The following code is for the Adding task button
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
                    intent.putExtra("projectCode", projectCode);
                    intent.putExtra("taskCode", taskTitleIndex);
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
                    intent.putExtra("projectCode", projectCode);
                    intent.putExtra("taskCode", taskTitleIndex);
                    intent.putExtra("taskID", taskMap.get("taskID"));
                    startActivity(intent);
                }
            });
            taskTitleIndex += 1;
            linearLayout.addView(taskButtonView, linearLayout.getChildCount());
        }
    }
}
