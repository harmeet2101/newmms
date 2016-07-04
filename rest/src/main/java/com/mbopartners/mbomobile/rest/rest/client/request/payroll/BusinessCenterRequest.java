package com.mbopartners.mbomobile.rest.rest.client.request.payroll;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

/**
 * Created by MboAdil on 4/7/16.
 */
public class BusinessCenterRequest extends AbstractRestRequest<EmptyRequestEntity> {
    public BusinessCenterRequest(String baseUrl) {
        super(RestApiContract.Method.getBusinessCenterList, baseUrl);
    }

    @Override
    public String getParsedUrl() {
        return null;
    }
}
