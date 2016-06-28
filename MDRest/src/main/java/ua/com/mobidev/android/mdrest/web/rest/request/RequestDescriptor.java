package ua.com.mobidev.android.mdrest.web.rest.request;

import java.io.Serializable;

import ua.com.mobidev.android.mdrest.web.rest.client.RestMethod;


public class RequestDescriptor implements Serializable {
    private final RequestId requestId;
    private final RestMethod restMethod;
    private final long creationTimeStamp;

    protected RequestDescriptor(RequestId requestId, RestMethod restMethod) {
        if (requestId == null) {
            throw new IllegalArgumentException("Wrong requestId value. requestId == null");
        }

        this.requestId = requestId;
        this.restMethod = restMethod;
        this.creationTimeStamp = System.currentTimeMillis();
    }

    public RequestId getRequestId() {
        return requestId;
    }

    public RestMethod getRestMethod() {
        return restMethod;
    }

    public long getCreationTimeStamp() {
        return creationTimeStamp;
    }

    @Override
    public boolean equals(Object something) {
        if ( !(something instanceof RequestDescriptor) ) return false;
        RequestDescriptor descriptor = (RequestDescriptor) something;
        return this.requestId.equals(descriptor.requestId);
    }

    @Override
    public String toString() {
        return "RequestDescriptor{" +
                "requestId=" + requestId +
                ", restMethod=" + restMethod +
                ", creationTimeStamp=" + creationTimeStamp +
                '}';
    }
}
