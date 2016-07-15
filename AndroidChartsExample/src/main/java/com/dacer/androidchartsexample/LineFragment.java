package com.dacer.androidchartsexample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import im.dacer.androidcharts.LineView;

import java.util.ArrayList;

/**
 * Created by Dacer on 11/15/13.
 */
public class LineFragment extends Fragment {
    int randomint = 9;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line, container, false);
        final LineView lineView = (LineView)rootView.findViewById(R.id.line_view);
        
        //must*
        ArrayList<String> test = new ArrayList<String>();
        for (int i=0; i<randomint; i++){
            test.add(String.valueOf(i+1));
        }
        lineView.setBottomTextList(test);
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);

        Button lineButton = (Button)rootView.findViewById(R.id.line_button);
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomSet(lineView);

            }
        });

        randomSet(lineView);
        return rootView;
    }

    private void randomSet(LineView lineView){
        ArrayList<Integer> dataList = new ArrayList<Integer>();
        int random = (int)(Math.random()*9+1);
        for (int i=0; i<randomint; i++){
            dataList.add((int)(Math.random()*random));
        }
        
        ArrayList<Integer> dataList2 = new ArrayList<Integer>();
        random = (int)(Math.random()*9+1);
        for (int i=0; i<randomint; i++){
			dataList2.add((int)(Math.random()*random));
        }

        ArrayList<Integer> dataList3 = new ArrayList<Integer>();
        random = (int)(Math.random()*9+1);
        for (int i=0; i<randomint; i++){
            dataList3.add((int)(Math.random()*random));
        }

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<ArrayList<Integer>>();
        dataLists.add(dataList);
        dataLists.add(dataList2);
//        dataLists.add(dataList3);
        
        lineView.setDataList(dataLists);
    }
}