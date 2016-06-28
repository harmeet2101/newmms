package com.mbopartners.mbomobile.ui.activity.passcodelock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.util.Security;

public class PasscodeUnlockActivity extends AbstractPasscodeKeyboardActivity {
    private int unlockAttemptsCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView pinPrompt = (TextView) findViewById(R.id.top_message);
        pinPrompt.setText(R.string.passcode_enter_passcode_unlock_prompt);

        TextView logoutTextView = (TextView) findViewById(R.id.logoutTextView);
        logoutTextView.setVisibility(View.VISIBLE);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = PasscodeUnlockActivity.this;
                Security.doLogoutRoutine(activity.getApplication());
                AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock();
                String message = getString(R.string.mbo_Login_Successfully_Logged_Out_message);
                activity.startActivity(ActivityIntentHelper.LoginActivityBuilder.getLoginActivity(activity,message));
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        unlockAttemptsCounter = 0;
    }

    @Override
    public void onBackPressed() {
        AppLockManager.getInstance().getCurrentAppLock().forcePasswordLock();
        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(i);
        finish();
    }

    @Override
    protected void onPinLockInserted() {
        String passLock = getPassLockWord();
        if( AppLockManager.getInstance().getCurrentAppLock().verifyPassword(passLock) ) {
            setResult(RESULT_OK);
            finish();
        } else {
            unlockAttemptsCounter++;
            Thread shake = new Thread() {
                public void run() {
                    Animation shake = AnimationUtils.loadAnimation(PasscodeUnlockActivity.this, R.anim.shake);
                    shake.setAnimationListener(animationListener);
                    findViewById(R.id.AppUnlockLinearLayout1).startAnimation(shake);
                    showPasswordError(R.string.mbo_passcode_wrong_passcode);
                    initEnteringPasswordSpace();
                }
            };
            runOnUiThread(shake);
        }
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            lockUi();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (unlockAttemptsCounter > 2) {
                Context context = PasscodeUnlockActivity.this;
                AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock();
                String message = getString(R.string.mbo_Login_Spent_all_Unlock_attempts_message);
                context.startActivity(ActivityIntentHelper.LoginActivityBuilder.getLoginActivity(context, message));
            }
            unLockUi();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };
}