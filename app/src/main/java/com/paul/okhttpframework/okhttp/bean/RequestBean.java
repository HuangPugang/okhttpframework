package com.paul.okhttpframework.okhttp.bean;

import java.util.Map;

public class RequestBean {

	public int method;
	public String url;
	public Map<String, String> headers;
	public Map<String, String> params;

	public RequestBean() {
		super();
	}

	public RequestBean(int method, String url, Map<String, String> headers,
					   Map<String, String> params) {
		super();
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.params = params;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
