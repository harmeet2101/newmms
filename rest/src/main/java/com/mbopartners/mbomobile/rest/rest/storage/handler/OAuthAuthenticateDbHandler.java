package com.mbopartners.mbomobile.rest.rest.storage.handler;

import com.mbopartners.mbomobile.rest.model.response.oauth.OAuthApiException;
import com.mbopartners.mbomobile.rest.model.response.oauth.OAuthToken;
import com.mbopartners.mbomobile.rest.rest.client.request.OAuthAuthenticateRequest;

import ua.com.mobidev.android.mdrest.web.rest.storage.handler.AbstractDbHandler;

public class OAuthAuthenticateDbHandler extends AbstractDbHandler<OAuthAuthenticateRequest, OAuthToken, OAuthApiException> {

    @Override
    public void handleRegistration(OAuthAuthenticateRequest request) {

    }

    @Override
    public void handleSuccess(OAuthToken oAuthToken) {

    }

    @Override
    public void handleError(OAuthApiException restServerApiException) {

    }
}
