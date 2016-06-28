package com.mbopartners.mbomobile.ui.activity.settings;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.AutoLockActivity;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;
import com.mbopartners.mbomobile.ui.activity.passcodelock.PasscodeManagePasswordActivity;
import com.mbopartners.mbomobile.ui.util.Communicator;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;

public class SettingsActivity extends AutoLockActivity {

    public static final int ENABLE_PASSLOCK = 0;
    public static final int DISABLE_PASSLOCK = 1;
    public static final int CHANGE_PASSWORD = 2;

    private Switch pinUsingSwitch;
    private TextView changePasswordTextView;
    private TextView applicationSupportTextView;
    private TextView aboutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_up, R.anim.do_nothing);
        setContentView(R.layout.activity_settings);
        setupUpAppTabButtonIfPossible();

        pinUsingSwitch = (Switch) findViewById(R.id.mbo_switch_settings_pin_on_off);
        changePasswordTextView = (TextView) findViewById(R.id.mbo_textView_settings_chagge_password);

        applicationSupportTextView = (TextView) findViewById(R.id.mbo_textView_settings_app_support);
        applicationSupportTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceManufacturer = android.os.Build.MANUFACTURER;
                String deviceName = android.os.Build.MODEL;
                String osRelease = Build.VERSION.RELEASE;
                int deviceApiVersion = android.os.Build.VERSION.SDK_INT;

                String appVersion = "Unknown";
                try {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    appVersion = pInfo.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                IApplicationControllersManager applicationControllersManager =
                        (IApplicationControllersManager) getApplication();
                ConfigurationController configurationController =
                        (ConfigurationController) applicationControllersManager.getController(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
                String email = configurationController.getCurrentEnvironmentVariables().supportEmail;
                String subject = getString(R.string.mbo_settings_Support_Email_Subject);
                String text =
                        "Device: " + deviceManufacturer + ", " + deviceName + ",\n" +
                                "OS: " + osRelease + " (API:" + deviceApiVersion + "),\n" +
                                "App version ; " + appVersion + "\n\n" +
                                getString(R.string.mbo_settings_Support_Email_EnterYourFeedbackHere_Phrase);
                String chooserTitle = getString(R.string.mbo_settings_Support_Email_Email_Chooser_title);

                Communicator.startSendEmail(SettingsActivity.this, email, subject, text, chooserTitle);
            }
        });

        aboutTextView = (TextView) findViewById(R.id.mbo_textView_settings_about);
        aboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ActivityIntentHelper.getAboutActivity(SettingsActivity.this));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            startPasscodeManagePasswordActivity();
        }
    };

    private View.OnClickListener changePasscodeTouchListener = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Intent i = new Intent(SettingsActivity.this, PasscodeManagePasswordActivity.class);
            i.putExtra("type", CHANGE_PASSWORD);
            i.putExtra("message", getString(R.string.passcode_enter_old_passcode));
            startActivityForResult(i, CHANGE_PASSWORD);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case DISABLE_PASSLOCK:
                break;
            case ENABLE_PASSLOCK:
            case CHANGE_PASSWORD:
                if (resultCode == RESULT_OK) {
                    // we could make toast message about successful PIN set
                }
                break;
            default:
                break;
        }
        updateUI();
    }

    private void updateUI() {
        pinUsingSwitch.setOnClickListener(null);
        pinUsingSwitch.setOnCheckedChangeListener(null);
        changePasswordTextView.setOnClickListener(null);

        if ( AppLockManager.getInstance().getCurrentAppLock().isPasswordLockedWithPin() ) {
            pinUsingSwitch.setChecked(true);
            changePasswordTextView.setEnabled(true);
        } else {
            pinUsingSwitch.setChecked(false);
            changePasswordTextView.setEnabled(false);
        }

        pinUsingSwitch.setOnCheckedChangeListener(checkedChangeListener);
        changePasswordTextView.setOnClickListener(changePasscodeTouchListener);
    }

    private void startPasscodeManagePasswordActivity() {
        int mode = AppLockManager.getInstance().getCurrentAppLock().isPasswordLockedWithPin() ? DISABLE_PASSLOCK : ENABLE_PASSLOCK;
        Intent intent = ActivityIntentHelper.getPasscodeManagePasswordActivity(this, mode);
        intent.putExtra("type", mode);
        startActivityForResult(intent, mode);
    }


}