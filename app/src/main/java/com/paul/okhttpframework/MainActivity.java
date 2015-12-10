package com.paul.okhttpframework;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paul.okhttpframework.constant.URLConstant;
import com.paul.okhttpframework.okhttp.bean.RequestBean;
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
        sendRequest();
    }

    private void sendRequest(){
        HashMap<String,String> params = ParamManager.getXxxParam("1","5");
        RequestBean requestBean = RequestBeanManager.getRequestBean(URLConstant.TAG_GET_HEALTH_NEWS_LIST,params);
        OkBD.businessDispatch(URLConstant.TAG_GET_HEALTH_NEWS_LIST,requestBean,this);
    }

    @Override
    public void onSuccessResult(Object object, int tag) {

        T.show(this,object.toString(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onFailureResult(Object object, int tag) {

    }
}
