package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.protosight.models.TaskResult;

public class ParticipantCompleteQuestionnaire extends AppCompatActivity {

    private String TAG = "ParticipantCompleteQuestionnaire";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_complete_questionnaire);

        Intent intent = getIntent();
        TaskResult taskResult = intent.getParcelableExtra("taskResult");
        Log.d(TAG, taskResult.toMap().toString());

    }
}