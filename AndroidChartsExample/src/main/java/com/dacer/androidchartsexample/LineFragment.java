package com.dacer.androidchartsexample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dacer.androidcharts.LineView;
import com.dacer.androidcharts.SimpleDate;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Dacer on 11/15/13.
 */
public class LineFragment extends Fragment {

    int temp = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line, container, false);
        final LineView lineView = (LineView)rootView.findViewById(R.id.line_view);
        set(lineView);

        Button lineButton = (Button)rootView.findViewById(R.id.line_button);
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regularSet(lineView);
            }
        });
        return rootView;
    }

    private void ramdomSet(LineView lineView){
        ArrayList<String> test = new ArrayList<String>();
        for (int i=0; i<20; i++){
            test.add(String.valueOf(i+1));
        }
        lineView.setBottomStringList(test);
        ArrayList<Integer> dataList = new ArrayList<Integer>();
        int random = (int)(Math.random()*10+1);
        for (int i=0; i<20; i++){
            dataList.add((int)(Math.random()*random));
        }
        lineView.setDataList(dataList);
    }

    private void regularSet(LineView lineView){
        ArrayList<String> test = new ArrayList<String>();
        for (int i=0; i<temp; i++){
            test.add(String.valueOf(i+1));
        }
        lineView.setBottomStringList(test);
        ArrayList<Integer> dataList = new ArrayList<Integer>();
        for (int i=0; i<temp; i++){
            dataList.add(i);
        }
        temp++;
        lineView.setDataList(dataList);
    }

    private void set(LineView lineView){
        ArrayList<String> test = new ArrayList<String>();
        for (int i=0; i<40; i++){
            test.add(String.valueOf(i+1));
        }
        lineView.setBottomStringList(test);
        ArrayList<Integer> dataList = new ArrayList<Integer>();
        dataList.add(20);
        dataList.add(599);

        lineView.setDataList(dataList);
    }
}