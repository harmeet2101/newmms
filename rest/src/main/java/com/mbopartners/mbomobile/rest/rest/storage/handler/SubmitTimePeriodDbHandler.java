package com.mbopartners.mbomobile.rest.rest.storage.handler;

import com.mbopartners.mbomobile.rest.model.response.EmptyResponseEntity;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.request.SubmitTimePeriodRequest;

public class SubmitTimePeriodDbHandler extends MboDbHandler<SubmitTimePeriodRequest, EmptyResponseEntity, RestServerApiException> {

    @Override
    public void handleRegistration(SubmitTimePeriodRequest request) {

    }

    @Override
    public void handleSuccess(EmptyResponseEntity emptyResponseEntity) {

    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {

    }
}
