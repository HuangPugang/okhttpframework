package com.paul.okhttpframework.okhttp.manager;

import android.os.Handler;
import android.os.Message;

import com.paul.okhttpframework.application.MyApp;
import com.paul.okhttpframework.okhttp.bean.ErrorBean;
import com.paul.okhttpframework.okhttp.bean.RequestBean;
import com.paul.okhttpframework.okhttp.callback.IParseCallback;
import com.paul.okhttpframework.okhttp.callback.IResultCallback;
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
    private static HashMap<Integer, IResultCallback> mCallbackHashMap = new HashMap<Integer, IResultCallback>();

    /**
     * TODO<结果回调注册>
     *
     * @param iResultCallback
     * @param tag
     * @return void
     * @throw
     */
    public static void setIntResultCallback(IResultCallback iResultCallback, int tag) {
        mCallbackHashMap.put(tag, iResultCallback);

    }

    /**
     * TODO< Traverse the HashMap to return the CallBack and remove it from the
     * HashMap when the tag matches>
     *
     * @param tag
     * @return IResultCallback
     * @throw
     */
    public static IResultCallback traverseFindAndRemoveHashMap(int tag) {
        Iterator<Map.Entry<Integer, IResultCallback>> iter = mCallbackHashMap
                .entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, IResultCallback> entry = (Map.Entry<Integer, IResultCallback>) iter
                    .next();
            if (entry.getKey() == tag) {
                IResultCallback iResultCallback = entry.getValue();
                mCallbackHashMap.remove(tag);
                return iResultCallback;
            }
        }
        return null;

    }

    /**
     * TODO< Traverse the HashMap to return the CallBack and remove it from the map
     * HashMap when the tag matches>
     *
     * @param tag
     * @return IResultCallback
     * @throw
     */
    public static IResultCallback getAndRemoveHashMap(int tag) {
        if (mCallbackHashMap.containsKey(tag)) {
            IResultCallback iResultCallback = mCallbackHashMap.get(tag);
            L.i(TAG, "Befor_removeTag_HashMap.size===" + mCallbackHashMap.size());

            return iResultCallback;
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
     * @param tag         业务标志
     * @param requestBean 参数
     * @return void
     * @throw
     */
    public static void businessDispatch(int tag, RequestBean requestBean, IResultCallback iResultCallback) {
        setIntResultCallback(iResultCallback,tag);
        sendRequest(requestBean, tag);
    }


    private static void sendRequest(RequestBean requestBean, final int tag) {
        try {

            OkhttpManager.getInstance().request(requestBean, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    T.showShort(MyApp.getMyAppContext(), "服务器连接异常，请稍后再试");
                    ErrorBean errorBean = new ErrorBean();
                    errorBean.setMsg("服务器连接异常，请稍后再试");
                    sendFailedMessage(tag, errorBean);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        JsonParseManager.JsonParseData(tag, response.body().string(),
                                new IParseCallback() {
                                    // 解析成功回调
                                    @Override
                                    public void onSuccess(Object object, int tag) {
                                        sendSuccessMessage(tag, object);
                                    }

                                    // 解析失败回调
                                    @Override
                                    public void onFailure(ErrorBean errorBean, int tag) {
                                        sendFailedMessage(tag, errorBean);
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new OkhttpManager.OnNetConnectListener() {
                @Override
                public void onNetDisconnected() {
                    L.i(TAG, "onNetDisconnected");
                    ErrorBean errorBean = new ErrorBean();
                    errorBean.setError("time_out");
                    errorBean.setMsg("当前网络已断开，请检查网络设置");
                    sendFailedMessage(tag, errorBean);
                }
            });

        } catch (Exception e) {

        }
    }

    private static void sendSuccessMessage(int tag, Object object) {
        Message message = Message.obtain();
        message.arg1 = tag;
        message.obj = object;
        handlerSuccess.sendMessage(message);
    }

    private static void sendFailedMessage(int tag, ErrorBean errorBean) {
        Message message = Message.obtain();
        message.arg1 = tag;
        message.obj = errorBean;
        handlerFailed.sendMessage(message);
    }

    private static Handler handlerSuccess = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int tag = msg.arg1;
            Object object = msg.obj;
            IResultCallback iResultCallback = getAndRemoveHashMap(tag);
            iResultCallback.onSuccessResult(object, tag);


        }
    };

    private static Handler handlerFailed = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int tag = msg.arg1;
            ErrorBean errorBean = (ErrorBean) msg.obj;
            IResultCallback iResultCallback = getAndRemoveHashMap(tag);
            iResultCallback.onFailureResult(errorBean, tag);
        }
    };

}
