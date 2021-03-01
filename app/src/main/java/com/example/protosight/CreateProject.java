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
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.protosight.adapters.CreateProjectImageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

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
//    private ArrayList<String> images;
//    private String projectName;
    private int CAMERA_REQUEST_CODE = 100;

    private static String projectName;
    private static ArrayList<String> images;
    private static boolean flag;
    private static String projectID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        images = new ArrayList<>();
        flag = true;

    }

    public void projectNameSendOnClick(View view){
        EditText editTextProjectName = findViewById(R.id.project_name);
        projectName =  editTextProjectName.getText().toString();
        RelativeLayout relativeLayout = findViewById(R.id.enter_project_name);

        if (projectName.isEmpty()){
            Toast.makeText(CreateProject.this, "Project name cannot be empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            relativeLayout.setVisibility(View.INVISIBLE);
//            CardView cardView = findViewById(R.id.upload_images_cardview);
//            cardView.setVisibility(View.VISIBLE);
//
            TextView textViewProjectName = findViewById(R.id.creator_project_name);
            textViewProjectName.setVisibility(View.VISIBLE);
            textViewProjectName.setText(projectName);

            FloatingActionButton button = findViewById(R.id.add_prototype_images);
            button.setVisibility(View.VISIBLE);
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
//                        InputStream is = getContentResolver().openInputStream(imageUri);
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        byte[] buffer = new byte[is.available()];
                        is.read(buffer);
                        File f = getPhoto("temp");
                        OutputStream outStream = new FileOutputStream(f);
                        outStream.write(buffer);
                        Log.d(TAG, f.getAbsolutePath());
                        images.add(f.getAbsolutePath());
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    } catch (IOException e ){
                        e.printStackTrace();
                    }
                }
            } else {
                Uri imageUri = data.getData();
                try{
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    File f = getPhoto("temp");
                    OutputStream outStream = new FileOutputStream(f);
                    outStream.write(buffer);


//                    Log.d(TAG, f.getAbsolutePath());


                    images.add(f.getAbsolutePath());
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e ){
                    e.printStackTrace();
                }
            }
            loadUploadedImages();
        }

    }

    private File getPhoto(String filename) throws IOException {
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(filename, ".jpg", storageDirectory);
    }


    private void loadUploadedImages(){
        RecyclerView rv = (RecyclerView) findViewById(R.id.upload_images_recyclerview);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CreateProject.this, 2);
        rv.setLayoutManager(layoutManager);
        CreateProjectImageAdapter ia = new CreateProjectImageAdapter(images, CreateProject.this, projectName);
        rv.setAdapter(ia);

    }

    public static ArrayList<String> getProjectImages(){
        return images;
    }

    public static String getProjectName(){
        return projectName;
    }

    public static String generateUUID(){

        if (flag){
            UUID uuid = UUID.randomUUID();
            projectID = uuid.toString();
            flag = false;
        }

        return projectID;
    }

}