package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

/**
 *
 */
public class GetExpenseTypesRequest extends AbstractRestRequest<EmptyRequestEntity> {
    public GetExpenseTypesRequest(String baseUrl) {
        super(RestApiContract.Method.getExpenseTypesList, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
