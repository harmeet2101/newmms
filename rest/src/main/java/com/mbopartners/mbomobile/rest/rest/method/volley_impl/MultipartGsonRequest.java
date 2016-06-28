package com.mbopartners.mbomobile.rest.rest.method.volley_impl;

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

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpMethod;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.HttpClientResult;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class MultipartGsonRequest extends JsonRequest<UniversalRestResponse> {
    private static final int TIMEOUT_MS = 30 * 1000;
    public static final String KEY_PICTURE = "file";

    private final File mFile;
    private HttpEntity mHttpEntity;

    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final AbstractRestRequest universalRestRequest;

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder = gsonBuilder.setDateFormat("yyyy-MM-dd");
        return gsonBuilder.create();
    }

    public MultipartGsonRequest(AbstractRestRequest universalRestRequest,
                                Response.Listener<UniversalRestResponse> responseListener,
                                Response.ErrorListener errorListener) {
        super(
                transformHttpMethod(universalRestRequest.getRequestDescriptor().getRestMethod().getHttpMethod()),
                universalRestRequest.getUrl(),
                null,
                responseListener,
                errorListener
        );
        this.universalRestRequest = universalRestRequest;
        this.headers = universalRestRequest.getHttpHeaders().getAsMap();
        this.params = universalRestRequest.getHttpParams().getAsMap();
        this.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        this.mFile = (File) universalRestRequest.getRequestBody();
        this.mHttpEntity = buildMultipartEntity(mFile);
    }

    @Override
    public String getBodyContentType() {
        String contentType = mHttpEntity.getContentType().getValue();
        return contentType;
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
        }
        return bos.toByteArray();
    }

    private HttpEntity buildMultipartEntity(File file) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        String fileName = file.getName();
        FileBody fileBody = new FileBody(file, ContentType.create("image/png"), "file");
        builder.addPart(KEY_PICTURE, fileBody);
        return builder.build();
    }

    @Override
    protected Response<UniversalRestResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
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
                if ( ! entity.isValid() ) {
                    return Response.error(new VolleyError(GsonRequest.getValidationException()));
                }
            }

            if (responseEntity != null && responseEntity.getClass().isArray()) {
                Validatable[] entities = (Validatable[]) responseEntity;
                if ( !ValidationHelper.validAll(entities) ) {
                    return Response.error(new VolleyError(GsonRequest.getValidationException()));
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
