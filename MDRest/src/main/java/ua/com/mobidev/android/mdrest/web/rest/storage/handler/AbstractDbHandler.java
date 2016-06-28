package ua.com.mobidev.android.mdrest.web.rest.storage.handler;

import android.content.Context;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

public abstract class AbstractDbHandler<Type_Request extends AbstractRestRequest, Type_ResponseEntity, Type_ErrorEntity> {

    public static final int REC_STATE__NORMAL = 0;

    public static final int REC_STATE__CREATE__REGISTERED = 101;
    public static final int REC_STATE__CREATE__SUCCESS = 102;
    public static final int REC_STATE__CREATE__ERROR = 103;

    public static final int REC_STATE__READ__REGISTERED = 201;
    public static final int REC_STATE__READ__SUCCESS = 202;
    public static final int REC_STATE__READ__ERROR = 203;

    public static final int REC_STATE__UPDATE__REGISTERED = 301;
    public static final int REC_STATE__UPDATE__SUCCESS = 302;
    public static final int REC_STATE__UPDATE__ERROR = 303;

    public static final int REC_STATE__DELETE__REGISTERED = 401;
    public static final int REC_STATE__DELETE__SUCCESS = 402;
    public static final int REC_STATE__DELETE__ERROR = 403;

    private Context context;
    private IApplicationControllersManager applicationControllersManager;

    public abstract void handleRegistration(Type_Request request);
    public abstract void handleSuccess(Type_ResponseEntity responseEntity);
    public abstract void handleError(Type_ErrorEntity restServerApiException);

    public void setEnvironment(Context context, IApplicationControllersManager applicationControllersManager) {
        this.context = context;
        this.applicationControllersManager = applicationControllersManager;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IApplicationControllersManager getApplicationControllersManager() {
        return applicationControllersManager;
    }

    public void setApplicationControllersManager(IApplicationControllersManager applicationControllersManager) {
        this.applicationControllersManager = applicationControllersManager;
    }
}
