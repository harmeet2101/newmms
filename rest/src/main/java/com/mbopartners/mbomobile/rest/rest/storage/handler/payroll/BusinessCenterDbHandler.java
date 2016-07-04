package com.mbopartners.mbomobile.rest.rest.storage.handler.payroll;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.BusinessCenter;
import com.mbopartners.mbomobile.rest.rest.client.request.DashboardsRequest;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.BusinessCenterRequest;
import com.mbopartners.mbomobile.rest.rest.storage.handler.MboDbHandler;

/**
 * Created by MboAdil on 4/7/16.
 */
public class BusinessCenterDbHandler  extends MboDbHandler<BusinessCenterRequest, BusinessCenter[], RestServerApiException> {
    private static final String TAG = BusinessCenterDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(BusinessCenterRequest request) {
    }

    @Override
    public void handleSuccess(BusinessCenter[] businessCenters) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if(businessCenters != null) {
            try {
                DbFiller.clearTablesForBusinessCenter(daoSession);
                DbFiller.insertAllBusinessCenter(businessCenters, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
    }

}