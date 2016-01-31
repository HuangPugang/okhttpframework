package com.paul.okhttpframework.okhttp.callback;

import com.paul.okhttpframework.okhttp.bean.OkError;

public interface IResponseCallback {

	public void onSuccess(int tag, Object object);

	public void onError(int tag, OkError error);

}
