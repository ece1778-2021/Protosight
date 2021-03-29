package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class EditPrototype extends AppCompatActivity {
    private Toolbar toolbar;
    private String TAG = "PlayPrototype";
    private FirebaseFirestore db;
    private String firstImageRef;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private String projectID;
    private ImageView imageHolder;
    private long ONE_MEGABYTE = 1024 * 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prototype);

        toolbar = findViewById(R.id.toolbar);
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        projectID = intent.getStringExtra("projectID");
        firstImageRef = intent.getStringExtra("firstImageRef");





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_proto_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.stop_proto){
            Intent intent = new Intent(EditPrototype.this, CreatorLandingPage.class);
            startActivity(intent);
        }
        return true;
    }
}