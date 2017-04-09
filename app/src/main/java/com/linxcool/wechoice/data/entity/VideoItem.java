package com.linxcool.wechoice.data.entity;

import java.io.Serializable;

/**
 * Created by linxcool on 17/4/8.
 */

public class VideoItem {

    private String topicSid;
    private String replyid;
    private String vid;

    private String title;
    private String sectiontitle;

    private String topicName;
    private String topicImg;
    private Topic videoTopic;

    private String videosource;
    private String topicDesc;
    private String cover;
    private int playCount;
    private String replyBoard;

    private String description;
    private int length;
    private int playersize;

    private String ptime;

    private String mp4_url;
    private String mp4Hd_url;
    private String m3u8Hd_url;
    private String m3u8_url;

    class Topic implements Serializable {
        private String alias;
        private String tname;
        private String ename;
        private String tid;
        private String topic_icons;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public String getCover() {
        return cover;
    }

    public String getTopicImg() {
        return topicImg;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getPtime() {
        return ptime;
    }
}
