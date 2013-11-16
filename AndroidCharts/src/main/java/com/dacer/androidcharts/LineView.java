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


/**
 * Created by Dacer on 11/4/13.
 */
public class LineView extends View {

    private int mViewWidth;
    private int mViewHeight;
    private Context mContext;

    //drawBackground
    private int dateOfAGird = 1;
    private int bottomTextHeight = 0;
    private int bottomTextSize;
    private final int backgroundGridWidth;
//    private float backgroundGridHeight;
    private int horizontalGridNum;
    private ArrayList<String> bottomStringList;
    private ArrayList<Integer> dataList;
    private ArrayList<Integer> xCoordinateList = new ArrayList<Integer>();
    private ArrayList<Integer> yCoordinateList = new ArrayList<Integer>();
    private ArrayList<Point> drawPointList;
    private Paint bottomTextPaint = new Paint();
    private int verticalGridNum; // only include the grid with 4 borders
    private int sideLineLength;  // --+--+--+--+--+--+--
                                //  ↑ this           ↑

    private int topLineLength; // | | ← this
                               //-+-+-
    private final int bottomLineLength;
    private final int bottomTextTopMargin;
    //popup
    private Rect popupTextRect = new Rect();
    private Paint popupTextPaint = new Paint();
    private int bottomTriangleHeight = 12;
    private boolean showPopup = false;
    private Point selectedPoint;
    private int popupTopPadding;
    private int popupBottomMargin;

    //Constants
    private final int DOT_INNER_CIR_RADIUS;
    private final int DOT_OUTER_CIR_RADIUS;

    private final int MIN_VERTICAL_GRID_NUM = 4;
    private final int MIN_HORIZONTAL_GRID_NUM = 1;
    private final int BACKGROUND_LINE_COLOR = Color.parseColor("#EEEEEE");
    private final int BOTTOM_TEXT_COLOR = Color.parseColor("#9B9A9B");

    public LineView(Context context){
        this(context,null);
    }
    public LineView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        popupTopPadding = MyUtils.dip2px(mContext,2);
        popupBottomMargin = MyUtils.dip2px(mContext,5);
        backgroundGridWidth = MyUtils.dip2px(context,45);
        sideLineLength = backgroundGridWidth/3*2;
        bottomTextSize = MyUtils.sp2px(mContext,12);
        bottomTextTopMargin = MyUtils.sp2px(mContext,5);
        bottomLineLength = MyUtils.sp2px(mContext, 22);
        topLineLength = MyUtils.dip2px(mContext, 12);
        DOT_INNER_CIR_RADIUS = MyUtils.dip2px(mContext, 2);
        DOT_OUTER_CIR_RADIUS = MyUtils.dip2px(mContext,5);

        popupTextPaint.setAntiAlias(true);
        popupTextPaint.setColor(Color.WHITE);
        popupTextPaint.setTextSize(MyUtils.sp2px(mContext, 13));
        popupTextPaint.setStrokeWidth(5);
        popupTextPaint.setTextAlign(Paint.Align.CENTER);


