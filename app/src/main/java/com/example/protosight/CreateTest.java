package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protosight.adapters.CreateTestBindingPrototypeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class CreateTest extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView prototypesRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        prototypesRecyclerView = findViewById(R.id.Prototype_RecyclerView);
        ArrayList<String[]> prototypeNames = new ArrayList<String[]>();
        String creatorEmail = mAuth.getCurrentUser().getEmail();
        prototypeNames.add(new String[]{"DEMO2","second prototype"});
        //get prototype names from database
        db.collection("prototypes").
                whereEqualTo("creatorEmail", creatorEmail).
                get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document: task.getResult()){
                    String[] data = new String[2];
                    data[0] = (String) document.getData().get("name");
                    data[1] = (String) document.getData().get("projectCode");
                    prototypeNames.add(data);
                }
                prototypesRecyclerView.setLayoutManager(new GridLayoutManager(CreateTest.this, 1));
                CreateTestBindingPrototypeAdapter adapter = new CreateTestBindingPrototypeAdapter(CreateTest.this, prototypeNames);
                prototypesRecyclerView.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateTest.this, "Failure to get prototype data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callback(String prototypeName, String projectCode){
        Intent intent = new Intent(this, NameTheTest.class);
        intent.putExtra("prototypeName", prototypeName);
        intent.putExtra("projectCode", projectCode);
        startActivity(intent);
    }
}
