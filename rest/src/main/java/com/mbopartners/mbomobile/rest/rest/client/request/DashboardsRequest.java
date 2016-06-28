package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class DashboardsRequest extends AbstractRestRequest<EmptyRequestEntity> {
    public DashboardsRequest(String baseUrl) {
        super(RestApiContract.Method.getDashboards, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
