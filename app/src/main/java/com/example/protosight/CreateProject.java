package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.protosight.adapters.CreateProjectImageAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

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
    private ArrayList<Bitmap> images;

    private int CAMERA_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        images = new ArrayList<>();


    }

    public void projectNameSendOnClick(View view){
        EditText editTextProjectName = findViewById(R.id.project_name);
        String projectName =  editTextProjectName.getText().toString();
        RelativeLayout relativeLayout = findViewById(R.id.enter_project_name);
        Log.d(TAG, projectName);
        if (projectName.isEmpty()){
            Toast.makeText(CreateProject.this, "Project name cannot be empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            relativeLayout.setVisibility(View.INVISIBLE);
            CardView cardView = findViewById(R.id.upload_images_cardview);
            cardView.setVisibility(View.VISIBLE);

            TextView textViewProjectName = findViewById(R.id.creator_project_name);
            textViewProjectName.setText(projectName);
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout){
            mAuth.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        return true;
    }

    public void uploadImagesOnclick(View vew){
        Log.d(TAG, "upload images");
        if (ActivityCompat.checkSelfPermission(
                CreateProject.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CreateProject.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
        }


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            ClipData clipData = data.getClipData();
            if (clipData != null){
                for (int i=0; i < clipData.getItemCount(); i++){
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    try{
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        images.add(BitmapFactory.decodeStream(is));
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
            } else {
                Uri imageUri = data.getData();
                try{
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    images.add(BitmapFactory.decodeStream(is));
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
            loadUploadedImages();
        }

    }

    private void loadUploadedImages(){
        RecyclerView rv = (RecyclerView) findViewById(R.id.upload_images_recyclerview);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CreateProject.this, 2);
        rv.setLayoutManager(layoutManager);
        CreateProjectImageAdapter ia = new CreateProjectImageAdapter(images, CreateProject.this);
        rv.setAdapter(ia);

    }

}