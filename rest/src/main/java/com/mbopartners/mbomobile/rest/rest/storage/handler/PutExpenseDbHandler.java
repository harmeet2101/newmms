package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.request.PutExpenseRequest;

public class PutExpenseDbHandler extends MboDbHandler<PutExpenseRequest, Expense, RestServerApiException>{
    private static final String TAG = PutExpenseDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(PutExpenseRequest request) {

    }

    @Override
    public void handleSuccess(Expense expense) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if (expense != null) {
            try {
                DbFiller.updateExpense_Cascaded(expense, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {

    }
}
