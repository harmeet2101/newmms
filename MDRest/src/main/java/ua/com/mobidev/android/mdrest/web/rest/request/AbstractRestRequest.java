package ua.com.mobidev.android.mdrest.web.rest.request;

import android.util.Log;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.rest.client.RestMethod;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.http.HttpParams;

public abstract class AbstractRestRequest<Type_ParamEntity> implements Serializable {
    private final RequestDescriptor requestDescriptor;
    protected final String baseUrl;
    private HttpHeaders httpHeaders;
    private HttpParams httpParams;
    private Type_ParamEntity requestBody;

    public AbstractRestRequest(RestMethod restMethod, String baseUrl) {
        this.requestDescriptor = new RequestDescriptor(RequestId.build(), restMethod);
        this.baseUrl = baseUrl;
        this.httpHeaders = new HttpHeaders();
        this.httpParams = new HttpParams();
        this.requestBody = null;
    }

    public abstract String getParsedUrl();

    public String getUrl() {
        String parsedUrl = getParsedUrl();
        if (parsedUrl != null) {
            Log.d("baseURL", baseUrl + parsedUrl);
            return baseUrl + parsedUrl;
        } else {
            Log.d("baseURL", baseUrl + getRequestDescriptor().getRestMethod().getUrl());
            return baseUrl + getRequestDescriptor().getRestMethod().getUrl();
        }
    }

    public void setRequestBody(Type_ParamEntity requestBody) {
        this.requestBody = requestBody;
    }

    public RequestDescriptor getRequestDescriptor() {
        return requestDescriptor;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public HttpParams getHttpParams() {
        return httpParams;
    }

    public void setHttpParams(HttpParams httpParams) {
        this.httpParams = httpParams;
    }

    public Type_ParamEntity getRequestBody() {
        return requestBody;
    }


}
