package com.paul.okhttpframework.okhttp.manager;

import java.util.HashMap;

/**
 * 参数管理类,所有方法都是静态方法，不能获得该类的实例
 */
public class ParamManager {
    private static final String TAG = ParamManager.class.getSimpleName();
    private ParamManager(){
    }
    //————————————————————————————————————————————这里是分割线————————————————————————————————————————————


    public static HashMap<String,String> getXxxParam(String pager,String rows){
        HashMap<String,String> params = new HashMap<String,String>();
//        params.put("pager",pager);
//        params.put("rows",rows);
        return params;
    }

}
