package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.protosight.models.HotSpot;
import com.example.protosight.views.DragRectView;


import java.util.ArrayList;


public class SelectHotspot extends AppCompatActivity {
    private String TAG = "SelectHotspot";
    private DragRectView view;
    private Toolbar toolbar;

    private int x,y,w,h;
    private ImageView iv;

    private static Bitmap newImage;
    private static Bitmap preImage;
    private ArrayList<String> images;
    private String projectName;
    private String current;
    private MenuItem itemReset;
    private MenuItem itemConfirm ;

    private MenuItem itemSaveHotspot;
    private static ArrayList<HotSpot> hotSpots = new ArrayList<>();
    private static Bitmap displayImage;
    private static int i;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hotspot);

        Intent intent = getIntent();

        images = intent.getStringArrayListExtra("images");


        projectName = intent.getStringExtra("projectName");
        current = intent.getExtras().getString("selectedImage");
        String isFromHotspotLinkScreen = intent.getStringExtra("fromHotspotLinkScreen");
        String cancelFromHotspotLinkScreen = intent.getStringExtra("cancelFromHotspotLinkScreen");

        i = CreateProject.getProjectImages().indexOf(current);
        Log.d(TAG, i + " - " + current);


        iv = findViewById(R.id.add_hotspot_image);
//        if (displayImage == null){
//            displayImage = BitmapFactory.decodeFile(current);
//        } else if (isFromHotspotLinkScreen == null){
//            displayImage = BitmapFactory.decodeFile(current);
//        }
        if (isFromHotspotLinkScreen != null){
            displayImage = newImage;
        } else if (cancelFromHotspotLinkScreen != null){
            displayImage = preImage;
        } else{
            displayImage =  BitmapFactory.decodeFile(current);
        }

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);




        Log.d(TAG, "hotspot.toString()");
        iv.setImageBitmap(displayImage);

        view = findViewById(R.id.dragRect);

        if (view != null) {
            view.setOnUpCallback(new DragRectView.OnUpCallback() {
                @Override
                public void onRectFinished(final Rect rect) {

                    x = (int) (rect.left) ;
                    y = (int) (rect.top) ;
                    w = (int) (rect.width());
                    h = (int) (rect.height());

                    view.setVisibility(View.INVISIBLE);


                    int imagex = (int) (rect.left) -(iv.getWidth()-iv.getDrawable().getBounds().right)/2;
                    int imagey = (int) (rect.top) -(iv.getHeight()-iv.getDrawable().getBounds().bottom)/2;

                    Paint mRectPaint = new Paint();
                    mRectPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
                    mRectPaint.setStyle(Paint.Style.STROKE);
                    mRectPaint.setStrokeWidth(5);
                    Bitmap theBitmap = displayImage.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(theBitmap);
                    canvas.drawRect(imagex,imagey,w+imagex,imagey+h, mRectPaint);
                    newImage = theBitmap.copy(Bitmap.Config.ARGB_8888, false);
                    iv.setImageBitmap(newImage);

                    preImage = displayImage;

                    itemReset.setVisible(true);
//                    itemNextImage.setVisible(false);
                    itemConfirm.setVisible(true); itemSaveHotspot.setVisible(false);



                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.reset_confirm_link, menu);
        Log.d(TAG, "creddd....");
        itemReset = menu.findItem(R.id.reset_hotspot);
        itemConfirm = menu.findItem(R.id.confirm_hotspot);

        itemSaveHotspot = menu.findItem(R.id.save_hotspot);
        itemReset.setVisible(false);
        itemConfirm.setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.reset_hotspot){
            Log.d(TAG, "Reset.....");
            iv.setImageBitmap(displayImage);
            view.setVisibility(View.VISIBLE);
            itemReset.setVisible(false);
            itemConfirm.setVisible(false);

            itemSaveHotspot.setVisible(true);

        } else if (id == R.id.confirm_hotspot){
            Log.d(TAG, "Confirm.....");
            Intent i = new Intent(SelectHotspot.this, HotspotsLinkScreen.class);
            Bundle b = new Bundle();
            HotSpot hotspot = new HotSpot(x,y,w,h, current);
            Log.d(TAG, hotspot.toString());

            b.putStringArrayList("images", images);

            i.putExtras(b);
            i.putExtra("selectedImage", current);
            i.putExtra("projectName", projectName);
            i.putExtra("hotspot", hotspot);
            displayImage = newImage;
            startActivity(i);
//            overridePendingTransition( R.anim.slide_out_up, R.anim.slide_in_up );

        } else if (id == R.id.save_hotspot){
            Log.d(TAG, "Saving....." + getHotSpots().toString());

            Intent intent = new Intent(SelectHotspot.this, CreateProject.class);

            intent.putExtra("next", "next");
            startActivity(intent);
        }
        return true;
    }



    public static void addHotspot(HotSpot hotSpot){
        hotSpots.add(hotSpot);
    }

    public static ArrayList<HotSpot> getHotSpots(){
        return hotSpots;
    }

    public static void clearHotspots(){
        hotSpots.removeAll(hotSpots);
    }

}