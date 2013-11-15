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
 * Created by Dacer on 11/13/13.
 */
public class PieView extends View {

    private Paint textPaint;
    private Paint redPaint;
    private Paint linePaint;
    private Paint whitePaint;
    private int mViewWidth;
    private int mViewHeight;
    private int textSize;
    private int pieRadius;
    private Point pieCenterPoint;
    private Point tempPoint;
    private Point tempPointRight;
    private int lineLength;
    private float leftTextWidth;
    private float rightTextWidth;
    private float topTextHeight;
    private int lineThickness;
    private RectF cirRect;
    private Rect textRect;

    private ArrayList<PieHelper> helperArrayList;

    private final int TEXT_COLOR = Color.parseColor("#9B9A9B");
    private final int GRAY_COLOR = Color.parseColor("#D4D3D4");
    private final int RED_COLOR = Color.argb(50, 255, 0, 51);

    public PieView(Context context){
        this(context,null);
    }
    public PieView(Context context, AttributeSet attrs){
        super(context, attrs);
        textSize = MyUtils.sp2px(context, 15);
        lineThickness = MyUtils.dip2px(context, 1);
        lineLength = MyUtils.dip2px(context, 10);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm = new Paint.FontMetrics();
        textPaint.getFontMetrics(fm);
        textRect = new Rect();
        textPaint.getTextBounds("18",0,1,textRect);
        redPaint = new Paint(textPaint);
        redPaint.setColor(RED_COLOR);
        linePaint = new Paint(textPaint);
        linePaint.setColor(GRAY_COLOR);
        linePaint.setStrokeWidth(lineThickness);
        whitePaint = new Paint(linePaint);
        whitePaint.setColor(Color.WHITE);
        tempPoint = new Point();
        pieCenterPoint = new Point();
        tempPointRight = new Point();
        cirRect = new RectF();
        leftTextWidth = textPaint.measureText("18");
        rightTextWidth = textPaint.measureText("6");
        topTextHeight = textRect.height();
    }

    public void setDate(ArrayList<PieHelper> helperList){
        helperArrayList = helperList;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        if(helperArrayList != null){
            for(PieHelper helper:helperArrayList){
                canvas.drawArc(cirRect,helper.getStart(),helper.getSweep(),true,redPaint);
            }
        }
    }

    private void drawBackground(Canvas canvas){
        for(int i=0; i<12; i++){
            tempPoint.set(pieCenterPoint.x-(int)(Math.sin(Math.PI / 12 * i)*(pieRadius+lineLength)),
                    pieCenterPoint.y-(int)(Math.cos(Math.PI / 12 * i)*(pieRadius+lineLength)));
            tempPointRight.set(
                    pieCenterPoint.x+(int)(Math.sin(Math.PI / 12 * i)*(pieRadius+lineLength)),
                    pieCenterPoint.y+(int)(Math.cos(Math.PI / 12 * i)*(pieRadius+lineLength)));
            canvas.drawLine(tempPoint.x,tempPoint.y,tempPointRight.x,tempPointRight.y,linePaint);
        }
        canvas.drawCircle(pieCenterPoint.x,pieCenterPoint.y,pieRadius+lineLength/2, whitePaint);
        canvas.drawCircle(pieCenterPoint.x,pieCenterPoint.y,pieRadius+lineThickness,linePaint);
        canvas.drawCircle(pieCenterPoint.x,pieCenterPoint.y,pieRadius,whitePaint);
        canvas.drawText("0", pieCenterPoint.x, topTextHeight, textPaint);
        canvas.drawText("12",pieCenterPoint.x,mViewHeight,textPaint);
        canvas.drawText("18",leftTextWidth/2,
                pieCenterPoint.y+textRect.height()/2,textPaint);
        canvas.drawText("6",mViewWidth-rightTextWidth/2,
                pieCenterPoint.y+textRect.height()/2,textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        pieRadius = mViewWidth/2-lineLength*2-(int)(textPaint.measureText("18")/2);
        pieCenterPoint.set(mViewWidth/2-(int)rightTextWidth/2+(int)leftTextWidth/2,
                mViewHeight/2+textSize/2-(int)(textPaint.measureText("18")/2));
        cirRect.set(pieCenterPoint.x-pieRadius,
                pieCenterPoint.y-pieRadius,
                pieCenterPoint.x+pieRadius,
                pieCenterPoint.y+pieRadius);
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
