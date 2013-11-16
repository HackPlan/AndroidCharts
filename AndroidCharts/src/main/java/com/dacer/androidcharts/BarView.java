package com.dacer.androidcharts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Dacer on 11/11/13.
 */
public class BarView extends View {
    private ArrayList<Integer> dataList;
    private Paint bgPaint;
    private Paint fgPaint;
    private Rect rect;
    private int mViewWidth;
    private int mViewHeight;
    private int barWidth;
    private int topMargin;
    private final int BACKGROUND_COLOR = Color.parseColor("#F6F6F6");
    private final int FOREGROUND_COLOR = Color.parseColor("#FC496D");



    public BarView(Context context){
        this(context,null);
    }
    public BarView(Context context, AttributeSet attrs){
        super(context, attrs);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(BACKGROUND_COLOR);
        fgPaint = new Paint(bgPaint);
        fgPaint.setColor(FOREGROUND_COLOR);
        rect = new Rect();
        topMargin = MyUtils.dip2px(context,5);
    }

    /**
     *
     * @param dataList The ArrayList of Integer with the range of [0-100].
     */
    public void setDataList(ArrayList<Integer> dataList){
        this.dataList = dataList;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=1; i<8; i++){
            rect.set(barWidth*(2*i-1),topMargin,barWidth*2*i,mViewHeight);
            canvas.drawRect(rect,bgPaint);
            if(dataList != null && !dataList.isEmpty()){
                rect.set(barWidth*(2*i-1),
                        topMargin+(mViewHeight-topMargin)*dataList.get(i-1)/100,
                        barWidth*2*i,
                        mViewHeight);
                canvas.drawRect(rect,fgPaint);
            }
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
        int preferred = 600;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred){
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;

        switch(MeasureSpec.getMode(measureSpec)){
            case MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }

        return measurement;
    }


}
