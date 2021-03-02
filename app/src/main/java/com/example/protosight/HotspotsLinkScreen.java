package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
    private DragRectView view;
    private static ArrayList<HotSpot> hotSpotsArray;

    private HotSpot selectedHotspot;

    private ArrayList<String> images;
    private String projectName;
    private String current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspots_link_screen);
        Intent intent = getIntent();


        images = intent.getStringArrayListExtra("images");
        projectName = intent.getStringExtra("projectName");
        current = intent.getExtras().getString("selectedImage");
        selectedHotspot = intent.getParcelableExtra("hotspot");
        Log.d(TAG, selectedHotspot.toString());

//
//
//        ImageView iv = findViewById(R.id.selected_image);
//        Bitmap tempBitmap = BitmapFactory.decodeByteArray(intent.getExtras().getByteArray("bitmapp"),0, intent.getExtras().getByteArray("bitmapp").length);
//        iv.setImageBitmap(tempBitmap);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Log.d(TAG, CreateProject.getProjectImages().toString());
        Log.d(TAG, CreateProject.getProjectName());

        RecyclerView rv = findViewById(R.id.link_gallery);

        rv.setHasFixedSize(true);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HotspotsLinkScreen.this, 2);
        rv.setLayoutManager(layoutManager);
        HotspotLinkScreenAdapter ia = new HotspotLinkScreenAdapter(images, HotspotsLinkScreen.this, selectedHotspot);
        rv.setAdapter(ia);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.link_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cancel_link){
            Intent intent = new Intent(HotspotsLinkScreen.this, SelectHotspot.class);
            Bundle b = new Bundle();
            b.putStringArrayList("images", images);

            intent.putExtras(b);
            intent.putExtra("selectedImage", current);
            intent.putExtra("projectName", projectName);
            intent.putExtra("cancel", "cancel");
            startActivity(intent);
            overridePendingTransition( R.anim.slide_out_up, R.anim.slide_in_up );
        }
        return true;
    }

}