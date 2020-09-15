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

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ImageActivity extends Activity {
    private Button buttonLoadImage;
    private ImageView imageView;
    private EditText editText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initListenerButton();
    }

    private void initListenerButton() {
        buttonLoadImage = (Button) findViewById(R.id.button_load_image);
        imageView = (ImageView) findViewById(R.id.imageViewLoader);
        editText = (EditText) findViewById(R.id.editTextUrlImage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        final String imageUrl = editText.getText().toString();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(imageUrl)
                        .transform(new CropCircleTransformation())
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
        };
        buttonLoadImage.setOnClickListener(onClickListener);
    }
}