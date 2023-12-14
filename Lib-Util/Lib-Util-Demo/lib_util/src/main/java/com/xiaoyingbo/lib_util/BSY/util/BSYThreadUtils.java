package com.xiaoyingbo.lib_util.BSY.util;

import android.os.Looper;
import android.os.Process;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class BSYThreadUtils {

    private static volatile BSYThreadUtils instance;

    public static BSYThreadUtils getInstance() {
        if(instance==null){
            synchronized (BSYThreadUtils.class){
                if(instance==null){
                    instance=new BSYThreadUtils();
                }
            }
        }
        return instance;
    }

    private final Object mLock = new Object();
    private final String EXECUTOR_NAME = "executor_%d";
    private final String HANDLER_THREAD_NAME = "handler_thread";

    private final ExecutorService mDiskIO;
    /**
     * 主线程Handler
     */
    private volatile Handler mainHandler;
    /**
     * 异步线程Handler
     */
    private volatile Handler asyncHandler;
    /**
     * 异步线程
     */
    private HandlerThread handlerThread;
    /**
     * 同时处理两个任务
     */
    private static final int POOL = 2;

    public BSYThreadUtils() {
        mDiskIO = new ThreadPoolExecutor(POOL, POOL,
                0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactory() {
                    private final AtomicInteger mThreadId = new AtomicInteger(0);

                    @Override
                    public Thread newThread(@NonNull Runnable runnable) {
                        Thread t = new Thread(runnable);
                        t.setName(String.format(Locale.getDefault(), EXECUTOR_NAME, mThreadId.getAndIncrement()));
                        return t;
                    }
                });
        handlerThread = new HandlerThread(HANDLER_THREAD_NAME, Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
    }

    /**
     * 检查主线程Handler初始化
     */
    private void checkHandlerIsNull() {
        if (mainHandler == null) {
            synchronized (mLock) {
                if (mainHandler == null) {
                    mainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
    }

    /**
     * @return 异步线程的Handler
     */
    public Handler createAsyncHandler() {
        return createAsyncHandler(null);
    }

    public Handler createAsyncHandler(Handler.Callback callback) {
        synchronized (mLock) {
            return new Handler(handlerThread.getLooper(), callback);
        }
    }

    /**
     * 获取异步线程的Handler
     */
    public Handler getAsyncHandler() {
        if (asyncHandler == null) {
            synchronized (mLock) {
                if (asyncHandler == null) {
                    asyncHandler = createAsyncHandler();
                }
            }
        }
        return asyncHandler;
    }

    /**
     * 异步执行
     *
     * @param runnable runnable
     */
    public void executeOnIO(@NonNull Runnable runnable) {
        mDiskIO.execute(runnable);
    }

    /**
     * 主线程执行
     *
     * @param runnable runnable
     */
    public void postToMainThread(@NonNull Runnable runnable) {
        checkHandlerIsNull();
        mainHandler.post(runnable);
    }

    /**
     * 主线程延迟执行
     *
     * @param runnable    任务
     * @param delayMillis 延迟毫秒数
     */
    public void postToMainThread(@NonNull Runnable runnable, long delayMillis) {
        checkHandlerIsNull();
        mainHandler.postDelayed(runnable, delayMillis);
    }

    /**
     * @return 是否在主线程
     */
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public void removeRunnable(Runnable runnable) {
        mainHandler.removeCallbacks(runnable);
    }

    public void release() {
        if (!mDiskIO.isShutdown()) {
            mDiskIO.shutdown();
        }
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        if (asyncHandler != null) {
            asyncHandler.removeCallbacksAndMessages(null);
        }

    }
}
