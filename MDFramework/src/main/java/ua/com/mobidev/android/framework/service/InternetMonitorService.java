package ua.com.mobidev.android.framework.service;

import android.app.IntentService;
import android.content.Intent;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.ConnectionController;
import ua.com.mobidev.android.framework.application.controller.Controllers;

/**
 *
 */
public class InternetMonitorService extends IntentService {

    public InternetMonitorService() {
        super(InternetMonitorService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Get state and serviceType from intent
        // TODO
        // TODO
        IApplicationControllersManager applicationService = (IApplicationControllersManager) this.getApplication();
        ConnectionController connectionController = (ConnectionController) applicationService.getController(Controllers.CONTROLLER__INTERNET_CONNECTION_STATUS);
        connectionController.wakeUp();
    }
}
