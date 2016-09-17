package com.xujun.funapp.network;

/**
 * @ explain:用来在测试和正式上线的时候切换IP地址的配置
 * @ author：xujun on 2016-7-19 17:44
 * @ email：gdutxiaoxu@163.com
 */
public class NetworkConfig {


    public static INetwork create(Class<? extends INetwork> clazz) {
        INetwork iNetwork = null;
        try {
            iNetwork = clazz.newInstance();
            return iNetwork;
        } catch (InstantiationException e) {
            iNetwork = new Normal();
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            iNetwork = new Normal();
            e.printStackTrace();

        }
        return iNetwork;
    }

    public static class Normal implements INetwork {
        public static final String mBaseHttps = "http://www.tngou.net/";

        public static final String mBaseHttp = "http://www.tngou.net/";
        private static String mHostName = "121.8.226.221";

        private static final String mBaseUrl = mBaseHttp;
        // 正式环境
        private static final String mDownLoadUrl = mBaseHttp + "/mobileOA/interface/";

        public static final String mUploapUrlBase = mBaseHttp + "mobileOA/upload/";

        @Override
        public String getBaseUrl() {
            return mBaseUrl;
        }

        @Override
        public String getUploadUrl() {
            return mUploapUrlBase;
        }

        @Override
        public String getDownloadUrl() {
            return mDownLoadUrl;
        }

        @Override
        public String getHostName() {
            return mHostName;
        }

    }

    public static class Test implements INetwork {
        //    测试环境========================================================================

        public static final String mBaseHttps = "https://192.168.0.45:8880";
        public static final String mBaseHttp = "http://192.168.0.45:8880";
        private static String mHostName = "192.168.0.45";

        private static final String mBaseUrl = mBaseHttp + "/mobileOA/interface/";

        private static final String mDownLoadUrl = mBaseHttp + "/mobileOA/interface/";

        public static final String mUploapUrlBase = mBaseHttp + "mobileOA/upload/";

        @Override
        public String getBaseUrl() {
            return mBaseUrl;
        }

        @Override
        public String getUploadUrl() {
            return mUploapUrlBase;
        }

        @Override
        public String getDownloadUrl() {
            return mDownLoadUrl;
        }

        @Override
        public String getHostName() {
            return mHostName;
        }

    }
}
