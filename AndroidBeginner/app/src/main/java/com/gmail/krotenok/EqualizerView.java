package com.gmail.krotenok;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EqualizerView extends View {
    private static final int COUNT_COLUMN = 5;
    private static final int COUNT_DISTANCE_COLUMN = COUNT_COLUMN * 2 + 1;
    private static final int MARGIN_GENERAL_BORDER = 10;
    private static final int MARGIN_COLUMNS_IN_BORDER = 50;
    private static final int GENERAL_BORDER_MARGIN = 10;
    private static final int STROKE_WIDTH_GENERAL_BORDER = 10;
    private static final int STROKE_WIDTH_COLUMN_BORDER = 5;
    private static final int START_VALUE = 50;
    private Paint generalBorderPaint = new Paint();
    private Paint columnBorderPaint = new Paint();
    private Paint columnPaint = new Paint();
    private Rect generalBorder = new Rect();
    private List<Rect> borderColumnList = new ArrayList<>(COUNT_COLUMN);
    private int[] percentColumnDataList = new int[COUNT_COLUMN];
    private OnEqualizerListener onEqualizerListener;

    public EqualizerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVerbal();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGeneralBorder(canvas);
        drawColumns(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        changeColumnListData(x, y);
        invalidate();
        return true;
    }

    public void setOnEqualizerListener(OnEqualizerListener onEqualizerListener) {
        this.onEqualizerListener = onEqualizerListener;
    }

    public int[] getPercentColumnDataList() {
        return percentColumnDataList;
    }

    private void changeColumnListData(int x, int y) {
        for (int i = 0; i < COUNT_COLUMN; i++) {
            if (borderColumnList.get(i).contains(x, y)) {
                int topY = borderColumnList.get(i).top;
                int bottomY = borderColumnList.get(i).bottom;
                int fullLengthColumn = bottomY - topY;
                float dataLengthColumn = bottomY - y;
                float percentDataColumn = (dataLengthColumn / fullLengthColumn) * 100f;
                percentColumnDataList[i] = Math.round(percentDataColumn);
                onEqualizerListener.OnEqualizerDataChanged(percentColumnDataList);
            }
        }
    }

    private void drawColumns(Canvas canvas) {
        for (int i = 0; i < COUNT_COLUMN; i++) {
            canvas.drawRect(borderColumnList.get(i), columnBorderPaint);
            Rect columnData = new Rect();
            columnData.set(borderColumnList.get(i));
            int topY = borderColumnList.get(i).top;
            int bottomY = borderColumnList.get(i).bottom;
            float fullLengthColumn = bottomY - topY;
            int topYDataColumn = Math.round(fullLengthColumn * percentColumnDataList[i] / 100f);
            columnData.top = bottomY - topYDataColumn;
            canvas.drawRect(columnData, columnPaint);
        }
    }

    private void drawGeneralBorder(Canvas canvas) {
        canvas.drawRect(generalBorder, generalBorderPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        generalBorder.set(MARGIN_GENERAL_BORDER, MARGIN_GENERAL_BORDER,
                w - MARGIN_GENERAL_BORDER, h - MARGIN_GENERAL_BORDER);
        calculationSizeColumns(generalBorder);
    }

    private void calculationSizeColumns(Rect generalBorder) {
        int step = Math.round(generalBorder.width() / COUNT_DISTANCE_COLUMN);
        int startX = generalBorder.left + step;
        int startY = generalBorder.top + MARGIN_COLUMNS_IN_BORDER;
        int finishY = generalBorder.bottom - MARGIN_COLUMNS_IN_BORDER;
        borderColumnList.clear();
        for (int i = 0; i < COUNT_COLUMN; i++) {
            Rect column = new Rect();
            column.set(startX, startY, startX + step, finishY);
            borderColumnList.add(column);
            startX = startX + (step * 2);
        }
    }

    private void initVerbal() {
        initPaints();
        for (int i = 0; i < percentColumnDataList.length; i++) {
            percentColumnDataList[i] = START_VALUE;
        }
        generalBorder.set(GENERAL_BORDER_MARGIN, GENERAL_BORDER_MARGIN,
                getHeight() - GENERAL_BORDER_MARGIN, getWidth() - GENERAL_BORDER_MARGIN);
    }

    private void initPaints() {
        generalBorderPaint.setAntiAlias(true);
        generalBorderPaint.setColor(getResources().getColor(R.color.equalizer_view_blue));
        generalBorderPaint.setStyle(Paint.Style.STROKE);
        generalBorderPaint.setStrokeWidth(STROKE_WIDTH_GENERAL_BORDER);
        columnBorderPaint.setAntiAlias(true);
        columnBorderPaint.setColor(getResources().getColor(R.color.equalizer_view_border_column));
        columnBorderPaint.setStyle(Paint.Style.STROKE);
        columnBorderPaint.setStrokeWidth(STROKE_WIDTH_COLUMN_BORDER);
        columnPaint.setAntiAlias(true);
        columnPaint.setColor(getResources().getColor(R.color.equalizer_view_blue));
        columnPaint.setStyle(Paint.Style.FILL);
    }
}