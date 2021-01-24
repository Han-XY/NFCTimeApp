package com.txbb.nfctimeapp.frontend.graphs;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.backend.Actor;
import com.txbb.nfctimeapp.frontend.AppState;

import java.util.ArrayList;
import java.util.Map;

public class GraphFragment extends Fragment implements Actor {

    private GraphViewModel mViewModel;

    public static GraphFragment newInstance() {
        return new GraphFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
////        mViewModel = new ViewModelProvider(this).get(GraphViewModel.class);
////        // TODO: Use the ViewModel
//

        RadarChart radarChart = getActivity().findViewById(R.id.radarChart);

        ArrayList<RadarEntry> userTraitsThisWeek = new ArrayList<>();

        userTraitsThisWeek.add(new RadarEntry(4.4f));
        userTraitsThisWeek.add(new RadarEntry(5f));
        userTraitsThisWeek.add(new RadarEntry(4.3f));
        userTraitsThisWeek.add(new RadarEntry(4f));
        userTraitsThisWeek.add(new RadarEntry(3.8f));

        RadarDataSet radarDataUserTraits = new RadarDataSet(userTraitsThisWeek, "Last Week");
        radarDataUserTraits.setColor(Color.rgb(244,67,54));
        radarDataUserTraits.setLineWidth(1.5f);
        radarDataUserTraits.setValueTextSize(0f);

        ArrayList<RadarEntry> userTraitsLastWeek = new ArrayList<>();
        userTraitsLastWeek.add(new RadarEntry(5.0f));
        userTraitsLastWeek.add(new RadarEntry(4.6f));
        userTraitsLastWeek.add(new RadarEntry(4.6f));
        userTraitsLastWeek.add(new RadarEntry(4.8f));
        userTraitsLastWeek.add(new RadarEntry(4.5f));

        RadarDataSet radarDataUserTraitsLastWeek = new RadarDataSet(userTraitsLastWeek, "This Week");
        radarDataUserTraitsLastWeek.setColor(Color.rgb(0,150,136));
        radarDataUserTraitsLastWeek.setLineWidth(1.5f);
        radarDataUserTraitsLastWeek.setValueTextSize(0f);


        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataUserTraits);
        radarData.addDataSet(radarDataUserTraitsLastWeek);

        String[] labels = {"Power", "Consistency", "Diversity", "Tenacity", "Balance"};
        radarChart.getDescription().setText("Your Traits");
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        radarChart.setData(radarData);


        BarChart barChart = getActivity().findViewById(R.id.barChart);

        ArrayList<BarEntry>dataVals = new ArrayList<BarEntry>();

        dataVals.add(new BarEntry(0, new float[]{38, 25, 86}));
        dataVals.add(new BarEntry(1, new float[]{42, 15, 76}));
        dataVals.add(new BarEntry(2, new float[]{41, 25, 86}));
        dataVals.add(new BarEntry(3, new float[]{42, 15, 66}));
        dataVals.add(new BarEntry(4, new float[]{30, 15, 96}));
        dataVals.add(new BarEntry(5, new float[]{35, 25, 76}));
        dataVals.add(new BarEntry(6, new float[]{38, 15, 68}));

        String[] xAxisLables = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));

        int[] colorClassArray = new int[]{Color.rgb(94, 119, 140), Color.rgb(128, 151, 166), Color.rgb(204, 212, 217)};

        BarDataSet barDataSet = new BarDataSet(dataVals, "Duration (min)");
        barDataSet.setColors(colorClassArray);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(0f);
        barDataSet.setStackLabels(new String[] {"Gaming", "Sports", "Studying"} );

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Category Duration Today");
        barChart.animateY(1000);
    }


    @Override
    public void onTagRegisterSuccess(String id) {

    }

    @Override
    public void onTagRegisterFailure() {

    }

    @Override
    public void onScanRequest(AppState nextState) {

    }

    @Override
    public void onBadRegister() {

    }

    @Override
    public void sync(Map<String, TagProperties> tags) {

    }

    @Override
    public void onTagStart(String id, long startTime, long durationToday) {

    }

    @Override
    public void onTagStop(String id, long startTime, long durationToday) {

    }
}