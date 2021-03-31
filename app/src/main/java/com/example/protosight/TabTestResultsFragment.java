package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.adapters.TabTestResultsAdapter;
import com.example.protosight.models.TheTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TabTestResultsFragment extends Fragment {

    private String testID;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private Map<String, ArrayList<TheTask>> participants = new HashMap<>();
    private final String[] questions = new String[]{
            "firstQuestion",
            "secondQuestion",
            "thirdQuestion",
            "fourthQuestion",
            "fifthQuestion"};
    private final String[] answersTitle = new String[]{
            "firstAnswer",
            "secondAnswer",
            "thirdAnswer",
            "fourthAnswer",
            "fifthAnswer"};



    public TabTestResultsFragment(String testID){
        this.testID = testID;
        this.db = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_test_results, container, false);
        recyclerView = view.findViewById(R.id.testResults_recyclerView);
        db.collection("tasks").
                whereEqualTo("testID", testID).
                orderBy("taskCode", Query.Direction.ASCENDING).
                get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task){
                        for (QueryDocumentSnapshot document: task.getResult()){
                            ArrayList<String> questionList = new ArrayList();
                            Map data = document.getData();
                            int i = 0;
                            while (data.containsKey(questions[i])){
                                questionList.add((String) data.get(questions[i]));
                                i += 1;
                            }
                            getTestResultIndividually(document.getId(), questionList);
                        }
                    }
                });

        return view;
    }

    private void getTestResultIndividually(String taskID, ArrayList<String> questionList){
        Log.d("taskID", taskID);
        db.collection("taskResult").
                whereEqualTo("taskID", taskID).
                get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document: task.getResult()){
                            Map data = document.getData();
                            String tester = (String) data.get("tester");
                            String videoPath = (String) data.get("videoPath");
                            ArrayList<String> answers = new ArrayList<String>();
                            for (int i=0; i<questionList.size();i++){
                                answers.add((String) data.get(answersTitle[i]));
                            }
                            //Construct the TheTask instance to be put into its participant's map
                            TheTask taskObject = new TheTask(tester, videoPath, questionList, answers);
                            if (participants.containsKey(tester)){
                                participants.get(tester).add(taskObject);
                            } else{
                                ArrayList<TheTask> mapList = new ArrayList<TheTask>();
                                mapList.add(taskObject);
                                participants.put(tester, mapList);
                            }
                        }
                        //Create an ArrayList of keys of map
                        ArrayList<String> testerNames = new ArrayList<String>(participants.keySet());
                        TabTestResultsAdapter adapter = new TabTestResultsAdapter(TabTestResultsFragment.this, testerNames);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    public void callBack(String testerName){
        String[] taskName = new String[]{"taskOne", "taskTwo", "taskThree"};
        Intent intent = new Intent(getContext(), PersonalTaskView.class);
        for (int i = 0; i<participants.get(testerName).size(); i++){
            intent.putExtra(taskName[i], participants.get(testerName).get(i));
        }
        startActivity(intent);
    }
}
