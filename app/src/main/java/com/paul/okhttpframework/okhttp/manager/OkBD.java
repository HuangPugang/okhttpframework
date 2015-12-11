package com.paul.okhttpframework.okhttp.manager;

import android.os.Handler;
import android.os.Message;

import com.paul.okhttpframework.application.MyApp;
import com.paul.okhttpframework.okhttp.bean.ErrorBean;
import com.paul.okhttpframework.okhttp.bean.HandlerBean;
import com.paul.okhttpframework.okhttp.bean.RequestBean;
import com.paul.okhttpframework.okhttp.bean.TagBean;
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
    private static HashMap<TagBean, IResultCallback> mCallbackHashMap = new HashMap<TagBean, IResultCallback>();


    private static OkBD instance = null;

    private OkBD() {

    }
    public static OkBD getInstance() {
        if (null == instance) {
            synchronized (OkBD.class) {
                if (null == instance) {
                    instance = new OkBD();
                }
            }
        }
        return instance;
    }
    /**
     * @param iResultCallback
     * @param tag
     * @return void
     * @throw
     */
    public static void setIntResultCallback(IResultCallback iResultCallback, TagBean tag) {
        mCallbackHashMap.put(tag, iResultCallback);

    }

    /**
     * @param tag
     * @return IResultCallback
     * @throw
     */
    public static IResultCallback traverseFindAndRemoveHashMap(TagBean tag) {
        Iterator<Map.Entry<TagBean, IResultCallback>> iterator = mCallbackHashMap
                .entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<TagBean, IResultCallback> entry = (Map.Entry<TagBean, IResultCallback>) iterator
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
     * @param tag
     * @return IResultCallback
     * @throw
     */
    public static IResultCallback getAndRemoveHashMap(TagBean tag) {
        if (mCallbackHashMap.containsKey(tag)) {
            IResultCallback iResultCallback = mCallbackHashMap.get(tag);
            L.i(TAG, "Befor_removeTag_HashMap.size===" + mCallbackHashMap.size());
            mCallbackHashMap.remove(tag);
            return iResultCallback;
        }
        return null;

    }

    /**
     * @param tag
     * @return void
     * @throw
     */
    public static void traverseRemoveHashMap(TagBean tag) {
        mCallbackHashMap.remove(tag);
    }


    /**
     * @param tag         业务标志
     * @param requestBean 参数
     * @return void
     * @throw
     */
    public static void businessDispatch(TagBean tag, RequestBean requestBean, IResultCallback iResultCallback) {
        setIntResultCallback(iResultCallback, tag);
        sendRequest(requestBean, tag);
    }


    private static void sendRequest(RequestBean requestBean, final TagBean tag) {
        try {
            OkHttpManager.getInstance().request(requestBean, new Callback() {
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
                                    public void onSuccess(TagBean tag, Object object) {
                                        sendSuccessMessage(tag, object);
                                    }

                                    // 解析失败回调
                                    @Override
                                    public void onFailure(TagBean tag, ErrorBean errorBean) {
                                        sendFailedMessage(tag, errorBean);
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new OkHttpManager.OnNetConnectListener() {
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

    private static void sendSuccessMessage(TagBean tag, Object object) {
        Message message = Message.obtain();
        IResultCallback iResultCallback = getAndRemoveHashMap(tag);
        HandlerBean handlerBean = new HandlerBean(tag,object,iResultCallback);
        message.obj = handlerBean;
        handlerSuccess.sendMessage(message);
    }

    private static void sendFailedMessage(TagBean tag, ErrorBean errorBean) {
        IResultCallback iResultCallback = getAndRemoveHashMap(tag);
        HandlerBean handlerBean = new HandlerBean(tag,errorBean,iResultCallback);
        Message message = Message.obtain();
        message.obj = handlerBean;
        handlerFailed.sendMessage(message);
    }

    private static Handler handlerSuccess = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            HandlerBean handlerBean = (HandlerBean) msg.obj;
            TagBean tag = handlerBean.getTagBean();
            Object object = handlerBean.getObject();
            IResultCallback iResultCallback = handlerBean.getiResultCallback();
            if (tag!=null) {
                iResultCallback.onSuccessResult(tag.getTag(), object);
            }

        }
    };

    private static Handler handlerFailed = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            HandlerBean handlerBean = (HandlerBean) msg.obj;
            ErrorBean errorBean = (ErrorBean) handlerBean.getObject();
            TagBean tag = handlerBean.getTagBean();
            IResultCallback iResultCallback = handlerBean.getiResultCallback();
            if (tag!=null) {
                iResultCallback.onFailureResult(tag.getTag(), errorBean);
            }
        }
    };


}
