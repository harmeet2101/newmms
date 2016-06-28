package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.data.db.generated.dao.TableExpenseDao;
import com.mbopartners.mbomobile.data.db.generated.model.TableExpense;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.Receipt;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.request.PostReceiptRequest;

public class PostReceiptDbHandler extends MboDbHandler<PostReceiptRequest, Receipt, RestServerApiException> {
private static final String TAG = PostReceiptDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(PostReceiptRequest request) {
    }

    @Override
    public void handleSuccess(Receipt receipt) {

        if (receipt != null) {
            try {
                saveReceipt(receipt);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }

        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {

    }

    private void saveReceipt(Receipt receipt) {
        DaoSession daoSession = getDbAccessController().getDaoSession();
        TableExpenseDao expenseDao = daoSession.getTableExpenseDao();
        TableExpense expense = expenseDao.queryBuilder()
                .where(TableExpenseDao.Properties.MboId.eq(receipt.getMboExpenseId()))
                .unique();

        if (expense != null) {
            long id = DbFiller.insertReceipt(expense.getId(), receipt, daoSession);
            Log.w(TAG, "Receipt added with ID = " + id + ", WO id = " + receipt.getMboExpenseId());
        } else {
            Log.w(TAG, "Unable to find Expense with MboId = " + receipt.getMboExpenseId());
        }
    }
}
