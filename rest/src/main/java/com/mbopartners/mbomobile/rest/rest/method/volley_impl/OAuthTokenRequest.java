package com.mbopartners.mbomobile.rest.rest.method.volley_impl;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.mbopartners.mbomobile.rest.model.param.OAuthBodyEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpMethod;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;
import ua.com.mobidev.android.mdrest.web.rest.response.HttpClientResult;

/**
 *
 */
public class OAuthTokenRequest extends JsonRequest<UniversalRestResponse> {
    private final Gson gson = new Gson();
    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final AbstractRestRequest universalRestRequest;


    public OAuthTokenRequest(AbstractRestRequest universalRestRequest,
                             Response.Listener<UniversalRestResponse> responseListener,
                             Response.ErrorListener errorListener) {
        super(
                transformHttpMethod(universalRestRequest.getRequestDescriptor().getRestMethod().getHttpMethod()),
                universalRestRequest.getUrl(),
                buildBodyAsString((OAuthBodyEntity)universalRestRequest.getRequestBody()),
                responseListener,
                errorListener
        );
        this.universalRestRequest = universalRestRequest;
        this.headers = universalRestRequest.getHttpHeaders().getAsMap();
        this.params = universalRestRequest.getHttpParams().getAsMap();
    }

    @Override
    protected Response<UniversalRestResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Class clazz = universalRestRequest.getRequestDescriptor().getRestMethod().getResponseEntityClass();
            Object responseEntity = gson.fromJson(json, clazz);
            HttpClientResult httpClientResult = HttpClientResult.Ok;
            httpClientResult.setStatusCode(response.statusCode);
            UniversalRestResponse universalRestResponse = new UniversalRestResponse(
                    universalRestRequest,
                    httpClientResult,
                    new HttpHeaders(response.headers),
                    responseEntity
            );

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
private static String buildBodyAsString(Object body) {
    OAuthBodyEntity params = (OAuthBodyEntity) body;
    String bodyAsString =
            "username=" + (params.username !=null ? params.username : "") + "&" +
            "password=" + (params.password !=null ? params.password : "") + "&" +
            "refresh_token=" + (params.refreshToken !=null ? params.refreshToken : "") + "&" +
            "grant_type=" + (params.grantType !=null ? params.grantType : "");
    return bodyAsString;
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

