package com.mbopartners.mbomobile.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.rest.configuration.NetworkingConstants;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;
import com.mbopartners.mbomobile.ui.util.Security;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;

public class AutoLockActivity extends MboBaseActivity {
    private static final String TAG = AutoLockActivity.class.getSimpleName();

    private static final int WHAT_ID = 12345;
    private long ONE_SECOND = 1000L;
    private long USER_INACTIVITY_TIME = ONE_SECOND * 300L;
    private static final String POWER_HOOK__LOCK_TIMEOUT = "lockTimeout";

    private Handler handler;
    private boolean activityVisible = true;

    Handler.Callback handlerCallback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            if (activityVisible) {
                lockApplication();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVisible = true;
        handler = new Handler(handlerCallback);

        ForceToUpdateEnvironmentVariables();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityVisible = true;
        long timeValueSeconds = Long.parseLong(NetworkingConstants.LOCK_TIMEOUT);
        USER_INACTIVITY_TIME = ONE_SECOND * timeValueSeconds;
        setLockingTimer();
    }

    @Override
    public void onUserInteraction() {
        clearLockingTimer();
        setLockingTimer();
        super.onUserInteraction();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityVisible = false;
        clearLockingTimer();
    }

    private void setLockingTimer() {
        if (activityVisible) {
            handler.sendEmptyMessageDelayed(WHAT_ID, USER_INACTIVITY_TIME);
        }
    }

    private void clearLockingTimer() {
        handler.removeMessages(WHAT_ID);
    }

    private void lockApplication() {
        long realUserInactivityTime = USER_INACTIVITY_TIME == 0L ? 0L : USER_INACTIVITY_TIME / ONE_SECOND;
        Context context = AutoLockActivity.this;
        if (AppLockManager.getInstance().getCurrentAppLock().isPasswordLockedWithPin()) {
            context.startActivity(ActivityIntentHelper.getPasscodeUnlockActivity(context));
        } else if (AppLockManager.getInstance().getCurrentAppLock().isPasswordLocked()) {
            Security.doLogoutRoutine(this.getApplication());
            context.startActivity(ActivityIntentHelper.LoginActivityBuilder.getLoginActivity(context));
        }
    }

    private void ForceToUpdateEnvironmentVariables() {
        IApplicationControllersManager applicationControllersManager =
                (IApplicationControllersManager) getApplication();
        ConfigurationController configurationController =
                (ConfigurationController) applicationControllersManager.getController(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
        configurationController.forceToUpdateVariables();
    }
}
