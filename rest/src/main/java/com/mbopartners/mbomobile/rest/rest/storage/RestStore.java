package com.mbopartners.mbomobile.rest.rest.storage;

import android.content.Context;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;
import ua.com.mobidev.android.mdrest.web.rest.storage.IRestStore;
import ua.com.mobidev.android.mdrest.web.rest.storage.handler.AbstractDbHandler;

public class RestStore implements IRestStore {
    public static final String TAG = RestStore.class.getSimpleName();

    private Context context;
    private  IApplicationControllersManager applicationControllersManager;

    public RestStore(Context context, IApplicationControllersManager applicationControllersManager) {
        this.context = context;
        this.applicationControllersManager = applicationControllersManager;
    }

    @Override
    public OperationResult registerRequest(AbstractRestRequest request) {
        AbstractDbHandler dbHandler = request.getRequestDescriptor().getRestMethod().getDbHandler();
        dbHandler.setEnvironment(this.context, this.applicationControllersManager);
        dbHandler.handleRegistration(request);
        return null;
    }

    @Override
    public OperationResult notifySuccess(UniversalRestResponse universalRestResponse) {
        AbstractDbHandler dbHandler = universalRestResponse.getRequest().getRequestDescriptor().getRestMethod().getDbHandler();
        dbHandler.setEnvironment(this.context, this.applicationControllersManager);
        dbHandler.handleSuccess(universalRestResponse.getResponseEntity());
        return null;
    }

    @Override
    public OperationResult notifyError(UniversalRestResponse universalRestResponse) {
        AbstractDbHandler dbHandler = universalRestResponse.getRequest().getRequestDescriptor().getRestMethod().getDbHandler();
        dbHandler.setEnvironment(this.context, this.applicationControllersManager);
        dbHandler.handleError(universalRestResponse.getResponseEntity());
        return null;
    }
}
