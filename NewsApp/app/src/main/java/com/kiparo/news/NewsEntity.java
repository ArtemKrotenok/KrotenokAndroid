package com.kiparo.news;

import java.util.List;

public class NewsEntity {

    private String title;
    private String summary;
    private String storyURL;
    private String byline;
    private String publishedDate;
    private List<MediaEntity> mediaEntityList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStoryURL() {
        return storyURL;
    }

    public void setStoryURL(String storyURL) {
        this.storyURL = storyURL;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<MediaEntity> getMediaEntityList() {
        return mediaEntityList;
    }

    public void setMediaEntityList(List<MediaEntity> mediaEntityList) {
        this.mediaEntityList = mediaEntityList;
    }
}