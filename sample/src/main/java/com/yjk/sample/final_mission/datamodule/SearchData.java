package com.yjk.sample.final_mission.datamodule;

//URL로 부터 가지고 온 데이터를 객체화 시켜서 리사이클러뷰에 넣어주기 위한 클래스입니다.

public class SearchData {
    String videoId;
    String title;
    String imageUrl;
    String channelId;
    boolean isLike = false;

    public SearchData(String videoId, String title, String ImageUrl,String ChannelId) {
        this.videoId = videoId;
        this.title = title;
        this.imageUrl = ImageUrl;
        this.channelId = ChannelId;
    }
    public SearchData(){}

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String ChannelId){
        this.channelId = ChannelId;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
