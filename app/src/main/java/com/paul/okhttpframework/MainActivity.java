package com.paul.okhttpframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.paul.okhttpframework.okhttp.API;
import com.paul.okhttpframework.okhttp.bean.OkError;
import com.paul.okhttpframework.okhttp.callback.IResponseCallback;
import com.paul.okhttpframework.okhttp.manager.OkClient;
import com.paul.okhttpframework.okhttp.manager.OkParamManager;

/**
 * how to use
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkClient.getInstance().dispatchRequest(API.TAG_NEWS_LIST, OkParamManager.getNewsListParam(API.GET,API.API_NEWS_LIST,1,8), RequestResult.class, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {
                RequestResult requestResult = (RequestResult) object;
                Log.e("HPG",requestResult.toString());
            }

            @Override
            public void onError(int tag, OkError error) {

            }
        });
    }



}
