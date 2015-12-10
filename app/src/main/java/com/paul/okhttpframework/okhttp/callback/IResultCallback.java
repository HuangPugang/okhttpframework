package com.paul.okhttpframework.okhttp.callback;

import com.paul.okhttpframework.okhttp.bean.TagBean;

/**
 * 请求结果
 */
public interface IResultCallback {

	/**
	 * @param object
	 *            回调数据
	 * @param tag
	 *            标识
	 * @throw
	 * @return void
	 */
	public void onSuccessResult(int tag, Object object);

	public void onFailureResult(int tag, Object object);
}
