package com.paul.okhttpframework.util;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class StrUtils {


    /**
     * 判断字符串为空
     *
     * @param str
     * @return boolean
     * @throw
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0 || str.equals("")
                || str.equals("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 清理空字符串
     *
     * @param str
     * @return String
     * @throw
     */
    public static String clearEmpty(CharSequence str) {
        if (isEmpty(str)) {
            return "";
        } else {
            return (String) str;
        }
    }


    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    //判断空对象

    public static boolean isNullOrEmptyObj(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {

            if (obj.getClass().isArray()) {
                return Array.getLength(obj) == 0;
            }
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println("ww");
    }


    public static String parseFloat(String s) {
        if (isEmpty(s)) {
            return "";
        }

        DecimalFormat fnum = new DecimalFormat("##0.00");
        if (Float.parseFloat(s) < 0) {
            return "0.00";
        }
        return fnum.format(Float.parseFloat(s));
    }

    //set转String
    public static String setToString(Set set, String separator) {
        if (set == null || set.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Object o : set) {
            sb.append(o == null ? "null" : o.toString()).append(separator);
        }
        return sb.toString();

    }
}
