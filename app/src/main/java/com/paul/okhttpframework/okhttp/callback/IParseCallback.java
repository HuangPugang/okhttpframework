package com.paul.okhttpframework.okhttp.callback;


import com.paul.okhttpframework.okhttp.bean.ErrorBean;

/**
 * 解析
 */
public interface IParseCallback {

	// 成功回调
	public void onSuccess(Object object, int tag);

	// 失败回调
	public void onFailure(ErrorBean errorBean, int tag);

}
