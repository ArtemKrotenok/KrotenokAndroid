package com.gmail.krotenok;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class MainMenuActivity extends Activity {
    private Button buttonSelectTextExchangeActivity;
    private Button buttonSelectFlagsActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initListenerButtonSelectTextExchangeActivity();
        initListenerButtonSelectFlagsActivity();
    }

    private void initListenerButtonSelectTextExchangeActivity() {
        buttonSelectTextExchangeActivity = (Button) findViewById(R.id.button_menu_text_exchange_activity);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, TextExchangeActivity.class);
                startActivity(intent);
            }
        };
        buttonSelectTextExchangeActivity.setOnClickListener(onClickListener);
    }

    private void initListenerButtonSelectFlagsActivity() {
        buttonSelectFlagsActivity = (Button) findViewById(R.id.button_menu_flags_activity);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, FlagsActivity.class);
                startActivity(intent);
            }
        };
        buttonSelectFlagsActivity.setOnClickListener(onClickListener);
    }
}