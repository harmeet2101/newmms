package com.mbopartners.mbomobile.ui.activity.dashboard;


import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;

public class DataLoadingDispatcher {

    private SharedPreferencesController sharedPreferencesController;
    private boolean loadingInProgress;

    public DataLoadingDispatcher(SharedPreferencesController sharedPreferencesController) {
        this.sharedPreferencesController = sharedPreferencesController;
        loadingInProgress = false;
    }

    public void notifyNeedDataReload() {
        sharedPreferencesController.setFlagLocalDbNeedToUpdateData();
    }

    public void notifyDadaLoadingStarted() {
        loadingInProgress = true;
    }

    public void notifyDataLoaded() {
        sharedPreferencesController.setFlagLocalDbHasActualData();
        loadingInProgress = false;
    }
    public void notifyDataLoadingFailed() {
        sharedPreferencesController.setFlagLocalDbNeedToUpdateData();
        loadingInProgress = false;
    }

    public boolean isNeedToLoadData() {
        return sharedPreferencesController.isLocalDbNeedsToLoadData();
    }

    public boolean isLoadingInProgress() {
        return loadingInProgress;
    }

    public boolean isFirstTimeLaunch()
    {
        return sharedPreferencesController.isFirstTimeLaunch();
    }

    public void setFlagFirstTimeLaunch()
    {
        sharedPreferencesController.setFlagFirstTimeLaunch();
    }

    public void setFlagFirstTimeLaunch_logout()
    {
        sharedPreferencesController.setFlagFirstTimeLaunch_logout();
    }
}
