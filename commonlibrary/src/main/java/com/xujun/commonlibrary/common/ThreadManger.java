package com.xujun.commonlibrary.common;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ explain:用来统一管理线程池
 * @ author：xujun on 2016/5/17 14:52
 * @ email：gdutxiaoxu@163.com
 */
public class ThreadManger {

    private volatile static ThreadPoolProxy mLongPool;
    private volatile static ThreadPoolProxy mShortPool;

    private ThreadManger() {
    }

    private static class ThreadFactoryHolder {
        private static ThreadManger mThreadManger = new ThreadManger();
    }

    public static ThreadManger getInstance() {
        return ThreadFactoryHolder.mThreadManger;
    }

    public ThreadPoolProxy getShortPool() {
        if (mShortPool == null) {
            synchronized (ThreadManger.class) {
                if (mShortPool == null) {
                    mShortPool = new ThreadPoolProxy(4, 4, 60);
                }

            }
        }
        return mShortPool;
    }

    public ThreadPoolProxy getLongPool() {
        if (mLongPool == null) {
            synchronized (ThreadManger.class) {
                if (mLongPool == null) {
                    mLongPool = new ThreadPoolProxy(4, 10, 120);
                }

            }
        }
        return mLongPool;
    }

    public static class ThreadPoolProxy {
        private static volatile ThreadPoolExecutor pool;
        private int corePoolSize;
        private int maximumPoolSize;
        private long time;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.time = time;
        }

        /**
         * 执行任务
         *
         * @param runnable
         */

        public void excute(Runnable runnable) {
            if (pool == null) {
                synchronized (ThreadPoolProxy.class) {
                    if (pool == null) {
                        pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, time,
                                TimeUnit.SECONDS,
                                new LinkedBlockingDeque<Runnable>(10));
                    }
                }

            }
            pool.execute(runnable);
        }

        /**
         * 取消任务
         *
         * @param runnable
         */
        public void cancel(Runnable runnable) {
            if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
                pool.remove(runnable); // 取消异步任务
            }
        }

    }

}
