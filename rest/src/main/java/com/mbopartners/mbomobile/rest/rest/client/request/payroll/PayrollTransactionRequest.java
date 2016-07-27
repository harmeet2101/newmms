package com.mbopartners.mbomobile.rest.rest.client.request.payroll;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

/**
 * Created by MboAdil on 25/7/16.
 */
public class PayrollTransactionRequest extends AbstractRestRequest<EmptyRequestEntity> {

    public PayrollTransactionRequest(String baseUrl) {
        super(RestApiContract.Method.getPayrollTransactions, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
