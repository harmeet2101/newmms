package ua.com.mobidev.android.mdrest.web.rest.response;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.rest.http.HttpHeaders;
import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;

/**
 *
 */
public final class UniversalRestResponse<Type_Request extends AbstractRestRequest, Type_ResponseEntity> implements Serializable {

    Type_Request request;
    // Response details
    private final HttpClientResult clientResult;
    /** Response headers. */
    private final HttpHeaders headers;
    // Parsed data from this response. */
    private final Type_ResponseEntity responseEntity;

    public UniversalRestResponse(
            Type_Request request,
            HttpClientResult clientResult,
            HttpHeaders headers,
            Type_ResponseEntity responseEntity) {

        this.request = request;
        this.clientResult = clientResult;
        this.headers = headers;
        this.responseEntity = responseEntity;
    }

    public Type_Request getRequest() {
        return request;
    }

    public HttpClientResult getClientResult() {
        return clientResult;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public Type_ResponseEntity getResponseEntity() {
        return responseEntity;
    }

    @Override
    public String toString() {
        return "BaseRestResponse{" +
                "requestDescriptor=" + request.getRequestDescriptor() +
                ", clientResult=" + clientResult +
                ", headers=" + headers +
                ", responseEntity=" + responseEntity +
                '}';
    }

    //--------------------------------------------------------------------------------

}
