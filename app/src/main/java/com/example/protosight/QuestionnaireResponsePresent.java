package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.protosight.models.TheTask;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionnaireResponsePresent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TheTask task;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private LinearLayout myLinearLayout;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private final String[] questionTitles = new String[]{"Q1", "Q2", "Q3", "Q4", "Q5"};

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_response);

        mAuth = FirebaseAuth.getInstance();

        myLinearLayout = findViewById(R.id.questionnaire_response_linearlayout);
        task = getIntent().getParcelableExtra("task");
        String testerName = task.getTester();
        ArrayList<String[]> QandA = task.toArray();
        Log.d("questionsss", Arrays.toString(QandA.get(0)));
        for (int i=0; i<QandA.size(); i++){
            addResponse(QandA.get(i), i);
        }

        navigationView = findViewById(R.id.personal_task_view_navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        myDrawerLayout = findViewById(R.id.questionnaire_response_drawerLayout);
        toolbar = findViewById(R.id.questionnaire_response_toolbar);
        toolbar.setTitle(testerName);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar, R.string.open, R.string.close);
        myDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void addResponse(String[] QA, int index){
        Log.d("liu", ""+index);
        View view = getLayoutInflater().inflate(R.layout.questionnaire_response_item, myLinearLayout, false);
        TextView questionTitle = view.findViewById(R.id.questionnaire_response_question_title);
        TextView questionContent = view.findViewById(R.id.questionnaire_response_question);
        TextView questionResponse = view.findViewById(R.id.questionnaire_response_response);
        questionTitle.setText(questionTitles[index]);
        questionContent.setText(QA[0]);
        questionResponse.setText(QA[1]);
        myLinearLayout.addView(view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, CreatorLandingPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_projects) {

            Intent intent = new Intent(this, CreatorLandingPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_tests) {

            Intent intent = new Intent(this, CreatorLandingPage.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            mAuth.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        return false;
    }
}
