package com.paul.okhttpframework;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.paul.okhttpframework.okhttp.API;
import com.paul.okhttpframework.okhttp.bean.OkError;
import com.paul.okhttpframework.okhttp.callback.IResponseCallback;
import com.paul.okhttpframework.okhttp.manager.OkClient;
import com.paul.okhttpframework.okhttp.manager.ParamManager;
import com.paul.okhttpframework.okhttp.progress.ProgressListener;
import com.paul.okhttpframework.util.PermissionManager;
import com.paul.okhttpframework.util.ToastUtil;

import java.io.File;
import java.util.Arrays;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * how to use
 */
public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);

    }

    /**
     * get 请求(已测可用)
     *
     * @param v
     */
    public void get(View v) {
        OkClient.request(ParamManager.getNewsListParam(1, 8), null, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {
                Log.e("HPG", object.toString());
            }

            @Override
            public void onError(int tag, OkError error) {

            }
        });
    }

    /**
     * post请求 (已测可用)
     *
     * @param v
     */
    public void post(View v) {
        OkClient.request(ParamManager.getPost(1, 8), null, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {
                Log.e("HPG", object.toString());
            }

            @Override
            public void onError(int tag, OkError error) {

            }
        });

    }

    /**
     * 上传文件(未测，在其他app中已测可用)
     *
     * @param v
     */
    public void upload(View v) {
        OkClient.cancelRequest(API.TAG_NEWS_LIST);
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//        OkClient.upload(ParamManager.upload(1, file), null, new IResponseCallback() {
//            @Override
//            public void onSuccess(int tag, Object object) {
//                Log.e("HPG", object.toString());
//            }
//
//            @Override
//            public void onError(int tag, OkError error) {
//
//            }
//        });
    }

    /**
     * 下载文件（已测可用）
     *
     * @param v
     */
    public void download(View v) {
        OkClient.download(ParamManager.download(), null, new IResponseCallback() {
            @Override
            public void onSuccess(int tag, Object object) {
                Log.e("hpg", "SUCCESS");
            }

            @Override
            public void onError(int tag, OkError error) {
                Log.e("hpg", "onError");

            }
        }, new ProgressListener() {
            @Override
            public void onProgress(long bytesWritten, long contentLength, long percent) {
                Log.e("HPG", percent + "%");
            }
        });
    }


    public void transform(View v) {
        PermissionManager.requirePermission(Manifest.permission.READ_EXTERNAL_STORAGE, new PermissionManager.Callback() {
            @Override
            public void permit(String permission) {
                tr();
            }

            @Override
            public void denied(String permission) {
                ToastUtil.showShort(MainActivity.this, "拒绝了");
            }

            @Override
            public void pass() {

            }
        });

    }

    private void tr() {
        Observable.just(Environment.getExternalStorageDirectory() + "/test.jpg") // 输入类型 String
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) { // 参数类型 String
                        return BitmapFactory.decodeFile(filePath); // 返回类型 Bitmap
                    }
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * 把两个相同的observable合并到一块
     *
     * @param v
     */
    public void merger(View v) {

        //合并
//        Observable<Integer> odds = Observable.just(1,3,5).subscribeOn(Schedulers.io());
//        Observable<Integer> evens = Observable.just(2,4,6).subscribeOn(Schedulers.io());
//
//        Observable.merge(odds,evens).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//
//                Log.e("HPG", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.e("HPG", integer + "");
//            }
//        });

        //普通订阅
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("test1");
                subscriber.onNext("test2");
                subscriber.onCompleted();
            }
        });
//        observable.subscribe(subscribe);
        observable.subscribe(action1);

        //from
        String[] name = new String[]{"huangpu", "wangjie", "huangcefs", "goodhuanpf", "wangnign", "huangbogasfdsa"};
//
//        Observable.from(Arrays.asList(name)).filter(new Func1<String, Boolean>() {
//            @Override
//            public Boolean call(String s) {
//                return s.startsWith("huang");
//            }
//        })
//                .map(new Func1<String, Integer>() {
//
//                    @Override
//                    public Integer call(String s) {
//                        return s.length();
//                    }
//                })
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e("HPG", "length=" + integer);
//                    }
//                });

//        Observable.from(name).subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e("HPG",s);
//            }
//        });
//
//        Observable.from(name).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//            }
//        });
    }


    private Subscriber<String> subscribe = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.e("HP", "onCompleted");
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            Log.e("HP", s);
        }
    };

    private Action1<String> action1 = new Action1<String>() {
        @Override
        public void call(String s) {
            Log.e("HP",s);
        }
    };


}
