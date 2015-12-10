package com.paul.okhttpframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paul.okhttpframework.constant.URLConstant;
import com.paul.okhttpframework.okhttp.bean.RequestBean;
import com.paul.okhttpframework.okhttp.bean.TagBean;
import com.paul.okhttpframework.okhttp.callback.IResultCallback;
import com.paul.okhttpframework.okhttp.manager.OkBD;
import com.paul.okhttpframework.okhttp.manager.ParamManager;
import com.paul.okhttpframework.okhttp.manager.RequestBeanManager;
import com.paul.okhttpframework.util.T;

import java.util.HashMap;

/**
 * how to use
 */
public class MainActivity extends AppCompatActivity implements IResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i=0;i<100;i++) {
            sendRequest();
        }

    }

    private void sendRequest(){
        HashMap<String,String> params = ParamManager.getXxxParam("1","5");
        RequestBean requestBean = RequestBeanManager.getRequestBean(URLConstant.TAG_GET_HEALTH_NEWS_LIST,params);
        OkBD.businessDispatch(new TagBean(URLConstant.TAG_GET_HEALTH_NEWS_LIST), requestBean, this);

    }

    @Override
    public void onSuccessResult(int tag, Object object) {
//        T.showShort(this,object.toString());
        Log.e("HPG",object.toString());
    }

    @Override
    public void onFailureResult(int tag, Object object) {

    }
}
