package com.munch.browser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.munch.browser.web.MunchWebView;

public class MunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munch);
        MunchWebView munchWebView = (MunchWebView) findViewById(R.id.munch_webview_view);
        munchWebView.loadUrl("https://yandex.ru");
    }
}
