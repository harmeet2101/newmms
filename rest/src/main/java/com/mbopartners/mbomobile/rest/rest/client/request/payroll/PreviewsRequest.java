package com.mbopartners.mbomobile.rest.rest.client.request.payroll;

import com.mbopartners.mbomobile.rest.model.param.PayrollPreviewsEntity;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public class PreviewsRequest extends AbstractRestRequest<PayrollPreviewsEntity> {


    public PreviewsRequest(String baseUrl) {
        super(RestApiContract.Method.getPayrollPreviews, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
