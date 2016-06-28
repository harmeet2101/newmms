package com.mbopartners.mbomobile.rest.rest.storage.handler;

import android.util.Log;

import com.mbopartners.mbomobile.data.db.database.controller.DbAccessController;
import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.model.response.RestServerApiException;
import com.mbopartners.mbomobile.rest.model.response.UserProfile;
import com.mbopartners.mbomobile.rest.rest.client.request.GetUserProfileRequest;

public class GetUserProfileDbHandler extends MboDbHandler<GetUserProfileRequest, UserProfile, RestServerApiException> {
    private static final String TAG = GetUserProfileDbHandler.class.getSimpleName();

    @Override
    public void handleRegistration(GetUserProfileRequest request) {

    }

    @Override
    public void handleSuccess(UserProfile userProfile) {
        DaoSession daoSession = getDbAccessController().getDaoSession();

        if(userProfile != null) {
            try {
                DbFiller.updateUserProfile(userProfile, daoSession);
            } catch (Exception e){
                Log.e(TAG, "Failed to save to DB.");
            }

        }
    }

    @Override
    public void handleError(RestServerApiException restServerApiException) {

    }
}
