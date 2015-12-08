package com.paul.okhttpframework.http.callback;


import com.paul.okhttpframework.http.bean.ErrorBean;

/**
 * 
 * TODO<用于所有Json数据解析回调>
 * 
 * @author Wen
 * @data: 2015-9-2 下午2:38:36
 */
public interface IntParseCallback {

	// 成功回调
	public void onSuccess(Object object, int tag);

	// 失败回调
	public void onFailure(ErrorBean errorBean, int tag);

}
