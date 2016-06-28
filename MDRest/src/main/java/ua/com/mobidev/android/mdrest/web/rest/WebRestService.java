package ua.com.mobidev.android.mdrest.web.rest;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;

import ua.com.mobidev.android.framework.application.IApplicationControllersManager;
import ua.com.mobidev.android.framework.application.controller.Controllers;
import ua.com.mobidev.android.mdrest.web.rest.processor.IHttpRequestProcessor;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class WebRestService extends IntentService {
    private static final String INTENT_KEY_REQUEST = "INTENT_KEY_REQUEST__UNIVERSAL_REST_REQUEST";
    private static final String INTENT_KEY_RESPONSE = "INTENT_KEY_REQUEST__UNIVERSAL_REST_RESPONSE";
    private static final String ACTION_BROADCAST_REST = "ACTION_BROADCAST_REST__ua.com.mobidev.android.web.rest";

    IHttpRequestProcessor requestProcessor;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WebRestService(String name) {
        super(name);
    }

    public WebRestService() {
        super(WebRestService.class.getSimpleName());
        // Why we need constructor if we have onCreate() ???
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IApplicationControllersManager applicationService = (IApplicationControllersManager) this.getApplication();
        requestProcessor = (IHttpRequestProcessor) applicationService.getController(Controllers.CONTROLLER__REST_PROCESSOR);
        requestProcessor.setCallbackListener(new ProcessorCallbackListener());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
//            return Service.START_STICKY;
        }

        AbstractRestRequest universalRestRequest = extractRequest(intent);
        if (universalRestRequest == null) {
//            return Service.START_STICKY;
        }

        //ExecuteCommand executeCommand = new ExecuteCommand(command);
        // TODO Use Executor !!!
        requestProcessor.process(universalRestRequest);
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        if (intent == null) {
//            return Service.START_STICKY;
//        }
//
//        AbstractRestRequest universalRestRequest = extractRequest(intent);
//        if (universalRestRequest == null) {
//            return Service.START_STICKY;
//        }
//
//        //ExecuteCommand executeCommand = new ExecuteCommand(command);
//        // TODO Use Executor !!!
//        requestProcessor.process(universalRestRequest);
//
//        return Service.START_STICKY;
//    }

    private class ProcessorCallbackListener implements IHttpRequestProcessor.Callback {
        @Override
        public void onComplete(UniversalRestResponse restResponse) {
            Intent intent = buildResponseIntent(getApplicationContext(), restResponse);
            sendBroadcast(intent);
        }

        @Override
        public void onError(UniversalRestResponse restResponse) {
            Intent intent = buildResponseIntent(getApplicationContext(), restResponse);
            sendBroadcast(intent);
        }
    }

    //--------------------------------------------------------------------------------
    //
    // Static methods
    //
    //--------------------------------------------------------------------------------
    public static Intent buildRequestIntent(Context context, AbstractRestRequest request) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_KEY_REQUEST, request);
        Intent intent = new Intent(context, WebRestService.class);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent buildResponseIntent(Context context, UniversalRestResponse universalRestResponse) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_KEY_RESPONSE, universalRestResponse);
        Intent intent = new Intent(ACTION_BROADCAST_REST);
        intent.putExtras(bundle);
        return intent;
    }

    public static AbstractRestRequest extractRequest(Intent intent) {
        if (intent == null) {
            return null;
        }

        AbstractRestRequest request = null;
        Bundle bundle = intent.getExtras();
        if ((bundle != null) && bundle.containsKey(INTENT_KEY_REQUEST)) {
            request = (AbstractRestRequest) bundle.getSerializable(INTENT_KEY_REQUEST);
        }
        return request;
    }

    public static UniversalRestResponse extractResponse(Intent intent) {
        if (intent == null) {
            return null;
        }
        return extractResponse(intent.getExtras());
    }

    public static UniversalRestResponse extractResponse(Bundle bundle) {
        UniversalRestResponse response = null;
        if ((bundle != null) && bundle.containsKey(INTENT_KEY_RESPONSE)) {
            response = (UniversalRestResponse) bundle.getSerializable(INTENT_KEY_RESPONSE);
        }
        return response;
    }

    public static IntentFilter buildBroadcastResponseFilter() {
        IntentFilter intentFilter = new IntentFilter(ACTION_BROADCAST_REST);
        return intentFilter;
    }
}
