package com.mbopartners.mbomobile.rest.rest.storage.handler;

import com.mbopartners.mbomobile.data.db.database.controller.DbAccessController;

import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.storage.handler.AbstractDbHandler;

public abstract class MboDbHandler <Type_Request extends AbstractRestRequest, Type_ResponseEntity, Type_ErrorEntity>
        extends AbstractDbHandler<Type_Request , Type_ResponseEntity, Type_ErrorEntity> {

    public DbAccessController getDbAccessController() {
        DbAccessController dbAccessController = (DbAccessController) getApplicationControllersManager().getController(Controllers.CONTROLLER__DATABASE);
        return dbAccessController;
    }
}
