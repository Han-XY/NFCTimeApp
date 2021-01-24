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

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
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
//        mViewModel = new ViewModelProvider(this).get(GraphViewModel.class);
//        // TODO: Use the ViewModel

        RadarChart radarChart = getActivity().findViewById(R.id.radarChart);
        System.out.println(getActivity());
        System.out.println(radarChart);

        ArrayList<RadarEntry> userTraits = new ArrayList<>();

        userTraits.add(new RadarEntry(4.4f));
        userTraits.add(new RadarEntry(5f));
        userTraits.add(new RadarEntry(4.3f));
        userTraits.add(new RadarEntry(4f));
        userTraits.add(new RadarEntry(3.8f));

        RadarDataSet radarDataUserTraits = new RadarDataSet(userTraits, "User");
        radarDataUserTraits.setColor(Color.RED);
        radarDataUserTraits.setLineWidth(2f);
        radarDataUserTraits.setValueTextColor(Color.RED);
        radarDataUserTraits.setValueTextSize(14f);

        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataUserTraits);

        String[] labels = {"Power", "Consistency", "Diversity", "Diversity", "Tenacity", "Balance"};

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        System.out.println(radarData);
        radarChart.setData(radarData);

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