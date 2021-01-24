package com.kiparo.news.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kiparo.news.R;
import com.kiparo.news.repository.NewsEntity;
import com.kiparo.news.servise.Constant;
import com.kiparo.news.servise.NewsLoadListener;
import com.kiparo.news.servise.NewsLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsLoadListener {

    private final List<NewsEntity> newsItemList = new ArrayList<>();

    private NewsListAdapter adapter;
    private NewsLoader newsLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVerbals();
        loadNews();
    }

    @Override
    public void onFinishLoad() {
        adapter.update(newsLoader.getNews());
    }

    private void initVerbals() {
        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new NewsListAdapter(newsItemList, new NewsListAdapter.OnClickListener() {
            @Override
            public void onItemClick(NewsEntity newsEntity) {
                DetailViewActivity.start(MainActivity.this, newsEntity);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void loadNews() {
        newsLoader = new NewsLoader(Constant.URL_DATA_REQUEST, this);
        newsLoader.loadNews();
    }
}