package com.paul.okhttpframework.okhttp.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.paul.okhttpframework.okhttp.API;
import com.paul.okhttpframework.okhttp.bean.OkError;
import com.paul.okhttpframework.okhttp.bean.OkResult;
import com.paul.okhttpframework.okhttp.bean.OkTag;
import com.paul.okhttpframework.okhttp.bean.RequestParams;
import com.paul.okhttpframework.okhttp.callback.IResponseCallback;
import com.paul.okhttpframework.util.L;
import com.paul.okhttpframework.util.NetUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp管理类
 */
public class OkHttpManager {
    private String TAG = OkHttpManager.class.getSimpleName();
    private static final int DEFAULT_TIME_OUT = 15000;
    private static final int CODE_SUCCESS = 0;
    private static final int CODE_FAILED = 1;

    private OkHttpClient mOkHttpClient;
    private InternalHandler mInternalHandler;
    private ConcurrentHashMap<OkTag, IResponseCallback> mCallbacks = new ConcurrentHashMap<>();
    private ConcurrentHashMap<OkTag, Call> mAsyncCalls = new ConcurrentHashMap<>();
    private Gson mGson;

    public OkHttpManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)

                .build();
    }


    /**
     * @param tag
     * @param params
     * @param cls
     * @param callback
     */
    public void request(OkTag tag, RequestParams params, Class<?> cls, IResponseCallback callback) {
        //添加到回调map里
        addCallback(tag, callback);
        if (params == null) {
            sendFailedMessage(tag, getOkError("参数错误", 300));
            return;
        }
        if (NetUtils.isNetAvailable()) {
            try {
                switch (params.getMethod()) {
                    case API.GET:
                        doGet(tag, params.getUrl(), params.getHeaders(),
                                params.getParams(), cls);
                        break;
                    case API.POST:
                        doPost(tag, params.getUrl(), params.getHeaders(),
                                params.getParams(), cls);
                        break;
                    case API.UPLOAD:
                        doUpload(tag, params.getUrl(), params.getHeaders(),
                                params.getParams(), params.getFiles(), cls);
                        break;

                }
            } catch (Exception e) {
                sendFailedMessage(tag, getOkError("请求失败", 0));
            }
        } else {
            sendFailedMessage(tag, getOkError("当前网络已断开,请检查网络后重试", 0));
        }
    }


    /**
     * @param tag
     * @param url
     * @param headers
     * @param params
     * @param cls
     * @throws Exception
     */
    private void doGet(final OkTag tag, String url, final Map<String, String> headers,
                       final Map<String, String> params, final Class<?> cls) throws Exception {
        String requestUrl;
        // 如果是GET请求，则请求参数在URL中
        if (params != null && !params.isEmpty() && params.size() != 0) {
            String param = urlEncode(params);
            requestUrl = url + "?" + param;
        } else {
            requestUrl = url;
        }
        L.i(TAG, "tag=" + tag + " GET:" + requestUrl);
        Request.Builder requestBuilder = new Request.Builder();
        if (null != headers && !headers.isEmpty() && headers.size() != 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
        }
        Request request = requestBuilder
                .url(requestUrl)
                .build();
        deliveryRequest(tag, request, cls);
    }

    /**
     * @param tag
     * @param url
     * @param headers
     * @param params
     * @param cls
     */
    private void doPost(final OkTag tag, String url, final Map<String, String> headers,
                        final Map<String, String> params, final Class<?> cls) {

        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty() && params.size() != 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
        }
        RequestBody formBody = builder.build();
        //build request header
        Request.Builder requestBuilder = new Request.Builder();
        if (null != headers && !headers.isEmpty() && headers.size() != 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
        }
        L.i(TAG, "tag=" + tag + " POST:" + url);
        L.i(TAG, "tag=" + tag + " params= " + params.toString());
        Request request = requestBuilder
                .url(url)
                .post(formBody)
                .build();
        deliveryRequest(tag, request, cls);
    }

    //fileupload
    public void doUpload(final OkTag tag, String url, final Map<String, String> headers, final Map<String, String> params,
                         final Map<String, File> files, final Class<?> cls) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        if (params != null && !params.isEmpty() && params.size() != 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, entry.getValue()));
                }
            }
        }


        if (files != null && !files.isEmpty() && files.size() != 0) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    RequestBody fileBody = null;
                    fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), entry.getValue());
                    builder.addPart(Headers.of("Content-Disposition",
                                    "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + entry.getValue().getName() + "\""),
                            fileBody);
                }
            }
        }
        RequestBody requestBody = builder.build();

        Request.Builder requestBuilder = new Request.Builder();
        if (null != headers && !headers.isEmpty() && headers.size() != 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
        }
        L.i(TAG, "tag=" + tag + " UPLOAD:" + url);
        L.i(TAG, "tag=" + tag + " params= " + params.toString());
        Request request = requestBuilder
                .url(url)
                .post(requestBody)
                .build();

        deliveryRequest(tag, request, cls);
    }

    private void deliveryRequest(final OkTag tag, final Request request, final Class<?> cls) {

        Call call = mOkHttpClient.newCall(request);
        //添加到请求map里
        addCall(tag, call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OkError okError = getOkError("服务器连接异常，请稍后再试", 100);
                sendFailedMessage(tag, okError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String strResult = response.body().string();
                    L.i(TAG, "tag=" + tag + " result=" + strResult);
                    Object result ;
                    if (cls != null) {
                        Gson gson = getGson();
                        result = gson.fromJson(strResult, cls);
                    } else { //没有指定类型，直接返回string
                        result = strResult;
                    }
                    if (result == null) {
                        sendFailedMessage(tag, getOkError("数据为空", 100));
                    }else {
                        sendSuccessMessage(tag, result);
                    }


                } catch (Exception e) {
                    sendFailedMessage(tag, getOkError("解析错误", 100));
                }

            }
        });
    }


    /**
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
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
        String s = "";
        if (sb2.length() != 0) {
            s = sb2.substring(0, sb2.length() - 1);
        }
        return s;
    }

    /**
     * @param iResponseCallback
     * @param tag
     * @return void
     * @throw
     */
    private void addCallback(OkTag tag, IResponseCallback iResponseCallback) {
        mCallbacks.put(tag, iResponseCallback);

    }

    private void addCall(OkTag tag, Call call) {
        mAsyncCalls.put(tag, call);
    }


    /**
     * @param tag
     * @return IResponseCallback
     * @throw
     */
    private IResponseCallback getAndRemoveCallback(OkTag tag) {
        if (mCallbacks != null && mCallbacks.size() != 0 && mCallbacks.containsKey(tag)) {
            IResponseCallback iResponseCallback = mCallbacks.get(tag);
            L.i(TAG, "Before_removeTag_HashMap.size===" + mCallbacks.size());
            mCallbacks.remove(tag);
            return iResponseCallback;
        }
        return null;
    }

    /**
     * @param tag
     * @return void
     * @throw
     */
    private void removeCallback(OkTag tag) {
        if (mCallbacks != null && mCallbacks.size() != 0 && mCallbacks.containsKey(tag)) {
            mCallbacks.remove(tag);
        }
    }

    private void removeCall(OkTag tag) {
        if (mAsyncCalls != null && mAsyncCalls.size() != 0 && mAsyncCalls.containsKey(tag)) {
            mAsyncCalls.remove(tag);
        }
    }

    private void sendSuccessMessage(OkTag tag, Object object) {
        removeCall(tag);
        IResponseCallback iResponseCallback = getAndRemoveCallback(tag);
        if (iResponseCallback != null) {
            Message message = getHandler().obtainMessage();
            OkResult okResult = new OkResult(tag, object, iResponseCallback);
            message.arg1 = CODE_SUCCESS;
            message.obj = okResult;
            message.sendToTarget();
        }
    }

    private void sendFailedMessage(OkTag tag, OkError okError) {
        removeCall(tag);
        IResponseCallback iResponseCallback = getAndRemoveCallback(tag);
        if (iResponseCallback != null) {
            OkResult okResult = new OkResult(tag, okError, iResponseCallback);
            Message message = getHandler().obtainMessage();
            message.obj = okResult;
            message.arg1 = CODE_FAILED;
            message.sendToTarget();
        }
    }

    /**
     * @return
     */
    private Gson getGson() {
        if (mGson == null) {

            mGson = new Gson();
        }
        return mGson;
    }

    public OkError getOkError(String msg, int status) {
        OkError okError = new OkError(status, msg);
        return okError;
    }

    /**
     * @param tag
     * @return
     */
    private boolean checkTag(OkTag tag) {
        if (mCallbacks != null && mCallbacks.size() != 0) {
            if (mCallbacks.containsKey(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param tags
     */
    public void cancelRequest(int... tags) {
        if (tags != null && tags.length != 0) {
            List<OkTag> cancelCalls = new ArrayList<>();
            List<OkTag> cancelCallbacks = new ArrayList<>();
            for (int i = 0; i < tags.length; i++) {
                int tag = tags[i];
                if (mAsyncCalls != null && mAsyncCalls.size() != 0) {
                    for (Map.Entry<OkTag, Call> entry : mAsyncCalls.entrySet()) {
                        if (entry.getKey().getTag() == tag) {
                            cancelCalls.add(entry.getKey());
                        }
                    }
                }
                if (mCallbacks != null && mCallbacks.size() != 0) {
                    for (Map.Entry<OkTag, IResponseCallback> entry : mCallbacks.entrySet()) {
                        if (entry.getKey().getTag() == tag) {
                            cancelCallbacks.add(entry.getKey());
                        }
                    }
                }
            }

            for (int i = 0; i < cancelCalls.size(); i++) {
                Call call = mAsyncCalls.get(cancelCalls.get(i));
                call.cancel();
                mAsyncCalls.remove(cancelCalls.get(i));
            }
            for (int i = 0; i < cancelCallbacks.size(); i++) {
                mCallbacks.remove(cancelCallbacks.get(i));
            }
        }
    }


    public void cancelAllRequest() {
        mAsyncCalls.clear();
        mCallbacks.clear();
    }

    private Handler getHandler() {
        synchronized (OkHttpManager.class) {
            if (mInternalHandler == null) {
                mInternalHandler = new InternalHandler();
            }
            return mInternalHandler;
        }
    }





    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            //run on main thread
            OkResult okResult = (OkResult) msg.obj;
            IResponseCallback iResponseCallback = okResult.getResponseCallback();
            OkTag tag = okResult.getTag();
            switch (msg.arg1) {
                case CODE_SUCCESS:
                    Object object = okResult.getObject();
                    iResponseCallback.onSuccess(tag.getTag(), object);
                    break;
                case CODE_FAILED:
                    OkError okError = (OkError) okResult.getObject();
                    iResponseCallback.onError(tag.getTag(), okError);
                    break;
            }
        }
    }

}
