package com.gmail.krotenok;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.gmail.krotenok.util.CircularTransformation;
import com.squareup.picasso.Picasso;

public class ImageActivity extends Activity {
    private Button buttonLoadImage;
    private ImageView imageView;
    private EditText editText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initVerbal();
        initListenerButton();
    }

    private void initVerbal() {
        buttonLoadImage = (Button) findViewById(R.id.button_load_image);
        imageView = (ImageView) findViewById(R.id.imageViewLoader);
        editText = (EditText) findViewById(R.id.editTextUrlImage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    private void initListenerButton() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHandler();
            }
        };
        buttonLoadImage.setOnClickListener(onClickListener);
    }

    private void onClickHandler() {
        String imageUrl = editText.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(imageUrl)
                .transform(new CircularTransformation())
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("ERROR", e.getMessage());
                    }
                });
    }
}