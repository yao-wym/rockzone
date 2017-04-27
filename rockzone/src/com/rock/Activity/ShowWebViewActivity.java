package com.rock.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
;
import com.rock.view.MyWebCilent;

/**
 *关于我们
 */
public class ShowWebViewActivity extends BaseActivity {
    private String title;
    private String webStr;
    private String webUrl;
    public WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rock_web);
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        webStr = getIntent().getStringExtra("webStr");
        webUrl = getIntent().getStringExtra("webUrl");
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setPluginsEnabled(true);
        webView.requestFocus();
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        if(webStr!=null&&!webStr.equals("")){
            WebSettings settings = webView.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);

            webView.loadData(webStr, "text/html; charset=UTF-8", null);
        }else if(webUrl!=null){
            webView.setWebViewClient(new MyWebCilent());
            webView.loadUrl(webUrl);
        }
        initialHeader(title);
    }
    public void initialHeader(String title){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText(title);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if(webView.canGoBack() && event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            webView.goBack();   //goBack()表示返回webView的上一页面
            return true;
        }
        //todo 覆盖父类initheadmiddle方法，监听左上角返回
        return super.dispatchKeyEvent(event);
    }
    final class InJavaScriptLocalObj {
        public void showSource(String html) {
//            Toast.makeText(context,html,10).show();
        }
    }
}