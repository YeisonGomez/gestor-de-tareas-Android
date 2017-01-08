package com.example.yeisongomez.gestordetareas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webview = (WebView) findViewById(R.id.webview_id);
        webview.loadUrl("http://www.google.com.co");
    }
}
