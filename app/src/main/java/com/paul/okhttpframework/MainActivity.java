package com.paul.okhttpframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paul.okhttpframework.constant.URLConstant;
import com.paul.okhttpframework.okhttp.bean.RequestBean;
import com.paul.okhttpframework.okhttp.bean.TagBean;
import com.paul.okhttpframework.okhttp.callback.IResultCallback;
import com.paul.okhttpframework.okhttp.manager.OkBD;
import com.paul.okhttpframework.okhttp.manager.ParamManager;
import com.paul.okhttpframework.okhttp.manager.RequestBeanManager;
import com.paul.okhttpframework.util.T;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * how to use
 */
public class MainActivity extends AppCompatActivity implements IResultCallback {
    OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
        mOkHttpClient.setWriteTimeout(10000, TimeUnit.MILLISECONDS);
        sendRequest();

    }

    private void sendRequest() {


        Request.Builder requestBuilder = new Request.Builder();

        Request request = requestBuilder
                .url("https://kyfw.12306.cn/otn/")
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("HPG", "fail");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.e("HPG", response.body().string());
            }
        });
    }

    public void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccessResult(int tag, Object object) {
//        T.showShort(this,object.toString());
        Log.e("HPG", object.toString());
    }

    @Override
    public void onFailureResult(int tag, Object object) {

    }
}
