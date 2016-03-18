package com.paul.okhttpframework.models;

import com.paul.okhttpframework.RequestResult;
import com.paul.okhttpframework.okhttp.API;
import com.paul.okhttpframework.okhttp.bean.OkError;
import com.paul.okhttpframework.okhttp.callback.IResponseCallback;
import com.paul.okhttpframework.okhttp.manager.OkClient;
import com.paul.okhttpframework.okhttp.manager.ParamManager;

/**
 * Created by Paul on 16/3/15.
 */
public class MainModel implements IMainModel{

    public MainModel(){

    }
    @Override
    public void requestNewsList(int id,IResponseCallback callback) {
        OkClient.request(ParamManager.getNewsListParam(1, 8), RequestResult.class, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {

            }

            @Override
            public void onError(int tag, OkError error) {

            }
        });
    }
}
