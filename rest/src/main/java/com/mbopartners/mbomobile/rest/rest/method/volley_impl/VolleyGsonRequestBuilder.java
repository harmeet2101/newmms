package com.mbopartners.mbomobile.rest.rest.method.volley_impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.Volley;
import com.artisan.incodeapi.ArtisanTrackingManager;
import com.google.gson.JsonSyntaxException;
import com.mbopartners.mbomobile.rest.R;
import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.rest.oauth.OAuthController;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.RestServiceHelper;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.method.IRestHttpClient;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.HttpClientResult;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class VolleyGsonRequestBuilder implements IRestHttpClient {
    private static final String TAG = VolleyGsonRequestBuilder.class.getSimpleName();
    private static char[] KEYSTORE_PASSWORD = "29af6e25-7d7e-4b28-9eb8-189ccefede99".toCharArray();

    private static final String DEFAULT_REQUEST_TAG = "Default Volley Request Tag";

    private static final int NOT_EXISTS = -1;
    private static final int CREATED = 0;
    private static final int READY = 1;
    private static final int SHUTTING_DOWN = 2;
    private static final int STOPPED =3;

    private int status = NOT_EXISTS;
    private RequestQueue http_RequestQueue;
    private RequestQueue https_RequestQueue_SelfSignedCert;
    private Callback responseProcessor;
    private SharedPreferencesController sharedPreferencesController;
    private ConfigurationController configurationController;

    private Response.Listener<UniversalRestResponse> responseListener;
    private Response.ErrorListener errorListener;
    private Context context;

    public VolleyGsonRequestBuilder(Context context, SharedPreferencesController sharedPreferencesController, ConfigurationController configurationController) {
        this.context = context;
        this.sharedPreferencesController = sharedPreferencesController;
        this.configurationController = configurationController;

//        Network for http connection and https connection with VERIFIED CERTIFICATE
        Cache noCache = new NoCache();
        Network network = new BasicNetwork(new HurlStack());
        http_RequestQueue = new RequestQueue(noCache, network, 1);

        // Queue for HTTPS connection with SELF SIGNED CERTIFICATE
        https_RequestQueue_SelfSignedCert = Volley.newRequestQueue(context.getApplicationContext(), new HurlStackNoHostnameVerification(null, newSslSocketFactory()), 1);
//        https_RequestQueue_SelfSignedCert = new RequestQueue(noCache, network);

        status = CREATED;
    }

    private SSLSocketFactory newSslSocketFactory() {
        try {
            // Get an instance of the Bouncy Castle KeyStore format
            KeyStore keyStore = KeyStore.getInstance("BKS");
            // Get the raw resource, which contains the keystore with
            // your trusted certificates (root and any intermediate certs)
            InputStream in = context.getApplicationContext().getResources().openRawResource(R.raw.mystore);
            try {
                // Initialize the keystore with the provided trusted certificates
                // Provide the password of the keystore
                keyStore.load(in, KEYSTORE_PASSWORD);
            } catch (IOException e) {
                Log.e(TAG, e.toString(), e);
            } catch (NoSuchAlgorithmException e) {
                Log.e(TAG, e.toString(), e);
            } catch (CertificateException e) {
                Log.e(TAG, e.toString(), e);
            } finally {
                in.close();
            }

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

            SSLSocketFactory sf = sslContext.getSocketFactory();
            //SSLSocketFactory sf = new NoSSLv3Factory();
            return sf;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public void setHttpClientResponseProcessor(Callback responseProcessor) {
        this.responseProcessor = responseProcessor;

        http_RequestQueue.start();
        https_RequestQueue_SelfSignedCert.start();
        status = READY;
    }

    @Override
    public void start(AbstractRestRequest universalRestRequest) {
        Log.d(TAG, "Started  : " + universalRestRequest.getRequestDescriptor().getRequestId() + " ===> " + universalRestRequest.getRequestDescriptor().getRestMethod());
        RequestQueue requestQueue_forHttps;
//        ConfigurationController.EnvironmentConfiguration  configuration = configurationController.getCurrentConfiguration();
//        switch (configuration) {
//            case PRODUCTION : {
                /**
                 * Use verified certificate
                 */
                requestQueue_forHttps = http_RequestQueue;
//                break;
//            }
//            case PRE_PROD : {
//                /**
//                 * Use self-signed cert
//                 */
//                requestQueue_forHttps = https_RequestQueue_SelfSignedCert;
//                break;
//            }
//            default : {
//                /**
//                 * Use self-signed cert
//                 */
//                requestQueue_forHttps = https_RequestQueue_SelfSignedCert;
//            }
//        }

        if (status != READY) {
            throw new UnsupportedOperationException("Wrong status. Please check sequence of methods invocation.");
        }

        VolleyListener volleyListener = new VolleyListener(universalRestRequest);
        this.errorListener = volleyListener;
        this.responseListener = volleyListener;

        // REST ARCHITECTURE REQUESTS EXCLUSIONS
        Request volleyHttpRequest;
        String methodName = universalRestRequest.getRequestDescriptor().getRestMethod().getName();
        switch (methodName) {
            case RestApiContract.Name.oAuth : {
                volleyHttpRequest = new OAuthTokenRequest(universalRestRequest, responseListener, errorListener);
                volleyHttpRequest.setTag(DEFAULT_REQUEST_TAG);
                requestQueue_forHttps.add(volleyHttpRequest);
                break;
            }
            case RestApiContract.Name.createReceipt : {
                setOauthToken(universalRestRequest, requestQueue_forHttps);
                volleyHttpRequest = new MultipartGsonRequest_2(universalRestRequest, responseListener, errorListener);
                volleyHttpRequest.setTag(DEFAULT_REQUEST_TAG);
                http_RequestQueue.add(volleyHttpRequest);
                break;
            }
            default: {
                setOauthToken(universalRestRequest, requestQueue_forHttps);
                volleyHttpRequest = new GsonRequest(universalRestRequest,responseListener, errorListener);
                volleyHttpRequest.setTag(DEFAULT_REQUEST_TAG);
                http_RequestQueue.add(volleyHttpRequest);
            }
        }
    }

    private void setOauthToken(AbstractRestRequest universalRestRequest, RequestQueue requestQueue) {
        OAuthController oAuthController = new OAuthController(sharedPreferencesController, configurationController, requestQueue, errorListener);
        // Check oAuthExpiration
        String accessToken = oAuthController.getAccessToken();
        if (accessToken == null) {
            AuthFailureError error = new AuthFailureError("Can not refresh token");
//            NotAbleToRefreshToken error = new  NotAbleToRefreshToken(...);
//            class NotAbleToRefreshToken extends AuthFailureError { ... }
            errorListener.onErrorResponse(error);
        } else {
            RestServiceHelper.updateJwtAuthenticationCredentials(universalRestRequest, accessToken);
        }
    }


    @Override
    public void shutDown() {
        status = SHUTTING_DOWN;

        // TODO shutdown
        http_RequestQueue.stop();
        https_RequestQueue_SelfSignedCert.stop();

        status = STOPPED;
    }

    @Override
    public void cancelAllRequests() {
        Log.d(TAG, "cancelAllRequests()");
        http_RequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        https_RequestQueue_SelfSignedCert.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    //================================================================================

    private class VolleyListener implements Response.Listener<UniversalRestResponse>, Response.ErrorListener {

        private final AbstractRestRequest request;

        private VolleyListener(AbstractRestRequest request) {
            this.request = request;
        }

        @Override
        public void onResponse(UniversalRestResponse response) {
            Log.d(TAG, "Received : " + response.getRequest().getRequestDescriptor().getRequestId() + " <=== " + response.getRequest().getRequestDescriptor().getRestMethod());
            trackEventInArtisan(response);
            responseProcessor.processSuccessResponse(response);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            HttpClientResult clientResult;
            Object errorEntity = null;
            Map<String, String> headers = Collections.emptyMap();

            Class errorClass = error.getClass();
            if (errorClass == NoConnectionError.class) {
                clientResult = HttpClientResult.NoConnectionError;
            } else if (errorClass == NetworkError.class) {
                clientResult = HttpClientResult.NetworkError;
            } else if (errorClass == TimeoutError.class) {
                clientResult = HttpClientResult.TimeoutError;
            } else if (errorClass == ParseError.class) {
                clientResult = HttpClientResult.ParseError;
                error.printStackTrace();
            } else if (errorClass == ServerError.class) {
                clientResult = HttpClientResult.HttpError;
                clientResult.setStatusCode(error.networkResponse.statusCode);
                headers = error.networkResponse.headers;
                errorEntity = parseErrorData(error.networkResponse.data, headers);
            } else if (errorClass == AuthFailureError.class) {
                // todo check for NotAbleToRefreshToken
                clientResult = HttpClientResult.HttpError;
                if (error.networkResponse != null) {
                    clientResult.setStatusCode(error.networkResponse.statusCode);
                    headers = error.networkResponse.headers;
                    errorEntity = parseErrorData(error.networkResponse.data, headers);
                } else {
                    clientResult.setStatusCode(401);
                }




            } else if (errorClass == VolleyError.class && error.getCause().getClass() == IllegalArgumentException.class) {
                clientResult = HttpClientResult.EntityValidationError;
            } else if(error.networkResponse != null && error.networkResponse.statusCode > 0) {
                clientResult = HttpClientResult.HttpError;
                clientResult.setStatusCode(error.networkResponse.statusCode);
                headers = error.networkResponse.headers;
                errorEntity = parseErrorData(error.networkResponse.data, headers);
            } else {
                clientResult = HttpClientResult.Unknown;
            }

            UniversalRestResponse universalRestResponse = new UniversalRestResponse<>(
                    request,
                    clientResult,
                    new HttpHeaders(headers),
                    errorEntity
            );

            trackEventInArtisan(universalRestResponse);
            responseProcessor.processErrorResponse(universalRestResponse);
        }

        private Object parseErrorData(byte[] data, Map<String, String> headers) {
            Object errorEntity = null;
            if (data != null) {
                String json = null;
                Class clazz = request.getRequestDescriptor().getRestMethod().getErrorEntityClass();
                try {
                    json = new String(data, HttpHeaderParser.parseCharset(headers));

                    errorEntity = GsonRequest.getGson().fromJson(json, clazz);
                } catch (UnsupportedEncodingException e) {
                    return instantiateEntity(clazz);
                } catch (JsonSyntaxException e) {
                    return instantiateEntity(clazz);
                }
            }
            return errorEntity;
        }

        private Object instantiateEntity(Class clazz) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                return null;
            } catch (IllegalAccessException e) {
                return null;
            }
        }
    }



    private void trackEventInArtisan(UniversalRestResponse universalRestResponse) {
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("url", universalRestResponse.getRequest().getRequestDescriptor().getRestMethod().getUrl());
        parameters.put("urlFull", universalRestResponse.getRequest().getUrl());
        String result = universalRestResponse.getClientResult().toString();
        String httpResultCode = Integer.toString(universalRestResponse.getClientResult().getStatusCode());
        ArtisanTrackingManager.trackEvent("API event", parameters, result, httpResultCode);
    }

    //================================================================================
}
