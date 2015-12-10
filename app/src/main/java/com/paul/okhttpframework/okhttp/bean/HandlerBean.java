package com.paul.okhttpframework.okhttp.bean;

/**
 * Created by Paul on 15/12/10.
 */
public class HandlerBean {
    private TagBean tagBean ;
    private Object object;

    public HandlerBean() {
    }

    public HandlerBean(TagBean tagBean, Object object) {
        this.tagBean = tagBean;
        this.object = object;
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
}
