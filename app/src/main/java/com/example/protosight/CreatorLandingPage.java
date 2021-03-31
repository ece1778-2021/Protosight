package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.protosight.adapters.HomeViewPagerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreatorLandingPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private String TAG = "CreatorLandingPage";
    private TextView  homeGreeting;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_landing_page);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar);

        homeGreeting = findViewById(R.id.home_greeting);
        tabLayout = findViewById(R.id.project_task_controller);
        viewPager = findViewById(R.id.home_viewPager);

        PagerAdapter pa = new HomeViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pa);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        db.collection("creators")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name = (String) documentSnapshot.get("username");
                        String homeDisplayName = "Hello, " + name;
                        homeGreeting.setText(homeDisplayName);
                    }
                });

        Log.d(TAG, "project --- " + CreateProject.project.toMap().toString());
        Log.d(TAG, "hotspots --- " + SelectHotspot.getHotSpots().toString());
        Log.d(TAG, "images --- " + CreateProject.getProjectImages().toString());
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){

            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_projects){

            TabLayout.Tab projectTab = tabLayout.getTabAt(0);
            tabLayout.selectTab(projectTab);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_tests){
            TabLayout.Tab testTab = tabLayout.getTabAt(1);
            tabLayout.selectTab(testTab);
            drawerLayout.closeDrawer(GravityCompat.START);

        } else if (id == R.id.logout){
            mAuth.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }


        return false;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.logout){
//            mAuth.signOut();
//            Intent intent = new Intent(this, Login.class);
//            startActivity(intent);
//        }
//        return true;
//    }



}