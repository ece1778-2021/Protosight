package com.example.protosight.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class TapShadowView extends View {


    private Bitmap mBitmap;
    private Paint paint;
    private float x = 0;
    private float y = 0;

    private Transformation mTransformation;
    private AlphaAnimation mFadeOut;

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
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTransformation = new Transformation();
        //Construct an animation that will do all the timing math for you
        mFadeOut = new AlphaAnimation(1f, 0f);
        mFadeOut.setDuration(500);
        //Use a listener to trigger the end action
        mFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Trigger your action to change screens here.
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, 30, paint);
        if (mFadeOut.hasStarted() && !mFadeOut.hasEnded()) {
            mFadeOut.getTransformation(System.currentTimeMillis(), mTransformation);
            //Keep drawing until we are done
            paint.setAlpha((int)(255 * mTransformation.getAlpha()));
            invalidate();
        } else {
            //Reset the alpha if animation is canceled
            paint.setAlpha(255);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            mFadeOut.start();
            mFadeOut.getTransformation(System.currentTimeMillis(), mTransformation);

            invalidate();

        }
        return false;
    }

}
