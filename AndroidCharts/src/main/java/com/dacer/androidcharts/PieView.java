package com.dacer.androidcharts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Dacer on 9/26/14.
 */
public class PieView extends View {

    private Paint cirPaint;
    private Paint whiteLinePaint;
    private Point pieCenterPoint;
    private RectF cirRect;
    private RectF cirSelectedRect;

    private int mViewWidth;
    private int mViewHeight;
    private int margin;
    private int pieRadius;

    private ArrayList<PieHelper> pieHelperList;
    private int selectedIndex = NO_SELECTED_INDEX;

    public static final int NO_SELECTED_INDEX = -999;

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
//            for(ClockPieHelper pie : pieArrayList){
//                pie.update();
//                if(!pie.isAtRest()){
//                    needNewFrame = true;
//                }
//            }
            if (needNewFrame) {
                postDelayed(this, 10);
            }
            invalidate();
        }
    };

    public PieView(Context context){
        this(context,null);
    }
    public PieView(Context context, AttributeSet attrs){
        super(context, attrs);

        pieHelperList = new ArrayList<PieHelper>();
        cirPaint = new Paint();
        cirPaint.setAntiAlias(true);
        cirPaint.setColor(Color.GRAY);
        whiteLinePaint = new Paint(cirPaint);
        whiteLinePaint.setColor(Color.WHITE);
        whiteLinePaint.setStrokeWidth(2f);
        pieCenterPoint = new Point();
        cirRect = new RectF();
        cirSelectedRect = new RectF();
    }

    public void setDate(ArrayList<PieHelper> helperList){
        pieHelperList = helperList;
        postInvalidate();
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(pieHelperList.isEmpty()){
            return ;
        }

        canvas.drawColor(Color.GREEN);

        int index = 0;
        for(PieHelper pieHelper : pieHelperList){
            boolean selected = selectedIndex == index;
            if(selected){
                canvas.drawArc(cirSelectedRect, pieHelper.getStartDegree(), pieHelper.getSweep(), true, cirPaint);
            }else {
                canvas.drawArc(cirRect, pieHelper.getStartDegree(), pieHelper.getSweep(), true, cirPaint);
            }
//            canvas.drawText(pieHelper.getTitle(), );
            drawLineBesideCir(canvas, pieHelper.getStartDegree(), selected);
            drawLineBesideCir(canvas, pieHelper.getEndDegree(), selected);
            index++;
        }

    }

    private void drawLineBesideCir(Canvas canvas, float angel, boolean selectedCir){
        int sth2 = selectedCir? mViewHeight/2 : pieRadius; // Sorry I'm really don't know how to name the variable..
        int sth = 1;                                       // And it's
        if(angel%360 > 180 && angel%360 < 360){
            sth = -1;
        }
        float lineToX = (float)(mViewHeight/2 + Math.cos(Math.toRadians(-angel)) * sth2);
        float lineToY = (float)(mViewHeight/2 + sth * Math.abs(Math.sin(Math.toRadians(-angel))) * sth2);
        canvas.drawLine(pieCenterPoint.x, pieCenterPoint.y, lineToX, lineToY, whiteLinePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        margin = mViewWidth/16;
        pieRadius = (mViewWidth)/2-margin;
        pieCenterPoint.set(pieRadius+margin, pieRadius+margin);
        cirRect.set(pieCenterPoint.x-pieRadius,
                pieCenterPoint.y-pieRadius,
                pieCenterPoint.x+pieRadius,
                pieCenterPoint.y+pieRadius);
        cirSelectedRect.set(0,
                0,
                mViewWidth,
                mViewHeight);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec){
        int preferred = 3;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec){
        int preferred = mViewWidth;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred){
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int measurement;

        switch(View.MeasureSpec.getMode(measureSpec)){
            case View.MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case View.MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }
}
