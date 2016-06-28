package com.mbopartners.mbomobile.app.application.controller.prestore;

import java.util.HashMap;
import java.util.Map;

import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.client.RestMethod;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;
import ua.com.mobidev.android.mdrest.web.rest.storage.IRestStore;
import ua.com.mobidev.android.mdrest.web.rest.storage.handler.AbstractDbHandler;

/**
 *
 */
public class PreStoreController extends AbstractApplicationController implements IRestStore {
    public static final String TAG = PreStoreController.class.getSimpleName();

    private IRestStore restStore;

    private Map<RestMethod, PreStoreListener> listeners = new HashMap<RestMethod, PreStoreListener>();

    public PreStoreController() {
        super(Controllers.CONTROLLER__REST_PRE_STORAGE);
    }

    @Override
    public boolean onStart() {
        return false;
    }

    @Override
    public boolean onPause() {
        return false;
    }

    //--------------------------------------------------------------------------------

    @Override
    public OperationResult registerRequest(AbstractRestRequest universalRestRequest) {
        RestMethod method = universalRestRequest.getRequestDescriptor().getRestMethod();
        PreStoreListener listener = listeners.get(method);
        if (listener != null) {
            listener.onMethodBefore(universalRestRequest);
        }
        return restStore.registerRequest(universalRestRequest);
    }

    @Override
    public OperationResult notifySuccess(UniversalRestResponse universalRestResponse) {
        RestMethod method = universalRestResponse.getRequest().getRequestDescriptor().getRestMethod();
        PreStoreListener listener = listeners.get(method);
        if (listener != null) {
            listener.onMethodOk(universalRestResponse);
        }
        return restStore.notifySuccess(universalRestResponse);
    }

    @Override
    public OperationResult notifyError(UniversalRestResponse universalRestResponse) {
        RestMethod method = universalRestResponse.getRequest().getRequestDescriptor().getRestMethod();
        PreStoreListener listener = listeners.get(method);
        if (listener != null) {
            listener.onMethodError(universalRestResponse);
        }
        return restStore.notifyError(universalRestResponse);
    }

    //--------------------------------------------------------------------------------

    public void setRestStore(IRestStore restStore) {
        this.restStore = restStore;
    }

    public <Type_Request extends AbstractRestRequest,
            Type_ParamEntity,
            Type_ResponseEntity,
            Type_ErrorEntity,
            Type_DbHandler extends AbstractDbHandler<Type_Request, Type_ResponseEntity, Type_ErrorEntity>
            > void addListener(RestMethod<Type_Request,
            Type_ParamEntity,
            Type_ResponseEntity,
            Type_ErrorEntity,
            Type_DbHandler> method, PreStoreListener listener) {
        listeners.put(method, listener);
    }

}
