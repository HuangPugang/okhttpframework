package com.paul.okhttpframework.util;

import android.util.Log;

/**
 * 日志打印类
 */
public class DLog {

	public final static int VERBOSE = 5; // 不过滤输出所有调试信息 包括
											// VERBOSE、DEBUG、INFO、WARN、ERROR

	public final static int DEBUG = 4; // debug过滤器，输出DEBUG、INFO、WARN、ERROR调试信息

	public final static int INFO = 3; // info过滤器，输出INFO、WARN、ERROR调试信息

	public final static int WARN = 2; // waring过滤器，输出WARN和ERROR调试信息

	public final static int ERROR = 1; // error过滤器，只输出ERROR调试信息

	public static int level = 0; // 0和6

	private static final String TAG = "wen";
	// private static final String TAG = _CLASS_FUNC();
	public static final String SEPARATOR = ",";

	// 下面是默认tag的函数
	public static void v(String msg) {
		if (VERBOSE >= level) {
			Log.v(TAG, combineNoTagMessage(msg));
		}

	}

	public static void d(String msg) {
		if (DEBUG >= level) {
			Log.d(TAG, combineNoTagMessage(msg));
		}

	}

	public static void i(String msg) {
		if (INFO >= level) {
			Log.i(TAG, combineNoTagMessage(msg));
		}

	}

	public static void w(String msg) {
		if (WARN >= level) {
			Log.w(TAG, combineNoTagMessage(msg));
		}

	}

	public static void e(String msg) {
		if (ERROR >= level) {
			Log.e(TAG, combineNoTagMessage(msg));
		}

	}

	// 下面是传入自定义tag的函数,tag为类名
	public static void v(String tag, String msg) {
		if (VERBOSE >= level) {
			Log.v(tag, combineMessage(tag, msg));
		}

	}

	public static void d(String tag, String msg) {
		if (DEBUG >= level) {
			Log.d(tag, combineMessage(tag, msg));
		}

	}

	public static void i(String tag, String msg) {
		if (INFO >= level) {
			Log.i(tag, combineMessage(tag, msg));
		}

	}

	public static void w(String tag, String msg) {
		if (WARN >= level) {
			Log.w(tag, combineMessage(tag, msg));
		}

	}

	public static void e(String tag, String msg) {
		if (ERROR >= level) {
			Log.e(tag, combineMessage(tag, msg));
		}

	}

	private static String combineNoTagMessage(String msg) {
		return "[ " + TAG + "] " + " Line: " + getLineNumber() + " : " + msg;

	}

	private static String combineMessage(String tag, String msg) {
		return "[ " + tag + "] " + " Line: " + getLineNumber() + " : " + msg;

	}

	private static int getLineNumber() {
		return Thread.currentThread().getStackTrace()[5].getLineNumber();
	}
}
