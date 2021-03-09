package com.example.protosight;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;

public class FillTheTask extends AppCompatActivity {

    private LinearLayout myLinearLayout;
    private EditText taskScenarioContent;
    private EditText userTaskContent;
    private EditText firstQuestionContent  ;
    private TextView taskScenarioContentWordsCount;
    private TextView userTaskContentWordsCount;
    private TextView firstQuestionWordsCount;
    private ArrayList<Object> allContents;
    private int howManyQuestions;
    private ArrayList<String> questionTitleList= new ArrayList<String>();



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_task);

        howManyQuestions = 0;
        questionTitleList.add("Second Question");
        questionTitleList.add("Third Question");

        taskScenarioContent = findViewById(R.id.task_scenario_content);
        taskScenarioContentWordsCount = findViewById(R.id.task_scenario_words_count);
        myLinearLayout = findViewById(R.id.task_creation_linearLayout);
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

        firstQuestionContent = findViewById(R.id.user_task_content);
        firstQuestionWordsCount = findViewById(R.id.first_question_words_count);
        TextWatcher firstQuestionTextEditorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstQuestionWordsCount.setText(String.valueOf(s.length())+"/150");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        firstQuestionContent.addTextChangedListener(firstQuestionTextEditorWatcher);

        Button button = findViewById(R.id.add_question_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (howManyQuestions<2) {
                    addQuestionnaireQuestion();
                }
            }
        });
    }

    private void addQuestionnaireQuestion(){
        View questionView = getLayoutInflater().inflate(R.layout.task_questionnaire_question_item, null);
        EditText questionContent = questionView.findViewById(R.id.questionnaire_question_item_content);
        TextView questionWordsCount = questionView.findViewById(R.id.questionnaire_question_item_wordscount);
        TextView questionTitle = questionView.findViewById(R.id.questionnaire_question_item_title);
        questionTitle.setText(questionTitleList.get(howManyQuestions));
        howManyQuestions += 1;
        TextWatcher questionTextEditorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                questionWordsCount.setText(String.valueOf(s.length()+"/150"));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        questionContent.addTextChangedListener(questionTextEditorWatcher);
        int position = myLinearLayout.getChildCount()-1;
        myLinearLayout.addView(questionView, position);
    }

}
