package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.model.response.TimeEntry;
import com.mbopartners.mbomobile.rest.rest.client.request.SaveTimeEntriesRequest;

public class SaveTimeEntriesDbHandler extends MboDbHandler<SaveTimeEntriesRequest, TimeEntry[], RestServerApiException> {
    private static final String TAG = SaveTimeEntriesDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(SaveTimeEntriesRequest request) {
        String mboWorkOrderId = request.getWorkOrderId();
        String mboPeriodId = request.getPeriodId();

        DaoSession daoSession = getDbAccessController().getDaoSession();
        if (mboWorkOrderId != null && mboPeriodId != null) {
            try {
                DbFiller.markTimeEntriesForUpdate(mboWorkOrderId, mboPeriodId, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to register DB operation");
            }
        }
    }

    @Override
        public void handleSuccess(TimeEntry[] timeEntries) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if (timeEntries != null) {
            try {
                DbFiller.replaceTimeEntriesToUpdate(timeEntries, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }
        }

    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {
        DaoSession daoSession = getDbAccessController().getDaoSession();
        DbFiller.unMarkTimeEntriesForUpdate(daoSession);
    }
}
