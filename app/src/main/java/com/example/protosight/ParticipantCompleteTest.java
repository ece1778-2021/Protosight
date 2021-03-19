package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ParticipantCompleteTest extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_complete_test);
        mAuth = FirebaseAuth.getInstance();
    }


    public void closeAppOnClick(View view){
        mAuth.signOut();
        Intent intent = new Intent(ParticipantCompleteTest.this, Login.class);
        startActivity(intent);

    }
}