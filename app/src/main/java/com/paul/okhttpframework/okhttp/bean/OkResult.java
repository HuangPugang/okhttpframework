package com.paul.okhttpframework.okhttp.bean;


import com.paul.okhttpframework.okhttp.callback.IResponseCallback;

/**
 * Created by Paul on 15/12/10.
 */
public class OkResult {
    private int tag;
    private Object object;
    private IResponseCallback iResponseCallback;
    public OkResult() {
    }

    public OkResult(int tag, Object object, IResponseCallback iResponseCallback) {
        this.tag = tag;
        this.object = object;
        this.iResponseCallback = iResponseCallback;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public IResponseCallback getiResponseCallback() {
        return iResponseCallback;
    }

    public void setiResponseCallback(IResponseCallback iResponseCallback) {
        this.iResponseCallback = iResponseCallback;
    }
}
