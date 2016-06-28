package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.Dashboard;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.request.DashboardsRequest;

import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class DashboardsDbHandler extends MboDbHandler<DashboardsRequest, Dashboard, RestServerApiException> {
    private static final String TAG = GetWorkOrdersDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(DashboardsRequest request) {
    }

    @Override
    public void handleSuccess(Dashboard dashboard) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if(dashboard != null) {
            try {
                DbFiller.clearTablesForDashboard(daoSession);
                DbFiller.insertAllDashboards(dashboard, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
    }

}
