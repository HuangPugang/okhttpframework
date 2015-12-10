package com.paul.okhttpframework.okhttp.bean;

/**
 * 可根据实际情况进行调整
 */
public class ErrorBean {
	public String error;
	public String msg;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "IParseCallback [error=" + error + ", msg=" + msg + "]";
	}
}
