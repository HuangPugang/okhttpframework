package com.paul.okhttpframework.okhttp;

import com.paul.okhttpframework.http.bean.RequestBean;
import com.paul.okhttpframework.http.callback.IntResultCallback;
import com.paul.okhttpframework.util.L;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Paul on 15/12/8.
 */
public class OKBD {
    private static String TAG = OKBD.class.getSimpleName();
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

            OKManager.getInstance().request(requestBean, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                }
            });

        }catch (Exception e){

        }
    }

}
