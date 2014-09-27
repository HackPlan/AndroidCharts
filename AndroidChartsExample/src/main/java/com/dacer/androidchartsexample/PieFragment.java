package com.dacer.androidchartsexample;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dacer.androidcharts.ClockPieHelper;
import com.dacer.androidcharts.ClockPieView;
import com.dacer.androidcharts.PieHelper;
import com.dacer.androidcharts.PieView;
import com.dacer.androidcharts.TempLog;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dacer on 11/16/13.
 */
public class PieFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pie_s, container, false);
        final PieView pieView = (PieView)rootView.findViewById(R.id.pie_view);
        Button button = (Button)rootView.findViewById(R.id.pie_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomSet(pieView);
            }
        });
        set(pieView);
        return rootView;
    }

    private void randomSet(PieView pieView){
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        ArrayList<Integer> intList = new ArrayList<Integer>();
        int totalNum = (int) (5*Math.random()) + 5;

        int totalInt = 0;
        for(int i=0; i<totalNum; i++){
            int ranInt = (int)(Math.random()*10)+1;
            intList.add(ranInt);
            totalInt += ranInt;
        }
        for(int i=0; i<totalNum; i++){
            pieHelperArrayList.add(new PieHelper(100f*intList.get(i)/totalInt));
        }

        pieView.setSelectedIndex(PieView.NO_SELECTED_INDEX);
        pieView.showPercentLabel(true);
        pieView.setDate(pieHelperArrayList);
    }

    private void set(PieView pieView){
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        pieHelperArrayList.add(new PieHelper(20, Color.BLACK));
        pieHelperArrayList.add(new PieHelper(6));
        pieHelperArrayList.add(new PieHelper(30));
        pieHelperArrayList.add(new PieHelper(12));
        pieHelperArrayList.add(new PieHelper(32));

        pieView.setDate(pieHelperArrayList);
        pieView.setSelectedIndex(2);
    }
}