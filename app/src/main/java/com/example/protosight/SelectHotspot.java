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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.protosight.models.HotSpot;
import com.example.protosight.views.DragRectView;

import java.util.ArrayList;


public class SelectHotspot extends AppCompatActivity {
    private String TAG = "SelectHotspot";
    private DragRectView view;

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



                    // TODO: Add hotspot to image and then go to link screen
                    Toast.makeText(SelectHotspot.this, "Rect is (" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")",
                            Toast.LENGTH_LONG).show();
                    view.setVisibility(View.INVISIBLE);

                    Intent i = new Intent(SelectHotspot.this, HotspotsLinkScreen.class);
                    Bundle b = new Bundle();

                    b.putStringArrayList("images", images);
                    i.putExtras(b);

                    i.putExtra("selectedImage", current);
                    i.putExtra("projectName", projectName);
                    i.putExtra("hotspot", hotspot);

                    startActivity(i);

                }
            });
        }
    }

}