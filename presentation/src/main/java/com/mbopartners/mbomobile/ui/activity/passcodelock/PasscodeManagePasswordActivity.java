package com.mbopartners.mbomobile.ui.activity.passcodelock;

import android.os.Bundle;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.settings.SettingsActivity;

public class PasscodeManagePasswordActivity extends AbstractPasscodeKeyboardActivity {
    private int type = -1;
    private String unverifiedPasscode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPasswordMessage(R.string.passcode_enter_passcode);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getInt("type", -1);
        }
    }
    
    @Override
    protected void onPinLockInserted() {
        String passLock = getPassLockWord();

        initEnteringPasswordSpace();
        
        switch (type) {
            
            case SettingsActivity.DISABLE_PASSLOCK:
                if( AppLockManager.getInstance().getCurrentAppLock().verifyPassword(passLock) ) {
                    setResult(RESULT_OK);
                    AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock();
                    finish();
                } else {
                    showPasswordError(R.string.mbo_passcode_wrong_passcode);
                }
                break;
                
            case SettingsActivity.ENABLE_PASSLOCK:
                if( unverifiedPasscode == null ) {
                    showPasswordMessage(R.string.passcode_re_enter_passcode);
                    unverifiedPasscode = passLock;
                } else {
                    if( passLock.equals(unverifiedPasscode)) {
                        setResult(RESULT_OK);
                        AppLockManager.getInstance().getCurrentAppLock().setLockModePinLock(passLock);
                        finish();
                    } else {
                        unverifiedPasscode = null;
                        showPasswordError(R.string.passcode_re_entered_wrong);
                    }
                }
                break;
                
            case SettingsActivity.CHANGE_PASSWORD:
                //verify old password
                if( AppLockManager.getInstance().getCurrentAppLock().verifyPassword(passLock) ) {
                    showPasswordMessage(R.string.passcode_enter_passcode);
                    type = SettingsActivity.ENABLE_PASSLOCK;
                } else {
                    showPasswordError(R.string.mbo_passcode_wrong_passcode);
                } 
                break;
                
            default:
                break;
        }
    }
}