package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.protosight.adapters.HotspotLinkScreenAdapter;
import com.example.protosight.models.HotSpot;
import com.example.protosight.views.DisplayRectView;
import com.example.protosight.views.DragRectView;


import java.util.ArrayList;
import java.util.List;

import at.lukle.clickableareasimage.ClickableArea;
import at.lukle.clickableareasimage.ClickableAreasImage;
import at.lukle.clickableareasimage.OnClickableAreaClickedListener;
import uk.co.senab.photoview.PhotoViewAttacher;

public class HotspotsLinkScreen extends AppCompatActivity {

    private String TAG = "HotspotsLinkScreen";
    private Toolbar toolbar;
    private List<ClickableArea> clickableAreas;
    private DragRectView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspots_link_screen);
        Intent intent = getIntent();


        ArrayList<String> images = getIntent().getStringArrayListExtra("images");
        String projectName = intent.getStringExtra("projectName");
        String current = intent.getExtras().getString("selectedImage");

        HotSpot hotSpot = intent.getParcelableExtra("hotspot");
        Log.d(TAG, hotSpot.toString());


        ImageView iv = findViewById(R.id.selected_image);
//        Bitmap myBitmap = BitmapFactory.decodeFile(current);
//        iv.setImageBitmap(myBitmap);




        Bitmap tempBitmap = BitmapFactory.decodeByteArray(intent.getExtras().getByteArray("bitmapp"),0, intent.getExtras().getByteArray("bitmapp").length);
        iv.setImageBitmap(tempBitmap);
//        DisplayRectView displayRectView = findViewById(R.id.hotspot_section);
//        displayRectView.setMinimumHeight(hotSpot.getH());
//        displayRectView.setMinimumWidth(hotSpot.getW());
//        displayRectView.setPadding(hotSpot.getX(), hotSpot.getY(),0,0);




        RecyclerView rv = (RecyclerView) findViewById(R.id.link_gallery);

        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HotspotsLinkScreen.this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        HotspotLinkScreenAdapter ia = new HotspotLinkScreenAdapter(images, HotspotsLinkScreen.this, projectName);
        rv.setAdapter(ia);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Log.d(TAG, current);

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
        }
        return true;
    }

}