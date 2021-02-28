package com.gmail.artemkrot.jokeabout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    public static final long SEC_30 = 30L;
    public static final long MIN_1 = 60L;
    public static final long HOUR_1 = 3600L;
    public static final long DEFAULT_TIMER = 100L;

    private EditText editTextFistName;
    private EditText editTextLastName;
    private RadioGroup radioGroup;
    private PreferencesRepository preferencesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVerbals();
    }

    private void initVerbals() {
        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonHandler();
            }
        });
        editTextFistName = findViewById(R.id.edit_text_fist_name);
        editTextLastName = findViewById(R.id.edit_text_last_name);
        radioGroup = findViewById(R.id.radioGroup);
        preferencesRepository = new PreferencesRepository(this);
    }

    private void setButtonHandler() {
        preferencesRepository.saveFistNameValue(editTextFistName.getText().toString());
        preferencesRepository.saveLastNameValue(editTextLastName.getText().toString());
        preferencesRepository.saveTimerValue(getTimerValueFromRadioGroup());
        startWorkManager(getTimerValueFromRadioGroup());
    }

    private void startWorkManager(long timer) {
        PeriodicWorkRequest myWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, timer, TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance().enqueue(myWorkRequest);
    }

    private long getTimerValueFromRadioGroup() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButton1: {
                return SEC_30;
            }
            case R.id.radioButton2: {
                return MIN_1;
            }
            case R.id.radioButton3: {
                return HOUR_1;
            }
            default: {
                return DEFAULT_TIMER;
            }
        }
    }
}