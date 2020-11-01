package com.gmail.krotenok;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class WatchView extends View {
    private static final float SIZE_DOT_CENTER_WATCH = 0.04f;
    private static final int OUT_TEXT_SIZE = 100;
    private static final int OUT_TEXT_COLOR = Color.RED;
    private static final int RECT_COLOR = Color.BLACK;
    private static final int SECOND_HAND_COLOR = Color.GRAY;
    private static final int MINUTE_HAND_COLOR = Color.BLACK;
    private static final int HOUR_HAND_COLOR = Color.BLACK;
    private static final int STROKE_WIDTH_WATCH = 20;
    private static final int STROKE_WIDTH_SECOND_HAND = 10;
    private static final int STROKE_WIDTH_MINUTE_HAND = 20;
    private static final int STROKE_WIDTH_HOUR_HAND = 30;
    private static final float SIZE_HOUR_HAND = 0.60f;
    private static final float SIZE_MINUTE_HAND = 0.80f;
    private static final float SIZE_SECOND_HAND = 0.95f;
    private static final int ONE_SECOND = 1000;
    private static final float COF_FOR_HOUR_TEXT = 0.3f;
    private static final float COF_FOR_LINE_END = 0.9f;
    private static final float COF_FOR_LINE_START_TEN_MINUTE = 0.7f;
    private static final float COF_FOR_LINE_START_HOUR = 0.85f;
    private static final int STROKE_WIDTH_LINE_FACE_WATCH = 5;
    private Paint watchPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint secondHandPaint = new Paint();
    private Paint minuteHandPaint = new Paint();
    private Paint hourHandPaint = new Paint();
    private RectF ovalRectF = new RectF();
    private Calendar timeNow = Calendar.getInstance();
    private Timer timer = new Timer();
    private MyTimerTask timerTask = new MyTimerTask();
    private float centerX;
    private float centerY;
    private float radiusWatch;

    public WatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVerbal();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(270, centerX, centerY);
        drawWatch(canvas);
        drawMinuteHand(canvas);
        drawHourHand(canvas);
        drawSecondHand(canvas);
        drawCenterWatch(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ovalRectF.set(0, 0, w, h);
        centerX = w / 2;
        centerY = h / 2;
        radiusWatch = Math.min(centerX, centerY) - STROKE_WIDTH_WATCH;
    }

    private void initVerbal() {
        initSecondHandPaint();
        initMinuteHandPaint();
        initHourHandPaint();
        initTextPaint();
        initTextPaint();
        initWatchPaint();
        ovalRectF.set(0, 0, getHeight(), getWidth());
        timeNow.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
        timer.schedule(timerTask, 0, ONE_SECOND);
    }

    private void initWatchPaint() {
        watchPaint.setAntiAlias(true);
        watchPaint.setColor(RECT_COLOR);
    }

    private void initTextPaint() {
        textPaint.setAntiAlias(true);
        textPaint.setColor(OUT_TEXT_COLOR);
        textPaint.setTextSize(OUT_TEXT_SIZE);
    }

    private void initHourHandPaint() {
        hourHandPaint.setAntiAlias(true);
        hourHandPaint.setColor(HOUR_HAND_COLOR);
        hourHandPaint.setStyle(Paint.Style.FILL);
        hourHandPaint.setStrokeWidth(STROKE_WIDTH_HOUR_HAND);
    }

    private void initMinuteHandPaint() {
        minuteHandPaint.setAntiAlias(true);
        minuteHandPaint.setColor(MINUTE_HAND_COLOR);
        minuteHandPaint.setStyle(Paint.Style.FILL);
        minuteHandPaint.setStrokeWidth(STROKE_WIDTH_MINUTE_HAND);
    }

    private void initSecondHandPaint() {
        secondHandPaint.setAntiAlias(true);
        secondHandPaint.setColor(SECOND_HAND_COLOR);
        secondHandPaint.setStyle(Paint.Style.FILL);
        secondHandPaint.setStrokeWidth(STROKE_WIDTH_SECOND_HAND);
    }

    private void drawCenterWatch(Canvas canvas) {
        watchPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radiusWatch * SIZE_DOT_CENTER_WATCH, watchPaint);
    }

    private void drawWatch(Canvas canvas) {
        drawBorderWatch(canvas);
        drawFaceWatch(canvas);
    }

    private void drawFaceWatch(Canvas canvas) {
        watchPaint.setStyle(Paint.Style.STROKE);
        watchPaint.setStrokeWidth(STROKE_WIDTH_LINE_FACE_WATCH);
        int gradesMinute = 1;
        int step = 6;
        while (gradesMinute <= 60) {
            canvas.rotate(step, centerX, centerY);
            if (gradesMinute % 15 == 0) {
                float textOutX = centerX + (radiusWatch - textPaint.getTextSize());
                float textOutY = centerY - (textPaint.getTextSize() * COF_FOR_HOUR_TEXT);
                if (gradesMinute == 60) {
                    textOutY = textOutY - (textPaint.getTextSize() * COF_FOR_HOUR_TEXT);
                }
                canvas.rotate(90, textOutX, textOutY);
                canvas.drawText(String.valueOf(gradesMinute / 5), textOutX, textOutY, textPaint);
                canvas.rotate(-90, textOutX, textOutY);
            } else if (gradesMinute % 5 == 0) {
                canvas.drawLine(centerX + (radiusWatch * COF_FOR_LINE_START_TEN_MINUTE),
                        centerY, centerX + radiusWatch * COF_FOR_LINE_END, centerY, watchPaint);
            } else {
                canvas.drawLine(centerX + (radiusWatch * COF_FOR_LINE_START_HOUR),
                        centerY, centerX + radiusWatch * COF_FOR_LINE_END, centerY, watchPaint);
            }
            gradesMinute++;
        }
    }

    private void drawBorderWatch(Canvas canvas) {
        watchPaint.setStyle(Paint.Style.STROKE);
        watchPaint.setStrokeWidth(STROKE_WIDTH_WATCH);
        canvas.drawCircle(centerX, centerY, radiusWatch, watchPaint);
    }

    private void drawHourHand(Canvas canvas) {
        int hours = timeNow.get(Calendar.HOUR);
        int gradesByHours = Math.round((hours) * 30);
        canvas.drawLine(centerX, centerY, getXByGrades(gradesByHours, radiusWatch * SIZE_HOUR_HAND),
                getYByGrades(gradesByHours, radiusWatch * SIZE_HOUR_HAND), hourHandPaint);
    }

    private void drawMinuteHand(Canvas canvas) {
        int minutes = timeNow.get(Calendar.MINUTE);
        int gradesByMinutes = (minutes) * 6;
        canvas.drawLine(centerX, centerY, getXByGrades(gradesByMinutes, radiusWatch * SIZE_MINUTE_HAND),
                getYByGrades(gradesByMinutes, radiusWatch * SIZE_MINUTE_HAND), minuteHandPaint);
    }

    private void drawSecondHand(Canvas canvas) {
        int seconds = timeNow.get(Calendar.SECOND);
        int gradesBySeconds = (seconds) * 6;
        canvas.drawLine(centerX, centerY, getXByGrades(gradesBySeconds, radiusWatch * SIZE_SECOND_HAND),
                getYByGrades(gradesBySeconds, radiusWatch * SIZE_SECOND_HAND), secondHandPaint);
    }

    private float getYByGrades(int grades, float radius) {
        return Math.round(radius * Math.sin(Math.toRadians(grades)) + centerY);
    }

    private float getXByGrades(int grades, float radius) {
        return Math.round(radius * Math.cos(Math.toRadians(grades)) + centerX);
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            timeNow = Calendar.getInstance();
            invalidate();
        }
    }
}