        bottomTextPaint.setAntiAlias(true);
        bottomTextPaint.setTextSize(bottomTextSize);
        bottomTextPaint.setTextAlign(Paint.Align.CENTER);
        bottomTextPaint.setStyle(Paint.Style.FILL);
        bottomTextPaint.setColor(BOTTOM_TEXT_COLOR);
    }

    /**
     * dataList will be reset when called is method.
     * @param bottomStringList The String ArrayList in the bottom.
     */
    public void setBottomStringList(ArrayList<String> bottomStringList){
        this.dataList = null;
        this.bottomStringList = bottomStringList;
        horizontalGridNum = bottomStringList.size()-1;
        if(horizontalGridNum<MIN_HORIZONTAL_GRID_NUM){
            horizontalGridNum = MIN_HORIZONTAL_GRID_NUM;
        }
        Rect r = new Rect();
        for(String s:bottomStringList){
            bottomTextPaint.getTextBounds(s,0,s.length(),r);
            if(bottomTextHeight<r.height()){
                bottomTextHeight = r.height();
            }
        }
        refreshView();
    }

    /**
     *
     * @param dataList The Integer ArrayList for showing,
     *                 dataList.size() must < bottomStringList.size()
     */
    public void setDataList(ArrayList<Integer> dataList){
        this.dataList = dataList;
        if(dataList.size() > bottomStringList.size()){
            throw new RuntimeException("dacer.LineView error:" +
                    " dataList.size() > bottomStringList.size() !!!");
        }
        refreshView();
    }

    private void refreshView(){
        verticalGridNum = MIN_VERTICAL_GRID_NUM;
        if(dataList != null && !dataList.isEmpty()){
            for(Integer integer:dataList){
                if(verticalGridNum<(integer+1)){
                    verticalGridNum = integer+1;
                }
            }
        }

        // For prevent popup can't be completely showed when backgroundGridHeight is too small.
        // But this code not so good.
        if((mViewHeight-topLineLength-bottomTextHeight-bottomTextTopMargin)/
                (verticalGridNum+2)<getPopupHeight()){
            topLineLength = getPopupHeight()+DOT_OUTER_CIR_RADIUS+DOT_INNER_CIR_RADIUS;
        }else{
            topLineLength = MyUtils.dip2px(mContext,12);
        }
        xCoordinateList.clear();
        yCoordinateList.clear();
        for(int i=0;i<(horizontalGridNum+1);i++){
            xCoordinateList.add(sideLineLength + backgroundGridWidth*i);
        }
        for(int i=0;i<(verticalGridNum+1);i++){
            yCoordinateList.add(topLineLength + ((mViewHeight-topLineLength-bottomTextHeight-bottomTextTopMargin-
                    bottomLineLength)*i/(verticalGridNum)));
        }

        drawPointList = new ArrayList<Point>();
        if(dataList != null && !dataList.isEmpty()){
            for(int i=0;i<dataList.size();i++){
                int x = xCoordinateList.get(i);
                int y = yCoordinateList.get(verticalGridNum - dataList.get(i));
                drawPointList.add(new Point(x, y));
            }
        }

        showPopup = false;
        setMinimumWidth(0); // It can help the LineView reset the Width,
                                // I don't know the better way..
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackgroundLines(canvas);
        drawLines(canvas);
        drawDots(canvas);
        if(showPopup && selectedPoint != null){
            drawPopup(canvas,
                    String.valueOf(verticalGridNum - yCoordinateList.indexOf(selectedPoint.y)),
                    selectedPoint);
        }
    }

    /**
     *
     * @param canvas  The canvas you need to draw on.
     * @param point   The Point consists of the x y coordinates from left bottom to right top.
     *                Like is ↓
     *                3
     *                2
     *                1
     *                0 1 2 3 4 5
     */
    private void drawPopup(Canvas canvas,String num, Point point){
        boolean singularNum = (num.length() == 1);
        int sidePadding = MyUtils.dip2px(mContext,singularNum? 8:5);
        int x = point.x;
        int y = point.y-MyUtils.dip2px(mContext,5);

        popupTextPaint.getTextBounds(num,0,num.length(),popupTextRect);
        Rect r = new Rect(x-popupTextRect.width()/2-sidePadding,
                y - popupTextRect.height()-bottomTriangleHeight-popupTopPadding*2-popupBottomMargin,
                x + popupTextRect.width()/2+sidePadding,
                y+popupTopPadding-popupBottomMargin);


        NinePatchDrawable popup = (NinePatchDrawable)getResources().
                getDrawable(R.drawable.popup_red);
        popup.setBounds(r);
        popup.draw(canvas);
        canvas.drawText(num, x, y-bottomTriangleHeight-popupBottomMargin, popupTextPaint);
    }

    private int getPopupHeight(){
        popupTextPaint.getTextBounds("9",0,1,popupTextRect);
        Rect r = new Rect(-popupTextRect.width()/2,
                 - popupTextRect.height()-bottomTriangleHeight-popupTopPadding*2-popupBottomMargin,
                 + popupTextRect.width()/2,
                +popupTopPadding-popupBottomMargin);
        return r.height();
    }

    private void drawDots(Canvas canvas){
        Paint bigCirPaint = new Paint();
        bigCirPaint.setAntiAlias(true);
        bigCirPaint.setColor(Color.parseColor("#FF0033"));
        Paint smallCirPaint = new Paint(bigCirPaint);
        smallCirPaint.setColor(Color.parseColor("#FFFFFF"));
        if(drawPointList!=null && !drawPointList.isEmpty()){
            for(Point p : drawPointList){
                canvas.drawCircle(p.x,p.y,DOT_OUTER_CIR_RADIUS,bigCirPaint);
                canvas.drawCircle(p.x, p.y,DOT_INNER_CIR_RADIUS, smallCirPaint);
            }
        }
    }

    private void drawLines(Canvas canvas){
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor("#FF0033"));
        linePaint.setStrokeWidth(MyUtils.dip2px(mContext,2));
        if(dataList!=null && !dataList.isEmpty()){
            for(int i=0;i<(dataList.size()-1);i++){
                canvas.drawLine(xCoordinateList.get(i),
                        yCoordinateList.get(verticalGridNum-dataList.get(i)),
                        xCoordinateList.get(i+1),
                        yCoordinateList.get(verticalGridNum-dataList.get(i+1)),
                        linePaint);
            }
        }
    }

    private void drawBackgroundLines(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(MyUtils.dip2px(mContext,1f));
        paint.setColor(BACKGROUND_LINE_COLOR);
        PathEffect effects = new DashPathEffect(
                new float[]{10,5,10,5}, 1);

        //draw vertical lines
        for(int i=0;i<xCoordinateList.size();i++){
            canvas.drawLine(xCoordinateList.get(i), 0, xCoordinateList.get(i), mViewHeight - bottomTextTopMargin - bottomTextHeight, paint);
        }

        //draw dotted lines
        paint.setPathEffect(effects);
        Path dottedPath = new Path();
        for(int i=0;i<yCoordinateList.size();i++){
            if((yCoordinateList.size()-1-i)%dateOfAGird == 0){
                dottedPath.moveTo(0, yCoordinateList.get(i));
                dottedPath.lineTo(getWidth(), yCoordinateList.get(i));
//                canvas.drawLine(0,yCoordinateList.get(i),getWidth(),yCoordinateList.get(i),paint);
                canvas.drawPath(dottedPath, paint);
            }
        }

        //draw bottom text
        if(bottomStringList != null){
            for(int i=0;i<bottomStringList.size();i++){
                canvas.drawText(bottomStringList.get(i), sideLineLength+backgroundGridWidth*i,
                        mViewHeight, bottomTextPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        refreshView();
        setMeasuredDimension(mViewWidth,mViewHeight);
    }

    private int measureWidth(int measureSpec){
        int preferred = backgroundGridWidth*horizontalGridNum+sideLineLength*2;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec){
        int preferred = 0;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        Region r = new Region();
        int width = backgroundGridWidth/2;
        if(drawPointList != null || !drawPointList.isEmpty()){
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
