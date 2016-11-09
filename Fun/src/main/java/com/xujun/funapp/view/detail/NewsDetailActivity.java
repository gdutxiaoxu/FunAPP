package com.xujun.funapp.view.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.News;
import com.xujun.funapp.common.APP;
import com.xujun.funapp.common.Constants.IntentConstants;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.databinding.ActivityNewsDetailBinding;

import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

public class NewsDetailActivity extends BaseMVPActivity<ActivityNewsDetailBinding, BasePresenter> {

    private ImageView mIvBack;
    private WebView mWebView;

    News.NewslistBean mNewsListBean;
    private String mUrl;
    private ProgressBar mProgressBar;
    private TextView mTvTitle;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initIntent(Intent intent) {
        super.initIntent(intent);
        mNewsListBean = intent.getParcelableExtra(IntentConstants.DEFAULT_PARCEABLE_NAME);
        checkNotNull(mNewsListBean);
    }

    @Override
    protected void initView(ActivityNewsDetailBinding bind) {
        mIvBack = bind.ivBack;
        mWebView = bind.webView;
        mProgressBar = bind.progressBar;
        mTvTitle = bind.tvTitle;

    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mUrl = mNewsListBean.url;
        //        final String url = "http://www.baidu.com";
        WriteLogUtil.i("mUrl=" + mUrl);
        mWebView.loadUrl(mUrl);
        WebSettings settings = mWebView.getSettings();
        // 设置是够支持js脚本
        settings.setJavaScriptEnabled(true);
        // 设置是否支持画面缩放
        settings.setBuiltInZoomControls(true);

        settings.setSupportZoom(true);
        // 设置是否显示缩放器
        settings.setDisplayZoomControls(false);
        //  设置字体的大小
        settings.setTextZoom(120);


        //优先使用缓存:
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //不使用缓存:
        //settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {

                //第一种下载方式是 自定义的http工具类
//                new HttpDownloadThread(url,contentDisposition,mimetype,contentLength).start();
                //第二种下载方式是调用系统的webView,具有默认的进度条
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            //重写这个方法 返回true，在当前 webView 打开，否则在浏览器中打开
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(mUrl);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request,
                                            WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    int statusCode = errorResponse.getStatusCode();
                }
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String
                    failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //  没有网络连接
                if (false == APP.getInstance().isConnected()) {
                    APP.getInstance().showWifiDlg(NewsDetailActivity.this);
                } else {
                    if (errorCode == 404) {
                        //用javascript隐藏系统定义的404页面信息
                        String data = "Page NO FOUND！";
                        view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
                        mWebView.setVisibility(View.INVISIBLE);
                    } else {//其他状态码错误的处理，这里就不罗列出来了

                    }
                }

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int errorCode = error.getErrorCode();
                    CharSequence description = error.getDescription();
                }


            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //                LUtils.i("newProgress=" + newProgress);
                if (newProgress != 100) {
                    mProgressBar.setProgress(newProgress);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTvTitle.setText(title);
            }
        });

    }



    /**
     * 监听按下返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();

    }

    /**
     * cookie同步
     */
    @SuppressWarnings("deprecation")
    private void syncCookieToWebView(String url,List<String> cookies)
    {
        CookieSyncManager.createInstance(this);
        CookieManager cm = CookieManager.getInstance();
        cm.setAcceptCookie(true);
        if(cookies!=null)
        {
            for (String cookie : cookies)
            {
                cm.setCookie(url,cookie);//注意端口号和域名，这种方式可以同步所有cookie，包括sessionid
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    @SuppressWarnings("deprecation")
    public static void loadCookies() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    @SuppressWarnings("deprecation")
    public void clearCookies(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(null);
        } else {
            cookieManager.removeAllCookie();
        }
    }

}
