package com.dacer.androidcharts;

/**
 * Created by Dacer on 11/14/13.
 */
public class PieHelper {

    private float start;
    private float end;

    public PieHelper(float startDegree, float endDegree){
        start = startDegree;
        end = endDegree;
    }

    public PieHelper(int startHour,int startMin,int startSec,int endHour,int endMin,int endSec){
        start = 270+startHour*15+startMin*15/60+startSec*15/3600;
        end = 270+endHour*15+endMin*15/60+endSec*15/3600;
        while(end<start){
            end+=360;
        }
    }

    public void reset(float startDegree, float endDegree){
        start = startDegree;
        end = endDegree;
    }

    public float getSweep(){
        return end-start;
    }

    public float getStart(){
        return start;
    }

    public float getEnd(){
        return end;
    }
}
