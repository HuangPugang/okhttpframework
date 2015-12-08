package com.paul.okhttpframework.okhttp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.paul.okhttpframework.application.MyApp;
import com.paul.okhttpframework.http.bean.RequestBean;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Paul on 15/12/8.
 */
public class OKManager {

    private String TAG = OKManager.class.getSimpleName();
    private volatile static OKManager instance = null;
    private static OkHttpClient mOkHttpClient;
    private OKManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OKManager getInstance() {
        if (null == instance) {
            synchronized (OKManager.class) {
                if (null == instance) {
                    instance = new OKManager();
                }
            }
        }
        return instance;
    }



    public void request(RequestBean requestBean,com.squareup.okhttp.Callback callback) throws Exception{

    }
}
