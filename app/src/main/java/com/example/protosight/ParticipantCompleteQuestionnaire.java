package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.protosight.adapters.ParticipantListTaskAdapter;

import com.example.protosight.models.TaskResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ParticipantCompleteQuestionnaire extends AppCompatActivity {

    private String TAG = "ParticipantCompleteQuestionnaire";
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    TaskResult taskResult;
    private FirebaseFirestore db;
    private TextView t1,q1,t2,q2,t3,q3,t4,q4,t5,q5;
    private EditText e1,e2,e3,e4,e5;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_complete_questionnaire);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = findViewById(R.id.questions);
        Intent intent = getIntent();
        taskResult = intent.getParcelableExtra("taskResult");

        initTextView();



        Log.d(TAG, taskResult.toMap().toString());
        db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .document(taskResult.getTaskID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        taskResult.setFirstQuestion((String) document.getData().get("firstQuestion"));
                        taskResult.setSecondQuestion((String) document.getData().get("secondQuestion"));
                        taskResult.setThirdQuestion((String) document.getData().get("thirdQuestion"));
                        taskResult.setFourthQuestion((String) document.getData().get("fourthQuestion"));
                        taskResult.setFifthQuestion((String) document.getData().get("fifthQuestion"));

                        updateUI();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_proto_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.stop_proto){
            Intent intent = new Intent(ParticipantCompleteQuestionnaire.this, Login.class);
            startActivity(intent);
        }
        return true;
    }

    public void submitQuestionnaireOnClick(View view){
        Log.d(TAG, "submit");
        Log.d(TAG, taskResult.toMap().toString());



        if (taskResult.getFirstQuestion() != null){
            taskResult.setFirstAnswer(e1.getText().toString());
        }

        if (taskResult.getSecondQuestion() != null){
            taskResult.setSecondAnswer(e2.getText().toString());
        }
        if (taskResult.getThirdQuestion() != null){
            taskResult.setThirdAnswer(e3.getText().toString());
        }
        if (taskResult.getFourthQuestion() != null){
            taskResult.setFourthAnswer(e4.getText().toString());
        }
        if (taskResult.getFifthQuestion() != null){
            taskResult.setFifthAnswer(e5.getText().toString());
        }
        progressDialog = new ProgressDialog(ParticipantCompleteQuestionnaire.this);
        progressDialog.setTitle("Saving the result...");
        progressDialog.show();

        db.collection("taskResult")
                .add(taskResult.toMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        progressDialog.dismiss();


                        ParticipantListTaskAdapter.status[ParticipantListTaskAdapter.tempPos] = 1;
                        if (ParticipantListTaskAdapter.status[0] == 1 &&
                                ParticipantListTaskAdapter.status[1] == 1 &&
                                ParticipantListTaskAdapter.status[2] == 1){
                            Log.d(TAG, "finished!!!");
                            Intent intent = new Intent(ParticipantCompleteQuestionnaire.this, ParticipantCompleteTest.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(ParticipantCompleteQuestionnaire.this, ParticipantListTasksPage.class);
                            intent.putExtra("testCode", taskResult.getTestCode());
                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });



    }


    private void initTextView(){
        t1 = findViewById(R.id.q1_title);
        t2 = findViewById(R.id.q2_title);
        t3 = findViewById(R.id.q3_title);
        t4 = findViewById(R.id.q4_title);
        t5 = findViewById(R.id.q5_title);

        q1 = findViewById(R.id.q1_text);
        q2 = findViewById(R.id.q2_text);
        q3 = findViewById(R.id.q3_text);
        q4 = findViewById(R.id.q4_text);
        q5 = findViewById(R.id.q5_text);

        e1 = findViewById(R.id.q1_ans);
        e2 = findViewById(R.id.q2_ans);
        e3 = findViewById(R.id.q3_ans);
        e4 = findViewById(R.id.q4_ans);
        e5 = findViewById(R.id.q5_ans);

    }

    private void updateUI(){
        if (taskResult.getFirstQuestion() != null){
            q1.setVisibility(View.VISIBLE);
            t1.setVisibility(View.VISIBLE);
            e1.setVisibility(View.VISIBLE);
            q1.setText(taskResult.getFirstQuestion());
        }

        if (taskResult.getSecondQuestion() != null){
            q2.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            e2.setVisibility(View.VISIBLE);
            q2.setText(taskResult.getSecondQuestion());
        }


        if (taskResult.getThirdQuestion() != null){
            q3.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            e3.setVisibility(View.VISIBLE);
            q3.setText(taskResult.getThirdQuestion());
        }


        if (taskResult.getFourthQuestion() != null){
            q4.setVisibility(View.VISIBLE);
            t4.setVisibility(View.VISIBLE);
            e4.setVisibility(View.VISIBLE);
            q4.setText(taskResult.getFourthQuestion());
        }


        if (taskResult.getFifthQuestion() != null){
            q5.setVisibility(View.VISIBLE);
            t5.setVisibility(View.VISIBLE);
            e5.setVisibility(View.VISIBLE);
            q5.setText(taskResult.getFifthQuestion());
        }


    }

}