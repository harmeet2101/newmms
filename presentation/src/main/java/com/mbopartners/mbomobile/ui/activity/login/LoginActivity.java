package com.mbopartners.mbomobile.ui.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mbopartners.mbomobile.rest.configuration.ConfigurationController;
import com.mbopartners.mbomobile.rest.model.response.UserProfile;
import com.mbopartners.mbomobile.rest.persistance.SharedPreferencesController;
import com.mbopartners.mbomobile.rest.rest.client.IRestClient;
import com.mbopartners.mbomobile.rest.rest.client.request.RestApiContract;
import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.ArtisanedBaseActivity;
import com.mbopartners.mbomobile.ui.activity.helper.ActivityIntentHelper;
import com.mbopartners.mbomobile.ui.activity.passcodelock.AppLockManager;
import com.mbopartners.mbomobile.ui.util.DefaultRestClientResponseHandler;
import com.mbopartners.mbomobile.ui.util.FontController;

import java.net.HttpURLConnection;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.framework.communication.Communicator;
import ua.com.mobidev.android.framework.ui.keyboard.KeyboardUtil;
import ua.com.mobidev.android.framework.util.UiUtils;
import ua.com.mobidev.android.mdrest.web.rest.processor.IHttpRequestProcessor;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class LoginActivity extends ArtisanedBaseActivity implements FontController{

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText editTextUserName;
    private EditText editTextPassword;
    private TextView textViewError_Message;
    private Button buttonSignIn;
    private TextView buttonForgotPassword;

    private ProgressDialog ringProgressDialog;

    private int normalTextColor;
    private int normalHintColor;
    private int redTextColor;
    private int redHintColor;
    private String USER_NAME;
    private  IRestClient restServiceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        UiUtils.hideActionBar(this);
        setContentView(R.layout.mbo_activity_login);

        restServiceHelper = getRestServiceHelper();

        restServiceHelper.clearCallbacks();
        DefaultRestClientResponseHandler defaultRestClientResponseHandler = new DefaultRestClientResponseHandler(this);
        final IRestClient.Callback loginCallback = new OAuthLoginCallback(defaultRestClientResponseHandler);
        final IRestClient.Callback getUserProfileCallback = new UserProfileCallback(defaultRestClientResponseHandler);
        restServiceHelper.registerCallback(RestApiContract.Method.oAuth, loginCallback);
        restServiceHelper.registerCallback(RestApiContract.Method.getUserProfile, getUserProfileCallback);

        final Context context = this;

        editTextUserName = (EditText) findViewById(R.id.mbo_editText_login_username);
        editTextPassword = (EditText) findViewById(R.id.mbo_editText_login_password);
        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doLogin(restServiceHelper, context);
                }
                return false;
            }
        });

        textViewError_Message = (TextView) findViewById(R.id.mbo_textView_login_ErrorMessage);
        buttonSignIn = (Button) findViewById(R.id.mbo_button_login_signIn);
        ;
        buttonForgotPassword = (TextView) findViewById(R.id.mbo_button_login_forgotPassword);
        View view=findViewById(R.id.mbo_button_login_forgotPassword);
        setFont(view);
        normalTextColor = getResources().getColor(R.color.mbo_simple_edittext_font_color);
        normalHintColor = getResources().getColor(R.color.mbo_simple_edittext_hint_color);
        redTextColor = getResources().getColor(R.color.mbo_error_message_text__color);
        redHintColor = getResources().getColor(R.color.mbo_error_message_hint__color);
        editTextUserName.setText("021d8075-e293-41e0-a6be-b520ad34e904");
        editTextPassword.setText("mbo.2011");
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(restServiceHelper, context);
            }
        });

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IApplicationControllersManager applicationControllersManager =
                        (IApplicationControllersManager) getApplication();
                ConfigurationController configurationController =
                        (ConfigurationController) applicationControllersManager.getController(Controllers.CONTROLLER__APPLICATION_CONFIGURATION);
                String forgotPasswordUri = configurationController.getCurrentEnvironmentVariables().forgotPasswordLink;
                Communicator.startExternalBrowser(LoginActivity.this, forgotPasswordUri);
            }
        });

        findViewById(R.id.mbo_login_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.htryToHideKeyboard(LoginActivity.this);
            }
        });

    }

    private void doLogin(IRestClient restServiceHelper, Context context) {
        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        if (userName.isEmpty() || password.isEmpty()) {
            textViewError_Message.setText("");
            if (userName.isEmpty()) {
                setUserNameColors(normalTextColor, redHintColor);
            }
            if (password.isEmpty()) {
                setPasswordColors(normalTextColor, redHintColor);
            }
        } else {
            lockUi();
            showProgressDialog(getString(R.string.mbo_Login_ProgressDialog_title), getString(R.string.mbo_Login_ProgressDialog_message));
            String grantType = "password";
            restServiceHelper.authenticateOauth(context, userName, password, grantType);

        }
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume()");
        super.onResume();

        SharedPreferencesController sharedPreferencesController =
                (SharedPreferencesController) getApplicationControllersManager().getController(Controllers.CONTROLLER__SHARED_PREFERENCES);

        String userName = sharedPreferencesController.getUserName();
        USER_NAME = userName;
        if (userName.length() > 0) {
            editTextUserName.setText(userName);
        }

        clearPassword();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        tryToShowReceivedToast();
    }

    private void lockUi() {
        buttonSignIn.setEnabled(false);
        buttonForgotPassword.setEnabled(false);
    }

    private void unlockUI() {
        buttonSignIn.setEnabled(true);
        buttonForgotPassword.setEnabled(true);
    }

    class OAuthLoginCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public OAuthLoginCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {
            hideProgressDialog();
            unlockUI();
            switch (response.getClientResult()) {
                case Ok: {
                    turnToNormalColors();

                   //restServiceHelper.getUserProfile(LoginActivity.this);
                    AppLockManager.getInstance().getCurrentAppLock().forceUnlockPermission();
                    b = AppLockManager.getInstance().getCurrentAppLock().getNotNowFlag();

                    setDataForPayrollTab(b, isNonBillableAlowed);

                    break;
                }
                case HttpError: {
                    int httpErrorCode = response.getClientResult().getStatusCode();
                    switch (httpErrorCode) {
                        case HttpURLConnection.HTTP_UNAUTHORIZED: {
                            textViewError_Message.setText(getString(R.string.mbo_login_ErrorMessage));
                            turnToErrorColors();
                            clearPassword();
                            break;
                        }
                        default: {
                            textViewError_Message.setText(getString(R.string.mbo_login_ServerError_ErrorMessage));
                            turnToErrorColors();
                        }
                    }
                    break;
                }
                case NoConnectionError:
                case NetworkError:
                case TimeoutError:
                case ParseError:
                case Unknown: {
                    textViewError_Message.setText(getString(R.string.mbo_login_NoConnection_ErrorMessage));
                    turnToErrorColors();
                    break;
                }
                default: {
                    defaultHandler.onComplete(response);
                }
            }
        }
    }
    class UserProfileCallback implements IRestClient.Callback {
        private DefaultRestClientResponseHandler defaultHandler;

        public UserProfileCallback(DefaultRestClientResponseHandler defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        @Override
        public void onComplete(UniversalRestResponse response) {

            switch (response.getClientResult()) {
                case Ok : {
                    UserProfile userProfile=(UserProfile)response.getResponseEntity();
                    isNonBillableAlowed=userProfile.getNonbillableAllowed();


                   setDataForPayrollTab(b,isNonBillableAlowed);

//                    startActivity(ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityRevenuePage(LoginActivity.this));
//                    LoginActivity.this.finish();
                    break;
                }
                default: {
                    setDataForPayrollTab(b, true);
                   defaultHandler.onComplete(response);
                }
            }
        }
    }
    private void setDataForPayrollTab(boolean b,boolean isNonBillableAlowed)
    {
        Bundle bundle=new Bundle();
        bundle.putBoolean("isNonBillableAlowed",isNonBillableAlowed);
        if (USER_NAME.equals(editTextUserName.getText().toString()) && b) {
            startActivity(ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityRevenuePage(LoginActivity.this,bundle));
            LoginActivity.this.finish();
        } else if (!USER_NAME.equals(editTextUserName.getText().toString()) && b) {
            AppLockManager.getInstance().getCurrentAppLock().setLockModeLoginLock01();
            startActivity(ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityRevenuePage(LoginActivity.this,bundle));
            LoginActivity.this.finish();
        } else if (!b) {
            startActivity(ActivityIntentHelper.DashboardActivityBuilder.getDashboardActivityRevenuePage(LoginActivity.this,bundle));
            LoginActivity.this.finish();
        }
    }
    private boolean isNonBillableAlowed,b;
    private void clearPassword() {
        editTextPassword.setText("");
    }

    private void turnToErrorColors() {
        if (textViewError_Message.getText().toString().isEmpty()) {
            textViewError_Message.setVisibility(View.INVISIBLE);
        } else {
            textViewError_Message.setVisibility(View.VISIBLE);
        }
        setUserNameColors(redTextColor, redHintColor);
        setPasswordColors(redTextColor, redHintColor);
    }

    private void turnToNormalColors() {
        textViewError_Message.setVisibility(View.INVISIBLE);
        setUserNameColors(normalTextColor, normalHintColor);
        setPasswordColors(normalTextColor, normalHintColor);
    }

    private void setUserNameColors(int textColor, int hintColor) {
        /**
         * Next strings is a trick
         */
        String userName = editTextUserName.getText().toString();

        editTextUserName.setText(".");
        editTextUserName.setTextColor(textColor);
        editTextUserName.setText(null);
        editTextUserName.setHintTextColor(hintColor);
        editTextUserName.setText(userName);
    }

    private void setPasswordColors(int textColor, int hintColor) {
        /**
         * Next strings is a trick
         */
        String password = editTextPassword.getText().toString();

        editTextPassword.setText(".");
        editTextPassword.setTextColor(textColor);
        editTextPassword.setText(null);
        editTextPassword.setHintTextColor(hintColor);
        editTextPassword.setText(password);
    }

    private void tryToShowReceivedToast() {
        String text = ActivityIntentHelper.LoginActivityBuilder.getStartingMessage(this);
        showToastLongMessage(this.getApplicationContext(), text);
    }

    public void showProgressDialog(String title, String message) {
        ringProgressDialog = ProgressDialog.show(LoginActivity.this, title, message, true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideProgressDialog() {
        if (ringProgressDialog != null) {
            ringProgressDialog.dismiss();
        }
    }

    public void showToastLongMessage(Context applicationContext, String text) {
        if (text != null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float density = displayMetrics.density;
            Toast toast = Toast.makeText(applicationContext, text, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, (int) (83 * density));
            toast.show();
        }
    }

    public void setFont(View...args)
    {
        TextView textView1=(TextView)args[0];
        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/Roboto-Regular.ttf");
        textView1.setTypeface(typeface);
    }
    private void clearHttpClientQueue() {
        IHttpRequestProcessor httpRequestProcessor = (IHttpRequestProcessor) getApplicationControllersManager().getController(Controllers.CONTROLLER__REST_PROCESSOR);
        httpRequestProcessor.cancelAllRequests();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");
        clearHttpClientQueue();
        restServiceHelper.clearCallbacks();
    }
}
