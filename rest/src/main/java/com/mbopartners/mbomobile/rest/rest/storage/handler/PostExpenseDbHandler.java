package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.request.PostExpenseRequest;

public class PostExpenseDbHandler extends MboDbHandler<PostExpenseRequest, Expense, RestServerApiException> {
    private static final String TAG = PostExpenseDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(PostExpenseRequest request) {

    }

    @Override
    public void handleSuccess(Expense expense) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if (expense != null) {
            try {
                DbFiller.insertExpense(expense, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
        RestServerApiException hmmm = restServerApiException;
    }
}
