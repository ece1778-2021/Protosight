package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.protosight.imageClickableArea.ClickableArea;
import com.example.protosight.imageClickableArea.ClickableAreasImage;
import com.example.protosight.imageClickableArea.OnClickableAreaClickedListener;
import com.example.protosight.models.HotSpot;
import com.example.protosight.views.DragRectView;

import java.util.ArrayList;
import java.util.List;


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
        Bitmap bitmap = BitmapFactory.decodeFile(current);

        iv.setImageBitmap(bitmap);

        BitmapDrawable drawable2 = (BitmapDrawable) iv.getDrawable();

        ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(iv),  SelectHotspot.this);

        // Initialize your clickable area list
        clickableAreas = new ArrayList<>();

        view = (DragRectView) findViewById(R.id.dragRect);
        if (view != null) {
            view.setOnUpCallback(new DragRectView.OnUpCallback() {
                @Override
                public void onRectFinished(final Rect rect) {

                    int x = (int) (rect.left );
                    int y = (int) (rect.top );
                    int w = (int) (rect.width());
                    int h = (int) (rect.height());


                    HotSpot hotspot = new HotSpot(x,y,w,h, current);
                    Log.d(TAG, hotspot.toString());

                    clickableAreas.add(new ClickableArea(x, y, w, h, hotspot));
                    clickableAreasImage.setClickableAreas(clickableAreas);



                    // TODO: Add hotspot to image and then go to link screen
                    Toast.makeText(SelectHotspot.this, "Rect is (" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")",
                            Toast.LENGTH_LONG).show();
                    view.setVisibility(View.INVISIBLE);

//                    i.putExtras(bundle);
//                    i.putExtra("selectedImage", current);
//                    i.putExtra("projectName", projectName);




                }
            });
        }
    }
    @Override
    public void onClickableAreaTouched(Object item) {
        Log.d(TAG, "asfdsaddfsadfsafasdfas------safadsf---");
        if (item instanceof String) {
            String text = (String) item;
            Toast.makeText(SelectHotspot.this, "wtf " +text, Toast.LENGTH_LONG).show();

        } else if (item instanceof HotSpot){
            Log.d(TAG, item.toString());

        }


    }
}