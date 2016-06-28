package com.mbopartners.mbomobile.rest.oauth;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.rest.model.param.OAuthBodyEntity;
import com.mbopartners.mbomobile.rest.model.response.oauth.OAuthToken;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.RestServiceHelper;
import com.mbopartners.mbomobile.rest.rest.client.request.OAuthAuthenticateRequest;
import com.mbopartners.mbomobile.rest.rest.method.volley_impl.OAuthTokenRequest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class OAuthController extends AbstractApplicationController {
    private static final String TAG = OAuthController.class.getSimpleName();
    private static final long REQUEST_TIMEOUT = 30; // seconds
    private static final long ONE_SECOND = 1000L;
    private static final long PRE_EXPIRATION_INTERVAL = 30L;

    private SharedPreferencesController sharedPreferencesController;
    private ConfigurationController configurationController;
    private RequestQueue https_RequestQueue;
    private Response.ErrorListener errorListener;

    public OAuthController(SharedPreferencesController sharedPreferencesController,
                           ConfigurationController configurationController,
                           RequestQueue https_RequestQueue,
                           Response.ErrorListener errorListener) {

        super(Controllers.CONTROLLER__OAUTH);
        this.sharedPreferencesController = sharedPreferencesController;
        this.configurationController = configurationController;
        this.https_RequestQueue = https_RequestQueue;
        this.errorListener = errorListener;
    }

    @Override
    public boolean onStart() {
        return false;
    }

    @Override
    public boolean onPause() {
        return false;
    }

    /**
     *
     * @return null if no ability to get Access token
     * IT MEAN THAT WE HAVE TO:
     *    1. GET NEW NORMAL TOKEN.
     *    2. SAVE TOKEN !!!
     */
    public String getAccessToken() {
        if (!sharedPreferencesController.isUserLoggedIn()) {
            return null;
        }

        OAuthToken oAuthToken = sharedPreferencesController.getOAuthToken();
        if ( ! isOAuthTokenExpired(oAuthToken)) {
            Log.d(TAG, "*    TOKEN IS FRESH    *");
            return oAuthToken.getAccess_token();
        }

        if (isRefreshTokenExpired(oAuthToken)) {
            return null;
        }

        OAuthToken refreshedToken = requestToRefreshToken(oAuthToken.refresh_token);
        sharedPreferencesController.saveOAuthToken(refreshedToken);
        Log.d(TAG, "************************");
        Log.d(TAG, "*                      *");
        Log.d(TAG, "*   Token refreshed.   *");
        Log.d(TAG, "*                      *");
        Log.d(TAG, "************************");
        Log.d(TAG, refreshedToken.access_token.substring(0,50) + "...");

        return sharedPreferencesController.getOAuthToken().getAccess_token();
    }

    private boolean isOAuthTokenExpired(OAuthToken oAuthToken) {
        long currentTimeStamp = System.currentTimeMillis();
        return oAuthToken.getTimeStamp() + ((long)oAuthToken.expires_in - PRE_EXPIRATION_INTERVAL) * ONE_SECOND < currentTimeStamp;
    }

    private boolean isRefreshTokenExpired(OAuthToken oAuthToken) {
        long currentTimeStamp = System.currentTimeMillis();
        return oAuthToken.getTimeStamp() + ((long)oAuthToken.refresh_expires_in - PRE_EXPIRATION_INTERVAL) * ONE_SECOND < currentTimeStamp;
    }

    /**
     * Synchronously refreshes Access Token
     * @param refreshToken
     * @return
     */
    private OAuthToken requestToRefreshToken(String refreshToken) {
        OAuthToken refreshedToken = runRefreshing(refreshToken, this.errorListener);
        return  refreshedToken;
    }

    //==============================================================================================
    /**
     * Runs a blocking Volley request
     *
     * http://stackoverflow.com/questions/16904741/can-i-do-a-synchronous-request-with-volley
     *
     * @param errorListener handles errors
     * @return the input stream result or exception: NOTE returns null once the onErrorResponse listener has been called
     */
    private OAuthToken runRefreshing(String refreshToken, Response.ErrorListener errorListener) {
        RequestFuture<UniversalRestResponse> future = RequestFuture.newFuture();

        OAuthBodyEntity params = new OAuthBodyEntity();
        params.grantType = "refresh_token";
        params.refreshToken = refreshToken;

        String oAuthServerUrl = configurationController.getCurrentEnvironmentVariables().oAuthHostname;
        OAuthAuthenticateRequest request = new OAuthAuthenticateRequest(params, oAuthServerUrl);
        OAuthTokenRequest requestHttp = new OAuthTokenRequest(request, future, errorListener);
        https_RequestQueue.add(requestHttp);
        try {
            UniversalRestResponse restResponse = future.get(REQUEST_TIMEOUT, TimeUnit.SECONDS);
            OAuthToken refreshedOAuthToken = (OAuthToken) restResponse.getResponseEntity();
            return refreshedOAuthToken;
        } catch (InterruptedException e) {
            Log.e(TAG, "Retrieve cards api call interrupted.", e);
            errorListener.onErrorResponse(new VolleyError(e));
        } catch (ExecutionException e) {
            Log.e(TAG, "Retrieve cards api call failed.", e);
            errorListener.onErrorResponse(new VolleyError(e));
        } catch (TimeoutException e) {
            Log.e(TAG, "Retrieve cards api call timed out.", e);
            errorListener.onErrorResponse(new VolleyError(e));
        }
        return null;
    }

    //==============================================================================================

}
