package com.dacer.androidchartsexample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dacer.androidcharts.BarView;
import com.dacer.androidcharts.LineView;

import java.util.ArrayList;

/**
 * Created by Dacer on 11/15/13.
 */
public class BarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bar, container, false);
        BarView barView = (BarView)rootView.findViewById(R.id.bar_view);
        ArrayList<Integer> barDataList = new ArrayList<Integer>();
        for(int i=0; i<7; i++){
            barDataList.add((int)(Math.random() * 100));
        }
        barView.setDataList(barDataList);
        return rootView;
    }
}