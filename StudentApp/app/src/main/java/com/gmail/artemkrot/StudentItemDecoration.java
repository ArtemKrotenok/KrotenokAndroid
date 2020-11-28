package com.gmail.artemkrot;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class StudentItemDecoration extends RecyclerView.ItemDecoration {
    public static final int STROKE_WIDTH_ITEM_LINE = 10;
    public static final int MARGIN_BUTTON_ITEM = 50;
    public static final int MARGIN_TOP_ITEM = 50;
    private Paint linePaint;

    public StudentItemDecoration() {
        linePaint = new Paint();
        linePaint.setColor(Color.GRAY);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(STROKE_WIDTH_ITEM_LINE);
        linePaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = outRect.bottom + MARGIN_BUTTON_ITEM;
        outRect.top = outRect.top + MARGIN_TOP_ITEM;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            canvas.drawLine(
                    layoutManager.getDecoratedLeft(child),
                    layoutManager.getDecoratedTop(child),
                    layoutManager.getDecoratedRight(child),
                    layoutManager.getDecoratedTop(child),
                    linePaint);
        }
    }
}