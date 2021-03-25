package com.example.protosight.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TapShadowView extends View {


    Bitmap mBitmap;
    Paint paint;
    float x = 0;
    float y = 0;


    public TapShadowView(final Context context) {
        super(context);
        init();
    }

    public TapShadowView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TapShadowView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        mBitmap = Bitmap.createBitmap(400, 800, Bitmap.Config.ARGB_8888);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, 50, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            invalidate();
        }
        return false;
    }
}
