package com.kdy.diary.vo;

import java.io.File;

public class DiaryVO {
    String title, content, stickerId;
    String date;
    File image;


    public DiaryVO() {
    }

    public DiaryVO(String title, String content, String stickerId, String date, File image) {
        this.title = title;
        this.content = content;
        this.stickerId = stickerId;
        this.date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
