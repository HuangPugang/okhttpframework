package com.paul.okhttpframework;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paul on 16/1/31.
 */
public class News {

    @SerializedName("title")
    private String title;//资讯标题
    @SerializedName("img")
    private String img;//图片
    @SerializedName("description")
    private String description;//描述
    @SerializedName("keywords")
    private String keywords;//关键字
    @SerializedName("message")
    private String message;//资讯内容
    @SerializedName("rcount")
    private int rcount;//评论读数

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }
}
