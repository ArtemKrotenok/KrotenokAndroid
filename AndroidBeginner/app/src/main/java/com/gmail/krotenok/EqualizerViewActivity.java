package com.gmail.krotenok;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class EqualizerViewActivity extends Activity {
    private TextView textView;
    private EqualizerView equalizerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equalizer_view);
        equalizerView = (EqualizerView) findViewById(R.id.equalizer_view);
        textView = (TextView) findViewById(R.id.equalizer_text_view);
        textView.setText(getFormatResult(equalizerView.getPercentColumnDataList()));
        equalizerView.setOnEqualizerListener(new OnEqualizerListener() {
            @Override
            public void OnEqualizerDataChanged(int[] values) {
                textView.setText(getFormatResult(values));
            }
        });
    }

    private String getFormatResult(int[] values) {
        StringBuffer resultText = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            if ((i + 1) != values.length) {
                resultText.append(values[i] + ", ");
            } else {
                resultText.append(values[i]);
            }
        }
        return resultText.toString();
    }
}