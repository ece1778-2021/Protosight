package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.protosight.imageClickableArea.ClickableArea;
import com.example.protosight.imageClickableArea.ClickableAreasImage;
import com.example.protosight.imageClickableArea.OnClickableAreaClickedListener;
import com.example.protosight.models.HotSpot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ParticipantStartTask extends AppCompatActivity implements OnClickableAreaClickedListener {
    private Toolbar toolbar;
    private String TAG = "ParticipantStartTask";
    private FirebaseFirestore db;
    private String firstImageRef;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private String projectID;
    private ImageView imageHolder;
    private long ONE_MEGABYTE = 1024 * 1024;

    ArrayList<HotSpot> hotSpots = new ArrayList<>();
    ClickableAreasImage clickableAreasImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_start_task);
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("task");

        toolbar = findViewById(R.id.toolbar);
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("test@gmail.com", "123123");

        setSupportActionBar(toolbar);
        projectID = hashMap.get("projectCode");
//        firstImageRef = intent.getStringExtra("firstImageRef");
        Log.d(TAG, "Wtf - - -" + projectID);


        db.collection("hotspots")
                .whereEqualTo("projectID", projectID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                            ArrayList<HotSpot> hotSpots = new ArrayList<>();
                            ArrayList<HotSpot> first = new ArrayList<>();
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
                                    first.add(hotSpot);
                                }

                            }

                            displayImage(first);



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
            Intent intent = new Intent(ParticipantStartTask.this, Login.class);
            startActivity(intent);
        }
        return true;
    }



    private void displayImage(ArrayList<HotSpot> first){
        StorageReference mStorageRef = storage.getReference();

        imageHolder = findViewById(R.id.task_image);
        String imageRef = first.get(0).getRelatedImage();



        mStorageRef.child(imageRef).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageHolder.setImageBitmap(bm);
                addClickables(first);

            }

        });
    }


    @Override
    public void onClickableAreaTouched(Object item) {
        if (item instanceof HotSpot) {

            String nextImage = ((HotSpot) item).getLinkImage();
            ArrayList<HotSpot> nexts = new ArrayList<>();
            boolean found = false;
            HotSpot last = null;
            for (HotSpot s : hotSpots){

                if (s.getRelatedImage().equals(nextImage)){
                    nexts.add(s);
                    found = true;
                }
                if (s.getLinkImage().equals(nextImage)){
                    last = s;
                }

            }

            if (found){
                displayImage(nexts);
            } else {
                displayLastImage(last);
            }


        }

    }


    private void displayLastImage(HotSpot hotSpot){
        StorageReference mStorageRef = storage.getReference();

        imageHolder = findViewById(R.id.task_image);
        String imageRef = hotSpot.getLinkImage();
        mStorageRef.child(imageRef).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageHolder.setImageBitmap(bm);

            }

        });
    }


    private void addClickables(ArrayList<HotSpot> hotSpot) {


        ImageView curr = findViewById(R.id.task_image);
        clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(curr), ParticipantStartTask.this);
        List<ClickableArea> clickableAreas = new ArrayList<>();
        for (HotSpot s : hotSpot){
            clickableAreas.add(new ClickableArea(s.getX(), s.getY(), s.getW(), s.getH(), s));
        }
        Log.d(TAG, clickableAreas.toString());

        clickableAreasImage.setClickableAreas(clickableAreas);
    }



}