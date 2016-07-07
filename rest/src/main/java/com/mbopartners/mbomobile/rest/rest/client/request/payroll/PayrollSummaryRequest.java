package com.mbopartners.mbomobile.rest.rest.client.request.payroll;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

/**
 * Created by MboAdil on 7/7/16.
 */
public class PayrollSummaryRequest extends AbstractRestRequest<EmptyRequestEntity> {

    public PayrollSummaryRequest(String baseUrl) {
        super(RestApiContract.Method.getPayrollSummary, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}

