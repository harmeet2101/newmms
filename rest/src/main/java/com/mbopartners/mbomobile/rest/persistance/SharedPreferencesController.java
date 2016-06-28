package com.mbopartners.mbomobile.rest.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.mbopartners.mbomobile.rest.model.response.oauth.OAuthToken;

import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;
import ua.com.mobidev.android.framework.application.controller.Controllers;

public class SharedPreferencesController extends AbstractApplicationController{

    private static final String KEY_USER_LOGIN_NAME = "UserLogin";

    private static final String KEY_OAUTH_ACCESS_TOKEN = "access_token";
    private static final String KEY_OAUTH_EXPIRES_IN = "expires_in";
    private static final String KEY_OAUTH_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_OAUTH_REFRESH_EXPIRES_IN = "refresh_expires_in";
    private static final String KEY_OAUTH_REFRESH_TOKEN_TIMESTAMP = "Timestamp when this token was received";

    private static final String KEY_APPLICATION_ENVIRONMENT_CONFIGURATION = "Application configuration";
    private static final String KEY_LOCAL_DB_NEEDS_TO_LOAD_DATA = "If local DB does not have any data or existing data probably changed on Server";

    private static final String EMPTY_STRING = "";
    private static final String EMPTY_USER_LOGIN_NAME = "";
    private static final String EMPTY_USER_LOGIN_TOKEN = null;
    private static final String FIRST_TIME_LAUNCH="firstTimeLaunch";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesController(Context context) {
        super(Controllers.CONTROLLER__SHARED_PREFERENCES);
        this.sharedPreferences = context.getSharedPreferences(null, Context.MODE_PRIVATE);
    }

    // TODO USE AS INTERFACE !!!

    @Override
    public boolean onStart() {
        return false;
    }

    @Override
    public boolean onPause() {
        return false;
    }

    //--------------------------------------------------------------------------------

    public void saveUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("User name should be not null and not empty");
        }

        sharedPreferences.edit().putString(KEY_USER_LOGIN_NAME, userName).apply();
    }

    public String  getUserName() {
        return sharedPreferences.getString(KEY_USER_LOGIN_NAME, EMPTY_USER_LOGIN_NAME);
    }

    public void saveOAuthToken(OAuthToken oAuthToken) {
        if (oAuthToken == null ||
                oAuthToken.getAccess_token().isEmpty() ||
                oAuthToken.getRefresh_token().isEmpty() ) {
            throw new IllegalArgumentException("User token should be not null and not empty");
        }

        sharedPreferences.edit().putString(KEY_OAUTH_ACCESS_TOKEN, oAuthToken.getAccess_token()).apply();
        sharedPreferences.edit().putInt(KEY_OAUTH_EXPIRES_IN, oAuthToken.getExpires_in()).apply();
        sharedPreferences.edit().putString(KEY_OAUTH_REFRESH_TOKEN, oAuthToken.getRefresh_token()).apply();
        sharedPreferences.edit().putInt(KEY_OAUTH_REFRESH_EXPIRES_IN, oAuthToken.getRefresh_expires_in()).apply();
        sharedPreferences.edit().putString(KEY_OAUTH_REFRESH_TOKEN_TIMESTAMP, Long.toString(oAuthToken.getTimeStamp())).apply();
    }

    public OAuthToken getOAuthToken() {
        OAuthToken oAuthToken = new OAuthToken(
            sharedPreferences.getString(KEY_OAUTH_ACCESS_TOKEN, null),
            sharedPreferences.getInt(KEY_OAUTH_EXPIRES_IN, 0),
            sharedPreferences.getString(KEY_OAUTH_REFRESH_TOKEN, null),
            sharedPreferences.getInt(KEY_OAUTH_REFRESH_EXPIRES_IN, 0),
            Long.parseLong(sharedPreferences.getString(KEY_OAUTH_REFRESH_TOKEN_TIMESTAMP, "0"))
        );
        return oAuthToken;
    }

    public boolean isUserLoggedIn() {
        OAuthToken oAuthToken = getOAuthToken();
        return (oAuthToken != null && oAuthToken.getAccess_token()!= null && oAuthToken.getRefresh_token() != null);
    }

    public void clearToken() {
        sharedPreferences.edit().remove(KEY_OAUTH_ACCESS_TOKEN).apply();
        sharedPreferences.edit().remove(KEY_OAUTH_EXPIRES_IN).apply();
        sharedPreferences.edit().remove(KEY_OAUTH_REFRESH_TOKEN).apply();
        sharedPreferences.edit().remove(KEY_OAUTH_REFRESH_EXPIRES_IN).apply();
    }

    public void logout() {
        clearToken();
        sharedPreferences.edit().remove(KEY_USER_LOGIN_NAME).apply();
    }

    public void saveEnvironmentConfiguration(String configuration) {
        sharedPreferences.edit().putString(KEY_APPLICATION_ENVIRONMENT_CONFIGURATION, configuration).apply();
    }

    public String getEnvironmentConfiguration() {
        return sharedPreferences.getString(KEY_APPLICATION_ENVIRONMENT_CONFIGURATION, EMPTY_STRING);
    }

    public boolean isLocalDbNeedsToLoadData() {
        return sharedPreferences.getBoolean(KEY_LOCAL_DB_NEEDS_TO_LOAD_DATA, true);
    }
    public void setFlagLocalDbHasActualData() {
        sharedPreferences.edit().putBoolean(KEY_LOCAL_DB_NEEDS_TO_LOAD_DATA, false).apply();
    }
    public void setFlagLocalDbNeedToUpdateData() {
        sharedPreferences.edit().putBoolean(KEY_LOCAL_DB_NEEDS_TO_LOAD_DATA, true).apply();
    }


    public boolean isFirstTimeLaunch()
    {
        return sharedPreferences.getBoolean(FIRST_TIME_LAUNCH,true);
    }
    public void setFlagFirstTimeLaunch()
    {
        sharedPreferences.edit().putBoolean(FIRST_TIME_LAUNCH,false).apply();
    }

    public void setFlagFirstTimeLaunch_logout()
    {
        sharedPreferences.edit().putBoolean(FIRST_TIME_LAUNCH,true).apply();
    }
}
