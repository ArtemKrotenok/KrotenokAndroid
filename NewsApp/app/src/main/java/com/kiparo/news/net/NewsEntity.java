package com.kiparo.news.net;

public class NewsEntity {

    private String title;
    private String summary;
    private String storyURL;
    private String smallPictureURL;
    private String bigPictureURL;

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

    public String getSmallPictureURL() {
        return smallPictureURL;
    }

    public void setSmallPictureURL(String smallPictureURL) {
        this.smallPictureURL = smallPictureURL;
    }

    public String getBigPictureURL() {
        return bigPictureURL;
    }

    public void setBigPictureURL(String bigPictureURL) {
        this.bigPictureURL = bigPictureURL;
    }
}