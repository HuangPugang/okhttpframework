package com.paul.okhttpframework.constant;

/**
 * Created by Paul on 15/12/1.
 */
public class URLConstant {
    //	// =======================================网络请求方式=======================================
    // 网络请求方式
    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;
    public static final int FILE_UPLOAD = 4;
    public static final int FILE_DOWNLOAD = 5;

    // ======================================请求URL地址=======================================
    public static String URL_HEAD = "http://www.tngou.net/api";

    //?id=3&rows=10获得列表
    public static String URL_GET_HEALTH_LORE_LIST = URL_HEAD + "/lore/list";


    public static final int TAG_GET_HEALTH_NEWS_LIST = 1001;

}
