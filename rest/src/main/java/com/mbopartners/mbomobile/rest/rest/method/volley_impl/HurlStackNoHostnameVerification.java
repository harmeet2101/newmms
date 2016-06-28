package com.mbopartners.mbomobile.rest.rest.method.volley_impl;

import android.util.Log;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 */
public class HurlStackNoHostnameVerification extends HurlStack {
    private static final String TAG = HurlStackNoHostnameVerification.class.getSimpleName();
    public HurlStackNoHostnameVerification(UrlRewriter urlRewriter, SSLSocketFactory sslSocketFactory) {
        super(urlRewriter, sslSocketFactory);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpsURLConnection httpsConnection = (HttpsURLConnection) super.createConnection(url);
        // Tell the URLConnection to use our HostnameVerifier
        httpsConnection.setHostnameVerifier(hostnameVerifier);
        return httpsConnection;
    }

    // Create an HostnameVerifier that hardwires the expected hostname.
    // Note that is different than the URL's hostname:
    // example.com versus example.org
    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String urlHostName, SSLSession session) {
            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
            Log.v(TAG, "Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
            hv.verify("sso2-dev.mbopartners.com", session);
            return true;
        }
    };
}
