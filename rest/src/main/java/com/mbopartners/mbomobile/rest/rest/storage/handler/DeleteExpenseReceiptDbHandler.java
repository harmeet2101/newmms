package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.EmptyResponseEntity;
import com.mbopartners.mbomobile.rest.model.response.Receipt;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.rest.client.request.DeleteExpenseReceiptRequest;

public class DeleteExpenseReceiptDbHandler extends MboDbHandler<DeleteExpenseReceiptRequest, EmptyResponseEntity, RestServerApiException> {
    private static final String TAG = DeleteExpenseReceiptDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(DeleteExpenseReceiptRequest request) {
        String mboExpenseId = request.getMboExpenseId();
        String receiptFilename = request.getReceiptFilename();

        DaoSession daoSession = getDbAccessController().getDaoSession();
        if (mboExpenseId != null && receiptFilename != null) {
            try {
                DbFiller.markReceiptsToDelete(mboExpenseId, receiptFilename, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to register DB operation");
            }
        }
    }

    @Override
    public void handleSuccess(EmptyResponseEntity entity) {
        DaoSession daoSession = getDbAccessController().getDaoSession();
        DbFiller.deleteMarkedReceipt(daoSession);
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
        DaoSession daoSession = getDbAccessController().getDaoSession();
        DbFiller.markReceiptsToCreated(daoSession);
    }
}
