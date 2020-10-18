package com.gmail.krotenok;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PieChartActivity extends Activity {
    private Button button;
    private EditText editText;
    private TextView textView;
    private PieChartView pieChartView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        initVerbal();
        initListenerButton();
        initListenerEdit();
    }

    private void initVerbal() {
        button = findViewById(R.id.buttonShowPieChart);
        editText = findViewById(R.id.editTextPieChart);
        textView = findViewById(R.id.viewTextPieChart);
        textView.setVisibility(View.GONE);
        pieChartView = findViewById(R.id.viewPieChart);
    }

    private void initListenerEdit() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().equals("")) {
                    button.setEnabled(false);
                    textView.setVisibility(View.VISIBLE);
                    pieChartView.setVisibility(View.GONE);
                } else {
                    button.setEnabled(true);
                    textView.setVisibility(View.GONE);
                    pieChartView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initListenerButton() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHandler();
            }
        };
        button.setOnClickListener(onClickListener);
    }

    private void onClickHandler() {
        String inputData = editText.getText().toString();
        List<Integer> inputDataList = getInputDataFromText(inputData);
        if (inputData.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
            pieChartView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.GONE);
            pieChartView.setVisibility(View.VISIBLE);
            pieChartView.setData(inputDataList);
        }
    }

    private List<Integer> getInputDataFromText(String inputData) {
        String[] dataTextList = inputData.split("[\\D]+");
        if (dataTextList.length == 1 && dataTextList[0].equals("")) {
            return Collections.emptyList();
        }
        List<Integer> resultList = new ArrayList<>();
        for (String data : dataTextList) {
            if (!data.equals("")) {
                resultList.add(Integer.parseInt(data));
            }
        }
        return resultList;
    }
}