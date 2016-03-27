package com.paul.okhttpframework.util;

import android.Manifest;
import android.os.Build;

import com.paul.okhttpframework.MyApp;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by Paul on 16/3/26.
 */
public class PermissionManager {
    public static final String READ_PHONE_STATUS = Manifest.permission.READ_PHONE_STATE;
    public static final String CAMERA = Manifest.permission.CAMERA;


    private PermissionManager() {

    }

    public static void requirePermission( final String permission,final Callback callback) {
        if (Build.VERSION.SDK_INT>=23) {
            RxPermissions.getInstance(MyApp.getApp())
                    .request(permission)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                callback.permit(permission);
                            } else {
                                callback.denied(permission);
                            }
                        }
                    });
        }else {
            callback.pass();
        }
    }

    public interface Callback {
        /**
         * API>=23 允许权限
         * @param permission
         */
        void permit(String permission);

        /**
         * API>=23 拒绝权限
         * @param permission
         */
        void denied(String permission);

        /**
         * API<23 无需授予权限
         */
        void pass();
    }
}
