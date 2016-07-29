package com.mbopartners.mbomobile.ui.util;

import android.app.Application;
import android.content.Context;

import com.mbopartners.mbomobile.data.db.database.controller.DbAccessController;
import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;
import com.mbopartners.mbomobile.rest.model.DbFiller;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.IRestClient;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.processor.IHttpRequestProcessor;

public class Security {
    public static void doLogoutRoutine(Application application) {
        clearHttpClientQueue(application);
        clearHttpClientCallbacks(application);
        clearUserToken(application);
        DbAccessController dbAccessController = getDbAccessController(application);
        DaoSession daoSession = dbAccessController.getDaoSession();
        clearAllDbTables(daoSession);
    }

    private static void clearAllDbTables(DaoSession daoSession) {

        DbFiller.clearTablesForDashboard(daoSession);
        DbFiller.clearTablesForPayrollSummary(daoSession);
        DbFiller.clearTablesForBusinessCenter(daoSession);
        DbFiller.clearTablesForExpenses(daoSession);
        DbFiller.clearTablesForPayrollTransaction(daoSession);
        DbFiller.clearTablesForWorkOrders(daoSession);
    }

    public static DbAccessController getDbAccessController(Application application) {
        IApplicationControllersManager applicationControllersManager = (IApplicationControllersManager) application;
        DbAccessController dbAccessController = (DbAccessController) applicationControllersManager.getController(Controllers.CONTROLLER__DATABASE);
        return dbAccessController;
    }

    public static void doHardLogoutRoutine(Application application) {
        doLogoutRoutine(application);
        clearUserAccount(application);
    }


    public static void clearHttpClientQueue(Application application) {
        IApplicationControllersManager acMan = (IApplicationControllersManager) application;
        IHttpRequestProcessor httpRequestProcessor =
                (IHttpRequestProcessor) acMan.getController(Controllers.CONTROLLER__REST_PROCESSOR);
        httpRequestProcessor.cancelAllRequests();
    }

    public static void clearHttpClientCallbacks(Application application) {
        IApplicationControllersManager acMan = (IApplicationControllersManager) application;
        IRestClient restServiceHelper = (IRestClient) acMan.getController(Controllers.CONTROLLER__REST_SERVICE_HELPER);
        restServiceHelper.clearCallbacks();
    }

    public static void invalidateDb(Application application) {
        IApplicationControllersManager acMan = (IApplicationControllersManager) application;
        SharedPreferencesController sharedPreferencesController =
                (SharedPreferencesController) acMan.getController(Controllers.CONTROLLER__SHARED_PREFERENCES);
        sharedPreferencesController.setFlagLocalDbNeedToUpdateData();
    }

    public static void clearUserToken(Application application) {
        IApplicationControllersManager acMan = (IApplicationControllersManager) application;
        SharedPreferencesController sharedPreferencesController =
                (SharedPreferencesController) acMan.getController(Controllers.CONTROLLER__SHARED_PREFERENCES);
        sharedPreferencesController.clearToken();
    }
    public static void clearUserAccount(Application application) {
        IApplicationControllersManager acMan = (IApplicationControllersManager) application;
        SharedPreferencesController sharedPreferencesController =
                (SharedPreferencesController) acMan.getController(Controllers.CONTROLLER__SHARED_PREFERENCES);
        sharedPreferencesController.logout();
    }
}
