package com.mbopartners.mbomobile.app.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.artisan.application.ArtisanRegisteredApplication;
import com.artisan.incodeapi.ArtisanExperimentManager;
import com.artisan.incodeapi.ArtisanProfileManager;
import com.artisan.manager.ArtisanManager;
import com.artisan.powerhooks.PowerHookManager;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.mbopartners.mbomobile.app.application.controller.prestore.OauthAuthenticateListener;
import com.mbopartners.mbomobile.app.application.controller.prestore.PreStoreController;
import com.mbopartners.mbomobile.data.db.database.controller.DbAccessController;
import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.RestServiceHelper;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;
import com.mbopartners.mbomobile.rest.rest.method.volley_impl.VolleyGsonRequestBuilder;
import com.mbopartners.mbomobile.rest.rest.storage.RestStore;
import com.mbopartners.mbomobile.ui.activity.joinus.JoinUsActivity;
import com.mbopartners.mbomobile.ui.activity.login.LoginActivity;
import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;
import com.mbopartners.mbomobile.ui.activity.splash.SplashActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.fabric.sdk.android.Fabric;
import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;
import ua.com.mobidev.android.framework.application.controller.ConnectionController;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.method.IRestHttpClient;
import ua.com.mobidev.android.mdrest.web.rest.processor.Processor;
import ua.com.mobidev.android.mdrest.web.rest.storage.IRestStore;

