package com.paul.okhttpframework;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.paul.okhttpframework.application.MyApp;
import com.paul.okhttpframework.okhttp.API;
import com.paul.okhttpframework.okhttp.bean.OkError;
import com.paul.okhttpframework.okhttp.callback.IResponseCallback;
import com.paul.okhttpframework.okhttp.manager.OkClient;
import com.paul.okhttpframework.okhttp.manager.ParamManager;
import com.paul.okhttpframework.okhttp.progress.ProgressListener;
import com.paul.okhttpframework.util.NetUtils;

import java.io.File;

/**
 * how to use
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

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
}
