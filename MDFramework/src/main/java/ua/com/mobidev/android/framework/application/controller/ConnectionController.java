package ua.com.mobidev.android.framework.application.controller;

/**
 *
 */
public class ConnectionController extends AbstractApplicationController {

    public ConnectionController() {
        super(Controllers.CONTROLLER__INTERNET_CONNECTION_STATUS);
    }

    public void wakeUp() {

    }

    @Override
    public boolean onStart() {
        return false;
    }

    @Override
    public boolean onPause() {
        return false;
    }
}
