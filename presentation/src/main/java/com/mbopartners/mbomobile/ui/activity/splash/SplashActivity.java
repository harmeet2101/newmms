package com.mbopartners.mbomobile.ui.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;

public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1200;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mbo_activity_splash);

        final IApplicationControllersManager applicationControllersManager = (IApplicationControllersManager) this.getApplication();

        final SharedPreferencesController sharedPreferencesController =
                (SharedPreferencesController) applicationControllersManager.getController(Controllers.CONTROLLER__SHARED_PREFERENCES);

        final Intent intent;
        if (sharedPreferencesController.isUserLoggedIn()) {
            intent = ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityRevenuePage(SplashActivity.this);
        } else {
            intent = ActivityIntentHelper.LoginActivityBuilder.getLoginActivity(SplashActivity.this);
        }

        /***********************************************************/
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        finish();
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}
