package com.example.protosight;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;


import com.example.protosight.imageClickableArea.ClickableArea;
import com.example.protosight.imageClickableArea.ClickableAreasImage;
import com.example.protosight.imageClickableArea.OnClickableAreaClickedListener;
import com.example.protosight.models.HotSpot;
import com.example.protosight.models.TaskResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbisoft.hbrecorder.HBRecorder;
import com.hbisoft.hbrecorder.HBRecorderListener;

import java.io.File;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ParticipantStartTask extends AppCompatActivity implements OnClickableAreaClickedListener, HBRecorderListener {
    private Toolbar toolbar;
    private String TAG = "ParticipantStartTask";
    private FirebaseFirestore db;
    private String taskID;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private String projectID;
    private ImageView imageHolder;
    private long ONE_MEGABYTE = 1024 * 1024;
    private static int counter = 0;
    private int flag = 0;
    ArrayList<HotSpot> hotSpots = new ArrayList<>();
    ClickableAreasImage clickableAreasImage;


    private static final int SCREEN_RECORD_REQUEST_CODE = 1000;

    HBRecorder hbRecorder;
    private HashMap<String, String> hashMap;
    private String goalScreenPath;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_start_task);
//        setContentView(new DrawingView(this));




        Intent intent = getIntent();
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("task");
        taskID = intent.getStringExtra("taskID");
        counter = 0;
        Log.d(TAG, taskID);
        Log.d(TAG, "sdfasdfa....");
        db = FirebaseFirestore.getInstance();

        db.collection("tasks")
                .document(taskID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                goalScreenPath = (String) document.getData().get("goalPageURL");
                                Log.d(TAG, goalScreenPath);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

        hbRecorder = new HBRecorder(this, this);

        storage = FirebaseStorage.getInstance();
        hbRecorder.isAudioEnabled(true);
        hbRecorder.setOutputPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        storage = FirebaseStorage.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("test@gmail.com", "123123");


        projectID = hashMap.get("projectCode");

        db.collection("hotspots")
                .whereEqualTo("projectID", projectID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

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


        startRecordingScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_proto_menu, menu);

        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "herer...");
        int id = item.getItemId();
        if (id == R.id.stop_proto){
            Log.d(TAG, "herer...");
            flag = 1;
            hbRecorder.stopScreenRecording();
            Intent intent = new Intent(ParticipantStartTask.this, ParticipantListTasksPage.class);
            intent.putExtra("testCode", hashMap.get("testID"));
            startActivity(intent);

        }
        return true;
    }



    private void displayImage(ArrayList<HotSpot> first){
        StorageReference mStorageRef = storage.getReference();

        imageHolder = findViewById(R.id.task_image);
        String imageRef = first.get(0).getRelatedImage();



        mStorageRef.child(imageRef).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageHolder.setImageBitmap(bm);
                addClickables(first);
                Log.d(TAG, goalScreenPath);
                Log.d(TAG, imageRef);
                if (goalScreenPath.contains(imageRef)){
                    try {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ParticipantStartTask.this);
                        builder.setMessage("Congratulations ! You have reached the goal screen!\n")
                                .setCancelable(false)
                                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        progressDialog = new ProgressDialog(ParticipantStartTask.this);
                                        progressDialog.setTitle("Saving the result...");
                                        progressDialog.show();
                                        hbRecorder.stopScreenRecording();


                                        Log.d(TAG, "SAVING TASK..recording .." + hbRecorder.getFilePath());
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        TimeUnit.SECONDS.sleep(1);
                        dialog.show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                imageHolder.setImageBitmap(bm);
                if (goalScreenPath.contains(imageRef)){
                    Log.d(TAG, "goal!!!!");
                    try {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ParticipantStartTask.this);
                        builder.setMessage("Congratulations ! You have reached the goal screen!\n")
                                .setCancelable(false)
                                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        progressDialog = new ProgressDialog(ParticipantStartTask.this);
                                        progressDialog.setTitle("Saving the result...");
                                        progressDialog.show();
                                        hbRecorder.stopScreenRecording();


                                        Log.d(TAG, "SAVING TASK..recording .." + hbRecorder.getFilePath());
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        TimeUnit.SECONDS.sleep(1);
                        dialog.show();


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

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


        clickableAreasImage.setClickableAreas(clickableAreas);
    }


    public void stopRecordingOnClick(View view){
        FloatingActionButton button = findViewById(R.id.endTask);
        if (counter % 2 == 0) {
            button.setImageResource(R.drawable.ic_resume);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Have you completed your task?\n" +
                    "If yes, you work will be saved.\n")
                    .setCancelable(false)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "SAVING TASK");
                            progressDialog = new ProgressDialog(ParticipantStartTask.this);
                            progressDialog.setTitle("Comleting the task...");
                            progressDialog.show();
                            hbRecorder.stopScreenRecording();


                            Log.d(TAG, "SAVING TASK..recording .." + hbRecorder.getFilePath());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();


        } else {
            button.setImageResource(R.drawable.ic_stop);
        }
        counter ++;



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void saveVideoToStorage() {
        StorageReference ref;
        String path = hbRecorder.getFilePath();
        String refPath = "ScreenRecord/"
                + projectID
                + "/"
                + path.substring(path.lastIndexOf("/"), path.length());

        ref = storage.getReference().child(refPath);
        Uri file = Uri.fromFile(new File(path));


        ref.putFile(file).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog.dismiss();
                TaskResult taskResult = new TaskResult(ParticipantLandingPage.participantName, projectID, ParticipantLandingPage.testID,  taskID,  refPath);
                Intent intent = new Intent(ParticipantStartTask.this, ParticipantCompleteQuestionnaire.class);
                intent.putExtra("taskResult", taskResult);
                startActivity(intent);

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startRecordingScreen() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent permissionIntent = mediaProjectionManager != null ? mediaProjectionManager.createScreenCaptureIntent() : null;
        startActivityForResult(permissionIntent, SCREEN_RECORD_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCREEN_RECORD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Start screen recording
                hbRecorder.startScreenRecording(data, resultCode, this);

            }
        }
    }

    @Override
    public void HBRecorderOnStart() {
        Log.d("HBRecorder", "HBRecorderOnStart called");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void HBRecorderOnComplete() {
        if (flag == 1){
            Log.d(TAG, hbRecorder.getFilePath());
        } else {
            flag = 0;
            saveVideoToStorage();
        }

    }

    @Override
    public void HBRecorderOnError(int errorCode, String reason) {

    }
}