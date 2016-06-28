package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class GetExpensesRequest extends AbstractRestRequest<EmptyRequestEntity> {

    public GetExpensesRequest(String baseUrl) {
        super(RestApiContract.Method.getExpensesList, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
