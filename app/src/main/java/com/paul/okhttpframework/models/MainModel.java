package com.paul.okhttpframework.models;

import android.util.Log;

import com.paul.okhttpframework.RequestResult;
import com.paul.okhttpframework.okhttp.API;
import com.paul.okhttpframework.okhttp.bean.OkError;
import com.paul.okhttpframework.okhttp.callback.IResponseCallback;
import com.paul.okhttpframework.okhttp.manager.OkClient;
import com.paul.okhttpframework.okhttp.manager.OkParamManager;

/**
 * Created by Paul on 16/3/15.
 */
public class MainModel implements IMainModel{

    public MainModel(){

    }
    @Override
    public void requestNewsList(int id,IResponseCallback callback) {
        OkClient.getInstance().dispatchRequest(API.TAG_NEWS_LIST, OkParamManager.getNewsListParam( 1, 8), RequestResult.class, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {

            }

            @Override
            public void onError(int tag, OkError error) {

            }
        });
    }
}
