package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.protosight.adapters.ListAllTestAdapter;
import com.example.protosight.adapters.ParticipantListTaskAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ParticipantListTasksPage extends AppCompatActivity {


    private FirebaseFirestore db;
    private String TAG = "ParticipantListTasksPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list_tasks_page);

        TextView textView = findViewById(R.id.testName);
        textView.setText("Hello " + ParticipantLandingPage.participantName);
        String testCode = getIntent().getStringExtra("testCode");

        db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .whereEqualTo("testID", testCode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Map<String, Object>> tasks = new ArrayList<>();
                            ArrayList<String> taskIds = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tasks.add(document.getData());
                                taskIds.add(document.getId());

                            }
                            RecyclerView recyclerView = findViewById(R.id.participant_list_task);

                            recyclerView.setLayoutManager(new LinearLayoutManager(ParticipantListTasksPage.this));
                            ParticipantListTaskAdapter ad = new ParticipantListTaskAdapter(tasks, ParticipantListTasksPage.this, taskIds);
                            recyclerView.setAdapter(ad);

                        } else {
                            Log.d(TAG, "errrrrorrrr");
                        }
                    }
                });

    }
}