package ua.com.mobidev.android.mdrest.web.rest.http;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders implements Serializable {
    private Map<String, String> headers;

    public static final HttpHeaders getEmptyHeaders() {
        return new HttpHeaders(Collections.EMPTY_MAP);
    }

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public HttpHeaders(Map<String, String> httpHeaders) {
        this.headers = httpHeaders;
    }

    public Map<String, String> getAsMap() {
        // TODO possibly return cloned collection or immutable collection will be better. Or not?
        return headers;
    }

    public String getAsString() {
        throw new UnsupportedOperationException();
    }

    public String putHeader(String headerName, String headerValue) {
        return headers.put(headerName, headerValue);
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }
}
