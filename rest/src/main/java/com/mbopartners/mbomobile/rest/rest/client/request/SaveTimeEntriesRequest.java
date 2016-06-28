package com.mbopartners.mbomobile.rest.rest.client.request;

import com.mbopartners.mbomobile.rest.model.response.EmptyResponseEntity;
import com.mbopartners.mbomobile.rest.model.response.TimeEntry;
import com.mbopartners.mbomobile.rest.rest.storage.handler.SaveTimeEntriesDbHandler;

import java.util.Arrays;
import java.util.List;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class SaveTimeEntriesRequest extends AbstractRestRequest<TimeEntry[]> {
    private final String workOrderId;
    private final String periodId;

    public SaveTimeEntriesRequest(String baseUrl, String workOrderId, String periodId, List<TimeEntry> timeEntries) {
        super(RestApiContract.Method.saveTimeEntries, baseUrl);
        TimeEntry[] timeEntriesArray = new TimeEntry[timeEntries.size()];
        this.workOrderId = workOrderId;
        this.periodId = periodId;
        timeEntriesArray = timeEntries.toArray(timeEntriesArray);
        this.setRequestBody(timeEntriesArray);
    }

    @Override
    public String getParsedUrl() {
        String parsedUrl = this.getRequestDescriptor().getRestMethod().getUrl();
        parsedUrl = parsedUrl.replace(RestApiContract.Key.WORK_ORDER_ID, workOrderId);
        parsedUrl = parsedUrl.replace(RestApiContract.Key.TIME_PERIOD_ID, periodId);
        return parsedUrl;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public String getPeriodId() {
        return periodId;
    }
}
