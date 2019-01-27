package com.example.a11708.informationflow.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a11708.informationflow.R;

public class WebviewActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private WebView mWebview;
    private TextView mBack;
    private TextView mClose;
    private TextView mWebTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();
    }

    private void init() {
        final String weburl = getIntent().getStringExtra("weburl");
        final String webtitle = getIntent().getStringExtra("webtitle");
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mWebview = (WebView) findViewById(R.id.web);
        mBack = (TextView) findViewById(R.id.w_back);
        mClose = (TextView) findViewById(R.id.w_close);
        mWebTitle = (TextView) findViewById(R.id.w_title);
        mWebTitle.setText(webtitle);
        WebSettings webSettings = mWebview.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setDomStorageEnabled(true);
        }
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);//设置进度值
                }
            }
        });
        mWebview.loadUrl(weburl);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                } else {
                    finish();
                }
            }
        });
    }
}
