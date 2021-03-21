package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.adapters.CreateProjectImageAdapter;
import com.example.protosight.adapters.SelectGoalPageAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SelectGoalPage extends AppCompatActivity {

    private String prototypeName;
    private RecyclerView recyclerView;
    private FirebaseAuth myAuth;
    private ArrayList<String> images = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goal_page);

        prototypeName = getIntent().getStringExtra("prototypeName");

        myAuth = FirebaseAuth.getInstance();

        String userID = myAuth.getCurrentUser().getUid();
        Log.d("uid", userID);
        FirebaseStorage.getInstance().
                getReference().
                child("prototypes").
                child(userID).
                child(prototypeName).
                listAll().
                addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference rf: listResult.getItems()){
                            images.add(rf.toString());
                        }
                        recyclerView = findViewById(R.id.goalPage_recyclerview);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SelectGoalPage.this, 2);
                        recyclerView.setLayoutManager(layoutManager);
                        SelectGoalPageAdapter adapter = new SelectGoalPageAdapter(images, SelectGoalPage.this, prototypeName);
                        recyclerView.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("URL", "fail");
            }
        });
    }

    public void callback(String URL){
        Intent intent = new Intent();
        intent.putExtra("URL", URL);
        setResult(1, intent);
        finish();
    }
}
