package com.example.protosight.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class DisplayRectView extends View {

    private Rect rectangle;
    private Paint paint;
    private boolean mDrawRect = false;
    private TextPaint mTextPaint = null;
    private Paint mRectPaint;

    public DisplayRectView(final Context context) {
        super(context);
        // create the Paint and set its color
        init();

    }

    public DisplayRectView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DisplayRectView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mRectPaint = new Paint();
        mRectPaint.setColor(getContext().getResources().getColor(android.R.color.holo_green_light));
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(5); // TODO: should take from resources

        mTextPaint = new TextPaint();
        mTextPaint.setColor(getContext().getResources().getColor(android.R.color.holo_green_light));
        mTextPaint.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(133, 133, 177, 160, mRectPaint );
    }

}
