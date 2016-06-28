package com.mbopartners.mbomobile.rest.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mbopartners.mbomobile.rest.rest.client.IRestClient;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.WebRestService;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class MdBaseActivity extends AppCompatActivity {
    private static final String TAG = MdBaseActivity.class.getSimpleName();
    private static final IntentFilter intentFilter = WebRestService.buildBroadcastResponseFilter();

    Handler handler;
    BroadcastReceiver br;
    IRestClient restServiceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restServiceHelper = (IRestClient) getApplicationControllersManager().getController(Controllers.CONTROLLER__REST_SERVICE_HELPER);
        handler = new MyServerResponseHandler(restServiceHelper);
        br = new MyBroadcastReceiver();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(br, intentFilter);
        Log.d(TAG, "Receiver registered in " + this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
        Log.d(TAG, "Receiver UNregistered from " + this.getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public IApplicationControllersManager getApplicationControllersManager() {
        return (IApplicationControllersManager) this.getApplication();
    }

    //--------------------------------------------------------------------------------
    public IRestClient getRestServiceHelper() {
        return restServiceHelper;
    }
    //--------------------------------------------------------------------------------

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle responseBundle = intent.getExtras();
            Message msg = new Message();
            msg.setData(responseBundle);
            handler.sendMessage(msg);
        }
    }

    static class MyServerResponseHandler extends Handler {
        private IRestClient restServiceHelper;

        public MyServerResponseHandler(IRestClient restServiceHelper) {
            this.restServiceHelper = restServiceHelper;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UniversalRestResponse response = WebRestService.extractResponse(msg.getData());
            this.restServiceHelper.onReceiveResponse(response);
        }
    }

}
