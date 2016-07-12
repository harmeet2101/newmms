package com.mbopartners.mbomobile.rest.configuration;

import com.artisan.powerhooks.PowerHookManager;
import com.artisan.powerhooks.PowerHookVariable;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;

import ua.com.mobidev.android.framework.application.controller.AbstractApplicationController;
import ua.com.mobidev.android.framework.application.controller.Controllers;

public class ConfigurationController extends AbstractApplicationController {
    private static final String POWER_HOOK__LOCK_TIMEOUT = "lockTimeout";
    private static final String POWER_HOOK__SUPPORT_EMAIL = "supportEmail";
    private static final String POWER_HOOK__JOIN_US_LINK = "joinUsLink";
    private static final String POWER_HOOK__FORGOT_PASSWORD_LINK = "forgotPasswordLink";
    private static final String POWER_HOOK__PRIVACY_POLICY_LINK = "privacyPolicyLink";
    private static final String POWER_HOOK__TERMS_AND_CONDITIONS_LINK = "termsAndConditionsLink";

    private static final String POWER_HOOK__DEV_HOSTNAME = "devHostname";
    private static final String POWER_HOOK__PROD_HOSTNAME = "prodHostname";

    private static final String POWER_HOOK__DEV_OAUTH_HOSTNAME = "devOAuthHostname";
    private static final String POWER_HOOK__PROD_OAUTH_HOSTNAME = "prodOAuthHostname";

    private static final String OAUTH_DEV_CLIENT_ID = "time-expenses-app";
    private static final String OAUTH_DEV_CLIENT_SECRET = "43568d19-3620-4ec4-8123-f63502ec261f";
    private static final String OAUTH_PROD_CLIENT_ID = "mbo-mobile";
    private static final String OAUTH_PROD_CLIENT_SECRET = "4eb69d74-b9ac-4052-acef-f5884bc8fd2e";

    //private static final String DEV_HOSTNAME="http://test.tanders.mbopartners.com/api/";
    private static final String DEV_HOSTNAME="http://66.208.23.234/";
    private SharedPreferencesController sharedPreferencesController;
    private EnvironmentConfiguration currentConfiguration;

    private String currentLockTimeout;

    private String currentSupportEmail;
    private String currentJoinUsLink;
    private String currentForgotPasswordLink;
    private String currentPrivacyPolicyLink;
    private String currentTermsAndConditionsLink;
    private String currentDevHostname;
    private String currentProdHostname;
    private String currentDevOAuthHostname;
    private String currentProdOAuthHostname;

