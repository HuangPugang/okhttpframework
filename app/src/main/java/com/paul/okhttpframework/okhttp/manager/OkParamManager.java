package com.paul.okhttpframework.okhttp.manager;


import com.paul.okhttpframework.okhttp.API;
import com.paul.okhttpframework.okhttp.bean.RequestParams;

import java.io.File;

/**
 * 参数管理类,所有方法都是静态方法，不能获得该类的实例
 */
public class OkParamManager {
    private static final String TAG = OkParamManager.class.getSimpleName();

    private OkParamManager() {

    }

    //————————————————————————————————————————————这里是分割线————————————————————————————————————————————

    public static RequestParams getNewsListParam(int id,int rows){
        RequestParams params = new RequestParams(API.TAG_NEWS_LIST,API.API_NEWS_LIST);
        params.put("id",id+"");
        params.put("rows", rows+"");
        return params;
    }

}
