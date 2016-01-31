package com.paul.okhttpframework;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Paul on 16/1/31.
 */
public class RequestResult {
    @SerializedName("list")
    private List<News> list;

    public RequestResult() {
    }

    public List<News> getList() {
        return list;
    }

    public void setList(List<News> list) {
        this.list = list;
    }
}
