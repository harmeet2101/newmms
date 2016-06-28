package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.request.GetExpensesRequest;

import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class GetExpensesDbHandler extends MboDbHandler<GetExpensesRequest, Expense[], RestServerApiException> {
    private static final String TAG = GetExpensesDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(GetExpensesRequest request) {
        Log.d(TAG, "GetExpensesRequest registration : " + request.getRequestDescriptor().getRequestId());
    }

    @Override
    public void handleSuccess(Expense[] expenses) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if(expenses != null) {
            try {
                DbFiller.insertAllExpenses(expenses, daoSession);
                Log.d(TAG, "Expenses ADDED to DB. " + expenses.length + "items");
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {

    }
}
