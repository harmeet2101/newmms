package com.mbopartners.mbomobile.ui.activity.joinus;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.MboBaseActivity;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;

public class JoinUsActivity extends MboBaseActivity {

    private WebView joinUsWebView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogin_us);
        setupUpAppTabButtonIfPossible();

        joinUsWebView = (WebView) findViewById(R.id.mbo_Join_Us_webView);
        progress = (ProgressBar) findViewById(R.id.mbo_Join_Us_progressBar);
        progress.setMax(100);

        /**
         *  We turned off hardware acceleration because we get rendering artifacts on LG Nexus 5 (5.0.1)
         */
        joinUsWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        joinUsWebView.setWebViewClient(new MyWebViewClient());
        joinUsWebView.setWebChromeClient(new MyWebChromeClient());


        WebSettings settings = joinUsWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);

        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IApplicationControllersManager applicationControllersManager =
                (IApplicationControllersManager) getApplication();
        ConfigurationController configurationController =
                (ConfigurationController) applicationControllersManager.getController(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
        String joinUsUrl = configurationController.getCurrentEnvironmentVariables().joinUsLink;
        joinUsWebView.loadUrl(joinUsUrl);
    }

    @Override
    protected void onPause() {
        super.onPause();
        joinUsWebView.stopLoading();
        joinUsWebView.clearCache(true);
        joinUsWebView.clearFormData();
        joinUsWebView.clearHistory();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && joinUsWebView.canGoBack()) {
            joinUsWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jogin_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_dashboard_action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            JoinUsActivity.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            JoinUsActivity.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
//            if (Uri.parse(url).getHost().equals("www.example.com")) {
//                // This is my web site, so do not override; let my WebView load the page
//                return false;
//            }
//            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//            return true;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            JoinUsActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
}
