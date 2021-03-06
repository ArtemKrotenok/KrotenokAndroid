package com.kiparo.news.ui;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.kiparo.news.R;
import com.kiparo.news.net.NewsEntity;
import com.kiparo.news.net.NewsParsingUtil;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListHolder> {

    private final List<NewsEntity> newsItemList;
    private final OnClickListener onClickListener;

    private static final String TAG = NewsListAdapter.class.getSimpleName();

    public NewsListAdapter(List<NewsEntity> newsItemList, OnClickListener onClickListener) {
        this.newsItemList = newsItemList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public NewsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsListHolder(
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(R.layout.list_item_news, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListHolder holder, int position) {

        final NewsEntity newsEntity = newsItemList.get(position);
        String thumbnailURL = newsEntity.getSmallPictureURL();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(newsEntity);
            }
        });
        holder.newsTitle.setText(newsEntity.getTitle());
        try {
            DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                    (Uri.parse(thumbnailURL))).setOldController(holder.imageView.getController()).build();
            holder.imageView.setController(draweeController);
        } catch (NullPointerException e) {
            Log.e(TAG, "Error parse URL image");
        }
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public void update(List<NewsEntity> list) {
        newsItemList.clear();
        newsItemList.addAll(list);
        notifyDataSetChanged();
    }

    static class NewsListHolder extends RecyclerView.ViewHolder {

        TextView newsTitle;
        DraweeView imageView;

        NewsListHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title);
            imageView = itemView.findViewById(R.id.news_item_image);
        }
    }

    interface OnClickListener {
        void onItemClick(NewsEntity newsEntity);
    }
}