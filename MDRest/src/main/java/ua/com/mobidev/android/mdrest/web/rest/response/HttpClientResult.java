package ua.com.mobidev.android.mdrest.web.rest.response;

public enum HttpClientResult {
    Ok(HttpClientResult.NOT_INITIALIZED, "Ok"),
    HttpError(HttpClientResult.NOT_INITIALIZED, "HttpError"),
    NoConnectionError(HttpClientResult.CLIENT_ERROR, "NoConnectionError"),
    NetworkError(HttpClientResult.CLIENT_ERROR, "NetworkError"),
    TimeoutError(HttpClientResult.CLIENT_ERROR, "TimeoutError"),
    ParseError(HttpClientResult.CLIENT_ERROR, "ParseError"),
    EntityValidationError(HttpClientResult.CLIENT_ERROR, "EntityValidationError"),
    Unknown(HttpClientResult.CLIENT_ERROR, "Unknown");

    private static final int NOT_INITIALIZED = -100500;
    private static final int CLIENT_ERROR = -1;

    private int statusCode = NOT_INITIALIZED;
    private String name = "";

    HttpClientResult(int statusCode, String name) {
        this.statusCode = statusCode;
        this.name = name;
    }

    public int getStatusCode() {
            return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        if (this == Ok || this == HttpError) {
            this.statusCode = statusCode;
        } else {
            throw new IllegalArgumentException("Can't set http status code for this ClientResult");
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
