package com.mbopartners.mbomobile.rest.rest.storage.handler.payroll;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollPreviews;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.PreviewsRequest;
import com.mbopartners.mbomobile.rest.rest.storage.handler.MboDbHandler;

public class PreviewsDbHandler extends MboDbHandler<PreviewsRequest
        , PayrollPreviews, RestServerApiException> {




    private static final String TAG = PreviewsDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(PreviewsRequest request) {
    }

    @Override
    public void handleSuccess(PayrollPreviews payrollTransactions) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        /*if(payrollTransactions != null) {
            try {
                DbFiller.clearTablesForPayrollTransaction(daoSession);
                DbFiller.insertAllPayrollTransactionsFields(payrollTransactions,daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }*/
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
    }
}
