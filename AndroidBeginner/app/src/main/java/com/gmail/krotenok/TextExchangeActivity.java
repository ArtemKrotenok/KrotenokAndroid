package com.gmail.krotenok;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TextExchangeActivity extends Activity {
    private Button button;
    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_exchange);
        initClickListener();
    }

    private void initClickListener() {
        button = (Button) findViewById(R.id.button1);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence contentTextView1 = textView1.getText();
                CharSequence contentTextView2 = textView2.getText();
                Drawable backgroundTextView1 = textView1.getBackground();
                Drawable backgroundTextView2 = textView2.getBackground();
                textView1.setBackground(backgroundTextView2);
                textView2.setBackground(backgroundTextView1);
                textView1.setText(contentTextView2);
                textView2.setText(contentTextView1);
            }
        };
        textView1.setOnClickListener(onClickListener);
        textView2.setOnClickListener(onClickListener);
        button.setOnClickListener(onClickListener);
    }
}