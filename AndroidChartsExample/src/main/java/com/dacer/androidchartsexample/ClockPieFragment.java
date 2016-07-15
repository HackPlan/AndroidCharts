package com.dacer.androidchartsexample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import im.dacer.androidcharts.ClockPieHelper;
import im.dacer.androidcharts.ClockPieView;

import java.util.ArrayList;

/**
 * Created by Dacer on 11/16/13.
 */
public class ClockPieFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pie, container, false);
        final ClockPieView clockPieView = (ClockPieView)rootView.findViewById(R.id.pie_view);
        Button button = (Button)rootView.findViewById(R.id.pie_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomSet(clockPieView);
            }
        });
        set(clockPieView);
        return rootView;
    }

    private void randomSet(ClockPieView clockPieView){
        ArrayList<ClockPieHelper> clockPieHelperArrayList = new ArrayList<ClockPieHelper>();
        for(int i=0; i<20; i++){
            int startHour = (int)(24*Math.random());
            int startMin = (int)(60*Math.random());
            int duration = (int)(50*Math.random());
            clockPieHelperArrayList.add(new ClockPieHelper(startHour,startMin,0,startHour,startMin+duration,0));
        }
        clockPieView.setDate(clockPieHelperArrayList);
    }

    private void set(ClockPieView clockPieView){
        ArrayList<ClockPieHelper> clockPieHelperArrayList = new ArrayList<ClockPieHelper>();
        clockPieHelperArrayList.add(new ClockPieHelper(1,50,2,30));
        clockPieHelperArrayList.add(new ClockPieHelper(6,50,8,30));
        clockPieView.setDate(clockPieHelperArrayList);
    }
}