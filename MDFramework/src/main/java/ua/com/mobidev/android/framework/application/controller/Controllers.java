package ua.com.mobidev.android.framework.application.controller;

public class Controllers {
    public static final String CONTROLLER__INTERNET_CONNECTION_STATUS = "ConnectionController";
    public static final String CONTROLLER__REST_SERVICE_HELPER = "MDRestServiceHelper";

    // It may seem that the Processor is not needed as a service (Singleton)
    // But this method of access it allows us to introduce the dependence of the Service
    // This simplifies testing and mokirovanie мокирование
    public static final String CONTROLLER__REST_PROCESSOR = "MDRestProcessor";
    public static final String CONTROLLER__SHARED_PREFERENCES = "SharedPreferencesController";
    public static final String CONTROLLER__REST_PRE_STORAGE = "PreStorageController";
    public static final String CONTROLLER__DATABASE = "SQLite database access controller";
    public static final String CONTROLLER__OAUTH = "oAuth 2.0 controller";

    public static final String CONTROLLER__APPLICATION_CONFIGURATION = "Application configuration controller";
//    public static final String MY_CONTROLLER__APPLICATION = "my controller";
}
