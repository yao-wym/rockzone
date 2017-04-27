package com.rock.view;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by wym on 2014/12/9.
 */
public class MyWebCilent extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        view.loadUrl("javascript:" +
                "d =  document.getElementsByClassName('list-info').item(0);" +
                "htmlStr = d.outerHtml;" +
                "window.local_obj.showSource(htmlStr);"+
                "window.local_obj.showSource(d)");
    }
}
