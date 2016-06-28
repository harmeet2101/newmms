package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.ExpenseType;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.request.GetExpenseTypesRequest;

import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class GetExpenseTypesDbHandler extends MboDbHandler<GetExpenseTypesRequest, ExpenseType[], RestServerApiException> {
    private static final String TAG = GetExpenseTypesDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(GetExpenseTypesRequest request) {

    }

    @Override
    public void handleSuccess(ExpenseType[] expenseTypes) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if(expenseTypes != null) {
            try {
                DbFiller.clearTablesForExpenses(daoSession);
                DbFiller.insertAllExpenseTypes(expenseTypes, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {

    }
}
