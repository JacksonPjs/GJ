package com.xiaowei.ui.activity.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.blibrary.loadinglayout.LoadingLayout;
import com.example.blibrary.log.Logger;
import com.xiaowei.R;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.MainActivity;
import com.xiaowei.utils.IntentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.contentLayout)
    LoadingLayout contentLayout;
    @Bind(R.id.title)
    TextView title;

    String url;
    String tv;
    String downloadLink;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);


//        url ="file:///android_asset/security.html";
//        url ="https://www.baidu.com";
        activity=this;
        url = getIntent().getStringExtra("url");
        tv = getIntent().getStringExtra("title");
        downloadLink = getIntent().getStringExtra("downloadLink");
//        downloadLink = "http://api.jixiangshop.com/jxsc.apk";
        title.setText(tv+"");
        toolbar.setBackgroundResource(R.color.colorPrimary);
        initWebView();
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    if (contentLayout != null)
                        contentLayout.setStatus(LoadingLayout.Success);
//                    if (webView != null)
//                        url = webView.getUrl();
                } else {
                    if (contentLayout != null)
                        contentLayout.setStatus(LoadingLayout.Loading);

                }

            }



        });

        webView.setWebViewClient(new MyWebViewClient());


        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);




        webView.getSettings().setJavaScriptEnabled(true);


        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.addJavascriptInterface(new JavaScriptObject(this), "xiaoweiapp");
        webView.getSettings().setUseWideViewPort(true); //设置加载进来的页面自适应手机屏幕（可缩放）
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setAppCacheEnabled(false);

        webView.getSettings().setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.loadUrl(url);
    }
    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class  MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //这里进行url拦截
            if (url != null && url.contains(downloadLink+"")) {
                IntentUtils.GoChrome(activity,url);
                finish();
//
//                finish(); Uri kk = Uri.parse(url);

                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return false;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }

    public class JavaScriptObject {
        Context mContxt;

        //sdk17版本以上加上注解
        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }

        @JavascriptInterface
        public void goRecharge() {
            //   T.ShowToastForShort(mContxt,"goReCharge");
            finish();

        }



    }

}