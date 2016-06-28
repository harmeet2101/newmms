package com.mbopartners.mbomobile.data.db.database.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mbopartners.mbomobile.data.db.generated.dao.DaoMaster;
import com.mbopartners.mbomobile.data.db.generated.dao.DaoSession;

import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;
import ua.com.mobidev.android.framework.application.controller.Controllers;


public class DbAccessController extends AbstractApplicationController {
    private DaoSession daoSession;

    public DbAccessController(Context applicationContext, String sqliteDbName) {
        super(Controllers.CONTROLLER__DATABASE);

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(applicationContext, sqliteDbName, null);
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    @Override
    public boolean onStart() {
        return false;
    }

    @Override
    public boolean onPause() {
        return false;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
