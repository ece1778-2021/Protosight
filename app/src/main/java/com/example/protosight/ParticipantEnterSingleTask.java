package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class ParticipantEnterSingleTask extends AppCompatActivity {
    HashMap<String, String> hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_enter_single_task);

        Intent intent = getIntent();
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("task");
        String instruction = hashMap.get("taskScenario") + "\n\n" + hashMap.get("userTask");
        TextView i = findViewById(R.id.instruction);
        i.setText(instruction);
    }

    public void startTaskOnClick(View view){
        Intent intent = new Intent(this, ParticipantStartTask.class);
        intent.putExtra("task", hashMap);
        startActivity(intent);


    }

}