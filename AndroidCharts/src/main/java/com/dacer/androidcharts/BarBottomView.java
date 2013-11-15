package com.dacer.androidcharts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Dacer on 11/12/13.
 */
public class BarBottomView extends View {

    private Paint paint;
    private int mViewWidth;
    private int mViewHeight;
    private int barWidth;
    private int textSize;

    private final int TEXT_COLOR = Color.parseColor("#9B9A9B");
    private String[] weekStr = {"S","M","T","W","T","F","S"};

    public BarBottomView(Context context){
        this(context,null);
    }
    public BarBottomView(Context context, AttributeSet attrs){
        super(context, attrs);
        textSize = MyUtils.sp2px(context,15);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(TEXT_COLOR);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0; i<7; i++){
            canvas.drawText(weekStr[i],barWidth*3/2+barWidth*i*2,textSize,paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        barWidth = mViewWidth/14;
        setMeasuredDimension(mViewWidth,mViewHeight);
    }

    private int measureWidth(int measureSpec){
        int preferred = 3;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec){
        int preferred = textSize;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred){
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int measurement = 0;

        switch(View.MeasureSpec.getMode(measureSpec)){
            case View.MeasureSpec.EXACTLY:
                // This means the width of this view has been given.
                measurement = specSize;
                break;
            case View.MeasureSpec.AT_MOST:
                // Take the minimum of the preferred size and what
                // we were told to be.
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }
}
