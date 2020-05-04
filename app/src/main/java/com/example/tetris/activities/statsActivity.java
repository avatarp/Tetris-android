package com.example.tetris.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tetris.DatabaseHandler;
import com.example.tetris.MyMarkerView;
import com.example.tetris.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


public class statsActivity extends AppCompatActivity {

    private final int color = Color.rgb(76, 200, 80);
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.lastScores));

        setContentView(R.layout.activity_stats);
        final Context context = this;

        chart = findViewById(R.id.chart1);
        LineData data = getData();


        setupChart(chart, data, color);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                TextView score = findViewById(R.id.statsScore);
                TextView date = findViewById(R.id.statsDateTime);
                TextView blocks = findViewById(R.id.statsTotalBlocks);
                TextView lines = findViewById(R.id.statsLines);
                DatabaseHandler db = new DatabaseHandler(context);
                TextView ml = findViewById(R.id.movesLeftText);
                TextView mr = findViewById(R.id.movesRightText);
                TextView tl = findViewById(R.id.rotateLeftText);
                TextView tr = findViewById(R.id.rotateRightText);

                String[] stats = db.getStatsByIndex((int) (chart.getData().getEntryCount() - h.getX()));

                ml.setText(stats[2]);
                mr.setText(stats[3]);
                tl.setText(stats[4]);
                tr.setText(stats[5]);
                score.setText(stats[0]);
                date.setText(stats[7]);
                blocks.setText(stats[1]);
                lines.setText(stats[6]);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        Highlight highlight = new Highlight(0, 0, 0);
        chart.highlightValue(highlight, true); //call onValueSelected()
    }

    private void setupChart(LineChart chart, LineData data, int color) {

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setBackgroundColor(color);
        chart.setExtraOffsets(0, 0, 0, 8);

        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);
        chart.getAxisLeft().setEnabled(true);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisLeft().setTextSize(16f);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setTextColor(Color.BLACK);
        chart.getAxisLeft().setLabelCount(5, true);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.marker_view);

        // Set the marker to the chart
        mv.setChartView(chart);
        chart.setMarker(mv);
        chart.animateX(data.getEntryCount() * 200);
    }

    private LineData getData() {

        ArrayList<Entry> values = new ArrayList<>();
        DatabaseHandler handler = new DatabaseHandler(this);
        Long[] scores = handler.getScores();

        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > 10) {
                values.add(new Entry(i, scores[i]));
            }


        }
        LineDataSet set1 = new LineDataSet(values, "Your Scores");

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
