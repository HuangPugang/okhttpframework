package com.paul.okhttpframework.http.manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paul.okhttpframework.application.MyApp;
import com.paul.okhttpframework.constant.URLConstant;
import com.paul.okhttpframework.http.bean.RequestBean;
import com.paul.okhttpframework.util.NetUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class RequestManager {

	private String TAG = RequestManager.class.getSimpleName();
	private volatile static RequestManager instance = null;
	private RequestQueue mRequestQueue = null;

	private RequestManager(Context context) {
		initRequestQueue(context);
	}

	/**
	 * 初始化mRequestQueue
	 */
	public void initRequestQueue(Context context) {

		this.mRequestQueue = Volley.newRequestQueue(context);

	}

	public static RequestManager getInstance() {
		if (null == instance) {
			synchronized (RequestManager.class) {
				if (null == instance) {
					instance = new RequestManager(
							MyApp.getMyAppContext());
				}
			}
		}
		return instance;
	}

	public RequestQueue getRequestQueue() {
		return this.mRequestQueue;
	}

	/**
	 * doGet()
	 */
	private void doGet(String url, final Map<String, String> headers,
			final Map<String, String> params,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) throws Exception {

		String requestUrl = null;
		// 如果是GET请求，则请求参数在URL中
		if (params != null && !params.isEmpty()) {
			String param = urlEncode(params);
			requestUrl = url + "?" + param;
		} else {
			requestUrl = url;
		}
		Log.i(TAG + "----doGet", requestUrl);

		StringRequest stringRequest = new StringRequest(Method.GET, requestUrl,
				successListener, errorListener) {
			// 设置头信息
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {

				if (headers != null && !headers.isEmpty()) {
					return headers;
				} else {
					return Collections.emptyMap();
				}
			}

			// 为空时参数params传入null
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}

		};

		mRequestQueue.add(stringRequest);

	}

	/**
	 * doPost()
	 */
	private void doPost(String url, final Map<String, String> headers,
			final Map<String, String> params,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) {

		Log.i(TAG + "----doPost", url + params.toString());

		StringRequest stringRequest = new StringRequest(Method.POST, url,
				successListener, errorListener) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {

				if (headers != null && !headers.isEmpty()) {
					return headers;
				} else {
					return Collections.emptyMap();
				}

			}

		};

		mRequestQueue.add(stringRequest);

	}


	private void doPut(String url, final Map<String, String> headers,
			final Map<String, String> params,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) throws Exception {

		String requestUrl = null;

		Log.i(TAG + "----doPut", url);

		StringRequest stringRequest = new StringRequest(Method.PUT, url,
				successListener, errorListener) {
			// 设置头信息
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {

				if (headers != null && !headers.isEmpty()) {
					return headers;
				} else {
					return Collections.emptyMap();
				}
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}

		};

		mRequestQueue.add(stringRequest);

	}


	private void doDelete(String url, final Map<String, String> headers,
			final Map<String, String> params,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener) throws Exception {

		String requestUrl = null;

		Log.i(TAG + "----doDelete", url);

		StringRequest stringRequest = new StringRequest(Method.DELETE, url,
				successListener, errorListener) {
			// 设置头信息
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {

				if (headers != null && !headers.isEmpty()) {
					return headers;
				} else {
					return Collections.emptyMap();
				}
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}

		};
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 4, 1.0f));// 超时5s，5+1次
		mRequestQueue.add(stringRequest);

	}

	public void request(RequestBean requestBean,
			Response.Listener<String> successListener,
			Response.ErrorListener errorListener,
			ITimeoutListener iTimeoutListener) throws Exception {

		if (NetUtils.isNetAvailable()) {
			switch (requestBean.getMethod()) {

			case URLConstant.GET:
				doGet(requestBean.getUrl(), requestBean.getHeaders(),
						requestBean.getParams(), successListener, errorListener);
				break;

			case URLConstant.POST:

				doPost(requestBean.getUrl(), requestBean.getHeaders(),
						requestBean.getParams(), successListener, errorListener);
				break;
			case URLConstant.PUT:
				doPut(requestBean.getUrl(), requestBean.getHeaders(),
						requestBean.getParams(), successListener, errorListener);
				break;
			case URLConstant.DELETE:
				doDelete(requestBean.getUrl(), requestBean.getHeaders(),
						requestBean.getParams(), successListener, errorListener);

				break;

			default:
				break;
			}
		} else {
			iTimeoutListener.onNetDisconnected();
		}
	}

	private String urlEncode(Map<String, String> params)
			throws UnsupportedEncodingException {
		Iterator<String> iter = params.keySet().iterator();
		int i = 0;
		StringBuffer sb = new StringBuffer();

		while (iter.hasNext()) {
			String key = iter.next();
			String value = params.get(key);

			if (i != 0) {
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(URLEncoder.encode(value, "utf-8").toString());

			i++;
		}
		return sb.toString();
	}

	/**
	 * 网络突然断开回调，在BD中
	 */

	public interface ITimeoutListener {

		public void onNetDisconnected();

	}
}
