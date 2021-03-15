package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protosight.adapters.ListAllProjectAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ParticipantLandingPage extends AppCompatActivity {
    private FirebaseFirestore db;
    private String TAG = "ParticipantLandingPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_landing_page);
        db = FirebaseFirestore.getInstance();
    }

    public void testCodeOnSubmit(View view){
        EditText editText = findViewById(R.id.test_code);
        String code = editText.getText().toString();
        db.collection("tests").document(code).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        TextView textView = findViewById(R.id.invalid_code);
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                textView.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(ParticipantLandingPage.this, ParticipantInstructionPage.class);
                                intent.putExtra("testCode", code);
                                intent.putExtra("testName", (String) document.getData().get("testName"));
                                startActivity(intent);


                            } else {
                                Log.d(TAG, "DocumentSnapshot data: missing");
                                textView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }
}