public class MBOMobileApplication extends Application
        implements IApplicationControllersManager,
        ArtisanRegisteredApplication {

    public static final String ARTISAN_APP_ID_PROD = "5589a82c4ef4a7fd51000002";                          //5589a82c4ef4a7fd51000002
    public static final String ARTISAN_APP_ID_PRE_PROD = "556dca7b4ef4a7b261000001";
    public static final String SQLITE_DB_NAME = "mbo_mobile_android";
    public static String config;
    public static boolean prod_pre_prod_flag;
    private SharedPreferences preferences;

    //public static String SYSTEM_LOCALE;
    @Override
    public void onCreate() {

        super.onCreate();

        preferences = getSharedPreferences("environment_pref", Context.MODE_PRIVATE);
        String env = preferences.getString("env", "prod");
        if (env.equalsIgnoreCase("prod")) {
            ArtisanManager.startArtisan(this, ARTISAN_APP_ID_PROD);
            Crashlytics crashlyticsKit = new Crashlytics.Builder()
                    .core(new CrashlyticsCore.Builder().disabled(false).build()).build();
            Fabric.with(this, crashlyticsKit);
        } else {

            ArtisanManager.startArtisan(this, ARTISAN_APP_ID_PRE_PROD);

            Crashlytics crashlyticsKit = new Crashlytics.Builder()
                    .core(new CrashlyticsCore.Builder().disabled(true).build()).build();
            Fabric.with(this, crashlyticsKit);
        }
        /**
         * App PIN lock initialization
         */
        AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);
        if (AppLockManager.getInstance().isAppLockFeatureEnabled()) {
            AppLockManager.getInstance().getCurrentAppLock().setDisabledActivities(
                    new String[]{
                            SplashActivity.class.getName(),
                            LoginActivity.class.getName(),
                            JoinUsActivity.class.getName()
                    });
        }
        // Initialize the singletons so their instances
        // are bound to the application process.
        createControllers();
    }

    private AbstractApplicationController assembleProcessor(ConfigurationController configurationController) {
        IRestHttpClient restHttpClient;
        SharedPreferencesController sharedPreferencesController = (SharedPreferencesController) getController(Controllers.CONTROLLER__SHARED_PREFERENCES);
        PreStoreController preStoreController = assemblePreStoreController(sharedPreferencesController);


        IRestStore restStore;
        Processor processor;

        restHttpClient = new VolleyGsonRequestBuilder(this, sharedPreferencesController, configurationController);
//        restHttpClient = new StubRestMethodImpl(this);
        restStore = new RestStore((Context) this, (IApplicationControllersManager) this);
        processor = new Processor();

        processor.setRestHttpClient(restHttpClient);

        processor.setRestStore(preStoreController);

        preStoreController.setRestStore(restStore);

        restHttpClient.setHttpClientResponseProcessor(processor);
        return processor;
    }

    private PreStoreController assemblePreStoreController(SharedPreferencesController sharedPreferencesController) {
        PreStoreController preStoreController = (PreStoreController) getController(Controllers.CONTROLLER__REST_PRE_STORAGE);
        OauthAuthenticateListener oauthAuthenticateListener = new OauthAuthenticateListener(sharedPreferencesController);
        preStoreController.addListener(RestApiContract.Method.oAuth, oauthAuthenticateListener);
        return preStoreController;
    }

    //================================================================================
    //
    // Work with application Controllers here
    //
    //================================================================================

    private Map<String, AbstractApplicationController> controllers = new LinkedHashMap<>();

    // --------------------------------------------------------------------------------

    @Override
    public AbstractApplicationController getController(String serviceName) {
        AbstractApplicationController controller = controllers.get(serviceName);
        if (controller == null) {
            throw new IllegalArgumentException("Unknown application-controller name : '" + serviceName + "'");
        }
        return controller;
    }

    @Override
    public void createControllers() {
        Context applicationContext = this.getApplicationContext();

        AbstractApplicationController controller;

        SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(this);
        controllers.put(sharedPreferencesController.getName(), sharedPreferencesController);

        controller = new DbAccessController(applicationContext, SQLITE_DB_NAME);
        controllers.put(controller.getName(), controller);

        controller = new ConnectionController();
        controllers.put(controller.getName(), controller);

        ConfigurationController configurationController = new ConfigurationController(sharedPreferencesController);
        /*config=configurationController.getCurrentConfiguration().toString();
        if(config.equalsIgnoreCase(ConfigurationController.EnvironmentConfiguration.PRE_PROD.toString()))
        {
            prod_pre_prod_flag=true;
            System.out.println("pre-pod");
        }else
        {
            prod_pre_prod_flag=false;
            System.out.println("pod");
        }*/
        controllers.put(configurationController.getName(), configurationController);

        controller = new RestServiceHelper(configurationController, sharedPreferencesController);
        controllers.put(controller.getName(), controller);

        controller = new PreStoreController();
        controllers.put(controller.getName(), controller);


        controller = assembleProcessor(configurationController);
        controllers.put(controller.getName(), controller);


        // TODO controllers.put(controller.getName(), other_controller);
        // TODO controllers.put(controller.getName(), other_controller);
        // TODO controllers.put(controller.getName(), other_controller);
        // TODO controllers.put(controller.getName(), other_controller);

    }

    @Override
    public void startControllers() {
        Set<Map.Entry<String, AbstractApplicationController>> entrySet = controllers.entrySet();
        Iterator<Map.Entry<String, AbstractApplicationController>> it = entrySet.iterator();

        AbstractApplicationController controller;
        while (it.hasNext()) {
            controller = it.next().getValue();
            controller.onStart();
        }
    }

    @Override
    public void stopControllers() {
        List<AbstractApplicationController> reverseList = new ArrayList<>();
        Set<Map.Entry<String, AbstractApplicationController>> entrySet = controllers.entrySet();
        Iterator<Map.Entry<String, AbstractApplicationController>> it = entrySet.iterator();

        AbstractApplicationController controller;
        while (it.hasNext()) {
            controller = it.next().getValue();
            reverseList.add(controller);
        }

        for (int i = reverseList.size() - 1; i <= 0; i--) {
            reverseList.get(i).onPause();
        }
    }

    // ================================================================================
    // Artisan ArtisanRegisteredApplication implementations
    // ================================================================================

    @Override
    public void registerPowerhooks() {
        // Register your Artisan Power Hook variables and Power Hook blocks here
        // See examples at http://docs.useartisan.com/dev/android/power-hooks
        PowerHookManager.registerVariable("lockTimeout", "Inactivity timeout before lock/logout", "300");
        PowerHookManager.registerVariable("supportEmail", "Support email address", "support@mbopartners.com");
        PowerHookManager.registerVariable("joinUsLink", "Join Us link for login page", "https://www.mbopartners.com/referred-to-mbo-partners-by-client");
        PowerHookManager.registerVariable("forgotPasswordLink", "Forgot password link", "https://sso2-dev.mbopartners.com/auth/realms/dev/login-actions/password-reset/");
        PowerHookManager.registerVariable("privacyPolicyLink", "Privacy Policy link", "https://www.mbopartners.com/privacy-policy");
        PowerHookManager.registerVariable("termsAndConditionsLink", "Terms And Conditions link", "https://www.mbopartners.com/terms-and-conditions");

        PowerHookManager.registerVariable("devHostname", "Pre-prod API hostname", "http://test.tanders.mbopartners.com/api/");
        PowerHookManager.registerVariable("prodHostname", "Prod API hostname", "https://api.mbopartners.com/api/");

        PowerHookManager.registerVariable("devOAuthHostname", "Pre-prod Login hostname", "https://sso2-dev.mbopartners.com/auth/realms/dev/protocol/openid-connect/token");
        PowerHookManager.registerVariable("prodOAuthHostname", "Prod Login hostname", "https://login.mbopartners.com/auth/realms/mobile/protocol/openid-connect/token");
    }

    @Override
    public void registerInCodeExperiments() {
        // Register your Artisan In-code Experiments here
        // See examples at http://docs.useartisan.com/dev/android/incode-experiments
        ArtisanExperimentManager.registerExperimentWithDescription("Cart Process", "Skip or don't skip the product detail page");
    }

    @Override
    public void registerUserProfileVariables() {
        // Register your Artisan User Profile variables here
        // See examples at http://docs.useartisan.com/dev/android/user-profiles

        ArtisanProfileManager.setGender(ArtisanProfileManager.Gender.Male);
        ArtisanProfileManager.setUserAge(35);
        ArtisanProfileManager.setUserFirstName("Adil");
        ArtisanProfileManager.setUserLastName("Moen");
        ArtisanProfileManager.setSharedUserId("testing12345");
    }


    // ================================================================================


    public class Facade {
        public DbAccessController getDbAccessController() {

            DbAccessController dbAccessController = (DbAccessController) getController(Controllers.CONTROLLER__DATABASE);
            return dbAccessController;
        }
    }

   /* @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        SYSTEM_LOCALE = newConfig.locale.getLanguage();
        Log.d("locale",SYSTEM_LOCALE);
    }*/

}
