package com.txbb.nfctimeapp.visualisation;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.txbb.nfctimeapp.R;

import java.util.ArrayList;

public class RadarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_graph);

        RadarChart radarChart = findViewById(R.id.radarChart);

        ArrayList<RadarEntry> userTraits = new ArrayList<>();

        userTraits.add(new RadarEntry(4.4f));
        userTraits.add(new RadarEntry(5f));
        userTraits.add(new RadarEntry(4.3f));
        userTraits.add(new RadarEntry(4f));
        userTraits.add(new RadarEntry(3.5f));

        RadarDataSet radarDataUserTraits = new RadarDataSet(userTraits, "User");
        radarDataUserTraits.setColor(Color.RED);
        radarDataUserTraits.setLineWidth(4f);
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

}
