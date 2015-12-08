package com.paul.okhttpframework.http.manager;

import android.util.Log;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.paul.okhttpframework.application.MyApp;
import com.paul.okhttpframework.http.bean.ErrorBean;
import com.paul.okhttpframework.http.bean.RequestBean;
import com.paul.okhttpframework.http.callback.IntParseCallback;
import com.paul.okhttpframework.http.callback.IntResultCallback;
import com.paul.okhttpframework.util.L;
import com.paul.okhttpframework.util.T;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * TODO<逻辑业务网络请求分发>
 */
public class BD {

    private static String TAG = BD.class.getSimpleName();

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

    /**
     * TODO<发送请求,this app's net request，待优化 >
     *
     * @param requestBean
     * @param tag
     * @return void
     * @throw
     */
    private static void sendRequest(RequestBean requestBean, final int tag) {

        try {
            RequestManager.getInstance().request(requestBean,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JsonParse.JsonParseData(tag, response,
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
                                Log.i(TAG, "onFailure:jsonparse error");
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            L.i(TAG, "tag=" + tag + " ---  " + error.getMessage());
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
                    }, new RequestManager.ITimeoutListener() {

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
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
