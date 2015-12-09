package com.paul.okhttpframework.okhttp.manager;

import android.util.Log;

import com.paul.okhttpframework.constant.URLConstant;
import com.paul.okhttpframework.okhttp.bean.RequestBean;
import com.paul.okhttpframework.util.NetUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Paul on 15/12/8.
 */
public class OkhttpManager {

    private String TAG = OkhttpManager.class.getSimpleName();
    private volatile static OkhttpManager instance = null;
    private static OkHttpClient mOkHttpClient;
    private OkhttpManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OkhttpManager getInstance() {
        if (null == instance) {
            synchronized (OkhttpManager.class) {
                if (null == instance) {
                    instance = new OkhttpManager();
                }
            }
        }
        return instance;
    }



    public void request(RequestBean requestBean,com.squareup.okhttp.Callback callback,
                        OkhttpManager.OnNetConnectListener netConnectListener) throws Exception{
        if (NetUtils.isNetAvailable()) {
            switch (requestBean.getMethod()) {

                case URLConstant.GET:
                    doGet(requestBean.getUrl(), requestBean.getHeaders(),
                            requestBean.getParams(), callback);
                    break;

                case URLConstant.POST:
                    doPost(requestBean.getUrl(), requestBean.getHeaders(),
                            requestBean.getParams(), callback);

                    break;
            }
        }else {
            netConnectListener.onNetDisconnected();
        }
    }


    private void doGet(String url, final Map<String, String> headers,
                       final Map<String, String> params,
                       com.squareup.okhttp.Callback callback) throws Exception{
        String requestUrl = null;
        // 如果是GET请求，则请求参数在URL中
        if (params != null && !params.isEmpty()) {
            String param = urlEncode(params);
            requestUrl = url + "?" + param;
        } else {
            requestUrl = url;
        }
        Log.e(TAG, requestUrl);
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }


    private void doPost(String url, final Map<String, String> headers,
                        final Map<String, String> params,
                        com.squareup.okhttp.Callback callback) throws Exception{

        FormEncodingBuilder builder = new FormEncodingBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }


    private String urlEncode(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuffer sb2 = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                sb2.append(entry.getKey());
                sb2.append("=");
                sb2.append(URLEncoder.encode(entry.getValue(), "utf-8").toString());
                sb2.append("&");
            }
        }
        String s = "" ;
        if (sb2.length()!=0) {
            s = sb2.substring(0, sb2.length() - 1);
        }
        return s;
    }



    /**
     * 网络突然断开回调，在BD中
     */

    public interface OnNetConnectListener {

        public void onNetDisconnected();

    }
}
