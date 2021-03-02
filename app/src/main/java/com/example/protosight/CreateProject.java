package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;

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
import com.example.protosight.models.HotSpot;
import com.example.protosight.models.Project;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private FirebaseStorage storage;

    private int CAMERA_REQUEST_CODE = 100;
    private MenuItem itemSave;
    private static String projectName;
    private static ArrayList<String> images = new ArrayList<>();
    private static boolean flag;
    public static String projectID = generateUUID();
    public static Project project = new Project();
    private int upload_count = 0;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        project.setProjectCode(projectID);
        Intent intent = getIntent();
        String next = intent.getStringExtra("next");
        if (next != null){
            setVisibilityForImage();
            loadUploadedImages();
        }

        db.collection("creators")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String creator = (String) documentSnapshot.get("username");
                        String creatorEmail = (String) documentSnapshot.get("email");
                        CreateProject.project.setCreator(creator);
                        CreateProject.project.setCreatorEmail(creatorEmail);

                    }
                });


    }

    public void projectNameSendOnClick(View view){
        EditText editTextProjectName = findViewById(R.id.project_name);
        projectName =  editTextProjectName.getText().toString();


        if (projectName.isEmpty()){
            Toast.makeText(CreateProject.this, "Project name cannot be empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            project.setProjectName(projectName);
            setVisibilityForImage();

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        itemSave = menu.findItem(R.id.save_project);
//        itemSave.setVisible(false);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_project){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving the prototypes...");
            progressDialog.show();
            saveHotspotsToDB();
            saveProjectToDB();
            saveImagesToStorage();
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
            project.setImages(images);
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

    private static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private void setVisibilityForImage(){
        RelativeLayout relativeLayout = findViewById(R.id.enter_project_name);
        relativeLayout.setVisibility(View.INVISIBLE);

        TextView textViewProjectName = findViewById(R.id.creator_project_name);
        textViewProjectName.setVisibility(View.VISIBLE);
        textViewProjectName.setText(projectName);

        FloatingActionButton button = findViewById(R.id.add_prototype_images);
        button.setVisibility(View.VISIBLE);

    }

    private void saveImagesToStorage(){
        StorageReference ref;

        for (String path : getProjectImages()){
            String refPath = "prototypes/"
                    + mAuth.getCurrentUser().getUid()
                    + "/" + getProjectName()
                    + path.substring(path.lastIndexOf("/"), path.length());
            ref = storage.getReference().child(refPath);
            Uri file = Uri.fromFile(new File(path));
            ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, path + "  UPLOADED");
                    upload_count += 1;
                    if (upload_count == getProjectImages().size()){
                        CreateProject.images.removeAll(CreateProject.images);
                        project.clear();
                        SelectHotspot.clearHotspots();
                        progressDialog.dismiss();
                        Intent intent = new Intent(CreateProject.this, CreatorLandingPage.class);
                        startActivity(intent);

                    }
                }
            });

        }

    }

    private void saveProjectToDB(){
        long ts = System.currentTimeMillis();
        String firstImageRef = "prototypes/"
                + mAuth.getCurrentUser().getUid()
                + "/" + getProjectName()
                + project.getFirstImageRef().substring(project.getFirstImageRef().lastIndexOf("/"), project.getFirstImageRef().length());
        project.setFirstImageRef(firstImageRef);
        project.setTimestamp(ts);
        db.collection("prototypes").document()
                .set(project.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "project ADD to DB");
                    }
                });

    }

    private void saveHotspotsToDB(){

        for (HotSpot hotSpot : SelectHotspot.getHotSpots()){
            String from = "prototypes/"
                    + mAuth.getCurrentUser().getUid()
                    + "/" + getProjectName()
                    + hotSpot.getRelatedImage().substring(hotSpot.getRelatedImage().lastIndexOf("/"), hotSpot.getRelatedImage().length());
            String to = "prototypes/"
                    + mAuth.getCurrentUser().getUid()
                    + "/" + getProjectName()
                    + hotSpot.getLinkImage().substring(hotSpot.getLinkImage().lastIndexOf("/"), hotSpot.getLinkImage().length());

            hotSpot.setRelatedImage(from);
            hotSpot.setLinkImage(to);
            db.collection("hotspots").document()
                    .set(hotSpot.toMap())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "HOTSPOTS ADD to DB");
                        }
                    });

        }

    }

}