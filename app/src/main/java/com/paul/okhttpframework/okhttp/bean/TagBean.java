package com.paul.okhttpframework.okhttp.bean;

/**
 * Created by Paul on 15/12/10.
 */
public class TagBean {
    private int tag ;
    private int count = 1 ;

    public TagBean(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
