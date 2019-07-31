package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webViewActivity extends AppCompatActivity {
        WebView mwebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String Url = intent.getExtras().getString("URL");
        mwebView=findViewById(R.id.webView);
        mwebView.getSettings().setJavaScriptEnabled(true);
        mwebView.loadUrl(Url);
        mwebView.setWebChromeClient(new WebChromeClient());

        mwebView.setWebViewClient(new WebViewClientClass());


    }
        private class WebViewClientClass extends WebViewClient {//페이지 이동
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//뒤로가기 버튼 이벤트
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mwebView.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            mwebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
