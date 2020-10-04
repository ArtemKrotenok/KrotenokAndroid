package com.gmail.krotenok;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.gmail.krotenok.util.CircularBorderTransformation;
import com.gmail.krotenok.util.CircularTransformation;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initAvatarUser();
    }

    private void initAvatarUser() {
        imageView = (ImageView) findViewById(R.id.imageViewUserAvatar);
        String imageUrl = getString(R.string.url_image);
        Picasso.get()
                .load(imageUrl)
                .transform(new CircularBorderTransformation())
                .into(imageView);
    }
}