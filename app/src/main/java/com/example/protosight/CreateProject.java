package com.example.protosight;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateProject extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private String TAG = "CreatoeProject";
    private TextView username, homeGreeting;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        db.collection("creators")
//                .document(mAuth.getCurrentUser().getUid())
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        String name = (String) documentSnapshot.get("username");
//                        username.setText(name);
//
//
//                    }
//                });
    }
}