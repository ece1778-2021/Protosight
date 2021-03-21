package com.example.protosight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.protosight.adapters.HomeViewPagerAdapter;
import com.example.protosight.adapters.TestViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class CreateTask extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.task_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_task);
        viewPager = findViewById(R.id.test_viewPager);
        tabLayout = findViewById(R.id.test_testResult_controller);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String testName = getIntent().getStringExtra("theTestName");
        String testID = getIntent().getStringExtra("testID");
        String projectCode = getIntent().getStringExtra("projectCode");
        String lastActivity = getIntent().getStringExtra("lastActivity");
        setSupportActionBar(toolbar);
        toolbar.setTitle(testName);
        toggle = new ActionBarDrawerToggle(CreateTask.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        PagerAdapter pa = new TestViewPagerAdapter(getSupportFragmentManager(),
                                                    tabLayout.getTabCount(),
                                                    testID,
                                                    lastActivity,
                                                    projectCode);
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
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(this, CreatorLandingPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_projects){

            Intent intent = new Intent(this, CreatorLandingPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_tests){

            Intent intent = new Intent(this, CreatorLandingPage.class);
            startActivity(intent);
        } else if (id == R.id.logout){
            mAuth.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        return false;
    }
}
