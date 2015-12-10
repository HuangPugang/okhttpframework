package com.paul.okhttpframework.okhttp.callback;


public interface IResultCallback {

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
