package com.paul.okhttpframework.util;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 16/3/15.
 */
public class TransValue {
    Map<String, Object> map;

    public TransValue() {
        map = new HashMap<>();
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key, Object defaultValue) {
        try {
            if (map.get(key) == null)
                return defaultValue;
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
        return map.get(key);
    }

    public int get(String key, int defaultValue) {
        try {
            if (map.get(key) == null)
                return defaultValue;
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
        return (Integer) map.get(key);
    }


    public Object get(String key){
        return map.get(key);
    }
}
