package com.smartcity.redux.adapters;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OverrideWebviewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
