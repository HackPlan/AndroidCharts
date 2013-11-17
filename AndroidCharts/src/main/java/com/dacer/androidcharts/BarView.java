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
    private Paint textPaint;
    private Paint bgPaint;
    private Paint fgPaint;
    private Rect rect;
    private int barWidth;
//    private boolean showSideMargin = true;
    private int bottomTextDescent;
    private boolean autoSetWidth = true;
    private int topMargin;
    private int bottomTextHeight;
    private ArrayList<String> bottomTextList;
    private int textSize;
    private final int MINI_BAR_WIDTH;
    private final int BAR_SIDE_MARGIN;
    private final int TEXT_TOP_MARGIN;
    private final int TEXT_COLOR = Color.parseColor("#9B9A9B");
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
        topMargin = MyUtils.dip2px(context, 5);
        textSize = MyUtils.sp2px(context, 15);
        barWidth = MyUtils.dip2px(context,22);
        MINI_BAR_WIDTH = MyUtils.dip2px(context,22);
        BAR_SIDE_MARGIN  = MyUtils.dip2px(context,22);
        TEXT_TOP_MARGIN = MyUtils.dip2px(context, 5);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * dataList will be reset when called is method.
     * @param bottomStringList The String ArrayList in the bottom.
     */
    public void setBottomTextList(ArrayList<String> bottomStringList){
        this.dataList = null;
        this.bottomTextList = bottomStringList;

        Rect r = new Rect();
        bottomTextDescent = 0;
        barWidth = MINI_BAR_WIDTH;
        for(String s:bottomTextList){
            textPaint.getTextBounds(s,0,s.length(),r);
            if(bottomTextHeight<r.height()){
                bottomTextHeight = r.height();
            }
            if(autoSetWidth&&(barWidth<r.width())){
                barWidth = r.width();
            }
            if(bottomTextDescent<(Math.abs(r.bottom))){
                bottomTextDescent = Math.abs(r.bottom);
            }
        }
        setMinimumWidth(2);
        postInvalidate();
    }

    /**
     *
     * @param dataList The ArrayList of Integer with the range of [0-100].
     */
    public void setDataList(ArrayList<Integer> dataList){
        this.dataList = dataList;
        setMinimumWidth(2);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int i = 1;
        if(dataList != null && !dataList.isEmpty()){
            for(Integer integer:dataList){
                rect.set(BAR_SIDE_MARGIN*i+barWidth*(i-1),
                        topMargin,
                        (BAR_SIDE_MARGIN+barWidth)* i,
                        getHeight()-bottomTextHeight-TEXT_TOP_MARGIN);
                canvas.drawRect(rect,bgPaint);
                rect.set(BAR_SIDE_MARGIN*i+barWidth*(i-1),
                        topMargin+(getHeight()-topMargin)*integer/100,
                        (BAR_SIDE_MARGIN+barWidth)* i,
                        getHeight()-bottomTextHeight-TEXT_TOP_MARGIN);
                canvas.drawRect(rect,fgPaint);
                i++;
            }
        }

        if(bottomTextList != null && !bottomTextList.isEmpty()){
            i = 1;
            for(String s:bottomTextList){
                canvas.drawText(s,BAR_SIDE_MARGIN*i+barWidth*(i-1)+barWidth/2,
                        getHeight()-bottomTextDescent,textPaint);
                i++;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mViewWidth = measureWidth(widthMeasureSpec);
        int mViewHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mViewWidth,mViewHeight);
    }

    private int measureWidth(int measureSpec){
        int preferred = 0;
        if(bottomTextList != null){
            preferred = bottomTextList.size()*(barWidth+BAR_SIDE_MARGIN)+BAR_SIDE_MARGIN;
        }
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec){
        int preferred = 222;
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
