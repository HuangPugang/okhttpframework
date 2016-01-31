# okhttpframework
network frame base on okhttp,
首先，非常感谢小纹同学，在他的帮助下，对okhttp进行了封装

#HOW TO USE
#示例
    private void sendRequest(){
        HashMap<String,String> params = ParamManager.getXxxParam("1","5");
        RequestBean requestParam = RequestBeanManager.getRequestBean(URLConstant.TAG_GET_HEALTH_NEWS_LIST,params);
        OkBD.businessDispatch(URLConstant.TAG_GET_HEALTH_NEWS_LIST,requestParam,this);


#步骤
#1.在ParamManager中封装参数
    public static HashMap<String,String> getXxxParam(String pager,String rows){
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("pager",pager);
        params.put("rows",rows);
        return params;
    }

#2.在RequestBeanManager中封装RequestBean
	public static RequestBean getRequestBean(int tag, Map<String, String> params) {
		switch (tag) {
			case URLConstant.TAG_GET_HEALTH_NEWS_LIST:
				return getHealthNewsList(params);

		default:
			return null;
		}
	}

	public static RequestBean getHealthNewsList(Map<String, String> params) {
		RequestBean requestParam = new RequestBean();
		requestParam.setMethod(URLConstant.GET);
		requestParam.setUrl(URLConstant.URL_GET_HEALTH_LORE_LIST); //
		requestParam.setParams(params);
		return requestParam;
	}

#3.调用OkBD中的businessDispatch(int tag, RequestBean requestParam, IResultCallback iResultCallback)方法派发网络请求

