package com.mbopartners.mbomobile.rest.rest.method.stub_impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import ua.com.mobidev.android.mdrest.web.rest.client.RestMethod;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.method.AbstractHttpRestMethod;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.request.RequestDescriptor;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;
import ua.com.mobidev.android.mdrest.web.rest.response.HttpClientResult;

/**
 * http REST Method STUB implementation.
 * For testing and early development.
 */
public class StubRestMethodImpl extends AbstractHttpRestMethod {
    private static final String TAG = StubRestMethodImpl.class.getSimpleName();
    private static final int PAUSE_FOR_EMULATING_CONNECTION_TO_SERVER = 3;

    private Context context;

    public StubRestMethodImpl(Context context) {
        this.context = context;

    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder = gsonBuilder.setDateFormat("yyyy-MM-dd");
        return gsonBuilder.create();
    }

    @Override
    public <Type_ParamEntity, Type_Response extends UniversalRestResponse, Type_DbHandler> void start(AbstractRestRequest<Type_ParamEntity> restRequest) {
        RequestDescriptor requestDescriptor = restRequest.getRequestDescriptor();
        RestMethod restMethod = requestDescriptor.getRestMethod();
        HttpHeaders requestHeaders = restRequest.getHttpHeaders();

        String filename = restMethod.getName() + ".json";
        String json = loadJSONFromAsset(filename);

        Class clazz = restRequest.getRequestDescriptor().getRestMethod().getResponseEntityClass();
        Object responseEntity = getGson().fromJson(json, clazz);
        HttpClientResult httpClientResult = HttpClientResult.Ok;
        httpClientResult.setStatusCode(200);
        UniversalRestResponse universalRestResponse = new UniversalRestResponse(
                restRequest,
                httpClientResult,
                HttpHeaders.getEmptyHeaders(),
                responseEntity
        );

//        try {
//            TimeUnit.SECONDS.sleep(PAUSE_FOR_EMULATING_CONNECTION_TO_SERVER);
//        } catch (InterruptedException e) {
//        }

        responseProcessor.processSuccessResponse(universalRestResponse);
    }

    @Override
    public void cancelAllRequests() {

    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
