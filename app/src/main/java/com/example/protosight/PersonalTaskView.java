package com.example.protosight;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.protosight.models.TheTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;


public class PersonalTaskView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout myLinearLayout;
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar myToolBar;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseStorage db;
    private final String[] titles = new String[]{"Task 1", "Task 2", "Task 3"};
    private final String[] taskName = new String[]{"taskOne", "taskTwo", "taskThree"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_task_view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseStorage.getInstance();
        myLinearLayout = findViewById(R.id.personal_task_view_linearlayout);
        myDrawerLayout = findViewById(R.id.personal_task_view_drawerLayout);

        navigationView = findViewById(R.id.personal_task_view_navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        myToolBar = findViewById(R.id.personal_task_toolbar);
        String testerName = getIntent().getStringExtra("testerName");
        myToolBar.setTitle(testerName);
        setSupportActionBar(myToolBar);
        toggle = new ActionBarDrawerToggle(PersonalTaskView.this, myDrawerLayout, myToolBar, R.string.open, R.string.close);
        myDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        int index = 0;
        while (getIntent().hasExtra(taskName[index])) {
            TheTask task = getIntent().getParcelableExtra(taskName[index]);
            addTaskView(task, index);
            index += 1;
        }
    }

    private void addTaskView(TheTask task, int titleIndex) {
        View view = getLayoutInflater().inflate(R.layout.personal_task_view_item, myLinearLayout, false);

        TextView myTextView = view.findViewById(R.id.personal_task_view_taskTitle);
        myTextView.setText(titles[titleIndex]);

        Toolbar toolbar = view.findViewById(R.id.personal_task_view_toolBar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalTaskView.this, QuestionnaireResponsePresent.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }
        });

        VideoView myVideoView = view.findViewById(R.id.personal_task_view_videoView);
        String path = task.getVideoPath();
        myVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalTaskView.this, VideoViewFullScreen.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
        db.getReference().child(path).
                getDownloadUrl().addOnCompleteListener(
                new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        myVideoView.setVideoURI(task.getResult());
                        myVideoView.seekTo(1);
                        myLinearLayout.addView(view);
                    }
                }
        );
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
