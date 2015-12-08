package com.paul.okhttpframework.http.manager;


import java.util.HashMap;

public class ParamManager {
	/**
	 * Description: 在这里接受接受Activity页面传递的参数
	 */

	private static String TAG = "ParamManager";

	public static HashMap<String,String> getHealthNewsParam(String page, String rows){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page",page);
		params.put("rows",rows);
		return params;
	}
	
}
