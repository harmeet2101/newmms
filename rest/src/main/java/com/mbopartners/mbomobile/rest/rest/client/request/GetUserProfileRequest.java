package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class GetUserProfileRequest extends AbstractRestRequest<EmptyRequestEntity> {
    public GetUserProfileRequest(String baseUrl) {
        super(RestApiContract.Method.getUserProfile, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
