package com.example.protosight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protosight.adapters.CreateProjectImageAdapter;
import com.example.protosight.adapters.HotspotLinkScreenAdapter;
import com.example.protosight.models.HotSpot;
import com.example.protosight.views.DragRectView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class SelectHotspot extends AppCompatActivity {
    private String TAG = "SelectHotspot";
    private DragRectView view;
    private Toolbar toolbar;

    private int x,y,w,h;
    private ImageView iv;
    private Bitmap originImage;
    private Bitmap newImage;
    private ArrayList<String> images;
    private String projectName;
    private String current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hotspot);

        Intent intent = getIntent();


        images = intent.getStringArrayListExtra("images");


        projectName = intent.getStringExtra("projectName");
        current = intent.getExtras().getString("selectedImage");

        iv = findViewById(R.id.add_hotspot_image);
        originImage = BitmapFactory.decodeFile(current);


//        ListView listView = findViewById(R.id.select_link_image);
//        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{ "stest", "42"}));


        Log.d(TAG, "hotspot.toString()");
        iv.setImageBitmap(originImage);

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
                    Bitmap theBitmap = originImage.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(theBitmap);
                    canvas.drawRect(imagex,imagey,w+imagex,imagey+h, mRectPaint);
                    newImage = theBitmap.copy(Bitmap.Config.ARGB_8888, false);
                    iv.setImageBitmap(newImage);

                    toolbar = findViewById(R.id.toolbar);

                    setSupportActionBar(toolbar);



                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reset_confirm_link, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.reset_hotspot){
            Log.d(TAG, "Reset.....");
            iv.setImageBitmap(originImage);
            view.setVisibility(View.VISIBLE);
            view.clearFocus();
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


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            newImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            i.putExtra("bitmapp", byteArray);

//            startActivity(i);
            
            RecyclerView rv = findViewById(R.id.select_link_image);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SelectHotspot.this, 2);
            rv.setLayoutManager(layoutManager);
            CreateProjectImageAdapter ia = new CreateProjectImageAdapter(images, SelectHotspot.this, projectName);
            rv.setAdapter(ia);



        }
        return true;
    }

}