package com.mbopartners.mbomobile.ui.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;
import com.mbopartners.mbomobile.ui.util.Security;

public class AutoLocker implements Application.ActivityLifecycleCallbacks {
    private static final long USER_INACTIVITY_TIME = 10000L;
    private static final int WHAT_ID = 12345;

    private Activity activity;
    private Handler handler;

    public AutoLocker() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        this.activity = activity;
        handler = new Handler(handlerCallback);
        setLockingTimer();
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        setLockingTimer();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        clearLockingTimer();
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public void onUserInteraction() {
        clearLockingTimer();
        setLockingTimer();
    }

    // --------------------------------------------------------------------------------

    Handler.Callback handlerCallback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            lockApplication();
            return false;
        }
    };

    private void setLockingTimer() {
        handler.sendEmptyMessageDelayed(WHAT_ID, USER_INACTIVITY_TIME);
    }

    private void clearLockingTimer() {
        handler.removeMessages(WHAT_ID);
    }

    private void lockApplication() {
        Context context = activity;
        if (AppLockManager.getInstance().getCurrentAppLock().isPasswordLockedWithPin()) {
            context.startActivity(ActivityIntentHelper.getPasscodeUnlockActivity(context));
        } else if (AppLockManager.getInstance().getCurrentAppLock().isPasswordLocked()) {
            Security.doLogoutRoutine(activity.getApplication());
            context.startActivity(ActivityIntentHelper.LoginActivityBuilder.getLoginActivity(context));
        }


    }
}
