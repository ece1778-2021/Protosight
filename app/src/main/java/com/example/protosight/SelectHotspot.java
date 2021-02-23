package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.protosight.views.DragRectView;

import java.util.ArrayList;
import java.util.List;

import at.lukle.clickableareasimage.ClickableArea;
import at.lukle.clickableareasimage.ClickableAreasImage;
import at.lukle.clickableareasimage.OnClickableAreaClickedListener;
import uk.co.senab.photoview.PhotoViewAttacher;

public class SelectHotspot extends AppCompatActivity implements OnClickableAreaClickedListener {
    private String TAG = "SelectHotspot";
    private DragRectView view;
    private List<ClickableArea> clickableAreas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hotspot);

        Intent intent = getIntent();


        ArrayList<String> images = getIntent().getStringArrayListExtra("images");
        String projectName = intent.getStringExtra("projectName");
        String current = intent.getExtras().getString("selectedImage");

        ImageView iv = findViewById(R.id.add_hotspot_image);
        iv.setImageBitmap(BitmapFactory.decodeFile(current));
        int[] coords = new int[2];
        iv.getLocationInWindow(coords);
        BitmapDrawable drawable2 = (BitmapDrawable) iv.getDrawable();
        int imageWidthInPx = (int) (drawable2.getBitmap().getWidth() );
        int imageHeightInPx = (int) (drawable2.getBitmap().getHeight());
        Log.d(TAG, "lol w " + imageWidthInPx);
        Log.d(TAG, "lol h " + imageHeightInPx);

        ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(iv), SelectHotspot.this);

        // Initialize your clickable area list
        clickableAreas = new ArrayList<>();
        clickableAreas.add(new ClickableArea(0, 0, 100, 200, current));
        clickableAreasImage.setClickableAreas(clickableAreas);
        view = (DragRectView) findViewById(R.id.dragRect);
        if (view != null) {
            view.setOnUpCallback(new DragRectView.OnUpCallback() {
                @Override
                public void onRectFinished(final Rect rect) {
                    int l,t,b,r;
                    l = rect.left - coords[0];
                    t = rect.top - coords[1];
                    b = rect.bottom - coords[1];
                    r = rect.right - coords[0];
//                    Log.d(TAG,"left d " + (rect.left - coords[0]));
//                    Log.d(TAG,"top d" + (rect.top - coords[1]));
//                    Log.d(TAG,"lef " + rect.left );
//                    Log.d(TAG,"top " + rect.top);
//
                    Log.d(TAG,"width: " + (rect.width()));
                    Log.d(TAG,"height: " + (rect.height()));
//                    Log.d(TAG,"ac w: " + (r - l));
//                    Log.d(TAG,"ac h: " + (b - t));
//
//                    Log.d(TAG,"hsfasdfsfadsfasddf ---- wtf");
//                    clickableAreas.add(new ClickableArea(l, t, r-l,b-t, current));
//                    clickableAreasImage.setClickableAreas(clickableAreas);
//                    HotSpot hotSpot = new HotSpot(rect.left , rect.top, rect.width(), rect.height(), current);
//
//                    clickableAreas.add(new ClickableArea(rect.left, rect.top, rect.width(), rect.height(), current));
////                    clickableAreasImage.setClickableAreas(clickableAreas);

                    // TODO: Add hotspot to image and then go to link screen
                    Toast.makeText(getApplicationContext(), "Rect is (" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")",
                            Toast.LENGTH_LONG).show();
//                    view.setVisibility(View.INVISIBLE);

//                    Intent i = new Intent(SelectHotspot.this, HotspotsLinkScreen.class);
//                    Bundle bundle = new Bundle();
//
//                    bundle.putStringArrayList("images", images);
//                    i.putExtras(bundle);
//                    i.putExtra("selectedImage", current);
//                    i.putExtra("projectName", projectName);
//
//                    startActivity(i);

                }
            });
        }
    }
    @Override
    public void onClickableAreaTouched(Object item) {
        Log.d(TAG, "asfdsaddfsadfsafasdfas------safadsf---");
        if (item instanceof String) {
            String text = (String) item;
            Toast.makeText(getApplicationContext(), "wtf " +text, Toast.LENGTH_LONG).show();
//            view.setVisibility(View.VISIBLE);
        }
    }
}