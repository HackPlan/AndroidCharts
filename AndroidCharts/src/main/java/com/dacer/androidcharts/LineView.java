package com.dacer.androidcharts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import dacer.utils.MyUtils;
import dacer.views.calendar.SimpleDate;


/**
 * Created by Dacer on 11/4/13.
 */
public class LineView extends View {
    private int dateTextSize;
    private Rect textRect;
    private int backgroundGridWidth;
    private int backgroundGridHeight;
    private int horizontalGridNum;
    private int verticalGridNum; // only include the grid with 4 borders
    private int sideLineLength;  // --+--+--+--+--+--+--
                                 //  ↑ this           ↑

    private int topLineLength; // | | ← this
                               //-+-+-
    private SimpleDate nowDate;
    private ArrayList<Integer> dataList;
    private ArrayList<Integer> xCoordinateList;
    private ArrayList<Integer> yCoordinateList;
    private ArrayList<Point> drawPointList;
    private boolean showPopup = false;
    private Point selectedPoint;
    private Context mContext;

    //popup
    private Paint textPaint = new Paint();
    private int bottomTriangleHeight = 12;

    public LineView(Context context){
        this(context,null);
    }
    public LineView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        textRect = new Rect();
        backgroundGridWidth = MyUtils.dip2px(context,45);
        backgroundGridHeight = MyUtils.dip2px(context,25);
        sideLineLength = backgroundGridWidth/3*2;
        topLineLength = backgroundGridHeight/3;
        dateTextSize = MyUtils.sp2px(mContext,12);
        verticalGridNum = 4;

        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(MyUtils.sp2px(mContext, 13));
        textPaint.setStrokeWidth(5);
        textPaint.setTextAlign(Paint.Align.CENTER);

