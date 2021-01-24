package com.kiparo.news.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.kiparo.news.R;
import com.kiparo.news.net.NewsEntity;

public class DetailViewActivity extends Activity {

    private static final String TAG_NAME_TITLE = "title";
    private static final String TAG_NAME_SUMMARY = "summary";
    private static final String TAG_NAME_IMAGE_URL = "imageURL";
    private static final String TAG_NAME_STORY_URL = "storyURL";

    private String storyURL;

    public static void start(Context context, NewsEntity newsEntity) {
        Intent intent = new Intent(context, DetailViewActivity.class);
        intent.putExtra(TAG_NAME_TITLE, newsEntity.getTitle());
        intent.putExtra(TAG_NAME_SUMMARY, newsEntity.getSummary());
        intent.putExtra(TAG_NAME_IMAGE_URL, newsEntity.getBigPictureURL());
        intent.putExtra(TAG_NAME_STORY_URL, newsEntity.getStoryURL());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        storyURL = extras.getString(TAG_NAME_STORY_URL);
        String title = extras.getString(TAG_NAME_TITLE);
        String summary = extras.getString(TAG_NAME_SUMMARY);
        String imageURL = extras.getString(TAG_NAME_IMAGE_URL);
        TextView titleView = (TextView) findViewById(R.id.title);
        DraweeView imageView = (DraweeView) findViewById(R.id.news_image);
        TextView summaryView = (TextView) findViewById(R.id.summary_content);
        titleView.setText(title);
        summaryView.setText(summary);
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(imageURL)))
                .setOldController(imageView.getController()).build();
        imageView.setController(draweeController);
    }

    public void onFullStoryClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(storyURL));
        startActivity(intent);
    }

}