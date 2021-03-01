package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.protosight.adapters.HotspotLinkScreenAdapter;
import com.example.protosight.models.HotSpot;

import com.example.protosight.views.DragRectView;


import java.util.ArrayList;
import java.util.List;

import at.lukle.clickableareasimage.ClickableArea;


public class HotspotsLinkScreen extends AppCompatActivity {

    private String TAG = "HotspotsLinkScreen";
    private Toolbar toolbar;
    private List<ClickableArea> clickableAreas;
    private DragRectView view;
    private static ArrayList<HotSpot> hotSpotsArray;
    private ArrayList<String> images;
    private ArrayList<String> restImageNames;
    private HotSpot selectedHotspot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspots_link_screen);
        Intent intent = getIntent();


        images = intent.getStringArrayListExtra("images");
        String projectName = intent.getStringExtra("projectName");
        String current = intent.getExtras().getString("selectedImage");
        selectedHotspot = intent.getParcelableExtra("hotspot");
        Log.d(TAG, selectedHotspot.toString());

        restImageNames = intent.getStringArrayListExtra("restImageName");
        String currentImageName = intent.getStringExtra("currentImageName");

        Log.d(TAG, currentImageName + "," + restImageNames.toString());

        ImageView iv = findViewById(R.id.selected_image);
        Bitmap tempBitmap = BitmapFactory.decodeByteArray(intent.getExtras().getByteArray("bitmapp"),0, intent.getExtras().getByteArray("bitmapp").length);
        iv.setImageBitmap(tempBitmap);

        TextView textView = findViewById(R.id.linkscreen_image_name);
        textView.setText(currentImageName);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Log.d(TAG, CreateProject.getProjectImages().toString());
        Log.d(TAG, CreateProject.getProjectName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.link_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.link_image){

            Log.d(TAG, "clicklcikclci");
            RecyclerView rv = (RecyclerView) findViewById(R.id.link_gallery);
            TextView textView = findViewById(R.id.hotspot_linkscreen_textview);
            textView.setText("Please select link image");
            textView.setVisibility(View.VISIBLE);
            rv.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(HotspotsLinkScreen.this, LinearLayoutManager.HORIZONTAL, false);
            rv.setLayoutManager(layoutManager);
            HotspotLinkScreenAdapter ia = new HotspotLinkScreenAdapter(images, HotspotsLinkScreen.this, CreateProject.getProjectName(), restImageNames, selectedHotspot);
            rv.setAdapter(ia);
        }
        return true;
    }

}