        if(isInEditMode()){
            nowDate = new SimpleDate(Calendar.getInstance(),context);
            setNowDate(nowDate);
            dataList = new ArrayList<Integer>();
            dataList.add(1);
            setDataList(dataList);
        }
    }

    /**
     *
     * @param nowDate 需显示的月份中任意一天的SimpleDate
     */
    public void setNowDate(SimpleDate nowDate){
        this.nowDate = nowDate;
        horizontalGridNum = nowDate.getThisMonthStrList().size()-1;
        postInvalidate();
    }

    /**
     *
     * @param dataList 汇总每天的数据的一个ArrayList，第0位为第一天.
     */
    public void setDataList(ArrayList<Integer> dataList){
//        verticalGridNum = 4;//init
        this.dataList = dataList;
        for(Integer integer:dataList){
            if(verticalGridNum<(integer+1)){
                verticalGridNum = integer+1;
            }
        }
        refreshCoordinateList();
        drawPointList = new ArrayList<Point>();
        for(int i=0;i<dataList.size();i++){
            drawPointList.add(new Point(xCoordinateList.get(i),yCoordinateList.
                    get(verticalGridNum-dataList.get(i))));
        }
        showPopup = false;
        setMinimumHeight(9999); // When the biggest data become bigger the layout height
                                // will not be changed.
                                // So this line is needed , but I don't know why...
        postInvalidate();
    }

    private void refreshCoordinateList(){
        xCoordinateList = new ArrayList<Integer>();
        yCoordinateList = new ArrayList<Integer>();
        for(int i=0;i<(horizontalGridNum+1);i++){
            xCoordinateList.add(sideLineLength+backgroundGridWidth*i);
        }
        for(int i=0;i<(verticalGridNum+1);i++){
            yCoordinateList.add(topLineLength+backgroundGridHeight*i);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawLines(canvas);
        drawDots(canvas);
        if(showPopup && selectedPoint != null){
            drawPopup(canvas,
                    String.valueOf(verticalGridNum - yCoordinateList.indexOf((int) selectedPoint.y)),
                    selectedPoint);
        }
    }

    /**
     *
     * @param canvas  The canvas you need to draw on.
     * @param point   The Coordinate from left bottom to right top. [0-max]
     */
    private void drawPopup(Canvas canvas,String num, Point point){

        int padding = MyUtils.dip2px(mContext,2);
        int marginBottom = MyUtils.dip2px(mContext,5);
        boolean singularNum = (num.length() == 1);
        int sidePadding = MyUtils.dip2px(mContext,singularNum? 8:5);
        int xCoor = point.x;
        int yCoor = point.y-MyUtils.dip2px(mContext,5);

        textPaint.getTextBounds(num,0,num.length(),textRect);
//        padding = 0;
        Rect r = new Rect(xCoor-textRect.width()/2-sidePadding,
                yCoor - textRect.height()-bottomTriangleHeight-padding*2-marginBottom,
                xCoor + textRect.width()/2+sidePadding,
                yCoor+padding-marginBottom);


        NinePatchDrawable popup = (NinePatchDrawable)getResources().
                getDrawable(R.drawable.popup_red);
        popup.setBounds(r);
        popup.draw(canvas);
        textPaint.setColor(Color.WHITE);
        canvas.drawText(num, xCoor, yCoor-bottomTriangleHeight-marginBottom, textPaint);
    }

    private void drawDots(Canvas canvas){
        Paint bigCirpaint = new Paint();
        bigCirpaint.setAntiAlias(true);
        bigCirpaint.setColor(Color.parseColor("#FF0033"));
        Paint smallCirPaint = new Paint(bigCirpaint);
        smallCirPaint.setColor(Color.parseColor("#FFFFFF"));
        for(Point p : drawPointList){
            canvas.drawCircle(p.x,p.y,MyUtils.dip2px(mContext,5),bigCirpaint);
            canvas.drawCircle(p.x,p.y,MyUtils.dip2px(mContext,2),smallCirPaint);
        }
    }

    private void drawLines(Canvas canvas){
        Paint linepaint = new Paint();
        linepaint.setAntiAlias(true);
        linepaint.setColor(Color.parseColor("#FF0033"));
        linepaint.setStrokeWidth(MyUtils.dip2px(mContext,2));
        for(int i=0;i<(dataList.size()-1);i++){
            canvas.drawLine(xCoordinateList.get(i),
                    yCoordinateList.get(verticalGridNum-dataList.get(i)),
                    xCoordinateList.get(i+1),
                    yCoordinateList.get(verticalGridNum-dataList.get(i+1)),
                    linepaint);
        }
    }



    private void drawBackground(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(MyUtils.dip2px(mContext,1f));
        paint.setColor(Color.parseColor("#EEEEEE"));
        PathEffect effects = new DashPathEffect(
                new float[]{10,5,10,5}, 1);
        //draw vertical lines
        Path linePath = new Path();
        int nowX = sideLineLength;
        for(int i=0;i<horizontalGridNum+1;i++){
            linePath.moveTo(nowX, 0);
            linePath.lineTo(nowX,(verticalGridNum+1)*backgroundGridHeight+topLineLength);
            nowX += backgroundGridWidth;
        }
        canvas.drawPath(linePath, paint);
        //draw dotted lines
        paint.setPathEffect(effects);
        Path dottedPath = new Path();
        int nowY = topLineLength;
        for(int i=0;i<verticalGridNum+1;i++){
            dottedPath.moveTo(0, nowY);
            dottedPath.lineTo(getWidth(), nowY);
            nowY += backgroundGridHeight;
        }
        canvas.drawPath(dottedPath, paint);
        //draw date str
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(dateTextSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#9B9A9B"));
        ArrayList<String> monthStrList = nowDate.getThisMonthStrList();
        for(int i=0;i<monthStrList.size();i++){
            canvas.drawText(monthStrList.get(i), sideLineLength+backgroundGridWidth*i,
                    (verticalGridNum + 1) * backgroundGridHeight +
                    backgroundGridHeight, textPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec){
        int preferred = backgroundGridWidth*horizontalGridNum+sideLineLength*2;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec){
        int preferred = backgroundGridHeight*(verticalGridNum+2)+dateTextSize/2+topLineLength;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred){
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;

        switch(MeasureSpec.getMode(measureSpec)){
            case MeasureSpec.EXACTLY:
                // This means the width of this view has been given.
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();

        Region r = new Region();
        int width = backgroundGridWidth/2;

        if(!drawPointList.isEmpty()){
            for(Point p : drawPointList){
                r.set(p.x-width,p.y-width,p.x+width,p.y+width);
                if (r.contains(point.x,point.y) && event.getAction() == MotionEvent.ACTION_DOWN){
                    selectedPoint = p;
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    if (r.contains(point.x,point.y)){
                        showPopup = true;
                    }
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_UP){
            postInvalidate();
        }
        return true;
    }
}
