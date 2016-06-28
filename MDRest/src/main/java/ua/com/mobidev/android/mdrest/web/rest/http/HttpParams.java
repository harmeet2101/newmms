package ua.com.mobidev.android.mdrest.web.rest.http;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpParams implements Serializable {
    private Map<String, String> params;

    public static final HttpParams getEmptyParams() {
        return new HttpParams(Collections.EMPTY_MAP);
    }

    public HttpParams() {
        this.params = new HashMap<>();
    }

    public HttpParams(Map<String, String> httpParams) {
        this.params = httpParams;
    }

    public Map<String, String> getAsMap() {
        // TODO possibly return cloned collection or immutable collection will be better. Or not?
        return params;
    }

    public String getAsString() {
        throw new UnsupportedOperationException();
    }

    public String putParam(String paramName, String paramValue) {
        return params.put(paramName, paramValue);
    }

    public String getParam(String paramName) {
        return params.get(paramName);
    }

    @Override
    public String toString() {
        return "HttpParams{" +
                "params=" + params +
                '}';
    }
}
