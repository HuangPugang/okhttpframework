package com.paul.okhttpframework.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Paul on 15/11/27.
 */
public class ThreadPoolUtils {
    private static ThreadPoolUtils instance;
    // 全局线程池
    private static ThreadPoolExecutor mThreadPool;

    private final int CORE_THREAD_NUM = 5;
    private final int MAX_THREAD_NUM = 10;
    private final int KEEP_ALIVE_TIME = 5000;
    private final int BLOCK_QUEUE_SIZE = 20;
    private final BlockingQueue<Runnable> blockQueue = new ArrayBlockingQueue<Runnable>(
            BLOCK_QUEUE_SIZE);
    private ThreadPoolUtils(){

        if (mThreadPool == null) {
            mThreadPool = new ThreadPoolExecutor(CORE_THREAD_NUM,
                    MAX_THREAD_NUM, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                    blockQueue, new ThreadPoolExecutor.DiscardOldestPolicy());
        }

    }
    public static ThreadPoolUtils getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolUtils.class) {
                instance = new ThreadPoolUtils();
            }
        }
        return instance;
    }

    public static ThreadPoolExecutor getThreadPool(){
        return mThreadPool;
    }

}
