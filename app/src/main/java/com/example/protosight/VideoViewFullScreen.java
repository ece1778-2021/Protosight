package com.example.protosight;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;

public class VideoViewFullScreen extends AppCompatActivity {

    private VideoView myVideoView;
    private FirebaseStorage db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_fullscreen);

        db = FirebaseStorage.getInstance();
        myVideoView = findViewById(R.id.video_view_fullscreen);
        MediaController mediaController = new MediaController(this);
        myVideoView.setMediaController(mediaController);
        String path = getIntent().getStringExtra("path");
        db.getReference().child(path).
                getDownloadUrl().addOnCompleteListener(
                new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        myVideoView.setVideoURI(task.getResult());
                        myVideoView.start();
                    }
                }
        );
    }
}
