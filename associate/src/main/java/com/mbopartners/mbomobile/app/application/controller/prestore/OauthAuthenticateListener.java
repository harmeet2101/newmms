package com.mbopartners.mbomobile.app.application.controller.prestore;

import com.mbopartners.mbomobile.rest.model.param.OAuthBodyEntity;
import com.mbopartners.mbomobile.rest.model.response.oauth.OAuthToken;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;

import ua.com.mobidev.android.framework.util.UrlUtils;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

/**
 *
 */
public class OauthAuthenticateListener implements PreStoreListener{
    private static final String TAG = OauthAuthenticateListener.class.getSimpleName();
    private SharedPreferencesController sharedPreferencesController;

    public OauthAuthenticateListener(SharedPreferencesController sharedPreferencesController) {
        this.sharedPreferencesController = sharedPreferencesController;
    }

    @Override
    public void onMethodBefore(AbstractRestRequest request) {
    }

    @Override
    public void onMethodOk(UniversalRestResponse response) {
        OAuthBodyEntity requestBody = (OAuthBodyEntity) response.getRequest().getRequestBody();

        String grantType =  UrlUtils.decode(requestBody.grantType, RestApiContract.CHARSET_NAME);
        String username =  UrlUtils.decode(requestBody.username, RestApiContract.CHARSET_NAME);

        OAuthToken token = (OAuthToken) response.getResponseEntity();
        sharedPreferencesController.saveUserName(username);
        sharedPreferencesController.saveOAuthToken(token);
    }

    @Override
    public void onMethodError(UniversalRestResponse response) {
        sharedPreferencesController.clearToken();
    }
}
