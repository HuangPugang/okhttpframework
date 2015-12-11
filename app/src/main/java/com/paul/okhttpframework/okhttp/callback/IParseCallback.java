package com.paul.okhttpframework.okhttp.callback;


import com.paul.okhttpframework.okhttp.bean.ErrorBean;
import com.paul.okhttpframework.okhttp.bean.TagBean;

/**
 * 解析
 */
public interface IParseCallback {

	// 成功回调
	public void onSuccess(TagBean tag, Object object);

	// 失败回调
	public void onFailure(TagBean tag, ErrorBean errorBean);

}
