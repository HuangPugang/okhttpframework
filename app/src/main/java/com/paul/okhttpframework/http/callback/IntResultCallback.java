package com.paul.okhttpframework.http.callback;

/**
 * 
 * TODO<Volly 请求结果回调>
 * 
 * @author Wen
 * @data: 2015-9-2 下午2:39:36
 * @version: V1.0
 */
public interface IntResultCallback {

	/**
	 * @param object
	 *            回调数据
	 * @param tag
	 *            标识
	 * @throw
	 * @return void
	 */
	public void onSuccessResult(Object object, int tag);

	public void onFailureResult(Object object, int tag);
}
