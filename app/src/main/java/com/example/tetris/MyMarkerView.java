package com.example.tetris;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private MPPointF mOffset;

    public MyMarkerView(Context context, int layoutResource) {

        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(String.valueOf((int) e.getY()));
        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {

        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2.0f), -getHeight() - 20);
        }

        return mOffset;
    }

}