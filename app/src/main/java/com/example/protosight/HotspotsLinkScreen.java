package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.protosight.models.Project;

import java.util.ArrayList;

public class HotspotsLinkScreen extends AppCompatActivity {

    private String TAG = "HotspotsLinkScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspots_link_screen);
        Intent intent = getIntent();


        ArrayList<String> images = getIntent().getStringArrayListExtra("images");
        String projectName = intent.getStringExtra("projectName");
        String current = intent.getExtras().getString("selectedImage");

        ImageView iv = findViewById(R.id.selected_image);
        iv.setImageBitmap(BitmapFactory.decodeFile(current));
    }
}