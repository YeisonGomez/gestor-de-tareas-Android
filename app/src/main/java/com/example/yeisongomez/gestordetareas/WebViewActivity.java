package com.example.yeisongomez.gestordetareas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webview = (WebView) findViewById(R.id.webview_id);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true); //Habilitar Zoom

        webview.loadUrl("http://www.google.com.co");

        webview.setWebViewClient(new WebViewClient(){
             public boolean shouldOverriceUrlLoading(WebView view, String url){
                return false;
            }
        });
    }
}
