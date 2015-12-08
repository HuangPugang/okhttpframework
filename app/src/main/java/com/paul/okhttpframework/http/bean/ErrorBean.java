package com.paul.okhttpframework.http.bean;

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
		return "IntParseCallback [error=" + error + ", msg=" + msg + "]";
	}
}
