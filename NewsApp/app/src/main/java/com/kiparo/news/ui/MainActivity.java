package com.kiparo.news.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kiparo.news.R;
import com.kiparo.news.net.NewsEntity;
import com.kiparo.news.net.Constant;
import com.kiparo.news.net.NewsLoadListener;
import com.kiparo.news.net.NewsLoader;

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
    protected void onStop() {
        super.onStop();
        newsLoader.finish();
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void loadNews() {
        newsLoader = new NewsLoader(Constant.URL_DATA_REQUEST, this);
        newsLoader.loadNews();
    }
}