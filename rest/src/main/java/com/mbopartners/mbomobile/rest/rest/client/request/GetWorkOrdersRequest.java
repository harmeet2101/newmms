package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class GetWorkOrdersRequest extends AbstractRestRequest<EmptyRequestEntity> {
    public GetWorkOrdersRequest(String baseUrl) {
        super(RestApiContract.Method.getWorkOrdersList, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
