package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ParticipantInstructionPage extends AppCompatActivity {
    private String testCode;
    private String TAG = "ParticipantInstructionPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_instruction_page);
        testCode = getIntent().getStringExtra("testCode");
    }


    public void startTestOnClick(View view){
        Log.d(TAG, "starting");
    }

}