package com.mbopartners.mbomobile.ui.activity.about;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.IRestClient;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.dashboard.DataLoadingDispatcher;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;
import com.mbopartners.mbomobile.ui.toast.ActivityToaster;
import com.mbopartners.mbomobile.ui.util.Security;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.framework.communication.Communicator;
import ua.com.mobidev.android.mdrest.web.rest.processor.IHttpRequestProcessor;

public class AboutActivity extends AutoLockActivity {
    private TextView appVersionTextView;
    private TextView privacyPolicyTextView;
    private TextView termsAndConditionsTextView;

    private DataLoadingDispatcher dataLoadingDispatcher;
    private ImageView logoImageView;
    private int magicClickCounter = 0;
    private int userInteractedCounter = 0;
    private  SharedPreferences sharedPreferences=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupUpAppTabButtonIfPossible();
        sharedPreferences=getSharedPreferences("environment_pref", Context.MODE_PRIVATE);
        appVersionTextView = (TextView) findViewById(R.id.mbo_textView_About_AppVersion);

        privacyPolicyTextView = (TextView) findViewById(R.id.mbo_privacy_policy_TextView);
        privacyPolicyTextView.setOnClickListener(privacyPolicyClickListener);

        termsAndConditionsTextView = (TextView) findViewById(R.id.mbo_terms_and_conditions_TextView);
        termsAndConditionsTextView.setOnClickListener(termsAndConditionsPolicyClickListener);

        logoImageView = (ImageView) findViewById(R.id.mbo_imageView_settings_Logo);
        logoImageView.setOnClickListener(magicClickListener);
        SharedPreferencesController sharedPreferencesController =
                (SharedPreferencesController) getApplicationControllersManager()
                        .getController(Controllers.CONTROLLER__SHARED_PREFERENCES);
        dataLoadingDispatcher = new DataLoadingDispatcher(sharedPreferencesController);
        String appVersion = "Unknown";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        appVersionTextView.setText(getString(R.string.mbo_Mobile_Version_text) + " V " + appVersion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetCounters();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userInteractedCounter++;
    }

    private void resetCounters() {
        magicClickCounter = 0;
        userInteractedCounter = 0;
    }

    private View.OnClickListener privacyPolicyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IApplicationControllersManager applicationControllersManager =
                    (IApplicationControllersManager) getApplication();
            ConfigurationController configurationController =
                    (ConfigurationController) applicationControllersManager.getController(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
            String forgotPasswordUri = configurationController.getCurrentEnvironmentVariables().privacyPolicyLink;
            Communicator.startExternalBrowser(AboutActivity.this, forgotPasswordUri);
        }
    };

    private View.OnClickListener termsAndConditionsPolicyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IApplicationControllersManager applicationControllersManager =
                    (IApplicationControllersManager) getApplication();
            ConfigurationController configurationController =
                    (ConfigurationController) applicationControllersManager.getController(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
            String forgotPasswordUri = configurationController.getCurrentEnvironmentVariables().termsAndConditionsLink;
            Communicator.startExternalBrowser(AboutActivity.this, forgotPasswordUri);
        }
    };

    private View.OnClickListener magicClickListener = new View.OnClickListener() {
        @Override
        public void onClick (View v) {
            if (userInteractedCounter != magicClickCounter + 1) {
                resetCounters();
            }

            if (magicClickCounter < 9 ) {
                magicClickCounter++;
                userInteractedCounter = magicClickCounter;
            } else {
                resetCounters();
                showChooseEnvironmentDialog();
                return;
            }
        }
    };

    private void showChooseEnvironmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConfigurationController configurationController = getConfigurationController();
        String currentConfiguration = "Current :\n" + configurationController.getCurrentConfiguration().toString();
        builder.setTitle(R.string.mbo_Settings_choose_environment_dialog_title);
        builder.setMessage(currentConfiguration);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        builder.setPositiveButton(R.string.mbo_Settings_choose_environment_dialog_Button_OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                forceToSwitchConfiguration(ConfigurationController.EnvironmentConfiguration.PRE_PROD);
                editor.putString("env", "pre_prod");
                editor.commit();
                doHardLogout();
            }
        });
        builder.setNegativeButton(R.string.mbo_Settings_choose_environment_dialog_Button_NO, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                forceToSwitchConfiguration(ConfigurationController.EnvironmentConfiguration.PRODUCTION);
                editor.putString("env","prod");
                editor.commit();
                doHardLogout();
            }
        });

        AlertDialog pinDialog = builder.create();
        pinDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pinDialog.show();
    }

    private void forceToSwitchConfiguration(ConfigurationController.EnvironmentConfiguration configuration) {
        ConfigurationController configurationController = getConfigurationController();
        switch (configuration) {
            case PRODUCTION: {
                configurationController.setCurrentConfiguration(ConfigurationController.EnvironmentConfiguration.PRODUCTION);
                break;
            }

            case PRE_PROD: {
                configurationController.setCurrentConfiguration(ConfigurationController.EnvironmentConfiguration.PRE_PROD);
                break;
            }

            default: {
                configurationController.setCurrentConfiguration(ConfigurationController.EnvironmentConfiguration.PRODUCTION);
            }
        }
        ActivityToaster.showToastLongMessage(this, getString(R.string.mbo_Settings_environment_changed));
    }

    private ConfigurationController getConfigurationController() {
        IApplicationControllersManager applicationControllersManager = (IApplicationControllersManager) AboutActivity.this.getApplication();
        ConfigurationController configurationController = (ConfigurationController) applicationControllersManager.getController(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
        return configurationController;
    }

    private void doHardLogout() {
        Security.doHardLogoutRoutine(this.getApplication());
        AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock01();
        dataLoadingDispatcher.setFlagFirstTimeLaunch_logout();
        startActivity(ActivityIntentHelper.LoginActivityBuilder.getLoginActivity(this));
    }

}
