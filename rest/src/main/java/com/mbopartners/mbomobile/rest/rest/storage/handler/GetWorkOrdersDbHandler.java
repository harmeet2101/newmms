package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.Converter;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.model.response.WorkOrder;
import com.mbopartners.mbomobile.rest.rest.client.request.GetWorkOrdersRequest;

import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class GetWorkOrdersDbHandler extends MboDbHandler<GetWorkOrdersRequest, WorkOrder[], RestServerApiException> {
    private static final String TAG = GetWorkOrdersDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(GetWorkOrdersRequest request) {
    }

    @Override
    public void handleSuccess(WorkOrder[] workOrders) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if(workOrders != null) {
            try {
                DbFiller.clearTablesForWorkOrders(daoSession);
                DbFiller.completeTimeEntriesWithVirtual(workOrders);
                DbFiller.insertAllWorkOrders(workOrders, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.", e);
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
        int i=1;
    }

}














