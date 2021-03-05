package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.protosight.adapters.ListAllProjectAdapter;
import com.example.protosight.imageClickableArea.ClickableArea;
import com.example.protosight.imageClickableArea.ClickableAreasImage;
import com.example.protosight.imageClickableArea.OnClickableAreaClickedListener;
import com.example.protosight.models.HotSpot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PlayPrototype extends AppCompatActivity implements OnClickableAreaClickedListener {
    private Toolbar toolbar;
    private String TAG = "PlayPrototype";
    private FirebaseFirestore db;
    private String firstImageRef;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private String projectID;
    private ImageView imageHolder;
    private long ONE_MEGABYTE = 1024 * 1024;
    private HotSpot first;
    ClickableAreasImage clickableAreasImage;
    List<ClickableArea> clickableAreas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_prototype);
        toolbar = findViewById(R.id.toolbar);
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        projectID = intent.getStringExtra("projectID");
        firstImageRef = intent.getStringExtra("firstImageRef");
        Log.d(TAG, projectID + "---" + firstImageRef);
        db.collection("hotspots")
                .whereEqualTo("projectID", projectID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<HotSpot> hotSpots = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int x = ((Long) document.get("x")).intValue();
                                int y = ((Long) document.get("y")).intValue();
                                int h = ((Long) document.get("h")).intValue();
                                int w = ((Long) document.get("w")).intValue();
                                String imageFrom = document.getString("screenshotFrom");
                                String imageTo = document.getString("screenshotTo");
                                boolean isFirst = (boolean) document.get("isFirst");

                                HotSpot hotSpot = new HotSpot(x,y,w,h,imageFrom);
                                hotSpot.setLinkImage(imageTo);
                                hotSpot.setFirst(isFirst);
                                hotSpots.add(hotSpot);
                                if (isFirst){
                                    displayImage(hotSpot);
                                    first = hotSpot;
                                }
                                Log.d(TAG, hotSpot.toMap().toString());
                            }
//                            addClickable(first);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

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
            Intent intent = new Intent(PlayPrototype.this, CreatorLandingPage.class);
            startActivity(intent);
        }
        return true;
    }

    private void displayImage(HotSpot hotSpot){
        StorageReference mStorageRef = storage.getReference();
        ConstraintLayout constraintLayout = findViewById(R.id.play_prototype_layout);
        imageHolder = findViewById(R.id.play_image);
        mStorageRef.child(hotSpot.getRelatedImage()).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageHolder.setImageBitmap(bm);
                addClickable(hotSpot);

            }

        });
    }


    @Override
    public void onClickableAreaTouched(Object item) {
        if (item instanceof HotSpot) {
            Log.d(TAG, "clickable clicked  - " + item.toString());
        }

    }

    private void addClickable(HotSpot hotSpot) {


        ImageView curr = findViewById(R.id.play_image);
        clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(curr), PlayPrototype.this);
        clickableAreas.add(new ClickableArea(hotSpot.getX(), hotSpot.getY(), hotSpot.getW(), hotSpot.getH(), hotSpot));
        clickableAreasImage.setClickableAreas(clickableAreas);
    }



}