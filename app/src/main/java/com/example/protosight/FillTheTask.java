package com.example.protosight;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FillTheTask extends AppCompatActivity {

    private EditText taskScenarioContent;
    private EditText userTaskContent;
    private EditText firstQuestionContent  ;
    private TextView taskScenarioContentWordsCount;
    private TextView userTaskContentWordsCount;
    private TextView firstQuestionWordsCount;
    private ArrayList<Object> allContents;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_task);
        taskScenarioContent = findViewById(R.id.task_scenario_content);
        taskScenarioContentWordsCount = findViewById(R.id.task_scenario_words_count);
        TextWatcher taskScenarioTextEditorWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                taskScenarioContentWordsCount.setText(String.valueOf(s.length())+"/300");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        taskScenarioContent.addTextChangedListener(taskScenarioTextEditorWatcher);

        userTaskContent = findViewById(R.id.user_task_content);
        userTaskContentWordsCount = findViewById(R.id.user_task_words_count);
        TextWatcher userTaskTextEditorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userTaskContentWordsCount.setText(String.valueOf(s.length()+"/200"));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        userTaskContent.addTextChangedListener(userTaskTextEditorWatcher);


    }
}
