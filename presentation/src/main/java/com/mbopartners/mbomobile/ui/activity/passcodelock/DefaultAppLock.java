package com.mbopartners.mbomobile.ui.activity.passcodelock;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;

import java.util.Arrays;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DefaultAppLock extends AbstractAppLock {
    private static final String TAG = DefaultAppLock.class.getSimpleName();

    private static final String KEY_PREFERENCE__LOCK_MODE = "application Lock mode";
    private static final String LOCK_MODE__UNKNOWN = "LOCK MODE HAS NEVER BEEN SET";
    private static final String LOCK_MODE__NO_LOCK = "WE DO NOT NEED LOCKING";
    private static final String LOCK_MODE__LOCK_WITH_PIN = "LOCK APPLICATION WITH PIN";
    private static final String LOCK_MODE__LOCK_WITH_PASSWORD = "LOCK APPLICATION WITH PASSWORD";

    private static final String KEY_PREFERENCE_PIN = "PIN CODE";
    private static final String LOCK_MODE__LOCK_WITH_PASSWORD_FLAG="LOCK APPLICATION WITH PASSWORD FLAG";

    private static final Object PIN_SALT = "dcae0ade-f2b5-4c39-91b6-10522db2aa7b";
    private static final String PIN_ENC_SECRET = "bc9bd202-9d47-4af1-908d-5a94cc56030a";
    private Application currentApp; //Keep a reference to the app that invoked the locker
    private SharedPreferences settings;
    private Date lostFocusDate;
    private static String USER_NAME;
    private static String NOT_NOW_FLAG="NOTNOWFLAG";


    public DefaultAppLock(Application currentApp) {
        super();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(currentApp);
        this.settings = settings;
        this.currentApp = currentApp;
    }

    public void enable(){
        if( isPasswordLocked() ) {
            currentApp.unregisterActivityLifecycleCallbacks(this);
            currentApp.registerActivityLifecycleCallbacks(this);
        }
    }

    public void disable( ){
        currentApp.unregisterActivityLifecycleCallbacks(this);
    }

    public void forcePasswordLock(){
        lostFocusDate = null;
        Log.v(TAG, "forcePasswordLock()");
    }

    public void forceUnlockPermission(){
        lostFocusDate = new Date();
        Log.v(TAG, "forceUnlockPermission()");
    }

    public boolean verifyPassword( String password ){
    	String storedPassword = "";

        if (settings.contains(KEY_PREFERENCE_PIN)) {
    		storedPassword = settings.getString(KEY_PREFERENCE_PIN, "");
    		storedPassword = decryptPassword(storedPassword);
    		password = PIN_SALT + password + PIN_SALT;
    	}

        if( password.equalsIgnoreCase(storedPassword) ) {
            forceUnlockPermission();
            return true;
        } else {
            return false;
        }
    }

    //Check if we need to show the lock screen at startup
    public boolean isPasswordLocked() {
        String lockMode = getLockMode();
        return (lockMode.equals(LOCK_MODE__LOCK_WITH_PIN) || lockMode.equals(LOCK_MODE__LOCK_WITH_PASSWORD));
    }

    public boolean isPasswordLockedWithPin() {
        String lockMode = getLockMode();
        return (lockMode.equals(LOCK_MODE__LOCK_WITH_PIN));
    }

    @Override
    public boolean isLockModeNotSet() {
        String lockMode = getLockMode();
        //boolean b=settings.getBoolean(LOCK_MODE__LOCK_WITH_PASSWORD_FLAG,false);
        return lockMode.equals(LOCK_MODE__UNKNOWN)/*&&(!b)*/;
    }

    private String encryptPassword(String clearText) {
        try {
            DESKeySpec keySpec = new DESKeySpec(
                    PIN_ENC_SECRET.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encrypedPwd = Base64.encodeToString(cipher.doFinal(clearText
                    .getBytes("UTF-8")), Base64.DEFAULT);
            return encrypedPwd;
        } catch (Exception e) {
        }
        return clearText;
    }

    private String decryptPassword(String encryptedPwd) {
        try {
            DESKeySpec keySpec = new DESKeySpec(PIN_ENC_SECRET.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encryptedWithoutB64 = Base64.decode(encryptedPwd, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
            return new String(plainTextPwdBytes);
        } catch (Exception e) {
        }
        return encryptedPwd;
    }

    private boolean mustShowUnlockScreen() {

        if( isPasswordLocked() == false)
            return false;

        if( lostFocusDate == null )
            return true; //first startup or when we forced to show the password

        int currentTimeOut = lockTimeOut; //get a reference to the current password timeout and reset it to default
        lockTimeOut = DEFAULT_TIMEOUT;
        Date now = new Date();
        long now_ms = now.getTime();
        long lost_focus_ms = lostFocusDate.getTime();
        int secondsPassed = (int) (now_ms - lost_focus_ms)/(1000);
        secondsPassed = Math.abs(secondsPassed); //Make sure changing the clock on the device to a time in the past doesn't by-pass PIN Lock
        if (secondsPassed >= currentTimeOut) {
            forcePasswordLock();
            return true;
        }

        return false;
    }


    public void startPinUnlockScreen(Activity arg0) {
        arg0.getApplication().startActivity(ActivityIntentHelper.getPasscodeUnlockActivity(arg0));
    }

    public void startLoginScreen(Activity arg0) {
        arg0.getApplication().startActivity(ActivityIntentHelper.LoginActivityBuilder.getLoginActivity(arg0));
    }

    @Override
    public void onActivityPaused(Activity arg0) {

        if( arg0.getClass() == PasscodeUnlockActivity.class )
            return;

        if( ( this.appLockDisabledActivities != null ) && Arrays.asList(this.appLockDisabledActivities).contains( arg0.getClass().getName() ) )
     	   return;

        forceUnlockPermission();

    }

    @Override
    public void onActivityResumed(Activity arg0) {

        if( arg0.getClass() == PasscodeUnlockActivity.class )
            return;

       if(  ( this.appLockDisabledActivities != null ) && Arrays.asList(this.appLockDisabledActivities).contains( arg0.getClass().getName() ) )
    	   return;

        if(mustShowUnlockScreen()) {
            forceUserToUnlockApp(arg0);
            return;
        }

    }

    @Override
    public void onActivityCreated(Activity arg0, Bundle arg1) {
    }

    @Override
    public void onActivityDestroyed(Activity arg0) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {
    }

    @Override
    public void onActivityStarted(Activity arg0) {
    }

    @Override
    public void onActivityStopped(Activity arg0) {
    }

    // --------------------------------------------------------------------------------

    public String getLockMode() {
        return settings.getString(KEY_PREFERENCE__LOCK_MODE, LOCK_MODE__UNKNOWN);
    }

    public void setLockModePinLock(String pinCode) {
        SharedPreferences.Editor editor = settings.edit();
        pinCode = PIN_SALT + pinCode + PIN_SALT;
        pinCode = encryptPassword(pinCode);
        editor.putString(KEY_PREFERENCE__LOCK_MODE, LOCK_MODE__LOCK_WITH_PIN);
        editor.putString(KEY_PREFERENCE_PIN, pinCode);
        editor.commit();
        this.enable();
    }
    public void setLockModeLoginLock() {
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_PREFERENCE_PIN);
        editor.putString(KEY_PREFERENCE__LOCK_MODE, LOCK_MODE__LOCK_WITH_PASSWORD);
        //editor.putBoolean(LOCK_MODE__LOCK_WITH_PASSWORD_FLAG,true);
        editor.commit();
        this.enable();
    }
    public void setLockModeLoginLock01(){
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_PREFERENCE_PIN);
        editor.putString(KEY_PREFERENCE__LOCK_MODE, LOCK_MODE__UNKNOWN);
        editor.commit();
        this.enable();
    }
    public void setLockModeLoginLock02(){
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_PREFERENCE_PIN);
        editor.putBoolean(NOT_NOW_FLAG,true);
        editor.commit();
        this.enable();
    }

    public boolean getNotNowFlag()
    {
        return settings.getBoolean(NOT_NOW_FLAG,false);
    }

    public void setLockModeNoLock() {
//        SharedPreferences.Editor editor = settings.edit();
//        editor.remove(KEY_PREFERENCE_PIN);
//        editor.putString(KEY_PREFERENCE__LOCK_MODE, LOCK_MODE__NO_LOCK);
//        editor.commit();
//        this.disable();
    }

    public void forceUserToUnlockApp (Activity currentActivity) {
        String lockMode = getLockMode();
        switch (lockMode) {

            case LOCK_MODE__NO_LOCK : {
                break;
            }

            case LOCK_MODE__LOCK_WITH_PIN : {
                startPinUnlockScreen(currentActivity);
                break;
            }

            case LOCK_MODE__LOCK_WITH_PASSWORD : {
                startLoginScreen(currentActivity);
                break;
            }

            default : {
                startLoginScreen(currentActivity);
            }

        }
    }

}
