package ua.com.mobidev.android.mdrest.web.rest.request;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public class RequestId implements Serializable {
    private final String id;

    public static RequestId build() {
        return new RequestId(generateRequestId());
    }

    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    protected RequestId(String id) {
        if (id == null) throw new IllegalArgumentException();
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestId requestId = (RequestId) o;

        if (id != null ? !id.equals(requestId.id) : requestId.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
