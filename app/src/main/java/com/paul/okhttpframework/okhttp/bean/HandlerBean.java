package com.paul.okhttpframework.okhttp.bean;

import com.paul.okhttpframework.okhttp.callback.IResultCallback;

/**
 * Created by Paul on 15/12/10.
 */
public class HandlerBean {
    private TagBean tagBean ;
    private Object object;
    private IResultCallback iResultCallback;
    public HandlerBean() {
    }

    public HandlerBean(TagBean tagBean, Object object, IResultCallback iResultCallback) {
        this.tagBean = tagBean;
        this.object = object;
        this.iResultCallback = iResultCallback;
    }

    public TagBean getTagBean() {
        return tagBean;
    }

    public void setTagBean(TagBean tagBean) {
        this.tagBean = tagBean;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public IResultCallback getiResultCallback() {
        return iResultCallback;
    }

    public void setiResultCallback(IResultCallback iResultCallback) {
        this.iResultCallback = iResultCallback;
    }
}
