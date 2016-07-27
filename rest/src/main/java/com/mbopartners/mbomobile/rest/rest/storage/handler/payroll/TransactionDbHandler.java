package com.mbopartners.mbomobile.rest.rest.storage.handler.payroll;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.model.response.payroll_response.PayrollTransactions;
import com.mbopartners.mbomobile.rest.rest.client.request.payroll.PayrollTransactionRequest;
import com.mbopartners.mbomobile.rest.rest.storage.handler.MboDbHandler;

/**
 * Created by MboAdil on 25/7/16.
 */
public class TransactionDbHandler extends MboDbHandler<PayrollTransactionRequest
        , PayrollTransactions[], RestServerApiException> {
    private static final String TAG = TransactionDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(PayrollTransactionRequest request) {
    }

    @Override
    public void handleSuccess(PayrollTransactions[] payrollTransactions) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if(payrollTransactions != null) {
            try {
                DbFiller.clearTablesForPayrollTransaction(daoSession);
                DbFiller.insertAllPayrollTransactionsFields(payrollTransactions,daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
    }

}

