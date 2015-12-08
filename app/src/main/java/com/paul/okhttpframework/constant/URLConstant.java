package com.paul.okhttpframework.constant;

/**
 * Created by Paul on 15/12/1.
 */
public class URLConstant {
    //	// =======================================网络请求方式=======================================
    // 网络请求方式
    public static final int GET = 0X000;
    public static final int POST = 0X001;
    public static final int PUT = 0X010;
    public static final int DELETE = 0X011;

    // ======================================请求URL地址=======================================
    public static String URL_HEAD = "http://www.tngou.net/api";
    public static String URL_HEAD_IMAGE = "http://tnfs.tngou.net/image";//http://tnfs.tngou.net/image/top/default.jpg_180x120

    //新闻部分
    public static String URL_HEALTH_NEWS_CLASSIFY = URL_HEAD + "/info/classify";

    //?page=4&id=3&rows=2  获得列表  http://www.tngou.net/api/info/news
    public static String URL_GET_HEALTH_NEWS_LIST = URL_HEAD + "/info/list";
    //?id=10 新闻详情
    public static String URL_GET_HEALTH_NEWS_DETAIL = URL_HEAD + "/info/show";

    //知识部分
    public static String URL_GET_HEALTH_LORE_CLASSIFY = URL_HEAD + "/lore/classify";
    //?id=3&rows=10获得列表
    public static String URL_GET_HEALTH_LORE_LIST = URL_HEAD + "/lore/list";
    //?id=10 详情
    public static String URL_GET_HEALTH_LORE_DETAIL = URL_HEAD + "/lore/show";




    public static final int TAG_GET_HEALTH_NEWS_CLASSIFY = 1000;
    public static final int TAG_GET_HEALTH_NEWS_LIST = 1001;
    public static final int TAG_GET_HEALTH_NEWS_LOAD_MORE = 1002;

}
