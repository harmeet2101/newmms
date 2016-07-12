package com.mbopartners.mbomobile.rest.rest.storage.handler.payroll;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollSummary;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.PayrollSummaryRequest;
import com.mbopartners.mbomobile.rest.rest.storage.handler.MboDbHandler;

/**
 * Created by MboAdil on 7/7/16.
 */
public class SummaryDbHandler   extends MboDbHandler<PayrollSummaryRequest, PayrollSummary, RestServerApiException> {
    private static final String TAG = SummaryDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(PayrollSummaryRequest request) {
    }

    @Override
    public void handleSuccess(PayrollSummary payrollSummary) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if(payrollSummary != null) {
            try {
                DbFiller.clearTablesForPayrollSummary(daoSession);
                DbFiller.insertAllPayrollSummaryFields(payrollSummary,daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
    }

}