package com.paul.okhttpframework.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;

import com.paul.okhttpframework.R;
import com.paul.okhttpframework.application.MyApp;


public class NetUtils {

	private NetUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity) {

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 网络已经连接，然后去判断是wifi连接还是GPRS连接 设置一些自己的逻辑调用
	 */
	public static void isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (gprs == State.CONNECTED || gprs == State.CONNECTING) {

			Toast.makeText(context, "当前为 2G/3G/4G网络，请注意使用流量", Toast.LENGTH_LONG)
					.show();
		}
		// 判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {

			Toast.makeText(context, "当前为 WIFI网络 ", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * 网络未连接时，调用设置方法
	 */
	public static void setNetwork(final Activity activity) {
		Toast.makeText(activity, "当前网络已关闭，请打开", Toast.LENGTH_LONG).show();

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setIcon(R.mipmap.ic_launcher);
		builder.setTitle("网络提示信息");
		builder.setMessage("网络不可用，如果继续，请先设置网络！");
		builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
				/**
				 * 判断手机系统的版本！如果API大于10 就是3.0+ 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
				 */
				if (android.os.Build.VERSION.SDK_INT > 10) {
					intent = new Intent(
							android.provider.Settings.ACTION_WIFI_SETTINGS);
				} else {
					intent = new Intent();
					ComponentName component = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				activity.startActivity(intent);
			}
		});
		builder.create();
		builder.show();
	}
	/**
	 * 是否为WiFi
	 * @return
	 */
	public static boolean isWifi(final DialogFactory.OnDialogClickListener listener){
		ConnectivityManager manager = (ConnectivityManager) MyApp.getMyAppContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (gprs == State.CONNECTED || gprs == State.CONNECTING) {

		}
		// 判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			return true;
		}else {
			DialogFactory.showAlertDialog(MyApp.getMyAppContext(), "温馨提示:", "当前为2G/3G/4G网络,是否继续操作?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					listener.confirm();
				}
			}, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					listener.cancel();
				}
			});
			return false;
		}
	}

	public static boolean isNetAvailable() {

		if (!NetUtils.isConnected(MyApp.getMyAppContext())) {
			// "当前网络已断开,请检查网络后重试");
			T.showShort(MyApp.getMyAppContext(), "当前网络已断开,请检查网络后重试");
			return false;

		}
		return true;
	}

}
