package com.example.tetris;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class stats extends AppCompatActivity {

    private final LineChart[] charts = new LineChart[1];
    private final int[] colors = new int[]{
            Color.rgb(137, 230, 81)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.lastscores));
        setContentView(R.layout.activity_stats);


        charts[0] = findViewById(R.id.chart1);


        for (int i = 0; i < charts.length; i++) {

            LineData data = getData();

            // add some transparency to the color with "& 0x90FFFFFF"
            setupChart(charts[i], data, colors[i % colors.length]);
        }
    }

    private void setupChart(LineChart chart, LineData data, int color) {

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);

        chart.setBackgroundColor(color);


        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.getAxisLeft().setEnabled(true);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisLeft().setTextSize(12f);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setTextColor(Color.BLACK);
        chart.getAxisLeft().setLabelCount(5, true);

        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(false);


        MyMarkerView mv = new MyMarkerView(this, R.layout.marker_view);

        // Set the marker to the chart
        mv.setChartView(chart);
        chart.setMarker(mv);
        // animate calls invalidate()...
        chart.animateX(2500);
    }

    private LineData getData() {

        ArrayList<Entry> values = new ArrayList<>();
        DatabaseHandler handler = new DatabaseHandler(this);
        Long[] scores = handler.getScores();

        int j = 0;
        for (Long score : scores) {
            if (score > 10) {
                j++;
                values.add(new Entry(j, score));
            }
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);

        // create a data object with the data sets
        return new LineData(set1);
    }




}
