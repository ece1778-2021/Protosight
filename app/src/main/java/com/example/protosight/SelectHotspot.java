package com.example.protosight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.protosight.models.HotSpot;
import com.example.protosight.views.DragRectView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class SelectHotspot extends AppCompatActivity {
    private String TAG = "SelectHotspot";
    private DragRectView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hotspot);

        Intent intent = getIntent();


        ArrayList<String> images = intent.getStringArrayListExtra("images");

        ArrayList<String> restImageNames = intent.getStringArrayListExtra("restImageName");
        String currentImageName = intent.getStringExtra("currentImageName");

        String projectName = intent.getStringExtra("projectName");
        String current = intent.getExtras().getString("selectedImage");

        ImageView iv = findViewById(R.id.add_hotspot_image);
        Bitmap bitmap = BitmapFactory.decodeFile(current);

        Log.d(TAG, currentImageName + "," + restImageNames.toString());


        iv.setImageBitmap(bitmap);

        BitmapDrawable drawable2 = (BitmapDrawable) iv.getDrawable();

        view = (DragRectView) findViewById(R.id.dragRect);
        if (view != null) {
            view.setOnUpCallback(new DragRectView.OnUpCallback() {
                @Override
                public void onRectFinished(final Rect rect) {

                    int x = (int) (rect.left) ;
                    int y = (int) (rect.top) ;
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
                    b.putStringArrayList("restImageName", restImageNames);
                    i.putExtras(b);
                    i.putExtra("selectedImage", current);
                    i.putExtra("projectName", projectName);
                    i.putExtra("hotspot", hotspot);
                    i.putExtra("currentImageName", currentImageName);


                    int imagex = (int) (rect.left) -(iv.getWidth()-iv.getDrawable().getBounds().right)/2;
                    int imagey = (int) (rect.top) -(iv.getHeight()-iv.getDrawable().getBounds().bottom)/2;

                    Paint mRectPaint = new Paint();
                    mRectPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
                    mRectPaint.setStyle(Paint.Style.STROKE);
                    mRectPaint.setStrokeWidth(5);
                    Bitmap theBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(theBitmap);
                    canvas.drawRect(imagex,imagey,w+imagex,imagey+h, mRectPaint);
                    Bitmap bb = theBitmap.copy(Bitmap.Config.ARGB_8888, false);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bb.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] byteArray = baos.toByteArray();
                    i.putExtra("bitmapp", byteArray);

                    startActivity(i);

                }
            });
        }
    }

}