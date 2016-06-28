package com.mbopartners.mbomobile.rest.rest.method.volley_impl;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpMethod;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;
import ua.com.mobidev.android.mdrest.web.rest.response.HttpClientResult;

public class GsonRequest extends JsonRequest<UniversalRestResponse> {
    public static final boolean LOUD_MESSAGE_VALIDATION_MODE = false;
    public static final String VALIDATION_ERROR_MESSAGE = "Received data did not pass validation!";

    public static final int TIMEOUT_MS = 30 * 1000;

    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final AbstractRestRequest universalRestRequest;
    private long startTime,endTime;
    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder = gsonBuilder.setDateFormat("yyyy-MM-dd");
        return gsonBuilder.create();
    }

    public GsonRequest(AbstractRestRequest universalRestRequest,
                       Response.Listener<UniversalRestResponse> responseListener,
                       Response.ErrorListener errorListener) {

        super(
                transformHttpMethod(universalRestRequest.getRequestDescriptor().getRestMethod().getHttpMethod()),
                universalRestRequest.getUrl(),
                (getGson()).toJson(universalRestRequest.getRequestBody()),
                responseListener,
                errorListener
        );
        startTime=System.currentTimeMillis();
        this.universalRestRequest = universalRestRequest;
        this.headers = universalRestRequest.getHttpHeaders().getAsMap();
        this.params = universalRestRequest.getHttpParams().getAsMap();
        this.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<UniversalRestResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            endTime=System.currentTimeMillis();
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d("json",json);
            Log.d("time",String.valueOf(endTime-startTime));
//            json = json != null ? json : "{}";
            Class clazz = universalRestRequest.getRequestDescriptor().getRestMethod().getResponseEntityClass();
            Object responseEntity = getGson().fromJson(json, clazz);
            HttpClientResult httpClientResult = HttpClientResult.Ok;
            httpClientResult.setStatusCode(response.statusCode);
            UniversalRestResponse universalRestResponse = new UniversalRestResponse(
                    universalRestRequest,
                    httpClientResult,
                    new HttpHeaders(response.headers),
                    responseEntity
            );

            if (responseEntity != null && responseEntity instanceof Validatable) {
                Validatable entity = (Validatable) responseEntity;
                if ( ! entity.isValid() && LOUD_MESSAGE_VALIDATION_MODE) {
                    return Response.error(new VolleyError(getValidationException()));
                }
            }

            if (responseEntity != null && responseEntity.getClass().isArray()) {
                Validatable[] entities = (Validatable[]) responseEntity;
                if ( !ValidationHelper.validAll(entities) && LOUD_MESSAGE_VALIDATION_MODE) {
                    return Response.error(new VolleyError(getValidationException()));
                }
            }

            return Response.success(universalRestResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> paramsToSend = super.getParams();
        if (params != null && !params.isEmpty()) {
          paramsToSend.putAll(params);
        }
        return paramsToSend;
    }

    public static Exception getValidationException() {
        return new IllegalArgumentException("Received data does not pass validation!");
    }

    //================================================================================

    public static int transformHttpMethod(HttpMethod httpMethod) {
        switch (httpMethod) {
            case POST: {
                return Request.Method.POST;
            }
            case PUT: {
                return Request.Method.PUT;
            }
            case GET: {
                return Request.Method.GET;
            }
            case DELETE: {
                return Request.Method.DELETE;
            }
            default: {
                throw new UnsupportedOperationException();
            }
        }
    }
}

