package com.paul.okhttpframework.okhttp.manager;

import com.paul.okhttpframework.application.MyApp;
import com.paul.okhttpframework.okhttp.bean.ErrorBean;
import com.paul.okhttpframework.okhttp.bean.RequestBean;
import com.paul.okhttpframework.okhttp.callback.IntParseCallback;
import com.paul.okhttpframework.okhttp.callback.IntResultCallback;
import com.paul.okhttpframework.util.L;
import com.paul.okhttpframework.util.T;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Paul on 15/12/8.
 */
public class OkBD {
    private static String TAG = OkBD.class.getSimpleName();
    private static HashMap<Integer, IntResultCallback> mCallbackHashMap = new HashMap<Integer, IntResultCallback>();
    /**
     * TODO<结果回调注册>
     *
     * @param intResultCallback
     * @param tag
     * @return void
     * @throw
     */
    public static void setIntResultCallback(IntResultCallback intResultCallback, int tag) {
        mCallbackHashMap.put(tag, intResultCallback);

    }
    /**
     * TODO< Traverse the HashMap to return the CallBack and remove it from the
     * HashMap when the tag matches>
     *
     * @param tag
     * @return IntResultCallback
     * @throw
     */
    public static IntResultCallback traverseFindAndRemoveHashMap(int tag) {
        Iterator<Map.Entry<Integer, IntResultCallback>> iter = mCallbackHashMap
                .entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, IntResultCallback> entry = (Map.Entry<Integer, IntResultCallback>) iter
                    .next();
            if (entry.getKey() == tag) {
                IntResultCallback intResultCallback = entry.getValue();
                mCallbackHashMap.remove(tag);
                return intResultCallback;
            }
        }
        return null;

    }

    /**
     * TODO< Traverse the HashMap to return the CallBack and remove it from the map
     * HashMap when the tag matches>
     *
     * @param tag
     * @return IntResultCallback
     * @throw
     */
    public static IntResultCallback getAndRemoveHashMap(int tag) {
        if (mCallbackHashMap.containsKey(tag)) {
            IntResultCallback intResultCallback = mCallbackHashMap.get(tag);
            L.i(TAG, "Befor_removeTag_HashMap.size===" + mCallbackHashMap.size());

            return intResultCallback;
        }
        return null;

    }

    /**
     * TODO<Traverse the HashMap to remove it from the HashMap when the tag
     * matches>
     *
     * @param tag
     * @return void
     * @throw
     */
    public static void traverseRemoveHashMap(int tag) {
        mCallbackHashMap.remove(tag);
    }


    /**
     * TODO<业务逻辑任务分发>
     *
     * @param tag     业务标志
     * @param requestBean 参数
     * @return void
     * @throw
     */
    public static void businessDispatch(int tag, RequestBean requestBean, IntResultCallback intResultCallback) {
        mCallbackHashMap.put(tag, intResultCallback);
        sendRequest(requestBean, tag);
    }


    private static void sendRequest(RequestBean requestBean, final int tag){
        try {

            OkhttpManager.getInstance().request(requestBean, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    T.showShort(MyApp.getMyAppContext(), "服务器连接异常，请稍后再试");
                    IntResultCallback intResultCallback = getAndRemoveHashMap(tag);
                    if (null != intResultCallback) {
                        ErrorBean errorBean = new ErrorBean();
                        errorBean.setMsg("服务器连接异常，请稍后再试");
                        intResultCallback.onFailureResult(errorBean, tag);
                    } else {
                        L.i(TAG, "onFailure:null == intResultCallback");
                    }
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        JsonParse.JsonParseData(tag, response.body().string(),
                                new IntParseCallback() {
                                    // 解析成功回调
                                    @Override
                                    public void onSuccess(Object object, int tag) {
                                        IntResultCallback intResultCallback = getAndRemoveHashMap(tag);
                                        if (null != intResultCallback) {
                                            intResultCallback.onSuccessResult(object, tag);
                                        } else {
                                            L.i(TAG, "onSuccess:null == intResultCallback");
                                        }
                                    }

                                    // 解析失败回调
                                    @Override
                                    public void onFailure(ErrorBean errorBean, int tag) {
                                        L.i(TAG, errorBean.toString());
                                        IntResultCallback intResultCallback = getAndRemoveHashMap(tag);
                                        if (null != intResultCallback) {
                                            intResultCallback.onFailureResult(errorBean, tag);
                                        } else {
                                            L.i(TAG, "onFailure:null == intResultCallback");
                                        }
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new OkhttpManager.OnNetConnectListener(){

                @Override
                public void onNetDisconnected() {
                    L.i(TAG, "onNetDisconnected");
                    ErrorBean errorBean = new ErrorBean();
                    errorBean.setError("time_out");
                    errorBean.setMsg("当前网络已断开，请检查网络设置");

                    IntResultCallback intResultCallback = getAndRemoveHashMap(tag);
                    if (null != intResultCallback) {
                        intResultCallback.onFailureResult(errorBean,
                                tag);
                        L.i(TAG, "onTimeout Callback");

                    } else {
                        L.i(TAG, "onFailure:null == intResultCallback");
                    }
                }
            });

        }catch (Exception e){

        }
    }

}
