package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ParticipantListTasksPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list_tasks_page);

        TextView textView = findViewById(R.id.testName);
        textView.setText(getIntent().getStringExtra("testName"));

    }
}