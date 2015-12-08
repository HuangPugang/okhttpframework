package com.paul.okhttpframework.okhttp.manager;

import android.util.Log;


import com.paul.okhttpframework.constant.URLConstant;
import com.paul.okhttpframework.okhttp.bean.ErrorBean;
import com.paul.okhttpframework.okhttp.callback.IntParseCallback;
import com.paul.okhttpframework.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class JsonParse {

    public static String TAG = JsonParse.class.getSimpleName();
    private static JsonParse instance = null;

    private JsonParse() {

    }

    public static JsonParse getInstance() {
        if (null == instance) {
            synchronized (JsonParse.class) {
                if (null == instance) {
                    instance = new JsonParse();
                }
            }
        }
        return instance;
    }

    public static void JsonParseData(int tag, String jsonString,  IntParseCallback callback) throws JSONException {

        try {
            JSONObject jsonObject = new JSONObject(jsonString);//这里报错，说明是下面解析时出现了错误
            if (jsonObject.has("error")) {
                ErrorBean errorBean = new ErrorBean();
                errorBean.setError(jsonObject.optString("error"));
                errorBean.setMsg(jsonObject.optString("msg"));
                if (callback != null) {
                    callback.onFailure(errorBean, tag);
                    Log.i(TAG, "解析error回调");
                } else {
                    Log.i(TAG, "intjason == null");
                }

            } else {
                switch (tag) {
                    case URLConstant.TAG_GET_HEALTH_NEWS_LIST:
                    case URLConstant.TAG_GET_HEALTH_NEWS_LOAD_MORE:
                        jsonParseHealthNewsList(jsonObject, tag, callback);
                        break;

                    default:
                        break;
                }

            }

        } catch (Exception e) {
            ErrorBean errorBean = new ErrorBean();
            errorBean.setError("接口错误");
            errorBean.setMsg("程序猿们累得打瞌了，我们马上叫醒他们，请稍后再试");

            if (callback != null) {
                callback.onFailure(errorBean, tag);
                Log.e(TAG, "程序猿们累得打瞌了，我们马上叫醒他们，请稍后再试=" + "tag=" + tag + "   " + jsonString);

            } else {
                Log.i(TAG, "intjason == null");
            }
        }


    }


    /*
     * 作为公用的成功回调
     */
    public static void jsonParseHealthNewsList(JSONObject jsonObject, int tag, IntParseCallback callback) throws JSONException {
        if (callback != null) {
            JSONArray array = jsonObject.optJSONArray("tngou");

        } else {
            Log.i(TAG, "intjason == null");
        }
    }

    /*
     * 作为公用的成功回调
     */
    public static void jsonParseSuccessCommon(JSONObject jsonObject, int tag, IntParseCallback callback) throws JSONException {
        if (callback != null) {
            callback.onSuccess("", tag);
        } else {
            Log.i(TAG, "intjason == null");
        }
    }

    // ————————————————————————————————————通用方法——————————————————————————————————————————————————————————————————

    /**
     * 根据key获得json值
     *
     * @param key
     * @param jsonObject
     * @return
     */
    private static String getStringByKey(String key, JSONObject jsonObject) {
        if (jsonObject.has(key)) {
            String value = jsonObject.opt(key).toString();
            if ("null".equals(value) || null == value) {
                return "";
            } else
                return value;

        } else {
            Log.e(TAG, "==================key=" + key + "不存在==============");
            return "";
        }
    }

    //判断是否有JSONEobect对象并且不为空

    private static boolean hasJSONEobect(String key, JSONObject jsonObject) {
        if (jsonObject.has(key) && !StringUtils.isEmpty(getStringByKey(key, jsonObject))) {
            return true;
        } else {
            if (jsonObject.has(key)) {
                if (StringUtils.isEmpty(getStringByKey(key, jsonObject))) {
                    Log.e(TAG, "==================key=" + key + "的对象存在，但是为空==============");
                }
            }
            return false;
        }
    }


    /**
     * 获得内部没有key值的数组列表，如[1,3,2]or["你好","早上好","晚上好"]
     *
     * @return
     * @throws JSONException
     */
    private static ArrayList<String> getListByKey(String key, JSONObject jsonObject)
            throws JSONException {
        ArrayList<String> list = new ArrayList<String>();
        if (jsonObject.has(key)) {
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        }
        return list;
    }


    /**
     * 获得jsonObject对象的key值
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List<String> getJSONObjectKeyList(JSONObject jsonObject) {
        Iterator<String> it = jsonObject.keys();
        List<String> keyList = new ArrayList<String>();
        while (it.hasNext()) {// 遍历jsonObject中的key值
            String key = it.next().toString();
            keyList.add(key);
        }
        return keyList;
    }

}
