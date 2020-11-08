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
    private Button buttonSelectImageActivity;
    private Button buttonSelectLoginActivity;
    private Button buttonSelectProfileActivity;
    private Button buttonSelectWatchViewActivity;
    private Button buttonSelectPieChartActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initListenerButtonSelectActivity();
    }

    private void initListenerButtonSelectActivity() {
        buttonSelectTextExchangeActivity = (Button) findViewById(R.id.button_menu_text_exchange_activity);
        buttonSelectFlagsActivity = (Button) findViewById(R.id.button_menu_flags_activity);
        buttonSelectImageActivity = (Button) findViewById(R.id.button_menu_image_activity);
        buttonSelectLoginActivity = (Button) findViewById(R.id.button_menu_login_activity);
        buttonSelectProfileActivity = (Button) findViewById(R.id.button_menu_profile_activity);
        buttonSelectWatchViewActivity = (Button) findViewById(R.id.button_menu_watch_view_activity);
        buttonSelectPieChartActivity = (Button) findViewById(R.id.button_menu_pie_chart_activity);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.button_menu_text_exchange_activity: {
                        intent = new Intent(MainMenuActivity.this, TextExchangeActivity.class);
                        break;
                    }
                    case R.id.button_menu_flags_activity: {
                        intent = new Intent(MainMenuActivity.this, FlagsActivity.class);
                        break;
                    }
                    case R.id.button_menu_login_activity: {
                        intent = new Intent(MainMenuActivity.this, LoginLinearActivity.class);
                        break;
                    }
                    case R.id.button_menu_image_activity: {
                        intent = new Intent(MainMenuActivity.this, ImageActivity.class);
                        break;
                    }
                    case R.id.button_menu_profile_activity: {
                        intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
                        break;
                    }
                    case R.id.button_menu_watch_view_activity: {
                        intent = new Intent(MainMenuActivity.this, WatchViewActivity.class);
                        break;
                    }
                    case R.id.button_menu_pie_chart_activity: {
                        intent = new Intent(MainMenuActivity.this, PieChartActivity.class);
                        break;
                    }
                    default:
                        intent = null;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        };
        buttonSelectTextExchangeActivity.setOnClickListener(onClickListener);
        buttonSelectFlagsActivity.setOnClickListener(onClickListener);
        buttonSelectImageActivity.setOnClickListener(onClickListener);
        buttonSelectLoginActivity.setOnClickListener(onClickListener);
        buttonSelectProfileActivity.setOnClickListener(onClickListener);
        buttonSelectWatchViewActivity.setOnClickListener(onClickListener);
        buttonSelectPieChartActivity.setOnClickListener(onClickListener);
    }
}