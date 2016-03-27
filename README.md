
#用法

##GET请求
```
/**
     * get 请求(已测可用)
     * @param v
     */
    public void get(View v){
        OkClient.request(ParamManager.getNewsListParam(1, 8), null, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {
                Log.e("HPG",object.toString());
            }

            @Override
            public void onError(int tag, OkError error) {

            }
        });
    }
```
##POST请求
```
    /**
     * post请求 (已测可用)
     * @param v
     */
    public void post(View v){
        OkClient.request(ParamManager.getPost(1, 8), null, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {
                Log.e("HPG",object.toString());
            }

            @Override
            public void onError(int tag, OkError error) {

            }
        });

    }
```
##文件上传
```
    /**
     * 上传文件(未测，在其他app中已测可用)
     * @param v
     */
    public void upload(View v){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        OkClient.upload(ParamManager.upload(1, file), null, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {
                Log.e("HPG",object.toString());
            }

            @Override
            public void onError(int tag, OkError error) {

            }
        });
    }
```
##文件下载
```

    /**
     * 下载文件（已测可用）
     * @param v
     */
    public void download(View v){
        OkClient.download(ParamManager.download(), null, null, new ProgressListener() {
            @Override
            public void onProgress(long bytesWritten, long contentLength, long percent) {
                Log.e("HPG",percent+"%");
            }
        });
    }
```
