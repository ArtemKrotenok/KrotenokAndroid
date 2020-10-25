package com.gmail.krotenok;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PieChartView extends View {
    private static final int FULL_ARC = 360;
    private static final int OUT_TEXT_SIZE = 80;
    private static final int OUT_TEXT_COLOR = Color.WHITE;
    private static final float TEXT_CORRECTION_Y = 0.8f;
    private static final float TEXT_CORRECTION_X = -1f;
    private static final int DESIRED_WIDTH = 950;
    private static final int DESIRED_HEIGHT = 950;
    private List<Integer> inputDataList = new ArrayList<>();
    private List<Integer> resultPercentList = new ArrayList<>();
    private Paint rectPaint = new Paint();
    private Paint textPaint = new Paint();
    private RectF ovalRectF = new RectF();

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVerbal();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!resultPercentList.isEmpty()) {
            drawDataDiagram(canvas, resultPercentList);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ovalRectF.set(0, 0, getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = getHeight();
        int desiredHeight = getWidth();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }
        int resultSize = Math.min(width, height);
        setMeasuredDimension(resultSize, resultSize);
    }

    private void initVerbal() {
        rectPaint.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        textPaint.setColor(OUT_TEXT_COLOR);
        textPaint.setTextSize(OUT_TEXT_SIZE);
        ovalRectF.set(0, 0, getHeight(), getWidth());
    }

    public void setData(List<Integer> inputDataList) {
        {
            if (inputDataList != null) {
                this.inputDataList = inputDataList;
            }
            resultPercentList = getResultPercentList(inputDataList);
            invalidate();
        }
    }

    private void drawDataDiagram(Canvas canvas, List<Integer> resultPercentList) {
        int startAngle = 0;
        int sweepAngle = 0;
        float outTextX;
        float outTextY;
        rectPaint.setColor(0);
        for (int i = 0; i < resultPercentList.size(); i++) {
            Integer percent = resultPercentList.get(i);
            if ((i + 1) == resultPercentList.size()) {
                sweepAngle = FULL_ARC - startAngle;
            } else {
                sweepAngle = percent;
            }
            rectPaint.setColor(getNextColor(rectPaint.getColor()));
            canvas.drawArc(ovalRectF, startAngle, sweepAngle, true, rectPaint);
            outTextX = getOutTextX(startAngle, sweepAngle, ovalRectF);
            outTextY = getOutTextY(startAngle, sweepAngle, ovalRectF);
            canvas.drawText(getPercentText(sweepAngle), outTextX, outTextY, textPaint);
            startAngle = startAngle + sweepAngle;
        }
    }

    private String getPercentText(int sweepAngle) {
        return Math.round(sweepAngle / 3.6) + "%";
    }

    private float getOutTextY(int startAngle, int sweepAngle, RectF oval) {
        double gradesCenterTextOut = Math.toRadians(startAngle + sweepAngle / 2);
        double radius = (oval.height() / 4);
        float textSizeCorrection = OUT_TEXT_SIZE * TEXT_CORRECTION_Y;
        return Math.round(radius * Math.sin(gradesCenterTextOut) + oval.centerY() + textSizeCorrection);
    }

    private float getOutTextX(int startAngle, int sweepAngle, RectF oval) {
        double gradesCenterTextOut = Math.toRadians(startAngle + sweepAngle / 2);
        double radius = (oval.height() / 4);
        float textSizeCorrection = OUT_TEXT_SIZE * TEXT_CORRECTION_X;
        return Math.round(radius * Math.cos(gradesCenterTextOut) + oval.centerX() + textSizeCorrection);
    }

    private int getNextColor(int color) {
        switch (color) {
            case 0: {
                color = Color.RED;
                break;
            }
            case Color.RED: {
                color = Color.BLUE;
                break;
            }
            case Color.BLUE: {
                color = Color.GREEN;
                break;
            }
            case Color.GREEN: {
                color = Color.MAGENTA;
                break;
            }
            case Color.MAGENTA: {
                color = Color.CYAN;
                break;
            }
            case Color.CYAN: {
                color = Color.YELLOW;
                break;
            }
            case Color.YELLOW: {
                color = Color.DKGRAY;
                break;
            }
            case Color.DKGRAY: {
                color = Color.WHITE;
                break;
            }
            case Color.WHITE: {
                color = Color.BLACK;
                break;
            }
            case Color.BLACK: {
                color = Color.WHITE;
                break;
            }
            default:
                color = Color.GRAY;
        }
        return color;
    }

    private List<Integer> getResultPercentList(List<Integer> inputDataList) {
        if (inputDataList == null) {
            return Collections.emptyList();
        }
        if (inputDataList.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> resultList = new ArrayList<>();
        int percent;
        int sumPart = 0;
        for (Integer part : inputDataList) {
            sumPart = sumPart + part;
        }
        for (Integer part : inputDataList) {
            percent = Math.round((part * 360) / sumPart);
            resultList.add(percent);
        }
        return resultList;
    }
}