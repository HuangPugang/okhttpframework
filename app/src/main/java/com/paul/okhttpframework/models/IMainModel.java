package com.paul.okhttpframework.models;

import com.paul.okhttpframework.okhttp.callback.IResponseCallback;

/**
 * Created by Paul on 16/3/15.
 */
public interface IMainModel {
    /**
     * 请求新闻列表
     * @param id
     */
    void requestNewsList(int id,IResponseCallback callback);
}