    public ConfigurationController(SharedPreferencesController sharedPreferencesController) {
        super(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
        this. sharedPreferencesController = sharedPreferencesController;

        forceToUpdateVariables();

        String configurationStr = sharedPreferencesController.getEnvironmentConfiguration();
        currentConfiguration =  EnvironmentConfiguration.distinguishConfiguration(configurationStr);

    }

    @Override
    public boolean onStart() {
        return false;
    }

    @Override
    public boolean onPause() {
        return false;
    }

    public EnvironmentConfiguration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(EnvironmentConfiguration newConfiguration) {
        currentConfiguration = newConfiguration;
        sharedPreferencesController.saveEnvironmentConfiguration(newConfiguration.toString());
        forceToUpdateVariables();
    }

    public EnvironmentVariables getCurrentEnvironmentVariables() {
        EnvironmentVariables result;
        switch (currentConfiguration) {
            case PRODUCTION : {
                result = new EnvironmentVariables(
                        this.currentLockTimeout,
                        this.currentProdHostname,
                        this.currentProdOAuthHostname,
                        this.currentSupportEmail,
                        this.currentJoinUsLink,
                        this.currentForgotPasswordLink,
                        this.currentPrivacyPolicyLink,
                        this.currentTermsAndConditionsLink,
                        OAUTH_PROD_CLIENT_ID,
                        OAUTH_PROD_CLIENT_SECRET
                        );
                break;
            }

            case PRE_PROD : {
                result = new EnvironmentVariables(
                        this.currentLockTimeout,
                        this.currentDevHostname,
                        this.currentDevOAuthHostname,
                        this.currentSupportEmail,
                        this.currentJoinUsLink,
                        this.currentForgotPasswordLink,
                        this.currentPrivacyPolicyLink,
                        this.currentTermsAndConditionsLink,
                        OAUTH_DEV_CLIENT_ID,
                        OAUTH_DEV_CLIENT_SECRET);
                break;
            }

            default : {
                result = new EnvironmentVariables(
                        this.currentLockTimeout,
                        this.currentProdHostname,
                        this.currentProdOAuthHostname,
                        this.currentSupportEmail,
                        this.currentJoinUsLink,
                        this.currentForgotPasswordLink,
                        this.currentPrivacyPolicyLink,
                        this.currentTermsAndConditionsLink,
                        OAUTH_PROD_CLIENT_ID,
                        OAUTH_PROD_CLIENT_SECRET
                );
                /*result = new EnvironmentVariables(
                        this.currentLockTimeout,
                        this.currentDevHostname,
                        this.currentDevOAuthHostname,
                        this.currentSupportEmail,
                        this.currentJoinUsLink,
                        this.currentForgotPasswordLink,
                        this.currentPrivacyPolicyLink,
                        this.currentTermsAndConditionsLink,
                        OAUTH_DEV_CLIENT_ID,
                        OAUTH_DEV_CLIENT_SECRET);*/
            }
        }
        return result;
    }

    public void forceToUpdateVariables() {
        currentLockTimeout = PowerHookManager.getVariableValue(POWER_HOOK__LOCK_TIMEOUT);
        currentSupportEmail = PowerHookManager.getVariableValue(POWER_HOOK__SUPPORT_EMAIL);
        currentJoinUsLink = PowerHookManager.getVariableValue(POWER_HOOK__JOIN_US_LINK);
        currentForgotPasswordLink = PowerHookManager.getVariableValue(POWER_HOOK__FORGOT_PASSWORD_LINK);
        currentPrivacyPolicyLink = PowerHookManager.getVariableValue(POWER_HOOK__PRIVACY_POLICY_LINK);
        currentTermsAndConditionsLink = PowerHookManager.getVariableValue(POWER_HOOK__TERMS_AND_CONDITIONS_LINK);

        //currentDevHostname = PowerHookManager.getVariableValue(POWER_HOOK__DEV_HOSTNAME);
        currentDevHostname=DEV_HOSTNAME;
        currentProdHostname = PowerHookManager.getVariableValue(POWER_HOOK__PROD_HOSTNAME);
        currentDevOAuthHostname = PowerHookManager.getVariableValue(POWER_HOOK__DEV_OAUTH_HOSTNAME);
        currentProdOAuthHostname = PowerHookManager.getVariableValue(POWER_HOOK__PROD_OAUTH_HOSTNAME);
    }

    public class EnvironmentVariables {
        public final String lockTimeout;
        public final String hostname;
        public final String oAuthHostname;
        public final String supportEmail;
        public final String joinUsLink;
        public final String forgotPasswordLink;
        public final String privacyPolicyLink;
        public final String termsAndConditionsLink;
        public final String oAuth_ClientId;
        public final String oAuth_ClientSecret;

        public EnvironmentVariables(String lockTimeout,
                                    String hostname,
                                    String oAuthHostname,
                                    String supportEmail,
                                    String joinUsLink,
                                    String forgotPasswordLink,
                                    String privacyPolicyLink,
                                    String termsAndConditionsLink,
                                    String oAuth_ClientId,
                                    String oAuth_ClientSecret) {
            this.lockTimeout = lockTimeout;
            this.hostname = hostname;
            this.oAuthHostname = oAuthHostname;
            this.supportEmail = supportEmail;
            this.joinUsLink = joinUsLink;
            this.forgotPasswordLink = forgotPasswordLink;
            this.privacyPolicyLink = privacyPolicyLink;
            this.termsAndConditionsLink = termsAndConditionsLink;
            this.oAuth_ClientId = oAuth_ClientId;
            this.oAuth_ClientSecret = oAuth_ClientSecret;
        }
    }

    public enum EnvironmentConfiguration {
        PRODUCTION("Production Environment"),
        PRE_PROD("Pre-Prod Environment");

        private static final String PRODUCTION_KEYVALUE = "Production Environment";
        private static final String PRE_PROD_KEYVALUE = "Pre-Prod Environment";

        private String configuration;

        EnvironmentConfiguration(String configValue) {
            this.configuration = configValue;
        }

        public static EnvironmentConfiguration distinguishConfiguration(String config) {
            EnvironmentConfiguration result;
            switch (config) {
                case PRODUCTION_KEYVALUE : {
                    result = PRODUCTION;
                    break;
                }

                case PRE_PROD_KEYVALUE : {
                    result = PRE_PROD;
                    break;
                }

                default : {
                    result = PRODUCTION;
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return configuration;
        }
    }
}
