package ua.com.mobidev.android.framework.application;

import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;

/**
 *
 */
public interface IApplicationControllersManager {

    /**
     * Instantiate controllers.
     * Put them into collection.
     */
    void createControllers();

    /**
     * Eventually sends "Start" signal to each controller.
     */
    void startControllers();

    /**
     * Destroy each controller.
     * Remove controllers from collection.
     */
    void stopControllers();

    AbstractApplicationController getController(String serviceName);
}
