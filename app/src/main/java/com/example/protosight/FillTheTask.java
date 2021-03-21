package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FillTheTask extends AppCompatActivity {

    private String lastActivity;
    private String testID;
    private String projectCode;
    private String goalPageURL;

    private FirebaseFirestore db;
    private LinearLayout myLinearLayout;
    private EditText task_scenario;
    private EditText user_task;
    private EditText first_question;
    private TextView taskScenarioContentWordsCount;
    private TextView userTaskContentWordsCount;
    private TextView firstQuestionWordsCount;
    private int howManyQuestions;
    private final String[] questionTitleList = new String[]{
            "Second Question",
            "Third Question",
            "Fourth Question",
            "Fifth Question"};
    private final String[] allEditView = new String[]{
            "taskScenario",
            "userTask",
            "firstQuestion",
            "secondQuestion",
            "thirdQuestion",
            "fourthQuestion",
            "fifthQuestion"};
    private HashMap<String, String> tasks = new HashMap<String, String>();
    private String myId;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_task);

        lastActivity = getIntent().getStringExtra("lastActivity");
        testID = getIntent().getStringExtra("testID");
        projectCode = getIntent().getStringExtra("projectCode");

        db = FirebaseFirestore.getInstance();

        myLinearLayout = findViewById(R.id.task_creation_linearLayout);

        myId = db.collection("tasks").document().getId();

        howManyQuestions = 0;

        task_scenario = findViewById(R.id.task_scenario_content);
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
        task_scenario.addTextChangedListener(taskScenarioTextEditorWatcher);

        user_task = findViewById(R.id.user_task_content);
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
        user_task.addTextChangedListener(userTaskTextEditorWatcher);

        first_question = findViewById(R.id.user_task_content);
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
        first_question.addTextChangedListener(firstQuestionTextEditorWatcher);


        Button button = findViewById(R.id.add_question_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (howManyQuestions<4) {
                    addQuestionnaireQuestion("");
                }
            }
        });
        if (!lastActivity.equals("NameTheTest")){
            String taskID = getIntent().getStringExtra("taskID");
            db.collection("tasks").
                    document(taskID).
                    get().
                    addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map taskMap = documentSnapshot.getData();
                            int mapIndex = 0;
                            int linearLayoutIndex = 0;
                            while (mapIndex<taskMap.size()-3){
                                if (linearLayoutIndex>=myLinearLayout.getChildCount()-2){
                                    addQuestionnaireQuestion((String) taskMap.get(allEditView[mapIndex]));
                                    mapIndex += 1;
                                }
                                else if (myLinearLayout.getChildAt(linearLayoutIndex) instanceof EditText){
                                    ((EditText) myLinearLayout.getChildAt(linearLayoutIndex)).setText((String) taskMap.get(allEditView[mapIndex]));
                                    mapIndex += 1;
                                }
                                linearLayoutIndex += 1;
                            }
                        }
                    });
        }
    }


    private void addQuestionnaireQuestion(String content){
        View questionView = getLayoutInflater().inflate(R.layout.task_questionnaire_question_item, null);
        EditText questionContent = questionView.findViewById(R.id.questionnaire_question_item_content);
        if (!content.equals("")){
            questionContent.setText(content);
        }
        TextView questionWordsCount = questionView.findViewById(R.id.questionnaire_question_item_wordscount);
        TextView questionTitle = questionView.findViewById(R.id.questionnaire_question_item_title);
        questionTitle.setText(questionTitleList[howManyQuestions]);
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


    public void saveTheTask(View view) {
        int currentEditViewIndex = 0;
        for (int i=1; i<myLinearLayout.getChildCount();i++){
            if (myLinearLayout.getChildAt(i) instanceof EditText){
                tasks.put(allEditView[currentEditViewIndex], ((EditText) myLinearLayout.getChildAt(i)).getText().toString());
                currentEditViewIndex += 1;
            } else if(myLinearLayout.getChildAt(i) instanceof LinearLayout){
                LinearLayout addedLayout = (LinearLayout) myLinearLayout.getChildAt(i);
                tasks.put(allEditView[currentEditViewIndex], ((EditText) addedLayout.getChildAt(1)).getText().toString());
                currentEditViewIndex += 1;
            }
        }
        tasks.put("projectCode", projectCode);
        tasks.put("testID", testID);
        tasks.put("goalPageURL", goalPageURL);

        if (lastActivity.equals("NameTheTest")) {
            db.collection("tasks").
                    document(myId).
                    set(tasks).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            tasks.clear();
                            Log.d("Upload", "The upload of tasks is successful!");
                            FillTheTask.this.finish();
                        }
                    });
        } else {
            db.collection("tasks").
                    document(getIntent().getStringExtra("taskID")).
                    set(tasks).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            tasks.clear();
                            Log.d("Update", "The update of tasks is successful!");
                            FillTheTask.this.finish();
                        }
                    });
        }
    }

    public void addTheGoalPage(View view) {
        Intent intent = new Intent(this, SelectGoalPage.class);
        db.collection("prototypes").
                whereEqualTo("projectCode", projectCode).
                get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String prototypeName = (String)task.getResult().getDocuments().get(0).get("name");
                        Log.d("pName",prototypeName);
                        intent.putExtra("prototypeName", prototypeName);
                        startActivityForResult(intent, 1);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        goalPageURL = data.getStringExtra("URL");
    }
}
