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
import com.kiparo.news.servise.NewsLoadListener;
import com.kiparo.news.servise.NewsLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsLoadListener {

    private static final String URL_DATA_REQUEST = "https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=Sijmje9kSWcErLObvGcoazyI77TGe0ss";

    private List<NewsEntity> newsItemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private NewsLoader newsLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVerbals();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishLoad() {
        adapter.update(newsLoader.getNews());
        adapter.notifyDataSetChanged();
    }

    private void initVerbals() {
        Fresco.initialize(this);
        recyclerView = findViewById(R.id.list);
        adapter = new NewsListAdapter(newsItemList, new NewsListAdapter.OnClickListener() {
            @Override
            public void onItemClick(NewsEntity newsEntity) {
                DetailViewActivity.start(MainActivity.this, newsEntity);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
        newsLoader = new NewsLoader(URL_DATA_REQUEST, this);
        newsLoader.loadNews();
    }

}