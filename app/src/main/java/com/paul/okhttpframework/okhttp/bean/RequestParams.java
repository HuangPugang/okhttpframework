package com.paul.okhttpframework.okhttp.bean;

import com.paul.okhttpframework.okhttp.API;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class RequestParams {
    private int method;
    private String url;
    protected Map<String, String> params;
    protected Map<String, File> files;
    protected Map<String, String> headers;

    public RequestParams(int method, String url) {
       this(method, url, false);
    }

    /**
     * @param method
     * @param url
     * @param withBaseURL 是否使用baseURL
     */
    public RequestParams(int method, String url,boolean withBaseURL) {
        init(method,url,withBaseURL);
    }

    private void init(int method, String url,boolean withBaseURL){
        this.method = method;
        if (withBaseURL) {
            this.url = API.SERVER_URL + url;
        }else{
            this.url = url;
        }
        this.params = new HashMap<>();
        this.files = new HashMap<>();
        this.headers = new HashMap<>();
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            params.put(key, value);
        }
    }

    public void putFile(String key, File file) {
        if (key != null && file != null) {
            files.put(key, file);
        }
    }

    public void putHeader(String key, String value) {
        if (key != null && value != null) {
            headers.put(key, value);
        }
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setFiles(Map<String, File> files) {
        this.files = files;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
