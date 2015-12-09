package com.paul.okhttpframework.okhttp.callback;


import com.paul.okhttpframework.okhttp.bean.ErrorBean;

/**
 * 
 * TODO<用于所有Json数据解析回调>
 * 
 * @author Wen
 * @data: 2015-9-2 下午2:38:36
 */
public interface IParseCallback {

	// 成功回调
	public void onSuccess(Object object, int tag);

	// 失败回调
	public void onFailure(ErrorBean errorBean, int tag);

}
