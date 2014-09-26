package com.dacer.androidcharts;


/**
 * Created by Dacer on 11/14/13.
 */
public class PieHelper {

    private float startDegree;
    private float endDegree;
    private float targetStartDegree;
    private float targetEndDegree;
    private String title;
    int velocity = 5;

    public PieHelper(float startDegree, float endDegree, PieHelper targetPie){
        startDegree = startDegree;
        endDegree = endDegree;
        targetStartDegree = targetPie.getStartDegree();
        targetEndDegree = targetPie.getEndDegree();
    }

    public PieHelper(float startDegree, float endDegree, String title){
        this.startDegree = startDegree;
        this.endDegree = endDegree;
        this.title = title;
    }


    PieHelper setTarget(float targetStart,float targetEnd){
        this.targetStartDegree = targetStart;
        this.targetEndDegree = targetEnd;
        return this;
    }

    PieHelper setTarget(PieHelper targetPie){
        this.targetStartDegree = targetPie.getStartDegree();
        this.targetEndDegree = targetPie.getEndDegree();
        return this;
    }

    boolean isAtRest(){
        return (startDegree==targetStartDegree)&&(endDegree==targetEndDegree);
    }

    void update(){
        this.startDegree = updateSelf(startDegree, targetStartDegree, velocity);
        this.endDegree = updateSelf(endDegree, targetEndDegree, velocity);
    }

    public String getTitle(){
        return title;
    }

    public float getSweep(){
        return endDegree-startDegree;
    }

    public float getStartDegree(){
        return startDegree;
    }

    public float getEndDegree(){
        return endDegree;
    }

    private float updateSelf(float origin, float target, int velocity){
        if (origin < target) {
            origin += velocity;
        } else if (origin > target){
            origin-= velocity;
        }
        if(Math.abs(target-origin)<velocity){
            origin = target;
        }
        return origin;
    }
}
