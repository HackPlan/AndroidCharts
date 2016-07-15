package com.dacer.androidchartsexample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import im.dacer.androidcharts.BarView;
import im.dacer.androidcharts.LineView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dacer on 11/15/13.
 */
public class BarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bar, container, false);
        final BarView barView = (BarView)rootView.findViewById(R.id.bar_view);
        Button button = (Button)rootView.findViewById(R.id.bar_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomSet(barView);
            }
        });
        randomSet(barView);
        return rootView;
    }

    private void randomSet(BarView barView){
        int random = (int)(Math.random()*20)+6;
        ArrayList<String> test = new ArrayList<String>();
        for (int i=0; i<random; i++){
            test.add("test");
            test.add("pqg");
//            test.add(String.valueOf(i+1));
        }
        barView.setBottomTextList(test);

        ArrayList<Integer> barDataList = new ArrayList<Integer>();
        for(int i=0; i<random*2; i++){
            barDataList.add((int)(Math.random() * 100));
        }
        barView.setDataList(barDataList,100);
    }
}