package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.protosight.adapters.HotspotLinkScreenAdapter;
import com.example.protosight.views.DragRectView;


import java.util.ArrayList;

public class HotspotsLinkScreen extends AppCompatActivity {

    private String TAG = "HotspotsLinkScreen";
    private Toolbar toolbar;
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


        Log.d(TAG, images.toString());

        RecyclerView rv = (RecyclerView) findViewById(R.id.link_gallery);
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HotspotsLinkScreen.this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        HotspotLinkScreenAdapter ia = new HotspotLinkScreenAdapter(images, HotspotsLinkScreen.this, projectName);
        rv.setAdapter(ia);

        final DragRectView view = (DragRectView) findViewById(R.id.dragRect);
        if (view != null) {
            view.setOnUpCallback(new DragRectView.OnUpCallback() {
                @Override
                public void onRectFinished(final Rect rect) {
                    toolbar = findViewById(R.id.toolbar);

                    setSupportActionBar(toolbar);


                    Toast.makeText(getApplicationContext(), "Rect is (" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
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