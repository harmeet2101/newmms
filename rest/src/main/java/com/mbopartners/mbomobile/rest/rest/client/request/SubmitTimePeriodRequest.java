package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.param.EmptyRequestEntity;
import com.mbopartners.mbomobile.rest.rest.storage.handler.SubmitTimePeriodDbHandler;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class SubmitTimePeriodRequest extends AbstractRestRequest<EmptyRequestEntity> {
    private final String workOrderId;
    private final String periodId;


    public SubmitTimePeriodRequest(String baseUrl, String workOrderId, String periodId) {
        super(RestApiContract.Method.submitTimePeriod, baseUrl);
        this.workOrderId = workOrderId;
        this.periodId = periodId;
    }

    @Override
    public String getParsedUrl() {
        String serverUrl = this.getRequestDescriptor().getRestMethod().getUrl();
        serverUrl = serverUrl.replace(RestApiContract.Key.WORK_ORDER_ID, workOrderId);
        serverUrl = serverUrl.replace(RestApiContract.Key.TIME_PERIOD_ID, periodId);
        return serverUrl;
    }
}
