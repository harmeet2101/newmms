package com.mbopartners.mbomobile.rest.rest.client.request;

import android.util.Base64;

import com.mbopartners.mbomobile.rest.model.param.OAuthBodyEntity;

import ua.com.mobidev.android.framework.util.UrlUtils;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class OAuthAuthenticateRequest extends AbstractRestRequest<OAuthBodyEntity> {

    public OAuthAuthenticateRequest(OAuthBodyEntity params, String baseUrl) {
        super(RestApiContract.Method.oAuth, baseUrl);

        params.username = params.username == null ? null : UrlUtils.encode(params.username, RestApiContract.CHARSET_NAME);
        params.password = params.password == null ? null : UrlUtils.encode(params.password, RestApiContract.CHARSET_NAME);
        params.grantType = params.grantType == null ? null : UrlUtils.encode(params.grantType, RestApiContract.CHARSET_NAME);
        params.refreshToken = params.refreshToken == null ? null : UrlUtils.encode(params.refreshToken, RestApiContract.CHARSET_NAME);

        setRequestBody(params);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
