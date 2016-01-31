package com.paul.okhttpframework.okhttp.manager;


import com.paul.okhttpframework.okhttp.bean.RequestParams;
import com.paul.okhttpframework.okhttp.callback.IResponseCallback;

/**
 * Created by Paul on 15/12/8.
 */
public class OkClient {
    private static String TAG = OkClient.class.getSimpleName();

    private static OkClient sInstance = null;
    private OkHttpManager mOkHttpManager;
    private OkClient() {
        mOkHttpManager = new OkHttpManager();
    }
    public static OkClient getInstance() {
        if (null == sInstance) {
            synchronized (OkClient.class) {
                if (null == sInstance) {
                    sInstance = new OkClient();
                }
            }
        }
        return sInstance;
    }

    /**
     * 请求
     * @param tag 网络请求的标志，不能为空
     * @param params 参数封装，不能为空
     * @param cls 返回的数据封装的类型，如果为null，则返回String
     * @param iResponseCallback 回调接口，每一个回调接口与tag绑定
     */
    public void dispatchRequest(int tag, RequestParams params, Class<?> cls, IResponseCallback iResponseCallback) {
        mOkHttpManager.request(tag, params, cls, iResponseCallback);
    }


    /**
     * 取消请求
     * @param tag
     */
    public void cancelRequest(int...tag){
        mOkHttpManager.cancelRequest(tag);
    }

    public void cancelAllRequest(){
        mOkHttpManager.cancelAllRequest();
    }
}
